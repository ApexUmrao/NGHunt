/**
 * BUG-ID		Changed DT		Changed By		Description
 * COLMP-759	04/10/2023		AJAY			Added method prepareJsonDecTxn()
 */
package com.newgen.cbg.utils;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import adminclient.OSASecurity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.newgen.cbg.cache.ProcessEventCache;
import com.newgen.cbg.cache.StageCallCache;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.AttributeDetails;
import com.newgen.cbg.request.Attributes;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;
import com.newgen.omni.wf.util.excp.NGException;

public class DSCOPUtils {

	private static DSCOPUtils instance = null;

	private DSCOPUtils(){

	}

	public static DSCOPUtils getInstance(){
		if(instance == null){
			instance = new DSCOPUtils();
		}
		return instance;
	}

	public String generateSysRefNumber(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT CBG_REFNO.NEXTVAL SYSREFNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}
	
	//added by Sanjay Kumar removingZeros
	public String removalLeadingZeros(String str){
		String strPattern="^0+(?!$)";
		str=str.replaceAll(strPattern, "");
		return str;
	}
	
	//addded by shivanshu 05/05/2023
	public String generateSysRefNumberFSK(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT FSK_SEQ.NEXTVAL SYSREFNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("SYSREFNO");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}


	public String generateUAEPASSRequestID(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COP_UAEPASS_REQUESTID.NEXTVAL REQUESTID FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("REQUESTID");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}

	public String generateJMBR_AWBNumber(){
		String sysNum = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT lpad(CBG_AWMNO.NEXTVAL,7,'0') AWMNO FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			sysNum = xp.getValueOf("AWMNO");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return sysNum;
	}

	public String formatToBPMDate(String dt){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(dt);
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			dt = sdf.format(date);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;

	}

