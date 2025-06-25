package com.newgen.cbg.event;

import java.lang.reflect.Constructor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.implementation.SingleUserConnection;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class D_Event implements IEventHandler{

	private String customerId= "";
	private String accNumber= "";
	private String mobileNumber= "";
	private String custEmail= "";
	private String bankingType = "";
	private String prefLanguage ="";
	private String sessionId;
	private String applicationName;
	private String sourcingChannel;
	private String stageId;

	@Override
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent){
		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();

		String WI_NAME = paramCoreEvent.getWI_NAME();
		String sysRefNo = paramCoreEvent.getSysRefNo();
		stageId = paramCoreEvent.getStageId();
		applicationName = paramCoreEvent.getApplicationName();
		sourcingChannel = paramCoreEvent.getSourcingChannel();
		String language = paramCoreEvent.getLanguage();
		try {
			if("IHUB".equalsIgnoreCase(sourcingChannel)){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in IHUB class");
//				return new IHUB_DEvent().dispatchEvent(paramCoreEvent);
			}
			boolean deliveryFromBranch = applicationName.equalsIgnoreCase("WMS") ? true : false;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY FROM BRANCH: "+ deliveryFromBranch);
			int delStatusCode = -1;
			SingleUserConnection instance = SingleUserConnection.getInstance(DSCOPConfigurations.getInstance().loginCount);
			sessionId = instance.getSession(DSCOPConfigurations.getInstance().CabinetName,DSCOPConfigurations.getInstance().UserName,DSCOPConfigurations.getInstance().Password);
			HashMap<String, String> defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();
			HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY_STATUS_CODE: attributeMap: "+ attributeMap);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY_STATUS_CODE: "+ attributeMap.get("DELIVERY_STATUS_CODE"));
			delStatusCode = Integer.parseInt(attributeMap.get("DELIVERY_STATUS_CODE"));
			HashMap<String, String> extAttributeMap = new HashMap<String, String>();

			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "D_Event"+stageId, "D-EVENT001", "Workitem Delivery Started", sessionId);
			try {
				if("".equals(WI_NAME)){
					WI_NAME ="DJ-"+attributeMap.get("BARCODE_NO")+"-WI";
					APCallCreateXML.APUpdate("USR_0_DSCOP_AUDIT_TRAIL", " WI_NAME ", "'"+WI_NAME+"'", " SYSREFNO = '"+ sysRefNo +"'", sessionId);
					
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"Error while updating winame in USR_0_DSCOP_AUDIT_TRAIL: " + e.getMessage());
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
			}
			
			String DELIVERY_DEVICE_ID = attributeMap.remove("DELIVERY_DEVICE_ID");
			if(DELIVERY_DEVICE_ID != null){
				extAttributeMap.put("DELIVERY_DEVICE_ID",DELIVERY_DEVICE_ID);
			} 

			String DELI_EIDA_NO = attributeMap.remove("DELI_EIDA_NO");
			if(DELI_EIDA_NO != null){
				extAttributeMap.put("DELI_EIDA_NO",DELI_EIDA_NO);
			}

			String DELI_BYNAME = attributeMap.remove("DELI_BYNAME");
			if(DELI_BYNAME != null){
				extAttributeMap.put("DELI_BYNAME",DELI_BYNAME);
			}

			String AGENT_EIDA_NUM = attributeMap.remove("AGENT_EIDA_NUM");
			if(AGENT_EIDA_NUM != null){
				extAttributeMap.put("AGENT_EIDA_NUM",AGENT_EIDA_NUM);
			}

			String DELI_DATETIME = attributeMap.remove("DELI_DATETIME");
			if(DELI_DATETIME != null){
				extAttributeMap.put("DELI_DATETIME",DELI_DATETIME);
			}

			String DELIVERY_STATUS = attributeMap.remove("DELIVERY_STATUS");
			if(DELIVERY_STATUS != null){
				extAttributeMap.put("DELIVERY_STATUS",DELIVERY_STATUS);
			}

			String DELI_REASON_STATUS = attributeMap.remove("DELI_REASON_STATUS");
			if(DELI_REASON_STATUS != null){
				extAttributeMap.put("DELI_REASON_STATUS",DELI_REASON_STATUS);
			}

			String DELIVERY_STATUS_CODE = attributeMap.remove("DELIVERY_STATUS_CODE");
			if(DELIVERY_STATUS_CODE != null){
				extAttributeMap.put("DELIVERY_STATUS_CODE",DELIVERY_STATUS_CODE);
			}

			String DELIVERY_STAFF_ID = attributeMap.remove("DEL_STAFF_ID");
			if(DELIVERY_STAFF_ID != null){
				extAttributeMap.put("DEL_STAFF_ID",DELIVERY_STAFF_ID);
			}

			String EIDA_BIO_STATUS = attributeMap.remove("EIDA_BIO_STATUS");
			if(EIDA_BIO_STATUS != null){
				extAttributeMap.put("EIDA_BIO_STATUS",EIDA_BIO_STATUS);
			}

			String DELI_CARD_HOL_NAME = attributeMap.remove("DELI_CARD_HOL_NAME");
			if(DELI_CARD_HOL_NAME != null){
				extAttributeMap.put("DELI_CARD_HOL_NAME",DELI_CARD_HOL_NAME);
			}

			String EIDA_VALID_ON = attributeMap.remove("EIDA_VALID_ON");
			if(EIDA_VALID_ON != null){
				extAttributeMap.put("EIDA_VALID_ON",EIDA_VALID_ON);
			}

			String DELI_AUTH_TYPE = attributeMap.remove("DELI_AUTH_TYPE");
			if(DELI_AUTH_TYPE != null){
				extAttributeMap.put("DELI_AUTH_TYPE",DELI_AUTH_TYPE);
			}

			String DELI_AUTH_REF_NO = attributeMap.remove("DELI_AUTH_REF_NO");
			if(DELI_AUTH_TYPE != null){
				extAttributeMap.put("DELI_AUTH_REF_NO",DELI_AUTH_REF_NO);
			}

			if(DELIVERY_STATUS_CODE.equals("6")){
				String DELIVERY_OPTION = attributeMap.remove("DELIVERY_OPTION");
				if(DELIVERY_OPTION != null){
					extAttributeMap.put("DELIVERY_OPTION",DELIVERY_OPTION);
				}

				String DELIVERY_PREF = attributeMap.remove("DELIVERY_PREF");
				if(DELIVERY_PREF != null){
					extAttributeMap.put("DELIVERY_PREF",DELIVERY_PREF);
				}
			}
			extAttributeMap.put("LAPS_APP_STATUS", "DELIVERED");
			String output;
			if(deliveryFromBranch){
				output = APCallCreateXML.APSelect("SELECT TO_CHAR(EIDA_EXPIRY_DATE, 'dd-MM-yyyy') AS EIDA_EXPIRY_DATE, STAGE_ID FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME = N'" + WI_NAME  + "'");
			}
			else{
				output = APCallCreateXML.APSelect("SELECT TO_CHAR(EIDA_EXPIRY_DATE, 'dd-MM-yyyy') AS EIDA_EXPIRY_DATE, STAGE_ID FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME = N'" + WI_NAME + "'");
			}			
			XMLParser parser = new XMLParser(output);
			int maincode = Integer.parseInt(parser.getValueOf("MainCode"));
			if(maincode == 0 && Integer.parseInt(parser.getValueOf("TotalRetrieved")) > 0)
			{
				String outputXml = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM WFINSTRUMENTTABLE WHERE ACTIVITYNAME='DELIVERY' AND PROCESSINSTANCEID = N'" + WI_NAME  + "'");
				int deliveryWSCount  = Integer.parseInt(new XMLParser(outputXml).getValueOf("COUNT"));
				if("9".equalsIgnoreCase(parser.getValueOf("STAGE_ID")) || "606".equalsIgnoreCase(parser.getValueOf("STAGE_ID")) || deliveryWSCount==1 || "611".equalsIgnoreCase(parser.getValueOf("STAGE_ID")) || "612".equalsIgnoreCase(parser.getValueOf("STAGE_ID"))){
					String eidaExpiry = parser.getValueOf("EIDA_EXPIRY_DATE");
					attributeMap.put("RESIDENCY_EXPIRY_DATE", eidaExpiry);

					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY_STATUS_CODE: extAttributeMap: "+ extAttributeMap);
					String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM USR_0_CBG_DELIVERY WHERE WI_NAME = N'" + WI_NAME + "'");
					XMLParser xp = new XMLParser(outputXML);
					int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0){
						if(Integer.parseInt(xp.getValueOf("COUNT")) == 0){
							attributeMap.put("WI_NAME", WI_NAME);
							int status = insertDeliveryData(attributeMap, extAttributeMap, sessionId, WI_NAME, deliveryFromBranch);
							if(status == 0){
								responseObj.setStatusCode("0");
								responseObj.setStatusMessage("Delivery Data Inserted Successfully.");
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data Inserted Successfully", sessionId);
								responseObj = doDelivery(delStatusCode, defaultAttributeMap, sessionId, WI_NAME, deliveryFromBranch);	
							} else {
								responseObj.setStatusCode("1");
								responseObj.setStatusMessage("Delivery Data Insertion Failed.");
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data Insertion Failed", sessionId);
							}
						} else {
							int status = modifyDeliveryData(attributeMap, extAttributeMap, sessionId, WI_NAME, deliveryFromBranch);
							if(status == 0){
								responseObj.setStatusCode("0");
								responseObj.setStatusMessage("Delivery Data Modified Successfully.");
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data Modified Successfully", sessionId);
								responseObj = doDelivery(delStatusCode, defaultAttributeMap, sessionId, WI_NAME, deliveryFromBranch);
							} else {
								responseObj.setStatusCode("1");
								responseObj.setStatusMessage("Delivery Data Modification Failed.");
								DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data Modification Failed", sessionId);
							}
						}
					}
				}
				else{
					responseObj.setStatusCode("1");
					responseObj.setStatusMessage("INVALID STAGE");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "D_Event", "D-EVENT"+stageId, "Invalid Stage", sessionId);
				}
			}
			else{
				responseObj.setStatusCode("1");
				responseObj.setStatusMessage("ERROR");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "ERROR", sessionId);
			}

			responseObj.setWI_NAME(WI_NAME);
			responseObj.setStage(stageId);
			responseObj.setApplicationName(applicationName);
			responseObj.setLanguage(language);
			responseObj.setSYSREFNO(sysRefNo);
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
		}
		return responseObj;
	}

	private int insertDeliveryData(HashMap<String, String> attributeMap,HashMap<String, String> extAttributeMap, String sessionId, String WI_NAME, boolean deliveryFromBranch){
		StringBuilder keyList = new StringBuilder("");
		StringBuilder valList = new StringBuilder("");
		StringBuilder keyListExt = new StringBuilder("");
		StringBuilder valListExt = new StringBuilder("");
		if(!deliveryFromBranch){
			Set<Map.Entry<String, String>> entrySet = attributeMap.entrySet();
			Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String,String> entry = entrySetIterator.next();
				if(!"SIGN_IMAGE_MORPHO".equalsIgnoreCase(entry.getKey())){
					keyList = keyList.append(entry.getKey()+",");
					valList = valList.append("'"+ entry.getValue() +"',");
				}
				else{
					signatureUpdate(WI_NAME, entry.getValue());
				}
			}
			keyList.deleteCharAt(keyList.lastIndexOf(","));
			valList.deleteCharAt(valList.lastIndexOf(","));
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "keyList: "+ keyList);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "valList: "+ valList);
			try {
				String outputXML = APCallCreateXML.APInsert("USR_0_CBG_DELIVERY", keyList.toString(), valList.toString(), sessionId);
				XMLParser xp = new XMLParser(outputXML);
				int status = Integer.parseInt(xp.getValueOf("MainCode"));
				if(status == 0){
					Set<Map.Entry<String, String>> entrySetExt = extAttributeMap.entrySet();
					Iterator<Entry<String,String>> entrySetIteratorExt = entrySetExt.iterator();
					while(entrySetIteratorExt.hasNext()){
						Entry<String,String> entry = entrySetIteratorExt.next();
						keyListExt = keyListExt.append(entry.getKey()+",");
						valListExt = valListExt.append("'"+ entry.getValue() +"',");			
					}
					keyListExt.deleteCharAt(keyListExt.lastIndexOf(","));
					valListExt.deleteCharAt(valListExt.lastIndexOf(","));
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "keyListExt: "+ keyListExt);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "valListExt: "+ valListExt);
					String output = APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", keyListExt+"", valListExt+"", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
					xp = new XMLParser(output);
					status = Integer.parseInt(xp.getValueOf("MainCode"));
					if(status == 0){
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DELIVERY UPDATE : "+ status);
					} else {
						return status;
					}
				} else {
					return status;
				}

				return status;
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
				return 1;
			}
		} else {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY FROM BRANCH: DELIVERY_STATUS_CODE "+ extAttributeMap.get("DELIVERY_STATUS_CODE"));
			String output;
			try {
				output = APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "LAPS_APP_STATUS,DELIVERY_STATUS_CODE, DELIVERY_STATUS, DELI_BYNAME, DEL_STAFF_ID, DELI_DATETIME, DELI_AUTH_TYPE, EIDA_VALID_ON, DELI_AUTH_REF_NO, DELI_CARD_HOL_NAME, EIDA_BIO_STATUS, DELI_REASON_STATUS, DELI_EIDA_NO",
						"'"+extAttributeMap.get("LAPS_APP_STATUS")+"','" +extAttributeMap.get("DELIVERY_STATUS_CODE")+"','" + extAttributeMap.get("DELIVERY_STATUS")+"','"+ extAttributeMap.get("DELI_BYNAME")+"','"+ extAttributeMap.get("DEL_STAFF_ID")+
						"','"+ extAttributeMap.get("DELI_DATETIME") +"'," + "'"+ extAttributeMap.get("DELI_AUTH_TYPE")+"','"+ extAttributeMap.get("EIDA_VALID_ON")+"','"+ extAttributeMap.get("DELI_AUTH_REF_NO")+
						"','"+ extAttributeMap.get("DELI_CARD_HOL_NAME") +"','"+ extAttributeMap.get("EIDA_BIO_STATUS")+"','"+ extAttributeMap.get("DELI_REASON_STATUS")+"','"+ extAttributeMap.get("DELI_EIDA_NO")+"'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				XMLParser xp = new XMLParser(output);
				int status = Integer.parseInt(xp.getValueOf("MainCode"));
				if(status == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DELIVERY UPDATE : "+ status);
					return status;
				} else {
					return status;
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
				return 1;
			}
		}
	}

	private int modifyDeliveryData(HashMap<String, String> attributeMap, HashMap<String, String> extAttributeMap, String sessionId, String WI_NAME, boolean deliveryFromBranch){
		StringBuilder keyList = new StringBuilder("");
		StringBuilder valList = new StringBuilder("");
		StringBuilder keyListExt = new StringBuilder("");
		StringBuilder valListExt = new StringBuilder("");
		if(!deliveryFromBranch){
			Set<Map.Entry<String, String>> entrySet = attributeMap.entrySet();
			Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String,String> entry = entrySetIterator.next();
				if(!"SIGN_IMAGE_MORPHO".equalsIgnoreCase(entry.getKey())){
				keyList = keyList.append(entry.getKey()+",");
				valList = valList.append("'"+ entry.getValue() +"',");			
			}
				else{
					signatureUpdate(WI_NAME, entry.getValue());
				}
			}
			keyList.deleteCharAt(keyList.lastIndexOf(","));
			valList.deleteCharAt(valList.lastIndexOf(","));
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "keyList: "+ keyList);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "valList: "+ valList);
			try {
				String outputXML = APCallCreateXML.APUpdate("USR_0_CBG_DELIVERY", keyList.toString(), valList.toString(), " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				XMLParser xp = new XMLParser(outputXML);
				int status = Integer.parseInt(xp.getValueOf("MainCode"));
				if(status == 0){
					Set<Map.Entry<String, String>> entrySetExt = extAttributeMap.entrySet();
					Iterator<Entry<String,String>> entrySetIteratorExt = entrySetExt.iterator();
					while(entrySetIteratorExt.hasNext()){
						Entry<String,String> entry = entrySetIteratorExt.next();
						if(!"SIGN_IMAGE_MORPHO".equalsIgnoreCase(entry.getKey())){
						keyListExt = keyListExt.append(entry.getKey()+",");
						valListExt = valListExt.append("'"+ entry.getValue() +"',");			
					}
					}
					keyListExt.deleteCharAt(keyListExt.lastIndexOf(","));
					valListExt.deleteCharAt(valListExt.lastIndexOf(","));
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "keyListExt: "+ keyListExt);
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "valListExt: "+ valListExt);
					String output = APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", keyListExt+"", valListExt+"", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
					xp = new XMLParser(output);
					status = Integer.parseInt(xp.getValueOf("MainCode"));
					if(status == 0){
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DELIVERY UPDATE : "+ status);
						return status;
					} else {
						return status;
					}
				} else {
					return status;
				}

			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
				return 1;
			}
		} else {
			String output;
			try {
				//output = APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "DELIVERY_STATUS_CODE","'"+extAttributeMap.get("DELIVERY_STATUS_CODE")+"'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				output = APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "DELIVERY_STATUS_CODE, DELIVERY_STATUS, DELI_BYNAME, DEL_STAFF_ID, DELI_DATETIME, DELI_AUTH_TYPE, EIDA_VALID_ON, DELI_AUTH_REF_NO, DELI_CARD_HOL_NAME, EIDA_BIO_STATUS, DELI_REASON_STATUS, DELI_EIDA_NO",
						"'"+extAttributeMap.get("DELIVERY_STATUS_CODE")+"'," + "'"+ extAttributeMap.get("DELIVERY_STATUS")+"','"+ extAttributeMap.get("DELI_BYNAME")+"','"+ extAttributeMap.get("DEL_STAFF_ID")+
						"','"+ extAttributeMap.get("DELI_DATETIME") +"'," + "'"+ extAttributeMap.get("DELI_AUTH_TYPE")+"','"+ extAttributeMap.get("EIDA_VALID_ON")+"','"+ extAttributeMap.get("DELI_AUTH_REF_NO")+
						"','"+ extAttributeMap.get("DELI_CARD_HOL_NAME") +"','"+ extAttributeMap.get("EIDA_BIO_STATUS")+"','"+ extAttributeMap.get("DELI_REASON_STATUS")+"','"+ extAttributeMap.get("DELI_EIDA_NO") + "'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				XMLParser xp = new XMLParser(output);
				int status = Integer.parseInt(xp.getValueOf("MainCode"));
				if(status == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DELIVERY UPDATE : "+ status);
					return status;
				} else {
					return status;
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
				return 1;
			}
		}		
	}

	private boolean callParallelCalls(String stageId, HashMap<String, String> defaultAttributeMap, String sessionId, String WI_NAME, String applicationVersion, String applicationJourney){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "EXECUTING SEQUENTIAL CALLS FOR DELIVERY stage: "+stageId);
		boolean moveToRepair = false;
		List<CallEntity> callArray;
		try {
			int processID = Integer.parseInt(defaultAttributeMap.get("PROCESS_ID"));
			HashMap<Integer, Integer> eventMap = DSCOPUtils.getInstance().getEventDetector(processID, "CBGAPP", applicationVersion, applicationJourney, Integer.parseInt(stageId));
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			callArray = DSCOPUtils.getInstance().getAsyncCallEventWise(eventID);
			if(callArray != null) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY CALLS: " + callArray.toString());
				for (CallEntity callEntity : callArray) {
					if(callEntity.isMandatory()){
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "DELIVERY CALL:  com.newgen.cbg.calls."+ callEntity.getCallName());
						Constructor<?> c = Class.forName("com.newgen.cbg.calls."+ callEntity.getCallName()).getConstructor(Map.class, String.class, String.class, String.class, Boolean.class, CallEntity.class);
						ICallExecutor call = (ICallExecutor)c.newInstance(defaultAttributeMap, sessionId, String.valueOf(eventID), WI_NAME, false, callEntity);
						String outputXML = call.executeCall(defaultAttributeMap);
						XMLParser xp = new XMLParser(outputXML);
						int mainCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
						if(mainCode != 0){
							moveToRepair = true;
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Delivery Call Failed " + callEntity.getCallName());
						}
					}
				}
			} else {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"No Sequential calls at Stage: "+ stageId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D_Event"+stageId, "D-EVENT100", e, sessionId);
		}
		return moveToRepair;
	}

	private CBGSingleHookResponse doDelivery(int status, HashMap<String, String> defaultAttributeMap, String sessionId, String WI_NAME, boolean deliveryFromBranch) throws Exception{
		String output = APCallCreateXML.APSelect("SELECT APP_VERSION, JOURNEY_TYPE, LOAN_ACCEPTANCE_FLAG, CUSTOMER_MOBILE_NO, CUSTOMER_EMAIL, BANKING_TYPE, CUSTOMER_ID, ACCOUNT_NUMBER, CC_APPROVED_LIMIT FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME = N'" + WI_NAME + "'");
		XMLParser parser = new XMLParser(output);
		String applicationVersion = null;
		String applicationJourney = null;
		int workitemID = 1;
		String workitemid=new XMLParser(APCallCreateXML.APSelect("SELECT WORKITEMID "
				+ " FROM wfinstrumenttable WHERE processinstanceid = N'" + WI_NAME + "' and activityname = 'DELIVERY'")).getValueOf("WORKITEMID");
		try{
			workitemID = Integer.parseInt(workitemid);			
		}catch(Exception ex){
			workitemID 	= 1;
		}	
		int maincode = Integer.parseInt(parser.getValueOf("MainCode"));
		if(maincode == 0){
			if(Integer.parseInt(parser.getValueOf("TotalRetrieved")) > 0){
				mobileNumber = parser.getValueOf("CUSTOMER_MOBILE_NO");
				custEmail =  parser.getValueOf("CUSTOMER_EMAIL");
				bankingType =  parser.getValueOf("BANKING_TYPE");
				customerId = parser.getValueOf("CUSTOMER_ID");
				accNumber = parser.getValueOf("ACCOUNT_NUMBER");
				prefLanguage = parser.getValueOf("PREFERRED_LANGUAGE");
				applicationVersion = parser.getValueOf("APP_VERSION");
				applicationJourney = parser.getValueOf("JOURNEY_TYPE");
//				workitemID = "Y".equalsIgnoreCase(parser.getValueOf("LOAN_ACCEPTANCE_FLAG"))?2:1;
			}
		}

		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();

		boolean moveToRepair = callParallelCalls((50+status )+"", defaultAttributeMap, sessionId, WI_NAME, applicationVersion, applicationJourney);
		if(!deliveryFromBranch){
			if(!moveToRepair){
				String outputXML = APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "ROUTE_TO_NEXT_STAGE, ROUTE_TO_AUTO_REPAIR", "'Y','N'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0) {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DELIVERY ROUTE_TO_NEXT_STAGE : Y : "+ mainCode);
				} else {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"DELIVERY ROUTE_TO_NEXT_STAGE : Y : FAILED"+ mainCode);
				}
			}
			if(status == 1){
				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, workitemID);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode != 0){
					responseObj.setStatusCode("-1");
					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Workitem Movement Failed after Saving Data", sessionId);
				} else {
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data inserted Successfully", sessionId);
				}

			} else if(status == 2){
				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, workitemID);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode != 0){
					responseObj.setStatusCode("-1");
					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Workitem Movement Failed after Saving Data", sessionId);
				} else {
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data inserted Successfully", sessionId);
				}
			} else if(status == 3){
				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, workitemID);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode != 0){
					responseObj.setStatusCode("-1");
					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Workitem Movement Failed after Saving Data", sessionId);
				} else {
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data inserted Successfully", sessionId);
				}
			} else if(status == 4){
				//				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				//				XMLParser xp = new XMLParser(outputXML);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode != 0){
				//					responseObj.setStatusCode("-1");
				//					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
				//				} else {
				//					responseObj.setStatusCode("0");
				//					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
				//				}
			} else if(status == 5){
				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, workitemID);
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode != 0){
					responseObj.setStatusCode("-1");
					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Workitem Movement Failed after Saving Data", sessionId);
				} else {
					responseObj.setStatusCode("0");
					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data inserted Successfully", sessionId);
				}
			} else if(status == 6){
				//				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				//				XMLParser xp = new XMLParser(outputXML);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode != 0){
				//					responseObj.setStatusCode("-1");
				//					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
				//				} else {
				responseObj.setStatusCode("0");
				responseObj.setStatusMessage("Delivery Data inserted Successfully.");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Data inserted Successfully", sessionId);
				//				}
			} else if(status == 7){
				//				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				//				XMLParser xp = new XMLParser(outputXML);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode != 0){
				//					responseObj.setStatusCode("-1");
				//					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
				//				} else {
				//					responseObj.setStatusCode("0");
				//					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
				//				}
			} else if(status == 8){
				//				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				//				XMLParser xp = new XMLParser(outputXML);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode != 0){
				//					responseObj.setStatusCode("-1");
				//					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
				//				} else {
				//					responseObj.setStatusCode("0");
				//					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
				//				}
			} else if(status == 9){
				//				String outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
				//				XMLParser xp = new XMLParser(outputXML);
				//				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				//				if(mainCode != 0){
				//					responseObj.setStatusCode("-1");
				//					responseObj.setStatusMessage("Workitem Movement Failed after Saving Data");
				//				} else {
				//					responseObj.setStatusCode("0");
				//					responseObj.setStatusMessage("Delivery Data inserted Successfully.");
				//				}//DEL_STG1_NOTIFY_TMP_ID
			} 			
		} else {
			responseObj.setStatusCode("0");
			responseObj.setStatusMessage("Delivery Successfully.");
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, WI_NAME, "D_Event", "D-EVENT"+stageId, "Delivery Successfully", sessionId);
		}

		if(status==1){
			DSCOPUtils.getInstance().sendEmailSMS(sessionId, "S", customerId, accNumber, "AC", defaultAttributeMap.get("DEL_STG1_NOTIFY_TMP_ID"),
					"", mobileNumber, defaultAttributeMap.get("DEL_STG1_NOTIFY_TMP_ID"), 
					custEmail, "C".equalsIgnoreCase(parser.getValueOf("BANKING_TYPE")) ? defaultAttributeMap.get("FLEXIFILLER1_CONVENTIONAL")
							:defaultAttributeMap.get("FLEXIFILLER1_ISLAMIC"), defaultAttributeMap.get("FROM_EMAIL_ID"), "P", WI_NAME, prefLanguage);
		}
		else if((!deliveryFromBranch && status==2) || status==3 || status==5){
			DSCOPUtils.getInstance().sendEmailSMS(sessionId, "S", customerId, accNumber, "AC", defaultAttributeMap.get("DEL_STG2_NOTIFY_TMP_ID"), 
					"", mobileNumber, defaultAttributeMap.get("DEL_STG2_NOTIFY_TMP_ID"), 
					custEmail, "C".equalsIgnoreCase(bankingType) ? defaultAttributeMap.get("FLEXIFILLER1_CONVENTIONAL")
							:defaultAttributeMap.get("FLEXIFILLER1_ISLAMIC"), defaultAttributeMap.get("FROM_EMAIL_ID"), "P", WI_NAME, prefLanguage);
		}
		return responseObj;
	}
	
	private void signatureUpdate(String WI_NAME, String newSignImage64){

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside signatureUpdate: "+WI_NAME);
		Connection con =null;
		try {

			int itr = newSignImage64.length()/4000;
			int mod = newSignImage64.length()%4000;
			StringBuilder signBinaryData = new StringBuilder();
			if(mod > 0 && newSignImage64.length()>=4000){
				++itr;
			}
			else{
				signBinaryData.append("TO_NCLOB('"+newSignImage64+ "')");
			}
			for (int i = 0; i < itr; i++) {
				if(i == 0){
					signBinaryData.append("TO_NCLOB('"+newSignImage64.substring(0,4000)+ "')");
				}
				else if(i < itr - 1) {
					signBinaryData.append(" || TO_NCLOB('"+newSignImage64.substring((4000*i),4000*(i+1))+ "')");
				}
				else {
					signBinaryData.append(" || TO_NCLOB('"+newSignImage64.substring((4000*i),newSignImage64.length())+ "')");
				}
			}
			con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
			String query = "UPDATE EXT_CBG_CUST_ONBOARDING SET SIGN_IMAGE_MORPHO = "+signBinaryData+" WHERE  WI_NAME = ?";
			CallableStatement cstmt = con.prepareCall(query);
			cstmt.setString(1, WI_NAME);//WINAME
			int count = cstmt.executeUpdate();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
			NGDBConnection.closeDBConnection(con, "APUpdate");
			con = null;
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D-EVENT", "SIGDOC100", e, sessionId);
		}
		finally
		{
			if(con != null){
				try {
					if(!(con.getAutoCommit())){
						con.rollback();
						con.setAutoCommit(true);
					}
					NGDBConnection.closeDBConnection(con, "APUpdate");
					con = null;
				} catch (Exception e) {
					e.printStackTrace();
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "D-EVENT", "SIGDOC100", e, sessionId);
				}
			}
		}
	}
}