package com.newgen;

import java.util.Date;

import com.newgen.omni.wf.util.excp.NGException;

import com.newgen.wfdesktop.xmlapi.WFInputXml;

public class GenerateXml {

	public static String getWMConnectInputXML(String cabinetName,String username, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<WMConnect_Input>\n");
		sb.append("<Option>WMConnect</Option>\n");
		sb.append("<EngineName>" + cabinetName + "</EngineName>\n");
		sb.append("<Participant>\n");
		sb.append("<Name>" + username + "</Name>\n");
		sb.append("<Password>" + password + "</Password>\n");
		sb.append("<Scope></Scope>\n");
		sb.append("<UserExist>N</UserExist>\n");
		sb.append("<Locale>en-us</Locale>\n");
		sb.append("<ParticipantType>U</ParticipantType>\n");
		sb.append("</Participant>\n");
		sb.append("</WMConnect_Input>");
		return sb.toString();
	}

	public static String ExecuteQuery_APSelect(String sQuery,String cabinetName, String sessionID) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<APSelect_Input>\n");
		sb.append("<Option>APSelect</Option>\n");
		sb.append("<EngineName>" + cabinetName + "</EngineName>\n");
		sb.append("<SessionId>" + sessionID + "</SessionId>");
		sb.append("<Query>" + sQuery + "</Query>");
		sb.append("</APSelect_Input>");
		return sb.toString();

	}

	public static String getWFSearchWorkItemListXML(String sEngineName,
			String sSession, String sProcessInstanceName,String sProcessDefinitionID) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<WFSearchWorkItemList_Input>");
		sb.append("<Option>WFSearchWorkItemList</Option>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSession + "</SessionId>");
		sb.append("<ZipBuffer>N</ZipBuffer>");
		sb.append("<Filter>");
		sb.append("<ProcessInstanceName>" + sProcessInstanceName+ "</ProcessInstanceName>");
		sb.append("<ProcessDefinitionID>" + sProcessDefinitionID+ "</ProcessDefinitionID>");
		sb.append("<SearchPreviousVersion>Y</SearchPreviousVersion>");
		sb.append("<QueryList><AdvancedSearch>N</AdvancedSearch></QueryList>");
		sb.append("</Filter>");
		sb.append("<DataFlag>Y</DataFlag><BatchInfo><OrderBy>2</OrderBy><SortOrder>A</SortOrder>");
		sb.append("<LastValue></LastValue><WorkitemID></WorkitemID><ProcessInstanceId></ProcessInstanceId>");
		sb.append("<NoOfRecordsToFetch>2</NoOfRecordsToFetch></BatchInfo>");
		sb.append("<UserDefVarFlag>Y</UserDefVarFlag><ShowAllWorkItemsFlag>Y</ShowAllWorkItemsFlag>");
		sb.append("</WFSearchWorkItemList_Input>");
		return sb.toString();
	}

	public static String getWMGetWorkItemXML(String sEngineName,
			String sSession, String sProcessInstanceName, String workitemiId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<WMGetWorkItem_Input>");
		sb.append("<Option>WMGetWorkItem</Option>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSession + "</SessionId>");
		sb.append("<ProcessInstanceId>" + sProcessInstanceName+ "</ProcessInstanceId>");
		sb.append("<WorkItemId>" + workitemiId + "</WorkItemId>");
		sb.append("</WMGetWorkItem_Input>");
		return sb.toString();
	}

	public static String WMCompleteWorkItemXML(String sEngineName,
			String sSession, String sProcessInstanceName, String workitemId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<WMCompleteWorkItem_Input>");
		sb.append("<Option>WMCompleteWorkItem</Option>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSession + "</SessionId>");
		sb.append("<ProcessInstanceId>" + sProcessInstanceName+ "</ProcessInstanceId>");
		sb.append("<WorkItemId>" + workitemId + "</WorkItemId>");
		sb.append("</WMCompleteWorkItem_Input>");
		return sb.toString();
	}

	public static String getWMDisconnectInputXML(String cabinetName,
			String sSessionID) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<WMDisConnect_Input>\n");
		sb.append("<Option>WMDisConnect</Option>\n");
		sb.append("<EngineName>" + cabinetName + "</EngineName>\n");
		sb.append("<SessionID>" + sSessionID + "</SessionID>\n");
		sb.append("</WMDisConnect_Input>");
		return sb.toString();
	}

	public static String getWMCompleteWorkItemXML(String sEngineName,
			String sSession, String sProcessInstanceName, String workitemId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<WMCompleteWorkItem_Input>");
		sb.append("<Option>WMCompleteWorkItem</Option>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSession + "</SessionId>");
		sb.append("<ProcessInstanceId>" + sProcessInstanceName
				+ "</ProcessInstanceId>");
		sb.append("<WorkItemId>" + workitemId + "</WorkItemId>");
		sb.append("</WMCompleteWorkItem_Input>");

		return sb.toString();
	}

	public static String aPSelectWithColumnNames_Input(String sQuery,
			String sEngineName, String sSessionId) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<APSelectWithColumnNames_Input>");
		sb.append("<Option>APSelectWithColumnNames</Option>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSessionId + "</SessionId>");
		sb.append("<QueryString>" + sQuery + "</QueryString>");
		sb.append("</APSelectWithColumnNames_Input>");

		return sb.toString();
	}

	public static String apUpdateInputXml(String sEngineName,
			String sSessionId, String tableName, String columnName,
			String strValues, String sWhere) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<APUpdate_Input>");
		sb.append("<Option>APUpdate</Option>");
		sb.append("<TableName>" + tableName + "</TableName>");
		sb.append("<ColName>" + columnName + "</ColName>");
		sb.append("<Values>" + strValues + "</Values>");
		sb.append("<WhereClause>" + sWhere + "</WhereClause>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSessionId + "</SessionId>");
		sb.append("</APUpdate_Input>");
		return sb.toString();
	}

	public static String apInsertInputXml(String sEngineName,
			String sSessionId, String TableName, String ColName, String Values) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<APInsert_Input>");
		sb.append("<Option>APInsert</Option>");
		sb.append("<TableName>" + TableName + "</TableName>");
		sb.append("<ColName>" + ColName + "</ColName>");
		sb.append("<Values>" + Values + "</Values>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSessionId + "</SessionId>");
		sb.append("</APInsert_Input>");
		return sb.toString();
	}

	public static String procCallInputXml(String sEngineName,
			String sSessionId, String procName, String params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\"?>");
		sb.append("<APProcedure_Input>");
		sb.append("<Option>APProcedure</Option>");
		sb.append("<ProcName>" + procName + "</ProcName>");
		sb.append("<Params>" + params + "</Params>");
		sb.append("<EngineName>" + sEngineName + "</EngineName>");
		sb.append("<SessionId>" + sSessionId + "</SessionId>");
		sb.append("</APProcedure_Input>");
		return sb.toString();
	}

	public static String getWMCompleteWorkItemInput(String cabinetName,String sessionId,String strProcessInstanceId,
			String strWorkItemId,String strAuditStatus, String activityId) {
		WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WMCompleteWorkItem", "Input");
		wfInputXml.appendTagAndValue("EngineName", cabinetName);
		wfInputXml.appendTagAndValue("SessionId", sessionId);
		wfInputXml.appendTagAndValue("ProcessInstanceId", strProcessInstanceId);
		wfInputXml.appendTagAndValue("WorkItemId", strWorkItemId);
		wfInputXml.appendTagAndValue("AuditStatus", strAuditStatus);
		wfInputXml.appendTagAndValue("ActivityId", activityId);
		wfInputXml.appendEndCallName("WMCompleteWorkItem", "Input");
		return wfInputXml.toString();
	}
	public static String getWMUnlockWorkItemInput(String strEngineName,String strSessionId,String strProcessInstanceId,String strWorkItemId) {
		WFInputXml wfInputXml = new WFInputXml();
		wfInputXml.appendStartCallName("WMUnlockWorkItem", "Input");
		wfInputXml.appendTagAndValue("EngineName", strEngineName);
		wfInputXml.appendTagAndValue("SessionId", strSessionId);
		wfInputXml.appendTagAndValue("ProcessInstanceID", strProcessInstanceId);
		wfInputXml.appendTagAndValue("WorkItemID", strWorkItemId);
		wfInputXml.appendTagAndValue("RightFlag", "010100");
		wfInputXml.appendEndCallName("WMUnlockWorkItem", "Input");
		return wfInputXml.toString();
	}
	public static void main(String a[]){
		String grpInputXml=GenerateXml.getWMUnlockWorkItemInput("adwms002", "sessionid","" ,"");
		System.out.println(grpInputXml);
		
	}
	public static String IsSessionValid(String sessionId,String cabinetName) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFIsSessionValid_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("</WFIsSessionValid_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}
	
	public static String createInputXMLForEvent(String mode, String wiName,String sessionId,String stageId,String extraValues, String cabinetName) {
		System.out.println("createInputXMLForEvent");
		StringBuilder inputXML = new StringBuilder();
		try {
			inputXML.append("<?xml version=\"1.0\"?>")
			.append("\n")
			.append("<APWebService_Input>")
			.append("\n")
			.append("<Option>WebService</Option>")
			.append("\n")
			.append("<EngineName>" + cabinetName + "</EngineName>")
			.append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>")
			.append("\n")
			.append("<Calltype>CBG</Calltype>")
			.append("\n")
			.append("<CBGCallType>SINGLE_HOOK</CBGCallType>")
			.append("\n")
			.append("<REF_NO>"+generateSysRefNumber(cabinetName,sessionId)+"</REF_NO>")
			.append("<senderID>CBG</senderID>")
			.append("<RequestDateTime>"+new Date().toString()+"</RequestDateTime>")
			.append("<MODE>"+ mode +"</MODE>")
			.append("<WI_NAME>"+wiName+"</WI_NAME>")
			.append("<stage>"+stageId+"</stage>")
			.append("<applicationAttributes>")
			.append("<attributeDetails>")
			.append("<attributes>")
			.append(extraValues)	
			.append("</attributes>")
			.append("</attributeDetails>")
			.append("</applicationAttributes>")
			.append("<applicationName>CBGAPP</applicationName>")
			.append("<SourcingChannel>CBG</SourcingChannel>")
			.append("<SourcingCenter>CBG</SourcingCenter>")
			.append("<Language>Eng</Language>")
			.append("<LeadNumber>CBG</LeadNumber>")
			.append("<DeviceID>CBG</DeviceID>")					
			.append("<IP>CBG</IP>")
			.append("</APWebService_Input>");	
			System.out.println("createInputXMLForEvent: \n"+inputXML);

			//CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,	"SINGLE_HOOK inputXML ===> " + inputXML.toString());
		} catch (Exception e) {
			//CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
			System.out.println("createInputXMLForEvent: \n"+e);

			e.printStackTrace();
		}
		return inputXML.toString();
	}
	
	public static String generateSysRefNumber(String cabinetName, String sessionId){
		String sysNum = "";
		try {
			String outputXML = aPSelectWithColumnNames_Input("SELECT CBG_REFNO.NEXTVAL SYSREFNO FROM DUAL",cabinetName, sessionId);
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysNum;
	}
		
}