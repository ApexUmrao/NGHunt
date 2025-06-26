package com.newgen.ao.laps.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adminclient.OSASecurity;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ITQAN_M_StagingCache;
import com.newgen.ao.laps.cache.CallValidation;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.SourcingChannelCache;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;
import com.newgen.omni.wf.util.excp.NGException;
//import com.sun.jmx.snmp.Timestamp;

public class LapsUtils {

	private static LapsUtils instance = null;

	private LapsUtils(){

	}

	public static LapsUtils getInstance(){
		if(instance == null){
			instance = new LapsUtils();
		}
		return instance;
	}

	public String generateSysRefNumber(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT SEQ_WEBSERVICE.NEXTVAL SYSREFNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}
	public String getTrsdRefNum() {
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT SEQ_WBG_TRSD.NEXTVAL AS SYSREFNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
			return sysNum;
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}
	
	public String getFSKRefNum() {
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT FSK_SEQ.nextval as SYSREFNO from DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
			return sysNum;
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}
	
	public String getArchivalRefNum() {
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT ARCHIVAL_SEQ.nextval as SYSREFNO from DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
			return sysNum;
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}
	public static boolean deleteDirectory(File dir){
		if(dir.isDirectory()){
			File[] children = dir.listFiles();
			for(int i=0;i<children.length;i++){
				boolean success = deleteDirectory(children[i]);
				if(!success){
					return false;
				}
			}
		}
		return dir.delete();
	}

	public List<String> getChannelNameList(){
		List<String> channelList = SourcingChannelCache.getInstance().getCachedStageChannelList();	   
		return channelList;		
	}

	public static String generateWMSID(String winame){
		String sWMSID = "";
		try {
			String sOutput = APCallCreateXML.APSelect("SELECT SEQ_WMSID.nextval as ID from DUAL ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutput---"+sOutput);
			XMLParser xp = new XMLParser(sOutput);
			String sID = xp.getValueOf("ID");

			sOutput = APCallCreateXML.APSelect("SELECT ACC_HOME_BRANCH,REQUEST_TYPE FROM EXT_AO WHERE WI_NAME='"+winame+"'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutput---"+sOutput);
			xp = new XMLParser(sOutput);
			String homeBranch = xp.getValueOf("ACC_HOME_BRANCH");
			String requestType = xp.getValueOf("REQUEST_TYPE");

			sOutput = APCallCreateXML.APSelect("SELECT SHORT_NAME FROM USR_0_SERVICE_CHANNEL WHERE SERVICE_CHANNEL = 'LAPS'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutput---"+sOutput);
			xp = new XMLParser(sOutput);
			String channelShortName = xp.getValueOf("SHORT_NAME");
			sOutput= APCallCreateXML.APSelect("SELECT SHORT_NAME FROM USR_0_HOME_BRANCH WHERE HOME_BRANCH = '"+homeBranch+"'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output---"+sOutput);
			xp = new XMLParser(sOutput);
			String branchShortName = xp.getValueOf("SHORT_NAME");
			if(requestType.equalsIgnoreCase("Category Change Only"))
			{
				sWMSID = sWMSID+"CC-";
			}
			else
			{
				sWMSID = sWMSID+"AO-";
			}

			sWMSID = (sWMSID+channelShortName+"-"+branchShortName+"-"+sID).toUpperCase();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WMS ID---"+sWMSID);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}

		return sWMSID;
	}

	public String formatToBPMDate(String dt){
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside formatToBPMDate: "+dt);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(dt);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			dt = sdf.format(date);	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "date after conversion: "+dt);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return dt;

	}

	public String formatToBPMDateTime(String dt){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"BPMTODATETIME: "+dt);
		try {
			if(!"".equalsIgnoreCase(dt) && null != dt){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = sdf.parse(dt);
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dt = sdf.format(date);
			}		
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return dt;
	}

	public static HashMap<String, String> getMasterData(String tableName){
		HashMap<String, String> returnVal=new HashMap<String, String>();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside createStagechannelMap once");
		String outputXML="";
		try {
			outputXML = APCallCreateXML
					.APSelect("select attribute_name, column_name from Laps_MasterData where table_name='"+tableName+"'");
		} catch (NGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;

		for (int i = 0; i < noOfFields; ++i) {

			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String attribute_name = xp.getValueOf("attribute_name", start, end);
			String column_name = xp.getValueOf("column_name", start, end);
			returnVal.put(attribute_name, column_name);
		}

		return returnVal;

	}

	public static void insertReqData(String sessionId, String tablename, HashMap<String, String> attribMap, 
			String winame) throws Exception
	{
		try {
			StringBuilder column = new StringBuilder("wi_name");
			StringBuilder value = new StringBuilder("'" + winame + "'");
			HashMap<String, String> MasterMap = LapsUtils.getMasterData(tablename);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"MasterMap is" + MasterMap.toString());

			Set<Map.Entry<String, String>> entrySet = attribMap.entrySet();
			Iterator<Entry<String, String>> entrySetIterator = entrySet.iterator();
			while (entrySetIterator.hasNext()) {
				Entry<String, String> entry = entrySetIterator.next();
				if (MasterMap.containsKey(entry.getKey())) {
					column = column.append("," + MasterMap.get(entry.getKey()));
					value = value.append(",");

					if (MasterMap.get(entry.getKey()).toString().contains("_DATETIME")
							|| MasterMap.get(entry.getKey()).toString().contains("_DateTime")
							|| (MasterMap.get(entry.getKey()).toString().contains("DATE"))) {
						value = value.append("to_date('"+ entry.getValue().toString()+ "','dd/MM/yyyy')");
					} else {
						value = value.append("'" + entry.getValue() + "'");
					}
				}
			}

			if(tablename.equalsIgnoreCase("USR_0_TRSD_DETAILS") || tablename.equalsIgnoreCase("USR_0_TRSD_HISTORY_AO"))
			{
				column = column.append(",ACC_RELATION_SNO");
				value = value.append(",'1'");
			}

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"column ===>" + column+column.length());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value ===>" + value);

			if (tablename.equalsIgnoreCase("USR_0_CUST_TXN") || !column.toString().equalsIgnoreCase("wi_name")) {
				APCallCreateXML.APInsert(tablename, column.toString(),value.toString(), sessionId);
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	}
	public static String returnInsertionOrderID(String tableName){
		String outputXML;
		String insertionOrderID="";
		try {
			outputXML = APCallCreateXML.APSelect("select (processdefid||'_'||extobjid) SEQ from wfudtvarmappingtable where mappedobjectname ='"+tableName+"'");
			XMLParser xp = new XMLParser(outputXML);
			String seq="";
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					seq = xp.getValueOf("SEQ");					
				}
			}

			outputXML = APCallCreateXML.APSelect("select wfseq_array_"+seq+".nextval from dual");
			xp = new XMLParser(outputXML);

			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					insertionOrderID = xp.getValueOf("NEXTVAL");					
				}
			}

		} catch (NGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertionOrderID;
	}


	public String decryptPassword(String pass)
	{			
		int len = pass.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[(i / 2)] = 
					(byte)((Character.digit(pass.charAt(i), 16) << 4) + 
							Character.digit(pass.charAt(i + 1), 16));
		}
		String password = OSASecurity.decode(data, "UTF-8");
		return password;
	} 
	/*public HashMap<String, String> requestToAttributeMap(ApplicationAttributes[] ApplicationAttributes){
		HashMap<String, String> attributeMap = new HashMap<String, String>();
//		Map<String, List<List<Map<String, String>>>> multipleAttributeMap  =  new HashMap<String, List<List<Map<String,String>>>>();
		for (ApplicationAttributes applicationAttributes : ApplicationAttributes) {
			AttributeDetails[] ad = applicationAttributes.getAttributeDetails();
			//String attrDetailName = applicationAttributes.getName();
			List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>();
			if(ad.length==1){
				for (Attributes attr : ad[0].getAttributes()) {
					//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Key:"+attr.getAttributeKey()+ " Value:"+attr.getAttributeValue());
					attributeMap.put(attr.getAttributeKey(), attr.getAttributeValue().replace("'","''"));
				} 
			}
			else {
				for (AttributeDetails attributeDetails : ad) {
					List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
					for (Attributes attr : attributeDetails.getAttributes()) {
						Map<String, String> at = new HashMap<String, String>();
						//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Key:"+attr.getAttributeKey()+ " Value:"+attr.getAttributeValue());
						at.put(attr.getAttributeKey(), attr.getAttributeValue().replace("'","''"));
						list1.add(at);
					} 
					list.add(list1);
				}
				//multipleAttributeMap.put(attrDetailName, list);
			}
		}
		return attributeMap;
	}
	 */	
	public ArrayList<CallEntity> getCallNameFromChannel(String channelName){
		HashMap< String,ArrayList<CallEntity> > channelMap=ChannelCallCache.getInstance().getCachedStageChannelMap();
		ArrayList<CallEntity> callNameMap=channelMap.get(channelName);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callName is : "+callNameMap);

		return callNameMap;

	}
	
