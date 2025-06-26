package com.newgen.ao.laps.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;


public class TSLMStagingCache { 
	private static TSLMStagingCache instance = new TSLMStagingCache();
	public static HashMap<String, HashMap<String, HashMap<String, String>>> attributeStructureHashMap =
			new HashMap<String,HashMap<String, HashMap<String, String>>>();
	private Map<String, String> TSLMStagingMap = new HashMap<String, String>();
	private Map<String, String> TSLMProcessTableMap = new HashMap<String, String>();
	private Map<String,Map<String,HashMap<String, String>>> TSLMMappingMap = new HashMap<String,Map<String,HashMap<String, String>>>();
	private Map<String, String> TSLMDateMap = new HashMap<String, String>();
	private Map<String, String> TSLMCLOBMasterMap = new HashMap<String, String>();
    private HashMap<String, TSLMComplexMapping> TSLMComplexMap = new HashMap<String, TSLMComplexMapping>(); 
	private HashMap<String, ArrayList<String>> TSLMReferralMasterMap = new HashMap<String, ArrayList<String>>();
	private Map<String,Map<String,String>> TSLMDefaultMap = new HashMap<String,Map<String,String>>();


	public static TSLMStagingCache getInstance()  {
		return instance;
	}
	
	private TSLMStagingCache(){
		getTSLMReferralMaster();
	}
	
	public Map<String,Map<String,HashMap<String, String>>> getTSLMMappingMap(String processDefId,String sourcingChannel){
		if (TSLMMappingMap.isEmpty()) {
			
			TSLMMappingMap=createTSLMMappingCache(processDefId,sourcingChannel);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of TSLMMappingMap is: " + TSLMMappingMap.toString());
		}
		return TSLMMappingMap;
	}
	
	public Map<String,Map<String,String>> getTSLMDefaultMaster(String processDefId,String sourcingChannel){
		if (TSLMDefaultMap.isEmpty()) {
			TSLMDefaultMap = createTSLMDefaultCache( processDefId, sourcingChannel);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of TSLMDefaultMap is: " + TSLMDefaultMap.toString());
		}
		return TSLMDefaultMap;
	}
	
	public HashMap<String,ArrayList<String>> getTSLMReferralMaster(){
		if (TSLMReferralMasterMap.isEmpty()) {
			TSLMReferralMasterMap = createTSLMReferralMaster();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of TSLMReferralMap is: " + TSLMReferralMasterMap.toString());
		}
		return TSLMReferralMasterMap;
	}
	
	public Map<String, String> getTSLMCLOBMasterMap(String processDefId,String sourcingChannel){
		if (TSLMCLOBMasterMap.isEmpty()) {
			try {
				TSLMCLOBMasterMap = createTSLMClobMasterMap(processDefId,sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of TSLMCLOBMasterMap is: " + TSLMDateMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"error in getTSLMCLOBMasterMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,e);
			}
		}
		return TSLMCLOBMasterMap;
	}
	
