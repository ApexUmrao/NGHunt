package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.ArchivalCache;
import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.ArchivalLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.EdmsUserConnection;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.wfdesktop.xmlapi.WFCallBroker;

public class C_ARCHIVAL implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private int processdefid;
	private String edmsSession;
	public String EdmsServerIP;
	public String EdmsServerPort;
	public String EdmsUserName;
	public String EdmsPassword;
	public String EdmsCabName;
	String initActivityName="";
	private Map<String,HashMap<Integer, String>> processArchivalInitMap;
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	ArrayList<String> processValues = new  ArrayList<String>();
	int stageId = 1;
	String status = "0";
	String archivalChildWi="";
	String statusDesc = "Calls Executed Successfully";
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside C_ARCHIVAL");
			processArchivalInitMap = ArchivalCache.getInstance().getProcessArchivalInitMap();
			HashMap<Integer, String> processInitValues = new HashMap<Integer, String>();
			processInitValues = processArchivalInitMap.get("DA");
			for(Map.Entry<Integer, String> entryInner: processInitValues.entrySet()){
				processdefid = entryInner.getKey();
				initActivityName = entryInner.getValue();
			}
			processValuesMap = ArchivalCache.getInstance().getArchivalProcessValuesMap();
			processValues = processValuesMap.get(processName);
			String outputXml = APCallCreateXML.APSelect("SELECT ARCHIVAL_WI_NAME  FROM "+processValues.get(0)+" WHERE  "+processValues.get(3)+" = N'" + wiNumber + "'");
			XMLParser xp1 = new XMLParser(outputXml);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode1 == 0){
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"ARCHIVAL_WI_NAME TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					archivalChildWi = xp1.getValueOf("ARCHIVAL_WI_NAME");
				}
			}
			attributeMap.put("REQUESTOR_PROCESS_NAME", processName);
			attributeMap.put("REQUESTOR_WI_NAME", wiNumber);
			EdmsServerIP =LapsConfigurations.getInstance().EdmsServerIP;
			EdmsServerPort =LapsConfigurations.getInstance().EdmsServerPort;
			EdmsUserName =LapsConfigurations.getInstance().EdmsUserName;
			EdmsPassword =LapsConfigurations.getInstance().EdmsPassword;
			EdmsCabName  =LapsConfigurations.getInstance().EDMS;
			edmsSession=EdmsUserConnection.getInstance(100).getSession(EdmsCabName, EdmsUserName, EdmsPassword, EdmsServerIP, EdmsServerPort);
			attributeMap.put("edmsSession", edmsSession);
			attributeMap.put("processdefid", String.valueOf(processdefid));
		} catch (Exception e) {
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,"exception in C_ARCHIVAL dispatchEvent: ");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR,e);
		}
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Inside C_ARCHIVAL processDefId  ::: " + processdefid);
		try {
			if ("".equalsIgnoreCase(archivalChildWi) || null == archivalChildWi){
				String outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "Y", attributeMap,"",processdefid,initActivityName);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"outputXml :"+outputXml);
				XMLParser xp  = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "mainCode " + mainCode);
				if (mainCode == 0) {
					resp.setStatusCode("0");
					resp.setStatusDescription("Workitem Created Successfully");
					String workItemNumber = xp.getValueOf("ProcessInstanceId");
					ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Wi no is " +
							"external table Name : "+processValues.get(0)+
							wiNumber+" ARCHIVAL WI_NAME :"+workItemNumber);
					//updating in external table
					APCallCreateXML.APUpdate(processValues.get(0), "ARCHIVAL_WI_NAME",
							"'"+workItemNumber+"'",processValues.get(3) + " = '"+wiNumber+"'", sessionId);

					resp.setWiNumber(workItemNumber);

					int stageId = 1;
					HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
							(processdefid, sourcingChannel, "1.0", sourcingChannel,stageId);
					int eventID = (Integer) eventMap.keySet().toArray()[0];
					List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
					ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
					if(callArray != null) {
						ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Call_Array: " + workItemNumber + ":" + callArray.toString());
						for (CallEntity callEntity : callArray) {
							if(callEntity.isMandatory()){
								String outputXML = CallHandler.getInstance().executeCall
										(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
												workItemNumber, false);
								ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
										+ outputXML);
								if(!"".equals(outputXML)){
									xp = new XMLParser(outputXML);
									mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
									if(mainCode != 0){
										resp.setStatusCode("1");
										resp.setStatusDescription("Archival Failed");
									}
								}
							}
						}
					}
				} else {
					resp.setStatusCode("-1");
					resp.setStatusDescription("Workitem Not Created | Reason :"+xp.getValueOf("Description"));
				}
			} else {
				resp.setStatusCode("0");
				resp.setStatusDescription("Workitem Modified Successfully");
				String workItemNumber = archivalChildWi;
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Wi no is " + workItemNumber);
				resp.setWiNumber(workItemNumber);
				int stageId = 1;
				HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
						(processdefid, sourcingChannel, "1.0", sourcingChannel,stageId);
				int eventID = (Integer) eventMap.keySet().toArray()[0];
				List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
				if(callArray != null) {
					ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Call_Array: " + workItemNumber + ":" + callArray.toString());
					for (CallEntity callEntity : callArray) {
						if(callEntity.isMandatory()){
							String outputXML = CallHandler.getInstance().executeCall
									(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
											workItemNumber, false);
							ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
									+ wiNumber  + ":" +outputXML);
							if(!"".equals(outputXML)){
								XMLParser xp = new XMLParser(outputXML);
								int mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
								ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Execute Call: mainCode: "
										+ mainCode);
								if(mainCode != 0){
									ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
											+ wiNumber  + ":" +outputXML);
									resp.setStatusCode("1");
									resp.setStatusDescription("Archival Failed");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, "exception in wfupload session ID: ");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, e);
			resp.setStatusCode("-1");
		}
		return resp;
	}	
}

