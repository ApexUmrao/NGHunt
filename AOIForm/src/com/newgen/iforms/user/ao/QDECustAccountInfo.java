package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.user.config.ConstantAlerts;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class QDECustAccountInfo extends Common implements Constants,
ConstantAlerts, IFormServerEventHandler {
	String sBackRoute = "";
	String sOnLoad = "false";
	String sLoadingDone = "false";
	boolean sCurrFlag = false;
	int sCurrTabIndex;

	public QDECustAccountInfo(IFormReference formObject) {
		super(formObject);
	}
	
	@Override 
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {

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
	public String executeServerEvent(IFormReference formObject,	String controlName, String eventType, String data) {
		logInfo("onQDEFormLoad","Inside executeServerEvent QDECustAccountInfo >");
		logInfo("onQDEFormLoad", "Event: " + eventType + ", Control Name: "+ controlName + ", Data: " + data);
		sendMessageList.clear();
		String retMsg = getReturnMessage(true);
		List<List<String>> recordList = null;
		try {
			//START OF LOAD EVENT 
			iProcessedCustomer=Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+1;
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				String decision = "";
				String sCompDec = "";
				Map mp = new HashMap();
				populateAuditSearch(SEARCH_DETAILS_LVW);
				logInfo("onQDEFormLoad", "LOAD PASSPORT");
				logInfo("onQDEFormLoad", "LOAD VISA");
				try {
					String qry = "select cro_dec,comp_dec from " + sExtTable + " where wi_name ='" + sWorkitemId + "'";
					logInfo("onQDEFormLoad", "sQuery1" + qry);
					List<List<String>> list = formObject.getDataFromDB(qry);
					if (list != null && !list.isEmpty()) {
						for (int i = 0; i < list.size(); i++) {
							decision = list.get(i).get(0);
							sCompDec = list.get(i).get(1);
						}
					}
					if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
						//editButtonVisible();
						try {
							String minorDOB = "";
							int minorAge = 0;
							String guardianCount = "";
							String sProduct = "";
							String sQuery1 = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME ='"+ sWorkitemId + "'";
							List<List<String>> list1 = formObject.getDataFromDB(sQuery1);
							if (list1 != null && !list1.isEmpty()) {
								for (int i = 0; i < list1.size(); i++) {
									minorDOB = list1.get(i).get(0);
									minorAge = CalculateAge(minorDOB);
								}
							}						
							if ((minorAge >= 18) && (minorAge <= 21)) {
								sQuery1 = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER "
										+ "WHERE acc_relation = 'Guardian' AND WI_NAME ='"+ sWorkitemId + "'";
								logInfo("LOAD","sQuery1 ::"+sQuery1);
								List<List<String>> list2 = formObject.getDataFromDB(sQuery1);
								if (list2 != null && !list2.isEmpty()) {
									for (int i = 0; i < list2.size(); i++) {
										guardianCount = list2.get(i).get(0);
									}
								}
								if ("0".equalsIgnoreCase(guardianCount)) {
									formObject.setStyle(DOC_APPROVAL_OBTAINED,VISIBLE, TRUE);
									formObject.setStyle(COURT_ORD_TRADE_LIC,VISIBLE, TRUE);
									formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, FALSE);
									formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, FALSE);
								}
							}
							String sQuery2 = "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME"+"='"+sWorkitemId+"'";
							logInfo("QDE ACCOUNT INFO LOAD","sQuery1 ::"+sQuery2);
							List<List<String>> list2 = formObject.getDataFromDB(sQuery2);
							if (list2 != null && !list2.isEmpty()) {
								for (int i = 0; i < list2.size(); i++) {
									sProduct = list2.get(i).get(0);
								}
							}
							logInfo("onQDEFormLoad", "sProduct----" + sProduct);
							if ("871".equalsIgnoreCase(sProduct)) {
								formObject.setStyle(DOC_APPROVAL_OBTAINED,VISIBLE, FALSE);
								formObject.setStyle(COURT_ORD_TRADE_LIC,VISIBLE, FALSE);
							}
						} catch (Exception e) {
							logError("Exception in Form Load QDE Account Info",e);
						}
					} else if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {
						int firstSelected = 0;
						formObject.setValue(CRS_CERTI_YES, TRUE);
						formObject.setValue(CRS_CERTIFICATION_OBTAINED, "Yes");
						int selectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
						String qryFirstSelected = "select FIRST_SELECTED_ROW_INDEX from "+ sExtTable+ " where wi_name ='"
								+ sWorkitemId + "'";
						logInfo("onQDEFormLoad", "qryFirstSelected "+ qryFirstSelected);
						List<List<String>> list1 = formObject.getDataFromDB(qryFirstSelected);
						if (list1 != null && !list1.isEmpty()) {
							for (int i = 0; i < list1.size(); i++) {
								firstSelected = Integer.parseInt(list1.get(i).get(0));
							}
						}
						int customerSearched = 0;
						try {
							customerSearched = Integer.parseInt((formObject.getValue(NO_OF_CUST_SEARCHED).toString()));
						} catch(Exception e){
							logInfo("onQDEFormLoad", "ERROR in customerSearched Integer Parsing");
						}

						int counter = 0;
						try {
							for (int i = 0; i < customerSearched; i++) {
								if (counter != 0) {
									firstSelected = firstSelected + 1;
								}
								if (firstSelected == customerSearched) {
									firstSelected = 0;
								}
								counter++;
								mp.put(counter, firstSelected);
							}
						} catch (Exception e) {
							logError("Form Load QDE CUST Info::", e);
						}
						//						int custProcessed = Integer.parseInt(formObject.getValue(CUST_PROCESSED).toString());
						//						logInfo("onQDEFormLoad"," Final selectedRow  "+ mp.get(custProcessed));
						////						if (mp.get(custProcessed) == null) {
						////							selectedRow = 1;
						////						} else {
						////							selectedRow = (Integer) mp.get(custProcessed);
						////						}
						//						logInfo("onQDEFormLoad"," Final selectedRow  "+ selectedRow);
						//						formObject.setValue(SELECTED_ROW_INDEX, selectedRow+ "");
						//						iProcessedCustomer = selectedRow;
						if(!formObject.getValue(CUST_PROCESSED).toString().isEmpty()) {
							int custProcessed = Integer.parseInt(formObject.getValue(CUST_PROCESSED).toString());
							if(mp.get(custProcessed) != null) {
								selectedRow = (Integer)mp.get(custProcessed);
								log.info("$$$$$$$$$$$ Final selectedRow $$$$$$$$$$$$$$ "+selectedRow);
								formObject.setValue(SELECTED_ROW_INDEX, String.valueOf(selectedRow));
								iProcessedCustomer = selectedRow;
							}
						}
						frame81_CPD_Disable();
						logInfo("fatca---","fatcaa q");
						FrameFATCA_CPD_Enable();
						populateRiskDataQDE();
						logInfo("onQDEFormLoad", "");
						mohit_flag = false;
						logInfo("onQDEFormLoad","");
						populateHiddenDataQDE();
						populateComparisonFields();
						populatePepAssesmentDetails(); //AO DCRA
						logInfo("onQDEFormLoad","outside SIGN"+ formObject.getValue(SIGN_STYLE1));
						if ("".equalsIgnoreCase(formObject.getValue(SIGN_STYLE1).toString())) {
							formObject.setValue(SIGN_STYLE1,"Signature in English");
							logInfo("onQDEFormLoad","INSIDE SIGN"+ formObject.getValue(SIGN_STYLE1));
						}
						setManualFieldsEnable();
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); //+ 1;sharan
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION, fieldToFocus, 0);
						manageFATCAFieldsEnable("Yes", sAccRelation);
						if ((formObject.getValue(ED_MONTHLY_INCM).toString()).equalsIgnoreCase("")) {
							formObject.setValue(ED_MONTHLY_INCM, "0");
						}
						if ((formObject.getValue(IDS_CB_VVIP).toString()).equalsIgnoreCase("false")) {
							formObject.setStyle(GI_YEARS_IN_UAE, DISABLE, TRUE);
						} else {
							formObject.setStyle(GI_YEARS_IN_UAE, DISABLE, FALSE);
						}
						formObject.setStyle(NIG_MAKER, DISABLE, TRUE);
						formObject.setStyle(NIG_CHECKER, DISABLE, TRUE);
						if ((formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString()).equalsIgnoreCase(TRUE)) {
							manageManualCheckBoxes();
						} else {
							setManualFieldsLock();
						}
						logInfo("onQDEFormLoad", "After CHECKBOX_SELECTALL_MANUAL");
						if ((formObject.getValue(CUST_VVIP).toString()).equalsIgnoreCase("")) {
							logInfo("CUST_VVIP", "");
							formObject.setValue(CUST_VVIP, "No");
						}
						if ((formObject.getValue(HAWALA).toString()).equalsIgnoreCase("")) {
							logInfo("onLoad", "HAWALA");
							formObject.setValue(HAWALA, "No");
						}
						if ((formObject.getValue(DEALS_IN_ARMAMENT).toString()).equalsIgnoreCase("")) {
							logInfo("DEALS_IN_ARMAMENT", "");
							formObject.setValue(DEALS_IN_ARMAMENT, "No");
						}
						if ((formObject.getValue(TAX_EVASION).toString()).equalsIgnoreCase("")) {
							formObject.setValue(TAX_EVASION, "No");
						}
						if ((formObject.getValue(ARE_U_PEP).toString()).equalsIgnoreCase("")) {
							formObject.setValue(ARE_U_PEP, "No");
						}
						String sNationality = getFinalDataComparison(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,
								CHECKBOX_NATIONALITY_MANUAL, FCR_NATIONALITY,EIDA_NATIONALITY, MANUAL_NATIONALITY).trim();
						if (sNationality.equalsIgnoreCase("UNITED STATES")) {
							formObject.setValue(FAT_US_PERSON, "YES");
							formObject.setValue(FAT_LIABLE_TO_PAY_TAX, "YES");
							formObject.setStyle(FAT_US_PERSON, DISABLE, TRUE);
							formObject.setStyle(GI_YEARS_IN_UAE, DISABLE, TRUE);
						} else if ((formObject.getValue(FAT_US_PERSON).toString()).equalsIgnoreCase("")) {
							formObject.setValue(FAT_US_PERSON, "");
							formObject.setValue(FAT_LIABLE_TO_PAY_TAX, "");
							formObject.setStyle(FAT_LIABLE_TO_PAY_TAX, DISABLE,FALSE);
						}
						if (!(formObject.getValue(FCR_NAME).toString()).equalsIgnoreCase("")) {
							formObject.setStyle(CUST_SEGMENT1, DISABLE, TRUE);
						} else {
							formObject.setStyle(CUST_SEGMENT1, DISABLE, FALSE);
						}
						if ((formObject.getValue(REQUEST_TYPE).toString()).equalsIgnoreCase("New Account")) {
							String acc_rel = formObject.getTableCellValue(ACC_RELATION, 1, 9);
							String pri_cust_cat = formObject.getValue(CUST_SEGMENT1).toString();
							logInfo("onQDEFormLoad","Customer segment------------::::"+ pri_cust_cat);
							if (!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA"))
									&& pri_cust_cat.equalsIgnoreCase("Signatory")) {
								sendMessageValuesList("","Primary Signatory customer can't open a new account");
							}
						}
						logInfo("onQDEFormLoad","fieldval out if "+ formObject.getValue(CHECKBOX_VISA_NO_MANUAL));
						if ((formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString()).equalsIgnoreCase(TRUE)
								&& (!(formObject.getValue(MANUAL_VISANO).toString()).equalsIgnoreCase("MDSA"))) {
							logInfo("onQDEFormLoad","fieldval in if "+ formObject.getValue(CHECKBOX_VISA_NO_MANUAL));
							formObject.setStyle(MANUAL_VISANO, DISABLE, FALSE);
						}
						saveComparisonData();
						saveCustNeedAnalysisData();
						saveHiddenDataQDE();
//						saveKYCInfoRetailQDE(); // Added by jamshed
//						savePreAssessmentDetails();   //shahbaz
						//saveCRSDetails();
						logInfo("onQDEFormLoad","outside SIGN"+ formObject.getValue(SIGN_STYLE1));
						if ("".equalsIgnoreCase(formObject.getValue(SIGN_STYLE1).toString())) {
							formObject.setValue(SIGN_STYLE1,"Signature in English");
							logInfo("onQDEFormLoad","INSIDE SIGN"+ formObject.getValue(SIGN_STYLE1));
						}
						//Jamshed ao additional
						String private_clnt_flg="";
						String pri_query ="select private_client from ext_ao where wi_name='"+sWorkitemId+"'";
						logInfo("On load QDE_Cust_Info","pri_query= "+ pri_query);
						List<List<String>> pri_clnt_list =formObject.getDataFromDB(pri_query); 
						if (pri_clnt_list != null && pri_clnt_list.size() > 0) {
							private_clnt_flg=pri_clnt_list.get(0).get(0); 
							logInfo("On load QDE_Cust_Info","private_clnt_flg= "+ private_clnt_flg);
						}
						if ((formObject.getValue(REQUEST_TYPE).toString()).equalsIgnoreCase("New Account") || 
								(formObject.getValue(REQUEST_TYPE).toString()).equalsIgnoreCase("New Account with Category Change")) {
							if(private_clnt_flg !=null && !private_clnt_flg.equalsIgnoreCase("") && private_clnt_flg.equalsIgnoreCase("Y") ){
								formObject.setValue(CUST_SEGMENT1, "Private Clients");
								formObject.setStyle(CUST_SEGMENT1, DISABLE, TRUE);
							}
						}
					} 
					if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
						String req_type = formObject.getValue(REQUEST_TYPE).toString();
						String[] disableBagSet = {SEC_DOC_REQ_CRO,NEW_DEL_MODE,CHANNEL_NAME,EXISTING_NOM_PRSN,
								BRNCH_OF_INSTANT_ISSUE };// Frame17,Frame2, to
						// be added
						disableControls(disableBagSet);
						String acc_type = formObject.getValue(REQUEST_TYPE).toString();
						prefLang();
						frame81_CPD_Disable();
						logInfo("fatca---","FATCA F3");
						frameFatcaCpdDisable();
						logInfo("onQDEAccInfoFormLoad","Populating CustInfo tab data");
						populateHiddenDataQDE();
						populateComparisonFields();
						populatePepAssesmentDetails(); //AO DCRA
						if (req_type.equalsIgnoreCase("New Account with Category Change")
							|| req_type.equalsIgnoreCase("Category Change Only")|| 
							req_type.equalsIgnoreCase("Upgrade"))  {
							String[] BagsetOne = { SEC_CAT_CHNG,SEC_INTERNAL_DETL, SEC_ACC_INFO_ECD };
							String[] BagsetTwo = { OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE, OLD_CUST_SEGMENT };
							disableControls(BagsetTwo);
							enableControls(BagsetOne);
							if (req_type.equalsIgnoreCase("Category Change Only")|| req_type.equalsIgnoreCase("Upgrade")) {
								formObject.setStyle(SEC_ACC_INFO_PD, DISABLE,TRUE);
							}
							
						}else if (req_type.equalsIgnoreCase("Downgrade")) {  //downgrade changes
							saveComparisonData();
							saveCustNeedAnalysisData();
							saveHiddenDataQDE();
						} else {
							String[] BagsetOne = { SEC_CAT_CHNG,SEC_INTERNAL_DETL, SEC_ACC_INFO_ECD };
							String[] BagsetTwo = { OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE, OLD_CUST_SEGMENT };
							disableControls(BagsetOne);
							formObject.setStyle(SEC_ACC_INFO_PD, DISABLE, FALSE);
							enableControls(BagsetOne);
						}

						//DECISION TAB CODE
						String[] clearField = { CRO_REMARKS, ACC_INFO_PD_LVW }; //Clearing data in Account Info ListView
						clearControls(clearField);
						formObject.setValue(CRO_REMARKS, "");
						formObject.setValue(TXT_DOB, formObject.getTableCellValue(ACC_RELATION, 0, 5));
						formObject.setValue(TXT_CUSTOMERNAME, formObject.getTableCellValue(ACC_RELATION, 0, 1));
						formObject.setValue(TXT_CUSTOMERID, formObject.getTableCellValue(ACC_RELATION, 0, 2));


						//QUERY FOR POPULATING MAKER CODE AND NAME
						String sQuery = "SELECT USERNAME,PERSONALNAME||' ' ||FAMILYNAME AS NAME FROM PDBUSER "
								+ "WHERE UPPER(USERNAME) =UPPER('"+ sUserName + "')";
						recordList = formObject.getDataFromDB(sQuery);
						logInfo("LOAD ",sQuery);
						formObject.setValue(MAKER_CODE, recordList.get(0).get(0));
						formObject.setValue(MAKER_NAME, recordList.get(0).get(1));


						//QUERY FOR POPULATING DATA IN UDF FIELS AND VALUE GRID
						int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) ;
						String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS"
								+ " WHERE ACC_RELATION_SERIALNO='"+ (custNo+1)+ "' AND WI_NAME='"+ sWorkitemId+ "'";
						populateUDFGrid(sQuery2);
						sQuery = "SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"
								+ sUserName+ "') AND PROCESSDEFID='"+ sProcessDefId + "'";
						recordList = formObject.getDataFromDB(sQuery);
						addDataInComboFromQuery(sQuery, MAKER_DEPT);
						if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")) {
						setTemp_usr_0_product_selected();// to be made
					   }
					}
					fieldValueUsr_0_Risk_Data();// to be made
					String sQuery = "select PROD_CODE,CURRENCY from usr_0_product_selected where wi_name='"
							+ sWorkitemId + "'";
					recordList = formObject.getDataFromDB(sQuery);
					formObject.setStyle("Pic1", VISIBLE, TRUE);
					formObject.setStyle("Tab5", VISIBLE, TRUE);
					// setTabVisible();//to be made
					disableEidaField();
					sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"+ sWorkitemId + "' AND STATUS='Success'";
					recordList = formObject.getDataFromDB(sQuery);
					String sCallName = formObject.getValue("CALL_NAME").toString();
					String sLoadingDone = TRUE;
					if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {
						logInfo("fatca---","fatcaa");
						FrameFATCA_CPD_Enable();
					}
					decision = formObject.getValue(CRO_DEC).toString();
					sCompDec = formObject.getValue(COMP_DEC).toString();
					if (decision.equalsIgnoreCase("Permanent Reject - Discard")
							||sCompDec.equalsIgnoreCase("Negative Advisory"))
					{
						// formObject.NGMakeFormReadOnly();
						String[] BagsetOne = { NEG_INFO, FATF, KYC,SEC_PERSONAL_DET, HIDDEN_SEC_ACC_REL,
								INTERNAL_DETAIL_SECTION, SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS, SEC_ASSESS_OTH_INFO,
								SEC_ACC_INFO_ECD, SEC_ACC_INFO_DCL,SEC_INTERNAL_DETL };// frame id to be updated
						disableControls(BagsetOne);
						String[] BagsetTwo = { CRO_DEC, CRO_REMARKS,CRO_REJ_REASON };
						enableControls(BagsetTwo);
						frame81_CPD_Disable();
						manualFrameCPDDisable(); // manual_frame_CPD_disable
					}
					if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
						frame23_CPD_Disable();
						frameDelivery();
						Frame48_CPD_Disable();
						if ((sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION"))) {
							logInfo("onLoad"," Inside Returned Case After Account Creation or customer"
									+ " creation call. ");
							formObject.setStyle("search", DISABLE, TRUE);
							formObject.setStyle("add", DISABLE, TRUE);
							formObject.setStyle("remove", DISABLE, TRUE);
						}
						populatePOANationality(); //Jamshed
						additionalSource();
					}
					if (!formObject.getValue("COUNT_WI").toString().equalsIgnoreCase("0")
							&& sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {
						// formObject.setNGEnable("SUBMIT_1",true);
					}
					if (decision.equalsIgnoreCase("Permanent Reject - Discard")
							|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
						// formObject.clearCombo(CRO_DEC);
						// formObject.NGAddItem(CRO_DEC,"");
						// formObject.NGAddItem(CRO_DEC,"Permanent Reject - Discard");
						// formObject.setNGListIndex(CRO_DEC,0);
					}


					String req_type = formObject.getValue(REQUEST_TYPE).toString();
					if (req_type.equalsIgnoreCase("New Account with Category Change")
							|| req_type.equalsIgnoreCase("Category Change Only")) {
						if (!formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {

						} else { 
							String[] bagSetFive = {IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
									IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE, IS_VVIP,
									IS_PREVILEGE_TP_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE,
									IS_SHOPPING_CAT_CHANGE,IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE,
									IS_EXCELLENCY_TP_CAT_CHANGE,IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER };
							disableControls(bagSetFive);
						}
						//setResidentWithoutEidaComboQDE();
						//deleteECBCallsDetails();
					}
					formObject.setStyle(BTN_VALIDATE, DISABLE, FALSE);
				} catch (Exception e) {
					log.error("Exception in Form Load QDE CUST ACCT INFO", e);
				}
				logInfo("load", "setResidentWithoutEidaComboQDE before");
				setResidentWithoutEidaComboQDE();
				logInfo("load", "setResidentWithoutEidaComboQDE after");
				deleteECBCallsDetails();
				formObject.setStyle(FAT_CUST_CLASSIFICATION, DISABLE, TRUE);
				formObject.setStyle(INDICIACOMBO, DISABLE, TRUE);
				if(formObject.getValue(SALARY_TRANSFER).toString().equals("")){
					formObject.setValue(SALARY_TRANSFER,"No");
				}
				checkCRS();
				CalculateAccTitle();
				LoadInstantDelivery();
				fbValidation();	//family banking
				populateUAEPassInfoStatus(sWorkitemId);
				logInfo("load", "populatePreAssesmentDetails before");
				populatePreAssesmentDetails(); //Shivanshu
				populateKycMultiDrop();
				logInfo("load", "populatePreAssesmentDetails after");
				populatePepAssesmentDetails(); //AO DCRA
				accountPurpose(); //AO DCRA
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {     
				if("PRODUCT_ROW_CLICK".equalsIgnoreCase(controlName)) {
					enableDisableChequebookField();
					enableDisableProductFields();
				} else if(controlName.equalsIgnoreCase(REMOVE_PRODUCT)) {
					if(removeProducts(Integer.parseInt(data))){
						return getReturnMessage(true, controlName);
					}
				} else if(BTN_FG_VALIDATE.equalsIgnoreCase(controlName)) { 	// Changes for Family Banking
					if(validateFBFetch()){
						familyBankingCalls();
					}
				} else if(controlName.equalsIgnoreCase(FETCH_INFO)) { //DP
					executeFetch(data);
					if(controlName.equalsIgnoreCase(FETCH_INFO)  && formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("CHANGE EVENT: FETCH_INFO","Fetch info button clicked after no radio button is clicked........111111");
						formObject.setStyle(NOM_REQ, DISABLE, TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN, DISABLE, TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
					}
				}else if (RD_INST_DEL.equalsIgnoreCase(controlName)){
					logInfo("RD_INST_DEL","inside if  no radio button is clicked");
					if(formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("No"))
					{
						FETCH_INFO_flag_NO=false;
						FETCH_INFO_flag=false;
						formObject.setStyle(NOM_REQ,DISABLE,TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
						logInfo("","KioskId is null.");
						int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'true'","WI_NAME ='"+sWorkitemId+"'");
						logInfo("","Update query output sout-----"+sOut1);
						int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'false'","WI_NAME ='"+sWorkitemId+"'");
					} else if (formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("","KioskId is null.");
						int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'false'","WI_NAME ='"+sWorkitemId+"'");
						logInfo("","Update query output sout-----"+sOut1);
						int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'true'","WI_NAME ='"+sWorkitemId+"'");
				}
				} else if("postMobileConfirm".equalsIgnoreCase(controlName)) {
					/*mobileConfirmFlag = true;
					mobileChangeFlag = false;
					if("no".equalsIgnoreCase(data)) {
						validateMobileNo2();
					}*/
				} else if(PRODUCT_QUEUE.equalsIgnoreCase(controlName) || "PROD_CODE".equalsIgnoreCase(controlName)) {
					if(addProductInGrid()) {
						return getReturnMessage(true, controlName);
					}
				} else if(TABCLICK.equalsIgnoreCase(controlName)) {
					/*if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
						editButtonVisible();
					}*/
					onTabClickQDE(data);
				} else if(controlName.equalsIgnoreCase(DC_BTN_ADD)){
					if(addDebitCard()){
						return  getReturnMessage(true, DC_BTN_ADD);
					}
				} else if(controlName.equalsIgnoreCase("DELETEPRODUCTROW")){
					updateCustSnoInProductGrid(Integer.parseInt(data));
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) { 
					onSaveTabClickQDE(Integer.parseInt(data.split(",")[1]));
					if(saveAndNextValidationQDE((String)data.split(",")[1])) {
						logInfo("saveNextTabClick","saveAndNextValidationQDE successful");
						return getReturnMessage(true, controlName);
					}
				} else if (controlName.equalsIgnoreCase(MANUAL_CNTRY)) {
					if (!formObject.getValue(MANUAL_CNTRY).toString().equalsIgnoreCase("")) {
						setCityMaunal();
					}
				} else if(controlName.equalsIgnoreCase(COMBODOC)&&formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN")){	
					formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
				} else if (controlName.equalsIgnoreCase(MANUAL_PREFIX)) {
					setGender();
				} else if (controlName.equalsIgnoreCase(TRSD_CHECK)) { //TRSD CHECK
					logInfo("TRSD_CHECK","TRSD=========================");
					if (formObject.getValue("trsd_approvedResult").toString().equalsIgnoreCase("Approved")) {
						formObject.setValue(CPD_MTCH_FOUND, "Eligible");// SANCTION SCREENING
					} else {
						formObject.setValue(CPD_MTCH_FOUND, "Not Eligible");// SANCTION SCREENING
					}
				} else if(controlName.equalsIgnoreCase("SAVE_TAB_CLICK_QDE")){
					onSaveTabClickQDE(Integer.parseInt(data));
				} else if (controlName.equalsIgnoreCase(MANUAL_RESIDENT)) {
					flag_phone_start = TRUE;
					flag_phone = true;
					flag_mobile = true;
					logInfo("SAVE_TAB_CLICK_QDE",flag_phone_start + flag_mobile + flag_phone);
				} else if (controlName.equalsIgnoreCase("AccInfo_UdfList")) {  //ACCOUNT INFO SAVE AND NECT BUTTON
					logInfo("AccInfo_UdfList","Inside udf_addRow");
					logInfo("AccInfo_UdfList","ACC_INFO_UDF_FIELD: " 
							+ formObject.getValue("table131_UDF Field").toString());
					if (formObject.getValue("table131_UDF Field").toString().equalsIgnoreCase("")) {
						sendMessageValuesList("table131_UDF Field","Select a UDF field first.");
						//						return false;
					} else {
						if (UdfUniquenessCheck(formObject.getValue("table131_UDF Field").toString())) {
							logInfo("AccInfo_UdfList","Inside udf_addRow 1");
							if (formObject.getValue("table131_UDF Field").toString().equalsIgnoreCase("Graduation Date")) {
								logInfo("AccInfo_UdfList","Inside udf_addRow 2");
								if (validateJavaDate(formObject.getValue("table131_UDF Value").toString())) {
									logInfo("AccInfo_UdfList","Inside udf_addRo 3");
									/*String colnames = "UDF_FIELD,UDF_VALUE";
									String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##"
											+ formObject.getValue(ACC_INFO_UDF_VALUE);
									//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
									formObject.setValue(ACC_INFO_UDF_VALUE, "");*/
								} else {
									sendMessageValuesList("table131_UDF Value","Date entered by you is not valid");
								}
							} else {
								logInfo("UDF_ADDROW","Inside UDF_ADDROW 4");
								/*String colnames = "UDF_FIELD,UDF_VALUE";
								String values = formObject.getValue(ACC_INFO_UDF_FIELD)+ "##" 
										+ formObject.getValue(ACC_INFO_UDF_VALUE);*/
								//LoadListViewWithHardCodeValues("ACCINFO_UDF_LIST", colnames, values);
//								formObject.setValue(ACC_INFO_UDF_VALUE, "");
							}
						} else {
							sendMessageValuesList("table131_UDF Field",formObject.getValue("table131_UDF Field").toString()
									+ " field already exist");
						}
					}
				}  else if (controlName.equalsIgnoreCase(CRS_ADD)) {  //CRS TAB ADD BUTTON
					logInfo("CRS_ADD","CRS add clicked");
					int gridCount = getGridCount(CRS_TAXCOUNTRYDETAILS);
					logInfo("Gautam","<<<< 62 >>>>>>>>>");
					//Added by Shivanshu For CRS_TIN 22-11-2024
					boolean TINFlag = validateCRSTINField(); 
					if (!(null == formObject.getValue(CRS_TAX_COUNTRY)
							|| formObject.getValue(CRS_TAX_COUNTRY).toString().equalsIgnoreCase("") 
							|| formObject.getValue(CRS_TAX_COUNTRY).toString().equalsIgnoreCase("."))) {
						if (!(null == formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER) 
								|| formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString().equalsIgnoreCase(""))) {
							if (crsGridUniquenessCheck(formObject.getValue(CRS_TAX_COUNTRY).toString())) {
								if ((!(null == formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER) 
										|| "".equalsIgnoreCase(formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString())))
										&& ((!(null == formObject.getValue(CRS_REASONNOTPROVIDINGTIN) 
										|| "".equalsIgnoreCase(formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString()))) 
										|| (!(null == formObject.getValue(REASON_DESC)
										|| "".equalsIgnoreCase(formObject.getValue(REASON_DESC).toString()))))) {
									sendMessageValuesList("","Please select either of TIN or Reason for not providing TIN");
								} else {
								    //Added by Shivanshu CRS_TIN 22-11-2024
									if(TINFlag) {
										String colnames = "Sno,Country_Of_Tax_Residency,TaxPayer_Identification_Number,Reason_For_Not_Providing_TIN,Reason_Description";
										String values = Integer.toString(gridCount + 1) + "##"
												+ formObject.getValue(CRS_TAX_COUNTRY)
												+ "##"
												+ formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER)
												+ "##"
												+ formObject.getValue(CRS_REASONNOTPROVIDINGTIN)
												+ "##"
												+ formObject.getValue(REASON_DESC);
										LoadListViewWithHardCodeValues(CRS_TAXCOUNTRYDETAILS, colnames,values);
										/*formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
									formObject.setValue(REASON_DESC, "");*/
										clearControls(new String[]{CRS_TAXPAYERIDENTIFICATIONNUMBER,REASON_DESC,CRS_TAX_COUNTRY,CRS_REASONNOTPROVIDINGTIN});
									}
								}
							} else {
								sendMessageValuesList("","TIN information already exist for this country.");
							}
						} else if (!(null == formObject.getValue(CRS_REASONNOTPROVIDINGTIN) 
								|| formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString().equalsIgnoreCase(""))) {
							if (crsGridUniquenessCheck(formObject.getValue(CRS_TAX_COUNTRY).toString())) {
								if ((!(null == formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER)
										|| "".equalsIgnoreCase(formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER).toString())))
										&& ((!(null == formObject.getValue(CRS_REASONNOTPROVIDINGTIN) 
										|| "".equalsIgnoreCase(formObject.getValue(CRS_REASONNOTPROVIDINGTIN).toString()))) 
										|| (!(null == formObject.getValue(REASON_DESC)
										|| "".equalsIgnoreCase(formObject.getValue(REASON_DESC).toString())))))
								{
									sendMessageValuesList("","Please select either of TIN or Reason for not providing TIN");
								} else {
									 //Added by Shivanshu CRS_TIN 22-11-2024
									if(TINFlag) {
										String colnames = "Sno,Country_Of_Tax_Residency,TaxPayer_Identification_Number,Reason_For_Not_Providing_TIN,Reason_Description";
										String values = Integer.toString(gridCount + 1)
												+ "##"
												+ formObject.getValue(CRS_TAX_COUNTRY)
												+ "##"
												+ formObject.getValue(CRS_TAXPAYERIDENTIFICATIONNUMBER)
												+ "##"
												+ formObject.getValue(CRS_REASONNOTPROVIDINGTIN)
												+ "##"
												+ formObject.getValue(REASON_DESC);
										LoadListViewWithHardCodeValues(CRS_TAXCOUNTRYDETAILS, colnames,values);
										/*formObject.setValue(CRS_TAXPAYERIDENTIFICATIONNUMBER,"");
									formObject.setValue(REASON_DESC, "");*/
										clearControls(new String[]{CRS_TAXPAYERIDENTIFICATIONNUMBER,REASON_DESC,CRS_TAX_COUNTRY,CRS_REASONNOTPROVIDINGTIN});
									}
								}
							} else {
								sendMessageValuesList("","TIN information already exist for this country.");
							}
						} else {
							//Need to add validation for TIN is null then throw error message shivanshu 13-03-2025
							sendMessageValuesList(CRS_REASONNOTPROVIDINGTIN,"Please enter valid TIN.");
						}
					} else {
						sendMessageValuesList(CRS_TAX_COUNTRY,"Enter Country of Tax Residency");
					}
					logInfo("Gautam","<<<< 65 >>>>>>>>>");
					checkCRS();
				}else if (controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_MORTAGAGE_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_INSURANCE_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_TRB_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_OTHERS_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_VVIP)
						|| controlName.equalsIgnoreCase(IS_PREVILEGE_TP_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_ENTERTAINMENT_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)
						|| controlName.equalsIgnoreCase(IS_EXCELLENCY_TP_CAT_CHANGE) 
						|| controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)) {
					if (formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IS_CAT_BENEFIT_OTHER, "false");
						formObject.setValue(CAT_BENEFIT_OTHER, "");
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE,TRUE);
					}
				} else if (controlName.equalsIgnoreCase("Command55")) {    //Pending for check
					if (formObject.getValue(NOM_REQ) == null || formObject.getValue(NOM_REQ) == "") {
					} else {
						if (formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes")) {
							if (formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes")) {
								boolean flg = nomDetailsUpdate(Integer.parseInt(data));
								if (!flg) {
									return null;
								}
							}
						} else {
							nomDetailsInsert();
						}
					}
				} else if (controlName.equalsIgnoreCase("next_5")) {    //Pending for check
					if (formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes")) {
						if (formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes")) {
							boolean flg = nomDetailsUpdate(Integer.parseInt(data));
							if (!flg) {
								return null;
							}
						} else {
							nomDetailsInsert();
						}
					}
				}/* else if (controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)
						|| controlName.equalsIgnoreCase(FCR_NATIONALITY)
						|| controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)
						|| controlName.equalsIgnoreCase(EIDA_NATIONALITY)
						|| controlName.equalsIgnoreCase(MANUAL_NATIONALITY)
						|| controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)
						|| controlName.equalsIgnoreCase(FCR_CNTRY)
						|| controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL)
						|| controlName.equalsIgnoreCase(EIDA_CNTRY)
						|| controlName.equalsIgnoreCase(MANUAL_CNTRY)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)
						|| controlName.equalsIgnoreCase(FCR_PH)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)
						|| controlName.equalsIgnoreCase(EIDA_PH)
						|| controlName.equalsIgnoreCase(MANUAL_PH)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)
						|| controlName.equalsIgnoreCase(FCR_MOBILE)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)
						|| controlName.equalsIgnoreCase(EIDA_MOBILE)
						|| controlName.equalsIgnoreCase(MANUAL_MOBILE)
						|| controlName.equalsIgnoreCase(CHECKFCR)

						|| controlName.equalsIgnoreCase(CHECKEIDA)
						|| controlName.equalsIgnoreCase(CHECKMANUAL)
						|| controlName.equalsIgnoreCase(EIDA_RESIDENT)
						|| controlName.equalsIgnoreCase(MANUAL_RESIDENT)
						|| controlName.equalsIgnoreCase(FAT_US_PERSON)
						|| controlName.equalsIgnoreCase(FAT_LIABLE_TO_PAY_TAX)
						|| controlName.equalsIgnoreCase(CNTRY_OF_BIRTH)
						|| controlName.equalsIgnoreCase("POAcombo")
						|| controlName.equalsIgnoreCase(INDICIACOMBO)
						|| controlName.equalsIgnoreCase(FAT_SSN)
						|| controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)
						|| controlName.equalsIgnoreCase(DATEPICKERCUST)
						|| controlName.equalsIgnoreCase(DATEPICKERW8)
						|| controlName.equalsIgnoreCase(COMBODOC)
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_FCR)
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)
						|| controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)
						|| controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH)
						|| controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH)) {
							if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
								// NGRepeater objChkRepeater =
								// formObject.getNGRepeater("REPEAT_FRAME");
								int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
								String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
								logInfo("","sAccRelation------"+ sAccRelation);
								manageChangeinFATCAFields(controlName,sAccRelation);
								autoSetFatca(controlName);
							}
				}*/else if (controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)) {
					Calendar calendar = Calendar.getInstance();
					SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
					String scurrentDate = simpledateformat.format(calendar.getTime());
					formObject.setValue(DATEPICKERCUST,scurrentDate);
				}else if (controlName.equalsIgnoreCase(CT_BTN_EIDA_REFRESH)) {
					/*
					 * Object [] ain = {sWorkitemID+"#"}; Object getResult;
					 * log
					 * .info("inside fetch button for EIDA..."+sWorkitemID);
					 * JSObject objJStemp = formObject.getJSObject();
					 * getResult= objJStemp.call("openEidainForm",ain);
					 * log.info
					 * ("Just below opening EIDA form and getting output"
					 * +getResult); String sResult = getResult.toString();
					 * if(sResult.equalsIgnoreCase("")){ return; }
					 * xmlDataParser = new XMLParser();
					 * xmlDataParser.setInputXML(sResult);
					 */
					String valEIDA[] = xmlDataParser.getValueOf("Val").split("#");
					HashMap<String, String> sListView = null;// fetchValFromProp();
					try {
						for (String skey : valEIDA) {
							try {
								String sTemp[] = skey.split("=");
								String sFormField = sListView.get(sTemp[1]);
								if (sFormField.indexOf(EIDA_NATIONALITY) != -1) {
									List<List<String>> sOutput = formObject.getDataFromDB("SELECT COUNTRY FROM "
											+ "USR_0_COUNTRY_MAST "+ "WHERE COUNTRY_CODE = "
											+ "(SELECT COUNTRY_CODE FROM USR_0_EIDA_COUNTRY WHERE EIDA_CODE ='"
											+ sTemp[0] + "')");
									formObject.setValue(sFormField, sOutput.get(0).get(0));
								} else {
									if (sTemp[0].indexOf("+") != -1) {
										sTemp[0] = sTemp[0].substring(1,sTemp[0].length());
										formObject.setValue(sFormField,sTemp[0]);
									} else {
										formObject.setValue(sFormField,sTemp[0]);
									}
								}
							} catch (Exception e) {
								logError("Excepiton in EVENT_TYPE_CLICK", e);
							}
						}
					} catch (Exception e) {
						logError("Excepiton in EVENT_TYPE_CLICK", e);
					} finally {
						sListView.clear();
					}
				}else if(controlName.equalsIgnoreCase(EDIT)) {
					logInfo("executeServerEvent","EDIT BUTTON");
					//formObject.setStyle(EDIT, DISABLE, TRUE);
					String sUpdateDecision = "update " + sExtTable + " set back_route_flag='true'"
							+ " Where WI_NAME='"+ sWorkitemId + "'";
					int sout = formObject.saveDataInDB(sUpdateDecision);
					logInfo("executeServerEvent","sUpdateDecision: "+sUpdateDecision+" sout: "+sout);
					sBackRoute = TRUE;
					formObject.setValue(NO_OF_CUST_PROCESSED, "0");
					formObject.setValue(CUST_PROCESSED, "0");
					//logInfo("","Inside Edit");
				}else if (controlName.equalsIgnoreCase(INSTANT_DEL_YES)) { //here
					if (controlName.equalsIgnoreCase(INSTANT_DEL_YES)&& data.equalsIgnoreCase(TRUE)) {
						sendMessageValuesList("","Instant delievery not allowed at this Workstep.");
						formObject.setValue(INSTANT_DEL_YES, "false");
						formObject.setValue(INSTANT_DEL_NO, TRUE);
					} else {
						disableControls(new String[] {BRNCH_OF_INSTANT_ISSUE,EXISTING_NOM_PRSN });
						enableControls(new String[] { NOM_REQ,"COMBO65", "TEXT131", DEL_MODE_YES,
								DEL_MODE_NO, DEL_EXCELLENCY_CNTR,DEL_BRNCH_COURIER, DEL_BRNCH_NAME });
						formObject.setValue(NOM_REQ, "");
						Frame48_CPD_ENable();
					}
				}else if (controlName.equalsIgnoreCase(INSTANT_DEL_NO)) {
					if (controlName.equalsIgnoreCase(INSTANT_DEL_NO)&& data.equalsIgnoreCase(TRUE)) {
						disableControls(new String[] {BRNCH_OF_INSTANT_ISSUE,EXISTING_NOM_PRSN });
						enableControls(new String[] { NOM_REQ,"COMBO65", "TEXT131", DEL_MODE_YES,DEL_MODE_NO,
								DEL_EXCELLENCY_CNTR,DEL_BRNCH_COURIER, DEL_BRNCH_NAME });
						formObject.setValue(NOM_REQ, "");
						Frame48_CPD_ENable();
					} else {
						if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
							sendMessageValuesList("","Instant delievery not allowed at this Workstep.");
							formObject.setValue(INSTANT_DEL_YES,"false");
							formObject.setValue(INSTANT_DEL_NO, TRUE);
						} else {
							enableControls(new String[] { BRNCH_OF_INSTANT_ISSUE });
							disableControls(new String[] { "COMBO65","TEXT131", DEL_MODE_YES,DEL_MODE_NO,
									DEL_EXCELLENCY_CNTR,DEL_BRNCH_COURIER, DEL_BRNCH_NAME });
						}
					}
				}else if (controlName.equalsIgnoreCase("COMMAND26")) {
					saveStandingInstrInfo();
					saveStandInstrInfo();
				}else if (controlName.equalsIgnoreCase("COMMAND14")) {
					// NGRepeater objChkRepeater =
					// formObject.getNGRepeater("REPEAT_FRAME");
					int iRows = getGridCount(ACC_RELATION);
					int iSelectedRow = Integer.parseInt(data);
					String sCustomerID = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
					int sOutput = 0;
					logInfo("","iRows---" + iRows);
					logInfo("","iSelectedRow---" + iSelectedRow);
					if (iRows == 1) {
						sendMessageValuesList(ACC_RELATION,"Please add a row first in the grid.");
					}
					if (!sCustomerID.equalsIgnoreCase("")) {
						sOutput = formObject.saveDataInDB("delete from USR_0_PRODUCT_EXISTING where CUSTOMER_ID='"
								+ sCustomerID + "'");
						logInfo("","Row deleted---" + sOutput);
					} else {
						String sNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
						logInfo("","sNo---" + sNo);
						int iNo = Integer.parseInt(sNo);
						while (iNo != iRows - 1) {
							formObject.setTableCellValue(ACC_RELATION,iNo, 0, iNo + "");
							iNo++;
						}
						iRows = getGridCount(ACC_RELATION);
						formObject.setValue(NO_OF_CUST_SEARCHED,(iRows - 1) + "");
					}
				} else if (controlName.equalsIgnoreCase("COMMAND19")) {
					loadBlackListData();
				} else if (controlName.equalsIgnoreCase("accountInfoUDFGrid_dialog")) {
					formObject.clearCombo(ACC_INFO_UDF_FIELD);
					formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Graduation Date","Graduation Date");
					formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Special Customer Identifier","Special Customer Identifier");
				} else if (controlName.equalsIgnoreCase(CT_BTN_REFRESH)) {
//					String sCustID = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer, 2);
					String sCustID = formObject.getTableCellValue(ACC_RELATION, 
							Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()), 2);
					logInfo("CT_BTN_REFRESH","sCustID----" + sCustID);
					if (sCustID.equalsIgnoreCase("")) {
						sendMessageValuesList("","Customer ID not present for this customer");
					} else {
						setFCRValueonLoad(sCustID);
					}
				} else if (controlName.equalsIgnoreCase(CT_BTN_RESETALL)) {
					if (!sActivityName.equalsIgnoreCase(ACTIVITY_ACCOUNT_RELATION)) {
						setManualFieldsBlank();
					}
				} else if (controlName.equalsIgnoreCase(BTN_DEDUPE_SEARCH)) {
					logInfo("BTN_DEDUPE_SEARCH","INSIDE BTN_DEDUPE_SEARCH");
					if (formObject.getValue(MANUAL_NAME).toString().equalsIgnoreCase("")
							&& formObject.getValue(FCR_NAME).toString().equalsIgnoreCase("")
							&& formObject.getValue(EIDA_NAME).toString().equalsIgnoreCase("")) 
					{
						logInfo("BTN_DEDUPE_SEARCH","INSIDE IF BTN_DEDUPE_SEARCH");
						sendMessageValuesList(MANUAL_NAME, CA011);
					} else {  
						logInfo("BTN_DEDUPE_SEARCH","btndedupe else");
						int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) +1 ;
						formObject.clearTable(LVW_DEDUPE_RESULT);
						checkDuplicate(iProcessedCustomer);
						String sUpdateDedupe = "update USR_0_CUST_TXN set IS_DEDUPE_CLICK='true' Where WI_NAME='"
								+ sWorkitemId+ "' and cust_sno='"+ iProcessedCustomer + "'";
						logInfo("BTN_DEDUPE_SEARCH",sUpdateDedupe + "sUpdateDecision");
						formObject.saveDataInDB(sUpdateDedupe);
					}
				}/*else if ((controlName.equalsIgnoreCase("COMMAND48")  || controlName.equalsIgnoreCase("STATIC_SAVE"))) {

				}*/else if (controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("%%%")) {					
					if(submitWorkitem(data) && submitQDEValidation() && checkProductOffered(formObject)) {
						if(ACTIVITY_QDE_ACCOUNT_INFO.equalsIgnoreCase(sActivityName)) {
							retMsg = confirmOnSubmitInForLoop();
							if(retMsg.equalsIgnoreCase(TRUE)) {
								return getReturnMessage(true, controlName, CA008);
							} else if(!retMsg.isEmpty()) {
								return retMsg;
							}
						} else {
							return getReturnMessage(true, controlName);
						}
					}
					/*logInfo("submitWorkitem","Inside submitWorkitem");
						if(submitWorkitem()) {
							return getReturnMessage(true, controlName);
						}
						// if(formObject.isNGVisible(DOC_APPROVAL_OBTAINED)
						// && formObject.isNGVisible(COURT_ORD_TRADE_LIC)){
						logInfo("ExecuteServerEvent","Inside Button click");
						if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
							&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
								logInfo("SUBMIT CLICK","inside if--------Please select the appropriate checkbox to complete the validation");
								sendMessageValuesList(null,"Please select the appropriate checkbox to complete the validation");
						}
						// }
						boolean prodChangeFlag = checkProdChngForNoEligibility();
						if (!prodChangeFlag) {
							logInfo("SUBMIT CLICK","INSIDE prodChangeFlag");
							sendMessageValuesList(null,"Customer is not eligible for cheque book. Please change the product.");

						}
						enableControls(new String[] { BTN_SUBMIT });
						boolean result = false;
						if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")) {
							// NGRepeater objChkRep =
							// formObject.getNGRepeater("REPEAT_FRAME");
							String acc_rel = formObject.getTableCellValue(ACC_RELATION, 1, 9);
							logInfo("SUBMIT BUTTON","ACC_RELATION  = " + acc_rel);
							logInfo("SUBMIT BUTTON","inside ikyc signatory-------");
							logInfo("SUBMIT BUTTON","start----");
							String sQuery = "select cust_seg from usr_0_cust_txn WHERE WI_NAME ='"+ sWorkitemId + "'"
									+ "and cust_sno='1'";
							logInfo("SUBMIT BUTTON","o/p from queryyyyyy" + sQuery);
							List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
							logInfo("SUBMIT BUTTON","AP select--" + sOutput);
							String pri_cust_cat = "";
							try{
								pri_cust_cat = sOutput.get(0).get(0);
							}catch(Exception e){
								e.printStackTrace();
							}							
							logInfo("SUBMIT BUTTON","Customer segment------------::::"+ pri_cust_cat);
							if (!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA"))
								&& pri_cust_cat.equalsIgnoreCase("Signatory")) {
									logInfo("SUBMIT BUTTON","!(acc_rel.equalsIgnoreCase(AUS) || acc_rel.equalsIgnoreCase(POA))===="
											+ !(acc_rel.equalsIgnoreCase("AUS") || acc_rel.equalsIgnoreCase("POA")));
									logInfo("SUBMIT BUTTON"," pri_cust_cat.equalsIgnoreCase(Signatory)"+ pri_cust_cat.equalsIgnoreCase("Signatory"));
									logInfo("SUBMIT BUTTON","inside if--------");
									sendMessageValuesList(null,"Primary Signatory Customer can't open a New Account!!");
							}
						}
					result = false;
					if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {
						logInfo("SUBMIT BUTTON","Inside QDE_CUST_INFO ");
						int iSelectedRow = 0;
						String ismandatory = null;
						try{
							iSelectedRow = Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX)) + 0;
						}catch(Exception e){
							logInfo("ERROR","ERROR in iSelectedRow Integer Parsing");
						}
						logInfo("SUBMIT BUTTON","CHECK 1");
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
						if (formObject.getValue(RESIDENCY_STATUS).toString().equalsIgnoreCase("")) {
							calculateResidencyStatus(RESIDENCY_STATUS);
						}
						logInfo("SUBMIT BUTTON","CHECK 2");
						result = checkNatSegment();
						logInfo("SUBMIT BUTTON", "NATIONALITY VALIDATION---"+ result);
						if (!result) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						result =mandatoryComparisonData();
						logInfo("SUBMIT BUTTON","Customer Info Validation result---" + result);
						if (!result) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						result = mandatoryiKYC();
						if (!result) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						result = mandatoryAtFatca();
						logInfo("SUBMIT BUTTON","Submit validation fatca result now---"+ result);
						if (!result) {
							enableControls(new String[] { BTN_SUBMIT });
						}

						String custSegment = formObject.getValue(CUST_SEGMENT1).toString();
						List<List<String>> sOutputt = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment where cust_segment='"+ custSegment + "'");
						if(sOutputt.size()>0 && sOutputt!=null){
							try {
								ismandatory = sOutputt.get(0).get(0);
							} catch (Exception e1) {
								logError("Exception in  QDE_CUST_INFO", e1);
							}
						}
						logInfo("SUBMIT BUTTON","CUST Segment result: " + ismandatory);
						boolean custSegmentCheck = false;
						try{
							if (ismandatory.equalsIgnoreCase("Yes"))
								custSegmentCheck = true;
							else
								custSegmentCheck = false;
						}catch(Exception e){
							e.printStackTrace();
						}

						logInfo("SUBMIT BUTTON","CUST Segment result:Debug " );					
						result = mandatoryCRSCheck(custSegmentCheck);
						logInfo("SUBMIT BUTTON","Submit CRS Check result--" + result);
						if (!result) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						if (!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,
								CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE, EIDA_PSSTYPE,MANUAL_PASSTYPE, CA018, HD_PASS_TYPE)) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						if (!ValidateComparisonData(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
								CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS, EIDA_VISASTATUS,
								MANUAL_VISASTATUS, CA019, "Mandatory","Visa Status")) {
							return null;
						}
						if (!validateVisaStatus()) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						if (!validateCustomerClassification()) {
							enableControls(new String[] { BTN_SUBMIT });
						}
						try {
							String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR, CHECKBOX_DOB_EIDA,
									CHECKBOX_DOB_MANUAL, FCR_DOB, EIDA_DOB,MANUAL_DOB);
							int age = calculateAge(sFinalDOB);
							int age1 = CalculateAge1(sFinalDOB);
							String accountOwn = formObject.getValue(ACC_OWN_TYPE).toString();
							boolean bresult = false;
							String sQueryy = "select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
							List<List<String>> sOutput = formObject.getDataFromDB(sQueryy);
							logInfo("SUBMIT BUTTON","sOutputt------" + sOutputt);
							int sMinorAge = Integer.parseInt(sOutputt.get(0).get(0));
							logInfo("inside QDE_CUST_INFO","sMinorAge....." + sMinorAge);
							if (accountOwn.equalsIgnoreCase("Minor")) {
								if (age1 >= sMinorAge&& sAccRelation.equalsIgnoreCase("Minor")) {
									sendMessageValuesList(null,"For Minor Date Of Birth Should Not Be Greater Than or equal to "
											+ sMinorAge + " Years.");
									bresult = true;
								}
								if (age < sMinorAge&& sAccRelation.equalsIgnoreCase("Guardian")) {
									sendMessageValuesList(null,"For Guardian Date Of Birth Should Be Greater Than or equal to "
											+ sMinorAge + " Years.");
									bresult = true;
								}
							} else if (accountOwn.equalsIgnoreCase("Joint")
									&& age < sMinorAge) {
								sendMessageValuesList(null,"For Joint Date Of Birth Should be greater than or equal to "
										+ sMinorAge + " Years.");
								bresult = true;
							} else {
								if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
									if (age < 18) {
										sendMessageValuesList(null,"For Single Date Of Birth Should be greater than or equal to 18 Years.");
										bresult = true;
									}
								}
							}
							if (bresult) {
								if (formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")) {
									sendMessageValuesList(FCR_DOB,"Date Of Birth Should be greater than or equal to 18 Years.");
								} else if (formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")) {
									sendMessageValuesList(EIDA_DOB,"Date Of Birth Should be greater than or equal to 18 Years.");
								} else if (formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")) {
									sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should be greater than or equal to 18 Years.");
								}
								enableControls(new String[] { "SUBMIT_1" });
							}
						} catch (Exception e) {
							logError("Exception QDE_CUST_INFO", e);
						}
						try {
							String sQuery1 = "SELECT IS_DEDUPE_CLICK FROM USR_0_CUST_TXN WHERE WI_NAME ='"
									+ sWorkitemId+ "' and cust_sno='"+ iSelectedRow + "'";
							List<List<String>> sOutput = formObject.getDataFromDB(sQuery1);
							String dedupeDone = sOutput.get(0).get(0);
							if (!dedupeDone.equalsIgnoreCase("true")) {
								sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
								enableControls(new String[] { "SUBMIT_1" });
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						try {
							if (sAccRelation.equalsIgnoreCase("Minor")) {
								result = minorDateCompDOB();
								if (!result) {
									enableControls(new String[] { "SUBMIT_1" });
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						saveComparisonData();
						saveCustNeedAnalysisData();
						saveHiddenDataQDE();
						saveClientQuestionsData(); 
						saveCRSDetails(); 
						int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sCustNo = "";
						sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
						String sBankRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 7);
						String sCID = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
						String sCASA = "";
						if (sBankRelation.equalsIgnoreCase("Existing")) {
							List<List<String>> sOutput = formObject.getDataFromDB("SELECT COUNT(1) AS COUNT_WI FROM "
									+ "USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"
									+ sWorkitemId+ "' AND CUSTOMER_ID ='"
									+ sCID+ "' AND ACCOUNT_TYPE ='CASA'");
							sCASA = sOutput.get(0).get(0);
							logInfo("inside QDE_CUST_INFO", "CASA Value:"+ sCASA);
						}
						if (sBankRelation.equalsIgnoreCase("New")|| sCASA.equalsIgnoreCase("0")) {
							String output = getApplicationRiskInputXML(iProcessedCustomer);
							logInfo("inside QDE_CUST_INFO", "XML:" + output);
							String outputresult = socket.connectToSocket(output);//xecuteWebserviceAll(output);
							logInfo("inside QDE_CUST_INFO","outputresult--in webservice--"+ outputresult);
							if (outputresult.equalsIgnoreCase("NO")) {
								sendMessageValuesList(null,"Selected passport holder and Non UAE Residents, not allowed to open Account");
							} else if (outputresult.equalsIgnoreCase("Partial")) {
								int respose = JOptionPane.showConfirmDialog(null,"Selected passport holder Residents "
										+ "does not meet conditions,\nHence not allowed to open Account. Do you still "
										+ "want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
								if (respose == JOptionPane.YES_OPTION) {
									formObject.setValue(NIG_MAKER, "YES");
									String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONMAKER='YES' Where WI_NAME='"
											+ formObject.getValue(WI_NAME)
											+ "' AND CUST_SNO ='"
											+ sCustNo
											+ "'";
									formObject.saveDataInDB(updatequery);
									logInfo("inside QDE_CUST_INFO",
											"Updated Successfully");
								} else {
									enableControls(new String[] { "SUBMIT_1" });
								}
							}
						}
						String sInputXML = getIndRiskInputXML(iProcessedCustomer);
						String sOutput = "";
						if (sInputXML.contains("<APWebService_Input>")) {
							sOutput = socket.connectToSocket(sInputXML);
						} else { 
							sOutput = sInputXML;
						}
						logInfo("inside QDE_CUST_INFO","sOutput--in webservice--" + sOutput);
						if (sOutput.equalsIgnoreCase("")) {
							sendMessageValuesList(null,"Some error occured in calculating Individual risk");
							enableControls(new String[] { "SUBMIT_1" });
						} else {
							if ((!sOutput.equalsIgnoreCase("Unacceptable Risk")
									&& !sOutput.equalsIgnoreCase("PEP")&& !sOutput.equalsIgnoreCase("UAE-PEP") 
									&& !sOutput.equalsIgnoreCase("Non UAE-PEP"))
									&& (formObject.getValue(CUST_SEGMENT1).toString().equalsIgnoreCase("Private Clients")))
							{
								sOutput = "Increased Risk";
							}
							String sWsName = formObject.getValue(CURR_WS_NAME).toString();
							String sriskColumn = "SNO,WI_NAME,WS_NAME,CUST_CUR_RISK_BANK";
							String sriskValue = "'" + iSelectedRow + "','"+ sWorkitemId + "','" + sWsName + "','"+ sOutput + "'";
							logInfo("inside QDE_CUST_INFO", "sriskColumn.."+ sriskColumn);
							logInfo("inside QDE_CUST_INFO", "sriskValue.."+ sriskValue);
							insert_Into_Usr_0_Risk_Data(sriskColumn,sriskValue);
							String sUpdateDecision = "update USR_0_CUST_TXN set CUST_INDI_RISK='"
									+ sOutput
									+ "' Where WI_NAME='"
									+ sWorkitemId
									+ "' AND CUST_SNO ='"
									+ iProcessedCustomer + "'";
							formObject.saveDataInDB(sUpdateDecision);
						}

						// formObject.RaiseEvent("WFDone");
					}*/
					// QDE_ Account_Info

				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName) && data.equalsIgnoreCase("confirmAppRisk")) {
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
					formObject.setValue(NIG_MAKER,"YES");
					String updateQuery = "update USR_0_CUST_TXN set NIGEXCEPTIONMAKER='YES' Where "
							+ "WI_NAME='"+formObject.getValue(WI_NAME).toString()+"' AND CUST_SNO ='"+sCustNo+"'";
					formObject.saveDataInDB(updateQuery);
					logInfo("submitWorkitem","Updated Successfully query: "+updateQuery);
					// DCRA for catagoery change only and Upgrade
					if (!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")||
						  formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")||
						  formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))) {
					if(calculateIndRiskQDE() && submitQDEValidation() && checkProductOffered(formObject)) {
						return getReturnMessage(true, controlName);
					}
				  }if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")||
						 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
						if(submitQDEValidation() && checkProductOffered(formObject)) {
							return getReturnMessage(true, controlName);
						}
				  }
				} else if(controlName.equalsIgnoreCase("postSubmit")){
					if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")){
						reKeyInsert();
						updateReKeyTemp("CRO");
					}
					createHistory();
					createAllHistory();
					updateProfitCentre();//garima added this function for  changes of CR 2018
					String dec = formObject.getValue(CRO_DEC).toString();
					Date d= new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
					String sDate = dateFormat.format(d);     

					//Added by Sahiba for sending the customer name in the email to Compliance.
					String sCustNo =getPrimaryCustomerSNO();
					String sCustName = "";
					String pri_bank_rel = "";
					String sQuery11="SELECT CUST_FULL_NAME FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'";
					List<List<String>> sOutput1=formObject.getDataFromDB(sQuery11);
					if(sOutput1.size()>0 && sOutput1!=null){
						sCustName=sOutput1.get(0).get(0);
					}
					String sQ2="select decode (bank_relation,'New','NTB', bank_relation) as pri_bank_relation from acc_relation_repeater where sno=1 and wi_name='"+sWorkitemId+"' ";
					List<List<String>> sOutput2 = formObject.getDataFromDB(sQ2); 
					if(sOutput2.size()>0 && sOutput2!=null){
						pri_bank_rel=sOutput1.get(0).get(0);
					}
					String sValue = "'"+ dec +"'"+(char)25+"'"+ sActivityName +"'"+(char)25+"'"+sUserName+"'"+(char)25+"'"+sDate+"'"+(char)25+"'"+sCustName
							+"'"+(char)25+"'"+pri_bank_rel+"'";
					int sOutput3 = updateDataInDB(sExtTable,"ACCOUNT_INFO_MAKER_DEC,WI_COMPLETED_FROM,CRO_NAME,"
							+ "CRO_SUBMIT_DATE,PRI_CUST_NAME,pri_bank_relation ",sValue,"WI_NAME = '"+sWorkitemId+"'");
					System.out.println("$$$$$$$ ExecuteQuery_APUpdate  $$$$ "+sOutput3);
					System.out.println("AT accrelation CALLING FOR EMAIL DATE  :");
					String param = sWorkitemId+"','"+sProcessName;
					List<String> paramlist = new ArrayList<>( );
					paramlist.add(PARAM_TEXT+sWorkitemId);
