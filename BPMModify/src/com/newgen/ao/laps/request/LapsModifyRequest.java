package com.newgen.ao.laps.request;

public class LapsModifyRequest {
	private String wiNumber;
	private String mode;
	private String userId;
	private String channelName;
	private String channelRefNumber;
	private String sysrefno;
	private String processName;
	private String correlationId;
	private String ip;
	private String deviceId;
	private String language;
	private String requestDateTime;
	private String version;
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
	}public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getSysrefno() {
		return sysrefno;
	}
	public void setSysrefno(String sysrefno) {
		this.sysrefno = sysrefno;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public String getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(String requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
