package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub;
import com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.HeaderType;
import com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.SettleIdentityReqMsg;
import com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.SettleIdentityReq_type0;
import com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.SettleIdentityResMsg;
import com.newgen.dscop.stub.ModEmiratesFaceRecognitionStub.SettleIdentityRes_type0;

public class ModEmiratesSettleIdentity extends DSCOPServiceHandler {

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String settleIdentity(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called settleIdentity");
		LogGEN.writeTrace("CBG_Log", "settleIdentity sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModEmiratesFaceRecognition");
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");
			String intermediaryAccountId = xmlDataParser.getValueOf("intermediaryAccountId");
			ModEmiratesFaceRecognitionStub request_stub=new ModEmiratesFaceRecognitionStub(sWSDLPath);
			SettleIdentityReqMsg reqMsg = new SettleIdentityReqMsg();
			SettleIdentityReq_type0 reqMsg_type0 = new SettleIdentityReq_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("ModEmiratesFaceRecognition");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			reqMsg_type0.setCustomerId(customerID);
			reqMsg_type0.setIntermediaryAccountId(intermediaryAccountId);
			reqMsg.setSettleIdentityReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);

			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			SettleIdentityResMsg resMsg = request_stub.settleIdentity_Oper(reqMsg);
			xmlInput= request_stub.getInputXML(reqMsg);
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity xmlInput xml : "+xmlInput);

			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity sOrg_put: "+sOrg_put);

			Header_Input=resMsg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			SettleIdentityRes_type0 settleIdentityRes_type0 = resMsg.getSettleIdentityRes();

			LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity resMsg xml : "+resMsg);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>ModEmiratesSettleIdentity</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<SettleIdentityRes>"+
						"<StatusCode>"+settleIdentityRes_type0.getStatusCode()+"</StatusCode>"+
						"<StatusMessage>"+settleIdentityRes_type0.getStatusMessage()+"</StatusMessage>"+
						"</SettleIdentityRes>"+	
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "Output XML--- ");	
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModEmiratesSettleIdentity</Option><Output><returnCode>"
						+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status>"
						+ "<td>Unable to retrieve ModEmiratesSettleIdentity.</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModEmiratesSettleIdentity</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to ModEmiratesSettleIdentity</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModEmiratesSettleIdentity</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to ModEmiratesSettleIdentity</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else{
				Status="Failure";
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
				e.printStackTrace();
			}
			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","ModEmiratesSettleIdentity  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","ModEmiratesSettleIdentity  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}
