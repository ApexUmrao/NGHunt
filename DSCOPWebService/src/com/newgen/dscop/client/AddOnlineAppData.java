package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddOnlineAppDataStub;
import com.newgen.dscop.stub.AddOnlineAppDataStub.AccountDetails_type1;
import com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineLendingApplicationDtlsReqMsg;
import com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineLendingApplicationDtlsReq_type0;
import com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineLendingApplicationDtlsResMsg;
import com.newgen.dscop.stub.AddOnlineAppDataStub.AddOnlineLendingApplicationDtlsRes_type0;
import com.newgen.dscop.stub.AddOnlineAppDataStub.ApplicationData_type0;
import com.newgen.dscop.stub.AddOnlineAppDataStub.HeaderType;
import com.newgen.dscop.stub.AddOnlineAppDataStub.ProductData_type0;
import com.newgen.dscop.stub.AddOnlineAppDataStub.Products_type0;

public class AddOnlineAppData extends DSCOPServiceHandler
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
	public String addOnlineAppDataExec(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddOnlineAppData");
		LogGEN.writeTrace("CBG_Log", "AddOnlineAppData sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddOnlineAppData");
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddOnlineAppData TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			String RMCode=xmlDataParser.getValueOf("RMCode");
			String TypeOfCustomer=xmlDataParser.getValueOf("TypeOfCustomer");
			String GrossIncome=xmlDataParser.getValueOf("GrossIncome");
			String MonthlyBalInAc=xmlDataParser.getValueOf("MonthlyBalInAc");
			String CompanyName=xmlDataParser.getValueOf("CompanyName");
			String ResidenceAddr1=xmlDataParser.getValueOf("ResidenceAddr1");
			String ResidenceAddr2=xmlDataParser.getValueOf("ResidenceAddr2");
			String ResidenceAddr3=xmlDataParser.getValueOf("ResidenceAddr3");
			String ResidenceCity=xmlDataParser.getValueOf("ResidenceCity");
			String ResidenceState=xmlDataParser.getValueOf("ResidenceState");
			String OfficeVillaNumber=xmlDataParser.getValueOf("OfficeVillaNumber");
			String OfficeBuildingName=xmlDataParser.getValueOf("OfficeBuildingName");
			String OfficeStreet=xmlDataParser.getValueOf("OfficeStreet");
			String OfficeCity=xmlDataParser.getValueOf("OfficeCity");
			String OfficeEmirate=xmlDataParser.getValueOf("OfficeEmirate");
			String HomeAddress1=xmlDataParser.getValueOf("HomeAddress1");
			String HomeAddress2=xmlDataParser.getValueOf("HomeAddress2");
			String HomeAddress3=xmlDataParser.getValueOf("HomeAddress3");
			String HomeCountry=xmlDataParser.getValueOf("HomeCountry");
			String HomePoBox=xmlDataParser.getValueOf("HomePoBox");
			String PoBox=xmlDataParser.getValueOf("PoBox");
			String Emirate=xmlDataParser.getValueOf("Emirate");
			String Country=xmlDataParser.getValueOf("Country");
			String HomePhone=xmlDataParser.getValueOf("HomePhone");
			String HomeCity=xmlDataParser.getValueOf("HomeCity");
			String HomeEmirate=xmlDataParser.getValueOf("HomeEmirate");
			String OfficePoBox=xmlDataParser.getValueOf("OfficePoBox");
			String OfficePhoneNumber=xmlDataParser.getValueOf("OfficePhoneNumber");
			String CustomerTitle=xmlDataParser.getValueOf("CustomerTitle");
			String CustomerName=xmlDataParser.getValueOf("CustomerName");
			String MobileNo=xmlDataParser.getValueOf("MobileNo");
			String CustomerId=xmlDataParser.getValueOf("CustomerId");
			String EmailId=xmlDataParser.getValueOf("EmailId");
			String accountType=xmlDataParser.getValueOf("accountType");

			AddOnlineAppDataStub add_online_stub=new AddOnlineAppDataStub(sWSDLPath);
			AddOnlineLendingApplicationDtlsReq_type0 add_online_req=new AddOnlineLendingApplicationDtlsReq_type0();
			AddOnlineLendingApplicationDtlsReqMsg add_online_req_msg=new AddOnlineLendingApplicationDtlsReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddOnlineAppData");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("LAPS");
			Header_Input.setCredentials(loggedinuser);
			add_online_req_msg.setHeader(Header_Input);

			ApplicationData_type0 appData = new ApplicationData_type0();
			appData.setTypeOfCustomer(TypeOfCustomer);
			appData.setGrossIncome(GrossIncome);
			appData.setMonthlyBalInAc(MonthlyBalInAc);
			appData.setCompanyName(CompanyName);
			appData.setResidenceAddr1(ResidenceAddr1);
			appData.setResidenceAddr2(ResidenceAddr2);
			appData.setResidenceAddr3(ResidenceAddr3);
			appData.setResidenceCity(ResidenceCity);
			appData.setResidenceState(ResidenceState);
			appData.setOfficeVillaNumber(OfficeVillaNumber);
			appData.setOfficeBuildingName(OfficeBuildingName);
			appData.setOfficeStreet(OfficeStreet);
			appData.setOfficeCity(OfficeCity);
			appData.setOfficeEmirate(OfficeEmirate);
			appData.setHomeAddress1(HomeAddress1);
			appData.setHomeAddress2(HomeAddress2);
			appData.setHomeAddress3(HomeAddress3);
			appData.setHomeCountry(HomeCountry);
			appData.setHomePoBox(HomePoBox);
			appData.setPoBox(PoBox);
			appData.setEmirate(Emirate);
			appData.setCountry(Country);
			appData.setHomePhone(HomePhone);
			appData.setHomeCity(HomeCity);
			appData.setHomeEmirate(HomeEmirate);
			appData.setOfficePoBox(OfficePoBox);
			appData.setOfficePhoneNumber(OfficePhoneNumber);
			appData.setCustomerTitle(CustomerTitle);
			appData.setCustomerName(CustomerName);
			appData.setMobileNo(MobileNo);
			appData.setCustomerId(CustomerId);
			appData.setEmailId(EmailId);

			ProductData_type0 prodData = new ProductData_type0();



			XMLParser xmlDataParser2 = new XMLParser();
			xmlDataParser2.setInputXML(sInputXML);
			int noOfFields = xmlDataParser2.getNoOfFields("ProductCode");
			LogGEN.writeTrace("CBG_Log", "ProductCode count :"+noOfFields);
			String winame=xmlDataParser.getValueOf("WiName");
			for (int noofTag = 0; noofTag < noOfFields; ++noofTag) {
				Products_type0 products = new Products_type0();
				String productCode, loanAmount, tenor, downPayment;
				if(noofTag==0){
					productCode=xmlDataParser2.getFirstValueOf("ProductCode");
					loanAmount=xmlDataParser2.getFirstValueOf("loanAmount");
					tenor=xmlDataParser2.getFirstValueOf("tenor");
					downPayment=xmlDataParser2.getFirstValueOf("downPayment");
				}
				else{
					productCode=xmlDataParser2.getNextValueOf("ProductCode");
					loanAmount=xmlDataParser2.getNextValueOf("loanAmount");
					tenor=xmlDataParser2.getNextValueOf("tenor");
					downPayment=xmlDataParser2.getNextValueOf("downPayment");
				}
				LogGEN.writeTrace("CBG_Log", "ProductCode :"+productCode);
				LogGEN.writeTrace("CBG_Log", "loanAmount :"+loanAmount);
				LogGEN.writeTrace("CBG_Log", "tenor :"+tenor);
				LogGEN.writeTrace("CBG_Log", "downPayment :"+downPayment);
				products.setProductCode(productCode);
				products.setLoanAmount(loanAmount);
				products.setTenor(tenor);
				products.setDownPayment(downPayment);
				products.setApplicationRefNumber(winame);
				prodData.addProducts(products);
				
				
			}

			AccountDetails_type1 acctDetails = new AccountDetails_type1();
			acctDetails.setAccountType(accountType);
			prodData.setAccountDetails(acctDetails);
			
			add_online_req.setRMCode(RMCode);
			add_online_req.setApplicationData(appData);
			add_online_req.setProductData(prodData);

			add_online_req_msg.setAddOnlineLendingApplicationDtlsReq(add_online_req);
			System.out.println("AddOnlineAppData input xml "+add_online_stub.getInputXml(add_online_req_msg));
			xmlInput=add_online_stub.getInputXml(add_online_req_msg);
			System.out.println("AddOnlineAppData xmlInput xml  "+xmlInput);
			add_online_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			AddOnlineLendingApplicationDtlsResMsg add_online_res_msg= add_online_stub.addOnlineLendingApplicationDtls_Oper(add_online_req_msg);
			sOrg_put=add_online_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddOnlineLendingApplicationDtlsResMsg sOrg_put: "+sOrg_put);
			Header_Input=add_online_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			//			System.out.println("AddOnlineAppData output xml  "+sOrg_put);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				LogGEN.writeTrace("CBG_Log", "AddOnlineAppData Successful Result");
				AddOnlineLendingApplicationDtlsRes_type0 res = add_online_res_msg.getAddOnlineLendingApplicationDtlsRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddOnlineAppData</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<addOnlineLendingApplicationDtlsRes>"+
						"<appId>"+res.getProductResDtls().getProductApps()[0].getAppId()+"</appId>"+
						"</addOnlineLendingApplicationDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddOnlineAppData</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to add online app data.</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add online app data.</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to add online app data.</Output>";
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
				LogGEN.writeTrace("CBG_Log","ModifyCudtomer  Exception: finally :"+e2.getStackTrace());
			}
			return sOutput;			
		}
	}
}

