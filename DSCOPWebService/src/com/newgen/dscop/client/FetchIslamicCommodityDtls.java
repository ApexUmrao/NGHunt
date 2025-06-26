package com.newgen.dscop.client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub;
import com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchIslamicCommodityDtlsReqMsg;
import com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchIslamicCommodityDtlsReq_type0;
import com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchIslamicCommodityDtlsResMsg;
import com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.FetchIslamicCommodityDtlsRes_type0;
import com.newgen.dscop.stub.BinValidationInqCBGCustomerOnboardingStub.HeaderType;

public class FetchIslamicCommodityDtls extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrgRes="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	String sFetchIslamicCommodityDtls = "";
	
	public String FetchIslamicCommodity(String sInputXML){
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called FetchIslamicCommodity");
		LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodity sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		String xmlInput="";
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		String requestDate =  new SimpleDateFormat("dd/mm/yyyy").format(new Date());
		
		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block:  FetchIslamicCommodity");
			String wsdl = loadWSDLDtls(sHandler);
			String ref_no = xmlDataParser.getValueOf("Ref_No");
			String senderId =  xmlDataParser.getValueOf("SenderId");
			LogGEN.writeTrace("CBG_Log", "senderId:  FetchIslamicCommodity " + senderId);
			BinValidationInqCBGCustomerOnboardingStub bvStub = new BinValidationInqCBGCustomerOnboardingStub(wsdl);
			FetchIslamicCommodityDtlsReqMsg reqMsg = new FetchIslamicCommodityDtlsReqMsg();
			FetchIslamicCommodityDtlsReq_type0 reqType = new FetchIslamicCommodityDtlsReq_type0();
			reqType.setRequestDate(requestDate);
			reqMsg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			reqMsg.setFetchIslamicCommodityDtlsReq(reqType);			
			bvStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = bvStub.getInputFetchIslamicCommodity(reqMsg);

			FetchIslamicCommodityDtlsResMsg resMsg = bvStub.fetchIslamicCommodityDtls_Oper(reqMsg);
			sFetchIslamicCommodityDtls = bvStub.resBinValidation;
			
			HeaderType header=resMsg.getHeader();
			LogGEN.writeTrace("CBG_Log", "res_msg.getHeader() FetchIslamicCommodity:"+resMsg.getHeader());
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode() FetchIslamicCommodity:"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail() FetchIslamicCommodity:"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription() FetchIslamicCommodity:"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				
				FetchIslamicCommodityDtlsRes_type0 resType0 = new FetchIslamicCommodityDtlsRes_type0();
				resType0 = resMsg.getFetchIslamicCommodityDtlsRes();
				
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>FetchIslamicCommodity</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
							"<FetchIslamicCommodityDtls>"+
							"<commodity>"+ resType0.getCommodity() +"</commodity>"+
							"<commodityUnit>"+ resType0.getCommodityUnit() +"</commodityUnit>"+
							"<commodityValue>"+ resType0.getCommodityValue() +"</commodityValue>"+
							"<profitRate>"+ resType0.getProfitRate() +"</profitRate>"+
							"</FetchIslamicCommodityDtls>"+
						"</Response>"+
				"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block FetchIslamicCommodityDtls:" + sOutput);
				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchIslamicCommodityDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Validate Card Bin</td></Output>";
			}
			LogGEN.writeTrace("CBG_Log", "output xml within if block  sOutput: FetchIslamicCommodityDtls:"+ sOutput);
			
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchIslamicCommodityDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Validate Card Bin</td></Output>";
			e.printStackTrace();
			
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","FetchIslamicCommodityDtls outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>FetchIslamicCommodityDtls</Option><Output><returnCode>"+sReturnCode+"</returnCode>"
						+ "<errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to Validate Card Bin</td></Output>";
			}
			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")){
				Status="Success";
			}
			else {
				Status="Failure";
			}
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame = xmlDataParser.getValueOf("WiName");
			String sessionID = xmlDataParser.getValueOf("SessionId");
			String call_type = xmlDataParser.getValueOf("CBGCalltype");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) "
				+ "values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally  :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sFetchIslamicCommodityDtls.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return sOutput;
	}
	
	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("CentralBank");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("BinValidation");
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls WebServiceConfig Map : " + wsConfig.toString());
			sWSDLPath = (String)wsConfig.get(0);
			sCabinet = (String)wsConfig.get(1);
			sUser = (String)wsConfig.get(2);
			sLoginReq = Boolean.valueOf((String)wsConfig.get(3));
			sPassword = (String)wsConfig.get(4);
			lTimeOut = Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FetchIslamicCommodityDtls TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}
	
	private HeaderType setHeaderDtls(String sDate,String ref_no, String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		//headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCBGCustomerOnboarding");
		headerType.setVersionNo("1.0");
		//headerType.setServiceAction("Addition");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		//headerType.setConsumer("BPM-WMS");
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
