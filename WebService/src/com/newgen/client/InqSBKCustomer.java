/*

---------------------------------------------------------------------------------------------------------
 
                                          NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 
Group                                     : Application Project 2
 
Project/Product                           : ADCB
 
Application                               : NGFUSER
 
Module                                    : AO
 
File Name                                 : InqSBKCustomer.java
 
Author                                    : Nishant Parmar
 
Date (DD/MM/YYYY)                         : 25/01/2016
 
-------------------------------------------------------------------------------------------------------
 
CHANGE HISTORY
 
-------------------------------------------------------------------------------------------------------
 
Problem No/CR No   Change Date   Changed By    Change Description
 
------------------------------------------------------------------------------------------------------
 
 
 
-----------------------------------------------------------------------------------------------------*/

package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqSBKCustomerStub;
import com.newgen.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg;
import com.newgen.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReq_type0;
import com.newgen.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg;
import com.newgen.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsRes_type0;
import com.newgen.stub.InqSBKCustomerStub.Chqueueleavesdetails_type0;
import com.newgen.stub.InqSBKCustomerStub.Chqueueleaves_type0;

import com.newgen.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg;
import com.newgen.stub.InqSBKCustomerStub.GetCustomerInfoReq_type0;
import com.newgen.stub.InqSBKCustomerStub.GetCustomerInfoResMsg;
import com.newgen.stub.InqSBKCustomerStub.GetCustomerInfoRes_type0;

import com.newgen.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg;
import com.newgen.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReq_type0;
import com.newgen.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg;
import com.newgen.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoRes_type0;

import com.newgen.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg;
import com.newgen.stub.InqSBKCustomerStub.Activities_type0;
import com.newgen.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReq_type0;
import com.newgen.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg;
import com.newgen.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlRes_type0;

import com.newgen.stub.InqSBKCustomerStub.HeaderType;
import com.newgen.client.WebServiceHandler;
import com.newgen.client.XMLParser;

public class InqSBKCustomer extends WebServiceHandler {
	
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String sOrgnlOutput = "";

	//**********************************************************************************//
	 
	 
	 
	//NEWGEN SOFTWARE TECHNOLOGIES LIMITED
	 
	 
	 
	//Group                        : Application Project 2
	//Project                      : ADCB
	 
	//Date Modified                : 23/02/2016          
	//Author                       : Nishant Parmar          
	//Description                  : To obtain Chqueue Leaves, Kiosk Customer EIDA Info and Customer Info details from InqSBKCustomer web service     
	 
	//**
	 	
	
	@SuppressWarnings("finally")
	public String SBKCustomer(String sInputXML)
	{
		LogGEN.writeTrace("Log", "Fuction called SBKCustomer");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sRepTimeSt = "";
		WebServiceHandler sHandler = new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		try
		{
			//Reading params from Config.properties file:
			sHandler.readCabProperty("InqSBKCustomer");				
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			//Writing into Logs:
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));

