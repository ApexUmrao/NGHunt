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
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class WBGItqanEIDAChecksum implements ICallExecutor, Callable<String> {
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String EIDANum;
	private String authSign;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String senderID="WMS";
	private String leadRefNumber ="";
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private CallEntity callEntity;
	private String auditCallName = "WBGEIDAChecksum";
	private String sourcingChannel;
	private String CID;
	private String COMPLETE_FLAG;
	boolean isCallExecuted;
	
	public WBGItqanEIDAChecksum( Map<String, String> attributeMap,String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE WBGitqanEidachecksum");
		String outputXML;
		try {
			XMLParser xp; 
			EIDANum = attributeMap.get("EIDA_NO");
			COMPLETE_FLAG = attributeMap.get("COMPLETE_FLAG");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EIDANum in EIDA Checksum===>"+EIDANum);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"COMPLETE_FLAG in EIDA Checksum===>"+COMPLETE_FLAG);
			if(EIDANum==null || EIDANum.equals("") ){
				outputXML = APCallCreateXML.APSelect("SELECT  EIDANO, CUST_ID FROM USR_0_WBG_AO_AUS  WHERE WI_NAME = N'" + WI_NAME + "'");
				xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
				if(mainCode == 0){
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){		
						EIDANum = xp.getValueOf("EIDANO");
					}
				}
			}
			if(EIDANum==null || EIDANum.equals("") ){
				updateCheckFlag = false;
			}

			String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp1 = new XMLParser(soutputXML);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA001", "EIDADedupe Started", sessionId);
			if(mainCode1 == 0){
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){

					leadRefNumber = xp1.getValueOf("LEAD_REF_NO");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber"+leadRefNumber);
					LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA002", EIDANum, sessionId);
				}
			}			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"WBGEIDAChecksum call: inside");
		String inputXml = "";
		String outputXml = "";
		callStatus = "Y";
		isCallExecuted = LapsUtils.isItqanMCallExecuted(auditCallName,WI_NAME);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted " + isCallExecuted);

		
		try {			
			if(!isCallExecuted){
				if(updateCheckFlag){
					inputXml = createInputXML();
					outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WBGEIDAChecksum outputXml ===> " + outputXml);
					if(outputXml==null || outputXml.equalsIgnoreCase("")){
						callStatus = "F";
						outputXml= "<returnCode>2</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				}
				else {
					callStatus = "F";
					errorDescription = "EIDA No cannot be blank";
					returnCode = 1;
					updateCallOutput(inputXml);
				}
			}
			if(!callStatus.equalsIgnoreCase("F")){
				executeDependencyCall();
			}			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}

		return outputXml;

	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder(); 
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>ValidateAUSEIDA</InnerCallType>").append("\n")
			.append("<useCase>Inquiry</useCase>").append("\n")
			.append("<Customer>").append("\n")
			.append("<CustID>" + CID + "</CustID>").append("\n")
			.append("<SENDERID>"+ senderID + "</SENDERID>").append("\n")
			.append("<EIDA_No>" + EIDANum + "</EIDA_No>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<USER>System</USER>").append("\n")
			.append("<CREDENTIALS>****</CREDENTIALS>").append("\n")
			.append("<WiName>" + WI_NAME + "</WiName>").append("\n")
			.append("</Customer>").append("\n")
			.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "EIDA Checksum inputXML ===> "+inputXML.toString());
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
		return inputXML.toString(); 
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		// TODO Auto-generated method stub
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, attributeMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		try{
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			if(returnCode == 0){
				callStatus = "Y";
			} else if(returnCode == 1 ){
					callStatus = "N";
			} else if(returnCode == 2){
				callStatus = "F";
			}
			updateCallOutput(EIDANum);
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus "+callStatus);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errorDescription "+errorDescription);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returnCode "+returnCode);

		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}




	}

	@Override
	public void updateCallOutput(String EidaNumber) throws Exception {
		String finalStatus = "F";
		if(callStatus == "Y" || callStatus == "N"){
			finalStatus = "Y";
		}
		if(LapsUtils.isCallNameInBpmCallOut(auditCallName,WI_NAME)){
			String valList = "'"+ callStatus +"','"+ returnCode +"','" + errorDescription +"'";
//			APCallCreateXML.APUpdate("BPM_CALL_OUT", "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION",valList, " WI_NAME = N'"+ winame +"'  AND CALL_STATUS = '"+auditCallName+"'", sessionId);
			String sTable_info = "BPM_CALL_OUT";
			String sColumn = "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION";
			String sWhere = "wi_name = N'" + WI_NAME + "' AND CALL_NAME = '"+auditCallName+"'";

			APCallCreateXML.APUpdate(sTable_info, sColumn, valList, sWhere, sessionId);

		} else {
			try {
				String Retry_flag ;
				String valueList = "'"+ WI_NAME +"',"+ StageID +",'" + auditCallName +"', '"+ finalStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"'";
				APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
						+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
				APCallCreateXML.APUpdate("EXT_WBG_AO", "EIDA_CHKSUM_FLAG", " "+ callStatus +" ", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				String noResultsFoundMsg = "";
				String sActivityName = "";
				String reasonForAction = "" ;
				String action = "";
				String errDetail = "200";

				if(callStatus == "Y"){
					noResultsFoundMsg = "EIDA Validation Successful";
					sActivityName = "Introduction";
					reasonForAction = "Eida Validation for EIDA Number: " + EidaNumber;
					action = "EIDA Validation Successful";
					errorDescription = "EIDA Validation Successful";
				} else if(callStatus == "F"){
					Retry_flag = "Y";
					String tables = "EXT_WBG_AO";
					String col = "RETRY_FLAG";
					String val = "'" + Retry_flag + "'";
					String where = "wi_name = N'" + WI_NAME
							+ "' ";
					APCallCreateXML.APUpdate(tables, col, val, where, sessionId);
					ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);

				}  else {
					callStatus = "N";
					noResultsFoundMsg = "EIDA Validation Failed";
					sActivityName = "Introduction";
					reasonForAction = "Eida Validation for EIDA Number: " + EidaNumber;
					action = "EIDA Validation Failed";
					String pushMessageXML = LapsUtils.createPushMesgXML(leadRefNumber,WI_NAME,
							callStatus,errDetail,errorDescription);
					LapsUtils.updatePushMeassgeXML(sessionId,WI_NAME,pushMessageXML);
					PushMessageItqanM pushMsg = new PushMessageItqanM(sessionId, WI_NAME);
					pushMsg.call();
					ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);

				String valList = "'"+ WI_NAME +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
				APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);
				if(COMPLETE_FLAG == "Y"){
					ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				}



			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
	}
	
}
