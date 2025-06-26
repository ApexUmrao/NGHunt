package com.newgen.cbg.calls;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

public class SuppFatcaDetails  implements ICallExecutor, Callable<String> {

	private String cid;
	private String fatcaCustClassification;
	private String fatcaCustClassificationDate;
	private String sessionId;
	private String wiName;
	private int stageId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private String finalOutputXml;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SVFATCADET";
	private String isNTBCust;
	private String skipModify;
	private boolean isCallSkip=false;

	public SuppFatcaDetails( Map<String, String> defaultMap,String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.sessionId = sessionId;
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_ID,IS_NTB_CUST,ASSOCIATE_CARD,SKIP_MODIFY FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SVFATCADET001", "SuppFatcaDetails Started", sessionId);
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppFatcaDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					cid = xp.getValueOf("CUSTOMER_ID");
					isNTBCust = xp.getValueOf("IS_NTB_CUST");
					skipModify=defaultMap.get("SKIP_MODIFY");
			        if (!(isNTBCust.equalsIgnoreCase("N") && skipModify.equalsIgnoreCase("Y"))) {
						isCallSkip = true;
					}
				}
			}
			senderID = defaultMap.get("SENDER_ID");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SVFATCADET100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		finalOutputXml = "<returnCode>0</returnCode>";
		try {
			if(!isCallSkip){
			if(!prevStageNoGo){
				finalOutputXml = ExecuteXML.executeWebServiceSocket(inputXml);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppFatcaDetails OutputXml: "+ finalOutputXml);
				if(finalOutputXml==null ||finalOutputXml.equalsIgnoreCase("")){
					finalOutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(finalOutputXml, inputXml);
			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SVFATCADET002", "SuppFatcaDetails Fail: ", sessionId);
			}
			}else {
				callStatus = "X";
				updateCallOutput(inputXml);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SVFATCADET003", "SuppFatcaDetails Skip: ", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SVFATCADET100", e, sessionId);
		}
		return finalOutputXml;
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
				status = xp.getValueOf("Status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				if(returnCode == 0){
					callStatus = "Y";
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SVFATCADET090", "SuppFatcaDetails Successfully Executed", sessionId);
				} else {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"errorDescription :"+errorDescription); 
					if(errorDescription.contains("Record already exists")){
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SVFATCADET101", "ESuppFatcaDetails Successfully Executed because Record already exists", sessionId);
						callStatus = "Y";
						finalOutputXml = "<returnCode>0</returnCode>";
					}
					else{
						callStatus = "N";
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "SVFATCADET102", "SuppFatcaDetails Failed", sessionId);
					}
				}
			} else {
				callStatus = "N";
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SVFATCADET100", e, sessionId);
		}			
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder(); 
		fatcaCustClassification = "CID_ONLY";			
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        fatcaCustClassificationDate = now.format(formatter);
        DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"fatcaCustClassification  :"+fatcaCustClassification+" And fatcaCustClassificationDate :"+fatcaCustClassificationDate);
		try {
			refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<WINAME>" + wiName + "</WINAME>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<DSCOPCallType>" + "SaveFATCADetails" + "</DSCOPCallType>").append("\n") 
			.append("<REF_NO>" + refNo + "</REF_NO>")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<customerId>" + cid + "</customerId>").append("\n")
			.append("<customerInfoType>FATCADetails</customerInfoType>").append("\n")
			.append("<maintenanceOption>ADD</maintenanceOption>").append("\n")	
			.append("<USpassportholder></USpassportholder>").append("\n")
			.append("<USTaxLiable></USTaxLiable>").append("\n")
			.append("<countryOfBirth></countryOfBirth>").append("\n")
			.append("<POAHolder></POAHolder>").append("\n")
			.append("<USIndiaciaFound></USIndiaciaFound>").append("\n")
			.append("<documentCollected></documentCollected>").append("\n")
			.append("<TINorSSN></TINorSSN>").append("\n")
			.append("<customerFATCAClsfctn>" + fatcaCustClassification + "</customerFATCAClsfctn>").append("\n")
			.append("<customerFATCAClsfctnDate>" + fatcaCustClassificationDate + "</customerFATCAClsfctnDate>").append("\n")
			.append("<W8_Sign_Date></W8_Sign_Date>").append("\n")
			.append("<SItoUSBeneficiary></SItoUSBeneficiary>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppFatcaDetails inputXML ===> "+inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SVFATCADET100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			String outputXMLTemp = "";
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SVFATCADET003", "SuppFatcaDetails DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
				finalOutputXml = outputXMLTemp;
			}else{
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "SVFATCADET003", "SuppFatcaDetails DependencyCall Not Found ", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SVFATCADET100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {

			APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppFatcaDetails' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);

			String valList = "'"+ wiName +"',"+ stageId +", 'SuppFatcaDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppFatcaDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
				executeDependencyCall();
			}else if(callStatus.equals("X")){
				executeDependencyCall();
			}
			else {
				String valList2 = "'"+ wiName +"',"+ stageId +", 'SuppFatcaDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "SVFATCADET100", e, sessionId);
		}
	}


}
