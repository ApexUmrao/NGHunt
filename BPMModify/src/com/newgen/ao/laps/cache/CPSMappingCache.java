package com.newgen.ao.laps.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;


public class CPSMappingCache {

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
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + "call Name is ");
		if (callMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + "call Name is empty ");
			try {
				callMap = createStagecallMap(cabinetName,sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + "Value of call name is " + callMap.toString());
				} catch (NGException e) {
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + " errror " + e);
			}
		}
		return callMap;
	}
	
	public HashMap<String, String> getSourcingChannelMasterMap(String cabinetName,String sessionId) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getSourcingChannelMasterMap]" + "call Name is ");
		if (callSourcingChannelMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getSourcingChannelMasterMap]" + "call Name is empty ");
			try {
				callSourcingChannelMap = createSourcingChannelCallMap(cabinetName,sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getSourcingChannelMasterMap]" + "Value of call name is " + callMap.toString());
				} catch (NGException e) {
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getSourcingChannelMasterMap]" + " errror " + e);
			}
		}
		return callSourcingChannelMap;
	}
	
	public HashMap<String,String> getCachedDecisionMasterMap(String cabinetName,String sessionId) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedDecisionMasterMap]" + "call Name is ");
		if (callDecisionMasterMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedDecisionMasterMap]" + "call Name is empty ");
			try {
				callDecisionMasterMap = createDecisionMasterMap(cabinetName,sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedDecisionMasterMap]" + "Value of call name is " + callDecisionMasterMap.toString());
				} catch (NGException e) {
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedDecisionMasterMap]" + " errror " + e);
			}
		}
		return callDecisionMasterMap;
	}
	
	public HashMap<String,String> getCachedParentDecisionMap(String cabinetName,String sessionId) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + "call Name is ");
		if (callParentDecisionMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + "call Name is empty ");
			try {
				callParentDecisionMap = createParentDecisionMap(cabinetName,sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + "Value of call name is " + callParentDecisionMap.toString());
				} catch (NGException e) {
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCachedStagecallMap]" + " errror " + e);
			}
		}
		return callParentDecisionMap;
	}
	
private HashMap<String,String> createSourcingChannelCallMap(String cabinetName,String sessionId) throws NGException {
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " inside createSourcingChannelCallMap once ");
		try{	
			callSourcingChannelMap = new HashMap<String,String>();
			String sQuery = "Select PROCESS_NAME,COMMUNICATION_TYPE,SOURCING_CHANNEL,IS_VALID_SOURCING_CHANNEL from CPS_SOURCING_CHANNEL_MASTER";
			String outputXML = APCallCreateXML.APSelect(sQuery);

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " outputxml is " + outputXML);
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

				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " key is " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " value is " + value);
				if(!callSourcingChannelMap.containsKey(key)){
					callSourcingChannelMap.put(key, value);
				} 			
			} 
		}catch (NGException e) {	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callSourcingChannelMap;
	}
	
	private HashMap<String,ArrayList<String>> createStagecallMap(String cabinetName,String sessionId) throws NGException {
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createStagecallMap]" + " inside createStagecallMap once ");
		try{
			callMap = new HashMap<String,ArrayList<String>>();
			String outputXML = APCallCreateXML
					.APSelect("select table_name, wi_name_column, table_column, process_name, column_desc from CPS_MAPPING_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createStagecallMap]" + " outputxml is " + outputXML);
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
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createStagecallMap]" + " key is " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createStagecallMap]" + " value is " + value);
				if(!callMap.containsKey(key)){
					callMap.put(key, value);
				} 			
			}
		} catch (NGException e) {	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " errror " + e);
	}
		return callMap;
	}


	private HashMap<String,String> createDecisionMasterMap(String cabinetName,String sessionId) throws NGException {
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createDecisionMasterMap]" + " inside createStagecallMap once ");
		try{
			callDecisionMasterMap = new HashMap<String,String>();
			String outputXML = APCallCreateXML
					.APSelect("Select PROCESS_NAME,WS_NAME,DECISION from CPS_DECISION_MASTER");

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createDecisionMasterMap]" + " outputxml is " + outputXML);
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
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createDecisionMasterMap]" + " key is " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createDecisionMasterMap]" + " value is " + value);
				if(!callDecisionMasterMap.containsKey(key)){
					callDecisionMasterMap.put(key, value);
				} 			
			}
		} catch (NGException e) {	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callDecisionMasterMap;
	}


	private HashMap<String,String> createParentDecisionMap(String cabinetName,String sessionId) throws NGException {
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createParentDecisionMap]" + " inside createParentDecisionMap once ");
		try{
			callParentDecisionMap = new HashMap<String,String>();
			String outputXML = APCallCreateXML
					.APSelect("SELECT PARENT_DECISION,PROCESS,FINAL_DECISON  from BPM_CPS_PARENT_DECISION_MASTER");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createStagecallMap]" + " outputxml is " + outputXML);
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
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[callDecisionMap]" + " key is " + key);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[callDecisionMap]" + " value is " + value);
				if(!callParentDecisionMap.containsKey(key)){
					callParentDecisionMap.put(key, value);
				} 			
			}
		} catch (NGException e) {	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[createSourcingChannelCallMap]" + " errror " + e);
		}
		return callParentDecisionMap;
}


}
