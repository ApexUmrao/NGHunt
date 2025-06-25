package com.newgen.cbg.calls;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;

public class SuppLeadUpdate implements ICallExecutor, Callable<String> {

	private String wiName;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String senderID;
	private String rmName="";
	private String emailKeyContactPerson;
	private String firstKeyContactPerson;
	private String mobKeyContactPerson;
	private String leadRefNo;
	private String customerId;
	private String refNo;
	private String leadStatus;
	private String productCode;
	private String productGroup;
	private String portalChannel;
	private String accessPoint="";
	private String businessSegment="";
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "LDUPDT";
	private String ntbCust;
	private String skipModify;
	private String outputXml;

	public SuppLeadUpdate(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId = sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT SUPP_CARDHOLDER_FULL_NAME, CUSTOMER_EMAIL, CUSTOMER_MOBILE_NO, LEAD_REF_NO,"
					+ "CUSTOMER_ID,IS_NTB_CUST,SKIP_MODIFY FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT001", "SuppLeadUpdate Started", sessionId);
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					firstKeyContactPerson = xp.getValueOf("CUSTOMER_NICK_NAME");
					emailKeyContactPerson = xp.getValueOf("CUSTOMER_EMAIL"); 
					mobKeyContactPerson = xp.getValueOf("CUSTOMER_MOBILE_NO");
					leadRefNo = xp.getValueOf("LEAD_REF_NO");
					customerId = xp.getValueOf("CUSTOMER_ID");
					ntbCust = defaultMap.get("IS_NTB_CUST");
					skipModify=defaultMap.get("SKIP_MODIFY");
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "RMNAME: "+rmName);
			}
			senderID = defaultMap.get("SENDER_ID");
			productGroup = "CASA";
		    portalChannel = "MIB";
			productCode="Digital Current Savings Account";
			leadStatus="Closed";
			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML(); 
	    outputXml = "<returnCode>0</returnCode>";
		try {
			if(!prevStageNoGo){
				outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
			}	
			responseHandler(outputXml, inputXml);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT002", "SuppLeadUpdate outputXml: "+outputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			String outputXMLTemp = "";
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT003", "SuppLeadUpdate DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
				outputXml = outputXMLTemp;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
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
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>LeadCreation</DSCOPCallType>").append("\n")
			.append("<REF_NO>"+refNo+"</REF_NO>").append("\n")
			.append("<senderID>"+senderID+"</senderID>").append("\n")
			.append("<serviceAction>Modify</serviceAction>").append("\n")
			.append("<RequestDateTime>"+new Date().toString()+"</RequestDateTime>")
			.append("<IntegrationId>" + wiName  + "</IntegrationId>").append("\n")															
			.append("<winame>" + wiName  + "</winame>").append("\n")
			.append("<LeadStatus>"+leadStatus+"</LeadStatus>").append("\n")
			.append("<LeadRefNumber>" + leadRefNo  + "</LeadRefNumber>").append("\n")
			.append("<ProductCode>" + productCode  + "</ProductCode>").append("\n")
			.append("<ProductGroup>" + productGroup  + "</ProductGroup>").append("\n")
			.append("<SourceChannel>" + portalChannel  + "</SourceChannel>").append("\n")
			.append("<BusinessSegment>"+businessSegment+"</BusinessSegment>").append("\n")
			.append("<EmailKeyContactPerson>"+ emailKeyContactPerson +"</EmailKeyContactPerson>").append("\n")
			.append("<FirstNKeyContactPerson>" + firstKeyContactPerson  + "</FirstNKeyContactPerson>").append("\n")
			.append("<MobKeyContactPerson>" + mobKeyContactPerson  + "</MobKeyContactPerson>").append("\n")
			.append("<AccessPoint>" + accessPoint  + "</AccessPoint>").append("\n")		
			.append("<CustomerId>" + customerId + "</CustomerId>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "LeadUpdate inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
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
			if(!prevStageNoGo){
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
				errorDescription = xp.getValueOf("errorDescription", "", true);
				errorDetail = xp.getValueOf("errorDetail", "", true);
				status = xp.getValueOf("status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0){
					callStatus = "Y";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "LDUPDT090", "LeadUpdate Successfully Executed", sessionId);
				} else {
					callStatus = "N";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "LDUPDT101", "LeadUpdate Failed", sessionId);
				}
			} else {
				callStatus = "N";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "LDUPDT103", "LeadUpdate Failed", sessionId);
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {

			APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppLeadUpdate' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);

			String valList = "'"+ wiName +"',"+ stageId +", 'SuppLeadUpdate', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppLeadUpdate', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);			
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppLeadUpdate', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
			if (!(ntbCust.equalsIgnoreCase("N") && skipModify.equalsIgnoreCase("Y"))) {
				APCallCreateXML.APUpdate("EXT_DSCOP", "COMPLETE_FLAG","'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
	}
}



