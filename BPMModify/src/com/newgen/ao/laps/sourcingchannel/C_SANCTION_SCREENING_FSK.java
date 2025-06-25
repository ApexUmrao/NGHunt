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
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_SANCTION_SCREENING_FSK implements SourcingChannelHandler
{
	private int processDefId;
	String initActivityName="";
	String outputXML="";
	int stageId = 1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	private Map<String,HashMap<Integer, String>> processInitMap= null ;
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	ArrayList<String> processValues = new  ArrayList<String>();
	public LapsModifyResponse resp = new LapsModifyResponse();
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) 
	{
		
		processInitMap = ChannelCallCache.getInstance().getProcessInitMap();
		HashMap<Integer, String> processInitValues = new HashMap<Integer, String>();
		processInitValues = processInitMap.get("SS");
		for(Map.Entry<Integer, String> entryInner: processInitValues.entrySet()){
			processDefId = entryInner.getKey();
			initActivityName = entryInner.getValue();
		}
		attributeMap.put("REQUESTOR_PROCESS_NAME", processName);
		attributeMap.put("REQUESTOR_WI_NAME", wiNumber);
		attributeMap.put("correlationNo", correlationId);
		attributeMap.put("channelRefNumber", channelRefNumber);
		
	try {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_SANCTION_SCREENING_FSK");
		//processDefId = LapsConfigurations.getInstance().processDefIdTFO;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_SANCTION_SCREENING_FSK processDefId  ::: " + processDefId);
		HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
				(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_SANCTION_SCREENING_FSK sourcingChannel  ::: " + sourcingChannel);

		int eventID = (Integer) eventMap.keySet().toArray()[0];
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_SANCTION_SCREENING_FSK eventID  ::: " + eventID);

		List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "C_SANCTION_SCREENING_FSK callArray: " + callArray);
		XMLParser xp; 
		if(callArray != null) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "C_SANCTION_SCREENING_FSK Call_Array: " + wiNumber + ":" + callArray.toString());
			boolean nogoCall = false;
			for (CallEntity callEntity : callArray) {
				if(callEntity.isMandatory()){
					outputXML = CallHandler.getInstance().executeCall(callEntity, attributeMap, sessionId,
									String.valueOf(eventID),wiNumber, false);
					xp = new XMLParser(outputXML);
					if("0".equalsIgnoreCase(status)){
						status = xp.getValueOf("returnCode");
					}
					if(!"0".equals(status)){
						statusDesc = xp.getValueOf("errorDescription");
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_SANCTION_SCREENING_FSK Execute Call: outputxml: "
							+ outputXML);
				}
			}
		}

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_SANCTION_SCREENING_FSK: status= "+status+"statusDesc= "
				+statusDesc);
		resp.setWiNumber(wiNumber);
		resp.setStatusCode(status);
		resp.setStatusDescription(statusDesc);
		} 
		catch (Exception e)
		{
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_SANCTION_SCREENING_FSK dispatchEvent: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}
}

