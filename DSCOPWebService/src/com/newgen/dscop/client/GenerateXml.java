/*
Group						: Application Projects 2
Product / Project			: ECO Bank
Module                      : Java File
File Name					: ChequeBook.java
Author                      : Sachin Gupta
Date written (DD/MM/YYYY)   : 10-March-2010
Date modified (DD/MM/YYYY)  : 13-March-2010
Description                 : Java file to Cheque Book Upload.
-------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------
CHANGE HISTORY 
-------------------------------------------------------------------------------------------------------------------
BUG ID                Date			Change By			Change Description 

-------------------------------------------------------------------------------------------------------------------
 */

package com.newgen.dscop.client;

import com.newgen.wfdesktop.xmlapi.WFInputXml;

public class GenerateXml
{
	public  String getWICompleteXML(String processInstanceId, String workItemId, String sessionId, String cabinetName) 
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

	public  String getGetWorkItemXML( String processInstanceId, String workItemId, String sessionId, String cabinetName) 
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
	





	public String getConnectInputXML(String cabinetName, String username, String password) 
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

	public String getFetchWorkItemsInputXML(String processInstanceId, String lastWorkItemId,  String sessionId, String cabinetName, String queueId) 
	{
		String xml = "<?xml version=\"1.0\"?>" + "\n" +
		"<WMFetchWorkItems_Input>" + "\n" +
		"<Option>WMFetchWorkItem</Option>" + "\n" +
		"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
		"<SessionID>" + sessionId + "</SessionID>" + "\n" +
		"<QueueId>" + queueId + "</QueueId>" + "\n" +
		"<BatchInfo>" + "\n" +
		"<NoOfRecordsToFetch>100</NoOfRecordsToFetch>" + "\n" +
		"<LastWorkItem>" +lastWorkItemId+ "</LastWorkItem>" + "\n" +
		"<LastValue></LastValue>" + "\n" +
		"<LastProcessInstance>" + processInstanceId + "</LastProcessInstance>" + "\n" +
		"</BatchInfo>" + "\n" +
		"</WMFetchWorkItems_Input>";

		return xml;
	}

