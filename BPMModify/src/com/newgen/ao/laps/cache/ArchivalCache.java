package com.newgen.ao.laps.cache;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.ao.laps.logger.ArchivalLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ArchivalCache {

	private static ArchivalCache instance = new ArchivalCache();

	private HashMap<String,String> ProcessTypeMap = new HashMap<String,String>();   
	private HashMap<String,ArrayList<String>> ProcessConfigMap = new HashMap<String, ArrayList<String>>(); 
	private HashMap<String,ArrayList<String>> ConfigMap = new HashMap<String, ArrayList<String>>();  
	private Map<String,HashMap<Integer, String>> processArchivalInitMap = new HashMap<String,HashMap<Integer, String>>();
	private HashMap<String, ArrayList<String>> processArchivalValuesMap = new HashMap<String, ArrayList<String> >();


	public static ArchivalCache getInstance() {
		return instance;
	}

	private ArchivalCache(){
		getProcessArchivalInitMap();  
		getArchivalProcessValuesMap();
		getProcessConfigMap();
	}
	public HashMap<String, String> getProcessType(String processName){
		if (ProcessTypeMap.isEmpty()) {
			try {
				createProcessTypeMap(processName);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"Value OF Process Type is: " + ProcessTypeMap.toString());
			} catch (NGException e) {
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "error in createProcessType: ");
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, e);
			}
		}
		return ProcessTypeMap;
	}

	public HashMap<String, ArrayList<String>> getArchivalProcessValuesMap(){
		if (processArchivalValuesMap.isEmpty()) {
			loadArchivalexternalTableCache();
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"Value of processValuesMap is: " + processArchivalValuesMap.toString());
		}
		return processArchivalValuesMap;
	}

	public HashMap<String, ArrayList<String>> getProcessConfigMap(){
		if (ProcessConfigMap.isEmpty()) {
			try {
				createProcessConfigMap();
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"Value OF ProcessConfigMap is: " + createProcessConfigMap().toString());
			} catch (NGException e) {
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "error in ProcessConfigMap: ");
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, e);
			}
		}
		return ProcessConfigMap;
	}

	public HashMap<String, ArrayList<String>> getConfigMap(){
		if (ConfigMap.isEmpty()) {
			try {
				createConfigMap();
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"Value OF createConfigMap is: " + createConfigMap().toString());
			} catch (NGException e) {
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "error in createConfigMap: ");
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, e);
			}
		}
		return ConfigMap;
	}

	public Map<String,HashMap<Integer, String>> getProcessArchivalInitMap(){
		if (processArchivalInitMap.isEmpty()) {
			createArchivalInitCache();
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"Value of protradeMappingMap is: " + processArchivalInitMap.toString());
		}
		return processArchivalInitMap;
	}


	private HashMap<String,String> createProcessTypeMap(String processName) throws NGException {
		try{
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "inside createProcessType once");
			String outputXML = APCallCreateXML.APSelect("SELECT PROCESSTYPE,PROCESSNAME FROM USR_0_ARCHIVAL_TYPE");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String processType = xp.getValueOf("PROCESSTYPE", start, end);
				processName = xp.getValueOf("PROCESSNAME", start, end);
				ProcessTypeMap.put(processName, processType);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "ProcessTypeMap Map: " + ProcessTypeMap.toString());
			} 
		} catch (Exception e) {
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, "error in ProcessTypeMap: ");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, e);
		}
		return ProcessTypeMap;		
	}

	private HashMap<String,ArrayList<String>> createProcessConfigMap() throws NGException {
		try{
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "inside createProcessConfigMap once");
			String outputXML = APCallCreateXML.APSelect("SELECT PROCESSNAME,EXTTABLENAME,ACTIVITY,TRANSACTIONTABLE,PRODUCTTABLE,WI_COMPLETE,BATCH_SIZE,WORKITEM_COLUMN FROM USR_0_CONFIG_PROCESS_MASTER");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String processName =xp.getValueOf("PROCESSNAME", start, end);
				String extTableName = xp.getValueOf("EXTTABLENAME", start, end);
				String activityName = xp.getValueOf("ACTIVITY", start, end);
				String transactionTable = xp.getValueOf("TRANSACTIONTABLE", start, end);
				String productTable = xp.getValueOf("PRODUCTTABLE", start, end);
				String completeFlag = xp.getValueOf("WI_COMPLETE", start, end);
				String batchSize = xp.getValueOf("BATCH_SIZE", start, end);
				String wiColumn = xp.getValueOf("WORKITEM_COLUMN", start, end);
				ArrayList<String> value = new ArrayList<>();
				value.add(extTableName);
				value.add(activityName);
				value.add(transactionTable);
				value.add(productTable);
				value.add(completeFlag);
				value.add(batchSize);
				value.add(wiColumn);
				ProcessConfigMap.put(processName, value);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "ProcessConfigMap Map: " + ProcessConfigMap.toString());
			} 
		}catch (Exception e) {
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, "error in createProcessConfigMap: ");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, e);
		}
		return ProcessConfigMap;
	}	

	private HashMap<String,ArrayList<String>> createConfigMap() throws NGException {
		try{
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "inside createProcessConfigMap once");
			String outputXML = APCallCreateXML.APSelect("SELECT PROCESS_NAME,REQUIRED_TYPE,REQUIRED_TABLE,REQUIRED_COLUMN,WHERE_CONDITION,PRIMARYTYPES FROM USR_0_CONFIG_PROCESS_XML_MASTER");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "outputxml is: " + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String processName =xp.getValueOf("PROCESS_NAME", start, end);
				String requiredType = xp.getValueOf("REQUIRED_TYPE", start, end);
				String requiredTable = xp.getValueOf("REQUIRED_TABLE", start, end);
				String requiredColumn = xp.getValueOf("REQUIRED_COLUMN", start, end);
				String whereCondition = xp.getValueOf("WHERE_CONDITION", start, end);
				String primaryTypes = xp.getValueOf("PRIMARYTYPES", start, end);
				String key = processName+"#"+requiredType;

				ArrayList<String> value = new ArrayList<>();
				value.add(requiredTable);
				value.add(requiredColumn);
				value.add(whereCondition);
				value.add(primaryTypes);
				ConfigMap.put(key, value);
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "createConfigMap: " + ConfigMap.toString());
			} 
		}catch (Exception e) {
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, "error in createConfigMap: ");
			ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, e);
		}
		return ConfigMap;
	}	

	//Added by reyaz 26-07-2023
		private void createArchivalInitCache() {
			String outputXml;
			try {
				outputXml = APCallCreateXML.APSelect("SELECT PROCESS_NAME, PROCESSDEFID, INIT_ACTIVITY_NAME FROM BPM_ARCHIVAL_INIT_DTLS");		
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"createArchivalInitCache outputxml " + outputXml);
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
					if(processArchivalInitMap.containsKey(processName)){
						HashMap<Integer, String> attribList = processArchivalInitMap.get(processName);
						attribList.put(processDefId,initActivityName);
						processArchivalInitMap.put(processName, attribList);
					} else {
						HashMap<Integer, String> attribList = new HashMap<Integer, String>();
						attribList.put(processDefId,initActivityName);
						processArchivalInitMap.put(processName, attribList);
					}			
					ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,	"Value of ArchivalInitCache if  is: " + processArchivalInitMap.toString());	
				}
			} catch (Exception e) {			
			}		
		}
		
		private void loadArchivalexternalTableCache() {
			String process;
			String external_table;
			String archival_flag_column;
			String complete_flag;
			String wiName;
			try {
				System.out.println("SS");
				String outputXML11 = null ;
				try {
					outputXML11 = APCallCreateXML.APSelect("SELECT PROCESS_NAME,PROCESS_TABLE, ARCHIVAL_FLAG_COLUMN , "
							+ "COMPLETE_FLAG,WORKITEM_NAME FROM BPM_DA_EXECUTION_MASTER");
				} catch (NGException e) {
					ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, "error in APSelect loadexternalTableCache: ");
					ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, e);
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
					archival_flag_column = xmlDataParser.getValueOf("ARCHIVAL_FLAG_COLUMN",start,end);
					complete_flag = xmlDataParser.getValueOf("COMPLETE_FLAG",start,end);
					wiName = xmlDataParser.getValueOf("WORKITEM_NAME",start,end);
					ArrayList ls = new ArrayList();
					ls.add(external_table);
					ls.add(archival_flag_column);
					ls.add(complete_flag);
					ls.add(wiName);
					processArchivalValuesMap.put(process, ls);
				}
			} catch (Exception e) {
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, "error in loadexternalTableCache: ");
				ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_ERROR, e);
			}
		}	

}
