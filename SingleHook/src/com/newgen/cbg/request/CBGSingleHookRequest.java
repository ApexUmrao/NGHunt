package com.newgen.cbg.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Request", propOrder={"SYSREFNO","MODE", "WI_NAME", "RequestDateTime", "Stage","SourcingChannel", "SourcingCenter", "DeviceID", 
		"IP", "OSType", "OSVersion", "AppVersion", "DeviceModal", "ApplicationName", "Language", "LeadNumber", "ApplicationVersion", "ApplicationJourney", "ApplicationAttributes"})
public class CBGSingleHookRequest {

	@XmlElement(name="MODE", required=true)
	private String  SYSREFNO;
	
	@XmlElement(name="MODE", required=true)
	private String  MODE;

	@XmlElement(name="WI_NAME", required=true)
	private String  WI_NAME;

	@XmlElement(name="RequestDateTime", required=true)
	private String  RequestDateTime;

	@XmlElement(name="Stage", required=true)
	private String  Stage;

	@XmlElement(name="SourcingChannel", required=true)
	private String  SourcingChannel;

	@XmlElement(name="SourcingCenter", required=true)
	private String  SourcingCenter;

	@XmlElement(name="DeviceID", required=true)
	private String  DeviceID;

	@XmlElement(name="IP", required=true)
	private String  IP;
	
	@XmlElement(name="OSType", required=false)
	private String  OSType;
	
	@XmlElement(name="OSVersion", required=false)
	private String  OSVersion;
	
	@XmlElement(name="AppVersion", required=false)
	private String  AppVersion;
	
	@XmlElement(name="DeviceModal", required=false)
	private String  DeviceModal;
	
	@XmlElement(name="ApplicationName", required=true)
	private String  ApplicationName;

	@XmlElement(name="Language", required=true)
	private String  Language;

	@XmlElement(name="LeadNumber", required=false)
	private String  LeadNumber;
	
	@XmlElement(name="ApplicationVersion", required=false)
	private String  ApplicationVersion;
	
	@XmlElement(name="ApplicationJourney", required=false)
	private String  ApplicationJourney;
	
	@XmlElement(name="ApplicationAttributes", required=false)
	private ApplicationAttributes[]  ApplicationAttributes;

	public String getSYSREFNO() {
		return SYSREFNO;
	}
	
	public void setSYSREFNO(String SYSREFNO) {
		this.SYSREFNO = SYSREFNO;
	}
	
	public String getMODE() {
		return MODE;
	}

	public void setMODE(String mODE) {
		MODE = mODE;
	}

	public String getWI_NAME() {
		return WI_NAME;
	}

	public void setWI_NAME(String wI_NAME) {
		WI_NAME = wI_NAME;
	}

	public String getRequestDateTime() {
		return RequestDateTime;
	}

	public void setRequestDateTime(String requestDateTime) {
		RequestDateTime = requestDateTime;
	}

	public String getStage() {
		return Stage;
	}

	public void setStage(String stage) {
		Stage = stage;
	}

	public String getSourcingChannel() {
		return SourcingChannel;
	}

	public void setSourcingChannel(String sourcingChannel) {
		SourcingChannel = sourcingChannel;
	}

	public String getSourcingCenter() {
		return SourcingCenter;
	}

	public void setSourcingCenter(String sourcingCenter) {
		SourcingCenter = sourcingCenter;
	}

	public String getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
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

	public String getApplicationName() {
		return ApplicationName;
	}

	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getLeadNumber() {
		return LeadNumber;
	}

	public void setLeadNumber(String leadNumber) {
		LeadNumber = leadNumber;
	}
	
	public String getApplicationVersion() {
		return ApplicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		ApplicationVersion = applicationVersion;
	}

	public String getApplicationJourney() {
		return ApplicationJourney;
	}

	public void setApplicationJourney(String applicationJourney) {
		ApplicationJourney = applicationJourney;
	}

	public ApplicationAttributes[] getApplicationAttributes() {
		return ApplicationAttributes;
	}

	public void setApplicationAttributes(
			ApplicationAttributes[] applicationAttributes) {
		ApplicationAttributes = applicationAttributes;
	}
}
