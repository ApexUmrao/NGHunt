package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class WBGSanctionScreening implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	private String leadRefNo="";
	Map<String, String> attributeMap = new HashMap<String, String>();
	String responseXML = "";
	String callStatus = "";
	private boolean prevStageNoGo;
	String status = "";
	int returnCode ;
	String errorDescription = "";
	String auditCallName = "WBGSanctionScreening";
	String trsdWIName = "";
	boolean isCallExecuted;
	
		
	public WBGSanctionScreening(Map<String, String> attributeMap, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		this.prevStageNoGo = prevStageNoGo;
		sUserName = LapsConfigurations.getInstance().UserName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WBGSanctionScreening cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}
	
	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"WBGSanctionScreening call: inside");
		String inputXml = "";
		callStatus = "Y";
		returnCode = 0;
		errorDescription = "TRSD EXECUTION FAILED";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted ");
		isCallExecuted = LapsUtils.isItqanMCallExecuted(auditCallName,winame);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted " + isCallExecuted);

		try {	
			if(!isCallExecuted){
				inputXml = createInputXML();
				String responseXML = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WBGSanctionScreening outputXml ===> " + responseXML);

				if(responseXML==null || responseXML.equalsIgnoreCase("")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside response null" + responseXML);
					//responseXML= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					callStatus = "F";
					status="Error";
					returnCode=1;
					errorDescription = "Web Service Error";
				} else {
					XMLParser xp = new XMLParser(responseXML);
					int mainCode = Integer.parseInt(xp.getValueOf("StatusCode"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside mainCode null" + mainCode);
					if(mainCode == 0){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside mainCode null2" + mainCode);
						trsdWIName= xp.getValueOf("WINumber");
						errorDescription = xp.getValueOf("StatusCode");
						if(!trsdWIName.equalsIgnoreCase("")){
							String op = APCallCreateXML.APSelect("SELECT TRSD_STATUS FROM BPM_TRSD_DETAILS WHERE WI_NAME ='"+trsdWIName+"'");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "op is"+op);
							XMLParser xp1 = new XMLParser(op);
							mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
							if(mainCode == 0){
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
								if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
									String trsdStatus = xp1.getValueOf("TRSD_STATUS");
									if(trsdStatus.equalsIgnoreCase("E")){
										callStatus = "F";
										returnCode = 1;
										errorDescription = xp.getValueOf("StatusCode");
										status="Error";

									}
								}
							}	
						}  else {
							returnCode = 1;
							callStatus = "F";
							errorDescription = xp.getValueOf("StatusCode");
							status="Error";

						}
					} else {
						callStatus = "F";
						returnCode = 1;
						errorDescription = "TRSD EXECUTION FAILED";
						status="Error";
					}
				}
				updateCallOutput(responseXML);
			}
			if(callStatus.equalsIgnoreCase("F")){
				APCallCreateXML.APUpdate("EXT_WBG_AO", "REPAIR_FLAG", "'Y'", "WI_NAME = '"+winame+"'", 
						sessionId);
				APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG", "'Y'", "WI_NAME = '"+winame+"'", 
						sessionId);
			}


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
			String op = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME ='"+winame+"'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "op is"+op);
			XMLParser xp1 = new XMLParser(op);
			int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					leadRefNo= xp1.getValueOf("LEAD_REF_NO");
					
				}
			}			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE createTRSDInputXML"+ inputXml.toString());
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + engineName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
			.append("<wiNumber>" +winame + "</wiNumber>").append("\n")
			.append("<REF_NO>" + leadRefNo + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<mode>C</mode>").append("\n")
			.append("<channelName>SANCTION_SCREENING</channelName>").append("\n")
			.append("<correlationId>"+leadRefNo+"</correlationId>").append("\n")
			.append("<channelRefNumber>"+leadRefNo+"</channelRefNumber>").append("\n")
			.append("<sysrefno>"+leadRefNo+"</sysrefno>").append("\n")
			.append("<processName>WBG</processName>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"input xml of createWorkitemInputXML="+ inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Error in getTRSDResponse() company: "
					+ e.getMessage());
		}
		return inputXml.toString();	
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called");
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, winame, auditCallName, "TRSD001", "TRSD DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, attributeMap, sessionId, winame, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, winame, auditCallName, "TRSD001", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside updateCallOutput");
		if(LapsUtils.isCallNameInBpmCallOut(auditCallName,winame)){
			String valList = "'"+ callStatus +"','"+ returnCode +"','" + errorDescription +"'";
//			APCallCreateXML.APUpdate("BPM_CALL_OUT", "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION",valList, " WI_NAME = N'"+ winame +"'  AND CALL_STATUS = '"+auditCallName+"'", sessionId);
			String sTable_info = "BPM_CALL_OUT";
			String sColumn = "CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION";
			String sWhere = "wi_name = N'" + winame + "' AND CALL_NAME = '"+auditCallName+"'";

			APCallCreateXML.APUpdate(sTable_info, sColumn, valList, sWhere, sessionId);

		} else {
			try {
				String errorDetail =  "";
				String reason = "";
				String valueList = "'"+ winame +"',"+ stageId +",'" + auditCallName +"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"'";
				APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
						+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
				//APCallCreateXML.APUpdate("EXT_WBG_AO", "EIDA_CHKSUM_FLAG", " "+ callStatus +" ", " WI_NAME = N'"+ winame +"'", sessionId);
				String noResultsFoundMsg = "";
				String sActivityName = "";
				String reasonForAction = "" ;
				String action = "";

				if(!callStatus.equalsIgnoreCase("F")){
					noResultsFoundMsg = "SUCCESS";
					sActivityName = "Introduction";
					reasonForAction = "TRSD EXECUTION SUCCESS";
					action = "TRSD PERFORMED";
					errorDescription = "TRSD PERFORMED";
				} else {
					noResultsFoundMsg = "FAILURE";
					sActivityName = "Introduction";
					reasonForAction = "TRSD EXECUTION FAILED";
					action = "TRSD FAILED";
					errorDescription = "TRSD FAILED";
					APCallCreateXML.APUpdate("EXT_WBG_AO", "REPAIR_FLAG", "'Y'", "WI_NAME = '"+winame+"'", 
							sessionId);
				}
				ProdCreateXML.WMCompleteWorkItem(sessionId, winame, 1);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNo);

				String valList = "'"+ winame +"','"+ leadRefNo +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
				APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}

	}
}
