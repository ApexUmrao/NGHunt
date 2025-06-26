package com.newgen.iforms.user.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AOSignLogger implements Constants{
	private static AOSignLogger signlogger = new AOSignLogger();
	private Logger logsign = Logger.getLogger(AOSignLogger.class);

	private AOSignLogger(){
		initializeSignLogger();
	}

	public static AOSignLogger getInstance(){
		return signlogger;
	}

	public Logger getSignLogger(){
		return logsign;
	}

	private void initializeSignLogger() {
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
			logsign.info("AO Sign Logger Initialized.");
		} catch (IOException e) {
			logsign.error("Exception in AO Sign Logger Initialization.", e);
		} 
	}

}
