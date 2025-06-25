package com.newgen.cbg.calls;

import java.util.Date;
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

public class SuppLeadCreation implements ICallExecutor, Callable<String>  {

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
	private String emailKeyContactPerson;
	private String firstKeyContactPerson;
	private String mobKeyContactPerson;
	private String productCode;
	private String leadStatus;
	private String productGroup;
	private String sourcingchannel;
	private String passportNumber;
	private String customerId;
	private String nationalityCode;
	private String journeyType;
	private String primaryCid;
	private boolean prevStageNoGo;
	private String refNo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SSLDCRTN";
	private String outputXML;
	private String associateCard;
	private String finalOutputXml;
	private String cardNumber;
	private String accountNumber;
	private String transactionType;


	public SuppLeadCreation(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId = sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;

		try {
			outputXML =fetchLeadCreationData();
			handleLeadCreationData(outputXML);		
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML(); 
		finalOutputXml = "<returnCode>0</returnCode>";
		try {
			if(!prevStageNoGo){
				finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppCreditCardCreation outputXml ===> " + finalOutputXml);
				if(finalOutputXml==null ||finalOutputXml.equalsIgnoreCase("")){
					finalOutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
			}	
			responseHandler(finalOutputXml, inputXml);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SLDCRTN002", "CCSSOLeadCreation outputXml: "+finalOutputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
		}
		return finalOutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SLDCRTN003", "CCSSOLeadCreation DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
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
			.append("<REF_NO>" + refNo + "</REF_NO>")
			.append("<senderID>"+senderID+"</senderID>").append("\n")
			.append("<serviceAction>Modify</serviceAction>").append("\n")
			.append("<RequestDateTime>"+new Date().toString()+"</RequestDateTime>")
			.append("<IntegrationId>" + wiName  + "</IntegrationId>").append("\n")
			.append("<winame>" + wiName + "</winame>")
			.append("<LeadStatus>" + leadStatus  + "</LeadStatus>").append("\n")
			.append("<ProductCode>" + productCode  + "</ProductCode>").append("\n")
			.append("<ProductGroup>" + productGroup  + "</ProductGroup>").append("\n")
			.append("<CustomerId>" + primaryCid  + "</CustomerId>").append("\n")
			.append("<BusinessSegment>6</BusinessSegment>").append("\n")
			.append("<SourceChannel>" + sourcingchannel  + "</SourceChannel>").append("\n")
			.append("<EmailKeyContactPerson>"+ emailKeyContactPerson +"</EmailKeyContactPerson>").append("\n")
			.append("<FirstNKeyContactPerson>" + firstKeyContactPerson  + "</FirstNKeyContactPerson>").append("\n")
			.append("<MobKeyContactPerson>" + mobKeyContactPerson  + "</MobKeyContactPerson>").append("\n");
			if(journeyType.equalsIgnoreCase("MIB")){
				inputXML.append(("<CustomerId>" + customerId + "</CustomerId>")).append("\n");
			}

			inputXML.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "CCSSOLeadCreation inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
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
				status = xp.getValueOf("LeadRefNo", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0){
					callStatus = "Y";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SLDCRTN090", "LeadCreation Successfully Executed", sessionId);
				} else {
					callStatus = "F";
					//Manually creating Lead  in service failure case
					finalOutputXml= "<returnCode>1</returnCode><errorDescription>Lead Creation Failed</errorDescription>";
					String valueList = "'"+ wiName +"',1,'P',SYSDATE,'"+productCode+"','"+ firstKeyContactPerson +"',"
							+ "'"+ emailKeyContactPerson +"','"+ mobKeyContactPerson +"','CREATE','"+passportNumber+"','"+sourcingchannel+"'";
					APCallCreateXML.APInsert("USR_0_CBG_LEAD_OPS", "WI_NAME,OPERATION_TYPE,STATUS,ENTRY_DATETIME,PRODUCT_CODE,"
							+ "CUSTOMER_NICK_NAME,CUSTOMER_EMAIL,CUSTOMER_MOBILE_NO,REASON,PASSPORT_NUMBER,SOURCING_CHANNEL", valueList, sessionId);
					
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SLDCRTN101", "LeadCreation Failed", sessionId);
				}
			} else {
				callStatus = "F";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SLDCRTN102", "LeadCreation Failed", sessionId);
				finalOutputXml= "<returnCode>1</returnCode><errorDescription>No Go Found</errorDescription>";
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {

			if(!prevStageNoGo){
				String outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "LEAD_REF_NO", " "+ status +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Lead Creation : "+ mainCode);
				}
			}
			String valList = "'"+ wiName +"',"+ stageId +", 'SuppLeadCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppLeadCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				//executeDependencyCall();
				 valList = "'"+ wiName +"',"+ stageId +", 'SuppEidaLocalDedupe', 'N',SYSTIMESTAMP,'0', 'Call Execute Through X event', 'Call AutoRepaired', '0', 'Call - X Event'";
				APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
						+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
				
				//Added by Shivanshu
				String msgValues = status;
				String prefLanguage = "EN";
				DSCOPUtils.getInstance().sendEmailSMS(sessionId,defaultMap.get("NOTIFY_TYPE"), primaryCid, accountNumber, transactionType, 
				defaultMap.get("SUBMISSION_NOTIFY_ID"), msgValues, mobKeyContactPerson,	
				defaultMap.get("SUBMISSION_NOTIFY_ID"), emailKeyContactPerson, 
				defaultMap.get("FROM_EMAIL_ID"),"", "P", wiName, prefLanguage);

			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppLeadCreation', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SLDCRTN100", e, sessionId);
		}
	}


	public String fetchLeadCreationData() throws Exception {
		return APCallCreateXML.APSelect("SELECT SUPP_CARDHOLDER_FULL_NAME,CUSTOMER_EMAIL, CUSTOMER_MOBILE_NO, PASSPORT_NO, JOURNEY_TYPE, CUSTOMER_ID, NATIONALITY, PRIMARY_CID,ASSOCIATE_CARD,PRIMARY_CARD_NO  FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}

	private void handleLeadCreationData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				firstKeyContactPerson = xp.getValueOf("SUPP_CARDHOLDER_FULL_NAME");
				emailKeyContactPerson = xp.getValueOf("CUSTOMER_EMAIL"); 
				mobKeyContactPerson = xp.getValueOf("CUSTOMER_MOBILE_NO");
				passportNumber = xp.getValueOf("PASSPORT_NUMBER");
				journeyType = xp.getValueOf("JOURNEY_TYPE");
				customerId = xp.getValueOf("CUSTOMER_ID");
				nationalityCode =  xp.getValueOf("NATIONALITY");
				primaryCid = xp.getValueOf("PRIMARY_CID");
				associateCard = xp.getValueOf("ASSOCIATE_CARD");
				cardNumber = xp.getValueOf("PRIMARY_CARD_NO");
			}
//			productCode = "Credit Card Digital Journey";
			senderID = defaultMap.get("SENDER_ID");
			leadStatus = "Open";
//			productGroup = "Credit Cards";
			sourcingchannel = "MIB";
		    if(associateCard.equalsIgnoreCase("Debit")) {
					productCode = "Supplementary DC MIB Journey";
					productGroup = "CASA";
					transactionType = "AC";
					
					String outputACC = APCallCreateXML.APSelect("SELECT LINKED_ACCOUNT FROM BPM_DSCOP_DEBITCARD_DETAILS WHERE CARD_NUMBER = '"+cardNumber+"' and WI_NAME = N'" + wiName + "'");
					XMLParser xpAcc = new XMLParser(outputACC);
					 mainCode = Integer.parseInt(xpAcc.getValueOf("MainCode"));
					if(mainCode == 0){
						if(Integer.parseInt(xpAcc.getValueOf("TotalRetrieved")) > 0){
							accountNumber = xpAcc.getValueOf("LINKED_ACCOUNT");
						}
					}
					
			}else {
					productCode = "Supplementary CC MIB Journey";
					productGroup = "Credit Cards";
					accountNumber = cardNumber;
					transactionType = "C";
			}
			
		}

	}

}
