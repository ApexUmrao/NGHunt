package com.newgen.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.AxisFault;

import com.newgen.AESEncryption;
import com.newgen.stub.modifyCustSig;
import com.newgen.stub.modifyCustSig.CustomerSignatureUpdate_type1;
import com.newgen.stub.modifyCustSig.HeaderType;
import com.newgen.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1;
import com.newgen.stub.modifyCustSig.ModCustAcctDetailsReqMsg;
import com.newgen.stub.modifyCustSig.ModCustAcctDetailsReq_type0;

public class SignatureUploadToFlex_COB extends WebServiceHandler 
{
	public String wsdlPath = null;
	public String cabinetName = null;
	public String userName = null;
	public String password = null;
	public String customerID = null;
	public String custAccNo = null; 
	public String wi_name = null;
	public XMLParser xmlDataParser = null;
	public String returnCode = null;
	public String errorDetail = null;	
	public String invocation = null;
	public String outputxml = null;
	public String inputSOAPxml = null;
	public String bpmMakerID = null;
	public String bpmCheckerID = null;
	public String imageType = null;
	public String imageBinaryData = null;
	public boolean isLoginRequired = false; 
	WebServiceHandler webHandler = null;
	
	public String signatureInjection(String inputxml) throws IOException
	{		
		LogGEN.writeTrace("COB_Log", "signatureInjection() for SignatureUpload, INPUTXML : " + inputxml);
		webHandler = new WebServiceHandler();		
		try
		{	
			LogGEN.writeTrace("COB_Log","ReadingCabProperty : COB_INTEGRATION_INVOCATION");
			webHandler.readCabProperty("COB_INTEGRATION_INVOCATION");
			invocation = (String)currentCabPropertyMap.get("INVOCATION");
			LogGEN.writeTrace("COB_Log","INVOCATION FLAG : " + invocation);
			if(invocation.equalsIgnoreCase("offshore"))
			{
				LogGEN.writeTrace("COB_Log","INTEGRATION CALL DATA READING FROM FILE...");			
				outputxml = readFromFile(inputxml);
			}
			else if(invocation.equalsIgnoreCase("onshore"))
			{
				LogGEN.writeTrace("COB_Log","INTEGRATION CALL DATA CALLING WEB SERVICE...");			
				outputxml = communicateToWebService(inputxml);
			}			
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for section: COB_INTEGRATION_INVOCATION");		
			e.printStackTrace();
		}
		return outputxml;
		
	}
		
	private String readFromFile(String inputxml) 
	{
		LogGEN.writeTrace("COB_Log","Inside readFromFile()");	
		String outputXml = null;
		try
		{		
			XMLParser parse = new XMLParser(inputxml);

			String processName = parse.getValueOf("ADCBProcess");			
			LogGEN.writeTrace("COB_Log", processName);

			String methodName = parse.getValueOf("ADCBMethod");			
			LogGEN.writeTrace("COB_Log" , methodName);

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
			LogGEN.writeTrace("COB_Log", "outputXml : " + outputXml);
		}
		catch(Exception e)
		{
			LogGEN.writeTrace("COB_Log", "Exception while reading the file");
			LogGEN.writeTrace("COB_Log", e.getMessage());

			outputXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"
					+"<NGException>Error reading file..</NGException>"
					+"<Retry>Y</Retry>";			
		} 
		finally 
		{
			LogGEN.writeTrace("COB_Log", "outputXml : " + outputXml);
			
		}
		return outputXml;
	}
	