			//Parsing inputXML to retrieve tag values: 
			String sCustomerID = xmlParser.getValueOf("CUST_ID");
			String sRef_no = xmlParser.getValueOf("REF_NO");
			String sCallType = xmlParser.getValueOf("Calltype");

			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			
			//Setting Request Header params:
			HeaderType header_req = new HeaderType();
			header_req.setUsecaseID("1234");
			header_req.setServiceName("InqSBKCustomer");
			header_req.setVersionNo("1.0");
			header_req.setServiceAction("Inquiry");
			header_req.setSysRefNumber(sRef_no);
			header_req.setSenderID(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			header_req.setReqTimeStamp(sDate);
			header_req.setUsername(sCustomerID);
			header_req.setCredentials(loggedinuser);	
			
			//Instantiating the Stub object: 
			InqSBKCustomerStub stub = new InqSBKCustomerStub(sWSDLPath);
		    
			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			if(sCallType.equalsIgnoreCase("ChqLeavesIssue")){				
//				String sCust_ID = xmlParser.getValueOf("CUST_ID");
				
				GetChqLeavesIssueAcctsReqMsg chq_request = new GetChqLeavesIssueAcctsReqMsg();			
				GetChqLeavesIssueAcctsReq_type0 chq_req = new GetChqLeavesIssueAcctsReq_type0();
				chq_req.setCustomerId(sCustomerID);
				chq_request.setHeader(header_req);
				chq_request.setGetChqLeavesIssueAcctsReq(chq_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_chqLeaves(chq_request);
				
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    
			    GetChqLeavesIssueAcctsResMsg response = stub.getChqLeavesIssueAccts_Oper(chq_request);
			    GetChqLeavesIssueAcctsRes_type0 res = response.getGetChqLeavesIssueAcctsRes();

			    sOrgnlOutput = stub.resChqLeaves;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();
				sRepTimeSt = header_resp.getRepTimeStamp();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){					
					LogGEN.writeTrace("Log", "ChqLeavesIssue : Successful Result");
					
					Chqueueleavesdetails_type0 chqueueLeavesDetails = new Chqueueleavesdetails_type0();
					Chqueueleaves_type0 chqueueLeaves[] = chqueueLeavesDetails.getChqueueleaves();

					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>ChqLeavesIssue</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<customerId>"+res.getCustomerId()+"</customerId>"+
					"<Chqueueleavesdetails>"+
					"<Chqueueleaves>";
					//Iterate over chqueueLeaves[] to fetch required debitCards info:
					for(int i = 0; i < chqueueLeaves.length; i++){
						sOutput += 
							"<accountNumber>"+ chqueueLeaves[i].getAccountNumber() +"</accountNumber>"+
							"<accountTitle>"+ chqueueLeaves[i].getAccountTitle() +"</accountTitle>"+
							"<chequeBookEOBStatus>"+ chqueueLeaves[i].getChequeBookEOBStatus() +"</chequeBookEOBStatus>"+	
							"<accountStatus>"+ chqueueLeaves[i].getAccountStatus() +"</accountStatus>"+	
							"<accountProductCode>"+ chqueueLeaves[i].getAccountProductCode() +"</accountProductCode>"+	
							"<productDescription>"+ chqueueLeaves[i].getProductDescription() +"</productDescription>"+	
							"<branchCode>"+ chqueueLeaves[i].getBranchCode() +"</branchCode>"+	
							"<accountRelationship>"+ chqueueLeaves[i].getAccountRelationship() +"</accountRelationship>";	
					}
					sOutput += "</Chqueueleavesdetails></Chqueueleaves>"+	
					"</Output>";

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);		    
				}

				else{					
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ChqLeavesIssue</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve ChqueueLeaves.</td></Output>";
				}
			}
			
