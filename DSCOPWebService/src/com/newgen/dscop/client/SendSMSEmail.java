package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddSendSMSEmailStub;
import com.newgen.dscop.stub.AddSendSMSEmailStub.HeaderType;
import com.newgen.dscop.stub.AddSendSMSEmailStub.SMSReq_type0;
import com.newgen.dscop.stub.AddSendSMSEmailStub.SMSRes_type1;
import com.newgen.dscop.stub.AddSendSMSEmailStub.SMSServiceReq_type0;
import com.newgen.dscop.stub.AddSendSMSEmailStub.SMSServiceRes_type0;
import com.newgen.dscop.stub.AddSendSMSEmailStub.SRSMSServiceReqMsg;
import com.newgen.dscop.stub.AddSendSMSEmailStub.SRSMSServiceResMsg;

public class SendSMSEmail extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;
	/**
	 * Function written to fetch Debit Card Details
	 * @author gaurav.berry
	 * @param sInputXML
	 * @return
	 */

	@SuppressWarnings("finally")
	public String sendSMSEmail(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called SEND_SMS");
		LogGEN.writeTrace("CBG_Log", "SendSMSEmail sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sOutput="";

		try
		{
			//sHandler.readCabProperty("SEND_SMS");

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("SEND_SMS");
			LogGEN.writeTrace("CBG_Log", "SEND_SMS WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "SEND_SMS WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "SEND_SMS CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "SEND_SMS USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "SEND_SMS PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "SEND_SMS LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "SEND_SMS TIME_OUT: "+lTimeOut);

			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String acc=xmlDataParser.getValueOf("Account_Number");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("SenderId");
			String Mobile=xmlDataParser.getValueOf("Mobile");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);

			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			//sDate="06/08/2013 18:33:10";
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);

			AddSendSMSEmailStub sms = new AddSendSMSEmailStub(sWSDLPath);
			SMSServiceReq_type0 sms_req=new SMSServiceReq_type0();
			SMSReq_type0[] req=new SMSReq_type0[1];
			SRSMSServiceReqMsg req_msg=new SRSMSServiceReqMsg();

			HeaderType Header_Input = new HeaderType();
			LogGEN.writeTrace("CBG_Log", "All Objects created");

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddSendSMSEmail");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			LogGEN.writeTrace("CBG_Log", "All values set11");
			req[0]=new SMSReq_type0();
			req[0].setCustomerID(sCustomerID);
			req[0].setAcctNumber(acc);
			req[0].setMobileNumber(Mobile);
			req[0].setSmsTemplateID(xmlDataParser.getValueOf("TEMPLATE_ID"));//1397
			req[0].setSmsTextValues(xmlDataParser.getValueOf("MSG"));//"SchoolFees|1000.25||25/02/2013|234234|Insufficient Balance100055"
			req[0].setEmailTemplateID(xmlDataParser.getValueOf("EMAIL_TEMP_ID"));
			req[0].setEmailTextValues(xmlDataParser.getValueOf("EMAIL_TEXT"));
			req[0].setEmailAddress(xmlDataParser.getValueOf("EMAIL_ADDRESS"));
			req[0].setFlexiFiller1(xmlDataParser.getValueOf("FlexiFiller1"));
			req[0].setFlexiFiller2(xmlDataParser.getValueOf("FlexiFiller2"));
			req[0].setFlexiFiller3(xmlDataParser.getValueOf("FlexiFiller3"));
			req[0].setFlexiFiller4(xmlDataParser.getValueOf("FlexiFiller4"));
			req[0].setFlexiFiller5(xmlDataParser.getValueOf("FlexiFiller5"));
			req[0].setTransactionType(xmlDataParser.getValueOf("transactionType"));
			req[0].setSendAsChannel(xmlDataParser.getValueOf("sendAsChannel"));
			req[0].setLanguagePref(xmlDataParser.getValueOf("LanguagePref"));		

			sms_req.setSMSReq(req);
			req_msg.setHeader(Header_Input);
			req_msg.setSMSServiceReq(sms_req);
			LogGEN.writeTrace("CBG_Log", "SendSMSEmail InputXML: " + req_msg);
			sms._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			SRSMSServiceResMsg res_msg= sms.addSendSMSEmail_Oper(req_msg);				
			sOrg_Output=sms.resSenESMsg;
			LogGEN.writeTrace("CBG_Log", "SendSMSEmail sOrg_Output: " + sOrg_Output);
			Header_Input = res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			System.out.println( "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>SEND_SMS</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<SMSRes>";
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				SMSServiceRes_type0 res= res_msg.getSMSServiceRes();				   
				SMSRes_type1[] r=res.getSMSRes();
				sOutput+="<Account_Number>"+r[0].getAcctNumber()+"</Account_Number>"+
						"<CUST_ID>"+r[0].getCustomerID()+"</CUST_ID>"+
						"<Reason>"+r[0].getReason()+"</Reason>"+
						"<Status>"+r[0].getStatus()+"</Status>";
				sOutput+="</SMSRes></Output>" ;
			} 
			else
			{
				sOutput+="</SMSRes></Output>" ;
			}

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";

			//sHandler.readCabProperty("JTS");	
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=sms.getinputXML(req_msg);

			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				e.getMessage();
			}

			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>SEND_SMS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to send SMS.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>SEND_SMS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to send SMS.</td></Output>";
			}
			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}

