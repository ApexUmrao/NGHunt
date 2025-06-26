package com.newgen.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.AxisFault;




import com.newgen.AESEncryption;
import com.newgen.stub.AddCustCreationStub;
import com.newgen.stub.AddCustCreationStub.AddCustCreationReqMsg;
import com.newgen.stub.AddCustCreationStub.AddCustCreationReq_type0;
import com.newgen.stub.AddCustCreationStub.AddCustCreationResMsg;
import com.newgen.stub.AddCustCreationStub.AddCustCreationRes_type0;
import com.newgen.stub.AddCustCreationStub.HeaderType;

public class CreateCustomerID_COB  extends WebServiceHandler
{
	public String sWSDLPath = "";
	public static String sCabinet = "";
	public String sUser = "";
	public String sPassword = "";
	public boolean sLoginReq = false;	
	String errorDetail = null;
	String errorDesc = null;
	String output = null;
	WebServiceHandler webHandler = null;
	String invocation = null;
	String returnCode = null;
	String outputxml = null;
	AddCustCreationStub stub = null;
	
	public String createCustomerIDInFCR_COB(String sInputXML) throws IOException 
	{	

		LogGEN.writeTrace("COB_Log", "Inside createCustomerIDInFCR_COB() for CreatCustomerID, INPUTXML : " + sInputXML);
		webHandler = new WebServiceHandler();		
		try
		{	
			LogGEN.writeTrace("COB_Log","ReadingCabProperty : COB_INTEGRATION_INVOCATION");
			
			webHandler.readCabProperty("COB_INTEGRATION_INVOCATION");
			
			invocation = (String)currentCabPropertyMap.get("INVOCATION");
			
			LogGEN.writeTrace("COB_Log","INVOCATION FLAG : " + invocation);
			
			if(invocation.equalsIgnoreCase("offshore"))
			{
				LogGEN.writeTrace("COB_Log","INTEGRATION CALL DATA READING FROM FILE...");			
				outputxml = readFromFile(sInputXML);
			}
			else if(invocation.equalsIgnoreCase("onshore"))
			{
				LogGEN.writeTrace("COB_Log","INTEGRATION CALL DATA CALLING WEB SERVICE...");			
				outputxml = communicateToWebService(sInputXML);
			}			
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for section: COB_INTEGRATION_INVOCATION");		
			e.printStackTrace();
		}
		return outputxml;
	}


