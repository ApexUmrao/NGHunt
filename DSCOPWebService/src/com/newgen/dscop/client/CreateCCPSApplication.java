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
import com.newgen.dscop.stub.InqCustApplicationDtlStub.CreateCCPSApplicationReqMsg;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.CreateCCPSApplicationReq_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.CreateCCPSApplicationResMsg;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.CreateCCPSApplicationRes_type0;
import com.newgen.dscop.stub.InqCustApplicationDtlStub.HeaderType;

public class CreateCCPSApplication extends DSCOPServiceHandler
{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String createCCPSApplicationCall(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Function called CreateCCPSApplication");
		LogGEN.writeTrace("CBG_Log", "CreateCCPSApplication sInputXML---");
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
			
			String customerName = xmlDataParser.getValueOf("customerName");
			String shortName = xmlDataParser.getValueOf("shortName");
			String passportNumber = xmlDataParser.getValueOf("passportNumber");
			String nationality = xmlDataParser.getValueOf("nationality");
			String customerId = xmlDataParser.getValueOf("customerId");
			String dateOfBirth = xmlDataParser.getValueOf("dateOfBirth");
			String gender = xmlDataParser.getValueOf("gender");
			String mobileNumber = xmlDataParser.getValueOf("mobileNumber");
			String xsellProduct = xmlDataParser.getValueOf("xsellProduct");
			String applicationType = xmlDataParser.getValueOf("applicationType");
			String primaryApplnNo = xmlDataParser.getValueOf("primaryApplnNo");
			String passportExpiryDate = xmlDataParser.getValueOf("passportExpiryDate");
			String subSegmentType = xmlDataParser.getValueOf("subSegmentType");
			String emiratesIdType = xmlDataParser.getValueOf("emiratesIdType");
			String emiratesId = xmlDataParser.getValueOf("emiratesId");
			String emiratedIdAppNo = xmlDataParser.getValueOf("emiratedIdAppNo");
			String companyCode = xmlDataParser.getValueOf("companyCode");
			String employerName = xmlDataParser.getValueOf("employerName");
			String income = xmlDataParser.getValueOf("income");
			String productType = xmlDataParser.getValueOf("productType");
			String applicationCategory = xmlDataParser.getValueOf("applicationCategory");
			String branchCode = xmlDataParser.getValueOf("branchCode");
			String cardType = xmlDataParser.getValueOf("cardType");
			String product = xmlDataParser.getValueOf("product");
			String sourceCode = xmlDataParser.getValueOf("sourceCode");
			String edmsEnabled = xmlDataParser.getValueOf("edmsEnabled");
			String aecbRequired = xmlDataParser.getValueOf("aecbRequired");
			String requestedCreditCardLimit = xmlDataParser.getValueOf("requestedCreditCardLimit");
			String calligraphyProduct = xmlDataParser.getValueOf("calligraphyProduct");
			String primaryCardHolderName = xmlDataParser.getValueOf("primaryCardHolderName");
			String primaryImageId = xmlDataParser.getValueOf("primaryImageId");
			String supplementaryCardHolderName = xmlDataParser.getValueOf("supplementaryCardHolderName");
			String supplementaryImageId = xmlDataParser.getValueOf("supplementaryImageId");
			String approvedLimit = xmlDataParser.getValueOf("approvedLimit");
			String dbr = xmlDataParser.getValueOf("dbr");
			String emi = xmlDataParser.getValueOf("emi");
			String customerIc = xmlDataParser.getValueOf("customerIc");
			String createdBy = xmlDataParser.getValueOf("createdBy");
			String leadReferenceNumber = xmlDataParser.getValueOf("leadReferenceNumber");
			//added new fields
			String partnerSourcingChannel= xmlDataParser.getValueOf("partnerSourcingChannel");
			String partnerReferenceNo= xmlDataParser.getValueOf("partnerReferenceNo");
			String partnerApplicationNo= xmlDataParser.getValueOf("partnerApplicationNo");
			//change ends
			InqCustApplicationDtlStub request_stub=new InqCustApplicationDtlStub(sWSDLPath);
			CreateCCPSApplicationReqMsg reqMsg = new CreateCCPSApplicationReqMsg();
			CreateCCPSApplicationReq_type0 reqMsg_type0 = new CreateCCPSApplicationReq_type0();
			CreateCCPSApplicationResMsg response_msg = new CreateCCPSApplicationResMsg();
			CreateCCPSApplicationRes_type0 response_type0  = new CreateCCPSApplicationRes_type0();
			
			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustApplicationDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);	
			
