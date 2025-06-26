package com.newgen.ao.laps.calls;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Arrays;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.core.ICallExecutor;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.ExecuteXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.omni.jts.cmgr.XMLParser;

public class PushMessageTSLM implements ICallExecutor, Callable<String> {
	private CallEntity callEntity;
	private String sessionId;
	private String winame;
	private String sUserName;
	private String engineName;
	private String stageId;
	Map<String, String> attributeMap = new HashMap<String, String>();
	private String refNo;
	private String callStatus;
	private String correlationNo = "";
	private int returnCode;
	private String errorDetail;
	private String errorDescription;	
	private String status;
	private String reason;
	private String responseXML;
	private String custSeg;
	private String cid ;
	private String loanProd="";
	private String disCid="";
	private String loanOldProd="";
	private String loanAmount="";
	private String loanCurrency ="";
	private String loanID="";
	private String newProdCode="";
	private String fundingReq="";
	private String addLoanAmount="";
	private String stdFlag;
	private String loanTenorBase;
	private String latestInvDate = "";
	private String oldInvDate = "";
	private String bookDate = "";
	private String latTransDate = "";
	private String oldTransDate = "";
	private String monEndDate = "";
	private String loanTenorDays;
	private String matuDate = "";
	private String txtAmount;
	private String txtCurr;
	private String accountNo;
	private String referralFlag;
	private String sysRefNo;
	private String sysTimeStamp;
	private String wiName;
	private String mode;
	private String principalFxRate ="";
	private String fromCur ="";
	private String toCur ="";
	private String interestFxRate ="";
	private String newMatuDate = "";
	private String amendEffecDate = "";
	
	public PushMessageTSLM(Map<String, String> attributeMap, String sessionId, String stageId, String wiNumber, 
			Boolean prevStageNoGo, CallEntity callEntity) {
		this.callEntity = callEntity;
		this.attributeMap = attributeMap;
		this.sessionId = sessionId;
		this.engineName=LapsConfigurations.getInstance().CabinetName;
		this.stageId=stageId;
		this.winame=wiNumber;
		this.correlationNo=attributeMap.get("correlationNo");
		this.mode=attributeMap.get("mode");
		sUserName = LapsConfigurations.getInstance().UserName;
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessagTSLM cunstructor called map===> " +callEntity.toString()+" Session id ===>"+sessionId);
	}

