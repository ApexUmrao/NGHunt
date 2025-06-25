package com.newgen.ao.laps.util;

import java.io.IOException;

import com.newgen.ao.laps.logger.ArchivalLogger;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;
import com.newgen.wfdesktop.xmlapi.WFCallBroker;

public class ExecuteXML
{
	private static NGEjbClient objNGEjbClient = null;
	private static NGEjbClient objNGEjbClientEdms = null; //added by reyaz 08062023

	public static void init(String appName, String IP, String port) throws NGException
	{
		if(objNGEjbClient == null)
		{
			objNGEjbClient = NGEjbClient.getSharedInstance();
			objNGEjbClient.initialize(IP, port, appName);
		}
	}

	//added by reyaz 08062023
	public static void initEdms(String EdmsAppName, String EdmsIP, String Edmsport) throws NGException
	{
		if(objNGEjbClientEdms == null)
		{
			objNGEjbClientEdms = NGEjbClient.getSharedInstance();
			//objNGEjbClientEdms.initialize(EdmsIP, Edmsport, EdmsAppName);
			objNGEjbClientEdms.initialize(EdmsAppName, EdmsIP, Edmsport);
		}
	}

	public static String executeXML(String inputXML) throws NGException
	{
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "InputXML ===>" + inputXML);
		String outputXML = objNGEjbClient.makeCall(inputXML);
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"OutputXML ===>"+outputXML);
		return outputXML;
	}

	//added by reyaz 08062023
	public static String executeXMLEdms(String inputXML, String jtsIP,String jtsPort) throws NumberFormatException, IOException, Exception
	{
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO, "InputXML ===>" + inputXML);
		String outputXML = WFCallBroker.execute(inputXML,jtsIP,Integer.parseInt(jtsPort),1);
		ArchivalLogger.logMe(ArchivalLogger.LOG_LEVEL_INFO,"OutputXML ===>"+outputXML);
		return outputXML;
	}

	/*public static String executeRestAPI(String url, String inputXML) throws Exception{
		StringBuilder outputXML = new StringBuilder();
		HttpURLConnection conn=null;
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"URL ..."+url);
			URL urlName = new URL(url);
			conn =  (HttpURLConnection) urlName.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");

			OutputStream os = conn.getOutputStream();
			os.write(inputXML.getBytes());
			os.flush();
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "conn.getResponseCode()===> "+conn.getResponseCode());
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, " HttpURLConnection.HTTP_CREATED===> "+ HttpURLConnection.HTTP_OK);
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Output from server ...\n");
			String out ;
			while ((out = br.readLine()) != null) {
				outputXML.append(out);
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "RestAPI output===> "+outputXML);
			}
		} catch (MalformedURLException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "RestAPI exception1===> "+e.getMessage());
		}catch(IOException e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "RestAPI exception2===> "+e.getMessage());
		}
		finally{
			if(conn!=null){
				conn.disconnect();
			}
		}
		return outputXML+"";
	}*/
}


