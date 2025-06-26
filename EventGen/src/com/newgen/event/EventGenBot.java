package com.newgen.event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import adminclient.OSASecurity;

import com.newgen.AESEncryption;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.util.ConnectSocket;
import com.newgen.util.ExecuteXML;
import com.newgen.util.GenerateXml;
//import com.newgen.tfo.jms.ExecuteXML;
import com.newgen.util.Log;
import com.newgen.util.CPSPrepareDocument;



class EventGenBot extends Log implements Runnable {
	private static String cabinetName;
	private static String username;
	private static String password;
	private static String serverIp;
	private static String socketIP;
	private static String jndiPort;
	private static String sessionId = "";
	private static int sleepTime = 10000;
	String pid;
	private static int wiCount = 0;
	private boolean errorFlag = false;
	private static String dbURL;
	private static String dbUser;
	private static String dbPass;
	private static String exceptionFrom;
	private static String exceptionTo;
	private static String exceptionCC;
	private static String exceptionSubject;
	private static String exceptionMail;
	private static int maxTrialCount;
	private String expiryDateInterval;
	private ConnectSocket socket;
	private static long sessionInterval;
	private static int schedulerInterval;
	private long prevUpdateTime = System.currentTimeMillis();
	public EventGenBot() {
		try {
			readConfig();  
			ExecuteXML.init("WebSphere", serverIp, String.valueOf(jndiPort));
			initializeSocket();
		} catch (Exception ex) {
			errorFlag=true;
			handleUtilityException(ex.toString());
			logger.info("Error during reading the configuration file.");
		}
		logger.info("Inside ThreadClient() constructor.");
	}

	public void run() {
		connectToWorkflow();
		setCustomServiceStatus("14011","Started","0");
		while (true) {
			if (sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null") || !isSessionIdValid(sessionId)) {
				connectToWorkflow();
			}
			try {
				if (!(null == sessionId || sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null"))){
					loadAndProcessWorkItems();
				}
			} catch (Exception exp) {
				customException("Exception...", exp);
			} finally {
				if(errorFlag) {
					setCustomServiceStatus("-25033","Stopped",0+"");
					System.exit(0);
				} 
			}
		}
	}

	private boolean isSessionIdValid(String sessionId) {
		String sOutputXML = "";
		try {
			logger.info("[isSessionIdValid]searchTimestamp : " + prevUpdateTime);
			long prevTime = System.currentTimeMillis() - sessionInterval;
			logger.info("[isSessionIdValid]prevTime : " + prevTime);
			if (prevUpdateTime < prevTime) {
				prevUpdateTime = System.currentTimeMillis();
				sOutputXML = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_SESSION_UPDATE","'"+sessionId+"'",1);
			}
			logger.info("WFSESSIONVIEW update : "+sOutputXML);
			logger.info("session id check starts for : "+sessionId);
			sOutputXML = GenerateXml.IsSessionValid(sessionId, cabinetName);
			logger.info("session id check sOutputXML : "+sOutputXML);
			XMLParser xp=new XMLParser(sOutputXML);   
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode != 0){
				return false;
			}
		} catch (Exception e) {
			logger.error("Error  while reading config file...", e);
		}
		return true;
	}

