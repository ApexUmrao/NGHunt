package com.newgen.dscop.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.DBConnection;
import com.newgen.dscop.client.LogGEN;
import com.newgen.dscop.client.WebServiceConfig;
import com.newgen.dscop.client.XMLParser;

public class DataServicesServerClient extends DSCOPServiceHandler  {

	protected String sWSDLPath = "";
	protected static String sCabinet = "";
	protected String sUser = "";
	protected String sPassword = "";
	protected boolean sLoginReq = false;
	protected static long lTimeOut = 30000;
	String xmlInput = "";
	String runBatchOutput = null;
	String dburl="";
	String dbuser="";
	String dbpass="";
	String sOutput= "";


	
	public String dataServices(String sInputXML) 
	{
		LogGEN.writeTrace("CBG_Log", "Fuction called dataServices");
		LogGEN.writeTrace("CBG_Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		String sReturnCode = "";
		String sErrorDesc = "";
		DSCOPServiceHandler sHandler = new DSCOPServiceHandler();
		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String sDate = dateFormat.format(d);
		try
		{
			LogGEN.writeTrace("CBG_Log", "inside DataServicesServer try block");
			String wsdl=loadWSDLDtls(sHandler);
			String ref_no = xmlDataParser.getValueOf("Ref_No");
			String senderId = xmlDataParser.getValueOf("SenderId");

			String username = xmlDataParser.getValueOf("username");
			String password = xmlDataParser.getValueOf("password");
			String cms_system = xmlDataParser.getValueOf("cms_system");
			String cms_authentication = xmlDataParser.getValueOf("cms_authentication");
			LogGEN.writeTrace("CBG_Log", "DataServices_Server LogonRequest: " + username + "****" +password+ "****" + cms_system + "****" +cms_authentication);

			String inputXML1 = generateLogonXml(username, password, cms_system ,cms_authentication);
			String LogonOutput = executesAPI(wsdl, "function=Logon", inputXML1);
			xmlInput = inputXML1;
//			LogGEN.writeTrace("CBG_Log", "DataServices_Server LogonOutput: " + LogonOutput);

			XMLParser xp1 = new XMLParser(LogonOutput);
			String SessionID = xp1.getValueOf("SessionID");
			LogGEN.writeTrace("CBG_Log", "DataServices_Server SessionID: " + SessionID);

			if(SessionID!=null && !SessionID.equals("")){
				LogGEN.writeTrace("CBG_Log", "Successful Result");

				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<Output>"+
						"<Option>DataServices_Server</Option>" +
						"<SessionID>"+ SessionID +"</SessionID>";

				String jobName = xmlDataParser.getValueOf("jobName");
				String repoName = xmlDataParser.getValueOf("repoName");
				String inputXML2 = generateRunBatchXml(SessionID, jobName, repoName );
				xmlInput = xmlInput + "##" + inputXML2;
				String runBatchOutput = executesAPI(wsdl, "jobAdmin=Run_Batch_Job", inputXML2);	
				XMLParser xp2 = new XMLParser(runBatchOutput);

				String pid = xp2.getValueOf("pid");
				String cid = xp2.getValueOf("cid");
				String rid = xp2.getValueOf("rid");
				String RepoName = xp2.getValueOf("repoName");
				sErrorDesc = xp2.getValueOf("errorMessage");


				sOutput += "<Cid>"+cid+"</Cid>" +
						"<Pid>"+pid+"</Pid>" +
						"<Rid>"+rid+"</Rid>" +
						"<RepoName>"+RepoName+"</RepoName>" +
						"<ErrorMessage>"+sErrorDesc+"</ErrorMessage>";
				sOutput += "</Output>";	

				if(Integer.parseInt(cid) >= 0 ){
					sReturnCode = "0";
				}else{
					sReturnCode = "1";
				}

			}else{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DataServices_Server</Option><returnCode>1</returnCode>"
						+ "<errorDescription>Error in retrieving the Session ID.</errorDescription><Status>Failure</Status><Reason>Error</Reason>"
						+ "<td>Error.</td></Output>";
			}

		}
		catch (Exception e)
		{
			LogGEN.writeTrace("CBG_Log", "Error in Web Service :"+e.toString());
			LogGEN.writeTrace("CBG_Log", "Error Trace in Web Service :"+e.getStackTrace());
			sReturnCode="1";
			sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Output><Option>DataServices_Server</Option><returnCode>-1</returnCode>"
					+ "<errorDescription>"+sErrorDesc+"</errorDescription><td>Error.</td></Output>";
			e.printStackTrace();
		}
		finally
		{
			LogGEN.writeTrace("CBG_Log","inside finally block");	
			LogGEN.writeTrace("CBG_Log","outputXML.trim().length() :"+sOutput.trim().length());				
			if(sOutput.trim().length()<1)
			{
				sOutput="<?xml version=\"1.0\" encoding=\"utf-8\"?><Option>Data Services Server</Option><Output><returnCode>"+sReturnCode+"</returnCode><errorDescription>"+sErrorDesc+"</errorDescription><td>Unable to modify credit limit </td></Output>";
			}			
			String Status="";
			if(sReturnCode.equalsIgnoreCase("0") || sReturnCode.equalsIgnoreCase("2"))
				Status="Success";
			else
				Status="Failure";
			String inputXml=xmlInput;
			loadJSTDtls(sHandler,"JTS");			
			LogGEN.writeTrace("CBG_Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");
			String call_type=xmlDataParser.getValueOf("DSCOPCallType");
			//sCabinet=xmlDataParser.getValueOf("EngineName");
			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("CBG_Log", "winame--"+winame+"||sessionID--"+sessionID+"||call_type--"+call_type);
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
				DBConnection con=new DBConnection();
				String Query="insert into USR_0_DSCOP_WS_AUDIT(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
						"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
						"?,sysdate,'"+  Status + "')";
				LogGEN.writeTrace("CBG_Log"," Add  Query : finally :"+Query);
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),runBatchOutput.replaceAll("'", "''"));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return sOutput;
	}

