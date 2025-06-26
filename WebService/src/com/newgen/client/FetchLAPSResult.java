/*----------------------------------------------------------------------------------------------------
		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
Group					  : AP2
Product / Project		  : ADCB 
Module					  : 6.11. Duplicate CID validation using FCUBS and LAPS  
File Name				  : FetchLAPSResult.java
Author					  : Harinder Pal Singh
Date written (DD/MM/YYYY) : 06/08/2018
Description				  : Client stub for LAPS Search Result Webservice.
----------------------------------------------------------------------------------------------------
CHANGE HISTORY
-------------------------------------------------------------------------------------------------------
Problem No/CR No   Change Date   Changed By    Change Description
------------------------------------------------------------------------------------------------------
NA						NA			 NA					NA
-----------------------------------------------------------------------------------------------------*/

package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCustApplicationDtlStub;
import com.newgen.stub.InqCustApplicationDtlStub.Application_Details_type0;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReq;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReqMsg;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReq_type0;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsRes;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsResMsg;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsRes_type0;
import com.newgen.stub.InqCustApplicationDtlStub.HeaderType;
import com.newgen.stub.InqCustApplicationDtlStub.Loans_and_Card_Pending_Applications_type0;
import com.newgen.stub.InqCustApplicationDtlStub.Pending_Application_Details_type0;


public class FetchLAPSResult extends WebServiceHandler{
	
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
	