//					paramlist.add(PARAM_TEXT+sProcessName);			//Commented for invalid column on 27042023(Performanceissue) by Ameena
					formObject.getDataFromStoredProcedure("SP_TemplateGenerationEmailDt", paramlist);
					logInfo("submitWorkitem","SP_TemplateGenerationEmailDt Procedure called");
					//Changed By Ashish Gupta on 20th Nov 2015 to store risk in datatbase in case of new segment is Private Client
					String sCustSeg =formObject.getValue(NEW_CUST_SEGMENT).toString();
					if(sCustSeg == null) {
						sCustSeg="";
					} 
				//	if(sCustSeg.equalsIgnoreCase("Private Clients")) {
				//		CalculateRiskCategoryChange();
				//	}
					if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")) {
					setTemp_usr_0_product_selected();
					}
					return getReturnMessage(true, "postSubmit");
				} else if (controlName.equalsIgnoreCase(DOC_APPROVAL_OBTAINED)) {
					logInfo("","INSIDE AO_DOC_APPROVAL_OBTAINED Click");
					if ("true".equalsIgnoreCase(formObject.getValue(DOC_APPROVAL_OBTAINED).toString())) {
						logInfo("","INSIDE AO_DOC_APPROVAL_OBTAINED true");
						formObject.setValue(COURT_ORD_TRADE_LIC, "false");
					} else {
						logInfo("","INSIDE AO_DOC_APPROVAL_OBTAINED false");
						formObject.setValue(COURT_ORD_TRADE_LIC, "true");
					}
				}else if (controlName.equalsIgnoreCase(COURT_ORD_TRADE_LIC)) {
					logInfo("","INSIDE AO_COURT_ORD_TRADE_LIC Click");
					if ("true".equalsIgnoreCase(formObject.getValue(COURT_ORD_TRADE_LIC).toString())) {
						logInfo("","INSIDE AO_COURT_ORD_TRADE_LIC true");
						formObject.setValue(DOC_APPROVAL_OBTAINED, "false");
					} else {
						logInfo("","INSIDE AO_COURT_ORD_TRADE_LIC false");
						formObject.setValue(DOC_APPROVAL_OBTAINED, "true");
					}
				}else if (controlName.equalsIgnoreCase(DC_BTN_REMOVE)) {// js
					// formObject.setNGSelectedTab("Tab5",1);
				}else if (controlName.equalsIgnoreCase(VIEW)) {
					logInfo("","inside view 5");
					formObject.clearTable(LVW_DEDUPE_RESULT);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
					String sQuery = "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',to_date(CUST_DOB,'"
							+ getDateValue("DATEFORMAT")
							+ "'),CUST_EIDA,CUST_NATIONALITY,system_type FROM USR_0_DUPLICATE_SEARCH_DATA WHERE WI_NAME='"
							+ sWorkitemId
							+ "' AND CUST_SNO ='"
							+ iSelectedRow
							+ "'";
					List<List<String>> list = formObject.getDataFromDB(sQuery);
					loadListView(list,"CID,Name,CustomerIC,PassportNo,Email,Mobile,DebitCardNo,CreditCardNo,"
							+ "DOB,EIDANO,Nationality,System",LVW_DEDUPE_RESULT);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"
							+ sWorkitemId+ "' and cust_sno='"+ iSelectedRow+ "'";
					logInfo("","sQuery ==== " + sQuery1);
					List<List<String>> list1 = formObject.getDataFromDB(sQuery1);
					logInfo("",list1.toString());
					int index1 = Integer.parseInt(list1.get(0).get(0));
					logInfo("","index1" + index1);
					int[] intA = new int[1];
					intA[0] = index1;
					// formObject.setNGLVWSelectedRows(LVW_DEDUPE_RESULT, intA
					// );
				}else if ((controlName.equalsIgnoreCase("COMMAND24") || controlName.equalsIgnoreCase("STATIC_SUBMIT"))
						&& sActivityName.equalsIgnoreCase("QDE_Account_Info")) {

					logInfo("","both are visble");
					if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
							&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
						logInfo("","inside if--------Please select the appropriate checkbox to complete the validation");
						sendMessageValuesList("","Please select the appropriate checkbox to complete the validation");

					}logInfo("","both are visble");
					if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")) {
					boolean prodChangeFlag = checkProdChngForNoEligibility();
					if (!prodChangeFlag) {
						logInfo("","inside if--------");
						sendMessageValuesList("","Customer is not eligible for cheque book. Please change the product.");
						getReturnMessage(false);
					}
				}	
					if (formObject.getValue(NOM_REQ) == null || formObject.getValue(NOM_REQ) == "") {

					} else {
						if (formObject.getValue(NOM_REQ).toString().equalsIgnoreCase("Yes")) {
							if (formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes")) {
								boolean rtn = nomDetailsUpdate(Integer.parseInt(data));
								if (!rtn) {
									getReturnMessage(false, "");
								}
							} else {
								nomDetailsInsert();
							}
						}
					}
					logInfo("","In command24");
					// formObject.setNGEnable("COMMAND24",false);
					// formObject.setNGEnable("static_submit",false);
					// formObject.RaiseEvent("WFSave");
					// formObject.RaiseEvent("WFDone");
				}else if (controlName.equalsIgnoreCase("COMMAND27")) {
					// formObject.setNGSelectedTab("Tab5",0);
				}else if (controlName.equalsIgnoreCase("COMMAND27")) {
					// formObject.setNGSelectedTab("Tab5",0);
					formObject.clearCombo(ACC_INFO_UDF_FIELD);
					formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Graduation Date","Graduation Date");
					formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Special Customer Identifier","Special Customer Identifier");
				}else if (controlName.equalsIgnoreCase("COMMAND28")) {  
					// formObject.clearCombo(ACC_INFO_UDF_FIELD);
					// formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Graduation Date","Graduation Date");
					// formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Special Customer Identifier","Special Customer Identifier");
					// formObject.setNGEnable("COMMAND28",false);
					// formObject.RaiseEvent("WFSave");
					// formObject.RaiseEvent("WFDone");
				}else if (controlName.equalsIgnoreCase("SEARCH")) {
					try {
						String sResult = data.toString();
						logInfo("","sResult = " + sResult);
						String rtnArry[] = null;
						String rtnData[] = null;
						String sAllProduct = "";
						int iRows = getGridCount(PRODUCT_QUEUE);
						boolean repeaterFlag = false;
						boolean repeaterFlag1 = false;
						boolean onetimeAdd = false;
						boolean onetimeAdd1 = false;
						logInfo("","iRows = " + iRows);
						String sQuery = "SELECT final_email FROM USR_0_CUST_TXN WHERE cust_sno='"
								+ getPrimaryCustomerSNO()+ "' AND WI_NAME = '"+ sWorkitemId + "'";
						logInfo("","sQuery---" + sQuery);
						List<List<String>> sEmailOutput = formObject.getDataFromDB(sQuery);
						String email = sEmailOutput.get(0).get(0);
						if (!sResult.equalsIgnoreCase("")) {
							if (sResult.contains("#")) {
								rtnArry = sResult.split("#");
								if (email.equalsIgnoreCase("")) {
									boolean rtn = emailEtihadVal(rtnArry);
									if (!rtn) {
										getReturnMessage(false, "");
									}
								}

								for (int i = 0; i < rtnArry.length; i++) {
									rtnData = rtnArry[i].split("~");
									// objChkRepeater.addRow();
									JSONArray jsonArray = new JSONArray();
									JSONObject obj = new JSONObject();
									obj.put("acc_status", "");
									obj.put("prod_code", "");
									obj.put("prod_desc", "");
									obj.put("currency", "");
									obj.put("acc_no", "");
									obj.put("acc_brnch", "");
									obj.put("cheque_book", "");
									obj.put("cheque_book_no", "");
									obj.put("mode_of_funding", "");
									obj.put("trnsfr_from_acc_no", "");
									obj.put("from_acc_bal", "MANUAL");
									obj.put("chk_box", "");
									obj.put("amt_trnsfered", "");
									obj.put("cid", "");
									obj.put("iban_no", "");
									obj.put("wi_name", "");
									obj.put("acc_title", "");
									obj.put("date_acc_opening", "");
									jsonArray.add(obj);
									logInfo("onClickAcctRel", "jsonArray2:: "+ jsonArray.toString());
									formObject.addDataToGrid(PRODUCT_QUEUE,jsonArray);
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 1, rtnData[0]);
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 2, rtnData[1]);
									logInfo(""," value of i =" + i + " "+ "... rtnData[2]..." + rtnData[2]);
									setProductCurrencyComboLoadDisable1(rtnData[0]);// testing03102017
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 3, rtnData[2]);
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 6, rtnData[3]);
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 5, rtnData[4]);
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 16, sWorkitemId);
									formObject.setTableCellValue(PRODUCT_QUEUE,iRows + i, 15, (iRows + i + ""));
									rtnData = null;
									String getquery = "SELECT visa_status FROM USR_0_CUST_TXN WHERE cust_sno='"
											+ getPrimaryCustomerSNO()+ "' AND WI_NAME = '"
											+ sWorkitemId+ "'";
									logInfo("","getquery---" + getquery);
									List<List<String>> getqueryOutput = formObject.getDataFromDB(getquery);

									String Visa = getqueryOutput.get(0).get(0);
									logInfo("","getquery---" + Visa);
									if (Visa.equalsIgnoreCase("")) {
										if (Visa.equalsIgnoreCase("Under Processing")) {
											formObject.setTableCellValue(PRODUCT_QUEUE, iRows + i,6, "No");
											// objChkRepeater.setEnabled(iRows+i,
											// 6, false);
											// objChkRepeater.setEditable(iRows+i,
											// 6, false);

										}
									}
								}
							} else {
								rtnData = sResult.split("~");
								sAllProduct = sAllProduct + "'" + rtnData[0]+ "',";
								if (email.equalsIgnoreCase("")) {
									sAllProduct = sAllProduct.substring(0,sAllProduct.length() - 1);
									String sQuery1 = "SELECT COUNT(SUB_PRODUCT_TYPE) as SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE IN ("
											+ sAllProduct+ ") AND UPPER(SUB_PRODUCT_TYPE) ='ETIHAD'";
									logInfo("","sQuery---" + sQuery1);
									List<List<String>> list = formObject.getDataFromDB(sQuery1);
									if (!list.get(0).get(0).equalsIgnoreCase("0")) {
										sendMessageValuesList("","Primary Customer's Email Id Is Mendatory For Etihad Product.");
										getReturnMessage(false);
									}
								}
								logInfo("","MAni iRows: " + iRows);
								logInfo("","objChkRepeater.getRowCount()"+ getGridCount(PRODUCT_QUEUE));
								// objChkRepeater.addRow();
								JSONArray jsonArray = new JSONArray();
								JSONObject obj = new JSONObject();
								obj.put("acc_status", "");
								obj.put("prod_code", "");
								obj.put("prod_desc", "");
								obj.put("currency", "");
								obj.put("acc_no", "");
								obj.put("acc_brnch", "");
								obj.put("cheque_book", "");
								obj.put("cheque_book_no", "");
								obj.put("mode_of_funding", "");
								obj.put("trnsfr_from_acc_no", "");
								obj.put("from_acc_bal", "MANUAL");
								obj.put("chk_box", "");
								obj.put("amt_trnsfered", "");
								obj.put("cid", "");
								obj.put("iban_no", "");
								obj.put("wi_name", "");
								obj.put("acc_title", "");
								obj.put("date_acc_opening", "");
								jsonArray.add(obj);
								logInfo("onClickAcctRel", "jsonArray2:: "+ jsonArray.toString());
								formObject.addDataToGrid(PRODUCT_QUEUE,jsonArray);
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 1, rtnData[0]);
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 2, rtnData[1]);
								logInfo(""," value of i = .. rtnData[2]..."+ rtnData[2]);
								setProductCurrencyComboLoadDisable1(rtnData[0]);// testing03102017
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 3, rtnData[2]);
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 6, rtnData[3]);
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 5, rtnData[4]);
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 15, sWorkitemId);
								formObject.setTableCellValue(PRODUCT_QUEUE,iRows, 16, (iRows + ""));
							}
						}
						EnableFamilyReffered();
					} catch (Exception e) {
						logInfo("execute server", "click event");
					}
				}else if(controlName.equalsIgnoreCase("COMMAND43")){					
					//NGRepeater objChkRepeater = formObject.getNGRepeater("acc_repeater");
					int iSelectedRow=Integer.parseInt(data);		//from js  selected row
					String sAccNo = formObject.getTableCellValue(PRODUCT_QUEUE,iSelectedRow, 4);
					if(!sAccNo.equalsIgnoreCase("")){
						sendMessageValuesList(PRODUCT_QUEUE,"You can not delete this product as account has been created for this.");
					}
					//objChkRepeater.removeRow(iSelectedRow);
					//objChkRepeater.refresh();
					int iRows = getGridCount(PRODUCT_QUEUE);
					for(int i=1;i<iRows;i++){
						logInfo("","In Loop");
						formObject.setTableCellValue(PRODUCT_QUEUE,i,14,i+"");
					}
					EnableFamilyReffered();
				}else if (controlName.equalsIgnoreCase(BTN_VALIDATE)) {
					ValidateFATCADetails("Mini");
					formObject.setStyle(COMBODOC, ENABLE, TRUE);
				}else if (controlName.equalsIgnoreCase(BTN_VALIDATEFATCA)) {
					ValidateFATCADetails("Main");
				}else if ((controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH))
						|| (controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH))
						|| (controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH))) {
					logInfo("COUNTRYBIRTH", "Inside change event of COUNTRYBIRTH ");
					String sFinalCountryOfBirth = getFinalDataComparison(
							CHECKBOX_COB_FCR, CHECKBOX_COB_EIDA, CHECKBOX_COB_MANUAL,
							FCR_COUNTRYBIRTH, EIDA_COUNTRYBIRTH,
							MANUAL_COUNTRYBIRTH); 
					logInfo("COUNTRYBIRTH", sFinalCountryOfBirth+ "sFinalCountryOfBirth inside country click");
					if (sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
						logInfo("COUNTRYBIRTH", sFinalCountryOfBirth + "united insoide inside country click firse");
						formObject.setStyle(COMBODOC, ENABLE, TRUE);
						formObject.clearCombo(COMBODOC);
						formObject.addItemInCombo(COMBODOC,"");
						formObject.addItemInCombo(COMBODOC,"W8BEN");
						formObject.addItemInCombo(COMBODOC,"W9");
					} else {		
						logInfo("COUNTRYBIRTH",sFinalCountryOfBirth+"else inside country click firse");						  
						formObject.setStyle(COMBODOC,ENABLE,"true");  
						formObject.clearCombo(COMBODOC);
						formObject.setValue(FAT_CUST_CLASSIFICATION, "");
						formObject.addItemInCombo(COMBODOC,"");
						formObject.addItemInCombo(COMBODOC,"NA");
						formObject.addItemInCombo(COMBODOC,"W8BEN");
						formObject.addItemInCombo(COMBODOC,"W9");
					}
				} else if (controlName.equalsIgnoreCase(BTN_DEDUPE_SEARCH)){  //Duplicate recheck
					fillDuplicateData(data);
				} else if(controlName.equalsIgnoreCase("DELETEGRID")){
					checkCRS();
				} else if(controlName.equalsIgnoreCase(BTN_APP_LEVEL_RISK)){  //Addeed by reyaz 14-09-2023
					logInfo("QDECustAccountInfo","Inside BTN_APP_LEVEL_RISK click ");
					calculateIndRiskQDE();
					updateRiskAssessmentData();
					fieldValueUsr_0_Risk_Data();// to be made
					calculateAppRisk();
				}

				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)){
					fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
				}
				if ((formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA"))
						&& (!(formObject.getValue(MANUAL_VISASTATUS).toString().equalsIgnoreCase("Under Processing"))))
				{
					setVisaNoManual();
					//						logInfo("","before MDSA is null pop up" + sOnLoad);
					//						String updateQuery = "UPDATE USR_0_CUST_TXN set VISA_NO='' WHERE cust_sno='"
					//						+ getPrimaryCustomerSNO()+ "' AND WI_NAME = '"+ sWorkitemId + "'";
					//						formObject.saveDataInDB(updateQuery);
					//						logInfo("MANUAL_VISANO","Update successful" + updateQuery);
					//						formObject.setValue(CHECKBOX_VISA_NO_MANUAL, TRUE);
					//						formObject.setValue(CHECKBOX_VISA_NO_FCR, "false");
					//						formObject.setValue(CHECKBOX_VISA_NO_EIDA, "false");
					//						formObject.setStyle(MANUAL_VISANO, DISABLE, FALSE);
					//						if (formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)
					//						|| formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE)) 
					//							{
					//								formObject.setValue(MANUAL_VISANO, "");
					//							}
					//						sendMessageValuesList("", CA0173);
				}
				setResidentWithoutEidaComboQDE();
				// Kishan End
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				logInfo("EVENT_TYPE_CHANGE", "1 control name: "+controlName);
				setResidentWithoutEidaComboQDE();
				logInfo("EVENT_TYPE_CHANGE", "2 control name: "+controlName);
				if(controlName.equalsIgnoreCase("table130_trnsfr_from_acc_no")) {
//					int iRows = getGridCount(PRODUCT_QUEUE);				
//					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()); 
					String sMode = formObject.getValue("table130_mode_of_funding").toString();
					if(!sMode.equalsIgnoreCase("")) {
						String sAccNo = formObject.getValue("table130_trnsfr_from_acc_no").toString();
						String sQuery= "SELECT DISTINCT ACC_BALANCE,CURRENCY FROM USR_0_PRODUCT_EXISTING WHERE "
								+ "WI_NAME ='"+sWorkitemId+"' AND ACC_NO = '"+sAccNo+"' ";
						List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
						if(sOutput.size()>0) {
							String sAccBalance = sOutput.get(0).get(0);
							String sCurrency = sOutput.get(0).get(1);
							logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sOutput: "+sOutput);
							logInfo("PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","sAccBalance: "+sAccBalance+", sCurrency: "
									+sCurrency);
							formObject.setValue("table130_from_acc_bal",sAccBalance);
							formObject.setValue("table130_trnsfr_from_currency",sCurrency);
						}
					} else {
						sendMessageValuesList(PRODUCT_QUEUE,"Please Select Mode of Transfer first");
					}
//					if(iRows>0) {}
				} else if("table130_mode_of_funding".equalsIgnoreCase(controlName)) {
					manageFundTransfer();
				}else if(controlName.equalsIgnoreCase("LISTVIEW_PUR_ACC_RELATION")){
					logInfo("LISTVIEW_PUR_ACC_RELATION", "inside QVAR_ACC_POURPOSE: ");
					accountPurpose();
		      	}else if(controlName.equalsIgnoreCase(ADDITIONAL_SOURCES_INCOME_AED)){
					logInfo("ADDITIONAL_SOURCES_INCOME_AED", "inside ADDITIONAL_SOURCES_INCOME_AED: ");
					additionalSource();
		      	}
				else if (controlName.equalsIgnoreCase(NEW_CUST_SEGMENT)) {

					logInfo("ON_CHANGE","NEW_CUST_SEGMENT");
					if(formObject.getValue(NEW_CUST_SEGMENT).toString().isEmpty()) {
						logInfo("ON_CHANGE","NEW_CUST_SEGMENT KDD1 " +formObject.getValue(NEW_CUST_SEGMENT).toString());
						uncheckCheckBoxes(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER});
						disableControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER});
						formObject.setValue(CAT_BENEFIT_OTHER, "");
						showControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER});
						//						formObject.setNGVisible("Label299",true);
						//						formObject.setNGVisible("Label298",true);
						//						formObject.setNGVisible("Label297",true);							
					} else {
						logInfo("ON_CHANGE","NEW_CUST_SEGMENT KDD1 " +formObject.getValue(NEW_CUST_SEGMENT).toString());
						uncheckCheckBoxes(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER});
						enableControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE, IS_MORTAGAGE_CAT_CHANGE, 
								IS_INSURANCE_CAT_CHANGE, IS_TRB_CAT_CHANGE, IS_OTHERS_CAT_CHANGE, IS_VVIP, 
								IS_PREVILEGE_TP_CAT_CHANGE, IS_ENTERTAINMENT_CAT_CHANGE, IS_SHOPPING_CAT_CHANGE,
								IS_SPORT_CAT_CHANGE, IS_TRAVEL_CAT_CHANGE, IS_EXCELLENCY_TP_CAT_CHANGE,
								IS_CAT_BENEFIT_OTHER, CAT_BENEFIT_OTHER});
						formObject.setValue(CAT_BENEFIT_OTHER, "");
					}
					manageCategoryChangeSection();
					if(!formObject.getValue(NEW_CUST_SEGMENT).toString().isEmpty()) {
						formObject.setStyle(IS_CAT_BENEFIT_OTHER, DISABLE, FALSE);		
						formObject.setValue(IS_CAT_BENEFIT_OTHER, FALSE);
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE, TRUE);
						formObject.setValue(CAT_BENEFIT_OTHER, "");
					} else {
						formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
						formObject.clearCombo(EXCELLENCY_CENTER_CC);
					}
					clearFBData();	//Family Banking
				}else if(controlName.equalsIgnoreCase(DFC_STATIONERY_AVAIL)) {
					try {
						if(formObject.getValue(DFC_STATIONERY_AVAIL).toString().isEmpty() && fetchInfoFlag) {
							updateDataInDB(sExtTable,INSTANT_DEL_NO,"'true'","WI_NAME ='"
									+sWorkitemId+"'");
							updateDataInDB(sExtTable,INSTANT_DEL_YES,"'false'","WI_NAME ='"
									+sWorkitemId+"'");
							 formObject.setValue(RD_INST_DEL, "No");
							/*formObject.setValue(INSTANT_DEL_YES,FALSE);
							formObject.setValue(INSTANT_DEL_NO,TRUE); */
							disableControls(new String[]{INSTANT_DEL_YES, INSTANT_DEL_NO});
						} else if(!formObject.getValue(DFC_STATIONERY_AVAIL).toString().isEmpty()) {
							enableControls(new String[]{INSTANT_DEL_YES, INSTANT_DEL_NO});
							updateDataInDB(sExtTable,INSTANT_DEL_YES,"'true'","WI_NAME ='"
									+sWorkitemId+"'");
							updateDataInDB(sExtTable,INSTANT_DEL_NO,"'false'","WI_NAME ='"
									+sWorkitemId+"'");
							 formObject.setValue(RD_INST_DEL, "Yes");
						/*	formObject.setValue(INSTANT_DEL_NO,FALSE);
							formObject.setValue(INSTANT_DEL_YES,TRUE); */
						}
					} catch(Exception e) {
						logError("CHANGE EVENT: DFC_STATIONERY_AVAIL", e);;
					}
				}else if (RD_INST_DEL.equalsIgnoreCase(controlName)){
					logInfo("RD_INST_DEL","inside if  no radio button is clicked");
					if(formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("No"))
					{
						FETCH_INFO_flag_NO=false;
						FETCH_INFO_flag=false;
						formObject.setStyle(NOM_REQ,DISABLE,TRUE);
						formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
						formObject.clearTable(DELIVERY_PREFERENCE);
						logInfo("","KioskId is null.");
						int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'true'","WI_NAME ='"+sWorkitemId+"'");
						logInfo("","Update query output sout-----"+sOut1);
						int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'false'","WI_NAME ='"+sWorkitemId+"'");
					} else if (formObject.getValue(RD_INST_DEL).toString().equalsIgnoreCase("Yes")) {
						logInfo("","KioskId is null.");
						int sOut1=updateDataInDB(sExtTable,INSTANT_DEL_NO,"'false'","WI_NAME ='"+sWorkitemId+"'");
						logInfo("","Update query output sout-----"+sOut1);
						int sOutt1=updateDataInDB(sExtTable,INSTANT_DEL_YES,"'true'","WI_NAME ='"+sWorkitemId+"'");
				}
				}else if (NOM_REQ.equalsIgnoreCase(controlName)){
					String val = formObject.getValue(NOM_REQ).toString();
						if("Yes".equalsIgnoreCase(val))	
						{			
							formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,FALSE);
							formObject.setValue(EXISTING_NOM_PRSN,"");				
							Frame48_CPD_ENable();
							manageNomineeDetails(val);
						}
						else
						{
							formObject.setStyle(EXISTING_NOM_PRSN,DISABLE,TRUE);
							formObject.setValue(EXISTING_NOM_PRSN,"");				
							Frame48_CPD_Disable();
							manageNomineeDetails(val);
						}
					
				} else if(EXISTING_NOM_PRSN.equalsIgnoreCase(controlName))
				{
					String val = formObject.getValue(EXISTING_NOM_PRSN).toString();
					if("Yes".equalsIgnoreCase(val))	
					{
						String cust_id = formObject.getTableCellValue(ACC_RELATION, 0, 2).toString();
						String sql="select nom_name,nom_po_box,nom_add1||nom_add2,nom_land,nom_city,nom_state,nom_others"
								+ ",nom_cntry,nom_fax,nom_zip,nom_email,nom_pref_lang,nom_phone,cust_id,"
								+ "nom_id,nom_mob from usr_0_nom_mast where cust_id ='"+cust_id+"'";
						logInfo("","ok"+cust_id+"---------"+sql);
						List<List<String>> sOutput = formObject.getDataFromDB(sql);
						logInfo("","sOutput "+sOutput);
						loadListView(sOutput, "NAME,POBOX,ADDRESS,LANDMARK,CITY,STATE,OTHERS,COUNTRY,FAX,"
								+ "ZIPCODE,EMAIL,PREFLANG,PHONE,CID,NOMID,MOBNO", DELIVERY_PREFERENCE);
						logInfo("","ok"+cust_id+"---------"+sql);
						logInfo("","************ Count ************** "+getGridCount(DELIVERY_PREFERENCE));
						if(getGridCount(DELIVERY_PREFERENCE)==0)
						{
							sendMessageValuesList(EXISTING_NOM_PRSN, "There Is No Existing Nominee For This Customer");
							formObject.setValue(EXISTING_NOM_PRSN, "");
							getReturnMessage(false);
						}
						//formObject.setNGEnable("Frame48",true);
						Frame48_CPD_ENable();
					}else
					{

						formObject.clearTable(DELIVERY_PREFERENCE);
					}
				}
				if (controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_MANUAL)) {
					logInfo("CHECKBOX_SELECTALL_MANUAL","In SELECT ALL CHECK BOX");
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR,CHECKBOX_SELECTALL_EIDA);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					setManualFieldsEnable();    
					manageManualCheckBoxes();
					setManualFieldsLock();
					formObject.setValue(MANUAL_ADDRESS, "PO BOX ");
					if (controlName.equalsIgnoreCase(CHECKBOX_PREFIX_MANUAL)) {
						toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA);
						manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
					}
					if (controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_MANUAL)) {
						toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA);
						manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
						if (formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE)
								&& formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA")) {
							logInfo("","MDSA");
						}
						if (formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE)
								&& formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("")) {
							enableControls(new String[] { MANUAL_VISANO });
						}
					}
					if (controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_MANUAL)) {
						logInfo("checkboxevent1","visaissuedatemanual");
						toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA);
						manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
					}
					if (controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_MANUAL)) {
						logInfo("checkboxevent1","visaexpirydatemanual");
						toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA);
						manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
					}

					if (controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_MANUAL)) {
						toggleCheckbox(controlName, CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA);
						manageManualFields(CHECKBOX_PROFESSION_MANUAL, MANUAL_PROFESSION,BTNPROFESSION);
					}

					if (controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_MANUAL)) {
						toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA);
						manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
					}
					if (controlName.equalsIgnoreCase(CHECKMANUAL)) {
						toggleCheckbox(controlName, CHECKFCR,CHECKEIDA);
						manageManualFields(CHECKMANUAL,MANUAL_RESIDENT);
						manageResidencyAndVisaStatus(RESIDENCY_STATUS, VISA_STATUS);
					}

					if (controlName.equalsIgnoreCase(ED_CB_TML)) {
						if (formObject.getValue(ED_CB_TML).toString().equalsIgnoreCase(TRUE)) {
							formObject.setValue(ED_CB_NON_TML, "false");
						} else {
							formObject.setValue(ED_CB_TML, "false");
						}
					}
					if (controlName.equalsIgnoreCase(ED_CB_NON_TML)) {
						if (formObject.getValue(ED_CB_NON_TML).toString().equalsIgnoreCase(TRUE)) {
							formObject.setValue(ED_CB_TML, "false");
						} else {
							formObject.setValue(ED_CB_NON_TML, "false");
						}
					}
					if (controlName.equalsIgnoreCase(IS_SALARY_TRANSFER_CAT_CHANGE)) {
						toggleCheckboxTP(controlName,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,
								IS_OTHERS_CAT_CHANGE,IS_VVIP);
						managePromoCodeCatChange();
					}
					if (controlName.equalsIgnoreCase(IS_MORTAGAGE_CAT_CHANGE)) {
						toggleCheckboxTP(controlName, IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,
								IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
						managePromoCodeCatChange();
					}
					if (controlName.equalsIgnoreCase(IS_INSURANCE_CAT_CHANGE)) {
						toggleCheckboxTP(controlName, IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
								IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
						managePromoCodeCatChange();
					}
					if (controlName.equalsIgnoreCase(IS_TRB_CAT_CHANGE)) {
						toggleCheckboxTP(controlName, IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
								IS_INSURANCE_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
						managePromoCodeCatChange();
					}
					if (controlName.equalsIgnoreCase(IS_OTHERS_CAT_CHANGE)) {
						toggleCheckboxTP(controlName, IS_VVIP,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
								IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE);
						managePromoCodeCatChange();
					}
					if (controlName.equalsIgnoreCase(IS_VVIP)) {
						toggleCheckboxTP(controlName,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
								IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE);
						managePromoCodeCatChange();
					}
					if (controlName.equalsIgnoreCase(IS_PREVILEGE_TP_CAT_CHANGE)) {
						toggleCheckboxTP(controlName,IS_TRAVEL_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,
								IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
					}
					if (controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)) {
						toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,
								IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
					}
					if (controlName.equalsIgnoreCase(IS_TRAVEL_CAT_CHANGE)) {
						toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,
								IS_SHOPPING_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
					}
					if (controlName.equalsIgnoreCase(IS_SHOPPING_CAT_CHANGE)) {
						toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,
								IS_TRAVEL_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE);
					}
					if (controlName.equalsIgnoreCase(IS_ENTERTAINMENT_CAT_CHANGE)) {
						toggleCheckboxTP(controlName,IS_PREVILEGE_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,
								IS_TRAVEL_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_EIDA);
					manageManualFields(CHECKBOX_COUNTRY_RES_MANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
				} else if(controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_RES_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_COUNTRY_RES_EIDA,CHECKBOX_COUNTRY_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_RES_MANUAL,MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RA_IS_UAE_RESIDENT,"COMBO34");
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(RES_CNTRY,formObject.getValue(FCR_RESIDENT).toString());

						if(formObject.getValue(FCR_RESIDENT).toString().equalsIgnoreCase("UNITED ARAB EMIRATES"))
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, FALSE);
						else
							formObject.setStyle(CONTACT_DETAILS_MAKANI_NO, DISABLE, TRUE);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_MANUAL)) {
					logInfo("EVENT_TYPE_CHANGE", "CHECKBOX_VISA_NO_MANUAL");
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
					if (formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE)
							&& formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("MDSA")) {
						logInfo("","MDSA");
					}
					if (formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE)
							&& formObject.getValue(MANUAL_VISANO).toString().equalsIgnoreCase("")) {
						enableControls(new String[] { MANUAL_VISANO });
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_FCR)) {
					logInfo("checkboxevent1","visaissuedatefcr");
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_FCR)) {
					logInfo("checkboxevent1","visaexpirydatefcr");
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_ISSUE_DATE_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_ISSUE_DATE_MANUAL,MANUAL_VISAISSDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_EXPIRY_DATE_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_MANUAL);
					manageManualFields(CHECKBOX_VISA_EXPIRY_DATE_MANUAL,MANUAL_VISAEXPDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_PROFESSION_EIDA, CHECKBOX_PROFESSION_MANUAL);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL, MANUAL_PROFESSION,BTNPROFESSION);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL,MANUAL_PROFESSION,BTNPROFESSION);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME); 
				} else if (controlName.equalsIgnoreCase(CHECKBOX_GENDER_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL);
					manageManualFields(CHECKBOX_GENDER_MANUAL, MANUAL_GENDER);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME, BTNEMLOYERNAME);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMP_NAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME,BTNEMLOYERNAME);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EMAIL_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_EMAIL_FCR, CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL, MANUAL_EMAIL);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PROFESSION_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_PROFESSION_FCR, CHECKBOX_PROFESSION_MANUAL);
					manageManualFields(CHECKBOX_PROFESSION_MANUAL, MANUAL_PROFESSION,BTNPROFESSION);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_GENDER_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_MANUAL);
					manageManualFields(CHECKBOX_GENDER_MANUAL, MANUAL_GENDER);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_DOB_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_DOB_FCR,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_MANUAL);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_MANUAL);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_MANUAL);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RESIDENCY_STATUS,VISA_STATUS);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_MANUAL);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL,MANUAL_MOTHERNAME);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EIDANO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CITY_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_CITY_FCR,CHECKBOX_CITY_MANUAL);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_STATE_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_STATE_FCR,CHECKBOX_STATE_MANUAL);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_MANUAL);
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL,MANUAL_CNTRY);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_TELE_RES_FCR, CHECKBOX_TELE_RES_MANUAL);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL, MANUAL_PH);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_TELE_MOB_FCR, CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL, MANUAL_MOBILE);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL,MANUAL_MOTHERNAME);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL);
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL,MANUAL_CNTRY);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("EVENT_TYPE_CHANGE","sAccRelation: "+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CITY_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_CITY_EIDA,CHECKBOX_CITY_MANUAL);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_STATE_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_STATE_EIDA,CHECKBOX_STATE_MANUAL);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL, MANUAL_PH);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_TELE_MOB_EIDA, CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL, MANUAL_MOBILE);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EMAIL_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_EMAIL_EIDA, CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL, MANUAL_EMAIL);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_DOB_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RESIDENCY_STATUS,VISA_STATUS);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if (((String) formObject.getValue(CHECKBOX_FULLNAME_FCR)).equalsIgnoreCase("True")
							&& ((String) formObject.getValue(CHECKBOX_SHORTNAME_MANUAL)).equalsIgnoreCase("True")) {

						shortnamefunctionality();
					} else {
						formObject.setValue(MANUAL_SHORTNAME, "");
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
				} else if (controlName.equalsIgnoreCase(ARE_U_PEP) 
						&& sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {
					logInfo("onChange","ARE_U_PEP"+formObject.getValue(ARE_U_PEP));
					if (formObject.getValue(ARE_U_PEP).toString().equalsIgnoreCase("Yes")) {
						logInfo("onChange","ARE_U_PEP 1");
						enableControls(new String[] { RA_LIST_OF_CUST_PEP });
						logInfo("onChange","ARE_U_PEP2");
					} else {
						logInfo("onChange","ARE_U_PEP3");
						formObject.setValue(RA_LIST_OF_CUST_PEP, "");
						logInfo("onChange","ARE_U_PEP4");
						disableControls(new String[] { RA_LIST_OF_CUST_PEP });
						logInfo("onChange","ARE_U_PEP5");
					}
				} else if (controlName.equalsIgnoreCase(EMP_STATUS)
						&& sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)
						&& !formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("")) {
					logInfo("","In EMP Status12----" + data);
					if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Employed")) {
						String [] fieldName = {RA_CB_GEN_TRDNG_CMPNY,RA_CB_PRECIOUS_STONE_DEALER,RA_CB_BULLN_COMMDTY_BROKR,
								RA_CB_REAL_STATE_BROKR,RA_CB_USD_AUTO_DEALER};
						String [] fieldValue = {FALSE,FALSE,FALSE,FALSE,FALSE};
						setMultipleFieldValues(fieldName, fieldValue);
						/*formObject.setStyle(RA_CB_GEN_TRDNG_CMPNY,DISABLE, TRUE);
						formObject.setStyle(RA_CB_PRECIOUS_STONE_DEALER,DISABLE, TRUE);
						formObject.setStyle(RA_CB_BULLN_COMMDTY_BROKR,DISABLE, TRUE);
						formObject.setStyle(RA_CB_REAL_STATE_BROKR,DISABLE, TRUE);
						formObject.setStyle(RA_CB_USD_AUTO_DEALER,DISABLE, TRUE);*/
						disableControls(BUSINESS_NATURE_SECTION);
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY, "false");
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,"false");
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,"false");
						formObject.setValue(RA_CB_REAL_STATE_BROKR, "false");
						formObject.setValue(RA_CB_USD_AUTO_DEALER, "false");
						formObject.setValue(FINANCIAL_BROKERS,"false");
						formObject.setValue(NOTARY_PUBLIC,"false");
						formObject.setValue(SOCIAL_MEDIA_INFLUNCER,"false");
						formObject.setValue(RA_CB_OTHERS,"false");
						formObject.setValue(RA_CB_OTHERS_FLD,"");
						enableControls(new String[] { EMPLYR_TYPE1, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
					} else if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Self Employed")) {
						formObject.setValue(RA_IS_CUST_WRKNG_UAE, "");
						formObject.setValue(RA_IS_CUST_WRKNG_NON_UAE, "");
						formObject.setValue(EMPLYR_TYPE1, "");
						//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
						disableControls(new String[] { EMPLYR_TYPE1, 
								RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
						enableControls(BUSINESS_NATURE_SECTION);
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
					} else {
						//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
						disableControls(new String[] { EMPLYR_TYPE1, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE,RA_CB_OTHERS_FLD });
						disableControls(BUSINESS_NATURE_SECTION);
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
						formObject.setValue(RA_CB_OTHERS,"false");
						formObject.setValue(RA_CB_OTHERS_FLD,"");
						//if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Retired")) {
						if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Salaried")) {	
						    //disableControls(BUSINESS_NATURE_SECTION );
						    //formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
							enableControls(new String[] { RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
							formObject.setStyle(RA_CB_GEN_TRDNG_CMPNY,DISABLE, TRUE);
							formObject.setStyle(RA_CB_PRECIOUS_STONE_DEALER,DISABLE, TRUE);
							formObject.setStyle(RA_CB_BULLN_COMMDTY_BROKR,DISABLE, TRUE);
							formObject.setStyle(RA_CB_REAL_STATE_BROKR,DISABLE, TRUE);
							formObject.setStyle(RA_CB_USD_AUTO_DEALER,DISABLE, TRUE);
							formObject.setStyle(FINANCIAL_BROKERS,DISABLE, TRUE);
							formObject.setStyle(NOTARY_PUBLIC,DISABLE, TRUE);
							formObject.setStyle(SOCIAL_MEDIA_INFLUNCER,DISABLE, TRUE);
							formObject.setStyle(RA_CB_OTHERS,DISABLE, TRUE);
							formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
							formObject.setValue(RA_CB_GEN_TRDNG_CMPNY,"false");
							formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER, "false");
							formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,"false");
							formObject.setValue(RA_CB_REAL_STATE_BROKR,"false");
							formObject.setValue(RA_CB_USD_AUTO_DEALER,"false");
							formObject.setValue(FINANCIAL_BROKERS,"false");
							formObject.setValue(NOTARY_PUBLIC,"false");
							formObject.setValue(SOCIAL_MEDIA_INFLUNCER,"false");
							formObject.setValue(RA_CB_OTHERS,"false");
							formObject.setValue(RA_CB_OTHERS_FLD,"");
						}
						/*else{
							formObject.setStyle(RA_CB_GEN_TRDNG_CMPNY,DISABLE, FALSE);
							formObject.setStyle(RA_CB_PRECIOUS_STONE_DEALER,DISABLE, FALSE);
							formObject.setStyle(RA_CB_BULLN_COMMDTY_BROKR,DISABLE, FALSE);
							formObject.setStyle(RA_CB_REAL_STATE_BROKR,DISABLE, FALSE);
							formObject.setStyle(RA_CB_USD_AUTO_DEALER,DISABLE, FALSE);
							formObject.setStyle(RA_CB_OTHERS,DISABLE, FALSE);
							formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, FALSE);
						}*/
					}
					if (!formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Self Employed")) {
						disableControls(new String[] { IDS_CB_VVIP,GI_YEARS_IN_UAE });
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					} else {
						enableControls(new String[] { IDS_CB_VVIP,GI_YEARS_IN_UAE });
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
				} else if (controlName.equalsIgnoreCase(RA_CB_PRECIOUS_STONE_DEALER)) {   //Pending for check
					if (formObject.getValue(IDS_CB_VVIP).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
				} else if (controlName.equalsIgnoreCase(RA_CB_BULLN_COMMDTY_BROKR)) {     //Pending for check
					if (formObject.getValue(IDS_CB_VVIP).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
				} else if (controlName.equalsIgnoreCase(RA_CB_REAL_STATE_BROKR)) {       //Pending for check
					if (formObject.getValue(IDS_CB_VVIP).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
				} else if (controlName.equalsIgnoreCase(RA_CB_USD_AUTO_DEALER)) {   	//pending for check	
					if (formObject.getValue(IDS_CB_VVIP).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
				} else if (controlName.equalsIgnoreCase(CRO_DEC)) {
					String sData  = formObject.getValue(CRO_DEC).toString();
					if (controlName.equalsIgnoreCase(CRO_DEC)&& sData.equalsIgnoreCase("Approve")) {
						logInfo("CRO_DEC","INSIDE CRO_DEC CONDITION ");
						formObject.setStyle(CRO_REJ_REASON, DISABLE,TRUE);
						formObject.setValue(CRO_REJ_REASON, "");
					} else {
						formObject.setStyle(CRO_REJ_REASON, DISABLE,FALSE);
					}
				} else if (controlName.equalsIgnoreCase(CRO_REJ_REASON)) {
					logInfo("CRO_REJ_REASON","INSIDE CRO_REJ_REASON CONDITION ");
					if (!formObject.getValue(CRO_REJ_REASON).toString().equalsIgnoreCase("")) {
						if (formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
							sendMessageValuesList(CRO_DEC,"Please select user decision first");
							formObject.setValue(CRO_REJ_REASON, "");
						}
					}
				} else if(controlName.equalsIgnoreCase(CRS_CERTIFICATION_OBTAINED)) {
					if("Yes".equalsIgnoreCase((String) formObject.getValue(CRS_CERTIFICATION_OBTAINED))) {
						formObject.addItemInCombo(CRS_CLASSIFICATION,"UPDATED-DOCUMENTED","UPDATED-DOCUMENTED");
						formObject.setValue(CRS_CLASSIFICATION,"UPDATED-DOCUMENTED");
					} else {
						formObject.addItemInCombo(CRS_CLASSIFICATION,"UPDATED-UNDOCUMENTED","UPDATED-UNDOCUMENTED");
						formObject.setValue(CRS_CLASSIFICATION,"UPDATED-UNDOCUMENTED");
					}
				} else if(controlName.equalsIgnoreCase(CRS_CB)){
					//if(formObject.getValue(CRS_CB).toString().equalsIgnoreCase(TRUE))
					//setStyle("tab4","5","disable","false");

				} else if(controlName.equalsIgnoreCase(MANUAL_VISASTATUS)) { 
					logInfo("MANUAL_VISASTATUS","Inside Click event of Visa status ");
					if (formObject.getValue(MANUAL_VISASTATUS).toString().equalsIgnoreCase("Under Processing"))
					{
						logInfo("MANUAL_VISASTATUS","before MDSA " + sOnLoad + mohit_flag);
						String updatequery = "UPDATE USR_0_CUST_TXN set VISA_NO='MDSA' WHERE cust_sno='"
								+ getPrimaryCustomerSNO()+ "' AND WI_NAME = '"+ sWorkitemId + "'";
						formObject.saveDataInDB(updatequery);
						logInfo("MANUAL_VISASTATUS","Udate successful" + updatequery);
						formObject.setValue(CHECKBOX_VISA_NO_MANUAL, TRUE);
						formObject.setValue(CHECKBOX_VISA_NO_FCR, "false");
						formObject.setValue(CHECKBOX_VISA_NO_EIDA, "false");
						if (formObject.getValue(CHECKBOX_SELECTALL_MANUAL).toString().equalsIgnoreCase(TRUE)
								|| formObject.getValue(CHECKBOX_VISA_NO_MANUAL).toString().equalsIgnoreCase(TRUE))
						{
							formObject.setValue(MANUAL_VISANO, "MDSA");
						}
						sendMessageValuesList("", CA017);
					}
				} else if (controlName.equalsIgnoreCase(ACC_INFO_UDF_FIELD)) {
					if (formObject.getValue(ACC_INFO_UDF_FIELD).toString().equalsIgnoreCase("Graduation Date")) {
						formObject.setValue(ACC_INFO_UDF_VALUE, "DD/MM/YYYY");
					} else {
						formObject.setValue(ACC_INFO_UDF_VALUE, "");
					}
				} else if (controlName.equalsIgnoreCase(ACC_INFO_UDF_VALUE)) {
					//formObject.setValue(ACC_INFO_UDF_VALUE, "");
				} else if(controlName.equalsIgnoreCase(MANUAL_MOBILE)){
					if("United Arab Emirates".equalsIgnoreCase(checkCountry())) {
						mobileChangeFlag = true;
						String sNumber=formObject.getValue(MANUAL_MOBILE).toString();
						//Modifed by SHivanshu ATP-472
						if(!(sNumber.startsWith("971") || sNumber.startsWith("+971") || sNumber.startsWith("00971"))) {
							sendMessageValuesList("","Mobile Number does not start with 971");
						}
					}
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(EIDA_RESIDENT)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_RESIDENT)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FAT_US_PERSON)) {
					logInfo("FAT_US_PERSON","Inside FAT_US_PERSON Condition");
					String controlValue = formObject.getValue(controlName).toString();
					logInfo("FAT_US_PERSON","FAT_US_PERSON ::  "+controlValue);
					if (controlValue.equalsIgnoreCase("YES")) {
						formObject.setValue(FAT_LIABLE_TO_PAY_TAX, "YES");
						formObject.setStyle(FAT_LIABLE_TO_PAY_TAX, DISABLE,TRUE);
					} else {
						formObject.setValue(FAT_LIABLE_TO_PAY_TAX, "");
						formObject.setStyle(FAT_LIABLE_TO_PAY_TAX, DISABLE, FALSE);
					}
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) ;
						logInfo("FAT_US_PERSON","fieldToFocus  :: "+ fieldToFocus);
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FAT_LIABLE_TO_PAY_TAX)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(CNTRY_OF_BIRTH)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(CHECKMANUAL)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(POACOMBO)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FAT_SSN)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(INDICIACOMBO)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				}
				else if(controlName.equalsIgnoreCase(DATEPICKERCUST)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(DATEPICKERW8)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(EIDA_MOBILE)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(EIDA_NATIONALITY)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_NATIONALITY)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(EIDA_CNTRY)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_CNTRY)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FCR_PH)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(EIDA_PH)) {
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FCR_MOBILE)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(MANUAL_PH)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("MANUAL_PH","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase("accountInfoUDFGrid_dialog")) {
					formObject.clearCombo(ACC_INFO_UDF_FIELD);
					formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Graduation Date","Graduation Date");
					formObject.addItemInCombo(ACC_INFO_UDF_FIELD, "Special Customer Identifier","Special Customer Identifier");
				} else if(controlName.equalsIgnoreCase(FCR_NATIONALITY)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if(controlName.equalsIgnoreCase(FCR_CNTRY)){
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(MANUAL_FIRSTNAME)
						|| controlName.equalsIgnoreCase(MANUAL_LASTNAME)) {
					formObject.setValue(MANUAL_NAME,formObject.getValue(MANUAL_FIRSTNAME).toString() + " "+
							formObject.getValue(MANUAL_LASTNAME).toString());
					formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
					formObject.setValue(CRS_LASTNAME,formObject.getValue(MANUAL_LASTNAME).toString());
					formObject.setValue(MANUAL_SHORTNAME,formObject.getValue(MANUAL_NAME).toString());
					if(TRUE.equalsIgnoreCase(formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString())){
						shortnamefunctionality();
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PREFIX_MANUAL)) {
					logInfo("","Inside CHECKBOX_PREFIX_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA);
					manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_MANUAL)) {
					logInfo("CHECKBOX_FIRSTNAME_MANUAL","Inside CHECKBOX_FIRSTNAME_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME,formObject.getValue(MANUAL_FIRSTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_MANUAL)) {
					logInfo("","Inside CHECKBOX_LASTNAME_MANUAL Change ");
					toggleCheckbox(controlName, CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_LASTNAME, formObject.getValue(MANUAL_LASTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_MANUAL)) {
					logInfo("EVENT_TYPE_CHANGE","Inside CHECKBOX_FULLNAME_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if (formObject.getValue(CHECKBOX_FULLNAME_MANUAL).toString().equalsIgnoreCase(TRUE)
							&& formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)) {
						shortnamefunctionality();
					} else {
						formObject.clearCombo(MANUAL_SHORTNAME);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL)) {
					logInfo("CHECKBOX_SHORTNAME_MANUAL","Inside CHECKBOX_SHORTNAME_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR, CHECKBOX_SHORTNAME_EIDA);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL,MANUAL_SHORTNAME);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_MOTHERSNAME_MANUAL)) {
					logInfo("","Inside CHECKBOX_MOTHERSNAME_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA);
					manageManualFields(CHECKBOX_MOTHERSNAME_MANUAL,MANUAL_MOTHERNAME);
				} else if (controlName.equalsIgnoreCase(COMBODOC)){
					if(formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN")) {
						formObject.setStyle(FAT_SSN,DISABLE,TRUE);
						formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
					}
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EIDANO_MANUAL)) {
					logInfo("","Inside CHECKBOX_EIDANO_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
						setResidentWithoutEidaComboQDE();
					}
					if (formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString().equalsIgnoreCase(TRUE)){
						logInfo("","Inside EIDANO_MANUAL  TRUE");
						formObject.setStyle(MANUAL_EIDANO, DISABLE, FALSE);
						enableControls(new String[] {MANUAL_EIDANO});

					}
					if (formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString().equalsIgnoreCase(FALSE)){
						logInfo("","Inside EIDANO_MANUAL FALSE ");
						formObject.setStyle(MANUAL_EIDANO, DISABLE, TRUE);
						disableControls(new String[] {MANUAL_EIDANO});
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CORR_POB_MANUAL)) {
					logInfo("EVENT_TYPE_CHANGE","Inside CHECKBOX_CORR_POB_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CNTRY_OF_CORR_MANUAL)) {
					logInfo("","Inside CHECKBOX_CNTRY_OF_CORR_MANUAL Change ");
					toggleCheckbox(controlName,CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA);
					manageManualFields(CHECKBOX_CNTRY_OF_CORR_MANUAL,MANUAL_CNTRY);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_STATE_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_STATE_FCR,CHECKBOX_STATE_EIDA);
					manageManualFields(CHECKBOX_STATE_MANUAL,MANUAL_STATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_CITY_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_CITY_FCR,CHECKBOX_CITY_EIDA);
					manageManualFields(CHECKBOX_CITY_MANUAL,MANUAL_CITY);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_COUNTRY_PER_RES_MANUAL)) {
					toggleCheckbox(controlName, CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA);
					manageManualFields(CHECKBOX_COUNTRY_PER_RES_MANUAL,MANUAL_PER_CNTRY);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)) {
					toggleCheckbox(controlName, CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
					manageManualFields(CHECKBOX_TELE_RES_MANUAL, MANUAL_PH);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)) {
					toggleCheckbox(controlName, CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL, MANUAL_MOBILE);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EMAIL_MANUAL)) {
					toggleCheckbox(controlName, CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
					manageManualFields(CHECKBOX_EMAIL_MANUAL, MANUAL_EMAIL);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_DOB_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR, CHECKBOX_DOB_EIDA);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASS_ISS_DT_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA);
					manageManualFields(CHECKBOX_PASS_ISS_DT_MANUAL,MANUAL_PASSPORTISSDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_NO_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA);
					manageManualFields(CHECKBOX_PASSPORT_NO_MANUAL,MANUAL_PASSPORTNO);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASS_EXP_DT_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA);
					manageManualFields(CHECKBOX_PASS_EXP_DT_MANUAL,MANUAL_PASSPORTEXPDATE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_NATIONALITY_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA);
					manageManualFields(CHECKBOX_NATIONALITY_MANUAL,MANUAL_NATIONALITY);
					autoSetFatca(controlName);
					manageResidencyAndVisaStatus(RESIDENCY_STATUS, VISA_STATUS);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_GENDER_MANUAL)) {
					toggleCheckbox(controlName, CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_EIDA);
					manageManualFields(CHECKBOX_GENDER_MANUAL,MANUAL_GENDER);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL)
						|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR)
						|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
					logInfo("","INSIDE NEW TOGGLE");
					if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL)) {
						toggleCheckbox(controlName, CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA);
					} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
						toggleCheckbox(controlName, CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_MANUAL);
					} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR)) {
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_PASSPORT_TYPE_EIDA);
					}
					manageManualFields(CHECKBOX_PASSPORT_TYPE_MANUAL,MANUAL_PASSTYPE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_MANUAL)) {
					shortnamefunctionality();
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)) {
					if (formObject.getValue(CHECKBOX_FULLNAME_EIDA).toString().equalsIgnoreCase(TRUE)
							&& formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase(TRUE)) {
						logInfo("CHECKBOX_FULLNAME_EIDA","CHECKBOX_FULLNAME_EIDA control clicked:");
						shortnamefunctionality();
					} else {
						formObject.setValue(MANUAL_SHORTNAME, "");
					}
				} else if (controlName.equalsIgnoreCase(MANUAL_PREFIX)) {
					logInfo("","INSIDE");
					setGender();
				} else if (controlName.equalsIgnoreCase(MANUAL_MOBILE)) {
					flag_mobile = true;
					// flag_phone_start="true";
				} else if (controlName.equalsIgnoreCase(MANUAL_PH)) {
					flag_phone = true;
					// flag_phone_start="true";
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_MANUAL)) {
					if (((String) formObject.getValue(CHECKBOX_FULLNAME_MANUAL)).equalsIgnoreCase("True")
							&& ((String) formObject.getValue(CHECKBOX_SHORTNAME_MANUAL)).equalsIgnoreCase("True")) {
						shortnamefunctionality();
					} else {
						formObject.setValue(MANUAL_SHORTNAME, "");
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
					if (((String) formObject.getValue(CHECKBOX_FULLNAME_EIDA)).equalsIgnoreCase("True")
							&& ((String) formObject.getValue(CHECKBOX_SHORTNAME_MANUAL)).equalsIgnoreCase("True")) {
						logInfo("","sushant control clicked:");
						shortnamefunctionality();
					} else {
						formObject.setValue(MANUAL_SHORTNAME, "");
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR)
						|| controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL)) {
					logInfo("","INSIDE NEW TOGGLE VISA");
					if (controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_FCR))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_MANUAL);
					else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_VISA_STATUS_FCR);
					else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_STATUS_EIDA))
						toggleCheckbox(controlName,CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_MANUAL);
					manageManualFields(CHECKBOX_VISA_STATUS_MANUAL,MANUAL_VISASTATUS);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL)
						|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR)
						|| controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA)) {
					logInfo("","INSIDE NEW TOGGLE");
					if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_MANUAL))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA);
					else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_EIDA))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_MANUAL);
					else if (controlName.equalsIgnoreCase(CHECKBOX_PASSPORT_TYPE_FCR))
						toggleCheckbox(controlName,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_PASSPORT_TYPE_EIDA);
					manageManualFields(CHECKBOX_PASSPORT_TYPE_MANUAL,MANUAL_PASSTYPE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_COB_FCR)
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA)
						|| controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)) { // added
					logInfo("","INSIDE NEW TOGGLE");
					if (controlName.equalsIgnoreCase(CHECKBOX_COB_FCR)){
						toggleCheckbox(controlName, CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL);
						if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
							// NGRepeater objChkRepeater =
							// formObject.getNGRepeater("REPEAT_FRAME");
							int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
							String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
							logInfo("","sAccRelation------"+ sAccRelation);
							manageChangeinFATCAFields(controlName,sAccRelation);
							autoSetFatca(controlName);
						}
					}
					else if (controlName.equalsIgnoreCase(CHECKBOX_COB_EIDA)){
						toggleCheckbox(controlName, CHECKBOX_COB_FCR,CHECKBOX_COB_MANUAL);
						if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
							// NGRepeater objChkRepeater =
							// formObject.getNGRepeater("REPEAT_FRAME");
							int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
							String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
							logInfo("","sAccRelation------"+ sAccRelation);
							manageChangeinFATCAFields(controlName,sAccRelation);
							autoSetFatca(controlName);
						}
					}
					else if (controlName.equalsIgnoreCase(CHECKBOX_COB_MANUAL)){
						toggleCheckbox(controlName, CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA);
						if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
							// NGRepeater objChkRepeater =
							// formObject.getNGRepeater("REPEAT_FRAME");
							int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
							String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
							logInfo("","sAccRelation------"+ sAccRelation);
							manageChangeinFATCAFields(controlName,sAccRelation);
							autoSetFatca(controlName);
						}
					}
					manageManualFields(CHECKBOX_COB_MANUAL,MANUAL_COUNTRYBIRTH);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_FIRSTNAME_MANUAL);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME, formObject.getValue(FCR_FIRSTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_MANUAL);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME, formObject.getValue(EIDA_FIRSTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FIRSTNAME_MANUAL)) {
					toggleCheckbox(controlName, CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA);
					manageManualFields(CHECKBOX_FIRSTNAME_MANUAL,MANUAL_FIRSTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_FIRSTNAME, formObject.getValue(MANUAL_FIRSTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_LASTNAME, formObject.getValue(FCR_LASTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_LASTNAME_EIDA)) {
					toggleCheckbox(controlName, CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_MANUAL);
					manageManualFields(CHECKBOX_LASTNAME_MANUAL,MANUAL_LASTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(CRS_LASTNAME, formObject.getValue(EIDA_LASTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(MAKER_NAME)) {
					String sReqType = formObject.getValue(REQUEST_TYPE).toString();
					if (sReqType.equalsIgnoreCase("New Account with Category Change")
							|| sReqType.equalsIgnoreCase("Category Change Only")) 
					{
						formObject.setValue(MAKER_NAME_CAT_CHANGE, formObject.getValue(MAKER_NAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(MAKER_CODE)) {
					String sReqType = formObject.getValue(REQUEST_TYPE).toString();
					if (sReqType.equalsIgnoreCase("New Account with Category Change")
							|| sReqType.equalsIgnoreCase("Category Change Only")) 
					{
						formObject.setValue(MAKER_CODE_CAT_CHANGE, formObject.getValue(MAKER_CODE).toString());
					}
				} else if (formObject.getValue(CHECKBOX_SHORTNAME_MANUAL).toString().equalsIgnoreCase("false")) {
					formObject.setValue(MANUAL_SHORTNAME, "");
				} else if (controlName.equalsIgnoreCase("CHECK17")) {
					toggleCheckbox(controlName, "CHECK62","CHECK39");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_VISA_NO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL);
					manageManualFields(CHECKBOX_VISA_NO_MANUAL,MANUAL_VISANO);
				} else if (controlName.equalsIgnoreCase(CRS_CHECKBOX)) {
					if (formObject.getValue(CRS_CHECKBOX).toString().equalsIgnoreCase(TRUE))
						formObject.setStyle(SEC_CRS_DETAILS, DISABLE, FALSE);
					else
						formObject.setStyle(SEC_CRS_DETAILS, DISABLE, TRUE);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_EIDA, CHECKBOX_SELECTALL_MANUAL);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					manageManualCheckBoxes();
					setManualFieldsEnable();
					setManualFieldsLock();
					formObject.setValue(MANUAL_ADDRESS, "");
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PREFIX_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_PREFIX_EIDA,CHECKBOX_PREFIX_MANUAL);
					manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_FCR)) {
					toggleCheckbox(controlName, CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL,MANUAL_SHORTNAME);
					if (data.equalsIgnoreCase(TRUE)) {
						formObject.setValue(PD_SHORTNAME, formObject.getValue(FCR_SHORTNAME).toString());
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_EIDANO_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
				} else if (controlName.equalsIgnoreCase(ED_CB_TML)) {
					toggleCheckbox(controlName,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL);
					manageManualFields(CHECKBOX_CORR_POB_MANUAL,MANUAL_ADDRESS);
				} else if (controlName.equalsIgnoreCase(CHECKFCR)) {
					toggleCheckbox(controlName, CHECKEIDA,CHECKMANUAL);
					manageManualFields(CHECKMANUAL, MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RESIDENCY_STATUS,VISA_STATUS);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(CHECKBOX_SELECTALL_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_SELECTALL_FCR, CHECKBOX_SELECTALL_MANUAL);
					manageFCRCheckBoxes();
					manageEIDACheckBoxes();
					manageManualCheckBoxes();
					setManualFieldsEnable();
					setManualFieldsLock();
					formObject.setValue(MANUAL_ADDRESS, "");
				} else if (controlName.equalsIgnoreCase(CHECKBOX_PREFIX_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_MANUAL);
					manageManualFields(CHECKBOX_PREFIX_MANUAL,MANUAL_PREFIX);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_FULLNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_MANUAL);
					manageManualFields(CHECKBOX_FULLNAME_MANUAL,MANUAL_NAME);
				} else if (controlName.equalsIgnoreCase(CHECKBOX_SHORTNAME_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_MANUAL);
					manageManualFields(CHECKBOX_SHORTNAME_MANUAL,MANUAL_SHORTNAME);
				} else if (controlName.equalsIgnoreCase("CHECK62")) {
					toggleCheckbox(controlName, "CHECK17","CHECK39");
					manageManualFields("CHECK39",PASSPORTEXPPLACE_MANUAL);
				} else if(controlName.equalsIgnoreCase("CHECK72")) {
					toggleCheckbox(controlName,CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_MANUAL);
					manageManualFields(CHECKBOX_EMP_NAME_MANUAL,MANUAL_EMPLYR_NAME, BTNEMLOYERNAME);
				} else if (controlName.equalsIgnoreCase(CHECKEIDA)) {
					toggleCheckbox(controlName, CHECKFCR,CHECKMANUAL);
					manageManualFields(CHECKMANUAL, MANUAL_RESIDENT);
					manageResidencyAndVisaStatus(RESIDENCY_STATUS,VISA_STATUS);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}
				} else if (controlName.equalsIgnoreCase(COMBODOC)) {
					if (data.equalsIgnoreCase("W9")) {
						formObject.setStyle(FAT_SSN, ENABLE, TRUE);
						formObject.setValue(DATEPICKERW8, "");
						formObject.setStyle(DATEPICKERW8,  ENABLE, TRUE);
					} else if (data.equalsIgnoreCase("W8BEN")) {
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
						String scurrentDate = simpledateformat.format(calendar.getTime());
						formObject.setValue(FAT_SSN, "");
						// formObject.setStyle(FAT_SSN,false);
						formObject.setStyle(FAT_SSN, ENABLE, FALSE);
						formObject.setStyle(DATEPICKERW8, ENABLE, TRUE);
						formObject.setValue(DATEPICKERW8, scurrentDate);
					} else {
						formObject.setValue(FAT_SSN, "");
						formObject.setValue(DATEPICKERW8, "");
						formObject.setStyle(FAT_SSN, ENABLE, FALSE);
						// formObject.setStyle(FAT_SSN,false);
						formObject.setStyle(DATEPICKERW8, ENABLE, FALSE);
						// formObject.setStyle(DATEPICKERW8,false);
					}
					String sFinalCountryOfBirth = getFinalDataComparison(
							CHECKBOX_COB_FCR, CHECKBOX_COB_EIDA, CHECKBOX_COB_MANUAL,FCR_COUNTRYBIRTH, EIDA_COUNTRYBIRTH,
							MANUAL_COUNTRYBIRTH); 
					logInfo("COMBODOC",sFinalCountryOfBirth+ "combodoc sFinalCountryOfBirth");
					if (sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
						logInfo("","sFinalCountryOfBirth inside combodoc");
						List<List<String>> sOutput = formObject.getDataFromDB("SELECT FATCA_CLASSIFICATION FROM USR_0_DOC_MASTER WHERE DOC_NAME ='"+ data + "'");
						if (sOutput.size() != 0) {
							logInfo("","sOuput----"+ (String) ((List) sOutput.get(0)).get(0));
							formObject.setValue(FAT_CUST_CLASSIFICATION,(String) ((List) sOutput.get(0)).get(0));
						}
					} else {
						logInfo("", formObject.getValue(INDICIACOMBO)+ INDICIACOMBO);
						if (formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("No")) {
							logInfo("", "inside NON US PERSON");
							formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
						} else if (formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase("Yes")) {
							logInfo("", "Inside  US PERSON");
							formObject.setValue(FAT_CUST_CLASSIFICATION,"US PERSON");
						}
					}
				} 
				// EIDA Ended
				// Manual CheckBoxes
				else if (controlName.equalsIgnoreCase(ED_MONTHLY_INCM)) {
					String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();
					try {
						if (mnthsalary.equalsIgnoreCase("")) {
							formObject.setValue(ED_ANNUAL_INC, "");
							formObject.setValue(ED_SAL_AED, "");
						}
						long mnthslry = Long.parseLong(mnthsalary);
						long finalsalary = mnthslry * 12;
						formObject.setValue(ED_ANNUAL_INC, finalsalary + "");
						formObject.setValue(ED_CB_SAL_AED, TRUE);
						if (((String) formObject.getValue(ED_CB_SAL_AED)).equalsIgnoreCase(TRUE)) {
							formObject.setValue(ED_SAL_AED, finalsalary + "");
						}
					} catch (Exception ex) {
						logInfo("executeServerEvent", "Exception in Event- "+ eventType + "control name- " + controlName+ ": ");
						logError("executeServerEvent", ex);
					}
				} else if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO) 
						&& (controlName.equalsIgnoreCase(FCR_EMPLYR_NAME)
								|| controlName.equalsIgnoreCase(EIDA_EMPLYR_NAME)
								|| controlName.equalsIgnoreCase(MANUAL_EMPLYR_NAME))) {
					String sIsFCREmpName = formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString();
					String sIsEIDAEmpName = formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString();
					String sIsManualEmpName = formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString();
					String sFCREmpName = formObject.getValue(FCR_EMPLYR_NAME).toString();
					String sEIDAEmpName = formObject.getValue(EIDA_EMPLYR_NAME).toString();
					String sManualEmpName = formObject.getValue(MANUAL_EMPLYR_NAME).toString();
					String sFinalEmpName = getFinalData(sIsFCREmpName,sIsEIDAEmpName, sIsManualEmpName, 
							sFCREmpName,sEIDAEmpName, sManualEmpName);
					logInfo("EMPLOYER NAME","sFinalEmpName ==== " + sFinalEmpName);
					if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("")) {
						if (!sFinalEmpName.equalsIgnoreCase("")) {
							formObject.setValue(EMP_STATUS, "Employed");
						}
					}
				} else if (controlName.equalsIgnoreCase(ED_ANNUAL_INC)) {
					String sAnnualSalary = formObject.getValue(ED_ANNUAL_INC).toString();
					try {
						int iAnnualSal = Integer.parseInt(sAnnualSalary);
						int finalsalary = iAnnualSal / 12;
						logInfo("ED_ANNUAL_INC"," finalsalary  ::"  + finalsalary );
						formObject.setValue(ED_MONTHLY_INCM, finalsalary + "");
						formObject.setValue(ED_SAL_AED,(String) formObject.getValue(ED_ANNUAL_INC));
					} catch (Exception ex) {
						logInfo("executeServerEvent", "Exception in Event- "+ eventType + "control name- " + controlName
								+ ": ");
						logError("executeServerEvent", ex);
					}
				}
				else if (controlName.equalsIgnoreCase(NOM_REQ)) {
					if (controlName.equalsIgnoreCase(NOM_REQ)&& ((String) formObject.getValue(NOM_REQ)).equalsIgnoreCase("Yes")) {
						formObject.setStyle(EXISTING_NOM_PRSN, ENABLE, TRUE);
						formObject.setValue(EXISTING_NOM_PRSN, "");
						Frame48_CPD_ENable(); // func definition present in this
						// code
						manageNomineeDetails(controlName);// func definition
						// present in this
						// code
					} else {
						formObject.setValue(EXISTING_NOM_PRSN, "");
						formObject.setStyle(EXISTING_NOM_PRSN, ENABLE, FALSE);
						Frame48_CPD_Disable();// func definition present in this
						// code
						manageNomineeDetails(controlName);// func definition
						// present in this
						// code
					}
				}
				else if (controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)) {
					// pass selected rows in data from js , js code written
					// below this code
					if (controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)&& formObject.getValue(EXISTING_NOM_PRSN)
							.toString().equalsIgnoreCase("Yes")) {
						int iSelectedRow = Integer.parseInt(data);
						String cust_Id = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
						String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
						List<List<String>> result;
						String query = "SELECT NOM_NAME,NOM_PO_BOX,NOM_ADD1||NOM_ADD2,NOM_LAND,NOM_CITY,NOM_STATE,NOM_OTHERS,NOM_CNTRY,NOM_FAX,NOM_ZIP,NOM_EMAIL,NOM_PREF_LANG,NOM_PHONE,'',cust_id,nom_id,NOM_MOB from USR_0_NOM_MAST where cust_id ='"
								+ cust_Id + "'";
						result = formObject.getDataFromDB(query);
						String listviewValues = result.get(0).get(0) + "##"+ result.get(0).get(1) + "##"
								+ result.get(0).get(2) + "##"+ result.get(0).get(3) + "##"+ result.get(0).get(4) + "##"
								+ result.get(0).get(5) + "##"+ result.get(0).get(6) + "##"
								+ result.get(0).get(7) + "##"+ result.get(0).get(8) + "##"+ result.get(0).get(9) + "##"
								+ result.get(0).get(10);
						LoadListViewWithHardCodeValues(EXISTING_NOM_PRSN, "",listviewValues);
						if (getGridCount(DELIVERY_PREFERENCE) == 0) {
							sendMessageValuesList(EXISTING_NOM_PRSN,"There Is No Existing Nominee For This Customer.");
							formObject.setValue(EXISTING_NOM_PRSN, "");
							return null;
						}
						Frame48_CPD_ENable();
					} else {
						formObject.clearCombo(DELIVERY_PREFERENCE);
					}
				} else if (controlName.equalsIgnoreCase(MANUAL_STATE)) {  //NEW CHECKS ADDED HERE
					formObject.setValue(MANUAL_CITY,formObject.getValue(MANUAL_STATE).toString());
					formObject.setValue(CP_CITY,formObject.getValue(MANUAL_STATE).toString());
					formObject.setValue(RA_CITY,formObject.getValue(MANUAL_STATE).toString());
					formObject.setValue(PERM_STATE,formObject.getValue(MANUAL_STATE).toString());
				} else if (controlName.equalsIgnoreCase(CRS_CERTI_YES)) {
					if (TRUE.equalsIgnoreCase(formObject.getValue(CRS_CERTI_NO).toString())) {
						formObject.setValue(CRS_CERTI_NO, "false");
						formObject.setValue(CRS_CLASSIFICATION,"UPDATED-DOCUMENTED");
					}
				} else if (controlName.equalsIgnoreCase(CRS_CERTI_NO)) {
					if (TRUE.equalsIgnoreCase(formObject.getValue(CRS_CERTI_YES).toString())) {
						formObject.setValue(CRS_CERTI_YES, "false");
						formObject.setValue(CRS_CLASSIFICATION,"UPDATED-UNDOCUMENTED");
					}
				} else if (controlName.equalsIgnoreCase(IDS_CB_VVIP)) {   // Pending for check
					logInfo("IDS_CB_VVIP","Inside Condition");
					if (formObject.getValue(IDS_CB_VVIP).toString().equalsIgnoreCase(TRUE)) {
						logInfo("IDS_CB_VVIP"," Inside IF Block");
						formObject.setStyle(GI_YEARS_IN_UAE, DISABLE, FALSE);
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY, "false");
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,"false");
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR, "false");
						formObject.setValue(RA_CB_REAL_STATE_BROKR, "false");
						formObject.setValue(RA_CB_USD_AUTO_DEALER, "false");
					} else {
						logInfo("IDS_CB_VVIP","Else Block");
						formObject.setValue(GI_YEARS_IN_UAE, "");
						formObject.setStyle(GI_YEARS_IN_UAE, DISABLE, TRUE);
					}
					if (formObject.getValue(IDS_CB_VVIP).toString().equalsIgnoreCase(TRUE)) {
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
				} 
				else if (controlName.equalsIgnoreCase(VISA_STATUS)) {
					if (!formObject.getValue(VISA_STATUS).toString().equalsIgnoreCase("Residency Visa")) {
						formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
						formObject.setValue(DRP_RESEIDA, "");
					} else {
						formObject.setStyle(DRP_RESEIDA, DISABLE, FALSE);
					}
				}  
				else if (controlName.equalsIgnoreCase(IS_CAT_BENEFIT_OTHER)) {    //pending for check
					logInfo("IS_CAT_BENEFIT_OTHER","INSIDE IS_CAT_BENEFIT_OTHER Block");
					if (formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase(TRUE)) {
						logInfo("CLICK CONDITION IS_CAT_BENEFIT_OTHER","IF Block");
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE,FALSE);
						formObject.setValue(IS_SALARY_TRANSFER_CAT_CHANGE,"false");
						formObject.setValue(IS_MORTAGAGE_CAT_CHANGE,"false");
						formObject.setValue(IS_INSURANCE_CAT_CHANGE, "false");
						formObject.setValue(IS_TRB_CAT_CHANGE, "false");
						formObject.setValue(IS_OTHERS_CAT_CHANGE, "false");
						formObject.setValue(IS_VVIP, "false");
						if (formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Privilege")
								|| formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Emirati")) {
							formObject.setValue(IS_PREVILEGE_TP_CAT_CHANGE,"false");
							formObject.setValue(IS_ENTERTAINMENT_CAT_CHANGE, "false");
							formObject.setValue(IS_SHOPPING_CAT_CHANGE,"false");
							formObject.setValue(IS_TRAVEL_CAT_CHANGE,"false");
							formObject.setValue(IS_TRAVEL_CAT_CHANGE,"false");
						} else if (formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Emirati Excellency")
								|| formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Excellency")
								|| formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Private Clients")) {
							formObject.setValue(IS_EXCELLENCY_TP_CAT_CHANGE, "false");
						}
					} else {
						logInfo("IS_CAT_BENEFIT_OTHER","Else Block");
						formObject.setValue(CAT_BENEFIT_OTHER, "");
						formObject.setStyle(CAT_BENEFIT_OTHER, DISABLE,TRUE);
					}
				} 
				else if (controlName.equalsIgnoreCase(RESIDENCY_STATUS)     //Pending for check
						&&  sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {   
					if (formObject.getValue(RESIDENCY_STATUS).toString().equalsIgnoreCase("No")) {
						enableControls(new String[] { RA_RSN_BNKNG_UAE});
					} else {
						disableControls(new String[] { RA_RSN_BNKNG_UAE});
					} 
				} else if (controlName.equalsIgnoreCase(DEL_CNTRY)) {       //pending for check
					String sState = formObject.getValue(DEL_STATE).toString();
					logInfo("DEL_CNTRY","sState----" + sState);
					if (data.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						formObject.clearCombo(DEL_STATE);
						formObject.addItemInCombo(DEL_STATE, UAESTATES,UAESTATES);
						formObject.setValue(DEL_STATE, sState);
					} else if (!data.equalsIgnoreCase("")) {
						formObject.clearCombo(DEL_STATE);
						formObject.addItemInCombo(DEL_STATE,OTHERTHENUAESTATES, OTHERTHENUAESTATES);
						formObject.setValue(DEL_STATE, sState);
					}
					if (formObject.getValue(DEL_STATE) == null) {
						formObject.setValue(DEL_STATE, "");
					}
				}  
				else if (controlName.equalsIgnoreCase(FCR_EMPLYR_NAME)     //pending for check
						|| controlName.equalsIgnoreCase(EIDA_EMPLYR_NAME)
						|| controlName.equalsIgnoreCase(MANUAL_EMPLYR_NAME)) {
					String sIsFCREmpName = formObject.getValue(CHECKBOX_EMP_NAME_FCR).toString();
					String sIsEIDAEmpName = formObject.getValue(CHECKBOX_EMP_NAME_EIDA).toString();
					String sIsManualEmpName = formObject.getValue(CHECKBOX_EMP_NAME_MANUAL).toString();
					String sFCREmpName = formObject.getValue(FCR_EMPLYR_NAME).toString();
					String sEIDAEmpName = formObject.getValue(EIDA_EMPLYR_NAME).toString();
					String sManualEmpName = formObject.getValue(MANUAL_EMPLYR_NAME).toString();
					String sFinalEmpName = getFinalData(sIsFCREmpName, sIsEIDAEmpName,sIsManualEmpName,
							sFCREmpName,sEIDAEmpName, sManualEmpName);
					logInfo("","sFinalEmpName ==== " + sFinalEmpName);
					if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("")) {
						if (!sFinalEmpName.equalsIgnoreCase("")) {
							formObject.setValue(EMP_STATUS,"Employed");
						}
					}
				} else if (controlName.equalsIgnoreCase(FCR_RESIDENT)    //pending for check
						|| controlName.equalsIgnoreCase(EIDA_RESIDENT)
						|| controlName.equalsIgnoreCase(MANUAL_RESIDENT)
						|| controlName.equalsIgnoreCase(FCR_NATIONALITY)
						|| controlName.equalsIgnoreCase(EIDA_NATIONALITY)
						|| controlName.equalsIgnoreCase(MANUAL_NATIONALITY)) {
					logInfo("","Setting Visa Status");
					manageResidencyAndVisaStatus(RESIDENCY_STATUS,VISA_STATUS);
					if (!formObject.getValue(SELECTED_ROW_INDEX).toString().equalsIgnoreCase("")) {
						// NGRepeater objChkRepeater =
						// formObject.getNGRepeater("REPEAT_FRAME");
						int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 0;
						String sAccRelation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
						logInfo("","sAccRelation------"+ sAccRelation);
						manageChangeinFATCAFields(controlName,sAccRelation);
						autoSetFatca(controlName);
					}					
				} else if (controlName.equalsIgnoreCase(INDICIACOMBO)
						|| controlName.equalsIgnoreCase(FAT_SSN)
						|| controlName.equalsIgnoreCase(FAT_CUST_CLASSIFICATION)
						|| controlName.equalsIgnoreCase(DATEPICKERCUST)
						|| controlName.equalsIgnoreCase(DATEPICKERW8)
						|| controlName.equalsIgnoreCase(COMBODOC)) {
					formObject.setValue("Change_In_FATCA_3way_Inputs", "Yes");
					formObject.setValue(FATCAMAIN, "Yes");
					disableControls(new String[] {BTN_VALIDATEFATCA});
				}
				else if (controlName.equalsIgnoreCase(MANUAL_CNTRY)) {     //Pending for check
					logInfo(""," in cntry manual if click ");
					String sIsFCRCountry = formObject.getValue(CHECKBOX_CNTRY_OF_CORR_FCR).toString();
					String sIsEIDACountry = formObject.getValue(CHECKBOX_CNTRY_OF_CORR_EIDA).toString();
					String sIsManualCountry = formObject.getValue(CHECKBOX_CNTRY_OF_CORR_MANUAL).toString();
					String sFCRCountry = formObject.getValue(FCR_CNTRY).toString();
					String sEIDACountry = formObject.getValue(EIDA_CNTRY).toString();
					String sManualCountry = formObject.getValue(MANUAL_CNTRY).toString();
					String sFinalCountry = getFinalData(sIsFCRCountry,sIsEIDACountry, sIsManualCountry,
							sFCRCountry,sEIDACountry, sManualCountry);
					if (sFinalCountry.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						String state = formObject.getValue(MANUAL_STATE).toString();
						formObject.clearCombo(MANUAL_STATE);
						formObject.addItemInCombo(MANUAL_STATE, UAESTATES,UAESTATES);
						formObject.setValue(MANUAL_STATE, state);
					} else if (!sFinalCountry.equalsIgnoreCase("")) {
						String state = formObject.getValue(MANUAL_STATE).toString();
						formObject.clearCombo(MANUAL_STATE);
						formObject.addItemInCombo(MANUAL_STATE,OTHERTHENUAESTATES, OTHERTHENUAESTATES);
						formObject.setValue(MANUAL_STATE, state);
					}
				}
				else if (controlName.equalsIgnoreCase(NEW_RM_CODE_CAT_CHANGE)) {  //pending for check
					String rm_code = formObject.getValue(NEW_RM_CODE_CAT_CHANGE).toString();
					String myQuery = "select rm_name from usr_0_rm where rm_code='"+ rm_code + "'";
					List<List<String>> sOutput = formObject.getDataFromDB(myQuery);
					String rm_name = sOutput.get(0).get(0);
					formObject.setValue(NEW_RM_NAME_CAT_CHANGE, rm_name);
				}else if (controlName.equalsIgnoreCase(NEW_RM_NAME_CAT_CHANGE)) {
					String sRMName = formObject.getValue(NEW_RM_NAME_CAT_CHANGE).toString().replaceAll("'", "''");
					String myQuery = "SELECT RM_CODE FROM USR_0_RM WHERE RM_NAME='"+ sRMName + "'";
					List<List<String>> sOutput = formObject.getDataFromDB(myQuery);
					formObject.setValue(NEW_RM_CODE_CAT_CHANGE, sOutput.get(0).get(0));
				}
				else if (controlName.equalsIgnoreCase(NEW_DEL_MODE)) {
					if (formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Internal Courier")) {
						formObject.clearCombo(CHANNEL_NAME);
						formObject.addItemInCombo(CHANNEL_NAME,"Aramex");
					} else if (formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Branch")) {
						addDataInComboFromQuery("Select HOME_BRANCH from usr_0_home_branch order by 1",CHANNEL_NAME);
					} else if (formObject.getValue(NEW_DEL_MODE).toString().equalsIgnoreCase("Excellency")) {
						addDataInComboFromQuery("select sourcing_center from usr_0_sourcing_center where"
								+ " sourcing_channel ='Excellency' order by 1",CHANNEL_NAME);
					} else {
						formObject.clearCombo(CHANNEL_NAME);
					}
				} else if (controlName.equalsIgnoreCase(DEL_STATE)) {
					if (formObject.getValue(DEL_STATE).toString().equalsIgnoreCase("Others")) {
						formObject.setValue(DEL_STATE_OTHER, "");
						enableControls(new String[] {DEL_STATE_OTHER});
					} else {
						formObject.setValue(DEL_STATE_OTHER, "");
						disableControls(new String[] {DEL_STATE_OTHER});
					}
				}
				else if (controlName.equalsIgnoreCase(NOM_REQ)) {
					if (controlName.equalsIgnoreCase(NOM_REQ)&& data.equalsIgnoreCase("Yes")) {
						enableControls(new String[] { EXISTING_NOM_PRSN });
						formObject.setValue(EXISTING_NOM_PRSN, "");
						Frame48_CPD_ENable();
						manageNomineeDetails(data);
					} else {
						formObject.setValue(EXISTING_NOM_PRSN, "");
						disableControls(new String[] { EXISTING_NOM_PRSN });
						Frame48_CPD_Disable();
						manageNomineeDetails(data);
					}
				}
				else if (controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)) {
					if (controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)) {
						if (controlName.equalsIgnoreCase(EXISTING_NOM_PRSN)
								&& formObject.getValue(EXISTING_NOM_PRSN).toString().equalsIgnoreCase("Yes")) {
							int iSelectedRow = Integer.parseInt(data);
							String cust_Id = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 2);
							String sBankRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 7);
							List<List<String>> result;
							String query = "SELECT NOM_NAME,NOM_PO_BOX,NOM_ADD1||NOM_ADD2,NOM_LAND,NOM_CITY,"
									+ "NOM_STATE,NOM_OTHERS,NOM_CNTRY,NOM_FAX,NOM_ZIP,NOM_EMAIL,NOM_PREF_LANG,"
									+ "NOM_PHONE,'',cust_id,nom_id,NOM_MOB from USR_0_NOM_MAST where cust_id ='"
									+ cust_Id + "'";
							result = formObject.getDataFromDB(query);
							String listviewValues = result.get(0).get(0)+ "##"+ result.get(0).get(1)+ "##"
									+ result.get(0).get(2)+ "##" + result.get(0).get(3)+ "##"
									+ result.get(0).get(4)+ "##" + result.get(0).get(5)+ "##"
									+ result.get(0).get(6)+ "##" + result.get(0).get(7)+ "##"
									+ result.get(0).get(8)+ "##" + result.get(0).get(9)+ "##"
									+ result.get(0).get(10);
							LoadListViewWithHardCodeValues(EXISTING_NOM_PRSN, "",listviewValues);// constant for
							// collective
							// columns needs to
							// be created
							// LoadListView(sql, 17,
							// DELIVERY_PREFERENCE,
							// "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16");

							if (getGridCount(DELIVERY_PREFERENCE) == 0) {
								sendMessageValuesList(EXISTING_NOM_PRSN,"There Is No Existing Nominee For This Customer.");
								formObject.setValue(EXISTING_NOM_PRSN,"");
								return null;
							}
							Frame48_CPD_ENable();
						} else {
							formObject.clearCombo(DELIVERY_PREFERENCE);
						}
					}
				}					
				else if (controlName.equalsIgnoreCase(CUST_SEGMENT1)) {
					int fieldToFocus = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) ;
					String bnk_relation = formObject.getTableCellValue(ACC_RELATION,fieldToFocus, 7);
					if (bnk_relation.equalsIgnoreCase("New")) {
						String segment = formObject.getValue(CUST_SEGMENT1).toString();
						logInfo("CUST_SEGMENT1","cust_seg selected-----"+ formObject.getValue(CUST_SEGMENT1));
						if (segment.equalsIgnoreCase("Signatory")) {
							logInfo("CUST_SEGMENT1","signatory selected----");
							if (!(formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9).toString().equalsIgnoreCase("AUS") 
									|| formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9).toString().equalsIgnoreCase("POA"))) {
								logInfo("","acc-rel-----"+ formObject.getTableCellValue(ACC_RELATION,fieldToFocus,9));
								sendMessageValuesList("","Signatory not allowed. Please select another type.");
								formObject.setValue(CUST_SEGMENT1,"");
							}
						}
					}
				}

				else if ((controlName.equalsIgnoreCase(FCR_COUNTRYBIRTH))
						|| (controlName.equalsIgnoreCase(EIDA_COUNTRYBIRTH))
						|| (controlName.equalsIgnoreCase(MANUAL_COUNTRYBIRTH))) {
					logInfo("COUNTRYBIRTH", "Inside change event of COUNTRYBIRTH ");
					String sFinalCountryOfBirth = getFinalDataComparison(
							CHECKBOX_COB_FCR, CHECKBOX_COB_EIDA, CHECKBOX_COB_MANUAL,
							FCR_COUNTRYBIRTH, EIDA_COUNTRYBIRTH,
							MANUAL_COUNTRYBIRTH); 
					logInfo("COUNTRYBIRTH", sFinalCountryOfBirth+ "sFinalCountryOfBirth inside country click");
					if (sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES")) {
						logInfo("COUNTRYBIRTH", sFinalCountryOfBirth + "united insoide inside country click firse");
						formObject.setStyle(COMBODOC, ENABLE, TRUE);
						formObject.clearCombo(COMBODOC);
						formObject.addItemInCombo(COMBODOC,"");
						formObject.addItemInCombo(COMBODOC,"W8BEN");
						formObject.addItemInCombo(COMBODOC,"W9");
					} else {		
						logInfo("COUNTRYBIRTH",sFinalCountryOfBirth+"else inside country click firse");						  
						formObject.setStyle(COMBODOC,ENABLE,"true");  
						formObject.clearCombo(COMBODOC);
						formObject.setValue(FAT_CUST_CLASSIFICATION, "");
						formObject.addItemInCombo(COMBODOC,"");
						formObject.addItemInCombo(COMBODOC,"NA");
						formObject.addItemInCombo(COMBODOC,"W8BEN");
						formObject.addItemInCombo(COMBODOC,"W9");
					}
				}

				else if (controlName.equalsIgnoreCase(DEL_MODE_YES)) {
					logInfo("","In side AO_DEL_MODE_YES");
					if (formObject.getValue(DEL_MODE_YES).toString().equalsIgnoreCase(TRUE)) {
						logInfo("","In side AO_DEL_MODE_YES IF ");
						disableControls(new String[] { NEW_DEL_MODE,CHANNEL_NAME });
					} else {
						logInfo("","In side AO_DEL_MODE_YES Else");
						enableControls(new String[] { NEW_DEL_MODE,CHANNEL_NAME });
					}
				}else if (controlName.equalsIgnoreCase(DEL_MODE_NO)) {
					logInfo("","In side AO_DEL_MODE_NO");
					if (formObject.getValue(DEL_MODE_NO).toString().equalsIgnoreCase(TRUE)) {
						logInfo("","In side AO_DEL_MODE_NO IF ");
						disableControls(new String[] { NEW_DEL_MODE,CHANNEL_NAME });
					} else {
						logInfo("","In side AO_DEL_MODE_NO Else");
						enableControls(new String[] { NEW_DEL_MODE,CHANNEL_NAME });
					}
				}

				else if (controlName.equalsIgnoreCase(DEL_MODE_YES)) { //from here
					toggleCheckbox_2(controlName, DEL_MODE_NO);
				}else if (controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)) {
					toggleCheckbox_2(controlName, DEL_EXCELLENCY_CNTR);
				}else if (controlName.equalsIgnoreCase(DEL_MODE_NO)) {
					logInfo("DEL_MODE_NO","DEL_MODE_NO");
					toggleCheckbox_2(controlName, DEL_MODE_YES);
				}else if (controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)) {
					toggleCheckbox_2(controlName, DEL_BRNCH_COURIER);
				}else if (controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)) {
					logInfo("DEL_MODE_NO",DEL_BRNCH_COURIER);
					if (controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)&& data.equalsIgnoreCase(TRUE)) {
						logInfo("","data" + data);
						disableControls(new String[] { EXCELLENCY_CENTER });
						enableControls(new String[] { DEL_BRNCH_NAME });
					} else if (controlName.equalsIgnoreCase(DEL_BRNCH_COURIER)&& data.equalsIgnoreCase("false")) {
						logInfo("","data" + data);
						disableControls(new String[] { DEL_BRNCH_NAME });
						enableControls(new String[] { EXCELLENCY_CENTER });
					}
				}else if (controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)) {
					if (controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)&& data.equalsIgnoreCase(TRUE)) {
						logInfo("","data" + data);
						disableControls(new String[] { DEL_BRNCH_NAME });
						enableControls(new String[] { EXCELLENCY_CENTER });
					} else if (controlName.equalsIgnoreCase(DEL_EXCELLENCY_CNTR)&& data.equalsIgnoreCase("false")) {
						logInfo("","data" + data);
						disableControls(new String[] { EXCELLENCY_CENTER });
						enableControls(new String[] { DEL_BRNCH_NAME });
					}
				}else if(controlName.equalsIgnoreCase(PER_INC_TAX_CON_1) ){
					System.out.println("Country 1 Selected........Sourav");
					return getReturnMessage(checkforDuplicateCountries(PER_INC_TAX_CON_1));
				}else if (controlName.equalsIgnoreCase(PER_INC_TAX_CON_2) ){
					System.out.println("Country 2 Selected........Sourav");
					return getReturnMessage(checkforDuplicateCountries(PER_INC_TAX_CON_2));
				}else if (controlName.equalsIgnoreCase(PER_INC_TAX_CON_3)){
					System.out.println("Country 3 Selected........Sourav");
					return getReturnMessage(checkforDuplicateCountries(PER_INC_TAX_CON_3));	   
				}else if (controlName.equalsIgnoreCase(SUB_PERSONAL_TAX)) {
					logInfo("change event","SUB_PERSONAL_TAX sarted");
					 if (formObject.getValue(SUB_PERSONAL_TAX).toString().equalsIgnoreCase("Yes")) {
					formObject.setStyle(PER_INC_TAX_CON_1, DISABLE, FALSE);
				}else if( formObject.getValue(SUB_PERSONAL_TAX).toString().equalsIgnoreCase("No")) {
					formObject.setStyle(PER_INC_TAX_CON_1, DISABLE, FALSE);
					formObject.setStyle(PER_INC_TAX_CON_2, DISABLE, FALSE);
					formObject.setStyle(PER_INC_TAX_CON_3, DISABLE, FALSE);
					formObject.setValue(PER_INC_TAX_CON_1,"");
					formObject.setValue(PER_INC_TAX_CON_1,"");
					formObject.setValue(PER_INC_TAX_CON_1,"");
				}
				}else if(controlName.equalsIgnoreCase("LISTVIEW_PUR_ACC_RELATION")){
					logInfo("LISTVIEW_PUR_ACC_RELATION", "inside LISTVIEW_PUR_ACC_RELATION: ");
					accountPurpose();
		      	}else if(controlName.equalsIgnoreCase(ADDITIONAL_SOURCES_INCOME_AED)){
					logInfo("ADDITIONAL_SOURCES_INCOME_AED", "inside ADDITIONAL_SOURCES_INCOME_AED: ");
					additionalSource();
					//Added by Shivanshu CRS TIN COUNTRY
				}else if(controlName.equalsIgnoreCase(CRS_TAX_COUNTRY)){
					logInfo("CRS_TAX_COUNTRY", "inside CRS_TAX_COUNTRY : ");
					disableFieldCRSCountry();
				}
				//END OF CHANGE EVENT


				/*if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO))
				{
					fillManualDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillFCRDataInBelowFields(controlName,formObject.getValue(controlName).toString());
					fillEIDADataInBelowFields(controlName,formObject.getValue(controlName).toString());
				}*/
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) { //START OF LOSTFOCUS EVENT
				if (controlName.equalsIgnoreCase(ED_MONTHLY_INCM)) {
					String mnthsalary = formObject.getValue(ED_MONTHLY_INCM).toString();
					try {
						if (mnthsalary.equalsIgnoreCase("")) {
							formObject.setValue(ED_ANNUAL_INC, "");
							formObject.setValue(ED_SAL_AED, "");
						}
						long mnthslry = Long.parseLong(mnthsalary);
						long finalsalary = mnthslry * 12;
						formObject.setValue(ED_ANNUAL_INC, finalsalary + "");
						formObject.setValue(ED_CB_SAL_AED, TRUE);
						if (((String) formObject.getValue(ED_CB_SAL_AED)).equalsIgnoreCase(TRUE)) {
							formObject.setValue(ED_SAL_AED, finalsalary + "");
						}
					} catch (Exception ex) {
						logInfo("executeServerEvent", "Exception in Event- "+ eventType + "control name- " + controlName
								+ ": ");
						logError("executeServerEvent", ex);
					}
				}
				if (controlName.equalsIgnoreCase(ED_ANNUAL_INC)) {
					String sAnnualSalary = formObject.getValue(ED_ANNUAL_INC).toString();
					try {
						int iAnnualSal = Integer.parseInt(sAnnualSalary);
						int finalsalary = iAnnualSal / 12;
						logInfo("ED_ANNUAL_INC","finalsalary ::" + finalsalary);
						formObject.setValue(ED_MONTHLY_INCM, finalsalary + "");
						formObject.setValue(ED_SAL_AED,(String) formObject.getValue(ED_ANNUAL_INC));
					} catch (Exception ex) {
						logInfo("executeServerEvent", "Exception in Event- "+ eventType + "control name- " + controlName+ ": ");
						logError("executeServerEvent", ex);
					}
				}
			} else if("handlingJSPData".equalsIgnoreCase(eventType)) {
				if(controlName.equalsIgnoreCase(BTN_PRD_SEARCH)) {
					searchProductList("USR_0_PRODUCT_OFFERED", data);
				} 
			}
		} /*catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- "+ controlName + ": ", e);
		} finally {
			logInfo("executeServerEvent", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		} */
		catch (Exception e) {
			logInfo("executeServerEvent", "Exception in Event- " + eventType + "control name- " +controlName+ ": ");
			logError("executeServerEvent", e);
		} finally {
			logInfo("executeServerEvent", sendMessageList.toString());
			if(!sendMessageList.isEmpty()) {
				logInfo("executeServerEvent", "Inside finally Condition");
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		}
		return retMsg;
	}
	// GET HEAD OF FAMILY CID
	
	/*private String getFamilyDetailsInputXML() {
		String familyGroupId = "";
		String senderID= "WMS";
		String sCust_ID = "";
		sCust_ID = formObject.getValue(CID_HOF).toString();
		List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL");
		String sSeqNo=sOutput.get(0).get(0);

		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input>"
				+ "<Option>WebService</Option>"
				+ "<Calltype>WS-2.0</Calltype>"
				+ "<InnerCallType>FamilyGroupDetails</InnerCallType>"
				+ "<SessionId>" + sSessionId  + "</SessionId>"
				+ "<operationName>fetchFamilyGroupDtls_Oper</operationName>"
				+ "<SENDERID>" +senderID+ "</SENDERID>"
				+ "<REF_NO>" +sSeqNo+ "</REF_NO>"
				+ "<familyGroupId>" +familyGroupId+ "</familyGroupId>"                        
				+ "<customerId>" +sCust_ID+ "</customerId>"
				+ "<EngineName>" + sEngineName + "</EngineName>"
				+"</APWebService_Input>";
		logInfo("getFamilyDetailsInputXML","Input xml of getFamilyDetailsInputXML: " + sInputXML);
		return sInputXML;
	}
	
	private String getCustomerInfoInputXML() {
		String sCust_ID = "";
		String senderID= "WMS";
		
		sCust_ID = formObject.getValue(CID_HOF).toString();


		List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL");
		String sSeqNo=sOutput.get(0).get(0);

		String sInputXML = "<APWebService_Input>"
				+ "<Option>WebService</Option>"
				+ "<Calltype>CustomerInformation</Calltype>"
				+ "<CUST_ID>"+ sCust_ID+ "</CUST_ID>"
				+ "<SENDERID>" +senderID+ "</SENDERID>"
				+ "<REF_NO>" +sSeqNo+ "</REF_NO>"
				+ "<SessionId>" + sSessionId  + "</SessionId>"
				+ "<EngineName>" + sEngineName + "</EngineName>"
				+"</APWebService_Input>";
		logInfo("getCustomerInfoInputXML","Input xml of getCustomerInfoInputXML : " + sInputXML);
		return sInputXML;

	}
	
private String getRMSupportStructureInputXML() {
		
		String senderID= "RCRM";
		String sRMStaffId = "";
		String sRMCode = "";

		sRMCode = formObject.getValue(RM_HOF).toString();


		List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL");
		String sSeqNo=sOutput.get(0).get(0);

		String sInputXML = "<?xml version=\"1.0\"?><APWebService_Input>"
				+ "<Option>WebService</Option>"
				+ "<Calltype>WS-2.0</Calltype>"
				+ "<InnerCallType>FamilyGroupDetails</InnerCallType>"
				+ "<SessionId>" + sSessionId  + "</SessionId>"
				+ "<operationName>fetchRMSupportStructure_Oper</operationName>"
				+ "<SENDERID>" +senderID+ "</SENDERID>"
				+ "<REF_NO>" +sSeqNo+ "</REF_NO>"
				+ "<RMStaffId>" +sRMStaffId+ "</RMStaffId>"
				+ "<RMCode>" +sRMCode+ "</RMCode>"
				+ "<EngineName>" + sEngineName + "</EngineName>"
				+"</APWebService_Input>";
		logInfo("getRMSupportStructureDetailsInputXML","Input xml of getRMSupportStructureDetailsInputXML : " + sInputXML);
		return sInputXML;

	}
*/
	public void setProductCurrencyComboLoadDisable1(String sProdCode) {
		try {
			logInfo("setProductCurrencyComboLoadDisable1",
					"inside setProductCurrencyComboLoadDisable1111 called after search product jsp....");
			String sQuery = "";
			String sAccClass = formObject.getValue(ACC_HOME_BRANCH).toString();
			logInfo("setProductCurrencyComboLoadDisable1", "sAccClass...." + sAccClass);
			if (sActivityName.equalsIgnoreCase(ACTIVITY_CPD_MAKER)) {
				sQuery = "SELECT DISTINCT b.CURRENCY_SHORT_NAME as CURRENCYCODE from USR_0_PRODUCT_OFFERED_CPD B,USR_0_PRODUCT_MASTER A,USR_0_HOME_BRANCH c where a.product_code= b.product_code and UPPER(b.WI_NAME) = UPPER('"
						+ sWorkitemId
						+ "') and c.HOME_BRANCH='"
						+ sAccClass
						+ "' and b.product_code='" + sProdCode + "' order by 1";
				logInfo("setProductCurrencyComboLoadDisable1",
						"sQuery else ..." + sQuery);
			} else {
				sQuery = "SELECT DISTINCT b.CURRENCY_SHORT_NAME as CURRENCYCODE from USR_0_PRODUCT_OFFERED B,USR_0_PRODUCT_MASTER A,USR_0_HOME_BRANCH c where a.product_code= b.product_code and UPPER(b.WI_NAME) = UPPER('"
						+ sWorkitemId
						+ "') and c.HOME_BRANCH='"
						+ sAccClass
						+ "' and b.product_code='" + sProdCode + "' order by 1";
				logInfo("setProductCurrencyComboLoadDisable1",
						"sQuery else ..." + sQuery);
			}

			List<List<String>> list = formObject.getDataFromDB(sQuery);
			logInfo("setProductCurrencyComboLoadDisable1", "sOutput..." + list);
			if (list!=null && list.size() > 0) {
				int iTotalRetrived =  list.size();//Integer.parseInt(list.get(0).get(0));
				logInfo("setProductCurrencyComboLoadDisable1",
						"iTotalRetrived...." + iTotalRetrived);

				formObject.getTableCellValue(PRODUCT_QUEUE, 1, 3);
			}
		} catch (Exception e) {
			logError("setProductCurrencyComboLoadDisable1", e);
		}
	}

	public boolean emailEtihadVal(String[] rtnArry) {
		String rtnData[] = null;
		String sAllProduct = "";
		for (int i = 0; i < rtnArry.length; i++) {
			rtnData = rtnArry[i].split("~");
			sAllProduct = sAllProduct + "'" + rtnData[0] + "',";
		}
		sAllProduct = sAllProduct.substring(0, sAllProduct.length() - 1);
		String sQuery = "SELECT COUNT(SUB_PRODUCT_TYPE) as SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE IN ("
				+ sAllProduct + ") AND UPPER(SUB_PRODUCT_TYPE) ='ETIHAD'";
		List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
		if (!sOutput.get(0).get(0).equals("0")) {
			sendMessageValuesList("",
					"Primary Customer's Email Id Is Mendatory For Etihad Product.");
			return false;
		}
		return true;
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

	public void onTabClickQDE(String data) {
		try {
			logInfo("onTabClick", "INSIDE: "+data);
			String[] selectedSheetInfo = data.split(",");
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			logInfo("onTabClick","selectedSheetIndex:"+selectedSheetIndex);
			logInfo("onTabClick", "activity: "+sActivityName+", tabCaption: , selectedSheetIndex: "+selectedSheetIndex);
			onSaveTabClickQDE(selectedSheetIndex);
			if(selectedSheetIndex==0) {
				logInfo("onTabClick","selectedSheetIndex:"+selectedSheetIndex);
				frame81_CPD_Disable();
				populatePreAssesmentDetails(); 
			} else if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 
					|| selectedSheetIndex == 4) {
				logInfo("onTabClick","Selected sheet index :" + selectedSheetIndex); 
				int iSelectedRow = Integer.parseInt( formObject.getValue(SELECTED_ROW_INDEX).toString());
				logInfo("onTabClick","iSelectedRow         :"+iSelectedRow);
				String sBankRelation= formObject.getTableCellValue(ACC_RELATION,iSelectedRow,7);	
				int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String name=formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer, 1);
				String dob=formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer, 5);
				String cust_id=formObject.getTableCellValue(ACC_RELATION,iProcessedCustomer, 2);
				resetRekey();
				if(!name.trim().equalsIgnoreCase("")) {
					formObject.setValue(TXT_CUSTOMERNAME, name);
				} if(!cust_id.trim().equalsIgnoreCase("")) {
					formObject.setValue(TXT_CUSTOMERID, cust_id);
				} if(!dob.trim().equalsIgnoreCase("")) {
					formObject.setValue(TXT_DOB, dob);
				} if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
					logInfo("FATCA :: ","FATCA");
					frameFatcaCpdDisable();
					clearRiskDataQDE(); 
					populateComparisonFields();
					populateCRSData();					//to be tested MOKSH
					PopulatePrivateClientQuestions();  
					populateRiskDataQDE();
//					populatePreAssesmentDetails(); //Jamshed
					populateHiddenDataQDE();
					populatePepAssesmentDetails(); //AO DCRA
					disableControls(new String[]{EMPLYR_TYPE1,SEC_CI,DRP_RESEIDA});
					frame18_QDE_Disable();
					//makeEnableRekeyFields();
					// disable tab fb
					fbValidation();
					populatePOANationality(); //Jamshed
				}
				loadDedupeSearchData(sWorkitemId);
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) { 
//					populatePreAssesmentDetails(); //Jamshed
					if(((String) formObject.getValue(FATCAMINI)).equalsIgnoreCase("")
							&& sBankRelation.equalsIgnoreCase("New")){   
						logInfo("onTabClick","Checking QDE");
						enableControls(new String[]{BTN_VALIDATE});
					}
					if(sBankRelation.equalsIgnoreCase("Existing")) {
						disableControls(new String[]{SEC_INT_DETAIL});
					} else {
						enableControls(new String[]{SEC_INT_DETAIL});
					}
					if(selectedSheetIndex == 3) {
						if(formObject.getValue(CRS_CERTIFICATION_OBTAINED).toString().equalsIgnoreCase("Yes")) {
							formObject.setValue(CRS_CLASSIFICATION,"UPDATED-DOCUMENTED");
						} 
					}

					logInfo("","In EMP Status12----" + data);
					if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Employed")) {
						String [] fieldName = {RA_CB_GEN_TRDNG_CMPNY,RA_CB_PRECIOUS_STONE_DEALER,RA_CB_BULLN_COMMDTY_BROKR,
								RA_CB_REAL_STATE_BROKR,RA_CB_USD_AUTO_DEALER};
						String [] fieldValue = {FALSE,FALSE,FALSE,FALSE,FALSE};
						setMultipleFieldValues(fieldName, fieldValue);
						/*formObject.setStyle(RA_CB_GEN_TRDNG_CMPNY,DISABLE, TRUE);
						formObject.setStyle(RA_CB_PRECIOUS_STONE_DEALER,DISABLE, TRUE);
						formObject.setStyle(RA_CB_BULLN_COMMDTY_BROKR,DISABLE, TRUE);
						formObject.setStyle(RA_CB_REAL_STATE_BROKR,DISABLE, TRUE);
						formObject.setStyle(RA_CB_USD_AUTO_DEALER,DISABLE, TRUE);*/
						disableControls(BUSINESS_NATURE_SECTION);
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
						formObject.setValue(RA_CB_GEN_TRDNG_CMPNY, "false");
						formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER,"false");
						formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,"false");
						formObject.setValue(RA_CB_REAL_STATE_BROKR, "false");
						formObject.setValue(RA_CB_USD_AUTO_DEALER, "false");
						formObject.setValue(FINANCIAL_BROKERS,"false");
						formObject.setValue(NOTARY_PUBLIC,"false");
						formObject.setValue(SOCIAL_MEDIA_INFLUNCER,"false");
						formObject.setValue(RA_CB_OTHERS,"false");
						formObject.setValue(RA_CB_OTHERS_FLD,"");
						enableControls(new String[] { EMPLYR_TYPE1, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
					} else if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Self Employed")) {
						formObject.setValue(RA_IS_CUST_WRKNG_UAE, "");
						formObject.setValue(RA_IS_CUST_WRKNG_NON_UAE, "");
						formObject.setValue(EMPLYR_TYPE1, "");
						//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
						disableControls(new String[] { EMPLYR_TYPE1, 
								RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
						enableControls(BUSINESS_NATURE_SECTION);
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
					} else {
						//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE });
						disableControls(new String[] { EMPLYR_TYPE1, RA_IS_CUST_WRKNG_NON_UAE,DEALS_IN_WMD,RA_IS_CUST_WRKNG_UAE,RA_CB_OTHERS_FLD });
						disableControls(BUSINESS_NATURE_SECTION);
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
						formObject.setValue(RA_CB_OTHERS,"false");
						formObject.setValue(RA_CB_OTHERS_FLD,"");
						//if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Retired")) {
						if (formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Salaried")) {	
						    //disableControls(BUSINESS_NATURE_SECTION );
						    //formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
							enableControls(new String[] { RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
							formObject.setStyle(RA_CB_GEN_TRDNG_CMPNY,DISABLE, TRUE);
							formObject.setStyle(RA_CB_PRECIOUS_STONE_DEALER,DISABLE, TRUE);
							formObject.setStyle(RA_CB_BULLN_COMMDTY_BROKR,DISABLE, TRUE);
							formObject.setStyle(RA_CB_REAL_STATE_BROKR,DISABLE, TRUE);
							formObject.setStyle(RA_CB_USD_AUTO_DEALER,DISABLE, TRUE);
							formObject.setStyle(FINANCIAL_BROKERS,DISABLE, TRUE);
							formObject.setStyle(NOTARY_PUBLIC,DISABLE, TRUE);
							formObject.setStyle(SOCIAL_MEDIA_INFLUNCER,DISABLE, TRUE);
							formObject.setStyle(RA_CB_OTHERS,DISABLE, TRUE);
							formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, TRUE);
							formObject.setValue(RA_CB_GEN_TRDNG_CMPNY,"false");
							formObject.setValue(RA_CB_PRECIOUS_STONE_DEALER, "false");
							formObject.setValue(RA_CB_BULLN_COMMDTY_BROKR,"false");
							formObject.setValue(RA_CB_REAL_STATE_BROKR,"false");
							formObject.setValue(RA_CB_USD_AUTO_DEALER,"false");
							formObject.setValue(FINANCIAL_BROKERS,"false");
							formObject.setValue(NOTARY_PUBLIC,"false");
							formObject.setValue(SOCIAL_MEDIA_INFLUNCER,"false");
							formObject.setValue(RA_CB_OTHERS,"false");
							formObject.setValue(RA_CB_OTHERS_FLD,"");
						}
						/*else{
							formObject.setStyle(RA_CB_GEN_TRDNG_CMPNY,DISABLE, FALSE);
							formObject.setStyle(RA_CB_PRECIOUS_STONE_DEALER,DISABLE, FALSE);
							formObject.setStyle(RA_CB_BULLN_COMMDTY_BROKR,DISABLE, FALSE);
							formObject.setStyle(RA_CB_REAL_STATE_BROKR,DISABLE, FALSE);
							formObject.setStyle(RA_CB_USD_AUTO_DEALER,DISABLE, FALSE);
							formObject.setStyle(RA_CB_OTHERS,DISABLE, FALSE);
							formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, FALSE);
						}*/
					}
					if (!formObject.getValue(EMP_STATUS).toString().equalsIgnoreCase("Self Employed")) {
						disableControls(new String[] { IDS_CB_VVIP,GI_YEARS_IN_UAE });
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					} else {
						enableControls(new String[] { IDS_CB_VVIP,GI_YEARS_IN_UAE });
						formObject.setValue(IDS_CB_VVIP, "false");
						formObject.setValue(GI_YEARS_IN_UAE, "");
					}
					if(formObject.getValue(RA_CB_OTHERS).toString().equalsIgnoreCase(TRUE)){
						formObject.setStyle(RA_CB_OTHERS_FLD,DISABLE, FALSE);
					}
					if(selectedSheetIndex == 2) {
						setEIDAInPersonalAndKYCTab();
						verifyChequeBook();
					}
				}
				if(!sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
					//formObject.NGFocus("PREFIX_MANUAL");
				}
				//enableControls(new String[]{MANUAL_EIDANO});
				//to be removed
				//saveComparisonData();
				//saveCustNeedAnalysisData();
				//saveHiddenDataQDE();
				//saveClientQuestionsData();  
				//saveCRSDetails(); 
//				logInfo("onTabClick","BEfore Kyc save");
//				saveKYCInfoRetailQDE();  //added by Jamshed
//				savePreAssessmentDetails();   //shahbaz
				
				// KYC TAB
			}
			//SANCTION SCREENING TAB (TRSD TAB)
			else if(selectedSheetIndex==5) {
				logInfo("onTabClick","Inside: TAB 5");
				logInfo("onTabClick","selectedSheetIndex:"+selectedSheetIndex);
				frame23_CPD_Disable();
				populateTRSD();
				loadCustData();
				if( formObject.getValue(TRSD_APPROVEDRESULT).toString().equalsIgnoreCase("Approved"))
					logInfo("onTabClick","Checking QDE");
				//formObject.setValue("Combo2", "Eligible");
				//else 
				//formObject.setValue("Combo2", "Not Eligible");
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
					populateScreeningDataCRO();
					logInfo("onTabClick","TRSD_CHECK disable");
					disableControls(new String[]{TRSD_CHECK});
				}
				disableControls(new String[]{"Calculate"});
				logInfo("onTabClick","END OF : TAB 5");
			}
			//APPLICATION ASSESMENT DATA
			else if(selectedSheetIndex==6) {	
				logInfo("onTabClick","Inside: TAB 6");
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
					loadApplicationAssessmentData();
					String sQuery= "";
					if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
						String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME "
								+ "= '"+sWorkitemId+"'";		
						List<List<String>> recordList = formObject.getDataFromDB(sQuery3);
						logInfo("",sQuery3);
						loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
					}
					if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
						String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED "
								+ "WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
						List<List<String>> recordList = formObject.getDataFromDB(sQuery4);
						logInfo("",sQuery4);
						loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
					}
					if(getGridCount(FAC_LVW_CRO) == 0) {
						String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE "
								+ "WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
						List<List<String>> recordList = formObject.getDataFromDB(sQuery5);
						logInfo("",sQuery5);
						loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
					}
				}
				logInfo("onTabClick","END OF: TAB 6");
			}
			//DELIVERY PREFRENCES
			else if(selectedSheetIndex==8) {
				logInfo("onTabClick","Inside: TAB 8");
				logInfo("onTabClick","Selected sheet index :" + selectedSheetIndex);
				int iCount = getListCount(EXISTING_NOM_PRSN);
				if(iCount==0 || iCount==1)
				{
					/*List<List<String>> combodata = 	formObject.getDataFromDB("Select COUNTRY from usr_0_country_mast "
							+ "order by 1");
					formObject.addItemInCombo("AO_DEL_CNTRY", combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("AO_DEL_CNTRY", combodata.get(0).get(1), combodata.get(0).get(1));
					combodata.clear();

					combodata = formObject.getDataFromDB("Select delivery_mode from usr_0_delivery_mode order by 1");
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(1), combodata.get(0).get(1));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(2), combodata.get(0).get(2));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(3), combodata.get(0).get(3));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(4), combodata.get(0).get(4));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(5), combodata.get(0).get(5));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(6), combodata.get(0).get(6));
					formObject.addItemInCombo("DEL_DELIVERY_MODE", combodata.get(0).get(7), combodata.get(0).get(7));
					combodata.clear();

					combodata = formObject.getDataFromDB("Select delivery_mode from usr_0_delivery_mode order by 1");
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(1), combodata.get(0).get(1));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(2), combodata.get(0).get(2));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(3), combodata.get(0).get(3));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(4), combodata.get(0).get(4));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(5), combodata.get(0).get(5));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(6), combodata.get(0).get(6));
					formObject.addItemInCombo("new_del_mode", combodata.get(0).get(7), combodata.get(0).get(7));
					combodata.clear();

					combodata = formObject.getDataFromDB("Select YES_NO from USR_0_YES_NO order by 1");
					formObject.addItemInCombo("AO_NOM_REQ", combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("AO_NOM_REQ", combodata.get(0).get(1), combodata.get(0).get(1));
					combodata.clear();

					combodata = formObject.getDataFromDB("Select to_char(YES_NO) from USR_0_YES_NO");
					formObject.addItemInCombo("AO_EXISTING_NOM_PRSN",combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("AO_EXISTING_NOM_PRSN",combodata.get(0).get(1), combodata.get(0).get(1));
					combodata.clear();

					combodata = formObject.getDataFromDB("Select Branch from usr_0_brnch_of_instant_issue order by 1");
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(1), combodata.get(0).get(1));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(2), combodata.get(0).get(2));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(3), combodata.get(0).get(3));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(4), combodata.get(0).get(4));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(5), combodata.get(0).get(5));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(6), combodata.get(0).get(6));
					formObject.addItemInCombo("BRNCH_OF_INSTANT_ISSUE", combodata.get(0).get(7), combodata.get(0).get(7));
					combodata.clear();

					combodata = formObject.getDataFromDB("Select STATE from usr_0_state_mast order by 1");
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(0), combodata.get(0).get(0));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(1), combodata.get(0).get(1));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(2), combodata.get(0).get(2));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(3), combodata.get(0).get(3));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(4), combodata.get(0).get(4));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(5), combodata.get(0).get(5));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(6), combodata.get(0).get(6));
					formObject.addItemInCombo("AO_DEL_STATE", combodata.get(0).get(7), combodata.get(0).get(7));
					combodata.clear();
					 */
					//,1,"AO_DEL_CNTRY");
					//	addItemsDropDown("AO_DEL_CNTRY","AO_DEL_CNTRY");
					//	formObject.getNGDataFromDataSource("Select delivery_mode from usr_0_delivery_mode order by 1",1,"AO_DEL_DELIVERY_MODE");
					//	addItemsDropDown("AO_DEL_DELIVERY_MODE","AO_DEL_DELIVERY_MODE");
					//	formObject.getNGDataFromDataSource("Select delivery_mode from usr_0_delivery_mode order by 1",1,"new_del_mode");
					//	addItemsDropDown("new_del_mode","new_del_mode"); [[0,0,1,1,0]]
					//	formObject.getNGDataFromDataSource("Select YES_NO from USR_0_YES_NO order by 1",1,"AO_NOM_REQ");
					//	addItemsDropDown("AO_NOM_REQ","AO_NOM_REQ");
					//	//formObject.getNGDataFromDataSource("Select to_char(YES_NO) from USR_0_YES_NO",1,"AO_EXISTING_NOM_PRSN");
					//	addItemsDropDown("AO_NOM_REQ","AO_EXISTING_NOM_PRSN");
					//	formObject.getNGDataFromDataSource("Select Branch from usr_0_brnch_of_instant_issue order by 1",1,"AO_BRNCH_OF_INSTANT_ISSUE");
					//	addItemsDropDown("AO_BRNCH_OF_INSTANT_ISSUE","AO_BRNCH_OF_INSTANT_ISSUE");
					//	formObject.getNGDataFromDataSource("Select STATE from usr_0_state_mast order by 1",1,"AO_DEL_STATE");
					//	addItemsDropDown("AO_DEL_STATE","AO_DEL_STATE");
				}
				setMailingAddInToDel();
				logInfo("onTabClick","END OF: TAB 8");
			} 
			//ACCOUNT INFO
			else if(selectedSheetIndex == 7){
				//Adding items in combo
				logInfo("onTabClick","Inside Tab 7");
				PopulatePrivateClientQuestions();    
				CalculateAccTitle();
				LockCreatedAccountRows();
				formObject.setStyle("product_search", DISABLE, FALSE);
				formObject.setStyle("Fetch Balance", DISABLE, FALSE);
				formObject.setStyle("udf_addRow", DISABLE, FALSE);  //add_product..ACC_TITLE
				formObject.setStyle("add_product", DISABLE, FALSE); 
				formObject.setStyle("ACC_TITLE", DISABLE, FALSE); 
				logInfo("onTabClick","Selected sheet index :" + selectedSheetIndex);
				
			} 
			//DECISION TAB
			//else if(selectedSheetIndex == 11) {
			else if(selectedSheetIndex == 12) {
				//logInfo("onTabClick","Inside: TAB 11");
				logInfo("onTabClick","Inside: TAB 12");
				logInfo("onTabClick","Selected sheet index :" + selectedSheetIndex);
				String count= null;			
				String sQuery="select count(WI_NAME) as count from USR_0_RISK_ASSESSMENT_DATA where "
						+ "approval_req='Yes' and wi_name='"+sWorkitemId+"'";
				List<List<String>> output=formObject.getDataFromDB(sQuery);
				if(output.size()>0 && output!=null){
					count=output.get(0).get(0);
				}
				try {
					if(Integer.parseInt(count)>0) {
						formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, "true");
					} else {
						formObject.setValue(IS_COMPLIANCE_RISK_ASSESS, "false");
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				sQuery="SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_CUST_TXN WHERE "
						+ "(BANK_DEC != 'Approved' AND BANK_DEC IS NOT NULL) AND WI_NAME ='"+sWorkitemId+"'";
				output=formObject.getDataFromDB(sQuery);
				if(output.size()>0 && output!=null){
					count=output.get(0).get(0);
				}
				if(Integer.parseInt(count)>0) {
					formObject.setValue(IS_COMPLIANCE_NAME_SCR, "true");
				} else {
					formObject.setValue(IS_COMPLIANCE_NAME_SCR, "false");
				}
				if(formObject.getValue(MOBILE_CHANGE_FLAG).toString().equalsIgnoreCase("True")) {
					formObject.setValue(IS_CALL_BACK_REQ, "true");
				} else {
					formObject.setValue(IS_CALL_BACK_REQ, "false");
				}
				disableControls(new String[]{IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ,IS_CALL_BACK_REQ});					
				if (getGridCount("DECISION_HISTORY") == 0) {
					String sQuery1 = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,"
							+ "CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM'"
							+ ")CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME,"
							+ " WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST "
							+ "WHERE WI_NAME = '"
							+ sWorkitemId + "') ORDER BY A";
					List recordList = formObject.getDataFromDB(sQuery1);
					loadListView(recordList,
							"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS","DECISION_HISTORY");
				}
				/*if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)){
					String decision_query = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='QDE_Account_Info' order by to_char(WS_DECISION)";
					logInfo("decision_query",decision_query);
					List<List<String>> decision_data = formObject.getDataFromDB(decision_query);
					logInfo("Submit ",Integer.toString(decision_data.get(0).size()));
					for(int i =0; i<decision_data.get(0).size(); i++){
						logInfo("decision_query",decision_data.get(0).get(i));
						formObject.addItemInCombo(CRO_DEC, decision_data.get(0).get(i), decision_data.get(0).get(i));
					}
					addDataInComboFromQuery(decision_query,CRO_DEC);
				}*/
				logInfo("onTabClick","END OF : TAB 11");
			} else if(selectedSheetIndex == 9) {
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
					   logInfo("krishna","END OF : TAB 11");
					   loadNewCust();//added by krishna
					   String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(sReqType.equalsIgnoreCase("New Account with Category Change")
					   || sReqType.equalsIgnoreCase("Category Change Only") 
					   || sReqType.equalsIgnoreCase("Downgrade")
							|| sReqType.equalsIgnoreCase("Upgrade")) {//Added by krishna
						String sCID= formObject.getTableCellValue(ACC_RELATION, 1, 2);
						formObject.saveDataInDB("SELECT MAX(CATEGORY_CHANGE_DATE) AS CATEGORY_CHANGE_DATE FROM "
								+ "USR_0_CUST_TXN WHERE CUST_ID ='"+sCID+"'");
						String sQuery ="SELECT CUST_SEG,RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME "
								+ "='"+sWorkitemId+"' AND CUST_SNO=(SELECT SELECTED_ROW_INDEX FROM EXT_AO WHERE "
										+ " WI_NAME = '"+sWorkitemId+"') + 1";
						logInfo("onTabClick","kp"+sQuery);
						List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
						logInfo("onTabClick","sOutput----"+sOutput);
						if(sOutput!=null && sOutput.size()>0){
							String Query ="select private_client from ext_ao where wi_name='"+sWorkitemId+"'";
							logInfo("On load QDE_Cust_Info","pri_query= "+ Query);
							String  privateClient ="";
							List<List<String>> List =formObject.getDataFromDB(Query); 
							if (List != null && List.size() > 0) {
						    privateClient =List.get(0).get(0); 
							logInfo("On load QDE_Cust_Info","private_clnt_flg= "+ privateClient);
							}
							if (!(sReqType.equalsIgnoreCase("New Account With Category Change") && privateClient.equalsIgnoreCase("Y"))) {
							formObject.setValue(OLD_CUST_SEGMENT,sOutput.get(0).get(0));
							logInfo("OLD_CUST_SEGMENT","OLD_CUST_SEGMENT= "+OLD_CUST_SEGMENT);
							}
							formObject.addItemInCombo(OLD_RM_NAME_CAT_CHANGE, sOutput.get(0).get(1));
							formObject.addItemInCombo(OLD_RM_CODE_CAT_CHANGE, sOutput.get(0).get(2));
							formObject.setValue(OLD_RM_NAME_CAT_CHANGE,sOutput.get(0).get(1));
							formObject.setValue(OLD_RM_CODE_CAT_CHANGE,sOutput.get(0).get(2));
						}
						formObject.setValue(MAKER_DEPARMENT_CAT_CHANGE,formObject.getValue(MAKER_DEPT).toString());
						formObject.setValue(MAKER_NAME_CAT_CHANGE,formObject.getValue(MAKER_NAME).toString());
						formObject.setValue(MAKER_CODE_CAT_CHANGE,formObject.getValue(MAKER_CODE).toString());
					}
					if(sReqType.equalsIgnoreCase("New Account with Category Change")){
						formObject.setValue(SOURCE_NAME_CAT_CHANGE,formObject.getValue(SOURCE_NAME).toString());
						formObject.setValue(SOURCE_CODE_CAT_CHANGE,formObject.getValue(SOURCE_CODE).toString());
						disableControls(new String[]{SOURCE_NAME_CAT_CHANGE,SOURCE_CODE_CAT_CHANGE,SRC_NAME_BTN});
						
					}if(sReqType.equalsIgnoreCase("Downgrade")){//Added by krishna
						formObject.setStyle(OLD_RM_CODE_CAT_CHANGE, DISABLE, TRUE);
						formObject.setStyle(OLD_RM_NAME_CAT_CHANGE, DISABLE, TRUE);
						
					}if(formObject.getValue(IS_CAT_BENEFIT_OTHER).toString().equalsIgnoreCase("false")) {
						disableControls(new String[]{CAT_BENEFIT_OTHER,IS_CAT_BENEFIT_OTHER});
						if(!formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
							enableControls(new String[]{IS_CAT_BENEFIT_OTHER});
							logInfo("onTabClick","if block me gaya");
							manageCategoryChangeSection();							
						} else {
							disableControls(new String[]{IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
									IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE,IS_VVIP,
									IS_PREVILEGE_TP_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
									IS_SPORT_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_EXCELLENCY_TP_CAT_CHANGE});
						}
					} else {
						enableControls(new String[]{CAT_BENEFIT_OTHER,IS_CAT_BENEFIT_OTHER});
						manageCategoryChangeSection();
					}
					if(formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("")) {
						formObject.clearCombo(PROMO_CODE_CAT_CHANGE);
//						formObject.addItemInCombo(PROMO_CODE_CAT_CHANGE,"");				
						formObject.clearCombo(EXCELLENCY_CENTER_CC);
//						formObject.addItemInCombo(EXCELLENCY_CENTER_CC,"");
					}
				}
			} else if(selectedSheetIndex == 10) {
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO))  {
					fbValidation();	//family  banking tab change check
				} 
			} else if(selectedSheetIndex == 11) {
				fbValidation();
				clearFBData();	//Clearing FB tab on category change
			}
		} catch (Exception e) {
			logInfo("onTabClick","tab details: "+data);
			logError("onTabClick", e);
		}
	}
	/*
	public boolean ValidateFATCADetails(String sType)
    {
		try
		{
			if(!ValidateComparisonDataCombo(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,
					CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,EIDA_CNTRY,MANUAL_CNTRY,CA020,
					"Mandatory","Country of Correspondence Address")){
				return false;
			}
			if(!ValidateComparisonDataCombo(CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_EIDA,CHECKBOX_COUNTRY_RES_MANUAL,
					FCR_RESIDENT,EIDA_RESIDENT,MANUAL_RESIDENT,CA0155,"Mandatory","Residential Address Country"))
			{
				return false;
			}

			if(!ValidateComparisonDataComboForDot(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
					FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH,CA0178,"Mandatory","Country of Birth"))
			{
				return false;
			} 
			if ((null==formObject.getValue(MANUAL_COUNTRYBIRTH).toString()
					||formObject.getValue(MANUAL_COUNTRYBIRTH).toString().equalsIgnoreCase("")))
			{
				sendMessageValuesList("","Please enter city of birth");
				//formObject.NGFocus("cityBirth_manual");
				return false;
			}
			if(!ValidateComparisonData(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,
					FCR_PH,EIDA_PH,MANUAL_PH,CA057,"Optional","Residence Phone number"))
			{
				return false;
			}

			if(!ValidatePhoneNo(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,
					FCR_PH,EIDA_PH,MANUAL_PH,CA0127))
			{
				return false;
			}
			if(flag_phone_start.equalsIgnoreCase("true")){
				if(!ValidateMobileNoStart("CHECK25","ph_MANUAL","Mandatory",CA0161,"Residence Number"))
				{
					return false;
				}
			}
			if(!ValidateComparisonData(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
					FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA059,"Mandatory","Mobile number"))
			{
				return false;
			}

			if(!ValidateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
					FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,"CA0126"))
			{
				return false;
			}
			if(flag_phone_start.equalsIgnoreCase("true")){
				if(!ValidateMobileNoStart(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE,"Mandatory",CA0161,"Mobile Number"))
				{
					return false;
				}
			}
			if(!ValidateComparisonDataCombo(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,
					CHECKBOX_NATIONALITY_MANUAL,FCR_NATIONALITY,EIDA_NATIONALITY,MANUAL_NATIONALITY,CA013,
					"Mandatory","Nationality"))
			{
				return false;
			}

			if(formObject.getValue(FAT_US_PERSON).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(FAT_US_PERSON,CA031);
				//formObject.NGFocus(FAT_US_PERSON);
				return false;
			}

			if(formObject.getValue(FAT_LIABLE_TO_PAY_TAX).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(FAT_LIABLE_TO_PAY_TAX,CA032);
				//formObject.NGFocus(FAT_LIABLE_TO_PAY_TAX);
				return false;
			}


			if(!ValidateCounrtyBirth(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
					FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH,CA033,""))
			{
				logInfo("ValidateFATCADetails","INSIDE DDE_CUSTACC ValidateCounrtyBirth");

				return false;
			}
			if(formObject.getValue(POACOMBO).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(POACOMBO,"Please select POA Holder");
				//formObject.NGFocus(POAcombo);
				return false;
			}
			if(sType.equalsIgnoreCase("Main"))
			{
				if(formObject.getValue(FAT_CUST_CLASSIFICATION).toString().equalsIgnoreCase("US PERSON"))
				{
					if(formObject.getValue(FAT_SSN).toString().equalsIgnoreCase(""))
					{
						sendMessageValuesList(FAT_SSN,"Please Fill SSN, FATCA Classification is US PERSON");
						//formObject.NGFocus(FAT_SSN);
						return false;
					}
					if(formObject.getValue(FAT_SSN).toString().length() < 9)
					{
						sendMessageValuesList(FAT_SSN,"SSN number length should be equal to 9");
						//formObject.NGFocus(FAT_SSN);
						return false;
					}
				}
				if(formObject.getValue(INDICIACOMBO).toString().equalsIgnoreCase(""))
				{
					sendMessageValuesList(INDICIACOMBO,"Please select US Indicia Found");
					//formObject.NGFocus(INDICIACOMBO);
					return false;
				}
				if(formObject.getValue(COMBODOC).toString().equalsIgnoreCase(""))
				{
					String sFinalCountryOfBirth = getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,
							CHECKBOX_COB_MANUAL,FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH);
					logInfo("ValidateFATCADetails","sFinalCountryOfBirth"+sFinalCountryOfBirth);
					String areYouUS = formObject.getValue(FAT_US_PERSON).toString();
					logInfo("",areYouUS+"areYouUS");
					if(sFinalCountryOfBirth.equalsIgnoreCase("UNITED STATES") && areYouUS.equalsIgnoreCase("YES") )
					{
						sendMessageValuesList(COMBODOC,"Please select Documents to be Collected");
						//formObject.NGFocus(COMBODOC);
						return false;
					}
				}
				if(formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN"))
				{
					if(formObject.getValue(DATEPICKERW8).toString().equalsIgnoreCase(""))
					{
						sendMessageValuesList(DATEPICKERW8,"Please select W8 Signup Date");
						//formObject.NGFocus(DATEPICKERW8);
						return false;
					}
					if(!validateW8SignUpDate("DatePickerW8","W8 Signup"))
					{
						//formObject.NGFocus(DATEPICKERW8);
						return false;
					}
				}
				if(formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W9"))
				{
					if(formObject.getValue(FAT_SSN).toString().equalsIgnoreCase(""))
					{
						sendMessageValuesList(FAT_SSN,"Please Fill SSN");
						//formObject.NGFocus("combo47");
						return false;
					}
					if(formObject.getValue(FAT_SSN).toString().length() < 9)
					{
						sendMessageValuesList(FAT_SSN,"SSN number length should be equal to 9");
						//formObject.NGFocus("combo47");
						return false;
					}
				}
			}

			String sInputXML = FetchFATCADetails(sType);
			logInfo("ValidateFATCADetails","sInputXML----"+sInputXML);
			String sOutput = socket.connectToSocket(sInputXML);
			logInfo("ValidateFATCADetails","sOutput----"+sOutput);
			String sReturnCode = getTagValues(sOutput,"returnCode");							
			logInfo("ValidateFATCADetails","Changes done for Fatca");
			if(sReturnCode.equalsIgnoreCase("0")||sReturnCode.equalsIgnoreCase("2"))
			{
				if(sType.equalsIgnoreCase("Mini"))
				{
					formObject.setValue(US_INDICIA_MDM, getTagValues(sOutput,"returnValue"));
					logInfo("ValidateFATCADetails","FAT_CUST_CLASSIFICATION set to non us person"+
					getTagValues(sOutput,"returnValue"));
					if(!getTagValues(sOutput,"returnValue").equalsIgnoreCase(""))
					{
						if(formObject.getValue(US_INDICIA_MDM).toString().equalsIgnoreCase("No"))
						{
							formObject.setValue(INDICIACOMBO,getTagValues(sOutput,"returnValue"));
							formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
							logInfo("ValidateFATCADetails","FAT_CUST_CLASSIFICATION set to non us person");
						}
						else
						{
							formObject.setValue(INDICIACOMBO,"YES");
							formObject.setValue(FAT_CUST_CLASSIFICATION,"US PERSON");
							logInfo("ValidateFATCADetails","FAT_CUST_CLASSIFICATION set to us person");
						}
					}

					//Start edit by Shivani -- Fatca 04082020
				String countryOfBirth=getFinalDataComparison("CHECK117","CHECK119","CHECK122",
						"countryBirth_fcr","countryBirth_eida","countryBirth_manual");
				String resCountry = getFinalData(formObject.getValue("CHECK7").toString()
							,formObject.getValue("CHECK51").toString(),
							formObject.getValue("CHECK27").toString(),
							formObject.getValue("per_cntry_fcr").toString()
							,formObject.getValue("per_cntry_eida").toString()
							,formObject.getValue("per_cntry_Manual").toString());
				String mailingCountry = getFinalData(formObject.getValue("CHECK29").toString(),
							formObject.getValue("CHECK52").toString(),
							formObject.getValue("CHECK28").toString(),
							formObject.getValue("cntry_fcr").toString(),
							formObject.getValue("cntry_EIDA").toString(),
							formObject.getValue("CNTRY_MANUAL").toString());

					if((countryOfBirth.equalsIgnoreCase("United States"))
							|| (resCountry.equalsIgnoreCase("United States")) 
							|| (mailingCountry.equalsIgnoreCase("United States")))
					{
						formObject.setValue(INDICIACOMBO,"YES");
						formObject.setValue(FAT_CUST_CLASSIFICATION,"US PERSON");
						logInfo("","Country is US :: FAT_CUST_CLASSIFICATION set to us person");
					}

					if(formObject.getValue(COMBODOC).toString().equalsIgnoreCase("W8BEN"))
					{
						formObject.setValue(FAT_CUST_CLASSIFICATION,"NON-US PERSON");
					}
					sendMessageValuesList("","Mini FATCA Validation done Successfully");
					formObject.setValue("FATCAMINI","No");
					formObject.setStyle("validate",ENABLE,FALSE);
					formObject.setStyle("validateFATCA",ENABLE,TRUE);
					return true;
				}
				else
				{
					if(getTagValues(sOutput,"returnValue").equalsIgnoreCase("Yes"))
					{				
						formObject.setValue(DATEPICKERCUST, getTagValues(sOutput,"customerFATCAClsfctnDate"));
						formObject.setValue(CHANGE_IN_FATCA_3WAY_INPUTS,"No");
						formObject.setValue(FATCAMAIN,"No");
						sendMessageValuesList("","Main FATCA Validation done Successfully");
						formObject.setStyle("validateFATCA",ENABLE,FALSE);
						return true;
					}
					else
					{
						sendMessageValuesList("","Mismatch in Customer US Classification.Please check");
						formObject.setStyle("validateFATCA",ENABLE,TRUE);
						return false;
					}
				}
			}
			else
			{
				String sMessege =getTagValue(sOutput,"errorDescription");
				if(sMessege.equalsIgnoreCase("")){
					sendMessageValuesList("","Unable to Validate FATCA Details");
					return false;
				}
				else{
					sendMessageValuesList("",sMessege);
					return false;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public Boolean ValidateComparisonDataComboForDot(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageID,String sStatus,String sFieldName)
	{
		if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
		{
			if(formObject.getValue(sFCRDataControl).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(sFCRDataControl, sMessageID);		 
				//formObject.NGFocus(sFCRDataControl);
				return false;
			}
		}
		else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
		{
			if(formObject.getValue(sEIDADataControl).toString().equalsIgnoreCase(""))
			{

				sendMessageValuesList(sEIDADataControl,sMessageID);
				//formObject.NGFocus(sEIDADataControl);
				return false;
			}
		}		
		else if(sStatus.equalsIgnoreCase("Mandatory"))
		{
			if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sManualDataControl).toString().equalsIgnoreCase("")
					|| formObject.getValue(sManualDataControl).toString().equalsIgnoreCase("")
					|| formObject.getValue(sManualDataControl).toString().equalsIgnoreCase(".")){		 
					sendMessageValuesList(sManualCBControl,sMessageID);
					//formObject.NGFocus(sManualDataControl);
					return false;
				}
			}
			else 
			{						 
				sendMessageValuesList(sManualCBControl,"Please select the manual checkbox for "+sFieldName);
				//formObject.NGFocus(sManualCBControl);
				return false;
			}
		}			
		else
		{
			if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sManualDataControl).toString().equalsIgnoreCase("") 
						|| formObject.getValue(sManualDataControl).toString().equalsIgnoreCase("."))
				{
					sendMessageValuesList(sManualDataControl,sMessageID);
					//formObject.NGFocus(sManualDataControl);
					return false;
				}
			}
		}
		return true;
	}
	//end
	public String FetchFATCADetails(String sType) 
	{
		XMLParser generalDataParser = new XMLParser();
		//String sGeneralData = formObject.getWFGeneralData();

		//String sEngineName = generalDataParser.getValueOf("EngineName");                  
		//String sSessionId  = generalDataParser.getValueOf("DMSSessionId"); 

		List<List<String>> sOutput = formObject.getDataFromDB("SELECT SEQ_WEBSERVICE.nextval as ID from DUAL");
		String sNationality = getFinalDataComparison(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,
				CHECKBOX_NATIONALITY_MANUAL,FCR_NATIONALITY,EIDA_NATIONALITY,MANUAL_NATIONALITY).trim();
		String sResidenceCountry = getFinalDataComparison(CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_EIDA,
				CHECKBOX_COUNTRY_RES_MANUAL,FCR_RESIDENT,EIDA_RESIDENT,MANUAL_RESIDENT).trim();
		String sCountry = getFinalDataComparison(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,
				CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,EIDA_CNTRY,MANUAL_CNTRY).trim();
		String sPhone = getFinalDataComparison(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,
				CHECKBOX_TELE_RES_MANUAL,FCR_PH,EIDA_PH,MANUAL_PH).trim();
		String sMobile = getFinalDataComparison(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,
				CHECKBOX_TELE_MOB_MANUAL,FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE).trim();
		String sCallType ="";
		String sFinalCountryOfBirth1 =getFinalDataComparison(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,
				CHECKBOX_COB_MANUAL,FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH); 
		logInfo("",sFinalCountryOfBirth1+"combodoc sFinalCountryOfBirth1");
		if(sType.equalsIgnoreCase("Mini"))
		{
			sCallType= "ValidateFATCAMini";
		}
		else
		{
			sCallType= "ValidateFATCAMain";
		}
		String sInputXML= "<?xml version=\"1.0\"?>" +
				"<APWebService_Input>" +
				"<Option>WebService</Option>" +
				"<Calltype>"+sCallType+"</Calltype>" +
				"<Customer>" +	
				"<customerSegment>"+formObject.getValue(SEARCH_PASS_NO)+"</customerSegment>\n"+
				"<serviceType>O</serviceType>\n"+
				"<product>L</product>\n"+
				"<nationality>"+sNationality+"</nationality>\n"+
				"<residentialAddressCountry>"+sResidenceCountry+"</residentialAddressCountry>\n"+
				"<mailingAddressCountry>"+sCountry+"</mailingAddressCountry>\n"+
				"<telephoneResidence>"+sPhone+"</telephoneResidence>\n"+
				"<telephoneOffice></telephoneOffice>\n"+
				"<telephoneMobile>"+sMobile+"</telephoneMobile>\n"+
				"<USpassportholder>"+formObject.getValue(FAT_US_PERSON)+"</USpassportholder>\n"+
				"<USTaxLiable>"+formObject.getValue(FAT_LIABLE_TO_PAY_TAX)+"</USTaxLiable>\n"+
				"<countryOfBirth>"+sFinalCountryOfBirth1+"</countryOfBirth>\n"+
				"<standingInstructionCountry>NO</standingInstructionCountry>\n"+
				"<POAHolderCountry>"+formObject.getValue(POACOMBO)+"</POAHolderCountry>\n";


		// formObject.getValue("CNTRY_OF_BIRTH")
		if(sType.equalsIgnoreCase("Main"))
		{	
			String sClassificationDate = formObject.getValue(DATEPICKERCUST).toString();

			if(sClassificationDate.equalsIgnoreCase(""))
			{
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
				sClassificationDate = simpledateformat.format(calendar.getTime());
			}

			sInputXML += "<USIndiaciaFound>"+formObject.getValue(INDICIACOMBO).toString()+"</USIndiaciaFound>\n"+
					"<documentCollected>"+formObject.getValue(COMBODOC).toString()+"</documentCollected>\n"+
					"<W8_Sign_Date>"+formObject.getValue(DATEPICKERW8).toString()+"</W8_Sign_Date>\n"+
					"<TINorSSN>"+formObject.getValue(FAT_SSN).toString()+"</TINorSSN>\n"+
					"<customerFATCAClsfctn>"+formObject.getValue(FAT_CUST_CLASSIFICATION).toString()+"</customerFATCAClsfctn>\n"+
					"<customerFATCAClsfctnDate>"+sClassificationDate+"</customerFATCAClsfctnDate>\n";
		}

		sInputXML += "</Customer>"+
				"<EngineName>" + sEngineName + "</EngineName>" +
				"<SessionId>" + sSessionId + "</SessionId>" +
				"<REF_NO>"+sOutput.get(0).get(0)+"</REF_NO>"+
				"<WiName>" + sWorkitemId + "</WiName>" +
				"</APWebService_Input>";
		return sInputXML;
	}



public Boolean ValidatePhoneNo(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,
		String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageID){

	if(flag_phone){
		if("United Arab Emirates".equalsIgnoreCase(checkCountry()))
		{
			sMessageID="CR0004";

			if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sFCRDataControl).toString().length()!=11)
				{
					//int reply=JOptionPane.showConfirmDialog(null,sMessageID, null, JOptionPane.YES_NO_OPTION);
					//if(reply==JOptionPane.NO_OPTION){
						//formObject.NGFocus(sFCRDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;//BUG ID: 1061554
					//	}
						return false;
					}
				}
			}
			else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sEIDADataControl).toString().length()!=11)
				{
					//int reply=JOptionPane.showConfirmDialog(null,sMessageID , null, JOptionPane.YES_NO_OPTION);
					//if(reply==JOptionPane.NO_OPTION){
						//formObject.NGFocus(sEIDADataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;//BUG ID: 1061554
						//}
						return false;}
				}
			}
			else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sManualDataControl).toString().length()!=11)
				{
					//int reply=JOptionPane.showConfirmDialog(null,sMessageID , null, JOptionPane.YES_NO_OPTION);
					//if(reply==JOptionPane.NO_OPTION){
						//formObject.NGFocus(sManualDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;//BUG ID: 1061554
						//}
						return false;}
				}
			}

		}
		else{
			if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True")){
				if(formObject.getValue(sFCRDataControl).toString().length() <10)
				{
					//Need to dicuss for this
					//int reply=JOptionPane.showConfirmDialog(null,sMessageID , null, JOptionPane.YES_NO_OPTION);
					//if(reply==JOptionPane.NO_OPTION){
						//formObject.NGFocus(sFCRDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;//BUG ID: 1061554
						//}
						return false;}
				}
			}
			else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sEIDADataControl).toString().length() <10)
				{
					//Need to dicuss for this
					//int reply=JOptionPane.showConfirmDialog(null,sMessageID , null, JOptionPane.YES_NO_OPTION);
					//if(reply==JOptionPane.NO_OPTION){
						//formObject.NGFocus(sEIDADataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;//BUG ID: 1061554
					//	}
						return false;
						//}
				}
				}
			}
			else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sManualDataControl).toString().length() <10)
				{
					//int reply=JOptionPane.showConfirmDialog(null,sMessageID , null, JOptionPane.YES_NO_OPTION);
					//if(reply==JOptionPane.NO_OPTION){
						//formObject.NGFocus(sManualDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;//BUG ID: 1061554
						//}
						return false;}
					}
				}
			}
		}
		flag_phone=false;
		return true;
	}	

 public Boolean ValidateMobileNo(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageID)
 {
	logInfo("ValidateMobileNo","INSIDE ValidateMobileNo");
	if(flag_mobile){
		if("United Arab Emirates".equalsIgnoreCase(checkCountry()))
		{
			sMessageID="CR0002";

			logInfo("ValidateMobileNo","INSIDE IF");
			if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
			{
				logInfo("ValidateMobileNo","Inside FCR check");
				if(formObject.getValue(sFCRDataControl).toString().length()!=12)
				{
					//int reply=JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID) , null, JOptionPane.YES_NO_OPTION);
					//if(reply== JOptionPane.NO_OPTION){
						//formObject.NGFocus(sFCRDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;
					//	}
						return false;}

				}
			}
			else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
			{
				logInfo("ValidateMobileNo","Inside EIDA check");
				if(formObject.getValue(sEIDADataControl).toString().length()!=12)
				{
					//int reply=JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID) , null, JOptionPane.YES_NO_OPTION);
					//if(reply== JOptionPane.NO_OPTION){
						//formObject.NGFocus(sEIDADataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;
						//}
						return false;}
				}
			}
			else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
			{
				logInfo("ValidateMobileNo","ValidateMobileNo:"+
						formObject.getValue(sManualDataControl).toString()+":"+sManualDataControl);
				if(formObject.getValue(sManualDataControl).toString().length()!=12)
				{
					logInfo("ValidateMobileNo","ValidateMobileNo:"+formObject.getValue(sManualDataControl).toString()
							+":"+sManualDataControl);
					//int reply=JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID) , null, JOptionPane.YES_NO_OPTION);
					//if(reply== JOptionPane.NO_OPTION){
						//formObject.NGFocus(sManualDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;
						//}
						return false;}
				}
			}
		}
		else
		{
			logInfo("ValidateMobileNo","INSIDE ELSE");
			if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sFCRDataControl).toString().length() <10)
				{
					//int reply=JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID) , null, JOptionPane.YES_NO_OPTION);
					//if(reply== JOptionPane.NO_OPTION){
						//formObject.NGFocus(sFCRDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;
						//}
						return false;}
				}
			}
			else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
			{
				if(formObject.getValue(sEIDADataControl).toString().length() <10)
				{
					//int reply=JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID) , null, JOptionPane.YES_NO_OPTION);
					//if(reply== JOptionPane.NO_OPTION){
						//formObject.NGFocus(sEIDADataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;
						//}
						return false;}
				}
			}
			else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
			{
				logInfo("ValidateMobileNo","INSIDE ELSE"+formObject.getValue(sManualDataControl).toString());
				if(formObject.getValue(sManualDataControl).toString().length() <10)
				{
					//int reply=JOptionPane.showConfirmDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID) , null, JOptionPane.YES_NO_OPTION);
					//if(reply== JOptionPane.NO_OPTION){
						//formObject.NGFocus(sManualDataControl);
						if(flag_insert_audit=true){
							insertMobileChangeAudit();
							flag_insert_audit=false;
						//}
						return false;
					}
				}
			}
		}
	}
	flag_mobile=false;
	return true;
 }
  public Boolean ValidateCounrtyBirth(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageID,String sControl){
		logInfo("ValidateCounrtyBirth","INSIDE ValidateCounrtyBirth");
		if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
		{
			if(formObject.getValue(sFCRDataControl).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(sFCRDataControl,sMessageID);
				//JOptionPane.showMessageDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID));
				//formObject.NGFocus(sFCRCBControl);
				return false;
			}
		}
		else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
		{
			if(formObject.getValue(sEIDADataControl).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(sEIDADataControl,sMessageID);
				//JOptionPane.showMessageDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID));
				//formObject.NGFocus(sEIDACBControl);
				return false;
			}
		}
		else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
		{
			logInfo("ValidateCounrtyBirth","INSIDE IT 1");
			if(formObject.getValue(sManualDataControl).toString().equalsIgnoreCase("") 
					|| formObject.getValue(sManualDataControl).toString().equalsIgnoreCase("--Select--"))
			{
				logInfo("ValidateCounrtyBirth","INSIDE IT 2");
				sendMessageValuesList(sManualDataControl,sMessageID);
				//JOptionPane.showMessageDialog(null,NGFUserResourceMgr.getResourceString_val(sMessageID));
				//formObject.NGFocus(sManualCBControl);
				return false;
			}
		}

		logInfo("ValidateCounrtyBirth","ITS TRUE");
		return true;
	}*/
	/*public Boolean MandatoryComparisonData(){
		//Start Edit For Non Mandatory In Case Of Minor Only
		logInfo("MandatoryComparisonData","inside MandatoryComparisonData-----");
		String sAccRelation="";
		String sBankRelation="";
		try
		{
			NGRepeater objChkRepeater = formObject.getNGRepeater("REPEAT_FRAME");
			int iSelectedRow = Integer.parseInt(formObject.getValue("AO_SELECTED_ROW_INDEX"))+1;
			sAccRelation= objChkRepeater.getValue(iSelectedRow,"AO_ACC_RELATION.ACC_RELATION");
			sBankRelation= objChkRepeater.getValue(iSelectedRow,"AO_ACC_RELATION.BANK_RELATION");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//End Edit  For Non Mandatory In Case Of Minor Only
		if(!ValidateComparisonDataCombo(CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA,CHECKBOX_PREFIX_MANUAL,FCR_PREFIX,EIDA_PREFIX,
				MANUAL_PREFIX,CA037,"Mandatory","Prefix"))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,EIDA_NAME,
				MANUAL_NAME,CA011,"Mandatory","Full Name"))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,FCR_SHORTNAME,EIDA_SHORTNAME,
				MANUAL_SHORTNAME,CA0184,"Mandatory","Short Name"))
		{
			return false;
		}
		if(!ValidateName(CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,EIDA_NAME,
				MANUAL_NAME,CA0134,CA0138))
		{
			return false;
		}

		if(sBankRelation.equalsIgnoreCase("New"))
		{
			if(!ValidateComparisonData("CHECK12","CHECK57","CHECK34","mothersname_fcr","MOTHERNAME_EIDA",
					"MOTHERNAME_manual",CA0123,"Mandatory","Mother Name"))
			{
				return false;
			}
		}
		else
		{
			if(!ValidateComparisonData("CHECK12","CHECK57","CHECK34","mothersname_fcr","MOTHERNAME_EIDA",
					"MOTHERNAME_manual",CA0123,"Optional","Mother Name"))
			{
				return false;
			}
		}

		if(!ValidateName(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL,FCR_MOTHERSNAME,
				EIDA_MOTHERNAME,MANUAL_MOTHERNAME,
				CA0139,CA0140))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,
				MANUAL_EIDANO,
				CA0167,"Optional","EIDA number"))
		{
			return false;
		}

		if(!validateEidaNo(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,
				MANUAL_EIDANO,CA0171))
		{
			return false;
		}	
		if(!ValidateComparisonData(CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL,FCR_ADDRESS,
				EIDA_ADDRESS,MANUAL_ADDRESS,
				CA048,"Mandatory","PO Box"))
		{
			return false;
		}

		if(!ValidateComparisonDataCombo(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL,
				FCR_CNTRY,EIDA_CNTRY,
			MANUAL_CNTRY,CA020,"Mandatory","Country of Correspondence Address"))
		{
			return false;
		}
		if(!ValidateComparisonDataCombo(CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_EIDA,CHECKBOX_COUNTRY_RES_MANUAL,
				FCR_RESIDENT,EIDA_RESIDENT,
			MANUAL_RESIDENT,CA0155,"Mandatory","Residential Address Country"))
		{
			return false;
		}

		if(!ValidateComparisonDataComboForDot(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,FCR_COUNTRYBIRTH,
				EIDA_COUNTRYBIRTH,
			MANUAL_COUNTRYBIRTH,CA0178,"Mandatory","Country of Birth"))
			{
				return false;
			}
		if ((null==formObject.getValue(CITYBIRTH_MANUAL).toString()
			||formObject.getValue(CITYBIRTH_MANUAL).toString().equalsIgnoreCase("")))
			 {
				sendMessageValuesList(null,"Please enter city of birth");
				return false;
			}
		if(!ValidateComparisonDataCombo(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,
				CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,			  EIDA_PER_CNTRY,MANUAL_PER_CNTRY,CA074,"Mandatory","Country of Permanant Residence"))
				{
					return false;
				}

		if(sActivityName.equalsIgnoreCase("DDE_Cust_Info")||sActivityName.equalsIgnoreCase("DDE_Account_Info")
			||sActivityName.equalsIgnoreCase("CPD Maker"))
				{
					if(formObject.getValue("PERM_CNTRY").toString().equalsIgnoreCase("") 
					  || formObject.getValue("PERM_CNTRY").toString().equalsIgnoreCase("") 
					  || formObject.getValue("PERM_CNTRY").toString().equalsIgnoreCase("."))
						{
							sendMessageValuesList(null,CA0185);
							return false;
						}
					if(formObject.getValue("RES_CNTRY").toString().equalsIgnoreCase("") 
						|| formObject.getValue("RES_CNTRY").toString().equalsIgnoreCase("") 
						|| formObject.getValue("RES_CNTRY").toString().equalsIgnoreCase("."))
							{
								sendMessageValuesList(null,CA0186);
								return false;
							}
					if(formObject.getValue("CORR_CNTRY").toString().equalsIgnoreCase("") 
						|| formObject.getValue("CORR_CNTRY").toString().equalsIgnoreCase("") 
						|| formObject.getValue("CORR_CNTRY").toString().equalsIgnoreCase("."))
						  {
							sendMessageValuesList(null,CA0187);
							return false;
						  }
					String sVisaType=returnVisaStatus();
					if(sVisaType.equalsIgnoreCase("Residency Visa")
					   &&formObject.getValue("drp_reseida").toString().equalsIgnoreCase("")){
							sendMessageValuesList(null,"Please select Value of Resident without EIDA");
							return false;
					}
					if(formObject.getValue("drp_reseida").toString().equalsIgnoreCase("no") 
						&& sVisaType.equalsIgnoreCase("Residency Visa"))
						{
							if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,MANUAL_EIDANO,
								CA0167,"Mandatory","EIDA number"))
								{
									return false;
								}
					}
				}
		if(sActivityName.equalsIgnoreCase("DDE_Cust_Info")||sActivityName.equalsIgnoreCase("CPD Maker")){
			if(formObject.getValue(CP_CITY).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(null,CA051);
				//ormObject.NGFocus("TEXT153");
				return false;
			}
			if(formObject.getValue(RA_CITY).toString().equalsIgnoreCase(""))
			{
				sendMessageValuesList(null,CA066);
				//formObject.NGFocus("TEXT157");
				return false;
			}
		}
		if(sActivityName.equalsIgnoreCase("DDE_Cust_Info")||sActivityName.equalsIgnoreCase("CPD Maker")){
			String employertype=formObject.getValue(ED_EMP_TYPE).toString();
			String employeeid=formObject.getValue(ED_EMPLYID).toString();//BUG ID: 1062276
			if(employertype.equalsIgnoreCase("ADCB")&&employeeid!=null){
				if (!(employeeid.matches("[0-9]+"))) {
					sendMessageValuesList(null,CA0179);
					//formObject.NGFocus("TEXT84");
					return false;
				}
				if (!(employeeid.length()>=4)) {
					sendMessageValuesList(null,CA0180);
					//formObject.NGFocus("TEXT84");
					return false;
				}
			}

		}
		if(sActivityName.equalsIgnoreCase("DDE_Cust_Info")
			||sActivityName.equalsIgnoreCase("QDE_Cust_Info")
			||sActivityName.equalsIgnoreCase("CPD Maker")
			||sActivityName.equalsIgnoreCase("CPD Checker")
			||sActivityName.equalsIgnoreCase("Bulk EOD Checker")
			||sActivityName.equalsIgnoreCase("QDE_Acc_Info_Chk")
			||sActivityName.equalsIgnoreCase("DDE_Acc_Info_Chk")){
				if(!ValidateComparisonDataComboForDot(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,	EIDA_CNTRY,MANUAL_CNTRY,CA0181,"Mandatory","Country of Correspondence address"))
				{
					return false;
				}


				if(!ValidateComparisonDataComboForDot(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,EIDA_PER_CNTRY,MANUAL_PER_CNTRY,CA0182,"Mandatory","Country of Permanent address"))
				{
					return false;
				}

				if(!ValidateComparisonDataComboForDot(CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_COUNTRY_RES_EIDA,CHECKBOX_COUNTRY_RES_MANUAL,FCR_RESIDENT,EIDA_RESIDENT,MANUAL_RESIDENT,CA0183,"Mandatory","Country of Residence address"))
				{
					return false;
				}
		}
		if(sActivityName.equalsIgnoreCase("QDE_Cust_Info"))
		{
			if(formObject.getValue(VISA_STATUS).toString().equalsIgnoreCase("Residency Visa")
				&&formObject.getValue(DRP_RESEIDA).toString().equalsIgnoreCase(""))
					{
						sendMessageValuesList(null,"Please select Value of Resident without EIDA");
						//formObject.NGFocus("drp_reseida");
						return false;
					}
			if(formObject.getValue(DRP_RESEIDA).toString().equalsIgnoreCase("no") 
				&& formObject.getValue(VISA_STATUS).toString().equalsIgnoreCase("Residency Visa"))
					{
						if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,MANUAL_EIDANO,
						CA0167,"Mandatory","EIDA number"))
						{
							return false;
						}
					}
		}

		if(!ValidateComparisonData(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,"ph_fcr","ph_EIDA","ph_MANUAL",CA057,
				"Optional","Phone number"))
		{
			return false;
		}
		if(flag_phone_start.equalsIgnoreCase("true")){
			if(!ValidateMobileNoStart(CHECKBOX_TELE_RES_MANUAL,"ph_MANUAL","Mandatory",CA0161,"Residence Phone Number"))
			{
				return false;
			}
		}
		if(!ValidatePhoneNo(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,"ph_fcr","ph_EIDA","ph_MANUAL",CA0127))
		{
			return false;
		}

		if(!ValidateComparisonData(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,"mobile_fcr","mobile_eida","mobile_manual",
			CA059,"Mandatory","Mobile number"))
				{
					return false;
				}
		if(flag_phone_start.equalsIgnoreCase("true")){
			if(!ValidateMobileNoStart(CHECKBOX_TELE_MOB_MANUAL,"mobile_manual","Optional",CA0161,"Mobile Number "))
			{
				return false;
			}
		}
		if(!ValidateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,"mobile_fcr","mobile_eida","mobile_manual",CA0126))
		{
			return false;
		}	
		if(sBankRelation.equalsIgnoreCase("New"))
		{
			if(!ValidateComparisonData(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,"email_fcr","email_eida","email_manual",CA054,
					"Optional","Email ID"))
			{
				return false;
			}
		}
		else
		{
			if(!ValidateComparisonData(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,"email_fcr","email_eida","email_manual",CA054,
					"Optional","Email ID"))
			{
				return false;
			}
		} 

		if(!ValidateComparisonData(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,"dob_fcr","dob_eida","dob_manual",CA012,
				"Mandatory","Date of Birth"))
		{
			return false;
		}
		if(!ValidateDOB(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,"dob_fcr","dob_eida","dob_manual"))			
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL,"passportno_fcr","passportno_eida","passportno_manual",CA0120,"Mandatory","Passport number"))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL,"passportissdate_fcr","passportissdate_eida",
				"passportissdate_manual",CA0121,"Mandatory","Passport Issue Date"))
		{
			return false;
		}
		if(!ValidateFutureDates(CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL,"passportissdate_fcr","passportissdate_eida",
				"passportissdate_manual","Passport Issue"))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL,"passportexpdate_fcr","passportexpdate_eida",
				"passportexpdate_manual",CA0122,"Mandatory","Passport Expiry Date"))
		{
			return false;
		}
		if(!ValidatePastDates(CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL,"passportexpdate_fcr","passportexpdate_eida",
				"passportexpdate_manual","Passport Expiry"))
		{
			return false;
		}
		if(!ValidateComparisonDataCombo(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL,"nationality_fcr","nationality_eida",
				"nationality_manual","CA013","Mandatory","Nationality"))
		{
			return false;
		}	
		if(!ValidateComparisonData(CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL,"VISANO_FCR","VISANO_EIDA","VISANO_MANUAL",
				"CA0135","Optional","Visa No"))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL,"visaissdate_FCR","visaissdate_eida","visaissdate_MANUAL","CA0136","Optional","Visa Issue Date"))
		{
			return false;
		}
		if(!ValidateFutureDates(CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL,"visaissdate_FCR","visaissdate_eida","visaissdate_MANUAL","Visa Issue"))
		{
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,"visaexpdate_fcr","visaexpdate_EIDA","visaexpdate_MANUAL","CA0137","Optional","Visa Expiry Date"))
		{
			return false;
		}
		if(!ValidatePastDates(CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,"visaexpdate_fcr","visaexpdate_EIDA","visaexpdate_MANUAL","Visa Expiry"))
		{	
			return false;
		}		
		if(!sAccRelation.equalsIgnoreCase("Minor"))
		{
			if(!ValidateComparisonDataCombo(CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA,CHECKBOX_PROFESSION_MANUAL,"prefession_fcr","prefession_eida",
					"MANUAL_PROF","CA075","Mandatory","Profession"))
			{
				return false;
			}
		}
		if(!ValidateComparisonDataCombo(CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL,FCR_GENDER,EIDA_GENDER,MANUAL_GENDER,
				CA041,"Mandatory","Gender"))
		{ 
			return false;
		}
		if(!ValidateComparisonData(CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL,EMPLYR_NAME_FCR,EIDA_EMPLYR_NAME,
				MANUAL_EMPLYR_NAME,CA0145,"Optional","Employer Name"))
		{
			return false;
		}
		logInfo("","Outside OFF");
		if(sActivityName.equalsIgnoreCase("DDE_Cust_Info") || sActivityName.equalsIgnoreCase("CPD Maker"))
		{	
			if(!validateOfficeNo(CP_TELEOFFICE,CR0003))
			{
				return false;
			}
		}
		//flag_phone_start="false";
		return true;
	}
	 */

	/*public Boolean ValidateName(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,  //by Kishan
			String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageID,String sMessageID2)
			{
				if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
				{
					if(formObject.getValue(sFCRDataControl).toString().length() <3)
					{
						sendMessageValuesList(null,sMessageID);
						return false;
					}
					if(formObject.getValue(sFCRDataControl).toString().length()>75)
					{
						sendMessageValuesList(null,sMessageID2);
						return false;
					}
				}
				else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
				{
					if(formObject.getValue(sEIDADataControl).toString().length() <3)
					{
						sendMessageValuesList(null,sMessageID);
						return false;
					}
					if(formObject.getValue(sEIDADataControl).toString().length()>75)
					{
						sendMessageValuesList(null,sMessageID2);
						return false;
					}
				}
				else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
				{
					if(formObject.getValue(sManualDataControl).toString().length() <3)
					{
						sendMessageValuesList(null,sMessageID);
						return false;
					}
					if(formObject.getValue(sManualCBControl).toString().length()>75)
					{
						sendMessageValuesList(null,sMessageID2);
						return false;
					}
				}
				return true;
		}*/

	//	public Boolean validateFutureDates(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,
	//			String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageField)  //By Kishan
	//		{
	//			if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
	//			{
	//				if(!validateFutureDate(sFCRDataControl,sMessageField))
	//				{
	//					//f//ormObject.NGFocus(sFCRDataControl);
	//					return false;
	//				}
	//			}
	//			else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
	//			{
	//				if(!validateFutureDate(sEIDADataControl,sMessageField))
	//				{
	//					//formObject.NGFocus(sEIDADataControl);
	//					return false;
	//				}
	//			}
	//			else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
	//			{
	//				if(!validateFutureDate(sManualDataControl,sMessageField))
	//				{
	//					//formObject.NGFocus(sManualDataControl);
	//					return false;
	//				}
	//			}
	//			return true;
	//	}
	/*public Boolean validateOfficeNo(String sFCRDataControl,String sMessageID) //By Kishan
	{
		logInfo("validateOfficeNo","INSIDE ValidateOfficeNo -Shivam");

		if(formObject.getValue("Text43").toString().equalsIgnoreCase("") 
			|| formObject.getValue("Text43").toString().equalsIgnoreCase(null))
			return true;

		if("United Arab Emirates".equalsIgnoreCase(checkCountry()))
		{
			String sNumber = formObject.getValue(sFCRDataControl).toString();
			if((!sNumber.startsWith("971")))					 
			{
				sendMessageValuesList(null,"Office no should start with 971");
				return false;

			}
			if(formObject.getValue(sFCRDataControl).toString().length()!=11)
			{
				sendMessageValuesList(null,sMessageID);
				return false;
			}
		}
		return true;
	}*/
	/*public Boolean validatePastDates(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,
			String sFCRDataControl,String sEIDADataControl,String sManualDataControl,String sMessageField)
	{
		if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
		{
			if(!validatePastDate(sFCRDataControl,sMessageField))
			{
				//formObject.NGFocus(sFCRDataControl);
				return false;
			}
		}
		else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
		{
			if(!validatePastDate(sEIDADataControl,sMessageField))
			{
				//formObject.NGFocus(sEIDADataControl);
				return false;
			}
		}
		else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
		{
			if(!validatePastDate(sManualDataControl,sMessageField))
			{
				//formObject.NGFocus(sManualDataControl);
				return false;
			}
		}
		return true;
	}*/
	/*public Boolean validatePastDate(String date,String controlName)
    {
        try
		{					 
			logInfo("validatePastDate","Called validateFutureDate ");
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpledateformat = new SimpleDateFormat("DATEFORMAT");
			String scurrentDate = simpledateformat.format(calendar.getTime());
			String sDate= formObject.getValue(date).toString();
			if(sDate.equalsIgnoreCase(""))
				return true;
			logInfo("validatePastDate","date :"+sDate + "\n "+ "scurrentDate "+scurrentDate);

			if(!scurrentDate.equals(""))
			{
				String [] temp =scurrentDate.split("/");				
				if(temp[1].length() ==3)
				{
					scurrentDate=temp[0]+"/"+getMonthNumber(temp[1])+"/"+temp[2];
				}
				else
				{
					scurrentDate=temp[0]+"/"+temp[1]+"/"+temp[2];
				}
			}

			Date currentDate = simpledateformat.parse(scurrentDate);
			Date nDate = simpledateformat.parse(sDate);   
			logInfo("validatePastDate","currentDate :"+currentDate +"\n "+"nDate "+nDate);

			if(nDate.compareTo(currentDate) < 0)
			{
				sendMessageValuesList(null, controlName+CA0129);
				return false;
			}
			else if(Integer.parseInt(sDate.substring(sDate.lastIndexOf("/")+1, sDate.length()))>= 2099)
			{    
				sendMessageValuesList(null, controlName+" Date can not be greater than 2099");			
				return false;
			}
		}
		catch(Exception exp)
		{
			logInfo("","Caught the exception "+exp.getMessage());
			exp.printStackTrace();
		}	
        return true;
    }*/
	/*public Boolean validateDOB(String sFCRCBControl,String sEIDACBControl,String sManualCBControl,
			String sFCRDataControl,String sEIDADataControl,String sManualDataControl)
	{
		if(formObject.getValue(sFCRCBControl).toString().equalsIgnoreCase("True"))
		{
			if(!validateDOB(sFCRDataControl))
			{
				return false;
			}
		}
		else if(formObject.getValue(sEIDACBControl).toString().equalsIgnoreCase("True"))
		{
			if(!validateDOB(sEIDADataControl))
			{
				return false;
			}
		}
		else if(formObject.getValue(sManualCBControl).toString().equalsIgnoreCase("True"))
		{
			if(!validateDOB(sManualDataControl))
			{
				return false;
			}
		}
		return true;
	}*/
	private boolean submitWorkitem(String data) {
		logInfo("submitWorkitem","Inside Button click");

		//commented for testing purpose , should be back in code 
		/*if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
			&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
				logInfo("SUBMIT CLICK","Inside if--------Please select the appropriate checkbox to complete the validation");
				sendMessageValuesList("","Please select the appropriate checkbox to complete the validation");
		}*/
		if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)){ //Jamshed
			/*
			//Jamshed
			String sData  = formObject.getValue(CRO_DEC).toString();
			String vig_query = "select pri_cust_segment,final_risk_val,is_vigilance_visited from ext_ao where wi_name='"+sWorkitemId+ "'";
			logInfo("CRO_DEC","vig_query= "+vig_query);
			List<List<String>> vig_list=formObject.getDataFromDB(vig_query);
			String pri_cust_segment="";
			String final_risk_val="";
			String is_vigilance_visited="";
			if (vig_list != null && vig_list.size() > 0){
				pri_cust_segment=vig_list.get(0).get(0);
				final_risk_val=vig_list.get(0).get(1);
				is_vigilance_visited=vig_list.get(0).get(2);
			}
			if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade") ||
				 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") || 
				 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Family Banking"))){
				
			if(! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") &&  
			   ! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ){
			if((final_risk_val!=null && final_risk_val.equalsIgnoreCase("Medium Risk")) && 
					(pri_cust_segment!=null && pri_cust_segment.equalsIgnoreCase("Private Clients")) &&
					!(sData.equalsIgnoreCase("Send To Vigilance")) && !(sData.equalsIgnoreCase("Permanent Reject - Discard")) &&
					!(is_vigilance_visited.equalsIgnoreCase("Yes"))){
				sendMessageValuesList(CRO_DEC,"Please select Send To Vigilance decision for Medium Risk and Private Clients ");
				return false;
					}
				}else{
					String newSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
					if((final_risk_val!=null && final_risk_val.equalsIgnoreCase("Medium Risk")) && 
							(newSegment!=null && newSegment.equalsIgnoreCase("Private Clients")) &&
							!(sData.equalsIgnoreCase("Send To Vigilance")) && !(sData.equalsIgnoreCase("Permanent Reject - Discard")) &&
							!(is_vigilance_visited.equalsIgnoreCase("Yes"))){
						sendMessageValuesList(CRO_DEC,"Please select Send To Vigilance decision for Medium Risk and Private Clients ");
						return false;
					}
				}
			} */
			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") //shahbaz
			      && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				logInfo("submitWorkitem ","check Dormant Customer: ");
				String reqType=formObject.getValue(REQUEST_TYPE).toString();
				String ws_decision=formObject.getValue(CRO_DEC).toString();
				logInfo("submitWorkitem ","Downgrade & Approve--: " +ws_decision+" : "+ws_decision);
				dormantCustAlert();				
			}
			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")){
				String newSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
				logInfo("croDec","Downgrade"+newSegment);
				String croDec = formObject.getValue(CRO_DEC).toString();
				logInfo("croDec","Downgrade"+croDec);
				if ((newSegment==null||newSegment.equalsIgnoreCase("")) && croDec.equalsIgnoreCase("Approve")){
					sendMessageValuesList(CRO_DEC,"Customer is already enrolled with lowest segment hence segment downgrade is not possible, Kindly reject the case.");
				}
			}
			//Jamshed end
		}
		if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject")) { //added on 14 oct 2021
			return true;
		}
		if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")) {
		boolean prodChangeFlag = checkProdChngForNoEligibility();
		if (!prodChangeFlag) {
			logInfo("SUBMIT CLICK","INSIDE prodChangeFlag");
			sendMessageValuesList("","Customer is not eligible for cheque book. Please change the product.");
		}
		}
		insertUdfDetails();
		enableControls(new String[] { BTN_SUBMIT });
		boolean result = false;
		if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account")) {
			// NGRepeater objChkRep =
			// formObject.getNGRepeater("REPEAT_FRAME");
			String acc_rel = formObject.getTableCellValue(ACC_RELATION, 1, 9);
			logInfo("SUBMIT BUTTON","ACC_RELATION  = " + acc_rel);
			logInfo("SUBMIT BUTTON","inside ikyc signatory-------");
			logInfo("SUBMIT BUTTON","start----");
			String sQuery = "select cust_seg from usr_0_cust_txn WHERE WI_NAME ='"+ sWorkitemId + "'"
					+ "and cust_sno='1'";
			logInfo("SUBMIT BUTTON","o/p from queryyyyyy" + sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("SUBMIT BUTTON","AP select--" + sOutput);
			String pri_cust_cat = "";
			try{
				if(sOutput!=null && sOutput.size()>0){
					pri_cust_cat = sOutput.get(0).get(0);
				}
			}catch(Exception e){
				logError("",e);//e.printStackTrace();
			}							
			logInfo("SUBMIT BUTTON","Customer segment------------::::"+ pri_cust_cat);
			if (!(acc_rel.equalsIgnoreCase("AUS") && acc_rel.equalsIgnoreCase("POA"))
					&& pri_cust_cat.equalsIgnoreCase("Signatory")) {
				logInfo("SUBMIT BUTTON","!(acc_rel.equalsIgnoreCase(AUS) || acc_rel.equalsIgnoreCase(POA))===="
						+ !(acc_rel.equalsIgnoreCase("AUS") || acc_rel.equalsIgnoreCase("POA")));
				logInfo("SUBMIT BUTTON"," pri_cust_cat.equalsIgnoreCase(Signatory)"+ pri_cust_cat.equalsIgnoreCase("Signatory"));
				logInfo("SUBMIT BUTTON","inside if--------");
				sendMessageValuesList("","Primary Signatory Customer can't open a New Account!!");
			}
		}
		result = false;
		if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {			
			logInfo("SUBMIT BUTTON","Inside QDE_CUST_INFO ");
			int iSelectedRow = 0;
			String ismandatory = null;
			try{
				iSelectedRow = Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX)) + 0;
			}catch(Exception e){
				logError("submitWorkitem", e);
			}
			logInfo("SUBMIT BUTTON","CHECK 1");
			String sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
			if (formObject.getValue(RESIDENCY_STATUS).toString().equalsIgnoreCase("")) {
				calculateResidencyStatus(RESIDENCY_STATUS);
			}
			logInfo("SUBMIT BUTTON","CHECK 2");
			//result = checkNatSegment();
			if(!checkNatSegment()) {
				return false;
			}
			logInfo("SUBMIT BUTTON", "NATIONALITY VALIDATION---"+ result);
			if(!mandatoryComparisonData()) {
				return false;
			}
			logInfo("SUBMIT BUTTON","mandatoryComparisonData result now---"+ result);
			if(!mandatoryiKYC()) {
				return false;
			}
			logInfo("SUBMIT BUTTON","mandatoryiKYC result now---"+ result);
			if(!mandatoryAtFatca()) {
				return false;
			}
			logInfo("SUBMIT BUTTON","Submit validation fatca result now---"+ result);			 
			String custSegment = formObject.getValue(CUST_SEGMENT1).toString();
			List<List<String>> sOutputt = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment "
					+ "where cust_segment='"+ custSegment + "'");
			if(sOutputt.size()>0 && sOutputt!=null){
				try {
					ismandatory = sOutputt.get(0).get(0);
				} catch (Exception e1) {
					logError("Exception in  QDE_CUST_INFO", e1);
				}
			}
			logInfo("SUBMIT BUTTON","CUST Segment result: " + ismandatory);
			boolean custSegmentCheck = false;
			try{
				if (ismandatory.equalsIgnoreCase("Yes"))
					custSegmentCheck = true;
				else
					custSegmentCheck = false;
			}catch(Exception e){
				e.printStackTrace();
			}		
			logInfo("SUBMIT BUTTON","CUST Segment result:Debug " );					
			logInfo("SUBMIT BUTTON","Submit CRS Check result--" + result);
			if (!mandatoryCRSCheck(custSegmentCheck)) {
				enableControls(new String[] { BTN_SUBMIT });
				return false;
			}
			if (!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,
					CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE, EIDA_PSSTYPE,MANUAL_PASSTYPE, CA018, HD_PASS_TYPE)) {
				enableControls(new String[] { BTN_SUBMIT });
			}
			if (!ValidateComparisonData(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
					CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS, EIDA_VISASTATUS,
					MANUAL_VISASTATUS, CA019, "Mandatory","Visa Status")) {
				return false;
			}
			if (!validateVisaStatus()) {
				return false;
			}
			enableControls(new String[] { BTN_SUBMIT });
			if (!validateCustomerClassification()) {
				return false;
			}
			if(getGridCount(CRS_TAXCOUNTRYDETAILS) > 0 )
			{
				for(int i=0 ; i<getGridCount(CRS_TAXCOUNTRYDETAILS);i++)
				{
					String countryOfTaxResidency=formObject.getTableCellValue(CRS_TAXCOUNTRYDETAILS,i,1);
					logInfo("submit","Value of selected Tax Residency country"+ countryOfTaxResidency);
					if (countryOfTaxResidency.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
						logInfo("Gautam","<<<< 66 >>>>>>>>>");
						result = MandatoryCRSDueDiligence();
						System.out.println("MandatoryCRSDueDiligence result---"+result);
						if(!result)
						{
							return false;
						}
						break;
					}
				}
			}
			if(!checkSalaryTransfer()){
				return false;
			}
			enableControls(new String[] { BTN_SUBMIT });
			try {
				String sFinalDOB = getFinalDataComparison(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,
						EIDA_DOB,MANUAL_DOB);
				int age = calculateAge(sFinalDOB);
				int age1 = CalculateAge1(sFinalDOB);
				String accountOwn = formObject.getValue(ACC_OWN_TYPE).toString();
				boolean bresult = false;
				String sQueryy = "select VALUE from usr_0_defaultvalue_fcr where name='Minor_Age'";
				List<List<String>> sOutput = formObject.getDataFromDB(sQueryy);
				logInfo("SUBMIT BUTTON","sOutputt------" + sOutput);
				int sMinorAge = 0 ;
				try{
					sMinorAge = Integer.parseInt(sOutput.get(0).get(0));
				}catch(Exception e){
					logError("submitWorkItem",e);
				}
				logInfo("SUBMIT BUTTON","sMinorAge....." + sMinorAge);
				if (accountOwn.equalsIgnoreCase("Minor")) {
					if (age1 > sMinorAge&& sAccRelation.equalsIgnoreCase("Minor")) {
						sendMessageValuesList("","For Minor Date Of Birth Should Not Be Greater Than or equal to "+ sMinorAge + " Years.");
						bresult = true;
					}
					if (age < sMinorAge&& sAccRelation.equalsIgnoreCase("Guardian")) {
						sendMessageValuesList("","For Guardian Date Of Birth Should Be Greater Than or equal to "+ sMinorAge + " Years.");
						bresult = true;
					}
				} else if (accountOwn.equalsIgnoreCase("Joint")&& age < sMinorAge) {
					sendMessageValuesList("","For Joint Date Of Birth Should be greater than or equal to "+ sMinorAge + " Years.");
					bresult = true;
				} else {
					if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
						if (age < 18) {
							sendMessageValuesList("","For Single Date Of Birth Should be greater than or equal to 18 Years.");
							bresult = true;
						}
					}
				}
				if (bresult) {
					if (formObject.getValue(CHECKBOX_DOB_FCR).toString().equalsIgnoreCase("true")) {
						sendMessageValuesList(FCR_DOB,"Date Of Birth Should be greater than or equal to 18 Years.");
					} else if (formObject.getValue(CHECKBOX_DOB_EIDA).toString().equalsIgnoreCase("true")) {
						sendMessageValuesList(EIDA_DOB,"Date Of Birth Should be greater than or equal to 18 Years.");
					} else if (formObject.getValue(CHECKBOX_DOB_MANUAL).toString().equalsIgnoreCase("true")) {
						sendMessageValuesList(MANUAL_DOB,"Date Of Birth Should be greater than or equal to 18 Years.");
					}
					enableControls(new String[] { BTN_SUBMIT });
				}
			} catch (Exception e) {
				logError("submitWorkitem",e);
			}
			try {
				String dedupeDone = "";
				int sSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;				
				String sQuery1 = "SELECT IS_DEDUPE_CLICK FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' and "
						+ "cust_sno='"+sSelectedRow+"'";
				logInfo("submitWorkitem"," sQuery1 dedupeDone: "+sQuery1);
				List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
				logInfo("submitWorkitem"," output1 dedupeDone"+output1);
				if(output1.size()>0) {
					dedupeDone = output1.get(0).get(0);
					logInfo("submitWorkitem","dedupeDone"+dedupeDone);
				}		
				if(!dedupeDone.equalsIgnoreCase("true")) {
					sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
					return false;
				}
			} catch (Exception e) {
				logError("submitWorkitem",e);
			}
			try {
				if (sAccRelation.equalsIgnoreCase("Minor")) {
					result = minorDateCompDOB();
					if (!result) {
						enableControls(new String[] { BTN_SUBMIT });
					}
				}
			} catch (Exception e) {
				logError("submitWorkitem",e);
			}	
			logInfo("SUBMIT BUTTON","SAVING DATA STARTED");
			saveKYCInfoRetailQDE(); //Added by Jamshed
