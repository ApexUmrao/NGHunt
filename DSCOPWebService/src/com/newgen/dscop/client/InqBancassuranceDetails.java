package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub.BancassuranceDetails_type0;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub.HeaderType;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsReqMsg;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsReq_type0;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsResMsg;
import com.newgen.dscop.stub.InqBancassuranceDetailsStub.InqBancassuranceDetailsRes_type0;

public class InqBancassuranceDetails extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String inqBancassuranceDetails(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called InqBancassuranceDetails");
		LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqBancassuranceDetails");
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails TIME_OUT: "+lTimeOut);

			String ref_no = xmlDataParser.getValueOf("ref_no");
			String customerId = xmlDataParser.getValueOf("customerId");

			InqBancassuranceDetailsStub request_stub=new InqBancassuranceDetailsStub(sWSDLPath);
			InqBancassuranceDetailsReqMsg reqMsg = new InqBancassuranceDetailsReqMsg();
			InqBancassuranceDetailsReq_type0 reqMsg_type0 = new InqBancassuranceDetailsReq_type0();
			InqBancassuranceDetailsResMsg response_msg = new InqBancassuranceDetailsResMsg();
			InqBancassuranceDetailsRes_type0 response_type0  = new InqBancassuranceDetailsRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqBancassuranceDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			reqMsg_type0.setCustomerId(customerId);

			reqMsg.setInqBancassuranceDetailsReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);

			response_msg = request_stub.inqBancassuranceDetails_Oper(reqMsg);
			xmlInput= request_stub.getInputXML(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getInqBancassuranceDetailsRes();
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetailsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			String custId = response_type0.getCustomerId();

			BancassuranceDetails_type0 bancDetails[] =  response_type0.getBancassuranceDetails();
			StringBuilder details = new StringBuilder(); 
			if(bancDetails!=null){
				for(BancassuranceDetails_type0 detailsType0 : bancDetails) {
					String accountNumber = detailsType0.getAccountNumber();
					String productName = detailsType0.getProductName();
					String policyNumber = detailsType0.getPolicyNumber();
					String currency = detailsType0.getCurrency();
					String premium = detailsType0.getPremium();
					String paidPremium = detailsType0.getPaidPremium();
					String frequency = detailsType0.getFrequency();
					String policyAccountValue = detailsType0.getPolicyAccountValue();
					String policySurrenderValue = detailsType0.getPolicySurrenderValue();
					String providerName = detailsType0.getProviderName();
					String policyStatus = detailsType0.getPolicyStatus();
					String policyStartDate = detailsType0.getPolicyStartDate();
					String policyTerm = detailsType0.getPolicyTerm();
					String servicingRM = detailsType0.getServicingRM();
					String servicingRMTeam = detailsType0.getServicingRMTeam();
					String productCategory = detailsType0.getProductCategory();
					String promotionOffer = detailsType0.getPromotionOffer();
					String countPremiumsPaid = detailsType0.getCountPremiumsPaid();
					String totalPremiumsPaid = detailsType0.getTotalPremiumsPaid();
					String countPremiumsUnpaid = detailsType0.getCountPremiumsUnpaid();
					String totalPremiumsUnpaid = detailsType0.getTotalPremiumsUnpaid();
					String regularPremium = detailsType0.getRegularPremium();
					String lumpsumPremium = detailsType0.getLumpsumPremium();
					String maturityDate = detailsType0.getMaturityDate();
					String nextAnniversaryDate = detailsType0.getNextAnniversaryDate();
					String lumpsumCommission = detailsType0.getLumpsumCommission();
					String regularCommissionAnnual = detailsType0.getRegularCommissionAnnual();
					String iPDate = detailsType0.getIPDate();
					String sIStartDate = detailsType0.getSIStartDate();
					String beneficary1 = detailsType0.getBeneficary1();
					String beneficary2 = detailsType0.getBeneficary2();
					String payerCID = detailsType0.getPayerCID();

					details.append("<bancassuranceDetails>").append("\n")
					.append("<accountNumber>"+accountNumber+"</accountNumber>").append("\n")
					.append("<productName>"+productName+"</productName>").append("\n")
					.append("<policyNumber>"+policyNumber+"</policyNumber>").append("\n")
					.append("<currency>"+currency+"</currency>").append("\n")
					.append("<premium>"+premium+"</premium>").append("\n")
					.append("<paidPremium>"+paidPremium+"</paidPremium>").append("\n")
					.append("<frequency>"+frequency+"</frequency>").append("\n")
					.append("<policyAccountValue>"+policyAccountValue+"</policyAccountValue>").append("\n")
					.append("<policySurrenderValue>"+policySurrenderValue+"</policySurrenderValue>").append("\n")
					.append("<providerName>"+providerName+"</providerName>").append("\n")
					.append("<policyStatus>"+policyStatus+"</policyStatus>").append("\n")
					.append("<policyStartDate>"+policyStartDate+"</policyStartDate>").append("\n")
					.append("<policyTerm>"+policyTerm+"</policyTerm>").append("\n")
					.append("<servicingRM>"+servicingRM+"</servicingRM>").append("\n")
					.append("<servicingRMTeam>"+servicingRMTeam+"</servicingRMTeam>").append("\n")
					.append("<productCategory>"+productCategory+"</productCategory>").append("\n")
					.append("<promotionOffer>"+promotionOffer+"</promotionOffer>").append("\n")
					.append("<countPremiumsPaid>"+countPremiumsPaid+"</countPremiumsPaid>").append("\n")
					.append("<totalPremiumsPaid>"+totalPremiumsPaid+"</totalPremiumsPaid>").append("\n")
					.append("<countPremiumsUnpaid>"+countPremiumsUnpaid+"</countPremiumsUnpaid>").append("\n")
					.append("<totalPremiumsUnpaid>"+totalPremiumsUnpaid+"</totalPremiumsUnpaid>").append("\n")
					.append("<regularPremium>"+regularPremium+"</regularPremium>").append("\n")
					.append("<lumpsumPremium>"+lumpsumPremium+"</lumpsumPremium>").append("\n")
					.append("<maturityDate>"+maturityDate+"</maturityDate>").append("\n")
					.append("<nextAnniversaryDate>"+nextAnniversaryDate+"</nextAnniversaryDate>").append("\n")
					.append("<lumpsumCommission>"+lumpsumCommission+"</lumpsumCommission>").append("\n")
					.append("<regularCommissionAnnual>"+regularCommissionAnnual+"</regularCommissionAnnual>").append("\n")
					.append("<iPDate>"+iPDate+"</iPDate>").append("\n")
					.append("<sIStartDate>"+sIStartDate+"</sIStartDate>").append("\n")
					.append("<beneficary1>"+beneficary1+"</beneficary1>").append("\n")
					.append("<beneficary2>"+beneficary2+"</beneficary2>").append("\n")
					.append("<payerCID>"+payerCID+"</payerCID>").append("\n")
					.append("</bancassuranceDetails>").append("\n");
				}
			}
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqBancassuranceDetails</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<InqBancassuranceDetailsRes>"+
						"<customerId>"+custId+"</customerId>"+	
						details+
						"</InqBancassuranceDetailsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_BANC_DTLS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Inquiry BancassuanceDetails</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_BANC_DTLS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry BancassuanceDetails</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>INQ_BANC_DTLS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Inquiry BancassuanceDetails</Output>";
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
			LogGEN.writeTrace("CBG_Log","InqBancassuranceDetails  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","InqBancassuranceDetails  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}