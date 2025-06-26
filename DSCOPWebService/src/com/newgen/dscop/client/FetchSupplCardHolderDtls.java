package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.CardHolderDtls_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchSupplCardHoldeDtlsrResMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchSupplCardHoldeDtlsrRes_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchSupplCardHolderDtlsReqMsg;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.FetchSupplCardHolderDtlsReq_type0;
import com.newgen.dscop.stub.InqCustomerDigitalLendingStub.HeaderType;

public class FetchSupplCardHolderDtls extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String fetchSupplCardHolderDtlsDtls(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called FetchSupplCardHolderDtls");
		LogGEN.writeTrace("CBG_Log", "FetchSupplCardHolderDtls sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqCustomerDigitalLending");
			LogGEN.writeTrace("CBG_Log", "FetchSupplCardHolderDtls WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerDigitalLending TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerID = xmlDataParser.getValueOf("customerId");

			InqCustomerDigitalLendingStub request_stub=new InqCustomerDigitalLendingStub(sWSDLPath);
			FetchSupplCardHolderDtlsReqMsg fetch_camp_dtls = new FetchSupplCardHolderDtlsReqMsg();			
			FetchSupplCardHolderDtlsReq_type0 fetch_camp_type0 = new FetchSupplCardHolderDtlsReq_type0();
			FetchSupplCardHoldeDtlsrResMsg fetch_camp_response = new FetchSupplCardHoldeDtlsrResMsg();
			FetchSupplCardHoldeDtlsrRes_type0 fetch_camp_res_type0 = new FetchSupplCardHoldeDtlsrRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("InqCustomerDigitalLending");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			fetch_camp_type0.setCustomerId(customerID);
			fetch_camp_dtls.setFetchSupplCardHolderDtlsReq(fetch_camp_type0);
			fetch_camp_dtls.setHeader(Header_Input);
			fetch_camp_response = request_stub.fetchSupplCardHolderDtls_Oper(fetch_camp_dtls);

			xmlInput= request_stub.getInputXMLSupplCardHolder(fetch_camp_dtls);
			Header_Input = fetch_camp_response.getHeader();
			fetch_camp_res_type0 = fetch_camp_response.getFetchSupplCardHoldeDtlsrRes();
			LogGEN.writeTrace("CBG_Log", "FetchSupplCardHolderDtls xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchSupplCardHolderDtlsResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			String statusCode="";
			String statusDescription="";
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				StringBuilder details = new StringBuilder(); 
				if(fetch_camp_res_type0 != null){
					statusCode = fetch_camp_res_type0.getStatusCode();
					statusDescription = fetch_camp_res_type0.getStatusDescription();
					CardHolderDtls_type0 cardHolderDtls[] = fetch_camp_res_type0.getCardHolderDtls();
					if(cardHolderDtls != null){
						for(CardHolderDtls_type0 detailsType0 : cardHolderDtls) {
							String fullName = detailsType0.getFullName();
							String embossingName = detailsType0.getEmbossingName();
							String supplimentaryId = detailsType0.getSupplimentaryId();
							String nationality = detailsType0.getNationality();
							String dateOfBirth = detailsType0.getDateOfBirth();
							String title = detailsType0.getTitle();
							String passportNumber = detailsType0.getPassportNumber();
							String relationShip = detailsType0.getRelationShip();
							String mothersMaidenName = detailsType0.getMothersMaidenName();
							String maritalStatus = detailsType0.getMaritalStatus();

							details.append("<cardHolderDtls>").append("\n")
							.append("<paymentAmount>"+fullName+"</fullName>").append("\n")
							.append("<embossingName>"+embossingName+"</embossingName>").append("\n")
							.append("<supplimentaryId>"+supplimentaryId+"</supplimentaryId>").append("\n")
							.append("<nationality>"+nationality+"</nationality>").append("\n")
							.append("<dateOfBirth>"+dateOfBirth+"</dateOfBirth>").append("\n")
							.append("<title>"+title+"</title>").append("\n")
							.append("<passportNumber>"+passportNumber+"</passportNumber>").append("\n")
							.append("<relationShip>"+relationShip+"</relationShip>").append("\n")
							.append("<mothersMaidenName>"+mothersMaidenName+"</mothersMaidenName>").append("\n")
							.append("<maritalStatus>"+maritalStatus+"</maritalStatus>").append("\n")
							.append("</cardHolderDtls>").append("\n");
						}
					}
				}
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchSupplCardHolderDtls</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDesc+"</errorDescription>"+
						"<errorDetail>"+sErrorDetail+"</errorDetail>"+
						"<FetchSupplCardHolderDtlsRes>"+
						"<statusCode>"+statusCode+"</statusCode>"+
						"<statusDescription>"+statusDescription+"</statusDescription>"+
						details+
						"</FetchSupplCardHolderDtlsRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchSupplCardHolderDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to fetch card holder details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchSupplCardHolderDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch card holder details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchSupplCardHolderDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to fetch card holder details</Output>";
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
			LogGEN.writeTrace("CBG_Log","FetchSupplCardHolderDtls  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","FetchSupplCardHolderDtls  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}