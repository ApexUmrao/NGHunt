package com.newgen.dscop.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newgen.dscop.expiry.APCallCreateXML;
import com.newgen.dscop.expiry.CBGLogMe;
import com.newgen.dscopretryexp.callhandler.CallEntity;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ProcessEventCache {

	private static ProcessEventCache instance = null;
    private HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> eventCallDependencyMap = new HashMap<Integer,HashMap<Integer, ArrayList<Integer>>>();
	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> eventReactorMap = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();
	private HashMap<Integer, ArrayList<String>> eventDetailsMap = new HashMap<Integer, ArrayList<String>>();
	private HashMap<Integer, ArrayList<com.newgen.dscopretryexp.callhandler.CallEntity>> eventCallListMap = new HashMap<Integer, ArrayList<CallEntity>>();
   
	private ProcessEventCache(){
		try {
			eventDetailsMap = createEventDetailsMap();
			eventReactorMap = createEventReactorMap();
			eventCallDependencyMap = createCallDependencyMap();
			eventCallListMap = createEventCallListMap();
		} catch (NGException e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	public static ProcessEventCache getReference(){
		if(instance==null){
			instance=new ProcessEventCache();
		}
		return instance;
	}


	public HashMap<Integer, ArrayList<String>> getEventDetailsMap() throws NGException {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside getEventDetailsMap: ");
		return eventDetailsMap;
	}

	public HashMap<Integer, HashMap<String, ArrayList<Integer>>> getEventReactorMap() throws NGException {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO," Inside getEventReactorMap:");
		return eventReactorMap;
	}

	public HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> getCallDependencyMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"getCallDependencyMap");
		return eventCallDependencyMap;
	}
	
	public List<CallEntity> getEventCallList(int eventId){
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"getEventList: ");
		return eventCallListMap.get(eventId);
	}



	private HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> createCallDependencyMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside createCallDependencyrMap");		
		String outputXML = APCallCreateXML.APSelect("SELECT EVENT_ID, CALL_ID, DEPENDENT_CALL_ID FROM BPM_EVENT_DEPENDENCY_MATRIX");

		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int eventId = Integer.parseInt(xp.getValueOf("EVENT_ID", start, end));
			int callID = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			int dependencyCallID = Integer.parseInt(xp.getValueOf("DEPENDENT_CALL_ID", start, end));
			if(callID!=dependencyCallID){
				if(eventCallDependencyMap.containsKey(eventId)){
					HashMap<Integer, ArrayList<Integer>> dependencyMap = eventCallDependencyMap.get(eventId);
					if(dependencyMap.containsKey(callID)){
						ArrayList<Integer> dependCallList  = dependencyMap.get(callID);
						dependCallList.add(dependencyCallID);
						dependencyMap.put(callID, dependCallList);			
					}
					else{
						ArrayList<Integer> dependCallList = new ArrayList<Integer>();
						dependCallList.add(dependencyCallID);
						dependencyMap.put(callID, dependCallList);
					}
					eventCallDependencyMap.put(eventId, dependencyMap);
				} else {
					ArrayList<Integer> dependCallList = new ArrayList<Integer>();
					dependCallList.add(dependencyCallID);		

					HashMap<Integer, ArrayList<Integer>> dependencyMap = new HashMap<Integer, ArrayList<Integer>>();
					dependencyMap.put(callID, dependCallList);

					eventCallDependencyMap.put(eventId, dependencyMap);
				}
			}
		}
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, eventCallDependencyMap+"");
		return eventCallDependencyMap;		
	}

	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> createEventReactorMap() throws NGException {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside createEventReactorMap");		
		String outputXML = APCallCreateXML.APSelect("SELECT EVENT_ID, CALL_ID, EXECUTION_TYPE, EXECUTION_SEQUENCE FROM BPM_EVENT_REACTOR ORDER BY EVENT_ID, EXECUTION_SEQUENCE");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int eventId = Integer.parseInt(xp.getValueOf("EVENT_ID", start, end));
			int callId = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			String executionType = xp.getValueOf("EXECUTION_TYPE", start, end);
			int executionSeq = Integer.parseInt(xp.getValueOf("EXECUTION_SEQUENCE", start, end));
    		if(eventReactorMap.containsKey(eventId)){
				HashMap<String, ArrayList<Integer>> etMap = eventReactorMap.get(eventId);
				if(etMap.containsKey(executionType)){
					ArrayList<Integer> callList = etMap.get(executionType);
					callList.add(callId);
					etMap.put(executionType, callList);
				} else {
					ArrayList<Integer> callList = new ArrayList<Integer>();
					callList.add(callId);
					etMap.put(executionType, callList);
				}
				eventReactorMap.put(eventId, etMap);
			}
			else {								
				ArrayList<Integer> callList = new ArrayList<Integer>();
				callList.add(callId);		

				HashMap<String, ArrayList<Integer>> etMap = new HashMap<String, ArrayList<Integer>>();
				etMap.put(executionType, callList);

				eventReactorMap.put(eventId, etMap);
			}
		}
		return eventReactorMap;
	}

	private HashMap<Integer,ArrayList<String>> createEventDetailsMap() throws NGException {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside createEventDetailsMap");		
		String outputXML = APCallCreateXML.APSelect("SELECT EVENT_ID, BREAK_FLAG, HOLD_SESSION_FLAG, HOLD_SESSION_TIME FROM BPM_EVENT_DETAILS");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int eventId = Integer.parseInt(xp.getValueOf("EVENT_ID", start, end));
			String breakFlag = xp.getValueOf("BREAK_FLAG", start, end);
			String holdSessionFlag = xp.getValueOf("HOLD_SESSION_FLAG", start, end);
			String holdSessionTime = xp.getValueOf("HOLD_SESSION_TIME", start, end);
			ArrayList<String> eventFlag = new ArrayList<String>();
			eventFlag.add(breakFlag);		
			eventFlag.add(holdSessionFlag);	
			eventFlag.add(holdSessionTime);	
			eventDetailsMap.put(eventId, eventFlag);
		}
		return eventDetailsMap;
	}
	
	private HashMap<Integer, ArrayList<CallEntity>> createEventCallListMap() throws NGException{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside createEventCallListMap Method");
		String outputXML = APCallCreateXML.APSelect("SELECT DISTINCT EVENT_ID FROM BPM_EVENT_REACTOR");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int eventId = Integer.parseInt(xp.getValueOf("EVENT_ID", start, end));
			ArrayList<CallEntity> callList = getAsyncCallEventWiseCache(eventId);
			eventCallListMap.put(eventId, callList);
		}
		return eventCallListMap;
	}
	
	public ArrayList<CallEntity> getAsyncCallEventWiseCache(int eventId) throws NGException {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method eventId : "+eventId);
		ArrayList<CallEntity> returnObj = new ArrayList<CallEntity>();
		HashMap<String, ArrayList<Integer>> asynCallListMap = getEventReactorMap().get(eventId);
		if(asynCallListMap != null){
			ArrayList<Integer> callList = asynCallListMap.get("A");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  getCallDependencyMap().get(eventId);
				if(callList != null) {
					for (Integer callID : callList) {
						CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method callID : "+callID);
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce = createEventCallEntity(eventId+"", callID, "A", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						returnObj.add(ce);
					}
				}
			}
			callList = asynCallListMap.get("S");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  getCallDependencyMap().get(eventId);
				if(callList != null) {
					for (Integer callID : callList) {
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce = createEventCallEntity(eventId+"", callID, "S", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						returnObj.add(ce);
					}
				}
			}		
		}
		return returnObj;
	}
	
	public CallEntity createEventCallEntity(String eventId, int callID, String executionType, ArrayList<String> call, ArrayList<Integer> dependencyCallList){
		CallEntity ce = new CallEntity();
		ce.setStage(eventId+"");
		ce.setCallID(callID);
		ce.setCallName(call.get(0).toString());
		ce.setExecutionType(executionType);
		ce.setMandatory(call.get(1).toString().equals("Y")?true:false);	
		ce.setIgnorable(call.get(2).toString().equals("Y")?true:false);
		ce.setProductType(call.get(3).toString());
		ce.setDependencyCallID(dependencyCallList);
		return ce;
	}
	

}
