package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class WBGInqAccountDetails implements ICallExecutor, Callable<String>{
	
	private String sessionId;
	private String callStatus = "N";
	private int returnCode = 0;
	private String errorDetail;
	private String errorDescription;
	private int stageId;
	private Map<String, String> attributeMap;
	private String wiName;
	private String mode;
	private String accNo;
	private String outputXML;
	private String requestCategory;
	private String auditCallName = "WBGInqAccountDetails";
	private String leadRefNumber ="";
	private String status ="";
	private String reason ="";
	private String acctNumber ="";
	private boolean prevStageNoGo;
    private String acctNumberIBAN ="";
	private String acctTitle ="";
	private String acctBranchCode ="";
	private String acctOps1 ="";
	private String acctBranchType ="";
	private String acctProductCode ="";
	private String acctCurrency ="";
	private String acctStatusCode ="";
	private String acctStatusDescription ="";
	private CallEntity callEntity;
	String senderId = "ICCS";
	boolean isCallExecuted;

	
	 HashMap<String, String> attribMap = new HashMap<String, String>();

	public WBGInqAccountDetails(Map<String, String> attributeMap, String sessionId, String stageId, String wiName, 
			Boolean prevStageNoGo, CallEntity callEntity){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside FetchAccountDetails >> ");
		try {
			this.sessionId =sessionId;
			this.wiName = wiName;
			this.attributeMap = attributeMap;
			this.mode = attributeMap.get("mode");
			this.prevStageNoGo = prevStageNoGo;
			this.stageId = Integer.parseInt(stageId);
			this.callEntity = callEntity;
			outputXML = APCallCreateXML.APSelect("SELECT ACC_NO FROM USR_0_WBG_AO_ACC_INFO"
					+ " WHERE WI_NAME = N'" + wiName + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, wiName, auditCallName, "FTD001", "Started", sessionId);
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					accNo = xp.getValueOf("ACC_NO");

					if(accNo == null || accNo.isEmpty()){
						callStatus = "F";
//						returnCode = 0;
					}
				}
			}
			String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + wiName + "'");
			XMLParser xp1 = new XMLParser(soutputXML);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, wiName, auditCallName, "EIDA001", "EIDADedupe Started", sessionId);
			if(mainCode1 == 0){
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){

					leadRefNumber = xp1.getValueOf("LEAD_REF_NO");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber"+leadRefNumber);
					LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, wiName, auditCallName, "EIDA002", accNo, sessionId);
				}
			}	
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "FetchAccountDetails exception: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

	@Override
	public String call() throws Exception {
		String sInput = createInputXML();
		String outputXml = "";
		callStatus = "Y";
		isCallExecuted = LapsUtils.isItqanMCallExecuted(auditCallName,wiName);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted " + isCallExecuted);

		try {			
			if(!isCallExecuted){	
				if(!prevStageNoGo && (!"".equalsIgnoreCase(accNo) && null != accNo)){
					outputXml = LapsConfigurations.getInstance().socket.connectToSocket(sInput);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Fetch account Details outputXml ===> " + outputXml);
					if(outputXml==null || outputXml.equalsIgnoreCase("")){
						callStatus = "F";
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, sInput);
				} else {
					errorDescription = "Account No cannot be blank";
					returnCode  = 1;
					callStatus = "F";
					updateCallOutput(sInput);
				}
			}
			if(!callStatus.equalsIgnoreCase("F")){
				executeDependencyCall();
			}

		} catch (NGException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in Fetch Account Details call(): ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

		if(returnCode == 2){
			returnCode = 0;
		}
		outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
	}
	
	

	@Override
	public String createInputXML() throws Exception {
		StringBuffer inputXml = new StringBuffer();
		try {
			if (null != accNo && !"0".equalsIgnoreCase(accNo)) {
				inputXml.append("<APWebService_Input>")
				.append("<Option>WebService</Option>")
				.append("<Calltype>TFO_AcctDetailsICCS</Calltype>")
				.append("<sysRefNumber>" + LapsUtils.getInstance().generateSysRefNumber() +"</sysRefNumber>")
				.append("<senderID>" + senderId + "</senderID>")
				.append("<WiName>" + wiName + "</WiName>")
				.append("<ServiceName>InqAccountDetailsICCS</ServiceName>")
				.append("<InqAccountDetailsICCSReq>")
				.append("<CreditAccountInput>")
				.append("<acctNumber>" + accNo + "</acctNumber>")
				.append("</CreditAccountInput>")
				.append("</InqAccountDetailsICCSReq>")
				.append("</APWebService_Input>");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"createAcctDetailsInputXML Xml ===>  " + inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in createAcctDetailsInputXML: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

		return inputXml.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, wiName, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, attributeMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output xml inside responseHandler :" + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
			errorDescription = xp.getValueOf("errorDescription");
			errorDetail = xp.getValueOf("errorDetail");
			status = xp.getValueOf("Status");
			reason = xp.getValueOf("Reason");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "returnCode :" + returnCode);
			if(returnCode == 0 || returnCode == 2){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, wiName, auditCallName, "FTD001", "Started", sessionId);
				acctNumber = xp.getValueOf("acctNumber");
				acctNumberIBAN = xp.getValueOf("acctNumberIBAN");
				acctTitle = xp.getValueOf("acctTitle");
				acctBranchCode = xp.getValueOf("acctBranchCode");
				acctBranchType = xp.getValueOf("acctBranchType");
				acctProductCode = xp.getValueOf("acctProductCode");
				acctCurrency = xp.getValueOf("acctCurrency");
				acctStatusCode = xp.getValueOf("acctStatusCode");
				acctStatusDescription =xp.getValueOf("acctStatusDescription");
				acctOps1 = xp.getValueOf("acctModeofOps1");
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "acctProductCode :"+ xp.getValueOf("acctProductCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "acctNumber :" +acctNumber);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "acctProductCode :"+ acctProductCode);
			} else {
				callStatus = "F";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
		
	}

	@Override
	public void updateCallOutput(String input) throws Exception {
		if(LapsUtils.isCallNameInBpmCallOut(auditCallName,wiName)){
			String valList = "'"+ callStatus +"','"+ returnCode +"','" + errorDescription +"'";
//			APCallCreateXML.APUpdate("BPM_CALL_OUT", "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION",valList, " WI_NAME = N'"+ winame +"'  AND CALL_STATUS = '"+auditCallName+"'", sessionId);
			String sTable_info = "BPM_CALL_OUT";
			String sColumn = "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION";
			String sWhere = "wi_name = N'" + wiName + "' AND CALL_NAME = '"+auditCallName+"'";

			APCallCreateXML.APUpdate(sTable_info, sColumn, valList, sWhere, sessionId);

		} else {
		try {
			
			String valueList = "'"+ wiName +"',"+ stageId +",'" + auditCallName +"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
			//APCallCreateXML.APUpdate("EXT_WBG_AO", "EIDA_CHKSUM_FLAG", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
			String noResultsFoundMsg = "";
			String sActivityName = "";
			String reasonForAction = "" ;
			String action = "";

			if(callStatus == "Y"){
				noResultsFoundMsg = "SUCCESS";
				sActivityName = "Introduction";
				reasonForAction = " Fetch Account Details for " + accNo;
				action = "Fetch Account Details Success";
			} else {
				noResultsFoundMsg = "FAILURE";
				sActivityName = "Introduction";
				reasonForAction = "Fetch Account Details Failed for  " + accNo;
				action = "Fetch Account Details Failed";
				APCallCreateXML.APUpdate("EXT_WBG_AO", "REPAIR_FLAG", "'Y'", "WI_NAME = '"+wiName+"'", 
						sessionId);
				APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG", "'Y'", "WI_NAME = '"+wiName+"'", 
						sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);

			String valList = "'"+ wiName +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
			APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);
			

			if(callStatus.equals("Y")){
				
					String columnList = "ACC_NO,IBAN_NUMBER,ACCOUNT_TITLE,ACCBRANCH,"
							+ "ACCOUNT_TYPE,PRODCODE,PRODCURR,ACCSTATUS,PRODDESC,OPERATING_INST";
					String valList3 = "'"+acctNumber+"','"+acctNumberIBAN+"','"+acctTitle+"','"+acctBranchCode+"','"+acctBranchType+"',"
							+ "'"+acctProductCode+"','"+acctCurrency+"','"+acctStatusCode+"','"+acctStatusDescription+"','"+acctOps1+"'";
					APCallCreateXML.APUpdate("USR_0_WBG_AO_ACC_INFO", columnList, valList3, " WI_NAME = '"+wiName+"'", sessionId);
			} 

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
		
	}
}
