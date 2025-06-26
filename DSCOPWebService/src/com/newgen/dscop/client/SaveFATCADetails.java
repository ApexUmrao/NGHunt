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
import com.newgen.dscop.stub.ModCustMDMPubInfoStub;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.HeaderType;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReqMsg;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReq_type0;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoResMsg;
import com.newgen.dscop.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoRes_type0;

public class SaveFATCADetails extends DSCOPServiceHandler
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
	String sOrg_Output=null;

	
	@SuppressWarnings("finally")
	public String saveFATCADetailsStatus(String sInputXML) throws RemoteException
	{	
		LogGEN.writeTrace("CBG_Log", "Fuction called getFATCADetails");
		LogGEN.writeTrace("CBG_Log", "inputxml  getFATCADetails " );
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
			//sHandler.readCabProperty("SaveFATCADetails");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("SaveFATCADetails");
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "SaveFATCADetails TIME_OUT: "+lTimeOut);

			
			String customerId = xmlDataParser.getValueOf("customerId");
			String USpassportHolder = xmlDataParser.getValueOf("USpassportholder");
			String USTaxLiable = xmlDataParser.getValueOf("USTaxLiable");
			String TINorSSN = xmlDataParser.getValueOf("TINorSSN");
			String sW8SignUpDate = xmlDataParser.getValueOf("W8_Sign_Date");
			String customerFATCAClsfctnDate = xmlDataParser.getValueOf("customerFATCAClsfctnDate");
			String documentCollected = xmlDataParser.getValueOf("documentCollected");
			String USIndiaciaFound = xmlDataParser.getValueOf("USIndiaciaFound");
			String customerFATCAClsfctn = xmlDataParser.getValueOf("customerFATCAClsfctn");
			String countryOfBirth = xmlDataParser.getValueOf("countryOfBirth");
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String sMaintenanceOption=xmlDataParser.getValueOf("maintenanceOption");
			String sPOAHolder = xmlDataParser.getValueOf("POAHolder");		
			String SItoUSBeneficiary = xmlDataParser.getValueOf("SItoUSBeneficiary");				
			
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
														"<FATCADetails>"+
															"<USpassportholder>"+USpassportHolder+"</USpassportholder>"+
															"<USTaxLiable>"+USTaxLiable+"</USTaxLiable>"+
															"<TINorSSN>"+TINorSSN+"</TINorSSN>"+
															"<W8_Sign_Date>"+sW8SignUpDate+"</W8_Sign_Date>"+
															"<customerFATCAClsfctnDate>"+customerFATCAClsfctnDate+"</customerFATCAClsfctnDate>"+
															"<documentCollected>"+documentCollected+"</documentCollected>"+
															"<USIndiaciaFound>"+USIndiaciaFound+"</USIndiaciaFound>"+
															"<customerFATCAClsfctn>"+customerFATCAClsfctn+"</customerFATCAClsfctn>"+
															"<countryOfBirth>"+countryOfBirth+"</countryOfBirth>"+
															"<SItoUSBeneficiary>"+SItoUSBeneficiary+"</SItoUSBeneficiary>"+ 
															"<POAtoPersonwithUSAddr>"+sPOAHolder+"</POAtoPersonwithUSAddr>"+ 
														"</FATCADetails>";

			reqType.setCustomerId(customerId);
			reqType.setCustomerInfoType("FATCADetails");
			reqType.setMaintenanceOption(sMaintenanceOption);
			reqType.setCustomerInformation(customerInformation);
			reqMessage.setModCustMDMPubInfoReq(reqType);
			xmlInput=stub.getinputXML(reqMessage);
//			LogGEN.writeTrace("CBG_Log", "xmlInput for savefatcadetails "+xmlInput);
			
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			resMessage = stub.modCustMDMPubInfo_Oper(reqMessage);	
			sOrg_Output=stub.resEidaMsg;//Added By Harish For inserting original mssg
//			LogGEN.writeTrace("CBG_Log", "xmlInput for sOrg_Output "+sOrg_Output);
			Header_Input = resMessage.getHeader();
			sReturnCode= Header_Input.getReturnCode();
		    sErrorDetail=Header_Input.getErrorDetail();
		    sErrorDesc = Header_Input.getErrorDescription();
		    
		    LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
		    LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
		    LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);
				

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
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save FATCA Details.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save FATCA Details.</td></Output>";
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
			
			
			String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
			//String outputxml=sOutput;
		/*	String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"','"+ inputXml.replaceAll("'", "''") +"',to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"'"+ outputxml.replaceAll("'", "''") +"',sysdate,'"+  Status + "')";*/
		
			
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
				 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
					e2.getMessage();
				}
//			LogGEN.writeTrace("CBG_Log","finally :"+sOutput);
			return sOutput;	
		}
	}
}