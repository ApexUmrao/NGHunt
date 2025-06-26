package com.newgen.ao.laps.core;

import java.util.HashMap;

import com.newgen.ao.laps.request.LapsModifyRequest;

public class CoreEvent {
	private String wiNumber;
	private String mode;
	private String channelName;
	private String channelRefNumber;
	private HashMap<String, String> attributeMap;
	private String correlationNo;
	private String sysrefno;
	private String processName;
	private String sessionId;
	public LapsModifyRequest request;
	public CoreEvent(String sessionId, String wiNumber, String mode, String channelName,
			String channelRefNumber, String correlationNo, String sysrefno,String processName,
			LapsModifyRequest request) {
		super();
		this.wiNumber = wiNumber;
		this.mode = mode;
		this.channelName = channelName;
		this.channelRefNumber = channelRefNumber;
		this.correlationNo = correlationNo;
		this.sysrefno = sysrefno;
		this.request = request;
		this.processName = processName;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
	}
	public String getWiNumber() {
		return wiNumber;
	}
	public void setWiNumber(String wiNumber) {
		this.wiNumber = wiNumber;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelRefNumber() {
		return channelRefNumber;
	}
	public void setChannelRefNumber(String channelRefNumber) {
		this.channelRefNumber = channelRefNumber;
	}
	public String getCorrelationNo() {
		return correlationNo;
	}
	public void setCorrelationNo(String correlationNo) {
		this.correlationNo = correlationNo;
	}
	public String getSysrefno() {
		return sysrefno;
	}
	public void setSysrefno(String sysrefno) {
		this.sysrefno = sysrefno;
	}
	public LapsModifyRequest getRequest() {
		return request;
	}
	public void setRequest(LapsModifyRequest request) {
		this.request = request;
	}
	public HashMap<String, String> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(HashMap<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String processName) {
		this.sessionId = sessionId;
	}
}
