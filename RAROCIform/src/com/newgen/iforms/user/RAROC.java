package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.raroc.Initiator;
import com.newgen.iforms.user.raroc.Common;
import com.newgen.iforms.user.raroc.CommonPool;
import com.newgen.iforms.user.raroc.Exit;


public class RAROC extends Common implements IFormListenerFactory{
	
	@Override
	public IFormServerEventHandler getClassInstance(IFormReference iFormReference) {
		String processName = iFormReference.getProcessName();
		String activityName = iFormReference.getActivityName();
		String sMode = iFormReference.getObjGeneralData().getM_strMode();		
         if ("Initiator".equalsIgnoreCase(activityName)) {
			return new Initiator(iFormReference);
		} else if("CommonPool".equalsIgnoreCase(activityName)){
			return new CommonPool(iFormReference);
		} else if("Exit".equalsIgnoreCase(activityName)){
			return new Exit(iFormReference);
		}
		return null;
	}
}

