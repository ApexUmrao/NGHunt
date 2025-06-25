package com.newgen.ao.laps.cache;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ProcessEventCache {

	private static ProcessEventCache cache = new ProcessEventCache();
	HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> eventDetectorMap = new HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>>();
	private HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> eventCallDependencyMap = new HashMap<Integer,HashMap<Integer, ArrayList<Integer>>>();
	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> eventReactorMap = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();
	private HashMap<Integer, ArrayList<String>> eventDetailsMap = new HashMap<Integer, ArrayList<String>>();
	private HashMap<Integer, ArrayList<CallEntity>> eventCallListMap = new HashMap<Integer, ArrayList<CallEntity>>();

	private ProcessEventCache(){
		try {
			eventDetectorMap = createEventDetectorMap();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In constructor eventDetectorMap: "+ eventDetectorMap.toString());
			eventDetailsMap = createEventDetailsMap();
			eventReactorMap = createEventReactorMap();
			eventCallDependencyMap = createCallDependencyMap();
			eventCallListMap = createEventCallListMap();
		} catch (Throwable e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"In Process EVent Cache constructor Catch ");
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exception=sw.toString();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Execption in Process EVent Cache constructor"+exception);
		}
	}

	public static ProcessEventCache getReference(){
		return cache;
	}

	public HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> getEventDetectorMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getEventDetectorMap: "+ eventDetectorMap.toString());
		return eventDetectorMap;
	}

	public HashMap<Integer, ArrayList<String>> getEventDetailsMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getEventDetailsMap: "+ eventDetailsMap.toString());
		return eventDetailsMap;
	}

	public HashMap<Integer, HashMap<String, ArrayList<Integer>>> getEventReactorMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getEventReactorMap: "+ eventReactorMap.toString());
		return eventReactorMap;
	}

	public HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> getCallDependencyMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getCallDependencyMap: "+ eventCallDependencyMap.toString());
		return eventCallDependencyMap;
	}
	
	public List<CallEntity> getEventCallList(int eventId){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getEventList: ");
		return eventCallListMap.get(eventId);
	}

	private HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> createEventDetectorMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createEventDetectorMap");
		HashMap<Integer, HashMap<Integer, Integer>> stageEventMapFinal =null;
		HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>> journeyMapFinal = null;
		HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>> appVersionMapFinal = null;
		HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>> channelMapFinal = null;
		HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> processEventMapFinal = new HashMap<Integer,  HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>>();

		String outputXML = APCallCreateXML.APSelect("SELECT PROCESS_ID, CHANNEL_NAME, APP_VERSION, JOURNEY_TYPE, STAGE_ID, EVENT_ID, NEXT_STAGE_ID FROM BPM_EVENT_DETECTOR");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			int processID = Integer.parseInt(xp.getValueOf("PROCESS_ID", start, end));
			String channelName = xp.getValueOf("CHANNEL_NAME", start, end);
			String appVersion = xp.getValueOf("APP_VERSION", start, end);
			String journeyType = xp.getValueOf("JOURNEY_TYPE", start, end);
			int stageID = Integer.parseInt(xp.getValueOf("STAGE_ID", start, end));
			int eventID = Integer.parseInt(xp.getValueOf("EVENT_ID", start, end));
			int nextStageID = Integer.parseInt(xp.getValueOf("NEXT_STAGE_ID", start, end).equals("")? "0" :xp.getValueOf("NEXT_STAGE_ID", start, end));
			if(processEventMapFinal.containsKey(processID)){
				channelMapFinal = processEventMapFinal.get(processID);
				if(channelMapFinal.containsKey(channelName)){
					appVersionMapFinal = channelMapFinal.get(channelName);
					if(appVersionMapFinal.containsKey(appVersion)){
						journeyMapFinal = appVersionMapFinal.get(appVersion);
						if(journeyMapFinal.containsKey(journeyType)){
							stageEventMapFinal = journeyMapFinal.get(journeyType);
							if(stageEventMapFinal.containsKey(stageID)){
								HashMap<Integer, Integer> eventList = stageEventMapFinal.get(stageID);
								eventList.put(eventID, nextStageID);
								stageEventMapFinal.put(stageID, eventList);
								journeyMapFinal.put(journeyType, stageEventMapFinal);
							}
							else{
								HashMap<Integer, Integer> eventList = new HashMap<Integer, Integer>();
								eventList.put(eventID, nextStageID);
								stageEventMapFinal.put(stageID, eventList);
								journeyMapFinal.put(journeyType, stageEventMapFinal);
								appVersionMapFinal.put(appVersion, journeyMapFinal);
								channelMapFinal.put(channelName, appVersionMapFinal);
								processEventMapFinal.put(processID, channelMapFinal);
							}
						}
						else{
							HashMap<Integer, Integer> eventList = new HashMap<Integer, Integer>();
							eventList.put(eventID, nextStageID);
							HashMap<Integer, HashMap<Integer, Integer>> stageMap = new HashMap<Integer, HashMap<Integer, Integer>>();
							stageMap.put(stageID, eventList);
							journeyMapFinal.put(journeyType, stageMap);
							appVersionMapFinal.put(appVersion, journeyMapFinal);
							channelMapFinal.put(channelName, appVersionMapFinal);
							processEventMapFinal.put(processID, channelMapFinal);
						}
					}
					else{
						HashMap<Integer, Integer> eventList = new HashMap<Integer, Integer>();
						eventList.put(eventID, nextStageID);

						HashMap<Integer, HashMap<Integer, Integer>> stageMap = new HashMap<Integer, HashMap<Integer, Integer>>();
						stageMap.put(stageID, eventList);

						HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>> journeyMap = new HashMap<String, HashMap<Integer,HashMap<Integer, Integer>>>();
						journeyMap.put(journeyType, stageMap);

						appVersionMapFinal.put(appVersion, journeyMap);
						channelMapFinal.put(channelName, appVersionMapFinal);
						processEventMapFinal.put(processID, channelMapFinal);
					}
				}
				else{
					HashMap<Integer, Integer> eventList = new HashMap<Integer, Integer>();
					eventList.put(eventID, nextStageID);

					HashMap<Integer, HashMap<Integer, Integer>> stageMap = new HashMap<Integer, HashMap<Integer, Integer>>();
					stageMap.put(stageID, eventList);

					HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>> journeyMap = new HashMap<String, HashMap<Integer,HashMap<Integer, Integer>>>();
					journeyMap.put(journeyType, stageMap);

					HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>> appVersionMap = new HashMap<String, HashMap<String,HashMap<Integer,HashMap<Integer,Integer>>>>();
					appVersionMap.put(appVersion, journeyMap);

					channelMapFinal.put(channelName, appVersionMap);
					processEventMapFinal.put(processID, channelMapFinal);
				}
			}
			else{
				HashMap<Integer, Integer> eventList = new HashMap<Integer, Integer>();
				eventList.put(eventID, nextStageID);

				HashMap<Integer, HashMap<Integer, Integer>> stageMap = new HashMap<Integer, HashMap<Integer, Integer>>();
				stageMap.put(stageID, eventList);

				HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>> journeyMap = new HashMap<String, HashMap<Integer,HashMap<Integer, Integer>>>();
				journeyMap.put(journeyType, stageMap);

				HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>> appVersionMap = new HashMap<String, HashMap<String, HashMap<Integer,HashMap<Integer, Integer>>>>();
				appVersionMap.put(appVersion, journeyMap);

				HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>  channelMap = new HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>();
				channelMap.put(channelName, appVersionMap);

				processEventMapFinal.put(processID, channelMap);
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processEventMapFinal :"+processEventMapFinal.toString());
		return processEventMapFinal;		
	}


	private HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> createCallDependencyMap() throws NGException{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createCallDependencyrMap");		
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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, eventCallDependencyMap+"");
		return eventCallDependencyMap;		
	}

	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> createEventReactorMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createEventReactorMap");		
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(eventId));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(callId));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(executionType));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(executionSeq));

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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createEventDetailsMap");		
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,String.valueOf(eventId));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,breakFlag);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,breakFlag);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,holdSessionTime);
			
			ArrayList<String> eventFlag = new ArrayList<String>();
			eventFlag.add(breakFlag);		
			eventFlag.add(holdSessionFlag);	
			eventFlag.add(holdSessionTime);	
			eventDetailsMap.put(eventId, eventFlag);
		}
		return eventDetailsMap;
	}
	
	private HashMap<Integer, ArrayList<CallEntity>> createEventCallListMap() throws Exception{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside createEventCallListMap Method");
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
	
	public ArrayList<CallEntity> getAsyncCallEventWiseCache(int eventId) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method");
		ArrayList<CallEntity> returnObj = new ArrayList<CallEntity>();
		HashMap<String, ArrayList<Integer>> asynCallListMap = getEventReactorMap().get(eventId);
		if(asynCallListMap != null){
			ArrayList<Integer> callList = asynCallListMap.get("A");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap();
				//Null Pointer handling for those Event ID in Event reactor whose entry is not present in Dependency Matrix
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Logger to check call list "+callList.toString()+" Event ID :"+eventId);
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache=null;
				try{
				 dependencyCallCache =  getCallDependencyMap().get(eventId);
				}catch(Exception e){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In getAsyncCallEventWiseCach Catch 1");
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String exception=sw.toString();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execption in getAsyncCallEventWiseCache in get dependecy call 1"+exception);
				}
				
				if(callList != null) {
					for (Integer callID : callList) {
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce=null;
						try{
						 ce = createEventCallEntity(eventId+"", callID, "A", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						}catch(Exception e){
							ce = createEventCallEntity(eventId+"", callID, "A", call, null);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In getAsyncCallEventWiseCach Catch 2");
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							e.printStackTrace(pw);
							String exception=sw.toString();
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execption in getAsyncCallEventWiseCache in get dependecy call 2"+exception);
						}
						returnObj.add(ce);
					}
				}
			}
			callList = asynCallListMap.get("S");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache=null;
				try{
				dependencyCallCache =  getCallDependencyMap().get(eventId);
				}catch(NullPointerException e){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In getAsyncCallEventWiseCach Catch 3");
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String exception=sw.toString();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execption in getAsyncCallEventWiseCache in get dependecy call 3"+exception);
				}
				if(callList != null) {
					for (Integer callID : callList) {
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce=null;
						try{
						 ce = createEventCallEntity(eventId+"", callID, "S", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						}catch(Exception e){
							ce = createEventCallEntity(eventId+"", callID, "S", call, null);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In getAsyncCallEventWiseCach Catch 4");
							StringWriter sw = new StringWriter();
							PrintWriter pw = new PrintWriter(sw);
							e.printStackTrace(pw);
							String exception=sw.toString();
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execption in getAsyncCallEventWiseCache in get dependecy call 4"+exception);
						}
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
		ce.setCallName(call!=null?call.get(0).toString():"");
		ce.setExecutionType(executionType);
		ce.setMandatory(call!=null?call.get(1).toString().equals("Y")?true:false:false);	
		ce.setIgnorable(call!=null?call.get(2).toString().equals("Y")?true:false:true);
		ce.setProductType(call!=null?call.get(3).toString():"");
		ce.setDependencyCallID(dependencyCallList);
		return ce;
	}
}
