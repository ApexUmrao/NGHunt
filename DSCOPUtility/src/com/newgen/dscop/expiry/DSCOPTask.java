package com.newgen.dscop.expiry;

import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.newgen.dscop.cache.ProcessEventCache;
import com.newgen.dscop.cache.StageCallCache;
import com.newgen.dscopretryexp.core.CBGInterface;

public class DSCOPTask extends Expiry implements Runnable {

	@Override
	public void run() {
		String wiName = "";
		String stageId = "";
		String currStageId = "";
		int expSeq = 1;
		String validTill = "";
		String sessionId = "";
		int maxTrialCount = 4;
		boolean moveToHistoryFlag = false;
		String finalStatus = "";
		String currStageName = "";
		try {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "DSCOPTask started");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			while (true) {
				CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside Loop");
				try {
					CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside Try Block");
					int mainCode1 = -1;
					String outputXML = APCallCreateXML.APSelect(
							"SELECT  WI_NAME, STAGE_ID, EXPIRY_SEQ, TO_CHAR(VALID_TILL,'DD-MM-YYYY HH24:MI:SS') AS VALID_TILL"
									+ " FROM USR_0_DSCOP_EXPIRY_EVENTS WHERE (VALID_TILL < SYSDATE) AND ROWNUM <= 1");
					XMLParser xp = new XMLParser(outputXML);
					CBGLogMe.logMe(1,
							"USR_0_DSCOP_EXPIRY_EVENTS data TotalRetrieved :" + xp.getValueOf("TotalRetrieved"));
					int noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
					if (noOfFields == 1) {
						wiName = xp.getValueOf("WI_NAME");
						stageId = xp.getValueOf("STAGE_ID");
						expSeq = Integer.parseInt(xp.getValueOf("EXPIRY_SEQ"));
						validTill = xp.getValueOf("VALID_TILL");
						outputXML = APCallCreateXML.APSelect(
								"SELECT STAGE_ID, NEXT_STAGE, SOURCING_CHANNEL, APP_VERSION, STAGE_NAME FROM EXT_DSCOP WHERE WI_NAME = N'"
										+ wiName + "'");
						XMLParser xp1 = new XMLParser(outputXML);
						noOfFields = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
						if (noOfFields == 1) {
							boolean callCompleteWI = true;
							sourcingChannel = xp1.getValueOf("SOURCING_CHANNEL");
							applicationVersion = xp1.getValueOf("APP_VERSION");
							sessionId = instance.getSession(cabinetName, username, password);
							currStageId = xp1.getValueOf("STAGE_ID");
							currStageName =  xp1.getValueOf("STAGE_NAME");
							String input = "";
							
							CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
									"Executing Expiry for WI Name :" + wiName + "and stage :" + stageId + " and stageName : " + currStageName) ;
							outputXML = APCallCreateXML.APSelect("SELECT CALL_NAME from USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'" + wiName
											+ "' AND CALL_STATUS = N'N' AND STAGE_ID NOT IN (-2) ORDER BY CALL_DT");
							xp = new XMLParser(outputXML);
							CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
									"USR_0_DSCOP_EXPIRY_EVENTS STAGEID -1: data TotalRetrieved :"
											+ xp.getValueOf("TotalRetrieved"));
							String callName = "";
							noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
							if (noOfFields > 0) {
								callName = xp.getFirstValueOf("CALL_NAME");
								String callID = StageCallCache.getReference().getCachedCallNameMap().get(callName).get(0);
								CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "CALL_NAME:" + callName + " CALL_ID:" + callID);
								HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> getCallDependencyMap = ProcessEventCache
										.getReference().getCallDependencyMap();
								String outputXML1 = APCallCreateXML.APSelect(
										"SELECT DISTINCT STAGE_ID AS STAGE_ID   from USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'"
												+ wiName + "' AND CALL_STATUS = N'N' AND STAGE_ID NOT IN (-1) ");
								XMLParser xp2 = new XMLParser(outputXML1);
								noOfFields = Integer.parseInt(xp2.getValueOf("TotalRetrieved"));
								if (noOfFields > 0) {
									int start1 = xp2.getStartIndex("Records", 0, 0);
									int deadEnd1 = xp2.getEndIndex("Records", start1, 0);
									int end1 = 0;
									noOfFields = xp2.getNoOfFields("Record", start1, deadEnd1);
									for (int j = 0; j < noOfFields; ++j) {
										start1 = xp2.getStartIndex("Record", end1, 0);
										end1 = xp2.getEndIndex("Record", start1, 0);
										int eventID = Integer.parseInt(xp2.getValueOf("STAGE_ID", start1, end1));
										String callNameXML = APCallCreateXML.APSelect(
												"SELECT CALL_NAME from USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'" + wiName
														+ "' AND CALL_STATUS = N'N' AND STAGE_ID = '" + eventID
														+ "' AND ROWNUM = 1 ORDER BY CALL_DT");
										XMLParser callNameXp = new XMLParser(callNameXML);
										int noOfFields1 = Integer.parseInt(callNameXp.getValueOf("TotalRetrieved"));
										if (noOfFields1 > 0) {
											callName = callNameXp.getValueOf("CALL_NAME");
										}
										callID = StageCallCache.getReference().getCachedCallNameMap().get(callName)
												.get(0);
										CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "WI_NAME : " + wiName + ", eventID : "
												+ eventID + ", getCallDependencyMap:" + getCallDependencyMap);
										if (getCallDependencyMap.containsKey(eventID)) {
											CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
													"WI_NAME : " + wiName + "Get EventID >>>>>>>:"
															+ getCallDependencyMap.get(eventID) + " CallID: " + callID);
											CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "WI_NAME : " + wiName
													+ "Dependency CALL >>>>>>:"
													+ getCallDependencyMap.get(eventID).get(Integer.parseInt(callID)));
											ArrayList<Integer> dependentCallID = getCallDependencyMap.get(eventID)
													.get(Integer.parseInt(callID));
											if (dependentCallID != null && !dependentCallID.isEmpty()) {
												HashMap<Integer, ArrayList<Integer>> dependencyCallCache = getCallDependencyMap
														.get(eventID);
												if (!dependencyCallCache.isEmpty()) {
													if (stageId.equalsIgnoreCase("2") && currStageName.equalsIgnoreCase("SYSTEM_WS")) {
														input =	createInputXML(wiName, sessionId, eventID + "", "",	sourcingChannel, applicationVersion);
													} else {
														input = createREvenInputXML(wiName, sessionId, eventID + "", "",sourcingChannel, applicationVersion);												  
													}
													String rEventXML = ExecuteXML.executeAPWebService(input, socketIP,socketPort);
													XMLParser rxp = new XMLParser(rEventXML);
													String status = rxp.getValueOf("statusCode");
													CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
															" status >>>>>>>:" + status);
													if (!"0".equalsIgnoreCase(status) && expSeq < maxTrialCount) {
														outputXML1 = APCallCreateXML.APProcedure(sessionId,
																"DSCOP_EXPIRY_EVENT",
																"'" + wiName + "','"+stageId+"','" + String.valueOf(expSeq + 1)
																+ "','" + RepairInterval + "'",
																4);

														moveToHistoryFlag = false;
														if ((expSeq + 1) == maxTrialCount) {
															finalStatus = "F";
															moveToHistoryFlag = true;
														}
													} else if ("0".equalsIgnoreCase(status)) {
														finalStatus = "Y";
														moveToHistoryFlag = true;
													} else {
														finalStatus = "F";
														moveToHistoryFlag = true;
													}

													if (moveToHistoryFlag) {
														outputXML1 = APCallCreateXML.APProcedure(sessionId,
																"DSCOP_EXPIRY_EVENT_MOVE_HISTORY",
																"'" + wiName + "','"+stageId+"','" + finalStatus + "'", 3);
													}
												}
											} else {

												String callStatus = retryCall(callName, sessionId, "-1", wiName);
												if ("".equalsIgnoreCase(callStatus)
														|| "N".equalsIgnoreCase(callStatus)) {
													break;
												}

											}
										} else {
											
												int start = xp.getStartIndex("Records", 0, 0);
												int deadEnd = xp.getEndIndex("Records", start, 0);
												int end = 0;
												noOfFields = xp.getNoOfFields("Record", start, deadEnd);
												for (int i = 0; i < noOfFields; ++i) {
													start = xp.getStartIndex("Record", end, 0);
													end = xp.getEndIndex("Record", start, 0);
													callName = xp.getValueOf("CALL_NAME", start, end);

													String callStatus = retryCall(callName, sessionId, "-1", wiName);
													if ("".equalsIgnoreCase(callStatus)
															|| "N".equalsIgnoreCase(callStatus)) {
														break;
													}
												}											
										}

									}
									
