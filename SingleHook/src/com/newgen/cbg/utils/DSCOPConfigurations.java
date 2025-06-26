package com.newgen.cbg.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.newgen.cbg.logger.DSCOPLogMe;

public class DSCOPConfigurations {

	public String CabinetName;
	public String JTSIP;
	public String JTSPort;
	public String ServerIP;
	public String ServerPort;
	public String Server;
	public String UserName;
	public String Password;
	public int loginCount;
	public int ProcessDefId;
	public String ServletPath;
	public String EDMS;
	public int IHUBProcessDefId;
	public int DSCOPProcessDefId;
	private static DSCOPConfigurations instance;

	private DSCOPConfigurations(){
		try {
			loadConfiguration();
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			e.printStackTrace();
		}
	}

	public static DSCOPConfigurations getInstance(){
		if(instance == null){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Configuration Instance created");
			instance = new DSCOPConfigurations();
		}
		return instance;
	}

	public void loadConfiguration() throws  FileNotFoundException, IOException, Exception
	{
		Properties p = new Properties();
		StringBuilder configFile = new StringBuilder(System.getProperty("user.dir"))
		.append(System.getProperty("file.separator"))
		.append("WebServiceConf")
		.append(System.getProperty("file.separator"))
		.append("DSCOP")
		.append(System.getProperty("file.separator"))
		.append("DSCOP_config.properties");

		InputStream stream = new FileInputStream(configFile.toString());
		if (stream == null) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "CBG_config.properties not found");
		}
		p.load(stream);
		stream.close();
		CabinetName = p.getProperty("CabinetName");
		JTSIP = p.getProperty("JtsIp");
		JTSPort = p.getProperty("JtsPort");
		ServerIP = p.getProperty("IP");
		ServerPort = p.getProperty("Port");
		Server = p.getProperty("Server");
		UserName = p.getProperty("Username");
		Password = p.getProperty("Password");
		Password = DSCOPUtils.getInstance().decryptPassword(Password);
		loginCount = Integer.parseInt(p.getProperty("LoginCount"));
		ProcessDefId = Integer.parseInt(p.getProperty("ProcessDefId"));
		ServletPath=p.getProperty("ServletPath");
		EDMS=p.getProperty("EDMS");
		IHUBProcessDefId = Integer.parseInt(p.getProperty("IHUBProcessDefId"));
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "CabinetName: " + CabinetName + ", JTSIP: " + JTSIP + ", JTSPort: " + JTSPort + ",  ServerIP: " + ServerIP + ", ServerPort: " + ServerPort + ", UserName: "+UserName + ", loginCOUNT: "+loginCount+ ", ProcessDefId: "+ProcessDefId+ ", IHUBProcessDefId: "+IHUBProcessDefId);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Configuration file loaded successfuly");

		ExecuteXML.init(Server, ServerIP, ServerPort);
		ProdCreateXML.init(CabinetName, ProcessDefId);
		APCallCreateXML.init(CabinetName, ProcessDefId);
	
	}
}
