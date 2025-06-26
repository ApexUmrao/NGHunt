package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub;
import com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchReferenceDetailsReqMsg;
import com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchReferenceDetailsReq_type0;
import com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchReferenceDetailsResMsg;
import com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.FetchReferenceDetailsRes_type0;
import com.newgen.dscop.stub.InqCustEmiratesDigitalWalletStub.HeaderType;

public class FetchReferenceDetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;
	DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

	public String FetchReferenceDetailsCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called FetchReferenceDetails");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustEmiratesDigitalWallet");
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String sourcingChannel=xmlDataParser.getValueOf("sourceChannel");
			String extSystemName=xmlDataParser.getValueOf("extSystemName");
			
			InqCustEmiratesDigitalWalletStub stub = new InqCustEmiratesDigitalWalletStub(sWSDLPath);
			FetchReferenceDetailsReqMsg fetechReqMsg = new FetchReferenceDetailsReqMsg();
			FetchReferenceDetailsReq_type0 fetechReqMsg_type_0 = new FetchReferenceDetailsReq_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustEmiratesDigitalWallet");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("123456");
			Header_Input.setCredentials("123456");
			fetechReqMsg.setHeader(Header_Input);

			fetechReqMsg_type_0.setSourceChannel(sourcingChannel);
			fetechReqMsg_type_0.setExtSystemName(extSystemName);
			fetechReqMsg.setFetchReferenceDetailsReq(fetechReqMsg_type_0);

			System.out.println("FetchReferenceDetails input xml "+stub.getInputXMLFetchRef(fetechReqMsg));
			xmlInput=stub.getInputXMLFetchRef(fetechReqMsg);
			System.out.println("FetchReferenceDetails xmlInput xml  "+xmlInput);
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			FetchReferenceDetailsResMsg  fetchResMsg = stub.fetchReferenceDetails_Oper(fetechReqMsg);
			FetchReferenceDetailsRes_type0 fetchResMsg_type0 = fetchResMsg.getFetchReferenceDetailsRes();
			sOrg_put=stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails sOrg_put: "+sOrg_put);

			Header_Input=fetchResMsg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			fetchResMsg_type0 = fetchResMsg.getFetchReferenceDetailsRes();
			String refDetails = fetchResMsg_type0.getReferenceNumber();
			LogGEN.writeTrace("CBG_Log", "getFetchReferenceDetailsRes refDetails: "+refDetails);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustEmiratesDigitalWallet</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<referenceNumber>"+fetchResMsg_type0.getReferenceNumber()+"</referenceNumber>"+
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchReferenceDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>InqCustEmiratesDigitalWallet Failed</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchReferenceDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>InqCustEmiratesDigitalWallet Failed</Output>";
			e.printStackTrace();
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchReferenceDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch kiosk list</td></Output>";
			}		
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");

				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
		return sOutput;
	}
}
