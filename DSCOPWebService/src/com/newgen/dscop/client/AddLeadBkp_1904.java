package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddLeadResponseStub;
import com.newgen.dscop.stub.AddLeadResponseStub.AddLeadResponse2ReqMsg;
import com.newgen.dscop.stub.AddLeadResponseStub.AddLeadResponse2Req_type0;
import com.newgen.dscop.stub.AddLeadResponseStub.AddLeadResponse2ResMsg;
import com.newgen.dscop.stub.AddLeadResponseStub.AddLeadResponse2Res_type0;
import com.newgen.dscop.stub.AddLeadResponseStub.AttributeNameValPair_type0;
import com.newgen.dscop.stub.AddLeadResponseStub.AttributeNameValPair_type1;
import com.newgen.dscop.stub.AddLeadResponseStub.EntityRecord_type1;
import com.newgen.dscop.stub.AddLeadResponseStub.HeaderType;
import com.newgen.dscop.stub.AddLeadResponseStub.LeadServiceModule_type1;
import com.newgen.dscop.stub.AddLeadResponseStub.LeadServiceModule_type2;

public class AddLeadBkp_1904 extends DSCOPServiceHandler  {
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	String xmlInput="";
	String Lead_Creation_ouput=null;

	public String addLeadResponse(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called AddLead");
		LogGEN.writeTrace("CBG_Log", "AddLead sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddLeadResponse");
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddLeadResponse TIME_OUT: "+lTimeOut);

			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);					
			String ref_no=xmlDataParser.getValueOf("REF_NO");
			sCabinet=xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");
			String entityName = xmlDataParser.getValueOf("entityName");

			AddLeadResponseStub addLeadStub = new AddLeadResponseStub(sWSDLPath);

			AddLeadResponse2ResMsg res_msg = new AddLeadResponse2ResMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddLeadResponse");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Addition");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			int noOfFields = xmlDataParser.getNoOfFields("attributeNameValPair");
			XMLParser innerParser = new XMLParser();
			AttributeNameValPair_type1[] attrNameValPair = new AttributeNameValPair_type1[noOfFields];
			for (int noofTag = 0; noofTag < noOfFields; ++noofTag) {
				if(noofTag==0)
					innerParser.setInputXML(xmlDataParser.getFirstValueOf("attributeNameValPair"));
				else
					innerParser.setInputXML(xmlDataParser.getNextValueOf("attributeNameValPair"));
				
				AddLeadResponseStub.AttributeNameValPair_type1 nvPair = new AddLeadResponseStub.AttributeNameValPair_type1();
				nvPair.setAttributeName(innerParser.getValueOf("attributeName"));
				nvPair.setAttributeValue(innerParser.getValueOf("attributeValue"));
				attrNameValPair[noofTag]=nvPair;
			}

			EntityRecord_type1[] entrityRecord = new EntityRecord_type1[1];
			EntityRecord_type1 eRecord = new EntityRecord_type1();
			eRecord.setAttributeNameValPair(attrNameValPair);
			entrityRecord[0]=eRecord;

			LeadServiceModule_type1[] leadServiceModule = new LeadServiceModule_type1[1];
			LeadServiceModule_type1 lsModule1 = new LeadServiceModule_type1();
			lsModule1.setEntityName(entityName);
			lsModule1.setEntityRecord(entrityRecord);
			leadServiceModule[0]=lsModule1;
		
			LeadServiceModule_type2[] req= new LeadServiceModule_type2[1];
			LeadServiceModule_type2 lsModule2 = new LeadServiceModule_type2();
			lsModule2.setLeadServiceModule(leadServiceModule);
			req[0]=lsModule2;

			AddLeadResponse2Req_type0 req_msg = new AddLeadResponse2Req_type0();
			req_msg.setLeadServiceModule(req);

			AddLeadResponse2ReqMsg addLeadReq = new AddLeadResponse2ReqMsg();
			addLeadReq.setHeader(Header_Input);
			addLeadReq.setAddLeadResponse2Req(req_msg);

			LogGEN.writeTrace("CBG_Log", "All values set");

			xmlInput=addLeadStub.getInputXml(addLeadReq);
			LogGEN.writeTrace("CBG_Log", "All values set xml " + xmlInput);
			addLeadStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg=addLeadStub.addLeadResponse2_Oper(addLeadReq);
			Lead_Creation_ouput = addLeadStub.outputXML;
			LogGEN.writeTrace("CBG_Log", "AddLead_output : " + Lead_Creation_ouput);
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "Error Description---"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "Successful Result");
				AddLeadResponse2Res_type0 res=new AddLeadResponse2Res_type0();
				res=res_msg.getAddLeadResponse2Res();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>LEAD_CREATION</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<returnCode>"+sReturnCode+"</returnCode><errorDetail>"+sErrorDetail+"</errorDetail>";	
				AttributeNameValPair_type0[] attrRes = res.getLeadServiceModule()[0].getEntityRecord()[0].getAttributeNameValPair();
				for (AttributeNameValPair_type0 attributeNameValPair_type0 : attrRes) {
					LogGEN.writeTrace("CBG_Log", "Lead_Creation AttributeName--- "+attributeNameValPair_type0.getAttributeName());
					if("LEADSTATUS".equalsIgnoreCase(attributeNameValPair_type0.getAttributeName())){
						sOutput+="<LeadStatus>"+attributeNameValPair_type0.getAttributeValue()+"</LeadStatus>";
					}else if("LEADREFNUM".equalsIgnoreCase(attributeNameValPair_type0.getAttributeName())){
						sOutput+="<LeadRefNum>"+attributeNameValPair_type0.getAttributeValue()+"</LeadRefNum>";
					}
				}
				sOutput+="</Output>";
				LogGEN.writeTrace("CBG_Log", "Lead_Creation Output XML--- "+sOutput);
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>AddLeadResponse</Option>"
						+ "<returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription>"
						+ "<Status>-1</Status><Reason>Failure</Reason><td>LEAD not created.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LEAD_CREATION</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>LEAD not created.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>LEAD_CREATION</Option><returnCode>-1</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>LEAD not created.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");

			String inputXml = xmlInput;
			String winame = xmlDataParser.getValueOf("WiName");
			String sessionID = xmlDataParser.getValueOf("SessionId");
			String call_type = xmlDataParser.getValueOf("DSCOPCallType");
			sCabinet = xmlDataParser.getValueOf("EngineName");

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
			LogGEN.writeTrace("CBG_Log"," Inquiry business rules Query : finally :"+Query);
			LogGEN.writeTrace("CBG_Log","Lead_Creation_ouput : finally :"+Lead_Creation_ouput);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),Lead_Creation_ouput.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			//			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
		}
		return sOutput;	
	}
}