			reqMsg_type0.setCustomerName(customerName);
			reqMsg_type0.setShortName(shortName);
			reqMsg_type0.setPassportNumber(passportNumber);
			reqMsg_type0.setNationality(nationality);
			reqMsg_type0.setCustomerId(customerId);
			reqMsg_type0.setDateOfBirth(dateOfBirth);
			reqMsg_type0.setGender(gender);
			reqMsg_type0.setMobileNumber(mobileNumber);
			reqMsg_type0.setXsellProduct(xsellProduct);
			reqMsg_type0.setApplicationType(applicationType);
			reqMsg_type0.setPrimaryApplnNo(primaryApplnNo);
			reqMsg_type0.setPassportExpiryDate(passportExpiryDate);
			reqMsg_type0.setSubSegmentType(subSegmentType);
			reqMsg_type0.setEmiratesId(emiratesId);
			reqMsg_type0.setEmiratedIdAppNo(emiratedIdAppNo);
			reqMsg_type0.setCompanyCode(companyCode);
			reqMsg_type0.setEmployerName(employerName);
			reqMsg_type0.setIncome(income);
			reqMsg_type0.setProductType(productType);
			reqMsg_type0.setApplicationCategory(applicationCategory);
			reqMsg_type0.setBranchCode(branchCode);
			reqMsg_type0.setCardType(cardType);
			reqMsg_type0.setProduct(product);
			reqMsg_type0.setSourceCode(sourceCode);
			reqMsg_type0.setEdmsEnabled(edmsEnabled);
			reqMsg_type0.setAecbRequired(aecbRequired);
			reqMsg_type0.setRequestedCreditCardLimit(requestedCreditCardLimit);
			reqMsg_type0.setCalligraphyProduct(calligraphyProduct);
			reqMsg_type0.setPrimaryCardHolderName(primaryCardHolderName);
			reqMsg_type0.setPrimaryImageId(primaryImageId);
			reqMsg_type0.setSupplementaryCardHolderName(supplementaryCardHolderName);
			reqMsg_type0.setSupplementaryImageId(supplementaryImageId);
			reqMsg_type0.setApprovedLimit(approvedLimit);
			reqMsg_type0.setDbr(dbr);
			reqMsg_type0.setEmi(emi);
			reqMsg_type0.setCustomerIc(customerIc);
			reqMsg_type0.setCreatedBy(createdBy);
			reqMsg_type0.setLeadReferenceNumber(leadReferenceNumber);
			reqMsg_type0.setEmiratesIdType(emiratesIdType);
			reqMsg_type0.setPartnerSourcingChannel(partnerSourcingChannel); //added new fields
			reqMsg_type0.setPartnerReferenceNo(partnerReferenceNo);//added new fields
			reqMsg_type0.setPartnerApplicationNo(partnerApplicationNo);//added new fields
			
			reqMsg.setCreateCCPSApplicationReq(reqMsg_type0);
			reqMsg.setHeader(Header_Input);
			
			response_msg = request_stub.createCCPSApplication_Oper(reqMsg);
			xmlInput= request_stub.getInputXMLCreate(reqMsg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getCreateCCPSApplicationRes();
			LogGEN.writeTrace("CBG_Log", "CreateCCPSApplication xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.custApplicationDtlMsg;
			LogGEN.writeTrace("CBG_Log", "CreateCCPSApplicationResMsg sOrg_put: "+sOrg_put);
			
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
						"<CreateCCPSApplicationRes>"+
						"<leadReferenceNumber>"+response_type0.getLeadReferenceNumber()+"</leadReferenceNumber>"+
						"<lendPerfectApplicationNo>"+response_type0.getLendPerfectApplicationNo()+"</lendPerfectApplicationNo>"+
						"<statusDesc>"+response_type0.getStatusDesc()+"</statusDesc>"+
						"<statusCode>"+response_type0.getStatusCode()+"</statusCode>"+
						"</CreateCCPSApplicationRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CREATE_CCPS_APPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Create CCPS App</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CREATE_CCPS_APPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Create CCPS App</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CREATE_CCPS_APPS</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Create CCPS App</Output>";
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
			LogGEN.writeTrace("CBG_Log","CreateCCPSApplication  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","CreateCCPSApplication  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;		
	}
}

