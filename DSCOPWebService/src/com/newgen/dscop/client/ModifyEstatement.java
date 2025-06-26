package com.newgen.dscop.client;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModEstmentRegStub;
import com.newgen.dscop.stub.ModEstmentRegStub.EStmtSubscriptionReq_type0;
import com.newgen.dscop.stub.ModEstmentRegStub.EStmtSubscriptionRes_type0;
import com.newgen.dscop.stub.ModEstmentRegStub.EstmtSubReq_type0;
import com.newgen.dscop.stub.ModEstmentRegStub.EstmtSubRes_type0;
import com.newgen.dscop.stub.ModEstmentRegStub.HeaderType;
import com.newgen.dscop.stub.ModEstmentRegStub.SREStmtSubscriptionReqMsg;
import com.newgen.dscop.stub.ModEstmentRegStub.SREStmtSubscriptionResMsg;
public class ModifyEstatement extends DSCOPServiceHandler 
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
			LogGEN.writeTrace("CBG_Log", "Fuction called estatement_registration");
			LogGEN.writeTrace("CBG_Log", "ModifyEstatement sInputXML---"+sInputXML);
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(sInputXML);
			String sReturnCode= "";
			String sErrorDetail="";
			String sErrorDesc = "";
			String sOutput="";
			DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			try
			{
				//sHandler.readCabProperty("estatement_registration");
				
				LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
				ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("estatement_registration");
				LogGEN.writeTrace("CBG_Log", "estatement_registration WebServiceConfig Map : "  +wsConfig.toString());
				sWSDLPath=(String)wsConfig.get(0);
				sCabinet=(String)wsConfig.get(1);
				sUser=(String)wsConfig.get(2);
				sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
				sPassword=(String)wsConfig.get(4);
				lTimeOut=Long.valueOf((String)wsConfig.get(5));
				LogGEN.writeTrace("CBG_Log", "estatement_registration WSDL PATH: "+sWSDLPath);
				LogGEN.writeTrace("CBG_Log", "estatement_registration CABINET: "+sCabinet);
				LogGEN.writeTrace("CBG_Log", "estatement_registration USER: "+sUser);
				LogGEN.writeTrace("CBG_Log", "estatement_registration PASSWORD: "+sPassword);
				LogGEN.writeTrace("CBG_Log", "estatement_registration LOGIN_REQ: "+sLoginReq);
				LogGEN.writeTrace("CBG_Log", "estatement_registration TIME_OUT: "+lTimeOut);
				
				String sCustomerID= xmlDataParser.getValueOf("customerID");
				String ref_no=xmlDataParser.getValueOf("REF_NO");
				LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
				
				LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);
				
				ModEstmentRegStub estat=new ModEstmentRegStub(sWSDLPath);
				EStmtSubscriptionReq_type0 req=new EStmtSubscriptionReq_type0();
				SREStmtSubscriptionReqMsg req_msg=new SREStmtSubscriptionReqMsg();
				
				HeaderType Header_Input = new HeaderType();
				LogGEN.writeTrace("CBG_Log", "All Objects created");
				Header_Input.setUsecaseID("1234");
				Header_Input.setServiceName("ModEstmentReg");
				Header_Input.setVersionNo("1.0");
				Header_Input.setServiceAction("Modify");
				Header_Input.setSysRefNumber(ref_no);
				Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
				Header_Input.setReqTimeStamp(sDate);
				Header_Input.setUsername(sCustomerID);
				Header_Input.setCredentials(loggedinuser);
				LogGEN.writeTrace("CBG_Log", "All values set11");
				
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
			   LogGEN.writeTrace("CBG_Log", " ModifyEstatement xmlInput: "+xmlInput);
			   estat._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
				SREStmtSubscriptionResMsg res_msg=estat.modEstmentReg_Oper(req_msg);
			   sOrg_Output=estat.resEstmtMsg;//Added By Harish For inserting original mssg
//			   LogGEN.writeTrace("CBG_Log", " ModifyEstatement sOrg_Output: "+sOrg_Output);
				Header_Input = res_msg.getHeader();
				sReturnCode=  Header_Input.getReturnCode();
				sErrorDetail=Header_Input.getErrorDetail();
				sErrorDesc = Header_Input.getErrorDescription();
				
				LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
				LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
				System.out.println( "Error Detail---"+sErrorDetail);
				LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
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
				LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
				LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
				sErrorDesc=e.getMessage();
				sReturnCode="-1";
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>estatement_registration</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to register esubscription.</td></Output>";
				e.printStackTrace();
			}
			finally
			{
				LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
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
					//sHandler.readCabProperty("JTS");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				 
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					
					
					String inputXml=xmlInput;
					LogGEN.writeTrace("CBG_Log", inputXml);
					String winame=xmlDataParser.getValueOf("WiName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String call_type1=xmlDataParser.getValueOf("DSCOPCallType");
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
						 String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
						 LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
//						 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
						 try {
							 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
						} catch (Exception e2) {
							// TODO: handle exception
							e2.getMessage();
						}
				LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
				return sOutput;			
			}
			
		}
}

