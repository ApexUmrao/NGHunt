package com.newgen.dscop.utility;

import com.newgen.omni.wf.util.excp.NGException;

public class APCallCreateXML {
	static private String cabinetName;
	static private int processDefId;

	public static void init(String cabName, int processdefId)
	{
		cabinetName = cabName;
		processDefId = processdefId;
	}

	public static String APDelete(String tableName, String sWhere, String sessionId) throws NGException 
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APDelete_Input>").append("\n")
		.append("<Option>APDelete</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<TableName>"+ tableName + "</TableName>").append("\n")
		.append( "<WhereClause>" + sWhere + "</WhereClause>").append("\n")
		.append("</APDelete_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML;
	}

	public static String APInsert(String tableName,String columnName,String strValues, String sessionId) throws NGException
	{

		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APInsert_Input>").append("\n")
		.append("<Option>APInsert</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<TableName>"+ tableName + "</TableName>").append("\n")
		.append("<ColName>" + columnName + "</ColName>").append("\n")
		.append( "<Values>" + strValues + "</Values>").append("\n")
		.append("</APInsert_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML;   
	}

	public static String APSelect(String Query) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APSelectWithColumnNames_Input>").append("\n")
		.append("<Option>APSelectWithColumnNames</Option>").append("\n")
		.append("<EngineName>"+ cabinetName +"</EngineName>").append("\n")
		.append("<QueryString>"+Query+"</QueryString>").append("\n")
		.append("</APSelectWithColumnNames_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML; 
	}

	public static String APUpdate(String tableName, String columnName, String strValues, String whereClause, String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APUpdate_Input>").append("\n")
		.append("<Option>APUpdate</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<TableName>"+ tableName + "</TableName>").append("\n")
		.append("<ColName>" + columnName + "</ColName>").append("\n")
		.append( "<Values>" + strValues + "</Values>").append("\n")
		.append( "<WhereClause>" + whereClause + "</WhereClause>").append("\n")
		.append("</APUpdate_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML;   
	}

	public static String APTemplate(String wiName, String sessionId) throws NGException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APTemplate_Input>").append("\n")
		.append("<Option>APTemplate</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Query>"+ wiName + "</Query>").append("\n")
		.append("</APTemplate_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}
	
	public static String APProcedure(String sessionId, String ProcName, String InValues, int NoOfCols) throws NGException
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
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO," input proc xml " + sInputXML.toString());
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());

		return outputXML;   
	}

	public static String Webservice(String sessionId) throws NGException, InterruptedException
	{
		StringBuilder sInputXML = new StringBuilder(); 
		sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>CustEIDAInfo</Calltype>").append("\n")
		.append("<EIDANum>784199173063076</EIDANum>").append("\n")
		.append("<REF_No>9999</REF_No>").append("\n")
		.append("</APWebService_Input>");
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,outputXML);
		return outputXML;   
	}
}
