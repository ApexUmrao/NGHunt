package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModLoanDisbursementStub;
import com.newgen.dscop.stub.ModLoanDisbursementStub.CancelLoanRecordReqMsg;
import com.newgen.dscop.stub.ModLoanDisbursementStub.CancelLoanRecordReq_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.CancelLoanRecordResMsg;
import com.newgen.dscop.stub.ModLoanDisbursementStub.CancelLoanRecordRes_type0;
import com.newgen.dscop.stub.ModLoanDisbursementStub.HeaderType;

public class CancelLoanRecord extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String cancelLoanRecordCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called CancelLoanRecord");
		LogGEN.writeTrace("CBG_Log", "CancelLoanRecord sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModLoanDisbursement");
			LogGEN.writeTrace("CBG_Log", "CancelLoanRecord WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModLoanDisbursement TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerId = xmlDataParser.getValueOf("customerId");
			String loanNumber = xmlDataParser.getValueOf("loanNumber");
			String remarks = xmlDataParser.getValueOf("remarks");

			ModLoanDisbursementStub request_stub=new ModLoanDisbursementStub(sWSDLPath);
			CancelLoanRecordReqMsg cancel_req_dtls = new CancelLoanRecordReqMsg();			
			CancelLoanRecordReq_type0 cancel_req_type0 = new CancelLoanRecordReq_type0();
			CancelLoanRecordResMsg cancel_response = new CancelLoanRecordResMsg();
			CancelLoanRecordRes_type0 cancel_response_type0 = new CancelLoanRecordRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("ModLoanDisbursement");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			cancel_req_type0.setCustomerId(customerId);
			cancel_req_type0.setLoanNumber(loanNumber);
			cancel_req_type0.setRemarks(remarks);
			cancel_req_dtls.setCancelLoanRecordReq(cancel_req_type0);
			cancel_req_dtls.setHeader(Header_Input);
			xmlInput= request_stub.getCancelLoanXML(cancel_req_dtls);
			LogGEN.writeTrace("CBG_Log", "CancelLoanRecord xmlInput xml : "+xmlInput);
			cancel_response = request_stub.cancelLoanRecord_Oper(cancel_req_dtls);

			Header_Input = cancel_response.getHeader();
			cancel_response_type0 = cancel_response.getCancelLoanRecordRes();
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "CancelLoanRecordResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				if(cancel_response_type0 != null){
					String custId = cancel_response_type0.getCustomerId();
					String loanAccountNumber = cancel_response_type0.getLoanAccountNumber();
					String loanStatus = cancel_response_type0.getLoanStatus();
					String responseCode = cancel_response_type0.getResponseCode();
					String responseMessage = cancel_response_type0.getResponseMessage();
					
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>CancelLoanRecord</Option>" +
							"<returnCode>"+sReturnCode+"</returnCode>" +
							"<errorDescription>"+sErrorDesc+"</errorDescription>"+
							"<errorDetail>"+sErrorDetail+"</errorDetail>"+
							"<CancelLoanRecordRes>"+
							"<customerId>"+custId+"</customerId>"+
							"<loanAccountNumber>"+loanAccountNumber+"</loanAccountNumber>"+
							"<loanStatus>"+loanStatus+"</loanStatus>"+
							"<responseCode>"+responseCode+"</responseCode>"+
							"<responseMessage>"+responseMessage+"</responseMessage>"+
							"</CancelLoanRecordRes>"+	
							"</Output>";
				}
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CancelLoanRecord</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Cancel  Loan Record</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CancelLoanRecord</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Cancel  Loan Record</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CancelLoanRecord</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Cancel  Loan Record</Output>";
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

			}
			DBConnection con=new DBConnection();
			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","CancelLoanRecord  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","CancelLoanRecord  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}