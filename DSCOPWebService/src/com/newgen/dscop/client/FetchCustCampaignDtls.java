package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCampaignDtlsStub;
import com.newgen.dscop.stub.ModCampaignDtlsStub.CRMCampaigns_type0;
import com.newgen.dscop.stub.ModCampaignDtlsStub.CampaignListCRM_type0;
import com.newgen.dscop.stub.ModCampaignDtlsStub.FetchCustCampaignDtlsReqMsg;
import com.newgen.dscop.stub.ModCampaignDtlsStub.FetchCustCampaignDtlsReq_type0;
import com.newgen.dscop.stub.ModCampaignDtlsStub.FetchCustCampaignDtlsResMsg;
import com.newgen.dscop.stub.ModCampaignDtlsStub.FetchCustCampaignDtlsRes_type0;
import com.newgen.dscop.stub.ModCampaignDtlsStub.HeaderType;

public class FetchCustCampaignDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchCustCampaignDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchCustCampaignDtls");
		LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModCampaignDtls");
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");
			String channel = xmlDataParser.getValueOf("channel");

			ModCampaignDtlsStub request_stub=new ModCampaignDtlsStub(sWSDLPath);
			FetchCustCampaignDtlsReqMsg fetch_camp_dtls = new FetchCustCampaignDtlsReqMsg();			
			FetchCustCampaignDtlsReq_type0 fetch_camp_type0 = new FetchCustCampaignDtlsReq_type0();
			FetchCustCampaignDtlsResMsg fetch_camp_response = new FetchCustCampaignDtlsResMsg();
			FetchCustCampaignDtlsRes_type0 fetch_camp_res_type0 = new FetchCustCampaignDtlsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCampaignDtls");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer("");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setRepTimeStamp("");
			Header_Input.setUsername("");
			Header_Input.setCredentials("");

			fetch_camp_type0.setCustomerID(customerID);
			fetch_camp_type0.setChannel(channel);
			fetch_camp_dtls.setFetchCustCampaignDtlsReq(fetch_camp_type0);
			fetch_camp_dtls.setHeader(Header_Input);
			fetch_camp_response = request_stub.fetchCustCampaignDtls_Oper(fetch_camp_dtls);

			xmlInput= request_stub.getInputXML(fetch_camp_dtls);
			Header_Input = fetch_camp_response.getHeader();
			fetch_camp_res_type0 = fetch_camp_response.getFetchCustCampaignDtlsRes();
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(fetch_camp_res_type0 != null){
					CampaignListCRM_type0 campaignListCRM = fetch_camp_res_type0.getCampaignListCRM();
					CRMCampaigns_type0[] CRMCampaigns = campaignListCRM.getCRMCampaigns();
					if(CRMCampaigns != null){
						for(CRMCampaigns_type0 detailsType0 : CRMCampaigns) {
							String crmCampaignID = detailsType0.getCrmCampaignID();
							String offerCode = detailsType0.getOfferCode();
							String offerName = detailsType0.getOfferName();
							String offerType = detailsType0.getOfferType();
							String offerDesc = detailsType0.getOfferDesc();
							String offerEffectiveDt = detailsType0.getOfferEffectiveDt();
							String offerExpiryDt = detailsType0.getOfferExpiryDt();
							String uTreatmentCode = detailsType0.getUTreatmentCode();
							String uTreatmentName = detailsType0.getUTreatmentName();
							String uTreatmentChannel = detailsType0.getUTreatmentChannel();
							String sMSMessageText = detailsType0.getSMSMessageText();
							String smsDeliveryStatus = detailsType0.getSmsDeliveryStatus();
							String smsSendTime = detailsType0.getSmsSendTime();

							details.append("<CRMCampaigns>").append("\n")
							.append("<crmCampaignID>"+crmCampaignID+"</crmCampaignID>").append("\n")
							.append("<offerCode>"+offerCode+"</offerCode>").append("\n")
							.append("<offerName>"+offerName+"</offerName>").append("\n")
							.append("<offerType>"+offerType+"</offerType>").append("\n")
							.append("<offerDesc>"+offerDesc+"</offerDesc>").append("\n")
							.append("<offerEffectiveDt>"+offerEffectiveDt+"</offerEffectiveDt>").append("\n")
							.append("<offerExpiryDt>"+offerExpiryDt+"</offerExpiryDt>").append("\n")
							.append("<uTreatmentCode>"+uTreatmentCode+"</uTreatmentCode>").append("\n")
							.append("<uTreatmentName>"+uTreatmentName+"</uTreatmentName>").append("\n")
							.append("<uTreatmentChannel>"+uTreatmentChannel+"</uTreatmentChannel>").append("\n")
							.append("<sMSMessageText>"+sMSMessageText+"</sMSMessageText>").append("\n")
							.append("<smsDeliveryStatus>"+smsDeliveryStatus+"</smsDeliveryStatus>").append("\n")
							.append("<smsSendTime>"+smsSendTime+"</smsSendTime>").append("\n")
							.append("</CRMCampaigns>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchCustCampaignDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<FetchCustCampaignDtlsRes>"+
						details+
						"</FetchCustCampaignDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustCampaignDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to fetch customer Campaign Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustCampaignDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customer Campaign Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","FetchCustCampaignDtls outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustCampaignDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch customer Campaign Details</Output>";
			}

			LogGEN.writeTrace("CBG_Log","FetchCustCampaignDtls outputXML : finally :"+sOutput);

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
			LogGEN.writeTrace("CBG_Log","FetchCustCampaignDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchCustCampaignDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

