package com.newgen.push.message.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.push.message.APCallCreateXML;
import com.newgen.push.message.BPMReqLogMe;

public class ProTradeUtils { 
	private static ProTradeUtils instance = null;
	public static HashMap<String, HashMap<String, HashMap<String, String>>> attributeStructureHashMap =
			new HashMap<String,HashMap<String, HashMap<String, String>>>();
	private Map<String, String> protradeStagingMap = new HashMap<String, String>();
	private Map<String, String> protradeDateMap = new HashMap<String, String>();
	private Map<String, String> protradeCLOBMasterMap = new HashMap<String, String>();
	
	private ProTradeUtils(){
	}

	public static ProTradeUtils getInstance(){
		if(instance == null){
			ProTradeUtils tempInstance = new ProTradeUtils();
//			tempInstance.getMasterData();
			instance = tempInstance;
		}
		return instance;
	}
	
	public Map<String, String> getProtradeDateMap(){
		if (protradeDateMap.isEmpty()) {
			try {
				createProtradeDateMap();
				BPMReqLogMe.logMe(1, "Value of protradeDateMap is: " + protradeDateMap.toString());
			} catch (NGException e) {
				BPMReqLogMe.logMe(1, "error in getProtradeDateMap: ");
				BPMReqLogMe.logMe(1, e);
			}
		}
		return protradeDateMap;
	}
	
	public Map<String, String> getprotradeCLOBMasterMap(){
		if (protradeCLOBMasterMap.isEmpty()) {
			try {
				createProtradeCLOBMasterMap();
				BPMReqLogMe.logMe(1, "Value of protradeCLOBMasterMap is: " + protradeDateMap.toString());
			} catch (NGException e) {
				BPMReqLogMe.logMe(1, "error in getprotradeCLOBMasterMap: ");
				BPMReqLogMe.logMe(1, e);
			}
		}
		return protradeCLOBMasterMap;
	}
	
	public Map<String, String> getProtradeStagingMap(){
		if (protradeStagingMap.isEmpty()) {
			try {
				createProtradeStagingMap();
				BPMReqLogMe.logMe(1, "Value of protradeStagingMap is: " + protradeStagingMap.toString());
			} catch (NGException e) {
				BPMReqLogMe.logMe(1, "error in getProtradeStagingMap: ");
				BPMReqLogMe.logMe(1, e);
			}
		}
		return protradeStagingMap;
	}

	private void createProtradeStagingMap() throws NGException {
		BPMReqLogMe.logMe(1, "inside createProtradeStagingMap once");
		protradeStagingMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT STRUCTURE_TYPE, STAGING_TABLE_NAME "
				+ "FROM TFO_DJ_PROTRADE_COMPLEX_MASTER");
		BPMReqLogMe.logMe(1, "outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
			String stagingTableName = xp.getValueOf("STAGING_TABLE_NAME", start, end);
			BPMReqLogMe.logMe(1, "structureType: " + structureType);
			BPMReqLogMe.logMe(1, "stagingTableName: " + stagingTableName);
			protradeStagingMap.put(structureType, stagingTableName);
			BPMReqLogMe.logMe(1, "Value of protradeStagingMap: " + protradeStagingMap.toString());
		}
		BPMReqLogMe.logMe(1, "inside createProtradeStagingMap " + protradeStagingMap.toString());
	}
	
	private void createProtradeDateMap() throws NGException {
		BPMReqLogMe.logMe(1, "inside createProtradeDateMap once");
		protradeDateMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, IS_DATE "
				+ "FROM TFO_DJ_PROTRADE_DATE_MASTER");
		BPMReqLogMe.logMe(1, "outputxml issss " + outputXML);
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
			BPMReqLogMe.logMe(1, "attrName: " + attrName);
			BPMReqLogMe.logMe(1, "isDate: " + isDate);
			protradeDateMap.put(attrName, isDate);
			BPMReqLogMe.logMe(1, "Value of protradeDateMap: " + protradeDateMap.toString());
		}
		BPMReqLogMe.logMe(1, "inside createProtradeDateMap " + protradeDateMap.toString());
	}
	private void createProtradeCLOBMasterMap() throws NGException {
		BPMReqLogMe.logMe(1, "inside createProtradeCLOBMap once");
		protradeCLOBMasterMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("select ATTRIBUTE_NAME,IS_CLOB from TFO_DJ_PROTRADE_CLOB_MASTER");
		BPMReqLogMe.logMe(1, "outputxml is: " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String attrName = xp.getValueOf("ATTRIBUTE_NAME", start, end);
			String isCLOB = xp.getValueOf("IS_CLOB", start, end);
			BPMReqLogMe.logMe(1, "attrName: " + attrName);
			BPMReqLogMe.logMe(1, "isDisCLOBate: " + isCLOB);
			protradeCLOBMasterMap.put(attrName, isCLOB);
			BPMReqLogMe.logMe(1, "Value of protradeDateMap: " + protradeCLOBMasterMap.toString());
		}
		BPMReqLogMe.logMe(1, "inside createProtradeCLOBMap final map: " 
				+ protradeCLOBMasterMap.toString());
	}
	
