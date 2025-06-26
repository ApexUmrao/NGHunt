package com.newgen.dscop.utility;

import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import adminclient.OSASecurity;

import com.newgen.omni.jts.cmgr.XMLParser;

public class SMSUtility
{
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String errorStatus;
	private String callReason;
	private String password;
	private String username;
	private String refNo;
	private String serverip;
	private int jndiport;
	private int processDefId;
	private String cabinetName;
	private int logincount;
	private SingleUserConnection instance;
	private static Map<String,String> currentCabPropertyMap = new HashMap<String,String>();
	private long sleepTime = 0L;
	private String socketIP;
	private int socketPort;
	private static final String CUSTOM_SERVICE_STATUS_UPDATE_PROCEDURE = "WFSetCustomServiceStatus";
	private static final String CUSTOM_SERVICE_PROCESSING_STATUS = "14017";
	private static final String CUSTOM_SERVICE_NO_WORKITEM_STATUS = "-14017";
	private static final String PSID = getServicePID();


	public SMSUtility() {
		try {
			DSCOPLogMe.getInstance().intitalizeLog();
			initializeUtility();			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	protected void initializeUtility()
	{
		try {
			this.readCabProperty();
			this.cabinetName=(String)currentCabPropertyMap.get("cabinet");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"cabinetName:" + cabinetName);				
			this.serverip=(String)currentCabPropertyMap.get("SERVERIP");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO," server ip: " + serverip);
			this.jndiport=Integer.parseInt((String)currentCabPropertyMap.get("JNDIPort"));				
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"jndiport: " + jndiport);
			this.logincount=Integer.parseInt((String)currentCabPropertyMap.get("LoginCount"));
			this.username = (String)currentCabPropertyMap.get("Username");
			this.password = (String)currentCabPropertyMap.get("Password");
			this.password = decryptPassword(this.password);
			this.sleepTime = Long.parseLong(currentCabPropertyMap.get("SLEEPTIME"));
			this.socketIP = (String)currentCabPropertyMap.get("SOCKETIP");
			this.socketPort = Integer.parseInt((String)currentCabPropertyMap.get("SOCKETPORT"));
			//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"password: " + password);
			processDefId = Integer.parseInt((String)currentCabPropertyMap.get("ProcessDefId"));
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"jndiport: " + jndiport + " processDefId " + processDefId);
			ProdCreateXML.init(this.cabinetName, processDefId);
			APCallCreateXML.init(this.cabinetName, processDefId);
			ExecuteXML.init("WebSphere", this.serverip, String.valueOf(this.jndiport));			
			instance = SingleUserConnection.getInstance(logincount);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
	}
	public static void main(String[] args)
	{
		try
		{
			SMSUtility object = new SMSUtility();
			object.run();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private synchronized void run()
	{
		try {			
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Task start" + new Date());	
			setCustomServiceStatus(CUSTOM_SERVICE_PROCESSING_STATUS, "Started", 0);
			processSMSRequests();
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			setCustomServiceStatus("-25033", "Stopped", 0);
			e.printStackTrace();
		}
	}

	public void updateCallOutput(String inputXml, String WI_NAME,String sessionId) throws Exception
	{
		String valList = "'" + WI_NAME + "'," + "0" + ", 'SendSMSEmail', '" + this.callStatus + "',SYSDATE," + this.returnCode + ", '" + this.errorDescription.replace(",", "") + "', '" + 
				this.errorDetail + "', '" + this.errorStatus + "', '" + this.callReason + "'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);

		String valList2 = "'" + WI_NAME + "', 'SendSMSEmail'," + this.refNo + ", '" + inputXml + "'";
		APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, CALL_NAME, REFNO, REQUEST", valList2, sessionId);
	}

	protected void processSMSRequests()
	{
		try {

			while(true)
			{
				String outputXML = APCallCreateXML.APSelect("SELECT * FROM USR_0_DSCOP_NOTIFY WHERE STATUS = 'P'");
				XMLParser xp = new XMLParser(outputXML);
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"noOfFields:" + noOfFields);
				if(noOfFields > 0)
				{
					String sessionId = instance.getSession(cabinetName,username,password);
					//DSCOP_NOTIFY  APProcedure(sessionId, ProcName, InValues, NoOfCols) throws NGException
					int end = 0;
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						SendSMSEntity obj = new SendSMSEntity(xp, start, end);						
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO," winame ==> "  + obj.getWI_NAME());						
						String output = APCallCreateXML.APProcedure(sessionId, "DSCOP_NOTIFY", "'"+obj.getWI_NAME()+"','L'", 2);
						String inputXML = createInputXML(obj,sessionId);
						if (sendSMS(inputXML, obj.getWI_NAME(),sessionId)) {
							output = APCallCreateXML.APProcedure(sessionId, "DSCOP_NOTIFY", "'"+obj.getWI_NAME()+"','D'", 2);
						}
						else {
							output = APCallCreateXML.APProcedure(sessionId, "DSCOP_NOTIFY", "'"+obj.getWI_NAME()+"','F'", 2);
						}
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO," proc output ==> "  + output);
						setCustomServiceStatus(CUSTOM_SERVICE_PROCESSING_STATUS, "Processing WorkItems", Integer.valueOf(1));
					}
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going for "+ this.sleepTime +" (ms) sleep... :");
					setCustomServiceStatus(CUSTOM_SERVICE_NO_WORKITEM_STATUS, "No more Workitems available", 0);
					Thread.sleep(sleepTime);
				}
				else{
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going for "+ this.sleepTime +" (ms) sleep... :");
					setCustomServiceStatus(CUSTOM_SERVICE_NO_WORKITEM_STATUS, "No more Workitems available", 0);
					Thread.sleep(sleepTime);
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going for "+3*sleepTime+" minute sleep... :");
			setCustomServiceStatus("-25033", "Sleeping", 0);
			try {
				Thread.sleep(3*sleepTime);
				SMSUtility object = new SMSUtility();
				object.run();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e1);	
			}
		}
	}

	private String createInputXML(SendSMSEntity obj,String sessionId) {
		StringBuilder inputXML = new StringBuilder();
		try {
			this.refNo = generateSysRefNumber();
			inputXML.append("<?xml version=\"1.0\"?>")
			.append("\n")
			.append("<APWebService_Input>")
			.append("\n")
			.append("<Option>WebService</Option>")
			.append("\n")
			.append("<EngineName>" + cabinetName + "</EngineName>")
			.append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>")
			.append("\n")
			.append("<WINAME>" + obj.getWI_NAME() + "</WINAME>")
			.append("\n")
			.append("<Calltype>DSCOP</Calltype>")
			.append("\n")
			.append("<DSCOPCallType>SEND_SMS_EMAIL</DSCOPCallType>")
			.append("\n")
			.append("<REF_NO>" + this.refNo + "</REF_NO>")
			.append("<senderID>WMSBPMENG</senderID>")
			.append("\n")
			.append("<username>" + obj.getCustomerID() + "</username>")
			.append("\n")
			.append("<CUST_ID>" + obj.getCustomerID() + "</CUST_ID>")
			.append("\n")
			.append("<Account_Number>" + obj.getAcctNumber() + "</Account_Number>")
			.append("\n")
			.append("<transactionType>" + obj.getTransactionType() + "</transactionType>")
			.append("\n")
			.append("<sendAsChannel>" + obj.getSendAsChannel() + "</sendAsChannel>")
			.append("\n")
			.append("<TEMPLATE_ID>" + obj.getSmsTemplateID() + "</TEMPLATE_ID>")
			.append("\n")
			.append("<MSG>" + obj.getSmsTextValues() + "</MSG>")
			.append("\n")
			.append("<Mobile>" + obj.getMobileNumber() + "</Mobile>")
			.append("\n")
			.append("<EMAIL_TEMP_ID>" + obj.getEmailTemplated()	+ "</EMAIL_TEMP_ID>")
			.append("\n")
			.append("<EMAIL_TEXT>" + obj.getEmailTextValue() + "</EMAIL_TEXT>")
			.append("\n")
			.append("<EMAIL_ADDRESS>" + obj.getEmailAddress() + "</EMAIL_ADDRESS>")
			.append("\n")
			.append("<FlexiFiller1>" + obj.getFlexiFiller1() + "</FlexiFiller1>")
			.append("\n")
			.append("<FlexiFiller2>" + obj.getFlexiFiller2() + "</FlexiFiller2>")
			.append("\n")
			.append("<FlexiFiller3>" + obj.getFlexiFiller3() + "</FlexiFiller3>")
			.append("\n")
			.append("<FlexiFiller4>" + obj.getFlexiFiller4() + "</FlexiFiller4>")
			.append("\n")
			.append("<FlexiFiller5>" + obj.getFlexiFiller5() + "</FlexiFiller5>").append("\n")
			.append("<LanguagePref>" + ("EN".equalsIgnoreCase(obj.getLanguagePref())?"0":"1") + "</LanguagePref>").append("\n")		
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SendSMSEmail inputXML ===> " + inputXML.toString());
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"SendSMS Exception :" + e);
		}
		return inputXML.toString();
	}


	private boolean sendSMS(String inputXML, String winame,String sessionId)
			throws Exception
	{
		boolean result = false;
		String outputXML = ExecuteXML.executeAPWebService(inputXML.toString(), socketIP, socketPort);
		XMLParser xp = new XMLParser(outputXML);
		this.returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
		this.errorDescription = xp.getValueOf("errorDescription", "", true);
		this.errorDetail = xp.getValueOf("errorDetail", "", true);
		this.errorStatus = xp.getValueOf("Status", "", true);
		this.callReason = xp.getValueOf("Reason", "", true);
		if (this.returnCode == 0) {
			this.callStatus = "Y";
			result = true;
		} else {
			this.callStatus = "F";
			result = false;
		}
		updateCallOutput(inputXML, winame,sessionId);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"SendSMS result:" + result);
		return result;
	}

	private void readCabProperty()
	{
		currentCabPropertyMap.clear();
		RandomAccessFile raf;
		String INI="Config.properties";
		try
		{
			raf= new RandomAccessFile(INI,"rw");
			for(String line = raf.readLine(); line != null; line = raf.readLine())
			{
				if(line==null||line.equalsIgnoreCase(""))
					continue;
				line = line.trim();
				for(line = raf.readLine(); line != null; line = raf.readLine())
				{
					if(line==null||line.equalsIgnoreCase(""))
						continue;
					line = line.trim();
					int i = line.indexOf('=');
					String temp="";
					temp = line.substring(0, i);
					currentCabPropertyMap.put(temp.trim(),line.substring(i+1));
				}
			}
			raf.close();
		}
		catch(Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"ReadPRoperty Eceptioon "+e);
		}
	}

	public String generateSysRefNumber(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT DSCOP_REFNO.NEXTVAL SYSREFNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}

		return sysNum;
	}

	private String decryptPassword(String pass)
	{
		int len = pass.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = (byte)((Character.digit(pass.charAt(i), 16) << 4) +	Character.digit(pass.charAt(i + 1), 16));
		}
		String password = OSASecurity.decode(data, "UTF-8");
		return password;
	}
	

	private void setCustomServiceStatus(String serviceStatus, String serviceStatusMsg, Integer workItemCount)
	{
		try {
			String sessionId = instance.getSession(cabinetName,username,password);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Calling ... setCustomServiceStatus() method.");
			String procedureParams = "'" + PSID + "','" + serviceStatus + "','" + serviceStatusMsg + "','" + workItemCount + "'";
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"setCustomServiceStatus params are :: " + procedureParams);
			String statusUpdateXML = APCallCreateXML.APProcedure(sessionId, CUSTOM_SERVICE_STATUS_UPDATE_PROCEDURE, procedureParams, 4);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"setCustomServiceStatus() execution finished. statusUpdateXML :: " + statusUpdateXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"Error in setCustomServiceStatus(): " + e);
		}
	}

	private static String getServicePID()
	{
		try {
			Properties properties = new Properties();
			properties.load(new FileReader("WFServiceConfig.ini"));
			return properties.getProperty("PSID");
		}
		catch (Exception e)
		{
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"Error in getServicePID(): " + e); 
		}
		return null;
	}
}