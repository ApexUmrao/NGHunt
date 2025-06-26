package com.newgen.ao.laps.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.CoreEvent;
import com.newgen.ao.laps.core.IEventHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.sourcingchannel.SourcingChannelHandler;
import com.newgen.ao.laps.util.LapsUtils;

public class R_Event implements IEventHandler{

	private String  sourcingChannel;
	private String channelRefNumber;
	private String correlationId;
	private String mode;
	private String channelName;
	private String wiNumber;
	private String processName;
	private HashMap<String, String> attributeMap;
	private static String sessionId;
	public LapsModifyRequest request;
	private ArrayList<CallEntity> callMap;
	public LapsModifyResponse resp = new LapsModifyResponse();

	@Override
	public LapsModifyResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside R_Event");
		List<String> channelList = LapsUtils.getInstance().getChannelNameList();
		sourcingChannel = paramCoreEvent.getChannelName();
		attributeMap = paramCoreEvent.getAttributeMap();
		mode = paramCoreEvent.getMode();
		channelName = paramCoreEvent.getChannelName();
		channelRefNumber = paramCoreEvent.getChannelRefNumber();
		correlationId = paramCoreEvent.getCorrelationNo();
		wiNumber = paramCoreEvent.getWiNumber();
		processName = paramCoreEvent.getProcessName();
		int mainCode = -1;
		boolean isChannelExist = channelList.contains(sourcingChannel);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ChannelList: "+channelList);
//		SingleUserConnection instance = SingleUserConnection.getInstance(1000);
//		sessionId = instance.getSession(
//				LapsConfigurations.getInstance().CabinetName,
//				LapsConfigurations.getInstance().UserName,
//				LapsConfigurations.getInstance().Password);
		sessionId = paramCoreEvent.getSessionId();
		if (isChannelExist==true){
			resp = triggerEvent();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"R_Event response from C_"+channelName+": "
					+resp.toString());
			return resp;
		}
		else{
			resp.setStatusCode("1");
			resp.setStatusDescription("Channel does not exist.");
		}
		return resp;
	}
	
	private String getDate(){
		String today = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		today = formatter.format(date);
		return today;	
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
			SourcingChannelHandler cEvent = (SourcingChannelHandler) instantiate(channelName, mode);
			responseObj = cEvent.dispatchEvent(sessionId,channelRefNumber,correlationId,sourcingChannel,mode,wiNumber,
					processName);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return responseObj;
	}

}
