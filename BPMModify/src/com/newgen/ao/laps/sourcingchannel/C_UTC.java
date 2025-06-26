package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_UTC implements SourcingChannelHandler {
	int processdefid ;
	HashMap<String, String> attribMap = new HashMap<String, String>();
	ArrayList<String> processValues = new  ArrayList<String>();
	public LapsModifyResponse resp = new LapsModifyResponse();
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_UTC BPMModify");

		attribMap.put("REQUESTOR_PROCESS", processName);
		attribMap.put("REQUESTOR_WI_NAME", wiNumber);
		processdefid = LapsConfigurations.getInstance().processDefIdTFO;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processdefid TFO " + processdefid);
		try {
					int stageId = 1;
					HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
							(processdefid, sourcingChannel, "1.0", sourcingChannel,stageId);
					int eventID = (Integer) eventMap.keySet().toArray()[0];
					List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID:123 " + eventID);
					if(callArray != null) {
						for (CallEntity callEntity : callArray) {
							if(callEntity.isMandatory()){
								String outputXML = CallHandler.getInstance().executeCall
										(callEntity, attribMap, sessionId, String.valueOf(eventID), 
												wiNumber, false);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute UTC Call:123 outputxml: "
										+ outputXML);
								XMLParser sxp = new XMLParser(outputXML);
								int returnCode = Integer.parseInt(sxp.getValueOf("returnCode"));
								String errorDesc = sxp.getValueOf("errorDescription");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returnCode "+ returnCode);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errorDesc "+ errorDesc);

								if(returnCode == 0){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside success ");
									resp.setStatusCode("0");
									resp.setStatusDescription(errorDesc);
									resp.setWiNumber("");
								} else {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside failure ");
									resp.setStatusCode("-1");
									resp.setStatusDescription(errorDesc);
									resp.setWiNumber("");
								}
							}
						}
					}
				} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in wfupload session ID:123 ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			resp.setStatusCode("-1");
			resp.setStatusDescription(e.toString());

		}
		return resp;
	}

}
