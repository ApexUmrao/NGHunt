package com.newgen.ao.laps.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class StageCallCache {
	
	private static StageCallCache cache = new StageCallCache();
	private HashMap<Integer,HashSet<Integer>> stageCallMap = new HashMap<Integer, HashSet<Integer>>();
	private HashMap<Integer, String> stageMap = new HashMap<Integer, String>();
	private HashMap<Integer, ArrayList<String>> callMap = new HashMap<Integer, ArrayList<String>>();
//	private HashMap<String, ArrayList<String>> callMapName = null;
	private HashMap<String, ArrayList<String>> callNameMap = new HashMap<String, ArrayList<String>>();
	private HashMap<Integer, ArrayList<Integer>> stagePreqCallMap = new HashMap<Integer, ArrayList<Integer>>();
	private HashMap<String,String> defaultMap = new HashMap<String,String>();
	private HashMap<Integer,Integer> stageCallCount = new HashMap<Integer, Integer>();
	
	private StageCallCache(){
		try {
			stageCallMap = createStageCallMap();
			stagePreqCallMap = createPreqStageCallMap();
			stageMap = createStageMap();
			callMap = createCallMap();
			callNameMap = createCallNameMap();
			defaultMap = createDefaultValueMap();
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
	}

	public static StageCallCache getReference(){
		return cache;
	}
	
	public HashMap<Integer, HashSet<Integer>> getCachedStageCallMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,stageCallMap.toString());
		return stageCallMap;
	}
	
	public HashMap<Integer,Integer> getCachedStageCallCountMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"CACHE STAGECALL COUNT: "+stageCallCount);
		return stageCallCount;
	}
	
	public HashMap<Integer, ArrayList<Integer>> getCachedStagePreqCallMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getCachedStagePreqCallMap: "+stagePreqCallMap.toString());
		return stagePreqCallMap;
	}
	
	public HashMap<Integer, String> getCachedStageMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getCachedStageMap: "+ stageMap.toString());
		return stageMap;
	}
	
	public HashMap<Integer, ArrayList<String>> getCachedCallMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getCachedCallMap: "+ callMap.toString());
		return callMap;
	}
	
	
	public HashMap<String, ArrayList<String>> getCachedCallNameMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getCachedCallNameMap: "+ callNameMap.toString());
		return callNameMap;
	}
	
	public HashMap<String, String> getCachedDefaultMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getCachedDefaultMap: "+ defaultMap.toString());
		return defaultMap;
	}
	
	private HashMap<Integer, HashSet<Integer>> createStageCallMap() throws Exception{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createStageCallMap");
		stageCallMap = new HashMap<Integer, HashSet<Integer>>();
		String outputXML = APCallCreateXML.APSelect("SELECT STAGE_ID, CALL_ID FROM USR_0_CBG_STAGE_CALL_MASTER ORDER BY STAGE_ID");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int stageId = Integer.parseInt(xp.getValueOf("STAGE_ID", start, end));
			int callId = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(stageId));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(callId));
			if(stageCallMap.containsKey(stageId)){
				HashSet<Integer> cl = stageCallMap.get(stageId);
				cl.add(callId);
				stageCallMap.put(stageId, cl);				
			}
			else {
				HashSet<Integer> callList = new HashSet<Integer>();
				callList.add(callId);
				stageCallMap.put(stageId, callList);
			}
		}
		
		Iterator<Entry<Integer, HashSet<Integer>>> itr = stageCallMap.entrySet().iterator();
		while(itr.hasNext()){
			Entry<Integer, HashSet<Integer>> pair = (Entry<Integer, HashSet<Integer>>) itr.next();
			int stageId = (Integer) pair.getKey();
			HashSet<Integer> callList = new HashSet<Integer>();
			callList = stageCallMap.get(stageId);
			stageCallCount.put(stageId, callList.size());			
		}
		
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"stagecallcount : "+stageCallCount.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,stageCallMap.toString());
		return stageCallMap;		
	}
	
	private HashMap<Integer, ArrayList<Integer>> createPreqStageCallMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createPreqStageCallMap");
		stagePreqCallMap = new HashMap<Integer, ArrayList<Integer>>();
		String outputXML = APCallCreateXML.APSelect("SELECT STAGE_ID, PREREQUISITE_CALL_ID FROM USR_0_CBG_PREQ_CALL_MASTER ORDER BY STAGE_ID, PREREQUISITE_CALL_SEQ");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int stageId = Integer.parseInt(xp.getValueOf("STAGE_ID", start, end));
			int preqCallId = Integer.parseInt(xp.getValueOf("PREREQUISITE_CALL_ID", start, end));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(stageId));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(preqCallId));
			if(stagePreqCallMap.containsKey(stageId)){
				ArrayList<Integer> cl = stagePreqCallMap.get(stageId);
				cl.add(preqCallId);
				stagePreqCallMap.put(stageId, cl);				
			}
			else {
				ArrayList<Integer> callList = new ArrayList<Integer>();
				callList.add(preqCallId);
				stagePreqCallMap.put(stageId, callList);
			}
			
		}
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,stagePreqCallMap.toString());
		return stagePreqCallMap;		
	}

	private HashMap<Integer, ArrayList<String>> createCallMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside1 createCallMap");
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,callMap.toString());
		return callMap;		
	}
	
	private HashMap<String, ArrayList<String>> createCallNameMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside1 createCallMap");
		callNameMap = new HashMap<String, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("SELECT CALL_ID, CALL_NAME, IS_MANDATORY, IS_IGNORABLE, PRODUCT_TYPE FROM USR_0_CBG_CALL_MASTER");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String callName = xp.getValueOf("CALL_NAME", start, end);
			ArrayList<String> call = new ArrayList<String>(4);
			call.add(xp.getValueOf("CALL_NAME", start, end));
			call.add(xp.getValueOf("IS_MANDATORY", start, end));
			call.add(xp.getValueOf("IS_IGNORABLE", start, end));
			call.add(xp.getValueOf("PRODUCT_TYPE", start, end));
			callNameMap.put(callName, call);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,callNameMap.toString());
		return callNameMap;		
	}
	
	private HashMap<Integer, String> createStageMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside2 createStageMap");
		stageMap = new HashMap<Integer, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT STAGE_ID, STAGE_NAME FROM USR_0_CBG_STAGE_MASTER ORDER BY STAGE_ID");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			stageMap.put(Integer.parseInt(xp.getValueOf("STAGE_ID", start, end)), xp.getValueOf("STAGE_NAME", start, end));
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,stageMap.toString());
		return stageMap;		
	}
	
	private HashMap<String, String> createDefaultValueMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside2 createStageMap");
		defaultMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT DEFAULT_KEY, DEFAULT_VALUE FROM USR_0_CBG_DEF_VAL_MASTER");
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,defaultMap.toString());
		return defaultMap;		
	}
}
