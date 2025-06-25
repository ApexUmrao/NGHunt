package com.newgen.ao.laps.calls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.mail.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.cache.TSLMComplexMapping;
import com.newgen.ao.laps.cache.TSLMStagingCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.implementation.BPMMandatoryCheck;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.ao.laps.util.SingleUserConnection;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

//DART 1128664
public class TSLMLinkData implements ICallExecutor, Callable<String> {
	//DART 1130789
	private String sessionId;
	private String WI_NAME;
	private String channelRefNo;
	private String processDefId;
	private String dataInputXML="";
	private String NASDataPath="";
	private String NASDataMovePath="";
	private String mode;
	private String channelName; 
	private String userId; 
	private String sysrefno; 
	private String requestDateTime;  
	private String correlationId; 
	private String version; 
	private String processName="TFO";
	private String cabinetName;
	private String username;
	private String password;
	private String tableName = "";
	private CallEntity callEntity;
	private HashMap<String, String> attributeMap=new HashMap<String, String>();
	private static Map<String, String> TSLM_SCF_StagingMap;
	private static Map<String, String> TSLM_SCF_ProcessTableMap;
	private static Map<String, String> TSLM_SCF_DateMap;
	private static Map<String, String> TSLM_SCF_CLOBMasterMap;
	private HashMap<String, String> defaultMap;
	private Map<String, String> TSLMDateMap;
	private static HashMap<String,ArrayList<String>> TSLMReferralMaster ;
	private String requestCategory;
	private String requestType;
	private HashMap<String, String> attributeCLOBMap;
	private String processType;
	private String requestedBy;
	private int wrkitmId = 0;
	
	private String sourceChannel;
	private static String sourcingChannel;
	private static String process;
	private Map<String,Map<String,String>> TSLMDefaultMap;
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap = new HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>>();
	private LinkedHashMap<Integer, HashMap<String, String>> innerComplexAttribMap;
	private HashMap<String, String> innerInnerComplexMap;
	private String decHist = "TFO_DJ_DEC_HIST";
	private Boolean prevStageNoGo;

//Commneting Unnecessary Code -- ATP-284 by Shivanshu
	private String auditCallName = "LinkTslmData";
	//DART 1130788
	public TSLMLinkData(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
	//DART 1129673	
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE TSLM Link Data Constructor");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sleep started at : " +java.time.LocalDateTime.now());
		
		//Added to handle 10 sec delay as TSLM place file at shared path after 10 sec
		//Changed to 60 sec as due to encryption files are not coming in payload immediately
		String TSLMDelayTimer=LapsConfigurations.getInstance().TSLMDelayTimer;
		try {  //ATP 212-213 by Shivanshu
			Thread.sleep(Integer.parseInt(TSLMDelayTimer));
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in Sleep ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
		}
		//DART 1132892 AYUSH
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sleep end at : " +java.time.LocalDateTime.now());
	     	this.WI_NAME = WI_NAME;
		    this.attributeMap.putAll(attributeMap);
		    this.callEntity = callEntity;
		    this.sessionId=sessionId;
		    this.prevStageNoGo = prevStageNoGo;
		try{
			processDefId =attributeMap.get("processDefId");
			channelRefNo =attributeMap.get("channelRefNumber");
			correlationId =attributeMap.get("correlationId");
			sourcingChannel =attributeMap.get("sourcingChannel");
			process =attributeMap.get("processName");
			TSLMReferralMaster = TSLMStagingCache.getInstance().getTSLMReferralMaster();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLM Link data channelRefNo "+channelRefNo);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLM Link data correlationId "+correlationId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLM Link data sourcingChannel "+sourcingChannel);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TSLM Link data processName "+processName);
			cabinetName =LapsConfigurations.getInstance().CabinetName;
			username =LapsConfigurations.getInstance().UserName;
			password =LapsConfigurations.getInstance().Password;
			NASDataPath = LapsConfigurations.getInstance().NASDataPath+channelRefNo+".xml";
			NASDataMovePath = String.valueOf(LapsConfigurations.getInstance().NASDataMovePath) + channelRefNo + ".xml";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loadConfiguration NASDataPath :"+NASDataPath);
			
			//Function to move data from Payload to Stagging
			linkDataToApplication();
			//DART 1129681 AYUSH
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception in TSLM LinkData Constructor"+e.getMessage());
		}
		
	}
	//DART 1129673
	private void readDataFromXML(){
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside read data from XML");
		BufferedReader reader=null;
		
		try{
			reader = new BufferedReader(new FileReader(NASDataPath));
			String line=null;
			StringBuilder sb=new StringBuilder();
			
			while((line=reader.readLine())!=null){
				sb.append(line);
			}
			dataInputXML=sb.toString(); //ATP -343 DATE 20-12-2023 REYAZ
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Data from XML @@"+dataInputXML);
			
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception in Read data from XML"+e.getMessage());
			String statusCode ="1";
			  String errorCode  ="420";
			  String errorDescription  ="Data Mapping Failed";
			  PushMessageTSLMSCF pushMsg = new PushMessageTSLMSCF(sessionId,this.WI_NAME,statusCode,errorCode,errorDescription);
			  try {
				pushMsg.call();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception in calling Push Message TSLM"+e1.getMessage());
			}
			
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error closing buffered reader ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
				}
			}
		}
	}
	
	private void linkDataToApplication(){
		
		//Read data from Payload and store in String
		readDataFromXML();
		
		XMLParser xp= new XMLParser(dataInputXML);
		this.mode = xp.getValueOf("mode");
		this.channelName = xp.getValueOf("channelName");
		this.sysrefno = xp.getValueOf("sysrefno");
		this.requestDateTime = xp.getValueOf("requestDateTime");
		this.version = xp.getValueOf("version");
		StringBuffer strColumns = new StringBuffer();
		StringBuffer strValues = new StringBuffer();
		String counterPartyFlag=getMDMFlag();
		//ATP -343 DATE 20-12-2023 REYAZ
		try {

			//DATE 09-01-2024 
			//START CODE
			String payLoadData =dataInputXML;
			String[] chunks = payLoadData.split("(?<=\\G.{3900})");
			payLoadData = "";
			for(String chunk: chunks){
				if(!payLoadData.equals("")){
					payLoadData = payLoadData + " || ";
				}
				payLoadData = payLoadData + "to_clob('"+ chunk +"')";
			}
			APCallCreateXML.APInsert("BPM_PAYLOAD_DATA", "WI_NAME,PAYLOAD_DATA,PAYLOAD_DATETIME", "'"+this.WI_NAME+"',"
					+ ""+payLoadData+",sysdate", sessionId);
			//END CODE


		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception in inserting data in bpm_payload_data table "+e.getMessage());
		}
		strColumns.append("REQUESTMODE,channelName,channelRefNumber,userId,sysrefno,requestDateTime,correlationId,version,processName,WI_NAME,insertiondatetime,COUNTER_PARTY_BRMS_FLAG,");
		strValues.append("'"+ mode+"','"+ channelName+"','"+ channelRefNo+"','"+ userId+"','"+ sysrefno+"','"+ requestDateTime+"','"+ correlationId+"','"+ version+"','"+ processName+"','"+ this.WI_NAME+"',SYSDATE,'"+counterPartyFlag+"',");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "channelName: " + this.channelName);		
		if("TSLM".equals(channelName)) {
			try {
				String outputXml = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT "
						+ "FROM WFINSTRUMENTTABLE WHERE PROCESSINSTANCEID = '"+WI_NAME
						+ "'");
				XMLParser xpCount = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xpCount.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"
				+Integer.parseInt(xpCount.getValueOf("ROWCOUNT")));	

				if (mainCode == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT")) != 0) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside if processing");
					
					//Function to move data from Payload to Stagging
                    startProcessing(dataInputXML, strColumns.toString(), strValues.toString(), this.sessionId, this.cabinetName, this.username, 
							this.password);
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside else processing");
                    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem not created for this channel ref no");	
					
				}
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in fetching row count: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
	}
	//DART 1130788
	//DART 1130789
	public void startProcessing(String message, String columns, String values, String sessionId, String cabinetName, String username,
			String password){
		int start;
		int deadEnd;
		int noOfFields;
		int end;
		int deadEndInner;
		int noOfFieldsInner;
		int startInner;
		int endInner=0;
		StringBuffer strColumns = new StringBuffer();
		StringBuffer strValues = new StringBuffer();
		StringBuffer strRepeaterColumns;
		StringBuffer strRepeaterValues;

		try {
		    LapsModifyLogger.logMe(1, "inside TSLM_SCF_ startProcessing");
			//DART 1129681 AYUSH
		    TSLM_SCF_StagingMap = TSLMStagingCache.getInstance().getTSLMStagingMap(processDefId,sourcingChannel);
		    TSLM_SCF_DateMap = TSLMStagingCache.getInstance().getTSLMDateMap(processDefId,sourcingChannel);
		    TSLM_SCF_CLOBMasterMap = TSLMStagingCache.getInstance().getTSLMCLOBMasterMap(processDefId,sourcingChannel);
		    TSLM_SCF_ProcessTableMap = TSLMStagingCache.getInstance().getTSLMProcessTableMap(processDefId,sourcingChannel);
//		    TSLMDefaultMap=ChannelCallCache.getInstance().getTSLMDefaultMaster(processDefId,sourcingChannel);

		    if(message.contains("<attributeValue/>")){
		    	message = message.replaceAll("<attributeValue/>", "<attributeValue></attributeValue>");
		    }
		    if(message.contains("&lt;")){
		    	message = message.replaceAll("&lt;", "<");
		    }
		    if(message.contains("&gt;")){
		    	message = message.replaceAll("&gt;", ">");
		    }
		    if(message.contains("&amp'")){
		    	message = message.replaceAll("&amp'", "&");
		    }
		    if(message.contains("'")){
		    	message = message.replaceAll("'", "''");
		    }
		    
		    LapsModifyLogger.logMe(1, "message after replacing blank tags: " + message);
			XMLParser xp = new XMLParser(message);
			
			strColumns.append(columns);
			strValues.append(values);
			start = xp.getStartIndex("attributeDetails", 0, 0);
			deadEnd = xp.getEndIndex("attributeDetails", start, 0);
			noOfFields = xp.getNoOfFields("attributes", start, deadEnd);
			end = 0;
			for (int i = 0; i < noOfFields; ++i) {//DART 1128664
				start = xp.getStartIndex("attributes", end, 0);
				end = xp.getEndIndex("attributes", start, 0);
				String attributeKey = xp.getValueOf("attributeKey", start, end);
				String attributeValue = xp.getValueOf("attributeValue", start, end);
				if(attributeValue.trim().equalsIgnoreCase("Y"))
					attributeValue="Yes";
				else if(attributeValue.trim().equalsIgnoreCase("N"))
					attributeValue="No";
				if(attributeValue.trim().equalsIgnoreCase("CTP"))
					attributeValue="IFA_CTP";
				
				if(attributeKey.trim().equalsIgnoreCase("TENOR_BASE")){
					
					if(attributeValue.trim().equalsIgnoreCase("From Latest Invoice Date")){
						attributeValue="FLID";
					}else if(attributeValue.trim().equalsIgnoreCase("From Oldest Invoice Date")){
						attributeValue="FOID";
					}else if(attributeValue.trim().equalsIgnoreCase("From Latest Transport Document Date")){
						attributeValue="FLTDD";
					}else if(attributeValue.trim().equalsIgnoreCase("From Oldest Transport Document Date")){
						attributeValue="FOTDD";
					}else if(attributeValue.trim().equalsIgnoreCase("Proposed Loan Effective Date")){
						attributeValue="PLED";
					}else if(attributeValue.trim().equalsIgnoreCase("Oldest Invoice Acknowledgment Date")){
						attributeValue="OIAD";
					}else if(attributeValue.trim().equalsIgnoreCase("Latest Invoice Acknowledgment Date")){
						attributeValue="LIAD";
					}
					
				}
				
				if(i != (noOfFields-1)) {
					strColumns.append(attributeKey+",");
					if(TSLM_SCF_CLOBMasterMap.containsKey(attributeKey)
							&& TSLM_SCF_CLOBMasterMap.get(attributeKey).equals("Y")){
						strValues.append(createNormalizedXML(attributeValue)+",");
					} else if(attributeKey.contains("_DATE") && TSLM_SCF_DateMap.containsKey(attributeKey)
							&& TSLM_SCF_DateMap.get(attributeKey).equals("Y")){
						strValues.append("TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss'),");
					} else {
						strValues.append("'"+attributeValue+"',");
					} 
				}
				else {
					strColumns.append(attributeKey);
					if(TSLM_SCF_CLOBMasterMap.containsKey(attributeKey)
							&& TSLM_SCF_CLOBMasterMap.get(attributeKey).equals("Y")){
						strValues.append(createNormalizedXML(attributeValue));
					}else if(attributeKey.contains("_DATE") && TSLM_SCF_DateMap.containsKey(attributeKey)
							&& TSLM_SCF_DateMap.get(attributeKey).equals("Y")){
						strValues.append("TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss')");
					} else {
						strValues.append("'"+attributeValue+"'");
					}
				}
			}
			String opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_SCF_TXN_DATA", strColumns.toString(), 
					strValues.toString(), sessionId);
			XMLParser xmlParser = new XMLParser(opXml);
			if(Integer.parseInt(xmlParser.getValueOf("MainCode")) == 11) {
				sessionId = SingleUserConnection.getInstance(100).getSession(cabinetName, username, 
						password);
				opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_SCF_TXN_DATA", strColumns.toString(), 
						strValues.toString(), sessionId);
				xmlParser = new XMLParser(opXml);
			} 
			
			if(Integer.parseInt(xmlParser.getValueOf("MainCode")) != 0) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Error in creating Workitem");
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In reapeter else condition");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(NASDataPath);
				NodeList nl =doc.getElementsByTagName("Repeater");
				NodeList n2=doc.getElementsByTagName("Repeaters");
				start = xp.getStartIndex("Repeaters", 0, 0);
				deadEnd = xp.getEndIndex("Repeaters", start, 0);
				noOfFields = nl.getLength();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"noOfFields ::"+noOfFields);
				end = 0;
				String messagecopy=message;
				for(int c=0;c<noOfFields;c++){
				messagecopy=messagecopy.replace(messagecopy.substring(messagecopy.indexOf("<Repeater "), messagecopy.indexOf(">", messagecopy.indexOf("<Repeater "))+1), "<Repeater>");
				}
				XMLParser xp4 = new XMLParser(messagecopy);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"messagecopy ::"+messagecopy);
				for (int i = 0; i < noOfFields; ++i) {
					Node currentItem = nl.item(i);
					strRepeaterColumns = new StringBuffer();
					strRepeaterValues = new StringBuffer();
					String Repeater = xp4.getNextValueOf("Repeater");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Repeater :"+Repeater);
					String type = currentItem.getAttributes().getNamedItem("type").getNodeValue();
					String rownum = currentItem.getAttributes().getNamedItem("rowno").getNodeValue();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"type :::"+ type);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"rownum :::"+ rownum);
					XMLParser xp2 = new XMLParser(Repeater);
					noOfFieldsInner = xp2.getNoOfFields("attributes");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"noOfFieldsInner :::"+ noOfFieldsInner);
					if(!type.equalsIgnoreCase("LOAN_DETAILS")) { //ATP-124 DATE 30-11-2023 BY REYAZ
					strRepeaterColumns.append("channelRefNo,correlationId,INSERTIONDATETIME,ROWNO,WI_NAME,REQUESTMODE,");
					strRepeaterValues.append("'"+channelRefNo+"','"+correlationId+"',CURRENT_TIMESTAMP,'"+rownum+"','"+WI_NAME+"','"+mode+"',");
					}//ATP-124 DATE 30-11-2023 BY REYAZ
					for (int  j = 0 ;j<noOfFieldsInner ;j++) {
						String attributeKey ="";
						String attributeValue ="";
//						if(j==0){
//							attributeKey =xp2.getValueOf("attributeKey");
//							attributeValue = xp2.getValueOf("attributeValue");
//						} else {
							 attributeKey = xp2.getNextValueOf("attributeKey");
							 attributeValue = xp2.getNextValueOf("attributeValue");
							 if(attributeValue.trim().equalsIgnoreCase("Y"))
									attributeValue="Yes";
								else if(attributeValue.trim().equalsIgnoreCase("N"))
									attributeValue="No";
//						}
						/*String attributeKey = xp2.getNextValueOf("attributeKey");
						String attributeValue = xp2.getNextValueOf("attributeValue");*/
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeKey :"+ attributeKey);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeValue :"+ attributeValue);
						if(j != (noOfFieldsInner-1)) {
							strRepeaterColumns.append(attributeKey+",");
							if(attributeKey.contains("_DATE") && TSLM_SCF_DateMap.containsKey(attributeKey)
									&& TSLM_SCF_DateMap.get(attributeKey).equals("Y")&&!(attributeValue.trim().isEmpty())){
								if(type.equalsIgnoreCase("PO_INVOICE_DETAILS")){//DART 1130790
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									Date formatedDate= new Date();
									formatedDate=sdf.parse(attributeValue);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "formatedDate: "+formatedDate);
									SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
									String formatedDate2=sdf2.format(formatedDate);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "formatedDate2  kkkkk: "+formatedDate2);
									
									strRepeaterValues.append("'"+formatedDate2+"',");
								}
								else{
									strRepeaterValues.append("TO_CHAR(TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss')),");
								}
