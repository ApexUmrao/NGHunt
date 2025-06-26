package com.newgen.ao.laps.calls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.Date;
import java.text.SimpleDateFormat;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.SanctionScreeningMapping;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.ao.laps.util.ExecuteXML;

public class SanctionScreening implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus = "Y";
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String customerId;
	private String txnType;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private String screeningType = "";
	private ArrayList screeningList;
	private String channelID = "";
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "SS";
	private String requestorProcessName;
	private String requestorWiName;
	private StringBuilder columnList ;
	private StringBuilder modifyColumnList ;
	private StringBuilder tagList ;
	private StringBuilder values ;
	private ArrayList valueList;
	private String type;
	private String entityType;
	private String column;
	private String modifyColumn;
	private String childWiName;
	private String batchId;
	private String trsdOutput;
	private String sourcingChannel;
	private String requestTypeCOB = "";
	//private String CBGSourcingChannel = "";
	private Boolean trsdExecutionSuccess = true;
	private StringBuilder query ;
	boolean trsdCaseError = false;
	boolean trsdCaseFound =false;
	private String leadRefNumber = "";
	private String requestCategory = "";
	private HashMap<String, ArrayList<String>> processValuesMap = new HashMap<String, ArrayList<String> >();
	private HashMap<String,ArrayList<String>> screeningChannelMap = new HashMap<String, ArrayList<String>>();
	ArrayList<String> processValues = new  ArrayList<String>();
	String screeningValues ="";
	Set<String> allTableNamesList = new HashSet<String>(); //insertionOrderId change
	private String requestorSourcingChannel;
	private String key;

	public SanctionScreening(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		SanctionScreeningMapping ss;
		String outputXML;	
		String tableWiNameCol;
		HashMap <String, String> attribMap;
		HashMap <String, String> attributeMapModifyColumn;
		String tablename;
		String trsdFlag = "";
		try {
			childWiName = defaultMap.get("TRSD_WI_NAME");

			if ("".equalsIgnoreCase(childWiName) || null == childWiName){
				batchId = "1";
			} else {
				outputXML = APCallCreateXML.APSelect("SELECT NVL(MAX(BATCH_ID)+1,0) as BATCH_ID FROM BPM_TRSD_DETAILS WHERE WI_NAME  = '"+this.WI_NAME+"'");
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					batchId = xp.getValueOf("BATCH_ID");
				}
			}
			if("WBG".equalsIgnoreCase(requestorProcessName)){
				outputXML = APCallCreateXML.APSelect("SELECT SOURCING_CHANNEL FROM EXT_WBG_AO WHERE WI_NAME  = '"+this.WI_NAME+"'");
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					sourcingChannel = xp.getValueOf("SOURCING_CHANNEL");
				}
			}
		
			requestorProcessName = defaultMap.get("REQUESTOR_PROCESS_NAME");
			requestorWiName = defaultMap.get("REQUESTOR_WI_NAME");
			leadRefNumber = defaultMap.get("LEAD_REF_NO");
			processValuesMap = ChannelCallCache.getInstance().getProcessValuesMapMap();
			processValues = processValuesMap.get(requestorProcessName);
			requestorSourcingChannel = defaultMap.get("REQUESTOR_SOURCING_CHANNEL"); //Added by reyaz 09-02-2023
			if("TFO".equalsIgnoreCase(requestorProcessName) || "AO".equalsIgnoreCase(this.requestorProcessName)){ 
				key = requestorProcessName +"#"+requestorProcessName;//Added shivanshu 13-02-23	
			} else {
			    key = requestorProcessName +"#"+requestorSourcingChannel.toUpperCase();//Added shivanshu 13-02-23
			}
			screeningChannelMap = ChannelCallCache.getInstance().getScreeningChannel();
			screeningList =new ArrayList<String>();
			screeningList = screeningChannelMap.get(key);
			screeningType = (String) screeningList.get(0);
			channelID = (String) screeningList.get(1);
			getRequestCategory();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "requestorProcessName : "+requestorProcessName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "requestorWiName : "+requestorWiName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processValues : "+processValues);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "key Value : "+key);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Screening List : "+screeningList);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Screening Type : "+screeningType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Screening ID : "+channelID);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "leadRefNumber : "+leadRefNumber);

			HashMap<String, ArrayList<SanctionScreeningMapping>> sanctionMaster = ChannelCallCache.getInstance().getSanctionScreeningMasterMap();	
			ArrayList<SanctionScreeningMapping> sanctionList = sanctionMaster.get(key);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sanction List : "+sanctionList);
			allTableNamesList.clear();
			//setting batch status as N 
			APCallCreateXML.APUpdate(processValues.get(0),"IS_TRSD_COMPLETE", "'N'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
			
			if ("COB".equalsIgnoreCase(requestorProcessName)) {
				getCOBReqType(requestorWiName);
			}
			for (int i = 0;i < sanctionList.size() ; i++){
				ss = new SanctionScreeningMapping();
				ss = sanctionList.get(i);
				type = ss.getType();				
				entityType = ss.getEntityType();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "type : "+type);
				tablename = ss.getTable();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tablename : "+tablename);
				allTableNamesList.add(tablename);
				attribMap = ss.getAttributeMap();
				attributeMapModifyColumn = ss.getAttributeModifyMap();
				columnList = new StringBuilder();
				modifyColumnList =new StringBuilder();
				tagList = new StringBuilder();
				for(Map.Entry<String, String> entryInner: attribMap.entrySet()){
					columnList.append(entryInner.getKey()).append(",");
					tagList.append(entryInner.getValue()).append(",");
					modifyColumnList.append(attributeMapModifyColumn.get(entryInner.getKey())).append(",");;
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"columnList : "+columnList.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"tagList : "+tagList.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"modifyColumnList : "+modifyColumnList.toString());

				column = columnList.substring(0,columnList.length()-1);
				modifyColumn = modifyColumnList.substring(0,modifyColumnList.length()-1);
				StringBuilder query =  new StringBuilder();
				if ("TFO".equalsIgnoreCase(requestorProcessName)){ //convert generic
					tableWiNameCol = "WINAME";
					outputXML = APCallCreateXML.APSelect("select "+processValues.get(1)+" from "+processValues.get(0)
							+" where  WI_NAME  = '"+this.requestorWiName+"'");
					XMLParser xp = new XMLParser(outputXML);
					int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0){
						trsdFlag = xp.getValueOf("TRSD_FLAG");
					}
				} else {
					tableWiNameCol = "WI_NAME";
				}
				if(tablename.equalsIgnoreCase("USR_0_WBG_AO_KYC_AFF_ENTITY") 
						|| tablename.equalsIgnoreCase("USR_0_WBG_AO_KYC_B_CLIENT")
						|| tablename.equalsIgnoreCase("USR_0_WBG_AO_KYC_SUPPLIER")
						|| tablename.equalsIgnoreCase("USR_0_WBG_AO_KYC_SUBSIDIARY")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"KDD405"+tablename);
					tableWiNameCol = "WINAME";
				}


				/*if ("TFO".equalsIgnoreCase(requestorProcessName)){
					column=column+",insertionorderid";
					query.append("SELECT "+column+" FROM "+tablename+" WHERE "+tableWiNameCol+" = '"+requestorWiName+"'");
                    query.append(" and UPPER(ENTITY) = UPPER('"+entityType+"') and "
							+ "UPPER(TRSD_SCREENING_TYPE) = UPPER('"+type+"') AND EXECUTION_STATUS = 'N'" );
				} else if("USR_0_WBG_AO_KYCUBODETAILS".equalsIgnoreCase(tablename)){
					query.append("SELECT "+column+" FROM "+tablename+" WHERE "
							+ ""+tableWiNameCol+" = '"+requestorWiName+"' AND EXECUTION_STATUS = 'N'");
                } else{
					query.append("SELECT "+column+" FROM "+tablename+" WHERE "
                      +tableWiNameCol+" = '"+requestorWiName+"'");
                }
*/
				if ("TFO".equalsIgnoreCase(requestorProcessName)){
					column=column+",insertionorderid";
					modifyColumn=modifyColumn+",insertionorderid";
					query.append("SELECT "+modifyColumn+" FROM "+tablename+" WHERE "+tableWiNameCol+" = '"+requestorWiName+"'");
                    query.append(" and UPPER(ENTITY) = UPPER('"+entityType.replace("'", "''")+"') and "
							+ "UPPER(TRSD_SCREENING_TYPE) = UPPER('"+type+"') AND EXECUTION_STATUS = 'N'" );
				} else if("USR_0_WBG_AO_KYCUBODETAILS".equalsIgnoreCase(tablename) && !"ITQAN".equalsIgnoreCase(this.requestorSourcingChannel) ){
					query.append("SELECT "+modifyColumn+" FROM "+tablename+" WHERE "
							+ ""+tableWiNameCol+" = '"+requestorWiName+"' AND EXECUTION_STATUS = 'N'");
                } else if("AUS".equalsIgnoreCase(entityType) && "ITQAN_M".equalsIgnoreCase(sourcingChannel)){
					query.append("SELECT "+modifyColumn+" FROM "+tablename+" WHERE "
		                      +tableWiNameCol+" = '"+requestorWiName+"'");
	            }else if("USR_0_CUST_TXN".equalsIgnoreCase(tablename) && "AO".equalsIgnoreCase(requestorProcessName)){ 
					query.append("SELECT "+modifyColumn+" FROM "+tablename+" WHERE "
							+ ""+tableWiNameCol+" = '"+requestorWiName+"' AND "
							+ " CUST_SNO = (SELECT SELECTED_ROW_INDEX FROM EXT_AO WHERE "
							+ " WI_NAME = '"+requestorWiName+"') + 1 ");
                }  else{
					query.append("SELECT "+modifyColumn+" FROM "+tablename+" WHERE "
                      +tableWiNameCol+" = '"+requestorWiName+"'");
                }
				outputXML = APCallCreateXML.APSelect(query.toString());
				XMLParser xp = new XMLParser(outputXML);
				valueList = new ArrayList<String>();
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					XMLParser xp1 = new XMLParser(outputXML);
					int start = xp1.getStartIndex("Records", 0, 0);
					int deadEnd = xp1.getEndIndex("Records", start, 0);
					int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int count = 0; count < noOfFields; ++count) {
						start = xp1.getStartIndex("Record", end, 0);
						end = xp1.getEndIndex("Record", start, 0);
						values = new StringBuilder();
						String colArray[] = column.split(",");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"colArray length  : "+colArray.length);
						if(colArray != null){
							for(int j=0; j< colArray.length; j++){
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"colArray["+j+"]  : "+colArray[j]);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"xp1 value  : "+xp1.getValueOf(colArray[j]));
								values.append(xp1.getValueOf(colArray[j], start, end)).append((char)25);
							}
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"upper Values  : "+values.toString());
						String values1 = values.toString().substring(0,values.length()-1);
						valueList.add(values1);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"valueList  : "+valueList);
				}
				
			//	screeningType = screeningValues.get(0);
