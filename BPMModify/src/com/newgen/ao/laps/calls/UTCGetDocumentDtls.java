package com.newgen.ao.laps.calls;

import java.text.SimpleDateFormat;
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


public  class UTCGetDocumentDtls implements ICallExecutor, Callable<String> {
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String batchNo;
	private String curr_ws;
	private String COMPLETE_FLAG;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String leadRefNumber ="";
	private String senderID;
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private HashMap<String, String> newAttributeMap ;
	private CallEntity callEntity;
	private String auditCallName = "UTCGetDocumentDtls";
	boolean isCallExecuted;
	private int utcExpiryTime;

	
	


	public UTCGetDocumentDtls(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {

		
		this.batchNo= WI_NAME;
		this.WI_NAME = batchNo.substring(0,batchNo.indexOf("REQUEST")+7);
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;
//		utcExpiryTime = LapsConfigurations.getInstance().utcExpiryTime;
		utcExpiryTime = 15;
		String outputXML= "";
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCGetDocumentDtls CONSTRUCTOR");
			
		} catch(Exception e){

		}

	}

			
@Override
public String call() throws Exception {
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCGetDocumentDtls CALL");

	StringBuffer outputXml = new StringBuffer();
	String finalOutputXml = "";
	try {
		//if(!getCallStatus().equalsIgnoreCase("Y")){//Commenetd for Batch Invoicing
			String outputXML= "";
			XMLParser xp;
			/*outputXML = APCallCreateXML.APSelect("SELECT BATCH_NO FROM TFO_DJ_UTC_INVOICE_DETAIL WHERE WI_NAME =  N'" + WI_NAME + "'");
			xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				if(Integer.parseInt(xp.getValueOf("TotalRetrieved")) > 0){		
					batchNo = xp.getValueOf("BATCH_NO");
					updateCheckFlag = true;
				}else{
					updateCheckFlag = false;
				}
			}*///Commenetd for Batch Invoicing
			String inputXml = createInputXML();
			if(updateCheckFlag){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCGetDocumentDtls updateCheckFlag");
				outputXml.append(LapsConfigurations.getInstance().socket.connectToSocket(inputXml));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml UTCGetDocumentDtls "+outputXml.toString());
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument outputXml ===> " + outputXml.toString());
				XMLParser xmlResponse = new XMLParser(outputXml.toString());


				if(outputXml==null ||outputXml.toString().equalsIgnoreCase("")){
					outputXml.append("<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>");
				} 			
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml outputXml "+outputXml);

				responseHandler(outputXml,inputXml);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "UTCG004", "EIDAMQ output:"+outputXml.toString(), sessionId);

			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCGetDocumentDtls else");
				callStatus = "N";
				returnCode = 1;
				updateCallOutput(outputXml);
			}
/*		} else {
			outputXml.append("<returnCode>0</returnCode><errorDescription>Call Executed</errorDescription>");

		}*///Commenetd for Batch Invoicing
	}catch (Exception e) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG100", e, sessionId);
	}
	return outputXml.toString();
}
			
@Override
public String createInputXML() throws Exception {
	StringBuilder inputXML = new StringBuilder(); 
	try {
		refNo = LapsUtils.getInstance().generateSysRefNumber();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		inputXML.append("<?xml version=\"1.0\"?>").append("\n")
		.append("<APWebService_Input>").append("\n")
		.append("<Option>WebService</Option>").append("\n")
		.append("<Calltype>WS-2.0</Calltype>").append("\n")
		.append("<InnerCallType>GetDocumentDetails</InnerCallType>").append("\n")      // (getDocumentDetail)
		.append("<ServiceName>ModInvoiceValidation</ServiceName>").append("\n")
		.append("<REF_NO>" + refNo + "</REF_NO>").append("\n")
		.append("<OLDREF_NO>" + refNo + "</OLDREF_NO>").append("\n")
		.append("<senderID>WMSBPMENG</senderID>").append("\n")
		.append("<WINAME>" + WI_NAME + "</WINAME>").append("\n")
		.append("<batchNo>" + batchNo + "</batchNo>").append("\n")
		.append("<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>").append("\n")
		.append("<SessionId>" + sessionId + "</SessionId>").append("\n")
		.append("</APWebService_Input>");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument inputXML ===> "+inputXML.toString());
	} catch(Exception e){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG100", e, sessionId);
	}
	return inputXML.toString();

}

