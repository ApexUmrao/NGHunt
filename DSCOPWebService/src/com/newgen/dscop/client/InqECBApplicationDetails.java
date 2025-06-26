package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Address_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Address_type1;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Card_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Company_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Contact_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Contact_type1;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.DBR_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.EmploymentStatus_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.External_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchECBReportDtlsReqMsg;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchECBReportDtlsReq_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchECBReportDtlsResMsg;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.FetchECBReportDtlsRes_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.FinancialDetails_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.HeaderType;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Individual_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Individual_type1;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Internal_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Loan_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.NAE_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.NotInstallment_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.OD_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.PersonalDetails_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.PreviousPassport_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Services_type0;
import com.newgen.dscop.stub.InqECBApplicationDetailsStub.Summary_type0;

public class InqECBApplicationDetails extends DSCOPServiceHandler {
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String ECBApplicationDetails_output = null;
	@SuppressWarnings("finally")
	public String ECBReportDetails(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called ECBReportDetails");
		LogGEN.writeTrace("CBG_Log", "InqECBApplicationDetails sInputXML---");
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
			//sHandler.readCabProperty("ECB_REPORT_DETAILS");				

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ECB_REPORT_DETAILS");
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");


			InqECBApplicationDetailsStub ECB_appDtl_stub=new InqECBApplicationDetailsStub(sWSDLPath);
			FetchECBReportDtlsReqMsg req_msg=new FetchECBReportDtlsReqMsg();
			FetchECBReportDtlsResMsg res_msg=new FetchECBReportDtlsResMsg();

			req_msg.setHeader(setHeaderDtls(sDate,ref_no,senderId));		

			req_msg.setFetchECBReportDtlsReq(fetchECB_req_type0(sInputXML, xmlDataParser));
			ECB_appDtl_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);

			xmlInput = ECB_appDtl_stub.getInputXml(req_msg);
			LogGEN.writeTrace("CBG_Log", "ECBReport InputXML: " + xmlInput);
			res_msg=ECB_appDtl_stub.fetchECBReportDtls_Oper(req_msg);
			ECBApplicationDetails_output = ECB_appDtl_stub.resECBApplicationDetails;
			LogGEN.writeTrace("CBG_Log", "ECBReport outputxml: " + ECBApplicationDetails_output);
			LogGEN.writeTrace("CBG_Log", "ECBReport Return Code---"+ sReturnCode);
			LogGEN.writeTrace("CBG_Log", "ECBReport Error Detail---"+ sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "ECBReport Error Description---"+ sErrorDesc);

