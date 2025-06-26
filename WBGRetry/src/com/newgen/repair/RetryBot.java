package com.newgen.repair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
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
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.util.APCallCreateXML;
import com.newgen.util.ConnectSocket;
import com.newgen.util.ExecuteXML;
import com.newgen.util.GenerateXml;
import com.newgen.util.Log;
import com.newgen.util.ProdCreateXML;
import com.newgen.repair.RetryIntegrationCall;


class RetryBot extends Log implements Runnable {
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
	private String repairInterval;
	private ConnectSocket socket;
	private static long sessionInterval;
	private static int schedulerInterval;
	
	private long prevUpdateTime = System.currentTimeMillis();
	public static final String CUSTOM_SERVICE_STATUS_UPDATE_PROCEDURE = "WFSetCustomServiceStatus";
	public static final String CUSTOM_SERVICE_PROCESSING_STATUS = "14017";
	public static final String CUSTOM_SERVICE_NO_WORKITEM_STATUS = "-14017";
	
	public RetryBot() {
		try {
			readConfig();  
			ExecuteXML.init("WebSphere", serverIp, String.valueOf(jndiPort));
			APCallCreateXML.init(cabinetName);
			ProdCreateXML.init(cabinetName);
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
			repairInterval=p.getProperty("expiryDateInterval");
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
				throw new RetryException("Username or Password not specified.");
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
		String sCallOrder = "";
		String stageId = "";
		String currStageId = "";
		int retryCount = 1;
		String validTill = "";
		int maxTrialCount = 4;
		String expiryDate = "";
		boolean moveToHistoryFlag = false;
		String currStageName = "";
		String outputXML1 = "";
		
		String sQuery="";
		String sInput="";
		String sOutput="";
		String sRecord = "";
		String sCall ="";
		String sStatus ="";
		String mandatory="";
		String sResponse="";
		String RequestDt="";
		String ErrorDesc="";
		String status = "";
		
		try {
				logger.info( "WBGTask started");
//				while (true) {
					logger.info( "Inside Loop");
					logger.info( "Inside Try Block");
						String outputXML = APCallCreateXML.APSelect(
								"SELECT  WI_NAME, CALL_ORDER, RETRY_COUNT, TO_CHAR(VALID_TILL,'DD-MM-YYYY HH24:MI:SS') AS VALID_TILL"
										+ " FROM BPM_WBG_RETRY_EVENT WHERE (VALID_TILL < SYSDATE) AND ROWNUM <= 1");
						XMLParser xp = new XMLParser(outputXML);
						logger.info("BPM_WBG_RETRY_EVENT data TotalRetrieved :" + xp.getValueOf("TotalRetrieved"));
						int noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
						if (noOfFields == 1) {
							wiName = xp.getValueOf("WI_NAME");
							sCallOrder = xp.getValueOf("CALL_ORDER");
							retryCount = Integer.parseInt(xp.getValueOf("RETRY_COUNT"));
							validTill = xp.getValueOf("VALID_TILL");
							
							sOutput = APCallCreateXML.APSelect("SELECT CALL_ORDER,CALL_NAME,STATUS,MANDATE_STATUS,RESPONSE,"
									+ " to_char(REQUEST_DATETIME,'dd/mm/yyyy hh:mi:ss')REQUEST_DATETIME,ERRORDESC "
									+ " FROM USR_0_WBG_AO_INTEGRATION_CALLS WHERE WI_NAME ='"+wiName+"' AND STATUS <>'Success'  "
									+ " ORDER BY to_number(CALL_ORDER)");
							logger.info("WBGTask sOutput : "+sOutput);
							XMLParser xpData = new XMLParser(sOutput);
							int start = 0;
							int end = 0;
							int deadend = 0;

							start = xpData.getStartIndex("Records", 0, 0);
							deadend = xpData.getEndIndex("Records", start, 0);
							int count = xpData.getNoOfFields("Record", start, deadend);
							for (int i = 0; i < count; i++)
							{
								start = xpData.getStartIndex("Record", end, 0);
								end = xpData.getEndIndex("Record", start, 0);
								sCallOrder = xpData.getValueOf("CALL_ORDER", start, end);
								sCall = xpData.getValueOf("CALL_NAME", start, end);
								sStatus = xpData.getValueOf("STATUS", start, end);
								sResponse = xpData.getValueOf("RESPONSE", start, end);
								mandatory = xpData.getValueOf("MANDATE_STATUS", start, end);
								RequestDt = xpData.getValueOf("REQUEST_DATETIME", start, end);
								ErrorDesc = xpData.getValueOf("ERRORDESC", start, end);
								
								logger.info(" sCallOrder >>>>>>>:" + sCallOrder + " and  sCall : " + sCall);

								
								status = RetryIntegrationCall.runJSP(wiName, sCallOrder, cabinetName, sessionId, username, serverIp, jndiPort);
								logger.info(" status >>>>>>>:" + status);


								if(status.equalsIgnoreCase("0")) {
									logger.info(" status >>>>>>>:" + status + " and  sCall : " + sCall);
								} else {
									logger.info(" Call Failed >>>>>>>:" + status);
									break;
								}
								
							}

							if (!"0".equalsIgnoreCase(status) && retryCount < maxTrialCount) {
								outputXML1 = APCallCreateXML.APProcedure(sessionId,
										"WBG_RETRY_EVENT",
										"'" + wiName + "','" + String.valueOf(retryCount + 1)
										+ "','" + repairInterval + "'",
										3);

								moveToHistoryFlag = false;
								if ((retryCount + 1) == maxTrialCount) {
									finalStatus = "F";
									moveToHistoryFlag = true;
								}
							} else if ("0".equalsIgnoreCase(status)) {
								finalStatus = "Y";
								moveToHistoryFlag = true;
							} else {
								finalStatus = "F";
								moveToHistoryFlag = true;
							}

							if (moveToHistoryFlag) {
								outputXML1 = APCallCreateXML.APProcedure(sessionId,
										"WBG_RETRY_EVENT_MOVE_HISTORY",
										"'" + wiName + "','" + finalStatus + "'", 2);
							}

							Thread.sleep(13000);

							outputXML = APCallCreateXML.APSelect(
									"SELECT CALL_NAME,STATUS,INPUT_XML,SLNO,RETRY_COUNT FROM USR_0_WBG_AO_INTEGRATION_CALLS "
											+ "WHERE WI_NAME = '"+wiName+"' AND STATUS IN ('Failed','Error','Pending') ORDER BY CALL_ORDER");
							xp = new XMLParser(outputXML);
							noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
							logger.info("BPM_WBG_RETRY_EVENT STAGEID -1: data TotalRetrieved after retry :"+ noOfFields); 
							if (noOfFields == 0) { // move to next WS
								logger.info("EXT_WBG_AO_EVENTS STAGEID -1: HOURS MOVE TO NEXT WS");
								outputXML = APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG",
										"'N'", " WI_NAME = N'" + wiName + "'", sessionId);
								ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
							} else if (moveToHistoryFlag){
								logger.info("MOVE TO HISTORY FLAG TRUE: RETRY_FLAG:Y");
								outputXML = APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG",
										"'Y'", " WI_NAME = N'" + wiName + "'", sessionId);
								ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
							}else {
								logger.info("MOVE TO HISTORY FLAG TRUE: RETRY_FLAG:N");
								outputXML = APCallCreateXML.APUpdate("EXT_WBG_AO", "RETRY_FLAG",
										"'N'", " WI_NAME = N'" + wiName + "'", sessionId);
							}

							setCustomServiceStatus(CUSTOM_SERVICE_PROCESSING_STATUS, "Processing WorkItems","1");
						} else {
							logger.info("No Workitem Availible ...");
							logger.info("Sleeping starts ...");
							Thread.sleep(sleepTime);
							logger.info("Sleeping ends ...");
							setCustomServiceStatus(CUSTOM_SERVICE_NO_WORKITEM_STATUS, "No more Workitems available", "0");
						}
//				}
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
			inputXml = GenerateXml.apInsertInputXml(cabinetName, sessionId, "BPM_UTILITY_EXCEPTION", "UTILITY_NAME,EXCEPTION_TIME,EXCEPTION_DESC", "'RetryBot',SYSDATE,'"+exception+"'");
			logger.info( "outputXml: "+inputXml);
			outputXML = ExecuteXML.executeXML(inputXml.toString());
			logger.info( "outputXml: "+outputXML);
			mailBody = exceptionMail.replaceAll("&<UTILITYNAME>&", "RetryBot");
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
}

