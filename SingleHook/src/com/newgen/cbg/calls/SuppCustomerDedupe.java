package com.newgen.cbg.calls;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;	
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;
import com.newgen.cbg.core.ICallExecutor;

public class SuppCustomerDedupe implements ICallExecutor, Callable<String> {
	private String wiName;
	private int stageId;
	private String passportNum;
	private String passportNationality;
	private String passportDob;
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
	private String auditCallName = "PPRTCUSTDDUP";
	private String finalOutputXml;

	public SuppCustomerDedupe(Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
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
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTCUSTDDUP100", e, sessionId);
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
		finalOutputXml = "<returnCode>0</returnCode>";
		boolean isDeDupe = true;
		Connection con = null;
		try {
			if(!"X".equalsIgnoreCase(callStatus)){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Passport Customer DEDUPE: inside");
				con = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APProcedure");
				CallableStatement cstmt = null;
				cstmt = con.prepareCall("{call DEDUPE_SUPP(?,?,?,?,?,?,?,?)}");
				cstmt.setString(1, "PDSCOP_CD");//Mode
				cstmt.setString(2, "");//MobileNo
				cstmt.setString(3, "");//EIDANo
				cstmt.setString(4, passportNum);//PASSPORTNo
				cstmt.setString(5, passportNationality);//Nationality
				cstmt.setString(6, passportDob);//DOB
				cstmt.setString(7, wiName);//WINAME
				cstmt.registerOutParameter(8, Types.INTEGER);
				cstmt.execute();
				int isDD = cstmt.getInt(8);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Passport Dedupe count: "+isDD);
				if(isDD == -2){
					isDeDupe = true;
					callStatus = "D";
					errorDescription = "Dedupe Found";
					APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_DEDUPE", "'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPUtils.getInstance().insertDuplicateValue(wiName, "Passport", "Local", sessionId);
				} else {
					isDeDupe = false;
					callStatus = "Y";
					errorDescription="Dedupe Not Found";
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Passport DEDUPE: "+isDeDupe);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "PPRTCUSTDDUP090", " "+ errorDescription, sessionId);
				NGDBConnection.closeDBConnection(con, "APProcedure");
				con = null;
			}
			updateCallOutput("");
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTCUSTDDUP100", e, sessionId);
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
			String outputXMLTemp = "";
			outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "PPRTCUSTDDUP003", "SuppCustomerDedupe DependencyCall: "+callEntity.getDependencyCallID(), sessionId);	
				finalOutputXml = outputXMLTemp;
			}
			
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTCUSTDDUP100", e, sessionId);
		}
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		
		APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppCustomerDedupe' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);
			
		String valList = "'"+ wiName +"',"+ stageId +", 'SuppCustomerDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		try {
			if(callStatus.equals("Y")|| callStatus.equals("X")){
			executeDependencyCall();
		  }
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "PPRTCUSTDDUP100", e, sessionId);
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
				passportDob = xp.getValueOf("DOB");
				passportNationality = xp.getValueOf("NATIONALITY");
				if("".equalsIgnoreCase(passportNum)){
					callStatus="X";
				}
			}

		}
	}



}
