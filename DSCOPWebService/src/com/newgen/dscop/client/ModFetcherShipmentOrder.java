package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub.CreateFetcherShipmentOrderReqMsg;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub.CreateFetcherShipmentOrderReq_type0;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub.CreateFetcherShipmentOrderResMsg;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub.CreateFetcherShipmentOrderRes_type0;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub.ExtraData_type0;
import com.newgen.dscop.stub.ModFetcherShipmentOrderStub.HeaderType;

public class ModFetcherShipmentOrder extends DSCOPServiceHandler{

	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String xmlInput="";
	String sOrgRes="";
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";

	public String FetcherShipmentOrder(String sInputXML){
		String Status="";                        
		LogGEN.writeTrace("CBG_Log", "Fuction called FetcherShipmentOrder");
		LogGEN.writeTrace("CBG_Log", "FetcherShipmentOrder sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";		
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try {
			LogGEN.writeTrace("CBG_Log", "inside try block");
			String wsdlpath=loadWSDLDtls(sHandler);             
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String senderId = xmlDataParser.getValueOf("SenderId");
			LogGEN.writeTrace("CBG_Log", "read Property successfully");
			ModFetcherShipmentOrderStub fetcherShipmentOrderStub =new ModFetcherShipmentOrderStub(wsdlpath);
			CreateFetcherShipmentOrderReqMsg orderReqMsg=new CreateFetcherShipmentOrderReqMsg();
			CreateFetcherShipmentOrderReq_type0 orderReq_type0=new CreateFetcherShipmentOrderReq_type0();
			ExtraData_type0 extraData_type0=new ExtraData_type0();

			orderReqMsg.setHeader(setHeaderDtls(sDate,ref_no,senderId));

			orderReq_type0.setOrderReference(xmlDataParser.getValueOf("orderReference"));
			orderReq_type0.setName(xmlDataParser.getValueOf("name"));
			orderReq_type0.setEmail(xmlDataParser.getValueOf("email"));
			orderReq_type0.setPhoneNumber(xmlDataParser.getValueOf("phoneNumber"));
			orderReq_type0.setAlternatePhone(xmlDataParser.getValueOf("alternatePhone"));
			orderReq_type0.setAddress(xmlDataParser.getValueOf("address"));
			orderReq_type0.setReceiverCountry(xmlDataParser.getValueOf("receiverCountry"));
			orderReq_type0.setReceiverCity(xmlDataParser.getValueOf("receiverCity"));
			orderReq_type0.setPaymentType(xmlDataParser.getValueOf("paymentType"));
			orderReq_type0.setTotalAmount(xmlDataParser.getValueOf("totalAmount"));
			orderReq_type0.setWeight(xmlDataParser.getValueOf("weight"));
			orderReq_type0.setLatitude(xmlDataParser.getValueOf("latitude"));
			orderReq_type0.setLongitude(xmlDataParser.getValueOf("longitude"));
			orderReq_type0.setBagCount(xmlDataParser.getValueOf("bagCount"));
			orderReq_type0.setDescription(xmlDataParser.getValueOf("description"));
			orderReq_type0.setComments(xmlDataParser.getValueOf("comments"));
			orderReq_type0.setOrderPackageType(xmlDataParser.getValueOf("orderPackageType"));
			orderReq_type0.setOriginClientName(xmlDataParser.getValueOf("originClientName"));
			String[] array=new String[2];
			array[0]=xmlDataParser.getValueOf("extraDocuments");
			array[1]=xmlDataParser.getValueOf("deliveryOption");
			extraData_type0.setExtraDocuments(array);
			orderReq_type0.setExtraData(extraData_type0);
			orderReqMsg.setCreateFetcherShipmentOrderReq(orderReq_type0);

			fetcherShipmentOrderStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(this.lTimeOut);
			xmlInput=fetcherShipmentOrderStub.getInputXml(orderReqMsg);
			LogGEN.writeTrace("CBG_Log", "ModFetcherShipmentOrder InputXML: " + xmlInput);
			CreateFetcherShipmentOrderResMsg orderResMsg=fetcherShipmentOrderStub.createFetcherShipmentOrder_Oper(orderReqMsg);
			sOrgRes = fetcherShipmentOrderStub.outputXML;
			LogGEN.writeTrace("CBG_Log", "ModFetcherShipmentOrder OutputXML: " + sOrgRes);
			HeaderType headerType=orderResMsg.getHeader();
			sReturnCode=headerType.getReturnCode();
			sErrorDetail=headerType.getErrorDetail();
			sErrorDesc=headerType.getErrorDescription();
			LogGEN.writeTrace("CBG_Log", "Error Description--- " + sErrorDesc+"|| sReturnCode-- "+sReturnCode+"|| sErrorDetail-- "+sErrorDetail);


			if ((!(sErrorDesc.equalsIgnoreCase("Failure"))) || (!(sReturnCode.equalsIgnoreCase("1")))){
				CreateFetcherShipmentOrderRes_type0 orderRes_type0=new CreateFetcherShipmentOrderRes_type0();
				orderRes_type0=orderResMsg.getCreateFetcherShipmentOrderRes();

				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>ModFetcherShipmentOrder</Option><returnCode>" + 
						sReturnCode + "</returnCode>" + 
						"<errorDescription>" + sErrorDetail + "</errorDescription>" + 
						"<createFetcherShipmentOrderRes>"+
						"<message>"+orderRes_type0.getOrderDetails().getMessage()+"</message>"+
						"<airwayBillPrintLink>"+orderRes_type0.getOrderDetails().getAirwayBillPrintLink()+"</airwayBillPrintLink>"+
						"<trackingNumber>"+orderRes_type0.getOrderDetails().getTrackingNumber()+"</trackingNumber>"+
						"<orderReference>"+orderRes_type0.getOrderDetails().getOrderReference()+"</orderReference>"+
						"<shipmentNumber>"+orderRes_type0.getOrderDetails().getShipmentNumber()+"</shipmentNumber>"+
						"<serviceOrderNumber>"+orderRes_type0.getOrderDetails().getServiceOrderNumber()+"</serviceOrderNumber>"+
						"<traceId>"+orderRes_type0.getOrderDetails().getTraceId()+"</traceId>"+
						"<status>"+orderRes_type0.getOrderDetails().getStatus()+"</status>"+
						"</createFetcherShipmentOrderRes>"+
						"</Output>";
			}
			else{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModFetcherShipmentOrder</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to execute ModFetcherShipmentOrder.</td></Output>";
			}

		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :" + e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :" + e.getStackTrace());
			System.out.println("Error Trace in Web Serviice :" + e.getStackTrace());
			Status = "Failure";
			sErrorDetail = e.getMessage();
			sReturnCode = "-1";
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModFetcherShipmentOrder</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to execute ModFetcherShipmentOrder.</td></Output>";
			e.printStackTrace();
		}
		finally{
			LogGEN.writeTrace("CBG_Log", "outputXML.trim().length() :" + sOutput.trim().length()); 
			System.out.println("outputXML.trim().length() :" + sOutput.trim().length());
		}
		if (sOutput.trim().length() < 1)
		{
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModFetcherShipmentOrder</Option><Output><returnCode>" + sReturnCode + "</returnCode><errorDescription>" + sErrorDetail + "</errorDescription><td>Unable to execute ModFetcherShipmentOrder.</td></Output>";
		}
		
		if ((sReturnCode.equalsIgnoreCase("0")) || (sReturnCode.equalsIgnoreCase("2")))
		{
			Status = "Success";
		}
		else
			Status = "Failure";
		
		String inputXml=xmlInput;
		String dburl = (String)currentCabPropertyMap.get("DBURL");
		String dbuser = (String)currentCabPropertyMap.get("USER");
		String dbpass = (String)currentCabPropertyMap.get("PASS");
		String winame = xmlDataParser.getValueOf("WiName");
		String sessionID = xmlDataParser.getValueOf("SessionId");
		String call_type = xmlDataParser.getValueOf("DSCOPCallType");
		sCabinet = xmlDataParser.getValueOf("EngineName");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		LogGEN.writeTrace("CBG_Log", "11111111111111111111%%%%%%%%%%%%");
		try
		{
			dbpass = AESEncryption.decrypt(dbpass);
		}
		catch (Exception e)
		{
			e.getMessage();
		}
		DBConnection con=new DBConnection();
		String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  Status + "')";
		LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
		con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrgRes.replaceAll("'", "''"));
		return sOutput;
	}

	private String loadWSDLDtls(DSCOPServiceHandler sHandler) {
		try {
			//sHandler.readCabProperty("FETCHER_SHIPMENT_ORDER");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("FETCHER_SHIPMENT_ORDER");
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}


	private HeaderType setHeaderDtls(String sDate,String ref_no,String senderId){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		HeaderType headerType= new HeaderType();		
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModFetcherShipmentOrder");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setSysRefNumber(ref_no);		
		headerType.setSenderID(senderId); 
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);
		headerType.setUsername("");
		headerType.setCredentials("");
		headerType.setCorrelationID("1212");
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");

		return headerType;
	}
}
