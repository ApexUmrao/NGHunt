package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqBankCodeDtlsStub;
import com.newgen.dscop.stub.InqBankCodeDtlsStub.BankDetails_type0;
import com.newgen.dscop.stub.InqBankCodeDtlsStub.HeaderType;
import com.newgen.dscop.stub.InqBankCodeDtlsStub.InqBankCodeDtlsReqMsg;
import com.newgen.dscop.stub.InqBankCodeDtlsStub.InqBankCodeDtlsReq_type0;
import com.newgen.dscop.stub.InqBankCodeDtlsStub.InqBankCodeDtlsResMsg;
import com.newgen.dscop.stub.InqBankCodeDtlsStub.InqBankCodeDtlsRes_type0;

public class InqBankCodeDtls extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqBankCodeDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqBankCodeDtls");
		LogGEN.writeTrace("CBG_Log", "inqBankCodeDtls sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqBankCodeDtls");
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String countryCode = xmlDataParser.getValueOf("countryCode");
			String IBAN = xmlDataParser.getValueOf("IBAN");

			InqBankCodeDtlsStub request_stub=new InqBankCodeDtlsStub(sWSDLPath);
			InqBankCodeDtlsReqMsg req_msg = new InqBankCodeDtlsReqMsg();
			InqBankCodeDtlsReq_type0 reqMsg_type0 = new InqBankCodeDtlsReq_type0();
			InqBankCodeDtlsResMsg response_msg = new InqBankCodeDtlsResMsg();
			InqBankCodeDtlsRes_type0 response_type0  = new InqBankCodeDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqBankCodeDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			reqMsg_type0.setCountryCode(countryCode);
			reqMsg_type0.setIBAN(IBAN);
			req_msg.setInqBankCodeDtlsReq(reqMsg_type0);
			req_msg.setHeader(Header_Input);

			response_msg = request_stub.inqBankCodeDtls_Oper(req_msg);
			xmlInput= request_stub.getInputXML(req_msg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getInqBankCodeDtlsRes();
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqBankCodeDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			BankDetails_type0[] bank_Details = response_type0.getBankDetails();

			StringBuilder bank_Detail = new StringBuilder(); 
			if(bank_Details.length>0){
				for(BankDetails_type0 detailsType0 : bank_Details) {
					String isoCountryCode = detailsType0.getIsoCountryCode();
					String bankCode = detailsType0.getBankCode();
					String institutionNameAbbr = detailsType0.getInstitutionNameAbbr();
					String institutionNameFull = detailsType0.getInstitutionNameFull();
					String branchName = detailsType0.getBranchName();
					String physicalAddress1 = detailsType0.getPhysicalAddress1();
					String physicalAddress2 = detailsType0.getPhysicalAddress2();
					String countryName = detailsType0.getCountryName();
					String swiftBICCode = detailsType0.getSwiftBICCode();
					String uniqueId = detailsType0.getUniqueId();

					bank_Detail.append("<bankDetails>").append("\n")
					.append("<isoCountryCode>"+isoCountryCode+"</isoCountryCode>").append("\n")
					.append("<bankCode>"+bankCode+"</bankCode>").append("\n")
					.append("<institutionNameAbbr>"+institutionNameAbbr+"</institutionNameAbbr>").append("\n")
					.append("<institutionNameFull>"+institutionNameFull+"</institutionNameFull>").append("\n")
					.append("<branchName>"+branchName+"</branchName>").append("\n")
					.append("<physicalAddress1>"+physicalAddress1+"</physicalAddress1>").append("\n")
					.append("<physicalAddress2>"+physicalAddress2+"</physicalAddress2>").append("\n")
					.append("<countryName>"+countryName+"</countryName>").append("\n")
					.append("<swiftBICCode>"+swiftBICCode+"</swiftBICCode>").append("\n")
					.append("<uniqueId>"+uniqueId+"</uniqueId>").append("\n")
					.append("</bankDetails>").append("\n");
				}
			}

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqBankCodeDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<inqBankCodeDtlsRes>"+
						bank_Detail+
						"</inqBankCodeDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Inq_BankCode_Details</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquire Bank code details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Inq_BankCode_Details</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquire Bank code details</Output>";
			e.printStackTrace();
		}

		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Inq_BankCode_Details</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquire Bank code details</Output>";
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
			LogGEN.writeTrace("CBG_Log","InqBankCodeDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqBankCodeDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}