	public String getFetchWorkItemAttributesXML(String processInstanceId, String workItemId, String sessionId, String cabinetName) {
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

	String get_WFUploadWorkItem_Input(String cabinetName, String sessionID, String processdefId, String attList) 
	{
		WFInputXml wfInputXml = new WFInputXml();

		wfInputXml.appendStartCallName("WFUploadWorkItem", "Input");
		wfInputXml.appendTagAndValue("EngineName",cabinetName);
		wfInputXml.appendTagAndValue("SessionId",sessionID);
		wfInputXml.appendTagAndValue("ProcessDefId",processdefId);
		wfInputXml.appendTagAndValue("Attributes",attList);
		wfInputXml.appendEndCallName("WFUploadWorkItem","Input");

		return wfInputXml.toString();
	}

	public String getFetchWorkItemsListInputXML(String processInstanceId, String lastWorkItemId, String sessionId, String cabinetName, String queueId) 
	{
		String xml = "<?xml version=\"1.0\"?>" + "\n" +
		"<WMFetchWorkList_Input>" + "\n" +
		"<Option>WMFetchWorkList</Option>" + "\n" +
		"<EngineName>" + cabinetName + "</EngineName>" + "\n" +
		"<SessionID>" + sessionId + "</SessionID>" + "\n" +
		"<QueueId>" + queueId + "</QueueId>" + "\n" +
		"<BatchInfo>" + "\n" +
		"<NoOfRecordsToFetch>100</NoOfRecordsToFetch>" + "\n" +
		"<LastWorkItem>" +lastWorkItemId+ "</LastWorkItem>" + "\n" +
		"<LastValue></LastValue>" + "\n" +
		"<LastProcessInstance>" + processInstanceId + "</LastProcessInstance>" + "\n" +
		"</BatchInfo>" + "\n" +
		"</WMFetchWorkList_Input>";

		return xml;
	}
	public String PrepareQuery_APSelect(String sQuery,String cabinetName, String sessionID)
	{
		String sInputXML = "<?xml version=\"1.0\"?>" +
		"<APSelect_Input>" +
		"<Option>APSelect</Option>" +
		"<Query>" + sQuery + "</Query>" +
		"<EngineName>" + cabinetName + "</EngineName>" +
		"<SessionId>" + sessionID + "</SessionId>" +
		"</APSelect_Input>";
		return sInputXML ;
	}

	public String XMLIn_APUpdate(String tableName,String columnName,String strValues,String sWhere,String cabinetName ,String sessionID)
	{
		String sInputXML = "<?xml version=\"1.0\"?>"+
		"<APUpdate_Input><Option>APUpdate</Option>"+
		"<TableName>" + tableName +"</TableName>"+
		"<ColName>" + columnName + "</ColName>"+
		"<Values>" + strValues + "</Values>"+
		"<WhereClause>" + sWhere + "</WhereClause>"+
		"<EngineName>" + cabinetName + "</EngineName>" +
		"<SessionId>" + sessionID + "</SessionId>" +
		"</APUpdate_Input>";
		return sInputXML;
	}
	public String XMLIn_APDelete(String tableName,String sWhere,String cabinetName ,String sessionID)
	{
		String sInputXML = "<?xml version=\"1.0\"?>"+
		"<APDelete_Input><Option>APDelete</Option>"+
		"<TableName>"+tableName+"</TableName>"+
		"<WhereClause>"+sWhere+"</WhereClause>"+
		"<EngineName>"+cabinetName+"</EngineName>"+
		"<SessionId>"+sessionID+"</SessionId>"+
		"</APUpdate_Input>";

		return sInputXML;
	}

	public String XMLIn_IGSetData(String cabinetName,String sessionID,String sQuery)
	{
		String sInputXML = "<?xml version=\"1.0\"?>"+

		"<IGSetData><Option>IGSetData</Option>"+
		"<EngineName>" + cabinetName + "</EngineName>" +
		"<SessionId>" + sessionID + "</SessionId>" +
		"<Query >" + sQuery + "</Query>" +
		"</IGSetData>";

		return sInputXML;
	}


	public String PrepareQuery_APInsert(String TableName, String ColName, String Values, String cabinetName ,String sessionId)
	{

		String sInputXML = "<?xml version=\"1.0\"?>" +
		"<APInsert_Input>" +
		"<Option>APInsert</Option>" +
		"<TableName>" + TableName + "</TableName>" +
		"<ColName>" + ColName + "</ColName>" +
		"<Values>" + Values + "</Values>" +
		"<EngineName>" + cabinetName + "</EngineName>" +
		"<SessionId>" + sessionId + "</SessionId>" +
		"</APInsert_Input>";
		return sInputXML;
	}



	public String get_NGOAddDocument_Input(String cabinetName, String sessionID, String folderIndex,String docSize,String DocumentName,String strISIndex) 
	{ 
		return "?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
		+"<NGOAddDocument_Input>\n" 
		+"<Option>NGOAddDocument</Option>\n" 
		+"<CabinetName>" + cabinetName +"</CabinetName>\n" 
		+"<UserDBId>" + sessionID + "</UserDBId>\n" 
		+"<GroupIndex>0</GroupIndex>\n"
		+"<Document>\n"
		+"<ParentFolderIndex>"+folderIndex+"</ParentFolderIndex>\n" 
		+"<NoOfPages>1</NoOfPages>\n"
		+"<AccessType>I</AccessType>\n" 
		+"<DocumentName>" + DocumentName + "</DocumentName>\n" 
		+"<CreatedByAppName>txt</CreatedByAppName>\n"   
		+"<ISIndex>"+strISIndex+"</ISIndex>"+"<NoOfPages>1</NoOfPages>"+"<DocumentType>N</DocumentType>"
		+"<DocumentSize>"+docSize+"</DocumentSize>\n"
		+"<ODMADocumentIndex></ODMADocumentIndex><Comment>"+DocumentName+"</Comment><EnableLog>Y</EnableLog>"
		+"<FTSFlag>PP</FTSFlag>"
		+"</Document>\n"
		+"</NGOAddDocument_Input>"; 

	}

	public  String  getSetAttributeXML(String engineName, String sessionId, String processInstanceId, String workItemId, String attributeName1, String attributeValue1) {
		String xml = "<?xml version=\"1.0\" ?>" + "\n" +

		"<WFSetAttributes_Input>" + "\n" +
		"<Option>WFSetAttributes</Option>" + "\n" +
		"<EngineName>" + engineName + "</EngineName>" + "\n" +
		"<SessionId>" + sessionId + "</SessionId>" + "\n" +
		"<ProcessInstanceID>" + processInstanceId + "</ProcessInstanceID>" + "\n" +
		"<WorkItemID>" + workItemId + "</WorkItemID>" + "\n" +
		"<Attributes>" + "\n" +
		"<Attribute>" + "\n" +
		"<Name>" + attributeName1 + "</Name>" + "\n" +
		"<Value>" + attributeValue1 + "</Value>" + "\n" +
		"</Attribute>" + "\n" +
		"</Attributes>" + "\n" +
		"</WFSetAttributes_Input>";

		return xml;
	}


}

