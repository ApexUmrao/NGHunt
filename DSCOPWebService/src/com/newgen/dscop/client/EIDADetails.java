/*

---------------------------------------------------------------------------------------------------------
 
                                          NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 
Group                                     : Application Project 2
 
Project/Product                           : ADCB
 
Application                               : NGFUSER
 
Module                                    : AO
 
File Name                                 : EIDADetails.java
 
Author                                    : Nishant Parmar
 
Date (DD/MM/YYYY)                         : 27/01/2016
 
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
import com.newgen.dscop.stub.EIDADetailsStub;
import com.newgen.dscop.stub.EIDADetailsStub.EIDADetailsReqMsg;
import com.newgen.dscop.stub.EIDADetailsStub.EIDADetailsReq_type0;
import com.newgen.dscop.stub.EIDADetailsStub.EIDADetailsResMsg;
import com.newgen.dscop.stub.EIDADetailsStub.EIDADetailsRes_type0;
import com.newgen.dscop.stub.EIDADetailsStub.HeaderType;

public class EIDADetails extends DSCOPServiceHandler {
	
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String sOrgnlOutput = "";


	@SuppressWarnings("finally")
	public String fetchEIDADetails(String sInputXML)
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called DebitEnquiry");
		LogGEN.writeTrace("CBG_Log", "EIDADetails inputXml: ");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		try
		{
			//Reading params from Config.properties file:
			//sHandler.readCabProperty("EIDADetails");				
			
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("EIDADetails");
			LogGEN.writeTrace("CBG_Log", "EIDADetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "EIDADetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "EIDADetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "EIDADetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "EIDADetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "EIDADetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "EIDADetails TIME_OUT: "+lTimeOut);


			//Parsing inputXML to retrieve tag values: 
			String sCustomerID = xmlParser.getValueOf("CUST_ID");
			String sRef_no = xmlParser.getValueOf("REF_NO");
			String sCallType = xmlParser.getValueOf("DSCOPCallType");

			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
			
			//Setting Request Header params:
			HeaderType header_req = new HeaderType();
			header_req.setUsecaseID("1234");
			header_req.setServiceName("EIDADetails");
			header_req.setVersionNo("1.0");
			header_req.setServiceAction("Retrieve");
			header_req.setSysRefNumber(sRef_no);
			header_req.setSenderID(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			header_req.setReqTimeStamp(sDate);
			header_req.setUsername(sCustomerID);
			header_req.setCredentials(loggedinuser);	
			
			//Instantiating the Stub object: 
			EIDADetailsStub stub = new EIDADetailsStub(sWSDLPath);
		    
			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			if(sCallType.equalsIgnoreCase("EIDA_Details")){
				
				String sLodg_Ref_no = xmlParser.getValueOf("LODGMENT_REF_NO");
				
				EIDADetailsReqMsg eida_request = new EIDADetailsReqMsg();			
				EIDADetailsReq_type0 eida_req = new EIDADetailsReq_type0();
				eida_req.setLodgmentRefNumber(sLodg_Ref_no);

				eida_request.setHeader(header_req);
				eida_request.setEIDADetailsReq(eida_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_EIDA(eida_request);
			    
			    EIDADetailsResMsg response = stub.eIDADetails_Oper(eida_request);
			    EIDADetailsRes_type0 res = response.getEIDADetailsRes();
				
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    sOrgnlOutput = stub.resEIDAMsg;
			    
//			    LogGEN.writeTrace("CBG_Log", "EIDADetails sOrgnlOutput---"+sOrgnlOutput);
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("CBG_Log", "Successful Result");
					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>EIDA_Details</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<EIDANumber>"+res.getEIDANumber()+"</EIDANumber>"+
					"<DOB>"+res.getDOB()+"</DOB>"+
					"<gender>"+res.getGender()+"</gender>"+
					"<husbandID>"+res.getHusbandID()+"</husbandID>"+
					"<issueDate>"+res.getIssueDate()+"</issueDate>"+
					"<expiryDate>"+res.getExpiryDate()+"</expiryDate>"+
					"<maritalStatus>"+res.getMaritalStatus()+"</maritalStatus>"+
					"<motherName>"+res.getMotherName()+"</motherName>"+
					"<title>"+res.getTitle()+"</title>"+
					"<Name>"+res.getName()+"</Name>"+
					"<nationality>"+res.getNationality()+"</nationality>"+
					"<occupation>"+res.getOccupation()+"</occupation>"+
					"<placeOfBirth>"+res.getPlaceOfBirth()+"</placeOfBirth>"+
					"<passportNo>"+res.getPassportNo()+"</passportNo>"+
					"<passportType>"+res.getPassportType()+"</passportType>"+
					"<passportCountry>"+res.getPassportCountry()+"</passportCountry>"+
					"<passportExpiryDate>"+res.getPassportExpiryDate()+"</passportExpiryDate>"+
					"<residentNo>"+res.getResidentNo()+"</residentNo>"+
					"<residentType>"+res.getResidentType()+"</residentType>"+
					"<residentExpiry>"+res.getResidentExpiry()+"</residentExpiry>"+

					"</Output>";

//					LogGEN.writeTrace("CBG_Log", "EidaDetails Output XML--- "+sOutput);		    
				}

				else{					
					LogGEN.writeTrace("CBG_Log", "EidaDetails Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>EIDA_Details</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
				}
			}

		}
		catch(Exception e){
			LogGEN.writeTrace("CBG_Log", "EidaDetails Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "EidaDetails Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>EIDA_Details</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("CBG_Log","EidaDetails outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>EIDA_Details</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve EIDA info.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")){
				Status="Success";
			}
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
				String winame=xmlParser.getValueOf("WiName");
				String sessionID= xmlParser.getValueOf("SessionId");
				String call_type=xmlParser.getValueOf("DSCOPCallType");
				sCabinet=xmlParser.getValueOf("EngineName");
				dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				 try {
					 dbpass=AESEncryption.decrypt(dbpass);
				 }
				 catch(Exception e){
					 
				 }
				 DBConnection con=new DBConnection();

					 String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("CBG_Log", " Add  Query : finally :" + Query);
					 LogGEN.writeTrace("CBG_Log", "sOrg_Output : finally :" + sOrgnlOutput);
					 try {
						 con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
//			LogGEN.writeTrace("CBG_Log","finally :" + sOutput);
			return sOutput;			
		}		
		
	}

}

