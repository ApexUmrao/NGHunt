package com.newgen.push.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.push.message.BPMReqLogMe;

public class ExecuteXML
{
	private static NGEjbClient objNGEjbClient = null;

	public static void init(String appName, String IP, String port) throws NGException
	{
		if(objNGEjbClient == null)
		{
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"objNGEjbClient: connection creation starts");
			objNGEjbClient = NGEjbClient.getSharedInstance();
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"objNGEjbClient: IP ==>" + IP+"port ==>"+port
					+"appName ==>"+appName);
			objNGEjbClient.initialize(IP, port, appName);
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"objNGEjbClient: connection creation ends");
		}
	}

	public static String executeXML(String inputXML) throws NGException
	{
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "InputXML ===>" + inputXML);
		String outputXML = objNGEjbClient.makeCall(inputXML);
		BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"OutputXML ===>" + outputXML);
		return outputXML;
	}

	public static String executeRestAPI(String url, String inputXML) throws Exception{
		String outputXML="";
		HttpURLConnection conn=null;
		try {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"URL ..."+url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "conn.getResponseCode()===> "+conn.getResponseCode());
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, " HttpURLConnection.HTTP_CREATED===> "+ HttpURLConnection.HTTP_OK);
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO,"Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML=out;
				BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "RestAPI exception1===> "+e.getMessage());
			e.printStackTrace();
		}catch(IOException e) {
			BPMReqLogMe.logMe(BPMReqLogMe.LOG_LEVEL_INFO, "RestAPI exception2===> "+e.getMessage());
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


