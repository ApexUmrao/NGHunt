package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryDtlsReqMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryDtlsReq_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryDtlsResMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryDtlsRes_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.SalaryDtls_type0;

public class FetchCustSalaryDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchCustSalaryDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchCustSalaryDtls");
		LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerDigitalLending");
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");
			String numberOfMonth = xmlDataParser.getValueOf("numberOfMonth");

			InqCustomerDigitalLendingStub request_stub=new InqCustomerDigitalLendingStub(sWSDLPath);
			FetchCustSalaryDtlsReqMsg salary_req_dtls = new FetchCustSalaryDtlsReqMsg();			
			FetchCustSalaryDtlsReq_type0 salary_req_type0 = new FetchCustSalaryDtlsReq_type0();
			FetchCustSalaryDtlsResMsg salaryResponse = new FetchCustSalaryDtlsResMsg();
			FetchCustSalaryDtlsRes_type0 salary_res_type0 = new FetchCustSalaryDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerDigitalLending");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			salary_req_type0.setCustomerId(customerID);
			salary_req_type0.setNumberOfMonth(numberOfMonth);
			salary_req_dtls.setFetchCustSalaryDtlsReq(salary_req_type0);
			salary_req_dtls.setHeader(Header_Input);
			salaryResponse = request_stub.fetchCustSalaryDtls_Oper(salary_req_dtls);

			xmlInput= request_stub.getInputXmlSalary(salary_req_dtls);
			Header_Input = salaryResponse.getHeader();
			salary_res_type0 = salaryResponse.getFetchCustSalaryDtlsRes();
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(salary_res_type0 != null){
					SalaryDtls_type0[] salary_details = salary_res_type0.getSalaryDtls();
					if(salary_details != null){
						for(SalaryDtls_type0 detailsType0 : salary_details) {
							String custID = detailsType0.getCustomerId();
							String misDate = detailsType0.getMisDate();
							String salaryAmount = detailsType0.getSalaryAmount();
							String salaryDate = detailsType0.getSalaryDate();
							String transDes = detailsType0.getTransactionDescription();
							String errorDes = detailsType0.getErrorDescription();
							String accountNumber = detailsType0.getAccountNumber();

							if(errorDes==null )
								errorDes = "";

							details.append("<salaryDtls>").append("\n")
							.append("<customerID>"+custID+"</customerID>").append("\n")
							.append("<salaryAmount>"+salaryAmount+"</salaryAmount>").append("\n")
							.append("<salaryDate>"+salaryDate+"</salaryDate>").append("\n")
							.append("<misDate>"+misDate+"</misDate>").append("\n")
							.append("<transactionDescription>"+transDes+"</transactionDescription>").append("\n")
							.append("<errorDescription>"+errorDes+"</errorDescription>").append("\n")
							.append("<accountNumber>"+accountNumber+"</accountNumber>").append("\n")
							.append("</salaryDtls>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchCustSalaryDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<FetchCustSalaryDtlsRes>"+
						details+
						"</FetchCustSalaryDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustSalaryDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to fetch customer salary details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customer salary details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customer salary details</Output>";
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
			LogGEN.writeTrace("CBG_Log","FetchCustSalaryDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchCustSalaryDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

