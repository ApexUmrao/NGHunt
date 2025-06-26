/*----------------------------------------------------------------------------------------------------
		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Group					  : AP2
Product / Project		  : ADCB 
Module					  : 6.9. Real time account balance in WMS on account info screen 
File Name				  : FetchCustomerBalance.java
Author					  : Harinder Pal Singh
Date written (DD/MM/YYYY) : 03 August,2018
Description				  : Client stub for Fetch Customer Balance Webservice.
----------------------------------------------------------------------------------------------------
CHANGE HISTORY
-------------------------------------------------------------------------------------------------------
Problem No/CR No   Change Date   Changed By    Change Description
------------------------------------------------------------------------------------------------------
NA						NA			 NA					NA
-----------------------------------------------------------------------------------------------------*/

package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCSAcctBalanceStub;
import com.newgen.stub.InqCSAcctBalanceStub.GetCustBalanceReqMsg;
import com.newgen.stub.InqCSAcctBalanceStub.GetCustBalanceReq_type0;
import com.newgen.stub.InqCSAcctBalanceStub.GetCustBalanceResMsg;
import com.newgen.stub.InqCSAcctBalanceStub.GetCustBalanceRes_type0;
import com.newgen.stub.InqCSAcctBalanceStub.HeaderType;
import com.newgen.stub.InqCSAcctBalanceStub.Rep_INQ_CustBalance_type0;

/**
 * @author Harinder Pal Singh
 * @Date : 03 August 2018
 * @Purpose : CR July 2018
 * 6.1. Fetching Customer Balance call 
 * */


public class FetchCustomerBalance extends WebServiceHandler{

	static String sWSDLPath="";
	static String sCabinet="";
	static String sUser="";
	static String sPassword="";
	static boolean sLoginReq=false;
	static long lTimeOut = 30000;
	String sOrgRes="";
	String xmlInput="";
	static String dburl="";
	static String dbuser="";
	static String dbpass="";
	static String sOutput= "";
	
	WebServiceHandler sHandler;
	
	public String fetchAccBalance(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("Log", "Fuction called fetchAccBalance");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
	
		
		try {

			loadWSDLDtls(sHandler);
			String sCustomerID= xmlDataParser.getValueOf("CustID");	
			String sAccountNumber=xmlDataParser.getValueOf("AccountNumber");
			String sTranAmount=xmlDataParser.getValueOf("TranAmount");
			String sTranCurrency=xmlDataParser.getValueOf("TranCurrency");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			
			LogGEN.writeTrace("Log", "read Property successfully");
			
			InqCSAcctBalanceStub stub= new InqCSAcctBalanceStub(sWSDLPath);
			GetCustBalanceReqMsg getCustBalanceReqMsg= new GetCustBalanceReqMsg();
			GetCustBalanceReq_type0 getCustBalanceReq_type0= new GetCustBalanceReq_type0();
			
			//setting input values for fetch_balance call
			getCustBalanceReq_type0.setCustomerID(sCustomerID);
			getCustBalanceReq_type0.setAccountNumber(sAccountNumber);
			getCustBalanceReq_type0.setTranAmount(sTranAmount);
			getCustBalanceReq_type0.setTranCurrency(sTranCurrency);
			
			//add header details here
			getCustBalanceReqMsg.setHeader(setHeaderDtls(sDate,ref_no,xmlDataParser.getValueOf("SENDERID"),sCustomerID));
			
			getCustBalanceReqMsg.setGetCustBalanceReq(getCustBalanceReq_type0);
			
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			
			
			GetCustBalanceResMsg getCustBalanceResMsg = stub.inqCSAcctBalance_Oper(getCustBalanceReqMsg);
			xmlInput=stub.getInputXml(getCustBalanceReqMsg);
			LogGEN.writeTrace("Log", "input---"+xmlInput);
			LogGEN.writeTrace("Log", "output---"+stub.sOrgMsg);
			sOrgRes=stub.sOrgMsg;
			GetCustBalanceRes_type0 getCustBalanceRes_type0= getCustBalanceResMsg.getGetCustBalanceRes();
			Rep_INQ_CustBalance_type0 rep_INQ_CustBalance_type0 = getCustBalanceRes_type0.getRep_INQ_CustBalance();
			HeaderType header_input= getCustBalanceResMsg.getHeader();
			
			sAccountNumber= rep_INQ_CustBalance_type0.getAccountNo();
			String sCurrentBalance = rep_INQ_CustBalance_type0.getCurrentBalance();
			sCustomerID=rep_INQ_CustBalance_type0.getCustomerID();
			String acctStatusCode=rep_INQ_CustBalance_type0.getAcctStatusCode();
					
			sReturnCode= header_input.getReturnCode();
		    sErrorDetail=header_input.getErrorDetail();
		    sErrorDesc = header_input.getErrorDescription();
		    
		    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
		    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
		    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
		
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
			
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>FETCH_CUSTOMER_BALANCE</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Status>"+sErrorDesc+"</Status>" +		
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<Customer>"+
				"<CustomerId>"+sCustomerID+"</CustomerId>"+
				"<AccountNumber>"+sAccountNumber+"</AccountNumber>"+
				"<CurrentBalance>"+sCurrentBalance+"</CurrentBalance>"+
				"<acctStatusCode>"+acctStatusCode+"</acctStatusCode>"+
				"</Customer>"+				
				"</Output>";				
			} else
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>FETCH_CUSTOMER_BALANCE</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +	
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}
			
		} catch (IOException e) {
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUSTOMER_BALANCE</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch Customer Balance</td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}finally{
			
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><FETCH_CUSTOMER_BALANCE</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch Customer Balance</td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			
			
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("Calltype");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				//LogGEN.writeTrace("WBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	
	}

	
	private static void loadWSDLDtls(WebServiceHandler sHandler){
		try {
			sHandler.readCabProperty("FETCH_CUSTOMER_BALANCE");
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			e.printStackTrace();			
		}
	}
	
	
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid, String customerID){
		
		HeaderType headerType= new HeaderType();
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCSAcctBalance");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);			
		headerType.setSenderID(sHandler.setSenderId(senderid)); 
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername(customerID);
		headerType.setCredentials(loggedinuser);
		
		return headerType;
	}
	
	private static void loadJSTDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			e.printStackTrace();
		}
	}
	
	
}
