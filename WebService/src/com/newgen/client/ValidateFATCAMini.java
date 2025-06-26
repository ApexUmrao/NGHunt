package com.newgen.client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.newgen.AESEncryption;
import com.newgen.stub.InqFATCAValidationStub;
import com.newgen.stub.InqFATCAValidationStub.HeaderType;

import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReqMsgChoice_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationReq_type0;

import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationResMsgChoice_type0;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationResMsg;
import com.newgen.stub.InqFATCAValidationStub.InqFATCAValidationRes_type0;

public class ValidateFATCAMini extends WebServiceHandler
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
	 * @author gupta.ashish
	 * @Date : 1st May 2014
	 * @Purpose :  To Validate Mini FATCA Details of a Customer
	 * @param sInputXML
	 * @return
	 */
	
	@SuppressWarnings("finally")
	public String getFATCAStatus( String sInputXML) throws RemoteException
	{
		LogGEN.writeTrace("Log", "Fuction called getFATCADetails");
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
			sHandler.readCabProperty("ValidateFATCAMini");				
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
			String customerSegement = xmlDataParser.getValueOf("customerSegment");
			String serviceType = xmlDataParser.getValueOf("serviceType");
			String product = xmlDataParser.getValueOf("product");
			String nationality = xmlDataParser.getValueOf("nationality");
			String residentialAddressCountry = xmlDataParser.getValueOf("residentialAddressCountry");
			String mailingAddressCountry = xmlDataParser.getValueOf("mailingAddressCountry");		
			String telephoneResidence = xmlDataParser.getValueOf("telephoneResidence");		
			String telephoneOffice = xmlDataParser.getValueOf("telephoneOffice");		
			String telephoneMobile = xmlDataParser.getValueOf("telephoneMobile");		
			String USpassportholder = xmlDataParser.getValueOf("USpassportholder");		
			String USTaxLiable = xmlDataParser.getValueOf("USTaxLiable");		
			String countryOfBirth = xmlDataParser.getValueOf("countryOfBirth");		
			String standingInstructionCountry = xmlDataParser.getValueOf("standingInstructionCountry");		
			String POAHolderCountry = xmlDataParser.getValueOf("POAHolderCountry");	
			String ssn = xmlDataParser.getValueOf("SSN");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			
			InqFATCAValidationStub stub = new InqFATCAValidationStub(sWSDLPath);
			InqFATCAValidationReqMsgChoice_type0 reqMessageChoice =  new InqFATCAValidationReqMsgChoice_type0();
			InqFATCAValidationReqMsg reqMessage = new InqFATCAValidationReqMsg();
			InqFATCAValidationReq_type0 reqType = new InqFATCAValidationReq_type0();
			InqFATCAValidationResMsgChoice_type0  resMessageChoice = new  InqFATCAValidationResMsgChoice_type0();
			InqFATCAValidationResMsg resMesage = new InqFATCAValidationResMsg();
			InqFATCAValidationRes_type0 resType = new InqFATCAValidationRes_type0();
			
			HeaderType Header_Input = new HeaderType();
			
			LogGEN.writeTrace("Log", "All Objects created");
			
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqFATCAValidation");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);				
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			
			reqMessage.setHeader(Header_Input);			
			reqType.setCustomerSegment(customerSegement);
			reqType.setServiceType(serviceType);
			reqType.setProduct(product);
			reqType.setNationality(nationality);
			reqType.setResidentialAddressCountry(residentialAddressCountry);
			reqType.setMailingAddressCountry(mailingAddressCountry);
			reqType.setTelephoneResidence(telephoneResidence);
			reqType.setTelephoneOffice(telephoneOffice);
			reqType.setTelephoneMobile(telephoneMobile);
			reqType.setUSpassportholder(USpassportholder);
			reqType.setUSTaxLiable(USTaxLiable);
			reqType.setCountryOfBirth(countryOfBirth);
			reqType.setStandingInstructionCountry(standingInstructionCountry);
			reqType.setPOAHolderCountry(POAHolderCountry);
			reqType.setSSN(ssn);	
			
			reqMessageChoice.setInqFATCAValidationReq(reqType);			
			reqMessage.setInqFATCAValidationReqMsgChoice_type0(reqMessageChoice);
			
			LogGEN.writeTrace("Log", "All values set");
			xmlInput=stub.getinputXML(reqMessage);
			
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			resMesage = stub.inqFATCAValidation_Oper(reqMessage);			
			sOrg_Output=stub.resFatcavMsg;//Added By Harish For inserting original mssg
			Header_Input = resMesage.getHeader();
			sReturnCode= Header_Input.getReturnCode();
		    sErrorDetail=Header_Input.getErrorDetail();
		    sErrorDesc = Header_Input.getErrorDescription();
		    
		    LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
		    LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
		    LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
		    
			if (sReturnCode.equalsIgnoreCase("0") && sErrorDesc.equalsIgnoreCase("Success")) // Green Case
			{
				 resMessageChoice = resMesage.getInqFATCAValidationResMsgChoice_type0();
				 resType = resMessageChoice.getInqFATCAValidationRes();
				 
				 sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
						 "<Output>" +
						 "<Option>InqFATCAValidation</Option>" +
						 "<returnCode>"+sReturnCode+"</returnCode>" +
						 "<errorDesc>"+sErrorDesc+"</errorDesc>" +
						 "<errorDetail>"+sErrorDetail+"</errorDetail>"+
						 "<customerSegment>"+resType.getCustomerSegment()+"</customerSegment>" +
						 "<serviceType>"+resType.getServiceType()+"</serviceType>"+
						 "<product>"+resType.getProduct()+"</product>"+
						 "<nationality>"+resType.getNationality()+"</nationality>"+
						 "<residentialAddressCountry>"+resType.getResidentialAddressCountry()+"</residentialAddressCountry>"+
						 "<mailingAddressCountry>"+resType.getMailingAddressCountry()+"</mailingAddressCountry>"+
						 "<telephoneResidence>"+resType.getTelephoneResidence()+"</telephoneResidence>"+
						 "<telephoneOffice>"+resType.getTelephoneOffice()+"</telephoneOffice>"+
						 "<telephoneMobile>"+resType.getTelephoneMobile()+"</telephoneMobile>"+
						 "<USpassportholder>"+resType.getUSpassportholder()+"</USpassportholder>"+
						 "<USTaxLiable>"+resType.getUSTaxLiable()+"</USTaxLiable>"+
						 "<countryOfBirth>"+resType.getCountryOfBirth()+"</countryOfBirth>"+
						 "<standingInstructionCountry>"+resType.getStandingInstructionCountry()+"</standingInstructionCountry>"+
						 "<POAHolderCountry>"+resType.getPOAHolderCountry()+"</POAHolderCountry>"+
						 "<returnValue>"+ resType.getReturnValue()+"</returnValue>" +
						 "</Output>"; 
			 }
			 else
			 {
				 sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
									"<Output>" +
									"<Option>InqFATCAValidation</Option>" +
									"<returnCode>"+sReturnCode+"</returnCode>" +
									"<errorDesc>"+sErrorDesc+"</errorDesc>" +
									"<errorDetail>"+sErrorDetail+"</errorDetail>"+
									"</Output>";
			 }
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqFATCAValidation</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Validate FATCA Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqFATCAValidation</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Validate FATCA Details.</td></Output>";
			}
			
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
			{
				Status="Failure";
			}
			
			try 
			{
				sHandler.readCabProperty("JTS");
			} 
			catch (Exception e) 
			{
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
			
	/*		String outputxml=sOutput;
			String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";
			
			LogGEN.writeTrace("Log",Query);*/
			
			try
			{
				 dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				 e.printStackTrace();
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
			