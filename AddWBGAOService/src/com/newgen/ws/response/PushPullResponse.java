/*

---------------------------------------------------------------------------------------------------------

NEWGEN SOFTWARE TECHNOLOGIES LIMITED

Group : Application Project 2

Project/Product :Aproj2_ADCB_CR_LODGEMENT_PHASE2

Application :  AO

Module : WebService

File Name : AccountOpening Response

Author : Rohini Rajpal

Date (DD/MM/YYYY) : 

Description : 

-------------------------------------------------------------------------------------------------------

CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No Change Date Changed By Change Description

------------------------------------------------------------------------------------------------------

 

-----------------------------------------------------------------------------------------------------*/

package com.newgen.ws.response;

public class PushPullResponse 
{
  private String Status;
 
  private String Description;
  private String ErrorCode;
  private String Message;
  private String workItemNo;
  private respHeader  respHeader;

  public String getStatus()
  {
    return Status; 
    
    
    }

  public void setStatus(String status) {
    Status = status; }

 

  public String getDescription() {
    return Description; }

  public void setDescription(String description) {
    Description = description; }

  public String getErrorCode() {
    return ErrorCode; }

  public void setErrorCode(String errorCode) {
    ErrorCode = errorCode; }

  public String getMessage() {
    return Message; }

  public void setMessage(String message) {
    Message = message;
  }

public respHeader getRespHeader() {
	return respHeader;
}

public void setRespHeader(respHeader respHeader) {
	this.respHeader = respHeader;
}

public String getWorkItemNo() {
	return workItemNo;
}

public void setWorkItemNo(String workItemNo) {
	this.workItemNo = workItemNo;
}


}