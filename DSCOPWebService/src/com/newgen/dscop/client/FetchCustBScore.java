package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustBScoreReqMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustBScoreReq_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustBScoreResMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustBScoreRes_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.HeaderType;

public class FetchCustBScore extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchCustBScore(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchCustBScore");
		LogGEN.writeTrace("CBG_Log", "FetchCustBScore sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerDigitalLending");
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");
			
			InqCustomerDigitalLendingStub request_stub=new InqCustomerDigitalLendingStub(sWSDLPath);
			FetchCustBScoreReqMsg reqMsg = new FetchCustBScoreReqMsg();
			FetchCustBScoreReq_type0 reqMsg_type0 = new FetchCustBScoreReq_type0();
			FetchCustBScoreResMsg response_msg = new FetchCustBScoreResMsg();
			FetchCustBScoreRes_type0 response_type0  = new FetchCustBScoreRes_type0();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerDigitalLending");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			reqMsg_type0.setCustomerId(customerID);
			reqMsg.setFetchCustBScoreReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);
			
			response_msg = request_stub.fetchCustBScore_Oper(reqMsg);
			xmlInput= request_stub.getInputXml(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getFetchCustBScoreRes();
			LogGEN.writeTrace("CBG_Log", "FetchCustBScore xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchCustBScoreResMsg sOrg_put: "+sOrg_put);
			
			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				String bScore = response_type0.getBScore();
				String custID = response_type0.getCustomerId();
				String scoreHistory = response_type0.getScoreHistory();
				if(scoreHistory==null )
					scoreHistory = "";

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchCustBScore</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<fetchCustBScoreRes>"+
						"<bScore>"+bScore+"</bScore>" +
						"<customerID>"+custID+"</customerID>"+
						"<scoreHistory>"+scoreHistory+"</scoreHistory>"+
						"</fetchCustBScoreRes>"+	
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
			LogGEN.writeTrace("CBG_Log","FetchCustBScore  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchCustBScore  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}