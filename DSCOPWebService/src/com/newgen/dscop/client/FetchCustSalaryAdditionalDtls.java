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
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryAdditionalDtlsReqMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryAdditionalDtlsReq_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryAdditionalDtlsResMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchCustSalaryAdditionalDtlsRes_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.SalaryAdditionalDtls_type0;

public class FetchCustSalaryAdditionalDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchCustSalaryAdditionalDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchCustSalaryAdditionalDtls");
		LogGEN.writeTrace("CBG_Log", "FetchCustSalaryAdditionalDtls sInputXML---");
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
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryAdditionalDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");
			String accountNumber = xmlDataParser.getValueOf("accountNumber");

			InqCustomerDigitalLendingStub request_stub=new InqCustomerDigitalLendingStub(sWSDLPath);
			FetchCustSalaryAdditionalDtlsReqMsg fetch_camp_dtls = new FetchCustSalaryAdditionalDtlsReqMsg();			
			FetchCustSalaryAdditionalDtlsReq_type0 fetch_camp_type0 = new FetchCustSalaryAdditionalDtlsReq_type0();
			FetchCustSalaryAdditionalDtlsResMsg fetch_camp_response = new FetchCustSalaryAdditionalDtlsResMsg();
			FetchCustSalaryAdditionalDtlsRes_type0 fetch_camp_res_type0 = new FetchCustSalaryAdditionalDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerDigitalLending");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			
			fetch_camp_type0.setCustomerId(customerID);
			fetch_camp_type0.setAccountNumber(accountNumber);
			fetch_camp_dtls.setFetchCustSalaryAdditionalDtlsReq(fetch_camp_type0);
			fetch_camp_dtls.setHeader(Header_Input);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			fetch_camp_response = request_stub.fetchCustSalaryAdditionalDtls_Oper(fetch_camp_dtls);

			xmlInput= request_stub.getInputXMLAddSalaryDtls(fetch_camp_dtls);
			Header_Input = fetch_camp_response.getHeader();
			fetch_camp_res_type0 = fetch_camp_response.getFetchCustSalaryAdditionalDtlsRes();
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryAdditionalDtls xmlInput xml : "+xmlInput);
			
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchCustSalaryAdditionalDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(fetch_camp_res_type0 != null){
					SalaryAdditionalDtls_type0 salaryAdditionalDtls[] = fetch_camp_res_type0.getSalaryAdditionalDtls();
					if(salaryAdditionalDtls != null){
						for(SalaryAdditionalDtls_type0 detailsType0 : salaryAdditionalDtls) {
							String transactionReferenceNo = detailsType0.getTransactionReferenceNo();
							String paymentAmount = detailsType0.getPaymentAmount();
							String paymentValueDate = detailsType0.getPaymentValueDate();
							String paymentCurrency = detailsType0.getPaymentCurrency();
							String exRate = detailsType0.getExRate();
							String debitAccountNo = detailsType0.getDebitAccountNo();
							String orderCustomerName = detailsType0.getOrderCustomerName();
							String orderCustomerAddress1 = detailsType0.getOrderCustomerAddress1();
							String orderCustomerAddress2 = detailsType0.getOrderCustomerAddress2();
							String orderCustomerAddress3 = detailsType0.getOrderCustomerAddress3();
							String beneficiaryName = detailsType0.getBeneficiaryName();
							String beneficiaryAccountNo = detailsType0.getBeneficiaryAccountNo();
							String remittanceInfo1 = detailsType0.getRemittanceInfo1();
							String remittanceInfo2 = detailsType0.getRemittanceInfo2();
							String remittanceInfo3 = detailsType0.getRemittanceInfo3();
							String remittanceInfo4 = detailsType0.getRemittanceInfo4();
							String transactionType = detailsType0.getTransactionType();
							String processDateTime = detailsType0.getProcessDateTime();
							String iban = detailsType0.getIban();
							String transactionCode = detailsType0.getTransactionCode();
							String customerIdentificationCode = detailsType0.getCustomerIdentificationCode();
							String beneficiaryCustomerId = detailsType0.getBeneficiaryCustomerId();

							details.append("<salaryAdditionalDtls>").append("\n")
							.append("<transactionReferenceNo>"+transactionReferenceNo+"</transactionReferenceNo>").append("\n")
							.append("<paymentAmount>"+paymentAmount+"</paymentAmount>").append("\n")
							.append("<paymentValueDate>"+paymentValueDate+"</paymentValueDate>").append("\n")
							.append("<paymentCurrency>"+paymentCurrency+"</paymentCurrency>").append("\n")
							.append("<exRate>"+exRate+"</exRate>").append("\n")
							.append("<debitAccountNo>"+debitAccountNo+"</debitAccountNo>").append("\n")
							.append("<orderCustomerName>"+orderCustomerName+"</orderCustomerName>").append("\n")
							.append("<orderCustomerAddress1>"+orderCustomerAddress1+"</orderCustomerAddress1>").append("\n")
							.append("<orderCustomerAddress2>"+orderCustomerAddress2+"</orderCustomerAddress2>").append("\n")
							.append("<orderCustomerAddress3>"+orderCustomerAddress3+"</orderCustomerAddress3>").append("\n")
							.append("<beneficiaryName>"+beneficiaryName+"</beneficiaryName>").append("\n")
							.append("<beneficiaryAccountNo>"+beneficiaryAccountNo+"</beneficiaryAccountNo>").append("\n")
							.append("<remittanceInfo1>"+remittanceInfo1+"</remittanceInfo1>").append("\n")
							.append("<remittanceInfo2>"+remittanceInfo2+"</remittanceInfo2>").append("\n")
							.append("<remittanceInfo3>"+remittanceInfo3+"</remittanceInfo3>").append("\n")
							.append("<remittanceInfo4>"+remittanceInfo4+"</remittanceInfo4>").append("\n")
							.append("<transactionType>"+transactionType+"</transactionType>").append("\n")
							.append("<processDateTime>"+processDateTime+"</processDateTime>").append("\n")
							.append("<iban>"+iban+"</iban>").append("\n")
							.append("<transactionCode>"+transactionCode+"</transactionCode>").append("\n")
							.append("<customerIdentificationCode>"+customerIdentificationCode+"</customerIdentificationCode>").append("\n")
							.append("<beneficiaryCustomerId>"+beneficiaryCustomerId+"</beneficiaryCustomerId>").append("\n")
							.append("</salaryAdditionalDtls>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchCustSalaryAdditionalDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDesc+"</errorDescription>"+
						"<errorDetail>"+sErrorDetail+"</errorDetail>"+
						"<fetchCustSalaryAdditionalDtlsRes>"+
						details+
						"</fetchCustSalaryAdditionalDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustSalaryAdditionalDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to fetch customerCustomer Additional Salary Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustSalaryAdditionalDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customerCustomer Additional Salary Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustSalaryAdditionalDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customerCustomer Additional Salary Details</Output>";
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
			LogGEN.writeTrace("CBG_Log","FetchCustSalaryAdditionalDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchCustSalaryAdditionalDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}