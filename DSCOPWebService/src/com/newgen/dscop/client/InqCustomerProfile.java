package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustChannelProfileStub;
import com.newgen.dscop.stub.InqCustChannelProfileStub.HeaderType;
import com.newgen.dscop.stub.InqCustChannelProfileStub.InqCustChannelProfileReqMsg;
import com.newgen.dscop.stub.InqCustChannelProfileStub.InqCustChannelProfileReq_type0;
import com.newgen.dscop.stub.InqCustChannelProfileStub.InqCustChannelProfileResMsg;
import com.newgen.dscop.stub.InqCustChannelProfileStub.InqCustChannelProfileRes_type0;

public class InqCustomerProfile extends DSCOPServiceHandler {
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	
	@SuppressWarnings("finally")
	public String FetchCustomerProfile(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called InqCustomerProfile");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sOrg_Output="";			
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerProfile");
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath = (String)wsConfig.get(0);
			sCabinet = (String)wsConfig.get(1);
			sUser = (String)wsConfig.get(2);
			sLoginReq = Boolean.valueOf((String)wsConfig.get(3));
			sPassword = (String)wsConfig.get(4);
			lTimeOut = Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerProfile TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);
			
			String sCustomerID = xmlDataParser.getValueOf("CUST_ID");
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);			
			
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate+" sWSDLPath "+sWSDLPath);
			//sDate="06/08/2013 18:33:10";
			InqCustChannelProfileStub custProfileStub = null;
			try
			{
				custProfileStub = new InqCustChannelProfileStub(sWSDLPath);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error---"+e.getMessage());
			}
			InqCustChannelProfileReqMsg reqMsg = new InqCustChannelProfileReqMsg();						
			InqCustChannelProfileResMsg resMsg=new InqCustChannelProfileResMsg();
			
			
			LogGEN.writeTrace("CBG_Log", "All Objects created");	  
					
			reqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			InqCustChannelProfileReq_type0 reqType0 = new InqCustChannelProfileReq_type0();
			reqType0.setCustomerId(sCustomerID);
			reqMsg.setInqCustChannelProfileReq(reqType0);
		    
		    LogGEN.writeTrace("CBG_Log", "All values set");
		    
			//xmlInput = custProfileStub.getInputXml(reqMsg);
			custProfileStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			resMsg = custProfileStub.inqCustChannelProfile_Oper(reqMsg);
		    sOrg_Output = custProfileStub.resMsg;
		    
		    HeaderType Header_Input= resMsg.getHeader();
		    sReturnCode= Header_Input.getReturnCode();
		    sErrorDetail=Header_Input.getErrorDetail();
		    sErrorDesc = Header_Input.getErrorDescription();
		    
		    LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
		    LogGEN.writeTrace("CBG_Log", "sOrg_Output---"+sOrg_Output);
		    LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
		    LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
		   
		    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
		    	InqCustChannelProfileRes_type0 profileRes_type0 = resMsg.getInqCustChannelProfileRes();
			    StringBuffer tempString = new StringBuffer();
			
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>InqCustomerProfile</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<InqCustChannelProfileRes>";
					
					tempString.append("<custFullName>"+profileRes_type0.getCustFullName()+"</custFullName>")
					.append("<custShortName>"+profileRes_type0.getCustShortName()+"</custShortName>")
					.append("<custCategory>"+profileRes_type0.getCustCategory()+"</custCategory>")
					.append("<custIslamicFlag>"+profileRes_type0.getCustIslamicFlag()+"</custIslamicFlag>")
					.append("<custMobile>"+profileRes_type0.getCustMobile()+"</custMobile>")
					.append("<custEmail>"+profileRes_type0.getCustEmail()+"</custEmail>")
					.append("<custSignatureType>"+profileRes_type0.getCustSignatureType()+"</custSignatureType>")
					.append("<staffFlag>"+profileRes_type0.getStaffFlag()+"</staffFlag>")
					.append("<memoFlag>"+profileRes_type0.getMemoFlag()+"</memoFlag>")
					.append("<memoDesc>"+profileRes_type0.getMemoDesc()+"</memoDesc>")
					.append("<memoSeverity>"+profileRes_type0.getMemoSeverity()+"</memoSeverity>")
					.append("<custRMCode>"+profileRes_type0.getCustRMCode()+"</custRMCode>")
					.append("<custRMName>"+profileRes_type0.getCustRMName()+"</custRMName>")
					.append("<custRMEmail>"+profileRes_type0.getCustRMEmail()+"</custRMEmail>")
					.append("<custEIDANo>"+profileRes_type0.getCustEIDANo()+"</custEIDANo>")
					.append("<custEIDAStatusFlag>"+profileRes_type0.getCustEIDAStatusFlag()+"</custEIDAStatusFlag>")
					.append("<custCommonTnCStatus>"+profileRes_type0.getCustCommonTnCStatus()+"</custCommonTnCStatus>")
					.append("<residentStatus>"+profileRes_type0.getResidentStatus()+"</residentStatus>")
					.append("<minorFlag>"+profileRes_type0.getMinorFlag()+"</minorFlag>")
					.append("<custPassportNo>"+profileRes_type0.getCustPassportNo()+"</custPassportNo>")
					.append("<custPassportIssueDt>"+profileRes_type0.getCustPassportIssueDt()+"</custPassportIssueDt>")
					.append("<custPassportExpiryDt>"+profileRes_type0.getCustPassportExpiryDt()+"</custPassportExpiryDt>")
					.append("<custVisaNo>"+profileRes_type0.getCustVisaNo()+"</custVisaNo>")
					.append("<custVisaIssueDt>"+profileRes_type0.getCustVisaIssueDt()+"</custVisaIssueDt>")
					.append("<custVisaExpiryDt>"+profileRes_type0.getCustVisaExpiryDt()+"</custVisaExpiryDt>")
					.append("<nationalityCode>"+profileRes_type0.getNationalityCode()+"</nationalityCode>")
					.append("<nationalityDesc>"+profileRes_type0.getNationalityDesc()+"</nationalityDesc>")
					.append("<dateOfBirth>"+profileRes_type0.getDateOfBirth()+"</dateOfBirth>")
					.append("<profitCenterCode>"+profileRes_type0.getProfitCenterCode()+"</profitCenterCode>")
					.append("<salaryAmount>"+profileRes_type0.getSalaryAmount()+"</salaryAmount>")
					.append("<salaryDate>"+profileRes_type0.getSalaryDate()+"</salaryDate>");					
				
					sOutput += tempString +"</InqCustChannelProfileRes>" +
					"</Output>";
					//System.out.println(sOutput);
					LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);
			}
		    else
			{
		    	LogGEN.writeTrace("CBG_Log", "Failed");
		    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerProfile</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			}
		  
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerProfile</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerProfile</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
				Status="Success";
			}
			else {
				Status="Failure";
			}

			
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				//sOrg_Output=xmlInput;
				String inputXml=xmlInput;
				LogGEN.writeTrace("CBG_Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
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
					 LogGEN.writeTrace("CBG_Log", "Error inserting---"+e.getMessage());
				 }
				 DBConnection con=new DBConnection();
				 //String retVal=con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);
				 
				 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				 LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
				 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				
				 LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCustChannelProfile");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId);
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername(loggedinuser);
		headerType.setCredentials(loggedinuser);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
