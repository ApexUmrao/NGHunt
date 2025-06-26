package com.newgen.ao.laps.sourcingchannel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.newgen.ao.laps.cache.CallEntity;
import com.newgen.ao.laps.cache.ChannelCallCache;
import com.newgen.ao.laps.cache.ProcessEventCache;
import com.newgen.ao.laps.cache.StageCallCache;
import com.newgen.ao.laps.callhandler.CallHandler;
import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.response.LapsModifyResponse;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.ao.laps.util.LapsConfigurations;
import com.newgen.ao.laps.util.LapsUtils;
import com.newgen.ao.laps.util.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class M_UAEPASS implements SourcingChannelHandler{

	public LapsModifyResponse resp = new LapsModifyResponse();
	private HashMap<String, String> attributeMap;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,
			String correlationId, String sourcingChannel, String mode, String wiNumber, String processName) {
		try {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try M_UAEPASS");
		    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Inside try M_UAEPASS channelRefNumber :"+channelRefNumber);
		    LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Input Parameter sourcingChannel :"+ sourcingChannel+" mode : "+mode+ " wiNumber : "+wiNumber);
		
			String output = APCallCreateXML.APSelect("SELECT IS_UAE_PASS_AUTH_DONE FROM EXT_AO  WHERE WI_NAME='"+wiNumber+"'");
			XMLParser xp = new XMLParser(output);
			String isUAEAuthDoneExtAO = xp.getValueOf("IS_UAE_PASS_AUTH_DONE");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of IS_UAE_PASS_AUTH_DONE from EXT_AO : "+isUAEAuthDoneExtAO);
			
			String output1 = APCallCreateXML.APSelect("SELECT IS_UAE_PASS_AUTH_DONE FROM ACC_RELATION_REPEATER  WHERE WI_NAME='"+wiNumber+"'");
			XMLParser xp1 = new XMLParser(output1);
			String isUAEAuthDoneAccRel = xp1.getValueOf("IS_UAE_PASS_AUTH_DONE");	
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of IS_UAE_PASS_AUTH_DONE from ACC_RELATION_REPEATER : "+isUAEAuthDoneAccRel);
			if(isUAEAuthDoneExtAO.equalsIgnoreCase("P") || isUAEAuthDoneAccRel.equalsIgnoreCase("P")){
				String output2 = APCallCreateXML.APSelect("SELECT STATUS FROM BPM_COP_LEAD_DETAILS WHERE WI_NAME='"+wiNumber+"'");
				XMLParser xp2 = new XMLParser(output2);			
				String status = xp2.getValueOf("STATUS");
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Value of Status from BPM_COP_LEAD_DETAILS : "+status);				
				if(status.equalsIgnoreCase("Processed")){

					APCallCreateXML.APUpdate("EXT_AO", "IS_UAE_PASS_AUTH_DONE", "'Y'", "WI_NAME = '"+wiNumber+"'", sessionId);
					APCallCreateXML.APUpdate("ACC_RELATION_REPEATER", "IS_UAE_PASS_AUTH_DONE", "'Y'", "WI_NAME = '"+wiNumber+"'", sessionId);
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Status  is Processed so updated the flag IS_UAE_PASS_AUTH_DONE: Y ");
					ProdCreateXML.WMCompleteWorkItem(sessionId, wiNumber, 1);
				}else {
					LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,"Workitem is not in Processed Status "+wiNumber);
				}
			}
		} catch (Exception e) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, "exception in M_UAEPASS dispatchEvent: ");
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_ERROR, e);
		}
		return resp;
	}	
	
	
}
