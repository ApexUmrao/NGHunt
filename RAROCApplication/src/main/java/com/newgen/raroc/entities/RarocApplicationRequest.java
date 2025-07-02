package com.newgen.raroc.entities;

import lombok.Data;

@Data
public class RarocApplicationRequest {
	
	String sessionID;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}


	

}
