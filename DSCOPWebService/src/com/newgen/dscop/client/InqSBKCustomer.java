/*

---------------------------------------------------------------------------------------------------------

NEWGEN SOFTWARE TECHNOLOGIES LIMITED

Group                                     : Application Project 2

Project/Product                           : ADCB

Application                               : NGFUSER

Module                                    : AO

File Name                                 : InqSBKCustomer.java

Author                                    : Gaurav Berry

Date (DD/MM/YYYY)                         : 25/01/2016

-------------------------------------------------------------------------------------------------------

CHANGE HISTORY

-------------------------------------------------------------------------------------------------------

Problem No/CR No   Change Date   Changed By    Change Description

------------------------------------------------------------------------------------------------------



-----------------------------------------------------------------------------------------------------*/

package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqSBKCustomerStub;
import com.newgen.dscop.stub.InqSBKCustomerStub.Activities_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.Chqueueleaves_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.Chqueueleavesdetails_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReqMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsReq_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsResMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetChqLeavesIssueAcctsRes_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReqMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoReq_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoResMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetCustomerInfoRes_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReqMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlReq_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlResMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustActivitiesDtlRes_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReqMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoReq_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoResMsg;
import com.newgen.dscop.stub.InqSBKCustomerStub.GetKioskCustEIDAInfoRes_type0;
import com.newgen.dscop.stub.InqSBKCustomerStub.HeaderType;

public class InqSBKCustomer extends DSCOPServiceHandler {

	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String sOrgnlOutput = "";

	public String SBKCustomer(String sInputXML)
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called SBKCustomer");
		LogGEN.writeTrace("CBG_Log", "inputXML SBKCustomer: " );
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sRepTimeSt = "";
		DSCOPServiceHandler sHandler = new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try
		{
			//Reading params from Config.properties file:
			//sHandler.readCabProperty("InqSBKCustomer");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqSBKCustomer");
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqSBKCustomer TIME_OUT: "+lTimeOut);

			//Parsing inputXML to retrieve tag values: 
			String sCustomerID = xmlParser.getValueOf("CUST_ID");
			String sRef_no = xmlParser.getValueOf("REF_NO");
			String sCallType = xmlParser.getValueOf("ServiceName");

			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);

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

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){					
					LogGEN.writeTrace("CBG_Log", "ChqLeavesIssue : Successful Result");

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
					sOutput += "</Chqueueleavesdetails></Chqueueleaves></Output>";
				}

				else{					
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ChqLeavesIssue</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve ChqueueLeaves.</td></Output>";
				}
			}
			else if(sCallType.equalsIgnoreCase("CustEIDAInfo")){				
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

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("CBG_Log", "CustEIDAInfo : Successful Result");
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
				}
				else{					
					LogGEN.writeTrace("CBG_Log", "Failed");
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

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("CBG_Log", "CustomerInfo : Successful Result");

					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>CustomerInfo</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							
							"<eidaNo>"+res.getEidaNo()+"</eidaNo>"+
							"<customerNamePrefix>"+res.getCustomerNamePrefix()+"</customerNamePrefix>"+
							"<customerFullName>"+res.getCustomerFullName()+"</customerFullName>"+
							"<customerShortName>"+res.getCustomerShortName()+"</customerShortName>"+
							"<customerId>"+res.getCustomerId()+"</customerId>"+
							"<customerDOB>"+res.getCustomerDOB()+"</customerDOB>"+
							"<customerSegment>"+res.getCustomerSegment()+"</customerSegment>"+
							"<customerSegmentDesc>"+res.getCustomerSegmentDesc()+"</customerSegmentDesc>"+
							"<customerType>"+res.getCustomerType()+"</customerType>"+
							"<customerPSPTNo>"+res.getCustomerPSPTNo()+"</customerPSPTNo>"+
							"<customerNationality>"+res.getCustomerNationality()+"</customerNationality>"+
							"<customerMotherName>"+res.getCustomerMotherName()+"</customerMotherName>"+
							"<customerMobileNumber>"+res.getCustomerMobileNumber()+"</customerMobileNumber>"+
							"<customerEmail>"+res.getCustomerEmail()+"</customerEmail>"+
							"</Output>";				    	
				}
				else{					
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>CustomerInfo</Option><returnCode>"
							+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><errorDetail>"+sErrorDetail+"</errorDetail><td>Unable to retrieve Customer Info.</td></Output>";
				}
			}
			else if(sCallType.equalsIgnoreCase("KioskCustActivities")){				
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

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Response Timestamp:---"+sRepTimeSt);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("CBG_Log", "CustomerInfo : Successful Result");
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
						sOutput += "<activity>"+ activities[i].getActivity() +"</activity>"+
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
					sOutput += "</activities></Output>";				    	
				}
				else{					
					LogGEN.writeTrace("CBG_Log", sCallType + " Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>KioskCustActivities</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve KioskCustActivities.</td></Output>";
				}
			}
		}
		catch(Exception e){
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqSBKCustomer</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("CBG_Log","inqSBKCoustmer outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqSBKCustomer</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")){
				Status="Success";
			}
			else{
				Status="Failure";
			}

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String winame=xmlParser.getValueOf("WiName");
			String sessionID= xmlParser.getValueOf("SessionId");
			String call_type=xmlParser.getValueOf("DSCOPCallType");
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

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log", " Add  Query : finally :" + Query);
			try {
				LogGEN.writeTrace("CBG_Log", "*****Executing Query : InqSBKCustomer*****");
				con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, xmlInput.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
				LogGEN.writeTrace("CBG_Log", "*****Query Executed : InqSBKCustomer*****");
			} catch (Exception e2) {
				e2.getMessage();
			}
		}		
		return sOutput;			
	}
}