//								strRepeaterValues.append("TO_DATE('"+attributeValue+"','DD-MM-YYYY'),");
							} else {
								strRepeaterValues.append("'"+attributeValue+"',");
							}					
						} else {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Last append attributeKey:"+ attributeKey);
							strRepeaterColumns.append(attributeKey);
							if(attributeKey.contains("_DATE") && TSLM_SCF_DateMap.containsKey(attributeKey)
									&& TSLM_SCF_DateMap.get(attributeKey).equals("Y")&&!attributeValue.trim().isEmpty()){
								if(type.equalsIgnoreCase("PO_INVOICE_DETAILS")){//DART 1130790
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									Date formatedDate= new Date();
									formatedDate=sdf.parse(attributeValue);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "formatedDate: "+formatedDate);
									SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
									String formatedDate2=sdf2.format(formatedDate);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "formatedDate2 kkkk:"+formatedDate2);
									
									strRepeaterValues.append("'"+formatedDate2+"'");
								}
								else{
									strRepeaterValues.append("TO_CHAR(TO_DATE('"+attributeValue+"','DD/MM/YYYY hh24:mi:ss'))");
								//strRepeaterValues.append("TO_DATE('"+attributeValue+"','DD-MM-YYYY')");
								}
								
							} else {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Last append attributeValue:"+ attributeValue);
								strRepeaterValues.append("'"+attributeValue+"'");
							}
						}
					}
					String tableName = TSLM_SCF_StagingMap.get(type);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "repeater tableName: " + tableName);	 				
					if(!tableName.isEmpty()){
						if(type.equalsIgnoreCase("LOAN_DETAILS")) { //ATP-124 DATE 30-11-2023 BY REYAZ
							APCallCreateXML.APUpdate(tableName,strRepeaterColumns.toString(),strRepeaterValues.toString(),
									"WI_NAME='"+WI_NAME+"'", sessionId);
						} else {
						String opXmlRepeater = APCallCreateXML.APInsert(tableName, 
								strRepeaterColumns.toString(), strRepeaterValues.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "opXmlRepeater: " + opXmlRepeater);
						xmlParser = new XMLParser(opXmlRepeater);
						if(Integer.parseInt(xmlParser.getValueOf("MainCode")) != 0) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"calling triggerExceptionMail");
						}
					}
				}
			}
			} //ATP-124 DATE 30-11-2023 BY REYAZ
			moveDataFromStagingToApplication();
		} catch (Exception e) {
			String statusCode ="1";
			  String errorCode  ="420";
			  String errorDescription  ="Data Mapping Failed";
			  PushMessageTSLMSCF pushMsg = new PushMessageTSLMSCF(sessionId,this.WI_NAME,statusCode,errorCode,errorDescription);
			  try {
				pushMsg.call();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception in calling Push Message TSLM"+e1.getMessage());
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "startProcessing: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	 				
		}
	}
	@Override
	public String call() throws Exception {
		
		
		return null;
	}

	@Override
	public String createInputXML() throws Exception {
		
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		
		return null;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
		      LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, this.WI_NAME, this.auditCallName, "EIDA003", "EIDA DependencyCall:" + this.callEntity.getDependencyCallID(), this.sessionId);
		      CallHandler.getInstance().executeDependencyCall(this.callEntity, this.attributeMap, this.sessionId, this.WI_NAME, this.prevStageNoGo);
		    } catch (Exception e) {
		      LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		      LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, this.WI_NAME, this.auditCallName, "EIDA100", e, this.sessionId);
		    } 
		  }
		
	

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		
		
	}
	
	public static  String createNormalizedXML(String outputXml) {
		try {
			if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
				outputXml = outputXml.replace("'", "''");
				outputXml = outputXml.replace("&", "'||chr(38)||'");
				if (outputXml.length() > 3200) {				
					outputXml = breakCLOBString(outputXml);
					return outputXml;
				}
				outputXml = "TO_NCLOB('" + outputXml + "')";
				return outputXml;
			}			
		} catch (Exception ex) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, ex);	 				
		}
		return "''";
	}
	
	private static  String breakCLOBString(String xml) {
		int itr = xml.length() / 3200;
		int mod = xml.length() % 3200;
		if (mod > 0) {
			++itr;
		}
		StringBuilder response = new StringBuilder();
		try {
			for (int i = 0; i < itr; ++i) {
				if (i == 0) {
					response.append("TO_NCLOB('" + xml.substring(0, 3200) + "')");
				} else if (i < itr - 1) {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,3200 * (i + 1)) + "')");
				} else {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,xml.length()) + "') ");
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	 				
		}
		return response.toString();
	}
