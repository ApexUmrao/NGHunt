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

public class PushMessageItqanM  implements ICallExecutor, Callable<String>{
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
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String responseXML;
	private String custSeg;
	String pushMessageXML = "";
	
	public PushMessageItqanM(String sessionId,String wiNumber) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside Push MEssage ITqanM");
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.winame=wiNumber;
		sUserName = LapsConfigurations.getInstance().UserName;
		String outputXml = "";
		try {
			outputXml = APCallCreateXML.APSelect("SELECT PUSH_MSG_XML from EXT_WBG_AO where WI_NAME = '" +wiNumber +"'");
		} catch (NGException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception Push MEssage ITqanM"+e);
		}
		XMLParser xp = new XMLParser(outputXml);

		if(Integer.parseInt(xp.getValueOf("MainCode")) == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
			pushMessageXML = xp.getValueOf("PUSH_MSG_XML");
		}
	
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
			.append("<ChannelName>ITQAN_M</ChannelName>")
		    .append("<WI_NAME>"+winame+"</WI_NAME>")
			.append("<PushMessage>")
			.append(pushMessageXML)
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
			}
			
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		XMLParser xp = new XMLParser(inputXml);
		String code = xp.getValueOf("Status");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute Updatecalloutput called");
		String auditCallName = "";
		if(code.equalsIgnoreCase("1")){
			APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG", "'E'", "wi_name='"+winame+"'", sessionId);
			APCallCreateXML.APUpdate("EXT_WBG_AO", "REASON_RETURN", "'cancel'", "wi_name='"+winame+"'", sessionId);
			ProdCreateXML.WMGetWorkItem(sessionId,winame,1);
			ProdCreateXML.WMCompleteWorkItem(sessionId, winame, 1);
		}
//		
//		String valueList = "'"+ winame +"',"+ stageId +",'" + auditCallName +"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
//				errorDetail +"', '"+ status +"', '"+ reason +"'";
//		APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
//				+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
//		
		
		}

//	@Override
//	public String executeCall(ArrayList<CallEntity> map) throws Exception {
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
//		return call();
//	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
		return null;
	}

}
