package com.newgen.dscop.client;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqFATCAValidationStub;
import com.newgen.dscop.stub.InqFATCAValidationStub.HeaderType;
import com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReq2_type0;
import com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsg;
import com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationReqMsgChoice_type0;
import com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationRes2_type0;
import com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsg;
import com.newgen.dscop.stub.InqFATCAValidationStub.InqFATCAValidationResMsgChoice_type0;

public class ValidateFATCAFull extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_Output=null;

	
	@SuppressWarnings("finally")
	public String getFATCAStatus(String sInputXML) throws RemoteException
	{	
		LogGEN.writeTrace("CBG_Log", "Fuction called getFATCADetails");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("ValidateFATCAFull");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ValidateFATCAFull");
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ValidateFATCAFull TIME_OUT: "+lTimeOut);
			
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
			String USIndiaciaFound = xmlDataParser.getValueOf("USIndiaciaFound");		
			String documentCollected = xmlDataParser.getValueOf("documentCollected");		
			String TINorSSN = xmlDataParser.getValueOf("TINorSSN");		
			String customerFATCAClsfctn = xmlDataParser.getValueOf("customerFATCAClsfctn");		
			String customerFATCAClsfctnDate = xmlDataParser.getValueOf("customerFATCAClsfctnDate");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String sW8SignDate =xmlDataParser.getValueOf("W8_Sign_Date");
			
			InqFATCAValidationStub stub = new InqFATCAValidationStub(sWSDLPath);
			InqFATCAValidationReqMsgChoice_type0 reqMessageChoice =  new InqFATCAValidationReqMsgChoice_type0();
			InqFATCAValidationReqMsg reqMessage = new InqFATCAValidationReqMsg();
			InqFATCAValidationReq2_type0 req2Type = new InqFATCAValidationReq2_type0();
			InqFATCAValidationResMsgChoice_type0  resMessageChoice = new  InqFATCAValidationResMsgChoice_type0();
			InqFATCAValidationResMsg resMesage = new InqFATCAValidationResMsg();		
			InqFATCAValidationRes2_type0 res2Type = new  InqFATCAValidationRes2_type0(); 			
			HeaderType Header_Input = new HeaderType();
			 
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqFATCAValidation");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);				
			Header_Input.setUsername(sCustomerID);
			Header_Input.setCredentials(loggedinuser);
			 
			reqMessage.setHeader(Header_Input);
			req2Type.setCustomerSegment(customerSegement);
			req2Type.setServiceType(serviceType);
			req2Type.setProduct(product);
			req2Type.setNationality(nationality);
			req2Type.setResidentialAddressCountry(residentialAddressCountry);
			req2Type.setMailingAddressCountry(mailingAddressCountry);
			req2Type.setTelephoneResidence(telephoneResidence);
			req2Type.setTelephoneOffice(telephoneOffice);
			req2Type.setTelephoneMobile(telephoneMobile);
			req2Type.setUSpassportholder(USpassportholder);
			req2Type.setUSTaxLiable(USTaxLiable);
			req2Type.setCountryOfBirth(countryOfBirth);
			req2Type.setStandingInstructionCountry(standingInstructionCountry);
			req2Type.setPOAHolderCountry(POAHolderCountry);
			req2Type.setUSIndiaciaFound(USIndiaciaFound);
			req2Type.setDocumentCollected(documentCollected);
			req2Type.setTINorSSN(TINorSSN);
			req2Type.setCustomerFATCAClsfctn(customerFATCAClsfctn);
			req2Type.setCustomerFATCAClsfctnDate(customerFATCAClsfctnDate);
			req2Type.setW8SignDate(sW8SignDate);
			
			reqMessageChoice.setInqFATCAValidationReq2(req2Type);			 
			reqMessage.setInqFATCAValidationReqMsgChoice_type0(reqMessageChoice);
			xmlInput=stub.getinputXML(reqMessage);
			LogGEN.writeTrace("CBG_Log", "validateFullFATCA xmlInput "+xmlInput);
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			resMesage = stub.inqFATCAValidation_Oper(reqMessage);	
			sOrg_Output=stub.resFatcavMsg;//Added By Harish For inserting original mssg
//			LogGEN.writeTrace("CBG_Log", "validateFullFATCA sOrg_Output "+sOrg_Output);
			Header_Input = resMesage.getHeader();
			 
			sReturnCode= Header_Input.getReturnCode();
		    sErrorDetail=Header_Input.getErrorDetail();
		    sErrorDesc = Header_Input.getErrorDescription();
		    
		    LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
		    LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
		    LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
			 
			if (sReturnCode.equalsIgnoreCase("0") && sErrorDesc.equalsIgnoreCase("Success"))
			{
				 resMessageChoice = resMesage.getInqFATCAValidationResMsgChoice_type0();
				 res2Type = resMessageChoice.getInqFATCAValidationRes2();
				 //returnValueAsFullResponse = res2Type.getReturnValue();
				 
				 sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
							"<Output>" +
							"<Option>ValidateFATCA</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDesc>"+sErrorDesc+"</errorDesc>" +
							"<errorDetail>"+sErrorDetail+"</errorDetail>"+
							"<customerSegment>"+ res2Type.getCustomerSegment()+"</customerSegment>"+
							"<serviceType>"+res2Type.getServiceType() +"</serviceType>"+
							"<product>"+res2Type.getProduct() +"</product>"+
							"<nationality>"+res2Type.getNationality() +"</nationality>"+
							"<residentialAddressCountry>"+res2Type.getResidentialAddressCountry() +"</residentialAddressCountry>"+
							"<mailingAddressCountry>"+ res2Type.getMailingAddressCountry()+"</mailingAddressCountry>"+
							"<telephoneResidence>"+res2Type.getTelephoneResidence() +"</telephoneResidence>"+
							"<telephoneOffice>"+res2Type.getTelephoneOffice() +"</telephoneOffice>"+
							"<telephoneMobile>"+res2Type.getTelephoneOffice() +"</telephoneMobile>"+
							"<USpassportholder>"+res2Type.getUSpassportholder() +"</USpassportholder>"+
							"<USTaxLiable>"+res2Type.getUSTaxLiable() +"</USTaxLiable>"+
							"<countryOfBirth>"+res2Type.getCountryOfBirth() +"</countryOfBirth>"+
							"<standingInstructionCountry>"+res2Type.getStandingInstructionCountry() +"</standingInstructionCountry>"+
							"<POAHolderCountry>"+res2Type.getPOAHolderCountry() +"</POAHolderCountry>"+
							"<USIndiaciaFound>"+res2Type.getUSIndiaciaFound() +"</USIndiaciaFound>"+
							"<documentCollected>"+res2Type.getDocumentCollected() +"</documentCollected>"+
							"<TINorSSN>"+res2Type.getTINorSSN() +"</TINorSSN>"+
							"<customerFATCAClsfctn>"+res2Type.getCustomerFATCAClsfctn() +"</customerFATCAClsfctn>"+
							"<customerFATCAClsfctnDate>"+res2Type.getCustomerFATCAClsfctnDate() +"</customerFATCAClsfctnDate>"+
							"<W8SignDate>"+res2Type.getW8SignDate() +"</W8SignDate>"+
							"<returnValue>"+res2Type.getReturnValue() +"</returnValue>"+
							"</Output>";
			 }
			 else
			 {
				 sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
							"<Output>" +
							"<Option>ValidateFATCA</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDesc>"+sErrorDesc+"</errorDesc>" +
							"</Output>";
			 }
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ValidateFATCA</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch FATCA Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ValidateFATCA</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Fetch FATCA Details.</td></Output>";
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
				//sHandler.readCabProperty("JTS");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");
			
			
			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			
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
				 String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
				 LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
//				 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
					e2.getMessage();
				}
			
			LogGEN.writeTrace("CBG_Log","finally :"+sOutput);
			return sOutput;	
		}
	}
	
}
		
