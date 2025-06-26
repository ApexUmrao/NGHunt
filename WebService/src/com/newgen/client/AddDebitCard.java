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
public class AddDebitCard extends WebServiceHandler
{


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_output_AdDBCrd =null;//Added By Harish For inserting original mssg
	@SuppressWarnings("finally")
	public String add_debit_request(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called Add_Debit_Card_Issuence");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String xmlInput="";
		String tmp="";
		
		WebServiceHandler sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			sHandler.readCabProperty("Add_Debit_Card_Issuence");
			
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
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			AddDebitCardIssueStub debit_stub=new AddDebitCardIssueStub(sWSDLPath);
			AddDebitCardIssueReq_type0 debit_req=new AddDebitCardIssueReq_type0();
			AddDebitCardIssueReqMsg debit_req_msg=new AddDebitCardIssueReqMsg();
						
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddDebitCardIssue");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			
			debit_req.setCardEmbossName(xmlDataParser.getValueOf("CardEmbossName"));
			debit_req.setCardPrimaryAccount(xmlDataParser.getValueOf("CardPrimaryAccount"));
			debit_req.setCardProductGroup(xmlDataParser.getValueOf("CardProductGroup"));
			debit_req.setInstantFlag("N");
			LogGEN.writeTrace("Log", "All values set11");
			
			debit_req_msg.setHeader(Header_Input);
			debit_req_msg.setAddDebitCardIssueReq(debit_req);
			
		    xmlInput=debit_stub.getInputXml(debit_req_msg);
//		    xmlInput = debit_stub.getInputXml_AddDCIssue(debit_req_msg); //changed by Nishant Parmar (02-02-2016)
		    debit_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
		    LogGEN.writeTrace("Log", "All values set111");
		    AddDebitCardIssueResMsg res_msg= debit_stub.addDebitCardIssue_Oper(debit_req_msg);
		    sOrg_output_AdDBCrd=debit_stub.resDebitMsg;
			Header_Input=res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sReturnCode+":"+sErrorDetail);
			LogGEN.writeTrace("Log", "All values set1111");
			//if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			//{
			try
			{
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{	
					AddDebitCardIssueRes_type0 debit_res=new AddDebitCardIssueRes_type0();
					debit_res=res_msg.getAddDebitCardIssueRes();
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
						"<Option>Add_Debit_Card_Issuence</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddDebitRes>"+
							"<CardPrimaryAccount>"+ debit_res.getCardPrimaryAccount() +"</CardPrimaryAccount>"+
							"<CardEmbossName>"+debit_res.getCardEmbossName()+"</CardEmbossName>"+
							"<CardProductGroup>"+debit_res.getCardProductGroup()+"</CardProductGroup>"+
							"<NewDebitCardNumber>"+debit_res.getNewDebitCardNumber()+"</NewDebitCardNumber>"+
							"<TransactionRefNumber>"+debit_res.getTransactionRefNumber()+"</TransactionRefNumber>"+
							"<CardTypeDescription>"+debit_res.getCardTypeDescription()+"</CardTypeDescription>"+	
						"</AddDebitRes>"+	
						"</Output>";
					tmp=sOutput;
					tmp= sOutput.substring(sOutput.indexOf("<NewDebitCardNumber>")+20,sOutput.indexOf("</NewDebitCardNumber>"));
					tmp=sOutput.replace(tmp,"XXXXXXXXXXXXXXXX");
				}
				else
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_Debit_Card_Issuence</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add new debit card request.</td></Output>";
					LogGEN.writeTrace("Log", "Output :"+sOutput);
				}
				
				
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getMessage());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_Debit_Card_Issuence</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add new debit card request.</td></Output>";

			}			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_Debit_Card_Issuence</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add new debit card request</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_Debit_Card_Issuence</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add new debit card request</td></Output>";
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
			// String outputxml=tmp;
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
				 LogGEN.writeTrace("Log"," Add Debit Card Query : finally :"+Query);
				 LogGEN.writeTrace("Log","sOrg_output_AdDBCrd : finally :"+sOrg_output_AdDBCrd);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_output_AdDBCrd.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
					e2.getMessage();
				}
				 
				 LogGEN.writeTrace("Log"," Add Debit Card sOutput : finally :"+sOutput);
				 //End Here
			return sOutput;			
		}
	}
}
