package com.newgen.ao.laps.sourcingchannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.request.LapsModifyRequest;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class DM_PROTRADE implements SourcingChannelHandler{
	
	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap = new HashMap<String, String>();
	public LapsModifyRequest request;
	private int processDefId;
	XMLParser xp = new XMLParser();
	int stageId = 2;
	int mainCode = -1;
	String status = "0";
	String statusDesc = "Calls Executed Successfully";
	String requestCategory ="";
	String requestType="";
	String wiNumber;
	
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber, String correlationNo, 
			String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside DM_PROTRADE >>");
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
			String journeyType = sourcingChannel;
			this.wiNumber=wiNumber;
			String processType;
			Map<String, String> fetchMap = ChannelCallCache.getInstance().getFetchMap();
			String outputXml = APCallCreateXML.APSelect("select PROCESS_TYPE, REQUEST_CATEGORY, REQUEST_TYPE from EXT_TFO where "
					+ "WI_NAME='"+wiNumber+"'");
			XMLParser xp = new XMLParser(outputXml);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
				requestCategory = xp.getValueOf("REQUEST_CATEGORY");
				requestType = xp.getValueOf("REQUEST_TYPE");
				processType = xp.getValueOf("PROCESS_TYPE");
				mainCode = -1;
				String key = processType + "#" + requestCategory + "#" + requestType;
				if(fetchMap.get(key) != null && "CUSTOMER_ID".equalsIgnoreCase(fetchMap.get(key))) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"journey type is issuance >>");
					journeyType = "ISSUANCE";
				}
				/*outputXml = APCallCreateXML.APSelect("select FIELD_NAME from TFO_DJ_RECEPTION_FETCH_MAST where "
						+ "REQ_CAT_CODE='"+requestCategory+"' and REQ_TYPE_CODE='"+requestType+"' and PROCESS_TYPE='BAU'");
				xp = new XMLParser(outputXml);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
					int end = 0;
					for (int i = 0; i < noOfFields; ++i) {
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						String fetchFieldName = xp.getValueOf("FIELD_NAME", start, end);
						if(fetchFieldName != null && "CID_Txt".equalsIgnoreCase(fetchFieldName)) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"journey type is issuance >>");
							journeyType = "ISSUANCE";
							break;
						}
					}
				}*/
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Invalid Workitem Number");
				resp.setStatusCode("-1");
				resp.setStatusDescription("Invalid Workitem Number");
				return resp;
			}
			attributeMap.put("mode", mode);
			attributeMap.put("ruleFlag", "N");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DM_PROTRADE attributeMap: "+attributeMap.toString());
			HashMap<Integer, Integer> eventMap = LapsUtils.getInstance().getEventDetector
					(processDefId, sourcingChannel, "1.0", journeyType,stageId);
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			List<CallEntity> callArray = ProcessEventCache.getReference().getEventCallList(eventID);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "eventID: " + eventID);
			if(callArray != null) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Call_Array: " + wiNumber + ":" + callArray.toString());
				boolean nogoCall = false;
				for (CallEntity callEntity : callArray) {
					if(callEntity.isMandatory()){
						String outputXML = CallHandler.getInstance().executeCall
								(callEntity, attributeMap, sessionId, String.valueOf(eventID), 
										wiNumber, false);
						xp = new XMLParser(outputXML);
						if("0".equalsIgnoreCase(status)){
							status = xp.getValueOf("returnCode");
						}
						if(!"0".equals(status)){
							statusDesc = "Calls Not Executed Successfully";
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Execute Call: outputxml: "
								+ outputXML);
					}
				}
			}
			
			//ATP-383 20-01-2024 Jamshed Starts
			updateLimitLineDetails(mode,sessionId);
			//ATP-383 20-01-2024 Jamshed Ends
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"DM_PROTRADE: status= "+status+"; statusDesc= "
					+statusDesc);
			String outputXML = APCallCreateXML.APSelect("select WORKITEMID from WFINSTRUMENTTABLE "
					+ "where PROCESSINSTANCEID = '"+wiNumber+"' and INTRODUCEDAT = 'Distribute1'"
							+ " and ACTIVITYNAME = 'ToDoList'");
			XMLParser xp3 = new XMLParser(outputXML);
			int wrkitmId = 0;
			if (Integer.parseInt(xp3.getValueOf("MainCode")) == 0) {
				wrkitmId = Integer.parseInt(xp3.getValueOf("WORKITEMID"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId from WFINSTRUMENTTABLE: " + wrkitmId);
			}
			if("0".equalsIgnoreCase(status)){
				if(wrkitmId != 0 && wrkitmId != 1){
					APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP,ROUTE_TO_REPAIR", 
							"'Customer Referral Response',''", "WI_NAME = '"+wiNumber+"'", sessionId);
					ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, wrkitmId);
				}
			} else {
				APCallCreateXML.APUpdate("EXT_TFO", "ROUTE_TO_REPAIR", "'Y'", "WI_NAME = '"+wiNumber+"'", sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, wrkitmId);
			}
			resp.setWiNumber(wiNumber);
			resp.setStatusCode(status);
			resp.setStatusDescription(statusDesc);
			
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in DM_PROTRADE dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returning response from DM_PROTRADE");
		return resp;
	}
	
	//ATP-383 20-01-2024 Jamshed start
		public void updateLimitLineDetails(String mode, String sessionId) {		
			try{

				StringBuffer strColumns2= new StringBuffer();
				StringBuffer strValues2 = new StringBuffer();
				String output="";
				strColumns2.append("WI_NAME,LT_DOC_RVW_GDLINES,LT_RESPONSE,INSERTIONORDERID");
					
				//GENERATE INSERTION ORDER ID
				String insertionorderID="";
				String tablName="TFO_DJ_LMT_CRDT_RECORDS";
				
				//DELETE EXISTING DATA IF MODE IS M
				String whereClause ="WI_NAME = '"+wiNumber+ "'";
				if(mode.equalsIgnoreCase("DM")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Before delete---: M CASE");
					APCallCreateXML.APDelete(tablName, whereClause, sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "After delete---: M CASE");
				}
				
				String sQuery = APCallCreateXML.APSelect("SELECT IS_100PCT_CASHBACK,LL_CODE FROM EXT_TFO WHERE WI_NAME = '"+wiNumber+"'");

				XMLParser xpParser = new XMLParser(sQuery);
				String is_100pct_cshbk=xpParser.getValueOf("IS_100PCT_CASHBACK");
				String ll_code=xpParser.getValueOf("LL_CODE");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside RequestCategory:"+requestCategory );
				
				if(is_100pct_cshbk !=null && !is_100pct_cshbk.equalsIgnoreCase("") && is_100pct_cshbk.equalsIgnoreCase("True")) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: TRUE CASE");
					if(requestCategory.equalsIgnoreCase("GRNT") && requestType.equalsIgnoreCase("NI")) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: GUARANTEE ISSUANCE CASE");
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Please select Collateral details."+"','CASH MARGIN',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
						
						strValues2 = new StringBuffer();
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Please input Cash Margin percentage."+"','100',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
						
						strValues2 = new StringBuffer();
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Please specify Limit line."+"','"+ll_code+"',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
					}
					else if((requestCategory.equalsIgnoreCase("SBLC") && requestType.equalsIgnoreCase("SBLC_NI"))
							|| (requestCategory.equalsIgnoreCase("ILC") && requestType.equalsIgnoreCase("ILC_NI"))) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: SBLC & ILC ISSUANCE CASE");
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Specify Collateral Details"+"','CASH MARGIN',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
						
						strValues2 = new StringBuffer();
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Specify Cash Margin Percentage"+"','100',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
						
						strValues2 = new StringBuffer();
						insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
						strValues2.append("'"+wiNumber+"','"+"Specify Limit line"+"','"+ll_code+"',"+insertionorderID);
						output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
					}
					
				} else if(is_100pct_cshbk !=null && !is_100pct_cshbk.equalsIgnoreCase("") && is_100pct_cshbk.equalsIgnoreCase("False")) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: FALSE CASE");
					if(requestCategory.equalsIgnoreCase("GRNT") && requestType.equalsIgnoreCase("NI")) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: GUARANTEE ISSUANCE CASE");						
						strValues2 = new StringBuffer();
						if(ll_code !=null && !ll_code.equalsIgnoreCase("")){
							insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
							strValues2.append("'"+wiNumber+"','"+"Please specify Limit line."+"','"+ll_code+"',"+insertionorderID);
							output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
						}
						
					}
					else if((requestCategory.equalsIgnoreCase("SBLC") && requestType.equalsIgnoreCase("SBLC_NI"))
							|| (requestCategory.equalsIgnoreCase("ILC") && requestType.equalsIgnoreCase("ILC_NI"))) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "is_100pct_cshbk: SBLC & ILC ISSUANCE CASE");						
						strValues2 = new StringBuffer();
						if(ll_code !=null && !ll_code.equalsIgnoreCase("")){
							insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
							strValues2.append("'"+wiNumber+"','"+"Specify Limit line"+"','"+ll_code+"',"+insertionorderID);
							output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
						}
					}
					
				}
				
	
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}


}
