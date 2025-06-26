package com.newgen.ao.laps.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.jws.WebService;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.CoreEvent;
import com.newgen.ao.laps.core.IEventHandler;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.LapsAudit;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.SingleUserConnection;

@WebService(serviceName = "BPMModify")
public class BPMModify {

	public LapsModifyRequest request;
	public LapsModifyResponse resp = new LapsModifyResponse();
	private String wiNumber;
	private String mode;
	private String channelName;
	private String channelRefNumber;
	private String correlationNo;
	private String sysrefno;
	private String wiQueue;
	private String processName;
	public ArrayList<CallEntity> callMap;
	private static String sessionid;

	public LapsModifyResponse ModWMSWorkItemReq(LapsModifyRequest request)
			throws FileNotFoundException, IOException, Exception {
		LapsModifyLogger.getInstance().intitalizeLog();
		LapsModifyDBLogger.getInstance();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Started 15/01/2022");
		LapsConfigurations.getInstance().loadConfiguration();
		SingleUserConnection instance = SingleUserConnection.getInstance(1000);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"creating session for : "+request.getChannelRefNumber());
        sessionid = instance.getSession(
				LapsConfigurations.getInstance().CabinetName,
				LapsConfigurations.getInstance().UserName,
				LapsConfigurations.getInstance().Password);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"session created for : "+request.getChannelRefNumber());

		this.request = request;
		this.wiNumber = request.getWiNumber();
		this.channelName = request.getChannelName();
		this.mode=request.getMode();
		this.channelRefNumber = request.getChannelRefNumber();
		this.sysrefno=request.getSysrefno();
		this.correlationNo=request.getCorrelationId();
		this.processName = request.getProcessName();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"BPMModify mode: "+mode+", channelName: "+channelName);
		LapsAudit lapsaudit = new LapsAudit();
		lapsaudit.serviceRequestAuditLog(this.sysrefno, "", "Req");
		lapsaudit = null;
		this.resp=triggerEvent(mode);
		return resp;
	}
	
	private <T> T instantiate(final String className, final Class<T> type) {
		try {
			return type.cast(Class.forName("com.newgen.ao.laps.event."+className+"_Event").newInstance());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return null;
	}

	private LapsModifyResponse triggerEvent(String eventName) throws Exception{
		try{
			IEventHandler event = instantiate(eventName, IEventHandler.class);
			LapsModifyResponse responseObj = event.dispatchEvent(new CoreEvent(sessionid,wiNumber,mode,channelName,
					channelRefNumber,correlationNo,sysrefno,processName,request));
			return responseObj;
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return null;
	}

}