	public String formatToBPMDateTime(String dt){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"BPMTODATETIME: "+dt);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = sdf.parse(dt);
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dt = sdf.format(date);	
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"return date: "+dt);
		} catch (Exception e) {
	    //		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
		return dt;
	}

	//	public List<CallEntity> getCallStageWise(String stage, String WI_NAME) throws NGException {
	//		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getCallStageWise Method");
	//		List<CallEntity> returnObj = new ArrayList<CallEntity>();
	//		//		HashMap<String, ArrayList<String>> callNameMap = StageCallCache.getReference().getCachedCallNameMap();
	//		HashSet<Integer> callList1 = StageCallCache.getReference().getCachedStageCallMap().get(Integer.parseInt(stage));
	//		if(callList1 != null){
	//			ArrayList<Integer> callList = new ArrayList<Integer>(callList1);
	//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"GetCallStageWise: BEFORE R-Type callList: "+ callList);
	//			//			if(!WI_NAME.equals("")){
	//			//				String outputXML = APCallCreateXML.APSelect("SELECT CALL_NAME FROM USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'"+ WI_NAME +"' AND CALL_STATUS = N'R'");
	//			//				XMLParser xp = new XMLParser(outputXML);
	//			//				int start = xp.getStartIndex("Records", 0, 0);
	//			//				int deadEnd = xp.getEndIndex("Records", start, 0);
	//			//				int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
	//			//				int end = 0;
	//			//				int count = 0;
	//			//				for(int i = 0; i < noOfFields; ++i) {
	//			//					start = xp.getStartIndex("Record", end, 0);
	//			//					end = xp.getEndIndex("Record", start, 0);
	//			//					String callName  = xp.getValueOf("CALL_NAME", start, end);
	//			//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"GetCallStageWise: R-Type callname: "+ callName);
	//			//					int callId = Integer.parseInt((callNameMap.get(callName)).get(0).toString());
	//			//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"GetCallStageWise: R-Type callid: "+ callId);
	//			//					callList.add(callId);
	//			//				}
	//			//				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"GetCallStageWise: AFTER R-Type callList: "+ callList);
	//			//			}
	//
	//			HashMap<Integer, ArrayList<String>> callCache = StageCallCache.getReference().getCachedCallMap(); 
	//			if(callList != null) {
	//				for (Integer object : callList) {
	//					ArrayList<String> call = callCache.get(object);
	//					CallEntity ce = new CallEntity();
	//					ce.setStage(stage);
	//					ce.setCallName(call.get(0).toString());
	//					ce.setMandatory(call.get(1).toString().equals("Y")?true:false);	
	//					ce.setIgnorable(call.get(2).toString().equals("Y")?true:false);
	//					ce.setProductType(call.get(3).toString());
	//					returnObj.add(ce);
	//				}
	//			}
	//		}
	//		//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"End getCallStageWise Method" + returnObj);
	//		return returnObj;
	//	}

	//	public List<CallEntity> getPreqCallStageWise(String stageid, String WI_NAME) throws NGException {
	//		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getPreqCallStageWise Method");
	//		HashMap<String, ArrayList<String>> callNameMap = StageCallCache.getReference().getCachedCallNameMap();
	//		ArrayList<String> callList = new ArrayList<String>();
	//		List<CallEntity> returnObj = null;
	//		String outputXML = APCallCreateXML.APSelect("SELECT CALL_NAME FROM USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'"+ WI_NAME +"' AND CALL_STATUS = N'R' ORDER BY STAGE_ID");
	//		XMLParser xp = new XMLParser(outputXML);
	//		int start = xp.getStartIndex("Records", 0, 0);
	//		int deadEnd = xp.getEndIndex("Records", start, 0);
	//		int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
	//		if(noOfFields > 0){
	//			returnObj = new ArrayList<CallEntity>();
	//		}
	//		int end = 0;
	//		for(int i = 0; i < noOfFields; ++i) {
	//			start = xp.getStartIndex("Record", end, 0);
	//			end = xp.getEndIndex("Record", start, 0);
	//			String callName  = xp.getValueOf("CALL_NAME", start, end);
	//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"GetCallStageWise: R-Type callname: "+ callName);
	//			callList = callNameMap.get(callName);
	//			CallEntity ce = new CallEntity();
	//			ce.setStage(stageid);
	//			ce.setCallName(callName);
	//			ce.setMandatory(callList.get(1).toString().equals("Y")?true:false);	
	//			ce.setIgnorable(callList.get(2).toString().equals("Y")?true:false);
	//			ce.setProductType(callList.get(3).toString());
	//			returnObj.add(ce);			
	//		}
	//
	//		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"End getPreqCallStageWise Method" + returnObj);
	//		return returnObj;
	//	}

	public HashMap<String, String> requestToAttributeMap(ApplicationAttributes[] ApplicationAttributes){
		HashMap<String, String> attributeMap = new HashMap<String, String>();
		//		Map<String, List<List<Map<String, String>>>> multipleAttributeMap  =  new HashMap<String, List<List<Map<String,String>>>>();
		for (ApplicationAttributes applicationAttributes : ApplicationAttributes) {
			AttributeDetails[] ad = applicationAttributes.getAttributeDetails();
			//String attrDetailName = applicationAttributes.getName();
			List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>();
			if(ad[0]!=null){
				if(ad.length==1){
					for (Attributes attr : ad[0].getAttributes()) {
						//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Key:"+attr.getAttributeKey()+ " Value:"+attr.getAttributeValue());
						attributeMap.put(attr.getAttributeKey(), attr.getAttributeValue().replace("'","''"));
					} 
				}
				else {
					for (AttributeDetails attributeDetails : ad) {
						List<Map<String, String>> list1 = new ArrayList<Map<String,String>>();
						for (Attributes attr : attributeDetails.getAttributes()) {
							Map<String, String> at = new HashMap<String, String>();
							//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Key:"+attr.getAttributeKey()+ " Value:"+attr.getAttributeValue());
							at.put(attr.getAttributeKey(), attr.getAttributeValue().replace("'","''"));
							list1.add(at);
						} 
						list.add(list1);
					}
					//multipleAttributeMap.put(attrDetailName, list);
				}
			}
		}
		return attributeMap;
	}

	public HashMap<String, String> requestToDefaultValueMap() throws NGException {
		HashMap<String, String> defaultAttributeMap = new HashMap<String, String>();
		defaultAttributeMap = StageCallCache.getReference().getCachedDefaultMap();
		//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,defaultAttributeMap.toString());
		return defaultAttributeMap;		
	}
	

	public ApplicationAttributes[] getApplicationAttributes(String xml) throws NGException {
		ApplicationAttributes[] aaa = new ApplicationAttributes[1];
		AttributeDetails[] ada = new AttributeDetails[1];
		String record = new XMLParser(xml).getValueOf("Records");
		record = record.replace("&", "&amp;");
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
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		} catch (SAXException e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		} catch (IOException e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
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
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
	}

	public void sendEmailSMS(String sessionId, String notificationType, String custID, String accNumber, String txnType, 
			String SMSTmpId, String SMSTxtValues, String mobNo, String emailTmpId, String emailAddr, String flxFilter1, String flxFilter2, 
			String status, String wi_name, String prefLanguage) {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO," ouput xml  for sendEmailSMS ==> ");
		String tableName = "USR_0_DSCOP_NOTIFY";
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
						+ emailTmpId +", '"+ SMSTxtValues +"' , '"+ emailAddr +"', '"+ flxFilter1 +"', '"+ flxFilter2 +"', '"+ status +"', '"+ wi_name +"', sysdate, 'E','"+prefLanguage+"'";
				APCallCreateXML.APInsert(tableName, columnName, strValues, sessionId);
			} else if("E".equalsIgnoreCase(notificationType)){
				strValues = "'"+ custID +"', '"+ accNumber +"', '"+txnType+"', "+ SMSTmpId +", '"+ SMSTxtValues +"', '"+ mobNo +"', "
						+ emailTmpId +", '"+ SMSTxtValues +"' , '"+ emailAddr +"', '"+ flxFilter1 +"', '"+ flxFilter2 +"', '"+ status +"', '"+ wi_name +"', sysdate, 'E','"+prefLanguage+"'";
				APCallCreateXML.APInsert(tableName, columnName, strValues, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
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
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
		return String.valueOf(ccNumArr);
	}

	public void updateAccRelSno(String WI_NAME,String sessionId){
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT NAME FROM ACC_RELATION_REPEATER WHERE WI_NAME = N'"+ WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(totalRetrieved > 0){
					int start = xp.getStartIndex("Records", 0, 0);
					int end = 0;
					for(int i=1 ; i <= totalRetrieved ; i++ ){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String name = xp.getValueOf("NAME", start, end);
						APCallCreateXML.APUpdate("ACC_RELATION_REPEATER", "SNO", "'"+i+"'", " WI_NAME = N'"+ WI_NAME +"' AND NAME = N'"+ name +"'", sessionId);

					}

				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
		}
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
		.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>DSCOP</Calltype>").append("\n")
		.append("<CBGCallType>Decrypt</CBGCallType>").append("\n")
		.append("<EncryptedPayLoad>"+ encryptedCardDetails +"</EncryptedPayLoad>").append("\n")
		.append("</APWebService_Input>");
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Decrypt Payload input xml:" + inputXml.toString());
		String outputXML = ExecuteXML.executeWebServiceSocket(inputXml.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Decrypt Payload outputXml:");
		XMLParser xp = new XMLParser(outputXML);
		return xp.getValueOf("DecryptedPayload");
	}

	public String decryptPayload602(String sessionId, String encryptedCardDetails) throws NGException{
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>DSCOP</Calltype>").append("\n")
		.append("<CBGCallType>Decrypt602</CBGCallType>").append("\n")
		.append("<EncryptedPayLoad>"+ encryptedCardDetails +"</EncryptedPayLoad>").append("\n")
		.append("</APWebService_Input>");
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Decrypt602 Payload input xml:" + inputXml.toString());
		String outputXML = ExecuteXML.executeWebServiceSocket(inputXml.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Decrypt602 Payload outputXml:" );
		XMLParser xp = new XMLParser(outputXML);
		return xp.getValueOf("DecryptedPayload");
	}

	public String encryptPayLoad(String sessionId, String payLoad) throws NGException{
		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<Calltype>DSCOP</Calltype>").append("\n")
		.append("<CBGCallType>Encrypt</CBGCallType>").append("\n")
		.append("<PayLoad>"+ payLoad +"</PayLoad>").append("\n")
		.append("</APWebService_Input>");
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Encrypt Payload input xml:" + inputXml.toString());
		String outputXML = ExecuteXML.executeWebServiceSocket(inputXml.toString());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " Encrypt Payload outputXml:");
		XMLParser xp = new XMLParser(outputXML);
		return xp.getValueOf("EncryptedPayload");
	}

	public HashMap<Integer, Integer> getEventDetector(int processID, String channelName, String appVersion, String journeyType, int stageID) throws NGException {
		HashMap<Integer, Integer> eventStageMap =null;
		try {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method processID:"+ processID+ " channelName:"+channelName+ " appVersion:"+appVersion+" journeyType:"+journeyType+" stageID:"+ stageID);
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method gaurav");
			ProcessEventCache object = ProcessEventCache.getReference();
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method object:"+ object);
			HashMap<Integer, HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>>> obj=object.getEventDetectorMap();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method getEventMap:"+object.getEventMap().toString());
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method obj:"+ obj);
			HashMap<String, HashMap<String, HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>>>> obj2 = obj.get(processID);
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method obj2:"+ obj2);
			HashMap<String, HashMap<String, HashMap<Integer, HashMap<Integer, Integer>>>> obj3= obj2.get(channelName.toUpperCase());
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method obj3:"+ obj3);
			HashMap<String,  HashMap<Integer, HashMap<Integer, Integer>>> obj4 = obj3.get(appVersion.toUpperCase());
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method obj4:"+ obj4);
			HashMap<Integer, HashMap<Integer, Integer>> obj5 = obj4.get(journeyType.toUpperCase());
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method obj5:"+ obj5);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method stageID:"+stageID);
			eventStageMap = obj5.get(stageID);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method processID:"+eventStageMap.toString());
			
			//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetector Method eventStageMap:"+ eventStageMap);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,"getEventDetector error :"+e);
			e.printStackTrace();
		}
		return eventStageMap;
	}

	public ArrayList<String> getEventDetails(int eventID) throws NGException {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getEventDetails Method");
		ArrayList<String> eventDetails = ProcessEventCache.getReference().getEventDetailsMap().get(eventID);
		return eventDetails;
	}

	public List<CallEntity> getAsyncCallEventWise(int eventId) throws NGException {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method eventId: "+eventId);
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
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Inside getAsyncCallEventWise Method eventId: "+eventId);
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
	}

	public void insertDuplicateValue(String WI_NAME, String dedupeType, String systemType, String sessionId)
	{
		XMLParser xp;
		String outputXML;
		int mainCode;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_MOBILE_NO, CUSTOMER_ID, CUSTOMER_FULL_NAME, CUSTOMER_EMAIL, PASSPORT_NO, DOB, NATIONALITY, EIDA_NUMBER FROM EXT_DSCOP WHERE WI_NAME = N'"
					+ WI_NAME + "'");
			xp = new XMLParser(outputXML);
			xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				String mobileNumber = xp.getValueOf("CUSTOMER_MOBILE_NO");
				String custID = xp.getValueOf("CUSTOMER_ID");
				String custName = xp.getValueOf("CUSTOMER_FULL_NAME");
				String custEmail = xp.getValueOf("CUSTOMER_EMAIL");
				String custPassport = xp.getValueOf("PASSPORT_NO");
				String custEida = xp.getValueOf("EIDA_NUMBER");
				String dob = xp.getValueOf("DOB");
				String custNationality = xp.getValueOf("NATIONALITY");
				String newDob="to_date('" + dob+"','DD-MM-YYYY')";

				String columnName = "WI_NAME , DEDUPE_TYPE, CUST_SNO, CUST_ID,CUST_NAME ,CUST_DOB ,CUST_EMAIL, CUST_MOBILE, CUST_PASSPORT ,CUST_EIDA, CUST_NATIONALITY, SYSTEM_TYPE ,INSERTION_DATE";
				String strValues = "'" + WI_NAME + "','"+dedupeType+"', '1', '" + custID+ "', '" + custName + "',"+newDob+", '" + custEmail + "', '"+ mobileNumber + "', '" + custPassport + "', '"+ custEida + "', '"+custNationality+"', '"+systemType+"',SYSDATE";
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Column values for BPM_SUPP_DEDUPE_DATA are :  " +strValues);
				APCallCreateXML.APInsert("BPM_DSCOP_DEDUPE_DATA",columnName, strValues, sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
	}

	public void insertMyChoiceKYC(String WI_NAME, HashMap<String, String> attributeMap, String sessionId){
		try {
			StringBuilder keyList = new StringBuilder("");
			StringBuilder valList = new StringBuilder("");
			Set<Map.Entry<String, String>> entrySet = attributeMap.entrySet();
			Iterator<Entry<String,String>> entrySetIterator = entrySet.iterator();
			while(entrySetIterator.hasNext()){
				Entry<String,String> entry = entrySetIterator.next();
				keyList = keyList.append(entry.getKey()+",");
				valList = valList.append("'"+ entry.getValue() +"',");	
			}
			keyList.append("WI_NAME,");
			valList.append("'"+WI_NAME+"',");
			keyList.deleteCharAt(keyList.lastIndexOf(","));
			valList.deleteCharAt(valList.lastIndexOf(","));
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "keyList: "+ keyList);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "valList: "+ valList);

			String outputXML = APCallCreateXML.APInsert("BPM_MYCHOICE_KYC", keyList.toString(), valList.toString(), sessionId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"MyChoice KYC Insert in execute call: "+ outputXML);
			XMLParser xp = new XMLParser(outputXML);
			int status = Integer.parseInt(xp.getValueOf("MainCode"));
			if(status==0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"MyChoice KYC data inserted.");
			}
		}
		catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
	}

	public String segmentJSON(String WI_NAME){

		String returnVal="";
		try {
			String outputXML =APCallCreateXML.APSelect("SELECT BANKING_TYPE, PREFERRED_LANGUAGE FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME='"+WI_NAME+"'");
			XMLParser xp = new XMLParser(outputXML);
			//			String bankingType = xp.getValueOf("BANKING_TYPE");
			String language = xp.getValueOf("PREFERRED_LANGUAGE");
			JsonArray jArrayASPIRE = new JsonArray();
			JsonArray jArrayEMIRATI = new JsonArray();
			JsonArray jArrayPRIVILEGE = new JsonArray();
			JsonArray jArrayEXCELLANCY = new JsonArray();
			String[] productType = {"CONVENTIONAL","ISLAMIC"}; 
			String[] bankingTypeArray = {"C","I"}; 
			for (int j = 0; j < productType.length; j++) {
				String bankingType = bankingTypeArray[j];
				//				if("I".equals(bankingType)){
				//					returnVal="'{\"segmentEligibility\":\"Y\", \"BankingType\":\"I\", \"segmentName\":\"Aspire\", \"segmentDescription\":\"ADCB Aspire offers you products and services supported by an extensive network of branches and ATMs across the UAE. Our award-winning facilities ensure that you enjoy all of the benefits of the bank at your convenience!\", \"segmentWebsiteURL\":\"https://www.adcb.com/en/personal/adcb-for-you/segments/aspire/\", \"accountProducts\":[{\"productName\":\"Current Account\", \"productCode\":\"217\"},{\"productName\":\"Savings Account\", \"productCode\":\"133\"}], \"CardProducts\":[ {\"productName\":\"TouchPoints Gold - Islamic banking \", \"productCode\":\"149\", \"productDesc\":\"Unlock a world of privileges and benefits, from online food delivery discounts to entertainment deals and much more.\", \"cardURL\":\"https://www.adcb.com/en/personal/cards/credit-cards/touchpoint-platinum-cc\"}]}' AS SEGMENT_ASPIRE,";
				//				}else{
				//					returnVal="'{\"segmentEligibility\":\"Y\", \"BankingType\":\"I\", \"segmentName\":\"Aspire\", \"segmentDescription\":\"ADCB Aspire offers you products and services supported by an extensive network of branches and ATMs across the UAE. Our award-winning facilities ensure that you enjoy all of the benefits of the bank at your convenience!\", \"segmentWebsiteURL\":\"https://www.adcb.com/en/personal/adcb-for-you/segments/aspire/\", \"accountProducts\":[{\"productName\":\"Current Account\", \"productCode\":\"132\"},{\"productName\":\"Savings Account\", \"productCode\":\"133\"}], \"CardProducts\":[ {\"productName\":\"TouchPoints Titanium\", \"productCode\":\"148\", \"productDesc\":\"Multiply your benefits with bonus monthly rewards, entertainment deals and much more.\", \"cardURL\":\"https://www.adcb.com/en/personal/cards/credit-cards/touchpoint-tg-cc\"}, {\"productName\":\"Simplylife Cashback\", \"productCode\":\"205\", \"productDesc\":\"Benefit from a wide range of features daily. Get cash back on spends as low as AED 1000, along with free movie tickets and a host of other rewards.\", \"cardURL\":\"https://www.simplylife.ae/cards/credit-card.aspx\"}]}' AS SEGMENT_ASPIRE,";
				//				}

				outputXML = APCallCreateXML.APSelect("SELECT PRIMARY_SEG, CARD_PRODUCTS, CARD_PRODUCT_CODES, PRIMARY_SEG_ACC_PROD, "
						+ "PRIMARY_SEG_ACC_PROD_CODES FROM USR_0_CBG_PROD_SEGMENT WHERE PRIMARY_SEG = 'Aspire' AND WI_NAME = N'"+  WI_NAME +"' AND BANKING_TYPE='"+productType[j]+"'");
				xp = new XMLParser(outputXML);
				int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(totalRetrieved > 0){
					String segmentName = xp.getValueOf("PRIMARY_SEG");
					String[] cardProduct = xp.getValueOf("CARD_PRODUCTS").split(",");
					String[] cardProductCode = xp.getValueOf("CARD_PRODUCT_CODES").split(",");
					String[] accountProduct = xp.getValueOf("PRIMARY_SEG_ACC_PROD").split(",");
					String[] accountProductCode = xp.getValueOf("PRIMARY_SEG_ACC_PROD_CODES").split(",");
					JsonObject jASPIRE = new JsonObject();

					String segmentDescription = "ADCB Aspire offers you products and services supported by an extensive network of branches and ATMs across the UAE. Our award-winning facilities ensure that you enjoy all of the benefits of the bank at your convenience!"; 
					String segmentWebsiteURL = "https://www.adcb.com/en/personal/adcb-for-you/segments/aspire/";

					JsonArray accountArray = new JsonArray();
					JsonArray cardArray = new JsonArray();

					for(int i = 0; i< accountProduct.length; i++){
						JsonObject jObj = new JsonObject();
						jObj.addProperty("productName", accountProduct[i]);
						jObj.addProperty("productCode", accountProductCode[i]);
						accountArray.add(jObj);
					}

					for(int i = 0; i< cardProduct.length; i++){
						JsonObject jObj = new JsonObject();
						jObj.addProperty("productName", cardProduct[i]);
						jObj.addProperty("productCode", cardProductCode[i]);
						if("EN".equalsIgnoreCase(language)){
							outputXML =APCallCreateXML.APSelect("SELECT CC_PRODUCT_EN_TEXT AS PRODUCT_DESC, CC_PRODUCT_URL FROM USR_0_CBG_PROD_MASTER WHERE CC_PROD_CODE='"+cardProductCode[i]+"'");
						}
						else{
							outputXML =APCallCreateXML.APSelect("SELECT CC_PRODUCT_AR_TEXT AS PRODUCT_DESC, CC_PRODUCT_URL FROM USR_0_CBG_PROD_MASTER WHERE CC_PROD_CODE='"+cardProductCode[i]+"'");
						}
						xp = new XMLParser(outputXML);
						jObj.addProperty("productDesc", xp.getValueOf("PRODUCT_DESC"));
						jObj.addProperty("cardURL",  xp.getValueOf("CC_PRODUCT_URL"));
						cardArray.add(jObj);
					}

					jASPIRE.addProperty("segmentEligibility", "Y");
					jASPIRE.addProperty("BankingType", bankingType);
					jASPIRE.addProperty("segmentName", segmentName);
					jASPIRE.addProperty("segmentDescription", segmentDescription);
					jASPIRE.addProperty("segmentWebsiteURL", segmentWebsiteURL);
					jASPIRE.add("CardProducts",cardArray);
					jASPIRE.add("accountProducts",accountArray);
					jArrayASPIRE.add(jASPIRE);

					JsonObject aspire = new JsonObject();
					aspire.add("Products", jArrayASPIRE);
					returnVal = "'"+new Gson().toJson(aspire) + "' AS SEGMENT_ASPIRE, ";
				}
				else{
					returnVal="'{\"Products\":[{\"segmentEligibility\":\"Y\",\"BankingType\":\"C\",\"segmentName\":\"Aspire\",\"segmentDescription\":\"ADCB Aspire offers you products and services supported by an extensive network of branches and ATMs across the UAE. Our award-winning facilities ensure that you enjoy all of the benefits of the bank at your convenience!\",\"segmentWebsiteURL\":\"https://www.adcb.com/en/personal/adcb-for-you/segments/aspire/\",\"accountProducts\":[{\"productName\":\"Current Account\",\"productCode\":\"132\"},{\"productName\":\"Savings Account\",\"productCode\":\"133\"}],\"CardProducts\":[{\"productName\":\"TouchPoints platinum\",\"productCode\":\"204\",\"productDesc\":\"Unlock a world of privileges and benefits, from online food delivery discounts to entertainment deals and much more.\",\"cardURL\":\"https://www.adcb.com/en/personal/cards/credit-cards/touchpoint-platinum-cc\"},{\"productName\":\"TouchPoints Titanium\",\"productCode\":\"148\",\"productDesc\":\"Multiply your benefitswith bonus monthly rewards, entertainment deals and much more.\",\"cardURL\":\"https://www.adcb.com/en/personal/cards/credit-cards/touchpoint-tg-cc\"},{\"productName\":\"Simplylife Cashback\",\"productCode\":\"205\",\"productDesc\":\"Benefit from a wide range of features daily. Get cash back on spends as low as AED 1000, along with free movie tickets and a host of other rewards.\",\"cardURL\":\"https://www.simplylife.ae/cards/credit-card.aspx\"},{\"productName\":\"Traveller Card\",\"productCode\":\"206\",\"productDesc\":\"Unlock a world of privileges and benefits, from online food delivery discounts to entertainment deals and much more.\",\"cardURL\":\"https://www.simplylife.ae/cards/credit-card.aspx\"}]}, 					 {\"segmentEligibility\":\"Y\",\"BankingType\":\"I\",\"segmentName\":\"Aspire\",\"segmentDescription\":\"ADCB Aspire offers you products and services supported by an extensive network of branches and ATMs across the UAE. Our award-winning facilities ensure that you enjoy all of the benefits ofthe bank at your convenience!\",\"segmentWebsiteURL\":\"https://www.adcb.com/en/personal/adcb-for-you/segments/aspire/\",\"accountProducts\":[{\"productName\":\"Islamic Current account\",\"productCode\":\"217\"},{\"productName\":\"MDSA\",\"productCode\":\"208\"}],\"CardProducts\":[{\"productName\":\"TouchPoints Gold - Islamic banking \",\"productCode\":\"149\",\"productDesc\":\"Unlock a world of privileges and benefits, from online food delivery discounts to entertainment deals and much more.\",\"cardURL\":\"https://www.adcb.com/en/personal/cards/credit-cards/touchpoint-platinum-cc\"}]}]}' AS SEGMENT_ASPIRE,";
				}


				outputXML = APCallCreateXML.APSelect("SELECT PRIMARY_SEG, CARD_PRODUCTS, CARD_PRODUCT_CODES, PRIMARY_SEG_ACC_PROD, "
						+ "PRIMARY_SEG_ACC_PROD_CODES FROM USR_0_CBG_PROD_SEGMENT WHERE PRIMARY_SEG != 'Aspire' AND WI_NAME = N'"+ WI_NAME +"' AND BANKING_TYPE='"+productType[j]+"'");
				xp = new XMLParser(outputXML);
				totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(totalRetrieved > 0){
					String segmentName = xp.getValueOf("PRIMARY_SEG");
					String[] cardProduct = xp.getValueOf("CARD_PRODUCTS").split(",");
					String[] cardProductCode = xp.getValueOf("CARD_PRODUCT_CODES").split(",");
					String[] accountProduct = xp.getValueOf("PRIMARY_SEG_ACC_PROD").split(",");
					String[] accountProductCode = xp.getValueOf("PRIMARY_SEG_ACC_PROD_CODES").split(",");

					JsonObject jEMIRATI = new JsonObject();
					JsonObject jPRIVILEGE = new JsonObject();
					JsonObject jEXCELLANCY = new JsonObject();

					JsonArray accountArray = new JsonArray();
					JsonArray cardArray = new JsonArray();

					for(int i = 0; i< accountProduct.length; i++){
						JsonObject jObj = new JsonObject();
						jObj.addProperty("productName", accountProduct[i]);
						jObj.addProperty("productCode", accountProductCode[i]);
						accountArray.add(jObj);
					}

					for(int i = 0; i< cardProduct.length; i++){
						JsonObject jObj = new JsonObject();
						jObj.addProperty("productName", cardProduct[i]);
						jObj.addProperty("productCode", cardProductCode[i]);
						if("EN".equalsIgnoreCase(language)){
							outputXML =APCallCreateXML.APSelect("SELECT CC_PRODUCT_EN_TEXT AS PRODUCT_DESC, CC_PRODUCT_URL FROM USR_0_CBG_PROD_MASTER WHERE CC_PROD_CODE='"+cardProductCode[i]+"'");
						}
						else{
							outputXML =APCallCreateXML.APSelect("SELECT CC_PRODUCT_AR_TEXT AS PRODUCT_DESC, CC_PRODUCT_URL FROM USR_0_CBG_PROD_MASTER WHERE CC_PROD_CODE='"+cardProductCode[i]+"'");
						}
						xp = new XMLParser(outputXML);
						jObj.addProperty("productDesc", xp.getValueOf("PRODUCT_DESC"));
						jObj.addProperty("cardURL",  xp.getValueOf("CC_PRODUCT_URL"));
						cardArray.add(jObj);
					}

					String segmentDescription = "", segmentWebsiteURL = "";
					if("EMIRATI".equalsIgnoreCase(segmentName)){
						returnVal="'{\"Products\":[{\"segmentEligibility\":\"N\",\"segmentName\":\"Aspire\"},{\"segmentEligibility\":\"N\",\"segmentName\":\"Aspire\"}]}' AS SEGMENT_ASPIRE,";
						segmentDescription="As a proud Emirati, your success is driven by your values, traditions and ambitions. To support you and your vision, we have created an entirely new banking experience called ADCB Emirati.";
						segmentWebsiteURL="https://www.adcb.com/en/personal/adcb-for-you/emirati/";

						jEMIRATI.addProperty("segmentEligibility", "Y");
						jEMIRATI.addProperty("BankingType", bankingType);
						jEMIRATI.addProperty("segmentName", segmentName);
						jEMIRATI.addProperty("segmentDescription", segmentDescription);
						jEMIRATI.addProperty("segmentWebsiteURL", segmentWebsiteURL);
						jEMIRATI.add("CardProducts",cardArray);
						jEMIRATI.add("accountProducts",accountArray);
						jArrayEMIRATI.add(jEMIRATI);

						jPRIVILEGE.addProperty("segmentEligibility", "N");
						jPRIVILEGE.addProperty("segmentName", "Privilege");
						jArrayPRIVILEGE.add(jPRIVILEGE);

						jEXCELLANCY.addProperty("segmentEligibility", "N");
						jEXCELLANCY.addProperty("segmentName", "Excellancy");
						jArrayEXCELLANCY.add(jEXCELLANCY);
					}

					if("PRIVILEGE".equalsIgnoreCase(segmentName)){
						segmentDescription="Join the ADCB Privilege Club At the ADCB Privilege Club, we provide you with a host of everyday banking solutions, tailored to compliment your lifestyle needs.";
						segmentWebsiteURL="https://www.adcb.com/en/personal/adcb-for-you/segments/privilege-club/";

						jPRIVILEGE.addProperty("segmentEligibility", "Y");
						jPRIVILEGE.addProperty("BankingType", bankingType);
						jPRIVILEGE.addProperty("segmentName", segmentName);
						jPRIVILEGE.addProperty("segmentDescription", segmentDescription);
						jPRIVILEGE.addProperty("segmentWebsiteURL", segmentWebsiteURL);
						jPRIVILEGE.add("CardProducts",cardArray);
						jPRIVILEGE.add("accountProducts",accountArray);
						jArrayPRIVILEGE.add(jPRIVILEGE);

						jEMIRATI.addProperty("segmentEligibility", "N");
						jEMIRATI.addProperty("segmentName", "Emirati");
						jArrayEMIRATI.add(jEMIRATI);

						jEXCELLANCY.addProperty("segmentEligibility", "N");
						jEXCELLANCY.addProperty("segmentName", "Excellancy");
						jArrayEXCELLANCY.add(jEXCELLANCY);
					}

					if("EXCELLANCY".equalsIgnoreCase(segmentName)){
						segmentDescription="Segment Description";
						segmentWebsiteURL="https://www.adcb.com/en/personal/adcb-for-you/emirati/";

						jEXCELLANCY.addProperty("segmentEligibility", "Y");
						jEXCELLANCY.addProperty("BankingType", bankingType);
						jEXCELLANCY.addProperty("segmentName", segmentName);
						jEXCELLANCY.addProperty("segmentDescription", segmentDescription);
						jEXCELLANCY.addProperty("segmentWebsiteURL", segmentWebsiteURL);
						jEXCELLANCY.add("CardProducts",cardArray);
						jEXCELLANCY.add("accountProducts",accountArray);
						jArrayEXCELLANCY.add(jEXCELLANCY);

						jEMIRATI.addProperty("segmentEligibility", "N");
						jEMIRATI.addProperty("segmentName", "Emirati");
						jArrayEMIRATI.add(jEMIRATI);

						jPRIVILEGE.addProperty("segmentEligibility", "N");
						jPRIVILEGE.addProperty("segmentName", "Privilege");
						jArrayPRIVILEGE.add(jPRIVILEGE);
					}
				}
				else{
					JsonObject jEMIRATI = new JsonObject();
					JsonObject jPRIVILEGE = new JsonObject();
					JsonObject jEXCELLANCY = new JsonObject();

					jEMIRATI.addProperty("segmentEligibility", "N");
					jEMIRATI.addProperty("BankingType", bankingType);
					jEMIRATI.addProperty("segmentName", "Emirati");
					jArrayEMIRATI.add(jEMIRATI);

					jPRIVILEGE.addProperty("segmentEligibility", "N");
					jPRIVILEGE.addProperty("BankingType", bankingType);
					jPRIVILEGE.addProperty("segmentName", "Privilege");
					jArrayPRIVILEGE.add(jPRIVILEGE);

					jEXCELLANCY.addProperty("segmentEligibility", "N");
					jEXCELLANCY.addProperty("BankingType", bankingType);
					jEXCELLANCY.addProperty("segmentName", "Excellancy");
					jArrayEXCELLANCY.add(jEXCELLANCY);
				}
			}
			JsonObject emirati = new JsonObject();
			emirati.add("Products", jArrayEMIRATI);

			JsonObject excellency = new JsonObject();
			excellency.add("Products", jArrayEXCELLANCY);

			JsonObject privilege = new JsonObject();
			privilege.add("Products", jArrayPRIVILEGE);

			return returnVal +" '"+new Gson().toJson(emirati) +"' AS SEGMENT_EMIRATI, '"+ 
			new Gson().toJson(excellency) +"' AS SEGMENT_EXCELLANCY, '"+
			new Gson().toJson(privilege) +"' AS SEGMENT_PRIVILEGE";

		}catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return returnVal;
	}

	public String pilProductsJSON(String WI_NAME){

		String returnVal="";
		try {
			String outputXML =APCallCreateXML.APSelect("SELECT BANKING_TYPE, PIL_ELIGIBILITY_FLAG FROM USR_0_CBG_CREDIT_ELIGIBILITY WHERE WI_NAME='"+WI_NAME+"'");
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
			int end = 0;
			JsonObject pilProducts = new JsonObject();
			JsonArray pilProductsArray = new JsonArray();
			for(int i = 0; i < noOfFields; ++i){
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String bankingType = xp.getValueOf("BANKING_TYPE", start, end);
				String pilEligibilityFlag = xp.getValueOf("PIL_ELIGIBILITY_FLAG", start, end);

				JsonArray TenorDetailsArray = new JsonArray();
				outputXML =APCallCreateXML.APSelect("SELECT TENOR, ROI, ELIGIBLE_AMOUNT FROM USR_0_CBG_PILCREDIT_MATRIX WHERE WI_NAME='"+WI_NAME+"' AND BANKING_TYPE='"+bankingType+"'");
				XMLParser xp1 = new XMLParser(outputXML);
				int start1 = xp1.getStartIndex("Records", 0, 0);
				int deadEnd1 = xp1.getEndIndex("Records", start1, 0);
				int noOfFields1 = xp1.getNoOfFields("Record", start1,deadEnd1);
				int end1 = 0;
				for(int j = 0; j< noOfFields1; j++){
					start1 = xp1.getStartIndex("Record", end1, 0);
					end1 = xp1.getEndIndex("Record", start1, 0);

					String Tenor = xp1.getValueOf("TENOR", start1, end1);
					String minAmount = "5000";//xp.getValueOf("PIL_ELIGIBILITY_FLAG", start, end);
					String maxAmount = xp1.getValueOf("ELIGIBLE_AMOUNT", start1, end1);
					String Rate = xp1.getValueOf("ROI", start1, end1);

					JsonObject jObj2 = new JsonObject();
					jObj2.addProperty("Tenor", Tenor);
					jObj2.addProperty("minAmount", minAmount);
					jObj2.addProperty("maxAmount", maxAmount);
					jObj2.addProperty("Rate", Rate);
					TenorDetailsArray.add(jObj2);
				}

				JsonObject jObj1 = new JsonObject();
				jObj1.addProperty("PILEligibility", pilEligibilityFlag);
				jObj1.addProperty("BankingType", bankingType);
				jObj1.addProperty("PILRequestedAmount", "5000");
				jObj1.add("TenorDetails", TenorDetailsArray);
				pilProductsArray.add(jObj1);

			}
			pilProducts.add("PILProducts", pilProductsArray);
			return "'"+	new Gson().toJson(pilProducts) +"' AS PIL_DETAILS";
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return returnVal;
	}

	public String bundleDetailsJSON(String WI_NAME){

		String returnVal="";
		try {
			String outputXML =APCallCreateXML.APSelect("SELECT NVL(CURRENT_ACCOUNT_ELIGIBILITY,'N') AS CURRENT_ACCOUNT_ELIGIBILITY, "
					+ "NVL(CC_ELIGIBILITY_FLAG,'N') AS CC_ELIGIBILITY_FLAG, NVL(PIL_ELIGIBILITY_FLAG,'N') AS PIL_ELIGIBILITY_FLAG, "
					+ "DEF_CRDT_LMT FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME='"+WI_NAME+"'");
			XMLParser xp = new XMLParser(outputXML);

			JsonObject bundleDetails = new JsonObject();
			bundleDetails.addProperty("currentAccountEligibility", xp.getValueOf("CURRENT_ACCOUNT_ELIGIBILITY"));
			bundleDetails.addProperty("AccountWithCardEligible", xp.getValueOf("CC_ELIGIBILITY_FLAG"));
			bundleDetails.addProperty("AccountWithLoanEligible", xp.getValueOf("PIL_ELIGIBILITY_FLAG"));
			bundleDetails.addProperty("CardEligibleAmount", xp.getValueOf("DEF_CRDT_LMT"));

			outputXML =APCallCreateXML.APSelect("SELECT MAX(ELIGIBLE_AMOUNT) AS PIL_ELIGIBLE_AMOUNT FROM USR_0_CBG_PILCREDIT_MATRIX WHERE WI_NAME='"+WI_NAME+"'");
			xp = new XMLParser(outputXML);

			bundleDetails.addProperty("LoanEligibleAmount", xp.getValueOf("PIL_ELIGIBLE_AMOUNT"));

			return "'"+	new Gson().toJson(bundleDetails) +"' AS BUNDLE_DETAILS";
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return returnVal;
	}

	public List<String> detectEvent(HashMap<String,String> attributeMap){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[ProcessEventCache][detectEvent]: attributeMap:"+attributeMap);
		try {
			HashMap<String,List<String>> eventMap = ProcessEventCache.getReference().getEventMap();
			String[] tagNames = DSCOPUtils.getInstance().requestToDefaultValueMap().get("EVENT_DETECTION_TAGS").split(",");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[ProcessEventCache][detectEvent]: tagNames:"+Arrays.toString(tagNames));
			StringBuffer key = new StringBuffer();
			for(String tagName : tagNames){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[ProcessEventCache][detectEvent]: tagName:"+tagName);
				String tagValue = attributeMap.get(tagName);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[ProcessEventCache][detectEvent]: tagValue:"+tagValue);
				key.append( tagValue == null ? "":tagValue).append("#");
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[ProcessEventCache][detectEvent]: key: "+key);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[ProcessEventCache][detectEvent] List: "+eventMap.get(String.valueOf(key)));
			return eventMap.get(String.valueOf(key));

		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return null;		
	}

	public String prepareJson(String responseXml, String listOfTags){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[DSCOPUtils][prepareJson]: INSIDE ");
		JSONArray ja = new JSONArray();
		try {
			String[] tags = listOfTags.split(",");
			XMLParser xp = new XMLParser(responseXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
			int end = 0;			
			for(int i = 0; i < noOfFields; ++i){
				JSONObject jo = new JSONObject();
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				for(String tagName: tags){
					jo.put(tagName,xp.getValueOf(tagName, start, end));
				}
				ja.put(jo);
			}
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}

		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[DSCOPUtils][prepareJson]: json "+ja.toString());
		return ja.toString();	
	}

	//done by tushar
	public JSONArray prepareJsonObj(String responseXml, String listOfTags) {
		// TODO Auto-generated method stub
		System.out.println("inside");
		JSONArray ja = new JSONArray();
		try {
			String[] tags = listOfTags.split(",");
			XMLParser xp = new XMLParser(responseXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start,deadEnd);
			int end = 0;			
			for(int i = 0; i < noOfFields; ++i){
				JSONObject jo = new JSONObject();
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				for(String tagName: tags){
					jo.put(tagName,xp.getValueOf(tagName, start, end));
				}
				ja.put(jo);
				
			}
		} catch(Exception e){
			System.out.println("exception");
		}

		System.out.println(ja.toString());
		return ja;
		
	}

	//	public boolean validateFullName(String WI_NAME, String sessionId){
	//		boolean result = false;
	//		String isNameValidated = "N";
	//		try {	
	//			String outputXML = APCallCreateXML.APSelect("SELECT UPPER(PASSPORT_FULL_NAME) AS PASSPORT_FULL_NAME,"
	//					+ "UPPER(EIDA_FULL_NAME) AS EIDA_FULL_NAME FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME = N'" + WI_NAME + "'");
	//			XMLParser xp = new XMLParser(outputXML);
	//			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
	//			if(mainCode == 0){
	//				if(!"".equals(xp.getValueOf("PASSPORT_FULL_NAME"))){
	//					String[] passportName = xp.getValueOf("PASSPORT_FULL_NAME").split(" ");
	//					String[] eidaName = xp.getValueOf("EIDA_FULL_NAME").split(" ");
	//					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passportName:::::"+passportName+"::::eidaName:::"+eidaName);
	//					
	//					for(String element : passportName) {
	//						if(!Arrays.asList(eidaName).contains(element)){
	//							result = false;
	//							isNameValidated = "Y";
	//							break;
	//							
	//						}else {
	//							isNameValidated = "N";
	//							result = true;
	//						}
	//					}
	//				}
	//			}
	//			if("N".equalsIgnoreCase(isNameValidated)){
	//				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "ValidateName", "ValidateName", "Customer Name Not Match for Passport Name Vs EIDA Name", sessionId);
	//			}else if("Y".equalsIgnoreCase(isNameValidated)){
	//				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "ValidateName", "ValidateName", "Customer Name Match for Passport Name Vs EIDA Name", sessionId);
	//			}
	//			APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "NAMESPLIT_DECISION", "'"+isNameValidated+"'", "NAMESPLIT_DECISION='N' AND WI_NAME = N'"+ WI_NAME +"'", sessionId);
	//		} catch (Exception e) {
	//			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
	//			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "ValidateFullName", "M-ValidateFullName", e, sessionId);
	//		}
	//		return result;
	//	}

	public void validateFullName(String WI_NAME, String sessionId){
		try {	
			String outputXML = APCallCreateXML.APSelect("SELECT UPPER(PASSPORT_FULL_NAME) AS PASSPORT_FULL_NAME, NVL(UAE_NATIONAL,'N') AS UAE_NATIONAL, "
					+ "UPPER(EIDA_FULL_NAME) AS EIDA_FULL_NAME, UPPER(B.PASSPORT_MANUAL_NAME) AS PASSPORT_MANUAL_NAME, NAMESPLIT_DECISION FROM EXT_CBG_CUST_ONBOARDING A, EXT_CBG_EXTENDED B WHERE A.WI_NAME=B.WI_NAME AND A.WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				String uaeNational = xp.getValueOf("UAE_NATIONAL");
				String passportFullName=xp.getValueOf("PASSPORT_FULL_NAME");
				String eidaFullName=xp.getValueOf("EIDA_FULL_NAME");
				String manualFullName=xp.getValueOf("PASSPORT_MANUAL_NAME");
				String isNameValidated = xp.getValueOf("NAMESPLIT_DECISION");

				if(!"Y".equalsIgnoreCase(isNameValidated)){
					if("Y".equalsIgnoreCase(uaeNational)){
						if(!"".equals(eidaFullName) && !"".equals(passportFullName) && !"Y".equalsIgnoreCase(isNameValidated)){
							int firstOrder = 0;
							String[] eidaName = eidaFullName.split(" ");
							String[] passportName = passportFullName.split(" ");
							List<Integer> orderList = new ArrayList<Integer>(); 
							//COLMP-8272|9321 RISHABH 180424 START
							/*for(int k=0; k<passportName.length;k++){
								if(Arrays.asList(eidaName).contains(passportName[k])){
									firstOrder = Arrays.asList(eidaName).indexOf(passportName[k]);
									orderList.add(firstOrder);
								}
								else{
									isNameValidated = "Y";
									break;
								}
							}*/
							HashMap<String,Integer> hmIndex = new HashMap<>();
							for (int k = 0; k < passportName.length; k++) {
								if (Arrays.asList(eidaName).contains(passportName[k])) {
									firstOrder = Arrays.asList(eidaName).indexOf(passportName[k]);
									if (hmIndex.get(passportName[k]) == null) {
										orderList.add(firstOrder);
										hmIndex.put(passportName[k], firstOrder);
									} else {
										for (int nextIndex = firstOrder + 1; nextIndex < eidaName.length; ++nextIndex) {
											if (eidaName[nextIndex].equals(passportName[k])) {
												hmIndex.put(passportName[k], nextIndex);
												orderList.add(nextIndex);
												break;
											}
										}
									}
								} else {
									isNameValidated = "Y";
									break;
								}
							}
							//COLMP-8272|9321 RISHABH 180424 END
							for(int i=0;i < orderList.size()-1;i++) {
								if(orderList.get(i)< orderList.get(i+1)){
									isNameValidated = "N";
								}
								else{
									isNameValidated = "Y";
									break;
								}
							}
						}
					}
					else {
						if(!"".equals(passportFullName) && !"".equals(manualFullName)){
							if(manualFullName.length() >= passportFullName.length()){
								isNameValidated = "N";
							}else {
								isNameValidated = "Y";
							}
						}
						if(!"".equals(passportFullName) && !"".equals(eidaFullName) && !"".equals(manualFullName) && !"Y".equalsIgnoreCase(isNameValidated)){
						String[] passportName = passportFullName.split(" ");
						String[] eidaName = eidaFullName.split(" ");
						String[] manualName = manualFullName.split(" ");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passportName:::::"+passportName+"::eidaName::"+eidaName+"::manualName::"+manualName);
						for(String element : passportName) {
							if(!Arrays.asList(eidaName).contains(element)){
								isNameValidated = "Y";
								break;
							}else {
								isNameValidated = "N";
							}
						}

						if(!"Y".equalsIgnoreCase(isNameValidated)){
							for(String element : manualName) {
								if(!Arrays.asList(eidaName).contains(element)){
									isNameValidated = "Y";
									break;
								}else {
									isNameValidated = "N";
								}
							}
						}
					}

						if(!"".equals(eidaFullName) && !"".equals(manualFullName) && !"Y".equalsIgnoreCase(isNameValidated)){
						int firstOrder = 0;
						String[] eidaName = eidaFullName.split(" ");
						String[] Mannual = manualFullName.split(" ");
						List<Integer> orderList = new ArrayList<Integer>(); 
						//COLMP-8272|9321 RISHABH 180424 START
						/*for(int k=0; k<Mannual.length;k++){
							if(Arrays.asList(eidaName).contains(Mannual[k])){
								firstOrder = Arrays.asList(eidaName).indexOf(Mannual[k]);
								orderList.add(firstOrder);
							}
							else{
								isNameValidated = "Y";
								break;
							}
						}*/
						HashMap<String,Integer> hmIndex = new HashMap<>();
						for (int k = 0; k < Mannual.length; k++) {
							if (Arrays.asList(eidaName).contains(Mannual[k])) {
								firstOrder = Arrays.asList(eidaName).indexOf(Mannual[k]);
								if (hmIndex.get(Mannual[k]) == null) {
									orderList.add(firstOrder);
									hmIndex.put(Mannual[k], firstOrder);
								} else {
									for (int nextIndex = firstOrder + 1; nextIndex < eidaName.length; ++nextIndex) {
										if (eidaName[nextIndex].equals(Mannual[k])) {
											hmIndex.put(Mannual[k], nextIndex);
											orderList.add(nextIndex);
											break;
										}
									}
								}
							} else {
								isNameValidated = "Y";
								break;
							}
						}
						//COLMP-8272|9321 RISHABH 180424 END
						for(int i=0;i < orderList.size()-1;i++){
							if(orderList.get(i)< orderList.get(i+1)){
								isNameValidated = "N";
							}
							else{
								isNameValidated = "Y";
								break;
							}
						}
						}
					}
					//colmp-7653|200324|aditi
					String query="";
					Connection con  = null;
					if("Y".equalsIgnoreCase(isNameValidated)){
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "ValidateName", "ValidateName", "Customer Name Not Match for Passport Name Vs Manual Name", sessionId);
						query = "UPDATE EXT_CBG_EXTENDED SET CASA_BREAK_REASON = CASA_BREAK_REASON||'| Name Not Match' WHERE  WI_NAME = ?";
							
					}else if("N".equalsIgnoreCase(isNameValidated)){
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "ValidateName", "ValidateName", "Customer Name Match for Passport Name Vs Manual Name", sessionId);
						query = "UPDATE EXT_CBG_EXTENDED SET CASA_BREAK_REASON =REPLACE(CASA_BREAK_REASON, '| Name Not Match', '') WHERE  WI_NAME = ?";
						APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "PASSPORT_FULL_NAME", "'"+manualFullName+"'", "NAMESPLIT_DECISION='N' AND WI_NAME = N'"+ WI_NAME +"'", sessionId);
					}
					
					try {
						con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
						CallableStatement cstmt = con.prepareCall(query);
						cstmt.setString(1, WI_NAME);//WINAME
						int count = cstmt.executeUpdate();
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
						NGDBConnection.closeDBConnection(con, "APUpdate");
						con = null;
					} catch (Exception e) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
					}finally {
						try {
							if (con != null) {
								NGDBConnection.closeDBConnection(con, "APUpdate");
								con = null;
							}
						} catch (Exception e) {
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
						}
					}
				}
				APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "NAMESPLIT_DECISION", "'"+isNameValidated+"'", "NAMESPLIT_DECISION='N' AND WI_NAME = N'"+ WI_NAME +"'", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "ValidateFullName", "M-ValidateFullName", e, sessionId);
		}
	}

	public String updateUaePassCodeForAO(HashMap<String, String> attributeMap,String WI_NAME,String sessionId){
		String eida = "";
		try{
			String outputXML = APCallCreateXML.APSelect("SELECT EMIRATES_ID FROM BPM_UAEPASS_INFO WHERE UAEPASS_CODE = N'"+attributeMap.get("UAEPASS_CODE")+"' ");
			XMLParser xp1 = new XMLParser(outputXML);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode1 == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddDocument TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					eida = xp1.getValueOf("EMIRATES_ID");
					APCallCreateXML.APUpdate("ACC_RELATION_REPEATER", "UAE_PASSCODE", "'"+attributeMap.get("UAEPASS_CODE")+"'", "WI_NAME = N'"+WI_NAME+"' AND EIDA = '"+eida+"'", sessionId);
				}
			}
		} catch(Exception e){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return eida;
	}

	public void updateCustInfoForAO(HashMap<String, String> attributeMap,String uaePassCode,String sessionId,String stageId,String WI_NAME ){
		try{
			if(Integer.parseInt(stageId) == 114){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "insiide 114  ");
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "uaePassCode 114  "+uaePassCode);
				String passGender = attributeMap.get("PASSPORT_GENDER");
				String passdocName = attributeMap.get("PASSPORT_DOC_NAME");
				String passExpDate = attributeMap.get("PASSPORT_EXPIRY_DATE");
				String passDobDate = attributeMap.get("PASSPORT_DOB_DATE");
				String passLastName = attributeMap.get("PASSPORT_LAST_NAME");
				String passFirstName = attributeMap.get("PASSPORT_FIRST_NAME");
				String passFullName = attributeMap.get("PASSPORT_FULL_NAME");
				String passIssueDate = attributeMap.get("PASSPORT_ISSUE_DATE");
				String passNo = attributeMap.get("PASSPORT_NUMBER");
				String passType = attributeMap.get("PASSPORT_TYPE");
				String passNationality = attributeMap.get("PASSPORT_NATIONALITY");
				String passDocRefNo = attributeMap.get("PASSPORT_DOC_INDEX");
				String columnList = "MANUAL_PASS_NO,MANUAL_NATIONALITY,MANUAL_FULL_NAME,MANUAL_GENDER,"
						+ "MANUAL_LAST_NAME,MANUAL_FIRST_NAME,MANUAL_PASS_TYPE,PASS_DOC_REF_NO";
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "insiide columnList  "+columnList);

				String valuesList ="'"+passNo +"','"+passNationality +"',"
						+ "'"+passFullName +"','"+passGender +"','"+passLastName +"','"+passFirstName +"',"
						+ "'"+passType +"','"+passDocRefNo+"'";
				//TO_DATE('"+passIssueDate +"','DD/MM/YYYY'),TO_DATE('"+passExpDate +"','DD/MM/YYYY'),TO_DATE('"+passDobDate +"','DD/MM/YYYY')
				//manual_pass_issue_date,manual_pass_exp_date,manual_dob 
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "insiide valuesList  "+valuesList);

				APCallCreateXML.APUpdate("USR_0_CUST_TXN", columnList, valuesList, " WI_NAME = N'"+ WI_NAME +"' AND UAE_PASSCODE = '"+uaePassCode+"'", sessionId);
				APCallCreateXML.APProcedure(sessionId, "APUPDATEWITHDATE", "'"+WI_NAME+"','"+passIssueDate+"','"+passExpDate+"','"+passDobDate+"','"
						+ ""+uaePassCode+"'",5);
			} else if(Integer.parseInt(stageId) == 115){
				//String corpMailID = attributeMap.get("CORPORATE_EMAIL_ID");
				String custMailId = attributeMap.get("CUSTOMER_EMAIL");//
				String custMobNo = attributeMap.get("CUSTOMER_MOBILE_NO");
				//String isEtb = attributeMap.get("USE_ETB_PROFILE");

				String columnList = "MANUAL_EMAIL,MANUAL_MOBILE_NO";
				String valuesList ="'"+custMailId +"','"+custMobNo +"'";
				APCallCreateXML.APUpdate("USR_0_CUST_TXN", columnList, valuesList, " WI_NAME = N'"+ WI_NAME +"' AND UAE_PASSCODE = '"+uaePassCode+"'", sessionId);

			}} catch(Exception e){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			}
	}

	public String getLinkedWI(String WI_NAME){
		String linkedWI = "";
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT LINKED_WI FROM BPM_COP_LEAD_DETAILS WHERE WI_NAME = N'"+ WI_NAME +"'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(totalRetrieved > 0){
					linkedWI = xp.getValueOf("LINKED_WI");
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
		return linkedWI;
	}

	/** Insert in AO Decision History **/
	public void insertInAOHistory(String WI_NAME , String sessionId , String message){
		try {
			String sourcingChannel = "";
			String wsName = "";
			String dsaCode = "";
			String outputXML = APCallCreateXML.APSelect("SELECT SOURCING_CHANNEL,CURR_WS_NAME,DSA_PROMO_CODE FROM EXT_AO WHERE WI_NAME = N'"+ WI_NAME +"'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(totalRetrieved > 0){
					sourcingChannel = xp.getValueOf("SOURCING_CHANNEL");
					wsName = xp.getValueOf("CURR_WS_NAME");
					dsaCode = xp.getValueOf("DSA_PROMO_CODE");
				}
			}


			String valList1 = "'"+ WI_NAME +"',sysdate,'"+wsName+"' , '"+dsaCode+"','Initiation','"+sourcingChannel+"','"+message+"'";
			APCallCreateXML.APInsert("usr_0_ao_dec_hist", "WI_NAME, CREATE_DAT, WS_NAME, USERNAME, WS_DECISION, CHANNEL,WS_COMMENTS", 
					valList1, sessionId);
		} catch (NGException e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
			e.printStackTrace();
		}
	}
	
	// Created On 25-11-22
	public int updateDataInDB(String query, String wi_name) {
		Connection con  = null;
		try {
			con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
			CallableStatement cstmt = con.prepareCall(query);
			cstmt.setString(1, wi_name);//WINAME 
			int count = cstmt.executeUpdate();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
			NGDBConnection.closeDBConnection(con, "APUpdate");
			con = null;
			return 0;
			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			return 1;
		} finally {
			try {
				if (con != null) {
					NGDBConnection.closeDBConnection(con, "APUpdate");
					con = null;
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			}
		}
	}

	
	public boolean isEIDADuplicate(String eidaNumber) {
		boolean isDedupe = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_DSCOP WHERE"
					+ " EIDA_NUMBER='"+eidaNumber+"'");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"eidaNumber : Y : eidaNumber"+ eidaNumber);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			else{
				isDedupe=true;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}
	
	public boolean isEIDAApplicationDuplicate(String eidaApplNumber, String mobile, String email) {
		boolean isDedupe = false;
		try {
/*			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_CBG_CUST_ONBOARDING WHERE STAGE_ID NOT IN (-3,-4,901,611,612) "
					+ "AND EIDA_APPL_NO = '"+eidaApplNumber+"' AND CUSTOMER_MOBILE_NO = '"+ mobile +"' AND CUSTOMER_EMAIL = '"+ email +"'");*/
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_CBG_CUST_ONBOARDING WHERE STAGE_ID NOT IN (-3,-4,901,611,612,622,623) "
					+ "AND EIDA_APPL_NO = '"+eidaApplNumber+"' AND CUSTOMER_MOBILE_NO = '"+ mobile +"' AND SOURCING_CHANNEL = 'ADCBCOP'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			else{
				isDedupe=true;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}
	
	public boolean isPassportApplicationDuplicate(String passportNumber, String dob, String nationality) {
		boolean isDedupe = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_DSCOP "
					+ "  WHERE PASSPORT_NO='"+passportNumber+"'AND DOB = '"+dob+"' AND "
					+" AND NATIONALITY ='"+nationality+"'");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"isPassportApplicationDuplicate : Y : isPassportApplicationDuplicate"+ passportNumber);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"isPassportApplicationDuplicate : Y : isPassportApplicationDuplicate"+ dob);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"isPassportApplicationDuplicate : Y : isPassportApplicationDuplicate"+ nationality);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			else{
				isDedupe=true;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}
	
	public boolean isPassportApplicationDuplicateNONNTC(String passportNumber, String dob, String nationality) {
		boolean isDedupe = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_CBG_CUST_ONBOARDING WHERE STAGE_ID NOT IN"
					+ " (-3,-4,901,611,612,622,623) AND PASSPORT_NUMBER = '"+passportNumber+"' AND "
					+ "PASSPORT_NATIONALITY = '"+ nationality +"' AND PASSPORT_DOB_DATE = TO_DATE('"+dob+"','DD/MM/YYYY') ");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>1?true:false;
			}
			else{
				isDedupe=false;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}
	
	public int getWorkItemID(String WI_NAME , String stageId, HashMap<String,String> defaultMap){
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "getWorkItemID Start: "+ WI_NAME);
		int workItemID = 1;
		try {
			String activityName = "SYSTEM_WS";
			List<String> moreInfoIDs = Arrays.asList(defaultMap.get("MORE_INFO_STAGE_IDS").split(","));
			if(!moreInfoIDs.isEmpty() && moreInfoIDs.contains(stageId)){
				activityName = "MORE_INFO_REQUIRED";
			}else{
				List<String> murabahaIDs = Arrays.asList(defaultMap.get("MURABAHA_STAGE_IDS").split(","));
				if(!murabahaIDs.isEmpty() && murabahaIDs.contains(stageId)){
					activityName = "MURABAHA_CONSENT";
				}
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "getWorkItemID ActivityName: "+ activityName);
			XMLParser xp=new XMLParser(APCallCreateXML.APSelect("SELECT WORKITEMID "
					+ " FROM wfinstrumenttable WHERE processinstanceid = N'" + WI_NAME + "' and activityname = '"+activityName+"'"));
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
					workItemID 	= Integer.parseInt(xp.getValueOf("WORKITEMID"));
				}
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "getWorkItemID Ends: "+ WI_NAME +" :WorkitemID: "+workItemID);
		return workItemID;
	}

	public boolean isMobileDuplicate(String mobileNumber) {
		boolean isDedupe = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_CBG_CUST_ONBOARDING WHERE STAGE_ID NOT IN (-3,-4) AND CUSTOMER_MOBILE_NO='"+mobileNumber+"'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			else{
				isDedupe=false;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}

	public boolean isEmailDuplicate(String email) {
		boolean isDedupe = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_CBG_CUST_ONBOARDING WHERE STAGE_ID NOT IN (-3,-4,901,611,612,622,623) AND UPPER(CUSTOMER_EMAIL)=UPPER('"+email+"')");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			else{
				isDedupe=false;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}

	public boolean isTalabatRefNumberDuplicate(String refNum) {
		boolean isDedupe = false;
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM EXT_CBG_CUST_ONBOARDING WHERE STAGE_ID NOT "
					+ "IN (-3,-4) AND  WI_NAME IN (SELECT WI_NAME FROM EXT_CBG_EXTENDED WHERE PARTNER_CUST_REF_NUM='"+refNum+"')");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			else{
				isDedupe=false;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}
	
	public void validateFullNameNTC(String WI_NAME, String sessionId){
		try {	
			String outputXML = APCallCreateXML.APSelect("SELECT UPPER(A.PASSPORT_FULL_NAME) AS PASSPORT_FULL_NAME, "
					+ "UPPER(B.PASSPORT_MANUAL_NAME) AS PASSPORT_MANUAL_NAME, NAMESPLIT_DECISION FROM EXT_CBG_CUST_ONBOARDING A, "
					+ "EXT_CBG_EXTENDED B WHERE A.WI_NAME=B.WI_NAME AND A.WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				String passportFullName=xp.getValueOf("PASSPORT_FULL_NAME");
				String manualFullName=xp.getValueOf("PASSPORT_MANUAL_NAME");
				String isNameValidated = xp.getValueOf("NAMESPLIT_DECISION");

				if(!"Y".equalsIgnoreCase(isNameValidated)){
					if (!"".equals(passportFullName) && !"".equals(manualFullName)) {
//						if (manualFullName.length() >= passportFullName.length()) {
						if (manualFullName.length() == passportFullName.length()) { //COLMP-55 : NTC NAME ORDER LOGIC
							isNameValidated = "N";
						} else {
							isNameValidated = "Y";
						}
					}
					if(!"".equals(passportFullName) && !"".equals(manualFullName) && !"Y".equalsIgnoreCase(isNameValidated)){
						String[] passportName = passportFullName.split(" ");
						String[] manualName = manualFullName.split(" ");
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passportName:::::"+passportName+"::manualName::"+manualName);
						//Changes done after discussion with amit / sanal by Rishabh on 19072023
						/*for(String element : passportName) {
							if(!Arrays.asList(manualName).contains(element)){
								isNameValidated = "Y";
								break;
							}else {
								isNameValidated = "N";
							}
						}*/
						int firstOrder = 0;
						List<Integer> orderList = new ArrayList<Integer>(); 
						for(int k=0; k<manualName.length;k++) {
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "passportName[k] : "+manualName[k]);
							if(Arrays.asList(passportName).contains(manualName[k])){
								firstOrder = Arrays.asList(passportName).indexOf(manualName[k]);
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "firstOrder : "+firstOrder);
								orderList.add(firstOrder);
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "orderList : "+orderList.toString());
							}
							else{
								isNameValidated = "Y";
								break;
							}
						}
						for(int i=0;i < orderList.size()-1;i++) {
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "orderList.get(i) : "+orderList.get(i) + " ::orderList.get(i+1):: " + orderList.get(i+1));
							if(orderList.get(i)< orderList.get(i+1)){
								isNameValidated = "N";
							}
							else{
								isNameValidated = "Y";
								break;
							}
						}
					}
					
					if("Y".equalsIgnoreCase(isNameValidated)){
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "ValidateName", "ValidateName", "Customer Name Not Match for Passport Name Vs Manual Name", sessionId);
						Connection con  = null;
						try {
							con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
							String query = "UPDATE EXT_CBG_EXTENDED SET CASA_BREAK_REASON = CASA_BREAK_REASON||'| Name Not Match' WHERE  WI_NAME = ?";
							CallableStatement cstmt = con.prepareCall(query);
							cstmt.setString(1, WI_NAME);//WINAME
							int count = cstmt.executeUpdate();
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
							NGDBConnection.closeDBConnection(con, "APUpdate");
							con = null;
						} catch (Exception e) {
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
						}finally {
							try {
								if (con != null) {
									NGDBConnection.closeDBConnection(con, "APUpdate");
									con = null;
								}
							} catch (Exception e) {
								DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
							}
						}
					}else if("N".equalsIgnoreCase(isNameValidated)){
						DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "ValidateName", "ValidateName", "Customer Name Match for Passport Name Vs Manual Name", sessionId);
						APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "PASSPORT_FULL_NAME", "'"+manualFullName+"'", "NAMESPLIT_DECISION='N' AND WI_NAME = N'"+ WI_NAME +"'", sessionId);
					}
				}
				APCallCreateXML.APUpdate("EXT_CBG_CUST_ONBOARDING", "NAMESPLIT_DECISION", "'"+isNameValidated+"'", "NAMESPLIT_DECISION='N' AND WI_NAME = N'"+ WI_NAME +"'", sessionId);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "ValidateFullName", "M-ValidateFullName", e, sessionId);
		}
	}
	
	/*//21/03/2023 COP-2843 Moksh
	public void validateFinalRiskClassification(String WI_NAME, String sessionId){
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT RISK_VALUE FROM USR_0_RISK_VALUES WHERE RISK_CODE = "+
														"(SELECT RISK_ELIGIBILITY_OUTPUT FROM EXT_CBG_CUST_ONBOARDING "+
														"WHERE WI_NAME = N'" + WI_NAME + "')");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				String riskValue=xp.getValueOf("RISK_VALUE");
				if("Medium Risk".equalsIgnoreCase(riskValue)){
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "validateFinalRiskClassification", "validateFinalRiskClassification", "Customer Medium Risk", sessionId);
					Connection con  = null;
					try {
						con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
						String query = "UPDATE EXT_CBG_EXTENDED SET CASA_BREAK_REASON = CASA_BREAK_REASON||'| Medium Risk' WHERE  WI_NAME = ?";
						CallableStatement cstmt = con.prepareCall(query);
						cstmt.setString(1, WI_NAME);//WINAME
						int count = cstmt.executeUpdate();
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
						NGDBConnection.closeDBConnection(con, "APUpdate");
						con = null;
					} catch (Exception e) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
					}finally {
						try {
							if (con != null) {
								NGDBConnection.closeDBConnection(con, "APUpdate");
								con = null;
							}
						} catch (Exception e) {
							DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
						}
					}
				} else {
					DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "validateFinalRiskClassification", "validateFinalRiskClassification", "Customer Not Medium Risk", sessionId);
				}
			}
		} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "validateFinalRiskClassification", "M-validateFinalRiskClassification", e, sessionId);
		}
	}
	
	//21/03/2023 COP-2840 Moksh
	public void validateNTCCustomer(String WI_NAME, String sessionId){
		try {
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WI_NAME, "validateNTCCustomer", "validateNTCCustomer", "Customer NTC", sessionId);
			Connection con  = null;
			try {
				con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
				String query = "UPDATE EXT_CBG_EXTENDED SET CASA_BREAK_REASON = CASA_BREAK_REASON||'| NTC' WHERE  WI_NAME = ?";
				CallableStatement cstmt = con.prepareCall(query);
				cstmt.setString(1, WI_NAME);//WINAME
				int count = cstmt.executeUpdate();
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
				NGDBConnection.closeDBConnection(con, "APUpdate");
				con = null;
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			}finally {
				try {
					if (con != null) {
						NGDBConnection.closeDBConnection(con, "APUpdate");
						con = null;
					}
				} catch (Exception e) {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				}
			}
		} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "validateFinalRiskClassification", "M-validateFinalRiskClassification", e, sessionId);
		}
	}*/
	
	public String breakCLOBString(String str, String WI_NAME, String sessionId) {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "str2222 : "+str);
		int itr = str.length() / 3200;
		int mod = str.length() % 3200;
		if (mod > 0) {
			++itr;
		}
		StringBuilder response = new StringBuilder();
		try {
			if(itr > 1){
				for (int i = 0; i < itr; ++i) {
					if (i == 0) {
						response.append("TO_NCLOB('" + str.substring(0, 3200) + "')");
					} else if (i < itr - 1) {
						response.append(" || TO_NCLOB('" + str.substring(3200 * i,3200 * (i + 1)) + "')");
					} else {
						response.append(" || TO_NCLOB('" + str.substring(3200 * i,str.length()) + "') ");
					}
				}
			} else {
				response.append("TO_NCLOB('" + str + "')");
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "str : "+str);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e); 
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, WI_NAME, "breakCLOBString", "M-breakCLOBString", e, sessionId);
		}
		return response.toString();
	}

	public boolean isSameDeviceVaild30Days(String deviceID, String WI_NAME) {
		boolean isDedupe = false;
		try {
			String outputXML = "";
			if(!"".equals(WI_NAME)){
				outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT, MIN(WI_NAME) AS WI_NAME FROM BPM_DEVIE_CID_MAPPING WHERE DEVICE_ID='"+deviceID+"' AND CID_CREATION_DATE>SYSDATE-30");
			}else {
				outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM BPM_DEVIE_CID_MAPPING WHERE DEVICE_ID='"+deviceID+"' AND CID_CREATION_DATE>SYSDATE-30");
			}
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				if(!"".equals(WI_NAME)){
					int count = Integer.parseInt(xp.getValueOf("COUNT"));
					if(count>0 && !WI_NAME.equals(xp.getValueOf("WI_NAME"))){
						isDedupe = true;
					}
				}
				else{
				isDedupe = Integer.parseInt(xp.getValueOf("COUNT"))>0?true:false;
			}
			}
			else{
				isDedupe=false;
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
		return isDedupe;
	}
	private static String hexServerIP = null;
	private static final java.security.SecureRandom seeder = new java.security.SecureRandom();
	/**
	 * A 32 byte GUID generator (Globally Unique ID). These artificial keys SHOULD <strong>NOT </strong> be seen by the user,
	 *  not even touched by the DBA but with very rare exceptions, just manipulated by the database and the programs.
	 * Usage: Add an id field (type java.lang.String) to your EJB, and add setId(XXXUtil.generateGUID(this)); to the ejbCreate method.
	 */

	public static final String generateGUID(Object o) {
		StringBuffer tmpBuffer = new StringBuffer(16);
		if (hexServerIP == null) {
			java.net.InetAddress localInetAddress = null;
			try {
				// get the inet address
				localInetAddress = java.net.InetAddress.getLocalHost();

			} catch (java.net.UnknownHostException uhe) {

				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AccountUtil: Could not get the local IP address using InetAddress.getLocalHost()!");
				uhe.printStackTrace();
				return null;
			}
			byte serverIP[] = localInetAddress.getAddress();
			hexServerIP = hexFormat(getInt(serverIP), 8);
		}

		String hashcode = hexFormat(System.identityHashCode(o), 8);
		tmpBuffer.append(hexServerIP);
		tmpBuffer.append(hashcode);
		long timeNow = System.currentTimeMillis();
		int timeLow = (int) timeNow & 0xFFFFFFFF;
		int node = seeder.nextInt();
		StringBuffer guid = new StringBuffer(32);
		guid.append(hexFormat(timeLow, 8));
		guid.append(tmpBuffer.toString());
		guid.append(hexFormat(node, 8));
		return guid.toString();

	}

   private static String hexFormat(int i, int j) {
       String s = Integer.toHexString(i);
       return padHex(s, j) + s;
   }
   private static int getInt(byte bytes[]) {
	   int i = 0;
	   int j = 24;
	   for (int k = 0; j >= 0; k++) {
		   int l = bytes[k] & 0xff;
		   i += l << j;
		   j -= 8;
	   }
	   return i;
   }

   private static String padHex(String s, int i) {
	   StringBuffer tmpBuffer = new StringBuffer();
	   if (s.length() < i) {
		   for (int j = 0; j < i - s.length(); j++) {
			   tmpBuffer.append('0');
		   }
	   }

	   return tmpBuffer.toString();

   }
   
   //COLMP-759
   public String prepareJsonDecTxn(String responseXml, String listOfTags, HashMap<String, String> resMap){
	   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[DSCOPUtils][prepareJsonDecTxn]: INSIDE ");
	   JSONArray ja = new JSONArray();
	   try {
		   String[] tags = listOfTags.split(",");
		   XMLParser xp = new XMLParser(responseXml);
		   int start = xp.getStartIndex("Records", 0, 0);
		   int deadEnd = xp.getEndIndex("Records", start, 0);
		   int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		   int end = 0;
		   for (int i = 0; i < noOfFields; ++i) {
			   JSONObject jo = new JSONObject();
			   start = xp.getStartIndex("Record", end, 0);
			   end = xp.getEndIndex("Record", start, 0);
			   for (String tagName : tags) {
				   if (tagName.equals("approverId")) {
					   String key = xp.getValueOf(tagName, start, end);
					   String valueFromMap;
					   if (resMap.containsKey(key)) {
						   valueFromMap = resMap.get(key) == null ? "" : resMap.get(key);
						   jo.put(tagName, valueFromMap + " [" + key + "]");
					   } else {
						   jo.put(tagName, key);
					   }
				   } else {
					   jo.put(tagName, xp.getValueOf(tagName, start, end));
				   }
			   }
			   ja.put(jo);
		   }
	   } catch (Exception e) {
		   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
	   }
	   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "[DSCOPUtils][prepareJsonDecTxn]: json "+ja.toString());
	   return ja.toString();	
   }
   //COLMP-759
   public HashMap<String, String> getUserMap(String WI_NAME) {
	   HashMap<String, String> userMap = new HashMap<String, String>();
	   try {
		   String userName="";
		   String fullName="";

		   String outputXML3 = APCallCreateXML.APSelect("select UPPER(USERNAME) AS USERNAME,UPPER(PERSONALNAME || ' ' || FAMILYNAME) AS FULLNAME from pdbuser where "
				   + "UPPER(USERNAME) in (select distinct UPPER(USER_NAME) AS USER_NAME from usr_0_cbg_dec_txn where wi_name = N'"+WI_NAME+"')");
		   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"usr_0_cbg_dec_txn query "+ outputXML3);
		   XMLParser xp3 = new XMLParser(outputXML3);
		   int mainCode2 = Integer.parseInt(xp3.getValueOf("MainCode"));
		   if (mainCode2 == 0 && (Integer.parseInt(xp3.getValueOf("TotalRetrieved")) > 0)) {
			   int start = xp3.getStartIndex("Records", 0, 0);
			   int deadEnd = xp3.getEndIndex("Records", start, 0);
			   int end = 0;
			   int noOfFields = xp3.getNoOfFields("Record", start, deadEnd);
			   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "COP_EEvent " + noOfFields);
			   for (int i = 0; i < noOfFields; ++i) {
				   start = xp3.getStartIndex("Record", end, 0);
				   end = xp3.getEndIndex("Record", start, 0);
				   userName = xp3.getValueOf("USERNAME", start, end);
				   fullName = xp3.getValueOf("FULLNAME", start, end);
				   userMap.put(userName, fullName);
			   }
		   }
	   }
	   catch(Exception ex) {
		   DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, ex);
	   }
	   return userMap;
   } 
   
   public static String convertToPlainString(String sInputXML){
		StringBuffer outputxml = new StringBuffer();
		sInputXML=sInputXML.replace("SessionId","SessionIdTemp");
	    if (sInputXML.length() > 4000) {
	      int itr = sInputXML.length() / 4000;
	      int mod = sInputXML.length() % 4000;
	      if (mod > 0) {
	        ++itr;
	      }
	      for (int i = 0; i < itr; ++i) {
	        if (i == 0) {
	          outputxml.append("TO_NCLOB('" + sInputXML.substring(0, 4000) + "')");
	        }
	        else if (i < itr - 1) {
	          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, 4000 * (i + 1)) + "')");
	        }
	        else
	        {
	          outputxml.append(" || TO_NCLOB('" + sInputXML.substring(4000 * i, sInputXML.length()) + "') ");
	        }
	      }

	    }
	    else
	    {
	      outputxml.append("'"+sInputXML+"'");
	    }
		return outputxml.toString();
	}
   
   public int updateDataInDB(String query) {
		Connection con  = null;
		try {
			con  = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APUpdate");
			CallableStatement cstmt = con.prepareCall(query);
			int count = cstmt.executeUpdate();
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Row update: "+count);
			NGDBConnection.closeDBConnection(con, "APUpdate");
			con = null;
			return 0;
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			return 1;
		} finally {
			try {
				if (con != null) {
					NGDBConnection.closeDBConnection(con, "APUpdate");
					con = null;
				}
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			}
		}
	}
}
