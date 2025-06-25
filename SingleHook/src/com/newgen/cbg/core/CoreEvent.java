package com.newgen.cbg.core;

import com.newgen.cbg.request.CBGSingleHookRequest;

public class CoreEvent {
	
	private CBGSingleHookRequest request;
	private String sessionId;
	private String stageId;
	private String WI_NAME;
	private String sysRefNo;
	private String language;
	private String applicationName;
	private String requestedDateTime;
	private String deviceID;
	private String deviceIP;
	private String OSType;
	private String OSVersion;
	private String AppVersion;
	private String DeviceModal;
	private String sourcingChannel;
	private String sourcingCenter;
	private String applicationJourney;
	private String applicationVersion;
	
	public String getDeviceIP() {
		return deviceIP;
	}

	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getOSType() {
		return OSType;
	}

	public void setOSType(String oSType) {
		OSType = oSType;
	}

	public String getOSVersion() {
		return OSVersion;
	}

	public void setOSVersion(String oSVersion) {
		OSVersion = oSVersion;
	}

	public String getAppVersion() {
		return AppVersion;
	}

	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}

	public String getDeviceModal() {
		return DeviceModal;
	}

	public void setDeviceModal(String deviceModal) {
		DeviceModal = deviceModal;
	}

	public String getRequestedDateTime() {
		return requestedDateTime;
	}

	public void setRequestedDateTime(String requestedDateTime) {
		this.requestedDateTime = requestedDateTime;
	}

	public String getSourcingChannel() {
		return sourcingChannel;
	}

	public void setSourcingChannel(String sourcingChannel) {
		this.sourcingChannel = sourcingChannel;
	}

	public String getSourcingCenter() {
		return sourcingCenter;
	}

	public void setSourcingCenter(String sourcingCenter) {
		this.sourcingCenter = sourcingCenter;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSysRefNo() {
		return sysRefNo;
	}

	public void setSysRefNo(String sysRefNo) {
		this.sysRefNo = sysRefNo;
	}

	public String getWI_NAME() {
		return WI_NAME;
	}

	public void setWI_NAME(String wI_NAME) {
		WI_NAME = wI_NAME;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getApplicationJourney() {
		return applicationJourney;
	}

	public void setApplicationJourney(String applicationJourney) {
		this.applicationJourney = applicationJourney;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public CoreEvent(CBGSingleHookRequest request, String sessioId, String stageId, String WI_NAME, String sysRefNo, String language, 
			String applicationName, String requestedDateTime, String sourcingCenter, String sourcingChannel, String deviceID, String deviceIP,
			String OSType, String OSVersion, String AppVersion, String DeviceModal, String applicationJourney, String applicationVersion){
		this.request = request;
		this.sessionId = sessioId;
		this.stageId = stageId;
		this.WI_NAME = WI_NAME;
		this.sysRefNo = sysRefNo;
		this.language = language;
		this.applicationName = applicationName;
		this.requestedDateTime = requestedDateTime;
		this.sourcingCenter = sourcingCenter;
		this.sourcingChannel = sourcingChannel;
		this.deviceID=deviceID;
		this.deviceIP=deviceIP;
		this.OSType=OSType;     
		this.OSVersion=OSVersion;  
		this.AppVersion=AppVersion; 
		this.DeviceModal=DeviceModal;
		this.applicationJourney=applicationJourney;
		this.applicationVersion=applicationVersion;
	}
	
	public CBGSingleHookRequest getRequest() {
		return request;
	}

	public void setRequest(CBGSingleHookRequest request) {
		this.request = request;
	}
}