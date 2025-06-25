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

public class SuppEidaLocalDedupe implements ICallExecutor, Callable<String>{

	private String wiName;
	private int stageId;
	private String eidaNum;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap; 
	private CallEntity callEntity;
	private String finalOutputXml;
	private String extractedNumber;

	
	private String auditCallName = "EIDALOCALDEDUPE";

	public SuppEidaLocalDedupe( Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = fetchEidaDedupeNo();
			handleEidaDedupe(outputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ELD100", e, sessionId);
		}
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
     //
	}

	@Override
	public String createInputXML() throws Exception {
		return "";   
	}


	@Override
	public String call() throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: inside call()");
		if(updateCheckFlag){
			boolean isDeDupe = true;
			Connection con = null;
			finalOutputXml = "<returnCode>0</returnCode>";
			try {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: inside");
				con = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APProcedure");
				CallableStatement cstmt = null;
				cstmt = con.prepareCall("{call DEDUPE_SUPP(?,?,?,?,?,?,?,?)}");
				cstmt.setString(1, "E");//Mode
				cstmt.setString(2, "");//MobileNo
				cstmt.setString(3, eidaNum);//EIDANo
				cstmt.setString(4, "");//PASSPORTNo
				cstmt.setString(5, "");//Nationality
				cstmt.setString(6, "");//DOB
				cstmt.setString(7, wiName);//WINAME
				cstmt.registerOutParameter(8, Types.INTEGER);
				cstmt.execute();
				int isDD = cstmt.getInt(8);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: inside call()"+isDD);
				if(isDD == -1){
					isDeDupe = true;
					callStatus = "D";
					errorDescription = "Application Dedupe Found";
					APCallCreateXML.APUpdate("EXT_DSCOP", "APPLICATION_DEDUPE,LEAD_REF_NO", "'Y','"+extractedNumber+"'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPUtils.getInstance().insertDuplicateValue(wiName, "EIDA","Local", sessionId);
					finalOutputXml = "<returnCode>2</returnCode>";
				}else if(isDD == -2){
					isDeDupe = true;
					callStatus = "D";
					errorDescription = "Customer Dedupe Found";
					APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_DEDUPE", "'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPUtils.getInstance().insertDuplicateValue(wiName, "EIDA", "Local", sessionId);
					finalOutputXml = "<returnCode>2</returnCode>";
				}else {
					isDeDupe = false;
					callStatus = "Y";
					errorDescription = "Dedupe Not Found";
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: "+isDeDupe);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "ELD090", "EIDA Local "+ errorDescription, sessionId);
				NGDBConnection.closeDBConnection(con, "APProcedure");
				con = null;
				updateCallOutput(finalOutputXml);
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
				e.printStackTrace();
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ELD100", e, sessionId);
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
		} else {
			callStatus="X";
			updateCallOutput("");
		}
		return finalOutputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDADDUP003", "EIDALocalDedupe DependencyCall: "+callEntity.getDependencyCallID(), sessionId);
		finalOutputXml = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
	}

	@Override
	public void updateCallOutput(String inputXML) throws Exception {
		String output;

		String valList = "'"+ wiName +"',"+ stageId +", 'SuppEidaLocalDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		try {
			if(callStatus.equals("Y")||callStatus.equals("X")){
				output = APCallCreateXML.APUpdate("EXT_DSCOP", "EIDA_LOCAL_DEDUPE", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);	
				XMLParser xp = new XMLParser(output);
				int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
				if(mainCode == 0){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EidaLocalDedupe : "+ mainCode);
				}
				executeDependencyCall();
			}
			else {
			    APCallCreateXML.APUpdate("EXT_DSCOP", "EIDA_LOCAL_DEDUPE", " "+ callStatus +" ", " WI_NAME = N'"+ wiName +"'", sessionId);
				ProdCreateXML.WMCompleteWorkItem(sessionId, wiName, 1);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "EidaLocalDedupe : "+ e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ELD100", e, sessionId);
		}
	}

	public String fetchEidaDedupeNo() throws Exception {
		return APCallCreateXML.APSelect("SELECT EIDA_NUMBER FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}

	private void handleEidaDedupe(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA100", "EIDA100 Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EidaDedupe TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				eidaNum = xp.getValueOf("EIDA_NUMBER");
			    extractedNumber = wiName.substring(8, 14);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA_DEDUPE", eidaNum, sessionId);
				if(eidaNum==null || eidaNum.equals("") ){
					updateCheckFlag = false;
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: updateCheckFlag: False "+updateCheckFlag);
				}
			}

		}
	}
}
