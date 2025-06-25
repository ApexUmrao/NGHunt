package com.newgen.cbg.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newgen.cbg.cache.ProcessEventCache;
import com.newgen.cbg.cache.StageCallCache;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.implementation.SingleUserConnection;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class R_Event implements IEventHandler{
	
	private String stageId;
	private String sysRefNo;
	private String applicationName;
	private String language;
	private String sourcingCenter;
	private String sourcingChannel;
	private String requestedDateTime;
	private String applicationVersion;
	private String applicationJourney;
	private String sessionId;

	private HashMap<String, String> defaultAttributeMap;
	boolean moveToExit = false;
	
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside COP_REvent");
		String WI_NAME = paramCoreEvent.getWI_NAME();
		stageId = paramCoreEvent.getStageId();
		sysRefNo = paramCoreEvent.getSysRefNo();
		applicationName = paramCoreEvent.getApplicationName();
		language = paramCoreEvent.getLanguage();
		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();
		try {

			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"applicationName: "+applicationName+" applicationVersion: "+ applicationVersion+" applicationJourney : "+applicationJourney);
			String output = APCallCreateXML.APSelect("SELECT SOURCING_CHANNEL, APP_VERSION, JOURNEY_TYPE "
					+ " FROM EXT_DSCOP WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser parser = new XMLParser(output);
			int maincode = Integer.parseInt(parser.getValueOf("MainCode"));
			if(maincode == 0){
				if(Integer.parseInt(parser.getValueOf("TotalRetrieved")) > 0){
					applicationVersion = parser.getValueOf("APP_VERSION");
					applicationJourney = parser.getValueOf("JOURNEY_TYPE");
					sourcingChannel = parser.getValueOf("SOURCING_CHANNEL");
				}
			}
			
			String outputXML = "";
			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName,DSCOPConfigurations.getInstance().UserName,DSCOPConfigurations.getInstance().Password);
			defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();
			int eventID = 0;
			if (stageId == null || "".equals(stageId)) {
				output = APCallCreateXML.APSelect("SELECT STAGE_ID FROM (SELECT STAGE_ID FROM USR_0_DSCOP_CALL_OUT "
						+ " WHERE WI_NAME = '" + WI_NAME + "'  AND CALL_STATUS = 'N' ORDER BY CALL_DT) WHERE ROWNUM = 1");
				parser = new XMLParser(output);
				maincode = Integer.parseInt(parser.getValueOf("MainCode"));
				if(maincode == 0){
					if(Integer.parseInt(parser.getValueOf("TotalRetrieved")) > 0){
						eventID = Integer.parseInt(parser.getValueOf("STAGE_ID"));
					}
				}
			} else {
				/*HashMap<Integer, Integer> eventMap = DSCOPUtils.getInstance().getEventDetector(processID, sourcingChannel, applicationVersion, applicationJourney, Integer.parseInt(stageId));
				eventID = (Integer) eventMap.keySet().toArray()[0];*/
				eventID = Integer.parseInt(stageId);
			}
			
			boolean noGo = false;
			String callName;
			XMLParser xp ;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "R_Event EXECUTING PARALLEL CALLS OF EVENT : "+ WI_NAME + " : " + eventID);
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			if(callArray != null) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "R_Event Call_Array: " + WI_NAME + " : " + callArray.toString());
				
				outputXML = APCallCreateXML.APSelect("SELECT CALL_NAME FROM (SELECT CALL_NAME FROM USR_0_DSCOP_CALL_OUT"
						+ " WHERE WI_NAME = '"+ WI_NAME +"' AND STAGE_ID = "+ eventID +" AND CALL_STATUS = 'N'"
						+ " ORDER BY CALL_DT) WHERE ROWNUM = 1");
				xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if (mainCode == 0) {
					int records = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
					if (records > 0) {
						callName = xp.getValueOf("CALL_NAME");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Name : "+ callName);
						CallEntity callEntity = getCallForRetry(eventID, callName);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Entity : "+ callEntity);
//						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"defaultAttributeMap : "+ defaultAttributeMap);
//						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call sessionId : "+ sessionId);
//						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Entity : "+ String.valueOf(eventID));
//						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Entity : "+ callEntity.getCallName());

						outputXML = CallHandler.getInstance().executeCallRetry(callEntity, defaultAttributeMap, sessionId, String.valueOf(eventID), WI_NAME, noGo);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Execute Call: outputxml: "+ outputXML);
						if(outputXML.equals("") && isLastCallExecuted(eventID, WI_NAME)){
							outputXML = "<returnCode>0</returnCode>";
						}
						xp = new XMLParser(outputXML);
						int returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
						if (returnCode == 0) {
							responseObj.setStatusCode("0");
							responseObj.setStatusMessage("Event Executed Successfully");
							//COLMP-9195
							APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS", "'Y'", "WI_NAME = N'"+WI_NAME+"' AND CALL_NAME = '"+callName+"'"
									+ " AND CALL_STATUS = 'N'", sessionId);
						}
						else {
							responseObj.setStatusCode("-1");
							responseObj.setStatusMessage("Event Execution Failed");
						}
					}
				}
			}
			
			responseObj.setWI_NAME(WI_NAME);
			responseObj.setStage(stageId);
			responseObj.setApplicationName(applicationName);
			responseObj.setLanguage(language);
			responseObj.setSYSREFNO(sysRefNo);
		} catch (Exception e) {
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "R_Event"+stageId, "R-EVENT100", e, sessionId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return responseObj;
	}
	
	private boolean isLastCallExecuted(int eventId, String wiName){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Inside isLastCallExecuted");
		try{
			String callName = "";
			String query = "Select Call_Name From USR_0_DSCOP_CALL_OUT WHERE WI_NAME = '"+wiName+"' AND CALL_STATUS = 'Y' ORDER BY CALL_DT DESC FETCH FIRST ROW ONLY ";
			String outputXml = APCallCreateXML.APSelect(query);
			XMLParser xp = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				int records = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if (records > 0) {
					callName = xp.getValueOf("CALL_NAME");
				}
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Inside isLastCallExecuted callName "+callName);
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Inside isLastCallExecuted callArray "+callArray.toString());
			for(CallEntity ce : callArray){
				if(ce.getDependencyCallID() != null) {
					ArrayList<Integer> dependencycallList = ce.getDependencyCallID();
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Inside isLastCallExecuted dependencycallList "+dependencycallList.toString());
					int dependencyCallID = dependencycallList.get(dependencycallList.size()-1);
					HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
					ArrayList<String> call = callCache.get(dependencyCallID);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Inside isLastCallExecuted call "+call.toString());
					if(call!=null && call.get(0).equalsIgnoreCase(callName)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return false;
	}
	
	private CallEntity getCallForRetry(int eventId, String callName){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"R Event Inside getCallForRetry");
		CallEntity callEntity = null;
		try {
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventId);
//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Entity List: "+ callArray);
			for(CallEntity ce : callArray){
				if(ce.getCallName().equals(callName)){
					return ce;
				} else {
					HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  ProcessEventCache.getReference().getCallDependencyMap().get(Integer.parseInt(ce.getStage()));
//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"dependencyCallID: "+ dependencyCallCache);
					callEntity = getDependencyCallForRetry(ce, callName, dependencyCallCache);			
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return callEntity;
	}
	
	private CallEntity getDependencyCallForRetry(CallEntity ce, String callName, HashMap<Integer, ArrayList<Integer>> dependencyCallCache) {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getDependencyCallForRetry ");
		CallEntity callEntity = null;
		try {
			if(ce.getDependencyCallID() != null) {
//				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Entity : "+ ce.getDependencyCallID());
				ArrayList<Integer> dependencycallList = ce.getDependencyCallID();
//				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"dependencycallList: "+ dependencycallList);
				
				for (Integer dependencyCallID : dependencycallList) {
//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"dependencyCallID: "+ dependencyCallID);
					HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"callCache : "+ callCache);
					ArrayList<String> call = callCache.get(dependencyCallID);
//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"call : "+ call.get(0));
					
					CallEntity callEntityTemp = DSCOPUtils.getInstance().createCallEntity(ce.getStage(), 
							dependencyCallID, ce.getExecutionType(), call, 
							dependencyCallCache!=null? dependencyCallCache.get(dependencyCallID): null);
//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Call Entity Temp : "+ callEntityTemp);
					
					if (call.get(0).equals(callName)) {
						return callEntityTemp;							
					}
					else {
						callEntity = getDependencyCallForRetry(callEntityTemp, callName, dependencyCallCache);
//						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Dependency Call Entity : "+ callEntity);
					}
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return callEntity;
	}
}
