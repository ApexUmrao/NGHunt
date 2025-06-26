package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCustApplicationDtlStub;
import com.newgen.stub.InqCustApplicationDtlStub.Application_Details_type0;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReqMsg;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReq_type0;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsResMsg;
import com.newgen.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsRes_type0;
import com.newgen.stub.InqCustApplicationDtlStub.HeaderType;
import com.newgen.stub.InqCustApplicationDtlStub.Loans_and_Card_Pending_Applications_type0;
import com.newgen.stub.InqCustApplicationDtlStub.Pending_Application_Details_type0;


public class InqCustApplicationDtl extends WebServiceHandler  {
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
	
	
	String InqCustApplicationDtl_ouput = null;
	WebServiceHandler sHandler;


	@SuppressWarnings({ "finally", "unused" })
	public String dedupeEIDALaps(String sInputXML) 
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
		try
		{
			LogGEN.writeTrace("Laps_Log", "Reading wsconfig...");
			loadWSDLDtls(sHandler);							
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl USER: "+sUser);
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl TIME_OUT: "+lTimeOut);
			
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl sDate---"+sDate);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String senderId = sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID"));
			InqCustApplicationDtlStub inq_cust_application_dtl_stub = new InqCustApplicationDtlStub(sWSDLPath);			
			GetPendingApplicationDetailsResMsg res_msg = new GetPendingApplicationDetailsResMsg();
			GetPendingApplicationDetailsReqMsg req_msg = new GetPendingApplicationDetailsReqMsg();		
			GetPendingApplicationDetailsReq_type0 req = new GetPendingApplicationDetailsReq_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustApplicationDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			req.setApplNo(xmlDataParser.getValueOf("ApplNo"));
			req.setCID(xmlDataParser.getValueOf("CustID"));
			req.setEIDANumber(xmlDataParser.getValueOf("EidaNumber"));
			req.setFullName(xmlDataParser.getValueOf("FullName"));
			req.setMobileNumber(xmlDataParser.getValueOf("MobileNumber"));
			req.setPassportNumber(xmlDataParser.getValueOf("PassportNo"));


			req_msg.setHeader(Header_Input);
			req_msg.setGetPendingApplicationDetailsReq(req);
			xmlInput = inq_cust_application_dtl_stub.getInputXml(req_msg);
			LogGEN.writeTrace("Laps_Log", "CustApplicationDtl InputXML: " + xmlInput);
			inq_cust_application_dtl_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg = inq_cust_application_dtl_stub.inqCustApplicationDtl_Oper(req_msg);
			sOrgRes = inq_cust_application_dtl_stub.custApplicationDtlMsg;
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("Laps_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("Laps_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("Laps_Log", "Error Description---"+sErrorDesc);
			LogGEN.writeTrace("Laps_Log", "InqCustApplicationDtl_ouputoutput: "+res_msg);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("Laps_Log", "FETCH_LAPS_RESULT Successful Result");

				GetPendingApplicationDetailsRes_type0 res=new GetPendingApplicationDetailsRes_type0();//response object
				res=res_msg.getGetPendingApplicationDetailsRes();
				Pending_Application_Details_type0 responseMsg = res.getPending_Application_Details();
				Application_Details_type0[] response = responseMsg.getApplication_Details();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>FETCH_LAPS_RESULT</Option>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
						"<returnCode>"+ sReturnCode +"</returnCode><errorDetail>"+ sErrorDetail +"</errorDetail>";				
				
				StringBuffer gridRecordBuffer= new StringBuffer();
				if(response!=null){
					
					gridRecordBuffer.append("<ListItems>");
					for (int j = 0; j < response.length; j++) {
						Application_Details_type0 application_Details_type0  = response[j];
						Loans_and_Card_Pending_Applications_type0[] loanCardPndApp = application_Details_type0.getLoans_and_Card_Pending_Applications();
						
						 LogGEN.writeTrace("Laps_Log", "Customer ID for  ---" + j + loanCardPndApp[j].getCustomerID());
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
					}
					gridRecordBuffer.append("</ListItems>");
				}
				sOutput += "<Customer>"+gridRecordBuffer.toString()+"</Customer></Output>";
				LogGEN.writeTrace("Laps_Log", "InqCustApplication Output XML--- "+sOutput);
			}
			else
			{
				GetPendingApplicationDetailsRes_type0 res=new GetPendingApplicationDetailsRes_type0();
				res=res_msg.getGetPendingApplicationDetailsRes();
				LogGEN.writeTrace("Laps_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FETCH_LAPS_RESULT</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription>" +
								"<td>Unable to fetch LAPS result.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("Laps_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Laps_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FETCH_LAPS_RESULT</Option>" +
					"<returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription>" +
							"<td>Unable to fetch LAPS result</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Laps_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>FETCH_LAPS_RESULT</Option>" +
						"<returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription>" +
								"<td>Unable to fetch LAPS result</td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";			

			loadJSTDtls(sHandler,"JTS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("Laps_Log", inputXml);
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
			LogGEN.writeTrace("Laps_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
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
