package com.newgen.ao.laps.sourcingchannel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
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

public class C_LINK_WI implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap;
	private HashMap<String, String> defaultMap;
	public LapsModifyRequest request;
	private String requestCategory;
	private String requestType;
	private String insertedBY;
    private int processDefId;

	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationId, String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside C_LINKED_WI");
			processDefId = LapsConfigurations.getInstance().processDefIdTFO;
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
			int mainCode = -1;
			resp.setChannelRefNumber(channelRefNumber);
			resp.setCorrelationId(correlationId);
			String outputXml = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE,INSERTED_BY  "
					+ "FROM TFO_DJ_PROTRADE_TXN_DATA WHERE CHANNELREFNUMBER = '"+channelRefNumber
					+ "' AND CORRELATIONID = '"+correlationId+"'");
			XMLParser xp = new XMLParser(outputXml);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				requestCategory = xp.getValueOf("REQUEST_CATEGORY");
				requestType = xp.getValueOf("REQUEST_TYPE");
				insertedBY = xp.getValueOf("INSERTED_BY");
				String[] retMsg = BPMMandatoryCheck.isMandatoryCheck(LapsConfigurations.getInstance().CabinetName, 
						channelRefNumber, correlationId).split("###");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"retMsg: "+retMsg.toString());
				if(retMsg[0].equalsIgnoreCase("true")){
					Map<String,Map<String,HashMap<String, String>>> protradeMappingMap = ChannelCallCache.getInstance().getProtradeMappingMap();
					Map<String,HashMap<String, String>> attribMap = protradeMappingMap.get(requestCategory+"#"+requestType);
					defaultMap = StageCallCache.getReference().getCachedDefaultMap();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"defaultMap: "+defaultMap);
					for(Map.Entry<String, HashMap<String, String>> mapEntry: attribMap.entrySet()){
						if(mapEntry.getKey().equals("EXTERNAL")){
							HashMap<String, String> attribList = mapEntry.getValue();
							StringBuilder attributeValues = new StringBuilder();
							for(Map.Entry<String, String> value : attribList.entrySet()){
								if(value.getKey().contains("_DATE")){
									attributeValues.append("TO_CHAR("+value.getKey()+",'DD/MM/YYYY') AS "+value.getKey()).append(",");
								} else {
									attributeValues.append(value.getKey()).append(",");
								}
							}
							attributeValues.setLength(attributeValues.length()-1);							
							outputXml = APCallCreateXML.APSelect("SELECT "+attributeValues
									+" FROM TFO_DJ_PROTRADE_TXN_DATA WHERE CHANNELREFNUMBER = '"
									+channelRefNumber+"' AND CORRELATIONID = '"+correlationId+"'");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML attrib values: "+outputXml);
							xp = new XMLParser(outputXml);
							mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
							if (mainCode == 0) {
								attributeMap = new HashMap<String, String>();
//								for(String value : attribList){
//									attributeMap.put(value, xp.getValueOf(value));
//								}
								for(Map.Entry<String, String> value : attribList.entrySet()){
									if(value.getKey().contains(" AS ")){
										attributeMap.put(value.getValue(), xp.getValueOf(
												value.getKey().substring(value.getKey().indexOf(" AS ")+4, 
														value.getKey().length())));
									} else {
										attributeMap.put(value.getValue(), xp.getValueOf(value.getKey()));
									}
//									attributeMap.put(value.getValue(), xp.getValueOf(value.getKey()));
								}
								attributeMap.put("CHILD_WI_FLAG", defaultMap.get("PT_UTILITY_FLAG"));
								attributeMap.put("REQ_DATE_TIME", getDate());
							if(!requestType.equalsIgnoreCase("ELCB_DISC")){
							//	if(!"TFO_SYSTEM".equalsIgnoreCase(insertedBY))
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
								}
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attributeMap C_LINKED_WI: "
										+attributeMap.toString());								
							}
						} 
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
						attributeMap.clear();
						attributeMap.put("WI_NAME", workItemNumber);
						try {
							String outputXmlAssgn = ProdCreateXML.WFSetAttributes(sessionId, workItemNumber, 1, 
									attributeMap,null,processDefId);
							XMLParser xp1 = new XMLParser(outputXmlAssgn);
							int mainCodeAssgn = Integer.parseInt(xp1.getValueOf("MainCode"));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Assign Attributes Main Code: "
									+mainCodeAssgn);
						} catch (Exception e) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in assign attributes: ");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
						associateDocumentsWithWorkitem(sessionId, wiNumber, workItemNumber);
						attributeMap.put("channelRefNumber", channelRefNumber);
						attributeMap.put("correlationId", correlationId);
						attributeMap.put("requestCategory", requestCategory);
						attributeMap.put("requestType", requestType);
						attributeMap.put("mode", mode);
						int stageId = 1;
						HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
								(processDefId, sourcingChannel, "1.0", sourcingChannel,stageId);
						int eventID = (Integer) eventMap.keySet().toArray()[0];
						List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
						if(callArray != null) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + workItemNumber + ":" + callArray.toString());
							boolean nogoCall = false;
							for (CallEntity callEntity : callArray) {
								if(callEntity.isMandatory()){
									String outputXML = CallHandler.getInstance().executeCall
											(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
													workItemNumber, false);
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
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "before wmcomplete: ");
						ProdCreateXML.WMCompleteWorkItem(sessionId, workItemNumber, 1);
						ProdCreateXML.WMUnlockWorkItem(sessionId, workItemNumber, 1);
					}
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "WI not created: ");
					resp.setStatusCode("-1");
					resp.setStatusDescription("Mandatory Data Missing");
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_LINKED_WI dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}

	private String getDate(){
		String today = "";
		Date date = Calendar.getInstance().getTime();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		today = formatter.format(date);
		return today;	
	}
	
	private void associateDocumentsWithWorkitem(String sessionId, String fromWorkItemNumber,String toWorkItemNumber)
			throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside C_LINK_WI associateDocumentsWithWorkitem");
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in adding documnet to link WI: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}
}
