package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddDigitalDebitCardRequestStub;
import com.newgen.dscop.stub.AddDebitCardIssueStub.AddDebitCardIssueReqMsg;
import com.newgen.dscop.stub.AddDebitCardIssueStub.AddDebitCardIssueReq_type0;
import com.newgen.dscop.stub.AddDebitCardIssueStub.AddDebitCardIssueResMsg;
import com.newgen.dscop.stub.AddDebitCardIssueStub.AddDebitCardIssueRes_type0;
import com.newgen.dscop.stub.AddDigitalDebitCardRequestStub.AddDigitalSupplDCDtlsReqMsg;
import com.newgen.dscop.stub.AddDigitalDebitCardRequestStub.AddDigitalSupplDCDtlsReq_type0;
import com.newgen.dscop.stub.AddDigitalDebitCardRequestStub.AddDigitalSupplDCDtlsResMsg;
import com.newgen.dscop.stub.AddDigitalDebitCardRequestStub.AddDigitalSupplDCDtlsRes_type0;
import com.newgen.dscop.stub.AddDigitalDebitCardRequestStub.HeaderType;

public class AddDigitalDebitCardRequest extends DSCOPServiceHandler {
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String sOrgnlOutput = "";
	String sOrg_Output="";
	@SuppressWarnings("finally")
	public String AddDigitalDebitCards(String sInputXML) {

	LogGEN.writeTrace("CBG_Log", "Fuction called DebitEnquiry");
	LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCards sInputXML:" +sInputXML);
	XMLParser xmlParser = new XMLParser();
	xmlParser.setInputXML(sInputXML);
	String sOutput="";
	String sReturnCode= "";
	String sErrorDetail="";
	String sErrorDesc = "";
	Date d= new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	String sDate = dateFormat.format(d);

	try
	{
		//Reading params from Config.properties file:
		//sHandler.readCabProperty("Add_Debit_Card_Issuence");				

		LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
		ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddDigitalDebitCards");
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard WebServiceConfig Map : "  +wsConfig.toString());
		sWSDLPath=(String)wsConfig.get(0);
		sCabinet=(String)wsConfig.get(1);
		sUser=(String)wsConfig.get(2);
		sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
		sPassword=(String)wsConfig.get(4);
		lTimeOut=Long.valueOf((String)wsConfig.get(5));
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard WSDL PATH: "+sWSDLPath);
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard CABINET: "+sCabinet);
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard USER: "+sUser);
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard PASSWORD: "+sPassword);
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard LOGIN_REQ: "+sLoginReq);
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard TIME_OUT: "+lTimeOut);
		//Parsing inputXML to retrieve tag values: 
		String senderId = xmlParser.getValueOf("senderID");
		AddDigitalDebitCardRequestStub stub =new AddDigitalDebitCardRequestStub(sWSDLPath);
		
		String sCardPrimAC = xmlParser.getValueOf("primaryCardNumber");
		String sCardEmbossName = xmlParser.getValueOf("CardEmbossName");
		String sSuppCardCid = xmlParser.getValueOf("suppCardCID");
		String sInstantFlag = xmlParser.getValueOf("InstantFlag");
		String sWorkIdRefNbr = xmlParser.getValueOf("WorkIdRefNbr");
		String sInstantUpdateFlag = xmlParser.getValueOf("WorkIdRefNbr");
		String sChannelId = xmlParser.getValueOf("ChannelID");
		String ref_no = xmlParser.getValueOf("REF_NO");
		String sOrignalRefNo = xmlParser.getValueOf("OLDREF_NO");
		String sSuppCardNumber = xmlParser.getValueOf("suppCardNo");
		String srelationship = xmlParser.getValueOf("relationship");
		String stransactionRefNumber = xmlParser.getValueOf("transactionRefNumber");
		//LogGEN.writeTrace("CBG_Log", "CBG AddDebitCardIssue sCustomerID---"+sCustomerID);
		
		AddDigitalSupplDCDtlsReqMsg reqMsg = new AddDigitalSupplDCDtlsReqMsg();	
		AddDigitalSupplDCDtlsResMsg resMsg =new AddDigitalSupplDCDtlsResMsg();
		AddDigitalSupplDCDtlsReq_type0 reqType0 = new AddDigitalSupplDCDtlsReq_type0();
		reqType0.setPrimaryCardNumber(sCardPrimAC);
		reqType0.setEmbossName(sCardEmbossName);
		reqType0.setChannelId(sChannelId);
		reqType0.setInsertUpdateFlag(sInstantUpdateFlag);
		reqType0.setInstantFlag(sInstantFlag);
		reqType0.setWorkIdRefNo(sWorkIdRefNbr);
		reqType0.setOrginalReqRefNo(sOrignalRefNo);
		reqType0.setSuppCardHolderCid(sSuppCardCid);
		reqType0.setSuppCardNumber(sSuppCardNumber);
		reqType0.setRequestRefNo(ref_no);
		reqType0.setRelationship(srelationship);
		reqType0.setTransactionRefNumber(stransactionRefNumber);
		reqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard Before: "+ref_no);
		reqMsg.setAddDigitalSupplDCDtlsReq(reqType0);
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard After: "+ref_no);
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
		String  response = stub.addDigitalSupplDCDtls_Oper(reqMsg);
		xmlInput = stub.reqMsg;
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard Request: "+xmlInput);
		sOrg_Output = stub.resMsg;
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard Response: "+sOrg_Output);
		sOrg_Output = sOrg_Output.replaceAll("ns0:", "").replaceAll("ns1:", "");
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard Response: "+sOrg_Output);
		com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(sOrg_Output);
		sReturnCode = parser.getValueOf("returnCode");
		sOutput = sOrg_Output;
	} catch (Throwable e)
	{			
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard Error in Web Service :"+e.toString());
		LogGEN.writeTrace("CBG_Log", "AddDigitalDebitCard Error Trace in Web Service :"+e);
		sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddDigitalDebitCards</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
		e.printStackTrace();
	}
	finally
	{
		LogGEN.writeTrace("CBG_Log","AddDigitalDebitCard outputXML.trim().length() :"+sOutput.trim().length());				
		if(sOutput.trim().length()<1)
		{
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddDigitalDebitCards</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
		}
		String Status="";
		if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
			Status="Success";
		}
		 else 
			Status="Failure";
		 try {
			//sHandler.readCabProperty("JTS");
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String winame=xmlParser.getValueOf("winame");
			String sessionID= xmlParser.getValueOf("SessionId");
			String call_type1=xmlParser.getValueOf("DSCOPCallType");
			sCabinet=xmlParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error inserting---"+e.getMessage());
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","AddDigitalDebitCard Query : finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			}catch (Exception e2) {
			}
			LogGEN.writeTrace("CBG_Log","AddDigitalDebitCard outputXML : finally :"+sOutput);

		}
	return sOutput;		

		
	}		
	    private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("AddDigitalDebitCardRequest");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("addition");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId);
		headerType.setReqTimeStamp(sDate);		
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
	
}
