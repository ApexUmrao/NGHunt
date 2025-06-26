package com.newgen.dscop.expiry;

import com.newgen.omni.wf.util.app.NGEjbClient;
import com.newgen.omni.wf.util.excp.NGException;

public class ExecuteXML
{
	private static NGEjbClient objNGEjbClient = null;

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
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "InputXML ===>" + inputXML);
		String outputXML = objNGEjbClient.makeCall(inputXML);
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"OutputXML ===>" + outputXML);
		return outputXML;
	}

	public static String executeAPWebService(String inputXML, String socketIP, int socketPort) throws NGException
	{
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO, "executeAPWebService InputXML ===>" + inputXML);
		String outputXML = WebServiceSocket.getInstance().connectToSocket(inputXML, socketIP, socketPort);
		CBGLogMe.logMe(CBGLogMe.LOG_LEVEL_INFO,"OutputXML ===>" + outputXML);
		return outputXML;
	}
}


