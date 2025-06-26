package com.newgen.ao.laps.cache;

import java.util.LinkedList;
import java.util.List;

import com.newgen.ao.laps.logger.LapsModifyLogger;
import com.newgen.ao.laps.util.APCallCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;
import com.newgen.omni.wf.util.excp.NGException;

public class SourcingChannelCache {
	private List<String> channelList = new LinkedList<String>();
	private static SourcingChannelCache instance = new SourcingChannelCache();

	public static SourcingChannelCache getInstance() {
		return instance;
	}
	
	private SourcingChannelCache(){
		getCachedStageChannelList();
	}

	public List<String> getCachedStageChannelList() {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO,	"calllman is ");
		if (channelList.isEmpty()) {
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "channelList is empty ");
			try {
				channelList = createStageChannelList();
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of channelList is " + channelList.toString());
			} catch (Exception e) {
				LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "errror" + e);
			}
		}

		return channelList;
	}

	private List<String> createStageChannelList() throws NGException {
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside createStagechannelList once");
		channelList = new LinkedList<String>();
		String outputXML = APCallCreateXML.APSelect("SELECT CHANNEL_NAME FROM CHANNEL_PURPOSE WHERE ISENABLED='Y'");
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "outputxml issss " + outputXML);
		XMLParser xp = new XMLParser(outputXML);
		int start = xp.getStartIndex("Records", 0, 0);
		int deadEnd = xp.getEndIndex("Records", start, 0);
		int noOfFields = xp.getNoOfFields("Record", start, deadEnd);
		int end = 0;
		for (int i = 0; i < noOfFields; ++i) {
			start = xp.getStartIndex("Record", end, 0);
			end = xp.getEndIndex("Record", start, 0);
			String channelName = xp.getValueOf("CHANNEL_NAME", start, end);
			channelList.add(channelName);
			LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "Value of channelLists are " + channelList.toString());
		}
		LapsModifyLogger.logMe(LapsModifyLogger.LOG_LEVEL_INFO, "inside channelList " + channelList.toString());

		return channelList;
	}
}
