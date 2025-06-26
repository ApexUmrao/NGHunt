package com.newgen.event;

import java.io.File;
import java.util.HashMap;

import com.newgen.cclpp.cache.StageCallCache;
import com.newgen.cclpp.callhandler.CallEntity;
import com.newgen.cclpp.callhandler.CallHandler;
import com.newgen.cclpp.calls.UpdateServiceRequest;
import com.newgen.cclpp.logger.CCLDBLogMe;
import com.newgen.cclpp.logger.CCLLogMe;
import com.newgen.cclpp.utils.APCallCreateXML;
import com.newgen.cclpp.utils.ExecuteXML;
import com.newgen.cclpp.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.util.GenerateXml;
import com.newgen.util.Log;

public class CCLRetry extends Log{
	
	public static void retryFailedCalls(String sessionId, String cabinetName, String serverIP, String jndiPort, 
			String processName, String wiName, String sourcingChannel, String mode) 
			throws Exception {
		try {
			logger.info("inside CCL retryFailedCalls >>");
			int returnCode = 0;
//			StringBuilder log4JPropertyFile = new StringBuilder(System.getProperty("user.dir"))
//			.append(System.getProperty("file.separator"))
//			.append("WebServiceConf")
//			.append(System.getProperty("file.separator"))
//			.append("CCL")
//			.append(System.getProperty("file.separator"))
//			.append("CCL_log4j.properties");
//			logger.info("log4JPropertyFile: "+log4JPropertyFile);
//			if(new File(log4JPropertyFile.toString()).exists()){
//				logger.info("log4JPropertyFile path exists ....");
//			}
			//			CallEntity callEntity = new CallEntity();
			//			callEntity.setCallName("UpdateServiceRequest");
						//callEntity.setExecutionType("S");
			//			CallHandler.getInstance().executeCall(callEntity, new HashMap<String, String>(), sessionId, "-1", wiName, 
			//					false);
			HashMap<String, String> defaultMap = new HashMap<String, String>();
			try {
				logger.info("before initializing ==> ");
				CCLLogMe.getInstance();
				CCLDBLogMe.getInstance();
				ExecuteXML.init("WebSphere", serverIP, jndiPort);
				ProdCreateXML.init(cabinetName, 11);
				APCallCreateXML.init(cabinetName, 11);
				logger.info("after initializing ==> ");
				defaultMap = StageCallCache.getReference().getCachedDefaultMap();
				logger.info("fetched defaultMap ==> ");
			} catch (Exception e2) {
				logger.error("exception in calling initializing: ",e2);
			}
			try {
				String sQuery = "select CURRENT_WS from CCL_EXT_TABLE where wi_name = '"+wiName+"'";
				logger.info("query for current WS ==> "+sQuery);
				String outputXMLCurrentWS = GenerateXml.APSelectWithColumnNames(cabinetName,sQuery,sessionId);
				XMLParser xmlDataParser = new XMLParser(outputXMLCurrentWS);
				if(!"Exit".equalsIgnoreCase(xmlDataParser.getValueOf("CURRENT_WS"))){
					UpdateServiceRequest usr = new UpdateServiceRequest(defaultMap, sessionId, "-2", wiName, false, "", "");
					String outputXml = usr.executeCall(new HashMap<String, String>());
					logger.info("UpdateServiceRequest outputXml ==> " + outputXml);
					xmlDataParser = new XMLParser(outputXml);
					returnCode = Integer.parseInt(xmlDataParser.getValueOf("returnCode"));
				}
			} catch (Exception e1) {
				logger.error("exception in calling UpdateServiceRequest: ",e1);
			}
			String finalStatus = "F";
			if(returnCode == 0){
				finalStatus = "Y";
			}
			try {
				String procOutput = GenerateXml.APProcedure_new(sessionId,cabinetName,"BPM_EVENTGEN_UPDATE","'"+wiName+
						"','"+finalStatus+"','"+mode+"'",3);
				logger.info("The OutputXml for PROC Workitem Call = " + procOutput);
			} catch (Exception e) {
				logger.error("exception in calling BPM_EVENTGEN_UPDATE: ",e);
			}
			logger.info("after calling executeCall() >>");
		} catch (Exception e) {
			logger.error("exception in retryFailedCalls: ",e);
		}
	}
}
