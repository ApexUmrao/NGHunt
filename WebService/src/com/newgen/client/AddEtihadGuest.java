package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.newgen.AESEncryption;
import com.newgen.stub.AddEtihadGuestStub.AddEtihadGuestReq_type0;
import com.newgen.stub.AddEtihadGuestStub.AddEtihadGuestResMsg;
import com.newgen.stub.AddEtihadGuestStub.AddEtihadGuestRes_type0;
import com.newgen.stub.AddEtihadGuestStub.HeaderType;
import com.newgen.stub.AddEtihadGuestStub.AddEtihadGuestReqMsg;
import com.newgen.stub.*;
public class AddEtihadGuest extends WebServiceHandler
{


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_AdEtihad_output=null;//Added By Harish For inserting original mssg
	String xmlInput="";
	@SuppressWarnings("finally")
	public String Add_Etihad_Guest(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called Add_Etihad_Guest");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		WebServiceHandler sHandler= new WebServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			sHandler.readCabProperty("ADD_ETIHAD_GUEST");
			
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
			String sCustName=xmlDataParser.getValueOf("CUST_NAME");	
			String sStaff=xmlDataParser.getValueOf("STAFF_FLAG");
			String sAction=xmlDataParser.getValueOf("ACTION_FLAG");
			String etihadno=xmlDataParser.getValueOf("ETIHAD_NO");
			LogGEN.writeTrace("Log", "sCustomerID---"+sCustomerID);
			
			AddEtihadGuestStub etihad_stub=new AddEtihadGuestStub(sWSDLPath);
			AddEtihadGuestReq_type0 etihad_cust_req=new AddEtihadGuestReq_type0();
			AddEtihadGuestReqMsg etihad_req_msg=new AddEtihadGuestReqMsg();
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddEtihadGuest");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			etihad_cust_req.setCustomerID(sCustomerID);
			etihad_cust_req.setEmbossName(sCustName);
			etihad_cust_req.setEtihadStaffFlag(sStaff);
			etihad_cust_req.setActionFlag(sAction);	
		    etihad_cust_req.setEtihadGuestNo(etihadno);
			
			LogGEN.writeTrace("Log", "All values set11");
			etihad_req_msg.setAddEtihadGuestReq(etihad_cust_req);
			etihad_req_msg.setHeader(Header_Input);				
			
			xmlInput=etihad_stub.getinputXML(etihad_req_msg);
			etihad_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			AddEtihadGuestResMsg add_acc_res_msg= etihad_stub.addEtihadGuest_Oper(etihad_req_msg);
			sOrg_AdEtihad_output=etihad_stub.resMsgEtihad;
			Header_Input=add_acc_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sReturnCode+":"+sErrorDetail);
			//if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			//{
			try
			{
				AddEtihadGuestRes_type0 etihad_res=new AddEtihadGuestRes_type0();
				etihad_res=add_acc_res_msg.getAddEtihadGuestRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>ADD_ETIHAD_GUEST</Option>" +
					"<returnCode>"+sReturnCode+"</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<AddEtihadRes>"+
						"<customerId>"+ etihad_res.getCustomerID() +"</customerId>"+
						"<EtihadGuestNo>"+etihad_res.getEtihadGuestNo()+"</EtihadGuestNo>"+
						"<ResponseCode>"+etihad_res.getResponseCode()+"</ResponseCode>"+										
					"</AddEtihadRes>"+	
					"</Output>";
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sReturnCode="-1";
				sErrorDetail=ex.getMessage();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ETIHAD</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add etihad guest.</td></Output>";
				
			}
			
			//}
			
			
			
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add etihad guest.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to add etihad guest.</td></Output>";
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
				//Date d1=new Date();
				//String sDate1 = dateFormat.format(d1);
				// String outputxml=sOutput;
				 /*String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";*/
				  /**
				  * //Added By Harish For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
				 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
				 LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
				 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_AdEtihad_output);

				 DBConnection con=new DBConnection();
				 try
				 {					 
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_AdEtihad_output.replaceAll("'", "''"));
				 }
				 catch(Exception ee)
				 {
					 ee.getMessage();
				 }
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			//End Here
			return sOutput;			
		}
	}
}

