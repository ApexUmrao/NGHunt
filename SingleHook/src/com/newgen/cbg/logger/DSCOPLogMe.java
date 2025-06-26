package com.newgen.cbg.logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DSCOPLogMe {

	public static final int LOG_LEVEL_INFO = 1;
	public static final int LOG_LEVEL_ERROR = 2;
	public static final int LOG_LEVEL_CACHE = 3;

	private static Logger log4j;
	private static Logger log4jerror;
	private static Logger log4jcache;
	private boolean enableConsoleLogging = false;

	private static DSCOPLogMe instance;

	public void enableConsoleLogging() {
		enableConsoleLogging = true;
	}

	public void disableConsoleLogging() {
		enableConsoleLogging = false;
	}
	
	public static DSCOPLogMe getInstance(){
		if(instance==null){
			instance = new DSCOPLogMe();
		}
		return instance;
	}
	
	private DSCOPLogMe() {
		intitalizeLog();
	}
	
	@SuppressWarnings("unused")
	public void intitalizeLog(){
		try {

			StringBuilder log4JPropertyFile = new StringBuilder(System.getProperty("user.dir"))
			.append(System.getProperty("file.separator"))
			.append("WebServiceConf")
			.append(System.getProperty("file.separator"))
			.append("DSCOP")
			.append(System.getProperty("file.separator"))
			.append("DSCOP_log4j.properties");


			System.out.println("Path of log4j file: " + log4JPropertyFile);
			InputStream stream = new FileInputStream(log4JPropertyFile.toString());
			if (stream == null) {
				System.out.println("DSCOP_log4j.properties not found");
			}
			Properties properties = new Properties();
			properties.load(stream);
			stream.close();
			PropertyConfigurator.configure(properties);
			if(log4j == null){
				log4j = Logger.getLogger("CBG");
				log4j.info("Logger is configured successfully");
			}
			if(log4jerror == null){
				log4jerror = Logger.getLogger("CBGError");
				log4jerror.error("Error Logger is configured successfully");
			}
			if(log4jcache == null){
				log4jcache = Logger.getLogger("CBGCache");
				log4jcache.error("Cache Logger is configured successfully");
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
		if (stackElement.getClassName().indexOf("DSCOPLogMe") >= 0
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
		if (level == DSCOPLogMe.LOG_LEVEL_INFO) {
			log4j.info(logText);
			return;
		} 
		else if (level == DSCOPLogMe.LOG_LEVEL_ERROR) {
			log4jerror.error(logText);
			return;
		} 
		else if (level == DSCOPLogMe.LOG_LEVEL_CACHE) {
			log4jcache.info(logText);
			return;
		} 
		throw new Exception("Unrecognized Log Level");
	}
}