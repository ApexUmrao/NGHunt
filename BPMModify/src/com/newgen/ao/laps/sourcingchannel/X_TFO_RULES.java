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

public class X_TFO_RULES implements SourcingChannelHandler{
	
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	private int processDefId;
	
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId, String sourcingChannel, 
			String mode, String wiNumber, String processName) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside X_TFO_RULES wiNumber: "+wiNumber);
		processDefId = LapsConfigurations.getInstance().processDefIdTFO;
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
		resp.setChannelRefNumber(channelRefNumber);
		resp.setCorrelationId(correlationId);
		if(wiNumber != null && !wiNumber.isEmpty()){
			try {
				attributeMap.put("mode", mode);
				attributeMap.put("ruleFlag", "Y");
				String status = "0";
				String statusDesc = "Workitem Routed Successfully";
				HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
						(processDefId, sourcingChannel, "1.0", sourcingChannel,1);
				int eventID = (Integer) eventMap.keySet().toArray()[0];
				List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
				if(callArray != null) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array- wiName= " + wiNumber 
							+ ":" + callArray.toString());
					boolean nogoCall = false;
					for (CallEntity callEntity : callArray) {
						if(callEntity.isMandatory()){
							String outputXML = CallHandler.getInstance().executeCall
									(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
											wiNumber, false);
							XMLParser xp = new XMLParser(outputXML);
							status = xp.getValueOf("returnCode");
							if(!"0".equals(status)){
								statusDesc = xp.getValueOf("errorDescription");
							}
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call "
									+ "from X_TFO_RULES outputxml: " + outputXML);
						}
					}
				}
				resp.setWiNumber(wiNumber);
				resp.setStatusCode(status);
				resp.setStatusDescription(statusDesc);
			} catch (Exception e) {
				resp.setStatusCode("-1");
				resp.setStatusDescription("Workitem Not Routed to Target Queue");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in X_TFO_RULES: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		} else {
			resp.setStatusCode("-1");
			resp.setStatusDescription("Mandatory Data Missing");
		}
		return resp;
	}

}
