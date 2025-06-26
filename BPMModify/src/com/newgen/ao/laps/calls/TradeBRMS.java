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

public class TradeBRMS implements ICallExecutor, Callable<String> {
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
	private String auditCallName = "TradeBRMS";
	private String requestCategory = "";
	private String requestType = "";
	private String transactionCurrency="";
	private boolean updateCheckFlag = true;
	private String transactionAmount="";
	private String transAmount="";
	private String profitCenter ="";
	private String hybridCustomer  = "";
	private String utcEligible;
	private String sUserName;
	private String mode = "";

	public TradeBRMS(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
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
			outputXML = APCallCreateXML.APSelect("SELECT CUSTOMER_ID,REQUEST_CATEGORY,REQUEST_TYPE,TRANSACTION_CURRENCY,PROFIT_CENTER_CODE,HYBRID_CUSTOMER,TRANSACTION_AMOUNT FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TPD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TradeTslmBRMS TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					customerId = xp.getValueOf("CUSTOMER_ID");
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					requestType = xp.getValueOf("REQUEST_TYPE");
					transactionCurrency=xp.getValueOf("TRANSACTION_CURRENCY");
					transAmount = xp.getValueOf("TRANSACTION_AMOUNT");
					profitCenter =xp.getValueOf("PROFIT_CENTER_CODE");
					profitCenter = profitCenter.substring(0, 3);  /// BRMS CAll IS FAILING HANDLED TO GET ONLY CODE
					hybridCustomer = xp.getValueOf("HYBRID_CUSTOMER");
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TPD002", "Customer Id: "+customerId, sessionId);
				}
			}
			//Setting Supplier Buyer Name in Invoice Grid
			setInvoiceDetail();
			
			//Get Converted amount
			transactionAmount = getCovertedAmount(transactionCurrency,transAmount);
			
