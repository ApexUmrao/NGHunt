
package com.newgen.cbg.event;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.newgen.cbg.callhandler.CallEntity;
import com.newgen.cbg.callhandler.CallHandler;
import com.newgen.cbg.core.CoreEvent;
import com.newgen.cbg.core.IEventHandler;
import com.newgen.cbg.logger.DSCOPDBLogMe;
import com.newgen.cbg.logger.DSCOPLogMe;
import com.newgen.cbg.request.ApplicationAttributes;
import com.newgen.cbg.response.CBGSingleHookResponse;
import com.newgen.cbg.utils.APCallCreateXML;
import com.newgen.cbg.utils.DSCOPConfigurations;
import com.newgen.cbg.utils.DSCOPUtils;
import com.newgen.cbg.utils.ProdCreateXML;
import com.newgen.omni.jts.cmgr.XMLParser;

public class C_Event implements IEventHandler {

	boolean prevStageNoGo = false;
	HashMap<String, String> defaultAttributeMap;
	HashMap<String, String> attributeMap = null;

	public CBGSingleHookResponse dispatchEvent(CoreEvent paramCoreEvent) throws Exception {
		
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Inside C_Events:dispatchEvent");
		String sessionId = paramCoreEvent.getSessionId();
		String stageId = paramCoreEvent.getStageId();
		String sysRefNo = paramCoreEvent.getSysRefNo();
		String language = paramCoreEvent.getLanguage();
		String sourcingChannel = paramCoreEvent.getSourcingChannel();
		String applicationName = paramCoreEvent.getApplicationName();
		String deviceID = paramCoreEvent.getDeviceID();
		String deviceIP = paramCoreEvent.getDeviceIP();
		String OSType = paramCoreEvent.getOSType();
		String OSVersion = paramCoreEvent.getOSVersion();
		String appVersion = paramCoreEvent.getApplicationVersion();
		String deviceModal = paramCoreEvent.getDeviceModal();
		String applicationJourney = paramCoreEvent.getApplicationJourney();
		String WIName = "";
		String outputXML ="";
		String	inqFlag="N";
		CBGSingleHookResponse responseObj = new CBGSingleHookResponse();

		defaultAttributeMap = DSCOPUtils.getInstance().requestToDefaultValueMap();
		attributeMap = DSCOPUtils.getInstance().requestToAttributeMap(paramCoreEvent.getRequest().getApplicationAttributes());
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events attributeMap: " + attributeMap);
		String primaryCid = attributeMap.get("PRIMARY_CID");
	    String primaryCard = attributeMap.get("PRIMARY_CARD_NO");
	    String associatedCard = attributeMap.get("ASSOCIATE_CARD");
	    DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events processID:" + primaryCid + "," + primaryCard + "," + associatedCard);

	    if (primaryCid == null || associatedCard == null || primaryCard == null) {
	        return prepareErrorResponse(responseObj, "25", "Mandatory data is not available", applicationName, appVersion, applicationJourney, sysRefNo);
	    }// else if (cardValidator(primaryCard)) {
	    //    return prepareErrorResponse(responseObj, "26", "Card is Betaqti", applicationName, appVersion, applicationJourney, sysRefNo);
	   // }
		setDefaultAttributes(attributeMap, appVersion, sourcingChannel, applicationJourney);

		HashMap<String, String> complexHM = new HashMap<String, String>();
		int processID = Integer.parseInt(defaultAttributeMap.get("DSCOP_PROCESS_ID"));

		outputXML = ProdCreateXML.WFUploadWorkItem(sessionId, "N", new HashMap<String, String>(),
				String.valueOf(DSCOPConfigurations.getInstance().ProcessDefId), complexHM);

		XMLParser xp = new XMLParser(outputXML);
		int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if (mainCode == 0) {
			WIName = xp.getValueOf("ProcessInstanceId");
			attributeMap.put("WI_NAME", WIName);

			String refNo = WIName.split("-")[1];
			refNo = DSCOPUtils.getInstance().removalLeadingZeros(refNo);
			attributeMap.put("APPLICATION_REF_NO", refNo);
			
			
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); 
			c.add(Calendar.MINUTE, 15);
			attributeMap.put("EXPIRY_DATE",new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c.getTime()));
			
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events default_entry:"+ sessionId+","+WIName+","+String.valueOf(DSCOPConfigurations.getInstance().ProcessDefId)+"");
			outputXML = ProdCreateXML.WFSetAttributesNew(sessionId, WIName, 1, attributeMap,
			String.valueOf(DSCOPConfigurations.getInstance().ProcessDefId));
			ProdCreateXML.WMUnlockWorkItem(sessionId, WIName, 1);
			String valdetail = "'"+ primaryCid +"','"+primaryCard+"','"+associatedCard+"','"+WIName+"'";
			APCallCreateXML.APInsert("EXT_DSCOP_EXTENDED","PRIMARY_CID,PRIMARY_CARD,ASSOCIATE_CARD,WI_NAME", valdetail, sessionId);
			DSCOPDBLogMe.logMe(DSCOPDBLogMe.LOG_LEVEL_INFO, WIName, "C_Event", "C_Event","Workitem Created Successfully - " + WIName, sessionId);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events processID: " + processID);
			HashMap<Integer, Integer> eventMap = DSCOPUtils.getInstance().getEventDetector(processID, sourcingChannel,
					appVersion, applicationJourney, Integer.parseInt(stageId));
			int eventID = (Integer) eventMap.keySet().toArray()[0];
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "EXECUTING PARALLEL CALLS OF EVENT : " + eventID);
			List<CallEntity> callArray = DSCOPUtils.getInstance().getAsyncCallEventWise(eventID);
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events callArray: " + callArray);
			DSCOPUtils.getInstance().updateAccRelSno(WIName, sessionId);

			if (callArray != null) {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, callArray.toString());

				for (CallEntity callEntity : callArray) {
					DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Call Name: " + callEntity.getCallName());
					if (callEntity.isMandatory()) {
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events default_entry:"+ primaryCid+","+primaryCard+","+associatedCard+"");
						defaultAttributeMap.put("primaryCid", primaryCid);
						defaultAttributeMap.put("primaryCardNo", primaryCard);
						defaultAttributeMap.put("associatedCard", associatedCard);
						outputXML = CallHandler.getInstance().executeCall(callEntity, defaultAttributeMap, sessionId,
								String.valueOf(eventID), WIName, false);
						DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "Execute Call: outputxml: " + outputXML);
					}
				}

			} else {
				DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events No Parallel calls at Stage: " + stageId);
			}
		} else {
			responseObj.setStatusCode("1");
			responseObj.setStatusMessage("WORKITEM CREATION FAILED");
		}
		ApplicationAttributes[] attr = responseApplicationAttributes(WIName);
		if (attr != null) {
			responseObj.setApplicationAttributes(attr);
		}
		responseObj.setStatusCode("0");
		responseObj.setWI_NAME(WIName);
		responseObj.setWSName("Introduction");
		responseObj.setStage(stageId);
		DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "inside " + stageId);
		responseObj.setNextStage("1");

		outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WIName, 1);
		xp = new XMLParser(outputXML);
		mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
		if(mainCode != 0)
		{
			responseObj.setStatusCode("1");
			responseObj.setStatusMessage("WORKITEM MOVEMENT FAILED");
		}

		String valList = "'"+ WIName +"','"+ OSType +"','"+ OSVersion +"','"+ deviceID +"','"+ appVersion +"','"+ deviceModal +"','"+ language +"','"+ deviceIP +"','"+ sourcingChannel +"',SYSTIMESTAMP";
		APCallCreateXML.APInsert("USR_0_DSCOP_DEVICE_INFO", "WI_NAME, OS_TYPE, OS_VERSION, DEVICE_ID, APP_VERSION, DEVICE_MODEL, LANG_SELECTION, IP_ADDRESS, SOURCE_SYSTEM, TIME_STAMP", valList, sessionId);
		if(inqFlag.equalsIgnoreCase("N")) {

			outputXML = APCallCreateXML.APSelect("SELECT COUNT(1) AS COUNT FROM USR_0_DSCOP_CALL_OUT WHERE WI_NAME = N'"+WIName+"' AND CALL_STATUS IN ('N','F')");
			XMLParser xp1 = new XMLParser(outputXML);
			mainCode = Integer.parseInt(xp1.getValueOf("MainCode"));
			if(mainCode == 0 && Integer.parseInt(xp1.getValueOf("TotalRetrieved")) > 0 && Integer.parseInt(xp1.getValueOf("COUNT")) >0){
					inqFlag="Y";	 
					APCallCreateXML.APUpdate("EXT_DSCOP", "INQ_FLAG,INQ_REJECT_STATUS", "'"+inqFlag+"','CallFail'", " WI_NAME = N'"+ WIName +"'", sessionId);
					responseObj.setStatusCode("30");
					responseObj.setStatusMessage("WORKITEM MOVEMENT FAIL");
					ProdCreateXML.WMCompleteWorkItem(sessionId, WIName, 1);
				}
			}

		if(inqFlag.equalsIgnoreCase("N")) {
			int debitCountLmt = Integer.parseInt(defaultAttributeMap.get("DEBIT_CARD"));
			int creditCountLmt = Integer.parseInt(defaultAttributeMap.get("CREDIT_CARD"));
		    DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "C_Events processID:" + debitCountLmt + "," + creditCountLmt + "," +defaultAttributeMap.get("DEBIT_CARD"));

			int suppLimitCount ;
			String sOutput = "";
			if (associatedCard.equalsIgnoreCase("Debit")) {
				suppLimitCount = debitCountLmt;
				sOutput = APCallCreateXML.APSelect("SELECT e.DORMANT_CUSTOMER,"
						+ " (CASE WHEN e.EIDA_EXP_DATE IS NULL OR to_date(e.EIDA_EXP_DATE, 'DD/MM/YYYY') < SYSDATE THEN 'Y' ELSE 'N' END) AS EIDA_EXPIRED,"
						+ " (CASE WHEN e.PASSPORT_EXP_DATE IS NULL OR to_date(e.PASSPORT_EXP_DATE, 'DD/MM/YYYY') < SYSDATE THEN 'Y' ELSE 'N' END) AS PASSPORT_EXPIRED,"
						+ " a.STATUS,b.ACCOUNT_STATUS_CODE,(SELECT COUNT(1)"
						+ " FROM bpm_dscop_debitcard_details"
						+ " WHERE wi_name = e.WI_NAME"
						+ " AND card_type = 'S' and STATUS in (0,1,11) and CARD_NUMBER = '"+primaryCard+"' AND LINKED_ACCOUNT = a.LINKED_ACCOUNT ) AS CARD_COUNT,"
						+ " (SELECT COUNT(1) "
						+ "  FROM ext_dscop "
						+ "  WHERE PRIMARY_CID = '"+primaryCid+"'"
						+ "  AND PRIMARY_CARD_NO = '"+primaryCard+"'"
						+ "  AND ASSOCIATE_CARD = '"+associatedCard+"'"
						+ "  AND LEAD_REF_NO IS NOT NULL"
						+ "  AND stage_name NOT IN ('DISCARD1', 'EXIT','ARCHIVAL') and wi_name != e.WI_NAME) AS TOTAL_COUNT"
						+ " FROM EXT_DSCOP_EXTENDED e LEFT JOIN ( SELECT LINKED_ACCOUNT,STATUS,WI_NAME"
						+ " FROM BPM_DSCOP_DEBITCARD_DETAILS WHERE CARD_NUMBER = '"+primaryCard+"') "
						+ " a ON e.WI_NAME = a.WI_NAME "
						+ " LEFT JOIN BPM_DSCOP_ACCOUNT_DETAILS b ON a.LINKED_ACCOUNT = b.ACCOUNT_NUMBER AND b.WI_NAME = N'"+ WIName +"'"
						+ " WHERE e.WI_NAME = N'"+ WIName +"'");
			}else{
                 suppLimitCount  = creditCountLmt;
                 sOutput =  APCallCreateXML.APSelect("SELECT e.DORMANT_CUSTOMER,"
 						+ " (CASE WHEN e.EIDA_EXP_DATE IS NULL OR to_date(e.EIDA_EXP_DATE, 'DD/MM/YYYY') < SYSDATE THEN 'Y' ELSE 'N' END) AS EIDA_EXPIRED,"
 						+ " (CASE WHEN e.PASSPORT_EXP_DATE IS NULL OR to_date(e.PASSPORT_EXP_DATE, 'DD/MM/YYYY') < SYSDATE THEN 'Y' ELSE 'N' END) AS PASSPORT_EXPIRED,"
 						+ " e.STATUS_PRIMARY_CARD,(SELECT COUNT(1)"
 						+ " FROM bpm_dscop_creditcard_details "
 						+ " WHERE wi_name = e.WI_NAME "
 						+ " and CARD_STATUS IN ('Regular','Not Activated') and primary_card_number='"+primaryCard+"') AS CARD_COUNT, "
 						+ " (SELECT COUNT(1) "
						+ "  FROM ext_dscop "
						+ "  WHERE PRIMARY_CID = '"+primaryCid+"'"
						+ "  AND PRIMARY_CARD_NO = '"+primaryCard+"'"
						+ "  AND ASSOCIATE_CARD = '"+associatedCard+"'"
						+ "  AND LEAD_REF_NO IS NOT NULL"
						+ "  AND stage_name NOT IN ('DISCARD1', 'EXIT','ARCHIVAL') and wi_name != e.WI_NAME) AS TOTAL_COUNT"
 						+ " FROM EXT_DSCOP_EXTENDED e "
 						+ " WHERE e.WI_NAME = N'"+ WIName +"'");
			}
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "sOutput" + sOutput);
			XMLParser xp1 = new XMLParser(sOutput);
			int cardCount = Integer.parseInt(xp1.getValueOf("CARD_COUNT"));
			int totalCount = Integer.parseInt(xp1.getValueOf("TOTAL_COUNT"));
			int totalCard =cardCount+totalCount;
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_INFO, "cardCount" + cardCount+"totalCount"+totalCount+"totalCard"+totalCard);
			int totalRetrieved = Integer.parseInt(xp1.getValueOf("TotalRetrieved"));
			if(totalRetrieved > 0){
				String status ="0";
				String statusMsg="";
				if (xp1.getValueOf("PASSPORT_EXPIRED").equalsIgnoreCase("Y")) {
					inqFlag = "Y";
					status = "27";
					statusMsg="Passport Expired";
				} else if (xp1.getValueOf("EIDA_EXPIRED").equalsIgnoreCase("Y")) {
					inqFlag = "Y";
					status = "28";
					statusMsg="EIDA Expired";
				} else if (xp1.getValueOf("DORMANT_CUSTOMER").equalsIgnoreCase("Y")) {
					inqFlag = "Y";
					status = "29";
					statusMsg="Customer is Dormant";
				}  else if (!primaryCardStatus(xp1.getValueOf("STATUS")) && associatedCard.equalsIgnoreCase("Debit")) {
					inqFlag = "Y";
					status = "32";
					statusMsg="Primary Card is Invalid";
				}  else if (!accountStatusCode(xp1.getValueOf("ACCOUNT_STATUS_CODE")) && associatedCard.equalsIgnoreCase("Debit")) {
					inqFlag = "Y";
					status = "31";
					statusMsg="Account is Invalid";
				}  else if(totalCard >= suppLimitCount) {
					inqFlag = "Y";
					status = "33";
					statusMsg="Supplementary Card is Exceeded";
				}else if(!(xp1.getValueOf("STATUS_PRIMARY_CARD").equalsIgnoreCase("A")|| xp1.getValueOf("STATUS_PRIMARY_CARD").equalsIgnoreCase("")||xp1.getValueOf("STATUS_PRIMARY_CARD").equalsIgnoreCase("null")) && associatedCard.equalsIgnoreCase("Credit")){
					inqFlag = "Y";
					status = "32";
					statusMsg="Primary Card No is Invalid";
				}else if (cardValidator(primaryCard)) {
					inqFlag = "Y";
					status = "26";
					statusMsg="Card is Betaqti";
				}
				responseObj.setStatusCode(status);
				responseObj.setStatusMessage(statusMsg);

				APCallCreateXML.APUpdate("EXT_DSCOP", "INQ_FLAG,INQ_REJECT_STATUS,INQ_REJECT_MSG", "'"+inqFlag+"','"+status+"','"+statusMsg+"'", " WI_NAME = N'"+ WIName +"'", sessionId);
				if(inqFlag.equalsIgnoreCase("Y")){
					responseObj.setStatusMessage("Workitem Created Successfully but Customer is not eligible for Card - "+statusMsg);
					outputXML = ProdCreateXML.WMCompleteWorkItem(sessionId, WIName, 1);
				}else{
					responseObj.setStatusCode(status);
					responseObj.setStatusMessage("Workitem Created Successfully and Customer is eligible for Card");	
				}
			}
		}
		responseObj.setApplicationName(applicationName);
		responseObj.setApplicationVersion(appVersion);
		responseObj.setApplicationJourney(applicationJourney);
		responseObj.setLanguage(language);
		responseObj.setSYSREFNO(sysRefNo);
		return responseObj;
		
	}

	private ApplicationAttributes[] responseApplicationAttributes(String wiName) {
		ApplicationAttributes[] attributes = null;
		try {
			String outputXML = "";
			outputXML = APCallCreateXML.APSelect("SELECT '" + wiName + "' AS WI_NAME " + " FROM DUAL");
			XMLParser xp = new XMLParser(outputXML);
			int mainCode = Integer.parseInt(xp.getValueOf("MainCode"));
			if (mainCode == 0) {
				attributes = DSCOPUtils.getInstance().getApplicationAttributes(outputXML);
			}
		} catch (Exception e) {
			DSCOPLogMe.logMe(DSCOPLogMe.LOG_LEVEL_ERROR, "Exception responseApplicationAttributes" + e);
		}
		return attributes;
	}

	public boolean cardValidator(String primaryCard) {
		boolean isCard = false;
		String[] rejectedPrefixes = {"515987", "529342","553533","555265","559234"};
		for (String prefix : rejectedPrefixes) {
			if (primaryCard.startsWith(prefix)) {
				isCard=true;
			}
		}
		return isCard;
	}
	
	private  boolean accountStatusCode(String accountStatusCode) {
		String value = "6,3,8,17,16,4";
        String[] valueArray = value.split(",");
        for (String values : valueArray) {
            if (accountStatusCode.contains(values.trim())) {
                return true;
            }
        }
        return false;
    }
	
	private  boolean primaryCardStatus(String primaryCardStatus) {
		String value = "0,1,11";
        String[] valueArray = value.split(",");
        for (String values : valueArray) {
            if (primaryCardStatus.contains(values.trim())) {
                return true;
            }
        }
        return false;
    }
	private void setDefaultAttributes(HashMap<String, String> attributeMap, String appVersion, String sourcingChannel, String applicationJourney) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.add(Calendar.DATE, 14);
	    attributeMap.put("TRANS_DATETIME", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
	    attributeMap.put("REQUESTED_DATETIME", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
	    attributeMap.put("HANDOFF_STAGEID", "0");
	    attributeMap.put("NEXT_STAGE_ID", "1");
	    attributeMap.put("APP_VERSION", appVersion);
	    attributeMap.put("SOURCING_CHANNEL", sourcingChannel);
	    attributeMap.put("JOURNEY_TYPE", applicationJourney);
	    attributeMap.put("TYPE", "C");
	    attributeMap.put("EXPIRY_DATE", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(c.getTime()));
	}
	
	private CBGSingleHookResponse prepareErrorResponse(CBGSingleHookResponse responseObj, String statusCode, String statusMessage, String applicationName, String appVersion, String applicationJourney, String sysRefNo) {
	    responseObj.setStatusCode(statusCode);
	    responseObj.setStatusMessage(statusMessage);
	    responseObj.setApplicationName(applicationName);
	    responseObj.setApplicationVersion(appVersion);
	    responseObj.setApplicationJourney(applicationJourney);
	    responseObj.setSYSREFNO(sysRefNo);
	    return responseObj;
	}
	
}