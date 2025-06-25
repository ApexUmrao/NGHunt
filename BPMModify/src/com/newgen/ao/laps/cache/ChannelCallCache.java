package com.newgen.ao.laps.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ChannelCallCache {

	private HashMap<String, ArrayList<CallEntity>> channelMap = new HashMap<String, ArrayList<CallEntity>>();
	private Map<String,Map<String,HashMap<String, String>>> protradeMappingMap = new HashMap<String,Map<String,HashMap<String, String>>>();
	private HashMap<String, ProtradeComplexMapping> protradeComplexMap = new HashMap<String, ProtradeComplexMapping>(); 
	private HashMap<String, String> protradeDateMap = new HashMap<String, String>();
	private Map<String,HashMap<Integer, String>> processInitMap = new HashMap<String,HashMap<Integer, String>>();
	private HashMap<String, ArrayList<SanctionScreeningMapping>> sanctionScreeningMasterMap = new HashMap<String, ArrayList<SanctionScreeningMapping>>(); 
	private Map<String, ArrayList<String>> tfoBRMSMap = new HashMap<String, ArrayList<String>>();
	private static ChannelCallCache instance = new ChannelCallCache();
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	private Map<String,String> requestTypeMap = new HashMap<String,String>();
	private Map<String, String> requestByMap = new HashMap<String,String>();
	private Map<String,String> sourceChannelMap  = new HashMap<String,String>();
	private HashMap<String,ArrayList<String>> screeningChannelMap = new HashMap<String, ArrayList<String>>();   // Shivanshu FSK Change
	private Map<String,Map<String,String>> protradeDefaultMap = new HashMap<String,Map<String,String>>();
	private Map<String,String> fetchMap  = new HashMap<String,String>();
	private Map<String,String> bpmServiceMap  = new HashMap<String,String>();
	private Map<String,String> protradeTargetMap  = new HashMap<String,String>();
	private HashMap<String, String> protradeCLOBMasterMap = new  HashMap<String, String>();
	private Map<String,Map<String,HashMap<String, String>>> childMappingMap = new HashMap<String,Map<String,HashMap<String, String>>>();


	
/*	private static HashMap<Integer,HashMap<String,String>> outerHashMap=new HashMap<Integer,HashMap<String,String>>();
	private static HashMap<String,String> innerHashMap = new HashMap<String,String>();
	private static HashMap<Integer,HashMap<String,String>> partyOuterHashMap=new HashMap<Integer,HashMap<String,String>>();
	private static HashMap<String,String> partyInnerHashMap = new HashMap<String,String>();*/

	private HashMap<String,SwiftRuleEntity> SwiftRuleHashMap = new HashMap<String,SwiftRuleEntity>();
	private HashMap<String, SwiftComplexMapping> swiftComplexMap = new HashMap<String, SwiftComplexMapping>(); 
	private Map<String,Map<String,HashMap<String, String>>> swiftMappingMap = new HashMap<String,Map<String,HashMap<String, String>>>();
	private Map<String,String> swiftDateMap =new HashMap<String,String>();
	private Map<String,String> swiftAmountMap =new HashMap<String,String>();
	private Map<String,String> swiftClobMap =new HashMap<String,String>();
	private Map<String,Map<String,HashMap<String, String>>> swiftDefaultMap = new HashMap<String,Map<String,HashMap<String, String>>>();
	
	public static ChannelCallCache getInstance() {
		return instance;
	}

	private ChannelCallCache(){
		getCachedStageChannelMap();
		createProtradeMappingCache();
		createProtradeDefaultCache();
		getProtradeComplexMap();
		getProtradeDateMap();
		getProcessInitMap();
		getSanctionScreeningMasterMap();
		getBRMSMap();
		getSourceChannel();
		getRequestBy();
		getRequestType();
		getFetchMap();
		getBPMServiceMap();
		amendMap();
		getProtradeTargetMap();
		//loadSwiftMessageDataCache();
		getSwiftRuleHashMap();//SwiftRuleMapCache();
		getSwiftFieldHashMap();//createSwiftTableMappingCache();
		getSwiftComplexHashMap();//createSwiftComplexMap();
		getSwiftNumberMap();//createSwiftNumberMap();
		getSwiftDateMap();//createSwiftDateMap();
		getSwiftDefaultHashMap();
		getSwiftClobHashMap();
//		getSanctionScreeningMasterMap_New();
	}

	public HashMap<String, ArrayList<CallEntity>> getCachedStageChannelMap(){
		if (channelMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"channelMap is empty ");
			try {
				channelMap = createStagechannelMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of channelMap is " + channelMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "errror" + e);
			}
		}
		return channelMap;
	}

	public Map<String,Map<String,HashMap<String, String>>> getProtradeMappingMap(){
		if (protradeMappingMap.isEmpty()) {
			createProtradeMappingCache();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap is: " + protradeMappingMap.toString());
		}
		return protradeMappingMap;
	}
	
	public Map<String,Map<String,HashMap<String, String>>> getChildMappingMap(){
		if (childMappingMap.isEmpty()) {
			createChildMappingCache();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap is: " + protradeMappingMap.toString());
		}
		return childMappingMap;
	}
	public Map<String,Map<String,String>> getProtradeDefaultMaster(){
		if (protradeDefaultMap.isEmpty()) {
			createProtradeDefaultCache();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap is: " + protradeMappingMap.toString());
		}
		return protradeDefaultMap;
	}
	
	public Map<String, String> getSourceChannel(){
		if (sourceChannelMap.isEmpty()) {
			try {
				createSourceChannel();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value OF sourcechannel is: " + sourceChannelMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in protradesourcechannel: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return sourceChannelMap;
	}
	
	//Shivanshu FSK Change 10/11/22
	public HashMap<String, ArrayList<String>> getScreeningChannel(){
		if (screeningChannelMap.isEmpty()) {
			try {
				createScreeningChannel();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value OF Screening Channel is: " + screeningChannelMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in createScreeningChannel: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return screeningChannelMap;
	}
	
	public Map<String, String> getRequestBy(){
		if (requestByMap.isEmpty()) {
			try {
				createRequestBy();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protraderequestby is: " + requestByMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in protraderequestby: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return requestByMap;
	}
	
	public   Map<String, String> getRequestType(){
		if (requestTypeMap.isEmpty()) {
			try {
				createRequestType();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of requestTypeMap is: " + requestTypeMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in requestTypeMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return requestTypeMap;
	}
	
	public Map<String, String> getFetchMap(){
		if (fetchMap.isEmpty()) {
			try {
				createFetchMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of fetchMap is: " + fetchMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getFetchMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return fetchMap;
	}

	public HashMap<String, ArrayList<String>> getProcessValuesMapMap(){
		if (processValuesMap.isEmpty()) {
			loadexternalTableCache();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of processValuesMap is: " + processValuesMap.toString());
		}
		return processValuesMap;
	}
	
	public HashMap<String, ProtradeComplexMapping> getProtradeComplexMap() {
		if (protradeComplexMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"protradeComplexMap is empty ");
			try {
				protradeComplexMap = createProtradeComplexMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeComplexMap is " 
						+ protradeComplexMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getProtradeComplexMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return protradeComplexMap;
	}
	
	public Map<String, String> getProtradeDateMap(){
		if (protradeDateMap.isEmpty()) {
			try {
				createProtradeDateMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeDateMap is: " 
						+ protradeDateMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getProtradeDateMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return protradeDateMap;
	}
	
	public Map<String, ArrayList<String>> getBRMSMap(){
		if (tfoBRMSMap.isEmpty()) {
			try {
				createBRMSMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of tfoBRMSMap is: " + tfoBRMSMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getBRMSMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return tfoBRMSMap;
	}
	public   HashMap<String,String> getProtradeCLOBMaster(){
		if (protradeCLOBMasterMap.isEmpty()) {
			try {
				createProtradeCLOBList();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of tfoBRMSMap is: " + tfoBRMSMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getBRMSMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return protradeCLOBMasterMap;
	}
	public Map<String, String> getBPMServiceMap(){
		if (bpmServiceMap.isEmpty()) {
			try {
				createBPMServiceMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value OF bpmServiceMap is: " + bpmServiceMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getBPMServiceMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return bpmServiceMap;
	}

	
	public Map<String,String> amendMap()
	{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ARPIT MAP INSIDE " );
		Map<String,String> amendmentMap  = new HashMap<String,String>();

				String outputXML = null;
				try {
					outputXML = APCallCreateXML.APSelect("SELECT REQ_CATEGORY,REQ_TYPE , TRANS_TENOR, NEW_TRANS_AMT , NEW_EXP_DATE , DEFAULT_VAL FROM tfo_dj_protrade_amend_default_mast");
				} catch (NGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
				XMLParser xp = new XMLParser(outputXML);
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				int end = 0;
				for (int i = 0; i < noOfFields; ++i) {
					start = xp.getStartIndex("Record", end, 0);
					end = xp.getEndIndex("Record", start, 0);
					String reqcat = xp.getValueOf("REQ_CATEGORY", start, end);
					String reqtype = xp.getValueOf("REQ_TYPE", start, end);
					String transtenor = xp.getValueOf("TRANS_TENOR", start, end);
					String newtransamt = xp.getValueOf("NEW_TRANS_AMT", start, end);
					String newexpdate = xp.getValueOf("NEW_EXP_DATE", start, end);
				    String defvalue = xp.getValueOf("DEFAULT_VAL", start, end);
				String key = reqcat+"#"+reqtype+"#"+transtenor+"#"+newtransamt+"#"+newexpdate;
				//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "process_type: " + process_type);
				//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_cat_code: " + req_cat_code);
				//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);	
				
				amendmentMap.put(key,defvalue);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ARPIT MAP AFTER PUT  ::  "+amendmentMap.toString());
					//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of createsourcechannel: " + amendmentMap.toString());
				}
				
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createsourcechannel final map: " 
						+ amendmentMap.toString());
						
						return amendmentMap;

	}
	
	public Map<String, String> getProtradeTargetMap(){
		if (protradeTargetMap.isEmpty()) {
			try {
				createProtradeTargetMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value OF protradeTargetMap is: " + protradeTargetMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getProtradeTargetMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return protradeTargetMap;
	}

	
	private HashMap<String, ArrayList<CallEntity>> createStagechannelMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside createStagechannelMap once");
		channelMap = new HashMap<String, ArrayList<CallEntity>>();
		String outputXML = APCallCreateXML
				.APSelect("SELECT CHANNEL_NAME,CALL_ID,CALL_NAME, EXECUTION_TYPE FROM CHANNEL_CALL_MASTER WHERE IS_ENABLED='Y' ORDER BY CALL_ID");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		CallEntity[] chArr=new CallEntity[noOfFields];
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String channelName = xp.getValueOf("CHANNEL_NAME", start, end);
			int callId = Integer.parseInt(xp.getValueOf("CALL_ID", start, end));
			String callname = xp.getValueOf("CALL_NAME", start, end);
			String execType = xp.getValueOf("EXECUTION_TYPE", start, end);
			chArr[i]=new CallEntity();
			chArr[i].setCallID(callId);
			chArr[i].setCallName(callname);
			chArr[i].setExecutionType(execType);
			if (channelMap.containsKey(channelName)) {
				ArrayList<CallEntity> callMap = channelMap.get(channelName);
				callMap.add(chArr[i]);
				channelMap.put(channelName, callMap);
			} else {
				ArrayList<CallEntity> callMap = new ArrayList<CallEntity>();
				callMap.add(chArr[i]);
				channelMap.put(channelName, callMap);
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
//					"Value of channelMaps are " + channelMap.toString());

		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
//				"inside channelMap " + channelMap.toString());
		return channelMap;
	}

	private void createProtradeMappingCache() {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE, STRUCTURE_TYPE, "
					+ "ATTRIBUTE_NAME, COLUMN_NAME FROM TFO_DJ_PROTRADE_MAPPING_MASTER");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createProtradeMappingCache outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			int duplicate = 1;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String requestCategory = xp.getValueOf("REQUEST_CATEGORY", start, end);
				String requestType = xp.getValueOf("REQUEST_TYPE", start, end);
				String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
				String attributeName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
				String columnName = xp.getValueOf("COLUMN_NAME", start, end);
				if(protradeMappingMap.containsKey(requestCategory+"#"+requestType)){
					Map<String, HashMap<String, String>> attribMap = protradeMappingMap.get(requestCategory+"#"+requestType);
					if(attribMap.containsKey(structureType)){
						HashMap<String, String> attribList = attribMap.get(structureType);
						if(attribList.containsKey(attributeName)){
							attribList.put(attributeName + " AS " + attributeName + "_" + duplicate,columnName);
							duplicate++;
						} else {
							attribList.put(attributeName,columnName);
						}
						attribMap.put(structureType, attribList);
						protradeMappingMap.put(requestCategory+"#"+requestType, attribMap);
					} else {
						HashMap<String, String> attribList = new HashMap<String, String>();
						attribList.put(attributeName,columnName);
						attribMap.put(structureType, attribList);
						protradeMappingMap.put(requestCategory+"#"+requestType, attribMap);
					}			
				} else {
					HashMap<String, String> attribList = new HashMap<String, String>();
					attribList.put(attributeName,columnName);
					Map<String, HashMap<String, String>> attribMap = new HashMap<String, HashMap<String, String>>();
					attribMap.put(structureType, attribList);
					protradeMappingMap.put(requestCategory+"#"+requestType, attribMap);
				}
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap inside  is: " + protradeMappingMap.toString());	
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		

	}
	private void createProtradeDefaultCache() {
		String outputXml;
		Map<String, String> attribMap ;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT PROCESS_TYPE,REQUEST_CATEGORY, REQUEST_TYPE, DEFAULT_KEY, "
					+ " DEFAULT_VALUE FROM TFO_DJ_PROTRADE_DEFAULT_MASTER");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createProtradeDefaultCache outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String processType = xp.getValueOf("PROCESS_TYPE", start, end);
				String requestCategory = xp.getValueOf("REQUEST_CATEGORY", start, end);
				String requestType = xp.getValueOf("REQUEST_TYPE", start, end);
				String defaultKey = xp.getValueOf("DEFAULT_KEY", start, end);
				String defaultValue = xp.getValueOf("DEFAULT_VALUE", start, end);
				String key=processType+"#"+requestCategory+"#"+requestType;
				if(protradeDefaultMap.containsKey(key)){
					attribMap = protradeDefaultMap.get(key);
					attribMap.put(defaultKey,defaultValue);
					protradeDefaultMap.put(key, attribMap);			
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of createProtradeDefaultCache if  is: " + protradeDefaultMap.toString());	
				} else {
					attribMap = new HashMap<String, String>();
					attribMap.put(defaultKey,defaultValue);
					protradeDefaultMap.put(key, attribMap);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of createProtradeDefaultCache else  is: " + protradeDefaultMap.toString());	
				}
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of createProtradeDefaultCache inside  is: " + protradeDefaultMap.toString());	
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		

	}
	
	private void createRequestType() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createrequesttype once");
		requestTypeMap = new HashMap<String,String>();
		String outputXML = APCallCreateXML.APSelect("SELECT PROCESS_TYPE, REQ_CAT_CODE, REQUEST_TYPE_ID, NEW_ISSUANCE_YES_NO FROM TFO_DJ_REQUEST_TYPE_MAST");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String process_type = xp.getValueOf("PROCESS_TYPE", start, end);
			String req_cat_code = xp.getValueOf("REQ_CAT_CODE", start, end);
			String request_type = xp.getValueOf("REQUEST_TYPE_ID", start, end);
			String new_issuance_yes_no = xp.getValueOf("NEW_ISSUANCE_YES_NO", start, end);	
			String key = process_type+"#"+req_cat_code+"#"+request_type;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "process_type: " + process_type);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_cat_code: " + req_cat_code);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);		
			requestTypeMap.put(key, new_issuance_yes_no);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of createprotraderequesttype: " + requestTypeMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createprotraderequesttype final map: " 
//				+ requestTypeMap.toString());
	}
	
	private void createRequestBy() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createrequestby once");
		requestByMap = new HashMap<String,String>();
		String outputXML = APCallCreateXML.APSelect("SELECT  PROCESS_TYPE,REQ_CAT_CODE, REQ_SUBBY_KEY, IS_ACK FROM TFO_DJ_REQ_SUB_BY_MAST");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
		        String process_type = xp.getValueOf("PROCESS_TYPE", start, end);
				String req_cat_code = xp.getValueOf("REQ_CAT_CODE", start, end);
				String req_subby_key = xp.getValueOf("REQ_SUBBY_KEY", start, end);
				String is_ack = xp.getValueOf("IS_ACK", start, end);
				
			
			String key = process_type+"#"+req_cat_code+"#"+req_subby_key;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "process_type: " + process_type);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_cat_code: " + req_cat_code);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);

			
			requestByMap.put(key, is_ack);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of createrequestby: " + requestByMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createrequestby final map: " 
//				+ requestByMap.toString());
	}
	
	private void createSourceChannel() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createsourcechannel once");
		sourceChannelMap = new HashMap<String,String>();
		String outputXML = APCallCreateXML.APSelect("SELECT PROCESS_TYPE, REQ_CAT_CODE,SOURCE_KEY, IS_ACK FROM TFO_DJ_SOURCE_CHANNEL_MAST");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String process_type = xp.getValueOf("PROCESS_TYPE", start, end);
			String req_cat_code = xp.getValueOf("REQ_CAT_CODE", start, end);
			String sourcekey = xp.getValueOf("SOURCE_KEY", start, end);
			String is_ack = xp.getValueOf("IS_ACK", start, end);


			String key = process_type+"#"+req_cat_code+"#"+sourcekey;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "process_type: " + process_type);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_cat_code: " + req_cat_code);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);	
			sourceChannelMap.put(key,is_ack);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of createsourcechannel: " + sourceChannelMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createsourcechannel final map: " 
//				+ sourceChannelMap.toString());
	}

	private HashMap<String, ProtradeComplexMapping> createProtradeComplexMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeComplexMap once");
		protradeComplexMap = new HashMap<String, ProtradeComplexMapping>();
		String outputXML = APCallCreateXML.APSelect("SELECT STRUCTURE_TYPE, COMPLEX_VAR_NAME, STAGING_TABLE_NAME, "
				+ "DELETE_PREV_ENTRY, COMPLEX_TABLE_NAME, MAPPED_ATTRIBUTE "
				+ "FROM TFO_DJ_PROTRADE_COMPLEX_MASTER");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		ProtradeComplexMapping pcm;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
			String complexVarName = xp.getValueOf("COMPLEX_VAR_NAME", start, end);
			String stagingTableName = xp.getValueOf("STAGING_TABLE_NAME", start, end);
			String dltPrvEntry = xp.getValueOf("DELETE_PREV_ENTRY", start, end);
			String complexTableName = xp.getValueOf("COMPLEX_TABLE_NAME", start, end);
			String mappingAttr = xp.getValueOf("MAPPED_ATTRIBUTE", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "structureType: " + structureType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexVarName: " + complexVarName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "stagingTableName: " + stagingTableName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "dltPrvEntry: " + dltPrvEntry);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexTableName: " + complexTableName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mappingAttr: " + mappingAttr);
			pcm = new ProtradeComplexMapping();
			pcm.setComplexTableName(complexTableName);
			pcm.setComplexVarName(complexVarName);
			pcm.setStagingTableName(stagingTableName);
			pcm.setDeletePreviousEntry(dltPrvEntry);
			pcm.setMappingAttribute(mappingAttr);
//			ArrayList<ProtradeComplexMapping> complexMap = new ArrayList<ProtradeComplexMapping>();
//			complexMap.add(pcm);
			protradeComplexMap.put(structureType, pcm);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of protradeComplexMap: " 
//					+ protradeComplexMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeComplexMap " 
//				+ protradeComplexMap.toString());
		return protradeComplexMap;
	}
	
	private void createProtradeDateMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeDateMap once");
		protradeDateMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, IS_DATE "
				+ "FROM TFO_DJ_PROTRADE_DATE_MASTER");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String attrName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
			String isDate = xp.getValueOf("IS_DATE", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attrName: " + attrName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isDate: " + isDate);
			protradeDateMap.put(attrName, isDate);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of protradeDateMap: " 
//					+ protradeDateMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeDateMap " 
//				+ protradeDateMap.toString());
	}
	
	public Map<String,HashMap<Integer, String>> getProcessInitMap(){
		if (processInitMap.isEmpty()) {
			createSanctionScreeningInitCache();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap is: " + processInitMap.toString());
		}
		return processInitMap;
	}

	private void createSanctionScreeningInitCache() {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT PROCESS_NAME, PROCESSDEFID, INIT_ACTIVITY_NAME FROM BPM_TRSD_INIT_DTLS");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createProtradeMappingCache outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String processName = xp.getValueOf("PROCESS_NAME", start, end);
				int processDefId = Integer.parseInt(xp.getValueOf("PROCESSDEFID", start, end));
				String initActivityName = xp.getValueOf("INIT_ACTIVITY_NAME", start, end);
				if(processInitMap.containsKey(processName)){
					HashMap<Integer, String> attribList = processInitMap.get(processName);
					attribList.put(processDefId,initActivityName);
					processInitMap.put(processName, attribList);
				} else {
					HashMap<Integer, String> attribList = new HashMap<Integer, String>();
					attribList.put(processDefId,initActivityName);
					processInitMap.put(processName, attribList);
				}			
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of sanctionScreeningInitMap if  is: " + processInitMap.toString());	
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of sanctionScreeningInitMap inside  is: " + processInitMap.toString());	
		} catch (Exception e) {			
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		
	}
	public HashMap<String, ArrayList<SanctionScreeningMapping>> getSanctionScreeningMasterMap() {
		if (sanctionScreeningMasterMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"SanctionScreeningMapping is empty ");
			try {
				sanctionScreeningMasterMap = createSanctionScreeningMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of SanctionScreeningMapping is " 
						+ sanctionScreeningMasterMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in SanctionScreeningMapping: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return sanctionScreeningMasterMap;
	}


	@SuppressWarnings("unchecked")
	private HashMap<String, ArrayList<SanctionScreeningMapping>> createSanctionScreeningMap()  {
		try {
			Boolean typeFound;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside SanctionScreeningMapping once");
			sanctionScreeningMasterMap = new HashMap<String, ArrayList<SanctionScreeningMapping>>();
			String outputXML = APCallCreateXML.APSelect("SELECT SOURCE_PROCESS_NAME AS PROCESS_NAME, SCREENING_FOR AS TRSD_TYPE, ENTITY_TYPE AS TRSD_ENTITY_TYPE,SOURCE_TABLE_NAME AS TRSD_TABLE_NAME,SOURCE_COLUMN_NAME AS TRSD_COLUMN_NAME,"
					+ " SOURCE_ATTRIBUTE_NAME AS TRSD_ATTRIBUTE_NAME,MODIFY_COLUMN,SOURCE_SOURCING_CHANNEL AS SOURCING_CHANNEL FROM BPM_TRSD_CALL_MASTER  ORDER BY TRSD_ATTRIBUTE_NAME");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml issss " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			int removeIndex = 0;
			String key ="";
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String process = xp.getValueOf("PROCESS_NAME", start, end);
				String type = xp.getValueOf("TRSD_TYPE", start, end);
				String entityType = xp.getValueOf("TRSD_ENTITY_TYPE", start, end);
				String table = xp.getValueOf("TRSD_TABLE_NAME", start, end);
				String column = xp.getValueOf("TRSD_COLUMN_NAME", start, end);
				String attribute = xp.getValueOf("TRSD_ATTRIBUTE_NAME", start, end);
				String modifyColumn = xp.getValueOf("MODIFY_COLUMN", start, end);
				String sourcingChannel = xp.getValueOf("SOURCING_CHANNEL", start, end);
				
				key = process+"#"+sourcingChannel;
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "process: " + process );
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type: " + type);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entityType: " + entityType);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "table: " + table);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "column: " + column);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attribute: " + attribute);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modifyColumn: " + modifyColumn);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sourchingChannel: " + sourcingChannel);

				if(sanctionScreeningMasterMap.containsKey(key)){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key is there ..");
					ArrayList<SanctionScreeningMapping> arrayList = sanctionScreeningMasterMap.get(key);
					SanctionScreeningMapping sMapToCheck = new SanctionScreeningMapping();
						typeFound = false;
						for (int k = 0; k < arrayList.size(); k++){
							sMapToCheck = (SanctionScreeningMapping) arrayList.get(k);
							String typeToCheck = sMapToCheck.getEntityType();
							if (entityType.equalsIgnoreCase(typeToCheck) && type.equalsIgnoreCase(sMapToCheck.getType())){
								typeFound = true;
								removeIndex = k;
								break;
							}
						}
						if (typeFound){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type equal ");
							HashMap<String, String> attributeMap = sMapToCheck.getAttributeMap();
							HashMap<String, String> attributeModifyMap = sMapToCheck.getAttributeModifyMap();
							attributeMap.put(column, attribute);
							sMapToCheck.setAttributeMap(attributeMap);
							
							attributeModifyMap.put(column, modifyColumn);
							sMapToCheck.setAttributeModifyMap(attributeModifyMap);
							
							arrayList.remove(removeIndex);
							arrayList.add(sMapToCheck);
						} else{
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type not equal ");
							SanctionScreeningMapping sMap1 = new SanctionScreeningMapping();
							HashMap<String, String> attributeMap = new HashMap<String, String>();
							HashMap<String, String> attributeModifyMap = new HashMap<String, String>();

							sMap1.setTable(table);
							sMap1.setType(type);
							sMap1.setEntityType(entityType);
							attributeMap.put(column, attribute);
							sMap1.setAttributeMap(attributeMap);
							//sheenu
							attributeModifyMap.put(column, modifyColumn);
							sMap1.setAttributeModifyMap(attributeModifyMap);
							arrayList.add(sMap1);
							sanctionScreeningMasterMap.put(key, arrayList);							
						}
					
				} else {
					SanctionScreeningMapping sMap = new SanctionScreeningMapping();
					HashMap<String, String> attributeMap = new HashMap<String, String>();
					HashMap<String, String> attributeModifyMap = new HashMap<String, String>();

					ArrayList<SanctionScreeningMapping> sanctionList = new ArrayList<SanctionScreeningMapping>();
					sMap.setTable(table);
					sMap.setType(type);
					sMap.setEntityType(entityType);
					attributeMap.put(column, attribute);
					sMap.setAttributeMap(attributeMap);
					attributeModifyMap.put(column,modifyColumn);
					sMap.setAttributeModifyMap(attributeModifyMap);
					sanctionList.add(sMap);
					sanctionScreeningMasterMap.put(key, sanctionList);
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of sanctionScreeningMasterMap: " + sanctionScreeningMasterMap.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "error in SanctionScreeningMapping: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return sanctionScreeningMasterMap;
	}
	
	//Ketali for new TRSD
//	private HashMap<String, ArrayList<SanctionScreeningMapping>> createSanctionScreeningMap_New()  {
//		try {
//			Boolean typeFound;
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside SanctionScreeningMapping New once");
//			sanctionScreeningMasterMapNew = new HashMap<String, ArrayList<SanctionScreeningMapping>>();
//			String outputXML = APCallCreateXML.APSelect("SELECT SOURCE_PROCESS_NAME AS PROCESS_NAME, SCREENING_FOR AS TRSD_TYPE, ENTITY_TYPE AS TRSD_ENTITY_TYPE,SOURCE_TABLE_NAME AS TRSD_TABLE_NAME,SOURCE_COLUMN_NAME AS TRSD_COLUMN_NAME,"
//					+ " SOURCE_ATTRIBUTE_NAME AS TRSD_ATTRIBUTE_NAME,MODIFY_COLUMN FROM bpm_trsd_call_master_new ORDER BY TRSD_ATTRIBUTE_NAME");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml od newwwwwww issss " + outputXML);
//			XMLParser xp = new XMLParser(outputXML);
//			int start = xp.getStartIndex("Records", 0, 0);
//			int deadEnd = xp.getEndIndex("Records", start, 0);
//			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//			int end = 0;
//			int removeIndex = 0;
//			for (int i = 0; i < noOfFields; ++i) {
//				start = xp.getStartIndex("Record", end, 0);
//				end = xp.getEndIndex("Record", start, 0);
//				String process = xp.getValueOf("PROCESS_NAME", start, end);
//				String type = xp.getValueOf("TRSD_TYPE", start, end);
//				String entityType = xp.getValueOf("TRSD_ENTITY_TYPE", start, end);
//				String table = xp.getValueOf("TRSD_TABLE_NAME", start, end);
//				String column = xp.getValueOf("TRSD_COLUMN_NAME", start, end);
//				String attribute = xp.getValueOf("TRSD_ATTRIBUTE_NAME", start, end);
//				String modifyColumn = xp.getValueOf("MODIFY_COLUMN", start, end);
//				
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "process: " + process);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type: " + type);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entityType: " + entityType);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "table: " + table);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "column: " + column);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attribute: " + attribute);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "modifyColumn: " + modifyColumn);
//
//				if(sanctionScreeningMasterMapNew.containsKey(process)){
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key is there new..");
//					ArrayList attribList = sanctionScreeningMasterMapNew.get(process);
//					SanctionScreeningMapping sMapToCheck = new SanctionScreeningMapping();
//						typeFound = false;
//						for (int k = 0; k < attribList.size(); k++){
//							sMapToCheck = (SanctionScreeningMapping) attribList.get(k);
//							String typeToCheck = sMapToCheck.getEntityType();
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entity type given "+k+" : "+entityType);
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "enityt type inside array"+k+" : "+sMapToCheck.getEntityType());
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entity type given "+k+" : "+type);
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "enityt type inside array"+k+" : "+sMapToCheck.getType());
//							if (entityType.equalsIgnoreCase(typeToCheck) && type.equalsIgnoreCase(sMapToCheck.getType())){
//								typeFound = true;
//								removeIndex = k;
//								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "remove index new + " +k);
//								break;
//							}
//						}
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type found new : "+typeFound);
//						if (typeFound){
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type equal new ");
//							HashMap<String, String> attributeMap = sMapToCheck.getAttributeMap();
//							HashMap<String, String> attributeModifyMap = sMapToCheck.getAttributeModifyMap();
//
//							attributeMap.put(column, attribute);
//							sMapToCheck.setAttributeMap(attributeMap);
//							
//							attributeModifyMap.put(column, modifyColumn);
//							sMapToCheck.setAttributeModifyMap(attributeModifyMap);
//							
//							attribList.remove(removeIndex);
//							attribList.add(sMapToCheck);
//						} else{
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type not equal new");
//							SanctionScreeningMapping sMap1 = new SanctionScreeningMapping();
//							HashMap<String, String> attributeMap = new HashMap<String, String>();
//							HashMap<String, String> attributeModifyMap = new HashMap<String, String>();
//
//							sMap1.setTable(table);
//							sMap1.setType(type);
//							sMap1.setEntityType(entityType);
//							attributeMap.put(column, attribute);
//							sMap1.setAttributeMap(attributeMap);
//							//attribList.add(sMap1);
//							
//							//sheenu
//							attributeModifyMap.put(column, modifyColumn);
//							sMap1.setAttributeModifyMap(attributeModifyMap);
//							attribList.add(sMap1);
//							sanctionScreeningMasterMapNew.put(process, attribList);
//						}
//					
//				} else {
//					SanctionScreeningMapping sMap = new SanctionScreeningMapping();
//					HashMap<String, String> attributeMap = new HashMap<String, String>();
//					HashMap<String, String> attributeModifyMap = new HashMap<String, String>();
//
//					ArrayList<SanctionScreeningMapping> sanctionList = new ArrayList<SanctionScreeningMapping>();
//					sMap.setTable(table);
//					sMap.setType(type);
//					sMap.setEntityType(entityType);
//					attributeMap.put(column, attribute);
//					sMap.setAttributeMap(attributeMap);
//					attributeModifyMap.put(column,modifyColumn);
//					sMap.setAttributeModifyMap(attributeModifyMap);
//					sanctionList.add(sMap);
//					sanctionScreeningMasterMapNew.put(process, sanctionList);
//				}
//			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of sanctionScreeningMasterMap: " 
//					+ sanctionScreeningMasterMapNew.toString());
//			ArrayList<SanctionScreeningMapping> attribList = sanctionScreeningMasterMapNew.get("WBG");
//			for (SanctionScreeningMapping ss : attribList){
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final type wbg : "+ss.getType());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final type wbg: "+ss.getEntityType());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final table wbg: "+ss.getTable());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final map wbg: "+ss.getAttributeMap());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final map wbg: "+ss.getAttributeModifyMap());
//
//			}
//			ArrayList<SanctionScreeningMapping> attribList1 = sanctionScreeningMasterMapNew.get("COB");
//			for (SanctionScreeningMapping ss : attribList1){
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final type cob: "+ss.getType());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final type cob: "+ss.getEntityType());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final table cob: "+ss.getTable());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final map cob: "+ss.getAttributeMap());
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"final map wbg: "+ss.getAttributeModifyMap());
//
//			}
////			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeComplexMap " 
////					+ sanctionScreeningMasterMap.toString());
//		} catch (Exception e) {
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "error in sanctionScreeningMasterMappingNew: ");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//		return sanctionScreeningMasterMapNew;
//	}
	
	
	private void createBRMSMap()throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createBRMSMap once");
		tfoBRMSMap = new HashMap<String, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("select PROCESS_TYPE, REQUEST_CATEGORY_CODE, REQUEST_TYPE_CODE, "
				+ "BILL_TYPE, IF_SIGHT_BILL, DISCREPANCY_INSTRUCTION, SETTLEMENT_INSTRUCTION, MATURITY_DATE, "
				+ "RULE_NO, TARGET_QUEUE, TARGET_QUEUE_2, LINK_WI_REQUEST_CATEGORY_CODE, LINK_WI_REQUEST_TYPE_CODE, "
				+ "LINK_WI_SOURCE_CHANNEL, LINK_WI_SUBMITTED_BY FROM TFO_DJ_BRMS_MASTER");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String processType = xp.getValueOf("PROCESS_TYPE", start, end);
			String reqCat = xp.getValueOf("REQUEST_CATEGORY_CODE", start, end);
			String reqType = xp.getValueOf("REQUEST_TYPE_CODE", start, end);
			String billType = xp.getValueOf("BILL_TYPE", start, end);
			String ifSightBill = xp.getValueOf("IF_SIGHT_BILL", start, end);
			String discInstn = xp.getValueOf("DISCREPANCY_INSTRUCTION", start, end);
			String setInstn = xp.getValueOf("SETTLEMENT_INSTRUCTION", start, end);
			String matDate = xp.getValueOf("MATURITY_DATE", start, end);
			String rule = xp.getValueOf("RULE_NO", start, end);
			String target1 = xp.getValueOf("TARGET_QUEUE", start, end);
			String target2 = xp.getValueOf("TARGET_QUEUE_2", start, end);
			String childReqCat = xp.getValueOf("LINK_WI_REQUEST_CATEGORY_CODE", start, end);
			String childReqType = xp.getValueOf("LINK_WI_REQUEST_TYPE_CODE", start, end);
			String childSource = xp.getValueOf("LINK_WI_SOURCE_CHANNEL", start, end);
			String childSubBy = xp.getValueOf("LINK_WI_SUBMITTED_BY", start, end);
			String key = processType+"#"+reqCat+"#"+reqType+"#"+billType+"#"+ifSightBill+"#"+discInstn+"#"+setInstn
					+"#"+matDate;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processType: " + processType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "reqCat: " + reqCat);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);
			ArrayList list = new ArrayList<String>();
			list.add(rule);
			list.add(target1);
			list.add(target2);
			list.add(childReqCat);
			list.add(childReqType);
			list.add(childSource);
			list.add(childSubBy);
			tfoBRMSMap.put(key, list);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of tfoBRMSMap: " + tfoBRMSMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createBRMSMap final map: " 
