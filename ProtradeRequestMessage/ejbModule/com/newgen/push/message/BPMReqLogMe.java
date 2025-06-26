package com.newgen.push.message;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BPMReqLogMe {

	public static final int LOG_LEVEL_INFO = 1;
	public static final int LOG_LEVEL_ERROR = 2;

	private static Logger log;
	private static Logger log4jerror;
	private boolean enableConsoleLogging = false;

	private static BPMReqLogMe instance = new BPMReqLogMe();


	public void enableConsoleLogging() {
		enableConsoleLogging = true;
	}

	public void disableConsoleLogging() {
		enableConsoleLogging = false;
	}
	
	public static BPMReqLogMe getInstance(){
		instance.intitalizeLog();
		return instance;
	}
	
	private BPMReqLogMe() {
	
	}
	
	@SuppressWarnings("unused")
	public void intitalizeLog(){
		try {

			StringBuilder log4JPropertyFile = new StringBuilder(System.getProperty("user.dir"))
			.append(System.getProperty("file.separator"))
			.append("WebServiceConf")
			.append(System.getProperty("file.separator"))
			.append("BPMRequestMessage")
			.append(System.getProperty("file.separator"))
			.append("ProtradeRequestMessage_log4j.properties");
			System.out.println("Path of log4j file: " + log4JPropertyFile);
			InputStream stream = new FileInputStream(log4JPropertyFile.toString());
			if (stream == null) {
				System.out.println("BPMRequestMessage_log4j.properties not found");
			}
			Properties properties = new Properties();
			properties.load(stream);
			stream.close();
			PropertyConfigurator.configure(properties);
			if(log == null){
				log = Logger.getLogger("BPMRequestMessage");
				log.info("Logger is configured successfully");
			}
			if(log4jerror == null){
				log4jerror = Logger.getLogger("BPMRequestMessageError");
				log4jerror.error("Error Logger is configured successfully");
			}
		} catch (Throwable t) {
			System.out.println(t);
		}
	}

	public static void logMe(int level, Throwable throwable) {
		try {
			instance.log(level, throwable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void logMe(int level, String message) {
		try {
			instance.log(level, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void log(int level, String logText) throws Exception {
		String formattedLogText = formatLogText(level, logText);
		if (enableConsoleLogging) {
			System.out.println(formattedLogText);
		}
		log4j(level, formattedLogText);
	}

	public void log(int level, Throwable throwable) {
		try {
			String logText = buildExceptionText(throwable);
			String formattedLogText = formatLogText(level, logText);
			if (enableConsoleLogging) {
				System.out.println(formattedLogText);
			}

			log4j(level, formattedLogText);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String buildExceptionText(Throwable t) throws Exception {
		if (t == null) {
			return "<Throwable is null>";
		}
		t.printStackTrace();
		String toReturn = "\n" + t.toString() + "(" + t.getMessage() + ")";
		StackTraceElement[] trace = t.getStackTrace();
		for (StackTraceElement element : trace) {
			toReturn += "\n" + element.getClassName() + "."
					+ element.getMethodName() + "[" + element.getLineNumber()
					+ "]";
		}
		return toReturn;
	}

	private String formatLogText(int level, String logText) throws Exception {

		Thread currentThread = Thread.currentThread();
		StackTraceElement[] stackElements = currentThread.getStackTrace();
		StackTraceElement stackElement = stackElements[4];
		if (stackElement.getClassName().indexOf("BPMReqLogMe") >= 0
				&& stackElement.getMethodName().indexOf("logMe") >= 0) {
			stackElement = stackElements[5];
		}
		return "[" + currentThread.getId() + "]"
		+ "[" + stackElement.getClassName()
		+ "." + stackElement.getMethodName() + "()]"
		+ "[#" + stackElement.getLineNumber() + "]"
		+ " => " + logText;
	}


	private void log4j(int level, String logText) throws Exception {
		if (level == BPMReqLogMe.LOG_LEVEL_INFO) {
			log.info(logText);
			return;
		} 
		else if (level == BPMReqLogMe.LOG_LEVEL_ERROR) {
			log4jerror.error(logText);
			return;
		} 
		throw new Exception("Unrecognized Log Level");
	}
}