package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.GetLoanIslamicCommonDetailsReq;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.GetLoanIslamicCommonDetailsReqMsg;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.GetLoanIslamicCommonDetailsReq_type0;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.GetLoanIslamicCommonDetailsRes;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.GetLoanIslamicCommonDetailsRes_type0;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.GetLoanIslamicCommonDetailsResMsg;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.Header;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.HeaderType;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.Loan_Details;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.Loan_Details_type0;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.Loan_Islamic_Common_Details;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.Loan_Islamic_Common_Details_type0;
import com.newgen.dscop.stub.InqFinanceBasicInfoStub.LoanAcctNo;

public class InqFinanceBasicInfo extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	String xmlInput="";
	@SuppressWarnings({ "finally", "unused" })
	public String financeBasicInfo(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called InqFinanceBasicInfo");
		LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo sInputXML---"+sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqFinanceBasicInfo");
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId=xmlDataParser.getValueOf("cust_id");
			String LoanAcctNo=xmlDataParser.getValueOf("LoanAcctNo");


			LogGEN.writeTrace("CBG_Log", "customerId :"+customerId);
			LogGEN.writeTrace("CBG_Log", "LoanAcctNo :"+LoanAcctNo);
 
			InqFinanceBasicInfoStub financeBasic =new InqFinanceBasicInfoStub(sWSDLPath);
			GetLoanIslamicCommonDetailsReqMsg getLoanIslamicReqMsg= new GetLoanIslamicCommonDetailsReqMsg();
			GetLoanIslamicCommonDetailsReq getLoanIslamicReq=new GetLoanIslamicCommonDetailsReq();
			GetLoanIslamicCommonDetailsReq_type0 getLoanIslamicReq_type0=new GetLoanIslamicCommonDetailsReq_type0();
			
			HeaderType Header_Input = new HeaderType();
		
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqFinanceBasicInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);
			Header_Input.setUsername(loggedinuser);

			
			if(customerId != null || customerId != "" || !customerId.isEmpty())
			{
				getLoanIslamicReq_type0.setCID(customerId);	
			}
			if(LoanAcctNo != null || LoanAcctNo != "" || !LoanAcctNo.isEmpty())
			{
				getLoanIslamicReq_type0.setLoanAcctNo(LoanAcctNo);
			}
			getLoanIslamicReq.setGetLoanIslamicCommonDetailsReq(getLoanIslamicReq_type0);
			getLoanIslamicReqMsg.setGetLoanIslamicCommonDetailsReq(getLoanIslamicReq_type0);
			getLoanIslamicReqMsg.setHeader(Header_Input);
			LogGEN.writeTrace("CBG_Log", "All values set11");
		
			
			
			financeBasic._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=financeBasic.getinputXML(getLoanIslamicReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml : "+xmlInput);
			sOrg_Output = financeBasic.getOutput(getLoanIslamicReqMsg);
			
		    sOrg_Output = sOrg_Output.replaceAll("ns0:", "").replaceAll("ns1:", "").replaceAll("ns2:", "");
			LogGEN.writeTrace("CBG_Log", "output xml : "+sOrg_Output);
			com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(sOrg_Output);
		
			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sErrorDesc);
			sReturnCode = parser.getValueOf("returnCode");
			LogGEN.writeTrace("CBG_Log", "sErrorDesc :"+sReturnCode);

			sOutput = sOrg_Output;
			/*if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				GetLoanIslamicCommonDetailsRes_type0 fetchFinanceRes = new GetLoanIslamicCommonDetailsRes_type0();
				if (res_msg != null)
				{
				fetchFinanceRes=res_msg.getGetLoanIslamicCommonDetailsRes();
			
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqFinanceBasicInfo</Option>" +
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
				
				if (fetchFinanceRes != null) 
				{	
					
					Loan_Islamic_Common_Details_type0 loan_Islamic_Details_type0  = new Loan_Islamic_Common_Details_type0();
					Loan_Details_type0[]  loan_Details_type0 ;
					loan_Islamic_Details_type0 = fetchFinanceRes.getLoan_Islamic_Common_Details();
					loan_Details_type0 = loan_Islamic_Details_type0.getLoan_Details();
							sOutput +=  "<GetLoanIslamicCommonDetailsRes>"+
							"<loan_Islamic_Common_Details>";
							for (int i=0;i<loan_Details_type0.length ; i++)
							{
							sOutput +="<loan_Details>"
					                  +"<loanAcctNo>"+loan_Details_type0[i].getLoanAcctNo()+"</loanAcctNo>"
					                  +"<domicileBranchShortName>"+loan_Details_type0[i].getDomicileBranchShortName()+"</domicileBranchShortName>"
					                  +"<domicileBranchName>"+loan_Details_type0[i].getDomicileBranchName()+"</domicileBranchName>"
					                  +"<domicileBranchCode>"+loan_Details_type0[i].getDomicileBranchCode()+"</domicileBranchCode>"
					                  +"<acctCurrencyCode>"+loan_Details_type0[i].getAcctCurrencyCode()+"</acctCurrencyCode>"
					                  +"<acctCurrencyLiteral>"+loan_Details_type0[i].getAcctCurrencyLiteral()+"</acctCurrencyLiteral>"
					                  +"<acctCurrencyName>"+loan_Details_type0[i].getAcctCurrencyName()+"</acctCurrencyName>"
					                  +"<loanProductCode>"+loan_Details_type0[i].getLoanProductCode()+"</loanProductCode>"
					                  +"<loanProductName>"+loan_Details_type0[i].getLoanProductName()+"</loanProductName>"
					                  +"<loanProductType>"+loan_Details_type0[i].getLoanProductType()+"</loanProductType>"
					                  +"<loanAmt>"+loan_Details_type0[i].getLoanAmt()+"</loanAmt>"
					                  +"<loanTenor>"+loan_Details_type0[i].getLoanTenor()+"</loanTenor>"
					                  +"<loanInterestRate>"+loan_Details_type0[i].getLoanInterestRate()+"</loanInterestRate>"
					                  +"<disbursalDate>"+loan_Details_type0[i].getDisbursalDate()+"</disbursalDate>"
					                  +"<maturityDate>"+loan_Details_type0[i].getMaturityDate()+"</maturityDate>"
					                  +"<acctStatus>"+loan_Details_type0[i].getAcctStatus()+"</acctStatus>"
					                  +"<EMIAmt>"+loan_Details_type0[i].getEMIAmt()+"</EMIAmt>"
					                  +"<nextEMIDate>"+loan_Details_type0[i].getNextEMIDate()+"</nextEMIDate>"
					                  +"<noOfEMIPaid>"+loan_Details_type0[i].getNoOfEMIPaid()+"</noOfEMIPaid>"
					                  +"<outstandingBalance>"+loan_Details_type0[i].getOutstandingBalance()+"</outstandingBalance>"
					                  +"</loan_Details>";
							}
							sOutput +="</loan_Islamic_Common_Details>"+
							"</GetLoanIslamicCommonDetailsRes></Output>";
				}else
				{
					sOutput +=  "<loan_Details>No Data Found</loan_Details></loan_Islamic_Common_Details>"+
								"</loan_Islamic_Common_Details></Output>";
				}
				}
				else
				{
					sOutput =  "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>InqFinanceBasicInfo</Option> <loan_Details>No Data Found</loan_Details></loan_Islamic_Common_Details>"+
								"</loan_Islamic_Common_Details></Output>";
				}
				
			} 
			
			else
			{ 
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqFinanceBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqFinanceBasicInfo service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqFinanceBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqFinanceBasicInfo service</Status></Output>";

				}*/
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqFinanceBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqFinanceBasicInfo service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqFinanceBasicInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqFinanceBasicInfo service</Status></Output>";
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


