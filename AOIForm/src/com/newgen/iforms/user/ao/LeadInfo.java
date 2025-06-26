package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.json.simple.JSONArray;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.ConstantAlerts;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class LeadInfo extends Common implements Constants, IFormServerEventHandler, ConstantAlerts{
	boolean bFormLoaded = false; //sOnLoad new variable name

	public LeadInfo(IFormReference formObject) {
		super(formObject);
	}
	
	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
			formObject.setValue(WI_NAME,sWorkitemId);
			formObject.setValue(CURR_WS_DETAIL,sActivityName);
			formObject.setStyle(CURR_WS_DETAIL,"visible","false");
			formObject.setValue("Label_WS_Name", sActivityName);
			
//			setTabVisible();
//			formObject.setNGLocked("Frame52",false);
		}
		return data;
		
	}	
	
	@Override
	public String generateHTML(EControl arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,	String arg2) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String introduceWorkItemInWorkFlow(IFormReference arg0, HttpServletRequest arg1, HttpServletResponse arg2,
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
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void fillFCRDataInBelowFields(String sControlName,String sControlValue) {
		try {
			if(sControlName.equalsIgnoreCase(FCR_ADDRESS) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_CORR_POB_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_POBOXNO,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_CITY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_CITY_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_CITY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_STATE) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_STATE_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CORR_STATE,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_CNTRY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CORR_CNTRY,sControlValue);
				}
				if(formObject.getValue("CNTRY_FCR").toString().equalsIgnoreCase("UAE")) {
					if(formObject.getValue("RESIDENCY_STATUS").toString().equalsIgnoreCase("")) {
						formObject.setValue("RESIDENCY_STATUS", "Yes");
						formObject.setStyle("RESIDENCY_STATUS", DISABLE, TRUE);
					}
					if(returnVisaStatus().equalsIgnoreCase("")) {
						setFinalDataComparison(formObject.getValue("Check124").toString(),
								formObject.getValue("Check99").toString(),
								formObject.getValue("Check98").toString(),
								formObject.getValue("visaStatus_fcr").toString(),
								formObject.getValue("visaStatus_eida").toString(),
								formObject.getValue("visaStatus_manual").toString(),
								"Not Required");
					}
					if(formObject.getValue("Combo43").toString().equalsIgnoreCase("")) {
						formObject.setValue("Combo43", "Yes");
						formObject.setStyle("Combo43", DISABLE, TRUE);
					}
				}
			} else if(sControlName.equalsIgnoreCase(RESIDENCY_STATUS) && sControlValue.equalsIgnoreCase("Yes")) {
				if(!formObject.getValue(MANUAL_CNTRY).toString().equalsIgnoreCase("UNITED STATES")){
					sendMessageValuesList(RESIDENCY_STATUS,"Residency status is not valid.");
					formObject.setValue(RESIDENCY_STATUS,"No");
				}
			} else if(sControlName.equalsIgnoreCase(FCR_PH) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(CP_PHONENO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_MOBILE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(CP_MOBILE,sControlValue);
			}
			else if(sControlName.equalsIgnoreCase(FCR_EMAIL) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_EMAIL_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_EMAIL,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_PREFIX) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_PREFIX_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CUST_PREFIX,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_NAME) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(PD_FULLNAME,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_EIDANO) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(PD_EIDANO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_NATIONALITY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_NATIONALITY_FCR).toString().equalsIgnoreCase(TRUE)) {
					logInfo("fillFCRDataInBelowFields","In side visaStatus_fcr ==== ");
					if(sControlValue.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						logInfo("fillFCRDataInBelowFields","In side if block visaStatus_fcr ==== ");
						formObject.setValue(FCR_VISASTATUS,"Not Required");
					} else {
						logInfo("fillFCRDataInBelowFields","In side else block visaStatus_fcr ==== ");
						formObject.setValue(FCR_VISASTATUS,"");
					}
					formObject.setValue(CUST_NATIONALITY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_MOTHERSNAME) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_MOTHERSNAME_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(PD_MOTHERMAIDENNAME,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_DOB) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(PD_DOB,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_GENDER) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_GENDER_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CUST_GENDER,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_PASSPORTNO) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_PASSPORT_NO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_PASSPORTISSDATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_PASS_ISS_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_PASSPORTEXPDATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_PASS_EXP_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_VISANO) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_VISA_NO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_VISAISSDATE) && !sControlValue.equalsIgnoreCase(""))
			{
				formObject.setValue(HD_VISA_ISSUE_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(FCR_VISAEXPDATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_EXP_DATE,sControlValue);
			}
			else if(sControlName.equalsIgnoreCase(FCR_PROFESSION) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_PROFESSION_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(PROF_CODE,sControlValue);
					formObject.setValue(PROFESION,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_EMPLYR_NAME) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(EMPNAME,formObject.getValue(FCR_EMPLYR_NAME).toString());
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM "
							+ "USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+sControlValue+"'");
					logInfo("fillEIDADataInBelowFields","CD_STATUS sOutput: "+sOutput);
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.size() > 0 && null != sOutput.get(0) && (sOutput.get(0).get(0).equalsIgnoreCase("1")
							|| sOutput.get(0).get(0).equalsIgnoreCase("2"))) {
						formObject.setValue(ED_CB_TML,TRUE);
					} else {
						formObject.setValue(ED_CB_NON_TML,TRUE);
					}
				}
			}
			if(sControlName.equalsIgnoreCase(FCR_PER_CNTRY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(RES_CNTRY, sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(FCR_RESIDENT) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_COUNTRY_RES_FCR).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(PERM_CNTRY, sControlValue);
				}
			}
		} catch(Exception e) {
			logError("fillFCRDataInBelowFields", e);
		}
	}
	
	public void fillEIDADataInBelowFields(String sControlName,String sControlValue) {
		try {
			logInfo("fillEIDADataInBelowFields", "INSIDE, sControlName: "+sControlName+", "
					+ "sControlValue: "+sControlValue);
			if(sControlName.equalsIgnoreCase(EIDA_ADDRESS) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_CORR_POB_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CP_POBOXNO,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_CITY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_CITY_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CP_CITY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_STATE) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_STATE_EIDA).toString().equalsIgnoreCase("True"))
				{
					formObject.setValue(CORR_STATE,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_CNTRY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CORR_CNTRY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_PH) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(CP_PHONENO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_MOBILE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(CP_MOBILE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_EMAIL) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_EMAIL_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CP_EMAIL,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_PREFIX) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_PREFIX_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CUST_PREFIX,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_NAME) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(PD_FULLNAME,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_EIDANO) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(PD_EIDANO,sControlValue);
				formObject.setValue(RA_CARRYNG_EID_CARD, "Yes");
			} else if(sControlName.equalsIgnoreCase(EIDA_NATIONALITY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_NATIONALITY_EIDA).toString().equalsIgnoreCase("True")) {
					if(sControlValue.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.setValue(DI_CODE,"Not Required");
					} else {
						formObject.setValue(DI_CODE,"");
					}
					formObject.setValue(CUST_NATIONALITY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_MOTHERNAME) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_MOTHERSNAME_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(PD_MOTHERMAIDENNAME,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_DOB) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(PD_DOB,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_GENDER) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_GENDER_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CUST_GENDER,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_PASSPORTNO) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_PASSPORT_NO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_PASS_ISSUE_DATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_PASS_ISS_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_PASS_EXP_DATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_PASS_EXP_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_VISANO) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_VISA_NO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_VISAISSDATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_VISA_ISSUE_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_VISAEXPDATE) && !sControlValue.equalsIgnoreCase("")) {
				formObject.setValue(HD_EXP_DATE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(EIDA_PROFESSION) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_PROFESSION_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(PROF_CODE,sControlValue);
					formObject.setValue(PROFESION,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_EMPLYR_NAME) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(EMPNAME,formObject.getValue(EIDA_EMPLYR_NAME).toString());
					formObject.setValue(EMP_STATUS, "Salaried");
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT CD_STATUS FROM "
							+ "USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+sControlValue+"'");
					logInfo("fillEIDADataInBelowFields","CD_STATUS sOutput: "+sOutput);
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.size() > 0 && null != sOutput.get(0) && (sOutput.get(0).get(0).equalsIgnoreCase("1") 
							|| sOutput.get(0).get(0).equalsIgnoreCase("2"))) {
						formObject.setValue(ED_CB_TML,"True");
					} else {
						formObject.setValue(ED_CB_NON_TML,"True");
					}
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_PER_CNTRY) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(RES_CNTRY, sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(EIDA_RESIDENT) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue(CHECKBOX_COUNTRY_RES_EIDA).toString().equalsIgnoreCase("True")) {
					formObject.setValue(PERM_CNTRY, sControlValue);
				}
			}
		} catch(Exception e) {
			logError("fillEIDADataInBelowFields", e);;
		}
	}
	
	public void FillManualDataInBelowFields(String sControlName,String sControlValue) {
		try {
			if(sControlName.equalsIgnoreCase(MANUAL_ADDRESS)) {
				if(formObject.getValue(CHECKBOX_CORR_POB_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CP_POBOXNO,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_CITY)) {
				if(formObject.getValue(CHECKBOX_CITY_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CP_CITY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_STATE)) {
				if(formObject.getValue(CHECKBOX_STATE_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CORR_STATE,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_NATIONALITY)) {
				if(formObject.getValue(CHECKBOX_NATIONALITY_MANUAL).toString().equalsIgnoreCase("True")) {
					if(sControlValue.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.setValue(MANUAL_VISASTATUS,"Not Required");
					} else {
						formObject.setValue(MANUAL_VISASTATUS,"");
					}
					formObject.setValue(CUST_NATIONALITY,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_CNTRY)) {
				if(formObject.getValue(CHECKBOX_CNTRY_OF_CORR_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CORR_CNTRY,sControlValue);
					if(sControlValue.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.setStyle(COR_MAKANI, DISABLE, FALSE);
					} else {
						formObject.setStyle(COR_MAKANI, DISABLE, TRUE);
					}
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_PH)) {
				formObject.setValue(CP_PHONENO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(MANUAL_MOBILE)) {
				formObject.setValue(CP_MOBILE,sControlValue);
			} else if(sControlName.equalsIgnoreCase(MANUAL_EMAIL)) {
				if(formObject.getValue(CHECKBOX_EMAIL_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CP_EMAIL,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_PREFIX)) {
				if(formObject.getValue(CHECKBOX_PREFIX_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CUST_PREFIX,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_NAME)) {
				formObject.setValue(PD_FULLNAME,sControlValue);
			} else if(sControlName.equalsIgnoreCase(MANUAL_EIDANO)) {
				formObject.setValue(PD_EIDANO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(MANUAL_MOTHERNAME)) {
				if(formObject.getValue(CHECKBOX_MOTHERSNAME_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(PD_MOTHERMAIDENNAME,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_GENDER)) {
				if(formObject.getValue(CHECKBOX_GENDER_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(CUST_GENDER,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_PASSPORTNO)) {
				formObject.setValue(HD_PASSPORT_NO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(MANUAL_VISANO)) {
				formObject.setValue(HD_VISA_NO,sControlValue);
			} else if(sControlName.equalsIgnoreCase(MANUAL_PROFESSION)) {
				if(formObject.getValue(CHECKBOX_PROFESSION_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(PROF_CODE,sControlValue);
					formObject.setValue(PROFESION,sControlValue);
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_PROFESSION)) {
				System.out.println("IN IF--select in profession");
				formObject.setValue(PROF_CODE,"");
				formObject.setValue(PROFESION,"");
			} else if(sControlName.equalsIgnoreCase(MANUAL_EMPLYR_NAME)) {
				if(formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(EMPNAME,formObject.getValue(MANUAL_EMPLYR_NAME).toString());
					List<List<String>> sOutput = formObject.getDataFromDB
							("SELECT CD_STATUS FROM USR_0_EMPLOYER_MASTER WHERE EMP_NAME ='"+sControlValue+"'");
					logInfo("fillManualDataInBelowFields","CD_STATUS sOutput: "+sOutput);
					formObject.setValue(ED_CB_TML,"False");
					formObject.setValue(ED_CB_NON_TML,"False");
					if(sOutput.size() > 0 && null != sOutput.get(0) && (sOutput.get(0).get(0).equalsIgnoreCase("1")
							 || sOutput.get(0).get(0).equalsIgnoreCase("2"))) {
						formObject.setValue(ED_CB_TML,"True");
					} else {
						formObject.setValue(ED_CB_NON_TML,"True");
					}
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_PER_CNTRY)) {
				if(formObject.getValue(CHECKBOX_COUNTRY_PER_RES_MANUAL).toString().equalsIgnoreCase("True")) {
					formObject.setValue(RES_CNTRY, sControlValue);
					if(sControlValue.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
						if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_MAKER))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
					} else {
						if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO))
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
						if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_MAKER))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
				}
			} else if(sControlName.equalsIgnoreCase(MANUAL_RESIDENT) && !sControlValue.equalsIgnoreCase("")) {
				if(formObject.getValue("CHECKMANUAL").toString().equalsIgnoreCase("True")) {
					formObject.setValue(PERM_CNTRY, sControlValue);
					if(sControlValue.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_MAKER))
							formObject.setStyle(RES_MAKANI, DISABLE, FALSE);
					} else {
						if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
						if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_MAKER))
							formObject.setStyle(RES_MAKANI, DISABLE, TRUE);
					}
				}
			}
		} catch(Exception e) {
			logError("fillManualDataInBelowFields", e);;
		}
	}
	
	public void setFinalDataComparison(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,
			String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String Value) {
		logInfo("setFinalDataComparison","INSIDE");
		if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True")) {
			formObject.setValue(sFCRDataControl,Value);
			formObject.setStyle(sFCRDataControl, DISABLE, FALSE);
			formObject.setValue(sEIDADataControl,"");
			formObject.setValue(sManualDataControl,"");

		} else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True")) {
			formObject.setValue(sEIDADataControl,Value);
			formObject.setStyle(sEIDADataControl, DISABLE, FALSE);
			formObject.setValue(sManualDataControl,"");
			formObject.setValue(sFCRDataControl,"");

		} else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True")) {
			formObject.setValue(sManualDataControl,Value);
			formObject.setStyle(sManualDataControl, DISABLE, FALSE);
			formObject.setValue(sFCRDataControl,"");
			formObject.setValue(sEIDADataControl,"");
		} else {
			formObject.setValue(sFCRDataControl,"");
			formObject.setValue(sEIDADataControl,"");
			formObject.setValue(sManualDataControl,"");
		}
	}
	
	public void searchProductList(String sTableName, String data) {
		logInfo("searchProductList", "INSIDE");
		try {
			String rtnArry[] = null;
			String rtnData[] = null;
			String sQuery = "";
			List<List<String>> sOutput;
			String sDebitAccNo = "";
			String sAllProduct = "";
			String[] resultArray = data.split("$$$");
			String sResult = resultArray[0];
			int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
			String sCustID = formObject.getTableCellValue(ACC_RELATION, iPrimaryCust, 2);
			int iRows = getGridCount(PRODUCT_QUEUE);
			int iSelectedRow = Integer.parseInt(resultArray[1]);
			String sEmail  = "SELECT FINAL_EMAIL FROM USR_0_CUST_TXN WHERE cust_sno='"+getPrimaryCustomerSNO()
					+"' AND WI_NAME = '"+sWorkitemId+"'";
			logInfo("searchProductList", "sEmail query: "+sEmail);
			//Email Etihad Validation
			List<List<String>> sEmailOutput = formObject.getDataFromDB(sEmail);
			logInfo("searchProductList", "sEmailOutput: "+sEmailOutput);
			String email = "";
			if(sEmailOutput.size() > 0) {
				email = sEmailOutput.get(0).get(0);
			}
			if(!sResult.equalsIgnoreCase("")) {
				if(sResult.contains("#")) {
					rtnArry = sResult.split("#");								
					if(email.equalsIgnoreCase("")) {
						boolean rtn = emailEtihadVal(rtnArry);
						if(!rtn) {
							return;
						}
					}
					String sAccClass = formObject.getValue(ACC_HOME_BRANCH).toString();
					logInfo("searchProductList", "sAccClass: "+sAccClass);
					logInfo("searchProductList", "rtnArry[0]......"+rtnArry[0]);
					for(int i=0; i<rtnArry.length; i++) {
						System.out.println("inside for loop for setting values in repeater.....,,,,,");
						rtnData = rtnArry[i].split("~");							 
//						objChkRepeater.addRow();
						sAllProduct = sAllProduct+"'"+rtnData[0]+"',";
//						objChkRepeater.setEnabled(iRows+i,"AO_PRODUCT_QUEUE.CURRENCY",true);
//						objChkRepeater.setEditable(iRows+i, "AO_PRODUCT_QUEUE.CURRENCY", true);
						logInfo("searchProductList", " value of i ="+i+" "+"... rtnData[2]..."+rtnData[2]);
						setProductCurrencyComboLoadDisable1(rtnData[0]);
//						objChkRepeater.setValue(iRows+i,"AO_PRODUCT_QUEUE.CURRENCY",rtnData[2]);
						String prodQueueColumns = "Product_Code,Product_Description,Cheque_Book,Account_Branch,"
								+ "WI_NAME,CID,Currency";
						String prodQueueValues = rtnData[0]+","+rtnData[1]+",No,"+rtnData[4]+","+sWorkitemId+","
								+iRows+i+","+rtnData[2];
						LoadListViewWithHardCodeValues(PRODUCT_QUEUE, prodQueueColumns, prodQueueValues);
						if(rtnData[3].equalsIgnoreCase("Yes")) {
							formObject.setStyle("table94_cheque_book", DISABLE, FALSE);
						} else {
							formObject.setStyle("table94_cheque_book", DISABLE, TRUE);
						}
						String getquery = "SELECT visa_status FROM USR_0_CUST_TXN WHERE cust_sno='"
								+getPrimaryCustomerSNO()+"' AND WI_NAME = '"+sWorkitemId+"'";
						logInfo("searchProductList", "getquery---"+getquery);
						List<List<String>> getqueryOutput = formObject.getDataFromDB(getquery);
						logInfo("searchProductList", "getqueryOutput---"+getqueryOutput);
						if(getqueryOutput.size() > 0) {
							String Visa = getqueryOutput.get(0).get(0);
							logInfo("searchProductList", "getquery---"+Visa);
							if(Visa.equalsIgnoreCase("Under Processing")) {
								formObject.setStyle("table94_cheque_book", DISABLE, TRUE);
							}
						}
						rtnData = null;
						sQuery = "SELECT ACC_NO FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId
								+"' AND ACC_STATUS IN (SELECT DESCRIPTION FROM USR_0_ACCOUNT_STATUS_CODE "
								+ "WHERE CODE IN ('6','8')) AND CUSTOMER_ID='"+sCustID+"'";
						sOutput = formObject.getDataFromDB(sQuery);
						logInfo("searchProductList", "sOutput---"+sOutput);
						if(sOutput.size() > 0) {
							sDebitAccNo = sOutput.get(0).get(0);
							logInfo("searchProductList", "sDebitAccNo---"+sDebitAccNo);
							if(!sDebitAccNo.equalsIgnoreCase("")) {
								String sTemp[] = sDebitAccNo.split(",");
								for(int j=0; j<sTemp.length; j++) {
									formObject.addItemInCombo("table94_trnsfr_from_acc_no", sTemp[i]);
								}
							}
						}
					}
				} else {
					rtnData=sResult.split("~");
					sAllProduct =sAllProduct+"'"+rtnData[0]+"',";
					if(email.equalsIgnoreCase("")) {	
						sAllProduct = sAllProduct.substring(0,sAllProduct.length()-1);
						String sQuery1  = "SELECT COUNT(SUB_PRODUCT_TYPE) as SUB_PRODUCT_TYPE FROM "
								+ "USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE IN ("+sAllProduct+") AND "
								+ "UPPER(SUB_PRODUCT_TYPE) ='ETIHAD'";
						logInfo("searchProductList", "sQuery---"+sQuery1);
						List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
						if(!sOutput1.get(0).get(0).equalsIgnoreCase("0")) {
							sendMessageValuesList("","Primary Customer's Email Id Is Mandatory For Etihad Product.");
							return;
						}
					}
					String sAccClass = formObject.getValue(ACC_HOME_BRANCH).toString();
					logInfo("searchProductList", "sAccClass ,,,,,......"+sAccClass);
					logInfo("searchProductList", "rtnData[0],,,,,,......"+rtnData[0]);
//					objChkRepeater.setEditable(iRows,"AO_PRODUCT_QUEUE.CHEQUE_BOOK",false);
//					objChkRepeater.setEnabled(iRows,"AO_PRODUCT_QUEUE.CHEQUE_BOOK",false);
					formObject.setStyle("table94_cheque_book", DISABLE, TRUE);
//					objChkRepeater.setEnabled(iRows,"AO_PRODUCT_QUEUE.CURRENCY",true);
//					objChkRepeater.setEditable(iRows, "AO_PRODUCT_QUEUE.CURRENCY", true);
					logInfo("searchProductList", " value of i = none "+"... rtnData[2]..."+rtnData[2]);
					setProductCurrencyComboLoadDisable1(rtnData[0]);
//					objChkRepeater.setValue(iRows,"AO_PRODUCT_QUEUE.CURRENCY",rtnData[2]);
					String prodQueueColumns = "Product_Code,Product_Description,Cheque_Book,Account_Branch,"
							+ "WI_NAME,CID,Currency";
					String prodQueueValues = rtnData[0]+","+rtnData[1]+",No,"+rtnData[4]+","+sWorkitemId+","
							+iRows+","+rtnData[2];
					LoadListViewWithHardCodeValues(PRODUCT_QUEUE, prodQueueColumns, prodQueueValues);
					sQuery= "SELECT ACC_NO FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId
							+"' AND ACC_STATUS IN (SELECT DESCRIPTION FROM USR_0_ACCOUNT_STATUS_CODE "
							+ "WHERE CODE IN ('6','8')) AND CUSTOMER_ID='"+sCustID+"'";
					sOutput = formObject.getDataFromDB(sQuery);
					logInfo("searchProductList", "sOutput---"+sOutput);
					if(sOutput.size()>0) {
						sDebitAccNo = sOutput.get(0).get(0);
						logInfo("searchProductList", "sDebitAccNo---"+sDebitAccNo);
						if(!sDebitAccNo.equalsIgnoreCase("")) {
							String sTemp[] =sDebitAccNo.split(",");
							for(int i=0;i<sTemp.length;i++)  {
//								formObject.NGAddItem("AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO", sTemp[i]);
								formObject.addItemInCombo("table94_trnsfr_from_acc_no", sTemp[i]);
							}
						}						
					}
					if(rtnData[3].equalsIgnoreCase("Yes")) {
						formObject.setStyle("table94_cheque_book", DISABLE, FALSE);
					} else {
						formObject.setStyle("table94_cheque_book", DISABLE, TRUE);
					}
					String getquery="SELECT visa_status FROM USR_0_CUST_TXN WHERE cust_sno='"+getPrimaryCustomerSNO()
							+"' AND WI_NAME = '"+sWorkitemId+"'";
					logInfo("searchProductList", "getquery---"+getquery);
					List<List<String>> getqueryOutput = formObject.getDataFromDB(getquery);
					if(getqueryOutput.size()>0) {
						String Visa = getqueryOutput.get(0).get(0);
						logInfo("searchProductList", "getquery---"+Visa);
						if(Visa.equalsIgnoreCase("Under Processing")) {
							formObject.setStyle("table94_cheque_book", DISABLE, TRUE);
						}
					}
				}
			}
			EnableEtihadFrame();
			LoadDebitCardCombo();
			EnableFamilyReffered();
		} catch(Exception e) {
			logError("searchProductList", e);
		}
	}

}
