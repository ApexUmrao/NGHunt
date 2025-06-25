package com.newgen.cbg.testjourney;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

//import com.newgen.cbg.calls.UpdateWIExpiryDtls;
import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.AttributeDetails;
import com.newgen.cbg.request.Attributes;
import com.newgen.cbg.request.CBGSingleHookRequest;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.EnquiryParser;
import com.newgen.omni.wf.util.xml.XMLParser;

public class TJ_Event  implements IEventHandler {

	HashMap<String, String> attributeMap = null;

	@Override
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside TJ_Event:dispatchEvent");
		String sessionId = paramCoreEvent.getSessionId();
		String stageId = paramCoreEvent.getStageId();
		String WI_NAME = paramCoreEvent.getWI_NAME();
		String sysRefNo = paramCoreEvent.getSysRefNo();
		String language = "AR";//paramCoreEvent.getLanguage();
		String sourcingCenter = paramCoreEvent.getSourcingCenter();
		String sourcingChannel = paramCoreEvent.getSourcingChannel();
		String applicationName = paramCoreEvent.getApplicationName();
		String deviceID = paramCoreEvent.getDeviceID();
		String deviceIP = paramCoreEvent.getDeviceIP();
		String OSType = paramCoreEvent.getOSType();
		String OSVersion = paramCoreEvent.getOSVersion();
		String appVersion = paramCoreEvent.getAppVersion();
		String deviceModal = paramCoreEvent.getDeviceModal();
		String  RequestDateTime= paramCoreEvent.getRequestedDateTime();
		String applicationJourney = paramCoreEvent.getApplicationJourney();
		String applicationVersion = paramCoreEvent.getApplicationVersion();
		CBGSingleHookRequest request = paramCoreEvent.getRequest();
		HashMap<String, String> defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();
		if(stageId.equals("602") || stageId.equals("603")){
			TF_Event event = new TF_Event();
			CBGSingleHookResponse responseObj = event.dispatchEvent(new CoreEvent(request, sessionId, stageId, WI_NAME, sysRefNo, language, applicationName, RequestDateTime, sourcingCenter, sourcingChannel,deviceID,deviceIP, OSType, OSVersion, appVersion, deviceModal, applicationJourney, applicationVersion));
			return responseObj;
		}
		String outputXML = APCallCreateXML.APSelect("SELECT RESXML FROM USR_0_CBG_TEST_J_STAGE_RES WHERE STAGEID = "+ stageId + " AND EVENT_MODE ='"+ request.getMODE()+"' AND SOURCING_CHANNEL='"+sourcingChannel+"' AND JOURNEY_TYPE='CASA'");
		XMLParser xp = new XMLParser(outputXML);
		String statusMessage = xp.getValueOf("statusMessage");
		String wiName = xp.getValueOf("WI_NAME");
		String wsName = xp.getValueOf("WSName");
		String nextStage = xp.getValueOf("nextStage");
		if(stageId.equals("0")){
			String values = "'"+wiName+"',-4,SYSDATE, sysdate + interval '15' minute,1";
			APCallCreateXML.APInsert("USR_0_CBG_EXPIRY_EVENTS", "WI_NAME,STAGE_ID,ENTRY_DATETIME,VALID_TILL,EXPIRY_SEQ", values, sessionId);
		}
		if(stageId.equals("101")){
	//		UpdateWIExpiryDtls call = new UpdateWIExpiryDtls(defaultAttributeMap, sessionId, stageId, WI_NAME, false, null);
	//		call.call();
		}

		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();

		String attributeDetails = xp.getValueOf("attributeDetails");
		if(attributeDetails != null && !attributeDetails.equals("")){
			attributeDetails = attributeDetails.replace("&", "&amp;");
			HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+attributeDetails);
			ApplicationAttributes[] aaa = new ApplicationAttributes[1];
			AttributeDetails[] ada = new AttributeDetails[1];
			Attributes[] ata = new Attributes[fieldVal.size()]; 
			Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
			int i = 0;
			//Attributes array 

			for(Entry entry:fieldValSet){
				Attributes a = new Attributes();
				a.setAttributeKey((String) entry.getKey());
				String temp = (((String) entry.getValue()));
				if(temp != null){
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"value with &amp; "+ temp);
					temp = temp.replace("&amp;", "&");
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"value with &amp; after "+ temp);
				}
				a.setAttributeValue(temp);
				ata[i] = a;
				i++;
			}

			//Attribute Details array
			AttributeDetails ad = new AttributeDetails();
			ad.setAttributes(ata);
			ada[0] = ad;

			//Application Attributes
			ApplicationAttributes aa = new ApplicationAttributes();
			aa.setAttributeDetails(ada);
			aaa[0] = aa;

			responseObj.setApplicationAttributes(aaa);
		}
		responseObj.setStatusCode("0");
		responseObj.setStatusMessage(statusMessage);
		responseObj.setWI_NAME(wiName);
		responseObj.setWSName(wsName);
		responseObj.setStage(stageId);
		responseObj.setApplicationName(applicationName);
		responseObj.setLanguage(language);
		responseObj.setSYSREFNO(sysRefNo);
		responseObj.setNextStage(nextStage);
		responseObj.setApplicationJourney(applicationJourney);
		responseObj.setApplicationVersion(applicationVersion);
		return responseObj;
	}
}