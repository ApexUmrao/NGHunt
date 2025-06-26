package com.newgen.iforms.user.tfo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.config.TFOLogger;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;


public class LoadConfiguration{
	private static LoadConfiguration instance = null;	
	private Map<String, String> defaultMap = new HashMap<>();
	private Map<String, String> tempGenIPPortMap = new HashMap<>();
	private Map<String, String> yesNoMap = new HashMap<>();
	private Map<String, String> amountCheckMap = new HashMap<>();
	private Map<String, String> tabDescIdMap = new HashMap<>();
	private Map<String, String> currencyDecimalMap = new HashMap<>();
	private Map<String, String> relatedPartyMap = new HashMap<>();
	private Map<String, HashMap<String, String>> routeToMap = new HashMap<>();
	private Map<String, HashMap<String, String>> systemActivityMap = new HashMap<>();
	private Map<String, LinkedHashMap<String, String>> decisionMap = new HashMap<>();
	private Map<String, HashMap<String, List<String>>> reasonMap = new HashMap<>();
	private Map<String, String> requestCategoryMap = new HashMap<>();
	Map<String, LinkedHashMap<String,String>> branchCodeMap = new HashMap();
	Map<String, String> referralMailMap = new HashMap<>();
	private Map<String,  List<String>> brmsMap = new HashMap<>();
	private Map<String,  List<String>> trsdPartyMap = new HashMap<>();
	private Map<String, String> tabHandlingMap = new HashMap<>();
	private Map<String, List<String>> createAmendCoreMap = new HashMap<>();
	private Map<String, String> pmRouteMap = new HashMap<>();
	private Map<String, HashMap<String, String>> partyTypeMastMap = new HashMap<>();
	private Map<String,  List<String>> amendPartyMap = new HashMap<>();
	private Map<String,Map<String,HashMap<String, String>>> protradeMappingMap = new HashMap<String,Map<String,HashMap<String, String>>>();
	private Map<String, String> protradeDateMap = new HashMap<String, String>();
	private static Map<String, String> protradeCLOBMasterMap = new HashMap<String, String>();
	private HashMap<String, ProtradeComplexMapping> protradeComplexMap = new HashMap<String, ProtradeComplexMapping>(); 
	private HashMap<String, ArrayList<String>> TraydStreamValueMap = new HashMap<String, ArrayList<String> >();  	//Traystream change |reyaz|atp-518|23-09-2024 
	private HashMap<String, String> bpmConfigValueMap = new HashMap<String, String>();  	//Traystream change |reyaz|atp-518|23-09-2024 
	private HashMap<String, String> autoDocChkValueMap = new HashMap<String, String>();  	//Traystream change |reyaz|atp-518|23-09-2024 





	public HashMap<String, ProtradeComplexMapping> getProtradeComplexMap() {
		return protradeComplexMap;
	}

	public void setProtradeComplexMap(
			HashMap<String, ProtradeComplexMapping> protradeComplexMap) {
		this.protradeComplexMap = protradeComplexMap;
	}

	public Map<String, List<String>> getCreateAmendCoreMap() {
		return createAmendCoreMap;
	}

	public void setCreateAmendCoreMap(Map<String, List<String>> createAmendCoreMap) {
		this.createAmendCoreMap = createAmendCoreMap;
	}

	static Logger tfoLogger = TFOLogger.getInstance().getLogger();

	public static LoadConfiguration getInstance(IFormReference formObject) {
		tfoLogger.info("inside LoadConfiguration getInstance");
		if(instance == null){
			LoadConfiguration tempInstance = new LoadConfiguration();
			tempInstance.loadDefaultMap(formObject);
			tempInstance.loadYesNoMap(formObject);
			tempInstance.loadTemplateGenConfig(formObject);
			tempInstance.loadAmountCheckMap(formObject);
			tempInstance.loadTabNameMap(formObject);
			tempInstance.loadRelatedPartiesMap(formObject);
			tempInstance.loadCurrDecimalMap(formObject);
			tempInstance.loadSystemActivityMap(formObject);
			tempInstance.loadDecisionMap(formObject);
			tempInstance.loadReasonMap(formObject);
			tempInstance.loadRouteToMap(formObject);
			tempInstance.loadReqCatMap(formObject);
			tempInstance.loadBPMServiceConfig(formObject);
			tempInstance.loadBranchCodeMap(formObject);
			tempInstance.loadReferralMailMap(formObject);
			tempInstance.loadBrmsMap(formObject);
			tempInstance.loadTrsdPartyMap(formObject);
			tempInstance.loadTabHandlingMap(formObject);
			tempInstance.loadCreateAmendCoreMap(formObject);
			tempInstance.loadPMRouteMap(formObject); 	//CODE BY MOKSH
			tempInstance.loadPartyMap(formObject);
			tempInstance.loadAmendPartyMap(formObject);
			tempInstance.loadProtradeMappingCache(formObject);
			tempInstance.loadProtradeDateMap(formObject);
			tempInstance.loadProtradeCLOBMap(formObject);
			tempInstance.loadProtradeComplexMap(formObject);
			tempInstance.loadTraydstreamDefaultCache(formObject); //Traystream change |reyaz|atp-518|23-09-2024 
			tempInstance.loadBpmConfigCache(formObject); //Traystream change |reyaz|atp-518|23-09-2024 
			tempInstance.loadAutoDocChkCache(formObject); //Traystream change |reyaz|atp-518|23-09-2024 
			instance = tempInstance;				
		}			
		return instance;
	}

