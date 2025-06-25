package com.newgen.ao.laps.sourcingchannel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;

import com.newgen.ao.laps.cache.EACache;
import com.newgen.ao.laps.cache.EADecisionMaster;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_EA implements SourcingChannelHandler{

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId,
			String channelRefNumber, String correlationNo,
			String sourcingChannel, String mode, String wiNumber,
			String processName) {
		LapsModifyResponse response = new LapsModifyResponse();
		HashMap<String, Integer> processMap = EACache.getInstance().getProcessdefMap();		
		EADecisionMaster ead = EACache.getInstance().getflowDecMast(9);
		//		EADecisionMaster ead = EACache.getInstance().getflowDecMast(10);  		//FOR PRODUCTION
		String decCode = "";
		String workItemNumber = "";
		String outputXML;
		try {
			outputXML = APCallCreateXML.APSelect("SELECT "+ ead.getDecisionColumnName() +" FROM "+ead.getDecisionTableName()+" WHERE WI_NAME = N'" + wiNumber + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					decCode = xp.getValueOf(ead.getDecisionColumnName());					
				}
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlow decCode :" + decCode);
			//			ArrayList<EAFlow> eaFlow = EACache.getInstance().getEAFlowMap(9, decCode);
			//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlow eaFlow :" + eaFlow);
			JSONArray ja = new JSONArray();
			//			for(EAFlow ef:eaFlow){
			//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlow ef :" + ef);
			//				JSONObject jo = new JSONObject();
			//				jo.put("LEVEL_SEQ",ef.getLevel());
			//				jo.put("MAKER_USERID",ef.getUserID());
			//				jo.put("MAKER_USERNAME",ef.getUserName());
			//				jo.put("MAKER_EMAIL",ef.getEmail());
			//				jo.put("EXECUTION_FLAG", "N");
			//				jo.put("EMAIL_STATUS", "PENDING");
			//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlow JSONObject jo :" + jo);
			//				ja.put(jo);
			//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EAFlow JSONArray ja :" + ja);
			//			}

			HashMap<String, String> attributeMap = new HashMap<>();
			attributeMap.put("PARENT_PROCESS_NAME",processName);
			attributeMap.put("PARENT_WI_NAME",wiNumber);
			String activityName = "";
			String wID = "";
			String sQuery = "select ACTIVITYNAME, workitemid from wfinstrumenttable where processinstanceid = '"+wiNumber+"'";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sQuery :: "+sQuery);
			outputXML = APCallCreateXML.APSelect(sQuery);
			xp = new XMLParser(outputXML);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"APSelect outputXML :: "+outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in  ACTIVITYNAME, WORKITEMID : "
						+noOfFields);
				for (int i = 0; i < noOfFields; ++i) {
					String Record = xp.getNextValueOf("Record");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Record :"+Record);
					XMLParser xp2 = new XMLParser(Record); 
					activityName = xp2.getValueOf("ACTIVITYNAME");
					if(activityName.equalsIgnoreCase("ITMAM ANALYST") ||
							activityName.equalsIgnoreCase("CBG ANALYST") || activityName.equalsIgnoreCase("ANALYST_CHECKER")){
						wID = xp2.getValueOf("WORKITEMID");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"activityName:: "+activityName+"  wID:: " + wID);
					}
				}
			}
			attributeMap.put("PARENT_WID",wID);
			try{
				//int processDefId=30;
				//				sQuery = "select DECISION_MAKER_USERID, DECISION_MAKER_USERNAME FROM BPM_EA_TEMPLATE_IDS WHERE "+
				//						"TEMPLATE_ID = (SELECT TEMPLATE_ID FROM BPM_EA_TEMPLATES WHERE EA_LEVEL_SEQ = 1)";
				//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sQuery :: "+sQuery);
				//				outputXML = APCallCreateXML.APSelect(sQuery);
				//				xp = new XMLParser(outputXML);
				//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"APSelect outputXML :: "+outputXML);
				//				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				String sUserName = "";
				String sUserID = "";
				//				if(mainCode == 0){
				//					sUserName = xp.getValueOf("DECISION_MAKER_USERNAME");
				//					sUserID = xp.getValueOf("DECISION_MAKER_USERID");
				//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," APSelect ASSIGNED_USERNAME:: "+sUserName+"  ASSIGNED_USERID:: " + sUserID);
				//				}
				int processDefId = 0;
				outputXML = APCallCreateXML.APSelect("SELECT PROCESSDEFID FROM PROCESSDEFTABLE WHERE PROCESSNAME = 'EA'");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"query : SELECT PROCESSDEFID FROM PROCESSDEFTABLE WHERE PROCESSNAME = 'EA'");
				xp = new XMLParser(outputXML);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML :: "+outputXML);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					processDefId = Integer.parseInt(xp.getValueOf("PROCESSDEFID"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"processDefId: "+processDefId);
				}
				String outputXml = ProdCreateXML.WFUploadWorkItem(sessionId, "N", attributeMap,"",processDefId,"Introduction");
				xp = new XMLParser(outputXml);
				mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :" + outputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode " + mainCode);

				if (mainCode == 0) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);
					HashMap<String, String> complexMap = new HashMap<>();
					workItemNumber = xp.getValueOf("ProcessInstanceId");
					String[] split = wiNumber.split("-");
					String regPrefix = split[0].toString();
					int processDefID = processMap.get(regPrefix);
					HashMap<String, String> hm = new HashMap<>();
					hm.put("WI_NAME", workItemNumber);
					hm.put("PARENT_PROCESSDEFID", processDefID+"");
					hm.put("ASSIGNED_USERNAME", sUserName);
					hm.put("ASSIGNED_USERID", sUserID);
					complexMap.put("Qvar_Decision_Flow", ja.toString());
					outputXml = ProdCreateXML.WFSetAttributes(sessionId, workItemNumber, 1, hm, processDefId+"", complexMap);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml MAINCODE 0:" + outputXml);
					xp = new XMLParser(outputXml);
					mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
					if(mainCode == 0)
					{
						//						String sCurrentDateTime = getCurrentDate();
						//						String sEncCurrentDate = new String(Base64.encodeBase64(sCurrentDateTime.getBytes()));
						//						String sEncWIName = new String(Base64.encodeBase64(workItemNumber.getBytes()));
						//						int NoOfCols = 5;
						//						String sInValues = "'"+workItemNumber+"','"+sEncCurrentDate+"','"+sEncWIName+"','"+processDefID+"','"+wiNumber+"','N','','','',''";
						//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sInValues: " + sInValues);
						//						
						//						outputXML = APCallCreateXML.APProcedure(sessionId, "BPM_EA_EMAIL_NOTIFY", sInValues, NoOfCols);
						//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," BPM_EA_EMAIL_NOTIFY outputXML: " + outputXML);

						//	                	//REASSIGN USER
						//	                	outputXML = APCallCreateXML.APSelect("select PARENT_PROCESS_NAME from ext_ea WHERE WI_NAME = N'" + workItemNumber + "'");
						//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"query : select PARENT_PROCESS_NAME from ext_ea WHERE WI_NAME = N'" + workItemNumber + "'");
						//						xp = new XMLParser(outputXML);
						//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML :: "+outputXML);
						//						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						//						String parentProcessName = xp.getValueOf("PARENT_PROCESS_NAME");
						//						if(mainCode == 0 && (!parentProcessName.isEmpty() || parentProcessName != null)){
						//							outputXML = APCallCreateXML.APSelect("select ASSIGNED_USERNAME from ext_ea WHERE WI_NAME = '"+ workItemNumber + "'");
						//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"query : select ASSIGNED_USERNAME from ext_ea WHERE WI_NAME = '"+ workItemNumber + "'");
						//							xp = new XMLParser(outputXML);
						//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML :: "+outputXML);
						//							mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						//							if(mainCode == 0){
						//								String targetUser = xp.getValueOf("ASSIGNED_USERNAME"); 
						//				                String comment = "User for Level:: 1";
						//				                outputXml = ProdCreateXML.WMReassignWorkItem(sessionId, wiNumber, "1", targetUser, comment);
						//				                mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
						//				                if (mainCode == 0){
						outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, workItemNumber, 1);
						//				                }
						//							}
						//						}
					}
				}
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"errror" + e);
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		response.setStatusCode("0");
		response.setStatusDescription("Workitem Created Successfully");
		response.setWiNumber(workItemNumber);		
		return response;
	}

	private String getCurrentDate()	
	{
		String today = null;
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			today = formatter.format(date);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCurrentDate]"  + today);
		} catch (Throwable exp) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"[getCurrentDate]" + " Exeception : " + exp);
		}

		return today;
	} 

}