//DART 1129702
public  void moveDataFromStagingToApplication(){
	StringBuilder strDecHistColumns = null;
	StringBuilder[] strDecHistValues = null;
	int start;
	int deadEnd;
	int noOfFields;
	int noOfFieldsInner;
	int mainCode = -1;
	int totalRetrievedCount =0;
	String outputXml = null;
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside moveDataFromStagingToApplication:");
	try {
		outputXml = APCallCreateXML.APSelect("SELECT * FROM (SELECT  REQUEST_CATEGORY, REQUEST_TYPE, CUSTOMER_ID, TSLM_REF_NUMBER, HYBRID_CUSTOMER, PROFIT_CENTER_CODE, TRANSACTION_CURRENCY, TRANSACTION_AMOUNT, PRODUCT_CODE  "
					+ "FROM TFO_DJ_TSLM_SCF_TXN_DATA WHERE channelRefNumber = '"+channelRefNo
					+ "' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE ='"+mode+"' ORDER BY REQUESTDATETIME DESC) WHERE ROWNUM =1");

		XMLParser xp = new XMLParser(outputXml);
		mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		defaultMap = StageCallCache.getReference().getCachedDefaultMap();

		if (mainCode == 0) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside moveDataFromStagingToApplication mainCode:"+mainCode);
			requestCategory = xp.getValueOf("REQUEST_CATEGORY");
			requestType = xp.getValueOf("REQUEST_TYPE");
			String custID = xp.getValueOf("CUSTOMER_ID");
			String TslmRefNo = xp.getValueOf("TSLM_REF_NUMBER");
			String hybridCustomer = xp.getValueOf("HYBRID_CUSTOMER");
			String profitCenter =xp.getValueOf("PROFIT_CENTER_CODE");
			String transactionCurrency=xp.getValueOf("TRANSACTION_CURRENCY");
			String transactionAmount=xp.getValueOf("TRANSACTION_AMOUNT");
			String productCode=xp.getValueOf("PRODUCT_CODE");
	
			String[] retMsg = BPMMandatoryCheck.isMandatoryCheck_TSLM(LapsConfigurations.getInstance().CabinetName, 
					channelRefNo, correlationId,mode).split("###");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"retMsg: "+Arrays.toString(retMsg));
			if(retMsg[0].equalsIgnoreCase("true")){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside retMsg::");
				Map<String,Map<String,HashMap<String, String>>> TSLMMappingMap = TSLMStagingCache.getInstance().getTSLMMappingMap(processDefId,sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"requestCategory ::"+requestCategory+" requestType ##" +requestType);
				Map<String,HashMap<String, String>> attribMap = TSLMMappingMap.get(requestCategory+"#"+requestType);
				TSLMComplexMapping cmplxMaster;
				TSLMDefaultMap = TSLMStagingCache.getInstance().getTSLMDefaultMaster(processDefId,sourcingChannel);
				TSLMDateMap = TSLMStagingCache.getInstance().getTSLMDateMap(processDefId,sourcingChannel);
				HashMap<String, TSLMComplexMapping> TSLMComplexMap = TSLMStagingCache.getInstance().getTSLMComplexMap(processDefId,sourcingChannel);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"defaultMap: "+defaultMap);
				attributeMap = new HashMap<String, String>();
				attributeCLOBMap = new HashMap<String, String>(); //ATP-124 DATE 30-11-2023 BY REYAZ

				for(Map.Entry<String, HashMap<String, String>> mapEntry: attribMap.entrySet()){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TSLM mapEntry: "+mapEntry.toString());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mapEntry.getKey() "+mapEntry.getKey());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TSLMComplexMap.get(mapEntry.getKey()) "+TSLMComplexMap.get(mapEntry.getKey()));
					cmplxMaster = TSLMComplexMap.get(mapEntry.getKey());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"cmplxMaster.getStagingTableName() "+cmplxMaster.getStagingTableName());
					tableName = cmplxMaster.getStagingTableName();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"tableName: "+tableName);
					if(mapEntry.getKey().equals("EXTERNAL")){
						try{
						HashMap<String, String> attribList = mapEntry.getValue();
						StringBuilder attributeValues = new StringBuilder();
						for(Map.Entry<String, String> value : attribList.entrySet()){
							if(value.getKey().contains("_DATE") && TSLMDateMap.containsKey(value.getKey())
									&& TSLMDateMap.get(value.getKey()).equals("Y")){							
								attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY') AS "+value.getKey()).append(",");
							} else {
								attributeValues.append(value.getKey()).append(",");
							}
						}
						attributeValues.setLength(attributeValues.length()-1);							
							outputXml = APCallCreateXML.APSelect("SELECT * FROM (SELECT "+attributeValues
									+" FROM " + tableName + " WHERE channelRefNumber = '"
									+channelRefNo+"' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE ='"+mode+"' ORDER BY REQUESTDATETIME DESC) WHERE ROWNUM =1");
				
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "+outputXml);
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if (mainCode == 0) {
							//attributeMap = new HashMap<String, String>(); //ATP-124 DATE 30-11-2023 BY REYAZ
							//attributeCLOBMap = new HashMap<String, String>();
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TSLM CLOBMap: "+TSLM_SCF_CLOBMasterMap);
							for(Map.Entry<String, String> value : attribList.entrySet()){
								if(TSLM_SCF_CLOBMasterMap.containsKey(value.getValue()) && TSLM_SCF_CLOBMasterMap.get(value.getValue()).equals("Y")){
									if(value.getKey().contains(" AS ")){
										attributeCLOBMap.put(value.getValue(), xp.getValueOf(
												value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
														value.getKey().length())));
									} else {
										attributeCLOBMap.put(value.getValue(), xp.getValueOf(value.getKey()));
									}
								} else {
									if(value.getKey().contains(" AS ")){
										attributeMap.put(value.getValue(), xp.getValueOf(
												value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
														value.getKey().length())));
									} else {
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TSLM value.getValue(): "+value.getValue());
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TSLM xp.getValueOf(value.getKey()): "+xp.getValueOf(value.getKey()));
										attributeMap.put(value.getValue(), xp.getValueOf(value.getKey()));
									}
								}
							}
							
							attributeMap.put("REQ_DATE_TIME", getDate());
							//attributeMap.put("PROCESS_TYPE", sourcingChannel);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Attribute Map After Req Date Time & Process Type");
							processType=sourcingChannel;
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Attribute Map Process Type: " + processType);
							String key=processType+"#"+requestCategory+"#"+requestType;
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Attribute Map key before Default Map: " + key);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Attribute Map Default Map: " + TSLMDefaultMap);
						
							for(Map.Entry<String, String> map : TSLMDefaultMap.get(key).entrySet()){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,map.getKey());
									attributeMap.put(map.getKey(),map.getValue());
							 }
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap: "
									+attributeMap.toString());
							sourceChannel=attributeMap.get("SOURCE_CHANNEL");
							requestedBy=attributeMap.get("REQUESTED_BY");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap: "
									+attributeMap.toString());
						 }
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into External Table: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							String statusCode ="1";
							  String errorCode  ="420";
							  String errorDescription  ="Data Mapping Failed";
							  PushMessageTSLMSCF pushMsg = new PushMessageTSLMSCF(sessionId,this.WI_NAME,statusCode,errorCode,errorDescription);
							  try {
								pushMsg.call();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Exception in calling Push Message TSLM"+e1.getMessage());
							}
							
						}
					} else if(mapEntry.getKey().equals("TSLM_REFERRAL_DETAILS")){
						//Directly inserting Referral data in Tables as Referral data will be inserted in 3 different tables based on Referral Type
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"map for: "+mapEntry.getKey());
						HashMap<String, String> attribList = mapEntry.getValue();
						StringBuilder attributeValues = new StringBuilder();
						String tabName ="";
						for(Map.Entry<String, String> value : attribList.entrySet()){
							attributeValues.append(value.getKey()).append(",");
						}
						attributeValues.setLength(attributeValues.length()-1);	
						try {
							outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
									" FROM " + tableName + " WHERE channelRefNo = '"
									+channelRefNo+"' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE='"+mode+"'");
						} catch (NGException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "
								+outputXml);
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if (mainCode == 0) {
							start = xp.getStartIndex("Records", 0, 0);
							deadEnd = xp.getEndIndex("Records", start, 0);
							noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in Tslm referral: "
									+noOfFields);
							for (int i = 0; i < noOfFields; ++i) {
								String Record = xp.getNextValueOf("Record");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tslm referral: Record :"+Record);
								XMLParser xp2 = new XMLParser(Record); 
								String FLAG_DEL="N";
								StringBuffer strColumns1 = new StringBuffer();
								StringBuffer strValues1 = new StringBuffer();
								StringBuffer strColumnsUpdate = new StringBuffer();
								StringBuffer strValuesUpdate = new StringBuffer();
								String seqNo = xp2.getValueOf("SEQNO");
								String transType = xp2.getValueOf("TRANSTYPE");
								String transId = xp2.getValueOf("TRANSID");
								//String refCode = xp2.getValueOf("REFCODE");//HARD CODED TILL NOT HANDLED IN TSLM
								String refCode="RM";
								String referralType = xp2.getValueOf("REFERRALTYPE");
								String refDesc = xp2.getValueOf("REFDESC");
								String decRefDesc=xp2.getValueOf("REFDESC");
								String userCmnt = xp2.getValueOf("USERCMNT");
								String status = xp2.getValueOf("STATUS");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," status : " + status);
								
								if(status.trim().equalsIgnoreCase("C")||status.trim().equalsIgnoreCase("Closed")){
									String outputXml1 = APCallCreateXML.APSelect("select randomnumber from pdbconnection where userindex=(select userIndex from pdbuser where username =(select lockedbyname from wfinstrumenttable where processinstanceid='"+WI_NAME+"' AND lockedbyname !='System' AND ROWNUM=1))");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem Flag data output XML:"+outputXml);
									XMLParser parser = new XMLParser(outputXml1);
									
									String lockedUserSessionId=parser.getValueOf("randomnumber");
									if(lockedUserSessionId==null||lockedUserSessionId.isEmpty())
										lockedUserSessionId=sessionId;
									
									closeChildWorkItem(lockedUserSessionId);
								}//DART 1129702
								String cpcid = xp2.getValueOf("COUNTERPARTYCID");
								
								//Hanndling Status Code value
								if(status.trim().equalsIgnoreCase("A")){
								status="Active";
								}else if(status.trim().equalsIgnoreCase("C")){
									status="Closed";
								}
								
								ArrayList<String> values = new ArrayList<>();
								values = TSLMReferralMaster.get(referralType+"#"+sourcingChannel); //ATP-379 15-FEB-24 --JAMSHED
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," referralType : " + referralType);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Value : " + values);
								tabName = values.get(0);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Tab Name Value : " + tabName);
								
								//Check existing referral of same type and mark as Closed and duplicate if already exist
								// ATP-483 REYAZ 20-06-2024  START
								/*if(mode.equalsIgnoreCase("M")&&status.equalsIgnoreCase("Active")){
									status=checkExistingReferralAndAutoClose(referralType,tabName);
									if(status.equalsIgnoreCase("Closed")){
										decRefDesc=decRefDesc+" : DUPLICATE";
										userCmnt=userCmnt+" DUPLICATE";
									}
								} */
								// ATP-483 REYAZ 20-06-2024  END
								if(status.trim().equalsIgnoreCase("Closed")){
									FLAG_DEL="Y";
								}
								String insertionorderID=LapsUtils.returnInsertionOrderID(tabName);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," insertionorderID: " +insertionorderID);
								strColumns1.append("SEQNO,TRANSTYPE,TRANSID,REFCODE,REFERRALTYPE,REFDESC,USERCMNT,STATUS,COUNTERPARTYCID,WI_NAME,INSERTIONORDERID,FLAG_DEL");
					            strValues1.append("'"+seqNo+"','"+transType+"','"+transId+"','"+refCode+"','"+referralType+"','"+refDesc+"','"+userCmnt+"','"+status+"','"+cpcid+"','"+this.WI_NAME+"',"+insertionorderID+",'"+FLAG_DEL+"'");
					            
					            strColumnsUpdate.append("STATUS,FLAG_DEL");
					            strValuesUpdate.append("'"+status+"','"+FLAG_DEL+"'");
					            
					            String whereClause ="WI_NAME = '"+WI_NAME+ "' AND SEQNO = '"+seqNo+ "'";
					            

					           LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Tab Name Value : " + tabName);
								String outputXml1 = APCallCreateXML.APSelect("SELECT count(1) as ROWCOUNT "
										+ "FROM "+tabName+" WHERE WI_NAME = '"+WI_NAME+ "' AND SEQNO = '"+seqNo+ "'");
								
								XMLParser xpCount = new XMLParser(outputXml1);
								int mainCode1 = Integer.parseInt(xpCount.getValueOf("MainCode"));
								   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1:"+mainCode+"ROWCOUNT :"+Integer.parseInt(xpCount.getValueOf("ROWCOUNT")));	

								if (mainCode1 == 0 && Integer.parseInt(xpCount.getValueOf("ROWCOUNT")) == 0) {
									   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside if processing");
									String opXml = APCallCreateXML.APInsert(tabName,strColumns1.toString(), strValues1.toString(), sessionId);
									   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert opXml: " + opXml);	
								} else {
									String opXml1 = APCallCreateXML.APUpdate(tabName, strColumnsUpdate.toString(), strValuesUpdate.toString(),
											whereClause, sessionId);
									   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);	
								}
								
								//insert into dec hist
								StringBuilder strDecHistValues2 = new StringBuilder();
							//	strDecHistValues2.append("'").append(WI_NAME).append("','Referred To :").append(refCode).append("',")
							//	.append("SYSTIMESTAMP,'TSLM SYSTEM USER','Status : ").append(status).append("','").append("SEQ NO: ").append(seqNo).append(" : REFERRAL TYPE : ")
								strDecHistValues2.append("'").append(WI_NAME).append("','").append(refCode).append("',")
								.append("SYSTIMESTAMP,'FINTRADE','FINTRADE','Referred To :").append(refCode).append("','").append("SEQ NO: ").append(seqNo).append(" : REFERRAL TYPE : ")
								.append(referralType).append(" : REF DESC :").append(decRefDesc).append("','',''");
								
								insertIntoDecisionHistory(strDecHistValues2);
						     }
					    }
					} else if(mapEntry.getKey().equals("LOAN_DETAILS")) { //ATP-124 DATE 30-11-2023 BY REYAZ
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"map for@@@ "+mapEntry.getKey());
						try{
							HashMap<String, String> attribList = mapEntry.getValue();
							StringBuilder attributeValues = new StringBuilder();
							for(Map.Entry<String, String> value : attribList.entrySet()){
								if(value.getKey().contains("_DATE") && TSLMDateMap.containsKey(value.getKey())
										&& TSLMDateMap.get(value.getKey()).equals("Y")){							
									attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY') AS "+value.getKey()).append(",");
								} else {
									attributeValues.append(value.getKey()).append(",");
								}
							}
							attributeValues.setLength(attributeValues.length()-1);							
							outputXml = APCallCreateXML.APSelect("SELECT * FROM (SELECT "+attributeValues
									+" FROM " + tableName + " WHERE channelRefNumber = '"
									+channelRefNo+"' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE ='"+mode+"' ORDER BY REQUESTDATETIME DESC) WHERE ROWNUM =1");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attribute values: "+outputXml);
							xp = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
							if (mainCode == 0) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TSLM CLOBMap#### "+TSLM_SCF_CLOBMasterMap);
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(TSLM_SCF_CLOBMasterMap.containsKey(value.getValue()) && TSLM_SCF_CLOBMasterMap.get(value.getValue()).equals("Y")){
										if(value.getKey().contains(" AS ")){
											attributeCLOBMap.put(value.getValue(), xp.getValueOf(
													value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
															value.getKey().length())));
										} else {
											attributeCLOBMap.put(value.getValue(), xp.getValueOf(value.getKey()));
										}
									} else {
										if(value.getKey().contains(" AS ")){
											attributeMap.put(value.getValue(), xp.getValueOf(
													value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
															value.getKey().length())));
										} else {
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TSLM value.getValue():## "+value.getValue());
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TSLM xp.getValueOf(value.getKey()):## "+xp.getValueOf(value.getKey()));
											attributeMap.put(value.getValue(), xp.getValueOf(value.getKey()));
										}
									}
								}
								
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap@@@@ "
										+attributeMap.toString());
							} 
						}catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into LOAN_DETAILS in external  Table: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
					} else if(mapEntry.getKey().equals("DEC_HIST")){
					    try{
						HashMap<String, String> attribList = mapEntry.getValue();
						StringBuilder attributeValues = new StringBuilder();
						strDecHistColumns = new StringBuilder();
						for(Map.Entry<String, String> value : attribList.entrySet()){
							if(!value.getKey().equalsIgnoreCase("WI_NAME")){
								if(value.getKey().contains("_DATE") && TSLMDateMap.containsKey(value.getKey())
										&& TSLMDateMap.get(value.getKey()).equals("Y")){
									attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY HH24:MI:SS') AS "+value.getKey()).append(",");
								} else {
									attributeValues.append(value.getKey()).append(",");
								}
								strDecHistColumns.append(value.getValue()).append(",");
							}
						}
						attributeValues.setLength(attributeValues.length()-1);	
						strDecHistColumns.setLength(strDecHistColumns.length());	
						try {
							outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
									" FROM " + tableName + " WHERE channelRefNo = '"
									+channelRefNo+"' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE='"+mode+"'");
						} catch (NGException e) {
							
							
						}
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if (mainCode == 0) {
							start = xp.getStartIndex("Records", 0, 0);
							deadEnd = xp.getEndIndex("Records", start, 0);
							noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in dec hist: "
									+noOfFields);
							strDecHistValues = new StringBuilder[noOfFields];
							for (int i = 0; i < noOfFields; ++i) {
								strDecHistValues[i] = new StringBuilder();
								String Record = xp.getNextValueOf("Record");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"dec Record :"+Record);
								XMLParser xp2 = new XMLParser(Record); 
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(!value.getKey().equalsIgnoreCase("WI_NAME")){
										if(value.getKey().contains("_DATE")){
											strDecHistValues[i].append("TO_DATE('"+xp2.getValueOf(value.getKey())+"',"
													+ "'DD/MM/YYYY HH24:MI:SS'),");
										} else {
											strDecHistValues[i].append("'").append(xp2.getValueOf(value.getKey()))
											.append("',");
										}
									}
								}
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"strDecHistValues at "
										+i+": "+strDecHistValues[i].toString());								
							}
						}
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into dec history Table: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
					} else {
						try {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"map for: "+mapEntry.getKey());
						HashMap<String, String> attribList = mapEntry.getValue();
						StringBuilder attributeValues = new StringBuilder();
						for(Map.Entry<String, String> value : attribList.entrySet()){
							attributeValues.append(value.getKey()).append(",");
						}
						attributeValues.setLength(attributeValues.length()-1);	
						outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+	" FROM " + tableName + " WHERE channelRefNo = '" +channelRefNo+"' AND CORRELATIONID = '"+correlationId+"' AND REQUESTMODE='"+mode+"' AND ROWNUM<=200");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "+ outputXml);
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if (mainCode == 0) {
							innerComplexAttribMap = new LinkedHashMap<Integer, HashMap<String, String>>();
							start = xp.getStartIndex("Records", 0, 0);
							deadEnd = xp.getEndIndex("Records", start, 0);
							noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in party: "+ noOfFields);
							for (int i = 0; i < noOfFields; ++i) {
								innerInnerComplexMap = new HashMap<String, String>();
								String Record = xp.getNextValueOf("Record");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"cmplx Record :"+Record);
								XMLParser xp2 = new XMLParser(Record); 
								for(Map.Entry<String, String> value : attribList.entrySet()){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value: "
											+value.getKey());
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getValueOf(value): "
											+xp2.getValueOf(value.getKey()));
									if(value.getKey().contains(" AS ")){
										innerInnerComplexMap.put(value.getValue(), xp2.getValueOf(
												value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
														value.getKey().length())));
									} else {
										innerInnerComplexMap.put(value.getValue(), xp2.getValueOf(value.getKey()));
									}
								}
								//Inserting order ID as 0 because on giving insertion order ID the same is not getting mapped with WorkItem 
								//CQRN - 254573
								innerInnerComplexMap.put("INSERTIONORDERID", "0");
								innerComplexAttribMap.put(i, innerInnerComplexMap);
							}
							complexMap.put(mapEntry.getKey(), innerComplexAttribMap);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Complex Map: " +complexMap.toString());
						}
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into Complex Table: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
				}
				}

				try {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexMap final: " + complexMap.toString());
					
					//Adding workitem Number to map.
					attributeMap.put("WI_NAME", this.WI_NAME);
					
					//Adding Profit Centre BRMS Flag if Profit centre is received from Payload
					if(requestCategory.equalsIgnoreCase("IFCPC")&&profitCenter!=null&&!profitCenter.trim().isEmpty()){
						attributeMap.put("PROFIT_CENTER_BRMS_FLAG", "Yes");
					}else if(requestCategory.equalsIgnoreCase("IFCPC")&&(profitCenter==null||profitCenter.trim().isEmpty())){
						attributeMap.put("PROFIT_CENTER_BRMS_FLAG", "No");
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attributeCLOBMap final: " + attributeMap.toString());
					
					//Mode M Handling
					if(mode.equalsIgnoreCase("M")){
						
						attributeMap.put("TSLMSCF_UTILITY_FLAG", "Y");  	// ATP-461 REYAZ 09-05-2024
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
								"Inside the Unlock WI Call --> Mode: " + mode);
						
						//Get session of user who has locked the application
						String outputXml1 = APCallCreateXML.APSelect("select randomnumber from pdbconnection where userindex=(select userIndex from pdbuser where username =(select lockedbyname from wfinstrumenttable where processinstanceid='"+WI_NAME+"' AND lockedbyname !='System' AND ROWNUM=1))");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem Flag data output XML:"+outputXml);
						XMLParser parser = new XMLParser(outputXml1);
						
						String lockedUserSessionId=parser.getValueOf("randomnumber");
						
						//Handling if application is not locked by any user
						if(lockedUserSessionId==null||lockedUserSessionId.isEmpty())
							lockedUserSessionId=sessionId;
						
						//Update Status of Customer Referral as Closed and Update Flags accordingly
						// onSubmitReferral("CUSTOMER_REVIEW");
						
						// ATP-461 REYAZ 09-05-2024
						String activityName = "";
						String isCRFlag = "";
						String isRMFlag = "";
						int mainCodeAssgn = -1;
						try {
							String outputXML = APCallCreateXML.APSelect("select CURR_WS, IS_CR_PPM, IS_RM_PPM "
									+ "FROM EXT_TFO " + "WHERE WI_NAME='" + this.WI_NAME + "'");
							XMLParser xpAct = new XMLParser(outputXML);
							int mainCodeAct = Integer.parseInt(xpAct.getValueOf("MainCode"));
							if (mainCodeAct == 0) {
								activityName = xpAct.getValueOf("CURR_WS");
								isCRFlag = xpAct.getValueOf("IS_CR_PPM");
								isRMFlag = xpAct.getValueOf("IS_RM_PPM");
							}
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "activityName: " + activityName);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCRFlag: " + isCRFlag);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isRMFlag: " + isRMFlag);
							if (isCRFlag.equalsIgnoreCase("Y")) {
								outputXML = APCallCreateXML.APSelect(
										"select WORKITEMID from WFINSTRUMENTTABLE " + "where PROCESSINSTANCEID = '"
												+ this.WI_NAME + "' and INTRODUCEDAT = 'Distribute1'"
												+ " and ACTIVITYNAME = 'CUSTOMER_REVIEW'");
								XMLParser xp3 = new XMLParser(outputXML);
								if (Integer.parseInt(xp3.getValueOf("MainCode")) == 0) {
									wrkitmId = Integer.parseInt(xp3.getValueOf("WORKITEMID"));
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
											"wrkitmId from WFINSTRUMENTTABLE: " + wrkitmId);
								}
							}
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,
									"exception in M_TSLMSY WMCompleteWorkitem:");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId: " + wrkitmId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "before WFSetAttributes ");
						String outputXmlAssgn = "";
						try {
							if ("Y".equalsIgnoreCase(isCRFlag) && wrkitmId != 0) {
								outputXmlAssgn = ProdCreateXML.WFSetAttributes_TSLM(sessionId, this.WI_NAME, wrkitmId,
										attributeMap, complexMap, Integer.parseInt(processDefId), sourcingChannel);
								XMLParser xp1 = new XMLParser(outputXmlAssgn);
								mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
										"Assign Attributes Main Code: " + mainCodeAssgn);
							}
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in modify: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}

						if (mainCodeAssgn == 0) {
							insertDecisionHistory(strDecHistColumns, strDecHistValues);
							if ((wrkitmId == 2 || wrkitmId == 3) && "Y".equalsIgnoreCase(isCRFlag)) {
								if ((hybridCustomer.equalsIgnoreCase("N") || hybridCustomer.equalsIgnoreCase("No"))
										|| requestCategory.equalsIgnoreCase("IFCPC")) {
									APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA",
											"INSERTIONDATETIME, WI_NAME, PROCESS_NAME, "
													+ "SOURCING_CHANNEL, REQUESTMODE, EXPIRY_DATE, STATUS_FLAG",
											"SYSDATE,'" + this.WI_NAME + "','TFO','TSLMSY','DM',"
													+ "SYSDATE + " + defaultMap.get("TSLM_DELAY_TIME") + ",'N'",
											sessionId);
									APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'DM_ToDoList'",
											"WI_NAME = '" + this.WI_NAME + "'", sessionId);
									ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, wrkitmId);
								}
							}
							// Move Payload File and Document folder to History Path
							File currentPath = new File(NASDataPath);
							File destPath = new File(NASDataMovePath);
							LapsModifyLogger.logMe(1, "currentPath : " + currentPath);
							LapsModifyLogger.logMe(1, "destPath : " + destPath);
							Boolean result = moveFile(currentPath, destPath);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Move Files result" + result);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
									"Files Moved" + this.mode + " Channel Ref " + channelRefNo);
							this.attributeMap.put("mode", this.mode);
							this.attributeMap.put("channelRefNo", channelRefNo);
						} else {
							// Push Message to TSLM for failed case
							String statusCode = "1";
							String errorCode = "420";
							String errorDescription = "Data Mapping Failed";
							PushMessageTSLMSCF pushMsg = new PushMessageTSLMSCF(sessionId, this.WI_NAME, statusCode,
									errorCode, errorDescription);
							pushMsg.call();
						}
						//Close All Child workitems
						// closeChildWorkItem(lockedUserSessionId);
						
						//Unlock Workitem if any Referral is not raised
						// ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, 1);
					} else {
					
				//Set Attribute call when Application is at Intiation
						String outputXmlAssgn = ProdCreateXML.WFSetAttributes_TSLM(sessionId, this.WI_NAME, 1,
								attributeMap, null, Integer.parseInt(processDefId), sourcingChannel);
				XMLParser xp1 = new XMLParser(outputXmlAssgn);
				int mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
								"Assign Attributes Main Code: " + mainCodeAssgn);
				
