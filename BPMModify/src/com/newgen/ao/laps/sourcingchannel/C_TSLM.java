package com.newgen.ao.laps.sourcingchannel;

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

public class C_TSLM implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private int processDefId;
	int stageId = 1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) {
		resp.setChannelRefNumber(channelRefNumber);
		resp.setCorrelationId(correlationId);
	try {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_TSLM");
		processDefId = LapsConfigurations.getInstance().processDefIdTFO;
		attributeMap.put("mode",mode);
		HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
				(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
		int eventID = (Integer) eventMap.keySet().toArray()[0];
		List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
		XMLParser xp; 
		if(callArray != null) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
			boolean nogoCall = false;
			for (CallEntity callEntity : callArray) {
				if(callEntity.isMandatory()){
					String outputXML = CallHandler.getInstance().executeCall(callEntity, attributeMap, sessionId,
									String.valueOf(eventID),wiNumber, false);
					xp = new XMLParser(outputXML);
					if("0".equalsIgnoreCase(status)){
						status = xp.getValueOf("returnCode");
					}
					if(!"0".equals(status)){
						statusDesc = xp.getValueOf("errorDescription");
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
							+ outputXML);
				}
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_TSLM: status= "+status+"statusDesc= "
				+statusDesc);
		resp.setWiNumber(wiNumber);
		resp.setStatusCode(status);
		resp.setStatusDescription(statusDesc);
		} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM dispatchEvent: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}
}

