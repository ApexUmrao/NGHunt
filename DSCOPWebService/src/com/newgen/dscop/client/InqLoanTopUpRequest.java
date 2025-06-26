package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReqMsg;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsReq_type0;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsResMsg;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.FetchEMIDetailsRes_type0;
import com.newgen.dscop.stub.InqLoanTopUpRequestStub.HeaderType;

public class InqLoanTopUpRequest  extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	String xmlInput="";
	
	public String InqLoanTopUpRequestToFCR(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called InqLoanTopUpRequest");
		LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String customerId=xmlDataParser.getValueOf("customerId");
		String loanType=xmlDataParser.getValueOf("loanType");
		String productCode=xmlDataParser.getValueOf("productCode");
		String loanAmount=xmlDataParser.getValueOf("loanAmount");
		String loanTenor=xmlDataParser.getValueOf("loanTenor");
		String loanInterestRate=xmlDataParser.getValueOf("loanInterestRate");
		String loanDisbursedDate=xmlDataParser.getValueOf("loanDisbursedDate");
		String loanRepayFrequency=xmlDataParser.getValueOf("loanRepayFrequency");
		String loanFirstRepayDate=xmlDataParser.getValueOf("loanFirstRepayDate");
		String currency=xmlDataParser.getValueOf("currency");
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("InqLoanTopUpRequest");
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqLoanTopUpRequest TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			InqLoanTopUpRequestStub inqLoanstub=new InqLoanTopUpRequestStub(sWSDLPath);
			FetchEMIDetailsReq_type0 fetchEMIReq=new FetchEMIDetailsReq_type0();
			FetchEMIDetailsReqMsg fetchEMIReqMsg=new FetchEMIDetailsReqMsg();

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

			fetchEMIReqMsg.setHeader(Header_Input);

			fetchEMIReq.setCustomerId(customerId);
			fetchEMIReq.setLoanType(loanType);
			fetchEMIReq.setProductCode(productCode);
			fetchEMIReq.setLoanAmount(loanAmount);
			fetchEMIReq.setLoanTenor(loanTenor);
			fetchEMIReq.setLoanInterestRate(loanInterestRate);
			fetchEMIReq.setLoanDisbursedDate(loanDisbursedDate);
			fetchEMIReq.setLoanRepayFrequency(loanRepayFrequency);
			fetchEMIReq.setLoanFirstRepayDate(loanFirstRepayDate);
			fetchEMIReq.setCurrency(currency);
			fetchEMIReq.setMoraProfitRate(loanInterestRate);

			fetchEMIReqMsg.setFetchEMIDetailsReq(fetchEMIReq);
			inqLoanstub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=inqLoanstub.getInputXml(fetchEMIReqMsg);
			FetchEMIDetailsResMsg add_CSAcct_res_msg= inqLoanstub.fetchEMIDetails_Oper(fetchEMIReqMsg);
			sOrg_Output=inqLoanstub.outputXML;
			Header_Input=add_CSAcct_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				
				FetchEMIDetailsRes_type0 resType0 = new FetchEMIDetailsRes_type0();
				resType0 = add_CSAcct_res_msg.getFetchEMIDetailsRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>InqLoanTopUpRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<FetchEMIDetailsRes>"+
						"<customerId>"+ resType0.getCustomerId() +"</customerId>"+
						"<loanType>"+ resType0.getLoanType() +"</loanType>"+
						"<productCode>"+ resType0.getProductCode() +"</productCode>"+
						"<loanAmount>"+ resType0.getLoanAmount() +"</loanAmount>"+
						"<loanTenor>"+ resType0.getLoanTenor() +"</loanTenor>"+
						"<loanInterestRate>"+ resType0.getLoanInterestRate() +"</loanInterestRate>"+
						"<loanDisbursedDate>"+ resType0.getLoanDisbursedDate() +"</loanDisbursedDate>"+
						"<loanRepayFrequency>"+ resType0.getLoanRepayFrequency() +"</loanRepayFrequency>"+
						"<loanFirstRepayDate>"+ resType0.getLoanFirstRepayDate() +"</loanFirstRepayDate>"+
						"<loanEMIValue>"+ resType0.getLoanEMIValue() +"</loanEMIValue>"+
						"</FetchEMIDetailsRes>"+
						"</Response>"+
						"</Output>";
//				LogGEN.writeTrace("CBG_Log", "output xml within if block InqLoanTopUpRequestToFCR:" + sOutput);
			}
			else
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanTopUpRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchEMI Details</Status></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviic InqLoanTopUpRequestToFCR :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice InqLoanTopUpRequestToFCR :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanTopUpRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchEMI Details</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() InqLoanTopUpRequestToFCR:"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() InqLoanTopUpRequestToFCR:"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqLoanTopUpRequest</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to add FetchEMI Details</Status></Output>";
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
			LogGEN.writeTrace("CBG_Log","InqLoanTopUpRequest Query : finally :"+Query);
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


