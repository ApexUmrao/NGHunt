package com.newgen.dscop.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModMIBRegistrationStub;

public class ModMIBRegistration extends DSCOPServiceHandler {

	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected long lTimeOut = 30000L;
	String xmlInput = "";
	String sMIB_Regisration = null;
	String Status = "";

	@SuppressWarnings("finally")
	public String Mod_MIB_Registration(String sInputXML) {
		LogGEN.writeTrace("CBG_Log", "Function called Mod_MIB_Registion");
		LogGEN.writeTrace("CBG_Log", "ModMIBRegistration sInputXML---" + sInputXML);
		XMLParser xmlParser = new XMLParser();
		xmlParser.setInputXML(sInputXML);
		String sReturnCode = "";
		String sErrorDetail = "";
		String sErrorDesc = "";
		String sOutput = "";
		String custId = xmlParser.getValueOf("customerID");
		String cust_mobNO = xmlParser.getValueOf("customerMobileNumber");
		String ref_no = xmlParser.getValueOf("REF_NO");
		String senderId = xmlParser.getValueOf("SenderId");
		String actionCode = xmlParser.getValueOf("actionCode");
		String deviceName = xmlParser.getValueOf("deviceName");
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);

		try {
			//handler.readCabProperty("Mod_MIB_Registration");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("Mod_MIB_Registration");
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "Mod_MIB_Registration TIME_OUT: "+lTimeOut);
					
			ModMIBRegistrationStub mibRegistrationStub = new ModMIBRegistrationStub(this.sWSDLPath);					
			ModMIBRegistrationStub.ModMIBRegistrationReq_type0 mib_registration_req = new ModMIBRegistrationStub.ModMIBRegistrationReq_type0();
			ModMIBRegistrationStub.ModMIBRegistrationReqMsg mib_registration_req_msg = new ModMIBRegistrationStub.ModMIBRegistrationReqMsg();

			ModMIBRegistrationStub.HeaderType headerType = new ModMIBRegistrationStub.HeaderType();
			headerType.setServiceName("ModMIBRegistration");
			headerType.setVersionNo("1.0");
			headerType.setServiceAction("Modify");
			headerType.setSysRefNumber(ref_no);
			headerType.setSenderID(senderId);
			headerType.setReqTimeStamp(sDate);
			headerType.setUsername(sUser);
			headerType.setCredentials(this.loggedinuser);

			mib_registration_req_msg.setHeader(headerType);
			mib_registration_req.setCustomerId(custId);
			mib_registration_req.setCustomerMobileNumber(cust_mobNO);
			mib_registration_req.setActionCode(actionCode);
			mib_registration_req.setDeviceName(deviceName);
			mib_registration_req_msg.setModMIBRegistrationReq(mib_registration_req);
			xmlInput = mibRegistrationStub.getInputXml(mib_registration_req_msg);
			LogGEN.writeTrace("CBG_Log", "ModMIBRegistration InputXML: " + xmlInput);
			mibRegistrationStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(this.lTimeOut);
			ModMIBRegistrationStub.ModMIBRegistrationResMsg registration_Res_Msg = mibRegistrationStub.modMIBRegistration_Oper(mib_registration_req_msg);
			sMIB_Regisration = mibRegistrationStub.resModMIBRegMsg;			
			headerType = registration_Res_Msg.getHeader();
			sReturnCode = headerType.getReturnCode();
			sErrorDetail = headerType.getErrorDetail();
			sErrorDesc = headerType.getErrorDescription();

