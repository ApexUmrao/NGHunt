package com.newgen.ao.laps.sourcingchannel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.cache.TSLMComplexMapping;
//import com.newgen.ao.laps.cache.TSLMStagingCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.implementation.BPMMandatoryCheck;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.ao.laps.util.SingleUserConnection;
import com.newgen.omni.jts.cmgr.XMLParser;

public class DM_TSLMSY implements SourcingChannelHandler{
	int stageId = 2;  // ATP-461 REYAZ 14-05-2024
	String status = "0";
	//String statusDesc = "Calls Executed Successfully";
	HashMap<String, String> attribMap = new HashMap<String, String>();
	private String statusDescription = "";
	private String statusCode = "0";
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	public LapsModifyRequest request; 
	private int processDefId;
	public LapsModifyResponse resp = new LapsModifyResponse();

	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try DM_TSLMSY_SCF");
		    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try DM_TSLMSY_SCF channelRefNumber :"+channelRefNumber);
		    //ATP-479 --JAMSHED 07-JUN-2024 START
		    String outputxml = APCallCreateXML.APSelect("SELECT CORRELATIONID FROM EXT_TFO WHERE WI_NAME = N'" + wiNumber + "'");
			XMLParser xp = new XMLParser(outputxml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DM_TSLMSY TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					channelRefNumber = xp.getValueOf("CORRELATIONID");		
				}
			}	
		    attributeMap.put("mode", mode);
			attributeMap.put("ruleFlag", "N");
			attributeMap.put("channelRefNo", channelRefNumber);
			//ATP-479 --JAMSHED 07-JUN-2024 END
		    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try DM_TSLMSY_SCF attributeMap :"+attributeMap.get("channelRefNumber"));
			String output = APCallCreateXML.APSelect("select PROCESSDEFID from processdeftable where processname='"+processName+"'");
			XMLParser xmlparser = new XMLParser(output);
			int mainCode1 = Integer.parseInt(xmlparser.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
			if(mainCode1 == 0){
				if(Integer.parseInt(xmlparser.getValueOf("TotalRetrieved")) > 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
					processDefId = Integer.parseInt(xmlparser.getValueOf("PROCESSDEFID"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processDefId: " + processDefId);	
					attributeMap.put("processDefId", xmlparser.getValueOf("PROCESSDEFID"));
				}
			}
				HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
							(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
				int eventID = (Integer) eventMap.keySet().toArray()[0];
				List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mode: " + mode);
//				attributeMap.put("mode", mode);  // ATP-461 REYAZ 14-05-2024
			 //ATP-485 Date 27-06-2024 REYAZ  START
					if(callArray != null) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
					boolean nogoCall = false;
						for (CallEntity callEntity : callArray) {
							if(callEntity.isMandatory()){
							String outputXML = CallHandler.getInstance().executeCall
									(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
											wiNumber, false);
							xp = new XMLParser(outputXML);
							if("0".equalsIgnoreCase(status)){
								status = xp.getValueOf("returnCode");
							}
							if(!"0".equals(status)){
								statusDescription = xp.getValueOf("errorDescription");
									}
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
									+ outputXML);
								   }
									}
						}
		           
					String outputXML1 = APCallCreateXML.APSelect("SELECT Count(1) as Count FROM BPM_ORCHESTRATION_STATUS WHERE WI_NAME = N'" + wiNumber + "' and CALL_STATUS IN ('N','F')");
					XMLParser xp1 = new XMLParser(outputXML1);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML1 ::: "+outputXML1);
					mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
					if(mainCode1 == 0){
						if(Integer.parseInt(xp1.getValueOf("Count")) > 0){
							String outputXML = APCallCreateXML.APSelect("select WORKITEMID from WFINSTRUMENTTABLE "
									+ "where PROCESSINSTANCEID = '"+wiNumber+"' and INTRODUCEDAT = 'Distribute1'"
											+ " and ACTIVITYNAME = 'ToDoList'");
							XMLParser xp3 = new XMLParser(outputXML);
							int wrkitmId = 0;
							if (Integer.parseInt(xp3.getValueOf("MainCode")) == 0) {
								wrkitmId = Integer.parseInt(xp3.getValueOf("WORKITEMID"));
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId from WFINSTRUMENTTABLE: " + wrkitmId);
							}
							APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP,ROUTE_TO_REPAIR",
									"'Customer Referral Response','Y'", "WI_NAME = '"+wiNumber+"'", sessionId);
							ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, wrkitmId);
						}
					}		
					 //ATP-485 Date 27-06-2024 REYAZ  END
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DM_TSLMSY_SCF:  statusDesc = "+statusDescription);
				} catch (Exception e) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in DM_TSLMSY_SCF dispatchEvent: ");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
	}

	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +wiNumber);
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"statusCode : " + statusCode);
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"statusDescription : " + statusDescription);
	resp.setWiNumber(wiNumber);
	resp.setStatusCode(statusCode); 
	resp.setStatusDescription(statusDescription); 
	resp.setChannelRefNumber(channelRefNumber);
	resp.setCorrelationId(correlationId);
	return resp;

	}

public static  String createNormalizedXML(String outputXml) {
	try {
		if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
			outputXml = outputXml.replace("'", "''");
			outputXml = outputXml.replace("&", "'||chr(38)||'");
			if (outputXml.length() > 3200) {				
				outputXml = breakCLOBString(outputXml);
				return outputXml;
			}
			outputXml = "TO_NCLOB('" + outputXml + "')";
			return outputXml;
		}			
	} catch (Exception ex) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, ex);	 				
	}
	return "''";
}

private static  String breakCLOBString(String xml) {
	int itr = xml.length() / 3200;
	int mod = xml.length() % 3200;
	if (mod > 0) {
		++itr;
	}
	StringBuilder response = new StringBuilder();
	try {
		for (int i = 0; i < itr; ++i) {
			if (i == 0) {
				response.append("TO_NCLOB('" + xml.substring(0, 3200) + "')");
			} else if (i < itr - 1) {
				response.append(" || TO_NCLOB('" + xml.substring(3200 * i,3200 * (i + 1)) + "')");
			} else {
				response.append(" || TO_NCLOB('" + xml.substring(3200 * i,xml.length()) + "') ");
			}
		}
	} catch (Exception e) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	 				
	}
	return response.toString();
}
	

}