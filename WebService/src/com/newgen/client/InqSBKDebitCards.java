/*
 
---------------------------------------------------------------------------------------------------------
 
                                          NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 
Group                                     : Application Project 2
 
Project/Product                           : ADCB
 
Application                               : NGFUSER
 
Module                                    : AO
 
File Name                                 : InqSBKDebitCards.java
 
Author                                    : Nishant Parmar
 
Date (DD/MM/YYYY)                         : 22/01/2016
 
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
import com.newgen.stub.InqSBKDebitCardsStub;
import com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReqMsg;
import com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusReq_type0;
import com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusResMsg;
import com.newgen.stub.InqSBKDebitCardsStub.GetDebitCardPrintStatusRes_type0;
import com.newgen.stub.InqSBKDebitCardsStub.DebitCardDetails_type0;
import com.newgen.stub.InqSBKDebitCardsStub.DebitCards_type0;
import com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReqMsg;
import com.newgen.stub.InqSBKDebitCardsStub.GetKioskListReq_type0;
import com.newgen.stub.InqSBKDebitCardsStub.GetKioskListResMsg;
import com.newgen.stub.InqSBKDebitCardsStub.GetKioskListRes_type0;
import com.newgen.stub.InqSBKDebitCardsStub.HeaderType;
import com.newgen.stub.InqSBKDebitCardsStub.KioskDetails_type0;
import com.newgen.stub.InqSBKDebitCardsStub.Kiosks_type0;
import com.newgen.client.WebServiceHandler;
import com.newgen.client.XMLParser;

public class InqSBKDebitCards extends WebServiceHandler {
	
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String sOrgnlOutput = "";

	//**********************************************************************************//
	 
	 
	 
	//NEWGEN SOFTWARE TECHNOLOGIES LIMITED
	 
	 
	 
	//Group                        : Application Project 2
	//Project                      : ADCB
	 
	//Date Modified                : 22/01/2016          
	//Author                       : Nishant Parmar          
	//Description                  : To obtain debit card and kiosk list details from InqSBKDebitCards web service     
	 
	//**
	 	
	
	@SuppressWarnings("finally")
	public String SBKDebitEnquiry(String sInputXML)
	{
		LogGEN.writeTrace("Log", "Fuction called DebitEnquiry");
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		WebServiceHandler sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		try
		{
			//Reading params from Config.properties file:
			sHandler.readCabProperty("Debit_Enquiry");				
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
			header_req.setServiceName("InqSBKDebitCards");
			header_req.setVersionNo("1.0");
			header_req.setServiceAction("Inquiry");
			header_req.setSysRefNumber(sRef_no);
			header_req.setSenderID(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			header_req.setReqTimeStamp(sDate);
			header_req.setUsername(sCustomerID);
			header_req.setCredentials(loggedinuser);	
			
			//Instantiating the Stub object: 
			InqSBKDebitCardsStub stub = new InqSBKDebitCardsStub(sWSDLPath);
		    
			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			if(sCallType.equalsIgnoreCase("InqDebitCardPrintStatus")){
				String sDC_no = xmlParser.getValueOf("DebitCardNumber");
							
				GetDebitCardPrintStatusReqMsg debit_request = new GetDebitCardPrintStatusReqMsg();			
				GetDebitCardPrintStatusReq_type0 dc_req = new GetDebitCardPrintStatusReq_type0();
				dc_req.setCustomerId(sCustomerID);
				dc_req.setDebitCardNumber(sDC_no);
				debit_request.setHeader(header_req);
				debit_request.setGetDebitCardPrintStatusReq(dc_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_DebitCard(debit_request);
				
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    			    
				GetDebitCardPrintStatusResMsg response = stub.getDebitCardPrintStatus_Oper(debit_request);
				GetDebitCardPrintStatusRes_type0 res = response.getGetDebitCardPrintStatusRes();
				sOrgnlOutput = stub.resMsgDebitCard;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("Log", "Successful Result");
									
					DebitCardDetails_type0 debitCardDetails = res.getDebitCardDetails();
					DebitCards_type0 debitCards[] = debitCardDetails.getDebitCards();

					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>DebitCard_PrintStatus</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<customerId>"+res.getCustomerId()+"</customerId>"+
					"<debitCardNumber>"+res.getDebitCardNumber()+"</debitCardNumber>"+
					"<DebitCards>";
					//Iterate over debitCards[] to fetch required debitCards info:
					for(int i = 0; i < debitCards.length; i++){
						sOutput += 
							"<DebitCard>"+
							"<cardTypeDescription>"+ debitCards[i].getCardTypeDescription() +"</cardTypeDescription>"+
							"<debitProductGroup>"+ debitCards[i].getDebitProductGroup() +"</debitProductGroup>"+
							"<embossName>"+ debitCards[i].getEmbossName() +"</embossName>"+	
							"<primSuppFlag>"+ debitCards[i].getPrimSuppFlag() +"</primSuppFlag>"+	
							"<statusCode>"+ debitCards[i].getStatusCode() +"</statusCode>"+	
							"<statusDescription>"+ debitCards[i].getStatusDescription() +"</statusDescription>"+	
							"<statusChangeDate>"+ debitCards[i].getStatusChangeDate() +"</statusChangeDate>"+	
							"<statusChangeUser>"+ debitCards[i].getStatusChangeUser() +"</statusChangeUser>"+	
							"<expiryDate>"+ debitCards[i].getExpiryDate() +"</expiryDate>"+	
							"<issueDate>"+ debitCards[i].getIssueDate() +"</issueDate>"+	
							"<fourthLineEmbossing>"+ debitCards[i].getFourthLineEmbossing() +"</fourthLineEmbossing>"+	
							"<serviceCode>"+ debitCards[i].getServiceCode() +"</serviceCode>"+
							"</DebitCard>";
					}
					sOutput += "</DebitCards>"+	
					"</Output>";				    	

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);		    
				}

				else{				
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DebitCard_PrintStatus</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
				}
			}
			else if(sCallType.equalsIgnoreCase("DFCListFetch")){

				String sCardProdGrp = xmlParser.getValueOf("CardProductGroup");
				String sCustCategory = xmlParser.getValueOf("CustomerCategory");				
				
				GetKioskListReqMsg kiosk_request = new GetKioskListReqMsg();
				GetKioskListReq_type0 ks_req = new GetKioskListReq_type0();
				ks_req.setCardProductGroup(sCardProdGrp);
				ks_req.setCustomerCategory(sCustCategory);
				kiosk_request.setHeader(header_req);
				kiosk_request.setGetKioskListReq(ks_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_Kiosk(kiosk_request);
								
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    
			    GetKioskListResMsg response = stub.getKioskList_Oper(kiosk_request);
				GetKioskListRes_type0 res = response.getGetKioskListRes();
				
			    sOrgnlOutput = stub.resMsgKiosk;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("Log", "Successful Result");
					KioskDetails_type0 kioskDetails = res.getKioskDetails();
					int flag=0;
					try
					{
					//Kiosks_type0 kiosks[] = kioskDetails.getKiosks();
					 flag=0;
					}
					catch(Exception e)
					  {
					  flag=1;
					  }

					LogGEN.writeTrace("Log", "flag---"+flag);
					//Iterate over kiosks[] to fetch required Kiosks info:	
					
					if(flag==0)
						
					{   
						Kiosks_type0 kiosks[] = kioskDetails.getKiosks();
						
						sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>DFCListFetch</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<cardProductGroup>"+res.getCardProductGroup()+"</cardProductGroup>"+
						"<customerCategory>"+res.getCustomerCategory()+"</customerCategory>"+
						"<Kiosks>";
						
						try
						{
							
						
					for(int i = 0; i < kiosks.length; i++){
					
						sOutput += 
							"<Kiosk>"+
							"<KioskId>"+ kiosks[i].getKioskId() +"</KioskId>"+
							"<KioskDescription>"+ kiosks[i].getKioskDesc() +"</KioskDescription>"+
							"<KioskLocation>"+ kiosks[i].getKioskLocation() +"</KioskLocation>"+
							"</Kiosk>";
					}
					sOutput += "</Kiosks>"+	
					"</Output>";
						}
						catch(Exception ex)
						{
							flag=1;
						}
					LogGEN.writeTrace("Log", "flag success---"+flag);
					}
					
					if(flag==1)
					{
						
						sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>DFCListFetch</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<cardProductGroup>"+res.getCardProductGroup()+"</cardProductGroup>"+
						"<customerCategory>"+res.getCustomerCategory()+"</customerCategory>"+
						"</Output>";
						LogGEN.writeTrace("Log", "flag fail---"+flag);
					}

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);						
				}
				else{				
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DebitCard_PrintStatus</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
				}
			}
		}
		catch(Exception e){
			LogGEN.writeTrace("Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DebitCard_PrintStatus_Info</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DebitCard_PrintStatus_Info</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
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
						 LogGEN.writeTrace("Log", "*****Executing Query : InqSBKDebitCards*****");
						 con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
						 LogGEN.writeTrace("Log", "*****Query Executed : InqSBKDebitCards*****");
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
			LogGEN.writeTrace("Log","finally :" + sOutput);
			return sOutput;			
		}		
		
	}

}
