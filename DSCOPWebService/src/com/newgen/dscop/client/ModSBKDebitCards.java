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

package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModSBKDebitCardsStub;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.AddDebitCardInfoReqMsg;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.AddDebitCardInfoReq_type0;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.AddDebitCardInfoResMsg;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.AddDebitCardInfoRes_type0;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.HeaderType;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusReqMsg;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusReq_type0;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusResMsg;
import com.newgen.dscop.stub.ModSBKDebitCardsStub.ModDebitCardPrintStatusRes_type0;

public class ModSBKDebitCards extends DSCOPServiceHandler {

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
		LogGEN.writeTrace("CBG_Log", "Fuction called ModifySBKDebitCards");

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
			//sHandler.readCabProperty("ModSBKDebitCards");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModSBKDebitCards");
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModSBKDebitCards TIME_OUT: "+lTimeOut);

			//Parsing inputXML to retrieve tag values: 
			String sCustomerID = xmlParser.getValueOf("CUST_ID");
			String sRef_no = xmlParser.getValueOf("REF_NO");
			String sCallType = xmlParser.getValueOf("CBGCalltype");

			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);

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

			LogGEN.writeTrace("CBG_Log", "***Instantiating the ModSBKDebitCardsStub object***");
			//Instantiating the Stub object: 
			ModSBKDebitCardsStub stub = new ModSBKDebitCardsStub(sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "***ModSBKDebitCardsStub object Instantiated***");

			//Based on the type of call:
			//Instantiating the Response objects to get the required params:
			if(sCallType.equalsIgnoreCase("Base24")){				
				String sDC_no = xmlParser.getValueOf("DebitCardNumber");
				LogGEN.writeTrace("CBG_Log", "***Inside Base24***");
				LogGEN.writeTrace("CBG_Log", "#DebitCardNumber# = "+sDC_no);

				AddDebitCardInfoReqMsg add_request = new AddDebitCardInfoReqMsg();			
				AddDebitCardInfoReq_type0 add_req = new AddDebitCardInfoReq_type0();
				add_req.setDebitCardNumber(sDC_no);
				add_request.setHeader(header_req);
				add_request.setAddDebitCardInfoReq(add_req);

				LogGEN.writeTrace("CBG_Log", "***calling getInputXml_AddCard***");
				//xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
				xmlInput = stub.getInputXml_AddCard(add_request);
				LogGEN.writeTrace("CBG_Log", "***Returned from getInputXml_AddCard***");

				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				LogGEN.writeTrace("CBG_Log", "***TimeOut: set***");

				AddDebitCardInfoResMsg response = stub.addDebitCardInfo_Oper(add_request);
				LogGEN.writeTrace("CBG_Log", "***Creating response reference of addDebitCardInfo_Oper Operation***");

				AddDebitCardInfoRes_type0 res = response.getAddDebitCardInfoRes();
				LogGEN.writeTrace("CBG_Log", "***Calling getAddDebitCardInfoRes from the response reference***");

				sOrgnlOutput = stub.resMsgAddDebitCard;
				LogGEN.writeTrace("CBG_Log", "***Getting resMsgAddDebitCard***");

				HeaderType header_resp = response.getHeader();
				LogGEN.writeTrace("CBG_Log", "***Getting Header of response reference***");

				sReturnCode = header_resp.getReturnCode();
				sErrorDetail = header_resp.getErrorDetail();
				sErrorDesc = header_resp.getErrorDescription();

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){					
					LogGEN.writeTrace("CBG_Log", "Successful Result");
					LogGEN.writeTrace("CBG_Log", "***Writing Base24 Response sOutput***");
					sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>Base24</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"<debitCardNumber>"+res.getDebitCardNumber()+"</debitCardNumber>"+
							"<status>"+res.getStatus()+"</status>"+
							"<reason>"+res.getReason()+"</reason>"+
							"</Output>";

//					LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);		    
				}

				else{					
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Base24</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info. (Base24)</td></Output>";
				}
			}
			else if(sCallType.equalsIgnoreCase("ModDebitCardPrintStatus")){				
				String sDC_no = xmlParser.getValueOf("DebitCardNumber");
				String sPrint_status = xmlParser.getValueOf("PrintStatus");	
				LogGEN.writeTrace("CBG_Log", "***Inside ModDebitCardPrintStatus***");				
				LogGEN.writeTrace("CBG_Log", "#DebitCardNumber# = "+sDC_no);
				LogGEN.writeTrace("CBG_Log", "#PrintStatus# = "+sPrint_status);

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

				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1")){
					LogGEN.writeTrace("CBG_Log", "Successful Result");

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

//					LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);						
				}
				else{					
					LogGEN.writeTrace("CBG_Log", "Failed");
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModDebitCardPrintStatus</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.(ModDebitCardPrintStatus)</td></Output>";
				}
			}
		}
		catch(Exception e){
			LogGEN.writeTrace("CBG_Log", "***Entered catch block of raised exception***");
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DebitCard_PrintStatus_Info</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to retrieve debit card info.</td></Output>";
			e.printStackTrace();			
		}
		finally{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
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
				//sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", "***inputXML***");
			LogGEN.writeTrace("CBG_Log", inputXml);
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
			LogGEN.writeTrace("CBG_Log", "sOrg_Output : finally :" + sOrgnlOutput);
			try {
				LogGEN.writeTrace("CBG_Log", "*****Executing Query : ModSBKDebitCards*****");
				con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sOrgnlOutput.replaceAll("'", "''"));
				LogGEN.writeTrace("CBG_Log", "*****Query Executed : ModSBKDebitCards*****");
			} catch (Exception e2) {
				// TODO: handle exception
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","finally :" + sOutput);
			return sOutput;			
		}		

	}

}
