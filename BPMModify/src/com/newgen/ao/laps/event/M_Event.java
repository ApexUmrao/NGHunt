package com.newgen.ao.laps.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.CoreEvent;
import com.newgen.ao.laps.core.IEventHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.sourcingchannel.SourcingChannelHandler;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class M_Event  implements IEventHandler{

	private String SourcingChannel;
	private String channelRefNumber;
	private String wiNumber;
	private String channelName;
	private String mode;
	private String sysrefno;
	LapsModifyRequest request;
	private String wiQueue;
	public LapsModifyResponse resp = new LapsModifyResponse();
	private static String sessionId;
	public ArrayList<CallEntity> callMap;
	private String correlationNo;
	private String processName;

	@Override
	public LapsModifyResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside M_Event");
//		SingleUserConnection instance = SingleUserConnection.getInstance(1000);
//		sessionid = instance.getSession(
//				LapsConfigurations.getInstance().CabinetName,
//				LapsConfigurations.getInstance().UserName,
//				LapsConfigurations.getInstance().Password);
		request = paramCoreEvent.getRequest();
		SourcingChannel = paramCoreEvent.getChannelName();
		wiNumber = paramCoreEvent.getWiNumber();
		channelRefNumber = paramCoreEvent.getChannelRefNumber();
		channelName = paramCoreEvent.getChannelName();
		mode = paramCoreEvent.getMode();
		sysrefno = paramCoreEvent.getSysrefno();
		correlationNo = paramCoreEvent.getCorrelationNo();
		processName = paramCoreEvent.getProcessName();
		sessionId = paramCoreEvent.getSessionId();
		List<String> channelList = LapsUtils.getInstance().getChannelNameList();
		boolean isChannelExist=channelList.contains(SourcingChannel);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"channelList channelList"+channelList);
		HashMap<String, String> attributeMap = new HashMap<String, String>();
		if (paramCoreEvent.getChannelName() != null)
			attributeMap.put("channelName", SourcingChannel);

		if (paramCoreEvent.getChannelRefNumber() != null)
			attributeMap.put("channelRefNumber", channelRefNumber);
		attributeMap.put("correlationNo", correlationNo);
		String statusCode = LapsUtils.getInstance()
				.checkMandatoryAndValidation(attributeMap);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"attributeMap bef" + attributeMap);

		String[] status = statusCode.split(";");
		resp.setChannelName(SourcingChannel);
		resp.setChannelRefNumber(channelRefNumber);
		if (isChannelExist==true){
			if(SourcingChannel.equalsIgnoreCase("PROTRADE")){
				resp = triggerEvent();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"M_Event response from M_PROTRADE: "
						+resp.toString());
				return resp;
			} else if(SourcingChannel.equalsIgnoreCase("TSLMSY")){
				resp = triggerEvent();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"M_Event response from M_TSLMSY: "
						+resp.toString());
				return resp;
			}
			// Gurwinder For UAEPASS AO 19052023
			else if(SourcingChannel.equalsIgnoreCase("UAEPASS")){
				resp = triggerEvent();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"M_Event response from M_UAEPASS: "
						+resp.toString());
				return resp;
			}
			// Gurwinder For UAEPASS AO 19052023
			else{
				if(SourcingChannel.equals("LAPS")){
					SourcingChannel=SourcingChannel+"MODIFY";
					if (status[0].equals("101")) {
						resp.setStatusCode(status[0]);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
								"attributeMap" + attributeMap);

						resp.setStatusDescription("Mandatory Field " + status[1]
								+ " is missing.");
					} else if (!status[0].equals("101")) {
						if (wiNumber.equals("")) {
							String outputXML = APCallCreateXML
									.APSelect("select WI_NAME FROM EXT_AO WHERE channel_ref_number='"
											+ channelRefNumber + "'");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
									"outputxml issss " + outputXML);
							XMLParser xp = new XMLParser(outputXML);
							int start = xp.getStartIndex("Records", 0, 0);
							int deadEnd = xp.getEndIndex("Records", start, 0);
							int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							int end = 0;

							for (int i = 0; i < noOfFields; ++i) {

								start = xp.getStartIndex("Record", end, 0);
								end = xp.getEndIndex("Record", start, 0);
								wiNumber = xp.getValueOf("WI_NAME", start, end);
							}

						}
						if (wiNumber.equals("")) {
							resp.setStatusCode("99");
							resp.setStatusDescription("No Data Found");
						}

						else {
							String outputXML = APCallCreateXML.APSelect("select ACTIVITYNAME from WFINSTRUMENTTABLE where PROCESSINSTANCEID ='"+wiNumber+"'");
							XMLParser xp = new XMLParser(outputXML);
							int start = xp.getStartIndex("Records", 0, 0);
							int deadEnd = xp.getEndIndex("Records", start, 0);
							int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							int end = 0;

							for (int i = 0; i < noOfFields; ++i) {

								start = xp.getStartIndex("Record", end, 0);
								end = xp.getEndIndex("Record", start, 0);
								wiQueue = xp.getValueOf("ACTIVITYNAME", start, end);
							}

							resp.setWiQueue(wiQueue);


							String AccNo = "";
							String callStatus = "";
							outputXML = APCallCreateXML
									.APSelect("select usr_0_product_selected.ACC_NO,usr_0_laps_call_out.CALL_STATUS from usr_0_product_selected join usr_0_laps_call_out on usr_0_product_selected.wi_name=usr_0_laps_call_out.wi_name  where usr_0_laps_call_out.wi_name='"
											+ wiNumber
											+ "' and usr_0_laps_call_out.call_name='CreateCASA'");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
									"outputxml issss " + outputXML);
							xp = new XMLParser(outputXML);
							start = xp.getStartIndex("Records", 0, 0);
							deadEnd = xp.getEndIndex("Records", start, 0);
							noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							end = 0;

							for (int i = 0; i < noOfFields; ++i) {
								start = xp.getStartIndex("Record", end, 0);
								end = xp.getEndIndex("Record", start, 0);
								AccNo = xp.getValueOf("ACC_NO", start, end);
								callStatus = xp.getValueOf("CALL_STATUS", start, end);
							}
							if (!AccNo.equalsIgnoreCase("")
									&& callStatus.equalsIgnoreCase("Y")) {
								resp.setStatusCode("112");
								resp.setStatusDescription("Workitem Cannot be cancelled. Account is already created.");

							}

							else {
								outputXML = ProdCreateXML.WMGetWorkItem(sessionId,
										wiNumber, 1);
								xp = new XMLParser(outputXML);
								String mainCode = xp.getValueOf("Status");
								if (mainCode.equals("16")) {
									resp.setStatusCode("108");
									resp.setStatusDescription("Workitem is already locked");

								} else {
									APCallCreateXML.APUpdate("EXT_AO", "NXT_WS_NAME",
											"'Work Exit1'", "wi_name='" + wiNumber + "'",
											sessionId);
									outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId,
											wiNumber, 1);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
											"outputxm l complete WI issss " + outputXML);

									callMap = LapsUtils.getInstance()
											.getCallNameFromChannel(SourcingChannel);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
											"Started2" + callMap.get(0).getCallName()
											+ " ss " + callMap.get(0).getCallID()
											+ " ss "
											+ callMap.get(0).getExecutionType());
									if(callMap != null) {
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, callMap.toString());
										for (CallEntity callEntity : callMap) {
											outputXML = CallHandler.getInstance().executeCall(callEntity, 
													attributeMap, sessionId, "", wiNumber, false);
										}
									}
									resp.setWiNumber(wiNumber);
									resp.setStatusCode("0");
									resp.setStatusDescription("Success");
								}
							}
						}
					}
				}
			}

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
			responseObj = mEvent.dispatchEvent(sessionId,channelRefNumber,correlationNo,SourcingChannel,mode,wiNumber,processName);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return responseObj;
	}
}
