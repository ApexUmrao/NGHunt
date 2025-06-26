package com.newgen.tsservice.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class SingleUserConnection {
	private static SingleUserConnection suc;
	private int iUserCount;
	private static long sessionUpdateTime = System.currentTimeMillis();
	
	
	private static final Logger logger = LogManager.getLogger(SingleUserConnection.class);

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
	
	public  synchronized String getSession(String cabinetName, String sUsername, String sPassword) 
			throws NGException{
	logger.info("SingleUserConnection  {}", "Inside getSession");
		String sessionId = "";
		String sessionIdNew = "";
		int isValid = 0;
		int mainCode = 0;
		Object[] countCon = countAndGetCurrentConnection(sUsername);
		if(((Integer)countCon[0] > 0)){
			sessionId = (String)countCon[1];
			isValid = ValidateSession(sessionId);
			if(isValid == 0) {
				mainCode = updateConnection("USR_0_BPM_APPCONNECTION","USERNAME, SESSIONID, DATETIME",
						("'"+sUsername+"', '"+sessionId+"' , SYSDATE"),sessionId,isValid);
				if(mainCode != 0){
					return "-3";
				}
				else {
					return sessionId;
				}
			} else {
				sessionIdNew = makeNewConnection(sUsername, sPassword);
				mainCode = deleteConnection("USR_0_BPM_APPCONNECTION", " SESSIONID = '"+sessionId+"'", sessionIdNew);
				if(mainCode != 0){
					return "-4";
					
				}
				sessionId = sessionIdNew;
			}
		}
		else {
			sessionId = makeNewConnection(sUsername, sPassword);
		}
		
		       
        return sessionId;
	}
	
	private  int ValidateSession(String sessionId) throws NGException 
	{				
		String sOutputXML = ProdCreateXML.IsSessionValid(sessionId);
		XMLParser xp=new XMLParser(sOutputXML);   
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		return mainCode;
	}
	
	private  String makeNewConnection(String sUsername, String sPassword) throws NGException 
	{	
	logger.info("SingleUserConnection {}", "Inside makeNewConnection");
		String sessionId="-1";
		String sOutputXML = ProdCreateXML.WMConnect(sUsername, sPassword);
		XMLParser xp=new XMLParser(sOutputXML);   
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			sessionId = xp.getValueOf("SessionId");
			mainCode = updateConnection("USR_0_BPM_APPCONNECTION","USERNAME, SESSIONID, DATETIME",
					("'"+sUsername+"', '"+sessionId+"' , SYSDATE"),sessionId,-1);
			if(mainCode != 0){
				return "-2";
			}
		}
		else if(mainCode == 1){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			logger.info("SingleUserConnection {}", "Inside catch InterruptedExceptione"+e.getMessage());
			}
			Object[] countConn = countAndGetCurrentConnection(sUsername);
			sessionId = (String)countConn[1];
			return sessionId;
		}
		else{
			return sessionId;
		}
		
		return sessionId;
	}
	
	private  Object[] countAndGetCurrentConnection(String sUsername) throws NGException 
	{	
	logger.info("SingleUserConnection {}", "Inside countAndGetCurrentConnection");
		Object[] obj = new Object[2];
		String outputXML = APCallCreateXML.APSelect("SELECT SESSIONID FROM USR_0_BPM_APPCONNECTION WHERE USERNAME = '"+ sUsername +"'  order by DATETIME desc");

		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode == 0){
			int count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			if(count>0){
				obj[0] = count;
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
	logger.info("SingleUserConnection {}", "Inside updateConnection");
		int mainCode = 0;
		String outputXML;
		if(isValid != 0) {
			outputXML = APCallCreateXML.APInsert(tableName,columnNames, values, sessionId);
			XMLParser xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		}
		try {
			long prevTime = System.currentTimeMillis() - LapsConfigurations.getInstance().sessionInterval;
		logger.info("SingleUserConnection", "updateConnection prevTime: {}" 
					+ prevTime);
		logger.info("", "updateConnection sessionUpdateTime: {}" 
					+ sessionUpdateTime);
			if (sessionUpdateTime < prevTime) {
				sessionUpdateTime = System.currentTimeMillis();
				outputXML = APCallCreateXML.APProcedure(sessionId,"BPM_SESSION_UPDATE","'"+sessionId+"'",1);
			}
		} catch (Exception e) {
			logger.error("SingleUserConnection {}", "exception in updateConnection BPM_SESSION_UPDATE");
			logger.error("SingleUserConnection {}", e);
		}
		return mainCode;
	}
	
	private  int deleteConnection(String tableName, String where, String sessionId) throws NGException 
	{	
	logger.info("SingleUserConnection {}", "Inside deleteConnection");
		String outputXML = APCallCreateXML.APDelete(tableName, where, sessionId);
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		return mainCode;
	}
}
