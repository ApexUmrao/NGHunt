package com.newgen.client;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.ModEstmentRegStub;
import com.newgen.stub.ModEstmentRegStub.EStmtSubscriptionReq_type0;
import com.newgen.stub.ModEstmentRegStub.EStmtSubscriptionRes_type0;
import com.newgen.stub.ModEstmentRegStub.EstmtSubReq_type0;
import com.newgen.stub.ModEstmentRegStub.EstmtSubRes_type0;
import com.newgen.stub.ModEstmentRegStub.SREStmtSubscriptionReqMsg;
import com.newgen.stub.ModEstmentRegStub.HeaderType;
import com.newgen.stub.ModEstmentRegStub.SREStmtSubscriptionResMsg;
public class ModifyEstatement extends WebServiceHandler 
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
	 * Function written to fetch Debit Card Details
	 * @author gupta.ashish
	 * @param sInputXML
	 * @return
	 */

		@SuppressWarnings("finally")
		public String email_subcription(String sInputXML) 
		{
			LogGEN.writeTrace("Log", "Fuction called estatement_registration");
			LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			WebServiceHandler sHandler= new WebServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				sHandler.readCabProperty("estatement_registration");
				
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
				
				String sCustomerID= xmlDataParser.getValueOf("customerID");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
				
				LogGEN.writeTrace("Log", "sDate---"+sDate);
				
				ModEstmentRegStub estat=new ModEstmentRegStub(sWSDLPath);
				EStmtSubscriptionReq_type0 req=new EStmtSubscriptionReq_type0();
				SREStmtSubscriptionReqMsg req_msg=new SREStmtSubscriptionReqMsg();
				
				HeaderType Header_Input = new HeaderType();
				LogGEN.writeTrace("Log", "All Objects created");
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("ModEstmentReg");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Modify");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);
				LogGEN.writeTrace("Log", "All values set11");
				
				EstmtSubReq_type0[] req_t=new EstmtSubReq_type0[1];
				req_t[0]=new EstmtSubReq_type0();
				req_t[0].setAccountType(xmlDataParser.getValueOf("AccountType"));
				req_t[0].setAcctNumber(xmlDataParser.getValueOf("AcctNumber"));
				req_t[0].setCustomerID(xmlDataParser.getValueOf("CustomerID"));
				req_t[0].setPhysicalstmtSubStatus(xmlDataParser.getValueOf("PhysicalstmtSubStatus"));
				req_t[0].setEstmtSubStatus(xmlDataParser.getValueOf("EstmtSubStatus"));
				
				
				
			   req.setEstmtSubReq(req_t);
			 
			   req_msg.setEStmtSubscriptionReq(req);
			   req_msg.setHeader(Header_Input);
			   xmlInput=estat.getinputXML(req_msg);
			   
			   estat._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				SREStmtSubscriptionResMsg res_msg=estat.modEstmentReg_Oper(req_msg);
			   sOrg_Output=estat.resEstmtMsg;//Added By Harish For inserting original mssg
				
				Header_Input = res_msg.getHeader();
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				
				LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
				System.out.println( "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>estatement_registration</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<EstatementRegRes>";
				 EStmtSubscriptionRes_type0 res= new EStmtSubscriptionRes_type0();	
				    res=res_msg.getEStmtSubscriptionRes();
				    EstmtSubRes_type0[] r=new EstmtSubRes_type0[1];
				    r=res.getEstmtSubRes();
				    sOutput+="<Reason>"+r[0].getReason()+"</Reason>"+
				    "<Status>"+r[0].getStatus()+"</Status>";
				    System.out.println(sOutput);
				if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
				{
				     res= new EStmtSubscriptionRes_type0();	
				    res=res_msg.getEStmtSubscriptionRes();
				    r=new EstmtSubRes_type0[1];
				    r=res.getEstmtSubRes();
				    sOutput+="<Account_Number>"+r[0].getAcctNumber()+"</Account_Number>"+
				    "<CUST_ID>"+r[0].getCustomerID()+"<CUST_ID>";
				    
				} 
				else
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>estatement_registration</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to register esubscription.</td></Output>";

				}
				sOutput+="</EstatementRegRes></Output>" ;
				
				
				
				
				    
			}
			catch (Exception e)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sErrorDesc=e.getMessage();
				sReturnCode="-1";
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>estatement_registration</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to register esubscription.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
				if(sOutput.trim().length()<1)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>estatement_registration</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to register esubscription.</td></Output>";
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
					String call_type1=xmlDataParser.getValueOf("Calltype");
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
							"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
						 LogGEN.writeTrace("Log"," Add  Query : finally :"+Query);
						 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
						 try {
							 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
						} catch (Exception e2) {
							// TODO: handle exception
							e2.getMessage();
						}
				LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
			
		}
}

