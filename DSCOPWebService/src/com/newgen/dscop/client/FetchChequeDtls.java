package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.ChequeDetails_type0;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchChequeDtlsReqMsg;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchChequeDtlsReq_type0;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchChequeDtlsResMsg;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchChequeDtlsRes_type0;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.HeaderType;

public class FetchChequeDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchChequeDtlsCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchChequeDtls");
		LogGEN.writeTrace("CBG_Log", "FetchChequeDtls sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustPaymentDtls");
			LogGEN.writeTrace("CBG_Log", "FetchChequeDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustPaymentDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustPaymentDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustPaymentDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustPaymentDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustPaymentDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustPaymentDtls TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String accountNumber = xmlDataParser.getValueOf("accountNumber");
			String date = xmlDataParser.getValueOf("date");
			String enquireType = xmlDataParser.getValueOf("enquireType");
			String postingDate = xmlDataParser.getValueOf("postingDate");
			String postingRefNumber = xmlDataParser.getValueOf("postingRefNumber");
			String chequePostingType = xmlDataParser.getValueOf("chequePostingType");

			InqCustPaymentDtlsStub request_stub=new InqCustPaymentDtlsStub(sWSDLPath);
			FetchChequeDtlsReqMsg fetch_Cheque_dtls = new FetchChequeDtlsReqMsg();			
			FetchChequeDtlsReq_type0 fetch_Cheque_type0 = new FetchChequeDtlsReq_type0();
			FetchChequeDtlsResMsg fetch_Cheque_response = new FetchChequeDtlsResMsg();
			FetchChequeDtlsRes_type0 fetch_Cheque_res_type0 = new FetchChequeDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustPaymentDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setConsumer("");
			Header_Input.setRepTimeStamp("");
			Header_Input.setUsername("");
			Header_Input.setCredentials("");
			
			fetch_Cheque_type0.setAccountNumber(accountNumber);
			fetch_Cheque_type0.setDate(date);
			fetch_Cheque_type0.setEnquireType(enquireType);
			fetch_Cheque_type0.setChequePostingType(chequePostingType);
			fetch_Cheque_type0.setPostingDate(postingDate);
			fetch_Cheque_type0.setPostingRefNumber(postingRefNumber);
			
			fetch_Cheque_dtls.setFetchChequeDtlsReq(fetch_Cheque_type0);
			fetch_Cheque_dtls.setHeader(Header_Input);
			fetch_Cheque_dtls.setFetchChequeDtlsReq(fetch_Cheque_type0);

			fetch_Cheque_response = request_stub.fetchChequeDtls_Oper(fetch_Cheque_dtls);
			xmlInput= request_stub.getInputXml(fetch_Cheque_dtls);
			Header_Input = fetch_Cheque_response.getHeader();
			fetch_Cheque_res_type0 = fetch_Cheque_response.getFetchChequeDtlsRes();
			LogGEN.writeTrace("CBG_Log", "FetchChequeDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchChequeDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(fetch_Cheque_res_type0 != null){
					ChequeDetails_type0 chequeDetails[] = fetch_Cheque_res_type0.getChequeDetails();
					if(chequeDetails != null){
						for(ChequeDetails_type0 chequeDetail : chequeDetails) {
							String accountNum = chequeDetail.getAccountNumber();
							String accountTitle = chequeDetail.getAccountTitle();
							String amount = chequeDetail.getAmount();
							String chequeNumber = chequeDetail.getChequeNumber();
							String isPDC = chequeDetail.getIsPDC();
							String payStatus = chequeDetail.getPayStatus();
							String payingBank = chequeDetail.getPayingBank();
							String payingBankFinancialRejectReason = chequeDetail.getPayingBankFinancialRejectReason();
							String payingBankTechnicalRejectReason = chequeDetail.getPayingBankTechnicalRejectReason();
							String postingRef = chequeDetail.getPostingRef();
							String presentingBank = chequeDetail.getPresentingBank();
							String presentingBankFinancialRejectReason = chequeDetail.getPresentingBankFinancialRejectReason();
							String presentingBankRejectReason = chequeDetail.getPresentingBankRejectReason();
							String settlementDate = chequeDetail.getSettlementDate();
							
							details.append("<chequeDetails>").append("\n")
							.append("<accountNumber>"+accountNum+"</accountNumber>").append("\n")
							.append("<accountTitle>"+accountTitle+"</accountTitle>").append("\n")
							.append("<amount>"+amount+"</amount>").append("\n")
							.append("<chequeNumber>"+chequeNumber+"</chequeNumber>").append("\n")
							.append("<isPDC>"+isPDC+"</isPDC>").append("\n")
							.append("<payStatus>"+payStatus+"</payStatus>").append("\n")
							.append("<payingBank>"+payingBank+"</payingBank>").append("\n")
							.append("<payingBankFinancialRejectReason>"+payingBankFinancialRejectReason+"</payingBankFinancialRejectReason>").append("\n")
							.append("<payingBankTechnicalRejectReason>"+payingBankTechnicalRejectReason+"</payingBankTechnicalRejectReason>").append("\n")
							.append("<postingRef>"+postingRef+"</postingRef>").append("\n")
							.append("<presentingBank>"+presentingBank+"</presentingBank>").append("\n")
							.append("<presentingBankFinancialRejectReason>"+presentingBankFinancialRejectReason+"</presentingBankFinancialRejectReason>").append("\n")
							.append("<presentingBankRejectReason>"+presentingBankRejectReason+"</presentingBankRejectReason>").append("\n")
							.append("<settlementDate>"+settlementDate+"</settlementDate>").append("\n")
							.append("</chequeDetails>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchChequeDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<FetchChequeDtlsRes>"+
						details+
						"</FetchChequeDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchChequeDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to fetch Cheque Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchChequeDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch Cheque Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchChequeDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch Cheque Details</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Partial Success";
			else
				Status="Failure";

			try {
				//sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			LogGEN.writeTrace("CBG_Log", "WINAME:"+winame);
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type1=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{

			}
			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","FetchChequeDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchChequeDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

