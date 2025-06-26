package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCSAcctTxnDtlStub;
import com.newgen.dscop.stub.InqCSAcctTxnDtlStub.GetAcctTransactionDetailsReqMsg;
import com.newgen.dscop.stub.InqCSAcctTxnDtlStub.GetAcctTransactionDetailsReq_type0;
import com.newgen.dscop.stub.InqCSAcctTxnDtlStub.HeaderType;

public class InqCSAcctTxnDtl extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqCSAcctTxnDtlCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqCSAcctTxnDtl");
		LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCSAcctTxnDtl");
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO"); 
			String custAcctNumber = xmlDataParser.getValueOf("CustAcctNumber");			
			String fromDate = xmlDataParser.getValueOf("FromDate");
			String toDate = xmlDataParser.getValueOf("ToDate");

			InqCSAcctTxnDtlStub request_stub=new InqCSAcctTxnDtlStub(sWSDLPath);
			GetAcctTransactionDetailsReqMsg reqMsg = new GetAcctTransactionDetailsReqMsg();
			GetAcctTransactionDetailsReq_type0 reqMsg_type0 = new GetAcctTransactionDetailsReq_type0();
			/*GetAcctTransactionDetailsResMsg response_msg = new GetAcctTransactionDetailsResMsg();
			GetAcctTransactionDetailsRes_type0 response_type0  = new GetAcctTransactionDetailsRes_type0();
*/
			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCSAcctTxnDtl");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("210742035");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));//WSUSER_SRC //TODO : Need To Confirm 
			Header_Input.setConsumer("FCUBS");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setRepTimeStamp(sDate);
			Header_Input.setUsername(loggedinuser);
			Header_Input.setCredentials(loggedinuser);

			reqMsg_type0.setCustAcctNumber(custAcctNumber);
			reqMsg_type0.setFromDate(fromDate);
			reqMsg_type0.setToDate(toDate);

			reqMsg.setHeader(Header_Input);
			reqMsg.setGetAcctTransactionDetailsReq(reqMsg_type0);

			String res_msg = request_stub.inqCSAcctTxnDtl_Oper(reqMsg);

			res_msg = res_msg.replace("ns2:", "");
			res_msg = res_msg.replace("ns1:", "");
			res_msg = res_msg.replace("ns0:", "");
			xmlInput= request_stub.getInputXML(reqMsg);
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl xmlInput xml : "+xmlInput);
			/*Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getGetAcctTransactionDetailsRes();*/
			XMLParser xmlParser = new XMLParser(res_msg);
			sErrorDesc = xmlParser.getValueOf("errorDescription");
			sReturnCode = xmlParser.getValueOf("returnCode");
			sErrorDetail = xmlParser.getValueOf("errorDetail");
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl sReturnCode: "+sReturnCode);
			String getAcctTransactionDetailsRes = xmlParser.getValueOf("GetAcctTransactionDetailsRes");
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl resMsg xml : "+res_msg);
			
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl sOrg_put: "+sOrg_put);

			/*sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();*/
			
			/*AcctDetails_type0 acctDetails = response_type0.getAcctDetails();
			StringBuilder details = new StringBuilder(); 
			if(response_type0 != null){

				Acct_Transaction_Details_type0  acct_Transaction_Details_type0= response_type0.getAcct_Transaction_Details();
				

				if(acct_Transaction_Details_type0 != null){

					Transactions_type0[] Transaction = acct_Transaction_Details_type0.getTransactions();

					for(Transactions_type0 Transactions : Transaction) {

						String transactionLiteral = Transactions.getTransactionLiteral();
						String transactionDate= Transactions.getTransactionDate();
						String valueDate= Transactions.getValueDate();
						String amount= Transactions.getAmount();
						String DrOrCr= Transactions.getDrOrCr();
						String transactionCode= Transactions.getTransactionCode();
						String description= Transactions.getDescription();
						String userID= Transactions.getUserID();
						String postingBranch= Transactions.getPostingBranch();
						String balanceAmount= Transactions.getBalanceAmount();
						String chequeNo= Transactions.getChequeNo();
						String sourceAmount= Transactions.getSourceAmount();
						String sourceCurrency= Transactions.getSourceCurrency();

						details.append("<Transaction>").append("\n")
						.append("<transactionLiteral>"+transactionLiteral+"</transactionLiteral>").append("\n")
						.append("<transactionDate>"+transactionDate+"</transactionDate>").append("\n")
						.append("<valueDate>"+valueDate+"</valueDate>").append("\n")
						.append("<amount>"+amount+"</amount>").append("\n")
						.append("<DrOrCr>"+DrOrCr+"</DrOrCr>").append("\n")
						.append("<transactionCode>"+transactionCode+"</transactionCode>").append("\n")
						.append("<description>"+description+"</description>").append("\n")
						.append("<userID>"+userID+"</userID>").append("\n")
						.append("<postingBranch>"+postingBranch+"</postingBranch>").append("\n")
						.append("<balanceAmount>"+balanceAmount+"</balanceAmount>").append("\n")
						.append("<chequeNo>"+chequeNo+"</chequeNo>").append("\n")
						.append("<sourceAmount>"+sourceAmount+"</sourceAmount>").append("\n")
						.append("<sourceCurrency>"+sourceCurrency+"</sourceCurrency>").append("\n")
						.append("</Transaction>").append("\n");
					}
				}
			}
*/
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				/*sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCSAcctTxnDtl</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<acctDetails>"+
						"<accountNumber>"+acctDetails.getAccountNumber()+"</accountNumber>"+
						"<accountTitle>"+ acctDetails.getAccountTitle() +"</accountTitle>"+
						"<accountCurrency>"+ acctDetails.getAccountCurrency() +"</accountCurrency>"+
						"<fromDates>"+ acctDetails.getFromDate() +"</fromDates>"+
						"<toDates>"+ acctDetails.getToDate() +"</toDates>"+
						"<openingBalance>"+ acctDetails.getOpeningBalance() +"</openingBalance>"+
						"<closingBalance>"+ acctDetails.getClosingBalance() +"</closingBalance>"+
						"</acctDetails>"+
						"<acct_Transaction_Details>"+
						getAcctTransactionDetailsRes+
						"</acct_Transaction_Details>"+	
						"</Output>";*/
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCSAcctTxnDtl</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<GetAcctTransactionDetailsRes>"+
						getAcctTransactionDetailsRes+
						"</GetAcctTransactionDetailsRes>"+
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_TRANSACTION_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquiry Transaction Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_TRANSACTION_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry Transaction Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_TRANSACTION_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry Transaction Details</Output>";
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
			LogGEN.writeTrace("CBG_Log","InqCSAcctTxnDtl  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqCSAcctTxnDtl  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}