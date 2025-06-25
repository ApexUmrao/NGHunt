
package com.newgen.ao.laps.calls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ConnectSocket;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class PushMessageTSLMSCF  implements ICallExecutor, Callable<String>{
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	private String refNo;
	private String callStatus;
	@SuppressWarnings("unused")
	private String correlationNo;
	private int returnCode;
	private String errorCode;
	private String errorDescription;	
	private String status;
	private String statusCode;
	private String reason;
	private String responseXML;
	private String custSeg;
	String pushMsgXML = "";
	
	public PushMessageTSLMSCF(String sessionId,String wiNumber,String statusCode,String errorCode,String errorDesription) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside Push Message TSLMSCF");
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.winame=wiNumber;
		this.statusCode =statusCode;
		this.errorCode =errorCode;
		this.errorDescription =errorDesription;
		sUserName = LapsConfigurations.getInstance().UserName;
		StringBuilder pushMessageXML=new StringBuilder();
		pushMessageXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		pushMessageXML.append("<GetCDCMQResponse>");
		pushMessageXML.append("<WorkItemName>"+winame+"</WorkItemName>");
		pushMessageXML.append("<Status>"+statusCode+"</Status>");
		pushMessageXML.append("<Code>"+errorCode+"</Code>");
		pushMessageXML.append("<Description>"+errorDescription+"</Description>");
		pushMessageXML.append("</GetCDCMQResponse>");
		
		pushMsgXML = pushMessageXML.toString();
	
	}
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PushMessage call: inside");
		String inputXml = "";
		callStatus="Y";
		try {			
			inputXml = createInputXML();
			responseXML = ExecuteXML.executeXML(inputXml);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessage outputXml ===> " + responseXML);
			if(responseXML==null || responseXML.equalsIgnoreCase("")){
				callStatus = "Y";
				status="Success";
				returnCode=1;
				errorDescription = "Web Service Error";
			}
			responseHandler(responseXML, inputXml);
			responseXML=responseXML+"<CallStatus>"+callStatus+"</CallStatus><CallResponse>"+errorDescription+""
					+ "</CallResponse>"+returnCode+"<returnCode></returnCode><errorDescription>"+errorDescription+""
					+ "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>"+status+"</Status>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessage outputXml Itqan_M ===> " + responseXML);
			
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
			inputXml.append("<?xml version=\"1.0\"?>")
			.append("<APWebService_Input>")
			.append("<Option>WebService</Option>")
			.append("<Calltype>WS-2.0</Calltype>")
			.append("<InnerCallType>LapsResponse</InnerCallType>")
			.append("<SessionId>" + sessionId + "</SessionId>")
			.append("<EngineName>"+engineName+"</EngineName>")
			.append("<ChannelName>TSLM</ChannelName>")
		    .append("<WI_NAME>"+winame+"</WI_NAME>")
			.append("<PushMessage>")
			.append(pushMsgXML)
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
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
	
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
	
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
		return null;
	}

}

