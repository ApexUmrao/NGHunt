package com.newgen.iforms.user.tfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

public class ComplianceReferralResponse extends Common implements Constants, IFormServerEventHandler{
	boolean bSubmit = true;

	public ComplianceReferralResponse(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		log.info("Inside ComplianceReferralResponse beforeFormLoad >>>");
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
		log.info("Inside complianceReferralResponse executeServerEvent>");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		String retMsg = getReturnMessage(true);
		try {
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					//formloadData();					
					log.info("inside on form load CustomerReferralResponse >>>>");
					String wiName = formObject.getValue("WI_NAME").toString();
					log.info("inside form load event -- WorkItem Name: " + wiName);
					Boolean view = setUserDetail();					
					//intializeStaticValue();
					referralLoadCombo();
					formObject.applyGroup(CONTROL_SET_CUSTOMER_DETAILS);
					loadDecReferalGrid();
					//setDecision(); //PT_US50
					log.info("Creating object of PPM -- WorkItem Name: " + wiName);
					PM pm_obj = new PM(formObject);
					pm_obj.formLoadDataPM();
					//disableFieldOnDemand("PRODUCT_TYPE####BRN_CODE");
					log.info("ENABLING REMARKS FIELD 1 >>>>");
		            enableFieldOnDemand("COMP_EXP_REMARKS");
		            log.info("ENABLING REMARKS FIELD 2 >>>>");
					if(Boolean.FALSE.equals(view)) {
						formObject.applyGroup(CONTROL_SET_READONLY_RM);
					}
					retMsg = getReturnMessage(view, controlName);
				}  else if (PM_DECISION_HISTORY.equalsIgnoreCase(controlName)) {
					log.info("loading decision history ...");
					loadDecisionHistoryListView();
				} 
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE))
			{
				if (DEC_CODE.equalsIgnoreCase(controlName)) {
					log.info("*********** inside decision change event *************");
					String sFinalDecision ="";
					sFinalDecision= formObject.getValue(controlName).toString(); 
					formObject.setValue(FINAL_DESC_PPM, getListValFromCode(lstDec, sFinalDecision));
				} 

			} else if (controlName.equalsIgnoreCase(BUTTON_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				String wiName = formObject.getValue("WI_NAME").toString();
				if(bSubmit)
				{
					bSubmit=false;
					saveDecHistory();
				}
			} else if (TAB_CLICK.equalsIgnoreCase(controlName)) {
					log.info("tab click event starts");
					implementTabNavigationCompRefRes(data);
					log.info("tab click event ends");
			 } else if(MANUAL_TAB_CLICK.equalsIgnoreCase(controlName)) {
					log.info("tab manually event starts" +data);
					executeTabClickCommandCompRefRes(data);
					log.info("tab manually event ends");
				}

		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		}
		return retMsg;
	}
	public boolean implementTabNavigationCompRefRes(String data) {
		log.info(">>>>>>>>>> Inside navigation method <<<<<<<<<< -- implementTabNavigationCompRefRes");
		String a[] =data.split(",");
		String button=a[0];
		String sheetID=a[1];
		int counterListCount=Integer.parseInt(a[2]);
		log.info("in button="+button+"sheetID="+sheetID+"listCount=");
		try {
			if (sheetID.equalsIgnoreCase(PM_COMPLIANCE_SHEET_ID)) {
				String strTabName = tabDescIdMap.get("CompScr");
				String strRefTo = tabDescIdMap.get("CompRefTo");
				formObject.clearTable(LISTVIEW_FINAL_DECISION);
				loadExcpSaveDataIntoRepeater(strTabName,strRefTo,"Yes",
						formObject.getValue(COMP_EXP_REMARKS).toString());
			}
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return true;
	}
	public String executeTabClickCommandCompRefRes(String tabID) {
		try { 
			log.info("Inside executeTabClickCommand : " + tabID);
            if(PM_INVOICE_DETAIL_SHEET_ID.equalsIgnoreCase(tabID)) {
				String strTabName = tabDescIdMap.get("CompScr");
				String strRefTo = tabDescIdMap.get("CompRefTo");
				formObject.clearTable(LISTVIEW_FINAL_DECISION);
				loadExcpSaveDataIntoRepeater(strTabName,strRefTo,"Yes",
						formObject.getValue(COMP_EXP_REMARKS).toString());
			}
		
		} catch (Exception e) {
			log.error("Exception: ",e);
		}
		return "";
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
	public void updateDataInWidget(IFormReference arg0, String arg1) {

	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		return null;
	}

}
