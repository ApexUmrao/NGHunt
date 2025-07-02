package com.newgen.iforms.user.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class RarocLogger implements Constants{
	private static RarocLogger logger = new RarocLogger();
	private Logger log = Logger.getLogger(RarocLogger.class);

	private RarocLogger(){
		initializeLogger();
	}

	public static RarocLogger getInstance(){
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
		.append("Process")
		.append(System.getProperty("file.separator"))
		.append(FILE_LOG4J_PROPERTIES).toString();
		try (FileInputStream fis = new FileInputStream(log4JPropertyFile)){			
			properties.load(fis);
			PropertyConfigurator.configure(properties);
			log.info("RAROC Logger Initialized.");
		} catch (IOException e) {
			log.error("Exception in RAROC Logger Initialization.", e);
		} 
	}


}
