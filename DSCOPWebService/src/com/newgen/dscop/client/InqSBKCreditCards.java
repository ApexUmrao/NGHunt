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
import com.newgen.dscop.stub.InqSBKCreditCardsStub;
import com.newgen.dscop.stub.InqSBKCreditCardsStub.GetKioskListReqMsg;
import com.newgen.dscop.stub.InqSBKCreditCardsStub.GetKioskListReq_type0;
import com.newgen.dscop.stub.InqSBKCreditCardsStub.GetKioskListResMsg;
import com.newgen.dscop.stub.InqSBKCreditCardsStub.GetKioskListRes_type0;
import com.newgen.dscop.stub.InqSBKCreditCardsStub.HeaderType;
import com.newgen.dscop.stub.InqSBKCreditCardsStub.Kiosks_type0;

public class InqSBKCreditCards extends DSCOPServiceHandler  {

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	public String getKioskList(String sInputXML){

		LogGEN.writeTrace("CBG_Log", "Fuction called getKioskList");
		LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		String xmlInput="";
		String sOutput= "";
		String ccBin=xmlDataParser.getValueOf("CreditCardBIN");

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block of InqSBKCreditCards");
			String wsdl=loadWSDLDtls(sHandler);
			String ref_no=xmlDataParser.getValueOf("Ref_No");
			String senderID=xmlDataParser.getValueOf("senderID");

			InqSBKCreditCardsStub kiosk_list_stub = new InqSBKCreditCardsStub(wsdl);
			GetKioskListReqMsg req_msg=new GetKioskListReqMsg();
			GetKioskListReq_type0 req_type0=new GetKioskListReq_type0();
			GetKioskListResMsg res_msg=new GetKioskListResMsg();
			req_msg.setHeader(setHeaderDtls(sDate,ref_no,senderID));
			req_type0.setCreditCardBIN(ccBin);
			req_msg.setGetKioskListReq(req_type0);
			kiosk_list_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = kiosk_list_stub.getInputXml(req_msg);
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards InputXML: " + xmlInput);
			res_msg=kiosk_list_stub.getKioskList_Oper(req_msg);
			sOrg_Output = kiosk_list_stub.outputXML;
			//			LogGEN.writeTrace("CBG_Log", "resCustEmiratesIDAuthDtls OutputXML: " + InqCustEmiratesIdAuthDtls_output);


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

				GetKioskListRes_type0 resType0 = new GetKioskListRes_type0();
				resType0 = res_msg.getGetKioskListRes();

				Kiosks_type0[] kisksType = resType0.getKioskDetails().getKiosks();
				StringBuffer kioskList = new StringBuffer();
				kioskList.append("[");
				for (int i = 0; i < kisksType.length; i++) {

					kioskList.append(kisksType[i].getKioskID());
					if(i+1 != kisksType.length){
						kioskList.append(",");
					}
				}
				kioskList.append("]");

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddCSAcctAmountHold</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<AddCSAcctAmountHold>"+
						"<kioskList>"+ kioskList.toString() +"</kioskList>"+
						"</AddCSAcctAmountHold>"+
						"</Response>"+
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block InqSBKCreditCards:" + sOutput);

			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqSBKCreditCards</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch kiosk list</td></Output>";
			}

		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqSBKCreditCards</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch kiosk list</td></Output>";
			e.printStackTrace();

		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqSBKCreditCards</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch kiosk list</td></Output>";
			}		
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");

				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
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
			//sHandler.readCabProperty("InqSBKCreditCards");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqSBKCreditCards");
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards TIME_OUT: "+lTimeOut);
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
		headerType.setServiceName("InqSBKCreditCards");
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
}
