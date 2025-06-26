package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddDigiCompanyDtlsStub;
import com.newgen.dscop.stub.AddDigiCompanyDtlsStub.CreateCompanyNotificationResMsg;
import com.newgen.dscop.stub.AddDigiCompanyDtlsStub.CreateCompanyNotificationRes_type0;
import com.newgen.dscop.stub.AddDigiCompanyDtlsStub.HeaderType;
import com.newgen.dscop.stub.AddDigiCompanyDtlsStub.CreateCompanyNotificationReqMsg;
import com.newgen.dscop.stub.AddDigiCompanyDtlsStub.CreateCompanyNotificationReq_type0;

public class AddDigiCompanyDtls  extends DSCOPServiceHandler
{	
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	String xmlInput="";
	@SuppressWarnings("finally")
	public String addDigiCompanyCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called AddDigiCompany");
		LogGEN.writeTrace("CBG_Log", "AddDigiCompany sInputXML---"+sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddDigiCompanyDtls");
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls WebServiceConfig Map : "  +wsConfig.toString());
			
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls TIME_OUT: "+lTimeOut);
			
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String operationName = xmlDataParser.getValueOf("operationName");
			String companyName = xmlDataParser.getValueOf("companyName"); 
			String companyCode = xmlDataParser.getValueOf("companyCode"); 
			String companyTradeLicenseNo = xmlDataParser.getValueOf("companyTradeLicenseNo");
			String companyWebsite = xmlDataParser.getValueOf("companyWebsite"); 
			String companyType = xmlDataParser.getValueOf("companyType"); 
			String companyPhone = xmlDataParser.getValueOf("companyPhone"); 
			String companyAddress = xmlDataParser.getValueOf("companyAddress"); 
			String companyCity = xmlDataParser.getValueOf("companyCity"); 
			String companyEmirate = xmlDataParser.getValueOf("companyEmirate"); 
			String companyFax = xmlDataParser.getValueOf("companyFax"); 
			String companyPOBox = xmlDataParser.getValueOf("companyPOBox");
			String companyContactPerson = xmlDataParser.getValueOf("companyContactPerson"); 
			String companyCreatedDate = xmlDataParser.getValueOf("companyCreatedDate"); 
			String companyTotalNoOfEmployees = xmlDataParser.getValueOf("companyTotalNoOfEmployees");
			String companyPartners = xmlDataParser.getValueOf("companyPartners");
			
			AddDigiCompanyDtlsStub add_digi_company_stub = new AddDigiCompanyDtlsStub(sWSDLPath);
			CreateCompanyNotificationReqMsg create_company_req_msg = new CreateCompanyNotificationReqMsg();
			CreateCompanyNotificationReq_type0 create_company_req_type0 = new CreateCompanyNotificationReq_type0();
			
			HeaderType Header_Input = new HeaderType();

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddDigiCompanyDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));//WSUSER_SRC //TODO : Need To Confirm 
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("WSUSER_SRC");
			Header_Input.setCredentials("TIBCO");

			LogGEN.writeTrace("CBG_Log", "All values set11");

			create_company_req_msg.setHeader(Header_Input);
			
			create_company_req_type0.setOperationName(operationName);
			create_company_req_type0.setCompanyName(companyName);
			create_company_req_type0.setCompanyCode(companyCode);
			create_company_req_type0.setCompanyTradeLicenseNo(companyTradeLicenseNo);
			create_company_req_type0.setCompanyWebsite(companyWebsite);
			create_company_req_type0.setCompanyType(companyType);
			create_company_req_type0.setCompanyPhone(companyPhone);
			create_company_req_type0.setCompanyAddress(companyAddress);
			create_company_req_type0.setCompanyCity(companyCity);
			create_company_req_type0.setCompanyEmirate(companyEmirate);
			create_company_req_type0.setCompanyFax(companyFax);
			create_company_req_type0.setCompanyPOBox(companyPOBox);
			create_company_req_type0.setCompanyContactPerson(companyContactPerson);
			create_company_req_type0.setCompanyCreatedDate(companyCreatedDate);
			create_company_req_type0.setCompanyTotalNoOfEmployees(companyTotalNoOfEmployees);
			create_company_req_type0.setCompanyPartners(companyPartners);
			
			create_company_req_msg.setCreateCompanyNotificationReq(create_company_req_type0);
			add_digi_company_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			CreateCompanyNotificationResMsg create_company_res_msg = new CreateCompanyNotificationResMsg();
			CreateCompanyNotificationRes_type0 create_company_res_type0 = new CreateCompanyNotificationRes_type0();
		
			xmlInput= add_digi_company_stub.getInputXML(create_company_req_msg);
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtlsRequest xmlInput xml : "+xmlInput);
			
			create_company_res_msg= add_digi_company_stub.createCompanyNotification_Oper(create_company_req_msg);
			
			sOrg_Output=add_digi_company_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtlsRequestResMsg sOrg_put: "+sOrg_Output);
			
			Header_Input=create_company_res_msg.getHeader();
			sReturnCode=  Header_Input. getReturnCode();
			sErrorDetail= Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddDigiCompanyDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddDigiCompanyDtlsRes>"+
						"<UID>"+ create_company_res_type0.getUID() +"</UID>"+
						"<status>"+ create_company_res_type0.getStatus() +"</status>"+
						"<reason>"+ create_company_res_type0.getReason() +"</reason>"+
				        "</AddDigiCompanyDtlsRes>"+	
						"</Output>";
			}
			else
			{
				try
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigiCompanyDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add digital company details </Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigiCompanyDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to add digital company details</Status></Output>";

				}
			}				
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigiCompanyDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add digital company details</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigiCompanyDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add digital company details</Status></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
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
			LogGEN.writeTrace("CBG_Log","AddDigiCompany Query : finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}
