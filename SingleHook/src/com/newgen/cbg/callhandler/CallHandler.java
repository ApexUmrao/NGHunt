package com.newgen.cbg.callhandler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.newgen.cbg.cache.ProcessEventCache;
import com.newgen.cbg.cache.StageCallCache;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPUtils;

public class CallHandler {

	ExecutorService service = null;
	Future<String> task;
	Map<String, String> defaultAttributeMap;
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
		service = Executors.newFixedThreadPool(80);
	}

	public String executeCall(CallEntity callobj, Map<String, String> defaultAttributeMap, String sessionId, String stageId, String WI_Name, Boolean noGo) throws Exception{
		this.defaultAttributeMap = defaultAttributeMap;
		this.sessionId=sessionId;
//		this.stageId = stageId;
//		this.WI_NAME = WI_Name;
//		this.noGo = noGo;
		String outputXML="";
		String callName = callobj.getCallName();
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call ===> " +callName);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call EXECUTOR OBJECT NAME===> " + service.toString());
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER001", "Executing Call ===> " +callName, sessionId);
		ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callobj, WI_Name, stageId, noGo);
		if(callobj.getExecutionType().equals("A")){
			task = service.submit((Callable<String>) objCall);
		}
		else{
			outputXML= objCall.executeCall((HashMap<String, String>) defaultAttributeMap);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER002", "Call outputXML===> " +outputXML, sessionId);
		}
		return outputXML;
	}
	
	public String executeCallRetry(CallEntity callobj, Map<String, String> defaultAttributeMap, String sessionId, String stageId, String WI_Name, Boolean noGo) throws Exception{
		this.defaultAttributeMap = defaultAttributeMap;
		this.sessionId=sessionId;
//		this.stageId = stageId;
//		this.WI_NAME = WI_Name;
//		this.noGo = noGo;
		String outputXML="";
		String callName = callobj.getCallName();
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call ===> " +callName);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call EXECUTOR OBJECT NAME===> " + service.toString());
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER001", "Executing Call ===> " +callName, sessionId);
		ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callobj, WI_Name, stageId, noGo);
		if(callobj.getExecutionType().equals("A")){
			task = service.submit((Callable<String>) objCall);
		} else {
			outputXML= objCall.executeCall((HashMap<String, String>) defaultAttributeMap);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER002", "Call outputXML===> " +outputXML, sessionId);
		}
		//HardSet the status as Y as In R Event Execution Start From the call is not setting as Y By Rishabh on 18032024
		//Moving this to R Event status Code 0 COLMP-9195
		/*APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS", "'Y'", "WI_NAME = N'"+WI_Name+"' AND CALL_NAME = '"+callName+"'"
				+ " AND CALL_STATUS = 'N'", sessionId);*/
		
		return outputXML;
	}

	private <T> ICallExecutor instantiate(final String className, final Class<T> type, CallEntity callEntity, String WI_Name, String stageId, Boolean noGo) {
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call package ===> " +"com.newgen.cbg.calls."+className);
			Constructor<?> c = Class.forName("com.newgen.cbg.calls."+className).getConstructor(Map.class, String.class, String.class, String.class, Boolean.class, CallEntity.class);
			return (ICallExecutor) c.newInstance(defaultAttributeMap,sessionId,stageId,WI_Name,noGo,callEntity);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER100", "Exception ===> " +e, sessionId);
			e.printStackTrace();
		}
		return null;
	}

	public String executeDependencyCall(CallEntity callobj, Map<String, String> defaultAttributeMap, String sessionId, String WI_Name, Boolean noGo){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "ExecutingDependency Call package ===> " +"com.newgen.cbg.calls."+callobj.getDependencyCallID());
		this.defaultAttributeMap = defaultAttributeMap;
		this.sessionId=sessionId;
		String stageId = callobj.getStage();
//		this.WI_NAME = WI_Name;
//		this.noGo = noGo;
		String outputXML="";
		try {
			ArrayList<Integer> dependencycallList = callobj.getDependencyCallID();
			HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  ProcessEventCache.getReference().getCallDependencyMap().get(Integer.parseInt(callobj.getStage()));
			if(dependencycallList != null){
				for (Integer dependencyCallID : dependencycallList) {
					HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "ExecutingDependency StageID ===> " +callobj.getStage() + "  "+ dependencyCallID );
					ArrayList<String> call = callCache.get(dependencyCallID);
					CallEntity callEntity = DSCOPUtils.getInstance().createCallEntity(callobj.getStage(), dependencyCallID, callobj.getExecutionType(), call, dependencyCallCache!=null? dependencyCallCache.get(dependencyCallID): null);
					if(callEntity.isMandatory()){
						String callName = callEntity.getCallName();
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Previous Call Status ===> " +noGo);
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER003", "Executing Dependency Call ===> " +callName, sessionId);
						if(!noGo){
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Dependency Call ===> " +callName);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing  Dependency Call EXECUTOR OBJECT NAME===> " + service.toString());
							ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callEntity, WI_Name, stageId, noGo);
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "callobj.getExecutionType()===> " + callobj.getExecutionType());
							if(callobj.getExecutionType().equals("A")){
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER004", "Executing Async ===> " +callName, sessionId);
								task = service.submit((Callable<String>) objCall);
							}
							else{
								outputXML= objCall.executeCall((HashMap<String, String>) defaultAttributeMap);
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER005", "Dependency Call outputXML===> " +outputXML, sessionId);
							}
						}
						else{
							String valList = "'"+ WI_Name +"',"+ stageId +", '"+callName+"', 'N',SYSTIMESTAMP,'Parent call failed'";
							APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, REASON", valList, sessionId);
						}
					}
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER100", "Exception ===> " +e, sessionId);
			e.printStackTrace();
		}
		return outputXML;
	}

	public void executeParallelCall(List<CallEntity> callList, Map<String, String> defaultAttributeMap, String sessionId, String stageId, String WI_Name, Boolean noGo){
		this.defaultAttributeMap = defaultAttributeMap;
		this.sessionId=sessionId;
//		this.stageId = stageId;
//		this.WI_NAME = WI_Name;
//		this.noGo = noGo;
		for (CallEntity callEntity : callList) {
			if(callEntity.isMandatory()){
				String callName = callEntity.getCallName();
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call ===> " +callName);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Executing Call EXECUTOR OBJECT NAME===> " + service.toString());
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_Name, auditCallName, "CALLHANDLER006", "Executing Call ===> " +callName, sessionId);
				ICallExecutor objCall = instantiate(callName, ICallExecutor.class, callEntity, WI_Name, stageId, noGo);
				task = service.submit((Callable<String>) objCall);
			}
		}
	}
}
