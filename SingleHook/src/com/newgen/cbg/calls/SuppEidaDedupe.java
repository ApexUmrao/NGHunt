package com.newgen.cbg.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class SuppEidaDedupe implements ICallExecutor, Callable<String>{


	private String wiName;
	private int stageId;
	private String sessionId;
	private String eidaNum;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "EIDA_DEDUPE";
	private String customerID;
	private String callDedupe;
	public SuppEidaDedupe( Map<String, String> defaultMap,String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = fetchEidaNo();
			handleEidaNo(outputXML);
			senderID = defaultMap.get("SENDER_ID");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try 
		{
			if(!prevStageNoGo){
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
				errorDescription = xp.getValueOf("errorDescription", "", true);
				errorDetail = xp.getValueOf("errorDetail", "", true);
				status = xp.getValueOf("Status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0){
					String customerID = xp.getValueOf("customerId", "", true);
					boolean dedupe = customerID == null || customerID.equalsIgnoreCase("") || customerID.equals(this.customerID);
					if(dedupe){
						callStatus = "Y";
					}
					else {
						callStatus = "D";
						callDedupe = "Y";
						errorDescription = "EIDA DEDUPE FOUND!! For Customer ID : " +customerID;
						DSCOPUtils.getInstance().insertDuplicateValue(wiName, "Mobile","FCUBS", sessionId);
					}
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "EIDA090", "EidaDedupe Successfully Executed - "+errorDescription, sessionId);
				} else {
					if(errorDetail.contains("101344")){
						callStatus = "D";
						errorDescription = "EIDA DEDUPE FOUND!! Multiple Customer IDs mapped to same EIDA";
						DSCOPUtils.getInstance().insertDuplicateValue(wiName, "Mobile","FCUBS", sessionId);
					}
					else if(errorDetail.contains("FCUBS System Timeout")){
						callStatus = "D";
						errorDescription="FCUBS System Timeout";
					}
					else{
						callStatus = "Y";
						errorDescription="EIDA FCUBS Dedupe Not Found";
						returnCode = 1;
					}
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "EIDA003", errorDescription, sessionId);
				}
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "EIDA101", "EidaDedupe Failed", sessionId);
			}
			updateCallOutput(inputXml);
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder(); 
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>CustEIDAInfo</DSCOPCallType>").append("\n")
			.append("<ServiceName>CustomerInfo</ServiceName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>").append("\n")
			.append("<EIDANum>" + eidaNum + "</EIDANum>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "EIDADedupe inputXML ===> "+inputXML.toString());
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "<returnCode>0</returnCode>";
		try {
			if(!prevStageNoGo){
				if(updateCheckFlag){					
					outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "EidaDedupe outputXml ===> " + outputXml);
					if(outputXml==null ||outputXml.equalsIgnoreCase("")){
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml,inputXml);
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA004", "EIDAMQ output:"+outputXml, sessionId);
					if(returnCode==1){
						outputXml = "<returnCode>0</returnCode>";
					}
				} else {
					callStatus = "X";
					executeDependencyCall();
					updateCallOutput(inputXml);
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA005", "EIDADedupe call Skipped", sessionId);
				}

			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
			}
		}catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {

		String valList = "'"+ wiName +"',"+ stageId +", 'SuppEidaDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
				+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

		if(callStatus.equals("Y") || callDedupe.equals("Y")){
			String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppEidaDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
			APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
					+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			executeDependencyCall();
		}
		else {
			String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppEidaDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
			APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
					+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
		}
		try {
			if(!prevStageNoGo){
				String output = APCallCreateXML.APUpdate("EXT_DSCOP", "EIDA_DEDUPE", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
				XMLParser xp = new XMLParser(output);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EidaDedupe : "+ mainCode);
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "EidaDedupe : "+ e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "EIDA100", e, sessionId);
		}
	}
	public String fetchEidaNo() throws Exception {
		return APCallCreateXML.APSelect("SELECT EIDA_NUMBER FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}
	private void handleEidaNo(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA100", "EIDA100 Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EidaDedupe TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				eidaNum = xp.getValueOf("EIDA_NUMBER");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA_DEDUPE", eidaNum, sessionId);
				if(eidaNum==null || eidaNum.equals("") ){
					updateCheckFlag = false;
				}
			}

		}
	}

}
