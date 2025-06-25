


package com.newgen.ao.laps.util;

import java.io.IOException;

import com.newgen.ao.laps.logger.ArchivalLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.omni.wf.util.xml.XMLParser;
import com.newgen.wfdesktop.xmlapi.WFCallBroker;

public class EdmsUserConnection {
	private static EdmsUserConnection suc;
	private int iUserCount;
	private static long sessionUpdateTime = System.currentTimeMillis();;
	public String EdmsServerIP;
	public String EdmsServerPort;
	public String EdmsUserName;
	public String EdmsPassword;
	public String EdmsCabName;
	

	private EdmsUserConnection(int iUserCount)
	{
		this.iUserCount = iUserCount;
	}
	
	public static synchronized EdmsUserConnection getInstance(int iUserCount){
		if(suc == null){
			synchronized(EdmsUserConnection.class){
				if(suc == null){
					suc = new EdmsUserConnection(iUserCount);
				}
			}
		}
		return suc;
	}
	
	public  synchronized String getSession(String EdmsCabName,String EdmsUserName,String EdmsPassword,String EdmsServerIP,String EdmsServerPort) 
			throws Exception{
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside getSession");
		String sessionId = "";
		String sessionIdNew = "";
		int isValid = 0;
		Object[] countCon = countAndGetCurrentConnection(EdmsUserName);
		if(((Integer)countCon[0] > 0)){
			sessionId = (String)countCon[1];
			isValid = ValidateSession(sessionId,EdmsServerIP,EdmsServerPort);
			if(isValid == 0) {
					return sessionId;
			} else {
				sessionIdNew = makeNewConnection(EdmsCabName, EdmsUserName, EdmsPassword, EdmsServerIP, EdmsServerPort);
				sessionId = sessionIdNew;
			}
		}
		else {
			sessionId = makeNewConnection(EdmsCabName, EdmsUserName, EdmsPassword, EdmsServerIP, EdmsServerPort);
		}
        return sessionId;
	}
	
	private  int ValidateSession(String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception 
	{				
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside ValidateSession");
		String sOutputXML = ProdCreateXML.IsEdmsSessionValid(sessionId,jtsIP,jtsPort);
		XMLParser xp=new XMLParser(sOutputXML);   
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		return mainCode;
	}
	
	private  String makeNewConnection(String cabinetName,String username,String password ,String jtsIP,String jtsPort) throws Exception 
	{
		try
		{		
			String connectInputXML = "<?xml version=\"1.0\"?><NGOConnectCabinet_Input><Option>NGOConnectCabinet</Option><CabinetName>"+cabinetName+"</CabinetName>"
					        + "<UserName>"+username+"</UserName><UserPassword>"+password+"</UserPassword><CurrentDateTime></CurrentDateTime>"
							+ "<UserExist>N</UserExist><MainGroupIndex>0</MainGroupIndex><UserType>S</UserType><Locale>en-us</Locale>"
							+ "<ApplicationName>N</ApplicationName><Hook>Omnidocs Admin</Hook></NGOConnectCabinet_Input>";
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"connectInputXML :" + connectInputXML);
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"jtsIP :" + jtsIP);
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"jtsPort :" + jtsPort);
			String connectOutputXML = WFCallBroker.execute(connectInputXML,jtsIP,Integer.parseInt(jtsPort),1);
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"connectOutputXML :" + connectOutputXML);
			XMLParser xml=new XMLParser(connectOutputXML);	
			String mainCode = xml.getValueOf("Status");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"mainCode :" + mainCode);
			mainCode = mainCode.trim();	
			if (!mainCode.equalsIgnoreCase("0")) 
			{

				String errorDesc =xml.getValueOf("Error").trim();
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"errorDesc :" + errorDesc);
				throw new Exception("Workflow Server Down");
			}  

			String sessionId = xml.getValueOf("UserDBId");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"sessionId :" + sessionId);
			sessionId = sessionId.trim();


			if (sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null"))
			{
				throw new Exception("Workflow Server Down");
			}
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"\nSuccessfully logged in into workflow");

			return sessionId;

		}
		catch(Exception e)
		{
			throw e;

		}
	}
	
	private Object[] countAndGetCurrentConnection(String sUsername) throws NGException 
	{	
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside countAndGetCurrentConnection");
		Object[] obj = new Object[2];
		String outputXML = APCallCreateXML.APSelectEdms("select COUNT(1) AS COUNT,randomnumber as SESSIONID  from pdbconnection where USERINDEX = (select userindex from pdbuser where upper(username) = '"+ sUsername.toUpperCase() +"') GROUP BY randomnumber");
		XMLParser xp = new XMLParser(outputXML);
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "countAndGetCurrentConnection "+outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "countAndGetCurrentConnection "+mainCode);
		if(mainCode == 0){
			if(xp.getNoOfFields("COUNT") > 0){
				obj[0] = Integer.parseInt(xp.getValueOf("COUNT"));
				obj[1] = xp.getValueOf("SESSIONID");
			}
			else {
				obj[0] = -1;
				obj[1] = "-1";				
			}
		}
		
		
		return obj;
	}
	
	private  int updateConnection(String tableName, String columnNames, String values, String sessionId, int isValid) 
			throws NGException {		
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside updateConnection");
		int mainCode = 0;
		String outputXML;
		if(isValid != 0) {
			outputXML = APCallCreateXML.APInsert(tableName,columnNames, values, sessionId);
			XMLParser xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		}
		try {
			long prevTime = System.currentTimeMillis() - LapsConfigurations.getInstance().sessionInterval;
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "updateConnection prevTime: " 
					+ prevTime);
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "updateConnection sessionUpdateTime: " 
					+ sessionUpdateTime);
			if (sessionUpdateTime < prevTime) {
				sessionUpdateTime = System.currentTimeMillis();
				outputXML = APCallCreateXML.APProcedure(sessionId,"BPM_SESSION_UPDATE","'"+sessionId+"'",1);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in updateConnection BPM_SESSION_UPDATE");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return mainCode;
	}
	
	private  int deleteConnection(String tableName, String where, String sessionId,String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception 
	{	
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "Inside deleteConnection");
		String outputXML = APCallCreateXML.APDeleteEdms(tableName, where, sessionId, jtsIP, jtsPort);
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		return mainCode;
	}
}
