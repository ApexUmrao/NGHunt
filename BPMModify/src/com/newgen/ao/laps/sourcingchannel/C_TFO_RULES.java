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
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_TFO_RULES implements SourcingChannelHandler{

	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap;
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap;
	private LinkedHashMap<Integer, HashMap<String, String>> innerComplexAttribMap;
	private HashMap<String, String> innerInnerComplexMap;
	private HashMap<String, String> defaultMap;
	private Map<String, ArrayList<String>> tfoBRMSMap;
	private String requestCategory;
	private String requestType;
	private String sourceChannel;
	private String subBy;
	private int processDefId;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationId, String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_TFO_RULES wiNumber: "+wiNumber);
			processDefId = LapsConfigurations.getInstance().processDefIdTFO;
			tfoBRMSMap = ChannelCallCache.getInstance().getBRMSMap();
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
			resp.setChannelRefNumber(channelRefNumber);
			resp.setCorrelationId(correlationId);
			int mainCode = -1;
			if(wiNumber != null && !wiNumber.isEmpty()){
				String outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE, TARGET_WORKSTEP, "
						+ "TRANSACTION_REFERENCE, BILL_LN_REFRNCE, INF_MATURITY_DATE, CURR_WS, INITIATOR_USERID, "
						+ "BILL_TYPE, IF_SIGHT_BILL, DISCREPANCY_INSTRUCTION, SETTLEMENT_INSTRUCTION, BRANCH_CITY "
						+ "FROM EXT_TFO WHERE WI_NAME = '"+ wiNumber +"'");
				XMLParser xp = new XMLParser(outputXml);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0) {
					String trnsRefNo = xp.getValueOf("TRANSACTION_REFERENCE");
					String billRefNo = xp.getValueOf("BILL_LN_REFRNCE");
					String targetWS = xp.getValueOf("TARGET_WORKSTEP");
					String maturityDate = xp.getValueOf("INF_MATURITY_DATE");
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					requestType = xp.getValueOf("REQUEST_TYPE");
					String issCntr = xp.getValueOf("BRANCH_CITY");
					String parentWS = xp.getValueOf("CURR_WS");
					String parentUser = xp.getValueOf("INITIATOR_USERID");
					String moveWorkitemNo = "";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "trnsRefNo: " + trnsRefNo);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "billRefNo: " + billRefNo);
					//BRMS
					String billType = xp.getValueOf("BILL_TYPE");
					String ifSightBill = xp.getValueOf("IF_SIGHT_BILL");
					String discInstn = xp.getValueOf("DISCREPANCY_INSTRUCTION");
					String stttlInstn = xp.getValueOf("SETTLEMENT_INSTRUCTION");
					String key = "PT#"+requestCategory+"#"+requestType+"#"+billType+"#"+ifSightBill+"#"+discInstn
							+"#"+stttlInstn+"#Greater";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"get BRMS value from key: "+key);
					ArrayList<String> list = tfoBRMSMap.get(key);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"BRMS value from key: "+list.toString());
					if(list.get(3) != null && !list.get(3).isEmpty()){
						requestCategory = list.get(3);
					}
					if(list.get(4) != null && !list.get(4).isEmpty()){
						requestType = list.get(4);
					}
					sourceChannel = list.get(5);
					subBy = list.get(6);
					//BRMS end
					if(trnsRefNo.isEmpty() && billRefNo.isEmpty()) {
						resp.setStatusCode("-1");
						resp.setStatusDescription("Mandatory Data Missing");
					} else {
						attributeMap = new HashMap<String, String>();
						if(!trnsRefNo.isEmpty()){
							if("TL".equalsIgnoreCase(requestCategory)){
								attributeMap.put("BILL_LN_REFRNCE", trnsRefNo);
								attributeMap.put("IFS_LOAN_GRP", billType);
//								outputXml = APCallCreateXML.APSelect("SELECT WI_NAME "
//										+ "FROM EXT_TFO WHERE BILL_LN_REFRNCE = '"+ trnsRefNo +"' "
//										+ "AND CURR_WS = 'ToDoList' AND "
//										+ "(TARGET_WORKSTEP is null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND "
//										+ "TARGET_WORKSTEP <> 'DM_PROTRADE') )");
							} else {
								attributeMap.put("TRANSACTION_REFERENCE", trnsRefNo);
//								outputXml = APCallCreateXML.APSelect("SELECT WI_NAME "
//										+ "FROM EXT_TFO WHERE TRANSACTION_REFERENCE = '"+ trnsRefNo +"' "
//												+ "AND CURR_WS = 'ToDoList' AND "
//										+ "(TARGET_WORKSTEP is null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND "
//										+ "TARGET_WORKSTEP <> 'DM_PROTRADE') )");
							}
							outputXml = APCallCreateXML.APSelect("SELECT WI_NAME "
									+ "FROM EXT_TFO WHERE (BILL_LN_REFRNCE = '"+ trnsRefNo +"' OR "
											+ "TRANSACTION_REFERENCE = '" + trnsRefNo + "') "
									+ "AND CURR_WS = 'ToDoList' AND "
									+ "(TARGET_WORKSTEP is null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND "
									+ "TARGET_WORKSTEP <> 'DM_PROTRADE'))");
						} else if(!billRefNo.isEmpty()){
							if("TL".equalsIgnoreCase(requestCategory)){
								attributeMap.put("BILL_LN_REFRNCE", billRefNo);
								attributeMap.put("IFS_LOAN_GRP", billType);
//								outputXml = APCallCreateXML.APSelect("SELECT WI_NAME "
//										+ "FROM EXT_TFO WHERE BILL_LN_REFRNCE = '"+ billRefNo +"' "
//												+ "AND CURR_WS = 'ToDoList' AND "
//										+ "(TARGET_WORKSTEP is null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND "
//										+ "TARGET_WORKSTEP <> 'DM_PROTRADE') )");
							} else {
								attributeMap.put("TRANSACTION_REFERENCE", billRefNo);
//								outputXml = APCallCreateXML.APSelect("SELECT WI_NAME "
//										+ "FROM EXT_TFO WHERE TRANSACTION_REFERENCE = '"+ billRefNo +"' "
//												+ "AND CURR_WS = 'ToDoList' AND "
//										+ "(TARGET_WORKSTEP is null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND "
//										+ "TARGET_WORKSTEP <> 'DM_PROTRADE') )");
							}
							outputXml = APCallCreateXML.APSelect("SELECT WI_NAME "
									+ "FROM EXT_TFO WHERE (BILL_LN_REFRNCE = '"+ billRefNo +"' OR "
											+ "TRANSACTION_REFERENCE = '" + billRefNo + "') "
											+ "AND CURR_WS = 'ToDoList' AND "
									+ "(TARGET_WORKSTEP is null or (TARGET_WORKSTEP <> 'DC_PROTRADE' AND "
									+ "TARGET_WORKSTEP <> 'DM_PROTRADE'))");
						}
						attributeMap.put("TARGET_WORKSTEP", targetWS);
						attributeMap.put("BILL_TYPE", billType);
						attributeMap.put("IF_SIGHT_BILL", ifSightBill);
						attributeMap.put("DISCREPANCY_INSTRUCTION", discInstn);
						attributeMap.put("SETTLEMENT_INSTRUCTION", stttlInstn);
						attributeMap.put("BRANCH_CITY", issCntr);
						attributeMap.put("ASSIGNED_CENTER", issCntr);

						try {
							APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "''", "WI_NAME = '"
									+wiNumber+"'", sessionId);
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in "
									+ "C_TFO_RULES updating target workstep: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if(mainCode == 0) {
							int start = xp.getStartIndex("Records", 0, 0);
							int deadEnd = xp.getEndIndex("Records", start, 0);
							int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
							int end = 0;
							for (int i = 0; i < noOfFields; ++i) {
								start = xp.getStartIndex("Record", end, 0);
								end = xp.getEndIndex("Record", start, 0);
								moveWorkitemNo = xp.getValueOf("WI_NAME", start, end);
								if(!moveWorkitemNo.isEmpty()) {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "moving to exit: " 
											+ moveWorkitemNo);
									try {
										APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", 
												"'A'", "WI_NAME = '"+moveWorkitemNo+"'", sessionId);
										ProdCreateXML.WMCompleteWorkItem(sessionId, moveWorkitemNo, 1);
									} catch (Exception e) {
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in "
												+ "C_TFO_RULES moving to exit: ");
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
									}
								}
							}
						}
						defaultMap = StageCallCache.getReference().getCachedDefaultMap();
						assignAttributeMap();
						outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "Y", attributeMap,"",processDefId,"Initiator");
						xp = new XMLParser(outputXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						if (mainCode == 0) {
							resp.setStatusCode("0");
							resp.setStatusDescription("Workitem Created Successfully");
							String workItemNumber = xp.getValueOf("ProcessInstanceId");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Wi no is " + workItemNumber);
							resp.setWiNumber(workItemNumber);
							try {
								String parentPersonalName = "";
								outputXml = APCallCreateXML.APSelect("SELECT PERSONALNAME FROM PDBUSER WHERE UPPER(USERNAME)='"
										+ parentUser.toUpperCase() + "'");
								xp = new XMLParser(outputXml);
								mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
								if(mainCode == 0){
									parentPersonalName = xp.getValueOf("PERSONALNAME");
								}
								APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", " WI_NAME,CURR_WS_NAME,USERNAME,"
										+ "CREATE_DATE,USER_ID,ACTION,REMARKS","'"+wiNumber+"','"+parentWS+"','"
										+ parentPersonalName.toUpperCase()+"',SYSDATE,'"+parentUser.toUpperCase()+"',"
										+ "'Rule R1(Link New Workitem) executed','Workitem ("+workItemNumber+") routed to "+targetWS+"'",sessionId);
								APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", " WI_NAME,CURR_WS_NAME,USERNAME,"
										+ "CREATE_DATE,USER_ID,ACTION,REMARKS","'"+workItemNumber+"','Initiator','"
										+ parentPersonalName.toUpperCase()+"',SYSDATE,'"+parentUser.toUpperCase()+"',"
										+ "'Rule R1(Link New Workitem) executed from parent: "+wiNumber+"',"
												+ "'Workitem routed to "+targetWS+"'",sessionId);
							} catch (Exception e) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in insert into dec hist: ");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							}
							APCallCreateXML.APUpdate("EXT_TFO", "LINKED_WI_NAME", "'"+workItemNumber+"'", "WI_NAME = '"
									+wiNumber+"'", sessionId);
							attributeMap.clear();
							attributeMap.put("WI_NAME", workItemNumber);
							assignComplexMap(wiNumber,workItemNumber);
							try {
								String outputXmlAssgn = ProdCreateXML.WFSetAttributes(sessionId, workItemNumber, 1, 
										attributeMap,complexMap,processDefId);
								XMLParser xp1 = new XMLParser(outputXmlAssgn);
								int mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Assign Attributes Main Code: "
										+mainCodeAssgn);
							} catch (Exception e) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in assign attributes: ");
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							}
							ProdCreateXML.WMUnlockWorkItem(sessionId, workItemNumber, 1);
							if("ToDoList".equalsIgnoreCase(targetWS)){
//								Date maturityDateFormatted = sdf.parse(maturityDate);
								APCallCreateXML.APInsert("BPM_EVENTGEN_TXN_DATA", "INSERTIONDATETIME, WI_NAME, "
										+ "PROCESS_NAME, SOURCING_CHANNEL, REQUESTMODE, EXPIRY_DATE, STATUS_FLAG", 
										"SYSDATE,'"+workItemNumber+"','TFO','"+sourcingChannel+"','X',to_date('"+maturityDate+"','yyyy-MM-dd HH24:MI:SS'),"
												+ "'N'", sessionId);
							}
							associateDocumentsWithWorkitem(sessionId, wiNumber, workItemNumber);
							attributeMap.clear();
							attributeMap.put("mode", mode);
							attributeMap.put("ruleFlag", "N");
							HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
									(processDefId, sourcingChannel, "1.0", sourcingChannel,1);
							int eventID = (Integer) eventMap.keySet().toArray()[0];
							List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
							if(callArray != null) {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array- wiName= " + workItemNumber 
										+ ":" + callArray.toString());
								boolean nogoCall = false;
								for (CallEntity callEntity : callArray) {
									if(callEntity.isMandatory()){
										String outputXML = CallHandler.getInstance().executeCall
												(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
														workItemNumber, false);
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call "
												+ "from C_TFO_BRMS outputxml: " + outputXML);
										if(!"".equals(outputXML)){
											xp = new XMLParser(outputXML);
//											mainCode = Integer.parseInt(xp.getValueOf("returnCode"));
//											if(mainCode != 0){
//												nogoCall = true;
//											}
										}
									}
								}
							}
							resp.setStatusCode("0");
							resp.setStatusDescription("Workitem Created Successfully");
							resp.setWiNumber(workItemNumber);
						}
					}
				}
			} else {
				resp.setStatusCode("-1");
				resp.setStatusDescription("Mandatory Data Missing");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TFO_RULES dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}	
	
	private void assignAttributeMap(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside assignAttributeMap");
		attributeMap.put("REQUEST_CATEGORY", requestCategory);
		attributeMap.put("REQUEST_TYPE", requestType);
		attributeMap.put("REQ_DATE_TIME", getDate());
		attributeMap.put("INITIATOR_BRANCH", defaultMap.get("TF_BRMS_INITIATOR_BRANCH"));
		attributeMap.put("INITIATOR_USERID", defaultMap.get("TF_BRMS_INITIATOR_USERID"));
		attributeMap.put("DELIVERY_BRANCH", defaultMap.get("TF_BRMS_DELIVERY_BRANCH"));
		attributeMap.put("PROCESS_TYPE", defaultMap.get("TF_BRMS_PROCESS_TYPE"));
		if(subBy != null && !"".equalsIgnoreCase(subBy)){
			attributeMap.put("REQUESTED_BY", subBy);
		} else {
			attributeMap.put("REQUESTED_BY", requestCategory+"_"+defaultMap.get("TF_BRMS_REQUESTED_BY"));
		}
		if(sourceChannel != null && !"".equalsIgnoreCase(sourceChannel)){
			attributeMap.put("SOURCE_CHANNEL", sourceChannel);
		} else {
			attributeMap.put("SOURCE_CHANNEL", requestCategory+"_"+defaultMap.get("TF_BRMS_SOURCE_CHANNEL"));
		}
		attributeMap.put("REQ_DATE_TIME", getDate());
		attributeMap.put("BILL_CUST_HLDING_ACC_US", defaultMap.get("TF_BRMS_BILL_CUST_HLDING_ACC_US"));
		attributeMap.put("INF_TENOR_BASE", defaultMap.get("TF_BRMS_INF_TENOR_BASE"));
		attributeMap.put("FINAL_DESC_PPM", defaultMap.get("TF_BRMS_FINAL_DESC_PPM"));
		attributeMap.put("LMTCRE_APP_AVL_PPM", defaultMap.get("TF_BRMS_LMTCRE_APP_AVL_PPM"));
		attributeMap.put("REQ_SIGN_MAN_PPM", defaultMap.get("TF_BRMS_REQ_SIGN_MAN_PPM"));
		attributeMap.put("DOC_REV_SUCC_PPM", defaultMap.get("TF_BRMS_DOC_REV_SUCC_PPM"));
		attributeMap.put("ROUTE_TO_PPM", defaultMap.get("TF_BRMS_ROUTE_TO_PPM"));
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "attributeMap: "+attributeMap.toString());
	}
	
	private void assignComplexMap(String wiNumber, String childWorkitemNumber) throws Exception{
		try {
			complexMap = new HashMap<String, LinkedHashMap<Integer,HashMap<String,String>>>();
			innerComplexAttribMap = new LinkedHashMap<Integer,HashMap<String,String>>();
			String outputXml = APCallCreateXML.APSelect("SELECT PARTY_TYPE, PARTY_ID, PARTY_DESC, CUSTOMER_NAME, ADDRESS1,"
					+ " ADDRESS2, ADDRESS3, COUNTRY, MEDIA_TYPE FROM TFO_DJ_PARTY_DETAILS WHERE WINAME = '" +wiNumber+"'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "
					+outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in party: "
						+noOfFields);
				for (int i = 0; i < noOfFields; ++i) {
					innerInnerComplexMap = new HashMap<String, String>();
					String Record = xp.getNextValueOf("Record");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"cmplx Record :"+Record);
					XMLParser xp2 = new XMLParser(Record); 
					//innerInnerComplexMap.put("winame", childWorkitemNumber);
					innerInnerComplexMap.put("partyType", xp2.getValueOf("PARTY_TYPE"));
					innerInnerComplexMap.put("partyID", xp2.getValueOf("PARTY_ID"));
					innerInnerComplexMap.put("partyDesc", xp2.getValueOf("PARTY_DESC"));
					innerInnerComplexMap.put("customerName", xp2.getValueOf("CUSTOMER_NAME"));
					innerInnerComplexMap.put("address1", xp2.getValueOf("ADDRESS1"));
					innerInnerComplexMap.put("address2", xp2.getValueOf("ADDRESS2"));
					innerInnerComplexMap.put("address3", xp2.getValueOf("ADDRESS3"));
					innerInnerComplexMap.put("country", xp2.getValueOf("COUNTRY"));
					innerInnerComplexMap.put("mediaType", xp2.getValueOf("MEDIA_TYPE"));
					innerComplexAttribMap.put(i, innerInnerComplexMap);
				}
				if(noOfFields>0){
					complexMap.put("PARTY", innerComplexAttribMap);
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"complexMap: "
						+complexMap.toString());
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in assignComplexMap: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}
	
	private void associateDocumentsWithWorkitem(String sessionId, String fromWorkItemNumber,String toWorkItemNumber)
			throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside associateDocumentsWithWorkitem");
		String sQuery;
		String outputXml;
		String itemIndex = "";
		try {
			outputXml = APCallCreateXML.APSelect("SELECT ITEMINDEX FROM EXT_TFO WHERE WI_NAME = N'" 
					+ toWorkItemNumber + "'");
			XMLParser xp = new XMLParser(outputXml);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				itemIndex = xp.getValueOf("ITEMINDEX");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in fetching item index: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		sQuery = "SELECT DOC.DOCUMENTINDEX DOCUMENTINDEX, DOC.DOCUMENTSIZE, DOC.COMMNT, DOC.NAME, DOC.IMAGEINDEX IMAGEINDEX, "
				+ "DOC.APPNAME APPNAME, DOC.DOCUMENTTYPE DOCUMENTTYPE, DOC.NOOFPAGES NOOFPAGES, DOC.VOLUMEID VOLUMEID"
				+ " FROM PDBDOCUMENT DOC, PDBDOCUMENTCONTENT DOCCONTENT , PDBFOLDER FOLDER "
				+ "WHERE DOC.DOCUMENTINDEX = DOCCONTENT.DOCUMENTINDEX AND "
				+ "DOCCONTENT.PARENTFOLDERINDEX = FOLDER.FOLDERINDEX AND FOLDER.NAME  = '"+fromWorkItemNumber+"'";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sQuery to get docindex is :"+sQuery);
		try {
			outputXml = APCallCreateXML.APSelect(sQuery);		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"docindex outputxml " + outputXml);
			XMLParser xp = new XMLParser(outputXml);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
//				String isIndex = xp.getValueOf("IMAGEINDEX", start, end)+ "#" + LapsConfigurations.getInstance().volumeID;
				String docName = xp.getValueOf("COMMNT", start, end);
				String docSize = xp.getValueOf("DOCUMENTSIZE", start, end);
				String docType = xp.getValueOf("NAME", start, end);
				String extension = xp.getValueOf("APPNAME", start, end);
				String docTypeFlag = xp.getValueOf("DOCUMENTTYPE", start, end);
				int volumeId = Integer.parseInt(xp.getValueOf("VOLUMEID", start, end));
				String isIndex = xp.getValueOf("IMAGEINDEX", start, end)+ "#" + volumeId;
				int noOfPages = Integer.parseInt(xp.getValueOf("NOOFPAGES", start, end));
//				outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, docType, "pdf", docName,
//						LapsConfigurations.getInstance().volumeID, docIndex, 1, "N", docSize);	
				outputXml = ProdCreateXML.AddDocument(sessionId, itemIndex, docType, extension, docName,
						volumeId, isIndex, noOfPages, docTypeFlag, docSize);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in adding documnet to child WI: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
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
