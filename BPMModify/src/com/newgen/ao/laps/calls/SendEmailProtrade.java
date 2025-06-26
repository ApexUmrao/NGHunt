package com.newgen.ao.laps.calls;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class SendEmailProtrade implements ICallExecutor, Callable<String>{
	private String sessionId;
	private String requestCategory;
	private String requestType;
	private Map<String, String> attributeMap;

	public SendEmailProtrade(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, 
			Boolean prevStageNoGo, CallEntity callEntity){
		this.sessionId = sessionId;
		this.attributeMap = attributeMap;
		this.requestCategory = attributeMap.get("requestCategory");
		this.requestType = attributeMap.get("requestType");
	}
	
	@Override
	public String call() throws Exception {
		try {
			String mailTo = "";
			HashMap<String, String> defaultMap = StageCallCache.getReference().getCachedDefaultMap();
			if(requestCategory.equalsIgnoreCase("GRNT")){
				mailTo = defaultMap.get("PT_MAILTO_GRNT");
			} else if(requestCategory.equalsIgnoreCase("ILC") && requestType.equalsIgnoreCase("ILC_NI")){
				mailTo = defaultMap.get("PT_MAILTO_ILC");
			} else if(requestCategory.equalsIgnoreCase("ELCB") || requestCategory.equalsIgnoreCase("EC")){
				mailTo = defaultMap.get("PT_MAILTO_ELCB");
			} else if(requestCategory.equalsIgnoreCase("ILC") && requestType.equalsIgnoreCase("ILC_AM")){
				mailTo = defaultMap.get("PT_MAILTO_ILC");
			} else if(requestCategory.equalsIgnoreCase("ILCB") || requestCategory.equalsIgnoreCase("IC")
					|| "TL_LD".equalsIgnoreCase(requestType)){
				mailTo = defaultMap.get("PT_MAILTO_ILCB");
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"SendEmailProtrade mailTo: "+mailTo);
//			String outputXML = APCallCreateXML.APSelect("SELECT SUBJECT, MAIL_BODY FROM TFO_DJ_EMAIL_TEMPLATE_MAST "
//					+ "WHERE REQ_CATEGORY = '" + requestCategory + "' AND REQ_TYPE = '"+requestType+"' AND "
//					+ "REFERRED_TO = 'PROTRADE'");
//			XMLParser xp = new XMLParser(outputXML);
//			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
//			if(mainCode == 0){
//				String valList = "'"+mailFrom + "','" + mailTo + "','" + mailCc + "','"  + xp.getValueOf("SUBJECT") 
//						+"','"+ xp.getValueOf("MAIL_BODY") +"','text/html;charset=UTF-8','PROTRADESERVICEUSER',"
//						+"'TRIGGER',sysdate,10,10,0";
//				APCallCreateXML.APInsert("WFMAILQUEUETABLE", "MAILFROM,MAILTO,MAILCC,MAILSUBJECT,MAILMESSAGE,"
//						+ "MAILCONTENTTYPE,INSERTEDBY,MAILACTIONTYPE,INSERTEDTIME,PROCESSDEFID,ACTIVITYID,"
//						+ "NOOFTRIALS", valList, sessionId);
//			}
			if(!mailTo.isEmpty()){
				Connection con = null;
				try {
			 		con = NGDBConnection.getDBConnection(LapsConfigurations.getInstance().CabinetName, "APProcedure");
					CallableStatement cstmt = null;
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"calling TFO_DJ_REFF_EMAIL_NOTIFY: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"channelRefNumber: "
							+attributeMap.get("channelRefNumber"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"correlationId: "
							+attributeMap.get("correlationId"));
					cstmt = con.prepareCall("{call TFO_DJ_REFF_EMAIL_NOTIFY(?,?,?,?,?,?,?,?)}");
					cstmt.setString(1, attributeMap.get("channelRefNumber"));
					cstmt.setString(2, "PROTRADE");
					cstmt.setString(3, mailTo);
					cstmt.setString(4, attributeMap.get("returnMessage"));
					cstmt.setString(5, attributeMap.get("correlationId"));
					cstmt.setString(6, "");
					cstmt.setString(7, "");
					cstmt.setString(8, "");
//					cstmt.registerOutParameter(3, Types.INTEGER);
					cstmt.execute();
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TFO_DJ_REFF_EMAIL_NOTIFY executed: ");
					NGDBConnection.closeDBConnection(con, "APProcedure");
					con = null;
				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in calling TFO_DJ_REFF_EMAIL_NOTIFY: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);				
				} finally {
					if(con != null){
						try {
							if(!(con.getAutoCommit())){
								con.rollback();
								con.setAutoCommit(true);
							}
							NGDBConnection.closeDBConnection(con, "APProcedure");
							con = null;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in call(): ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return null;
	}

	@Override
	public String createInputXML() throws Exception {
		
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		
		
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		
		
	}

}