// CHANGES NEED TO BE DONE FROM HERE FOR TODO LIST--------------------------------------------------------------------------------
				//Route application
				routeApplication(hybridCustomer);
				
				//Set Attribute call when Application is at PP Maker or in TO-DO LIST
						outputXmlAssgn = ProdCreateXML.WFSetAttributes_TSLM(sessionId, this.WI_NAME, 1, attributeMap,
								complexMap, Integer.parseInt(processDefId), sourcingChannel);
				xp1 = new XMLParser(outputXmlAssgn);
				
				//Insert Decision History Values
				insertDecisionHistory(strDecHistColumns,strDecHistValues);
				
				mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
								"Assign Attributes Main Code: " + mainCodeAssgn);
				
				if(mainCodeAssgn==0){
					
				//Move Payload File and Document folder to History Path   
				File currentPath=new File(NASDataPath);
				File destPath=new File(NASDataMovePath);
				LapsModifyLogger.logMe(1, "currentPath : " + currentPath);
				LapsModifyLogger.logMe(1, "destPath : " + 	destPath);
				Boolean result=moveFile(currentPath, destPath);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Move Files result" +result);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Files Moved" + this.mode + " Channel Ref " + channelRefNo);
				this.attributeMap.put("mode", this.mode);
				this.attributeMap.put("channelRefNo", channelRefNo);
				
//				try{ //ATP-284 by Shivanshu
//
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside dependency Call");
//				executeDependencyCall();
//
//				}catch (Exception e) {
//						
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception inexecuteDependencyCall "+e.getMessage());
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//					}
				} else {
				//Push Message to TSLM for failed case
				String statusCode ="1";
				String errorCode  ="420";
				String errorDescription  ="Data Mapping Failed";
							PushMessageTSLMSCF pushMsg = new PushMessageTSLMSCF(sessionId, this.WI_NAME, statusCode,
									errorCode, errorDescription);
				pushMsg.call();
				}
				
				attributeMap.clear();
				
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Profit center:"+profitCenter);
//				//ATP-284 Shivanshu
//					
//				//Auto Submit case to RM
//				if(requestCategory.equalsIgnoreCase("IFCPC")&&profitCenter!=null){
//					autoSubmitIFCPCApp(custID);
//				}else{
//					autoSubmitPPM(custID);
//				}
//				
//				
//				//Validations and auto data population after Payload data is mapped
//				postWorkItemCreationValidation(custID,productCode,transactionCurrency,transactionAmount);
				
				//Unlock workitem after Application is processed
				ProdCreateXML.WMUnlockWorkItem(sessionId, this.WI_NAME, 1);
				
				//Move data From Staging to History
				//moveDataFromStagingToHistory(); //ATP-284 by Shivanshu
					}
				
				} catch (Exception e) {
					
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into dec hist: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
			
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Else retMsg: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Mandatory Data Is Missing " + Arrays.toString(retMsg));

			}
		 }
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in moveDataFromStagingToApplication : ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	// }
   }
   
	private void closeChildWorkItem(String lockedUserSessionId){
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside closeChildWorkItem");
		String isRM="";
		String isLegal="";
		String isCustomer="";
		String isTSD="";
		String isCorrespondentBank="";
		String isTrade="";
		String activityID="";
		String activityType="";
		String workItemID="";
		
		
		try{
		String outputXml = APCallCreateXML.APSelect("SELECT IS_CB_PPM, IS_TRADE_PPM,IS_CR_PPM ,IS_RM_PPM ,IS_REF_PPM AS IS_TSD_PPM ,IS_LEGAL_PPM  FROM EXT_TFO WHERE WI_NAME ='"+WI_NAME	+ "'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem Flag data output XML:"+outputXml);
		XMLParser parser = new XMLParser(outputXml);
		
		isRM=parser.getValueOf("IS_RM_PPM");
		isCorrespondentBank=parser.getValueOf("IS_CB_PPM");
		isTrade=parser.getValueOf("IS_TRADE_PPM");
		isCustomer=parser.getValueOf("IS_CR_PPM");
		isTSD=parser.getValueOf("IS_TSD_PPM");
		isLegal=parser.getValueOf("IS_LEGAL_PPM");
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Flag values:");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isRM :"+isRM);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCorrespondentBank :"+isCorrespondentBank);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isTrade :"+isTrade);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCustomer :"+isCustomer);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isTSD :"+isTSD);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isLegal :"+isLegal);
		
		if(isRM.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='RM'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'RM'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType);
		} if(isCorrespondentBank.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='CORRESPONDENT_BANK'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'CORRESPONDENT_BANK'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType);
		} if(isTrade.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='TB Sales'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'TB Sales'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType);
		} if(isTSD.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='TSD'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'TSD'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType);
		} if(isLegal.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='LEGAL_TEAM'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'LEGAL_TEAM'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType);
		}if(isCustomer.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='CUSTOMER_REVIEW'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'CUSTOMER_REVIEW'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, this.WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType);
		}
		
		
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in closeChildWorkItem : ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	private void discardChildWorkitem(String workItemID,String activityID,String activityType){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside closeChildWorkItem");
		try{
			StringBuffer sInputXML = new StringBuffer(); 
			sInputXML.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<WMAssignWorkItemAttributes_Input>").append("\n")
			.append("<Option>WMAssignWorkItemAttributes</Option>").append("\n")
			.append("<SessionId>"+ sessionId +"</SessionId>").append("\n")
			.append("<EngineName>"+ cabinetName + "</EngineName>").append("\n")
			.append("<ProcessDefId>"+ processDefId +"</ProcessDefId>").append("\n")
			.append("<ProcessInstanceId>"+ WI_NAME +"</ProcessInstanceId>").append("\n")
			.append("<WorkitemId>"+workItemID+"</WorkitemId>").append("\n")
			.append("<ActivityId>"+activityID+"</ActivityId>").append("\n")
			.append("<ActivityType>"+activityType+"</ActivityType>").append("\n")
			.append("<UserDefVarFlag>Y</UserDefVarFlag>").append("\n")
			.append("<ReminderFlag>N</ReminderFlag>").append("\n")
			.append("<complete>D</complete>").append("\n")
			.append("<Attributes><DECISION>Reject</DECISION><DEC_CODE>REJ</DEC_CODE><REMARKS>Auto Closed by TSLM</REMARKS></Attributes>").append("\n")
			.append("</WMAssignWorkItemAttributes_Input>");
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes inputXML ===>" + sInputXML);
			ProdCreateXML.WMGetWorkItem(sessionId, WI_NAME, Integer.valueOf(workItemID));
			String outputXML =  ExecuteXML.executeXML(sInputXML.toString());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WFSetAttributes OutputXML ===>" + outputXML);		
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in closeChildWorkItem : ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	private Boolean moveFile(File original,File dest){
		boolean filesMoved=false;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside Move File function");
		try{
			FileUtils.copyFileToDirectory(original, new File(dest.getParent()), true);
			File newFile=new File(dest.getParent()+File.separator+original.getName());
			if(newFile.exists()&&FileUtils.contentEquals(original, newFile)){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside Delete condition");
				original.delete();
				filesMoved=true;
			}
		}catch(Exception e)
		{
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return filesMoved;
	}
	private String getDate(){
		String today = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		today = formatter.format(date);
		return today;	
	 }
    private String getMDMFlag(){
    	String MDMFlag = "";
    	try{
    		String outputXml = APCallCreateXML.APSelect("SELECT AUTO_COUNTER_PARTY_TRIGGER FROM TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='TSLMSY'"); //ATP-379 15-FEB-24 --JAMSHED
    		XMLParser parser = new XMLParser(outputXml);
    		MDMFlag = parser.getValueOf("AUTO_COUNTER_PARTY_TRIGGER");
    		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "MDM Flag For Counter Party : "+MDMFlag);
      	}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return MDMFlag;
    	
    }
    
//    private String getAutoReferralFlag(){ //ATP-284 by Shivanshu
//    	String MDMFlag = "";
//    	try{
//    		String outputXml = APCallCreateXML.APSelect("SELECT AUTO_REFERRAL_FLAG FROM TFO_DJ_AUTO_TRIGGER_MASTER");
//    		XMLParser parser = new XMLParser(outputXml);
//    		MDMFlag = parser.getValueOf("AUTO_REFERRAL_FLAG");
//    		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "MDM Flag For AUTO_REFERRAL_FLAG : "+MDMFlag);
//      	}catch(Exception e){
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//		return MDMFlag;
//    	
//    }
    
//	public void insertCounterPartyDetails(String productCode) { //ATP-284
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertCounterPartyDetails call: inside");
//		String SCUSTID    = "";
//		String pCode      = "";
//		String stdLoanFlg = "N";
//		String loanCcy    = "";
//		String compyType  = "";
//		
//		if(productCode.trim().equalsIgnoreCase("L092")||productCode.trim().equalsIgnoreCase("L128")){
//			compyType="B";
//		}else if(productCode.trim().equalsIgnoreCase("L129")||productCode.trim().equalsIgnoreCase("L130")||productCode.trim().equalsIgnoreCase("L132")||productCode.trim().equalsIgnoreCase("L157")){
//			compyType="S";
//		}
//		
//		String selectedCpID="";
//		String sOperationName = "fetchTSLMCompanyDtl_Oper";
//		try {			
//			String sOutputlist = APCallCreateXML.APSelect("SELECT PRODUCT_CODE,CUSTOMER_ID,INF_LOAN_CURR FROM TFO_DJ_TSLM_SCF_TXN_DATA WHERE WI_NAME='"+WI_NAME+"' AND ROWNUM=1");
//			XMLParser xp =new XMLParser(sOutputlist);
//			SCUSTID=xp.getValueOf("CUSTOMER_ID");
//			pCode=xp.getValueOf("PRODUCT_CODE");
//			loanCcy=xp.getValueOf("INF_LOAN_CURR");
//			
//			sOutputlist = APCallCreateXML.APSelect("select COUNTERPARTY_CODE from TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA where wi_name='"+WI_NAME+"' AND ROWNUM=1");
//			xp =new XMLParser(sOutputlist);
//			selectedCpID=xp.getValueOf("COUNTERPARTY_CODE");
//			
//			String sOutputXML = FetchTSLMCompanyDetailsOutputXML(SCUSTID,pCode,stdLoanFlg,loanCcy,
//					compyType,sOperationName,selectedCpID);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML ::"+ sOutputXML);
//			
//		} catch (Exception e) {
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//	   
//	}
		
		
//	public String FetchTSLMCompanyDetailsOutputXML(String SCUSTID,String pCode,String stdLoanFlg,
//			String loanCcy,String compyType,String sOperationName,String selectedCpID){ //ATP-284
//		String responseXML="";
//		try{
//		String inputXml = getTSLMCompanyDetailsInputXML(SCUSTID,pCode,stdLoanFlg,loanCcy,compyType,sOperationName);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertCounterPartyDetails inputXml ===> " + inputXml);
//		 responseXML= LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertCounterPartyDetails outputXml ===> " + responseXML);
//		
//		XMLParser xmlparser = new XMLParser(responseXML);
//		String returnTypeCheck = xmlparser.getValueOf("returnCode");
//		String whereClause ="WINAME = '"+WI_NAME+ "'";
//		if (returnTypeCheck.equalsIgnoreCase("0")) {
//			APCallCreateXML.APDelete("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", 
//					whereClause, sessionId);
//			int counterPartyCount = xmlparser.getNoOfFields("counterPartyList");
//			XMLParser innerXmlParser = new XMLParser();
//			String counterPartyList = "";
//			
//			StringBuffer strColumns1 = new StringBuffer();
//			StringBuffer strValues1 = new StringBuffer();
//			strColumns1.append("CID,PARTY_NAME,COUNTRY,NOA,WINAME,CHECKBOX");
//			for (int i = 0; i < counterPartyCount; i++) {
//				strValues1 = new StringBuffer();
//				counterPartyList = xmlparser.getNextValueOf("counterPartyList");
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "counterPartyListData " + counterPartyList);
//				innerXmlParser.setInputXML(counterPartyList);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "innerXmlParser " + innerXmlParser.toString());
//				String Counter_Party_CID=innerXmlParser.getValueOf("cid");
//				String Counter_Party_Name=innerXmlParser.getValueOf("countryPartyName");
//				String Counter_Party_Country=innerXmlParser.getValueOf("country");
//				String Notice_of_Acknowledgement=innerXmlParser.getValueOf("noa");
//				
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Counter_Party_CID " + Counter_Party_CID);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," selectedCounterParty : " + selectedCpID);
//				if(selectedCpID.trim().equalsIgnoreCase(Counter_Party_CID)){
//					strValues1.append("'"+Counter_Party_CID+"', '"+Counter_Party_Name+"' ,'"+Counter_Party_Country+"', '"+Notice_of_Acknowledgement+"', '"+WI_NAME+"', 'true'");	
//				
//				
//				String opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", strColumns1.toString(), 
//						strValues1.toString(), sessionId);
//				XMLParser xmlParser = new XMLParser(opXml);
//				if(Integer.parseInt(xmlParser.getValueOf("MainCode")) == 11) {
//					sessionId = SingleUserConnection.getInstance(100).getSession(cabinetName, username, 
//							password);
//					opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", strColumns1.toString(), 
//							strValues1.toString(), sessionId);
//					xmlParser = new XMLParser(opXml);
//				}/*else{
//				strValues1.append("'"+Counter_Party_CID+"', '"+Counter_Party_Name+"' ,'"+Counter_Party_Country+"', '"+Notice_of_Acknowledgement+"', '"+WI_NAME+"', 'false'");
//				}*///Commednted for defect 101019
//			  }
//				
//			}
//			
//			
//		}
//		
//		//Changes for Scf new category calling new past due services Date -13-11-2023
//		//Code start
//		if("SCF".equalsIgnoreCase(requestCategory) && "PD".equalsIgnoreCase(requestType)){
//			inputXml = getModLendingAutomationInputXML(SCUSTID);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertCounterPartyDetails inputXml &&===> " + inputXml);
//			responseXML= LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertCounterPartyDetails outputXml &&===> " + responseXML);
//
//			XMLParser xp2 = new XMLParser(responseXML);
//			String returnCode = xp2.getValueOf("returnCode");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "returnCode::: "+ returnCode);
//			if (returnCode.equalsIgnoreCase("0")) { 
//				String pastDueFlag = xp2.getValueOf("pastDueFlag");
//				APCallCreateXML.APUpdate("EXT_TFO", "PAST_DUE_LIABILITY", "'"+pastDueFlag+"'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//			}
//		}
//                //Code End
//		}catch(Exception e){
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//		return responseXML;
//	}
//	
// //ATP-284	public String getTSLMCompanyDetailsInputXML(String SCUSTID,String pCode, String stdLoanFlg, String loanCcy,
//			String compyType,String sOperationName) {
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE getTSLMCompanyDetailsInputXML");
//		String txnNumber="";
//		try{
//		String sOutputlist = APCallCreateXML.APSelect("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
//		XMLParser xp =new XMLParser(sOutputlist);
//		txnNumber= xp.getValueOf("IDValue");
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
//		if(!compyType.equalsIgnoreCase("")){
//			if(compyType.equalsIgnoreCase("B")){
//				compyType = "BUYER";
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
//			}else if(compyType.equalsIgnoreCase("S")){
//				compyType = "SELLER";
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
//			}else{
//				compyType = "BOTH";
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
//			}
//		}
//		}catch(Exception e){
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside getTSLMCorpLoanDetailsInputXML >>>");
//		return "<?xml version=\"1.0\"?><APWebService_Input>"
//		+ "<Option>WebService</Option>"
//		+"<Calltype>WS-2.0</Calltype>"
//		+"<InnerCallType>TFO_TSLMCorpLoanDetails</InnerCallType>"
//		+"<EngineName>" + cabinetName + "</EngineName>"
//		+"<SessionId>" + sessionId + "</SessionId>"
//		+"<serviceName>ModTSLMCorpLoanDetails</serviceName>"
//		+"<serviceAction>Modify</serviceAction>"
//		+"<senderId>" + "WMS" + "</senderId>"
//		+"<correlationId>"+txnNumber+"</correlationId>"
//		+"<channelRefNumber>"+txnNumber+"</channelRefNumber>"
//		+"<sysRefNumber>"+txnNumber+"</sysRefNumber>"
//		+"<CUSTOMERID>"+SCUSTID+"</CUSTOMERID>"
//		+"<PRODUCTCODE>"+pCode+"</PRODUCTCODE>"
//		+"<LOANCCY>"+loanCcy+"</LOANCCY>"
//		+"<STDLOANFLAG>"+stdLoanFlg+"</STDLOANFLAG>"
//		+"<COMPANYTYPE>"+compyType+"</COMPANYTYPE>"
//		+"<operationType>"+ sOperationName + "</operationType>"
//		+"<winame>"+WI_NAME+"</winame>"
//		+"</APWebService_Input>";
//	}
			
//	public void setInvoiceDetail(){ //ATP-284
//		
//		try{
//		String sOutputlist = APCallCreateXML.APSelect("select SUPPLIER_NAME,BUYER_NAME,WI_NAME from TFO_DJ_UTC_INVOICE_DETAIL WHERE WI_NAME ='"+WI_NAME+"' AND ROWNUM=1");
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputlist invoice details : ="+sOutputlist);
//		
//		sOutputlist = APCallCreateXML.APSelect("select CUSTOMER_NAME from TFO_DJ_TSLM_SCF_TXN_DATA WHERE WI_NAME ='"+WI_NAME+"' AND ROWNUM=1");
//		XMLParser xp =new XMLParser(sOutputlist);
//		String name=xp.getValueOf("CUSTOMER_NAME");
//		String query ="select CID,PARTY_NAME from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS where winame ='"+WI_NAME+"' and CHECKBOX = '"+true+"' AND ROWNUM=1";
//		sOutputlist = APCallCreateXML.APSelect(query);
//		xp =new XMLParser(sOutputlist);
//		String cpId=xp.getValueOf("CID");
//		String cpName=xp.getValueOf("PARTY_NAME");
//		String supplier_name="";
//		String buyer_name="";
//		String tabName="TFO_DJ_UTC_INVOICE_DETAIL";
//		StringBuffer strColumns1 = new StringBuffer();
//		StringBuffer strValues1 = new StringBuffer();
//		strColumns1.append("SUPPLIER_NAME,BUYER_NAME");
//		
//		if("IFS".equalsIgnoreCase(requestCategory)){
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "validateInvoiceDetailsTab : requestCategory="+requestCategory);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "validateInvoiceDetailsTab : name="+name);
//			supplier_name=name;
//			buyer_name=cpId+"-"+cpName;
//
//		}
//		if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)){ //Added for SCF REQ CAT
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "validateInvoiceDetailsTab : requestCategory="+requestCategory);
//			buyer_name=name;
//			supplier_name=cpId+"-"+cpName;
//		}
//		
//		String whereClause ="WI_NAME = '"+WI_NAME+ "'";
//		String opXml1 = APCallCreateXML.APUpdate(tabName, "SUPPLIER_NAME", "'"+supplier_name+"'",
//				whereClause, sessionId);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
//		
//		opXml1 = APCallCreateXML.APUpdate(tabName, "BUYER_NAME", "'"+buyer_name+"'",
//				whereClause, sessionId);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
//		
//		opXml1 = APCallCreateXML.APUpdate(tabName, "Batch_No", "'"+WI_NAME+"'",
//				whereClause, sessionId);
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
//		
//		
//		}catch(Exception e){
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//
//		
//		
//	}
			
