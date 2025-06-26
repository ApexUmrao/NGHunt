package com.newgen.cbg.model.ccfulfillment;

import java.util.Date;

public class Header {
	public String eventName;
    public String senderId;
    public String correlationId;
    public String externalReferenceNo;
    public String messageId;
    public String sentAt;
    public String sentBy;
    
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getExternalReferenceNo() {
		return externalReferenceNo;
	}
	public void setExternalReferenceNo(String externalReferenceNo) {
		this.externalReferenceNo = externalReferenceNo;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSentAt() {
		return sentAt;
	}
	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}
	public String getSentBy() {
		return sentBy;
	}
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}
}
