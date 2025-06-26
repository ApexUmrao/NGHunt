package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModLendingAutomationStub;
import com.newgen.dscop.stub.ModLendingAutomationStub.CustomerDetails_type0;
import com.newgen.dscop.stub.ModLendingAutomationStub.FetchCustomerDelinquencyDtlsReqMsg;
import com.newgen.dscop.stub.ModLendingAutomationStub.FetchCustomerDelinquencyDtlsReq_type0;
import com.newgen.dscop.stub.ModLendingAutomationStub.FetchCustomerDelinquencyDtlsResMsg;
import com.newgen.dscop.stub.ModLendingAutomationStub.FetchCustomerDelinquencyDtlsRes_type0;
import com.newgen.dscop.stub.ModLendingAutomationStub.HeaderType;

public class ModLendingAutomation extends DSCOPServiceHandler
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
	public String modLending(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called Modlending");
		LogGEN.writeTrace("CBG_Log", "Modlending sInputXML---"+sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModLendingAutomation");
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Delinquent_Collection TIME_OUT: "+lTimeOut);


			String customerId=xmlDataParser.getValueOf("cust_id");
			String emirates_id=xmlDataParser.getValueOf("emirates_id");
			String mobile_no=xmlDataParser.getValueOf("mobile_no");
			String ref_no=xmlDataParser.getValueOf("REF_NO");

			LogGEN.writeTrace("CBG_Log", "customerId"+customerId);
			LogGEN.writeTrace("CBG_Log", "emirates_id"+emirates_id);
			LogGEN.writeTrace("CBG_Log", "mobile_no"+mobile_no);
			LogGEN.writeTrace("CBG_Log", "ref_no"+ref_no);
			ModLendingAutomationStub mod_lend_stub =new ModLendingAutomationStub(sWSDLPath);



			FetchCustomerDelinquencyDtlsReq_type0 req_type0 = new FetchCustomerDelinquencyDtlsReq_type0();
			FetchCustomerDelinquencyDtlsReqMsg ReqMsg = new FetchCustomerDelinquencyDtlsReqMsg();

			HeaderType Header_Input = new HeaderType();

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModLendingAutomation");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);

			LogGEN.writeTrace("CBG_Log", "All values set11");

			if(customerId != null || customerId != "" || !customerId.isEmpty())
			{
				req_type0.setCustomerID(customerId);
			}

			if(emirates_id != null || emirates_id != "" || !emirates_id.isEmpty())
			{
				req_type0.setEmiratesID(emirates_id);
			}

			if(mobile_no != null || mobile_no != "" || !mobile_no.isEmpty())
			{
				req_type0.setMobileNumber(mobile_no);
			}


			ReqMsg.setFetchCustomerDelinquencyDtlsReq(req_type0);
			ReqMsg.setHeader(Header_Input);

			mod_lend_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=mod_lend_stub.getinputXML(ReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml"+xmlInput);
			LogGEN.writeTrace("CBG_Log", "inupt xml inside of stub"+mod_lend_stub.requestModLend);
			FetchCustomerDelinquencyDtlsResMsg res_msg= mod_lend_stub.fetchCustomerDelinquencyDtls_Oper(ReqMsg);
			sOrg_Output = mod_lend_stub.responseModLend;
			LogGEN.writeTrace("CBG_Log", "output xml xml"+sOrg_Output);
			HeaderType Header_output = new HeaderType();
			Header_output = res_msg.getHeader();

			sReturnCode=  Header_output.getReturnCode();
			sErrorDetail=Header_output.getErrorDetail();
			sErrorDesc = Header_output.getErrorDescription();
			System.out.println(sErrorDetail);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				FetchCustomerDelinquencyDtlsRes_type0 fetchCustRes = new FetchCustomerDelinquencyDtlsRes_type0();
				fetchCustRes=res_msg.getFetchCustomerDelinquencyDtlsRes();
				CustomerDetails_type0[] cust_details = null;
				if (fetchCustRes != null)
				{
					cust_details = fetchCustRes.getCustomerDetails();
				}

				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>ModLendingAutomation</Option>" +
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
					sOutput = sOutput + "<fetchCustomerCount>" + cust_details.length + "</fetchCustomerCount><customerList>";
					for(int i =0; i<cust_details.length; i++)
					{
						sOutput+="<customerDetails>"+
								"<customerId>"+cust_details[i].getCustomerId()+"</customerId>"+
								"<customerName>"+cust_details[i].getCustomerName()+"</customerName>"+
								"<emiratesId>"+cust_details[i].getEmiratesId()+"</emiratesId>"+
								"<passportNumber>"+cust_details[i].getPassportNumber()+"</passportNumber>"+
								"<mobileNumber>"+cust_details[i].getMobileNumber()+"</mobileNumber>"+
								"<productCode>"+cust_details[i].getProductCode()+"</productCode>"+
								"<productType>"+cust_details[i].getProductType()+"</productType>"+
								"<delinquencyString>"+cust_details[i].getDelinquencyString()+"</delinquencyString>"+
								"<totalOutstanding>"+cust_details[i].getTotalOutstanding()+"</totalOutstanding>"+
								"<principleOutstanding>"+cust_details[i].getPrincipleOutstanding()+"</principleOutstanding>"+
								"<interestOutstanding>"+cust_details[i].getInterestOutstanding()+"</interestOutstanding>"+
								"<pastDues>"+cust_details[i].getPastDues()+"</pastDues>"+
								"<dayPassedDues>"+cust_details[i].getDayPassedDues()+"</dayPassedDues>"+
								"<cardBlockcode>"+cust_details[i].getCardBlockcode()+"</cardBlockcode>"+
								"<cardUtilization>"+cust_details[i].getCardUtilization()+"</cardUtilization>"+
								"</customerDetails>";
					}
					System.out.println(sOutput);
					sOutput+="</customerList></Output>";
				}
				sOutput = sOutput + "<fetchCustomerCount>0</fetchCustomerCount><customerList></customerList></Output>";
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModLendingAutomation</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform ModLendingAutomation service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModLendingAutomation</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform ModLendingAutomation service</Status></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModLendingAutomation</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform ModLendingAutomation service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModLendingAutomation</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform ModLendingAutomation service</Status></Output>";
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