//	public ArrayList<CallValidation> getValidationMaster(String channelName){
//		HashMap< String,ArrayList<CallValidation> > channelMap=ITQAN_M_StagingCache.getInstance().getValidationMasterMap();
//		ArrayList<CallValidation> callNameMap= channelMap.get(channelName);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callName is : "+callNameMap);
//		return callNameMap;
//
//	}

	public static <T> ICallExecutor instantiate(final String className, final Class<T> type,LinkedHashMap defaultAttributeMap,String sessionId,String winame,String cabinet) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Executing Call package ===> " +"com.newgen.cbg.calls."+className);
			Constructor<?> c = Class.forName("com.newgen.ao.laps.calls."+className).getConstructor(LinkedHashMap.class, String.class, String.class,String.class,String.class);
			return (ICallExecutor) c.newInstance(defaultAttributeMap,sessionId,winame,cabinet,"1");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			e.printStackTrace();
		}
		return null;
	}

	public String checkMandatoryAndValidation(HashMap<String, String> attributeMap) throws NGException {
		String status = "0;0";
		String outputXML = APCallCreateXML.APSelect("SELECT MANDATORY_FIELD FROM NG_CHANNEL_MANDATORY WHERE CHANNEL_NAME='LAPSMODIFY'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;

		for (int i = 0; i < noOfFields; ++i) {

			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String mandField = xp.getValueOf("MANDATORY_FIELD", start, end);
			if (!attributeMap.containsKey(mandField)) {
				status = "101;"+mandField+"";
				return status;
			}
		}

		/*	if (attributeMap.containsKey("CHANNEL_REF_NUMBER") && (!(attributeMap.get("CHANNEL_REF_NUMBER").length() < 21) || !checkAlphaNumeric(attributeMap.get("CHANNEL_REF_NUMBER")))) {
			status = "100;CHANNEL_REF_NUMBER";
		} 
		else if (attributeMap.containsKey("CID") && (!isNumeric(attributeMap.get("CID"))|| !(attributeMap.get("CID").length() < 21))) {
			status = "100;CID";
		} 
		else if (attributeMap.containsKey("IS_TML") && (!isYesOrNo(attributeMap.get("IS_TML"))|| !(attributeMap.get("IS_TML").length() < 21))) {
			status = "100;IS_TML";
		}
		else if (attributeMap.containsKey("NATURE_OF_BUSINESS") && !(attributeMap.get("NATURE_OF_BUSINESS").length() < 101)) {
			status = "100;NATURE_OF_BUSINESS";
		}
		else if (attributeMap.containsKey("IS_VVIP_CUSTOMER") && (!isYesOrNo(attributeMap.get("IS_VVIP_CUSTOMER"))|| !(attributeMap.get("IS_VVIP_CUSTOMER").length() < 21))) {
			status = "100;IS_VVIP_CUSTOMER";
		}
		else if (attributeMap.containsKey("SPECIAL_CATEGORY") && !(attributeMap.get("SPECIAL_CATEGORY").length() < 21)) {
			status = "100;SPECIAL_CATEGORY";
		}
		else if (attributeMap.containsKey("SIGNATURE_TYPE") && !(attributeMap.get("SIGNATURE_TYPE").length() < 151)) {
			status = "100;SIGNATURE_TYPE";
		}
		else if (attributeMap.containsKey("PURPOSE_TAX_EVASION") && !(attributeMap.get("PURPOSE_TAX_EVASION").length() < 21)) {
			status = "100;PURPOSE_TAX_EVASION";
		}
		else if (attributeMap.containsKey("IS_HAWALA") && (!isYesOrNo(attributeMap.get("IS_HAWALA"))|| !(attributeMap.get("IS_HAWALA").length() < 21))) {
			status = "100;IS_HAWALA";
		}
		else if (attributeMap.containsKey("IS_ARMAMENT") && (!isYesOrNo(attributeMap.get("IS_ARMAMENT"))|| !(attributeMap.get("IS_ARMAMENT").length() < 21))) {
			status = "100;IS_ARMAMENT";
		}
		else if (attributeMap.containsKey("IS_UAE_RESIDENT") && (!isYesOrNo(attributeMap.get("IS_UAE_RESIDENT"))|| !(attributeMap.get("IS_UAE_RESIDENT").length() < 21))) {
			status = "100;IS_UAE_RESIDENT";
		}
		else if (attributeMap.containsKey("LIST_OF_COUNTRY_RESIDENCE") && !(attributeMap.get("LIST_OF_COUNTRY_RESIDENCE").length() < 256)) {
			status = "100;LIST_OF_COUNTRY_RESIDENCE";
		}
		else if (attributeMap.containsKey("IS_PEP") && (!isYesOrNo(attributeMap.get("IS_PEP"))|| !(attributeMap.get("IS_PEP").length() < 21))) {
			status = "100;IS_PEP";
		}
		else if (attributeMap.containsKey("LIST_OF_PEP") && !(attributeMap.get("LIST_OF_PEP").length() < 256)) {
			status = "100;LIST_OF_PEP";
		}
		else if (attributeMap.containsKey("EMPLOYMENT_STATUS") && !(attributeMap.get("EMPLOYMENT_STATUS").length() < 31)) {
			status = "100;EMPLOYMENT_STATUS";
		}
		else if (attributeMap.containsKey("IS_UAE_CUSTOMER_INVOLVED") && (!isYesOrNo(attributeMap.get("IS_UAE_CUSTOMER_INVOLVED"))|| !(attributeMap.get("IS_UAE_CUSTOMER_INVOLVED").length() < 21))) {
			status = "100;IS_UAE_CUSTOMER_INVOLVED";
		}
		else if (attributeMap.containsKey("IS_NON_UAE_CUSTOMER_INVOLVED") && (!isYesOrNo(attributeMap.get("IS_NON_UAE_CUSTOMER_INVOLVED"))|| !(attributeMap.get("IS_NON_UAE_CUSTOMER_INVOLVED").length() < 21))) {
			status = "100;IS_NON_UAE_CUSTOMER_INVOLVED";
		}
		else if (attributeMap.containsKey("TRSD_CHANNEL_REF") && !(attributeMap.get("TRSD_CHANNEL_REF").length() < 101)) {
			status = "100;TRSD_CHANNEL_REF";
		}
		else if (attributeMap.containsKey("TRSD_CASE_ID") && !(attributeMap.get("TRSD_CASE_ID").length() < 51)) {
			status = "100;TRSD_CASE_ID";
		}
		else if (attributeMap.containsKey("TRSD_NAME") && !(attributeMap.get("TRSD_NAME").length() < 256)) {
			status = "100;TRSD_NAME";
		}
		else if (attributeMap.containsKey("TRSD_SYSTEM_RESULT") && !(attributeMap.get("TRSD_SYSTEM_RESULT").length() < 256)) {
			status = "100;TRSD_SYSTEM_RESULT";
		}
		else if (attributeMap.containsKey("TRSD_FINAL_DECISION") && !(attributeMap.get("TRSD_FINAL_DECISION").length() < 101)) {
			status = "100;TRSD_FINAL_DECISION";
		}
		else if (attributeMap.containsKey("IS_TRSH_APPROVAL_REQUIRED") && !(attributeMap.get("IS_TRSH_APPROVAL_REQUIRED").length() < 4)) {
			status = "100;IS_TRSH_APPROVAL_REQUIRED";
		}
		else if (attributeMap.containsKey("TRSD_INITIAL_ASSESSMENT_DATE") && !isValidDate(attributeMap.get("TRSD_INITIAL_ASSESSMENT_DATE"))) {
			status = "100;TRSD_INITIAL_ASSESSMENT_DATE";
		}
		else if (attributeMap.containsKey("TRSD_SCREENING_DATE") && !isValidDate(attributeMap.get("TRSD_SCREENING_DATE"))) {
			status = "100;TRSD_SCREENING_DATE";
		}
		else if (attributeMap.containsKey("TRSD_ASSESSMENT_DATE") && !isValidDate(attributeMap.get("TRSD_ASSESSMENT_DATE"))) {
			status = "100;TRSD_ASSESSMENT_DATE";
		}  
		else if (attributeMap.containsKey("TRSD_REMARKS") && !(attributeMap.get("TRSD_REMARKS").length() < 2001)) {
			status = "100;TRSD_REMARKS";
		}
		else if (attributeMap.containsKey("TRSD_APPROVED_BY") && !(attributeMap.get("TRSD_APPROVED_BY").length() < 21)) {
			status = "100;TRSD_APPROVED_BY";
		}
		else if (attributeMap.containsKey("IS_ECB_HIT") && !(attributeMap.get("IS_ECB_HIT").length() < 21)) {
			status = "100;IS_ECB_HIT";
		}
		else if (attributeMap.containsKey("ECB_INQUIRY_REF") && !(attributeMap.get("ECB_INQUIRY_REF").length() < 21)) {
			status = "100;ECB_INQUIRY_REF";
		}
		else if (attributeMap.containsKey("NUMBER_OF_CHEQUE_BOUNCES") && (!isNumeric(attributeMap.get("NUMBER_OF_CHEQUE_BOUNCES"))|| !(attributeMap.get("NUMBER_OF_CHEQUE_BOUNCES").length() < 4))) {
			status = "100;NUMBER_OF_CHEQUE_BOUNCES";
		} 
		else if (attributeMap.containsKey("PRODUCT_CODE") && (!isNumeric(attributeMap.get("PRODUCT_CODE"))|| !(attributeMap.get("PRODUCT_CODE").length() < 51))) {
			status = "100;PRODUCT_CODE";
		} 
		else if (attributeMap.containsKey("ACCOUNT_TITLE") && (isNumeric(attributeMap.get("ACCOUNT_TITLE"))|| !(attributeMap.get("ACCOUNT_TITLE").length() < 36))) {
			status = "100;ACCOUNT_TITLE";
		}
		else if (attributeMap.containsKey("NEW_CATEGORY") && !(attributeMap.get("NEW_CATEGORY").length() < 101)) {
			status = "100;NEW_CATEGORY";
		}
		else if (attributeMap.containsKey("SOURCE_CODE") && !(attributeMap.get("SOURCE_CODE").length() < 21)) {
			status = "100;SOURCE_CODE";
		}
		else if (attributeMap.containsKey("IS_ECB_HIT") && !(attributeMap.get("NEW_RM_CODE").length() < 21)) {
			status = "100;NEW_RM_CODE";
		}
		else if (attributeMap.containsKey("DOCUMENT_NAME") && !(attributeMap.get("DOCUMENT_NAME").length() < 101)) {
			status = "100;DOCUMENT_NAME";
		}
		else if (attributeMap.containsKey("DOCUMENT_INDEX") && !(attributeMap.get("DOCUMENT_INDEX").length() < 101)) {
			status = "100;DOCUMENT_INDEX";
		}
		else{
			 outputXML = APCallCreateXML.APSelect("SELECT ATTRIBUTE_KEY,MASTER_TABLE,MASTER_COLUMN FROM NG_CHANNEL_SOURCE_MASTER WHERE CHANNEL_NAME='LAPS'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"outputxml issss " + outputXML);
			 xp = new XMLParser(outputXML);
			 start = xp.getStartIndex("Records", 0, 0);
			 deadEnd = xp.getEndIndex("Records", start, 0);
			 noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			 end = 0;

			for (int i = 0; i < noOfFields; ++i) {

				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String attributeKey = xp.getValueOf("ATTRIBUTE_KEY", start, end);
				String tableName = xp.getValueOf("MASTER_TABLE", start, end);
				String columnName = xp.getValueOf("MASTER_COLUMN", start, end);
				if (attributeMap.containsKey(attributeKey) && !isExistInMaster(columnName, tableName, attributeMap.get(attributeKey))) {
					status = "102;"+attributeKey+"";
					break;
				}
			}

		}
		else if (attributeMap.containsKey("REQUEST_TYPE") && !isExistInMaster("REQUEST_TYPE", "usr_0_req_type", attributeMap.get("REQUEST_TYPE"))){
			status = "";
		}
		else if (attributeMap.containsKey("ACCOUNT_CLASSIFICATION") && !isExistInMaster("ACC_CLASSIFICATION", "usr_0_acc_classification", attributeMap.get("ACCOUNT_CLASSIFICATION"))){
			status = "102;ACCOUNT_CLASSIFICATION";
		}
		else if (attributeMap.containsKey("ACCOUNT_HOME_BRANCH") && !isExistInMaster("CODE", "usr_0_home_branch", attributeMap.get("ACCOUNT_HOME_BRANCH"))){
			status = "102;ACCOUNT_HOME_BRANCH";
		}
		else if (attributeMap.containsKey("PASSPORT_TYPE") && !isExistInMaster("PASSPORT_TYPE", "usr_0_passport_type", attributeMap.get("PASSPORT_TYPE"))){
			status = "102;PASSPORT_TYPE";
		}
		else if (attributeMap.containsKey("VISA_STATUS") && !isExistInMaster("VISA_STATUS", "usr_0_visa_status", attributeMap.get("VISA_STATUS"))){
			status = "102;VISA_STATUS";
		}
		else if (attributeMap.containsKey("SIGNATURE_TYPE") && !isExistInMaster("SIGN_CODE", "usr_0_sign_style", attributeMap.get("SIGNATURE_TYPE"))){
			status = "102;SIGNATURE_TYPE";
		}
		else if (attributeMap.containsKey("EMPLOYMENT_STATUS") && !isExistInMaster("EMP_STATUS", "usr_0_employment_status", attributeMap.get("EMPLOYMENT_STATUS"))){
			status = "102;EMPLOYMENT_STATUS";
		}
		else if (attributeMap.containsKey("PRODUCT_CODE") && !isExistInMaster("PRODUCT_CODE", "usr_0_product_master", attributeMap.get("PRODUCT_CODE"))){
			status = "102;PRODUCT_CODE";
		}
		 */		return status;
	}

	public static boolean isNumeric(String s) {

		boolean numeric = true;

		numeric = s.matches("-?\\d+(\\d+)?");

		return numeric;  
	}

	public static boolean isYorN(String s) {
		if (s.equals("Y")||s.equals("N")) {
			return true;
		}
		return false;
	}

	public static boolean isYesOrNo(String s) {
		if (s.equals("Yes")||s.equals("No")) {
			return true;
		}
		return false;
	}

	public static boolean isValidDate(String value) {
		Date date = null;
		String format="dd/MM/yyyy";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	public static boolean isExistInMaster(String columnName,String tableName, String Value) {
		try {
			String outVal="";
			StringBuffer query=new StringBuffer();
			query.append("SELECT "+columnName+" FROM "+tableName+" WHERE "+columnName+" = '" + Value + "'");

			String outputXML = APCallCreateXML.APSelect(query.toString());
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"CustomerInformation TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					outVal = xp.getValueOf(columnName);					
				}
			}
			if (outVal.equalsIgnoreCase(Value)) {
				return true;
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return false;
	}

	public static boolean checkAlphaNumeric(String val) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9]*$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(val);
		boolean b = m.find();

		return b;
	}

	public static void insertInDecisionHistory(String winame,String username,String callName,String status,String sessionId){
		String tablename="usr_0_ao_dec_hist";
		String column="WI_NAME,USERNAME,WS_DECISION,WS_COMMENTS,CHANNEL,CREATE_DAT";
		String value="'"+winame+"','"+username+"','"+callName+"','"+status+"','LAPS',sysdate";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside decision history "+column+" "+value);

		try {
			APCallCreateXML.APInsert(tablename, column.toString(),value.toString(), sessionId);
		} catch (NGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/*	public List<CallEntity> getCallStageWise(String stage, String WI_NAME) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getCallStageWise Method");
		List<CallEntity> returnObj = new ArrayList<CallEntity>();
//		HashMap<String, ArrayList<String>> callNameMap = StageCallCache.getReference().getCachedCallNameMap();
		HashSet<Integer> callList1 = StageCallCache.getReference().getCachedStageCallMap().get(Integer.parseInt(stage));
		if(callList1 != null){
			ArrayList<Integer> callList = new ArrayList<Integer>(callList1);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"GetCallStageWise: BEFORE R-Type callList: "+ callList);
			//			if(!WI_NAME.equals("")){
			//				String outputXML = APCallCreateXML.APSelect("SELECT CALL_NAME FROM USR_0_CBG_CALL_OUT WHERE WI_NAME = N'"+ WI_NAME +"' AND CALL_STATUS = N'R'");
			//				XMLParser xp = new XMLParser(outputXML);
			//				int start = xp.getStartIndex("Records", 0, 0);
			//				int deadEnd = xp.getEndIndex("Records", start, 0);
			//				int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
			//				int end = 0;
			//				int count = 0;
			//				for(int i = 0; i < noOfFields; ++i) {
			//					start = xp.getStartIndex("Record", end, 0);
			//					end = xp.getEndIndex("Record", start, 0);
			//					String callName  = xp.getValueOf("CALL_NAME", start, end);
			//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"GetCallStageWise: R-Type callname: "+ callName);
			//					int callId = Integer.parseInt((callNameMap.get(callName)).get(0).toString());
			//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"GetCallStageWise: R-Type callid: "+ callId);
			//					callList.add(callId);
			//				}
			//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"GetCallStageWise: AFTER R-Type callList: "+ callList);
			//			}

			HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
			if(callList != null) {
				for (Integer object : callList) {
					ArrayList<String> call = callCache.get(object);
					CallEntity ce = new CallEntity();
					ce.setStage(stage);
					ce.setCallName(call.get(0).toString());
					ce.setMandatory(call.get(1).toString().equals("Y")?true:false);	
					ce.setIgnorable(call.get(2).toString().equals("Y")?true:false);
					ce.setProductType(call.get(3).toString());
					returnObj.add(ce);
				}
			}
		}
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"End getCallStageWise Method" + returnObj);
		return returnObj;
	}

	public List<CallEntity> getPreqCallStageWise(String stageid, String WI_NAME) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getPreqCallStageWise Method");
		HashMap<String, ArrayList<String>> callNameMap = StageCallCache.getReference().getCachedCallNameMap();
		ArrayList<String> callList = new ArrayList<String>();
		List<CallEntity> returnObj = null;
		String outputXML = APCallCreateXML.APSelect("SELECT CALL_NAME FROM USR_0_CBG_CALL_OUT WHERE WI_NAME = N'"+ WI_NAME +"' AND CALL_STATUS = N'R' ORDER BY STAGE_ID");
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
		if(noOfFields > 0){
			returnObj = new ArrayList<CallEntity>();
		}
		int end = 0;
		for(int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String callName  = xp.getValueOf("CALL_NAME", start, end);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"GetCallStageWise: R-Type callname: "+ callName);
			callList = callNameMap.get(callName);
			CallEntity ce = new CallEntity();
			ce.setStage(stageid);
			ce.setCallName(callName);
			ce.setMandatory(callList.get(1).toString().equals("Y")?true:false);	
			ce.setIgnorable(callList.get(2).toString().equals("Y")?true:false);
			ce.setProductType(callList.get(3).toString());
			returnObj.add(ce);			
		}

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"End getPreqCallStageWise Method" + returnObj);
		return returnObj;
	}



	public HashMap<String, String> requestToDefaultValueMap() throws NGException {
		HashMap<String, String> defaultAttributeMap = new HashMap<String, String>();
		defaultAttributeMap = StageCallCache.getReference().getCachedDefaultMap();
		//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,defaultAttributeMap.toString());
		return defaultAttributeMap;		
	}

	public ApplicationAttributes[] getApplicationAttributes(String xml) throws NGException {
		ApplicationAttributes[] aaa = new ApplicationAttributes[1];
		AttributeDetails[] ada = new AttributeDetails[1];
		String record = new XMLParser(xml).getValueOf("Records");
		HashMap<String, String> fieldVal;
		try {
			fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+record);
			Attributes[] ata = new Attributes[fieldVal.size()]; 
			Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
			int i = 0;
			//Attributes array 
			for(Entry<String,String> entry:fieldValSet){
				Attributes a = new Attributes();
				a.setAttributeKey((String) entry.getKey());
				a.setAttributeValue((String) entry.getValue());
				ata[i] = a;
				i++;
			}
			//Attribute Details array
			AttributeDetails ad = new AttributeDetails();
			ad.setAttributes(ata);
			ada[0] = ad;

			//Application Attributes
			ApplicationAttributes aa = new ApplicationAttributes();
			aa.setAttributeDetails(ada);
			aaa[0] = aa;
		} catch (ParserConfigurationException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		} catch (SAXException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		} catch (IOException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return aaa;
	}

	public void addAudit(String sessionId, String wiName, String decision, String reason, String sourcingChannel) {
		String valList = "'"+ wiName +"', SYSTIMESTAMP, 'BPM-SYSTEM-USER', 'BPM-SYSTEM', '"+ decision +"', '"+ reason +"', '"+ sourcingChannel +"'";
		try {
			APCallCreateXML.APInsert("USR_0_CBG_DEC_HIST", "WI_NAME, DEC_DATE_TIME, USERNAME, USER_GROUPNAME, DECISION, REASON, SOURCING_CHANNEL"
					,valList , sessionId);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}

	public void sendEmailSMS(String sessionId, String notificationType, String custID, String accNumber, String txnType, 
			String SMSTmpId, String SMSTxtValues, String mobNo, String emailTmpId, String emailAddr, String flxFilter1, String flxFilter2, 
			String status, String wi_name, String prefLanguage) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," ouput xml  for sendEmailSMS ==> ");
		String tableName = "USR_0_CBG_NOTIFY";
		String columnName = "CUSTOMERID, ACCTNUMBER, TRANSACTIONTYPE, SMSTEMPLATEID, SMSTEXTVALUES, MOBILENUMBER, EMAILTEMPLATEID, EMAILTEXTVALUES,"
				+ "EMAILADDRESS, FLEXIFILLER1, FLEXIFILLER2, STATUS, WI_NAME, ENTRY_DATE_TIME, SENDASCHANNEL, PREFFEREDLANGUAGE";
		String strValues = "";
		try {                
			if("B".equalsIgnoreCase(notificationType)){
				strValues = "'"+ custID +"', '"+ accNumber +"', '"+txnType+"', "+ SMSTmpId +", '"+ SMSTxtValues +"', '"+ mobNo +"', "
						+ emailTmpId +", '"+ SMSTxtValues +"' , '"+ emailAddr +"', '"+ flxFilter1 +"', '"+ flxFilter2 +"', '"+ status +"', '"+ wi_name +"', sysdate, 'B','"+prefLanguage+"'";
				APCallCreateXML.APInsert(tableName, columnName, strValues, sessionId);
			} else if("S".equalsIgnoreCase(notificationType)){
				strValues = "'"+ custID +"', '"+ accNumber +"', '"+txnType+"', "+ SMSTmpId +", '"+ SMSTxtValues +"', '"+ mobNo +"', "
						+ emailTmpId +", '"+ SMSTxtValues +"' , '"+ emailAddr +"', '"+ flxFilter1 +"', '"+ flxFilter2 +"', '"+ status +"', '"+ wi_name +"', sysdate, 'S','"+prefLanguage+"'";
				APCallCreateXML.APInsert(tableName, columnName, strValues, sessionId);
			} else if("B-MIB".equalsIgnoreCase(notificationType)){
				columnName = "CUSTOMERID, ACCTNUMBER, TRANSACTIONTYPE, SMSTEMPLATEID, SMSTEXTVALUES, MOBILENUMBER, EMAILTEMPLATEID, EMAILTEXTVALUES,"
						+ "EMAILADDRESS, FLEXIFILLER2, FLEXIFILLER3, STATUS, WI_NAME, ENTRY_DATE_TIME, SENDASCHANNEL, PREFFEREDLANGUAGE";
				strValues = "'"+ custID +"', '"+ accNumber +"', '"+txnType+"', "+ SMSTmpId +", '"+ SMSTxtValues +"', '"+ mobNo +"', "
						+ emailTmpId +", '"+ SMSTxtValues +"' , '"+ emailAddr +"', '"+ flxFilter1 +"', '"+ flxFilter2 +"', '"+ status +"', '"+ wi_name +"', sysdate, 'B','"+prefLanguage+"'";
				APCallCreateXML.APInsert(tableName, columnName, strValues, sessionId);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}

	public String maskCreditCardNo(String CCNo){
		char[] ccNumArr = null;
		try {
			//first 4 - last 6
			ccNumArr = CCNo.toCharArray();
			for(int counter = 0; counter < ccNumArr.length; counter++){
				if(counter > 3 && counter < 12){
					ccNumArr[counter] = 'X';
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		return String.valueOf(ccNumArr);
	}




	public String[][] getResponseList(String sResponse){

		String []blocks = sResponse.split("\\|\\|");
		String [][]fields = new String[blocks.length][];
		int counter=0;
		for(String str:blocks){
			String []innerBlock = str.split("\\|");
			fields[counter] = innerBlock;
			++counter;
		}
		return fields;
	}

	public String decryptPayload(String sessionId, String encryptedCardDetails) throws NGException{
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + CBGConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>CBG</Calltype>").append("\n")
		.append("<CBGCallType>Decrypt</CBGCallType>").append("\n")
		.append("<EncryptedPayLoad>"+ encryptedCardDetails +"</EncryptedPayLoad>").append("\n")
		.append("</APWebService_Input>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Decrypt Payload input xml:" + inputXml.toString());
		String outputXML = ExecuteXML.executeXML(inputXml.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Decrypt Payload outputXml:");
		XMLParser xp = new XMLParser(outputXML);
		return xp.getValueOf("DecryptedPayload");
	}

	public String decryptPayload602(String sessionId, String encryptedCardDetails) throws NGException{
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + CBGConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>CBG</Calltype>").append("\n")
		.append("<CBGCallType>Decrypt602</CBGCallType>").append("\n")
		.append("<EncryptedPayLoad>"+ encryptedCardDetails +"</EncryptedPayLoad>").append("\n")
		.append("</APWebService_Input>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Decrypt602 Payload input xml:" + inputXml.toString());
		String outputXML = ExecuteXML.executeXML(inputXml.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Decrypt602 Payload outputXml:" );
		XMLParser xp = new XMLParser(outputXML);
		return xp.getValueOf("DecryptedPayload");
	}

	public String encryptPayLoad(String sessionId, String payLoad) throws NGException{
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + CBGConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>CBG</Calltype>").append("\n")
		.append("<CBGCallType>Encrypt</CBGCallType>").append("\n")
		.append("<PayLoad>"+ payLoad +"</PayLoad>").append("\n")
		.append("</APWebService_Input>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Encrypt Payload input xml:" + inputXml.toString());
		String outputXML = ExecuteXML.executeXML(inputXml.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Encrypt Payload outputXml:");
		XMLParser xp = new XMLParser(outputXML);
		return xp.getValueOf("EncryptedPayload");
	}

	public HashMap<Integer, Integer> getEventDetector(int processID, String channelName, String appVersion, String journeyType, int stageID) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getEventDetector Method processID:"+ processID+ " channelName:"+channelName+ " appVersion:"+appVersion+" journeyType:"+journeyType+" stageID"+ stageID);
		HashMap<Integer, Integer> eventStageMap = ProcessEventCache.getReference().getEventDetectorMap().get(processID).get(channelName).get(appVersion).get(journeyType).get(stageID);
		return eventStageMap;
	}

	public ArrayList<String> getEventDetails(int eventID) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getEventDetails Method");
		ArrayList<String> eventDetails = ProcessEventCache.getReference().getEventDetailsMap().get(eventID);
		return eventDetails;
	}

	public List<CallEntity> getAsyncCallEventWise(int eventId) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method");
		List<CallEntity> returnObj = new ArrayList<CallEntity>();
		HashMap<String, ArrayList<Integer>> asynCallListMap = ProcessEventCache.getReference().getEventReactorMap().get(eventId);
		if(asynCallListMap != null){
			ArrayList<Integer> callList = asynCallListMap.get("A");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  ProcessEventCache.getReference().getCallDependencyMap().get(eventId);
				if(callList != null) {
					for (Integer callID : callList) {
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce = createCallEntity(eventId+"", callID, "A", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						returnObj.add(ce);
					}
				}
			}
			callList = asynCallListMap.get("S");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  ProcessEventCache.getReference().getCallDependencyMap().get(eventId);
				if(callList != null) {
					for (Integer callID : callList) {
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce = createCallEntity(eventId+"", callID, "S", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						returnObj.add(ce);
					}
				}
			}		
		}
		return returnObj;
	}

	public List<CallEntity> getSyncCallEventWise(int eventId) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method");
		List<CallEntity> returnObj = new ArrayList<CallEntity>();
		HashMap<String, ArrayList<Integer>> asynCallListMap = ProcessEventCache.getReference().getEventReactorMap().get(eventId);
		if(asynCallListMap != null){
			ArrayList<Integer> callList = asynCallListMap.get("S");
			if(callList != null){
				HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
				HashMap<Integer, ArrayList<Integer>> dependencyCallCache =  ProcessEventCache.getReference().getCallDependencyMap().get(eventId);
				if(callList != null) {
					for (Integer callID : callList) {
						ArrayList<String> call = callCache.get(callID);
						CallEntity ce = createCallEntity(eventId+"", callID, "S", call, dependencyCallCache!=null? dependencyCallCache.get(callID): null);
						returnObj.add(ce);
					}
				}
			}		
		}	
		return returnObj;
	}

	public CallEntity createCallEntity(String eventId, int callID, String executionType, ArrayList<String> call, ArrayList<Integer> dependencyCallList){
		CallEntity ce = new CallEntity();
		ce.setStage(eventId+"");
		ce.setCallID(callID);
		ce.setCallName(call.get(0).toString());
		ce.setExecutionType(executionType);
		ce.setMandatory(call.get(1).toString().equals("Y")?true:false);	
		ce.setIgnorable(call.get(2).toString().equals("Y")?true:false);
		ce.setProductType(call.get(3).toString());
		ce.setDependencyCallID(dependencyCallList);
		return ce;
	}*/

	public HashMap<Integer, Integer> getEventDetector(int processID, String channelName, String appVersion, String journeyType, int stageID) throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside getEventDetector Method processID:"+ processID+ " channelName:"+channelName+ " appVersion:"+appVersion+" journeyType:"+journeyType+" stageID"+ stageID);
		HashMap<Integer, Integer> eventStageMap = ProcessEventCache.getReference().getEventDetectorMap().get(processID).get(channelName).get(appVersion).get(journeyType).get(stageID);
		return eventStageMap;
	}
	
	public CallEntity createCallEntity(String eventId, int callID, String executionType, ArrayList<String> call, ArrayList<Integer> dependencyCallList){
		CallEntity ce = new CallEntity();
		ce.setStage(eventId+"");
		ce.setCallID(callID);
		ce.setCallName(call.get(0).toString());
		ce.setExecutionType(executionType);
		ce.setMandatory(call.get(1).toString().equals("Y")?true:false);	
		ce.setIgnorable(call.get(2).toString().equals("Y")?true:false);
		ce.setProductType(call.get(3).toString());
		ce.setDependencyCallID(dependencyCallList);
		return ce;
	}
	public static String executeRestAPI(String url, String inputXML) throws Exception {
		StringBuffer outputXML = new StringBuffer("");
		HttpURLConnection conn = null;
		try {
			  //log.debug("URL ..." + url);
			URL urlName = new URL(url);
			conn = (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			//log.debug("conn.getResponseCode()===> " + conn.getResponseCode());
			//log.debug(" HttpURLConnection.HTTP_CREATED===> " + HttpURLConnection.HTTP_OK);
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			//log.debug("Output from server ...\n");
			String out;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
			}
			//log.debug("RestAPI output===> " + outputXML);
			// } catch (MalformedURLException e) {
			// log.debug("RestAPI exception1===> " + e.getMessage());
			// } catch (IOException e) {
			// log.debug("RestAPI exception2===> " + e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return outputXML.toString();
	}
	

	
	public static void updatePushMeassgeXML(String sessionId,String workItemNumber,String pushMessageXML) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute Updatecalloutput called");
		APCallCreateXML.APUpdate("EXT_WBG_AO", "PUSH_MSG_XML", "'"+pushMessageXML+"'", "wi_name='"+workItemNumber+"'", sessionId);
		//ProdCreateXML.WMCompleteWorkItem(sessionId, workItemNumber, 1);
	}
	
	public static String createPushMesgXML(String leadRefNo,String workItemNumber,String statusCodeMandatoryCheck,
			String errorCodeMandatoryCheck,String errorDescMandatoryCheck){
		StringBuilder pushMessageXML=new StringBuilder();
		pushMessageXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		pushMessageXML.append("<GetCDCMQResponse>");
		pushMessageXML.append("<LeadRefNumber>"+leadRefNo+"</LeadRefNumber>");
		pushMessageXML.append("<WorkItemName>"+workItemNumber+"</WorkItemName>");
		pushMessageXML.append("<CustomerId/>");
		pushMessageXML.append("<DebitCardNum/>");
		pushMessageXML.append("<EmbossName/>");
		pushMessageXML.append("<Status>"+statusCodeMandatoryCheck+"</Status>");
		pushMessageXML.append("<Code>"+errorCodeMandatoryCheck+"</Code>");
		pushMessageXML.append("<Description>"+errorDescMandatoryCheck+"</Description>");
		pushMessageXML.append("</GetCDCMQResponse>");
		
		return pushMessageXML.toString();
	}
	
	public static boolean isItqanMCallExecuted(String callName,String wiName){
		boolean isCallExecuted = true;
		String outputXML = "";
		String callStatus = "";
		try {
			outputXML = APCallCreateXML.APSelect("SELECT CALL_STATUS FROM BPM_CALL_OUT  WHERE WI_NAME = N'" + wiName + "' AND CALL_NAME = '"+callName+"'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){		
					callStatus = xp.getValueOf("CALL_STATUS");
					if(callStatus.equalsIgnoreCase("F")){
						isCallExecuted = false;
					} else {
						isCallExecuted = true;
					}
				} else {
					isCallExecuted = false;
				}
			}
		} catch (NGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isCallExecuted;
	}
	
	public static boolean isCallNameInBpmCallOut(String callName,String wiName){
		boolean isCallPresent = true;
		String outputXML = "";
		int count;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT COUNT(CALL_STATUS) AS COUNT FROM BPM_CALL_OUT  WHERE WI_NAME = N'" + wiName + "' AND CALL_NAME = '"+callName+"'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){		
					count = Integer.parseInt(xp.getValueOf("COUNT"));
					if(count > 0){
						isCallPresent = true;
					} else {
						isCallPresent = false;
					}
				} 
			}
		} catch (NGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isCallPresent;
	}

	//added by reyaz 09-05-2023
	public int updateDataInDB(String query) {
		Connection con  = null;
		try {
			con  = NGDBConnection.getDBConnection(LapsConfigurations.getInstance().CabinetName, "APUpdate");
			CallableStatement cstmt = con.prepareCall(query);
			int count = cstmt.executeUpdate();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Row update: "+count);
			NGDBConnection.closeDBConnection(con, "APUpdate");
			con = null;
			return 0;
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			return 1;
		} finally {
			try {
				if (con != null) {
					NGDBConnection.closeDBConnection(con, "APUpdate");
					con = null;
				}
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
	}
}
