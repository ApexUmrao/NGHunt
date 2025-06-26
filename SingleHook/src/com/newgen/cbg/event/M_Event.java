package com.newgen.cbg.event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.newgen.cbg.cache.ProcessEventCache;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.implementation.SingleUserConnection;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class  M_Event implements IEventHandler{

	private String stageId;
	private String WI_NAME;
	private String sysRefNo;
	private String applicationName;
	private String language;
	private String sourcingCenter;
	private String sourcingChannel;
	private String requestedDateTime;
	private String deviceID;
	private String deviceIP;
	private String OSType;
	private String OSVersion;
	private String appVersion;
	private String deviceModal;
	private String applicationJourney;
	private String sessionId;
	private String PrimaryCid;
	private String isEIDAAvailable;
	private String isPassportDedupe;
	private HashMap<String, String> defaultAttributeMap;
	boolean moveToExit = false;
	boolean moveToCompliance = false;
	boolean cExit = false;
	private String journeyType;
	private String applicationVersion;
	private String	primaryCid;
	private String	eidaNumber;
	private String passportNo ;
	private String nationality ;
	private String dormantCustomer;
	private	String passportNumber;
	private	String lead;
	private	String nameMissmatch;
	private	String fullName;

	@Override
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent){

		WI_NAME = paramCoreEvent.getWI_NAME();
		stageId = paramCoreEvent.getStageId();
		sysRefNo = paramCoreEvent.getSysRefNo();
		applicationName = paramCoreEvent.getApplicationName();
		language = paramCoreEvent.getLanguage();
		sourcingCenter = paramCoreEvent.getSourcingCenter();
		sourcingChannel = paramCoreEvent.getSourcingChannel();
		requestedDateTime = paramCoreEvent.getRequestedDateTime();
		deviceID = paramCoreEvent.getDeviceID();
		deviceIP = paramCoreEvent.getDeviceIP();
		OSType = paramCoreEvent.getOSType();
		OSVersion = paramCoreEvent.getOSVersion();
		deviceModal = paramCoreEvent.getDeviceModal();
		applicationJourney = paramCoreEvent.getApplicationJourney();
		applicationVersion = paramCoreEvent.getApplicationVersion();
		journeyType = paramCoreEvent.getApplicationJourney();
		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();

		try {
			boolean skipParCalls = false;
			boolean stageValid = false;
			int consentCount = 0;
			int currStage = 1;
			String outputXML = "";
			String ecbFlag = "";
			int handoffStage = 0;
			int workitemID =1;
			if(stageId.equals("1")) {
				stageValid=true;
			}

			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside M_Events");
			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName,DSCOPConfigurations.getInstance().UserName,DSCOPConfigurations.getInstance().Password);
			defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();

			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
			PrimaryCid = attributeMap.get("PRIMARY_CID");
			nameMissmatch = attributeMap.get("NAME_MISSMATCH");
			fullName= attributeMap.get("SUPP_CARDHOLDER_FULL_NAME").toUpperCase();
			int processID = Integer.parseInt(defaultAttributeMap.get("DSCOP_PROCESS_ID"));
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events processID:"+processID+","+sourcingChannel+","+applicationVersion+","+journeyType+","+stageId+","+applicationJourney+"");
			HashMap<Integer, Integer> eventMap =  DSCOPUtils.getInstance().getEventDetector(processID, sourcingChannel, applicationVersion, journeyType, Integer.parseInt(stageId));
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "eventID : " + eventID);
			String nextStage = String.valueOf(eventMap.get(eventID));
			ArrayList<String> eventDetails = DSCOPUtils.getInstance().getEventDetails(eventID);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "eventDetails  "+eventDetails);
			attributeMap.put("REQUESTED_DATETIME",requestedDateTime);
			attributeMap.put("SOURCING_CHANNEL",sourcingChannel);
			attributeMap.put("SOURCING_CENTER",sourcingCenter);
			attributeMap.put("PREFERRED_LANGUAGE", language);
			attributeMap.put("DEVICE_ID", deviceID);
			attributeMap.put("DEVICE_IP", deviceIP);
			attributeMap.put("NEXT_STAGE_ID", nextStage);
			attributeMap.put("STAGE_ID", stageId);
			attributeMap.put("APP_VERSION", applicationVersion);
			attributeMap.put("PASSPORT_LOCAL_DEDUPE", "");
			attributeMap.put("EIDA_LOCAL_DEDUPE", "");
			attributeMap.put("EIDA_DEDUPE", "");
			attributeMap.put("SUPP_CARDHOLDER_FULL_NAME", fullName);
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.DATE, 1);
			attributeMap.put("EXPIRY_DATE",new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c.getTime()));
			String query="UPDATE WFINSTRUMENTTABLE SET  VALIDTILL= SYSDATE+1 WHERE processinstanceid = '"+WI_NAME+"'";
			DSCOPUtils.getInstance().updateDataInDB(query);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "stageId  "+stageId);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "SUPP_MEvent", "M-EVENT"+stageId, "Handoff Received", sessionId);
			// Insert into Device table
			String valList = "'"+ WI_NAME +"','"+ OSType +"','"+ OSVersion +"','"+ deviceID +"','"+ applicationVersion +"','"+ deviceModal +"','"+ language +"','"+ deviceIP +"','"+ sourcingChannel +"',SYSTIMESTAMP";
			APCallCreateXML.APInsert("USR_0_DSCOP_DEVICE_INFO", "WI_NAME, OS_TYPE, OS_VERSION, DEVICE_ID, APP_VERSION, DEVICE_MODEL, LANG_SELECTION, IP_ADDRESS, SOURCE_SYSTEM, TIME_STAMP", valList, sessionId);


			if(stageValid){
				boolean noGo = false;
				boolean autoRepair = false;
				boolean completeFlag = false;
				attributeMap.put("HANDOFF_STAGEID", stageId);
				eidaNumber = attributeMap.get("EIDA_NUMBER");
				passportNumber = attributeMap.get("PASSPORT_NO");
				if (eidaNumber != null) {
					isEIDAAvailable="Y";
				}else {
					isEIDAAvailable="N";
				}
				if (passportNumber != null) {
					isPassportDedupe="Y";
				}else {
					isPassportDedupe="N";
				}
				if(nameMissmatch.equalsIgnoreCase("")|| nameMissmatch == null) {
					nameMissmatch="N";
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Before  WFSetAttributesNew:");
				outputXML = ProdCreateXML.WFSetAttributesNew(sessionId, WI_NAME, workitemID, attributeMap, String.valueOf(processID));
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " outputXML  WFSetAttributesNew:"+outputXML);
				ProdCreateXML.WMUnlockWorkItem(sessionId, WI_NAME, workitemID);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " mainCode  WFSetAttributesNew:"+mainCode);
				APCallCreateXML.APUpdate("EXT_DSCOP", "NAME_MISSMATCH", "'"+nameMissmatch+"'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);

				if(mainCode == 0){
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_MEvent", "M-EVENT"+stageId, "Workitem Modified Successfully", sessionId);
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Workitem Modified Successfully");
					String statusCode = "0";
					responseObj.setStatusCode(statusCode);
					
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "moveToExit flag:  "+moveToExit);
					if(!moveToExit) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside moveToExit flag:  "+moveToExit);

						//EXECUTE PARALLEL CALLS
						if(!skipParCalls){
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "EXECUTING PARALLEL CALLS OF EVENT : "+ WI_NAME + ":" + eventID);
							List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
							if(callArray != null) {
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Call_Array: " + WI_NAME + ":" + callArray.toString());
								for (CallEntity callEntity : callArray) {
									if(callEntity.isMandatory()){
										outputXML = CallHandler.getInstance().executeCall(callEntity, defaultAttributeMap, sessionId, String.valueOf(eventID), WI_NAME, noGo);
										DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Execute Call: outputxml: "+ outputXML);
										if(!"".equals(outputXML)){
											xp = new XMLParser(outputXML);
											mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
											if(mainCode != 0){
												noGo = true;
												APCallCreateXML.APUpdate("EXT_DSCOP", "INQ_FLAG,INQ_REJECT_STATUS,INQ_REJECT_MSG", "'Y',"
												+"'"+statusCode+"','Lead Creation Failure'"," WI_NAME = N'"+ WI_NAME +"'", sessionId);
												responseObj.setStatusCode("-1");
												responseObj.setStatusMessage("Workitem Modification Failed");
												completeFlag = true;
											} else {
												responseObj.setStatusCode("0");
												responseObj.setStatusMessage("Workitem Modified Successfully");
												APCallCreateXML.APInsert("USR_0_DSCOP_EXPIRY_EVENTS", "WI_NAME,STAGE_ID,ENTRY_DATETIME,VALID_TILL,EXPIRY_SEQ,LOCKED_STATUS", 
														"'"+ WI_NAME +"' , '2' , SYSDATE , (SYSDATE+2/(24*60)) , '4' , 'N' ", sessionId);

											 }
										   }
										}
									}
								}
							} else {
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"No Parallel calls at Stage: "+ stageId);
							}
						//	CHECK ANY CALL  FAIL OR NOT 