@Override
public String executeCall(HashMap<String, String> input) throws Exception {
	// TODO Auto-generated method stub
	return call();
}
				


public void responseHandler(StringBuffer outputXML, String inputXml) throws Exception {
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument responseHandler inside ===> ");
	XMLParser xp = new XMLParser(outputXML.toString());
	returnCode = Integer.parseInt(xp.getValueOf("returnCode", "1", true));
	errorDescription = xp.getValueOf("errorDescription", "", true);
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument inputXML ===> "+returnCode);
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument inputXML ===> "+errorDescription);


	try{
		if(returnCode == 0){
			callStatus = "Y";
		}else{
			callStatus = "N";
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG100", "FetchUAEPassDocumentDtls Failed", sessionId);
		}
		updateCallOutput(outputXML);
	} catch (Exception e) {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG100", e, sessionId);
	}	
}

	public void updateCallOutput(StringBuffer outputXML) throws Exception 
	{ 
		
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument updateCallOutput inside ===> ");
		if (callStatus.equalsIgnoreCase("Y")){
			status = "Success";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument updateCallOutput callStaue = Y ===> ");
			//outputXML = outputStrin();
			XMLParser xp = new XMLParser(outputXML.toString());
			int start = xp.getStartIndex("getDocumentDtlsRes", 0, 0);
			int deadEnd = xp.getEndIndex("getDocumentDtlsRes", start, 0);
			int noOfFields = xp.getNoOfFields("documentDetails", start,deadEnd);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "noOfFields "+noOfFields);
			String messageStatus = xp.getValueOf("messageStatus");
			String UTCMessageID = xp.getValueOf("UTCMessageID");

			int end = 0;
			for(int i = 0; i < noOfFields; ++i){
				start = xp.getStartIndex("documentDetails", end, 0);
				end = xp.getEndIndex("documentDetails", start, 0);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "start "+start +"end " + end);

				String documentNo= xp.getValueOf("documentNo",start,end).trim();
				String UTCRefNo= xp.getValueOf("utcRefNo",start,end);
				String ruleExecutionDate= xp.getValueOf("ruleExecutionDate",start,end);
				String duplicateRisk= xp.getValueOf("duplicate",start,end);
				String financialRisk= xp.getValueOf("financialRisk",start,end);
				String ruleStep= xp.getValueOf("step",start,end);
				@SuppressWarnings("unused")
				String score= xp.getValueOf("score",start,end);
				String utc_status= xp.getValueOf("utcStatus",start,end);
				StringBuffer columnList = new StringBuffer("RULEEXECUTIONDATE,DUPLICATE_RISK ,FINANCIAL_RISK ,RULE_STEP ,UTC_REF_NO,SCORE,UTC_STATUS");

				//UPLODED_ON ,	'"+ uploadedOn  +"', 
				String valuesList ="'"+ruleExecutionDate +"','"+duplicateRisk +"','"+financialRisk  +"','"+ruleStep  +"','"+UTCRefNo  +"'"
						+ ",'"+score  +"','"+utc_status  +"'";
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "valuesList "+valuesList);

				APCallCreateXML.APUpdate("TFO_DJ_UTC_INVOICE_DETAIL",columnList.toString(),valuesList," WI_NAME = N'"+ WI_NAME +"'"
						+ " AND INVOICE_NUMBER = N'"+ documentNo +"'", sessionId);
				insertIntoUTCRefresh(documentNo);
				
			}
			APCallCreateXML.APUpdate("EXT_TFO", "IS_GETDOCUMENT_UTC_DONE", "'Y'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG101", reason, sessionId);
			
