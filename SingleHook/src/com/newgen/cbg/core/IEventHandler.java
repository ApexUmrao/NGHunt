package com.newgen.cbg.core;

import com.newgen.cbg.response.CBGSingleHookResponse;

public interface IEventHandler {

	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception;
	
}