//							if(eventDetails!=null){
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside moveToExit flag eventDetails: "+ eventDetails);
//								completeFlag = true;
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside moveToExit flag completeFlag:  "+completeFlag);
//								if("Y".equals(eventDetails.get(0))){
//									String	outputCallout = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM USR_0_DSCOP_CALL_OUT "
//											+ " WHERE WI_NAME = N'"+WI_NAME+"' AND CALL_STATUS IN ('N') AND CALL_NAME !='SuppSanctionScreeningDigital'");
//									XMLParser xp1 = new XMLParser(outputCallout);
//									mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
//									if(mainCode == 0){
//										if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
//											if(Integer.parseInt(xp1.getValueOf("COUNT")) > 0 && (Integer.parseInt(stageId) == 1)){
//												DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "prodCode: NOGO2");
//												autoRepair = true;
//												noGo = true; 
//											}
//										}
//									}
//								}
//							}

							//ENABLE WI MOVEMENT TO NEXT STAGE
//							if(!noGo){
//								if(completeFlag) {
//									DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "ENABLE WI MOVEMENT TO NEXT STAGE");
//									DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_MEvent"+stageId, "M-EVENT003", "WI MOVEMENT TO NEXT STAGE", sessionId);
//									outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_AUTO_REPAIR", "'N'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
//									xp = new XMLParser(outputXML);
//									mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
//									if(mainCode == 0) {
//										DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"ROUTE_TO_NEXT_STAGE : Y : "+ mainCode);
//									} else {
//										DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"ROUTE_TO_NEXT_STAGE : Y : FAILED"+ mainCode);
//									}
//								}
//							}
//
//							else if(autoRepair){
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WI MOVEMENT TO AUTO REPAIR");
//								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_MEvent"+stageId, "M-EVENT004", "WI MOVEMENT TO AUTO REPAIR", sessionId);
//								outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_AUTO_REPAIR", " Y ", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
//								responseObj.setStatusCode("0");
//								responseObj.setStatusMessage("Workitem Modified Successfully");
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AUTO REPAIR FOUND!!");
//							}
//							else if(noGo){
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "WI MOVEMENT TO EXIT");
//								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_MEvent"+stageId, "M-EVENT005", "WI MOVEMENT TO EXIT", sessionId);
////								outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_REPAIR", " Y ", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
//								outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "INQ_FLAG,INQ_REJECT_STATUS,INQ_REJECT_MSG", "'Y',"
//										+ " '"+statusCode+"','Lead Creation Failure'"," WI_NAME = N'"+ WI_NAME +"'", sessionId);
//								responseObj.setStatusCode("-1");
//								responseObj.setStatusMessage("Workitem Modification Failed Due To Lead Creation Failure");
//								completeFlag = true;
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside noGo flag completeFlag:  "+completeFlag);
//								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "NOGO FOUND!!");
//							}
						}
						else 
						{
							responseObj.setStatusCode("1");
							responseObj.setStatusMessage("NO-GO");
						}

						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, WI_NAME+":->"+noGo+":"+autoRepair+":"+completeFlag);

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
				}else {
					ProdCreateXML.WMUnlockWorkItem(sessionId, WI_NAME, 1);
					responseObj.setStatusCode("1");
					responseObj.setStatusMessage("Workitem Modification Failed Due To SetAttribute");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "SUPP_MEvent", "M-EVENT"+stageId, "Workitem Modification Failed", sessionId);
				}				
			}
			else if(currStage != 1 ){
				responseObj.setStatusMessage("Invalid Stage ID");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, ""+stageId, "M-EVENT006", "Invalid Stage ID", sessionId);
			}
			ApplicationAttributes[] attributes = DSCOPUtils.getInstance().getApplicationAttributes(APCallCreateXML.APSelect("SELECT LEAD_REF_NO AS LEADNUMBER FROM EXT_DSCOP WHERE  WI_NAME = '"+ WI_NAME +"'"));
			responseObj.setApplicationAttributes(attributes);
			responseObj.setWI_NAME(WI_NAME);
			responseObj.setStage(currStage+"");
			responseObj.setApplicationName(applicationName);
			responseObj.setLanguage(language);
			responseObj.setSYSREFNO(sysRefNo);
			responseObj.setNextStage(nextStage);
			responseObj.setApplicationVersion(applicationVersion);
			responseObj.setApplicationJourney(applicationJourney);
			
		}catch(Exception e) {
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "SUPP_MEvent"+stageId, "M-EVENT100", e, sessionId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return responseObj;
	}  
}
