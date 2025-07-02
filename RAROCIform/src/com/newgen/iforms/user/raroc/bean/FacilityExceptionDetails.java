package com.newgen.iforms.user.raroc.bean;

import java.util.ArrayList;

public class FacilityExceptionDetails {
	public String successIndicator;
    public ArrayList<Object> messages;
	public String getSuccessIndicator() {
		return successIndicator;
	}
	public void setSuccessIndicator(String successIndicator) {
		this.successIndicator = successIndicator;
	}
	public ArrayList<Object> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Object> messages) {
		this.messages = messages;
	}

}
