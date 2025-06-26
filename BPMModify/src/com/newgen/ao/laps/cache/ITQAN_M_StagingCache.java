package com.newgen.ao.laps.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ITQAN_M_StagingCache {
	private HashMap<String, ArrayList<String>> wbgStagingMasterMap = new HashMap<String, ArrayList<String>>();
	private HashMap<String, CallValidation> validationMap = new HashMap<String, CallValidation>();


	private static ITQAN_M_StagingCache instance = new ITQAN_M_StagingCache();
	
	public static ITQAN_M_StagingCache getInstance() {
		return instance;
	}
	

	private ITQAN_M_StagingCache(){
		getWbgStagingMasterMap();
		getValidationMasterMap();
	}
	

	
	
	public Map<String, ArrayList<String>> getWbgStagingMasterMap(){
		if (wbgStagingMasterMap.isEmpty()) {
			try {
				createWbgStagingMasterMap();
			//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of createwbgStagingMasterMap is: " + wbgStagingMasterMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"error in getwbgStagingMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,e);
			}
		}
		return wbgStagingMasterMap;
	}
	
 
	public HashMap<String, CallValidation> getValidationMasterMap(){
		if (validationMap.isEmpty()) {
			try {
				createValidationMasterMap();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of createwbgValidationMasterMap is: " + validationMap.toString());
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"error in getwbgValidationMap: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,e);
			}
		}
		return validationMap;
	}
 
	private void createWbgStagingMasterMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside createwbgStagingMasterMap once");
		wbgStagingMasterMap = new HashMap<String, ArrayList<String>>();
		String outputXML = APCallCreateXML.APSelect("SELECT TAGNAME,TABLENAME,COLUMN_NAME,IS_DATE,"
				+ "STAGING_COLUMN_NAME,STAGING_TABLE,STRUCTURE_TYPE FROM BPM_WBG_AO_STAGING_MASTER");
	//	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String tagName = xp.getValueOf("TAGNAME", start, end);
			String tableName = xp.getValueOf("TABLENAME", start, end);
			String colName = xp.getValueOf("COLUMN_NAME", start, end);
			String isDate = xp.getValueOf("IS_DATE", start, end);
			String stagingColName = xp.getValueOf("STAGING_COLUMN_NAME", start, end);
			String stagingTable = xp.getValueOf("STAGING_TABLE", start, end);
			String structureType = xp.getValueOf("STRUCTURE_TYPE", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"structureType: " + tagName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"stagingTableName: " + tableName);
			ArrayList<String> value = new ArrayList<String>();
			value.add(tableName);
			value.add(colName);
			value.add(isDate);
			value.add(stagingColName);	
			value.add(stagingTable);
			value.add(structureType);	
			wbgStagingMasterMap.put(tagName, value);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of wbgStagingMasterMap: " + wbgStagingMasterMap.toString());
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside wbgStagingMasterMap " + wbgStagingMasterMap.toString());
	}
	
		
	private HashMap<String, CallValidation> createValidationMasterMap() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside createStagevalidationMap once");
		validationMap = new HashMap<String,CallValidation>();
		String outputXML = APCallCreateXML
				.APSelect("SELECT TAGNAME,ERRORCODE,ERRORDESCRIPTION,LENGTH_CHECK,MANDATORY_CHECK,"
						+ "STAGING_COLUMN_NAME,MINIMUM_LENGTH,MAXIMUM_LENGTH,IS_LOV,LOV_QUERY,IS_SPECIFIC_VALUE,"
						+ "SPECIFIC_VALUE,DATE_FORMAT FROM "
						+ "BPM_WBG_AO_REQ_VALIDATION_MASTER  ORDER BY TO_NUMBER(ERRORCODE)");
		/*LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"outputxml issss " + outputXML);*/
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {

			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String tagName = xp.getValueOf("TAGNAME", start, end);
			String errorDesc = xp.getValueOf("ERRORDESCRIPTION", start, end);
			String errorCode = xp.getValueOf("ERRORCODE", start, end);
			String lenCheck = xp.getValueOf("LENGTH_CHECK", start, end);
			String mandatoryCheck = xp.getValueOf("MANDATORY_CHECK", start, end);
			String colName = xp.getValueOf("STAGING_COLUMN_NAME", start, end);
			String minLength = xp.getValueOf("MINIMUM_LENGTH", start, end);
			String maxLength = xp.getValueOf("MAXIMUM_LENGTH", start, end);
			String isLovMand = xp.getValueOf("IS_LOV", start, end);
			String lovQuery = xp.getValueOf("LOV_QUERY", start, end);
			String isSpecValue = xp.getValueOf("IS_SPECIFIC_VALUE", start, end);
			String specValue = xp.getValueOf("SPECIFIC_VALUE", start, end);
			String dateFormat = xp.getValueOf("DATE_FORMAT", start, end);

			
			CallValidation obj = new CallValidation();
			obj.setErrorCode(errorCode); 
			obj.setErrorDesc(errorDesc);
			obj.setMaxLength(maxLength);
			obj.setIsMandateValue(mandatoryCheck);
			obj.setLengthCheck(lenCheck);
			obj.setMinLength(minLength);
			obj.setStagingColName(colName);	
			obj.setIsLOV(isLovMand);
			obj.setLOVQuery(lovQuery);	
			obj.setIsSpecValue(isSpecValue);
			obj.setSpecValue(specValue);
			obj.setDateFormat(dateFormat);
			validationMap.put(tagName, obj);
			
//			
//			ArrayList<callValidation> callMap = new ArrayList<callValidation>();
//			callMap.add(obj);
//			validationMap.put(tagName, callMap);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside validationMap " + validationMap.toString());

		return validationMap;
	}

	
	
}
