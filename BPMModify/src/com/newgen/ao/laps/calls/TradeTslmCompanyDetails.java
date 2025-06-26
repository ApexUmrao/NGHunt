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
import com.newgen.ao.laps.util.SingleUserConnection;
import com.newgen.omni.jts.cmgr.XMLParser;

public class TradeTslmCompanyDetails implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String customerId;
	private String productCode;
	private String loanCurrency;
	private String transRefNo;
	private String stdLoanFlg = "N";
	private String  compyType ="";
	private String txnType;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "TTCD";
	private String pastDueFlag;
	private String mode = "";
	private String requestCategory = "";
	private String requestType = "";
	private String cabinetName;
	private String username;
	private String password;

	public TradeTslmCompanyDetails(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
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
			cabinetName =LapsConfigurations.getInstance().CabinetName;
			username =LapsConfigurations.getInstance().UserName;
			password =LapsConfigurations.getInstance().Password;
			outputXML = APCallCreateXML.APSelect("SELECT TRANSACTION_REFERENCE,REQUEST_CATEGORY,REQUEST_TYPE,CUSTOMER_ID,PRODUCT_TYPE,INF_LOAN_CURR,STANDALONE_LOAN,IF_PURCHASE_PRODUCT_CODE FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'"); // ATP-465 REYAZ 15-05-2024
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTCD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"pastDueFlag TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					transRefNo = xp.getValueOf("TRANSACTION_REFERENCE");	
					customerId = xp.getValueOf("CUSTOMER_ID");
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					requestType = xp.getValueOf("REQUEST_TYPE");
					productCode = xp.getValueOf("PRODUCT_TYPE");	
					loanCurrency = xp.getValueOf("INF_LOAN_CURR");
					stdLoanFlg = xp.getValueOf("STANDALONE_LOAN");
					 // ATP-461 REYAZ 14-05-2024
					if("1".equalsIgnoreCase(stdLoanFlg)) {
						stdLoanFlg = "Y";
					} else {
						stdLoanFlg = "N";
					}
					//END
					// ATP-465 REYAZ 15-05-2024 START
					 if(requestType.equalsIgnoreCase("IFA_CTP")) {
						productCode = xp.getValueOf("IF_PURCHASE_PRODUCT_CODE");	
					 }
					//END
//					compyType = xp.getValueOf("TSLM_COMPANY_TYPE");
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTCD002", "Customer Id: "+customerId, sessionId);
				}
			}		
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTCD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TradeTslmCompanyDetails call: inside");
		String inputXml = "";
		String outputXml = "<returnCode>0</returnCode>";
		if (!requestCategory.equalsIgnoreCase("IFCPC")) {
			try {
				inputXml = createInputXML();
				if (!prevStageNoGo && (!"".equalsIgnoreCase(customerId) && null != customerId)) {
					outputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
							"TradeTslmCompanyDetails outputXml ===> " + outputXml);
					if (outputXml == null || outputXml.equalsIgnoreCase("")) {
						outputXml = "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
					}
					responseHandler(outputXml, inputXml);
				} else {
					callStatus = "N";
					updateCallOutput(inputXml);
				}

			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTCD100",
						e.getMessage(), sessionId);
			}
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
			if(productCode.trim().equalsIgnoreCase("L092")||productCode.trim().equalsIgnoreCase("L128") || productCode.trim().equalsIgnoreCase("SCFP")){ //Condition Added for SCF Req Category
				compyType="B";
			} else if(productCode.trim().equalsIgnoreCase("L129")||productCode.trim().equalsIgnoreCase("L130")||productCode.trim().equalsIgnoreCase("L132")||productCode.trim().equalsIgnoreCase("L157")){
				compyType="S";
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
			if(!compyType.equalsIgnoreCase("")){
				if(compyType.equalsIgnoreCase("B")){
					compyType = "BUYER";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
				}else if(compyType.equalsIgnoreCase("S")){
					compyType = "SELLER";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
				}else{
					compyType = "BOTH";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "compyType ::"+compyType);
				}
			}
			inputXml="<?xml version=\"1.0\"?><APWebService_Input>"
					+ "<Option>WebService</Option>"
					+"<Calltype>WS-2.0</Calltype>"
					+"<InnerCallType>TFO_TSLMCorpLoanDetails</InnerCallType>"
					+"<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>"
					+"<SessionId>" + sessionId + "</SessionId>"
					+"<serviceName>ModTSLMCorpLoanDetails</serviceName>"
					+"<serviceAction>Modify</serviceAction>"
					+"<senderId>" + "WMS" + "</senderId>"
					+"<correlationId>"+refNo+"</correlationId>"
					+"<channelRefNumber>"+refNo+"</channelRefNumber>"
					+"<sysRefNumber>"+refNo+"</sysRefNumber>"
					+"<CUSTOMERID>"+customerId+"</CUSTOMERID>"
					+"<PRODUCTCODE>"+productCode+"</PRODUCTCODE>"
					+"<LOANCCY>"+loanCurrency+"</LOANCCY>"
					+"<STDLOANFLAG>"+stdLoanFlg+"</STDLOANFLAG>"
					+"<COMPANYTYPE>"+compyType+"</COMPANYTYPE>"
					+"<operationType>fetchTSLMCompanyDtl_Oper</operationType>"
					+"<winame>"+WI_NAME+"</winame>"
					+"</APWebService_Input>";
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTCD100", e.getMessage(), sessionId);
		}
		return inputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {			
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTCD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
				CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
			} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTCD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			String whereClause ="WINAME = '"+WI_NAME+ "'";
			returnCode = Integer.parseInt(xp.getValueOf("returnCode"));
			errorDescription = xp.getValueOf("errorDescription");
			if(returnCode == 0){
				APCallCreateXML.APDelete("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", 
						whereClause, sessionId);
				int counterPartyCount = xp.getNoOfFields("counterPartyList");
				XMLParser innerXmlParser = new XMLParser();
				String counterPartyList = "";
				StringBuffer strColumns1 = new StringBuffer();
				StringBuffer strValues1 = new StringBuffer();
				strColumns1.append("CID,PARTY_NAME,COUNTRY,NOA,WINAME,CHECKBOX");
				String sOutputlist = APCallCreateXML.APSelect("select COUNTERPARTY_CODE from TFO_DJ_TSLM_PO_INVOICE_DETAILS_TXN_DATA"
						             + " where wi_name='"+WI_NAME+"' AND ROWNUM=1");
				XMLParser xp1 =new XMLParser(sOutputlist);
				String selectedCpID=xp1.getValueOf("COUNTERPARTY_CODE");
				for (int i = 0; i < counterPartyCount; i++) {
					strValues1 = new StringBuffer();
					counterPartyList = xp.getNextValueOf("counterPartyList");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "counterPartyListData " + counterPartyList);
					innerXmlParser.setInputXML(counterPartyList);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "innerXmlParser " + innerXmlParser.toString());
					String Counter_Party_CID=innerXmlParser.getValueOf("cid");
					String Counter_Party_Name=innerXmlParser.getValueOf("countryPartyName");
					String Counter_Party_Country=innerXmlParser.getValueOf("country");
					String Notice_of_Acknowledgement=innerXmlParser.getValueOf("noa");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Counter_Party_CID " + Counter_Party_CID);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO," selectedCounterParty : " + selectedCpID);
					if(selectedCpID.trim().equalsIgnoreCase(Counter_Party_CID)){
						strValues1.append("'"+Counter_Party_CID+"', '"+Counter_Party_Name+"' ,'"+Counter_Party_Country+"', '"+Notice_of_Acknowledgement+"', '"+WI_NAME+"', 'true'");	


						String opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", strColumns1.toString(), 
								strValues1.toString(), sessionId);
						XMLParser xmlParser = new XMLParser(opXml);
						if(Integer.parseInt(xmlParser.getValueOf("MainCode")) == 11) {
							sessionId = SingleUserConnection.getInstance(100).getSession(cabinetName, username, 
									password);
							opXml = APCallCreateXML.APInsert("TFO_DJ_TSLM_COUNTER_PARTY_DETAILS", strColumns1.toString(), 
									strValues1.toString(), sessionId);
							xmlParser = new XMLParser(opXml);
						}
					}

				}
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TTCD090", "TTCD Successfully Executed", sessionId);			
			} else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTCD100", e.getMessage(), sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
                         //ATP-265 DATE - 12/12/2023 BY REYAZ
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'TradeTslmCompanyDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			if(callStatus.equals("Y") && !"".equalsIgnoreCase(transRefNo) && null != transRefNo
					&& categroyFlagUpdate()){
				executeDependencyCall();
			}  
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TTCD100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	
	public boolean categroyFlagUpdate() {
		if((requestCategory.equalsIgnoreCase("IFA") || requestCategory.equalsIgnoreCase("IFP") 
				|| requestCategory.equalsIgnoreCase("IFS") || requestCategory.equalsIgnoreCase("TL"))
				&& (requestType.equalsIgnoreCase("AM") || requestType.equalsIgnoreCase("STL")
						|| requestType.equalsIgnoreCase("TL_AM") || requestType.equalsIgnoreCase("TL_STL")
						||  requestType.equalsIgnoreCase("IFA_CTP"))){ // ATP-465 REYAZ 15-05-2024
			return true;
		}else {
			return false;
		}
	}
}