	public String LAPSResult(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("Laps_Log", "Fuction called LAPSResult");
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
//			String sCustomerID= xmlDataParser.getValueOf("CustID");	
//			String sEidaNumber=xmlDataParser.getValueOf("EidaNumber");
//			String sPassportNo=xmlDataParser.getValueOf("PassportNo");
			String ref_no=xmlDataParser.getValueOf("REF_NO");			
			LogGEN.writeTrace("Laps_Log", "read Property successfully");
			
			InqCustApplicationDtlStub inqCustApplicationDtlStub= new InqCustApplicationDtlStub(sWSDLPath);
			GetPendingApplicationDetailsReqMsg getAppReqMsg= new GetPendingApplicationDetailsReqMsg();
			GetPendingApplicationDetailsReq_type0 reqTyp0= new GetPendingApplicationDetailsReq_type0();			

			@SuppressWarnings("unused")
			GetPendingApplicationDetailsReq inq_cust_application_dtl_Req = new GetPendingApplicationDetailsReq();
			@SuppressWarnings("unused")
			GetPendingApplicationDetailsRes inq_cust_application_dtl_Res = new GetPendingApplicationDetailsRes();
			@SuppressWarnings("unused")
			GetPendingApplicationDetailsResMsg res_msg = new GetPendingApplicationDetailsResMsg();
			//GetPendingApplicationDetailsReqMsg req_msg = new GetPendingApplicationDetailsReqMsg();		
			@SuppressWarnings("unused")
			GetPendingApplicationDetailsReq_type0 req = new GetPendingApplicationDetailsReq_type0();
			//setting values of input xml
			reqTyp0.setApplNo("");
			reqTyp0.setCID(xmlDataParser.getValueOf("CustID"));
			reqTyp0.setEIDANumber(xmlDataParser.getValueOf("EidaNumber"));
			reqTyp0.setFullName("");
			reqTyp0.setMobileNumber("");
			reqTyp0.setPassportNumber(xmlDataParser.getValueOf("PassportNo"));			
			// setting header here
			getAppReqMsg.setHeader(setHeaderDtls(sDate,ref_no,xmlDataParser.getValueOf("SENDERID")));
			
			getAppReqMsg.setGetPendingApplicationDetailsReq(reqTyp0);
			
			inqCustApplicationDtlStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=inqCustApplicationDtlStub.getInputXml(getAppReqMsg);
			
			LogGEN.writeTrace("Laps_Log","Input XML "+xmlInput);
			
			GetPendingApplicationDetailsResMsg serviceRes=inqCustApplicationDtlStub.inqCustApplicationDtl_Oper(getAppReqMsg);
			sOrgRes=inqCustApplicationDtlStub.custApplicationDtlMsg;
			LogGEN.writeTrace("Laps_Log" , "Output xml"+ sOrgRes);
			
			
			HeaderType header_input=serviceRes.getHeader();			
			sReturnCode= header_input.getReturnCode();
		    sErrorDetail=header_input.getErrorDetail();
		    sErrorDesc = header_input.getErrorDescription();
		    LogGEN.writeTrace("Laps_Log", "Return Code---"+sReturnCode);
		    LogGEN.writeTrace("Laps_Log", "Error Detail---"+sErrorDetail);
		    LogGEN.writeTrace("Laps_Log", "Error Description---"+sErrorDesc);		    
		    
		    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
		    	GetPendingApplicationDetailsRes_type0 serviceResTyp0=serviceRes.getGetPendingApplicationDetailsRes();
		    	Pending_Application_Details_type0 pendAppDtlsTyp0= serviceResTyp0.getPending_Application_Details();
				Application_Details_type0 appDtlsTyp0[]= pendAppDtlsTyp0.getApplication_Details();
				StringBuffer gridRecordBuffer= new StringBuffer();
				if(appDtlsTyp0!=null){
					
					gridRecordBuffer.append("<ListItems>");					
					for(int i=0,count=0;i<appDtlsTyp0.length;i++)
					{
						Loans_and_Card_Pending_Applications_type0 loanCardPndApp[]= appDtlsTyp0[i].getLoans_and_Card_Pending_Applications();						
						for(int j=0;j<loanCardPndApp.length;j++)
						{
							 LogGEN.writeTrace("Laps_Log", "Customer ID for  ---" + i + loanCardPndApp[j].getCustomerID());
							if(loanCardPndApp[j].getCustomerID()!=null 
									&& ! loanCardPndApp[j].getCustomerID().isEmpty()){
								gridRecordBuffer.append("<ListItem><CustomerID>");
								gridRecordBuffer.append(loanCardPndApp[j].getCustomerID());
								gridRecordBuffer.append("</CustomerID><CustomerName>");
								gridRecordBuffer.append(loanCardPndApp[j].getCustomerName());
								gridRecordBuffer.append("</CustomerName><PassportNo>");
								gridRecordBuffer.append(loanCardPndApp[j].getPassportNo());
								gridRecordBuffer.append("</PassportNo><MobileNo>");
								gridRecordBuffer.append(loanCardPndApp[j].getMobileNo());
								gridRecordBuffer.append("</MobileNo><EidaNo>");
								gridRecordBuffer.append(loanCardPndApp[j].getEIDANumber());
								gridRecordBuffer.append("</EidaNo></ListItem>");
							}
							count++;
						}
					}
					gridRecordBuffer.append("</ListItems>");
				}
				
				
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>FETCH_LAPS_RESULT</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Status>"+sErrorDesc+"</Status>" +		
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<Customer>"+gridRecordBuffer.toString()+"</Customer>"+				
				"</Output>";				
			} else
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>FETCH_LAPS_RESULT</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +	
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}
		    LogGEN.writeTrace("Laps_Log", "Parse Response sOutput ---"+sOutput);
			
		} catch (IOException e) {
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FETCH_LAPS_RESULT</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch LAPS result</td></Output>";
			e.printStackTrace();			
		}finally{
			
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><FETCH_LAPS_RESULT</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch LAPS result</td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			
			
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("Calltype");
			sCabinet=xmlDataParser.getValueOf("EngineName");
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
			sHandler.readCabProperty("FETCH_LAPS_RESULT");
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			LogGEN.writeTrace("Laps_Log", " sWSDLPath " +sWSDLPath );
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("Laps_Log",sw.toString());
			e.printStackTrace();			
		}
	}
	
	
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid){
		
		HeaderType headerType= new HeaderType();
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCustApplicationDtl");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);			
		headerType.setSenderID(sHandler.setSenderId(senderid)); 
		headerType.setReqTimeStamp(sDate);
		headerType.setCredentials(loggedinuser);
		
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
			LogGEN.writeTrace("Laps_Log",sw.toString());
			e.printStackTrace();
		}
	}
	

}
