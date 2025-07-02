//package com.newgen.raroc.util;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//public class CBGConfigurations {
//
//	public String CabinetName;
//	public String JTSIP;
//	public String JTSPort;
//	public String ServerIP;
//	public String ServerPort;
//	public String Server;
//	public String UserName;
//	public String Password;
//	public int loginCount;
//	public int ProcessDefId;
//	public String ServletPath;
//	public String EDMS;
//	public int IHUBProcessDefId;
//	private static CBGConfigurations instance;
//	static Logger logger = LoggerFactory.getLogger(APCallCreateXML.class);
//
//	private CBGConfigurations(){
//		try {
//			loadConfiguration();
//		} catch (Exception e) {
//			logger.info("Exception in CBGConfigurations ", e);
//			e.printStackTrace();
//		}
//	}
//
//	public static CBGConfigurations getInstance(){
//		if(instance == null){
//			logger.info("Configuration Instance created ");
//			instance = new CBGConfigurations();
//		}
//		return instance;
//	}
//
//	public void loadConfiguration() throws  FileNotFoundException, IOException, Exception
//	{
//		Properties p = new Properties();
//		StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
//		.append(System.getProperty("file.separator"))
//		.append("WebServiceConf")
//		.append(System.getProperty("file.separator"))
//		.append("BPMApplication")
//		.append(System.getProperty("file.separator"))
//		.append("BPMApplication_config.properties");
//
//		InputStream stream = new FileInputStream(configFile.toString());
//		if (stream == null) {
//			logger.info("BPMApplication_config.properties not found");
//		}
//		p.load(stream);
//		stream.close();
//		CabinetName = p.getProperty("CabinetName");
//		JTSIP = p.getProperty("JtsIp");
//		JTSPort = p.getProperty("JtsPort");
//		ServerIP = p.getProperty("IP");
//		ServerPort = p.getProperty("Port");
//		Server = p.getProperty("Server");
//		UserName = p.getProperty("Username");
//		Password = p.getProperty("Password");
////		Password = CBGUtils.getInstance().decryptPassword(Password);
//		loginCount = Integer.parseInt(p.getProperty("LoginCount"));
//		ProcessDefId = Integer.parseInt(p.getProperty("ProcessDefId"));
//		ServletPath=p.getProperty("ServletPath");
//		EDMS=p.getProperty("EDMS");
//		IHUBProcessDefId = Integer.parseInt(p.getProperty("IHUBProcessDefId"));
//		logger.info("CabinetName: " + CabinetName + ", JTSIP: " + JTSIP + ", JTSPort: " + JTSPort + ",  ServerIP: " + ServerIP + ", ServerPort: " + ServerPort + ", UserName: "+UserName + ", loginCOUNT: "+loginCount+ ", ProcessDefId: "+ProcessDefId+ ", IHUBProcessDefId: "+IHUBProcessDefId);
//		logger.info("Configuration file loaded successfuly");
//
////		ExecuteXML.init(Server, ServerIP, ServerPort);
////		ProdCreateXML.init(CabinetName, ProcessDefId);
////		APCallCreateXML.init(CabinetName, ProcessDefId);
//
//	}
//}
