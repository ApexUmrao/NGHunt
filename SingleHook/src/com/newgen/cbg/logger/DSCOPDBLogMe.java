package com.newgen.cbg.logger;

import com.newgen.cbg.utils.APCallCreateXML;

public class DSCOPDBLogMe {

	public static final int LOG_LEVEL_INFO = 1;
	public static final int LOG_LEVEL_ERROR = 2;
	public static final int LOG_LEVEL_DEBUG = 3;
	private String tableName="BPM_AUDIT_TRAIL";


	private static DSCOPDBLogMe instance;


	public static DSCOPDBLogMe getInstance(){
		if(instance==null){
			instance = new DSCOPDBLogMe();
		}
		return instance;
	}

	private DSCOPDBLogMe() {

	}

	public static void logMe(int level, String WI_NAME, String callName, String logCode, Throwable throwable, String sessionId) {
		try {
			instance.insertDB(level, WI_NAME, callName, logCode, throwable.getMessage(), sessionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void logMe(int level, String WI_NAME, String callName, String logCode, String message, String sessionId) {
		try {
			instance.insertDB(level, WI_NAME, callName, logCode, message, sessionId);
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
		if (stackElement.getClassName().indexOf("DSCOPDBLogMe") >= 0
				&& stackElement.getMethodName().indexOf("logMe") >= 0) {
			stackElement = stackElements[5];
		}
		return "[" + currentThread.getId() + "]"
		+ "[" + stackElement.getClassName()
		+ "." + stackElement.getMethodName() + "()]"
		+ "[#" + stackElement.getLineNumber() + "]"
		+ " => " + logText;
	}
	
	private void insertDB(int level, String WI_NAME, String callName, String logCode, String message, String sessionId) throws Exception {
		
		
		
		if (level == DSCOPDBLogMe.LOG_LEVEL_INFO) {
			String columnName = "LOG_MODE, WI_NAME, CALL_NAME, LOG_CODE, MESSAGE";
			String strValues = "'I','"+ WI_NAME+"','"+ callName+"','"+logCode+"','"+message+"'";
			APCallCreateXML.APInsertNoLogging(tableName, columnName, strValues, sessionId);
			return;
		} 
		else if (level == DSCOPDBLogMe.LOG_LEVEL_DEBUG) {
			String columnName = "LOG_MODE, WI_NAME, CALL_NAME, LOG_CODE, MESSAGE";
			String strValues = "'D','"+ WI_NAME+"','"+ callName+"','"+logCode+"','"+message+"'";
			APCallCreateXML.APInsertNoLogging(tableName, columnName, strValues, sessionId);
			return;
		} 
		else if (level == DSCOPDBLogMe.LOG_LEVEL_ERROR) {
			String columnName = "LOG_MODE, WI_NAME, CALL_NAME, LOG_CODE, MESSAGE";
			String strValues = "'E','"+ WI_NAME+"','"+ callName+"','"+logCode+"','"+message+"'";
			APCallCreateXML.APInsertNoLogging(tableName, columnName, strValues, sessionId);
			return;
		} 
		throw new Exception("Unrecognized Log Level");
	}
}