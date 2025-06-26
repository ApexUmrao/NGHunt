package com.newgen.dscop.expiry;

import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import adminclient.OSASecurity;

import com.newgen.dscopretryexp.logger.CBGDBLogMe;
import com.newgen.omni.jts.cmgr.XMLParser;

public class Expiry {

	public String password;
	public String username;
	public String serverip;
	public int jndiport;
	public String cabinetName;
	public int logincount;
	public int processDefId;
	public int ARMorningTime;
	public int AREveningTime;
	public int MoveToRepairTime;
	public int RepairInterval;
	public static Map<String,String> currentCabPropertyMap = new HashMap<String,String>();
	public HashMap<String, String> defaultMap;
	public SingleUserConnection instance;
	public String sourcingChannel;
	public String applicationVersion;
	public long sleepTime = 0L;
	public String frmEmailID;
	public String socketIP;
	public int socketPort;
	public static final String CUSTOM_SERVICE_STATUS_UPDATE_PROCEDURE = "WFSetCustomServiceStatus";
	public static final String CUSTOM_SERVICE_PROCESSING_STATUS = "14017";
	public static final String CUSTOM_SERVICE_NO_WORKITEM_STATUS = "-14017";
	public static final String PSID = getServicePID();
	
	public String dbURL;
	public String dbUser;
	public String dbPassword;

