package com.newgen.dscop.client;
import java.io.IOException;
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
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddCardCCPSApplicationReqMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddCardCCPSApplicationReq_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddCardCCPSApplicationResMsg;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AddCardCCPSApplicationRes_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.ApplicationDetails_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.CardDetails_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.CustomerDemographicsInfo_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.CustomerEmploymentInfo_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.HeaderType;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.OtherCustDetails_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.PrescreeningDetails_type0;

public class AddCardCCPS extends DSCOPServiceHandler {
	
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrgRes="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	
	public String AddCCPS(String sInputXML){
		
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called addCRS");
		LogGEN.writeTrace("CBG_Log", "AddCardCCPS sInputXML---"+sInputXML);
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
			ModCBGCustomerOnboardingStub cbg_onboarding_stub = new ModCBGCustomerOnboardingStub(wsdl);
			AddCardCCPSApplicationReqMsg req_msg = new AddCardCCPSApplicationReqMsg();
			AddCardCCPSApplicationReq_type0 req_type0 = new AddCardCCPSApplicationReq_type0();
			ApplicationDetails_type0 appdtl_type0 = new ApplicationDetails_type0();
			AddCardCCPSApplicationResMsg res_msg=new AddCardCCPSApplicationResMsg();
			req_msg.setHeader(setHeaderDtls(sDate,ref_no,senderId));
			appdtl_type0.setCustomerDemographicsInfo(setCustomerDtls(sInputXML, xmlDataParser));
			appdtl_type0.setCustomerEmploymentInfo(setCustomerInfo(sInputXML, xmlDataParser));
			appdtl_type0.setOtherCustDetails(setOtherCustDetails(sInputXML, xmlDataParser));
			appdtl_type0.setCardDetails(setCardDetails(sInputXML, xmlDataParser));
			req_type0.setApplicationDetails(appdtl_type0);
			appdtl_type0.setPrescreeningDetails(setpreScreeningdtls(sInputXML, xmlDataParser));
			req_msg.setAddCardCCPSApplicationReq(req_type0);
			cbg_onboarding_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = cbg_onboarding_stub.getInputXml(req_msg);
			LogGEN.writeTrace("CBG_Log", "CCPS InputXML: " + xmlInput);
			res_msg=cbg_onboarding_stub.addCardCCPSApplication_Oper(req_msg);
			sOrgRes = cbg_onboarding_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "CCPS OutputXML: ");
			
