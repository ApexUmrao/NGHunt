package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.ModDebitCardDetailsStub;
import com.newgen.stub.ModDebitCardDetailsStub.ModDebitCardDetailsReqMsg;
import com.newgen.stub.ModDebitCardDetailsStub.ModDebitCardDetailsReq_type0;
import com.newgen.stub.ModDebitCardDetailsStub.HeaderType;
import com.newgen.stub.ModDebitCardDetailsStub.ModDebitCardDetailsResMsg;
import com.newgen.stub.ModDebitCardDetailsStub.ModDebitCardDetailsRes_type0;

public class ModifyDebitCard extends WebServiceHandler
{


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	String xmlInput="";
	@SuppressWarnings("finally")
	public String Modify_Debit_Card(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called Modify_Debit_Card");
		String t=sInputXML;
		String tmp="";
		try
		{
		 tmp= sInputXML.substring(sInputXML.indexOf("<CardNumber>")+12,sInputXML.indexOf("</CardNumber>"));
		
		t=t.replace(tmp,"XXXXXXXXXXXXXXXX");
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("Log", e.toString());
		}
		LogGEN.writeTrace("Log", "sInputXML---"+t);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			sHandler.readCabProperty("Modify_Debit_Card");
			
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
			
			String sCustomerID= xmlDataParser.getValueOf("CustomerId");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			ModDebitCardDetailsStub debit_stub=new ModDebitCardDetailsStub(sWSDLPath);
			ModDebitCardDetailsReq_type0 debit_req=new ModDebitCardDetailsReq_type0();
			ModDebitCardDetailsReqMsg debit_req_msg=new ModDebitCardDetailsReqMsg();
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModDebitCardDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			
			debit_req.setCardMaintenanceType(xmlDataParser.getValueOf("CardMaintenanceType"));
			debit_req.setAccountType(xmlDataParser.getValueOf("AccountType"));
			debit_req.setCardNumber(xmlDataParser.getValueOf("CardNumber"));
			debit_req.setCustomerId(xmlDataParser.getValueOf("CustomerId"));
			debit_req.setPrimaryAccountNumber(xmlDataParser.getValueOf("PrimaryAccountNumber"));
			
			
			
			LogGEN.writeTrace("Log", "All values set11");
			
			debit_req_msg.setHeader(Header_Input);
			debit_req_msg.setModDebitCardDetailsReq(debit_req);
			
			xmlInput=debit_stub.getinputXML(debit_req_msg);
			
			debit_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			ModDebitCardDetailsResMsg res_msg= debit_stub.modDebitCardDetails_Oper(debit_req_msg);
			LogGEN.writeTrace("Log", "22222222222222222");
			sOrg_Output=debit_stub.resDCModMsg;//Added By Harish For inserting original mssg
			Header_Input=res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sReturnCode+":"+sErrorDetail);
			//if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			//{
			try
			{
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{	
					ModDebitCardDetailsRes_type0 debit_res=new ModDebitCardDetailsRes_type0();
					debit_res=res_msg.getModDebitCardDetailsRes();
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
						"<Option>Modify_Debit_Card</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<ModDebitRes>"+
							"<AccountType>"+ debit_res.getAccountType() +"</AccountType>"+
							"<CardMaintenanceType>"+debit_res.getCardMaintenanceType()+"</CardMaintenanceType>"+
							"<CardNumber>"+debit_res.getCardNumber()+"</CardNumber>"+
							"<CustomerId>"+debit_res.getCustomerId()+"</CustomerId>"+
							"<PrimaryAccountNumber>"+debit_res.getPrimaryAccountNumber()+"</PrimaryAccountNumber>"+											
							"<TransactionRefNumber>"+debit_res.getTransactionRefNumber()+"</TransactionRefNumber>"+
						"</ModDebitRes>"+	
						"</Output>";
				}
				else
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Modify_Debit_Card</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify debit card request.</td></Output>";
					LogGEN.writeTrace("Log", "Output :"+sOutput);
				}
				
				
				
				
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Modify_Debit_Card</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify debit card request.</td></Output>";

			}
			//}
			//else
			//{
		    	//LogGEN.writeTrace("Log", "Failed");
		    	//sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETCUSTINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			//}
			
				 
				
			
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sErrorDesc=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Debit_Card</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify debit card request.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Modify_Debit_Card</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify debit card request.</td></Output>";
			}
			
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			
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
					//LogGEN.writeTrace("Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type=xmlDataParser.getValueOf("Calltype");
					sCabinet=xmlDataParser.getValueOf("EngineName");
					dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					
					 String outputxml=sOutput;
					 
					 if(outputxml.indexOf("<CardNumber>")>0)
					 {
						tmp= outputxml.substring(outputxml.indexOf("<CardNumber>")+12,outputxml.indexOf("</CardNumber>"));
						outputxml=outputxml.replace(tmp,"XXXXXXXXXXXXXXXX");
					 }
					 
					 
					/* String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";*/
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
			
			
			return sOutput;			
		}
	}
}