	public String communicateToWebService(String sInputXML )
	{
		LogGEN.writeTrace("COB_Log", "Inside communicateToWebService()");
		LogGEN.writeTrace("COB_Log","ReadingCabProperty : CreateCustomerID_COB call");

		try
		{
			webHandler.readCabProperty("CreateCustomerID_COB");
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for call :CreateCustomerID_COB.");		
			e.printStackTrace();
		}	

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		LogGEN.writeTrace("COB_Log", "sDate :  " + sDate);
	
		sWSDLPath = (String)currentCabPropertyMap.get("WSDL_PATH");
		sCabinet = (String)currentCabPropertyMap.get("CABINET");
		sUser = (String)currentCabPropertyMap.get("USER");
		sLoginReq = Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
		sPassword = (String)currentCabPropertyMap.get("PASSWORD");


		LogGEN.writeTrace("COB_Log", "WSDL PATH : " + sWSDLPath);
		LogGEN.writeTrace("COB_Log", "CABINET : " + sCabinet);
		LogGEN.writeTrace("COB_Log", "USER : " + sUser);
		LogGEN.writeTrace("COB_Log", "PASSWORD : " + sLoginReq);
		LogGEN.writeTrace("COB_Log", "LOGIN_REQ : " + sPassword);
		LogGEN.writeTrace("COB_Log", "*******NISHANT log******* (01-03-2016)");

		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		LogGEN.writeTrace("COB_Log", "WorkitemNo : " + xmlDataParser.getValueOf("WIName"));

		
		String custFullName = xmlDataParser.getValueOf("custFullName");
		LogGEN.writeTrace("COB_Log", "custFullName : " + custFullName);
		String custPrefix = xmlDataParser.getValueOf("custPrefix");
		LogGEN.writeTrace("COB_Log", "custPrefix : " + custPrefix);
		String custGender = xmlDataParser.getValueOf("custGender");
		LogGEN.writeTrace("COB_Log", "custGender : " + custGender);
		String custReligion = xmlDataParser.getValueOf("custReligion");
		LogGEN.writeTrace("COB_Log", "custReligion : " + custReligion);
		String custMaritalStatus = xmlDataParser.getValueOf("custMaritalStatus");
		LogGEN.writeTrace("COB_Log", "custMaritalStatus : " + custMaritalStatus);
		String custEmirate= xmlDataParser.getValueOf("custEmirate");
		LogGEN.writeTrace("COB_Log", "custEmirate.. : " + custEmirate);
		String custState= xmlDataParser.getValueOf("custState");
		LogGEN.writeTrace("COB_Log", "custState.. : " + custState);
		String custNationality= xmlDataParser.getValueOf("custNationality");
		LogGEN.writeTrace("COB_Log", "custNationality : " + custNationality);
		String custResidence= xmlDataParser.getValueOf("custResidence");
		LogGEN.writeTrace("COB_Log", "custResidence : " + custResidence);
		String custNationalId= xmlDataParser.getValueOf("custNationalId");
		LogGEN.writeTrace("COB_Log", "custNationalId : " + custNationalId);
		String custEmployerName= xmlDataParser.getValueOf("custEmployerName");
		LogGEN.writeTrace("COB_Log", "custEmployerName : " + custEmployerName);
		String custPosition= xmlDataParser.getValueOf("custPosition");
		LogGEN.writeTrace("COB_Log", "custPosition : " + custPosition);
		String custEmployerEmirate= xmlDataParser.getValueOf("custEmployerEmirate");
		LogGEN.writeTrace("COB_Log", "custEmployerEmirate : " + custEmployerEmirate);
		String custEmployerPhone= xmlDataParser.getValueOf("custEmployerPhone");
		LogGEN.writeTrace("COB_Log", "custEmployerPhone : " + custEmployerPhone);
		String custMonthlySalary= xmlDataParser.getValueOf("custMonthlySalary");
		LogGEN.writeTrace("COB_Log", "custMonthlySalary : " + custMonthlySalary);
		String txtCustAddr1= xmlDataParser.getValueOf("txtCustAddr1");
		LogGEN.writeTrace("COB_Log", "txtCustAddr1 : " + txtCustAddr1);
		String txtCustAddr2= xmlDataParser.getValueOf("txtCustAddr2");
		LogGEN.writeTrace("COB_Log", "txtCustAddr2 : " + txtCustAddr2);
		String txtCustAddr3= xmlDataParser.getValueOf("txtCustAddr3");
		LogGEN.writeTrace("COB_Log", "txtCustAddr3 : " + txtCustAddr3);
		String txtCustDOB= xmlDataParser.getValueOf("txtCustDOB");
		LogGEN.writeTrace("COB_Log", "txtCustDOB : " + txtCustDOB);
		String custEmail= xmlDataParser.getValueOf("custEmail");
		LogGEN.writeTrace("COB_Log", "custEmail : " + custEmail);
		String txtCustPhone= xmlDataParser.getValueOf("txtCustPhone");
		LogGEN.writeTrace("COB_Log", "txtCustPhone : " + txtCustPhone);
		String custMobile= xmlDataParser.getValueOf("custMobile");
		LogGEN.writeTrace("COB_Log", "custMobile : " + custMobile);
		String ICType= xmlDataParser.getValueOf("ICType");
		LogGEN.writeTrace("COB_Log", "ICType : " + ICType);
		String flgCustType= xmlDataParser.getValueOf("flgCustType");
		LogGEN.writeTrace("COB_Log", "flgCustType : " + flgCustType);
		String flgStaff= xmlDataParser.getValueOf("flgStaff");
		LogGEN.writeTrace("COB_Log", "flgStaff : " + flgStaff);
		String homeBranch= xmlDataParser.getValueOf("homeBranch");
		LogGEN.writeTrace("COB_Log", "homeBranch : " + homeBranch);
		String signType= xmlDataParser.getValueOf("signType");
		LogGEN.writeTrace("COB_Log", "signType : " + signType);
		String professCategory= xmlDataParser.getValueOf("professCategory");
		LogGEN.writeTrace("COB_Log", "professCategory : " + professCategory);
		String custCountry= xmlDataParser.getValueOf("custCountry");
		LogGEN.writeTrace("COB_Log", "custCountry : " + custCountry);
		String zip= xmlDataParser.getValueOf("zip");
		LogGEN.writeTrace("COB_Log", "zip : " + zip);
		String flgMinor= xmlDataParser.getValueOf("flgMinor");
		LogGEN.writeTrace("COB_Log", "flgMinor : " + flgMinor);
		String codCustType= xmlDataParser.getValueOf("codCustType");
		LogGEN.writeTrace("COB_Log", "codCustType : " + codCustType);
		String flgCustClass= xmlDataParser.getValueOf("flgCustClass");
		LogGEN.writeTrace("COB_Log", "codCustType : " + flgCustClass);
		String misCod1= xmlDataParser.getValueOf("misCod1");
		LogGEN.writeTrace("COB_Log", "misCod1 : " + misCod1);
		String misCod2= xmlDataParser.getValueOf("misCod2");
		LogGEN.writeTrace("COB_Log", "misCod2 : " + misCod2);
		String misCod3= xmlDataParser.getValueOf("misCod3");
		LogGEN.writeTrace("COB_Log", "misCod3 : " + misCod3);
		String misCod4= xmlDataParser.getValueOf("misCod4");
		LogGEN.writeTrace("COB_Log", "misCod4 : " + misCod4);
		String makerId= xmlDataParser.getValueOf("makerId");
		LogGEN.writeTrace("COB_Log", "makerId : " + makerId);
		String checkerId= xmlDataParser.getValueOf("checkerId");
		LogGEN.writeTrace("COB_Log", "checkerId : " + checkerId);
		String citizenshipId= xmlDataParser.getValueOf("citizenshipId");
		LogGEN.writeTrace("COB_Log", "citizenshipId : " + citizenshipId);
		String codEmpId= xmlDataParser.getValueOf("codEmpId");
		LogGEN.writeTrace("COB_Log", "codEmpId : " + codEmpId);
		String noOfDependants= xmlDataParser.getValueOf("noOfDependants");
		LogGEN.writeTrace("COB_Log", "noOfDependants : " + noOfDependants);
		String permAddr1= xmlDataParser.getValueOf("permAddr1");
		LogGEN.writeTrace("COB_Log", "permAddr1 : " + permAddr1);
		String permAddr2= xmlDataParser.getValueOf("permAddr2");
		LogGEN.writeTrace("COB_Log", "permAddr2 : " + permAddr2);
		String permAddr3= xmlDataParser.getValueOf("permAddr3");
		LogGEN.writeTrace("COB_Log", "permAddr3 : " + permAddr3);
		String permAddrCity= xmlDataParser.getValueOf("permAddrCity");
		LogGEN.writeTrace("COB_Log", "permAddrCity : " + permAddrCity);
		String permAddrCountry= xmlDataParser.getValueOf("permAddrCountry");
		LogGEN.writeTrace("COB_Log", "permAddrCountry : " + permAddrCountry);
		String permAddrState= xmlDataParser.getValueOf("permAddrState");
		LogGEN.writeTrace("COB_Log", "permAddrState : " + permAddrState);
		String permAddrZip= xmlDataParser.getValueOf("permAddrZip");
		LogGEN.writeTrace("COB_Log", "permAddrZip : " + permAddrZip);
		String custNatId= xmlDataParser.getValueOf("custNatId");
		LogGEN.writeTrace("COB_Log", "custNatId : " + custNatId);
		String custShortName= xmlDataParser.getValueOf("custShortName"); // Added on 14-July-2014 @ Offshore
		LogGEN.writeTrace("COB_Log", "custShortName : " + custShortName);
		String custClass= xmlDataParser.getValueOf("custClass"); // Added on 14-July-2014 @ Offshore
		LogGEN.writeTrace("COB_Log", "custShortName : " + custShortName);
/*		String oldRefNo = xmlDataParser.getValueOf("OLDREF_NO");
		LogGEN.writeTrace("COB_Log", "oldRefNo : " + oldRefNo);//change for old ref no (Nishant Parmar, 23-02-2016)
*/				
		//Added by Nishant Parmar (23-02-2016), re:(01-03-2016) 
		//BEGIN ---
		try {
			 webHandler.readCabProperty("JTS");
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		String dburl=(String)currentCabPropertyMap.get("DBURL");
		String dbuser=(String)currentCabPropertyMap.get("USER");
		String dbpass=(String)currentCabPropertyMap.get("PASS");
		

		String inputXml = sInputXML;
		LogGEN.writeTrace("COB_Log", "inputXml:::::"+inputXml);
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String winame = xmlParser.getValueOf("WIName");
		//String sessionID= xmlParser.getValueOf("SessionId");
		String call_type = xmlParser.getValueOf("Calltype");
		//String call_order=xmlParser.getValueOf("Callorder");
		//String Status=xmlParser.getValueOf("Status");
		
		//select on the basis of winame callname
		 try
		 {
			 dbpass=AESEncryption.decrypt(dbpass);
		 }
		 catch(Exception e)
		 {
			 
		 }
		DBConnection conn = new DBConnection();
		String sSelQuery = "select REFNUMBER from usr_0_integration_old_ref_cob where WI_NAME = '"+winame+"' and CALL_NAME = '"+call_type+"'";
		LogGEN.writeTrace("COB_Log","sSelQuery ::::" + sSelQuery);
		String oldRefNumber = "";
		LogGEN.writeTrace("COB_Log", "*******NISHANT SETTING OLDREF******* (01-03-2016)");
		 try {
			 oldRefNumber = conn.executeSelect("jdbc:oracle:thin:@" + dburl,dbuser,dbpass,sSelQuery);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
	
	LogGEN.writeTrace("COB_Log","Oldrefnumber ::::" + oldRefNumber);		
		try
		{
			stub = new AddCustCreationStub(sWSDLPath);
		}
		catch (AxisFault e)
		{
			LogGEN.writeTrace("COB_Log", "Exception while creating stub object");
			e.printStackTrace();
		}

		AddCustCreationReq_type0 requestType = new AddCustCreationReq_type0();
		AddCustCreationReqMsg requestMessage = new AddCustCreationReqMsg();
		
		HeaderType headerType = new HeaderType();
		HeaderType headerTypeRes = new HeaderType();
		//************************************************************		

		headerType.setUsecaseID("1234");
		headerType.setServiceName("AddCustCreation");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Addition");
		headerType.setCorrelationID("");	
		String newRefNumber = getUniqueNo(); //Nishant Parmar (01-03-2016)
		LogGEN.writeTrace("COB_Log","oldRefNumber=newRefNumber: " + oldRefNumber+' '+newRefNumber);
		if (oldRefNumber.equalsIgnoreCase("")){
			
			oldRefNumber=newRefNumber;
			LogGEN.writeTrace("COB_Log","oldRefNumber=newRefNumber: " + oldRefNumber+' '+newRefNumber);
		}
		headerType.setSysRefNumber(newRefNumber);
		headerType.setSenderID(webHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setRepTimeStamp(sDate);	
		headerType.setUsername("WMS");
		headerType.setCredentials("WMS");		
		headerType.setReturnCode("");
		headerType.setErrorDescription("");
		headerType.setErrorDetail("");

		// Setting HeaderType to RequestMessage
		requestMessage.setHeader(headerType);

		requestType.setCheckerId(checkerId);
		requestType.setCitizenshipId(citizenshipId);
		requestType.setCodCustType(codCustType);
		requestType.setCodEmpId(codEmpId);
		requestType.setCustCountry(custCountry);
		requestType.setCustEmail(custEmail);
		requestType.setCustEmirate(custState);// interchange with custState //02-June-2014 ; Ajay Dhiman
		requestType.setCustEmployerEmirate(custEmployerEmirate);
		requestType.setCustEmployerName(custEmployerName);
		requestType.setCustEmployerPhone(custEmployerPhone);
		requestType.setCustFullName(custFullName);
		requestType.setCustGender(custGender);			
		requestType.setCustMaritalStatus(custMaritalStatus);
		requestType.setCustMobile(custMobile);
		requestType.setCustMonthlySalary(custMonthlySalary);
		requestType.setCustNationalId(custNationalId);
		requestType.setCustNationality(custNationality);
		requestType.setCustResidence(custResidence);
		requestType.setCustPosition(custPosition);
		requestType.setCustPrefix(custPrefix);
		requestType.setCustReligion(custReligion);
		requestType.setCustState(custEmirate);// interchange with custEmirate //02-June-2014 ; Ajay Dhiman
		requestType.setFlgCustType(flgCustType);
		requestType.setFlgCustClass(flgCustClass);
		requestType.setFlgMinor(flgMinor);
		requestType.setFlgStaff(flgStaff);
		requestType.setHomeBranch(homeBranch);
		//requestType.setICType(ICType);
		requestType.setICType("");
		requestType.setMakerId(makerId);
		requestType.setMisCod1(misCod1);
		requestType.setMisCod2(misCod2);
		requestType.setMisCod3(misCod3);
		requestType.setMisCod4(misCod4);
		requestType.setNoOfDependants(noOfDependants);
		requestType.setPermAddr1(permAddr1);
		requestType.setPermAddr2(permAddr2);
		requestType.setPermAddr3(permAddr3);
		requestType.setPermAddrCity(permAddrCity);
		requestType.setPermAddrCountry(permAddrCountry);
		requestType.setPermAddrState(permAddrState);
		requestType.setPermAddrZip(permAddrZip);
		requestType.setProfessCategory(professCategory);
		requestType.setSignType(signType);
		requestType.setTxtCustAddr1(txtCustAddr1);
		requestType.setTxtCustAddr2(txtCustAddr2);
		requestType.setTxtCustAddr3(txtCustAddr3);
		requestType.setTxtCustDOB(txtCustDOB);
		requestType.setTxtCustPhone(txtCustPhone);
		requestType.setZip(zip);
		requestType.setICType(ICType);
		requestType.setCustNatlId(custNatId); // 16-May-2014, Onsite
		requestType.setCustShortName(custShortName); //14-July-2014 , Ajay Dhiman, Offshore
		requestType.setOrigRefNumber(oldRefNumber); //change for old ref no (Nishant Parmar, 01-03-2016)
		requestType.setFlgCustClass(custClass);//FCR changes
		
		requestMessage.setAddCustCreationReq(requestType);
		
		//************ Handling Response Start *******************************************************

		AddCustCreationResMsg respMessage = new AddCustCreationResMsg();
		AddCustCreationRes_type0 respType = new AddCustCreationRes_type0();
		
		try
		{
			LogGEN.writeTrace("COB_Log","inputXML: " + stub.getInputXML(requestMessage));
			LogGEN.writeTrace("COB_Log",stub.getInputXML(requestMessage));
			respMessage = stub.addCustCreation_Oper(requestMessage);	
			LogGEN.writeTrace("COB_Log",stub.outputxml);
		}
		catch(Exception ex)
		{
			LogGEN.writeTrace("COB_Log", "Exception while calling WebService"+ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("COB_Log", "Inside Finally Block");

		}

		headerTypeRes = respMessage.getHeader();

		respType = respMessage.getAddCustCreationRes();

		outputxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
							"<ns0:AddCustCreationResMsg>"+
									"<ns1:header>"+
											"<ns1:usecaseID>"+headerTypeRes.getUsecaseID()+"</ns1:usecaseID>"+
											"<ns1:serviceName>"+headerTypeRes.getServiceName()+"</ns1:serviceName>"+
											"<ns1:versionNo>"+headerTypeRes.getVersionNo()+"</ns1:versionNo>"+
											"<ns1:serviceAction>"+headerTypeRes.getServiceAction()+"</ns1:serviceAction>"+
											"<ns1:correlationID>"+headerTypeRes.getCorrelationID()+"</ns1:correlationID>"+
											"<ns1:sysRefNumber>"+headerTypeRes.getSysRefNumber()+"</ns1:sysRefNumber>"+
											"<ns1:senderID>"+headerTypeRes.getSenderID()+"</ns1:senderID>"+
											"<ns1:consumer>"+headerTypeRes.getConsumer()+"</ns1:consumer>"+
											"<ns1:reqTimeStamp>"+headerTypeRes.getReqTimeStamp()+"</ns1:reqTimeStamp>"+
											"<ns1:repTimeStamp>"+headerTypeRes.getRepTimeStamp()+"</ns1:repTimeStamp>"+
											"<ns1:username>"+headerTypeRes.getUsername()+"</ns1:username>"+
											"<ns1:credentials>"+headerTypeRes.getCredentials()+"</ns1:credentials>"+
											"<ns1:returnCode>"+headerTypeRes.getReturnCode()+"</ns1:returnCode>"+
											"<ns1:errorDescription>"+headerTypeRes.getErrorDescription()+"</ns1:errorDescription>"+
											"<ns1:errorDetail>"+headerTypeRes.getErrorDetail()+"</ns1:errorDetail>"+
											"<ns1:errorDetail>"+oldRefNumber+"</ns1:errorDetail>"+
									"</ns1:header>"+
									"<ns0:AddCustCreationRes>"+
											"<ns0:customerId>"+respType.getCustomerId()+"</ns0:customerId>"+
											//"<ns0:custNatlId>"+respType.getCustNationalId()+"<ns0:custNatlId>"+
											"<ns0:custFullName>"+respType.getCustFullName()+"</ns0:custFullName>"+
											"<ns0:custPrefix>"+respType.getCustPrefix()+"</ns0:custPrefix>"+
											"<ns0:custGender>"+respType.getCustGender()+"</ns0:custGender>"+
											"<ns0:custReligion>"+respType.getCustReligion()+"</ns0:custReligion>"+
											"<ns0:custMaritalStatus>"+respType.getCustMaritalStatus()+"</ns0:custMaritalStatus>"+
											"<ns0:custEmirate>"+respType.getCustEmirate()+"</ns0:custEmirate>"+
											"<ns0:custState>"+respType.getCustState()+"</ns0:custState>"+
											"<ns0:custNationality>"+respType.getCustNationality()+"</ns0:custNationality>"+
											"<ns0:custResidence>"+respType.getCustResidence()+"</ns0:custResidence>"+
											"<ns0:custNationalId>"+respType.getCustNationalId()+"</ns0:custNationalId>"+
											"<ns0:custEmployerName>"+respType.getCustEmployerName()+"</ns0:custEmployerName>"+
											"<ns0:custPosition>"+respType.getCustPosition()+"</ns0:custPosition>"+
											"<ns0:custEmployerEmirate>"+respType.getCustEmployerEmirate()+"</ns0:custEmployerEmirate>"+
											"<ns0:custEmployerPhone>"+respType.getCustEmployerPhone()+"</ns0:custEmployerPhone>"+
											"<ns0:custMonthlySalary>"+respType.getCustMonthlySalary()+"</ns0:custMonthlySalary>"+
											"<ns0:txtCustAddr1>"+respType.getTxtCustAddr1()+"</ns0:txtCustAddr1>"+
											"<ns0:txtCustAddr2>"+respType.getTxtCustAddr2()+"</ns0:txtCustAddr2>"+
											"<ns0:txtCustAddr3>"+respType.getTxtCustAddr3()+"</ns0:txtCustAddr3>"+
											"<ns0:txtCustDOB>"+respType.getTxtCustDOB()+"</ns0:txtCustDOB>"+ // 22-May onsite
											"<ns0:custEmail>"+respType.getCustEmail()+"</ns0:custEmail>"+
											"<ns0:txtCustPhone>"+respType.getTxtCustPhone()+"</ns0:txtCustPhone>"+
											"<ns0:custMobile>"+respType.getCustMobile()+"</ns0:custMobile>"+
											"<ns0:ICType>"+respType.getICType()+"</ns0:ICType>"+
											"<ns0:flgCustType>"+respType.getFlgCustType()+"</ns0:flgCustType>"+
											"<ns0:flgStaff>"+respType.getFlgStaff()+"</ns0:flgStaff>"+
											"<ns0:homeBranch>"+respType.getHomeBranch()+"</ns0:homeBranch>"+
											"<ns0:signType>"+respType.getSignType()+"</ns0:signType>"+
											"<ns0:professCategory>"+respType.getProfessCategory()+"</ns0:professCategory>"+
											"<ns0:custCountry>"+respType.getCustCountry()+"</ns0:custCountry>"+
											"<ns0:zip>"+respType.getZip()+"</ns0:zip>"+
											"<ns0:flgMinor>"+respType.getFlgMinor()+"</ns0:flgMinor>"+
											"<ns0:codCustType>"+respType.getCodCustType()+"</ns0:codCustType>"+
											"<ns0:misCod1>"+respType.getMisCod1()+"</ns0:misCod1>"+
											"<ns0:misCod2>"+respType.getMisCod2()+"</ns0:misCod2>"+
											"<ns0:misCod3>"+respType.getMisCod3()+"</ns0:misCod3>"+
											"<ns0:misCod4>"+respType.getMisCod4()+"</ns0:misCod4>"+
											"<ns0:makerId>"+respType.getMakerId()+"</ns0:makerId>"+
											"<ns0:checkerId>"+respType.getCheckerId()+"</ns0:checkerId>"+
											"<ns0:citizenshipId>"+respType.getCitizenshipId()+"</ns0:citizenshipId>"+
											"<ns0:codEmpId>"+respType.getCodEmpId()+"</ns0:codEmpId>"+
											"<ns0:noOfDependants>"+respType.getNoOfDependants()+"</ns0:noOfDependants>"+
											"<ns0:permAddr1>"+respType.getPermAddr1()+"</ns0:permAddr1>"+
											"<ns0:permAddr2>"+respType.getPermAddr2()+"</ns0:permAddr2>"+
											"<ns0:permAddr3>"+respType.getPermAddr3()+"</ns0:permAddr3>"+
											"<ns0:permAddrCity>"+respType.getPermAddrCity()+"</ns0:permAddrCity>"+
											"<ns0:permAddrCountry>"+respType.getPermAddrCountry()+"</ns0:permAddrCountry>"+
											"<ns0:permAddrState>"+respType.getPermAddrState()+"</ns0:permAddrState>"+
											"<ns0:permAddrZip>"+respType.getPermAddrZip()+"</ns0:permAddrZip>"+
											"<ns0:custNatlId>"+respType.getCustNatlId()+"<ns0:custNatlId>"+ 
											"<ns0:custShortName>"+respType.getCustShortName()+"<ns0:custShortName>"+ //14-July-2014, Ajay Dhiman, Offshore
									"</ns0:AddCustCreationRes>"+								         
							"</ns0:AddCustCreationResMsg>";
		LogGEN.writeTrace("COB_Log","outputxml for call createCustomerIDInFCR_COB: " + outputxml);

		
		sCabinet=xmlParser.getValueOf("EngineName");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
/*		 String selectRes = "";
		 String sOutputXML2 = outputxml;
*/
		 DBConnection con = new DBConnection();

		 
		 try
		 {
			 dbpass=AESEncryption.decrypt(dbpass);
		 }
		 catch(Exception e)
		 {
			 
		 }
			
			//DBConnection con=new DBConnection();
			  /**
			  * //Added By Harish For inserting original mssg
			  * Date			:	17 Mar 2015
			  * Description 	:	Replace execute with executeClob
			  */
		    String sOrg_Output_Add_Cust=stub.outputxml;
		    try {
				inputXml=stub.getInputXML(requestMessage);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String Query="insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
			"and randomnumber=''),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
			"?,sysdate,'"+  headerTypeRes.getReturnCode() + "')";
			LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
			//LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output_Add_Cust);
		 try {
			 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output_Add_Cust.replaceAll("'", "''"));
		} catch (Exception e2) {
			// TODO: handle exception
		}
		 
		 
		 
		 
		 
		 
		 
		 
		 LogGEN.writeTrace("COB_Log", "dburl::::"+dburl+" dbuser:"+dbuser+" dbpass:"+dbpass);
		 try {

		} catch (Exception e2) {
			// TODO: handle exception
			e2.getMessage();
		}
		//check, if exists update else insert
		String sCOBQuery="";

		if(oldRefNumber.equalsIgnoreCase("")||oldRefNumber.equalsIgnoreCase(newRefNumber)){
			oldRefNumber = newRefNumber;
			sCOBQuery = "insert into usr_0_integration_old_ref_cob(WI_NAME,CALL_NAME,REFNUMBER) values('"+winame+"', '"+call_type+"','"+newRefNumber+"')";
			LogGEN.writeTrace("COB_Log","sCOBQuery [Insert] ::::" + sCOBQuery);
		}
		/*else{
			sCOBQuery = "update usr_0_integration_old_ref_cob set REFNUMBER = '"+newRefNumber+"' where WI_NAME = '"+winame+"' and CALL_NAME = '"+call_type+"' ";
			LogGEN.writeTrace("COB_Log","sCOBQuery [Update] ::::" + sCOBQuery);
		}*/
			 
		try {
				 con.execute("jdbc:oracle:thin:@" + dburl,dbuser,dbpass,sCOBQuery);
			} catch (Exception e2) {
				LogGEN.writeTrace("COB_Log","error [Insert] ::::" + e2.getMessage());
				// TODO: handle exception
				e2.getMessage();
			}
		
		LogGEN.writeTrace("COB_Log","finally ::::" + outputxml);
		LogGEN.writeTrace("COB_Log", "*******NISHANT FINAL******* (01-03-2016)");
		//----- END
		
		return outputxml;
	}

	public String getUniqueNo()
	{
		Date date = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHMMS"); changed by Bikash for COB duplicate CustId issue reported on 16 November 2017		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmssSSS"); 
		String uniqueNo = sdf.format(date);
		LogGEN.writeTrace("COB_Log", "UNIQUE NO. is :"  +uniqueNo);
		return uniqueNo;
	}

	@SuppressWarnings("finally")
	public  String readFromFile(String inputxml) 
	{
		LogGEN.writeTrace("COB_Log","Inside readFromFile()");	
		String outputXml = null;
		try
		{		
			XMLParser parse = new XMLParser(inputxml);

			String processName = parse.getValueOf("ADCBProcess");			
			LogGEN.writeTrace("COB_Log", processName);

			String methodName = parse.getValueOf("ADCBMethod");			
			LogGEN.writeTrace("COB_Log" , methodName);

			StringBuffer fileData = new StringBuffer();
			try 
			{
				//BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ System.getProperty("file.separator") + "FBData//FBOutputFile//" + processName + "//" + methodName + ".xml"));
				BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ System.getProperty("file.separator") + "COB_DUMMY_INTEGRATION//" + processName + "//" + methodName + ".xml"));
				char[] buf = new char[1024];
				int numRead = 0;
				while ((numRead = reader.read(buf)) != -1) 
				{
					String readData = String.valueOf(buf, 0, numRead);
					fileData.append(readData);
				}				
				reader.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			outputXml = fileData.toString();
			LogGEN.writeTrace("COB_Log", "outputXml : " + outputXml);
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("COB_Log", "Exception while reading the file");
			LogGEN.writeTrace("COB_Log", e.getMessage());

			outputXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+"<NGException>Error reading file..</NGException>"
					+"<Retry>Y</Retry>";			
		} 
		finally 
		{
			LogGEN.writeTrace("COB_Log", "outputXml : " + outputXml);
			return outputXml;
		}
	}
	
}