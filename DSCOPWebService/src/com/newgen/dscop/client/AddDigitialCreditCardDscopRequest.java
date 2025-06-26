package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalSupplCCDtlsReqMsg;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalSupplCCDtlsReq_type0;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalSupplCCDtlsResMsg;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalSupplCCDtlsRes_type0;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.HeaderType;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.SupplementaryCardDetails_type0;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.SupplementaryDetails_type0;

public class AddDigitialCreditCardDscopRequest extends DSCOPServiceHandler {

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;

	@SuppressWarnings("finally")
	public String AddDigitalCreditCardDscopRequestCall(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddDigitalCreditCardRequest");
		LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCard sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddDigitalCreditCardDscopRequest");
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardDscopRequest TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String leadRefNo = xmlDataParser.getValueOf("leadRefNo");
			String customerId = xmlDataParser.getValueOf("customerId");
			String primaryRefNo = xmlDataParser.getValueOf("primaryRefNo");
			String dsaCode = xmlDataParser.getValueOf("dsaCode");


			int end = 0;
			int start = xmlDataParser.getStartIndex("SupplementryCardDetails", 0, 0);
			int deadend = xmlDataParser.getEndIndex("SupplementryCardDetails", start, 0);
			int suppCount  = xmlDataParser.getNoOfFields("supplementaryDetails",start,deadend);

			LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCard suppliment:"+suppCount);

			AddDigitalCreditCardRequestStub add_credit_request_stub = new AddDigitalCreditCardRequestStub(sWSDLPath);
			AddDigitalSupplCCDtlsReq_type0 add_suppl_req_typ0 = new AddDigitalSupplCCDtlsReq_type0();
			AddDigitalSupplCCDtlsReqMsg add_supp_req_msg = new AddDigitalSupplCCDtlsReqMsg();
			SupplementaryDetails_type0[] supp_req  = new SupplementaryDetails_type0[suppCount];
			AddDigitalSupplCCDtlsResMsg add_supp_res_msg = new AddDigitalSupplCCDtlsResMsg();
			SupplementaryCardDetails_type0 supp_cardDtls = new SupplementaryCardDetails_type0();

			HeaderType Header_Input = new HeaderType();

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddDigitalCreditCardRequest");
			Header_Input.setServiceAction("Addition");
			Header_Input.setCorrelationID(ref_no);
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer("");
			Header_Input.setVersionNo("1.0");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setRepTimeStamp("");

			add_supp_req_msg.setHeader(Header_Input);

			add_suppl_req_typ0.setCustomerId(customerId);
			add_suppl_req_typ0.setDsaCode(dsaCode);
			add_suppl_req_typ0.setPrimaryRefNo(primaryRefNo);
			add_suppl_req_typ0.setLeadRefNo(leadRefNo);
			
			
			
			for (int i=0;i<suppCount;i++)
			{
				LogGEN.writeTrace("CBG_Log", "Inside debit card suppliment:"+suppCount);
			start = xmlDataParser.getStartIndex("supplementaryDetails", end, 0);
			end = xmlDataParser.getEndIndex("supplementaryDetails", start, 0);
			String supplementaryTitle = xmlDataParser.getValueOf("supplementaryTitle", start, end);
			String supplName = xmlDataParser.getValueOf("supplementaryName", start, end);
			String supplEmbossName = xmlDataParser.getValueOf("supplementaryEmbossName", start, end);
			String supplLimitPercent = xmlDataParser.getValueOf("supplementaryLimitPercentage", start, end);
			String supplRelationship = xmlDataParser.getValueOf("supplementaryRelationship", start, end);
			String supplDOB = xmlDataParser.getValueOf("supplementaryDOB", start, end);
			String supplCID = xmlDataParser.getValueOf("supplementaryCID", start, end);
			String supplGender = xmlDataParser.getValueOf("gender", start, end);
			String suppNationality = xmlDataParser.getValueOf("supplementaryNationality", start, end);
			String suppPass = xmlDataParser.getValueOf("supplementaryPassport", start, end);
			String suppEida = xmlDataParser.getValueOf("supplementaryEIDA", start, end);
			String supplCntryRes = xmlDataParser.getValueOf("countryOfResidence", start, end);
			String supplCustId = xmlDataParser.getValueOf("supplementaryCustomizationID", start, end);
			String suppEidaExp = xmlDataParser.getValueOf("eidaExpiryDate", start, end);
			String passportExpiryDate = xmlDataParser.getValueOf("passportExpiryDate", start, end);
			SupplementaryDetails_type0 suppReq  = new SupplementaryDetails_type0();
			suppReq.setSupplementaryTitle(supplementaryTitle);
			suppReq.setSupplementaryName(supplName);
			suppReq.setSupplementaryEmbossName(supplEmbossName);
			suppReq.setSupplementaryLimitPercentage(supplLimitPercent);
			suppReq.setSupplementaryRelationship(supplRelationship);
			suppReq.setSupplementaryDOB(supplDOB);
			suppReq.setSupplementaryCID(supplCID);
			suppReq.setGender(supplGender);
			suppReq.setSupplementaryEIDA(suppEida);
			suppReq.setSupplementaryNationality(suppNationality);
			suppReq.setSupplementaryPassport(suppPass);
			suppReq.setCountryOfResidence(supplCntryRes);
			suppReq.setSupplementaryCustomizationID(supplCustId);
			suppReq.setPassportExpiryDate(passportExpiryDate);
			suppReq.setEidaExpiryDate(suppEidaExp);
			
			supp_req[i] = suppReq;
			}
			add_suppl_req_typ0.setSupplementaryDetails(supp_req);

			add_supp_req_msg.setAddDigitalSupplCCDtlsReq(add_suppl_req_typ0);
			xmlInput= add_credit_request_stub.getInputXml(add_supp_req_msg);


			LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCard xmlInput xml : "+xmlInput);
			add_credit_request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCard 1");
			add_supp_res_msg = add_credit_request_stub.addDigitalSupplCCDtls_Oper(add_supp_req_msg);
			LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCard 2");
			sOrg_put=add_credit_request_stub.outputXML;

			LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCardResMsg sOrg_put: "+sOrg_put);

			Header_Input=add_supp_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();


			AddDigitalSupplCCDtlsRes_type0 add_supp_res_type0 = new AddDigitalSupplCCDtlsRes_type0();
			add_supp_res_type0 = add_supp_res_msg.getAddDigitalSupplCCDtlsRes();
			supp_cardDtls=add_supp_res_type0.getSupplementaryCardDetails()[0];
			String status= add_supp_res_type0.getStatusCode();
			String reason = add_supp_res_type0.getStatusDescription();
			String leadReferenceNumber = add_supp_res_type0.getLeadReferenceNumber();
			String leadApplicationNumber = supp_cardDtls.getLendperfectReferenceNumber();
			String cardLogo = supp_cardDtls.getCardLogo();
			String cardNo = supp_cardDtls.getCardNumber();
			String expiryDate = supp_cardDtls.getExpiryDate();
			LogGEN.writeTrace("CBG_Log", "Status sOrg_put: "+status);
			LogGEN.writeTrace("CBG_Log", "reason sOrg_put: "+reason);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddDigitalCreditCardRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<addDigitalSupplCCDtlsRes>"+
						"<leadReferenceNumber>"+leadReferenceNumber+"</leadReferenceNumber>"+
						"<lendperfectReferenceNumber>"+leadApplicationNumber+"</lendperfectReferenceNumber>"+
						"<cardLogo>"+cardLogo+"</cardLogo>"+
						"<cardNumber>"+cardNo+"</cardNumber>"+
						"<expiryDate>"+expiryDate+"</expiryDate>"+
						"<statusCode>"+status+"</statusCode>"+
						"<statusDescription>"+reason+"</statusDescription>"+
						"</addDigitalSupplCCDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigitalCreditCardRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to add Digital Credit Card.</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add Digital Credit Card.</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add Digital Credit Card.</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Pratial Success";
			else
				Status="Failure";

			try {

			} catch (Exception e) {

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
			LogGEN.writeTrace("CBG_Log","Modify Cudtomer  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","ModifyCudtomer  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}
	}


}
