package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.axis2.databinding.utils.ConverterUtil;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ADCBServiceStub;
import com.newgen.dscop.stub.ADCBServiceStub.ADCBService_Input;
import com.newgen.dscop.stub.ADCBServiceStub.ADCBService_Output;
import com.newgen.dscop.stub.ADCBServiceStub.AttributeNameValPair;
import com.newgen.dscop.stub.ADCBServiceStub.EntityRecord;
import com.newgen.dscop.stub.ADCBServiceStub.Header;
import com.newgen.dscop.stub.ADCBServiceStub.ListOfADCBServicesQUReq;
import com.newgen.dscop.stub.ADCBServiceStub.Service;
import com.newgen.dscop.stub.ADCBServiceStub.ServiceBody;
import com.newgen.dscop.stub.ADCBServiceStub.ServiceModule;
import com.newgen.dscop.stub.AddServiceRequestStub;
import com.newgen.dscop.stub.InqCustPersonalDtl2Stub;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2ReqMsg;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2Req_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2ResMsg;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2Res_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.AttributeNameValPair_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.AttributeNameValPair_type1;
import com.newgen.dscop.stub.AddServiceRequestStub.EntityRecord_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.EntityRecord_type1;
import com.newgen.dscop.stub.AddServiceRequestStub.ServiceModuleDtls_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.ServiceModuleDtls_type1;
import com.newgen.dscop.stub.InqCustPersonalDtl2Stub.HeaderType;
import com.newgen.dscop.stub.InqCustPersonalDtl2Stub.InqCustPersonalDtl2ReqMsg;
import com.newgen.dscop.stub.InqCustPersonalDtl2Stub.InqCustPersonalDtl2Req_type0;
import com.newgen.dscop.stub.InqCustPersonalDtl2Stub.InqCustPersonalDtl2ResMsg;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.DocumentDetails_type1;
import com.newgen.dscop.stub.ModLoanDisbursementStub.UaeDds_type0;

public class AddSRServiceRequestDscop  extends DSCOPServiceHandler{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";

	public String fetchAddSRServiceRequestDscop(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called AddServiceRequest2");
		LogGEN.writeTrace("CBG_Log", "AddServiceRequest2 sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String sOrg_Output="";
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("fetchAddSRServiceRequestDscop");
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath = (String)wsConfig.get(0);
			sCabinet = (String)wsConfig.get(1);
			sUser = (String)wsConfig.get(2);
			sLoginReq = Boolean.valueOf((String)wsConfig.get(3));
			sPassword = (String)wsConfig.get(4);
			lTimeOut = Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);

			String ref_no = xmlDataParser.getValueOf("REF_NO");
			String module = xmlDataParser.getValueOf("module");
			String unit = xmlDataParser.getValueOf("unit");
			String typeIdentifierNo = xmlDataParser.getValueOf("typeIdentifierNo");
			String serialNo = xmlDataParser.getValueOf("serialNo");
			String srStatus = xmlDataParser.getValueOf("srStatus");
			String moduleEvent = xmlDataParser.getValueOf("moduleEvent");
			String sourceChannel = xmlDataParser.getValueOf("sourceChannel");
			String modeOfOperation = xmlDataParser.getValueOf("modeOfOperation");
			String notification = xmlDataParser.getValueOf("notification");
			String assignmentType = xmlDataParser.getValueOf("assignmentType");
			String submissionReq = xmlDataParser.getValueOf("submissionReq");
			LogGEN.writeTrace("CBG_Log", "sCustomerID---"+ref_no);	
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate+" sWSDLPath "+sWSDLPath);

			ADCBServiceStub stub = null;
			try
			{
				stub = new ADCBServiceStub(sWSDLPath);
			}
			catch(Exception e)
			{
				LogGEN.writeTrace("CBG_Log", "InqCustPersonalDtl2 Error---"+e.getMessage());
			}
			ADCBService_Input  reqMsg = new  ADCBService_Input();
			ListOfADCBServicesQUReq  reqType0 = new ListOfADCBServicesQUReq();
			Service service = new Service();
			ServiceBody serviceBody = new  ServiceBody();
			ServiceModule serviceModule =new ServiceModule();
			
			
			service.setHeader(Header(sDate, ref_no));
			serviceBody.setMODULE(submissionReq);
			serviceBody.setUNIT(submissionReq);
			serviceBody.setTYPE_IDENTIFIER_NO(submissionReq);
			serviceBody.setASSIGNMENT_TYPE(submissionReq);
			serviceBody.setSUBMISSION_REQ(submissionReq);
			serviceBody.setMODULE_EVENT(submissionReq);
			serviceBody.setSOURCE_CHANNEL(submissionReq);
			serviceBody.setMODE_OF_OPERATION(submissionReq);
			
			serviceModule.setEntityName(submissionReq);
		//	serviceModule.setEntityRecord();
			EntityRecord entityRecord = new EntityRecord();
		
			AttributeNameValPair[] attributeNameValPair=  new AttributeNameValPair[]{};
			
			StringBuilder details = new StringBuilder(); 
/*			if(valpair != null){
				for(AttributeNameValPair detailsType0 : valpair) {
					detailsType0.setAttributeName(submissionReq);
					
				}
			}*/
			
			serviceModule.getEntityRecord();
			service.setServiceBody(serviceBody);
			reqType0.setService(service);
			reqMsg.setListOfADCBServicesQUReq(reqType0);
			
			LogGEN.writeTrace("CBG_Log", "InqCustPersonalDtl2 values set");
			stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput = stub.reqMsg;
			sOrg_Output = stub.resMsg;
			sOrg_Output = sOrg_Output.replaceAll("ns0:", "").replaceAll("ns1:", "");
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop Request: "+xmlInput);
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop Response: "+sOrg_Output);

			com.newgen.omni.jts.cmgr.XMLParser parser = new com.newgen.omni.jts.cmgr.XMLParser(sOrg_Output);
			sReturnCode = parser.getValueOf("returnCode");
			sOutput = sOrg_Output;
		} catch (Throwable e)
		{			
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "fetchAddSRServiceRequestDscop Error Trace in Web Service :"+e);
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>fetchInqCustPersonalDtl2</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","fetchAddSRServiceRequestDscop outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>fetchInqCustPersonalDtl2</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to fetch data from flex. Either CID is invalid or Integration server is down.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2")) {
				Status="Success";
			}
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

				String winame=xmlDataParser.getValueOf("WiName");
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
					LogGEN.writeTrace("CBG_Log", "Error inserting---"+e.getMessage());
				}
				DBConnection con=new DBConnection();

				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type1+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log","CustomerPersonalDetailsDA Query : finally :"+Query);
				try {
					con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,xmlInput.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				}catch (Exception e2) {
				}
				LogGEN.writeTrace("CBG_Log","CustomerPersonalDetailsDA outputXML : finally :"+sOutput);

			}
		return sOutput;		
	}
	private Header Header(String sDate,String ref_no){
		LogGEN.writeTrace("CBG_Log", "inside setHeaderDtls function ");
		Header headerType= new Header();		
		headerType.setUSERNAME("");
		headerType.setSERVICENAME("SR Service");
		headerType.setREPTIMESTAMP(sDate);
		headerType.setSYSREFNUMBER(ref_no);
		headerType.setREQTIMESTAMP(sDate);		
		headerType.setERRORMESSAGE("");
		LogGEN.writeTrace("CBG_Log", "setHeaderDtls complete ");
		return headerType;
	}
}
