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
import com.newgen.dscop.stub.ModCustomerConsentStub.AddCustomerConsentReqMsg;
import com.newgen.dscop.stub.ModCustomerConsentStub.AddCustomerConsentReq_type0;
import com.newgen.dscop.stub.ModCustomerConsentStub.AddCustomerConsentResMsg;
import com.newgen.dscop.stub.ModCustomerConsentStub.AddCustomerConsentRes_type0;
import com.newgen.dscop.stub.ModCustomerConsentStub.HeaderType;


public class AddCustomerConsent extends DSCOPServiceHandler
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
	public String addCustomerConsentCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called AddCustomerConsent");
		LogGEN.writeTrace("CBG_Log", "AddCustomerConsent sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorCode="";
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
			String customerId = xmlDataParser.getValueOf("customerId");
			String consentUseData =xmlDataParser.getValueOf("consentUseData");
			String consentShareData =xmlDataParser.getValueOf("consentShareData");
			String sourcingChannel = xmlDataParser.getValueOf("sourcingChannel");
			String channelValue = xmlDataParser.getValueOf("channelValue");
			String consent2SMS =xmlDataParser.getValueOf("consent2SMS");
			String consent2Email =xmlDataParser.getValueOf("consent2Email");
			String consent2Phone = xmlDataParser.getValueOf("consent2Phone");
			String consent2Post = xmlDataParser.getValueOf("consent2Post");
			String consent2OtherChannels =xmlDataParser.getValueOf("consent2OtherChannels");
			String consentMobileNumber = xmlDataParser.getValueOf("consentMobileNumber");
			String consentEmailAddress =xmlDataParser.getValueOf("consentEmailAddress");
			
			String consentType =xmlDataParser.getValueOf("consentType");
			String prospectCreatedBy =xmlDataParser.getValueOf("prospectCreatedBy");
			String prospectCreationDate =xmlDataParser.getValueOf("prospectCreationDate");
			String prospectFirstName =xmlDataParser.getValueOf("prospectFirstName");
			String prospectLastName =xmlDataParser.getValueOf("prospectLastName");
			String prospectId =xmlDataParser.getValueOf("prospectId");
			String prospectModifiedBy =xmlDataParser.getValueOf("prospectModifiedBy");
			String prospectModifiedDate =xmlDataParser.getValueOf("prospectModifiedDate");
			String remarks = xmlDataParser.getValueOf("remarks");
			String passportNumber = xmlDataParser.getValueOf("passportNumber");
			String usersSubDepartment = xmlDataParser.getValueOf("usersSubDepartment");
			String userDepartment = xmlDataParser.getValueOf("userDepartment");
			String interactionType = xmlDataParser.getValueOf("interactionType");
			String status = xmlDataParser.getValueOf("status");
			String eidaNumber = xmlDataParser.getValueOf("eidaNumber");
			String agentId = xmlDataParser.getValueOf("agentId");
			
			String teleCallingConsent = xmlDataParser.getValueOf("teleCallingConsent");
			
			ModCustomerConsentStub request_stub=new ModCustomerConsentStub(sWSDLPath);
			AddCustomerConsentReqMsg reqMsg = new AddCustomerConsentReqMsg();
			AddCustomerConsentReq_type0 reqMsg_type0 = new AddCustomerConsentReq_type0();
			AddCustomerConsentResMsg response_msg = new AddCustomerConsentResMsg();
			AddCustomerConsentRes_type0 response_type0  = new AddCustomerConsentRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCustomerConsent");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			
			reqMsg_type0.setCustomerId(customerId);
			reqMsg_type0.setConsentUseData(consentUseData);
			reqMsg_type0.setConsentShareData(consentShareData);
			reqMsg_type0.setSourcingChannel(sourcingChannel);
			reqMsg_type0.setChannelValue(channelValue);
			reqMsg_type0.setConsent2SMS(consent2SMS);
			reqMsg_type0.setConsent2Email(consent2Email);
			reqMsg_type0.setConsent2Phone(consent2Phone);
			reqMsg_type0.setConsent2Post(consent2Post);
			reqMsg_type0.setConsent2OtherChannels(consent2OtherChannels);
			reqMsg_type0.setConsentMobileNumber(consentMobileNumber);
			reqMsg_type0.setConsentEmailAddress(consentEmailAddress);
			reqMsg_type0.setRemarks(remarks);
			
			reqMsg_type0.setPassportNumber(passportNumber);
			reqMsg_type0.setUsersSubDepartment(usersSubDepartment);
			reqMsg_type0.setUsersDepartment(userDepartment);
			reqMsg_type0.setInteractionType(interactionType);
			reqMsg_type0.setStatus(status);
			reqMsg_type0.setEidaNumber(eidaNumber);
			reqMsg_type0.setAgentId(agentId);
			reqMsg_type0.setConsentType(consentType);
			reqMsg_type0.setProspectCreatedBy(prospectCreatedBy);
			reqMsg_type0.setProspectCreationDate(prospectCreationDate);
			reqMsg_type0.setProspectFirstName(prospectFirstName);
			reqMsg_type0.setProspectLastName(prospectLastName);
			reqMsg_type0.setProspectId(prospectId);
			reqMsg_type0.setProspectModifiedBy(prospectModifiedBy);
			reqMsg_type0.setProspectModifiedDate(prospectModifiedDate);
			
			reqMsg_type0.setTeleCallingConsent(teleCallingConsent);
			
			reqMsg.setHeader(Header_Input);


			reqMsg.setAddCustomerConsentReq(reqMsg_type0);
			
			response_msg = request_stub.addCustomerConsent_Oper(reqMsg);
			xmlInput= request_stub.getinputXML(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getAddCustomerConsentRes();
			LogGEN.writeTrace("CBG_Log", "AddCustomerConsent xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.resMsg;
			LogGEN.writeTrace("CBG_Log", "AddCustomerConsentResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDet = Header_Input.getErrorDetail();
			sErrorDes = Header_Input.getErrorDescription();
			
			sErrorCode = response_type0.getErrorCode();
			sErrorDesc = response_type0.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddCustomerConsent</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDes+"</errorDescription>"+
						"<errorDescription>"+sErrorDet+"</errorDescription>"+
						"<AddCustomerConsentRes>"+
						"<errorCode>"+sErrorCode+"</errorCode>" +
						"<errorDescription>"+sErrorDesc+"</errorDescription>"+
						"</AddCustomerConsentRes>"+	
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
			LogGEN.writeTrace("CBG_Log","AddCustomerConsent  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","AddCustomerConsent  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}
	}
}