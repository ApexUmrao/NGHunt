package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.axis2.transport.http.HTTPConstants;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.ApplicationAttributes;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.AttributeDetails;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.Attributes;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.CBGSingleHookRequest;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.CBGSingleHookResponse;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.SingleHook;
import com.newgen.dscop.stub.DSCOPSingleHookServiceStub.SingleHookResponse;


public class DSCOPInternalHookService extends DSCOPServiceHandler  {
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String singleHook_ouput = null;

	public String singleHook(String sInputXML)
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called singleHook");
		LogGEN.writeTrace("CBG_Log", "DSCOPInternalHookService sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput = "";
		String sReturnCode = "";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("SINGLE_HOOK");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("DSCOP_SINGLE_HOOK");
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=wsConfig.get(0);
			sCabinet=wsConfig.get(1);
			sUser=wsConfig.get(2);
			sLoginReq=Boolean.valueOf(wsConfig.get(3));
			sPassword=wsConfig.get(4);
			lTimeOut=3*Long.valueOf(wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK sDate---"+sDate);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");
			String WINAME = xmlDataParser.getValueOf("WI_NAME");
			String stage  = xmlDataParser.getValueOf("Stage");
			String ApplicationName = xmlDataParser.getValueOf("ApplicationName");
			String SourcingChannel = xmlDataParser.getValueOf("SourcingChannel");
			String SourcingCenter = xmlDataParser.getValueOf("SourcingCenter");
			String RequestDateTime = xmlDataParser.getValueOf("RequestDateTime");
			String MODE = xmlDataParser.getValueOf("MODE");
			String LeadNumber = xmlDataParser.getValueOf("LeadNumber");
			String Language = xmlDataParser.getValueOf("Language");
			String IP = xmlDataParser.getValueOf("IP");
			String DeviceID = xmlDataParser.getValueOf("DeviceID");
			String applicationVersion = xmlDataParser.getValueOf("ApplicationVersion"); //17/01/2024 - X TO M

			String ApplicationAttributes = xmlDataParser.getValueOf("applicationAttributes");
			XMLParser xp = new XMLParser(ApplicationAttributes);
			String AttributeDetails = xp.getValueOf("attributeDetails");
			xp = new XMLParser(AttributeDetails);
			xp.setInputXML(AttributeDetails);
			int noOfAttributes = xp.getNoOfFields("attributes");

			ApplicationAttributes[] applicationAttributes = new ApplicationAttributes[1];
			AttributeDetails[] attributeDetails = new AttributeDetails[1];
			Attributes[] ata = new Attributes[noOfAttributes];
			for (int i = 0; i < noOfAttributes; i++) {
				String sAttributes = xp.getNextValueOf("attributes");
				XMLParser xp2 = new XMLParser();
				xp2.setInputXML(sAttributes);
				Attributes attributes =new Attributes();
				String key = xp2.getValueOf("attributeKey");
				String Value = xp2.getValueOf("attributeValue");
				attributes.setAttributeKey(key);
				attributes.setAttributeValue(Value);
				ata[i] = attributes;
			}

			AttributeDetails ad = new AttributeDetails();
			ad.setAttributes(ata);
			attributeDetails[0] = ad;

			ApplicationAttributes aa = new ApplicationAttributes();
			aa.setAttributeDetails(attributeDetails);
			applicationAttributes[0] = aa;

			DSCOPSingleHookServiceStub singleHook_stub = new DSCOPSingleHookServiceStub(sWSDLPath);
			CBGSingleHookRequest singleHook_Req_Msg = new CBGSingleHookRequest();
			//CBGSingleHookResponse res_msg = new CBGSingleHookResponse();
			SingleHook sSinglehook = new SingleHook();
			SingleHookResponse sSinglehook_res = new SingleHookResponse();

			singleHook_Req_Msg.setSYSREFNO(ref_no);
			singleHook_Req_Msg.setWI_NAME(WINAME);
			singleHook_Req_Msg.setStage(stage);
			singleHook_Req_Msg.setApplicationAttributes(applicationAttributes);
			singleHook_Req_Msg.setApplicationName(ApplicationName);
			singleHook_Req_Msg.setDeviceID(DeviceID);
			singleHook_Req_Msg.setIP(IP);
			singleHook_Req_Msg.setLanguage(Language);
			singleHook_Req_Msg.setLeadNumber(LeadNumber);
			singleHook_Req_Msg.setMODE(MODE);
			singleHook_Req_Msg.setRequestDateTime(RequestDateTime);
			singleHook_Req_Msg.setSourcingCenter(SourcingCenter);
			singleHook_Req_Msg.setSourcingChannel(SourcingChannel);
			singleHook_Req_Msg.setApplicationVersion(applicationVersion); //17/01/2024 - X TO M

			sSinglehook.setRequest(singleHook_Req_Msg);

			xmlInput = singleHook_stub.getInputXml(sSinglehook);
			LogGEN.writeTrace("CBG_Log", "winame "+WINAME+" singleHook InputXML: " + xmlInput);
			singleHook_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			singleHook_stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, (int)lTimeOut);
			singleHook_stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, (int)lTimeOut);
			sSinglehook_res = singleHook_stub.singleHook(sSinglehook);
			singleHook_ouput = singleHook_stub.singleHookMsg;
			LogGEN.writeTrace("CBG_Log", "singleHook_ouput: " + singleHook_ouput);
			CBGSingleHookResponse res_msg = sSinglehook_res.get_return();
			sReturnCode = res_msg.getStatusCode();
			String StatusMessage = res_msg.getStatusMessage();
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				LogGEN.writeTrace("CBG_Log", "Successful Result: "+StatusMessage);
				//SingleHookResponse res=new SingleHookResponse();


				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>SINGLEHOOK</Option>" +
						"<StatusCode>"+ res_msg.getStatusCode() +"</StatusCode>" +
						"<StatusMessage>"+  res_msg.getStatusMessage() +"</StatusMessage>";
				sOutput+="<ApplicationName>"+  res_msg.getApplicationName() + "</ApplicationName>" +
						"<Language>"+ res_msg.getLanguage() + "</Language>" +
						"<LeadNumber>"+ res_msg.getLeadNumber() +"</LeadNumber>"+
						"<Stage>"+ res_msg.getStage() +"</Stage>"+
						"<WI_NAME>"+ res_msg.getWI_NAME() +"</WI_NAME>"+
						"<WSName>"+ res_msg.getWSName() +"</WSName>"+
						"<ApplicationAttributes>"+ res_msg.getApplicationAttributes() +"</ApplicationAttributes>";
				sOutput += "</Output>";
				LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK Output XML--- "+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>SINGLEHOOK</Option><StatusCode>"+res_msg.getStatusCode()+"</StatusCode>"
						+ "<StatusMessage>"+ res_msg.getStatusMessage()+"</StatusMessage>"
						+ "<td>Error.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>SINGLEHOOK</Option><StatusCode>-1</StatusCode>"
					+ "<errorDescription>"+"Error in Web Serviice"+"</errorDescription><td>Error.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","SINGLE_HOOK outputXML.trim().length() :"+sOutput.trim().length());
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>SINGLEHOOK</Option><StatusCode>-1</StatusCode>"
						+ "<errorDescription>"+"Error in Web Serviice"+"</errorDescription><td>Error.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";


			String dburl=currentCabPropertyMap.get("DBURL");
			String dbuser=currentCabPropertyMap.get("USER");
			String dbpass=currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WI_NAME");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			LogGEN.writeTrace("CBG_Log", "winame "+winame);
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK Error in Web Serviice :"+e.toString());
			}
			DBConnection con=new DBConnection();
			/**
			 * //Added By Akash For inserting original mssg
			 * Date			:	3 Apr 2018
			 * Description 	:	Replace execute with executeClob
			 */
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," SINGLEHOOK Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","singleHook_ouput : finally :"+singleHook_ouput);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),singleHook_ouput.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
		}
		return sOutput;
	}
}
