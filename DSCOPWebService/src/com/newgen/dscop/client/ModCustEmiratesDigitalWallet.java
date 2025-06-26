package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.HeaderType;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletDtlsReqMsg;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletDtlsReq_type0;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.ModifyCustEmiratesDigitalWalletDtlsResMsg;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.RegisterCustEmiratesDigitalWalletReqMsg;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.RegisterCustEmiratesDigitalWalletReq_type0;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.RegisterCustEmiratesDigitalWalletResMsg;
import com.newgen.dscop.stub.ModCustEmiratesDigitalWalletStub.RegisterCustEmiratesDigitalWalletRes_type0;

public class ModCustEmiratesDigitalWallet extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put=null;
	DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

	public String ModCustEmiratesDigitalWalletCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called ModCustEmiratesDigitalWallet");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModCustEmiratesDigitalWallet");
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet TIME_OUT: "+lTimeOut);

			String operation = xmlDataParser.getValueOf("Operation");
			//Modify operation
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId=xmlDataParser.getValueOf("customerId");
			String operationType=xmlDataParser.getValueOf("operationType");
			String registeredMobileNo=xmlDataParser.getValueOf("registeredMobileNo");
			String registeredEmailId=xmlDataParser.getValueOf("registeredEmailId");
			String psuedoPAN=xmlDataParser.getValueOf("psuedoPAN");
			String EIDANumber=xmlDataParser.getValueOf("EIDANumber");
			String termsAndConditionAcceptance=xmlDataParser.getValueOf("termsAndConditionAcceptance");
			String termsAndConditionVersion=xmlDataParser.getValueOf("termsAndConditionVersion");
			String walletCreationDate=xmlDataParser.getValueOf("walletCreationDate");
			//Register operation
			String userType = xmlDataParser.getValueOf("userType");
			String identification = xmlDataParser.getValueOf("identification");
			String identificationType = xmlDataParser.getValueOf("identificationType");
			String programCode = xmlDataParser.getValueOf("programCode");
			String bankCode = xmlDataParser.getValueOf("bankCode");
			String registrationType = xmlDataParser.getValueOf("registrationType");
			String title = xmlDataParser.getValueOf("title");
			String firstName = xmlDataParser.getValueOf("firstName");
			String lastName = xmlDataParser.getValueOf("lastName");
			String dateOfBirth = xmlDataParser.getValueOf("dateOfBirth");
			String gender = xmlDataParser.getValueOf("gender");
			String emailAddress = xmlDataParser.getValueOf("emailAddress");
			String addressLine1 = xmlDataParser.getValueOf("addressLine1");
			String addressLine2 = xmlDataParser.getValueOf("addressLine2");
			String addressLine3 = xmlDataParser.getValueOf("addressLine3");
			String countryCode = xmlDataParser.getValueOf("countryCode");
			String cityCode = xmlDataParser.getValueOf("cityCode");
			String zipCode = xmlDataParser.getValueOf("zipCode");
			String state = xmlDataParser.getValueOf("state");
			String legalID1 = xmlDataParser.getValueOf("legalID1");
			String legalID1Type = xmlDataParser.getValueOf("legalID1Type");
			String legalID1ExpiryDate = xmlDataParser.getValueOf("legalID1ExpiryDate");
			String kycStatus = xmlDataParser.getValueOf("kycStatus");
			String isDefaultWallet = xmlDataParser.getValueOf("isDefaultWallet");
			String bankUniqueIdentity = xmlDataParser.getValueOf("bankUniqueIdentity");
			String locale = xmlDataParser.getValueOf("locale");
			String acceptedTnCVersion = xmlDataParser.getValueOf("acceptedTnCVersion");
			String requestDateTime = xmlDataParser.getValueOf("requestDateTime");
			String requestId = xmlDataParser.getValueOf("requestId");
			String rrNumber = xmlDataParser.getValueOf("rrNumber");
			String remarks = xmlDataParser.getValueOf("remarks");


			ModCustEmiratesDigitalWalletStub stub = new ModCustEmiratesDigitalWalletStub(sWSDLPath);
			ModifyCustEmiratesDigitalWalletDtlsReqMsg modifyReqMsg = new ModifyCustEmiratesDigitalWalletDtlsReqMsg();
			ModifyCustEmiratesDigitalWalletDtlsReq_type0 modifyReqMsg_type_0 = new ModifyCustEmiratesDigitalWalletDtlsReq_type0();
			RegisterCustEmiratesDigitalWalletReqMsg registerReqMsg = new RegisterCustEmiratesDigitalWalletReqMsg();
			RegisterCustEmiratesDigitalWalletReq_type0  registerReq_MsgType0 = new RegisterCustEmiratesDigitalWalletReq_type0();

			if(operation.equalsIgnoreCase("Modify"))
			{
				HeaderType Header_Input = new HeaderType();
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("modCustEmiratesDigitalWallet");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Modify");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername("123456");
				Header_Input.setCredentials("123456");
				modifyReqMsg.setHeader(Header_Input);

				modifyReqMsg_type_0.setOperationType(operationType);
				modifyReqMsg_type_0.setCustomerId(customerId);
				modifyReqMsg_type_0.setRegisteredMobileNo(registeredMobileNo);
				modifyReqMsg_type_0.setRegisteredEmailId(registeredEmailId);
				modifyReqMsg_type_0.setPsuedoPAN(psuedoPAN);
				modifyReqMsg_type_0.setEIDANumber(EIDANumber);
				modifyReqMsg_type_0.setTermsAndConditionAcceptance(termsAndConditionAcceptance);
				modifyReqMsg_type_0.setTermsAndConditionVersion(termsAndConditionVersion);
				modifyReqMsg_type_0.setWalletCreationDate(walletCreationDate);
				modifyReqMsg.setModifyCustEmiratesDigitalWalletDtlsReq(modifyReqMsg_type_0);
				xmlInput=stub.getInputXML(modifyReqMsg);
				LogGEN.writeTrace("CBG_Log", "modifyCustEmiratesDigitalWalletDtls xmlInput xml  "+xmlInput);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);

				ModifyCustEmiratesDigitalWalletDtlsResMsg modifyResMsg = stub.modifyCustEmiratesDigitalWalletDtls_Oper(modifyReqMsg);
				sOrg_put=stub.outputXML;
				LogGEN.writeTrace("CBG_Log", "modifyCustEmiratesDigitalWalletDtls sOrg_put: "+sOrg_put);
				
				Header_Input= modifyResMsg.getHeader();
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>ModCustEmiratesDigitalWallet</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"</Output>";
				}
				else
				{
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustEmiratesDigitalWallet</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>ModCustEmiratesDigitalWallet Failed</td></Output>";
				}
			}
			else if(operation.equalsIgnoreCase("Register"))
			{
				HeaderType Header_Input = new HeaderType();
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("modCustEmiratesDigitalWallet");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Modify");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername("123456");
				Header_Input.setCredentials("123456");
				registerReqMsg.setHeader(Header_Input);
				registerReq_MsgType0.setUserType(userType);
				registerReq_MsgType0.setIdentification(identification);
				registerReq_MsgType0.setIdentificationType(identificationType);
				registerReq_MsgType0.setProgramCode(programCode);
				registerReq_MsgType0.setBankCode(bankCode);
				registerReq_MsgType0.setRegistrationType(registrationType);
				registerReq_MsgType0.setTitle(title);
				registerReq_MsgType0.setFirstName(firstName);
				registerReq_MsgType0.setLastName(lastName);
				registerReq_MsgType0.setDateOfBirth(dateOfBirth);
				registerReq_MsgType0.setGender(gender);
				registerReq_MsgType0.setEmailAddress(emailAddress);
				registerReq_MsgType0.setAddressLine1(addressLine1);
				registerReq_MsgType0.setAddressLine2(addressLine2);
				registerReq_MsgType0.setAddressLine3(addressLine3);
				registerReq_MsgType0.setCountryCode(countryCode);
				registerReq_MsgType0.setCityCode(cityCode);
				registerReq_MsgType0.setZipCode(zipCode);
				registerReq_MsgType0.setState(state);
				registerReq_MsgType0.setLegalID1(legalID1);
				registerReq_MsgType0.setLegalID1Type(legalID1Type);
				registerReq_MsgType0.setLegalID1ExpiryDate(legalID1ExpiryDate);
				registerReq_MsgType0.setKycStatus(kycStatus);
				registerReq_MsgType0.setIsDefaultWallet(isDefaultWallet);
				registerReq_MsgType0.setBankUniqueIdentity(bankUniqueIdentity);
				registerReq_MsgType0.setLocale(locale);
				registerReq_MsgType0.setAcceptedTnCVersion(acceptedTnCVersion);
				registerReq_MsgType0.setRequestDateTime(requestDateTime);
				registerReq_MsgType0.setRequestId(requestId);
				registerReq_MsgType0.setRrNumber(rrNumber);
				registerReq_MsgType0.setRemarks(remarks);
				registerReqMsg.setRegisterCustEmiratesDigitalWalletReq(registerReq_MsgType0);
				System.out.println("registerCustEmiratesDigitalWallet input xml "+stub.getInputXMLRegister(registerReqMsg));
				xmlInput=stub.getInputXMLRegister(registerReqMsg);
				LogGEN.writeTrace("CBG_Log","registerCustEmiratesDigitalWallet xmlInput xml  "+xmlInput);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				stub.registerCustEmiratesDigitalWallet_Oper(registerReqMsg);
				sOrg_put=stub.outputXML;
				LogGEN.writeTrace("CBG_Log", "registerCustEmiratesDigitalWallet sOrg_put: "+sOrg_put);

				RegisterCustEmiratesDigitalWalletResMsg registerResMsg = new RegisterCustEmiratesDigitalWalletResMsg();
				RegisterCustEmiratesDigitalWalletRes_type0 registerResMsg_type0 = registerResMsg.getRegisterCustEmiratesDigitalWalletRes();
				Header_Input= registerResMsg.getHeader();

				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();

				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>ModCustEmiratesDigitalWallet</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"<pseudoPAN>"+registerResMsg_type0.getPseudoPAN()+"</pseudoPAN>"+
							"</Output>";
				}
				else
				{
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustEmiratesDigitalWallet</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>ModCustEmiratesDigitalWallet Failed</td></Output>";
				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustEmiratesDigitalWallet</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>ModCustEmiratesDigitalWallet Failed</Output>";
			e.printStackTrace();
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustEmiratesDigitalWallet</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch kiosk list</td></Output>";
			}		
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");

				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}
}
