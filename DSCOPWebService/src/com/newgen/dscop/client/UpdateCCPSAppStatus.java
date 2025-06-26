package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustApplicationDtlStub;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.UpdateCCPSAppStatusReqMsg;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.UpdateCCPSAppStatusReq_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.UpdateCCPSAppStatusResMsg;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.UpdateCCPSAppStatusRes_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.HeaderType;

public class UpdateCCPSAppStatus extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String updateCCPSAppStatusCall(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Function called UpdateCCPSAppStatus");
		LogGEN.writeTrace("CBG_Log", "UpdateCCPSAppStatus sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustApplicationDtl");
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustApplicationDtl TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String leadReferenceNumber = xmlDataParser.getValueOf("leadReferenceNumber");
			String lendPerfectApplicationNo = xmlDataParser.getValueOf("lendPerfectApplicationNo");
			String serviceType = xmlDataParser.getValueOf("serviceType");
			String requestedBy = xmlDataParser.getValueOf("requestedBy");
			String rejectCodes = xmlDataParser.getValueOf("rejectCodes");
			String partnerReferenceNo = xmlDataParser.getValueOf("partnerReferenceNo");//added new fields
			String partnerCustomizationId = xmlDataParser.getValueOf("partnerCustomizationId");//added new fields
			String productCode = xmlDataParser.getValueOf("productCode");//added new fields
			
			InqCustApplicationDtlStub request_stub=new InqCustApplicationDtlStub(sWSDLPath);
			UpdateCCPSAppStatusReqMsg reqMsg = new UpdateCCPSAppStatusReqMsg();
			UpdateCCPSAppStatusReq_type0 reqMsg_type0 = new UpdateCCPSAppStatusReq_type0();
			UpdateCCPSAppStatusResMsg response_msg = new UpdateCCPSAppStatusResMsg();
			UpdateCCPSAppStatusRes_type0 response_type0  = new UpdateCCPSAppStatusRes_type0();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustApplicationDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);	
			
			reqMsg_type0.setLeadReferenceNumber(leadReferenceNumber);
			reqMsg_type0.setLendPerfectApplicationNo(lendPerfectApplicationNo);
			reqMsg_type0.setServiceType(serviceType);
			reqMsg_type0.setRequestedBy(requestedBy);
			reqMsg_type0.setRejectCodes(rejectCodes);
			reqMsg_type0.setPartnerCustomizationId(partnerCustomizationId);//added new fields
			reqMsg_type0.setPartnerReferenceNo(partnerReferenceNo);//added new fields
			reqMsg_type0.setProductCode(productCode);//added new fields
			
			reqMsg.setUpdateCCPSAppStatusReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);
			
			response_msg = request_stub.updateCCPSAppStatus_Oper(reqMsg);
			xmlInput= request_stub.getInputXMLUpdate(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getUpdateCCPSAppStatusRes();
			LogGEN.writeTrace("CBG_Log", "UpdateCCPSAppStatus xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.custApplicationDtlMsg;
			LogGEN.writeTrace("CBG_Log", "UpdateCCPSAppStatusResMsg sOrg_put: "+sOrg_put);
			
			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqCustApplicationDtl</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<UpdateCCPSAppStatusRes>"+
						"<leadReferenceNumber>"+response_type0.getLeadReferenceNumber()+"</leadReferenceNumber>"+
						"<lendPerfectApplicationNo>"+response_type0.getLendPerfectApplicationNo()+"</lendPerfectApplicationNo>"+
						"<cardNumber>"+response_type0.getCardNumber()+"</cardNumber>"+
						"<expiryDate>"+response_type0.getExpiryDate()+"</expiryDate>"+
						"<statusDesc>"+response_type0.getStatusDesc()+"</statusDesc>"+
						"<statusCode>"+response_type0.getStatusCode()+"</statusCode>"+
						"</UpdateCCPSAppStatusRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>UPDATE_CCPS_APPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Update CCPS App</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>UPDATE_CCPS_APPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Update CCPS App</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>UPDATE_CCPS_APPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Update CCPS App</Output>";
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
			LogGEN.writeTrace("CBG_Log","UpdateCCPSAppStatus  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","UpdateCCPSAppStatus  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}

