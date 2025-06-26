package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class PushMessage  implements ICallExecutor, Callable<String>{
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	private String refNo;
	private String callStatus;
	private String correlationNo;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String responseXML;
	private String custSeg;
	
	public PushMessage(Map<String, String> attributeMap, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		this.correlationNo=attributeMap.get("correlationNo");
		sUserName = LapsConfigurations.getInstance().UserName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessage cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PushMessage call: inside");
		String inputXml = "";
		callStatus="Y";
		try {			
			inputXml = createInputXML();
//			responseXML = ExecuteXML.executeXML(inputXml);
			responseXML = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessage outputXml ===> " + responseXML);
			if(responseXML==null || responseXML.equalsIgnoreCase("")){
				//responseXML= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				callStatus = "Y";
				status="Success";
				returnCode=1;
				errorDescription = "Web Service Error";
			}
			responseHandler(responseXML, inputXml);
			responseXML=responseXML+"<CallStatus>"+callStatus+"</CallStatus><CallResponse>"+errorDescription+""
					+ "</CallResponse>"+returnCode+"<returnCode></returnCode><errorDescription>"+errorDescription+""
					+ "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>"+status+"</Status>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessage outputXml ===> " + responseXML);
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	   
		return responseXML;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
		//	refNo = LapsUtils.getInstance().generateSysRefNumber();

			String AccountNumber="";
			String IBAN="";
			String CID="";
			String AccountCreatedDate="";
			String RiskClassification="";
			String CHANNEL_REF_NUMBER="";
			
			String op = APCallCreateXML.APSelect("SELECT USR_0_CUST_TXN.cust_id,usr_0_product_selected.ACC_NO,usr_0_product_selected.iban_no,usr_0_product_selected.acc_open_dt,USR_0_CUST_TXN.CPD_CUST_INDI_RISK,EXT_AO.CHANNEL_REF_NUMBER FROM USR_0_CUST_TXN  JOIN usr_0_product_selected ON USR_0_CUST_TXN.WI_NAME = usr_0_product_selected.WI_NAME JOIN EXT_AO ON EXT_AO.WI_NAME = usr_0_product_selected.WI_NAME where usr_0_product_selected.wi_name='"+winame+"'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "op is"+op);
			XMLParser xp1 = new XMLParser(op);
			int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Application_Eligibility TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					AccountNumber= xp1.getValueOf("ACC_NO");
					IBAN= xp1.getValueOf("IBAN_NO");
					CID= xp1.getValueOf("CUST_ID");
					AccountCreatedDate= xp1.getValueOf("ACC_OPEN_DT");
					RiskClassification= xp1.getValueOf("CPD_CUST_INDI_RISK");
					CHANNEL_REF_NUMBER=	 xp1.getValueOf("CHANNEL_REF_NUMBER");
				}
			}
			
			inputXml.append("<?xml version=\"1.0\"?>")
			.append("<APWebService_Input>")
			.append("<Option>WebService</Option>")
			.append("<Calltype>WS-2.0</Calltype>")
			.append("<InnerCallType>LapsResponse</InnerCallType>")
			.append("<SessionId>" + sessionId + "</SessionId>")
			.append("<EngineName>"+engineName+"</EngineName>")
			.append("<PushMessage>")
			.append("<ChannelResponse>")
			.append("<AccountNumber>"+AccountNumber+"</AccountNumber>")
			.append("<WINumber>"+winame+"</WINumber>")
			.append("<WIQueue></WIQueue>")
			.append("<WILockedByUser></WILockedByUser>")
			.append("<IBAN>" + IBAN + "</IBAN>")
			.append("<CID>"+CID+"</CID>")
			.append("<AccountCreatedDate>" + AccountCreatedDate + "</AccountCreatedDate>")
			.append("<RiskClassification>"+RiskClassification+"</RiskClassification>")
			.append("<correlationId>"+refNo+"</correlationId>")
			.append("<ChannelName>LAPS</ChannelName>")
			.append("<ChannelRefNumber>"+CHANNEL_REF_NUMBER+"</ChannelRefNumber>")
			.append("<StatusCode>0</StatusCode><StatusDescription>Success</StatusDescription>")
			.append("</ChannelResponse>")
			.append("</PushMessage>")
			.append("</APWebService_Input>");
						
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return inputXml.toString();
	}

	

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute Dependency called");
//			for (CallEntity callEntity : map) {
//				CallHandler.getInstance().executeCall(callEntity, map, sessionId,engineName,winame,correlationNo );
//			}
			CallHandler.getInstance().executeCall(callEntity, attributeMap, sessionId,"",winame,false );
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		try {
			if(!callStatus.equalsIgnoreCase("N"))
			{
				status="Success";
				errorDescription="Success";
				returnCode=0;
				//String output=outputXML;
			}
			
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute Updatecalloutput called");
		
		APCallCreateXML.APUpdate("EXT_AO", "REJECT_DEC", "'cancel'", "wi_name='"+winame+"'", sessionId);
		ProdCreateXML.WMCompleteWorkItem(sessionId, winame, 1);
	
	/*	APCallCreateXML.APInsert("USR_0_LAPS_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS,  RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,CALL_ID,CHANNEL_NAME,CALL_DT", valList, sessionId);

		

		String columns = "WI_NAME, USER_NAME, INPUT_XML, OUTPUT_XML, REQUEST_DATETIME, "
				+ "RESPONSE_DATETIME, CALL_NAME, CALL_ID, STATUS, ERRORDESC";
		String values = "'" + winame + "','" + sUserName + "','" + inputXml + "','" + responseXML
				+ "', sysdate, sysdate, 'CheckRiskClassification', " + currCallID + ", '"+status+"', '"+errorDescription+"'";
		//values = values+(callStatus.equalsIgnoreCase("Y")?"'Success', ''":"'Error', '"+errorDescription+"'");
		APCallCreateXML.APInsert("LAPS_INTEGRATION_CALLS", columns, values, sessionId);
		LapsUtils.insertInDecisionHistory(winame, LapsConfigurations.getInstance().UserName, "CheckRiskClassification", status, sessionId);
		if(!stageID.equals("-1")){
		for (java.util.Map.Entry<Integer, String> entry : map.entrySet()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key is "+ entry.getKey() + " Value is" + entry.getValue());
			if (val == 1) {
				callName = entry.getValue();
				break;
			}
			if (entry.getValue().equals("PushMessage")) {
				currCallID = entry.getKey();
				val++;
			}
		}
		if (callStatus.equalsIgnoreCase("N")) {
			String sWMSID = LapsUtils.generateWMSID(winame);
			APCallCreateXML.APUpdate("EXT_AO", "NXT_WS_NAME,WMS_ID,SCAN_MODE", "'Repair','"+sWMSID+"','New WMS ID'", "wi_name='" + winame + "'", sessionId);
			ProdCreateXML.WMCompleteWorkItem(sessionId, winame, 1);
		} else if (callStatus.equalsIgnoreCase("Y")) {

			String sWMSID = LapsUtils.generateWMSID(winame);
			APCallCreateXML.APUpdate("EXT_AO", "NXT_WS_NAME,WMS_ID,SCAN_MODE", "'CPD Maker','"+sWMSID+"','New WMS ID'", "wi_name='"+winame+"'", sessionId);
			ProdCreateXML.WMCompleteWorkItem(sessionId, winame, 1);
		
			}
		}
*/	}

//	@Override
//	public String executeCall(ArrayList<CallEntity> map) throws Exception {
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
//		return call();
//	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
		return call();
	}

}
