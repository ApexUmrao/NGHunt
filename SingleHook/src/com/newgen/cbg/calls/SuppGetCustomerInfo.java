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

public class SuppGetCustomerInfo implements ICallExecutor, Callable<String>{
	private String wiName;
	private int stageId;
	private String sessionId;
	private String eidaNum;
	private boolean isNTB = true;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean updateCheckFlag = true;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "GetCustomerInfo";
	private boolean skipCall = false;
	private String custSegment;
	private String journeyType;
	private String outputXML;

	public SuppGetCustomerInfo(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity) {
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo.booleanValue();
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		try
		{
			outputXML = fetchGetCustomerInfo();
			handleGetCustomerInfo(outputXML);
			senderID = defaultMap.get("SENDER_ID");
		} catch (Exception e) {
			DSCOPLogMe.logMe(2, e);
			DSCOPDBLogMe.logMe(2, wiName, auditCallName, "GCI100", e, sessionId);
		}
	}

	public String call() throws Exception
	{
		String inputXml = createInputXML();
		String outputXml = "<returnCode>0</returnCode>";
		try {
			if (!(prevStageNoGo)) {
				if (updateCheckFlag) {
					if(!skipCall){ // COP 4371
						outputXml = ExecuteXML.executeWebServiceSocket(inputXml);
						DSCOPLogMe.logMe(1, "GetCustomerInfo outputXml ===> " + outputXml);
						if ((outputXml == null) || (outputXml.equalsIgnoreCase(""))) {
							outputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
						}
						responseHandler(outputXml, inputXml);
						DSCOPDBLogMe.logMe(3, wiName, auditCallName, "GCI004", "EIDAMQ output:" + outputXml, sessionId);
						if (returnCode == 1) {
							outputXml = "<returnCode>0</returnCode>";
						} 
					}   else {
						callStatus = "X";
						updateCallOutput(inputXml);
					}	
				} else {
					callStatus = "X";
					updateCallOutput(inputXml);
					executeDependencyCall();
					DSCOPDBLogMe.logMe(3, wiName, auditCallName, "GCI005", "GetCustomerInfo call Skipped", sessionId);
					
				}
			} else {
				callStatus = "F";
				updateCallOutput(inputXml);
			}
		}
		catch (Exception e) {
			DSCOPLogMe.logMe(2, e);
			DSCOPDBLogMe.logMe(2, wiName, auditCallName, "GCI100", e, sessionId);
		}
		return outputXml;
	}

	public String executeCall(HashMap<String, String> input) throws Exception
	{
		return call();
	}

	public void responseHandler(String outputXML, String inputXml) throws Exception
	{
		try
		{
			if (!(prevStageNoGo)) {
				XMLParser xp = new XMLParser(outputXML);
				returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
				errorDescription = xp.getValueOf("errorDescription", "", true);
				errorDetail = xp.getValueOf("errorDetail", "", true);
				status = xp.getValueOf("Status", "", true);
				reason = xp.getValueOf("Reason", "", true);
				callStatus = "Y";
				if (returnCode == 0) {
					String customerID = xp.getValueOf("customerId", "", true);
					isNTB = customerID.equalsIgnoreCase("");
					if (isNTB) {
						APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_TYPE,IS_NTB_CUST", "'NTB','Y'", " WI_NAME = N'" + wiName + "'", sessionId);
						DSCOPDBLogMe.logMe(1, wiName, auditCallName, "GCI090", "GetCustomerInfo Successfully Executed - NTB", sessionId);
					}
					else {
						isNTB = false;
						String customerId = xp.getValueOf("customerId");
						String customerNamePrefix = xp.getValueOf("customerNamePrefix");
						String customerFullName = xp.getValueOf("customerFullName");
						String customerShortName = xp.getValueOf("customerShortName");
						String customerSegment = xp.getValueOf("customerSegment");
						String customerType = xp.getValueOf("customerType");
						String customerNationality = xp.getValueOf("customerNationality");
						String customerMotherName = xp.getValueOf("customerMotherName").equalsIgnoreCase("null")?"":xp.getValueOf("customerMotherName");	
						if (customerNamePrefix.equalsIgnoreCase("") || customerNamePrefix.equalsIgnoreCase("null")) {
							customerNamePrefix="Mr";
						}
						APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_ID,PREFIX,CUSTOMER_FULL_NAME,CUSTOMER_SEGMENT,BANKING_TYPE,CUSTOMER_MOTHER_MAIDEN_NAME,IS_NTB_CUST,NATIONALITY,EIDA_NATIONALITY,CUSTOMER_TYPE", 
								"'" + customerId + "','" + customerNamePrefix + "','" + customerFullName + "','" + customerSegment + "','" + customerType + "','" + customerMotherName + "','N','"+customerNationality+"','"+customerNationality+"','ETB'", " WI_NAME = N'" + wiName + "'", sessionId);

						String outputXML2 = APCallCreateXML.APSelect("SELECT CUST_SEGMENT FROM BPM_COP_SEGMENT_MASTER "
								+ "WHERE UNIQUE_ID = N'" + customerSegment + "'");
						XMLParser xp2 = new XMLParser(outputXML2);
						int mainCode2 = Integer.parseInt(xp2.getValueOf("MainCode"));
						DSCOPDBLogMe.logMe(3, wiName, auditCallName, "GCI001", "GetCustomerInfo ResponseHandler", sessionId);
						if (mainCode2 == 0) {
							DSCOPLogMe.logMe(1, "GetCustomerInfo ResponseHandler TotalRetrieved: " + Integer.parseInt(xp2.getValueOf("TotalRetrieved")));
							if (Integer.parseInt(xp2.getValueOf("TotalRetrieved")) > 0) {
								custSegment = xp2.getValueOf("CUST_SEGMENT");
							}
						}
						if("MIB".equalsIgnoreCase(journeyType)){
							APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_SEGMENT", "'"+ custSegment + "'", " WI_NAME = N'" + wiName + "'", sessionId);
						}
						DSCOPDBLogMe.logMe(1, wiName, auditCallName, "GCI090", "GetCustomerInfo Successfully Executed - CID: " + customerId, sessionId);
					}
				} else {
					if (errorDetail.contains("101821")) {

						APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_TYPE,IS_NTB_CUST", "'NTB','Y'", " WI_NAME = N'" + wiName + "'", sessionId);
						errorDescription = "EIDA not available - NTB";
					}
					else if (errorDetail.contains("101344")) {
						errorDescription = "EIDA DEDUPE FOUND!! Multiple Customer IDs mapped to same EIDA";
						APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_TYPE,CUSTOMER_DEDUPE", "'ETB','Y'", " WI_NAME = N'" + wiName + "'", sessionId);
						String values = "'" + wiName + "','EIDA-FCUBS','" + wiName + "','MIB','','','" + eidaNum + "','','','','',''";
						APCallCreateXML.APInsert("BPM_COP_CUSTOMER_DEDUPE", "WI_NAME, SOURCE, REFERENCE_NUMBER, PRODUCT_APPLIED,CUSTID,FULL_NAME, EIDANUM, PASSPORTNUM, DOB, NATIONALITY, EMAIL, MOBILE", 
								values, sessionId);
					}
					else {
						callStatus = "F";
						errorDescription = "GetCustomerInfo Failed";
					}
					DSCOPDBLogMe.logMe(1, wiName, auditCallName, "GCI101", errorDescription, sessionId);
				}
			} else {
				callStatus = "F";
				DSCOPDBLogMe.logMe(1, wiName, auditCallName, "GCI101", "GetCustomerInfo Failed", sessionId);
			}
			updateCallOutput(inputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(2, e);
			DSCOPDBLogMe.logMe(2, wiName, auditCallName, "GCI100", e, sessionId);
		}
	}

