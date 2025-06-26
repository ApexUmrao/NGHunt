package com.newgen.push.message;

import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.omni.wf.util.xml.XMLParser;
import com.newgen.push.message.APCallCreateXML;
import com.newgen.push.message.BPMReqLogMe;
import com.newgen.push.message.ProdCreateXML;

public class SingleUserConnection {
	private static SingleUserConnection suc;
	private int iUserCount;
	private static long sessionUpdateTime = System.currentTimeMillis();
	private static String sessionInterval;

	private SingleUserConnection(int iUserCount)
	{
		this.iUserCount = iUserCount;
	}

	public static synchronized SingleUserConnection getInstance(int iUserCount){
		if(suc == null){
			synchronized(SingleUserConnection.class){
				if(suc == null){
					suc = new SingleUserConnection(iUserCount);
				}
			}
		}
		return suc;
	}

	public String getSession(String cabinetName, String sUsername, String sPassword, String sessionInterval) throws NGException{
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside getSession");
		this.sessionInterval = sessionInterval;
		String sessionId = "";
		String sessionIdNew = "";
		int isValid = 0;
		int mainCode = 0;
		Object[] countCon = countAndGetCurrentConnection(sUsername);
		if(((Integer)countCon[0] > 0)){//&& ((Integer)countCon[0] <= iUserCount)
			sessionId = (String)countCon[1];
			isValid = ValidateSession(sessionId);
			if(isValid == 0) {
				mainCode = updateConnection("USR_0_BPM_APPCONNECTION","USERNAME, SESSIONID, DATETIME",
						("'"+sUsername+"', '"+sessionId+"' , SYSDATE"), sessionId, isValid);
				if(mainCode != 0){
					return "-3";
				}
				else {
					return sessionId;
				}
			}
			else {
				sessionIdNew = makeNewConnection(sUsername, sPassword);
				mainCode = deleteConnection("USR_0_BPM_APPCONNECTION", " SESSIONID = '"+sessionId+"'", sessionIdNew);
				if(mainCode != 0){
					return "-4";

				}
				sessionId = sessionIdNew;
				/*mainCode = updateConnection("USR_0_BPM_APPCONNECTION","USERNAME, SESSIONID, DATETIME",("'"+sUsername+"', '"+sessionId+"' , SYSDATE"),sessionId);
				if(mainCode != 0){
					return "-5";
				}*/
			}
		}
		else {
			sessionId = makeNewConnection(sUsername, sPassword);
			/*mainCode = updateConnection("USR_0_BPM_APPCONNECTION","USERNAME, SESSIONID, DATETIME",
					("'"+sUsername+"', '"+sessionId+"' , SYSDATE"),sessionId);
			if(mainCode != 0){
				return "-5";
			}*/
		}


		return sessionId;
	}

	private int ValidateSession(String sessionId) throws NGException 
	{				
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside ValidateSession");
		String sOutputXML = ProdCreateXML.IsSessionValid(sessionId);
		XMLParser xp=new XMLParser(sOutputXML);   
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside ValidateSession mainCode : "+mainCode);
		return mainCode;
	}

	private String makeNewConnection(String sUsername, String sPassword) throws NGException 
	{	
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside makeNewConnection");
		String sessionId="-1";
		String sOutputXML = ProdCreateXML.WMConnect(sUsername, sPassword);
		XMLParser xp=new XMLParser(sOutputXML);   
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			sessionId = xp.getValueOf("SessionId");
			mainCode = updateConnection("USR_0_BPM_APPCONNECTION","USERNAME, SESSIONID, DATETIME",
					("'"+sUsername+"', '"+sessionId+"' , SYSDATE"), sessionId, -1);
			if(mainCode != 0){
				return "-2";
			}
		}
		else {
			return sessionId;
		}

		return sessionId;
	}

	private Object[] countAndGetCurrentConnection(String sUsername) throws NGException 
	{	
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside countAndGetCurrentConnection");
		Object[] obj = new Object[2];
		String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT, SESSIONID FROM USR_0_BPM_APPCONNECTION WHERE USERNAME = '"+ sUsername +"' GROUP BY SESSIONID");
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
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

	private int updateConnection(String tableName, String columnNames, String values, String sessionId, int isValid) 
			throws NGException 
	{		
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside updateConnection");
		int mainCode = 0;
		String outputXML;
		if(isValid != 0) {
			outputXML = APCallCreateXML.APInsert(tableName,columnNames, values, sessionId);
			XMLParser xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		}
		try {
			long prevTime = System.currentTimeMillis() - (Long.parseLong(sessionInterval) * 60 * 1000);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "updateConnection prevTime: " 
					+ prevTime);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "updateConnection sessionUpdateTime: " 
					+ sessionUpdateTime);
			if (sessionUpdateTime < prevTime) {
				sessionUpdateTime = System.currentTimeMillis();
				outputXML = APCallCreateXML.APProcedure(sessionId,"BPM_SESSION_UPDATE","'"+sessionId+"'",1);
				/*XMLParser xp = new XMLParser(outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));*/
			}
		} catch (Exception e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, "exception in updateConnection BPM_SESSION_UPDATE");
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_ERROR, e);
		}
		return mainCode;
	}

	private int deleteConnection(String tableName, String where, String sessionId) throws NGException 
	{	
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "Inside deleteConnection");
		String outputXML = APCallCreateXML.APDelete(tableName, where, sessionId);
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		return mainCode;
	}
}
