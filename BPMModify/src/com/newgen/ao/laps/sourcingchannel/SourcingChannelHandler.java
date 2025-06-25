package com.newgen.ao.laps.sourcingchannel;

import com.newgen.ao.laps.response.LapsModifyResponse;

public interface SourcingChannelHandler {
	public LapsModifyResponse dispatchEvent(String sessionId, String channelRefNumber,String correlationNo,
			String sourcingChannel, String mode, String wiNumber, String processName);
}
