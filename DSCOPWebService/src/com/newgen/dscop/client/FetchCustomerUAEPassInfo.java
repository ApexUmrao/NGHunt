package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.HeaderType;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReqMsg;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoReq_type0;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoResMsg;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchCustomerUAEPassInfoRes_type0;

public class FetchCustomerUAEPassInfo extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String FetchCustomerUAEPassInfo(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Function called FetchCustomerUAEPassInfo");
		LogGEN.writeTrace("CBG_Log", "FetchCustomerUAEPassInfo sInputXML---");
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
			//ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerUAEPassInfo");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerUAEPassAuthInfo");
			
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String accessCode = xmlDataParser.getValueOf("accessCode");
			String hashCodeState = xmlDataParser.getValueOf("hashCodeState");
			String redirectURI = xmlDataParser.getValueOf("redirectURI");
			
			InqCustomerUAEPassInfoStub request_stub=new InqCustomerUAEPassInfoStub(sWSDLPath);
			FetchCustomerUAEPassInfoReqMsg reqMsg = new FetchCustomerUAEPassInfoReqMsg();
			FetchCustomerUAEPassInfoReq_type0 reqMsg_type0 = new FetchCustomerUAEPassInfoReq_type0();
			FetchCustomerUAEPassInfoResMsg response_msg = new FetchCustomerUAEPassInfoResMsg();
			FetchCustomerUAEPassInfoRes_type0 response_type0  = new FetchCustomerUAEPassInfoRes_type0();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerUAEPassInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			
			reqMsg_type0.setAccessCode(accessCode);
			reqMsg_type0.setHashCodeState(hashCodeState);
			reqMsg_type0.setRedirectURI(redirectURI);
			
			reqMsg.setFetchCustomerUAEPassInfoReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);
			
			response_msg = request_stub.fetchCustomerUAEPassInfo_Oper(reqMsg);
			xmlInput= request_stub.getInputXml(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getFetchCustomerUAEPassInfoRes();
			LogGEN.writeTrace("CBG_Log", "FetchCustomerUAEPassInfo xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchCustomerUAEPassInfoResMsg sOrg_put: "+sOrg_put);
			
			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustomerUAEPassInfo</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<fetchCustomerUAEPassInfoRes>"+
						"<titleEnglish>"+response_type0.getTitleEnglish()+"</titleEnglish>"+
						"<titleArabic>"+response_type0.getTitleArabic()+"</titleArabic>"+
						"<fullNameEnglish>"+response_type0.getFullNameEnglish()+"</fullNameEnglish>"+
						"<fullNameArabic>"+response_type0.getFullNameArabic()+"</fullNameArabic>"+
						"<firstNameEnglish>"+response_type0.getFirstNameEnglish()+"</firstNameEnglish>"+
						"<firstNameArabic>"+response_type0.getFirstNameArabic()+"</firstNameArabic>"+
						"<lastNameEnglish>"+response_type0.getLastNameEnglish()+"</lastNameEnglish>"+
						"<lastNameArabic>"+response_type0.getLastNameArabic()+"</lastNameArabic>"+
						"<nationalityEnglish>"+response_type0.getNationalityEnglish()+"</nationalityEnglish>"+
						"<nationalityArabic>"+response_type0.getNationalityArabic()+"</nationalityArabic>"+
						"<gender>"+response_type0.getGender()+"</gender>"+
						"<emailId>"+response_type0.getEmailId()+"</emailId>"+
						"<mobileNumber>"+response_type0.getMobileNumber()+"</mobileNumber>"+
						"<emiratesId>"+response_type0.getEmiratesId()+"</emiratesId>"+
						"<userType>"+response_type0.getUserType()+"</userType>"+
						"<registeredIdType>"+response_type0.getRegisteredIdType()+"</registeredIdType>"+
						"<uniqueUserIdentifier>"+response_type0.getUniqueUserIdentifier()+"</uniqueUserIdentifier>"+
						"<smartDubaiPassId>"+response_type0.getSmartDubaiPassId()+"</smartDubaiPassId>"+
						"<transactionId>"+response_type0.getTransactionId()+"</transactionId>"+
						"</fetchCustomerUAEPassInfoRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUST_UAE_PASS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Fetch customer UAE Pass</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUST_UAE_PASS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Fetch customer UAE Pass</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUST_UAE_PASS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Fetch customer UAE Pass</Output>";
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
			LogGEN.writeTrace("CBG_Log","FetchCustomerUAEPassInfo  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchCustomerUAEPassInfo  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}