//			String outputXML1= "";
//			XMLParser xp1;
//			outputXML1 = APCallCreateXML.APSelect("SELECT curr_ws from ext_tfo WHERE WI_NAME =  N'" + WI_NAME + "'");
//			xp1 = new XMLParser(outputXML1);
//			int mainCode1 = Integer.parseInt(xp1.getValueOf("MainCode"));
//			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
//			if(mainCode1 == 0){
//				if(Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0){		
//					curr_ws = xp1.getValueOf("curr_ws");
//				}
//				}
//			if (getCurrWs().equalsIgnoreCase("UTC_REFERRAL")){
//				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument inside UTC_REFERRAL ===> ");
//				ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME,2);
//			}
		} else {
			status = "Failure";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument updateCallOutput callStaue = N ===> ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument else ===> ");
			APCallCreateXML.APUpdate("EXT_TFO", "IS_GETDOCUMENT_UTC_DONE", "'N'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
			ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME,1);
			String tableName ="BPM_EVENTGEN_TXN_DATA";
			String sColumn ="insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
					+ "SOURCING_CHANNEL, REQUESTMODE";
			String sValues = "SYSDATE,'"+WI_NAME+"',(SYSDATE+("+utcExpiryTime+"/(24*60))),'N','TFO','UTC','C'";
			
			String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);

		}
		String tableName1 ="TFO_DJ_DEC_HIST";
		String sColumn1 ="WI_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS";
		String sValues1 = "'"+WI_NAME+"','Procssing Maker',sysdate,'TFO User','UTCGetDocument call Executed',"+
				"'',sysdate,'"+  status + " : BATCH NO : "+batchNo+"'";
		
		String outputXML2 = APCallCreateXML.APInsert(tableName1, sColumn1, sValues1, sessionId);
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String outputStrin() throws Exception {
		String output = "<usecaseID>123123</usecaseID><serviceName>ModInvoiceValidation</serviceName><versionNo>1.0</versionNo><serviceAction>Modify</serviceAction><correlationID>10038</correlationID><sysRefNumber>10038</sysRefNumber><senderID>WMS</senderID><consumer>APIGateway</consumer><reqTimeStamp>28/04/2020 09:31:00</reqTimeStamp><repTimeStamp>29/08/2022 09:16:55</repTimeStamp><username/><credentials/><returnCode>0</returnCode><errorDescription>Success</errorDescription><errorDetail>ModInvoiceValidation-0-Success</errorDetail><getDocumentDtlsRes><messageStatus>OK</messageStatus><errorDescription>Success</errorDescription><utcMessageId>c833e450-2759-11ed-a818-0741463ac8d2</utcMessageId><documentDetails>   <batchNo>TF-00000100006-REQUEST</batchNo>   <documentNo>INVoice24082022_001</documentNo>   <utcRefNo>1798b4e0-242f-11ed-9c0c-ffe68eb03621</utcRefNo>   <documentData>      <key>1798b4e0-242f-11ed-9c0c-ffe68eb03621</key>      <documentName>document</documentName>      <documentNo>INVoice24082022_001</documentNo>      <utcRefNo>1798b4e0-242f-11ed-9c0c-ffe68eb03621</utcRefNo>      <batchNo>TF-00000100006-REQUEST</batchNo>      <documentType>INVOICE</documentType>      <documentSubType>INVOICE</documentSubType>      <override>N</override>      <ruleSetId/>      <score>2</score>      <status>UNDERPROCESS</status>      <processingStatus>RULEPROCESSCOMPLETED</processingStatus>      <uploadedBy>adcb.api</uploadedBy>      <uploadOn>1661402019</uploadOn>      <progress>100</progress>      <utcStatus>S</utcStatus>      <financialRisk>2</financialRisk>      <duplicate>0</duplicate>      <uploadType>API</uploadType>      <bankRefNo>         <bankRefNumbers>null</bankRefNumbers>      </bankRefNo>      <type>ELECTRONIC</type>      <bankCode>ADCB</bankCode>      <ruleExecutionDate>25/08/2022 08:33:57.564</ruleExecutionDate>      <customerDtls>         <name>ADCBCUSTone</name>         <taxNo>100029485858909123</taxNo>         <buyerOrSupplier>SUPPLIER</buyerOrSupplier>         <tradeLicenseNo>TRN199938485</tradeLicenseNo>      </customerDtls>      <documentDtls>         <documentName>documentruleresult</documentName>         <key>1798b4e0-242f-11ed-9c0c-ffe68eb03621_fa404ed0-630b-11ea-994e-09776c7e6796</key>         <ruleId>fa404ed0-630b-11ea-994e-09776c7e6796</ruleId>         <step>Exact Duplicate</step>         <ruleType>fieldsMatch</ruleType>         <ruleCategory>DUPLICATE</ruleCategory>         <status>S</status>         <isCounterParty>false</isCounterParty>         <utcRefNo>1798b4e0-242f-11ed-9c0c-ffe68eb03621</utcRefNo>         <outputType/>         <availableScore>0</availableScore>      </documentDtls>   </documentData></documentDetails><documentDetails>   <batchNo>TF-00000100006-REQUEST</batchNo>   <documentNo>INVoice24082022_002</documentNo>   <utcRefNo>1798b4e1-242f-11ed-9c0c-ffe68eb03621</utcRefNo>   <documentData>      <key>1798b4e1-242f-11ed-9c0c-ffe68eb03621</key>      <documentName>document</documentName>      <documentNo>INVoice24082022_002</documentNo>      <utcRefNo>1798b4e1-242f-11ed-9c0c-ffe68eb03621</utcRefNo>      <batchNo>TF-00000100006-REQUEST</batchNo>      <documentType>INVOICE</documentType>      <documentSubType>INVOICE</documentSubType>      <override>N</override>      <ruleSetId/>      <score>2</score>      <status>UNDERPROCESS</status>      <processingStatus>RULEPROCESSCOMPLETED</processingStatus>      <uploadedBy>adcb.api</uploadedBy>      <uploadOn>1661402019</uploadOn>      <progress>100</progress>      <utcStatus>S</utcStatus>      <financialRisk>2</financialRisk>      <duplicate>0</duplicate>      <uploadType>API</uploadType>      <bankRefNo>         <bankRefNumbers>null</bankRefNumbers>      </bankRefNo>      <type>ELECTRONIC</type>      <bankCode>ADCB</bankCode>      <ruleExecutionDate>25/08/2022 08:33:57.368</ruleExecutionDate>      <customerDtls>         <name>ADCBCUSTone</name>         <taxNo>100029485858909123</taxNo>         <buyerOrSupplier>SUPPLIER</buyerOrSupplier>         <tradeLicenseNo>TRN199938485</tradeLicenseNo>      </customerDtls>      <documentDtls>         <documentName>documentruleresult</documentName>         <key>1798b4e1-242f-11ed-9c0c-ffe68eb03621_fa404ed0-630b-11ea-994e-09776c7e6796</key>         <ruleId>fa404ed0-630b-11ea-994e-09776c7e6796</ruleId>         <step>Exact Duplicate</step>         <ruleType>fieldsMatch</ruleType>         <ruleCategory>DUPLICATE</ruleCategory>         <status>S</status>         <isCounterParty>false</isCounterParty>         <utcRefNo>1798b4e1-242f-11ed-9c0c-ffe68eb03621</utcRefNo>         <outputType/>         <availableScore>0</availableScore>      </documentDtls>   </documentData></documentDetails></getDocumentDtlsRes></getDocumentDtlsResMsg></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		return output;
		
	}
	
	public String getCallStatus() throws Exception {
		String status = ""; 
		try{
			String outputXML= "";
			XMLParser xp;

			outputXML = APCallCreateXML.APSelect("SELECT IS_GETDOCUMENT_UTC_DONE FROM EXT_TFO WHERE WI_NAME =  N'" + WI_NAME + "'");
			xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				int count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(count > 0){		
					status = xp.getValueOf("IS_GETDOCUMENT_UTC_DONE");
				}
			}
		} catch (Exception e){

		}
		return status;

	}
	
	public String getCurrWs() throws Exception {
		String currWs = ""; 
		try{
			String outputXML= "";
			XMLParser xp;

			outputXML = APCallCreateXML.APSelect("SELECT curr_ws from ext_tfo WHERE WI_NAME =  N'" + WI_NAME + "'");
			xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				int count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(count > 0){		
					currWs = xp.getValueOf("curr_ws");
				}
			}
		} catch (Exception e){

		}
		return currWs;

	}
	
	public void insertInIntegrationTable(){
		try{
			APCallCreateXML.APDelete("TFO_DJ_INTEGRATION_CALLS", "WI_NAME='"+WI_NAME+"' AND CALL_NAME = 'getDocumentDetailsUTC'", sessionId);
			String tableName ="TFO_DJ_INTEGRATION_CALLS";
			String sColumn ="SNO,WI_NAME,VESSEL_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION,"
					+ "ERROR_DETAIL,STATUS,ACTIONDATETIME,RETRY_COUNT,CALL_OPERATION";
			String sValues = "1,'"+WI_NAME+"','getDocumentDetailsUTC','getDocumentDetailsUTC',"
					+ "SYSTIMESTAMP,'"+callStatus+"','','','','"+status+"','',0,''";
			String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
			insertInTFODecistory();
		} catch(Exception e){
			
		}
	}
	
	public void insertIntoUTCRefresh(String invNo){
		try{
			String outputXML= "";
			String orderId = "";
			String wiName = "";
			String invoiceNo = "";
			String invDate = "";
			String invCurr = "";
			String invAmt = "";
			String supplierName = "";
			String buyerName = "";
			String batchNo = "";
			String refNo = "";
			String score = "";
			String utcStatus = "";
			String finRisk = "";
			String ruleStep = "";
			String duplicateRisk = "";
			String execDate = "";
			
			XMLParser xp;
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "insertIntoUTCRefresh inside:::: " );	
			outputXML = APCallCreateXML.APSelect("SELECT INSERTIONORDERID,WI_NAME,INVOICE_NUMBER,INVOICE_DATE,INVOICE_CURRENCY,INVOICE_AMOUNT,SUPPLIER_NAME,BUYER_NAME,BATCH_NO,"
					+ "UTC_REF_NO,SCORE,UTC_STATUS,FINANCIAL_RISK,RULE_STEP,DUPLICATE_RISK,RULEEXECUTIONDATE FROM tfo_dj_utc_invoice_detail WHERE WI_NAME=  N'" + WI_NAME + "' and INVOICE_NUMBER=  N'" + invNo + "' ");
			xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);	
			if(mainCode == 0){
				int count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(count > 0){		
					int end = 0;
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					for(int i = 0; i < count; i++){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);

						orderId = xp.getValueOf("INSERTIONORDERID", start, end);
						 wiName = xp.getValueOf("WI_NAME", start, end);
						 invoiceNo = xp.getValueOf("INVOICE_NUMBER", start, end);
						 invDate = xp.getValueOf("INVOICE_DATE", start, end);
						 invCurr = xp.getValueOf("INVOICE_CURRENCY", start, end);
						 invAmt = xp.getValueOf("INVOICE_AMOUNT", start, end);
						 supplierName = xp.getValueOf("SUPPLIER_NAME", start, end);
						 buyerName = xp.getValueOf("BUYER_NAME", start, end);
						 batchNo = xp.getValueOf("BATCH_NO", start, end);
						 refNo = xp.getValueOf("UTC_REF_NO", start, end);
						 score = xp.getValueOf("SCORE", start, end);
						 utcStatus = xp.getValueOf("UTC_STATUS", start, end);
						 finRisk = xp.getValueOf("FINANCIAL_RISK", start, end);
						 ruleStep = xp.getValueOf("RULE_STEP", start, end);
						 duplicateRisk = xp.getValueOf("DUPLICATE_RISK", start, end);
						 execDate = xp.getValueOf("RULEEXECUTIONDATE", start, end);
							 String tableName ="TFO_DJ_UTC_REFRESH_INVOICES";
								String sColumn ="insertionorderid,wi_name,INVOICE_NUMBER,INVOICE_DATE,INVOICE_CURRENCY,INVOICE_AMOUNT,SUPPLIER_NAME,BUYER_NAME,BATCH_NO,"
										+ "UTC_REF_NO,SCORE,UTC_STATUS,FINANCIAL_RISK,RULE_STEP,DUPLICATE_RISK,RULEEXECUTIONDATE";
								String sValues = "'"+orderId+"','"+wiName+"','"+invoiceNo+"',to_date('"+invDate+"','yyyy-MM-dd hh24:mi:ss'),'"+invCurr+"','"+invAmt+"','"+supplierName+"','"+buyerName+"',"
										+ "'"+batchNo+"','"+refNo+"','"+score+"','"+utcStatus+"','"+finRisk+"','"+ruleStep+"','"+duplicateRisk+"','"+execDate+"'";
								String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
				}
			}
			}
		} catch(Exception e){
			
		}
	}
	public void insertInTFODecistory(){
		try{
			String tableName ="TFO_DJ_DEC_HIST";
			String sColumn ="WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,"
					+ "CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,"
					+ "TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG";
			String sValues = "'"+WI_NAME+"','BpmUser','PROCESSING MAKER','PROCESSING MAKER',"
					+ "sysdate,'','','getDocumentDetailsUTC','" + errorDescription + "',"
				+ "sysdate,'getDocumentDetailsUTC Call  " + status + "','','','',''";
			String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
		} catch(Exception e){
			
		}
	}


	@Override
	public void responseHandler(String outputXML, String inputXml)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateCallOutput(String inputXml) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
			