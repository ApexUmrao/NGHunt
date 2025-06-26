/*

---------------------------------------------------------------------------------------------------------

NEWGEN SOFTWARE TECHNOLOGIES LIMITED

Group : Application Project 2

Project/Product :Aproj2_ADCB_CR_LODGEMENT_PHASE2

Application :  AO

Module : WebService

File Name : AccountOpening Exception

Author : Rohini Rajpal

Date (DD/MM/YYYY) : 

Description : 

-------------------------------------------------------------------------------------------------------

CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No Change Date Changed By Change Description

------------------------------------------------------------------------------------------------------

 

-----------------------------------------------------------------------------------------------------*/
package com.newgen.ws.exception;

public class WbgAccountOpenningException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String message;
	private String description;
	public WbgAccountOpenningException(String status,String message,String description)
	{
		this.status=status;
		this.message=message;
		this.description=description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString()
	{
		return "WBG Exception- Status:"+getStatus()+", Msg:"+getMessage()+", Desc:"+getDescription();
	}

}
