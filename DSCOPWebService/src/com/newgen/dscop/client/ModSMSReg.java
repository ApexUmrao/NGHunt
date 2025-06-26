package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModSMSChannelRegStub;
import com.newgen.dscop.stub.ModSMSChannelRegStub.HeaderType;
import com.newgen.dscop.stub.ModSMSChannelRegStub.SMSRegistrationReq_type0;
import com.newgen.dscop.stub.ModSMSChannelRegStub.SMSRegistrationRes_type0;
import com.newgen.dscop.stub.ModSMSChannelRegStub.SRSMSRegistrationReqMsg;
import com.newgen.dscop.stub.ModSMSChannelRegStub.SRSMSRegistrationResMsg;
import com.newgen.dscop.stub.ModSMSChannelRegStub.SmsRegReq_type0;
import com.newgen.dscop.stub.ModSMSChannelRegStub.SmsRegRes_type0;


public class ModSMSReg extends DSCOPServiceHandler
{


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	@SuppressWarnings("finally")
	public String add_sms_reg(String sInputXML) 
	{
	
		LogGEN.writeTrace("CBG_Log", "Fuction called Add_SMS_Reg");
		LogGEN.writeTrace("CBG_Log", "ModSMSReg sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		
		
		
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("Add_SMS_Reg");
			
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Add_SMS_Reg");
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg TIME_OUT: "+lTimeOut);
			
			String sCustomerID= xmlDataParser.getValueOf("CustomerId");	
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);
			ModSMSChannelRegStub sms_stub=new ModSMSChannelRegStub(sWSDLPath);
			SmsRegReq_type0 sms_req=new SmsRegReq_type0();
			
			SRSMSRegistrationReqMsg req_msg=new SRSMSRegistrationReqMsg();
			SMSRegistrationReq_type0 sms_req1=new SMSRegistrationReq_type0();
			
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModSMSChannelReg");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			
			sms_req.setDefaultAccountNumber(xmlDataParser.getValueOf("DefaultAccountNumber"));
			sms_req.setMobileNumber(xmlDataParser.getValueOf("MobileNumber"));
			sms_req.setPreferedLanguage(xmlDataParser.getValueOf("PreferedLanguage"));
			sms_req.setCustomerNumber(xmlDataParser.getValueOf("CustomerId"));
			sms_req.setRequestType(xmlDataParser.getValueOf("RequestType"));
			sms_req.setStartTime(xmlDataParser.getValueOf("StartTime"));
			sms_req.setEndTime(xmlDataParser.getValueOf("EndTim"));
			
			
			
			LogGEN.writeTrace("CBG_Log", "All values set11");
			sms_req1.setSmsRegReq(sms_req);
			req_msg.setSMSRegistrationReq(sms_req1);
			req_msg.setHeader(Header_Input);
			
			xmlInput=sms_stub.getinputXML(req_msg);
			
			sms_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			SRSMSRegistrationResMsg res_msg= sms_stub.modSMSChannelReg_Oper(req_msg);
			LogGEN.writeTrace("CBG_Log", "MOdSMSReg inputxml " + xmlInput);
			sOrg_Output=sms_stub.resModSMMsg;
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
					SMSRegistrationRes_type0 sms_res=new SMSRegistrationRes_type0();
					sms_res=res_msg.getSMSRegistrationRes();
					SmsRegRes_type0 res=new SmsRegRes_type0();
					res=sms_res.getSmsRegRes();
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<Output>"+
						"<Option>Add_SMS_Reg</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<SMSRes>"+
							"<CustomerNumber>"+ res.getCustomerNumber() +"</CustomerNumber>"+
							"<Reason>"+ res.getReason()+"</Reason>"+
							"<Status>"+ res.getStatus()+"</Status>"+
						"</SMSRes>"+	
						"</Output>";
				}
				else
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_SMS_Reg</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to process SMS request.</td></Output>";
					LogGEN.writeTrace("CBG_Log", "Output :"+sOutput);
				}
				
				
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sErrorDesc=ex.getMessage();
				sReturnCode="-1";
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_SMS_Reg</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to process SMS request.</td></Output>";

			}
			//}
			//else
			//{
		    	//LogGEN.writeTrace("CBG_Log", "Failed");
		    	//sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_GETCUSTINFO</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			//}
			
			
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_SMS_Reg</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to process SMS request.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_SMS_Reg</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to process SMS request.</td></Output>";
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
//					 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
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