	private String loadWSDLDtls(DSCOPServiceHandler sHandler){
		try {
			//sHandler.readCabProperty("ETL_DataService");
			LogGEN.writeTrace("CBG_Log", "Reading wsconfig...");
			ArrayList<String>  wsConfig = WebServiceConfig.getInstance().getCBGWSConfig().get("ETL_DataService");
			LogGEN.writeTrace("CBG_Log", "ETL_DataService WebServiceConfig Map : "  +wsConfig.toString());
			sWSDLPath=(String)wsConfig.get(0);
			sCabinet=(String)wsConfig.get(1);
			sUser=(String)wsConfig.get(2);
			sLoginReq=Boolean.valueOf((String)wsConfig.get(3));
			sPassword=(String)wsConfig.get(4);
			lTimeOut=Long.valueOf((String)wsConfig.get(5));
			LogGEN.writeTrace("CBG_Log", "ETL_DataService WSDL PATH: "+sWSDLPath);
			LogGEN.writeTrace("CBG_Log", "ETL_DataService CABINET: "+sCabinet);
			LogGEN.writeTrace("CBG_Log", "ETL_DataService USER: "+sUser);
			LogGEN.writeTrace("CBG_Log", "ETL_DataService PASSWORD: "+sPassword);
			LogGEN.writeTrace("CBG_Log", "ETL_DataService LOGIN_REQ: "+sLoginReq);
			LogGEN.writeTrace("CBG_Log", "ETL_DataService TIME_OUT: "+lTimeOut);
			return sWSDLPath;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return "";
	}

	private void loadJSTDtls(DSCOPServiceHandler sHandler,String tagName){
		try {
			//sHandler.readCabProperty(tagName);
			dburl=(String)currentCabPropertyMap.get("DBURL");
			dbuser=(String)currentCabPropertyMap.get("USER");
			dbpass=(String)currentCabPropertyMap.get("PASS");
			LogGEN.writeTrace("CBG_Log", "dburl---"+dburl+"||dbuser--"+dbuser+"||dbpass--"+dbpass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String executesAPI(String url, String soapAction, String inputXML) throws Exception{
		LogGEN.writeTrace("CBG_Log", "inside executesAPI ");
		String outputXML="";
		HttpURLConnection conn=null;
		try {
			LogGEN.writeTrace("CBG_Log", "inside try Block executesAPI ");
			byte[] buffer = new byte[inputXML.length()];
			buffer = inputXML.getBytes();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			bout.write(buffer);
			byte [] b = bout.toByteArray();
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", String.valueOf(b.length));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8"); 
			conn.setRequestProperty("SOAPAction", soapAction);
			conn.setRequestMethod("POST");
			OutputStream os = conn.getOutputStream();
			LogGEN.writeTrace("CBG_Log", "OutputStream : " +os);
			os.write(b);
			os.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML=out;
			}
			LogGEN.writeTrace("CBG_Log", "DataServicesServer outputXML: "+outputXML);
		} catch (MalformedURLException e) {
			LogGEN.writeTrace("CBG_Log", "Exception in MalformedURLException : " +e);
			e.printStackTrace();
		}catch(IOException e) {
			LogGEN.writeTrace("CBG_Log", "Exception in IOException : " +e);
			e.printStackTrace();
		}
		finally{
			LogGEN.writeTrace("CBG_Log", "inside Finally Block " );
			if(conn!=null){
				conn.disconnect();
				LogGEN.writeTrace("CBG_Log", "disconnect connection : " );
			}
		}
		LogGEN.writeTrace("CBG_Log", "outputXML:  "+outputXML );
		return outputXML;
	}

	private String generateLogonXml(String username, String password, String cms_system ,String cms_authentication){

		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://www.businessobjects.com/DataServices/ServerX.xsd\">").append("\n")
		.append("<soap:Header/>").append("\n")
		.append("<soap:Body>").append("\n") 
		.append("<ser:LogonRequest>").append("\n")
		.append("<username>"+username+"</username>").append("\n")
		.append("<password>"+password+"</password>").append("\n")   
		.append("<cms_system>"+cms_system+"</cms_system>").append("\n")		         
		.append("<cms_authentication>"+cms_authentication+"</cms_authentication>").append("\n")
		.append("</ser:LogonRequest>").append("\n")   
		.append("</soap:Body>").append("\n")         
		.append("</soap:Envelope>").append("\n"); 
		LogGEN.writeTrace("CBG_Log", "DataServicesServer generateLogonXml: "+inputXml.toString());
		return inputXml.toString();
	}

	private String generateRunBatchXml(String SessionID, String jobName, String repoName ){

		StringBuilder inputXml = new StringBuilder();
		inputXml.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://www.businessobjects.com/DataServices/ServerX.xsd\">").append("\n")
		.append("<soapenv:Header>").append("\n")
		.append("<ser:session>").append("\n") 
		.append("<SessionID>"+SessionID+"</SessionID>").append("\n")
		.append("</ser:session>").append("\n")   
		.append("</soapenv:Header>").append("\n")		         
		.append("<soapenv:Body>").append("\n")
		.append("<ser:RunBatchJobRequest>").append("\n")         
		.append("<jobName>"+jobName+"</jobName>").append("\n")
		.append("<repoName>"+repoName+"</repoName>").append("\n")         
		.append("</ser:RunBatchJobRequest>").append("\n")
		.append("</soapenv:Body>").append("\n")         
		.append("</soapenv:Envelope>").append("\n");
		LogGEN.writeTrace("CBG_Log", "DataServicesServer generateRunBatchXml: "+inputXml.toString());
		return inputXml.toString();

	}
}
