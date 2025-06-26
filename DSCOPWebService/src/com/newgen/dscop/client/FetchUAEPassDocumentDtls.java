package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.DocumentDetails_type1;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReqMsg;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsReq_type0;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsResMsg;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.FetchUAEPassDocumentDtlsRes_type0;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.HeaderType;

public class FetchUAEPassDocumentDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchUAEPassDocumentDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchUAEPassDocumentDtls");
		LogGEN.writeTrace("CBG_Log", "FetchUAEPassDocumentDtls sInputXML---"+ sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerUAEPassInfo");

			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerUAEPassInfo TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String acknowledgementRefno = xmlDataParser.getValueOf("acknowledgementRefno");

			InqCustomerUAEPassInfoStub request_stub=new InqCustomerUAEPassInfoStub(sWSDLPath);
			FetchUAEPassDocumentDtlsReqMsg reqMsg = new FetchUAEPassDocumentDtlsReqMsg();
			FetchUAEPassDocumentDtlsReq_type0 reqMsg_type0 = new FetchUAEPassDocumentDtlsReq_type0();
			FetchUAEPassDocumentDtlsResMsg response_msg = new FetchUAEPassDocumentDtlsResMsg();
			FetchUAEPassDocumentDtlsRes_type0 response_type0  = new FetchUAEPassDocumentDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerUAEPassInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			reqMsg_type0.setAcknowledgementRefno(acknowledgementRefno);

			reqMsg.setFetchUAEPassDocumentDtlsReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);

			response_msg = request_stub.fetchUAEPassDocumentDtls_Oper(reqMsg);
			xmlInput= request_stub.getInputXml(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getFetchUAEPassDocumentDtlsRes();
			LogGEN.writeTrace("CBG_Log", "FetchUAEPassDocumentDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchUAEPassDocumentDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			String externalRefNo = response_type0.getExternalRefNo();
			String purposeEn = response_type0.getPurposeEn();
			String purposeAr = response_type0.getPurposeAr();
			String requestedAt = response_type0.getRequestedAt();
			String channel = response_type0.getChannel();
			String orgin = response_type0.getOrgin();
			String typeOfRequest = response_type0.getTypeOfRequest();
			String documentDtlsStatus = response_type0.getDocumentDtlsStatus();
			String documentReceiedStatus = response_type0.getDocumentReceiedStatus();
			String rejectStatus = response_type0.getRejectStatus();
			String auditDateTime = response_type0.getAuditDateTime();
			String acknowledRefno = response_type0.getAcknowledgementRefno();
			String mobileNumber = response_type0.getMobileNumber();
			String email = response_type0.getEmail();
			String referenceNumber = response_type0.getReferenceNumber();
			String issuerIdentifier = response_type0.getIssuerIdentifier();
			String emiratesId = response_type0.getEmiratesId();
			String rejectReason = response_type0.getRejectReason();
			String remarks = response_type0.getRemarks();

			DocumentDetails_type1[] docDetails =response_type0.getDocumentDetails();
			StringBuilder details = new StringBuilder(); 
			if(docDetails != null){
				for(DocumentDetails_type1 detailsType0 : docDetails) {
					String documentType = detailsType0.getDocumentType();
					String externalDocRefNo = detailsType0.getExternalDocRefNo();
					String documentExpiryDate = detailsType0.getDocumentExpiryDate();
					String documentAssuranceLevel = detailsType0.getDocumentAssuranceLevel();
					String createdOn = detailsType0.getCreatedOn();
					String creator = detailsType0.getCreator();
					String nameAr = detailsType0.getNameAr();
					String nameEn = detailsType0.getNameEn();
					String expiryDate = detailsType0.getExpiryDate();
					String idNumber = detailsType0.getIdNumber();
					String cardNumber = detailsType0.getCardNumber();
					String dateOfBirth = detailsType0.getDateOfBirth();
					String documentTypeCode = detailsType0.getDocumentTypeCode();
					String genderAr = detailsType0.getGenderAr();
					String genderCode = detailsType0.getGenderCode();
					String genderEn = detailsType0.getGenderEn();
					String nationalityAr = detailsType0.getNationalityAr();
					String nationalityCode = detailsType0.getNationalityCode();
					String nationalityEn = detailsType0.getNationalityEn();
					String photoBase64 = detailsType0.getPhotoBase64();
					String signatureBase64 = detailsType0.getSignatureBase64();
					String notifyToCustomer = detailsType0.getNotifyToCustomer();
					String fileNumber = detailsType0.getFileNumber();
					String professionCode = detailsType0.getProfessionCode();
					String professionAr = detailsType0.getProfessionAr();
					String professionEn = detailsType0.getProfessionEn();
					String sponsorNo = detailsType0.getSponsorNo();
					String sponsorAr = detailsType0.getSponsorAr();
					String sponsorEn = detailsType0.getSponsorEn();
					String accompaniedBy = detailsType0.getAccompaniedBy();
					String transactionRefNo = detailsType0.getTransactionRefNo();
					String edmsDocRefNo = detailsType0.getEdmsDocRefNo();
					String externalBlockChainId = detailsType0.getExternalBlockChainId();
					String status = detailsType0.getStatus();
					String placeOfBirthEn = detailsType0.getPlaceOfBirthEn();
					String placeOfBirthAr = detailsType0.getPlaceOfBirthAr();
					String passportTypeEn = detailsType0.getPassportTypeEn();
					String countryCode = detailsType0.getCountryCode();
					String externalRequestId = detailsType0.getExternalDocRefNo();
					String issueDate = detailsType0.getIssueDate();
					String IssuingAuthEn = detailsType0.getIssuingAuthEn();
					String IssuingAuthAr = detailsType0.getIssuingAuthAr();

					details.append("<documentDetails>").append("\n")
					.append("<documentType>"+documentType+"</documentType>").append("\n")
					.append("<externalDocRefNo>"+externalDocRefNo+"</externalDocRefNo>").append("\n")
					.append("<documentExpiryDate>"+documentExpiryDate+"</documentExpiryDate>").append("\n")
					.append("<documentAssuranceLevel>"+documentAssuranceLevel+"</documentAssuranceLevel>").append("\n")
					.append("<createdOn>"+createdOn+"</createdOn>").append("\n")
					.append("<creator>"+creator+"</creator>").append("\n")
					.append("<nameAr>"+nameAr+"</nameAr>").append("\n")
					.append("<nameEn>"+nameEn+"</nameEn>").append("\n")
					.append("<expiryDate>"+expiryDate+"</expiryDate>").append("\n")
					.append("<issueDate>"+issueDate+"</issueDate>").append("\n")
					.append("<idNumber>"+idNumber+"</idNumber>").append("\n")
					.append("<cardNumber>"+cardNumber+"</cardNumber>").append("\n")
					.append("<dateOfBirth>"+dateOfBirth+"</dateOfBirth>").append("\n")
					.append("<documentTypeCode>"+documentTypeCode+"</documentTypeCode>").append("\n")
					.append("<genderAr>"+genderAr+"</genderAr>").append("\n")
					.append("<genderCode>"+genderCode+"</genderCode>").append("\n")
					.append("<genderEn>"+genderEn+"</genderEn>").append("\n")
					.append("<nationalityAr>"+nationalityAr+"</nationalityAr>").append("\n")
					.append("<nationalityCode>"+nationalityCode+"</nationalityCode>").append("\n")
					.append("<nationalityEn>"+nationalityEn+"</nationalityEn>").append("\n")
					.append("<photoBase64>"+photoBase64+"</photoBase64>").append("\n")
					.append("<signatureBase64>"+signatureBase64+"</signatureBase64>").append("\n")
					.append("<notifyToCustomer>"+notifyToCustomer+"</notifyToCustomer>").append("\n")
					.append("<IssuingAuthEn>"+IssuingAuthEn+"</IssuingAuthEn>").append("\n")
					.append("<IssuingAuthAr>"+IssuingAuthAr+"</IssuingAuthAr>").append("\n")
					.append("<fileNumber>"+fileNumber+"</fileNumber>").append("\n")
					.append("<professionCode>"+professionCode+"</professionCode>").append("\n")
					.append("<professionAr>"+professionAr+"</professionAr>").append("\n")
					.append("<professionEn>"+professionEn+"</professionEn>").append("\n")
					.append("<sponsorNo>"+sponsorNo+"</sponsorNo>").append("\n")
					.append("<sponsorAr>"+sponsorAr+"</sponsorAr>").append("\n")
					.append("<sponsorEn>"+sponsorEn+"</sponsorEn>").append("\n")
					.append("<accompaniedBy>"+accompaniedBy+"</accompaniedBy>").append("\n")
					.append("<transactionRefNo>"+transactionRefNo+"</transactionRefNo>").append("\n")
					.append("<edmsDocRefNo>"+edmsDocRefNo+"</edmsDocRefNo>").append("\n")
					.append("<externalBlockChainId>"+externalBlockChainId+"</externalBlockChainId>").append("\n")
					.append("<status>"+status+"</status>").append("\n")
					.append("<placeOfBirthEn>"+placeOfBirthEn+"</placeOfBirthEn>").append("\n")
					.append("<placeOfBirthAr>"+placeOfBirthAr+"</placeOfBirthAr>").append("\n")
					.append("<passportTypeEn>"+passportTypeEn+"</passportTypeEn>").append("\n")
					.append("<countryCode>"+countryCode+"</countryCode>").append("\n")
					.append("<externalRequestId>"+externalRequestId+"</externalRequestId>").append("\n")
					.append("</documentDetails>").append("\n");
			}
			
			}
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustomerUAEPassInfo</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<FetchUAEPassDocumentDtlsRes>"+
						"<externalRefNo>"+externalRefNo+"</externalRefNo>" +
						"<purposeEn>"+purposeEn+"</purposeEn>"+
						"<purposeAr>"+purposeAr+"</purposeAr>" +
						"<requestedAt>"+requestedAt+"</requestedAt>"+
						"<channel>"+channel+"</channel>" +
						"<orgin>"+orgin+"</orgin>"+
						"<typeOfRequest>"+typeOfRequest+"</typeOfRequest>" +
						"<documentDtlsStatus>"+documentDtlsStatus+"</documentDtlsStatus>"+
						"<documentReceiedStatus>"+documentReceiedStatus+"</documentReceiedStatus>" +
						"<rejectStatus>"+rejectStatus+"</rejectStatus>"+
						"<auditDateTime>"+auditDateTime+"</auditDateTime>"+
						"<acknowledgementRefno>"+acknowledRefno+"</acknowledgementRefno>" +
						"<mobileNumber>"+mobileNumber+"</mobileNumber>"+
						"<email>"+email+"</email>" +
						"<referenceNumber>"+referenceNumber+"</referenceNumber>"+
						"<issuerIdentifier>"+issuerIdentifier+"</issuerIdentifier>" +
						"<emiratesId>"+emiratesId+"</emiratesId>"+
						"<rejectReason>"+rejectReason+"</rejectReason>" +
						"<remarks>"+remarks+"</remarks>"+
						details+
						"</FetchUAEPassDocumentDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUST_UAE_PASS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Fetch customer UAE Pass</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUST_UAE_PASS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Fetch customer UAE Pass</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CUST_UAE_PASS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Fetch customer UAE Pass</Output>";
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
			LogGEN.writeTrace("CBG_Log","FetchUAEPassDocumentDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchUAEPassDocumentDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}