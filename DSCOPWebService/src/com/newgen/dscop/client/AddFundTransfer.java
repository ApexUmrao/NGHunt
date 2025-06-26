package com.newgen.dscop.client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddFundTransferStub;
import com.newgen.dscop.stub.AddFundTransferStub.FundTransferReq2_type0;
import com.newgen.dscop.stub.AddFundTransferStub.FundTransferRes2_type0;
import com.newgen.dscop.stub.AddFundTransferStub.HeaderType;
import com.newgen.dscop.stub.AddFundTransferStub.SRFundTransferReqMsg;
import com.newgen.dscop.stub.AddFundTransferStub.SRFundTransferReq_type0;
import com.newgen.dscop.stub.AddFundTransferStub.SRFundTransferResMsg;
import com.newgen.dscop.stub.AddFundTransferStub.SRFundTransferRes_type0;

public class AddFundTransfer extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrgRes="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	String sFundTransfer="";

	public String FundTransfer(String sInputXML){


		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called FundTransfer");
		LogGEN.writeTrace("CBG_Log", "AddFundTransfer sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		String xmlInput="";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);	

		String sTransactionAmount = xmlDataParser.getValueOf("sTransactionAmount");
		String sTransactionCode = xmlDataParser.getValueOf("sTransactionCode");
		String sTransactionCurrencyCode = xmlDataParser.getValueOf("sTransactionCurrencyCode");
		String sLocalCurrencyCode = xmlDataParser.getValueOf("sLocalCurrencyCode");
		String sConvRate = xmlDataParser.getValueOf("sConvRate");
		String sorgbrCode = xmlDataParser.getValueOf("sOrgbrCode");
		String sCalculateAmtFlag = xmlDataParser.getValueOf("sCalculateAmtFlag");
		String sAmntlocalCurr = xmlDataParser.getValueOf("sAmntlocalCurr");
		String sRepeatFlag = xmlDataParser.getValueOf("sRepeatFlag");
		String sServiceCharge = xmlDataParser.getValueOf("sServiceCharge");
		String sForceDebitFlag = xmlDataParser.getValueOf("sForceDebitFlag");
		String sPostSuspenseFlag = xmlDataParser.getValueOf("sPostSuspenseFlag");
		String sDebitAcctNumber = xmlDataParser.getValueOf("sDebitAcctNumber");
		String sDebitBrCode = xmlDataParser.getValueOf("sDebitBrCode");
		String sDebitNarration = xmlDataParser.getValueOf("sDebitNarration");
		String sDebitAccCurrCode = xmlDataParser.getValueOf("sDebitAccCurrCode");
		String sDebitAccTxnAmnt = xmlDataParser.getValueOf("sDebitAccTxnAmnt");
		String sConvRateToLCY = xmlDataParser.getValueOf("sConvRateToLCY");
		String creditCustId = xmlDataParser.getValueOf("sCreditCustId");
		String sCreditAccNumber = xmlDataParser.getValueOf("sCreditAccNumber");
		String sCreditBrCode = xmlDataParser.getValueOf("sCreditBrCode");
		String sCreditNarration = xmlDataParser.getValueOf("sCreditNarration");
		String sCreditAccCurrCode = xmlDataParser.getValueOf("sCreditAccCurrCode");
		String sCreditAccTxnAmnt = xmlDataParser.getValueOf("sCreditAccTxnAmnt");
		String sCreditRateConvToLCY = xmlDataParser.getValueOf("sCreditRateConvToLCY");
		String noDebitFlag = xmlDataParser.getValueOf("sNoDebitFlag");
		String sDocRefnumber = xmlDataParser.getValueOf("sDocRefnumber");
		
		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block:  AddFundTransfer");
			String wsdl = loadWSDLDtls(sHandler);
			String ref_no = xmlDataParser.getValueOf("Ref_No");
			String senderId =  xmlDataParser.getValueOf("SenderId");
			AddFundTransferStub afsStub = new AddFundTransferStub(wsdl);
			SRFundTransferReqMsg reqMsg = new SRFundTransferReqMsg();
			SRFundTransferReq_type0 reqMsgDetails = new SRFundTransferReq_type0();
			FundTransferReq2_type0 reqMsgDetails2 = new FundTransferReq2_type0();
			SRFundTransferResMsg resMsg = new SRFundTransferResMsg();

			reqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			reqMsgDetails2.setTransactionAmount(sTransactionAmount);
			reqMsgDetails2.setTransactionCode(sTransactionCode);
			reqMsgDetails2.setTransactionCurrencyCode(sTransactionCurrencyCode);
			reqMsgDetails2.setLocalCurrencyCode(sLocalCurrencyCode);
			reqMsgDetails2.setConvRateTRNtoLCY(sConvRate);
			reqMsgDetails2.setOrgBranchCode(sorgbrCode);
			reqMsgDetails2.setCalculateAmountFlag(sCalculateAmtFlag);
			reqMsgDetails2.setTxnAmountLocalCurr(sAmntlocalCurr);
			reqMsgDetails2.setRepeatFlag(sRepeatFlag);
			reqMsgDetails2.setServiceChargeFlag(sServiceCharge);
			reqMsgDetails2.setForceDebitFlag(sForceDebitFlag);
			reqMsgDetails2.setPostSuspenseFlag(sPostSuspenseFlag);
			reqMsgDetails2.setDebitAcctNumber(sDebitAcctNumber);
			reqMsgDetails2.setDebitAcctBrCode(sDebitBrCode);
			reqMsgDetails2.setDebitNarration(sDebitNarration);
			reqMsgDetails2.setDebitAcctCurrCode(sDebitAccCurrCode);
			reqMsgDetails2.setDebitAcctCurrTranAmount(sDebitAccTxnAmnt);
			reqMsgDetails2.setDebitConvRateAcctCurrToLCY(sConvRateToLCY);
			reqMsgDetails2.setCreditCustId(creditCustId);
			reqMsgDetails2.setCreditAcctNumber(sCreditAccNumber);
			reqMsgDetails2.setCreditAcctBrCode(sCreditBrCode);
			reqMsgDetails2.setCreditNarration(sCreditNarration);
			reqMsgDetails2.setCreditAcctCurrCode(sCreditAccCurrCode);
			reqMsgDetails2.setCreditAcctCurrTranAmount(sCreditAccTxnAmnt);
			reqMsgDetails2.setCreditConvRateAcctCurrToLCY(sCreditRateConvToLCY);
			reqMsgDetails2.setNoDebitFlag(noDebitFlag);
			reqMsgDetails2.setDocRefNumber(sDocRefnumber);


			reqMsgDetails.setFundTransferReq2(reqMsgDetails2);
			reqMsg.setSRFundTransferReq(reqMsgDetails);

			afsStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=afsStub.getInputXml(reqMsg);
			//			LogGEN.writeTrace("CBG_Log", "InputXML: AddFundTransfer---> Input Log\n" + xmlInput);
			resMsg = afsStub.addFundTransfer_Oper(reqMsg);
			sFundTransfer = afsStub.resAddFundTransfer;
			//			LogGEN.writeTrace("CBG_Log", "Output: AddFundTransfer---> Resonse Log\n" + resMsg);
			HeaderType header=resMsg.getHeader();
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode() AddFundTransfer:"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail() AddFundTransfer:"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription() AddFundTransfer:"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");

				SRFundTransferRes_type0 fundTransferResponse_type0 = new SRFundTransferRes_type0();
				fundTransferResponse_type0 = resMsg.getSRFundTransferRes();
				FundTransferRes2_type0 resFundTransfer = fundTransferResponse_type0.getFundTransferRes2();

				fundTransferResponse_type0.getFundTransferRes2().getTransactionAmount();


				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddFundTransfer</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<SRFundTransferRes>"+
						"<FundTransferRes2>"+
						"<transactionAmount>"+resFundTransfer.getTransactionAmount()+"</transactionAmount>"+
						"<transactionCode>"+resFundTransfer.getTransactionCode()+"</transactionCode>"+
						"<transactionCurrencyCode>"+resFundTransfer.getTransactionCurrencyCode()+"</transactionCurrencyCode>"+
						"<localCurrencyCode>"+resFundTransfer.getLocalCurrencyCode()+"</localCurrencyCode>"+
						"<convRateTRNtoLCY>"+resFundTransfer.getConvRateTRNtoLCY()+"</convRateTRNtoLCY>"+
						"<orgBranchCode>"+resFundTransfer.getOrgBranchCode()+"</orgBranchCode>"+
						"<calculateAmountFlag>"+resFundTransfer.getCalculateAmountFlag()+"</calculateAmountFlag>"+
						"<txnAmountLocalCurr>"+resFundTransfer.getTxnAmountLocalCurr()+"</txnAmountLocalCurr>"+
						"<repeatFlag>"+resFundTransfer.getRepeatFlag()+"</repeatFlag>"+
						"<serviceChargeFlag>"+resFundTransfer.getServiceChargeFlag()+"</serviceChargeFlag>"+
						"<forceDebitFlag>"+resFundTransfer.getForceDebitFlag()+"</forceDebitFlag>"+
						"<postSuspenseFlag>"+resFundTransfer.getPostSuspenseFlag()+"</postSuspenseFlag>"+
						"<debitAcctNumber>"+resFundTransfer.getDebitAcctNumber()+"</debitAcctNumber>"+
						"<debitAcctBrCode>"+resFundTransfer.getDebitAcctBrCode()+"</debitAcctBrCode>"+
						"<debitNarration>"+resFundTransfer.getDebitNarration()+"</debitNarration>"+
						"<debitChequeNo>"+resFundTransfer.getDebitChequeNo()+"</debitChequeNo>"+
						"<debitCustId>"+resFundTransfer.getDebitCustId()+"</debitCustId>"+
						"<debitAcctShortName>"+resFundTransfer.getDebitAcctShortName()+"</debitAcctShortName>"+
						"<debitAcctCurrCode>"+resFundTransfer.getDebitAcctCurrCode()+"</debitAcctCurrCode>"+
						"<debitAcctCurrTranAmount>"+resFundTransfer.getDebitAcctCurrTranAmount()+"</debitAcctCurrTranAmount>"+
						"<debitConvRateAcctCurrToLCY>"+resFundTransfer.getDebitConvRateAcctCurrToLCY()+"</debitConvRateAcctCurrToLCY>"+
						"<debitAcctAvailableBal>"+resFundTransfer.getDebitAcctAvailableBal()+"</debitAcctAvailableBal>"+
						"<creditAcctNumber>"+resFundTransfer.getCreditAcctNumber()+"</creditAcctNumber>"+
						"<creditAcctBrCode>"+resFundTransfer.getCreditAcctBrCode()+"</creditAcctBrCode>"+
						"<creditNarration>"+resFundTransfer.getCreditNarration()+"</creditNarration>"+
						"<creditCustId>"+resFundTransfer.getCreditCustId()+"</creditCustId>"+
						"<creditAcctShortName>"+resFundTransfer.getCreditAcctShortName()+"</creditAcctShortName>"+
						"<creditAcctCurrCode>"+resFundTransfer.getCreditAcctCurrCode()+"</creditAcctCurrCode>"+
						"<creditAcctCurrTranAmount>"+resFundTransfer.getCreditAcctCurrTranAmount()+"</creditAcctCurrTranAmount>"+
						"<creditConvRateAcctCurrToLCY>"+resFundTransfer.getCreditConvRateAcctCurrToLCY()+"</creditConvRateAcctCurrToLCY>"+
						"<noDebitFlag>"+resFundTransfer.getNoDebitFlag()+"</noDebitFlag>"+
						"<paymentRefNo>" + resFundTransfer.getPaymentRefNo() + "</paymentRefNo>" + 
						"<creditAcctAvailableBal>"+resFundTransfer.getCreditAcctAvailableBal()+"</creditAcctAvailableBal>"+
						"<docRefNumber>"+resFundTransfer.getDocRefNumber()+"</docRefNumber>"+
						"<status>"+resFundTransfer.getStatus()+"</status>"+
						"<reason>"+resFundTransfer.getReason()+"</reason>"+
						"</FundTransferRes2>"+
						"</SRFundTransferRes>"+
						"</Response>"+
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block AddFundTransfer:"+sOutput);

			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddFundTransfer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Add Fund Transfer</td></Output>";
			}
			//			LogGEN.writeTrace("CBG_Log", "output xml within if block  sOutput: AddFundTransfer:"+sOutput);
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddFundTransfer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Transfer Fund</td></Output>";
			e.printStackTrace();
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","AddFundTransfer outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddFundTransfer</Option><Output><returnCode>"+sReturnCode+"</returnCode>"
						+ "<errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Add Fund Transfer</td></Output>";
			}

			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")){
				Status="Success";
			}
			else {
				Status="Failure";
			}
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
//			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame = xmlDataParser.getValueOf("WiName");
			String sessionID = xmlDataParser.getValueOf("SessionId");
			String call_type = xmlDataParser.getValueOf("CBGCalltype");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) "
						+ "values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally  :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sFundTransfer.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}

	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("CentralBank");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddFundTransfer");
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer WebServiceConfig Map : " + wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddFundTransfer TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}

	private HeaderType setHeaderDtls(String sDate,String ref_no, String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("AddFundTransfer");
		headerType.setVersionNo("2.0");
		headerType.setServiceAction("Addition");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("BPM-WMS");
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete");
		return headerType;
	}
}
