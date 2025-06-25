package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import com.newgen.ao.laps.cache.CallEntity;

public class FetchAccountDetails implements ICallExecutor, Callable<String>{
	
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
	private String auditCallName = "FACCD";
	 HashMap<String, String> attribMap = new HashMap<String, String>();

	public FetchAccountDetails(Map<String, String> attributeMap, String sessionId, String stageId, String wiName, 
			Boolean prevStageNoGo, CallEntity callEntity){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside FetchAccountDetails >> ");
		try {
			this.sessionId =sessionId;
			this.wiName = wiName;
			this.attributeMap = attributeMap;
			this.mode = attributeMap.get("mode");
			this.stageId = Integer.parseInt(stageId);
			outputXML = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY,ACCOUNT_NUMBER,INF_CHARGE_ACC_NUM "
					+ "FROM EXT_TFO WHERE WI_NAME = N'" + wiName + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, wiName, auditCallName, "FTD001", "Started", sessionId);
			if(mainCode == 0){
				requestCategory = xp.getValueOf("REQUEST_CATEGORY");
				if("GRNT".equalsIgnoreCase(requestCategory) || "ELC".equalsIgnoreCase(requestCategory) 
						|| "ILC".equalsIgnoreCase(requestCategory) || "SG".equalsIgnoreCase(requestCategory)){
					accNo = xp.getValueOf("ACCOUNT_NUMBER");
				} else if("IFS".equalsIgnoreCase(requestCategory) || "IFP".equalsIgnoreCase(requestCategory)
						|| "TL".equalsIgnoreCase(requestCategory) || "EC".equalsIgnoreCase(requestCategory)
						|| "IC".equalsIgnoreCase(requestCategory) || "DBA".equalsIgnoreCase(requestCategory)
						|| "ELCB".equalsIgnoreCase(requestCategory) || "ILCB".equalsIgnoreCase(requestCategory)
						){
					accNo = xp.getValueOf("INF_CHARGE_ACC_NUM");
				} else {
					callStatus = "X";
					returnCode = 0;
				}
				if(accNo == null || accNo.isEmpty()){
					callStatus = "X";
					returnCode = 0;
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "FetchAccountDetails exception: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

	@Override
	public String call() throws Exception {
		if(accNo != null && !accNo.isEmpty()){
			String sInput = createAcctDetailsInputXML() ;
			String sOutput = "";
			try {
				sOutput =  ExecuteXML.executeXML(sInput);
				XMLParser xp = new XMLParser(sOutput);
				if(!sOutput.isEmpty()){
					returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
					errorDescription = xp.getValueOf("errorDescription"); 
					errorDetail = xp.getValueOf("errorDetail"); 
					if(returnCode == 0){
						//loadLinkedCustomersDataGrid(sOutput);
						attribMap.put("", xp.getValueOf("acctBranchCode"));
						attribMap.put("", xp.getValueOf("acctCurrency"));
						attribMap.put("", xp.getValueOf("acctBranchCode"));
						attribMap.put("", xp.getValueOf("acctBranchCode"));
						attribMap.put("", xp.getValueOf("acctBranchCode"));
						attribMap.put("", xp.getValueOf("acctBranchCode"));
					} else {
						callStatus = "N";
						errorDescription = "Fetch Account Details Failed";
					}
				}
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in FetchAccountDetails call(): ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
		String outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
	}
	
	private String createAcctDetailsInputXML(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createAcctDetailsInputXML accNo  "+accNo);		
		StringBuffer inputXml = new StringBuffer();
		try {
			if (null != accNo && !"0".equalsIgnoreCase(accNo)) {
				inputXml.append("<APWebService_Input>")
				.append("<Option>WebService</Option>")
				.append("<Calltype>TFO_AcctDetailsICCS</Calltype>")
				.append("<sysRefNumber>" + LapsUtils.getInstance().generateSysRefNumber() +"</sysRefNumber>")
				.append("<senderID>WMS</senderID>")
				.append("<WiName>" + wiName + "</WiName>")
				.append("<ServiceName>InqAccountDetailsICCS</ServiceName>")
				.append("<InqAccountDetailsICCSReq>")
				.append("<CreditAccountInput>")
				.append("<acctNumber>" + accNo + "</acctNumber>")
				.append("</CreditAccountInput>")
				.append("</InqAccountDetailsICCSReq>");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"createAcctDetailsInputXML Xml ===>  " + inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in createAcctDetailsInputXML: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

		return inputXml.toString();
	}
	
	/* private void loadLinkedCustomersDataGrid(String data){
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"in loadLinkedCustomersDataGrid ");
			StringBuffer gridData = new StringBuffer();
			String singleCustomerData = "";
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(data);
			XMLParser innerXmlParser = new XMLParser();		
			for(int i=0; i<xmlDataParser.getNoOfFields("linkedCustomer"); i++){
				singleCustomerData = xmlDataParser.getNextValueOf("linkedCustomer");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"singleCustomerData "+singleCustomerData);
				innerXmlParser.setInputXML(singleCustomerData);
			//	jsonObject.put("WI_NAME",sWorkitemID);
				APCallCreateXML.APInsert("", "", strValues, singleCustomerData);
				jsonObject.put("Signatory ID",innerXmlParser.getValueOf("custID"));
				jsonObject.put("Signatory Name",innerXmlParser.getValueOf("custName"));
				jsonObject.put("Customer Relation",innerXmlParser.getValueOf("custRelation"));
			}
			formObject.clearTable(QVAR_LINKED_CUST);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"gridData "+jsonArrayCustomer.size());

			formObject.addDataToGrid(QVAR_LINKED_CUST, jsonArrayCustomer);
			formObject.setValue("SIGACC_ACC_TOTAL_BAL", xmlDataParser.getValueOf("netBalAvailable"));
			formObject.setValue("SIGACC_ACC_CURR_BAL", xmlDataParser.getValueOf("acctBalAvailable"));
			formObject.setValue("SIGACC_DOMCL_BRN_CODE", xmlDataParser.getValueOf("acctBranchCode"));
			formObject.setValue("SIGACC_ACC_CURRENCY", xmlDataParser.getValueOf("acctCurrency"));
			formObject.setValue(SIGACC_ACC_NO, xmlDataParser.getValueOf("acctNumber"));			
			try {
				amountFormat("SIGACC_ACC_TOTAL_BAL", xmlDataParser.getValueOf("acctCurrency"));
				amountFormat("SIGACC_ACC_CURR_BAL", xmlDataParser.getValueOf("acctCurrency"));
			} catch (Exception e) {
				log.error("Exception: ",e);
			}			 
			String mandate = xmlDataParser.getValueOf("acctModeofOps1");
			mandate = mandate.replaceAll("&", "~amp~");
			mandate = mandate.replaceAll("%", "~perc~");
			mandate = mandate.replaceAll("\\?", "~ques~");
			mandate = mandate.replaceAll("#", "~hash~");
			formObject.setValue("SIGACC_ACC_MNDT", mandate.replaceAll("(\r\n|\n\r|\n)", "<br />"));
			enableFieldOnDemand(VIEW_ACCOUNT_MANDATE);
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
	} */

	@Override
	public String createInputXML() throws Exception {
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		
	}
}
