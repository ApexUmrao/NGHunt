package com.newgen.push.message;

import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.omni.wf.util.xml.XMLParser;
import com.newgen.push.message.BPMReqLogMe;
import com.newgen.push.message.ExecuteXML;

public class ProdCreateXML {
	static private String cabinetName;
	static private int processDefId;
	static private String initiateActivityId;
	static private String initiateActivityName;

	public static void init(String cabName, int processdefId, String initiateFromActivityId, 
			String initiateFromActivityName)
	{
		cabinetName = cabName;
		processDefId = processdefId;
		initiateActivityId = initiateFromActivityId;
		initiateActivityName = initiateFromActivityName;
	}

	public static String WMConnect(String sUsername, String sPassword) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder();
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMConnect_Input>").append("\n")
		.append("<Option>WMConnect</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<Name>" + sUsername + "</Name>").append("\n")
		.append("<Password>" + sPassword + "</Password>").append("\n")
		.append("<UserExist>N</UserExist>").append("\n")
		.append("</WMConnect_Input>");			
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "WMConnect OutputXML ===>" + outputXML);
		return outputXML;    
	}

	public static String WMGetWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMGetWorkItem_Input>").append("\n")
		.append("<Option>WMGetWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMGetWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "WMGetWorkItem OutputXML ===>" + outputXML);
		int mainCode = Integer.parseInt(new XMLParser(outputXML).getValueOf("MainCode"));
		if(mainCode == 16){
			WMUnlockWorkItem(sessionId, ProcessInstanceId, WorkitemId);
		}
		return outputXML;   
	}

	public static String WMUnlockWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMUnlockWorkItem_Input>").append("\n")
		.append("<Option>WMUnlockWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<UnlockOption>W</UnlockOption>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMUnlockWorkItem_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;
	}

	public static String WMCompleteWorkItem(String sessionId, String ProcessInstanceId, int WorkitemId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WMCompleteWorkItem_Input>").append("\n")
		.append("<Option>WMCompleteWorkItem</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<ProcessInstanceId>" + ProcessInstanceId + "</ProcessInstanceId>").append("\n")
		.append("<WorkitemId>" + WorkitemId + "</WorkitemId>").append("\n")
		.append("</WMCompleteWorkItem_Input>");
		//Lock Workitem
		WMGetWorkItem(sessionId, ProcessInstanceId, WorkitemId);		
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "WMCompleteWorkItem OutputXML ===>" + outputXML);
		return outputXML;   
	}

	public static String IsSessionValid(String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<WFIsSessionValid_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("</WFIsSessionValid_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}
	
	public static String AddDocument(String sessionId, String parentFolderIndex, String documentName, String createdByAppName, String comment, 
			int volumeIndex, int dataDef, String accessType, String ISIndex, int noOfPages, String docType, String docSize, String FTSFlag) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<NGOAddDocument_Input>").append("\n")
		.append("<Option>WFIsSessionValid</Option>").append("\n")
		.append("<UserDBId>" + sessionId + "</UserDBId>").append("\n")
		.append("<Document>").append("\n")
		.append("<ParentFolderIndex>" + parentFolderIndex + "</ParentFolderIndex>").append("\n")
		.append("<DocumentName>" + documentName + "</DocumentName>").append("\n")
		.append("<CreatedByAppName>" + createdByAppName + "</CreatedByAppName>").append("\n")
		.append("<Comment>" + comment + "</Comment>").append("\n")
		.append("<VolumeIndex>" + volumeIndex + "</VolumeIndex>").append("\n")
		.append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
		.append("<DataDefinition>"+ dataDef +"</DataDefinition>").append("\n")
		.append("<AccessType>" + accessType + "</AccessType>").append("\n")
		.append("<NoOfPages>" + noOfPages + "</NoOfPages>").append("\n")
		.append("<ISIndex>" + ISIndex + "</ISIndex>").append("\n")
		.append("<DocumentType>" + docType + "</DocumentType>").append("\n")
		.append("<DocumentSize>" + docSize + "</DocumentSize>").append("\n")
		.append("<FTSFlag>" + FTSFlag + "</FTSFlag>").append("\n")
		.append("</Document>").append("\n")
		.append("</NGOAddDocument_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ECBINFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}
	
	public static String WFUploadWorkItem(String sessionId, String initiateAlso, String attribXML) throws NGException
	  {
	    StringBuilder sInputXML = new StringBuilder();
	    sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
	      .append("<WFUploadWorkItem_Input>").append("\n")
	      .append("<Option>WFUploadWorkItem</Option>").append("\n")
	      .append("<SessionId>" + sessionId + "</SessionId>").append("\n")
	      .append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
	      .append("<ProcessDefId>" + processDefId + "</ProcessDefId>").append("\n")
	      .append("<InitiateAlso>" + initiateAlso + "</InitiateAlso>").append("\n")
	      .append("<Documents>" + "" + "</Documents>").append("\n")
	      .append("<ValidationRequired></ValidationRequired>").append("\n")
	      .append("<DataDefName></DataDefName>").append("\n")
	      .append("<InitiateFromActivityId>" + initiateActivityId + "</InitiateFromActivityId>").append("\n")
	      .append("<InitiateFromActivityName>" + initiateActivityName + "</InitiateFromActivityName>").append("\n")
	      .append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n")
	      .append("<Attributes>" + attribXML + "</Attributes>").append("\n")
	      .append("</WFUploadWorkItem_Input>");
	    BPMReqLogMe.logMe(1, "WFUploadWorkItem sInputXML ===>" + sInputXML);
	    String outputXML = ExecuteXML.executeXML(sInputXML.toString());
	    BPMReqLogMe.logMe(1, "WFUploadWorkItem OutputXML ===>" + outputXML);
	    return outputXML;
	  }
}