////ATP-284	public String getCovertedAmount(String transactionCurrency,String transactionAmount){
//
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "executeUTCCalls Inside");
//		double converted_amt = 0;
//		try{
//
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "reqCat value : "+requestCategory);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "requestType value : "+requestType);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "transactionAmount value : "+transactionAmount);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "transCurr value : "+transactionAmount);
//
//			String brnCode = "";
//			String errorDescription  = "";
//			double converted_amount=0;//change by Ayush - convert  to bigdecimal type
//			double book_rate = 0;
//			boolean executeCall = true;
//			String utcEligible = "";
//			String outputXml ="";
//			XMLParser xp = null;
//			String whereClause ="WI_NAME = '"+WI_NAME+ "'";
//			if ("IFS".equalsIgnoreCase(requestCategory)|| "IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory))  {  //Added for SCF REQ CAT AND PD TYPE
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside reqCat");
//				if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType)) {
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside requestType");
//						brnCode = "299";
//					if(!transactionCurrency.equalsIgnoreCase("AED")){
//						String sQuery = "SELECT BOOK_RATE FROM STG_FXRATE WHERE FROM_CCY = '"+transactionCurrency.toUpperCase()+"' AND BRANCH_CODE='"+brnCode+"'";
//						String resultSet = APCallCreateXML.APSelect(sQuery);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sQuery value : "+sQuery);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "resultSet	"+resultSet);
//						xp = new XMLParser(resultSet);
//						
//						
//
//						if (resultSet != null && resultSet.contains("BOOK_RATE")) {
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "book_rate value : "+book_rate);
//							book_rate  = Double.parseDouble(xp.getValueOf("BOOK_RATE"));
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "book_rate value : "+book_rate);
//							double amount = Double.parseDouble(transactionAmount);
//							converted_amount = book_rate * amount;
//							
//							String roundoff= String.format("%.2f", converted_amount);
//							converted_amt = Double.parseDouble(roundoff);
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "converted_amount value : "+converted_amt);
//						} else {
//							executeCall = false;
//							converted_amt =0;
//							utcEligible = "Yes";
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside else utcEligible value : "+utcEligible);
//
//							converted_amount=0;
////							String query="update ext_tfo set utc_converted_amount = '0',utc_required_brms = 'Yes' where wi_name='"+this.sWorkitemID+"'";
////							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Saving in ext table records query : "+query);
////							int output=formObject.saveDataInDB(query);
//						}
//
//					} else {
//						//converted_amt =  Double.parseDouble(transactionAmount);
						//converted_amt =  Double.parseDouble(transactionAmount);
//						//Added By Ayush
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "transactionAmount :"+transactionAmount);
//
//						converted_amount=Double.parseDouble(new BigDecimal(transactionAmount).toBigInteger().toString());
//						//converted_amt=Double.parseDouble(new BigDecimal(transactionAmount).toPlainString());
//					}
//					//Query change - convertedAmount
//					String output=APCallCreateXML.APUpdate("ext_tfo", "utc_converted_amount", "'"+converted_amt+"'", whereClause, sessionId);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Aupdate query result : "+output);
//					output=APCallCreateXML.APUpdate("ext_tfo", "utc_book_rate", "'"+book_rate+"'", whereClause, sessionId);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Aupdate query result : "+output);
//				}
//			}
//		} catch (Exception e) {
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//		
//		return String.valueOf(converted_amt);
//	
//	}
	
	public void onSubmitReferral(String sActivityName){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ActivityName "+sActivityName+ "   Workitem ID"+WI_NAME);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "");
		try{
		String sReferTo="";
		String lst;
		
		if("RM".equalsIgnoreCase(sActivityName)){
			sReferTo = "RM";
		}
		else if("LEGAL_TEAM".equalsIgnoreCase(sActivityName)){
			sReferTo = "Legal";
		}
		else if("TSD".equalsIgnoreCase(sActivityName)){
			sReferTo = "TSD";
		}
		else if("CUSTOMER_REVIEW".equalsIgnoreCase(sActivityName) ||
				"Customer Referral Response".equalsIgnoreCase(sActivityName)){
			sReferTo = "Customer";
		}
		else if("CORRESPONDENT_BANK".equalsIgnoreCase(sActivityName)){
			sReferTo = "Correspondent Bank";
		}
		else if("Treasury".equalsIgnoreCase(sActivityName)){
			sReferTo = "Treasury";
		}
		else if("Trade Sales".equalsIgnoreCase(sActivityName)){
			sReferTo = "Trade Sales";
		}
		else if("COMPLIANCE".equalsIgnoreCase(sActivityName)){
			sReferTo = "COMPLIANCE";
		}
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loadDecReferalGrid: " + processType);
		String whereClause ="WI_NAME='" + WI_NAME + "' AND REFCODE='"+sReferTo+"'";
		
			lst = APCallCreateXML.APUpdate("TFO_DJ_TSLM_DOCUMENT_REVIEW", "FLAG_DEL", "'Y'", whereClause, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "  lst :"+lst);		
		
			lst = APCallCreateXML.APUpdate("TFO_DJ_TSLM_REFERRAL_DETAIL", "FLAG_DEL", "'Y'", whereClause, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "  lst :"+lst);
			
			lst = APCallCreateXML.APUpdate("TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW", "FLAG_DEL", "'Y'", whereClause, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "  lst :"+lst);
			
			setReferralStatusOnLoad("SIGN_REFERRAL_ID");
			setReferralStatusOnLoad("Doc_Review_RefID");
			setReferralStatusOnLoad("LIMIT_REFERRAL_ID");
	}catch(Exception e){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
	}
	}
	
	public void setReferralStatusOnLoad(String listVwName){

		String tableName = "";
		if ("SIGN_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_REFERRAL_DETAIL";
		}else if ("Doc_Review_RefID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_DOCUMENT_REVIEW";	
		} else if ("LIMIT_REFERRAL_ID".equalsIgnoreCase(listVwName)) {
			tableName = "TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW";
		}
		String status="";
		String seqNo="";
		String delFlag="";
		
		
		try{
		String result=APCallCreateXML.APSelect("SELECT SEQNO,STATUS,FLAG_DEL FROM "+tableName+" WHERE WI_NAME='"+WI_NAME+"'");
		XMLParser xp= new XMLParser(result);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loadTabListView_TSLM output: "+result);
		int length = 0;
		length=xp.getNoOfFields("Record");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loadTabListView_TSLM length: "+length);
		XMLParser innerXp=new XMLParser();
		for(int i=0;i<length;i++){
			innerXp.setInputXML(xp.getNextValueOf("Record"));
			status=innerXp.getValueOf("STATUS");
			seqNo=innerXp.getValueOf("SEQNO");
			delFlag=innerXp.getValueOf("FLAG_DEL");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "status: "+status);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "seqNo: "+seqNo);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "delFlag: "+delFlag);
			
			if(delFlag.trim().equalsIgnoreCase("Y")){
			
			String whereClause ="WI_NAME = '"+WI_NAME+"' AND SEQNO='"+seqNo+"'";
			String sUpdateOutput = APCallCreateXML.APUpdate(tableName, "STATUS", "'Closed'", whereClause,sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "updateReferralDetailsTSLM Output: "+sUpdateOutput);
			}
		}
		
		
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	//Added by reyaz 29/03/2023	
//	public void insertTrsdDetails() { //ATP-284 by Shivanshu
//		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails call: inside");
//		
//		StringBuffer strColumns = new StringBuffer();
//		StringBuffer strValues = new StringBuffer();
//		String entityname ="";
//		String channelRefNum ="";
//		int start;
//		int deadEnd;
//		int noOfFields;
//		String whereClause ="WINAME = '"+WI_NAME+ "'";
//		try {
//			//DELETE EXISTING DATA IF MODE IS M
//			if(mode.equalsIgnoreCase("M")){
//			APCallCreateXML.APDelete("tfo_dj_trsd_screening_other_details", 
//					whereClause, sessionId);
//			}
//			
//			String outputXml = APCallCreateXML.APSelect("SELECT CUSTOMER_NAME FROM  EXT_TFO WHERE  WI_NAME  = '"+WI_NAME+"'");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml:" +outputXml);
//			XMLParser Xp = new XMLParser(outputXml);
//			int mainCode = Integer.parseInt(Xp.getValueOf("MainCode"));
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"+Integer.parseInt(Xp.getValueOf("TotalRetrieved")));	
//			if (mainCode == 0 && Integer.parseInt(Xp.getValueOf("TotalRetrieved"))!= 0) {
//				entityname =Xp.getValueOf("CUSTOMER_NAME");
//				channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//				strColumns.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
						//		+ "channel_reference_no,execution_status");
