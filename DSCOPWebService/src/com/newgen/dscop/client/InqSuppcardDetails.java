package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqSuppCardDetailsStub;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.HeaderType;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.InqSuppCardDetailsReqMsg;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.InqSuppCardDetailsReq_type0;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.InqSuppCardDetailsResMsg;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.InqSuppCardDetailsRes_type0;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.SuppCardDetails_type0;
import com.newgen.dscop.stub.InqSuppCardDetailsStub.SuppCards_type0;


public class InqSuppcardDetails extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	String xmlInput="";
	public String fetchSuppCardDetailCall(String sInputXML) 
	{LogGEN.writeTrace("CBG_Log", "Fuction called InqSuppCardDetailCall");
	LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall sInputXML---"+sInputXML);
	XMLParser xmlDataParser = new XMLParser();
	xmlDataParser.setInputXML(sInputXML);
	String sReturnCode= "";
	String sErrorDetail="";
	String sErrorDesc =  "";
	String sOutput= "";
	String customerId=xmlDataParser.getValueOf("CUST_ID");
	String cardType=xmlDataParser.getValueOf("cardType");
	String suppNumber=xmlDataParser.getValueOf("suppCardNumber");
	LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall sInputXML---"+customerId);
	LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall sInputXML---"+cardType);
	Date d= new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String sDate = dateFormat.format(d);
	DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
	try
	{
		LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
		ArrayList<String> wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("fetchSuppCardDetailCall");
		LogGEN.writeTrace("CBG_Log", "fetchSuppCardDetailCall WebServiceConfig Map : " + wsConfig.toString());
		sWSDLPath = (String) wsConfig.get(0);
		sCabinet = (String) wsConfig.get(1);
		sUser = (String) wsConfig.get(2);
		sLoginReq = Boolean.valueOf((String) wsConfig.get(3));
		sPassword = (String) wsConfig.get(4);
		lTimeOut = Long.valueOf((String) wsConfig.get(5));
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall WSDL PATH: " + sWSDLPath);
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall CABINET: " + sCabinet);
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall USER: " + sUser);
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall PASSWORD: " + sPassword);
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall LOGIN_REQ: " + sLoginReq);
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall TIME_OUT: " + lTimeOut);

		String ref_no = xmlDataParser.getValueOf("REF_NO");

		InqSuppCardDetailsStub stub = new InqSuppCardDetailsStub(sWSDLPath);
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);

		InqSuppCardDetailsReqMsg inqSuppCardDetailsReqMsg = new InqSuppCardDetailsReqMsg();
		InqSuppCardDetailsReq_type0 inqSuppCardDetailsReq_type0 = new InqSuppCardDetailsReq_type0();

		HeaderType Header_Input = new HeaderType();
		Header_Input.setUsecaseID("1234");
		Header_Input.setServiceName("InqSuppcardDetails");
		Header_Input.setVersionNo("1.0");
		Header_Input.setSysRefNumber(ref_no);
		Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
		Header_Input.setReqTimeStamp(sDate);
		Header_Input.setConsumer("");
		Header_Input.setServiceAction("Inquiry");
		Header_Input.setCorrelationID(ref_no);
		Header_Input.setUsername("1234");
		Header_Input.setCredentials(loggedinuser);

		LogGEN.writeTrace("CBG_Log", "All values set fetchSuppCardDetailCall");

		inqSuppCardDetailsReqMsg.setHeader(Header_Input);
		inqSuppCardDetailsReq_type0.setCustomerId(customerId);
		inqSuppCardDetailsReq_type0.setCardType(cardType);
		inqSuppCardDetailsReqMsg.setInqSuppCardDetailsReq(inqSuppCardDetailsReq_type0);

		xmlInput = stub.getInputXML(inqSuppCardDetailsReqMsg);
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall InputXML: " + xmlInput);
		InqSuppCardDetailsResMsg inqSuppCardDetailsResMsg = null;
		inqSuppCardDetailsResMsg = stub.inqSuppCardDetails_Oper(inqSuppCardDetailsReqMsg);

		sOrg_Output = stub.outputXML;
		LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall res_msg: " + sOrg_Output);
		InqSuppCardDetailsRes_type0 inqSuppCardDetailsRes_type0 = inqSuppCardDetailsResMsg.getInqSuppCardDetailsRes();
		inqSuppCardDetailsRes_type0 = inqSuppCardDetailsResMsg.getInqSuppCardDetailsRes();
		Header_Input = inqSuppCardDetailsResMsg.getHeader();
		sReturnCode = Header_Input.getReturnCode();
		sErrorDetail = Header_Input.getErrorDetail();
		System.out.println(sErrorDetail);
		if (sReturnCode.equalsIgnoreCase("0")) {
			if (inqSuppCardDetailsRes_type0.getSuppCardDetails() != null) {
				SuppCardDetails_type0 suppCardDetails_type0 = inqSuppCardDetailsRes_type0.getSuppCardDetails();

				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"
						+ "<Option>InqSuppcardDetails</Option>" + "<operation></operation>" + "<returnCode>"
						+ sReturnCode + "</returnCode>" + "<errorDescription>" + sErrorDetail + "</errorDescription>"
						+ "<InqSuppCardDetailsRes>" + "<customerId>" + inqSuppCardDetailsRes_type0.getCustomerId()
						+ "</customerId>" + "<cardType>" + inqSuppCardDetailsRes_type0.getCardType() + "</cardType>"
						+ "<suppCardDetails>";
				int totalRecords = inqSuppCardDetailsRes_type0.getSuppCardDetails().getSuppCards() != null
						? inqSuppCardDetailsRes_type0.getSuppCardDetails().getSuppCards().length
						: 0;
				LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall InputXML: " + totalRecords);
				LogGEN.writeTrace("CBG_Log", "InqSuppCardDetailCall InputXML: "
						+ inqSuppCardDetailsRes_type0.getSuppCardDetails().getSuppCards());
				StringBuffer tmp = new StringBuffer();
				if (totalRecords > 0) {
					SuppCards_type0[] SuppCardsInfo = suppCardDetails_type0.getSuppCards();
					for (int counter = 0; counter < totalRecords; counter++) {
						tmp.append("<suppCards>" +
					            "<primaryCardNumber>" + SuppCardsInfo[counter].getPrimaryCardNumber()+"</primaryCardNumber>" 
					            + "<suppCardNumber>"+SuppCardsInfo[counter].getSuppCardNumber()+"</suppCardNumber>"
								+ "<suppCardEmbossName>" + SuppCardsInfo[counter].getSuppCardEmbossName()+"</suppCardEmbossName>" 
								+ "<suppCardProductDesc>"+ SuppCardsInfo[counter].getSuppCardProductDesc()+"</suppCardProductDesc>"
								+ "<suppCardStatus>" + SuppCardsInfo[counter].getSuppCardStatus() +"</suppCardStatus>"
								+ "<suppCardMobileNumber>" + SuppCardsInfo[counter].getSuppCardMobileNumber()+ "</suppCardMobileNumber>" 
								+ "<suppCardEmailAddress>"+ SuppCardsInfo[counter].getSuppCardEmailAddress() + "</suppCardEmailAddress>"
								+ "<suppCardDOB>" + SuppCardsInfo[counter].getSuppCardDOB()+ "</suppCardDOB>"
								+ "<suppCardGender>" + SuppCardsInfo[counter].getSuppCardGender()+ "</suppCardGender>"
								+ "<suppCardRelationWithPrimary>"+ SuppCardsInfo[counter].getSuppCardRelationWithPrimary()+ "</suppCardRelationWithPrimary>"
								+ "</suppCards>");
					}
					tmp.append("</suppCardDetails>" + "</InqSuppCardDetailsRes>" + "</Output>");
					sOutput = sOutput + tmp.toString();
				}
				LogGEN.writeTrace("CBG_Log", "output xml within if block fetchSuppCardDetailCall:" + sOutput);
			} else {
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqSuppcardDetails</Option><Output><returnCode>"
						+ sReturnCode + "</returnCode><errorDescription>" + sErrorDetail
						+ "</errorDescription><Status>Unable to add Supplimentry Details</Status></Output>";
			}
		}
	}
	catch (Exception e)
	{
		LogGEN.writeTrace("CBG_Log", "Error in Web Serviic fetchSuppCardDetailCall :"+e.toString());
		LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice fetchSuppCardDetailCall :"+e.getStackTrace());
		sReturnCode="-1";
		sErrorDetail=e.getMessage();
		sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqSuppcardDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add Supplimentry Details</Status></Output>";
		e.printStackTrace();
	}
	finally
	{
		LogGEN.writeTrace("CBG_Log","outputXML.trim().length() InqSuppCardDetailCall:"+sOutput.trim().length());	
		LogGEN.writeTrace("CBG_Log","outputXML.trim().length() InqSuppCardDetailCall:"+sOutput);	

		if(sOutput.trim().length()<1)
		{
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqSuppcardDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add Supplimentry Details</Status></Output>";
		}
		String Status="";
		if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
		{
			Status="Success";
		}
		else
			Status="Failure";
	
		String dburl=(String)currentCabPropertyMap.get("DBURL");
		String dbuser=(String)currentCabPropertyMap.get("USER");
		String dbpass=(String)currentCabPropertyMap.get("PASS");

		String inputXml=xmlInput;
		LogGEN.writeTrace("CBG_Log", "InqSuppcardDetails" + inputXml);
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

		String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
		LogGEN.writeTrace("CBG_Log","InqSuppCardDetailCall Query : finally :"+Query);
		try {
			con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOutput.replaceAll("'", "''"));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	return sOutput;		
}
}