//				if ("CBG".equalsIgnoreCase(requestorProcessName)) {
//					CBGSourcingChannel = getCBGChannelName(requestorWiName);
//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sourcing Channel CBG: " + CBGSourcingChannel);
//					if ("ADCBCOP".equalsIgnoreCase(CBGSourcingChannel)){
//						screeningType = "TRSD";
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Screening Type CBG: " + screeningType);
//					}
//				}		
				if(screeningType.equalsIgnoreCase("FSK")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside FSK " + screeningType);
					trsdOutput = executeFSK(type,entityType,tagList,valueList,column,tablename);
				    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FSK xml " + trsdOutput);
				    
				} else if(screeningType.equalsIgnoreCase("TRSD")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside TRSD " + screeningType);
					trsdOutput = executeTrsd(type,entityType,tagList,valueList,column,tablename);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " trsd xml" + trsdOutput);
				}
			}
			
			//setting batch status as y 
		//	APCallCreateXML.APUpdate(processValues.get(0),"BATCH_STATUS", "'Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///trsdSuccess" + trsdExecutionSuccess);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///trsdCaseError" + trsdCaseError);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///trsdCaseFound" + trsdCaseFound);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///trsdFlag" + trsdFlag);
			if (trsdExecutionSuccess) {
				if("TFO".equalsIgnoreCase(requestorProcessName)){

					String extTrsdFlag = getTrsdFlagValue(requestorWiName);
					APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE",
							"'"+extTrsdFlag+"','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);

				} else if (!trsdCaseError && !trsdCaseFound) {
					if(trsdFlag!=null && !"Y".equalsIgnoreCase(trsdFlag) 
							&& "TFO".equalsIgnoreCase(requestorProcessName)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///if");
						APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'N','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					} else if ("WBG".equalsIgnoreCase(requestorProcessName)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///else");
						APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'N','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					} else if ("CBG".equalsIgnoreCase(requestorProcessName)){
			            LapsModifyLogger.logMe(1, "///else if cbg :: update " + (String)this.processValues.get(0) + " " + (String)this.processValues.get(1) + ",IS_TRSD_COMPLETE 'N','Y' WI_NAME  = '" + this.requestorWiName + "'");
						if("ADCBCOP".equalsIgnoreCase(requestorSourcingChannel) || "ADCBCCSSO".equalsIgnoreCase(requestorSourcingChannel) || "ADCBMIB".equalsIgnoreCase(requestorSourcingChannel)){
				            APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'N','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
						}else {
				            APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'No','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
						}
			        } else if ("IHUB".equalsIgnoreCase(requestorProcessName)){
			            LapsModifyLogger.logMe(1, "///else if ihub :: update " + (String)this.processValues.get(0) + " " + (String)this.processValues.get(1) + ",IS_TRSD_COMPLETE 'N','Y' WI_NAME  = '" + this.requestorWiName + "'");
				            APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'No','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					}  else if ("AO".equalsIgnoreCase(requestorProcessName)){
			            LapsModifyLogger.logMe(1, "///else if AO :: update " + (String)this.processValues.get(0) + " " + (String)this.processValues.get(1) + ",IS_TRSD_COMPLETE 'N','Y' WI_NAME  = '" + this.requestorWiName + "'");
			            APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'N','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					//Supplimentry changes Done 
					} else if ("DSCOP".equalsIgnoreCase(requestorProcessName)){
			            LapsModifyLogger.logMe(1, "///else if DSCOP :: update " + (String)this.processValues.get(0) + " " + (String)this.processValues.get(1) + ",IS_TRSD_COMPLETE 'N','Y' WI_NAME  = '" + this.requestorWiName + "'");
			            APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'N','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					}
			        
					if("WBG".equalsIgnoreCase(requestorProcessName))
						addTRSDAudit(WI_NAME, leadRefNumber, "SUCCESS");
				} else if (trsdCaseError) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD CASE ERROR");
					callStatus = "N";
					if("AO".equalsIgnoreCase(requestorProcessName)){
						APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'E','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					} else {
					    APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'P','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					}
					if("WBG".equalsIgnoreCase(requestorProcessName)){
						addTRSDAudit(WI_NAME, leadRefNumber, "TRSD PENDING FOR RETRY");
					}
				} else if (trsdCaseFound) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD CASE FOUND");
					if("IHUB".equalsIgnoreCase(requestorProcessName)){
				        LapsModifyLogger.logMe(1, "///else if ihub :: update " + (String)this.processValues.get(0) + " " + (String)this.processValues.get(1) + ",IS_TRSD_COMPLETE 'N','Y' WI_NAME  = '" + this.requestorWiName + "'");
					    APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'Yes','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					} else if("CBGAPP".equalsIgnoreCase(requestorSourcingChannel) || "MIB".equalsIgnoreCase(requestorSourcingChannel)){  //added date 14-03-2023
			            LapsModifyLogger.logMe(1, "///trsdCaseFound if cbg :: update " + (String)this.processValues.get(0) + " " + (String)this.processValues.get(1) + ",IS_TRSD_COMPLETE 'N','Y' WI_NAME  = '" + this.requestorWiName + "'");
			            APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'Yes','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
			        } else {
					    APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'Y','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
			        }
					if("WBG".equalsIgnoreCase(requestorProcessName))
						addTRSDAudit(WI_NAME, leadRefNumber, "MATCH FOUND");
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD CASE ELSE");
					APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'P','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
					if("WBG".equalsIgnoreCase(requestorProcessName))
						addTRSDAudit(WI_NAME, leadRefNumber, "FAILURE");
				}
				// updateTRSDXml(response,winame);
				// saveTRSDResponse(response,winame);
				// WFSetAttributes(winame, 1, "N");

			} else {
				APCallCreateXML.APUpdate(processValues.get(0), processValues.get(1)+",IS_TRSD_COMPLETE", "'P','Y'"," WI_NAME  = '"+requestorWiName+"'", sessionId);
				if("WBG".equalsIgnoreCase(requestorProcessName))
					addTRSDAudit(WI_NAME, leadRefNumber, "TRSD PENDING FOR RETRY");
			}
		} catch (Exception e) {
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
	}



	private String executeTrsd(String type,String entityType, StringBuilder tagList, 
			ArrayList valueList,String columnList,String tablename) {
		StringBuilder trsdReq ;
		String trsdResponse = "";
		boolean[] caseFound;
		boolean trsdSuccess = true;
		String[] typeArr;
		String entityName = "";
		String field1 = "";
		String insertionOrderId ="";
		

		try {
			if("both".equalsIgnoreCase(type)){
				typeArr = new String[2];
				typeArr[0] = "INDIVIDUAL";
				typeArr[1] = "ORGANISATION";	
			} else {
				typeArr = new String[1];
				typeArr[0] = type;
			}
			
			if("TFO".equalsIgnoreCase(requestorProcessName)){
				field1 ="TRADE";
			}else{
				field1 = requestorProcessName;
			} 
			
			for(int counter1 = 0 ;counter1 < typeArr.length ; counter1++ ){
				for (int counter = 0 ;counter < valueList.size() ; counter++ ) {
					String clientID =  LapsUtils.getInstance().getTrsdRefNum();
					String[] tags = tagList.toString().substring(0,tagList.length()-1).split(",");
					String[] values = valueList.get(counter).toString().split(String.valueOf((char)25));
					String[] columnList1 =columnList.split(",");
					trsdReq = new StringBuilder();
					trsdReq.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
					.append("<sdQuery>")
					.append("<types>")
					.append("<type>"+typeArr[counter1]+"</type>")
					.append("</types>");
					//for tfo
					if("TFO".equalsIgnoreCase(requestorProcessName)){
					for(int j=0;j<columnList1.length;j++){
						
						if(columnList1[j].equalsIgnoreCase("INSERTIONORDERID")){
							insertionOrderId =values[j];
							break;
						}
					}
				    }
					for (int i = 0;i < tags.length ; i++){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "values[i] : " + values[i]);
						entityName = values[i];
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entityName[i] : " + entityName);
						//entityName =  normalizeString(values[i]) ;
						trsdReq.append("<"+tags[i]+">" + normalizeString(values[i]) + "</"+tags[i]+">");
					}
					if (("COB".equalsIgnoreCase(this.requestorProcessName)) && (!(type.equalsIgnoreCase(this.requestTypeCOB)))) {
				            LapsModifyLogger.logMe(1, "Inside if COB" + type);
				            break;
					}else{
						trsdReq.append("<createCaseOnMatches>CREATECASE</createCaseOnMatches>")
						//.append("<field1>" + requestorProcessName + "</field1>")
						//.append("<field1>TRADE</field1>")
						.append("<field1>"+field1+"</field1>")
						.append("<clientID>" +clientID+ "</clientID>")
						.append("<field2>" + requestorWiName + "</field2>")
						.append("<field3>" +requestCategory + "</field3>")
						.append("</sdQuery>");
					 }
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "trsdReq : " + trsdReq.toString());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "trsd url : " + LapsConfigurations.getInstance().trsdUrl);
					insertTRSDXmlAudit(WI_NAME, trsdReq.toString(), "Request", clientID + "", "S");
					saveTRSDDetails(leadRefNumber, WI_NAME, requestorProcessName,clientID,tagList,valueList.get(counter).toString(), typeArr[counter1], entityType);
					try {
							trsdResponse =  LapsUtils.executeRestAPI(processValues.get(3), trsdReq.toString());
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "trsdResponse : " + trsdResponse);
					} catch (Exception e) {
						trsdSuccess = false;
						trsdExecutionSuccess = false;
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entityName[i] 2 : " + entityName);
					caseFound = updateTRSDDetail(trsdResponse, WI_NAME, trsdSuccess, clientID + "",
							tablename,type,entityType,entityName,leadRefNumber,requestorProcessName,tagList,
							valueList.get(counter).toString(),typeArr[counter1],insertionOrderId);
					//saveTRSDDetails(leadRefNumber, WI_NAME, requestorProcessName,clientID,
					//	tagList,valueList.get(counter).toString(), typeArr[counter1], entityType);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"$$" + caseFound[1] + "--" + caseFound[0] + "3439");
					if (caseFound[1] == true) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///3442");
						trsdCaseError = true;
					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}
		} catch (Exception e) {
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
		return trsdResponse;
	}
	
	public boolean[] updateTRSDDetail(String Response, String winame, Boolean trsdSuccess, 
			String clientIDFromReq,String tablename, String type, String entityType, String entityName,
			String leadRefNumber,String requestorProcessName, StringBuilder tagList,
			String valueList,String typeArr,String insertionOrderId) {boolean[] out = new boolean[2];
		    out[0] = false;
		    out[1] = false;
		    String case_code = "";
		    String case_num = "";
		    String client_id = "";
		    int case_num_found = 0;
		    int statusCode = 0;
		    String trsd_status = "";
		    String trsd_status_desc = "";
		    try
		    {
		      LapsModifyLogger.logMe(1, "inside saveTRSDResponse" + 
		        Response + "insertionOrderId :" + insertionOrderId);

		      LapsModifyLogger.logMe(1, "valuee for success" + trsdSuccess);
		      if ((trsdSuccess.booleanValue()) && (Response != null) && 
		        (!("".equalsIgnoreCase(Response)))) {
		        String sTable_info;
		        String sColumn;
		        String sValues;
		        String sWhere;
		        String values;
		        XMLParser xmlDataParser = new XMLParser();
		        xmlDataParser.setInputXML(Response);

		        if (clientIDFromReq.equalsIgnoreCase(xmlDataParser.getValueOf("clientID"))) {
		          case_code = xmlDataParser.getValueOf("caseCode");
		          case_num = xmlDataParser.getValueOf("caseNum");
		          client_id = xmlDataParser.getValueOf("clientID");
		          statusCode = Integer.parseInt(xmlDataParser.getValueOf("statusCode"));
		          LapsModifyLogger.logMe(1, "insert case_code case_num" + case_code + "$" + case_num + "$" + statusCode);
		          if (statusCode != 0) {
		            out[1] = true;
		            trsd_status = "E";
		            trsd_status_desc = "TRSD Screening Error";
		          }
		          else if ((!("".equalsIgnoreCase(case_num))) && (case_num != null))
		          {
		            trsd_status = "P";
		            case_num_found = 1;
		            trsd_status_desc = "Match Found & Pending in TRSD";
		          } else {
		            trsd_status = "N";
		            trsd_status_desc = "No Match Found";
		          }

		          if ("TFO".equalsIgnoreCase(requestorProcessName)) {
		            sTable_info = tablename;
		            sColumn = "CASE_ID,CASE_NUMBER,TRSD_SCREENING_RESULT,channel_reference_no,execution_status";
		            sValues = "'" + case_code + "','" + case_num + "','" + 
		              trsd_status_desc + "','" + client_id + "','" + 
		              "Y" + "'";
		            sWhere = "winame = N'" + this.requestorWiName + 
		              "'  and upper(entity) = upper('" + entityType + "')" + 
		              " and upper(trsd_screening_type) = upper('" + type + "')" + 
		              " and insertionOrderId='" + insertionOrderId + "'" + 
		              " and upper(ENTITY_NAME) = upper('" + entityName.replaceAll("'", "''") + "')";
		            LapsModifyLogger.logMe(1, "sWhere===============" + sWhere);
		            APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, this.sessionId);
		            values = "'" + this.requestorWiName + "','" + tablename + "','" + entityType + "','" + type + "','','S'";
		            APCallCreateXML.APProcedure(this.sessionId, "BPM_SCREENING_UPDATE", values, 6);
		          }

		          updateTRSDDetails(leadRefNumber, this.WI_NAME, requestorProcessName, clientIDFromReq, 
		            tagList, valueList, typeArr, entityType);

		          sTable_info = "BPM_TRSD_DETAILS";
		          sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
		          sValues = "'" + case_code + "','" + case_num + "','" + 
		            trsd_status + "'";
		          sWhere = "wi_name = N'" + winame + 
		            "' and trsd_client_id ='" + client_id + "'";
		          APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, this.sessionId);

		          insertTRSDXmlAudit(winame, Response, "Response", client_id, trsd_status);
		        } else {
		          out[1] = true;
		          trsd_status = "E";
		          insertTRSDXmlAudit(winame, Response, "Response", client_id, 
		            trsd_status);
		          sTable_info = "BPM_TRSD_DETAILS";
		          sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
		          sValues = "'" + case_code + "','" + case_num + "','" + 
		            trsd_status + "'";
		          sWhere = "wi_name = N'" + winame + 
		            "' and trsd_client_id ='" + clientIDFromReq + "'";
		          APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, this.sessionId);
		          if ("TFO".equalsIgnoreCase(requestorProcessName)) {
		            sTable_info = tablename;
		            sColumn = "CASE_ID,CASE_NUMBER,TRSD_SCREENING_RESULT,channel_reference_no";
		            sValues = "'" + case_code + "','" + case_num + "','" + 
		              "TRSD Screening Error" + "','" + clientIDFromReq + "'";
		            sWhere = "winame = N'" + this.requestorWiName + 
		              "'  and upper(entity) = upper('" + entityType + "') and upper(trsd_screening_type) = upper('" + type + "')" + 
		              " and upper(ENTITY_NAME) = upper('" + entityName.replaceAll("'", "''") + "')";
		            LapsModifyLogger.logMe(1, "sWhere===============" + sWhere);
		            APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, this.sessionId);
		            values = "'" + this.requestorWiName + "','" + tablename + "','" + entityType + "','" + type + "','','S'";
		            APCallCreateXML.APProcedure(this.sessionId, "BPM_SCREENING_UPDATE", values, 6);
		          }

		        }

		      }else{
		        out[1] = true;
		        trsd_status = "E";
		        insertTRSDXmlAudit(winame, Response, "Response", 
		          client_id, trsd_status);
		        String sTable_info = "BPM_TRSD_DETAILS";
		        String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
		        String sValues = "'" + case_code + "','" + case_num + "','" + 
		          trsd_status + "'";
		        String sWhere = "wi_name = N'" + winame + 
		          "' and trsd_client_id ='" + clientIDFromReq + "'";
		        APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, this.sessionId);

		        if ("TFO".equalsIgnoreCase(requestorProcessName)) {
		          sTable_info = tablename;
		          sColumn = "CASE_ID,CASE_NUMBER,TRSD_SCREENING_RESULT,channel_reference_no";
		          sValues = "'" + case_code + "','" + case_num + "','" + 
		            "TRSD Screening Error" + "','" + clientIDFromReq + "'";
		          sWhere = "winame = N'" + this.requestorWiName + 
		            "'  and upper(entity) = upper('" + entityType + "') and upper(trsd_screening_type) = upper('" + type + "')" + 
		            " and upper(ENTITY_NAME) = upper('" + entityName.replaceAll("'", "''") + "')";
		          LapsModifyLogger.logMe(1, "sWhere===============" + sWhere);
		          APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, this.sessionId);
		          String values = "'" + this.requestorWiName + "','" + tablename + "','" + entityType + "','" + type + "','','S'";
		          APCallCreateXML.APProcedure(this.sessionId, "BPM_SCREENING_UPDATE", values, 6);
		        }

		      }

		      if (out[1]) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside status code check");
					// WFSetAttributes(winame, 1, "P");
					return out;
				} else if (case_num_found == 1) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside case num");
					// WFSetAttributes(winame, 1, "Y");
					out[0] = true;
					return out;
				}

			} catch (Exception ex) {
				callStatus = "N";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Error in saveTRSDResponse(): " + ex.getMessage());
				out[0] = true;
				return out;
			}
			return out;
		   }
	
	
	public void insertTRSDXmlAudit(String wi_name, String xml, String audit_type, String trsd_client_id, String trsd_status){
		try{
			String sValues = "";
			String sTable = "bpm_trsd_audit";
			String sColumn = "WI_NAME,TRSD_AUDIT_TIME,TRSD_AUDIT_TYPE,TRSD_AUDIT_XML,TRSD_CLIENT_ID,TRSD_STATUS";
			xml = xml.replace("'", "''");
			xml = createNormalizedXML(xml);
		//	trsd_client_id = trsd_client_id+"_"+entityType;
			if (audit_type.equalsIgnoreCase("Request")) {
				sValues = "'" + wi_name + "',systimestamp,'REQ'," + xml + ",'"
						+ trsd_client_id + "','S'";
			} else if (audit_type.equalsIgnoreCase("Response")) {
				sValues = "'" + wi_name + "',systimestamp,'RES'," + xml + ",'"
						+ trsd_client_id + "','" + trsd_status + "'";
			}
			String outputXml = APCallCreateXML.APInsert(sTable, sColumn, sValues, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);
		}catch (Exception e){
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
	}
	public void saveTRSDDetails(String leadRefNo, String winame,
			String channelName, String ClientID,StringBuilder tagList, String valueList , String type, String entityType){
		try {
			String tableName = "bpm_trsd_details";
			String insertionOrderId = "";
			StringBuilder columnName = new StringBuilder();
			StringBuilder sValues = new StringBuilder();
			columnName.append("BATCH_ID, WI_NAME, TRSD_CHANNEL_NAME, TRSD_STATUS, TRSD_CLIENT_ID,"
					+ " TRSD_TYPE, TRSD_ENTITY_TYPE, TRSD_SCREENING_DATE");
			sValues.append( "'" + batchId + "','" + winame + "','" + channelName + "','N','" + ClientID + "','" + type+ "','" +
					entityType + "', SYSDATE");
			String tagArr[] =  tagList.toString().split(",");
			for (int i = 0;i < tagArr.length ; i++) {
				String value = valueList.split(String.valueOf((char)25))[i];
				if("dateOfBirth".equalsIgnoreCase(tagArr[i]) || "indDob".equalsIgnoreCase(tagArr[i])){
					columnName.append(",TRSD_DOB");
				} else if("gender".equalsIgnoreCase(tagArr[i]) || "indGender".equalsIgnoreCase(tagArr[i])) {
					columnName.append(",TRSD_GENDER");
				} else if("name".equalsIgnoreCase(tagArr[i])) {
					columnName.append(",TRSD_NAME");
					value = value.replaceAll("'", "''");
				} else if((tagArr[i]).contains("nationality")) {
					columnName.append(",TRSD_NATIONALITY");
				} else if("passport".equalsIgnoreCase(tagArr[i])) {
					columnName.append(",TRSD_PASSPORTNO");
				} else if("nationalId".equalsIgnoreCase(tagArr[i])) {
					columnName.append(",TRSD_EIDANO");
				} else if("residenceCountry".equalsIgnoreCase(tagArr[i])) {
					columnName.append(",TRSD_RESIDENCE_COUNTRY");
				} else if((tagArr[i]).contains("CHANNEL_REFERENCE_NO")) {
					columnName.append(",TRSD_CLIENT_ID");
					insertionOrderId = value;
				}
				
				// sValues.append(",'"+value+"'");
				if("address1".equalsIgnoreCase(tagArr[i]) || "address2".equalsIgnoreCase(tagArr[i]) || "address3".equalsIgnoreCase(tagArr[i]) 
						|| "city".equalsIgnoreCase(tagArr[i]) || "state".equalsIgnoreCase(tagArr[i])|| "majortype".equalsIgnoreCase(tagArr[i])){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tag name not appending : " + tagArr[i]);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tag value not appending : " + value);
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tag name appending : " + tagArr[i]);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tag value appending : " + value);
					sValues.append(",'"+value+"'");
				}
			}
			StringBuilder query1 = new StringBuilder();

			/* sheenu if(batchId!=null?(Integer.parseInt(batchId)>1):false){
				if(!allTableNamesList.isEmpty()){
					for(String mainTableName :allTableNamesList){
						query1 =query1.append("select CHANNEL_REFERENCE_NO from "+mainTableName+" where winame='"+requestorWiName+"' union ");
					}

				}
				String query2 = query1.substring(0,(query1.length()-6));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"allTableNamesList : "
				+ allTableNamesList+" query1:"+query2);

				APCallCreateXML.APUpdate(tableName,"DISCARD_FLAG",
		    		 "'Y'"," WI_NAME  = '"+winame+"' and TRSD_CLIENT_ID not in ("+query2+")", sessionId);
             }*/
			columnName.append(",DISCARD_FLAG");
			sValues.append(",'N'");
			String outputXml = APCallCreateXML.APInsert(tableName, columnName.toString(), sValues.toString(), sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);
		}catch (Exception e) {
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}

	}
	private String executeFSK(String type,String entityType, StringBuilder tagList,ArrayList valueList,String columnList,String tablename){
		StringBuilder inputXml ;
		String trsdResponse = "";
		boolean[] caseFound;
		boolean trsdSuccess = true;
		String[] typeArr;
		String entityName = "";
		String field1 = "";
		String insertionOrderId ="";
		String type1="";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeFSK type " + type);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeFSK valueList :" + valueList);
			if("both".equalsIgnoreCase(type)){
				typeArr = new String[2];
				typeArr[0] = "INDIVIDUAL";
				typeArr[1] = "ORGANISATION";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeFSK inside if: " + typeArr.length);

			} else if("vessel".equalsIgnoreCase(type)){
				typeArr = new String[2];
				typeArr[0] = "VESSEL";
				typeArr[1] = "ORGANISATION";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeFSK inside if: " + typeArr.length);

			} else {
				typeArr = new String[1];
				typeArr[0] = type;
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeFSK inside type else: " + typeArr[0] );
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"executeFSK outside array size " + typeArr.length );
	
			 field1 = this.channelID;

			for(int counter1 = 0 ;counter1 < typeArr.length ; counter1++ ){
				for (int counter = 0 ;counter < valueList.size() ; counter++ ) 
				{
					String clientID ="";
					String fskRefNo =  LapsUtils.getInstance().getFSKRefNum();
					/*String clientID =  LapsUtils.getInstance().getFSKRefNum();
					clientID = clientID+"_"+entityType;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "clientID Length" + clientID.length());
					if(clientID.length()>21){
						clientID = clientID.substring(0, 21);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "clientID Value" + clientID);
					}*/
					String[] tags = tagList.toString().substring(0,tagList.length()-1).split(",");
					String[] values = valueList.get(counter).toString().split(String.valueOf((char)25),-1);
					String[] columnList1 =columnList.split(",");
					inputXml = new StringBuilder();
					
					if(typeArr[counter1].equalsIgnoreCase("INDIVIDUAL"))
					{
						type1 = "I";
					} 
					else if(typeArr[counter1].equalsIgnoreCase("VESSEL"))
					{
						type1 = "V";
					}
					else
					{
						type1 = "C";
					}
					refNo = LapsUtils.getInstance().generateSysRefNumber();
					
			    if (("COB".equalsIgnoreCase(requestorProcessName)) && (!(type.equalsIgnoreCase(requestTypeCOB))) 
			    		&& requestTypeCOB != null && !requestTypeCOB.equalsIgnoreCase("")) {
			    	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside if COB" + type);
			            break;
				}else{
					inputXml.append("<?xml version=\"1.0\"?>").append("\n")
					.append("<APWebService_Input>").append("\n")
					.append("<Option>WebService</Option>").append("\n")
					.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
					.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
					.append("<Calltype>WS-2.0</Calltype>").append("\n")
					.append("<InnerCallType>FSK_FetchNameSearching</InnerCallType>").append("\n")			
					.append("<WiName>" + WI_NAME + "</WiName>").append("\n")
					.append("<fetchNameSearchingReqMsg>").append("\n")
					.append("<usecaseID>" +1234+ "</usecaseID>").append("\n")
					.append("<serviceName>InqCustomerScan</serviceName>").append("\n")
					.append("<versionNo>1.0</versionNo>").append("\n")
					.append("<serviceAction>Inquiry</serviceAction>").append("\n")
					.append("<correlationID></correlationID>").append("\n")
					.append("<sysRefNumber>" + refNo + "</sysRefNumber>").append("\n")
					.append("<senderId>WMS</senderId>").append("\n")
					.append("<consumer>FSK</consumer>").append("\n")	
					.append("<reqTimeStamp>" +sDate+ "</reqTimeStamp>").append("\n")	
					.append("<repTimeStamp></repTimeStamp>").append("\n")	
					.append("<username></username>").append("\n")	
					.append("<credentials></credentials>").append("\n")	
					.append("<returnCode></returnCode>").append("\n")	
					.append("<errorDescription></errorDescription>").append("\n")	
					.append("<errorDetail></errorDetail>").append("\n")	
					.append("<fetchNameSearchingReq>").append("\n")
					.append("<flag>001</flag>").append("\n")
					.append("<caseCode>0</caseCode>").append("\n")
					.append("<format>ADCB_TRUST</format>").append("\n")
					.append("<messageId>" + refNo + "</messageId>").append("\n")
					.append("<entity>" + field1 + "</entity>").append("\n")
					.append("<type>"+type1+"</type>").append("\n");
				  }					
					//for tfo
					if("TFO".equalsIgnoreCase(requestorProcessName)){
					for(int j=0;j<columnList1.length;j++){
						
						if(columnList1[j].equalsIgnoreCase("INSERTIONORDERID")){
							insertionOrderId =values[j];
							break;
						}
					}
				    }
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Values length : " + values.length);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Tags length : " + tags.length);
					for (int i = 0;i < tags.length ; i++){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "No of I : " + i);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Tags Values : " + tags[i]);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "values[i] : " + values[i]);
						entityName = values[i];
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entityName[i] : " + entityName);
						if("majortype".equalsIgnoreCase(tags[i])){
							entityType ="MAJOR "+values[i].toUpperCase();
						} else {
						  inputXml.append("<"+tags[i]+">" + normalizeString(values[i]) + "</"+tags[i]+">").append("\n");
						}
						
						clientID = fskRefNo+"_"+entityType;
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "clientID Length" + clientID.length());
						if(clientID.length()>21){
							clientID = clientID.substring(0, 21);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "clientID Value" + clientID);
						}
					}
					inputXml.append("<clientId>"+clientID+"</clientId>").append("\n")
					.append("<workitemNumber>"+ requestorWiName +"</workitemNumber>").append("\n")
					.append("</fetchNameSearchingReq>").append("\n")
		            .append("</fetchNameSearchingReqMsg>").append("\n")
					.append("</APWebService_Input>");
					
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FSK inputXml : " + inputXml.toString());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FSK url : " + LapsConfigurations.getInstance().trsdUrl);
					insertTRSDXmlAudit(WI_NAME, inputXml.toString(), "Request", clientID.replaceAll("'", "''") + "", "S");
					saveFSKDetails(leadRefNumber, WI_NAME, requestorProcessName,clientID,tagList,valueList.get(counter).toString(), typeArr[counter1], entityType.replaceAll("'", "''"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "After FSK inputXml");
					try {		
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Before Socket execute: " );
						trsdResponse = LapsConfigurations.getInstance().socket.connectToSocket(inputXml.toString());
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FSK RESPONSE : " + trsdResponse); 
					}catch (Exception e) {
						trsdSuccess = false;
						trsdExecutionSuccess = false;
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "entityName[i] 2 : " + entityName);
					caseFound = updateFSKDetail(trsdResponse, WI_NAME, trsdSuccess, clientID + "",
							tablename,type,entityType,entityName,leadRefNumber,requestorProcessName,tagList,
							valueList.get(counter).toString(),typeArr[counter1],insertionOrderId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"$$" + caseFound[1] + "--" + caseFound[0] + "3439");
					if (caseFound[1] == true) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"///3442");
						trsdCaseError = true;
					} else if (caseFound[0] == true) {
						trsdCaseFound = true;
					}
				}
			}
		} catch (Exception e) {
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
		return trsdResponse;
	}
	
	public boolean[] updateFSKDetail(String Response, String winame, Boolean trsdSuccess, 
			String clientID,String tablename, String type, String entityType, String entityName,
			String leadRefNumber,String requestorProcessName, StringBuilder tagList,
			String valueList,String typeArr,String insertionOrderId) {

		boolean[] out = new boolean[2];
		out[0] = false;
		out[1] = false;
		String statusDescription = "";
		String case_num = "",case_code="";
		//String client_id = clientID;
		int case_num_found = 0;
		int statusCode = 0;
		String trsd_status = "";
		String trsd_status_desc = "";
		String returnCode = "";
		
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside updateFSKDetail: " + Response);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value for success : " + trsdSuccess);
			if(!(null == Response) && !"".equalsIgnoreCase(Response)){
				XMLParser xp = new XMLParser(Response);
				returnCode =xp.getValueOf("returnCode");
			}
			if (trsdSuccess && !(null == Response) && !"".equalsIgnoreCase(Response)&& !"1".equalsIgnoreCase(returnCode)
                && !"-1".equalsIgnoreCase(returnCode)) 
			{
				XMLParser xmlDataParser = new XMLParser();
				xmlDataParser.setInputXML(Response);

				//if (sysrefnofromrequest.equalsIgnoreCase(xmlDataParser.getValueOf("sysRefNumber")))
				//{
					statusDescription = xmlDataParser.getValueOf("statusDescription");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value for statusDescription" + statusDescription);
					statusCode = Integer.parseInt(xmlDataParser.getValueOf("statusCode"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value for statusCode" + statusCode);
					case_num = xmlDataParser.getValueOf("systemId");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value for case_num" + case_num);
					case_code = xmlDataParser.getValueOf("systemId");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"value for case_code" + case_code);

					//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert case_code case_num" + case_code + "$" + case_num + "$" + statusCode);
					if (statusCode == 99) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FSK Error");
						out[1] = true;
						trsd_status = "E";
						trsd_status_desc = "FSK Screening Error/Input Error";
						// break;
					} else if (statusCode == 01) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FSK System ID Created");
						trsd_status = "P";
						case_num_found = 1;
						trsd_status_desc = "Case Created and pending Investigation";
					} 
					else if (statusCode == 02 || statusCode == 03 )
					{
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FSK No Match");
						trsd_status = "N";
						trsd_status_desc = "No Match Found";
					}
				

					if("TFO".equalsIgnoreCase(requestorProcessName)){
						String sTable_info = tablename;
						String sColumn = "CASE_ID,CASE_NUMBER,TRSD_SCREENING_RESULT,channel_reference_no,execution_status";
						String sValues = "'" + case_code + "','" + case_num + "','"
								+ trsd_status_desc + "','"+clientID+"','"
								+"Y"+"'";
						String sWhere = "winame = N'" + requestorWiName
								+ "'  and upper(entity) = upper('"+entityType.replaceAll("'", "''")+"')"
								+ " and upper(trsd_screening_type) = upper('"+type+"')"
								+ " and insertionOrderId='"+insertionOrderId+"'"
								+ " and upper(ENTITY_NAME) = upper('"+entityName.replaceAll("'", "''")+"')";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sWhere==============="+sWhere);
						APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);
						String values = "'"+requestorWiName+"','"+tablename+"','"+entityType.replaceAll("'", "''''")+"','"+type+"','','S'";
						APCallCreateXML.APProcedure(sessionId, "BPM_SCREENING_UPDATE", values, 6);	
						
					}
					//save into trsd
					updateTRSDDetails(leadRefNumber, WI_NAME, requestorProcessName,clientID,
							tagList,valueList, typeArr, entityType.replaceAll("'", "''"));

					String sTable_info = "BPM_TRSD_DETAILS";
					String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
					String sValues = "'" + case_code + "','" + case_num + "','"
							+ trsd_status + "'";
					String sWhere = "wi_name = N'" + winame
							+ "' and trsd_client_id ='" + clientID.replaceAll("'", "''") + "'";
					APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);

					insertTRSDXmlAudit(winame, Response, "Response", clientID.replaceAll("'", "''") + "", trsd_status);
				//} 
				/*else {
					out[1] = true;
					trsd_status = "E";
					insertTRSDXmlAudit(winame, Response, "Response", client_id
							+ "", trsd_status);
					String sTable_info = "BPM_TRSD_DETAILS";
					String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
					String sValues = "'" + case_code + "','" + case_num + "','"
							+ trsd_status + "'";
					String sWhere = "wi_name = N'" + winame
							+ "' and trsd_client_id ='" + sysrefnofromrequest + "'";
					APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);
					if("TFO".equalsIgnoreCase(requestorProcessName)){
						sTable_info = tablename;
						sColumn = "CASE_ID,CASE_NUMBER,TRSD_SCREENING_RESULT,channel_reference_no";
						sValues = "'" + case_code + "','" + case_num + "','"
								+ "TRSD Screening Error/Input Error" + "','"+sysrefnofromrequest+"'";
						sWhere = "winame = N'" + requestorWiName
								+ "'  and upper(entity) = upper('"+entityType+"') and upper(trsd_screening_type) = upper('"+type+"')"
								+ " and upper(ENTITY_NAME) = upper('"+entityName.replaceAll("'", "''")+"')";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sWhere==============="+sWhere);
						APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);
						String values = "'"+requestorWiName+"','"+tablename+"','"+entityType+"','"+type+"','','S'";
						APCallCreateXML.APProcedure(sessionId, "BPM_SCREENING_UPDATE", values, 6);	
					}
					
				}*/
			} else {
				out[1] = true;
				trsd_status = "E";
				insertTRSDXmlAudit(winame, Response, "Response",
						clientID.replaceAll("'", "''") + "", trsd_status);
				String sTable_info = "BPM_TRSD_DETAILS";
				String sColumn = "TRSD_CASE_CODE,TRSD_CASE_NUM,TRSD_STATUS";
				String sValues = "'" + case_code + "','" + case_num + "','"
						+ trsd_status + "'";
				String sWhere = "wi_name = N'" + winame
						+ "' and trsd_client_id ='" + clientID.replaceAll("'", "''") + "'";
				APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);

				if("TFO".equalsIgnoreCase(requestorProcessName)){
					sTable_info = tablename;
					sColumn = "CASE_ID,CASE_NUMBER,TRSD_SCREENING_RESULT,channel_reference_no";
					sValues = "'" + case_code + "','" + case_num + "','"
							+ "TRSD Screening Error/Input Error" + "','"+clientID+"'";
					sWhere = "winame = N'" + requestorWiName
							+ "'  and upper(entity) = upper('"+entityType.replaceAll("'", "''")+"') and upper(trsd_screening_type) = upper('"+type+"')"
							+ " and upper(ENTITY_NAME) = upper('"+entityName.replaceAll("'", "''")+"')";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sWhere==============="+sWhere);
					APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);
					String values = "'"+requestorWiName+"','"+tablename+"','"+entityType.replaceAll("'", "''''")+"','"+type+"','','S'";
					APCallCreateXML.APProcedure(sessionId, "BPM_SCREENING_UPDATE", values, 6);	
				}
			}

			if (out[1]) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside status code check");
				// WFSetAttributes(winame, 1, "P");
				return out;
			} else if (case_num_found == 1) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside case num");
				// WFSetAttributes(winame, 1, "Y");
				out[0] = true;
				return out;
			}

		} catch (Exception ex) {
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Error in saveTRSDResponse(): " + ex.getMessage());
			out[0] = true;
			return out;
		}
		return out;
	}
	
	
	public String createNormalizedXML(String outputXml) {
		try {
			if (outputXml.length() > 4000) {
				int itr = outputXml.length() / 4000;
				int mod = outputXml.length() % 4000;
				if (mod > 0) {
					++itr;
				}
				StringBuilder response = new StringBuilder();
				try {
					for (int i = 0; i < itr; i++) {
						if (i == 0) {
							response.append("TO_NCLOB('"
									+ outputXml.substring(0, 4000) + "')");
						} else if (i < itr - 1) {
							response.append(" || TO_NCLOB('"
									+ outputXml.substring((4000 * i),
											4000 * (i + 1)) + "')");

						} else {
							response.append(" || TO_NCLOB('"
									+ outputXml.substring((4000 * i),
											outputXml.length()) + "') ");

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				outputXml = response.toString();
				return outputXml;
			} else {
				outputXml = "'" + outputXml + "'";
				return outputXml;
			}

		} catch (Exception ex) {
			ex.printStackTrace();	
		}
		return "";
	}
	private String normalizeString(String val) {
		return val.replace("&", "&amp;");
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sanction screening call: inside");
		String inputXml = "" , outputXml = "<returnCode>0</returnCode>";
		try {			
			//inputXml = createInputXML();
			if(!prevStageNoGo){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TRSD STATUS :" + callStatus);
				if("N".equalsIgnoreCase(callStatus)){
					outputXml= "<returnCode>1</returnCode><errorDescription>TRSD Service Error</errorDescription>";
				}
				responseHandler(outputXml, inputXml);
			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
		return outputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>TFO_InqCustPersonalDetails</InnerCallType>").append("\n")			
			.append("<WiName>" + WI_NAME + "</WiName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<cust_id>" + customerId + "</cust_id>").append("\n")			
			//.append("<txnType>" + txnType + "</txnType>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SS004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(returnCode == 0){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "SS090", "SS Successfully Executed", sessionId);
			} else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'SanctionScreening', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
				
			
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	public void addTRSDAudit(String winame, String leadRefNo, String status) {
		//log.debug("INSIDE insertTRSDAuditTrail");
		String LeadRefNo = "";
		try {
			//log.debug("in USR_0_WBG_AO_DEC_HIST");
			String sTable = "USR_0_WBG_AO_DEC_HIST";
			String sOutputXML = null;
			String sColumn = "WI_NAME,LEAD_REFNO,USERID,USERNAME,PRV_WS_NAME,WS_NAME,WS_DECISION,"
					+ "REJ_REASON,WS_COMMENTS,DEC_DATE_TIME,QUEUE_ENTRY_DATE_TIME";

			String sValues = "'" + winame + "','" + LeadRefNo
					+ "','TRSD_USER','TRSD_USER','','Introduction','" + status
					+ "','','',sysdate,sysdate";
			APCallCreateXML.APInsert(sTable, sColumn, sValues, sessionId);
			/*String sInputXML = "<?xml version=\"1.0\"?><APInsert_Input><Option>APInsert</Option><TableName>"
					+ sTable
					+ "</TableName>"
					+ "<ColName>"
					+ sColumn
					+ "</ColName>"
					+ "<Values>"
					+ sValues
					+ "</Values>"
					+ "<EngineName>"
					+ sCabinetName
					+ "</EngineName>"
					+ "<SessionId>"
					+ sUserDBId
					+ "</SessionId>"
					+ "</APInsert_Input>";

			// log.debug("sInputXML:"+sInputXML);
			sOutputXML = executeAPI(sInputXML);
			// log.debug("sOUTPUTXML:"+sOutputXML);
			 */
		} catch (Exception e) {
			//log.debug("Exception" + e.getMessage());
			e.printStackTrace();
		}
	}
	private void getRequestCategory(){
		try{
			String outputXMLLead = APCallCreateXML.APSelect("select request_category from tfo_dj_request_category_mast "
					+ "where request_category_id in (select request_category "
					+"from ext_tfo where wi_name='"+requestorWiName +"') and rownum=1");

			XMLParser xp1 = new XMLParser(outputXMLLead);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode1 == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getRequestCategory TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					requestCategory = xp1.getValueOf("request_category");					
				}

			} 
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
	}

	public void updateTRSDDetails(String leadRefNo, String winame, 
			String channelName, String ClientID,StringBuilder tagList, String valueList , String type, String entityType) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"in updateTRSDDetails ");
			String tableName = "bpm_trsd_details";
			StringBuilder query1 = new StringBuilder();

			if(batchId!=null?(Integer.parseInt(batchId)>1):false){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"in batchId "+batchId);
				if(!allTableNamesList.isEmpty()){
					for(String mainTableName :allTableNamesList){
						query1 =query1.append("select CHANNEL_REFERENCE_NO from "+mainTableName+" where winame='"+requestorWiName+"' union ");
					}

				}
				String query2 = query1.substring(0,(query1.length()-6));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"allTableNamesList : "
						+ allTableNamesList+" query1:"+query2);

				APCallCreateXML.APUpdate(tableName,"DISCARD_FLAG",
						"'Y'"," WI_NAME  = '"+winame+"' and TRSD_CLIENT_ID not in ("+query2+")", sessionId);
			}
		} catch (Exception e) {
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}

	}

	private String getTrsdFlagValue(String wiName){
		String output = "";
		int dataCount =0 ;
		boolean rejectStatus = false;
		boolean returnStatus = false;
		try {
			String outputXMLLead = APCallCreateXML.APSelect("SELECT  count(1) as dataCount from BPM_TRSD_DETAILS  WHERE WI_NAME in "
					+ " (select trsd_wi_name from ext_tfo where wi_name='"+wiName+"')"
					+" and (discard_flag='N' OR discard_flag is null) AND TRSD_CASE_NUM IS NOT NULL"
					+" and trsd_decision is  null");

			XMLParser xp1 = new XMLParser(outputXMLLead);
			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode1 == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"getRequestCategory TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					dataCount = Integer.parseInt(xp1.getValueOf("dataCount"));					
				}}

			if(dataCount>0){
				output = "Y";
				return output; 
			} else {
				outputXMLLead = APCallCreateXML.APSelect("SELECT  TRSD_DECISION from BPM_TRSD_DETAILS  WHERE WI_NAME in "
						+ " (select trsd_wi_name from ext_tfo where wi_name='"+wiName+"')"
						+" and (discard_flag='N' OR discard_flag is null) AND TRSD_CASE_NUM IS NOT NULL");
				
				xp1 = new com.newgen.omni.jts.cmgr.XMLParser(outputXMLLead);
				int start = xp1.getStartIndex("Records", 0, 0);
				int deadEnd = xp1.getEndIndex("Records", start, 0);
				int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"noOfFields ="+noOfFields );
				int end = 0;
				if (noOfFields > 0)
				{
					for (int i = 0; i < noOfFields; i++)
					{
						start = xp1.getStartIndex("Record", end, 0);
						end = xp1.getEndIndex("Record", start, 0);
						String trsdDecision = xp1.getValueOf("TRSD_DECISION", start, end);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "trsd_decision: "+trsdDecision);
						if(trsdDecision.equalsIgnoreCase("Reject")) {
							rejectStatus = true;
							break;
						} else if(trsdDecision.equalsIgnoreCase("Return")) {
							returnStatus = true; 
						}
					}
				}

				if ((!rejectStatus) && (!returnStatus)) {
					output = "A";
				} else if (rejectStatus) {
					output = "J"; 
				} else if (returnStatus) {
					output = "R";
				}
			}

		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
		}
		return output;
	}

	
	
	public void getCOBReqType(String requestorWiName){
		try {
			String outputXML = APCallCreateXML.APSelect("SELECT INTRO_REQUESTTYPE FROM EXT_COB WHERE TRSD_WI_NAME  = '" + WI_NAME + "'");
	        XMLParser xp = new XMLParser(outputXML);
	        int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
	        if (mainCode == 0) {
	          requestTypeCOB = xp.getValueOf("INTRO_REQUESTTYPE");
	          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INTRO_REQUESTTYPE : " + requestTypeCOB);
	          if ("Individual".equalsIgnoreCase(requestTypeCOB)){
	            requestTypeCOB = "INDIVIDUAL";
	          }else if (("Corporate".equalsIgnoreCase(requestTypeCOB)) || ("Bank & Insurance".equalsIgnoreCase(requestTypeCOB))) {
	            requestTypeCOB = "ORGANISATION";
	          }
	          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "requestTypeCOB : " + requestTypeCOB);
	        }
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "SS100", e.getMessage(), sessionId);
	   }
	}
	
	
	 public void saveFSKDetails(String leadRefNo, String winame, String channelName, String ClientID, StringBuilder tagList, String valueList, String type, String entityType) {
		    try {
		      LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside saveFSKDetails : ");
		      String tableName = "bpm_trsd_details";
		      String insertionOrderId = "";
		      StringBuilder columnName = new StringBuilder();
		      StringBuilder sValues = new StringBuilder();
		      columnName.append("LEAD_REFNO,BATCH_ID, WI_NAME, TRSD_CHANNEL_NAME, TRSD_STATUS, TRSD_CLIENT_ID, TRSD_TYPE, TRSD_ENTITY_TYPE, TRSD_SCREENING_DATE");
		      sValues.append("'" + this.leadRefNumber + "','" + this.batchId + "','" + winame + "','" + channelName + "','N','" + ClientID.replaceAll("'", "''") + "','" + type + "','" + 
		          entityType + "', SYSDATE");
		      String[] tagArr = tagList.toString().split(",");
		      for (int i = 0; i < tagArr.length; i++) {
		        String value = valueList.split(String.valueOf((char)25), -1)[i];
		        if ("dateOfBirth".equalsIgnoreCase(tagArr[i]) || "indDob".equalsIgnoreCase(tagArr[i])) {
		          columnName.append(",TRSD_DOB");
		        } else if ("gender".equalsIgnoreCase(tagArr[i]) || "indGender".equalsIgnoreCase(tagArr[i])) {
		          columnName.append(",TRSD_GENDER");
		        } else if ("name".equalsIgnoreCase(tagArr[i])) {
		          columnName.append(",TRSD_NAME");
		          value = value.replaceAll("'", "''");
		        } else if (tagArr[i].contains("nationality")) {
		          columnName.append(",TRSD_NATIONALITY");
		        } else if ("passport".equalsIgnoreCase(tagArr[i])) {
		          columnName.append(",TRSD_PASSPORTNO");
		        } else if ("nationalId".equalsIgnoreCase(tagArr[i])) {
		          columnName.append(",TRSD_EIDANO");
		        } else if ("residenceCountry".equalsIgnoreCase(tagArr[i])) {
		          columnName.append(",TRSD_RESIDENCE_COUNTRY");
		        } else if (tagArr[i].contains("CHANNEL_REFERENCE_NO")) {
		          columnName.append(",TRSD_CLIENT_ID");
		          insertionOrderId = value;
		        } 
		        if ("address1".equalsIgnoreCase(tagArr[i]) || "address2".equalsIgnoreCase(tagArr[i]) || "address3".equalsIgnoreCase(tagArr[i]) || 
		          "city".equalsIgnoreCase(tagArr[i]) || "state".equalsIgnoreCase(tagArr[i]) || "majortype".equalsIgnoreCase(tagArr[i])) {
		          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Tag name not appending : " + tagArr[i]);
		          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Tag value not appending : " + value);
		        } else {
		          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Tag name appending : " + tagArr[i]);
		          LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Tag value appending : " + value);
		          sValues.append(",'" + value + "'");
		        } 
		      } 
		      StringBuilder query1 = new StringBuilder();
		      columnName.append(",DISCARD_FLAG");
		      sValues.append(",'N'");
		      String outputXml = APCallCreateXML.APInsert(tableName, columnName.toString(), sValues.toString(), this.sessionId);
		      LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml :insertTRSDXmlAudit " + outputXml);
		    } catch (Exception e) {
		      this.callStatus = "N";
		      LapsModifyLogger.logMe(2, e);
		      LapsModifyDBLogger.logMe(2, this.WI_NAME, this.auditCallName, "SS100", e.getMessage(), this.sessionId);
		    } 
		 }
	 
}
