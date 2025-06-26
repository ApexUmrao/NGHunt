package com.newgen.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.ModCustMDMPubInfoStub;
import com.newgen.stub.ModCustMDMPubInfoStub.HeaderType;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReqMsg;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoReq_type0;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoResMsg;
import com.newgen.stub.ModCustMDMPubInfoStub.ModCustMDMPubInfoRes_type0;


public class WBG_AddFatca extends WebServiceHandler
{	
	String customerId = "";
	String xmlInput="";
	String sOrgRes="";
	String customerInfoType = "";
	String outputxml = "";
	String customerInformation = "";
	static String sWSDLPath = "";
	static String sCabinet = "";
	static String sUser = "";
	static String sPassword = "";
	String wi_name = "";
	String returnCode = "";
	String errorDetail = "";
	String errorDesc = "";
	String reason = "";
	String status = "";
	static long lTimeOut = 30000;
	
	static String dburl="";
	static String dbuser="";
	static String dbpass="";
	WebServiceHandler webHandler = null;
	static boolean sLoginReq = false;

	public String addFatcaDtls(String inputxml)
	{
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		webHandler = new WebServiceHandler();
		LogGEN.writeTrace("WBG_Log","ReadingCabProperty : saveFATCADetailsStatus call");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputxml);
		String custType = xmlDataParser.getValueOf("CustType");
		LogGEN.writeTrace("WBG_Log", "custType : " + custType);
		if(custType.equalsIgnoreCase("C"))
		{
			
			String customerClsfctn = xmlDataParser.getValueOf("customerClsfctn");			
			customerInfoType = xmlDataParser.getValueOf("customerInfoType");
			if (customerClsfctn.equalsIgnoreCase("Client"))
			{							
				if("Y".equalsIgnoreCase(xmlDataParser.getValueOf("OwnerDtlsFlag"))){
					String requiredxml =subStringMsg(inputxml, "OwnerInfo");
					int ownerDetailsTagIndex = requiredxml.indexOf("<OwnerDetails>");
					if(ownerDetailsTagIndex > 0)
					{
						LogGEN.writeTrace("WBG_Log","ELSE_CASE_OWNER_DETAILS PRESENT...");
						customerInformation = setCustomerInformation(xmlDataParser,inputxml,customerClsfctn)+setOwnerDtls(inputxml)+"</FATCA_NI_Details>";										
					}	
				}
				else
				{
					LogGEN.writeTrace("WBG_Log","ELSE_CASE_OWNER_DETAILS NOT PRESENT...");
					customerInformation = setCustomerInformation(xmlDataParser,inputxml,customerClsfctn)+"</FATCA_NI_Details>";	;					
				}								
			}
			else if (customerClsfctn.equalsIgnoreCase("Counter Party"))
			{
				customerInformation = setCustomerInformation(xmlDataParser,inputxml,customerClsfctn)+"</FATCA_NI_Details>";
				
			}
			LogGEN.writeTrace("WBG_Log","customerInformation (Counter Party..) : " + customerInformation);
		}		
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{			
			loadWSDLDtls(webHandler,"ADDFATCA_WBG");
			customerId = xmlDataParser.getValueOf("CustID");
			String maintenanceOption = xmlDataParser.getValueOf("maintenanceOption");
			ModCustMDMPubInfoStub stub = new ModCustMDMPubInfoStub(sWSDLPath);
			ModCustMDMPubInfoReq_type0 reqType = new ModCustMDMPubInfoReq_type0();
			ModCustMDMPubInfoReqMsg reqMessage = new ModCustMDMPubInfoReqMsg();

			ModCustMDMPubInfoRes_type0 resType = new ModCustMDMPubInfoRes_type0();
			ModCustMDMPubInfoResMsg resMessage = new ModCustMDMPubInfoResMsg();
						
			reqMessage.setHeader(setHeaderDtls(sDate,xmlDataParser.getValueOf("SENDERID")));
			reqType.setCustomerId(customerId);
			if(custType.equalsIgnoreCase("C"))
			{
				reqType.setCustomerInfoType(customerInfoType);
			}		
			reqType.setMaintenanceOption(maintenanceOption);
			reqType.setCustomerInformation(customerInformation);
			reqMessage.setModCustMDMPubInfoReq(reqType);
			xmlInput=stub.getinputXML(reqMessage);
		//	LogGEN.writeTrace("WBG_Log","Soap Request "+xmlInput);
			resMessage = stub.modCustMDMPubInfo_Oper(reqMessage);
			sOrgRes=stub.resEidaMsg;
			//LogGEN.writeTrace("WBG_Log","Soap Response "+sOrgRes);
			HeaderType headerType = resMessage.getHeader();
			sReturnCode=headerType.getReturnCode();
			sErrorDetail=headerType.getErrorDetail();
			sErrorDesc = headerType.getErrorDescription();
			if (sReturnCode.equalsIgnoreCase("0") && sErrorDesc.equalsIgnoreCase("Success"))
			{
				resType = resMessage.getModCustMDMPubInfoRes();
				reason = resType.getReason();
				status = resType.getStatus();			
				outputxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
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
				outputxml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
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
			LogGEN.writeTrace("WBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("WBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("WBG_Log",sw.toString());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			outputxml="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save FATCA Details.</td></Output>";
			e.printStackTrace();
		}	
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+outputxml.trim().length());				
			if(outputxml.trim().length()<1)
			{
				outputxml="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModCustMDMPubInfo</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to Save FATCA Details.</td></Output>";
			}	
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			try 
			{			
				loadJSTDtls(webHandler,"JTS");
				String inputXml=(xmlInput.replaceAll("&lt;", "<")).replaceAll("&gt;", ">");
				//LogGEN.writeTrace("WBG_Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("Calltype");
				//sCabinet=xmlDataParser.getValueOf("EngineName");
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("WBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
				e2.printStackTrace();
			}
			LogGEN.writeTrace("WBG_Log","finally :"+outputxml);
		}		
		return outputxml;		
	}