			if(profitCenter == null||transactionAmount==null||requestCategory==null){
				this.updateCheckFlag = false;
				callStatus = "N";
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TradeTslmBRMS call: inside");
	    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmBRMS updateCheckFlag"+this.updateCheckFlag);

		String inputXml = "";
		String outputXml = "<returnCode>0</returnCode>";
		try {
			if(updateCheckFlag){
			inputXml = createInputXML();
				outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeTslmBRMS outputXml ===> " + outputXml);
				if(outputXml==null || outputXml.equalsIgnoreCase("")){
					outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(outputXml, inputXml);
				outputXml = outputXml + "<CallStatus>" + this.callStatus + "</CallStatus>";
			    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeBRMS outputXml ===> " + outputXml);
			} else {
				callStatus = "F";
				errorDescription = "BRMS Execution failed";
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
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>WBG_BRMS_RULES_RESPONSE</InnerCallType>").append("\n")
			.append("<serviceName>InqBusinessRules</serviceName>").append("\n")
			.append("<serviceAction>Inquiry</serviceAction>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<WIName>" + WI_NAME + "</WIName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SENDERID>UTC</SENDERID>").append("\n").append(" <username>appuser</username>").append("\n")
			.append("<credentials>appuser</credentials>").append("\n")
			.append("<InqBusinessRulesReq>")
			.append("<ruleFlowGroup>TFO-UTC</ruleFlowGroup>").append("\n")
			.append("<requestChannelName>WMS</requestChannelName>").append("\n")
			.append("<Eligibility>")
			.append("<PAYLOAD type=\"UTC_ELIGIBILITY\"><UTC_ELIGIBILITY>"
			+ "<REQUEST_CATEGORY>"+ requestCategory +"</REQUEST_CATEGORY>"
			+ "<PROFIT_CENTER_CODE>"+profitCenter+"</PROFIT_CENTER_CODE>"
			+ "<TRANSACTION_AMOUNT>"+ transactionAmount +"</TRANSACTION_AMOUNT></UTC_ELIGIBILITY></PAYLOAD>"
			+ "</Eligibility>").append("\n")
			.append("</InqBusinessRulesReq>")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "BRMS inputXML ===> " + inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TPD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
				CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeBRMS responseHandler inside ===> ");
		XMLParser xp = new XMLParser(outputXML);
		returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
		errorDescription = xp.getValueOf("errorDescription");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeBRMS returnCode ===> "+returnCode);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeBRMS errorDescription ===> "+errorDescription);

		try{
			if(returnCode == 0){
				callStatus = "Y";
				if((xp.getValueOf("UTC_ELIGIBILITY_OUTPUT").toUpperCase()).equalsIgnoreCase("TRUE")){
					utcEligible = "Yes";
				} else if((xp.getValueOf("UTC_ELIGIBILITY_OUTPUT").toUpperCase()).equalsIgnoreCase("FALSE")){
					utcEligible = "No";
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TradeBRMS utcEligible "+utcEligible);
			}else{
				callStatus = "N";
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "BRMS", "TradeBRMS Failed", sessionId);
			}
			updateCallOutput(outputXML);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "BRMS", e, sessionId);
		}	
}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
                        //ATP-265 DATE - 12/12/2023 BY REYAZ
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'TradeBRMS', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			if(callStatus.equals("Y")){
				APCallCreateXML.APUpdate("EXT_TFO", "utc_required_brms", " " + utcEligible + " ", " WI_NAME = N'" + this.WI_NAME + "'", this.sessionId);
		     
				String valList2 = "'"+ WI_NAME +"','"+ sUserName +"','','PP_MAKER',sysdate,'','','BRMS Call Executed: " + errorDescription+ "',"
					+ "'',sysdate,'UTC Required BRMS " + utcEligible + "','',' ',' ',''";
				APCallCreateXML.APInsert("TFO_DJ_DEC_HIST", "WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG", valList2, sessionId);
				//Added to Execute UTC Invoice Call 
				String flagUTC = getAutoFlag();
				if(utcEligible.equalsIgnoreCase("Yes") && flagUTC.equalsIgnoreCase("Y")) {
					executeDependencyCall();
				}
			}
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TPD100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	
public void setInvoiceDetail(){
		
		try{
		String sOutputlist = APCallCreateXML.APSelect("select SUPPLIER_NAME,BUYER_NAME,WI_NAME from TFO_DJ_UTC_INVOICE_DETAIL WHERE WI_NAME ='"+WI_NAME+"' AND ROWNUM=1");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sOutputlist invoice details : ="+sOutputlist);
		
		sOutputlist = APCallCreateXML.APSelect("select CUSTOMER_NAME from TFO_DJ_TSLM_SCF_TXN_DATA WHERE WI_NAME ='"+WI_NAME+"' AND ROWNUM=1");
		XMLParser xp =new XMLParser(sOutputlist);
		String name=xp.getValueOf("CUSTOMER_NAME");
		String query ="select CID,PARTY_NAME from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS where winame ='"+WI_NAME+"' and CHECKBOX = '"+true+"' AND ROWNUM=1";
		sOutputlist = APCallCreateXML.APSelect(query);
		xp =new XMLParser(sOutputlist);
		String cpId=xp.getValueOf("CID");
		String cpName=xp.getValueOf("PARTY_NAME");
		String supplier_name="";
		String buyer_name="";
		String tabName="TFO_DJ_UTC_INVOICE_DETAIL";
		StringBuffer strColumns1 = new StringBuffer();
		StringBuffer strValues1 = new StringBuffer();
		strColumns1.append("SUPPLIER_NAME,BUYER_NAME");
		
		if("IFS".equalsIgnoreCase(requestCategory)){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "validateInvoiceDetailsTab : requestCategory="+requestCategory);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "validateInvoiceDetailsTab : name="+name);
			supplier_name=name;
			buyer_name=cpId+"-"+cpName;

		}
		if("IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory)){ //Added for SCF REQ CAT
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "validateInvoiceDetailsTab : requestCategory="+requestCategory);
			buyer_name=name;
			supplier_name=cpId+"-"+cpName;
		}
		
