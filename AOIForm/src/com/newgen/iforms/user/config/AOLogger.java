package com.newgen.iforms.user.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AOLogger implements Constants{
	private static AOLogger logger = new AOLogger();
	private Logger log = Logger.getLogger(AOLogger.class);

	private AOLogger(){
		initializeLogger();
	}

	public static AOLogger getInstance(){
		return logger;
	}

	public Logger getLogger(){
		return log;
	}

	private void initializeLogger() {
		Properties properties = new Properties();
		String log4JPropertyFile = new StringBuilder()
		.append(System.getProperty("user.dir"))
		.append(System.getProperty("file.separator"))
		.append(FOLDER_CONFIG)
		.append(System.getProperty("file.separator"))
		.append("AO")
		.append(System.getProperty("file.separator"))
		.append(FILE_LOG4J_PROPERTIES).toString();
		try (FileInputStream fis = new FileInputStream(log4JPropertyFile)){			
			properties.load(fis);
			PropertyConfigurator.configure(properties);
			log.info("AO Logger Initialized.");
		} catch (IOException e) {
			log.error("Exception in AO Logger Initialization.", e);
		} 
	}

}
