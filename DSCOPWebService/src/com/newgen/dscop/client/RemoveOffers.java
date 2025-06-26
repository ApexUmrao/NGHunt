package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddServiceRequestStub;
import com.newgen.dscop.stub.AddServiceRequestStub.HeaderType;
import com.newgen.dscop.stub.AddServiceRequestStub.RemoveOffersReqMsg;
import com.newgen.dscop.stub.AddServiceRequestStub.RemoveOffersReq_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.RemoveOffersResMsg;
import com.newgen.dscop.stub.AddServiceRequestStub.RemoveOffersRes_type0;

public class RemoveOffers extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String removeOffers(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called RemoveOffers");
		LogGEN.writeTrace("CBG_Log", "RemoveOffers sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddServiceRequest");
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String customerId = xmlDataParser.getValueOf("customerId");
			String referenceNumber = xmlDataParser.getValueOf("referenceNumber");

			AddServiceRequestStub request_stub=new AddServiceRequestStub(sWSDLPath);
			RemoveOffersReqMsg req_msg = new RemoveOffersReqMsg();
			RemoveOffersReq_type0 reqMsg_type0 = new RemoveOffersReq_type0();
			RemoveOffersResMsg response_msg = new RemoveOffersResMsg();
			RemoveOffersRes_type0 response_type0  = new RemoveOffersRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("AddServiceRequest");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer("");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setRepTimeStamp("");
			Header_Input.setUsername("");
			Header_Input.setCredentials("");
			Header_Input.setReturnCode("");
			Header_Input.setErrorDescription("");
			Header_Input.setErrorDetail("");
			reqMsg_type0.setCustomerId(customerId);
			reqMsg_type0.setReferenceNumber(referenceNumber);
			req_msg.setRemoveOffersReq(reqMsg_type0);
			req_msg.setHeader(Header_Input);

			response_msg = request_stub.removeOffers_Oper(req_msg);
			xmlInput= request_stub.getInputXML(req_msg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getRemoveOffersRes();
			LogGEN.writeTrace("CBG_Log", "RemoveOffers xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "RemoveOffersResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			String status = response_type0.getStatus();
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddServiceRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<removeOffersRes>"+
						"<status>"+status+"</status>"+
						"</removeOffersRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Remove Offers</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Add service Request - remove offers</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Remove Offers</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Add service Request - remove offers</Output>";
			e.printStackTrace();
		}

		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Remove Offers</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Add service Request - remove offers</Output>";
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

			} catch (Exception e) {

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
			LogGEN.writeTrace("CBG_Log","RemoveOffers  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","RemoveOffers  Exception: finally :"+e2.getStackTrace());
			}

		}
		return sOutput;			
	}
}