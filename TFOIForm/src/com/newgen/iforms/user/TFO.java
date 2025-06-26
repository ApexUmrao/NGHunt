package com.newgen.iforms.user;

import com.newgen.iforms.custom.IFormListenerFactory;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.tfo.AsgnQueue;
import com.newgen.iforms.user.tfo.Common;
import com.newgen.iforms.user.tfo.ComplianceReferralResponse;
import com.newgen.iforms.user.tfo.CustomerReferralResponse;
import com.newgen.iforms.user.tfo.Initiator;
import com.newgen.iforms.user.tfo.Logistics;
import com.newgen.iforms.user.tfo.PC;
import com.newgen.iforms.user.tfo.PM;
import com.newgen.iforms.user.tfo.PMPCProcessingSystem;
import com.newgen.iforms.user.tfo.PPM;
import com.newgen.iforms.user.tfo.ReadOnly;
import com.newgen.iforms.user.tfo.ReferralCommon;
import com.newgen.iforms.user.tfo.Delivery;
import com.newgen.iforms.user.tfo.Repair;
import com.newgen.iforms.user.tfo.TraydStream;

public class TFO extends Common implements IFormListenerFactory   {

	@Override
	public IFormServerEventHandler getClassInstance(IFormReference iFormReference) {
		String processName = iFormReference.getProcessName();
		String activityName = iFormReference.getActivityName();
		String sMode = iFormReference.getObjGeneralData().getM_strMode();		
		log.info("ProcessName : "+processName);
		log.info("ActivityName : "+activityName);
		log.info("Mode : "+sMode);

		 if("R".equalsIgnoreCase(sMode)||"Archival".equalsIgnoreCase(activityName)){
			return new ReadOnly(iFormReference);
		}else if ("Initiator".equalsIgnoreCase(activityName)) {
			return new Initiator(iFormReference);
		} else if("Logistics Team".equalsIgnoreCase(activityName)){
			return new Logistics(iFormReference);
		} else if("Assignment Queue".equalsIgnoreCase(activityName)){ 
			return new AsgnQueue(iFormReference);
		} else if("PP_MAKER".equalsIgnoreCase(activityName)){ //DART 1129779
			return new PPM(iFormReference);
		} else if("PROCESSING MAKER".equalsIgnoreCase(activityName)){ 
			return new PM(iFormReference);
		} else if("PROCESSING CHECKER".equalsIgnoreCase(activityName)){ 
			return new PC(iFormReference);
		} else if("Treasury".equalsIgnoreCase(activityName) || "TSD".equalsIgnoreCase(activityName) ||
				"LEGAL_TEAM".equalsIgnoreCase(activityName) || "RM".equalsIgnoreCase(activityName) ||
				"CORRESPONDENT_BANK".equalsIgnoreCase(activityName) || "Trade Sales".equalsIgnoreCase(activityName) 
				|| "TB Sales".equalsIgnoreCase(activityName)||
				"COMPLIANCE".equalsIgnoreCase(activityName) || "CUSTOMER_REVIEW".equalsIgnoreCase(activityName)) { 
			return new ReferralCommon(iFormReference);
		} else if("DELIVERY".equalsIgnoreCase(activityName)){
			return new Delivery(iFormReference);
		} else if("PM PROCESSING SYSTEM".equalsIgnoreCase(activityName) 
				||"PC PROCESSING SYSTEM".equalsIgnoreCase(activityName)
				||"Legalization Queue".equalsIgnoreCase(activityName)
				||"SCC".equalsIgnoreCase(activityName)){
			return new PMPCProcessingSystem(iFormReference);
		} else if("Customer Referral Response".equalsIgnoreCase(activityName)){
			return new CustomerReferralResponse(iFormReference);
		} else if("Compliance Referral Response".equalsIgnoreCase(activityName)){
			return new ComplianceReferralResponse(iFormReference);
		} else if("Repair".equalsIgnoreCase(activityName)){
			return new Repair(iFormReference);
		} else if ("Trayd Stream".equalsIgnoreCase(activityName)) {  // Traydstream |reyaz|atp-519|07-10-2024| start
			return new TraydStream(iFormReference);
		}
		return null;
	}

}
