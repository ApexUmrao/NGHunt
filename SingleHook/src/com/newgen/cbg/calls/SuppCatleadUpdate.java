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

public class SuppCatleadUpdate  implements ICallExecutor, Callable<String>  {

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
	private String refNo;
	private String productCode;
	private String productGroup;
	private String accessPoint="";
	private String BusinessSegment="";
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "LDUPDT";
	private String emailKeyContactPerson;
	private String firstKeyContactPerson;
	private String mobKeyContactPerson;
	private String leadStatus;
	private String sourcingchannel;
	private String customerId;
	private String associateCard;
	private String leadRefNo;
	private String stageName;
	public SuppCatleadUpdate(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId = sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		String outputXML;
		try {
			handleLeadCatsUpdateData(fetchLeadCatsUpdateData());
			} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML(); 
		String outputXml = "";
		try {
			if (stageName.equalsIgnoreCase("CATS") && !prevStageNoGo) {
				outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
				responseHandler(outputXml, inputXml);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT002", "SuppCatleadUpdate outputXml: "+outputXml, sessionId);
			} else {
				callStatus="X";
				updateCallOutput(inputXml);
				executeDependencyCall();
			}
		}catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT003", "SuppCatleadUpdate DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
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
			.append("<SourceChannel>" + sourcingchannel  + "</SourceChannel>").append("\n")
			.append("<BusinessSegment>"+BusinessSegment+"</BusinessSegment>").append("\n")
			.append("<EmailKeyContactPerson>"+ emailKeyContactPerson +"</EmailKeyContactPerson>").append("\n")
			.append("<FirstNKeyContactPerson>" + firstKeyContactPerson  + "</FirstNKeyContactPerson>").append("\n")
			.append("<MobKeyContactPerson>" + mobKeyContactPerson  + "</MobKeyContactPerson>").append("\n")
			.append("<AccessPoint>" + accessPoint  + "</AccessPoint>").append("\n")		
			.append("<CustomerId>" + customerId + "</CustomerId>").append("\n")
			.append("<ADCBAssignFlag>Y</ADCBAssignFlag>").append("\n")
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
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT090", "SuppCatleadUpdate Successfully Executed", sessionId);
				} else {
					callStatus = "F";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "LDUPDT101", "SuppCatleadUpdate Failed", sessionId);
				}
			} else {
				callStatus = "N";
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
			String valList = "'"+ wiName +"',"+ stageId +", 'LeadUpdate', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			String outputXML = APCallCreateXML.APInsert("USR_0_CBG_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'LeadUpdate', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				outputXML = APCallCreateXML.APInsert("USR_0_CBG_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);			
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'LeadUpdate', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				outputXML = APCallCreateXML.APInsert("USR_0_CBG_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "LDUPDT100", e, sessionId);
		}
	}
	
	   public String fetchLeadCatsUpdateData() throws Exception {
		return APCallCreateXML.APSelect("SELECT SUPP_CARDHOLDER_FULL_NAME,CUSTOMER_EMAIL, CUSTOMER_MOBILE_NO, PASSPORT_NO, JOURNEY_TYPE, CUSTOMER_ID, NATIONALITY, PRIMARY_CID,ASSOCIATE_CARD,LEAD_REF_NO FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}
	   private void handleLeadCatsUpdateData(String outputXML) throws Exception {
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					firstKeyContactPerson = xp.getValueOf("SUPP_CARDHOLDER_FULL_NAME");
					emailKeyContactPerson = xp.getValueOf("CUSTOMER_EMAIL"); 
					mobKeyContactPerson = xp.getValueOf("CUSTOMER_MOBILE_NO");
					customerId = xp.getValueOf("CUSTOMER_ID");
					associateCard = xp.getValueOf("ASSOCIATE_CARD");
					leadRefNo = xp.getValueOf("LEAD_REF_NO");
					stageName = xp.getValueOf("STAGE_NAME");
					if(stageName.equalsIgnoreCase("CATS")){
						leadStatus="Pending";
					}
					
				}
				senderID = defaultMap.get("SENDER_ID");
				sourcingchannel = "MIB";
			    if(associateCard.equalsIgnoreCase("Debit")) {
						productCode = "Supplementary DC MIB Journey";
						productGroup = "CASA";
				}else {
						productCode = "Supplementary CC MIB Journey";
						productGroup = "Credit Cards";
				}
				
			}

		}
}
