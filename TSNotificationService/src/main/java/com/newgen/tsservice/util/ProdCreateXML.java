package com.newgen.tsservice.util;



import com.newgen.omni.wf.util.excp.NGException;


public class ProdCreateXML {
	static private String cabinetName;
	

	public static void init(String cabName)
	{
		cabinetName = cabName;
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
		return outputXML;   
	}



	

}


