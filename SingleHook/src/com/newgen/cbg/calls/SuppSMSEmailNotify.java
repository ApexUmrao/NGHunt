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
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class SuppSMSEmailNotify implements ICallExecutor, Callable<String> {

	private String WI_NAME;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String columnName;
	private String strValues = "";
	private String NATIONALITY;
	private String FATCA_BIRTH_COUNTRY;
	private String CUSTOMERID;
	private String ACCTNUMBER;
	private String LEADREFNO;
	private String MOBILENO;
	private String EIDA_NATIONALITY;
	private String EMPLOYMENT_TYPE;
	private String FINAL_RISK;
	private String ACCOUNT_NUMBER;
	private String CREDIT_CARD_NUMBER;
	private String PREFERRED_LANGUAGE;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SMSEMLNTFY";

	public SuppSMSEmailNotify(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {
		this.sessionId=sessionId;
		this.WI_NAME = WI_NAME;
		this.stageId = Integer.parseInt(stageId);
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT NATIONALITY,FATCA_BIRTH_COUNTRY,CUSTOMER_ID,"
					+ "LEAD_REF_NO,CUSTOMER_MOBILE_NO,CREDIT_CARD_NUMBER FROM EXT_DSCOP WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SMSEMLNTFY001", "SuppSMSEmailNotify Started", sessionId);
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SuppSMSEmailNotify TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					NATIONALITY = xp.getValueOf("NATIONALITY");
					FATCA_BIRTH_COUNTRY = xp.getValueOf("FATCA_BIRTH_COUNTRY");
					CUSTOMERID = xp.getValueOf("CUSTOMER_ID");
					CREDIT_CARD_NUMBER = xp.getValueOf("CREDIT_CARD_NUMBER");
					LEADREFNO = xp.getValueOf("LEAD_REF_NO");
					MOBILENO = xp.getValueOf("CUSTOMER_MOBILE_NO");
//					EIDA_NATIONALITY = xp.getValueOf("EIDA_NATIONALITY");
//					EMPLOYMENT_TYPE = xp.getValueOf("EMPLOYMENT_TYPE");
//					FINAL_RISK = xp.getValueOf("BUSI_RISK_FLG");
//					ACCOUNT_NUMBER = xp.getValueOf("ACCOUNT_NUMBER");
//					PREFERRED_LANGUAGE = xp.getValueOf("PREFERRED_LANGUAGE");
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SMSEMLNTFY100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String outputXml = "";
		try {
			columnName = "CUSTOMERID,ACCTNUMBER,TRANSACTIONTYPE,SENDASCHANNEL,SMSTEMPLATEID,SMSTEXTVALUES,MOBILENUMBER,EMAILTEMPLATEID,EMAILTEXTVALUES,"
					+ "EMAILADDRESS,FLEXIFILLER1,FLEXIFILLER2,FLEXIFILLER3,FLEXIFILLER4,FLEXIFILLER5,STATUS,WI_NAME,ENTRY_DATE_TIME,PREFFEREDLANGUAGE";
			//strValues = "'','','AC',"+"'S',"+"'','','','','','','','','','','','','"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SMS_STAGEID:"+stageId);
//			if(stageId==1){
//				if(PASSPORT_NATIONALITY.equals("US") || FATCA_BIRTH_COUNTRY.equals("US")){
//					strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1937',"+"'"+ LEADREFNO +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//							"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//				}
//			}
//			else if(stageId==2){
//				if(FATCA_BIRTH_COUNTRY.equals("US")){
//					strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1938',"+"'"+ ACCOUNT_NUMBER +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//							"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//				}
//			}
//			else if(stageId==3){
//				if(EIDA_NATIONALITY.equals("US")){
//					strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1939',"+"'"+ CREDIT_CARD_NUMBER +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//							"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//				}
//			}
//			else if(stageId==4){
//				if(EMPLOYMENT_TYPE.equals("SELF EMPLOYED") && FINAL_RISK.equals("Y") ){
//					strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1937',"+"'"+ LEADREFNO +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//							"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//				}
//			}
//			else if(stageId==5){
//				if(ACCOUNT_NUMBER.equals("")){
//					strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1937',"+"'"+ LEADREFNO +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//							"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//				}
//			}
//			else if(stageId==7){
//				if(CREDIT_CARD_NUMBER.equals("SELF EMPLOYED")){
//					strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1937',"+"'"+ LEADREFNO +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//							"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//				}
//			}
//			else if(stageId==51 || stageId==9 || stageId==-1){
//				strValues = "'"+ CUSTOMERID +"',"+"'"+ ACCTNUMBER +"',"+"'AC',"+"'S',"+"'1943',"+"'"+ LEADREFNO +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
//						"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'"+PREFERRED_LANGUAGE+"'";
//			}
			strValues = "'"+ CUSTOMERID +"','',"+"'AC',"+"'S',"+"'1943',"+"'"+ LEADREFNO +"',"+"'"+ MOBILENO +"',"+"'',"+"'',"+"'',"+"'2627/2628',"+
			"'',"+"'',"+"'',"+"'',"+ "'P',"+"'"+ WI_NAME +"',"+"SYSTIMESTAMP,'EN'";
			
			outputXml = "";
			String inputXml = createInputXML();
			if(!strValues.equals("")){
				if(!prevStageNoGo){
					outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SuppSMSEmailNotify outputXml ===> " + outputXml);
					if(outputXml==null ||outputXml.equalsIgnoreCase("")){
						outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				}			
			}
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SMSEMLNTFY002", "SuppSMSEmailNotify outputXml: "+outputXml, sessionId);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SMSEMLNTFY100", e, sessionId);
		} 
		//		else {
		//			errorDescription = "SMS Condition Not Met";
		//			errorDetail = "SMS Condition Not Met";
		//		}

		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SMSEMLNTFY003", "SuppSMSEmailNotify DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SMSEMLNTFY100", e, sessionId);
		}
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder sInputXML = new StringBuilder(); 
		try {
			sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APInsert_Input>").append("\n")
			.append("<Option>APInsert</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<TableName>"+ "USR_0_DSCOP_NOTIFY" + "</TableName>").append("\n")
			.append("<ColName>" + columnName + "</ColName>").append("\n")
			.append( "<Values>" + strValues + "</Values>").append("\n")
			.append("</APInsert_Input>");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SMSEMLNTFY100", e, sessionId);
		}
		return sInputXML.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("MainCode", "1", true));
			if(returnCode == 0) {
				callStatus = "S";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, auditCallName, "SMSEMLNTFY090", "SuppSMSEmailNotify Successfully Executed", sessionId);
			} else {
				callStatus = "F";
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, auditCallName, "SMSEMLNTFY101", "SuppSMSEmailNotify Failed", sessionId);

			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SMSEMLNTFY100", e, sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ WI_NAME +"',"+ stageId +", 'SuppSMSEmailNotify', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("S")){
				String valList2 = "'"+ WI_NAME +"',"+ stageId +", 'SuppSMSEmailNotify', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, '', '"+ inputXml+"', 0";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
						+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);			
			}
			else {
				String valList2 = "'"+ WI_NAME +"',"+ stageId +", 'SuppSMSEmailNotify', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"', 0, '', '"+ inputXml+"', 1";
				APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
						+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SMSEMLNTFY100", e, sessionId);
		}
	}
}
