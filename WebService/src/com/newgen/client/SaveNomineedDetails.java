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

public class SaveNomineedDetails extends WebServiceHandler
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
	public String saveNomineeDetailsStatus(String sInputXML) throws RemoteException
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
			
			String nomineeName=xmlDataParser.getValueOf("nomineeName");
			String nomineeAddress1=xmlDataParser.getValueOf("nomineeAddress1");
			String nomineeCity=xmlDataParser.getValueOf("nomineeCity");
			String nomineeState=xmlDataParser.getValueOf("nomineeState");
			String nomineeCountry=xmlDataParser.getValueOf("nomineeCountry");
			String nomineeZipCode=xmlDataParser.getValueOf("nomineeZipCode");
			String nomineeEmail=xmlDataParser.getValueOf("nomineeEmail");
			String nomineePrefLangugage=xmlDataParser.getValueOf("nomineePrefLangugage");
			String nomineeMobileNo=xmlDataParser.getValueOf("nomineeMobileNo");
			String sMaintenanceOption=xmlDataParser.getValueOf("maintenanceOption");
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
			"<NomineedDetails>"+
				"<customerId>"+customerId+"</customerId>"+
				"<nomineeName>"+nomineeName+"</nomineeName>"+
				"<nomineeAddress1>"+nomineeAddress1+"</nomineeAddress1>"+
				"<nomineeCity>"+nomineeCity+"</nomineeCity>"+
				"<nomineeState>"+nomineeState+"</nomineeState>"+
				"<nomineeCountry>"+nomineeCountry+"</nomineeCountry>"+
				"<nomineeZipCode>"+nomineeZipCode+"</nomineeZipCode>"+
				"<nomineeEmail>"+nomineeEmail+"</nomineeEmail>"+
				"<nomineePrefLangugage>"+nomineePrefLangugage+"</nomineePrefLangugage>"+
				"<nomineeMobileNo>"+nomineeMobileNo+"</nomineeMobileNo>"+
					
			"</NomineedDetails>";

			
			reqType.setCustomerId(customerId);
			reqType.setCustomerInfoType("SaveNomineedDetails");
			reqType.setMaintenanceOption(sMaintenanceOption);
			reqType.setCustomerInformation(customerInformation);
			reqMessage.setModCustMDMPubInfoReq(reqType);
			
			
			
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
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save Nominee Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save Nominee Details.</td></Output>";
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
				
				/*String outputxml=sOutput;
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
				return sOutput;
		}
	}
}
