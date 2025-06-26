package com.newgen.tsservice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.newgen.AESEncryption;


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
	public long sessionInterval;

	private static LapsConfigurations instance= new LapsConfigurations();



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
		.append("TSService")
		.append(System.getProperty("file.separator"))
		.append("config.properties");
		InputStream stream = new FileInputStream(configFile.toString());
		if (stream == null) {
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
		Password = AESEncryption.decrypt(p.getProperty("Password"));
	    sessionInterval = Long.parseLong(p.getProperty("sessionInterval")) * 60 * 1000;
	    
		ExecuteXML.init(Server, ServerIP, ServerPort);
		ProdCreateXML.init(CabinetName);
		APCallCreateXML.init(CabinetName);
	}
}


