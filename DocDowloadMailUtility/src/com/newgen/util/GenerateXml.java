
package com.newgen.util;

import com.newgen.omni.wf.util.excp.NGException;



public class GenerateXml
{
	public  static String getWICompleteXML( String processInstanceId, String workItemId, String sessionId, String cabinetName) 
	{
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +
		"<WMCompleteWorkItem_Input>" + "\n" +
		"<Option>WMCompleteWorkItem</Option>" + "\n" +
		"<EngineName>"+ cabinetName + "</EngineName>" + "\n" +
		"<SessionId>" + sessionId + "</SessionId>" + "\n" +
		"<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>" + "\n" +
		"<WorkItemId>" + workItemId + "</WorkItemId>" + "\n" +
		"<AuditStatus></AuditStatus>" + "\n" +
		"</WMCompleteWorkItem_Input>";

		return xml;
	}

	public static  String getGetWorkItemXML( String processInstanceId, String workItemId, String sessionId, String cabinetName) 
	{
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +
		"<WMGetWorkItem_Input>"+ "\n" +
		"<Option>WMGetWorkItem</Option>"+ "\n" +
		"<EngineName>" + cabinetName + "</EngineName>"+ "\n" +
		"<SessionId>" + sessionId + "</SessionId>"+ "\n" +
		"<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>" + "\n" +
		"<WorkItemId>" + workItemId + "</WorkItemId>"+ "\n" +
		"</WMGetWorkItem_Input>";

		return xml;
	}

	public static  String getConnectInputXML(String cabinetName, String username, String password) 
	{
		String connectInputXML="<?xml version=\"1.0\"?>" +
		"<WMConnect_Input>" +
		"<Option>WMConnect</Option>" +
		"<EngineName>" + cabinetName + "</EngineName>" +
		"<Participant>" +
		"<Name>" + username + "</Name>" +
		"<Password>" + password + "</Password>" +
		"<Scope></Scope>" +
		"<UserExist>N</UserExist>" +
		"<Locale>en-us</Locale>" +
		"<ParticipantType>U</ParticipantType>" +
		"</Particpant>" + 
		"</WMConnect_Input>";
		return connectInputXML;
	}

	public static  String getFetchWorkItemsInputXML(String sessionId, String cabinetName, String queueId) 
	{
		String xml = "<?xml version=\"1.0\"?>" + "\n" +
		"<WMFetchWorkItems_Input>" + "\n" +
		"<Option>WMFetchWorkItem</Option>" + "\n" +
		"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
		"<SessionID>" + sessionId + "</SessionID>" + "\n" +
		"<QueueId>" + queueId + "</QueueId>" + "\n" +
		"<BatchInfo>" + "\n" +
		"<NoOfRecordsToFetch>100</NoOfRecordsToFetch>" + "\n" +
		"<LastWorkItem></LastWorkItem>" + "\n" +
		"<LastValue></LastValue>" + "\n" +
		"<LastProcessInstance></LastProcessInstance>" + "\n" +
		"</BatchInfo>" + "\n" +
		"</WMFetchWorkItems_Input>";

		return xml;
	}

	public static  String getFetchWorkItemAttributesXML(String processInstanceId, String workItemId, String sessionId, String cabinetName) {
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +
		"<WMFetchWorkItemAttributes_Input>" + "\n" +
		"<Option>WMFetchWorkItemAttributes</Option>" + "\n" +
		"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
		"<SessionId>" + sessionId + "</SessionId>" + "\n" +
		"<ProcessInstanceId>" + processInstanceId + "</ProcessInstanceId>" + "\n" +
		"<WorkItemId>" + workItemId + "</WorkItemId>" + "\n" +		
		"</WMFetchWorkItemAttributes_Input>" ;

		return xml;
	}

	public static String APProcedureWithColumnNames(String cabinetName, String sessionId, String strProcName, String strParams) {

		return "<?xml version=\"1.0\" ?>" + "\n" +
		"<APProcedure_Input>" + "\n" +
		"<Option>APProcedureWithColumnNames</Option>" + "\n" +
		"<SessionId>" + sessionId + "</SessionId>" + "\n" +
		"<ProcName>" + strProcName + "</ProcName>" + "\n" +
		"<Params>'" + strParams + "'</Params>" + "\n" +
		"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
		"<APProcedure_Input>";

	}

	public static String APProcedure(String strEngineName, String strSessionId, String strProcName, String strParams) {
		return "<?xml version=\"1.0\"?>" +    		
		"<WMTestProcedure_Input>" +
		"<Option>APProcedure</Option>" +
		"<SessionId>"+strSessionId+"</SessionId>" +
		"<ProcessDefId>61</ProcessDefId>" +
		"<ProcName>"+strProcName+"</ProcName>" +
		"<Params>'"+strParams+"'</Params>" +
		"<Status></Status>" +
		"<EngineName>"+strEngineName+"</EngineName>" +
		"<WMTestProcedure_Input>";

	}

	public static String APSelectWithColumnNames(String strEngineName,String strQuery, String sessionId) {
		return "<?xml version=\"1.0\"?>"
		+ "<APSelect_Input>"
		+ "<Option>APSelectWithColumnNames</Option>"
		+ "<QueryString>" + strQuery + "</QueryString>"
		+ "<EngineName>" + strEngineName + "</EngineName>"
		+ "<SessionId>" + sessionId + "</SessionId>"
		+ "</APSelect_Input>";         
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
	
	public static String APProcedure_new(String sessionId,String cabinetName, String ProcName, String InValues, int NoOfCols) throws NGException 
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APProcedure_Input>").append("\n")
		.append("<Option>APProcedure</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<ProcName>"+ ProcName + "</ProcName>").append("\n")
		.append("<Params>" + InValues + "</Params>").append("\n")
		.append("<NoOfCols>" + NoOfCols + "</NoOfCols>").append("\n")
		.append("</APProcedure_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML;   
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
}

