package com.newgen.ao.laps.sourcingchannel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.ProtradeComplexMapping;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.cache.TSLMStagingCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.implementation.BPMMandatoryCheck;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class C_PROTRADE implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap;
	private HashMap<String, String> attributeCLOBMap;
	private HashMap<String, String> defaultMap;
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap;
	private LinkedHashMap<Integer, HashMap<String, String>> innerComplexAttribMap;
	private HashMap<String, String> innerInnerComplexMap;
	private Map<String, String> protradeDateMap;
	private Map<String,Map<String,String>> protradeDefaultMap;
	HashMap<String,String> protradeCLOBMap;
	private String tableName = "";
	private String decHist = "TFO_DJ_DEC_HIST";
	public LapsModifyRequest request;
	private String requestCategory;
	private String requestType;
	private int processDefId;
	private String requestedBy;
	private String sourceChannel;
	private String processType;
	private static HashMap<String,ArrayList<String>> ProtradeReferralMaster ;  //ATP-379 06-02-2024 REYAZ
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationId, String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_PROTRADE");
			String journeyType = sourcingChannel;
			processDefId = LapsConfigurations.getInstance().processDefIdTFO;
			ProtradeReferralMaster = TSLMStagingCache.getInstance().getTSLMReferralMaster();  //ATP-379 06-02-2024 REYAZ
			complexMap = new HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>>();
			//			SingleUserConnection instance = SingleUserConnection.getInstance(1000);
			//			try {
			//				sessionId = instance.getSession(
			//						LapsConfigurations.getInstance().CabinetName,
			//						LapsConfigurations.getInstance().UserName,
			//						LapsConfigurations.getInstance().Password);
			//			} catch (Exception e) {
			//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in fetching session ID: ");
			//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			//			}
			String winame = "";
			StringBuilder strDecHistColumns = null;
			StringBuilder[] strDecHistValues = null;
			StringBuilder strRefferalColumns = null;  // ATP-379  29-01-2024 REYAZ
			StringBuilder[] strRefferalValues = null;  // ATP-379  29-01-2024 REYAZ
			int start;
			int deadEnd;
			int noOfFields;
			int noOfFieldsInner;
			int mainCode = -1;
			int totalRetrievedCount =0;
			resp.setChannelRefNumber(channelRefNumber);
			resp.setCorrelationId(correlationId);
			String outputXml = APCallCreateXML.APSelect("select count(1) as WI_COUNT from EXT_TFO where "
					+ "PRO_TRADE_REF_NO = '" +channelRefNumber +"'");
			XMLParser xp = new XMLParser(outputXml);
			if(Integer.parseInt(xp.getValueOf("MainCode")) == 0 && Integer.parseInt(xp.getValueOf("WI_COUNT")) == 0){
				outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE, CUSTOMER_ID, PRO_TRADE_REF_NO "
						+ "FROM TFO_DJ_PROTRADE_TXN_DATA WHERE CHANNELREFNUMBER = '"+channelRefNumber
						+ "' AND CORRELATIONID = '"+correlationId+"'");
				xp = new XMLParser(outputXml);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				defaultMap = StageCallCache.getReference().getCachedDefaultMap();
				if (mainCode == 0) {
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					requestType = xp.getValueOf("REQUEST_TYPE");
					String custID = xp.getValueOf("CUSTOMER_ID");
					String protradeRefNo = xp.getValueOf("PRO_TRADE_REF_NO");
					if(protradeRefNo.isEmpty()){
						//					 int sleepLoopCount = Integer.parseInt(defaultMap.get("PT_SLEEP_COUNT"));
						//					   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sleepLoopCount :"+sleepLoopCount);
						//					   for(int j=0;j<sleepLoopCount;j++){
						//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Sleeping for "+defaultMap.get("PT_SLEEP_TIME"));
						Thread.sleep(Long.parseLong(defaultMap.get("PT_SLEEP_TIME")));
						outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE, CUSTOMER_ID, PRO_TRADE_REF_NO "
								+ "FROM TFO_DJ_PROTRADE_TXN_DATA WHERE CHANNELREFNUMBER = '"+channelRefNumber
								+ "' AND CORRELATIONID = '"+correlationId+"'");
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						//							totalRetrievedCount = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
						//							if(totalRetrievedCount>0){
						//	                             break;
						//							 }
						//					   }
					}
					if (mainCode == 0) {
						requestCategory = xp.getValueOf("REQUEST_CATEGORY");
						requestType = xp.getValueOf("REQUEST_TYPE");
						custID = xp.getValueOf("CUSTOMER_ID");
						protradeRefNo = xp.getValueOf("PRO_TRADE_REF_NO");
					}
					String[] retMsg = BPMMandatoryCheck.isMandatoryCheck(LapsConfigurations.getInstance().CabinetName, 
							channelRefNumber, correlationId).split("###");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"retMsg: "+retMsg.toString());
					if(retMsg[0].equalsIgnoreCase("true")){
						Map<String,Map<String,HashMap<String, String>>> protradeMappingMap = ChannelCallCache.getInstance().getProtradeMappingMap();
						Map<String,HashMap<String, String>> attribMap = protradeMappingMap.get(requestCategory+"#"+requestType);
						HashMap<String, ProtradeComplexMapping> protradeComplexMap = ChannelCallCache.getInstance().getProtradeComplexMap();
						//Map<String,String> requestTypeMap = new HashMap<String,String>();

						ProtradeComplexMapping cmplxMaster;
						protradeDateMap = ChannelCallCache.getInstance().getProtradeDateMap();
						protradeDefaultMap=ChannelCallCache.getInstance().getProtradeDefaultMaster();
						protradeCLOBMap=ChannelCallCache.getInstance().getProtradeCLOBMaster();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"defaultMap: "+defaultMap);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"protradeCLOBMap: "+protradeCLOBMap);

						for(Map.Entry<String, HashMap<String, String>> mapEntry: attribMap.entrySet()){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"protrade mapEntry: "+mapEntry.toString());
							//							String outputXmlCmplx = APCallCreateXML.APSelect("SELECT STAGING_TABLE_NAME"
							//									+ " FROM TFO_DJ_PROTRADE_COMPLEX_MASTER WHERE STRUCTURE_TYPE = '"
							//									+ mapEntry.getKey() + "'");
							//							XMLParser xpCmplx = new XMLParser(outputXmlCmplx);
							//							int mainCodeCmplx = Integer.parseInt(xpCmplx.getValueOf("MainCode"));
							//							if(mainCodeCmplx == 0){
							//								tableName = xpCmplx.getValueOf("STAGING_TABLE_NAME");
							//							}
							cmplxMaster = protradeComplexMap.get(mapEntry.getKey());
							tableName = cmplxMaster.getStagingTableName();
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"tableName: "+tableName);
							if(mapEntry.getKey().equals("EXTERNAL")){
								HashMap<String, String> attribList = mapEntry.getValue();
								StringBuilder attributeValues = new StringBuilder();
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(value.getKey().contains("_DATE") && protradeDateMap.containsKey(value.getKey())
											&& protradeDateMap.get(value.getKey()).equals("Y")){
										//											&& !value.getKey().equalsIgnoreCase("INF_BASE_DOC_DATE")
										//											&& !value.getKey().equalsIgnoreCase("INF_MATURITY_DATE")
										//											&& !value.getKey().equalsIgnoreCase("LC_LTST_SHIPMNT_DATE")){
										attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY') AS "+value.getKey()).append(",");
									} else {
										attributeValues.append(value.getKey()).append(",");
									}
								}
								attributeValues.setLength(attributeValues.length()-1);							
								outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues
										+" FROM " + tableName + " WHERE CHANNELREFNUMBER = '"
										+channelRefNumber+"' AND CORRELATIONID = '"+correlationId+"'");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "+outputXml);
								xp = new XMLParser(outputXml);
								mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
								if (mainCode == 0) {
									attributeMap = new HashMap<String, String>();
									attributeCLOBMap = new HashMap<String, String>();
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," protradeCLOBMap: "+protradeCLOBMap);
									for(Map.Entry<String, String> value : attribList.entrySet()){
										if(protradeCLOBMap.containsKey(value.getValue()) && protradeCLOBMap.get(value.getValue()).equals("Y")){
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
												attributeMap.put(value.getValue(), xp.getValueOf(value.getKey()));
											}
										}
									}
									//attributeMap.put("PT_UTILITY_FLAG", defaultMap.get("PT_UTILITY_FLAG"));
									attributeMap.put("REQ_DATE_TIME", getDate());

									/*if(!requestType.equalsIgnoreCase("ELCB_DISC")){
										attributeMap.put("INITIATOR_BRANCH", defaultMap.get("PT_INITIATOR_BRANCH"));
										attributeMap.put("INITIATOR_USERID", defaultMap.get("PT_INITIATOR_USERID"));
										attributeMap.put("IS_ACC_VALID", defaultMap.get("PT_IS_ACC_VALID"));
										if(requestCategory.equals("GRNT")){
											attributeMap.put("SOURCE_CHANNEL", defaultMap.get("PT_SOURCE_CHANNEL"));
										} else {
											attributeMap.put("SOURCE_CHANNEL", requestCategory+"_"
													+defaultMap.get("PT_SOURCE_CHANNEL"));
										}
										if(requestCategory.equals("GRNT")){
											attributeMap.put("REQUESTED_BY", "A");
										} else {
											attributeMap.put("REQUESTED_BY", requestCategory+"_CT");
										}
									}*/
									processType=attributeMap.get("PROCESS_TYPE");
									String key=processType+"#"+requestCategory+"#"+requestType;

