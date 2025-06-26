package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCommonReportingStandardsStub;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsReqMsg;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsReq_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsResMsg;
import com.newgen.stub.InqCommonReportingStandardsStub.AddCRSDetailsRes_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.ControlPersonTaxResCountries_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.ControlPersonTaxResCountryDtls_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.CustomerDetails_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.DocumentDetails_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.Documents_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.EntityControlPersonDtls_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.EntityControlPersons_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.HeaderType;
import com.newgen.stub.InqCommonReportingStandardsStub.TaxResidenceCountries_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.TaxResidenceCountryDtls_type0;


public class WBG_AddCRS extends WebServiceHandler{

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
	
	WebServiceHandler sHandler;
	public String addCRS(String sInputXML){
		String Status="";
		LogGEN.writeTrace("WBG_Log", "Fuction called addCRS");
		//LogGEN.writeTrace("WBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try {

			loadWSDLDtls(sHandler);
			String sCustomerID= xmlDataParser.getValueOf("CustID");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			
			LogGEN.writeTrace("WBG_Log", "read Property successfully");
			String custType=xmlDataParser.getValueOf("CustType");
			InqCommonReportingStandardsStub crsStub = new InqCommonReportingStandardsStub(sWSDLPath);
			AddCRSDetailsReqMsg reqMsg = new AddCRSDetailsReqMsg();
			AddCRSDetailsReq_type0 reqTyp0= new AddCRSDetailsReq_type0();
			
			reqMsg.setHeader(setHeaderDtls(sDate,ref_no,xmlDataParser.getValueOf("SENDERID")));
			LogGEN.writeTrace("WBG_Log", "After setting hrader");
			//Setting customer details
			reqTyp0.setCustomerDetails(setCustomerDtls(sInputXML, xmlDataParser));
			LogGEN.writeTrace("WBG_Log", "After setting setCustomerDtls");
			reqTyp0.setCustomerId(sCustomerID);
			reqTyp0.setCustomerType(custType);
			LogGEN.writeTrace("WBG_Log", "After setting hrader");
			// Setting control person tax res country details
			reqTyp0.setControlPersonTaxResCountryDtls(setControlPrsnTaxResCntry(sInputXML));
			LogGEN.writeTrace("WBG_Log", "After setting setControlPersonTaxResCountryDtls");
			// Setting Control Person Details
			reqTyp0.setEntityControlPersonDtls(setControlPersonDtls(sInputXML));
			LogGEN.writeTrace("WBG_Log", "After setting setEntityControlPersonDtls");
			// setting for Tax res country Details
			reqTyp0.setTaxResidenceCountryDtls(setTaxResidenceCountryDtls(sInputXML));
			//LogGEN.writeTrace("WBG_Log", "After setting setTaxResidenceCountryDtls");
			reqTyp0.setDocumentDetails(setCRSDocDetails(sInputXML,xmlDataParser));
			
			reqMsg.setAddCRSDetailsReq(reqTyp0);
			crsStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			
			xmlInput=crsStub.getinputXML(reqMsg);
			LogGEN.writeTrace("WBG_Log", "SOAP CRS Req"+xmlInput);
			AddCRSDetailsResMsg res=crsStub.addCRSDetails_Oper(reqMsg);
			sOrgRes= crsStub.resCRS;
			LogGEN.writeTrace("WBG_Log", "SOAP Response"+sOrgRes);
			HeaderType header= res.getHeader();
			sReturnCode=  header.getReturnCode();
			sErrorDetail=header.getErrorDetail();
			sErrorDesc = header.getErrorDescription();
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				AddCRSDetailsRes_type0 detailsRes_type0=new AddCRSDetailsRes_type0();
				detailsRes_type0=res.getAddCRSDetailsRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>ADD_CRS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Status>"+sErrorDesc+"</Status>" +		
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<AddCrsRes>"+
				"<CustomerID>"+detailsRes_type0.getCustomerId()+"</CustomerID>"+
				"</AddCrsRes>"+	
				"</Output>";				
			} else
			{
				LogGEN.writeTrace("WBG_Log", "Failed");
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>ADD_CRS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +	
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_CRS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add customer into </td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}finally{
			LogGEN.writeTrace("WBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_CRS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add customer into </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			
			//LogGEN.writeTrace("WBG_Log", xmlInput);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("Calltype");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("WBG_Log", "11111111111111111111%%%%%%%%%%%");
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				//LogGEN.writeTrace("WBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}
	private String subStringMsg(String input,String tagName){
		String startTag="<"+tagName+">";
		String endTag="</"+tagName +">";
		return input.substring(input.indexOf(startTag), input.indexOf(endTag))+endTag;
	} 
	private WFXmlList xmlList(String input,String records,String record){
		WFXmlResponse xmlResponse = new WFXmlResponse(input);			
		WFXmlList lWfXml = xmlResponse.createList(records,record);
		return lWfXml;
	}
	private ControlPersonTaxResCountryDtls_type0 setControlPrsnTaxResCntry(String sInputXML){
		ControlPersonTaxResCountryDtls_type0 cntlPTaxResCntryDtls_tpe0= new ControlPersonTaxResCountryDtls_type0();
		//This code should be inside the loop --Code Start here for Control person tax res country getVal
		String prsnTxResCnty=subStringMsg(sInputXML, "controlPersonTaxResCountryDtls");
		WFXmlList lWfXml =xmlList(prsnTxResCnty, "controlPersonTaxResCountryDtls", "controlPersonTaxResCountries");
		for (int i = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), i++){
			ControlPersonTaxResCountries_type0 controlPersonTaxResCountries_type0= new ControlPersonTaxResCountries_type0();
			controlPersonTaxResCountries_type0.setControlPersonId(lWfXml.getVal("ControlPersonId"));
			controlPersonTaxResCountries_type0.setControlPersonPrimaryKey(lWfXml.getVal("ControlPersonPrimaryKey"));
			controlPersonTaxResCountries_type0.setControlPersonReasonId(lWfXml.getVal("ControlPersonReasonId"));
			controlPersonTaxResCountries_type0.setControlPersonReportableFlag(lWfXml.getVal("ControlPersonReportableFlag"));
			controlPersonTaxResCountries_type0.setControlPersonTaxpayerIdNo(lWfXml.getVal("ControlPersonTaxpayerIdNo"));
			controlPersonTaxResCountries_type0.setControlPersonTaxResCountry(lWfXml.getVal("ControlPersonTaxResCountry"));		
			cntlPTaxResCntryDtls_tpe0.addControlPersonTaxResCountries(controlPersonTaxResCountries_type0);
		}	
		return cntlPTaxResCntryDtls_tpe0;
	}
	private EntityControlPersonDtls_type0 setControlPersonDtls(String sInputXML){
		EntityControlPersonDtls_type0 controlPersonDtls_type0= new EntityControlPersonDtls_type0();
		String prsnTxResCnty=subStringMsg(sInputXML, "entityControlPersonDtls");
		WFXmlList lWfXml =xmlList(prsnTxResCnty, "entityControlPersonDtls", "entityControlPersons");

		for (int i = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), i++){
			EntityControlPersons_type0 entityControlPersons_type0= new EntityControlPersons_type0();
			//This code should be inside the loop --Code Start here for Control person Entity Details
			entityControlPersons_type0.setControlPersonBirthCity(lWfXml.getVal("PersonBirthCity"));
			entityControlPersons_type0.setControlPersonBirthCountry(lWfXml.getVal("PersonBirthCountry"));
			entityControlPersons_type0.setControlPersonBuildingName(lWfXml.getVal("PersonBuildingName"));
			entityControlPersons_type0.setControlPersonCity(lWfXml.getVal("PersonCity"));
			entityControlPersons_type0.setControlPersonControlTypeId(lWfXml.getVal("PersonControlTypeId"));
			entityControlPersons_type0.setControlPersonCountry(lWfXml.getVal("PersonCountry"));
			entityControlPersons_type0.setControlPersonDateOfBirth(lWfXml.getVal("PersonDateOfBirth"));
			entityControlPersons_type0.setControlPersonEmirate(lWfXml.getVal("PersonEmirate"));
			entityControlPersons_type0.setControlPersonFirstName(lWfXml.getVal("PersonFirstName"));
			entityControlPersons_type0.setControlPersonFlatVillaNo(lWfXml.getVal("PersonFlatVillaNo"));
			entityControlPersons_type0.setControlPersonId(lWfXml.getVal("ControlPersonId"));
			entityControlPersons_type0.setControlPersonLastName(lWfXml.getVal("PersonLastName"));
			entityControlPersons_type0.setControlPersonPrimaryKey(lWfXml.getVal("ControlPersonPrimaryKey"));
			entityControlPersons_type0.setControlPersonStreet(lWfXml.getVal("PersonStreet"));
			controlPersonDtls_type0.addEntityControlPersons(entityControlPersons_type0);
		}
		return controlPersonDtls_type0;
	}
	private TaxResidenceCountryDtls_type0 setTaxResidenceCountryDtls(String sInputXML){
		TaxResidenceCountryDtls_type0 countryDtls_type0= new TaxResidenceCountryDtls_type0();
		String prsnTxResCnty=subStringMsg(sInputXML, "taxResidenceCountryDtls");
		WFXmlList lWfXml =xmlList(prsnTxResCnty, "taxResidenceCountryDtls", "taxResidenceCountries");	
		for (int i = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), i++){
			//This code should be inside the loop --Code Start here for Tax res country Details
			TaxResidenceCountries_type0 taxResidenceCountries_type0= new TaxResidenceCountries_type0();
			taxResidenceCountries_type0.setTaxResidenceCountry(lWfXml.getVal("TaxResidenceCountry"));
			taxResidenceCountries_type0.setTaxpayerIdNumber(lWfXml.getVal("TaxpayerIdNumber"));
			taxResidenceCountries_type0.setReportableFlag(lWfXml.getVal("ReportableFlag"));
			taxResidenceCountries_type0.setReasonId(lWfXml.getVal("ReasonId"));
			taxResidenceCountries_type0.setReasonDesc(lWfXml.getVal("ReasonDesc"));
			countryDtls_type0.addTaxResidenceCountries(taxResidenceCountries_type0);
		}
		return countryDtls_type0;
	}
	private CustomerDetails_type0 setCustomerDtls(String sInputXML,XMLParser xmlDataParser){
		xmlDataParser.setInputXML(sInputXML);
		CustomerDetails_type0 customerDetails_type0= new CustomerDetails_type0();
		try {
			customerDetails_type0.setChannel(xmlDataParser.getValueOf("Channel"));
			customerDetails_type0.setCheckerDate(xmlDataParser.getValueOf("checkerDate"));
			customerDetails_type0.setCheckerId(xmlDataParser.getValueOf("CheckerId"));				
			customerDetails_type0.setClassificationDate(xmlDataParser.getValueOf("ClassificationDate"));
			customerDetails_type0.setClassificationId(xmlDataParser.getValueOf("ClassificationId"));			
			customerDetails_type0.setCrsCertFormObtained(xmlDataParser.getValueOf("CrsCertFormObtained"));
			customerDetails_type0.setCustBirthCity(xmlDataParser.getValueOf("CustBirthCity"));
			customerDetails_type0.setCustFirstName(xmlDataParser.getValueOf("CustFirstName"));
			customerDetails_type0.setCustLastName(xmlDataParser.getValueOf("CustLastName"));
			customerDetails_type0.setEntityTypeId(xmlDataParser.getValueOf("EntityTypeId"));
			customerDetails_type0.setMakerDate(xmlDataParser.getValueOf("makerDate"));		
			customerDetails_type0.setMakerId(xmlDataParser.getValueOf("MakerId"));
			customerDetails_type0.setResidenceAddressConfirmationStatus(xmlDataParser.getValueOf("ResidenceAddressConfirmationStatus"));
			customerDetails_type0.setTermsAndCondAccepted(xmlDataParser.getValueOf("TermsAndCondAccepted"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
		}	
		return customerDetails_type0;
	}
	private DocumentDetails_type0 setCRSDocDetails(String xml,XMLParser xmlDataParser){
		xmlDataParser.setInputXML(xml);
		DocumentDetails_type0 details_type0= new DocumentDetails_type0();		
		try {
			Documents_type0 docType= new Documents_type0();
			docType.setDocCRSRefNo(xmlDataParser.getValueOf("CRSDocRefNo"));
			docType.setDocCRSCertDate(xmlDataParser.getValueOf("CRSCertDate"));
			docType.setDocIndex(xmlDataParser.getValueOf("CRSDocIndex"));
			docType.setDocName(xmlDataParser.getValueOf("CRSDocName"));
			docType.setDocRefNo(xmlDataParser.getValueOf("CRSRefNo"));			
			details_type0.addDocuments(docType);			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
		}		
		return details_type0;		
	}
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid){
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("123456");
		headerType.setServiceName("InqCommonReportingStandards");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setCorrelationID("");
		headerType.setSysRefNumber(ref_no);			
		headerType.setSenderID(sHandler.setSenderId(senderid)); 
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("WBG"); 
		return headerType;
	}

	private static void loadWSDLDtls(WebServiceHandler sHandler){
		try {
			sHandler.readCabProperty("ADD_CRS");
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			e.printStackTrace();			
		}
	}
	private static void loadJSTDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			e.printStackTrace();
		}
	}
}