	public String getUniqueNo()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmssSSS");
		String uniqueNo = sdf.format(date);
		LogGEN.writeTrace("COB_Log", "uniqueNo : " + uniqueNo);	
		return uniqueNo;
	}
	
	public String communicateToWebService(String inputxml)
	{	
		LogGEN.writeTrace("COB_Log", "Inside communicateToWebService()");
		LogGEN.writeTrace("COB_Log","ReadingCabProperty : SignatureUpoadToFlexSystem_COB call");
		try
		{
			webHandler.readCabProperty("SignatureUploadToFlexSystem_COB");
		}
		catch (Exception e)
		{
			LogGEN.writeTrace("COB_Log","Exception while reding the property file for call :SignatureUpoadToFlexSystem_COB.");		
			e.printStackTrace();
		}	
		
		wsdlPath = (String)currentCabPropertyMap.get("WSDL_PATH");
		cabinetName = (String)currentCabPropertyMap.get("CABINET");
		userName = (String)currentCabPropertyMap.get("USER");
		isLoginRequired = Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
		password = (String)currentCabPropertyMap.get("PASSWORD");
		
		LogGEN.writeTrace("COB_Log", "wsdlPath : " + wsdlPath);
		LogGEN.writeTrace("COB_Log", "cabinetName : " + cabinetName);
		LogGEN.writeTrace("COB_Log", "userName : " + userName);
		LogGEN.writeTrace("COB_Log", "isLoginRequired : " + isLoginRequired);
		LogGEN.writeTrace("COB_Log", "password : " + password);
		
		
		xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(inputxml);
		
		customerID = xmlDataParser.getValueOf("CUST_ID");
		LogGEN.writeTrace("COB_Log", "customerID : " + customerID);
		
		wi_name = xmlDataParser.getValueOf("wi_name");
		LogGEN.writeTrace("COB_Log", "wi_name : " + wi_name);
		
		custAccNo= xmlDataParser.getValueOf("custAccountNumber");
		LogGEN.writeTrace("COB_Log", "custAccNo : " + custAccNo); 
		
		bpmMakerID = xmlDataParser.getValueOf("bpmMakerID");
		LogGEN.writeTrace("COB_Log", "bpmMakerID : " + bpmMakerID);
		
		bpmCheckerID = xmlDataParser.getValueOf("bpmCheckerID");
		LogGEN.writeTrace("COB_Log", "bpmCheckerID : " + bpmCheckerID); 
		
		imageType = xmlDataParser.getValueOf("imageType");
		LogGEN.writeTrace("COB_Log", "imageType : " + imageType);
		
		imageBinaryData = xmlDataParser.getValueOf("imageBinaryData");
		LogGEN.writeTrace("COB_Log", "imageBinaryData : " + imageBinaryData);
		
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);		
		LogGEN.writeTrace("COB_Log", "sDate : " + sDate);
		
