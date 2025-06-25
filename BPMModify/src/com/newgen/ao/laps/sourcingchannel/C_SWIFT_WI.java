package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.SwiftComplexMapping;
import com.newgen.ao.laps.cache.SwiftRuleEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

// Details :   fetch the data from Staging tables and put in respective tables.(External and Complex Tables)
// then create Workitem after that we make call to SwiftDocumentGen class of call Package to generate a Document and attach 
// to Workitem .Values for contractRefNo,processStatus,messageToPDF are initialized using DCN number received in dispatchEvent 
// parameter.

// Major change :: Data will be fetched from TXn table therefore while making column value hashmap direct column name 
// Will be used to get data from dcnValues Xml parser so no

public class C_SWIFT_WI implements SourcingChannelHandler{

	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<Integer,HashMap<String,String>> outerHashMap;
	private HashMap<Integer,HashMap<String,String>> partyOuterHashMap;
	private HashMap<String, String> attributeMap;
	private HashMap<String ,SwiftRuleEntity> SwiftRuleHashMap;
	private Map<String, Map<String, HashMap<String, String>>> SwiftFieldHashMap;
	private Map<String,SwiftComplexMapping> swiftComplexMapping;
	private LinkedHashMap<Integer, HashMap<String, String>> innerComplexAttribMap;
	private HashMap<String, String> innerInnerComplexMap;
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap;
	private Map<String,Map<String,HashMap<String, String>>> swiftDefaultHashMap ;
	private  Map<String,String> swiftDateMap =null;
	private  Map<String,String> swiftNumberMap =null;
	private HashMap<String, String> attributeCLOBMap; //11/01/2022
	Map<String,String> swiftCLOBMap;//11/01/2022
	XMLParser dcnValues;
	private int processDefId;
	String msgType;
	String tagValue;
	String tagType;
	String tagColumn;
	String ColumnValue;
	
	String processStatus = "";
	String reqCategory="";
	String reqType="";
	String branchCity="";
	String contractRefNo = "";
	String DCN = "";
	
	String PURPOSE_MESSAGE_A;
	String FORM_UNDERTAKING_B;
	String DEMAND_TYPE;
	String RuleID;
	//Modified ATP-458 Shivanshu 
	String INDEX_TYPE;
	String EXTENSION_TYPE;
	
	XMLParser outerParser;
	XMLParser innerParser;
	
	public char fieldSep = ((char)21);
	public char recordSep =((char)25);	
	String outputXML;
	int mainCode ;
	String txntableName = "TFO_DJ_SWIFT_TXN_DETAILS";
	String auditTableName = "TFO_DJ_MQ_NOTIFICATION_AUDIT";
	String auditColumns = "DCN,NOTIFY_CODE,MESSAGE_RECEIVED_DT,MESSAGE";
	String auditValues = "";
	String messageToPDF = "";
	String SwiftDocName ;//= "Customer_Request";
	String InitiateFromActivityName="";
	String InitiateFromActivityId;
	StringBuffer WFAttributeList = new StringBuffer();
	StringBuffer workitemAttrb = new StringBuffer();
	private String tableName = "";
	int stageId = 1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";
	String outputXml="";
	//private HashMap<String, String> sAttributeMap = new HashMap<String, String>();//commented by kishan 25/11/21 making it local
	