	public Expiry() {
		try {
			CBGLogMe.getInstance().intitalizeLog();
			CBGDBLogMe.getInstance();
			initializeUtility();			
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	protected void initializeUtility()
	{
		try {
			this.readCabProperty();
			this.cabinetName = currentCabPropertyMap.get("cabinet");
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"cabinetName:" + cabinetName);				
			this.serverip = currentCabPropertyMap.get("SERVERIP");
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO," server ip: " + serverip);
			this.jndiport = Integer.parseInt(currentCabPropertyMap.get("JNDIPort"));				
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"jndiport: " + jndiport);
			this.logincount = Integer.parseInt(currentCabPropertyMap.get("LoginCount"));
			this.username = currentCabPropertyMap.get("Username");
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Username: " + username);
			this.password = currentCabPropertyMap.get("Password");	
			this.password  = decryptPassword(this.password);
			this.sleepTime = Long.parseLong(currentCabPropertyMap.get("SLEEPTIME"));
			this.frmEmailID = currentCabPropertyMap.get("FromEmailID");
			this.socketIP = (String)currentCabPropertyMap.get("SOCKETIP");
			this.socketPort = Integer.parseInt((String)currentCabPropertyMap.get("SOCKETPORT"));
			
			
			this.dbURL = currentCabPropertyMap.get("DBURL");			
			this.dbUser = currentCabPropertyMap.get("DBUSER");			
			this.dbPassword = currentCabPropertyMap.get("DBPASS");
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"dbURL: " + dbURL + " dbUser:" + dbUser + " dbPassword:" + dbPassword);
			this.dbPassword = decryptPassword(this.dbPassword );
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"dbPassword: " + dbPassword);
			
			processDefId = Integer.parseInt(currentCabPropertyMap.get("ProcessDefId"));
			ARMorningTime = Integer.parseInt(currentCabPropertyMap.get("ARMorningTime"));
			AREveningTime = Integer.parseInt(currentCabPropertyMap.get("AREveningTime"));
			MoveToRepairTime = Integer.parseInt(currentCabPropertyMap.get("MoveToRepairTime"));
			RepairInterval = Integer.parseInt(currentCabPropertyMap.get("RepairInterval"));
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"jndiport: " + jndiport + " processDefId " + processDefId);
			ProdCreateXML.init(this.cabinetName, processDefId);
			APCallCreateXML.init(this.cabinetName, processDefId);
			ExecuteXML.init("JBossEAP", this.serverip, String.valueOf(this.jndiport));
			com.newgen.dscopretryexp.utils.ExecuteXML.init("WebSphere", this.serverip, String.valueOf(this.jndiport));
			com.newgen.dscopretryexp.logger.CBGLogMe.getInstance().intitalizeLog();
			com.newgen.dscopretryexp.utils.APCallCreateXML.init(cabinetName, processDefId);
			com.newgen.dscopretryexp.utils.ProdCreateXML.init(cabinetName, processDefId);
			com.newgen.dscopretryexp.utils.CBGConfigurations.getInstance().loadConfiguration();
			instance = SingleUserConnection.getInstance(logincount);
			
			createDefaultValueMap();
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	public static void main(String[] args)
	{
		try
		{
			Expiry exp = new Expiry();
			exp.run();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private synchronized void run()
	{
		try {			
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Task start" + new Date());	
			setCustomServiceStatus(CUSTOM_SERVICE_PROCESSING_STATUS, "Started", 0);
			new Thread(new DSCOPTask()).start();	
			} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
			setCustomServiceStatus("-25033", "Stopped", 0);
			e.printStackTrace();
		}
	}

	protected String createInputXML(String wiName,String sessionId,String stageId,String extraValues, String sourcingChannel, String applicationVersion) {
		StringBuilder inputXML = new StringBuilder();
		try {
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
			.append("<Calltype>DSCOP</Calltype>")
			.append("\n")
			.append("<DSCOPCallType>SINGLE_HOOK</DSCOPCallType>")
			.append("\n")
			.append("<REF_NO>"+generateSysRefNumber()+"</REF_NO>")
			.append("<senderID>DSCOP</senderID>")
			.append("<RequestDateTime>"+new Date().toString()+"</RequestDateTime>")
			.append("<MODE>X</MODE>")
			.append("<WI_NAME>"+wiName+"</WI_NAME>")
			.append("<stage>"+stageId+"</stage>")
			.append("<applicationAttributes>")
			.append("<attributeDetails>")
			.append("<attributes>")
			.append(extraValues)	
			.append("</attributes>")
			.append("</attributeDetails>")
			.append("</applicationAttributes>")
			.append("<applicationName>"+sourcingChannel+"</applicationName>")
			.append("<SourcingChannel>"+sourcingChannel+"</SourcingChannel>")
			.append("<applicationVersion>"+applicationVersion+"</applicationVersion>")
			.append("<SourcingCenter>DSCOP</SourcingCenter>")
			.append("<Language>Eng</Language>")
			.append("<LeadNumber>DSCOP</LeadNumber>")
			.append("<DeviceID>DSCOP</DeviceID>")					
			.append("<IP>DSCOP</IP>")
			.append("</APWebService_Input>");			
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,	"SINGLE_HOOK inputXML ===> " + inputXML.toString());
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
		return inputXML.toString();
	}

	private void readCabProperty()
	{
		currentCabPropertyMap.clear();
		String INI="Config.properties";
		try
		{
			RandomAccessFile raf= new RandomAccessFile(INI,"rw");
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
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	// Decision - Expired by BPM-SYSTEM and 
	public void addAudit(String sessionId, String wiName, String decision, String reason, String sourcingChannel) {
		String valList = "'"+ wiName +"', SYSDATE, 'BPM-SYSTEM-USER', 'BPM-SYSTEM', '"+ decision +"', '"+ reason +"', '"+ sourcingChannel +"'";
		try {
			APCallCreateXML.APInsert("USR_0_DSCOP_DEC_HIST", "WI_NAME, DEC_DATE_TIME, USERNAME, USER_GROUPNAME, DECISION, REASON, SOURCING_CHANNEL", valList, sessionId);
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	public String generateSysRefNumber(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT CBG_REFNO.NEXTVAL SYSREFNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
		return sysNum;
	}

	protected String decryptPassword(String pass)
	{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"decryptPassword:" + pass);
		int len = pass.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = 
					(byte)((Character.digit(pass.charAt(i), 16) << 4) + 
							Character.digit(pass.charAt(i + 1), 16));
		}
		String password = OSASecurity.decode(data, "UTF-8");
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"decryptPassword:" + password);
		return password;
	}	

	

	
	protected void createDefaultValueMap(){
		try {
			defaultMap = new HashMap<String, String>();
			String sOutput = APCallCreateXML.APSelect("SELECT DEFAULT_KEY, DEFAULT_VALUE FROM USR_0_DSCOP_DEF_VAL_MASTER");
			XMLParser xp = new XMLParser(sOutput);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for(int i = 0; i < noOfFields; ++i){
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				defaultMap.put(xp.getValueOf("DEFAULT_KEY", start, end), xp.getValueOf("DEFAULT_VALUE", start, end));
			}			
		} catch(Exception e){
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR," Exception in createDefaultValueMap "+e);
		}
	}

	protected void callSMSEmail(String wiName, String sessionId) {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"********************inside callSMSEmail**********************");

		try {
			String SMSTmpId = "1944";
			String flxFilter1 = "2627";
			String flxFilter2 = "2628";
			String appPercentage = "75%";

			String outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_MOBILE_NO, CUSTOMER_EMAIL, CUSTOMER_ID, PREFERRED_LANGUAGE, BANKING_TYPE FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME = N'"+wiName+"'");								
			XMLParser xp = new XMLParser(outputXML);
			int noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			if(noOfFields == 1)
			{
				String mobNo = xp.getValueOf("CUSTOMER_MOBILE_NO");
				String emailAddr = xp.getValueOf("CUSTOMER_EMAIL");
				String custID = xp.getValueOf("CUSTOMER_ID");
				String prefferedLanguage = xp.getValueOf("PREFERRED_LANGUAGE");
				String bankingType = xp.getValueOf("BANKING_TYPE");
				String flxFilter = bankingType.equals("C") ? flxFilter1 : flxFilter2;

				if("".equals(custID)){
					String columnName = "CUSTOMERID, ACCTNUMBER, TRANSACTIONTYPE, SMSTEMPLATEID, SMSTEXTVALUES, MOBILENUMBER, EMAILTEMPLATEID, EMAILTEXTVALUES,"
							+ "EMAILADDRESS, FLEXIFILLER1, FLEXIFILLER2, STATUS, WI_NAME, ENTRY_DATE_TIME, SENDASCHANNEL, PREFFEREDLANGUAGE";
					String strValues = "'', '', 'AC', " + SMSTmpId + ", '" + appPercentage + "', '" + mobNo + "', " + SMSTmpId + ", '"
							+ appPercentage + "', '" + emailAddr + "', '"+ flxFilter + "', '" + frmEmailID + "', 'P', '" + wiName 
							+ "', sysdate, 'B', '"+ prefferedLanguage + "'";

					APCallCreateXML.APInsert("USR_0_CBG_NOTIFY", columnName, strValues, sessionId);
				}
			}
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Exception in sendEmailSMS:"+e);
		}
	}

	protected void setCustomServiceStatus(String serviceStatus, String serviceStatusMsg, Integer workItemCount)
	{
		try {
			String sessionId = instance.getSession(cabinetName,username,password);
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Calling ... setCustomServiceStatus() method.");
			String procedureParams = "'" + PSID + "','" + serviceStatus + "','" + serviceStatusMsg + "','" + workItemCount + "'";
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"setCustomServiceStatus params are :: " + procedureParams);
			String statusUpdateXML = APCallCreateXML.APProcedure(sessionId, CUSTOM_SERVICE_STATUS_UPDATE_PROCEDURE, procedureParams, 4);
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"setCustomServiceStatus() execution finished. statusUpdateXML :: " + statusUpdateXML);
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Error in setCustomServiceStatus(): " + e);
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
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Error in getServicePID(): " + e); 
		}
		return null;
	}
}