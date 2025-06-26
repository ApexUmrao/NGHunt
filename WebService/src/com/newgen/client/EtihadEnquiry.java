package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.newgen.AESEncryption;
import com.newgen.stub.InqEtihadGuestStub.HeaderType;

import com.newgen.stub.InqEtihadGuestStub.InqEtihadGuestReqMsg;
import com.newgen.stub.InqEtihadGuestStub.InqEtihadGuestReq_type0;
import com.newgen.stub.InqEtihadGuestStub.InqEtihadGuestResMsg;
import com.newgen.stub.InqEtihadGuestStub.InqEtihadGuestRes_type0;
import com.newgen.stub.InqEtihadGuestStub.TPRateRequestFlag_type1;
import com.newgen.stub.*;

public class EtihadEnquiry extends WebServiceHandler
{
	

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	String xmlInput="";
	protected static long lTimeOut = 30000;
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	@SuppressWarnings("finally")
	public String inqEtidahGuest(String sInputXML) 
	{
	
		LogGEN.writeTrace("Log", "Fuction called inqEtihadGuest");
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
			sHandler.readCabProperty("Etihad_Guest_Information");
			
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
			
			InqEtihadGuestStub etihad_stub=new InqEtihadGuestStub(sWSDLPath);
			InqEtihadGuestReq_type0 etihad_cust_req=new InqEtihadGuestReq_type0();
			InqEtihadGuestReqMsg etihad_req_msg=new InqEtihadGuestReqMsg();
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqEtihadGuest");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			etihad_cust_req.setCustomerID(sCustomerID);
			etihad_cust_req.setEtihadGuestNo(xmlDataParser.getValueOf("etihadGuestNo"));
			TPRateRequestFlag_type1 tp=new TPRateRequestFlag_type1();
			tp.setTPRateRequestFlag_type0("N");
			etihad_cust_req.setTPRateRequestFlag(tp);
			
			LogGEN.writeTrace("Log", "All values set11");
			etihad_req_msg.setInqEtihadGuestReq(etihad_cust_req);
			etihad_req_msg.setHeader(Header_Input);				
			
			xmlInput=etihad_stub.getinputXML(etihad_req_msg);
			etihad_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			InqEtihadGuestResMsg add_acc_res_msg= etihad_stub.inqEtihadGuest_Oper(etihad_req_msg);
			sOrg_Output=etihad_stub.resIngEtihadMsg;
			Header_Input=add_acc_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			System.out.println(sReturnCode+":"+sErrorDetail);
			//if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			//{
			try
			{
				InqEtihadGuestRes_type0 etihad_res=new InqEtihadGuestRes_type0();
				etihad_res=add_acc_res_msg.getInqEtihadGuestRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
					"<Option>INQ_ETIHAD</Option>" +
					"<returnCode>0</returnCode>" +
					"<errorDescription>"+sErrorDetail+"</errorDescription>"+
					"<EtihadRes>"+
						"<customerId>"+ etihad_res.getCustomerID() +"</customerId>"+
						"<EtihadGuestNo>"+etihad_res.getEtihadGuestNo()+"</EtihadGuestNo>"+						
						"<ResponseCode>"+etihad_res.getResponseCode()+"</ResponseCode>"+
						"<ResponseValue>"+etihad_res.getResponseValue()+"</ResponseValue>"+								
					"</EtihadRes>"+	
					"</Output>";
			}
			catch(Exception ex)
			{
				LogGEN.writeTrace("Log", "Error in Web Serviice :"+ex.toString());
				LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+ex.getStackTrace());
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";

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
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch etihad guest.</td></Output>";
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
			LogGEN.writeTrace("Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
