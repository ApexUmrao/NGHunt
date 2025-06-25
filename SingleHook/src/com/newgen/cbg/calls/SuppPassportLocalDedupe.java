package com.newgen.cbg.calls;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class SuppPassportLocalDedupe implements ICallExecutor, Callable<String>{

	private String wiName;
	private int stageId;
	private String passportNum;
	private String passportNationality;
	private String passportDOB;
	private String sessionId;
	private String callStatus="";
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "PPRTDDUP";
	private String finalOutputXml;
	private String extractedNumber;

	public SuppPassportLocalDedupe(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = fetchPassportData();
			handlePassportData(outputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTDDUP100", e, sessionId);
		}
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Passport DEDUPE: inside");

	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {

	}

	@Override
	public String createInputXML() throws Exception {
		return "";   
	}

	@Override
	public String call() throws Exception {
		boolean isDeDupe = true;
		Connection con = null;
		finalOutputXml = "<returnCode>0</returnCode>";
		try {
			if(!"X".equalsIgnoreCase(callStatus)){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Passport DEDUPE: inside");
				con = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APProcedure");
				CallableStatement cstmt = null;
				cstmt = con.prepareCall("{call DEDUPE_SUPP(?,?,?,?,?,?,?,?)}");
				cstmt.setString(1, "P");//Mode
				cstmt.setString(2, "");//MobileNo
				cstmt.setString(3, "");//EIDANo
				cstmt.setString(4, passportNum);//PASSPORTNo
				cstmt.setString(5, passportNationality);//Nationality
				cstmt.setString(6, passportDOB);//DOB
				cstmt.setString(7, wiName);//WINAME
				cstmt.registerOutParameter(8, Types.INTEGER);
				cstmt.execute();
				int isDD = cstmt.getInt(8);
				if(isDD == -1){
					isDeDupe = true;
					callStatus = "D";
					errorDescription = "Application Dedupe Found";
					APCallCreateXML.APUpdate("EXT_DSCOP", "APPLICATION_DEDUPE,LEAD_REF_NO", "'Y','"+extractedNumber+"'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPUtils.getInstance().insertDuplicateValue(wiName, "PASSPORT", "Local", sessionId);
					finalOutputXml = "<returnCode>2</returnCode>";
				}else if(isDD == -2){
					isDeDupe = true;
					callStatus = "D";
					errorDescription = "Customer Dedupe Found";
					APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_DEDUPE", "'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPUtils.getInstance().insertDuplicateValue(wiName, "PASSPORT", "Local", sessionId);
					finalOutputXml = "<returnCode>2</returnCode>";
				}else {
					isDeDupe = false;
					callStatus = "Y";
					errorDescription="Dedupe Not Found";
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Passport DEDUPE: "+isDeDupe);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "PPRTDDUP090", " "+ errorDescription, sessionId);
				NGDBConnection.closeDBConnection(con, "APProcedure");
				con = null;
			}
			updateCallOutput(finalOutputXml);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTDDUP100", e, sessionId);
		}
		finally{
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
		return finalOutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		try{
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "PPRTDDUP003", "PassportLocalDedupe DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
			finalOutputXml = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTDDUP100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		String valList = "'"+ wiName +"',"+ stageId +", 'SuppPassportLocalDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		try {
			if(callStatus.equals("Y")||callStatus.equals("X")){
				String output = APCallCreateXML.APUpdate("EXT_DSCOP", "PASSPORT_LOCAL_DEDUPE", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
				XMLParser xp = new XMLParser(output);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"PassportLocalDedupe : "+ mainCode);
				}
				executeDependencyCall();
			}
			else {
			    APCallCreateXML.APUpdate("EXT_DSCOP", "PASSPORT_LOCAL_DEDUPE", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTDDUP100", e, sessionId);
		}
	}
	
	public String fetchPassportData() throws Exception {
		return APCallCreateXML.APSelect("SELECT PASSPORT_NO,NATIONALITY,DOB FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}
	
	private void handlePassportData(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "PPRTDDUP001", "PassportLocalDedupe Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"PassportLocalDedupe: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				passportNum = xp.getValueOf("PASSPORT_NO");
				passportDOB = xp.getValueOf("DOB");
				extractedNumber = wiName.substring(8, 14);
				passportNationality = xp.getValueOf("NATIONALITY");
				if("".equalsIgnoreCase(passportNum)){
					callStatus="X";
				}
			}

		}
	}


}
