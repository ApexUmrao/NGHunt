package com.newgen.ao.laps.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.newgen.AESEncryption;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ConnectSocket;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.logger.LapsModifyLogger;

public class LapsConfigurations {

	public String CabinetName;
	public String JTSIP;
	public int JTSPort;
	public String ServerIP;
	public String socketIP;
	public String ServerPort;
	public String Server;
	public String UserName;
	public String Password;
	public int volumeID;
	public String ProTradeReceiptDoc;
	public String ProTradeCustomerDoc;
	public String docFolderName;
	public String moveDocFolderName;
	public String EDMS;
	public String trsdUrl;
	//public int loginCount;
	public int processDefIdAO;
	public int processDefIdTFO;
	public int utcExpiryTime;
	
	public String NASDataPath = "";
	public String NASDataMovePath = "";
	public String TSLMReceiptDoc = "";
	public String TSLMCustomerDoc = "";
	public String NASDocFolderName = "";
	public String customUTCCount="";
	public String TSLMDelayTimer="";
	
	//added by reyaz 23-01-2023
	public String TSLMMICSDoc = "";
	public String TSLMApprovalDoc = "";
	
	//Addded by shivanshu 14-08-2023
	public String DHLDocumentPDF = "";
	public String DHLDocFolderName = "";
	public String DHLDocName = "";
	
	
	//added by reyaz 12-06-2023
	public String EdmsServerIP;
	public String EdmsServerPort;
	public String EdmsUserName;
	public String EdmsPassword;
	
	//public String ServletPath;
	private static LapsConfigurations instance= new LapsConfigurations();
	private Map<String,String> bpmServiceMap  = new HashMap<String,String>();
	public ConnectSocket socket;
	public long sessionInterval;
	
	//Swift request Change
	public String SwiftDocName ;
	public String InitiateFromActivityName;
	public String InitiateFromActivityId;
	public String SwiftDocFolderName;

