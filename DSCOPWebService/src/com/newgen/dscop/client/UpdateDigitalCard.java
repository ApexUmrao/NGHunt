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
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.HeaderType;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReqMsg;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusReq_type0;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusResMsg;
import com.newgen.dscop.stub.ModCCDigitalCardDetailsStub.UpdateDigitalCardStatusRes_type0;

public class UpdateDigitalCard extends DSCOPServiceHandler  {

	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String cardonboarding_ouput = null;
	String updateDigitalCardStatus_output = null;

	@SuppressWarnings("finally")
	public String DigitalCardStatus(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called DigitalCardStatus");
//		LogGEN.writeTrace("CBG_Log", "UpdateDigitalCard sInputXML---"+sInputXML);
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
			LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING sDate---"+sDate);
			
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");

			ModCCDigitalCardDetailsStub card_onboarding_stub = new ModCCDigitalCardDetailsStub(sWSDLPath);
			UpdateDigitalCardStatusReqMsg updt_card_status_req_Msg = new UpdateDigitalCardStatusReqMsg();
			UpdateDigitalCardStatusResMsg res_msg = new UpdateDigitalCardStatusResMsg();
			UpdateDigitalCardStatusReq_type0 req_type0 = new UpdateDigitalCardStatusReq_type0();		

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCCDigitalCardDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID("CBG");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			req_type0.setCardNumber(xmlDataParser.getValueOf("cardNumber"));
			req_type0.setCardType(xmlDataParser.getValueOf("cardType"));

			updt_card_status_req_Msg.setHeader(Header_Input);
			updt_card_status_req_Msg.setUpdateDigitalCardStatusReq(req_type0);
			xmlInput = card_onboarding_stub.getInputXml(updt_card_status_req_Msg);
//			LogGEN.writeTrace("CBG_Log", "updateDigitalCard xmlInput: "+xmlInput);
			card_onboarding_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg = card_onboarding_stub.updateDigitalCardStatus_Oper(updt_card_status_req_Msg);
			updateDigitalCardStatus_output = card_onboarding_stub.updatedigitalcardMsg;
//			LogGEN.writeTrace("CBG_Log", "updateDigitalCard updateDigitalCardStatus_output: "+updateDigitalCardStatus_output);
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
				UpdateDigitalCardStatusRes_type0 res_type0=new UpdateDigitalCardStatusRes_type0();
				res_type0=res_msg.getUpdateDigitalCardStatusRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>DIGITAL_CARD_STATUS</Option>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
						"<updateDigitalCardStatusRes>" +
						"<cardNumber>"+ res_type0.getCardNumber() +"</cardNumber>" +
						"<status>"+ res_type0.getStatus() +"</status>" +
						"<reason>"+ res_type0.getReason() +"</reason>" +
						"</updateDigitalCardStatusRes>" +
						"</Output>";

//				LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);
			}
			else
			{
				UpdateDigitalCardStatusRes_type0 res_type0=new UpdateDigitalCardStatusRes_type0();
				res_type0=res_msg.getUpdateDigitalCardStatusRes();
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DIGITAL_CARD_STATUS</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>"+res_type0.getStatus()+"</Status><Reason>"+res_type0.getReason()+"</Reason><td>unable to update the digital card status.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DIGITAL_CARD_STATUS</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to update the digital card status.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DIGITAL_CARD_STATUS</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to update the digital card status.</td></Output>";
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
				e.getMessage();
			}
			DBConnection con=new DBConnection();
			
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," Card Onboarding NI Query : finally :"+Query);
//			LogGEN.writeTrace("CBG_Log","updateDigitalCardStatus_output : finally :"+updateDigitalCardStatus_output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),updateDigitalCardStatus_output.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
//			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
