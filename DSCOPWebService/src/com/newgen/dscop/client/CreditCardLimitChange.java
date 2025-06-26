package com.newgen.dscop.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.HeaderType;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeReqMsg;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeReq_type0;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeResMsg;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeRes_type0;

public class CreditCardLimitChange extends DSCOPServiceHandler {

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String creditLimitChangeOutput="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";


	public String CreditLimitChange(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called CreditLimitChange");
		LogGEN.writeTrace("CBG_Log", "CreditCardLimitChange sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		String xmlInput="";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);	

		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdl=loadWSDLDtls(sHandler);
			String ref_no=xmlDataParser.getValueOf("Ref_No");
			String senderId = xmlDataParser.getValueOf("SenderId");
			ModCreditCardLimitChangeStub credit_card_limitchange_stub = new ModCreditCardLimitChangeStub(wsdl);
			ModCreditCardLimitChangeReqMsg req_msg = new ModCreditCardLimitChangeReqMsg();
			ModCreditCardLimitChangeReq_type0 req_type0 = new ModCreditCardLimitChangeReq_type0();

			ModCreditCardLimitChangeResMsg res_msg=new ModCreditCardLimitChangeResMsg();

			req_msg.setHeader(setHeaderDtls(sDate,ref_no,senderId));
			req_type0.setCardNumber(xmlDataParser.getValueOf("cardNumber"));
			req_type0.setCreditLimit(xmlDataParser.getValueOf("creditLimit"));
			req_type0.setIndicator(xmlDataParser.getValueOf("indicator"));
			req_type0.setSupCreditlimitPercentage(xmlDataParser.getValueOf("supCreditlimitPercentage"));


			req_msg.setModCreditCardLimitChangeReq(req_type0);
			credit_card_limitchange_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = credit_card_limitchange_stub.getInputXml(req_msg);
			//			LogGEN.writeTrace("CBG_Log", "CreditCardLimitChange InputXML: " + xmlInput);
			res_msg=credit_card_limitchange_stub.modCreditCardLimitChange_Oper(req_msg);
			creditLimitChangeOutput = credit_card_limitchange_stub.resCreditLimitChange;
			//			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change OutputXML: " + creditLimitChangeOutput);

			//			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change res_msg"+res_msg);
			HeaderType header=res_msg.getHeader();
			LogGEN.writeTrace("CBG_Log", "res_msg.getHeader()"+res_msg.getHeader());
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode()"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail()"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription()"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				ModCreditCardLimitChangeRes_type0 creditLimitChangeRes_type0=new ModCreditCardLimitChangeRes_type0();
				creditLimitChangeRes_type0=res_msg.getModCreditCardLimitChangeRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>Modify_CreditLimit</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<ModCreditCardLimitChangeRes>"+
						"<cardNumber>"+creditLimitChangeRes_type0.getCardNumber()+"</cardNumber>"+				
						"</ModCreditCardLimitChangeRes>"+	
						"</Output>";
				//				LogGEN.writeTrace("CBG_Log", "output xml within if block--------"+sOutput);

			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_CreditLimit</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify credit Limit </td></Output>";
			}

		}
		catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_CreditLimit</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify credit Limit  </td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_CreditLimit</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify credit limit </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
//			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),creditLimitChangeOutput.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}

	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("Credit_Card_Limit_Change");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Credit_Card_Limit_Change");
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Credit_Card_Limit_Change TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}

	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("101");
		headerType.setServiceName("ModCreditCardLimitChange");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