	public Map<String, String> getProtradeDateMap() {
		return protradeDateMap;
	}

	public void setProtradeDateMap(Map<String, String> protradeDateMap) {
		this.protradeDateMap = protradeDateMap;
	}

	private LoadConfiguration(){
		tfoLogger.info("inside LoadConfiguration");		
	}

	private void loadDefaultMap(IFormReference formObject) {
		List tmpList; 
		try {
			tmpList = formObject.getDataFromDB("SELECT DEF_NAME,DEF_VALUE FROM TFO_DJ_DEFAULT_MAST");
			tfoLogger.info("select query BPM_SERVICE_CONFIG size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					defaultMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("defaultMap : " + defaultMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadYesNoMap(IFormReference formObject) {
		List tmpList; 
		try {
			tmpList = formObject.getDataFromDB("SELECT UNIQUE_ID,YES_NO FROM USR_0_YES_NO");
			tfoLogger.info("select query yes no size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					yesNoMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("yesNoMap : " + yesNoMap.toString());
			}
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadTemplateGenConfig(IFormReference formObject) {
		List tmpList;
		try {			
			tmpList = formObject.getDataFromDB("select key,value from ng_utility_tmp_gen_config order by 1");
			tfoLogger.info("select query yes no size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					tempGenIPPortMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("tempGenIPPortMap : " + tempGenIPPortMap.toString());
			}
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadAmountCheckMap(IFormReference formObject) {
		List tmpList;
		try {
			tmpList = formObject.getDataFromDB("SELECT Queue,CHK_AMT FROM TFO_DJ_AMT_CHK_MAST");
			tfoLogger.info("select query check amount size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					amountCheckMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("amountCheckMap : " + amountCheckMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadTabNameMap(IFormReference formObject) {
		List tmpList;
		try {
			tmpList = formObject.getDataFromDB("SELECT TAB_ID,TAB_NAME FROM TFO_DJ_TAB_LVW_MAST");
			tfoLogger.info("select query tab name size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					tabDescIdMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("tabDescIdMap : " + tabDescIdMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadRelatedPartiesMap(IFormReference formObject) {
		List tmpList; 
		try {
			tmpList = formObject.getDataFromDB("SELECT CODE,DESCRIPTION FROM TFO_DJ_RELATED_PARTIES");
			tfoLogger.info("select query related party size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					relatedPartyMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("relatedPartyMap : " + relatedPartyMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadTabHandlingMap(IFormReference formObject) {
		List tmpList; 
		try {
			tmpList = formObject.getDataFromDB("select  request_category,activity_name,tab_name,is_req from tfo_dj_tab_handling_mast");
			tfoLogger.info("select query related patab Handling  size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String key=((String) ((List) tmpList.get(counter)).get(0))+
							((String) ((List) tmpList.get(counter)).get(1))+((String) ((List) tmpList.get(counter)).get(2));
					String value=((String) ((List) tmpList.get(counter)).get(3));
					tabHandlingMap.put(key,value);
				}
				tfoLogger.info("tabHandlingMap : " + tabHandlingMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadCurrDecimalMap(IFormReference formObject) {
		List tmpList; 
		try {
			tmpList = formObject.getDataFromDB("SELECT CCY_CODE,CCY_DECIMALS FROM TFO_DJ_CURR_DECIMALS");
			tfoLogger.info("select query currency Decimal Map size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					currencyDecimalMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("currencyDecimalMap : " + currencyDecimalMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadSystemActivityMap(IFormReference formObject) {
		List tmpList;
		String firstVal;
		String secondVal;
		String thirdVal;
		try {
			tmpList = formObject.getDataFromDB("SELECT WS_NAME,ACTIVITY_CODE,ACTIVITY_NAME FROM TFO_DJ_PMPC_ACTIVITY_MASTER ORDER BY WS_NAME");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					firstVal =  (String) ((List) tmpList.get(counter)).get(0);
					secondVal =  (String) ((List) tmpList.get(counter)).get(1);
					thirdVal =  (String) ((List) tmpList.get(counter)).get(2);
					if(systemActivityMap.containsKey(firstVal))
					{
						HashMap<String, String> innerMap = systemActivityMap.get(firstVal);
						innerMap.put(secondVal, thirdVal);
						systemActivityMap.put(firstVal, innerMap);
					}else 
					{
						HashMap<String, String> innerMap = new HashMap<>();
						innerMap.put(secondVal, thirdVal);
						systemActivityMap.put(firstVal, innerMap);
					}
				}
				tfoLogger.info("systemActivityMap Map : " + systemActivityMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadRouteToMap(IFormReference formObject) {
		List tmpList;
		String firstVal;
		String secondVal;
		String thirdVal;
		try {
			tmpList = formObject.getDataFromDB("SELECT WS_NAME,UNIQUE_ID,ROUTE_TO FROM TFO_DJ_ROUTE_TO_MAST ORDER BY WS_NAME");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					firstVal =  (String) ((List) tmpList.get(counter)).get(0);
					secondVal =  (String) ((List) tmpList.get(counter)).get(1);
					thirdVal =  (String) ((List) tmpList.get(counter)).get(2);
					if(routeToMap.containsKey(firstVal))
					{
						HashMap<String, String> innerMap = routeToMap.get(firstVal);						 
						innerMap.put(secondVal, thirdVal);
						routeToMap.put(firstVal, innerMap);
					} 
					else 
					{
						HashMap<String, String> innerMap = new HashMap<>();
						innerMap.put(secondVal, thirdVal);
						routeToMap.put(firstVal, innerMap);
					}
				}
				tfoLogger.info("routeToMap Map : " + routeToMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadDecisionMap(IFormReference formObject) {
		List tmpList;
		String firstVal;
		String secondVal;
		String thirdVal;
		try {
			tmpList = formObject.getDataFromDB("Select WS_NAME,DEC_CODE,DECISION FROM TFO_DJ_DEC_MASTER ORDER BY SNO");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					firstVal =  (String) ((List) tmpList.get(counter)).get(0);
					secondVal =  (String) ((List) tmpList.get(counter)).get(1);
					thirdVal =  (String) ((List) tmpList.get(counter)).get(2);
					if(decisionMap.containsKey(firstVal))
					{
						LinkedHashMap<String, String> innerMap = decisionMap.get(firstVal);						
						innerMap.put(secondVal, thirdVal);
						decisionMap.put(firstVal, innerMap);
					}
					else 
					{
						LinkedHashMap<String, String> innerMap = new LinkedHashMap<>();
						innerMap.put(secondVal, thirdVal);
						decisionMap.put(firstVal, innerMap);
					}
				}
				tfoLogger.info("decisionMap Map : " + decisionMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadReasonMap(IFormReference formObject) {
		List tmpList;
		String firstVal;
		String secondVal;
		String thirdVal;
		try {
			tmpList = formObject.getDataFromDB("Select WS_NAME, DEC_CODE, REASON from TFO_DJ_REASON_MASTER ORDER BY SNO");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					firstVal =  (String) ((List) tmpList.get(counter)).get(0);
					secondVal =  (String) ((List) tmpList.get(counter)).get(1);
					thirdVal =  (String) ((List) tmpList.get(counter)).get(2);
					if(reasonMap.containsKey(firstVal))
					{
						HashMap<String, List<String>> innerMap  = reasonMap.get(firstVal);
						if(innerMap.containsKey(secondVal)) 
						{
							List<String> list = innerMap.get(secondVal);
							list.add(thirdVal);
							innerMap.put(secondVal, list);
						} 
						else 
						{
							List<String> list = new  ArrayList<>();
							list.add(thirdVal);
							innerMap.put(secondVal, list);
						}
						reasonMap.put(firstVal, innerMap);
					}
					else 
					{
						HashMap<String, List<String>> innerMap = new HashMap<>();
						List<String> list = new  ArrayList<>();
						list.add(thirdVal);
						innerMap.put(secondVal, list); 
						reasonMap.put(firstVal, innerMap);
					}
				}
				tfoLogger.info("reasonMap Map : " + reasonMap.toString());
			}

		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadReqCatMap(IFormReference formObject) {
		List tmpList;
		try {
			tmpList = formObject.getDataFromDB("SELECT REQUEST_CATEGORY_ID, REQUEST_CATEGORY FROM TFO_DJ_REQUEST_CATEGORY_MAST");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					requestCategoryMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("requestCategoryMap Map : " + requestCategoryMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}

	private void loadBPMServiceConfig(IFormReference formObject) {
		try {
			tfoLogger.info("inside loadBPMServiceConfig");
			List tmpList = formObject.getDataFromDB("SELECT KEY, VALUE FROM BPM_SERVICE_CONFIG");
			tfoLogger.info("select query BPM_SERVICE_CONFIG size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					defaultMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("Default map " + defaultMap.toString());
			}
		} catch (Exception e) {			
			tfoLogger.debug("exception in setProperties: " + e, e);
		}
	}
	public Map<String, String> getReferralMailMap() {
		return referralMailMap;
	}

	public void setReferralMailMap(Map<String, String> referralMailMap) {
		this.referralMailMap = referralMailMap;
	}

	private void loadReferralMailMap(IFormReference formObject) {
		try {
			tfoLogger.info("inside loadReferralMailMap");
			List tmpList = formObject.getDataFromDB("Select STAKEHOLDER, REFEMAILADDRESSEDTO from TFO_DJ_EMAIL_REF_MAST");
			tfoLogger.info("select query loadReferralMailMap size: "+tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					referralMailMap.put(((String) ((List) tmpList.get(counter)).get(0)),((String) ((List) tmpList.get(counter)).get(1)));
				}
				tfoLogger.info("referralMailMap map " + referralMailMap.toString());
			}
		} catch (Exception e) {			
			tfoLogger.debug("exception in setProperties: " + e, e);
		}
	}

	private void loadBranchCodeMap(IFormReference formObject) {
		tfoLogger.info("inside loadBPMServiceConfig");
		List tmpList;
		String firstVal;
		String secondVal;
		String thirdVal;
		try {
			tmpList = formObject.getDataFromDB("SELECT TXN_BRANCH_CODE,BRANCH_CODE,IS_PROTECTED FROM USR_0_BRANCH_CODE_MAST ORDER BY SNO");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					firstVal =  (String) ((List) tmpList.get(counter)).get(0);
					secondVal =  (String) ((List) tmpList.get(counter)).get(1);
					thirdVal =  (String) ((List) tmpList.get(counter)).get(2);
					if(branchCodeMap.containsKey(firstVal))
					{
						LinkedHashMap<String, String> innerMap = branchCodeMap.get(firstVal);						
						innerMap.put(secondVal, thirdVal);
						branchCodeMap.put(firstVal, innerMap);
					}
					else 
					{
						LinkedHashMap<String, String> innerMap = new LinkedHashMap<String, String>();
						innerMap.put(secondVal, thirdVal);
						branchCodeMap.put(firstVal, innerMap);
					}
				}
				tfoLogger.info("decisionMap Map : " + branchCodeMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in",e);
		}
	}
	public Map<String, String> getDefaultMap() {
		return defaultMap;
	}

	public void setDefaultMap(Map<String, String> defaultMap) {
		this.defaultMap = defaultMap;
	}

	public Map<String, String> getYesNoMap() {
		return yesNoMap;
	}

	public void setYesNoMap(Map<String, String> yesNoMap) {
		this.yesNoMap = yesNoMap;
	}

	public Map<String, String> getTabDescIdMap() {
		return tabDescIdMap;
	}

	public void setTabDescIdMap(Map<String, String> tabDescIdMap) {
		this.tabDescIdMap = tabDescIdMap;
	}

	public Map<String, String> getAmountCheckMap() {
		return amountCheckMap;
	}

	public void setAmountCheckMap(Map<String, String> amountCheckMap) {
		this.amountCheckMap = amountCheckMap;
	}


	public Map<String, String> getRelatedPartyMap() {
		return relatedPartyMap;
	}

	public void setRelatedPartyMap(Map<String, String> relatedPartyMap) {
		this.relatedPartyMap = relatedPartyMap;
	}

	public Map<String, String> getCurrencyDecimalMap() {
		return currencyDecimalMap;
	}

	public void setCurrencyDecimalMap(Map<String, String> currencyDecimalMap) {
		this.currencyDecimalMap = currencyDecimalMap;
	}

	public Map<String,  HashMap<String, String>> getSystemActivityMap() {
		return systemActivityMap;
	}

	public void setSystemActivityMap(Map<String,  HashMap<String, String>> systemActivityMap) {
		this.systemActivityMap = systemActivityMap;
	}

	public Map<String, HashMap<String, String>> getRouteToMap() {
		return routeToMap;
	}

	public void setRouteToMap(Map<String, HashMap<String, String>> routeToMap) {
		this.routeToMap = routeToMap;
	}

	public Map<String, LinkedHashMap<String, String>> getDecisionMap() {
		return decisionMap;
	}

	public void setDecisionMap(Map<String, LinkedHashMap<String, String>> decisionMap) {
		this.decisionMap = decisionMap;
	}

	public Map<String, HashMap<String, List<String>>> getReasonMap() {
		return reasonMap;
	}

	public void setReasonMap(Map<String, HashMap<String, List<String>>> reasonMap) {
		this.reasonMap = reasonMap;
	}

	public Map<String, String> getRequestCategoryMap() {
		return requestCategoryMap;
	}

	public void setRequestCategoryMap(Map<String, String> requestCategoryMap) {
		this.requestCategoryMap = requestCategoryMap;
	}

	public Map<String, String> getTempGenIPPortMap() {
		return tempGenIPPortMap;
	}

	public void setTempGenIPPortMap(Map<String, String> tempGenIPPortMap) {
		this.tempGenIPPortMap = tempGenIPPortMap;
	}

	public Map<String, List<String>> getBrmsMap() {
		return brmsMap;
	}

	public void setBrmsMap(Map<String, List<String>> brmsMap) {
		this.brmsMap = brmsMap;
	}

	public Map<String, List<String>> getTrsdPartyMap() {
		return trsdPartyMap;
	}


	public void setTrsdPartyMap(Map<String, List<String>> trsdPartyMap) {
		this.trsdPartyMap = trsdPartyMap;
	}

	public Map<String, List<String>> getAmendPartyMap() {
		return amendPartyMap;
	}

	public Map<String, Map<String, HashMap<String, String>>> getProtradeMappingMap() {
		return protradeMappingMap;
	}

	public void setProtradeMappingMap(
			Map<String, Map<String, HashMap<String, String>>> protradeMappingMap) {
		this.protradeMappingMap = protradeMappingMap;
	}

	public void setAmendPartyMap(Map<String, List<String>> amendPartyMap) {
		this.amendPartyMap = amendPartyMap;
	}

	public Map<String, String> getTabHandlingMap() {
		return tabHandlingMap;
	}

	public void setTabHandlingMap(Map<String, String> tabHandlingMap) {
		this.tabHandlingMap = tabHandlingMap;
	}

	public Map<String, LinkedHashMap<String, String>> getBranchCodeMap() {
		return branchCodeMap;
	}

	public void setBranchCodeMap(
			Map<String, LinkedHashMap<String, String>> branchCodeMap) {
		this.branchCodeMap = branchCodeMap;
	}


	public Map<String, HashMap<String, String>> getPartyTypeMastMap() {
		return partyTypeMastMap;
	}

	public void setPartyTypeMastMap(
			Map<String, HashMap<String, String>> partyTypeMastMap) {
		this.partyTypeMastMap = partyTypeMastMap;
	}

	//CODE BY MOKSH
	public Map<String, String> getPmRouteMap() {
		return pmRouteMap;
	}

	public void setPmRouteMap(Map<String, String> pmRouteMap) {
		this.pmRouteMap = pmRouteMap;
	}
	//END OF CODE BY MOKSH
	private void loadBrmsMap(IFormReference formObject) {
		tfoLogger.info("inside loadBrmsMap");
		List tmpList;
		String processType   =null;
		String billType      =null;
		String discrpanyInst =null;
		String settlInstr    =null;
		String maturityDate    =null;
		String reqCat=null;
		String reqType=null;
		String ifSightBill=null;
		String rule=null;
		String targetQueue1=null;
		String targetQueue2=null;

		try {
			tmpList = formObject.getDataFromDB("select PROCESS_TYPE, REQUEST_CATEGORY_CODE, REQUEST_TYPE_CODE, "
					+ "BILL_TYPE, IF_SIGHT_BILL, DISCREPANCY_INSTRUCTION, SETTLEMENT_INSTRUCTION, MATURITY_DATE, "
					+ "RULE_NO, TARGET_QUEUE, TARGET_QUEUE_2 FROM TFO_DJ_BRMS_MASTER");
			tfoLogger.info("inside loadBrmsMap"+tmpList.size());

			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					processType =  (String) ((List) tmpList.get(counter)).get(0);
					reqCat =  (String) ((List) tmpList.get(counter)).get(1);
					reqType =  (String) ((List) tmpList.get(counter)).get(2);
					billType =  (String) ((List) tmpList.get(counter)).get(3);
					ifSightBill =  (String) ((List) tmpList.get(counter)).get(4);
					discrpanyInst =  (String) ((List) tmpList.get(counter)).get(5);
					settlInstr =  (String) ((List) tmpList.get(counter)).get(6);
					maturityDate =  (String) ((List) tmpList.get(counter)).get(7);
					rule =  (String) ((List) tmpList.get(counter)).get(8);
					targetQueue1 =  (String) ((List) tmpList.get(counter)).get(9);
					targetQueue2 =  (String) ((List) tmpList.get(counter)).get(10);
					String key = processType+"#"+reqCat+"#"+reqType+"#"+billType+"#"+ifSightBill+"#"+discrpanyInst+"#"+settlInstr
							+"#"+maturityDate;

					ArrayList list = new ArrayList<String>();
					list.add(rule);
					list.add(targetQueue1);
					list.add(targetQueue2);
					brmsMap.put(key, list);
				}
				tfoLogger.info("loadBrmsMap Map : " + brmsMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in",e);
		}
	}

	private void loadTrsdPartyMap(IFormReference formObject) {
		tfoLogger.info("Inside loadTrsdPartyMap");
		List tmpList;
		String requestCategoryCode   = null;
		String partyType      = null;

		try {
			tmpList = formObject.getDataFromDB("select  REQUEST_CATEGORY_CODE, PARTY_TYPE "
					+ "FROM tfo_dj_trsd_default_party_mast");
			tfoLogger.info("inside loadTrsdPartyMap"+tmpList.size());

			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					requestCategoryCode =  (String) ((List) tmpList.get(counter)).get(0);
					partyType =  (String) ((List) tmpList.get(counter)).get(1);
					if(trsdPartyMap.containsKey(requestCategoryCode)){
						ArrayList list=(ArrayList) trsdPartyMap.get(requestCategoryCode);
						list.add(partyType);
						trsdPartyMap.put(requestCategoryCode, list);
					}else{
						ArrayList list = new ArrayList<String>();
						list.add(partyType);
						trsdPartyMap.put(requestCategoryCode, list);
					}
				}
				tfoLogger.info("trsdPartyMap Map : " + trsdPartyMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in",e);
		}
	}

	private void loadCreateAmendCoreMap(IFormReference formObject){
		tfoLogger.info("Inside loadCreateAmendCoreMap");
		List tmpList;
		String req_cat = null;
		String req_type = null;
		String processing_sys = null;
		String field_value = null;
		String field_state = null;
		try{
			tmpList=formObject.getDataFromDB("select req_cat,req_type,processing_sys,field_value,field_state"
					+ " from tfo_dj_create_amend_core_mast");
			tfoLogger.info("Inside loadCreateAmendCoreMap"+tmpList);
			if (!tmpList.isEmpty()) {
				for(int counter=0 ; counter<tmpList.size(); counter++){
					req_cat =  (String) ((List) tmpList.get(counter)).get(0);
					req_type =  (String) ((List) tmpList.get(counter)).get(1);
					processing_sys =  (String) ((List) tmpList.get(counter)).get(2);
					field_value =  (String) ((List) tmpList.get(counter)).get(3);
					field_state =  (String) ((List) tmpList.get(counter)).get(4);
					String key = req_cat+"#"+req_type+"#"+processing_sys;

					ArrayList list = new ArrayList<String>();
					list.add(field_value);
					list.add(field_state);
					createAmendCoreMap.put(key, list);			
				}
			}		
		}
		catch(Exception e){
			tfoLogger.error("Exception in",e);
		}
	}
	//CODE BY MOKSH
	private void loadPMRouteMap(IFormReference formObject) {
		tfoLogger.info("inside loadPMRouteMap");
		List tmpList;
		String reqCat = null;
		String reqType = null;
		String createAmendCnt = null;
		String createAmendSta = null;
		String processingSys = null;
		String sendNoti = null;
		try {
			tmpList = formObject.getDataFromDB("SELECT REQUEST_CATEGORY_CODE, REQUEST_TYPE_CODE, CREATE_AMEND_CNTRCT_FCUBS,"+ 
					"CREATE_AMEND_STATUS_FCUBS, PROCESSING_SYSTEM, SEND_NOTIFICATION from TFO_DJ_PM_ROUTE_MAST");
			tfoLogger.info("inside loadPMRouteMap"+tmpList.size());

			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					reqCat =  (String) ((List) tmpList.get(counter)).get(0);
					reqType =  (String) ((List) tmpList.get(counter)).get(1);
					createAmendCnt =  (String) ((List) tmpList.get(counter)).get(2);
					createAmendSta =  (String) ((List) tmpList.get(counter)).get(3);
					processingSys =  (String) ((List) tmpList.get(counter)).get(4);
					sendNoti =  (String) ((List) tmpList.get(counter)).get(5);
					String key = reqCat+"#"+reqType+"#"+createAmendCnt+"#"+createAmendSta+"#"+processingSys;

					pmRouteMap.put(key, sendNoti);
				}
				tfoLogger.info("loadPMRouteMap Map : " + pmRouteMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in",e);
		}
	}

	private void loadAmendPartyMap(IFormReference formObject){
		tfoLogger.info("Inside loadAmendPartyMap");
		List amendList;
		String req_cat = null;
		String req_type = null;
		String prod_type = null;
		String purpose_message = null;
		String trn_third_party = null;
		String mode_of_gtee = null;
		String party_type = null;
		String source_channel=null;

		try{
			amendList=formObject.getDataFromDB("select sno,request_category_code,request_type_code,product_type,purpose_message,trn_third_party,"
					+ " mode_of_gtee,party_type,source_channel from tfo_dj_amendment_party");
			tfoLogger.info("Inside loadAmendPartyMap"+amendList);
			if (!amendList.isEmpty()) {
				for(int i=0 ; i<amendList.size(); i++){
					tfoLogger.info("The value of i is :"+i);
					req_cat =  (String) ((List) amendList.get(i)).get(1);
					req_type =  (String) ((List) amendList.get(i)).get(2);
					prod_type =  (String) ((List) amendList.get(i)).get(3);
					purpose_message = (String) ((List) amendList.get(i)).get(4);
					trn_third_party = (String) ((List) amendList.get(i)).get(5);
					mode_of_gtee = (String) ((List) amendList.get(i)).get(6);
					party_type = (String) ((List) amendList.get(i)).get(7);
					source_channel=(String) ((List) amendList.get(i)).get(8);

					String key = req_cat+"#"+req_type+"#"+prod_type+"#"+purpose_message+"#"+trn_third_party+"#"+mode_of_gtee+"#"+source_channel;
					ArrayList list =null;
					if(amendPartyMap.containsKey(key)){
						list=(ArrayList) amendPartyMap.get(key);
						list.add(party_type);
						amendPartyMap.put(key, list);

					}
					else{
						list = new ArrayList<String>();
						list.add(party_type);
						amendPartyMap.put(key, list);
					}
					tfoLogger.info("The size of map is :"+amendPartyMap.size());
				}		
			}
		}
		catch(Exception e){
			tfoLogger.error("Exception in",e);
		}
	}

	private void loadPartyMap(IFormReference formObject) { //sheenu
		List tmpList;
		String firstVal;
		String secondVal;
		String thirdVal;
		try {
			tmpList = formObject.getDataFromDB("SELECT REQUEST_CATEGORY_CODE,PARTY_TYPE_CODE,PARTY_TYPE_DESC FROM TFO_DJ_PARTY_TYPE_MASTER");
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					firstVal =  (String) ((List) tmpList.get(counter)).get(0);
					secondVal =  (String) ((List) tmpList.get(counter)).get(1);
					thirdVal =  (String) ((List) tmpList.get(counter)).get(2);
					if(partyTypeMastMap.containsKey(firstVal))
					{
						HashMap<String, String> innerMap = partyTypeMastMap.get(firstVal);						 
						innerMap.put(secondVal, thirdVal);
						partyTypeMastMap.put(firstVal, innerMap);
					} 
					else 
					{
						HashMap<String, String> innerMap = new HashMap<>();
						innerMap.put(secondVal, thirdVal);
						partyTypeMastMap.put(firstVal, innerMap);
					}
				}
				tfoLogger.info("partyTypeMastMap Map : " + partyTypeMastMap.toString());
			}				
		} catch (Exception e) {
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadProtradeDateMap(IFormReference formObject) {
		List tmpList;
		try{
			tmpList=formObject.getDataFromDB("SELECT ATTRIBUTE_NAME, IS_DATE "
					+ "FROM TFO_DJ_PROTRADE_DATE_MASTER");
			tfoLogger.info( "outputxml issss " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String attrName =(String) ((List) tmpList.get(counter)).get(0);
					String isDate = (String) ((List) tmpList.get(counter)).get(1);
					tfoLogger.info( "attrName: " + attrName);
					tfoLogger.info("isDate: " + isDate);
					protradeDateMap.put(attrName, isDate);
					tfoLogger.info(  "Value of protradeDateMap: " + protradeDateMap.toString());
				}
			}
			tfoLogger.info( "inside createProtradeDateMap " + protradeDateMap.toString());
		} catch (Exception e) {			
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	private void loadProtradeCLOBMap(IFormReference formObject) {
		List tmpList;
		try{
			tmpList=formObject.getDataFromDB("select ATTRIBUTE_NAME,IS_CLOB from TFO_DJ_PROTRADE_CLOB_MASTER");
			tfoLogger.info( "outputxml issss " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String attrName =(String) ((List) tmpList.get(counter)).get(0);
					String isDate = (String) ((List) tmpList.get(counter)).get(1);
					tfoLogger.info( "attrName: " + attrName);
					tfoLogger.info("isDate: " + isDate);
					protradeCLOBMasterMap.put(attrName, isDate);
					tfoLogger.info(  "Value of protradeCLOBMasterMap: " + protradeCLOBMasterMap.toString());
				}
			}
			tfoLogger.info( "inside protradeCLOBMasterMap " + protradeCLOBMasterMap.toString());
		} catch (Exception e) {			
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	public static Map<String, String> getProtradeCLOBMasterMap() {
		return protradeCLOBMasterMap;
	}

	public static void setProtradeCLOBMasterMap(
			Map<String, String> protradeCLOBMasterMap) {
		LoadConfiguration.protradeCLOBMasterMap = protradeCLOBMasterMap;
	}

	private void loadProtradeMappingCache(IFormReference formObject) {
		List tmpList;
		int duplicate = 1;
		try {

			tmpList=formObject.getDataFromDB("SELECT REQUEST_CATEGORY,REQUEST_TYPE,PROCESS_TYPE,STRUCTURE_TYPE,"
					+ "ATTRIBUTE_NAME,COLUMN_NAME FROM TFO_DJ_CHILD_MAPPING_MASTER");

			tfoLogger.info("createProtradeMappingCache outputxml " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String requestCategory = (String) ((List) tmpList.get(counter)).get(0);
					String requestType = (String) ((List) tmpList.get(counter)).get(1);
					String processType = (String) ((List) tmpList.get(counter)).get(2);
                    String structureType = (String) ((List) tmpList.get(counter)).get(3);
					String attributeName = (String) ((List) tmpList.get(counter)).get(4);
					String columnName = (String) ((List) tmpList.get(counter)).get(5);
					String key = requestCategory+"#"+requestType+"#"+processType;
					if(protradeMappingMap.containsKey(key)){
						Map<String, HashMap<String, String>> attribMap = protradeMappingMap.get(key);
						if(attribMap.containsKey(structureType)){
							HashMap<String, String> attribList = attribMap.get(structureType);
							if(attribList.containsKey(attributeName)){
								attribList.put(attributeName + " AS " + attributeName + "_" + duplicate,columnName);
								duplicate++;
							} else {
								attribList.put(attributeName,columnName);
							}
							attribMap.put(structureType, attribList);
							protradeMappingMap.put(key, attribMap);
						} else {
							HashMap<String, String> attribList = new HashMap<String, String>();
							attribList.put(attributeName,columnName);
							attribMap.put(structureType, attribList);
							protradeMappingMap.put(key, attribMap);
						}			
					} else {
						HashMap<String, String> attribList = new HashMap<String, String>();
						attribList.put(attributeName,columnName);
						Map<String, HashMap<String, String>> attribMap = new HashMap<String, HashMap<String, String>>();
						attribMap.put(structureType, attribList);
						protradeMappingMap.put(key, attribMap);
					}
				}
				//			tfoLogger.info(	"Value of protradeMappingMap inside  is: " + protradeMappingMap.toString());	
			}} catch (Exception e) {			
				tfoLogger.error("Exception in setProperties: ", e);
			}		
	}

	private void loadProtradeComplexMap(IFormReference formObject)  {
		List tmpList;
		ProtradeComplexMapping pcm;
		try {

			tmpList=formObject.getDataFromDB("SELECT STRUCTURE_TYPE, COMPLEX_VAR_NAME, STAGING_TABLE_NAME, "
					+ "DELETE_PREV_ENTRY, COMPLEX_TABLE_NAME, MAPPED_ATTRIBUTE "
					+ "FROM TFO_DJ_PROTRADE_COMPLEX_MASTER");

			tfoLogger.info("createProtradeMappingCache outputxml " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String requestCategory = (String) ((List) tmpList.get(counter)).get(0);

					String structureType = (String) ((List) tmpList.get(counter)).get(0);
					String complexVarName =(String) ((List) tmpList.get(counter)).get(1);
					String stagingTableName = (String) ((List) tmpList.get(counter)).get(2);
					String dltPrvEntry =(String) ((List) tmpList.get(counter)).get(3);
					String complexTableName = (String) ((List) tmpList.get(counter)).get(4);
					String mappingAttr = (String) ((List) tmpList.get(counter)).get(5);
					tfoLogger.info( "structureType: " + structureType);
					tfoLogger.info( "complexVarName: " + complexVarName);
					tfoLogger.info( "stagingTableName: " + stagingTableName);
					tfoLogger.info( "dltPrvEntry: " + dltPrvEntry);
					tfoLogger.info( "complexTableName: " + complexTableName);
					tfoLogger.info( "mappingAttr: " + mappingAttr);
					pcm = new ProtradeComplexMapping();
					pcm.setComplexTableName(complexTableName);
					pcm.setComplexVarName(complexVarName);
					pcm.setStagingTableName(stagingTableName);
					pcm.setDeletePreviousEntry(dltPrvEntry);
					pcm.setMappingAttribute(mappingAttr);

					protradeComplexMap.put(structureType, pcm);
				}
			}
		}catch (Exception e) {			
			tfoLogger.error("Exception in setProperties: ", e);
		}
	}
	
	//Traystream change |reyaz|atp-518|23-09-2024 start
	public HashMap<String, ArrayList<String>> getTraydStreamValueMap() {
	 	   return TraydStreamValueMap;
		}
	
	private void loadTraydstreamDefaultCache(IFormReference formObject) {
		List tmpList;
		try {
			
			tmpList=formObject.getDataFromDB("SELECT SOURCE,BRANCHID,TENANTID,UPLOADSCOUNT,TXNTYPE,DOCTYPE,REQUEST_CATEGORY, REQUEST_TYPE,"
					+ "PROCESS_TYPE,TARGET_WORKSTEP,TS_REQ FROM USR_0_TRAYDSTREAM_DEFAULT_MASTER");
			tfoLogger.info("loadTraydstreamDefaultCache outputxml " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String source =(String) ((List) tmpList.get(counter)).get(0);
					String branchId =(String) ((List) tmpList.get(counter)).get(1);
					String tenantId =(String) ((List) tmpList.get(counter)).get(2);
					String uploadsCount =(String) ((List) tmpList.get(counter)).get(3);
					String txnType =(String) ((List) tmpList.get(counter)).get(4);
					String docType = (String) ((List) tmpList.get(counter)).get(5);
					String requestCategory  =(String) ((List) tmpList.get(counter)).get(6);
					String requestType =(String) ((List) tmpList.get(counter)).get(7);
					String processType =(String) ((List) tmpList.get(counter)).get(8);
					String targetWS = (String) ((List) tmpList.get(counter)).get(9);
					String tsReq = (String) ((List) tmpList.get(counter)).get(10);
					String key=processType+"#"+requestCategory+"#"+requestType+"#"+tsReq;
					ArrayList ls = new ArrayList();
					ls.add(source);
					ls.add(branchId);
					ls.add(tenantId);
					ls.add(uploadsCount);
					ls.add(txnType);
					ls.add(docType);
					ls.add(targetWS);
					TraydStreamValueMap.put(key, ls);
				}
			}
		} catch (Exception e) {
			tfoLogger.error("Exception in loadTraydstreamDefaultCache : ", e);
		}
	}
	
	public HashMap<String, String> getloadBpmConfigCache() {
	 	   return bpmConfigValueMap;
		}
	private void loadBpmConfigCache(IFormReference formObject) {
		List tmpList;
		try {
			tmpList=formObject.getDataFromDB("SELECT KEY, VALUE FROM BPM_CONFIG WHERE PROCESS_NAME ='TFO'");
			tfoLogger.info("loadBpmConfigCache outputxml " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					bpmConfigValueMap.put((String) ((List) tmpList.get(counter)).get(0), (String) ((List) tmpList.get(counter)).get(1));
				}
			}
		} catch (Exception e) {
			tfoLogger.error("Exception in loadBpmConfigCache : ", e);
		}
	}
	
	public HashMap<String, String> getAutoDocChkCache() {
	 	   return autoDocChkValueMap;
		}
	private void loadAutoDocChkCache(IFormReference formObject) {
		List tmpList;
		try {
			tmpList=formObject.getDataFromDB("SELECT CID, REQ_CAT,REQ_TYPE,DEFAULT_VAlUE FROM TFO_DJ_AUTODOCCHK_MASTER");
			tfoLogger.info("loadAutoDocChkCache outputxml " + tmpList.size());
			if (!tmpList.isEmpty()) {
				for (int counter = 0; counter < tmpList.size(); counter++) {
					String cid =(String) ((List) tmpList.get(counter)).get(0);
					String reqCat =(String) ((List) tmpList.get(counter)).get(1);
					String reqType =(String) ((List) tmpList.get(counter)).get(2);
					String defaultValue =(String) ((List) tmpList.get(counter)).get(3);
                    String key =cid+"#"+reqCat+"#"+reqType;
					autoDocChkValueMap.put(key, defaultValue);
				}
			}
		} catch (Exception e) {
			tfoLogger.error("Exception in loadAutoDocChkCache : ", e);
		}
	}
	//Traystream change |reyaz|atp-518|23-09-2024 end
}


