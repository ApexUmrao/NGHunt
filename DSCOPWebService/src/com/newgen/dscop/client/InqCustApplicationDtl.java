package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustApplicationDtlStub;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.Application_Details_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReqMsg;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsReq_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsResMsg;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.GetPendingApplicationDetailsRes_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.HeaderType;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.Loans_and_Card_Pending_Applications_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.Pending_Application_Details_type0;


public class InqCustApplicationDtl extends DSCOPServiceHandler  {
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String InqCustApplicationDtl_ouput = null;



	@SuppressWarnings("finally")
	public String dedupeEIDALaps(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called dedupeEIDALaps");
		LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput = "";
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("InqCustApplicationDtl");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustApplicationDtl");
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl TIME_OUT: "+lTimeOut);

			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl sDate---"+sDate);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("SenderId");


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
			req.setCID(xmlDataParser.getValueOf("CID"));
			req.setEIDANumber(xmlDataParser.getValueOf("EIDANumber"));
			req.setFullName(xmlDataParser.getValueOf("FullName"));
			req.setMobileNumber(xmlDataParser.getValueOf("MobileNumber"));
			req.setPassportNumber(xmlDataParser.getValueOf("PassportNumber"));
			req.setDeDupeRuleId(xmlDataParser.getValueOf("DeDupeRuleId"));

			req_msg.setHeader(Header_Input);
			req_msg.setGetPendingApplicationDetailsReq(req);
			xmlInput = inq_cust_application_dtl_stub.getInputXml(req_msg);
			//			LogGEN.writeTrace("CBG_Log", "CustApplicationDtl InputXML: " + xmlInput);
			inq_cust_application_dtl_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			//res_msg = inq_cust_application_dtl_stub.inqCustApplicationDtl_Oper(req_msg);
			InqCustApplicationDtl_ouput = inq_cust_application_dtl_stub.inqCustApplicationDtl_Oper(req_msg);
			//InqCustApplicationDtl_ouput = inq_cust_application_dtl_stub.custApplicationDtlMsg;
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl_ouput: "+InqCustApplicationDtl_ouput);
			com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(InqCustApplicationDtl_ouput);
			sReturnCode = parser.getValueOf("returnCode");
			sErrorDesc = parser.getValueOf("errorDescription");
			sErrorDetail = parser.getValueOf("errorDetail");
//			Header_Input = res_msg.getHeader();
//			sReturnCode = Header_Input.getReturnCode();
//			sErrorDetail = Header_Input.getErrorDetail();
//			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			//			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl_ouputoutput: "+res_msg);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "InqCustApplication Successful Result");

//				GetPendingApplicationDetailsRes_type0 res=new GetPendingApplicationDetailsRes_type0();//response object
//				res=res_msg.getGetPendingApplicationDetailsRes();
//				Pending_Application_Details_type0 responseMsg = res.getPending_Application_Details();
//				Application_Details_type0[] response = responseMsg.getApplication_Details();

//				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
//						"<Option>InqCustApplicationDtl_RESPONSE</Option>" +
//						"<returnCode>"+ sReturnCode +"</returnCode>" +
//						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
//						"<returnCode>"+ sReturnCode +"</returnCode><errorDetail>"+ sErrorDetail +"</errorDetail>";				
//				sOutput += "<GetPendingApplicationDetailsRes>" +
//						"<Pending_Application_Details>";
//
//				if(response!=null){
//					for (int i = 0; i < response.length; i++) {
//						Application_Details_type0 application_Details_type0  = response[i];
//						Loans_and_Card_Pending_Applications_type0[] param = application_Details_type0.getLoans_and_Card_Pending_Applications();
//						sOutput += "<application_Details>"+
//								"<approvalCode>"+ application_Details_type0.getApprovalCode() +"</approvalCode>";
//						for (Loans_and_Card_Pending_Applications_type0 loans_and_Card_Pending_Applications_type0 : param) {
//							sOutput +="<appStatus>"+ loans_and_Card_Pending_Applications_type0.getAppStatus() +"</appStatus>"+
//									"<CustomerID>"+ loans_and_Card_Pending_Applications_type0.getCustomerID() +"</CustomerID>"+
//									"<SubmissionDate>"+ loans_and_Card_Pending_Applications_type0.getSubmissionDate() +"</SubmissionDate>"+
//									"<decisionDate>"+ loans_and_Card_Pending_Applications_type0.getDecisionDate() +"</decisionDate>";
//						}
//						sOutput +=	"</application_Details>";
//					}
//				}
//				sOutput += "</Pending_Application_Details>" +	
//						"</GetPendingApplicationDetailsRes>";
//				sOutput += "</Output>";
				sOutput = InqCustApplicationDtl_ouput;
				LogGEN.writeTrace("CBG_Log", "InqCustApplication Output XML--- "+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustApplicationDtl_RESPONSE</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>InqCustApplicationDtl failed.</td></Output>";
			}
		}
		catch (Throwable e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e);
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustApplicationDtl_RESPONSE</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>InqCustApplicationDtl failed.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustApplicationDtl_RESPONSE</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>InqCustApplicationDtl failed.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");

			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				e.getMessage();
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," InqCustApplicationDtl Query : finally :"+Query);
			//			LogGEN.writeTrace("CBG_Log","InqCustApplicationDtl_ouput : finally :"+InqCustApplicationDtl_ouput);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),InqCustApplicationDtl_ouput.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
