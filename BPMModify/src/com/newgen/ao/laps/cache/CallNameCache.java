package com.newgen.ao.laps.cache;

import java.util.HashMap;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class CallNameCache {

	private HashMap<Integer, String> callMap = new HashMap<Integer, String>();
	private static CallNameCache instance = new CallNameCache();

	public static CallNameCache getInstance() {
		return instance;
	}
	
	private CallNameCache(){
		getCachedStagecallMap();
	}

	public HashMap<Integer,String> getCachedStagecallMap() {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call Name is ");
		if (callMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"call Name is empty ");
			try {
				callMap = createStagecallMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of call name is " + callMap.toString());
			} catch (NGException e) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errror" + e);
			}
		}
		return callMap;
	}

	private HashMap<Integer,String> createStagecallMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside createStagecallMap once");
		callMap = new HashMap<Integer,String>();
		String outputXML = APCallCreateXML
				.APSelect("SELECT CALL_ID,CALL_NAME FROM CHANNEL_CALL_NAME ORDER BY CALL_ID");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {

			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int callId = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			String callname = xp.getValueOf("CALL_NAME", start, end);
			callMap.put(callId, callname);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"Value of callName are " + callMap.toString());

		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside callMap " + callMap.toString());
		return callMap;
	}
}
