package com.newgen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;

public class ExecuteXML
{
	private static Logger logger = Logger.getLogger("consoleLogger");
	private static NGEjbClient objNGEjbClient = null;
	public static int enablelogging=0;
	public static void init(String appName, String IP, String port) throws NGException
	{		
		writeCustLog(enablelogging, "objNGEjbClient start:" );		

		if(objNGEjbClient == null)
		{
			writeCustLog(enablelogging, "objNGEjbClient start inside" );		
			objNGEjbClient = NGEjbClient.getSharedInstance();
			writeCustLog(enablelogging, "objNGEjbClient middle inside" );		
			objNGEjbClient.initialize(IP, port, appName);
			writeCustLog(enablelogging, "objNGEjbClient end inside" );		

		}
	}

	public static String executeXML(String inputXML) throws NGException
	{
		writeCustLog(enablelogging, "InputXML ===>" + inputXML);
		String outputXML="";
		try {
			outputXML = objNGEjbClient.makeCall(inputXML);
			writeCustLog(enablelogging,"OutputXML ===>" + outputXML);
			return outputXML;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputXML;

		
	}

	public static String executeRestAPI(String url, String inputXML) throws Exception{
		String outputXML="";
		HttpURLConnection conn=null;
		try {
			writeCustLog(enablelogging,"URL ..."+url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			writeCustLog(enablelogging, "conn.getResponseCode()===> "+conn.getResponseCode());
			writeCustLog(enablelogging, " HttpURLConnection.HTTP_CREATED===> "+ HttpURLConnection.HTTP_OK);
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			writeCustLog(enablelogging,"Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML=out;
				writeCustLog(enablelogging, "RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			writeCustLog(enablelogging, "RestAPI exception1===> "+e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			writeCustLog(enablelogging, "RestAPI exception2===> "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML;
	}
	private static void writeCustLog(int enablelogging,String msg)
	{
		logger.info(msg);
	}
}


