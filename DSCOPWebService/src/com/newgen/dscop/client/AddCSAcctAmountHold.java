package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.AddCSAcctAmountHoldStub;
import com.newgen.dscop.stub.AddCSAcctAmountHoldStub.AddCSAcctAmountHoldReqMsg;
import com.newgen.dscop.stub.AddCSAcctAmountHoldStub.AddCSAcctAmountHoldReq_type0;
import com.newgen.dscop.stub.AddCSAcctAmountHoldStub.AddCSAcctAmountHoldResMsg;
import com.newgen.dscop.stub.AddCSAcctAmountHoldStub.AddCSAcctAmountHoldRes_type0;
import com.newgen.dscop.stub.AddCSAcctAmountHoldStub.HeaderType;


public class AddCSAcctAmountHold extends DSCOPServiceHandler
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
	public String AddCSAcctAmountHoldToFCR(String sInputXML) 
	{

		LogGEN.writeTrace("CBG_Log", "Fuction called AddCSAcctAmountHold");
		LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		String accountNo=xmlDataParser.getValueOf("accountNo");
		String amountToHold=xmlDataParser.getValueOf("amountToHold");
		String expiryDate=xmlDataParser.getValueOf("expiryDate");
		String narrative=xmlDataParser.getValueOf("narrative");
		String reasonCode=xmlDataParser.getValueOf("reasonCode");
		String orgBrnCode=xmlDataParser.getValueOf("orgBrnCode");
		String flgRepeat=xmlDataParser.getValueOf("flgRepeat");

		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		DSCOPServiceHandler sHandler= new DSCOPServiceHandler();

		try
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("AddCSAcctAmountHold");
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "AddCSAcctAmountHold TIME_OUT: "+lTimeOut);

			String ref_no=xmlDataParser.getValueOf("REF_NO");

			AddCSAcctAmountHoldStub add_CSAcct_stub=new AddCSAcctAmountHoldStub(sWSDLPath);
			AddCSAcctAmountHoldReq_type0 add_CSAcct_req=new AddCSAcctAmountHoldReq_type0();
			AddCSAcctAmountHoldReqMsg add_CSAcct_req_msg=new AddCSAcctAmountHoldReqMsg();

			HeaderType Header_Input = new HeaderType();
			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("AddCSAcctAmountHold");
			Header_Input.setVersionNo("1.0");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);


			LogGEN.writeTrace("CBG_Log", "All values set11");

			add_CSAcct_req_msg.setHeader(Header_Input);

			add_CSAcct_req.setAccountNo(accountNo);
			add_CSAcct_req.setAmountToHold(amountToHold);
			add_CSAcct_req.setExpiryDate(expiryDate);
			add_CSAcct_req.setNarrative(narrative);
			add_CSAcct_req.setReasonCode(reasonCode);
			add_CSAcct_req.setOrgBrnCode(orgBrnCode);
			add_CSAcct_req.setFlgRepeat(flgRepeat);

			add_CSAcct_req_msg.setAddCSAcctAmountHoldReq(add_CSAcct_req);
			add_CSAcct_stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(lTimeOut);
			xmlInput=add_CSAcct_stub.getInputXml(add_CSAcct_req_msg);
			AddCSAcctAmountHoldResMsg add_CSAcct_res_msg= add_CSAcct_stub.addCSAcctAmountHold_Oper(add_CSAcct_req_msg);
			sOrg_Output=add_CSAcct_stub.outputXML;
			Header_Input=add_CSAcct_res_msg.getHeader();
			sReturnCode=  Header_Input.getReturnCode();
			sErrorDetail=Header_Input.getErrorDetail();
			sErrorDesc=Header_Input.getErrorDescription();
			System.out.println(sErrorDetail);
			if(!sErrorDesc.equalsIgnoreCase("Failure") || !sReturnCode.equalsIgnoreCase("1"))
			{	
				LogGEN.writeTrace("CBG_Log", "inside if");

				AddCSAcctAmountHoldRes_type0 resType0 = new AddCSAcctAmountHoldRes_type0();
				resType0 = add_CSAcct_res_msg.getAddCSAcctAmountHoldRes();

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<Output>"+
						"<Option>AddCSAcctAmountHold</Option>" +
						"<returnCode>"+sReturnCode+"</returnCode>" +
						"<errorDescription>"+sErrorDetail+"</errorDescription>"+
						"<Response>"+ 
						"<AddCSAcctAmountHold>"+
						"<accountNo>"+ resType0.getAccountNo() +"</accountNo>"+
						"<amountToHold>"+ resType0.getAmountToHold() +"</amountToHold>"+
						"<expiryDate>"+ resType0.getExpiryDate() +"</expiryDate>"+
						"<narrative>"+ resType0.getNarrative() +"</narrative>"+
						"<reasonCode>"+ resType0.getReasonCode() +"</reasonCode>"+
						"<orgBrnCode>"+ resType0.getOrgBrnCode() +"</orgBrnCode>"+
						"<holdNumber>"+ resType0.getHoldNumber() +"</holdNumber>"+
						"<status>"+ resType0.getStatus() +"</status>"+
						"<reason>"+ resType0.getReason() +"</reason>"+
						"</AddCSAcctAmountHold>"+
						"</Response>"+
						"</Output>";
				LogGEN.writeTrace("CBG_Log", "output xml within if block FetchIslamicCommodityDtls:" + sOutput);

			}
			else
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddCSAcctAmountHold</Option><Output><returnCode>"+sReturnCode+"</returnCode"
						+ "><errorDescription>"+sErrorDetail+"</errorDescription><Reason>"+sErrorDesc+"</Reason>"
						+ "<Status>Unable to Add CSAcctAmount Hold To FCR</Status></Output>";
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddCSAcctAmountHold</Option><Output><returnCode>"+sReturnCode+"</returnCode"
					+ "><errorDescription>"+sErrorDetail+"</errorDescription><Reason>"+sErrorDesc+"</Reason>"
					+ "<Status>Unable to Add CSAcctAmount Hold To FCR</Status></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput);	

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>AddCSAcctAmountHold</Option><Output><returnCode>"+sReturnCode+"</returnCode"
						+ "><errorDescription>"+sErrorDetail+"</errorDescription><Reason>"+sErrorDesc+"</Reason>"
						+ "<Status>Unable to Add CSAcctAmount Hold To FCR</Status></Output>";
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
			String winame=xmlDataParser.getValueOf("WiName");
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
				e.printStackTrace();
			}
			DBConnection con=new DBConnection();

			String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("CBG_Log","AddCSAcctAmountHold Query : finally :"+Query);
			//				 LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
			return sOutput;			
		}
	}
}