//				+ tfoBRMSMap.toString());
	}
	
	private void createProtradeCLOBList() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeCLOBMap once");
		protradeCLOBMasterMap = new HashMap<String,String>();
		String outputXML = APCallCreateXML.APSelect("select ATTRIBUTE_NAME,IS_CLOB from TFO_DJ_PROTRADE_CLOB_MASTER");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String attributeName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
			String isCLOB = xp.getValueOf("IS_CLOB", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attributeName: " + attributeName);
			protradeCLOBMasterMap.put(attributeName, isCLOB);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of createProtradeCLOBMap: " + protradeCLOBMasterMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeCLOBMap final map: " 
//				+ protradeCLOBMasterMap.toString());
	}
	
	private void loadexternalTableCache() {
		String process;
		String external_table;
		String trsd_flag_column;
		String complete_flag;
		String trsdUrl;
		String sourcing_channel_column;
		try {
			System.out.println("SS");
			String outputXML11 = null ;
			try {
				outputXML11 = APCallCreateXML.APSelect("SELECT PROCESS_NAME,PROCESS_TABLE, TRSD_FLAG_COLUMN , "
						+ "COMPLETE_FLAG, TRSD_URL,SOURCING_CHANNEL_COLUMN FROM BPM_SS_EXECUTION_MASTER");
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "error in APSelect loadexternalTableCache: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			XMLParser xmlDataParser = new XMLParser(outputXML11);
			int start = 0;
			int end = 0;
			int deadend = 0;
			start = xmlDataParser.getStartIndex("Records", 0, 0);
			deadend = xmlDataParser.getEndIndex("Records", start, 0);
			int count  = xmlDataParser.getNoOfFields("Record",start,deadend);
			for (int i=0;i<count;i++)
			{
				start = xmlDataParser.getStartIndex("Record", end, 0);
				end = xmlDataParser.getEndIndex("Record", start, 0);
				process = xmlDataParser.getValueOf("PROCESS_NAME",start,end);
				external_table = xmlDataParser.getValueOf("PROCESS_TABLE",start,end);
				trsd_flag_column = xmlDataParser.getValueOf("TRSD_FLAG_COLUMN",start,end);
				complete_flag = xmlDataParser.getValueOf("COMPLETE_FLAG",start,end);
				trsdUrl = xmlDataParser.getValueOf("TRSD_URL",start,end);
				sourcing_channel_column = xmlDataParser.getValueOf("SOURCING_CHANNEL_COLUMN",start,end);
				ArrayList ls = new ArrayList();
				ls.add(external_table);
				ls.add(trsd_flag_column);
				ls.add(complete_flag);
				ls.add(trsdUrl);
				ls.add(sourcing_channel_column);
				processValuesMap.put(process, ls);
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"processValuesMap:::  "+processValuesMap);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "error in loadexternalTableCache: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	private void createFetchMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createFetchMap once");
		tfoBRMSMap = new HashMap<String, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("select REQ_CAT_CODE, REQ_TYPE_CODE, PROCESS_TYPE, FIELD_NAME "
				+ "from TFO_DJ_RECEPTION_FETCH_MAST");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String processType = xp.getValueOf("PROCESS_TYPE", start, end);
			String reqCat = xp.getValueOf("REQ_CAT_CODE", start, end);
			String reqType = xp.getValueOf("REQ_TYPE_CODE", start, end);
			String fieldName = xp.getValueOf("FIELD_NAME", start, end);
			String key = processType+"#"+reqCat+"#"+reqType;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processType: " + processType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "reqCat: " + reqCat);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);
			fetchMap.put(key, fieldName);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of fetchMap: " + fetchMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createBRMSMap final map: " 
//				+ fetchMap.toString());
	}
	
	private void createBPMServiceMap() throws NGException {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createBPMServiceMap once");
			bpmServiceMap = new HashMap<String, String>();
			String outputXML = APCallCreateXML.APSelect("SELECT KEY, VALUE FROM BPM_SERVICE_CONFIG");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String key = xp.getValueOf("KEY", start, end);
				String value = xp.getValueOf("VALUE", start, end);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "value: " + value);
				bpmServiceMap.put(key, value);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of bpmServiceMap: " + bpmServiceMap.toString());
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createBPMServiceMap final map: " 
//					+ bpmServiceMap.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in createBPMServiceMap: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}
	
	private void createProtradeTargetMap() throws NGException {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeTargetMap once");
			protradeTargetMap = new HashMap<String, String>();
			String outputXML = APCallCreateXML.APSelect("select PROCESS_TYPE, REQUEST_CATEGORY, REQUEST_TYPE, "
					+ "TARGET_WORKSTEP from TFO_DJ_PROTRADE_TARGET_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String processType = xp.getValueOf("PROCESS_TYPE", start, end);
				String reqCat = xp.getValueOf("REQUEST_CATEGORY", start, end);
				String reqType = xp.getValueOf("REQUEST_TYPE", start, end);
				String targetWS = xp.getValueOf("TARGET_WORKSTEP", start, end);
				String key = processType+"#"+reqCat+"#"+reqType;
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key: " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "value: " + targetWS);
				protradeTargetMap.put(key, targetWS);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of protradeTargetMap: " 
//						+ protradeTargetMap.toString());
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createProtradeTargetMap final map: " 
//					+ protradeTargetMap.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in createProtradeTargetMap: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}
	
	/*public HashMap<Integer,HashMap<String,String>> getSwiftOuterHashMap(){
		if(outerHashMap.isEmpty()){
			try {
				loadSwiftMessageDataCache();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftOuterHashMap is " 
						+ outerHashMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftOuterHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return outerHashMap;
	}
	
	public HashMap<Integer,HashMap<String,String>> getSwiftPartyOuterHashMap(){
		if(partyOuterHashMap.isEmpty()){
			try {
				loadSwiftMessageDataCache();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftPartyOuterHashMap is " 
						+ partyOuterHashMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftPartyOuterHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return partyOuterHashMap;
	}*/
	
	/*private  void loadSwiftMessageDataCache(){
		int msgType;	
		int partyMsgType = 0;
		String tagName="";
		String columnName="";
		String tableName="";
		int ruleID;
		int partyRuleID = 0;

		try{
			outerHashMap = new HashMap<Integer,HashMap<String,String>>();
			partyOuterHashMap = new HashMap<Integer,HashMap<String,String>>();
			String query = "SELECT DISTINCT(RULE_ID) as RULE_ID FROM TFO_DJ_SWIFT_FIELD_MASTER";
			String outputXML = APCallCreateXML.APSelect(query);
			XMLParser valueParser = new XMLParser(outputXML);
			XMLParser tagsParser = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(valueParser.getValueOf("MainCode"));
			if(mainCode == 0){
				int tagCount  =  valueParser.getNoOfFields("Record");
				for(int i = 0 ;i < tagCount ; i++)
				{
					innerHashMap=null;
					partyInnerHashMap=null;
					tagsParser.setInputXML(valueParser.getNextValueOf("Record"));
					//msgType =  Integer.parseInt(tagsParser.getValueOf("MSG_TYPE"));//Commented by kishan for new code
					ruleID =  Integer.parseInt(tagsParser.getValueOf("RULE_ID"));
					//String queryInner = "SELECT TAG_NAME,COLUMN_NAME,TABLE_NAME  FROM TFO_DJ_SWIFT_FIELD_MASTER WHERE MSG_TYPE = '"+msgType+"'";
					//above line commented by kishan for new code
					String queryInner = "SELECT TAG_NAME,COLUMN_NAME,TABLE_NAME  FROM TFO_DJ_SWIFT_FIELD_MASTER WHERE RULE_ID = '"+ruleID+"'";
					String outputXMLInner = APCallCreateXML.APSelect(queryInner);
					XMLParser valueParserInner = new XMLParser(outputXMLInner);
					XMLParser tagsParserInner = new XMLParser(outputXMLInner);
					int mainCodeInner = Integer.parseInt(valueParser.getValueOf("MainCode"));
					if(mainCodeInner == 0){
						int tagCountInner  =  valueParserInner.getNoOfFields("Record");
						innerHashMap = new HashMap<String,String>();
						partyInnerHashMap =  new HashMap<String,String>();
						for(int j = 0 ;j < tagCountInner ; j++)
						{
							tagsParserInner.setInputXML(valueParserInner.getNextValueOf("Record"));
							tableName = tagsParserInner.getValueOf("TABLE_NAME");
							if("EXT_TFO".equalsIgnoreCase(tableName))
							{
								tagName = tagsParserInner.getValueOf("TAG_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagName :" + tagName);
								columnName = tagsParserInner.getValueOf("COLUMN_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "columnName :" + columnName);
								innerHashMap.put(columnName,tagName);
							}else if("TFO_DJ_PARTY_DETAILS".equalsIgnoreCase(tableName)){
								//partyMsgType = msgType;//commented by kishan for new code change
								partyRuleID = ruleID;
								tagName = tagsParserInner.getValueOf("TAG_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagName :" + tagName);
								columnName = tagsParserInner.getValueOf("COLUMN_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "columnName :" + columnName);
								partyInnerHashMap.put(columnName, tagName);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "partyRuleID :" + partyRuleID);
							}
							//SwiftTableHashMap
							if(!SwiftTableHashMap.containsKey(tableName)){
								HashMap<String,String> map =  new HashMap<>();
								tagName = tagsParserInner.getValueOf("TAG_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagName    :" + tagName);
								columnName = tagsParserInner.getValueOf("COLUMN_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "columnName :" + columnName);
								map.put(columnName,tagName);
								SwiftTableHashMap.put(tableName,map);
							}else{
								HashMap<String,String> obj = SwiftTableHashMap.get(tableName);
								tagName = tagsParserInner.getValueOf("TAG_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagName    :" + tagName);
								columnName = tagsParserInner.getValueOf("COLUMN_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "columnName :" + columnName);
								obj.put(columnName, tagName);
								SwiftTableHashMap.put(tableName, obj);
							}
							
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "innerHashMap :" + innerHashMap);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "partyInnerHashMap :" + partyInnerHashMap);
					}
					if(outerHashMap != null)
					{
						outerHashMap.put(ruleID, innerHashMap);
						msgType=0;
						ruleID = 0;
					}
					if(partyInnerHashMap != null)
					{
						partyOuterHashMap.put(partyRuleID, partyInnerHashMap);
						partyMsgType=0;
						partyRuleID = 0;
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outer main map for external table" + outerHashMap);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outer main map for PARTY table" + partyOuterHashMap);
				}	
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}*/
	
	public HashMap<String,SwiftRuleEntity> getSwiftRuleHashMap(){
		if(SwiftRuleHashMap.isEmpty()){
			try {
				SwiftRuleMapCache();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftRuleHashMap is " 
						+ SwiftRuleHashMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftRuleHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return SwiftRuleHashMap;
	}
	//Modify ATP-458 Shivanshu 18-7-2024
	private void SwiftRuleMapCache(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE  : SwiftRuleMapCache " );
		String msgType;	
		String PURPOSE_MESSAGE_A;
		String FORM_UNDERTAKING_B;
		String DEMAND_TYPE;
		String RuleID;
		String INDEX_TYPE;
		String EXTENSION_TYPE;
		SwiftRuleHashMap = new HashMap<String,SwiftRuleEntity>();
		try{
			String query = "SELECT RULE_ID,MSG_TYPE,REQ_CAT_CODE,REQ_TYPE_CODE,"
					+ "PURPOSE_MESSAGE_A,FORM_UNDERTAKING_B,DEMAND_TYPE,INDEX_TYPE,EXTENSION_TYPE FROM TFO_DJ_SWIFT_REQ_CAT_MASTER";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Query  ::"+query );
			String outputXML = APCallCreateXML.APSelect(query);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Query result ::"+outputXML );
			XMLParser valueParser = new XMLParser(outputXML);
			XMLParser tagsParser = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(valueParser.getValueOf("MainCode"));
				if(mainCode == 0){
					int tagCount  =  valueParser.getNoOfFields("Record");
					for(int i = 0 ;i < tagCount ; i++){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside For Loop" );
						tagsParser.setInputXML(valueParser.getNextValueOf("Record"));
						msgType =  tagsParser.getValueOf("MSG_TYPE");
						PURPOSE_MESSAGE_A =  tagsParser.getValueOf("PURPOSE_MESSAGE_A");
						FORM_UNDERTAKING_B =  tagsParser.getValueOf("FORM_UNDERTAKING_B");
						DEMAND_TYPE =  tagsParser.getValueOf("DEMAND_TYPE");
						RuleID = tagsParser.getValueOf("RULE_ID");
						INDEX_TYPE =  tagsParser.getValueOf("INDEX_TYPE");
						EXTENSION_TYPE = tagsParser.getValueOf("EXTENSION_TYPE");
						
						String key = msgType +"#"+PURPOSE_MESSAGE_A+"#"+FORM_UNDERTAKING_B+"#"+DEMAND_TYPE+"#"+INDEX_TYPE+"#"+EXTENSION_TYPE;
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Check 7 :: "+key );
						SwiftRuleEntity object = new SwiftRuleEntity();
						object.setRequestCategory(tagsParser.getValueOf("REQ_CAT_CODE"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Check 9" );
						object.setRequesttype(tagsParser.getValueOf("REQ_TYPE_CODE"));
						object.setRuleID(RuleID);
						String queryInner = "SELECT SNO,RULE_ID,REQ_CAT_CODE,REQ_TYPE_CODE,MAST_TABLE  "
								+ "FROM TFO_DJ_SWIFT_REQ_CAT_RULE_MAST WHERE RULE_ID = '"+RuleID+"'";
						String outputXMLInner = APCallCreateXML.APSelect(queryInner);
						XMLParser valueParserInner = new XMLParser(outputXMLInner);
						XMLParser tagsParserInner = new XMLParser(outputXMLInner);
						int mainCodeInner = Integer.parseInt(valueParser.getValueOf("MainCode"));
						if(mainCodeInner == 0){
							int tagCountInner  =  valueParserInner.getNoOfFields("Record");
							ArrayList<String> subRuleList = new ArrayList<String>();
							for(int j = 0 ;j < tagCountInner ; j++)
							{
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside SubRule For Loop" );
								tagsParserInner.setInputXML(valueParserInner.getNextValueOf("Record"));
								String innerReqCat = tagsParserInner.getValueOf("REQ_CAT_CODE");
								String innerReqType = tagsParserInner.getValueOf("REQ_TYPE_CODE");
								String innertable = tagsParserInner.getValueOf("MAST_TABLE");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "suRulevalue ::"+innerReqCat +"#"+ innerReqType+"#"+ innertable );
								subRuleList.add(innerReqCat +"#"+ innerReqType+"#"+ innertable);
							}
						object.setSubRule(subRuleList);	
					}
					SwiftRuleHashMap.put(key,object);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "SwiftRuleHashMap :: "+SwiftRuleHashMap.toString() );
				}
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ERROR :" +e.getStackTrace());
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "END  : SwiftRuleMapCache " );
	}
	
	public Map<String, Map<String, HashMap<String, String>>> getSwiftFieldHashMap(){
		if(swiftMappingMap.isEmpty()){
			try {
				createSwiftFieldMappingCache();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftTableHashMap is " 
						+ swiftMappingMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftTableHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return swiftMappingMap;
	}
	
	private void createSwiftFieldMappingCache() {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT RULE_ID, TABLE_NAME, TAG_NAME, "
					+ " COLUMN_NAME FROM TFO_DJ_SWIFT_FIELD_MASTER ");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createSwiftTableMappingCache outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String ruleID = xp.getValueOf("RULE_ID", start, end);
				String tableName = xp.getValueOf("TABLE_NAME", start, end);
				String tagName = xp.getValueOf("TAG_NAME", start, end);
				String columnName = xp.getValueOf("COLUMN_NAME", start, end);
				if(swiftMappingMap.containsKey(ruleID)){
					Map<String, HashMap<String, String>> attribMap = swiftMappingMap.get(ruleID);
					if(attribMap.containsKey(tableName)){
						HashMap<String, String> attribList = attribMap.get(tableName);
						if(!attribList.containsKey(tagName)){
							attribList.put(tagName,columnName);
						}
						attribMap.put(tableName, attribList);
						swiftMappingMap.put(ruleID, attribMap);
					} else {
						HashMap<String, String> attribList = new HashMap<String, String>();
						attribList.put(tagName,columnName);
						attribMap.put(tableName, attribList);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	" attribMap" + attribMap);
						swiftMappingMap.put(ruleID, attribMap);
					}			
				} else {
					HashMap<String, String> attribList = new HashMap<String, String>();
					attribList.put(tagName,columnName);
					Map<String, HashMap<String, String>> attribMap = new HashMap<String, HashMap<String, String>>();
					attribMap.put(tableName, attribList);
					swiftMappingMap.put(ruleID, attribMap);
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createSwiftTableMappingCache swiftMappingMap " + swiftMappingMap);
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ERROR : "+ e.getStackTrace());
		}		
	}
	
	public HashMap<String, SwiftComplexMapping> getSwiftComplexHashMap(){
		if(swiftComplexMap.isEmpty()){
			try {
				createSwiftComplexMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftComplexHashMap is " 
						+ swiftComplexMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftComplexHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return swiftComplexMap;
	}
	
	private HashMap<String, SwiftComplexMapping> createSwiftComplexMap()  {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createSwiftComplexMap");
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT STRUCTURE_TYPE, COMPLEX_VAR_NAME, STAGING_TABLE_NAME, "
					+ "DELETE_PREV_ENTRY, COMPLEX_TABLE_NAME, MAPPED_ATTRIBUTE "
					+ "FROM TFO_DJ_SWIFT_COMPLEX_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml :: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			SwiftComplexMapping pcm;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
				String complexVarName = xp.getValueOf("COMPLEX_VAR_NAME", start, end);
				String stagingTableName = xp.getValueOf("STAGING_TABLE_NAME", start, end);
				String dltPrvEntry = xp.getValueOf("DELETE_PREV_ENTRY", start, end);
				String complexTableName = xp.getValueOf("COMPLEX_TABLE_NAME", start, end);
				String mappingAttr = xp.getValueOf("MAPPED_ATTRIBUTE", start, end);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "structureType: " + structureType);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexVarName: " + complexVarName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "stagingTableName: " + stagingTableName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "dltPrvEntry: " + dltPrvEntry);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexTableName: " + complexTableName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mappingAttr: " + mappingAttr);
				pcm = new SwiftComplexMapping();
				pcm.setComplexTableName(complexTableName);
				pcm.setComplexVarName(complexVarName);
				pcm.setStagingTableName(stagingTableName);
				pcm.setDeletePreviousEntry(dltPrvEntry);
				pcm.setMappingAttribute(mappingAttr);
				pcm.setStructureType(structureType);
				swiftComplexMap.put(complexTableName, pcm);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PRINT SWIFTCOMPLEXMAP " 
					+ swiftComplexMap.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ERROR SWIFTCOMPLEXMAP " + e.getStackTrace());
		}
		return swiftComplexMap;
	}
	
	public Map<String,String> getSwiftDateMap(){
		if(swiftDateMap.isEmpty()){
			try {
				createSwiftDateMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftDateMap is " 
						+ swiftDateMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftDateMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return swiftDateMap;
	}
	
	private  void createSwiftDateMap() {
		try{
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside createSwiftDateMap ");
			//swiftDateMap = new HashMap<String, String>();
			String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, IS_DATE "
					+ "FROM TFO_DJ_SWIFT_DATE_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml" + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String attrName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
				String isDate = xp.getValueOf("IS_DATE", start, end);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attrName: " + attrName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isDate: " + isDate);
				swiftDateMap.put(attrName, isDate);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, swiftDateMap.toString());
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "ERROR createSwiftDateMap :: "+e.getStackTrace());
		}
	}
	
	public Map<String,String> getSwiftNumberMap(){
		if(swiftAmountMap.isEmpty()){
			try {
				createSwiftNumberMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftNumberMap is " 
						+ swiftAmountMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftNumberMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return swiftAmountMap;
	}
	
	private  void createSwiftNumberMap()  {
		try{
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createSwiftAmountMap once");
			//swiftAmountMap = new HashMap<String, String>();
			String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, IS_AMOUNT "
					+ "FROM TFO_DJ_SWIFT_AMOUNT_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Outputxml" + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String attrName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
				String isDate = xp.getValueOf("IS_AMOUNT", start, end);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "AttrName: " + attrName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IsDate: " + isDate);
				swiftAmountMap.put(attrName, isDate);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, swiftAmountMap.toString());
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Error createSwiftAmountMEap "+e.getStackTrace());
		}
	}
	
	public Map<String, Map<String, HashMap<String, String>>> getSwiftDefaultHashMap(){
		if(swiftDefaultMap.isEmpty()){
			try {
				createSwiftDefaultMappingCache();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftDefaultHashMap is " 
						+ swiftDefaultMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftDefaultHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return swiftDefaultMap;
	}
	
	private void createSwiftDefaultMappingCache() {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT RULE_ID, TABLE_ID, MAP_FIELD, "
					+ " DEF_VALUE FROM TFO_DJ_SWIFT_DEFAULT_MASTER");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createSwiftDefaultMappingCache outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			int duplicate = 1;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String ruleID = xp.getValueOf("RULE_ID", start, end);
				String tableName = xp.getValueOf("TABLE_ID", start, end);
				String tagName = xp.getValueOf("MAP_FIELD", start, end);
				String columnName = xp.getValueOf("DEF_VALUE", start, end);
				if(swiftDefaultMap.containsKey(ruleID)){
					Map<String, HashMap<String, String>> attribMap = swiftDefaultMap.get(ruleID);
					if(attribMap.containsKey(tableName)){
						HashMap<String, String> attribList = attribMap.get(tableName);
						if(!attribList.containsKey(tagName)){
							attribList.put(tagName,columnName);
						}
						attribMap.put(tableName, attribList);
						swiftDefaultMap.put(ruleID, attribMap);
					} else {
						HashMap<String, String> attribList = new HashMap<String, String>();
						attribList.put(tagName,columnName);
						attribMap.put(tableName, attribList);
						swiftDefaultMap.put(ruleID, attribMap);
					}			
				} else {
					HashMap<String, String> attribList = new HashMap<String, String>();
					attribList.put(tagName,columnName);
					Map<String, HashMap<String, String>> attribMap = new HashMap<String, HashMap<String, String>>();
					attribMap.put(tableName, attribList);
					swiftDefaultMap.put(ruleID, attribMap);
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createSwiftDefaultMappingCache swiftDefaultMap " + swiftDefaultMap);
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ERROR : "+ e.getStackTrace());
		}		
	}
	private void createChildMappingCache() {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY,REQUEST_TYPE,PROCESS_TYPE,STRUCTURE_TYPE,"
					+ "ATTRIBUTE_NAME,COLUMN_NAME FROM TFO_DJ_CHILD_MAPPING_MASTER");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createProtradeMappingCache outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			int duplicate = 1;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String requestCategory = xp.getValueOf("REQUEST_CATEGORY", start, end);
				String requestType = xp.getValueOf("REQUEST_TYPE", start, end);
				String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
				String attributeName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
				String columnName = xp.getValueOf("COLUMN_NAME", start, end);
				String processType = xp.getValueOf("PROCESS_TYPE", start, end);
				String key=requestCategory+"#"+requestType+"#"+processType;
				if(childMappingMap.containsKey(key)){
					Map<String, HashMap<String, String>> attribMap = childMappingMap.get(key);
					if(attribMap.containsKey(structureType)){
						HashMap<String, String> attribList = attribMap.get(structureType);
						if(attribList.containsKey(attributeName)){
							attribList.put(attributeName + " AS " + attributeName + "_" + duplicate,columnName);
							duplicate++;
						} else {
							attribList.put(attributeName,columnName);
						}
						attribMap.put(structureType, attribList);
						childMappingMap.put(key, attribMap);
					} else {
						HashMap<String, String> attribList = new HashMap<String, String>();
						attribList.put(attributeName,columnName);
						attribMap.put(structureType, attribList);
						childMappingMap.put(key, attribMap);
					}			
				} else {
					HashMap<String, String> attribList = new HashMap<String, String>();
					attribList.put(attributeName,columnName);
					Map<String, HashMap<String, String>> attribMap = new HashMap<String, HashMap<String, String>>();
					attribMap.put(structureType, attribList);
					childMappingMap.put(key, attribMap);
				}
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap inside  is: " + protradeMappingMap.toString());	
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		

	}
	
	public Map<String, String> getSwiftClobHashMap(){
		if(swiftClobMap.isEmpty()){
			try {
				createSwiftClobMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of getSwiftClobHashMap is " 
						+ swiftClobMap.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getSwiftClobHashMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return swiftClobMap;
	}
	
	private  void createSwiftClobMap()  {
		try{
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside createSwiftClobMap once");
			String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, IS_CLOB"
					+ " FROM TFO_DJ_SWIFT_CLOB_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Outputxml" + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String attrName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
				String isDate = xp.getValueOf("IS_CLOB", start, end);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "AttrName: " + attrName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "IS_CLOB: " + isDate);
				swiftClobMap.put(attrName, isDate);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, swiftClobMap.toString());
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Error createSwiftClobMap "+e.getStackTrace());
		}
	}
	
	// Shivanshu FSK Change
	private void createScreeningChannel() throws NGException {
		try{
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createScreeningChannel once");
			String outputXML = APCallCreateXML.APSelect("SELECT SCREENING_CHANNEL,PROCESS_NAME,SOURCING_CHANNEL,CHANNEL_ID FROM BPM_SANCTION_SCREENING_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String screeningType = xp.getValueOf("SCREENING_CHANNEL", start, end);
				String processName = xp.getValueOf("PROCESS_NAME", start, end);
				String sourcingChannel = xp.getValueOf("SOURCING_CHANNEL", start, end);
				String channelID = xp.getValueOf("CHANNEL_ID", start, end);
				String key = processName + "#" + sourcingChannel;
				ArrayList<String> value = new ArrayList<>();
				value.add(screeningType);
				value.add(channelID);
				screeningChannelMap.put(key, value);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Screening Map: " + screeningChannelMap.toString());
			} 
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "error in createScreeningChannel: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}	

		
}
