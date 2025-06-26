package com.newgen.dscop.utility;

import com.newgen.omni.wf.util.excp.NGException;

public class ProdCreateXML {
	static private String cabinetName;
	static private int processDefId;

	public static void init(String cabName, int processdefId)
	{
		cabinetName = cabName;
		processDefId = processdefId;
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
		.append("<UserExist>Y</UserExist>").append("\n")
		.append("</WMConnect_Input>");			
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WMConnect OutputXML ===>" + outputXML);
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
		//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "IsSessionValid OutputXML ===>" + outputXML);
		return outputXML;   
	}
	
}

