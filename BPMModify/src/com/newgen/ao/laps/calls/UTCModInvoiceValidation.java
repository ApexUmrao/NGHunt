package com.newgen.ao.laps.calls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyDBLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ConnectSocket;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.omni.jts.cmgr.XMLParser;

public class UTCModInvoiceValidation implements ICallExecutor, Callable<String> {

	private String WI_NAME;
	private String sessionId;

	private String auditCallName = "Submit Invoice Validation";
	boolean isCallExecuted;
	private int StageID;
	private Boolean prevStageNoGo;
	private Map<String, String> attributeMap;
	private CallEntity callEntity;

	public UTCModInvoiceValidation(Map<String, String> attributeMap, String sessionId, String stageId, String WI_NAME, Boolean prevStageNoGo, CallEntity callEntity) {

		this.WI_NAME = WI_NAME;
		this.StageID = Integer.parseInt(stageId);
		this.sessionId = sessionId;
		this.prevStageNoGo = prevStageNoGo;
		this.attributeMap = attributeMap;
		this.callEntity = callEntity;

		String outputXML= "";
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCModInvoiceValidation CONSTRUCTOR");
		} catch(Exception e){

		}

	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"INSIDE UTCModInvoiceValidation CALL");
		
		processUTC();
		
		return "Success";
	}

	@Override
	public String createInputXML() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeDependencyCall() throws Exception {
		// TODO Auto-generated method stub

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

	private void processUTC() {

		String sQuery = "";
		String sOutput = "";
		String sOutput1 = "";

		try {
			sQuery = "";
			sOutput = "";
			String sErrorDescription = "";
			String sResponse = "";

			String callXML = "";
			
			String REFNO = "";
			XMLParser xp = null;
			XMLParser xp4 = null;
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat dateFormat2 = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String sDate = dateFormat.format(d);
			String requestCategory = "";
			String custName = "";
			String BorS = "";

			

			sQuery = "select CUSTOMER_NAME,REQUEST_CATEGORY from ext_tfo where wi_name = '"
					+ WI_NAME + "'";
			sOutput=APCallCreateXML.APSelect(sQuery);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"data output XML:" + sOutput);
			xp = new XMLParser(sOutput);
					requestCategory = xp.getValueOf("REQUEST_CATEGORY");
					custName = xp.getValueOf("CUSTOMER_NAME");
			
			if (requestCategory.equalsIgnoreCase("IFP")
					|| requestCategory.equalsIgnoreCase("IFA"))
				BorS = "BUYER";
			else
				BorS = "SUPPLIER";

			sQuery = "select INVOICE_NUMBER,INVOICE_DATE,INVOICE_CURRENCY,INVOICE_AMOUNT,SUPPLIER_NAME,BUYER_NAME,UTC_REF_NO from TFO_DJ_UTC_INVOICE_DETAIL where wi_name= '"
					+ WI_NAME + "'";
			sOutput=APCallCreateXML.APSelect(sQuery);
			
			// callXML =
			xp = new XMLParser(sOutput);
			boolean batchEnabled=true;
			int noOfInvoice = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
			int counter=Integer.parseInt(LapsConfigurations.getInstance().customUTCCount);
			int batchCount=1;
			String batchNo=WI_NAME;
			if(noOfInvoice<=counter){
				counter=noOfInvoice;
				batchEnabled=false;
			}
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"counter:" + counter);
			
			for(int i=1;i<=noOfInvoice;i=i+counter){
				
			if(batchEnabled)
			batchNo=WI_NAME+"-"+batchCount;
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"batchNo:" + batchNo);
			
			sOutput1 = APCallCreateXML
					.APSelect("SELECT SEQ_WEBSERVICE.NEXTVAL REFNO FROM DUAL");
			xp4=new XMLParser(sOutput1);
					REFNO = xp4.getValueOf("REFNO");
			
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
							"REFNO:" + REFNO);
					
				StringBuilder inputXml = new StringBuilder();
				
				inputXml.append("<APWebService_Input>")
					.append("\n")
					.append("<Option>WebService</Option>")
					.append("\n")
					.append("<Calltype>WS-2.0</Calltype>")
					.append("\n")
					.append("<InnerCallType>SubmitInvoiceValidation</InnerCallType>")
					.append("\n")
					.append("<submitInvoiceDtlsReqMsg>")
					.append("\n")
					.append("<usecaseID></usecaseID>")
					.append("\n")
					.append("<serviceName>ModInvoiceValidation</serviceName>")
					.append("\n")
					.append("<versionNo>1.0</versionNo>")
					.append("\n")
					.append("<serviceAction>Modify</serviceAction>")
					.append("\n")
					.append("<correlationID>" + REFNO + "</correlationID>")
					.append("\n")
					.append("<sysRefNumber>" + REFNO + "</sysRefNumber>")
					.append("\n")
					.append("<WiName>" + batchNo + "</WiName>")
					.append("<senderID>WMS</senderID>")
					.append("\n")
					.append("<consumer>ESB</consumer>")
					.append("\n")
					.append("<reqTimeStamp>" + sDate + "</reqTimeStamp>")
					.append("\n")
					.append("<repTimeStamp>" + sDate + "</repTimeStamp>")
					.append("\n")
					.append("<username></username>")
					.append("\n")
					.append("<credentials></credentials>")
					.append("\n")
					.append("<returnCode></returnCode>")
					.append("\n")
					.append("<errorDescription></errorDescription>")
					.append("\n")
					.append("<errorDetail></errorDetail>")
					.append("\n")
					.append("<submitInvoiceDtlsReq>")
					.append("\n")
					.append("<batchNo>" + batchNo + "</batchNo>")
					.append("<documentCount>" + counter
							+ "</documentCount>").append("\n")
					.append("<documentType>INVOICE</documentType>")
					.append("\n")
					.append("<documentSubType>INVOICE</documentSubType>")
					.append("\n").append("<override>N</override>").append("\n")
					.append("<uploadType>API</uploadType>").append("\n")
					.append("<bankRefNo>").append("\n")
					.append("<bankRefNumbers></bankRefNumbers>").append("\n")
					.append("</bankRefNo>").append("\n")
					.append("<customerDtls>").append("\n")
					.append("<customerName>" + custName + "</customerName>")
					.append("\n").append("<taxNo></taxNo>")
					.append("<buyerOrSupplier>" + BorS + "</buyerOrSupplier>")
					.append("\n").append("<tradeLicenseNo></tradeLicenseNo>")
					.append("\n").append("</customerDtls>").append("\n")
					.append("<electronicDatas>").append("\n");
			for (int k = 0; k < counter; k++) {
				String nextValue = xp.getNextValueOf("Record");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"nextValue:: " + nextValue);
				XMLParser xp1 = new XMLParser(nextValue);
				String utcRef = xp1.getValueOf("UTC_REF_NO");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"utc REFERENCE:: " + utcRef);
				String documentNo = xp1.getValueOf("INVOICE_NUMBER");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"documentNo:: " + documentNo);
				String documentDate = xp1.getValueOf("INVOICE_DATE");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"documentDate:: " + documentDate);
				String currency = xp1.getValueOf("INVOICE_CURRENCY");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"currency:: " + currency);
				String totalInvoiceAmount = xp1.getValueOf("INVOICE_AMOUNT");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"totalInvoiceAmount:: " + totalInvoiceAmount);
				String buyerName = xp1.getValueOf("BUYER_NAME");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"buyerName:: " + buyerName);
				String supplierName = xp1.getValueOf("SUPPLIER_NAME");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"supplierName:: " + supplierName);

				Date d2 = dateFormat2.parse(documentDate);
				documentDate = dateFormat1.format(d2);

				if ("".equalsIgnoreCase(utcRef)) {
					inputXml.append("<electronicData>")
							.append("\n")
							.append("<documentNo>" + documentNo
									+ "</documentNo>")
							.append("\n")
							.append("<documentDate>" + documentDate
									+ "</documentDate>")
							.append("\n")
							.append("<currency>" + currency + "</currency>")
							.append("\n")
							.append("<totalInvoiceAmount>"
									+ totalInvoiceAmount.replace(",", "")
									+ "</totalInvoiceAmount>").append("\n")
							.append("<contractNo><contractNo>").append("\n")
							.append("<poNumber></poNumber>").append("\n")
							.append("<amountInWords></amountInWords>")
							.append("\n")
							.append("<paymentDueDate></paymentDueDate>")
							.append("\n")
							.append("<termsOfPayment></termsOfPayment>")
							.append("\n")
							.append("<billingAddress></billingAddress>")
							.append("\n").append("<discount>0</discount>")
							.append("\n").append("<taxAmount>0</taxAmount>")
							.append("\n")
							.append("<taxNoSupplier></taxNoSupplier>")
							.append("\n")
							.append("<grossAmount>0</grossAmount>")
							.append("\n").append("<supplierDtls>").append("\n");
					if (supplierName.length() > 0) {
						if (supplierName.contains("-")
								&& ("IFP".equalsIgnoreCase(requestCategory) || "IFA"
										.equalsIgnoreCase(requestCategory))) {
							supplierName = supplierName.split("-")[1];

						}
						LapsModifyLogger.logMe(
								LapsModifyLogger.LOG_LEVEL_INFO,
								"listView,i,4 in supplier if : "
										+ supplierName.length());
						inputXml.append(
								"<SupplierName>" + supplierName
										+ "</SupplierName>").append("\n");
					} else {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
								"listView,i,4 in supplier else");
						inputXml.append("<SupplierName></SupplierName>")
								.append("\n");
					}
					inputXml.append("<SupplierAccountNo></SupplierAccountNo>")
							.append("<SupplierEmailAddress></SupplierEmailAddress>")
							.append("\n")
							.append("<SupplierWebsite></SupplierWebsite>")
							.append("\n")
							.append("<SupplierTelephone></SupplierTelephone>")
							.append("\n").append("<SupplierTrn></SupplierTrn>")
							.append("\n").append("<addressDtls>").append("\n")
							.append("<SupplierLine1></SupplierLine1>")
							.append("\n")
							.append("<SupplierLine2></SupplierLine2>")
							.append("\n")
							.append("<SupplierCity></SupplierCity>")
							.append("\n")
							.append("<SupplierCountry></SupplierCountry>")
							.append("\n")
							.append("<SupplierPoBox></SupplierPoBox>")
							.append("</addressDtls>").append("\n")
							.append("</supplierDtls>").append("\n")
							.append("<buyerDtls>").append("\n");
					if (buyerName.length() > 0) {
						if (buyerName.contains("-")
								&& ("IFS".equalsIgnoreCase(requestCategory))) {
							buyerName = buyerName.split("-")[1];

						}
						LapsModifyLogger.logMe(
								LapsModifyLogger.LOG_LEVEL_INFO,
								"listView,i,5 in buyer if : "
										+ buyerName.length());
						inputXml.append(
								"<BuyerName>" + buyerName + "</BuyerName>")
								.append("\n");
					} else {
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
								"listView,i,4 in buyer else");
						inputXml.append("<BuyerName></BuyerName>").append("\n");
					}
					inputXml.append("<BuyerEmailAddress></BuyerEmailAddress>")
							.append("\n")
							.append("<BuyerWebsite></BuyerWebsite>")
							.append("\n")
							.append("<BuyerTelephone></BuyerTelephone>")
							.append("\n")
							.append("<BuyerTrn></BuyerTrn>")
							.append("\n")
							.append("<addressDtls>")
							.append("\n")
							.append("<BuyerLine1></BuyerLine1>")
							.append("\n")
							.append("<BuyerLine2></BuyerLine2>")
							.append("\n")
							.append("<BuyerCity></BuyerCity>")
							.append("\n")
							.append("<BuyerCountry></BuyerCountry>")
							.append("\n")
							.append("<BuyerPoBox></BuyerPoBox>")
							.append("</addressDtls>")
							.append("\n")
							.append("</buyerDtls>")
							.append("\n")
							.append("<productLines>")
							.append("\n")
							.append("<hsCode></hsCode>")
							.append("\n")
							.append("<lineItemDescription></lineItemDescription>")
							.append("\n").append("<unitPrice>0</unitPrice>")
							.append("\n")
							.append("<subTotalAmount>0</subTotalAmount>")
							.append("\n").append("<quantity>0</quantity>")
							.append("\n").append("<lineNo></lineNo>")
							.append("\n").append("<uom></uom>").append("\n")
							.append("</productLines>").append("\n")
							.append("</electronicData>").append("\n");
				}
			}
			inputXml.append("</electronicDatas>").append("\n")
					.append("</submitInvoiceDtlsReq>").append("\n")
					.append("</submitInvoiceDtlsReqMsg>").append("\n")
					.append("</APWebService_Input>");

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"input xml of submitInvoiceDetailInputXML Finalllll = "
							+ inputXml.toString());
			callXML = inputXml.toString();

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"submitInvoiceDetailInputXML input socket: " + callXML);

			sOutput = ConnectSocket.connectToSocket(callXML);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"submitInvoiceDetailInputXML callXML sOutput socket" + sOutput);

			XMLParser xp3 = new XMLParser(sOutput);

			String smessageStatus = xp3.getValueOf("messageStatus");
			String sReturnCode = xp3.getValueOf("returnCode");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"submitInvoiceDetail smessageStatus	" + smessageStatus);

		 if (sOutput.equals("")
					|| smessageStatus.equalsIgnoreCase("ERROR")
					|| sReturnCode.equalsIgnoreCase("1")) {
				sResponse = "ERROR";
				sErrorDescription = "Web Service Error";

				sOutput = APCallCreateXML.APInsert(
						"TFO_DJ_DEC_HIST",
						"WI_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS",
						"'" + WI_NAME + "','PP MAKER',sysdate,'TFO User','"
								+ auditCallName + "','',sysdate,'" + sResponse
								+ " : BATCH NO : "+batchNo+"'",  sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Decision output insert query TFO_DJ_DEC_HIST: "
								+ sOutput);

			} else if (smessageStatus.equalsIgnoreCase("OK")) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"submitInvoiceDetail inside smessageStatus	"
								+ smessageStatus);

				sOutput = APCallCreateXML.APUpdate("EXT_TFO",
						"hidden_start_flag", "'true'", "WI_NAME = '" + WI_NAME
								+ "'",  sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"update integration external query hidden_start_flag: "
								+ sOutput);

				sOutput = APCallCreateXML.APUpdate("EXT_TFO",
						"IS_GETDOCUMENT_UTC_DONE", "'N'", "WI_NAME = '"
								+ WI_NAME + "'",  sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"update integration external query IS_GETDOCUMENT_UTC_DONE: "
								+ sOutput);

				sResponse = "Success";

				int end = 0;
				int start = xp3.getStartIndex("submitInvoiceDtlsRes", 0, 0);
				int deadEnd = xp3.getEndIndex("submitInvoiceDtlsRes", start, 0);
				int count = xp3.getNoOfFields("utcRefList", start, deadEnd);

				for (int l = 0; l < count; l++) {
					start = xp3.getStartIndex("utcRefList", end, 0);
					end = xp3.getEndIndex("utcRefList", start, 0);
					String responseDocumentNo = xp3.getValueOf("documentNo",
							start, end);
					String sUtcRefNo = xp3.getValueOf("utcRefNo", start, end);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
							"submitInvoiceDetail sUtcRefNo	" + sUtcRefNo);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
							"submitInvoiceDetail responseDocumentNo	"
									+ responseDocumentNo);

					sOutput = APCallCreateXML.APUpdate("TFO_DJ_UTC_INVOICE_DETAIL", "UTC_REF_NO", "'"
									+ sUtcRefNo + "'", "WI_NAME = '" + WI_NAME
									+ "' AND INVOICE_NUMBER='"
									+ responseDocumentNo + "'", 
							sessionId);
					
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
							"update integration external query IS_GETDOCUMENT_UTC_DONE: "
									+ sOutput);
					
					sOutput = APCallCreateXML.APUpdate("TFO_DJ_UTC_INVOICE_DETAIL", "BATCH_NO", "'"
							+ batchNo + "'", "WI_NAME = '" + WI_NAME
							+ "' AND INVOICE_NUMBER='"
							+ responseDocumentNo + "'", 
					sessionId);
					
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
							"update integration external query IS_GETDOCUMENT_UTC_DONE: "
									+ sOutput);

				}
				sOutput = APCallCreateXML.APInsert(
						"TFO_DJ_DEC_HIST",
						"WI_NAME,CURR_WS_NAME,CREATE_DATE,USERNAME,ACTION,REASON_FOR_ACTION,TRANSACTION_DATE_TIME,REMARKS",
						"'" + WI_NAME + "','PP MAKER',sysdate,'TFO User','"
								+ auditCallName + "','',sysdate,'" + sResponse
								+ " : BATCH NO : "+batchNo+"'",  sessionId);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Decision output insert query TFO_DJ_DEC_HIST: "
								+ sOutput);

			}
			
			batchCount++;
			}
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Success executeUTCBRMSCalls WI_NAME: " + WI_NAME);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Success executeUTCBRMSCalls auditCallName: " + auditCallName);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Success executeUTCBRMSCalls sResponse: " + sResponse);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Success executeUTCBRMSCalls sErrorDescription: "
								+ sErrorDescription);
			
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			LapsModifyDBLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, WI_NAME, auditCallName, "UTCG100", e, sessionId);
		}
	}

}