	public String createInputXML() throws Exception
	{
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
			DSCOPLogMe.logMe(1, "GetCustomerInfo inputXML ===> " + inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(2, e);
			DSCOPDBLogMe.logMe(2, wiName, auditCallName, "GCI100", e, sessionId);
		}
		return inputXML.toString();
	}

	public void executeDependencyCall() throws Exception
	{
		try {
			DSCOPDBLogMe.logMe(3, wiName, auditCallName, "GCI003", "EIDA DependencyCall:" + callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, Boolean.valueOf(prevStageNoGo));
		} catch (Exception e) {
			DSCOPLogMe.logMe(2, e);
			DSCOPDBLogMe.logMe(2, wiName, auditCallName, "GCI100", e, sessionId);
		}
	}

	public void updateCallOutput(String inputXml)
			throws Exception
	{
		String valList2;
		String valList = "'" + wiName + "'," + stageId + ", 'SuppGetCustomerInfo', '" + callStatus + "',SYSTIMESTAMP," + returnCode + ", '" + errorDescription + "', '" + 
				errorDetail + "', '" + status + "', '" + reason + "'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", 
				valList, sessionId);

		if (callStatus.equals("Y")) {
			valList2 = "'" + wiName + "'," + stageId + ", 'SuppGetCustomerInfo', '" + callStatus + "',SYSTIMESTAMP," + returnCode + ", '" + errorDescription + "', '" + 
					errorDetail + "', '" + status + "', '" + reason + "', 0, " + refNo + ", '" + inputXml + "', 0";
			APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", 
					valList2, sessionId);
			executeDependencyCall();
		}
		else {
			valList2 = "'" + wiName + "'," + stageId + ", 'SuppGetCustomerInfo', '" + callStatus + "',SYSTIMESTAMP," + returnCode + ", '" + errorDescription + "', '" + 
					errorDetail + "', '" + status + "', '" + reason + "', 0, " + refNo + ", '" + inputXml + "', 1";
			APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,  RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", 
					valList2, sessionId);
		}
		DSCOPDBLogMe.logMe(3, wiName, auditCallName, "GCI003", "isNTB : " + isNTB, sessionId);
   //   commented by krishna
	/*	if (isNTB) {
			executeDependencyCall();
		}*/
	}

	public String fetchGetCustomerInfo() throws Exception {
		return APCallCreateXML.APSelect("SELECT EIDA_NUMBER FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}

	private void handleGetCustomerInfo(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			eidaNum = xp.getValueOf("EIDA_NUMBER");
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "GCI100", eidaNum, sessionId);
			if(eidaNum==null || eidaNum.equals("") ){
				updateCheckFlag = false;
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: updateCheckFlag:"+updateCheckFlag);
			}
		}


	}


}
