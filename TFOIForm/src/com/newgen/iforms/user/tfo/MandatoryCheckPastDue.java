package com.newgen.iforms.user.tfo;

import java.util.List;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.user.config.Constants;

public class MandatoryCheckPastDue extends PPM implements Constants {

	public MandatoryCheckPastDue(IFormReference formObject) {
		super(formObject);
	}
	public boolean validateMandatoryPastDuePPM()  
	{
		List<List<String>> sResultSet = null;
		requestType = formObject.getValue(REQUEST_TYPE).toString();
		requestCategory = formObject.getValue(REQUEST_CATEGORY).toString();
		sWorkitemID = formObject.getObjGeneralData().getM_strProcessInstanceId();
		String sQuery1 = "SELECT MANDATORY,INSERTION_DATETIME from TFO_DJ_PAST_DUE_VALIDATION_MAST where REQUEST_CATEGORY_ID = '"
				+ requestCategory + "' AND REQUEST_TYPE_ID = '" + requestType
				+ "' AND TO_DATE(INSERTION_DATETIME,'dd-MON-yy') <= (SELECT TO_DATE(CREATEDDATETIME,'dd-MON-yy') FROM WFINSTRUMENTTABLE WHERE PROCESSINSTANCEID = '"
				+ sWorkitemID + "')";
		log.info("Query " + sQuery1);
		sResultSet = formObject.getDataFromDB(sQuery1);

		log.info("validateMandatoryPastDue===Rseultset " + sResultSet);

		if (sResultSet != null && sResultSet.size() > 0) {
			if (sResultSet.get(0).get(0).equalsIgnoreCase("Y")) {
				String alert = "Please Fill Mandatory Past Due Liability Field";
				log.info("MandatoryCheckPastDue--1-sendmessagelist: "+sendMessageList);
				return validateMandatoryFieldsPPM(PAST_DUE_LIABILITY, alert);
			}
		}
		return true;
	}
	
	public boolean validateMandatoryFieldsPPM(String sFieldName, String alertMsg) {
		try {
			log.info("Validation Conrol Name :"+sFieldName);
			log.info("Validation Conrol Value :"+formObject.getValue(sFieldName).toString());
			String fieldValue = normalizeString(formObject.getValue(sFieldName).toString());
			log.info("fieldValue --validateMandatoryFieldsPPM  " + fieldValue);
			if (!(isEmpty(fieldValue)) || "".equalsIgnoreCase(fieldValue) || emptyStr.equalsIgnoreCase(fieldValue)) {
				log.info("validateMandatoryFieldsPPM blank");
				sendMessageHashMap(sFieldName, alertMsg);
				log.info("MandatoryCheckPastDue--2-sendmessagelist: "+sendMessageList);
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			log.error("Exception: ",e);
			return false;
		}

	}

}
