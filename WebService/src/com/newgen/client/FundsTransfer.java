package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.*;
import com.newgen.stub.AddFundTransferStub.FundTransferReq2_type0;
import com.newgen.stub.AddFundTransferStub.FundTransferRes2_type0;
import com.newgen.stub.AddFundTransferStub.SRFundTransferReqMsg;
import com.newgen.stub.AddFundTransferStub.SRFundTransferReq_type0;
import com.newgen.stub.AddFundTransferStub.HeaderType;
import com.newgen.stub.AddFundTransferStub.SRFundTransferResMsg;
import com.newgen.stub.AddFundTransferStub.SRFundTransferRes_type0;


public class FundsTransfer extends WebServiceHandler {


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	@SuppressWarnings("finally")
	public String transfer_funds(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called Funds_Transfer");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sOutput= "";
		String xmlInput="";
				
		WebServiceHandler sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			sHandler.readCabProperty("Funds_Transfer");
			
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			
			AddFundTransferStub fund_stub=new AddFundTransferStub(sWSDLPath);
			SRFundTransferReq_type0 fund_req=new SRFundTransferReq_type0();
			SRFundTransferReqMsg fund_req_msg=new SRFundTransferReqMsg();
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddFundTransfer");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("");
			Header_Input.setCredentials(loggedinuser);
			
			FundTransferReq2_type0 funds=new FundTransferReq2_type0();
			
			funds.setTransactionAmount(xmlDataParser.getValueOf("transactionAmount"));
			funds.setTransactionCode(xmlDataParser.getValueOf("transactionCode"));
			funds.setTransactionCurrencyCode(xmlDataParser.getValueOf("transactionCurrencyCode"));
			funds.setCreditNarration(xmlDataParser.getValueOf("creditNarration"));			
			funds.setCreditAcctBrCode(xmlDataParser.getValueOf("creditAcctBrCode"));
			funds.setCreditAcctCurrCode(xmlDataParser.getValueOf("creditAcctCurrCode"));
			funds.setCreditAcctCurrTranAmount(xmlDataParser.getValueOf("creditAcctCurrTranAmount"));
			funds.setCreditAcctNumber(xmlDataParser.getValueOf("creditAcctNumber"));
			funds.setCreditConvRateAcctCurrToLCY(xmlDataParser.getValueOf("creditConvRateAcctCurrToLCY"));
			funds.setCreditCustId(xmlDataParser.getValueOf("creditCustId"));
			funds.setCustomerReferenceNumber(xmlDataParser.getValueOf("customerReferenceNumber"));
			funds.setConvRateTRNtoLCY(xmlDataParser.getValueOf("convRateTRNtoLCY"));
			funds.setOrgBranchCode(xmlDataParser.getValueOf("orgBranchCode"));
			funds.setCalculateAmountFlag(xmlDataParser.getValueOf("calculateAmountFlag"));
			funds.setTxnAmountLocalCurr(xmlDataParser.getValueOf("txnAmountLocalCurr"));
			funds.setRepeatFlag(xmlDataParser.getValueOf("repeatFlag"));
			funds.setServiceChargeFlag(xmlDataParser.getValueOf("serviceChargeFlag"));
			funds.setForceDebitFlag(xmlDataParser.getValueOf("forceDebitFlag"));
			funds.setPostSuspenseFlag(xmlDataParser.getValueOf("postSuspenseFlag"));
			funds.setNoDebitFlag(xmlDataParser.getValueOf("noDebitFlag"));
			
			funds.setValueDate(xmlDataParser.getValueOf("valueDate"))	;
			funds.setDebitAcctBrCode(xmlDataParser.getValueOf("debitAcctBrCode"));
			funds.setDebitAcctCurrCode(xmlDataParser.getValueOf("debitAcctCurrCode"));
			funds.setDebitAcctCurrTranAmount(xmlDataParser.getValueOf("debitAcctCurrTranAmount"));
			funds.setDebitAcctNumber(xmlDataParser.getValueOf("debitAcctNumber"));
			funds.setDebitChequeNo(xmlDataParser.getValueOf("debitChequeNo"));
			funds.setDebitConvRateAcctCurrToLCY(xmlDataParser.getValueOf("debitConvRateAcctCurrToLCY"));
			funds.setDebitCustId(xmlDataParser.getValueOf("debitCustId"));
			funds.setDebitNarration(xmlDataParser.getValueOf("debitNarration"));
			funds.setDocRefNumber("");
			
			fund_req.setFundTransferReq2(funds);
			
			fund_req_msg.setHeader(Header_Input);
			fund_req_msg.setSRFundTransferReq(fund_req);
			xmlInput=fund_stub.getinputXML(fund_req_msg);
			
			fund_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			SRFundTransferResMsg fund_res_msg= fund_stub.addFundTransfer_Oper(fund_req_msg);
			sOrg_Output=fund_stub.resFundMsg;//Added By Harish For inserting original mssg
			Header_Input=fund_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			System.out.println(sReturnCode+":"+sErrorDetail);
			//if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			//{
			try
			{
				SRFundTransferRes_type0 funds_res=new SRFundTransferRes_type0();
				funds_res=fund_res_msg.getSRFundTransferRes();
				FundTransferRes2_type0 r  = funds_res.getFundTransferRes2();
				System.out.println("Credit Acc----"+r.getCreditAcctNumber());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>Funds_Transfer</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<FundsTransferRes>"+
						"<CreditAccount>"+ r.getCreditAcctNumber() +"</CreditAccount>"+
						"<DebitAccount>"+ r.getDebitAcctNumber() +"</DebitAccount>"+
						"<Reason>"+ r.getReason() +"</Reason>"+
						"<Status>"+ r.getStatus() +"</Status>"+
						"<TransactionAmount>"+ r.getTransactionAmount() +"</TransactionAmount>"+
						"<TransactionCurrency>"+ r.getTransactionCurrencyCode() +"</TransactionCurrency>"+
						"<TransactionType>"+ r.getTransactionCode() +"</TransactionType>"+						
					"</FundsTransferRes>"+	
					"</Output>";
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Funds_Transfer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to transfer funds.</td></Output>";

			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Funds_Transfer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to transfer funds.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Funds_Transfer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to transfer funds.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";
			
			 try {
				sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			 
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				String inputXml=xmlInput;
				LogGEN.writeTrace("Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("Calltype");
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
				 /**
				  * //Added By Harish For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
					 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("Log"," Add  Query : finally :"+Query);
					 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
					 try {
						 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
