package com.newgen.ao.laps.calls;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
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

public class FetchTransactionDetails implements ICallExecutor, Callable<String> {
	private String sessionId;
	private String WI_NAME;
	private String callStatus="N";
	private int stageID;
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String transactionReference;
	private String requestCategory;
	private String requestType;
	private String protradeFlag;
	private String status;
	private String customerId;
	private String reason;
	private String refNo;
	private String senderID;
	private String customerID;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private CallEntity callEntity;
	private String auditCallName = "FTD";
	private String customerIC;
	private int processDefId;
	private String sRelationType = "";
	private String otherRef = "";
	private String columnFetch="";
	private String mode = "";
	private HashMap<String, LinkedHashMap<Integer, HashMap<String, String>>> complexMap;
	private boolean skipCall = true;
	private String ruleFlag = "";
	private Map<String, String> fetchMap;
	private String finaloutputXml =""; //ATP-496 29-07-2024 REYAZ

	public FetchTransactionDetails(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity){
		processDefId = LapsConfigurations.getInstance().processDefIdTFO;
		this.WI_NAME = WI_NAME;
		stageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;
		if(attributeMap.containsKey("mode")){
			this.mode = attributeMap.get("mode");
		}
		if(attributeMap.containsKey("ruleFlag")){
			this.ruleFlag = attributeMap.get("ruleFlag");
		}
		fetchMap = ChannelCallCache.getInstance().getFetchMap();
		String outputXML;	
		String outputXMLFetch;	
		try {
			outputXML = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY, REQUEST_TYPE, PT_UTILITY_FLAG FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD001", "Started", sessionId);
			if(mainCode == 0){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"CustomerIC TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");	
					requestType = xp.getValueOf("REQUEST_TYPE");
					protradeFlag = xp.getValueOf("PT_UTILITY_FLAG");
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD002", "requestCategory: "+requestCategory, sessionId);
					LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD002", "requestType: "+requestType, sessionId);
					//					if(("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType)) 
					//							||  ("ELCB".equalsIgnoreCase(requestCategory) && ("ELCB_BL".equalsIgnoreCase(requestType)
					//									|| "ELCB_DISC".equalsIgnoreCase(requestType) || "ELCB_AM".equalsIgnoreCase(requestType))) 
					//									|| ("ILC".equalsIgnoreCase(requestCategory) && "ILC_AM".equalsIgnoreCase(requestType))
					//									|| "ILCB".equalsIgnoreCase(requestCategory) || "IC".equalsIgnoreCase(requestCategory)
					//									|| ("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType))
					//									|| ("EC".equalsIgnoreCase(requestCategory) && ("EC_AM".equalsIgnoreCase(requestType) 
					//											|| "EC_DISC".equalsIgnoreCase(requestType)))){
					//						if(("GRNT".equalsIgnoreCase(requestCategory) && "AM".equalsIgnoreCase(requestType)) ||
					//								("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_DISC".equalsIgnoreCase(requestType))
					//								|| ("ILC".equalsIgnoreCase(requestCategory) && "ILC_AM".equalsIgnoreCase(requestType))
					//								|| "ILCB_AC".equalsIgnoreCase(requestType) || "ILCB_BS".equalsIgnoreCase(requestType)
					//								|| "ILCB_BCDR".equalsIgnoreCase(requestType) || "IC_AC".equalsIgnoreCase(requestType) 
					//								|| "IC_BS".equalsIgnoreCase(requestType) || "ELCB_AM".equalsIgnoreCase(requestType)
					//								|| "EC_AM".equalsIgnoreCase(requestType) || "EC_DISC".equalsIgnoreCase(requestType)){
					//							columnFetch = "TRANSACTION_REFERENCE";
					//							otherRef = "TRANSACTION_UNB_REFERENCE";
					//						} else if("ELCB".equalsIgnoreCase(requestCategory) && "ELCB_BL".equalsIgnoreCase(requestType)) {
					//							columnFetch = "TRANSACTION_ADCB_LC_REFERENCE";
					//							otherRef = "TRANSACTION_UNB_LC_REFERENCE";
					//						} else if("TL".equalsIgnoreCase(requestCategory) && "TL_LD".equalsIgnoreCase(requestType)) {
					//							columnFetch = "BILL_LN_REFRNCE";
					//						}
					//						outputXMLFetch = APCallCreateXML.APSelect("SELECT "+columnFetch+" FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
					//						XMLParser xp1 = new XMLParser(outputXMLFetch);
					//						int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
					//						if(mainCode1 == 0){
					//							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FTD TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
					//							if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
					//								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside column "+columnFetch+" fetch: "+xp1.getValueOf(columnFetch));
					//								transactionReference = xp1.getValueOf(columnFetch);	
					//							}
					//						}
					//						skipCall = false;
					//					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"get fetch column from fetchMap "); 
					if(null != fetchMap.get("PT#"+requestCategory+"#"+requestType)) {
						columnFetch = fetchMap.get("PT#"+requestCategory+"#"+requestType);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"from fetchMap column: " + 
								fetchMap.get("PT#"+requestCategory+"#"+requestType));
					} else {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"no column found "); 
						columnFetch = "TRANSACTION_REFERENCE";
					}
					if("TRANSACTION_REFERENCE".equalsIgnoreCase(columnFetch)) {
						otherRef = "TRANSACTION_UNB_REFERENCE";
					} else if("TRANSACTION_ADCB_LC_REFERENCE".equalsIgnoreCase(columnFetch)) {
						otherRef = "TRANSACTION_UNB_LC_REFERENCE";
					}
					outputXMLFetch = APCallCreateXML.APSelect("SELECT "+columnFetch+" FROM EXT_TFO WHERE WI_NAME = N'" + WI_NAME + "'");
					XMLParser xp1 = new XMLParser(outputXMLFetch);
					int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
					if(mainCode1 == 0){
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FTD TotalRetrieved: "+Integer.parseInt(xp1.getValueOf("TotalRetrieved")));
						if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside column "+columnFetch+" fetch: "+xp1.getValueOf(columnFetch));
							transactionReference = xp1.getValueOf(columnFetch);	
						}
					}
					skipCall = false;
				}
			}
			senderID = attributeMap.get("SENDER_ID");	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchTransactionDetails attributeMap: "
					+attributeMap.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FTD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchTransactionDetails call: inside");
		String inputXml = "" ;
		finaloutputXml = "<returnCode>0</returnCode>";
		try {			
			if(!prevStageNoGo){	
				if(!skipCall){
					finaloutputXml = fetchTransactioContractDtls(transactionReference,requestCategory,requestType);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "FetchTransactionDetails outputXml ===> " + finaloutputXml);
					if(finaloutputXml==null || finaloutputXml.equalsIgnoreCase("") 
							|| "Please enter correct Transaction Ref. Number".equalsIgnoreCase(finaloutputXml)
							|| "Unable to fetch Contract details. Kindly Enter correct Reference Number.".equalsIgnoreCase(finaloutputXml)){
						finaloutputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :104 N");
						callStatus = "N";
					}
					responseHandler(finaloutputXml, inputXml);
				} else{
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :109 X");
					callStatus = "X";
					updateCallOutput(inputXml);
				}
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :114 N");
				callStatus = "N";
				updateCallOutput(inputXml);
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FTD100", e.getMessage(), sessionId);
		}
                if("Success".equalsIgnoreCase(finaloutputXml)){
			returnCode = 0;
			errorDescription = "Success";
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
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>WS-2.0</Calltype>").append("\n")
			.append("<InnerCallType>TFO_InqCustPersonalDetails</InnerCallType>").append("\n")			
			.append("<WiName>" + WI_NAME + "</WiName>").append("\n")
			.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
			.append("<senderId>" + "WMS" + "</senderId>").append("\n")
			.append("<cust_id>" + customerId + "</cust_id>").append("\n")			
			//.append("<txnType>" + txnType + "</txnType>").append("\n")
			.append("</APWebService_Input>");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, inputXml.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FTD100", e.getMessage(), sessionId);
		}
		return inputXml.toString();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		try {
			String outputXMLTemp = "";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FetchTransactionDetails attributeMap passed in "
					+ "dependency call: "+attributeMap.toString());
			outputXMLTemp =CallHandler.getInstance().executeDependencyCall(callEntity, attributeMap, sessionId, WI_NAME, prevStageNoGo);
			if(!outputXMLTemp.isEmpty()){
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD004", "DependencyCall"+ callEntity.getDependencyCallID(), sessionId);
				finaloutputXml = outputXMLTemp;
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FTD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public void responseHandler(String outputXML, String inputXml) throws Exception {
		try {
			XMLParser xp = new XMLParser(outputXML);
			returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
			errorDescription = xp.getValueOf("errorDescription", "", true);
			errorDetail = xp.getValueOf("errorDetail", "", true);
			status = xp.getValueOf("Status", "", true);
			reason = xp.getValueOf("Reason", "", true);
			if(("Y").equals(callStatus)){
				LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_DEBUG, WI_NAME, auditCallName, "FTD090", "FTD Successfully Executed", sessionId);
			}			 
			updateCallOutput(inputXml);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FTD100", e.getMessage(), sessionId);
		}		
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		try {
			if("R".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "DMR".equalsIgnoreCase(mode)){
				stageID = -1 * stageID;
			}
			String valList = "'"+ WI_NAME +"',"+ stageID +", 'FetchTransactionDetails', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"'";
			APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
					+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
			if("Y".equals(callStatus)){
				attributeMap.put("transactionReferenceColumn", columnFetch);
				attributeMap.put("transactionReferenceValue", transactionReference);
				executeDependencyCall();
			}/* else if("Y".equalsIgnoreCase(ruleFlag)){
				APCallCreateXML.APUpdate("EXT_TFO", "ROUTE_TO_REPAIR", "'Y'", "WI_NAME = '"+WI_NAME+"'", 
						sessionId); //new change 21march
				ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME, 1);
			}*/
		} catch(Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyDBLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "FTD100", e.getMessage(), sessionId);
		}
	}

	@Override
	public String executeCall(HashMap<String, String> map) throws Exception {
		return call();
	}

	@SuppressWarnings("unchecked")
	public String fetchTransactioContractDtls(String refNo, String sRequestCategory,
			String requestType) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside the fetchTransactioDtls  " + refNo
				+ " and req category : " + sRequestCategory);
		String retMsg = "";
		try {
			String whereConditionField = "GTEE_NUMBER";
			String columnName = "";
			String tableName = "";
			String whereClause = "";
			String tagName = "";

			List<List<String>> sOutputlist = null;
			if ("GRNT".equalsIgnoreCase(sRequestCategory)|| "SBLC".equalsIgnoreCase(sRequestCategory)//added by mansi
					||"SG_SD".equalsIgnoreCase(requestType)) {  //Shipping_Gtee_10
				columnName = "CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT,DECODE(IS_CUSTOMER_VIP,'0',null,IS_CUSTOMER_VIP) IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,LC_OS_AMT,IS_ACTIVE,TO_CHAR(TO_DATE(EXP_DATE,'DD-MON-YYYY'),'YYYY-MM-DD') EXP_DATE1, TRNS_TENOR,"
						+ "DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) "
						+ "TRNS_THIRD_PARTY,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";

				tagName = "CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT, IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,LC_OS_AMT,IS_ACTIVE, EXP_DATE1, TRNS_TENOR,"
						+ " IS_VALID_ACC,TRNS_THIRD_PARTY, ACCOUNT_NUMBER, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";
				tableName = "TFO_WMS_MASTER";
				whereClause	= whereConditionField + "='" + refNo + "'";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Trns Detls query " + columnName + tableName+ whereClause);
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
			} else if ("IFS".equalsIgnoreCase(sRequestCategory)
					|| "IFP".equalsIgnoreCase(sRequestCategory)
					|| "TL".equalsIgnoreCase(sRequestCategory)) {
				columnName = "CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT,IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,OS_AMT,DECODE(IS_ACTIVE,'0',null,IS_ACTIVE) IS_ACTIVE ,TENOR_DAY,DECODE(TENOR_BASE,'0',null,TENOR_BASE) TENOR_BASE, TENOR_BASE_DTLS,DECODE(CHARGE_ACC_NUM,0,null,CHARGE_ACC_NUM) CHARGE_ACC_NUM,DECODE(STTLMNT_ACC_NUM,0,null,STTLMNT_ACC_NUM) STTLMNT_ACC_NUM,DECODE(CRDT_ACC_NUM,0,null,CRDT_ACC_NUM) CRDT_ACC_NUM,"
						+ "REMITTANCE_CURR,REMITTANCE_AMT,BILL_LN_REFRNCE,LOAN_CURR,TO_CHAR(BASE_DOC_DT,'YYYY-MM-DD') BASE_DOC_DT,"
						+ "TO_CHAR(MATURITY_DATE,'YYYY-MM-DD') MATURITY_DATE ,"
						+ "PMNT_AUTH_OTH_SYS,FND_TRNSFER_FCUBS_REF,PMNT_HUB_REF,UDF_FIELD_CHK,DECODE(BILL_CUST_HLDING_ACC_US,0,null,BILL_CUST_HLDING_ACC_US) BILL_CUST_HLDING_ACC_US,"
						+ " OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";

				tagName = "CUSTOMER_ID,COMPANY_NAME,PROFIT_CENTER_CODE,CUSTOMER_CATEGORY,EMAIL_ID,MOBILE_NUMBER,ADDRESS_LINE_1,"
						+ "ADDRESS_LINE_2,EMIRATES,PO_BOX,RM_NAME, RM_MOBILE_NUMBER,GTEE_NUMBER,CURRENCY,AMOUNT,IS_CUSTOMER_VIP,PRODUCT,"
						+ "TRNS_BRN_CODE,OS_AMT, IS_ACTIVE ,TENOR_DAY, TENOR_BASE, TENOR_BASE_DTLS, CHARGE_ACC_NUM, STTLMNT_ACC_NUM, CRDT_ACC_NUM,"
						+ "REMITTANCE_CURR,REMITTANCE_AMT,BILL_LN_REFRNCE,LOAN_CURR, BASE_DOC_DT,"
						+ " MATURITY_DATE ,"
						+ "PMNT_AUTH_OTH_SYS,FND_TRNSFER_FCUBS_REF,PMNT_HUB_REF,UDF_FIELD_CHK, BILL_CUST_HLDING_ACC_US,"
						+ " OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";
				tableName ="TFO_WMS_MASTER_IF";
				whereClause	= whereConditionField + "='" + refNo + "' AND REQ_CAT='" + sRequestCategory + "'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
			} else if ("ELC".equalsIgnoreCase(sRequestCategory)
					|| "ELCB_BL".equalsIgnoreCase(requestType)
					|| "SG_NILC".equalsIgnoreCase(requestType)) { //Shipping_Gtee_10        // Export LC
				if ("ELCB_BL".equalsIgnoreCase(requestType)) {
					sRequestCategory = "ELC";
				}
				columnName = "GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,"
						+ "TRNS_BAL,IS_ACTIVE,PRODUCT,DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER,TO_CHAR(EXP_DATE,'YYYY-MM-DD') EXP_DATE1,"
						+ "DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) TRNS_THIRD_PARTY,LC_CORRSPNDNT_BNK,"
						+ "LC_CONF_AMNT, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";

				tagName = "GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,"
						+ "TRNS_BAL,IS_ACTIVE,PRODUCT, IS_VALID_ACC, ACCOUNT_NUMBER, EXP_DATE1,"
						+ " TRNS_THIRD_PARTY,LC_CORRSPNDNT_BNK,"
						+ "LC_CONF_AMNT, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";
				tableName = " TFO_WMS_MASTER_IELC ";
				whereClause	=  whereConditionField + "='" + refNo + "' AND REQ_CAT='" + sRequestCategory + "'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
			} else if ("ILC".equalsIgnoreCase(sRequestCategory)
					|| "ILCB_BL".equalsIgnoreCase(requestType)) { // Import LC
				if ("ILCB_BL".equalsIgnoreCase(requestType)) {
					sRequestCategory = "ILC";
				}
				columnName = "GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,"
						+ "TRNS_BAL,IS_ACTIVE,PRODUCT,DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(ACCOUNT_NUMBER,0,null,ACCOUNT_NUMBER) ACCOUNT_NUMBER,TO_CHAR(EXP_DATE,'YYYY-MM-DD') EXP_DATE1,"
						+ "DECODE(TRNS_THIRD_PARTY,0,null,TRNS_THIRD_PARTY) TRNS_THIRD_PARTY,LC_CORRSPNDNT_BNK,LC_CONF_AMNT,"
						+ " OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";

				tagName = "GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,"
						+ "TRNS_BAL,IS_ACTIVE,PRODUCT, IS_VALID_ACC, ACCOUNT_NUMBER, EXP_DATE1,"
						+ "TRNS_THIRD_PARTY,LC_CORRSPNDNT_BNK,LC_CONF_AMNT,"
						+ " OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE";
				tableName =	" TFO_WMS_MASTER_IELC ";
				whereClause	=  whereConditionField + "='" + refNo + "' AND REQ_CAT='" + sRequestCategory + "'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
			} else if ("ILCB".equalsIgnoreCase(sRequestCategory)
					|| "ELCB".equalsIgnoreCase(sRequestCategory)
					|| "EC".equalsIgnoreCase(sRequestCategory)
					|| "IC".equalsIgnoreCase(sRequestCategory)
					|| "DBA".equalsIgnoreCase(sRequestCategory)) {
				columnName = "GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,TRNS_BAL,"
						+ "IS_ACTIVE,PRODUCT,TENOR_DAYS,DECODE(TENOR_BASE,'0',null,TENOR_BASE) TENOR_BASE,TENOR_BASE_DTLS,TO_CHAR(BASE_DOC_DT,'YYYY-MM-DD') BASE_DOC_DT,"
						+ "TO_CHAR(MATURITY_DATE,'YYYY-MM-DD') MATURITY_DATE,DECODE(IS_VALID_ACC,0,null,IS_VALID_ACC) IS_VALID_ACC,DECODE(STL_ACC_NUMBER,0,null,STL_ACC_NUMBER) STL_ACC_NUMBER,DECODE(CHRG_ACC_NUMBER,0,null,CHRG_ACC_NUMBER) CHRG_ACC_NUMBER,"
						+ "PAYMENT_MODE,OUR_LC_REF_NO,BILLS_CORRSPNDNT_BNK, OTHER_TRAN_REF_NUM, ISSUING_CENTER,BRN_CODE,"
						+ "BILL_TYPE,IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION";

				tagName = "GTEE_NUMBER,CUSTOMER_ID,CURRENCY,AMOUNT,TRNS_BRN_CODE,TRNS_BAL,"
						+ "IS_ACTIVE,PRODUCT,TENOR_DAYS, TENOR_BASE,TENOR_BASE_DTLS, BASE_DOC_DT,"
						+ " MATURITY_DATE, IS_VALID_ACC, STL_ACC_NUMBER, CHRG_ACC_NUMBER,"
						+ "PAYMENT_MODE,OUR_LC_REF_NO,BILLS_CORRSPNDNT_BNK, OTHER_TRAN_REF_NUM,ISSUING_CENTER,BRN_CODE,"
						+ "BILL_TYPE,IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION";
				tableName ="  TFO_WMS_MASTER_IECLCB  " ;
				whereClause = whereConditionField + "='" + refNo + "' AND REQ_CAT='" + sRequestCategory + "'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
			}
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"OutputList " + sOutputlist + sRequestCategory);

			if (("GRNT".equalsIgnoreCase(sRequestCategory)||"SBLC".equalsIgnoreCase(sRequestCategory)//added by mansi
					|| "IFP".equalsIgnoreCase(sRequestCategory)
					|| "IFS".equalsIgnoreCase(sRequestCategory) || "TL".equalsIgnoreCase(sRequestCategory)
					||"SG_SD".equalsIgnoreCase(requestType)) ) { 
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside if "+ sRequestCategory);
				retMsg = setTransactionData(sOutputlist, sRequestCategory, requestType);
			}  else if (("EC".equalsIgnoreCase(sRequestCategory)
					|| "IC".equalsIgnoreCase(sRequestCategory)
					|| "DBA".equalsIgnoreCase(sRequestCategory)
					|| "ILC".equalsIgnoreCase(sRequestCategory)
					|| "ELC".equalsIgnoreCase(sRequestCategory)
					|| "ILCB".equalsIgnoreCase(sRequestCategory) 
					|| "ELCB".equalsIgnoreCase(sRequestCategory))
					||"SG".equalsIgnoreCase(sRequestCategory)) { 
				if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
					retMsg = setTransactionDataLc(refNo,sOutputlist, sRequestCategory,  requestType);
				} else {
					retMsg = setTransactionDataLcFCUBS(refNo,sOutputlist, sRequestCategory,  requestType );
				}
			} else { 
				retMsg = "Please enter correct Transaction Ref. Number";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :331 N");
				callStatus = "N";
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in fetchTransactionDtls: "+e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"return message from fetchTransactionDtls: "+retMsg);
		if("Y".equalsIgnoreCase(callStatus))
			retMsg = "Success";
		return retMsg;
	}
	private String setTransactionData(List<List<String>> recordList, String reqCat, String requestType) {
		String retMsg = "";
		HashMap<String, String> attribMap = new HashMap<String, String>();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"recordList" + recordList);
		try {
			if (recordList != null && !recordList.isEmpty() && !recordList.get(0).isEmpty()){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Start Setting data in setTransactionData"+ recordList.get(0).get(0) + " recordList.get(0).get(13) "
						+ recordList.get(0).get(13));
				attribMap.put("PROFIT_CENTER_CODE",(recordList.get(0)).get(2));// PROFIT_CENTER_CODE
				attribMap.put("CUSTOMER_CATEGORY", (recordList.get(0)).get(3));// CUSTOMER_CATEGORY
				attribMap.put("FCR_CUST_EMAIL_ID", (recordList.get(0)).get(4));// EMAIL_ID
				attribMap.put("MOBILE_NUMBER", (recordList.get(0)).get(5));// MOBILE_NUMBER
				attribMap.put("ADDRESS_LINE_1", (recordList.get(0)).get(6));// ADDRESS_LINE_1
				attribMap.put("ADDRESS_LINE_2", (recordList.get(0)).get(7));// ADDRESS_LINE_2
				attribMap.put("EMIRATES", (recordList.get(0)).get(8));// EMIRATES
				attribMap.put("PO_BOX", (recordList.get(0)).get(9));// PO_BOX
				attribMap.put("RM_NAME", (recordList.get(0)).get(10));// RM_NAME
				attribMap.put("RM_MOBILE_NUMBER", (recordList.get(0)).get(11));// RM_MOBILE_NUMBER
				//attribMap.put(transFieldName, (recordList.get(0)).get(12));// GTEE_NUMBER
				attribMap.put("IS_CUSTOMER_VIP", (recordList.get(0)).get(15));// IS_CUSTOMER_VIP
				attribMap.put("PRODUCT_TYPE", (recordList.get(0)).get(16));// PRODUCT
				attribMap.put("RELATIONSHIP_TYPE",(recordList.get(0)).get(17));// TRNS_BRN_CODE
				if ("GRNT".equalsIgnoreCase(reqCat) && ("NI".equalsIgnoreCase(requestType) || "GA".equalsIgnoreCase(requestType))) {
					attribMap.put("TRNS_BAL", (recordList.get(0)).get(18));// OS_AMT
					attribMap.put("TRN_STATUS", (recordList.get(0)).get(19)); // IS_ACTIVE
					attribMap.put("TRANSACTION_CURRENCY",(recordList.get(0)).get(13));// CURRENCY
					attribMap.put("TRANSACTION_AMOUNT",(recordList.get(0)).get(14));// AMOUNT
				} else if ("SBLC".equalsIgnoreCase(reqCat) && ("SBLC_NI".equalsIgnoreCase(requestType))){//added by mansi
					attribMap.put("TRNS_BAL", (recordList.get(0)).get(18));// OS_AMT
					attribMap.put("TRN_STATUS", (recordList.get(0)).get(19)); // IS_ACTIVE
					attribMap.put("TRANSACTION_CURRENCY",(recordList.get(0)).get(13));// CURRENCY
					attribMap.put("TRANSACTION_AMOUNT",(recordList.get(0)).get(14));// AMOUNT
				}else if (!("GRNT".equalsIgnoreCase(reqCat)|| "SBLC".equalsIgnoreCase(reqCat))) {//added by mansi
					attribMap.put("TRNS_BAL", (recordList.get(0)).get(18));// OS_AMT
					attribMap.put("TRN_STATUS", (recordList.get(0)).get(19)); // IS_ACTIVE
					attribMap.put("TRANSACTION_CURRENCY",(recordList.get(0)).get(13));// CURRENCY
					attribMap.put("TRANSACTION_AMOUNT",(recordList.get(0)).get(14));// AMOUNT
				}
				if ("GRNT".equalsIgnoreCase(reqCat)||"SBLC".equalsIgnoreCase(reqCat)) {//added by mansi
					attribMap.put("EXP_DATE", (recordList.get(0)).get(20)); // EXP_DATE1
					attribMap.put("TRN_TENOR", (recordList.get(0)).get(21));// TRNS_TENOR
					//					attribMap.put("IS_ACC_VALID", (recordList.get(0)).get(22));// IS_VALID_ACC
					attribMap.put("TRN_THIRD_PARTY",(recordList.get(0)).get(23));// TRNS_THIRD_PARTY
					//					attribMap.put("ACCOUNT_NUMBER",(recordList.get(0)).get(24));// ACCOUNT_NUMBER
					if(recordList.get(0).get(26) != null && !recordList.get(0).get(26).equalsIgnoreCase("")){
						attribMap.put("BRANCH_CITY", (recordList.get(0)).get(26));// ISSUING_CENTER
						attribMap.put("ASSIGNED_CENTER",(recordList.get(0)).get(26));// PROCESSING_CENTER same
					}
					if("AM".equalsIgnoreCase(requestType)||"SBLC_AM".equalsIgnoreCase(requestType)) {//added by mansi
						attribMap.put("IS_ACC_VALID", (recordList.get(0)).get(22));// IS_VALID_ACC
						attribMap.put("ACCOUNT_NUMBER",(recordList.get(0)).get(24));// ACCOUNT_NUMBER
					}
				} else if (("IFS".equalsIgnoreCase(reqCat))
						|| ("IFP".equalsIgnoreCase(reqCat))
						|| ("TL".equalsIgnoreCase(reqCat))) {
					attribMap.put("INF_TENOR_DAYS",(recordList.get(0)).get(20));// TENOR_DAY
					attribMap.put("INF_TENOR_BASE",(recordList.get(0)).get(21));// TENOR_BASE
					attribMap.put("INF_ACTUAL_TENOR_BASE",(recordList.get(0)).get(22));// TENOR_BASE_DTLS
					//					attribMap.put("INF_CHARGE_ACC_NUM",(recordList.get(0)).get(23));// CHARGE_ACC_NUM
					//					attribMap.put("INF_SETTLEMENT_ACC_NUM",(recordList.get(0)).get(24));// STTLMNT_ACC_NUM
					attribMap.put("INF_CREDIT_ACC_NUM",(recordList.get(0)).get(25));// CRDT_ACC_NUM
					attribMap.put("REMITTANCE_CURR",(recordList.get(0)).get(26));// REMITTANCE_CURR
					attribMap.put("REMITTANCE_AMT",(recordList.get(0)).get(27));// REMITTANCE_AMT
					attribMap.put("BILL_LN_REFRNCE",(recordList.get(0)).get(28));// BILL_LN_REFRNCE
					attribMap.put("INF_LOAN_CURR",(recordList.get(0)).get(29));// LOAN_CURR
					attribMap.put("PMNT_AUTH_OTH_SYS",(recordList.get(0)).get(32));// PMNT_AUTH_OTH_SYS
					attribMap.put("FND_TRNSFER_FCUBS_REF",(recordList.get(0)).get(33));// FND_TRNSFER_FCUBS_REF
					attribMap.put("PMNT_HUB_REF", (recordList.get(0)).get(34));// PMNT_HUB_REF
					attribMap.put("UDF_FIELD_CHK",(recordList.get(0)).get(35));// UDF_FIELD_CHK
					//					attribMap.put("BILL_CUST_HLDING_ACC_US",(recordList.get(0)).get(36));// BILL_CUST_HLDING_ACC_US
					//attribMap.put(transRefOutFieldName,(recordList.get(0)).get(37));// OTHER_TRAN_REF_NUM
					if(recordList.get(0).get(38) != null && !recordList.get(0).get(38).equalsIgnoreCase("")){
						attribMap.put("BRANCH_CITY", (recordList.get(0)).get(38));// ISSUING_CENTER
						attribMap.put("ASSIGNED_CENTER",(recordList.get(0)).get(38));
					}
					try {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Dates " + ((recordList.get(0)).get(30)));
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Dates " + ((recordList.get(0)).get(31)));
						attribMap.put("INF_BASE_DOC_DATE", recordList.get(0).get(30));
						attribMap.put("INF_MATURITY_DATE", recordList.get(0).get(31));
					} catch (Exception localException) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"localException: "+localException);
					}
					//seFTDDetails(reqCat);

				} else if("SG_SD".equalsIgnoreCase(requestType)){	//Shipping_Gtee_11
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"In setTransaction Data for SG_Scan Document");
					if(!recordList.get(0).get(26).equalsIgnoreCase("")){
						attribMap.put("BRANCH_CITY",(recordList.get(0)).get(26));// ISSUING_CENTER
						attribMap.put("ASSIGNED_CENTER",(recordList.get(0)).get(26));// PROCESSING_CENTER
					}
				}
				String sWhere = " WI_NAME = '"+WI_NAME+"'";
				if("DC".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "C".equalsIgnoreCase(mode)
						|| "R".equalsIgnoreCase(mode)){
					ProdCreateXML.APUpdateFromMap(sessionId,"EXT_TFO",attribMap,sWhere);
				}
			}
		} catch (Exception e1) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in setting value from outputlist setTransactionData GTEE "+ e1);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"set transacntion data recordlist " + recordList);
		retMsg = setTransactionDataFCUBS(recordList, reqCat, requestType);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Set data without issue- CPD");
		return retMsg;
	}

	@SuppressWarnings("unchecked")
	private String setTransactionDataFCUBS(List<List<String>> recordList, String reqCat,  String requestType) {
		String retMsg = "";
		try {
			String columnName = "";
			String tableName = "";
			String whereClause = "";
			String tagName = "";
			String sFetchedBranchCode = "";
			String sContractRefNo = "";
			sRelationType = "";
			List<List<String>> sOutputlist = null;
			sContractRefNo = transactionReference;
			if ("GRNT".equalsIgnoreCase(reqCat)|| "SBLC".equalsIgnoreCase(reqCat)|| "IFS".equalsIgnoreCase(reqCat) ||"SG".equalsIgnoreCase(reqCat)
					|| "IFP".equalsIgnoreCase(reqCat)
					|| "TL".equalsIgnoreCase(reqCat)) { //sblc added by mansi
				if ("IFS".equalsIgnoreCase(reqCat)|| "IFP".equalsIgnoreCase(reqCat)|| "TL".equalsIgnoreCase(reqCat)) {
					if (recordList != null && !recordList.isEmpty()) {
						sFetchedBranchCode = recordList.get(0).get(39).equals("") ? sContractRefNo.substring(0, 3) : recordList.get(0)
								.get(39);
					} else {
						sFetchedBranchCode = sContractRefNo.substring(0, 3);
					}
				} else if ("GRNT".equalsIgnoreCase(reqCat)||"SBLC".equalsIgnoreCase(reqCat)) {//sblc added by mansi
					if (recordList != null && !recordList.isEmpty()) {
						sFetchedBranchCode = recordList.get(0).get(27).equals("") ? sContractRefNo.substring(0, 3) : recordList.get(0).get(27);
					} else {
						sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sFetchedBranchCode " + sFetchedBranchCode);
				}
				columnName = "RELATIONSHIP_TYPE";
				tagName = "RELATIONSHIP_TYPE";
				tableName = "TFO_DJ_BRN_REL_TYPE_MAP_MAST ";
				whereClause = "BRANCH_CODE='" + sFetchedBranchCode + "'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
				if(sOutputlist != null){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Relationship Type returned "+ sOutputlist.get(0).get(0).toString());
					sRelationType = sOutputlist.get(0).get(0);
					sOutputlist = null;
				}
				columnName = "OPERATION_NAME";
				tagName = "OPERATION_NAME";
				tableName =  "TFO_DJ_INQUIRY_OPERATION_MST ";
				whereClause =  "REQUEST_CATEGORY_CODE='" + reqCat + "' AND REQUEST_TYPE_CODE = '" + requestType + "' AND BRANCH_CODE='"
						+ sRelationType + "' AND PROCESS_TYPE='BAU'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
				if(sOutputlist != null){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Operation Name returned "
							+ sOutputlist.get(0).get(0).toString());
				}
				if (!sOutputlist.equals(null)) {
					if (!sOutputlist.get(0).get(0).equalsIgnoreCase("NA")) {
						String returnFetchMessage[] = fetchCustomerContractData(sContractRefNo, sOutputlist.get(0).get(0).toString(), 
								sFetchedBranchCode,reqCat, requestType).split("##");
						int statusCode = Integer.parseInt(returnFetchMessage[0]);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returnFetchMessage n status: "+returnFetchMessage.toString()+"->"+statusCode);
						if(returnFetchMessage.length>1){
							retMsg = returnFetchMessage[1];
						}
						if (statusCode == 2) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"customerID : "+customerID);
							if (("").equals(customerID)) {
								//JOptionPane.showMessageDialog(null,"Unable to fetch Contract details. Kindly Enter correct Reference Number.");
								retMsg = "Unable to fetch Contract details. Kindly Enter correct Reference Number.";
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :501 N");
								callStatus = "N";
							} else {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :504 Y");
								callStatus = "Y"; 
							}
						}
					} else {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :509 Y");
						callStatus = "Y"; 
					}
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :513 N");
					callStatus = "N";
				}
				//				else {
				//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :517 N");
				//					callStatus = "N";
				//				}
			}
		}catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Fetch inquiry service excp " +e);
		}
		return retMsg;
	}
	public String fetchCustomerContractData(String sContractRefNo,String sFCUBSEnquiryMethod, String sBranchCode, String reqCategory,
			String requestType) {
		return setContractData(fetchContractData(sContractRefNo,sFCUBSEnquiryMethod,sBranchCode),reqCategory,requestType,sBranchCode);
	}
	private String getTradeLCContractInputXML(String sContractRef,
			String sSeqNo, String sUserName, String sOperationName,
			String sBranchCode) {
		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input><Option>WebService</Option><Calltype>WS-2.0</Calltype><InnerCalltype>FCUBSTradeContractOps</InnerCalltype><Customer>"
				+ "<refNo>"
				+ sSeqNo
				+ "</refNo>"
				+ "<contractRefNo>"
				+ sContractRef
				+ "</contractRefNo>"
				+ "<branchCode>"
				+ sBranchCode
				+ "</branchCode>"
				+ "<USER>"
				+ sUserName
				+ "</USER>"
				+ "<serviceName>InqCustomerTradeJourney</serviceName>"
				+ "<senderID>WMS</senderID>"
				+ "<WiName>"
				+ WI_NAME
				+ "</WiName>"
				+ "<operationType>"
				+ sOperationName
				+ "</operationType>" + "</Customer>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"input XML: "+sInputXML);
		sInputXML+="<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>"
				+ "<SessionId>" + sessionId + "</SessionId>"
				+ "</APWebService_Input>";
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"input XML: "+sInputXML);
		return sInputXML;
	}
	@SuppressWarnings("static-access")
	private String fetchContractData(String sContractRef, String sOperationName, String sBranchCode) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside fetchContractData..");
		String sInput = "";
		String sOutput="";
		sInput = getTradeLCContractInputXML(sContractRef, LapsUtils.getInstance().generateSysRefNumber(),  "TP906079" , sOperationName, sBranchCode);
		try {
			//			sOutput =  ExecuteXML.executeXML(sInput);
			sOutput = LapsConfigurations.getInstance().socket.connectToSocket(sInput);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Error in fetchContractData: ");	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);	
		}	
		return sOutput;
	}

	@SuppressWarnings("unchecked")
	private String setContractData(String resContractData,String reqCategory,String requestType,String sBranchCode) {
		String sContractStatus = "";
		String sTXNCurrency = "";
		String sTXNAmount = "";
		String sTXNBalance = "";
		String custid = "";
		String sMessege = "";
		String expDate = "";
		String trnTenor = "";
		String tolerance = "";
		String productCode = "";
		String guaranteeExpDate = "";
		String sConfirmedAmount = "";
		String sPositiveTolerance = "";
		String sNegativeTolerance = "";
		String sFromPlace = "";
		String sToPlace = "";
		String sLatestShipmentDate = "";
		String sPortOfDischarge = "";
		String sPortOfLoading = "";
		String sGoodsDescription = "";
		String sLinkageRefNo = "";
		String sLinkagePercent = "";
		String sMaturityDate = "";
		String sTenorDays = "";
		String sBaseDate = "";
		String sPrincipalOutStanding = "";
		String sOurLCNumber = "";
		String sShipmentFrom = "";
		String sShipmentTo = "";
		String sValueDate = "";
		String operationCode="";
		String sBaseDateDescription = "";
		String sSwiftCid = "";
		String stage = "";
		String sCustomerName ="";
		int iCDError = 0;
		String spartyType=""; 
		int setContractDataStatus = 0;
		String sDiscountOnAccept = ""; //US362
		int setContractDataStatus1 = 0;
		int setContractDataStatus2 = 0; //US362
		resContractData = resContractData.replace("null", "");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"resContractData   >>>>>>>>" + resContractData);
		XMLParser xmlDataParser = new XMLParser(resContractData);
		String sReturnCode = xmlDataParser.getValueOf("returnCode");
		HashMap<String, String> attribMap = new HashMap<String, String>();
		try {
			if ("0".equalsIgnoreCase(sReturnCode)) { 
				iCDError = 1;
				sContractStatus = xmlDataParser.getValueOf("contractStatus");
				sTXNCurrency = xmlDataParser.getValueOf("contractCurrency");
				sTXNAmount = xmlDataParser.getValueOf("contractAmount");
				sTXNBalance = xmlDataParser.getValueOf("outstandingLiability");
				custid = xmlDataParser.getValueOf("customerid");
				expDate = xmlDataParser.getValueOf("ExpiryDate").equals("null") ? "" :xmlDataParser.getValueOf("ExpiryDate").equals("") ? "" :xmlDataParser.getValueOf("ExpiryDate");
				guaranteeExpDate = xmlDataParser.getValueOf("guaranteeExpiryDate").equals("null") ? "" :xmlDataParser.getValueOf("guaranteeExpiryDate").equals("") ? "" :xmlDataParser.getValueOf("guaranteeExpiryDate");
				productCode = xmlDataParser.getValueOf("productCode");
				sConfirmedAmount = xmlDataParser.getValueOf("confirmedAmount");
				sPositiveTolerance = xmlDataParser.getValueOf("positiveTolerance");
				sNegativeTolerance = xmlDataParser.getValueOf("negativeTolerance");
				sFromPlace = xmlDataParser.getValueOf("fromPlace");
				sToPlace = xmlDataParser.getValueOf("toPlace");
				sLatestShipmentDate = xmlDataParser.getValueOf("latestShipmentDate");
				sPortOfDischarge = xmlDataParser.getValueOf("portOfDischarge");
				sPortOfLoading = xmlDataParser.getValueOf("portOfLoading");
				sGoodsDescription = xmlDataParser.getValueOf("goodsDescription");
				sLinkageRefNo = xmlDataParser.getValueOf("linkageReferenceNumber");
				sLinkagePercent = xmlDataParser.getValueOf("collateralPercent");
				spartyType= xmlDataParser.getValueOf("contractLimitPartyType");  
				sMaturityDate = xmlDataParser.getValueOf("MaturityDate").equals("null") ? "" :xmlDataParser.getValueOf("MaturityDate").equals("") ? "" :xmlDataParser.getValueOf("MaturityDate");
				sTenorDays = xmlDataParser.getValueOf("TenorDays");
				sPrincipalOutStanding = xmlDataParser.getValueOf("principalOutStanding");
				sOurLCNumber = xmlDataParser.getValueOf("OurLCNumber");
				sShipmentFrom = xmlDataParser.getValueOf("ShipmentFrom");
				sShipmentTo = xmlDataParser.getValueOf("ShipmentTo");
				sBaseDate = xmlDataParser.getValueOf("BaseDate").equals("null") ? "" :xmlDataParser.getValueOf("BaseDate").equals("") ? "" :xmlDataParser.getValueOf("BaseDate");				
				operationCode = xmlDataParser.getValueOf("operationCode");  
				stage = xmlDataParser.getValueOf("stage");  
				sFromPlace = checkSplChar(sFromPlace);
				sToPlace = checkSplChar(sToPlace);
				sPortOfDischarge = checkSplChar(sPortOfDischarge);
				sPortOfLoading = checkSplChar(sPortOfLoading);
				sGoodsDescription = createNormalizedXML(sGoodsDescription);
				sShipmentFrom = checkSplChar(sShipmentFrom);
				sShipmentTo = checkSplChar(sShipmentTo);
				sBaseDate = checkSplChar(sBaseDate);
				sValueDate = xmlDataParser.getValueOf("ValueDate").equals("null") ? "" :xmlDataParser.getValueOf("ValueDate").equals("") ? "" :xmlDataParser.getValueOf("ValueDate");
				sBaseDateDescription = checkSplChar(xmlDataParser.getValueOf("BaseDateDescription"));
				sCustomerName = xmlDataParser.getValueOf("customerName");
				sDiscountOnAccept = xmlDataParser.getValueOf("discountOnAccept"); //US362
				try {
					attribMap.put("RELATIONSHIP_TYPE",sRelationType);
					String columnName = "";
					String tableName = "";
					String whereClause = "";
					String tagName = "";
					List<List<String>> sOutputlist =null;
					columnName = "TRANSACTION_DESCRIPTION ";
					tagName = "TRANSACTION_DESCRIPTION"; 
					tableName = "TFO_DJ_CONTRACT_STATUS_MAST";
					whereClause = "TRANSACTION_STATUS='"+sContractStatus+"'";
					sOutputlist=APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sOutputlist: "+sOutputlist);
					if(sOutputlist != null){
						if(!sOutputlist.get(0).get(0).equalsIgnoreCase("NA") &&
								(!("ILCB_BL".equalsIgnoreCase(requestType)|| "ELCB_BL".equalsIgnoreCase(requestType)))){ //US3165
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"put TRN_STATUS in assgn map..");
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"TRN_STATUS: "
									+sOutputlist.get(0).get(0).toString());
							attribMap.put("TRN_STATUS", sOutputlist.get(0).get(0).toString());
						}
					}
				} catch (Exception e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Set Transaction status excp "+e.getMessage());
				}	
				attribMap.put("CUSTOMER_ID", custid);	
				customerID = custid;

				if("GRNT".equalsIgnoreCase(reqCategory)|| "SBLC".equalsIgnoreCase(reqCategory)){//sblc added by mansi
					attribMap.put("TRANSACTION_CURRENCY", sTXNCurrency);//CURRENCY
					attribMap.put("TRANSACTION_AMOUNT", sTXNAmount);//AMOUNT
					attribMap.put("TRNS_BAL", sTXNBalance);//OS_AMT - LC_OS_AMT
					//update ext table records
					if(expDate.equals("") || expDate.equals(null)){
						trnTenor = "OE";//TRNS_TENOR
					}else{
						trnTenor = "FD";//TRNS_TENOR LC_CONF_AMNT
					}
					if(("GRNT".equalsIgnoreCase(reqCategory) || "SBLC".equalsIgnoreCase(reqCategory)) 
							&& ("AM".equalsIgnoreCase(requestType)|| "SBLC_AM".equalsIgnoreCase(requestType))){//SBLC added by mansi
						attribMap.put("GRNTEE_CNTR_EXP_DATE",formatToYYYYMMDD(guaranteeExpDate));
						attribMap.put("EXP_DATE",formatToYYYYMMDD(expDate));
						attribMap.put("WS_LINKAGE_REF_NUMBER",sLinkageRefNo);
						attribMap.put("WS_PERCENT",sLinkagePercent);
						attribMap.put("BRN_CODE",sBranchCode);
						attribMap.put("PRODUCT_TYPE",productCode);
						attribMap.put("TRN_TENOR",trnTenor);
						attribMap.put("OPERATION_CODE_WS",operationCode);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"setContractData GRNT update : "+attribMap);
						insertAmendmnentDetails(xmlDataParser,reqCategory,requestType);//By KISHAN
					}else if("GRNT".equalsIgnoreCase(reqCategory) && "GAA".equalsIgnoreCase(requestType)){ //ADDED BY KISHAN
						attribMap.put("GRNTEE_CNTR_EXP_DATE",formatToYYYYMMDD(guaranteeExpDate));
						attribMap.put("EXP_DATE",formatToYYYYMMDD(expDate));
						attribMap.put("BRN_CODE",sBranchCode);
						attribMap.put("PRODUCT_TYPE",productCode);
						attribMap.put("TRN_TENOR",trnTenor);
						attribMap.put("OPERATION_CODE_WS",operationCode);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"else setContractData GRNT AM update : "+attribMap);
						insertAmendmnentDetails(xmlDataParser,reqCategory,requestType);//By KISHAN
					} else{
						attribMap.put("GRNTEE_CNTR_EXP_DATE",formatToYYYYMMDD(guaranteeExpDate));
						attribMap.put("EXP_DATE",formatToYYYYMMDD(expDate));
						attribMap.put("BRN_CODE",sBranchCode);
						attribMap.put("PRODUCT_TYPE",productCode);
						attribMap.put("TRN_TENOR",trnTenor);
						attribMap.put("OPERATION_CODE_WS",operationCode);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"else setContractData GRNT AM update : "+attribMap);
					}
					insertPDDetails(xmlDataParser,reqCategory,requestType); 
				}
				else if("ELC".equalsIgnoreCase(reqCategory) || "ILC".equalsIgnoreCase(reqCategory)){
					if(expDate.equals("") || expDate.equals(null)){ 
						trnTenor = "OE";//TRNS_TENOR
					}
					else{
						trnTenor = "FD";//TRNS_TENOR LC_CONF_AMNT LC_TOLERANCE AV NN
					}
					if((sPositiveTolerance.equals("0") || sPositiveTolerance.equals("null")||sPositiveTolerance.equals(""))       //US3110
							&& (sNegativeTolerance.equals("0") || sNegativeTolerance.equals("null")||sNegativeTolerance.equals(""))){
						tolerance = "NN";//TRNS_TENOR LC_CONF_AMNT LC_TOLERANCE AV NN
					}else if(!(sPositiveTolerance.equals("0") || sNegativeTolerance.equals("0"))){ 
						tolerance = "AV";//TRNS_TENOR
					}
					try {
						attribMap.put("TRANSACTION_CURRENCY", sTXNCurrency);//CURRENCY
						attribMap.put("TRANSACTION_AMOUNT", sTXNAmount);//AMOUNT
						attribMap.put("TRNS_BAL", sTXNBalance);//OS_AMT - LC_OS_AMT
						if("ILC".equalsIgnoreCase(reqCategory) && "ILC_AM".equalsIgnoreCase(requestType)){
							attribMap.put("EXP_DATE",formatToYYYYMMDD(expDate));
							attribMap.put("LC_TOLERANCE",tolerance);
							attribMap.put("LC_ABOVE",sPositiveTolerance);
							attribMap.put("LC_UNDER",sNegativeTolerance);
							attribMap.put("PRODUCT_TYPE",productCode);
							attribMap.put("TRN_TENOR",trnTenor);
							attribMap.put("LC_CONF_AMNT",sConfirmedAmount);
							attribMap.put("BRN_CODE",sBranchCode);
							attribMap.put("WS_LINKAGE_REF_NUMBER",sLinkageRefNo);
							attribMap.put("WS_PERCENT",sLinkagePercent);
							attribMap.put("LC_FROM_PLACE",sFromPlace);
							attribMap.put("LC_TO_PLACE",sToPlace);
							attribMap.put("LC_LTST_SHIPMNT_DATE",formatToYYYYMMDD(sLatestShipmentDate));
							attribMap.put("LC_PORT_OF_DISCHRG",sPortOfDischarge);
							attribMap.put("LC_PORT_OF_LOADING",sPortOfLoading);
							attribMap.put("LC_GOODS_DESC",sGoodsDescription);
							attribMap.put("OPERATION_CODE_WS",operationCode);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"setContractData update ext table records query : "+attribMap);
							insertAmendmnentDetails(xmlDataParser,reqCategory,requestType);
						}
						else{
							attribMap.put("EXP_DATE",formatToYYYYMMDD(expDate));
							attribMap.put("LC_TOLERANCE",tolerance);
							attribMap.put("LC_ABOVE",sPositiveTolerance);
							attribMap.put("LC_UNDER",sNegativeTolerance);
							attribMap.put("PRODUCT_TYPE",productCode);
							attribMap.put("TRN_TENOR",trnTenor);
							attribMap.put("LC_CONF_AMNT",sConfirmedAmount);
							attribMap.put("BRN_CODE",sBranchCode);
							attribMap.put("LC_FROM_PLACE",sFromPlace);
							attribMap.put("LC_TO_PLACE",sToPlace);
							attribMap.put("LC_LTST_SHIPMNT_DATE",formatToYYYYMMDD(sLatestShipmentDate));
							attribMap.put("LC_PORT_OF_DISCHRG",sPortOfDischarge);
							attribMap.put("LC_PORT_OF_LOADING",sPortOfLoading);
							attribMap.put("LC_GOODS_DESC",sGoodsDescription);
							attribMap.put("OPERATION_CODE_WS",operationCode);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"setContractData update ext table records query : "+attribMap);
						}
						String sWhere = " WI_NAME = '"+WI_NAME+"'";
						ProdCreateXML.APUpdateFromMap(sessionId,"EXT_TFO",attribMap,sWhere);
					} catch (Exception e) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"ILC and ELC update on Fetch at Reception : "+e.getMessage());
					}
					insertPDDetails(xmlDataParser,reqCategory,requestType);
				}
				else if("IC".equalsIgnoreCase(reqCategory) || "EC".equalsIgnoreCase(reqCategory) 
						|| ("ILCB".equalsIgnoreCase(reqCategory) && !"ILCB_BL".equalsIgnoreCase(requestType))
						|| ("ELCB".equalsIgnoreCase(reqCategory) && !"ELCB_BL".equalsIgnoreCase(requestType)) 
						|| "DBA".equalsIgnoreCase(reqCategory)){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"requestType--:: starting[][]"+requestType);
					attribMap.put("TRANSACTION_CURRENCY", sTXNCurrency);//CURRENCY
					attribMap.put("TRANSACTION_AMOUNT", sTXNAmount);//AMOUNT
					attribMap.put("TRNS_BAL", sTXNBalance);//OS_AMT - LC_OS_AMT
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"requestType--:: mid1[][]"+requestType);
					attribMap.put("INF_BASE_DOC_DATE",formatToYYYYMMDD(sBaseDate));
					attribMap.put("INF_MATURITY_DATE",formatToYYYYMMDD(sMaturityDate));
					attribMap.put("BRN_CODE",sBranchCode);
					attribMap.put("BILL_OUR_LC_NO",sOurLCNumber);
					attribMap.put("BILL_PORT_OF_DISCHARGE",sShipmentTo);
					attribMap.put("BILL_PORT_OF_LOADING",sShipmentFrom);
					attribMap.put("PRODUCT_TYPE",productCode);
					attribMap.put("INF_TENOR_DAYS",sTenorDays);
					attribMap.put("INF_ACTUAL_TENOR_BASE",sBaseDateDescription);
					attribMap.put("OPERATION_CODE_WS",operationCode);
					attribMap.put("BILL_STAGE",stage);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"attribMap setContractData: "+attribMap);
					//					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"else setContractData "+reqCategory+" update: "+setContractDataStatus);
					insertBC_PDDetails(xmlDataParser); 
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"requestType--:: value[][]"+requestType);

					if("ILCB_AC".equalsIgnoreCase(requestType)           || "IC_AC".equalsIgnoreCase(requestType)
							||"ELCB_AC".equalsIgnoreCase(requestType)    || "ELCB_AM".equalsIgnoreCase(requestType)
							|| "ELCB_DISC".equalsIgnoreCase(requestType) || "EC_AC".equalsIgnoreCase(requestType)
							|| "EC_AM".equalsIgnoreCase(requestType)     || "EC_DISC".equalsIgnoreCase(requestType)){
						if("ELCB_AC".equalsIgnoreCase(requestType)){
							if("Y".equalsIgnoreCase(sDiscountOnAccept) || "Yes".equalsIgnoreCase(sDiscountOnAccept))
								sDiscountOnAccept = "1";
							else if("N".equalsIgnoreCase(sDiscountOnAccept) || "No".equalsIgnoreCase(sDiscountOnAccept))
								sDiscountOnAccept = "2";
							attribMap.put("LC_GOODS_DESC",sGoodsDescription);
							attribMap.put("DISCOUNT_ON_ACCEP",sDiscountOnAccept);
							//String sQuery2 ="UPDATE EXT_TFO SET LC_GOODS_DESC="+sGoodsDescription+",DISCOUNT_ON_ACCEP='"+sDiscountOnAccept+"' WHERE WI_NAME='"+sWorkitemID+"'";
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside US362");
						}else{
							attribMap.put("LC_GOODS_DESC",sGoodsDescription);
							//attribMap.put("DISCOUNT_ON_ACCEP",sDiscountOnAccept);
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside else part of US362");
							//String sQuery2 ="UPDATE EXT_TFO SET LC_GOODS_DESC='"+sGoodsDescription+"' WHERE WI_NAME='"+sWorkitemID+"'";
							//LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"input query: "+sQuery2);
							//setContractDataStatus2 = formObject.saveDataInDB(sQuery2);
							//l//og.info("else setContractData1 "+reqCategory+" update: "+setContractDataStatus2);
						}
					}
				}

				else if("ILCB_BL".equalsIgnoreCase(requestType)|| "ELCB_BL".equalsIgnoreCase(requestType)){ 
					attribMap.put("TRANSACTION_CURRENCY", sTXNCurrency);
					if("APP".equalsIgnoreCase(spartyType)){spartyType="DRAWEE";} 
					if("BEN".equalsIgnoreCase(spartyType)){spartyType="DRAWER";}
					if("ISB".equalsIgnoreCase(spartyType)){spartyType="ISSUING BANK";}
					if("REB".equalsIgnoreCase(spartyType)){spartyType="REIMBURSING BANK";}
					attribMap.put("LC_LIMIT_LINE",sLinkageRefNo);
					attribMap.put("LIMIT_PARTY_TYPE",spartyType);
					attribMap.put("WS_CONTRACT_AMOUNT",sTXNAmount); 
					attribMap.put("WS_POSITIVE_TOLERANCE",sPositiveTolerance);
					attribMap.put("WS_EXP_DATE",expDate);
					attribMap.put("WS_LC_LTST_SHIPMNT_DATE",sLatestShipmentDate);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"else setContractData "+reqCategory+" update : "+setContractDataStatus);
					insertPDDetails(xmlDataParser,reqCategory,requestType);
					insertLCContractDetails(xmlDataParser);

					/* else if("IFS".equalsIgnoreCase(reqCategory) || "IFP".equalsIgnoreCase(reqCategory) 
								 || "TL".equalsIgnoreCase(reqCategory)){/*
					attribMap.put(TRNS_BAL, sPrincipalOutStanding);//OS_AMT - LC_OS_AMT
					String sQuery="UPDATE EXT_TFO SET INF_BASE_DOC_DATE='"+formatToYYYYMMDD(sValueDate)+"',INF_MATURITY_DATE='"+formatToYYYYMMDD(sMaturityDate)+"',BRN_CODE='"+sBranchCode+"'"
							+" ,PRODUCT_TYPE='"+productCode+"',INF_TENOR_DAYS='"+sTenorDays+"' WHERE WI_NAME='"+sWorkitemID+"'";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"setContractData update ext table records query : "+sQuery);
					setContractDataStatus = APCallCreateXML.saveDataInDB(sQuery);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"else setContractData "+reqCategory+" update : "+setContractDataStatus);
					 */}
				else if("IFS".equalsIgnoreCase(reqCategory) || "IFP".equalsIgnoreCase(reqCategory) 
						|| "TL".equalsIgnoreCase(reqCategory)){
					attribMap.put("TRNS_BAL",sPrincipalOutStanding);
					if("TL_LD".equalsIgnoreCase(requestType)){ 			
						attribMap.put("TRANSACTION_CURRENCY",sTXNCurrency);
						attribMap.put("TRANSACTION_AMOUNT",sTXNAmount); 
						attribMap.put("CUSTOMER_ID",custid);
						attribMap.put("CUSTOMER_NAME",sCustomerName);
						attribMap.put("INF_TENOR_DAYS",sTenorDays);
						attribMap.put("INF_MATURITY_DATE",formatToYYYYMMDD(sMaturityDate));
						attribMap.put("BILL_STAGE",stage);
					}
					attribMap.put("INF_BASE_DOC_DATE",formatToYYYYMMDD(sValueDate));
					attribMap.put("INF_MATURITY_DATE",formatToYYYYMMDD(sMaturityDate));
					attribMap.put("BRN_CODE",sBranchCode);
					attribMap.put("PRODUCT_TYPE",productCode);
					attribMap.put("INF_TENOR_DAYS",sTenorDays);					
				}
				else if("SG".equalsIgnoreCase(reqCategory)){/* 
					if("SG_NILC".equalsIgnoreCase(requestType)){
						attribMap.put(TRANSACTION_CURRENCY, sTXNCurrency);  //Shipping_Gtee_14
						insertPDDetails(xmlDataParser,requestType);               //Shipping_Gtee_61
						attribMap.put(TRNS_BAL, "");	//Shipping_Gtee_13
						attribMap.put(TRN_STATUS, "");	//Shipping_Gtee_13

					}
					else if("SG_NIC".equalsIgnoreCase(requestType)){
						attribMap.put(TRNS_BAL, "");   //Shipping_Gtee_13
						attribMap.put(TRN_STATUS, "");	 //Shipping_Gtee_13
					}
					else if("SG_SD".equalsIgnoreCase(requestType)){
						attribMap.put(TRANSACTION_CURRENCY, sTXNCurrency);//Shipping_Gtee_14
						attribMap.put(TRANSACTION_AMOUNT, sTXNAmount);//Shipping_Gtee_15					 
					}
				 */}
				if(!("ILCB_BL".equalsIgnoreCase(requestType)|| "ELCB_BL".equalsIgnoreCase(requestType)
						|| "DBA_BL".equalsIgnoreCase(requestType)|| "EC_BL".equalsIgnoreCase(requestType)
						|| "EC_LDDI".equalsIgnoreCase(requestType)|| "IC_BL".equalsIgnoreCase(requestType)
						|| "IC_AC".equalsIgnoreCase(requestType)|| "ILCB_AC".equalsIgnoreCase(requestType))){
					insertBCContractDetails(xmlDataParser); 
				}				
				iCDError = 2;
			} else {
				XMLParser xp = new XMLParser(resContractData);
				sMessege = xp.getValueOf("td");
			}

			String sWhere = " WI_NAME = '"+WI_NAME+"'";
			ProdCreateXML.APUpdateFromMap(sessionId,"EXT_TFO",attribMap,sWhere);
		} catch (Exception e) {
			sMessege = "Unable to Fetch Contract Details"; 
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :912 N");
			callStatus = "N";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
		} finally {
			if (iCDError == 1) {
				sMessege = "Unable to fetch Contract details. Kindly Enter correct Reference Number.";
			}
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sMessege from Fetch Contract Details: "+sMessege+"--and- iCDError: "+iCDError);
		//setAmendmentType();
		return iCDError+"##"+sMessege;
	}

	public void setAmendmentType(){
		try {

			Map<String,String> amendmentMap  = ChannelCallCache.getInstance().amendMap();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"MAP ROM CHANNEL CACHE AMEND  "+amendmentMap.toString());
			String outputIform = null;

			try {
				outputIform = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY,REQUEST_TYPE,TRN_TENOR,NEW_TRN_AMT,TRANSACTION_AMOUNT, "
						+ "TO_CHAR(TO_TIMESTAMP(EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') as EXP_DATE, TO_CHAR(TO_TIMESTAMP(NEW_EXP_DATE,'YYYY-MM-DD HH24:MI:SS.FF3'),'DD/MM/YYYY') as NEW_EXP_DATE FROM EXT_TFO WHERE WI_NAME ='"+WI_NAME+"'");
			} catch (NGException e) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
			}	
			XMLParser xp1 = new XMLParser(outputIform);	
			String reqcat = xp1.getValueOf("REQUEST_CATEGORY");
			String reqtype = xp1.getValueOf("REQUEST_TYPE");
			String trnTenor = xp1.getValueOf("TRN_TENOR");
			String newtmt = xp1.getValueOf("NEW_TRN_AMT");
			String 	tamt = 	xp1.getValueOf("TRANSACTION_AMOUNT");
			String expdate = xp1.getValueOf("EXP_DATE");
			String newexpdate = xp1.getValueOf("NEW_EXP_DATE");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"VALUES FROM DBBBBB: "+reqcat+ "  " +newtmt+ "  " +newexpdate);
			String dateFlag="NA";
			String tamtFlag = "NA";
			String keyn=reqcat + "#"+reqtype;
			Date expdatec = null;
			Date newexpdatec = null;
			try{
				BigDecimal oldAmount=new BigDecimal(tamt);
				BigDecimal newAmount=new BigDecimal(newtmt);

				if(-1==oldAmount.compareTo(newAmount)){
					tamtFlag="GT";
				}else if(1==oldAmount.compareTo(newAmount)){
					tamtFlag="LE";
				}else if(0==oldAmount.compareTo(newAmount)){
					tamtFlag="EQ";
				}
			}catch(Exception e)	{
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ERRORRR "+e);
			}
			try {
				expdatec = new SimpleDateFormat("DD/MM/YYYY").parse(expdate);
				newexpdatec = new SimpleDateFormat("DD/MM/YYYY").parse(newexpdate);
				if(newexpdatec.after(expdatec))
				{
					dateFlag="GT";
				}else if(newexpdatec.before(expdatec))
				{
					dateFlag="LE";
				}else if(newexpdatec.equals(expdatec))
				{
					dateFlag="EQ";
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"ERRORRR "+e);
			}


			keyn = keyn+"#"+trnTenor+"#"+tamtFlag+"#"+dateFlag;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"key value:="+keyn);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"amendmentMap ="+amendmentMap);
			String finalvalueset = amendmentMap.get(keyn);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"FINAL VALUE AMEND ="+finalvalueset);
			HashMap<String, String> attribMap = new HashMap<String, String>();
			attribMap.put("AMEND_TYPE",finalvalueset);
			String sWhere = " WI_NAME = '"+WI_NAME+"'";
			try {
				ProdCreateXML.APUpdateFromMap(sessionId,"EXT_TFO",attribMap,sWhere);
			} catch (NGException e) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
		}

	}

	public String checkSplChar(String str) {
		try {
			if (str.indexOf("'") > -1) {
				str = str.replace("'", "''");
			}
			if (str.indexOf("&") > -1) {
				str = str.replace("&", "'||chr(38)||'");
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
		}
		return str;
	}
	public static String createNormalizedXML(String outputXml) {
		try {
			if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
				outputXml = outputXml.replace("'", "''");
				outputXml = outputXml.replace("&", "'||chr(38)||'");
				if (outputXml.length() > 3200) {				
					outputXml = breakCLOBString(outputXml);
					return outputXml;
				} else {
					return "'"+outputXml+"'";
				}
			} 			
		} catch (Exception ex) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+ex);
		}
		return "''";
	}
	public static String createNormalizedXMLForProc(String outputXml) {
		try {
			if ((outputXml != null) && (!("".equalsIgnoreCase(outputXml)))) {
				outputXml = outputXml.replace("'", "''");
				outputXml = outputXml.replace("&", "'||chr(38)||'");
				if (outputXml.length() > 3200) {				
					outputXml = breakCLOBString(outputXml);
					return outputXml;
				} else {
					return "TO_NCLOB('"+outputXml+"')";
				}
			} else {
				return "TO_NCLOB('')";
			}			
		} catch (Exception ex) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+ex);
		}
		return "TO_NCLOB('')";
	}
	public String formatToYYYYMMDD(String dtExp){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"formatToYYYYMMDD "+dtExp);
		try {
			if(!dtExp.equals("")){
				SimpleDateFormat sdfEXPDate = new SimpleDateFormat("dd/MM/yyyy");
				Date dateExpDate = sdfEXPDate.parse(dtExp);
				sdfEXPDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dtExp = sdfEXPDate.format(dateExpDate);
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"formatToYYYYMMDD excp "+e.getMessage());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
		}
		return dtExp;
	}
	private static String breakCLOBString(String xml) {
		int itr = xml.length() / 3200;
		int mod = xml.length() % 3200;
		if (mod > 0) {
			++itr;
		}
		StringBuilder response = new StringBuilder();
		try {
			for (int i = 0; i < itr; ++i) {
				if (i == 0) {
					response.append("TO_NCLOB('" + xml.substring(0, 3200) + "')");
				} else if (i < itr - 1) {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,3200 * (i + 1)) + "')");
				} else {
					response.append(" || TO_NCLOB('" + xml.substring(3200 * i,xml.length()) + "') ");
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: "+e);
		}
		return response.toString();
	}
	public void insertPDDetails(XMLParser xmlDataParser,String reqCat,String reqType){
		int count=0;
		String columnName=" party_type, party_desc, party_id, customer_name, address1, address2,"
				+ " address3,address4, country,winame,insertionorderid";   //US3118
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("partyDetails");
		try{
			if(("GRNT".equalsIgnoreCase(reqCat) &&"AM".equalsIgnoreCase(reqType))
					||("SBLC".equalsIgnoreCase(reqCat) &&"SBLC_AM".equalsIgnoreCase(reqType))
					){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,":in block");
				APCallCreateXML.APDelete("TFO_DJ_PARTY_DETAILS", "winame='"+WI_NAME+"'", sessionId);
			}
		}catch(Exception e){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
		for(int i=0;i<count;i++)
		{
			parser2.setInputXML(xmlDataParser.getNextValueOf("partyDetails"));
			/*	String partyType=parser2.getValueOf("partyType").trim();
			String partyDesc=parser2.getValueOf("partyDescription").trim();
			String partyID=parser2.getValueOf("customerNumber").trim();
			String customerName=checkSplChar(parser2.getValueOf("customerName").trim());
			String address1=checkSplChar(parser2.getValueOf("addressLine1").trim());
			String address2=checkSplChar(parser2.getValueOf("addressLine2").trim());
			String address3=checkSplChar(parser2.getValueOf("addressLine3").trim());
			String address4=checkSplChar(parser2.getValueOf("addressLine4").trim()); 
			String country=parser2.getValueOf("countryCode").trim();
			String wi_name= WI_NAME;*/
			String partyType="'"+parser2.getValueOf("partyType").trim()+"'";
			String partyDesc=",'"+parser2.getValueOf("partyDescription").trim()+"'";
			String partyID=",'"+parser2.getValueOf("customerNumber").trim()+"','";
			String customerName=checkSplChar(parser2.getValueOf("customerName").trim())+"','";
			String address1=checkSplChar(parser2.getValueOf("addressLine1").trim())+"','";
			String address2=checkSplChar(parser2.getValueOf("addressLine2").trim())+"','";
			String address3=checkSplChar(parser2.getValueOf("addressLine3").trim())+"','";
			String address4=checkSplChar(parser2.getValueOf("addressLine4").trim())+"','"; 
			String country=parser2.getValueOf("countryCode").trim()+"','";
			String wi_name= WI_NAME+"'";
			String insertionId=getInsertIonIdForTable("TFO_DJ_PARTY_DETAILS");
			/*if(i == 0){
				try {
					APCallCreateXML.APDelete("TFO_DJ_PARTY_DETAILS", "winame='"+wi_name+"'", sessionId);
				} catch (NGException e) {
					e.printStackTrace();
				}
			}*/
			String partyTypeforELCB="'APP','BEN','ISB','REB','AS1','RS1'";
			List<String> partyAllowedELCB=Arrays.asList(partyTypeforELCB.split(","));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"partyType= "+partyType);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"contains "+partyAllowedELCB.contains(partyType));

			if("ELCB_BL".equalsIgnoreCase(requestType) && partyAllowedELCB.contains(partyType))
			{
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"if loop");

				if("'APP'".equalsIgnoreCase(partyType)){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"if loop1");
					partyType="'DRAWEE'";partyDesc=",'DRAWEE'";}
				if("'BEN'".equalsIgnoreCase(partyType)){partyType="'DRAWER'";partyDesc=",'DRAWER'";}
				if("'ISB'".equalsIgnoreCase(partyType)){partyType="'ISSUING BANK'";partyDesc=",'ISSUING BANK'";}
				if("'REB'".equalsIgnoreCase(partyType)){partyType="'REIMBURSING BANK'";partyDesc=",'REIMBURSING BANK'";}
			}
			//String strValues = "'"+partyType+"','"+partyDesc+"','"+partyID+"','"+customerName+"','"+address1+"','"+address2+"','"+address3+"','"+address4+"','"+country+"','"+wi_name+"','"+insertionId+"'";
			String strValues = partyType+partyDesc+partyID+customerName+address1+address2+address3+address4+country+wi_name+",'"+insertionId+"'";

			try {

				APCallCreateXML.APInsert("TFO_DJ_PARTY_DETAILS", columnName, strValues, sessionId);
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			}
		} 
	}

	public void insertBC_PDDetails(XMLParser xmlDataParser){
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside insertBC_PDDetails >>");
			int count=0;
			String querystart="insert all ";
			//			String query1="into tfo_dj_party_details (party_type, party_desc, party_id, customer_name, address1, address2,"
			//					+ " address3,address4, winame,insertionorderid) values(";
			String finalInsert="";
			//			String queryEnd="select * from dual";
			String columnNames = "party_type, party_desc, party_id, customer_name, address1, address2,"
					+ " address3,address4, winame,insertionorderid";
			String columnValues = "";
			boolean flag=false;
			XMLParser parser2=new XMLParser();
			count=xmlDataParser.getNoOfFields("partyDetails");
			String partyDesc = "";
			APCallCreateXML.APDelete("TFO_DJ_PARTY_DETAILS", "winame='"+WI_NAME+"'", sessionId);
			for(int i=0;i<count;i++)
			{
				flag=true;
				partyDesc = "''";
				parser2.setInputXML(xmlDataParser.getNextValueOf("partyDetails"));
				String partyType="'"+parser2.getValueOf("partyType").trim()+"','";
				try {
					String strQ="";
					List<List<String>> sOutputlist =null;
					strQ="SELECT PARTY_TYPE_DESC FROM TFO_DJ_PARTY_TYPE_MASTER WHERE PARTY_TYPE_CODE='"
							+parser2.getValueOf("partyType").trim()+"' and ROWNUM=1";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"party type query " + strQ);
					//Outputlist=formObject.getDataFromDB(strQ);
					String outputXML = APCallCreateXML.APSelect(strQ);
					XMLParser xpParty = new XMLParser(outputXML);
					int mainCode = Integer.parseInt(xpParty.getValueOf("MainCode"));
					//					if(!sOutputlist.get(0).get(0).equalsIgnoreCase("")){
					//						partyDesc = sOutputlist.get(0).get(0).toString()+"','";
					//					}
					if(mainCode == 0 && Integer.parseInt(xpParty.getValueOf("TotalRetrieved")) > 0){
						partyDesc = xpParty.getValueOf("PARTY_TYPE_DESC").trim()+"','";
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"party partyDesc: " + partyDesc);
					}
				} catch (Exception e) {
					partyDesc = "','";
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Party desc not found for party typ excp "
							+parser2.getValueOf("partyType").trim()+"\n"+e.getMessage());
				}
				String partyID=parser2.getValueOf("customerNumber").trim()+"','";
				String customerName=parser2.getValueOf("customerName").trim()+"','";
				String address1=parser2.getValueOf("addressLine1").trim()+"','";
				String address2=parser2.getValueOf("addressLine2").trim()+"','";
				String address3=parser2.getValueOf("addressLine3").trim()+"','";
				String address4=parser2.getValueOf("addressLine4").trim()+"','";
				String wi_name= WI_NAME+"','";
				String insertionId=getInsertIonIdForTable("TFO_DJ_PARTY_DETAILS")+"'";

				//				finalInsert=finalInsert+query1+partyType+partyDesc+partyID+customerName+address1
				//						+address2+address3+address4+wi_name+insertionId; 
				columnValues = partyType+partyDesc+partyID+customerName+address1
						+address2+address3+address4+wi_name+insertionId;
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inserting into TFO_DJ_PARTY_DETAILS: "+
						columnValues);
				APCallCreateXML.APInsert("TFO_DJ_PARTY_DETAILS", columnNames, columnValues, sessionId);
			}
			/*if(flag)
			{
				//String query="delete from TFO_DJ_PARTY_DETAILS where winame='"+WI_NAME+"'";
				APCallCreateXML.APDelete("TFO_DJ_PARTY_DETAILS", "winame='"+WI_NAME+"'", sessionId);
				//formObject.saveDataInDB(query);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,querystart+finalInsert+queryEnd);
				APCallCreateXML.APInsert("TFO_DJ_PARTY_DETAILS", columnName, strValues, sessionId);
				//formObject.saveDataInDB(querystart+finalInsert+queryEnd);
			}*/
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in insertBC_PDDetails: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
		}
	}

	public void insertBCContractDetails(XMLParser xmlDataParser) {
		int count=0;
		String columnName="PARTY_TYPE, SERIAL_NUMBER, OPERATION, CUSTOMER_NO, TYPE, LINKAGE_REFERENCE_NO, PERCENTAGE_CONTRIBUTION,AMOUNT_TAG, WINAME,INSERTIONORDERID";
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("partyLimit");
		for(int i=0;i<count;i++) {
			parser2.setInputXML(xmlDataParser.getNextValueOf("partyLimit"));
			String partyType="'"+parser2.getValueOf("partyType").trim()+"'";
			String serialNumber=",'"+parser2.getValueOf("serialNumber").trim()+"'";
			String operation=",'"+parser2.getValueOf("limitOperation").trim()+"'";
			String customerNumber=",'"+parser2.getValueOf("customerNumber").trim()+"','";
			String limitType=parser2.getValueOf("limitLinkageType").trim()+"','";
			String linkageReferenceNumber=parser2.getValueOf("linkageReferenceNumber").trim().toUpperCase()+"','"; //US3504
			String limitPercentContribution=parser2.getValueOf("limitPercentContribution").trim()+"','";
			String amountTag=parser2.getValueOf("amountTag").trim()+"','";
			String winame= WI_NAME;
			String insertionId=getInsertIonIdForTable("TFO_DJ_CONTRACT_LIMITS_DETAILS")+"'";
			/*if(i == 0){
				try {
					APCallCreateXML.APDelete("TFO_DJ_CONTRACT_LIMITS_DETAILS", "winame='"+winame+"'", sessionId);
				} catch (NGException e) {
					e.printStackTrace();
				}
			}*/
			String strValues = "'"+partyType+"','"+serialNumber+"','"+operation+"','"+customerNumber+"','"+limitType+"','"+linkageReferenceNumber+"','"+limitPercentContribution+"','"+amountTag+"','"+winame+"','"+insertionId+"'";
			try {
				APCallCreateXML.APInsert("TFO_DJ_CONTRACT_LIMITS_DETAILS", columnName, strValues, sessionId);
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in insertBCContractDetails: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			}
		}
	}

	public void insertLCContractDetails(XMLParser xmlDataParser){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE insertLCContractDetails");
		int count=0;
		String columnName="PARTY_TYPE, SERIAL_NUMBER, OPERATION, CUSTOMER_NO, TYPE, LINKAGE_REFERENCE_NO, PERCENTAGE_CONTRIBUTION,AMOUNT_TAG, WINAME,INSERTIONORDERID";
		XMLParser parser2=new XMLParser();
		count=xmlDataParser.getNoOfFields("contractLimit");
		for(int i=0;i<count;i++) {
			parser2.setInputXML(xmlDataParser.getNextValueOf("contractLimit"));
			String partyType="'"+parser2.getValueOf("partyType").trim()+"'";
			String serialNumber=",'"+parser2.getValueOf("serialNumber").trim()+"'";
			//			String operation=",'"+parser2.getValueOf("limitOperation").trim()+"'";
			String operation=",'ACC'";
			String customerNumber=",'"+parser2.getValueOf("customerNumber").trim()+"','";
			String limitType=parser2.getValueOf("limitLinkageType").trim()+"','";
			String linkageReferenceNumber=parser2.getValueOf("linkageReferenceNumber").trim().toUpperCase()+"','"; //US3504
			String limitPercentContribution=parser2.getValueOf("limitPercentContribution").trim()+"','";
			String amountTag=parser2.getValueOf("amountTag").trim()+"','";
			String winame= WI_NAME;
			String insertionId=getInsertIonIdForTable("TFO_DJ_CONTRACT_LIMITS_DETAILS");
			if("'APP'".equalsIgnoreCase(partyType) || "'ACC'".equalsIgnoreCase(partyType)){
				partyType="'DRAWEE'";}
			if("'ISB'".equalsIgnoreCase(partyType)){
				partyType="'ISSUING BANK'";}
			if("LIAB_OS_AMT','".equalsIgnoreCase(amountTag) || "CNF_LIAB_OS_AMT','".equalsIgnoreCase(amountTag)){
				amountTag="BILL_OS_AMT','";
			}
			if("USN_INT_AMT','".equalsIgnoreCase(amountTag)){
				amountTag="USN_INT_AMT','";
			}
			if(i == 0){
				try {
					APCallCreateXML.APDelete("TFO_DJ_CONTRACT_LIMITS_DETAILS", "winame='"+winame+"'", sessionId);
				} catch (NGException e) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in insertLCContractDetails: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
				}
			}
			String strValues = partyType+serialNumber+operation+customerNumber+limitType+linkageReferenceNumber
					+limitPercentContribution+amountTag+winame+"','"+insertionId+"'";
			try {
				APCallCreateXML.APInsert("TFO_DJ_CONTRACT_LIMITS_DETAILS", columnName, strValues, sessionId);
			} catch (NGException e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in insertLCContractDetails: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,e);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public String getInsertIonIdForTable(String mainTableName){
		String sequenceName="WFSEQ_ARRAY_";
		String seqValue=null;
		String columnName = "";
		String tableName = "";
		String whereClause = "";
		String tagName = "";
		columnName = "(processdefid||'_'||extobjid) as Value";
		tableName = "wfudtvarmappingtable";
		whereClause = "mappedobjectname='"+mainTableName+"'";
		tagName = "Value";
		List<List<String>> sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
		if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			sequenceName = sequenceName+sOutputlist.get(0).get(0);
		}
		columnName = ""+sequenceName+".nextval as Value";
		tableName = "dual";
		whereClause = "";
		tagName = "Value";
		sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
		if (sOutputlist != null && !sOutputlist.isEmpty() && !sOutputlist.get(0).isEmpty()) {
			seqValue = sOutputlist.get(0).get(0);
		}
		return seqValue;
	}

	private String setTransactionDataLc(String refNo,List<List<String>> recordList, String reqCat, String requestType) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside setTransactionDataLc >>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"request category: " + reqCat);
		HashMap<String, String> attribMap = new HashMap<String, String>();
		String retMsg = "";
		try {
			try {
				attribMap.put("RELATIONSHIP_TYPE", (recordList.get(0)).get(4));// TRNS_BRN_CODE
				String sWhere = " WI_NAME = '"+WI_NAME+"'";
				ProdCreateXML.APUpdateFromMap(sessionId,"EXT_TFO",attribMap,sWhere);
				if (!"ILCB_BL".equalsIgnoreCase(requestType) && !"ELCB_BL".equalsIgnoreCase(requestType)) {
					if (("ELC".equalsIgnoreCase(reqCat) || "ILC"
							.equalsIgnoreCase(reqCat))
							&& ("ILC_NI".equalsIgnoreCase(requestType) || "ELC_LCA"
									.equalsIgnoreCase(requestType))) {
						attribMap.put("TRANSACTION_CURRENCY", (recordList.get(0)).get(2));// Currency
						attribMap.put("TRANSACTION_AMOUNT", (recordList.get(0)).get(3));// Amount
						attribMap.put("TRNS_BAL", (recordList.get(0)).get(5));// TRNS_BAL -
						// LC_OS_AMT 
						attribMap.put("TRN_STATUS", (recordList.get(0)).get(6)); // IS_ACTIVE
					} else if (!("ELC".equalsIgnoreCase(reqCat) && "ILC"
							.equalsIgnoreCase(reqCat))
							&& ("DBA_BL".equalsIgnoreCase(requestType)
									|| "EC_BL".equalsIgnoreCase(requestType)
									|| "EC_LDDI".equalsIgnoreCase(requestType) || "IC_BL"
									.equalsIgnoreCase(requestType))) {
						attribMap.put("TRANSACTION_CURRENCY", (recordList.get(0)).get(2));// Currency
						attribMap.put("TRANSACTION_AMOUNT", (recordList.get(0)).get(3));// Amount
						attribMap.put("TRNS_BAL", (recordList.get(0)).get(5));// TRNS_BAL -LC_OS_AMT
						attribMap.put("TRN_STATUS", (recordList.get(0)).get(6)); // IS_ACTIVE
						attribMap.put("PRODUCT_TYPE", (recordList.get(0)).get(7));// PRODUCT
					}
				}
				if ("ELC".equalsIgnoreCase(reqCat) || "ILC".equalsIgnoreCase(reqCat)) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"request category is ELC or ILC");
					attribMap.put("CUSTOMER_ID", ""); 
					//					attribMap.put("IS_ACC_VALID", (recordList.get(0)).get(8));
					//					attribMap.put("ACCOUNT_NUMBER", (recordList.get(0)).get(9));// ACCOUNT_NUMBER
					attribMap.put("EXP_DATE", (recordList.get(0)).get(10)); // EXP_DATE
					attribMap.put("TRN_THIRD_PARTY", (recordList.get(0)).get(11));// TRNS_THIRD_PARTY
					attribMap.put("LC_CORRSPNDNT_BNK", (recordList.get(0)).get(12)); // LC_CORRSPNDNT_BNK
					attribMap.put("LC_CONF_AMNT", (recordList.get(0)).get(13));// LC_CONF_AMNT
					attribMap.put("TRANSACTION_UNB_REFERENCE", (recordList.get(0)).get(14));// OTHER_TRAN_REF_NUM
					if(recordList.get(0).get(15) != null && !recordList.get(0).get(15).equalsIgnoreCase("")){
						attribMap.put("BRANCH_CITY",(recordList.get(0)).get(15));
						attribMap.put("ASSIGNED_CENTER",(recordList.get(0)).get(15));
					}
					if("ILC_AM".equalsIgnoreCase(requestType)) {
						attribMap.put("IS_ACC_VALID", (recordList.get(0)).get(8));
						attribMap.put("ACCOUNT_NUMBER", (recordList.get(0)).get(9));
					}

				} else if ("EC".equalsIgnoreCase(reqCat)
						|| "IC".equalsIgnoreCase(reqCat)
						|| "DBA".equalsIgnoreCase(reqCat)
						|| "ELCB".equalsIgnoreCase(reqCat)
						|| "ILCB".equalsIgnoreCase(reqCat)) {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"request category is EC, IC DBA, ELCB"
							+ " or ILCB");
					if (!"ILCB_BL".equalsIgnoreCase(requestType) && !"ELCB_BL".equalsIgnoreCase(requestType)) {
						attribMap.put("TRNS_BAL", (recordList.get(0)).get(5));
						attribMap.put("TRN_STATUS", (recordList.get(0)).get(6));
						attribMap.put("INF_TENOR_DAYS", (recordList.get(0)).get(8));
						attribMap.put("INF_TENOR_BASE", (recordList.get(0)).get(9));
						attribMap.put("INF_ACTUAL_TENOR_BASE", (recordList.get(0)).get(10));
						attribMap.put("INF_BASE_DOC_DATE", (recordList.get(0)).get(11));
						attribMap.put("INF_MATURITY_DATE", (recordList.get(0)).get(12));
						//						if(!("Y".equalsIgnoreCase(protradeFlag) && ("ELCB_AM".equalsIgnoreCase(requestType) 
						//								|| "EC_AM".equalsIgnoreCase(requestType)
						//								|| "ELCB_DISC".equalsIgnoreCase(requestType) 
						//								|| "EC_DISC".equalsIgnoreCase(requestType)))){
						//							attribMap.put("BILL_CUST_HLDING_ACC_US", (recordList.get(0)).get(13));
						//							attribMap.put("INF_SETTLEMENT_ACC_NUM", (recordList.get(0)).get(14));
						//							attribMap.put("INF_CHARGE_ACC_NUM", (recordList.get(0)).get(15));
						//						}
						if(!("Y".equalsIgnoreCase(protradeFlag) && ("ELCB_AM".equalsIgnoreCase(requestType) 
								|| "EC_AM".equalsIgnoreCase(requestType)))){
							attribMap.put("BILL_MODE_OF_PMNT", (recordList.get(0)).get(16));
						}
						attribMap.put("BILL_OUR_LC_NO", (recordList.get(0)).get(17));
						attribMap.put("BILL_CORRSPNDNT_BNK", (recordList.get(0)).get(18));
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"(recordList.get(0)).get(19): " 
							+ (recordList.get(0)).get(19));
					if(!otherRef.isEmpty()) {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"put other ref no in map: " + otherRef);
						attribMap.put(otherRef, (recordList.get(0)).get(19));
					}
					if(recordList.get(0).get(20) != null && !recordList.get(0).get(20).equalsIgnoreCase("")){
						attribMap.put("BRANCH_CITY", (recordList.get(0)).get(20));
						attribMap.put("ASSIGNED_CENTER", (recordList.get(0)).get(20));
					}
					//					if("ILCB_BS".equalsIgnoreCase(requestType) || "ILCB_AC".equalsIgnoreCase(requestType)
					//							||"IC_AC".equalsIgnoreCase(requestType) || "IC_BS".equalsIgnoreCase(requestType)){
					//						attribMap.put("BILL_TYPE", (recordList.get(0)).get(22));
					//						attribMap.put("IF_SIGHT_BILL", (recordList.get(0)).get(23));
					//						attribMap.put("DISCREPANCY_INSTRUCTION", (recordList.get(0)).get(24));
					//						attribMap.put("SETTLEMENT_INSTRUCTION", (recordList.get(0)).get(25));
					//					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"setTransactionDataLc attribMap: "
							+ attribMap.toString());
				}if ("SG".equalsIgnoreCase(reqCat)) {/*   
					if("SG_NILC".equalsIgnoreCase(requestType)){ //Shipping_Gtee_10
						formObject.setValue(transRefOutFieldName,(recordList.get(0)).get(14));// OTHER_TRAN_REF_NUM
						if(recordList.get(0).get(15) != null && !recordList.get(0).get(15).equalsIgnoreCase("")){
							formObject.setValue(TFO_BRANCH_CITY,(recordList.get(0)).get(15));// ISSUING_CENTER
							formObject.setValue(TFO_ASSIGNED_CENTER,(recordList.get(0)).get(15));// PROCESSING_CENTER
						}
					}	
				 */}
			} catch (Exception e1) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in setting value from outputlist " + e1);
			}
			String sWhere = " WI_NAME = '"+WI_NAME+"'";
			if("DC".equalsIgnoreCase(mode) || "DCR".equalsIgnoreCase(mode) || "C".equalsIgnoreCase(mode)
					|| "R".equalsIgnoreCase(mode)){
				ProdCreateXML.APUpdateFromMap(sessionId,"EXT_TFO",attribMap,sWhere);
			}
			retMsg = setTransactionDataLcFCUBS(refNo,recordList, reqCat, requestType);
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in setting value from outputlist 2" + e);
		}
		return retMsg;
	}
	@SuppressWarnings("unchecked")
	private String setTransactionDataLcFCUBS(String refNo,List<List<String>> recordList, String reqCat,String reqType) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"inside setTransactionDataLcFCUBS >>");
		String columnName = "";
		String tableName = "";
		String whereClause = "";
		String tagName = "";
		String retMsg = "";
		try {
			String strQ = "";
			String sFetchedBranchCode = "";
			String sContractRefNo = "";
			List<List<String>> sOutputlist = null;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"setTransactionDataLcFCUBS requestCategory: " + requestCategory);
			reqCat = requestCategory;
			if ("ELC".equalsIgnoreCase(reqCat)
					|| "ILC".equalsIgnoreCase(reqCat)
					|| "EC".equalsIgnoreCase(reqCat)
					|| "IC".equalsIgnoreCase(reqCat)		
					|| "ELCB".equalsIgnoreCase(reqCat)
					|| "ILCB".equalsIgnoreCase(reqCat)
					|| "DBA".equalsIgnoreCase(reqCat)   // ILC and ELC
					|| "SG".equalsIgnoreCase(reqCat)) { //Shipping_Gtee_10
				if ("EC".equalsIgnoreCase(reqCat)							
						|| "IC".equalsIgnoreCase(reqCat)
						|| ("ELCB".equalsIgnoreCase(reqCat)
								&& !"ELCB_BL".equalsIgnoreCase(requestType))
								|| ("ILCB".equalsIgnoreCase(reqCat) 
										&& !"ILCB_BL".equalsIgnoreCase(requestType)) // US3160
										|| "DBA".equalsIgnoreCase(reqCat)
										|| "SG_SD".equalsIgnoreCase(requestType)) {
					if (recordList != null && !recordList.isEmpty()) {
						sContractRefNo = refNo;
						sFetchedBranchCode = recordList.get(0).get(21).equals("") ? sContractRefNo.toString()
								.substring(0, 3) : recordList.get(0).get(21);
					} else {
						sContractRefNo = refNo;
						sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
					}
				} else if ("ELC".equalsIgnoreCase(reqCat)
						|| "ILC".equalsIgnoreCase(reqCat)
						|| "ELCB_BL".equalsIgnoreCase(requestType)
						|| "ILCB_BL".equalsIgnoreCase(requestType)// ILC and ELC
						|| "SG_NILC".equalsIgnoreCase(requestType)) {  //Shipping_Gtee_10
					if (recordList != null && !recordList.isEmpty() && !refNo.toString().equalsIgnoreCase("")) {
						sContractRefNo = refNo;
						sFetchedBranchCode = recordList.get(0).get(16).equals("") ? sContractRefNo
								.substring(0, 3) : recordList.get(0).get(16);
					} else {
						sContractRefNo = refNo;
						sFetchedBranchCode = sContractRefNo.substring(0, 3).toString();
					}
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sFetchedBranchCode " + sFetchedBranchCode);
				columnName = "RELATIONSHIP_TYPE";
				tagName = "RELATIONSHIP_TYPE";
				tableName = "TFO_DJ_BRN_REL_TYPE_MAP_MAST ";
				whereClause = "BRANCH_CODE='" + sFetchedBranchCode + "'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
				if(sOutputlist != null){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Relationship Type returned "+ sOutputlist.get(0).get(0).toString());
					sRelationType = sOutputlist.get(0).get(0);
					sOutputlist = null;
				}
				columnName = "OPERATION_NAME";
				tagName = "OPERATION_NAME";
				tableName =  "TFO_DJ_INQUIRY_OPERATION_MST ";
				whereClause =  "REQUEST_CATEGORY_CODE='" + reqCat + "' AND REQUEST_TYPE_CODE = '" + requestType + "' AND BRANCH_CODE='"
						+ sRelationType + "' AND PROCESS_TYPE='BAU'";
				sOutputlist = APCallCreateXML.getDataFromDB(columnName,tableName,whereClause,tagName);
				if(sOutputlist != null){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Operation Name returned "
							+ sOutputlist.get(0).get(0).toString());
				}
				if (!sOutputlist.equals(null)) {
					if (!sOutputlist.get(0).get(0).equalsIgnoreCase("NA")) {
						String returnFetchMessage[] = fetchCustomerContractData(sContractRefNo, sOutputlist.get(0).get(0).toString(), 
								sFetchedBranchCode,reqCat, requestType).split("##");
						int statusCode = Integer.parseInt(returnFetchMessage[0]);
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"returnFetchMessage n status: "+returnFetchMessage.toString()+"->"+statusCode);
						if(returnFetchMessage.length>1){
							retMsg = returnFetchMessage[1];
						}
						if (statusCode == 2) {
							LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"customerID : "+customerID);
							if (("").equals(customerID)) {
								//JOptionPane.showMessageDialog(null,"Unable to fetch Contract details. Kindly Enter correct Reference Number.");
								retMsg = "Unable to fetch Contract details. Kindly Enter correct Reference Number.";
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :501 N");
								callStatus = "N";
							} else {
								LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :504 Y");
								callStatus = "Y"; 
							}
						}
					} else {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :509 Y");
						callStatus = "Y"; 
					}
				} else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :513 N");
					callStatus = "N";
				}
			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"callStatus :517 N");
				callStatus = "N";
			}

		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"Exception in ENQUIREY SERVICE 2" + e);
		}
		return retMsg;
	}
	public void insertAmendmnentDetails(XMLParser xmlParser,String reqCategory,String requestType){
		HashMap<String, LinkedHashMap<Integer,  HashMap<String, String>>>  attribComplexMap = new HashMap<String, LinkedHashMap<Integer,  HashMap<String, String>>>();
		LinkedHashMap<Integer, HashMap<String, String>> innerComplexAttribMap = new LinkedHashMap<Integer, HashMap<String, String>>();
		HashMap<String, String> dummyMap = new HashMap<String, String>();
		HashMap<String, String> attribMap = new HashMap<String, String>();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"XML PArser Data"+xmlParser.toString());
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertAmendmnentDetail starts");
		try {
			if("AM".equalsIgnoreCase(requestType) ||"ELC_SLCAA".equalsIgnoreCase(requestType) 
					||"SBLC_AM".equalsIgnoreCase(requestType) || "GAA".equalsIgnoreCase(requestType))
			{
				String sExpiryDate = xmlParser.getValueOf("expiryDate").equals("null") ? "" 
						:xmlParser.getValueOf("expiryDate").equals("") ? "" :xmlParser.getValueOf("expiryDate"); 
				String insertionId = getInsertIonIdForTable("TFO_DJ_AMENDMENT_FRAME_DATA");
				String sExpiryConditions= createNormalizedXML(xmlParser.getValueOf("expiryConditionOrEvent")); //BY KISHAN
				String sExpiryType = xmlParser.getValueOf("expiryType");
				String sTransactionAmount= xmlParser.getValueOf("contractAmount");
				String sCounterGuaranteeExpiryDate="";

				if("AM".equalsIgnoreCase(requestType)) //Condition to fill value only when GRNT AM . As per Req.. .
					sCounterGuaranteeExpiryDate = xmlParser.getValueOf("guaranteeExpiryDate");
				String trnTenor = "";

				if("LIMT".equalsIgnoreCase(sExpiryType)){
					trnTenor ="FD";
				}else if("UNLM".equalsIgnoreCase(sExpiryType)){
					trnTenor ="OE";
				}else if("COND".equalsIgnoreCase(sExpiryType)){
					trnTenor ="COND";
				}
				/*if("FIXD".equalsIgnoreCase(sExpiryType)){
							trnTenor ="LIMT";
						}else if("Open".equalsIgnoreCase(sExpiryType)){
							trnTenor ="UNLM";
						}else if("Conditional".equalsIgnoreCase(sExpiryType)){
							trnTenor ="COND";
						}*/
				attribMap.put("FC_TRANSACTION_AMOUNT",sTransactionAmount);
				attribMap.put("FC_EXPIRY_TYPE",trnTenor);
				innerComplexAttribMap.put(0, attribMap);
				attribComplexMap.put("AMEND",innerComplexAttribMap);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertAmendmnentDetail insert query :"+attribMap);
				String sWhere = " WINAME = '"+WI_NAME+"'";
				ProdCreateXML.APUpdateFromMap(sessionId,"TFO_DJ_AMENDMENT_FRAME_DATA",attribMap,sWhere);
				String sParams = "'"+WI_NAME+"','"+reqCategory+"','"+requestType+"','"+sExpiryDate+"','','','','','',"
						+sExpiryConditions+",'"+sCounterGuaranteeExpiryDate+"'";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sParams insert query :"+sParams);

				APCallCreateXML.APProcedure(sessionId, "TFO_APUPDATEWITHDATE", sParams, 7);
			}else{
				String amount = xmlParser.getValueOf("contractAmount");
				String expDate = xmlParser.getValueOf("ExpiryDate").equals("null") ? "" :xmlParser.getValueOf("ExpiryDate").equals("") ? "" :xmlParser.getValueOf("ExpiryDate"); 
				String sPositiveTolerance = xmlParser.getValueOf("positiveTolerance");
				String sNegativeTolerance = xmlParser.getValueOf("negativeTolerance");
				String sFromPlace = xmlParser.getValueOf("fromPlace");
				String sToPlace = xmlParser.getValueOf("toPlace");
				String sLatestShipmentDate = xmlParser.getValueOf("latestShipmentDate");
				String sPortOfDischarge = xmlParser.getValueOf("portOfDischarge");
				String sPortOfLoading = xmlParser.getValueOf("portOfLoading");
				String sGoodsDescription = xmlParser.getValueOf("goodsDescription");
				//String insertionId = getInsertIonIdForTable("TFO_DJ_AMENDMENT_FRAME_DATA");
				sFromPlace = checkSplChar(sFromPlace);
				sToPlace = checkSplChar(sToPlace);
				String sExpiryPlace = xmlParser.getValueOf("expiryPlace");
				String sGoodsCode = xmlParser.getValueOf("goodsCode");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sexpiryPlace="+sExpiryPlace);

				String winame = WI_NAME;
				sPortOfDischarge = checkSplChar(sPortOfDischarge);
				sPortOfLoading = checkSplChar(sPortOfLoading);
				sGoodsDescription = createNormalizedXMLForProc(sGoodsDescription);
				/*	if(sGoodsDescription == null || "".equalsIgnoreCase(sGoodsDescription)) {
					sGoodsDescription = "''";
				} */

				//Protrade_final_sheet 360-361
				//requestType = xp.getValueOf("REQUEST_TYPE");
				String may_Confirm =xmlParser.getValueOf("mayConfirmFlag");  
				String CONFIRMATION_INSTRUCTION="";
				String sexpiryPlace1 = xmlParser.getValueOf("expiryPlace");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"sexpiryPlace1="+sexpiryPlace1);

				String screditMode = xmlParser.getValueOf("creditMode");
				//String req_type = formObject.getValue(REQUEST_TYPE).toString();
				String sdraftTenor = xmlParser.getValueOf("draftTenor");
				String screditDaysFrom = xmlParser.getValueOf("creditDaysFrom");
				String sdraftAmount = xmlParser.getValueOf("draftAmount");
				String sdrawee = xmlParser.getValueOf("drawee");
				String screditDetails = xmlParser.getValueOf("creditDetails");
				String spartialShipmentAllowed = xmlParser.getValueOf("partialShipmentAllowed");
				String spartialShipmentDetails = createNormalizedXMLForProc(xmlParser.getValueOf("partialShipmentDetails"));
				//			String spartialShipmentDetails = xmlParser.getValueOf("partialShipmentDetails");
				String stransShipmentAllowed = xmlParser.getValueOf("transShipmentAllowed");
				String stransShipmentDetails = createNormalizedXMLForProc(xmlParser.getValueOf("transShipmentDetails"));
				//			String stransShipmentDetails = xmlParser.getValueOf("transShipmentDetails");
				String srequestedConfirmationParty = xmlParser.getValueOf("requestedConfirmationParty");
				String schargesFromBeneficiary = xmlParser.getValueOf("chargesFromBeneficiary");

				String sspecialPaymemtConditionForBeneficiary = createNormalizedXMLForProc(xmlParser.getValueOf
						("specialPaymemtConditionForBeneficiary"));
				//			String sspecialPaymemtConditionForBeneficiary = xmlParser.getValueOf
				//					("specialPaymemtConditionForBeneficiary");
				String speriodOfPresentationDays = xmlParser.getValueOf("periodOfPresentationDays");
				String speriodOfPresentationMode = xmlParser.getValueOf("periodOfPresentationMode");
				if("ILC_AM".equalsIgnoreCase(requestType) ){//protrade_final 360-361
					String soperation_code = xmlParser.getValueOf("operationCode");
					if(soperation_code.equalsIgnoreCase("ONC") || soperation_code.equalsIgnoreCase("CONFIRM")){
						CONFIRMATION_INSTRUCTION ="C";
					}else if (soperation_code.equalsIgnoreCase("OPN") && may_Confirm.equalsIgnoreCase("N")){
						CONFIRMATION_INSTRUCTION ="W";
					}else if (soperation_code.equalsIgnoreCase("OPN") && may_Confirm.equalsIgnoreCase("Y")){
						CONFIRMATION_INSTRUCTION ="M";
					}
				}
				if("ELC_LCAA".equalsIgnoreCase(requestType) ||"ELC_LCC".equalsIgnoreCase(requestType)){
					String soperation_code = xmlParser.getValueOf("operationCode");
					if(soperation_code.equalsIgnoreCase("ANC") || soperation_code.equalsIgnoreCase("CONFIRM")){
						CONFIRMATION_INSTRUCTION ="C";
					}else if (soperation_code.equalsIgnoreCase("ADV") && may_Confirm.equalsIgnoreCase("N")){
						CONFIRMATION_INSTRUCTION ="W";
					}else if (soperation_code.equalsIgnoreCase("ADV") && may_Confirm.equalsIgnoreCase("Y")){
						CONFIRMATION_INSTRUCTION ="M";
					}
				}
				String shipmentPeriod = xmlParser.getValueOf("shipmentPeriod");
				//End
				attribMap.put("FC_GOODS_CODE",sGoodsCode);
				attribMap.put("FC_TRANSACTION_AMOUNT",amount);
				//attribMap.put("FC_EXPIRY_DATE",formatToYYYYMMDD(expDate));
				attribMap.put("FC_UNDER_TOLERANCE",sNegativeTolerance);
				attribMap.put("FC_ABOVE_TOLERANCE",sPositiveTolerance);
				attribMap.put("FC_SHIPMENT_FROM",sFromPlace);
				attribMap.put("FC_SHIPMENT_TO",sToPlace);
				//attribMap.put("FC_LATEST_SHIPMENT_DATE",formatToYYYYMMDD(sLatestShipmentDate));
				attribMap.put("FC_PORT_OF_DISCHARGE",sPortOfDischarge);
				attribMap.put("FC_PORT_OF_LOADING",sPortOfLoading);
				//attribMap.put("FC_DESCRIPTION_OF_GOODS",sGoodsDescription);
				attribMap.put("FC_DRAFT_CREDIT_DAYS_FROM_DT","");
				attribMap.put("FC_DRAFT_CREDIT_DAYS_FROM_DAYS",screditDaysFrom);
				attribMap.put("FC_DRAFT_AMOUNT",sdraftAmount);
				attribMap.put("FC_DRAFT_SPECIFY_OTHERS","");
				attribMap.put("FC_DRAFT_DRAWEE_BANK",sdrawee);
				attribMap.put("FC_DEFERRED_PAYMENT",screditDetails);
				attribMap.put("FC_PARTIAL_SHIPMENT",spartialShipmentAllowed);
				//			attribMap.put("FC_PARTIAL_SHIPMENT_CONDITION",spartialShipmentDetails);
				attribMap.put("FC_TRANSSHIPMENT",stransShipmentAllowed);
				//			attribMap.put("FC_TRANSSHIPMENT_CONDITION",stransShipmentDetails);
				attribMap.put("FC_SHIPMENT_PERIOD",shipmentPeriod);

				attribMap.put("FC_INCOTERM","");
				attribMap.put("FC_PLACE_OF_EXPIRY",sExpiryPlace);
				attribMap.put("FC_CREDIT_MODE",screditMode);
				attribMap.put("FC_DRAFT_TENOR",sdraftTenor);
				attribMap.put("FC_REQUESTED_CONFIRMATION_PARTY",srequestedConfirmationParty);
				attribMap.put("FC_CHARGES",schargesFromBeneficiary);
				//			attribMap.put("FC_SPL_PAY_COND_FOR_BEN",sspecialPaymemtConditionForBeneficiary);
				attribMap.put("FC_PERIOD_OF_PRESENTATION_DAYS",speriodOfPresentationDays);
				attribMap.put("FC_PERIOD_OF_PRESENTATION_MODE",speriodOfPresentationMode);
				attribMap.put("FC_CONFIRMATION_INSTRUCTION",CONFIRMATION_INSTRUCTION);

				String insertionId = getInsertIonIdForTable("TFO_DJ_AMENDMENT_FRAME_DATA");

				innerComplexAttribMap.put(0, attribMap);
				attribComplexMap.put("AMEND",innerComplexAttribMap);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"insertAmendmnentDetail insert query :"+attribMap);
				//APCallCreateXML.APDelete("TFO_DJ_CONTRACT_LIMITS_DETAILS", "winame='"+winame+"'", sessionId);
				String sWhere = " WINAME = '"+WI_NAME+"'";
				ProdCreateXML.APUpdateFromMap(sessionId,"TFO_DJ_AMENDMENT_FRAME_DATA",attribMap,sWhere);
				String sParams = "'"+WI_NAME+"','"+reqCategory+"','"+requestType+"','"+expDate+"','"+sLatestShipmentDate+"',"+sGoodsDescription+
						","+spartialShipmentDetails+","+stransShipmentDetails+","+sspecialPaymemtConditionForBeneficiary
						+",'',''";
				//			String sParams = "'"+WI_NAME+"','"+expDate+"','"+sLatestShipmentDate+"',"+sGoodsDescription;
				APCallCreateXML.APProcedure(sessionId, "TFO_APUPDATEWITHDATE", sParams, 7);
				//			APCallCreateXML.APProcedure(sessionId, "TFO_APUPDATEWITHDATE", sParams, 4);

			}
		} catch (Exception e) {		
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR,"exception in saveDecHistory: " + e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"InsertAmendmnentDetail Ends");
	}
}
