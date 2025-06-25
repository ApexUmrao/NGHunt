package com.newgen.ao.laps.sourcingchannel;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class D_TSLMSY implements SourcingChannelHandler{
	public LapsModifyResponse resp = new LapsModifyResponse();
	private int wrkitmId = 0;
	private String decHist = "TFO_DJ_DEC_HIST";
	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationNo, String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside D_TSLM");
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
			//			String outputXML = APCallCreateXML.APSelect("select WI_NAME FROM TFO_DJ_TSLM_TXN_DATA "
			//					+ "WHERE channel_ref_number='"+channelRefNumber+"' AND CORRELATIONID = '"+correlationNo
			//					+"' AND REQUESTMODE='"+mode+"'");
			//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXML is: " + outputXML);
			//			String workItemNumber = "";
			//			String exitWS = "Work Exit";
			//			XMLParser xp = new XMLParser(outputXML);
			//			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"mainCode is: " + mainCode);
			//			if (mainCode == 0) {
			//				workItemNumber = xp.getValueOf("WI_NAME");
			//			}
						
			String outputXML = "";
			String activityName = "";
			String isPMSystemFlag = "";
			String processType = "";
			String cabinetName =LapsConfigurations.getInstance().CabinetName;
			
				 outputXML = APCallCreateXML.APSelect("SELECT PROCESS_TYPE,CURR_WS,IS_PM_SYSTEM"
						+ " FROM EXT_TFO"
						+ " WHERE WI_NAME='"+wiNumber+"'");
				XMLParser xp = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if (mainCode == 0) {
					processType = xp.getValueOf("PROCESS_TYPE");
					activityName = xp.getValueOf("CURR_WS");
					isPMSystemFlag = xp.getValueOf("IS_PM_SYSTEM");
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"activityName: "+activityName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"isPMSystemFlag: "+isPMSystemFlag);
			
			if(processType.equalsIgnoreCase("TSLM Front End")){
				String discardWS = "Discard_WI";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"wiNameInput is: " + wiNumber);
				String output = "";
				mainCode = -1;
				try {
					if(!isPMSystemFlag.equalsIgnoreCase("Y")){
						
						output = APCallCreateXML.APSelect("select PROCESSDEFID from processdeftable where processname='"+processName+"'");
						XMLParser xmlparser = new XMLParser(output);
						String processDefId = xmlparser.getValueOf("PROCESSDEFID");
								
						//Get session of user who has locked the application
						String outputXml1 = APCallCreateXML.APSelect("select randomnumber from pdbconnection where userindex=(select userIndex from pdbuser where username =(select lockedbyname from wfinstrumenttable where processinstanceid='"+wiNumber+"' AND lockedbyname !='System' AND ROWNUM=1))");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem Flag data output XML:"+outputXml1);
						XMLParser parser = new XMLParser(outputXml1);
						
						String lockedUserSessionId=parser.getValueOf("randomnumber");
						
						//Handling if application is not locked by any user
						if(lockedUserSessionId==null||lockedUserSessionId.isEmpty())
							lockedUserSessionId=sessionId;
						
						//Close All Child workitems
						closeChildWorkItem(lockedUserSessionId,wiNumber,processDefId,cabinetName);
						
						outputXML = APCallCreateXML.APSelect("select WORKITEMID from WFINSTRUMENTTABLE "
								+ "where PROCESSINSTANCEID = '"+wiNumber+"'");
						XMLParser xp3 = new XMLParser(outputXML);
						if (Integer.parseInt(xp3.getValueOf("MainCode")) == 0) {
							wrkitmId = Integer.parseInt(xp3.getValueOf("WORKITEMID"));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "wrkitmId from WFINSTRUMENTTABLE: " + wrkitmId);
						}
					} 
					if(!wiNumber.isEmpty()){
						output = ProdCreateXML.WFAdhocWorkItem(sessionId, wiNumber, wrkitmId, discardWS, "3");
					}
				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in D_TSLM WFAdhocWorkItem:");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
				XMLParser xp1 = new XMLParser(output);
				mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
				if (mainCode == 0) {
					//insert into dec hist
					StringBuilder strDecHistColumns = new StringBuilder();
					StringBuilder strDecHistValues = new StringBuilder();
					strDecHistColumns.append("WI_NAME").append(",").append("CREATE_DATE").append(",")
					.append("USER_ID").append(",").append("ACTION").append(",")
					.append("REMARKS").append(",").append("REASON_FOR_ACTION").append(",").append("BRANCH");
					strDecHistValues.append("'").append(wiNumber).append("',")
					.append("SYSTIMESTAMP,'TSLM SYSTEM USER','Discard','Workitem Discarded',"
							+ "'Discard Workitem',''");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inert into strDecHistColumns: "
							+strDecHistColumns.toString());
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inert into strDecHistValues: "
							+strDecHistValues.toString());
					try {
						APCallCreateXML.APInsert(decHist, strDecHistColumns.toString(), strDecHistValues.toString()
								,sessionId);
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
					resp.setStatusCode("0");
					resp.setStatusDescription("Workitem discarded successfully");
				} else {
					resp.setStatusCode("-1");
					resp.setStatusDescription("Workitem Not Discarded");
				}
			} else {
				resp.setStatusCode("-1");
				resp.setStatusDescription("Discard Failed [Not a TSLM workitem]");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"set final response: ");
			resp.setChannelRefNumber(channelRefNumber);
			resp.setCorrelationId(correlationNo);
			resp.setChannelName(sourcingChannel);
			resp.setWiNumber(wiNumber);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in D_TSLM dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}
	
	private void closeChildWorkItem(String lockedUserSessionId,String WI_NAME,String processDefId,String cabinetName){
		
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
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType,lockedUserSessionId,cabinetName,processDefId,WI_NAME);
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
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType,lockedUserSessionId,cabinetName,processDefId,WI_NAME);
		} if(isTrade.trim().equalsIgnoreCase("Y")){
			outputXml = APCallCreateXML.APSelect("select ACTIVITYID,ACTIVITYTYPE from activitytable where processdefid="+processDefId+" AND ACTIVITYNAME='Trade Sales'");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Activity ID and Activity Name output XML :"+outputXml);
			parser=new XMLParser(outputXml);
			activityID=parser.getValueOf("ACTIVITYID");
			activityType=parser.getValueOf("ACTIVITYTYPE");
			
			outputXml = APCallCreateXML.APSelect("SELECT WORKITEMID from WFINSTRUMENTTABLE "
					+ "WHERE PROCESSINSTANCEID = '"+WI_NAME+"' AND INTRODUCEDAT = 'Distribute1'"
							+ " AND ACTIVITYNAME = 'Trade Sales'"); 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID data output XML:"+outputXml);
			
			XMLParser xp1= new XMLParser(outputXml);
			if (Integer.parseInt(xp1.getValueOf("MainCode")) == 0) {
				workItemID = xp1.getValueOf("WORKITEMID").toString(); 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Workitem ID :"+workItemID);
			}
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType,lockedUserSessionId,cabinetName,processDefId,WI_NAME);
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
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType,lockedUserSessionId,cabinetName,processDefId,WI_NAME);
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
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType,lockedUserSessionId,cabinetName,processDefId,WI_NAME);
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
			ProdCreateXML.WMUnlockWorkItem(lockedUserSessionId, WI_NAME, Integer.valueOf(workItemID));
			discardChildWorkitem(workItemID,activityID,activityType,lockedUserSessionId,cabinetName,processDefId,WI_NAME);
		}
		
		
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in closeChildWorkItem : ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	private void discardChildWorkitem(String workItemID,String activityID,String activityType,String sessionId,String cabinetName,String processDefId,String WI_NAME){
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
	
}