			else if(sCallType.equalsIgnoreCase("CustEIDAInfo")){				
//				String sCust_ID = xmlParser.getValueOf("CUST_ID");
				String sEIDANum = xmlParser.getValueOf("EIDANum");				
				
				GetKioskCustEIDAInfoReqMsg eida_request = new GetKioskCustEIDAInfoReqMsg();
				GetKioskCustEIDAInfoReq_type0 eida_req = new GetKioskCustEIDAInfoReq_type0();
				eida_req.setCustomerId(sCustomerID);
				eida_req.setEidaNo(sEIDANum);
				eida_request.setHeader(header_req);
				eida_request.setGetKioskCustEIDAInfoReq(eida_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_KioskEIDA(eida_request);
			    
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    
			    GetKioskCustEIDAInfoResMsg response = stub.getKioskCustEIDAInfo_Oper(eida_request);
			    GetKioskCustEIDAInfoRes_type0 res = response.getGetKioskCustEIDAInfoRes();
							    
			    sOrgnlOutput = stub.resKioskEIDA;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();
				sRepTimeSt = header_resp.getRepTimeStamp();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("Log", "CustEIDAInfo : Successful Result");

					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>CustEIDAInfo</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<eidaNo>"+res.getEidaNo()+"</eidaNo>"+
					"<dateOfBirth>"+res.getDateOfBirth()+"</dateOfBirth>"+
					"<gender>"+res.getGender()+"</gender>"+
					"<husbandID>"+res.getHusbandId()+"</husbandID>"+
					"<idPhoto>"+res.getIdPhoto()+"</idPhoto>"+
					"<issueDate>"+res.getIssueDate()+"</issueDate>"+
					"<expiryDate>"+res.getExpiryDate()+"</expiryDate>"+
					"<maritalStatus>"+res.getMaritalStatus()+"</maritalStatus>"+
					"<motherName>"+res.getMotherName()+"</motherName>"+
					"<title>"+res.getTitle()+"</title>"+
					"<name>"+res.getName()+"</name>"+
					"<nationality>"+res.getNationality()+"</nationality>"+
					"<occupation>"+res.getOccupation()+"</occupation>"+
					"<placeOfBirth>"+res.getPlaceOfBirth()+"</placeOfBirth>"+
					"<passportNumber>"+res.getPassportNumber()+"</passportNumber>"+
					"<passportType>"+res.getPassportType()+"</passportType>"+
					"<passportIssuedCountry>"+res.getPassportIssuedCountry()+"</passportIssuedCountry>"+
					"<passportExpiryDate>"+res.getPassportExpiryDate()+"</passportExpiryDate>"+
					"<residentNo>"+res.getResidentNo()+"</residentNo>"+
					"<residentType>"+res.getResidentType()+"</residentType>"+
					"<residentExpiry>"+res.getResidentExpiry()+"</residentExpiry>"+
					"<passportIssueDate>"+res.getPassportIssueDate()+"</passportIssueDate>"+
					"<employerName>"+res.getEmployerName()+"</employerName>"+
					"<corrPoBox>"+res.getCorrPoBox()+"</corrPoBox>"+
					"<corrCountry>"+res.getCorrCountry()+"</corrCountry>"+
					"<corrState>"+res.getCorrState()+"</corrState>"+
					"<corrCity>"+res.getCorrCity()+"</corrCity>"+
					"<countryofResident>"+res.getCountryofResident()+"</countryofResident>"+
					"<countryofPerm>"+res.getCountryofPerm()+"</countryofPerm>"+
					"<telephoneResidence>"+res.getTelephoneResidence()+"</telephoneResidence>"+
					"<telMobile>"+res.getTelMobile()+"</telMobile>"+
					"<email>"+res.getEmail()+"</email>"+
					"</Output>";				    	

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);						
				}
				else{					
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CustEIDAInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve Kiosk EIDA info.</td></Output>";
				}
			}
			
			else if(sCallType.equalsIgnoreCase("CustomerInfo")){				
//				String sCust_ID = xmlParser.getValueOf("CUST_ID");
				String sEIDANum = xmlParser.getValueOf("EIDANum");				
				
				GetCustomerInfoReqMsg cust_request = new GetCustomerInfoReqMsg();
				GetCustomerInfoReq_type0 cust_req = new GetCustomerInfoReq_type0();
				cust_req.setCustomerId(sCustomerID);
				cust_req.setEidaNo(sEIDANum);
				cust_request.setHeader(header_req);
				cust_request.setGetCustomerInfoReq(cust_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_CustInfo(cust_request);
								
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    
			    GetCustomerInfoResMsg response = stub.getCustomerInfo_Oper(cust_request);
			    GetCustomerInfoRes_type0 res = response.getGetCustomerInfoRes();
				
			    sOrgnlOutput = stub.resCustInfo;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();
				sRepTimeSt = header_resp.getRepTimeStamp();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("Log", "CustomerInfo : Successful Result");

					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>CustomerInfo</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<customerId>"+res.getCustomerId()+"</customerId>"+
					"<eidaNo>"+res.getEidaNo()+"</eidaNo>"+
					"<customerNamePrefix>"+res.getCustomerNamePrefix()+"</customerNamePrefix>"+
					"<customerFullName>"+res.getCustomerFullName()+"</customerFullName>"+
					"<customerShortName>"+res.getCustomerShortName()+"</customerShortName>"+
					"<customerSegment>"+res.getCustomerSegment()+"</customerSegment>"+
					"<customerSegmentDesc>"+res.getCustomerSegmentDesc()+"</customerSegmentDesc>"+
					"<customerType>"+res.getCustomerType()+"</customerType>"+
					"<customerDOB>"+res.getCustomerDOB()+"</customerDOB>"+
					"<customerPSPTNo>"+res.getCustomerPSPTNo()+"</customerPSPTNo>"+
					"<customerNationality>"+res.getCustomerNationality()+"</customerNationality>"+
					"<customerMotherName>"+res.getCustomerMotherName()+"</customerMotherName>"+
					"<customerMobileNumber>"+res.getCustomerMobileNumber()+"</customerMobileNumber>"+
					"<customerEmail>"+res.getCustomerEmail()+"</customerEmail>"+
					"</Output>";				    	

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);						
				}
				else{					
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CustomerInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve Customer Info.</td></Output>";
				}
			}
			
			else if(sCallType.equalsIgnoreCase("KioskCustActivities")){				
//				String sCust_ID = xmlParser.getValueOf("CUST_ID");
//				String sEIDANum = xmlParser.getValueOf("EIDANum");				
				String sFromDate = xmlParser.getValueOf("searchFromDate");				
				String sToDate = xmlParser.getValueOf("searchToDate");				
				
				GetKioskCustActivitiesDtlReqMsg detail_request = new GetKioskCustActivitiesDtlReqMsg();
				GetKioskCustActivitiesDtlReq_type0 dtl_req = new GetKioskCustActivitiesDtlReq_type0();
				dtl_req.setCustomerId(sCustomerID);
				dtl_req.setSearchFromDate(sFromDate);
				dtl_req.setSearchToDate(sToDate);
				detail_request.setHeader(header_req);
				detail_request.setGetKioskCustActivitiesDtlReq(dtl_req);
								
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_KioskActs(detail_request);
				
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    
			    GetKioskCustActivitiesDtlResMsg response = stub.getKioskCustActivitiesDtl_Oper(detail_request);
			    GetKioskCustActivitiesDtlRes_type0 res = response.getGetKioskCustActivitiesDtlRes();

			    sOrgnlOutput = stub.resKioskDtls;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();
				sRepTimeSt = header_resp.getRepTimeStamp();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("Log", "CustomerInfo : Successful Result");
					
					Activities_type0 activities[] = res.getActivities();
					
					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>KioskCustActivities</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<customerId>"+res.getCustomerId()+"</customerId>"+
					"<activities>";
					//Iterate over activities[] to fetch required debitCards info:
					for(int i = 0; i < activities.length; i++){
						sOutput += 
							"<activity>"+ activities[i].getActivity() +"</activity>"+
							"<authFlag>"+ activities[i].getAuthFlag() +"</authFlag>"+
							"<cline>"+ activities[i].getCline() +"</cline>"+	
							"<callTransferTo>"+ activities[i].getCallTransferTo() +"</callTransferTo>"+	
							"<createdBy>"+ activities[i].getCreatedBy() +"</createdBy>"+	
							"<customerCategory>"+ activities[i].getCustomerCategory() +"</customerCategory>"+	
							"<custType>"+ activities[i].getCustType() +"</custType>"+	
							"<description>"+ activities[i].getDescription() +"</description>"+
							"<driverID>"+ activities[i].getDriverID() +"</driverID>"+
							"<startDateTime>"+ activities[i].getStartDateTime() +"</startDateTime>"+
							"<endDateTime>"+ activities[i].getEndDateTime() +"</endDateTime>"+
							"<ivrRequest>"+ activities[i].getIvrRequest() +"</ivrRequest>"+
							"<owner>"+ activities[i].getOwner() +"</owner>"+
							"<status>"+ activities[i].getStatus() +"</status>"+
							"<type>"+ activities[i].getType() +"</type>"+
							"<wrapUpFlag>"+ activities[i].getWrapUpFlag() +"</wrapUpFlag>"+
							"<wrapUpIncomplete>"+ activities[i].getWrapUpIncomplete() +"</wrapUpIncomplete>"+
							"<wrapUpCategory>"+ activities[i].getWrapUpCategory() +"</wrapUpCategory>"+
							"<wrapUpSubCategory>"+ activities[i].getWrapUpSubCategory() +"</wrapUpSubCategory>";	
					}
					sOutput += "</activities>"+	
					"</Output>";				    	

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);						
				}
				else{					
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>KioskCustActivities</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve KioskCustActivities.</td></Output>";
				}
			}
			
		}
		catch(Exception e){
			LogGEN.writeTrace("Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqSBKCustomer</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqSBKCustomer</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")){
				Status="Success";
			}
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
				String winame=xmlParser.getValueOf("WiName");
				String sessionID= xmlParser.getValueOf("SessionId");
				String call_type=xmlParser.getValueOf("Calltype");
				sCabinet=xmlParser.getValueOf("EngineName");
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 try
				 {
					 dbpass=AESEncryption.decrypt(dbpass);
				 }
				 catch(Exception e)
				 {
					 
				 }
				 DBConnection con=new DBConnection();
				 	
					 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("Log", " Add  Query : finally :" + Query);
					 LogGEN.writeTrace("Log", "sOrg_Output : finally :" + sOrgnlOutput);
					 try {
						 LogGEN.writeTrace("Log", "*****Executing Query : InqSBKCustomer*****");
						 con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
						 LogGEN.writeTrace("Log", "*****Query Executed : InqSBKCustomer*****");
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
			LogGEN.writeTrace("Log","finally :" + sOutput);
			return sOutput;			
		}		
		
	}

}

