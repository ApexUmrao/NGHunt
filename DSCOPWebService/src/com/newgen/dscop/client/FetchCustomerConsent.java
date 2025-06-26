package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCustomerConsentStub;
import com.newgen.dscop.stub.ModCustomerConsentStub.FetchCustomerConsentReqMsg;
import com.newgen.dscop.stub.ModCustomerConsentStub.FetchCustomerConsentReq_type0;
import com.newgen.dscop.stub.ModCustomerConsentStub.FetchCustomerConsentResMsg;
import com.newgen.dscop.stub.ModCustomerConsentStub.FetchCustomerConsentRes_type0;
import com.newgen.dscop.stub.ModCustomerConsentStub.HeaderType;


public class FetchCustomerConsent extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;

	@SuppressWarnings("finally")
	public String fetchCustomerConsentCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchCustomerConsent");
		LogGEN.writeTrace("CBG_Log", "FetchCustomerConsent sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDesc =  "";
		String sErrorDet="";
		String sErrorDes =  "";
		
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModCustomerConsent");
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModCustomerConsent TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String consentType =xmlDataParser.getValueOf("consentType");
			String consentMobileNumber = xmlDataParser.getValueOf("consentMobileNumber");
			
			ModCustomerConsentStub request_stub=new ModCustomerConsentStub(sWSDLPath);
			FetchCustomerConsentReqMsg reqMsg = new FetchCustomerConsentReqMsg();
			FetchCustomerConsentReq_type0 reqMsg_type0 = new FetchCustomerConsentReq_type0();
			FetchCustomerConsentResMsg response_msg = new FetchCustomerConsentResMsg();
			FetchCustomerConsentRes_type0 response_type0  = new FetchCustomerConsentRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("123656");
			Header_Input.setServiceName("ModCustomerConsent");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setCorrelationID(sHandler.setSenderId(xmlDataParser.getValueOf("CorrelationID")));
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			reqMsg_type0.setConsentType(consentType);
			reqMsg_type0.setConsentMobileNumber(consentMobileNumber);
		

			reqMsg.setFetchCustomerConsentReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);

			response_msg = request_stub.fetchCustomerConsent_Oper(reqMsg);
			xmlInput= request_stub.getInputXML_Fetch(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getFetchCustomerConsentRes();
			LogGEN.writeTrace("CBG_Log", "FetchCustomerConsent xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.resMsg;
			LogGEN.writeTrace("CBG_Log", "FetchCustomerConsentResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDet = Header_Input.getErrorDetail();
			sErrorDes = Header_Input.getErrorDescription();
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchCustomerConsent</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDes+"</errorDescription>"+
						"<errorDescription>"+sErrorDet+"</errorDescription>"+
						"<FetchCustomerConsentRes>";
						if(response_type0.getConsentDetails() != null && response_type0.getConsentDetails().length > 0)
						{
								sOutput =sOutput +"<ConsentDetails>" +
								"<CustomerId>"+response_type0.getConsentDetails()[0].getCustomerId()+"</CustomerId>"+
								"<ConsentMobileNumber>"+response_type0.getConsentDetails()[0].getConsentMobileNumber()+"</ConsentMobileNumber>" +
								"<ConsentEmailAddress>"+response_type0.getConsentDetails()[0].getConsentEmailAddress()+"</ConsentEmailAddress>"+
								"<ChannelValue>"+response_type0.getConsentDetails()[0].getChannelValue()+"</ChannelValue>" +
								"<DigitalId>"+response_type0.getConsentDetails()[0].getDigitalId()+"</DigitalId>"+
								"<Consent2SMS>"+response_type0.getConsentDetails()[0].getConsent2SMS()+"</Consent2SMS>" +
								"<Consent2Email>"+response_type0.getConsentDetails()[0].getConsent2Email()+"</Consent2Email>"+
								"<Consent2Phone>"+response_type0.getConsentDetails()[0].getConsent2Phone()+"</Consent2Phone>" +
								"<Consent2Post>"+response_type0.getConsentDetails()[0].getConsent2Post()+"</Consent2Post>"+
								"<ChannelValue>"+response_type0.getConsentDetails()[0].getChannelValue()+"</ChannelValue>" +
								"<Consent2OtherChannels>"+response_type0.getConsentDetails()[0].getConsent2OtherChannels()+"</Consent2OtherChannels>"+
								"<ConsentType>"+response_type0.getConsentDetails()[0].getConsentType()+"</ConsentType>" +
								"<ProspectId>"+response_type0.getConsentDetails()[0].getProspectId()+"</ProspectId>"+
								"<ProspectFirstName>"+response_type0.getConsentDetails()[0].getProspectFirstName()+"</ProspectFirstName>" +
								"<ProspectLastName>"+response_type0.getConsentDetails()[0].getProspectLastName()+"</ProspectLastName>"+
								"<TeleCallingConsent>"+response_type0.getConsentDetails()[0].getTeleCallingConsent()+"</TeleCallingConsent>" +
								"<UsersDepartment>"+response_type0.getConsentDetails()[0].getUsersDepartment()+"</UsersDepartment>"+
								"<UsersSubDepartment>"+response_type0.getConsentDetails()[0].getUsersSubDepartment()+"</UsersSubDepartment>" +
								"<InteractionType>"+response_type0.getConsentDetails()[0].getInteractionType()+"</InteractionType>"+
								"<Remarks>"+response_type0.getConsentDetails()[0].getRemarks()+"</Remarks>" +
								"<Status>"+response_type0.getConsentDetails()[0].getStatus()+"</Status>"+
								"<ProspectCreationDate>"+response_type0.getConsentDetails()[0].getProspectCreationDate()+"</ProspectCreationDate>" +
								"<ProspectCreatedBy>"+response_type0.getConsentDetails()[0].getProspectCreatedBy()+"</ProspectCreatedBy>"+
								"<ProspectModifiedDate>"+response_type0.getConsentDetails()[0].getProspectModifiedDate()+"</ProspectModifiedDate>" +
								"<ProspectModifiedBy>"+response_type0.getConsentDetails()[0].getProspectModifiedBy()+"</ProspectModifiedBy>"+
								"</ConsentDetails>";
						}
					sOutput = sOutput +"</FetchCustomerConsentRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to add customer consent</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add customer consent</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add customer consent</Output>";
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
			LogGEN.writeTrace("CBG_Log","FetchCustomerConsent  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchCustomerConsent  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}
	}
}