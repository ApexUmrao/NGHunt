package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCCDtlStub;
import com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReqMsg;
import com.newgen.dscop.stub.InqCCDtlStub.GetCreditCardDetailsReq_type0;
import com.newgen.dscop.stub.InqCCDtlStub.HeaderType;

public class InqCCDetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqCCDetails(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called GetCreditCardDetails");
		LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCCDetails");
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String cardNumber = xmlDataParser.getValueOf("CreditCardNumber");
			
			InqCCDtlStub request_stub=new InqCCDtlStub(sWSDLPath);
			GetCreditCardDetailsReqMsg reqMsg = new GetCreditCardDetailsReqMsg();
			GetCreditCardDetailsReq_type0 reqMsg_type0 = new GetCreditCardDetailsReq_type0();
//			GetCreditCardDetailsResMsg response_msg = new GetCreditCardDetailsResMsg();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCCDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			reqMsg_type0.setCreditCardNumber(cardNumber);
			reqMsg.setGetCreditCardDetailsReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);
			
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			String resMsg = request_stub.inqCCDtl_Oper(reqMsg);
			xmlInput= request_stub.getInputXml(reqMsg);
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetails xmlInput xml : "+xmlInput);
	
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetailsResMsg sOrg_put: "+sOrg_put);
			
			resMsg = resMsg.replace("ns2:", "");
			resMsg = resMsg.replace("ns1:", "");
			resMsg = resMsg.replace("ns0:", "");
			
			XMLParser xmlParser = new XMLParser(resMsg);
			sErrorDesc = xmlParser.getValueOf("errorDescription");
			sReturnCode = xmlParser.getValueOf("returnCode");
			sErrorDetail = xmlParser.getValueOf("errorDetail");
			
			LogGEN.writeTrace("CBG_Log", "GetCreditCardDetailsResMsg resMsg xml : "+resMsg);
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				String cardLimit = xmlParser.getValueOf("card_limit");
				String availableCredit = xmlParser.getValueOf("available_credit");
				String outStandingBalance = xmlParser.getValueOf("outstanding_balance");
				String cardBlockCode = xmlParser.getValueOf("card_block_code");
				//Added by Shivanshu New TAGS for CC CARD Eligible 
				String accountBlockCode1 = xmlParser.getValueOf("account_block_code1");
				String accountBlockCode2 = xmlParser.getValueOf("account_block_code2");

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCCDetails</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<InqCCDetailsRes>"+
						"<cardLimit>"+cardLimit+"</cardLimit>" +
						"<availableCredit>"+availableCredit+"</availableCredit>"+
						"<outStandingBalance>"+outStandingBalance+"</outStandingBalance>"+
						"<cardBlockCode>"+cardBlockCode+"</cardBlockCode>"+
						"<accountBlockCode1>"+accountBlockCode1+"</accountBlockCode1>"+
						"<accountBlockCode2>"+accountBlockCode2+"</accountBlockCode2>"+
						"</InqCCDetailsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Fetch customer BScore details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Fetch customer BScore details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Fetch customer BScore details</Output>";
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
			LogGEN.writeTrace("CBG_Log","InqCCDetails  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqCCDetails  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}