								if (!stageId.equalsIgnoreCase("2")){
									Thread.sleep(13000);
									
									outputXML = APCallCreateXML.APSelect(
											"SELECT CALL_NAME from USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'" + wiName
													+ "' AND CALL_STATUS = N'N' AND STAGE_ID NOT IN (-2,-1) ORDER BY CALL_DT");
									xp = new XMLParser(outputXML);
									noOfFields = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
									CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
											"USR_0_DSCOP_EXPIRY_EVENTS STAGEID -1: data TotalRetrieved after retry :"
													+ noOfFields); 
									if (noOfFields == 0) // move to next WS
									{
										CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
												"USR_0_CBG_DSCOP_EVENTS STAGEID -1: HOURS MOVE TO NEXT WS");
										outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_REPAIR",
												"'N'", " WI_NAME = N'" + wiName + "'", sessionId);
										input = ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
									} else if (moveToHistoryFlag){
										CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"MOVE TO HISTORY FLAG TRUE: ROUTE_TO_REPAIR:Y");
										outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_REPAIR",
												"'Y'", " WI_NAME = N'" + wiName + "'", sessionId);
										input = ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
									}else {
										CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"MOVE TO HISTORY FLAG TRUE: ROUTE_TO_REPAIR:N");
										outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", "ROUTE_TO_REPAIR",
												"'N'", " WI_NAME = N'" + wiName + "'", sessionId);
									}
								   }
								   
								} else {
									CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
											"USR_0_DSCOP_EXPIRY_EVENTS STAGEID -1: HOURS MOVE TO NEXT WS 2");
									outputXML = APCallCreateXML.APUpdate("EXT_DSCOP", " ROUTE_TO_REPAIR",
											"'N'", " WI_NAME = N'" + wiName + "'", sessionId);
									input = ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
									CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, " Shivanshu Legend" + input);
								}
							}else {
								APCallCreateXML.APProcedure(sessionId,"DSCOP_EXPIRY_EVENT_MOVE_HISTORY","'" + wiName + "','"+stageId+"','No call'", 0);
							}

						}

						setCustomServiceStatus(CUSTOM_SERVICE_PROCESSING_STATUS, "Processing WorkItems",
								Integer.valueOf(1));
					} else {
						CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Going for " + this.sleepTime + " (ms) sleep... :");
						setCustomServiceStatus(CUSTOM_SERVICE_NO_WORKITEM_STATUS, "No more Workitems available", 0);
						Thread.sleep(sleepTime);
					}
				} catch (NGException ex) {
					CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"Inside NGException :::"+ex);
					Thread.sleep(5 * sleepTime);
					restartUtility();// Feature/UtilityUp|REYAZ|19-12-2024
				} catch (Exception c) {
					CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside Exception");
					CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR, c);
					CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Moving record into history...");
					try {
						sessionId = instance.getSession(cabinetName, username, password);
						APCallCreateXML.APUpdate(
								"USR_0_DSCOP_EXPIRY_EVENTS", "LOCKED_STATUS", "'E'", " WI_NAME = N'" + wiName
										+ "' AND STAGE_ID = '" + stageId + "' AND LOCKED_BY ='" + username + "'",
								sessionId);
						APCallCreateXML.APProcedure(sessionId, "DSCOP_EXPIRY_EVENT_MOVE_HISTORY",
								"'" + wiName + "','-1','E'", 3);
					} catch (Exception e) {
						CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR, e);
					}
				}
			}
		}
		catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR, e);
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Going for 5 minute sleep... :");
			setCustomServiceStatus("-25033", "Sleeping", 0);
			try {
				Thread.sleep(3 * sleepTime);
				run();
			} catch (InterruptedException e1) {
				CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR, e1);
			}
		}
	}

	private String retryCall(String sCallName, String sSessionId, String stageId, String sWIName) throws Exception {
		String callStatus = "";
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,
				"Inside retryCall to execute call : com.newgen.cbgretryexp.calls." + sCallName);

		Class<?> cl = Class.forName("com.newgen.dscopretryexp.calls." + sCallName);
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside retryCall to execute call : Check1");
		Constructor<?> cons = cl.getConstructor(Map.class, String.class, String.class, String.class, Boolean.class);
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside retryCall to execute call : Check2");
		CBGInterface caller = CBGInterface.class
				.cast(cons.newInstance(defaultMap, sSessionId, stageId, sWIName, false));
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside retryCall to execute call : Check3");
		callStatus = executeCall(caller, sCallName, sWIName, sSessionId);
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside retryCall to execute call : callStatus" + callStatus);
		return callStatus;
	}

	private String executeCall(CBGInterface callObj, String sCallName, String winame, String sessionId) {
		String callStatus = "";
		try {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, sCallName + " started>>>>>>>>>>>>>>: ");
			String sOutput = callObj.call();
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, sCallName + " sOutput >>>>>>>>>>>>>>: " + sOutput);
			String outputXML = APCallCreateXML
					.APSelect("SELECT CALL_STATUS from USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'" + winame
							+ "' AND CALL_NAME = N'" + sCallName + "' and STAGE_ID = -1 ORDER BY CALL_DT DESC");
			XMLParser xp = new XMLParser(outputXML);
			callStatus = xp.getValueOf("CALL_STATUS");
			sOutput = APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS", "'" + callStatus + "'",
					" WI_NAME = N'" + winame + "' AND CALL_NAME = N'" + sCallName + "'", sessionId);
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, " apupdate output " + sOutput);
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, sCallName + " finished  >>>>>>>>>>>>>>: status: " + callStatus);
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, " Exception in executeCall " + e);
		}
		return callStatus;
	}

	protected String createREvenInputXML(String wiName, String sessionId, String stageId, String extraValues,
			String sourcingChannel, String applicationVersion) {
		StringBuilder inputXML = new StringBuilder();
		try {
			inputXML.append("<?xml version=\"1.0\"?>").append("\n").append("<APWebService_Input>").append("\n")
					.append("<Option>WebService</Option>").append("\n")
					.append("<EngineName>" + cabinetName + "</EngineName>").append("\n")
					.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
					.append("<Calltype>DSCOP</Calltype>").append("\n")
					.append("<DSCOPCallType>SINGLE_HOOK</DSCOPCallType>").append("\n")
					.append("<REF_NO>" + generateSysRefNumber() + "</REF_NO>").append("<senderID>DSCOP</senderID>")
					.append("<RequestDateTime>" + new Date().toString() + "</RequestDateTime>").append("<MODE>R</MODE>")
					.append("<WI_NAME>" + wiName + "</WI_NAME>").append("<stage>" + stageId + "</stage>")
					.append("<applicationAttributes>").append("<attributeDetails>").append("<attributes>")
					.append(extraValues).append("</attributes>").append("</attributeDetails>")
					.append("</applicationAttributes>")
					.append("<applicationName>" + sourcingChannel + "</applicationName>")
					.append("<SourcingChannel>" + sourcingChannel + "</SourcingChannel>")
					.append("<applicationVersion>" + applicationVersion + "</applicationVersion>")
					.append("<SourcingCenter>DSCOP</SourcingCenter>").append("<Language>Eng</Language>")
					.append("<LeadNumber>DSCOP</LeadNumber>").append("<DeviceID>DSCOP</DeviceID>")
					.append("<IP>DSCOP</IP>").append("</APWebService_Input>");
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "SINGLE_HOOK inputXML ===> " + inputXML.toString());
		} catch (Exception e) {
			CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR, e);
		}
		return inputXML.toString();
	}
	
	// Feature/UtilityUp|REYAZ|19-12-2024
	public  void restartUtility() {
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Inside :restartUtility ");
        ProcessBuilder processBuilder = new ProcessBuilder("bash", "./restart.sh");
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
        	CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "Script executed with exit code: " + exitCode);
        } catch (Exception e) {
        	CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_ERROR,"getting exception in restartUtility " +e);
        }
    }

}
