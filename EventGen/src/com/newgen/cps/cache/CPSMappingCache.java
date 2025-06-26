package com.newgen.cps.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.newgen.util.ExecuteXML;
import com.newgen.util.APCallCreateXML;
import com.newgen.util.GenerateXml;
import com.newgen.util.Log;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.util.*;



public class CPSMappingCache extends Log{

	private static String cabinetName;
	private static String sessionId;
	private HashMap<String, ArrayList<String>> callMap = new HashMap<String, ArrayList<String>>();
	private HashMap<String, String> callParentDecisionMap = new HashMap<String, String>();
	private HashMap<String, String> callDecisionMasterMap = new HashMap<String, String>();
	private HashMap<String, String> callSourcingChannelMap = new HashMap<String, String>();
	private static CPSMappingCache instance = new CPSMappingCache();
	

	public static CPSMappingCache getInstance() {
		return instance;
	}
	
//	private CPSMappingCache(){
//		getCachedStagecallMap(cabinetName,sessionId);
//		getCachedParentDecisionMap(cabinetName,sessionId);
//		getCachedDecisionMasterMap(cabinetName,sessionId);
//
//	}

	public HashMap<String,ArrayList<String>> getCachedStagecallMap(String cabinetName,String sessionId) {
		logger.info("[getCachedStagecallMap]" + "call Name is ");
		if (callMap.isEmpty()) {
			logger.info("[getCachedStagecallMap]" + "call Name is empty ");
			try {
				callMap = createStagecallMap(cabinetName,sessionId);
				logger.info("[getCachedStagecallMap]" + "Value of call name is " + callMap.toString());
				} catch (NGException e) {
				
				logger.info("[getCachedStagecallMap]" + " errror " + e);
			}
		}
		return callMap;
	}
	
	public HashMap<String, String> getSourcingChannelMasterMap(String cabinetName,String sessionId) {
		logger.info("[getSourcingChannelMasterMap]" + "call Name is ");
		if (callSourcingChannelMap.isEmpty()) {
			logger.info("[getSourcingChannelMasterMap]" + "call Name is empty ");
			try {
				callSourcingChannelMap = createSourcingChannelCallMap(cabinetName,sessionId);
				logger.info("[getSourcingChannelMasterMap]" + "Value of call name is " + callMap.toString());
				} catch (NGException e) {
				
				logger.info("[getSourcingChannelMasterMap]" + " errror " + e);
			}
		}
		return callSourcingChannelMap;
	}
	
	public HashMap<String,String> getCachedDecisionMasterMap(String cabinetName,String sessionId) {
		logger.info("[getCachedDecisionMasterMap]" + "call Name is ");
		if (callDecisionMasterMap.isEmpty()) {
			logger.info("[getCachedDecisionMasterMap]" + "call Name is empty ");
			try {
				callDecisionMasterMap = createDecisionMasterMap(cabinetName,sessionId);
				logger.info("[getCachedDecisionMasterMap]" + "Value of call name is " + callDecisionMasterMap.toString());
				} catch (NGException e) {
				
				logger.info("[getCachedDecisionMasterMap]" + " errror " + e);
			}
		}
		return callDecisionMasterMap;
	}
	
