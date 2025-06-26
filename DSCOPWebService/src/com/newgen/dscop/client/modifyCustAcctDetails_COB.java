package com.newgen.dscop.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;
import com.newgen.dscop.stub.ModCustAcctDetailsStub;
import com.newgen.dscop.stub.ModCustAcctDetailsStub.HeaderType;
import com.newgen.dscop.stub.ModCustAcctDetailsStub.ModCustAcctDetailsReqChoice_type1;
import com.newgen.dscop.stub.ModCustAcctDetailsStub.ModCustAcctDetailsReqMsg;
import com.newgen.dscop.stub.ModCustAcctDetailsStub.ModCustAcctDetailsReq_type0;
import com.newgen.dscop.stub.ModCustAcctDetailsStub.ModCustAcctDetailsResMsg;
import com.newgen.dscop.stub.modifyCustSig;
import com.newgen.dscop.stub.modifyCustSig.CustomerSignatureUpdate_type1;

public class modifyCustAcctDetails_COB extends DSCOPServiceHandler 
{
	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	public String invocation = null;
	public String outputxml = null;
	XMLParser xmlDataParser =null;

	public String modify_account(String sInputXML) 
	{			
		LogGEN.writeTrace("CBG_Log", "call to SignatureUpoadToFlexSystem_COB, INPUTXML : " + sInputXML);			
		xmlDataParser= new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		LogGEN.writeTrace("CBG_Log", "WorkitemNo : " + xmlDataParser.getValueOf("WIName"));
		String sOutput="";
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc = "";
		String xmlInput="";

		DSCOPServiceHandler sHandler = new DSCOPServiceHandler();
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		LogGEN.writeTrace("CBG_Log", "sDate : " + sDate);

		LogGEN.writeTrace("CBG_Log","ReadingCabProperty : COB_INTEGRATION_INVOCATION");
	
		invocation = "onshore";

		LogGEN.writeTrace("CBG_Log","INVOCATION FLAG : " + invocation);

		if(invocation.equalsIgnoreCase("offshore"))
		{
			LogGEN.writeTrace("CBG_Log","INTEGRATION CALL DATA READING FROM FILE...");
			outputxml = readFromFile(sInputXML);
//			LogGEN.writeTrace("CBG_Log","output xml " + outputxml);
		}		
		else
		{
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("SignatureUpoadToFlexSystem_COB");
			LogGEN.writeTrace("CBG_Log", "SignatureUpoadToFlexSystem_COB WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			LogGEN.writeTrace("CBG_Log", "SignatureUpoadToFlexSystem_COB WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "SignatureUpoadToFlexSystem_COB CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "SignatureUpoadToFlexSystem_COB USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "SignatureUpoadToFlexSystem_COB PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "SignatureUpoadToFlexSystem_COB LOGIN_REQ: "+sLoginReq);


			String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			LogGEN.writeTrace("CBG_Log", "sCustomerID : " + sCustomerID);

			String ref_no=xmlDataParser.getValueOf("REF_NO");
			LogGEN.writeTrace("CBG_Log", "ref_no : " + ref_no);

			String call_type=xmlDataParser.getValueOf("ServiceName");
			LogGEN.writeTrace("CBG_Log", "call_type : " + call_type);

			String WMSUser=xmlDataParser.getValueOf("WMSUser");
			LogGEN.writeTrace("CBG_Log", "WMSUser : " + WMSUser);						

			ModCustAcctDetailsStub acc_stub = null;
			try
			{
				acc_stub = new ModCustAcctDetailsStub(sWSDLPath);
			}
			catch (AxisFault e)
			{
				LogGEN.writeTrace("CBG_Log","Exception while making object of ModCustAcctDetailsStub(wsdlLocation)");
				e.printStackTrace();
			}

			ModCustAcctDetailsReqMsg acc_Req_Msg = new ModCustAcctDetailsReqMsg();
			ModCustAcctDetailsReq_type0 acc_Req_Type=new ModCustAcctDetailsReq_type0();				
			ModCustAcctDetailsResMsg acc_Rep_Msg=new ModCustAcctDetailsResMsg();

			modifyCustSig sig_stub = null;
			try
			{
				sig_stub = new modifyCustSig(sWSDLPath);
			}
			catch (AxisFault e)
			{
				LogGEN.writeTrace("CBG_Log","Exception while making object of modifyCustSig(wsdlLocation)");
				e.printStackTrace();
			}

			modifyCustSig.ModCustAcctDetailsReqMsg sig_req_msg = new modifyCustSig.ModCustAcctDetailsReqMsg();
			modifyCustSig.ModCustAcctDetailsReq_type0 acc_Req_Type_sig = new modifyCustSig.ModCustAcctDetailsReq_type0();				
			String acc_Rep_Msg_sig = "";

			HeaderType Header_Input = new HeaderType();
			modifyCustSig.HeaderType Header_Input_sig = new modifyCustSig.HeaderType();				

			Header_Input.setUsecaseID("1234");
			Header_Input.setServiceName("ModCustAcctDetails");
			Header_Input.setVersionNo("1.0");
			Header_Input.setServiceAction("Modify");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername(sCustomerID);
			//Header_Input.setCredentials(loggedinuser);	
			Header_Input.setCredentials(WMSUser);						

			Header_Input_sig.setUsecaseID("1234");
			Header_Input_sig.setServiceName("ModCustAcctDetails");
			Header_Input_sig.setVersionNo("1.0");

			if(call_type.equalsIgnoreCase("customerSignatureUpdate"))
			{
				if(xmlDataParser.getValueOf("sigInq").equalsIgnoreCase("Y"))						
				{
					LogGEN.writeTrace("CBG_Log", "sigInq Flag If block" );
					Header_Input_sig.setServiceAction("Inquiry");
				}
				else
				{
					LogGEN.writeTrace("CBG_Log", "sigInq Flag else block" );
					Header_Input_sig.setServiceAction("Modify");
				}
			}
			else
			{
				Header_Input_sig.setServiceAction("Modify");
			}

			Header_Input_sig.setSysRefNumber(ref_no);
			Header_Input_sig.setSenderID(sHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
			Header_Input_sig.setReqTimeStamp(sDate);
			Header_Input_sig.setUsername(sCustomerID);
			//Header_Input_sig.setCredentials(loggedinuser);	
			Header_Input_sig.setCredentials(WMSUser);						

			acc_Req_Type.setCustAccountNumber(xmlDataParser.getValueOf("custAccountNumber"));
			//acc_Req_Type.setCustomerId(xmlDataParser.getValueOf("customerId"));
			acc_Req_Type.setCustomerId(sCustomerID); // 16-May-2014 Onsite
			acc_Req_Type.setMaintenanceType(call_type);
			acc_Req_Type.setMaintenanceOption(xmlDataParser.getValueOf("maintenanceOption"));
			acc_Req_Type.setCheckerId("WMSUSER");
			acc_Req_Type.setMakerId("WMSUSER");
			acc_Req_Type.setUpdateSerialNo("");

			acc_Req_Type_sig.setCustAccountNumber(xmlDataParser.getValueOf("custAccountNumber"));
			//acc_Req_Type_sig.setCustomerId(xmlDataParser.getValueOf("customerId"));
			acc_Req_Type_sig.setCustomerId(sCustomerID); // 16-May-2014 Onsite
			acc_Req_Type_sig.setMaintenanceType(call_type);
			acc_Req_Type_sig.setMaintenanceOption(xmlDataParser.getValueOf("maintenanceOption"));
			acc_Req_Type_sig.setCheckerId("WMSUSER");
			acc_Req_Type_sig.setMakerId("WMSUSER");
			acc_Req_Type_sig.setUpdateSerialNo("");					

			ModCustAcctDetailsReqChoice_type1 choice=new ModCustAcctDetailsReqChoice_type1();
			//AccountStatusUpdate_type1 acc_up=new AccountStatusUpdate_type1();
			//ServiceChargesUpdate_type1 ser_chr=new ServiceChargesUpdate_type1();
			//StandingInstructionUpdate_type1 stand=new StandingInstructionUpdate_type1();
			//CustomerRelationUpdate_type1 cust_rel=new CustomerRelationUpdate_type1();
			//MemoDetailsUpdate_type1 memo=new MemoDetailsUpdate_type1();
			CustomerSignatureUpdate_type1 sig=new CustomerSignatureUpdate_type1();

			com.newgen.dscop.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1 sigchoice=new com.newgen.dscop.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1();

			if(call_type.equalsIgnoreCase("customerSignatureUpdate"))
			{
				LogGEN.writeTrace("CBG_Log","setting Image paramaters for customerSignatureUpdate call");

				sig.setImageType(xmlDataParser.getValueOf("imageType"));
				sig.setImageAddDate(xmlDataParser.getValueOf("imageAddDate"));							
				sig.setImageBinaryData(xmlDataParser.getValueOf("imageBinaryData"));							
				sigchoice.setCustomerSignatureUpdate(sig);
			}

			acc_Req_Type.setModCustAcctDetailsReqChoice_type1(choice);

			acc_Req_Type_sig.setModCustAcctDetailsReqChoice_type1(sigchoice);

			acc_Req_Msg.setHeader(Header_Input);
			sig_req_msg.setHeader(Header_Input_sig);

			acc_Req_Msg.setModCustAcctDetailsReq(acc_Req_Type);
			sig_req_msg.setModCustAcctDetailsReq(acc_Req_Type_sig);						

			if(!call_type.equalsIgnoreCase("customerSignatureUpdate"))
			{
				try
				{
					xmlInput = acc_stub.getinputXML(acc_Req_Msg);
					acc_Rep_Msg = acc_stub.modCustAcctDetails_Oper(acc_Req_Msg);						
//					LogGEN.writeTrace("CBG_Log","ModifyCustAcctDetails_COB output "+  acc_Rep_Msg);
				}
				catch (RemoteException e)
				{
					e.printStackTrace();
				}							
			}	
			else
			{							
				try
				{
					xmlInput = sig_stub.getinputXML(sig_req_msg);
//					LogGEN.writeTrace("CBG_Log","Printing Inputxml for signature call : " + xmlInput);
				}
				catch (RemoteException e)
				{
					LogGEN.writeTrace("CBG_Log","Exception while getting Inputxml for signature call : " + e.getMessage());
					e.printStackTrace();
				}					

				try
				{
					LogGEN.writeTrace("CBG_Log","calling Sub object");
					acc_Rep_Msg_sig = sig_stub.modCustAcctDetails_Oper(sig_req_msg);	
//					LogGEN.writeTrace("CBG_Log","Printing outputxml : " + acc_Rep_Msg_sig);		
				}
				catch (RemoteException e)
				{
					LogGEN.writeTrace("CBG_Log","Exception while getting outputxml for signature call : " + e.getMessage());
					e.printStackTrace();
				}
			}

			if(call_type.equalsIgnoreCase("customerSignatureUpdate"))
			{
				try
				{						    	
					sReturnCode =acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:returnCode>")+16,acc_Rep_Msg_sig.indexOf("</ns1:returnCode>"));
					sErrorDetail =acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:errorDetail>")+17,acc_Rep_Msg_sig.indexOf("</ns1:errorDetail>"));

					LogGEN.writeTrace("CBG_Log","sReturnCode : " + sReturnCode);
					LogGEN.writeTrace("CBG_Log","sErrorDetail : " + sErrorDetail);						    	


					sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<Output>"+
							"<Option>SignatureUpoadToFlexSystem_COB</Option>" +
							"<returnCode>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:returnCode>")+16,acc_Rep_Msg_sig.indexOf("</ns1:returnCode>"))+"</returnCode>" +
							"<errorDescription>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns1:errorDetail>")+17,acc_Rep_Msg_sig.indexOf("</ns1:errorDetail>"))+"</errorDescription>";				
					sOutput+="<Reason>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns0:reason>")+12,acc_Rep_Msg_sig.indexOf("</ns0:reason>")) +"</Reason>"+
							"<imageType>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns0:imageType>")+15,acc_Rep_Msg_sig.indexOf("</ns0:imageType>")) +"</imageType>"+
							"<Status>"+acc_Rep_Msg_sig.substring(acc_Rep_Msg_sig.indexOf("<ns0:status>")+12,acc_Rep_Msg_sig.indexOf("</ns0:status>")) +"</Status></Output>";
