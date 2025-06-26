package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchDDSPaymentInfoReqMsg;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchDDSPaymentInfoReq_type0;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchDDSPaymentInfoResMsg;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.FetchDDSPaymentInfoRes_type0;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.HeaderType;
import com.newgen.dscop.stub.InqCustPaymentDtlsStub.PaymentDtls_type0;

public class FetchDDSPaymentInfo extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchDDSPaymentInfo(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called fetchDDSPaymentInfo");
		LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustPaymentDtls");
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String accountNumber = xmlDataParser.getValueOf("accountNumber");
			String amount = xmlDataParser.getValueOf("amount");
			String corebankPostingReference = xmlDataParser.getValueOf("enquireType");
			String postingDate = xmlDataParser.getValueOf("postingDate");
					
			InqCustPaymentDtlsStub request_stub=new InqCustPaymentDtlsStub(sWSDLPath);
			FetchDDSPaymentInfoReqMsg fetch_DDS_dtls = new FetchDDSPaymentInfoReqMsg();			
			FetchDDSPaymentInfoReq_type0 fetch_DDS_type0 = new FetchDDSPaymentInfoReq_type0();
			FetchDDSPaymentInfoResMsg fetch_DDS_response = new FetchDDSPaymentInfoResMsg();
			FetchDDSPaymentInfoRes_type0 fetch_DDS_res_type0 = new FetchDDSPaymentInfoRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustPaymentDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setConsumer("");
			Header_Input.setRepTimeStamp("");
			Header_Input.setUsername("");
			Header_Input.setCredentials("");
		
			fetch_DDS_type0.setAccountNumber(accountNumber);
			fetch_DDS_type0.setAmount(amount);
			fetch_DDS_type0.setCorebankPostingReference(corebankPostingReference);
			fetch_DDS_type0.setPostingDate(postingDate);
			
			fetch_DDS_dtls.setFetchDDSPaymentInfoReq(fetch_DDS_type0);
			fetch_DDS_dtls.setHeader(Header_Input);
			fetch_DDS_dtls.setFetchDDSPaymentInfoReq(fetch_DDS_type0);

			fetch_DDS_response = request_stub.fetchDDSPaymentInfo_Oper(fetch_DDS_dtls);
			xmlInput= request_stub.getInputXML(fetch_DDS_dtls);
			Header_Input = fetch_DDS_response.getHeader();
			fetch_DDS_res_type0 = fetch_DDS_response.getFetchDDSPaymentInfoRes();
			LogGEN.writeTrace("CBG_Log", "fetchDDSPaymentInfo xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "fetchDDSPaymentInfoResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(fetch_DDS_res_type0 != null){
					PaymentDtls_type0 DDSDetails[] = fetch_DDS_res_type0.getPaymentDtls();
					if(DDSDetails != null){
						for(PaymentDtls_type0 DDSDetail : DDSDetails) {
							String ADCBaccountNumber = DDSDetail.getADCBaccountNumber();
							String ADCBaccountTitle = DDSDetail.getADCBaccountTitle();
							String ADCBreturnReason = DDSDetail.getADCBreturnReason();
							String amounts = DDSDetail.getAmount();
							String corebankPostingRef = DDSDetail.getCorebankPostingReference();
							String DDAreferenceNumber = DDSDetail.getDDAreferenceNumber();
							String DDAType = DDSDetail.getDDAType();
							String payStatus = DDSDetail.getPayStatus();
							String postDate = DDSDetail.getPostingDate();
							String presentingBank = DDSDetail.getPresentingBank();
							String representFlag = DDSDetail.getRepresentFlag();
									
							details.append("<PaymentDtls>").append("\n")
							.append("<ADCBaccountNumber>"+ADCBaccountNumber+"</ADCBaccountNumber>").append("\n")
							.append("<ADCBaccountTitle>"+ADCBaccountTitle+"</accountTitle>").append("\n")
							.append("<ADCBreturnReason>"+ADCBreturnReason+"</ADCBreturnReason>").append("\n")
							.append("<amount>"+amounts+"</amount>").append("\n")
							.append("<corebankPostingReference>"+corebankPostingRef+"</corebankPostingReference>").append("\n")
							.append("<DDAreferenceNumber>"+DDAreferenceNumber+"</DDAreferenceNumber>").append("\n")
							.append("<DDAType>"+DDAType+"</DDAType>").append("\n")
							.append("<payStatus>"+payStatus+"</payStatus>").append("\n")
							.append("<postingDate>"+postDate+"</postingDate>").append("\n")
							.append("<presentingBank>"+presentingBank+"</presentingBank>").append("\n")
							.append("<representFlag>"+representFlag+"</representFlag>").append("\n")
						    .append("</PaymentDtls>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>fetchDDSPaymentInfo</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<fetchDDSPaymentInfoRes>"+
						details+
						"</fetchDDSPaymentInfoRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>fetchDDSPaymentInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to fetch dds payment Info Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>fetchDDSPaymentInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch dds payment Info Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>fetchDDSPaymentInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch dds payment Info Details</Output>";
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
			LogGEN.writeTrace("CBG_Log","fetchDDSPaymentInfo  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","fetchDDSPaymentInfo  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