	private String getUniqueNo()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHMMS");
		String uniqueNo = sdf.format(date);
		LogGEN.writeTrace("WBG_Log", "uniqueNo : " + uniqueNo);
		return uniqueNo;
	}
	private String subStringMsg(String input,String tagName){
		String startTag="<"+tagName+">";
		String endTag="</"+tagName +">";
		return input.substring(input.indexOf(startTag), input.indexOf(endTag))+endTag;
	} 
	private WFXmlList xmlList(String input,String records,String record){
		WFXmlResponse xmlResponse = new WFXmlResponse(input);			
		WFXmlList lWfXml = xmlResponse.createList(records,record);
		return lWfXml;
	}
	private String setCustomerInformation(XMLParser xmlDataParser,String inputxml,String customerClsfctn){
		xmlDataParser.setInputXML(inputxml);

		String customerClsfctnDate = xmlDataParser.getValueOf("customerClsfctnDate");			
		String natureOfEntity = xmlDataParser.getValueOf("natureOfEntity");			
		String typeOfEntity = xmlDataParser.getValueOf("typeOfEntity");			
		String FATCAStatus = xmlDataParser.getValueOf("FATCAStatus");			
		String documentCollected = xmlDataParser.getValueOf("documentCollected");			
		String dateOfDocument = xmlDataParser.getValueOf("dateOfDocument");			
		String idntfctnNumRequired = xmlDataParser.getValueOf("idntfctnNumRequired");			
		String idntfctnNumber = xmlDataParser.getValueOf("idntfctnNumber");			
		String customerFATCAClsfctnDate = xmlDataParser.getValueOf("customerFATCAClsfctnDate");
		StringBuffer custInfo= new StringBuffer();
		custInfo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><FATCA_NI_Details>");
		custInfo.append("<customerClsfctn>");
		custInfo.append(customerClsfctn);
		custInfo.append("</customerClsfctn>");
		custInfo.append("<customerClsfctnDate>");
		custInfo.append(customerClsfctnDate);
		custInfo.append("</customerClsfctnDate>");
		custInfo.append("<natureOfEntity>");
		custInfo.append(natureOfEntity);
		custInfo.append("</natureOfEntity>");
		custInfo.append("<typeOfEntity>");
		custInfo.append(typeOfEntity);
		custInfo.append("</typeOfEntity>");
		custInfo.append("<FATCAStatus>");
		custInfo.append(FATCAStatus);
		custInfo.append("</FATCAStatus>");
		custInfo.append("<documentCollected>");
		custInfo.append(documentCollected);
		custInfo.append("</documentCollected>");
		custInfo.append("<dateOfDocument>");
		custInfo.append(dateOfDocument);
		custInfo.append("</dateOfDocument>");
		custInfo.append("<idntfctnNumRequired>");
		custInfo.append(idntfctnNumRequired);
		custInfo.append("</idntfctnNumRequired>");
		custInfo.append("<idntfctnNumber>");
		custInfo.append(idntfctnNumber);
		custInfo.append("</idntfctnNumber>");
		custInfo.append("<customerFATCAClsfctnDate>");
		custInfo.append(customerFATCAClsfctnDate);
		custInfo.append("</customerFATCAClsfctnDate>");
		return custInfo.toString();
	}
	private String setOwnerDtls(String inputxml){
		StringBuffer sbOwnerDtls= new StringBuffer();
		WFXmlList lWfXml = xmlList(inputxml, "OwnerInfo", "OwnerDetails");	
		for (int i = 0; lWfXml.hasMoreElements(true); lWfXml.skip(true), i++){
			sbOwnerDtls.append("<OwnerDetails>");
			sbOwnerDtls.append("<ownerNumber>");
			sbOwnerDtls.append(lWfXml.getVal("ownerNumber"));
			sbOwnerDtls.append("</ownerNumber>");
			sbOwnerDtls.append("<ownerName>");
			sbOwnerDtls.append(lWfXml.getVal("ownerName"));
			sbOwnerDtls.append("</ownerName>");
			sbOwnerDtls.append("<ownershipSharePercentage>");
			sbOwnerDtls.append(lWfXml.getVal("ownershipSharePercentage"));
			sbOwnerDtls.append("</ownershipSharePercentage>");
			sbOwnerDtls.append("<ownershipAddress>");
			sbOwnerDtls.append(lWfXml.getVal("ownershipAddress"));
			sbOwnerDtls.append("</ownershipAddress>");
			sbOwnerDtls.append("<ownerTINorSSN>");
			sbOwnerDtls.append(lWfXml.getVal("ownerTINorSSN"));
			sbOwnerDtls.append("</ownerTINorSSN>");
			sbOwnerDtls.append("<ownerW9Availability>");
			sbOwnerDtls.append(lWfXml.getVal("ownerW9Availability"));
			sbOwnerDtls.append("</ownerW9Availability>");
			sbOwnerDtls.append("</OwnerDetails>");
		}
		return sbOwnerDtls.toString();
	}
	private HeaderType setHeaderDtls(String sDate,String senderid){
		HeaderType headerType= new HeaderType();
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModCustMDMPubInfo");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setCorrelationID("");
		headerType.setSysRefNumber(getUniqueNo());
		headerType.setSenderID(webHandler.setSenderId(senderid)); 
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("WMS"); 
		return headerType;
	}
	private static void loadWSDLDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);
		//	webHandler.readCabProperty("AddFatca_WBG");	
			sWSDLPath = (String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet = (String)currentCabPropertyMap.get("CABINET");
			sUser = (String)currentCabPropertyMap.get("USER");
			sLoginReq = Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword = (String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			LogGEN.writeTrace("WBG_Log", "wsdlLocation: " + sWSDLPath);
			LogGEN.writeTrace("WBG_Log", "cabinetName: " + sCabinet);
			LogGEN.writeTrace("WBG_Log", "userName: " + sUser);
			LogGEN.writeTrace("WBG_Log", "password : " + sPassword);
			LogGEN.writeTrace("WBG_Log", "isLoginRequired : " + sLoginReq);
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	private static  void loadJSTDtls(WebServiceHandler sHandler,String tagName){
		try {
			sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
