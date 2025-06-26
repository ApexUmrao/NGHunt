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
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDigitalLendingApplicationReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDigitalLendingApplicationReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDigitalLendingApplicationResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddDigitalLendingApplicationRes_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.HeaderType;

public class AddDigitalLendingApplication  extends DSCOPServiceHandler
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
	public String addDigitalLendingApplication(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddDigitalLendingApplication");
		LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		  
		String applicationStatus=xmlDataParser.getValueOf("applicationStatus");
		String rejectionCode=xmlDataParser.getValueOf("rejectionCode");
		String transactionNo=xmlDataParser.getValueOf("transactionNo");
		String lapsApplicationNo=xmlDataParser.getValueOf("lapsApplicationNo");
		String productType=xmlDataParser.getValueOf("productType");
		String applicationCategory=xmlDataParser.getValueOf("applicationCategory");
		String product=xmlDataParser.getValueOf("product");
		String customerID=xmlDataParser.getValueOf("customerID");
		String branchCode=xmlDataParser.getValueOf("branchCode");
		String eligibleLoan=xmlDataParser.getValueOf("eligibleLoan");
		String creditDecision=xmlDataParser.getValueOf("creditDecision");
		String firstDueDate=xmlDataParser.getValueOf("firstDueDate");
		String maturityDate=xmlDataParser.getValueOf("maturityDate");
		String settlementAcctNo=xmlDataParser.getValueOf("settlementAcctNo");
		String settlementAcctBranch=xmlDataParser.getValueOf("settlementAcctBranch");
		String gracePeriodInterestRate=xmlDataParser.getValueOf("gracePeriodInterestRate");
		String loanAmountRequested=xmlDataParser.getValueOf("loanAmountRequested");
		String rateOfInterest=xmlDataParser.getValueOf("rateOfInterest");
		String tenor=xmlDataParser.getValueOf("tenor");
		String installmentAmount=xmlDataParser.getValueOf("installmentAmount");
		String dsaCode=xmlDataParser.getValueOf("dsaCode");
		String dbrPercentage=xmlDataParser.getValueOf("dbrPercentage");
		String specialPromoCode=xmlDataParser.getValueOf("specialPromoCode");
		String IBanAcctNo=xmlDataParser.getValueOf("IBanAcctNo");
		String loanType=xmlDataParser.getValueOf("loanType");
		String repaymentAcctNo=xmlDataParser.getValueOf("repaymentAcctNo");
		String approvalDate=xmlDataParser.getValueOf("approvalDate");
		String stlStatus=xmlDataParser.getValueOf("stlStatus");
		String mobileDedupeFlg=xmlDataParser.getValueOf("mobileDedupeFlg");
		String adcbRelatiionshipDedupeFlg=xmlDataParser.getValueOf("adcbRelatiionshipDedupeFlg");
		String customerTRSDFlg=xmlDataParser.getValueOf("customerTRSDFlg");
		String companyTRSDFlg=xmlDataParser.getValueOf("companyTRSDFlg");
		String midasDedupeFlg=xmlDataParser.getValueOf("midasDedupeFlg");
		String cdmsVeification=xmlDataParser.getValueOf("cdmsVeification");
		String cbrbExposureFlg=xmlDataParser.getValueOf("cbrbExposureFlg");
		String cbRating=xmlDataParser.getValueOf("cbRating");
		String ecbHit=xmlDataParser.getValueOf("ecbHit");
		String ecbScore=xmlDataParser.getValueOf("ecbScore");
		String brmsPrimaryScore=xmlDataParser.getValueOf("brmsPrimaryScore");
		String brmsSecondaryScore=xmlDataParser.getValueOf("brmsSecondaryScore");
		String brmsEligibileAmt=xmlDataParser.getValueOf("brmsEligibileAmt");
		String brmsPolicyValidation=xmlDataParser.getValueOf("brmsPolicyValidation");
		String customerIC=xmlDataParser.getValueOf("customerIC");
		String customerName=xmlDataParser.getValueOf("customerName");
		String shortName=xmlDataParser.getValueOf("shortName");
		String dateOfBirth=xmlDataParser.getValueOf("dateOfBirth");
		String gender=xmlDataParser.getValueOf("gender");
		String nationalityCode=xmlDataParser.getValueOf("nationalityCode");
		String customerType=xmlDataParser.getValueOf("customerType");
		String mobileNo=xmlDataParser.getValueOf("mobileNo");
		String segmentType=xmlDataParser.getValueOf("segmentType");
		String serialNo=xmlDataParser.getValueOf("serialNo");
		String customerClass=xmlDataParser.getValueOf("customerClass");
		String employerName=xmlDataParser.getValueOf("employerName");
		String companyCode=xmlDataParser.getValueOf("companyCode");
		String grossIncome=xmlDataParser.getValueOf("grossIncome");
		String dateOfJoining=xmlDataParser.getValueOf("dateOfJoining");
		String tmlFlg=xmlDataParser.getValueOf("tmlFlg");
		String homeCountryAdd1=xmlDataParser.getValueOf("homeCountryAdd1");
		String homeCountryAdd2=xmlDataParser.getValueOf("homeCountryAdd2");
		String homeCountryAdd3=xmlDataParser.getValueOf("homeCountryAdd3");
		String homeCountryCity=xmlDataParser.getValueOf("homeCountryCity");
		String homeCountryState=xmlDataParser.getValueOf("homeCountryState");
		String homeCountryCountry=xmlDataParser.getValueOf("homeCountryCountry");
		String homeCountryPoBoxNo=xmlDataParser.getValueOf("homeCountryPoBoxNo");
		String homeCountryPhoneNo=xmlDataParser.getValueOf("homeCountryPhoneNo");
		String serviceMode=xmlDataParser.getValueOf("serviceMode");
		String sourcingChannel=xmlDataParser.getValueOf("sourcingChannel");
		String applicationStage=xmlDataParser.getValueOf("applicationStage");
		String remarks=xmlDataParser.getValueOf("Remarks");
		String remarksBy=xmlDataParser.getValueOf("RemarksBy");

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("mod_cbg_customer_onboarding");
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddDigitalLendingApplication TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			ModCBGCustomerOnboardingStub addDGLendingstub=new ModCBGCustomerOnboardingStub(sWSDLPath);
			AddDigitalLendingApplicationReq_type0 addDGLendingReq=new AddDigitalLendingApplicationReq_type0();
			AddDigitalLendingApplicationReqMsg addDGLendingReqMsg=new AddDigitalLendingApplicationReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCBGCustomerOnboarding");
			Header_Input.setVersionNo("1.0");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);


			LogGEN.writeTrace("CBG_Log", "All values set11");

			addDGLendingReqMsg.setHeader(Header_Input);

			addDGLendingReq.setApplicationStatus(applicationStatus);
			addDGLendingReq.setRejectionCode(rejectionCode);
			addDGLendingReq.setTransactionNo(transactionNo);
			addDGLendingReq.setLapsApplicationNo(lapsApplicationNo);
			addDGLendingReq.setProductType(productType);
			addDGLendingReq.setApplicationCategory(applicationCategory);
			addDGLendingReq.setProduct(product);
			addDGLendingReq.setCustomerID(customerID);
			addDGLendingReq.setBranchCode(branchCode);
			addDGLendingReq.setEligibleLoan(eligibleLoan);
			addDGLendingReq.setCreditDecision(creditDecision);
			addDGLendingReq.setFirstDueDate(firstDueDate);
			addDGLendingReq.setMaturityDate(maturityDate);
			addDGLendingReq.setSettlementAcctNo(settlementAcctNo);
			addDGLendingReq.setSettlementAcctBranch(settlementAcctBranch);
			addDGLendingReq.setGracePeriodInterestRate(gracePeriodInterestRate);
			addDGLendingReq.setLoanAmountRequested(loanAmountRequested);
			addDGLendingReq.setRateOfInterest(rateOfInterest);
			addDGLendingReq.setTenor(tenor);
			addDGLendingReq.setInstallmentAmount(installmentAmount);
			addDGLendingReq.setDsaCode(dsaCode);
			addDGLendingReq.setDbrPercentage(dbrPercentage);
			addDGLendingReq.setSpecialPromoCode(specialPromoCode);
			addDGLendingReq.setIBanAcctNo(IBanAcctNo);
			addDGLendingReq.setLoanType(loanType);
			addDGLendingReq.setRepaymentAcctNo(repaymentAcctNo);
			addDGLendingReq.setApprovalDate(approvalDate);
			addDGLendingReq.setStlStatus(stlStatus);
			addDGLendingReq.setMobileDedupeFlg(mobileDedupeFlg);
			addDGLendingReq.setAdcbRelatiionshipDedupeFlg(adcbRelatiionshipDedupeFlg);
			addDGLendingReq.setCustomerTRSDFlg(customerTRSDFlg);
			addDGLendingReq.setCompanyTRSDFlg(companyTRSDFlg);
			addDGLendingReq.setMidasDedupeFlg(midasDedupeFlg);
			addDGLendingReq.setCdmsVeification(cdmsVeification);
			addDGLendingReq.setCbrbExposureFlg(cbrbExposureFlg);
			addDGLendingReq.setCbRating(cbRating);
			addDGLendingReq.setEcbHit(ecbHit);
			addDGLendingReq.setEcbScore(ecbScore);
			addDGLendingReq.setBrmsPrimaryScore(brmsPrimaryScore);
			addDGLendingReq.setBrmsSecondaryScore(brmsSecondaryScore);
			addDGLendingReq.setBrmsEligibileAmt(brmsEligibileAmt);
			addDGLendingReq.setBrmsPolicyValidation(brmsPolicyValidation);
			addDGLendingReq.setCustomerIC(customerIC);
			addDGLendingReq.setCustomerName(customerName);
			addDGLendingReq.setShortName(shortName);
			addDGLendingReq.setDateOfBirth(dateOfBirth);
			addDGLendingReq.setGender(gender);
			addDGLendingReq.setNationalityCode(nationalityCode);
			addDGLendingReq.setCustomerType(customerType);
			addDGLendingReq.setMobileNo(mobileNo);
			addDGLendingReq.setSegmentType(segmentType);
			addDGLendingReq.setSerialNo(serialNo);
			addDGLendingReq.setCustomerClass(customerClass);
			addDGLendingReq.setEmployerName(employerName);
			addDGLendingReq.setCompanyCode(companyCode);
			addDGLendingReq.setGrossIncome(grossIncome);
			addDGLendingReq.setDateOfJoining(dateOfJoining);
			addDGLendingReq.setTmlFlg(tmlFlg);
			addDGLendingReq.setHomeCountryAdd1(homeCountryAdd1);
			addDGLendingReq.setHomeCountryAdd2(homeCountryAdd2);
			addDGLendingReq.setHomeCountryAdd3(homeCountryAdd3);
			addDGLendingReq.setHomeCountryCity(homeCountryCity);
			addDGLendingReq.setHomeCountryState(homeCountryState);
			addDGLendingReq.setHomeCountryCountry(homeCountryCountry);
			addDGLendingReq.setHomeCountryPoBoxNo(homeCountryPoBoxNo);
			addDGLendingReq.setHomeCountryPhoneNo(homeCountryPhoneNo);
			addDGLendingReq.setServiceMode(serviceMode);
			addDGLendingReq.setSourcingChannel(sourcingChannel);
			addDGLendingReq.setApplicationStage(applicationStage);
			addDGLendingReq.setRemarks(remarks);
			addDGLendingReq.setRemarksBy(remarksBy);

			
			addDGLendingReqMsg.setAddDigitalLendingApplicationReq(addDGLendingReq);
			addDGLendingstub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=addDGLendingstub.getinputXML(addDGLendingReqMsg);
			AddDigitalLendingApplicationResMsg add_CSAcct_res_msg= addDGLendingstub.addDigitalLendingApplication_Oper(addDGLendingReqMsg);
			sOrg_Output=addDGLendingstub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtlsRequestResMsg sOrg_put: "+sOrg_Output);
			
			Header_Input=add_CSAcct_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");

				AddDigitalLendingApplicationRes_type0 resType0 = new AddDigitalLendingApplicationRes_type0();
				resType0 = add_CSAcct_res_msg.getAddDigitalLendingApplicationRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddDigitalLendingApplication</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<AddDigitalLendingApplication>"+
						"<lapsApplicationNo>"+ resType0.getLapsApplicationNo() +"</lapsApplicationNo>"+
						"<status>"+ resType0.getStatus() +"</status>"+
						"</AddDigitalLendingApplication>"+
						"</Response>"+
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block AddDigitalLendingApplication:" + sOutput);

			}
			else
			{
				try
				{
					AddDigitalLendingApplicationRes_type0 add_CSAcct_res=new AddDigitalLendingApplicationRes_type0();
					add_CSAcct_res=add_CSAcct_res_msg.getAddDigitalLendingApplicationRes();

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigitalLendingApplication</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason>"+add_CSAcct_res.getStatus()+"</Reason><Status>"+add_CSAcct_res.getErrorDescription()+"</Status><td>Unable to Add DigitalLendingApplication.</td></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigitalLendingApplication</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status></Status><td>Unable to Add Digital Lending Application.</td></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigitalLendingApplication</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Add Digital Lending Application.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigitalLendingApplication</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Add Digital Lending Application.</td></Output>";
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
				e.printStackTrace();
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","AddDigitalLendingApplication Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}