	private LapsConfigurations(){
		try {
			loadConfiguration();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static LapsConfigurations getInstance(){
		
		return instance;
	}
	
	@SuppressWarnings("unused")
	public void loadConfiguration() throws  FileNotFoundException, IOException, Exception
	{
		Properties p = new Properties();
		StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
		.append(System.getProperty("file.separator"))
		.append("WebServiceConf")
		.append(System.getProperty("file.separator"))
		.append("BPMModify")
		.append(System.getProperty("file.separator"))
		.append("LAPS_config.properties");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loadConfiguration- configFile: "+configFile);
		InputStream stream = new FileInputStream(configFile.toString());
		if (stream == null) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "LAPS_config.properties not found");
		}
		p.load(stream);
		stream.close();
		CabinetName = p.getProperty("CabinetName");
		JTSIP = p.getProperty("sJtsIp");
		JTSPort = Integer.parseInt(p.getProperty("iJtsPort"));
		ServerIP = p.getProperty("IP");
		ServerPort = p.getProperty("Port");
		Server = p.getProperty("Server");
		UserName = p.getProperty("Username");
		Password = p.getProperty("Password");
		docFolderName = p.getProperty("docFolderName");
		moveDocFolderName = p.getProperty("moveDocFolderName");
		ProTradeReceiptDoc = p.getProperty("ProTradeReceiptDoc");
		ProTradeCustomerDoc = p.getProperty("ProTradeCustomerDoc");
		trsdUrl = p.getProperty("TRSDURL");
		socketIP = p.getProperty("socketIP");
		volumeID = Integer.parseInt(p.getProperty("volumeID"));
		Password = LapsUtils.getInstance().decryptPassword(Password);
		//loginCount = Integer.parseInt(p.getProperty("LoginCount"));
		processDefIdAO = Integer.parseInt(p.getProperty("ProcessDefIdAO"));
		processDefIdTFO = Integer.parseInt(p.getProperty("ProcessDefIdTFO"));
		utcExpiryTime = Integer.parseInt(p.getProperty("UtcExpiryTime"));
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "utcExpiryTime	"+utcExpiryTime);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loadConfiguration- configFile: "+"::"+JTSIP
				+"::"+JTSPort+"::"+volumeID+"::"+"END");
		
		//For Swift Request gen 
		SwiftDocName = p.getProperty("SwiftDocName");
		InitiateFromActivityName = p.getProperty("InitiateFromActivityName");
		InitiateFromActivityId  = p.getProperty("InitiateFromActivityId");
		SwiftDocFolderName = p.getProperty("SwiftDocFolderName");
		EDMS=p.getProperty("EDMS");
		
		//For TSLM SCF
		NASDataPath =p.getProperty("NASDataPath");
		NASDataMovePath =p.getProperty("NASDataMovePath");
		NASDocFolderName = p.getProperty("NASDocPath");
		TSLMReceiptDoc = p.getProperty("TSLMReceiptDoc");
		TSLMCustomerDoc = p.getProperty("TSLMCustomerDoc");
		customUTCCount = p.getProperty("customUTCCount");
		TSLMDelayTimer=p.getProperty("TSLMDelayTimer");
		// added by reyaz  23-01-2023
		TSLMMICSDoc = p.getProperty("TSLMMICSDoc");
		TSLMApprovalDoc = p.getProperty("TSLMApprovalDoc");
	
		// added by shivanshu  14-08-2023
		DHLDocumentPDF = p.getProperty("DHLDocumentPDF");
		DHLDocFolderName = p.getProperty("DHLDocFolderName");
		DHLDocName = p.getProperty("DHLDocName");
		
		// added by reyaz  12-06-2023
	        EdmsServerIP =p.getProperty("EdmsServerIP");
		EdmsServerPort =p.getProperty("EdmsServerPort");
		EdmsUserName =p.getProperty("EdmsUserName");
		EdmsPassword =p.getProperty("EdmsPassword");
		EdmsPassword=AESEncryption.decrypt(EdmsPassword);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "NASDataMovePath  :"+NASDataMovePath);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "NASDocFolderName  :"+NASDocFolderName);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLMReceiptDoc  :"+TSLMReceiptDoc);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLMCustomerDoc  :"+TSLMCustomerDoc);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLMMICSDoc  :"+TSLMMICSDoc);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLMApprovalDoc  :"+TSLMApprovalDoc);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "DHLDocumentPDF  :"+DHLDocumentPDF);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "EdmsPassword  :"+EdmsPassword);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "EdmsServerIP  :"+EdmsServerIP);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "EdmsServerPort  :"+EdmsServerPort);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DHLDocFolderName : " + DHLDocFolderName);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DHLDocName : " + DHLDocName);
	
		//=======================
		
		//ServletPath=p.getProperty("ServletPath");
		sessionInterval = Long.parseLong(p.getProperty("sessionInterval")) * 60 * 1000;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "CabinetName: " + CabinetName + ",  ServerIP: " 
				+ ServerIP + ", ServerPort: " + ServerPort + ", UserName: "+UserName 
				+ ", ProcessDefIdAO: "+processDefIdAO + ", processDefIdTFO: "+processDefIdTFO);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Edms value"+EDMS); //Added by reyaz
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Configuration file loaded successfuly");
		ExecuteXML.init(Server, ServerIP, ServerPort);
//		ProdCreateXML.init(CabinetName, processDefId);
//		APCallCreateXML.init(CabinetName, processDefId);
		ProdCreateXML.init(CabinetName);
		APCallCreateXML.init(CabinetName);
		APCallCreateXML.initEdms(EDMS);
		ProdCreateXML.initEdms(EDMS);
//		try {
//			bpmServiceMap = ChannelCallCache.getInstance().getBPMServiceMap();
//			ConnectSocket.initSocket(bpmServiceMap.get("SOCKETIP"), Integer.parseInt(bpmServiceMap.get("SOCKETPORT")));
//		} catch (Exception e) {
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in ConnectSocket.initSocket: ");	
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);	
//		}
		try {
			bpmServiceMap = ChannelCallCache.getInstance().getBPMServiceMap();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "socketIP: "+socketIP+", socketPort: "
					+Integer.parseInt(bpmServiceMap.get("SOCKETPORT")));
			socket = ConnectSocket.getReference(socketIP, Integer.parseInt(bpmServiceMap.get("SOCKETPORT")));
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in ConnectSocket.initSocket: ");	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);	
		}
	}
}
