package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.stub.InqCustPersonalDtlStub.GetCustPersonalDetailReq_type0;
import com.newgen.dscop.stub.InqCustPersonalDtlStub.HeaderType;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustPersonalDtlStub;
import com.newgen.dscop.stub.InqCustPersonalDtlStub.GetCustPersonalDetailReqMsg;
import com.newgen.dscop.stub.InqCustPersonalDtlStub.GetCustPersonalDetailResMsg;

public class CustomerPersonalDetails extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";

	public String FetchCustomerPersonalDetails(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called CustomerPersonalDetails");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sOrg_Output="";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("CustomerPersonalDetails");
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath = (String)wsConfig.get(0);
			sCabinet = (String)wsConfig.get(1);
			sUser = (String)wsConfig.get(2);
			sLoginReq = Boolean.valueOf((String)wsConfig.get(3));
			sPassword = (String)wsConfig.get(4);
			lTimeOut = Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);

			String sCustomerID = xmlDataParser.getValueOf("CUST_ID");
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);	
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate+" sWSDLPath "+sWSDLPath);

			InqCustPersonalDtlStub stub = null;
			try
			{
				stub = new InqCustPersonalDtlStub(sWSDLPath);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error---"+e.getMessage());
			}
			GetCustPersonalDetailReqMsg reqMsg = new GetCustPersonalDetailReqMsg();						
			GetCustPersonalDetailResMsg resMsg = new GetCustPersonalDetailResMsg();
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails All Objects created");	

			reqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			GetCustPersonalDetailReq_type0 reqType0 = new GetCustPersonalDetailReq_type0();
			reqType0.setCID(sCustomerID);			
			reqMsg.setGetCustPersonalDetailReq(reqType0);

			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetailsAll values set");

			//xmlInput = custProfileStub.getInputXml(reqMsg);
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			String response = stub.inqCustPersonalDtl_Oper(reqMsg);
			xmlInput = stub.reqMsg;
			sOrg_Output = stub.resMsg;
			sOrg_Output = sOrg_Output.replaceAll("ns0:", "").replaceAll("ns1:", "");
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails Request: "+xmlInput);
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails Response: "+sOrg_Output);

			com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(sOrg_Output);
			sReturnCode = parser.getValueOf("returnCode");
			sOutput = sOrg_Output;
		} catch (Throwable e)
		{			
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails Error Trace in Web Service :"+e);
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CustomerPersonalDetails</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","CustomerPersonalDetails outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CustomerPersonalDetails</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
				Status="Success";
			}
			else {
				Status="Failure";

				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");

				String winame=xmlDataParser.getValueOf("WiName");
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
					LogGEN.writeTrace("CBG_Log", "Error inserting---"+e.getMessage());
				}
				DBConnection con=new DBConnection();

				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));

				LogGEN.writeTrace("CBG_Log","CustomerPersonalDetails outputXML : finally :"+sOutput);
			}
		}
		return sOutput;		
	}

	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCustPersonalDtl");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId);
		headerType.setReqTimeStamp(sDate);		
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
