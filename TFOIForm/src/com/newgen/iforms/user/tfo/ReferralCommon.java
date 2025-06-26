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

public class ReferralCommon extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;

	public ReferralCommon(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside ReferralCommon beforeFormLoad >>>");
		String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		log.info("WorkItem Name: " + workitemName);		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent >");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		String retMsg = getReturnMessage(true);
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					log.info("inside on form load ReferralCommon >>>>");
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + wiName);
					Boolean view = setUserDetail();					
					intializeStaticValue();
					referralLoadCombo();
					tabHandlingMast();
					formObject.applyGroup(CONTROL_SET_CUSTOMER_DETAILS);
					loadDecReferalGrid();
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY_RM);
					}
					retMsg = getReturnMessage(view, controlName);
				} else if (FRAME_DECISION_HIST.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				} 
			} else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(bSubmit)
				{
					  bSubmit=false;
					  onSubmitReferral();
				      saveDecHistory();
				      if("RM".equalsIgnoreCase(this.sActivityName)&&requestCategory.equalsIgnoreCase("IFCPC")){
				    	  createTSLMPushMsg("R");
						}
				}
			}

		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		}
		return retMsg;
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		return arg1;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		//unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

}