			if ((!(sErrorDesc.equalsIgnoreCase("Failure")))	|| (!(sReturnCode.equalsIgnoreCase("1")))) {

				ModMIBRegistrationStub.ModMIBRegistrationRes_type0 mod_mib_res = new ModMIBRegistrationStub.ModMIBRegistrationRes_type0();
				mod_mib_res = registration_Res_Msg.getModMIBRegistrationRes();
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<Output>" +
				"<Option>ADCB_MODMIBREGISTRATION</Option>" +
				"<returnCode>"+ sReturnCode	+ "</returnCode>"+ 
				"<errorDescription>"+ sErrorDetail+ "</errorDescription>"+ 
				"<ModMIBRegistrationRes>"+ 
				"<ACTION_CODE>"+ mod_mib_res.getActionCode()+ "<ACTION_CODE>"+ 
				"<CUSTOMER_ID>"+ mod_mib_res.getCustomerId()+ "</CUSTOMER_ID>"+
				"<CUSTOMER_MOBILE_NO>"+ mod_mib_res.getCustomerMobileNumber()+ "</CUSTOMER_MOBILE_NO>"+ 
				"<DEVICE_NAME>"	+ mod_mib_res.getDeviceName()+ "</DEVICE_NAME>"+ 
				"</ModMIBRegistrationRes>"+ 
				"</Output>";
			} else {
				LogGEN.writeTrace("CBG_Log", "Failed");
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_MODMIBREGISTRATION</Option><Output><returnCode>"
						+ sReturnCode
						+ "</returnCode><errorDescription>"
						+ sErrorDetail
						+ "</errorDescription><td>Unable MOD MIB REGISTRATION.</td></Output>";
			}
			LogGEN.writeTrace("CBG_Log", "ModMIBRegistration output xml : " + sOutput);
		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", "Error in Web Serviice :" + e.toString());
			LogGEN.writeTrace("CBG_Log","Error Trace in Web Serviice :" + e.getStackTrace());
			System.out.println("Error Trace in Web Serviice :"+ e.getStackTrace());
			Status = "Failure";
			sErrorDetail = e.getMessage();
			sReturnCode = "-1";
			sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_MODMIBREGISTRATION</Option><Output><returnCode>"
					+ sReturnCode
					+ "</returnCode><errorDescription>"
					+ sErrorDetail
					+ "</errorDescription><td>Unable  MOD MIB REGISTRATION.</td></Output>";
			e.printStackTrace();

		} finally {
			LogGEN.writeTrace("CBG_Log", "outputXML.trim().length() :"
					+ sOutput.trim().length());
			if (sOutput.trim().length() < 1) {
				sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADCB_MODMIBREGISTRATION</Option><Output><returnCode>"
						+ sReturnCode
						+ "</returnCode><errorDescription>"
						+ sErrorDetail
						+ "</errorDescription><td>Unable to add customer.</td></Output>";
			}
			if ((sReturnCode.equalsIgnoreCase("0"))
					|| (sReturnCode.equalsIgnoreCase("2"))) {
				Status = "Success";
			} else
				Status = "Failure";
			try {
				//handler.readCabProperty("JTS");
			} catch (Exception e) {
				e.printStackTrace();
			}

			String dburl = (String) currentCabPropertyMap.get("DBURL");
			String dbuser = (String) currentCabPropertyMap.get("USER");
			String dbpass = (String) currentCabPropertyMap.get("PASS");
			String inputXml = xmlInput;
			String winame = xmlParser.getValueOf("WiName");
			String sessionID = xmlParser.getValueOf("SessionId");
			String call_type = xmlParser.getValueOf("DSCOPCallType");
			sCabinet = xmlParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "11111111111111111111%%%%%%%%%%%%");

			try {
				dbpass = AESEncryption.decrypt(dbpass);
			} catch (Exception localException1) {
			}
			DBConnection con = new DBConnection();

			String Query = "insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"
					+ winame
					+ "',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "
					+ "and randomnumber='"
					+ sessionID
					+ "'),'"
					+ call_type
					+ "',?,to_date('"
					+ sDate
					+ "','dd/mm/yyyy hh24:mi:ss'),"
					+ "?,sysdate,'" + Status + "')";
			LogGEN.writeTrace("CBG_Log"," Modify MIB Registration Query : finally :" + Query);
//			LogGEN.writeTrace("CBG_Log", "sMIB_Regisration : finally :"+ sMIB_Regisration);
			try {
				con.executeClob("jdbc:oracle:thin:@" + dburl, dbuser, dbpass, Query, inputXml.replaceAll("'", "''"), sMIB_Regisration.replaceAll("'", "''"));
			} catch (Exception e2) {
				e2.getMessage();
			}
			LogGEN.writeTrace("CBG_Log", "outputXML : finally :" + sOutput);
			return sOutput;
		}

	}

}
