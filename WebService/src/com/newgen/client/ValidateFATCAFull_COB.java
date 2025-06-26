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
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReq2_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReqMsgChoice_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationRes2_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationResMsg;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationResMsgChoice_type0;

public class ValidateFATCAFull_COB extends WebServiceHandler
{
	String wsdlLocation = null;
	String cabinetName = null;
	String userName = null;
	String password = null;
	String errorDesc = null;
	String returnCode = null;
	String errorDetail = null;
	String returnValueAsFullResponse = null;
	String outputxml;
	String invocation = null;
	WebServiceHandler webHandler = null;
	boolean isLoginRequired = false;

	public String getFATCAStatus(String inputxml) throws RemoteException
	{	
		LogGEN.writeTrace("COB_Log", "Inside getFATCAStatus() for FATCAFullValidation, INPUTXML" + inputxml);
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
		try
		{
			LogGEN.writeTrace("COB_Log", "Inside communicateToWebService()...");
			LogGEN.writeTrace("COB_Log","ReadingCabProperty : ValidateFATCAFull_COB call");
			
			webHandler.readCabProperty("ValidateFATCAFull_COB");			
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property the file for call : ValidateFATCAFull_COB.");		
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
		String wi_name = xmlDataParser.getValueOf("WIName");
		LogGEN.writeTrace("COB_Log","wi_name : " + wi_name);
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
		String USIndiaciaFound = xmlDataParser.getValueOf("USIndiaciaFound");		
		LogGEN.writeTrace("COB_Log","USIndiaciaFound : " + USIndiaciaFound);
		String documentCollected = xmlDataParser.getValueOf("documentCollected");		
		LogGEN.writeTrace("COB_Log","documentCollected : " + documentCollected);
		String TINorSSN = xmlDataParser.getValueOf("TINorSSN");		
		LogGEN.writeTrace("COB_Log","TINorSSN : " + TINorSSN);
		String customerFATCAClsfctn = xmlDataParser.getValueOf("customerFATCAClsfctn");		
		LogGEN.writeTrace("COB_Log","customerFATCAClsfctn : " + customerFATCAClsfctn);
		String customerFATCAClsfctnDate = xmlDataParser.getValueOf("customerFATCAClsfctnDate");
		LogGEN.writeTrace("COB_Log","customerFATCAClsfctnDate : " + customerFATCAClsfctnDate);
		// Getting W8BenDate and passing it to request sending to webservice. (22-June-2014)//Post Live
		String W8SignDate = xmlDataParser.getValueOf("W8SignDate");
		LogGEN.writeTrace("COB_Log","W8BenDate : " + W8SignDate);
		

		InqFATCAValidationStub stub = null;
		try
		{
			LogGEN.writeTrace("COB_Log", "making stub object for call : ValidateFATCAFULL_COB");
			stub = new InqFATCAValidationStub(wsdlLocation);
		}
		catch (AxisFault e1)
		{
			LogGEN.writeTrace("COB_Log","Exception while making object of InqFATCAValidationStub(wsdlLocation)");
			e1.printStackTrace();
		}
		// All Request Objects			
		InqFATCAValidationReqMsgChoice_type0 reqMessageChoice =  new InqFATCAValidationReqMsgChoice_type0();
		InqFATCAValidationReqMsg reqMessage = new InqFATCAValidationReqMsg();
		InqFATCAValidationReq2_type0 req2Type = new InqFATCAValidationReq2_type0();		

		// All Response Objects		
		InqFATCAValidationResMsgChoice_type0  resMessageChoice = new  InqFATCAValidationResMsgChoice_type0();
		InqFATCAValidationResMsg resMesage = new InqFATCAValidationResMsg();		
		InqFATCAValidationRes2_type0 res2Type = new  InqFATCAValidationRes2_type0(); 

		HeaderType headerType1 = new HeaderType();

		headerType1.setUsecaseID("1234");
		headerType1.setServiceName("InqFATCAValidation");
		headerType1.setVersionNo("2.0");
		headerType1.setServiceAction("Inquiry");
		headerType1.setSysRefNumber(getUniqueNo());
		headerType1.setSenderID(webHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
		headerType1.setReqTimeStamp(sDate);				
		headerType1.setUsername("");
		headerType1.setCredentials("WMS"); //BPM Logged in user id
		
		reqMessage.setHeader(headerType1);

		req2Type.setCustomerSegment(customerSegement);
		req2Type.setServiceType(serviceType);
		req2Type.setProduct(product);
		req2Type.setNationality(nationality);
		req2Type.setResidentialAddressCountry(residentialAddressCountry);
		req2Type.setMailingAddressCountry(mailingAddressCountry);
		req2Type.setTelephoneResidence(telephoneResidence);
		req2Type.setTelephoneOffice(telephoneOffice);
		req2Type.setTelephoneMobile(telephoneMobile);
		req2Type.setUSpassportholder(USpassportholder);
		req2Type.setUSTaxLiable(USTaxLiable);
		req2Type.setCountryOfBirth(countryOfBirth);
		req2Type.setStandingInstructionCountry(standingInstructionCountry);
		req2Type.setPOAHolderCountry(POAHolderCountry);
		req2Type.setUSIndiaciaFound(USIndiaciaFound);
		req2Type.setDocumentCollected(documentCollected);
		req2Type.setTINorSSN(TINorSSN);
		req2Type.setCustomerFATCAClsfctn(customerFATCAClsfctn);
		req2Type.setCustomerFATCAClsfctnDate(customerFATCAClsfctnDate);
		req2Type.setW8SignDate(W8SignDate);// 22-June-2014
		
		reqMessageChoice.setInqFATCAValidationReq2(req2Type);

		reqMessage.setInqFATCAValidationReqMsgChoice_type0(reqMessageChoice);

		try
		{
			LogGEN.writeTrace("COB_Log", "calling webservice for call : ValidateFATCAFULL_COB");
			LogGEN.writeTrace("COB_Log",stub.getinputXML(reqMessage));
			resMesage = stub.inqFATCAValidation_Oper(reqMessage);
			LogGEN.writeTrace("COB_Log",stub.resFatcavMsg);
			LogGEN.writeTrace("COB_Log","resMesage for call ValidateFATCAFULL_COB: " + resMesage);
		}
		catch (RemoteException e)
		{
			LogGEN.writeTrace("COB_Log", "In catch block for call : ValidateFATCAFULL_COB");
			e.printStackTrace();
		}	
		finally
		{
			LogGEN.writeTrace("COB_Log", "In finally block for call : ValidateFATCAFULL_COB");
			LogGEN.writeTrace("COB_Log","resMesage for call ValidateFATCAFULL_COB: " + resMesage);
		}
		

		headerType1 = resMesage.getHeader();
		resMessageChoice = resMesage.getInqFATCAValidationResMsgChoice_type0();
		res2Type = resMessageChoice.getInqFATCAValidationRes2();

		outputxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
								"<ns0:InqFATCAValidationResMsg >"+
										"<ns1:header>"+
													"<ns1:usecaseID>"+headerType1.getUsecaseID()+"</ns1:usecaseID>"+
													"<ns1:serviceName>"+headerType1.getServiceName()+"</ns1:serviceName>"+
													"<ns1:versionNo>"+headerType1.getVersionNo()+"</ns1:versionNo>"+
													"<ns1:serviceAction>"+headerType1.getServiceAction()+"</ns1:serviceAction>"+
													"<ns1:correlationID>"+headerType1.getCorrelationID()+"</ns1:correlationID>"+
													"<ns1:sysRefNumber>"+headerType1.getSysRefNumber()+"</ns1:sysRefNumber>"+
													"<ns1:senderID>"+headerType1.getSenderID()+"</ns1:senderID>"+
													"<ns1:consumer>"+headerType1.getConsumer()+"</ns1:consumer>"+
													"<ns1:reqTimeStamp>"+headerType1.getReqTimeStamp()+"</ns1:reqTimeStamp>"+
													"<ns1:repTimeStamp>"+headerType1.getRepTimeStamp()+"</ns1:repTimeStamp>"+
													"<ns1:username>"+headerType1.getUsername()+"</ns1:username>"+
													"<ns1:credentials>"+headerType1.getCredentials()+"</ns1:credentials>"+
													"<ns1:returnCode>"+headerType1.getReturnCode()+"</ns1:returnCode>"+
													"<ns1:errorDescription>"+headerType1.getErrorDescription()+"</ns1:errorDescription>"+
													"<ns1:errorDetail>"+headerType1.getErrorDetail()+"</ns1:errorDetail>"+
										"</ns1:header>"+
								"<ns0:InqFATCAValidationRes2>"+									
													"<ns0:customerSegment>"+res2Type.getCustomerSegment()+"</ns0:customerSegment>"+
													"<ns0:serviceType>"+res2Type.getServiceType() +"</ns0:serviceType>"+
													"<ns0:product>"+res2Type.getProduct()+"</ns0:product>"+
													"<ns0:nationality>"+res2Type.getNationality()+"</ns0:nationality>"+
													"<ns0:residentialAddressCountry>"+res2Type.getResidentialAddressCountry()+"</ns0:residentialAddressCountry>"+
													"<ns0:mailingAddressCountry>"+res2Type.getMailingAddressCountry()+"</ns0:mailingAddressCountry>"+
													"<ns0:telephoneResidence>"+res2Type.getTelephoneResidence()+"</ns0:telephoneResidence>"+
													"<ns0:telephoneOffice>"+res2Type.getTelephoneOffice()+"</ns0:telephoneOffice>"+
													"<ns0:telephoneMobile>"+res2Type.getTelephoneOffice()+"</ns0:telephoneMobile>"+
													"<ns0:USpassportholder>"+res2Type.getUSpassportholder()+"</ns0:USpassportholder>"+
													"<ns0:USTaxLiable>"+res2Type.getUSTaxLiable() +"</ns0:USTaxLiable>"+
													"<ns0:countryOfBirth>"+res2Type.getCountryOfBirth()+"</ns0:countryOfBirth>"+
													"<ns0:standingInstructionCountry>"+res2Type.getStandingInstructionCountry() +"</ns0:standingInstructionCountry>"+
													"<ns0:POAHolderCountry>"+res2Type.getPOAHolderCountry()+"</ns0:POAHolderCountry>"+
													"<ns0:USIndiaciaFound>"+res2Type.getUSIndiaciaFound()+"</ns0:USIndiaciaFound>"+
													"<ns0:documentCollected>"+res2Type.getDocumentCollected() +"</ns0:documentCollected>"+
													"<ns0:TINorSSN>"+res2Type.getTINorSSN()+"</ns0:TINorSSN>"+
													"<ns0:customerFATCAClsfctn>"+res2Type.getCustomerFATCAClsfctn()+"</ns0:customerFATCAClsfctn>"+
													"<ns0:customerFATCAClsfctnDate>"+res2Type.getCustomerFATCAClsfctnDate() +"</ns0:customerFATCAClsfctnDate>"+
													"<ns0:W8SignDate>"+res2Type.getW8SignDate()+"</ns0:W8SignDate>"+
													"<ns0:returnValue>"+res2Type.getReturnValue() +"</ns0:returnValue>"+
								"</ns0:InqFATCAValidationRes2>"+
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
				"and randomnumber=''),'ValidateFATCAFull_COB',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  headerType1.getReturnCode() + "')";
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
		
		LogGEN.writeTrace("COB_Log","outputxml for call ValidateFATCAFULL_COB: " + outputxml);		
		return outputxml;
	}

	@SuppressWarnings("finally")
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
			return outputXml;
		}
	}
	
	public String getUniqueNo()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmssSSS");
		String uniqueNo = sdf.format(date);
		return uniqueNo;
	}
}

