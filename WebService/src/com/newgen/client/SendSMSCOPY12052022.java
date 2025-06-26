package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.AddSendSMSEmailStub;
import com.newgen.stub.AddSendSMSEmailStub.HeaderType;
import com.newgen.stub.AddSendSMSEmailStub.SMSRes_type1;
import com.newgen.stub.AddSendSMSEmailStub.SMSServiceReq_type0;
import com.newgen.stub.AddSendSMSEmailStub.SMSServiceRes_type0;
import com.newgen.stub.AddSendSMSEmailStub.SRSMSServiceReqMsg;
import com.newgen.stub.AddSendSMSEmailStub.SRSMSServiceResMsg;
import com.newgen.stub.AddSendSMSEmailStub.SMSReq_type0;

public class SendSMSCOPY12052022 extends WebServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	/**
	 * Function written to fetch Debit Card Details
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String send_sms(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called SEND_SMS");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			WebServiceHandler sHandler= new WebServiceHandler();
			
			try
			{
				sHandler.readCabProperty("SEND_SMS");
				
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
				String acc=xmlDataParser.getValueOf("Account_Number");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				String Mobile=xmlDataParser.getValueOf("Mobile");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				Date d= new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String sDate = dateFormat.format(d);
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				AddSendSMSEmailStub sms = new AddSendSMSEmailStub(sWSDLPath);
				SMSServiceReq_type0 sms_req=new SMSServiceReq_type0();
				SMSReq_type0[] req=new SMSReq_type0[1];
				SRSMSServiceReqMsg req_msg=new SRSMSServiceReqMsg();
				
				HeaderType Header_Input = new HeaderType();
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("AddSendSMSEmail");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Addition");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);
				LogGEN.writeTrace("Log", "All values set11");
				
				req[0]=new SMSReq_type0();
				req[0].setCustomerID(sCustomerID);
				req[0].setAcctNumber(acc);
				req[0].setMobileNumber(Mobile);
				req[0].setSendAsChannel("WMS");
				req[0].setSmsTemplateID(xmlDataParser.getValueOf("TEMPLATE_ID"));//1397
				req[0].setSmsTextValues(xmlDataParser.getValueOf("MSG"));//"SchoolFees|1000.25||25/02/2013|234234|Insufficient Balance100055"
				req[0].setTransactionType("AC");
				req[0].setSendAsChannel("S");
			
				sms_req.setSMSReq(req);
				req_msg.setHeader(Header_Input);
				req_msg.setSMSServiceReq(sms_req);
				
				System.out.println(sms.getinputXML(req_msg));
				LogGEN.writeTrace("Log", "All values set");
				sms._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				SRSMSServiceResMsg res_msg= sms.addSendSMSEmail_Oper(req_msg);				
				sOrg_Output=sms.resSenESMsg;//Added By Harish For inserting original mssg
				Header_Input = res_msg.getHeader();
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				
				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				System.out.println( "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
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
				
				
				 sHandler.readCabProperty("JTS");	
				 	String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=sms.getinputXML(req_msg);
					
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
				    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>SEND_SMS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to send SMS.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>SEND_SMS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to send SMS.</td></Output>";
				}
				
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}

