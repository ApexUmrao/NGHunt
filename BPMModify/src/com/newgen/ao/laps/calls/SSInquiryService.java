
package com.newgen.ao.laps.calls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.SanctionScreeningMapping;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.ao.laps.util.ExecuteXML;	

public class SSInquiryService implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	private String refNo;
	private String callStatus;
	private String systemId = "";
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String responseXML;
	private String auditCallName = "FSK Inquiry";
	private int stageID;


	public SSInquiryService(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside SSInquiryService");
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageID = Integer.parseInt(stageId);
		this.winame=WI_NAME;
		this.systemId=attributeMap.get("correlationNo");
		this.refNo = attributeMap.get("channelRefNumber");
		sUserName = LapsConfigurations.getInstance().UserName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SSInquiryService cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"SSInquiryService call: inside");
		String inputXml = "";
		String clientID = getFSKClientID(systemId);
		try {			
			inputXml = createInputXML();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SSInquiryService inputXml ===> " + inputXml);
			responseXML = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SSInquiryService outputXml ===> " + responseXML);
			updateFskDetails();
			insertFSKXmlAudit(winame, inputXml.toString(), "Request", clientID + "", "S");
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SSInquiryService outputXml ===> " + responseXML);
			insertFSKXmlAudit(winame, responseXML.toString(), "Response", clientID + "", "Y");
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "FSK100", e.getMessage(), sessionId);
		}
	   
		return responseXML;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuffer input = new StringBuffer();
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try{
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside create xml");
		    input.append("<?xml version=\"1.0\"?>").append("\n")
				.append("<APWebService_Input>").append("\n")
				.append("<Option>WebService</Option>").append("\n")
				.append("<EngineName>" + engineName + "</EngineName>").append("\n")
				.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
				.append("<Calltype>WS-2.0</Calltype>").append("\n")
				.append("<InnerCallType>FSK_FetchCaseStatus</InnerCallType>").append("\n")			
				.append("<WiName>" + winame + "</WiName>").append("\n")
				.append("<fetchCaseStatusReqMsg>").append("\n")
				.append("<usecaseID>" +1234+ "</usecaseID>").append("\n")
				.append("<serviceName>InqCustomerScan</serviceName>").append("\n")
				.append("<versionNo>1.0</versionNo>").append("\n")
				.append("<serviceAction>Inquiry</serviceAction>").append("\n")
				.append("<correlationID></correlationID>").append("\n")
				.append("<sysRefNumber>" + refNo + "</sysRefNumber>").append("\n")
				.append("<senderId>WMS</senderId>").append("\n")
				.append("<consumer>FSK</consumer>").append("\n")	
				.append("<reqTimeStamp>" +sDate+ "</reqTimeStamp>").append("\n")	
				.append("<username></username>").append("\n")	
				.append("<credentials></credentials>").append("\n")	
				.append("<returnCode></returnCode>").append("\n")	
				.append("<errorDescription></errorDescription>").append("\n")	
				.append("<errorDetail></errorDetail>").append("\n")	
				.append("<fetchCaseStatusReq>").append("\n")
	            .append("<systemId>"+systemId+"</systemId>").append("\n")
				.append("</fetchCaseStatusReq>").append("\n")
	            .append("</fetchCaseStatusReqMsg>").append("\n")
				.append("</APWebService_Input>");
		    
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, input.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
			return input.toString();
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called in SSInquiryService");
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, winame, auditCallName, "FSK090", "FSKINQUIRY Successfully Executed", sessionId);
			} else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "FSK100", e.getMessage(), sessionId);
		}		
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ winame +"',"+ stageID +", 'SSInquiryService', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
				
			
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "FSK100", e.getMessage(), sessionId);
		}
		
	}
	
	public void updateFskDetails()throws Exception{
		try{
			XMLParser xp1 = new XMLParser(responseXML);
			String dbAccount = xp1.getValueOf("dbAccount");	
			String unit = xp1.getValueOf("unit");
			String trsdApprovedBy = xp1.getValueOf("lastOperator");
			String trsdDate = xp1.getValueOf("lastUpdate");	
			String trsdDecisionReason = xp1.getValueOf("lastComment");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"before replace trsdDecisionReason "+trsdDecisionReason);
			trsdDecisionReason = trsdDecisionReason.replace("'", "''");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"After replace trsdDecisionReason "+trsdDecisionReason);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD APPROVED BY "+trsdApprovedBy);
			/*String tableName = "BPM_TRSD_DETAILS";
			String columnName = " TRSD_APPROVEDBY, TRSD_ASSESSMENT_DATE,TRSD_DECISION_REASON";
			String values = "'" + trsdApprovedBy + "','" + trsdDate + "','FSK case closed with Remarks " + trsdDecisionReason + "'";	
			String where = " TRSD_CASE_CODE = '"+systemId+"'";*/
			String query = "UPDATE BPM_TRSD_DETAILS SET TRSD_APPROVEDBY ='"+trsdApprovedBy+"', TRSD_ASSESSMENT_DATE ='"+trsdDate+"',"
					+ "TRSD_DECISION_REASON = 'FSK case closed with Remarks "+trsdDecisionReason+"' WHERE  TRSD_CASE_CODE = '"+systemId+"'"; 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"query:: "+query);
			LapsUtils.getInstance().updateDataInDB(query);
			//APCallCreateXML.APUpdate(tableName,columnName,values,where, sessionId);
		} catch (Exception e) {
		    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
	    }
	}

	public void insertFSKXmlAudit(String wi_name, String xml, String audit_type, String trsd_client_id, String trsd_status){
		try{
			String sValues = "";
			String sTable = "bpm_trsd_audit";
			String sColumn = "WI_NAME,TRSD_AUDIT_TIME,TRSD_AUDIT_TYPE,TRSD_AUDIT_XML,TRSD_CLIENT_ID,TRSD_STATUS";
			xml = xml.replace("'", "''");
			xml = createNormalizedXML(xml);
		//	trsd_client_id = trsd_client_id+"_"+entityType;
			if (audit_type.equalsIgnoreCase("Request")) {
				sValues = "'" + wi_name + "',systimestamp,'REQ'," + xml + ",'"
						+ trsd_client_id + "','S'";
			} else if (audit_type.equalsIgnoreCase("Response")) {
				sValues = "'" + wi_name + "',systimestamp,'RES'," + xml + ",'"
						+ trsd_client_id + "','" + trsd_status + "'";
			}
			String outputXml = APCallCreateXML.APInsert(sTable, sColumn, sValues, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertFSKXmlAudit " + outputXml);
		}catch (Exception e){
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "FSK100", e.getMessage(), sessionId);
		}
	}
	
	public String createNormalizedXML(String outputXml) {
		try {
			if (outputXml.length() > 4000) {
				int itr = outputXml.length() / 4000;
				int mod = outputXml.length() % 4000;
				if (mod > 0) {
					++itr;
				}
				StringBuilder response = new StringBuilder();
				try {
					for (int i = 0; i < itr; i++) {
						if (i == 0) {
							response.append("TO_NCLOB('"
									+ outputXml.substring(0, 4000) + "')");
						} else if (i < itr - 1) {
							response.append(" || TO_NCLOB('"
									+ outputXml.substring((4000 * i),
											4000 * (i + 1)) + "')");

						} else {
							response.append(" || TO_NCLOB('"
									+ outputXml.substring((4000 * i),
											outputXml.length()) + "') ");

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				outputXml = response.toString();
				return outputXml;
			} else {
				outputXml = "'" + outputXml + "'";
				return outputXml;
			}

		} catch (Exception ex) {
			ex.printStackTrace();	
		}
		return "";
	}
	

	public String getFSKClientID(String systemCaseID){
		String clientIdFSK = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT TRSD_CLIENT_ID FROM BPM_TRSD_DETAILS WHERE  TRSD_CASE_CODE  = '" + systemCaseID + "'");
	        XMLParser xp = new XMLParser(outputXML);
	        int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
	        if (mainCode == 0) {
	          clientIdFSK = xp.getValueOf("TRSD_CLIENT_ID");
	          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "clientIdFSK : " + clientIdFSK);
	        }
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, winame, auditCallName, "FSK100", e.getMessage(), sessionId);
	   }
		return clientIdFSK;
	}
}
		