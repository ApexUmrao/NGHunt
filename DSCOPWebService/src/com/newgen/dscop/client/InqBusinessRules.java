package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.InqBusinessRulesStub;
import com.newgen.dscop.stub.InqBusinessRulesStub.HeaderType;
import com.newgen.dscop.stub.InqBusinessRulesStub.InqBusinessRulesReqMsg;
import com.newgen.dscop.stub.InqBusinessRulesStub.InqBusinessRulesReq_type0;
import com.newgen.dscop.stub.InqBusinessRulesStub.InqBusinessRulesResMsg;
import com.newgen.dscop.stub.InqBusinessRulesStub.InqBusinessRulesRes_type0;

public class InqBusinessRules extends DSCOPServiceHandler {
	
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String InqBusiRules_ouput = "";

	@SuppressWarnings("finally")
	public String customerEligibility(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called inq_business_rules");
		LogGEN.writeTrace("CBG_Log", "InqBusinessRules sInputXML---");
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sOutput = "";
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			//sHandler.readCabProperty("inq_business_rules");				
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("inq_business_rules");
			LogGEN.writeTrace("CBG_Log", "inq_business_rules WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "inq_business_rules WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules TIME_OUT: "+lTimeOut);
			LogGEN.writeTrace("CBG_Log", "sDate---"+sDate);
			String ref_no = xmlDataParser.getValueOf("REF_NO");
			sCabinet = xmlDataParser.getValueOf("EngineName");
			String senderId = xmlDataParser.getValueOf("SenderId");


			InqBusinessRulesStub inq_business_rules = new InqBusinessRulesStub(sWSDLPath);
			InqBusinessRulesReqMsg inq_business_rules_Req_Msg = new InqBusinessRulesReqMsg();
			InqBusinessRulesResMsg res_msg = new InqBusinessRulesResMsg();
			InqBusinessRulesReq_type0 req = new InqBusinessRulesReq_type0();		

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("InqBusinessRules");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderId);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setCredentials(loggedinuser);			  

			req.setRequestChannelName(xmlDataParser.getValueOf("requestChannelName"));
			req.setRuleFlowGroup(xmlDataParser.getValueOf("ruleFlowGroup"));
			String temp = xmlDataParser.getValueOf("Eligibility");
			req.setRuleInputData(temp);
			inq_business_rules_Req_Msg.setHeader(Header_Input);
			inq_business_rules_Req_Msg.setInqBusinessRulesReq(req);
			xmlInput = inq_business_rules.getInputXml(inq_business_rules_Req_Msg);
			LogGEN.writeTrace("CBG_Log", "InqBusinessRules InputXML: " + xmlInput);
			inq_business_rules._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			res_msg = inq_business_rules.inqBusinessRules_Oper(inq_business_rules_Req_Msg);
			InqBusiRules_ouput = inq_business_rules.businessRuleMsg;
			Header_Input = res_msg.getHeader();
			sReturnCode = Header_Input.getReturnCode();
			sErrorDetail = Header_Input.getErrorDetail();
			sErrorDesc = Header_Input.getErrorDescription();

			LogGEN.writeTrace("CBG_Log", "inq_business_rules Return Code---"+sReturnCode);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules Error Detail---"+sErrorDetail);
			LogGEN.writeTrace("CBG_Log", "inq_business_rules Error Description---"+sErrorDesc);

			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{			    
				LogGEN.writeTrace("CBG_Log", "Successful Result");
				InqBusinessRulesRes_type0 res=new InqBusinessRulesRes_type0();
				res=res_msg.getInqBusinessRulesRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>INQUIRE_BRMS_RULES_RESPONSE</Option>" +
						"<returnCode>"+ sReturnCode +"</returnCode>" +
						"<errorDescription>"+ sErrorDetail +"</errorDescription>" +
						"<returnCode>"+ sReturnCode +"</returnCode><errorDetail>"+ sErrorDetail +"</errorDetail>";						
				sOutput+="<RuleFlowGroup>"+ res.getRuleFlowGroup() + "</RuleFlowGroup>" +
						"<RequestChannelName>"+ res.getRequestChannelName() + "</RequestChannelName>" +
						"<RuleOutputData>"+ res.getRuleOutputData() +"</RuleOutputData>"+
						"<Status>"+ res.getStatus() +"</Status>"+
						"<Reason>"+ res.getReason() +"</Reason>";
				sOutput += "</Output>";
				LogGEN.writeTrace("CBG_Log", "inq_business_rules Output XML--- "+sOutput);
			}
			else
			{
				InqBusinessRulesRes_type0 res=new InqBusinessRulesRes_type0();
				res=res_msg.getInqBusinessRulesRes();
				LogGEN.writeTrace("CBG_Log", "inq_business_rules Failed");
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>INQUIRE_BRMS_RULES_RESPONSE</Option><returnCode>"+sReturnCode+"</returnCode>"
						+ "<errorDescription>"+sErrorDetail+"</errorDescription><Status>"+res.getStatus()+"</Status><Reason>"+res.getReason()+"</Reason>"
						+ "<td>Customer is not eligible.</td></Output>";
			}
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "inq_business_rules Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "inq_business_rules Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="1";
			//sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>INQUIRE_BRMS_RULES_RESPONSE</Option><returnCode>-1</returnCode>"
					+ "<errorDescription>"+sErrorDetail+"</errorDescription><td>Customer is not eligible.</td></Output>";
			e.printStackTrace();
			InqBusiRules_ouput=e.getMessage();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","inq_business_rules outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>INQUIRE_BRMS_RULES_RESPONSE</Option><returnCode>-1</returnCode>"
						+ "<errorDescription>"+sErrorDetail+"</errorDescription><td>Customer is not eligible.</td></Output>";
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
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			String ruleFlowGroup = xmlDataParser.getValueOf("ruleFlowGroup");
			if("CBG-CUSTELGroup".equalsIgnoreCase(ruleFlowGroup)){
				call_type = "InqBusinessRules";
			}else if("PILCREDIT_MATRIX".equalsIgnoreCase(ruleFlowGroup)){
				call_type = "CreditEligibility";
			}else if("MRZ-Code".equalsIgnoreCase(ruleFlowGroup)){
				call_type = "NameSplit";
			}else if("CBG-Proflix".equalsIgnoreCase(ruleFlowGroup)){
				call_type = "ProflixEligibility";
			}else if("CBG-RiskGroup".equalsIgnoreCase(ruleFlowGroup)){
				call_type = "RiskClassificationRetail";
			}
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
			LogGEN.writeTrace("CBG_Log"," Inquiry business rules Query : finally :"+Query);
			//				 LogGEN.writeTrace("CBG_Log","InqBusiRules_ouput : finally :"+InqBusiRules_ouput);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),InqBusiRules_ouput.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log","outputXML : finally :"+sOutput);
			return sOutput;			
		}
	}
}
