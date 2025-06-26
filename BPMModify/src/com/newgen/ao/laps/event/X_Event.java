package com.newgen.ao.laps.event;

import java.util.List;

import com.newgen.ao.laps.core.CoreEvent;
import com.newgen.ao.laps.core.IEventHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.sourcingchannel.SourcingChannelHandler;
import com.newgen.ao.laps.util.LapsUtils;

public class X_Event implements IEventHandler{

	private String  sourcingChannel;
	private String channelRefNumber;
	private String correlationId;
	private String mode;
	private String channelName;
	private String wiNumber;
	private String processName;
	private String sessionId;
	public LapsModifyRequest request;
	public LapsModifyResponse resp = new LapsModifyResponse();

	@Override
	public LapsModifyResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside X_Event >>");
		List<String> channelList = LapsUtils.getInstance().getChannelNameList();
		sourcingChannel = paramCoreEvent.getChannelName();
		mode = paramCoreEvent.getMode();
		channelName = paramCoreEvent.getChannelName();
		channelRefNumber = paramCoreEvent.getChannelRefNumber();
		correlationId = paramCoreEvent.getCorrelationNo();
		wiNumber = paramCoreEvent.getWiNumber();
		processName = paramCoreEvent.getProcessName();
		sessionId = paramCoreEvent.getSessionId();
		boolean isChannelExist = channelList.contains(sourcingChannel);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ChannelList: "+channelList);
		if (isChannelExist == true){
			resp = triggerEvent();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"X_Event response from X_"+sourcingChannel+": "
					+resp.toString());
			return resp;
		}
		else{
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
			SourcingChannelHandler mEvent = (SourcingChannelHandler) instantiate(channelName, mode);
			responseObj = mEvent.dispatchEvent(sessionId,channelRefNumber,correlationId,sourcingChannel,mode,wiNumber,processName);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return responseObj;
	}

}
