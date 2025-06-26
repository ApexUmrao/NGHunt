package com.newgen.ao.laps.cache;

import java.util.HashMap;
import java.util.LinkedList;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class ChannelUploadWICache {

	private HashMap<String,LinkedList<String>> uploadMap=new HashMap<String, LinkedList<String>>();
	private LinkedList<String> UploadWIList = new LinkedList<String>();
	private static ChannelUploadWICache instance = new ChannelUploadWICache();

	public static ChannelUploadWICache getInstance() {
		return instance;
	}
	
	private ChannelUploadWICache(){
		getCachedStagechannelList();
	}

	public HashMap<String,LinkedList<String>>  getCachedStagechannelList() {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"calllman is ");
		if (uploadMap.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"channelList is empty ");
			try {
				uploadMap = createStagechannelList();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"Value of uploadMap is " + uploadMap.toString());
			} catch (NGException e) {
				// TODO Auto-generated catch block
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
						"errror" + e);
			}
		}
		return uploadMap;
	}

	private HashMap<String,LinkedList<String>> createStagechannelList()
			throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside createStagechannelList once");
		uploadMap=new HashMap<String, LinkedList<String>>();
		UploadWIList = new LinkedList<String>();
		String outputXML = APCallCreateXML
				.APSelect("select CHANNEL_NAME,PROCESSDEFID, ACTIVITYID, ACTIVITYNAME from CHANNEL_UPLOADWI");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;

		for (int i = 0; i < noOfFields; ++i) {

			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String channelName = xp.getValueOf("CHANNEL_NAME", start, end);
			String PROCESSDEFID = xp.getValueOf("PROCESSDEFID", start, end);
			String ACTIVITYID = xp.getValueOf("ACTIVITYID", start, end);
			String ACTIVITYNAME = xp.getValueOf("ACTIVITYNAME", start, end);
			
			UploadWIList.add(PROCESSDEFID);
			UploadWIList.add(ACTIVITYID);
			UploadWIList.add(ACTIVITYNAME);
			uploadMap.put(channelName, UploadWIList);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
					"Value of channelLists are " + UploadWIList.toString());

		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,
				"inside channelList " + uploadMap.toString());
		return uploadMap;
	}
}
