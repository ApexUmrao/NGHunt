package com.newgen.dscop.client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.CustomerDetails_type1;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.DueDiligenceInfo_type0;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.DueDiligenceInfo_type1;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.FetchCRSDetailsReqMsg;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.FetchCRSDetailsReq_type0;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.FetchCRSDetailsResMsg;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.FetchCRSDetailsRes_type0;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.HeaderType;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.TaxResidenceCountries_type1;
import com.newgen.dscop.stub.InqCommonReportingStandardsStub.TaxResidenceCountryDtls_type1;

public class FetchCRS extends DSCOPServiceHandler{

	static String sWSDLPath="";
	static String sCabinet="";
	static String sUser="";
	static String sPassword="";
	static boolean sLoginReq=false;
	static long lTimeOut = 30000;
	String sOrgRes="";
	String xmlInput="";
	static String dburl="";
	static String dbuser="";
	static String dbpass="";
	static String sOutput= "";

	DSCOPServiceHandler sHandler;

	public String fetchCRSDetails(String sInputXML){
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called fetchCRSDetails");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try {

			loadWSDLDtls();
			String sCustomerID= xmlDataParser.getValueOf("CustID");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String custType=xmlDataParser.getValueOf("CustType");
			String useCase = xmlDataParser.getValueOf("useCase");
			LogGEN.writeTrace("CBG_Log", "read Property successfully");

			LogGEN.writeTrace("CBG_Log", "FetchCRS sCustomerID:" + sCustomerID);
			LogGEN.writeTrace("CBG_Log", "FetchCRS ref_no:" + ref_no);
			LogGEN.writeTrace("CBG_Log", "FetchCRS custType:" + custType);
			LogGEN.writeTrace("CBG_Log", "FetchCRS useCase:" + useCase);
			InqCommonReportingStandardsStub crsStub = new InqCommonReportingStandardsStub(sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchCRS crsStub:" + crsStub);
			FetchCRSDetailsReq_type0 fetchCRS = new FetchCRSDetailsReq_type0();
			LogGEN.writeTrace("CBG_Log", "FetchCRS fetchCRS:" + fetchCRS);
			FetchCRSDetailsReqMsg requestMsg = new FetchCRSDetailsReqMsg();
			LogGEN.writeTrace("CBG_Log", "FetchCRS requestMsg:" + requestMsg);
			TaxResidenceCountryDtls_type1 countryDetails = new TaxResidenceCountryDtls_type1();
			LogGEN.writeTrace("CBG_Log", "FetchCRS countryDetails:" + countryDetails);
			
			DueDiligenceInfo_type1 due1 = new DueDiligenceInfo_type1();
			LogGEN.writeTrace("CBG_Log", "FetchCRS due1:" + due1);
			DueDiligenceInfo_type0 due0 = new DueDiligenceInfo_type0();
			LogGEN.writeTrace("CBG_Log", "FetchCRS due0:" + due0);
			requestMsg.setHeader(setHeaderDtls(sDate, ref_no, xmlDataParser.getValueOf("SENDERID"), useCase));

			fetchCRS.setCustomerId(sCustomerID);
			fetchCRS.setCustomerType(custType);

			requestMsg.setFetchCRSDetailsReq(fetchCRS);
			LogGEN.writeTrace("CBG_Log", "FetchCRS requestMsg2:" + requestMsg);
			crsStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = crsStub.FetchCRSinputxml(requestMsg);
			LogGEN.writeTrace("CBG_Log", "FetchCRS input:" + xmlInput);
			FetchCRSDetailsResMsg resMsg = crsStub.fetchCRSDetails_Oper(requestMsg);
			sOrgRes = crsStub.resFetchCRS;
			LogGEN.writeTrace("CBG_Log", "FetchCRS sOrgRes:" + sOrgRes);

			FetchCRSDetailsRes_type0 response = resMsg.getFetchCRSDetailsRes();

			CustomerDetails_type1[] customerDetails = response.getCustomerDetails();

			countryDetails = response.getTaxResidenceCountryDtls();
			TaxResidenceCountries_type1[] taxCountries = countryDetails.getTaxResidenceCountries();
			due1 = response.getDueDiligenceInfo();

			StringBuffer gridRecordBuffer = new StringBuffer();
			String gridRecords = new String();

			for (int i = 0; i < taxCountries.length; ++i)
			{
				gridRecordBuffer.append("<GridRecord><countryTaxRes>");
				gridRecordBuffer.append(taxCountries[i].getTaxResidenceCountry());
				gridRecordBuffer.append("</countryTaxRes><TIN>");
				gridRecordBuffer.append(taxCountries[i].getTaxpayerIdNumber());
				gridRecordBuffer.append("</TIN><reasonNoTin>");
				gridRecordBuffer.append(taxCountries[i].getReasonId());
				gridRecordBuffer.append("</reasonNoTin><reasonDesc>");
				gridRecordBuffer.append(taxCountries[i].getReasonDesc());
				gridRecordBuffer.append("</reasonDesc></GridRecord>");
			}

			gridRecords = gridRecordBuffer.toString();

			HeaderType header = resMsg.getHeader();
			sReturnCode = header.getReturnCode();
			sErrorDetail = header.getErrorDetail();
			sErrorDesc = header.getErrorDescription();

			if ((!(sErrorDesc.equalsIgnoreCase("Failure"))) || (!(sReturnCode.equalsIgnoreCase("1"))))
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FETCH_CRS_DETAILS</Option><returnCode>" + 
						sReturnCode + "</returnCode>" + 
						"<errorDescription>" + sErrorDetail + "</errorDescription>" + 
						"<Status>" + sErrorDesc + "</Status>" + 
						"<errorDesc>" + sErrorDesc + "</errorDesc>" + 
						"<FirstName>" + customerDetails[0].getCustFirstName() + "</FirstName>" + 
						"<LastName>" + customerDetails[0].getCustLastName() + "</LastName>" + 
						"<CityOfBirth>" + customerDetails[0].getCustBirthCity() + "</CityOfBirth>" + 
						"<CRSObtained>" + customerDetails[0].getCrsCertFormObtained() + "</CRSObtained>" + 
						"<ClassificationId>" + customerDetails[0].getClassificationId() + "</ClassificationId>" + 
						"<CRSGridRecords>" + gridRecords + "</CRSGridRecords>" + 
						"<DueDiligenceInfo>" + 
						"<ResOtherThanUAE>" + due1.getResOtherThanUAE() + "</ResOtherThanUAE>" + 
						"<TaxPayerInOtherCountry>" + due1.getTaxPayerInOtherCountry() + "</TaxPayerInOtherCountry>" + 
						"<UaeResUnderInvestScheme>" + due1.getUaeResUnderInvestScheme() + "</UaeResUnderInvestScheme>" + 
						"<TaxCountry1>" + due1.getTaxCountry1() + "</TaxCountry1>" + 
						"<TaxCountry2>" + due1.getTaxCountry2() + "</TaxCountry2>" + 
						"<TaxCountry3>" + due1.getTaxCountry3() + "</TaxCountry3>" + 
						"</DueDiligenceInfo>" + 
						"</Output>";
			}
			else {
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FETCH_CRS_DETAILS</Option><returnCode>" + 
						sReturnCode + "</returnCode>" + 
						"<errorDesc>" + sErrorDesc + "</errorDesc>" + 
						"<errorDescription>" + sErrorDetail + "</errorDescription>" + 
						"</Output>";
			}

			LogGEN.writeTrace("CBG_Log", "output:" + sOutput);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log",sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CRS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch CRS</td></Output>";
			e.printStackTrace();
		
		}finally{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CRS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch CRS</td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			
			//LogGEN.writeTrace("CBG_Log", xmlInput);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "11111111111111111111%%%%%%%%%%%");
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				//LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}

	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid, String useCase){
		HeaderType headerType= new HeaderType();
		LogGEN.writeTrace("Log", "useCase : " + useCase);
		if ((useCase != null) && (!(useCase.equalsIgnoreCase(""))))
			headerType.setUsecaseID(useCase);
		else {
			headerType.setUsecaseID("123456");
		}
		headerType.setServiceName("InqCommonReportingStandards");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setCorrelationID("");
		headerType.setSysRefNumber(ref_no);			
		headerType.setSenderID(sHandler.setSenderId(senderid)); 
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("CBG"); 
		headerType.setCredentials("CBG"); 
		return headerType;
	}

	private static void loadWSDLDtls(){
		try {
			//sHandler.readCabProperty("ADD_CRS");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ADD_CRS");
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FETCH_CRS TIME_OUT: "+lTimeOut);

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log",sw.toString());
			e.printStackTrace();			
		}
	}
	private static void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log",sw.toString());
			e.printStackTrace();
		}
	}
}
