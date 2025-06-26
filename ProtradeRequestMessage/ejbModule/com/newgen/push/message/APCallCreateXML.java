package com.newgen.push.message;

import com.newgen.omni.wf.util.excp.NGException;

public class APCallCreateXML {
	static private String cabinetName;
	static private int processDefId;
	private static String exceptionFrom;
	private static String exceptionTo;
	private static String exceptionCC;
	private static String exceptionSubject;
	private static String exceptionMail;

	public static void init(String cabName, int processdefId, String excFrom, String excTo, 
			String excCC, String excSubject, String excMail)
	{
		cabinetName = cabName;
		processDefId = processdefId;
		exceptionFrom = excFrom;
		exceptionTo = excTo;
		exceptionCC = excCC;
		exceptionSubject = excSubject;
		exceptionMail = excMail;
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
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "inside APInsert: ");
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
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "sInputXML: " + sInputXML);
		String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "outputXML: " + outputXML);
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
		System.out.println(outputXML);
		return outputXML;   
	}
	

	public static void triggerExceptionMail(String tableName, String msg,String channelRefNumber, String correlationId ,String sessionId) {
		String inputXml = "";
		String outputXML = "";
		String mailBody = "";
		try {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"Inside handleLoginException");
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"[handleUtilityException] exception: "+msg);
			outputXML = APInsert("BPM_UTILITY_EXCEPTION", 
					"UTILITY_NAME,EXCEPTION_TIME,EXCEPTION_DESC", "'MQ-PROTRADE',SYSDATE,'"+msg+"'",sessionId);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "outputXml: "+outputXML);
		    mailBody = exceptionMail + "in staging table " + tableName + " [" + msg + "].";
			outputXML = APInsert("WFMAILQUEUETABLE", 
					"MAILFROM,MAILTO,MAILCC,MAILSUBJECT,MAILMESSAGE,MAILCONTENTTYPE,ATTACHMENTISINDEX,ATTACHMENTNAMES,"
					+ "ATTACHMENTEXTS,MAILPRIORITY,MAILSTATUS,STATUSCOMMENTS,LOCKEDBY,SUCCESSTIME,LASTLOCKTIME,"
					+ "INSERTEDBY,MAILACTIONTYPE,INSERTEDTIME,PROCESSDEFID,PROCESSINSTANCEID,WORKITEMID,ACTIVITYID,"
					+ "NOOFTRIALS", "'"+exceptionFrom+"','"+exceptionTo+"','"+exceptionCC+"','"+exceptionSubject+"','"
					+mailBody+"','text/html;charset=UTF-8','','','',1,'N','','','','','MailUtilItyUser1','TRIGGER',"
					+ "SYSDATE,'10','MQ-PROTRADE','1','10','0'",sessionId);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"[triggerExceptionMail] outputXml: "+inputXml);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "outputXml: "+outputXML);
		} catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR,"excpetion in triggerExceptionMail: ");
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR,e);
		}
	}
	 
}
