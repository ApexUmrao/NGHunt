package com.newgen.client;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.client.DBConnection;
import com.newgen.client.LogGEN;
import com.newgen.client.WebServiceHandler;
import com.newgen.client.XMLParser;
import com.newgen.stub.AddCBRequestStub;
import com.newgen.stub.AddCBRequestStub.HeaderType;
import com.newgen.stub.AddCBRequestStub.SRChequeBookReq2_type0;
import com.newgen.stub.AddCBRequestStub.SRChequeBookReqMsg;
import com.newgen.stub.AddCBRequestStub.SRChequeBookReqMsgChoice_type0;
import com.newgen.stub.AddCBRequestStub.SRChequeBookRes2_type0;
import com.newgen.stub.AddCBRequestStub.SRChequeBookResMsg;
import com.newgen.stub.AddCBRequestStub.SRChequeBookResMsgChoice_type0;


public class ChequeBook extends WebServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Chk_ouput=null;
	
	/**
	 * Function written to fetch customer information
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String addCheque(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called Cheque_Book");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
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
				
				sHandler.readCabProperty("Cheque_Book");				
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
				//String sessionID= xmlDataParser.getValueOf("SessionId");
				//String winame=xmlDataParser.getValueOf("WiName");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				String oldRefNo=xmlDataParser.getValueOf("OLDREF_NO"); //change for old ref no
				sCabinet=xmlDataParser.getValueOf("EngineName");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				//sDate="06/08/2013 18:33:10";
				AddCBRequestStub cheque_book=new AddCBRequestStub(sWSDLPath);
				SRChequeBookReqMsg cheque_book_Req_Msg = new SRChequeBookReqMsg();
				SRChequeBookResMsg res_msg=new SRChequeBookResMsg();
				SRChequeBookReqMsgChoice_type0 req=new SRChequeBookReqMsgChoice_type0();
				SRChequeBookReq2_type0 r=new SRChequeBookReq2_type0();
				
				HeaderType Header_Input = new HeaderType();
				
				
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("AddCBRequest");
				Header_Input.setVersionNo("2.0");
				Header_Input.setServiceAction("Addition");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);			  
				
				r.setCheckerId("WMSUSER");
				r.setChequeBookIssueDate(xmlDataParser.getValueOf("ChequeBookIssueDate"));
				r.setChequeBookSerialNo(xmlDataParser.getValueOf("ChequeBookSerialNo"));
				r.setChequeBookStatus(xmlDataParser.getValueOf("ChequeBookStatus"));
				r.setChequeEndNumber(xmlDataParser.getValueOf("ChequeEndNumber"));
				r.setChequePaidStatus(xmlDataParser.getValueOf("ChequePaidStatus"));
				r.setChequeStartNumber(xmlDataParser.getValueOf("ChequeStartNumber"));
				r.setCustAccountNumber(xmlDataParser.getValueOf("CustAccountNumber"));
				r.setFlagChequeBookType(xmlDataParser.getValueOf("FlagChequeBookType"));
				r.setFlagChequeType(xmlDataParser.getValueOf("FlagChequeType"));
				r.setFlagPRN(xmlDataParser.getValueOf("FlagPRN"));
				r.setFlagServiceChargesWaiver(xmlDataParser.getValueOf("FlagServiceChargesWaiver"));
				r.setMaintenanceOption(xmlDataParser.getValueOf("MaintenanceOption"));
				r.setMakerId("WMSUSER");
				r.setUpdateSerialNo(xmlDataParser.getValueOf("UpdateSerialNo"));
				r.setNoOfLeavesRequested(xmlDataParser.getValueOf("NoOfLeavesRequested"));
				r.setOrigRefNumber(oldRefNo);	//change for old ref no
				req.setSRChequeBookReq2(r);
				
				cheque_book_Req_Msg.setSRChequeBookReqMsgChoice_type0(req);
				cheque_book_Req_Msg.setHeader(Header_Input);
			    
			    LogGEN.writeTrace("Log", "All values set");
			    
			    xmlInput=cheque_book.getinputXML(cheque_book_Req_Msg);
			    
			    cheque_book._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			    res_msg=cheque_book.addCBRequest_Oper(cheque_book_Req_Msg);
			    sOrg_Chk_ouput=cheque_book.resAddChkMsg;
			    Header_Input= res_msg.getHeader();
			    sReturnCode= Header_Input.getReturnCode();
			    sErrorDetail=Header_Input.getErrorDetail();
			    sErrorDesc = Header_Input.getErrorDescription();
			    
			    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
			    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
			    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
			    
			    
			    
			    if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{			    
			    	LogGEN.writeTrace("Log", "Successful Result");
			    	SRChequeBookResMsgChoice_type0 res_ch=new SRChequeBookResMsgChoice_type0();
			    	res_ch=res_msg.getSRChequeBookResMsgChoice_type0();
			    	SRChequeBookRes2_type0 res=new SRChequeBookRes2_type0();
			    	res=res_ch.getSRChequeBookRes2();
				    
				 
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
						"<Option>Cheque_Book</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<returnCode>"+sReturnCode+"</returnCode><errorDetail>"+sErrorDetail+"</errorDetail>";						
						sOutput+="<ChequeBookSerialNo>"+res.getChequeBookSerialNo()+"</ChequeBookSerialNo>"+
						"<ChequeBookStatus>"+res.getChequeBookStatus()+"</ChequeBookStatus>"+
						"<ChequeEndNumber>"+res.getChequeEndNumber()+"</ChequeEndNumber>"+
						"<ChequePaidStatus>"+res.getChequePaidStatus()+"</ChequePaidStatus>"+
						"<ChequeStartNumber>"+res.getChequeStartNumber()+"</ChequeStartNumber>"+
						"<CustAccountNumber>"+res.getCustAccountNumber()+"</CustAccountNumber>"+
						"<FlagChequeBookType>"+res.getFlagChequeBookType()+"</FlagChequeBookType>"+
						"<FlagChequeType>"+res.getFlagChequeType()+"</FlagChequeType>"+
						"<FlagPRN>"+res.getFlagPRN()+"</FlagPRN>"+
						"<FlagServiceChargesWaiver>"+res.getFlagServiceChargesWaiver()+"</FlagServiceChargesWaiver>"+						
						"<NoOfLeavesRequested>"+res.getNoOfLeavesRequested()+"</NoOfLeavesRequested>"+
						"<UpdateSerialNo>"+res.getUpdateSerialNo()+"</UpdateSerialNo>"+
						"<Status>"+res.getStatus()+"</Status>"+
						"<Reason>"+res.getReason()+"</Reason>";
						sOutput+="</Output>";
						System.out.println(sOutput);
						LogGEN.writeTrace("Log", "Output XML--- "+sOutput);
				}
			    else
				{
			    	SRChequeBookResMsgChoice_type0 res_ch=new SRChequeBookResMsgChoice_type0();
			    	res_ch=res_msg.getSRChequeBookResMsgChoice_type0();
			    	SRChequeBookRes2_type0 res=new SRChequeBookRes2_type0();
			    	res=res_ch.getSRChequeBookRes2();
			    	LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Cheque_Book</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>"+res.getStatus()+"</Status><Reason>"+res.getReason()+"</Reason><td>Unable to issue cheque book.</td></Output>";
				}
			    
			    
				
					 
			    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sReturnCode="1";
				//sErrorDetail=e.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Cheque_Book</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to issue cheque book.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>Cheque_Book</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to issue cheque book.</td></Output>";
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
					// String outputxml=sOutput;
					 /*String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";
*/
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
					 LogGEN.writeTrace("Log"," Add Cheque Query : finally :"+Query);
					 LogGEN.writeTrace("Log","sOrg_Chk_ouput : finally :"+sOrg_Chk_ouput);
					 try {
						 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Chk_ouput.replaceAll("'", "''"));
					} catch (Exception e2) {
						// TODO: handle exception
						e2.getMessage();
					}
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
		}
}
