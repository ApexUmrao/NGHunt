package com.newgen.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.stub.EstmentConsolSubRequestServiceStub;
import com.newgen.stub.EstmentConsolSubRequestServiceStub.SubUnsubRequest;
import com.newgen.stub.EstmentConsolSubRequestServiceStub.SubUnsubRequestResponse;



public class ESubcription extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	
	/**
	 * Function written to fetch Debit Card Details
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String email_subcription(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called email_subcription");
			System.out.println("sdfasdfsdfasdfsadfsdfsdf");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			WebServiceHandler sHandler= new WebServiceHandler();
			
			try
			{
				sHandler.readCabProperty("email_subcription");
				
				sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				sCabinet=(String)currentCabPropertyMap.get("CABINET");
				sUser=(String)currentCabPropertyMap.get("USER");
				sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
				sPassword=(String)currentCabPropertyMap.get("PASSWORD");
				lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
				
				LogGEN.writeTrace("Log", "read Property successfully");
				LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
				LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
				LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
				LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
				LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
				LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
				
				String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				Date d= new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String sDate = dateFormat.format(d);
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				SubUnsubRequest req=new SubUnsubRequest();
				EstmentConsolSubRequestServiceStub stub=new EstmentConsolSubRequestServiceStub(sWSDLPath);
				SubUnsubRequestResponse res=new SubUnsubRequestResponse();
				System.out.println(xmlDataParser.getValueOf("inputXML"));
				req.setArg_0_0(xmlDataParser.getValueOf("inputXML"));
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				res=stub.subUnsubRequest(req);
								
				LogGEN.writeTrace("Log", "All Objects created");
			
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>SEND_SMS</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<SMSRes>";
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
				 
				    sOutput+="<SubUnsubRequestReturn>"+res.getSubUnsubRequestReturn()+"</SubUnsubRequestReturn>";
				   
				} 
				else
				{
					
				}
				sOutput+="</SmsRes>" ;
				
				    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETDEBITCARDINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETDEBITCARDINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
				}
				
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
			
		}
}

