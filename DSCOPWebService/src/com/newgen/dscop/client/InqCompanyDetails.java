package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCompanyDetailsStub;
import com.newgen.dscop.stub.InqCompanyDetailsStub.CompanyAuthSignDetails_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.CompanyOtherDetails_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.FetchCompanyMasterDetailsReqMsg;
import com.newgen.dscop.stub.InqCompanyDetailsStub.FetchCompanyMasterDetailsReq_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.FetchCompanyMasterDetailsResMsg;
import com.newgen.dscop.stub.InqCompanyDetailsStub.FetchCompanyMasterDetailsRes_type0;
import com.newgen.dscop.stub.InqCompanyDetailsStub.HeaderType;
import com.newgen.dscop.stub.InqCompanyDetailsStub.LendingDetails_type0;

public class InqCompanyDetails extends DSCOPServiceHandler{
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String fetchCompanyMasterDetails_output = "";

	public String companyMasterDetails(String sInputXML)
	{
		LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Fuction called inq_company_details");
		LogGEN.writeTrace("CBG_Log", "InqCompanyDetails sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput = "";
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("inq_company_details");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("inq_company_details");
			LogGEN.writeTrace("CBG_Log", "inq_company_details WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "inq_company_details WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "inq_company_details CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "inq_company_details USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "inq_company_details PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "inq_company_details LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "inq_company_details TIME_OUT: "+lTimeOut);			
			LogGEN.writeTrace("CBG_Log", "inq_company_details sDate---"+sDate);
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");

			InqCompanyDetailsStub inq_companyDtl_stub = new InqCompanyDetailsStub(sWSDLPath);
			FetchCompanyMasterDetailsReqMsg companyDtl_Req_Msg = new FetchCompanyMasterDetailsReqMsg();	
			FetchCompanyMasterDetailsResMsg res_msg=new FetchCompanyMasterDetailsResMsg();
			FetchCompanyMasterDetailsReq_type0 companyDtl_req_type0 = new FetchCompanyMasterDetailsReq_type0();		

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCompanyDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			companyDtl_Req_Msg.setHeader(Header_Input);

			companyDtl_req_type0.setCompanyCode(xmlDataParser.getValueOf("companyCode"));
			companyDtl_req_type0.setCompanyName(xmlDataParser.getValueOf("companyName"));
			companyDtl_req_type0.setCustomerNationality(xmlDataParser.getValueOf("customerNationality"));
			companyDtl_req_type0.setProduct(xmlDataParser.getValueOf("product"));
			companyDtl_Req_Msg.setFetchCompanyMasterDetailsReq(companyDtl_req_type0);
			xmlInput = inq_companyDtl_stub.getInputXml(companyDtl_Req_Msg);
			LogGEN.writeTrace("CBG_Log", "CompanyMasterDetails InputXML: " + xmlInput);
			inq_companyDtl_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg=inq_companyDtl_stub.fetchCompanyMasterDetails_Oper(companyDtl_Req_Msg);
			fetchCompanyMasterDetails_output = inq_companyDtl_stub.res_fetchCompanyMasterDetails;
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Return Code: "+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Error Detail: "+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Error Description: "+sErrorDesc);
			LogGEN.writeTrace("CBG_Log", "InqCompanyDetails res_msg: "+fetchCompanyMasterDetails_output);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Successful Result");
				FetchCompanyMasterDetailsRes_type0 res=new FetchCompanyMasterDetailsRes_type0();
				res=res_msg.getFetchCompanyMasterDetailsRes();

				CompanyOtherDetails_type0[] otherDetails = res.getCompanyOtherDetails();

				String MultiplesCap = "";
				String AmountCap = "";
				String NationalityRestriction = "";
				String DesignationRestriction = "";
				String RestrictionApply = "";
				if(otherDetails != null){
					for (CompanyOtherDetails_type0 companyOtherDetails_type0 : otherDetails) {
						if("3".equals(companyOtherDetails_type0.getCompanyDetailCode())){
							MultiplesCap = companyOtherDetails_type0.getCompanyDetails();
						}
						else if("4".equals(companyOtherDetails_type0.getCompanyDetailCode())){
							AmountCap = companyOtherDetails_type0.getCompanyDetails();
						}
						else if("16".equals(companyOtherDetails_type0.getCompanyDetailCode())){
							NationalityRestriction = companyOtherDetails_type0.getCompanyDetails();
						}
						else if("17".equals(companyOtherDetails_type0.getCompanyDetailCode())){
							DesignationRestriction = companyOtherDetails_type0.getCompanyDetails();
						}
						else if("18".equals(companyOtherDetails_type0.getCompanyDetailCode())){
							RestrictionApply = companyOtherDetails_type0.getCompanyDetails();
						}
					}
				}

				StringBuffer details  = new StringBuffer();

				CompanyAuthSignDetails_type0 authDetails[] = res.getCompanyAuthSignDetails();
				if(authDetails != null){
					for (CompanyAuthSignDetails_type0 companyAuthSignDetails_type0 : authDetails)
					{
						String companyAuthSignNbr = companyAuthSignDetails_type0.getCompanyAuthSignNbr();
						String companyAuthSignName = companyAuthSignDetails_type0.getCompanyAuthSignName();
						String companyAuthSignDesg = companyAuthSignDetails_type0.getCompanyAuthSignDesg();
						String companyAuthSignEmail = companyAuthSignDetails_type0.getCompanyAuthSignEmail();
						String companyAuthSignStatus = companyAuthSignDetails_type0.getCompanyAuthSignStatus();
						String companyAuthSignAuthty = companyAuthSignDetails_type0.getCompanyAuthSignAuthty();
						String companyAuthSignCont = companyAuthSignDetails_type0.getCompanyAuthSignCont();

						details.append("<companyAuthSignDetails>").append("\n")
						.append("<companyAuthSignNbr>"+companyAuthSignNbr+"</companyAuthSignNbr>").append("\n")
						.append("<companyAuthSignName>"+companyAuthSignName+"</companyAuthSignName>").append("\n")
						.append("<companyAuthSignDesg>"+companyAuthSignDesg+"</companyAuthSignDesg>").append("\n")
						.append("<companyAuthSignStatus>"+companyAuthSignStatus+"</companyAuthSignStatus>").append("\n")
						.append("<companyAuthSignEmail>"+companyAuthSignEmail+"</companyAuthSignEmail>").append("\n")
						.append("<companyAuthSignAuthty>"+companyAuthSignAuthty+"</companyAuthSignAuthty>").append("\n")
						.append("<companyAuthSignCont>"+companyAuthSignCont+"</companyAuthSignCont>").append("\n")
						.append("</companyAuthSignDetails>").append("\n");

					}
				}

				LendingDetails_type0 lendingDetails[] = res.getLendingDetails();
				if(lendingDetails != null){
					for (LendingDetails_type0 lendingDetails_type0 : lendingDetails)
					{
						String LendingStatus = lendingDetails_type0.getLendingStatus();
						String CompanyStatus = lendingDetails_type0.getCompanyStatus();
						String LendingMultiplesCap = lendingDetails_type0.getMultiplesCap();
						String LendingAmountCap = lendingDetails_type0.getAmountCap();
						String ApplicationScore = lendingDetails_type0.getApplicationScore();
						String AECBRiskBand = lendingDetails_type0.getAECBRiskBand();
						String BScore = lendingDetails_type0.getBScore();
						String HRCPV = lendingDetails_type0.getHRCPV();
						String STLFormat = lendingDetails_type0.getSTLFormat();
						String Pricing = lendingDetails_type0.getPricing();
						String LOS = lendingDetails_type0.getLOS();
						String MiniSalaryCap = lendingDetails_type0.getMiniSalaryCap();
						String Reason = lendingDetails_type0.getReason();
						String ProductType = lendingDetails_type0.getProductType();
						String BayutRestrictions=lendingDetails_type0.getBayutRestrictions();
						String AddBackAccomodation= lendingDetails_type0.getAddBackAccomodation();
						String LendingNationalityRestriction= lendingDetails_type0.getNationalityRestriction();
						String DesinationRestriction= lendingDetails_type0.getDesinationRestriction();
						String LendingRestrictionApply= lendingDetails_type0.getRestrictionApply();

						details.append("<companyLendingDetails>").append("\n")
						.append("<LendingStatus>"+LendingStatus+"</LendingStatus>").append("\n")
						.append("<CompanyStatus>"+CompanyStatus+"</CompanyStatus>").append("\n")
						.append("<MultiplesCap>"+LendingMultiplesCap+"</MultiplesCap>").append("\n")
						.append("<AmountCap>"+LendingAmountCap+"</AmountCap>").append("\n")
						.append("<ApplicationScore>"+ApplicationScore+"</ApplicationScore>").append("\n")
						.append("<AECBRiskBand>"+AECBRiskBand+"</AECBRiskBand>").append("\n")
						.append("<BScore>"+BScore+"</BScore>").append("\n")
						.append("<HRCPV>"+HRCPV+"</HRCPV>").append("\n")
						.append("<STLFormat>"+STLFormat+"</STLFormat>").append("\n")
						.append("<Pricing>"+Pricing+"</Pricing>").append("\n")
						.append("<LOS>"+LOS+"</LOS>").append("\n")
						.append("<MiniSalaryCap>"+MiniSalaryCap+"</MiniSalaryCap>").append("\n")
						.append("<Reason>"+Reason+"</Reason>").append("\n")
						.append("<ProductType>"+ProductType+"</ProductType>").append("\n")
						.append("<BayutRestrictions>"+BayutRestrictions+"</BayutRestrictions>").append("\n")
						.append("<AddBackAccomodation>"+AddBackAccomodation+"</AddBackAccomodation>").append("\n")
						.append("<NationalityRestriction>"+LendingNationalityRestriction+"</NationalityRestriction>").append("\n")
						.append("<DesinationRestriction>"+DesinationRestriction+"</DesinationRestriction>").append("\n")
						.append("<RestrictionApply>"+LendingRestrictionApply+"</RestrictionApply>").append("\n")
						.append("</companyLendingDetails>").append("\n");
					}
				}

				CompanyOtherDetails_type0 othersDetails[] = res.getCompanyOtherDetails();
				if(othersDetails != null){
					for (CompanyOtherDetails_type0 CompanyOtherDetails_type0 : othersDetails)
					{
						String companyDetailCode = CompanyOtherDetails_type0.getCompanyDetailCode();
						String companyDetails = CompanyOtherDetails_type0.getCompanyDetails();
						String companyNationalityFlag = CompanyOtherDetails_type0.getCompanyNationalityFlag();

						details.append("<companyOtherDetails>").append("\n")
						.append("<companyDetailCode>"+companyDetailCode+"</companyDetailCode>").append("\n")
						.append("<companyDetails>"+companyDetails+"</companyDetails>").append("\n")
						.append("<companyNationalityFlag>"+companyNationalityFlag+"</companyNationalityFlag>").append("\n")
						.append("</companyOtherDetails>").append("\n");

					}
				}

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>inq_company_details</Option>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
						"<returnCode>"+ sReturnCode +"</returnCode><errorDetail>"+ sErrorDetail +"</errorDetail>"
						+ "<fetchCompanyMasterDetailsRes>"
						+ "<companyCode>"+res.getCompanyCode()+"</companyCode>"
						+ "<companyName>"+res.getCompanyName()+"</companyName>"
						+ "<companyType>"+res.getCompanyType()+"</companyType>"
						+ "<companyAddress1>"+res.getCompanyAddress1()+"</companyAddress1>"
						+ "<companyAddress2>"+res.getCompanyAddress2()+"</companyAddress2>"
						+ "<companyState>"+res.getCompanyState()+"</companyState>"
						+ "<companyCity>"+res.getCompanyCity()+"</companyCity>"
						+ "<companyPhone>"+res.getCompanyPhone()+"</companyPhone>"
						+ "<companyFax>"+res.getCompanyFax()+"</companyFax>"
						+ "<companyPO>"+res.getCompanyPO()+"</companyPO>"
						+ "<companyContPerson>"+res.getCompanyContPerson()+"</companyContPerson>"
						+ "<companyAuthSign>"+res.getCompanyAuthSign()+"</companyAuthSign>"
						+ "<companyCat>"+res.getCompanyCat()+"</companyCat>"
						+ "<companyProposedBy>"+res.getCompanyProposedBy()+"</companyProposedBy>"
						+ "<companyApprovedBy>"+res.getCompanyApprovedBy()+"</companyApprovedBy>"
						+ "<companyTMLFlag>"+res.getCompanyTMLFlag()+"</companyTMLFlag>"
						+ "<companyIndustry>"+res.getCompanyIndustry()+"</companyIndustry>"
/*Missed values */  	+ "<companySubIndustry>"+res.getCompanySubIndustry()+"</companySubIndustry>"
						+ "<companySubsidiary>"+res.getCompanySubsidiary()+"</companySubsidiary>"
						+ "<companyStatus>"+res.getCompanyStatus()+"</companyStatus>"
						+ "<MultiplesCap>"+MultiplesCap+"</MultiplesCap>"
						+ "<AmountCap>"+AmountCap+"</AmountCap>"
						+ "<NationalityRestriction>"+NationalityRestriction+"</NationalityRestriction>"
						+ "<DesignationRestriction>"+DesignationRestriction+"</DesignationRestriction>"
						+ "<RestrictionApply>"+RestrictionApply+"</RestrictionApply>"
						+ "<companyNationalityFlag>"+(res.getCompanyOtherDetails()!=null && res.getCompanyOtherDetails().length > 0 ? res.getCompanyOtherDetails()[0].getCompanyNationalityFlag():"")+"</companyNationalityFlag>"
						+ "<companyPartners>"+res.getCompanyPartners()+"</companyPartners>"
						+ "<companyWBGCat>"+res.getCompanyWBGCat()+"</companyWBGCat>"
						+ "<companyWBGFacilities>"+res.getCompanyWBGFacilities()+"</companyWBGFacilities>"
						+ "<CdFieldVisitDate>"+res.getCdFieldVisitDate()+"</CdFieldVisitDate>"
						+ "<CdFieldVisitStatus>"+res.getCdFieldVisitStatus()+"</CdFieldVisitStatus>"
						+ "<CurrentStatus>"+res.getCurrentStatus()+"</CurrentStatus>"
/*Missed values */  	+ "<comments>"+res.getComments()+"</comments>"
/*Missed values */  	+ "<cdComments>"+res.getCdComments()+"</cdComments>"
/*Missed values */  	+ "<cdPolicyComments>"+res.getCdPolicyComments()+"</cdPolicyComments>"
/*Missed values */  	+ "<cdFraudComments>"+res.getCdFraudComments()+"</cdFraudComments>"
/*Missed values */  	+ "<cdHrCpvComments>"+res.getCdHrCpvComments()+"</cdHrCpvComments>"
/*Missed values */  	+ "<companyEIKONLink>"+res.getCompanyEIKONLink()+"</companyEIKONLink>"
/*Missed values */  	+ "<companyTOTALEmp>"+res.getCompanyTOTALEmp()+"</companyTOTALEmp>"
/*Missed values */  	+ "<companyBankableEmp>"+res.getCompanyBankableEmp()+"</companyBankableEmp>"
/*Missed values */  	+ "<createdDate>"+res.getCreatedDate()+"</createdDate>"
/*Missed values */  	+ "<companyAddress3>"+res.getCompanyAddress3()+"</companyAddress3>"
						+ "<cdCommentIndicator>"+res.getCdCommentIndicator()+"</cdCommentIndicator>"
						+ "<wpsCode>"+res.getWpsCode()+"</wpsCode>"
						+ "<uaeFtsCode>"+res.getUaeFtsCode()+"</uaeFtsCode>"
						+details
						+ "</fetchCompanyMasterDetailsRes>"
						+ "</Output>";					

				LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Output XML: "+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>inq_company_details</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to fetch company details</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "InqCompanyDetails Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>inq_company_details</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to fetch company details</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","InqCompanyDetails outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>inq_company_details</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>unable to fetch company details</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log","InqCompanyDetails:"+ inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
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
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," InqCompanyDetails Query : finally :"+Query);

			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),fetchCompanyMasterDetails_output.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","InqCompanyDetails outputXML : finally :"+sOutput);
		}
		return sOutput;			
	}
}