	/* (non-Javadoc)
	 * @see com.newgen.ao.laps.sourcingchannel.SourcingChannelHandler#dispatchEvent(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationId, String sourcingChannel, String mode, String wiNumber, String processName) {
			try { 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_SWIFT_WI 24/11/21");
				DCN = channelRefNumber;
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "DCN Number :: " +DCN);
				int start;
				int deadEnd;
				int noOfFields;
				
				//Swift Property Loading Start
				InitiateFromActivityName = LapsConfigurations.getInstance().InitiateFromActivityName;
				SwiftDocName = LapsConfigurations.getInstance().SwiftDocName;
				InitiateFromActivityId = LapsConfigurations.getInstance().InitiateFromActivityId;
				processDefId = LapsConfigurations.getInstance().processDefIdTFO;
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "After loading configuration changes");
				//Swift Property Loading Ends

				//Cache Map Loading Starts
				complexMap = new HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>>();//Complex table
				SwiftRuleHashMap = ChannelCallCache.getInstance().getSwiftRuleHashMap(); //req cat and req cat rule Master
				SwiftFieldHashMap = ChannelCallCache.getInstance().getSwiftFieldHashMap(); //Field Master Master
				swiftComplexMapping = ChannelCallCache.getInstance().getSwiftComplexHashMap(); //complex Master
				swiftDateMap = ChannelCallCache.getInstance().getSwiftDateMap();//date master Master
				swiftNumberMap = ChannelCallCache.getInstance().getSwiftNumberMap(); //Amount master Master
				swiftDefaultHashMap =ChannelCallCache.getInstance().getSwiftDefaultHashMap(); //default value master Master
				swiftCLOBMap=ChannelCallCache.getInstance().getSwiftClobHashMap();
				//Cache Map Loading ENDS
				if(verifySwiftWIWithDCN(DCN)){//Ameena 17042023 SWIFT Dupliacte Workitem change
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Creating Workitem with DCN"+DCN);

					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"AFTER CACHE LOADING ");
					loadSwiftTXNData(DCN);
					resp.setChannelRefNumber(channelRefNumber);
					resp.setCorrelationId(correlationId);
					XMLParser xp = new XMLParser(outputXml);

					if(SwiftFieldHashMap != null){ //(outerHashMap != null) {
						Map<String,HashMap<String, String>> workItemDetails = SwiftFieldHashMap.get(RuleID);//RuleID SwiftTablehashMap
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"workItemDetails : "+workItemDetails);
						SwiftComplexMapping cmplxMaster;
						attributeMap = new HashMap<String, String>();
						//attributeCLOBMap = new HashMap<String, String>(); //21/06/22
						if (null != workItemDetails) {
							for(Map.Entry<String, HashMap<String, String>> swiftMapEntry: workItemDetails.entrySet()){
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mapEntry.getKey(): "+swiftMapEntry.getKey());
								cmplxMaster = swiftComplexMapping.get(swiftMapEntry.getKey());
								tableName = cmplxMaster.getStagingTableName();
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," TableName : "+tableName);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," cmplxMaster.getComplexTableName() : "+cmplxMaster.getComplexTableName());//cmplxMaster.getComplexTableName()
								//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DATE_RECEIVED attributeValues: CHECK ");
								if(cmplxMaster.getStructureType().equals("EXTERNAL")){//swiftMapEntry.getKey().equals("EXTERNAL")){
									HashMap<String, String> attribList = swiftMapEntry.getValue();
									StringBuilder attributeValues = new StringBuilder();
									for(Map.Entry<String, String> value : attribList.entrySet()){
										if("DATE_RECEIVED".equalsIgnoreCase(value.getKey())){
											attributeValues.append("to_Char("+value.getKey()+",'dd/MM/YYYY HH24:mi:ss') as DATE_RECEIVED").append(",") ;
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DATE_RECEIVED attributeValues: "+attributeValues);
										}else{
											attributeValues.append(value.getKey()).append(",");
										}
									}
									attributeValues.setLength(attributeValues.length()-1);							
									try {
										outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues 
												+" FROM " + tableName +" WHERE DCN = '"+DCN+"' ");
									} catch (Exception e) {
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
									}
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"OutputXML attrib values: "+outputXml);
									xp = new XMLParser(outputXml);
									mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
									if (mainCode == 0) {
										attributeCLOBMap = new HashMap<String, String>();  //11/01/2022
										for(Map.Entry<String, String> value : attribList.entrySet()){
											String sColumn = value.getValue();
											String sColumnValue = xp.getValueOf(value.getKey());
											if (sColumn == null|| "".equalsIgnoreCase(sColumn)
													|| "null".equalsIgnoreCase(sColumn)) {
												sColumn = "";
											}
											if (!"".equalsIgnoreCase(sColumn)) {
												if ("CONTRACT_REF_NO".equalsIgnoreCase(sColumn)) {
													if (!"P".equalsIgnoreCase(processStatus))
														sColumn = "";
												} else if (swiftNumberMap.containsKey(sColumn)) {
													sColumnValue = sColumnValue.replaceAll(",", "");
												}/* else if ("DATE_RECEIVED".equalsIgnoreCase(sColumn)) { //DATE_RECEIVED to_Char(DATE_RECEIVED,'dd/MM/YYYY HH24:mi:ss') 
												LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DATE_RECEIVED CHECK 2");
												String time = sColumnValue.substring(11, 19);
												sColumnValue = sColumnValue.substring(8, 10)  + "/" +sColumnValue.substring(5, 7)  + "/" +sColumnValue.substring(0, 4) ;
												sColumnValue = sColumnValue+" "+time;
												LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"requested date time : " + sColumnValue);
											} */
											}

											if(swiftCLOBMap.containsKey(value.getValue()) && swiftCLOBMap.get(value.getValue()).equals("Y")){ //11/01/2022
												attributeCLOBMap.put(sColumn,sColumnValue);
											} else{
												attributeMap.put(sColumn, handleCData(sColumnValue));
											}
										}
									}
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"BEFORE DEFAULT VALUES LOAD");
									if(swiftDefaultHashMap.containsKey(RuleID)){
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE DEFAULT VALUES LOAD CONDITION 1");
										if(swiftDefaultHashMap.get(RuleID).size() > 0){ 
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE DEFAULT VALUES LOAD CONDITION 2");
											if(swiftDefaultHashMap.get(RuleID).containsKey(cmplxMaster.getComplexTableName())){
												LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"complextable ::" +cmplxMaster.getComplexTableName() );
												HashMap<String,String> defaultValues = swiftDefaultHashMap.get(RuleID).get(cmplxMaster.getComplexTableName());
												for(Map.Entry<String,String> enrty  : defaultValues.entrySet()){
													String Key = enrty.getKey();
													String value = enrty.getValue();
													attributeMap.put(Key, handleCData(value));
												}
											}
										}
									}
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"AFTER DEFAULT VALUES LOAD");
								} else {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Map for: "+swiftMapEntry.getKey());
									HashMap<String, String> attribList = swiftMapEntry.getValue();
									StringBuilder attributeValues = new StringBuilder();
									for(Map.Entry<String, String> value : attribList.entrySet()){
										attributeValues.append(value.getKey()).append(",");
									}
									attributeValues.setLength(attributeValues.length()-1);	
									outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
											" FROM " + tableName + " WHERE DCN = '"+DCN+"'");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML Attrib values: "+outputXml);
									xp = new XMLParser(outputXml);
									mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
									if (mainCode == 0) {
										innerComplexAttribMap = new LinkedHashMap<Integer, HashMap<String, String>>();
										start = xp.getStartIndex("Records", 0, 0);
										deadEnd = xp.getEndIndex("Records", start, 0);
										noOfFields = xp.getNoOfFields("Record", start, deadEnd);
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in party: "+noOfFields);
										for (int i = 0; i < noOfFields; ++i) { //Loop iterrating for every single row
											innerInnerComplexMap = new HashMap<String, String>();
											String Record = xp.getNextValueOf("Record");
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"cmplx Record :"+Record);
											XMLParser xp2 = new XMLParser(Record);
											for(Map.Entry<String, String> value : attribList.entrySet()){ 
												LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value: "+value.getKey());
												LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Key: "+value.getKey());
												LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getValueOf(value): "+xp2.getValueOf(value.getKey()));
												innerInnerComplexMap.put(value.getValue(), xp2.getValueOf(value.getKey())); 
											}
											innerComplexAttribMap.put(i, innerInnerComplexMap);
										}
										if(noOfFields>0){
											complexMap.put(swiftMapEntry.getKey(), innerComplexAttribMap);
										}
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"complexMap: "+complexMap.toString());
									}
								}
							}
						}	
						attributeMap.put("SWIFT_UTILITY_FLAG", "Y");
						attributeMap.put("SOURCE_CHANNEL", "ISA");
						attributeMap.put("PROCESS_TYPE", "SWIFT");
						attributeMap.put("CURR_WS", "Initiator");
						attributeMap.put("DCN", DCN);
						initiatorDetails(msgType,contractRefNo,sessionId);
						outputXML = ProdCreateXML.WFUploadWorkItem( sessionId,"N"
								,attributeMap,"",processDefId,InitiateFromActivityName);//Function to Create a Workitem
						String wi_name="";
						if (!("".equalsIgnoreCase(outputXML) || null == outputXML)) {
							outerParser = new XMLParser(outputXML);
							mainCode = Integer.parseInt(outerParser.getValueOf("MainCode"));
							if (mainCode == 0) {
								wi_name = outerParser.getValueOf("ProcessInstanceId");
								String itemIndex = outerParser.getValueOf("FolderIndex");

								//APCallCreateXML.APUpdate("EXT_TFO", "WI_NAME", "'"
								//+ wi_name + "'", " ITEMINDEX = N'"+ itemIndex + "'", sessionId);

								APCallCreateXML.APUpdate(txntableName, "WI_NAME,REQUEST_CATEGORY,REQUEST_TYPE,BRANCH_CITY",
										"'" + wi_name + "','"+reqCategory+"','"+reqType+"','"+branchCity+"'", " DCN = N'" + DCN
										+ "'", sessionId);	
								APCallCreateXML.APUpdate(auditTableName, "WI_NAME",
										"'" + wi_name + "'", " DCN = N'" + DCN+ "'", sessionId);
								if ("R".equalsIgnoreCase(processStatus) || "720".equalsIgnoreCase(msgType)){//Point to be checked
									savePartyDetails(RuleID, wi_name,sessionId);//Changed 1st parameter from msg_type to ruleid
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "R condition CHECK");
								}
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is " + wi_name);
								resp.setWiNumber(wi_name);
								wiNumber = wi_name;
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexMap final: " 
										+ complexMap.toString());
								attributeMap.clear();
								if(attributeCLOBMap == null){ //22-06-22
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attributeCLOBMap is " + "NULL");
									attributeCLOBMap = new HashMap<String, String>();
								}
								attributeCLOBMap.put("WI_NAME", wi_name); //11/01/2021
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attributeCLOBMap final: " 
										+ attributeCLOBMap.toString());
								try {
									String outputXmlAssgn = ProdCreateXML.WFSwiftSetAttributes(sessionId, wi_name, 1, 
											attributeCLOBMap,complexMap,processDefId);//Function to insert data of complex into tables//attributeCLOBMap,complexMap,processDefId);
									XMLParser xp1 = new XMLParser(outputXmlAssgn);
									int mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Assign Attributes Main Code: "
											+mainCodeAssgn);
								} catch (Exception e) {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in assign attributes: ");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
								}
								ProdCreateXML.WMUnlockWorkItem(sessionId, wi_name, 1);
							}
						}else {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "create workitem will not be done !!!");
						}
						try {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number :: " +wi_name+ " : sourcingChannel :: " +sourcingChannel);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number :: " +wi_name+ " : wi_name :: " +wi_name);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number :: " +wi_name+ " : sessionId :: " +sessionId);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number :: " +wi_name+ " : DCN :: " +DCN);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem Number :: " +wi_name+ " : messageToPDF :: " +messageToPDF);
							calltoSwiftDocGenEvent(sourcingChannel,wi_name,sessionId,DCN,messageToPDF);
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Error :"+e);
						}
					}
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Duplicate Workitem cannot be Created !!!");
					statusDesc = "Duplicate Workitems cannot be Created with DCN."+DCN;
					status = "-1";
				}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Catch exception:: "+e);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Catch exception:: "+e.toString());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Catch exception:: "+e.getLocalizedMessage());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Catch exception:: "+e.getCause());
		}finally{
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
		}	
		return resp;
	}

	private String handleCData(String value){
		try { 
			if(!"".equalsIgnoreCase(value) && null != value)
			{
				if (value.contains("<![CDATA[")) {
					value = value.substring(9, value.length());
					value = value.substring(0, value.length() - 3);
				} 
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return value;
	}

	private void initiatorDetails(String msgType, String contractRefNo,String sessionId) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Initiator details starts");//28/11/21
		String sQuery;
		String outputXML;
		int mainCode;
		String req_cat = "";
		String req_type = "";
		String iss_center;
		Boolean reqMasterFound = true;
		Boolean grntNiFound = true;
		int tagCount;
		XMLParser outerParser = new XMLParser();
		StringBuffer workAttribute = new StringBuffer();
		try {
			reqCategory="";
			reqType="";
			String key = msgType +"#"+PURPOSE_MESSAGE_A+"#"+FORM_UNDERTAKING_B+"#"+DEMAND_TYPE+"#"+INDEX_TYPE+"#"+EXTENSION_TYPE;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "KEY InitiatorDetails: " + key);
			if(SwiftRuleHashMap.containsKey(key)){
				SwiftRuleEntity RuleReqCatTypMast = SwiftRuleHashMap.get(key);
				String ruleReqCat = RuleReqCatTypMast.getRequestCategory();
				String ruleReqType = RuleReqCatTypMast.getRequesttype();
				ArrayList<String> subRuleMasterList = RuleReqCatTypMast.getSubRule();
				String ruleRuleID = RuleReqCatTypMast.getRuleID();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Rule Data by key : " + ruleReqCat + " :: "+ruleReqType
						+" :: "+ruleRuleID + " :: "+ subRuleMasterList.toString());
				if(subRuleMasterList.size()>0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside Subrule Condition  : "); //28/11/21
					for(String master :subRuleMasterList){
						String[] mapArray = master.split("#");
						String subRuleReqCat = mapArray[0];
						String subRuleReqType = mapArray[1];
						String subRulemaster = mapArray[2];
						
						String query = "Select count(1) as DATACOUNT from " +subRulemaster+" where REQ_CAT='"+subRuleReqCat+"' "
								+ "and gtee_number = '"+ contractRefNo+ "'";
						String subRuleOutputXML = APCallCreateXML.APSelect(query);
						XMLParser subRuleOuterParser = new XMLParser(subRuleOutputXML);
						int subRuleMainCode = Integer.parseInt(subRuleOuterParser.getValueOf("MainCode"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode : " + subRuleMainCode);
						
						if(subRuleMainCode ==0){
							int count = Integer.parseInt(subRuleOuterParser.getValueOf("DATACOUNT"));
							if(count >0){
								 reqCategory=subRuleReqCat;
								 reqType=subRuleReqType;
								 attributeMap.put("REQUEST_CATEGORY",reqCategory);
								 attributeMap.put("REQUEST_TYPE",reqType);
								 reqMasterFound = false;
								 break;
							}
						}
					}
				}
				if(reqCategory.equalsIgnoreCase("") && reqType.equalsIgnoreCase("") 
						&& !(ruleReqCat.equalsIgnoreCase("NA") && ruleReqType.equalsIgnoreCase("NA"))){
					 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside !NA If condition  : "); //28/11/21
					 reqCategory=ruleReqCat;
					 reqType=ruleReqType;
					 attributeMap.put("REQUEST_CATEGORY",reqCategory);
					 attributeMap.put("REQUEST_TYPE",reqType);
					 reqMasterFound = false;

				}
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "After !NA If condition reqMasterFound value :"+reqMasterFound); //28/11/21
				if ("GRNT".equalsIgnoreCase(reqCategory)&& "NI".equalsIgnoreCase(reqType) && checkUnassigned(msgType)) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"for gurrantee req_type : " + req_type);
					attributeMap.put("BRANCH_CITY","AUH");
					branchCity = "AUH";
					grntNiFound = false;
				}
			}
			
			
			/*old code		sQuery = "SELECT REQ_CAT_CODE,REQ_TYPE_CODE FROM TFO_DJ_SWIFT_REQ_CAT_MASTER WHERE MSG_TYPE  = '"
					+ msgType + "'";  //need to add other three column condition
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Query from master : "+ sQuery);
			outputXML = APCallCreateXML.APSelect(sQuery);
			outerParser = new XMLParser(outputXML);
			mainCode = Integer.parseInt(outerParser.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode : " + mainCode);

			if (mainCode == 0) {
				tagCount = outerParser.getNoOfFields("Record");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagCount : "+ tagCount);
				if (tagCount > 0) {
					req_cat = outerParser.getValueOf("REQ_CAT_CODE");
					req_type = outerParser.getValueOf("REQ_TYPE_CODE");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_cat : "+ req_cat);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_type : "+ req_type);

					attributeMap.put("REQUEST_CATEGORY",req_cat);
					attributeMap.put("REQUEST_TYPE",req_type);
					/*
					workAttribute.append("REQUEST_CATEGORY");
					workAttribute.append(fieldSep);
					workAttribute.append(req_cat);
					workAttribute.append(recordSep);

					workAttribute.append("REQUEST_TYPE");
					workAttribute.append(fieldSep);
					workAttribute.append(req_type);
					workAttribute.append(recordSep);*/

				/*	reqCategory=req_cat;
					reqType=req_type;
					if ("GRNT".equalsIgnoreCase(req_cat)&& "NI".equalsIgnoreCase(req_type) && checkUnassigned(msgType)) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"for gurrantee req_type : " + req_type);
						
						attributeMap.put("BRANCH_CITY","AUH");
						
						branchCity = "AUH";
						grntNiFound = false;
					}
					reqMasterFound = false;
				}
			}
*/
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "contractRefNo : "+ contractRefNo);
			if (!"".equalsIgnoreCase(contractRefNo) && null != contractRefNo) {
				sQuery = " select req_cat AS REQUEST_CATEGORY,  issuing_center AS ISS_CENTER from tfo_wms_master WHERE gtee_number = '"
						+ contractRefNo
						+ "' "
						+ " UNION "
						+ " select req_cat AS REQUEST_CATEGORY,  issuing_center AS ISS_CENTER from tfo_wms_master_if WHERE gtee_number = '"
						+ contractRefNo
						+ "' "
						+ " UNION "
						+ " select req_cat AS REQUEST_CATEGORY, issuing_center AS ISS_CENTER from tfo_wms_master_ieclcb  WHERE gtee_number = '"
						+ contractRefNo
						+ "' "
						+ " UNION "
						+ "select req_cat AS REQUEST_CATEGORY, issuing_center AS ISS_CENTER from tfo_wms_master_ielc  WHERE gtee_number = '"
						+ contractRefNo + "'";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sQuery for wms master : " + sQuery);
				outputXML = APCallCreateXML.APSelect(sQuery);
				outerParser = new XMLParser(outputXML);
				mainCode = Integer.parseInt(outerParser.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode : "+ mainCode);
				if (mainCode == 0) {
					tagCount = outerParser.getNoOfFields("Record");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagCount : "+ tagCount);
					if (tagCount == 1) {
						req_cat = outerParser.getValueOf("REQUEST_CATEGORY");
						iss_center = outerParser.getValueOf("ISS_CENTER");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "req_cat : "+ req_cat);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "iss_center : "+ iss_center);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"grntNiFound : " + grntNiFound);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"reqMasterFound : " + reqMasterFound);
						if (grntNiFound&& !(iss_center == null 
							|| "".equalsIgnoreCase(iss_center)) && (checkUnassigned(msgType) || 
							"708".equalsIgnoreCase(msgType))) {
							attributeMap.put("BRANCH_CITY",iss_center);
							branchCity = iss_center;
						}
						if (reqMasterFound&& !(req_cat == null || "".equalsIgnoreCase(req_cat))) {
							attributeMap.put("REQUEST_CATEGORY",req_cat);
							attributeMap.put("REQUEST_TYPE",req_cat + "_MSM");
							reqCategory=req_cat;
							reqType=req_cat + "_MSM";
						}
					}
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Request Category Before Mis condition : " +reqCategory);//28/11/21
			if ((reqCategory == null || "".equalsIgnoreCase(reqCategory)) 
					|| ( "NA".equalsIgnoreCase(reqCategory))){// || "NA".equalsIgnoreCase(reqType))) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "All is Null");
				attributeMap.put("REQUEST_CATEGORY","MISC");
				attributeMap.put("REQUEST_TYPE","MISC_MSM");
				reqCategory="MISC";
				reqType="MISC_MSM";
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Process end for Initiator");
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

	public String handleDates(String tagColumn, String tagValue) {
		if (tagValue != null && !"".equalsIgnoreCase(tagValue)) {
			if (tagValue.contains(":")) {
				tagValue = " to_date('" + tagValue+ "','yyyy-MM-dd hh24:mi:ss') ";
			}else{
				tagValue = " to_date('" + tagValue+ "','yyyy-MM-dd') ";
			} 
		} else {
			tagValue = "''";
		}
		return tagValue;
	}
	
	public boolean checkUnassigned(String msgType){
		try {
			if ("701".equalsIgnoreCase(msgType)  || "708".equalsIgnoreCase(msgType)
				|| "711".equalsIgnoreCase(msgType)  || "721".equalsIgnoreCase(msgType)){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return true; 
	}
	
	
	public boolean verifySwiftWIWithDCN(String DCN){
		boolean isWICreated = true;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside verifySwiftWIWithDCN"+DCN);
		try {
			String outputXml = APCallCreateXML.APSelect("select count(1) as WI_COUNT from EXT_TFO where DCN='"+DCN+"'");
			XMLParser xp = new XMLParser(outputXml);
			if(Integer.parseInt(xp.getValueOf("MainCode")) == 0 && Integer.parseInt(xp.getValueOf("WI_COUNT")) > 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Duplicate Entry with DCN:"+DCN);
				isWICreated = false;
			}
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return isWICreated; 
	}
	
	private void savePartyDetails(String RuleID, String wi_name,String sessionId){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside SavePartyDetails");
		String tagValue;
		String tagType;
		String tagColumn;
		String txnColumns = "";
		String txnValues = "";
		String ColumnValue;
		try {
			if (SwiftFieldHashMap != null) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside SavePartyDetails");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"saving party details starts for" + RuleID);
				HashMap<String, String> workItemDetails = SwiftFieldHashMap.get(Integer.parseInt(RuleID)).get("TFO_DJ_PARTY_DETAILS");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "workItemDetails : "+ workItemDetails);
				if (null != workItemDetails) {
					Iterator<Entry<String, String>> wiIterator = workItemDetails.entrySet().iterator();
					while (wiIterator.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry mapElement = (Map.Entry) wiIterator.next();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside iteration");
						tagColumn = mapElement.getKey().toString();
						tagType = mapElement.getValue().toString();
						ColumnValue = dcnValues.getValueOf(tagColumn);//
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagType : "+ tagType);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagColumn : "+ tagColumn);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ColumnValue : " + ColumnValue);

						if (ColumnValue == null || "".equalsIgnoreCase(ColumnValue)
								|| "null".equalsIgnoreCase(ColumnValue)) {
							ColumnValue = "";
						}
						txnColumns = txnColumns + tagColumn + ",";
						txnValues = txnValues + createNormalizedXML(handleCData(ColumnValue)) + ",";
					}
					txnColumns = txnColumns.substring(0,txnColumns.length() - 1);
					txnValues = txnValues.substring(0, txnValues.length() - 1);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "txnColumns :"+ txnColumns);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "txnValues :"+ txnValues);
					APCallCreateXML.APInsert("TFO_DJ_PARTY_DETAILS", txnColumns
							+ ",PARTY_ID,WINAME,PARTY_TYPE", txnValues+ ",'100000','" + wi_name + "','APP'", sessionId);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"saving party details ends");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}

	public static String createNormalizedXML(String outputXml) {
		try {
			if (outputXml != null && !"".equalsIgnoreCase(outputXml)) {

				outputXml = outputXml.replaceAll("'", "''");
				if (outputXml.length() > 3200) {
					int itr = outputXml.length() / 3200;
					int mod = outputXml.length() % 3200;
					if (mod > 0) {
						++itr;
					}
					StringBuilder response = new StringBuilder();
					try {
						for (int i = 0; i < itr; i++) {
							if (i == 0) {
								response.append("TO_NCLOB('"
										+ outputXml.substring(0, 3200) + "')");
							} else if (i < itr - 1) {
								response.append(" || TO_NCLOB('"
										+ outputXml.substring((3200 * i),
												3200 * (i + 1)) + "')");

							} else {
								response.append(" || TO_NCLOB('"
										+ outputXml.substring((3200 * i),
												outputXml.length()) + "') ");

							}
						}
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					outputXml = response.toString();
					return outputXml;
				} else {
					outputXml = "'" + outputXml + "'";
					return outputXml;
				}
			} else {
				outputXml = "";
				outputXml = "'" + outputXml + "'";
				return outputXml;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, ex);
		}
		return "";
	}
	//Modified ATP-458 Shivanshu
	private void loadSwiftTXNData(String DCN){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside Of loadSwiftTXNData");
		String query  = "Select PURPOSE_MESSAGE_A,MSG_TYPE,FORM_UNDERTAKING_B,DEMAND_TYPE,RELATED_REF,PROCESS_STATUS,INDEX_TYPE,EXTENSION_TYPE"
				+ ",MESSAGE from " +txntableName  +"  where DCN='"+DCN+"'"; //'"+msgType+"'";
		String outputXML = null;				
		try {
			outputXML = APCallCreateXML.APSelect(query);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		XMLParser valueParser = new XMLParser(outputXML);
		try {
			int mainCode = Integer.parseInt(valueParser.getValueOf("MainCode"));
			if(mainCode == 0){
				 PURPOSE_MESSAGE_A  = valueParser.getValueOf("PURPOSE_MESSAGE_A");
				 FORM_UNDERTAKING_B  = valueParser.getValueOf("FORM_UNDERTAKING_B");
				 DEMAND_TYPE =valueParser.getValueOf("DEMAND_TYPE");
				 msgType = valueParser.getValueOf("MSG_TYPE");
				 contractRefNo = valueParser.getValueOf("RELATED_REF"); //CONTRACT_REF_NO
				 processStatus = valueParser.getValueOf("PROCESS_STATUS");
				 messageToPDF = valueParser.getValueOf("MESSAGE");
				 INDEX_TYPE =  valueParser.getValueOf("INDEX_TYPE");
				 EXTENSION_TYPE = valueParser.getValueOf("EXTENSION_TYPE");
				 
				 String key = msgType +"#"+PURPOSE_MESSAGE_A+"#"+FORM_UNDERTAKING_B+"#"+DEMAND_TYPE+"#"+INDEX_TYPE+"#"+EXTENSION_TYPE;
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Key::"+key);
				 RuleID = SwiftRuleHashMap.get(key).getRuleID();
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"RULEID :: "+RuleID);
				
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"contractRefNo::"+contractRefNo);
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"processStatus::"+processStatus);
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"messageToPDF::"+messageToPDF);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"END Of loadSwiftTXNData");
		//return new  XMLParser(outputXML2);
	}
	
	/*private void addDataInComplexTable(String RuleID, String wi_name,String tableName,String sessionId){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside addDataInComplexTable");
		String tagValue;
		String tagType;
		String tagColumn;
		String txnColumns = "";
		String txnValues = "";
		String ColumnValue;
		try {
			if (SwiftTablehashMap != null) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside addDataInComplexTable");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Saving Complex details starts for" + RuleID);
				HashMap<String, String> workItemDetails = SwiftTablehashMap.get(Integer.parseInt(RuleID)).get(tableName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "workItemDetails : "+ workItemDetails);
				if (null != workItemDetails) {
					Iterator<Entry<String, String>> wiIterator = workItemDetails.entrySet().iterator();
					while (wiIterator.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry mapElement = (Map.Entry) wiIterator.next();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside Iteration");
						tagColumn = mapElement.getKey().toString();
						tagType = mapElement.getValue().toString();
						//ColumnValue = txnColumnValues.get(tagType);//dcnValues
						ColumnValue = dcnValues.getValueOf(tagColumn);//
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagType : "+ tagType);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tagColumn : "+ tagColumn);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ColumnValue : " + ColumnValue);

						if (ColumnValue == null || "".equalsIgnoreCase(ColumnValue)
								|| "null".equalsIgnoreCase(ColumnValue)) {
							ColumnValue = "";
						}
						txnColumns = txnColumns + tagColumn + ",";
						txnValues = txnValues + createNormalizedXML(handleCData(ColumnValue)) + ",";
					}
					txnColumns = txnColumns.substring(0,txnColumns.length() - 1);
					txnValues = txnValues.substring(0, txnValues.length() - 1);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "txnColumns :"+ txnColumns);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "txnValues :"+ txnValues);
					APCallCreateXML.APInsert(tableName, txnColumns, txnValues, sessionId);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Saving Complex details ends");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}*/
	
	private String calltoSwiftDocGenEvent(String sourcingChannel,String wiNumber,String sessionId,String sDCN,String sMessageToPDF ) throws Exception{//DCn and message should be passed in parater same like wi number
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Inside calltoSwiftDocgenEvent");
		HashMap<String, String> sAttributeMap = new HashMap<String, String>();//25/11/21
		sAttributeMap.put("DCN", sDCN);
		sAttributeMap.put("messageToPDF", sMessageToPDF);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"CalltoSwiftDocGenEvent For Wi :: " +wiNumber +" :: attribute values::" +sAttributeMap); //25/11/21
		String sOutputXML="";

		HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
				(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
		int eventID = (Integer) eventMap.keySet().toArray()[0];
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID 1: " + eventID);
		/*try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sAttributeMap 1: " + sAttributeMap);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sessionId 1: " + sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "String.valueOf(eventID) 1: " + String.valueOf(eventID));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wiNumber 1: " + wiNumber);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "new CallEntity() 1: " + new CallEntity());
			
			com.newgen.ao.laps.calls.SwiftDocumentGen obj 
			= new com.newgen.ao.laps.calls.SwiftDocumentGen(sAttributeMap,sessionId,String.valueOf(eventID),wiNumber,false,new CallEntity());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "EROR FOR CUSTOM SWIFT : "+ e);	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		*/
		List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
		XMLParser xp; 
		if(callArray != null) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
			boolean nogoCall = false;
			for (CallEntity callEntity : callArray) {
				if(callEntity.isMandatory()){
					sOutputXML = CallHandler.getInstance().executeCall(callEntity, sAttributeMap, sessionId,
									String.valueOf(eventID),wiNumber, false);
					xp = new XMLParser(sOutputXML);
					if("0".equalsIgnoreCase(status)){
						status = xp.getValueOf("returnCode");
					}
					if(!"0".equals(status)){
						statusDesc = xp.getValueOf("errorDescription");
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "+ sOutputXML);
				}
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"SwiftDocumentgen: status= "+status+"statusDesc= "
				+statusDesc);
		return sOutputXML;
	}
}