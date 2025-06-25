package com.newgen.ao.laps.response;

public class LapsModifyResponse {

	private String channelName;
	private String channelRefNumber;
	private String wiNumber;
	private String wiQueue;
	private String correlationId;
	private String statusCode;
	private String statusDescription;
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
	public String getWiNumber() {
		return wiNumber;
	}
	public void setWiNumber(String wiNumber) {
		this.wiNumber = wiNumber;
	}
	public String getWiQueue() {
		return wiQueue;
	}
	public void setWiQueue(String wiQueue) {
		this.wiQueue = wiQueue;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
}
