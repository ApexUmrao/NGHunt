package com.newgen.dscop.client;

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
import com.newgen.dscop.stub.AddCustCreationStub;
import com.newgen.dscop.stub.AddCustCreationStub.AddCustCreationReqMsg;
import com.newgen.dscop.stub.AddCustCreationStub.AddCustCreationReq_type0;
import com.newgen.dscop.stub.AddCustCreationStub.AddCustCreationResMsg;
import com.newgen.dscop.stub.AddCustCreationStub.AddCustCreationRes_type0;
import com.newgen.dscop.stub.AddCustCreationStub.HeaderType;

public class AddCustomer extends DSCOPServiceHandler
{
	static String sWSDLPath="";
	static String sCabinet="";
	static String sUser="";
	static String sPassword="";
	static boolean sLoginReq=false;
	static long lTimeOut = 30000;
	String xmlInput="";
	String sOrgResMsg=null;
	static String dburl="";
	static String dbuser="";
	static String dbpass="";
	DSCOPServiceHandler sHandler;
	
	@SuppressWarnings("finally")
	public String addCustomer(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddCustomer");
//		LogGEN.writeTrace("CBG_Log", "addCustomer sInputXML---"+sInputXML);
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
		String custNatId= xmlDataParser.getValueOf("custNatId");
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
		String flgCustClass= xmlDataParser.getValueOf("flgCustClass");
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
		//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
		String dualNationalityFlag = xmlDataParser.getValueOf("dualNationalityFlag");
		String secondNationality = xmlDataParser.getValueOf("secondNationality");
		String countryOfIncome = xmlDataParser.getValueOf("countryOfIncome");
		String annualIncome = xmlDataParser.getValueOf("annualIncome");
		String eidaExpiryDate = xmlDataParser.getValueOf("eidaExpiryDate");
		//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
		
		sHandler= new DSCOPServiceHandler();

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		String Status="";
		try
		{
			loadWSDLDtls(sHandler,"ADD_CUSTOMER_WBG");

			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);

			AddCustCreationStub addCustStub=new AddCustCreationStub(sWSDLPath);
			AddCustCreationReq_type0 addCustReq=new AddCustCreationReq_type0();
			AddCustCreationReqMsg addCustReqMsg=new AddCustCreationReqMsg();

			addCustReqMsg.setHeader(setHeaderDtls(sDate, ref_no,xmlDataParser.getValueOf("SENDERID")));
			addCustReq.setCheckerId(checkerId);
			addCustReq.setCitizenshipId(citizenshipId);
			addCustReq.setCodCustType(codCustType);
			addCustReq.setCodEmpId(codEmpId);
			addCustReq.setCustCountry(custCountry);
			addCustReq.setCustEmail(custEmail);
			addCustReq.setCustEmirate(custEmirate);
			addCustReq.setCustEmployerEmirate(custEmployerEmirate);
			addCustReq.setCustEmployerName(custEmployerName);
			addCustReq.setCustEmployerPhone(custEmployerPhone);
			addCustReq.setCustFullName(custFullName);
			addCustReq.setCustGender(custGender);			
			addCustReq.setCustMaritalStatus(custMaritalStatus);
			addCustReq.setCustMobile(custMobile);
			addCustReq.setCustMonthlySalary(custMonthlySalary);
			addCustReq.setCustNationalId(custNationalId);
			addCustReq.setCustNatlId(custNatId);
			addCustReq.setCustNationality(custNationality);
			addCustReq.setCustResidence(custResidence);
			addCustReq.setCustPosition(custPosition);
			addCustReq.setCustPrefix(custPrefix);
			addCustReq.setCustReligion(custReligion);
			addCustReq.setCustState(custState);
			addCustReq.setFlgCustType(flgCustType);
			addCustReq.setFlgCustClass(flgCustClass);
			addCustReq.setFlgMinor(flgMinor);
			addCustReq.setFlgStaff(flgStaff);
			addCustReq.setHomeBranch(homeBranch);
			addCustReq.setICType(ICType);
			addCustReq.setMakerId(makerId);
			addCustReq.setMisCod1(misCod1);
			addCustReq.setMisCod2(misCod2);
			addCustReq.setMisCod3(misCod3);
			addCustReq.setMisCod4(misCod4);
			addCustReq.setNoOfDependants(noOfDependants);
			addCustReq.setPermAddr1(permAddr1);
			addCustReq.setPermAddr2(permAddr2);
			addCustReq.setPermAddr3(permAddr3);
			addCustReq.setPermAddrCity(permAddrCity);
			addCustReq.setPermAddrCountry(permAddrCountry);
			addCustReq.setPermAddrState(permAddrState);
			addCustReq.setPermAddrZip(permAddrZip);
			addCustReq.setProfessCategory(professCategory);
			addCustReq.setSignType(signType);
			addCustReq.setTxtCustAddr1(txtCustAddr1);
			addCustReq.setTxtCustAddr2(txtCustAddr2);
			addCustReq.setTxtCustAddr3(txtCustAddr3);
			addCustReq.setTxtCustDOB(txtCustDOB);
			addCustReq.setTxtCustPhone(txtCustPhone);
			addCustReq.setZip(zip);
			addCustReq.setCustShortName(custShortName);
			addCustReq.setOrigRefNumber(oldRefNo);
			//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
			if(!dualNationalityFlag.equalsIgnoreCase("")){ //Added by Shivanshu DSCOP CHANGES
			    addCustReq.setDualNationalityFlag(dualNationalityFlag);
			}
			addCustReq.setSecondNationality(secondNationality);
			addCustReq.setCountryOfIncome(countryOfIncome);
			addCustReq.setAnnualIncome(annualIncome);
			addCustReq.setEidaExpiryDate(eidaExpiryDate);
			//MOKSH | 14032024 | NEW TAGS FOR LatestKYCReviewDate
			addCustReqMsg.setAddCustCreationReq(addCustReq);
			
			addCustStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=addCustStub.getInputXML(addCustReqMsg);
//			LogGEN.writeTrace("CBG_Log", "input for ADD_Customer " + xmlInput);
			AddCustCreationResMsg add_cust_res_msg= addCustStub.addCustCreation_Oper(addCustReqMsg);
			sOrgResMsg=addCustStub.outputxml;
//			LogGEN.writeTrace("CBG_Log", "sOrgResMsg for ADD_Customer " + sOrgResMsg);
			HeaderType headerInput=add_cust_res_msg.getHeader();
			sReturnCode=  headerInput.getReturnCode();
			sErrorDetail=headerInput.getErrorDetail();
			sErrorDesc = headerInput.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				AddCustCreationRes_type0 add_cust_res=new AddCustCreationRes_type0();
				add_cust_res=add_cust_res_msg.getAddCustCreationRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>ADD_Customer</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<AddCustRes>"+
				"<CustomerID>"+add_cust_res.getCustomerId()+"</CustomerID>"+
				"<CustomerFullName>"+add_cust_res.getCustFullName()+"</CustomerFullName>"+
				"<CustomerIC>"+add_cust_res.getCustNatlId()+"</CustomerIC>"+	
				"</AddCustRes>"+	
				"</Output>";
			}else{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Customer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add customer.</td></Output>";
			}	
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log",sw.toString());
			
			Status="Failure";
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Customer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add customer.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Customer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add customer.</td></Output>";
			}
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";

			try {
				loadJSTDtls(sHandler,"JTS");
				String inputXml=xmlInput;				
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("DSCOPCallType");
				sCabinet=xmlDataParser.getValueOf("EngineName");
				dbpass=AESEncryption.decrypt(dbpass);

				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log","ADD CASA Account Query : finally :"+Query);
//				LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrgResMsg);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgResMsg.replaceAll("'", "''"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sOutput;			
		}
	}
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderid){
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("AddCustCreation");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Addition");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(sHandler.setSenderId(senderid));
		headerType.setReqTimeStamp(sDate);
		headerType.setCredentials(loggedinuser);
		return headerType;
	}

	private static void loadWSDLDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get(tagName);
			LogGEN.writeTrace("CBG_Log", "Inq_cbg_customer_onboarding WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", tagName+" WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", tagName+" CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", tagName+" USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", tagName+" PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", tagName+" LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", tagName+" TIME_OUT: "+lTimeOut);

		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	private static void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
