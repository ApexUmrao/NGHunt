package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchLeadDetailsReq_type0;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchLeadDetailsReqMsg;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchLeadDetailsRes_type0;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.FetchLeadDetailsResMsg;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.LeadServiceModule_type0;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.LeadServiceModule_type1;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.EntityRecord_type0;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.EntityRecord_type1;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.AttributeNameValPair_type0;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.AttributeNameValPair_type1;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.Header;
import com.newgen.dscop.stub.InqCustomerLeadDetailsStub.HeaderType;

public class LeadInquiry extends DSCOPServiceHandler
{
	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;

	String xmlInput="";
	@SuppressWarnings("finally")
	public String leadCustomerEnquiry(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called leadCustomerEnquiry");
		LogGEN.writeTrace("CBG_Log", "leadCustomerEnquiry sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
	
	


		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();
		try
		{

			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("LeadInquiry");
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "InqCustomerLeadDetails TIME_OUT: "+lTimeOut);

			String entityName=xmlDataParser.getValueOf("entityName");
			String leadName=xmlDataParser.getValueOf("leadName");
			String leadRefNo=xmlDataParser.getValueOf("leadRefNo");
			String ref_no=xmlDataParser.getValueOf("REF_NO");


			LogGEN.writeTrace("CBG_Log", "leadName : "+leadName);
			LogGEN.writeTrace("CBG_Log", "leadRefNo : "+leadRefNo);
			LogGEN.writeTrace("CBG_Log", "ref_no : "+ref_no);
			LogGEN.writeTrace("CBG_Log", "entityName : "+entityName);
			InqCustomerLeadDetailsStub inq_lead_stub =new InqCustomerLeadDetailsStub(sWSDLPath);
			
		
			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqCustomerLeadDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setConsumer("");
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(sHandler.setSenderId(xmlDataParser.getValueOf("USERNAME")));
			Header_Input.setUsername(sHandler.setSenderId(xmlDataParser.getValueOf("USERNAME")));
			LogGEN.writeTrace("CBG_Log", "All values set");
			
			FetchLeadDetailsReqMsg  ReqMsg = new FetchLeadDetailsReqMsg();
			FetchLeadDetailsReq_type0 Req_type0 = new FetchLeadDetailsReq_type0();
			
			LeadServiceModule_type0[] Module_type0;
			EntityRecord_type0[] Record_type0 ;
			AttributeNameValPair_type0[] ValPair_type0;
			
			Module_type0 = new LeadServiceModule_type0[1];
			Record_type0 = new EntityRecord_type0[1];
			ValPair_type0 = new AttributeNameValPair_type0[1];
			
			ValPair_type0[0] = new AttributeNameValPair_type0();
			Record_type0[0] = new EntityRecord_type0();
			Module_type0[0] = new LeadServiceModule_type0();
			
			if(leadName != null || leadName != "" || !leadName.isEmpty())
			{
				ValPair_type0[0].setAttributeName(leadName);
			}
			if(leadRefNo != null || leadRefNo != "" || !leadRefNo.isEmpty())
			{
				ValPair_type0[0].setAttributeValue(leadRefNo);
			}
			
			Record_type0[0].setAttributeNameValPair(ValPair_type0);
			Module_type0[0].setEntityRecord(Record_type0);
			Module_type0[0].setEntityName(entityName);
			Req_type0.setLeadServiceModule(Module_type0);
			ReqMsg.setFetchLeadDetailsReq(Req_type0);
			ReqMsg.setHeader(Header_Input);
			
			inq_lead_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=inq_lead_stub.getinputXML(ReqMsg);
			LogGEN.writeTrace("CBG_Log", "inupt xml"+xmlInput);
			FetchLeadDetailsResMsg res_msg= inq_lead_stub.fetchLeadDetails_Oper(ReqMsg);
			sOrg_Output = inq_lead_stub.responseInqlead;
			LogGEN.writeTrace("CBG_Log", "output xml xml"+sOrg_Output);
			HeaderType Header_output = new HeaderType();
			Header_output = res_msg.getHeader();
			
			sReturnCode=  Header_output.getReturnCode();
			sErrorDetail = Header_output.getErrorDetail();
			sErrorDesc = Header_output.getErrorDescription();
			System.out.println(sErrorDetail);
			
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{
				FetchLeadDetailsRes_type0 Res_type0 = new FetchLeadDetailsRes_type0();
				Res_type0 = res_msg.getFetchLeadDetailsRes();
				
				LeadServiceModule_type1[] Module_type1;
				EntityRecord_type1[] Record_type1 ;
				AttributeNameValPair_type1[] ValPair_type1;
				
				
				
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>LeadInquiry</Option>" +
						"<header>"+
					    "<usecaseID>"+Header_output.getUsecaseID()+"</usecaseID>"+
					    "<serviceName>"+Header_output.getServiceName()+"</serviceName>"+
					    "<versionNo>"+Header_output.getVersionNo()+"</versionNo>"+
					    "<serviceAction>"+Header_output.getServiceAction()+"</serviceAction>"+
					    "<correlationID>"+Header_output.getCorrelationID()+"</correlationID>"+
					    "<sysRefNumber>"+Header_output.getSysRefNumber()+"</sysRefNumber>"+
					    "<consumer>"+Header_output.getConsumer()+"</consumer>"+
					    "<reqTimeStamp>"+Header_output.getRepTimeStamp()+"</reqTimeStamp>"+
					    "<username>"+Header_output.getUsername()+"</username>"+
					    "<credentials>"+Header_output.getCredentials()+"</credentials>"+
					    "<returnCode>"+Header_output.getReturnCode()+"</returnCode>"+
					    "<errorDescription>"+Header_output.getErrorDescription()+"</errorDescription>"+
					    "<errorDetail>"+Header_output.getErrorDetail()+"</errorDetail>"+
					    "</header>";
				
				if (Res_type0 != null)
				{
					Module_type1 = Res_type0.getLeadServiceModule();
					sOutput+="<fetchLeadDetailsRes>";
					for (int i =0 ; i<Module_type1.length;i++)
					{
						sOutput+="<leadServiceModule><entityName>"+Module_type1[i].getEntityName()+"</entityName>";
						Record_type1 = Module_type1[i].getEntityRecord();
						for (int j =0 ; j <Record_type1.length; j++ )
						{
							sOutput+="<entityRecord>";
							ValPair_type1 = Record_type1[j].getAttributeNameValPair();
							for (int k =0 ;k<ValPair_type1.length ; k++)
							{
								sOutput+= "<attributeNameValPair>"+
								"<attributeName>"+ValPair_type1[k].getAttributeName()+"</attributeName>"+
								"<attributeValue>"+ValPair_type1[k].getAttributeValue()+"</attributeValue>"+
								"</attributeNameValPair>";
							}
							sOutput+="</entityRecord>";
						}
						sOutput+="</leadServiceModule>";
					}
					
					sOutput+="</fetchLeadDetailsRes></Output>";
					LogGEN.writeTrace("CBG_Log", "output xml :"+sOutput);
					System.out.println(sOutput);
			
				}else
				{
					sOutput+="<fetchLeadDetailsRes><leadServiceModule>Inquiry call Return Empty Result</leadServiceModule></fetchLeadDetailsRes></Output>";
				}				
			}
			else
			{
				try
				{

					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>LeadInquiry</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform LeadInquiry service</Status></Output>";
				}
				catch(Exception e)
				{
					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>LeadInquiry</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Reason></Reason><Status>Unable to perform LeadInquiry service</Status></Output>";

				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>LeadInquiry</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform LeadInquiry service</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>LeadInquiry</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><Status>Unable to perform LeadInquiry service</Status></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
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


			String inputXml=xmlInput;
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WI_NAME");
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

			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log"," perform Delinquent service finally :"+Query);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}


