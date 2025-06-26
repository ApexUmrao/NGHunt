package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqRetailCustomerRiskStub;
import com.newgen.dscop.stub.InqRetailCustomerRiskStub.FetchRetailCustomerRiskReq_type0;
import com.newgen.dscop.stub.InqRetailCustomerRiskStub.FetchRetailCustomerRiskReqMsg;
import com.newgen.dscop.stub.InqRetailCustomerRiskStub.FetchRetailCustomerRiskRes_type0;
import com.newgen.dscop.stub.InqRetailCustomerRiskStub.FetchRetailCustomerRiskResMsg;
import com.newgen.dscop.stub.InqRetailCustomerRiskStub.HeaderType;


public class InqRetailCustomerRisk  extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	String xmlInput="";
	
	public String inqRetailCustomerRiskCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called InqRetailCustomerRisk");
		LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String customerId=xmlDataParser.getValueOf("customerId");
		String preAssessmentFlag=xmlDataParser.getValueOf("preAssessmentFlag");
		String requestTimestamp=xmlDataParser.getValueOf("requestTimestamp");
		String occupation=xmlDataParser.getValueOf("occupation");
		String industry=xmlDataParser.getValueOf("industry");
		String accountOpeningPurpose=xmlDataParser.getValueOf("accountOpeningPurpose");
		String additionalSourceOfIncome=xmlDataParser.getValueOf("additionalSourceOfIncome");
		String sourceOfWealth=xmlDataParser.getValueOf("sourceOfWealth");
		String accountInOtherBanksFlag=xmlDataParser.getValueOf("accountInOtherBanksFlag");
		String pepIndicator=xmlDataParser.getValueOf("pepIndicator");
		String poaFlag=xmlDataParser.getValueOf("poaFlag");
		String nationality=xmlDataParser.getValueOf("nationality");
		String residence=xmlDataParser.getValueOf("residence");
		String sourceOfIncomeCountry=xmlDataParser.getValueOf("sourceOfIncomeCountry");
		String onboardingMethod=xmlDataParser.getValueOf("onboardingMethod");
		String product=xmlDataParser.getValueOf("product");
		String channelIndicator=xmlDataParser.getValueOf("channelIndicator");
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqRetailCustomerRisk");
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			InqRetailCustomerRiskStub inqRetailstub=new InqRetailCustomerRiskStub(sWSDLPath);
			FetchRetailCustomerRiskReq_type0 fetchRetailReq=new FetchRetailCustomerRiskReq_type0();
			FetchRetailCustomerRiskReqMsg fetchRetailReqMsg=new FetchRetailCustomerRiskReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqRetailCustomerRisk");
			Header_Input.setVersionNo("1.0");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setConsumer("");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setCorrelationID("12456");
			Header_Input.setUsername("1234");
			Header_Input.setCredentials(loggedinuser);

			LogGEN.writeTrace("CBG_Log", "All values set InqRetailCustomerRisk");

			fetchRetailReqMsg.setHeader(Header_Input);

			fetchRetailReq.setCustomerId(customerId);
			fetchRetailReq.setPreAssessmentFlag(preAssessmentFlag);;
			fetchRetailReq.setRequestTimestamp(requestTimestamp);
			fetchRetailReq.setOccupation(occupation);
			fetchRetailReq.setIndustry(industry);
			fetchRetailReq.setAccountOpeningPurpose(accountOpeningPurpose);
			fetchRetailReq.setAdditionalSourceOfIncome(additionalSourceOfIncome);
			fetchRetailReq.setSourceOfWealth(sourceOfWealth);
			fetchRetailReq.setAccountInOtherBanksFlag(accountInOtherBanksFlag);
			fetchRetailReq.setPepIndicator(pepIndicator);
			fetchRetailReq.setNationality(nationality);
			fetchRetailReq.setResidence(residence);
			fetchRetailReq.setSourceOfIncomeCountry(sourceOfIncomeCountry);
			fetchRetailReq.setOnboardingMethod(onboardingMethod);
			fetchRetailReq.setProduct(product);
			fetchRetailReq.setChannelIndicator(channelIndicator);
			fetchRetailReq.setPoaFlag(poaFlag);

			fetchRetailReqMsg.setFetchRetailCustomerRiskReq(fetchRetailReq);;
			inqRetailstub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=inqRetailstub.getInputXml(fetchRetailReqMsg);
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk InputXML: " + xmlInput);
			FetchRetailCustomerRiskResMsg res_msg= inqRetailstub.fetchRetailCustomerRisk_Oper(fetchRetailReqMsg);
			sOrg_Output=inqRetailstub.outputXML;
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk res_msg: "+sOrg_Output);
			Header_Input=res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				
				FetchRetailCustomerRiskRes_type0 resType0 = new FetchRetailCustomerRiskRes_type0();
				resType0 = res_msg.getFetchRetailCustomerRiskRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqRetailCustomerRisk</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<FetchRetailCustomerRiskRes>"+
						"<customerId>"+ resType0.getCustomerId() +"</customerId>"+
						"<responseTimestamp>"+ resType0.getResponseTimestamp() +"</responseTimestamp>"+
						"<finalRisk>"+ resType0.getFinalRisk() +"</finalRisk>"+
						"<preOverrideRisk>"+ resType0.getPreOverrideRisk() +"</preOverrideRisk>"+
						"<riskScore>"+ resType0.getRiskScore() +"</riskScore>"+
						"<remarks>"+ resType0.getRemarks() +"</remarks>"+
						"</FetchRetailCustomerRiskRes>"+
						"</Response>"+
						"</Output>";
//				LogGEN.writeTrace("CBG_Log", "output xml within if block InqRetailCustomerRisk:" + sOutput);
			}
			else
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqRetailCustomerRisk</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchEMI Details</Status></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviic InqRetailCustomerRisk :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice InqRetailCustomerRisk :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqRetailCustomerRisk</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchEMI Details</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() InqRetailCustomerRisk:"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() InqRetailCustomerRisk:"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqRetailCustomerRisk</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchEMI Details</Status></Output>";
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
			LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk" + inputXml);
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
			LogGEN.writeTrace("CBG_Log","InqRetailCustomerRisk Query : finally :"+Query);
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


