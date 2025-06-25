package com.newgen.ao.laps.sourcingchannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.wf.util.xml.XMLParser;

public class C_SANCTION_SCREENING_VALIDATION implements SourcingChannelHandler{
	Map<String, ArrayList<String>> processMap = new HashMap();
	public LapsModifyResponse resp = new LapsModifyResponse();

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId,String channelRefNumber, String correlationNo,
			String sourcingChannel, String mode, String wiNumber,String processName) {
		
		int mainCode;
		String tableName = "";
		String executionStatus = "";
		boolean caseFoundFlag = false;
		String screeningProcess = "";
		String reqWiName = "";
		String trsd_decision = "";
		boolean batchStatus = true;
		boolean rejectStatus = false;
		boolean returnStatus = false;
		boolean recheck  = true;
		String outputXML="";
		String winame="";
		String flag="";
		String currWS="";
		String isTrsdCompleteFlag = "";
		String sourChannelWBG = null; //to check ITQAN or BCA
		int count;
		
		//if (mainCode == 0){
			try{
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "*********Inside C_Sanction_Screening_Valiadation***********");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "*********dispatchEvent***********");
			processMap = ChannelCallCache.getInstance().getProcessValuesMapMap(); //newly added
			ArrayList<String> value = new ArrayList();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "processName : " + processName);
			value = (ArrayList)processMap.get(processName);
			
			if("WBG".equalsIgnoreCase(processName)){
				outputXML = APCallCreateXML.APSelect("SELECT IS_TRSD_COMPLETE,CURR_WS,SOURCING_CHANNEL  FROM "
						+ (String)value.get(0)+" WHERE wi_name   = '"+wiNumber+"' ");
				
				com.newgen.omni.jts.cmgr.XMLParser xpn = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);//07/12/21
				isTrsdCompleteFlag = xpn.getValueOf("IS_TRSD_COMPLETE");
				sourChannelWBG = xpn.getValueOf("SOURCING_CHANNEL");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isTrsdCompleteFlag"
						+ " : " + isTrsdCompleteFlag+" sourChannelWBG : "+sourChannelWBG);
             } else {
				outputXML = APCallCreateXML.APSelect("SELECT IS_TRSD_COMPLETE,CURR_WS  FROM "
						+ (String)value.get(0)+" WHERE wi_name   = '"+wiNumber+"' ");
				sourChannelWBG ="TFO";
				com.newgen.omni.jts.cmgr.XMLParser xpn = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);//07/12/21
				isTrsdCompleteFlag = xpn.getValueOf("IS_TRSD_COMPLETE");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isTrsdCompleteFlag : " + isTrsdCompleteFlag);
             }
		
			if(processMap.size() >0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ProcessMap not empty");
			}else{
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "ProcessMap is empty");
			}
			
			
			 if("WBG".equalsIgnoreCase(processName) && "ITQAN".equalsIgnoreCase(sourChannelWBG)){
				 callTrsdItqanCase(value, wiNumber, sessionId,isTrsdCompleteFlag);
			 } 
			 else
			 {
				outputXML = APCallCreateXML.APSelect("SELECT  WI_NAME,TRSD_DECISION from BPM_TRSD_DETAILS  WHERE WI_NAME in "
							+ " (select trsd_wi_name from "+(String)value.get(0)+" where wi_name='"+wiNumber+"')"
							+" and (discard_flag='N' OR discard_flag is null) AND TRSD_CASE_NUM IS NOT NULL");
				com.newgen.omni.jts.cmgr.XMLParser xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML); 
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS noOfFields: " + noOfFields);
				int end = 0;
				if (noOfFields > 0)
				{
					for (int i = 0; i < noOfFields; i++)
					{
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						if (i == 0)
						{
							winame = xp.getValueOf("WI_NAME", start, end);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS winame: " + winame);
							String outputXML111 = APCallCreateXML.APSelect("SELECT REQUESTOR_PROCESS_NAME,REQUESTOR_WI_NAME FROM EXT_SS WHERE WI_NAME = N'" + winame + "'");
							XMLParser xpp = new XMLParser(outputXML111);
							processName = xpp.getValueOf("REQUESTOR_PROCESS_NAME");
							reqWiName = xpp.getValueOf("REQUESTOR_WI_NAME");
						}
						trsd_decision = xp.getValueOf("TRSD_DECISION", start, end);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS trsd_decision: " + trsd_decision);
						if (trsd_decision.equalsIgnoreCase("")) {
							batchStatus = false;
						} else if (trsd_decision.equalsIgnoreCase("Reject")) {
							rejectStatus = true;
						} else if (trsd_decision.equalsIgnoreCase("Return")) {
							returnStatus = true;
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS batchStatus: " + batchStatus);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS rejectStatus: " + rejectStatus);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS returnStatus: " + returnStatus);
						if ((!batchStatus) && ((rejectStatus) || (returnStatus))) {
							break;
						}
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS batchStatus_: "+batchStatus);
					if (batchStatus)
					{
						String outputXml=APCallCreateXML.APSelect("Select WORKITEMSTATE,ACTIVITYNAME from wfinstrumenttable "
								+ "where PROCESSINSTANCEID = '"+wiNumber+"'");
						com.newgen.omni.jts.cmgr.XMLParser xpl = new com.newgen.omni.jts.cmgr.XMLParser(outputXml);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"WorkitemState = " +xpl.getValueOf("WORKITEMSTATE")
								+ "ACTIVITYNAME" + xpl.getValueOf("ACTIVITYNAME")
										+ "********WI :: "+wiNumber);
						 if(processName.equals("TFO") && "3".equalsIgnoreCase(xpl.getValueOf("WORKITEMSTATE")) &&
									"Distribute2".equalsIgnoreCase(xpl.getValueOf("ACTIVITYNAME"))){
							 outputXML=APCallCreateXML.APSelect("select queueid from queuedeftable where queuename = 'TFO_PROCESSING MAKER'");
								com.newgen.omni.jts.cmgr.XMLParser xp1 = new com.newgen.omni.jts.cmgr.XMLParser(outputXML); 
								xp1 = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
								outputXML=APCallCreateXML.APSelect("select activityid from activitytable where activityname ='PROCESSING MAKER'");
								com.newgen.omni.jts.cmgr.XMLParser xp2 = new com.newgen.omni.jts.cmgr.XMLParser(outputXML); 
								xp2 = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
								APCallCreateXML.APUpdate("wfinstrumenttable", "STATENAME,ACTIVITYNAME,ACTIVITYID,ASSIGNMENTTYPE,"
										+ "COLLECTFLAG,Q_QUEUEID,WORKITEMSTATE,ACTIVITYTYPE,routingstatus,LOCKSTATUS,QUEUENAME,QUEUETYPE",
										"'NOTSTARTED','PROCESSING MAKER','"+xp2.getValueOf("activityid")+"','S','N',"
										+ "'"+xp1.getValueOf("queueid")+"','1','10','N','N','TFO_PROCESSING MAKER','N'", 
										"PROCESSINSTANCEID=N'"+wiNumber+"'", sessionId);
						 }
						if ((!rejectStatus) && (!returnStatus)) {
							flag = "A";
						} else if (rejectStatus) {
							flag = "J"; 
						} else if (returnStatus) {
							flag = "R";
						}
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "map " + processMap);
					
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS flag: " + flag);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside doActionSS value: " + value);
						
						outputXML = APCallCreateXML.APUpdate((String)value.get(0), 
								(String)value.get(1), "'" + flag + "'", " WI_NAME = N'" + reqWiName + "'", sessionId);
						xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "MainCode " + mainCode+"value.get(2) :"+(String)value.get(2));
						if ((mainCode == 0) && ("Y".equals(((String)value.get(2)).toString())))
						{
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside complete workitem loop: ");
							//boolean returnFlag = true;
							int workitemId = 0;
							String activityName = "";
							if(processName.equalsIgnoreCase("WBG")){
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop 1: ");
								workitemId = 1;
								recheck =false;
							} else {
								if(processName.equals("WBG_AO") ||processName.equals("WBG")){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop if 2: ");
									outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
											+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD SCREENING'");										
								} else {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop if else 2: ");
									outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
											+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD_ACTION'");
								}
								
								xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
								try {
									count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " TotalRetrieved: "+count);
									if(count > 0){
										workitemId = Integer.parseInt(xp.getValueOf("WORKITEMID"));
										recheck = false;
									}										
								} catch(Exception e){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, reqWiName+": Workitem Data not in WFInstrumentTable");
									//return true;  Need Confirmation
								}
							}								
							
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Completing workitem doActionSS workitemName: "
							+reqWiName+" workitemId: "+workitemId);
							if(recheck){ // condition to complete workitem if match case found and trsd decision empty
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sleeping for 5 seconds to recheck TRSD ACTION");
								Thread.sleep(5000L);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "recheck:"+recheck+"processName:"+processName);
	
								if(processName.equalsIgnoreCase("WBG_AO")||processName.equalsIgnoreCase("WBG")){
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop");
									outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
											+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD SCREENING'");										
								} else {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "else loop");
									outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
											+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD_ACTION'");
								}
							
								xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
								count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
								if(count > 0){
									workitemId = Integer.parseInt(xp.getValueOf("WORKITEMID"));
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Completing workitem doActionSS if workitemName: "
											+reqWiName+" workitemId: "+workitemId);
									if(isTrsdCompleteFlag.equalsIgnoreCase("Y")){
										ProdCreateXML.WMCompleteWorkItem(sessionId, reqWiName, workitemId);
	                                  } else{
										tableName ="BPM_EVENTGEN_TXN_DATA";
										String sColumn ="insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
												+ "SOURCING_CHANNEL, REQUESTMODE";
										String sValues = "SYSDATE,'"+reqWiName+"',(SYSDATE+(5/(24*60))),'N','"+processName+"','SANCTION_SCREENING_VALIDATION','C'";
										
										outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);
	
	                              }
								} else {
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, reqWiName+": Workitem Not found in TRSD WS");
								
								}
								
							} else {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Completing workitem doActionSS else workitem : "
										+reqWiName+" workitemId: "+workitemId);
								if(isTrsdCompleteFlag.equalsIgnoreCase("Y")){
									ProdCreateXML.WMCompleteWorkItem(sessionId, reqWiName, workitemId);
								} else{
									tableName ="BPM_EVENTGEN_TXN_DATA";
									String sColumn ="insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
											+ "SOURCING_CHANNEL, REQUESTMODE";
									String sValues = "SYSDATE,'"+reqWiName+"',(SYSDATE+(5/(24*60))),'N','"+processName+"','SANCTION_SCREENING_VALIDATION','C'";
									
									 outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
									LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);
	
								}
							}
						}
					}else{
					//SET TRSD_FLAG Y IN EXT_TFO
					APCallCreateXML.APUpdate((String)value.get(0), (String)value.get(1) , "'Y'", " ", sessionId);
					}
					
				}
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		//}
			resp.setStatusCode("0");
		return resp;
	}
	
	public void callTrsdItqanCase(ArrayList<String> value,String wiNumber,String sessionId,String isTrsdCompleteFlag){
		int mainCode;
		String tableName = "";
		String reqWiName = "";
		String trsd_decision = "";
		boolean batchStatus = true;
		boolean rejectStatus = false;
		boolean returnStatus = false;
		boolean recheck  = true;
		String outputXML="";
		String winame="";
		String flag="";
		String currWS="";
		String sourChannelWBG = null; //to check ITQAN or BCA
		int count;
		String processName = "";
		
		
		try{
		/*	outputXML = APCallCreateXML.APSelect("SELECT  WI_NAME,TRSD_DECISION from BPM_TRSD_DETAILS  WHERE WI_NAME in "
					+ " (select trsd_wi_name from "+(String)value.get(0)+" where wi_name='"+wiNumber+"')"
					+" and (discard_flag='N' OR discard_flag is null) AND TRSD_CASE_NUM IS NOT NULL");
			*/
			outputXML = APCallCreateXML.APSelect("SELECT  TRSD_DECISION,TRSD_CHANNEL_NAME  "
					+ "from usr_0_wbg_ao_trsd_info  where  wi_name='"+wiNumber+"' "
					+ "and TRSD_CASE_NUM IS NOT NULL");

		com.newgen.omni.jts.cmgr.XMLParser xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML); 
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase noOfFields: " + noOfFields);
		int end = 0;
		if (noOfFields > 0)
		{
			for (int i = 0; i < noOfFields; i++)
			{
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				if (i == 0)
				{
				    processName = xp.getValueOf("TRSD_CHANNEL_NAME", start, end);;
					reqWiName = wiNumber;
				}
				 trsd_decision = xp.getValueOf("TRSD_DECISION", start, end);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase trsd_decision: " + trsd_decision);
				if (trsd_decision.equalsIgnoreCase("")) {
					batchStatus = false;
				} else if (trsd_decision.equalsIgnoreCase("Reject")) {
					rejectStatus = true;
				} else if (trsd_decision.equalsIgnoreCase("Return")) {
					returnStatus = true;
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase batchStatus: " + batchStatus);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase rejectStatus: " + rejectStatus);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase returnStatus: " + returnStatus);
				if ((!batchStatus) && ((rejectStatus) || (returnStatus))) {
					break;
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase batchStatus: "+batchStatus);
			if (batchStatus)
			{
				if ((!rejectStatus) && (!returnStatus)) {
					flag = "A";
				} else if (rejectStatus) {
					flag = "J"; 
				} else if (returnStatus) {
					flag = "R";
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "map " + processMap);
			
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase flag: " + flag);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside callTrsdItqanCase value: " + value);
				
				outputXML = APCallCreateXML.APUpdate((String)value.get(0), 
						(String)value.get(1), "'" + flag + "'", " WI_NAME = N'" + reqWiName + "'", sessionId);
				xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "MainCode " + mainCode+"value.get(2) :"+(String)value.get(2));
				if ((mainCode == 0) && ("Y".equals(((String)value.get(2)).toString())))
				{
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside complete workitem loop: ");
					//boolean returnFlag = true;
					int workitemId = 0;
					String activityName = "";
					if(processName.equalsIgnoreCase("WBG")){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop 1: ");
						workitemId = 1;
						recheck =false;
					} else {
						if(processName.equals("WBG_AO") ||processName.equals("WBG")){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop if 2: ");
							outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
									+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD SCREENING'");										
						} else {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop if else 2: ");
							outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
									+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD_ACTION'");
						}
						
						xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
						try {
							count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " TotalRetrieved: "+count);
							if(count > 0){
								workitemId = Integer.parseInt(xp.getValueOf("WORKITEMID"));
								recheck = false;
							}										
						} catch(Exception e){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, reqWiName+": Workitem Data not in WFInstrumentTable");
							//return true;  Need Confirmation
						}
					}								
					
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Completing workitem doActionSS workitemName: "
					+reqWiName+" workitemId: "+workitemId);
					if(recheck){ // condition to complete workitem if match case found and trsd decision empty
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Sleeping for 5 seconds to recheck TRSD ACTION");
						Thread.sleep(5000L);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "recheck:"+recheck+"processName:"+processName);

						if(processName.equalsIgnoreCase("WBG_AO")||processName.equalsIgnoreCase("WBG")){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "in loop");
							outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
									+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD SCREENING'");										
						} else {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "else loop");
							outputXML = APCallCreateXML.APSelect("SELECT WORKITEMID FROM "
									+ "wfinstrumenttable WHERE PROCESSINSTANCEID  = '"+reqWiName+"' AND ACTIVITYNAME = 'TRSD_ACTION'");
						}
					
						xp = new com.newgen.omni.jts.cmgr.XMLParser(outputXML);
						count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
						if(count > 0){
							workitemId = Integer.parseInt(xp.getValueOf("WORKITEMID"));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Completing workitem doActionSS if workitemName: "
									+reqWiName+" workitemId: "+workitemId);
							if(isTrsdCompleteFlag.equalsIgnoreCase("Y")){
								ProdCreateXML.WMCompleteWorkItem(sessionId, reqWiName, workitemId);
                              } else{
								tableName ="BPM_EVENTGEN_TXN_DATA";
								String sColumn ="insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
										+ "SOURCING_CHANNEL, REQUESTMODE";
								String sValues = "SYSDATE,'"+reqWiName+"',(SYSDATE+(5/(24*60))),'N','"+processName+"','SANCTION_SCREENING_VALIDATION','C'";
								
								String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);

                          }
						} else {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, reqWiName+": Workitem Not found in TRSD WS");
						
						}
						
					} else {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Completing workitem doActionSS else workitem : "
								+reqWiName+" workitemId: "+workitemId);
						if(isTrsdCompleteFlag.equalsIgnoreCase("Y")){
							ProdCreateXML.WMCompleteWorkItem(sessionId, reqWiName, workitemId);
						} else{
							tableName ="BPM_EVENTGEN_TXN_DATA";
							String sColumn ="insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
									+ "SOURCING_CHANNEL, REQUESTMODE";
							String sValues = "SYSDATE,'"+reqWiName+"',(SYSDATE+(5/(24*60))),'N','"+processName+"','SANCTION_SCREENING_VALIDATION','C'";
							
							String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);

						}
					}
				}
			}else{
				//SET TRSD_FLAG Y IN EXT_TFO
				APCallCreateXML.APUpdate((String)value.get(0), (String)value.get(1) , "'Y'", " ", sessionId);
			}
			
		}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in callTrsdItqanCase: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
}