//									if(protradeDefaultMap.containsKey(key)){
										for(Map.Entry<String, String> map : protradeDefaultMap.get(key).entrySet()){
											LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,map.getKey());
											attributeMap.put(map.getKey(),map.getValue());
//										}
									}
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap: "
											+attributeMap.toString());
									sourceChannel=attributeMap.get("SOURCE_CHANNEL");
									requestedBy=attributeMap.get("REQUESTED_BY");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap: "
											+attributeMap.toString());
									//send email flag
									if(chkAckBtVisible()){
										attributeMap.put("CHKBX_SEND_MAIL","true");
									}
									if(attributeMap.containsKey("CUSTOMER_ID")){
										journeyType = "ISSUANCE";
									}
								}
							} else if(mapEntry.getKey().equals("DEC_HIST")){
								HashMap<String, String> attribList = mapEntry.getValue();
								StringBuilder attributeValues = new StringBuilder();
								strDecHistColumns = new StringBuilder();
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(!value.getKey().equalsIgnoreCase("WI_NAME")){
										if(value.getKey().contains("_DATE") && protradeDateMap.containsKey(value.getKey())
												&& protradeDateMap.get(value.getKey()).equals("Y")){
											attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY HH24:MI:SS') AS "+value.getKey()).append(",");
										} else {
											attributeValues.append(value.getKey()).append(",");
										}
										strDecHistColumns.append(value.getValue()).append(",");
									}
								}
								attributeValues.setLength(attributeValues.length()-1);	
								strDecHistColumns.setLength(strDecHistColumns.length());	
								outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
										" FROM " + tableName + " WHERE CHANNELREFNUMBER = '"
										+channelRefNumber+"' AND CORRELATIONID = '"+correlationId+"'");
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
							} 
							// ATP-379  29-01-2024 REYAZ
							// START CODE
							else if(mapEntry.getKey().equals("PROTRADE_REFERRAL_DETAILS")){
								HashMap<String, String> attribList = mapEntry.getValue();
								StringBuilder attributeValues = new StringBuilder();
								strRefferalColumns = new StringBuilder();
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(!value.getKey().equalsIgnoreCase("WI_NAME")){
										if(value.getKey().contains("_DATE") && protradeDateMap.containsKey(value.getKey())
												&& protradeDateMap.get(value.getKey()).equals("Y")){
											attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY HH24:MI:SS') AS "+value.getKey()).append(",");
										} else {
											attributeValues.append(value.getKey()).append(",");
										}
										strRefferalColumns.append(value.getValue()).append(",");
									}
								}
								attributeValues.setLength(attributeValues.length()-1);	
								strRefferalColumns.setLength(strRefferalColumns.length());
								outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
										" FROM " + tableName + " WHERE CHANNELREFNUMBER = '"+channelRefNumber+"'"
										+ " AND CORRELATIONID = '"+correlationId+"'");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "
										+outputXml);
								xp = new XMLParser(outputXml);
								mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
								if (mainCode == 0) {
									start = xp.getStartIndex("Records", 0, 0);
									deadEnd = xp.getEndIndex("Records", start, 0);
									noOfFields = xp.getNoOfFields("Record", start, deadEnd);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in Protrade referral: "
											+noOfFields);

									strRefferalValues = new StringBuilder[noOfFields];
									for (int i = 0; i < noOfFields; ++i) {
										strRefferalValues[i] = new StringBuilder();
										String Record = xp.getNextValueOf("Record");
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"dec Record :"+Record);
										XMLParser xp2 = new XMLParser(Record); 
										String Flag_del ="";
										for (Map.Entry<String, String> value : attribList.entrySet()) {
											if (value.getKey().equalsIgnoreCase("STATUS")) {
												if ("A".equalsIgnoreCase(xp2.getValueOf(value.getKey()))) {
													Flag_del ="N";
													strRefferalValues[i].append("'").append("Active").append("',");
												} else if ("C".equalsIgnoreCase(xp2.getValueOf(value.getKey()))) {
													Flag_del ="Y";
													strRefferalValues[i].append("'").append("Closed").append("',");
												}
											}else if (value.getKey().equalsIgnoreCase("FLAG_DEL")) {
												strRefferalValues[i].append("'").append(Flag_del).append("',");
											} else if (!value.getKey().equalsIgnoreCase("WI_NAME")) {
												if (value.getKey().contains("_DATE")) {
													strRefferalValues[i]
															.append("TO_DATE('" + xp2.getValueOf(value.getKey()) + "',"
																	+ "'DD/MM/YYYY HH24:MI:SS'),");
							} else {
													strRefferalValues[i].append("'")
															.append(xp2.getValueOf(value.getKey())).append("',");
												}
											}
										}
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"strRefferalValues at "
												+i+": "+strRefferalValues[i].toString());								
									}
								}
							}
							// ATP-379  29-01-2024 REYAZ
							// END Code
							else {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"map for: "+mapEntry.getKey());
								HashMap<String, String> attribList = mapEntry.getValue();
								StringBuilder attributeValues = new StringBuilder();
								for(Map.Entry<String, String> value : attribList.entrySet()){
									attributeValues.append(value.getKey()).append(",");
								}
								attributeValues.setLength(attributeValues.length()-1);	
								outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
										" FROM " + tableName + " WHERE CHANNELREFNUMBER = '"
										+channelRefNumber+"' AND CORRELATIONID = '"+correlationId+"'");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "
										+outputXml);
								xp = new XMLParser(outputXml);
								mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
								if (mainCode == 0) {
									innerComplexAttribMap = new LinkedHashMap<Integer, HashMap<String, String>>();
									start = xp.getStartIndex("Records", 0, 0);
									deadEnd = xp.getEndIndex("Records", start, 0);
									noOfFields = xp.getNoOfFields("Record", start, deadEnd);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in party: "
											+noOfFields);
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
										innerComplexAttribMap.put(i, innerInnerComplexMap);
									}
									complexMap.put(mapEntry.getKey(), innerComplexAttribMap);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"complexMap: "
											+complexMap.toString());
								}
							}
						}
						if(attributeMap.containsKey("INSTRUCTION1")) {
							if(attributeMap.get("INSTRUCTION1") != null && 
									attributeMap.get("INSTRUCTION1").equalsIgnoreCase("true")){
								attributeMap.put("CUST_INSTR", defaultMap.get("PT_CUST_INST_TRUE"));
							} else {
								attributeMap.put("CUST_INSTR", defaultMap.get("PT_CUST_INST_FALSE"));
							}
						}
						if(attributeMap.containsKey("INSTRUCTION2")) {
							if(attributeMap.get("INSTRUCTION2") != null && 
									attributeMap.get("INSTRUCTION2").equalsIgnoreCase("true")){
								attributeMap.put("DISCOUNT_ON_ACCEP", defaultMap.get("PT_CUST_INST_2_TRUE"));
							} else {
								attributeMap.put("DISCOUNT_ON_ACCEP", defaultMap.get("PT_CUST_INST_2_FALSE"));
							}
						}
						if(!attributeMap.containsKey("PRO_TRADE_REF_NO")){
							attributeMap.put("PRO_TRADE_REF_NO", channelRefNumber);
						}
						outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "N", attributeMap,"",processDefId,"Initiator");
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if (mainCode == 0) {
							resp.setStatusCode("0");
							resp.setStatusDescription("Workitem Created Successfully");
							String workItemNumber = xp.getValueOf("ProcessInstanceId");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is " + workItemNumber);
							resp.setWiNumber(workItemNumber);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexMap final: " 
									+ complexMap.toString());
							attributeMap.clear();
							//attributeMap.put("WI_NAME", workItemNumber);
							attributeCLOBMap.put("WI_NAME", workItemNumber);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attributeCLOBMap final: " 
									+ attributeCLOBMap.toString());
							try {
								//String outputXmlAssgn = ProdCreateXML.WFSetAttributes(sessionId, workItemNumber, 1, 
								//			attributeMap,complexMap,processDefId);
								String outputXmlAssgn = ProdCreateXML.WFSetAttributes(sessionId, workItemNumber, 1, 
										attributeCLOBMap,complexMap,processDefId);
								XMLParser xp1 = new XMLParser(outputXmlAssgn);
								int mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Assign Attributes Main Code: "
										+mainCodeAssgn);
							} catch (Exception e) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in assign attributes: ");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							}
							// ATP-379 07-02-2024 REYAZ
							// START CODE
							if(strRefferalValues!=null){
								strRefferalColumns.append("WI_NAME").append(",");
								strRefferalColumns.append("INSERTIONORDERID");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"strRefferalValues.length: "
										+strRefferalValues.length);
								for(int y=0; y<strRefferalValues.length; y++){
									strRefferalValues[y].append("'").append(workItemNumber).append("'").append(",");
									ArrayList<String> values = new ArrayList<>();
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strRefferalValues: "
											+strRefferalValues[y].toString());
									String [] reffralValue =strRefferalValues[y].toString().split(",");
									String referralType =reffralValue[6].replaceAll("'", "");
									String tabName ="";
									values = ProtradeReferralMaster.get(referralType+"#"+sourcingChannel);   //ATP-379 15-FEB-24 --JAMSHED
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," referralType : " + referralType);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Value : " + values);
									tabName = values.get(0);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," Tab Name Value : " + tabName);
									String insertionorderID=LapsUtils.returnInsertionOrderID(tabName);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," insertionorderID: " +insertionorderID);
									strRefferalValues[y].append("'").append(insertionorderID).append("'");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strRefferalColumns: "
											+strRefferalColumns.toString());
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strRefferalValues: "
											+strRefferalValues[y].toString());
									APCallCreateXML.APInsert(tabName, strRefferalColumns.toString(), strRefferalValues[y].toString()
											,sessionId);
								}
							}
							// END CODE 
							// ATP-379 07-02-2024 REYAZ
							if(strDecHistValues!=null){
								strDecHistColumns.append("WI_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"strDecHistValues.length: "
										+strDecHistValues.length);
								for(int y=0; y<strDecHistValues.length; y++){
									strDecHistValues[y].append("'").append(workItemNumber).append("'");
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strDecHistColumns: "
											+strDecHistColumns.toString());
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strDecHistValues: "
											+strDecHistValues[y].toString());
									APCallCreateXML.APInsert(decHist, strDecHistColumns.toString(), strDecHistValues[y].toString()
											,sessionId);
								}
							}
							try {
								APCallCreateXML.APInsert(decHist, " WI_NAME,USERNAME,CREATE_DATE,USER_ID,ACTION,REMARKS", 
										"'"+workItemNumber+"','"+defaultMap.get("PT_INITIATOR_USERID")+"',SYSDATE,'"+
												defaultMap.get("PT_INITIATOR_USERID")+"','Protrade Workitem Created','Initiated by "+
												defaultMap.get("PT_INITIATOR_USERID")+"'",sessionId);
							} catch (Exception e) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into dec hist: ");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							}
							APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA", "INSERTIONDATETIME, WI_NAME, PROCESS_NAME, "
									+ "SOURCING_CHANNEL, REQUESTMODE, EXPIRY_DATE, STATUS_FLAG", 
									"SYSDATE,'"+workItemNumber+"','TFO','PROTRADE','DC',"
											+ "SYSDATE + "+defaultMap.get("PT_DELAY_TIME")+",'N'", sessionId);
							APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'DC_ToDoList'", "WI_NAME = '"+workItemNumber+"'", sessionId);
							ProdCreateXML.WMCompleteWorkItem(sessionId, workItemNumber, 1);
							///new change 21march
							/*if(!journeyType.equalsIgnoreCase(sourcingChannel)){
								ProdCreateXML.WMCompleteWorkItem(sessionId, workItemNumber, 1);
							}*/ //new
							//ProdCreateXML.WMCompleteWorkItem(sessionId, workItemNumber, 1);
						}
					} else {
						try {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WI not created: ");
							attributeMap = new HashMap<String, String>();
							attributeMap.put("channelRefNumber", channelRefNumber);
							attributeMap.put("correlationId", correlationId);
							attributeMap.put("requestCategory", requestCategory);
							attributeMap.put("requestType", requestType);
							if(retMsg.length>=2){
								attributeMap.put("returnMessage", retMsg[1]);
								resp.setStatusDescription("Mandatory Data Missing: "+retMsg[1]);
							} else {
								attributeMap.put("returnMessage", "");
							}
							HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
									(10, sourcingChannel, "1.0", sourcingChannel,-1);
							int eventID = (Integer) eventMap.keySet().toArray()[0];
							List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
							if(callArray != null) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: "+callArray.toString());
								boolean nogoCall = false;
								for (CallEntity callEntity : callArray) {
									if(callEntity.isMandatory()){
										String outputXML = CallHandler.getInstance().executeCall
												(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
														"", false);
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call outputxml: "
												+ outputXML);
										if(!"".equals(outputXML)){
											xp = new XMLParser(outputXML);
											mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
											if(mainCode != 0){
												nogoCall = true;
											}
										}
									}
								}
							}
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in calling sendemail: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						resp.setStatusCode("-1");
					}
				}
			} else {
				String outputXml1 = APCallCreateXML.APSelect("select wi_name from ext_tfo where pro_trade_ref_no ="
						+"'" +channelRefNumber +"'");
				XMLParser xp1 = new XMLParser(outputXml1);
				if(Integer.parseInt(xp1.getValueOf("MainCode")) == 0){//santhosh changes
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is ==== " + xp1.getValueOf("wi_name"));
					resp.setWiNumber(xp1.getValueOf("wi_name"));
				}
				resp.setStatusCode("-1");
				resp.setStatusDescription("Duplicate Request (Workitem with same ProTrade Ref Number already exists)");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_PROTRADE dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}


	boolean chkAckBtVisible() {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside chkAckBtVisible sourceKey: ");

		Map<String, String> requestTypeMap = ChannelCallCache.getInstance().getRequestType();
		Map<String, String> requestByMap = ChannelCallCache.getInstance().getRequestBy();
		Map<String, String> sourceChannelMap = ChannelCallCache.getInstance().getSourceChannel();


		if ("GRNT".equalsIgnoreCase(requestCategory)) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " if GRNT ...: ");
			if (("A".equalsIgnoreCase(requestedBy)) || ("B".equalsIgnoreCase(requestedBy))
					&& (flagCheck(getMasterValue(sourceChannelMap,"sourceChannelMap"))) 
					&& flagCheck(getMasterValue(requestTypeMap,"requestTypeMap"))) {
				return true;
			}
		} else if ("IFS".equalsIgnoreCase(requestCategory)) {
			if (flagCheck(getMasterValue(sourceChannelMap,"sourceChannelMap"))
					&& flagCheck(getMasterValue(requestTypeMap,"requestTypeMap"))
					&& flagCheck(getMasterValue(requestByMap,"requestByMap"))) {
				return true;
			}
		} else if ("IFP".equalsIgnoreCase(requestCategory)) {
			if (flagCheck(getMasterValue(sourceChannelMap,"sourceChannelMap"))
					&& flagCheck(getMasterValue(requestTypeMap,"requestByMap"))
					&& flagCheck(getMasterValue(requestByMap,"requestByMap"))) {
				return true;
			}
		} else if ((("EC".equalsIgnoreCase(requestCategory)
				|| "IC".equalsIgnoreCase(requestCategory)
				|| "TL".equalsIgnoreCase(requestCategory)
				|| "ILCB".equalsIgnoreCase(requestCategory)
				|| "ELCB".equalsIgnoreCase(requestCategory)
				|| "ILC".equalsIgnoreCase(requestCategory)
				|| "ELC".equalsIgnoreCase(requestCategory) 
				|| "DBA".equalsIgnoreCase(requestCategory))) 
				&& (flagCheck(getMasterValue(sourceChannelMap,"sourceChannelMap"))
						&& flagCheck(getMasterValue(requestTypeMap,"requestTypeMap"))
						&& flagCheck(getMasterValue(requestByMap,"requestByMap")))) {
			return true;
		} else if ("SG".equalsIgnoreCase(requestCategory)){
			if ("SG_CT".equalsIgnoreCase(requestedBy)
					&& (flagCheck(getMasterValue(sourceChannelMap,"sourceChannelMap")) && flagCheck(getMasterValue(requestTypeMap,"requestTypeMap")))) {
				return true;
			}
		}
		return false;
	}

	public String getMasterValue(Map<String,String> map,String mapType) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside cinside getMasterValue >>>: ");
		String tempStr = "";
		try {
			String key ="";

			if("requestTypeMap".equalsIgnoreCase(mapType)){
				key = processType+"#"+requestCategory+"#"+requestType;
			}else if("sourceChannelMap".equalsIgnoreCase(mapType)){
				key = processType+"#"+requestCategory+"#"+sourceChannel;
			}else if("requestByMap".equalsIgnoreCase(mapType)){
				key = processType+"#"+requestCategory+"#"+requestedBy	;
			}
			tempStr=map.get(key).toString();

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "get Master List tempStr:: "+tempStr);
		return tempStr;
	}

	private boolean flagCheck(String val) {
		if ("Y".equalsIgnoreCase(val) || "Yes".equalsIgnoreCase(val)) {
			return true;
		} else {
			return false;
		}
	}


	private String getDate(){
		String today = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		today = formatter.format(date);
		return today;	
	}
}
