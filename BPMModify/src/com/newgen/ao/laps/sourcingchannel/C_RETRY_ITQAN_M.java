package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_RETRY_ITQAN_M  implements SourcingChannelHandler{
	private int processDefId;
	private String WI_NAME;
	int stageId = 1;
	String status = "0";
	int mainCode;
	HashMap<String, String> attribMap = new HashMap<String, String>();
	private Map<String,HashMap<Integer, String>> processInitMap;
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	ArrayList<String> processValues = new  ArrayList<String>();
	public LapsModifyResponse resp = new LapsModifyResponse();
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_RETRY_ITQAN_M");

		attribMap.put("REQUESTOR_PROCESS_NAME", processName);
		attribMap.put("REQUESTOR_WI_NAME", wiNumber);
		processDefId=8;
		String outputXML;
		XMLParser xp;
		String statusDesc = "Calls Executed Successfully";
		try {
			HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
					(processDefId, sourcingChannel, "1", sourcingChannel,stageId);
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);

			if(callArray != null) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
				boolean nogoCall = false;
				for (CallEntity callEntity : callArray) {
					if(callEntity.isMandatory()){
						outputXML = CallHandler.getInstance().executeCall(callEntity, attribMap, sessionId,
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_ITQAN_M: status= "+status+"statusDesc= "+statusDesc);
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
			///}




		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_RETRY_ITQAN_M dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

		return resp;
	}


}