			HeaderType header=res_msg.getHeader();
			LogGEN.writeTrace("CBG_Log", "res_msg.getHeader()"+res_msg.getHeader());
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode()"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail()"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription()"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				AddCardCCPSApplicationRes_type0 appRes_type0=new AddCardCCPSApplicationRes_type0();
				appRes_type0=res_msg.getAddCardCCPSApplicationRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>Add_CCPS</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<addCardCCPSApplicationRes>"+
				"<lapsApplicationNo>"+appRes_type0.getLapsApplicationNo()+"</lapsApplicationNo>"+
				"<cardNumber>"+appRes_type0.getCardNumber()+"</cardNumber>"+
				"<cardExpiryDate>"+appRes_type0.getCardExpiryDate()+"</cardExpiryDate>"+
				"<cardLogo>"+appRes_type0.getCardLogo()+"</cardLogo>"+
				"<status>"+appRes_type0.getStatus()+"</status>"+
				"<reason>"+appRes_type0.getReason()+"</reason>"+
				"</addCardCCPSApplicationRes>"+	
				"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block--------");
				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_CCPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add CCPS</Status></Output>";
			}
			
		}
		catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_CCPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add CCPS</Status></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_CCPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add CCPS</Status></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
			LogGEN.writeTrace("CBG_Log", inputXml);
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
				//con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''")); //Commented by Ameena 04092023 Response is not getting saved
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOutput.replaceAll("'", "''"));

			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return sOutput;
	}
	
	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("mod_cbg_customer_onboarding");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ADD_CCPS");
			LogGEN.writeTrace("CBG_Log", "mod_cbg_customer_onboarding WebServiceConfig Map : "  +wsConfig.toString());
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
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModCBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("BPM-WMS");
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
	
	private CustomerDemographicsInfo_type0 setCustomerDtls(String sInputXML,XMLParser xmlDataParser){
		LogGEN.writeTrace("CBG_Log", "inside setCustomerDtls fn ");
		xmlDataParser.setInputXML(sInputXML);
		CustomerDemographicsInfo_type0 customerDetails_type0= new CustomerDemographicsInfo_type0();
		customerDetails_type0.setCustPrefix(xmlDataParser.getValueOf("custPrefix"));
		customerDetails_type0.setCustCategory(xmlDataParser.getValueOf("custCategory"));				
		customerDetails_type0.setCustCustomerType(xmlDataParser.getValueOf("custCustomerType"));
		customerDetails_type0.setCustSubsegmentType(xmlDataParser.getValueOf("custSubsegmentType"));
		customerDetails_type0.setCustCustomerCategory(xmlDataParser.getValueOf("custCustomerCategory"));
		customerDetails_type0.setCustLocation(xmlDataParser.getValueOf("custLocation"));
		customerDetails_type0.setCustFullName(xmlDataParser.getValueOf("custFullName"));
		customerDetails_type0.setCustShortname(xmlDataParser.getValueOf("custShortname"));
		customerDetails_type0.setCustMothersMadien(xmlDataParser.getValueOf("custMothersMadien"));
		customerDetails_type0.setCustNationality(xmlDataParser.getValueOf("custNationality"));
		customerDetails_type0.setCustDob(xmlDataParser.getValueOf("custDob"));
		customerDetails_type0.setCustGender(xmlDataParser.getValueOf("custGender"));
		customerDetails_type0.setCustOurbankstaff(xmlDataParser.getValueOf("custOurbankstaff"));
		customerDetails_type0.setCustEmiratesId(xmlDataParser.getValueOf("custEmiratesId"));
		customerDetails_type0.setCustEidaExpiryDt(xmlDataParser.getValueOf("custEidaExpiryDt"));
		customerDetails_type0.setCustVisaExpiryDt(xmlDataParser.getValueOf("custVisaExpiryDt"));
		customerDetails_type0.setCustBankingWithUs(xmlDataParser.getValueOf("custBankingWithUs"));
		customerDetails_type0.setCustEmailAddress(xmlDataParser.getValueOf("custEmailAddress"));
		customerDetails_type0.setCustMobileNo(xmlDataParser.getValueOf("custMobileNo"));
		customerDetails_type0.setCustEduQualification(xmlDataParser.getValueOf("custEduQualification"));
		customerDetails_type0.setCustMaritalStatus(xmlDataParser.getValueOf("custMaritalStatus"));
		customerDetails_type0.setCustPassportNo(xmlDataParser.getValueOf("custPassportNo"));
		customerDetails_type0.setCustPpIssueDt(xmlDataParser.getValueOf("custPpIssueDt"));
		customerDetails_type0.setCustPpExpiryDt(xmlDataParser.getValueOf("custPpExpiryDt"));
		customerDetails_type0.setCustProfession(xmlDataParser.getValueOf("custProfession"));
		customerDetails_type0.setCustMailAddr1(xmlDataParser.getValueOf("custMailAddr1"));
		customerDetails_type0.setCustMailAddr2(xmlDataParser.getValueOf("custMailAddr2"));
		customerDetails_type0.setCustMailAddr3(xmlDataParser.getValueOf("custMailAddr3"));
		customerDetails_type0.setCustMailAddrCity(xmlDataParser.getValueOf("custMailAddrCity"));
		customerDetails_type0.setCustMailAddrState(xmlDataParser.getValueOf("custMailAddrState"));
		customerDetails_type0.setCustMailAddrZip(xmlDataParser.getValueOf("custMailAddrZip"));
		customerDetails_type0.setCustPresentCountry(xmlDataParser.getValueOf("custPresentCountry"));
		customerDetails_type0.setCustCntryOfRes(xmlDataParser.getValueOf("custCntryOfRes"));
		customerDetails_type0.setCustRunningSerial(xmlDataParser.getValueOf("custRunningSerial"));
		customerDetails_type0.setCustRelationshipValue(xmlDataParser.getValueOf("custRelationshipValue"));
		customerDetails_type0.setCustCustomerClass(xmlDataParser.getValueOf("custCustomerClass"));
		customerDetails_type0.setCustomerId(xmlDataParser.getValueOf("customerId"));
		customerDetails_type0.setCustCurrUnsecuredExp(xmlDataParser.getValueOf("custCurrUnsecuredExp"));
		customerDetails_type0.setCustAod(xmlDataParser.getValueOf("custAod"));
		customerDetails_type0.setCustHomeAddr1(xmlDataParser.getValueOf("custHomeAddr1"));
		customerDetails_type0.setCustHomeAddr2(xmlDataParser.getValueOf("custHomeAddr2"));
		customerDetails_type0.setCustHomeAddr3(xmlDataParser.getValueOf("custHomeAddr3"));
		customerDetails_type0.setCustHomeState(xmlDataParser.getValueOf("custHomeState"));
		customerDetails_type0.setCustHomeZip(xmlDataParser.getValueOf("custHomeZip"));
		customerDetails_type0.setCustHomeCountry(xmlDataParser.getValueOf("custHomeCountry"));
		customerDetails_type0.setCustHmecntryPhone(xmlDataParser.getValueOf("custHmecntryPhone"));
		customerDetails_type0.setCustResidencePhone(xmlDataParser.getValueOf("custResidencePhone"));
		customerDetails_type0.setCustCreditBureauConsentFlg(xmlDataParser.getValueOf("custCreditBureauConsentFlg"));
		
		LogGEN.writeTrace("CBG_Log", "setCustomerDtls comolete");
		
		return customerDetails_type0;
	}
	
	
	private CustomerEmploymentInfo_type0 setCustomerInfo(String sInputXML,XMLParser xmlDataParser){
		LogGEN.writeTrace("CBG_Log", "inside setCustomerInfo fn");
		xmlDataParser.setInputXML(sInputXML);
		CustomerEmploymentInfo_type0 customerInfo_type0= new CustomerEmploymentInfo_type0();
		customerInfo_type0.setCustNetincome(xmlDataParser.getValueOf("custNetincome"));
		customerInfo_type0.setCustTmlFlg(xmlDataParser.getValueOf("custTmlFlg"));
		customerInfo_type0.setCustLos(xmlDataParser.getValueOf("custLos"));
		customerInfo_type0.setCustLoe(xmlDataParser.getValueOf("custLoe"));
		customerInfo_type0.setCustEmploymentType(xmlDataParser.getValueOf("custEmploymentType"));
		customerInfo_type0.setCustEmpCategory(xmlDataParser.getValueOf("custEmpCategory"));
		customerInfo_type0.setCustEmployerName(xmlDataParser.getValueOf("custEmployerName"));
		customerInfo_type0.setCustEmployeeCategory(xmlDataParser.getValueOf("custEmployeeCategory"));
		customerInfo_type0.setCustEmployerPhone(xmlDataParser.getValueOf("custEmployerPhone"));
		customerInfo_type0.setCustGrossIncome(xmlDataParser.getValueOf("custGrossIncome"));
		customerInfo_type0.setCustDoj(xmlDataParser.getValueOf("custDoj"));
		customerInfo_type0.setCustIndustryClass(xmlDataParser.getValueOf("custIndustryClass"));
		customerInfo_type0.setCustDesignation(xmlDataParser.getValueOf("custDesignation"));
		customerInfo_type0.setCustEmpAddr1(xmlDataParser.getValueOf("custEmpAddr1"));
		customerInfo_type0.setCustEmployerEmirates(xmlDataParser.getValueOf("custEmployerEmirates"));
		customerInfo_type0.setCustStaffId(xmlDataParser.getValueOf("custStaffId"));
		customerInfo_type0.setCustFixedIncome(xmlDataParser.getValueOf("custFixedIncome"));
		customerInfo_type0.setCustJobConfirmation(xmlDataParser.getValueOf("custJobConfirmation"));
		customerInfo_type0.setCustServicePeriod(xmlDataParser.getValueOf("custServicePeriod"));
		customerInfo_type0.setCustEmployerType(xmlDataParser.getValueOf("custEmployerType"));
		customerInfo_type0.setCustEmpCode(xmlDataParser.getValueOf("custEmpCode"));
		customerInfo_type0.setCustNoOfSalCredits(xmlDataParser.getValueOf("custNoOfSalCredits"));
		
		LogGEN.writeTrace("CBG_Log", "setCustomerInfo complete");
		
		return customerInfo_type0;
	}
	
	
	private OtherCustDetails_type0 setOtherCustDetails(String sInputXML,XMLParser xmlDataParser){
		LogGEN.writeTrace("CBG_Log", "inside setOtherCustDetails fn ");
		xmlDataParser.setInputXML(sInputXML);
		OtherCustDetails_type0 othrcustdtl_type0= new OtherCustDetails_type0();
		othrcustdtl_type0.setCustAvgSalary(xmlDataParser.getValueOf("custAvgSalary"));
		othrcustdtl_type0.setCustApplicantType(xmlDataParser.getValueOf("custApplicantType"));
		othrcustdtl_type0.setCustAvgMobileBill(xmlDataParser.getValueOf("custAvgMobileBill"));
		othrcustdtl_type0.setCustChqReturns(xmlDataParser.getValueOf("custChqReturns"));
		othrcustdtl_type0.setCustSelfEmpCat(xmlDataParser.getValueOf("custSelfEmpCat"));
		othrcustdtl_type0.setCustLob(xmlDataParser.getValueOf("custLob"));
		othrcustdtl_type0.setCustNatureOfBuss(xmlDataParser.getValueOf("custNatureOfBuss"));
		LogGEN.writeTrace("CBG_Log", "setOtherCustDetails complete");
		return othrcustdtl_type0;
	}
	
	
	private CardDetails_type0 setCardDetails(String sInputXML,XMLParser xmlDataParser){
		LogGEN.writeTrace("CBG_Log", "inside setCardDetails fn");
		xmlDataParser.setInputXML(sInputXML);
		CardDetails_type0 cardDetails_type0= new CardDetails_type0();
		cardDetails_type0.setProductType(xmlDataParser.getValueOf("productType"));
		cardDetails_type0.setCardType(xmlDataParser.getValueOf("cardType"));
		cardDetails_type0.setComboFlag(xmlDataParser.getValueOf("comboFlag"));
		cardDetails_type0.setProductCode(xmlDataParser.getValueOf("productCode"));
		cardDetails_type0.setCreditLimit(xmlDataParser.getValueOf("creditLimit"));
		cardDetails_type0.setCreditFlag(xmlDataParser.getValueOf("creditFlag"));
		cardDetails_type0.setPromoValue(xmlDataParser.getValueOf("promoValue"));
		cardDetails_type0.setDsaCode(xmlDataParser.getValueOf("dsaCode"));
		cardDetails_type0.setOrgRefNo(xmlDataParser.getValueOf("orgRefNo"));
		cardDetails_type0.setPromptPayment(xmlDataParser.getValueOf("promptPayment"));
		cardDetails_type0.setEmbossingName(xmlDataParser.getValueOf("embossingName"));
		cardDetails_type0.setStandingInstruction(xmlDataParser.getValueOf("standingInstruction"));
		cardDetails_type0.setAdcbAccountNo(xmlDataParser.getValueOf("adcbAccountNo"));
		cardDetails_type0.setSiPercentage(xmlDataParser.getValueOf("siPercentage"));
		cardDetails_type0.setPrimaryProduct(xmlDataParser.getValueOf("primaryProduct"));
		cardDetails_type0.setFirstLevelPromocode(xmlDataParser.getValueOf("firstLevelPromocode"));
		cardDetails_type0.setSecondLevelPromocode(xmlDataParser.getValueOf("secondLevelPromocode"));
		cardDetails_type0.setCreditLimitIncrease(xmlDataParser.getValueOf("creditLimitIncrease"));
		cardDetails_type0.setEmirateSourcecode(xmlDataParser.getValueOf("emirateSourcecode"));
		cardDetails_type0.setSalaryTransfer(xmlDataParser.getValueOf("salaryTransfer"));
		cardDetails_type0.setStatementCycle(xmlDataParser.getValueOf("statementCycle"));
		cardDetails_type0.setOnlineDebitday(xmlDataParser.getValueOf("onlineDebitday"));
		cardDetails_type0.setThirdlevelPromo(xmlDataParser.getValueOf("thirdlevelPromo"));
		cardDetails_type0.setCreditShield(xmlDataParser.getValueOf("creditShield"));
		cardDetails_type0.setFileLocation(xmlDataParser.getValueOf("fileLocation"));
		cardDetails_type0.setTamoohaEnabledflg(xmlDataParser.getValueOf("tamoohaEnabledflg"));
		cardDetails_type0.setApplicationSignedDt(xmlDataParser.getValueOf("applicationSignedDt"));
		cardDetails_type0.setSupplementaryLimitPercent(xmlDataParser.getValueOf("supplementaryLimitPercent"));
		cardDetails_type0.setEcbCheckReq(xmlDataParser.getValueOf("ecbCheckReq"));
		cardDetails_type0.setLimitSignOffLevel(xmlDataParser.getValueOf("limitSignOffLevel"));
		cardDetails_type0.setC2CBankName(xmlDataParser.getValueOf("c2cBankName"));
		cardDetails_type0.setCreditCardUtilPercent(xmlDataParser.getValueOf("creditCardUtilPercent"));
		cardDetails_type0.setLoanOnCard(xmlDataParser.getValueOf("loanOnCard"));
		cardDetails_type0.setEstmtFlag(xmlDataParser.getValueOf("estmtFlag"));
		cardDetails_type0.setPrintStmtFlag(xmlDataParser.getValueOf("printStmtFlag"));
		cardDetails_type0.setCreditDecision(xmlDataParser.getValueOf("creditDecision"));
		cardDetails_type0.setGroupFlag(xmlDataParser.getValueOf("groupFlag"));
		cardDetails_type0.setTracerScore(xmlDataParser.getValueOf("tracerScore"));
		cardDetails_type0.setTracerPolicy(xmlDataParser.getValueOf("tracerPolicy"));
		cardDetails_type0.setEdmsRefNo(xmlDataParser.getValueOf("edmsRefNo"));
		cardDetails_type0.setBranchCode(xmlDataParser.getValueOf("branchCode"));
		cardDetails_type0.setEdmsEnableFlg(xmlDataParser.getValueOf("edmsEnableFlg"));
		cardDetails_type0.setPrimaryCardNo(xmlDataParser.getValueOf("primaryCardNo"));
		cardDetails_type0.setSupplName(xmlDataParser.getValueOf("supplName"));
		cardDetails_type0.setSupplPrefix(xmlDataParser.getValueOf("supplPrefix"));
		cardDetails_type0.setSupplEmbName(xmlDataParser.getValueOf("supplEmbName"));
		cardDetails_type0.setSupplRelationship(xmlDataParser.getValueOf("supplRelationship"));
		cardDetails_type0.setSupplDob(xmlDataParser.getValueOf("supplDob"));
		
		LogGEN.writeTrace("CBG_Log", "setCardDetails complete");
		
		return cardDetails_type0;
	}
	
	
	private PrescreeningDetails_type0 setpreScreeningdtls(String sInputXML,XMLParser xmlDataParser){
		
		LogGEN.writeTrace("CBG_Log", "inside setpreScreeningdtls fn");
		xmlDataParser.setInputXML(sInputXML);
		PrescreeningDetails_type0 prescreenDetails_type0= new PrescreeningDetails_type0();
		prescreenDetails_type0.setPrescrnMobileChk(xmlDataParser.getValueOf("prescrnMobileChk"));
		prescreenDetails_type0.setPrescrnAdcbrelChk(xmlDataParser.getValueOf("prescrnAdcbrelChk"));
		prescreenDetails_type0.setPrescrnMisChk(xmlDataParser.getValueOf("prescrnMisChk"));
		prescreenDetails_type0.setPrescrnDebtMgrChk(xmlDataParser.getValueOf("prescrnDebtMgrChk"));
		prescreenDetails_type0.setPrescrnMidasChk(xmlDataParser.getValueOf("prescrnMidasChk"));
		prescreenDetails_type0.setPrescrnClsdAcctChk(xmlDataParser.getValueOf("prescrnClsdAcctChk"));
		prescreenDetails_type0.setPrescrnCustBlklistChk(xmlDataParser.getValueOf("prescrnCustBlklistChk"));
		prescreenDetails_type0.setPrescrnCompBlklistChk(xmlDataParser.getValueOf("prescrnCompBlklistChk"));
		prescreenDetails_type0.setPrescrnAcctCustmemo(xmlDataParser.getValueOf("prescrnAcctCustmemo"));
		prescreenDetails_type0.setPrescrnDowJones(xmlDataParser.getValueOf("prescrnDowJones"));
		prescreenDetails_type0.setPrescrnCbRating(xmlDataParser.getValueOf("prescrnCbRating"));
		prescreenDetails_type0.setPrescrnEmCredit(xmlDataParser.getValueOf("prescrnEmCredit"));
		prescreenDetails_type0.setPrescrnCbRbExposure(xmlDataParser.getValueOf("prescrnCbRbExposure"));
		LogGEN.writeTrace("CBG_Log", "setpreScreeningdtls complete");
		return prescreenDetails_type0;
	}
	
	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
