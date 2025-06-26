package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReqMsg;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardReq_type0;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardResMsg;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.AddDigitalCardRes_type0;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.HeaderType;

public class CardOnboarding extends DSCOPServiceHandler  {

	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String cardonboarding_ouput = null;

	@SuppressWarnings("finally")
	public String cardOnboarding(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called cardOnboarding");
		LogGEN.writeTrace("CBG_Log", "CardOnboarding sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput = "";
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("CARD_ONBOARDING");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("CARD_ONBOARDING");
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");

			ModCCDigitalCardDetailsStub card_onboarding_stub = new ModCCDigitalCardDetailsStub(sWSDLPath);
			AddDigitalCardReqMsg card_onboarding_stub_Req_Msg = new AddDigitalCardReqMsg();
			AddDigitalCardResMsg res_msg = new AddDigitalCardResMsg();
			AddDigitalCardReq_type0 req = new AddDigitalCardReq_type0();		

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCCDigitalCardDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			req.setAccountNumber(xmlDataParser.getValueOf("AccountNumber"));
			req.setAppRefNo(xmlDataParser.getValueOf("AppRefNo"));
			req.setBaseSegmentCardExpiry(xmlDataParser.getValueOf("BaseSegmentCardExpiry"));
			req.setCardExpiry(xmlDataParser.getValueOf("CardExpiry"));
			req.setCardLogo(xmlDataParser.getValueOf("CardLogo"));
			req.setCardNumber(xmlDataParser.getValueOf("CardNumber"));
			req.setCreditLimit(xmlDataParser.getValueOf("CreditLimit"));
			req.setCustomerNumber(xmlDataParser.getValueOf("CustomerNumber"));
			req.setDateOfBirth(xmlDataParser.getValueOf("DateOfBirth"));
			req.setDigitalIndicator(xmlDataParser.getValueOf("DigitalIndicator"));
			req.setEmbosserName1(xmlDataParser.getValueOf("EmbosserName1"));
			req.setMobileNo(xmlDataParser.getValueOf("MobileNo"));
			req.setNameLine1(xmlDataParser.getValueOf("NameLine1"));
			req.setRequestType(xmlDataParser.getValueOf("RequestType"));
			req.setSupCreditLimitPercentage(xmlDataParser.getValueOf("SupCreditLimitPercentage"));


			card_onboarding_stub_Req_Msg.setHeader(Header_Input);
			card_onboarding_stub_Req_Msg.setAddDigitalCardReq(req);
			xmlInput = card_onboarding_stub.getInputXml(card_onboarding_stub_Req_Msg);
			//			LogGEN.writeTrace("CBG_Log", "Card Onboarding InputXML: " + xmlInput);
			card_onboarding_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg = card_onboarding_stub.addDigitalCard_Oper(card_onboarding_stub_Req_Msg);
			cardonboarding_ouput = card_onboarding_stub.cardonboardingMsg;
			//			LogGEN.writeTrace("CBG_Log", "cardonboarding_ouput---"+cardonboarding_ouput);
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "Successful Result");
				AddDigitalCardRes_type0 res=new AddDigitalCardRes_type0();
				res=res_msg.getAddDigitalCardRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>CARD_ONBOARDING</Option>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
						"<returnCode>"+ sReturnCode +"</returnCode><errorDetail>"+ sErrorDetail +"</errorDetail>";						
				sOutput+="<CardNumber>"+ res.getCardNumber() + "</CardNumber>" +
						"<TransactionRefId>"+ res.getTransactionRefId() +"</TransactionRefId>"+
						"<Status>"+ res.getStatus() +"</Status>"+
						"<Reason>"+ res.getReason() +"</Reason>";
				sOutput += "</Output>";

				//LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);
			}
			else
			{
				AddDigitalCardRes_type0 res=new AddDigitalCardRes_type0();
				res=res_msg.getAddDigitalCardRes();
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CARD_ONBOARDING</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>"+res.getStatus()+"</Status><Reason>"+res.getReason()+"</Reason><td>Card Onboarding failed.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CARD_ONBOARDING</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Card Onboarding failed.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CARD_ONBOARDING</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Card Onboarding failed.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";


			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=xmlInput;
//			LogGEN.writeTrace("CBG_Log", inputXml);
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

			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," Card Onboarding NI Query : finally :"+Query);
//			LogGEN.writeTrace("CBG_Log","cardonboarding_ouput : finally :"+cardonboarding_ouput);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),cardonboarding_ouput.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.getMessage();
			}
//			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
