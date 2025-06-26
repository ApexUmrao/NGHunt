package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerSearchStub;
import com.newgen.dscop.stub.InqCustomerSearchStub.Customers_type0;
import com.newgen.dscop.stub.InqCustomerSearchStub.HeaderType;
import com.newgen.dscop.stub.InqCustomerSearchStub.InqCustomerSearchReqMsg;
import com.newgen.dscop.stub.InqCustomerSearchStub.InqCustomerSearchReq_type0;
import com.newgen.dscop.stub.InqCustomerSearchStub.InqCustomerSearchResMsg;
import com.newgen.dscop.stub.InqCustomerSearchStub.InqCustomerSearchRes_type0;

public class InqCustomerSearch extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";

	@SuppressWarnings("finally")
	public String SearchCustomer(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqCustomerSearch");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String sOrg_Output="";			
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try {
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerSearch");
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath = (String)wsConfig.get(0);
			sCabinet = (String)wsConfig.get(1);
			sUser = (String)wsConfig.get(2);
			sLoginReq = Boolean.valueOf((String)wsConfig.get(3));
			sPassword = (String)wsConfig.get(4);
			lTimeOut = Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerSearch TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);

			String sCustomerID = xmlDataParser.getValueOf("CUST_ID");
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+sCustomerID);		
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate+" sWSDLPath "+sWSDLPath);

			InqCustomerSearchStub stub = null;
			try
			{
				stub = new InqCustomerSearchStub(sWSDLPath);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "Error---"+e.getMessage());
			}

			InqCustomerSearchReqMsg reqMsg = new InqCustomerSearchReqMsg();
			InqCustomerSearchResMsg resMsg = new InqCustomerSearchResMsg();
			InqCustomerSearchReq_type0 reqType0 = new InqCustomerSearchReq_type0();
			reqType0.setCustomerId(sCustomerID);
			reqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			reqMsg.setInqCustomerSearchReq(reqType0);
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);

			resMsg = stub.inqCustomerSearch_Oper(reqMsg);
			xmlInput = stub.request;
			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			sOrg_Output = stub.response;
			LogGEN.writeTrace("CBG_Log", "sOrg_Output---"+sOrg_Output);

			HeaderType Header_Input = resMsg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);		    
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "Successful Result");
				InqCustomerSearchRes_type0 resType0 = resMsg.getInqCustomerSearchRes();
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustomerSearch</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<InqCustomerSearchRes>"+
						"<customers>";
				Customers_type0[] cust = resType0.getCustomers();
				if(cust != null && (cust.length > 0)){
					sOutput = sOutput + "<custCategoryCode>"+ cust[0].getCustCategoryCode() +"</custCategoryCode>"
							+ "<custStaffFlag>"+ cust[0].getCustStaffFlag() +"</custStaffFlag>"
							+ "<custHomeBranch>"+ cust[0].getCustHomeBranch() +"</custHomeBranch>"; //COP-2922
				}		    	
				sOutput = sOutput + "</customers></InqCustomerSearchRes>";
				LogGEN.writeTrace("CBG_Log", "Output XML--- "+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerSearch</Option><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e);
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerSearch</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>InqCustomerSearch</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
				Status="Success";
			}
			else {
				Status="Failure";
			}

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			//sOrg_Output=xmlInput;
			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type1=xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet=xmlDataParser.getValueOf("EngineName");
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
			//String retVal=con.execute("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query);


			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));

			//LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}

	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCustomerSearch");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId);
		headerType.setReqTimeStamp(sDate);		
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
