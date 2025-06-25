package com.newgen.ao.laps.calls;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class TradeTslmExecuteData implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String customerId;
	private String txnType;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "TradeTslmExecuteData";
	private String requestCategory = "";
	private String requestType = "";
	private String channelRefNo = "";
	private String profitCenter ="";
	private String sUserName;
	private String mode = "";
	private String rmEmail = ""; //ATP-438 REYAZ 25/03/2024

	public TradeTslmExecuteData(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.sUserName = LapsConfigurations.getInstance().UserName;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		if(defaultMap.containsKey("mode")){
			this.mode = defaultMap.get("mode");
		}
		String outputXML;		
		try {
			
			//ATP -328 DATE -18-12-2023 REYAZ
			if(mode.equalsIgnoreCase("DC") || mode.equalsIgnoreCase("DCR") || mode.equalsIgnoreCase("R") ) {
				mode ="C";
			} else if(mode.equalsIgnoreCase("DM") || mode.equalsIgnoreCase("DMR")) {
				mode ="M";
			} 
			//ATP-516 |reyaz |atp-516 | 03-09-2024 ends
			outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_ID,REQUEST_CATEGORY,REQUEST_TYPE,PROFIT_CENTER_CODE,CORRELATIONID,RM_EMAIL FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TPD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TradeTslmExecuteData TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					customerId = xp.getValueOf("CUSTOMER_ID");
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					requestType = xp.getValueOf("REQUEST_TYPE");
					profitCenter =xp.getValueOf("PROFIT_CENTER_CODE");
					channelRefNo = xp.getValueOf("CORRELATIONID");
					rmEmail = xp.getValueOf("RM_EMAIL");   //ATP-438 REYAZ 25/03/2024
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TPD002", "Customer Id: "+customerId, sessionId);
				}
			}
			//INSERT CUSTOM JSP DATA
			insertCustomJSPData();
			
			//INSERT DATA FOR FSK SCREENING
			insertDataForScreening();
			
			//Move data From Staging to History
			moveDataFromStagingToHistory();

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TradeTslmExecuteData call: inside");
		String inputXml = "";
		String outputXml = "<returnCode>0</returnCode>";
		try {
			inputXml = createInputXML();
			if(!prevStageNoGo){
			  if("SCF".equalsIgnoreCase(requestCategory) && "PD".equalsIgnoreCase(requestType) && "Y".equalsIgnoreCase(getFlagFromMaster("AutoScreening"))) {
				outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmExecuteData outputXml ===> " + outputXml);
				if(outputXml==null || outputXml.equalsIgnoreCase("")){
					outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(outputXml, inputXml);
				outputXml = outputXml + "<CallStatus>" + this.callStatus + "</CallStatus><CallResponse>" + this.errorDescription + 
				          "</CallResponse><returnCode>" + this.returnCode + "</returnCode><errorDescription>" + this.errorDescription + 
				          "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>" + this.status + "</Status>";
				        LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmExecuteData outputXml ===> " + outputXml);
			  } else {
				  callStatus = "N";
				  outputXml= "<returnCode>0</returnCode><errorDescription>No Screening required for " +requestCategory+" at this Stage</errorDescription>";
				  responseHandler(outputXml, inputXml);
				  outputXml = outputXml + "<CallStatus>" + this.callStatus + "</CallStatus>";
					        LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmExecuteData outputXml ===> " + outputXml);
			  }
			} else {
				callStatus = "F";
				errorDescription = "FSK Execution failed";
				returnCode = 1;
				updateCallOutput(inputXml);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}
		outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName  + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>BPMModify</InnerCallType>").append("\n")			
			.append("<wiNumber>" +WI_NAME + "</wiNumber>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<mode>C</mode>").append("\n")
			.append("<channelName>SANCTION_SCREENING</channelName>").append("\n")
			.append("<correlationId>"+refNo+"</correlationId>").append("\n")
			.append("<channelRefNumber>"+refNo+"</channelRefNumber>").append("\n")
			.append("<sysrefno>"+refNo+"</sysrefno>").append("\n")
			.append("<processName>TFO</processName>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FSK inputXML ===> " + inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTLD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
			CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTLD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmExecuteData responseHandler inside ===> ");    
		XMLParser xp = new XMLParser(outputXML);
		if("SCF".equalsIgnoreCase(requestCategory) && "PD".equalsIgnoreCase(requestType) && "Y".equalsIgnoreCase(getFlagFromMaster("AutoScreening"))) {//ATP - 342 PM Route Condition Handling
			returnCode = Integer.parseInt(xp.getValueOf("StatusCode"));
			errorDescription = xp.getValueOf("StatusMessage");
		}else {
			returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
			errorDescription = xp.getValueOf("errorDescription");
		}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmExecuteData returnCode ===> "+returnCode);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmExecuteData errorDescription ===> "+errorDescription);

		try{
			if(returnCode == 0 || returnCode==100){ 
				callStatus = "Y";
					//Auto Submit case to RM
					if(requestCategory.equalsIgnoreCase("IFCPC")&&profitCenter!=null){
						autoSubmitIFCPCApp(customerId);
					}else if("C".equalsIgnoreCase(mode) || "M".equalsIgnoreCase(mode)){ // ATP-461 REYAZ 16-05-2024
						autoSubmitPPM(customerId);
					}
			}else{
				callStatus = "N";
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FSK", "TradeTslmExecuteData Failed", sessionId);
			}
			updateCallOutput(outputXML);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FSK", e, sessionId);
		}	
}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
                        //ATP-265 DATE - 12/12/2023 BY REYAZ
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'TradeTslmExecuteData', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
		
	//Code to insert Custom JSP data
		public void insertCustomJSPData(){
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside insertCustomJSPData: ");
			String LimitAvailable="";
			String cashMarginPercentage="";
			String clientContributionAmt="";
			Double cashMarginPercentageD=0.0;
			Double clientContributionAmtD=0.0;
			String limitLine="";
			String CollatreralSelectedValue="";
			String output="";
			
			StringBuffer strColumns2= new StringBuffer();
		StringBuffer strValues2 = new StringBuffer();
			
			try{
			String outputXML=APCallCreateXML.APSelect("select count(0) as COUNT from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where WI_NAME='"+this.WI_NAME+"'");
			XMLParser parser = new XMLParser(outputXML);
			
			int countLimitRef=Integer.parseInt(parser.getValueOf("COUNT"));
			
			
			if(countLimitRef>0)
				LimitAvailable="Yes";
			else
				LimitAvailable="No";
			
			outputXML=APCallCreateXML.APSelect("select CASH_MARGIN_PERCENTAGE,CUSTOMER_CONTRIBUTION_AMT,LIMIT_LINE from TFO_DJ_TSLM_SCF_TXN_DATA where wi_name = '"+this.WI_NAME+"'");
			parser = new XMLParser(outputXML);
			cashMarginPercentage=parser.getValueOf("CASH_MARGIN_PERCENTAGE");
			clientContributionAmt=parser.getValueOf("CUSTOMER_CONTRIBUTION_AMT");
			limitLine=parser.getValueOf("LIMIT_LINE");
			
			if(cashMarginPercentage!=null&&!cashMarginPercentage.trim().isEmpty()){
				cashMarginPercentage=cashMarginPercentage.trim().substring(0,cashMarginPercentage.length()-1);
				cashMarginPercentageD=Double.parseDouble(cashMarginPercentage);
			}
			if(clientContributionAmt!=null&&!clientContributionAmt.trim().isEmpty()){
				clientContributionAmtD=Double.parseDouble(clientContributionAmt);
			}
			if(cashMarginPercentageD>0){
				CollatreralSelectedValue="CASH MARGIN";
			}
			if(clientContributionAmtD>0){
				CollatreralSelectedValue="CLIENT CONTRIBUTION";
			}
			if(cashMarginPercentageD>0&&clientContributionAmtD>0){
				CollatreralSelectedValue="CASH MARGIN AND CLIENT CONTRIBUTION";
			}
			
			strColumns2.append("WI_NAME,LT_DOC_RVW_GDLINES,LT_RESPONSE,INSERTIONORDERID");
			
			
			//GENERATE INSERTION ORDER ID
			String insertionorderID="";
		String tablName="TFO_DJ_LMT_CRDT_RECORDS";	
		insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
        
		strValues2.append("'"+this.WI_NAME+"','"+"Please input Cash Margin percentage."+"','"+String.valueOf(cashMarginPercentageD)+"',"+insertionorderID);
		output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
			
		
		strValues2 = new StringBuffer();
		insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
		strValues2.append("'"+this.WI_NAME+"','"+"Please specify Client Contribution Amount"+"','"+String.valueOf(clientContributionAmtD)+"',"+insertionorderID);
		output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
		
		strValues2 = new StringBuffer();
		insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
		strValues2.append("'"+this.WI_NAME+"','"+"Please specify Loan Limit Line to be attached to the contract"+"','"+String.valueOf(limitLine)+"',"+insertionorderID);
		output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
		
		strValues2 = new StringBuffer();
		insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
		strValues2.append("'"+this.WI_NAME+"','"+"Please select Collateral details."+"','"+CollatreralSelectedValue+"',"+insertionorderID);
		output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
		
		strValues2 = new StringBuffer();
		insertionorderID=LapsUtils.returnInsertionOrderID(tablName);
		strValues2.append("'"+this.WI_NAME+"','"+"Transaction within available Limits and Limits are available?"+"','"+LimitAvailable+"',"+insertionorderID);
		output = APCallCreateXML.APInsert("TFO_DJ_LMT_CRDT_RECORDS",strColumns2.toString(), strValues2.toString(), sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);
		
		
			}catch(Exception e){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
		
		
		public void autoSubmitIFCPCApp(String custID){

			
			StringBuffer strColumns1 = new StringBuffer();
			StringBuffer strValues1 = new StringBuffer();
			    String seqNo = "1";
				String transType = "";
				String TRANSID = "";
				String REFCODE = "RM";
				String REFERRALTYPE = "";
				String REFDESC = "Counter Party creation Approval Required as per DLA";
				String USERCMNT = "";
				String STATUS = "Active";
			
			try
			{
		 //ATP-438 REYAZ 25/03/2024
		//	String TRADE_CUST_EMAIL_ID =fetchTradeEmailDetailfrmETL(custID,requestCategory);
			String insertionorderID=LapsUtils.returnInsertionOrderID("TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," insertionorderID: " +insertionorderID);

			strColumns1.append("SEQNO,TRANSTYPE,TRANSID,REFCODE,REFERRALTYPE,REFDESC,USERCMNT,STATUS,COUNTERPARTYCID,WI_NAME,INSERTIONORDERID,FLAG_DEL");
								            strValues1.append("'"+seqNo+"','"+transType+"','"+TRANSID+"','"+REFCODE+"','"+REFERRALTYPE+"','"+REFDESC+"','"+USERCMNT+"','"+STATUS+"','','"+this.WI_NAME+"',"+insertionorderID+",'N'");
			String opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW",strColumns1.toString(), strValues1.toString(), sessionId);
												   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert opXml: " + opXml);	
			
	       //CODE TO INSERT DATA IN FINAL DEC SUMMARY TABLE
			String tablName="tfo_dj_final_dec_summary";						   
            String outputXml1 = APCallCreateXML.APSelect("select S_"+tablName+".nextval from dual");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"S_TFO_DJ_UTC_INVOICE_DETAIL data output XML:"+ outputXml1);
				XMLParser xp1 = new XMLParser(outputXml1);
				int mainCode = Integer.parseInt(xp1.getValueOf("MainCode").trim().isEmpty() ? "1" : xp1.getValueOf("MainCode").trim());
				if (mainCode == 0) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+ Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
					if (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0) {
						insertionorderID = xp1.getValueOf("NEXTVAL");
					}
				}
				StringBuffer strColumns2= new StringBuffer();
				StringBuffer strValues2 = new StringBuffer();	
			   String	MODULE    ="Limit/Credit Review";          
			   String	REFFERDTO   ="RM";           
			   String	DECISION     ="Yes";           
			   String	EXCPREMARKS  ="Counter Party creation Approval Required as per DLA";
			   //ATP-438 REYAZ 25/03/2024  
			  // String	EXISTINGMAIL  =TRADE_CUST_EMAIL_ID; 
			   String	EXISTINGMAIL  =rmEmail;    
			   
			   strColumns2.append("TAB_MODULE,REFFERD_TO,DECISION,EXCP_REMARKS,EXISTING_MAIL,WI_NAME,INSERTIONORDERID");
			   strValues2.append("'"+MODULE+"','"+REFFERDTO+"','"+DECISION+"','"+EXCPREMARKS+"','"+EXISTINGMAIL+"','"+this.WI_NAME+"',"+insertionorderID);
			   String output = APCallCreateXML.APInsert("tfo_dj_final_dec_summary",strColumns2.toString(), strValues2.toString(), sessionId);
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
				
				//CODE TO MOVE WORKITREM TO RM
				APCallCreateXML.APUpdate("EXT_TFO", "DEC_CODE", "'REF'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				APCallCreateXML.APUpdate("EXT_TFO", "IS_RM_PPM", "'Y'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				APCallCreateXML.APUpdate("EXT_TFO", "IS_CR_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				APCallCreateXML.APUpdate("EXT_TFO", "IS_LEGAL_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				APCallCreateXML.APUpdate("EXT_TFO", "IS_REF_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				APCallCreateXML.APUpdate("EXT_TFO", "IS_CB_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				APCallCreateXML.APUpdate("EXT_TFO", "IS_TRADE_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
				
				ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
			}catch(Exception e){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
 		}
		
		
		public void autoSubmitPPM(String custID) {		
		String outputWI = ""; //ATP - 330
		try{
				String autoTrigger =getFlagFromMaster("AutoEmail");
				String groupMailId =getFlagFromMaster("EmailID");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"autoTrigger :"+ autoTrigger);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"groupMailId :"+ groupMailId);
					
			//	String TRADE_CUST_EMAIL_ID =fetchTradeEmailDetailfrmETL(custID,requestCategory);
				String autoReferralFlag=getFlagFromMaster("AutoReferal");
				String opXml =APCallCreateXML.APSelect("select 'Limit/Credit Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'"
							+" union "
							+"select 'Signature and Acc Bal Check' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_REFERRAL_DETAIL where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'"
							+" union "
							+"select 'Document Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_DOCUMENT_REVIEW where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'");
				XMLParser parser=new XMLParser(opXml);
				int mainCode = Integer.parseInt(parser.getValueOf("MainCode"));
			if ((autoReferralFlag.trim().equalsIgnoreCase("Y")||autoReferralFlag.trim().equalsIgnoreCase("Yes"))&&mainCode == 0 
					&& Integer.parseInt(parser.getValueOf("TotalRetrieved"))!= 0) {
					int count=parser.getNoOfFields("Record");
				
				for(int i=0;i<count;i++){
					String Record = parser.getNextValueOf("Record");
					XMLParser xp2 = new XMLParser(Record);
					
					 //CODE TO INSERT DATA IN FINAL DEC SUMMARY TABLE
	 				String insertionorderID="";
	 				String tablName="tfo_dj_final_dec_summary";						   
	 	            String outputXml1 = APCallCreateXML.APSelect("select S_"+tablName+".nextval from dual");
	 					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"data output XML:"+ outputXml1);
	 					XMLParser xp1 = new XMLParser(outputXml1);
	 					int mainCode2 = Integer.parseInt(xp1.getValueOf("MainCode").trim().isEmpty() ? "1" : xp1.getValueOf("MainCode").trim());
	 					if (mainCode2 == 0) {
	 						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertionOrderID TotalRetrieved: "+ Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
	 						if (Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0) {
	 							insertionorderID = xp1.getValueOf("NEXTVAL");
	 						}
	 					}
	 					StringBuffer strColumns2= new StringBuffer();
	 					StringBuffer strValues2 = new StringBuffer();	
	 				   String	MODULE    =xp2.getValueOf("MODULE");          
	 				   String	REFFERDTO   ="RM";           
	 				   String	DECISION     ="Yes";           
	 				   String	EXCPREMARKS  =xp2.getValueOf("refdesc");
	 				  //ATP-438 REYAZ 25/03/2024
	 				 //String	EXISTINGMAIL  =TRADE_CUST_EMAIL_ID; 
	 				   String	EXISTINGMAIL  =rmEmail; 
	 				   String  NEW_MAIL="";
	 				   
	 				   if(autoTrigger.trim().equalsIgnoreCase("Y")||autoTrigger.trim().equalsIgnoreCase("Yes"))
	 					  NEW_MAIL=groupMailId;
	 				   
	 				   strColumns2.append("TAB_MODULE,REFFERD_TO,DECISION,EXCP_REMARKS,EXISTING_MAIL,NEW_MAIL,WI_NAME,INSERTIONORDERID");
	 				   strValues2.append("'"+MODULE+"','"+REFFERDTO+"','"+DECISION+"','"+EXCPREMARKS+"','"+EXISTINGMAIL+"','"+NEW_MAIL+"','"+this.WI_NAME+"',"+insertionorderID);
	 				   String output = APCallCreateXML.APInsert("tfo_dj_final_dec_summary",strColumns2.toString(), strValues2.toString(), sessionId);
	 				   LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Insert output: " + output);	
	 					 
					
				}
				
				//CODE TO MOVE WORKITREM TO RM
					APCallCreateXML.APUpdate("EXT_TFO", "DEC_CODE", "'REF'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_RM_PPM", "'Y'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_CR_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_LEGAL_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_REF_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_CB_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					APCallCreateXML.APUpdate("EXT_TFO", "IS_TRADE_PPM", "'N'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
					
					outputWI = ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
					XMLParser xp1 = new XMLParser(outputWI);
    				mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
    				if (mainCode == 0) {
    					insertDecisionHistory("RM","Referred to RM","Workitem Moved to RM for Approval"); //ATP - 330
    				}
			}
			//ADDED for AutoRouting to Next Stage
			if("SCF".equalsIgnoreCase(requestCategory) && "PD".equalsIgnoreCase(requestType)) {
				routePMAutoTrigger(); //ATP 212-213
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	 }
		
		private String fetchTradeEmailDetailfrmETL(String cid, String reqCat) {
 			
 			String tradeCustEmailID="";
 			try {
 				String tradeFinanceService = "";

 				if("GRNT".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Guarantee";
 				}else if("ELC".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Export LC";
 				}
 				else if("ILCB".equalsIgnoreCase(reqCat) ||  "IC".equalsIgnoreCase(reqCat) || "DBA".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Incoming Bill";
 				}
 				else if("ELCB".equalsIgnoreCase(reqCat) ||  "EC".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Outgoing Bill";
 				}
 				else if("SG".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Shipping Guarantee";
 				}
 				else if("ILC".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Import LC";
 				}
 				else if("IFA".equalsIgnoreCase(reqCat)|| "IFS".equalsIgnoreCase(reqCat) || "IFP".equalsIgnoreCase(reqCat)){
 					tradeFinanceService="Loans";
 				}
 				String str = "SELECT email "
 						+ "FROM TFO_DJ_TRADE_EMAIL_MAST "
 						+ "WHERE CUST_ID ='"+ cid +"' and trade_finance_service='"+tradeFinanceService+"'  AND ROWNUM=1 union  all"+
 						" select email  from tfo_dj_trade_email_mast where  cust_id='"+cid+"' "
 								+ "and UPPER(trade_finance_service)=UPPER('ALL') AND ROWNUM=1";
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchTradeEmailDeatilfrmETL   " + str);

 				String sOutputXML = APCallCreateXML.APSelect(str);
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +sOutputXML);
 				XMLParser Xp3 = new XMLParser(sOutputXML);
 				tradeCustEmailID=Xp3.getValueOf("email");
 			} catch (Exception e) {
 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
 			}
 			
 			return tradeCustEmailID;
 		}
				
		//Auto TRSD Trigger related changes
		public void insertDataForScreening() {
			try{
				if( "Y".equalsIgnoreCase(getFlagFromMaster("AutoScreening")) ||  "Yes".equalsIgnoreCase(getFlagFromMaster("AutoScreening"))){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRSD STARTED FOR "+WI_NAME+" AT "+java.time.LocalDateTime.now());
					insertTrsdDetails();
				}
			}catch(Exception e){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
		}
		 
		  public void insertTrsdDetails() {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails call: inside");
				
		    StringBuffer strColumns;
		    StringBuffer strValues;
		    String entityname;
		    String channelRefNum;
		    String whereClause = "WINAME = '" + WI_NAME + "'";
				int start;
				int deadEnd;
				int noOfFields;

				try {
		        // Delete existing data if mode is M
					if(mode.equalsIgnoreCase("M")){
		            APCallCreateXML.APDelete("tfo_dj_trsd_screening_other_details", whereClause, sessionId);
					}
					
		        // Fetch CUSTOMER_NAME and SUBCOMPANY_NAME from EXT_TFO
		        String outputXml = APCallCreateXML.APSelect("SELECT CUSTOMER_NAME, SUBCOMPANY_NAME FROM EXT_TFO WHERE WI_NAME = '" + WI_NAME + "'");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXml:" +outputXml);
		        XMLParser xp = new XMLParser(outputXml);
		        int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		        int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		        LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:" + mainCode + " ROWCOUNT:" + totalRetrieved);

		        if (mainCode == 0 && totalRetrieved != 0) {
		            entityname = xp.getValueOf("CUSTOMER_NAME");
		            channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		            strColumns = new StringBuffer("insertionorderid,winame,entity,entity_name,trsd_screening_type,channel_reference_no,execution_status");
		            strValues = new StringBuffer("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','DRAWEE/BUYER','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		            String opXml = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);

		            entityname = xp.getValueOf("SUBCOMPANY_NAME");
						channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		            strValues = new StringBuffer("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','Other Parties or vessel','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		            opXml = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);

						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertTrsdDetails opXml: "+opXml);
					}

		        // Fetch details from TFO_DJ_TSLM_GOODS_SERVICES_TXN_DATA and TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA
		        String opXml = APCallCreateXML.APSelect("SELECT SHIPMENT_DELIVERYTO, SHIPMENT_DELIVERYFROM, GOOD_SERVICE_ORIGIN, GOOD_SERVICE_DESCRIPTION "
		                + " FROM TFO_DJ_TSLM_GOODS_SERVICES_TXN_DATA  WHERE WI_NAME ='" + WI_NAME + "' AND REQUESTMODE = '" + mode + "' AND ROWNUM <= 200"
		                + " UNION "
		                + " SELECT SHIPMENT_DELIVERYTO, SHIPMENT_DELIVERYFROM, GOOD_SERVICE_ORIGIN, GOOD_SERVICE_DESCRIPTION "
		                + " FROM TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA  WHERE WI_NAME ='" + WI_NAME + "' AND REQUESTMODE = '" + mode + "' AND ROWNUM <= 200 ");
		        xp = new XMLParser(opXml);
						mainCode = Integer.parseInt(xp.getValueOf("MainCode"));

		        if (mainCode == 0 && totalRetrieved != 0) {
							start = xp.getStartIndex("Records", 0, 0);
							deadEnd = xp.getEndIndex("Records", start, 0);
							noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		            LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "No of Records in Po invoice table: " + noOfFields);
		            strColumns = new StringBuffer("insertionorderid,winame,entity,entity_name,trsd_screening_type,channel_reference_no,execution_status");
							
							for (int i = 0; i < noOfFields; ++i) {
								String Record = xp.getNextValueOf("Record");
								XMLParser xp2 = new XMLParser(Record); 
		                strValues = new StringBuffer();
								
								entityname = xp2.getValueOf("SHIPMENT_DELIVERYTO");
		                if (!entityname.isEmpty()) {
								channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                    strValues.append("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','Port of Discharge/Airport of Destination','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		                    String opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);
		                }
								
		                strValues = new StringBuffer();
								entityname = xp2.getValueOf("SHIPMENT_DELIVERYFROM");
		                if (!entityname.isEmpty()) {
								channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                    strValues.append("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','Port of Loading/Airport of Departure','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		                    String opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);
		                }
								
		                strValues = new StringBuffer();
								entityname = xp2.getValueOf("GOOD_SERVICE_DESCRIPTION");
		                if (!entityname.isEmpty()) {
								channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                    strValues.append("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','Goods/Services','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		                    String opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);
		                }
								
		                strValues = new StringBuffer();
								entityname = xp2.getValueOf("GOOD_SERVICE_ORIGIN");
		                if (!entityname.isEmpty()) {
								channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                    strValues.append("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','Country of Origin','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		                    String opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);
		                }
							}
						}

		        // Fetch COUNTERPARTY_NAME from TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA
		        opXml = APCallCreateXML.APSelect("SELECT DISTINCT COUNTERPARTY_NAME FROM TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA WHERE WI_NAME ='" + WI_NAME + "' AND REQUESTMODE = '" + mode + "' AND ROWNUM <= 200");
						    xp = new XMLParser(opXml);
							mainCode = Integer.parseInt(xp.getValueOf("MainCode"));

		        if (mainCode == 0 && totalRetrieved != 0) {
								start = xp.getStartIndex("Records", 0, 0);
								deadEnd = xp.getEndIndex("Records", start, 0);
								noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		            LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "No of Records in Po invoice table: " + noOfFields);
		            strColumns = new StringBuffer("insertionorderid,winame,entity,entity_name,trsd_screening_type,channel_reference_no,execution_status");
								
								for (int i = 0; i < noOfFields; ++i) {
									String Record = xp.getNextValueOf("Record");
									XMLParser xp2 = new XMLParser(Record); 
		                entityname = xp2.getValueOf("COUNTERPARTY_NAME");
		                channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                strValues = new StringBuffer("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','DRAWER/SELLER','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		                String opXml1 = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);
		            }
		        }
									
		        // Fetch VESSEL_NAME from TFO_DJ_TSLM_GOODS_SERVICES_TXN_DATA and TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA
		        opXml = APCallCreateXML.APSelect("SELECT DISTINCT VESSEL_NAME FROM TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA WHERE WI_NAME ='" + WI_NAME + "' AND REQUESTMODE = '" + mode + "' AND ROWNUM <= 200"
		                + " UNION "
		                + " SELECT DISTINCT VESSEL_NAME FROM TFO_DJ_TSLM_GOODS_SERVICES_TXN_DATA WHERE WI_NAME ='" + WI_NAME + "' AND REQUESTMODE = '" + mode + "' AND ROWNUM <= 200");
		        xp = new XMLParser(opXml);
		        mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
									
		        if (mainCode == 0 && totalRetrieved != 0) {
		            start = xp.getStartIndex("Records", 0, 0);
		            deadEnd = xp.getEndIndex("Records", start, 0);
		            noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		            LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "No of Records in Po invoice table: " + noOfFields);
		            strColumns = new StringBuffer("SNO,WI_NAME,VESSEL_NAME,INSERTIONORDERID");

		            for (int i = 0; i < noOfFields; ++i) {
		                String Record = xp.getNextValueOf("Record");
		                XMLParser xp2 = new XMLParser(Record);
									entityname = xp2.getValueOf("VESSEL_NAME");
									if(!entityname.trim().isEmpty()){
										channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                    strValues = new StringBuffer("'" + (i + 1) + "','" + WI_NAME + "','" + entityname + "'," + getInserIdForTable("S_TFO_DJ_LLI_VESSEL_DTLS"));
		                    String opXml1 = APCallCreateXML.APInsert("TFO_DJ_LLI_VESSEL_DTLS", strColumns.toString(), strValues.toString(), sessionId);
										LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insert VESSEL IN LLI GRID opXml1: "+opXml1);
									}
								}
							}

		        // Fetch COUNTRY from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS
							opXml =APCallCreateXML.APSelect("SELECT DISTINCT(COUNTRY) FROM TFO_DJ_TSLM_COUNTER_PARTY_DETAILS WHERE WINAME  = '"+WI_NAME+"'");
						    xp = new XMLParser(opXml);
							mainCode = Integer.parseInt(xp.getValueOf("MainCode"));

		        if (mainCode == 0 && totalRetrieved != 0) {
								start = xp.getStartIndex("Records", 0, 0);
								deadEnd = xp.getEndIndex("Records", start, 0);
								noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		            LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "No of Records in TSLM COUNTER PARTY: " + noOfFields);
		            strColumns = new StringBuffer("insertionorderid,winame,entity,entity_name,trsd_screening_type,channel_reference_no,execution_status");
								
								for (int i = 0; i < noOfFields; ++i) {
									String Record = xp.getNextValueOf("Record");
									XMLParser xp2 = new XMLParser(Record); 														
									entityname =xp2.getValueOf("COUNTRY");
		                if (!entityname.isEmpty()) {
									channelRefNum = LapsUtils.getInstance().getTrsdRefNum();
		                    strValues = new StringBuffer("'" + getInserIdForTable("WFSEQ_ARRAY_TRSD_ID") + "','" + WI_NAME + "','DRAWER/SELLER COUNTRY','" + entityname + "','Organisation','" + channelRefNum + "','N'");
		                    String opXmlCountry = APCallCreateXML.APInsert("tfo_dj_trsd_screening_other_details", strColumns.toString(), strValues.toString(), sessionId);
		                }
								}
							}

				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				}
		}	
			
			 public String getInserIdForTable(String seqName){
					String seqValue=null;
					String outputXml ="";
					try {
						outputXml = APCallCreateXML.APSelect("select "+seqName+".nextval from dual");
					} catch (NGException e) {
						e.printStackTrace();
					}
					XMLParser xpCount = new XMLParser(outputXml);
					int mainCode = Integer.parseInt(xpCount.getValueOf("MainCode"));
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"+Integer.parseInt(xpCount.getValueOf("TotalRetrieved")));	
					if (mainCode == 0 && Integer.parseInt(xpCount.getValueOf("TotalRetrieved"))!= 0) {
						seqValue =xpCount.getValueOf("NEXTVAL");
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "seqValue :" +seqValue);
					return seqValue;
				}
			 
			//Added for AutoRouting To PM FOR SCF CATEGORY  //JIRA ATP 212-213
				private void routePMAutoTrigger(){
					String output = ""; //ATP -330
			    	int mainCode = 0;
			    	try{
			    			String outputXml =  APCallCreateXML.APSelect("select 'Limit/Credit Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'"
		 							+" union "
		 							+"select 'Signature and Acc Bal Check' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_REFERRAL_DETAIL where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'"
		 							+" union "
		 							+"select 'Document Review' AS MODULE,'RM' AS REFFERED_TO,refdesc from TFO_DJ_TSLM_DOCUMENT_REVIEW where wi_name ='"+WI_NAME+"' and REFCODE='RM' AND STATUS='Active'");
			    			XMLParser Xp = new XMLParser(outputXml);
			    			mainCode = Integer.parseInt(Xp.getValueOf("MainCode"));
			    			int rowCount = Integer.parseInt(Xp.getValueOf("TotalRetrieved"));
			    			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode : "+mainCode+"  and rowCount : "+rowCount);
			    			
			   // 			if (rowCount == 0 && "N".equalsIgnoreCase(getFlagFromMaster("AutoReferal"))) {
			    			 if (rowCount == 0 ) {
			    				APCallCreateXML.APUpdate("EXT_TFO", "TARGET_WORKSTEP", "'PROCESSING MAKER'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
			    				output = ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
			    				XMLParser xp1 = new XMLParser(output);
			    				mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			    				if (mainCode == 0) {
			    					insertDecisionHistory("PP_MAKER","All Validations are OK","Workitem Moved to PROCESSING MAKER"); //ATP - 330
			    				}
			    				if("Y".equalsIgnoreCase(getFlagFromMaster("AutoUTC")) || "Y".equalsIgnoreCase(getFlagFromMaster("AutoScreening"))) {
			    					String outputXmlPM =  APCallCreateXML.APSelect("SELECT IS_GETDOCUMENT_UTC_DONE,TRSD_FLAG FROM EXT_TFO");
			    	    			XMLParser xpPM = new XMLParser(outputXmlPM);
			    	    			mainCode = Integer.parseInt(xpPM.getValueOf("MainCode"));
			    	    			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode:"+mainCode+"ROWCOUNT :"+rowCount);
			    	    			 if (mainCode == 0) {
			    	    				 String utcStatus = xpPM.getValueOf("IS_GETDOCUMENT_UTC_DONE");
			    	 	    			 String trsdStatus = xpPM.getValueOf("TRSD_FLAG");
			    	 	    			 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "utcStatus : "+utcStatus+" trsdStatus : "+trsdStatus);
			    	 	    			 if (utcStatus.equalsIgnoreCase("N") || trsdStatus.equalsIgnoreCase("Y")) {
			    	 	    				APCallCreateXML.APUpdate("EXT_TFO", "DEC_CODE", "'REF'", "WI_NAME = '" + this.WI_NAME + "'", this.sessionId);
			    	 	    				output = ProdCreateXML.WMCompleteWorkItem(sessionId, this.WI_NAME, 1);
			    	 	    				XMLParser xp2 = new XMLParser(output);
						    				mainCode = Integer.parseInt(xp2.getValueOf("MainCode"));
						    				if (mainCode == 0) {
						    					insertDecisionHistory("PP_MAKER","All Validations are OK","Workitem Moved to PROCESSING MAKER"); //ATP - 330
						    				}
			    	 	    			 }
			    	    			 }
			    				}
			    			} else {
			    				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "No WI Movement in case of auto referral is NO and No data in referral grid ");
			    			}
			    	}catch(Exception e){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}	    	
			    }
				
				public void moveDataFromStagingToHistory(){
		 			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside moveDataFromStagingToHistory:" );
		 			try {
		 				String sParameter = "'"+channelRefNo+"','"+mode+"','"+requestCategory+"'";	
		 				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sParameter:" +sParameter);
						String output=APCallCreateXML.APProcedure(sessionId, "BPM_TFO_TSLM_MOVE_DATA_TO_HISTORY", sParameter, 3);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputXML:" +output);
						
					} catch (NGException e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
					}
		 		}
		
				//Added by Shivanshu to handle a Flag based Routing and Call Triggering 
				 private String getFlagFromMaster(String MDMFlag){  //ATP  212-213 , ATP 126-128
					String utcFlag = "";
					String trsdFlag = "";
					String referralFlag = "";	
					String emailFlag ="";
					String emailID ="";

					 try{
				    		String outputXml = APCallCreateXML.APSelect("SELECT AUTO_UTC_TRIGGER,AUTO_TRSD_TRIGGER,AUTO_REFERRAL_FLAG,AUTO_TRIGGER_EMAIL,TFO_GROUP_MAILID FROM TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='TSLMSY'"); //ATP-379 15-FEB-24 --JAMSHED
			    			XMLParser parser = new XMLParser(outputXml);
			    			utcFlag = parser.getValueOf("AUTO_UTC_TRIGGER");
			    			trsdFlag = parser.getValueOf("AUTO_TRSD_TRIGGER");
			    			referralFlag = parser.getValueOf("AUTO_REFERRAL_FLAG");
			    			emailFlag = parser.getValueOf("AUTO_TRIGGER_EMAIL");
			    			emailID = parser.getValueOf("TFO_GROUP_MAILID");

			    			if("AutoUTC".equalsIgnoreCase(MDMFlag)){
			    				MDMFlag = utcFlag;
			    			}else if("AutoScreening".equalsIgnoreCase(MDMFlag)){
			    				MDMFlag = trsdFlag;
			    			}else if("AutoReferal".equalsIgnoreCase(MDMFlag)){
			    				MDMFlag = referralFlag;
			    			}else if("AutoEmail".equalsIgnoreCase(MDMFlag)){
			    				MDMFlag = emailFlag;
			    			}else if("EmailID".equalsIgnoreCase(MDMFlag)){
			    				MDMFlag = emailID;
			    			}
				      	}catch(Exception e){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
						}
		    			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " Flag value --> " +MDMFlag);
						return MDMFlag;
				    	
				    }
			
				 
				 public void insertDecisionHistory(String currWS,String action,String status) { //ATP - 300 Added for DEC HISTORY INSERT AFTER WI COMPLETE
						try {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside insertDocDecisionHistory");
							String valList = "'"+ WI_NAME +"','BPM-USER','"+currWS+"',SYSTIMESTAMP,'"+action+"','"+status+"'";
							APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME, USERNAME, CURR_WS_NAME, CREATE_DATE, ACTION, REMARKS", valList, sessionId);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
}
