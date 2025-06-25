package com.newgen.ao.laps.calls;

import java.util.HashMap;
import java.util.List;
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
import com.newgen.omni.jts.cmgr.XMLParser;

public class TradeGetCustomerSummary implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus;
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String customerId;
	private String status;
	private String reason;
	private String refNo;
	private String senderID;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;
	private String auditCallName = "TGCS";
	private String custFullName;
	private String profitCenterCode;
	private String custCategory;
	private String requestCategory;
	private String mobile;
	private String strRMCode;
	private String addressLine_1;
	private String addressLine_2;
	private String state;
	private String poBox;
	private String strRMName;
	private String fax;
	private String email;
	private String isCustVip;
	private String rmMail;
	private String rmPhone;
	private String tradeCustEmail;
	private String acctNo;
	private String grntAcctTitle = "";
	private String grntAcctCurr = "";
	private String chrgAcctNo;
	private String chrgAcctTitle = "";
	private String chrgAcctCurr = "";
	private String settlAcctNo;
	private String settlAcctTitle = "";
	private String settlAcctCurr = "";
	private String mode = "";
	private String finaloutputXml =""; //ATP-496 29-07-2024 REYAZ
	//ATP-510 26-08-2024 REYAZ START
	private String creditAcctNo; 
	private String creditAcctTitle = "";
	private String creditAcctCurr = "";
	//ATP-510 26-08-2024 REYAZ END

	public TradeGetCustomerSummary(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
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
			outputXML = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, CUSTOMER_ID, ACCOUNT_NUMBER, INF_CHARGE_ACC_NUM, INF_SETTLEMENT_ACC_NUM,INF_CREDIT_ACC_NUM FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TGCS001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					customerId = xp.getValueOf("CUSTOMER_ID");	
					chrgAcctNo = xp.getValueOf("INF_CHARGE_ACC_NUM");					
					settlAcctNo = xp.getValueOf("INF_SETTLEMENT_ACC_NUM");
					acctNo = xp.getValueOf("ACCOUNT_NUMBER");	
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");	
					creditAcctNo= xp.getValueOf("INF_CREDIT_ACC_NUM");	 //ATP-510 26-08-2024 REYAZ
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TGCS002", "Customer Id: "+customerId, sessionId);
				}
			}
			senderID = defaultMap.get("SENDER_ID");			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails call: inside" +prevStageNoGo);
		String inputXml = "" ;
		finaloutputXml = "<returnCode>0</returnCode>";
		try {			
			inputXml = createInputXML();
			if(!prevStageNoGo && (!"".equalsIgnoreCase(customerId) && null != customerId)){
//				outputXml = ExecuteXML.executeXML(inputXml);
				finaloutputXml = LapsConfigurations.getInstance().socket.connectToSocket(inputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FetchDetails outputXml ===> " + finaloutputXml);
				if(finaloutputXml==null || finaloutputXml.equalsIgnoreCase("")){
					finaloutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				}
				responseHandler(finaloutputXml, inputXml);
			} else {
				callStatus = "N";
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
		if(returnCode == 2){
			returnCode = 0;
                        finaloutputXml = "<returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+"</errorDescription>";
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml: " +finaloutputXml);
		return finaloutputXml;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXml=new StringBuilder();
		try {
			refNo = LapsUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>Customer_Information</Calltype>").append("\n")
			.append( "<Customer>").append("\n")
			.append( "<CUST_ID>"+ customerId + "</CUST_ID>").append("\n")
			.append( "<REF_NO>"+ refNo + "</REF_NO>").append("\n")
			.append( "<txnType>"+ "GetCustomerSummary"	+ "</txnType>").append("\n")
			.append( "<EngineName>"+LapsConfigurations.getInstance().CabinetName+"</EngineName>").append("\n")
			.append( "<USER>"+ "TP906079" + "</USER>").append("\n")
			.append( "<WiName>"+ this.WI_NAME + "</WiName>").append("\n")
			.append( "</Customer></APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp = "";
			outputXMLTemp =	CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TGCS004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
				finaloutputXml = outputXMLTemp;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output xml inside responseHandler :" + outputXML);
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "returnCode :" + returnCode);
			if(returnCode == 0 || returnCode == 2){
				callStatus = "Y";
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "TGCS090", "TGCS Successfully Executed", sessionId);
				custFullName = xp.getValueOf("FullName", "", true);
				strRMCode = xp.getValueOf("RMCode", "", true);
				strRMName = xp.getValueOf("RMName", "", true);
				addressLine_1 = xp.getValueOf("AddressLine_1", "", true);
				addressLine_2 = xp.getValueOf("AddressLine_2", "", true);
				state = xp.getValueOf("State", "", true);
				mobile = xp.getValueOf("Mobile", "", true);
				poBox = xp.getValueOf("POBox", "", true);
				custCategory = xp.getValueOf("CustCategory", "", true);
				fax = xp.getValueOf("Fax", "", true);
				email = xp.getValueOf("Email", "", true);
				profitCenterCode = xp.getValueOf("ProfitCenterCode", "", true)+" - "+ xp.getValueOf("ProfitCenterName", "", true);
				
				setAccountDetails(outputXML);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "custFullName :" + custFullName+
						"profitCenterCode:"+profitCenterCode);
			} else {
				callStatus = "N";
			}		 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'TradeGetCustomerSummary', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);

			if(callStatus.equals("Y")){
				isVIPCustomerCheck(customerId);
				fetchRMDeatilfrmETL(strRMCode);
				fetchTradeEmailDetailfrmETL(customerId);
				String columnList = "CUSTOMER_NAME,RM_CODE,RM_NAME,ADDRESS_LINE_1,ADDRESS_LINE_2,"
						+ "EMIRATES,MOBILE_NUMBER,PO_BOX,CUSTOMER_CATEGORY,FAX_NO,FCR_CUST_EMAIL_ID,SWIFT_CID,"
						+ "IS_CUSTOMER_VIP,RM_EMAIL,RM_MOBILE_NUMBER,TRADE_CUST_EMAIL_ID,"
						+ "INF_CHARGE_ACC_CURR,INF_CHARGE_ACC_TITLE,INF_SETTLEMENT_ACC_CURR,INF_SETTLEMENT_ACC_TITLE,"
						+ "GRNT_CHRG_ACC_TITLE,GRNT_CHRG_ACC_CRNCY,PROFIT_CENTER_CODE,INF_CREDIT_ACC_CURR,INF_CREDIT_ACC_TITLE";	//ATP-510 26-08-2024 REYAZ
				String valList3 = "'"+custFullName+"','"+strRMCode+"','"+strRMName+"','"+addressLine_1+"','"+addressLine_2+"','"+state+"','"+mobile+"','"+poBox+"','"+custCategory+"','"+fax+"'"
						+ ",'"+email+"','"+customerId+"','"+isCustVip+"','"+rmMail+"','"+rmPhone+"',"
						+ "'"+tradeCustEmail+"','"+chrgAcctCurr+"','"+chrgAcctTitle+"','"+settlAcctCurr+"','"
						+settlAcctTitle+"','"+grntAcctTitle+"','"+grntAcctCurr+"','"+profitCenterCode+"','"+creditAcctCurr+"','"+creditAcctTitle+"'"; 	//ATP-510 26-08-2024 REYAZ
				APCallCreateXML.APUpdate("EXT_TFO", columnList, valList3, " WI_NAME = '"+WI_NAME+"'", sessionId);
				executeDependencyCall();
			} else {
				returnCode = -1;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}

	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}
	private void isVIPCustomerCheck(String cid) {
		try {
			String str = "SELECT COUNT(1) CNT FROM LG_VIP_MASTER WHERE CUSTOMER_ID='"+ cid + "'";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"isVIPCustomerCheck   " + str);
			String  outputXML =  APCallCreateXML.APSelect("SELECT COUNT(1) CNT FROM LG_VIP_MASTER WHERE CUSTOMER_ID = '"+ cid + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					isCustVip =  "Yes";
				} else {
					isCustVip =  "No";
				}
			}
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
	}
	private void fetchRMDeatilfrmETL(String rmcode) {
		try {
			String str = "SELECT RM_EMAIL,RM_PHONE FROM USR_0_RM WHERE RM_CODE ='" + rmcode + "'";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchRMDeatilfrmETL   " + str);
			String sOutputlist = APCallCreateXML.APSelect("SELECT RM_EMAIL,RM_PHONE FROM USR_0_RM WHERE RM_CODE ='" + rmcode + "'");
			XMLParser xp = new XMLParser(sOutputlist);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				rmMail = xp.getValueOf("RM_EMAIL");   
				rmPhone = xp.getValueOf("RM_PHONE");
				}
			} 
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
	}
	
	private void fetchTradeEmailDetailfrmETL(String cid) {
		try {
			String str = "SELECT TRADE_FINANCE_SERVICE,EMAIL FROM TFO_DJ_TRADE_EMAIL_MAST WHERE CUST_ID ='"
					+ cid + "'";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"fetchRMDeatilfrmETL   " + str);
			String sOutputlist = APCallCreateXML.APSelect("SELECT TRADE_FINANCE_SERVICE, EMAIL FROM TFO_DJ_TRADE_EMAIL_MAST "
					+ "WHERE CUST_ID ='"+ cid +"'");
			XMLParser xp = new XMLParser(sOutputlist);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchDetails TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
				tradeCustEmail = xp.getValueOf("EMAIL");   
				}
			} 
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "TGCS100", e.getMessage(), sessionId);
		}
	}
	
	private void setAccountDetails(String outputXML){
		outputXML = outputXML.replaceAll("null", "");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside setAccountDetails: " + outputXML);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"chrgAcctNo: " + chrgAcctNo + ",settlAcctNo: " 
				+ settlAcctNo + ",acctNo: " + acctNo);
		XMLParser xpOuter = new XMLParser(outputXML);
		String sAccCASA = "";
		String sAccType = "";
		String sReturnCode = xpOuter.getValueOf("returnCode");
		if ("0".equalsIgnoreCase(sReturnCode) || "2".equalsIgnoreCase(sReturnCode)) {
			String sAccountNo = "";
			String sAccountTitle = "";
			String sAccCurrency = "";
			XMLParser xpInner = new XMLParser();
			int rowCount = xpOuter.getNoOfFields("Account");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"rowcount " + rowCount);
			List<String> lInner = null;
			for (int lstitemCount = 0; lstitemCount < rowCount; lstitemCount++) {
				if (lstitemCount == 0) {
					xpInner.setInputXML(xpOuter.getFirstValueOf("Account"));
				} else {
					xpInner.setInputXML(xpOuter.getNextValueOf("Account"));
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inner Parser " + xpInner);
				sAccountNo = xpInner.getValueOf("AccountNo");
				sAccountTitle = xpInner.getValueOf("AccountTitle");
				sAccCurrency = xpInner.getValueOf("CurrencyName");
				sAccCASA = xpInner.getValueOf("AccountType");
				sAccType = xpInner.getValueOf("AcctStatus");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Account " + sAccountNo + " Curr " 
						+ sAccCurrency);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sAccountNo.equals(chrgAcctNo): " 
						+ sAccountNo.equals(chrgAcctNo));
				if ("CASA".equalsIgnoreCase(sAccCASA) && ("ACCOUNT OPENED TODAY".equalsIgnoreCase(sAccType) 
						|| "ACCOUNT OPEN REGULAR".equalsIgnoreCase(sAccType))){
//					if(sAccountNo.equals(chrgAcctNo) && chrgAcctNo.equals(settlAcctNo)){
//						chrgAcctCurr = sAccCurrency;
//						chrgAcctTitle = sAccountTitle;
//						settlAcctCurr = sAccCurrency;
//						settlAcctTitle = sAccountTitle;
//						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"charge and settlement no are same ");
//						break;
//					} else 
					if(sAccountNo.equals(chrgAcctNo)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"chrg accnt no matched");
						if(sAccCurrency != null){
							chrgAcctCurr = sAccCurrency;
						}
						if(sAccountTitle != null){
							chrgAcctTitle = sAccountTitle;
						}
					}
					if(sAccountNo.equals(settlAcctNo)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sttlmnt accnt no matched");
						if(sAccCurrency != null){
							settlAcctCurr = sAccCurrency;
						}
						if(sAccountTitle != null){
							settlAcctTitle = sAccountTitle;
						}
					}
					//ATP-510 26-08-2024 REYAZ START
					if(sAccountNo.equals(creditAcctNo)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"credit accnt no matched");
						if(sAccCurrency != null){
							creditAcctCurr = sAccCurrency;
						}
						if(sAccountTitle != null){
							creditAcctTitle = sAccountTitle;
						}
					}
					//ATP-510 26-08-2024 REYAZ END
					if(sAccountNo.equals(acctNo)){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sttlmnt accnt no matched");
						if(sAccCurrency != null){
							grntAcctCurr = sAccCurrency;
						}
						if(sAccountTitle != null){
							grntAcctTitle = sAccountTitle;
						}
					}
				}
			}
		}
	}
	
}