//				strValues.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','DRAWEE/BUYER','"+entityname+"',"
//						+ "'Organisation','"+channelRefNum+"','N'");
//				String opXml = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), 
//						strValues.toString(), sessionId);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml: "+opXml);
//			}
//			    String opXml =APCallCreateXML.APSelect("SELECT DISTINCT COUNTERPARTY_NAME, SHIPMENT_DELIVERYTO, SHIPMENT_DELIVERYFROM , GOOD_SERVICE_DESCRIPTION, GOOD_SERVICE_ORIGIN  from TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA WHERE WI_NAME ='"+WI_NAME+"' AND REQUESTMODE = '"+mode+"' AND ROWNUM<=200");
//			    XMLParser xp = new XMLParser(opXml);
//				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
//				if (mainCode == 0 && Integer.parseInt(Xp.getValueOf("TotalRetrieved"))!= 0) {
//					start = xp.getStartIndex("Records", 0, 0);
//					deadEnd = xp.getEndIndex("Records", start, 0);
//					noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in Po invoice table: "
//							+noOfFields);
//					StringBuffer strColumns1 = new StringBuffer();
						//strColumns1.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
						//		+ "channel_reference_no,execution_status");
//					
//					for (int i = 0; i < noOfFields; ++i) {
//						String Record = xp.getNextValueOf("Record");
//						
//						StringBuffer strValues1 = new StringBuffer();
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tslm referral: Record :"+Record);
//						XMLParser xp2 = new XMLParser(Record); 
//						//INSERT COUNTER PARTY IN OPTIONAL ENTITIES TO BE ADDED GRID
//						entityname = xp2.getValueOf("COUNTERPARTY_NAME");
//						channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//						
//						strValues1.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','DRAWER/SELLER','"+entityname+"',"
//								+ "'Organisation','"+channelRefNum+"','N'");
//						String opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns1.toString(), 
//								strValues1.toString(), sessionId);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml1: "+opXml1);
//						
//						strValues1 = new StringBuffer();
//						//INSERT SHIPMENT_DELIVERYTO IN OPTIONAL ENTITIES TO BE ADDED GRID
//						entityname = xp2.getValueOf("SHIPMENT_DELIVERYTO");
//						channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//						//.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
//						//		+ "channel_reference_no,execution_status");
//						strValues1.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Port of Discharge/Airport of Destination','"+entityname+"',"
//								+ "'Organisation','"+channelRefNum+"','N'");
//						opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns1.toString(), 
//								strValues1.toString(), sessionId);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml1: "+opXml1);
//						
//						strValues1 = new StringBuffer();
//						//INSERT SHIPMENT_DELIVERYFROM IN OPTIONAL ENTITIES TO BE ADDED GRID
//						entityname = xp2.getValueOf("SHIPMENT_DELIVERYFROM");
//						channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//						//strColumns1.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
//						//		+ "channel_reference_no,execution_status");
//						strValues1.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Port of Loading/Airport of Departure','"+entityname+"',"
//								+ "'Organisation','"+channelRefNum+"','N'");
//						 opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns1.toString(), 
//								strValues1.toString(), sessionId);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml1: "+opXml1);
//						
//						strValues1 = new StringBuffer();
//						//INSERT GOOD_SERVICE_DESCRIPTION IN OPTIONAL ENTITIES TO BE ADDED GRID
//						entityname = xp2.getValueOf("GOOD_SERVICE_DESCRIPTION");
//						channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//						//strColumns1.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
//							//	+ "channel_reference_no,execution_status");
//						strValues1.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Goods/Services','"+entityname+"',"
//								+ "'Organisation','"+channelRefNum+"','N'");
//						opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns1.toString(), 
//								strValues1.toString(), sessionId);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml1: "+opXml1);
//						
//						strValues1 = new StringBuffer();
//						//INSERT GOOD_SERVICE_ORIGIN IN OPTIONAL ENTITIES TO BE ADDED GRID
//						entityname = xp2.getValueOf("GOOD_SERVICE_ORIGIN");
//						channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//						//strColumns1.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
//								//+ "channel_reference_no,execution_status");
//						strValues1.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Country of Origin','"+entityname+"',"
//								+ "'Organisation','"+channelRefNum+"','N'");
//						opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns1.toString(), 
//								strValues1.toString(), sessionId);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml1: "+opXml1);
//						
//					}
//				}
//					//INSERT VESSEL_NAME IN LLI GRID
//					opXml =APCallCreateXML.APSelect("SELECT DISTINCT VESSEL_NAME from TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA WHERE WI_NAME ='"+WI_NAME+"' AND REQUESTMODE = '"+mode+"' AND ROWNUM<=200");
//				    xp = new XMLParser(opXml);
//					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
//					if (mainCode == 0 && Integer.parseInt(Xp.getValueOf("TotalRetrieved"))!= 0) {
//						start = xp.getStartIndex("Records", 0, 0);
//						deadEnd = xp.getEndIndex("Records", start, 0);
//						noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in Po invoice table: "
//								+noOfFields);
//						StringBuffer strColumns2 = new StringBuffer();
//						strColumns2.append("SNO,WI_NAME,VESSEL_NAME,INSERTIONORDERID");
//						
//						StringBuffer strValues1 = new StringBuffer();
//						
//						for (int i = 0; i < noOfFields; ++i) {
//							String Record = xp.getNextValueOf("Record");
//							
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tslm referral: Record :"+Record);
//							XMLParser xp2 = new XMLParser(Record); 
//							
//							strValues1 = new StringBuffer();
//							
//							entityname = xp2.getValueOf("VESSEL_NAME");
//							if(!entityname.trim().isEmpty()){
//								channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//								strValues1.append("'"+(i+1)+"','"+WI_NAME+"','"+entityname+"',"+getInserIdForTable("S_TFO_DJ_LLI_VESSEL_DTLS"));
//								String opXml1 = APCallCreateXML.APInsert("TFO_DJ_LLI_VESSEL_DTLS", strColumns2.toString(), 
//										strValues1.toString(), sessionId);
//								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert VESSEL IN LLI GRID opXml1: "+opXml1);
//							}
//						}
//					}
//					
//					//Added for Country Details
//					opXml =APCallCreateXML.APSelect("SELECT DISTINCT(COUNTRY) FROM TFO_DJ_TSLM_COUNTER_PARTY_DETAILS WHERE WINAME  = '"+WI_NAME+"'");
//				    xp = new XMLParser(opXml);
//					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
//					if (mainCode == 0 && Integer.parseInt(Xp.getValueOf("TotalRetrieved"))!= 0) {
//						start = xp.getStartIndex("Records", 0, 0);
//						deadEnd = xp.getEndIndex("Records", start, 0);
//						noOfFields = xp.getNoOfFields("Record", start, deadEnd);
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in TSLM COUNTER PARTY: "
//								+noOfFields);
//						StringBuffer strColumns2 = new StringBuffer();
//						strColumns2.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
							//	+ "channel_reference_no,execution_status");
//						
//						for (int i = 0; i < noOfFields; ++i) {
//							String Record = xp.getNextValueOf("Record");
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tslm referral: Record :"+Record);
//							XMLParser xp2 = new XMLParser(Record); 														
//							entityname =xp2.getValueOf("COUNTRY");
//							channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//							strValues = new StringBuffer();
//							strValues.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','DRAWER/SELLER COUNTRY','"+entityname+"',"
//									+ "'Organisation','"+channelRefNum+"','N'");
//							String opXmlCountry = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns2.toString(), 
//									strValues.toString(), sessionId);
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert country detail fsk opXml: "+opXmlCountry);
//							
//						}
//					}
//					
//					//ADDED FOR BENEFICIARY DETAILS 12/07/2023
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"iNSIDE BENEFICIARY DETAILS: ");
//					StringBuffer strColumns2 = new StringBuffer();
//					entityname ="";
//					strColumns2.append("insertionorderid,winame,entity,entity_name,trsd_screening_type,"
								//+ "channel_reference_no,execution_status");
//					
//					channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//					strValues = new StringBuffer();
//					strValues.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Beneficiary Bank Name','"+entityname+"',"
//							+ "'Organisation','"+channelRefNum+"','N'");
//					String opXmlCountry = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns2.toString(), 
//							strValues.toString(), sessionId);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert country detail fsk opXml: "+opXmlCountry);
//					
//					channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//					strValues = new StringBuffer();
//					strValues.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Beneficiary Bank Country','"+entityname+"',"
//							+ "'Organisation','"+channelRefNum+"','N'");
//					String opXmlCountry1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns2.toString(), 
//							strValues.toString(), sessionId);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert country detail fsk opXml: "+opXmlCountry1);
//					
//					channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//					strValues = new StringBuffer();
//					strValues.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Beneficiary Bank Correspondence''s Name','"+entityname+"',"
//							+ "'Organisation','"+channelRefNum+"','N'");
//					String opXmlCountry2 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns2.toString(), 
//							strValues.toString(), sessionId);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert country detail fsk opXml: "+opXmlCountry2);
//					
//					channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
//					strValues = new StringBuffer();
//					strValues.append("'"+getInserIdForTable("WFSEQ_ARRAY_TRSD_ID")+"','"+WI_NAME+"','Beneficiary Bank Correspondence''s Country','"+entityname+"',"
//							+ "'Organisation','"+channelRefNum+"','N'");
//					String opXmlCountry3 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns2.toString(), 
//							strValues.toString(), sessionId);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert country detail fsk opXml: "+opXmlCountry3);
//					
//		} catch (Exception e) {
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//		}
//}	
						
//		 public String getInserIdForTable(String seqName){ //ATP-284
//			String seqValue=null;
//			String outputXml ="";
//			try {
//				outputXml = APCallCreateXML.APSelect("select "+seqName+".nextval from dual");
//			} catch (NGException e) {
//				e.printStackTrace();
//			}
//			XMLParser xpCount = new XMLParser(outputXml);
//			int mainCode = Integer.parseInt(xpCount.getValueOf("MainCode"));
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"+Integer.parseInt(xpCount.getValueOf("TotalRetrieved")));	
//			if (mainCode == 0 && Integer.parseInt(xpCount.getValueOf("TotalRetrieved"))!= 0) {
//				seqValue =xpCount.getValueOf("NEXTVAL");
//			}
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "seqValue :" +seqValue);
//			return seqValue;
//		}		
						
// 		public void autoSubmitIFCPCApp(String custID){ //ATP-284
//
//			
//			StringBuffer strColumns1 = new StringBuffer();
//			StringBuffer strValues1 = new StringBuffer();
//			    String seqNo = "1";
//				String transType = "";
//				String TRANSID = "";
//				String REFCODE = "RM";
//				String REFERRALTYPE = "";
//				String REFDESC = "Counter Party creation Approval Required as per DLA";
//				String USERCMNT = "";
//				String STATUS = "Active";
//			
//			try
//			{
//			String TRADE_CUST_EMAIL_ID =fetchTradeEmailDetailfrmETL(custID,requestCategory);
//			String insertionorderID=LapsUtils.returnInsertionOrderID("TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," insertionorderID: " +insertionorderID);
//
//			strColumns1.append("SEQNO,TRANSTYPE,TRANSID,REFCODE,REFERRALTYPE,REFDESC,USERCMNT,STATUS,COUNTERPARTYCID,WI_NAME,INSERTIONORDERID,FLAG_DEL");
//								            strValues1.append("'"+seqNo+"','"+transType+"','"+TRANSID+"','"+REFCODE+"','"+REFERRALTYPE+"','"+REFDESC+"','"+USERCMNT+"','"+STATUS+"','','"+this.WI_NAME+"',"+insertionorderID+",'N'");
//			String opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW",strColumns1.toString(), strValues1.toString(), sessionId);
//												   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert opXml: " + opXml);	
//			
//	       //CODE TO INSERT DATA IN FINAL DEC SUMMARY TABLE
//			String tablName="tfo_dj_final_dec_summary";						   
//            String outputXml1 = APCallCreateXML.APSelect("select S_"+tablName+".nextval from dual");
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"S_TFO_DJ_UTC_INVOICE_DETAIL data output XML:"+ outputXml1);
//				XMLParser xp1 = new XMLParser(outputXml1);
//				int mainCode = Integer.parseInt(xp1.getValueOf("MainCode").trim().isEmpty() ? "1" : xp1.getValueOf("MainCode").trim());
//				if (mainCode == 0) {
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+ Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
//					if (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0) {
//						insertionorderID = xp1.getValueOf("NEXTVAL");
//					}
//				}
//				StringBuffer strColumns2= new StringBuffer();
//				StringBuffer strValues2 = new StringBuffer();	
//			   String	MODULE    ="Limit/Credit Review";          
//			   String	REFFERDTO   ="RM";           
//			   String	DECISION     ="Yes";           
//			   String	EXCPREMARKS  ="Counter Party creation Approval Required as per DLA";         
//			   String	EXISTINGMAIL  =TRADE_CUST_EMAIL_ID;            
//			   
//			   strColumns2.append("TAB_MODULE,REFFERD_TO,DECISION,EXCP_REMARKS,EXISTING_MAIL,WI_NAME,INSERTIONORDERID");
//			   strValues2.append("'"+MODULE+"','"+REFFERDTO+"','"+DECISION+"','"+EXCPREMARKS+"','"+EXISTINGMAIL+"','"+this.WI_NAME+"',"+insertionorderID);
//			   String output = APCallCreateXML.APInsert("tfo_dj_final_dec_summary",strColumns2.toString(), strValues2.toString(), sessionId);
//				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
//				
//				//CODE TO MOVE WORKITREM TO RM
//				APCallCreateXML.APUpdate("EXT_TFO", "DEC_CODE", "'REF'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				APCallCreateXML.APUpdate("EXT_TFO", "IS_RM_PPM", "'Y'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				APCallCreateXML.APUpdate("EXT_TFO", "IS_CR_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				APCallCreateXML.APUpdate("EXT_TFO", "IS_LEGAL_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				APCallCreateXML.APUpdate("EXT_TFO", "IS_REF_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				APCallCreateXML.APUpdate("EXT_TFO", "IS_CB_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				APCallCreateXML.APUpdate("EXT_TFO", "IS_TRADE_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				
//				ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
//			}catch(Exception e){
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			}
// 		}
						
