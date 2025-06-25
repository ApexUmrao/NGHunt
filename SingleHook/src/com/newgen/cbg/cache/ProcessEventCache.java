package com.newgen.cbg.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.implementation.BpmColumnMappingDetails;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ProcessEventCache {

	private static ProcessEventCache instance = null;
	HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> eventDetectorMap = new HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>>();
	private HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> eventCallDependencyMap = new HashMap<Integer,HashMap<Integer, ArrayList<Integer>>>();
	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> eventReactorMap = new HashMap<Integer, HashMap<String, ArrayList<Integer>>>();
	private HashMap<Integer, ArrayList<String>> eventDetailsMap = new HashMap<Integer, ArrayList<String>>();
	private HashMap<Integer, ArrayList<CallEntity>> eventCallListMap = new HashMap<Integer, ArrayList<CallEntity>>();
	private HashMap<String,List<String>> eventMap = new HashMap<>();
	private HashMap<String,HashMap<String,RequestAttributeDetails>> sourcingChannelAttribMap = new HashMap<String,HashMap<String,RequestAttributeDetails>>();
	private HashMap<String,LpMasterDocDetails> createLpDocMasterMap = new HashMap<String,LpMasterDocDetails>();

	private HashMap<String, BpmColumnMappingDetails> columnMappingDetailsMap = new HashMap<>();
	
	private HashMap<String, ArrayList<String>> stageBlankAttributeMap = new HashMap<>();

	private ProcessEventCache() throws Exception{
		//try {
			eventDetectorMap = createEventDetectorMap();
			eventDetailsMap = createEventDetailsMap();
			eventReactorMap = createEventReactorMap();
			eventCallDependencyMap = createCallDependencyMap();
			eventCallListMap = createEventCallListMap();
			eventMap = createEventDetectorMapNew();
			sourcingChannelAttribMap = createRequestMappingMap();
	//		stageBlankAttributeMap = createMapStageBlankConfigMaster();
			columnMappingDetailsMap = createColumnMappingDetailsMap();
			createLpDocMasterMap = createLpDocMasterMap();

			/*
			 * } catch (NGException e) { DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			 * e.printStackTrace(); }
			 */
	}

	public static ProcessEventCache getReference() {
		if(instance==null){
			try {
				instance=new ProcessEventCache();
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"ProcessEventCache: "+ e.getMessage());
				e.printStackTrace();
			}
		}
		return instance;
	}

	public HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> getEventDetectorMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE," Inside getEventDetectorMap: ");
		return eventDetectorMap;
	}

	public HashMap<Integer, ArrayList<String>> getEventDetailsMap() throws NGException {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE," Inside getEventDetailsMap: ");
		return eventDetailsMap;
	}

	public HashMap<Integer, HashMap<String, ArrayList<Integer>>> getEventReactorMap() throws NGException {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE," Inside getEventReactorMap: ");
		return eventReactorMap;
	}

	public HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> getCallDependencyMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE," Inside getCallDependencyMap: ");
		return eventCallDependencyMap;
	}

	public List<CallEntity> getEventCallList(int eventId){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"getEventList: ");
		return eventCallListMap.get(eventId);
	}

	public HashMap<String,List<String>> getEventMap(){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"getEventMap: ");
		return eventMap;
	}

	public HashMap<String,RequestAttributeDetails> getSourcingChannelAttribMap(String sourcingChannel){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"getSourcingChannelAttribMap: ");
		return sourcingChannelAttribMap.get(sourcingChannel);
	}
	
	public HashMap<String, ArrayList<String>> getStageBlankConfigMap(){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,
				"getStageBlankConfigMap: ");
		return stageBlankAttributeMap;
	}

	public HashMap<String,LpMasterDocDetails> getLpDocMasterMap(){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,
				"getLpDocMasterMap: ");
		return createLpDocMasterMap;
	}


	private HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> createEventDetectorMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createEventDetectorMap");
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
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE, "Generted processEventMapFinal : "+processEventMapFinal.toString());
		return processEventMapFinal;		
	}

	private HashMap<String,List<String>> createEventDetectorMapNew() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] Inside");
		try {
			//HashMap<String,List<String>> eventMap = new HashMap<>();
			List<String> eventIdDetails = null;

			String columns = DSCOPUtils.getInstance().requestToDefaultValueMap().get("EVENT_DETECTOR_TABLE_COLUMNS_LIST");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] columns: "+columns);
			String tableName = DSCOPUtils.getInstance().requestToDefaultValueMap().get("EVENT_DETECTOR_TABLE_NAME");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew]: tableName: "+tableName);
			String returnColumns = DSCOPUtils.getInstance().requestToDefaultValueMap().get("EVENT_DETECTOR_TABLE_RETURN_COLUMNS");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] returnColums: "+returnColumns);

			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] Query: SELECT "+columns+","+returnColumns+" FROM " + tableName);
			String outputXML = APCallCreateXML.APSelect("SELECT "+columns+","+returnColumns+" FROM " + tableName);
			String[] columnNames = columns.split(",");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] ColumnNames: "+Arrays.toString(columnNames));
			String[] returnColumnNames = returnColumns.split(",");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] returnColumnNames: "+Arrays.toString(returnColumnNames));

			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
			int end = 0;
			for(int i = 0; i < noOfFields; ++i){
				StringBuffer key = new StringBuffer("");
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				for(String columnName:columnNames){
					key.append(xp.getValueOf(columnName, start, end)+"#");
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] key: "+key);
				if(!eventMap.containsKey(String.valueOf(key))){
					eventIdDetails = new ArrayList<>();
					for(String columnName:returnColumnNames){
						eventIdDetails.add(xp.getValueOf(columnName, start, end));
					}
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"[createEventDetectorMapNew] eventIdDetails: "+eventIdDetails);
					eventMap.put(String.valueOf(key), eventIdDetails);
				} 
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE, "[createEventDetectorMapNew] Generated eventMap : "+eventMap.toString());
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"[createEventDetectorMapNew] Exception: "+e);
		}

		return eventMap;		
	}


	private HashMap<Integer,HashMap<Integer, ArrayList<Integer>>> createCallDependencyMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createCallDependencyrMap");		
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
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE, eventCallDependencyMap+"");
		return eventCallDependencyMap;		
	}

	private HashMap<Integer, HashMap<String, ArrayList<Integer>>> createEventReactorMap() throws NGException {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createEventReactorMap");		
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
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createEventDetailsMap");		
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
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,String.valueOf(eventId));
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,breakFlag);
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,breakFlag);
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,holdSessionTime);

			ArrayList<String> eventFlag = new ArrayList<String>();
			eventFlag.add(breakFlag);		
			eventFlag.add(holdSessionFlag);	
			eventFlag.add(holdSessionTime);	
			eventDetailsMap.put(eventId, eventFlag);
		}
		return eventDetailsMap;
	}

	private HashMap<Integer, ArrayList<CallEntity>> createEventCallListMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createEventCallListMap Method");
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
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside getAsyncCallEventWise Method eventId : "+eventId);
		ArrayList<CallEntity> returnObj = new ArrayList<CallEntity>();
		HashMap<String, ArrayList<Integer>> asynCallListMap = getEventReactorMap().get(eventId);
		if(asynCallListMap != null){
			ArrayList<Integer> callList = asynCallListMap.get("A");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  getCallDependencyMap().get(eventId);
				if(callList != null) {
					for (Integer callID : callList) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside getAsyncCallEventWise Method callID : "+callID);
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

	public HashMap<String,HashMap<String,RequestAttributeDetails>> createRequestMappingMap() throws NGException{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createRequestMappingMap");		
		String outputXML = APCallCreateXML.APSelect("SELECT SOURCING_CHANNEL, ATTRIBUTE_ID, ATTRIBUTE_NAME,"
				+ " ATTRIBUTE_TYPE, MAPPING_VAR_NAME, MAPPING_COLUMN_NAME, DELETE_TABLE_ENTRY, COMPLEX_TABLE_NAME from BPM_REQUEST_ATTRIBUTE_DETAILS");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		HashMap<String,RequestAttributeDetails> attribMap;
		for(int i = 0; i < noOfFields; ++i){
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String sourcingChannel = xp.getValueOf("SOURCING_CHANNEL", start, end);
			int attributeId = Integer.parseInt(xp.getValueOf("ATTRIBUTE_ID", start, end));
			String attributeName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
			String attributeType = xp.getValueOf("ATTRIBUTE_TYPE", start, end);
			String mappingVarName = xp.getValueOf("MAPPING_VAR_NAME", start, end);
			String mappingColumnName = xp.getValueOf("MAPPING_COLUMN_NAME", start, end);
			String deleteTableEntry = xp.getValueOf("DELETE_TABLE_ENTRY", start, end);
			String complexTableName = xp.getValueOf("COMPLEX_TABLE_NAME", start, end);
			RequestAttributeDetails rad = new RequestAttributeDetails();
			rad.setAttributeId(attributeId);
			rad.setAttributeName(attributeName);
			rad.setAttributeType(attributeType);
			rad.setMappingColumnName(mappingColumnName);
			rad.setMappingVarName(mappingVarName);
			rad.setDeleteTableEntry(deleteTableEntry);
			rad.setComplexTableName(complexTableName);

			if(sourcingChannelAttribMap.containsKey(sourcingChannel)){
				attribMap = sourcingChannelAttribMap.get(sourcingChannel);
				attribMap.put(attributeName, rad);
				sourcingChannelAttribMap.put(sourcingChannel, attribMap);
			} else {
				attribMap = new HashMap<String,RequestAttributeDetails>();
				attribMap.put(attributeName, rad);
				sourcingChannelAttribMap.put(sourcingChannel, attribMap);
			}
			
		}
		return sourcingChannelAttribMap;
	}

	
	public HashMap<String, BpmColumnMappingDetails> getColumnMappingDetails(){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,
				"getColumnMappingDetails: ");
		return columnMappingDetailsMap;
	}
	
	public HashMap<String, BpmColumnMappingDetails> createColumnMappingDetailsMap()throws NGException {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createColumnMappingDetailsMap");
		String outputXML = APCallCreateXML.APSelect("SELECT TABLE_NAME, TAG_NAME, COLUMN_NAME from BPM_COLUMN_MAPPING_DETAILS");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		HashMap<String, BpmColumnMappingDetails> returnObject = new HashMap<String, BpmColumnMappingDetails>();
		for (int i = 0; i < noOfFields; ++i) {
			BpmColumnMappingDetails bpmCMDetails =new  BpmColumnMappingDetails();
			HashMap<String,String> kvMap = new HashMap<String,String>();
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
	
			String tableName = xp.getValueOf("TABLE_NAME", start,end);
			String tagName =(xp.getValueOf("TAG_NAME", start, end));
			String columnName = xp.getValueOf("COLUMN_NAME", start, end);
	
			String key = tableName;
			if(returnObject.containsKey(key)) {
				bpmCMDetails = returnObject.get(key);
				kvMap = bpmCMDetails.getHashmap();
				kvMap.put(tagName, columnName);
				bpmCMDetails.setHashmap(kvMap);
				returnObject.put(key, bpmCMDetails);
			} else {
				kvMap.put(tagName, columnName);
				bpmCMDetails.setHashmap(kvMap);
				returnObject.put(key, bpmCMDetails);
			}
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"columnMappingDetailsMap : "+columnMappingDetailsMap);
		return returnObject;
	}
	
	public HashMap<String,LpMasterDocDetails> createLpDocMasterMap() throws NGException{
		
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_CACHE,"Inside createLpDocMasterMap Method");
		String outputXMLMasterDoc = APCallCreateXML.APSelect("SELECT BPM_DOC_NAME, LP_DOC_NAME, LP_DOC_ID FROM BPM_COP_DEVIATION_DOC_MASTER ");					
		XMLParser xpDocParserMaster = new XMLParser(outputXMLMasterDoc);
		HashMap<String,LpMasterDocDetails> docMasterMap = new HashMap<String,LpMasterDocDetails>();
		int start = xpDocParserMaster.getStartIndex("Records", 0, 0);
		int deadEnd = xpDocParserMaster.getEndIndex("Records", start, 0);
		int noOfFields = xpDocParserMaster.getNoOfFields("Record", start,deadEnd);
		int end = 0;
		for(int i = 0; i < noOfFields; ++i){
			LpMasterDocDetails msDoc = new LpMasterDocDetails(); 
			start = xpDocParserMaster.getStartIndex("Record", end, 0);
			end = xpDocParserMaster.getEndIndex("Record", start, 0);
			
			msDoc.setBpmDocName(xpDocParserMaster.getValueOf("BPM_DOC_NAME",start,end));
			msDoc.setLpDocName(xpDocParserMaster.getValueOf("LP_DOC_NAME",start,end));
			msDoc.setLpDocId(xpDocParserMaster.getValueOf("LP_DOC_ID",start,end));
			docMasterMap.put(xpDocParserMaster.getValueOf("BPM_DOC_NAME",start,end),msDoc);
		}
		return docMasterMap;
	}
}
