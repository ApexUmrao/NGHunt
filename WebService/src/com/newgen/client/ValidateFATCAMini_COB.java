package com.newgen.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.newgen.AESEncryption;
import com.newgen.stub.InqFATCAValidationStub;
import com.newgen.stub.InqFATCAValidationStub.HeaderType;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReqMsgChoice_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReq_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationResMsg;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationResMsgChoice_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationRes_type0;

public class ValidateFATCAMini_COB extends WebServiceHandler
{
	String wsdlLocation = null;
	String cabinetName = null;
	String userName = null;
	String password = null;
	boolean isLoginRequired = false;
	String usIndiciaFound = null;
	String documentCollected = null;
	String telephoneMobile = null;
	String nationality = null;
	String outputxml = null;
	String wi_name = null;
	String errorDesc = null;
	String returnCode = null;
	String errorDetail = null;
	String returnValueAsFullResponse = null;
	String returnValueAsMiniRes = null;
	String invocation = null;
	WebServiceHandler webHandler = null;
	InqFATCAValidationStub stub = null;

	public String getFATCAStatus(String inputxml) throws RemoteException
	{
		LogGEN.writeTrace("COB_Log", "getFATCAStatus() for MiniValidation, INPUTXML : " + inputxml);
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
				outputxml = readFromFile(inputxml);
			}
			else if(invocation.equalsIgnoreCase("onshore"))
			{
				LogGEN.writeTrace("COB_Log","INTEGRATION CALL DATA CALLING WEB SERVICE...");			
				outputxml = communicateToWebService(inputxml);
			}			
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for section: COB_INTEGRATION_INVOCATION");		
			e.printStackTrace();
		}
		return outputxml;
	}

	public String communicateToWebService(String inputxml)
	{	
		LogGEN.writeTrace("COB_Log", "Inside communicateToWebService()");
		LogGEN.writeTrace("COB_Log","ReadingCabProperty : ValidateFATCAMini_COB call");
		try
		{
			webHandler.readCabProperty("ValidateFATCAMini_COB");
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for call :ValidateFATCAMini_COB.");		
			e.printStackTrace();
		}	
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);		

		wsdlLocation = (String)currentCabPropertyMap.get("WSDL_PATH");
		cabinetName = (String)currentCabPropertyMap.get("CABINET");
		userName = (String)currentCabPropertyMap.get("USER");
		isLoginRequired = Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
		password = (String)currentCabPropertyMap.get("PASSWORD");

		LogGEN.writeTrace("COB_Log", "wsdlLocation: " + wsdlLocation);
		LogGEN.writeTrace("COB_Log", "cabinetName: " + cabinetName);
		LogGEN.writeTrace("COB_Log", "userName: " + userName);
		LogGEN.writeTrace("COB_Log", "password : " + password);
		LogGEN.writeTrace("COB_Log", "isLoginRequired : " + isLoginRequired);

		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputxml); 
		LogGEN.writeTrace("COB_Log", "WorkitemNo : " + xmlDataParser.getValueOf("WIName"));

		// Getting form values for Mini Validation
		String customerSegement = xmlDataParser.getValueOf("customerSegment");
		LogGEN.writeTrace("COB_Log","customerSegement : " + customerSegement);
		String serviceType = xmlDataParser.getValueOf("serviceType");
		LogGEN.writeTrace("COB_Log","serviceType : " + serviceType);
		String product = xmlDataParser.getValueOf("product");
		LogGEN.writeTrace("COB_Log","product : " + product);
		String nationality = xmlDataParser.getValueOf("nationality");
		LogGEN.writeTrace("COB_Log","nationality : " + nationality);
		String residentialAddressCountry = xmlDataParser.getValueOf("residentialAddressCountry");
		LogGEN.writeTrace("COB_Log","residentialAddressCountry : " + residentialAddressCountry);
		String mailingAddressCountry = xmlDataParser.getValueOf("mailingAddressCountry");
		LogGEN.writeTrace("COB_Log","mailingAddressCountry : " + mailingAddressCountry);
		String telephoneResidence = xmlDataParser.getValueOf("telephoneResidence");	
		LogGEN.writeTrace("COB_Log","telephoneResidence : " + telephoneResidence);
		String telephoneOffice = xmlDataParser.getValueOf("telephoneOffice");		
		LogGEN.writeTrace("COB_Log","telephoneOffice : " + telephoneOffice);
		String telephoneMobile = xmlDataParser.getValueOf("telephoneMobile");	
		LogGEN.writeTrace("COB_Log","telephoneMobile : " + telephoneMobile);
		String USpassportholder = xmlDataParser.getValueOf("USpassportholder");		
		LogGEN.writeTrace("COB_Log","USpassportholder : " + USpassportholder);
		String USTaxLiable = xmlDataParser.getValueOf("USTaxLiable");		
		LogGEN.writeTrace("COB_Log","USTaxLiable : " + USTaxLiable);
		String countryOfBirth = xmlDataParser.getValueOf("countryOfBirth");		
		LogGEN.writeTrace("COB_Log","countryOfBirth : " + countryOfBirth);
		String standingInstructionCountry = xmlDataParser.getValueOf("standingInstructionCountry");		
		LogGEN.writeTrace("COB_Log","standingInstructionCountry : " + standingInstructionCountry);
		String POAHolderCountry = xmlDataParser.getValueOf("POAHolderCountry");	
		LogGEN.writeTrace("COB_Log","POAHolderCountry : " + POAHolderCountry);
		String ssn = xmlDataParser.getValueOf("SSN");
		LogGEN.writeTrace("COB_Log","ssn : " + ssn);		

		// All Request Objects
		InqFATCAValidationReqMsgChoice_type0 reqMessageChoice =  new InqFATCAValidationReqMsgChoice_type0();
		InqFATCAValidationReqMsg reqMessage = new InqFATCAValidationReqMsg();
		InqFATCAValidationReq_type0 reqType = new InqFATCAValidationReq_type0();
		
		// All Response Objects		
		InqFATCAValidationResMsgChoice_type0  resMessageChoice = new  InqFATCAValidationResMsgChoice_type0();
		InqFATCAValidationResMsg resMesage = new InqFATCAValidationResMsg();
		InqFATCAValidationRes_type0 resType = new InqFATCAValidationRes_type0();
		
		HeaderType headerType = new HeaderType();

		headerType.setUsecaseID("1234"); // Hard coded value 1234
		headerType.setServiceName("InqFATCAValidation"); // Hard coded
		headerType.setVersionNo("1.0"); // Hard coded
		headerType.setServiceAction("Inquiry"); // Hard Coded
		headerType.setSysRefNumber(getUniqueNo()); //Need to check again 16/18
		headerType.setSenderID(webHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
		headerType.setReqTimeStamp(sDate);				
		headerType.setUsername("");  // Hard Coded set to BLANK
		LogGEN.writeTrace("COB_Log","loggedinuser : " + loggedinuser);
		headerType.setCredentials("WMS"); //BPM Logged in user id
		reqMessage.setHeader(headerType);

		reqType.setCustomerSegment(customerSegement);
		reqType.setServiceType(serviceType);
		reqType.setProduct(product);
		reqType.setNationality(nationality);
		reqType.setResidentialAddressCountry(residentialAddressCountry);
		reqType.setMailingAddressCountry(mailingAddressCountry);
		reqType.setTelephoneResidence(telephoneResidence);
		reqType.setTelephoneOffice(telephoneOffice);
		reqType.setTelephoneMobile(telephoneMobile);
		reqType.setUSpassportholder(USpassportholder);
		reqType.setUSTaxLiable(USTaxLiable);
		reqType.setCountryOfBirth(countryOfBirth);
		reqType.setStandingInstructionCountry(standingInstructionCountry);
		reqType.setPOAHolderCountry(POAHolderCountry);
		reqType.setSSN(ssn);	
		
		reqMessageChoice.setInqFATCAValidationReq(reqType);

		reqMessage.setInqFATCAValidationReqMsgChoice_type0(reqMessageChoice);

		
		try
		{
			LogGEN.writeTrace("COB_Log", "making stub object for call : ValidateFATCAMini_COB");
			stub = new InqFATCAValidationStub(wsdlLocation);
		}
		catch (AxisFault e)
		{
			LogGEN.writeTrace("COB_Log","Exception while making object of InqFATCAValidationStub(wsdlLocation)");
			e.printStackTrace();
		}
		
		try
		{
			LogGEN.writeTrace("COB_Log", "calling webservice for call : ValidateFATCAMini_COB");
			LogGEN.writeTrace("COB_Log",stub.getinputXML(reqMessage));
			resMesage = stub.inqFATCAValidation_Oper(reqMessage);
			LogGEN.writeTrace("COB_Log",stub.resFatcavMsg);
			LogGEN.writeTrace("COB_Log","resMesage for call ValidateFATCAMini_COB: " + resMesage);
		}
		catch(Exception ex)
		{
			LogGEN.writeTrace("COB_Log", "In catch block for call : ValidateFATCAMini_COB");
			ex.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("COB_Log", "In finally block for call : ValidateFATCAMini_COB");
			LogGEN.writeTrace("COB_Log","resMesage for call ValidateFATCAMini_COB: " + resMesage);
		}

		headerType = resMesage.getHeader();		
		resMessageChoice = resMesage.getInqFATCAValidationResMsgChoice_type0();
		resType = resMessageChoice.getInqFATCAValidationRes();


		outputxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<ns0:InqFATCAValidationResMsg>"+
						"<ns1:header>"+
										"<ns1:usecaseID>"+headerType.getUsecaseID()+"</ns1:usecaseID>"+
										"<ns1:serviceName>"+headerType.getServiceName()+"</ns1:serviceName>"+
										"<ns1:versionNo>"+headerType.getVersionNo()+"</ns1:versionNo>"+
										"<ns1:serviceAction>"+headerType.getServiceAction()+"</ns1:serviceAction>"+
										"<ns1:correlationID>"+headerType.getCorrelationID()+"</ns1:correlationID>"+
										"<ns1:sysRefNumber>"+headerType.getSysRefNumber()+"</ns1:sysRefNumber>"+
										"<ns1:senderID>"+headerType.getSenderID()+"</ns1:senderID>"+
										"<ns1:consumer>"+headerType.getConsumer()+"</ns1:consumer>"+
										"<ns1:reqTimeStamp>"+headerType.getReqTimeStamp()+"</ns1:reqTimeStamp>"+
										"<ns1:repTimeStamp>"+headerType.getRepTimeStamp()+"</ns1:repTimeStamp>"+
										"<ns1:username>"+headerType.getUsername()+"</ns1:username>"+
										"<ns1:credentials>"+headerType.getCredentials()+"</ns1:credentials>"+
										"<ns1:returnCode>"+headerType.getReturnCode()+"</ns1:returnCode>"+
										"<ns1:errorDescription>"+headerType.getErrorDescription()+"</ns1:errorDescription>"+
										"<ns1:errorDetail>"+headerType.getErrorDetail()+"</ns1:errorDetail>"+
						"</ns1:header>"+								            
						"<ns0:InqFATCAValidationRes>"+
												"<ns0:customerSegment>"+resType.getCustomerSegment()+"</ns0:customerSegment>"+
												"<ns0:serviceType>"+resType.getServiceType()+"</ns0:serviceType>"+
												"<ns0:product>"+resType.getProduct()+"</ns0:product>"+
												"<ns0:nationality>"+resType.getNationality()+"</ns0:nationality>"+
												"<ns0:residentialAddressCountry>"+resType.getResidentialAddressCountry()+"</ns0:residentialAddressCountry>"+
												"<ns0:mailingAddressCountry>"+resType.getMailingAddressCountry()+"</ns0:mailingAddressCountry>"+
												"<ns0:telephoneResidence>"+resType.getTelephoneResidence()+"</ns0:telephoneResidence>"+
												"<ns0:telephoneOffice>"+resType.getTelephoneOffice()+"</ns0:telephoneOffice>"+
												"<ns0:telephoneMobile>"+resType.getTelephoneMobile()+"</ns0:telephoneMobile>"+
												"<ns0:USpassportholder>"+resType.getUSpassportholder()+"</ns0:USpassportholder>"+
												"<ns0:USTaxLiable>"+resType.getUSTaxLiable()+"</ns0:USTaxLiable>"+
												"<ns0:countryOfBirth>"+resType.getCountryOfBirth()+"</ns0:countryOfBirth>"+
												"<ns0:standingInstructionCountry>"+resType.getStandingInstructionCountry()+"</ns0:standingInstructionCountry>"+
												"<ns0:POAHolderCountry>"+resType.getPOAHolderCountry()+"</ns0:POAHolderCountry>"+
												"<ns0:returnValue>"+resType.getReturnValue()+"</ns0:returnValue>"+
						"</ns0:InqFATCAValidationRes>"+
				"</ns0:InqFATCAValidationResMsg>";			

		
		try
		{
			WebServiceHandler webHandler = null;
			webHandler = new WebServiceHandler();	
			try {
				 webHandler.readCabProperty("JTS");
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");
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
			    String sOrg_Output_Add_Cust=stub.resFatcavMsg;
			    String inputXml=stub.getinputXML(reqMessage);
			    String winame=xmlDataParser.getValueOf("WIName");
				String Query="insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber=''),'ValidateFATCADMini_COB',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  headerType.getReturnCode() + "')";
				LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
				//LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output_Add_Cust);
			 try {
				 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output_Add_Cust.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		catch(Exception ee)
		{
			
		}
		LogGEN.writeTrace("COB_Log","outputxml for call ValidateFATCAMini_COB: " + outputxml);		
		return outputxml;
	}


	private String readFromFile(String inputxml) 
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
			
		}
		return outputXml;
	}
	
	public String getUniqueNo()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmssSSS");
		String uniqueNo = sdf.format(date);
		return uniqueNo;
	}
}

