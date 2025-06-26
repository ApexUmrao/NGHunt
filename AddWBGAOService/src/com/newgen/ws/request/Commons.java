package com.newgen.ws.request;

public class Commons {
	
	public String WFUploadWorkItem(String sOption ,String sEngineName ,String sSessionID ,String sProcessDefId ,String sValidationRequired ,String sAttributes,String sTemp,String sIniActName,String sIniActId)
	 {
		// return  "<?xml version=\"1.0\"?><WFUploadWorkItem_Input><Option>WFUploadWorkItem</Option><EngineName>"+
	// sEngineName+"</EngineName><SessionId>"+
		//		 sSessionID+"</SessionId><ValidationRequired></ValidationRequired><ProcessDefId>11</ProcessDefId><DataDefName></DataDefName><Attributes>"+sAttributes+"</Attributes></WFUploadWorkItem_Input>";
				 
				 
				 return  "<?xml version=\"1.0\"?><WFUploadWorkItem_Input><Option>WFUploadWorkItem</Option><EngineName>"+sEngineName+"</EngineName><SessionId>"+sSessionID+"</SessionId><ValidationRequired></ValidationRequired><ProcessDefId>"+sProcessDefId+"</ProcessDefId><DataDefName></DataDefName><Documents>"+sTemp+"</Documents><InitiateFromActivityId>"+sIniActId+"</InitiateFromActivityId><InitiateFromActivityName>"+sIniActName+"</InitiateFromActivityName><Attributes>"+sAttributes+"</Attributes></WFUploadWorkItem_Input>";

	 }
}
