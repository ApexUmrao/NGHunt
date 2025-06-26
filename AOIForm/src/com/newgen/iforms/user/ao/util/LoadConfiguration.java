package com.newgen.iforms.user.ao.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.ao.util.LoadConfiguration;
import com.newgen.iforms.user.config.AOLogger;

public class LoadConfiguration{ 
	private String processDefId ;
	private static LoadConfiguration instance = null;	
	private HashMap<String, String> defaultMap = new HashMap<>();
	private HashMap<String, String> countryMap = new HashMap<>();
	private HashMap<String, String> fulfillmentDefaultMap = new HashMap<>();
	private HashMap<String, String> fcrDefault = new HashMap<>();
	static Logger aoLogger = AOLogger.getInstance().getLogger();
	
	public static LoadConfiguration getInstance(IFormReference formObject) {
		aoLogger.info("inside LoadConfiguration getInstance");
		if(instance == null){
			LoadConfiguration tempInstance = new LoadConfiguration();
			tempInstance.loadProcessDefId(formObject);
			tempInstance.loadSocketServiceConfig(formObject);
			tempInstance.loadCountryMap(formObject);
			tempInstance.loadFulfillmentValue(formObject);
			tempInstance.loadFcrdefaultvalue(formObject);
			instance = tempInstance;
		}	
		return instance;
	}
	
	private LoadConfiguration(){
		aoLogger.info("inside LoadConfiguration");		
	}
	
	private void loadProcessDefId(IFormReference formObject) {		 
		try {
			List<List<String>> tmpList = formObject.getDataFromDB("SELECT PROCESSDEFID FROM PROCESSDEFTABLE WHERE PROCESSNAME  = 'AO'");
			if (!tmpList.isEmpty()) {
				processDefId = ((tmpList.get(0)).get(0));
				aoLogger.info("loadProcessDefId: " + processDefId);
			}				
		} catch (Exception e) {
			aoLogger.error("Exception in setProperties: ", e);
		}
	}
	
	private void loadSocketServiceConfig(IFormReference formObject) {
		try {
			aoLogger.info("inside loadBPMServiceConfig");
			List tmpList = formObject.getDataFromDB("SELECT KEY, VALUE FROM BPM_SERVICE_CONFIG");
			aoLogger.info("select query loadSocketServiceConfig size: "+tmpList.size());
			if (tmpList.size()>0) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					defaultMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				aoLogger.info("Default map " + defaultMap.toString());
			}
		} catch (Exception e) {			
			aoLogger.debug("exception in setProperties: " + e, e);
		}
	}
	
	private void loadCountryMap(IFormReference formObject) {
		try {
			aoLogger.info("inside loadCountryMap");
			List tmpList = formObject.getDataFromDB("SELECT COUNTRY_CODE, COUNTRY FROM USR_0_COUNTRY_MAST");
			aoLogger.info("select query loadCountryMap size: "+tmpList.size());
			if (tmpList.size()>0) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					countryMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				aoLogger.info("country map " + countryMap.toString());
			}
		} catch (Exception e) {			
			aoLogger.debug("exception in loadCountryMap: ", e);
		}
	}
	private void loadFcrdefaultvalue(IFormReference formObject) {
		try {
			aoLogger.info("inside loadFcrdefaultvalue");
			List tmpList = formObject.getDataFromDB("SELECT NAME, VALUE FROM USR_0_DEFAULTVALUE_FCR");
			aoLogger.info("select query loadFcrdefaultvalue size: "+tmpList.size());
			if (tmpList.size()>0) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					fcrDefault.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				aoLogger.info("loadFcrdefaultvalue map " + fcrDefault.toString());
			}
		} catch (Exception e) {			
			aoLogger.debug("exception in loadCountryMap: ", e);
		}
	}
	//Added for fullfilment
	public String generateSysRefNumber(IFormReference formObject){
		String sysNum = "";
		try {
			List<List<String>> sOutput = formObject.getDataFromDB("SELECT AO_REFNO.NEXTVAL SYSREFNO FROM DUAL");
			sysNum = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(0).toString() : "";
		} catch (Exception e) {
			aoLogger.debug("generateSysRefNumber", e);
			e.printStackTrace();
		}
		return sysNum;
	}
	
	private void loadFulfillmentValue(IFormReference formObject) {
		try {
			aoLogger.info("inside fulfillmentDefaultMap");
			List tmpList = formObject.getDataFromDB("SELECT KEY, VALUE FROM BPM_CONFIG WHERE PROCESS_NAME='AO'");
			aoLogger.info("select query fulfillmentDefaultMap size: "+tmpList.size());
			if (tmpList.size()>0) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					fulfillmentDefaultMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				aoLogger.info("fulfillmentDefaultMap map " + defaultMap.toString());
			}
		} catch (Exception e) {			
			aoLogger.debug("exception in setProperties: " + e, e);
		}
	}
	public String getProcessDefId() {
		return processDefId;
	}
	
	public void setProcessDefID(String processDefId) {
		this.processDefId = processDefId;
	}
	
	public HashMap<String, String> getDefaultMap() {
		return defaultMap;
	}
	
	public void setDefaultMap(HashMap<String, String> defaultMap) {
		this.defaultMap = defaultMap;
	}
	
	public HashMap<String, String> getCountryMap() {
		return countryMap;
	}
	
	public void setCountryMap(HashMap<String, String> defaultMap) {
		this.countryMap = countryMap;
	}
	public HashMap<String, String> getFulfillmentValue() {
	 	   return fulfillmentDefaultMap;
		}
	public HashMap<String, String> getFcrValue() {
	 	   return fcrDefault;
		}
}
