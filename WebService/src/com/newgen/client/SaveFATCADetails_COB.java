package com.newgen.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis2.AxisFault;
import org.xml.sax.SAXException;

import com.newgen.AESEncryption;
import com.newgen.stub.ModCustMDMPubInfoStub;
import com.newgen.stub.ModCustMDMPubInfoStub.HeaderType;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReqMsg;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReq_type0;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoResMsg;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoRes_type0;

public class SaveFATCADetails_COB extends WebServiceHandler
{	
	String customerId = null;
	String customerInfoType = null;
	String outputxml = null;
	String customerInformation = null;
	String wsdlLocation = null;
	String cabinetName = null;
	String userName = null;
	String password = null;
	String wi_name = null;
	String returnCode = null;
	String errorDetail = null;
	String errorDesc = null;
	String reason = null;
	String status = null;
	String invocation = null;
	
	WebServiceHandler webHandler = null;
	
	boolean isLoginRequired = false;


	public String saveFATCADetailsStatus(String inputxml) throws RemoteException
	{	
		LogGEN.writeTrace("COB_Log", "getFATCAStatus() for saveFATCADetailsStatus, INPUTXML : " + inputxml);		
		try
		{	
			LogGEN.writeTrace("COB_Log","ReadingCabProperty : COB_INTEGRATION_INVOCATION");
			webHandler = new WebServiceHandler();		
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
		LogGEN.writeTrace("COB_Log","ReadingCabProperty : saveFATCADetailsStatus call");
		try
		{			
			webHandler.readCabProperty("SaveFATCADetails_COB");
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for call :saveFATCADetailsStatus.");		
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

		// Getting values from form
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputxml);
		
		String custType = xmlDataParser.getValueOf("CustType");
		LogGEN.writeTrace("COB_Log", "custType : " + custType);
		
		if(custType.equalsIgnoreCase("Individual"))
		{
			String USpassportHolder = xmlDataParser.getValueOf("USpassportholder");
			LogGEN.writeTrace("COB_Log", "USpassportHolder : " + USpassportHolder);
			
			String USTaxLiable = xmlDataParser.getValueOf("USTaxLiable");
			LogGEN.writeTrace("COB_Log", "USTaxLiable : " + USTaxLiable);
			
			String TINorSSN = xmlDataParser.getValueOf("TINorSSN");
			LogGEN.writeTrace("COB_Log", "TINorSSN : " + TINorSSN);
			
			String customerFATCAClsfctnDate = xmlDataParser.getValueOf("customerFATCAClsfctnDate");
			LogGEN.writeTrace("COB_Log", "customerFATCAClsfctnDate : " + customerFATCAClsfctnDate);
			
			String documentCollected = xmlDataParser.getValueOf("documentCollected");
			LogGEN.writeTrace("COB_Log", "documentCollected : " + documentCollected);
			
			String USIndiaciaFound = xmlDataParser.getValueOf("USIndiaciaFound");
			LogGEN.writeTrace("COB_Log", "USIndiaciaFound : " + USIndiaciaFound);
			
			String customerFATCAClsfctn = xmlDataParser.getValueOf("customerFATCAClsfctn");
			LogGEN.writeTrace("COB_Log", "customerFATCAClsfctn : " + customerFATCAClsfctn);
			
			String countryOfBirth = xmlDataParser.getValueOf("countryOfBirth");
			LogGEN.writeTrace("COB_Log", "countryOfBirth : " + countryOfBirth);	
			
			// Added on 29-June-2014, getting W8SignDate and passing it in customerInformation
			String W8_Sign_Date = xmlDataParser.getValueOf("W8_Sign_Date");
			LogGEN.writeTrace("COB_Log", "W8_Sign_Date_1 : " + W8_Sign_Date);
			
			// For Individual customer  
			customerInformation  = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
											 "<FATCADetails>"+
												"<USpassportholder>"+USpassportHolder+"</USpassportholder>"+
												"<USTaxLiable>"+USTaxLiable+"</USTaxLiable>"+
												"<TINorSSN>"+TINorSSN+"</TINorSSN>"+
												//"<!--&lt;W8_Sign_Date>10/02/2014&lt;/W8_Sign_Date-->"+
												"<W8_Sign_Date>"+W8_Sign_Date+"</W8_Sign_Date>"+ //Added on 29-June-2014
												"<customerFATCAClsfctnDate>"+customerFATCAClsfctnDate+"</customerFATCAClsfctnDate>"+
												"<documentCollected>"+documentCollected+"</documentCollected>"+
												"<USIndiaciaFound>"+USIndiaciaFound+"</USIndiaciaFound>"+
												"<customerFATCAClsfctn>"+customerFATCAClsfctn+"</customerFATCAClsfctn>"+
												"<countryOfBirth>"+countryOfBirth+"</countryOfBirth>"+
											"</FATCADetails>";
		}
		else if(custType.equalsIgnoreCase("Corporate")||custType.equalsIgnoreCase("Bank & Insurance"))
		{
			String customerClsfctn = xmlDataParser.getValueOf("customerClsfctn");
			LogGEN.writeTrace("COB_Log", "customerClsfctn : " + customerClsfctn);
			
			String customerClsfctnDate = xmlDataParser.getValueOf("customerClsfctnDate");
			LogGEN.writeTrace("COB_Log", "customerClsfctnDate : " + customerClsfctnDate);
			
			String natureOfEntity = xmlDataParser.getValueOf("natureOfEntity");
			LogGEN.writeTrace("COB_Log", "natureOfEntity : " + natureOfEntity);
			
			String typeOfEntity = xmlDataParser.getValueOf("typeOfEntity");
			LogGEN.writeTrace("COB_Log", "typeOfEntity : " + typeOfEntity);
			
			String FATCAStatus = xmlDataParser.getValueOf("FATCAStatus");
			LogGEN.writeTrace("COB_Log", "FATCAStatus : " + FATCAStatus);
			
			String documentCollected = xmlDataParser.getValueOf("documentCollected");
			LogGEN.writeTrace("COB_Log", "documentCollected : " + documentCollected);
			
			String dateOfDocument = xmlDataParser.getValueOf("dateOfDocument");
			LogGEN.writeTrace("COB_Log", "dateOfDocument : " + dateOfDocument);
			
			String idntfctnNumRequired = xmlDataParser.getValueOf("idntfctnNumRequired");
			LogGEN.writeTrace("COB_Log", "idntfctnNumRequired : " + idntfctnNumRequired);
			
			String idntfctnNumber = xmlDataParser.getValueOf("idntfctnNumber");
			LogGEN.writeTrace("COB_Log", "idntfctnNumber : " + idntfctnNumber);
			
			String customerFATCAClsfctnDate = xmlDataParser.getValueOf("customerFATCAClsfctnDate");
			LogGEN.writeTrace("COB_Log", "customerFATCAClsfctnDate : " + customerFATCAClsfctnDate);	
			
					
			if (customerClsfctn.equalsIgnoreCase("Client"))
			{
				//********************************* UAT Change 12-June-2014 Start ****************************
				
				int startIndex = inputxml.indexOf("<FATCA_NI_Details>");
				LogGEN.writeTrace("COB_Log", "startIndex : "+ startIndex);
				
				int endIndex = inputxml.indexOf("</FATCA_NI_Details>");
				LogGEN.writeTrace("COB_Log", "endIndex : "+ endIndex);
				
				String requiredxml = "";
				try
				{
					requiredxml = inputxml.substring(startIndex, endIndex+19);				
				}
				catch(Exception ex)
				{
					LogGEN.writeTrace("COB_Log","Exception while substring");
					ex.printStackTrace();
					return "BLANK_VALUE";
				}
				LogGEN.writeTrace("COB_Log", "requiredxml_org : "+ requiredxml);
				requiredxml = requiredxml.replaceAll("\r", "");
				requiredxml = requiredxml.replaceAll("\n", "");
				requiredxml = requiredxml.replaceAll("\t", "");
				LogGEN.writeTrace("COB_Log", "requiredxml_modified : "+ requiredxml);
				
				int ownerDetailsTagIndex = requiredxml.indexOf("<OwnerDetails>");
				LogGEN.writeTrace("COB_Log","ownerDetailsTagIndex : " + ownerDetailsTagIndex);
				
				if(ownerDetailsTagIndex > 0)
				{
					TestXML target = new TestXML();
					//LogGEN.writeTrace("COB_Log", "target : " + target);
					String requiredData = null;
					try 
					{
						//LogGEN.writeTrace("COB_Log", "inside Try Block");
						requiredData = target.getTagValue(requiredxml, "OwnerDetails");
					}
					catch (ParserConfigurationException e) 
					{
						LogGEN.writeTrace("COB_Log", "ParserConfigurationException" + e.getMessage());
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (SAXException e) 
					{
						// TODO Auto-generated catch block
						LogGEN.writeTrace("COB_Log", "SAXException" + e.getMessage());
						e.printStackTrace();
					}
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						LogGEN.writeTrace("COB_Log", "IOException" + e.getMessage());
						e.printStackTrace();
					}			
					LogGEN.writeTrace("COB_Log", "requiredData : "+ requiredData);
					String noOfOwners[] = null;
					if(requiredData != null)
					{
						LogGEN.writeTrace("COB_Log","Length of requiredData : " + requiredData.length());
						noOfOwners = requiredData.split(";");
						LogGEN.writeTrace("COB_Log", "length of noOfOwners array[] : "+ noOfOwners.length);				
					}
					else
					{
						//LogGEN.writeTrace("COB_Log","Length of requiredData : " + requiredData.length());
						LogGEN.writeTrace("COB_Log","ELSE_BLOCK");
						return "BLANK_VALUE";
					}
					
					String detailsOfOwners[];
					String actualInfoAddToCustomerInformation = "";
					for (int i = 0 ; i < noOfOwners.length; i++)
					{
						detailsOfOwners = noOfOwners[i].split(",");				
						LogGEN.writeTrace("COB_Log", "length of detailsOfOwners array[] : " + detailsOfOwners.length);
						
						/*LogGEN.writeTrace("COB_Log", "*************************");
						LogGEN.writeTrace("COB_Log","detailsOfOwners[0] : " + detailsOfOwners[0]);
						LogGEN.writeTrace("COB_Log","detailsOfOwners[1] : " + detailsOfOwners[1]);
						LogGEN.writeTrace("COB_Log","detailsOfOwners[2] : " + detailsOfOwners[2]);
						LogGEN.writeTrace("COB_Log","detailsOfOwners[3] : " + detailsOfOwners[3]);
						LogGEN.writeTrace("COB_Log","detailsOfOwners[4] : " + detailsOfOwners[4]);
						LogGEN.writeTrace("COB_Log","detailsOfOwners[5] : " + detailsOfOwners[5]);*/
						String temp_detailsOfOwners5 = ""; // added on 18-June-2014, handling exception, if last value in grid is blank
						try
						{
							temp_detailsOfOwners5 = detailsOfOwners[5];
						}
						catch(ArrayIndexOutOfBoundsException aiobex)
						{
							LogGEN.writeTrace("COB_Log","Exception while getting the details of owners");
						}
						LogGEN.writeTrace("COB_Log", "temp_detailsOfOwners5 : " + temp_detailsOfOwners5);
						
						actualInfoAddToCustomerInformation = actualInfoAddToCustomerInformation + "<OwnerDetails>"+
									"<ownerNumber>"+detailsOfOwners[0]+"</ownerNumber>"+
									"<ownerName>"+detailsOfOwners[1]+"</ownerName>"+
									"<ownershipSharePercentage>"+detailsOfOwners[2]+"</ownershipSharePercentage>"+
									"<ownershipAddress>"+detailsOfOwners[3]+"</ownershipAddress>"+
									"<ownerTINorSSN>"+detailsOfOwners[4]+"</ownerTINorSSN>"+
									"<ownerW9Availability>"+temp_detailsOfOwners5+"</ownerW9Availability>"+
								"</OwnerDetails>";				
						
						LogGEN.writeTrace("COB_Log", "actualInfoAddToCustomerInformation(inside loop) :" + actualInfoAddToCustomerInformation);
					}
					LogGEN.writeTrace("COB_Log", "actualInfoAddToCustomerInformation(outside loop) :" + actualInfoAddToCustomerInformation);
					
					// For Non Individual customer
					customerInformation = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
													 "<FATCA_NI_Details>"+
														"<customerClsfctn>"+customerClsfctn+"</customerClsfctn>"+
														"<customerClsfctnDate>"+customerClsfctnDate+"</customerClsfctnDate>"+
														"<natureOfEntity>"+natureOfEntity+"</natureOfEntity>"+
														//"<!--&lt;W8_Sign_Date>10/02/2014&lt;/W8_Sign_Date-->"+														
														"<typeOfEntity>"+typeOfEntity+"</typeOfEntity>"+
														"<FATCAStatus>"+FATCAStatus+"</FATCAStatus>"+
														"<documentCollected>"+documentCollected+"</documentCollected>"+
														"<dateOfDocument>"+dateOfDocument+"</dateOfDocument>"+
														"<idntfctnNumRequired>"+idntfctnNumRequired+"</idntfctnNumRequired>"+
														"<idntfctnNumber>"+idntfctnNumber+"</idntfctnNumber>"+
														"<customerFATCAClsfctnDate>"+customerFATCAClsfctnDate+"</customerFATCAClsfctnDate>"+											
														actualInfoAddToCustomerInformation+
														"</FATCA_NI_Details>";	
				
					LogGEN.writeTrace("COB_Log", "customerInformation (Client)... :" + customerInformation);				
					//********************************* UAT Change 12-June-2014 End  ****************************
				}
				else
				{
					LogGEN.writeTrace("COB_Log","ELSE_CASE_OWNER_DETAILS NOT PRESENT...");
					
					customerInformation = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
										 "<FATCA_NI_Details>"+
											"<customerClsfctn>"+customerClsfctn+"</customerClsfctn>"+
											"<customerClsfctnDate>"+customerClsfctnDate+"</customerClsfctnDate>"+
											"<natureOfEntity>"+natureOfEntity+"</natureOfEntity>"+
											//"<!--&lt;W8_Sign_Date>10/02/2014&lt;/W8_Sign_Date-->"+											
											"<typeOfEntity>"+typeOfEntity+"</typeOfEntity>"+
											"<FATCAStatus>"+FATCAStatus+"</FATCAStatus>"+
											"<documentCollected>"+documentCollected+"</documentCollected>"+
											"<dateOfDocument>"+dateOfDocument+"</dateOfDocument>"+
											"<idntfctnNumRequired>"+idntfctnNumRequired+"</idntfctnNumRequired>"+
											"<idntfctnNumber>"+idntfctnNumber+"</idntfctnNumber>"+
											"<customerFATCAClsfctnDate>"+customerFATCAClsfctnDate+"</customerFATCAClsfctnDate>"+											
											"</FATCA_NI_Details>";					
				}								
			}
			else if (customerClsfctn.equalsIgnoreCase("Counter Party"))
			{
				customerInformation = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
										 "<FATCA_NI_Details>"+
											"<customerClsfctn>"+customerClsfctn+"</customerClsfctn>"+
											"<customerClsfctnDate>"+customerClsfctnDate+"</customerClsfctnDate>"+
											"<natureOfEntity>"+natureOfEntity+"</natureOfEntity>"+
											//"<!--&lt;W8_Sign_Date>10/02/2014&lt;/W8_Sign_Date-->"+											
											"<typeOfEntity>"+typeOfEntity+"</typeOfEntity>"+
											"<FATCAStatus>"+FATCAStatus+"</FATCAStatus>"+
											"<documentCollected>"+documentCollected+"</documentCollected>"+
											"<dateOfDocument>"+dateOfDocument+"</dateOfDocument>"+
											"<idntfctnNumRequired>"+idntfctnNumRequired+"</idntfctnNumRequired>"+
											"<idntfctnNumber>"+idntfctnNumber+"</idntfctnNumber>"+
											"<customerFATCAClsfctnDate>"+customerFATCAClsfctnDate+"</customerFATCAClsfctnDate>"+											
											"</FATCA_NI_Details>";
				
				LogGEN.writeTrace("COB_Log","customerInformation (Counter Party..) : " + customerInformation);
			}
		}		
		
		LogGEN.writeTrace("COB_Log", "WorkitemNo : " + xmlDataParser.getValueOf("WIName"));

		String WIName = xmlDataParser.getValueOf("WIName");
		LogGEN.writeTrace("COB_Log", "WIName : " + WIName);
		
		String customerId = xmlDataParser.getValueOf("mod:customerId");
		LogGEN.writeTrace("COB_Log", "customerId : " + customerId);	
		
		String maintenanceOption = xmlDataParser.getValueOf("mod:maintenanceOption");
		LogGEN.writeTrace("COB_Log", "maintenanceOption : " + maintenanceOption);
		
		
		ModCustMDMPubInfoStub stub = null;
		try
		{
			LogGEN.writeTrace("COB_Log", "making stub object for call : saveFATCADetailsStatus");
			stub = new ModCustMDMPubInfoStub(wsdlLocation);
		}
		catch (AxisFault e)
		{
			LogGEN.writeTrace("COB_Log","Exception while making object of saveFATCADetailsStatus(wsdlLocation)");
			e.printStackTrace();
		}

		// Header Object
	//	Header header = new Header();
		HeaderType headerType = new HeaderType();

		// Request Objects
		//ModCustMDMPubInfoReq req = new ModCustMDMPubInfoReq();
		ModCustMDMPubInfoReq_type0 reqType = new ModCustMDMPubInfoReq_type0();
		ModCustMDMPubInfoReqMsg reqMessage = new ModCustMDMPubInfoReqMsg();
		
		// Response Objects
	//	ModCustMDMPubInfoRes res = new ModCustMDMPubInfoRes();
		ModCustMDMPubInfoRes_type0 resType = new ModCustMDMPubInfoRes_type0();
		ModCustMDMPubInfoResMsg resMessage = new ModCustMDMPubInfoResMsg();
		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModCustMDMPubInfo");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setCorrelationID("");
		headerType.setSysRefNumber(getUniqueNo());
		headerType.setSenderID(webHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		//headerType.setRepTimeStamp(sDate);
		headerType.setUsername("WMS"); // It should be BPM logged in user
		
		reqMessage.setHeader(headerType);
		
		LogGEN.writeTrace("COB_Log", "customerInformation... : " + customerInformation);

		reqType.setCustomerId(customerId);
		
		if(custType.equalsIgnoreCase("Individual"))
		{
			reqType.setCustomerInfoType("FATCADetails");
			
		}
		else 
		{
			reqType.setCustomerInfoType("FATCA_NI_Details");
		}		
		
		reqType.setMaintenanceOption(maintenanceOption);
		
		reqType.setCustomerInformation(customerInformation);
		
		reqMessage.setModCustMDMPubInfoReq(reqType);		
		
		try
		{
			LogGEN.writeTrace("COB_Log", "calling webservice for call : saveFATCADetailsStatus");
			LogGEN.writeTrace("COB_Log",stub.getinputXML(reqMessage));
			LogGEN.writeTrace("COB_Log","before calling new");
			resMessage = stub.modCustMDMPubInfo_Oper(reqMessage);
			LogGEN.writeTrace("COB_Log","after calling new");
			LogGEN.writeTrace("COB_Log",stub.resEidaMsg);
			LogGEN.writeTrace("COB_Log","resMesage for call saveFATCADetailsStatus: " + resMessage);
		}
		catch (RemoteException e)
		{
			LogGEN.writeTrace("COB_Log", "In catch block for call : saveFATCADetailsStatus"+e.getMessage());
			e.printStackTrace();
		}	
		finally
		{
			LogGEN.writeTrace("COB_Log", "In finally block for call : saveFATCADetailsStatus");
			LogGEN.writeTrace("COB_Log","resMesage for call saveFATCADetailsStatus: " + resMessage);
			LogGEN.writeTrace("COB_Log","resMesage for call saveFATCADetailsStatus: " + stub.resEidaMsg);
		}		
		
		headerType = resMessage.getHeader();
		resType = resMessage.getModCustMDMPubInfoRes();
		
		outputxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + 
							"<ns0:ModCustMDMPubInfoResMsg>"+
								"<ns1:header>" +
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
							            "<ns1:credentials>"+headerType.getCredentials()+"</ns1:credentials/>"+
							            "<ns1:returnCode>"+headerType.getReturnCode()+"</ns1:returnCode>"+
							            "<ns1:errorDescription>"+headerType.getErrorDescription()+"</ns1:errorDescription>"+
							            "<ns1:errorDetail>"+headerType.getErrorDetail()+"</ns1:errorDetail>"+
							     "</ns1:header>"+
							     "<ns0:ModCustMDMPubInfoRes>"+       
									     "<ns0:customerId>"+resType.getCustomerId()+"</ns0:customerId>"+
								            "<ns0:customerInfoType>"+resType.getCustomerInfoType()+"</ns0:customerInfoType>"+
								            "<ns0:maintenanceOption>"+resType.getMaintenanceOption()+"</ns0:maintenanceOption>"+
								            "<ns0:customerInformation><![CDATA["+resType.getCustomerInformation()+"]]></ns0:customerInformation>"+  
								            "<ns0:status>"+resType.getStatus()+"</ns0:status>"+
								            "<ns0:reason>"+resType.getReason()+"</ns0:reason>"+
						         "</ns0:ModCustMDMPubInfoRes>"+
		 				"</ns0:ModCustMDMPubInfoResMsg>";		
		
		
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
			    String sOrg_Output_Add_Cust=stub.resEidaMsg;
			    String inputXml=stub.getinputXML(reqMessage);
			    String winame=xmlDataParser.getValueOf("WIName");
				String Query="insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber=''),'SaveFATCADetails_COB',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
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
		
		
		
		LogGEN.writeTrace("COB_Log","outputxml for call saveFATCADetailsStatus: " + outputxml);		
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
		LogGEN.writeTrace("COB_Log", "uniqueNo : " + uniqueNo);
		return uniqueNo;
	}
}