//			saveKycMultiDropDownData();
//			savePreAssessmentDetails();   //shahbaz
			saveComparisonData();
			logInfo("SUBMIT BUTTON","SAVING DATA STARTED CHECK 1");
			saveCustNeedAnalysisData();
			logInfo("SUBMIT BUTTON","SAVING DATA STARTED CHECK 2");
			saveHiddenDataQDE();
			logInfo("SUBMIT BUTTON","SAVING DATA STARTED CHECK 3");
			saveClientQuestionsData(); 
			logInfo("SUBMIT BUTTON","SAVING DATA STARTED CHECK 4");
			saveCRSDetails(); 
			logInfo("SUBMIT BUTTON","SAVING DATA STARTED CHECK 5");
			int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) ;
			logInfo("SUBMIT BUTTON","iProcessedCustomer :: "+ iProcessedCustomer);
			String sCustNo = "";
			sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			logInfo("SUBMIT BUTTON","sCustNo :: "+ sCustNo);
			String sBankRelation = formObject.getTableCellValue(ACC_RELATION,iSelectedRow, 7);
			logInfo("SUBMIT BUTTON","sBankRelation :: "+ sBankRelation);
			String sCID = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
			logInfo("SUBMIT BUTTON","sCID :: "+ sCID);
			String sCASA = "";
			if (sBankRelation.equalsIgnoreCase("Existing")) {
				logInfo("SUBMIT BUTTON","Inside sBankRelation Condition ");
				try{
					List<List<String>> sOutput = formObject.getDataFromDB("SELECT COUNT(1) AS COUNT_WI FROM "
							+ "USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+ sWorkitemId+ "' AND CUSTOMER_ID ='"+ sCID+ "'"
							+ " AND ACCOUNT_TYPE ='CASA'");
					logInfo("SUBMIT BUTTON","sBankRelation Condition sOutput :: "+sOutput);
					sCASA = sOutput.get(0).get(0);
					logInfo("SUBMIT BUTTON", "CASA Value:"+ sCASA);
				}catch(Exception e){
					logError("submitWorkitem",e);
				}
			}
			logInfo("SUBMIT BUTTON","After sBankRelation Condition");
			if (sBankRelation.equalsIgnoreCase("New")|| sCASA.equalsIgnoreCase("0")) {
				try {
					String output = getApplicationRiskInputXML(iProcessedCustomer +1);
					logInfo("SUBMIT BUTTON", "XML:" + output);
					String outputresult = socket.connectToSocket(output);//xecuteWebserviceAll(output);
					logInfo("SUBMIT BUTTON","outputresult--in webservice--"+ outputresult);
					if (outputresult.equalsIgnoreCase("NO")) {
						sendMessageValuesList("","Selected passport holder and Non UAE Residents, not allowed to open "
								+ "Account");
					} else if (outputresult.equalsIgnoreCase("Partial")) {
						/*int respose = JOptionPane.showConfirmDialog(null,"Selected passport holder Residents "
								+ "does not meet conditions,\nHence not allowed to open Account. Do you still "
								+ "want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
						if (respose == JOptionPane.YES_OPTION) {
							formObject.setValue(NIG_MAKER, "YES");
							String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONMAKER='YES' Where WI_NAME='"
									+ formObject.getValue(WI_NAME)+ "' AND CUST_SNO ='"+ sCustNo+ "'";
							formObject.saveDataInDB(updatequery);
							logInfo("SUBMIT BUTTON","Updated Successfully");
						} else {
							enableControls(new String[] { BTN_SUBMIT });
						}*/
						sendMessageList.clear();
						sendMessageValuesList("", "Selected passport holder Residents does not meet conditions,\nHence "
								+ "not allowed to open Account. Do you still want to proceed with account opening?");
						return false;
					}
				} catch (Exception e) {
					logError("submitWorkitem",e);
				}
			}
			logInfo("SUBMIT BUTTON","After getApplicationRiskInputXML Code");	
			if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")||
			     formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))){
			if(!calculateIndRiskQDE()) {
				return false;
			}
			}
		} else if (sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
			List<List<String>> recordList = null;
			String sOutput = "";
			String resultTRSD = checkTRSDResult("CRO");
			logInfo(BTN_SUBMIT,"resultTRSD:" + resultTRSD);			
			/*if(formObject.getValue(SOURCE_NAME).toString().equalsIgnoreCase("")){
				logInfo(BTN_SUBMIT,"REQUEST_TYPE->SOURCE_NAME Please Select Source Name.");
				sendMessageValuesList("SOURCE_NAME","Please Select Source Name.");
				sCurrTabIndex=4;
				return false;
			} else if(formObject.getValue(SOURCE_CODE).toString().equalsIgnoreCase("")){
				logInfo(BTN_SUBMIT,"REQUEST_TYPE->SOURCE_CODE Please Select Source Code.");
				sendMessageValuesList("SOURCE_CODE","Please Select Source Code.");
				return false;
			} else*/ 
			if (resultTRSD.equalsIgnoreCase("2")
					&& !formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")){
				sendMessageValuesList("","This application can only be Rejected since TRSD result is Rejected.");
				return false;						
			}  else if (resultTRSD.equalsIgnoreCase("1")&& !formObject.getValue(CRO_DEC).toString().
					equalsIgnoreCase("Return to Originator")){
				sendMessageValuesList("","This application can only be Returned since TRSD result is Returned.");
				return false;						
			}
			//boolean crscategory = mandatoryCRSCheckcategorychange();
			//if (!crscategory)
			//return null;
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")){	
				logInfo(BTN_SUBMIT,"CRO_DEC"+CA0124);
				sendMessageValuesList("CRO_DEC",CA0124);
				return false;						
			} else if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")){
				recordList= formObject.getDataFromDB("SELECT ACCOUNT_INFO_CHECKER_DEC FROM "+sExtTable+" "
						+ "WHERE WI_NAME ='"+sWorkitemId+"'");  
				if(recordList.get(0).get(0).equalsIgnoreCase("Permanent Reject - Discard")){	
					logInfo(BTN_SUBMIT,"CRO_DEC Please select decision Permanent Reject as CRM decision is Permanent Reject");
					sendMessageValuesList("CRO_DEC","Please select decision Permanent Reject as CRM decision is Permanent Reject");
					return false;
				}
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") 
						|| formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||
						formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")){//for downgrade	krishna
					//boolean result =MandatoryCategoryChangeData();// why it is commented in QDE_ACcount_Info its manadatory check
					//logInfo("NEXT_7 result: ", result.toString());
					boolean resultCategoryChange = mandatoryCategoryChangeData();
					/*if(!result){
						enableControls(new String[] { BTN_SUBMIT });
						return false;
					}*/
					if(!resultCategoryChange){
						enableControls(new String[] { BTN_SUBMIT });
						return false;
					}
					//result=();
					//logInfo("NEW VALIDATION---"+result);
					if(!checkNatCatSegment()){
						enableControls(new String[] { BTN_SUBMIT });
						return false;
					}
					/*if(validateFBFetch()) { // family Banking submit validation
						isValidateFBDone();
					}*///FB SUPPRESSED
					if(validateFBFetch()) { // family Banking submit validation
						isValidateFBDone();
					}//FB ADDED
				}	
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")){
				String rejectionReason=formObject.getValue("CRO_REJ_REASON").toString();
				String rejectionRemarks=formObject.getValue("CRO_REMARKS").toString();
				if(rejectionReason.equalsIgnoreCase("")){	
					logInfo(BTN_SUBMIT,"CRO_DEC Please Select Rejection Reason.");
					sendMessageValuesList(CRO_DEC,"Please Select Rejection Reason.");      
					return false;
				}
				if(rejectionRemarks.equalsIgnoreCase("")){	
					logInfo(BTN_SUBMIT,"CRO_DEC Please Fill Remarks.");
					sendMessageValuesList(CRO_DEC,"Please Fill Remarks.");      
					return false;
				}
			} else {
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Compliance") || 
						formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Product Approver"))	{
					if(checkMandatoryDoc(data)!= true){
						logInfo(BTN_SUBMIT,"CRO_DEC checkMandatoryDoc!=True");
						return false;
					}
				}				 
					if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ||
				    	formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") ||
				    	formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){	// downgrade changes krishna				
					if(formObject.getValue(ACC_TITLE).toString().equalsIgnoreCase("")){
						logInfo(BTN_SUBMIT,"REQUEST_TYPE->"+formObject.getValue(REQUEST_TYPE));
						sendMessageValuesList(ACC_TITLE, "Please fill Account Title.");
						//formObject.NGFocus("ACC_TITLE");
						return false;
					}				
					//					NGRepeater objChkRepeater = formObject.getNGRepeater("acc_repeater");
					//					int iRows = objChkRepeater.getRowCount(); //CM
					int iRows = getGridCount(PRODUCT_QUEUE);
					logInfo(BTN_SUBMIT,"getGridCount(PRODUCT_QUEUE)->"+iRows);//acc_repeater     product_queue (EXCEL)
					String sProdCode = ""; 
					if(iRows <0){
						logInfo(BTN_SUBMIT,"REQUEST_TYPE->ACC_TITLE Please add atleast one product.");
						sendMessageValuesList("","Please add atleast one product.");
						//formObject.setStyle("COMMAND24",DISABLE, FALSE);
						//formObject.setStyle(BTN_SUBMIT,DISABLE, FALSE);
						return false;
					}
					for(int i=0; i<iRows; i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);//(i,"AO_PRODUCT_QUEUE.PROD_CODE");
						if(sProdCode.equalsIgnoreCase("")) {
							sendMessageValuesList(PRODUCT_QUEUE,"Blank Row is not allowed.");
							return false;
						}						
						formObject.setTableCellValue(PRODUCT_QUEUE, i, 14,i+1+"");
					}
					if(formObject.getValue(SOURCE_NAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_NAME,"Please Select Source Name.");
						return false;
					}if(formObject.getValue(SOURCE_CODE).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_CODE,"Please Select Source Code.");
						return false;
					    }
				   // downgrade changes krishna
				   }if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				    if((!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||
					    formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") ||
					    formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))) &&
					   (formObject.getTableCellValue(PRODUCT_QUEUE, 0, 3).equalsIgnoreCase(""))) { 
						sendMessageValuesList(PRODUCT_QUEUE,"Please select currency.");
						return false;
					} 
				}
			}
			
			/*boolean v=true;
			if(v==true) {
				try {
					logInfo("confirmOnSubmitInForLoop", "INSIDE");
					int iRows = getGridCount(PRODUCT_QUEUE);
					List<List<String>> output = formObject.getDataFromDB("SELECT DISTINCT PRODUCT_CODE,PRODUCT_NAME,CURRENCY "
							+ "FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"'");
					int iTotalRetrived = output.size();
					if(iTotalRetrived != 0 && null != output.get(0)) {
						for(int i=0; i<iTotalRetrived; i++) {
							for (int j=1; j<iRows; j++) {
								if(formObject.getTableCellValue(PRODUCT_QUEUE, j, 1).equalsIgnoreCase(output.get(i).get(0))
										&& formObject.getTableCellValue(PRODUCT_QUEUE, j, 2).equalsIgnoreCase
										(output.get(i).get(1))
										&& formObject.getTableCellValue(PRODUCT_QUEUE, j, 3).equalsIgnoreCase
										(output.get(i).get(2))) {
									sendMessageValuesList("PRODUCT_QUEUE","Product with following details already added,"
											+ " Do you still want to add \n Code:"+output.get(i).get(0)+" \n Description:"
											+output.get(i).get(1)+" \n Currency:"+output.get(i).get(2)+"$$$confirm$$$"+i);
									return false;
								}
							}
						}
					}
				} catch (Exception e) {
					logError("confirmOnSubmitInForLoop", e);
				}
			}*/
		}
		return true;
	}

	//Save Button Functionality
	private void onSaveTabClickQDE(int tabId){
		logInfo("onSaveTabClickQDE","INSIDE");
		logInfo("onSaveTabClickQDE","tabId=>> "+tabId);
		if(tabId == 1 || tabId == 2 || tabId == 3 || tabId == 4) {
			if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)) {
				saveComparisonData();
				saveCustNeedAnalysisData();
				saveHiddenDataQDE();
				saveClientQuestionsData();  
				saveCRSDetails(); 
				saveKYCInfoRetailQDE();  //Added by Jamshed
				if(tabId == 3){
					savePepAssessmentDetails();   //Ao Dcra
					saveKycMultiDropDownData();
				}
//			        savePreAssessmentDetails();   //shahbaz
			}
		} else if(tabId == 7) {
			if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
				insertUdfDetails();
			}
		}
	}
	
	private boolean calculateIndRiskQDE() {
		String sInputXML ="";
		String sOutput = "";
		int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
		try {
			int iProcessedCustomer=Integer.parseInt((String) formObject.getValue(SELECTED_ROW_INDEX))+1;
		//	sInputXML = getIndRiskInputXML(iProcessedCustomer);
		// AO DCRA COMMENTED //15082023
		   sInputXML = executeApplicationAssessmentRiskRetail(iProcessedCustomer);
			if (sInputXML.contains("<APWebService_Input>")) {
				sOutput = socket.connectToSocket(sInputXML);
				logInfo("SUBMIT BUTTON","outputresult--in webservice--"+ sOutput);
				//Added by Jamshed
				XMLParser xp = new XMLParser(sOutput);
				String finalRisk_cd = xp.getValueOf("finalRisk");
				logInfo("executeApplicationAssessmentRiskRetail", "finalRisk_cd :  "+ finalRisk_cd);
				
				String finalRisk_cd_query ="select risk_value from usr_0_risk_values where risk_code='"+finalRisk_cd+"'";
				logInfo("executeApplicationAssessmentRiskRetail","finalRisk_cd_query= "+ finalRisk_cd_query);
				List<List<String>> output_list_db =formObject.getDataFromDB(finalRisk_cd_query); 
				String finalRisk_value=output_list_db.get(0).get(0); 
				logInfo("executeApplicationAssessmentRiskRetail","finalRisk_value= "+ finalRisk_value);
				sOutput=finalRisk_value;
				
			} else {
				sOutput = sInputXML;
			}
		} catch (Exception e) {

			logError("submitWorkitem",e);
		}
		logInfo("SUBMIT BUTTON","sOutput--in webservice--" + sOutput);
		if (sOutput.equalsIgnoreCase("")) {
			sendMessageValuesList("","Some error occured in calculating Individual risk"); 
			enableControls(new String[] { BTN_SUBMIT });
			return false;
		} else {
			logInfo("onQDEButtonsumbit","Request Type"+formObject.getValue(CUST_SEGMENT1).toString());
			logInfo("onQDEButtonsubmit ","Cust Segment" + formObject.getValue(PD_CUSTSEGMENT).toString());
			logInfo("onQDEButtonsumbit","Request Type"+formObject.getValue(REQUEST_TYPE).toString());
		//	commented  19/11/22
//			 if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") && 
//						formObject.getValue(NEW_CUST_SEGMENT).toString().equalsIgnoreCase("Private Clients")){
//					sOutput="Neutral Risk";
//					logInfo("onQDEButtonsubmit ","Cust Segment" + formObject.getValue(PD_CUSTSEGMENT).toString());
//					logInfo("onQDEButtonsumbit","Request Type"+formObject.getValue(REQUEST_TYPE).toString());
//					}
//			 else
			if ((!sOutput.equalsIgnoreCase("Unacceptable Risk") && !sOutput.equalsIgnoreCase("Non UAE-PEP"))
					&& !sOutput.equalsIgnoreCase("PEP")&& !sOutput.equalsIgnoreCase("UAE-PEP") 
					&& !sOutput.equalsIgnoreCase("Increased Risk") && !sOutput.equalsIgnoreCase("HIO PEP")
					&& (formObject.getValue(CUST_SEGMENT1).toString().equalsIgnoreCase("Private Clients"))){
//				sOutput = "Increased Risk"; //commented by Ameena
				sOutput = "Medium Risk";
			}
		  
			String sWsName = formObject.getValue(CURR_WS_NAME).toString();
			String sriskColumn = "SNO,WI_NAME,WS_NAME,CUST_CUR_RISK_BANK";
			String sriskValue = "'" + (iSelectedRow+1) + "','"+ sWorkitemId + "','" + sWsName + "','"+ sOutput + "'";
			logInfo("SUBMIT BUTTON", "sriskColumn.."+ sriskColumn);
			logInfo("SUBMIT BUTTON", "sriskValue.."+ sriskValue);
			insert_Into_Usr_0_Risk_Data(sriskColumn,sriskValue);
			String sUpdateDecision = "update USR_0_CUST_TXN set CUST_INDI_RISK='"
					+ sOutput + "' Where WI_NAME='" + sWorkitemId+ "' AND CUST_SNO ='" + iProcessedCustomer + "'";
			formObject.saveDataInDB(sUpdateDecision);
		}
		return true;
	}
	
	private boolean submitQDEValidation() {
		logInfo("submitQDEValidation","submitWorkitem condition");
		boolean prodChangeFlag = checkProdChngForNoEligibility();
		if (!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")
				|| formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject") || prodChangeFlag)) {
			logInfo("submitQDEValidation","inside if--------");
			sendMessageValuesList("","Customer is not eligible for cheque book. Please change the product.");
		}
		else if(sActivityName.equalsIgnoreCase("QDE_CUST_INFO")) {
			reKeyInsert();
			updateReKeyTemp("CRO");
			createHistory();
			createAllHistory();
			updateProfitCentre();
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") || formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") || formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("null")) {
				//formObject.setValue("AO_CRO_DEC", "Approve");
				formObject.setValue(CRO_DEC, "Approve");
			}
			String sUpdateDecision="update "+sExtTable+" set wi_completed_from='"+ sActivityName +"' Where WI_NAME='"+ sWorkitemId +"'";
			formObject.saveDataInDB(sUpdateDecision);
			try {
				String sQuery1="SELECT COUNT(A.WI_NAME) IS_MOB_CHANGE FROM USR_0_CUST_TXN A, ACC_RELATION_REPEATER B WHERE A.WI_NAME='"+ sWorkitemId +"' AND A.WI_NAME=B.WI_NAME  AND A.CUST_SNO =B.SNO AND B.BANK_RELATION='Existing' AND A.final_mobile_no <> nvl(A.fcr_mobile_no,0) and a.final_mobile_no <> nvl(a.AFTER_CONT_CNTR_MOB_NO,0)";
				List<List<String>> output=formObject.getDataFromDB(sQuery1);
				String isMobChange= output.get(0).get(0);
				if(Integer.parseInt(isMobChange)>0) {
					formObject.setValue(MOBILE_CHANGE_FLAG,"True");
				} else {
					formObject.setValue(MOBILE_CHANGE_FLAG,"False");
				}
			} catch(Exception e) {
				logError("submitQDEValidation",e);
			}
		}
		return true;
	}
	
	private boolean saveAndNextValidationQDE(String data) {
		logInfo("saveAndNextValidations", "INSIDE, data: "+data);
		int sheetIndex = Integer.parseInt(data);
		String sAccRelation = "";
		String sBankRelation = "";
		try {
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			sAccRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 9);
			sBankRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
		} catch(Exception e) {
			logError("saveAndNextValidations", e);
		}
		if(sheetIndex == 10){
			
			logInfo("saveAndNextValidation", "decision");
			formObject.clearCombo(CRO_DEC);
			int iCount =getListCount(CRO_DEC);
			if(iCount == 0) {
				String sQuery1 = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name="
						+ "'"+formObject.getValue(CURR_WS_NAME).toString()+"'"
						+ " order by to_char(WS_DECISION)";
				addDataInComboFromQuery(sQuery1, CRO_DEC);
			}
			if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
				formObject.removeItemFromCombo(CRO_DEC, 4); //added by krishna for upgrade
			}
			//Jamshed
			if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)){
				String vig_query = "select pri_cust_segment,final_risk_val from ext_ao where wi_name='"+sWorkitemId+ "'";
				logInfo("saveAndNextValidationQDE","vig_query= "+vig_query);
				List<List<String>> vig_list=formObject.getDataFromDB(vig_query);
				String pri_cust_segment="";
				String final_risk_val="";
				if (vig_list != null && vig_list.size() > 0){
					pri_cust_segment=vig_list.get(0).get(0);
					final_risk_val=vig_list.get(0).get(1);
				}
				logInfo("saveAndNextValidationQDE","pri_cust_segment, final_risk_val <==> "+pri_cust_segment+", "+final_risk_val);
				/*if(pri_cust_segment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
					formObject.addItemInCombo(CRO_DEC, "Send To Vigilance");
					logInfo("saveAndNextValidationQDE","Send To Vigilance Added>>>>>>>");
				}*/
				if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade") ||
				     formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") || 
					 formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Family Banking"))){
					
					if(! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") &&  
							! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ){
				if(pri_cust_segment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
					formObject.addItemInCombo(CRO_DEC, "Send To Vigilance");
							logInfo("onSaveTabClickDDE","Send To Vigilance Added>>>>>>>");
						}
					}else{
						String newSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
						if(newSegment!=null && !newSegment.equalsIgnoreCase("") &&
								newSegment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
							formObject.addItemInCombo(CRO_DEC, "Send To Vigilance");
							logInfo("onSaveTabClickDDE","Send To Vigilance Added>>>>>>>");
						}
					}
				}
			}
		}
		
		if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_CUST_INFO)){
			if(sheetIndex == 1) {
				logInfo("saveAndNextValidations", "sheet 1");
				if(!ValidateComparisonDataCombo(CHECKBOX_PREFIX_FCR,CHECKBOX_PREFIX_EIDA,CHECKBOX_PREFIX_MANUAL,FCR_PREFIX,
						EIDA_PREFIX,MANUAL_PREFIX,CA037,"Mandatory","Prefix")) {
					return false;
				}
				if(formObject.getValue(MANUAL_FIRSTNAME).toString().trim().equalsIgnoreCase("")) {
					sendMessageValuesList(MANUAL_LASTNAME,"Please Fill First Name.");
					return false;
				} 
				if(formObject.getValue(MANUAL_LASTNAME).toString().trim().equalsIgnoreCase("")) {
					sendMessageValuesList(MANUAL_LASTNAME,"Please Fill Last Name.");
					return false;
				} 
				if(!ValidateComparisonData(CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,
						EIDA_NAME,MANUAL_NAME,CA011,"Mandatory","Full Name")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,
						FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME,CA0184,"Mandatory","Short Name")) {
					return false;
				}
				String sShortName =getFinalDataComparison(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME).trim();
				if(sShortName !=null  && sShortName.length() > 20){
					sendMessageValuesList(MANUAL_SHORTNAME, "Short name length must be less than or equal to 20");
					return false;
				}
				if(!ValidateName(CHECKBOX_FULLNAME_FCR,CHECKBOX_FULLNAME_EIDA,CHECKBOX_FULLNAME_MANUAL,FCR_NAME,EIDA_NAME,
						MANUAL_NAME,CA0134,CA0138)) {
					return false;
				}
				//Added by Shivanshu ATP-472
				if(!ValidateName(CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_FIRSTNAME_MANUAL,
						FCR_FIRSTNAME,EIDA_FIRSTNAME,MANUAL_FIRSTNAME,"First Name "+CA0205,"First Name "+CA0206)) {
					return false;
				}
				if(!ValidateName(CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL,
						FCR_LASTNAME,EIDA_LASTNAME,MANUAL_LASTNAME,"Last Name "+CA0205,"Last Name "+CA0206)) {
					return false;
				}
				if(!ValidateName(CHECKBOX_SHORTNAME_FCR,CHECKBOX_SHORTNAME_EIDA,CHECKBOX_SHORTNAME_MANUAL,
						FCR_SHORTNAME,EIDA_SHORTNAME,MANUAL_SHORTNAME,"Short Name "+CA0205,"Short Name "+CA0206)) {
					return false;
				}
				//END ATP-472
				if(sBankRelation.equalsIgnoreCase("New")) {
					if(!ValidateComparisonData(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,
							CHECKBOX_MOTHERSNAME_MANUAL,FCR_MOTHERSNAME,EIDA_MOTHERNAME,
							MANUAL_MOTHERNAME,CA0123,"Mandatory","Mother Name")) {
						return false;
					}
				} else {
					if(!ValidateComparisonData(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL,FCR_MOTHERSNAME,EIDA_MOTHERNAME,
							MANUAL_MOTHERNAME,CA0123,"Optional","Mother Name")) {
						return false;
					}
				}
				if(!ValidateName(CHECKBOX_MOTHERSNAME_FCR,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_MOTHERSNAME_MANUAL,
						FCR_MOTHERSNAME,EIDA_MOTHERNAME,MANUAL_MOTHERNAME,CA0139,CA0140)) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,
						EIDA_EIDANO,MANUAL_EIDANO,CA0167,"Optional","EIDA number")) {
					return false;
				}
				if(!validateEidaNo(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,MANUAL_EIDANO,CA0171)) {
					return false;
				}	
				if(!ValidateComparisonData(CHECKBOX_CORR_POB_FCR,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL,
						FCR_ADDRESS,EIDA_ADDRESS,MANUAL_ADDRESS,CA048,"Mandatory","PO Box")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,
						CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,EIDA_CNTRY,MANUAL_CNTRY,
						CA020,"Mandatory","Country of Correspondence Address")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKFCR,CHECKEIDA,CHECKMANUAL,FCR_RESIDENT,EIDA_RESIDENT,
						MANUAL_RESIDENT,CA0155,"Mandatory","Residential Address Country")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,
						FCR_COUNTRYBIRTH,EIDA_COUNTRYBIRTH,MANUAL_COUNTRYBIRTH,CA0178,"Mandatory","Country of Birth")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,
						CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,EIDA_PER_CNTRY,
						MANUAL_PER_CNTRY,CA074,"Mandatory","Country of Permanant Residence")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_CNTRY_OF_CORR_MANUAL,FCR_CNTRY,EIDA_CNTRY,
						MANUAL_CNTRY,CA0181,"Mandatory","Country of Correspondence address")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_COUNTRY_PER_RES_MANUAL,FCR_PER_CNTRY,
						EIDA_PER_CNTRY,MANUAL_PER_CNTRY,CA0182,"Mandatory","Country of Permanent address")) {
					return false;
				}
				if(!ValidateComparisonDataComboForDot(CHECKFCR,CHECKEIDA,CHECKMANUAL,FCR_RESIDENT,
						EIDA_RESIDENT,MANUAL_RESIDENT,CA0183,"Mandatory","Country of Residence address")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,FCR_PH,EIDA_PH,MANUAL_PH,CA057,
						"Optional","Phone number")) {
					return false;
				}
				if(flag_phone_start.equalsIgnoreCase("true")){
					if(!ValidateMobileNoStart(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH,"Mandatory",CA0161,"Residence Phone Number")) {
						return false;
					}
				}
				if(!ValidatePhoneNo(CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_RES_MANUAL,FCR_PH,EIDA_PH,MANUAL_PH,CA0127)) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,
						CA059,"Mandatory","Mobile number")) {
					return false;
				}
				if(flag_phone_start.equalsIgnoreCase("true")) {
					if(!ValidateMobileNoStart(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE,"Optional",CA0161,"Mobile Number ")) {
						return false;
					}
				}
				if(!validateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
						FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA0126)) {
					return false;
				}	
				if(sBankRelation.equalsIgnoreCase("New")) {
					if(!ValidateComparisonData(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,FCR_EMAIL,EIDA_EMAIL,MANUAL_EMAIL,
							CA054,"Optional","Email ID")) {
						return false;
					}
				} else {
					if(!ValidateComparisonData(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,FCR_EMAIL,EIDA_EMAIL,MANUAL_EMAIL,
							CA054,"Optional","Email ID")) {
						return false;
					}
				} 
				if(!ValidateComparisonData(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB,
						CA012,"Mandatory","Date of Birth")) {
					return false;
				}
				if(!validateDOB(CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL,FCR_DOB,EIDA_DOB,MANUAL_DOB)) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASSPORT_NO_MANUAL,FCR_PASSPORTNO,EIDA_PASSPORTNO,
						MANUAL_PASSPORTNO,CA0120,"Mandatory","Passport number")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL,FCR_PASSPORTISSDATE,
						EIDA_PASSPORTISSDATE,MANUAL_PASSPORTISSDATE,CA0121,"Mandatory","Passport Issue Date")) {
					return false;
				}
				if(!validateFutureDates(CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_ISS_DT_MANUAL,FCR_PASSPORTISSDATE,
						EIDA_PASSPORTISSDATE,MANUAL_PASSPORTISSDATE,"Passport Issue")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL,FCR_PASSPORTEXPDATE,
						EIDA_PASSPORTEXPDATE,MANUAL_PASSPORTEXPDATE,CA0122,"Mandatory","Passport Expiry Date")) {
					return false;
				}
				if(!validatePastDates(CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_PASS_EXP_DT_MANUAL,FCR_PASSPORTEXPDATE,
						EIDA_PASSPORTEXPDATE,MANUAL_PASSPORTEXPDATE,"Passport Expiry")) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_NATIONALITY_FCR,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_NATIONALITY_MANUAL,FCR_NATIONALITY,
						EIDA_NATIONALITY,MANUAL_NATIONALITY,CA013,"Mandatory","Nationality")) {
					return false;
				}	
				if(!ValidateComparisonData(CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_NO_MANUAL,FCR_VISANO,EIDA_VISANO,MANUAL_VISANO,
						CA0135,"Optional","Visa No")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL,FCR_VISAISSDATE,EIDA_VISAISSDATE,
						MANUAL_VISAISSDATE,CA0136,"Optional","Visa Issue Date")) {
					return false;
				}
				if(!validateFutureDates(CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_ISSUE_DATE_MANUAL,FCR_VISAISSDATE,EIDA_VISAISSDATE,
						MANUAL_VISAISSDATE,"Visa Issue")) {
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,FCR_VISAEXPDATE,EIDA_VISAEXPDATE,
						MANUAL_VISAEXPDATE,CA0137,"Optional","Visa Expiry Date")) {
					return false;
				}
				if(!validatePastDates(CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,FCR_VISAEXPDATE,EIDA_VISAEXPDATE,
						MANUAL_VISAEXPDATE,"Visa Expiry")) {	
					return false;
				}		
				if(!sAccRelation.equalsIgnoreCase("Minor")) {
					if(!ValidateComparisonDataCombo(CHECKBOX_PROFESSION_FCR,CHECKBOX_PROFESSION_EIDA,CHECKBOX_PROFESSION_MANUAL,FCR_PROFESSION,
							EIDA_PROFESSION,MANUAL_PROFESSION,CA075,"Mandatory","Profession")) {
						return false;
					}
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_GENDER_FCR,CHECKBOX_GENDER_EIDA,CHECKBOX_GENDER_MANUAL,FCR_GENDER,EIDA_GENDER,
						MANUAL_GENDER,CA041,"Mandatory","Gender")) { 
					return false;
				}
				if(!ValidateComparisonData(CHECKBOX_EMP_NAME_FCR,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_EMP_NAME_MANUAL,FCR_EMPLYR_NAME,EIDA_EMPLYR_NAME,
						MANUAL_EMPLYR_NAME,CA0145,"Optional","Employer Name")) {
					return false;
				}
				if(!validatePassportType(CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,FCR_PASSTYPE,EIDA_PSSTYPE,
						MANUAL_PASSTYPE,CA018,HD_PASS_TYPE)) {
					return false;
				}
				if(!ValidateComparisonDataCombo(CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,
						CHECKBOX_VISA_STATUS_MANUAL,FCR_VISASTATUS,EIDA_VISASTATUS,
						MANUAL_VISASTATUS,CA019,"Mandatory","Visa Status")) {
					return false;
				}
				int sSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;				
				String sQuery1 = "SELECT IS_DEDUPE_CLICK FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' and "
						+ "cust_sno='"+sSelectedRow+"'";
				logInfo("saveAndNextValidations"," sQuery1 dedupeDone: "+sQuery1);
				List<List<String>> output1 = formObject.getDataFromDB(sQuery1);
				logInfo("saveAndNextValidations"," output1 dedupeDone"+output1);
				String dedupeDone = "false";
				if(output1.size()>0) {
					dedupeDone = output1.get(0).get(0);
				}
				logInfo("saveAndNextValidations","dedupeDone"+dedupeDone);
				if(!dedupeDone.equalsIgnoreCase("true")) {
					sendMessageValuesList(BTN_DEDUPE_SEARCH,"Please Do Dedupe Search For This Customer.");
					return false;
				}
				if(!validateVisaStatus()) {
					return false;
				}
				/*if(!validateMobileNo(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,
						FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE,CA0126)) {
					if(!mobileConfirmFlag || mobileChangeFlag) {
						return false;
					}
				}*/
			} else if(sheetIndex == 2) {
				logInfo("saveAndNextValidations", "sheet 2");
				if(formObject.getValue(VISA_STATUS).toString().equalsIgnoreCase("Residency Visa")
						&& formObject.getValue(DRP_RESEIDA).toString().equalsIgnoreCase("")) {
					sendMessageValuesList(DRP_RESEIDA,"Please select Value of Resident without EIDA");
					return false;
				}
				if(formObject.getValue(DRP_RESEIDA).toString().equalsIgnoreCase("no") 
						&& formObject.getValue(VISA_STATUS).toString().equalsIgnoreCase("Residency Visa")) {
					if(!ValidateComparisonData(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,
							MANUAL_EIDANO,CA0167,"Mandatory","EIDA number")) {
						return false;
					}
				}
				if(!mandatoryiKYC()) {
					return false;
				}
				if(!checkSalaryTransfer()){
					return false;
				}
				/*if(formObject.getValue(CITYBIRTH_MANUAL).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(CITYBIRTH_MANUAL,"Please enter city of birth");
				return true;
			}*/
			} else if(sheetIndex == 4) {
				logInfo("saveAndNextValidations", "sheet 3");
				/*if(formObject.getValue(CITYBIRTH_MANUAL).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(CITYBIRTH_MANUAL,"Please enter city of birth");
				return true;
			}*/
				if(!mandatoryAtFatca()) {
					return false;
				}
				if (!validateCustomerClassification()) {
					return false;
				}
			} else if(sheetIndex == 3) {
				logInfo("saveAndNextValidations", "sheet 4");
				String custSegment = formObject.getValue(CUST_SEGMENT1).toString();
				List<List<String>> output = formObject.getDataFromDB("select iscrsmandatory from usr_0_cust_segment "
						+ "where cust_segment='"+custSegment+"'");
				String ismandatory = "";
				if(output.size()>0) {
					ismandatory = output.get(0).get(0);
				}
				logInfo("saveAndNextValidations","CUST Segment result: "+ismandatory);
				boolean custSegmentCheck;
				if(ismandatory.equalsIgnoreCase("Yes"))
					custSegmentCheck=true;
				else
					custSegmentCheck=false;
				if(!mandatoryCRSCheck(custSegmentCheck)) {
					return false;
				}
				if(getGridCount(CRS_TAXCOUNTRYDETAILS) > 0 ) {
					for(int i=0 ; i<getGridCount(CRS_TAXCOUNTRYDETAILS);i++) {
						String countryOfTaxResidency=formObject.getTableCellValue(CRS_TAXCOUNTRYDETAILS,i,1);
						logInfo("saveAndNextValidations","Value of selected Tax Residency country"+ countryOfTaxResidency);
						if (countryOfTaxResidency.equalsIgnoreCase("UNITED ARAB EMIRATES")) {
							logInfo("Gautam","<<<< 67 >>>>>>>>>");
							if(!MandatoryCRSDueDiligence()) {
								return false;
							}
							break;
						}
					}
				}
			}
		} else if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO)) {
			logInfo("Inside SaveandNextValidationQDE","sActivityName: "+sActivityName);
			String sQuery  = "";
			List<List<String>> output = null;
			if(sheetIndex == 7) {  //Acct info tab
				// added just below condition 
				if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") ||
				     formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")||
				     formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){
					{
			    if(formObject.getValue(ACC_TITLE).toString().equalsIgnoreCase("")){
				     logInfo(BTN_SUBMIT,"REQUEST_TYPE->"+formObject.getValue(REQUEST_TYPE));
					 sendMessageValuesList(ACC_TITLE, "Please fill Account Title.");
					 return false;
					}				
					 int iRows = getGridCount(PRODUCT_QUEUE);	
					 boolean isEtihad = false;					
					 String sProdCode = "";
					 String sChequebook = "";
				 if(iRows < 1) {
				     sendMessageValuesList(PRODUCT_QUEUE, "Please add atleast one product.");
					 return false;
					}
					sQuery= "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
					output = formObject.getDataFromDB(sQuery);
					String sProduct = "";
					if(output.size() > 0) {
						for(int i=0; i<output.size(); i++) {
							sProduct = sProduct+output.get(i).get(0)+",";
						}
					}
					logInfo("submitDDEValidations", "product list offered: "+sProduct);
					for(int i=0;i<iRows;i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);
						sChequebook = formObject.getTableCellValue(PRODUCT_QUEUE, i, 6);
						logInfo("submitDDEValidations", "row: "+i+", sProdCode: "+sProdCode+", sChequebook: "
								+sChequebook);
						if(sProdCode.equalsIgnoreCase("")) {
							sendMessageValuesList(PRODUCT_QUEUE, "Blank Row is not allowed.");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						if(sProduct.indexOf(sProdCode) == -1) {
							sendMessageValuesList(PRODUCT_QUEUE,"Product code "
									+sProdCode+" is not matching in the offered product list");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						if(sChequebook.isEmpty()) {
							sendMessageValuesList(PRODUCT_QUEUE, "Please fill Chequebook required.");
							//formObject.setNGSelectedTab("Tab5",4); //handle in js
							return false;
						}
						formObject.setTableCellValue(PRODUCT_QUEUE, i, 14, String.valueOf(i+1));
					}
					/*for(int i=0; i<iRows; i++) {
						sProdCode = formObject.getTableCellValue(PRODUCT_QUEUE, i, 1);
						sQuery = "SELECT SUB_PRODUCT_TYPE FROM USR_0_PRODUCT_TYPE_MASTER WHERE PRODUCT_CODE ='"+sProdCode+"'";
						output = formObject.getDataFromDB(sQuery);
						if(output.size() > 0 && output.get(0) != null && !output.get(0).get(0).equalsIgnoreCase("")) {
							if(output.get(0).get(0).equalsIgnoreCase("Etihad")) {
								isEtihad = true;
								break;
							}
						}
					}
					if(isEtihad) {
						if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().isEmpty()) {
							sendMessageValuesList(EXISTING_ETIHAD_CUST, "Please Select Etihad Status.");
							return false;
						} else if(formObject.getValue(EXISTING_ETIHAD_CUST).toString().equalsIgnoreCase("Yes")) {
							if(formObject.getValue(ETIHAD_NO).toString().equalsIgnoreCase("")) {
								sendMessageValuesList(ETIHAD_NO,"Please fill Etihad Number.");
								return false;
							} else if(formObject.getValue(ETIHAD_NO).toString().equalsIgnoreCase("0")) {
								sendMessageValuesList(ETIHAD_NO,"Please validate Etihad Number.");
								return false;
							}
						}
					}*///ETIHAD ISSUE 
					if(formObject.getValue(SOURCE_NAME).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_NAME, "Please Select Source Name.");
						return false;
					}
					if(formObject.getValue(SOURCE_CODE).toString().equalsIgnoreCase("")) {
						sendMessageValuesList(SOURCE_CODE,"Please Select Source Code.");
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
					/*boolean result = validateDebitDetails();				
					if(!result) {
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}						
					try {
						if(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate")
								&& sActivityName.equalsIgnoreCase("DDE_Account_Info")) {
							if(formObject.getValue(INSTANT_DEL_YES).toString().equalsIgnoreCase("true") 
									&& formObject.getValue(DFC_STATIONERY_AVAIL).toString().equalsIgnoreCase("")) {
								sendMessageValuesList(DFC_STATIONERY_AVAIL,"Please select DFC Stationary Available");
								//edit button enable removed
								formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
								return false;
							}
							sQuery = "SELECT COUNT(1) AS COUNT FROM USR_0_AO_DEBITCARD WHERE LODGEMENT_REF_NO='"+formObject.getValue(LODGEMENT_NO)+"' and CARDTYPE is not null";
							output = formObject.getDataFromDB(sQuery);
							int sCount = Integer.parseInt(output.get(0).get(0));
							if(sCount != 0) {
								String sQuery2 = "SELECT COUNT(1) AS COUNT FROM DEBIT_CARD_REP WHERE WI_NAME='"+sWorkitemId+"'";
								output = formObject.getDataFromDB(sQuery);
								int sCount2 = Integer.parseInt(output.get(0).get(0));
								if(sCount2==0) {
									sendMessageValuesList("","User has requested debit card.");
								}
							}
						} 
					} catch(Exception e) {	
						logError("", e);
					}*///ETIHAD ISSUE
				}
			 }
				
			}else if(sheetIndex == 9) {// category change tab
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") || 
					formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||
				    formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")) {//Addded by krishna
					boolean resultCategory = mandatoryCategoryChangeData();
					if(!resultCategory) {
						return false;
					}
					resultCategory = checkNatCatSegment();
					if(!resultCategory) {
						return false;
					}
				}
			} else if(sheetIndex == 11) { // Family Banking 
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase
						("New Account with Category Change") || 
						formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase
						("Category Change Only")) {
					/*if(validateFBFetch()) {
						isValidateFBDone();
					}*///FB SUPPRESSED
					if(validateFBFetch()) {
						isValidateFBDone();
					}//FB ADDED
				}
			}
		}
		return true;
	}

}
