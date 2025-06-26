package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.sourcingchannel.C_SANCTION_SCREENING;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;
//import org.json.JSONArray;
//import org.json.JSONObject;
import com.newgen.ao.laps.cache.CallEntity;

public class WBGItqanPassportDedupe implements ICallExecutor, Callable<String> {
	private String WI_NAME;
	private int StageID;
	private String PassportNum;
	private String PassportNationality;
	private String PassportDOB;
	private String AUS;
	private String EIDANo;
	private String CID;
	private String sessionId;
	private String callStatus="N";
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private boolean prevStageNoGo;
	private CallEntity callEntity;
	private Map<String, String> attribMap;
	private String isNTBCust;
	private String finalOutputXml;
	private String auditCallName = "WBGItqanPassportDedupe";
	private String sourcingChannel;
	private String leadRefNumber;
	private HashMap<String, String> attributeMap = null;
	private String output;
	private String sQuery;
	boolean isCallExecuted;

	public WBGItqanPassportDedupe( Map<String, String> attribMap,String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attribMap =  attribMap;
		this.callEntity = callEntity;
		String outputXML;
		
		try {
			outputXML = APCallCreateXML.APSelect("SELECT AUTH_SIGN,EIDANO, PASSPORT, DOB, NATIONALITY,CUSTID "
					+ "FROM USR_0_WBG_AO_AUS WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "PASS001", "Pass_Dedupe Started", sessionId);
			if(mainCode == 0){
				AUS = xp.getValueOf("AUTH_SIGN");
				EIDANo = xp.getValueOf("EIDANO");
				PassportNum = xp.getValueOf("PASSPORT");
				PassportNationality = xp.getValueOf("NATIONALITY");
				PassportDOB = xp.getValueOf("DOB");
				CID = xp.getValueOf("CUSTID");

				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"EIDANo"+ EIDANo);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PassportNum"+ PassportNum);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PassportNationality"+ PassportNationality);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"CID"+ CID);

//				if(CID.equalsIgnoreCase("")){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"WBG Passport DEDUPE: inside CID"+CID);
					 sQuery = "SELECT '"+AUS+"',CUST_ID,FINAL_FULL_NAME,FINAL_EIDA_NO,FINAL_PASS_NO,FINAL_DOB,"
							+ "FINAL_NATIONALITY,'ID' FROM USR_0_CUST_TXN WHERE FINAL_PASS_NO = '"+PassportNum
							+"' AND TRUNC(final_dob) = TO_DATE('"+PassportDOB+"','YYYY-MM-DD HH24:MI:SS') AND FINAL_NATIONALITY = '"
							+PassportNationality+"' UNION select '"+AUS+"',CUSTOMER_ID,CUSTOMER_FULL_NAME,a.EIDA_NUMBER,a.PASSPORT_NUMBER,"
							+ "a.EIDA_DOB_DATE,a.EIDA_NATIONALITY,'ID' from EXT_CBG_CUST_ONBOARDING a "
							+ "WHERE PASSPORT_NUMBER = '"+PassportNum+"' AND WI_NAME != '"+WI_NAME+"' AND "
							+ "(STAGE_ID != -3 AND STAGE_ID != -4) UNION select '"+AUS
							+"',CUST_ID,CUST_FULL_NAME,b.EIDA_NO,b.CUST_PASS_NO,b.CUST_DOB,b.CUST_NATIONALITY,'ID' "
							+ "from USR_0_CUST_MASTER b WHERE CUST_NATIONALITY = (SELECT  CNTRY_CODE FROM USR_0_COUNTRY_MAST WHERE COUNTRY ='"+PassportNationality
							+"') AND  TRUNC(CUST_DOB) = TO_DATE('"+PassportDOB+"','YYYY-MM-DD HH24:MI:SS') AND CUST_PASS_NO ='"
							+PassportNum+"'";

					
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Passport DEDUPE: callStatus "+callStatus);

				String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + WI_NAME + "'");
				XMLParser xp4 = new XMLParser(soutputXML);
				int mainCode1 = Integer.parseInt(xp4.getValueOf("MainCode"));
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "PASS_DEDUPE", "PASS_DEDUPE Started", sessionId);
				if(mainCode1 == 0){
					if(Integer.parseInt(xp4.getValueOf("TotalRetrieved")) > 0){

						leadRefNumber = xp4.getValueOf("LEAD_REF_NO");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber"+leadRefNumber);
						LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "PASS_DEDUPE", leadRefNumber, sessionId);
					}
				}

			}

					
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "PASS_DEDUPE", e.getMessage(), sessionId);
		}

	}

	@Override
	public String call() throws Exception {
		try{

			callStatus = "Y";
			isCallExecuted = LapsUtils.isItqanMCallExecuted(auditCallName,WI_NAME);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted " + isCallExecuted);
			if(!isCallExecuted){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sQuery"+ sQuery);
				output = APCallCreateXML.APSelect(sQuery);
				XMLParser xp1 = new XMLParser(output);

				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Passport Dedupe TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
					errorDescription = "Passport Dedupe Found";
					callStatus = "Y";

				}else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Passport Dedupe TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
					errorDescription = "Passport Dedupe Not Found";
					callStatus = "N";
				}
				updateCallOutput(PassportNum);
			}
			
		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
		return "";
	}

	@Override
	public String createInputXML() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try{
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, attribMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			
			String finalStatus = "F";
			if(callStatus == "Y" || callStatus == "N"){
				finalStatus = "Y";
			}
			String valList = "'"+ WI_NAME +"',"+ StageID +", '" + auditCallName +"', '"+ finalStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			String noResultsFoundMsg ;
			String remarksControl ;
			String sActivityName;
			String reasonForAction;
			String action;
			
			if(callStatus == "Y"){
				 noResultsFoundMsg = "Passport Dedupe Found";
				 remarksControl = "decRemarks";
				 sActivityName = "Auto Intro";
				 reasonForAction = "Dedupe Check is performed for Passport Number: " + PassportNum;
				 action = "Passport Dedupe found";
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);
					
					String valueList1 = "'"+ WI_NAME +"','"+ CID +"','"+ AUS +"', '"+ EIDANo +"', '"+ PassportNum +"',TO_DATE('" + PassportDOB +"','DD/MM/YYYY'),'"+ PassportNationality +"'";
					APCallCreateXML.APInsert("USR_0_WBG_AO_DEDUPE_AUS_DET", "WINAME,customer_id, name, eida_number, pass_number, DATE_OF_BIRTH, NATIONALITY ", valueList1, sessionId);
					
			} else if (callStatus == "X"){
				 noResultsFoundMsg = "Passport Dedupe Skipped";
				 remarksControl = "decRemarks";
				 sActivityName = "Introduction";
				 reasonForAction = "Dedupe Check is skipped for Passport Number: " + PassportNum;
				 action = "Passport Dedupe Skipped";
				 
			}else {

//			String output = APCallCreateXML.APUpdate("EXT_WBG_AO", "WBG_PASS_DEDUPE", " "+ callStatus +" ", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
			String leadRefNo = "";
			 noResultsFoundMsg = "No Records Found For Dedupe";
			 remarksControl = "decRemarks";
			 sActivityName = "Introduction";
			 reasonForAction = "Dedupe Check is performed for Passport Number: " + PassportNum;
			 action = "Passport Dedupe not found";
			}
			 executeDependencyCall();
			valList = "'"+ WI_NAME +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
			APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}

	}
}