	public HashMap<String,String> getCachedParentDecisionMap(String cabinetName,String sessionId) {
		logger.info("[getCachedStagecallMap]" + "call Name is ");
		if (callParentDecisionMap.isEmpty()) {
			logger.info("[getCachedStagecallMap]" + "call Name is empty ");
			try {
				callParentDecisionMap = createParentDecisionMap(cabinetName,sessionId);
				logger.info("[getCachedStagecallMap]" + "Value of call name is " + callParentDecisionMap.toString());
				} catch (NGException e) {
				
				logger.info("[getCachedStagecallMap]" + " errror " + e);
			}
		}
		return callParentDecisionMap;
	}
	
private HashMap<String,String> createSourcingChannelCallMap(String cabinetName,String sessionId) throws NGException {
	
		logger.info("[createSourcingChannelCallMap]" + " inside createSourcingChannelCallMap once ");
		try{
			callSourcingChannelMap = new HashMap<String,String>();
			String sQuery = "Select PROCESS_NAME,COMMUNICATION_TYPE,SOURCING_CHANNEL,IS_VALID_SOURCING_CHANNEL from CPS_SOURCING_CHANNEL_MASTER";
			String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			logger.info("[createSourcingChannelCallMap]" + " outputxml is " + outputXML);
			XMLParser xp = new XMLParser(outputXML);

			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String communicationType = xp.getValueOf("COMMUNICATION_TYPE" , start, end);
				String sourcingChannel = xp.getValueOf("SOURCING_CHANNEL" , start, end);
				String processName = xp.getValueOf("PROCESS_NAME", start, end);
				String is_valid_sourcing_channel = xp.getValueOf("IS_VALID_SOURCING_CHANNEL", start, end);

				String key = processName + "#" + sourcingChannel+"#"+communicationType;
				String value = is_valid_sourcing_channel;
				logger.info("[createSourcingChannelCallMap]" + " key is " + key);
				logger.info("[createSourcingChannelCallMap]" + " value is " + value);
				if(!callSourcingChannelMap.containsKey(key)){
					callSourcingChannelMap.put(key, value);
				} 
			}
		} catch (NGException e) {	
			logger.info("[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callSourcingChannelMap;
 }


	
	private HashMap<String,ArrayList<String>> createStagecallMap(String cabinetName,String sessionId) throws NGException {
		
		logger.info("[createStagecallMap]" + " inside createStagecallMap once ");
		try{
			callMap = new HashMap<String,ArrayList<String>>();
			String sQuery = "select table_name, wi_name_column, table_column, process_name, column_desc from CPS_MAPPING_MASTER";
			String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			logger.info("[createStagecallMap]" + " outputxml is " + outputXML);
			XMLParser xp = new XMLParser(outputXML);

			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String table_name = xp.getValueOf("table_name" , start, end);
				String wi_name_column = xp.getValueOf("wi_name_column" , start, end);
				String table_column = xp.getValueOf("table_column" , start, end);
				String process_name = xp.getValueOf("process_name", start, end);
				String column_desc = xp.getValueOf("column_desc", start, end);
				String key = column_desc + "#" + process_name;
				//String value = table_name + "#" + wi_name_column + "#" + table_column;
				ArrayList<String> value = new ArrayList<>();
				value.add(table_name);
				value.add(wi_name_column);
				value.add(table_column);
				logger.info("[createStagecallMap]" + " key is " + key);
				logger.info("[createStagecallMap]" + " value is " + value);
				if(!callMap.containsKey(key)){
					callMap.put(key, value);
				} 			
			}
		}catch (NGException e) {	
			logger.info("[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callMap;
	}


	private HashMap<String,String> createDecisionMasterMap(String cabinetName,String sessionId) throws NGException {
		
		logger.info("[createDecisionMasterMap]" + " inside createStagecallMap once ");
		try{
			callDecisionMasterMap = new HashMap<String,String>();
			String sQuery = "Select PROCESS_NAME,WS_NAME,DECISION from CPS_DECISION_MASTER";
			String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			logger.info("[createDecisionMasterMap]" + " outputxml is " + outputXML);
			XMLParser xp = new XMLParser(outputXML);

			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String wsName = xp.getValueOf("WS_NAME" , start, end);
				String decision = xp.getValueOf("DECISION" , start, end);
				String processName = xp.getValueOf("PROCESS_NAME", start, end);
				String key = processName + "#" + wsName;
				String value = decision;
				logger.info("[createDecisionMasterMap]" + " key is " + key);
				logger.info("[createDecisionMasterMap]" + " value is " + value);
				if(!callDecisionMasterMap.containsKey(key)){
					callDecisionMasterMap.put(key, value);
				} 			
			}
		} catch (NGException e) {	
			logger.info("[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callDecisionMasterMap;
	}


	private HashMap<String,String> createParentDecisionMap(String cabinetName,String sessionId) throws NGException {
		
		logger.info("[createParentDecisionMap]" + " inside createParentDecisionMap once ");
		try{
			callParentDecisionMap = new HashMap<String,String>();
			String sQuery = "SELECT PARENT_DECISION,PROCESS,FINAL_DECISON  from BPM_CPS_PARENT_DECISION_MASTER";
			String outputXML = ExecuteXML.executeXML(GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId));
			logger.info("[createStagecallMap]" + " outputxml is " + outputXML);
			XMLParser xp = new XMLParser(outputXML);

			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			String parentDecison = "";	String process = "";	String final_decision = "";	String key ="";	String value ="";
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				parentDecison = xp.getValueOf("PARENT_DECISION" , start, end);
				process = xp.getValueOf("PROCESS" , start, end);
				final_decision = xp.getValueOf("FINAL_DECISON" , start, end);
				key = process + "#" + parentDecison;
				value = final_decision;
				logger.info("[callDecisionMap]" + " key is " + key);
				logger.info("[callDecisionMap]" + " value is " + value);
				if(!callParentDecisionMap.containsKey(key)){
					callParentDecisionMap.put(key, value);
				} 			
			}
		} catch (NGException e) {	
			logger.info("[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callParentDecisionMap;
	}


}
