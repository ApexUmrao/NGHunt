package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.HeaderType;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.RemoveCardPrintHoldReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.RemoveCardPrintHoldReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.RemoveCardPrintHoldResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.RemoveCardPrintHoldRes_type0;;

public class RemoveCardPrintHold extends DSCOPServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Output="";
	@SuppressWarnings("finally")

	public String removeCardPrintHold(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called RemoveCardPrintHold");
		LogGEN.writeTrace("CBG_Log", "RemoveCardPrintHold sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";		

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		String Status="";
		try
		{		

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("mod_cbg_customer_onboarding");
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding TIME_OUT: "+lTimeOut);

			String winame= xmlDataParser.getValueOf("winame");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId=xmlDataParser.getValueOf("SENDERID");
			String accountUser= xmlDataParser.getValueOf("accountUser");
			String authFlag=xmlDataParser.getValueOf("authFlag");
			String cardNumber=xmlDataParser.getValueOf("cardNumber");
			LogGEN.writeTrace("CBG_Log", "winame--- "+winame);

			ModCBGCustomerOnboardingStub modCBGCustomerOnboardingStub = new ModCBGCustomerOnboardingStub(sWSDLPath);			
			RemoveCardPrintHoldReqMsg removeCardPrintHoldReqMsg = new RemoveCardPrintHoldReqMsg();
			RemoveCardPrintHoldReq_type0 removeCardPrintHoldReq = new RemoveCardPrintHoldReq_type0();
			LogGEN.writeTrace("CBG_Log", "RemoveCardPrintHold created");			


			LogGEN.writeTrace("CBG_Log", "setting setHeaderDtls");
			HeaderType headerType= new HeaderType();		
			headerType.setUsecaseID("1234");
			headerType.setServiceName("ModCBGCustomerOnboarding");
			headerType.setVersionNo("1.0");
			headerType.setServiceAction("Modify");
			headerType.setSysRefNumber(ref_no);
			headerType.setSenderID(senderId); 
			headerType.setConsumer("BPM-WMS");
			headerType.setReqTimeStamp(sDate);
			LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete");
			modCBGCustomerOnboardingStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			LogGEN.writeTrace("CBG_Log", "Header created");

			removeCardPrintHoldReq.setAccountUser(accountUser);
			removeCardPrintHoldReq.setAuthFlag(authFlag);
			removeCardPrintHoldReq.setCardNumber(cardNumber);
			removeCardPrintHoldReqMsg.setHeader(headerType);
			removeCardPrintHoldReqMsg.setRemoveCardPrintHoldReq(removeCardPrintHoldReq);

			xmlInput = modCBGCustomerOnboardingStub.getinputXML(removeCardPrintHoldReqMsg);
			LogGEN.writeTrace("CBG_Log", "RemoveCardPrintHold input "+xmlInput);


			RemoveCardPrintHoldResMsg  removeCardPrintHoldResMsg = modCBGCustomerOnboardingStub.removeCardPrintHold_Oper(removeCardPrintHoldReqMsg);
			headerType = removeCardPrintHoldResMsg.getHeader();
			sOrg_Output = modCBGCustomerOnboardingStub.outputXML;
			sReturnCode = headerType.getReturnCode();
			sErrorDetail = headerType.getErrorDetail();
			sErrorDesc = headerType.getErrorDescription();			
			sOutput =  sOrg_Output;			

			LogGEN.writeTrace("CBG_Log","Error Description--- "+sErrorDesc);
			LogGEN.writeTrace("CBG_Log","Error Description--- "+sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				RemoveCardPrintHoldRes_type0 removeCardPrintHoldRes_type0 = removeCardPrintHoldResMsg.getRemoveCardPrintHoldRes();
				sOutput =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>RemoveCardPrintHold</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDetails>"+sErrorDetail+"</errorDetails>"+
						"<errorDescription>"+sErrorDesc+"</errorDescription>"+
						"<RemoveCardPrintHoldRes>"+
						"<status>"+removeCardPrintHoldRes_type0.getErrorCode()+"</status>"+
						"<reason>"+removeCardPrintHoldRes_type0.getErrorMessage()+"</reason>"+
						"</RemoveCardPrintHoldRes>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>RemoveCardPrintHold</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Remove CardPrintHold</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			Status="Failure";
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>RemoveCardPrintHold</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Remove CardPrintHold.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>RemoveCardPrintHold</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Remove CardPrintHold</td></Output>";
			}

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
			LogGEN.writeTrace("CBG_Log", inputXml);
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
				LogGEN.writeTrace("CBG_Log","Exception :"+e.getMessage());
				e.printStackTrace();
			}

			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","RemoveCardPrintHold : finally :"+Query);
			//					LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","Exception :"+e2.getMessage());
			}
			return sOutput;			
		}
	}
} 