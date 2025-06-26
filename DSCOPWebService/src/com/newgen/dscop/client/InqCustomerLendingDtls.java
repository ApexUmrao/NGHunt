package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.ContractDetails_type0;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.CustomerDetails_type0;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.InitiateCustomerDBRCalculationReqMsg;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.InitiateCustomerDBRCalculationReq_type0;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.InitiateCustomerDBRCalculationResMsg;
import com.newgen.dscop.stub.InqCustomerLendingDtlsStub.InitiateCustomerDBRCalculationRes_type0;

public class InqCustomerLendingDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output="";

	String xmlInput="";
	@SuppressWarnings("finally")
	public String custLending(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called InqCustomerLendingDtls");
		LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls sInputXML---"+sInputXML);
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerLendingDtls");
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId=xmlDataParser.getValueOf("cust_id");
			String contractType=xmlDataParser.getValueOf("contractType");
			String loanAmount=xmlDataParser.getValueOf("loanAmount");
			String ruleId=xmlDataParser.getValueOf("ruleId");
			String sourceRefNo=xmlDataParser.getValueOf("WiName");
			String tenor=xmlDataParser.getValueOf("tenor");
			String custType=xmlDataParser.getValueOf("custType");
			String dateOfBirth=xmlDataParser.getValueOf("dateOfBirth");
			String customerName=xmlDataParser.getValueOf("customerName");
			String emiratesId=xmlDataParser.getValueOf("emiratesId");
			String employeeCode=xmlDataParser.getValueOf("employeeCode");
			String employmentCategory=xmlDataParser.getValueOf("employmentCategory");
			String gender=xmlDataParser.getValueOf("gender");
			String grossIncome=xmlDataParser.getValueOf("grossIncome");
			String nationality=xmlDataParser.getValueOf("nationality");
			String passport=xmlDataParser.getValueOf("passport");
			String passportExpiryDate=xmlDataParser.getValueOf("passportExpiryDate");

			LogGEN.writeTrace("CBG_Log", "customerId :"+customerId);
			LogGEN.writeTrace("CBG_Log", "ref_no :"+ref_no);
			LogGEN.writeTrace("CBG_Log", "contractType :"+contractType);
			LogGEN.writeTrace("CBG_Log", "loanAmount :"+loanAmount);
			LogGEN.writeTrace("CBG_Log", "ruleId :"+ruleId);
			LogGEN.writeTrace("CBG_Log", "sourceRefNo :"+sourceRefNo);
			LogGEN.writeTrace("CBG_Log", "tenor :"+tenor);
			LogGEN.writeTrace("CBG_Log", "custType :"+custType);
			LogGEN.writeTrace("CBG_Log", "dateOfBirth :"+dateOfBirth);
			LogGEN.writeTrace("CBG_Log", "customerName :"+customerName);
			LogGEN.writeTrace("CBG_Log", "emiratesId :"+emiratesId);
			LogGEN.writeTrace("CBG_Log", "employeeCode :"+employeeCode);
			LogGEN.writeTrace("CBG_Log", "employmentCategory :"+employmentCategory);
			LogGEN.writeTrace("CBG_Log", "gender :"+gender);
			LogGEN.writeTrace("CBG_Log", "grossIncome :"+grossIncome);
			LogGEN.writeTrace("CBG_Log", "nationality :"+nationality);
			LogGEN.writeTrace("CBG_Log", "passport :"+passport);
			LogGEN.writeTrace("CBG_Log", "passportExpiryDate :"+passportExpiryDate);
 
			InqCustomerLendingDtlsStub cust_lend_stub =new InqCustomerLendingDtlsStub(sWSDLPath);
			InitiateCustomerDBRCalculationReq_type0 req_type0 = new InitiateCustomerDBRCalculationReq_type0();
			InitiateCustomerDBRCalculationReqMsg ReqMsg = new InitiateCustomerDBRCalculationReqMsg();
			CustomerDetails_type0 customerDetails= new CustomerDetails_type0();
			ContractDetails_type0 contractDetails= new ContractDetails_type0();
 
			HeaderType Header_Input = new HeaderType();
		
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustomerLendingDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);

			
			if(customerId != null || customerId != "" || !customerId.isEmpty())
			{
				customerDetails.setCustomerId(customerId);
			}
			if(custType != null || custType != "" || !custType.isEmpty())
			{
				customerDetails.setCustType(custType);
			}
			if(dateOfBirth != null || dateOfBirth != "" || !dateOfBirth.isEmpty())
			{
				customerDetails.setDateOfBirth(dateOfBirth);
			}
			if(customerName != null || customerName != "" || !customerName.isEmpty())
			{
				customerDetails.setCustomerName(customerName);
			}
			if(emiratesId != null || emiratesId != "" || !emiratesId.isEmpty())
			{
				customerDetails.setEmiratesId(emiratesId);
			}
			if(employeeCode != null || employeeCode != "" || !employeeCode.isEmpty())
			{
				customerDetails.setEmployeeCode(employeeCode);
			}
			if(employmentCategory != null || employmentCategory != "" || !employmentCategory.isEmpty())
			{
				customerDetails.setEmploymentCategory(employmentCategory);
			}
			if(gender != null || gender != "" || !gender.isEmpty())
			{
				customerDetails.setGender(gender);
			}
			if(grossIncome != null || grossIncome != "" || !grossIncome.isEmpty())
			{
				customerDetails.setGrossIncome(grossIncome);
			}
			if(nationality != null || nationality != "" || !nationality.isEmpty())
			{
				customerDetails.setNationality(nationality);
			}
			if(passport != null || passport != "" || !passport.isEmpty())
			{
				customerDetails.setPassport(passport);
			}
			if(passportExpiryDate != null || passportExpiryDate != "" || !passportExpiryDate.isEmpty())
			{
				customerDetails.setPassportExpiryDate(passportExpiryDate);
			}
			if(contractType != null || contractType != "" || !contractType.isEmpty())
			{
				contractDetails.setContractType(contractType);
			}
			if(loanAmount != null || loanAmount != "" || !loanAmount.isEmpty())
			{
				contractDetails.setLoanAmount(loanAmount);
			}
			if(ruleId != null || ruleId != "" || !ruleId.isEmpty())
			{
				contractDetails.setRuleId(ruleId);
			}
			if(sourceRefNo != null || sourceRefNo != "" || !sourceRefNo.isEmpty())
			{
				contractDetails.setSourceRefNo(sourceRefNo);
			}
			if(tenor != null || tenor != "" || !tenor.isEmpty())
			{
				contractDetails.setTenor(tenor);
			}
			
			LogGEN.writeTrace("CBG_Log", "All values set11");
			req_type0.setContractDetails(contractDetails);
			req_type0.setCustomerDetails(customerDetails);
			
			ReqMsg.setHeader(Header_Input);
			ReqMsg.setInitiateCustomerDBRCalculationReq(req_type0);


			cust_lend_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=cust_lend_stub.getinputXML(ReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml : "+xmlInput);
			InitiateCustomerDBRCalculationResMsg res_msg= cust_lend_stub.initiateCustomerDBRCalculation_Oper(ReqMsg);
			sOrg_Output = cust_lend_stub.responseCustLend;
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
				InitiateCustomerDBRCalculationRes_type0 fetchCustRes = new InitiateCustomerDBRCalculationRes_type0();
				fetchCustRes=res_msg.getInitiateCustomerDBRCalculationRes();
			
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustomerLendingDtls</Option>" +
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
					sOutput +=  "<applicationRefNo>"+fetchCustRes.getApplicationRefNo()+"</applicationRefNo>"+
							"<sourceRefNo>"+fetchCustRes.getSourceRefNo()+"</sourceRefNo></Output>";
				}
				else
				{
					sOutput +=  "<applicationRefNo>No Data Found</applicationRefNo>"+
						"<sourceRefNo>No Data Found</sourceRefNo></Output>";
				}
				
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerLendingDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCustomerLendingDtls service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerLendingDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform InqCustomerLendingDtls service</Status></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerLendingDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCustomerLendingDtls service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustomerLendingDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform InqCustomerLendingDtls service</Status></Output>";
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


