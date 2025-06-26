package com.newgen.dscop.client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.AuthenticationDetails_type0;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.CustomerInformation_type0;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.EidaApplicationDetails_type0;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.HeaderType;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReqMsg;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsReq_type0;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsResMsg;
import com.newgen.dscop.stub.InqCustEmiratesIDAuthDtlsStub.InqCustEmiratesIDAuthDtlsRes_type0;

public class InqCustEmiratesIDAuthDtls extends DSCOPServiceHandler  {
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String InqCustEmiratesIdAuthDtls_output="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	
	public String FetchCustEmiratesIdAuthDtls(String sInputXML){
		
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called FetchCustEmiratesIdAuthDtls");
		LogGEN.writeTrace("CBG_Log", "InqCustEmiratesIDAuthDtls sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		String xmlInput="";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);			
		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdl=loadWSDLDtls(sHandler);
			String ref_no=xmlDataParser.getValueOf("Ref_No");
			String senderID=xmlDataParser.getValueOf("senderID");
			InqCustEmiratesIDAuthDtlsStub cust_emirates_stub=new InqCustEmiratesIDAuthDtlsStub(wsdl);
			InqCustEmiratesIDAuthDtlsReqMsg req_msg=new InqCustEmiratesIDAuthDtlsReqMsg();
			InqCustEmiratesIDAuthDtlsReq_type0 req_type0=new InqCustEmiratesIDAuthDtlsReq_type0();
			InqCustEmiratesIDAuthDtlsResMsg res_msg=new InqCustEmiratesIDAuthDtlsResMsg();
			req_msg.setHeader(setHeaderDtls(sDate,ref_no,senderID));
			req_type0.setChannelId(xmlDataParser.getValueOf("channelId"));
			req_type0.setChannelReferenceNumber(xmlDataParser.getValueOf("channelReferenceNumber"));
			req_type0.setInquryType(xmlDataParser.getValueOf("inquryType"));
			req_type0.setPhotoRequestFlag(xmlDataParser.getValueOf("photoRequestFlag"));
			req_msg.setInqCustEmiratesIDAuthDtlsReq(req_type0);
			cust_emirates_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = cust_emirates_stub.getInputXml(req_msg);
			LogGEN.writeTrace("CBG_Log", "InputXML: " + xmlInput);
			res_msg=cust_emirates_stub.inqCustEmiratesIDAuthDtls_Oper(req_msg);
			InqCustEmiratesIdAuthDtls_output = cust_emirates_stub.resCustEmiratesIDAuthDtls;
//			LogGEN.writeTrace("CBG_Log", "resCustEmiratesIDAuthDtls OutputXML: " + InqCustEmiratesIdAuthDtls_output);
			
//			LogGEN.writeTrace("CBG_Log", "res_msg"+res_msg);
			HeaderType header=res_msg.getHeader();
			LogGEN.writeTrace("CBG_Log", "res_msg.getHeader()"+res_msg.getHeader());
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode()"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail()"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription()"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				InqCustEmiratesIDAuthDtlsRes_type0 res_type0=new InqCustEmiratesIDAuthDtlsRes_type0();
				res_type0=res_msg.getInqCustEmiratesIDAuthDtlsRes();
				AuthenticationDetails_type0 authenticationDetails_type0= new AuthenticationDetails_type0();
				CustomerInformation_type0 customerInformation_type0=new CustomerInformation_type0();
//				PhotoImage_type0 photoImage_type0=new PhotoImage_type0();
				EidaApplicationDetails_type0 eidaApplicationDetails_type0=new EidaApplicationDetails_type0();
				
				authenticationDetails_type0=res_type0.getAuthenticationDetails();
				customerInformation_type0=res_type0.getCustomerInformation();
//				photoImage_type0=res_type0.getPhotoImage();
				eidaApplicationDetails_type0=res_type0.getEidaApplicationDetails();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>Inq_Cust_EmriatesID</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<InqCustEmiratesIDAuthDtlsRes>"+
	            "<channelId>"+res_type0.getChannelId()+"</channelId>"+
	            "<channelReferenceNumber>"+res_type0.getChannelReferenceNumber()+"</channelReferenceNumber>"+
	            "<inquryType>"+res_type0.getInquryType()+"</inquryType>"+
	            "<photoRequestFlag>"+res_type0.getPhotoRequestFlag()+"</photoRequestFlag>"+
	            "<authenticationDetails>"+
	               "<eidaCardNumber>"+authenticationDetails_type0.getEidaCardNumber().trim()+"</eidaCardNumber>"+
	               "<eidaCardHolderName>"+authenticationDetails_type0.getEidaCardHolderName().trim()+"</eidaCardHolderName>"+
	               "<authReferenceNumber>"+authenticationDetails_type0.getAuthReferenceNumber().trim()+"</authReferenceNumber>"+
	               "<fingerIndex>"+authenticationDetails_type0.getFingerIndex().trim()+"</fingerIndex>"+
	               "<authStaus>"+authenticationDetails_type0.getAuthStaus().trim()+"</authStaus>"+
	               "<authenticationType>"+authenticationDetails_type0.getAuthenticationType().trim()+"</authenticationType>"+
	               "<authDateTime>"+authenticationDetails_type0.getAuthDateTime().trim()+"</authDateTime>"+
	               "<errorCode>"+authenticationDetails_type0.getErrorCode().trim()+"</errorCode>"+
	               "<errorMsg>"+authenticationDetails_type0.getErrorMsg().trim()+"</errorMsg>"+
	            "</authenticationDetails>"+
	            "<customerInformation>"+
	               "<IdType>"+customerInformation_type0.getIdType().trim()+"</IdType>"+
	               "<cardSerialNumber>"+customerInformation_type0.getCardSerialNumber().trim()+"</cardSerialNumber>"+
	               "<cardNumber>"+customerInformation_type0.getCardNumber().trim()+"</cardNumber>"+
	               "<issueDate>"+customerInformation_type0.getIssueDate().trim()+"</issueDate>"+
	               "<expiryDate>"+customerInformation_type0.getExpiryDate().trim()+"</expiryDate>"+
	               "<gender>"+customerInformation_type0.getGender().trim()+"</gender>"+
	               "<dateOfBirth>"+customerInformation_type0.getDateOfBirth().trim()+"</dateOfBirth>"+
	               "<maritalStatus>"+customerInformation_type0.getMaritalStatus().trim()+"</maritalStatus>"+
	               "<occupation>"+customerInformation_type0.getOccupation().trim()+"</occupation>"+
	               "<title>"+customerInformation_type0.getTitle().trim()+"</title>"+
	               "<nationality>"+customerInformation_type0.getNationality().trim()+"</nationality>"+
	               "<motherFirstName>"+customerInformation_type0.getMotherFirstName().trim()+"</motherFirstName>"+
	               "<familyId>"+customerInformation_type0.getFamilyId().trim()+"</familyId>"+
	               "<husbandIdn>"+customerInformation_type0.getHusbandIdn().trim()+"</husbandIdn>"+
	               "<sponsorType>"+customerInformation_type0.getSponsorType().trim()+"</sponsorType>"+
	               "<sponsorNumber>"+customerInformation_type0.getSponsorNumber().trim()+"</sponsorNumber>"+
	               "<sponsorName>"+customerInformation_type0.getSponsorName().trim()+"</sponsorName>"+
	               "<residencyType>"+customerInformation_type0.getResidencyType().trim()+"</residencyType>"+
	               "<residencyNumber>"+customerInformation_type0.getResidencyNumber().trim()+"</residencyNumber>"+
	               "<residencyExpiryDate>"+customerInformation_type0.getResidencyExpiryDate().trim()+"</residencyExpiryDate>"+
	               "<passportNumber>"+customerInformation_type0.getPassportNumber().trim()+"</passportNumber>"+
	               "<passportCountry>"+customerInformation_type0.getPassportCountry().trim()+"</passportCountry>"+
	               "<passportCountryDesc>"+customerInformation_type0.getPassportCountryDesc().trim()+"</passportCountryDesc>"+
	               "<placeOfBirth>"+customerInformation_type0.getPlaceOfBirth().trim()+"</placeOfBirth>"+
	               "<passportType>"+customerInformation_type0.getPassportType().trim()+"</passportType>"+
	               "<passportIssueDate>"+customerInformation_type0.getPassportIssueDate().trim()+"</passportIssueDate>"+
	               "<passportExpiryDate>"+customerInformation_type0.getPassportExpiryDate().trim()+"</passportExpiryDate>"+
	               "<homeAddressEmirateDesc>"+customerInformation_type0.getHomeAddressEmirateDesc().trim()+"</homeAddressEmirateDesc>"+
	               "<homeAddressCityCode>"+customerInformation_type0.getHomeAddressCityCode().trim()+"</homeAddressCityCode>"+
	               "<homeAddressCityDesc>"+customerInformation_type0.getHomeAddressCityDesc().trim()+"</homeAddressCityDesc>"+
	               "<homeAddressStreet>"+customerInformation_type0.getHomeAddressStreet().trim()+"</homeAddressStreet>"+
	               "<homeAddressAreaDesc>"+customerInformation_type0.getHomeAddressAreaDesc().trim()+"</homeAddressAreaDesc>"+
	               "<homeAddressBuilding>"+customerInformation_type0.getHomeAddressBuilding().trim()+"</homeAddressBuilding>"+
	               "<homeAddressAreaCode>"+customerInformation_type0.getHomeAddressAreaCode().trim()+"</homeAddressAreaCode>"+
	               "<homeAddressFlatNumber>"+customerInformation_type0.getHomeAddressFlatNumber().trim()+"</homeAddressTypeCode>"+
	               "<homeAddressTypeCode>"+customerInformation_type0.getHomeAddressTypeCode().trim()+"</homeAddressTypeCode>"+
	               "<homeAddressLocationCode>"+customerInformation_type0.getHomeAddressLocationCode().trim()+"</homeAddressLocationCode>"+
	               "<homeAddressResidentPhoneNumber>"+customerInformation_type0.getHomeAddressResidentPhoneNumber().trim()+"</homeAddressResidentPhoneNumber>"+
	               "<homeAddressMobilePhoneNumber>"+customerInformation_type0.getHomeAddressMobilePhoneNumber().trim()+"</homeAddressMobilePhoneNumber>"+
	               "<homeAddressPoBox>"+customerInformation_type0.getHomeAddressPoBox().trim()+"</homeAddressPoBox>"+
	               "<homeAddressEmail>"+customerInformation_type0.getHomeAddressEmail().trim()+"</homeAddressEmail>"+
	               "<homeAddressEmirateCode>"+customerInformation_type0.getHomeAddressEmirateCode().trim()+"</homeAddressEmirateCode>"+
	               "<workAddressCompanyName>"+customerInformation_type0.getWorkAddressCompanyName().trim()+"</workAddressCompanyName>"+
	               "<workAddressEmirateDesc>"+customerInformation_type0.getWorkAddressEmirateDesc().trim()+"</workAddressEmirateDesc>"+
	               "<workAddressCityCode>"+customerInformation_type0.getWorkAddressCityCode().trim()+"</workAddressCityCode>"+
	               "<workAddressCityDesc>"+customerInformation_type0.getWorkAddressCityDesc().trim()+"</workAddressCityDesc>"+
	               "<workAddressStreet>"+customerInformation_type0.getWorkAddressStreet().trim()+"</workAddressStreet>"+
	               "<workAddressAreaDesc>"+customerInformation_type0.getWorkAddressAreaDesc().trim()+"</workAddressAreaDesc>"+
	               "<workAddressBuilding>"+customerInformation_type0.getWorkAddressBuilding().trim()+"</workAddressBuilding>"+
	               "<workAddressAreaCode>"+customerInformation_type0.getWorkAddressAreaCode().trim()+"</workAddressAreaCode>"+
	               "<workAddressTypeCode>"+customerInformation_type0.getWorkAddressTypeCode().trim()+"</workAddressTypeCode>"+
	               "<workAddressLocationCode>"+customerInformation_type0.getWorkAddressLocationCode().trim()+"</workAddressLocationCode>"+
	               "<workAddressLandPhoneNumber>"+customerInformation_type0.getWorkAddressLandPhoneNumber().trim()+"</workAddressLandPhoneNumber>"+
	               "<workAddressMobilePhoneNumber>"+customerInformation_type0.getWorkAddressMobilePhoneNumber().trim()+"</workAddressMobilePhoneNumber>"+
	               "<workAddressPoBox>"+customerInformation_type0.getWorkAddressPoBox().trim()+"</workAddressPoBox>"+
	               "<workAddressEmirateCode>"+customerInformation_type0.getWorkAddressEmirateCode().trim()+"</workAddressEmirateCode>"+
	               "<workAddressEmail>"+customerInformation_type0.getWorkAddressEmail().trim()+"</workAddressEmail>"+
	            "</customerInformation>"+
//	            "<photoImage>"+
//	               "<imageBinaryData>"+photoImage_type0.getImageBinaryData()+"</imageBinaryData>"+
//	            "</photoImage>"+
	            "<eidaApplicationDetails>"+
	               "<eidaUserID>"+eidaApplicationDetails_type0.getEidaUserID().trim()+"</eidaUserID>"+
	               "<eidaUserName>"+eidaApplicationDetails_type0.getEidaUserName().trim()+"</eidaUserName>"+
	               "<eidaIPAddress>"+eidaApplicationDetails_type0.getEidaIPAddress().trim()+"</eidaIPAddress>"+
	               "<eidaBranchCode>"+eidaApplicationDetails_type0.getEidaBranchCode().trim()+"</eidaBranchCode>"+
	            "</eidaApplicationDetails>"+
	         "</InqCustEmiratesIDAuthDtlsRes>"+
				
				
				"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block-----InqCustEmiratesIdAuthDtls  "+sOutput);
				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Inq_Cust_EmriatesID</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer emirates Id </td></Output>";
			}
			
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Inq_Cust_EmriatesID</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer emirates Id  </td></Output>";
			e.printStackTrace();
			
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Inq_Cust_EmriatesID</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer emirates Id </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),InqCustEmiratesIdAuthDtls_output.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return sOutput;
	}
	
	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("Cust_Emirates_Authentication");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Cust_Emirates_Authentication");
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Cust_Emirates_Authentication TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderID){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("12345");
		headerType.setServiceName("InqCustEmiratesIDAuthDtls");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderID); 
		headerType.setConsumer("BPM-WMS");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("DSFS");
		headerType.setCredentials("DSFS");
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		
		 
		return headerType;
	}
	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