// 			public void autoSubmitPPM(String custID){  //ATP-284
// 				
// 				try{
// 				// added by reyaz 17-01-2023
// 				String outputXml2 = APCallCreateXML.APSelect("select AUTO_TRIGGER_EMAIL,TFO_GROUP_MAILID from TFO_DJ_AUTO_TRIGGER_MASTER");
// 				XMLParser xp3 = new XMLParser(outputXml2);
// 				String autoTrigger =xp3.getValueOf("AUTO_TRIGGER_EMAIL");
// 				String groupMailId =xp3.getValueOf("TFO_GROUP_MAILID");
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"autoTrigger :"+ autoTrigger);
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"groupMailId :"+ groupMailId);
// 					
// 				String TRADE_CUST_EMAIL_ID =fetchTradeEmailDetailfrmETL(custID,requestCategory);
// 				String autoReferralFlag=getAutoReferralFlag();
// 				String opXml =APCallCreateXML.APSelect("select 'Limit/Credit Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'"
// 							+" union "
// 							+"select 'Signature and Acc Bal Check' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_REFERRAL_DETAIL where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'"
// 							+" union "
// 							+"select 'Document Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_DOCUMENT_REVIEW where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'");
// 				XMLParser parser=new XMLParser(opXml);
// 				int mainCode = Integer.parseInt(parser.getValueOf("MainCode"));
//				if ((autoReferralFlag.trim().equalsIgnoreCase("Y")||autoReferralFlag.trim().equalsIgnoreCase("Yes"))&&mainCode == 0 && Integer.parseInt(parser.getValueOf("TotalRetrieved"))!= 0) {
//					int count=parser.getNoOfFields("Record");
//					
//					for(int i=0;i<count;i++){
//						String Record = parser.getNextValueOf("Record");
//						XMLParser xp2 = new XMLParser(Record);
//						
//						 //CODE TO INSERT DATA IN FINAL DEC SUMMARY TABLE
//		 				String insertionorderID="";
//		 				String tablName="tfo_dj_final_dec_summary";						   
//		 	            String outputXml1 = APCallCreateXML.APSelect("select S_"+tablName+".nextval from dual");
//		 					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"data output XML:"+ outputXml1);
//		 					XMLParser xp1 = new XMLParser(outputXml1);
//		 					int mainCode2 = Integer.parseInt(xp1.getValueOf("MainCode").trim().isEmpty() ? "1" : xp1.getValueOf("MainCode").trim());
//		 					if (mainCode2 == 0) {
//		 						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+ Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
//		 						if (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0) {
//		 							insertionorderID = xp1.getValueOf("NEXTVAL");
//		 						}
//		 					}
//		 					StringBuffer strColumns2= new StringBuffer();
//		 					StringBuffer strValues2 = new StringBuffer();	
//		 				   String	MODULE    =xp2.getValueOf("MODULE");          
//		 				   String	REFFERDTO   ="RM";           
//		 				   String	DECISION     ="Yes";           
//		 				   String	EXCPREMARKS  =xp2.getValueOf("refdesc");         
//		 				   String	EXISTINGMAIL  =TRADE_CUST_EMAIL_ID; 
//		 				   String  NEW_MAIL="";
//		 				   
//		 				   if(autoTrigger.trim().equalsIgnoreCase("Y")||autoTrigger.trim().equalsIgnoreCase("Yes"))
//		 					  NEW_MAIL=groupMailId;
//		 				   
//		 				   strColumns2.append("TAB_MODULE,REFFERD_TO,DECISION,EXCP_REMARKS,EXISTING_MAIL,NEW_MAIL,WI_NAME,INSERTIONORDERID");
//		 				   strValues2.append("'"+MODULE+"','"+REFFERDTO+"','"+DECISION+"','"+EXCPREMARKS+"','"+EXISTINGMAIL+"','"+NEW_MAIL+"','"+this.WI_NAME+"',"+insertionorderID);
//		 				   String output = APCallCreateXML.APInsert("tfo_dj_final_dec_summary",strColumns2.toString(), strValues2.toString(), sessionId);
//		 				   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
//		 					 
//						
//					}
//					
//					//CODE TO MOVE WORKITREM TO RM
// 					APCallCreateXML.APUpdate("EXT_TFO", "DEC_CODE", "'REF'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					APCallCreateXML.APUpdate("EXT_TFO", "IS_RM_PPM", "'Y'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					APCallCreateXML.APUpdate("EXT_TFO", "IS_CR_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					APCallCreateXML.APUpdate("EXT_TFO", "IS_LEGAL_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					APCallCreateXML.APUpdate("EXT_TFO", "IS_REF_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					APCallCreateXML.APUpdate("EXT_TFO", "IS_CB_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					APCallCreateXML.APUpdate("EXT_TFO", "IS_TRADE_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
// 					
// 					ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
//				}
// 					
// 				}catch(Exception e){
// 					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 				}
// 	 		}
 			
 		
 		public void postWorkItemCreationValidation(String custID,String productCode,String transactionCurrency,String transactionAmount){
 			
 			//Insertion of Counter Party Details
//			insertCounterPartyDetails(productCode); //ATP-284
			
			//Setting Supplier Buyer Name in Invoice Grid
//			setInvoiceDetail(); //ATP-284
			
			//Get Converted amount
//			String converted_amt = getCovertedAmount(transactionCurrency,transactionAmount); //ATP-284
			
			//get Profit Centre code //ATP-284
//			String profitCentreCode="";
//			String customerSummary=fetchcustomerSummary(custID);
//			setCustomerData(custID,customerSummary);
//			
//			if(customerSummary!=null && !customerSummary.trim().isEmpty()){
// 				XMLParser xp= new XMLParser(customerSummary);
// 				profitCentreCode=xp.getValueOf("ProfitCenterCode");
// 			}
			
			//INSERT CUSTOM JSP DATA
//			insertCustomJSPData(); //ATP-284 by Shivanshu
//			
//			String outputXml1="";
//			int mainCode;
//			
//			//Auto TRSD Trigger related changes
//			try{
//			outputXml1 = APCallCreateXML.APSelect("select AUTO_TRSD_TRIGGER from TFO_DJ_AUTO_TRIGGER_MASTER");
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml11:" +outputXml1);
//			XMLParser Xp3 = new XMLParser(outputXml1);
//		    mainCode = Integer.parseInt(Xp3.getValueOf("MainCode"));
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"+Integer.parseInt(Xp3.getValueOf("TotalRetrieved")));	
//			if (mainCode == 0 && Integer.parseInt(Xp3.getValueOf("TotalRetrieved"))!= 0) {
//				String autoTrsdFlag =Xp3.getValueOf("AUTO_TRSD_TRIGGER");
//				if("Y".equalsIgnoreCase(autoTrsdFlag) || "Yes".equalsIgnoreCase(autoTrsdFlag)){
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD STARTED FOR "+WI_NAME+" AT "+java.time.LocalDateTime.now());
//				insertTrsdDetails();
//				/*ExecuteTSLMTrsd callTrsd = new ExecuteTSLMTrsd();
//				callTrsd.dispatchEvent(sessionId,channelRefNo,correlationId,"SANCTION_SCREENING","C",WI_NAME,"TFO");*/
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml11: "+outputXml1);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD ENDED FOR "+WI_NAME+" AT "+java.time.LocalDateTime.now());
//				}
//			}
//			}catch(Exception e){
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			}
			
			//Auto UTC BRMS Trigger related changes //ATP-284
//					ExecuteTSLMBRMS executeBrms = new ExecuteTSLMBRMS(sessionId, WI_NAME,profitCentreCode,converted_amt,requestCategory);
//					try {
//						executeBrms.call();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//						e.printStackTrace();
//					}
//					
//		
			}
			
// 		public String fetchcustomerSummary(String custID){ //ATP-284
// 			return fetchFCRCustomer(custID);
// 		}
					
// 		public String fetchFCRCustomer(String sCustID) { //ATP-284
// 			String inputXML = "";
// 			String responseXML="";
// 			String sTxn="GetCustomerSummary";
// 			
// 			try{
// 			String sOutputlist = APCallCreateXML.APSelect("SELECT SEQ_WEBSERVICE.nextval as IDValue from DUAL");
// 			XMLParser xp =new XMLParser(sOutputlist);
// 			String txnNumber= xp.getValueOf("IDValue");
// 			inputXML = getFCRInputXML(sCustID, txnNumber,"TP906079", sTxn);
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertCounterPartyDetails inputXml ===> " + inputXML);
// 				 responseXML= LapsConfigurations.getInstance().socket.connectToSocket(inputXML);
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertCounterPartyDetails outputXml ===> " + responseXML);
// 				
// 			}catch(NGException e){
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 			}
// 			catch(Exception e1){
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
// 			}
// 			return responseXML;
// 		}
		
// 		private String getFCRInputXML(String sCustID, String sSeqNo, String sUserName, String sTxn) {  //ATP-284
// 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside getFCRInputXML >>>");
// 			return "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>Customer_Information</Calltype>"
// 			+ "<Customer>"
// 			+ "<CUST_ID>"+ sCustID + "</CUST_ID>"
// 			+ "<REF_NO>"+ sSeqNo + "</REF_NO>"
// 			+ "<txnType>"+ sTxn	+ "</txnType>"
// 			+ "<USER>"+ sUserName + "</USER>"
// 			+ "<WiName>"+ this.WI_NAME + "</WiName>"
// 			+ "</Customer>";
// 		}
 		
 		public void routeApplication(String hybridCustomer){
 			
 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside before EventGen routeApplication: ");
 			
 //	//ATP-284 by Shivanshu		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Hybrid Customer: "+hybridCustomer);
			
 			try{
 //ATP-284 by Shivanshu			//Route workitem to TO-DO LIST if REQ CAT -- IFP/IFS/IFA/TL and REQ TYPE - AMEND/STL	
 				//ATP-459 08-MAY-2024 --JAMSHED
 				if((hybridCustomer.equalsIgnoreCase("N") || hybridCustomer.equalsIgnoreCase("No")) || requestCategory.equalsIgnoreCase("IFCPC")) {
 				if(mode.equalsIgnoreCase("C")) { //ATP - 330
 					mode = "DC";
 				}else if (mode.equalsIgnoreCase("M")) {
 					mode = "DM";
 				}
 				APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA", "INSERTIONDATETIME, WI_NAME, PROCESS_NAME, "
						+ "SOURCING_CHANNEL, REQUESTMODE, EXPIRY_DATE, STATUS_FLAG", 
						"SYSDATE,'"+this.WI_NAME+"','TFO','TSLMSY','"+this.mode+"',"
								+ "SYSDATE + "+defaultMap.get("TSLM_DELAY_TIME")+",'N'", sessionId);
				APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'DC_ToDoList'", "WI_NAME = '"+this.WI_NAME+"'", sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
 				}
 				
 				if(hybridCustomer.equalsIgnoreCase("Y") || hybridCustomer.equalsIgnoreCase("Yes")) {
 					String sParameter = "'"+channelRefNo+"','"+mode+"','"+requestCategory+"'";	
	 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sParameter:" +sParameter);
					String output=APCallCreateXML.APProcedure(sessionId, "BPM_TFO_TSLM_MOVE_DATA_TO_HISTORY", sParameter, 3);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +output);
 				}
 				//ATP-459 08-MAY-2024 --JAMSHED ENDS
 				
				
//ATP-284 by Shivanshu			//Route workitem to PP Maker if Hybrid Customer is no
//			if((hybridCustomer.equalsIgnoreCase("N") || hybridCustomer.equalsIgnoreCase("No"))){
//				APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'PP_MAKER'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
//				
//			}	
			
			//Route workitem to PP Maker if Request Category is IFCPC
//			if(requestCategory.equalsIgnoreCase("IFCPC")&&!hybridCustomer.equalsIgnoreCase("N")){
//				APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'PP_MAKER'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
//				ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
//			} //ATP-284 by Shivanshu
 			}catch(NGException e){
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
 			}
 			catch(Exception e1){
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
 			}
 		}
 		
 		
 		//To insert decision history grid
 		public void insertDecisionHistory(StringBuilder strDecHistColumns,StringBuilder[] strDecHistValues){
 			
 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside insertDecisionHistory: ");
 			
 			try{
 			if(strDecHistValues!=null){  //Added by reyaz 20-03-2023
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside strDecHistValues:::");
				strDecHistColumns.append("WI_NAME");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"strDecHistValues.length: "
						+strDecHistValues.length);
				for(int y=0; y<strDecHistValues.length; y++){
					strDecHistValues[y].append("'").append(WI_NAME).append("'");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strDecHistColumns: "
							+strDecHistColumns.toString());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strDecHistValues: "
							+strDecHistValues[y].toString());
					APCallCreateXML.APInsert(decHist, strDecHistColumns.toString(), strDecHistValues[y].toString()
							,sessionId);
					}
 				}
 			}catch(NGException e){
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
 			}
 			catch(Exception e1){
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
 			}
 			
 			if(mode.equalsIgnoreCase("C")){
	 			try{
	 			APCallCreateXML.APInsert(decHist, " WI_NAME,USERNAME,CREATE_DATE,USER_ID,ACTION,REMARKS", 
						"'"+this.WI_NAME+"','"+defaultMap.get("TSLM_INITIATOR_USERID")+"',SYSDATE,'"+
								defaultMap.get("TSLM_INITIATOR_USERID")+"','TSLM SCF Workitem Created','Initiated by "+
								defaultMap.get("TSLM_INITIATOR_USERID")+"'",sessionId);
	 			}catch(NGException e){
	 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
	 			}
	 			catch(Exception e1){
	 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
	 			}
 			}else if(mode.equalsIgnoreCase("M")){
 	 			try{
 	 	 			APCallCreateXML.APInsert(decHist, " WI_NAME,USERNAME,CREATE_DATE,USER_ID,ACTION,REMARKS", 
 	 						"'"+this.WI_NAME+"','"+defaultMap.get("TSLM_INITIATOR_USERID")+"',SYSDATE,'"+
 	 								defaultMap.get("TSLM_INITIATOR_USERID")+"','TSLM SCF Workitem Modified','Initiated by "+
 	 								defaultMap.get("TSLM_INITIATOR_USERID")+"'",sessionId);
 	 	 			}catch(NGException e){
 	 	 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
 	 	 			}
 	 	 			catch(Exception e1){
 	 	 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e1);
 	 	 			}
 	 	 		}
		
 		}
 		
 		
 		
