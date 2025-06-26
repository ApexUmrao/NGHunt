package com.newgen.ao.laps.callhandler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;

public class CallHandler {

	ExecutorService service = null;
	Future<String> task;
	//Map<String, String> defaultAttributeMap; //16/12/21
	private static CallHandler instance;
	private String sessionId;
	private String auditCallName = "CALLHANDLER";

	public static CallHandler getInstance(){
		if(instance==null){
			instance = new CallHandler();
		}
		return instance;
	}

	private CallHandler() {
		service = Executors.newFixedThreadPool(20);
	}

	public String executeCall(CallEntity callobj, Map<String, String> defaultAttributeMap, String sessionId, String stageId, String WI_Name, Boolean noGo) throws Exception{
		//this.defaultAttributeMap = defaultAttributeMap; //16/12/21
		this.sessionId=sessionId;
		String outputXML="";
		String callName = callobj.getCallName();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Call ===> " +callName);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Call EXECUTOR OBJECT NAME===> " + service.toString());
		LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER001", "Executing Call ===> " +callName, sessionId);
		ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callobj, WI_Name, stageId, noGo, defaultAttributeMap); //16/12/21 defaultAttributeMap parameter added
		if(callobj.getExecutionType().equals("A")){
			task = service.submit((Callable<String>) objCall);
		} else {
			outputXML= objCall.executeCall((HashMap<String, String>) defaultAttributeMap);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER002", "Call outputXML===> " +outputXML, sessionId);
		}
		return outputXML;
	}

	private <T> ICallExecutor instantiate(final String className, final Class<T> type, CallEntity callEntity, String WI_Name, String stageId, Boolean noGo,Map<String, String> defaultAttributeMap) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Call package ===> " 
					+"com.newgen.ao.laps.calls."+className);
			if(className.equalsIgnoreCase("SwiftDocumentGen")){ //08/12/21 Sanal Grover
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," 16/12/21: " + "winame: " + WI_Name + " defaultAttributeMap :" +defaultAttributeMap);
			}
			Constructor<?> c = Class.forName("com.newgen.ao.laps.calls."+className).getConstructor(Map.class, String.class, String.class, String.class, Boolean.class, CallEntity.class);
			return (ICallExecutor) c.newInstance(defaultAttributeMap,sessionId,stageId,WI_Name,noGo,callEntity);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER100", "Exception ===> " +e, sessionId);
			e.printStackTrace();
		}
		return null;
	}

	public String executeDependencyCall(CallEntity callobj, Map<String, String> defaultAttributeMap, String sessionId, String WI_Name, Boolean noGo){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ExecutingDependency Call package ===> " 
				+"com.newgen.ao.laps.calls."+callobj.getDependencyCallID());
		//this.defaultAttributeMap = defaultAttributeMap; //16/12/21 
		this.sessionId=sessionId;
		String stageId = callobj.getStage();
		String outputXML="";
		try {
			ArrayList<Integer> dependencycallList = callobj.getDependencyCallID();
			HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  ProcessEventCache.getReference().getCallDependencyMap().get(Integer.parseInt(callobj.getStage()));
			if(dependencycallList != null){
				for (Integer dependencyCallID : dependencycallList) {
					HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ExecutingDependency StageID ===> " +callobj.getStage() + "  "+ dependencyCallID );
					ArrayList<String> call = callCache.get(dependencyCallID);
					CallEntity callEntity = LapsUtils.getInstance().createCallEntity(callobj.getStage(), dependencyCallID, callobj.getExecutionType(), call, dependencyCallCache!=null? dependencyCallCache.get(dependencyCallID): null);
					if(callEntity.isMandatory()){
						String callName = callEntity.getCallName();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeDependencyCall defaultAttributeMap "
								+ "passed in call "+callName+": "+defaultAttributeMap.toString());
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Previous Call Status ===> " +noGo);
						LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER003", "Executing Dependency Call ===> " +callName, sessionId);
						if(!noGo){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Dependency Call ===> " +callName);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing  Dependency Call EXECUTOR OBJECT NAME===> " + service.toString());
							ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callEntity, WI_Name, stageId, noGo, defaultAttributeMap); //16/12/21 parameter added defaultAttributeMap
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "callobj.getExecutionType()===> " + callobj.getExecutionType());
							if(callobj.getExecutionType().equals("A")){
								LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER004", "Executing Async ===> " +callName, sessionId);
								task = service.submit((Callable<String>) objCall);
							}
							else{
								outputXML= objCall.executeCall((HashMap<String, String>) defaultAttributeMap);
								LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER005", "Dependency Call outputXML===> " +outputXML, sessionId);
							}
						}
						else{
							String valList = "'"+ WI_Name +"',"+ stageId +", '"+callName+"', 'N',SYSTIMESTAMP,'Parent call failed'";
							APCallCreateXML.APInsert("USR_0_CBG_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, REASON", valList, sessionId);
						}
					}
				}
			}		
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER100", "Exception ===> " +e, sessionId);
			e.printStackTrace();
		}
		return outputXML;
	}

	public void executeParallelCall(List<CallEntity> callList, Map<String, String> defaultAttributeMap, String sessionId, String stageId, String WI_Name, Boolean noGo){
		//this.defaultAttributeMap = defaultAttributeMap; //16/12/21
		this.sessionId=sessionId;
		for (CallEntity callEntity : callList) {
			if(callEntity.isMandatory()){
				String callName = callEntity.getCallName();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Call ===> " +callName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Call EXECUTOR OBJECT NAME===> " + service.toString());
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER006", "Executing Call ===> " +callName, sessionId);
				ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callEntity, WI_Name, stageId, noGo, defaultAttributeMap); //16/12/21 Parameter defaultAttributeMap Added 
				task = service.submit((Callable<String>) objCall);
			}
		}
	}
}
