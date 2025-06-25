package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_SANCTION_SCREENING implements SourcingChannelHandler {
	int processdefid ;
	String initActivityName="";
	String trsdChildWi="";
	String leadRefno="";
	String processSourcingChannel ="";
	HashMap<String, String> attribMap = new HashMap<String, String>();
	private Map<String,HashMap<Integer, String>> processInitMap;
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	ArrayList<String> processValues = new  ArrayList<String>();
	public LapsModifyResponse resp = new LapsModifyResponse();
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_SANCTION_SCREENING");
//		SingleUserConnection instance = SingleUserConnection.getInstance(1000);
//		try {
//			sessionId = instance.getSession(
//					LapsConfigurations.getInstance().CabinetName,
//					LapsConfigurations.getInstance().UserName,
//					LapsConfigurations.getInstance().Password);
//		} catch (Exception e) {
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in fetching session ID: ");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
		processInitMap = ChannelCallCache.getInstance().getProcessInitMap();
		HashMap<Integer, String> processInitValues = new HashMap<Integer, String>();
		processInitValues = processInitMap.get("SS");
		for(Map.Entry<Integer, String> entryInner: processInitValues.entrySet()){
			processdefid = entryInner.getKey();
			initActivityName = entryInner.getValue();
		}
		attribMap.put("REQUESTOR_PROCESS_NAME", processName);
		attribMap.put("REQUESTOR_WI_NAME", wiNumber);
		String outputXMLLead ="";
		try {
			processValuesMap = ChannelCallCache.getInstance().getProcessValuesMapMap();
			processValues = processValuesMap.get(processName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"processValues Map");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"External Table"+processValues.get(0));
			if("WBG".equalsIgnoreCase(processName)){
				 outputXMLLead = APCallCreateXML.APSelect("SELECT TRSD_WI_NAME,LEAD_REF_NO,"+processValues.get(4)+" as SOURCING_CHANNEL  FROM "+processValues.get(0)+" WHERE WI_NAME = N'" + wiNumber + "'");
				 XMLParser xp3 = new XMLParser(outputXMLLead);
					int mainCode3 = Integer.parseInt(xp3.getValueOf("MainCode"));
					if(mainCode3 == 0){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD_WI_NAME TotalRetrieved: "+Integer.parseInt(xp3.getValueOf("TotalRetrieved")));
						if(Integer.parseInt(xp3.getValueOf("TotalRetrieved")) > 0){
							leadRefno = xp3.getValueOf("LEAD_REF_NO");
							attribMap.put("LEAD_REF_NO", leadRefno);
						}
					}
			} else {
			     outputXMLLead = APCallCreateXML.APSelect("SELECT TRSD_WI_NAME,"+processValues.get(4)+" as SOURCING_CHANNEL  FROM "+processValues.get(0)+" WHERE WI_NAME = N'" + wiNumber + "'");
			}
			XMLParser xp1 = new XMLParser(outputXMLLead);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode1 == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD_WI_NAME TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					trsdChildWi = xp1.getValueOf("TRSD_WI_NAME");
					processSourcingChannel = xp1.getValueOf("SOURCING_CHANNEL");
					attribMap.put("REQUESTOR_SOURCING_CHANNEL", processSourcingChannel);
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in wfupload session ID: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			resp.setStatusCode("-1");
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"trsdChildWi : "+trsdChildWi);
		attribMap.put("TRSD_WI_NAME", trsdChildWi);
		try {
			if ("".equalsIgnoreCase(trsdChildWi) || null == trsdChildWi){
				String outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "Y", attribMap,"",processdefid,initActivityName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :"+outputXml);
				XMLParser xp  = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode " + mainCode);
				if (mainCode == 0) {
					resp.setStatusCode("0");
					resp.setStatusDescription("Workitem Created Successfully");
					String workItemNumber = xp.getValueOf("ProcessInstanceId");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is " +
					"external table Name : "+processValues.get(0)+
					wiNumber+" TRSD wiName :"+workItemNumber);
					//updating in external table
					APCallCreateXML.APUpdate(processValues.get(0), "TRSD_WI_NAME",
							"'"+workItemNumber+"'", "WI_NAME = '"+wiNumber+"'", sessionId);
                    
					resp.setWiNumber(workItemNumber);
					
					int stageId = 1;
					HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
							(processdefid, sourcingChannel, "1.0", sourcingChannel,stageId);
					int eventID = (Integer) eventMap.keySet().toArray()[0];
					List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
					if(callArray != null) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + workItemNumber + ":" + callArray.toString());
						boolean nogoCall = false;
						for (CallEntity callEntity : callArray) {
							if(callEntity.isMandatory()){
								String outputXML = CallHandler.getInstance().executeCall
										(callEntity, attribMap, sessionId, String.valueOf(eventID), 
												workItemNumber, false);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
										+ outputXML);
								if(!"".equals(outputXML)){
									xp = new XMLParser(outputXML);
									mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
									if(mainCode != 0){
										nogoCall = true;
										resp.setStatusCode("1");
										resp.setStatusDescription("Sanction Screening Failed");
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
				String workItemNumber = trsdChildWi;
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is " + workItemNumber);
				resp.setWiNumber(workItemNumber);
				
				int stageId = 1;
				HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
						(processdefid, sourcingChannel, "1.0", sourcingChannel,stageId);
				int eventID = (Integer) eventMap.keySet().toArray()[0];
				List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
				if(callArray != null) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + workItemNumber + ":" + callArray.toString());
					boolean nogoCall = false;
					for (CallEntity callEntity : callArray) {
						if(callEntity.isMandatory()){
							String outputXML = CallHandler.getInstance().executeCall
									(callEntity, attribMap, sessionId, String.valueOf(eventID), 
											workItemNumber, false);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
									+ wiNumber  + ":" +outputXML);
							if(!"".equals(outputXML)){
								XMLParser xp = new XMLParser(outputXML);
								int mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: mainCode: "
										+ mainCode);
								if(mainCode != 0){
									nogoCall = true;
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
											+ wiNumber  + ":" +outputXML);
									resp.setStatusCode("1");
									resp.setStatusDescription("Sanction Screening Failed");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in wfupload session ID: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			resp.setStatusCode("-1");
		}
		return resp;
	}

}
