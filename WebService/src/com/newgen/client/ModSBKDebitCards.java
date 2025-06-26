/*

---------------------------------------------------------------------------------------------------------
 
                                          NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 
Group                                     : Application Project 2
 
Project/Product                           : ADCB
 
Application                               : NGFUSER
 
Module                                    : AO
 
File Name                                 : ModSBKDebitCards.java
 
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
import com.newgen.stub.ModSBKDebitCardsStub;
import com.newgen.stub.ModSBKDebitCardsStub.AddDebitCardInfoReq_type0;
import com.newgen.stub.ModSBKDebitCardsStub.AddDebitCardInfoReqMsg;
import com.newgen.stub.ModSBKDebitCardsStub.AddDebitCardInfoRes_type0;
import com.newgen.stub.ModSBKDebitCardsStub.AddDebitCardInfoResMsg;
import com.newgen.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusReq_type0;
import com.newgen.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusRes_type0;
import com.newgen.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusReqMsg;
import com.newgen.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusResMsg;
import com.newgen.stub.ModSBKDebitCardsStub.HeaderType;
import com.newgen.client.WebServiceHandler;
import com.newgen.client.XMLParser;

public class ModSBKDebitCards extends WebServiceHandler {
	
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
	 
	//Date Modified                : 25/01/2016          
	//Author                       : Nishant Parmar          
	//Description                  : To obtain added debit card details and modified debit card print status details from ModSBKDebitCards web service     
	 
	//**
	 	
	
	@SuppressWarnings("finally")
	public String ModifySBKDebitCards(String sInputXML)
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
			sHandler.readCabProperty("ModSBKDebitCards");				
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
			header_req.setServiceName("ModSBKDebitCards");
			header_req.setVersionNo("1.0");
			header_req.setServiceAction("Modify");
			header_req.setSysRefNumber(sRef_no);
			header_req.setSenderID(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			header_req.setReqTimeStamp(sDate);
			header_req.setUsername(sCustomerID);
			header_req.setCredentials(loggedinuser);
			
			LogGEN.writeTrace("Log", "***Instantiating the ModSBKDebitCardsStub object***");
			//Instantiating the Stub object: 
			ModSBKDebitCardsStub stub = new ModSBKDebitCardsStub(sWSDLPath);
			LogGEN.writeTrace("Log", "***ModSBKDebitCardsStub object Instantiated***");
			
			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			if(sCallType.equalsIgnoreCase("Base24")){				
				String sDC_no = xmlParser.getValueOf("DebitCardNumber");
				LogGEN.writeTrace("Log", "***Inside Base24***");
				LogGEN.writeTrace("Log", "#DebitCardNumber# = "+sDC_no);
				
				AddDebitCardInfoReqMsg add_request = new AddDebitCardInfoReqMsg();			
				AddDebitCardInfoReq_type0 add_req = new AddDebitCardInfoReq_type0();
				add_req.setDebitCardNumber(sDC_no);
				add_request.setHeader(header_req);
				add_request.setAddDebitCardInfoReq(add_req);
				
			    	LogGEN.writeTrace("Log", "***calling getInputXml_AddCard***");
				//xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_AddCard(add_request);
			    	LogGEN.writeTrace("Log", "***Returned from getInputXml_AddCard***");
			    
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    	LogGEN.writeTrace("Log", "***TimeOut: set***");
			    	
			    AddDebitCardInfoResMsg response = stub.addDebitCardInfo_Oper(add_request);
			    	LogGEN.writeTrace("Log", "***Creating response reference of addDebitCardInfo_Oper Operation***");
			    	
			    AddDebitCardInfoRes_type0 res = response.getAddDebitCardInfoRes();
			    	LogGEN.writeTrace("Log", "***Calling getAddDebitCardInfoRes from the response reference***");

			    sOrgnlOutput = stub.resMsgAddDebitCard;
			    	LogGEN.writeTrace("Log", "***Getting resMsgAddDebitCard***");
			    
				HeaderType header_resp = response.getHeader();
					LogGEN.writeTrace("Log", "***Getting Header of response reference***");
					
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){					
					LogGEN.writeTrace("Log", "Successful Result");
					LogGEN.writeTrace("Log", "***Writing Base24 Response sOutput***");
					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>Base24</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<debitCardNumber>"+res.getDebitCardNumber()+"</debitCardNumber>"+
					"<status>"+res.getStatus()+"</status>"+
					"<reason>"+res.getReason()+"</reason>"+
					"</Output>";

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);		    
				}

				else{					
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Base24</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info. (Base24)</td></Output>";
				}
			}
			else if(sCallType.equalsIgnoreCase("ModDebitCardPrintStatus")){				
				String sDC_no = xmlParser.getValueOf("DebitCardNumber");
				String sPrint_status = xmlParser.getValueOf("PrintStatus");	
				LogGEN.writeTrace("Log", "***Inside ModDebitCardPrintStatus***");				
				LogGEN.writeTrace("Log", "#DebitCardNumber# = "+sDC_no);
				LogGEN.writeTrace("Log", "#PrintStatus# = "+sPrint_status);
				
				ModDebitCardPrintStatusReqMsg mod_request = new ModDebitCardPrintStatusReqMsg();
				ModDebitCardPrintStatusReq_type0 mod_req = new ModDebitCardPrintStatusReq_type0();
				mod_req.setDebitCardNumber(sDC_no);
				mod_req.setPrintStatus(sPrint_status);
				mod_request.setHeader(header_req);
				mod_request.setModDebitCardPrintStatusReq(mod_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml_ModPrntSt(mod_request);

				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				
			    ModDebitCardPrintStatusResMsg response = stub.modDebitCardPrintStatus_Oper(mod_request);
			    ModDebitCardPrintStatusRes_type0 res = response.getModDebitCardPrintStatusRes();

			    sOrgnlOutput = stub.resModDCPrintStatus;
			    
				HeaderType header_resp = response.getHeader();
				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();

				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("Log", "Successful Result");

					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
					"<Option>ModDebitCardPrintStatus</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<debitCardNumber>"+res.getDebitCardNumber()+"</debitCardNumber>"+
					"<printStatus>"+res.getPrintStatus()+"</printStatus>"+
					"<status>"+res.getStatus()+"</status>"+
					"<reason>"+res.getReason()+"</reason>"+
					"</Output>";				    	

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);						
				}
				else{					
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModDebitCardPrintStatus</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.(ModDebitCardPrintStatus)</td></Output>";
				}
			}
		}
		catch(Exception e){
			LogGEN.writeTrace("Log", "***Entered catch block of raised exception***");
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
				LogGEN.writeTrace("Log", "***inputXML***");
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
						 LogGEN.writeTrace("Log", "*****Executing Query : ModSBKDebitCards*****");
						 con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
						 LogGEN.writeTrace("Log", "*****Query Executed : ModSBKDebitCards*****");
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
			LogGEN.writeTrace("Log","finally :" + sOutput);
			return sOutput;			
		}		
		
	}

}