//					LogGEN.writeTrace("CBG_Log", "sOutput : " + sOutput);								
				}
				catch(Exception ee)
				{
					Header_Input= acc_Rep_Msg.getHeader();
					sReturnCode= Header_Input.getReturnCode();
					sErrorDetail=Header_Input.getErrorDetail();
					sErrorDesc = Header_Input.getErrorDescription();

					LogGEN.writeTrace("CBG_Log","sReturnCode : " + sReturnCode);
					LogGEN.writeTrace("CBG_Log","sErrorDetail : " + sErrorDetail);
					LogGEN.writeTrace("CBG_Log","sErrorDesc : " + sErrorDesc);

					sOutput ="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>SignatureUpoadToFlexSystem_COB</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to modify Signature.</td></Output>";
					LogGEN.writeTrace("CBG_Log", "sOutput (Catch_Block): " + sOutput);
				}
				finally
				{

					String Status="";
					if(sReturnCode.equalsIgnoreCase("0"))
					{
						Status="Success";
					}
					else
						Status="Failure";
					
					String dburl=(String)currentCabPropertyMap.get("DBURL");
					String dbuser=(String)currentCabPropertyMap.get("USER");
					String dbpass=(String)currentCabPropertyMap.get("PASS");
					DBConnection con = new DBConnection();
					try
					{
						dbpass=AESEncryption.decrypt(dbpass);
					}
					catch(Exception e)
					{

					}
					String inputXml="";
					try {
						inputXml = sig_stub.getinputXML(sig_req_msg);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					String winame=xmlDataParser.getValueOf("WIName");
					String sessionID= xmlDataParser.getValueOf("SessionId");
					String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
							"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
							"?,sysdate,'"+  Status + "')";
					LogGEN.writeTrace("CBG_Log","ADD CASA Account Query : finally :"+Query);
					//LogGEN.writeTrace("CBG_Log","sOrg_Output : finally :"+sOrg_Output_Add_Cust);
					try {
						con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),acc_Rep_Msg_sig.replaceAll("'", "''"));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			else
			{
				LogGEN.writeTrace("CBG_Log", "else case");
			}				
		}
		xmlDataParser=null;
		return sOutput;
	}
	// Ajay Dhiman
	@SuppressWarnings("finally")
	private String readFromFile(String inputxml) 
	{
		LogGEN.writeTrace("CBG_Log","Inside readFromFile()");	
		String outputXml = null;
		try
		{		
			XMLParser parse = new XMLParser(inputxml);

			String processName = parse.getValueOf("ADCBProcess");			
			LogGEN.writeTrace("CBG_Log", processName);

			String methodName = parse.getValueOf("ADCBMethod");			
			LogGEN.writeTrace("CBG_Log" , methodName);

			StringBuffer fileData = new StringBuffer();
			try 
			{
				//BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ System.getProperty("file.separator") + "FBData//FBOutputFile//" + processName + "//" + methodName + ".xml"));
				BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+ System.getProperty("file.separator") + "COB_DUMMY_INTEGRATION//" + processName + "//" + methodName + ".xml"));
				char[] buf = new char[1024];
				int numRead = 0;
				while ((numRead = reader.read(buf)) != -1) 
				{
					String readData = String.valueOf(buf, 0, numRead);
					fileData.append(readData);
				}				
				reader.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			outputXml = fileData.toString();
			LogGEN.writeTrace("CBG_Log", "outputXml : " + outputXml);
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Exception while reading the file");
			LogGEN.writeTrace("CBG_Log", e.getMessage());

			outputXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+"<NGException>Error reading file..</NGException>"
					+"<Retry>Y</Retry>";			
		} 
		finally 
		{
			LogGEN.writeTrace("CBG_Log", "outputXml : " + outputXml);
			return outputXml;
		}			
	}

	public String getUniqueNo()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHMMS");
		String uniqueNo = sdf.format(date);
		return uniqueNo;
	}
}
