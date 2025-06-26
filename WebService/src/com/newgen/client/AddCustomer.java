package com.newgen.client;

import com.newgen.AESEncryption;
import com.newgen.stub.AddCustCreationStub.AddCustCreationReqMsg;
import com.newgen.stub.AddCustCreationStub.AddCustCreationReq_type0;
import com.newgen.stub.AddCustCreationStub.AddCustCreationResMsg;
import com.newgen.stub.AddCustCreationStub.AddCustCreationRes_type0;
import com.newgen.stub.AddCustCreationStub.CustAddAccountOpen_type1;
import com.newgen.stub.AddCustCreationStub.HeaderType;
import com.newgen.stub.AddCustCreationStub;

import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.newgen.stub.*;

public class AddCustomer extends WebServiceHandler {

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Output_Add_Cust=null;

	@SuppressWarnings("finally")
	
	public String AddCustomerToFCR(String sInputXML) {
		
		LogGEN.writeTrace("Log", "Fuction called AddCustomer");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String custFullName= xmlDataParser.getValueOf("custFullName");
		String custShortName= xmlDataParser.getValueOf("custShortName");
		String custPrefix= xmlDataParser.getValueOf("custPrefix");
		String custGender= xmlDataParser.getValueOf("custGender");
		String custReligion= xmlDataParser.getValueOf("custReligion");
		String custMaritalStatus= xmlDataParser.getValueOf("custMaritalStatus");
		String custEmirate= xmlDataParser.getValueOf("custEmirate");
		String custState= xmlDataParser.getValueOf("custState");
		String custNationality= xmlDataParser.getValueOf("custNationality");
		String custResidence= xmlDataParser.getValueOf("custResidence");
		String custNationalId= xmlDataParser.getValueOf("custNationalId");
		String custEmployerName= xmlDataParser.getValueOf("custEmployerName");
		String custPosition= xmlDataParser.getValueOf("custPosition");
		String custEmployerEmirate= xmlDataParser.getValueOf("custEmployerEmirate");
		String custEmployerPhone= xmlDataParser.getValueOf("custEmployerPhone");
		String custMonthlySalary= xmlDataParser.getValueOf("custMonthlySalary");
		String txtCustAddr1= xmlDataParser.getValueOf("txtCustAddr1");
		String txtCustAddr2= xmlDataParser.getValueOf("txtCustAddr2");
		String txtCustAddr3= xmlDataParser.getValueOf("txtCustAddr3");
		String txtCustDOB= xmlDataParser.getValueOf("txtCustDOB");
		String custEmail= xmlDataParser.getValueOf("custEmail");
		String txtCustPhone= xmlDataParser.getValueOf("txtCustPhone");
		String custMobile= xmlDataParser.getValueOf("custMobile");
		String ICType= xmlDataParser.getValueOf("ICType");
		String flgCustType= xmlDataParser.getValueOf("flgCustType");
		String flgCustClass= xmlDataParser.getValueOf("flgCustClass");
		String flgStaff= xmlDataParser.getValueOf("flgStaff");
		String homeBranch= xmlDataParser.getValueOf("homeBranch");
		
		String signType= xmlDataParser.getValueOf("signType");
		String professCategory= xmlDataParser.getValueOf("professCategory");
		String custCountry= xmlDataParser.getValueOf("custCountry");
		String zip= xmlDataParser.getValueOf("zip");
		String flgMinor= xmlDataParser.getValueOf("flgMinor");
		String codCustType= xmlDataParser.getValueOf("codCustType");
		String misCod1= xmlDataParser.getValueOf("misCod1");
		String misCod2= xmlDataParser.getValueOf("misCod2");
		String misCod3= xmlDataParser.getValueOf("misCod3");
		String misCod4= xmlDataParser.getValueOf("misCod4");
		String makerId= xmlDataParser.getValueOf("makerId");
		String checkerId= xmlDataParser.getValueOf("checkerId");
		String citizenshipId= xmlDataParser.getValueOf("citizenshipId");
		String codEmpId= xmlDataParser.getValueOf("codEmpId");
		String noOfDependants= xmlDataParser.getValueOf("noOfDependants");
		String permAddr1= xmlDataParser.getValueOf("permAddr1");
		String permAddr2= xmlDataParser.getValueOf("permAddr2");
		String permAddr3= xmlDataParser.getValueOf("permAddr3");
		String permAddrCity= xmlDataParser.getValueOf("permAddrCity");
		String permAddrCountry= xmlDataParser.getValueOf("permAddrCountry");
		String permAddrState= xmlDataParser.getValueOf("permAddrState");
		String permAddrZip= xmlDataParser.getValueOf("permAddrZip");
		String oldRefNo=xmlDataParser.getValueOf("OLDREF_NO"); //change for old ref no
		String txtGaurdian=xmlDataParser.getValueOf("custGuardian");

		/// Need to Parse A new Tag Value Shivanshu ATP - 377
		String websiteAddress = xmlDataParser.getValueOf("websiteAddress");
		String sourceOfIncome = xmlDataParser.getValueOf("sourceOfIncome");
		String natureOfBusiness = xmlDataParser.getValueOf("natureOfBusiness");
		String nameOfGuardian = xmlDataParser.getValueOf("nameOfGuardian");
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
		// New field ADD For DCRA WBG//
		String sourceOfWealth = xmlDataParser.getValueOf("sourceOfWealth");
		String otherBankUAEAccount = xmlDataParser.getValueOf("otherBankUAEAccount");
		String pepStatusRisk = xmlDataParser.getValueOf("pepStatusRisk");
		String onboardingRisk = xmlDataParser.getValueOf("onboardingRisk");
		String channelRisk = xmlDataParser.getValueOf("channelRisk");
		String powerOfAttorney = xmlDataParser.getValueOf("powerOfAttorney");
		
		int end = 0;
		int start = xmlDataParser.getStartIndex("Customer", 0, 0);
		int deadend = xmlDataParser.getEndIndex("Customer", start, 0);
		int custAddAccountOpenCount = xmlDataParser.getNoOfFields("custAddAccountOpen", start, deadend);

		WebServiceHandler sHandler= new WebServiceHandler();
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		String Status="";
		try {
			sHandler.readCabProperty("ADD_Customer");
			
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
			
			AddCustCreationStub add_cust_stub=new AddCustCreationStub(sWSDLPath);
			AddCustCreationReq_type0 add_cust_req=new AddCustCreationReq_type0();
			AddCustCreationReqMsg add_cust_req_msg=new AddCustCreationReqMsg();
			ArrayList<AddCustCreationStub.CustAddAccountOpen_type1> addAccountOpenList = new ArrayList<>();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddCustCreation");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			//Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			
			add_cust_req_msg.setHeader(Header_Input);
			add_cust_req.setCheckerId(checkerId);
			add_cust_req.setCitizenshipId(citizenshipId);
			add_cust_req.setCodCustType(codCustType);
			add_cust_req.setCodEmpId(codEmpId);
			add_cust_req.setCustCountry(custCountry);
			add_cust_req.setCustEmail(custEmail);
			add_cust_req.setCustEmirate(custEmirate);
			add_cust_req.setCustEmployerEmirate(custEmployerEmirate);
			add_cust_req.setCustEmployerName(custEmployerName);
			add_cust_req.setCustEmployerPhone(custEmployerPhone);
			add_cust_req.setCustFullName(custFullName);
			add_cust_req.setCustGender(custGender);			
			add_cust_req.setCustMaritalStatus(custMaritalStatus);
			add_cust_req.setCustMobile(custMobile);
			add_cust_req.setCustMonthlySalary(custMonthlySalary);
			add_cust_req.setCustNationalId(custNationalId);
			add_cust_req.setCustNationality(custNationality);
			add_cust_req.setCustResidence(custResidence);
			add_cust_req.setCustPosition(custPosition);
			add_cust_req.setCustPrefix(custPrefix);
			add_cust_req.setCustReligion(custReligion);
			
			add_cust_req.setCustState(custState);
			add_cust_req.setFlgCustType(flgCustType);
			add_cust_req.setFlgCustClass(flgCustClass);
			add_cust_req.setFlgMinor(flgMinor);
			add_cust_req.setFlgStaff(flgStaff);
			add_cust_req.setHomeBranch(homeBranch);
			add_cust_req.setICType(ICType);
			add_cust_req.setMakerId(makerId);
			add_cust_req.setMisCod1(misCod1);
			add_cust_req.setMisCod2(misCod2);
			add_cust_req.setMisCod3(misCod3);
			add_cust_req.setMisCod4(misCod4);
			add_cust_req.setNoOfDependants(noOfDependants);
			add_cust_req.setPermAddr1(permAddr1);
			add_cust_req.setPermAddr2(permAddr2);
			add_cust_req.setPermAddr3(permAddr3);
			add_cust_req.setPermAddrCity(permAddrCity);
			add_cust_req.setPermAddrCountry(permAddrCountry);
			add_cust_req.setPermAddrState(permAddrState);
			add_cust_req.setPermAddrZip(permAddrZip);
			add_cust_req.setProfessCategory(professCategory);
			add_cust_req.setSignType(signType);
			add_cust_req.setTxtCustAddr1(txtCustAddr1);
			add_cust_req.setTxtCustAddr2(txtCustAddr2);
			add_cust_req.setTxtCustAddr3(txtCustAddr3);
			add_cust_req.setTxtCustDOB(txtCustDOB);
			add_cust_req.setTxtCustPhone(txtCustPhone);
			add_cust_req.setZip(zip);
			add_cust_req.setCustShortName(custShortName);
			//new field for dcra SHIVANSHU
			add_cust_req.setSourceOfWealth(sourceOfWealth)	;
			add_cust_req.setOtherBankUAEAccount(otherBankUAEAccount);
			add_cust_req.setPepStatusRisk(pepStatusRisk);
			add_cust_req.setOnboardingRisk(onboardingRisk);
			add_cust_req.setChannelRisk(channelRisk);
			add_cust_req.setPowerOfAttorney(powerOfAttorney);
			//
			add_cust_req.setOrigRefNumber(oldRefNo); //change for old ref no
			if(!txtGaurdian.equalsIgnoreCase(""))
				add_cust_req.setCustGuardian(txtGaurdian);
			
			//Passing Variable to New Service Shivanshu ATP - 377
			if(!websiteAddress.equalsIgnoreCase(""))
				add_cust_req.setWebsiteAddress(websiteAddress);
			if(!sourceOfIncome.equalsIgnoreCase(""))
				add_cust_req.setSourceOfIncome(sourceOfIncome);
			if(!natureOfBusiness.equalsIgnoreCase(""))
				add_cust_req.setNatureOfBusiness(natureOfBusiness);
			if(!employmentType.equalsIgnoreCase(""))
				add_cust_req.setEmploymentType(employmentType);
			if(!resLandmark.equalsIgnoreCase(""))
				add_cust_req.setResLandmark(resLandmark);
			if(!resLatitude.equalsIgnoreCase(""))
				add_cust_req.setResLatitude(resLatitude);
			if(!resLongitude.equalsIgnoreCase(""))
				add_cust_req.setResLongitude(resLongitude);
			if(!mailingLandmark.equalsIgnoreCase(""))
				add_cust_req.setMailingLandmark(mailingLandmark);
			if(!mailingLatitude.equalsIgnoreCase(""))
				add_cust_req.setMailingLatitude(mailingLatitude);
			if(!mailingLongitude.equalsIgnoreCase(""))
				add_cust_req.setMailingLongitude(mailingLongitude);
			if(!eidaExpiryDate.equalsIgnoreCase(""))
				add_cust_req.setEidaExpiryDate(eidaExpiryDate); 
			if(!countryOfIncome.equalsIgnoreCase(""))
				add_cust_req.setCountryOfIncome(countryOfIncome);
			if(!annualIncome.equalsIgnoreCase(""))
				add_cust_req.setAnnualIncome(annualIncome);
			if(!annualIncomeCurrency.equalsIgnoreCase(""))
				add_cust_req.setAnnualIncomeCurrency(annualIncomeCurrency);
			if(!dualNationalityFlag.equalsIgnoreCase(""))
				add_cust_req.setDualNationalityFlag(dualNationalityFlag);
			if(!secondNationality.equalsIgnoreCase(""))
				add_cust_req.setSecondNationality(secondNationality);
			if(!permLandmark.equalsIgnoreCase(""))
				add_cust_req.setPermLandmark(permLandmark);
			if(!permLatitude.equalsIgnoreCase(""))
				add_cust_req.setPermLatitude(permLatitude);
			if(!permLongitude.equalsIgnoreCase(""))
				add_cust_req.setPermLongitude(permLongitude);
			if(!selfEmpCompanyName.equalsIgnoreCase(""))
				add_cust_req.setSelfEmpCompanyName(selfEmpCompanyName);

			add_cust_req_msg.setAddCustCreationReq(add_cust_req);
			
			if (custAddAccountOpenCount > 0) {
				 LogGEN.writeTrace("WS-2_0_Log", "custAddAccountOpenCount : " + custAddAccountOpenCount);
			    for (int i = 0; i < custAddAccountOpenCount; i++) {
			    	
			        String custAddAccountOpen = xmlDataParser.getNextValueOf("custAddAccountOpen");
			        XMLParser xp1 = new XMLParser(custAddAccountOpen);
			        String accountOpeningPurpose = xp1.getValueOf("accountOpeningPurpose");

			        LogGEN.writeTrace("WS-2_0_Log", "AddCustomer accountOpeningPurpose: " + accountOpeningPurpose);

			        if (accountOpeningPurpose != null && !accountOpeningPurpose.isEmpty() && !"".equalsIgnoreCase(accountOpeningPurpose)) {
			            AddCustCreationStub.CustAddAccountOpen_type1 accountOpen = new AddCustCreationStub.CustAddAccountOpen_type1();
			            accountOpen.setAccountOpeningPurpose(accountOpeningPurpose);
			            addAccountOpenList.add(accountOpen); 
			        }
			    }

			    if (!addAccountOpenList.isEmpty()) {
			        AddCustCreationStub.CustAddAccountOpen_type1[] finalArray = addAccountOpenList.toArray(new AddCustCreationStub.CustAddAccountOpen_type1[0]);
			        add_cust_req.setCustAddAccountOpen(finalArray);
			    }
			}
			
		
			///insert into log table
						
			System.out.println(add_cust_stub.getInputXML(add_cust_req_msg));
			add_cust_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=add_cust_stub.getInputXML(add_cust_req_msg);
			AddCustCreationResMsg add_cust_res_msg= add_cust_stub.addCustCreation_Oper(add_cust_req_msg);
			sOrg_Output_Add_Cust = add_cust_stub.outputxml;// .getInputXML(add_cust_req_msg);//add_cust_stub.resAddCust;//Added
															// BY Harish for original msg
			Header_Input=add_cust_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			
			///insert into log table	
			
			 System.out.println("Error Description---"+Header_Input.getErrorDescription());
			    System.out.println("Error Description---"+Header_Input.getErrorDetail());
			if (!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")) {
				AddCustCreationRes_type0 add_cust_res=new AddCustCreationRes_type0();
				add_cust_res=add_cust_res_msg.getAddCustCreationRes();
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>" + "<Option>ADCB_ADDCUST</Option>"
						+ "<returnCode>" + sReturnCode + "</returnCode>" + "<errorDescription>" + sErrorDetail
						+ "</errorDescription>" + "<AddCustRes>" + "<CustomerID>" + add_cust_res.getCustomerId()
						+ "</CustomerID>" + "<CustomerFullName>" + add_cust_res.getCustFullName()
						+ "</CustomerFullName>" +
						
						"</AddCustRes>" + "</Output>";
			} else {
			    	LogGEN.writeTrace("Log", "Failed");
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Customer</Option><Output><returnCode>"
						+ sReturnCode + "</returnCode><errorDescription>" + sErrorDetail
						+ "</errorDescription><td>Unable to add customer.</td></Output>";
				}
			
		} catch (Exception e) {
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			Status="Failure";
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Customer</Option><Output><returnCode>"
					+ sReturnCode + "</returnCode><errorDescription>" + sErrorDetail
					+ "</errorDescription><td>Unable to add customer.</td></Output>";
			e.printStackTrace();
		} finally {
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if (sOutput.trim().length() < 1) {
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"
						+ sReturnCode + "</returnCode><errorDescription>" + sErrorDetail
						+ "</errorDescription><td>Unable to add customer.</td></Output>";
			}
			
			 try {
				sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			if (sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
				Status="Success";
			} else
				Status="Failure";
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				String inputXml=xmlInput;
				LogGEN.writeTrace("Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("Calltype");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				LogGEN.writeTrace("Log", "11111111111111111111%%%%%%%%%%%%");
				// String outputxml=sOutput;
				
			/*
			 * String
			 * Query="insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"
			 * +winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "
			 * + "and randomnumber='"+ sessionID +"'),'"+call_type+"','"+
			 * inputXml.replaceAll("'", "''")
			 * +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+ "'"+
			 * outputxml.replaceAll("'", "''") +"',sysdate,'"+ Status + "')";
			 */
				 
			try {
					 dbpass=AESEncryption.decrypt(dbpass);
			} catch (Exception e) {
					 
				 }
					
					DBConnection con=new DBConnection();
					  /**
			 * //Added By Harish For inserting original mssg Date : 17 Mar 2015 Description
			 * : Replace execute with executeClob
					  */
			String Query = "insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"
					+ winame + "',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "
					+ "and randomnumber='" + sessionID + "'),'" + call_type + "',?,to_date('" + sDate
					+ "','dd/mm/yyyy hh24:mi:ss')," + "?,sysdate,'" + Status + "')";
					LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
					LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output_Add_Cust);
				 try {
				con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"),
						sOrg_Output_Add_Cust.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				 
			 //End here
			return sOutput;			
		}
	}
}
