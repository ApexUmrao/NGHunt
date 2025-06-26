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

public class ContactCenterTeam extends Common implements Constants, IFormServerEventHandler{

	public ContactCenterTeam(IFormReference formObject) {
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
					logInfo("Execute Server Event Load","Inside event type load");
					disableControls(OPTIONAL_PRODUCT);
					logInfo("Execute Server Event Load","OPTIONAL PRODUCT DISABLED");
					prefLang();
					frameDelivery();
					onFormLoadData();
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
					String sName = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 1);
					String sDOB = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 5);
					String TXT_CUSTOMERID = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 2);
					formObject.setValue(TXT_CUSTOMERNAME,sName);
					formObject.setValue(TXT_DOB, sDOB);
					formObject.setValue("TXT_CUSTOMERID", TXT_CUSTOMERID);
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
						//formObject.NGFocus(CRO_DEC);
					} 
				}else if("postSubmit".equalsIgnoreCase(controlName)){
						createHistory();
						createAllHistory();
						if(sActivityName.equalsIgnoreCase(ACTIVITY_CONTACT_CENTER)){
							if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")){
								String sUpdateDecision = "update "+sExtTable+" set mobile_change_flag = 'False' "
										+ "Where WI_NAME = '"+sWorkitemId+"'";
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
													+ "AFTER_CONT_CNTR_MOB_NO = (SELECT FINAL_MOBILE_NO FROM "
													+ "USR_0_CUST_TXN WHERE WI_NAME = '"+sWorkitemId+"' AND "
													+ "CUST_SNO = '"+sno+"') WHERE wi_name = '"+sWorkitemId+"' "
													+ "AND CUST_SNO = '"+sno+"'";
											formObject.saveDataInDB(sUpdateMobNo);
											log.info("sUpdateMobNo::"+sUpdateMobNo);
										}
									}
								}
							} catch (Exception e) {			
								log.error("Exception: ",e);
							}
						}
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("") || formObject.getValue(CRO_DEC).equals(null)) {
							formObject.setValue(CRO_DEC, "Approve");
						}
						String sUpdateDecision = "update "+sExtTable+" set wi_completed_from = '"+ sActivityName 
								+"',CONTACT_CENTER_DEC = '"+formObject.getValue(CRO_DEC)+"' Where WI_NAME = '"
								+sWorkitemId+"'";
						logInfo("BTN_SUBMIT","sUpdateDecision::"+sUpdateDecision);
						formObject.saveDataInDB(sUpdateDecision);
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
							formObject.setValue(NO_OF_CUST_PROCESSED, "0");
						}
						formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
						if(sActivityName.equalsIgnoreCase(ACTIVITY_CONTACT_CENTER) || sActivityName.equalsIgnoreCase(ACTIVITY_CONTACT_CENTER_CPD)) {
							log.info("AT accrelation CALLING FOR EMAIL DATE  :");
							String sProcName = "SP_TemplateGenerationEmailDt";
							List<String> paramlist = new ArrayList<String>();
							paramlist.add ("Text :"+sWorkitemId);
							List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
							log.info("Output AT accrelation CALLING FOR EMAIL DATE  :"+sOutput1);
						} else {
							formObject.setStyle(BTN_SUBMIT,DISABLE,FALSE);
						}
						return getReturnMessage(true, controlName,sendMessageList.get(0).toString()+"###"+ sendMessageList.get(1));
					}
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
			}  else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
			logError("executeServerEvent", e);
		} finally {
			logInfo("onCCTexecuteServerEvent","sendMessageList="+sendMessageList);
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
		if(sActivityName.equalsIgnoreCase(ACTIVITY_CONTACT_CENTER) || sActivityName.equalsIgnoreCase(ACTIVITY_CONTACT_CENTER_CPD)) {
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
		} else if(sActivityName.equalsIgnoreCase("Product Approver") || sActivityName.equalsIgnoreCase("Product_Approver_CPD")) {
			String sUpdateDecision="update "+sExtTable+" set CRO_DEC=  Where WI_NAME='"+sWorkitemId+"'";
			formObject.saveDataInDB(sUpdateDecision);
			hideControls(new String[]{"OLD_MOBILE_NO","NEW_MOBILE_NO"});
		}
		disableControls(new String[]{SEC_CI,SEC_SRCH_EXIST_CUST,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,SEC_SI,
				SEC_DEL_INST,SEC_INTERNAL_DETL,SEC_PERSONAL_DET,Frame_ClientQuestions,SEC_CONTACT_DET_CP,SEC_CONTACT_DET_PA,
				SEC_CONTACT_DET_RA,SEC_INT_DETAIL,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,SEC_FUND_EXP_RELTNSHP,SEC_ASSESS_OTH_INFO,
				SEC_BNK_REL_UAE_OVRS,SEC_SIGN_OFF,FrameFATCA,SEC_CRS_DETAILS,SEC_SS_CPD_TRSD,SEC_SS_CPD_RISK_ASSESS,
				SEC_SS_TRSD,SEC_SS_RISK_ASSESS,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS,SEC_PRODUCTION_CRO,SEC_OPT_PROD_CRO,
				SEC_FACILITY_CRO,SEC_DOC_REQ_CRO,SEC_RISK_ASSESS_CPD,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CPD,
				SEC_OPT_PROD_CPD,SEC_FACILITY_CPD,SEC_DOC_REQ_CPD,SEC_ACC_INFO_PD,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,SEC_SI_SIRFT,SEC_SI_SWP});
		clearUdfGrid();
		setCPDCheckerCombos();
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
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || selectedSheetIndex == 4 
					|| selectedSheetIndex == 5) {	
				logInfo("onTabClick", "INSIDE 1,2,3,4,5");
				setManualFieldsBlank();
				setManualChecksBlank();
				clearComparisonFields();
				clearPersonalData();
				clearKYCData();
				clearRiskData();
				//setCPDCheckerCombos();
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
				} else {
					populateScreeningDataCPD();
					//formObject.setNGSelectedTab("Tab2",1);
				}
				populateTRSD();
				if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")) {
					formObject.setValue(CPD_MTCH_FOUND, "Eligible");
				} else {
					formObject.setValue(CPD_MTCH_FOUND, "Not Eligible"); }
			} else if(selectedSheetIndex==7) {
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					populateScreeningDataCRO();
				}
			} else if(selectedSheetIndex==8) {
				logInfo("onTabClick", "INSIDE 7");
				disableControls(new String[]{BTN_SUBMIT});
				String sQuery= "";
				List<List<String>> recordList = null;
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
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
				} 
			} else if(selectedSheetIndex==9) {
				String sQuery= "";
				List<List<String>> recordList = null;
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
			} else if(selectedSheetIndex == 10) {
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
			} else if(selectedSheetIndex == 14) {
				logInfo("onTabClick", "INSIDE 14");
				disableControls(new String[]{BTN_SUBMIT});
				loadSICombos();
				populateStndInstr();
			} else if(selectedSheetIndex == 13) {
				manageCategoryChangeSectionCPDChecker();
			//} else if(selectedSheetIndex == 18) {
			} else if(selectedSheetIndex == 19) { // Changes for Family Banking
				String query = "";
				logInfo("onTabClick", "INSIDE 19");
				enableControls(new String[]{BTN_SUBMIT});
				hideControls(new String[]{L1_APP_REQ,L2_APP_REQ,L2_APP_REQ,L2_APP_REQ});
				disableControls(new String[]{IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ,
						IS_CALL_BACK_REQ});				
				formObject.clearCombo(CRO_DEC);
				int iCount1 = getListCount(CRO_DEC);
				logInfo("ontabclickcct","iCount"+iCount1);
				if(iCount1==0 || iCount1==1) {
					query = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name "
							+ "= '"+formObject.getValue(CURR_WS_NAME).toString()+"'";
					addDataInComboFromQuery(query, CRO_DEC);
				}
				formObject.clearCombo(CRO_REJ_REASON);
				int iCount = getListCount(CRO_REJ_REASON);
				logInfo("ontabclickcct","iCount1"+iCount);
				if(iCount==0 || iCount==1){
					query = "Select to_char(ws_rej_reason) from usr_0_rej_reason_mast";
					addDataInComboFromQuery(query, CRO_REJ_REASON);
				}
				
				/*if(getGridCount(DECISION_LVW) == 0) {
					String sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery);
					log.info("decision history query "+sQuery);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);							
				}*/
				int sSelectedCust = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				String sQuery = "select FCR_MOBILE_NO,manual_mobile_no from usr_0_cust_txn where wi_name ='"
						+sWorkitemId+"' and cust_sno = "+sSelectedCust+" ";
				List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
				if(sOutput!=null && sOutput.size()>0){
					String fcrMobileNo = sOutput.get(0).get(0);
					String manualMobileNo = sOutput.get(0).get(1);
					formObject.setValue("OLD_MOBILE_NO",fcrMobileNo);
					formObject.setValue("NEW_MOBILE_NO",manualMobileNo);
				}
			} else if(tabCaption.equalsIgnoreCase("tab2")) {
				logInfo("onTabClick","in tab2........");
				if(selectedSheetIndex==0) {
					if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
						populateScreeningDataCRO();
					}
				}
			} else if(tabCaption.equalsIgnoreCase("tab1")) {
				if(selectedSheetIndex==0) {
					if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
						loadApplicationAssessmentData();
					}
				}
			}
		} catch (Exception e) {
			logInfo("onTabClick","tab details: "+data);
			logError("onTabClick", e);
		}
	}
}
