package com.newgen.iforms.user.ao;

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

public class Referrals extends Common implements Constants, IFormServerEventHandler{
	public String sWorkitemID =formObject.getObjGeneralData().getM_strProcessInstanceId();
	public Referrals(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		

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
		sendMessageList.clear(); 
		try{
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				logInfo("EVENT_TYPE_LOAD","inside");
				eventOnLoadReferals(controlName, eventType,data);
				if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
					formObject.removeItemFromCombo(CRO_DEC, 2); //added by shivanshu for upgrade
				}
			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					logInfo("executeServerEvent","Inside Tab CLick");
					onTabReferral(data);
				} else if(controlName.equalsIgnoreCase("setRemarksHistory")) {
					log.info("IN ARPIT SERVER EVENT");
					createHistory();
				} else if(controlName.equalsIgnoreCase(PRODUCT_QUEUE) ) {
					//	String modetype =manageFundTransfer(); byyamini
				}  else if(controlName.equalsIgnoreCase(BTN_SUBMIT)) {
					logInfo("BTN_SUBMIT","inside btn ");
					if(submitValidationReferals(eventType,data)){
						return getReturnMessage(true, controlName);
					}
					//eventOnClickEventReferals(controlName, eventType,data);
				} 
			}else if(eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				eventOnChangeEventReferals(controlName, eventType,data);
			}
		} catch (Exception e) {
			logError("executeServerEvent",e);
		} finally {
			logInfo("EXECUTESERVEREVENT", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		}
		return null;
	}

	private String eventOnChangeEventReferals(String controlName, String eventType,String data) {
		try {
			if(formObject.getValue(DATA_ENTRY_MODE).toString().equalsIgnoreCase("Quick Data Entry") 
					&& !formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
				fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
				fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
				fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
				if(controlName.equalsIgnoreCase(MANUAL_DOB) && !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					if(formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_DOB,formObject.getValue(controlName).toString());
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_PASSPORTISSDATE) && !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					formObject.setValue(HD_PASS_ISS_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(MANUAL_PASSPORTEXPDATE) && !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					formObject.setValue(HD_PASS_EXP_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(MANUAL_VISAISSDATE) && !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					formObject.setValue(HD_VISA_ISSUE_DATE,formObject.getValue(controlName).toString());
				} else if(controlName.equalsIgnoreCase(MANUAL_VISAEXPDATE) && !formObject.getValue(controlName).toString().equalsIgnoreCase("")) {
					formObject.setValue(HD_EXP_DATE,formObject.getValue(controlName).toString());
				} 
			}
		} catch (Exception e) {

		}
		return "";
	}
	private String eventOnClickEventReferals(String controlName, String eventType,String data) {
		
		boolean result = false;
		if(controlName.equalsIgnoreCase("setRemarksHistory")) {
			log.info("IN ARPIT SERVER EVENT");
			createHistory();
		} else if(controlName.equalsIgnoreCase(PRODUCT_QUEUE) ) {
			//	String modetype =manageFundTransfer(); byyamini
		}   else if(controlName.equalsIgnoreCase(BTN_SUBMIT)) {
			logInfo("BTN_SUBMIT","inside btn ");
			submitValidationReferals(eventType,data);
			logInfo("","inside btn submit");
			if(!formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("")) {
				logInfo("","inside btn submit1");
				if(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Increased")) {
					logInfo("","inside btn submit2");
					result = validateComplianceData();
					if(!validateComplianceData()){
							return getReturnMessage(false, controlName, sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
					}
				} else {
					result = validateComplianceData_NotIncreasedRisk();
				}
			}  else {
				if(formObject.getValue(CUST_CUR_RISK_BANK).toString().equalsIgnoreCase("Increased")) {
					result = validateComplianceData();
					if(!validateComplianceData()){
						return getReturnMessage(false, controlName, sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1).toString());
				}
			} else {
				result = validateComplianceData_NotIncreasedRisk();
			}
		}
		if(!result) {
			return "";
		}
		
		}
		return "" ;
	}
	private boolean validateComplianceData() {
		logInfo("validateComplianceData","INSIDE");
		if(formObject.getValue(NEG_INFO).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData","Value of NEG_INFO"+formObject.getValue(NEG_INFO));
			sendMessageValuesList(NEG_INFO, "Please Select Negative Information value.");
			return false;
		}else if(formObject.getValue(NEG_INFO).toString().equalsIgnoreCase("Yes") 
				&& formObject.getValue(NEG_AXPLAIN).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData","Value of NEG_AXPLAIN"+formObject.getValue(NEG_AXPLAIN));
			sendMessageValuesList(NEG_AXPLAIN, "Please Fill Negative Information Remarks.");
			return false;
		}else if(formObject.getValue(FATF).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData","Value of FATF"+formObject.getValue(FATF));
			sendMessageValuesList(FATF, "Please Select FATF Information value.");
			return false;
		}else if(formObject.getValue(FATF).toString().equalsIgnoreCase("Yes") 
				&& formObject.getValue(FATF_EXPLAIN).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData","Value of FATF_EXPLAIN"+formObject.getValue(FATF_EXPLAIN));
			sendMessageValuesList(FATF_EXPLAIN, "Please Fill FATF Information Remarks.");
			return false;
		}else if(formObject.getValue(KYC).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData","Value of KYC"+formObject.getValue(KYC));
			sendMessageValuesList(KYC, "Please Select KYC Information value.");
			return false;
		}else if(formObject.getValue(KYC).toString().equalsIgnoreCase("Yes") 
				&& formObject.getValue(KYC_AXPLAIN).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData","Value of KYC_AXPLAIN"+formObject.getValue(KYC_AXPLAIN));
			sendMessageValuesList(KYC_AXPLAIN, "Please Fill KYC Information Remarks.");
			return false;
		}
		return true;
	}


	public boolean validateComplianceData_NotIncreasedRisk() {
		logInfo("validateComplianceData_NotIncreasedRisk","INSIDE");
		if(formObject.getValue(NEG_INFO).toString().equalsIgnoreCase("Yes")
				&& formObject.getValue(NEG_AXPLAIN).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData_NotIncreasedRisk","Value of NEG_INFO"+formObject.getValue(NEG_INFO));
			sendMessageValuesList(NEG_AXPLAIN, "Please Fill Negative Information Remarks.");
			return false;
		}
		if(formObject.getValue(FATF).toString().equalsIgnoreCase("Yes") 
				&& formObject.getValue(FATF_EXPLAIN).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData_NotIncreasedRisk","Value of FATF"+formObject.getValue(FATF));
			sendMessageValuesList(FATF_EXPLAIN, "Please Fill FATF Information Remarks.");
			return false;
		}
		if(formObject.getValue(KYC).toString().equalsIgnoreCase("Yes") 
				&& formObject.getValue(KYC_AXPLAIN).toString().equalsIgnoreCase("")) {
			logInfo("validateComplianceData_NotIncreasedRisk","Value of KYC"+formObject.getValue(KYC));
			sendMessageValuesList(KYC_AXPLAIN, "Please Fill KYC Information Remarks.");
			return false;
		}
		return true;
	}

	private void eventOnLoadReferals(String controlName, String eventType,String data) {
		logInfo("eventOnLoadReferals","COMPLIANCE LOAD==="+controlName+"selected row index"+SELECTED_ROW_INDEX);
		
		formObject.setValue(SELECTED_ROW_INDEX, "0");
		formObject.setValue(TXT_CURRWS,sActivityName);
		int iRows = getGridCount(ACC_RELATION);
		logInfo("eventOnLoadReferals",""+iRows);
		//formObject.setValue(SELECTED_ROW_INDEX,formObject.getValue(controlName).toString());
		String sName=formObject.getTableCellValue(ACC_RELATION,iRows,1);
		String sDOB=formObject.getTableCellValue(ACC_RELATION,iRows,5);
		String cust_id=formObject.getTableCellValue(ACC_RELATION,iRows,2);
		logInfo("eventOnLoadReferals","Customer Name"+sName+"dtae of birth"+sDOB+"customer id "+cust_id);
		formObject.setValue(TXT_DOB, formObject.getTableCellValue(ACC_RELATION, 0, 5));
		formObject.setValue(TXT_CUSTOMERNAME, formObject.getTableCellValue(ACC_RELATION, 0, 1));
		formObject.setValue(TXT_CUSTOMERID, formObject.getTableCellValue(ACC_RELATION, 0, 2));
		
		populateComparisonFields();
		frame81_CPD_Disable();
		manualFrameCPDDisable();
		populateStndInstr();
		populateTRSD();
		//loadApplicationAssessmentDataCPD(); // tab removed 
		loadApplicationAssessmentData();
		populateCRSData();
		prefLang();    
		populatePreAssesmentDetails();  //Jamshed
		populatePepAssesmentDetails();
		populateKycMultiDrop();
		populateKYCData();
		formObject.setValue(WI_NAME,sWorkitemId);
		formObject.setValue(CURR_WS_DETAIL,sActivityName);
		formObject.setStyle(CURR_WS_DETAIL,VISIBLE,FALSE);
		formObject.setValue(CRO_REMARKS,"");
		//Frame52_Disable();
		enableControls(new String[] {SI_FRST_PAYMNT,SI_LST_PAYMNT,SEC_DEC});
		disableControls(new String[] {SEC_CC_AORM,SEC_CC_AORC,SEC_CI,SEC_ACC_REL,SEC_SI,SEC_DEL_INST,SEC_INTERNAL_DETL,SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,HD_BTN});
		//formObject.setNGLocked("Tab2", false);
		//formObject.setNGLocked("Frame81", false);
		//formObject.setNGLocked("Frame41", false);
		//disable_custInfo();  // VIA JS
		//formObject.setNGLocked("Frame35",true);
		//formObject.setNGLocked("Frame23", false);
		//formObject.setNGLocked("Repeat_Frame", false);

		if(sActivityName.equalsIgnoreCase("Compliance Approver") || sActivityName.equalsIgnoreCase("PBG Vigilance")) {  // Gurwinder PBG Vigilance Chnage 27062023
			formObject.clearCombo(CRO_DEC);
			formObject.addItemInCombo(CRO_DEC, "Neutral Advisory");
			if(!sActivityName.equalsIgnoreCase("PBG Vigilance")) {
				formObject.addItemInCombo(CRO_DEC, "Neutral Advisory and send to CPD maker");
			}
			formObject.addItemInCombo(CRO_DEC, "Return");
			formObject.addItemInCombo(CRO_DEC, "Negative Advisory");
			formObject.addItemInCombo(CRO_DEC, "COM REV NOT REQD");
			formObject.addItemInCombo(CRO_DEC, "No Objection");
			formObject.setStyle("old_mobile_no","visible", "false");
			formObject.setStyle("new_mobile_no","visible", "false");
			formObject.setStyle("Label341","visible", "false");
			formObject.setStyle("Label342","visible", "false");
			disableControls(new String[] {RA_CB_OTHERS,RA_OTHERS,CHECKBOX_VISA_STATUS_MANUAL,IDS_OTH_CB_OTHERS});
			enableControls(new String[] {});
			//formObject.setNGEnable("Frame72", true);
			//formObject.setNGEnable("Text19", false);
			//formObject.setNGEnable("Text18", false);
			//formObject.setNGEnable("NIG_MAKER", false);
			//formObject.setNGEnable("NIG_CHECKER", false);
			//formObject.setNGEnable("drp_reseida", false);
			//formObject.setNGEnable("NIG_CPDMAKER", false);
			//formObject.setNGEnable("NIG_CPDCHECKER", false);
			setTemp_usr_0_product_selected();
		}

		frame81_CPD_Disable();
		setCPDCheckerCombos();
		populateTRSD();
		populateTRSDCPD();
		int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		String tempQuery="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS "
				+ "WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemID+"'";
		populateUDFGrid(tempQuery);
		if(sActivityName.equalsIgnoreCase("Compliance Approver")){
			populatePOANationality(); //Jamshed
		}
	}
	public void setCPDCheckerCombos() {
		try{
			int sCustNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
			String sQuery = "SELECT DECODE(MANUAL_PREFIX,'','--Select--',MANUAL_PREFIX) MANUAL_PREFIX,"
					+ "DECODE(PREFIX,'','--Select--',PREFIX) PREFIX,DECODE(MANUAL_COUNTRY,'','--Select--',"
					+ "MANUAL_COUNTRY) MANUAL_COUNTRY,DECODE(MANUAL_RESIDENCE_COUNTRY,'','--Select--',"
					+ "MANUAL_RESIDENCE_COUNTRY) MANUAL_RESIDENCE_COUNTRY,DECODE(MANUAL_NATIONALITY,'','--Select--',"
					+ "MANUAL_NATIONALITY) MANUAL_NATIONALITY,DECODE(BIRTH_COUNTRY,'','--Select--',BIRTH_COUNTRY) "
					+ "BIRTH_COUNTRY,DECODE(NATIONALITY,'','--Select--',NATIONALITY) NATIONALITY,"
					+ "DECODE(CORR_CNTRY,'','--Select--',CORR_CNTRY) CORR_CNTRY,DECODE(PER_COUNTRY,'','--Select--',"
					+ "PER_COUNTRY) PER_COUNTRY,DECODE(RES_CNTRY,'','--Select--',RES_CNTRY) RES_CNTRY,"
					+ "DECODE(MANUAL_STATE,'','--Select--',MANUAL_STATE) MANUAL_STATE,"
					+ "DECODE(CORR_STATE,'','--Select--',CORR_STATE) CORR_STATE,"
					+ "DECODE(RES_STATE,'','--Select--',RES_STATE) RES_STATE,DECODE(PER_STATE,'','--Select--',PER_STATE)"
					+ " PER_STATE,DECODE(RM_NAME,'','--Select--',RM_NAME) RM_NAME,DECODE(RM_CODE,'','--Select--',RM_CODE)"
					+ " RM_CODE,DECODE(EMP_PROFESSION_NAME,'','--Select--',EMP_PROFESSION_NAME) EMP_PROFESSION_NAME,"
					+ "DECODE(PROFESSION_CODE,'','--Select--',PROFESSION_CODE) PROFESSION_CODE,"
					+ "DECODE(BLACKLIST_DEC,'','--Select--',BLACKLIST_DEC) BLACKLIST_DEC,"
					+ "DECODE(BAD_CHECK_DEC,'','--Select--',BAD_CHECK_DEC) BAD_CHECK_DEC,"
					+ "DECODE(WORLD_CHECK_DEC,'','--Select--',WORLD_CHECK_DEC) WORLD_CHECK_DEC,"
					+ "DECODE(BLACKLIST_DEC_CPD,'','--Select--',BLACKLIST_DEC_CPD) BLACKLIST_DEC_CPD,"
					+ "DECODE(BAD_CHECK_DEC_CPD,'','--Select--',BAD_CHECK_DEC_CPD) BAD_CHECK_DEC_CPD,"
					+ "DECODE(WORLD_CHECK_DEC_CPD,'','--Select--',WORLD_CHECK_DEC_CPD) WORLD_CHECK_DEC_CPD,"
					+ "DECODE(FINAL_ELIGIBILITY,'','--Select--',FINAL_ELIGIBILITY) FINAL_ELIGIBILITY,"
					+ "DECODE(FINAL_ELIGIBILITY_CPD,'','--Select--',FINAL_ELIGIBILITY_CPD) FINAL_ELIGIBILITY_CPD,"
					+ "DECODE(MANUAL_GENDER,'','--Select--',MANUAL_GENDER) MANUAL_GENDER,"
					+ "DECODE(GENDER,'','--Select--',GENDER) GENDER,DECODE(CUST_SEG,'','--Select--',CUST_SEG) CUST_SEG,"
					+ "DECODE(PROMO_CODE,'','--Select--',PROMO_CODE) PROMO_CODE,"
					+ "DECODE(SYSTEM_DEC,'','--Select--',SYSTEM_DEC)SYSTEM_DEC,DECODE(BANK_DEC,'','--Select--',BANK_DEC)"
					+ " BANK_DEC,DECODE(BANK_DEC_CPD,'','--Select--',BANK_DEC_CPD) BANK_DEC_CPD,"
					+ "DECODE(SYSTEM_DEC_CPD,'','--Select--',SYSTEM_DEC_CPD) SYSTEM_DEC_CPD,"
					+ "DECODE(IS_VVIP_CUSTOMER,'','--Select--',IS_VVIP_CUSTOMER) IS_VVIP_CUSTOMER,"
					+ "DECODE(EIDA_PRESENT,'','--Select--',EIDA_PRESENT) EIDA_PRESENT,"
					+ "DECODE(STAFF_FLAG,'','--Select--',STAFF_FLAG) STAFF_FLAG,"
					+ "DECODE(PURPOSE_TAX,'','--Select--',PURPOSE_TAX) PURPOSE_TAX,"
					+ "DECODE(IS_UAE_RESIDENT,'','--Select--',IS_UAE_RESIDENT) IS_UAE_RESIDENT,"
					+ "DECODE(DEALS_ARMAMENT,'','--Select--',DEALS_ARMAMENT) DEALS_ARMAMENT,"
					+ "DECODE(PEP,'','--Select--',PEP) PEP,DECODE(HAWALA,'','--Select--',HAWALA) HAWALA,"
					+ "DECODE(IS_HOLDING_GREENCARD,'','--Select--',IS_HOLDING_GREENCARD) IS_HOLDING_GREENCARD,"
					+ "DECODE(IS_US_TAX_LIABLE,'','--Select--',IS_US_TAX_LIABLE) IS_US_TAX_LIABLE,"
					+ "DECODE(IS_US_TIN_PRESENT,'','--Select--',IS_US_TIN_PRESENT) IS_US_TIN_PRESENT,"
					+ "DECODE(IS_US_CLASSIFICATION,'','--Select--',IS_US_CLASSIFICATION) IS_US_CLASSIFICATION"
					+ "DECODE(IS_US_RESIDENT,'','--Select--',IS_US_RESIDENT) IS_US_RESIDENT,"
					+ "DECODE(IS_CUST_DETAIL_CHANGE,'','--Select--',IS_CUST_DETAIL_CHANGE) IS_CUST_DETAIL_CHANGE,"
					+ "DECODE(RELIGION,'','--Select--',RELIGION) RELIGION,"
					+ "DECODE(CUST_MARITAL_STATUS,'','--Select--',CUST_MARITAL_STATUS) CUST_MARITAL_STATUS,"
					+ "DECODE(PASS_TYPE,'','--Select--',PASS_TYPE) PASS_TYPE,"
					+ "DECODE(VISA_STATUS,'','--Select--',VISA_STATUS) VISA_STATUS,"
					+ "DECODE(ACC_RELATIONSHIP_PURPOSE,'','--Select--',ACC_RELATIONSHIP_PURPOSE)ACC_RELATIONSHIP_PURPOSE,"
					+ "DECODE(EMPLOYEE_TYPE,'','--Select--',EMPLOYEE_TYPE)EMPLOYEE_TYPE,"
					+ "DECODE(EMP_STATUS,'','--Select--',EMP_STATUS)EMP_STATUS,"
					+ "DECODE(SPECIAL_CATAGORY,'','--Select--',SPECIAL_CATAGORY)SPECIAL_CATAGORY,"
					+ "DECODE(EXELLENCY_CENTER,'','--Select--',EXELLENCY_CENTER)EXELLENCY_CENTER,"
					+ "DECODE(SIGN_STYLE,'','--Select--',SIGN_STYLE) SIGN_STYLE,"
					+ "DECODE(IS_CROSS_BORDER_PAYMENT,'','--Select--',IS_CROSS_BORDER_PAYMENT) IS_CROSS_BORDER_PAYMENT,"
					+ "DECODE(IS_WORK_DEFENCE_UAE,'','--Select--',IS_WORK_DEFENCE_UAE) IS_WORK_DEFENCE_UAE,"
					+ "DECODE(IS_WORK_DEFENCE_NONUAE,'','--Select--',IS_WORK_DEFENCE_NONUAE) IS_WORK_DEFENCE_NONUAE,"
					+ "DECODE(CORR_CITY,'','--Select--',CORR_CITY) CORR_CITY,DECODE(RES_CITY,'','--Select--',RES_CITY) "
					+ "RES_CITY,DECODE(PER_CITY,'','--Select--',PER_CITY) PER_CITY,DECODE(MANUAL_CITY,'','--Select--',"
					+ "MANUAL_CITY) MANUAL_CITY,DECODE(IS_POA_US_PERSON,'','--Select--',IS_POA_US_PERSON) IS_POA_US_PERSON,DECODE(IS_US_INDICIA_FOUND,'','--Select--',IS_US_INDICIA_FOUND) IS_US_INDICIA_FOUND,DECODE(DOCUMENT_TO_BE_COLLECTED,'','--Select--',DOCUMENT_TO_BE_COLLECTED) DOCUMENT_TO_BE_COLLECTED,DECODE(MANUAL_COUNTRY_RESIDENCE,'','--Select--',MANUAL_COUNTRY_RESIDENCE) MANUAL_COUNTRY_RESIDENCE,DECODE(RES_EIDA,'','--Select--',RES_EIDA) RES_EIDA FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemID+"' AND CUST_SNO='"+sCustNo+"'";
			List<List<String>> out =formObject.getDataFromDB(sQuery);
			String[] LIST_OF_CONSTANTS ={MANUAL_PREFIX,CUST_PREFIX,MANUAL_STATE,CORR_STATE,RES_STATE,PERM_STATE
					,MANUAL_CNTRY,MANUAL_PER_CNTRY,MANUAL_NATIONALITY,CNTRY_OF_BIRTH
					,CUST_NATIONALITY,CORR_CNTRY,PERM_CNTRY,RES_CNTRY,PROFESION,PROF_CODE
					,MATCH_FOUND,CRO_MATCH_FOUND,MATCH,CPD_CHK_MATCH_FOUND,CPD_MATCH_FOUND,CPD_MTCH_FOUND
					,FINAL_ELIGIBILITY,CPD_FINAL_ELIGIBILITY,MANUAL_GENDER,CUST_GENDER,PD_CUSTSEGMENT
					,PRO_CODE,CRO_SYS_DEC,CRO_BANK_DECISION,"CPD_BANK_DECISION",CRO_SYS_DEC,GI_IS_CUST_VIP,
					RA_CARRYNG_EID_CARD,ED_SET_FLG,RA_PRPSE_TAX_EVSN,RA_IS_UAE_RESIDENT,RA_IS_CUST_DEALNG_ARMAMNT
					,RA_IS_CUST_PEP,RA_IS_CUST_DEALNG_HAWALA,FAT_US_PERSON,FAT_LIABLE_TO_PAY_TAX,
					FAT_SSN,FAT_CUST_CLASSIFICATION,"Combo45",PD_ANY_CHNG_CUST_INFO,RELIGION,MARITAL_STATUS,
					"Combo34",GI_PURPOSE_ACC_REL,ED_EMP_TYPE,EMP_STATUS,SPECIAL_CAT,EXCELLENCY_CNTR,
					SIGN_STYLE1,ED_CUST_CRS_BRDR_PAYMENT,RA_IS_CUST_WRKNG_UAE,RA_IS_CUST_WRKNG_NON_UAE,
					CP_CITY,PA_CITY,RA_CITY,MANUAL_CITY,POACOMBO,INDICIACOMBO,COMBODOC,MANUAL_RESIDENT
					,DRP_RESEIDA};
			if(out.size() > 0)
			{
				addItemInComboFromDB(out,LIST_OF_CONSTANTS);
			}

			formObject.addItemInCombo(SEARCH_NATIONALITY,"");
			formObject.addItemInCombo(NEW_CUST_NATIONALITY,"");
			formObject.addItemInCombo(HD_FCR_SEARCH,"");
			formObject.addItemInCombo(NATION_CRO,"");
			formObject.addItemInCombo("nation_cpd","");
			formObject.addItemInCombo(SI_CURRENCY,"");

			String ssQuery = "SELECT DECODE(SWEEP_OUT_DEBIT_ACC,'','--Select--',SWEEP_OUT_DEBIT_ACC) SWEEP_OUT_DEBIT_ACC,DECODE(SWEEP_IN_DEBIT_ACC,'','--Select--',SWEEP_IN_DEBIT_ACC) SWEEP_IN_DEBIT_ACC,DECODE(SWEEP_IN_CURR,'','--Select--',SWEEP_IN_CURR) SWEEP_IN_CURR,DECODE(SWEEP_OUT_CURR,'','--Select--',SWEEP_OUT_CURR) SWEEP_OUT_CURR FROM USR_0_STND_INSTR WHERE WI_NAME ='"+sWorkitemID+"'";
			List<List<String>> out1 = formObject.getDataFromDB(ssQuery);
			String[] LIST_OF_CONSTANTS1 ={SWP_OUT_DEB_ACC_NO,SWP_IN_DEB_ACC_NO,SWP_OUT_CURRENCY,SWP_IN_CURRENCY};
			if(out1.size()>0)
			{
				addItemInComboFromDB(out1,LIST_OF_CONSTANTS1);
			}
		 } catch(Exception e) {
			logInfo("setCPDCheckerCombos",e.getMessage());
		}
	}

	public boolean submitValidationReferals(String pEvent, String data) {
		boolean result = false;
		if(!formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("")) {
			if(formObject.getValue(CPD_CUR_RISK_BANK).toString().equalsIgnoreCase("Increased Risk")) {
				result = validateComplianceData();
								
			} else {
				result = validateComplianceData_NotIncreasedRisk();
			}
		} else {
			if(formObject.getValue(CUST_CUR_RISK_BANK).toString().equalsIgnoreCase("Increased Risk")) {
				result = validateComplianceData();
			} else {
				result = validateComplianceData_NotIncreasedRisk();
			}
		}
		if(!result) {
			formObject.setStyle("COMMAND24", "disable", "false");
			return false;
		}
		logInfo("submitValidationReferals","Entered Done Event"+sActivityName);
		String reply;
		String sDecision = formObject.getValue(CRO_DEC).toString();
		if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Neutral Advisory and Send to CPD Maker") ) {
			logInfo("","inside if of(Entered Done Event)");
			String sQuery = "SELECT IS_CPD_MAKER_DONE FROM "+sExtTable+" WHERE WI_NAME='"+ sWorkitemID +"'";
			logInfo("submitValidationReferals$$$",sQuery);
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			logInfo("submitValidationReferals$$$",""+sOutput.get(0).get(0));
			if(!(sOutput.get(0).get(0)).equalsIgnoreCase("true")) {
				sendMessageValuesList(CRO_DEC,"This workitem never visited CPD Maker. Please change the decision");
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
				formObject.setStyle("static_submit",DISABLE,FALSE);
				return false;
			}
		} else if(sDecision.equalsIgnoreCase("")) {
			logInfo("SUBMIT BUTTON","value of deciion"+sDecision);
			sendMessageValuesList(CRO_DEC,"Please Select User Decision");
			return false;
			//setStyle('static_submit',PROPERTY_NAME.DISABLE,FALSE);
		} else if(!sDecision.equalsIgnoreCase("Neutral Advisory")
				&&!sDecision.equalsIgnoreCase("Neutral Advisory and Send to CPD Maker")
				&&!sDecision.equalsIgnoreCase("No Objection"))  {

			logInfo("SUbmit Click","not NA not NAS not NO :"+sDecision);
			String rejectionReason = formObject.getValue(CRO_REJ_REASON).toString();
			String rejectionRemarks = formObject.getValue(CRO_REMARKS).toString();
			logInfo("submit click","reason"+rejectionReason+" and remarks"+rejectionRemarks);
			if(rejectionReason.equalsIgnoreCase("")) {
				sendMessageValuesList(CRO_REJ_REASON, "Please Select Rejection Reason.");
				formObject.setStyle(BUTTON_SUBMIT,DISABLE,FALSE);
				return false;
				//setStyle('static_submit',DISABLE,FALSE);
			}else if(rejectionRemarks.equalsIgnoreCase("")) {
				sendMessageValuesList(CRO_REMARKS, "Please Fill Remarks.");
				formObject.setStyle(BUTTON_SUBMIT,DISABLE,FALSE);
				return false;
				//setStyle('static_submit',DISABLE,FALSE);
			}	
		}
		logInfo("submitValidationReferals","before reply");
		logInfo("submitValidationReferals","datat from js =="+data);
		if(!data.equalsIgnoreCase("")){
			if (data.equalsIgnoreCase("CA008_Clear")) {
				logInfo("submitValidationReferals","CA008_Clearreply");
				createHistory(); 
				createAllHistory();
				formObject.setStyle("COMMAND24",DISABLE,FALSE);
				formObject.setStyle("static_submit",DISABLE,FALSE);
				if(sActivityName.equalsIgnoreCase("Compliance Approver")/* || sActivityName.equalsIgnoreCase("PBG Vigilance")*/) { // Gurwinder PBG Vigilance Chnage 27062023
					logInfo("submitValidationReferals123","222");
					String sUpdateDecision="update "+sExtTable+" set COMP_DEC='"+ formObject.getValue(CRO_DEC) +"' Where WI_NAME='"+ sWorkitemID +"'";
					formObject.saveDataInDB(sUpdateDecision);
					//CH_20012016_001
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Neutral Advisory") 
							|| formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("No Objection") 
							|| formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("COM REV NOT REQD")) {
						formObject.saveDataInDB("update "+sExtTable+" set IS_COMPLIANCE_VISITED='Yes', COMPLIANCE_APPROVE_DATE =sysdate Where WI_NAME='"+ sWorkitemID +"'");
					}

					logInfo("","AT accrelation CALLING FOR EMAIL DATE  :");
					String sProcName="SP_TemplateGenerationEmailDt";
					List<String> paramlist = new ArrayList<String>();
					paramlist.add ("Text :"+sWorkitemId);
					List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
					logInfo("Submitting workitem","Output AT accrelation CALLING FOR EMAIL DATE  :"+sOutput1);
				} else if (sActivityName.equalsIgnoreCase(ACTIVITY_PBG_VIGILANCE)){ // Jamshed PBG Vigilance Chnage 28JUL2023
					logInfo("submitValidationReferals123","222");
					String sUpdateDecision="update "+sExtTable+" set VIGILANCE_DEC='"+ formObject.getValue(CRO_DEC) +"' Where WI_NAME='"+ sWorkitemID +"'";
					formObject.saveDataInDB(sUpdateDecision);
					//CH_20012016_001
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Neutral Advisory") 
							|| formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("No Objection") 
							|| formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("COM REV NOT REQD")) {
						formObject.saveDataInDB("update "+sExtTable+" set IS_VIGILANCE_VISITED='Yes', VIGILANCE_APPROVE_DATE =sysdate Where WI_NAME='"+ sWorkitemID +"'");
					}

					logInfo("","AT accrelation CALLING FOR EMAIL DATE  :");
					String sProcName="SP_TemplateGenerationEmailDt";
					List<String> paramlist = new ArrayList<String>();
					paramlist.add ("Text :"+sWorkitemId);
					List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
					logInfo("Submitting workitem","Output AT accrelation CALLING FOR EMAIL DATE  :"+sOutput1);
				}
			}  else {
				formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
				return false;
			}
		}
		return true;	
	}	

	public void onTabReferral(String data) {
		logInfo("onTabReferral","Inside"+data);
		List<List<String>> recordList;
		try{
			int selectedSheetIndex = Integer.parseInt(data);
			if(selectedSheetIndex == 1){

				logInfo("onTabReferral","Customer Info tab");
				setManualFieldsBlank();
				//setManualChecksBlank();
				setCPDCheckerCombos();
				//populatePersonalDataCPD();
				populateRiskData();
		//		populateKYCData();
		//		populateKycMultiDrop();
				populateComparisonFields();
				PopulatePrivateClientQuestions();  
				loadDedupeSearchData(sWorkitemID);
				int iRows = getGridCount(ACC_RELATION);
				String bnk_relation= formObject.getTableCellValue(ACC_RELATION,iRows,7);
				if(bnk_relation.equalsIgnoreCase("New")||bnk_relation.equalsIgnoreCase("Existing")) {
					visibleOnSegmentBasis(formObject.getValue(PD_CUSTSEGMENT).toString());
					String req_type=formObject.getValue(REQUEST_TYPE).toString();
					if(req_type.equalsIgnoreCase("New Account with Category Change") 
							|| req_type.equalsIgnoreCase("Category Change Only")) 	{
						manageCategoryChangeSection();
					}
				}
			} else if(selectedSheetIndex==6 || selectedSheetIndex==7) {
				logInfo("onTabReferral","TAB 6");
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) 	{
					populateScreeningDataCRO();
				} else {
				}
				logInfo("onTabReferral","CRO");
				populateTRSD();
				populateTRSDCPD();
				logInfo("onTabRefferal","TRSD");
				if( formObject.getValue(TRSD_APPROVEDRESULT).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(CPD_MTCH_FOUND, "Eligible");
				else
					formObject.setValue(CPD_MTCH_FOUND, "Not Eligible");
			} else if(selectedSheetIndex==8 || selectedSheetIndex==9) {
				String sQuery ="";
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					loadApplicationAssessmentData();
					if(getGridCount(FAC_OFRD_LVW_CRO)==0) {
						sQuery= "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemID+"'";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						//LoadListView(sQuery,3,"OFFERED_DEBIT_CARD_GRID","0,1,2");
						loadListView(out, "CUST_ID,CUST_NAME,CARD_TYPE", FAC_OFRD_LVW_CRO);
					}
					if(getGridCount(PROD_OFRD_CRO_LVW)==0)   {
						sQuery= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out, "PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC", PROD_OFRD_CRO_LVW);
					}
					if(getGridCount(FAC_LVW_CRO)==0)  {
						sQuery ="SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY FACILITY";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out, "CID,FACILITY,DESCRIPTION", FAC_LVW_CRO);
					}
				}
				loadApplicationAssessmentDataCPD();
				if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
					if(getGridCount(PROD_SEC_OFRD_CPD_LVW)==0) {
						sQuery= "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC FROM USR_0_PRODUCT_OFFERED_CPD WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out,"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC",PROD_SEC_OFRD_CPD_LVW);	
					}
					if(getGridCount(FAC_LVW_CPD)==0) {
						sQuery ="SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD WHERE WI_NAME ='"+sWorkitemID+"' ORDER BY FACILITY";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out,"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
					}
					if(getGridCount(FAC_OFRD_LVW_CPD)==0) {
						sQuery= "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemID+"'";
						List<List<String>> out = formObject.getDataFromDB(sQuery);
						loadListView(out,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
					}
				}
			} else if(selectedSheetIndex == 10) {	
				formObject.setStyle("Frame37", DISABLE ,TRUE);
				formObject.setStyle("Frame54", DISABLE ,TRUE);
				formObject.setStyle("button_generate_template", DISABLE ,TRUE);
				formObject.setStyle(PRODUCT_QUEUE, DISABLE ,TRUE);
				formObject.setStyle("AO_ACC_TITLE", DISABLE ,TRUE);
				formObject.setStyle("search", DISABLE ,TRUE);
				formObject.setStyle("add_product", DISABLE ,TRUE);
				formObject.setStyle("remove", DISABLE ,TRUE);
				LoadDebitCardCombo();
			} else if(selectedSheetIndex == 14 ) {//Standing Instruction 
				loadSICombos();
				populateStndInstr();
			} else if(selectedSheetIndex == 12) {//Delivery Preferences
				Frame_delivery();
			} else if(selectedSheetIndex == 19) {// Changes for Family Banking //Decision 
			//} else if(selectedSheetIndex == 18) {//Decision
				int iCount = getListCount(CRO_REJ_REASON);
				if(iCount==0 || iCount==1) {
					//formObject.getNGDataFromDataSource("Select '--Select--' from dual Union Select to_char(ws_rej_reason) from usr_0_rej_reason_mast",1,"AO_CRO_REJ_REASON");
				}
				if(getGridCount(DECISION_LVW) == 0) {
					logInfo("DECISION_LVW","");
					String sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					logInfo("DECISIONTAB",sQuery);
					recordList = formObject.getDataFromDB(sQuery);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,"
							+ "WS_NAME,WS_COMMENTS",DECISION_LVW);							
					formObject.setStyle(DEC_STORAGE_DETAILS,VISIBLE,TRUE);
				}
				//loadListView("SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemID+"') ORDER BY A","LISTVIEW_DECISION","0,1,2,3,4,5,6,7,8");							

				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					String[] cName = {IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ,IS_CALL_BACK_REQ};
					disableControls(cName);
					hideControls(new String[]{L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ});
				} else {
					String[] controlName = {L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ};
					disableControls(controlName);
				}
			}

			if(sActivityName.equalsIgnoreCase("Compliance Approver")){
				populatePOANationality(); //Jamshed
			}

		} catch(Exception e) {

		}
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
	public void updateDataInWidget(IFormReference arg0, String arg1) {
		

	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1,
			File arg2, Locale arg3) {
		
		return null;
	}

	@Override
	public JSONArray validateSubmittedForm(FormDef arg0, IFormReference arg1,
			String arg2) {
		
		return null;
	}

}
