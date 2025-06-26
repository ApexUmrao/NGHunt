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


public  class UTCUpdateDocumentStatus implements ICallExecutor, Callable<String> {
	private String WI_NAME;
	private int StageID;
	private String sessionId;
	private String invoiceNumber;
	private String COMPLETE_FLAG;
	private String callStatus;
	private int returnCode;
	private String errorDescription;
	private String errorDetail;
	private String status;
	private String reason;
	private String refNo;
	private String utcRefNo ="";
	private String senderID;
	private boolean updateCheckFlag=true;
	private boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private HashMap<String, String> newAttributeMap ;
	private CallEntity callEntity;
	
	private String auditCallName = "UTCUpdateDocumentStatus";
	boolean isCallExecuted;
	private StringBuilder inputReqXml=new StringBuilder();

	
	


	public UTCUpdateDocumentStatus(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {

		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;

		String outputXML= "";
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCUpdateDocumentStatus CONSTRUCTOR");
		} catch(Exception e){

		}

	}

			
@Override
public String call() throws Exception {
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCUpdateDocumentStatus CALL");
	
	StringBuffer outputXml = new StringBuffer();
	String finalOutputXml = "";
	try {
		if(getCurrWS().equalsIgnoreCase("PC Processing System") || getCurrWS().equalsIgnoreCase("PM Processing System")){

			String outputXML= "";
			XMLParser xp;

			outputXML = APCallCreateXML.APSelect("SELECT INVOICE_NUMBER,UTC_REF_NO FROM TFO_DJ_UTC_INVOICE_DETAIL WHERE WI_NAME =  N'" + WI_NAME + "'");
			xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				int count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(count > 0){		
					invoiceNumber = xp.getValueOf("INVOICE_NUMBER");
					utcRefNo = xp.getValueOf("UTC_REF_NO");
					updateCheckFlag = true;
					int end = 0;
					int start = xp.getStartIndex("Records", 0, 0);
					int deadEnd = xp.getEndIndex("Records", start, 0);
					for(int i = 0; i < count; i++){
						start = xp.getStartIndex("Record", end, 0);
						end = xp.getEndIndex("Record", start, 0);
						inputReqXml.append("<DocumentData>").append("\n")
						.append("<documentNo>"+xp.getValueOf("INVOICE_NUMBER", start, end)+"</documentNo>").append("\n")
						.append("<UTCRefNo>"+xp.getValueOf("UTC_REF_NO", start, end)+"</UTCRefNo>").append("\n")
						.append("<status>FINANCED</status>").append("\n")
						.append("<reason></reason>").append("\n")
						.append("</DocumentData>").append("\n");
					}
				}else{
					updateCheckFlag = false;
				}
			}
			String inputXml = createInputXML();
			if(updateCheckFlag){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCUpdateDocumentStatus updateCheckFlag");
				outputXml.append(LapsConfigurations.getInstance().socket.connectToSocket(inputXml));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml UTCUpdateDocumentStatus "+outputXml);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument outputXml ===> " + outputXml);
				XMLParser xmlResponse = new XMLParser(outputXml.toString());


				if(outputXml==null ||outputXml.toString().equalsIgnoreCase("")){
					outputXml.append("<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>");
				} 			
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml outputXml "+outputXml);

				responseHandler(outputXml,inputXml);
				LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, WI_NAME, auditCallName, "UTCG004", "EIDAMQ output:"+outputXml, sessionId);

			} else {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCUpdateDocumentStatus else");
				callStatus = "N";
				returnCode = 1;
				updateCallOutput(outputXml);
			}
		} else {
			outputXml.append("<returnCode>0</returnCode><errorDescription></errorDescription>");
		}
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
		inputXML.append("<APWebService_Input>"
				+ "<Option>WebService</Option>"
				+ "<Calltype>WS-2.0</Calltype>"
				+ "<InnerCallType>updateDocumentStatus</InnerCallType>"
				+ "<REF_NO>"+ refNo +"</REF_NO>"
				+ "<senderID>WMS</senderID>"
				+ "<WiName>"+ WI_NAME +"</WiName>"
				+ "<serviceName>ModInvoiceValidation</serviceName>"
				+ "<updateDocumentStatusReqMsg>"
				+ "<Documents>"+inputReqXml.toString()+"</Documents>"
				+ "</updateDocumentStatusReqMsg>"
				+ "<EngineName>" + LapsConfigurations.getInstance().CabinetName + "</EngineName>"
				+ "<SessionId>"+ sessionId +"</SessionId>"
				+ "</APWebService_Input>");
		
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
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument updateCallOutput callStaue = Y ===> ");
			XMLParser xp = new XMLParser(outputXML.toString());
			String messageStatus = xp.getValueOf("messageStatus");
			String UTCMessageID = xp.getValueOf("utcMessageId");
			status = "Success";
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG101", reason, sessionId);
		} else {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument updateCallOutput callStaue = N ===> ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "UTCGetDocument else ===> ");
			//APCallCreateXML.APUpdate("EXT_TFO", "IS_GETDOCUMENT_UTC_DONE", "'N'", " WI_NAME = N'"+ WI_NAME +"'", sessionId);
			//ProdCreateXML.WMCompleteWorkItem(sessionId, WI_NAME,1);
			String tableName ="BPM_EVENTGEN_TXN_DATA";
			String sColumn ="insertiondatetime, wi_name, expiry_date, status_flag,PROCESS_NAME, "
					+ "SOURCING_CHANNEL, REQUESTMODE";
			String sValues = "SYSDATE,'"+WI_NAME+"',(SYSDATE+(5/(24*60))),'N','TFO','UTC','C'";
			status = "Failure";
			String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"outputXml :insertTRSDXmlAudit " + outputXml);
		}
		insertInIntegrationTable();
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public String outputStrin() throws Exception {
		String output = "<usecaseID>123123</usecaseID><serviceName>ModInvoiceValidation</serviceName><versionNo>1.0</versionNo><serviceAction>Modify</serviceAction><correlationID>10038</correlationID><sysRefNumber>10038</sysRefNumber><senderID>WMS</senderID><consumer>APIGateway</consumer><reqTimeStamp>28/04/2020 09:31:00</reqTimeStamp><repTimeStamp>29/08/2022 09:16:55</repTimeStamp><username/><credentials/><returnCode>0</returnCode><errorDescription>Success</errorDescription><errorDetail>ModInvoiceValidation-0-Success</errorDetail><getDocumentDtlsRes><messageStatus>OK</messageStatus><errorDescription>Success</errorDescription><utcMessageId>c833e450-2759-11ed-a818-0741463ac8d2</utcMessageId><documentDetails>   <batchNo>TF-00000100006-REQUEST</batchNo>   <documentNo>INVoice24082022_001</documentNo>   <utcRefNo>1798b4e0-242f-11ed-9c0c-ffe68eb03621</utcRefNo>   <documentData>      <key>1798b4e0-242f-11ed-9c0c-ffe68eb03621</key>      <documentName>document</documentName>      <documentNo>INVoice24082022_001</documentNo>      <utcRefNo>1798b4e0-242f-11ed-9c0c-ffe68eb03621</utcRefNo>      <batchNo>TF-00000100006-REQUEST</batchNo>      <documentType>INVOICE</documentType>      <documentSubType>INVOICE</documentSubType>      <override>N</override>      <ruleSetId/>      <score>2</score>      <status>UNDERPROCESS</status>      <processingStatus>RULEPROCESSCOMPLETED</processingStatus>      <uploadedBy>adcb.api</uploadedBy>      <uploadOn>1661402019</uploadOn>      <progress>100</progress>      <utcStatus>S</utcStatus>      <financialRisk>2</financialRisk>      <duplicate>0</duplicate>      <uploadType>API</uploadType>      <bankRefNo>         <bankRefNumbers>null</bankRefNumbers>      </bankRefNo>      <type>ELECTRONIC</type>      <bankCode>ADCB</bankCode>      <ruleExecutionDate>25/08/2022 08:33:57.564</ruleExecutionDate>      <customerDtls>         <name>ADCBCUSTone</name>         <taxNo>100029485858909123</taxNo>         <buyerOrSupplier>SUPPLIER</buyerOrSupplier>         <tradeLicenseNo>TRN199938485</tradeLicenseNo>      </customerDtls>      <documentDtls>         <documentName>documentruleresult</documentName>         <key>1798b4e0-242f-11ed-9c0c-ffe68eb03621_fa404ed0-630b-11ea-994e-09776c7e6796</key>         <ruleId>fa404ed0-630b-11ea-994e-09776c7e6796</ruleId>         <step>Exact Duplicate</step>         <ruleType>fieldsMatch</ruleType>         <ruleCategory>DUPLICATE</ruleCategory>         <status>S</status>         <isCounterParty>false</isCounterParty>         <utcRefNo>1798b4e0-242f-11ed-9c0c-ffe68eb03621</utcRefNo>         <outputType/>         <availableScore>0</availableScore>      </documentDtls>   </documentData></documentDetails><documentDetails>   <batchNo>TF-00000100006-REQUEST</batchNo>   <documentNo>INVoice24082022_002</documentNo>   <utcRefNo>1798b4e1-242f-11ed-9c0c-ffe68eb03621</utcRefNo>   <documentData>      <key>1798b4e1-242f-11ed-9c0c-ffe68eb03621</key>      <documentName>document</documentName>      <documentNo>INVoice24082022_002</documentNo>      <utcRefNo>1798b4e1-242f-11ed-9c0c-ffe68eb03621</utcRefNo>      <batchNo>TF-00000100006-REQUEST</batchNo>      <documentType>INVOICE</documentType>      <documentSubType>INVOICE</documentSubType>      <override>N</override>      <ruleSetId/>      <score>2</score>      <status>UNDERPROCESS</status>      <processingStatus>RULEPROCESSCOMPLETED</processingStatus>      <uploadedBy>adcb.api</uploadedBy>      <uploadOn>1661402019</uploadOn>      <progress>100</progress>      <utcStatus>S</utcStatus>      <financialRisk>2</financialRisk>      <duplicate>0</duplicate>      <uploadType>API</uploadType>      <bankRefNo>         <bankRefNumbers>null</bankRefNumbers>      </bankRefNo>      <type>ELECTRONIC</type>      <bankCode>ADCB</bankCode>      <ruleExecutionDate>25/08/2022 08:33:57.368</ruleExecutionDate>      <customerDtls>         <name>ADCBCUSTone</name>         <taxNo>100029485858909123</taxNo>         <buyerOrSupplier>SUPPLIER</buyerOrSupplier>         <tradeLicenseNo>TRN199938485</tradeLicenseNo>      </customerDtls>      <documentDtls>         <documentName>documentruleresult</documentName>         <key>1798b4e1-242f-11ed-9c0c-ffe68eb03621_fa404ed0-630b-11ea-994e-09776c7e6796</key>         <ruleId>fa404ed0-630b-11ea-994e-09776c7e6796</ruleId>         <step>Exact Duplicate</step>         <ruleType>fieldsMatch</ruleType>         <ruleCategory>DUPLICATE</ruleCategory>         <status>S</status>         <isCounterParty>false</isCounterParty>         <utcRefNo>1798b4e1-242f-11ed-9c0c-ffe68eb03621</utcRefNo>         <outputType/>         <availableScore>0</availableScore>      </documentDtls>   </documentData></documentDetails></getDocumentDtlsRes></getDocumentDtlsResMsg></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		return output;
		
	}
	
	public String getCurrWS() throws Exception {
		String workStep = ""; 
		try{
			String outputXML= "";
			XMLParser xp;

			outputXML = APCallCreateXML.APSelect("SELECT ACTIVITYNAME FROM WFINSTRUMENTTABLE WHERE PROCESSINSTANCEID =  N'" + WI_NAME + "'");
			xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
			if(mainCode == 0){
				int count = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
				if(count > 0){		
					workStep = xp.getValueOf("ACTIVITYNAME");
				}
			}
		} catch (Exception e){

		}
		return workStep;

	}
	
	public void insertInIntegrationTable(){
		try{
			APCallCreateXML.APDelete("TFO_DJ_INTEGRATION_CALLS", "WI_NAME='"+WI_NAME+"' AND CALL_NAME = 'updateDocumentStatusUTC'", sessionId);
			String tableName ="TFO_DJ_INTEGRATION_CALLS";
			String sColumn ="SNO,WI_NAME,VESSEL_NAME,CALL_NAME,CALL_DT,CALL_STATUS,RETURN_CODE,ERROR_DESCRIPTION,"
					+ "ERROR_DETAIL,STATUS,ACTIONDATETIME,RETRY_COUNT,CALL_OPERATION";
			String sValues = "1,'"+WI_NAME+"','updateDocumentStatusUTC','updateDocumentStatusUTC',"
					+ "SYSTIMESTAMP,'"+callStatus+"','','','','"+status+"','',0,''";
			String outputXml = APCallCreateXML.APInsert(tableName, sColumn, sValues, sessionId);
			insertInTFODecistory();
		} catch(Exception e){
			
		}
	}
	
	public void insertInTFODecistory(){
		try{
			String tableName ="TFO_DJ_DEC_HIST";
			String sColumn ="WI_NAME,USER_ID,PRV_WS_NAME,CURR_WS_NAME,"
					+ "CREATE_DATE,USERNAME,USER_GROUPNAME,ACTION,REASON_FOR_ACTION,"
					+ "TRANSACTION_DATE_TIME,REMARKS,BRANCH,DUP_CHECK,ROUTE_TO,SEND_EMAIL_FLAG";
			String sValues = "'"+WI_NAME+"','BpmUser','PC Processing System','PC Processing System',sysdate,'','','updateDocumentStatusUTC','" + errorDescription + "',sysdate,'updateDocumentStatusUTC Call  " + status + "','','','',''";
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
			