//		Date d1 = new Date();
//		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		String sDate1 = dateFormat.format(d);		
		LogGEN.writeTrace("COB_Log", "sDate1 : " + sDate1);
		
		// Header Type Object
		HeaderType headerType = new HeaderType();
		// Populating Header Type Object
		headerType.setUsecaseID("1234");
		headerType.setServiceName("ModCustAcctDetails");
		headerType.setVersionNo("1.0");
		headerType.setServiceAction("Modify");
		headerType.setCorrelationID("");
		headerType.setSysRefNumber(getUniqueNo());
		headerType.setSenderID(webHandler.setSenderId(xmlDataParser.getValueOf("SENDERID")));
		headerType.setConsumer("");
		headerType.setReqTimeStamp(sDate);		
		headerType.setUsername(bpmCheckerID);
		headerType.setCredentials(bpmCheckerID);
		
		LogGEN.writeTrace("COB_Log", "Header Populated");

		ModCustAcctDetailsReq_type0 modCustAccDetailReqType0 = new ModCustAcctDetailsReq_type0();
		
		modCustAccDetailReqType0.setMaintenanceType("customerSignatureUpdate");
		modCustAccDetailReqType0.setMaintenanceOption("A");
		modCustAccDetailReqType0.setCustomerId(customerID);
		//modCustAccDetailReqType0.setCustAccountNumber(custAccNo);
		modCustAccDetailReqType0.setUpdateSerialNo("");
		modCustAccDetailReqType0.setMakerId(bpmMakerID);
		modCustAccDetailReqType0.setCheckerId(bpmCheckerID);	
		
		LogGEN.writeTrace("COB_Log", "ModCustAcctDetailsReq_type0 Populated");
		
		// Creating Object of CustomerSignatureUpdateType1
		CustomerSignatureUpdate_type1 custSigUpdateType1 = new CustomerSignatureUpdate_type1();		
		// Populating CustomerSignatureUpdateType1
		
		
		//com.newgen.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1 sigchoice=new com.newgen.stub.modifyCustSig.ModCustAcctDetailsReqChoice_type1();

		
		
		custSigUpdateType1.setImageType("S");
		custSigUpdateType1.setImageAddDate(sDate1);
		custSigUpdateType1.setImageBinaryData(imageBinaryData);
		LogGEN.writeTrace("COB_Log", "CustomerSignatureUpdate_type1 Populated");			
		
		ModCustAcctDetailsReqChoice_type1 modCustAccDetailReqChoiceType1 = new ModCustAcctDetailsReqChoice_type1();
		
		modCustAccDetailReqChoiceType1.setCustomerSignatureUpdate(custSigUpdateType1);
		
		modCustAccDetailReqType0.setModCustAcctDetailsReqChoice_type1(modCustAccDetailReqChoiceType1);
		
		// Creating Object of ModifyCustomerAccountDetailRequestMessage
		ModCustAcctDetailsReqMsg modCustAccDetailReqMes = new ModCustAcctDetailsReqMsg();
		// Populating object of ModifyCustomerAccountDetailRequestMessage with Header Type Object
		modCustAccDetailReqMes.setHeader(headerType);
		// Populating object of ModifyCustomerAccountDetailRequestMessage with ModifyCustomerAccountDetailRequestType0
		modCustAccDetailReqMes.setModCustAcctDetailsReq(modCustAccDetailReqType0);
		
		// Creating customStub object of type modifyCustSig
		modifyCustSig customStub = null;
		try
		{
			customStub = new modifyCustSig(wsdlPath);
			inputSOAPxml = customStub.getinputXML(modCustAccDetailReqMes);	
			LogGEN.writeTrace("COB_Log", "InputSOAPxml : " + inputSOAPxml);	
			
			outputxml = customStub.modCustAcctDetails_Oper(modCustAccDetailReqMes);		
		}
		catch (AxisFault e)
		{
			LogGEN.writeTrace("COB_Log", "Exception while creating stub object of modifyCustSig");
			e.printStackTrace();
		} 
		catch (RemoteException e)
		{		
			LogGEN.writeTrace("COB_Log", "Exception while getting InputSOAPxml");	
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("COB_Log", "Finally block w.r.t calling customsub");	
		}
		
		LogGEN.writeTrace("COB_Log", "outputxml : " + outputxml);	
		
		try
		{
			WebServiceHandler webHandler = null;
			webHandler = new WebServiceHandler();	
			try {
				 webHandler.readCabProperty("JTS");
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
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
				
				//DBConnection con=new DBConnection();
				  /**
				  * //Added By Harish For inserting original mssg
				  * Date			:	17 Mar 2015
				  * Description 	:	Replace execute with executeClob
				  */
			    String sOrg_Output_Add_Cust=outputxml;
			    String inputXml=inputSOAPxml;
			    String winame=xmlDataParser.getValueOf("WIName");
				String Query="insert into usr_0_cust_creation_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
				"and randomnumber=''),'SignatureUpload',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
				"?,sysdate,'"+  headerType.getReturnCode() + "')";
				LogGEN.writeTrace("Log","ADD CASA Account Query : finally :"+Query);
				//LogGEN.writeTrace("Log","sOrg_Output : finally :"+sOrg_Output_Add_Cust);
			 try {
				 con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),sOrg_Output_Add_Cust.replaceAll("'", "''"));
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		catch(Exception ee)
		{
			
		}
		
		
		
		
		return outputxml;
		
	/*	LogGEN.writeTrace("Log","test1 : " + test1);
		
		String returnCode = outputxml.substring(test1.indexOf("<ns1:returnCode>")+16,test1.indexOf("</ns1:returnCode>"));
		LogGEN.writeTrace("Log","returnCode : " + returnCode);
    	
		String errorDetail = test1.substring(test1.indexOf("<ns1:errorDetail>")+17,test1.indexOf("</ns1:errorDetail>"));    	
    	LogGEN.writeTrace("Log","errorDetail : " + errorDetail);
    	   	
    	String sOutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		"<Output>"+
			"<Option>ADCB_ADDCUST</Option>" +
			"<returnCode>"+test1.substring(test1.indexOf("<ns1:returnCode>")+16,test1.indexOf("</ns1:returnCode>"))+"</returnCode>" +
			"<errorDescription>"+test1.substring(test1.indexOf("<ns1:errorDetail>")+17,test1.indexOf("</ns1:errorDetail>"))+"</errorDescription>";				
    	  	sOutput+="<Reason>"+test1.substring(test1.indexOf("<ns0:reason>")+12,test1.indexOf("</ns0:reason>")) +"</Reason>"+
    	  	"<imageType>"+test1.substring(test1.indexOf("<ns0:imageType>")+12,test1.indexOf("</ns0:imageType>")) +"</imageType>"+
    	"<Status>"+test1.substring(test1.indexOf("<ns0:status>")+12,test1.indexOf("</ns0:status>")) +"</Status></Output>";
    	  	LogGEN.writeTrace("Log","sOutput : " + sOutput);*/	
	}	
}
