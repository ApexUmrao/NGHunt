package com.newgen.iforms.user.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TFOLogger implements Constants{
	private static TFOLogger logger = new TFOLogger();
	private Logger log = Logger.getLogger(TFOLogger.class);
	
	private TFOLogger(){
		initializeLogger();
	}
	
	public static TFOLogger getInstance(){
		return logger;
	}
	
	public Logger getLogger(){
		return log;
	}
	
	private void initializeLogger() {
		Properties properties = new Properties();
		//dev
	/*	String log4JPropertyFile = new StringBuilder()
				.append(System.getProperty("user.dir"))
				.append(System.getProperty("file.separator"))
				.append(FOLDER_CONFIG)
				.append(System.getProperty("file.separator"))
				.append(FILE_LOG4J_PROPERTIES).toString();*/
		
		String log4JPropertyFile = new StringBuilder()
		.append(System.getProperty("user.dir"))
		.append(System.getProperty("file.separator"))
		.append(FOLDER_CONFIG)
		.append(System.getProperty("file.separator"))
		.append("TFO")
		.append(System.getProperty("file.separator"))
		.append(FILE_LOG4J_PROPERTIES).toString(); 
		
		try (FileInputStream fis = new FileInputStream(log4JPropertyFile)){			
			properties.load(fis);
			PropertyConfigurator.configure(properties);
			log.info("TFO Logger Initialized.");
		} catch (IOException e) {
			log.error("Exception in TFO Logger Initialization.", e);
		} 
	}

}