	@Override
	public String call() throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"PushMessageTSLM call: inside");
		String inputXml = "";
		callStatus="Y";
		try {			
			inputXml = createInputXML();
			responseXML = ExecuteXML.executeXML(inputXml);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessageTSLM outputXml ===> " + responseXML);
			if(responseXML==null || responseXML.equalsIgnoreCase("")){
				//responseXML= "<returnCode>1</returnCode><errorDescription>Web Service Error</errorDescription>";
				callStatus = "Y";
				status="Success";
				returnCode=1;
				errorDescription = "Web Service Error";
			}
			//responseHandler(responseXML, inputXml);//Commenting ATP - 346
			responseXML=responseXML+"<CallStatus>"+callStatus+"</CallStatus><CallResponse>"+errorDescription+""
					+ "</CallResponse><returnCode>"+returnCode+"</returnCode><errorDescription>"+errorDescription+""
					+ "</errorDescription><errorDetail></errorDetail><Reason></Reason><Status>"+status+"</Status>";
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "PushMessageTSLM outputXml ===> " + responseXML);
			updateCallOutput(responseXML); //ATP-346 by Shivanshu
			
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
			throw new Exception(e.getMessage());
		}
	   
		return responseXML;
	}

	@Override
	public String createInputXML() throws Exception {
		StringBuffer input = new StringBuffer();
		try{
		int mainCode = -1;
		String reqType="";
		String reqCat="";
		//SimpleDateFormat datFormat = new SimpleDateFormat("DD/MM/YYYY");
		Date date;
		String outputXml;
		
		outputXml = APCallCreateXML.APSelect("SELECT CUSTOMER_ID,PRODUCT_TYPE,LOAN_AMOUNT,INF_LOAN_CURR,TRANSACTION_REFERENCE,"
				+ "IF_PURCHASE_PRODUCT_CODE,REQUEST_TYPE,REQUEST_CATEGORY,"
					+ "FUNDING_REQUIRED,ADDITIONAL_LOAN_AMOUNT,STANDALONE_LOAN,INF_TENOR_BASE,"
					+ "to_char(to_date( trim(TRAILING '.' from (trim(.000 from INF_BASE_DOC_DATE))),'YYYY-MM-DD HH24:MI:SS'),'dd/mm/yyyy')INF_BASE_DOC_DATE"
					+ ",INF_TENOR_DAYS,"
					+ "to_char(to_date( trim(TRAILING '.' from (trim(.000 from INF_MATURITY_DATE))),'YYYY-MM-DD HH24:MI:SS'),'dd/mm/yyyy')INF_MATURITY_DATE"
					+ ",TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,INF_CHARGE_ACC_NUM,TSLM_REFERRAL_FLAG, "
					+ "TO_CHAR(SYSTIMESTAMP,'DD/MM/YYYY HH24:MI:SS')SYSTIMESTAMP, WI_NAME, "
					+ "PR_EXCHANGE_RATE,INT_EXCHANGE_RATE,TR_SELL_CUR,TR_BUY_CUR,"
					// ATP-454 REYAZ 03-05-2024 START
					+ "to_char(to_date(INF_NEW_MATURITY_DATE,'YYYY-MM-DD HH24:MI:SS'),'dd/mm/yyyy')INF_NEW_MATURITY_DATE,"
					+ "to_char(to_date( trim(TRAILING '.' from (trim(.000 from AMENDED_EFFECTIVE_DATE))),'YYYY-MM-DD HH24:MI:SS'),'dd/mm/yyyy')AMENDED_EFFECTIVE_DATE "
					// ATP-454 REYAZ 03-05-2024 END
					+ " FROM ext_tfo WHERE wi_name = '"+this.winame+"'");
		XMLParser xp = new XMLParser(outputXml);
		mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				reqType = xp.getValueOf("REQUEST_TYPE");
				reqCat = xp.getValueOf("REQUEST_CATEGORY");
				cid = xp.getValueOf("CUSTOMER_ID");
				if(reqType.equalsIgnoreCase("LD") || reqType.equalsIgnoreCase("PD") ){  //ADDED FOR SCF CHANGES 20-11-2023  ATP-246 reyaz
					disCid = xp.getValueOf("CUSTOMER_ID");
					loanAmount = xp.getValueOf("LOAN_AMOUNT");
					loanCurrency = xp.getValueOf("INF_LOAN_CURR");
					loanProd = xp.getValueOf("PRODUCT_TYPE");
				}
				if(!(reqType.equalsIgnoreCase("LD") || reqType.equalsIgnoreCase("STL")) || reqType.equalsIgnoreCase("PD")){   //ADDED FOR SCF CHANGES 20-11-2023 ATP-246,ATP-360 reyaz
					loanOldProd = xp.getValueOf("PRODUCT_TYPE");
					loanID = xp.getValueOf("TRANSACTION_REFERENCE");
					newProdCode = xp.getValueOf("IF_PURCHASE_PRODUCT_CODE");
					String sFundingReq = xp.getValueOf("FUNDING_REQUIRED");
					if(sFundingReq.equalsIgnoreCase("1")){
						fundingReq = "Y";
					}else if(sFundingReq.equalsIgnoreCase("2")){
						fundingReq = "N";
					}
					
				}else if(reqType.equalsIgnoreCase("IFA_CTP")){
					String oldLoan=xp.getValueOf("TRANSACTION_REFERENCE");
					loanOldProd=oldLoan.substring(3,4);
				}
				if(xp.getValueOf("STANDALONE_LOAN").equalsIgnoreCase("1")){
					stdFlag = "Y";
				}else if(xp.getValueOf("STANDALONE_LOAN").equalsIgnoreCase("2")){
					stdFlag = "N";
				}
				if(correlationNo == null){
					correlationNo = "";
				}
				String tenorQuery;
				loanTenorBase = xp.getValueOf("INF_TENOR_BASE");
				tenorQuery = APCallCreateXML.APSelect("select tenor_base_desc from TFO_DJ_LN_TNR_MAST "
						+ "where tenor_base_key = '"+loanTenorBase+"'");
				XMLParser xptenor = new XMLParser(tenorQuery);
				loanTenorBase = xptenor.getValueOf("tenor_base_desc");
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "TenorBase Valye :: "+loanTenorBase );
				
				if(loanTenorBase.equalsIgnoreCase("From Latest Invoices Date")){
					loanTenorBase = "1";
				}else if(loanTenorBase.equalsIgnoreCase("From Oldest Invoices Date")){
					loanTenorBase = "2";
				}else if(loanTenorBase.equalsIgnoreCase("From Booking Date")){
					loanTenorBase = "3";
				}else if(loanTenorBase.equalsIgnoreCase("From Latest Transport Document Date")){
					loanTenorBase = "4";
				}else if(loanTenorBase.equalsIgnoreCase("From Oldest Transport Document Date")){
					loanTenorBase = "5";
				}else if(loanTenorBase.equalsIgnoreCase("From Month End Date")){
					loanTenorBase = "6";
				}else if(loanTenorBase.trim().equalsIgnoreCase("Proposed Loan Effective Date")){
					loanTenorBase="8";
				}else if(loanTenorBase.trim().equalsIgnoreCase("Oldest Invoice Acknowledgment Date")){
					loanTenorBase="9";
				}else if(loanTenorBase.trim().equalsIgnoreCase("Latest Invoice Acknowledgment Date")){
					loanTenorBase="10";
				}else if(loanTenorBase.equalsIgnoreCase("Others")){
					loanTenorBase = "7";
				}

				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "tenor value :: "+ xptenor.getValueOf("tenor_base_desc") );
				
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "CHECK 1" );
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("FLID")){
					latestInvDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("FOID")){
					oldInvDate =  xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("FBD")){
					bookDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("FLTDD")){
					latTransDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("FOTDD")){
					oldTransDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("FMED")){
					monEndDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("PLED")){
					bookDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("OIAD")){
					oldInvDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				if(xp.getValueOf("INF_TENOR_BASE").equalsIgnoreCase("LIAD")){
					latestInvDate = xp.getValueOf("INF_BASE_DOC_DATE");
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "CHECK 2" );
				
				
				 loanTenorDays = xp.getValueOf("INF_TENOR_DAYS");
				 matuDate = xp.getValueOf("INF_MATURITY_DATE");
				 
				 //ATP-454 REYAZ 03-05-2024
				 //START CODE
				 if(reqType.equalsIgnoreCase("AM")){ 
					newMatuDate = xp.getValueOf("INF_NEW_MATURITY_DATE");
					amendEffecDate = xp.getValueOf("AMENDED_EFFECTIVE_DATE");
				}
				 //END CODE
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "CHECK 12:: "+ matuDate);
				 
				 String curr_ws="";
				 String dec_code="";
				 String outputXML = APCallCreateXML.APSelect("select CURR_WS,DEC_CODE from EXT_TFO WHERE WI_NAME='"+this.winame+"'");
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXML :"+outputXML);
				 XMLParser xp5 = new XMLParser(outputXML);
				 curr_ws=xp5.getValueOf("CURR_WS");
				 dec_code=xp5.getValueOf("DEC_CODE");
				 
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "curr_ws :"+curr_ws);
				 
				 if(curr_ws.equalsIgnoreCase("PP_MAKER")&&loanAmount.trim().isEmpty()&&(reqType.equalsIgnoreCase("LD") || reqType.equalsIgnoreCase("PD"))){  //ADDED FOR SCF CHANGES 20-11-2023 ATP-246 reyaz
					 loanAmount="0.0";
				 }
				 
				 LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "loanAmount :"+loanAmount);
				 
				 if(fundingReq==null||fundingReq.isEmpty()||fundingReq.trim().equalsIgnoreCase("")){
						fundingReq = "N";
				 }
					
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "fundingReq :: "+ fundingReq );
				addLoanAmount = xp.getValueOf("ADDITIONAL_LOAN_AMOUNT");
				if(addLoanAmount==null||addLoanAmount.isEmpty()||addLoanAmount.trim().equalsIgnoreCase("")){
					addLoanAmount="0";
				}
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "addLoanAmount :: "+ addLoanAmount );
					
				 txtAmount = xp.getValueOf("TRANSACTION_AMOUNT").replace(",","");
				 txtCurr = xp.getValueOf("TRANSACTION_CURRENCY");
				 accountNo = xp.getValueOf("INF_CHARGE_ACC_NUM");
				 referralFlag = xp.getValueOf("TSLM_REFERRAL_FLAG");
				 
				 sysRefNo = LapsUtils.getInstance().generateSysRefNumber();
				 sysTimeStamp = xp.getValueOf("SYSTIMESTAMP");
				 wiName = xp.getValueOf("WI_NAME");
				 
				 //ATP-362 11-01-2024 REYAZ
				 //START CODE
				 if (reqType.equalsIgnoreCase("STL")) {
					 principalFxRate = xp.getValueOf("PR_EXCHANGE_RATE");
					 interestFxRate = xp.getValueOf("INT_EXCHANGE_RATE");
					 fromCur = xp.getValueOf("TR_SELL_CUR");
					 toCur = xp.getValueOf("TR_BUY_CUR");
				 }
				 //END CODE
				 
				if("R".equalsIgnoreCase(mode)){
					
					if(dec_code.trim().equalsIgnoreCase("REJ")){
						mode="D";
					}//Updated logic as discussed with Avinash Defect ID 101757 -- UPDATED REJECT DECISON FOR DEFECT 101562 26TH APR
					
						 input.append("<?xml version=\"1.0\"?>")
							.append("<APWebService_Input>")
							.append("<Option>WebService</Option>")
							.append("<Calltype>WS-2.0</Calltype>")
							.append("<InnerCallType>LapsResponse</InnerCallType>")
							.append("<SessionId>" + sessionId + "</SessionId>")
							.append("<EngineName>"+engineName+"</EngineName>")
							.append("<WI_NAME>"+ this.winame +"</WI_NAME>")  //ATP -358 DATE 2/01/2023 REYAZ
							.append("<PushMessage>")//.append("<ChannelResponse>")
							.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
							.append("<createWorkItemLinkageMsg>")
							.append("<header>")
							.append("<mode>"+mode+"</mode>")
							.append("<channelName>TSLM</channelName>")
							.append("<sysrefno>"+sysRefNo+"</sysrefno>")
							.append("<processName>TFO</processName>")
							.append("<requestDateTime>"+sysTimeStamp+"</requestDateTime>")	
							.append("<version>1.0</version>")
							.append("<correlationId>"+correlationNo+"</correlationId>")
							.append("</header>")
							.append("<createWorkItemLinkage>")
							.append("<wmsId>"+wiName+"</wmsId>")
							.append("<disbursement>")
							.append("<cid>"+disCid+"</cid>")
							.append("<loanProduct>"+loanProd+"</loanProduct>")
							.append("<loanAmt>"+loanAmount+"</loanAmt>")
							.append("<loanCcy>"+loanCurrency+"</loanCcy>")
						    .append("</disbursement>")
						    .append("<loanConversion>")
						    .append("<loanId>"+loanID+"</loanId>")
						    .append("<oldProductCode>"+loanOldProd+"</oldProductCode>")
						    .append("<newProductCode>"+newProdCode+"</newProductCode>")		
						    .append("<fundingRequiredFlag>"+fundingReq+"</fundingRequiredFlag>")
						    .append("<additionalLoanAmt>"+addLoanAmount+"</additionalLoanAmt>")	
						    .append("</loanConversion>")
						    .append("<extendedMaturityDate>"+newMatuDate+"</extendedMaturityDate>")  //ATP -454  REYAZ  03-05-2024
						    .append("<extendedEffectiveDate>"+amendEffecDate+"</extendedEffectiveDate>")  //ATP -454  REYAZ  03-05-2024
							.append("<limitLine>"+getLimitLine()+"</limitLine>")     // ADDED by Shivanshu ATP-346
							.append(getAdditionalStlTag()); //ATP -363 11-01-2024 REYAZ
								
						input.append(getStatusReferralBlock())
						.append("</createWorkItemLinkage>")
								.append("</createWorkItemLinkageMsg>")
								.append("</PushMessage>")
								.append("</APWebService_Input>");
				} else {
					 input.append("<?xml version=\"1.0\"?>")
						.append("<APWebService_Input>")
						.append("<Option>WebService</Option>")
						.append("<Calltype>WS-2.0</Calltype>")
						.append("<InnerCallType>LapsResponse</InnerCallType>")
						.append("<SessionId>" + sessionId + "</SessionId>")
						.append("<EngineName>"+engineName+"</EngineName>")
						.append("<WI_NAME>"+ this.winame +"</WI_NAME>")  //ATP -358 DATE 2/01/2023 REYAZ
						.append("<PushMessage>")//.append("<ChannelResponse>")
						.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
						.append("<createWorkItemLinkageMsg>")
						.append("<header>")
						.append("<mode>C</mode>")
						.append("<channelName>TSLM</channelName>")
						.append("<sysrefno>"+sysRefNo+"</sysrefno>")
						.append("<processName>TFO</processName>")
						.append("<requestDateTime>"+sysTimeStamp+"</requestDateTime>")	
						.append("<version>1.0</version>")
						.append("<correlationId>"+correlationNo+"</correlationId>")
						.append("</header>")
						.append("<createWorkItemLinkage>")
						.append("<wmsId>"+wiName+"</wmsId>")
						.append("<disbursement>")
						.append("<cid>"+disCid+"</cid>")
						.append("<loanProduct>"+loanProd+"</loanProduct>")
						.append("<loanAmt>"+loanAmount+"</loanAmt>")
						.append("<loanCcy>"+loanCurrency+"</loanCcy>")
					    .append("</disbursement>")
					    .append("<loanConversion>")
					    .append("<loanId>"+loanID+"</loanId>")
					    .append("<oldProductCode>"+loanOldProd+"</oldProductCode>")
					    .append("<newProductCode>"+newProdCode+"</newProductCode>")		
					    .append("<fundingRequiredFlag>"+fundingReq+"</fundingRequiredFlag>")
					    .append("<additionalLoanAmt>"+addLoanAmount+"</additionalLoanAmt>")	
					    .append("</loanConversion>")
					    .append("<stdFlag>"+stdFlag+"</stdFlag>")
					    .append("<loanTenorBase>"+loanTenorBase+"</loanTenorBase>")
					    .append("<latestInvoiceDate>"+latestInvDate+"</latestInvoiceDate>")
					    .append("<oldestInvoiceDate>"+oldInvDate+"</oldestInvoiceDate>")
					    .append("<bookingDate>"+bookDate+"</bookingDate>")
					    .append("<latestTransportDate>"+latTransDate+"</latestTransportDate>")
					    .append("<oldestTransportDate>"+oldTransDate+"</oldestTransportDate>")
					    .append("<monthEndDate>"+monEndDate+"</monthEndDate>")
						.append("<loanTenorDays>"+loanTenorDays+"</loanTenorDays>")
						.append("<maturityDate>"+matuDate+"</maturityDate>")
						.append("<trxAmt>"+txtAmount+"</trxAmt>")
						.append("<trxCcy>"+txtCurr+"</trxCcy>"+getCounterPartyList(stdFlag))
						.append("<accountList>")
						.append("<account>")
						.append("<cid>"+cid+"</cid>")
						.append("<itemType>PRINCIPAL</itemType>")
						.append("<crDr>D</crDr>")
						.append("<accountNo>"+accountNo+"</accountNo>")
						.append("</account>")
						.append("<account>")
						.append("<cid>"+cid+"</cid>")
						.append("<itemType>Interest</itemType>")
						.append("<crDr>D</crDr>")
					    .append("<accountNo>"+accountNo+"</accountNo>")
						.append("</account>")
						.append("</accountList>")
						.append(getInvoiceDetail());//Ameena
					if(referralFlag.equalsIgnoreCase("1")){
						input.append("<referralFlag>Y</referralFlag>");
					}
					if(referralFlag.equalsIgnoreCase("2")){
						input.append("<referralFlag>N</referralFlag>");
					}
					input.append(getInvoiceList(stdFlag))
							.append("</createWorkItemLinkage>")
							.append("</createWorkItemLinkageMsg>")//.append("</ChannelResponse>")
							.append("</PushMessage>")
							.append("</APWebService_Input>");
				 }
		   }
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, input.toString());
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
			return input.toString();
		}
	
	public String getCounterPartyList(String stdFlag ){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getCounterPartyList: ");
		StringBuffer output =  new StringBuffer();
		try{
			if(stdFlag.equalsIgnoreCase("N")){
				String outputXML = APCallCreateXML.APSelect("select cid from TFO_DJ_TSLM_COUNTER_PARTY_DETAILS where checkbox = 'true' and winame = '"+this.winame+"'");
				output.append("<cpcidList>");
				XMLParser xp = new XMLParser(outputXML);
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				int end = 0;
				for (int i = 0; i < noOfFields; ++i) {
					start = xp.getStartIndex("Record", end, 0);
					end = xp.getEndIndex("Record", start, 0);
					String cid = xp.getValueOf("cid", start, end);
					output.append( "<cpcid>"+cid+"</cpcid>");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "cid: " + cid);
				}
				output.append("</cpcidList>");
			}
		}catch(Exception e ){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getCounterPartyList: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output: " + output.toString());
		return output.toString();
	}
	
	public String getInvoiceList(String stdFlag ){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getInvoiceList: ");
	    StringBuffer output =  new StringBuffer();
	    StringBuffer invoiceNo = new StringBuffer();
	    try{
		if(stdFlag.equalsIgnoreCase("Y")){
			String outputXML = APCallCreateXML.APSelect("select invoiceno from TFO_DJ_TSLM_INVOICE_NO_STA_LOAN where winame = '"+this.winame+"'");
			
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			for (int i = 0; i < noOfFields; ++i) {
				start = xp.getStartIndex("Record", end, 0);
				end = xp.getEndIndex("Record", start, 0);
				String invoice = xp.getValueOf("invoiceno", start, end);
				invoiceNo.append(invoice+",");
			}
			if(invoiceNo.length()>0){
				invoiceNo.substring(0,invoiceNo.length()-1 );
			}
			output.append("<invoiceList>"+invoiceNo+"</invoiceList>");
		}
		
	}catch(Exception e ){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getInvoiceList: ");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
	}
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output: " + output.toString());
	return output.toString();
	}
	//Added by reyaz 4/8/2022
	public String getInvoiceDetail(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getInvoiceDetail: ");
	    StringBuffer output =  new StringBuffer();
	    StringBuffer invoiceNo = new StringBuffer();
	    try{
		
			String outputXML = APCallCreateXML.APSelect("Select INVOICE_NUMBER,to_char(to_date(INVOICE_DATE),'dd/MM/yyyy') AS INVOICE_DATE,INVOICE_CURRENCY,INVOICE_AMOUNT"
					+ " from TFO_DJ_UTC_INVOICE_DETAIL where wi_name = '"+this.winame+"'");
			
			XMLParser xp = new XMLParser(outputXML);
			int start = xp.getStartIndex("Records", 0, 0);
			int deadEnd = xp.getEndIndex("Records", start, 0);
			int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
			int end = 0;
			String originalMaturityDate = getMaturityDate();
//			String counterPartyID = getCounterPartyId();
			String custContAmt = getCustContribution();
			String limitLine = getLimitLine(); //Added by shivanshu ATP-346
			output.append("<invoiceDetailsList>");
			 	for (int i = 0; i < noOfFields; ++i) {
			 		start = xp.getStartIndex("Record", end, 0);
					end = xp.getEndIndex("Record", start, 0);
					String invoice = xp.getValueOf("INVOICE_NUMBER", start, end);
					String invoiceCCY = xp.getValueOf("INVOICE_CURRENCY", start, end);
					String invoiceAmount = xp.getValueOf("INVOICE_AMOUNT", start, end).replace(",", "");
					String invoiceDate = xp.getValueOf("INVOICE_DATE", start, end);
					
					output.append("<invoiceDetail>")
					      .append("<invoiceNumber>"+invoice+"</invoiceNumber>")
					      .append("<invoiceCCY>"+invoiceCCY+"</invoiceCCY>")
					      .append("<invoiceAmount>"+invoiceAmount+"</invoiceAmount>")
					      .append("<invoiceDate>"+invoiceDate+"</invoiceDate>")
					      .append("<originalMaturityDate>"+originalMaturityDate+"</originalMaturityDate>"+getCounterPartyId(invoice))
					      .append("</invoiceDetail>");
				}
		    output.append("</invoiceDetailsList>");
			output.append("<custContAmt>"+custContAmt+"</custContAmt>");
			output.append("<limitLine>"+limitLine+"</limitLine>"); //Added by shivanshu ATP-346 for limit line to pass in push message
		
		}catch(Exception e ){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getInvoiceDetail: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output: " + output.toString());
		return output.toString();
	}
			

	public String getMaturityDate(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getMaturityDate: ");
	  String inf_maturity_date="";
	  String newDate = "";
	    try{
		
			String outputXML = APCallCreateXML.APSelect("SELECT INF_MATURITY_DATE FROM EXT_TFO where wi_name = '"+this.winame+"'");
			XMLParser xmlparser = new XMLParser(outputXML);
			int mainCode1 = Integer.parseInt(xmlparser.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
			if(mainCode1 == 0){
				if(Integer.parseInt(xmlparser.getValueOf("TotalRetrieved")) > 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
					inf_maturity_date = xmlparser.getValueOf("INF_MATURITY_DATE");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INF_MATURITY_DATE: " + inf_maturity_date);	
					String sDate="";
					Date d = new Date();
					if(inf_maturity_date.contains(" ")){
						sDate = inf_maturity_date.split(" ")[0];

					}else{
						sDate = inf_maturity_date;
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sDate: " + sDate);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
					d = sdf.parse(sDate);
					newDate = sdf1.format(d);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "newDate: " + newDate);
				}
			}
			}catch(Exception e ){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getMaturityDate: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
			}


		return newDate;
	}
	
	public String getCustContribution(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getCustContribution: ");
	  String custContAmt="";
	    try{
		
			String outputXML = APCallCreateXML.APSelect("select LT_RESPONSE from TFO_DJ_LMT_CRDT_RECORDS where LT_DOC_RVW_GDLINES='Please specify Client Contribution Amount' and wi_name=  '"+this.winame+"'");
			XMLParser xmlparser = new XMLParser(outputXML);
			int mainCode1 = Integer.parseInt(xmlparser.getValueOf("MainCode"));
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);			
			if(mainCode1 == 0){
				if(Integer.parseInt(xmlparser.getValueOf("TotalRetrieved")) > 0){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode1: " + mainCode1);		
					custContAmt = xmlparser.getValueOf("LT_RESPONSE");
					if(custContAmt.contains(",")){
						custContAmt = custContAmt.replace(",", "");
					}
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "custContAmt: " + custContAmt);	
				}
			}
			}catch(Exception e ){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getMaturityDate: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
			}


		return custContAmt;
	}
	public String getCounterPartyId(String invoiceNo){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getCounterPartyId: ");
		String supplierName="";
		String buyerName="";
		StringBuffer output =  new StringBuffer();
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "inside getCounterPartyId: ");
		try{

			String sQuery = APCallCreateXML.APSelect("SELECT REQUEST_CATEGORY,REQUEST_TYPE FROM EXT_TFO WHERE WI_NAME = '"+this.winame+"'");

			XMLParser xpParser = new XMLParser(sQuery);
			String reqCategory = xpParser.getValueOf("REQUEST_CATEGORY");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside RequestCategory:"+reqCategory );
			if(reqCategory.equalsIgnoreCase("IFP") || reqCategory.equalsIgnoreCase("IFA")){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside RequestCategory:"+reqCategory );
				String outputXML = APCallCreateXML.APSelect("SELECT SUPPLIER_NAME FROM TFO_DJ_UTC_INVOICE_DETAIL WHERE WI_NAME = '"+this.winame+"' AND INVOICE_NUMBER='"+invoiceNo+"'");

				XMLParser xp = new XMLParser(outputXML);
				int start = xp.getStartIndex("Records", 0, 0);
				int deadEnd = xp.getEndIndex("Records", start, 0);
				int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
				int end = 0;
				for (int i = 0; i < noOfFields; ++i) {
					start = xp.getStartIndex("Record", end, 0);
					end = xp.getEndIndex("Record", start, 0);
					supplierName = xp.getValueOf("SUPPLIER_NAME", start, end);
					String[] value = supplierName.split("-");
					output.append( "<counterPartyID>"+value[0].replaceAll("&", "&amp;")+"</counterPartyID>"); // ATP -420 REYAZ 04-03-2024
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "value[0]: " + value[0]);
				}
			}
			else if(reqCategory.equalsIgnoreCase("IFS")){
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Inside RequestCategory:"+reqCategory );
				String sOutput = APCallCreateXML.APSelect("SELECT BUYER_NAME FROM TFO_DJ_UTC_INVOICE_DETAIL WHERE WI_NAME = '"+this.winame+"' AND INVOICE_NUMBER='"+invoiceNo+"'");

				XMLParser xp1 = new XMLParser(sOutput);
				int start = xp1.getStartIndex("Records", 0, 0);
				int deadEnd = xp1.getEndIndex("Records", start, 0);
				int noOfFields = xp1.getNoOfFields("Record", start, deadEnd);
				int end = 0;
				for (int i = 0; i < noOfFields; ++i) {
					start = xp1.getStartIndex("Record", end, 0);
					end = xp1.getEndIndex("Record", start, 0);
					buyerName = xp1.getValueOf("BUYER_NAME", start, end);
					String[] sValue = buyerName.split("-");
					output.append( "<counterPartyID>"+sValue[0].replaceAll("&", "&amp;")+"</counterPartyID>");   // ATP -420 REYAZ 04-03-2024
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "sValue[0]: " + sValue[0]);
				}
			}
		}catch(Exception e ){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getMaturityDate: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
		}


		return output.toString();
	}
