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

public class C_DOCUMENT_TFO implements SourcingChannelHandler {
	
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attribMap = new HashMap<String, String>();
	private int processdefid;
	XMLParser xp=new XMLParser(); //ATP-256
	int stageId = 1;
	int mainCode = -1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_DOCUMENT_TFO BPMModify");

		attribMap.put("REQUESTOR_PROCESS", processName);
		attribMap.put("REQUESTOR_WI_NAME", wiNumber);
		attribMap.put("CONTRACT_REF_NO", channelRefNumber);

		processdefid = LapsConfigurations.getInstance().processDefIdTFO;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processdefid TFO " + processdefid);
		try {
					HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
							(processdefid, sourcingChannel, "1.0", sourcingChannel,stageId);
					int eventID = (Integer) eventMap.keySet().toArray()[0];
					List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
					if(callArray != null) {
				        LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
						for (CallEntity callEntity : callArray) {
							if(callEntity.isMandatory()){
								String outputXML = CallHandler.getInstance().executeCall
										(callEntity, attribMap, sessionId, String.valueOf(eventID), 
												wiNumber, false);
						XMLParser xp = new XMLParser(outputXML);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "+ outputXML);
						//ATP-256
						if("0".equalsIgnoreCase(status)){
							status = xp.getValueOf("returnCode");
								}
						if(!"0".equals(status)){
							statusDesc = xp.getValueOf("errorDescription");
						}
						
							}
						}
					}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_DOCUMENT_TFO: status= "+status+"statusDesc= "
					+statusDesc);
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
				} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_DOCUMENT_TFO dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returning response from C_DOCUMENT_TFO");
		return resp;
	}

}
