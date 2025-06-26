package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.newgen.AESEncryption;
import com.newgen.stub.InqSearchByCardNumberStub.Customer_type0;
import com.newgen.stub.InqSearchByCardNumberStub.Customer_type0E;
import com.newgen.stub.InqSearchByCardNumberStub.HeaderType;
import com.newgen.stub.InqSearchByCardNumberStub.ListOfCustomer_type0;
import com.newgen.stub.InqSearchByCardNumberStub.ListOfCustomer_type0E;
import com.newgen.stub.InqSearchByCardNumberStub.SearchByCardNumberReqMsg;
import com.newgen.stub.InqSearchByCardNumberStub.SearchByCardNumberReq_type0;
import com.newgen.stub.InqSearchByCardNumberStub.SearchByCardNumberResMsg;
import com.newgen.stub.InqSearchByCardNumberStub.SearchByCardNumberRes_type0;
import com.newgen.stub.*;
public class inqDebitCard extends WebServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	 String sOrg_Output=null;//Added By Harish For inserting original mssg
	
	/**
	 * Function written to fetch customer information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String DebitInquiry(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called Debitsearch");
			//LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
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
				
				sHandler.readCabProperty("Search_By_Debit");				
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
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				String DebitCard=xmlDataParser.getValueOf("DEBIT_CARD");
				LogGEN.writeTrace("Log", "sInputXML---"+sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX"));
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				
				//sDate="06/08/2013 18:33:10";
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				InqSearchByCardNumberStub debit_stub=new InqSearchByCardNumberStub(sWSDLPath);
				SearchByCardNumberReqMsg debi_Info_Req_Msg = new SearchByCardNumberReqMsg();				
				SearchByCardNumberResMsg debi_Info_Rep_Msg=new SearchByCardNumberResMsg();
			
				HeaderType Header_Input = new HeaderType();
				
				LogGEN.writeTrace("Log", "All Objects created");
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("InqSearchByCardNumber");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Inquiry");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);	
				SearchByCardNumberReq_type0 debit_req=new SearchByCardNumberReq_type0();
				ListOfCustomer_type0E cust_list=new ListOfCustomer_type0E();
				Customer_type0E cust=new Customer_type0E();
				cust.setCardNumber(DebitCard);
				cust.setCardType(xmlDataParser.getValueOf("CARD_TYPE"));
				cust_list.setCustomer(cust);
				//debi_Req_Type.setListOfCustomer(cust_list);
				debit_req.setListOfCustomer(cust_list);
				debi_Info_Req_Msg.setHeader(Header_Input);
				debi_Info_Req_Msg.setSearchByCardNumberReq(debit_req);	 
		
			    LogGEN.writeTrace("Log", "All values set");
			    
			    xmlInput=sInputXML.replace(DebitCard, "XXXXXXXXXXXXXXXX");
			    xmlInput=debit_stub.getInputXml(debi_Info_Req_Msg);
			    

			    debit_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    debi_Info_Rep_Msg=debit_stub.searchByCardNumber_Oper(debi_Info_Req_Msg);
			    sOrg_Output=debit_stub.resMsgDCSearch;//Added By Harish For inserting original mssg
			    Header_Input= debi_Info_Rep_Msg.getHeader();
			    sReturnCode= Header_Input.getReturnCode();
			    sErrorDetail=Header_Input.getErrorDetail();
			    sErrorDesc = Header_Input.getErrorDescription();
			    
			    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
			    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
			    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
			    
			    
			    
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{			    
			    	LogGEN.writeTrace("Log", "Successful Result");
			    	SearchByCardNumberRes_type0 debi_Info_Res=debi_Info_Rep_Msg.getSearchByCardNumberRes();
				   ListOfCustomer_type0 cust_list_res=debi_Info_Res.getListOfCustomer();
				   Customer_type0[] cust_res=cust_list_res.getCustomer();
			    	for(int i=0;i<=cust_res.length-1;i++)
			    	{
				    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
							"<Option>Debit_Card_Information</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDetail+"</errorDescription>"+
							"<Customers>"+
								"<customerId>"+ cust_res[0].getCustomerID() +"</customerId>"+																			
							"</Customers>"+	
							"</Output>";
			    	}
					LogGEN.writeTrace("Log", "Output XML--- "+sOutput);
				}
			    else
				{
			    	LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Debit_Card_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
				}
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sReturnCode="-1";
				sErrorDetail=e.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Debit_Card_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Debit_Card_Information</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to search debit card.</td></Output>";
				}
				String Status="";
				if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				{
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
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					 try
					 {
						 dbpass=AESEncryption.decrypt(dbpass);
					 }
					 catch(Exception e)
					 {
						 
					 }
					 DBConnection con=new DBConnection();
					 /**
					  * //Added By Harish For inserting original mssg
					  * Date			:	17 Mar 2015
					  * Description 	:	Replace execute with executeClob
					  */
						 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
						 LogGEN.writeTrace("Log"," Add  Query : finally :"+Query);
						 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
						 try {
							 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
						} catch (Exception e2) {
							// TODO: handle exception
							e2.getMessage();
						}
				LogGEN.writeTrace("Log","finally :"+sOutput);
				return sOutput;			
			}
		}
}
