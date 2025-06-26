package com.newgen.cbg.event;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.newgen.cbg.cache.ProcessEventCache;
import com.newgen.cbg.cache.StageCallCache;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.implementation.SingleUserConnection;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class X_Event implements IEventHandler{

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
	int workitemID =1;
	
	private HashMap<String, String> defaultAttributeMap;
	boolean moveToExit = false;


	@Override
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside DSCOP_XEvent");
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
			if (stageId.equalsIgnoreCase("2")) {
				stageId = "28001";
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "X_Event INSIDE CALLS OF X EVENT : "+ WI_NAME + " : " + stageId);

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
			boolean autoRepair = false;
			boolean completeFlag = false;
			
			String callName;
			XMLParser xp ;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "X_Event EXECUTING PARALLEL CALLS OF EVENT : "+ WI_NAME + " : " + eventID);
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			if(callArray != null) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "X_Event Call_Array: " + WI_NAME + " : " + callArray.toString());
				
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
						outputXML = CallHandler.getInstance().executeCall(callEntity, defaultAttributeMap, sessionId, String.valueOf(eventID), WI_NAME, noGo);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"X Event Execute Call: outputxml: "+ outputXML);
						if(!"".equals(outputXML)){
							xp = new XMLParser(outputXML);
							mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
							if(mainCode != 0){
								noGo = true;
								if (mainCode == 2) {
									responseObj.setStatusCode("0");
									responseObj.setStatusMessage(" X Event Dedupe Found WI Discarded");
								}else {
									responseObj.setStatusCode("1");
									responseObj.setStatusMessage(" X Event Execution Failure");
									//								autoRepair = true;
								}
							} else {
//								noGo = false;
								responseObj.setStatusCode("0");
								responseObj.setStatusMessage(" X Event Executed Successfully");
								APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS", "'Y'", "WI_NAME = N'"+WI_NAME+"' AND CALL_NAME = '"+callName+"'"
										+ " AND CALL_STATUS = 'N'", sessionId);
							}
						}
					}
				}
			
			} else {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"No Parallel calls at Stage: "+ stageId);
			}
			
			String	outputCallout = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM USR_0_DSCOP_CALL_OUT "
					+ " WHERE WI_NAME = N'"+WI_NAME+"' AND CALL_STATUS IN ('N') AND CALL_NAME !='SuppSanctionScreeningDigital'");
			XMLParser xp1 = new XMLParser(outputCallout);
			int mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode == 0){
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					if(Integer.parseInt(xp1.getValueOf("COUNT")) > 0 && (Integer.parseInt(stageId) == 28001)){
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "prodCode: NOGO2");
						autoRepair = true;
						noGo = true; 
					}
				}
			}
			
			//ENABLE WI MOVEMENT TO NEXT STAGE
			if(!noGo){
				completeFlag = true;
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "ENABLE WI MOVEMENT TO NEXT STAGE");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_XEvent"+stageId, "X-EVENT003", "WI MOVEMENT TO NEXT STAGE", sessionId);
				outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_AUTO_REPAIR", "'N'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				xp = new XMLParser(outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0) {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"ROUTE_TO_NEXT_STAGE : Y : "+ mainCode);
				} else {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"ROUTE_TO_NEXT_STAGE : Y : FAILED"+ mainCode);
				}

			}else if(autoRepair){
				completeFlag = true;
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WI MOVEMENT TO AUTO REPAIR");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_XEvent"+stageId, "X-EVENT004", "WI MOVEMENT TO AUTO REPAIR", sessionId);
				outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_AUTO_REPAIR", " Y ", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				responseObj.setStatusCode("0");
				responseObj.setStatusMessage("Workitem Modified Successfully");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AUTO REPAIR FOUND!!");
			}
			
			//MOVE WORKITEM TO NEXT STAGE
			if(completeFlag){
				outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, workitemID);
				xp = new XMLParser(outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode != 0){
					responseObj.setStatusCode("-1");
					responseObj.setStatusMessage("Workitem Movement Failed After WI Modification");
				}
			}
			
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		responseObj.setWI_NAME(WI_NAME);
		responseObj.setStage(stageId);
		responseObj.setApplicationName(applicationName);
		responseObj.setLanguage(language);
		responseObj.setSYSREFNO(sysRefNo);
		return responseObj;
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

