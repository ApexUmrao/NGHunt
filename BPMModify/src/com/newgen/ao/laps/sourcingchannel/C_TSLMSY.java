package com.newgen.ao.laps.sourcingchannel;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_TSLMSY implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private int processDefId;
	int stageId = 1;
	String status = "0";
	String workItemNumber ="";
	String statusDesc = "Calls Executed Successfully";
	HashMap<String, String> attribMap = new HashMap<String, String>();
	private String statusDescription;
	private String statusCode;
	
	private HashMap<String, String> attributeMap = new HashMap<String, String>();


	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) {
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_TSLM_SCF");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_TSLM_SCF  ProcessName" +processName);

		try {
			    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try C_TSLM_SCF");
			    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try C_TSLM_SCF channelRefNumber :"+channelRefNumber);
				attributeMap.put("channelRefNumber", channelRefNumber);
				attributeMap.put("correlationId", correlationId);
				attributeMap.put("processName", processName);
				attributeMap.put("sourcingChannel", sourcingChannel);
			    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try C_TSLM_SCF attributeMap :"+attributeMap.get("channelRefNumber"));
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

				String outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "N", attributeMap,"",processDefId,"Initiator");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_TSLM_SCF outputXml :"+outputXml);
				XMLParser xp = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode " + mainCode);

				if (mainCode == 0) {
					statusCode ="0";
					statusDescription ="Workitem Created Successfully";
					workItemNumber = xp.getValueOf("ProcessInstanceId");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "workItemNumber is " +workItemNumber);
					//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sleep started at : " +java.time.LocalDateTime.now());//Added by satyansh for UAT only
					//Thread.sleep(13000);
					//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sleep end at : " +java.time.LocalDateTime.now());
					HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
								(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventMap.toString(): " + eventMap.toString());
					int eventID = (Integer) eventMap.keySet().toArray()[0];
					List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);

						if(callArray != null) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: C_TSLM_SCF " + workItemNumber + ":" + callArray.toString());
							for (CallEntity callEntity : callArray) {
								if(callEntity.isMandatory()){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callEntity C_TSLM_SCF " );
									new Thread(new Runnable(){
										
										public void run(){
										try {
											CallHandler.getInstance().executeCall(callEntity, attributeMap, sessionId,
													String.valueOf(eventID),workItemNumber, false);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in CompletableFuture.runAsync ");
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
										}}
									}).start();
									//String outputXML = CallHandler.getInstance().executeCall(callEntity, attributeMap, sessionId,
											//String.valueOf(eventID),workItemNumber, false);
/*									xp = new XMLParser(outputXML);
									if("0".equalsIgnoreCase(status)){
										status = xp.getValueOf("returnCode");
									}
									if(!"0".equals(status)){
										statusDesc = xp.getValueOf("errorDescription");
									}
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
											+ outputXML);*/
								}
							}
						}
						
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_TSLM_SCF:  statusDesc= "+statusDesc);
				} else {
					statusDescription ="Workitem Not Created ";
				}
				
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM_SCF dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +workItemNumber);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +statusCode);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number : " +statusDescription);
		resp.setWiNumber(workItemNumber);
		resp.setStatusCode(statusCode); 
		resp.setStatusDescription(statusDescription); 
		resp.setChannelRefNumber(channelRefNumber);
		resp.setCorrelationId(correlationId);
		return resp;
	}
	
}