// Added by reyaz 02-03-2023
	public String getStatusReferralBlock(){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getStatusReferralBlock: ");
	    StringBuffer output =  new StringBuffer();
	    String seqNo = "";
		String transType = "";
		String transId = "";
		String refCode = "";
		String referralType = "";
		String refDesc = "";
		String userCmnt = "";
		String status = "";
		String cpCid = "";
		try{
		String outputXML = APCallCreateXML.APSelect("select REQUEST_CATEGORY,CUSTOMER_REFERENCE,CURR_WS,DEC_CODE,REMARKS,REJ_RESN_PPM from EXT_TFO WHERE WI_NAME='"+this.winame+"'"); //SCFMVP2.5 12-JUL-2024 ---JAMSHED
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXML :"+outputXML);
		XMLParser xp5 = new XMLParser(outputXML);
		String curr_ws=xp5.getValueOf("CURR_WS");
		String decCode=xp5.getValueOf("DEC_CODE");
		String remarks=xp5.getValueOf("REMARKS");
		String ReqCat=xp5.getValueOf("REQUEST_CATEGORY");
		String tslmID="REQUEST_CATEGORY";
		String wiTxnStatus="Success";
		//SCFMVP2.5 12-JUL-2024 ---JAMSHED START
		String rejReason=xp5.getValueOf("REJ_RESN_PPM");
		if(decCode.equalsIgnoreCase("REJ")) {
			rejReason  = rejReason.substring(1,rejReason.length()-1);
			rejReason = rejReason.replaceAll("\"", "");
			String[] reasonList = rejReason.split(",");
			if(!Arrays.asList(reasonList).contains("Others")) {
				remarks=rejReason;
			}else if(Arrays.asList(reasonList).contains("Others") && Arrays.asList(reasonList).size() > 1) {
				remarks=!remarks.equalsIgnoreCase("")?rejReason+","+remarks : rejReason;
			}
		}
		//SCFMVP2.5 12-JUL-2024 ---JAMSHED END
		
		outputXML=APCallCreateXML.APSelect("SELECT TSLM_REF_NUMBER FROM TFO_DJ_TSLM_SCF_TXN_DATA WHERE WI_NAME ='"+this.winame+"' and rownum=1");
		xp5 = new XMLParser(outputXML);
		tslmID=xp5.getValueOf("TSLM_REF_NUMBER");
		
		outputXML=APCallCreateXML.APSelect("SELECT REFFERD_TO FROM TFO_DJ_FINAL_DEC_SUMMARY where wi_name ='"+this.winame+"' and rownum=1");
		xp5 = new XMLParser(outputXML);
		String reffredTo=xp5.getValueOf("REFFERD_TO");
		
		outputXML=APCallCreateXML.APSelect("SELECT COUNT(0) as COUNT FROM TFO_DJ_FINAL_DEC_SUMMARY where wi_name ='"+this.winame+"' and REFFERD_TO='Customer'");
		xp5 = new XMLParser(outputXML);
		int customerReferalFlag=Integer.parseInt(xp5.getValueOf("COUNT"));
		
		outputXML=APCallCreateXML.APSelect("SELECT COUNT(0) as COUNT FROM TFO_DJ_FINAL_DEC_SUMMARY where wi_name ='"+this.winame+"' and REFFERD_TO='Customer Only Through Email'");
		xp5 = new XMLParser(outputXML);
		int customerEmailReferalFlag=Integer.parseInt(xp5.getValueOf("COUNT"));
		
		if(decCode.trim().equalsIgnoreCase("APP")){
			wiTxnStatus="Completed";
		}else if(decCode.trim().equalsIgnoreCase("REJ")){
			wiTxnStatus="Rejected";
		}
		
		if(curr_ws.trim().equalsIgnoreCase("PP_MAKER")&&decCode.equalsIgnoreCase("REF")&&customerReferalFlag>0){
			wiTxnStatus="Return to customer for modification";
			outputXML=APCallCreateXML.APSelect("SELECT EXCP_REMARKS FROM TFO_DJ_FINAL_DEC_SUMMARY where wi_name ='"+this.winame+"' and REFFERD_TO='Customer'");
			xp5 = new XMLParser(outputXML);
			remarks=xp5.getValueOf("EXCP_REMARKS");
			if(remarks.contains("USERCMNT :"))
			remarks=remarks.substring(remarks.indexOf("USERCMNT :")+10, remarks.length());
			
		}else if(curr_ws.trim().equalsIgnoreCase("PP_MAKER")&&decCode.equalsIgnoreCase("REF")&&customerEmailReferalFlag>0&&customerReferalFlag==0){
			wiTxnStatus="Request for clarification from customer in email";
			//ATP-489 REYAZ 04-07-2024  START
			outputXML=APCallCreateXML.APSelect("SELECT EXCP_REMARKS FROM TFO_DJ_FINAL_DEC_SUMMARY where wi_name ='"+this.winame+"' and REFFERD_TO='Customer Only Through Email'");
			xp5 = new XMLParser(outputXML);
			remarks=xp5.getValueOf("EXCP_REMARKS");
			if(remarks.contains("USERCMNT :"))
			remarks=remarks.substring(remarks.indexOf("USERCMNT :")+10, remarks.length());
			//ATP-489 REYAZ 04-07-2024  END
		}else if(curr_ws.trim().equalsIgnoreCase("PP_MAKER")&&decCode.equalsIgnoreCase("REF")&&reffredTo.equalsIgnoreCase("RM")&&customerReferalFlag==0&&customerEmailReferalFlag==0){
			wiTxnStatus="Pending for internal approval";
		}
		
		if(curr_ws.trim().equalsIgnoreCase("PP_MAKER")&&decCode.equalsIgnoreCase("APP") && !ReqCat.trim().equalsIgnoreCase("IFCPC")){ //ATP-500 REYAZ 01-08-2024 
			wiTxnStatus="Under review";
		}
		//ATP-500 REYAZ 01-08-2024 START
		if(curr_ws.trim().equalsIgnoreCase("PP_MAKER")&&decCode.equalsIgnoreCase("APP") && ReqCat.trim().equalsIgnoreCase("IFCPC")){ //ATP-500 REYAZ 01-08-2024 
			wiTxnStatus="Completed";
		}
		//ATP-500 REYAZ 01-08-2024 END
		if(curr_ws.trim().equalsIgnoreCase("PROCESSING MAKER")&&(decCode.equalsIgnoreCase("TXNC")||decCode.equalsIgnoreCase("TXNAU"))){
			String whereClause ="WI_NAME = '"+this.winame+ "'";
			String opXml1 = APCallCreateXML.APUpdate("EXT_TFO", "PM_TSLM_SENDMSG_FLAG", "'Y'",
					whereClause, sessionId);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Update opXml1: " + opXml1);
			
			wiTxnStatus="Under process";
		}
		/*if(curr_ws.trim().equalsIgnoreCase("PROCESSING MAKER")&&(decCode.equalsIgnoreCase("REF")||decCode.equalsIgnoreCase("RET"))){
			wiTxnStatus="Pending for internal approval";
		}*///Commented for time being as per Avinash defect ID 100182
		if((curr_ws.trim().equalsIgnoreCase("PROCESSING CHECKER")||curr_ws.trim().equalsIgnoreCase("PC PROCESSING SYSTEM")||curr_ws.trim().equalsIgnoreCase("Legalization Queue")||curr_ws.trim().equalsIgnoreCase("Archival"))&&(decCode.equalsIgnoreCase("TXNAC")||decCode.equalsIgnoreCase("TXNAU")||decCode.equalsIgnoreCase("TXNC")||decCode.equalsIgnoreCase("SBMT"))){
			wiTxnStatus="Completed";
		}
		 if(ReqCat.trim().equalsIgnoreCase("IFCPC")&&curr_ws.trim().equalsIgnoreCase("PP_MAKER")&&decCode.equalsIgnoreCase("REF")){
			wiTxnStatus="Pending for internal approval";
		}
		
//		if(curr_ws.trim().equalsIgnoreCase("PROCESSING CHECKER")&&(decCode.equalsIgnoreCase("RPM")||decCode.equalsIgnoreCase("RPPM"))){
//			wiTxnStatus="Pending for internal approval";
//		}Commented for time being as per Avinash defect ID 100182
		
		outputXML = APCallCreateXML.APSelect("select LEGALIZATION_CHARGES_DETAIL,EDAS_APPROVAL,LEGALIZATION_CHARGES , EDAS_REFERENCE, LOAN_VAL_DATE,UI_EXCHANGE_RATE from ext_tfo where wi_name='"+this.winame+"'");
		xp5 = new XMLParser(outputXML);
		
		String legalizationChargesApplicable="";
		String edasApprovalStatus="";
		String legalizationChargesAsPerEDAS="";
		String EDASReference="";
		String loanValueDate="";
		String UserInputtedExchangeRate="";
		
		edasApprovalStatus=xp5.getValueOf("EDAS_APPROVAL");
		legalizationChargesAsPerEDAS=xp5.getValueOf("LEGALIZATION_CHARGES");
		EDASReference=xp5.getValueOf("EDAS_REFERENCE");
		loanValueDate=xp5.getValueOf("LOAN_VAL_DATE");
		UserInputtedExchangeRate=xp5.getValueOf("UI_EXCHANGE_RATE");
		
		outputXML = APCallCreateXML.APSelect("select LT_RESPONSE from TFO_DJ_LMT_CRDT_RECORDS where LT_DOC_RVW_GDLINES ='Legalization Charges Applicable' AND wi_name ='"+this.winame+"'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputXML :"+outputXML);
		xp5 = new XMLParser(outputXML);
		
		legalizationChargesApplicable=xp5.getValueOf("LT_RESPONSE");
		
		/*if(legalizationChargesApplicable.trim().equalsIgnoreCase("Yes"))
			legalizationChargesApplicable="Y";
		else 
			legalizationChargesApplicable="N";
		
		if(edasApprovalStatus.trim().equalsIgnoreCase("Not Applicable"))
			edasApprovalStatus="";
		else if(edasApprovalStatus.trim().equalsIgnoreCase("Rejected"))
			edasApprovalStatus="R";
		else if(edasApprovalStatus.trim().equalsIgnoreCase("Pending"))
			edasApprovalStatus="P";
		else
			edasApprovalStatus="";*/
		//Commented for Defect ID 100395 Complete Description should be passed to TSLM
		//Re commented after defect is handled in TSLM
			
	    				
			  	      
			  String outputXML1 = APCallCreateXML.APSelect("select count(0) as count  from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW "
			 		+ "where wi_name = '"+this.winame+"'");
			  XMLParser xp1 = new XMLParser(outputXML1);
			  int countLimitGrid =Integer.parseInt(xp1.getValueOf("count"));
			  LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "countLimitGrid :"+countLimitGrid);
			  String outputXML2 = APCallCreateXML.APSelect("select count(0) as count  from TFO_DJ_TSLM_REFERRAL_DETAIL "
				 		+ "where wi_name = '"+this.winame+"'");
			  XMLParser xp2 = new XMLParser(outputXML2);
			  int countSignGrid =Integer.parseInt(xp2.getValueOf("count"));
			  LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "countSignGrid :"+countSignGrid);
			  String outputXML3 = APCallCreateXML.APSelect("select count(0) as count  from TFO_DJ_TSLM_DOCUMENT_REVIEW "
				 		+ "where wi_name = '"+this.winame+"'");
			  XMLParser xp3 = new XMLParser(outputXML3);
			  int countDocGrid =Integer.parseInt(xp3.getValueOf("count"));
			  LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "countDocGrid :"+countDocGrid);
			  
			  if(countLimitGrid ==0 && countSignGrid ==0 && countDocGrid ==0){
				  output.append("<referalFlag>N</referalFlag>");
			  } else {
				  output.append("<referalFlag>Y</referalFlag>");
			  }
			  output.append("<tslmId>"+ tslmID+ "</tslmId>")
				.append("<wiTxnStatus>"+ wiTxnStatus+"</wiTxnStatus>");
				
		  if((curr_ws.trim().equalsIgnoreCase("PP_MAKER") && decCode.equalsIgnoreCase("REF") && (customerReferalFlag>0 || customerEmailReferalFlag>0))
				  || (curr_ws.trim().equalsIgnoreCase("PP_MAKER") && decCode.equalsIgnoreCase("REJ"))) //Added by Shivanshu ATP-481
				output.append("<returntToCustomerRemarks>"+remarks.replaceAll("&", "&amp;") +"</returntToCustomerRemarks>");  // ATP -482 REYAZ 14-06-2024
		  else
			  output.append("<returntToCustomerRemarks></returntToCustomerRemarks>");
		  
		//  if(!legalizationChargesApplicable.trim().isEmpty())
		  output.append("<legalizationChargesApplicable>"+legalizationChargesApplicable+"</legalizationChargesApplicable>");
		  //if(!edasApprovalStatus.trim().isEmpty())
		  output.append("<edasApprovalStatus>"+edasApprovalStatus+"</edasApprovalStatus>");
		  //if(!legalizationChargesAsPerEDAS.trim().isEmpty())
		  output.append("<legalizationChargesAsPerEDAS>"+legalizationChargesAsPerEDAS+"</legalizationChargesAsPerEDAS>");
		  //if(!EDASReference.trim().isEmpty())
		  output.append("<EDASReference>"+EDASReference+"</EDASReference>");
		  //if(!loanValueDate.trim().isEmpty())
		  output.append("<loanValueDate>"+loanValueDate+"</loanValueDate>");
		  //if(!UserInputtedExchangeRate.trim().isEmpty())
		  output.append("<UserInputtedExchangeRate>"+UserInputtedExchangeRate+"</UserInputtedExchangeRate>");
			
			
			  if(countLimitGrid>0||countSignGrid>0||countDocGrid>0){
				  output.append("<referralList>");
				  String outputXML4 = APCallCreateXML.APSelect("select SEQNO,TRANSTYPE,TRANSID,COUNTERPARTYCID,REFERRALTYPE,REFCODE,USERCMNT,STATUS from TFO_DJ_TSLM_LIMIT_CREDIT_REVIEW where wi_name = '"+winame+"' AND SEQNO  NOT LIKE '%WMS%'"
															+" union "
															+"select SEQNO,TRANSTYPE,TRANSID,COUNTERPARTYCID,REFERRALTYPE,REFCODE,USERCMNT,STATUS from TFO_DJ_TSLM_REFERRAL_DETAIL where wi_name = '"+winame+"' AND SEQNO  NOT LIKE '%WMS%'"
															+" union "
															+"select SEQNO,TRANSTYPE,TRANSID,COUNTERPARTYCID,REFERRALTYPE,REFCODE,USERCMNT,STATUS from TFO_DJ_TSLM_DOCUMENT_REVIEW where wi_name = '"+winame+"' AND SEQNO  NOT LIKE '%WMS%'");
				  XMLParser xp4 = new XMLParser(outputXML4);
				 
				  int start = xp4.getStartIndex("Records", 0, 0);
				  int deadEnd = xp4.getEndIndex("Records", start, 0);
				  int noOfFields = xp4.getNoOfFields("Record", start, deadEnd);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"No of Records in Tslm referral: "
							+noOfFields);
				 for (int i = 0; i < noOfFields; ++i) {
					String Record = xp4.getNextValueOf("Record");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Tslm referral: Record :"+Record);
					XMLParser xp6 = new XMLParser(Record);
					seqNo = xp6.getValueOf("SEQNO");
					transType = xp6.getValueOf("TRANSTYPE");
					transId = xp6.getValueOf("TRANSID");
					refCode = xp6.getValueOf("REFCODE");
					referralType = xp6.getValueOf("REFERRALTYPE");
					refDesc = xp6.getValueOf("REFDESC");
					userCmnt = xp6.getValueOf("USERCMNT");
					status = xp6.getValueOf("STATUS");
					if(status.trim().equalsIgnoreCase("active")){
						status="A";
					}else{
						status="C";
					}
					cpCid  = xp6.getValueOf("COUNTERPARTYCID");
					
					output.append("<referal>")
					      .append("<seqNo>"+seqNo+"</seqNo>")
					      .append("<trxType>"+transType+"</trxType>")
					      .append("<trxId>"+transId+"</trxId>")
					      .append("<cpcid>"+cpCid+"</cpcid>")
					      .append("<referralUnit>"+refCode+"</referralUnit>")
					      .append("<referralDesc>"+refDesc.replaceAll("&", "&amp;")+"</referralDesc>")  // ATP -482 REYAZ 14-06-2024
					      .append("<comment>"+userCmnt.replaceAll("&", "&amp;")+"</comment>")   // ATP -482 REYAZ 14-06-2024
					      .append("<referralType>"+referralType+"</referralType>")
					      .append("<status>"+status+"</status>")
					      .append("</referal>");
				 }
				 output.append("</referralList>");
			  }else{
				  output.append("<referralList></referralList>");
			  }
				
	 } catch(Exception e ){
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getStatusReferralBlock: ");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
	}
	LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output: " + output.toString());
	return output.toString();
	}	
	
	// ADDED by Shivanshu ATP-346 for fetching Limit Line 
		public String getLimitLine(){
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE C_TSLM getLimitLine: ");
			String limitLine="";
		    try{
			
				String outputXML = APCallCreateXML.APSelect("select LT_RESPONSE from TFO_DJ_LMT_CRDT_RECORDS where LT_DOC_RVW_GDLINES='PLEASE SPECIFY LIMIT LINE' and wi_name=  '"+this.winame+"'");
				XMLParser xmlparser = new XMLParser(outputXML);
				int mainCode = Integer.parseInt(xmlparser.getValueOf("MainCode"));
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "mainCode: " + mainCode);			
				if(mainCode == 0){
					if(Integer.parseInt(xmlparser.getValueOf("TotalRetrieved")) > 0){
						limitLine = xmlparser.getValueOf("LT_RESPONSE");
						LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "limitLine: " + limitLine);	
					}
				}
				}catch(Exception e ){
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in C_TSLM getMaturityDate: ");
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);	
				}


			return limitLine;
		}
		
	// ATP-362 11-01-2023 REYAZ		
	public String getAdditionalStlTag() {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "INSIDE getAdditionalStlTag: ");
		StringBuffer output = new StringBuffer();
		try {
          output.append("<principalFxRate>" + principalFxRate + "</principalFxRate>")
				.append("<fromCCYPrincipal>" + fromCur + "</fromCCYPrincipal>")
				.append("<toCCYPrincipal>" + toCur + "</toCCYPrincipal>")
				.append("<interestFxRate>" + interestFxRate + "</interestFxRate>")
				.append("<fromCCYInterest>"+ fromCur + "</fromCCYInterest>")
				.append("<toCCYInterest>"+ toCur + "</toCCYInterest>");
		} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in getAdditionalStlTag: ");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "output ### " + output.toString());
		return output.toString();
	}	
	
	@Override
	public String executeCall(HashMap<String, String> input) throws Exception {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Execute call called in PushMessageTSLM");
		return call();
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
		//ATP - 346 Added by Shivanshu
		String valList = "'"+ this.winame +"',"+ stageId +", 'PushMessageTSLM', '"+ callStatus +"',SYSTIMESTAMP,"+ returnCode +", '"+ errorDescription +"', '"+
				errorDetail +"', '"+ status +"', '"+ reason +"'";
		APCallCreateXML.APInsert("BPM_ORCHESTRATION_STATUS", "WI_NAME, STAGE_ID, CALL_NAME, CALL_STATUS, CALL_DT, RETURN_CODE, ERROR_DESCRIPTION, "
				+ "ERROR_DETAIL, STATUS, REASON", valList, sessionId);
		
	}
	

}
