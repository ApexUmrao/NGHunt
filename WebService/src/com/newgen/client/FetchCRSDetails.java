/*----------------------------------------------------------------------------------------------------
		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Group					  : AP2
Product / Project		  : ADCB 
Module					  : CRS 6.1
File Name				  : FetchCRSDetails.java
Author					  : Harinder Pal Singh
Date written (DD/MM/YYYY) : 02 August,2018
Description				  : Client stub for Fetch CRS Details Webservice.
----------------------------------------------------------------------------------------------------
CHANGE HISTORY
-------------------------------------------------------------------------------------------------------
Problem No/CR No   Change Date   Changed By    Change Description
------------------------------------------------------------------------------------------------------
NA						NA			 NA					NA
-----------------------------------------------------------------------------------------------------*/

package com.newgen.client;
/**
 * @Date : 05 july 2018
 * @Purpose : CR July 2018
 * 6.1. CRS Fetch Call 
 * */


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCommonReportingStandardsStub;
import com.newgen.stub.InqCommonReportingStandardsStub.CustomerDetails_type1;
import com.newgen.stub.InqCommonReportingStandardsStub.FetchCRSDetailsReqMsg;
import com.newgen.stub.InqCommonReportingStandardsStub.FetchCRSDetailsReq_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.FetchCRSDetailsResMsg;
import com.newgen.stub.InqCommonReportingStandardsStub.FetchCRSDetailsRes_type0;
import com.newgen.stub.InqCommonReportingStandardsStub.HeaderType;
import com.newgen.stub.InqCommonReportingStandardsStub.TaxResidenceCountries_type1;
import com.newgen.stub.InqCommonReportingStandardsStub.TaxResidenceCountryDtls_type1;

public class FetchCRSDetails extends WebServiceHandler {
	
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
	
	public String fetchCRSDetails(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("AO_CRS_Log", "Fuction called fetchCRSDetails");
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
			String custType=xmlDataParser.getValueOf("CustType");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			
			LogGEN.writeTrace("Log", "read Property successfully");
			
			InqCommonReportingStandardsStub stub= new InqCommonReportingStandardsStub(sWSDLPath);
			FetchCRSDetailsReq_type0 fetchCRS= new FetchCRSDetailsReq_type0();
			FetchCRSDetailsReqMsg requestMsg= new FetchCRSDetailsReqMsg();
			TaxResidenceCountryDtls_type1 countryDetails= new TaxResidenceCountryDtls_type1();
			TaxResidenceCountries_type1[] taxCountries;
			CustomerDetails_type1 []customerDetails;
			
			requestMsg.setHeader(setHeaderDtls(sDate,ref_no,xmlDataParser.getValueOf("SENDERID")));
			
			//setting values for fetch input call
			fetchCRS.setCustomerId(sCustomerID);
			fetchCRS.setCustomerType(custType);
			
			requestMsg.setFetchCRSDetailsReq(fetchCRS);

			//stub make fetchCRSDetails call
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=stub.CRSinputxml(requestMsg);
			FetchCRSDetailsResMsg resMsg=stub.fetchCRSDetails_Oper(requestMsg);
			sOrgRes= stub.resFetchCRS;
			LogGEN.writeTrace("Log", "input:"+xmlInput);
			LogGEN.writeTrace("Log", "sOrgRes:"+sOrgRes);
			//Response of stub received
			FetchCRSDetailsRes_type0 response=resMsg.getFetchCRSDetailsRes();
			
			//fetching data from response	
			customerDetails=response.getCustomerDetails();
			//customerDetails[0].getClassificationDate();
			
			countryDetails=response.getTaxResidenceCountryDtls();
			taxCountries=countryDetails.getTaxResidenceCountries();
			
			StringBuffer gridRecordBuffer= new StringBuffer();
			String gridRecords=new String();
			
			for(int i=0;i<taxCountries.length;i++)
			{
				gridRecordBuffer.append("<GridRecord><countryTaxRes>");
				gridRecordBuffer.append(taxCountries[i].getTaxResidenceCountry());
				gridRecordBuffer.append("</countryTaxRes><TIN>");
				gridRecordBuffer.append(taxCountries[i].getTaxpayerIdNumber());
				gridRecordBuffer.append("</TIN><reasonNoTin>");
				gridRecordBuffer.append(taxCountries[i].getReasonId());
				gridRecordBuffer.append("</reasonNoTin><reasonDesc>");      //added by harinder on 12102018
				gridRecordBuffer.append(taxCountries[i].getReasonDesc());	 //added by harinder on 12102018
				gridRecordBuffer.append("</reasonDesc></GridRecord>");
			}
			
			gridRecords=gridRecordBuffer.toString();
		
			HeaderType header= resMsg.getHeader();
			sReturnCode=  header.getReturnCode();
			sErrorDetail=header.getErrorDetail();
			sErrorDesc = header.getErrorDescription();
		
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
			
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>FETCH_CRS_DETAILS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Status>"+sErrorDesc+"</Status>" +		
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<FirstName>"+customerDetails[0].getCustFirstName()+"</FirstName>"+
				"<LastName>"+customerDetails[0].getCustLastName()+"</LastName>"+
				"<CityOfBirth>"+customerDetails[0].getCustBirthCity()+"</CityOfBirth>"+
				"<CRSObtained>"+customerDetails[0].getCrsCertFormObtained()+"</CRSObtained>"+
				"<ClassificationId>"+customerDetails[0].getClassificationId()+"</ClassificationId>"+				
				"<CRSGridRecords>"+gridRecords+"</CRSGridRecords>"+				
				"</Output>";				
			} else
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>FETCH_CRS_DETAILS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +	
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}
			
			LogGEN.writeTrace("Log", "output:"+sOutput);
		} catch (IOException e) {
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_CRS_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch CRS Details</td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}finally{
			
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><FETCH_CRS_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch CRS Details </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			
			
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("Calltype");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
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

	
	private static void loadWSDLDtls(WebServiceHandler sHandler){
		try {
			sHandler.readCabProperty("FETCH_CRS_DETAILS");
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("AO_CRS_Log",sw.toString());
			e.printStackTrace();			
		}
	}
	
	
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid){
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCommonReportingStandards");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);			
		headerType.setSenderID(sHandler.setSenderId(senderid)); 
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("TP100066");
		headerType.setCredentials("TP100066");
		return headerType;
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
			LogGEN.writeTrace("AO_CRS_Log",sw.toString());
			e.printStackTrace();
		}
	}
	
}
