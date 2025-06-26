package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCBStatementStub;
import com.newgen.dscop.stub.ModCBStatementStub.AddCustStatementReqMsg;
import com.newgen.dscop.stub.ModCBStatementStub.AddCustStatementReq_type0;
import com.newgen.dscop.stub.ModCBStatementStub.AddCustStatementResMsg;
import com.newgen.dscop.stub.ModCBStatementStub.AddCustStatementRes_type0;
import com.newgen.dscop.stub.ModCBStatementStub.HeaderType;

public class AddCustStatement extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String addCustStatement(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called AddCustStatement");
		LogGEN.writeTrace("CBG_Log", "AddCustStatement sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";

		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModCBStatement");
			LogGEN.writeTrace("CBG_Log", "AddCustStatement WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModCBStatement WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModCBStatement CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModCBStatement USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModCBStatement PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModCBStatement LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModCBStatement TIME_OUT: "+lTimeOut);

			ModCBStatementStub request_stub=new ModCBStatementStub(sWSDLPath);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String appNumber = xmlDataParser.getValueOf("appNumber");
			String bankID = xmlDataParser.getValueOf("bankID");
			String customerFullName = xmlDataParser.getValueOf("customerFullName");
			String fromDate = xmlDataParser.getValueOf("fromDate");
			String groupName = xmlDataParser.getValueOf("groupName");
			String ibanNumber = xmlDataParser.getValueOf("ibanNumber");
			String idNumber = xmlDataParser.getValueOf("idNumber");
			String idType = xmlDataParser.getValueOf("idType");
			String productType = xmlDataParser.getValueOf("productType");
			String requestedBy = xmlDataParser.getValueOf("requestedBy");
			String requestorEmailID = xmlDataParser.getValueOf("requestorEmailID");
			String requestorSystemName = xmlDataParser.getValueOf("requestorSystemName");
			String toDate = xmlDataParser.getValueOf("toDate");

			AddCustStatementReqMsg reqMsg = new AddCustStatementReqMsg();
			AddCustStatementReq_type0 reqMsg_type0 = new AddCustStatementReq_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("ModCBStatement");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			reqMsg_type0.setAppNumber(appNumber);
			reqMsg_type0.setBankID(bankID);
			reqMsg_type0.setCustomerFullName(customerFullName);
			reqMsg_type0.setFromDate(fromDate);
			reqMsg_type0.setGroupName(groupName);
			reqMsg_type0.setIbanNumber(ibanNumber);
			reqMsg_type0.setIdNumber(idNumber);
			reqMsg_type0.setIdType(idType);
			reqMsg_type0.setProductType(productType);
			reqMsg_type0.setRequestedBy(requestedBy);
			reqMsg_type0.setRequestorEmailID(requestorEmailID);
			reqMsg_type0.setRequestorSystemName(requestorSystemName);
			reqMsg_type0.setToDate(toDate);

			reqMsg.setAddCustStatementReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);

			com.newgen.dscop.stub.ModCBStatementStub.AddCustStatementResMsg response_msg = new AddCustStatementResMsg();
			AddCustStatementRes_type0 response_type0 = new AddCustStatementRes_type0();
			response_msg = request_stub.addCustStatementRequest_Oper(reqMsg);

			xmlInput = request_stub.getInputXml(reqMsg);
			LogGEN.writeTrace("CBG_Log", "AddCustStatement xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddCustStatementResMsg sOrg_put: "+sOrg_put);

			Header_Input=response_msg.getHeader();

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			response_type0 = response_msg.getAddCustStatementRes();
			String status="", refID="", statusCode="";
			if(response_type0 != null){
				status = response_type0.getStatus();
				refID = response_type0.getReturnResponseRefID();
				statusCode = response_type0.getStatusCode();
			}
					
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1") || !refID.equals(""))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddCustStatement</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddCustStatementRes>"+
						"<status>"+status+"</status>" +
						"<statusCode>"+statusCode+"</statusCode>"+
						"<ReturnResponseRefID>"+refID+"</ReturnResponseRefID>"+
						"</AddCustStatementRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquire AddCustStatement</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquire AddCustStatement</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_ADDCUST</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquire AddCustStatement</Output>";
			}

			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);

			String Status="";
			if(sReturnCode.equalsIgnoreCase("0"))
			{
				Status="Success";
			}
			else if(sReturnCode.equalsIgnoreCase("2"))
				Status="Partial Success";
			else
			{
				Status="Failure";
			}

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			LogGEN.writeTrace("CBG_Log", "WINAME:"+winame);
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
				e.printStackTrace();
			}
			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","AddCustStatement  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","AddCustStatement  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}