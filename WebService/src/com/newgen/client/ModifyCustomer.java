package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.ModCustomerInfoStub;
import com.newgen.stub.ModCustomerInfoStub.Addresses_type0;
import com.newgen.stub.ModCustomerInfoStub.CBRCodes_type0;
import com.newgen.stub.ModCustomerInfoStub.CustomerAdditionalDetails_type0;
import com.newgen.stub.ModCustomerInfoStub.CustomerAdresses_type0;
import com.newgen.stub.ModCustomerInfoStub.CustomerCBRCodes_type0;
import com.newgen.stub.ModCustomerInfoStub.CustomerSource_type0;
import com.newgen.stub.ModCustomerInfoStub.HeaderType;
import com.newgen.stub.ModCustomerInfoStub.MisCodeDetails_type0;
import com.newgen.stub.ModCustomerInfoStub.MisCodes_type0;
import com.newgen.stub.ModCustomerInfoStub.ModCustomerInfoReqMsg;
import com.newgen.stub.ModCustomerInfoStub.ModCustomerInfoReq_type0;
import com.newgen.stub.ModCustomerInfoStub.ModCustomerInfoResMsg;
import com.newgen.stub.ModCustomerInfoStub.ModCustomerInfoRes_type0;
import com.newgen.stub.ModCustomerInfoStub.AdditionalIncomeCountry_type0;
import com.newgen.stub.ModCustomerInfoStub.AdditionalIncome_type0;
import com.newgen.stub.ModCustomerInfoStub.CustAccountOpenPurpose_type0;
import com.newgen.stub.ModCustomerInfoStub.CustSourceIndustry_type0;

