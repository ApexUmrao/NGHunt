package com.newgen.iforms.user.tfo.util;

import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;

public class ExecuteXML
{
	private static NGEjbClient objNGEjbClient = null;
	
	private ExecuteXML() {		
	}

	public static void init(String appName, String iP, String port) throws NGException
	{
		if(objNGEjbClient == null)
		{
			objNGEjbClient = NGEjbClient.getSharedInstance();
			objNGEjbClient.initialize(iP, port, appName);
		}
	}

	public static String executeXml(String inputXml) throws NGException
	{
		return objNGEjbClient.makeCall(inputXml);
	}
	
}


