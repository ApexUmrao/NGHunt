package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModIslamicCommodityTransactionStub;
import com.newgen.dscop.stub.ModIslamicCommodityTransactionStub.HeaderType;
import com.newgen.dscop.stub.ModIslamicCommodityTransactionStub.ModIslamicCommodityTransactionReqMsg;
import com.newgen.dscop.stub.ModIslamicCommodityTransactionStub.ModIslamicCommodityTransactionReq_type0;
import com.newgen.dscop.stub.ModIslamicCommodityTransactionStub.ModIslamicCommodityTransactionResMsg;
import com.newgen.dscop.stub.ModIslamicCommodityTransactionStub.ModIslamicCommodityTransactionRes_type0;

public class MurabahaSaleCancelTransaction extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String MurabahaSaleCancelTransactionCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called MurabahaSaleCancelTransaction");
		LogGEN.writeTrace("CBG_Log", "MurabahaSaleCancelTransaction sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModIslamicCommodityTransaction");
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String referenceNumber = xmlDataParser.getValueOf("referenceNumber");
			String prevRefNumber = xmlDataParser.getValueOf("prevRefNumber");
			String approvalDate=xmlDataParser.getValueOf("approvalDate");
			String applicationNumber = xmlDataParser.getValueOf("applicationNumber");
			String branch = xmlDataParser.getValueOf("branch");
			String reInitiate = xmlDataParser.getValueOf("reInitiate");
			String product = xmlDataParser.getValueOf("product");
			String productCategory = xmlDataParser.getValueOf("productCategory");
			String transactionType = xmlDataParser.getValueOf("transactionType");
			String transactionKey = xmlDataParser.getValueOf("transactionKey");
			String customerName = xmlDataParser.getValueOf("customerName");
			String customerId = xmlDataParser.getValueOf("customerId");
			String emailId = xmlDataParser.getValueOf("emailId");
			String mobileNumber = xmlDataParser.getValueOf("mobileNumber");
			String financeAmount = xmlDataParser.getValueOf("financeAmount");
			String currency = xmlDataParser.getValueOf("currency");
			String tenor = xmlDataParser.getValueOf("tenor");
			String profitAmount = xmlDataParser.getValueOf("profitAmount");
			String emi = xmlDataParser.getValueOf("emi");
			String paymentDate = xmlDataParser.getValueOf("paymentDate");
			String finalPaymentDate = xmlDataParser.getValueOf("finalPaymentDate");
			String deferredPrice = xmlDataParser.getValueOf("deferredPrice");
			String transactionDate = xmlDataParser.getValueOf("transactionDate");
			String manual = xmlDataParser.getValueOf("manual");
			String purchaseId = xmlDataParser.getValueOf("purchaseId");
			String purchaseKey = xmlDataParser.getValueOf("purchaseKey");
			String commodity = xmlDataParser.getValueOf("commodity");
			String quantity = xmlDataParser.getValueOf("quantity");
			String unitPrice = xmlDataParser.getValueOf("unitPrice");
			String extraReferences = xmlDataParser.getValueOf("extraReferences");

			ModIslamicCommodityTransactionStub request_stub=new ModIslamicCommodityTransactionStub(sWSDLPath);
			ModIslamicCommodityTransactionReqMsg service_req = new ModIslamicCommodityTransactionReqMsg();			
			ModIslamicCommodityTransactionReq_type0 service_req_type0 = new ModIslamicCommodityTransactionReq_type0();
			ModIslamicCommodityTransactionResMsg service_response = new ModIslamicCommodityTransactionResMsg();
			ModIslamicCommodityTransactionRes_type0 service_response_type0 = new ModIslamicCommodityTransactionRes_type0();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("ModIslamicCommodityTransaction");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			service_req_type0.setReferenceNumber(referenceNumber);
			service_req_type0.setPrevRefNumber(prevRefNumber);
			service_req_type0.setApprovalDate(approvalDate);
			service_req_type0.setApplicationNumber(applicationNumber);
			service_req_type0.setBranch(branch);
			service_req_type0.setReInitiate(reInitiate);
			service_req_type0.setProduct(product);
			service_req_type0.setProductCategory(productCategory);
			service_req_type0.setTransactionType(transactionType);
			service_req_type0.setTransactionKey(transactionKey);
			service_req_type0.setCustomerName(customerName);
			service_req_type0.setCustomerId(customerId);
			service_req_type0.setEmailId(emailId);
			service_req_type0.setMobileNumber(mobileNumber);
			service_req_type0.setFinanceAmount(financeAmount);
			service_req_type0.setCurrency(currency);
			service_req_type0.setTenor(tenor);
			service_req_type0.setProfitAmount(profitAmount);
			service_req_type0.setEmi(emi);
			service_req_type0.setPaymentDate(paymentDate);
			service_req_type0.setFinalPaymentDate(finalPaymentDate);
			service_req_type0.setDeferredPrice(deferredPrice);
			service_req_type0.setTransactionDate(transactionDate);
			service_req_type0.setManual(manual);
			service_req_type0.setPurchaseId(purchaseId);
			service_req_type0.setPurchaseKey(purchaseKey);
			service_req_type0.setCommodity(commodity);
			service_req_type0.setQuantity(quantity);
			service_req_type0.setUnitPrice(unitPrice);
			service_req_type0.setExtraReferences(extraReferences);

			service_req.setModIslamicCommodityTransactionReq(service_req_type0);
			service_req.setHeader(Header_Input);
			service_response = request_stub.modIslamicCommodityTransaction_Oper(service_req);

			xmlInput= request_stub.getInputXML(service_req);
			Header_Input = service_response.getHeader();
			service_response_type0 = service_response.getModIslamicCommodityTransactionRes();
			LogGEN.writeTrace("CBG_Log", "MurabahaSaleCancelTransaction xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "MurabahaSaleCancelTransaction sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			String refNumber= service_response_type0.getReferenceNumber();
			String prevReferenceNumber= service_response_type0.getPrevReferenceNumber();
			String appNumber= service_response_type0.getApplicationNumber();
			String transType= service_response_type0.getTransactionType();
			String transId= service_response_type0.getTransactionId();
			String transKey= service_response_type0.getTransactionKey();
			String saleKey= service_response_type0.getSaleKey();
			String commo= service_response_type0.getCommodity();
			String quant= service_response_type0.getQuantity();
			String unitPric= service_response_type0.getUnitPrice();
			String status= service_response_type0.getStatus();
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>ModIslamicCommodityTransaction</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<ModIslamicCommodityTransactionRes>"+
						"<referenceNumber>"+refNumber+"</referenceNumber>" +
						"<prevReferenceNumber>"+prevReferenceNumber+"</prevReferenceNumber>"+
						"<applicationNumber>"+appNumber+"</applicationNumber>" +
						"<transactionType>"+transType+"</transactionType>"+
						"<transactionId>"+transId+"</transactionId>" +
						"<transactionKey>"+transKey+"</transactionKey>"+
						"<saleKey>"+saleKey+"</saleKey>" +
						"<commodity>"+commo+"</commodity>"+
						"<quantity>"+quant+"</quantity>" +
						"<unitPrice>"+unitPric+"</unitPrice>"+
						"<status>"+status+"</status>"+
						"</ModIslamicCommodityTransactionRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>MurabahaSaleCancelTransaction</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Cancel Mod Commodity Transaction</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>MurabahaSaleCancelTransaction</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Cancel Mod Commodity Transaction</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>MurabahaSaleCancelTransaction</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Cancel Mod Commodity Transaction</Output>";
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
			LogGEN.writeTrace("CBG_Log","MurabahaSaleCancelTransaction  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","MurabahaSaleCancelTransaction  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

