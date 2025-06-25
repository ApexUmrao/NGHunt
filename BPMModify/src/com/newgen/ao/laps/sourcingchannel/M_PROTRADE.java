package com.newgen.ao.laps.sourcingchannel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.ProtradeComplexMapping;
import com.newgen.ao.laps.cache.StageCallCache;
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

public class M_PROTRADE implements SourcingChannelHandler{
	private HashMap<String, String> attributeMap;
	private HashMap<String, String> attributeCLOBMap;
//	private HashMap<String, HashMap<String, String>> complexMap;
//	private HashMap<String, String> innerComplexAttribMap;
	private HashMap<String, String> defaultMap;
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap;
	private LinkedHashMap<Integer, HashMap<String, String>> innerComplexAttribMap;
	private HashMap<String, String> innerInnerComplexMap;
	private Map<String, String> protradeDateMap;
	private String decHist = "TFO_DJ_DEC_HIST";
	private String tableName = "";
	public LapsModifyRequest request;
	private String workItemNumber = "";
	private int wrkitmId = 0;
	private int processDefId;
	public LapsModifyResponse resp = new LapsModifyResponse();

	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationId,
			String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside M_PROTRADE");
			String journeyType = sourcingChannel;
			processDefId = LapsConfigurations.getInstance().processDefIdTFO;
			complexMap = new HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>>();
			List<String> channelList = LapsUtils.getInstance().getChannelNameList();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ChannelList: "+channelList);
//			SingleUserConnection instance = SingleUserConnection.getInstance(1000);
//			try {
//				sessionId = instance.getSession(
//						LapsConfigurations.getInstance().CabinetName,
//						LapsConfigurations.getInstance().UserName,
//						LapsConfigurations.getInstance().Password);
//			} catch (Exception e) {
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
//			}
			StringBuilder strDecHistColumns = null;
			StringBuilder[] strDecHistValues = null;
			int start;
			int deadEnd;
			int noOfFields;
			int noOfFieldsInner;
			resp.setChannelRefNumber(channelRefNumber);
			resp.setCorrelationId(correlationId);
			String outputXml="";
			try {
				outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE "
						+ "FROM TFO_DJ_PROTRADE_TXN_DATA WHERE CHANNELREFNUMBER = '"+channelRefNumber
						+"' AND CORRELATIONID = '"+correlationId+"'");
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			XMLParser xp = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				String requestCategory = xp.getValueOf("REQUEST_CATEGORY");
				String requestType = xp.getValueOf("REQUEST_TYPE");
				Map<String,Map<String,HashMap<String, String>>> protradeMappingMap = ChannelCallCache.getInstance().getProtradeMappingMap();
				Map<String,HashMap<String, String>> attribMap = protradeMappingMap.get(requestCategory+"#"+requestType);
				HashMap<String, ProtradeComplexMapping> protradeComplexMap = ChannelCallCache.getInstance().getProtradeComplexMap();
				ProtradeComplexMapping cmplxMaster;
				String[] retMsg = BPMMandatoryCheck.isMandatoryCheck(LapsConfigurations.getInstance().CabinetName, 
						channelRefNumber, correlationId).split("###");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"retMsg: "+retMsg.toString());
				if(retMsg[0].equalsIgnoreCase("true")){
					defaultMap = StageCallCache.getReference().getCachedDefaultMap();
					protradeDateMap = ChannelCallCache.getInstance().getProtradeDateMap();
                    for(Map.Entry<String, HashMap<String, String>> mapEntry: attribMap.entrySet()){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mapEntry.getKey(): "+mapEntry.getKey());
//						String outputXmlCmplx = APCallCreateXML.APSelect("SELECT STAGING_TABLE_NAME"
//								+ " FROM TFO_DJ_PROTRADE_COMPLEX_MASTER WHERE STRUCTURE_TYPE = '"
//								+ mapEntry.getKey() + "'");
//						XMLParser xpCmplx = new XMLParser(outputXmlCmplx);
//						int mainCodeCmplx = Integer.parseInt(xpCmplx.getValueOf("MainCode"));
//						if(mainCodeCmplx == 0){
//							tableName = xpCmplx.getValueOf("STAGING_TABLE_NAME");
//						}
						cmplxMaster = protradeComplexMap.get(mapEntry.getKey());
						tableName = cmplxMaster.getStagingTableName();
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"tableName: "+tableName);
						if(mapEntry.getKey().equals("EXTERNAL")){
							HashMap<String, String> attribList = mapEntry.getValue();
							StringBuilder attributeValues = new StringBuilder();
							for(Map.Entry<String, String> value : attribList.entrySet()){
								if(value.getKey().contains("_DATE") && protradeDateMap.containsKey(value.getKey())
										&& protradeDateMap.get(value.getKey()).equals("Y")){ 
//										&& !value.getKey().equalsIgnoreCase("INF_BASE_DOC_DATE")
//										&& !value.getKey().equalsIgnoreCase("INF_MATURITY_DATE")
//										&& !value.getKey().equalsIgnoreCase("LC_LTST_SHIPMNT_DATE")){
									attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY') AS "+value.getKey()).append(",");
								} else {
									attributeValues.append(value.getKey()).append(",");
								}
							}
							attributeValues.setLength(attributeValues.length()-1);							
							try {
								outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues 
										+" FROM " + tableName + " "
										+ "WHERE CHANNELREFNUMBER = '"+channelRefNumber+"' AND REQUESTMODE='"+mode
										+"' AND CORRELATIONID = '"+correlationId+"'");
							} catch (Exception e) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							}
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "+outputXml);
							xp = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
							if (mainCode == 0) {
								attributeMap = new HashMap<String, String>();
								attributeCLOBMap = new HashMap<String, String>();
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(value.getKey().contains(" AS ")){
										attributeMap.put(value.getValue(), xp.getValueOf(
												value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
														value.getKey().length())));
									} else {
										attributeMap.put(value.getValue(), xp.getValueOf(value.getKey()));
									}
									}
								attributeMap.put("PT_UTILITY_FLAG", "Y");
								if(requestCategory.equals("GRNT")){
									attributeMap.put("SOURCE_CHANNEL", "IPT");
								} else {
									attributeMap.put("SOURCE_CHANNEL", requestCategory+"_IPT");
								}
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap: "
										+attributeMap.toString());
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
							try {
								outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues+
										" FROM " + tableName + " WHERE CHANNELREFNUMBER = '"
										+channelRefNumber+"' AND CORRELATIONID = '"+correlationId+"'");
							} catch (Exception e) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
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
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Record :"+Record);
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
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"strDecHistValues: "
											+strDecHistValues[i].toString());								
								}
							}
						} else {
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
								if(noOfFields>0){
									complexMap.put(mapEntry.getKey(), innerComplexAttribMap);
								}
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"complexMap: "
										+complexMap.toString());
							}
						}
					}
					//String proTradeRefNo = attributeMap.get("PRO_TRADE_REF_NO");					
					String outputXmlWI = "";
					String wiNameInput = "";
					try {
						outputXmlWI = APCallCreateXML.APSelect("select WI_NAME from TFO_DJ_PROTRADE_TXN_DATA "
								+ "WHERE CHANNELREFNUMBER = '"+channelRefNumber+"' AND REQUESTMODE='"+mode
								+ "' AND CORRELATIONID = '"+correlationId+"'");
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXmlWI: " + outputXmlWI);
					XMLParser xpWI = new XMLParser(outputXmlWI);
					int mainCodeWI = Integer.parseInt(xpWI.getValueOf("MainCode"));
					if (mainCodeWI == 0) {
						wiNameInput = xpWI.getValueOf("WI_NAME");
					}
					String outputXMLRef = "";
					try {
						outputXMLRef = APCallCreateXML.APSelect("select WI_NAME FROM EXT_TFO "
								+ "WHERE PRO_TRADE_REF_NO='"+channelRefNumber+"'");
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXMLRef: " + outputXMLRef);
					XMLParser xpRef = new XMLParser(outputXMLRef);
					start = xpRef.getStartIndex("Records", 0, 0);
					deadEnd = xpRef.getEndIndex("Records", start, 0);
					noOfFields = xpRef.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; ++i) {
						start = xpRef.getStartIndex("Record", end, 0);
						end = xpRef.getEndIndex("Record", start, 0);
						workItemNumber = xpRef.getValueOf("WI_NAME", start, end);
						if(!workItemNumber.isEmpty() && workItemNumber.equalsIgnoreCase(wiNameInput)) {
							break;
						}
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no in ext_tfo is " + workItemNumber);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Wi no in input is: " + wiNameInput);
					if(!workItemNumber.isEmpty() && workItemNumber.equalsIgnoreCase(wiNameInput)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"workitem nos matched");
						attributeMap.put("WI_NAME", wiNameInput);
						if(attributeMap.containsKey("INSTRUCTION1")) {
							if(attributeMap.get("INSTRUCTION1") != null && 
									attributeMap.get("INSTRUCTION1").equalsIgnoreCase("true")){
								attributeMap.put("CUST_INST", defaultMap.get("PT_CUST_INST_TRUE"));
							} else {
								attributeMap.put("CUST_INST", defaultMap.get("PT_CUST_INST_FALSE"));
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
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "complexMap final: " 
								+ complexMap.toString());
						String activityName = "";
						String isCRFlag = "";
						String isRMFlag = "";
						int mainCodeAssgn = -1;
						try {
							String outputXML = APCallCreateXML.APSelect("select CURR_WS, IS_CR_PPM, IS_RM_PPM "
									+ "FROM EXT_TFO "
									+ "WHERE WI_NAME='"+wiNameInput+"'");
							XMLParser xpAct = new XMLParser(outputXML);
							int mainCodeAct = Integer.parseInt(xpAct.getValueOf("MainCode"));
							if (mainCodeAct == 0) {
								activityName = xpAct.getValueOf("CURR_WS");
								isCRFlag = xpAct.getValueOf("IS_CR_PPM");
								isRMFlag = xpAct.getValueOf("IS_RM_PPM");
							}
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"activityName: "+activityName);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"isCRFlag: "+isCRFlag);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"isRMFlag: "+isRMFlag);
//							if(activityName.contains("Distribute1") && isCRFlag.equalsIgnoreCase("Y")){
//								if(isRMFlag.equalsIgnoreCase("Y")){
//									wrkitmId = 3;
//								} else {
//									wrkitmId = 2;
//								}
							if(isCRFlag.equalsIgnoreCase("Y")){
								outputXML = APCallCreateXML.APSelect("select WORKITEMID from WFINSTRUMENTTABLE "
										+ "where PROCESSINSTANCEID = '"+wiNameInput+"' and INTRODUCEDAT = 'Distribute1'"
												+ " and ACTIVITYNAME = 'CUSTOMER_REVIEW'");
								XMLParser xp3 = new XMLParser(outputXML);
								if (Integer.parseInt(xp3.getValueOf("MainCode")) == 0) {
									wrkitmId = Integer.parseInt(xp3.getValueOf("WORKITEMID"));
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId from WFINSTRUMENTTABLE: " + wrkitmId);
								}
							} 