			HeaderType header = res_msg.getHeader();
			sReturnCode = header.getReturnCode();
			sErrorDetail = header.getErrorDetail();
			sErrorDesc = header.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				FetchECBReportDtlsRes_type0 res_type0=new FetchECBReportDtlsRes_type0();
				EmploymentStatus_type0 emp_status_type0= new EmploymentStatus_type0();
				FinancialDetails_type0 fin_type0=new FinancialDetails_type0();
				Summary_type0 smm_type0=new Summary_type0();
				Card_type0 cd_type0=new Card_type0();
				OD_type0 od_type0=new OD_type0();
				DBR_type0 dbr_type0=new DBR_type0();
				Loan_type0 loan_type0=new Loan_type0();
				Internal_type0 int_type0=new Internal_type0();
				External_type0 ext_type0=new External_type0();
				Services_type0 service_type0=new Services_type0();
				PersonalDetails_type0 personal_dtl_type0=new PersonalDetails_type0();
				Company_type0 company_type0=new Company_type0();
				Address_type0 add_type0=new Address_type0();
				Contact_type0 contact_type0=new Contact_type0();
				Individual_type0 indiv_type0=new Individual_type0();
				Address_type1 address_type1=new Address_type1();
				Contact_type1 contact_type1=new Contact_type1();
				res_type0=res_msg.getFetchECBReportDtlsRes();
				fin_type0=res_type0.getFinancialDetails();
				smm_type0=fin_type0.getSummary();
				cd_type0=smm_type0.getCard();
				od_type0=smm_type0.getOD();
				service_type0=smm_type0.getServices();
				loan_type0=smm_type0.getLoan();
				dbr_type0=fin_type0.getDBR();
				ext_type0=dbr_type0.getExternal();
				int_type0=dbr_type0.getInternal();
				emp_status_type0=res_type0.getEmploymentStatus();
				personal_dtl_type0=res_type0.getPersonalDetails();
				company_type0=personal_dtl_type0.getCompany();
				add_type0=company_type0.getAddress();
				contact_type0=company_type0.getContact();
				indiv_type0=personal_dtl_type0.getIndividual();
				address_type1=indiv_type0.getAddress();
				contact_type1=indiv_type0.getContact();
				LogGEN.writeTrace("CBG_Log", "inside if");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>ECB_APPLICATION_DETAILS</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"
						+"<fetchECBReportDtlsRes>";
				if(res_type0!=null){
					sOutput+= "<CBContractId>"+res_type0.getCBContractId()+"</CBContractId>"
							+"<CBSubjectId>"+res_type0.getCBSubjectId()+"</CBSubjectId>"
							+"<ChannelID>"+res_type0.getChannelID()+"</ChannelID>"
							+ "<HitNoHit>"+res_type0.getHitNoHit()+"</HitNoHit>"
							+"<ProviderSubjectNumber>"+res_type0.getProviderSubjectNumber()+"</ProviderSubjectNumber>"
							+"<ReferenceNumber>"+res_type0.getReferenceNumber()+"</ReferenceNumber>"
							+"<Score>"+res_type0.getScore()+"</Score>"
							+"<ScoreRange>"+res_type0.getScoreRange()+"</ScoreRange>"
							+"<Status>"+res_type0.getStatus()+"</Status>"
							+"<worstCurrentPaymentDelay>"+res_type0.getWorstCurrentPaymentDelay()+"</worstCurrentPaymentDelay>"
							+"<lastChequeBouncedDate>"+res_type0.getLastChequeBouncedDate()+"</lastChequeBouncedDate>"
							+"<noOf60PlusDPDInHistory>"+res_type0.getNoOf60PlusDPDInHistory()+"</noOf60PlusDPDInHistory>"
							+"<noOfRestructureLoanInLast24Months>"+res_type0.getNoOfRestructureLoanInLast24Months()+"</noOfRestructureLoanInLast24Months>"
							+"<noOfChequeBounceInLast12Months>"+res_type0.getNoOfChequeBounceInLast12Months()+"</noOfChequeBounceInLast12Months>"
							+"<latestRestructuredDate>"+res_type0.getLatestRestructuredDate()+"</latestRestructuredDate>"
							+"<oldPassportNos>"+res_type0.getOldPassportNos()+"</oldPassportNos>"
							+ "<ErrorCode>"+res_type0.getErrorCode()+"</ErrorCode>"
							+"<ErrorDesc>"+res_type0.getErrorDesc()+"</ErrorDesc>";
				}
				sOutput+="<EmploymentStatus>";
				if(emp_status_type0!=null){
					sOutput+="<DateOfEmployment>"+emp_status_type0.getDateOfEmployment()+"</DateOfEmployment>"
							+"<DateOfTermination>"+emp_status_type0.getDateOfTermination()+"</DateOfTermination>"
							+"<DateOfLastUpdate>"+emp_status_type0.getDateOfLastUpdate()+"</DateOfLastUpdate>"
							+"<EmpName>"+emp_status_type0.getEmpName()+"</EmpName>"
							+"<EmpType>"+emp_status_type0.getEmpType()+"</EmpType>"
							+"<GrossAnnualIncome>"+emp_status_type0.getGrossAnnualIncome()+"</GrossAnnualIncome>"
							+"<ProviderNo>"+emp_status_type0.getProviderNo()+"</ProviderNo>";
				}
				sOutput+="</EmploymentStatus>"
						+"<FinancialDetails>"
						+"<DBR>"
						+"<External>";
				if(ext_type0!=null){
					sOutput+="<SumOf5PERCC>"+ext_type0.getSumOf5PERCC()+"</SumOf5PERCC>"
							+" <SumOfEMIOfAllActiveLoans>"+ext_type0.getSumOfEMIOfAllActiveLoans()+"</SumOfEMIOfAllActiveLoans>"
							+" <SumOfEMIOfAllActiveOD>"+ext_type0.getSumOfEMIOfAllActiveOD()+"</SumOfEMIOfAllActiveOD>";
				}
				sOutput+="</External>"
						+"<Internal>";
				if(int_type0!=null){
					sOutput+="  <SumOf5PerCC>"+int_type0.getSumOf5PerCC()+"</SumOf5PerCC>"
							+"<SumOfEMIOfAllActiveLoans>"+int_type0.getSumOfEMIOfAllActiveLoans()+"</SumOfEMIOfAllActiveLoans>"
							+" <SumOfEMIOfAllActiveOD>"+int_type0.getSumOfEMIOfAllActiveOD()+"</SumOfEMIOfAllActiveOD>";
				}
				sOutput+="</Internal>"
						+"</DBR>";
				if(fin_type0!=null){
					sOutput+="<NoOfBounceChequeTotal>"+fin_type0.getNoOfBounceChequeTotal()+"</NoOfBounceChequeTotal>"
							+"<NoOfBounceChequeLast90Days>"+fin_type0.getNoOfBounceChequeLast90Days()+"</NoOfBounceChequeLast90Days>"
							+" <NoOf30dpdInLast6Months>"+fin_type0.getNoOf30DpdInLast6Months()+"</NoOf30dpdInLast6Months>"
							+" <NoOf60dpdInLast12Months>"+fin_type0.getNoOf60DpdInLast12Months()+"</NoOf60dpdInLast12Months>"
							+"<NoOf90dpd>"+fin_type0.getNoOf90Dpd()+"</NoOf90dpd>"
							+" <NumberOfEnquiry>"+fin_type0.getNumberOfEnquiry()+"</NumberOfEnquiry>"
							+" <OldestContractStartDate>"+fin_type0.getOldestContractStartDate()+"</OldestContractStartDate>"
							+" <RestructureActivePILWithTenorGreaterThen48Mnths>"+fin_type0.getRestructureActivePILWithTenorGreaterThen48Mnths()+"</RestructureActivePILWithTenorGreaterThen48Mnths>";
				}
				sOutput+="<Summary>"
						+"<Card>";
				if(cd_type0!=null){
					sOutput+=" <NoActiveContract>"+cd_type0.getNoActiveContract()+"</NoActiveContract>"
							+" <TotalCreditLimit>"+cd_type0.getTotalCreditLimit()+"</TotalCreditLimit>"
							+" <TotalOutstandingBalanceAmount>"+cd_type0.getTotalOutstandingBalanceAmount()+"</TotalOutstandingBalanceAmount>"
							+"<TotalOverdueAmount>"+cd_type0.getTotalOverdueAmount()+"</TotalOverdueAmount>";
				}
				sOutput+="</Card>"
						+"<Loan>";
				if(loan_type0!=null){
					sOutput+="<NoActiveContract>"+loan_type0.getNoActiveContract()+"</NoActiveContract>"
							+"<TotalMonthlyInstalmentsAmount>"+loan_type0.getTotalMonthlyInstalmentsAmount()+"</TotalMonthlyInstalmentsAmount>"
							+" <TotalOutstandingBalanceAmount>"+loan_type0.getTotalOutstandingBalanceAmount()+"</TotalOutstandingBalanceAmount>"
							+"<TotalOverdueAmount>"+loan_type0.getTotalOverdueAmount()+"</TotalOverdueAmount>";
				}
				sOutput+=" </Loan>"
						+"<OD>";
				if(od_type0!=null){
					sOutput+="<NoActiveContract>"+od_type0.getNoActiveContract()+"</NoActiveContract>"
							+"<TotalCreditLimit>"+od_type0.getTotalCreditLimit()+"</TotalCreditLimit>"
							+"<TotalOutstandingBalanceAmount>"+od_type0.getTotalOutstandingBalanceAmount()+"</TotalOutstandingBalanceAmount>"
							+" <TotalOverdueAmount>"+od_type0.getTotalOverdueAmount()+"</TotalOverdueAmount>";
				}
				sOutput+=" </OD>"
						+" <Services>";
				if(service_type0!=null){
					sOutput+="<NoActiveContract>"+service_type0.getNoActiveContract()+"</NoActiveContract>"
							+"<TotalCreditLimit>"+service_type0.getTotalCreditLimit()+"</TotalCreditLimit>"
							+" <TotalOutstandingBalanceAmount>"+service_type0.getTotalOutstandingBalanceAmount()+"</TotalOutstandingBalanceAmount>"
							+"<TotalOverdueAmount>"+service_type0.getTotalOverdueAmount()+"</TotalOverdueAmount>";
				}
				sOutput+="  </Services>"
						+"</Summary>";
				if(fin_type0!=null){
					sOutput+="<TotalOutstandingBalance>"+fin_type0.getTotalOutstandingBalance()+"</TotalOutstandingBalance>"
							+" <TotalOverdueAmount>"+fin_type0.getTotalOverdueAmount()+"</TotalOverdueAmount>";
				}
				sOutput+="</FinancialDetails>";
				sOutput+="<PersonalDetails>"
						+"<Company>"
						+"<Address>";
				if(add_type0!=null){
					sOutput+="<ADRDetails>"+add_type0.getADRDetails()+"</ADRDetails>"
							+"<DateLastUpdated>"+add_type0.getDateLastUpdated()+"</DateLastUpdated>"
							+"<Emirates>"+add_type0.getEmirates()+"</Emirates>"
							+"  <POBox>"+add_type0.getPOBox()+"</POBox>";
				}
				sOutput+="</Address>";
				if(company_type0!=null){
					sOutput+="<CompanyName>"+company_type0.getCompanyName()+"</CompanyName>"
							+"<CompanyNameAR>"+company_type0.getCompanyNameAR()+"</CompanyNameAR>";
				}
				sOutput+="<Contact>";
				if(contact_type0!=null){
					sOutput+=" <EmailID>"+contact_type0.getEmailID()+"</EmailID>"
							+"<MobileNumber>"+contact_type0.getMobileNumber()+"</MobileNumber>"
							+"<PhoneNumber>"+contact_type0.getPhoneNumber()+"</PhoneNumber>";
				}
				sOutput+="</Contact>";
				if(company_type0!=null){
					sOutput+="<RegistrationDate>"+company_type0.getRegistrationDate()+"</RegistrationDate>"
							+"<TradeLicenseNumber>"+company_type0.getTradeLicenseNumber()+"</TradeLicenseNumber>"
							+"<TradeLicensePlace>"+company_type0.getTradeLicensePlace()+"</TradeLicensePlace>";
				}
				sOutput+="</Company>"
						+"<Individual>"
						+"<Address>";
				if(address_type1!=null){
					sOutput+=" <ADRDetails>"+address_type1.getADRDetails()+"</ADRDetails>"
							+"<DateLastUpdated>"+address_type1.getDateLastUpdated()+"</DateLastUpdated>"
							+"<Emirates>"+address_type1.getEmirates()+"</Emirates>"
							+"<POBox>"+address_type1.getPOBox()+"</POBox>";
				}
				sOutput+="</Address>"
						+"<Contact>";
				if(contact_type1!=null){
					sOutput+=" <EmailID>"+contact_type1.getEmailID()+"</EmailID>"
							+"<MobileNumber>"+contact_type1.getMobileNumber()+"</MobileNumber>"
							+"<PhoneNumber>"+contact_type1.getPhoneNumber()+"</PhoneNumber>";
				}
				sOutput+="</Contact>";
				if(indiv_type0!=null){
					sOutput+="<DateOfBirth>"+indiv_type0.getDateOfBirth()+"</DateOfBirth>"
							+"<EIDA>"+indiv_type0.getEIDA()+"</EIDA>"
							+"<FullName>"+indiv_type0.getDateOfBirth()+"</FullName>"
							+"<FullNameAR>"+indiv_type0.getDateOfBirth()+"</FullNameAR>"
							+"<Nationality>"+indiv_type0.getDateOfBirth()+"</Nationality>"
							+"<Passport>"+indiv_type0.getDateOfBirth()+"</Passport>"
							+"<ResidentFlag>"+indiv_type0.getDateOfBirth()+"</ResidentFlag>"
							+"<Title>"+indiv_type0.getDateOfBirth()+"</Title>";
				}
				sOutput+="</Individual>"
						+"</PersonalDetails>";
				sOutput+="</fetchECBReportDtlsRes>"
						+ "</Output>";
				//				LogGEN.writeTrace("CBG_Log", "output xml within if block--------"+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ECB_REPORT_DETAILS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch ECB report </td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ECB_REPORT_DETAILS</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch ECB report </td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ECB_REPORT_DETAILS</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td> ECB application detail report generation failed.</td></Output>";
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
			//			LogGEN.writeTrace("CBG_Log", inputXml);
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
				e.printStackTrace();
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," ECB Application Detail Report NI Query : finally :"+Query);
			//			LogGEN.writeTrace("CBG_Log","ECBApplicationDetails_output : finally :"+ECBApplicationDetails_output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),ECBApplicationDetails_output.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			//			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}

	private NAE_type0 NAEReq_type0(String sInputXML,XMLParser xmlDataParser){
		LogGEN.writeTrace("CBG_Log", "inside setpreScreeningdtls fn");
		xmlDataParser.setInputXML(sInputXML);
		NAE_type0 req_type0= new NAE_type0();
		Individual_type1 indiv_req_type1=new Individual_type1();
		NotInstallment_type0 notInstall_req_type0=new NotInstallment_type0();
		indiv_req_type1.setDOB(xmlDataParser.getValueOf("DOB"));
		indiv_req_type1.setEmiratesId(xmlDataParser.getValueOf("EmiratesId"));
		indiv_req_type1.setFullName(xmlDataParser.getValueOf("FullName"));
		indiv_req_type1.setGender(xmlDataParser.getValueOf("Gender"));
		indiv_req_type1.setNationality(xmlDataParser.getValueOf("Nationality"));
		indiv_req_type1.setPassport(xmlDataParser.getValueOf("Passport"));
		indiv_req_type1.setPassportExpiryDate(xmlDataParser.getValueOf("PassportExpiryDate"));
		PreviousPassport_type0 previosusPassport = new PreviousPassport_type0();
		previosusPassport.setPassport1("");
		previosusPassport.setPassport2("");
		previosusPassport.setPassport3("");
		previosusPassport.setPassport4("");
		indiv_req_type1.setPreviousPassport(previosusPassport);
		indiv_req_type1.setPrimaryMobileNo(xmlDataParser.getValueOf("PrimaryMobileNo"));

		notInstall_req_type0.setCreditLimit(xmlDataParser.getValueOf("CreditLimit"));

		req_type0.setIndividual(indiv_req_type1);
		req_type0.setNotInstallment(notInstall_req_type0);
		return req_type0;
	}

	private FetchECBReportDtlsReq_type0 fetchECB_req_type0(String sInputXML,XMLParser xmlDataParser)
	{
		LogGEN.writeTrace("CBG_Log", "inside setpreScreeningdtls fn");
		xmlDataParser.setInputXML(sInputXML);
		FetchECBReportDtlsReq_type0 req_type0=new FetchECBReportDtlsReq_type0();
		req_type0.setChannel(xmlDataParser.getValueOf("Channel"));
		req_type0.setConsentFlag(xmlDataParser.getValueOf("ConsentFlag"));
		req_type0.setContractType(xmlDataParser.getValueOf("ContractType"));
		req_type0.setEnquiryType(xmlDataParser.getValueOf("EnquiryType"));
		req_type0.setNAE(NAEReq_type0(sInputXML, xmlDataParser));
		req_type0.setReferenceNumber(xmlDataParser.getValueOf("ReferenceNumber"));
		req_type0.setReportType(xmlDataParser.getValueOf("ReportType"));
		req_type0.setRole(xmlDataParser.getValueOf("Role"));
		req_type0.setUserID(xmlDataParser.getValueOf("UserID"));
		req_type0.setLocalDBSearchReq(xmlDataParser.getValueOf("LocalDBSearchReq"));
		return req_type0;
	}

	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("123245");
		headerType.setServiceName("InqECBApplicationDetails");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("ECB");
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
