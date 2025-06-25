package com.newgen.ao.laps.event;

import java.util.List;

import com.newgen.ao.laps.core.CoreEvent;
import com.newgen.ao.laps.core.IEventHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.sourcingchannel.SourcingChannelHandler;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class D_Event  implements IEventHandler{
	
	private String SourcingChannel;
	private String channelRefNumber;
	private String channelName;
	private String mode;
	private String correlationId;
	private String wiNumber;
	private String processName;
	private static String sessionId;
	public LapsModifyResponse resp = new LapsModifyResponse();
	
	@Override
	public LapsModifyResponse dispatchEvent(CoreEvent paramCoreEvent)
			throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside D_Event");
		
		SourcingChannel=paramCoreEvent.getChannelName();
		channelName = paramCoreEvent.getChannelName();
		mode = paramCoreEvent.getMode();
		correlationId = paramCoreEvent.getCorrelationNo();
		wiNumber = paramCoreEvent.getWiNumber();
		processName = paramCoreEvent.getProcessName();
		sessionId = paramCoreEvent.getSessionId();
		List<String> channelList = LapsUtils.getInstance().getChannelNameList();
		boolean isChannelExist=channelList.contains(SourcingChannel);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"channelList channelList"+channelList);
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
		if (isChannelExist==true){
			if(SourcingChannel.equalsIgnoreCase("PROTRADE")) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"IF D_Event PROTRADE");
				resp = triggerEvent();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"D_Event response from D_PROTRADE: "
						+resp.toString());
				return resp;
			}else if (SourcingChannel.equalsIgnoreCase("TSLMSY")) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"IF D_Event TSLM");
				resp = triggerEvent();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"D_Event response from D_PROTRADE: "
						+resp.toString());
				return resp;
			} else if(SourcingChannel.equalsIgnoreCase("TRADE")) {
				if(null != wiNumber && !wiNumber.isEmpty()) {
					String outputXml = APCallCreateXML.APSelect("select CURR_WS "
							+ "from EXT_TFO where WI_NAME='"+wiNumber+"'");
					XMLParser xp = new XMLParser(outputXml);
					int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
						if("ToDoList".equalsIgnoreCase(xp.getValueOf("CURR_WS"))) {
							outputXml = ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, 1);
							xp = new XMLParser(outputXml);
							if(Integer.parseInt(xp.getValueOf("MainCode")) == 0) {
								resp.setStatusCode("0");
								resp.setStatusDescription("Workitem"+wiNumber+" discarded");
							} else {
								resp.setStatusCode("-1");
								resp.setStatusDescription("Discarding workitem failed");
							}
						} else {
							resp.setStatusCode("-1");
							resp.setStatusDescription("Workitem: "+wiNumber+" not at ToDoList");
						}
					}
				} else {
					resp.setStatusCode("-1");
					resp.setStatusDescription("Invalid Workitem Number");
				}
			}
		} else {
			resp.setStatusCode("1");
			resp.setStatusDescription("Channel does not exist.");
		}
		return resp;
	}
	
	private <T> T instantiate(final String channelName, String mode) {
		try {
			return (T) Class.forName("com.newgen.ao.laps.sourcingchannel."+mode+"_"+channelName).newInstance();
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return null;
	}

	private LapsModifyResponse triggerEvent() throws Exception{
		LapsModifyResponse responseObj = new LapsModifyResponse();
		try{
			SourcingChannelHandler dEvent = (SourcingChannelHandler) instantiate(channelName, mode);
			responseObj = dEvent.dispatchEvent(sessionId,channelRefNumber,correlationId,SourcingChannel,mode,wiNumber,processName);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return responseObj;
	}
}
