package com.newgen.iforms.user.ao;

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

public class Levels extends Common implements Constants, IFormServerEventHandler{
	public String sWorkitemID =formObject.getObjGeneralData().getM_strProcessInstanceId();
	public Levels(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1,
			String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1,
			String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		try{
			log.info(" LEVELS CALLED PP ");
		if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
			frame81_CPD_Disable();
			populateComparisonFields();
			manualFrameCPDDisable();
			populateStndInstr();
			log.info(" populateTRSD ");
			populateTRSD();
			//loadApplicationAssessmentDataCPD(); // tab removed 
			loadApplicationAssessmentData();
			log.info("after loadApplicationAssessmentData");
			populateCRSData();
			logInfo("Inside Loadd Call IFF." +controlName, "");
			formObject.setValue("Label_WS_Name", sActivityName);
			formObject.setValue(WI_NAME,sWorkitemID);
			formObject.setValue(CURR_WS_DETAIL,sActivityName);
			formObject.setStyle(CURR_WS_DETAIL,VISIBLE, FALSE);
			formObject.setValue(CRO_DEC,"");
			formObject.setValue(CRO_REMARKS,"");
			Frame52_Disable();
			//doReadOnlyStatic("CUST_ID,MASK8,TEXT163,AO_SOURCING_CHANNEL,AO_ACC_HOME_BRANCH,AO_SOURCING_CENTER,AO_REQUEST_TYPE,AO_DATA_ENTRY_MODE,AO_FORM_AUTO_GENERATE,AO_ACC_OWN_TYPE,AO_ACC_CLASS,AO_WMS_ID,AO_CURR_WS_DETAIL,");
			//disable_custInfo();
			disableControls(new String[] {SEC_CI,SEC_SI,SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,SEC_CC_AORM,SEC_CC_AORC,SEC_SI,SEC_INTERNAL_DETL,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ});
			//formObject.setNGEnable("Frame23", false);
			enableControls(new String[]  {SEC_DEC,SI_FRST_PAYMNT,SI_LST_PAYMNT});
			//formObject.setNGEnable("Frame78",false);
			//formObject.setNGEnable("Frame85",false);
			//formObject.setNGEnable("Frame41",false);
			//formObject.setNGEnable("Frame35",true);
			//formObject.setNGLocked("Frame23", false);
			//formObject.setNGLocked("Frame2",false);
			//formObject.setNGLocked("Frame78",false);
			//formObject.setNGLocked("Frame85",false);
			//formObject.setNGEnable("NEXT_10",false);//
			//formObject.setNGEnable("EDIT_10",false);
			formObject.setValue(CRO_REMARKS, "");
			//formObject.setNGLocked("Tab1", false);
			//formObject.setNGLocked("Tab2", false);		
			//formObject.setNGVisible("Pic1", false);
			//formObject.setNGVisible("Tab5", true);
			
			////setCPDCheckerCombos();
			////setTabVisible();
			setTemp_usr_0_product_selected();
			populateTRSD();
			////populateTRSDCPD();
			String custNo = formObject.getValue("AO_SELECTED_ROW_INDEX").toString();
            String tempQuery="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemID+"'";
            populateUDFGrid(tempQuery);
		}
		if(eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE) || eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
			try
			{
				if(formObject.getValue(DATA_ENTRY_MODE).toString().equalsIgnoreCase("Quick Data Entry") && !formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID"))
				{
					fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
				}
				
				if(controlName.equalsIgnoreCase(MANUAL_DOB) && !formObject.getValue(controlName).toString().equalsIgnoreCase(""))
				{
					if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("True"))
					{
						formObject.setValue("MASK2",formObject.getValue(controlName).toString());
					}
				}
				else if(controlName.equalsIgnoreCase("PASSPORTISSDATE_MANUAL") && !formObject.getValue(controlName).toString().equalsIgnoreCase(""))
				{
					formObject.setValue("MASK6",formObject.getValue(controlName).toString());
				}
				else if(controlName.equalsIgnoreCase("PASSPORTEXPDATE_MANUAL") && !formObject.getValue(controlName).toString().equalsIgnoreCase(""))
				{
					formObject.setValue("MASK7",formObject.getValue(controlName).toString());
				}
				
				else if(controlName.equalsIgnoreCase("VISAISSDATE_MANUAL") && !formObject.getValue(controlName).toString().equalsIgnoreCase(""))
				{
					formObject.setValue("MASK5",formObject.getValue(controlName).toString());
				}
				else if(controlName.equalsIgnoreCase("VISAEXPDATE_MANUAL") && !formObject.getValue(controlName).toString().equalsIgnoreCase(""))
				{
					formObject.setValue("MASK4",formObject.getValue(controlName).toString());
				}
				
				
				if(controlName.equalsIgnoreCase(CRO_DEC) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK))
				{
					if(controlName.equalsIgnoreCase(CRO_DEC) && formObject.getValue(controlName).toString().equalsIgnoreCase("Approve"))
					{
						formObject.setValue(CRO_REJ_REASON,"");
						disableControls(new String[] {CRO_REJ_REASON});
					}
					else{
						enableControls(new String[] {CRO_REJ_REASON});	
					}
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			}
			
		if(controlName.equalsIgnoreCase(BTN_SUBMIT) && eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)){
			saveLevels(eventType);
		}
		}
		catch (Exception e) 
		{
			log.error(e);
			return null;
		}
			return null;
	}

	
	
	public void Frame52_Disable()
	{
		formObject.setStyle(SOURCING_CHANNEL,ENABLE,FALSE);
		//formObject.setStyle(SOURCING_CENTER,ENABLE,FALSE);
		formObject.setStyle(REQUEST_TYPE,ENABLE,FALSE);
		formObject.setStyle(DATA_ENTRY_MODE,ENABLE,FALSE);
		formObject.setStyle(FORM_AUTO_GENERATE,ENABLE,FALSE);
		formObject.setStyle(ACC_OWN_TYPE,ENABLE,FALSE);
		formObject.setStyle(ACC_CLASS,ENABLE,FALSE);
		formObject.setStyle(ACC_HOME_BRANCH,ENABLE,FALSE);
		logInfo("Form 52 Disable." , "");
	}

	public int saveLevels(String eventType)
	{
		int reply;
		log.info("Inside Save of LEVELLLLLS");
		log.info("dec in LEVELS  ::: "+formObject.getValue(CRO_DEC).toString());
		if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase(""))
		{
			sendMessageValuesList(CRO_DEC, "Please select user decision.");
			formObject.setStyle(BUTTON_SUBMIT,ENABLE,TRUE);
			return 0;
		}
		if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve"))
		{
			log.info("Not Approve.");
			String rejectionReason=formObject.getValue(CRO_REJ_REASON).toString();
			String rejectionRemarks=formObject.getValue(CRO_REMARKS).toString();
			log.info("Rejection REason  >> "+rejectionReason);
			if(rejectionReason.equalsIgnoreCase(""))
			{
				sendMessageValuesList(CRO_REJ_REASON, "Please select Rejection reason.");
				formObject.setStyle(BUTTON_SUBMIT,ENABLE,TRUE);
				return 0;
			}
			if(rejectionRemarks.equalsIgnoreCase(""))
			{
				sendMessageValuesList(CRO_REMARKS, "Please select Rejection remarks.");
				formObject.setStyle(BUTTON_SUBMIT,ENABLE,TRUE);
				return 0;
			}
		}
		if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve"))
		{
			String sUpdateDecision="update "+sExtTable+" set IS_LEVELS_DONE='false' Where WI_NAME='"+ sWorkitemID +"'";
			formObject.saveDataInDB(sUpdateDecision);   //setdatainDB
		}
		if(sActivityName.equalsIgnoreCase("Level1 Approved"))
		{
			log.info("Levels 1 decision SETTT");
			String decision=formObject.getValue(CRO_DEC).toString();
			String sUpdateDecision="update "+sExtTable+" set level1_dec='"+ decision +"' Where WI_NAME='"+ sWorkitemID +"'";
			formObject.saveDataInDB(sUpdateDecision);
		}
		else if(sActivityName.equalsIgnoreCase("Level2 Approved"))
		{
			String decision=formObject.getValue(CRO_DEC).toString();
			String sUpdateDecision="update "+sExtTable+" set level2_dec='"+ decision +"' Where WI_NAME='"+ sWorkitemID +"'";
			formObject.saveDataInDB(sUpdateDecision);
		}
		else if(sActivityName.equalsIgnoreCase("Level3 Approved"))
		{
			String decision=formObject.getValue(CRO_DEC).toString();
			String sUpdateDecision="update "+sExtTable+" set level3_dec='"+ decision +"' Where WI_NAME='"+ sWorkitemID +"'";
			formObject.saveDataInDB(sUpdateDecision);
		}
		else if(sActivityName.equalsIgnoreCase("Level4 Approved"))
		{
			String decision=formObject.getValue(CRO_DEC).toString();
			String sUpdateDecision="update "+sExtTable+" set level4_dec='"+ decision +"' Where WI_NAME='"+ sWorkitemID +"'";
			formObject.saveDataInDB(sUpdateDecision);
		}
		//reply = JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val("CA008") , null, JOptionPane.YES_NO_OPTION);
		//reply = sendMessageValuesList();
		
		
			createHistory();
			createAllHistory();
	
			formObject.setStyle(BUTTON_SUBMIT,ENABLE,TRUE);
			//formObject.setNGEnable("SUBMIT_1",true);
			formObject.setStyle(BUTTON_SUBMIT,ENABLE,TRUE);
			return 0;
	
		
		
	}
	
	@Override
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0,
			HttpServletRequest arg1, HttpServletResponse arg2,
			WorkdeskModel arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMaskedValue(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
