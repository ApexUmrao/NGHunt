package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jsp.newsearch;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReqMsg;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersReq_type0;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersResMsg;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchCustOffersRes_type0;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.HeaderType;

public class FetchCustOffersRequest  extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword=""; 
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	String xmlInput="";
	
	public String FetchCustOffersRequestToFCR(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called FetchCustOffersRequest");
		LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String customerId=xmlDataParser.getValueOf("customerId");		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("FetchCustOffersRequest");
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			InqLoanTopUpRequestStub inqLoanstub=new InqLoanTopUpRequestStub(sWSDLPath);
			FetchCustOffersReq_type0 fetchCustReq=new FetchCustOffersReq_type0();
			FetchCustOffersReqMsg fetchCustReqMsg=new FetchCustOffersReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqLoanTopUpRequest");
			Header_Input.setVersionNo("1.0");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setUsername("1234");
			Header_Input.setCredentials(loggedinuser);
			LogGEN.writeTrace("CBG_Log", "All values set InqLoanTopUpRequestToFCR");
			fetchCustReqMsg.setHeader(Header_Input);
			fetchCustReq.setCustomerId(customerId);
			fetchCustReqMsg.setFetchCustOffersReq(fetchCustReq);
			inqLoanstub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=inqLoanstub.getInputXml(fetchCustReqMsg);
			FetchCustOffersResMsg add_CSAcct_res_msg= inqLoanstub.fetchCustOffers_Oper(fetchCustReqMsg);
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest add_CSAcct_res_msg: "+add_CSAcct_res_msg);
			sOrg_Output=inqLoanstub.outputXML;
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest sOrg_Output: "+sOrg_Output);
			Header_Input=add_CSAcct_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "FetchCustOffersRequest sErrorDetail: "+sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");

				FetchCustOffersRes_type0 resType0 = new FetchCustOffersRes_type0();
				resType0 = add_CSAcct_res_msg.getFetchCustOffersRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqLoanTopUpRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<fetchCustOffersRes>"+
						"<customerId>"+ resType0.getCustomerId()+"</customerId>"+
						"<offerId>"+ resType0.getOfferId()+"</offerId>"+
						"<offerType>"+ resType0.getOfferType()+"</offerType>"+
						"<offerCode>"+ resType0.getOfferId()+"</offerCode>"+
						"<loanAcctNumber>"+ resType0.getLoanAcctNumber()+"</loanAcctNumber>"+
						"<productDesc>"+ resType0.getProductDesc()+"</productDesc>"+
						"<productCode>"+ resType0.getProductCode()+"</productCode>"+
						"<salarySTL>"+ resType0.getSalarySTL()+"</salarySTL>"+
						"<pFEligibility1>"+ resType0.getPFEligibility1()+"</pFEligibility1>"+
						"<pFEligibility2>"+ resType0.getPFEligibility2()+"</pFEligibility2>"+
						"<pFEligibility3>"+ resType0.getPFEligibility3()+"</pFEligibility3>"+
						//"<employerName>"+ resType0.getOfferId()+"</employerName>"+
						"<employerName>"+ resType0.getEmployerName()+"</employerName>"+
						"<maxiFinanceTenor>"+ resType0.getMaxiFinanceTenor()+"</maxiFinanceTenor>"+
						"<ROP_ROI>"+ resType0.getROP_ROI()+"</ROP_ROI>"+
						"<newComdtyRatePerUnit>"+ resType0.getNewComdtyRatePerUnit()+"</newComdtyRatePerUnit>"+
						"<newComdtyUnit>"+ resType0.getNewComdtyUnit()+"</newComdtyUnit>"+
						"<newComdtyName>"+ resType0.getNewComdtyName()+"</newComdtyName>"+
						"<existingComdtyRatePerUnit>"+ resType0.getExistingComdtyRatePerUnit()+"</existingComdtyRatePerUnit>"+
						"<existingComdtyUnit>"+ resType0.getExistingComdtyUnit()+"</existingComdtyUnit>"+
						"<existingComdtyName>"+ resType0.getExistingComdtyName()+"</existingComdtyName>"+
						"<dateOfEMI>"+ resType0.getDateOfEMI()+"</dateOfEMI>"+
						"<processingFeeType>"+ resType0.getProcessingFeeType()+"</processingFeeType>"+
						"<processingFee>"+ resType0.getProcessingFee()+"</processingFee>"+
						"<minProcessingFee>"+ resType0.getMinProcessingFee()+"</minProcessingFee>"+
						"<maxProcessingFee>"+ resType0.getMaxProcessingFee()+"</maxProcessingFee>"+
						"<insuranceFeeType>"+ resType0.getInsuranceFeeType()+"</insuranceFeeType>"+
						"<insuranceFee>"+ resType0.getInsuranceFee()+"</insuranceFee>"+
						"<earlySettlementFeeType>"+ resType0.getEarlySettlementFeeType()+"</earlySettlementFeeType>"+
						"<earlySettlementFee>"+ resType0.getEarlySettlementFee()+"</earlySettlementFee>"+
						"</fetchCustOffersRes>"+
						"</Response>"+
						"</Output>";
				// LogGEN.writeTrace("CBG_Log", "output xml within if block FetchCustOffersRequestToFCR:" + sOutput);
			}
			else
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustOffersRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchCustOffers Details</Status></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviic FetchCustOffersRequestToFCR :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice FetchCustOffersRequestToFCR :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustOffersRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchCustOffers Details</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() FetchCustOffersRequestToFCR:"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() FetchCustOffersRequestToFCR:"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchCustOffersRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchCustOffers Details</Status></Output>";
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
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequestToFCR" + inputXml);
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
			LogGEN.writeTrace("CBG_Log","FetchCustOffersRequest Query : finally :"+Query);
			//				 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sOutput;		
	}
}


