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
import com.newgen.dscop.stub.InqCentralBankOpsStub;
import com.newgen.dscop.stub.InqCentralBankOpsStub.CustomerBorrowersInfo_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.CustomerData_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetComCustomerInfoReqMsg;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetComCustomerInfoReq_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetComCustomerInfoResMsg;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetComCustomerInfoRes_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetCustomerRatingReqMsg;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetCustomerRatingReq_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetCustomerRatingResMsg;
import com.newgen.dscop.stub.InqCentralBankOpsStub.GetCustomerRatingRes_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.HeaderType;
import com.newgen.dscop.stub.InqCentralBankOpsStub.RespStat_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.RespStat_type1;
import com.newgen.dscop.stub.InqCentralBankOpsStub.RespStat_type2;
import com.newgen.dscop.stub.InqCentralBankOpsStub.RespStat_type3;
import com.newgen.dscop.stub.InqCentralBankOpsStub.ResponseDetail_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.ResponseDetail_type0E;
import com.newgen.dscop.stub.InqCentralBankOpsStub.ResponseList_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.ResponseList_type1;
import com.newgen.dscop.stub.InqCentralBankOpsStub.ResponseList_type2;
import com.newgen.dscop.stub.InqCentralBankOpsStub.Response_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.Response_type1;
import com.newgen.dscop.stub.InqCentralBankOpsStub.WsData_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.WsData_type1;
import com.newgen.dscop.stub.InqCentralBankOpsStub.WsRespList_type0;
import com.newgen.dscop.stub.InqCentralBankOpsStub.WsRespList_type1;

public class CentralBank extends DSCOPServiceHandler {
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sCustomerRatingOutput="";
	String sCustomerInfoOutput="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";
	
