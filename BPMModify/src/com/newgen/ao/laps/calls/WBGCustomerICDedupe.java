package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class WBGCustomerICDedupe  implements ICallExecutor, Callable<String> {
	private String WI_NAME;
	private int StageID;
	private String PassportNum;
	private String nationality;
	private String dob;
	private String name;
	private String EIDANo;
	private String CID;
	private String pass_no;
	private String eida_no;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private boolean prevStageNoGo;
	private CallEntity callEntity;
	private Map<String, String> attribMap;
	private String custIC;
	private String finalOutputXml;
	private String auditCallName = "WBGCustomerICDedupe";
	private String sourcingChannel;
	private String leadRefNumber;
	private String is_ic_validate;
	private HashMap<String, String> attributeMap = null;
	boolean result ;
	boolean isCallExecuted;

	public WBGCustomerICDedupe( Map<String, String> attribMap,String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attribMap =  attribMap;
		this.callEntity = callEntity;
		String outputXML;
		String output;
	
		try{
			outputXML = APCallCreateXML.APSelect("SELECT  CUSTID, CUSTFULLNAME, EIDANO, PASSPORT, DOB, NATIONALITY FROM USR_0_WBG_AO_AUS WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "CUST001", "CUST_IC Started", sessionId);
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					name = xp.getValueOf("CUSTFULLNAME");
					dob = xp.getValueOf("DOB");
					nationality = xp.getValueOf("NATIONALITY");
					CID = xp.getValueOf("CUSTID");
					eida_no = xp.getValueOf("EIDANO");
					pass_no = xp.getValueOf("PASSPORT");
					custIC = nationality.substring(0, 2) + name.toUpperCase().substring(0, 3) + dob.replace("/", "").substring(0,4)
							+ dob.substring(8,10);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "custIC value : " + custIC);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "nationality.substring(0, 2) : " + nationality.substring(0, 2) + name.toUpperCase().substring(0, 3) + dob.replace("/", "").substring(0,4)
							+ dob.substring(8,10));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "custIC value : " + custIC);
				

				}
			}

			String soutputXML = APCallCreateXML.APSelect("SELECT LEAD_REF_NO FROM EXT_WBG_AO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp4 = new XMLParser(soutputXML);
			int mainCode1 = Integer.parseInt(xp4.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "custIC", "custIC Started", sessionId);
			if(mainCode1 == 0){
				if(Integer.parseInt(xp4.getValueOf("TotalRetrieved")) > 0){

					leadRefNumber = xp4.getValueOf("LEAD_REF_NO");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber"+leadRefNumber);
					LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "custIC", leadRefNumber, sessionId);
				}
			}
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "custIC", e.getMessage(), sessionId);
		}

	}


	@Override
	public String call() throws Exception {
		String finalOutputXml = "";

		callStatus = "Y";
		isCallExecuted = LapsUtils.isItqanMCallExecuted(auditCallName,WI_NAME);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "isCallExecuted " + isCallExecuted);
		
		try 
		{
			if(!isCallExecuted){
				result = validateCustIC(custIC);
				if(result){
					callStatus = "Y";
				//	errorDescription = "Customer IC is valid";
					errorDescription = "No existing Customer IC found";
					returnCode = 0;
				} else {
					callStatus = "N";
					returnCode = 1;
					errorDescription = "Customer IC is already Exist";
				}
				updateCallOutput(custIC);
			}
			
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA004", "EIDAMQ output:"+callStatus, sessionId);
			finalOutputXml= "<returnCode>"+returnCode+"</returnCode><Eida>"+callStatus+"</Eida><Status>0</Status><errorDescription>"+errorDescription+"</errorDescription>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "finalOutputXml " + finalOutputXml);


		} catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
		}
		return finalOutputXml;

	}

		@Override
		public String createInputXML() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		private Boolean validateCustIC(String cust_ic){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"validateCustIC"+cust_ic);		
			String soutput;
			try {
				/*String masterQuery = "select count(1) cnt  from USR_0_CUST_MASTER where cust_ic is not null and cust_ic='"
						+ cust_ic + "'";*/
				String masterQuery = "select cust_ic  from USR_0_CUST_MASTER where cust_ic is not null"
						+ " and cust_ic like '%"+ cust_ic + "%'";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "Query for Master table : " + masterQuery);


				soutput = APCallCreateXML.APSelect(masterQuery);
				XMLParser xp1 = new XMLParser(soutput);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "Record List for Master table : " + xp1);
				if (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0) {
					/*String extQueryCOB = "select count(1) cnt from ext_cob where DE8053_CUSTOMERIC IS NOT NULL AND DE8053_CUSTOMERIC ='"
							+ cust_ic + "' AND CURR_WS NOT IN ('Work Exit')";*/
					String extQueryCOB = "select DE8053_CUSTOMERIC from ext_cob where DE8053_CUSTOMERIC IS NOT NULL AND"
							+ " DE8053_CUSTOMERIC like '%"+ cust_ic + "%' AND CURR_WS NOT IN ('Work Exit')";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "Query for External table COB : " + extQueryCOB);

					soutput = APCallCreateXML.APSelect(extQueryCOB);
					XMLParser xp2 = new XMLParser(soutput);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Record List for External table COB: "
							+ xp2);


					if (Integer.parseInt(xp2.getValueOf("TotalRetrieved")) > 0) {
						result = false;
					} else {
						/*String extQuery = "select count(1) cnt  from EXT_WBG_AO  where WI_NAME!='"
								+ WI_NAME + "' " + "AND CUSTIC is not null and CUSTIC='"
								+ cust_ic + "' and FINAL_STATUS NOT IN ('Exit')";*/
						String extQuery = "select CUSTIC  from EXT_WBG_AO  where WI_NAME!='"
								+ WI_NAME + "' " + "AND CUSTIC is not null and CUSTIC like '%"+ cust_ic + "%' and FINAL_STATUS NOT IN ('Exit')";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,  "Query for External table WBG: " + extQuery);

						soutput = APCallCreateXML.APSelect(extQuery);
						XMLParser xp3 = new XMLParser(soutput);
						if (Integer.parseInt(xp3.getValueOf("TotalRetrieved")) > 0) {
						 // result = true;
							result = false;
						}
					}
				  }
				result = true; //added by reyaz
			} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "Exception in createAcctDetailsInputXML: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"validateCustIC result"+result);
			return result;
		}

		@Override
		public String executeCall(HashMap<String, String> input) throws Exception {
			// TODO Auto-generated method stub
			return call();
		}

		@Override
		public void executeDependencyCall() throws Exception {
			try{
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "EIDA003", "EIDA DependencyCall:"+callEntity.getDependencyCallID(), sessionId);
				CallHandler.getInstance().executeDependencyCall(callEntity, attribMap, sessionId, WI_NAME, prevStageNoGo);
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "EIDA100", e, sessionId);
			}
		}

		@Override
		public void responseHandler(String outputXML, String inputXml)
				throws Exception {

		}

		@Override
		public void updateCallOutput(String cust_ic) throws Exception {
			try {
				String finalStatus = "F";
				if(callStatus == "Y" || callStatus == "N"){
					finalStatus = "Y";
				}
				String valueList = "'"+ WI_NAME +"',"+ StageID +", '"  + auditCallName +"', '"+ finalStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
						errorDetail +"', '"+ status +"', '"+ reason +"'";
				APCallCreateXML.APInsert("BPM_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
						+ "ERROR_DETAIL, STATUS, REASON", valueList, sessionId);
				String noResultsFoundMsg = "";
				String sActivityName = "";
				String reasonForAction = "" ;
				String action = "";
				String errDetail = "200";

				if(callStatus == "Y"){
					is_ic_validate = "Y";
				//	noResultsFoundMsg = "Customer IC is valid";
					noResultsFoundMsg = "No existing Customer IC found";
					sActivityName = "Introduction";
					reasonForAction = "Customer IC Dedupe ";
				//	action = "Customer IC is valid." + cust_ic;
					action = "No existing Customer IC found" + cust_ic;
				//	errorDescription = "Customer IC is valid";
					errorDescription = "No existing Customer IC found";


				} else {
					is_ic_validate = "N";
					noResultsFoundMsg = "Customer IC already exists";
					sActivityName = "Introduction";
					reasonForAction = "Customer IC already exists: " + cust_ic;
					action = "Customer IC already exists";
					errorDescription = "Customer IC is already Exist";

				}


				if((is_ic_validate != null) && (is_ic_validate != "Y")){

					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);
					String valueList1 = "'"+ WI_NAME +"','"+ CID +"','"+ name +"', '"+ eida_no +"', '"+ pass_no +"',TO_DATE('" + dob +"','DD/MM/YYYY'),'"+ nationality +"'";
					APCallCreateXML.APInsert("USR_0_WBG_AO_DEDUPE_AUS_DET", "WINAME,customer_id, name, eida_number, "
							+ "pass_number, DATE_OF_BIRTH, NATIONALITY ", valueList1, sessionId);

					/*String pushMessageXML = LapsUtils.createPushMesgXML(leadRefNumber,WI_NAME,
							callStatus,errDetail,errorDescription);
					LapsUtils.updatePushMeassgeXML(sessionId,WI_NAME,pushMessageXML);
					PushMessageItqanM pushMsg = new PushMessageItqanM(sessionId, WI_NAME);
					pushMsg.call();*/
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"leadRefNumber "+leadRefNumber);
				String sTable_info = "USR_0_WBG_AO_AUS";
				String sColumn = "CUSTIC";
				String sValues = "'" + cust_ic + "'";
				String sWhere = "wi_name = N'" + WI_NAME
						+ "' ";
				APCallCreateXML.APUpdate(sTable_info, sColumn, sValues, sWhere, sessionId);


				String valList = "'"+ WI_NAME +"','"+ leadRefNumber +"','"+ sActivityName +"', '"+ action +"', '"+ reasonForAction +"', '"+ noResultsFoundMsg +"'";
				APCallCreateXML.APInsert("USR_0_WBG_AO_DEC_HIST", "WI_NAME,LEAD_REFNO, WS_NAME, WS_DECISION, REJ_REASON, WS_COMMENTS", valList, sessionId);
				executeDependencyCall();



			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
}
