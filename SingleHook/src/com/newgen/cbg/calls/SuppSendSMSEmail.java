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

public class SuppSendSMSEmail implements ICallExecutor, Callable<String> {
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String senderID;
	private String customerID;
	private String acctNumber;
	private String transactionType;
	private String sendAsChannel;
	private String smsTemplateID;
	private String smsTextValues;
	private String mobileNumber;
	private String refNo;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SNDSMSEML";

	public SuppSendSMSEmail( Map<String, String> defaultMap,String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity)
	{
		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_ID, CUSTOMER_MOBILE_NO FROM EXT_DSCOP "
					+ "WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SNDSMSEML001", "SuppSendSMSEmail Started", sessionId);
			if(mainCode == 0) {
				customerID = xp.getValueOf("CUSTOMER_ID");//"19975325";
//				acctNumber = xp.getValueOf("ACCOUNT_NUMBER");
				mobileNumber = xp.getValueOf("CUSTOMER_MOBILE_NO");
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SNDSMSEML100", e, sessionId);
		}	

		//DEFAULTS
		senderID = defaultMap.get("SENDER_ID");
		transactionType = "AC";
		sendAsChannel = "S";
		//smsTemplateID = "1511";
		//smsTextValues = "19975325449001|19975325|CORNICHE|AE910030019975325449001|Investment Account - KWD|KWD|21-OCT-14|HOWARD GUANT||";
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "<returnCode>0</returnCode>";
		try {
			if(!prevStageNoGo){
				outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppSendSMSEmail OutputXml: "+ outputXml);
				if(outputXml==null ||outputXml.equalsIgnoreCase("")){
					outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(outputXml, inputXml);
			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SNDSMSEML100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SNDSMSEML003", "SuppSendSMSEmail DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SNDSMSEML100", e, sessionId);
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
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>SEND_SMS</DSCOPCallType>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
			.append("<username>" + customerID + "</username>").append("\n")
			.append("<CUST_ID>" + customerID + "</CUST_ID>").append("\n")
			.append("<Account_Number>" + acctNumber + "</Account_Number>").append("\n")
			.append("<transactionType>" + transactionType + "</transactionType>").append("\n")
			.append("<sendAsChannel>" + sendAsChannel + "</sendAsChannel>").append("\n")
			.append("<TEMPLATE_ID>" + smsTemplateID + "</TEMPLATE_ID>").append("\n")
			.append("<MSG>" + smsTextValues + "</MSG>").append("\n")
			.append("<Mobile>" + mobileNumber + "</Mobile>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppSendSMSEmail inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SNDSMSEML100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			if(!prevStageNoGo) {
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
				errorDescription = xp.getValueOf("errorDescription", "", true);
				errorDetail = xp.getValueOf("errorDetail", "", true);
				status = xp.getValueOf("Status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0){
					callStatus = "Y";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, auditCallName, "SNDSMSEML090", "SuppSendSMSEmail Successfully Executed", sessionId);
				} else {
					callStatus = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, auditCallName, "SNDSMSEML101", "SuppSendSMSEmail Failed", sessionId);
				}
			} else {
				callStatus = "N";
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SNDSMSEML100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ WI_NAME +"',"+ StageID +", 'SuppSendSMSEmail', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ WI_NAME +"',"+ StageID +", 'SuppSendSMSEmail', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);			
			}
			else {
				String valList2 = "'"+ WI_NAME +"',"+ StageID +", 'SuppSendSMSEmail', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SNDSMSEML100", e, sessionId);
		}
	}
}
