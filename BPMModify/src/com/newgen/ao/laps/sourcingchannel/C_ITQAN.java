package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_ITQAN implements SourcingChannelHandler{
	private int processDefId;
	private String WI_NAME;
	int stageId = 1;
	
	String status = "0";
	String statusDesc = "Calls Executed Successfully";
	int mainCode;
	HashMap<String, String> attribMap = new HashMap<String, String>();
	private Map<String,HashMap<Integer, String>> processInitMap;
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	ArrayList<String> processValues = new  ArrayList<String>();
	public LapsModifyResponse resp = new LapsModifyResponse();
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_ITQAN");
		try {
		attribMap.put("REQUESTOR_PROCESS_NAME", processName);
		attribMap.put("REQUESTOR_WI_NAME", wiNumber);
		String output = APCallCreateXML.APSelect("select PROCESSDEFID from wfinstrumenttable where PROCESSINSTANCEID =  N'" + wiNumber + "'");
		XMLParser xmlparser = new XMLParser(output);
		int mainCode1 = Integer.parseInt(xmlparser.getValueOf("MainCode"));
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
		if(mainCode1 == 0){
			if(Integer.parseInt(xmlparser.getValueOf("TotalRetrieved")) > 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
				processDefId = Integer.parseInt(xmlparser.getValueOf("PROCESSDEFID"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processDefId: " + processDefId);			

			}
		}

		
		String outputXML;
		String EIDANum;
		String reqClssification;
		String custID;
		String COMPLETE_FLAG = "";
		// added by reyaz 26/10/2022
		String existingCust;
		String authSign;
		String passPort;
		String dob;
		String nationality;
		String custfullName;
		
			HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
					(processDefId, sourcingChannel, "1", sourcingChannel,stageId);
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
			XMLParser xp; 
			if(callArray != null) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
				boolean nogoCall = false;
				outputXML = APCallCreateXML.APSelect("SELECT  EIDANO, REQUESTCLASSIFICATION,CUST_ID,EXISTING_CUST,"
						+ "AUTH_SIGN,PASSPORT, DOB, NATIONALITY,CUSTFULLNAME FROM USR_0_WBG_AO_AUS  WHERE WI_NAME = N'" + wiNumber + "'");
				 xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
				if(mainCode == 0){
				//	boolean custIdNotFound = false;
					if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
						int start = xp.getStartIndex("Records", 0, 0);
						int deadEnd = xp.getEndIndex("Records", start, 0);
						int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"noOfFields :"+noOfFields);

						int end = 0;
						boolean custIdNotFound = false;
						for (int i = 0; i < noOfFields; ++i) {
							start = xp.getStartIndex("Record", end, 0);
							end = xp.getEndIndex("Record", start, 0);
							EIDANum = xp.getValueOf("EIDANO", start, end);
							reqClssification = xp.getValueOf("REQUESTCLASSIFICATION", start, end);
							custID = xp.getValueOf("CUST_ID", start, end);
							// added by reyaz 26/10/2022
							existingCust = xp.getValueOf("EXISTING_CUST", start, end);
							authSign = xp.getValueOf("AUTH_SIGN", start, end);
							passPort = xp.getValueOf("PASSPORT", start, end);
							dob = xp.getValueOf("DOB", start, end);
							nationality = xp.getValueOf("NATIONALITY", start, end);
							custfullName = xp.getValueOf("CUSTFULLNAME", start, end);
							
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"reqClssification"+ reqClssification);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"custID"+ custID);
							if("CARD".equalsIgnoreCase(reqClssification)||("AUS".equalsIgnoreCase(reqClssification) && "N".equalsIgnoreCase(existingCust))){
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value EIDANum"+  EIDANum);
								attribMap.put("EIDA_NO", EIDANum);
								// added by reyaz 26/10/2022
								attribMap.put("AUTH_SIGN", authSign);
								attribMap.put("PASSPORT", passPort);
								attribMap.put("DOB", dob);
								attribMap.put("NATIONALITY", nationality);
								attribMap.put("CUSTFULLNAME", custfullName);
								int j = i-1;
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value of j"+ j);

								if(noOfFields == j){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside i-1"+ i);
									COMPLETE_FLAG = "Y";
									attribMap.put("COMPLETE_FLAG", COMPLETE_FLAG);
								}
								custIdNotFound = true;
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EIDANum"+ EIDANum);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attribMap"+ attribMap);
								for (CallEntity callEntity : callArray) {
									if(callEntity.isMandatory()){
										XMLParser xp1; 
										String soutputXML = CallHandler.getInstance().executeCall(callEntity, attribMap, sessionId,
												String.valueOf(eventID),wiNumber, false);
										xp1 = new XMLParser(soutputXML);
										if("0".equalsIgnoreCase(status)){
											status = xp1.getValueOf("returnCode");
										}
										if(!"0".equals(status)){
											statusDesc = xp1.getValueOf("errorDescription");
										}
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
												+ outputXML);

									}
								}
								//Added by reyaz 
								APCallCreateXML.APSelect("select prev_ws,curr_ws from "
									 		+ "ext_wbg_ao where WI_NAME =  N'" + wiNumber + "'");
							}
						}
						if(!custIdNotFound){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"complete from c_itqan :"+noOfFields);
							ProdCreateXML.WMCompleteWorkItem(sessionId,wiNumber,1);
						}
						//Added by reyaz
						 APCallCreateXML.APSelect("select prev_ws,curr_ws from "
							 		+ "ext_wbg_ao where WI_NAME =  N'" + wiNumber + "'");
					}
					/*if(!custIdNotFound){
						ProdCreateXML.WMCompleteWorkItem(sessionId,wiNumber,1);
					}*/
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"C_ITQAN: status= "+status+"statusDesc= "
					+statusDesc);
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in wfupload session ID:123 ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			resp.setStatusCode("-1");
		}
		return resp;
	}


}
