package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.HeaderType;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateCustomerNPSDtlsReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateCustomerNPSDtlsReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateCustomerNPSDtlsResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.UpdateCustomerNPSDtlsRes_type0;

public class UpdateCustomerNPSDetails extends DSCOPServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String xmlInput="";
	String sOrgRes="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	public String updateCustomerNPSDetailsCall(String sInputXML){
		String Status="";                        
		LogGEN.writeTrace("CBG_Log", "Fuction called ModCBGCustomerOnboarding");
		LogGEN.writeTrace("CBG_Log", "UpdateCustomerNPSDetails sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try {
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdlpath=loadWSDLDtls(sHandler);             
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("SenderId");
			String correlationID = xmlDataParser.getValueOf("CorrelationID");
			LogGEN.writeTrace("CBG_Log", "read Property successfully");
			ModCBGCustomerOnboardingStub custOnboaring =new ModCBGCustomerOnboardingStub(wsdlpath);
			UpdateCustomerNPSDtlsReqMsg updateCustomerNPSDtlsReqMsg=new UpdateCustomerNPSDtlsReqMsg();
			UpdateCustomerNPSDtlsReq_type0 updateCustomerNPSDtlsReq_type0=new UpdateCustomerNPSDtlsReq_type0();

			updateCustomerNPSDtlsReqMsg.setHeader(setHeaderDtls(sDate,ref_no,senderId,correlationID));

			updateCustomerNPSDtlsReq_type0.setCustomerId(xmlDataParser.getValueOf("customerId"));
			updateCustomerNPSDtlsReq_type0.setFeedbackType(xmlDataParser.getValueOf("feedbackType"));
			updateCustomerNPSDtlsReq_type0.setNpsRating(xmlDataParser.getValueOf("npsRating"));
			updateCustomerNPSDtlsReq_type0.setInterviewDateTime(xmlDataParser.getValueOf("interviewDateTime"));
			updateCustomerNPSDtlsReq_type0.setSourcedBy(xmlDataParser.getValueOf("sourcedBy"));
			updateCustomerNPSDtlsReq_type0.setSourcedByLineManager(xmlDataParser.getValueOf("sourcedByLineManager"));
			updateCustomerNPSDtlsReq_type0.setNpsScoreReasons(xmlDataParser.getValueOf("npsScoreReasons"));
			updateCustomerNPSDtlsReq_type0.setFurtherAddressConcerns(xmlDataParser.getValueOf("furtherAddressConcerns"));
			updateCustomerNPSDtlsReq_type0.setReceiveWelcomeKit(xmlDataParser.getValueOf("receiveWelcomeKit"));
			updateCustomerNPSDtlsReq_type0.setSignUpDate(xmlDataParser.getValueOf("signUpDate"));
			updateCustomerNPSDtlsReq_type0.setSourceDepartment(xmlDataParser.getValueOf("sourceDepartment"));
			updateCustomerNPSDtlsReq_type0.setSourceSubDepartment(xmlDataParser.getValueOf("sourceSubDepartment"));
			updateCustomerNPSDtlsReq_type0.setWelcomeKitNPS(xmlDataParser.getValueOf("welcomeKitNPS"));
			updateCustomerNPSDtlsReq_type0.setStatus(xmlDataParser.getValueOf("status"));
			updateCustomerNPSDtlsReq_type0.setComments(xmlDataParser.getValueOf("comments"));
			updateCustomerNPSDtlsReq_type0.setBankingType(xmlDataParser.getValueOf("bankingType"));
			updateCustomerNPSDtlsReq_type0.setAssignToDepartment(xmlDataParser.getValueOf("assignToDepartment"));
			updateCustomerNPSDtlsReq_type0.setAssignToSubDepartment(xmlDataParser.getValueOf("assignToSubDepartment"));
			updateCustomerNPSDtlsReq_type0.setWelcomeKitNPSReasons(xmlDataParser.getValueOf("welcomeKitNPSReasons"));
			updateCustomerNPSDtlsReq_type0.setAreaOnlyforBranch(xmlDataParser.getValueOf("areaOnlyforBranch"));

			updateCustomerNPSDtlsReqMsg.setUpdateCustomerNPSDtlsReq(updateCustomerNPSDtlsReq_type0);

			custOnboaring._getServiceClient().getOptions().setTimeOutInMilliSeconds(this.lTimeOut);
			xmlInput=custOnboaring.getinputXML(updateCustomerNPSDtlsReqMsg);
			LogGEN.writeTrace("CBG_Log", "UpdateCustomerNPSDetails InputXML: " + xmlInput);
			UpdateCustomerNPSDtlsResMsg updateCustomerNPSDtlsResMsg=custOnboaring.updateCustomerNPSDtls_Oper(updateCustomerNPSDtlsReqMsg);
			sOrgRes = custOnboaring.outputXML; 
      		LogGEN.writeTrace("CBG_Log", "UpdateCustomerNPSDetails OutputXML: " + sOrgRes);
			HeaderType headerType=updateCustomerNPSDtlsResMsg.getHeader();
			sReturnCode=headerType.getReturnCode();
			sErrorDetail=headerType.getErrorDetail();
			sErrorDesc=headerType.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "Error Description--- " + sErrorDesc+"|| sReturnCode-- "+sReturnCode+"|| sErrorDetail-- "+sErrorDetail);


			if ((!(sErrorDesc.equalsIgnoreCase("Failure"))) || (!(sReturnCode.equalsIgnoreCase("1")))){
				UpdateCustomerNPSDtlsRes_type0 updateCardLimitRes_type0=new UpdateCustomerNPSDtlsRes_type0();
				updateCardLimitRes_type0=updateCustomerNPSDtlsResMsg.getUpdateCustomerNPSDtlsRes();
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Output>"
						+ "<Option>ModCBGCustomerOnboarding</Option>"
						+ "<returnCode>" + sReturnCode + "</returnCode>" 
						+ "<errorDescription>" + sErrorDetail + "</errorDescription>" + 
						"<updateCustomerNPSDtlsRes>"+
						"<feedbackrefnumber>"+updateCardLimitRes_type0.getFeedbackRefNumber()+"</feedbackrefnumber>"+
						"</updateCustomerNPSDtlsRes>"+
						"</Output>";
			}
			else{
				LogGEN.writeTrace("CBG_Log", "Failed");
				System.out.println("Failed");
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCBGCustomerOnboarding</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable MOD MIB REGISTRATION.</td></Output>";
			}

		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :" + e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :" + e.getStackTrace());
			System.out.println("Error Trace in Web Serviice :" + e.getStackTrace());
			Status = "Failure";
			sErrorDetail = e.getMessage();
			sReturnCode = "-1";
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCBGCustomerOnboarding</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable  MOD MIB REGISTRATION.</td></Output>";
			e.printStackTrace();


		}
		finally{
			LogGEN.writeTrace("CBG_Log", "outputXML.trim().length() :" + sOutput.trim().length()); 
			System.out.println("outputXML.trim().length() :" + sOutput.trim().length());
		}
		if (sOutput.trim().length() < 1)
		{
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCBGCustomerOnboarding</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to add customer.</td></Output>";
		}
		try
		{
			//sHandler.readCabProperty("JTS");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if ((sReturnCode.equalsIgnoreCase("0")) || (sReturnCode.equalsIgnoreCase("2")))
		{
			Status = "Success";
		}
		else
			Status = "Failure";
		String inputXml=xmlInput;
		String dburl = (String)currentCabPropertyMap.get("DBURL");
		String dbuser = (String)currentCabPropertyMap.get("USER");
		String dbpass = (String)currentCabPropertyMap.get("PASS");
		String winame = xmlDataParser.getValueOf("WiName");
		String sessionID = xmlDataParser.getValueOf("SessionId");
		String call_type = xmlDataParser.getValueOf("DSCOPCallType");
		sCabinet = xmlDataParser.getValueOf("EngineName");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try
		{
			dbpass = AESEncryption.decrypt(dbpass);
		}
		catch (Exception localException1)
		{
		}
		DBConnection con=new DBConnection();
		String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
		LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
		con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
		return sOutput;
	}

	private String loadWSDLDtls(DSCOPServiceHandler sHandler) {
		try {
			//sHandler.readCabProperty("ModCBGCustomerOnboarding");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("mod_cbg_customer_onboarding");
			LogGEN.writeTrace("CBG_Log", "ModCBGCustomerOnboarding WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}


	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId, String correlationID){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1");
		headerType.setServiceName("ModCBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("ITQAN");
		headerType.setReqTimeStamp(sDate);
//		headerType.setUsername(loggedinuser);
//		headerType.setCredentials(loggedinuser);
		headerType.setCorrelationID(correlationID);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");

		return headerType;
	}
}
