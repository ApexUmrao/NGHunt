package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerDetailsStub;
import com.newgen.dscop.stub.InqCustomerDetailsStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerDetailsStub.InqCustomerDetailsReqMsg;
import com.newgen.dscop.stub.InqCustomerDetailsStub.InqCustomerDetailsReq_type0;
import com.newgen.dscop.stub.InqCustomerDetailsStub.InqCustomerDetailsResMsg;
import com.newgen.dscop.stub.InqCustomerDetailsStub.InqCustomerDetailsRes_type0;

public class InqCustomerDetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	String xmlInput="";
	@SuppressWarnings("finally")
	public String custDetails(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called InqCustomerDetails");
		LogGEN.writeTrace("CBG_Log", "InqCustomerDetails sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
	
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		try
		{

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerDetails");
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDetails TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId=xmlDataParser.getValueOf("cust_id");
			String correlationID=xmlDataParser.getValueOf("CorrelationID");

			LogGEN.writeTrace("CBG_Log", "customerId :"+customerId);
			LogGEN.writeTrace("CBG_Log", "ref_no :"+ref_no);
		
			InqCustomerDetailsStub cust_detail_stub =new InqCustomerDetailsStub(sWSDLPath);

			HeaderType Header_Input = new HeaderType();
		
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustomerDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);
			Header_Input.setCorrelationID(correlationID);
			InqCustomerDetailsReq_type0 inqCustomerDetailsReq_type0 = new InqCustomerDetailsReq_type0();
			InqCustomerDetailsReqMsg  inqCustomerDetailsReqMsg  = new InqCustomerDetailsReqMsg ();
			
			if(customerId != null || customerId != "" || !customerId.isEmpty())
			{
				inqCustomerDetailsReq_type0.setCustomerId(customerId);
			}
			
			inqCustomerDetailsReqMsg.setHeader(Header_Input);
			inqCustomerDetailsReqMsg.setInqCustomerDetailsReq(inqCustomerDetailsReq_type0);
			
			LogGEN.writeTrace("CBG_Log", "All values set11");
			
			cust_detail_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=cust_detail_stub.getinputXML(inqCustomerDetailsReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml : "+xmlInput);
			InqCustomerDetailsResMsg res_msg= cust_detail_stub.inqCustomerDetails_Oper(inqCustomerDetailsReqMsg);
			sOrg_Output = cust_detail_stub.responseCustDeatils;
			LogGEN.writeTrace("CBG_Log", "output xml : "+sOrg_Output);
			HeaderType Header_output = new HeaderType();
			Header_output = res_msg.getHeader();
			
			sReturnCode=  Header_output.getReturnCode();
			sErrorDetail=Header_output.getErrorDetail();
			sErrorDesc = Header_output.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "sReturnCode :"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "sErrorDetail :"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sErrorDesc);
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				InqCustomerDetailsRes_type0 fetchCustRes = new InqCustomerDetailsRes_type0();
				fetchCustRes = res_msg.getInqCustomerDetailsRes();
			
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustomerDetails</Option>" +
						"<header>"+
					    "<usecaseID>"+Header_output.getUsecaseID()+"</usecaseID>"+
					    "<serviceName>"+Header_output.getServiceName()+"</serviceName>"+
					    "<versionNo>"+Header_output.getVersionNo()+"</versionNo>"+
					    "<serviceAction>"+Header_output.getServiceAction()+"</serviceAction>"+
					    "<correlationID>"+Header_output.getCorrelationID()+"</correlationID>"+
					    "<sysRefNumber>"+Header_output.getSysRefNumber()+"</sysRefNumber>"+
					    "<consumer>"+Header_output.getConsumer()+"</consumer>"+
					    "<reqTimeStamp>"+Header_output.getRepTimeStamp()+"</reqTimeStamp>"+
					    "<username>"+Header_output.getUsername()+"</username>"+
					    "<credentials>"+Header_output.getCredentials()+"</credentials>"+
					    "<returnCode>"+Header_output.getReturnCode()+"</returnCode>"+
					    "<errorDescription>"+Header_output.getErrorDescription()+"</errorDescription>"+
					    "<errorDetail>"+Header_output.getErrorDetail()+"</errorDetail>"+
					    "</header>";
				
				if (fetchCustRes != null) 
				{	
					sOutput += "<InqCustomerDetailsRes>"
				            +"<customerId>"+fetchCustRes.getCustomerId()+"</customerId>"
				            +"<customerName>"+fetchCustRes.getCustomerName()+"</customerName>"
				            +"<customerShortName>"+fetchCustRes.getCustomerShortName()+"</customerShortName>"
				            +"<customerCategory>"+fetchCustRes.getCustomerCategory()+"</customerCategory>"
				            +"<addressLine1>"+fetchCustRes.getAddressLine1()+"</addressLine1>"
				            +"<addressLine2>"+fetchCustRes.getAddressLine2()+"</addressLine2>"
				            +"<addressLine3>"+fetchCustRes.getAddressLine3()+"</addressLine3>"
				            +"<city>"+fetchCustRes.getCity()+"</city>"
				            +"<state>"+fetchCustRes.getState()+"</state>"
				            +"<country>"+fetchCustRes.getCountry()+"</country>"
				            +"<POBox>"+fetchCustRes.getPOBox()+"</POBox>"
				            +"<phoneNo>"+fetchCustRes.getPhoneNo()+"</phoneNo>"
				            +"<mobileNo>"+fetchCustRes.getMobileNo()+"</mobileNo>"
				            +"<email>"+fetchCustRes.getEmail()+"</email>"
				            +"<passportNo>"+fetchCustRes.getPassportNo()+"</passportNo>"
				            +"<RMCode>"+fetchCustRes.getRMCode()+"</RMCode>"
				            +"<RMName/>"+fetchCustRes.getRMName()+"</RMName>"
				            +"<RMMobile/>"+fetchCustRes.getRMMobile()+"</RMMobile>"
				            +"<profitCenterCode>"+fetchCustRes.getProfitCenterCode()+"</profitCenterCode>"
				            +"<profitCenterName>"+fetchCustRes.getProfitCenterName()+"</profitCenterName>"
				            +"<customerMemoFlag>"+fetchCustRes.getCustomerMemoFlag()+"</customerMemoFlag>"
				            +"<customerMemoDesc>"+fetchCustRes.getCustomerMemoDesc()+"</customerMemoDesc>"
				            +"<customerMemoSeverity>"+fetchCustRes.getCustomerMemoSeverity()+"</customerMemoSeverity>"
				            +"<economicSector>"+fetchCustRes.getEconomicSector()+"</economicSector>"
				            +"<tradeLicenseNum>"+fetchCustRes.getTradeLicenseNum()+"</tradeLicenseNum>"
				            +"<IDType>"+fetchCustRes.getIDType()+"</IDType>"
				            +"</InqCustomerDetailsRes>";
				}
				else
				{
					sOutput +=  "<InqCustomerDetailsRes>No Data Found</InqCustomerDetailsRes>"+
						"</Output>";
				}
				
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCustomerDetails service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCustomerDetails service</Status></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCustomerDetails service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCustomerDetails service</Status></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";
			try {
				//sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

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

			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," perform Delinquent service finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}


