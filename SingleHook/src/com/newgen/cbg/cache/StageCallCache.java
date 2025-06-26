package com.newgen.cbg.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class StageCallCache {

	private static StageCallCache cache = new StageCallCache();
	private HashMap<Integer, ArrayList<String>> callMap = new HashMap<Integer, ArrayList<String>>();
	private HashMap<String,String> defaultMap = new HashMap<String,String>();

	private StageCallCache(){
		try {
			callMap = createCallMap();
			defaultMap = createDefaultValueMap();
			
		} catch (NGException e) {
			e.printStackTrace();
		}
	}

	public static StageCallCache getReference(){
		return cache;
	}

	public HashMap<Integer, ArrayList<String>> getCachedCallMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Indide getCachedCallMap: ");	
		return callMap;
	}

	public HashMap<String, String> getCachedDefaultMap() throws NGException{
		return defaultMap;
	}

	private HashMap<Integer, ArrayList<String>> createCallMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside1 createCallMap");
		callMap = new HashMap<Integer, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("SELECT CALL_ID, CALL_NAME, IS_MANDATORY, IS_IGNORABLE, PRODUCT_TYPE FROM USR_0_CBG_CALL_MASTER");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int callId = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			ArrayList<String> call = new ArrayList<String>(4);
			call.add(xp.getValueOf("CALL_NAME", start, end));
			call.add(xp.getValueOf("IS_MANDATORY", start, end));
			call.add(xp.getValueOf("IS_IGNORABLE", start, end));
			call.add(xp.getValueOf("PRODUCT_TYPE", start, end));
			callMap.put(callId, call);
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,callMap.toString());
		return callMap;		
	}


	private HashMap<String, String> createDefaultValueMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside2 createStageMap");
		defaultMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT DEFAULT_KEY, DEFAULT_VALUE FROM USR_0_DSCOP_DEF_VAL_MASTER");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			defaultMap.put(xp.getValueOf("DEFAULT_KEY", start, end), xp.getValueOf("DEFAULT_VALUE", start, end));
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,defaultMap.toString());
		return defaultMap;		
	}
	




}
