package com.newgen.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newgen.AESEncryption;

public class TRSDCall extends WebServiceHandler {

	protected String sTrsdCreateWSDLPath="";
	protected String sTrsdInqWSDLPath="";
	protected static String sCabinet="";
	protected String sUser="";
	protected String sPassword="";
	protected boolean sLoginReq=false;
	protected static long lTimeOut = 30000;
	public String callTRSD(String sInputXML)
	{
		LogGEN.writeTrace("Log", "Fuction called TRSD");
		LogGEN.writeTrace("Log", "sInputXML---"+sInputXML);
		XMLParser xmlDataParser = new XMLParser();
		xmlDataParser.setInputXML(sInputXML);
		WebServiceHandler sHandler= new WebServiceHandler();
		try
		{
			/*VersionStub ver=new VersionStub();
			GetVersionResponse r =ver.getVersion();
			System.out.println(r.get_return());*/
			String call_type=xmlDataParser.getValueOf("Calltype");
			if(call_type.equalsIgnoreCase("CALL_TRSD_CREATE")){
				sHandler.readCabProperty("TRSD");	
				sTrsdCreateWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				LogGEN.writeTrace("Log", "WSDL sTrsdCreateWSDLPath PATH---- "+sTrsdCreateWSDLPath);
			}else if(call_type.equalsIgnoreCase("CALL_TRSD_ENQUIRY")){
				sHandler.readCabProperty("CALL_TRSD_ENQUIRY");
				sTrsdInqWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
				LogGEN.writeTrace("Log", "WSDL sTrsdInqWSDLPath PATH---- "+sTrsdInqWSDLPath);
			}

			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String sDate = dateFormat.format(d);
			sTrsdCreateWSDLPath=(String)currentCabPropertyMap.get("WSDL_PATH");
			sCabinet=(String)currentCabPropertyMap.get("CABINET");
			sUser=(String)currentCabPropertyMap.get("USER");
			sLoginReq=Boolean.valueOf((String)currentCabPropertyMap.get("LOGIN_REQ"));
			sPassword=(String)currentCabPropertyMap.get("PASSWORD");
			lTimeOut=Long.valueOf((String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));

			LogGEN.writeTrace("Log", "read Property successfully");
			LogGEN.writeTrace("Log", "WSDL PATH---- "+(String)currentCabPropertyMap.get("WSDL_PATH"));
			LogGEN.writeTrace("Log", "CABINET---- "+(String)currentCabPropertyMap.get("CABINET"));
			LogGEN.writeTrace("Log", "USER---- "+(String)currentCabPropertyMap.get("USER"));
			LogGEN.writeTrace("Log", "PASSWORD---- "+(String)currentCabPropertyMap.get("PASSWORD"));
			LogGEN.writeTrace("Log", "LOGIN_REQ---- "+(String)currentCabPropertyMap.get("LOGIN_REQ"));
			LogGEN.writeTrace("Log", "TIME_OUT---- "+(String)currentCabPropertyMap.get("TIMEOUT_INTERVAL"));
			//String sCustomerID= xmlDataParser.getValueOf("CUST_ID");
			//String ref_no=xmlDataParser.getValueOf("REF_NO");
			//sCabinet=xmlDataParser.getValueOf("EngineName");

			LogGEN.writeTrace("Log", "All Objects created");			
			String Status="Success";
			sHandler.readCabProperty("JTS");	

			String dburl=(String)currentCabPropertyMap.get("DBURL");
			String dbuser=(String)currentCabPropertyMap.get("USER");
			String dbpass=(String)currentCabPropertyMap.get("PASS");


			String inputXml=sInputXML;
			LogGEN.writeTrace("Log", inputXml);
			String winame=xmlDataParser.getValueOf("WiName");
			String sessionID= xmlDataParser.getValueOf("SessionId");

			sCabinet=xmlDataParser.getValueOf("EngineName");

			dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			LogGEN.writeTrace("Log", "TRSD Call:"+xmlDataParser.getValueOf("Calltype"));
			String outputxml="";
			if(call_type.equalsIgnoreCase("CALL_TRSD_CREATE"))
				outputxml=executeRestAPI( sTrsdCreateWSDLPath,  xmlDataParser.getValueOf("data"));
			else
				outputxml=executeRestAPI( sTrsdInqWSDLPath,  xmlDataParser.getValueOf("caseNum"));
			
			try
			{
				dbpass=AESEncryption.decrypt(dbpass);
			}
			catch(Exception e)
			{
				customException("Error While Decrypting password",e);
			}
			DBConnection con=new DBConnection();
			
			String Query="insert into usr_0_other_calls_web_log(winame,userid,request_type,request,requestdatetime,response,responsedatetime,status) values('"+winame+"',( select username from pdbuser a, pdbconnection b   where a.userindex=b.userindex "+
			"and randomnumber='"+ sessionID +"'),'"+call_type+"',?,to_date('"+sDate+"','dd/mm/yyyy hh24:mi:ss'),"+
			"?,sysdate,'"+  Status + "')";
			LogGEN.writeTrace("Log"," Add Cheque Query : finally :"+Query);
			LogGEN.writeTrace("Log","sOrg_Output : finally :"+outputxml);
			try {
				con.executeClob("jdbc:oracle:thin:@"+dburl,dbuser,dbpass,Query,inputXml.replaceAll("'", "''"),outputxml.replaceAll("'", "''"));
			} catch (Exception e2) {

				customException("Error While Executing clob insert script",e2);
			}
			return outputxml;
		}
		catch(Exception e)
		{
			customException("Error While Executing clob insert script",e);
		}
		return "";
	}

	public  String executeRestAPI(String url, String inputXML) throws Exception{
		String outputXML="";
		HttpURLConnection conn=null;
		try {

			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();

			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));


			String out ;
			while ((out = br.readLine()) != null) {
				outputXML+=out;

			}
		} catch (MalformedURLException e) {

		}catch(IOException e) {

		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML;
	}
	
	private void customException(String called, Exception e){
		try {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			LogGEN.writeTrace("Log","Exception occur 0 >>>><<<<<"+called +" >>> \n"+sw.toString() );
		} catch (Exception e1) {
			e.printStackTrace();
		}
	}
}
