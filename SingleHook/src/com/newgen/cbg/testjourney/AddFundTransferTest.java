package com.newgen.cbg.testjourney;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.ICallExecutor;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ExecuteXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class AddFundTransferTest implements ICallExecutor, Callable<String> {

	private String WI_NAME;
	private int stageId;
	private String sessionId;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String senderID;
	private String sTransactionAmount;
	private String sTransactionCode;
	private String sTransactionCurrencyCode;
	private String sLocalCurrencyCode;
	private String sConvRate;
	private String sorgbrCode;
	private String sCalculateAmtFlag;
	private String sAmntlocalCurr;
	private String sRepeatFlag;
	private String sServiceCharge;
	private String sForceDebitFlag;
	private String sPostSuspenseFlag;
	private String sDebitAcctNumber;
	private String sDebitBrCode;
	private String sDebitNarration;
	private String sDebitAccCurrCode;
	private String sDebitAccTxnAmnt;
	private String sConvRateToLCY;
	private String sCreditAccNumber;
	private String sCreditBrCode;
	private String sCreditNarration;
	private String sCreditAccCurrCode;
	private String sCreditAccTxnAmnt;
	private String sCreditRateConvToLCY;
	private String sDocRefnumber;
	private String pgEncrypt;
	private String ftDebitCardNo;
	private String refNo;
	private String ftStatus;
	private String ftTransactionRefNumber;
	private String encryptedCardDetails;
	String [][]blocksNFields;
	private boolean prevStageNoGo;
	private Map<String, String> defaultMap;
	private CallEntity callEntity;

	public AddFundTransferTest(Map<String, String> defaultMap, String sessionId, String stageId, String WI_NAME, String ftTransactionRefNumber) {
		this.sessionId=sessionId;
		this.WI_NAME = WI_NAME;
		this.stageId = Integer.parseInt(stageId);
		this.defaultMap = defaultMap;
		this.ftTransactionRefNumber = ftTransactionRefNumber;
		String outputXML;
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer defaultMap ===> " + defaultMap.toString());
		try {

			outputXML = APCallCreateXML.APSelect("SELECT TJ_ACCOUNT_NUM, TJ_BRANCH_CODE FROM USR_0_CBG_TEST_J_ADMIN_MASTER ORDER BY ACTION_DATETIME DESC");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddFundTransfer TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					sCreditAccNumber = xp.getValueOf("TJ_ACCOUNT_NUM");	
					sCreditBrCode= xp.getValueOf("TJ_BRANCH_CODE");	
				}
			}

			outputXML = APCallCreateXML.APSelect("SELECT FT_AMOUNT, PG_ENCRYPTED_RESPONSE, FT_ENCRYPTED_CARD_DETAILS FROM USR_0_CBG_FT WHERE FT_TRANSACTION_REF_NUMBER = N'" + ftTransactionRefNumber + "'");
			xp = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddFundTransfer TotalRetrieved: "+Integer.parseInt(xp.getValueOf("TotalRetrieved")));
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){
					sTransactionAmount = xp.getValueOf("FT_AMOUNT");
					encryptedCardDetails = xp.getValueOf("FT_ENCRYPTED_CARD_DETAILS");
					String decryptedCardDetails = DSCOPUtils.getInstance().decryptPayload602(sessionId, encryptedCardDetails);
					StringTokenizer st = new StringTokenizer(decryptedCardDetails,"|");
					ftDebitCardNo = st.nextToken();
					ftDebitCardNo = DSCOPUtils.getInstance().maskCreditCardNo(ftDebitCardNo);
					pgEncrypt = decryptResponse((xp.getValueOf("PG_ENCRYPTED_RESPONSE").split("\\|\\|"))[1]);
					
				}
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer pgEncrypt ===> " + pgEncrypt);
			blocksNFields = DSCOPUtils.getInstance().getResponseList(pgEncrypt);
			SimpleDateFormat f = new SimpleDateFormat("DD-MMM-yyyy hh:mm:ss a");
		    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMDDHHmmss");
		    sDebitNarration = "HAYYAK_FT_"+ ftDebitCardNo + "_"+ff.format((f.parseObject(blocksNFields[2][2])))+"_"+blocksNFields[2][6]+"_"+blocksNFields[2][1];
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer sDebitNarration ===> " + sDebitNarration);
			sCreditNarration = "HAYYAK_FT_" + ftDebitCardNo.substring(ftDebitCardNo.length() - 4, ftDebitCardNo.length()) + "_"+blocksNFields[2][6]+"_"+blocksNFields[2][1];
			sDocRefnumber = blocksNFields[2][1];
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer DebitNarration ===> " + sDebitNarration);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer sDocRefnumber ===> " + sCreditNarration);
			
			senderID =defaultMap.get("SENDER_ID"); //"IBR";
			sTransactionCurrencyCode = defaultMap.get("FT_CURRENCY");
			sTransactionCode = defaultMap.get("FT_TRANSACTION_CODE");
			sorgbrCode = defaultMap.get("FT_ORG_BRANCH_CODE");
			sDebitAcctNumber =defaultMap.get("FT_DEBIT_ACC_NUMBER");
			sDebitBrCode = defaultMap.get("FT_DEBIT_BRANCH_CODE");