	private void readConfig() throws IOException {
		try {
			Properties p = null;
			p = new Properties();
			p.load(new FileInputStream("config.properties"));
			username = p.getProperty("username");
			password = p.getProperty("password");
			cabinetName = p.getProperty("CabinetName");
			serverIp = p.getProperty("JTSIP");
			socketIP = p.getProperty("socketIP");
			jndiPort = p.getProperty("JTSPort");	
			dbURL = p.getProperty("DBURL");			
			dbUser = p.getProperty("DBUSER");			
			dbPass = p.getProperty("DBPASS");	
			exceptionFrom = p.getProperty("exceptionFrom");
			exceptionTo = p.getProperty("exceptionTo");
			exceptionCC = p.getProperty("exceptionCC");
			exceptionSubject = p.getProperty("exceptionSubject");
			exceptionMail = p.getProperty("exceptionMail");
			maxTrialCount=Integer.parseInt(p.getProperty("maxTrialCount"));
			expiryDateInterval=p.getProperty("expiryDateInterval");
			sessionInterval = Long.parseLong(p.getProperty("sessionInterval")) * 60 * 1000;
			schedulerInterval = Integer.parseInt(p.getProperty("schedulerInterval"));
			
			try {
				File myObj = new File("WFServiceConfig.ini");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					if(data != null && !"".equalsIgnoreCase(data))
					{
						pid = data.split("=")[1];
					}
				}
				myReader.close();
				logger.info("pid : "+pid);
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}			
			try {
				password = AESEncryption.decrypt(password);
			} catch (Exception ex) {
				customException("Error while decypting the password... ", ex);
			}
		} catch (IOException exp) {
			customException("Error  while reading config file...", exp);
		}
	}


	@SuppressWarnings("unused")
	private void connectToWorkflow(){
		try {			
			if (username == null || password == null) {
				throw new EventGenException("Username or Password not specified.");
			}
			logger.info("cabinetName= " + cabinetName + " username-" + username);
			String connectInputXML = GenerateXml.getConnectInputXML(cabinetName, username, password);
			logger.info("Connect Input XML:\n" + modifyInputXML(connectInputXML));
			String connectOutputXML = ExecuteXML.executeXML(connectInputXML);
			logger.info(connectOutputXML);
			String mainCode = getTagValue(connectOutputXML, "MainCode");
			mainCode = mainCode.trim();
			logger.info("main code: " + mainCode);
			if (!mainCode.equalsIgnoreCase("0")) {
				logger.info("Error during connection with workflow.");	
			}
			sessionId = getTagValue(connectOutputXML, "SessionId").trim();
			logger.info("session id: " + sessionId);
			if(null == sessionId || sessionId.equalsIgnoreCase("") || sessionId.equalsIgnoreCase("null")) {
				errorFlag = true;
				logger.info("Unable to login, some problem occurred");
				handleUtilityException("Unable to login, some problem occurred");
			}
			logger.info("Successfully logged in into workflow");
		} catch (Exception e) {
			errorFlag = true;
			handleUtilityException(e.toString());
			logger.error("exception in connectToWorkflow: ",e);;
		}
	}

	private void loadAndProcessWorkItems() throws NumberFormatException, IOException, Exception {
		int mainCode = -1;
		String wiName = "";
		String processName = "";
		String sourcingChannel = "";
		String mode = "";
		String refNo = "";
		String statusCode = "";
		String currws = "";
		String finalStatus = "";
		int trialCount=0;
		String expiryDate = "";
		boolean moveToHistoryFlag=false;
		try {
			try {
				String driver = "oracle.jdbc.driver.OracleDriver";
				Connection conn = null;
				try
				{
					Class.forName(driver);
					logger.info("After database values");
					String password = decryptPassword(dbPass);
					//			    	logger.info("decrypted password: " + password);
					 conn = DriverManager.getConnection(dbURL, dbUser, password);
					CallableStatement cstmt = null;
					cstmt = conn.prepareCall("{call BPMGETNEXTWORKITEMFOREVENTGEN(?,?)}");
					cstmt.setString(1, username);
					cstmt.registerOutParameter(2, -10);
					cstmt.execute();
					ResultSet rs = (ResultSet) cstmt.getObject(2);
					if(null != rs) {
						logger.info("BPMGETNEXTWORKITEMFOREVENTGEN returned ResultSet: "+rs.toString());
						while(rs.next()) {
							wiName = rs.getString("WI_NAME");
							processName = rs.getString("PROCESS_NAME");
							sourcingChannel = rs.getString("SOURCING_CHANNEL");
							mode = rs.getString("REQUESTMODE");
							trialCount = rs.getInt("TRIAL_COUNT");
							expiryDate = rs.getString("EXPIRY_DATE");
							mainCode = 0;
							logger.info("wiName: "+wiName+", processName: "+processName+", sourcingChannel: "+sourcingChannel
									+", mode: "+mode+", trialCount: "+trialCount +", Expiry date: "+expiryDate);
						}
					}
					conn.close();
					conn = null;
				} catch(Exception e) {
					logger.error("exception in BPMGETNEXTWORKITEMFOREVENTGEN 0: ",e);
				} finally {
					if(conn != null){
						try {
							conn.close();
							conn = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				logger.info("mainCode: "+mainCode);
				logger.info("wiName: "+wiName+", processName: "+processName+", sourcingChannel: "+sourcingChannel
						+", mode: "+mode);
			} catch (Exception e) {
				logger.error("exception in BPMGETNEXTWORKITEMFOREVENTGEN: ",e);
				errorFlag = true;
			}
			if (0 == mainCode) {
				if("TFO".equalsIgnoreCase(processName)) {
					 String outputXml=ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,
							"Select WORKITEMSTATE,ACTIVITYNAME from wfinstrumenttable where PROCESSINSTANCEID = N'"+wiName+"'",sessionId));
					com.newgen.omni.jts.cmgr.XMLParser xpl = new com.newgen.omni.jts.cmgr.XMLParser(outputXml);
					logger.info("WorkitemState = " +xpl.getValueOf("WORKITEMSTATE")
							+ "ACTIVITYNAME" + xpl.getValueOf("ACTIVITYNAME"));
					String sQuery1 = "select curr_ws from ext_tfo where wi_name  = N'"+wiName+"'";
					String currwsListInputXML = GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId);
					String currwsListOutputXML = ExecuteXML.executeXML(currwsListInputXML);
					XMLParser xmlDataParser1 = new XMLParser(currwsListOutputXML);
					currws = xmlDataParser1.getValueOf("curr_ws");
					if(currws.equals("ToDoList")||currws.equalsIgnoreCase("TRSD Action") 
							|| ("3".equalsIgnoreCase(xpl.getValueOf("WORKITEMSTATE")) &&
							"Distribute2".equalsIgnoreCase(xpl.getValueOf("ACTIVITYNAME")))) {	
						setCustomServiceStatus("14017","Processing Workitem",1+"");
						logger.info("WorkItem Name: " +wiName);
						String seqQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
						outputXml = GenerateXml.APSelectWithColumnNames(cabinetName,seqQuery,sessionId);
						logger.info("The InputXml for SEQ Workitem Call = " + outputXml);
						String seqQueryOutputXML =  ExecuteXML.executeXML(outputXml);
						logger.info("The OutputXml for SEQ Workitem Call = " + seqQueryOutputXML);
						XMLParser xp1 = new XMLParser(seqQueryOutputXML);
						int mainCodeSeq = Integer.parseInt(xp1.getValueOf("MainCode"));
						if(mainCodeSeq == 0){
							refNo = xp1.getValueOf("REFNO");  // all ref stxn
						}
						
						String webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId , cabinetName , 
								wiName ,refNo, processName, sourcingChannel, mode);
						logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
						//						String webserviceOutput =  ExecuteXML.executeXML(webserviceInput);
						String webserviceOutput =  socket.connectToSocket(webserviceInput);
						logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
						if(webserviceOutput != null && !webserviceOutput.isEmpty()){
							XMLParser xp2 = new XMLParser(webserviceOutput);
							statusCode = xp2.getValueOf("StatusCode");	
						}
						logger.info("statusCode= " + statusCode+"trialCount="+trialCount+"maxTrialCount="+maxTrialCount+"expiryDateInterval="+expiryDateInterval);
						if((null == webserviceOutput || "".equalsIgnoreCase(webserviceOutput)
								|| null == statusCode || "".equalsIgnoreCase(statusCode) 
								|| "100".equalsIgnoreCase(statusCode)) && trialCount < maxTrialCount){
							outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE_DATETIME",
									"'"+wiName+"','"+String.valueOf(trialCount+1)+"','"+
											("EXPIRY_DATE+"+expiryDateInterval)+"'",3);
							logger.info("trialCount and expiry date updateQuery response " + outputXml);
							moveToHistoryFlag = false;
							if((trialCount+1) == maxTrialCount){
								finalStatus = "F";
								moveToHistoryFlag = true;
								handleUtilityException("Unable to generate the events for: "+wiName+"");
							}
						} else if("0".equalsIgnoreCase(statusCode)){
							finalStatus = "Y";
							moveToHistoryFlag = true;
						} else {
							finalStatus = "F";
							moveToHistoryFlag = true;
							sQuery1 = "select CURR_WS from ext_tfo where wi_name  = N'"+wiName+"'";
							outputXml = ExecuteXML.executeXML(
									GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
							xmlDataParser1 = new XMLParser(outputXml);
							currws = xmlDataParser1.getValueOf("CURR_WS");
							if("ToDoList".equalsIgnoreCase(currws)&& trialCount < maxTrialCount) {
								String updateQuery =GenerateXml.APUpdate("EXT_TFO", "ROUTE_TO_REPAIR", "'Y'", 
										"WI_NAME = '"+wiName+"'", sessionId , cabinetName);
								GenerateXml.getGetWorkItemXML(wiName, "1", sessionId , cabinetName);
								GenerateXml.getWICompleteXML(wiName, "1", sessionId,cabinetName);
							}
						}
						logger.info("moveToHistoryFlag= " + moveToHistoryFlag);
						if(moveToHistoryFlag) {
							outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
									+wiName+"','"+finalStatus+"','"+mode+"'",2);
							logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
							if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
							xmlDataParser1 = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
							if(mainCode != 0) {
								logger.info("AP Procedure call failed");
								String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG", 
										"'"+finalStatus+"'", "WI_NAME = N'"+wiName+"' AND REQUESTMODE = '"+mode+"'", 
										sessionId , cabinetName) ;
								xmlDataParser1 = new XMLParser(updateQuery);
								mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
								if(mainCode != 0) {
									errorFlag = true;
									handleUtilityException("Unable to generate the events for: "+wiName+"");
								}
							}
							}  // ATP-439 REYAZ 26-03-2024
						}
					} else if (sourcingChannel.equalsIgnoreCase("UTC")) {
						logger.info("UTC ");
						String seqQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
						outputXml = GenerateXml.APSelectWithColumnNames(cabinetName,seqQuery,sessionId);
						logger.info("The InputXml for SEQ Workitem Call = " + outputXml);
						String seqQueryOutputXML =  ExecuteXML.executeXML(outputXml);
						logger.info("The OutputXml for SEQ Workitem Call = " + seqQueryOutputXML);
						XMLParser xp1 = new XMLParser(seqQueryOutputXML);
						int mainCodeSeq = Integer.parseInt(xp1.getValueOf("MainCode"));
						if(mainCodeSeq == 0){
							refNo = xp1.getValueOf("REFNO");  // all ref stxn
						}
						String webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId , cabinetName , 
								wiName ,refNo, processName, sourcingChannel, mode);
						logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
						//						String webserviceOutput =  ExecuteXML.executeXML(webserviceInput);
						String webserviceOutput =  socket.connectToSocket(webserviceInput);
						logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
						finalStatus = "Y";
						outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
								+wiName+"','"+finalStatus+"','"+mode+"'",2);
						logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
					} else if("ARCHIVAL".equalsIgnoreCase(sourcingChannel)) { //added by reyaz 30-08-2023
						logger.info(" TFO Archival WorkItem Name: " +wiName);
						String seqQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
						outputXml = GenerateXml.APSelectWithColumnNames(cabinetName,seqQuery,sessionId);
						logger.info("The InputXml for SEQ Workitem Call = " + outputXml);
						String seqQueryOutputXML =  ExecuteXML.executeXML(outputXml);
						logger.info("The OutputXml for SEQ Workitem Call = " + seqQueryOutputXML);
						XMLParser xp1 = new XMLParser(seqQueryOutputXML);
						int mainCodeSeq = Integer.parseInt(xp1.getValueOf("MainCode"));
						if(mainCodeSeq == 0){
							refNo = xp1.getValueOf("REFNO"); 
						}
						String webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId , cabinetName , 
								wiName ,refNo, processName, sourcingChannel, mode);
						logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
						String webserviceOutput =  socket.connectToSocket(webserviceInput);
						logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
						if(webserviceOutput != null && !webserviceOutput.isEmpty()){
							XMLParser xp2 = new XMLParser(webserviceOutput);
							statusCode = xp2.getValueOf("StatusCode");	
						}
						logger.info("statusCode= " + statusCode+"trialCount="+trialCount+"maxTrialCount="+maxTrialCount+"expiryDateInterval="+expiryDateInterval);
						if((null == webserviceOutput || "".equalsIgnoreCase(webserviceOutput)
								|| null == statusCode || "".equalsIgnoreCase(statusCode) 
								|| "100".equalsIgnoreCase(statusCode)) && trialCount < maxTrialCount){
							outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE_DATETIME",
									"'"+wiName+"','"+String.valueOf(trialCount+1)+"','"+
											("EXPIRY_DATE+"+expiryDateInterval)+"'",3);
							logger.info("trialCount and expiry date updateQuery response " + outputXml);
							moveToHistoryFlag = false;
							if((trialCount+1) == maxTrialCount){
								finalStatus = "F";
								moveToHistoryFlag = true;
								handleUtilityException("Unable to generate the events for: "+wiName+"");
							}
						} else if("0".equalsIgnoreCase(statusCode)){
							finalStatus = "Y";
							moveToHistoryFlag = true;
						} else {
							finalStatus = "F";
							moveToHistoryFlag = true;	
						}
						logger.info("moveToHistoryFlag= " + moveToHistoryFlag);
						if(moveToHistoryFlag) {
							outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
									+wiName+"','"+finalStatus+"','"+mode+"'",2);
							logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
							if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
							XMLParser xp2 = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
							if(mainCode != 0) {
								logger.info("AP Procedure call failed");
								String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG", 
										"'"+finalStatus+"'", "WI_NAME = N'"+wiName+"' AND REQUESTMODE = '"+mode+"'", 
										sessionId , cabinetName) ;
								xp2 = new XMLParser(updateQuery);
								mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
								if(mainCode != 0) {
									errorFlag = true;
									handleUtilityException("Unable to generate the events for: "+wiName+"");
								}
							}
						}
						}
					} else {
						 outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
								+wiName+"','X','"+mode+"'",2);
						logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
						if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
						xmlDataParser1 = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
						if(mainCode != 0) {
							logger.info("AP Procedure call failed");
							String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG", 
									"'X'", "WI_NAME = N'"+wiName+"' AND REQUESTMODE = '"+mode+"'", 
									sessionId , cabinetName) ;
							xmlDataParser1 = new XMLParser(updateQuery);
							mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
							if(mainCode != 0) {
								errorFlag = true;
								handleUtilityException("Unable to generate the events for: "+wiName+"");
							}
						}
					}
					}
					Thread.sleep(1000);
				} else if("CCL".equalsIgnoreCase(processName)){
					logger.info("retryFailedCalls for process CCL");
					CCLRetry.retryFailedCalls(sessionId,cabinetName,serverIp,jndiPort,processName,wiName,sourcingChannel,mode);
				} else if("WBG_AO".equalsIgnoreCase(processName)||"WBG".equalsIgnoreCase(processName)) {
					String sQuery1 = "select curr_ws from ext_wbg_ao where wi_name  = N'"+wiName+"'";
					String currwsListInputXML = GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId);
					String currwsListOutputXML = ExecuteXML.executeXML(currwsListInputXML);
					XMLParser xmlDataParser1 = new XMLParser(currwsListOutputXML);
					currws = xmlDataParser1.getValueOf("curr_ws");
					if(currws.equals("TRSD SCREENING")) {	
						setCustomServiceStatus("14017","Processing Workitem",1+"");
						logger.info("WorkItem Name: " +wiName);
						String seqQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
						String outputXml = GenerateXml.APSelectWithColumnNames(cabinetName,seqQuery,sessionId);
						logger.info("The InputXml for SEQ Workitem Call = " + outputXml);
						String seqQueryOutputXML =  ExecuteXML.executeXML(outputXml);
						logger.info("The OutputXml for SEQ Workitem Call = " + seqQueryOutputXML);
						XMLParser xp1 = new XMLParser(seqQueryOutputXML);
						int mainCodeSeq = Integer.parseInt(xp1.getValueOf("MainCode"));
						if(mainCodeSeq == 0){
							refNo = xp1.getValueOf("REFNO");  // all ref stxn
						}
						String webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId , cabinetName , 
								wiName ,refNo, processName, sourcingChannel, mode);
						logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
						//						String webserviceOutput =  ExecuteXML.executeXML(webserviceInput);
						String webserviceOutput =  socket.connectToSocket(webserviceInput);
						logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
						if(webserviceOutput != null && !webserviceOutput.isEmpty()){
							XMLParser xp2 = new XMLParser(webserviceOutput);
							statusCode = xp2.getValueOf("StatusCode");	
						}
						logger.info("statusCode= " + statusCode+"trialCount="+trialCount+"maxTrialCount="+maxTrialCount+"expiryDateInterval="+expiryDateInterval);
						if((null == webserviceOutput || "".equalsIgnoreCase(webserviceOutput)
								|| null == statusCode || "".equalsIgnoreCase(statusCode) 
								|| "100".equalsIgnoreCase(statusCode)) && trialCount < maxTrialCount){
							outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE_DATETIME",
									"'"+wiName+"','"+String.valueOf(trialCount+1)+"','"+
											("EXPIRY_DATE+"+expiryDateInterval)+"'",3);
							logger.info("trialCount and expiry date updateQuery response " + outputXml);
							moveToHistoryFlag = false;
							if((trialCount+1) == maxTrialCount){
								finalStatus = "F";
								moveToHistoryFlag = true;
								handleUtilityException("Unable to generate the events for: "+wiName+"");
							}
						} else if("0".equalsIgnoreCase(statusCode)){
							finalStatus = "Y";
							moveToHistoryFlag = true;
						} else {
							finalStatus = "F";
							moveToHistoryFlag = true;
							sQuery1 = "select CURR_WS from ext_wbg_ao where wi_name  = N'"+wiName+"'";
							outputXml = ExecuteXML.executeXML(
									GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
							xmlDataParser1 = new XMLParser(outputXml);
							currws = xmlDataParser1.getValueOf("CURR_WS");
							
						}
						logger.info("moveToHistoryFlag= " + moveToHistoryFlag);
						if(moveToHistoryFlag) {
							outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
									+wiName+"','"+finalStatus+"','"+mode+"'",2);
							logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
							if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
							xmlDataParser1 = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
							if(mainCode != 0) {
								logger.info("AP Procedure call failed");
								String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG", 
										"'"+finalStatus+"'", "WI_NAME = N'"+wiName+"' AND REQUESTMODE = '"+mode+"'", 
										sessionId , cabinetName) ;
								xmlDataParser1 = new XMLParser(updateQuery);
								mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
								if(mainCode != 0) {
									errorFlag = true;
									handleUtilityException("Unable to generate the events for: "+wiName+"");
								}
							}
                                                     }
						}
					} else {
						String outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
								+wiName+"','X','"+mode+"'",2);
						logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
						xmlDataParser1 = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
						if(mainCode != 0) {
							logger.info("AP Procedure call failed");
							String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG", 
									"'X'", "WI_NAME = N'"+wiName+"' AND REQUESTMODE = '"+mode+"'", 
									sessionId , cabinetName) ;
							xmlDataParser1 = new XMLParser(updateQuery);
							mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
							if(mainCode != 0) {
								errorFlag = true;
								handleUtilityException("Unable to generate the events for: "+wiName+"");
							}
						}
					}
					Thread.sleep(1000);
				} 
				
				//Added for AO UAE Pass Info
				else if ("AO".equalsIgnoreCase(processName))
				{
					logger.info("inside AO process");

					try
					{
						if(wiName.startsWith("AO-"))
						{	
							String webserviceOutput;
							String webserviceInput;
							String sQuery1 = "select CURR_WS_NAME from ext_ao where wi_name  = N'"+wiName+"'";
							String currwsListInputXML = GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId);
							String currwsListOutputXML = ExecuteXML.executeXML(currwsListInputXML);
							XMLParser xmlDataParser1 = new XMLParser(currwsListOutputXML);
							currws = xmlDataParser1.getValueOf("CURR_WS_NAME");
							logger.info(" currws " + currws);
							logger.info(" mode " + mode);
							if(currws.equalsIgnoreCase("UAE_Pass_Pending") && mode.equalsIgnoreCase("M")){
								webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId, cabinetName, 
										wiName, wiName, processName, sourcingChannel, mode);
								logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
								webserviceOutput = ConnectSocket.connectToSocket(webserviceInput);
								logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
								XMLParser xp = new XMLParser(webserviceOutput);
								statusCode = xp.getValueOf("StatusCode");
								if (!(statusCode.equalsIgnoreCase("0")))
									logger.info(" WebserviceOutput Error ");
							}  else if ( mode.equalsIgnoreCase("Q")) {
								logger.info("Current Workstepname = " + currws);
								String seqQuery = "SELECT SEQ_WEBSERVICE.nextval as REFNO from DUAL";
								webserviceOutput = GenerateXml.APSelectWithColumnNames(cabinetName, seqQuery, sessionId);
								logger.info("The InputXml for SEQ Workitem Call = " + webserviceOutput);
								String seqQueryOutputXML = ExecuteXML.executeXML(webserviceOutput);
								logger.info("The OutputXml for SEQ Workitem Call = " + seqQueryOutputXML);
								XMLParser xp1 = new XMLParser(seqQueryOutputXML);
								int mainCodeSeq = Integer.parseInt(xp1.getValueOf("MainCode"));
								if (mainCodeSeq == 0) {
									refNo = xp1.getValueOf("REFNO");
								}
								webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId, cabinetName, 
										wiName, refNo, processName, sourcingChannel, mode);
								logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
								webserviceOutput = ConnectSocket.connectToSocket(webserviceInput);
								logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
								XMLParser xp = new XMLParser(webserviceOutput);
								statusCode = xp.getValueOf("StatusCode");
								if (!(statusCode.equalsIgnoreCase("0")))
									logger.info(" WebserviceOutput Error ");
							}else {

								String Query1 = "select linked_wi from bpm_cop_lead_details where wi_name=N'" + wiName + "'";
								String outputXml1 = ExecuteXML.executeXML(
										GenerateXml.APSelectWithColumnNames(cabinetName, Query1, sessionId));
								XMLParser xmlDataParser3 = new XMLParser(outputXml1);
								String linkWI = xmlDataParser3.getValueOf("linked_wi");
								logger.info(" Query1 : " + Query1);
								logger.info(" linkWI : " + linkWI);
								if (!(linkWI.equalsIgnoreCase(""))) {
									logger.info(" inside link WI is not null : " + linkWI);
									String Query2 = "select count(1) as count from acc_relation_repeater where wi_name=N'" + linkWI + "' and DECEASED ='true'";
									String outputXml3 = ExecuteXML.executeXML(
											GenerateXml.APSelectWithColumnNames(cabinetName, Query2, sessionId));
									XMLParser xmlDataParser4 = new XMLParser(outputXml3);
									String count = xmlDataParser4.getValueOf("count");
									logger.info(" Query1 : " + Query2);
									logger.info(" linkWI : " + count);
									if (count.equalsIgnoreCase("0")) {
										String sQuery2 = "select is_initiated_uae_pass,is_uae_pass_auth_done,uae_pass_auth_flag from ext_ao where wi_name  = N'" + wiName + "'";
										String outputXml = ExecuteXML.executeXML(
												GenerateXml.APSelectWithColumnNames(cabinetName,sQuery2,sessionId));
										XMLParser xmlDataParser2 = new XMLParser(outputXml);
										String flag1 = xmlDataParser2.getValueOf("is_initiated_uae_pass");
										String flag2 = xmlDataParser2.getValueOf("is_uae_pass_auth_done");
										String flag3 = xmlDataParser2.getValueOf("uae_pass_auth_flag");
										logger.info("inside AO process flags: " + flag1 +" ::::" + flag2 + "::::::" +flag3);
										if(flag1.equalsIgnoreCase("Y") && flag2.equalsIgnoreCase("P") && flag3.equalsIgnoreCase("Y"))
										{
											String sQuery3 = "select sysdate from dual";
											String outputXml5 = ExecuteXML.executeXML(
													GenerateXml.APSelectWithColumnNames(cabinetName,sQuery3,sessionId));
											XMLParser xmlDataParser5 = new XMLParser(outputXml5);
											String sysdate = xmlDataParser5.getValueOf("sysdate");

											logger.info("sysdate extracted");
											Date expiry =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiryDate);
											Date sysDateFinal =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sysdate);

											logger.info("inside AO expiry : " + expiry);
											logger.info("inside AO sysDateFinal : " + sysDateFinal);

											if(expiry.before(sysDateFinal) || expiry.equals(sysDateFinal))
											{
												int maincode1 = updateExpiryFlg(wiName);
												int maincode2 = updateExpiryFlg(linkWI);
												if (maincode1 == 0 && maincode2 == 0)
												{
													int returnCode1 =getWIForExpiry(wiName);
													int returnCode2 =getWIForExpiry(linkWI);
													if (returnCode1 == 0 && returnCode2 == 0)
													{
														compWIExpiry(wiName);
														compWIExpiry(linkWI);
													}
													else
													{
														logger.info("AO update/complete failed");
													}
												}
												else
												{
													logger.info("AO update/complete failed");
												}
											}
										}
									} else {
										logger.info("Count is not null Skip Flag is choosed for the Work Item");
										logger.info("Workitem is not expired as Skip Flag is selected");
									}
								} else {
									logger.info("Link WI is null so expiring the main Workitem");
									String sQuery2 = "select is_uae_pass_auth_done,uae_pass_auth_flag from ext_ao where wi_name  = N'" + wiName + "'";
									String outputXml = ExecuteXML.executeXML(
											GenerateXml.APSelectWithColumnNames(cabinetName, sQuery2, sessionId));
									XMLParser xmlDataParser2 = new XMLParser(outputXml);
									String flag2 = xmlDataParser2.getValueOf("is_uae_pass_auth_done");
									String flag3 = xmlDataParser2.getValueOf("uae_pass_auth_flag");
									logger.info("inside AO process flags ::::" + flag2 + "::::::" + flag3);
									if (flag2.equalsIgnoreCase("P") && flag3.equalsIgnoreCase("Y"))
									{
										String sQuery3 = "select sysdate from dual";
										String outputXml5 = ExecuteXML.executeXML(
												GenerateXml.APSelectWithColumnNames(cabinetName, sQuery3, sessionId));
										XMLParser xmlDataParser5 = new XMLParser(outputXml5);
										String sysdate = xmlDataParser5.getValueOf("sysdate");

										logger.info("sysdate extracted");
										Date expiry = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiryDate);
										Date sysDateFinal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sysdate);

										logger.info("inside AO expiry : " + expiry);
										logger.info("inside AO sysDateFinal : " + sysDateFinal);

										if (expiry.before(sysDateFinal) || expiry.equals(sysDateFinal))    
										{
											int maincode1 = updateExpiryFlg(wiName);

											if (maincode1 == 0)
											{
												int returnCode1 =getWIForExpiry(wiName);
												if (returnCode1 == 0)
												{
													compWIExpiry(wiName);
												}
												else
												{
													logger.info("AO update/complete failed");
												}
											}	
											else
											{
												logger.info("AO update/complete failed");
											}
										}
									}
								}
							}

						}
						moveToHistoryFlag = true;
						finalStatus = "Y";
						logger.info("moveToHistoryFlag= " + moveToHistoryFlag);
						if(moveToHistoryFlag) {
							GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
									+wiName+"','"+finalStatus+"','"+mode+"'",2);
						}
					}
					catch(Exception e)
					{
						e.getMessage();
					}

					//AO End

				}else if("CPS".equalsIgnoreCase(processName) && "CPS_SCHEDULER".equalsIgnoreCase(sourcingChannel)){
					logger.info("inside cps and sourcingChannel is CPS_SCHEDULER");
					try{
					Integer	utilityRunFlag =0;
					Integer hourDiff = 0;
					String dateTime = java.time.LocalDateTime.now().toString();
					logger.info("Current Date Time: "+dateTime);
					logger.info("Expiry Date: "+expiryDate);
					String update = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE",
							"'"+wiName+"','Y','"+mode+"'",2);
					logger.info("update procedure call :"+update); // MOVE TO HISTORY
					
					if(trialCount == 24){
                    	trialCount = 0;
                    logger.info("reset trialCount "+trialCount);	
                    }
					
					Integer hour = Integer.parseInt(dateTime.substring(11,13));
					logger.info("Current Hour Count :"+hour); //14 trial - 12
					logger.info("EventGen Trial Count :"+trialCount);
					if(hour != trialCount){
						hourDiff = hour - trialCount;
						logger.info("Number of hour Utility is Stopped:"+hourDiff);
						}
					utilityRunFlag=utilityRunFlag+hourDiff;
					logger.info("Total Number of hour Utility is Stopped:"+utilityRunFlag);

					String moveData = GenerateXml.APProcedure_new(sessionId,cabinetName,"MOVEDATAFORCPS",
							"'"+trialCount+"','"+schedulerInterval+hourDiff+"','"+expiryDate+"'",2);
					logger.info("procedure call :"+moveData);
					
					String tableName = "bpm_eventgen_txn_data";
					String colName = "insertiondatetime, wi_name, expiry_date,"
							+ "status_flag,PROCESS_NAME,SOURCING_CHANNEL, REQUESTMODE,TRIAL_COUNT";
					String Values = "SYSDATE,'"+wiName+"',(SYSDATE+("+schedulerInterval+"/24)),'N','CPS','CPS_SCHEDULER','C','"+
							(trialCount+schedulerInterval+hourDiff)+"'";
					String soutputXML = GenerateXml.apInsertInputXml(cabinetName,sessionId,tableName,colName,Values);
					String outputXML =  ExecuteXML.executeXML(soutputXML.toString());
					logger.info("insert input query "+outputXML);
					}catch(Exception e){
						insertAuditTrail("CPS Scheduler Exception",wiName);
						logger.error("Exception CPS Scheduler:",e);
				       }
				}else if("CPS".equalsIgnoreCase(processName)) {
					logger.info("Inside CPS Prepare Documet Calling: " +wiName);
					try{
					setCustomServiceStatus("14017","Processing Workitem",1+"");
					String outputXml = "";
					CPSPrepareDocument obj = new CPSPrepareDocument(sessionId , cabinetName , 
							wiName ,refNo, processName, sourcingChannel, mode);
						logger.info("trialCount and expiry date updateQuery response " + outputXml);
						moveToHistoryFlag = true;
						finalStatus = "Y";

					if(moveToHistoryFlag) {
						outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
								+wiName+"','"+finalStatus+"','"+mode+"'",2);
						logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
						if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
						XMLParser xmlDataParser1 = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
						if(mainCode != 0) {
							logger.info("AP Procedure call failed");
							}
					   }
					   }
					}catch(Exception e){
						insertAuditTrail("CPS Prepare Document Calling Exception",wiName);
						deleteFromEventgenTable();
						logger.error("Exception CPS Prepare Document Calling",e);
					}	
     			} else if("SWIFT".equalsIgnoreCase(processName)) {
					setCustomServiceStatus("14017","Processing Workitem",1+"");
					logger.info("WorkItem Name/DCN: " +wiName);
					String outputXml = "";
					XMLParser xmlDataParser1;
					String webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId , cabinetName , 
							"" ,wiName, processName, sourcingChannel, mode);
					logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
					//						String webserviceOutput =  ExecuteXML.executeXML(webserviceInput);
					String webserviceOutput =  socket.connectToSocket(webserviceInput);
					logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
					if(webserviceOutput != null && !webserviceOutput.isEmpty()){
						XMLParser xp2 = new XMLParser(webserviceOutput);
						statusCode = xp2.getValueOf("StatusCode");	
					}
					logger.info("statusCode= " + statusCode+"trialCount="+trialCount+"maxTrialCount="+maxTrialCount+"expiryDateInterval="+expiryDateInterval);
					if((null == webserviceOutput || "".equalsIgnoreCase(webserviceOutput)
							|| null == statusCode || "".equalsIgnoreCase(statusCode) 
							|| "100".equalsIgnoreCase(statusCode)) && trialCount < maxTrialCount){
						outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE_DATETIME",
								"'"+wiName+"','"+String.valueOf(trialCount+1)+"','"+
										("EXPIRY_DATE+"+expiryDateInterval)+"'",3);
						logger.info("trialCount and expiry date updateQuery response " + outputXml);
						moveToHistoryFlag = false;
						if((trialCount+1) == maxTrialCount){
							finalStatus = "F";
							moveToHistoryFlag = true;
							handleUtilityException("Unable to generate the events for: "+wiName+"");
						}
					} else if("0".equalsIgnoreCase(statusCode)){
						finalStatus = "Y";
						moveToHistoryFlag = true;
					} else {
						finalStatus = "F";
						moveToHistoryFlag = true;
						String sQuery1 = "select CURR_WS from ext_wbg_ao where wi_name  = N'"+wiName+"'";
						outputXml = ExecuteXML.executeXML(
								GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
						xmlDataParser1 = new XMLParser(outputXml);
						currws = xmlDataParser1.getValueOf("CURR_WS");
						
					}
					logger.info("moveToHistoryFlag= " + moveToHistoryFlag);
					if(moveToHistoryFlag) {
						outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
								+wiName+"','"+finalStatus+"','"+mode+"'",2);
						logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
						if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
						xmlDataParser1 = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
						if(mainCode != 0) {
							logger.info("AP Procedure call failed");
							String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG", 
									"'"+finalStatus+"'", "WI_NAME = N'"+wiName+"' AND REQUESTMODE = '"+mode+"'", 
									sessionId , cabinetName) ;
							xmlDataParser1 = new XMLParser(updateQuery);
							mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
							if(mainCode != 0) {
								errorFlag = true;
								handleUtilityException("Unable to generate the events for: "+wiName+"");
							}
						}
                                             }
					}
					}
				//	ATP-511 REYAZ 26-08-2024 START
     			else {
					String webserviceInput = GenerateXml.getAPWebserviceForEventGen(sessionId , cabinetName , 
							wiName ,refNo, processName, sourcingChannel, mode);
					logger.info("The InputXml for WEBSERVICE Workitem Call = " + webserviceInput);
					String webserviceOutput =  socket.connectToSocket(webserviceInput);
					logger.info("The OutputXml for WEBSERVICE Workitem Call = " + webserviceOutput);
					if(webserviceOutput != null && !webserviceOutput.isEmpty()){
						XMLParser xp2 = new XMLParser(webserviceOutput);
						statusCode = xp2.getValueOf("StatusCode");	
				}
					logger.info("statusCode= " + statusCode+"trialCount="+trialCount+"maxTrialCount="+maxTrialCount+"expiryDateInterval="+expiryDateInterval);
					if((null == webserviceOutput || "".equalsIgnoreCase(webserviceOutput)
							|| null == statusCode || "".equalsIgnoreCase(statusCode) 
							|| "100".equalsIgnoreCase(statusCode))){
						String outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE_DATETIME",
								"'"+wiName+"','"+String.valueOf(trialCount+1)+"','"+
										("EXPIRY_DATE+"+expiryDateInterval)+"'",3);
						logger.info("trialCount and expiry date updateQuery response " + outputXml);
						finalStatus = "F";
						moveToHistoryFlag = true;
						handleUtilityException("Unable to generate the events for: "+wiName+"");
					} else if("0".equalsIgnoreCase(statusCode)){
						finalStatus = "Y";
						moveToHistoryFlag = true;
					} else {
						finalStatus = "F";
						moveToHistoryFlag = true;	
					}
					logger.info("moveToHistoryFlag= " + moveToHistoryFlag);
					if(moveToHistoryFlag) {
						String outputXml = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"
								+wiName+"','"+finalStatus+"','"+mode+"'",2);
						logger.info("The OutputXml for PROC Workitem Call = " + outputXml);
						if (outputXml != null && !outputXml.isEmpty()) { // ATP-439 REYAZ 26-03-2024
							XMLParser xmlDataParser1 = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
							if (mainCode != 0) {
								logger.info("AP Procedure call failed");
								String updateQuery = GenerateXml.APUpdate("BPM_EVENTGEN_TXN_DATA", "STATUS_FLAG",
										"'" + finalStatus + "'",
										"WI_NAME = N'" + wiName + "' AND REQUESTMODE = '" + mode + "'", sessionId,
										cabinetName);
								xmlDataParser1 = new XMLParser(updateQuery);
								mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
								if (mainCode != 0) {
									errorFlag = true;
									handleUtilityException("Unable to generate the events for: " + wiName + "");
								}
							}
						}
					}
				
			}
		    //	ATP-511 REYAZ 26-08-2024 END
			} else {
				logger.info("CPS Scheduler Validate...");
				validateCPSSchedulerEntry();
				logger.info("No Workitem Availible ...");
				logger.info("Sleeping starts ...");
				Thread.sleep(sleepTime);
				logger.info("Sleeping ends ...");
			}
		} catch (Exception exp) {
			logger.error("Exception in loadAndProcessWorkItems: ",exp);
			customException("Exception...", exp);
			errorFlag = true;
		} finally {
			if(errorFlag) {
				setCustomServiceStatus("-25033","Stopped",0+"");
				System.exit(0);
			} else{
				setCustomServiceStatus("-14007","No more Workitems available",0+"");
			}
		}
	}

	public void setCustomServiceStatus(String serviceStatus,String serviceStatusMsg,String workItemCount){
		try {
			logger.info("setCustomServiceStatus started");
			String strparamName ="'"+pid+"','"+serviceStatus+"','"+serviceStatusMsg+"','"+workItemCount+"'";
			logger.info("setCustomServiceStatus params : "+strparamName);
			String sInputXML_APSelect = "<?xml version=\"1.0\"?>"
					+ "<WMTestProcedure_Input>"
					+ "<Option>APProcedure</Option>"
					+ "<SessionId>" + sessionId + "</SessionId>"                   
					+ "<ProcName>WFSetCustomServiceStatus</ProcName>"
					+ "<Params>" + strparamName + "</Params>"                    
					+ "<EngineName>" + cabinetName + "</EngineName>"
					+ "<WMTestProcedure_Input>";
			logger.info("sendEmailMulExcp Mailing-->" + sInputXML_APSelect);
			logger.info("setCustomServiceStatus input-->" + sInputXML_APSelect);
			String	sOutputXML_apselect =  ExecuteXML.executeXML(sInputXML_APSelect);
			logger.info("Procedure setCustomServiceStatus Output "+sOutputXML_apselect);
			logger.info("setCustomServiceStatus ends");
		} catch (Exception e) {
			logger.error("Exception in setCustomServiceStatus: ",e);
		}
	}

	private void handleUtilityException(String exception) {
		String inputXml = "";
		String outputXML = "";
		String mailBody = "";
		try {
			exception = exception.replaceAll("'", "''");
			logger.info("Inside handleLoginException");
			logger.info("[handleUtilityException] exception: "+exception);
			inputXml = GenerateXml.apInsertInputXml(cabinetName, sessionId, "BPM_UTILITY_EXCEPTION", "UTILITY_NAME,EXCEPTION_TIME,EXCEPTION_DESC", "'EventGenBot',SYSDATE,'"+exception+"'");
			logger.info( "outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			logger.info( "outputXml: "+outputXML);
			mailBody = exceptionMail.replaceAll("&<UTILITYNAME>&", "EventGenBot");
			mailBody = mailBody.replaceAll("&<REASON>&", exception);
			inputXml = GenerateXml.apInsertInputXml(cabinetName,sessionId,
					"WFMAILQUEUETABLE", "MAILFROM,MAILTO,MAILCC,MAILSUBJECT,MAILMESSAGE,MAILCONTENTTYPE,ATTACHMENTISINDEX,ATTACHMENTNAMES,ATTACHMENTEXTS,MAILPRIORITY,MAILSTATUS,STATUSCOMMENTS,LOCKEDBY,SUCCESSTIME,LASTLOCKTIME,INSERTEDBY,MAILACTIONTYPE,INSERTEDTIME,PROCESSDEFID,PROCESSINSTANCEID,WORKITEMID,ACTIVITYID,NOOFTRIALS",
					"'"+exceptionFrom+"','"+exceptionTo+"','"+exceptionCC+"'" +
							",'"+exceptionSubject+"','"+mailBody+"'" +
					",'text/html;charset=UTF-8','','','',1,'N','','','','','MailUtilItyUser1','TRIGGER',SYSDATE,'10','EmailUtility','1','10','0'");
			logger.info("[handleUtilityException] outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			logger.info( "outputXml: "+outputXML);
			logger.info("utility will stop now");
		} catch (Exception exc) {
			logger.error("handleLoginException MessagingException ",exc);
		}
	}

	private void customException(String called, Exception e){

		try {
			errorFlag=true;
			StringWriter sw = new StringWriter();
			handleUtilityException(e.toString());
			e.printStackTrace(new PrintWriter(sw));
			logger.info(called +sw.toString());
		} catch (Exception e1) {
			logger.error("Exception in customException: ",e1);
		}

	}

	private String decryptPassword(String pass)
	{			
		int len = pass.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = 
					(byte)((Character.digit(pass.charAt(i), 16) << 4) + 
							Character.digit(pass.charAt(i + 1), 16));
		}
		String password = OSASecurity.decode(data, "UTF-8");
		return password;
	} 

	private String getTagValue(String xml, String tag) throws ParserConfigurationException, SAXException, IOException {
		Document doc = getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName(tag);

		int length = nodeList.getLength();

		if (length > 0) {
			Node node = nodeList.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList childNodes = node.getChildNodes();
				String value = "";
				int count = childNodes.getLength();
				for (int i = 0; i < count; i++) {
					Node item = childNodes.item(i);
					if (item.getNodeType() == Node.TEXT_NODE) {
						value += item.getNodeValue();
					}
				}
				return value;
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				return node.getNodeValue();
			}

		}
		return "";
	}

	private Document getDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	}
	
	private String modifyInputXML(String sInputXML)
	{
		return sInputXML.replace(sInputXML.substring(sInputXML.indexOf("<Password>")+10, sInputXML.indexOf("</Password>")),"*********");		
	}

	private void initializeSocket() {
		try{
			String sQuery1 = "SELECT (SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETIP') IP,"
					+ "(SELECT VALUE FROM BPM_SERVICE_CONFIG WHERE KEY = 'SOCKETPORT') PORT FROM DUAL";
			String outputXml = ExecuteXML.executeXML(
					GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			logger.info("outputXml="+outputXml);
			XMLParser xmlDataParser1 = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xmlDataParser1.getValueOf("MainCode"));
			if (mainCode == 0) {
				String ip = xmlDataParser1.getValueOf("IP");
				String port = xmlDataParser1.getValueOf("PORT");
				socket = ConnectSocket.getReference(socketIP, Integer.parseInt(port));
			}
		}catch(Exception e){
			logger.error("Exception in initializeSocket",e);
		}
	}
	
	public void insertAuditTrail(String progressDetail,String WI_NAME ){
		String insertQuery = "";
		String outputXML ="";
		try {
			insertQuery = GenerateXml.apInsertInputXml(cabinetName, sessionId, "BPM_CPS_AUDIT_TRAIL", 
					"EVENT_TYPE,WI_NAME,COUNT_OF_PROCESSED_WI,INSERTED_DATETIME", "'"+progressDetail+"','"+WI_NAME+"','1',SYSDATE");
			logger.info("[insertInAudit]"  + " Insert Query " +insertQuery);
			outputXML = ExecuteXML.executeXML(insertQuery.toString());
			logger.info("[insertInAudit]"  + " Output XML " +outputXML);
		} catch (Exception e) {
			logger.info("[insertInAudit]"  + " Exception in Insertion " +e);
		}
		
	}
	
	public void deleteFromEventgenTable(){
		String tableName = "bpm_eventgen_txn_data";
		String whereClause =  " STATUS_FLAG = 'L' AND SOURCING_CHANNEL = 'CPS'";
		try {
			String sQuery1 = "SELECT COUNT(WI_NAME) as COUNT FROM BPM_EVENTGEN_TXN_DATA WHERE " + whereClause ;
			String selectOutputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			XMLParser xp = new XMLParser(selectOutputXML);
			int wiCount = Integer.parseInt(xp.getValueOf("COUNT"));
			logger.info("[deleteFromEventgenTable]" +"CPS WI Count in BPM_EVENTGEN_TXN_DATA : "+ wiCount);
			if(wiCount == 1){
				String soutputXML = GenerateXml.APDelete(cabinetName,tableName,whereClause,sessionId);
				logger.info("[deleteFromEventgenTable]" + "deleteFromEventgenTable output "+soutputXML);
			}
		} catch (Exception e) {
			logger.info("[deleteFromEventgenTable]" +"Delete Exception e:"+e);
		}	
	}
	
	
	public void validateCPSSchedulerEntry(){
		String tableName = "BPM_EVENTGEN_TXN_DATA";
		String whereClause =  " SOURCING_CHANNEL = 'CPS_SCHEDULER' AND STATUS_FLAG = 'N'";
		try {
			String sQuery1 = "SELECT COUNT(WI_NAME) as COUNT FROM BPM_EVENTGEN_TXN_DATA WHERE " + whereClause ;
			String selectOutputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery1,sessionId));
			XMLParser xp = new XMLParser(selectOutputXML);
			int wiCount = Integer.parseInt(xp.getValueOf("COUNT"));
			logger.info("[validateCPSSchedulerEntry]" +"CPS Scheduler Count in BPM_EVENTGEN_TXN_DATA : "+ wiCount);
			if(wiCount == 0){
				logger.info("CPS Scheduler Entry is not there");
				String colName = "insertiondatetime, wi_name, expiry_date,"
						+ "status_flag,PROCESS_NAME,SOURCING_CHANNEL, REQUESTMODE,PROCESSEDBYNAME,TRIAL_COUNT";
				String Values = "SYSDATE,'CPS',SYSDATE,'N','CPS','CPS_SCHEDULER','C','eventgen',0";
				String soutputXML = GenerateXml.apInsertInputXml(cabinetName,sessionId,tableName,colName,Values);
				String outputXML =  ExecuteXML.executeXML(soutputXML.toString());
				logger.info("validateCPSSchedulerEntry"+outputXML);
				logger.info("CPS Scheduler Entry is inserted");
			}
			else{
				logger.info("CPS Scheduler Entry is there");
			}
		} catch (Exception e) {
			logger.info("[validateCPSSchedulerEntry]" +"CPS Scheduler Exception e:"+e);
		}	
	}
	
	public static int updateExpiryFlg(String wiName){
		int mainCode = -1;
		try {
		logger.info("[updateExpiryFlg] inside updateExpiryFlg ");
		String updateQuery = "";
		String eflag = "Y";
		updateQuery = GenerateXml.APUpdate("EXT_AO", "EXPFLAG", "'" + eflag + "'", "WI_NAME = N'" + wiName + "'", sessionId, cabinetName);
		logger.info("[updateExpiryFlag] Update Query " + updateQuery);
		XMLParser xmlDataParser6 = new XMLParser(updateQuery);
		mainCode = Integer.parseInt(xmlDataParser6.getValueOf("MainCode"));
		logger.info("[updateExpiryFlag] Main Code " + mainCode);
		} catch (Exception e) {
			logger.info("[chkGetWI]" +"chkGetWI Exception e:"+e);
		}	
		return mainCode;
	}
	public static int getWIForExpiry(String wiName) throws Exception{
		int returnCode = -1;
		try {
		logger.info("getWIForExpiry inside " );
		String Query10 = "select randomnumber from pdbconnection where userindex=(select userIndex from pdbuser where username =(select lockedbyname from wfinstrumenttable where processinstanceid='" + wiName + "' AND lockedbyname !='System' AND ROWNUM=1))";
		String outputXml6 = ExecuteXML.executeXML(
				GenerateXml.APSelectWithColumnNames(cabinetName, Query10, sessionId));
		logger.info("Query10 " + Query10);
		XMLParser parser = new XMLParser(outputXml6);
		String lockedUserSessionId = parser.getValueOf("randomnumber");
		logger.info("[Lockeduserchk] lockedUserSessionId " + lockedUserSessionId);
		if ((lockedUserSessionId == null) || (lockedUserSessionId.isEmpty())) {
			lockedUserSessionId = sessionId;
		}
		String unlockWi = GenerateXml.WMUnlockWorkItem(cabinetName, lockedUserSessionId, wiName, 1);
		logger.info("unlockWi " + unlockWi);
		logger.info("AO Expired WI");
		String getWI = GenerateXml.getGetWorkItemXML(wiName, "1", sessionId, cabinetName);
		logger.info("Get WI : " + getWI);
		XMLParser xmlDataParser11 = new XMLParser(getWI);
		returnCode = Integer.parseInt(xmlDataParser11.getValueOf("MainCode"));
		logger.info(" returnCode1 : " + returnCode);
		} catch (Exception e) {
			logger.info("[chkGetWI]" +"chkGetWI Exception e:"+e);
		}	
		
		return returnCode;
	}
	public void compWIExpiry(String wiName) throws Exception{
		try {
		logger.info("compWIExpiry Inside For "+ wiName ); 
		String wiCompleteInputXML = GenerateXml.getWICompleteXML(wiName, "1", sessionId, cabinetName);
		logger.info("AO --- wiCompleteInputXML 1234" + wiCompleteInputXML);
		XMLParser xmlDataParser = new XMLParser(wiCompleteInputXML);
		int returnCode = Integer.parseInt(xmlDataParser.getValueOf("MainCode"));
		logger.info("returnCode : " + returnCode);
		if (returnCode == 0)
		{
			logger.info("AO Complete workitem successful");
		}
		}catch (Exception e) {
			logger.info("[compWIExpiry]" +"compWIExpiry Exception e:"+e);
		}	
		
	}
}

//class SleepThread extends Log implements Runnable {
//
//	public void run() {
//		logger.info("Inside SleepThread Class");
//	}
//}
//
//class EventGen extends Log{
//	public EventGen() {
//		logger.info("Constructor called");
//	}
//
//	public static void main(String[] args) {
//		try {
//			initializeLogger();
//			EventGenBot_Prod objTC = new EventGenBot_Prod();
//			Thread objThread = new Thread(objTC);
//			objThread.start();
//		} catch (Exception exception) {
//			exception.printStackTrace();
//		}
//	}
//
//}
