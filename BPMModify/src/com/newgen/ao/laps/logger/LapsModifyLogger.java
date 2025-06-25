package com.newgen.ao.laps.logger;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LapsModifyLogger {
	public static final int LOG_LEVEL_INFO = 1;
	  public static final int LOG_LEVEL_ERROR = 2;
	  private static Logger log4j;
	  private static Logger log4jerror;
	  private boolean enableConsoleLogging = false;

	  private static LapsModifyLogger instance = new LapsModifyLogger();

	  public void enableConsoleLogging() {
	    this.enableConsoleLogging = true;
	  }

	  public void disableConsoleLogging() {
	    this.enableConsoleLogging = false;
	  }

	  public static LapsModifyLogger getInstance() {
	    return instance;
	  }

	  private LapsModifyLogger() {
	    intitalizeLog();
	  }

	  public void intitalizeLog()
	  {
	    try
	    {
	      StringBuilder log4JPropertyFile = new StringBuilder(System.getProperty("user.dir"))
	        .append(System.getProperty("file.separator"))
	        .append("WebServiceConf")
	        .append(System.getProperty("file.separator"))
	        .append("BPMModify")
	        .append(System.getProperty("file.separator"))
	        .append("log4j.properties");

	      System.out.println("Path of log4j file: " + log4JPropertyFile);
	      InputStream stream = new FileInputStream(log4JPropertyFile.toString());
	      if (stream == null) {
	        System.out.println("log4j.properties not found");
	      }
	      Properties properties = new Properties();
	      properties.load(stream);
	      stream.close();
	      PropertyConfigurator.configure(properties);
	      if (log4j == null) {
	        log4j = Logger.getLogger("Laps");
	        log4j.info("Logger is configured successfully");
	      }
	      if (log4jerror == null) {
	        log4jerror = Logger.getLogger("LapsError");
	        log4jerror.error("Error Logger is configured successfully");
	      }
	    } catch (Throwable t) {
	      System.out.println(t);
	    }
	  }

	  public static void logMe(int level, Throwable throwable)
	  {
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
	    if (this.enableConsoleLogging) {
	      System.out.println(formattedLogText);
	    }
	    log4j(level, formattedLogText);
	  }

	  public void log(int level, Throwable throwable) {
	    try {
	      String logText = buildExceptionText(throwable);
	      String formattedLogText = formatLogText(level, logText);
	      if (this.enableConsoleLogging) {
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
	      toReturn = toReturn + "\n" + element.getClassName() + "." + 
	        element.getMethodName() + "[" + element.getLineNumber() + 
	        "]";
	    }
	    return toReturn;
	  }

	  private String formatLogText(int level, String logText) throws Exception
	  {
	    Thread currentThread = Thread.currentThread();
	    StackTraceElement[] stackElements = currentThread.getStackTrace();
	    StackTraceElement stackElement = stackElements[4];
	    if ((stackElement.getClassName().indexOf("CBGLogMe") >= 0) && 
	      (stackElement.getMethodName().indexOf("logMe") >= 0)) {
	      stackElement = stackElements[5];
	    }
	    return "[" + currentThread.getId() + "]" + 
	      "[" + stackElement.getClassName() + 
	      "." + stackElement.getMethodName() + "()]" + 
	      "[#" + stackElement.getLineNumber() + "]" + 
	      " => " + logText;
	  }

	  private void log4j(int level, String logText) throws Exception
	  {
	    if (level == 1) {
	      log4j.info(logText);
	      return;
	    }
	    if (level == 2) {
	      log4jerror.error(logText);
	      return;
	    }
	    throw new Exception("Unrecognized Log Level");
	  }
}
