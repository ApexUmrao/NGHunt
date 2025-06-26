package com.newgen.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.stub.InqCustSignatureInfoStub;
import com.newgen.stub.InqCustSignatureInfoStub.CustSignatureInfoReqMsg;
import com.newgen.stub.InqCustSignatureInfoStub.CustSignatureInfoReq_type0;
import com.newgen.stub.InqCustSignatureInfoStub.HeaderType;


public class TFO_CustSignatureInfo extends WebServiceHandler{
	


	protected String sWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected long lTimeOut = 30000;
	String sOrg_Output=null;
	
	String xmlInput="";
	@SuppressWarnings("finally")
	public String fetchSignature(String sInputXML) 
	{

		LogGEN.writeTrace("Log", "fetchSignature sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode= "";
		String sErrorDetail="";
		String sErrorDesc =  "";
		String sOutput= "";
		
		
		
		
		Date d= new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			sHandler.readCabProperty("TFO_CustSignInfo");
			
			sWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH TFO_CustSignInfo---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET TFO_CustSignInfo---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER TFO_CustSignInfo---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD TFO_CustSignInfo---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ TFO_CustSignInfo---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT TFO_CustSignInfo---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			
			String customerId=xmlDataParser.getValueOf("customerID");
			String ref_no=xmlDataParser.getValueOf("sysRefNumber");			
			String senderID = xmlDataParser.getValueOf("senderID");
			String signatureType = xmlDataParser.getValueOf("signatureType");
			
			InqCustSignatureInfoStub inqCustSignatureInfoStub=new InqCustSignatureInfoStub(sWSDLPath);
			CustSignatureInfoReqMsg custSignatureInfoReqMsg = new CustSignatureInfoReqMsg();
			
			CustSignatureInfoReq_type0 custSignatureInfoReq_type0  = new CustSignatureInfoReq_type0();
			
			custSignatureInfoReq_type0.setCustomerID(customerId);
			custSignatureInfoReq_type0.setSignatureType(signatureType);
			
			
			custSignatureInfoReqMsg.setCustSignatureInfoReq(custSignatureInfoReq_type0);
			
			
			HeaderType Header_Input = new HeaderType();
			
			Header_Input.setUsecaseID("1.0");
			Header_Input.setServiceName("InqCustSignatureInfo");
			Header_Input.setVersionNo("2.0");
			Header_Input.setServiceAction("Inquiry");
			Header_Input.setSysRefNumber(ref_no);
			Header_Input.setSenderID(senderID);
			Header_Input.setReqTimeStamp(sDate);
			Header_Input.setUsername("");
			Header_Input.setCredentials("");
			
			custSignatureInfoReqMsg.setHeader(Header_Input);
			LogGEN.writeTrace("Log", "All values set11");			
			
			xmlInput=inqCustSignatureInfoStub.getinputXML(custSignatureInfoReqMsg);
			LogGEN.writeTrace("Log", "xmlInput for inqCustSignatureInfo " + xmlInput);

			/**
			  * //Added By Ashwani For fetching signature
			  * Date			:	12/09/2018
			  * Description 	:	Handling for signature string 
			  */
			
			String responseMain= inqCustSignatureInfoStub.inqCustSignatureInfo_Oper(custSignatureInfoReqMsg);
			LogGEN.writeTrace("Log", "setting output xml for inqCustSignatureInfo  and output is "+responseMain);
			responseMain = responseMain.replaceAll("ns0:", "");
			responseMain = responseMain.replaceAll("ns1:", "");
			responseMain = responseMain.replaceAll("ns2:", "");
			responseMain = responseMain.replaceAll("ns3:", "");			
			
			xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(responseMain);
			
			LogGEN.writeTrace("Log", "inqCustSignatureInfo clean output is "+responseMain);
			sReturnCode = xmlDataParser.getValueOf("returnCode"); 
			sErrorDetail = xmlDataParser.getValueOf("errorDetail");
			sErrorDesc = xmlDataParser.getValueOf("errorDescription");
			System.out.println(sErrorDetail);
			
			if((sErrorDesc != null && !sErrorDesc.equalsIgnoreCase("Failure")) || (sReturnCode != null && !sReturnCode.equalsIgnoreCase("1")))
			{
				StringBuffer tempOutput = new StringBuffer();
				tempOutput.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
						.append("<Output>")
						.append("<option>InqCustSignatureInfo</option>")
						.append("<returnCode>"+sReturnCode+"</returnCode>")
						.append("<errorDescription>"+sErrorDesc+"</errorDescription>")
						.append("<errorDetail>"+sErrorDetail+"</errorDetail>")
						.append("<custSignatureInfoResMsg>")						
						.append("<custSignatureInfoRes>")
						.append("<customerID>"+xmlDataParser.getValueOf("customerID")+"</customerID>")
						.append("<signatureType>"+xmlDataParser.getValueOf("signatureType")+"</signatureType>")
						.append("<signature>"+xmlDataParser.getValueOf("signature")+"</signature>")
						.append("</custSignatureInfoRes>")
						.append("</custSignatureInfoResMsg>")
						.append("</Output>");			
				
					sOutput = tempOutput.toString();		
			}
			else
			{
				try
				{
					LogGEN.writeTrace("Log", "Failed");
			    	sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>ADD_Customer</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch Signature.</td></Output>";
				}
				catch(Exception e)
				{
					LogGEN.writeTrace("Log", "Failed");
					e.printStackTrace();
				}
			}
		}	
		catch (Exception e)
		{
			LogGEN.writeTrace("Log", "Error in Web Serviice :"+e.toString());
			LogGEN.writeTrace("Log", "Error Trace in Web Serviice :"+e.getStackTrace());
			sReturnCode="-1";
			sErrorDetail=e.getMessage();
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustSignatureInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetchSignature.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("Log","outputXML.trim().length() :"+sOutput.trim().length());			

			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>InqCustSignatureInfo</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDetail+"</errorDescription><td>Unable to fetch signature.</td></Output>";
			}
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
			{
				Status="Success";
			}
			else
				Status="Failure";
			 try {
				sHandler.readCabProperty("JTS");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			 
				String dburl=(String)currentCabPropertyMap.get("DBURL");
				String dbuser=(String)currentCabPropertyMap.get("USER");
				String dbpass=(String)currentCabPropertyMap.get("PASS");
				
				
				String inputXml=xmlInput;
				LogGEN.writeTrace("Log", inputXml);
				String winame=xmlDataParser.getValueOf("WiName");
				String sessionID= xmlDataParser.getValueOf("SessionId");
				String call_type=xmlDataParser.getValueOf("Calltype");
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
				  /**
				  * //Added By Ashwani For inserting original mssg
				  * Date			:	12/09/2018
				  * Description 	:	Replace execute with executeClob
				  */
				 String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
					"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
					"?,sysdate,'"+  Status + "')";
				 LogGEN.writeTrace("Log","TFO CustSignatureINfo Query : finally :"+Query);
				 LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output);
				 try {
					 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output.replaceAll("'", "''"));
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			 //End Here
			return sOutput;			
		}
	}

	



}
