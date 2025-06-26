package com.newgen.iforms.user.ao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class ContactCenterCPD extends Common implements Constants, IFormServerEventHandler{

	public ContactCenterCPD(IFormReference formObject) {
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
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		log.info("Inside executeServerEvent ContactCenterTeam >");
		log.info("Event: " + eventType + ", Control Name: " + controlName + ", Data: " + data);
		sendMessageList.clear(); 
		String retMsg = getReturnMessage(true,controlName);
		List<String> recordList = null;
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				if (CONTROL_NAME_FORM.equalsIgnoreCase(controlName)) {
					log.info("Inside event type load");
					prefLang();
					frameDelivery();
					onFormLoadData();
					int iSelectedRow = Integer.parseInt((SELECTED_ROW_INDEX).toString());
					logInfo("EVENT_TYPE_LOAD", "iSelectedRow: "+iSelectedRow);
					String sName = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 1);
					String sDOB = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 5);
					String sCustId = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
					formObject.setValue(TXT_CUSTOMERNAME, sName);
					formObject.setValue(TXT_DOB, sDOB);
					formObject.setValue(TXT_CUSTOMERID, sCustId);
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabClick(data);
				} else if(BTN_SUBMIT.equalsIgnoreCase(controlName)){
					log.info("Entered Done Event"+sActivityName);
					int reply;
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") || formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("--Select--")) {
						log.info("value in decision is NULL");
						sendMessageValuesList(CRO_DEC,"Please select user decision.");
						return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					} 
				}else if("postSubmit".equalsIgnoreCase(controlName)){
					createHistory();
					createAllHistory();
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")){
						String sUpdateDecision = "update "+sExtTable+" set mobile_change_flag = 'False' Where WI_NAME = '"+sWorkitemId+"'";
						formObject.saveDataInDB(sUpdateDecision);
						log.info("sUpdateDecision::"+sUpdateDecision);
					}
					try {
						int count = getGridCount(ACC_RELATION);
						if(count>0) {
							for(int i = 0;i<count;i++) {
								log.info("i = "+i);
								int sno = i;
								String bankRelation = formObject.getTableCellValue(ACC_RELATION, sno, 7);//objChkRepeater.getValue(sno, "ACC_RELATION.BANK_RELATION");
								if(bankRelation.equalsIgnoreCase("Existing")) {
									String sUpdateMobNo = "UPDATE USR_0_CUST_TXN SET "
											+ "AFTER_CONT_CNTR_MOB_NO = (SELECT FINAL_MOBILE_NO FROM USR_0_CUST_TXN "
											+ "WHERE WI_NAME = '"+sWorkitemId+"' AND CUST_SNO = '"+sno+"') "
											+ "WHERE wi_name = '"+sWorkitemId+"' AND CUST_SNO = '"+sno+"'";
									formObject.saveDataInDB(sUpdateMobNo);
									log.info("sUpdateMobNo::"+sUpdateMobNo);
								}
							}
						}
					}
					catch (Exception e) {			
						log.error("Exception: ",e);
					}
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") || formObject.getValue(CRO_DEC).equals(null)) {
						formObject.setValue(CRO_DEC, "Approve");
					}
					String sUpdateDecision="update "+sExtTable+" set wi_completed_from='"+ sActivityName +"' "
							+ "Where WI_NAME='"+ sWorkitemId +"'";
					formObject.saveDataInDB(sUpdateDecision);
					log.info("AT accrelation CALLING FOR EMAIL DATE  :");
					String sProcName = "SP_TemplateGenerationEmailDt";
					List<String> paramlist = new ArrayList<String>();
					paramlist.add ("Text :"+sWorkitemId);
					List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
					log.info("Output AT accrelation CALLING FOR EMAIL DATE  :"+sOutput1);
					return getReturnMessage(true, controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
				}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally {
			logInfo("onCCTCPDExcuteServerEvent","sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()) {
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
			}
		}
		return retMsg;		
	}

	public  void onFormLoadData(){
		frame81_CPD_Disable();
		String onLoadCCTDisable[] = {BTN_TRSD_CHECK,BTN_RE_CHECK,FINAL_ELIGIBILITY,RISK_ASSESS_MARKS,BTN_CPD_TRSD_CHK,BTN_CPD_RE_CHK,CPD_FINAL_ELIGIBILITY,CPD_RISK_ASSESS_MARKS,
				BTN_CALCULATE,BTN_NEXT_CUST,CRS_CB,CRS_RES_PERM_ADRS_US,CHECKBOX_FATCA,CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,
				CHK_RISK_ASS,CHK_BANKING_RELATION,CK_PER_DET,PD_ANY_CHNG_CUST_INFO,CHK_CONTACT_DET,CHK_INTERNAL_SEC,BTN_SUBMIT};
		disableControls(onLoadCCTDisable);
		log.info("inside onFormLoadData");
		String sQuery = "select FCR_MOBILE_NO,manual_mobile_no from usr_0_cust_txn where wi_name = '"+sWorkitemId+"' and cust_sno = 1 ";
		List<List<String>> record1 = formObject.getDataFromDB(sQuery);
		log.info(sQuery);
		if (record1 !=  null && !record1.isEmpty()) {
			String oldMobileNo = record1.get(0).get(0);
			String newMobileNo = record1.get(0).get(1);
			formObject.setValue("OLD_MOBILE_NO",oldMobileNo);
			formObject.setValue("NEW_MOBILE_NO",newMobileNo);
			formObject.setStyle("OLD_MOBILE_NO",DISABLE,TRUE);
			formObject.setStyle("NEW_MOBILE_NO",DISABLE,TRUE);
		}
		disableControls(new String[]{SEC_CI,SEC_SRCH_EXIST_CUST,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,SEC_SI,
				SEC_DEL_INST,SEC_INTERNAL_DETL,SEC_PERSONAL_DET,Frame_ClientQuestions,SEC_CONTACT_DET_CP,SEC_CONTACT_DET_PA,
				SEC_CONTACT_DET_RA,SEC_INT_DETAIL,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,SEC_FUND_EXP_RELTNSHP,SEC_ASSESS_OTH_INFO,
				SEC_BNK_REL_UAE_OVRS,SEC_SIGN_OFF,FrameFATCA,SEC_CRS_DETAILS,SEC_SS_CPD_TRSD,SEC_SS_CPD_RISK_ASSESS,
				SEC_SS_TRSD,SEC_SS_RISK_ASSESS,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS,SEC_PRODUCTION_CRO,SEC_OPT_PROD_CRO,
				SEC_FACILITY_CRO,SEC_DOC_REQ_CRO,SEC_RISK_ASSESS_CPD,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CPD,
				SEC_OPT_PROD_CPD,SEC_FACILITY_CPD,SEC_DOC_REQ_CPD,SEC_ACC_INFO_PD,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,SEC_SI_SIRFT,SEC_SI_SWP});
		clearUdfGrid();
		int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		log.info("field valuebagset custNO: "+custNo);
		String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO = "+custNo+" AND WI_NAME = '"+sWorkitemId+"'";
		populateUDFGrid(sQuery2);
		populateTRSD();
		populateTRSDCPD();
		setTemp_usr_0_product_selected();
		fieldValueUsr_0_Risk_Data();
	}

	@Override
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1,	String arg2) {
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

	public void onTabClick(String data) {
		try {
			logInfo("onTabClick", "INSIDE");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || selectedSheetIndex == 4 || selectedSheetIndex == 5) {	
				logInfo("onTabClick", "INSIDE 1,2,3,4,5");
				disableControls(new String[]{BTN_SUBMIT});
				setManualFieldsBlank();
				setManualChecksBlank();
				clearComparisonFields();
				clearPersonalData();
				clearKYCData();
				clearRiskData();
//				setCPDCheckerCombos();
				populatePersonalDataCPD(); 
				populateRiskData();
				populateKYCData();
				populateKycMultiDrop();
				populatePreAssesmentDetails();  //shahbaz
				populateComparisonFields();
				PopulatePrivateClientQuestions(); 
				loadDedupeSearchData(sWorkitemId);
				visibleOnSegmentBasis(formObject.getValue(PD_CUSTSEGMENT).toString());
			} else if(selectedSheetIndex==6) {
				logInfo("onTabClick", "INSIDE 6");
				disableControls(new String[]{BTN_SUBMIT});
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					populateScreeningDataCRO();
				} /*else {
					populateScreeningDataCPD();
					//formObject.setNGSelectedTab("Tab2",1);
				}*/
				populateTRSD();
				/*if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")) {
					formObject.setValue(CPD_MTCH_FOUND, "Eligible");
				} else {
					formObject.setValue(CPD_MTCH_FOUND, "Not Eligible"); 
				}*/
				if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")) {
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
				} else {
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible"); 
				}
			} else if(selectedSheetIndex==7) {
				populateScreeningDataCPD();
				populateTRSDCPD();
			} else if(selectedSheetIndex==8) {
				logInfo("onTabClick", "INSIDE 7");
				disableControls(new String[]{BTN_SUBMIT});
				String sQuery= "";
				List<List<String>> recordList = null;
				/*if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					loadApplicationAssessmentDataCPD();		
					if(getGridCount(PROD_SEC_OFRD_CPD_LVW) == 0) {
						sQuery = "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
						recordList = formObject.getDataFromDB(sQuery);
						log.info(sQuery);
						loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,PROD_ACCT_OPNG",PROD_SEC_OFRD_CPD_LVW);
					} 
					if(getGridCount(FAC_LVW_CPD) == 0) {
						String sQuery1 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
						recordList = formObject.getDataFromDB(sQuery1);
						log.info(sQuery1);
						loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
					}
					if(getGridCount(FAC_OFRD_LVW_CPD) == 0) {
						String sQuery2  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
						recordList = formObject.getDataFromDB(sQuery2);
						log.info(sQuery2);
						loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
					}
				} else {
					log.info("inside else block of loadApplicationAssessmentData ");
					if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
						String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
						recordList = formObject.getDataFromDB(sQuery3);
						log.info(sQuery3);
						loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
					}
					if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
						String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
						recordList = formObject.getDataFromDB(sQuery4);
						log.info(sQuery4);
						loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
					}
					if(getGridCount(FAC_LVW_CRO) == 0) {
						String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
						recordList = formObject.getDataFromDB(sQuery5);
						log.info(sQuery5);
						loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
					}
					loadApplicationAssessmentData();
				}*/
				log.info("loadApplicationAssessmentData ");
				if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
					String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
					recordList = formObject.getDataFromDB(sQuery3);
					log.info(sQuery3);
					loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
				}
				if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
					String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
					recordList = formObject.getDataFromDB(sQuery4);
					log.info(sQuery4);
					loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
				}
				if(getGridCount(FAC_LVW_CRO) == 0) {
					String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
					recordList = formObject.getDataFromDB(sQuery5);
					log.info(sQuery5);
					loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
				}
				loadApplicationAssessmentData();
			} else if(selectedSheetIndex==9) {
				/*if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					loadApplicationAssessmentData();
				} */
				disableControls(new String[]{BTN_SUBMIT});
				String sQuery= "";
				List<List<String>> recordList = null;
				loadApplicationAssessmentDataCPD();		
				if(getGridCount(PROD_SEC_OFRD_CPD_LVW) == 0) {
					sQuery = "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED_CPD "
							+ "WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
					recordList = formObject.getDataFromDB(sQuery);
					log.info(sQuery);
					loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",
							PROD_SEC_OFRD_CPD_LVW);
				} 
				if(getGridCount(FAC_LVW_CPD) == 0) {
					String sQuery1 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED_CPD WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
					recordList = formObject.getDataFromDB(sQuery1);
					log.info(sQuery1);
					loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CPD);
				}
				if(getGridCount(FAC_OFRD_LVW_CPD) == 0) {
					String sQuery2  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
					recordList = formObject.getDataFromDB(sQuery2);
					log.info(sQuery2);
					loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CPD);
				}
			} else if(selectedSheetIndex==10) {
				logInfo("onTabClick", "INSIDE 8");
				disableControls(new String[]{BTN_SUBMIT});
				log.info("inside on click tab 9 Account Info ");
				int gridCount = getGridCount(ACC_INFO_EDC_LVW);
				log.info("COUNT OF GRID = "+gridCount);
				if(gridCount ==  0) {
					String sQuery6  =  "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(sQuery6);
					log.info("query for account info " +sQuery6);
					loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE",ACC_INFO_EDC_LVW);	
				}
			} else if(selectedSheetIndex==14) {
				logInfo("onTabClick", "INSIDE 14");
				disableControls(new String[]{BTN_SUBMIT});
				loadSICombos();
				populateStndInstr();
			} else if(selectedSheetIndex == 12) {
				Frame_delivery();
			} else if(selectedSheetIndex == 18) {
			} else if(selectedSheetIndex == 19) {	// Changes for Family Banking
				String query = "";
				logInfo("onTabClick", "INSIDE 19");
				enableControls(new String[]{BTN_SUBMIT});
				hideControls(new String[]{L1_APP_REQ,L2_APP_REQ,L2_APP_REQ,L2_APP_REQ});
				disableControls(new String[]{IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ,IS_CALL_BACK_REQ});
				
				formObject.clearCombo(CRO_DEC);
				int iCount1 = getListCount(CRO_DEC);
				logInfo("ontabclickcct","iCount"+iCount1);
				if(iCount1==0 || iCount1==1) {
					query = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name "
							+ "= '"+formObject.getValue(CURR_WS_NAME).toString()+"'";
					addDataInComboFromQuery(query, CRO_DEC);
				}
				/*formObject.clearCombo(CRO_REJ_REASON);
				int iCount = getListCount(CRO_REJ_REASON);
				logInfo("ontabclickcct","iCount1"+iCount);
				if(iCount==0 || iCount==1){
					query = "Select to_char(ws_rej_reason) from usr_0_rej_reason_mast";
					addDataInComboFromQuery(query, CRO_REJ_REASON);
				}*/ // newly commented

				
				/*if(getGridCount(DECISION_LVW) == 0) {
					String sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery);
					log.info("decision history query "+sQuery);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);							
				}*/
				int sSelectedCust = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				String sQuery = "select FCR_MOBILE_NO,manual_mobile_no from usr_0_cust_txn where wi_name ='"+sWorkitemId+"' and cust_sno = "+sSelectedCust+" ";
				List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
				if(sOutput!=null && sOutput.size()>0){
					String fcrMobileNo = sOutput.get(0).get(0);
					String manualMobileNo = sOutput.get(0).get(1);
					formObject.setValue("OLD_MOBILE_NO",fcrMobileNo);
					formObject.setValue("NEW_MOBILE_NO",manualMobileNo);
				}
			} else if(selectedSheetIndex == 13) {
				manageCategoryChangeSectionCPDChecker();
			}
		} catch (Exception e) {
			logInfo("onTabClick","tab details: "+data);
			logError("onTabClick", e);
		}
	}

	public void  manageCategoryChangeSectionCPDChecker(){	
		if(formObject.getValue(NEW_CUST_SEGMENT) == "Aspire"|| formObject.getValue(NEW_CUST_SEGMENT) == "Simplylife") {
			showControls(new String[]{IS_VVIP,IS_OTHERS_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
					IS_INSURANCE_CAT_CHANGE});
			hideControls(new String[]{IS_EXCELLENCY_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
					IS_PREVILEGE_TP_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE});
		} else if(formObject.getValue(NEW_CUST_SEGMENT) == "Emirati Excellency" || formObject.getValue(NEW_CUST_SEGMENT) == "Excellency"||formObject.getValue(NEW_CUST_SEGMENT) == "Private Clients") {
			showControls(new String[]{IS_MORTAGAGE_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
					IS_ENTERTAINMENT_CAT_CHANGE,IS_PREVILEGE_TP_CAT_CHANGE,IS_VVIP,IS_OTHERS_CAT_CHANGE,IS_TRB_CAT_CHANGE,
					IS_INSURANCE_CAT_CHANGE,IS_SALARY_TRANSFER_CAT_CHANGE});
			hideControls(new String[]{IS_EXCELLENCY_TP_CAT_CHANGE});
		} else if( formObject.getValue(NEW_CUST_SEGMENT) == "Signatory") {
			formObject.setValue(NEW_CUST_SEGMENT, TRUE);
		}	
	}

	public void populateScreeningDataCPD() {
		try {
			int iSelectedRow= 0;
			List<List<String>> recordList = null;
			String sCustNo="";
			iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1; 
			sCustNo=iSelectedRow+"";
			logInfo("populateScreeningDataCPD","In PopulateScreeningDataCPD iSelectedRow = "+iSelectedRow);
			String sQuery= "SELECT SYSTEM_DEC_CPD,BANK_DEC_CPD,BLACKLIST_DEC_CPD,WORLD_CHECK_DEC_CPD,BAD_CHECK_DEC_CPD, "
					+ "FINAL_ELIGIBILITY_CPD,BLACKLIST_REMARKS_CPD,BAD_CHECK_REMARKS_CPD,WORLD_CHECK_REMARKS_CPD,SYSTEM_REMARKS_CPD FROM USR_0_CUST_TXN WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO= "+sCustNo+"" ;
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			logInfo("populateScreeningDataCPD","sOutput-PopulateScreeningData--"+sOutput);
			String columns[] = {"SYS_DEC","CPD_BANK_DECISION",SANC_SCRN_MATCH_FOUND,SANC_FINAL_ELIGIBILITY,CPD_MATCH_FOUND,CPD_FINAL_ELIGIBILITY,CPD_CHK_REMARKS,CPD_REMARKS,CPD_WRLD_CHK_REMARKS,CPD_RISK_ASSESS_MARKS};
			if(sOutput!=null && sOutput.size()>0){
				setValuesFromDB(sOutput, columns);
			}
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,,BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' and CUST_SNO="+iSelectedRow+" AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE=INT)";
			logInfo("populateScreeningDataCPD","Loading Internal Blacklist---"+sQuery);
			recordList = formObject.getDataFromDB(sQuery);
			log.info(sQuery);
			loadListView(recordList,"Name,Nationality,DOB,Passport_No,Reason,Department",CPD_CHK_INT_BLK_LVW);
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,,BLACKLIST_TYPE FROM USR_0_BLACKLIST_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' and CUST_SNO="+iSelectedRow+" AND BLACKLIST_TYPE IN (SELECT CHECKTYPE FROM USR_0_CHECK_TYPE WHERE TYPE=EXT)";
			recordList = formObject.getDataFromDB(sQuery);
			log.info(sQuery);
			loadListView(recordList,"Name,Nationality,DOB,Passport_No,Expiry_Peroid,Department",CPD_HD2_LVW);
			sQuery ="SELECT CUST_NAME,NATIONALITY,DOB,PASSPORT_NO,EXPIRY_PERIOD,DEPARTMENT FROM USR_0_CENTRAL_BANK_DATA_CPD WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO = "+sCustNo+"";
			recordList = formObject.getDataFromDB(sQuery);
			log.info(sQuery);
			loadListView(recordList,"Name,Nationality,DOB,Passport_No,Expiry_Peroid,Department",CPD_CNTRL_BNK_BAD_LVW);	
			if(!sActivityName.equalsIgnoreCase("CPD Maker")) {
				sQuery ="SELECT CUST_ID,CUST_NAME,CURRENT_RISK_SYSTEM, CURRENT_RISK_BUSSINESS, PREVIOUS_RISK, FCR_RISK, APPROVAL_REQ,RISK_CLASSIFICATION,CPDRISK_CLASSIFICATION,cpdcust_assessment_date FROM USR_0_RISK_ASSESSMENT_DATA_CPD WHERE WI_NAME ='"+sWorkitemId+"' AND SNO = "+sCustNo+"";
				List<List<String>> sOutput1=formObject.getDataFromDB(sQuery);
				String columns1[] = {CPD_RISK_CID,CPD_RISK_NAME,CPD__RISK_CURRENT_RSK_SYSTEM,CPD_RISK_CURRENT_RSK_BANK,CPD_RISK_PREVIOUS_RSK,CPD_RISK_FCR_RSK,CPD_RISK_COMPL_APP_REQ,CPD_RISK_INITIAL_ASSESS_DATE,CPD_RISK_RSK_CLSF};
				if(sOutput1!=null && sOutput1.size()>0){
					setValuesFromDB(sOutput1, columns1);
				}
				set_Values_From_Usr_0_Risk_Data();
			}
		} catch(Exception e) {
			logError("Exception in onLoadCCT", e);
		}
	}

	public void populateTRSDCPD() {
		int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		String Query="select count(1)CNT from usr_0_trsd_details where wi_name='"+sWorkitemId+"' and acc_relation_sno="+iSelectedRow+"  and user_id='CPD' ";
		List<List<String>> out =formObject.getDataFromDB(Query);
		if(out.size()>0 && out!=null){
			if(!out.get(0).get(0).equalsIgnoreCase("0")) {
				Query="select TRSD_CASE_ID,FULL_NAME,TRSD_1_STATUS,TRSD_2_STATUS,decode(TRSD_APPROVALREQUIRED,Yes,Yes,No,No,Yes) as TRSD_APPROVALREQUIRED ,to_char(TRSD_SCREENINGDATE,dd/mm/yyyy hh:mi:ss)TRSD_SCREENINGDATE,TRSD_CHANNELREFNO,to_char(TRSD_ASSESSMENT_DATE,dd/mm/yyyy hh:mi:ss)TRSD_ASSESSMENTDATE from usr_0_trsd_details where wi_name='"+sWorkitemId+"' and acc_relation_sno="+iSelectedRow+" and user_id=CPD";
				out =formObject.getDataFromDB(Query);
				if(out.size() > 0) {
					formObject.setValue(CPD_TRSD_CASE_ID, out.get(0).get(0));
					formObject.setValue(CPD_TRSD_NAME, out.get(0).get(1));
					formObject.setValue(CPD_TRSD_SYS_CALC_RES, out.get(0).get(2));
					formObject.setValue(CPD_TRSD_FINAL_DECISION,out.get(0).get(3));
					formObject.setValue(CPD_TRSD_APPROVAL_REQUIRED, out.get(0).get(4));
					formObject.setValue(CPD_TRSD_CHANNEL_REF_NO,  out.get(0).get(5));
					trsdDate();
				}
			} else {
				formObject.setValue(CPD_TRSD_CASE_ID, "");
				formObject.setValue(CPD_TRSD_NAME, "");
				formObject.setValue(CPD_TRSD_SYS_CALC_RES, "");
				formObject.setValue(CPD_TRSD_FINAL_DECISION,"");
				formObject.setValue(CPD_TRSD_APPROVAL_REQUIRED,"");
				formObject.setValue(CPD_TRSD_ASSESSMENT_DATE,  "");
				formObject.setValue(CPD_TRSD_CHANNEL_REF_NO,"");
				formObject.setValue(CPD_TRSD_SCREENING_DATE,"");

			}
		}
		if((formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved") || formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Pending")) && checkTRSDDataChange()==true ){	
			formObject.setStyle(BTN_CPD_TRSD_CHK, "disable", "true");
		} else {
			formObject.setStyle(BTN_CPD_TRSD_CHK, "disable", "false");

		}
	}

	public boolean checkTRSDDataChange() {
		int sCustNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		String Query="select count(1) CNT from USR_0_MAKER_CHANGE_TRACKER where field_name in (FINAL_NATIONALITY,FINAL_GENDER,FINAL_DOB,FINAL_FULL_NAME,FINAL_LAST_NAME,FINAL_FIRST_NAME) and work_step=CPD Maker and wi_name='"+sWorkitemId+"' " +
				"AND CUST_SNO= "+sCustNo+" and "+
				" DT> (select MAX(TRSD_SCREENINGDATE) from usr_0_trsd_details where wi_name = '"+sWorkitemId+"'  AND ACC_RELATION_SNO = "+sCustNo+" AND user_ID=CPD)";
		List<List<String>> out =formObject.getDataFromDB(Query);
		if(out.size() > 0) {
			if(!out.get(0).get(0).equalsIgnoreCase("0")) {
				formObject.setStyle(BTN_CPD_TRSD_CHK, "disable", "false");
				return false;
			}
		}
		return true;
	}

	/*public void	populatePersonalDataCPD() {	
		String sameAsResi="";
		String sameAsPerm="";
		String otherCityResi="";
		String otherCityPerm="";
		if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_CUST_INFO)||sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO)||sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO_CHECK)||sActivityName.equalsIgnoreCase(ACTIVITY_APP_ASSESSMENT)||sActivityName.equalsIgnoreCase(ACTIVITY_CUST_SCREEN)){
			sameAsResi=RA_SAMEAS;
			sameAsPerm=PA_SAMEAS;
			otherCityResi=OTHER_PERM_CITY;
			otherCityPerm=OTHER_RESIDENTIAL_CITY;
		} else {
			sameAsResi=PA_SAMEAS;
			sameAsPerm=RA_SAMEAS;
			otherCityResi=OTHER_RESIDENTIAL_CITY;
			otherCityPerm=OTHER_PERM_CITY;
		}
		long start_Time1=System.currentTimeMillis();
		logInfo("PopulatePersonalDataCPD","Start Time in PopulatePersonalDataCPD---"+start_Time1);
		logInfo("PopulatePersonalDataCPD","In PopulatePersonalDataCPD");
		int sCustNo=0;
		mohit_flag=true;
		sCustNo=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
		String sCID = formObject.getTableCellValue(ACC_RELATION, sCustNo, 2);
		logInfo("PopulatePersonalDataCPD","sCustNo----"+sCustNo);
		String sQuery= "SELECT CUST_MARITAL_OTHERS,MAIDEN_NAME,CUST_SHORT_NAME,CURR_RELATION_TYPE,NEW_RELATION_TYPE, EIDA_NO,CUST_FULL_NAME,PREFIX_OTHER,CORR_PO_BOX,CORR_FLOOR,CORR_STREET,CORR_EMAIL,OTHER_CORR_CITY,CORR_PHONE,CORR_OFF_PHONE,CORR_MOB,RES_BUILDING,RES_VILLA,RES_LANDMARK,OTHER_RESI_CITY,PASS_NO, VISA_NO,PROFIT_CENTER_CODE,RFERRED_BY,REFFER_STAFF_ID,OTHER_PERM_CITY FROM USR_0_CUST_TXN WHERE WI_NAME= '"+sWorkitemId+"' AND CUST_SNO= "+sCustNo+"" ;
		formObject.getDataFromDB(sQuery);
		sQuery="SELECT PASS_TYPE, PER_MAKANI, RES_MAKANI,VISA_STATUS,CORR_MAKANI,SAME_AS_RESIDENTIAL,SAME_AS_PERMANENT,CORR_STATE,CORR_CITY,PER_CITY,RES_CITY,CORR_OTHER,CORR_CNTRY,CORR_CNTRY_CODE,CORR_OFF_CNTRY_CODE,CORR_MOB_CNTRY_CODE,RES_CNTRY,RES_STATE,RES_OTHER,PER_BUILDING,PER_VILLA,PER_LANDMARK,PER_STATE,PER_OTHER, PER_COUNTRY,CUST_MARITAL_STATUS,RELIGION,CUST_SEG,PREFIX, CUST_DOB, NATIONALITY, GENDER,DATE_ATTAINING_MAJORITY,  PASS_ISSUE_DATE, PASS_EXP_DATE, PASS_TYPE, VISA_ISSUE_DATE, VISA_EXP_DATE, VISA_STATUS,RM_CODE,RM_NAME, IS_SALARY_TRANSFER, IS_MORTAGAGE,IS_INSURANCE, IS_TRB,IS_OTHERS, IS_PREVILEGE_TP, IS_TRAVEL, IS_SPORT,IS_SHOPPING,IS_ENTERTAINMENT, IS_EXCELLENCY_TP,PROFESSION_CODE,PROMO_CODE,EXELLENCY_CENTER,IS_UPDATE_SIGN,IS_VVIP,IS_BENEFIT_OTHER,BENEFIT_OTHER,RES_EIDA FROM USR_0_CUST_TXN WHERE WI_NAME= '"+sWorkitemId+"' AND CUST_SNO= "+sCustNo+"" ;
		List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
		logInfo("PopulatePersonalDataCPD","Personal Data sOutput----"+sOutput);
		String columns1[] = {MANUAL_PASSTYPE,"","",MANUAL_VISASTATUS,"",sameAsResi,sameAsPerm,CORR_STATE,CP_CITY,
				PA_CITY,CP_OTHERS,CORR_CNTRY,"","","",RES_CNTRY,RES_STATE,PA_OTHERS,"","","","","","",
				MARITAL_STATUS,RELIGION,PD_CUSTSEGMENT,CUST_PREFIX,PD_DOB,CUST_NATIONALITY,CUST_GENDER,PD_DATEOFATTAININGMAT,HD_PASS_ISS_DATE,HD_PASS_EXP_DATE,HD_VISA_ISSUE_DATE,HD_EXP_DATE,RM_CODE,RM_NAME,"","","","","","","","","","","",PROF_CODE,"","",IS_SIGN_UPDATE,"",IDS_OTH_CB_OTHERS,IDS_BNFT_CB_OTHERS,DRP_RESEIDA};
		setValuesFromDB(sOutput,columns1);
		populateMakaniData();
		logInfo("PopulatePersonalDataCPD","66666 Combo4"+formObject.getValue(PD_CUSTSEGMENT));
		if(sOutput.size()==0 || sOutput==null) {
			sQuery= "SELECT IS_SALARY_TRANSFER, IS_MORTAGAGE,IS_INSURANCE, IS_TRB,IS_OTHERS, IS_PREVILEGE_TP, IS_TRAVEL, IS_SPORT,IS_SHOPPING,IS_ENTERTAINMENT,IS_EXCELLENCY_TP,PASS_TYPE,VISA_STATUS,PER_BUILDING, PER_VILLA,PER_LANDMARK,PER_CITY,PER_STATE,PER_OTHER,PER_COUNTRY,EXELLENCY_CENTER,PROMO_CODE FROM USR_0_CUST_WMS	 WHERE CUST_ID= "+sCID+"" ;
			sOutput=formObject.getDataFromDB(sQuery);
		}
		String columns[] = {IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,IDS_PC_CB_TP,IDS_PC_CB_TRAVEL,IDS_PC_CB_SPORT,IDS_PC_CB_SHOPPING,IDS_PC_CB_ENTERTAINMENT,IDS_BNFT_CB_TP,MANUAL_PASSTYPE,"COMBO34",RA_BUILDINGNAME,RA_VILLAFLATNO,RA_STREET,RA_CITY,PERM_STATE,RA_OTHERS,PERM_CNTRY,EXCELLENCY_CNTR,PRO_CODE};
		setValuesFromDB(sOutput,columns);
		long end_time1=System.currentTimeMillis();
		long diff1=end_time1-start_Time1;
		logInfo("PopulatePersonalDataCPD","End time PopulatePersonalDataCPD---- :"+end_time1);
		logInfo("PopulatePersonalDataCPD","Diff time PopulatePersonalDataCPD---- :"+diff1);
	}*/
}
