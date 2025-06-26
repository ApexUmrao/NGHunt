package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.TestXML;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCustomerInfoStub;
import com.newgen.dscop.stub.ModCustomerInfoStub.Addresses_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.CBRCodes_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.CustomerAdditionalDetails_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.CustomerAdresses_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.CustomerCBRCodes_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.HeaderType;
import com.newgen.dscop.stub.ModCustomerInfoStub.MisCodeDetails_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.MisCodes_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.ModCustomerInfoReqMsg;
import com.newgen.dscop.stub.ModCustomerInfoStub.ModCustomerInfoReq_type0;
import com.newgen.dscop.stub.ModCustomerInfoStub.ModCustomerInfoResMsg;
import com.newgen.dscop.stub.ModCustomerInfoStub.ModCustomerInfoRes_type0;

public class ModifyCustomer extends DSCOPServiceHandler
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
	public String ModifyCustomerToFCR(String sInputXML) 
	{
	
		LogGEN.writeTrace("CBG_Log", "Fuction called Modify_Customer");
		LogGEN.writeTrace("CBG_Log", "ModifyCustomer sInputXML---");
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
		
		if(sInputXML.contains("Corr_Addresses"))
			corAddresses=sInputXML.substring(sInputXML.indexOf("<Corr_Addresses>")+16,sInputXML.indexOf("</Corr_Addresses>"));
		if(sInputXML.contains("Res_Addresses"))
			resAddresses=sInputXML.substring(sInputXML.indexOf("<Res_Addresses>")+15,sInputXML.indexOf("</Res_Addresses>"));
		if(sInputXML.contains("Per_Addresses"))
			perAddresses=sInputXML.substring(sInputXML.indexOf("<Per_Addresses>")+15,sInputXML.indexOf("</Per_Addresses>"));
		
		String cbr="";
		
		String miscodes="";
		
		//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
		String dualNationalityFlag = xmlDataParser.getValueOf("dualNationalityFlag");
		String secondNationality = xmlDataParser.getValueOf("secondNationality");
		String countryOfIncome = xmlDataParser.getValueOf("countryOfIncome");
		String annualIncome = xmlDataParser.getValueOf("annualIncome");
		String eidaExpiryDate = xmlDataParser.getValueOf("eidaExpiryDate");
		//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
		
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
			LogGEN.writeTrace("CBG_Log", cbr);
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
		
		
		
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("MODIFY_CUSTOMER");
			
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("MODIFY_CUSTOMER");
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "MODIFY_CUSTOMER TIME_OUT: "+lTimeOut);

			
			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
			
			ModCustomerInfoStub mod_cust_stub=new ModCustomerInfoStub(sWSDLPath);
			ModCustomerInfoReq_type0 mod_cust_req=new ModCustomerInfoReq_type0();
			ModCustomerInfoReqMsg mod_cust_req_msg=new ModCustomerInfoReqMsg();
			
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
			LogGEN.writeTrace("CBG_Log", "Before CBR");
			for(int i=0;i<cbrcode.length;i++)
			{
				cbrcode[i]=cbrcode[i]+"$";
				String[] tagval=cbrcode[i].split(",");				
				cbr_codes[i]=new CBRCodes_type0();
				cbr_codes[i].setCBRCode(tagval[0]);
				cbr_codes[i].setCBRValue(tagval[1].replace("$", ""));
				
			}
			
			
			cust_cbr.setCBRCodes(cbr_codes);
			LogGEN.writeTrace("CBG_Log", "MIS CODE"+miscodes);
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
					add[tmpcount].setAddressZip(resAddresses.substring(resAddresses.indexOf("<addressZip>")+12,resAddresses.indexOf("</addressZip>")));
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
			
			address.setAddresses(add);
			mod_cust_req.setCustomerAdresses(address);			
			
			//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
			if(!dualNationalityFlag.equalsIgnoreCase("")){ //Added by Shivanshu DSCOP CHANGES
				mod_cust_req.setDualNationalityFlag(dualNationalityFlag);
			}
			mod_cust_req.setSecondNationality(secondNationality);
			mod_cust_req.setCountryOfIncome(countryOfIncome);
			mod_cust_req.setAnnualIncome(annualIncome);
			mod_cust_req.setEidaExpiryDate(eidaExpiryDate);
			//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
			
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
			
			LogGEN.writeTrace("CBG_Log","Modify customer modified");
			
			mod_cust_req.setCustomerAdditionalDetails(cust_add_details);
			System.out.println("modifyCustomer input xml "+mod_cust_stub.getinputXML(mod_cust_req_msg));
			xmlInput=mod_cust_stub.getinputXML(mod_cust_req_msg);
			System.out.println("modifyCustomer xmlInput xml  "+xmlInput);
			mod_cust_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			ModCustomerInfoResMsg mod_cust_res_msg= mod_cust_stub.modCustomerInfo_Oper(mod_cust_req_msg);
			sOrg_put=mod_cust_stub.resMod_Cust_msg;
			Header_Input=mod_cust_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
//			System.out.println("modifyCustomer output xml  "+sOrg_put);
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
		    	LogGEN.writeTrace("CBG_Log", "Failed");
		    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETCUSTINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Reason>"+add_cust_res.getReason()+"</Reason><Status>"+add_cust_res.getStatus()+"</Status><td>Unable to modify customer.</td></Output>";
			}
			
			
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify customer.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify customer.</td></Output>";
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
					  /**
					  * //Added By Harish For inserting original mssg
					  * Date			:	17 Mar 2015
					  * Description 	:	Replace execute with executeClob
					  */
					 DBConnection con=new DBConnection();
					String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
					LogGEN.writeTrace("CBG_Log","Modify Cudtomer  Query : finally :"+Query);
					LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
				}
			return sOutput;			
		}
	}
}