		String whereClause ="WI_NAME = '"+WI_NAME+ "'";
		String opXml1 = APCallCreateXML.APUpdate(tabName, "SUPPLIER_NAME", "'"+supplier_name+"'",
				whereClause, sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
		
		opXml1 = APCallCreateXML.APUpdate(tabName, "BUYER_NAME", "'"+buyer_name+"'",
				whereClause, sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
		
		opXml1 = APCallCreateXML.APUpdate(tabName, "Batch_No", "'"+WI_NAME+"'",
				whereClause, sessionId);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
		
		
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
	}
	
	
	public String getCovertedAmount(String transactionCurrency,String transactionAmount){

		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "executeUTCCalls Inside");
		double converted_amt = 0;
		try{

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "reqCat value : "+requestCategory);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "requestType value : "+requestType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "transactionAmount value : "+transactionAmount);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "transCurr value : "+transactionAmount);

			String brnCode = "";
			String errorDescription  = "";
			double converted_amount=0;//change by Ayush - convert  to bigdecimal type
			double book_rate = 0;
			boolean executeCall = true;
			String utcEligible = "";
			String outputXml ="";
			XMLParser xp = null;
			String whereClause ="WI_NAME = '"+WI_NAME+ "'";
			if ("IFS".equalsIgnoreCase(requestCategory)|| "IFP".equalsIgnoreCase(requestCategory)|| "IFA".equalsIgnoreCase(requestCategory) || "SCF".equalsIgnoreCase(requestCategory))  {  //Added for SCF REQ CAT AND PD TYPE
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside reqCat");
				if("LD".equalsIgnoreCase(requestType) || "IFA_CTP".equalsIgnoreCase(requestType) || "PD".equalsIgnoreCase(requestType)) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside requestType");
						brnCode = "299";
					if(!transactionCurrency.equalsIgnoreCase("AED")){
						String sQuery = "SELECT BOOK_RATE FROM STG_FXRATE WHERE FROM_CCY = '"+transactionCurrency.toUpperCase()+"' AND BRANCH_CODE='"+brnCode+"'";
						String resultSet = APCallCreateXML.APSelect(sQuery);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sQuery value : "+sQuery);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "resultSet	"+resultSet);
						xp = new XMLParser(resultSet);
						
						

						if (resultSet != null && resultSet.contains("BOOK_RATE")) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "book_rate value : "+book_rate);
							book_rate  = Double.parseDouble(xp.getValueOf("BOOK_RATE"));
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "book_rate value : "+book_rate);
							double amount = Double.parseDouble(transactionAmount);
							converted_amount = book_rate * amount;
							
							String roundoff= String.format("%.2f", converted_amount);
							converted_amt = Double.parseDouble(roundoff);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "converted_amount value : "+converted_amt);
						} else {
							executeCall = false;
							converted_amt =0;
							utcEligible = "Yes";
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside else utcEligible value : "+utcEligible);

							converted_amount=0;
//							String query="update ext_tfo set utc_converted_amount = '0',utc_required_brms = 'Yes' where wi_name='"+this.sWorkitemID+"'";
//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Saving in ext table records query : "+query);
//							int output=formObject.saveDataInDB(query);
						}

					} else {
						//converted_amt =  Double.parseDouble(transactionAmount);
						converted_amt= Double.parseDouble(transactionAmount);
						//Added By Ayush
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "transactionAmount :"+transactionAmount);

						converted_amount=Double.parseDouble(new BigDecimal(transactionAmount).toBigInteger().toString());
						//converted_amt=Double.parseDouble(new BigDecimal(transactionAmount).toPlainString());
					}
					//Query change - convertedAmount
					String output=APCallCreateXML.APUpdate("ext_tfo", "utc_converted_amount", "'"+converted_amt+"'", whereClause, sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Aupdate query result : "+output);
					output=APCallCreateXML.APUpdate("ext_tfo", "utc_book_rate", "'"+book_rate+"'", whereClause, sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Aupdate query result : "+output);
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		
		return String.valueOf(converted_amt);
	
	}
	
	 private String getAutoFlag(){
	    	String utcFlag = "";
	    	try{
	    		String outputXml = APCallCreateXML.APSelect("SELECT AUTO_UTC_TRIGGER FROM TFO_DJ_AUTO_TRIGGER_MASTER WHERE SOURCING_CHANNEL='TSLMSY'"); //ATP-379 15-FEB-24 --JAMSHED
    			XMLParser parser = new XMLParser(outputXml);
    			utcFlag = parser.getValueOf("AUTO_UTC_TRIGGER");
    			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " utcFlag : "+utcFlag );
    			
	      	}catch(Exception e){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			}
			return utcFlag;
	    	
	    }
}
