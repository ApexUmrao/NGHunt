
package com.newgen.ao.laps.calls;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class PushMsgWMSToItqan implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	private String refNo;
	private String callStatus;
	private String correlationNo = "";
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String responseXML;
	
	
	public PushMsgWMSToItqan(Map<String, String> attributeMap, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		this.correlationNo=attributeMap.get("correlationNo");
		sUserName = LapsConfigurations.getInstance().UserName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessageToQueue2 cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PushMessageToQueue2 call: inside");
		String inputXml = "";
		callStatus="Y";
		try {			
			inputXml = createInputXML();
			responseXML = ExecuteXML.executeXML(inputXml);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessageToQueue2 outputXml ===> " + responseXML);
			if(responseXML==null || responseXML.equalsIgnoreCase("")){
				//responseXML= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				callStatus = "Y";
				status="Success";
				returnCode=1;
				errorDescription = "Web Service Error";
			}
			responseHandler(responseXML, inputXml);
			responseXML=responseXML+"<CallStatus>"+callStatus+"</CallStatus><CallResponse>"+errorDescription+""
					+ "</CallResponse><returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+""
					+ "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>"+status+"</Status>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessageToQueue2 outputXml ===> " + responseXML);
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	   
		return responseXML;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuffer input = new StringBuffer();
		try{
			String leadRefNo="";
			String wiName="";
			String debitCardNo="";
			String custID="";
			String embossName="";
			String description="";
			String inputXML ="";
			int status =1;
			String outputXml;
			String outputXml1;
			int mainCode = -1;
			int mainCode1 = -1;
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside create xml");
			outputXml = APCallCreateXML.APSelect("SELECT E.LEAD_REF_NO,A.WI_NAME,A.DEBIT_CARD_NO,A.CUST_ID,A.EMBOSSNAME FROM USR_0_WBG_AO_AUS A,"
					+ " EXT_WBG_AO E WHERE E.WI_NAME=A.WI_NAME and  E.WI_NAME = '"+this.winame+"'");
			XMLParser xp = new XMLParser(outputXml);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			outputXml1 = APCallCreateXML.APSelect("select distinct(ws_comments) from USR_0_WBG_AO_DEC_HIST where ws_decision ='Reject' and  WI_NAME = '"+this.winame+"'");
			mainCode1 = Integer.parseInt(xp.getValueOf("MainCode"));
			XMLParser xp1 = new XMLParser(outputXml1);
			if (mainCode1 == 0) {
				description = xp1.getValueOf("ws_comments");
			}
			if (mainCode == 0) {
				leadRefNo = xp.getValueOf("LEAD_REF_NO");
				wiName = xp.getValueOf("WI_NAME");
				debitCardNo = xp.getValueOf("DEBIT_CARD_NO");
				custID = xp.getValueOf("CUST_ID");
				embossName = xp.getValueOf("EMBOSSNAME");
				if(description ==""){
					description ="Work Item completed successfully";
					status =0;
				}
				 
				input.append("<?xml version=\"1.0\"?>")
				.append("<APWebService_Input>")
				.append("<Option>WebService</Option>")
				.append("<Calltype>WS-2.0</Calltype>")
				.append("<InnerCallType>LapsResponse</InnerCallType>")
				.append("<SessionId>" + sessionId + "</SessionId>")
				.append("<EngineName>"+engineName+"</EngineName>")
				.append("<ChannelName>ITQAN_M</ChannelName>")
				.append("<WI_NAME>" + wiName + "</WI_NAME>")
				.append("<PushMessage>")
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
				.append("<GetCDCMQResponse>")
				.append("<LeadRefNumber>" + leadRefNo + "</LeadRefNumber>")
				.append("<WorkItemName>" + wiName + "</WorkItemName>")
				.append("<CustomerId>" + custID + "</CustomerId>")
				.append("<DebitCardNum>" + debitCardNo + "</DebitCardNum>")
				.append("<EmbossName>" + embossName + "</EmbossName>")
				.append("<Code>201</Code>")
				.append("<Status>"+status+"</Status>")
				.append("<Description>" + description + "</Description>")
				.append("</GetCDCMQResponse>")
				.append("</PushMessage>")
				.append("</APWebService_Input>");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, input.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
			return input.toString();
		}
	

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called in PushMessageToQueue2");
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

}

