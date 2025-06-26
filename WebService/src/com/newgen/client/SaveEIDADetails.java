package com.newgen.client;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.newgen.AESEncryption;
import com.newgen.stub.ModCustMDMPubInfoStub;
import com.newgen.stub.ModCustMDMPubInfoStub.HeaderType;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReqMsg;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReq_type0;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoResMsg;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoRes_type0;

public class SaveEIDADetails extends WebServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String reason ="";
	String status ="";
	String sOrg_Output=null;//Added By Harish For inserting original mssg
	/**
	 * @author gupta.ashish
	 * @Date : 21st April 2014
	 * @Purpose :  To Save FATCA Details of a Customer
	 * @param sInputXML
	 * @return
	 */
	
	@SuppressWarnings("finally")
	public String saveEIDADetailsStatus(String sInputXML) throws RemoteException
	{
	
		LogGEN.writeTrace("Log", "Fuction called saveEIDADetailsStatus");
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
			sHandler.readCabProperty("SaveFATCADetails");				
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
			
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId = xmlDataParser.getValueOf("customerId");
			
			String EIDANo=xmlDataParser.getValueOf("EIDANo");
			String prefix=xmlDataParser.getValueOf("prefix");
			String full_Name=xmlDataParser.getValueOf("full_Name");
			String motherName=xmlDataParser.getValueOf("motherName");
			String POBox=xmlDataParser.getValueOf("POBox");
			String address2=xmlDataParser.getValueOf("address2");
			String address3=xmlDataParser.getValueOf("address3");
			String city=xmlDataParser.getValueOf("city");
			String sMaintenanceOption=xmlDataParser.getValueOf("maintenanceOption");
			
			
			String stateEmirate=xmlDataParser.getValueOf("stateEmirate");
			String country=xmlDataParser.getValueOf("country");
			String phoneNo=xmlDataParser.getValueOf("phoneNo");
			String mobileNo=xmlDataParser.getValueOf("mobileNo");
			String email=xmlDataParser.getValueOf("email");
			
			
			String dateOfBirth=xmlDataParser.getValueOf("dateOfBirth");
			String passportNo=xmlDataParser.getValueOf("passportNo");
			String passportIssueDate=xmlDataParser.getValueOf("passportIssueDate");
			String passportExpiryDate=xmlDataParser.getValueOf("passportExpiryDate");
			String passportType=xmlDataParser.getValueOf("passportType");
			String passportCountry=xmlDataParser.getValueOf("passportCountry");
			String nationality=xmlDataParser.getValueOf("nationality");
			String profession=xmlDataParser.getValueOf("profession");
			String gender=xmlDataParser.getValueOf("gender");
			String employerName=xmlDataParser.getValueOf("employerName");
			String EIDAIssueDate=xmlDataParser.getValueOf("EIDAIssueDate");
			String maritalStatus=xmlDataParser.getValueOf("maritalStatus");
			String cardNumber=xmlDataParser.getValueOf("cardNumber");
			 
			
			String sponsorType=xmlDataParser.getValueOf("sponsorType");
			String sponsorNumber=xmlDataParser.getValueOf("sponsorNumber");
			String residencyType=xmlDataParser.getValueOf("residencyType");
			String residencyNumber=xmlDataParser.getValueOf("residencyNumber");
			String residencyExpiryDate=xmlDataParser.getValueOf("residencyExpiryDate");
			String placeOfBirth=xmlDataParser.getValueOf("placeOfBirth");
			String EIDAExpiryDate=xmlDataParser.getValueOf("EIDAExpiryDate");
			 
			ModCustMDMPubInfoStub stub = new ModCustMDMPubInfoStub(sWSDLPath);
			HeaderType Header_Input = new HeaderType();
			ModCustMDMPubInfoReq_type0 reqType = new ModCustMDMPubInfoReq_type0();
			ModCustMDMPubInfoReqMsg reqMessage = new ModCustMDMPubInfoReqMsg();
			ModCustMDMPubInfoRes_type0 resType = new ModCustMDMPubInfoRes_type0();
			ModCustMDMPubInfoResMsg resMessage = new ModCustMDMPubInfoResMsg();
			

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCustMDMPubInfo");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(customerId);
			Header_Input.setCredentials(loggedinuser);
			
			reqMessage.setHeader(Header_Input);
			
			String customerInformation = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			"<EIDADetails>"+				
			"<EIDANo>"+EIDANo+"</EIDANo>"+
			"<prefix>"+prefix+"</prefix>"+
			"<full_Name>"+full_Name+"</full_Name>"+
			"<motherName>"+motherName+"</motherName>"+
			"<EIDAAddresses>"+
				"<Addresses>"+
					"<addressType>local</addressType>"+
					"<POBox>"+POBox+"</POBox>"+
					"<address2>"+address2+"</address2>"+
					"<address3>"+address3+"</address3>"+
					"<city>"+city+"</city>"+				
					"<stateEmirate>"+stateEmirate+"</stateEmirate>"+
					"<country>"+country+"</country>"+
					"<phoneNo>"+phoneNo+"</phoneNo>"+
					"<mobileNo>"+mobileNo+"</mobileNo>"+
					"<email>"+email+"</email>"+
				"</Addresses>"+
			"</EIDAAddresses>"+
			"<dateOfBirth>"+dateOfBirth+"</dateOfBirth>"+
			"<passportNo>"+passportNo+"</passportNo>"+
			"<passportIssueDate>"+passportIssueDate+"</passportIssueDate>"+
			"<passportExpiryDate>"+passportExpiryDate+"</passportExpiryDate>"+				
			"<passportType>"+passportType+"</passportType>"+
			"<passportCountry>"+passportCountry+"</passportCountry>"+
			"<nationality>"+nationality+"</nationality>"+
			"<profession>"+profession+"</profession>"+
			"<gender>"+gender+"</gender>"+
			"<employerName>"+employerName+"</employerName>"+
			"<EIDAIssueDate>"+EIDAIssueDate+"</EIDAIssueDate>"+
			"<maritalStatus>"+maritalStatus+"</maritalStatus>"+
			"<cardNumber>"+cardNumber+"</cardNumber>"+					
			"<sponsorType>"+sponsorType+"</sponsorType>"+
			"<sponsorNumber>"+sponsorNumber+"</sponsorNumber>"+
			"<residencyType>"+residencyType+"</residencyType>"+
			"<residencyNumber>"+residencyNumber+"</residencyNumber>"+
			"<residencyExpiryDate>"+residencyExpiryDate+"</residencyExpiryDate>"+
			"<placeOfBirth>"+placeOfBirth+"</placeOfBirth>"+
			"<EIDAExpiryDate>"+EIDAExpiryDate+"</EIDAExpiryDate>"+
		"</EIDADetails>";

			reqType.setCustomerId(customerId);
			reqType.setCustomerInfoType("EIDADetails");
			reqType.setMaintenanceOption(sMaintenanceOption);
			reqType.setCustomerInformation(customerInformation);
			reqMessage.setModCustMDMPubInfoReq(reqType);
			LogGEN.writeTrace("Log", "customerInformation---- "+customerInformation);
			xmlInput=stub.getinputXML(reqMessage);
			
			resMessage = stub.modCustMDMPubInfo_Oper(reqMessage);	
			sOrg_Output=stub.resEidaMsg;//Added By Harish For inserting original mssg
			Header_Input = resMessage.getHeader();
			sReturnCode= Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("Log", "Error Description---"+sErrorDesc);
			
			
			if (sReturnCode.equalsIgnoreCase("0") && sErrorDesc.equalsIgnoreCase("Success"))
			{
				resType = resMessage.getModCustMDMPubInfoRes();
				reason = resType.getReason();
				status = resType.getStatus();			
				
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>ModCustMDMPubInfo</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<Reason>"+reason+"</Reason>" +
				"<customerId>"+resType.getCustomerId()+"</customerId>"+
				"<customerInfoType>"+resType.getCustomerInfoType()+"</customerInfoType>"+
				"<customerInformation>"+resType.getCustomerInformation()+"</customerInformation>"+
				"<Status>"+status+"</Status>" +											
				"</Output>";
			}
			else
			{
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<Output>" +
				"<Option>ModCustMDMPubInfo</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDesc>"+sErrorDesc+"</errorDesc>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"</Output>";
			}	
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save EIDA Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save EIDA Details.</td></Output>";
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
				
				
				String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
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
				
				
				return sOutput;
		}
	}
}