// 		//Code to insert Custom JSP data
// 		public void insertCustomJSPData(){
// 			//ATP-284 by Shivanshu
// 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside insertCustomJSPData: ");
// 			String LimitAvailable="";
// 			String cashMarginPercentage="";
// 			String clientContributionAmt="";
// 			Double cashMarginPercentageD=0.0;
// 			Double clientContributionAmtD=0.0;
// 			String limitLine="";
// 			String CollatreralSelectedValue="";
// 			String output="";
// 			
// 			StringBuffer strColumns2= new StringBuffer();
//			StringBuffer strValues2 = new StringBuffer();
// 			
// 			try{
// 			String outputXML=APCallCreateXML.APSelect("select count(0) as COUNT from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where WI_NAME='"+this.WI_NAME+"'");
// 			XMLParser parser = new XMLParser(outputXML);
// 			
// 			int countLimitRef=Integer.parseInt(parser.getValueOf("COUNT"));
// 			
// 			
// 			if(countLimitRef>0)
// 				LimitAvailable="Yes";
// 			else
// 				LimitAvailable="No";
// 			
// 			outputXML=APCallCreateXML.APSelect("select CASH_MARGIN_PERCENTAGE,CUSTOMER_CONTRIBUTION_AMT,LIMIT_LINE from TFO_DJ_TSLM_SCF_TXN_DATA where wi_name = '"+this.WI_NAME+"'");
// 			parser = new XMLParser(outputXML);
// 			cashMarginPercentage=parser.getValueOf("CASH_MARGIN_PERCENTAGE");
// 			clientContributionAmt=parser.getValueOf("CUSTOMER_CONTRIBUTION_AMT");
// 			limitLine=parser.getValueOf("LIMIT_LINE");
// 			
// 			if(cashMarginPercentage!=null&&!cashMarginPercentage.trim().isEmpty()){
// 				cashMarginPercentage=cashMarginPercentage.trim().substring(0,cashMarginPercentage.length()-1);
// 				cashMarginPercentageD=Double.parseDouble(cashMarginPercentage);
// 			}
// 			if(clientContributionAmt!=null&&!clientContributionAmt.trim().isEmpty()){
// 				clientContributionAmtD=Double.parseDouble(clientContributionAmt);
// 			}
// 			if(cashMarginPercentageD>0){
// 				CollatreralSelectedValue="CASH MARGIN";
// 			}
// 			if(clientContributionAmtD>0){
// 				CollatreralSelectedValue="CLIENT CONTRIBUTION";
// 			}
// 			if(cashMarginPercentageD>0&&clientContributionAmtD>0){
// 				CollatreralSelectedValue="CASH MARGIN AND CLIENT CONTRIBUTION";
// 			}
// 			
// 			strColumns2.append("WI_NAME,LT_DOC_RVW_GDLINES,LT_RESPONSE,INSERTIONORDERID");
// 			
// 			
// 			//GENERATE INSERTION ORDER ID
// 			String insertionorderID="";
//			String tablName="TFO_DJ_LMT_CRDT_RECORDS";	
//			insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
//	        
//			strValues2.append("'"+this.WI_NAME+"','"+"Please input Cash Margin percentage."+"','"+String.valueOf(cashMarginPercentageD)+"',"+insertionorderID);
//			output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
// 			
//			
//			strValues2 = new StringBuffer();
//			insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
//			strValues2.append("'"+this.WI_NAME+"','"+"Please specify Client Contribution Amount"+"','"+String.valueOf(clientContributionAmtD)+"',"+insertionorderID);
//			output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
//			
//			strValues2 = new StringBuffer();
//			insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
//			strValues2.append("'"+this.WI_NAME+"','"+"Please specify Loan Limit Line to be attached to the contract"+"','"+String.valueOf(limitLine)+"',"+insertionorderID);
//			output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
//			
//			strValues2 = new StringBuffer();
//			insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
//			strValues2.append("'"+this.WI_NAME+"','"+"Please select Collateral details."+"','"+CollatreralSelectedValue+"',"+insertionorderID);
//			output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
//			
//			strValues2 = new StringBuffer();
//			insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
//			strValues2.append("'"+this.WI_NAME+"','"+"Transaction within available Limits and Limits are available?"+"','"+LimitAvailable+"',"+insertionorderID);
//			output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
//			
//			
// 			}catch(Exception e){
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 			}
// 		}
 			
// 		public void setCustomerData(String custID,String cutomerSummary){
// 			//ATP-284 by Shivanshu
// 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside setCustomerData   >>>>>>>>" );
// 			cutomerSummary = cutomerSummary.replace("null", "");
// 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"resCustSmry   >>>>>>>>" + cutomerSummary);
// 			XMLParser xmlDataParser = new XMLParser(cutomerSummary);
// 			String sReturnCode = xmlDataParser.getValueOf("returnCode");
// 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"return code: "+sReturnCode);
// 			
// 			try {
// 				if ("0".equalsIgnoreCase(sReturnCode)|| "2".equalsIgnoreCase(sReturnCode)) {
// 					StringBuffer strColumns1 = new StringBuffer();
//					StringBuffer strValues1 = new StringBuffer();
//					String whereClause ="WI_NAME = '"+WI_NAME+ "'";
//					
//					strColumns1.append("CUSTOMER_CATEGORY,FCR_CUST_EMAIL_ID,PROFIT_CENTER_CODE,ADDRESS_LINE_1,ADDRESS_LINE_2 ,MOBILE_NUMBER,PO_BOX,FAX_NO,EMIRATES,RM_NAME ,RM_MOBILE_NUMBER,IS_CUSTOMER_VIP,RM_EMAIL,TRADE_CUST_EMAIL_ID,RM_CODE");
//		            
//		            String strFullName = xmlDataParser.getValueOf("FullName").trim();//Full Customer name insertion removed after UTC Buyer Name and Customer is not matching
//					String ADDRESS_LINE_1= xmlDataParser.getValueOf("AddressLine_1");
//					String ADDRESS_LINE_2=xmlDataParser.getValueOf("AddressLine_2");
//					String CUSTOMER_CATEGORY= xmlDataParser.getValueOf("CustCategory");
//					String EMIRATES=	xmlDataParser.getValueOf("State");
//					String MOBILE_NUMBER=xmlDataParser.getValueOf("Mobile");
//					String PO_BOX= xmlDataParser.getValueOf("POBox");
//					String PROFIT_CENTER_CODE=xmlDataParser.getValueOf("ProfitCenterCode") + " - "
//							+ xmlDataParser.getValueOf("ProfitCenterName");
//					String RM_CODE=xmlDataParser.getValueOf("RMCode");
//					String RM_NAME=xmlDataParser.getValueOf("RMName");
//					String FAX_NO= xmlDataParser.getValueOf("Fax");
//					String FCR_CUST_EMAIL_ID=xmlDataParser.getValueOf("Email");
//					String  IS_CUSTOMER_VIP= isVIPCustomerCheck(custID);
//					String[] rmDetail=fetchRMDeatilfrmETL(RM_CODE);
//					String RM_EMAIL=rmDetail[0];
//					String RM_MOBILE_NUMBER=rmDetail[1];
//					String TRADE_CUST_EMAIL_ID =fetchTradeEmailDetailfrmETL(custID,requestCategory);
//					
//					
//					strValues1.append("'"+CUSTOMER_CATEGORY+"','"+FCR_CUST_EMAIL_ID+"','"+PROFIT_CENTER_CODE+"','"+ADDRESS_LINE_1+"','"+ADDRESS_LINE_2+"','"+MOBILE_NUMBER+"','"+PO_BOX+"','"+FAX_NO+"','"+EMIRATES+"','"+RM_NAME+"','"+RM_MOBILE_NUMBER+"','"+IS_CUSTOMER_VIP+"','"+RM_EMAIL+"','"+TRADE_CUST_EMAIL_ID+"','"+RM_CODE+"'");
//					
//					String opXml1 = APCallCreateXML.APUpdate("EXT_TFO", strColumns1.toString(), strValues1.toString(),
//							whereClause, sessionId);
//					   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);	
// 					
// 				}
// 			}catch(Exception e){
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 			}
// 		}
 			
// 		private String isVIPCustomerCheck(String cid) { //ATP-284 by Shivanshu
// 			String isVIPCustomerCheck="";
// 			
// 			try {
// 				String str = "SELECT COUNT(1) CNT FROM LG_VIP_MASTER WHERE CUSTOMER_ID='"+ cid + "'";
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"isVIPCustomerCheck   " + str);
// 				String sOutputXML = APCallCreateXML.APSelect("SELECT COUNT(1) CNT FROM LG_VIP_MASTER WHERE CUSTOMER_ID = '"+ cid + "'");
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +sOutputXML);
// 				XMLParser Xp3 = new XMLParser(sOutputXML);
// 			    int CNT = Integer.parseInt(Xp3.getValueOf("CNT"));
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "CNT:"+CNT);	
// 				if (CNT> 0) {
// 					isVIPCustomerCheck= "Yes";
// 				}
// 				else
// 					isVIPCustomerCheck= "No";
// 			} catch (Exception e) {
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 			}
// 			return isVIPCustomerCheck;
// 		}
 			
// 		private String[] fetchRMDeatilfrmETL(String rmcode) { //ATP-284
// 			String[] rmDetail=new String[2];
// 			try {
// 				String str = "SELECT RM_EMAIL,RM_PHONE FROM USR_0_RM WHERE RM_CODE ='" + rmcode + "'";
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchRMDeatilfrmETL   " + str);
// 				String sOutputXML = APCallCreateXML.APSelect("SELECT RM_EMAIL,RM_PHONE FROM USR_0_RM WHERE RM_CODE ='" + rmcode + "'");
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +sOutputXML);
// 				XMLParser Xp3 = new XMLParser(sOutputXML);
// 				rmDetail[0]=Xp3.getValueOf("RM_EMAIL");
// 				rmDetail[1]=Xp3.getValueOf("RM_MOBILE_NUMBER");
// 				
// 			} catch (Exception e) {
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 			}
// 			return rmDetail;
// 		}
 			
// //ATP-284		private String fetchTradeEmailDetailfrmETL(String cid, String reqCat) {
// 			
// 			String tradeCustEmailID="";
// 			try {
// 				String tradeFinanceService = "";
//
// 				if("GRNT".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Guarantee";
// 				}else if("ELC".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Export LC";
// 				}
// 				else if("ILCB".equalsIgnoreCase(reqCat) ||  "IC".equalsIgnoreCase(reqCat) || "DBA".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Incoming Bill";
// 				}
// 				else if("ELCB".equalsIgnoreCase(reqCat) ||  "EC".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Outgoing Bill";
// 				}
// 				else if("SG".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Shipping Guarantee";
// 				}
// 				else if("ILC".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Import LC";
// 				}
// 				else if("IFA".equalsIgnoreCase(reqCat)|| "IFS".equalsIgnoreCase(reqCat) || "IFP".equalsIgnoreCase(reqCat)){
// 					tradeFinanceService="Loans";
// 				}
// 				String str = "SELECT email "
// 						+ "FROM TFO_DJ_TRADE_EMAIL_MAST "
// 						+ "WHERE CUST_ID ='"+ cid +"' and trade_finance_service='"+tradeFinanceService+"'  AND ROWNUM=1 union  all"+
// 						" select email  from tfo_dj_trade_email_mast where  cust_id='"+cid+"' "
// 								+ "and UPPER(trade_finance_service)=UPPER('ALL') AND ROWNUM=1";
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchTradeEmailDeatilfrmETL   " + str);
//
// 				String sOutputXML = APCallCreateXML.APSelect(str);
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +sOutputXML);
// 				XMLParser Xp3 = new XMLParser(sOutputXML);
// 				tradeCustEmailID=Xp3.getValueOf("email");
// 			} catch (Exception e) {
// 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
// 			}
// 			
// 			return tradeCustEmailID;
// 		}
 			
 //		public void moveDataFromStagingToHistory(){ //ATP-284
 //			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside moveDataFromStagingToHistory:" );
 //			try {
// 				String sParameter = "'"+channelRefNo+"','"+mode+"','"+requestCategory+"'";	
 //				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sParameter:" +sParameter);
//				String output=APCallCreateXML.APProcedure(sessionId, "BPM_TFO_TSLM_MOVE_DATA_TO_HISTORY", sParameter, 3);
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +output);
 //		
//			} catch (NGException e) {
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			}
 //		}
 		
 		private void insertIntoDecisionHistory(StringBuilder strDecHistValues ){
			StringBuilder strDecHistColumns = new StringBuilder();
			strDecHistColumns.append("WI_NAME").append(",").append("CURR_WS_NAME").append(",").append("CREATE_DATE").append(",")
			.append("USER_ID").append(",").append("USERNAME").append(",").append("ACTION").append(",")
			.append("REMARKS").append(",").append("REASON_FOR_ACTION").append(",").append("BRANCH");
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inert into strDecHistColumns: "
					+strDecHistColumns.toString());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inert into strDecHistValues: "
					+strDecHistValues.toString());
			try {
				APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", strDecHistColumns.toString(), strDecHistValues.toString()
						,sessionId);
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
 			
 		private String checkExistingReferralAndAutoClose(String referralType,String tableName ){
 			
 			String outputXml="";
 			int count;
 			XMLParser xp=null;
 			String PM_TSLM_SENDMSG_FLAG="";
 			String Status="Active";
 				
 			try{
 			outputXml = APCallCreateXML.APSelect("SELECT PM_TSLM_SENDMSG_FLAG FROM EXT_TFO WHERE WI_NAME='"+WI_NAME+"'");

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML PM_TSLM_SENDMSG_FLAG: "+outputXml);
			xp = new XMLParser(outputXml);
			PM_TSLM_SENDMSG_FLAG=xp.getValueOf("PM_TSLM_SENDMSG_FLAG");
			
			if(!PM_TSLM_SENDMSG_FLAG.trim().equalsIgnoreCase("Y")){
				
				outputXml = APCallCreateXML.APSelect("SELECT COUNT(0) CNT FROM "+tableName+" WHERE WI_NAME='"+WI_NAME+"' AND REFERRALTYPE='"+referralType+"'");

				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML PM_TSLM_SENDMSG_FLAG: "+outputXml);
				xp = new XMLParser(outputXml);
				count = Integer.parseInt(xp.getValueOf("CNT"));
				if (count>0) {
					Status="Closed";
		 		}
				
			}
			
 			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
 			
 			return Status;
 		}
 		
 		
 		 public String getInserIdForTable(String seqName){
				String seqValue=null;
				String outputXml ="";
				try {
					outputXml = APCallCreateXML.APSelect("select "+seqName+".nextval from dual");
				} catch (NGException e) {
					e.printStackTrace();
				}
				XMLParser xpCount = new XMLParser(outputXml);
				int mainCode = Integer.parseInt(xpCount.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"+Integer.parseInt(xpCount.getValueOf("TotalRetrieved")));	
				if (mainCode == 0 && Integer.parseInt(xpCount.getValueOf("TotalRetrieved"))!= 0) {
					seqValue =xpCount.getValueOf("NEXTVAL");
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "seqValue :" +seqValue);
				return seqValue;
			}
}