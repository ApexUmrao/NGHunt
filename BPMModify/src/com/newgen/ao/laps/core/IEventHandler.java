package com.newgen.ao.laps.core;

import com.newgen.ao.laps.response.LapsModifyResponse;



public interface IEventHandler {

	public LapsModifyResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception;
	
}