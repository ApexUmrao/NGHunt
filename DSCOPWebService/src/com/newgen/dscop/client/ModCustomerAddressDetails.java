package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCustomerDtlStub;
import com.newgen.dscop.stub.ModCustomerDtlStub.HeaderType;
import com.newgen.dscop.stub.ModCustomerDtlStub.ModCustomerDtlReqMsg;
import com.newgen.dscop.stub.ModCustomerDtlStub.ModCustomerDtlReq_type0;
import com.newgen.dscop.stub.ModCustomerDtlStub.ModCustomerDtlResMsg;
import com.newgen.dscop.stub.ModCustomerDtlStub.ModCustomerDtlRes_type0;	
import com.newgen.dscop.stub.ModCustomerDtlStub.ResidentialAddress_type0;

public class ModCustomerAddressDetails extends DSCOPServiceHandler
{ 
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String modCustomerAddressDetailsCall(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called ModCustomerAddressDetails");
		LogGEN.writeTrace("CBG_Log", "ModCustomerAddressDetails sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ModCustomerDtl");
			LogGEN.writeTrace("CBG_Log", "ModCustomerAddressDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ModCustomerDtl WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ModCustomerDtl CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ModCustomerDtl USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ModCustomerDtl PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ModCustomerDtl LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ModCustomerDtl TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			String customerID=xmlDataParser.getValueOf("customerID");
			String residenceBuildingName = xmlDataParser.getValueOf("residenceBuildingName");
			String residenceStreetName = xmlDataParser.getValueOf("residenceStreetName");
			String residenceFlatNo=xmlDataParser.getValueOf("residenceFlatNo");
			String residenceArea = xmlDataParser.getValueOf("residenceArea");
			String residenceCity = xmlDataParser.getValueOf("residenceCity");
			String residenceState = xmlDataParser.getValueOf("residenceState");
			String residenceCountry = xmlDataParser.getValueOf("residenceCountry");
			String residencePOBox = xmlDataParser.getValueOf("residencePOBox");
			String residencePhone = xmlDataParser.getValueOf("residencePhone");

			ModCustomerDtlStub request_stub=new ModCustomerDtlStub(sWSDLPath);
			ModCustomerDtlReqMsg service_req = new ModCustomerDtlReqMsg();			
			ModCustomerDtlReq_type0 service_req_type0 = new ModCustomerDtlReq_type0();
			ModCustomerDtlResMsg service_response = new ModCustomerDtlResMsg();
			ModCustomerDtlRes_type0 service_response_type0 = new ModCustomerDtlRes_type0();
			ResidentialAddress_type0 residential_req_type0 = new ResidentialAddress_type0();


			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("ModCustomerDtl");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);

			service_req_type0.setCustomerID(customerID);
			residential_req_type0.setResidenceBuildingName(residenceBuildingName);
			residential_req_type0.setResidenceStreetName(residenceStreetName);
			residential_req_type0.setResidenceFlatNo(residenceFlatNo);
			residential_req_type0.setResidenceArea(residenceArea);
			residential_req_type0.setResidenceCity(residenceCity);
			residential_req_type0.setResidenceState(residenceState);
			residential_req_type0.setResidencePhone(residencePhone);
			residential_req_type0.setResidenceCountry(residenceCountry);
			residential_req_type0.setResidencePOBox(residencePOBox);

			service_req_type0.setResidentialAddress(residential_req_type0);

			service_req.setModCustomerDtlReq(service_req_type0);
			service_req.setHeader(Header_Input);
			service_response = request_stub.modCustomerDtl_Oper(service_req);

			xmlInput= request_stub.getInputXML(service_req);
			Header_Input = service_response.getHeader();
			service_response_type0 = service_response.getModCustomerDtlRes();
			LogGEN.writeTrace("CBG_Log", "ModCustomerAddressDetails xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "ModCustomerAddressDetails sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			customerID = service_response_type0.getCustomerID();
			String reason= service_response_type0.getReason();
			String status= service_response_type0.getStatus();

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>ModCustomerAddressDetails</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<ModCustomerDtlRes>"+
						"<customerID>"+customerID+"</customerID>" +
						"<status>"+status+"</status>"+
						"<reason>"+reason+"</reason>" +
						"</ModCustomerDtlRes>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustomerAddressDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to modify Customer Address Details</td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustomerAddressDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to modify Customer Address Details</Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ModCustomerAddressDetails</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to modify Customer Address Details</Output>";
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
			LogGEN.writeTrace("CBG_Log","ModCustomerAddressDetails  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","ModCustomerAddressDetails  Exception: finally :"+e2.getStackTrace());
			}
		}
		return sOutput;			
	}
}

