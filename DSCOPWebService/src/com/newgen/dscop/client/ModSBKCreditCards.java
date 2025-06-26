package com.newgen.dscop.client;

import java.io.IOException;
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
import com.newgen.dscop.stub.ModSBKCreditCardsStub;
import com.newgen.dscop.stub.ModSBKCreditCardsStub.AddCreditCardInfoReqMsg;
import com.newgen.dscop.stub.ModSBKCreditCardsStub.AddCreditCardInfoReq_type0;
import com.newgen.dscop.stub.ModSBKCreditCardsStub.AddCreditCardInfoResMsg;
import com.newgen.dscop.stub.ModSBKCreditCardsStub.AddCreditCardInfoRes_type0;
import com.newgen.dscop.stub.ModSBKCreditCardsStub.HeaderType;
public class ModSBKCreditCards extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String sOrgRes="";
	String xmlInput="";
	String sOrg_Chk_ouput=null;
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	
	public String AddCreditCard(String sInputXML)
	{
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called Add_CCInfo");
		LogGEN.writeTrace("CBG_Log", "ModSBKCreditCards sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		
		try
		{
			loadWSDLDtls(sHandler);
			String ref_no=xmlDataParser.getValueOf("Ref_No");
			String senderId = xmlDataParser.getValueOf("SenderId");
			ModSBKCreditCardsStub cc_stub=new ModSBKCreditCardsStub(sWSDLPath);
			AddCreditCardInfoReqMsg req_msg=new AddCreditCardInfoReqMsg();
			
			req_msg.setHeader(setHeaderDtls(sDate,ref_no,senderId));
			req_msg.setAddCreditCardInfoReq(setCreditCardDtl(sInputXML, xmlDataParser));
			cc_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=cc_stub.getinputXML(req_msg);
			LogGEN.writeTrace("CBG_Log", "ModSBKCreditCard inputXML "+xmlInput);
			AddCreditCardInfoResMsg res=cc_stub.addCreditCardInfo_Oper(req_msg);
			sOrgRes= cc_stub.resCC;
//			LogGEN.writeTrace("CBG_Log", "SOAP Response"+sOrgRes);
			
			HeaderType header= res.getHeader();
			sReturnCode=  header.getReturnCode();
			sErrorDetail=header.getErrorDetail();
			sErrorDesc = header.getErrorDescription();
			sErrorDesc = header.getErrorDescription();
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				AddCreditCardInfoRes_type0 ccRes_type0=new AddCreditCardInfoRes_type0();
				ccRes_type0=res.getAddCreditCardInfoRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>Add_CCInfo</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<addCreditCardInfoRes>"	
				+ "<creditCardNumber>"+ccRes_type0.getCreditCardNumber()+"</creditCardNumber>"
				+"<status>"+ccRes_type0.getStatus()+"</status>"
				+"<reason>"+ccRes_type0.getReason()+"</reason>"
				+"</addCreditCardInfoRes>"
				+"</Output>";				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_CCInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add credit card info </td></Output>";
			}
			
		}
		catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log",sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_CCInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add credit card info </td></Output>";
			e.printStackTrace();
			e.printStackTrace();
		}finally{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Add_CCInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to add credit card info </td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			loadJSTDtls(sHandler,"JTS");			
			LogGEN.writeTrace("CBG_Log", xmlInput);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "11111111111111111111%%%%%%%%%%%");
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}
	
	private void loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("Add_CCInfo");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Add_CCInfo");
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Add_CCInfo TIME_OUT: "+lTimeOut);
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}
	
	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("101");
		headerType.setServiceName("ModSBKCreditCards");
		headerType.setVersionNo("1.0");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("BPM-WMS");
		headerType.setReqTimeStamp(sDate);
		return headerType;
	}
	
	
	private AddCreditCardInfoReq_type0 setCreditCardDtl(String sInputXML,XMLParser xmlDataParser){
		xmlDataParser.setInputXML(sInputXML);
		AddCreditCardInfoReq_type0 ccReq_type0= new AddCreditCardInfoReq_type0();
		ccReq_type0.setActionIndicator(xmlDataParser.getValueOf("actionIndicator"));
		ccReq_type0.setCustomerID(xmlDataParser.getValueOf("customerID"));
		ccReq_type0.setCardNumber(xmlDataParser.getValueOf("cardNumber"));
		ccReq_type0.setEmbossName(xmlDataParser.getValueOf("embossName"));
		ccReq_type0.setOldCardNumber(xmlDataParser.getValueOf("oldCardNumber"));
		ccReq_type0.setVisionPlusAccNo(xmlDataParser.getValueOf("visionPlusAccNo"));
		ccReq_type0.setCardType(xmlDataParser.getValueOf("cardType"));
		ccReq_type0.setLogo(xmlDataParser.getValueOf("logo"));
		ccReq_type0.setCardLimit(xmlDataParser.getValueOf("cardLimit"));
		ccReq_type0.setCardStatus(xmlDataParser.getValueOf("cardStatus"));
		ccReq_type0.setCardissueDate(xmlDataParser.getValueOf("cardissueDate"));
		ccReq_type0.setCardExpiryDate(xmlDataParser.getValueOf("cardExpiryDate"));
		ccReq_type0.setBillingCycle(xmlDataParser.getValueOf("billingCycle"));
		ccReq_type0.setBranch(xmlDataParser.getValueOf("branch"));
		ccReq_type0.setCardProductName(xmlDataParser.getValueOf("cardProductName"));
		ccReq_type0.setDirectDebitday(xmlDataParser.getValueOf("directDebitday"));
		ccReq_type0.setDirectDebitAccNo(xmlDataParser.getValueOf("directDebitAccNo"));	
		return ccReq_type0;
	}
	
	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
