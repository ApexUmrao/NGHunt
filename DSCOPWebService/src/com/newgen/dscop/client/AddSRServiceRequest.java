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
import com.newgen.dscop.stub.AddServiceRequestStub;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2ReqMsg;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2Req_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2ResMsg;
import com.newgen.dscop.stub.AddServiceRequestStub.AddServiceRequest2Res_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.AttributeNameValPair_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.AttributeNameValPair_type1;
import com.newgen.dscop.stub.AddServiceRequestStub.EntityRecord_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.EntityRecord_type1;
import com.newgen.dscop.stub.AddServiceRequestStub.HeaderType;
import com.newgen.dscop.stub.AddServiceRequestStub.ServiceModuleDtls_type0;
import com.newgen.dscop.stub.AddServiceRequestStub.ServiceModuleDtls_type1;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.ApplicationAttributes_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.AttributeDetails_type0;
import com.newgen.dscop.stub.ModCBGCustomerOnboardingStub.Attributes_type0;

public class AddSRServiceRequest extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String sOrg_put="";

	public String callAddSRServiceRequest(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Function called AddServiceRequest2");
		LogGEN.writeTrace("CBG_Log", "AddServiceRequest2 sInputXML---");
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
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddServiceRequest");
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest TIME_OUT: "+lTimeOut);

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
			String senderID = xmlDataParser.getValueOf("senderID"); 

			AddServiceRequestStub request_stub=new AddServiceRequestStub(sWSDLPath);
			AddServiceRequest2ReqMsg req_msg = new AddServiceRequest2ReqMsg();
			AddServiceRequest2Req_type0 reqMsg_type0 = new AddServiceRequest2Req_type0();
			AddServiceRequest2ResMsg response_msg = new AddServiceRequest2ResMsg();
			AddServiceRequest2Res_type0 response_type0  = new AddServiceRequest2Res_type0();
			
			ServiceModuleDtls_type0 ServiceModuleDtls_type0 = new  ServiceModuleDtls_type0();
			//String entityName = ServiceModuleDtls_type0.getEntityName();
			String entityName = xmlDataParser.getValueOf("entityName");
			LogGEN.writeTrace("CBG_Log", "entityName "+entityName);

		
			int start = xmlDataParser.getStartIndex("entityRecord", 0, 0);
			int deadEnd = xmlDataParser.getEndIndex("entityRecord", start, 0);
			int noOfFieldss = xmlDataParser.getNoOfFields("attributeNameValPair", start,deadEnd);
			int end = 0;
			LogGEN.writeTrace("CBG_Log", "noOfFieldss "+noOfFieldss);

			EntityRecord_type0[] entityRecord = new EntityRecord_type0[1];
			AttributeNameValPair_type0[] attrNameValPair = new AttributeNameValPair_type0[noOfFieldss];
			
			for (int i = 0; i < noOfFieldss; i++) {
				start = xmlDataParser.getStartIndex("attributeNameValPair", end, 0);
				end = xmlDataParser.getEndIndex("attributeNameValPair", start, 0);
				String attributeName = xmlDataParser.getValueOf("attributeName", start, end);
				String attributeValue = xmlDataParser.getValueOf("attributeValue", start, end);
				
				AttributeNameValPair_type0 ad = new AttributeNameValPair_type0();
				
				ad.setAttributeName(attributeName);
				ad.setAttributeValue(attributeValue);
				attrNameValPair[i] = ad; 	
				LogGEN.writeTrace("CBG_Log", "attributeName "+attributeName);
				LogGEN.writeTrace("CBG_Log", "attributeValue "+attributeValue);


			}
			EntityRecord_type0 aa = new EntityRecord_type0();
			aa.setAttributeNameValPair(attrNameValPair);
			entityRecord[0] = aa;
			
			
			
			ServiceModuleDtls_type0.setEntityName(entityName);
			if(entityName.equalsIgnoreCase("") || entityName == null){
				LogGEN.writeTrace("CBG_Log", "inside entity is null");

				entityName = "SRDetail";
				ServiceModuleDtls_type0.setEntityName(entityName);

			}
			ServiceModuleDtls_type0.setEntityRecord(entityRecord);
			ServiceModuleDtls_type0[] qw = new ServiceModuleDtls_type0[1];
			qw[0] = ServiceModuleDtls_type0;
			reqMsg_type0.setServiceModuleDtls(qw);
			HeaderType Header_Input = new HeaderType();
			Header_Input.setServiceName("AddServiceRequest");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setCorrelationID("");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderID);
			Header_Input.setConsumer("");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setRepTimeStamp("");
			Header_Input.setUsername("1234");
			Header_Input.setCredentials("");
			Header_Input.setReturnCode("");
			Header_Input.setErrorDescription("");
			Header_Input.setErrorDetail("");
			
			reqMsg_type0.setModule(module);
			reqMsg_type0.setUnit(unit);
			reqMsg_type0.setTypeIdentifierNo(typeIdentifierNo);
			reqMsg_type0.setSerialNo(serialNo);
			reqMsg_type0.setSrStatus(srStatus);
			reqMsg_type0.setModuleEvent(moduleEvent);
			reqMsg_type0.setSourceChannel(sourceChannel);
			reqMsg_type0.setModeOfOperation(modeOfOperation);
			reqMsg_type0.setNotification(notification);
			reqMsg_type0.setAssignmentType(assignmentType);
			reqMsg_type0.setSubmissionReq(submissionReq);
			req_msg.setAddServiceRequest2Req(reqMsg_type0);
			req_msg.setHeader(Header_Input);

			response_msg = request_stub.addServiceRequest2_Oper(req_msg);
			xmlInput= request_stub.getAddService_2(req_msg);
			Header_Input=response_msg.getHeader();
			response_type0 = response_msg.getAddServiceRequest2Res();
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest2 xmlInput xml : "+xmlInput);
			request_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			sOrg_put=request_stub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddServiceRequest2ResMsg sOrg_put: "+sOrg_put);

			sReturnCode =  Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();
			ServiceModuleDtls_type1[] ServiceModuleDtls =response_type0.getServiceModuleDtls();
			StringBuilder details = new StringBuilder(); 
			if(ServiceModuleDtls != null){
				for(ServiceModuleDtls_type1 detailsType0 : ServiceModuleDtls) {
					String entityname = detailsType0.getEntityName();
					EntityRecord_type1[]  EntityRecord= detailsType0.getEntityRecord();
					if(EntityRecord != null){
						for(EntityRecord_type1 EntityRecorddetails : EntityRecord) {
							AttributeNameValPair_type1[]  AttributeNameValPair_type1= EntityRecorddetails.getAttributeNameValPair();
							for(AttributeNameValPair_type1 AttributeNameValPairdetails : AttributeNameValPair_type1) {
								String attributeName = AttributeNameValPairdetails.getAttributeName();
								String attributeValue = AttributeNameValPairdetails.getAttributeValue();
								details.append("<serviceModuleDtls>").append("\n")
								.append("<entityName>"+entityname+"</entityName>").append("\n")
								.append("<entityRecord>").append("\n")
								.append("<attributeNameValPair>").append("\n")
								.append("<attributeName>"+attributeName+"</documentType>").append("\n")
								.append("<externalDocRefNo>"+attributeValue+"</externalDocRefNo>").append("\n")
								.append("</entityRecord>").append("\n")
								.append("</attributeNameValPair>").append("\n")
								.append("<serviceModuleDtls>").append("\n");
							}
						}
						
				}
					
			}
			}
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddServiceRequest</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<AddServiceRequest2Res>"+
						"<module>"+module+"</module>"+
						"<unit>"+unit+"</unit>"+
						"<typeIdentifierNo>"+typeIdentifierNo+"</typeIdentifierNo>"+
						"<serialNo>"+serialNo+"</serialNo>"+
						"<srStatus>"+srStatus+"</srStatus>"+
						"<moduleEvent>"+moduleEvent+"</moduleEvent>"+
						"<sourceChannel>"+sourceChannel+"</sourceChannel>"+
						"<modeOfOperation>"+modeOfOperation+"</modeOfOperation>"+
						"<notification>"+notification+"</notification>"+
						"<assignmentType>"+assignmentType+"</assignmentType>"+
						"<submissionReq>"+submissionReq+"</submissionReq>"+
						"<submissionReq>"+submissionReq+"</submissionReq>"+
						details+
						"</AddServiceRequest2Res>"+	
						"</Output>";
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddServiceRequest2</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><Status>ERROR</Status><td>Unable to Add service Request </td></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDesc=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddServiceRequest2</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Add service Request </Output>";
			e.printStackTrace();
		}

		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddServiceRequest2</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription>Unable to Add service Request </Output>";
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

			} catch (Exception e) {

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
			LogGEN.writeTrace("CBG_Log","AddServiceRequest2  Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","sOrg_put : finally :"+sOrg_put);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_put.replaceAll("'", "''"));
			} catch (Exception e2) {
				LogGEN.writeTrace("CBG_Log","AddServiceRequest2  Exception: finally :"+e2.getStackTrace());
			}

		}
		return sOutput;			
	}
}