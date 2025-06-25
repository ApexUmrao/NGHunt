package com.newgen.cbg.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.request.AttributeDetails;
import com.newgen.cbg.request.Attributes;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.EnquiryParser;
import com.newgen.cbg.utils.GenerateSecurityToken;
import com.newgen.omni.wf.util.xml.XMLParser;

public class I_Event implements IEventHandler
{
	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception
	{
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside I_Event:dispatchEvent");
		String WI_NAME = paramCoreEvent.getWI_NAME();
		String stageId = paramCoreEvent.getStageId();
		String sysRefNo = paramCoreEvent.getSysRefNo();
		String sourcingCenter = paramCoreEvent.getSourcingCenter();
		String sourcingChannel = paramCoreEvent.getSourcingChannel();
		String applicationName = paramCoreEvent.getApplicationName();
		String language = paramCoreEvent.getLanguage();


		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SourcingChannel :"+sourcingChannel+", StaeID :"+stageId+", WI_NAME :"+WI_NAME);

		if("ADCBCCSSO".equalsIgnoreCase(sourcingChannel)){
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO,"Going in ADCB CCSO EEvent class");
	//		return new CCSSO_IEvent().dispatchEvent(paramCoreEvent);
		}

		HashMap<String, String> attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
		String securityToken = attributeMap.get("SECURITY_TOKEN");
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "SECURITY_TOKEN : "+securityToken);



		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();

		if("HAPI".equals(sourcingChannel) && "0".equals(stageId) && !attributeMap.containsKey("CUSTOMER_MOBILE_NO")){
			if(!GenerateSecurityToken.validatekey(WI_NAME, attributeMap.get("SECURITY_TOKEN"))){
				responseObj.setWI_NAME(WI_NAME);
				responseObj.setLanguage(language);
				responseObj.setStatusCode("1");
				responseObj.setStatusMessage("SECURITY TOKEN NOT VALID");
				responseObj.setStage(stageId);
				responseObj.setApplicationName(applicationName);
				responseObj.setSYSREFNO(sysRefNo);
				return responseObj;	
			}
		}

		String outputXML = APCallCreateXML.APSelect("SELECT IBAN, (CASE WHEN IBAN IS NOT NULL  THEN 'Approved'  WHEN IBAN IS NULL AND STAGE_ID IN (-1,-5) THEN 'UnderReview'  WHEN IBAN IS NULL AND STAGE_ID IN (-3,-4) "
				+ "THEN 'Rejected'  WHEN IBAN IS NULL AND STAGE_ID IN (0,1,2,3,4,5,6,601) THEN 'InProgress'  ELSE '' END) AS STATUS "
				+ "FROM EXT_CBG_CUST_ONBOARDING WHERE WI_NAME=N'"+WI_NAME+"'");
		XMLParser xp = new XMLParser(outputXML);
		int totalRetrieved = Integer.parseInt(xp.getValueOf("TotalRetrieved"));
		if(totalRetrieved==0){
			responseObj.setWI_NAME(WI_NAME);
			responseObj.setLanguage(language);
			responseObj.setStatusCode("1");
			responseObj.setStatusMessage("NO DATA FOUND");
			responseObj.setStage(stageId);
			responseObj.setApplicationName(applicationName);
			responseObj.setSYSREFNO(sysRefNo);
			return responseObj;	
		}

		String record = xp.getValueOf("Records");
		record = record.replace("&", "&amp;");
		HashMap<String,String> fieldVal = EnquiryParser.parseXML("<?xml version=\"1.0\"?>"+record);
		Attributes[] ata = new Attributes[fieldVal.size()]; 
		Set<Map.Entry<String,String>> fieldValSet = fieldVal.entrySet();
		int i = 0;
		//Attributes array 
		for(Entry<String,String> entry:fieldValSet){
			Attributes a = new Attributes();
			a.setAttributeKey((String) entry.getKey());
			String temp = (((String) entry.getValue()));
			if(temp != null){
				temp = temp.replace("&amp;", "&");
			}
			a.setAttributeValue(temp);
			ata[i] = a;
			i++;
		}
		if(WI_NAME == null || (WI_NAME.equals(""))){
			WI_NAME = fieldVal.get("WI_NAME");
		}

		ApplicationAttributes[] aaa = new ApplicationAttributes[1];
		AttributeDetails[] ada = new AttributeDetails[1];

		//Attribute Details array
		AttributeDetails ad = new AttributeDetails();
		ad.setAttributes(ata);
		ada[0] = ad;

		//Application Attributes
		ApplicationAttributes aa = new ApplicationAttributes();
		aa.setAttributeDetails(ada);
		aaa[0] = aa;

		responseObj.setApplicationAttributes(aaa);
		responseObj.setWI_NAME(WI_NAME);
		responseObj.setLanguage(fieldVal.get("PREFERRED_LANGUAGE"));
		responseObj.setLeadNumber(fieldVal.get("LEAD_REF_NO"));
		responseObj.setStatusCode("0");
		responseObj.setStage(fieldVal.get("STAGE_ID"));
		responseObj.setApplicationName(applicationName);
		responseObj.setSYSREFNO(sysRefNo);
		return responseObj;	
	}
}