	public HashMap<String, TSLMComplexMapping> getTSLMComplexMap(String processDefId,String sourcingChannel) {
		if (TSLMComplexMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"TSLMComplexMap is empty ");
			try {
				TSLMComplexMap = createTSLMComplexMap(processDefId, sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of TSLMComplexMap is " 
						+ TSLMComplexMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getTSLMComplexMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return TSLMComplexMap;
	}
	
	public Map<String, String> getTSLMDateMap(String processDefId,String sourcingChannel){
		if (TSLMDateMap.isEmpty()) {
			try {
				TSLMDateMap = createTSLMDateMap(processDefId,sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of TSLMDateMap is: " 
						+ TSLMDateMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "error in getTSLMDateMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, e);
			}
		}
		return TSLMDateMap;
	}
	
	public Map<String, String> getTSLMStagingMap(String processDefId,String sourcingChannel){
		if (TSLMStagingMap.isEmpty()) {
			try {
				TSLMStagingMap = createTSLMStagingMap(processDefId,sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of TSLMStagingMap is: " + TSLMStagingMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"error in getTSLMStagingMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,e);
			}
		}
		return TSLMStagingMap;
	}
	
	public Map<String, String> getTSLMProcessTableMap(String processDefId,String sourcingChannel){
		if (TSLMProcessTableMap.isEmpty()) {
			try {
				TSLMProcessTableMap = createTSLMProcessTableMap(processDefId,sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of TSLMProcessTableMap is: " + TSLMProcessTableMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"error in getTSLMProcessTableMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,e);
			}
		}
		return TSLMProcessTableMap;
	}
	
	private HashMap<String, TSLMComplexMapping> createTSLMComplexMap(String processDefId,String sourcingChannel) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createTSLMComplexMap once");
		TSLMComplexMap = new HashMap<String, TSLMComplexMapping>();
		String outputXML = APCallCreateXML.APSelect("SELECT SOURCING_CHANNEL,STRUCTURE_TYPE, COMPLEX_VAR_NAME, STAGING_TABLE_NAME, "
				+ "DELETE_PREV_ENTRY, COMPLEX_TABLE_NAME, MAPPED_ATTRIBUTE "
				+ "FROM BPM_COMPLEX_MASTER WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		TSLMComplexMapping tcm;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String sourcingChannelType = xp.getValueOf("SOURCING_CHANNEL", start, end);
			String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
			String complexVarName = xp.getValueOf("COMPLEX_VAR_NAME", start, end);
			String stagingTableName = xp.getValueOf("STAGING_TABLE_NAME", start, end);
			String dltPrvEntry = xp.getValueOf("DELETE_PREV_ENTRY", start, end);
			String complexTableName = xp.getValueOf("COMPLEX_TABLE_NAME", start, end);
			String mappingAttr = xp.getValueOf("MAPPED_ATTRIBUTE", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "structureType: " + sourcingChannelType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "structureType: " + structureType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexVarName: " + complexVarName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "stagingTableName: " + stagingTableName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "dltPrvEntry: " + dltPrvEntry);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexTableName: " + complexTableName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mappingAttr: " + mappingAttr);
			tcm = new TSLMComplexMapping();
			
			tcm.setComplexTableName(sourcingChannelType);
			tcm.setComplexTableName(complexTableName);
			tcm.setComplexVarName(complexVarName);
			tcm.setStagingTableName(stagingTableName);
			tcm.setDeletePreviousEntry(dltPrvEntry);
			tcm.setMappingAttribute(mappingAttr);
//			ArrayList<TSLMComplexMapping> complexMap = new ArrayList<TSLMComplexMapping>();
//			complexMap.add(tcm);
			TSLMComplexMap.put(structureType, tcm);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of TSLMComplexMap: " 
//					+ TSLMComplexMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createTSLMComplexMap " 
//				+ TSLMComplexMap.toString());
		return TSLMComplexMap;
	}

	private Map<String, String> createTSLMStagingMap(String processDefId,String sourcingChannel) throws NGException {
		try {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside createTSLMStagingMap once");
		TSLMStagingMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT STRUCTURE_TYPE, STAGING_TABLE_NAME "
				+ "FROM BPM_COMPLEX_MASTER WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"structureType: " + structureType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"stagingTableName: " + stagingTableName);
			TSLMStagingMap.put(structureType, stagingTableName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of TSLMStagingMap: " + TSLMStagingMap.toString());
		}
		}catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return TSLMStagingMap;
	}
	
	private Map<String, String> createTSLMProcessTableMap(String processDefId,String sourcingChannel) throws NGException {
		try {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside createTSLMProcessTableMap once");
		TSLMProcessTableMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT STRUCTURE_TYPE, COMPLEX_TABLE_NAME "
				+ "FROM BPM_COMPLEX_MASTER WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
			String stagingTableName = xp.getValueOf("COMPLEX_TABLE_NAME", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"structureType: " + structureType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"stagingTableName: " + stagingTableName);
			TSLMProcessTableMap.put(structureType, stagingTableName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of TSLMProcessTableMap: " + TSLMProcessTableMap.toString());
		}
		}catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return TSLMProcessTableMap;
	}
		
	
	
	private Map<String, String> createTSLMDateMap(String processDefId,String sourcingChannel) throws NGException {
		try {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createTSLMDateMap once");
		TSLMDateMap = new HashMap<String, String>();
		String outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_NAME, IS_DATE "
				+ "FROM BPM_DATE_MASTER WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");
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
			TSLMDateMap.put(attrName, isDate);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of TSLMDateMap: " 
//					+ TSLMDateMap.toString());
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createTSLMDateMap " 
//				+ TSLMDateMap.toString());
		}catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return TSLMDateMap;
	}
	
	
	private Map<String, String> createTSLMClobMasterMap(String processDefId,String sourcingChannel) throws NGException {
		try {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createTSLMCLOBMap once");
		TSLMCLOBMasterMap = new HashMap<String,String>();
		String outputXML = APCallCreateXML.APSelect("select ATTRIBUTE_NAME,IS_CLOB from BPM_CLOB_MASTER "
				+ " WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");
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
			TSLMCLOBMasterMap.put(attributeName, isCLOB);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of createTSLMCLOBMap: " + TSLMCLOBMasterMap.toString());
		}
		}catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createTSLMCLOBMap final map: " 
//				+ TSLMCLOBMasterMap.toString());
		return TSLMCLOBMasterMap;
	}
	
	
	private Map<String, Map<String, String>> createTSLMDefaultCache(String processDefId,String sourcingChannel) {
		String outputXml;
		Map<String, String> attribMap ;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT PROCESS_TYPE,REQUEST_CATEGORY, REQUEST_TYPE, DEFAULT_KEY, "
					+ " DEFAULT_VALUE FROM BPM_DEFAULT_MASTER WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createTSLMDefaultCache outputxml " + outputXml);
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
				if(TSLMDefaultMap.containsKey(key)){
					attribMap = TSLMDefaultMap.get(key);
					attribMap.put(defaultKey,defaultValue);
					TSLMDefaultMap.put(key, attribMap);			
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of createTSLMDefaultCache if  is: " + TSLMDefaultMap.toString());	
				} else {
					attribMap = new HashMap<String, String>();
					attribMap.put(defaultKey,defaultValue);
					TSLMDefaultMap.put(key, attribMap);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of createTSLMDefaultCache else  is: " + TSLMDefaultMap.toString());	
				}
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of createTSLMDefaultCache inside  is: " + TSLMDefaultMap.toString());	
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		
        return TSLMDefaultMap;
	}
	
	
	private HashMap<String,ArrayList<String>> createTSLMReferralMaster() {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT  REF_CODE, TABLE_NAME, SOURCING_CHANNEL"   ////ATP-379 15-FEB-24 --JAMSHED
					+ " FROM BPM_REFERRAL_MASTER ");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createTSLMReferralMaster outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String referCode = xp.getValueOf("REF_CODE", start, end);
				String tableName = xp.getValueOf("TABLE_NAME", start, end);
				//ATP-379 15-FEB-24 --JAMSHED
				String sourcingChannel = xp.getValueOf("SOURCING_CHANNEL", start, end);
				String key = referCode+"#"+sourcingChannel;
				//ATP-379 15-FEB-24 --JAMSHED ends
				ArrayList<String> value = new ArrayList<>();
				value.add(tableName);
				if(!TSLMReferralMasterMap.containsKey(key)){
					TSLMReferralMasterMap.put(key, value);
			  }
			}
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		
		return TSLMReferralMasterMap;
	}
	
	private Map<String, Map<String, HashMap<String, String>>> createTSLMMappingCache(String processDefId,String sourcingChannel) {
		String outputXml;
		try {
			outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE, STRUCTURE_TYPE, "
					+ "ATTRIBUTE_NAME, COLUMN_NAME FROM BPM_STAGGING_MAPPING_MASTER "
					+ "WHERE PROCESSDEFID='"+processDefId+"' and SOURCING_CHANNEL ='"+sourcingChannel+"'");		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"createTSLMMappingCache outputxml " + outputXml);
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
				if(TSLMMappingMap.containsKey(requestCategory+"#"+requestType)){
					Map<String, HashMap<String, String>> attribMap = TSLMMappingMap.get(requestCategory+"#"+requestType);
					if(attribMap.containsKey(structureType)){
						HashMap<String, String> attribList = attribMap.get(structureType);
						if(attribList.containsKey(attributeName)){
							attribList.put(attributeName + " AS " + attributeName + "_" + duplicate,columnName);
							duplicate++;
						} else {
							attribList.put(attributeName,columnName);
						}
						attribMap.put(structureType, attribList);
						TSLMMappingMap.put(requestCategory+"#"+requestType, attribMap);
					} else {
						HashMap<String, String> attribList = new HashMap<String, String>();
						attribList.put(attributeName,columnName);
						attribMap.put(structureType, attribList);
						TSLMMappingMap.put(requestCategory+"#"+requestType, attribMap);
					}			
				} else {
					HashMap<String, String> attribList = new HashMap<String, String>();
					attribList.put(attributeName,columnName);
					Map<String, HashMap<String, String>> attribMap = new HashMap<String, HashMap<String, String>>();
					attribMap.put(structureType, attribList);
					TSLMMappingMap.put(requestCategory+"#"+requestType, attribMap);
				}
			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"Value of TSLMMappingMap inside  is: " + TSLMMappingMap.toString());	
		} catch (Exception e) {			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}		
         return TSLMMappingMap;
	}
	

	
	
	public HashMap<String, HashMap<String, HashMap<String, String>>> getAttributeStructureMap() {
		return attributeStructureHashMap;
	}
	
	public void setAttributeStructureMap(HashMap<String, HashMap<String, HashMap<String, String>>> 
		attributeStructureHashMap) {
		this.attributeStructureHashMap = attributeStructureHashMap;
	}
}
