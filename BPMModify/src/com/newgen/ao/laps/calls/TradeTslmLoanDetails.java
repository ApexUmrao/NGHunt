package com.newgen.ao.laps.calls;

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

public class TradeTslmLoanDetails implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String transRefNo;
	private String txnType;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "TTLD";
	private String pastDueFlag;
	private String mode = "";
	private String oLoanID;
	private String oLoanCcy;
	private String oProdCode;		
	private String oCID;
	private String oCustName;
	private String oAmntFin;
	private String oLoanAmt;
	private String oPrincAmt;		
	private String oTnxCcy;		
	private String oTxnAmt;
	private String oMatDate;	
	private String oLoanStatus;
	private String oLoanStage;
	private String oCustPrtyID;
	private String oCustPrtyName;
	private String requestCategory = "";
	private String requestType = "";
	private String hybridCustomer  = "";


	public TradeTslmLoanDetails(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.defaultMap = defaultMap;
		this.callEntity = callEntity;
		if(defaultMap.containsKey("mode")){
			this.mode = defaultMap.get("mode");
		}
		String outputXML;		
		try {
			outputXML = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY,REQUEST_TYPE,HYBRID_CUSTOMER,TRANSACTION_REFERENCE FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTLD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pastDueFlag TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					transRefNo = xp.getValueOf("TRANSACTION_REFERENCE");	
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					requestType = xp.getValueOf("REQUEST_TYPE");
					hybridCustomer = xp.getValueOf("HYBRID_CUSTOMER");
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTLD002", "transaction ref no: "+transRefNo, sessionId);
				}
			}		
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTLD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TradeTslmLoanDetails call: inside");
		String inputXml = "";
		String outputXml = "<returnCode>0</returnCode>";
		try {			
			inputXml = createInputXML();
			if(!prevStageNoGo){
				outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmLoanDetails outputXml ===> " + outputXml);
				if(outputXml==null || outputXml.equalsIgnoreCase("")){
					outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(outputXml, inputXml);
			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTLD100", e.getMessage(), sessionId);
		}
		outputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +outputXml);
		return outputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		String inputXml="";
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXml="<?xml version=\"1.0\"?><APWebService_Input>"
					+ "<Option>WebService</Option>"
					+"<Calltype>WS-2.0</Calltype>"
					+"<InnerCallType>TFO_TSLMCorpLoanDetails</InnerCallType>"
					+"<EngineName>" + LapsConfigurations.getInstance().CabinetName +"</EngineName>"
					+"<SessionId>" + sessionId + "</SessionId>"
					+"<serviceName>ModTSLMCorpLoanDetails</serviceName>"
					+"<serviceAction></serviceAction>"
					+"<winame>"+WI_NAME+"</winame>"
					+"<senderId>" + "WMS" + "</senderId>"
					+"<correlationId>"+refNo+"</correlationId>"
					+"<channelRefNumber>"+refNo+"</channelRefNumber>"
					+"<sysRefNumber>"+refNo+"</sysRefNumber>"
					+"<loanId>"+transRefNo+"</loanId>"
					+"<operationType>fetchTSLMLoanDtl_Oper</operationType>"
					+"</APWebService_Input>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTLD100", e.getMessage(), sessionId);
		}
		return inputXml;
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
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			if(returnCode == 0){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTLD090", "TTLD Successfully Executed", sessionId);
				oLoanID = xp.getValueOf("loanId");
				oLoanCcy  = xp.getValueOf("loanCcy");
				oProdCode  = xp.getValueOf("productCode");		
				oCID  = xp.getValueOf("cid");
				oCustName  = xp.getValueOf("customerName");
				oAmntFin  = xp.getValueOf("amountFinanced");
				oLoanAmt  = xp.getValueOf("osLoanAmt");
				oPrincAmt  = xp.getValueOf("osPrincipalAmount");		
				oTnxCcy  = xp.getValueOf("txnCcy");		
				oTxnAmt  = xp.getValueOf("txnAmt");
				oMatDate  = xp.getValueOf("maturityDate");	
				oLoanStatus = xp.getValueOf("loanStatus");
				oLoanStage  = xp.getValueOf("loanStage");
				oCustPrtyID  = xp.getValueOf("counterPartyID");
				oCustPrtyName  = xp.getValueOf("counterPartyName");
				
				//ATP-343 DATE 28-12-2023 REYAZ
				//START CODE
				String sDate="";
				Date d = new Date();
				if(oMatDate.contains(" ")){
					sDate = oMatDate.split(" ")[0];

				}else{
					sDate = oMatDate;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				d = sdf.parse(sDate);
				oMatDate = sdf1.format(d);
				//END CODE
//				//Added to Route WI 
//				routeApplication(hybridCustomer);				
			} else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTLD100", e.getMessage(), sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
                        //ATP-265 DATE - 12/12/2023 BY REYAZ
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'TradeTslmLoanDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			//ATP-313 TRANSACTION_REFERENCE NOT UPDATED
			if(callStatus.equals("Y")){
				String columnList = "TRANSACTION_REFERENCE,INF_LOAN_CURR,PRODUCT_TYPE,CUSTOMER_ID,CUSTOMER_NAME,FINANCED_AMOUNT,OVERALL_OUTSTANDING_AMOUNT"
						+ ",TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,INF_MATURITY_DATE,LOAN_STAGE";
				String valList3 = "'"+oLoanID+"','"+oLoanCcy+"','"+oProdCode+"','"+oCID+"','"+oCustName+"','"+oAmntFin+"','"+oLoanAmt+"','"+oTnxCcy+"',"
						+ "'"+oTxnAmt+"','"+oMatDate+"','"+oLoanStage+"'";
				APCallCreateXML.APUpdate("EXT_TFO", columnList, valList3, " WI_NAME = '"+WI_NAME+"'", sessionId);
				APCallCreateXML.APInsert("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", "CID,PARTY_NAME", "'"+oCustPrtyID+"','"+oCustPrtyName+"'", sessionId);
				filldataInLoanRefInitiator(oCustPrtyID,oLoanID);
			} 
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTLD100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	
	public void filldataInLoanRefInitiator(String CounterPartyID,String oLoanID) throws NGException{ 
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE filldataInLoanRefInitiator ");
		if ((requestCategory.equalsIgnoreCase("IFA") || requestCategory.equalsIgnoreCase("IFS")
				||requestCategory.equalsIgnoreCase("IFP"))&& requestType.equalsIgnoreCase("AM")) {
			APCallCreateXML.APInsert("TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS", "SNO,CID,LOANREFNO,WINAME,INSERTIONORDERID", "'1','"+CounterPartyID+"','"+oLoanID+"','"+WI_NAME+"','1'", sessionId);
		}else if((requestCategory.equalsIgnoreCase("IFA") || requestCategory.equalsIgnoreCase("IFS") 
				||requestCategory.equalsIgnoreCase("IFP"))&& requestType.equalsIgnoreCase("STL")){
			APCallCreateXML.APInsert("TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS", "SNO,CID,LOANREFNO,WINAME,INSERTIONORDERID", "'1','"+CounterPartyID+"','"+oLoanID+"','"+WI_NAME+"','1'", sessionId);
		}else if(requestCategory.equalsIgnoreCase("IFA") && requestType.equalsIgnoreCase("IFA_CTP")){
			APCallCreateXML.APInsert("TFO_DJ_TSLM_LOAN_REFERENCE_DETAILS", "SNO,CID,LOANREFNO,WINAME,INSERTIONORDERID", "'1','"+CounterPartyID+"','"+oLoanID+"','"+WI_NAME+"','1'", sessionId);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"END filldataInLoanRefInitiator ");
	}

	

}
