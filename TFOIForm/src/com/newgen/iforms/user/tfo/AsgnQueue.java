package com.newgen.iforms.user.tfo;

import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class AsgnQueue extends Common implements Constants, IFormServerEventHandler{
	boolean sOnLoad=false;
	boolean bSubmit = true;

	public AsgnQueue(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference formObject) {
		log.info("Inside beforeFormLoad >>>");
		String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		log.info("WorkItem Name: " + workitemName);		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent >");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		String retMsg = getReturnMessage(true);
		String msg = "";
		Boolean success = true;
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					log.info("inside on form load Assignment Queue >>>>");
					String winame = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + winame);
					Boolean view = true;
					log.info("process type value from assignment queue: "+formObject.getValue(PROCESS_TYPE).toString());
					view = setUserDetail();
					formObject.applyGroup(CONTROL_SET_DISABLE_TEMP_TXT);
					formObject.applyGroup(CONTROL_SET_DISABLE_STATIC_INTRO);
					formObject.applyGroup(CONTROL_SET_DISABLE_STATIC_LOGIS_LOV);
					formObject.setStyle(TRANSACTION_AMOUNT, "mandatory", FALSE);
					loadRequestCategory();
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY);
					}						
					retMsg = getReturnMessage(view, controlName);
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				}
			} else if (bSubmit && controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(bSubmit){
				bSubmit = false;
				formObject.setStyle(BUTTON_SUBMIT, "disable", TRUE);
				String[] retVal = docCheck(formObject.getValue(REQUEST_TYPE).toString()).split("#");
				if(retVal[0].equalsIgnoreCase(TRUE)){
					formObject.setValue(DEC_CODE, "App");
					formObject.setValue(DECISION, "Accept");
					saveDecHistory();
					success = true;
					retMsg = getReturnMessage(success);
					return retMsg;
				} else {
					bSubmit = true;
					formObject.setStyle(BUTTON_SUBMIT, "disable", FALSE);
					success = false;
					msg = retVal[1];
					retMsg = getReturnMessage(success, controlName, msg);
					return retMsg;
				}
				}
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": " , e);
		}
		return retMsg;
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,
			String arg2) {
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		return null;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		return null;
	}

}
