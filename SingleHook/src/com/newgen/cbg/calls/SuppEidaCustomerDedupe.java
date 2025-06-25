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
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.jts.srvr.NGDBConnection;

public class SuppEidaCustomerDedupe implements ICallExecutor, Callable<String>{

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
	private String auditCallName = "EIDACUSTOMERLOCALDEDUPE";
	private String sourcingChannel;
	private String finalOutputXml;
	public SuppEidaCustomerDedupe( Map<String, String> defaultMap, String sessionId, String stageId, String wiName, Boolean prevStageNoGo, CallEntity callEntity){
		this.wiName = wiName;
		this.stageId = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		String outputXML;
		try {
			outputXML = fetchCustomerDedupe();
			handleCustomerDedupe(outputXML);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ECLD100", e, sessionId);
		}
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
		if(updateCheckFlag){
			finalOutputXml = "<returnCode>0</returnCode>";
			boolean isDeDupe = true;
			Connection con = null;
			try {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: inside");
				con = NGDBConnection.getDBConnection(DSCOPConfigurations.getInstance().CabinetName, "APProcedure");
				CallableStatement cstmt = null;
				cstmt = con.prepareCall("{call DEDUPE_SUPP(?,?,?,?,?,?,?,?)}");
			    cstmt.setString(1, "EDSCOP_CD");//Mode
				cstmt.setString(2, "");//MobileNo
				cstmt.setString(3, eidaNum);//EIDANo
				cstmt.setString(4, "");//PASSPORTNo
				cstmt.setString(5, "");//Nationality
				cstmt.setString(6, "");//DOB
				cstmt.setString(7, wiName);//WINAME
				cstmt.registerOutParameter(8, Types.INTEGER);
				cstmt.execute();
				int isDD = cstmt.getInt(8);
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA isDD: "+isDD);
				if(isDD == -2){
					isDeDupe = true;
					callStatus = "D";
					errorDescription = "Customer Dedupe Found";
					APCallCreateXML.APUpdate("EXT_DSCOP", "CUSTOMER_DEDUPE", "'Y'", " WI_NAME = N'"+ wiName +"'", sessionId);
					DSCOPUtils.getInstance().insertDuplicateValue(wiName, "EIDA","Local", sessionId);
				} else {
					isDeDupe = false;
					callStatus = "Y";
					errorDescription = "Dedupe Not Found";
				}
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EIDA DEDUPE: "+isDeDupe);
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, wiName, auditCallName, "ECLD090", "EIDA Customer Local "+ errorDescription, sessionId);
				NGDBConnection.closeDBConnection(con, "APProcedure");
				con = null;
				updateCallOutput("");
			} catch (Exception e) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR,e);
				e.printStackTrace();
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ECLD100", e, sessionId);
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
		String outputXMLTemp = "";
		outputXMLTemp = CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, wiName, prevStageNoGo);
		if(!outputXMLTemp.isEmpty()){	
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "DependencyCall", "DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			finalOutputXml = outputXMLTemp;
		}
	}

	@Override
	public void updateCallOutput(String inputXML) throws Exception {

		APCallCreateXML.APUpdate("USR_0_DSCOP_CALL_OUT", "CALL_STATUS,ERROR_DESCRIPTION", "'Y','Processed By Expiry Utility'", " WI_NAME = N'"+ wiName +"' AND CALL_NAME = 'SuppEidaCustomerDedupe' AND CALL_STATUS ='N' and STAGE_ID= '"+stageId+"'", sessionId);


		String valList = "'"+ wiName +"',"+ stageId +", 'SuppEidaCustomerDedupe', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		try {
			if(callStatus.equals("Y")||callStatus.equals("X")){
				executeDependencyCall();
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "SuppEidaCustomerDedupe : "+ e);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_ERROR, wiName, auditCallName, "ECLD100", e, sessionId);
		}
	}
	public String fetchCustomerDedupe() throws Exception {
		return APCallCreateXML.APSelect("SELECT EIDA_NUMBER FROM EXT_DSCOP WHERE WI_NAME = N'" + wiName + "'");
	}
	private void handleCustomerDedupe(String outputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA100", "EIDA100 Started", sessionId);
		if (mainCode == 0 && Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"EidaDedupe TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
			if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				eidaNum = xp.getValueOf("EIDA_NUMBER");
				DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_DEBUG, wiName, auditCallName, "EIDA_NUMBER", eidaNum, sessionId);
				if(eidaNum==null || eidaNum.equals("") ){
					updateCheckFlag = false;
				}
			}

		}
	}


}
