package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
public class WBGEidaDedupe implements ICallExecutor, Callable<String>{
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String EIDANum;
	private String COMPLETE_FLAG;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String leadRefNumber ="";
	private String senderID ="WMSBPMENG";
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private HashMap<String, String> newAttributeMap =null;
	private CallEntity callEntity;
	private String auditCallName = "EIDA_DEDUPE";
	
	
	public WBGEidaDedupe(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {
		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"defaultMap "+attributeMap);

	String outputXML;
		try {
			XMLParser xp;
			newAttributeMap = new HashMap<String,String>();
			EIDANum = attributeMap.get("EIDA_NO");
			COMPLETE_FLAG = attributeMap.get("COMPLETE_FLAG");
			newAttributeMap.put("EIDA_NO", EIDANum);
			newAttributeMap.put("COMPLETE_FLAG", COMPLETE_FLAG);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EIDANum in EIDA Dedupe===>"+EIDANum);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"newAttributeMap ===>"+newAttributeMap);
			if(EIDANum == null || EIDANum.equals("") ){
				updateCheckFlag = false;
				callStatus = "F";
				errorDescription = "EIDA No cannot be blank";
				returnCode = 1;
			}

			String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp1 = new XMLParser(soutputXML);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA001", "EIDADedupe Started", sessionId);
			if(mainCode1 == 0){
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					
					leadRefNumber = xp1.getValueOf("LEAD_REF_NO");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber"+leadRefNumber);
					LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA002", EIDANum, sessionId);
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml = "";
		String finalOutputXml = "";
		try {
			if(updateCheckFlag){
				outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml Eida "+outputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "EidaDedupe outputXml ===> " + outputXml);
				XMLParser xmlResponse = new XMLParser(outputXml);
				returnCode = Integer.parseInt(xmlResponse.getValueOf("returnCode", "1", true));
				if(outputXml==null ||outputXml.equalsIgnoreCase("")){
					outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				} 			

				responseHandler(outputXml,inputXml);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA004", "EIDAMQ output:"+outputXml, sessionId);
				  finalOutputXml= "<returnCode>'"+returnCode+"'</returnCode><Eida>'"+callStatus+"'</Eida><Status>0</Status><errorDescription>'"+errorDescription+"'</errorDescription>";
				  LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "finalOutputXml " + finalOutputXml);

			} else {
				callStatus = "F";
				errorDescription = "EIDA No cannot be blank";
				returnCode = 1;
				updateCallOutput(inputXml);
			}
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
		return finalOutputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder(); 
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>CustEIDAInfo</InnerCallType>").append("\n")
			.append("<ServiceName>CustomerInfo</ServiceName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
			.append("<senderID>" + senderID + "</senderID>").append("\n")
			.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
			.append("<EIDANum>" + EIDANum + "</EIDANum>").append("\n")
			.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "EIDADedupe inputXML ===> "+inputXML.toString());
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
		return inputXML.toString();   
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		// TODO Auto-generated method stub
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, newAttributeMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		try 
		{
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA090", "EidaDedupe Successfully Executed - "+callStatus, sessionId);

			} else {
				if(errorDetail.contains("EIDA not available")){
					callStatus = "X";
					LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA090", "EidaDedupe Successfully Executed - "+callStatus, sessionId);
				} else {
					callStatus = "N";
					LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA090", "EidaDedupe Successfully Executed - "+callStatus, sessionId);

				}

			}
			updateCallOutput(EIDANum);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus "+callStatus);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errorDescription "+errorDescription);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returnCode "+returnCode);


		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String EidaNumber) throws Exception {
		
		try {
			String valueList = "'"+ WI_NAME +"',"+ StageID +",'" + auditCallName +"', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
			APCallCreateXML.APUpdate("EXT_WBG_AO", "EIDA_DEDUPE_FLAG", " "+ callStatus +" ", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
			String noResultsFoundMsg = "";
			String sActivityName = "";
			String reasonForAction = "" ;
			String action = "";
			
			errorDescription = "Records Found For Dedupe";
			if(callStatus == "Y"){
				 noResultsFoundMsg = "Eida Dedupe found";
				 sActivityName = "Introduction";
				 reasonForAction = "Dedupe Check is performed for EIDA Number: " + EidaNumber;
				 action = "Eida Dedupe found";
				 errorDescription = "Records Found For Dedupe";
				 if(COMPLETE_FLAG == "Y"){
					 ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				 }
				 
			} else if(callStatus == "X"){
				 noResultsFoundMsg = "Eida Not Available";
				 sActivityName = "Introduction";
				 reasonForAction = "Dedupe Check is performed for EIDA Number: " + EidaNumber;
				 action = "Eida Not Available";
				 errorDescription = "No Records Found For Dedupe";
				 executeDependencyCall();
			}  else if(callStatus == "N"){
				 noResultsFoundMsg = "Eida Dedupe not found";
				 sActivityName = "Introduction";
				 reasonForAction = "Dedupe Check is performed for EIDA Number: " + EidaNumber;
				 action = "Eida Dedupe not found";
				 errorDescription = "No Records Found For Dedupe";
				executeDependencyCall();
			}
				
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);
				String valList = "'"+ WI_NAME +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
				APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);
			
			
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

}
