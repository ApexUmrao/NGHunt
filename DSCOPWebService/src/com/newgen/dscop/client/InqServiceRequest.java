package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqServiceRequestStub;
import com.newgen.dscop.stub.InqServiceRequestStub.HeaderType;
import com.newgen.dscop.stub.InqServiceRequestStub.InqServiceRequestReqMsg;
import com.newgen.dscop.stub.InqServiceRequestStub.InqServiceRequestReq_type0;
import com.newgen.dscop.stub.InqServiceRequestStub.InqServiceRequestResMsg;
import com.newgen.dscop.stub.InqServiceRequestStub.InqServiceRequestRes_type0;
import com.newgen.dscop.stub.InqServiceRequestStub.ListofdocumentId_type0;
import com.newgen.dscop.stub.InqServiceRequestStub.ServiceRequests_type0;

public class InqServiceRequest extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqServiceRequest(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqServiceRequest");
		LogGEN.writeTrace("CBG_Log", "InqServiceRequest sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqServiceRequest");
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("senderIDReq");
			String dateFrom = xmlDataParser.getValueOf("dateFrom");
			String dateTo=xmlDataParser.getValueOf("dateTo");
			String customerId = xmlDataParser.getValueOf("customerId");
			String initiatorId = xmlDataParser.getValueOf("initiatorId");

			InqServiceRequestStub request_stub=new InqServiceRequestStub(sWSDLPath);
			InqServiceRequestReqMsg service_req = new InqServiceRequestReqMsg();			
			InqServiceRequestReq_type0 service_req_type0 = new InqServiceRequestReq_type0();
			InqServiceRequestResMsg service_response = new InqServiceRequestResMsg();
			InqServiceRequestRes_type0 service_response_type0 = new InqServiceRequestRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqServiceRequest");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			service_req_type0.setSenderId(senderId);
			service_req_type0.setDateFrom(dateFrom);
			service_req_type0.setDateTo(dateTo);
			service_req_type0.setCustomerId(customerId);
			service_req_type0.setInitiatorId(initiatorId);
			
			service_req.setInqServiceRequestReq(service_req_type0);
			service_req.setHeader(Header_Input);
			service_response = request_stub.inqServiceRequest_Oper(service_req);

			xmlInput= request_stub.getInputXML(service_req);
			Header_Input = service_response.getHeader();
			service_response_type0 = service_response.getInqServiceRequestRes();
			LogGEN.writeTrace("CBG_Log", "InqServiceRequest xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqServiceRequestResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(service_response_type0 != null){
					ServiceRequests_type0 serviceReq[] = service_response_type0.getServiceRequests();
					if(serviceReq != null){
						for(ServiceRequests_type0 detailsType0 : serviceReq) {
							String SRNumber = detailsType0.getSRNumber();
							String INSArea = detailsType0.getINSArea();
							String INSProduct = detailsType0.getINSProduct();
							String initiationDate = detailsType0.getInitiationDate();
							String completionDate = detailsType0.getCompletionDate();
							String expectedTAT = detailsType0.getExpectedTAT();
							String currentTAT = detailsType0.getCurrentTAT();
							String SRDepartment = detailsType0.getSRDepartment();
							String ownerDepartment = detailsType0.getOwnerDepartment();
							String reasonForFailure = detailsType0.getReasonForFailure();
							String accountNumber = detailsType0.getAccountNumber();
							String SRStatus = detailsType0.getSRStatus();
							String remarks = detailsType0.getRemarks();
							String rejectReasonCMD = detailsType0.getRejectReasonCMD();
							String resolRejectDescription = detailsType0.getResolRejectDescription();
							String resolRejectType = detailsType0.getResolRejectType();
							String custId = detailsType0.getCustomerId();
							String customerName = detailsType0.getCustomerName();
							String coronaCaseId = detailsType0.getCoronaCaseId();
							String requestType = detailsType0.getRequestType();
							String requestSubType = detailsType0.getRequestSubType();
							String extRefNumber = detailsType0.getExtRefNumber();
							String processingDate = detailsType0.getProcessingDate();
							String transactionSource = detailsType0.getTransactionSource();
							String valDate = detailsType0.getValDate();
							String amount = detailsType0.getAmount();
							String currencyCode = detailsType0.getCurrencyCode();
							String commentsHistory = detailsType0.getCommentsHistory();
							String complaintId = detailsType0.getComplaintId();
							String tranRefNumeber = detailsType0.getTranRefNumeber();
							String docID = "";
							ListofdocumentId_type0 ListofdocumentId[] = detailsType0.getListofdocumentId();
							if(ListofdocumentId !=null){
								for(ListofdocumentId_type0 ListofdocumentID : ListofdocumentId){
								 docID = ListofdocumentID.getDocumentId();
							}
							}
							details.append("<serviceRequests>").append("\n")
							.append("<SRNumber>"+SRNumber+"</SRNumber>").append("\n")
							.append("<INSArea>"+INSArea+"</INSArea>").append("\n")
							.append("<INSProduct>"+INSProduct+"</INSProduct>").append("\n")
							.append("<initiationDate>"+initiationDate+"</initiationDate>").append("\n")
							.append("<completionDate>"+completionDate+"</completionDate>").append("\n")
							.append("<expectedTAT>"+expectedTAT+"</expectedTAT>").append("\n")
							.append("<currentTAT>"+currentTAT+"</currentTAT>").append("\n")
							.append("<SRDepartment>"+SRDepartment+"</SRDepartment>").append("\n")
							.append("<ownerDepartment>"+ownerDepartment+"</ownerDepartment>").append("\n")
							.append("<reasonForFailure>"+reasonForFailure+"</reasonForFailure>").append("\n")
							.append("<accountNumber>"+accountNumber+"</accountNumber>").append("\n")
							.append("<SRStatus>"+SRStatus+"</SRStatus>").append("\n")
							.append("<remarks>"+remarks+"</remarks>").append("\n")
							.append("<rejectReasonCMD>"+rejectReasonCMD+"</rejectReasonCMD>").append("\n")
							.append("<resolRejectDescription>"+resolRejectDescription+"</resolRejectDescription>").append("\n")
							.append("<resolRejectType>"+resolRejectType+"</resolRejectType>").append("\n")
							.append("<customerId>"+custId+"</customerId>").append("\n")
							.append("<customerName>"+customerName+"</customerName>").append("\n")
							.append("<coronaCaseId>"+coronaCaseId+"</coronaCaseId>").append("\n")
							.append("<requestType>"+requestType+"</requestType>").append("\n")
							.append("<requestSubType>"+requestSubType+"</requestSubType>").append("\n")
							.append("<extRefNumber>"+extRefNumber+"</extRefNumber>").append("\n")
							.append("<processingDate>"+processingDate+"</processingDate>").append("\n")
							.append("<transactionSource>"+transactionSource+"</transactionSource>").append("\n")
							.append("<valDate>"+valDate+"</valDate>").append("\n")
							.append("<amount>"+amount+"</amount>").append("\n")
							.append("<currencyCode>"+currencyCode+"</currencyCode>").append("\n")
							.append("<commentsHistory>"+commentsHistory+"</commentsHistory>").append("\n")
							.append("<complaintId>"+complaintId+"</complaintId>").append("\n")
							.append("<tranRefNumeber>"+tranRefNumeber+"</tranRefNumeber>").append("\n")
							.append("<listofdocumentId>").append("\n")
							.append("<documentId>"+docID+"</documentId>").append("\n")
							.append("</listofdocumentId>").append("\n")
						    .append("</serviceRequests>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqServiceRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<InqServiceRequestRes>"+
						details+
						"</InqServiceRequestRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqServiceRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquire Service Request</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqServiceRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquire Service Request</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqServiceRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquire Service Request</Output>";
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
			LogGEN.writeTrace("CBG_Log","InqServiceRequest  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqServiceRequest  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}