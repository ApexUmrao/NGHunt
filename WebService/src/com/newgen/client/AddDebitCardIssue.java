/*

---------------------------------------------------------------------------------------------------------
 
                                          NEWGEN SOFTWARE TECHNOLOGIES LIMITED
 
Group                                     : Application Project 2
 
Project/Product                           : ADCB
 
Application                               : NGFUSER
 
Module                                    : AO
 
File Name                                 : AddDebitCardIssue.java
 
Author                                    : Nishant Parmar
 
Date (DD/MM/YYYY)                         : 27/01/2016
 
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
import com.newgen.stub.AddDebitCardIssueStub;
import com.newgen.stub.AddDebitCardIssueStub.AddDebitCardIssueReqMsg;
import com.newgen.stub.AddDebitCardIssueStub.AddDebitCardIssueReq_type0;
import com.newgen.stub.AddDebitCardIssueStub.AddDebitCardIssueResMsg;
import com.newgen.stub.AddDebitCardIssueStub.AddDebitCardIssueRes_type0;
import com.newgen.stub.AddDebitCardIssueStub.HeaderType;
import com.newgen.client.WebServiceHandler;
import com.newgen.client.XMLParser;

public class AddDebitCardIssue extends WebServiceHandler {
	
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
	 
	//Date Modified                : 27/01/2016          
	//Author                       : Nishant Parmar          
	//Description                  : To obtain added debit card details from AddDebitCardIssue web service     
	 
	//**
	 	
	
	@SuppressWarnings("finally")
	public String AddDebitCards(String sInputXML)
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
			sHandler.readCabProperty("AddDebitCardIssue");				
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
			header_req.setServiceName("AddDebitCardIssue"); //changed by Nishant (14-04-2016)
			header_req.setVersionNo("1.0");
			header_req.setServiceAction("Addition");
			header_req.setSysRefNumber(sRef_no);
			header_req.setSenderID(sHandler.setSenderId(xmlParser.getValueOf("SENDERID")));
			header_req.setReqTimeStamp(sDate);
			header_req.setUsername(sCustomerID);
			header_req.setCredentials(loggedinuser);	
			
			//Instantiating the Stub object: 
			AddDebitCardIssueStub stub = new AddDebitCardIssueStub(sWSDLPath);
		    
			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			if(sCallType.equalsIgnoreCase("DebitCard_New")){
				
				String sCardPrimAC = xmlParser.getValueOf("CardPrimaryAccount");
				String sCardEmbossName = xmlParser.getValueOf("CardEmbossName");
				String sCardProdGrp = xmlParser.getValueOf("CardProductGroup");
				String sInstantFlag = xmlParser.getValueOf("InstantFlag");
				
				AddDebitCardIssueReqMsg add_request = new AddDebitCardIssueReqMsg();			
				AddDebitCardIssueReq_type0 add_req = new AddDebitCardIssueReq_type0();
				add_req.setCardPrimaryAccount(sCardPrimAC);
				add_req.setCardEmbossName(sCardEmbossName);
				add_req.setCardProductGroup(sCardProdGrp);
				add_req.setInstantFlag(sInstantFlag);
				add_request.setHeader(header_req);
				add_request.setAddDebitCardIssueReq(add_req);
				
			    //xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput = stub.getInputXml(add_request);
				
			    stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    
			    AddDebitCardIssueResMsg response = stub.addDebitCardIssue_Oper(add_request);
			    AddDebitCardIssueRes_type0 res = response.getAddDebitCardIssueRes();

			    sOrgnlOutput = stub.resDebitMsg;
			    
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
					"<Option>DebitCard_New</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<AddDebitRes>"+					
						"<CardPrimaryAccount>"+res.getCardPrimaryAccount()+"</CardPrimaryAccount>"+
						"<CardEmbossName>"+res.getCardEmbossName()+"</CardEmbossName>"+
						"<CardProductGroup>"+res.getCardProductGroup()+"</CardProductGroup>"+
						"<NewDebitCardNumber>"+res.getNewDebitCardNumber()+"</NewDebitCardNumber>"+
						"<CardTypeDescription>"+res.getCardTypeDescription()+"</CardTypeDescription>"+
						"<TransactionRefNumber>"+res.getTransactionRefNumber()+"</TransactionRefNumber>"+
					"</AddDebitRes>"+					
					"</Output>";

					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);		    
				}

				else{			
					LogGEN.writeTrace("Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DebitCard_New</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
				}
			}

		}
		catch(Exception e){
			LogGEN.writeTrace("Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddDebitCardIssue</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1){
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddDebitCardIssue</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
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
				 try {
					 dbpass=AESEncryption.decrypt(dbpass);
				 }
				 catch(Exception e){
					 
				 }
				 DBConnection con=new DBConnection();

					 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
					 LogGEN.writeTrace("Log", " Add  Query : finally :" + Query);
					 LogGEN.writeTrace("Log", "sOrg_Output : finally :" + sOrgnlOutput);
					 try {
						 LogGEN.writeTrace("Log", "*****Executing Query : AddDebitCardNew*****");
						 con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
						 LogGEN.writeTrace("Log", "*****Query Executed : AddDebitCardNew*****");
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
			LogGEN.writeTrace("Log","finally :" + sOutput);
			return sOutput;			
		}		
		
	}

}