public class ModifyCustomer extends WebServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;//Added By Harish For inserting original mssg
	@SuppressWarnings("finally")
	public String ModifyCustomerToFCR(String sInputXML)
	{
	
		LogGEN.writeTrace("Log", "Fuction called Modify_Customer");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String sCustomerName=xmlDataParser.getValueOf("customerName");
		String customerId=xmlDataParser.getValueOf("customerId");
		String custPrefix=xmlDataParser.getValueOf("custPrefix");
		String custDesignation=xmlDataParser.getValueOf("custDesignation");
		String custLanguage=xmlDataParser.getValueOf("custLanguage");
		String custSignatureType=xmlDataParser.getValueOf("custSignatureType");
		String custProfessionCategory=xmlDataParser.getValueOf("custProfessionCategory");
		String custPhone=xmlDataParser.getValueOf("custPhone");
		String custPhoneOff=xmlDataParser.getValueOf("custPhoneOff");
		//String custFax=xmlDataParser.getValueOf("custFax");
		//String custMobile=xmlDataParser.getValueOf("custMobile");
		String custEmail=xmlDataParser.getValueOf("custEmail");
		//String custStatus=xmlDataParser.getValueOf("custStatus");
		String custType=xmlDataParser.getValueOf("custType");
		System.out.println("custType---"+custType);
		String custSex=xmlDataParser.getValueOf("custSex");
		String custMaritalStatus=xmlDataParser.getValueOf("custMaritalStatus");
		String custDependantChildren=xmlDataParser.getValueOf("custDependantChildren");
		String custEthnicOrigin=xmlDataParser.getValueOf("custEthnicOrigin");
		String custNationality=xmlDataParser.getValueOf("custNationality");
		//String custBusinessRegNo=xmlDataParser.getValueOf("custBusinessRegNo");
		//String custBusinessCategory=xmlDataParser.getValueOf("custBusinessCategory");
		//String custBusinessType=xmlDataParser.getValueOf("custBusinessType");
		String flagStaff=xmlDataParser.getValueOf("flagStaff");
		String flagMinor=xmlDataParser.getValueOf("flagMinor");
		//String flagMember=xmlDataParser.getValueOf("flagMember");
		//String flagLocGlob=xmlDataParser.getValueOf("flagLocGlob");
		//String flagUnadv=xmlDataParser.getValueOf("flagUnadv");
		//String flagDocComplete=xmlDataParser.getValueOf("flagDocComplete");
		//String flagKio=xmlDataParser.getValueOf("flagKio");
		//String flagHoldMail=xmlDataParser.getValueOf("flagHoldMail");
		String codMntOption=xmlDataParser.getValueOf("codMntOption");
		String codLastMntMakerId=xmlDataParser.getValueOf("codLastMntMakerId");
		String codLastMntCheckerId=xmlDataParser.getValueOf("codLastMntCheckerId");
		String codOfficerId=xmlDataParser.getValueOf("codOfficerId");
		String codMemberNo=xmlDataParser.getValueOf("codMemberNo");
		//String codReport1=xmlDataParser.getValueOf("codReport1");
		//String codReport2=xmlDataParser.getValueOf("codReport2");
		//String codReport3=xmlDataParser.getValueOf("codReport3");
		String codLiabNo=xmlDataParser.getValueOf("codLiabNo");
		//String creditRating=xmlDataParser.getValueOf("creditRating");
		//String dateRevision=xmlDataParser.getValueOf("dateRevision");
		//String fxCustCleanRiskLimit=xmlDataParser.getValueOf("fxCustCleanRiskLimit");
		//String fxCleanRiskLimit=xmlDataParser.getValueOf("fxCleanRiskLimit");
		String amtLimitTot=xmlDataParser.getValueOf("amtLimitTot");
		String codCcyLimit=xmlDataParser.getValueOf("codCcyLimit");
		String codEmployeeId=xmlDataParser.getValueOf("codEmployeeId");
		//String guardianType=xmlDataParser.getValueOf("guardianType");
		//String acctNoDefault=xmlDataParser.getValueOf("acctNoDefault");
		String txtCustResidence=xmlDataParser.getValueOf("txtCustResidence");
		String aflIssueDdate=xmlDataParser.getValueOf("aflIssueDdate");
		String aflExpiryDdate=xmlDataParser.getValueOf("aflExpiryDdate");
		String aflEffectiveDdate=xmlDataParser.getValueOf("aflEffectiveDdate");
		String custcurrentjobstartdt=xmlDataParser.getValueOf("custCurrentJobStartDate");
		String custcurrentjobsalary=xmlDataParser.getValueOf("custCurrentJobSalaryAmount");
		
		String CustDesignation=xmlDataParser.getValueOf("custDesignation");
		String CustEmployerName=xmlDataParser.getValueOf("custEmployerName");
		String CustEmployerMobile=xmlDataParser.getValueOf("custEmployerMobile");
		String CustEmployerFax=xmlDataParser.getValueOf("custEmployerFax");
		String CustEmployerPhone=xmlDataParser.getValueOf("custEmployerPhone");
		String CustMonthlyIncome=xmlDataParser.getValueOf("custMonthlyIncome");
		String CustMotherMaidenName=xmlDataParser.getValueOf("custMotherMaidenName");
		String CustOccupation=xmlDataParser.getValueOf("custOccupation");
		String CustOccupationCategory=xmlDataParser.getValueOf("custOccupationCategory");
		String CustPassportExpiryDate=xmlDataParser.getValueOf("custPassportExpiryDate");
		String CustPassportIssueDate=xmlDataParser.getValueOf("custPassportIssueDate");
		String CustVisaExpiryDate=xmlDataParser.getValueOf("custVisaExpiryDate");
		String CustVisaIssueDate=xmlDataParser.getValueOf("custVisaIssueDate");
		String CustVisaNumber=xmlDataParser.getValueOf("custVisaNumber");
		String CustVisaType=xmlDataParser.getValueOf("custVisaType");
		String CustMobile=xmlDataParser.getValueOf("custMobile");
		String passport=xmlDataParser.getValueOf("custPassportNumber");
		String EIDA=xmlDataParser.getValueOf("custEIDA");
		String sCustShortName = xmlDataParser.getValueOf("custShortName");
		//String CBRCODES=xmlDataParser.getValueOf("CBRCodes");
		String corAddresses="";
		String resAddresses="";
		String perAddresses="";
		String empAddresses=""; //Added by Shivanshu ATP-400
		/// Need to Parse A new Tag Value Shivanshu ATP - 400
		String websiteAddress = xmlDataParser.getValueOf("websiteAddress");
		String sourceOfIncome = xmlDataParser.getValueOf("sourceOfIncome");
		String natureOfBusiness = xmlDataParser.getValueOf("natureOfBusiness");
		String employmentType = xmlDataParser.getValueOf("employmentType");
		String resLandmark = xmlDataParser.getValueOf("resLandmark");
		String resLatitude = xmlDataParser.getValueOf("resLatitude");
		String resLongitude = xmlDataParser.getValueOf("resLongitude");
		String mailingLandmark = xmlDataParser.getValueOf("mailingLandmark");
		String mailingLatitude = xmlDataParser.getValueOf("mailingLatitude");
		String mailingLongitude = xmlDataParser.getValueOf("mailingLongitude");
		String eidaExpiryDate = xmlDataParser.getValueOf("eidaExpiryDate");
		String countryOfIncome = xmlDataParser.getValueOf("countryOfIncome");
		String annualIncome = xmlDataParser.getValueOf("annualIncome");
		String annualIncomeCurrency = xmlDataParser.getValueOf("annualIncomeCurrency");
		String dualNationalityFlag = xmlDataParser.getValueOf("dualNationalityFlag");
		String secondNationality = xmlDataParser.getValueOf("secondNationality");
		String permLandmark = xmlDataParser.getValueOf("permLandmark");
		String permLatitude = xmlDataParser.getValueOf("permLatitude");
		String permLongitude = xmlDataParser.getValueOf("permLongitude");
		String selfEmpCompanyName = xmlDataParser.getValueOf("selfEmpCompanyName");
		String custEducationalQualification = xmlDataParser.getValueOf("custEducationalQualification");
		// New field ADD For DCRA AO Shivanshu
//		String sourceOfWealth = xmlDataParser.getValueOf("sourceOfWealth");
		String otherBankUAEAccount = xmlDataParser.getValueOf("otherBankUAEAccount");
		String pepStatusRisk = xmlDataParser.getValueOf("pepStatusRisk");
		String onboardingRisk = xmlDataParser.getValueOf("onboardingRisk");
		String channelRisk = xmlDataParser.getValueOf("channelRisk");
		String powerOfAttorney = xmlDataParser.getValueOf("powerOfAttorney");
		
		
		//Added by Shivanshu DCRA
		String poaNationality = xmlDataParser.getValueOf("poaNationality");    			
		String poaResidency = xmlDataParser.getValueOf("poaResidency");  			
		String primSourceofIncomeIndustry = xmlDataParser.getValueOf("primSourceofIncomeIndustry");  			
		String primSourceofIncomeCntry = xmlDataParser.getValueOf("primSourceofIncomeCntry");
//		String addSourceIncomeIndustry = xmlDataParser.getValueOf("addSourceIncomeIndustry");	
		
		
		
		String customerProductRelationInfoValue = "";
	    if (sInputXML.contains("custProductRelation2")) {
	      customerProductRelationInfoValue = sInputXML.substring(sInputXML.indexOf("<custProductRelation2>") + 22, sInputXML.indexOf("</custProductRelation2>"));
	    }
	    LogGEN.writeTrace("Log", "customerProductRelationInfoValue---" + customerProductRelationInfoValue);
		
		if(sInputXML.contains("Corr_Addresses"))
			corAddresses=sInputXML.substring(sInputXML.indexOf("<Corr_Addresses>")+16,sInputXML.indexOf("</Corr_Addresses>"));
		if(sInputXML.contains("Res_Addresses"))
			resAddresses=sInputXML.substring(sInputXML.indexOf("<Res_Addresses>")+15,sInputXML.indexOf("</Res_Addresses>"));
		if(sInputXML.contains("Per_Addresses"))
			perAddresses=sInputXML.substring(sInputXML.indexOf("<Per_Addresses>")+15,sInputXML.indexOf("</Per_Addresses>"));
		if(sInputXML.contains("Emp_Addresses")) //Added by shivanshu ATP-400
			empAddresses=sInputXML.substring(sInputXML.indexOf("<Emp_Addresses>")+15,sInputXML.indexOf("</Emp_Addresses>"));
		
		String cbr="";
		
		String miscodes="";
		///get relations
		//TestXML xml1=new TestXML();
		TestXML xml1=new TestXML();
		try
		{
			miscodes=xml1.getTagValue(sInputXML, "misCode");
			
		}
		catch (Exception e1)
		{
			System.out.println("errror11111111111111");
		}
		
		
		
		try
		{
			 cbr=xml1.getTagValue(sInputXML, "CBRCode");
			LogGEN.writeTrace("Log", cbr);
		}
		catch (Exception e1)
		{
			System.out.println("errror11111111111111");
		}
		String[]cbrcode;
		
		if(!cbr.equalsIgnoreCase(""))
		{
			cbrcode=cbr.split(";");
		}
		else
			cbrcode=new String[0];
		
		
		
		WebServiceHandler sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			sHandler.readCabProperty("MODIFY_CUSTOMER");
			
			
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			
			ModCustomerInfoStub mod_cust_stub=new ModCustomerInfoStub(sWSDLPath);
			ModCustomerInfoReq_type0 mod_cust_req=new ModCustomerInfoReq_type0();
			ModCustomerInfoReqMsg mod_cust_req_msg=new ModCustomerInfoReqMsg();
		    ModCustomerInfoStub.CustomerProductRelationInfo_type0 mod_cust_type0 = new ModCustomerInfoStub.CustomerProductRelationInfo_type0();


			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCustomerInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("");
			Header_Input.setCredentials(loggedinuser);
			mod_cust_req_msg.setHeader(Header_Input);
		
			mod_cust_req.setCustomerId(customerId);
			mod_cust_req.setCodMntOption(codMntOption);
			 
			if (customerProductRelationInfoValue.equalsIgnoreCase("BOTH"))
		      {
		        mod_cust_type0.setCustProductRelation2(customerProductRelationInfoValue);
		        mod_cust_req.setCustomerProductRelationInfo(mod_cust_type0);
		      }
			 
			mod_cust_req.setCodLastMntCheckerId("WMSUSER");
			mod_cust_req.setCodLastMntMakerId("WMSUSER");
			
			if(!sCustomerName.equalsIgnoreCase(""))
				mod_cust_req.setCustFullName(sCustomerName);
			if(!custPrefix.equalsIgnoreCase(""))
				mod_cust_req.setCustPrefix(custPrefix);
			if(!sCustShortName.equalsIgnoreCase(""))
				mod_cust_req.setCustShortName(sCustShortName);
			if(!aflEffectiveDdate.equalsIgnoreCase(""))
				mod_cust_req.setAflEffectiveDate(aflEffectiveDdate);
			if(!aflExpiryDdate.equalsIgnoreCase(""))
				mod_cust_req.setAflExpiryDate(aflExpiryDdate);
			if(!aflIssueDdate.equalsIgnoreCase(""))
				mod_cust_req.setAflIssueDate(aflIssueDdate);
			if(!amtLimitTot.equalsIgnoreCase(""))
				mod_cust_req.setAmtLimitTot(amtLimitTot);
			if(!codCcyLimit.equalsIgnoreCase(""))
				mod_cust_req.setCodCcyLimit(codCcyLimit);
			if(!codEmployeeId.equalsIgnoreCase(""))
				mod_cust_req.setCodEmployeeId(codEmployeeId);
			if(!codLastMntCheckerId.equalsIgnoreCase(""))
				mod_cust_req.setCodLastMntCheckerId(codLastMntCheckerId);
			if(!codLastMntMakerId.equalsIgnoreCase(""))
				mod_cust_req.setCodLastMntMakerId(codLastMntMakerId);
			if(!codLiabNo.equalsIgnoreCase(""))
				mod_cust_req.setCodLiabNo(codLiabNo);
			if(!codMemberNo.equalsIgnoreCase(""))
				mod_cust_req.setCodMemberNo(codMemberNo);
			if(!custSex.equalsIgnoreCase(""))
				mod_cust_req.setCustSex(custSex);
			if(!custSignatureType.equalsIgnoreCase(""))
				mod_cust_req.setCustSignatureType(custSignatureType);
			if(!flagMinor.equalsIgnoreCase(""))
				mod_cust_req.setFlagMinor(flagMinor);
			if(!custProfessionCategory.equalsIgnoreCase(""))
				mod_cust_req.setCustProfessionCategory(custProfessionCategory);
			if(!custEthnicOrigin.equalsIgnoreCase(""))
				mod_cust_req.setCustEthnicOrigin(custEthnicOrigin);
			if(!flagStaff.equalsIgnoreCase(""))
				mod_cust_req.setFlagStaff(flagStaff);
			if(!custPhoneOff.equalsIgnoreCase(""))
				mod_cust_req.setCustPhoneOff(custPhoneOff);
			if(!custType.equalsIgnoreCase(""))
				mod_cust_req.setCustType(custType);
							
			if(!codOfficerId.equalsIgnoreCase(""))
				mod_cust_req.setCodOfficerId(codOfficerId);
			if(!custDesignation.equalsIgnoreCase(""))
				mod_cust_req.setCustDesignation(custDesignation);
			if(!custEmail.equalsIgnoreCase(""))
				mod_cust_req.setCustEmail(custEmail);
			if(!custDependantChildren.equalsIgnoreCase(""))
				mod_cust_req.setCustDependantChildren(custDependantChildren);
			if(!custDesignation.equalsIgnoreCase(""))
				mod_cust_req.setCustDesignation(custDesignation);
			if(!custLanguage.equalsIgnoreCase(""))
				mod_cust_req.setCustLanguage(custLanguage);
			if(!custMaritalStatus.equalsIgnoreCase(""))
				mod_cust_req.setCustMaritalStatus(custMaritalStatus);
			if(!custNationality.equalsIgnoreCase(""))
				mod_cust_req.setCustNationality(custNationality);
			if(!custPhone.equalsIgnoreCase(""))
				mod_cust_req.setCustPhone(custPhone);
			if(!CustMobile.equalsIgnoreCase(""))
				mod_cust_req.setCustMobile(CustMobile);
			if(!EIDA.equalsIgnoreCase(""))
				mod_cust_req.setCustCitizenshipId(EIDA);
			if(!txtCustResidence.equalsIgnoreCase(""))
				mod_cust_req.setTxtCustResidence(txtCustResidence);
		
			CustomerCBRCodes_type0 cust_cbr=new CustomerCBRCodes_type0();
			CBRCodes_type0[] cbr_codes=new CBRCodes_type0[cbrcode.length];
			LogGEN.writeTrace("Log", "Before CBR");
			for(int i=0;i<cbrcode.length;i++)
			{
				cbrcode[i]=cbrcode[i]+"$";
				String[] tagval=cbrcode[i].split(",");
				cbr_codes[i]=new CBRCodes_type0();
				cbr_codes[i].setCBRCode(tagval[0]);
				cbr_codes[i].setCBRValue(tagval[1].replace("$", ""));
				
			}
			
			
			cust_cbr.setCBRCodes(cbr_codes);
			LogGEN.writeTrace("Log", "MIS CODE"+miscodes);
			String[] miscode;
			if(!miscodes.equalsIgnoreCase(""))
			{
				miscode=miscodes.split(";");
			}
			else
				miscode=new String[0];
			
			MisCodes_type0[] mis_codes=new MisCodes_type0[miscode.length];
			MisCodeDetails_type0 miscodedet=new MisCodeDetails_type0();
			for(int i=0;i<miscode.length;i++)
			{
				miscode[i]=miscode[i]+"$";
				String[] tagval=miscode[i].split(",");
				mis_codes[i]=new MisCodes_type0();
				mis_codes[i].setMisCodeNumber(tagval[1]);
				mis_codes[i].setMisCodeText(tagval[2].replace("$", ""));
				mis_codes[i].setMisCodeType(tagval[0]);
				miscodedet.addMisCodes(mis_codes[i]);
			}
			mod_cust_req.setMisCodeDetails(miscodedet);
			
			mod_cust_req.setCustomerCBRCodes(cust_cbr);
			
			int addcount=0;
			int tmpcount=0;
			if(sInputXML.contains("Corr_Addresses"))
				addcount++;
			if(sInputXML.contains("Res_Addresses"))
				addcount++;
			if(sInputXML.contains("Per_Addresses"))
				addcount++;
			if(sInputXML.contains("Emp_Addresses"))
				addcount++;
			
			CustomerAdresses_type0 address=new CustomerAdresses_type0();
			
			
			Addresses_type0[] add=new Addresses_type0[addcount];
			if(sInputXML.contains("Corr_Addresses"))
			{
				add[tmpcount]=new Addresses_type0();
				add[tmpcount].setAddressType(corAddresses.substring(corAddresses.indexOf("<addressType>")+13,corAddresses.indexOf("</addressType>")));
				if(corAddresses.contains("addressLine1"))
					add[tmpcount].setAddressLine1(corAddresses.substring(corAddresses.indexOf("<addressLine1>")+14,corAddresses.indexOf("</addressLine1>")));
				if(corAddresses.contains("addressLine2"))
					add[tmpcount].setAddressLine2(corAddresses.substring(corAddresses.indexOf("<addressLine2>")+14,corAddresses.indexOf("</addressLine2>")));
				if(corAddresses.contains("addressLine3"))
					add[tmpcount].setAddressLine3(corAddresses.substring(corAddresses.indexOf("<addressLine3>")+14,corAddresses.indexOf("</addressLine3>")));
				if(corAddresses.contains("addressCity"))
					add[tmpcount].setAddressCity(corAddresses.substring(corAddresses.indexOf("<addressCity>")+13,corAddresses.indexOf("</addressCity>")));
				if(corAddresses.contains("addressState"))
					add[tmpcount].setAddressState(corAddresses.substring(corAddresses.indexOf("<addressState>")+14,corAddresses.indexOf("</addressState>")));
				if(corAddresses.contains("addressCountry"))
					add[tmpcount].setAddressCountry(corAddresses.substring(corAddresses.indexOf("<addressCountry>")+16,corAddresses.indexOf("</addressCountry>")));
				if(corAddresses.contains("addressZip"))
					add[tmpcount].setAddressZip(corAddresses.substring(corAddresses.indexOf("<addressZip>")+12,corAddresses.indexOf("</addressZip>")));
				tmpcount++;
			}
			if(sInputXML.contains("Res_Addresses"))
			{
				add[tmpcount]=new Addresses_type0();
				add[tmpcount].setAddressType(resAddresses.substring(resAddresses.indexOf("<addressType>")+13,resAddresses.indexOf("</addressType>")));
				if(resAddresses.contains("addressLine1"))
					add[tmpcount].setAddressLine1(resAddresses.substring(resAddresses.indexOf("<addressLine1>")+14,resAddresses.indexOf("</addressLine1>")));
				if(resAddresses.contains("addressLine2"))
					add[tmpcount].setAddressLine2(resAddresses.substring(resAddresses.indexOf("<addressLine2>")+14,resAddresses.indexOf("</addressLine2>")));
				if(resAddresses.contains("addressLine3"))
					add[tmpcount].setAddressLine3(resAddresses.substring(resAddresses.indexOf("<addressLine3>")+14,resAddresses.indexOf("</addressLine3>")));
				if(resAddresses.contains("addressCity"))
					add[tmpcount].setAddressCity(resAddresses.substring(resAddresses.indexOf("<addressCity>")+13,resAddresses.indexOf("</addressCity>")));
				if(resAddresses.contains("addressState"))
					add[tmpcount].setAddressState(resAddresses.substring(resAddresses.indexOf("<addressState>")+14,resAddresses.indexOf("</addressState>")));
				if(resAddresses.contains("addressCountry"))
					add[tmpcount].setAddressCountry(resAddresses.substring(resAddresses.indexOf("<addressCountry>")+16,resAddresses.indexOf("</addressCountry>")));
				if(resAddresses.contains("addressZip"))
					add[tmpcount].setAddressZip(resAddresses.substring(corAddresses.indexOf("<addressZip>")+12,corAddresses.indexOf("</addressZip>")));
				tmpcount++;
			}
			if(sInputXML.contains("Per_Addresses"))
			{
				add[tmpcount]=new Addresses_type0();
				add[tmpcount].setAddressType(perAddresses.substring(perAddresses.indexOf("<addressType>")+13,perAddresses.indexOf("</addressType>")));
				if(perAddresses.contains("addressLine1"))
					add[tmpcount].setAddressLine1(perAddresses.substring(perAddresses.indexOf("<addressLine1>")+14,perAddresses.indexOf("</addressLine1>")));
				if(perAddresses.contains("addressLine2"))
					add[tmpcount].setAddressLine2(perAddresses.substring(perAddresses.indexOf("<addressLine2>")+14,perAddresses.indexOf("</addressLine2>")));
				if(perAddresses.contains("addressLine3"))
					add[tmpcount].setAddressLine3(perAddresses.substring(perAddresses.indexOf("<addressLine3>")+14,perAddresses.indexOf("</addressLine3>")));
				if(perAddresses.contains("addressCity"))
					add[tmpcount].setAddressCity(perAddresses.substring(perAddresses.indexOf("<addressCity>")+13,perAddresses.indexOf("</addressCity>")));
				if(perAddresses.contains("addressState"))
					add[tmpcount].setAddressState(perAddresses.substring(perAddresses.indexOf("<addressState>")+14,perAddresses.indexOf("</addressState>")));
				if(perAddresses.contains("addressCountry"))
					add[tmpcount].setAddressCountry(perAddresses.substring(perAddresses.indexOf("<addressCountry>")+16,perAddresses.indexOf("</addressCountry>")));
				if(perAddresses.contains("addressZip"))
					add[tmpcount].setAddressZip(perAddresses.substring(perAddresses.indexOf("<addressZip>")+12,perAddresses.indexOf("</addressZip>")));
				tmpcount++;
			}
			//Added by Shivanshu ATP-400
			if(sInputXML.contains("Emp_Addresses"))
			{
				add[tmpcount]=new Addresses_type0();
				add[tmpcount].setAddressType(empAddresses.substring(empAddresses.indexOf("<addressType>")+13,empAddresses.indexOf("</addressType>")));
				if(empAddresses.contains("addressLine1"))
					add[tmpcount].setAddressLine1(empAddresses.substring(empAddresses.indexOf("<addressLine1>")+14,empAddresses.indexOf("</addressLine1>")));
				if(empAddresses.contains("addressLine2"))
					add[tmpcount].setAddressLine2(empAddresses.substring(empAddresses.indexOf("<addressLine2>")+14,empAddresses.indexOf("</addressLine2>")));
				if(empAddresses.contains("addressLine3"))
					add[tmpcount].setAddressLine3(empAddresses.substring(empAddresses.indexOf("<addressLine3>")+14,empAddresses.indexOf("</addressLine3>")));
				if(empAddresses.contains("addressCity"))
					add[tmpcount].setAddressCity(empAddresses.substring(empAddresses.indexOf("<addressCity>")+13,empAddresses.indexOf("</addressCity>")));
				if(empAddresses.contains("addressState"))
					add[tmpcount].setAddressState(empAddresses.substring(empAddresses.indexOf("<addressState>")+14,empAddresses.indexOf("</addressState>")));
				if(empAddresses.contains("addressCountry"))
					add[tmpcount].setAddressCountry(empAddresses.substring(empAddresses.indexOf("<addressCountry>")+16,empAddresses.indexOf("</addressCountry>")));
				if(empAddresses.contains("addressZip"))
					add[tmpcount].setAddressZip(empAddresses.substring(empAddresses.indexOf("<addressZip>")+12,empAddresses.indexOf("</addressZip>")));
				tmpcount++;
			}
			
			address.setAddresses(add);
			mod_cust_req.setCustomerAdresses(address);
			
			//Passing Variable to New Service Shivanshu ATP - 400
			if(!websiteAddress.equalsIgnoreCase(""))
				mod_cust_req.setWebsiteAddress(websiteAddress);
			if(!sourceOfIncome.equalsIgnoreCase(""))
				mod_cust_req.setSourceOfincome(sourceOfIncome);
			if(!natureOfBusiness.equalsIgnoreCase(""))
				mod_cust_req.setNatureOfBusiness(natureOfBusiness);
			if(!employmentType.equalsIgnoreCase(""))
				mod_cust_req.setEmploymentType(employmentType);
			if(!resLandmark.equalsIgnoreCase(""))
				mod_cust_req.setResLandmark(resLandmark);
			if(!resLatitude.equalsIgnoreCase(""))
				mod_cust_req.setResLatitude(resLatitude);
			if(!resLongitude.equalsIgnoreCase(""))
				mod_cust_req.setResLongitude(resLongitude);
			if(!mailingLandmark.equalsIgnoreCase(""))
				mod_cust_req.setMailingLandmark(mailingLandmark);
			if(!mailingLatitude.equalsIgnoreCase(""))
				mod_cust_req.setMailingLatitude(mailingLatitude);
			if(!mailingLongitude.equalsIgnoreCase(""))
				mod_cust_req.setMailingLongitude(mailingLongitude);
			if(!eidaExpiryDate.equalsIgnoreCase(""))
				mod_cust_req.setEidaExpiryDate(eidaExpiryDate);
			if(!countryOfIncome.equalsIgnoreCase(""))
				mod_cust_req.setCountryOfIncome(countryOfIncome);
			if(!annualIncome.equalsIgnoreCase(""))
				mod_cust_req.setAnnualIncome(annualIncome);
			if(!annualIncomeCurrency.equalsIgnoreCase(""))
				mod_cust_req.setAnnualIncomeCurrency(annualIncomeCurrency);
			if(!dualNationalityFlag.equalsIgnoreCase(""))
				mod_cust_req.setDualNationalityFlag(dualNationalityFlag);
			if(!secondNationality.equalsIgnoreCase(""))
				mod_cust_req.setSecondNationality(secondNationality);
			if(!permLandmark.equalsIgnoreCase(""))
				mod_cust_req.setPermLandmark(permLandmark);
			if(!permLatitude.equalsIgnoreCase(""))
				mod_cust_req.setPermLatitude(permLatitude);
			if(!permLongitude.equalsIgnoreCase(""))
				mod_cust_req.setPermLongitude(permLongitude);
			if(!selfEmpCompanyName.equalsIgnoreCase(""))
				mod_cust_req.setSelfEmpCompanyName(selfEmpCompanyName);
					
			//Added by SHivanshu DCRA
			int start;
			int noOfFields;
			int end;
			
			noOfFields = xmlDataParser.getNoOfFields("custAddAccountOpen", 0, 0);
			end = 0;
			LogGEN.writeTrace("Log","Cust Acc Field noOfFields >>  " + noOfFields);
			
			CustAccountOpenPurpose_type0[] custAccOpen=new CustAccountOpenPurpose_type0[noOfFields];

			LogGEN.writeTrace("Log", "Before custAccOpen "+custAccOpen.length);
			
			for (int i = 0; i < noOfFields; ++i) {

				custAccOpen[i]=new CustAccountOpenPurpose_type0();
				start = xmlDataParser.getStartIndex("custAddAccountOpen", end, 0);
				end = xmlDataParser.getEndIndex("custAddAccountOpen", start, 0);
				
				custAccOpen[i].setAccountOpeningPurpose(xmlDataParser.getValueOf("accountOpeningPurpose", start, end));
			}
			
			
			//new tag DCRA TAG added for modification shivanshu 
			mod_cust_req.setCustAccountOpenPurpose(custAccOpen);
//			mod_cust_req.setSourceOfWealth(sourceOfWealth);
			mod_cust_req.setOtherBankUAEAccount(otherBankUAEAccount);
			mod_cust_req.setPepStatusRisk(pepStatusRisk);
			mod_cust_req.setOnboardingRisk(onboardingRisk);
			mod_cust_req.setChannelRisk(channelRisk);
			mod_cust_req.setPowerOfAttorney(powerOfAttorney);
			
			//Added by Shivanshu DCRA
			if(!poaNationality.equalsIgnoreCase(""))
				mod_cust_req.setPoaNationality(poaNationality);
			if(!poaResidency.equalsIgnoreCase(""))
				mod_cust_req.setPoaResidence(poaResidency);
			if(!primSourceofIncomeIndustry.equalsIgnoreCase(""))
				mod_cust_req.setPrimSourceofIncomeIndustry(primSourceofIncomeIndustry);
			if(!primSourceofIncomeCntry.equalsIgnoreCase(""))
				mod_cust_req.setPrimSourceofIncomeCntry(primSourceofIncomeCntry);
//			if(!addSourceIncomeIndustry.equalsIgnoreCase(""))
//				mod_cust_req.setAddSourceIncomeIndustry(addSourceIncomeIndustry);
			
			//Added by SHivanshu DCRA
			noOfFields = xmlDataParser.getNoOfFields("additionalIncome", 0, 0);
			end = 0;
			LogGEN.writeTrace("Log","additionalIncome Field noOfFields >>  " + noOfFields);
			
			AdditionalIncome_type0[] additionalInc = new AdditionalIncome_type0[noOfFields];

			LogGEN.writeTrace("Log", "Before additionalIncome "+additionalInc.length);
			
			for (int i = 0; i < noOfFields; ++i) {

				additionalInc[i]=new AdditionalIncome_type0();
				start = xmlDataParser.getStartIndex("additionalIncome", end, 0);
				end = xmlDataParser.getEndIndex("additionalIncome", start, 0);
				
				additionalInc[i].setAddSourcIncome(xmlDataParser.getValueOf("addSourcIncome", start, end));
			}
			
			//Added by SHivanshu DCRA
			noOfFields = xmlDataParser.getNoOfFields("additionalIncomeCountry", 0, 0);
			end = 0;
			LogGEN.writeTrace("Log","additionalIncomeCountry Field noOfFields >>  " + noOfFields);
			
			AdditionalIncomeCountry_type0[] additionalIncCntry = new AdditionalIncomeCountry_type0[noOfFields];

			LogGEN.writeTrace("Log", "Before additionalIncCntry "+additionalIncCntry.length);
			
			for (int i = 0; i < noOfFields; ++i) {

				additionalIncCntry[i]=new AdditionalIncomeCountry_type0();
				start = xmlDataParser.getStartIndex("additionalIncomeCountry", end, 0);
				end = xmlDataParser.getEndIndex("additionalIncomeCountry", start, 0);
				
				additionalIncCntry[i].setAddsourceIncomeCountry(xmlDataParser.getValueOf("addsourceIncomeCountry", start, end));
			}
			
			//Added by SHivanshu DCRA
			noOfFields = xmlDataParser.getNoOfFields("customerSource", 0, 0);
			end = 0;
			LogGEN.writeTrace("Log","customerSource Field noOfFields >>  " + noOfFields);
			
			CustomerSource_type0[] customerSource = new CustomerSource_type0[noOfFields];

			LogGEN.writeTrace("Log", "Before customerSource "+customerSource.length);
			
			for (int i = 0; i < noOfFields; ++i) {

				customerSource[i]=new CustomerSource_type0();
				start = xmlDataParser.getStartIndex("customerSource", end, 0);
				end = xmlDataParser.getEndIndex("customerSource", start, 0);
				
				customerSource[i].setSourceOfWealth(xmlDataParser.getValueOf("sourceOfWealth", start, end));
			}
			
			//Added by SHivanshu DCRA
			noOfFields = xmlDataParser.getNoOfFields("custSourceIndustry", 0, 0);
			end = 0;
			LogGEN.writeTrace("Log","additionalIncomeCountry Field noOfFields >>  " + noOfFields);
			
			CustSourceIndustry_type0[] custSourceInd = new CustSourceIndustry_type0[noOfFields];

			LogGEN.writeTrace("Log", "Before custSourceInd "+custSourceInd.length);
			
			for (int i = 0; i < noOfFields; ++i) {

				custSourceInd[i]=new CustSourceIndustry_type0();
				start = xmlDataParser.getStartIndex("custSourceIndustry", end, 0);
				end = xmlDataParser.getEndIndex("custSourceIndustry", start, 0);
				
				custSourceInd[i].setAddSourceIncomeIndustry(xmlDataParser.getValueOf("addSourceIncomeIndustry", start, end));
			}
			
			mod_cust_req.setAdditionalIncome(additionalInc);
			mod_cust_req.setAdditionalIncomeCountry(additionalIncCntry);
			mod_cust_req.setCustomerSource(customerSource);
			mod_cust_req.setCustSourceIndustry(custSourceInd);
			
			mod_cust_req_msg.setModCustomerInfoReq(mod_cust_req);
			
			CustomerAdditionalDetails_type0 cust_add_details=new CustomerAdditionalDetails_type0();
			
		
			if(!CustDesignation.equalsIgnoreCase(""))
				cust_add_details.setCustDesignation(CustDesignation);
			if(!CustEmployerName.equalsIgnoreCase(""))
			{
				cust_add_details.setCustEmployerName(CustEmployerName.replaceAll("&","and"));
			}
			if(!CustEmployerMobile.equalsIgnoreCase(""))
				cust_add_details.setCustEmployerMobile(CustEmployerMobile);
			if(!CustEmployerFax.equalsIgnoreCase(""))
				cust_add_details.setCustEmployerFax(CustEmployerFax);
			if(!CustMonthlyIncome.equalsIgnoreCase(""))
				cust_add_details.setCustMonthlyIncome(CustMonthlyIncome);
			if(!CustMotherMaidenName.equalsIgnoreCase(""))
				cust_add_details.setCustMotherMaidenName(CustMotherMaidenName);
			if(!CustOccupation.equalsIgnoreCase(""))
				cust_add_details.setCustOccupation(CustOccupation);
			if(!CustOccupationCategory.equalsIgnoreCase(""))
				cust_add_details.setCustOccupationCategory(CustOccupationCategory);
			if(!CustPassportExpiryDate.equalsIgnoreCase(""))
				cust_add_details.setCustPassportExpiryDate(CustPassportExpiryDate);
			if(!CustPassportIssueDate.equalsIgnoreCase(""))
				cust_add_details.setCustPassportIssueDate(CustPassportIssueDate);
			if(!CustVisaExpiryDate.equalsIgnoreCase(""))
				cust_add_details.setCustVisaExpiryDate(CustVisaExpiryDate);
			if(!CustVisaIssueDate.equalsIgnoreCase(""))
				cust_add_details.setCustVisaIssueDate(CustVisaIssueDate);
			if(!CustVisaNumber.equalsIgnoreCase(""))
				cust_add_details.setCustVisaNumber(CustVisaNumber);
			if(!CustVisaType.equalsIgnoreCase(""))
				cust_add_details.setCustVisaType(CustVisaType);
			if(!passport.equalsIgnoreCase(""))
				cust_add_details.setCustPassportNumber(passport);
			if(!custcurrentjobstartdt.equalsIgnoreCase(""))
				cust_add_details.setCustCurrentJobStartDate(custcurrentjobstartdt);
			if(!custcurrentjobsalary.equalsIgnoreCase(""))
				cust_add_details.setCustCurrentJobSalaryAmount(custcurrentjobsalary);
			if(!CustEmployerPhone.equalsIgnoreCase(""))
				cust_add_details.setCustEmployerPhone(CustEmployerPhone);
			
			LogGEN.writeTrace("Log","Modify customer modified");
			
			mod_cust_req.setCustomerAdditionalDetails(cust_add_details);
			System.out.println("Ashish Here---"+mod_cust_stub.getinputXML(mod_cust_req_msg));
			xmlInput=mod_cust_stub.getinputXML(mod_cust_req_msg);
			LogGEN.writeTrace("Log", "xmlInput:" + this.xmlInput);
			
			mod_cust_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			ModCustomerInfoResMsg mod_cust_res_msg= mod_cust_stub.modCustomerInfo_Oper(mod_cust_req_msg);
			sOrg_put=mod_cust_stub.resMod_Cust_msg;//Added By Harish For inserting original mssg
			Header_Input=mod_cust_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			LogGEN.writeTrace("Log", "sErrorDesc:" + sErrorDesc + "sReturnCode:" + sReturnCode + "sErrorDetail:" + sErrorDetail);
			System.out.println(sErrorDetail);
			ModCustomerInfoRes_type0 add_cust_res=new ModCustomerInfoRes_type0();
			add_cust_res=mod_cust_res_msg.getModCustomerInfoRes();
			System.out.println(add_cust_res.getReason());
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>ADCB_ADDCUST</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<AddCustRes>"+
						"<CustomerID>"+add_cust_res.getCustomerId()+"</CustomerID>"+
						"<Reason>"+add_cust_res.getReason()+"</Reason>"+
						"<Status>"+add_cust_res.getStatus()+"</Status>"+
					"</AddCustRes>"+
					"</Output>";
			}
			else
			{
		    	LogGEN.writeTrace("Log", "Failed");
		    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETCUSTINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Reason>"+add_cust_res.getReason()+"</Reason><Status>"+add_cust_res.getStatus()+"</Status><td>Unable to modify customer.</td></Output>";
			}
			
			
			
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify customer.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify customer.</td></Output>";
			}
			
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			
			
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
					sHandler.readCabProperty("JTS");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=xmlInput;
					LogGEN.writeTrace("Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					LogGEN.writeTrace("Log", "WINAME:"+winame);
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type1=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					
					 try
					 {
						 dbpass=AESEncryption.decrypt(dbpass);
					 }
					 catch(Exception e)
					 {
						 
					 }
					  /**
					  * //Added By Harish For inserting original mssg
					  * Date			:	17 Mar 2015
					  * Description 	:	Replace execute with executeClob
					  */
					 DBConnection con=new DBConnection();
					String Query="insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
					LogGEN.writeTrace("Log","Modify Cudtomer  Query : finally :"+Query);
					LogGEN.writeTrace("Log","sOrg_put : finally :"+sOrg_put);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
				}
			
			
			
			return sOutput;
		}
	}
}

