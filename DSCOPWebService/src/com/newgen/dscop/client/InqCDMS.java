package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCompanyDetailsStub;
import com.newgen.dscop.stub.InqCompanyDetailsStub.CompanyDetails_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.Company_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.HeaderType;
import com.newgen.dscop.stub.InqCompanyDetailsStub.InqCompanyDetailsReqChoice_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.InqCompanyDetailsReqMsg;
import com.newgen.dscop.stub.InqCompanyDetailsStub.InqCompanyDetailsReq_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.InqCompanyDetailsResMsg;
import com.newgen.dscop.stub.InqCompanyDetailsStub.InqCompanyDetailsRes_type0;


public class InqCDMS extends DSCOPServiceHandler{
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String getCompanyMasterDetails_output = "";

	public String InqCDMS(String sInputXML)
	{
		LogGEN.writeTrace("CBG_Log", "InqCDMS Fuction called inq_company_details");
		LogGEN.writeTrace("CBG_Log", "InqCDMS sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput = "";
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		String reqCompanyName = "";
		String reqCompanyCode = "";
		try
		{
			//sHandler.readCabProperty("inq_company_details");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("inq_company_details");
			LogGEN.writeTrace("CBG_Log", "InqCDMS WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCDMS WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCDMS CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCDMS USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCDMS PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCDMS LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCDMS TIME_OUT: "+lTimeOut);			
			LogGEN.writeTrace("CBG_Log", "InqCDMS sDate---"+sDate);
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");

			InqCompanyDetailsStub inq_companyDtl_stub = new InqCompanyDetailsStub(sWSDLPath);
			InqCompanyDetailsReqMsg req_msg = new InqCompanyDetailsReqMsg();
			InqCompanyDetailsReq_type0 req_type_0 = new InqCompanyDetailsReq_type0();
			InqCompanyDetailsReqChoice_type0 req_msg_type0 = new InqCompanyDetailsReqChoice_type0();
			InqCompanyDetailsResMsg res_msg=new InqCompanyDetailsResMsg();
			InqCompanyDetailsRes_type0 res_type0 = new InqCompanyDetailsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCompanyDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);		
			Header_Input.setUsername(loggedinuser);

			req_msg.setHeader(Header_Input);
			reqCompanyName = xmlDataParser.getValueOf("companyName");
			reqCompanyCode = xmlDataParser.getValueOf("companyCode");
			
			if(("").equalsIgnoreCase(reqCompanyCode)) {
				req_msg_type0.setCompanyName(reqCompanyName);
			} else {
				req_msg_type0.setCompanyCode(reqCompanyCode);
			}
			//COLMP-7851|RISHABH|07052024 START
			req_type_0.setNtmlFlg(xmlDataParser.getValueOf("ntmlFlag"));
			req_type_0.setPortalFlag(xmlDataParser.getValueOf("portalFlag"));
			req_type_0.setWpsCode(xmlDataParser.getValueOf("wpsCode"));
			//COLMP-7851|RISHABH|07052024 END
//			req_msg_type0.setCompanyName(xmlDataParser.getValueOf("companyName"));
//			req_msg_type0.setCompanyCode(xmlDataParser.getValueOf("companyCode"));
			req_type_0.setInqCompanyDetailsReqChoice_type0(req_msg_type0);
			
			req_msg.setInqCompanyDetailsReq(req_type_0);
			
			xmlInput = inq_companyDtl_stub.getCompanyMasterInputXml(req_msg);
			LogGEN.writeTrace("CBG_Log", "InqCDMS InputXML: " + xmlInput);
			inq_companyDtl_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg=inq_companyDtl_stub.inqCompanyDetails_Oper(req_msg);
			getCompanyMasterDetails_output = inq_companyDtl_stub.res_getCompanyMasterDetails;
			sOutput = getCompanyMasterDetails_output;
			LogGEN.writeTrace("CBG_Log", "InqCDMS res_msg: "+getCompanyMasterDetails_output);
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "InqCDMS Return Code: "+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "InqCDMS Error Detail: "+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "InqCDMS Error Description: "+sErrorDesc);

			res_type0=res_msg.getInqCompanyDetailsRes();

			StringBuilder details = new StringBuilder();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				CompanyDetails_type0  company_Details_type0 = res_type0.getCompanyDetails();

				if(company_Details_type0 != null){

					Company_type0[] Company = company_Details_type0.getCompany();

					for(Company_type0 Comp : Company) {

						String companyName = Comp.getCompanyName();
						String companyCode= Comp.getCompanyCode();

						details.append("<company>").append("\n")
						.append("<companyName>"+companyName+"</companyName>").append("\n")
						.append("<companyCode>"+companyCode+"</companyCode>").append("\n")
						.append("</company>").append("\n");
					}
				}

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + 
						"<Output>"+
						"<Option>inq_company_details</Option>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDetail>"+ sErrorDetail +"</errorDetail>" +
						"<companyDetails>" +
						details+
						"</companyDetails>" +
						"</Output>";					

				LogGEN.writeTrace("CBG_Log", "InqCDMS Wrapper Output XML: "+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>inq_company_details</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to fetch company details</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "InqCDMS Error in Web Service :"+e.toString());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>inq_company_details</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to fetch company details</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","InqCDMS outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>inq_company_details</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to fetch company details</td></Output>";
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
			LogGEN.writeTrace("CBG_Log","GetCompanyMasterList:"+ inputXml);
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
			LogGEN.writeTrace("CBG_Log"," GetCompanyMasterList Query : finally :"+Query);

			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOutput.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","InqCDMS outputXML : finally :"+sOutput);
		}
		return sOutput;			
	}
}
