package com.newgen.iforms.user.ao.model.casafulfillment;

public class Header {
	
	 private String workItemNo;
	    private String senderId;
	    private String sentOn;
	    private String messageId;
	    private String eventName;
	    private String externalReferenceNo;
	    private String correlationId;
	    private String sentBy;
	    private String statusCode;
	    
		public String getWorkItemNo() {
			return workItemNo;
		}
		public void setWorkItemNo(String workItemNo) {
			this.workItemNo = workItemNo;
		}
		public String getSenderId() {
			return senderId;
		}
		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}
		public String getSentOn() {
			return sentOn;
		}
		public void setSentOn(String sentOn) {
			this.sentOn = sentOn;
		}
		public String getMessageId() {
			return messageId;
		}
		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}
		public String getEventName() {
			return eventName;
		}
		public void setEventName(String eventName) {
			this.eventName = eventName;
		}
		public String getExternalReferenceNo() {
			return externalReferenceNo;
		}
		public void setExternalReferenceNo(String externalReferenceNo) {
			this.externalReferenceNo = externalReferenceNo;
		}
		public String getCorrelationId() {
			return correlationId;
		}
		public void setCorrelationId(String correlationId) {
			this.correlationId = correlationId;
		}
		public String getSentBy() {
			return sentBy;
		}
		public void setSentBy(String sentBy) {
			this.sentBy = sentBy;
		}
		public String getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

}
