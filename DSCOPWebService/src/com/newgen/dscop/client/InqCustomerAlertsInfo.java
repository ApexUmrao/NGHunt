package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.Alerts_type0;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.CustomerAlerts_type0;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.InqCustomerAlertsInfoReqMsg;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.InqCustomerAlertsInfoReq_type0;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.InqCustomerAlertsInfoResMsg;
import com.newgen.dscop.stub.InqCustomerAlertsInfoStub.InqCustomerAlertsInfoRes_type0;

public class InqCustomerAlertsInfo extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqCustomerAlertsInfo(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqCustomerAlertsInfo");
		LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerAlertsInfo");
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("ref_no");
			String customerId = xmlDataParser.getValueOf("customerId");
			String fromDate = xmlDataParser.getValueOf("fromDate");
			String toDate = xmlDataParser.getValueOf("toDate");

			InqCustomerAlertsInfoStub request_stub=new InqCustomerAlertsInfoStub(sWSDLPath);
			InqCustomerAlertsInfoReqMsg reqMsg = new InqCustomerAlertsInfoReqMsg();
			InqCustomerAlertsInfoReq_type0 reqMsg_type0 = new InqCustomerAlertsInfoReq_type0();
			InqCustomerAlertsInfoResMsg response_msg = new InqCustomerAlertsInfoResMsg();
			InqCustomerAlertsInfoRes_type0 response_type0  = new InqCustomerAlertsInfoRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerAlertsInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			reqMsg_type0.setCustomerId(customerId);
			reqMsg_type0.setFromDate(fromDate);
			reqMsg_type0.setToDate(toDate);

			reqMsg.setInqCustomerAlertsInfoReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);

			response_msg = request_stub.inqCustomerAlertsInfo_Oper(reqMsg);
			xmlInput= request_stub.getInputXml(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getInqCustomerAlertsInfoRes();
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfoResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			StringBuilder details = new StringBuilder(); 
			if(response_type0 != null){
				CustomerAlerts_type0 customerAlerts =  response_type0.getCustomerAlerts();
				if(customerAlerts != null){
					Alerts_type0[] alerts = customerAlerts.getAlerts();

					for(Alerts_type0 detailsType0 : alerts) {
						String custId = detailsType0.getCustomerId();
						String mobileNumber = detailsType0.getMobileNumber();
						String templateId = detailsType0.getTemplateId();
						String messageText = detailsType0.getMessageText();
						String sendDateTime = detailsType0.getSendDateTime();
						String SMSsendDateTime = detailsType0.getSMSsendDateTime();
						String receivedDateTime = detailsType0.getReceivedDateTime();
						String statusCode = detailsType0.getStatusCode();
						String extractionDateTime = detailsType0.getExtractionDateTime();
						String deviceId = detailsType0.getDeviceId();
						String deviceType = detailsType0.getDeviceType();
						String notificationStatus = detailsType0.getNotificationStatus();

						details.append("<alerts>").append("\n")
						.append("<customerId>"+custId+"</customerId>").append("\n")
						.append("<mobileNumber>"+mobileNumber+"</mobileNumber>").append("\n")
						.append("<templateId>"+templateId+"</templateId>").append("\n")
						.append("<messageText>"+messageText+"</messageText>").append("\n")
						.append("<sendDateTime>"+sendDateTime+"</sendDateTime>").append("\n")
						.append("<SMSsendDateTime>"+SMSsendDateTime+"</SMSsendDateTime>").append("\n")
						.append("<receivedDateTime>"+receivedDateTime+"</receivedDateTime>").append("\n")
						.append("<statusCode>"+statusCode+"</statusCode>").append("\n")
						.append("<extractionDateTime>"+extractionDateTime+"</extractionDateTime>").append("\n")
						.append("<deviceId>"+deviceId+"</deviceId>").append("\n")
						.append("<deviceType>"+deviceType+"</deviceType>").append("\n")
						.append("<notificationStatus>"+notificationStatus+"</notificationStatus>").append("\n")
						.append("</alerts>").append("\n");
					}
				}
			}
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustomerAlertsInfo</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<InqCustomerAlertsInfoRes>"+
						"<customerAlerts>"+
						details+
						"</customerAlerts>"+
						"</InqCustomerAlertsInfoRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_CUST_ALERTS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquiry Customer Alerts</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_CUST_ALERTS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry Customer Alerts</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_CUST_ALERTS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry Customer Alerts</Output>";
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
			LogGEN.writeTrace("CBG_Log","InqCustomerAlertsInfo  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqCustomerAlertsInfo  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}