	public String fetchCustomerRating(String sInputXML){

		
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called CustomerRating");
		LogGEN.writeTrace("CBG_Log", "CentralBank sInputXML---"+sInputXML);
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
		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block:  CustomerRating");
			String wsdl = loadWSDLDtls(sHandler);
			String ref_no = xmlDataParser.getValueOf("Ref_No");
			String senderId =  xmlDataParser.getValueOf("SenderId");
			InqCentralBankOpsStub central_bankOps_stub = new InqCentralBankOpsStub(wsdl);
			GetCustomerRatingReqMsg customer_rating_req_msg = new GetCustomerRatingReqMsg();
			GetCustomerRatingReq_type0 customer_rating_req_type0 = new GetCustomerRatingReq_type0();
			GetCustomerRatingResMsg customer_rating_res_msg=new GetCustomerRatingResMsg();
					
			customer_rating_req_msg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			//customer_rating_req_type0.setCin(xmlDataParser.getValueOf("cin"));
			customer_rating_req_type0.setFirstName(xmlDataParser.getValueOf("firstName"));
			/*if(xmlDataParser.getValueOf("secondName") != null && !"".equalsIgnoreCase(xmlDataParser.getValueOf("secondName")))
			{
				customer_rating_req_type0.setSecondName(xmlDataParser.getValueOf("secondName"));
			}*/
			//customer_rating_req_type0.setThirdName(xmlDataParser.getValueOf("thirdName"));
			customer_rating_req_type0.setFamilyName(xmlDataParser.getValueOf("familyName"));
			//customer_rating_req_type0.setFileNumber(xmlDataParser.getValueOf("fileNumber"));			
			customer_rating_req_msg.setGetCustomerRatingReq(customer_rating_req_type0);
			central_bankOps_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = central_bankOps_stub.getInputXml(customer_rating_req_msg);
			LogGEN.writeTrace("CBG_Log", "InputXML: CustomerRating--->\n" + xmlInput);
			customer_rating_res_msg=central_bankOps_stub.getCustomerRating_Oper(customer_rating_req_msg);
			sCustomerRatingOutput = central_bankOps_stub.resCustomerRating;
//			LogGEN.writeTrace("CBG_Log", "OutputXML:  CustomerRating--->\n " + sCustomerRatingOutput);
			
		
//			LogGEN.writeTrace("CBG_Log", "res_msg"+customer_rating_res_msg);
			HeaderType header=customer_rating_res_msg.getHeader();
			LogGEN.writeTrace("CBG_Log", "res_msg.getHeader() CustomerRating:"+customer_rating_res_msg.getHeader());
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode() CustomerRating:"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail() CustomerRating:"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription() CustomerRating:"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				GetCustomerRatingRes_type0 customerRatingRes_type0=new GetCustomerRatingRes_type0();
				
				customerRatingRes_type0 = customer_rating_res_msg.getGetCustomerRatingRes();
				ResponseDetail_type0E response_dtl_type0 = new ResponseDetail_type0E();
				RespStat_type2 respStat_type2 = new RespStat_type2();
				ResponseList_type2 responseList_type2 = new ResponseList_type2();
				Response_type1 response_type1 = new Response_type1();
				RespStat_type3 respStat_type3 = new RespStat_type3();
				WsData_type1 wsData_type1 = new WsData_type1();
				WsRespList_type1 wsRespList_type1 = new WsRespList_type1();
				ResponseList_type1 [] responseList_type1;
				response_dtl_type0 = customerRatingRes_type0.getResponseDetail();
				respStat_type2 = response_dtl_type0.getRespStat();
				responseList_type2 = response_dtl_type0.getResponseList();
				response_type1 = responseList_type2.getResponse();
				respStat_type3 = response_type1.getRespStat();
				wsData_type1 = response_type1.getWsData();
				wsRespList_type1 = response_type1.getWsRespList();
				responseList_type1 = wsRespList_type1.getResponseList();
				
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>CB_Customer_Rating</Option>" +
				"<returnCode>"+sReturnCode+"</returnCode>" +
				"<errorDescription>"+sErrorDetail+"</errorDescription>"+
				"<getCustomerRatingRes>"+
	            "<responseDetail>"+
	               "<respStat>"+
	                  "<wsiRefNo>"+respStat_type2.getWsiRefNo()+"</wsiRefNo>"+
	                  "<responseCode>"+respStat_type2.getResponseCode()+"</responseCode>"+
	               "</respStat>"+
	               "<responseList>"+
	                  "<response>"+
	                     "<respStat>"+
	                        "<responseCode>"+respStat_type3.getResponseCode()+"</responseCode>"+
	                     "</respStat>"+
	                     "<wsData>"+
	                        "<firstName>"+wsData_type1.getFirstName()+"</firstName>"+
	                        "<familyName>"+wsData_type1.getFamilyName()+"</familyName>"+
	                        "<fileNumber>"+wsData_type1.getFileNumber()+"</fileNumber>"+
	                     "</wsData>"+
	                     "<wsRespList>";
				
				if(responseList_type1 != null){
					for (int j=0;j<responseList_type1.length;j++)
					{
		          		sOutput+=  "<responseList>"+
                       "<bankName>"+responseList_type1[j].getBankName().trim()+"</bankName>"+
                       "<referenceNum>"+responseList_type1[j].getReferenceNum().trim()+"</referenceNum>"+
                       "<cin>"+responseList_type1[j].getCin().trim()+"</cin>"+
                       "<firstName>"+responseList_type1[j].getFirstName().trim()+"</firstName>"+
                       "<secondName>"+responseList_type1[j].getSecondName().trim()+"</secondName>"+
                       "<thirdName>"+responseList_type1[j].getThirdName().trim()+"</thirdName>"+
                       "<familyName>"+responseList_type1[j].getFamilyName().trim()+"</familyName>"+
                       "<fileNumber>"+responseList_type1[j].getFileNumber().trim()+"</fileNumber>"+
                       "<cusNatid>"+responseList_type1[j].getCusNatid().trim()+"</cusNatid>"+
                       "<currentRating>"+responseList_type1[j].getCurrentRating().trim()+"</currentRating>"+
                       "</responseList>";
					}
				}
				
				sOutput +=  "</wsRespList>"+
							"</response>"+
							"</responseList>"+
							"</responseDetail>"+
					        "</getCustomerRatingRes>"+
							"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block CustomerRating:"+sOutput);
				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Customer_Rating</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer rating </td></Output>";
			}
			LogGEN.writeTrace("CBG_Log", "output xml within if block  sOutput: CustomerRating:"+sOutput);
			
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail=e.getMessage();
			sReturnCode="-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Customer_Rating</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer rating</td></Output>";
			e.printStackTrace();
			
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","CentralBank outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Customer_Rating</Option><Output><returnCode>"+sReturnCode+"</returnCode>"
						+ "<errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer rating</td></Output>";
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
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sCustomerRatingOutput.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return sOutput;
		
		
	}
	
	public String fetchCustomerInfo(String sInputXML){

		
		String Status="";
		LogGEN.writeTrace("CBG_Log", "Fuction called CustomerInfo");
		LogGEN.writeTrace("CBG_Log", "fetchCustomerInfo sInputXML CustomerInfo---"+sInputXML);
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
		try 
		{
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdl=loadWSDLDtls(sHandler);
			String ref_no=xmlDataParser.getValueOf("Ref_No");
			String senderId =  xmlDataParser.getValueOf("SenderId");
			InqCentralBankOpsStub central_bankOps_stub = new InqCentralBankOpsStub(wsdl);
			GetComCustomerInfoReqMsg com_cust_info_req_msg = new GetComCustomerInfoReqMsg();
			GetComCustomerInfoReq_type0 com_cust_info_req_type0 = new GetComCustomerInfoReq_type0();
			GetComCustomerInfoResMsg com_cust_info_res_msg = new GetComCustomerInfoResMsg();
			com_cust_info_req_msg.setHeader(setHeaderDtls(sDate, ref_no, senderId));
			//com_cust_info_req_type0.setCin(xmlDataParser.getValueOf("CIN"));
			//com_cust_info_req_type0.setCsNo(xmlDataParser.getValueOf("CSNo"));
			//com_cust_info_req_type0.setCustArabName(xmlDataParser.getValueOf("CustArabName"));
			com_cust_info_req_type0.setCustEngName(xmlDataParser.getValueOf("CustEngName"));
			//com_cust_info_req_type0.setCustIdNo(xmlDataParser.getValueOf("CustIdNo"));
			com_cust_info_req_msg.setGetComCustomerInfoReq(com_cust_info_req_type0);
			central_bankOps_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = central_bankOps_stub.getInputXml1(com_cust_info_req_msg);
			LogGEN.writeTrace("CBG_Log", "InputXML: " + xmlInput);
			com_cust_info_res_msg=central_bankOps_stub.getComCustomerInfo_Oper(com_cust_info_req_msg);
			sCustomerInfoOutput = central_bankOps_stub.resComCustomerInfo;
			LogGEN.writeTrace("CBG_Log", "OutputXML: " + sCustomerInfoOutput);
			
			LogGEN.writeTrace("CBG_Log", "res_msg"+com_cust_info_res_msg);
			HeaderType header=com_cust_info_res_msg.getHeader();
			LogGEN.writeTrace("CBG_Log", "res_msg.getHeader()"+com_cust_info_res_msg.getHeader());
			sReturnCode=header.getReturnCode();
			LogGEN.writeTrace("CBG_Log", "header.getReturnCode()"+header.getReturnCode());
			sErrorDetail=header.getErrorDetail();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDetail()"+header.getErrorDetail());
			sErrorDesc=header.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "header.getErrorDescription()"+header.getErrorDescription());
			LogGEN.writeTrace("CBG_Log", "sReturnCode--"+sReturnCode+"||sErrorDetail--"+sErrorDetail+"sErrorDesc--"+sErrorDesc);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");
				GetComCustomerInfoRes_type0 com_customerInfoRes_type0 = new GetComCustomerInfoRes_type0();
				com_customerInfoRes_type0 = com_cust_info_res_msg.getGetComCustomerInfoRes();
				ResponseDetail_type0 response_dtl_type0 = new ResponseDetail_type0();
				RespStat_type0 respstat_type0 = new RespStat_type0();
				ResponseList_type0 responseList_type0 = new ResponseList_type0();
				Response_type0 response_type0 = new Response_type0();
				RespStat_type1 respstat_type1 = new RespStat_type1();
				WsData_type0 wsData_type0 = new WsData_type0();
				response_dtl_type0 = com_customerInfoRes_type0.getResponseDetail();
				respstat_type0 = response_dtl_type0.getRespStat();
				responseList_type0 = response_dtl_type0.getResponseList();
				response_type0 = responseList_type0.getResponse();
				respstat_type1 = response_type0.getRespStat();
				wsData_type0 = response_type0.getWsData();
				WsRespList_type0 wsResp = response_type0.getWsRespList();
				
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>"+
				"<Option>CB_Customer_Info</Option>" +
				"<returnCode>"+ sReturnCode +"</returnCode>" +
				"<errorDescription>"+ sErrorDetail +"</errorDescription>"+
				"<getComCustomerInfoRes>"+
	            "<ResponseDetail>"+
	               "<respStat>"+
	                  "<wsiRefNo>"+ respstat_type0.getWsiRefNo() +"</wsiRefNo>"+
	                  "<responseCode>"+ respstat_type0.getResponseCode() +"</responseCode>"+
	               "</respStat>"+
	               "<responseList>"+
	                  "<response>"+
	                     "<respStat>"+
	                        "<responseCode>"+ respstat_type1.getResponseCode() +"</responseCode>"+
	                     "</respStat>"+
	                     "<wsData>"+
	                        "<cin>"+ wsData_type0.getCin() +"</cin>"+
	                        "<custEngName>"+ wsData_type0.getCustEngName() +"</custEngName>"+
	                     "</wsData>"+
	                     "<wsRespList>";
				
				
				if(wsResp != null){
					CustomerData_type0[] custData = wsResp.getCustomerData();
					if(custData != null){
						for (int j=0;j < custData.length;j++)
						{
							CustomerBorrowersInfo_type0 borrower = custData[j].getCustomerBorrowersInfo();
			          	   sOutput +=  "<responseList>"+
	                       "<cin>"+ custData[j].getCin() +"</cin>"+
	                       "<csNo>"+ custData[j].getCsNo() +"</csNo>"+
	                       "<bankCode>"+ custData[j].getBankCode() +"</bankCode>"+
	                       "<custEngName>"+ custData[j].getCustEngName() +"</custEngName>";
	                       if(borrower != null) {
	                    	   sOutput += "<licIdNo>"+ borrower.getLicIdNo() +"</licIdNo>"+
			                   "<facClass>"+ borrower.getFacClass() +"</facClass>"+
			                   "<borrowerType>"+ borrower.getBorrowerType() +"</borrowerType>"+
			                   "<businessType>"+ borrower.getBusinessType() +"</businessType>"+
			                   "<nat>"+ borrower.getNat() +"</nat>"+
			                   "<estDate>"+ borrower.getEstDate() +"</estDate>"+
			                   "<capital>"+ borrower.getCapital() +"</capital>"+
			                   "<auditor>"+ borrower.getAuditor() +"</auditor>"+
			                   "<provision>"+ borrower.getProvision() +"</provision>"+
			                   "<suspndIntr>"+ borrower.getSuspndIntr() +"</suspndIntr>"+
			                   "</responseList>";
	                       }
	                       
						}
					}
				}
				
				sOutput +=  "</wsRespList>"+
							"</response>"+
							"</responseList>"+
							"</responseDetail>"+
					        "</getComCustomerInfoRes>"+
							"</Output>";
	                 
				LogGEN.writeTrace("CBG_Log", "output xml within if block Customer Info--------"+ sOutput);
				
			} else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CB_Customer_Info</Option><Output><returnCode>"+ sReturnCode +"</returnCode>"
						+ "<errorDescription>"+ sErrorDetail +"</errorDescription><td>Unable to fetch customer info </td></Output>";
			}
			
		}
		catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("CBG_Log","Catch:"+sw.toString());
			sErrorDetail = e.getMessage();
			sReturnCode = "-1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CB_Customer_Info</Option><Output><returnCode>"+sReturnCode+"</returnCode>"
					+ "<errorDescription>"+ sErrorDetail +"</errorDescription><td>Unable to fetch customer info</td></Output>";
			e.printStackTrace();
			
		}
		finally 
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+ sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>CB_Customer_Info</Option><Output><returnCode>"+sReturnCode+"</returnCode>"
						+ "<errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch customer info</td></Output>";
			}			
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("CBGCalltype");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sCustomerInfoOutput.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return sOutput;
		
	}
	
	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("CentralBank");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("CentralBank");
			LogGEN.writeTrace("CBG_Log", "CentralBank WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "CentralBank WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "CentralBank CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "CentralBank USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "CentralBank PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "CentralBank LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "CentralBank TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}
	
	private HeaderType setHeaderDtls(String sDate,String ref_no, String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("InqCentralBankOps");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Inquiry");
		headerType.setSysRefNumber(ref_no);
		headerType.setSenderID(senderId); 
		headerType.setConsumer("BPM-WMS");
		headerType.setReqTimeStamp(sDate);
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		
		 
		return headerType;
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
}
