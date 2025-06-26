package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalCreditCardRequestReqMsg;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalCreditCardRequestReq_type0;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalCreditCardRequestResMsg;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.AddDigitalCreditCardRequestRes_type0;
import com.newgen.dscop.stub.AddDigitalCreditCardRequestStub.HeaderType;

public class AddDigitalCreditCardRequest extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;

	@SuppressWarnings("finally")
	public String AddDigitalCreditCardRequestCall(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddDigitalCreditCardRequest");
		LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddDigitalCreditCardRequest");
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			String leadReferenceNumber = xmlDataParser.getValueOf("leadReferenceNumber");
			String customerId = xmlDataParser.getValueOf("customerId");
			String customerName = xmlDataParser.getValueOf("customerName");
			String nationalityCode = xmlDataParser.getValueOf("nationalityCode");
			String dateofBirth = xmlDataParser.getValueOf("dateofBirth");
			String gender = xmlDataParser.getValueOf("gender");
			String passportNo = xmlDataParser.getValueOf("passportNo");
			String passportExpiryDate = xmlDataParser.getValueOf("passportExpiryDate");
			String mobilePhoneNo = xmlDataParser.getValueOf("mobilePhoneNo");
			String employerType = xmlDataParser.getValueOf("employerType");
			String employerName = xmlDataParser.getValueOf("employerName");
			String employerCode = xmlDataParser.getValueOf("employerCode");
			String emiratesId = xmlDataParser.getValueOf("emiratesId");
			String productCode = xmlDataParser.getValueOf("productCode");
			String leadsBranchCode = xmlDataParser.getValueOf("leadsBranchCode");
			String sourceCode = xmlDataParser.getValueOf("sourceCode");
			String creditLimit = xmlDataParser.getValueOf("creditLimit");
			String processingCenter = xmlDataParser.getValueOf("processingCenter");

			String cardType = xmlDataParser.getValueOf("cardType");
			String applicationReferenceNumber = xmlDataParser.getValueOf("applicationReferenceNumber");
			String standingInstruction = xmlDataParser.getValueOf("standingInstruction");
			String adcbAcctNumber = xmlDataParser.getValueOf("adcbAcctNumber");
			String fileLocation = xmlDataParser.getValueOf("fileLocation");
			String firstLevelPromoCode = xmlDataParser.getValueOf("firstLevelPromoCode");
			String secondLevelPromoCode = xmlDataParser.getValueOf("secondLevelPromoCode");
			String c2cBankName = xmlDataParser.getValueOf("c2cBankName");
			String ccUtilizationPercentage = xmlDataParser.getValueOf("ccUtilizationPercentage");
			String promoValue = xmlDataParser.getValueOf("promoValue");
			String ecbCheck = xmlDataParser.getValueOf("ecbCheck");
			String ecbScore = xmlDataParser.getValueOf("ecbScore");
			String salaryTransferADCB = xmlDataParser.getValueOf("salaryTransferADCB");
			String sameDayEmobossingFlag = xmlDataParser.getValueOf("sameDayEmobossingFlag");
			String dispatchMode = xmlDataParser.getValueOf("dispatchMode");
			String embossingName = xmlDataParser.getValueOf("embossingName");
			String eStatement = xmlDataParser.getValueOf("eStatement");
			String printStatement = xmlDataParser.getValueOf("printStatement");
			String onlineDebitPercentage = xmlDataParser.getValueOf("onlineDebitPercentage");
			String statementCycle = xmlDataParser.getValueOf("statementCycle");
			String onlineDebitDay = xmlDataParser.getValueOf("onlineDebitDay");
			String creditShield = xmlDataParser.getValueOf("creditShield");
			String thirdLevelPromoCode = xmlDataParser.getValueOf("thirdLevelPromoCode");
			String applicationSignedDate = xmlDataParser.getValueOf("applicationSignedDate");
			String applicationFormType = xmlDataParser.getValueOf("applicationFormType");
			String cardsBranchCode = xmlDataParser.getValueOf("cardsBranchCode");
			String emirateSourceCode = xmlDataParser.getValueOf("emirateSourceCode");
			String ibanAcctNumber = xmlDataParser.getValueOf("ibanAcctNumber");
			String supplementaryPrefix = xmlDataParser.getValueOf("supplementaryPrefix");
			String supplementaryNameInFull = xmlDataParser.getValueOf("supplementaryNameInFull");
			String supplementaryEmbossingName = xmlDataParser.getValueOf("supplementaryEmbossingName");
			String supplementaryDateOfBirth = xmlDataParser.getValueOf("supplementaryDateOfBirth");
			String supplementaryMaritalStatus = xmlDataParser.getValueOf("supplementaryMaritalStatus");
			String supplementaryNationalityCode = xmlDataParser.getValueOf("supplementaryNationalityCode");
			String supplementaryRelationship = xmlDataParser.getValueOf("supplementaryRelationship");
			String supplementaryMothersMaidenName = xmlDataParser.getValueOf("supplementaryMothersMaidenName");
			String supplementaryPassportNo = xmlDataParser.getValueOf("supplementaryPassportNo");
			String supplementaryLimitPercentage = xmlDataParser.getValueOf("supplementaryLimitPercentage");

			String title = xmlDataParser.getValueOf("title");
			String shortName = xmlDataParser.getValueOf("shortName");
			String maritalStatus = xmlDataParser.getValueOf("maritalStatus");
			String educationalQualification = xmlDataParser.getValueOf("educationalQualification");
			String ourBankStaff = xmlDataParser.getValueOf("ourBankStaff");
			String category = xmlDataParser.getValueOf("category");
			String subSegmentType = xmlDataParser.getValueOf("subSegmentType");
			String customerCategory = xmlDataParser.getValueOf("customerCategory");
			String mothersMaidenName = xmlDataParser.getValueOf("mothersMaidenName");
			String passportIssuedDate = xmlDataParser.getValueOf("passportIssuedDate");
			String visaNumber = xmlDataParser.getValueOf("visaNumber");
			String visaIssuedDate = xmlDataParser.getValueOf("visaIssuedDate");
			String visaExpiryDate = xmlDataParser.getValueOf("visaExpiryDate");
			String eidaExpiryDate = xmlDataParser.getValueOf("eidaExpiryDate");
			String customerType = xmlDataParser.getValueOf("customerType");
			String noOfDependents = xmlDataParser.getValueOf("noOfDependents");
			String customerClass = xmlDataParser.getValueOf("customerClass");
			String location = xmlDataParser.getValueOf("location");
			String customerCPVWaived = xmlDataParser.getValueOf("customerCPVWaived");
			String acctOpeningDate = xmlDataParser.getValueOf("acctOpeningDate");
			String relationshipValue = xmlDataParser.getValueOf("relationshipValue");
			String runningSerialNo = xmlDataParser.getValueOf("runningSerialNo");

			String email = xmlDataParser.getValueOf("email");
			String officeEmail = xmlDataParser.getValueOf("officeEmail");
			String mailingPOBoxNo = xmlDataParser.getValueOf("mailingPOBoxNo");
			String mailingAddress1 = xmlDataParser.getValueOf("mailingAddress1");
			String mailingAddress2 = xmlDataParser.getValueOf("mailingAddress2");
			String mailingAddress3 = xmlDataParser.getValueOf("mailingAddress3");
			String mailingAddressCountry = xmlDataParser.getValueOf("mailingAddressCountry");
			String mailingAddresCity = xmlDataParser.getValueOf("mailingAddresCity");
			String mailingAddresState = xmlDataParser.getValueOf("mailingAddresState");
			String uaeResAddressLine1 = xmlDataParser.getValueOf("uaeResAddressLine1");
			String uaeResAddressLine2 = xmlDataParser.getValueOf("uaeResAddressLine2");
			String uaeResAddressLine3 = xmlDataParser.getValueOf("uaeResAddressLine3");
			String uaeResAddressCity = xmlDataParser.getValueOf("uaeResAddressCity");
			String uaeResAddressState = xmlDataParser.getValueOf("uaeResAddressState");
			String uaeResPOBoxNo = xmlDataParser.getValueOf("uaeResPOBoxNo");
			String countryOfResidence = xmlDataParser.getValueOf("countryOfResidence");
			String uaeResPhoneNo = xmlDataParser.getValueOf("uaeResPhoneNo");
			String homeCountryAddressLine1 = xmlDataParser.getValueOf("homeCountryAddressLine1");
			String homeCountryAddressLine2 = xmlDataParser.getValueOf("homeCountryAddressLine2");
			String homeCountryAddressLine3 = xmlDataParser.getValueOf("homeCountryAddressLine3");
			String homeCountryAddressCity = xmlDataParser.getValueOf("homeCountryAddressCity");
			String homeCountryAddressState = xmlDataParser.getValueOf("homeCountryAddressState");
			String homeCountryPOBoxNo = xmlDataParser.getValueOf("homeCountryPOBoxNo");
			String homeCountry = xmlDataParser.getValueOf("homeCountry");
			String homeCountryPhoneNo = xmlDataParser.getValueOf("homeCountryPhoneNo");

			String employerCPVWaived = xmlDataParser.getValueOf("employerCPVWaived");
			String employerEmirate = xmlDataParser.getValueOf("employerEmirate");
			String dateOfJoining = xmlDataParser.getValueOf("dateOfJoining");
			String designation = xmlDataParser.getValueOf("designation");
			String profession = xmlDataParser.getValueOf("profession");
			String grossIncome = xmlDataParser.getValueOf("grossIncome");
			String staffId = xmlDataParser.getValueOf("staffId");
			String employmentType = xmlDataParser.getValueOf("employmentType");
			String employmentTenure = xmlDataParser.getValueOf("employmentTenure");
			String jobConfirmation = xmlDataParser.getValueOf("jobConfirmation");

			String userDefinedField1 = xmlDataParser.getValueOf("userDefinedField1");
			String userDefinedField2 = xmlDataParser.getValueOf("userDefinedField2");
			String userDefinedField3 = xmlDataParser.getValueOf("userDefinedField3");
			String userDefinedField4 = xmlDataParser.getValueOf("userDefinedField4");
			String userDefinedField5 = xmlDataParser.getValueOf("userDefinedField5");
			String userDefinedField6 = xmlDataParser.getValueOf("userDefinedField6");
			String userDefinedField7 = xmlDataParser.getValueOf("userDefinedField7");
			String userDefinedField8 = xmlDataParser.getValueOf("userDefinedField8");
			String userDefinedField9 = xmlDataParser.getValueOf("userDefinedField9");
			String userDefinedField10 = xmlDataParser.getValueOf("userDefinedField10");

			AddDigitalCreditCardRequestStub add_credit_request_stub=new AddDigitalCreditCardRequestStub(sWSDLPath);
			AddDigitalCreditCardRequestReq_type0 add_credit_req=new AddDigitalCreditCardRequestReq_type0();
			AddDigitalCreditCardRequestReqMsg add_credit_req_msg=new AddDigitalCreditCardRequestReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddDigitalCreditCardRequest");
			Header_Input.setServiceAction("Addition");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer("");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setRepTimeStamp("");
			Header_Input.setUsername("TESTUSR");
			Header_Input.setCredentials("123");
			Header_Input.setVersionNo("1.0");
			Header_Input.setReturnCode("");
			Header_Input.setErrorDescription("");
			Header_Input.setErrorDetail("");

			add_credit_req_msg.setHeader(Header_Input);

			add_credit_req.setLeadReferenceNumber(leadReferenceNumber);
			add_credit_req.setCustomerId(customerId);
			add_credit_req.setCustomerName(customerName);
			add_credit_req.setNationalityCode(nationalityCode);
			add_credit_req.setDateOfBirth(dateofBirth);
			add_credit_req.setGender(gender);
			add_credit_req.setPassportNo(passportNo);
			add_credit_req.setPassportExpiryDate(passportExpiryDate);
			add_credit_req.setMobilePhoneNo(mobilePhoneNo);
			add_credit_req.setEmployerType(employerType);
			add_credit_req.setEmployerName(employerName);
			add_credit_req.setEmployerCode(employerCode);
			add_credit_req.setEmiratesId(emiratesId);
			add_credit_req.setProductCode(productCode);
			add_credit_req.setLeadsBranchCode(leadsBranchCode);
			add_credit_req.setSourceCode(sourceCode);
			add_credit_req.setCreditLimit(creditLimit);
			add_credit_req.setProcessingCenter(processingCenter);
			//CardDetails
			add_credit_req.setCardType(cardType);
			add_credit_req.setApplicationReferenceNumber(applicationReferenceNumber);
			add_credit_req.setStandingInstruction(standingInstruction);
			add_credit_req.setAdcbAcctNumber(adcbAcctNumber);
			add_credit_req.setFileLocation(fileLocation);
			add_credit_req.setFirstLevelPromoCode(firstLevelPromoCode);
			add_credit_req.setSecondLevelPromoCode(secondLevelPromoCode);
			add_credit_req.setC2CBankName(c2cBankName);
			add_credit_req.setCcUtilizationPercentage(ccUtilizationPercentage);
			add_credit_req.setPromoValue(promoValue);
			add_credit_req.setEcbCheck(ecbCheck);
			add_credit_req.setEcbScore(ecbScore);
			add_credit_req.setSalaryTransferADCB(salaryTransferADCB);
			add_credit_req.setSameDayEmobossingFlag(sameDayEmobossingFlag);
			add_credit_req.setDispatchMode(dispatchMode);
			add_credit_req.setEmbossingName(embossingName);
			add_credit_req.setEStatement(eStatement);
			add_credit_req.setPrintStatement(printStatement);
			add_credit_req.setOnlineDebitPercentage(onlineDebitPercentage);
			add_credit_req.setStatementCycle(statementCycle);
			add_credit_req.setOnlineDebitDay(onlineDebitDay);
			add_credit_req.setCreditShield(creditShield);
			add_credit_req.setThirdLevelPromoCode(thirdLevelPromoCode);
			add_credit_req.setApplicationSignedDate(applicationSignedDate);
			add_credit_req.setApplicationFormType(applicationFormType);
			add_credit_req.setCardsBranchCode(cardsBranchCode);

			add_credit_req.setEmirateSourceCode(emirateSourceCode);
			add_credit_req.setIbanAcctNumber(ibanAcctNumber);

			//supplementary Details
			add_credit_req.setSupplementaryPrefix(supplementaryPrefix);
			add_credit_req.setSupplementaryNameInFull(supplementaryNameInFull);
			add_credit_req.setSupplementaryEmbossingName(supplementaryEmbossingName);
			add_credit_req.setSupplementaryDateOfBirth(supplementaryDateOfBirth);
			add_credit_req.setSupplementaryMaritalStatus(supplementaryMaritalStatus);
			add_credit_req.setSupplementaryNationalityCode(supplementaryNationalityCode);
			add_credit_req.setSupplementaryRelationship(supplementaryRelationship);
			add_credit_req.setSupplementaryMothersMaidenName(supplementaryMothersMaidenName);
			add_credit_req.setSupplementaryPassportNo(supplementaryPassportNo);
			add_credit_req.setSupplementaryLimitPercentage(supplementaryLimitPercentage);

			//other Details
			add_credit_req.setUserDefinedField1(userDefinedField1);
			add_credit_req.setUserDefinedField2(userDefinedField2);
			add_credit_req.setUserDefinedField3(userDefinedField3);
			add_credit_req.setUserDefinedField4(userDefinedField4);
			add_credit_req.setUserDefinedField5(userDefinedField5);
			add_credit_req.setUserDefinedField6(userDefinedField6);
			add_credit_req.setUserDefinedField7(userDefinedField7);
			add_credit_req.setUserDefinedField8(userDefinedField8);
			add_credit_req.setUserDefinedField9(userDefinedField9);
			add_credit_req.setUserDefinedField10(userDefinedField10);

			//customerDemographic
			add_credit_req.setTitle(title);
			add_credit_req.setShortName(shortName);
			add_credit_req.setMaritalStatus(maritalStatus);
			add_credit_req.setEducationalQualification(educationalQualification);
			add_credit_req.setOurBankStaff(ourBankStaff);
			add_credit_req.setCategory(category);
			add_credit_req.setSubSegmentType(subSegmentType);
			add_credit_req.setCustomerCategory(customerCategory);
			add_credit_req.setMothersMaidenName(mothersMaidenName);
			add_credit_req.setPassportIssuedDate(passportIssuedDate);
			add_credit_req.setVisaExpiryDate(visaExpiryDate);
			add_credit_req.setVisaIssuedDate(visaIssuedDate);
			add_credit_req.setVisaNumber(visaNumber);
			add_credit_req.setEidaExpiryDate(eidaExpiryDate);
			add_credit_req.setCustomerType(customerType);
			add_credit_req.setNoOfDependents(noOfDependents);
			add_credit_req.setCustomerClass(customerClass);
			add_credit_req.setLocation(location);
			add_credit_req.setCustomerCPVWaived(customerCPVWaived);
			add_credit_req.setAcctOpeningDate(acctOpeningDate);
			add_credit_req.setRelationshipValue(relationshipValue);
			add_credit_req.setRunningSerialNo(runningSerialNo);

			//contact details
			add_credit_req.setEmail(email);
			add_credit_req.setOfficeEmail(officeEmail);
			add_credit_req.setMailingPOBoxNo(mailingPOBoxNo);
			add_credit_req.setMailingAddress1(mailingAddress1);
			add_credit_req.setMailingAddress2(mailingAddress2);
			add_credit_req.setMailingAddress3(mailingAddress3);
			add_credit_req.setMailingAddressCountry(mailingAddressCountry);
			add_credit_req.setMailingAddresCity(mailingAddresCity);
			add_credit_req.setMailingAddresState(mailingAddresState);
			add_credit_req.setUaeResAddressLine1(uaeResAddressLine1);
			add_credit_req.setUaeResAddressLine2(uaeResAddressLine2);
			add_credit_req.setUaeResAddressLine3(uaeResAddressLine3);
			add_credit_req.setUaeResAddressCity(uaeResAddressCity);
			add_credit_req.setUaeResAddressState(uaeResAddressState);
			add_credit_req.setUaeResPOBoxNo(uaeResPOBoxNo);
			add_credit_req.setUaeResPhoneNo(uaeResPhoneNo);
			add_credit_req.setCountryOfResidence(countryOfResidence);
			add_credit_req.setHomeCountryAddressLine1(homeCountryAddressLine1);
			add_credit_req.setHomeCountryAddressLine2(homeCountryAddressLine2);
			add_credit_req.setHomeCountryAddressLine3(homeCountryAddressLine3);
			add_credit_req.setHomeCountryAddressCity(homeCountryAddressCity);
			add_credit_req.setHomeCountryAddressState(homeCountryAddressState);
			add_credit_req.setHomeCountryPOBoxNo(homeCountryPOBoxNo);
			add_credit_req.setHomeCountryPhoneNo(homeCountryPhoneNo);
			add_credit_req.setHomeCountry(homeCountry);

			//employment details
			add_credit_req.setEmployerCPVWaived(employerCPVWaived);
			add_credit_req.setEmployerEmirate(employerEmirate);
			add_credit_req.setDateOfJoining(dateOfJoining);
			add_credit_req.setDesignation(designation);
			add_credit_req.setProfession(profession);
			add_credit_req.setGrossIncome(grossIncome);
			add_credit_req.setStaffId(staffId);
			add_credit_req.setEmploymentType(employmentType);
			add_credit_req.setEmploymentTenure(employmentTenure);
			add_credit_req.setJobConfirmation(jobConfirmation);

			add_credit_req_msg.setAddDigitalCreditCardRequestReq(add_credit_req);
			
			xmlInput= add_credit_request_stub.getInputXml(add_credit_req_msg);
			
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequest xmlInput xml : "+xmlInput);
			add_credit_request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			AddDigitalCreditCardRequestResMsg add_credit_res_msg = add_credit_request_stub.addDigitalCreditCardRequest_Oper(add_credit_req_msg);
			sOrg_put=add_credit_request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCardRequestResMsg sOrg_put: "+sOrg_put);
			Header_Input=add_credit_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			AddDigitalCreditCardRequestRes_type0 add_credit_res_type0 = add_credit_res_msg.getAddDigitalCreditCardRequestRes();
			String status= add_credit_res_type0.getStatus();
			String reason = add_credit_res_type0.getReason();
			String leadApplicationNumber = add_credit_res_type0.getLeadApplicationNumber();
			LogGEN.writeTrace("CBG_Log", "Status sOrg_put: "+status);
			LogGEN.writeTrace("CBG_Log", "reason sOrg_put: "+reason);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddDigitalCreditCardRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddDigitalCreditCardRequestRes>"+
						"<status>"+status+"</status>"+
						"<reason>"+reason+"</reason>"+
						"<leadApplicationNumber>"+leadApplicationNumber+"</leadApplicationNumber>"+
						"</AddDigitalCreditCardRequestRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddDigitalCreditCardRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to add Digital Credit Card.</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add Digital Credit Card.</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add Digital Credit Card.</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Pratial Success";
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
			LogGEN.writeTrace("CBG_Log", "WINAME:"+winame);
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

			}
			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","Modify Cudtomer  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","ModifyCudtomer  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}
	}
}