//							else if(!activityName.contains("Distribute1")){
//								wrkitmId = 1;
//							}
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, 
									"exception in M_PROTRADE WMCompleteWorkitem:");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId: " + wrkitmId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "before WFSetAttributes ");
						String outputXmlAssgn="";
						try {
							if("Y".equalsIgnoreCase(isCRFlag) && wrkitmId != 0){
								outputXmlAssgn = ProdCreateXML.WFSetAttributes(sessionId, wiNameInput, wrkitmId, 
										attributeMap,complexMap,processDefId);
								XMLParser xp1 = new XMLParser(outputXmlAssgn);
								mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Assign Attributes Main Code: "
										+mainCodeAssgn);
							}
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in modify: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						if(mainCodeAssgn == 0){
							if(strDecHistValues!=null){
								strDecHistColumns.append("WI_NAME");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inert into strDecHistColumns: "
										+strDecHistColumns.toString());
								try {
									for(int y=0; y<strDecHistValues.length; y++){
										strDecHistValues[y].append("'").append(wiNameInput).append("'");
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert into strDecHistValues: "
												+strDecHistValues[y].toString());
										APCallCreateXML.APInsert(decHist, strDecHistColumns.toString(), 
												strDecHistValues[y].toString(), sessionId);
									}
								} catch (Exception e) {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
								}
							}
							/*attributeMap.clear();
							attributeMap.put("channelRefNumber", channelRefNumber);
							attributeMap.put("correlationId", correlationId);
							attributeMap.put("requestCategory", requestCategory);
							attributeMap.put("requestType", requestType);
							attributeMap.put("mode", mode);
							HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
									(10, sourcingChannel, "1.0", journeyType,2);
							int eventID = (Integer) eventMap.keySet().toArray()[0];
							List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
							if(callArray != null) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNameInput + ":" + callArray.toString());
								boolean nogoCall = false;
								for (CallEntity callEntity : callArray) {
									if(callEntity.isMandatory()){
										String outputXML = CallHandler.getInstance().executeCall
												(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
														wiNameInput, false);
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
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
							}*/
							if((wrkitmId == 2 || wrkitmId == 3) && "Y".equalsIgnoreCase(isCRFlag)){
								APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA", "INSERTIONDATETIME, WI_NAME, PROCESS_NAME, "
										+ "SOURCING_CHANNEL, REQUESTMODE, EXPIRY_DATE, STATUS_FLAG", 
										"SYSDATE,'"+workItemNumber+"','TFO','PROTRADE','DM',"
										+ "SYSDATE + "+defaultMap.get("PT_DELAY_TIME")+",'N'", sessionId);
								APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'DM_ToDoList'", "WI_NAME = '"
										+workItemNumber+"'", sessionId);
								ProdCreateXML.WMCompleteWorkItem(sessionId, wiNameInput, wrkitmId);
							}
							resp.setStatusCode("0");
							resp.setStatusDescription("Workitem Modified Successfully");
							resp.setWiNumber(wiNameInput);
						} else {
							resp.setStatusCode(String.valueOf(mainCodeAssgn));
							resp.setStatusDescription("Workitem Not Modified");
							resp.setWiNumber(wiNameInput);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Assign Attributes Failed ");
						}
						try {
							ProdCreateXML.WMUnlockWorkItem(sessionId, wiNameInput, wrkitmId);
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in M_PROTRADE WMUnlockWorkItem:");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
					} else {
						resp.setStatusCode("-1");
						resp.setStatusDescription("Workitem not found.");
					}
				} else {
					try {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WI not modified: ");
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
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in send email: ");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					resp.setStatusCode("-1");
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"dispatchEvent response: "+resp.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in M_PROTRADE event:");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}

}