//	public void getMasterData(){
//		HashMap<String, HashMap<String, String>> innermap1 = null;
//		HashMap<String, String> innermap2 = null;
//		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"inside getMasterData from table ");
//		String outputXML="";
//		String structure_type = "";
//		String structure_name = "";
//		try {
//			outputXML = APCallCreateXML.APSelect("select structure_type, structure_name, attribute_name, "
//					+ "column_name from TFO_DJ_PROTRADE_MQ_MASTER ");
//		} catch (NGException e) {
//			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"exception in getmasterdata APSelect: "+e);
//		}
//		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"outputxml is: " + outputXML);
//		XMLParser xp = new XMLParser(outputXML);
//		int start = xp.getStartIndex("Records", 0, 0);
//		int deadEnd = xp.getEndIndex("Records", start, 0);
//		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//		int end = 0;
//		for (int i = 0; i < noOfFields; ++i) {
//			start = xp.getStartIndex("Record", end, 0);
//			end = xp.getEndIndex("Record", start, 0);
//			structure_type = xp.getValueOf("structure_type", start, end);
//			structure_name = xp.getValueOf("structure_name", start, end);
//			String attribute_name = xp.getValueOf("attribute_name", start, end);
//			String column_name = xp.getValueOf("column_name", start, end);
//			if(attributeStructureHashMap.containsKey(structure_type))
//			{	
//				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"1- attributeStructureHashMap: "
//						+attributeStructureHashMap);
//				if(attributeStructureHashMap.get(structure_type).containsKey(structure_name))
//				{
//					attributeStructureHashMap.get(structure_type).get(structure_name)
//						.put(attribute_name, column_name);
//				}
//				else 
//				{
//					innermap2 = new HashMap<String, String>();
//					innermap2.put(attribute_name, column_name);
//					attributeStructureHashMap.get(structure_type).put(structure_name, innermap2);
//				}
//			}
//			else 
//			{	
//				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"2- attributeStructureHashMap: "
//						+attributeStructureHashMap);
//				innermap1 = new HashMap<String, HashMap<String, String>>();
//				innermap2 = new HashMap<String, String>();
//				innermap2.put(attribute_name, column_name);
//				innermap1.put(structure_name, innermap2);
//				attributeStructureHashMap.put(structure_type, innermap1);
//			}
//		}
//		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"final attributeStructureHashMap: "+attributeStructureHashMap);
//	}
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> getAttributeStructureMap() {
		return attributeStructureHashMap;
	}
	
	public void setAttributeStructureMap(HashMap<String, HashMap<String, HashMap<String, String>>> 
		attributeStructureHashMap) {
		this.attributeStructureHashMap = attributeStructureHashMap;
	}
}
