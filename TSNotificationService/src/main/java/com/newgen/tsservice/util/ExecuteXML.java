package com.newgen.tsservice.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;

public class ExecuteXML
{
	private static NGEjbClient objNGEjbClient = null;
	private static final Logger logger = LogManager.getLogger(ExecuteXML.class);

	public static void init(String appName, String IP, String port) throws NGException
	{
		if(objNGEjbClient == null)
		{
			objNGEjbClient = NGEjbClient.getSharedInstance();
			objNGEjbClient.initialize(IP, port, appName);
		}
	}

	public static String executeXML(String inputXML) throws NGException
	{
		logger.info("ExecuteXML  InputXML  ===> {}",inputXML);
		String outputXML = objNGEjbClient.makeCall(inputXML);
		logger.info("ExecuteXML  OutputXML  ===> {}",outputXML);
		return outputXML;
	}


	
}


