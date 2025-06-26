package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustMDMPubInfoStub;
import com.newgen.dscop.stub.InqCustMDMPubInfoStub.HeaderType;
import com.newgen.dscop.stub.InqCustMDMPubInfoStub.InqCustMDMPubInfoReqMsg;
import com.newgen.dscop.stub.InqCustMDMPubInfoStub.InqCustMDMPubInfoReq_type0;
import com.newgen.dscop.stub.InqCustMDMPubInfoStub.InqCustMDMPubInfoResMsg;
import com.newgen.dscop.stub.InqCustMDMPubInfoStub.InqCustMDMPubInfoRes_type0;

public class FetchFATCADetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Output=null;
	/**
	 * @author gaurav.berry
	 * @Date : 21st April 2014
	 * @Purpose :  To Fetch FATCA Details of a Customer
	 * @param sInputXML
	 * @return
	 */

	@SuppressWarnings("finally")
	public String getFATCADetails(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called getFATCADetails");
		LogGEN.writeTrace("CBG_Log", "sInputXML FetchFATCADetails: "+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("Fetch_FATCA_Details");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Fetch_FATCA_Details");
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Fetch_FATCA_Details TIME_OUT: "+lTimeOut);


			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String sCustomerType= xmlDataParser.getValueOf("CUST_INFO_TYPE");
			String ref_no=xmlDataParser.getValueOf("REF_NO");

			InqCustMDMPubInfoStub InqFATCAStub = new InqCustMDMPubInfoStub(sWSDLPath);
			InqCustMDMPubInfoReqMsg InqFATCAReqMsg  = new InqCustMDMPubInfoReqMsg();
			InqCustMDMPubInfoResMsg InqFATCARepMsg = new  InqCustMDMPubInfoResMsg();
			InqCustMDMPubInfoRes_type0 InqFATCARep = new InqCustMDMPubInfoRes_type0();
			InqCustMDMPubInfoReq_type0 InqFATCAReq = new InqCustMDMPubInfoReq_type0();
			HeaderType Header_Input = new HeaderType();

			LogGEN.writeTrace("CBG_Log", "All Objects created");

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustMDMPubInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);

			InqFATCAReq.setCustomerId(sCustomerID);
			InqFATCAReq.setCustomerInfoType(sCustomerType);
			InqFATCAReqMsg.setHeader(Header_Input);
			InqFATCAReqMsg.setInqCustMDMPubInfoReq(InqFATCAReq);
			LogGEN.writeTrace("CBG_Log", "All values set");

			xmlInput=InqFATCAStub.getinputXML(InqFATCAReqMsg);

			InqFATCAStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			InqFATCARepMsg = InqFATCAStub.inqCustMDMPubInfo_Oper(InqFATCAReqMsg);
			sOrg_Output=InqFATCAStub.resFatcaDtlsMsg;
			Header_Input = InqFATCARepMsg.getHeader();

			sReturnCode= Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				LogGEN.writeTrace("CBG_Log", "Successful Result");
				InqFATCARep = InqFATCARepMsg.getInqCustMDMPubInfoRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FATCA_Details</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Customer>"+
						"<CustomerId>"+ InqFATCARep.getCustomerId() +"</CustomerId>"+
						"<CustomerInfoType>"+ InqFATCARep.getCustomerInfoType() +"</CustomerInfoType>"+
						"<CustomerInfo>"+ InqFATCARep.getCustomerInformation() +"</CustomerInfo>"+
						"</Customer>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "FetchFatcaDetails Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FATCA_Details</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch FATCA Details.</td></Output>";
			}
			LogGEN.writeTrace("CBG_Log", "FetchFatcaDetails Output XML--- "+sOutput);
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "FetchFatcaDetails Error in Web Serviice :"+e.toString());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FATCA_Details</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch FATCA Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","FetchFatcaDetails outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FATCA_Details</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch FATCA Details.</td></Output>";
			}

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
			{
				Status="Failure";
			}

			try 
			{
				//sHandler.readCabProperty("JTS");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			/*	String outputxml=sOutput;
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";

			LogGEN.writeTrace("CBG_Log",Query);*/

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			DBConnection con=new DBConnection();
			/**
			 * //Added By Harish For inserting original mssg
			 * Date			:	17 Mar 2015
			 * Description 	:	Replace execute with executeClob
			 */
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
			//				 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.getMessage();
			}

			LogGEN.writeTrace("CBG_Log","finally :"+sOutput);
			return sOutput;	
		}
	}
}
