package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeReq_type0;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeReqMsg;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeRes_type0;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.ModCreditCardLimitChangeResMsg;
import com.newgen.dscop.stub.ModCreditCardLimitChangeStub.HeaderType;


public class ModCreditCardLimitChange  extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	String xmlInput="";
	
	public String ModCreditCardLimitChange(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called ModCreditCardLimitChange");
		LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  ""; 
		String sOutput= "";
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModCreditCardLimitChange");
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String cardNumber=xmlDataParser.getValueOf("cardNumber");
			String creditLimit=xmlDataParser.getValueOf("creditLimit");
			String indicator=xmlDataParser.getValueOf("indicator");
			String supCreditlimitPercentage=xmlDataParser.getValueOf("supCreditlimitPercentage");

			ModCreditCardLimitChangeStub modCreditstub=new ModCreditCardLimitChangeStub(sWSDLPath);
			ModCreditCardLimitChangeReq_type0 fetchCredLimitReq=new ModCreditCardLimitChangeReq_type0();
			ModCreditCardLimitChangeReqMsg fetchCredLimitReqMsg=new ModCreditCardLimitChangeReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("101");
			Header_Input.setServiceName("ModCreditCardLimitChange");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			
			LogGEN.writeTrace("CBG_Log", "All values set ModCreditCardLimitChange");

			fetchCredLimitReqMsg.setHeader(Header_Input);
			fetchCredLimitReq.setCardNumber(cardNumber);
			fetchCredLimitReq.setCreditLimit(creditLimit);
			fetchCredLimitReq.setIndicator(indicator);
			fetchCredLimitReq.setSupCreditlimitPercentage(supCreditlimitPercentage);

			fetchCredLimitReqMsg.setModCreditCardLimitChangeReq(fetchCredLimitReq);
			modCreditstub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=modCreditstub.getInputXml(fetchCredLimitReqMsg);
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange InputXML: " + xmlInput);
			
			ModCreditCardLimitChangeResMsg res_msg= modCreditstub.modCreditCardLimitChange_Oper(fetchCredLimitReqMsg);
			sOrg_Output=modCreditstub.resCreditLimitChange;
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange res_msg: "+sOrg_Output);
			Header_Input=res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				
				ModCreditCardLimitChangeRes_type0 resType0 = new ModCreditCardLimitChangeRes_type0();
				resType0 = res_msg.getModCreditCardLimitChangeRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>ModCreditCardLimitChange</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<ModCreditCardLimitChangeRes>"+
						"<cardNumber>"+ resType0.getCardNumber() +"</cardNumber>"+
						"<transactionRefId>"+ resType0.getTransactionRefId()+"</transactionRefId>"+
						"<status>"+ resType0.getStatus()+"</status>"+
						"<reason>"+ resType0.getReason() +"</reason>"+
						"</ModCreditCardLimitChangeRes>"+
						"</Response>"+
						"</Output>";

			}
			else
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCreditCardLimitChange</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to update Credit Card Limit</Status></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviic ModCreditCardLimitChange :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice ModCreditCardLimitChange :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCreditCardLimitChange</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to update Credit Card Limit</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() ModCreditCardLimitChange:"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() ModCreditCardLimitChange:"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCreditCardLimitChange</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to update Credit Card Limit</Status></Output>";
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
			LogGEN.writeTrace("CBG_Log", "ModCreditCardLimitChange" + inputXml);
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
				e.printStackTrace();
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","ModCreditCardLimitChange Query : finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sOutput;		
	}
}


