package com.newgen.cbg.response;

import com.newgen.cbg.request.ApplicationAttributes;

public class CBGSingleHookResponse {

	private String StatusCode;
	private String StatusMessage;
	private String WI_NAME;
	private String Stage;
	private String ApplicationName;
	private String WSName;
	private String Language;
	private String LeadNumber;
	private String ApplicationVersion;
	private String ApplicationJourney;
	private String SYSREFNO;
	private ApplicationAttributes[] ApplicationAttributes;
	private String NextStage;
	
	public String getSYSREFNO() {
		return SYSREFNO;
	}
	public void setSYSREFNO(String sYSREFNO) {
		SYSREFNO = sYSREFNO;
	}
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	public String getStatusMessage() {
		return StatusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		StatusMessage = statusMessage;
	}
	public String getWI_NAME() {
		return WI_NAME;
	}
	public void setWI_NAME(String wI_NAME) {
		WI_NAME = wI_NAME;
	}
	public String getStage() {
		return Stage;
	}
	public void setStage(String stage) {
		Stage = stage;
	}
	public String getApplicationName() {
		return ApplicationName;
	}
	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}
	public String getWSName() {
		return WSName;
	}
	public void setWSName(String wSName) {
		WSName = wSName;
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
	public void setApplicationAttributes(ApplicationAttributes[] applicationAttributes) {
		ApplicationAttributes = applicationAttributes;
	}
	public String getNextStage() {
		return NextStage;
	}
	public void setNextStage(String nextStage) {
		NextStage = nextStage;
	}
}
