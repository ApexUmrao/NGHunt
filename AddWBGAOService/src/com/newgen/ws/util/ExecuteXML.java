package com.newgen.ws.util;

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
import com.newgen.ws.service.AddWBGAOService;

public class ExecuteXML
{
	private static NGEjbClient objNGEjbClient = null;
	protected static Logger log = Logger.getLogger(AddWBGAOService.class);

	public static void init(String appName, String IP, String port) throws NGException
	{		
		log.debug("objNGEjbClient start:" );		

		if(objNGEjbClient == null)
		{
			log.debug("objNGEjbClient start inside" );		
			objNGEjbClient = NGEjbClient.getSharedInstance();
			log.debug("objNGEjbClient middle inside" );		
			objNGEjbClient.initialize(IP, port, appName);
			log.debug("objNGEjbClient end inside" );		

		}
	}

	public static String executeXML(String inputXML) throws NGException
	{
		log.debug("InputXML ===>" + inputXML);
		String outputXML="";
		try {
			outputXML = objNGEjbClient.makeCall(inputXML);
			log.debug("OutputXML ===>" + outputXML);
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
			log.debug("URL ..."+url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			log.debug("conn.getResponseCode()===> "+conn.getResponseCode());
			log.debug(" HttpURLConnection.HTTP_CREATED===> "+ HttpURLConnection.HTTP_OK);
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			log.debug("Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML=out;
				log.debug("RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			log.debug( "RestAPI exception1===> "+e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			log.debug( "RestAPI exception2===> "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML;
	}
}


