package com.newgen.tsservice.util;

import org.springframework.context.annotation.Configuration;


import com.newgen.omni.wf.util.excp.NGException;
@Configuration
public class APCallCreateXML {
	static private String cabinetName;

	public static void init(String cabName)
	{
		cabinetName = cabName;
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
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		return outputXML;   
	}

}
