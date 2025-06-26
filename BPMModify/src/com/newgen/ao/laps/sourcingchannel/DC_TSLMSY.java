package com.newgen.ao.laps.sourcingchannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class DC_TSLMSY implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	public LapsModifyRequest request;
	private int processDefId;
	XMLParser xp=new XMLParser();
	int stageId = 2;
	int mainCode = -1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside DC_TSLMSY >>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_TSLMSY mode: "+mode
					+", sourcingChannel: "+sourcingChannel);
			
			 String outputxml = APCallCreateXML.APSelect("SELECT CORRELATIONID FROM EXT_TFO WHERE WI_NAME = N'" + wiNumber + "'");
				XMLParser xp = new XMLParser(outputxml);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_TSLMSY TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
						channelRefNumber = xp.getValueOf("CORRELATIONID");		
					}
				}			
			processDefId = LapsConfigurations.getInstance().processDefIdTFO;
			String requestCategory;
			String requestType;
			String processType;
			Map<String, String> fetchMap = ChannelCallCache.getInstance().getFetchMap();
			attributeMap.put("mode", mode);
			attributeMap.put("ruleFlag", "Y");
			attributeMap.put("channelRefNo", channelRefNumber);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_TSLMSY attributeMap: "+attributeMap.toString());
			HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
					(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
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
							statusDesc = xp.getValueOf("errorDescription");
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
								+ outputXML);
					}
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DC_TSLMSY: status= "+status+"statusDesc= "
					+statusDesc);
                        //ATP-265 Date 12-12-2023 By Reyaz
			String outputXML1 = APCallCreateXML.APSelect("SELECT Count(1) as Count FROM BPM_ORCHESTRATION_STATUS WHERE WI_NAME = N'" + wiNumber + "' and CALL_STATUS IN ('N','F')");
			XMLParser xp1 = new XMLParser(outputXML1);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML1 ::: "+outputXML1);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode1);			
			if(mainCode1 == 0){
				if(Integer.parseInt(xp1.getValueOf("Count")) > 0){
					status ="1";
					statusDesc ="Calls Not Executed Successfully";
				}
			}
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
			
			/*
			 * APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP, CURR_WS",
			 * "'PP_MAKER','Initiator'", "WI_NAME = '"+wiNumber+"'", sessionId);
			 * ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, 1);
			 */

		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in DC_TSLMSY dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returning response from DC_TSLMSY");
		return resp;
	}

}