//			sCreditBrCode=defaultMap.get("FT_CREDIT_BRANCH_CODE");

			sCalculateAmtFlag="N";
			sRepeatFlag="N";
			sServiceCharge="N";
			sForceDebitFlag="N";
			sPostSuspenseFlag="N";
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);
		}
	}

	@Override
	public String call() throws Exception {
		String inputXml = createInputXML();
		String outputXml =  ExecuteXML.executeWebServiceSocket(inputXml);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer outputXml ===> " + outputXml);
		if(outputXml==null ||outputXml.equalsIgnoreCase("")){
			outputXml= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
			callStatus = "N";
			updateCallOutput(inputXml);
		} else {
			responseHandler(outputXml, inputXml);			
			updateCallOutput(inputXml);
		}		
		
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddFundTransfer outputXml ===> "+ outputXml);
		return outputXml;
	}

	@Override
	public void executeDependencyCall() throws Exception{
		CallHandler.getInstance().executeDependencyCall(callEntity, defaultMap, sessionId, WI_NAME, prevStageNoGo);
	}
	
	@Override
	public String createInputXML() throws Exception {
		StringBuilder inputXML = new StringBuilder();
		refNo = DSCOPUtils.getInstance().generateSysRefNumber();
		inputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
		.append("<Calltype>DSCOP</Calltype>").append("\n")
		.append("<CBGCallType>AddFundTransfer</CBGCallType>").append("\n")		
		.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
		.append("<OLDREF_NO>"+ refNo +"</OLDREF_NO>").append("\n")
		.append("<senderID>" + senderID + "</senderID>").append("\n")
		.append("<sTransactionAmount>" + sTransactionAmount + "</sTransactionAmount>").append("\n")
		.append("<sTransactionCode>" + sTransactionCode + "</sTransactionCode>").append("\n")
		.append("<sTransactionCurrencyCode>" + sTransactionCurrencyCode + "</sTransactionCurrencyCode>").append("\n") 
		.append("<sLocalCurrencyCode>" + sTransactionCurrencyCode + "</sLocalCurrencyCode>").append("\n")
		.append("<sConvRate>" + sTransactionAmount + "</sConvRate> ").append("\n")
		.append("<sorgbrCode>" + sorgbrCode + "</sorgbrCode> ").append("\n")
		.append("<sCalculateAmtFlag>" + sCalculateAmtFlag + "</sCalculateAmtFlag>").append("\n")
		.append("<sAmntlocalCurr>" + sTransactionAmount + "</sAmntlocalCurr> ").append("\n")
		.append("<sRepeatFlag>" + sRepeatFlag + "</sRepeatFlag>").append("\n")
		.append("<sServiceCharge>" + sServiceCharge + "</sServiceCharge>").append("\n")
		.append("<sForceDebitFlag>" + sForceDebitFlag + "</sForceDebitFlag>").append("\n")
		.append("<sPostSuspenseFlag>" + sPostSuspenseFlag + "</sPostSuspenseFlag>").append("\n")
		.append("<sDebitAcctNumber>" + sDebitAcctNumber + "</sDebitAcctNumber>").append("\n")
		.append("<sDebitBrCode>" + sDebitBrCode + "</sDebitBrCode>").append("\n")
		.append("<sDebitNarration>" + sDebitNarration + "</sDebitNarration>").append("\n")
		.append("<sDebitAccCurrCode>" + sTransactionCurrencyCode + "</sDebitAccCurrCode>").append("\n")
		.append("<sDebitAccTxnAmnt>" + sTransactionAmount + "</sDebitAccTxnAmnt>").append("\n")
		.append("<sConvRateToLCY>" + sTransactionAmount + "</sConvRateToLCY>").append("\n")
		.append("<sCreditAccNumber>" + sCreditAccNumber + "</sCreditAccNumber>").append("\n")
		.append("<sCreditBrCode>" + sCreditBrCode + "</sCreditBrCode> ").append("\n")
		.append("<sCreditNarration>" + sCreditNarration + "</sCreditNarration>").append("\n")
		.append("<sCreditAccCurrCode>" + sTransactionCurrencyCode + "</sCreditAccCurrCode>").append("\n")
		.append("<sCreditAccTxnAmnt>" + sTransactionAmount + "</sCreditAccTxnAmnt>").append("\n")
		.append("<sCreditRateConvToLCY>" + sTransactionAmount + "</sCreditRateConvToLCY>").append("\n")
		.append("<sDocRefnumber>" + ftTransactionRefNumber + "</sDocRefnumber>").append("\n")
		.append("</APWebService_Input>");
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "AddFundTransfer inputXML ===> "+inputXML.toString());
		return inputXML.toString();   
	}

	private String  decryptResponse(String encryptedResponse){
		String encryptedCardDetails = "";
		try {
			StringBuilder inputXml = new StringBuilder();
			String refNo = DSCOPUtils.getInstance().generateSysRefNumber();
			inputXml.append("<?xml version=\"1.0\"?>").append("\n")
			.append("<APWebService_Input>").append("\n")
			.append("<Option>WebService</Option>").append("\n")
			.append("<EngineName>" + DSCOPConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
			.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
			.append("<Calltype>DSCOP</Calltype>").append("\n")
			.append("<CBGCallType>Decrypt</CBGCallType>").append("\n")
			.append("<EncryptedPayLoad>"+ encryptedResponse +"</EncryptedPayLoad>").append("\n")
			.append("</APWebService_Input>");
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " FT Decrypt Payload input xml:" + inputXml.toString());
			String outputXML = ExecuteXML.executeWebServiceSocket(inputXml.toString());
			//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, " FT Decrypt Payload outputXml:" + outputXML.toString());
			XMLParser xp = new XMLParser(outputXML);
			encryptedCardDetails = xp.getValueOf("DecryptedPayload");
			//DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "FT Decrypt Payload Output" + encryptedCardDetails);
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, e);			
		}
		return encryptedCardDetails;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		return call();
	}

	@Override
	public void responseHandler(String outputXML, String inputXML) throws Exception {
		XMLParser xp = new XMLParser(outputXML);
		returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
		errorDescription = xp.getValueOf("errorDescription", "", true);
		errorDetail = xp.getValueOf("errorDetail", "", true);
		status = xp.getValueOf("Status", "", true);
		reason = xp.getValueOf("Reason", "", true);
		if(returnCode == 0) {
			callStatus = "Y";
			sTransactionAmount = xp.getValueOf("transactionAmount");
			ftStatus = xp.getValueOf("status");
			if(ftStatus.equalsIgnoreCase("success")){
				ftStatus = "Y";
			} else {
				ftStatus = "N";
			}
		} else {
			callStatus = "N";
			ftStatus = "N";
		}
		
		updateCallOutput(inputXML);			
	}

	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		String valList = "'"+ WI_NAME +"',"+ stageId +", 'AddFundTransfer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		String outputXML = APCallCreateXML.APInsert("USR_0_DSCOP_CALL_OUT", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON", valList, sessionId);

		if(callStatus.equals("Y")){
			outputXML = APCallCreateXML.APUpdate("USR_0_CBG_FT", "FT_STATUS_CODE,FT_SUCCESS_COUNT,FT_IS_ELIGIBLE", "'"+ ftStatus +"','1','N'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTransactionRefNumber +"'", sessionId);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddFundTransfer FT_STATUS_CODE and FT_SUCCESS_COUNT UPDATE: "+ mainCode);
			}

			String valList2 = "'"+ WI_NAME +"',"+ stageId +", 'AddFundTransfer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 0";
			outputXML = APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON,"
					+ " RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);		
		}
		else {
			outputXML = APCallCreateXML.APUpdate("USR_0_CBG_FT", "FT_STATUS_CODE,FT_IS_ELIGIBLE", "'"+ ftStatus +"','N'", " FT_TRANSACTION_REF_NUMBER = N'"+ ftTransactionRefNumber +"'", sessionId);
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if(mainCode == 0){
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"AddFundTransfer FT_STATUS_CODE and FT_SUCCESS_COUNT UPDATE: "+ mainCode);
			}
			
			String valList2 = "'"+ WI_NAME +"',"+ stageId +", 'AddFundTransfer', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
					errorDetail +"', '"+ status +"', '"+ reason +"', 0, "+ refNo +", '"+ inputXml+"', 1";
			outputXML = APCallCreateXML.APInsert("USR_0_DSCOP_REQUEST", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, ERROR_DETAIL, STATUS, REASON, "
					+" RETRYCOUNT, OLDREFNO, REQUEST, CALL_STATE", valList2, sessionId);
		}
	}
		
}
