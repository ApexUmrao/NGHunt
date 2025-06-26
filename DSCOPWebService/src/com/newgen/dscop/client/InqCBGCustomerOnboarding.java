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
import com.newgen.dscop.stub.InqCBGCustomerOnboardingStub;
import com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.FetchCustomerScoreReqMsg;
import com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.FetchCustomerScoreReq_type0;
import com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.FetchCustomerScoreResMsg;
import com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.FetchCustomerScoreRes_type0;
import com.newgen.dscop.stub.InqCBGCustomerOnboardingStub.HeaderType;
public class InqCBGCustomerOnboarding extends DSCOPServiceHandler {
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String customerOnBoarding_ouput = null;
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	
	public String CustomerScore(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called customerScore");
		LogGEN.writeTrace("CBG_Log", "InqCBGCustomerOnboarding sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		String xmlInput="";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);	
		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdl=loadWSDLDtls(sHandler);
			String ref_no=xmlDataParser.getValueOf("Ref_No");
			String senderId = xmlDataParser.getValueOf("SenderId");
			InqCBGCustomerOnboardingStub cbg_cust_onboarding_stub=new InqCBGCustomerOnboardingStub(wsdl);
			FetchCustomerScoreReqMsg req_msg=new FetchCustomerScoreReqMsg();
			FetchCustomerScoreReq_type0 req_type0=new FetchCustomerScoreReq_type0();
			FetchCustomerScoreResMsg res_msg=new FetchCustomerScoreResMsg();
			req_msg.setHeader(setHeaderDtls(sDate, ref_no,senderId));
			req_type0.setInputData(xmlDataParser.getValueOf("inputData"));
			req_type0.setScoringType(xmlDataParser.getValueOf("scoringType"));
			req_type0.setCallingAppID(xmlDataParser.getValueOf("callingAppID"));
			req_msg.setFetchCustomerScoreReq(req_type0);
			xmlInput = cbg_cust_onboarding_stub.getInputXml(req_msg);
			LogGEN.writeTrace("CBG_Log", "CustomerOnboarding InputXML: " + xmlInput);
			cbg_cust_onboarding_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg=cbg_cust_onboarding_stub.fetchCustomerScore_Oper(req_msg);
			
			customerOnBoarding_ouput=cbg_cust_onboarding_stub.res_CustomerOnBoarding;
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding output" + customerOnBoarding_ouput);
			
			HeaderType header =res_msg.getHeader();
			
			sReturnCode=header.getReturnCode();
			sErrorDetail=header.getErrorDetail();
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				FetchCustomerScoreRes_type0 cust_score_res_type0=new FetchCustomerScoreRes_type0();
				cust_score_res_type0=res_msg.getFetchCustomerScoreRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>Tracer</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<fetchCustomerScoreRes>"+
				"<outputData>"+cust_score_res_type0.getOutputData()+"</outputData>"+
				"<scoringType>"+cust_score_res_type0.getScoringType()+"</scoringType>"+
				"<callingAppID>"+cust_score_res_type0.getCallingAppID()+"</callingAppID>"+
				
				"<status>"+cust_score_res_type0.getStatus()+"</status>"+
				"<reason>"+cust_score_res_type0.getReason()+"</reason>"+
				"</fetchCustomerScoreRes>"+	
				"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block--------"+sOutput);
				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Tracer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add Tracer </td></Output>";
			}
			
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log",sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Tracer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch the customer score </td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","Inq_cbg_customer_onboarding outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Tracer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch the customer score </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),customerOnBoarding_ouput.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return sOutput;
	}
	
	//Inq_cbg_customer_onboarding
	
	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Inq_cbg_customer_onboarding");
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}
	
	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding Setting CBG props...");	
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("TRACER");
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");	 
		return headerType;
	}
}
