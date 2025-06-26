//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: AP2
//Product / Project			: iForm Builder
//Module					: iForms
//File Name					: ApplicationAssessment.java
//Author					: 	
//Date written (DD/MM/YYYY)	: 
//Description				: Java File for AO Application Assessment Workstep
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date	(DD/MM/YYYY)		 Change By	 	Change Description (Bug No. (If Any))
// 10/11/2021			   Gautam Rajbhar	Changes for AO-BRN-YAH -1179844
//----------------------------------------------------------------------------------------------------
package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.newgen.iforms.user.ao.util.XMLParser;
import com.newgen.iforms.user.config.Constants;
import com.newgen.mvcbeans.model.WorkdeskModel;

public class CPDBulkEODChecker extends Common implements Constants, IFormServerEventHandler {
	boolean custInfoTabLoad = false;
	String custSno = "";
	private Fulfillment fulfillment;
	
	public CPDBulkEODChecker(IFormReference iFormReference) {
		super(iFormReference);
		
		fulfillment = new Fulfillment(formObject);
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
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeServerEvent(IFormReference formObject,String controlName, String eventType, String data) {
		logInfo("onLoadCPDBulkEODChecker","Event: " + eventType + ", Control Name: " + controlName+ ", Data: " + data);
		sendMessageList.clear();
		logInfo("onLoadAcctRelation","sendmessagelist49: "+sendMessageList);
		String retMsg = getReturnMessage(true,controlName);
		try {

			logInfo("isRestrictDisplay : ",isRestrictDisplay()+"");

			if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")) {
				setRestrictedFlag();
			}
			logInfo("isRestrictDisplay : ",isRestrictDisplay()+"");
			if(isRestrictDisplay()){
				return getReturnMessage(false,controlName,"The user is not authorized to access Staff Data.");
			}
			if (eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				String fieldList = "";
				loadSICombos();
				loadApplicationAssessmentData();
				populatePreAssesmentDetails(); //Added by krishna
				populatePepAssesmentDetails();
				accountPurpose(); //AO DCRA
				additionalSource();
				long start_Time1=System.currentTimeMillis();
				logInfo("onLoadCPDBulkEODChecker","Start Time in fieldvalue---"+start_Time1);
				//sOnLoad="False";
				String decision = "";
				String sCompDec = "";
				try {			
					logInfo("onLoadCPDBulkEODChecker","Inside fieldValueBagSet sahil12345---");
					enableControls(new String[]{VIEW});
					prefLang();
					formObject.clearCombo(CRO_DEC);
					String Query = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where"
							+ " ws_name='"+formObject.getValue(CURR_WS_NAME)+"' and route_type='"+formObject.getValue(SCAN_MODE)+"'"
							+ "and upper(REQUEST_TYPE) = upper('"+formObject.getValue(REQUEST_TYPE)+"')"
							+ " order by to_char(WS_DECISION)";
					logInfo("saveAndNextValidation", "decision sQuery "+Query);
					addDataInComboFromQuery(Query,CRO_DEC);

					Frame_delivery();
					populateMakaniData();
					populateAuditSearch(SEARCH_DETAILS_LVW);
					int iProcessedCustomer=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					if(isExistingCustomer()) {
						fieldList = changeFieldsBackColor(Integer.toString(iProcessedCustomer));
					}//added on 11july2021		
					String qry="select cro_dec,comp_dec from "+sExtTable+" where wi_name ='"+sWorkitemId+"'";
					logInfo("onLoadCPDBulkEODChecker","sQuery1"+qry);
					List<List<String>> out=formObject.getDataFromDB(qry);
					if(out!=null && out.size()>0){
						decision=out.get(0).get(0);
						sCompDec=out.get(0).get(1);
					}
					if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
						hideControls(new String[]{NIG_MAKER,NIG_CHECKER});
					}
					//Enable_Disable_Load(sCurrTabIndex, sAcitivityName);
					disableControls(new String[]{BTN_VIEWDETAILS_SANCT});
					//formObject.setValue(SELECTED_ROW_INDEX, "0");
					frame81_CPD_Disable();
					manualFrameCPDDisable();
					disableControls(new String[]{SEC_SRCH_EXIST_CUST,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,SEC_SI,
							SEC_DEL_INST,SEC_INTERNAL_DETL,SEC_SS_CPD_TRSD,SEC_SS_CPD_RISK_ASSESS,SEC_CC_AORM,SEC_CC_AORC,
							SEC_SS_TRSD,SEC_SS_RISK_ASSESS,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS,SEC_PRODUCTION_CRO,SEC_OPT_PROD_CRO,
							SEC_FACILITY_CRO,SEC_DOC_REQ_CRO,SEC_RISK_ASSESS_CPD,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CPD,
							SEC_OPT_PROD_CPD,SEC_FACILITY_CPD,SEC_DOC_REQ_CPD,SEC_ACC_INFO_PD,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,SEC_SI_SIRFT,
							SEC_SI_SWP,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ,SEC_CAT_CHNG,SEC_INTERNAL_DETL,SEC_CC_AORM,
							SEC_OPT_PROD,SEC_OPT_MI,SEC_OPT_OI,BTN_TRSD_CHECK,BTN_RE_CHECK,FINAL_ELIGIBILITY,RISK_ASSESS_MARKS,BTN_CPD_TRSD_CHK,BTN_CPD_RE_CHK,CPD_FINAL_ELIGIBILITY,CPD_RISK_ASSESS_MARKS,
							BTN_CALCULATE,BTN_NEXT_CUST,SEC_ACC_INFO_AOR_MAKER});
					formObject.setValue(CRO_REMARKS, "");
					enableControls(new String[]{SI_FRST_PAYMNT,SI_LST_PAYMNT});
					logInfo("onLoadCPDBulkEODChecker","before calling getRealTimeDetails in CPD Checker");
					getRealTimeDetails();
					setCPDCheckerCombos();
					String sQuery = "SELECT DEBIT_ACC_NO,DEBIT_CURRENCY,CREDIT_PRODUCT,TO_CHAR(FIRST_PAY_DATE,"
							+ "'DATEFORMAT'),TO_CHAR(LAST_PAY_DATE,'DATEFORMAT'),PERIOD, AMOUNT FROM "
							+ "USR_0_STANDING_INSTRUCTION WHERE WI_NAME ='"+sWorkitemId+"' ORDER BY TO_NUMBER(SNO)";				
					List<List<String>> recordList = formObject.getDataFromDB(sQuery);
					log.info(recordList);
					loadListView(recordList,"DEBIT_ACC_NO,DEBIT_CURRENCY,CREDIT_PRODUCT,FIRST_PAY_DAT,LAST_PAY_DATE,"
							+ "PERIOD,AMOUNT",STND_INST_LVW);
					hideControls(new String[]{PIC1});
					if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {
						String query ="SELECT USERNAME,PERSONALNAME||' ' ||FAMILYNAME AS NAME FROM PDBUSER WHERE "
								+ "UPPER(USERNAME) =UPPER('"+sUserName+"')";
						List<List<String>> sOutput=formObject.getDataFromDB(query);
						logInfo("onLoadCPDBulkEODChecker","sOutput...."+sOutput);
						if(sOutput!=null && sOutput.size()>0) {
							formObject.setValue(CHECKER_CODE,sOutput.get(0).get(0));
							formObject.setValue(CHECKER_NAME,sOutput.get(0).get(1));
						}
						String sQuery1="select count(*) countwi from usr_0_ao_dec_hist  where ws_name='CPD Maker' and "
								+ "upper(userid)=upper('"+sUserName+"') and wi_name='"+sWorkitemId+"'";
						List<List<String>> sOutput1=formObject.getDataFromDB(sQuery1);
						logInfo("onLoadCPDBulkEODChecker","sOutput1---"+sOutput1);
						if(sOutput1!=null && sOutput1.size()>0){
							int sCount = Integer.parseInt(sOutput1.get(0).get(0));
							if(sCount>0){
								logInfo("onLoadCPDBulkEODChecker","Workitem is ReadOnly");
								formObject.applyGroup("CONTROL_SET_CPD_FORM");//formObject.NGMakeFormReadOnly();
								sendMessageValuesList("","Maker & Checker cannot be same!");
								formObject.setStyle("AO_IFORM",DISABLE, TRUE);
								return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
										+ sendMessageList.get(1).toString()); 
							}
						}
					}
					String req_type=formObject.getValue(REQUEST_TYPE).toString();
					if(req_type.equalsIgnoreCase("New Account with Category Change") 
							|| req_type.equalsIgnoreCase("Category Change Only")) {
						formObject.setValue(CHECKER_CODE_CAT_CHANGE,formObject.getValue(CHECKER_CODE).toString());
						formObject.setValue(CHECKER_NAME_CAT_CHANGE,formObject.getValue(CHECKER_NAME).toString());
					}
					String sQuery0 = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"+ sWorkitemId 
							+"' AND STATUS='Success'";
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery0);
					if(sOutput!=null && sOutput.size()>0){
						logInfo("onLoadCPDBulkEODChecker","sOutput...."+sOutput);
						for(int i=0; i<sOutput.size(); i++) {
							String sCallName  = sOutput.get(i).get(0);
							if(sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION")) {
								loadIntegrationDataOnForm();
								break;
							}
						}
					}
					if(decision.equalsIgnoreCase("Permanent Reject - Discard") 
							|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
						logInfo("onLoadCPDBulkEODChecker","Workitem is ReadOnly");
						formObject.applyGroup("CONTROL_SET_CPD_FORM");//formObject.NGMakeFormReadOnly();
						frame81_CPD_Disable();
						manualFrameCPDDisable();
						frame18_QDE_Disable();
						disableControls(new String[]{NEG_INFO,FATF,KYC,SEC_SS_TRSD,SEC_SS_RISK_ASSESS,SEC_ACC_INFO_PD,
								SEC_ACC_INFO_ECD,SEC_ACC_INFO_AOR_MAKER,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS,
								SEC_PRODUCTION_CRO,SEC_OPT_PROD_CRO,SEC_FACILITY_CRO,SEC_DOC_REQ_CRO,SEC_ACC_INFO_DCL,
								SEC_SS_CPD_TRSD,SEC_SS_CPD_RISK_ASSESS,SEC_SI,SEC_CAT_CHNG,SEC_INTERNAL_DETL,SEC_CC_AORM,
								SEC_OPT_PROD,SEC_OPT_MI,SEC_OPT_OI,BTN_TRSD_CHECK,BTN_RE_CHECK,FINAL_ELIGIBILITY,
								RISK_ASSESS_MARKS,BTN_CPD_TRSD_CHK,BTN_CPD_RE_CHK,CPD_FINAL_ELIGIBILITY,
								CPD_RISK_ASSESS_MARKS,BTN_CALCULATE,BTN_NEXT_CUST});
						Frame_delivery();
						Frame48_CPD_Disable();
						hideControls(new String[]{PIC1});
						if(decision.equalsIgnoreCase("Permanent Reject - Discard") 
								|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
							enableControls(new String[]{CRO_DEC,CRO_REMARKS,CRO_REJ_REASON});
							formObject.setValue(CRO_DEC,"Permanent Reject - Discard");
						}
						return null;
					}
					logInfo("onLoadCPDBulkEODChecker","before setProductCurrencyComboOnFormLoad");
					setTemp_usr_0_product_selected();
					fieldValueUsr_0_Risk_Data();
					logInfo("onLoadCPDBulkEODChecker","aftr fieldValueUsr_0_Risk_Data");
					enableControls(new String[]{VIEW});
					formObject.setValue(P_ECB_CHQ_VALIDATION, "True");
					sQuery = "SELECT count(1) AS COUNT FROM USR_0_CUST_TXN WHERE WI_NAME='"
							+ sWorkitemId + "' and PASS_TYPE='Diplomat'";
					sOutput = formObject.getDataFromDB(sQuery);
					if(sOutput!=null && sOutput.size()>0){
						int diplomatPassType = Integer.parseInt(sOutput.get(0).get(0));
						if (diplomatPassType > 0) {
							enableControls(new String[]{P_ECB_CHQ_VALIDATION});
						} else {
							disableControls(new String[]{P_ECB_CHQ_VALIDATION});
						}
					}
					disableControls(new String[]{P_ECB_REASON});
					loadECBChqBookValidation();
				} catch (Exception e)  {
					logError("Exception in OnloadCPDCHecker:::",e);
				}	
				long end_time1=System.currentTimeMillis();
				long diff1=end_time1-start_Time1;
				logInfo("onLoadCPDBulkEODChecker","End time Fieldvaluebaf---- :"+end_time1);
				logInfo("onLoadCPDBulkEODChecker","Diff time Fieldvaluebaf---- :"+diff1);
				custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
				populateUAEPassInfoStatus(sWorkitemId);
				LoadInstantDelivery(); 
				populateStndInstr();
				if(!fieldList.isEmpty()){
					return getReturnMessage(true, controlName, fieldList);
				}
				
                                //Mutilpe account bug
				if(formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Branch-Excellency")
						|| formObject.getValue("SOURCING_CHANNEL").toString().equalsIgnoreCase("Branch")) {
					String sQuery5 = "SELECT CID FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME='"+ sWorkitemId+ "' ORDER BY TO_NUMBER(INSERTIONORDERID)";
					logInfo("CPD CHECKER sQuery5 ",sQuery5);
					List<List<String>> sOutput5 = formObject.getDataFromDB(sQuery5);
					for(int i = 0; i < sOutput5.size(); i++) {
						logInfo(" CPD CHECKER sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14));
						formObject.setTableCellValue(PRODUCT_QUEUE, i, 14 ,i+1+"");
						logInfo(" CPD CHECKER sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14)); 
					}
				}

			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					onTabClick(data);
				} else if(SAVE_TAB_CLICK.equalsIgnoreCase(controlName)) {
					if(saveAndNextValidation((String)data.split(",")[1])){
						return getReturnMessage(true, controlName);
					}
				} else if(controlName.equalsIgnoreCase(BTN_ECB_REFRSH)) {
					loadECBChqBookValidation();
				} else if(controlName.equalsIgnoreCase(VIEW)) {
					formObject.clearTable(LVW_DEDUPE_RESULT);
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					String query = "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'',"
							+ "'',to_date(CUST_DOB,'"+DATEFORMAT+"'),CUST_EIDA,CUST_NATIONALITY,system_type FROM "
							+ "USR_0_DUPLICATE_SEARCH_DATA WHERE WI_NAME='"+sWorkitemId+"' AND CUST_SNO ='"+iSelectedRow+"'";
					List<List<String>> out = formObject.getDataFromDB(query);
					loadListView(out,COLUMNS_LVW_DEDUPE_RESULT,LVW_DEDUPE_RESULT);
					String sQuery1 = "select dedupe_selected_index from USR_0_CUST_TXN Where WI_NAME='"+ sWorkitemId +"'"
							+ " and cust_sno='"+iSelectedRow+"'";
					List<List<String>> out1 = formObject.getDataFromDB(sQuery1);
					int index1= Integer.parseInt(out1.get(0).get(0));
					int[] intA = new int[1];
					intA[0] =index1;
					//formObject.setNGLVWSelectedRows("ListView5",  intA );
					// formObject.setTableCellValue(arg0, arg1, arg2, arg3);
				} else if(controlName.equalsIgnoreCase(CP_CITY)||controlName.equalsIgnoreCase(PA_CITY)|| 
						controlName.equalsIgnoreCase(RA_CITY)){
					manageCity(controlName);
				} else if(controlName.equalsIgnoreCase("AO_PRODUCT_QUEUE.MODE_OF_FUNDING")) {  ///js
					System.out.println("In Transfer Mode");
					int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
					String sCustID  = formObject.getTableCellValue(ACC_RELATION,iPrimaryCust,2);
					int iRows = getGridCount(PRODUCT_QUEUE);				
					int iSelectedRow=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					System.out.println("iRows---"+iRows);
					if(iRows>0) {
						String sMode=formObject.getTableCellValue(PRODUCT_QUEUE,iSelectedRow,8);
						if(sMode.equalsIgnoreCase("Transfer - Internal")) {
							//							objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO", true);
							//							objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.AMT_TRNSFERED", true);
							//							objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK_NO", false);
							//formObject.NGClear("AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO");
							String sQuery= "SELECT ACC_NO FROM USR_0_PRODUCT_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' AND ACC_STATUS IN (SELECT DESCRIPTION FROM USR_0_ACCOUNT_STATUS_CODE WHERE CODE IN ('6','8')) AND CUSTOMER_ID='"+sCustID+"'";
							List<List<String>> out = formObject.getDataFromDB(sQuery);
							if(out!=null && out.size()>0){
								String sDebitAccNo = out.get(0).get(0);
								if(!sDebitAccNo.equalsIgnoreCase("")) {
									String sTemp[] =sDebitAccNo.split(",");
									for(int i=0;i<sTemp.length;i++) {
										formObject.addItemInTableCellCombo(PRODUCT_QUEUE, i, 4, sTemp[i], sTemp[i]);//("AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO", sTemp[i]);
									}
									formObject.setValue("AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO","");
								}
							}
						} else {
							//							objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.CHEQUE_BOOK_NO", true);
							//							objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO", false);
							//							objChkRepeater.setEnabled(iSelectedRow, "AO_PRODUCT_QUEUE.AMT_TRNSFERED", false);
						}
					}
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT)){
					insertEmpCompanyName();//Ameena 08062023
					insertIntoTrackChangeModify(); //Ameena 13012023
//					validateCrsClassification(); //added by reyaz 04-04-2023
					if(isControlVisible(DOC_APPROVAL_OBTAINED) && isControlVisible(COURT_ORD_TRADE_LIC)){// newly added
						if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
								&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
							sendMessageValuesList("", "Please select the appropriate checkbox to complete the validation");
							return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
									+ sendMessageList.get(1).toString());
						}
					} 
					if(formObject.getValue(P_ECB_CHQ_VALIDATION).toString().equalsIgnoreCase("false")
							&& formObject.getValue(P_ECB_REASON).equals("")) {
						sendMessageValuesList(P_ECB_REASON, "Please enter reason for skipping the ECB check");
						return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
								+ sendMessageList.get(1).toString());
					} else if(!validateEligibilityField()) {
						sendMessageValuesList(BUTTON_REFRESH, "Please click Refresh button to check eligibility "
								+ "status for cheque book.");
						return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
								+ sendMessageList.get(1).toString());
					} else if(formObject.getValue(NIG_CPDMAKER).toString().equalsIgnoreCase("yes") 
							&& data.equalsIgnoreCase("confirmYes")) {
						formObject.setValue(NIG_CPDCHECKER,"YES");
						String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDCHECKER='YES' Where WI_NAME='"+sWorkitemId+"'";
						formObject.saveDataInDB(updatequery);
					}
					if(saveBulkEod()) {
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") && 
								sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {
							return getReturnMessage(true, controlName, "openJSP");
						} else return getReturnMessage(true, controlName, CA008);
					}
					checkIntegrationCall(formObject); // check on submit pending calls
				} else if(controlName.equalsIgnoreCase("postSubmit")){
					logInfo("onLoadCPDBulkEODChecker","postSubmit---- :");
					if(postSubmit()) {
						return getReturnMessage(true, controlName);
					}
				}
				else if(controlName.equalsIgnoreCase("BTN_FULFILLMENT_START")) {// Added for FULLFILMENT
					logInfo("executeServerEvent", "BTN_FULFILLMENT_START");
					fulfillment.pushMessageFulfillment();
				} else if(controlName.equalsIgnoreCase("BTN_FULFILLMENT_REFRESH")) {// Added for FULLFILMENT
					logInfo("executeServerEvent", "BTN_FULFILLMENT_REFRESH");
					fulfillment.refreshStatus();
				}
			} else if(eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				logInfo("CHANGEEE EVENT",controlName);
				if("confirmAppRisk".equalsIgnoreCase(controlName)) {
					int sCustNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString()) + 1;
					formObject.setValue(NIG_CPDCHECKER,"YES");
					String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDCHECKER='YES' Where "
							+ "WI_NAME='"+sWorkitemId+"'";// AND CUST_SNO ='"+SNO+"'";
					formObject.saveDataInDB(updatequery);
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_EIDANO,formObject.getValue(EIDA_EIDANO).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_FCR)) {
					logInfo("CLICK EVENT ARPIT EIDAAA2..",controlName);
					toggleCheckbox(controlName,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_EIDANO,formObject.getValue(FCR_EIDANO).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_MANUAL)) {
					logInfo("CLICK EVENT ARPIT EIDAAA3..",controlName);
					toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
					manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_EIDANO,formObject.getValue(MANUAL_EIDANO).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(CP_MOBILE,formObject.getValue(MANUAL_MOBILE).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(CP_MOBILE,formObject.getValue(FCR_MOBILE).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_MANUAL);
					manageManualFields(CHECKBOX_TELE_MOB_MANUAL,MANUAL_MOBILE);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(CP_MOBILE,formObject.getValue(EIDA_MOBILE).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(CP_EMAIL,formObject.getValue(EIDA_EMAIL).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(CP_EMAIL,formObject.getValue(FCR_EMAIL).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
					manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(CP_EMAIL,formObject.getValue(MANUAL_EMAIL).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_FCR)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_EIDA,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_DOB,formObject.getValue(FCR_DOB).toString());
					}
				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_EIDA)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_MANUAL);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);

				} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_MANUAL)) {
					toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
					manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
					}
					if(formObject.getValue(controlName).toString().equalsIgnoreCase("True")) {
						formObject.setValue(PD_DOB,formObject.getValue(MANUAL_DOB).toString());
					}
				} else if (controlName.equalsIgnoreCase(P_ECB_CHQ_VALIDATION)
						&& formObject.getValue(P_ECB_CHQ_VALIDATION).toString().equalsIgnoreCase("false")) {
					enableControls(new String[] {P_ECB_REASON});
				} else if (controlName.equalsIgnoreCase(P_ECB_CHQ_VALIDATION)
						&& formObject.getValue(P_ECB_CHQ_VALIDATION).toString().equalsIgnoreCase("true")) {
					formObject.setValue(P_ECB_REASON, "");
					disableControls(new String[] {P_ECB_REASON});
				} else if(controlName.equalsIgnoreCase(DOC_APPROVAL_OBTAINED)) {
					if("true".equalsIgnoreCase(formObject.getValue(DOC_APPROVAL_OBTAINED).toString())){
						formObject.setValue(COURT_ORD_TRADE_LIC,"false");
					} else{
						formObject.setValue(COURT_ORD_TRADE_LIC,"true");
					}
				} else if(controlName.equalsIgnoreCase(COURT_ORD_TRADE_LIC)) {
					if("true".equalsIgnoreCase(formObject.getValue(COURT_ORD_TRADE_LIC).toString())){
						formObject.setValue(DOC_APPROVAL_OBTAINED,"false");
					} else{
						formObject.setValue(DOC_APPROVAL_OBTAINED,"true");
					}
				} else if(controlName.equalsIgnoreCase(CP_CITY)||controlName.equalsIgnoreCase(PA_CITY)||controlName.equalsIgnoreCase(RA_CITY)){
					manageCity(controlName);
				} else if(controlName.equalsIgnoreCase(CRO_DEC)){
					if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {
						if(formObject.getValue(controlName).toString().equalsIgnoreCase("Approve with Additional Approval") || formObject.getValue(controlName).toString().equalsIgnoreCase("Send To Compliance")){
							enableControls(new String[] {L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ});
							if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("Increased Risk")){
								formObject.setValue(L1_APP_REQ,"true");
								formObject.setValue(L2_APP_REQ,"true");
								formObject.setValue(L3_APP_REQ,"true");
								formObject.setValue(L4_APP_REQ,"false");
							} else if(formObject.getValue(FINAL_RISK_VAL).toString().equalsIgnoreCase("UAE-PEP")){
								formObject.setValue(L1_APP_REQ,"true");
								formObject.setValue(L2_APP_REQ,"true");
								formObject.setValue(L3_APP_REQ,"true");
								formObject.setValue(L4_APP_REQ,"true");
							} else {
								formObject.setValue(L1_APP_REQ,"false");
								formObject.setValue(L2_APP_REQ,"false");
								formObject.setValue(L3_APP_REQ,"false");
								formObject.setValue(L4_APP_REQ,"false");
							}
							if(formObject.getValue(controlName).toString().equalsIgnoreCase("Approve with Additional Approval")){
								//formObject.setNGEnable("AO_CRO_REJ_REASON",false);
								formObject.setStyle(CRO_REJ_REASON, "disable", "true");
							} else{
								formObject.setStyle(CRO_REJ_REASON, "disable", "false");
							}
						} else{
							formObject.setValue(L1_APP_REQ,"false");
							formObject.setValue(L2_APP_REQ,"false");
							formObject.setValue(L3_APP_REQ,"false");
							formObject.setValue(L4_APP_REQ,"false");
							disableControls(new String[] {L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ});
						}
					}
				}
			} else if((eventType.equalsIgnoreCase(EVENT_TYPE_GOTFOCUS)|| eventType.equalsIgnoreCase(EVENT_TYPE_CLICK))) {
				int iRows = getGridCount(ACC_RELATION);
				int iSelectedRow=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				String sName=formObject.getTableCellValue(ACC_RELATION,iSelectedRow,1);
				String sDOB=formObject.getTableCellValue(ACC_RELATION,iSelectedRow,5);
				String cust_id=formObject.getTableCellValue(ACC_RELATION,iSelectedRow,2);

				formObject.setValue(TXT_CUSTOMERNAME, sName);
				formObject.setValue(TXT_DOB, sDOB);
				formObject.setValue(CUST_ID, cust_id);
			} else if("handlingJSPData".equalsIgnoreCase(eventType)) {
				if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
					if(showOpenCallJSP(data)) {
						return getReturnMessage(true, controlName, CA008);
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception in Event- " + eventType + "control name- "+ controlName + ": ", e);
		} finally {
			logInfo("onLoadCPDBulkEODChecker","sendMessageList="+sendMessageList);
			if(!sendMessageList.isEmpty()){
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return getReturnMessage(false,controlName,sendMessageList.get(0).toString()+"###"
						+ sendMessageList.get(1).toString());
			}
		}
		return retMsg;
	}

	private boolean saveBulkEod() {
		if(formObject.getValue(FINAL_RISK_VAL_CPD).toString().equalsIgnoreCase("") &&
				formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") &&
				(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER))) {
			sendMessageValuesList(FINAL_RISK_VAL_CPD, "Risk is blank, kindly recalculate.");
			return false;
		} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
			sendMessageValuesList(CRO_DEC, "Please select user decision.");
			return false;
		} else if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
			String rejectionReason=formObject.getValue(CRO_REJ_REASON).toString();
			String rejectionRemarks=formObject.getValue(CRO_REMARKS).toString();
			if(rejectionReason.equalsIgnoreCase("")) {
				sendMessageValuesList(CRO_REJ_REASON, "Please Select Rejection Reason.");
				return false;
			}
			if(rejectionRemarks.equalsIgnoreCase("")) {
				sendMessageValuesList(CRO_REMARKS, "Please Fill Remarks.");
				return false;
			}	
		}
		String sQuery="";
		if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {
			long st=System.currentTimeMillis();
			long end_Time=System.currentTimeMillis();
			long diff=System.currentTimeMillis();
//			if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")
//					&& !formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
			if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					sQuery = "SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_INTEGRATION_CALLS WHERE"
							+ " WI_NAME='"+ sWorkitemId +"' AND (CALL_NAME LIKE 'CUSTOMER_CREATION%'"
							+ " OR CALL_NAME LIKE 'CUSTOMER_MODIFY%' OR CALL_NAME LIKE 'ACCOUNT_CREATION%') AND STATUS='Success'";
				} else {
					sQuery = "SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_INTEGRATION_CALLS WHERE"
							+ " WI_NAME='"+ sWorkitemId +"' AND CALL_NAME LIKE 'ACCOUNT_MODIFY%' AND STATUS='Success'";
				}
				List<List<String>> out = formObject.getDataFromDB(sQuery);
				end_Time=System.currentTimeMillis();
				diff=st-end_Time;
				if(!out.get(0).get(0).equalsIgnoreCase("0")) {
//					sendMessageValuesList("","You can only Approve/Permanent Reject this workitem as CID/Account "
//							+ "has been created/modified.");
					sendMessageValuesList("","You can only Approve this workitem as CID/Account "
							+ "has been created/modified.");
					return false;
				}
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") || 
					formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Compliance")) {
				if(!reKeyLogic()) {
					if(formObject.getValue(MANUAL_NAME).toString().isEmpty() 
							&& formObject.getValue(EIDA_NAME).toString().isEmpty() 
							&& formObject.getValue(FCR_NAME).toString().isEmpty()) {
						sendMessageValuesList("", "Please click on Customer Info Tab in order to check any mismatch in "
								+ "customer data");
					}
					return false;
				} else {
					updateRekeyCheck("true");
				}
				if(!checkRekeyDone()) {
					return false;
				}
				if (!searchCustCRM()) {
					return false;
				}
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) { 
				int iSICount = getGridCount(STND_INST_LVW);
				if(iSICount>0){
					for(int i=0;i<iSICount;i++) {	
						boolean bReturn = validateSI(formObject.getTableCellValue(STND_INST_LVW, i, 3),"SI First Payment ");
						if(bReturn == false) {
							//formObject.setNGEnable("Command24", true);
							//formObject.setNGEnable("static_submit", true);
							return false;
						}
					}
				}
				String sQuery2="select STATUS,SOURCE from usr_0_integration_calls where WI_NAME='"+sWorkitemId+"' and CALL_NAME LIKE 'CUSTOMER_MODIFY%'";
				List<List<String>>out = formObject.getDataFromDB(sQuery2);
				if(out!=null && out.size()>0){
					String sStatus2=out.get(0).get(0);
					String sSource2=out.get(0).get(1);
					if(!(sStatus2.equalsIgnoreCase("Pending")) && (sSource2.equalsIgnoreCase("QDE_Acc_Info_Chk")||sSource2.equalsIgnoreCase("DDE_Acc_Info_Chk"))) {
						String query3 = "UPDATE USR_0_INTEGRATION_CALLS set STATUS ='Pending' where WI_NAME ='"+sWorkitemId+" and CALL_NAME LIKE 'CUSTOMER_MODIFY%'";
						//String sOutput3=ExecuteQuery_APUpdate("USR_0_INTEGRATION_CALLS","STATUS","'Pending'","WI_NAME ='"+sWorkitemId+"' and CALL_NAME LIKE 'CUSTOMER_MODIFY%'");
						formObject.saveDataInDB(query3);
					}
				}
				String query4 = "UPDATE USR_0_CRS_DETAILS SET CRSCLASSIFICATION ='UPDATED-UNDOCUMENTED' WHERE "
						+ "WI_NAME ='"+sWorkitemId+"' and CRSCERTIFICATIONFORMOBTAINED= 'No'";
				String query5 = "UPDATE USR_0_CRS_DETAILS SET CRSCLASSIFICATION ='UPDATED-DOCUMENTED'"
						+ " WHERE WI_NAME ='"+sWorkitemId+"' and CRSCERTIFICATIONFORMOBTAINED= 'Yes'";
				formObject.saveDataInDB(query4);
				formObject.saveDataInDB(query5);
				st=System.currentTimeMillis();
				String sFinalStatus =insertDataInIntegrationTable();
				String sOutput= "";
				end_Time=System.currentTimeMillis();
				diff=st-end_Time;
				st=System.currentTimeMillis();
				if(sFinalStatus.equalsIgnoreCase("Success")) {
					sQuery ="SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME ='"+sWorkitemId+"' AND SOURCE ='"+sActivityName+"'";
					List<List<String>> out1 = formObject.getDataFromDB(sQuery);
					end_Time=System.currentTimeMillis();
					diff=st-end_Time;
					if(out1!=null && out1.size()>0){
						if(out1.get(0).get(0).equalsIgnoreCase("0")) {
							sendMessageValuesList("", "Some error occured in starting webservice");
							return false;
						}
					}
				}
				
				// Fulfillment changes START
				String fulfillmentFlag = formObject.getValue("FULLFILMENT_FLAG").toString();
				log.info("fulfillmentFlag: "+ fulfillmentFlag);
				if ("Y".equals(fulfillmentFlag)) {	
					fulfillment.insertFulfillmentDataInIntegrationTable();
					fulfillment.updateCallOrder();
					fulfillment.loadFulfillmentdata();
				}
				// Fulfillment changes END
			}
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

	private boolean postSubmit(){
		
		String sQuery="";
		long start_Time1=System.currentTimeMillis();
		long start_Time2=System.currentTimeMillis();
		saveKycTxnData();
		String query1="select VALUE from usr_0_defaultvalue_fcr where NAME='resubmission'";
		long qs1=System.currentTimeMillis();
		List<List<String>> sOutput1=formObject.getDataFromDB(query1);
		long qe1=System.currentTimeMillis();
		logInfo("postSubmit","qs1 and qe1"+qs1+" "+qe1);
		logInfo("postSubmit",sOutput1.toString());
		if(sOutput1!=null && sOutput1.size()>0){
			String resubmit_flag=sOutput1.get(0).get(0);
			System.out.println("resubmit flag----"+resubmit_flag);
			if(resubmit_flag.equalsIgnoreCase("True")) {
				if(sActivityName.equalsIgnoreCase("CPD Checker") && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {	
					String queryWIForceDone ="select a.wi_name AS WINAME,a.username as USERNAME  from usr_0_ao_dec_hist a, ext_ao b where a.ws_decision='Approve' and a.ws_name='CPD Checker' and a.wi_name =b.wi_name and b.curr_ws_name='CPD Checker'";
					logInfo("postSubmit","query for Resubmit CPD Checker :"+queryWIForceDone);
					List<List<String>> outputwlForceDone=formObject.getDataFromDB(queryWIForceDone);
					logInfo("postSubmit","output for Resubmit CPD Checker  :"+outputwlForceDone);
					if(outputwlForceDone!=null && outputwlForceDone.size()>0){
						try  {
							if(!outputwlForceDone.get(0).get(0).equalsIgnoreCase("")) {
								String varWiName=outputwlForceDone.get(0).get(0);
								if(outputwlForceDone.get(0).get(0).contains(",")) {
									String aryWI[]=outputwlForceDone.get(0).get(0).split(",");
									varWiName=aryWI[0];
								} else {
									varWiName=outputwlForceDone.get(0).get(0);
									System.out.println("varWiName----"+varWiName);
								}
								String sQuery2="select PERSONALNAME from pdbuser where upper(UserName)=upper('"+sUserName+"')";
								logInfo("postSubmit","Query1: "+sQuery2);
								long qs2=System.currentTimeMillis();
								List<List<String>>  sOutput2=formObject.getDataFromDB(sQuery2);
								if(sOutput2!=null && sOutput2.size()>0){
									long qe2=System.currentTimeMillis();
									logInfo("postSubmit","qs2 and qe2"+qs2+" "+qe2);
									logInfo("postSubmit",sOutput2+"");
									String id=sOutput2.get(0).get(0);
									//CompleteWorkitem(varWiName,"1",sActivityId);
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							logError("Exception in Postsubmit ",e);
						}
					}
				}
			}
			long end_Time=System.currentTimeMillis();
			long diff=end_Time-start_Time1;
			System.out.println("Time for Workitem submit:"+diff);
			start_Time1=System.currentTimeMillis();
			end_Time=System.currentTimeMillis();
			diff=end_Time-start_Time1;
			String sOutput="";
//			String flagKey="true";
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
				formObject.setValue(NO_OF_CUST_PROCESSED, "0");
//				updateFlag( flagKey ); //9aug2021
			}
			if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER) && formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				start_Time1=System.currentTimeMillis();
				String sProcName="SP_TemplateGeneration_SMS";
//				String param = sWorkitemId+"','"+sProcessName;
				List<String> paramlist =new ArrayList<>();
				paramlist.add (PARAM_TEXT+sWorkitemId);
//				paramlist.add (PARAM_TEXT+sProcessName);
				paramlist.add (PARAM_TEXT+"CPDchecker");
				logInfo("postSubmit",sProcName+":"+paramlist);
				formObject.getDataFromStoredProcedure(sProcName, paramlist);
				long end_Time1=System.currentTimeMillis();
				long diff1=end_Time1-start_Time1;
				start_Time1=System.currentTimeMillis();
				String sPrimaryCust = getPrimaryCustomerSNO();
				long qs4=System.currentTimeMillis();
				String upquer = "SELECT CUST_SEG,CUST_ID FROM USR_0_CUST_TXN WHERE CUST_SNO ='"+sPrimaryCust+"' AND WI_NAME = '"+sWorkitemId+"'";
				logInfo("postSubmit","Query2: "+upquer);
				List<List<String>> outt = formObject.getDataFromDB(upquer);
				long qe4=System.currentTimeMillis();
				String sCustID = outt.get(0).get(1);
				sProcName="CDTS_WIMovement";
				List<String> paramlist1 =new ArrayList<>();
				paramlist1.add (PARAM_TEXT+sWorkitemId);
				//paramlist1.add ("1");
				logInfo("postSubmit",sProcName+":"+paramlist1);
				formObject.getDataFromStoredProcedure(sProcName, paramlist1);
				String sReqType=formObject.getValue(REQUEST_TYPE).toString();
				if(sReqType.equalsIgnoreCase("New Account with Category Change") || sReqType.equalsIgnoreCase("Category Change Only")) {
					String updQuery ="UPDATE USR_0_CUST_TXN SET CATEGORY_CHANGE_DATE =sysdate WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='1'";
					formObject.saveDataInDB(updQuery);
				}
				String sCustSeg = "";
				String sRMName = "";
				String sRMCode = "";
				if(sReqType.equalsIgnoreCase("New Account with Category Change") || sReqType.equalsIgnoreCase("Category Change Only")) {
					sCustSeg = formObject.getValue(NEW_CUST_SEGMENT).toString();
					sRMName = formObject.getValue(NEW_RM_NAME_CAT_CHANGE).toString();
					sRMCode = formObject.getValue(NEW_RM_CODE_CAT_CHANGE).toString();
				} else {
					String sCustNo =getPrimaryCustomerSNO();
					String sQuery1="SELECT CUST_SEG,RM_NAME,RM_CODE FROM USR_0_CUST_TXN WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'";
					logInfo("postSubmit","Query3: "+sQuery1);
					long qs5=System.currentTimeMillis();
					List<List<String>> out7 = formObject.getDataFromDB(sQuery1);
					long qe5=System.currentTimeMillis();
					sCustSeg=out7.get(0).get(0);
					sRMName=out7.get(0).get(1);
					sRMCode=out7.get(0).get(2);
				}
				if(sCustSeg.equalsIgnoreCase("Privilege") || sCustSeg.equalsIgnoreCase("Emirati")) {
					List<String> param11 =new ArrayList<>();
					param11.add(PARAM_TEXT+formObject.getValue(WMS_ID).toString());
					param11.add(PARAM_TEXT+"AO");
					logInfo("postSubmit","ADCB_PC_TP_PKG.PC_INSERT_TOUCH_POINT_TAT_DATA: "+param11);
					formObject.getDataFromStoredProcedure("ADCB_PC_TP_PKG.PC_INSERT_TOUCH_POINT_TAT_DATA",param11);	
				}
				end_Time1=System.currentTimeMillis();
				diff1=end_Time1-start_Time1;
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
					formObject.setValue(ACC_CLASS,"Conventional");
				}
				if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
					String sQuery11="SELECT INSTANT_DEL_YES FROM "+sExtTable+" WHERE WI_NAME ='"+sWorkitemId+"'";
					List<List<String>> out7 = formObject.getDataFromDB(sQuery11);
					String sInstant_del_yes = out7.get(0).get(0);
					if(sInstant_del_yes.equalsIgnoreCase("true")) {
						long qs61=System.currentTimeMillis();
						sQuery = "SELECT CASE WHEN (SELECT COUNT(WI_NAME) FROM DEBIT_CARD_REP WHERE WI_NAME ='"+sWorkitemId+"' AND REP_NEW_LINK ='New') !=0 AND (SELECT COUNT(WI_NAME) FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME ='"+sWorkitemId+"' AND CHEQUE_BOOK ='No')!=0 THEN 'NO' ELSE 'YES' END SECURITY_ITEM FROM DUAL";
					} else {	
						long qs62=System.currentTimeMillis();
						sQuery = "SELECT CASE WHEN (SELECT COUNT(WI_NAME) FROM DEBIT_CARD_REP WHERE WI_NAME ='"+sWorkitemId+"' AND REP_NEW_LINK ='New') !=0 THEN 'YES' WHEN (SELECT COUNT(WI_NAME) FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME ='"+sWorkitemId+"' AND CHEQUE_BOOK ='Yes')!=0 THEN 'YES' ELSE 'NO' END SECURITY_ITEM FROM DUAL";
					}	
				} else {
					long qs63=System.currentTimeMillis();
					System.out.println(" qs63"+qs63);
					sQuery = "SELECT CASE WHEN (SELECT SUM(COUNT_WI) FROM (SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_DEBITCARD_EXISTING WHERE WI_NAME ='"+sWorkitemId+"' UNION ALL SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME ='"+sWorkitemId+"')) !=0 THEN 'YES' ELSE 'NO' END SECURITY_ITEM FROM DUAL";	
				}
				logInfo("postSubmit","Query4: "+sQuery);
				List<List<String>> out7 = formObject.getDataFromDB(sQuery);	
				long qe6=System.currentTimeMillis();
				String sSecurityItem = out7.get(0).get(out7.size()-1);
				String decision=formObject.getValue(CRO_DEC).toString();
				Date d= new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy ");
				String sDate = dateFormat.format(d);
				String updatequery ="UPDATE EXT_AO SET CPD_CHECKER_SUBMIT_DATE ='"+sDate+"', CUST_ID ='"+sCustID+"' ,"
						+ "NO_SECURITY_ITEMS = '"+sSecurityItem+"' ,PRI_RM_CODE= '"+sRMCode+"' ,PRI_RM_NAME='"+sRMName+"' ,"
						+ "PRI_CUST_SEGMENT='"+sCustSeg+"',CPD_CHECKER_DEC='"+decision+"' WHERE WI_NAME ='"+sWorkitemId+"'";
				formObject.saveDataInDB(updatequery);
				List<String> paramlistttt =new ArrayList<>();
				paramlistttt.add (PARAM_TEXT+sProcessName);
				paramlistttt.add (PARAM_TEXT+sWorkitemId);
				logInfo("postSubmit","SP_EMAIL_CONFIG: "+paramlistttt);
				formObject.getDataFromStoredProcedure("SP_EMAIL_CONFIG", paramlistttt);
				try {
					long start_Time11=System.currentTimeMillis();
					loadIntegrationDataOnForm();
				} catch(Exception e) {
					logError("Exception in Postsubmit ",e);
				}
				end_Time1=System.currentTimeMillis();
				diff1=end_Time1-start_Time2;
				String sQ1="update "+sExtTable+" set PROD_SELECTD='True' where wi_name='"+sWorkitemId+"'";
				formObject.saveDataInDB(sQ1);
				start_Time1=System.currentTimeMillis();
				sProcName="SP_TemplateGeneration_Email";
				List<String> prml =new ArrayList<>();
				prml.add (PARAM_TEXT+sWorkitemId);
				prml.add (PARAM_TEXT+sProcessName);
				logInfo("postSubmit","SP_TemplateGeneration_Email: "+prml);
				formObject.getDataFromStoredProcedure(sProcName, prml);
				end_Time1=System.currentTimeMillis();
				diff1=end_Time1-start_Time1;
				start_Time1=System.currentTimeMillis();
				sProcName="SP_TemplateGenerationEmailDt";
				List<String> prm2 =new ArrayList<>();
				prm2.add (PARAM_TEXT+sWorkitemId);
				logInfo("postSubmit","SP_TemplateGeneration_Email: "+prm2);
				formObject.getDataFromStoredProcedure("SP_TemplateGenerationEmailDt", prm2);
				end_Time1=System.currentTimeMillis();
				diff1=end_Time1-start_Time1;
				start_Time1=System.currentTimeMillis();
				String queryforwl ="select count(prod_code) as COUNT_wi from usr_0_product_selected where prod_code " +
						"not in (select prod_code from USR_0_BLOCK_PROD_MAST) and wi_name  ='"+sWorkitemId+"' and prod_code is not null";
				logInfo("postSubmit","Query5: "+queryforwl);
				long qs3=System.currentTimeMillis();
				List<List<String>> outpt = formObject.getDataFromDB(queryforwl);
				long qe3=System.currentTimeMillis();
				String sReqType1=formObject.getValue(REQUEST_TYPE).toString();
				if(outpt.get(0).get(0).equalsIgnoreCase("0") && !(sReqType1.equalsIgnoreCase("Category Change Only")||sReqType1.equalsIgnoreCase("Upgrade"))){                     
					String sColumnWL="all_prod_blocked";
					String	sValuesWL = "'Y'";
					String sWhereWL ="wi_name='"+sWorkitemId+"'"; 
					String upq = "UPDATE EXT_AO SET all_prod_blocked = 'Y' WHERE WI_NAME ='"+sWorkitemId+"'";
					formObject.saveDataInDB(upq);
				} else {
					start_Time1=System.currentTimeMillis();
					sProcName="SP_TemplateGeneration_WL";
					List<String> prm9 =new ArrayList<>();
					prm9.add (PARAM_TEXT+sWorkitemId);
					logInfo("templateinsidesubmit", "SP_TEMPLATEGENERATION_WL : "+prm9);
					List out = formObject.getDataFromStoredProcedure("SP_TEMPLATEGENERATION_WL", prm9);	
					logInfo("templateinsidesubmit", "out : "+out);
					end_Time1=System.currentTimeMillis();
					diff1=end_Time1-start_Time1;
					String upq = "UPDATE EXT_AO SET all_prod_blocked = 'N' WHERE WI_NAME ='"+sWorkitemId+"'";
					formObject.saveDataInDB(upq);
					String paperOut = "";
					String tmpGenOutputXML="";
					try { //Resolution for IP & Port NULL
						tmpGenOutputXML = executeAPTemplate("Welcome_Letter");
					if(isPaperJourney()){
						paperOut = executeAPTemplate("Other"); 
					}
					} catch (Exception e) {
						logError("Exception in Template Generation ATP-364",e);
					}
					
					if (!tmpGenOutputXML.isEmpty() && !getTagValues(tmpGenOutputXML,"Status").equals("0")) {
						String userDescription = getTagValues(tmpGenOutputXML,"UserDescription");
						sendMessageValuesList("", userDescription);
						return true;
					}
					if (isPaperJourney() && !paperOut.isEmpty() && !getTagValues(paperOut,"Status").equals("0")) {
						String userDescription = getTagValues(paperOut,"UserDescription");
						sendMessageValuesList("", userDescription);
						return true;
					}
					String query = "SELECT COUNT(A.NAME) AS COUNT FROM PDBDOCUMENT A, PDBDOCUMENTCONTENT B, PDBFOLDER C "
							+ "WHERE A.DOCUMENTINDEX=B.DOCUMENTINDEX AND B.PARENTFOLDERINDEX=C.FOLDERINDEX AND "
							+ "UPPER(A.NAME) IN ('WELCOME_LETTER', 'ADCB_WELCOME_LETTER', 'WELCOME_LETTER_PRINT') AND C.NAME='"+sWorkitemId+"'";
					logInfo("postSubmit","Query6: "+query);
					List<List<String>> lt = formObject.getDataFromDB(query);
					if(lt!=null && lt.size()>0){
						if(Integer.parseInt(lt.get(0).get(0)) < 3) { // changes for Template generation issue AO-BRN-YAH -1179844
							sendMessageValuesList("", "Some error occurred while generating Templates. Please try again later or contact app support team.");
							return false;
						}
					} 
				}
				end_Time1=System.currentTimeMillis();
				diff1=end_Time1-start_Time1;
			// Archival changes 05-12-2023	done by reyaz
				try {
					logInfo("onLoadCPDBulkEODChecker","Before archival calling");
					executeArchival();
					logInfo("onLoadCPDBulkEODChecker","Ater archival calling");
				} catch (Exception e) {

				}
			}
			createHistory();				
			createAllHistory();
			
		} 
		return true;
	}
	
	private void saveKycTxnData() {
		int count = -1;
		String updQuery = "";
		try {
			logInfo("saveKycTxnData","inside");
			logInfo("saveKycTxnData","deals in wmd : "+formObject.getValue(DEALS_IN_WMD));
			logInfo("saveKycTxnData","salary transfer : "+formObject.getValue(SALARY_TRANSFER));
			String sQuery = "SELECT CID FROM ACC_RELATION_REPEATER WHERE WI_NAME = '"+sWorkitemId+"'";
			logInfo("saveKycTxnData","sQuery : "+sQuery);
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("saveKycTxnData","");
			if (sOutput != null &&  sOutput.size() > 0){
				for (int i=0; i<sOutput.size() ; i++){
					String sQuery2 = "SELECT COUNT(1) FROM bpm_kyc_questions_txn WHERE CUST_ID = '"+sOutput.get(i).get(0)+"'";
					List<List<String>> sOutput2 = formObject.getDataFromDB(sQuery2);
					logInfo("saveKycTxnData","sQuery2 : "+sQuery2);
					if(sOutput2 != null &&  sOutput2.size() > 0){
						count = Integer.parseInt(sOutput2.get(0).get(0));
						logInfo("saveKycTxnData","count : "+count);
					}
					if(count == 1){
						updQuery = "UPDATE bpm_kyc_questions_txn SET "
								+ "deals_in_wmd = '"+formObject.getValue(DEALS_IN_WMD)+"' , "
								+ "salary_transfer = '"+formObject.getValue(SALARY_TRANSFER)+"' "
								+ "WHERE CUST_ID = '"+sOutput.get(i).get(0)+"'";
						logInfo("saveKycTxnData","updQuery : "+updQuery);
						formObject.saveDataInDB(updQuery);
					} else{
						//						updQuery = "INSERT INTO bpm_kyc_questions_txn (CUST_ID, DEALS_IN_WMD, SALARY_TRANSFER)  "
						//								+ "VALUES ('"+sOutput.get(i).get(0)+"','"+formObject.getValue(DEALS_IN_WMD)+"','"+formObject.getValue(SALARY_TRANSFER)+"')";
						//						logInfo("saveKycTxnData","updQuery : "+updQuery);
						//						formObject.saveDataInDB(updQuery); //Commented for invalid column on 27042023(Performanceissue) by Ameena
						if(!sOutput.get(i).get(0).equalsIgnoreCase("")){
							updQuery = "INSERT INTO bpm_kyc_questions_txn (CUST_ID, DEALS_IN_WMD, SALARY_TRANSFER)  "
									+ "VALUES ('"+sOutput.get(i).get(0)+"','"+formObject.getValue(DEALS_IN_WMD)+"','"+formObject.getValue(SALARY_TRANSFER)+"')";
							logInfo("saveKycTxnData","updQuery : "+updQuery);
							formObject.saveDataInDB(updQuery);
						}
					}
				}
			}
			logInfo("saveKycTxnData","ends");
		} catch (Exception e) {
			logError("saveKycTxnData",e);
		}
	}

	public void changeFieldsBkColor(String wi_name, String custSerialNo) {
		logInfo("changeFieldsBkColor","**************Background color call***************");
		String labelId = "";
		String sQuery="SELECT label_ID FROM USR_0_FIELD_MAPPING WHERE DBCOLUMN IN "
				+ "(SELECT FIELD_Name FROM USR_0_MAKER_CHANGE_TRACKER WHERE WI_NAME='"+wi_name+"'"
						+ " AND OLD_VALUE<>New_Value AND cust_sno='"+custSerialNo+"')";
		List<List<String>> output=formObject.getDataFromDB(sQuery);
		if(output!=null && output.size()>0){
			for(int i=0; i<output.size(); i++){
				labelId = labelId +","+output.get(i).get(0);
			}
		}
		String arrLabelId[]=labelId.split(",");
		for(int i=0;i<arrLabelId.length;i++) {
			//formObject.setNGBackColor(arrLabelId[i],Color.yellow); have to ask yamini
		}
	}

	public void onTabClick(String data) {
		try {
			logInfo("onTabClick", "INSIDER");
			String[] selectedSheetInfo = data.split(",");
			String tabCaption = selectedSheetInfo[0];
			int selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 || 
					selectedSheetIndex == 4 || selectedSheetIndex == 5) {
				logInfo("onTabClick", "INSIDE qwert");
				disableControls(new String[]{BTN_SUBMIT});
				logInfo("onTabClick", "custInfoTabLoad: "+custInfoTabLoad+", custSno: "+custSno);
				if(!custInfoTabLoad || !custSno.equalsIgnoreCase(formObject.getValue(SELECTED_ROW_INDEX).toString())) {
					custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
					custInfoTabLoad = true;
					formObject.applyGroup(CONTROL_SET_MANUAL);
					setManualFieldsBlank();
					setCPDCheckerCombos();
					populatePersonalDataCPD();
					populateRiskData();
					populateKYCData();
					populateKycMultiDrop();
					populatePreAssesmentDetails(); //Added by krishna
					populatePepAssesmentDetails();
					populateCRSData();   
					PopulatePrivateClientQuestions(); 
					populateComparisonFields();
					logInfo("onTabClickBULKEODChhecker","+++NOVA+++");
					loadDedupeSearchData(sWorkitemId);
					makeEnableRekeyFields();
					rekeyloaded=true;
//					updateFlag("false"); //9aug2021
//					insertIntoReKeyTemp("CPD");
					populateReKey();
					populateReKeyTemp("CPD");
					// Upgrade changes Krishna
					if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
						reKeyInsert();
						updateReKeyTemp("CPD");    
					}
				}
				//reKeyLogic(); added in savandnext fn
				/*if(selectedSheetIndex != 1){
					reKeyLogic();
					if(sendMessageList.size()>0) {
						return;
					}
				}*/
				int fieldToFocus=Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String bnk_relation= formObject.getTableCellValue(ACC_RELATION,fieldToFocus,7);
				if(bnk_relation.equalsIgnoreCase("New")||bnk_relation.equalsIgnoreCase("Existing")) {
					String segment=formObject.getValue(PD_CUSTSEGMENT).toString();
					logInfo("onTabClickBULKEODChhecker","customer segment:"+segment);
					visibleonSegmentBasisCPDCHECKER(segment);
					String req_type=formObject.getValue(REQUEST_TYPE).toString();;
					if(req_type.equalsIgnoreCase("New Account with Category Change") || req_type.equalsIgnoreCase("Category Change Only")) {
						manageCategoryChangeSectionCPDChecker();
					}
				}
				/*if(!validationTab()) {
					return;
				}*/
				/*updateFlag("false");
				reKeyLogic();
				insertIntoReKeyTemp("CPD");*/
				String value=formObject.getValue(NIG_CPDMAKER).toString();
				if(value.equalsIgnoreCase("yes")) {
					/*int respose=JOptionPane.showConfirmDialog(null,"Selected passport holder Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
					if(respose==JOptionPane.YES_OPTION) {
						formObject.setValue(NIG_CPDCHECKER,"YES");
						String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDCHECKER='YES' Where WI_NAME='"+formObject.getValue(WI_NAME)+"'";// AND CUST_SNO ='"+SNO+"'";
						formObject.saveDataInDB(updatequery);
						System.out.println("Updated Successfully");
					}*/
					sendMessageValuesList("", "Selected passport holder Residents does not meet conditions,"
							+ "\nHence not allowed to open Account. Do you still want to proceed with account"
							+ " opening?");
				}
			} else if(selectedSheetIndex==11) {
				disableControls(new String[]{BTN_SUBMIT});
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID") && sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {						
					String sQuery ="SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME AS NAME FROM PDBUSER WHERE UPPER(USERNAME) =UPPER('"+sUserName+"')";
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
					logInfo("onTabClickBULKEODChhecker","sOutput...."+sOutput);
					if(sOutput!=null && sOutput.size()>0){
						formObject.setValue(CHECKER_NAME_CAT_CHANGE,sOutput.get(0).get(1));
						formObject.setValue(CHECKER_CODE_CAT_CHANGE,sOutput.get(0).get(0));
					}
					sQuery ="SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"') AND PROCESSDEFID='"+sProcessDefId+"'";
					sOutput=formObject.getDataFromDB(sQuery);
					logInfo("onTabClickBULKEODChhecker","sOutput...."+sOutput);
					if(sOutput!=null && sOutput.size()>0){
						formObject.setValue(CHECKER_DEPARMENT_CAT_CHANGE,sOutput.get(0).get(0));
					}
				}
			} else if(selectedSheetIndex==6) {
				disableControls(new String[]{BTN_SUBMIT});
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
					populateScreeningDataCRO();
				}
				populateTRSD();
				if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")) {
					formObject.setValue(FINAL_ELIGIBILITY, "Eligible");
				} else {
					formObject.setValue(FINAL_ELIGIBILITY, "Not Eligible");
					//disableControls(new String[]{BTN_TRSD_CHECK,BTN_CPD_TRSD_CHK});	
				}
				disableControls(new String[]{BTN_TRSD_CHECK});// should come outside its common for if and else
			} else if(selectedSheetIndex == 7) {// upgrade changes
				if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")){
					if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
						populateScreeningDataCRO();
					}
					int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
					logInfo("onTabCPDMakerFourStep","INSIDE iSelectedRow "+iSelectedRow+"");
					//String sInputXML = getIndRiskInputXML(iSelectedRow);
				// AO DCRA COMMENTED //15082023	
		/*                      String sInputXML = executeApplicationAssessmentRiskRetail(iSelectedRow);
					logInfo("onTabCPDMakerFourStep","sInputXML in CPD: "+sInputXML);
					String sOutputxml = "";
					if(sInputXML.contains("<APWebService_Input>")){
						sOutputxml = socket.connectToSocket(sInputXML);
						//Added by Jamshed
						//AO DCRA COMMENT 16082023
						XMLParser xp = new XMLParser(sOutputxml);
						String finalRisk_cd = xp.getValueOf("finalRisk");
						logInfo("executeApplicationAssessmentRiskRetail", "finalRisk_cd :  "+ finalRisk_cd);						
						String finalRisk_cd_query ="select risk_value from usr_0_risk_values where risk_code='"+finalRisk_cd+"'";
						logInfo("executeApplicationAssessmentRiskRetail","finalRisk_cd_query= "+ finalRisk_cd_query);
						List<List<String>> output_list_db =formObject.getDataFromDB(finalRisk_cd_query); 
						String finalRisk_value=output_list_db.get(0).get(0); 
						logInfo("executeApplicationAssessmentRiskRetail","finalRisk_value= "+ finalRisk_value);
						sOutputxml=finalRisk_value;
					}
					else{
						sOutputxml = sInputXML;
					}
					logInfo("onTabDDEcheker","sOutput:-------------"+sOutputxml);
					if(!sOutputxml.equalsIgnoreCase("")) {
						String sSegment =formObject.getValue(PD_CUSTSEGMENT).toString();
						
						if(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only") 
								|| (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("New Account with Category Change") 
										&& iSelectedRow == 1)) {
							sSegment= formObject.getValue(NEW_CUST_SEGMENT).toString();
						}
						
						if((!sOutputxml.equalsIgnoreCase("Unacceptable Risk") && !sOutputxml.equalsIgnoreCase("PEP") 
								&& !sOutputxml.equalsIgnoreCase("UAE-PEP") && !sOutputxml.equalsIgnoreCase("Non UAE-PEP")
								&& !sOutputxml.equalsIgnoreCase("Increased Risk")) // Added by Ameena 
								&& sSegment.equalsIgnoreCase("Private Clients"))
							// added by krishna
						{
							sOutputxml="Medium Risk";
						}
		 */
						logInfo("onTabDDEcheker>>> ","Cust Segment" + formObject.getValue(NEW_CUST_SEGMENT).toString());
				     	logInfo("onTabDDEcheker>>>","Request Type"+formObject.getValue(REQUEST_TYPE).toString());

						String sWsName = formObject.getValue(CURR_WS_NAME).toString();
					String sQuery = "SELECT CURRENT_RISK_BUSSINESS FROM USR_0_RISK_ASSESSMENT_DATA "
							+ "WHERE WI_NAME= '"+ sWorkitemId+ "'";
					List<List<String>> custCurrent = formObject.getDataFromDB(sQuery);
					if(custCurrent != null && custCurrent.size() > 0) {        
						String value = custCurrent.get(0).get(0); 
						logInfo("onTabDDEcheker>>>","Upgrade"+value);
						String sriskColumn = "SNO,WI_NAME,WS_NAME,CUST_CUR_RISK_BANK";
						String sriskValue= "'"+iSelectedRow+"','"+sWorkitemId+"','"+sWsName+"','"+value+"'";	
						logInfo("sriskColumn",sriskColumn);
						logInfo("sriskValue",sriskValue);
						insert_Into_Usr_0_Risk_Data(sriskColumn,sriskValue);
						String sUpdateDecision="update USR_0_CUST_TXN set CPD_CUST_INDI_RISK='"+ value+"'"
								+ " Where WI_NAME='"+ sWorkitemId +"' and cust_sno='"+iSelectedRow+"'";
						logInfo("onTabDDEcheker","sUpdateDecision: "+sUpdateDecision);
						formObject.saveDataInDB(sUpdateDecision);
						loadCPDcustdata();
						String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
						int sOutput= updateDataInDB("USR_0_BRMS_TRACKER","SCREENING_STATUS","'Success'",
								"WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"' AND SCREENING_STATUS ='Pending'");
						logInfo("onTabCPDMakerFourStep","sOutput----"+sOutput);
					}	
						saveScreeningDataCPD();//made
						calculateAppRiskCPD();
					//		 }//krishna
				}
				populateScreeningDataCPD();
				populateTRSDCPD();
				if(formObject.getValue(CPD_TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved")) {
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
				} else {
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
				}
				disableControls(new String[]{BTN_CPD_TRSD_CHK});// should come outside its common for if and else
			} else if(selectedSheetIndex == 8) {	 // doubt in this condition
				disableControls(new String[]{BTN_SUBMIT});
				List<List<String>> recordList = null;
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
			} else if(selectedSheetIndex == 9) {
				logInfo("onTabClick", "selectedSheetIndex is 9");
				loadApplicationAssessmentDataCPD();						
				String sQuery= "";
				List<List<String>> recordList = null;
				logInfo("onTabClick", "getGridCount(PROD_SEC_OFRD_CPD_LVW): "+getGridCount(PROD_SEC_OFRD_CPD_LVW));
				if(!formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID")) {
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
				}
			} else if(selectedSheetIndex == 10) {// else if(selectedSheetIndex==8) {// ACCOUNT INFO is on 10 number // newly added
				disableControls(new String[]{BTN_SUBMIT});
				if(formObject.getValue(SCAN_MODE).toString().equalsIgnoreCase("New WMS ID") && sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {						
					String sQuery ="SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME AS NAME FROM PDBUSER WHERE UPPER(USERNAME) =UPPER('"+sUserName+"')";
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
					logInfo("onTabClickBULKEODChhecker","sOutput...."+sOutput);
					if(sOutput!=null && sOutput.size()>0){
						formObject.setValue(CHECKER_NAME,sOutput.get(0).get(1));
						formObject.setValue(CHECKER_CODE,sOutput.get(0).get(0));
					}
					sQuery ="SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"') AND PROCESSDEFID='"+sProcessDefId+"'";
					sOutput=formObject.getDataFromDB(sQuery);
					logInfo("onTabClickBULKEODChhecker","sOutput...."+sOutput);
					if(sOutput!=null && sOutput.size()>0){
						formObject.setValue("CHECKER_DEPT",sOutput.get(0).get(0));
					}
				}
				String sQuery= "SELECT CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE FROM USR_0_DEBITCARD_EXISTING_CPD WHERE WI_NAME = '"+sWorkitemId+"'";		
				List<List<String>> recordList = formObject.getDataFromDB(sQuery);
				log.info("query for account info " +sQuery);
				loadListView(recordList,"CUST_ID,EMBOSS_NAME,CARD_TYPE_DESC,STATUS_DESC,ISSUANCE_DATE,EXPIRY_DATE",ACC_INFO_EDC_LVW);	
				clearUdfGrid();
				int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString())+1;
				String sQuery2="SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
				populateUDFGrid(sQuery2);
			}
			if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				disableControls(new String[]{BTN_SUBMIT});
				loadSICombos();
				populateStndInstr();
			} else if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				disableControls(new String[]{BTN_SUBMIT});
				Frame_delivery();
			} else if(tabCaption.equalsIgnoreCase("Decision")) {
				String query = "";
				enableControls(new String[]{BTN_SUBMIT});
				hideControls(new String[]{L1_APP_REQ,L2_APP_REQ,L2_APP_REQ,L2_APP_REQ});
				disableControls(new String[]{IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ,IS_CALL_BACK_REQ});
				
				
				
				if(getGridCount(DECISION_LVW) == 0) {
					String sQuery1 = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery1);
					log.info("decision history query "+sQuery1);
					loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",DECISION_LVW);							
				}
				try {
					hideControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
					String sQuery1 = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME ='"+sWorkitemId+"'";	
					List<List<String>> sOutput=formObject.getDataFromDB(sQuery1);
					if(sOutput!=null && sOutput.size()>0){
						String minorDOB =sOutput.get(0).get(0);
						int minorAge = CalculateAge(minorDOB);
						if((minorAge >= 18) && (minorAge <= 21)){
							sQuery1 = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER WHERE acc_relation = 'Guardian' AND WI_NAME ='"+sWorkitemId+"'";
							sOutput=formObject.getDataFromDB(sQuery1);
							if(sOutput!=null && sOutput.size()>0){
								String guardianCount = sOutput.get(0).get(0);
								if("0".equalsIgnoreCase(guardianCount)){
									showControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
									formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, FALSE);
									formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, FALSE);
								}
							}
						}
					}
					sQuery1 = "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
					sOutput=formObject.getDataFromDB(sQuery1);
					if(sOutput!=null && sOutput.size()>0){
						String sProduct =sOutput.get(0).get(0);
						logInfo("onTabClickBULKEODChhecker","sProduct----"+sProduct);
						if("871".equalsIgnoreCase(sProduct)){
							hideControls(new String[]{DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC});
						}	
					}
				} catch (Exception e) {
					logError("Exception in onTabclick",e);
				}
				if(formObject.getValue(CHQ_ELIGIBILITY).toString().equalsIgnoreCase("None")) {
					formObject.setValue(CRO_DEC,"Return to Originator");
					formObject.setValue(CRO_REJ_REASON,"Bounced cheques > 3");
					formObject.setValue(CRO_REMARKS,"Bounced cheques are more than 3");
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
			} else if(tabCaption.equalsIgnoreCase("Category Change")) {
				disableControls(new String[]{BTN_SUBMIT});
				manageCategoryChangeSectionCPDChecker();
			}
		} catch (Exception e) {
			logInfo("onTabClick","tab details: "+data);
			logError("onTabClick", e);
		}
	}

	/*public void makeEnableRekeyFields() {
		logInfo("makeEnableRekeyFields","INSIDE makeEnableRekeyFields");
		try {
			if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO_CHECK)||sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)) {
				System.out.println("INSIDE IF");
				String FrameNames[]={SEC_PERSONAL_DET,SEC_CONTACT_DET_CP,SEC_CONTACT_DET_PA,SEC_INT_DETAIL,SEC_PERSONAL_DET,FRAME_CLIENTQUESTIONS,SEC_CONTACT_DET_RA,SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_INT_DETAIL,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,SEC_FUND_EXP_RELTNSHP,SEC_ASSESS_OTH_INFO,SEC_BNK_REL_UAE_OVRS,SEC_SIGN_OFF};
				disableControls(FrameNames);
			} else {
				logInfo("makeEnableRekeyFields","INSIDE sAcitivityName"+sActivityName);
				String FrameNames[]={SEC_PERSONAL_DET,FRAME_CLIENTQUESTIONS,FrameFATCA,SEC_CRS_DETAILS,CRS_CHECKBOX};
				disableControls(FrameNames);
			}
			String controlDisable[]={FCR_PREFIX,FCR_FIRSTNAME,FCR_LASTNAME,FCR_NAME,FCR_SHORTNAME,FCR_MOTHERSNAME,
					FCR_EIDANO,FCR_ADDRESS,FCR_CNTRY,FCR_STATE,FCR_CITY,FCR_RESIDENT,FCR_PER_CNTRY,FCR_PH,FCR_MOBILE,
					FCR_EMAIL,FCR_DOB,FCR_PASSPORTNO,FCR_PASSPORTISSDATE,FCR_PASSPORTEXPDATE,FCR_PASSTYPE,FCR_NATIONALITY
					,FCR_COUNTRYBIRTH,CITYBIRTH_FCR,FCR_VISANO,FCR_VISAISSDATE,FCR_VISAEXPDATE,FCR_PROFESSION,FCR_GENDER,
					FCR_EMPLYR_NAME,EIDA_PREFIX,EIDA_FIRSTNAME,EIDA_LASTNAME,EIDA_NAME,EIDA_SHORTNAME,EIDA_MOTHERNAME,EIDA_EIDANO,
					EIDA_ADDRESS,EIDA_CNTRY,EIDA_STATE,EIDA_CITY,EIDA_RESIDENT,EIDA_PER_CNTRY,EIDA_PH,EIDA_MOBILE,EIDA_EMAIL,EIDA_DOB,
					EIDA_PASSPORTNO,EIDA_PASSPORTISSDATE,EIDA_PASSPORTEXPDATE,EIDA_PSSTYPE,EIDA_NATIONALITY,EIDA_COUNTRYBIRTH,CITYBIRTH_EIDA,
					EIDA_VISANO,EIDA_VISAISSDATE,EIDA_VISAEXPDATE,EIDA_PROFESSION,EIDA_GENDER,EIDA_EMPLYR_NAME,MANUAL_PREFIX,MANUAL_FIRSTNAME,
					MANUAL_LASTNAME,MANUAL_NAME,MANUAL_SHORTNAME,MANUAL_MOTHERNAME,MANUAL_EIDANO,MANUAL_ADDRESS,MANUAL_CNTRY,MANUAL_STATE,MANUAL_CITY,MANUAL_RESIDENT,
					MANUAL_PER_CNTRY,MANUAL_PH,MANUAL_MOBILE,MANUAL_EMAIL,MANUAL_DOB,MANUAL_PASSPORTNO,MANUAL_PASSPORTISSDATE,MANUAL_PASSPORTEXPDATE,MANUAL_PASSTYPE,MANUAL_NATIONALITY,MANUAL_COUNTRYBIRTH,MANUAL_VISANO,MANUAL_VISAISSDATE,MANUAL_VISAEXPDATE,MANUAL_PROFESSION,MANUAL_GENDER,MANUAL_EMPLYR_NAME,FCR_VISASTATUS}; 
			disableControls(controlDisable);
			String checkBoxDisable[]={CHECKBOX_PREFIX_FCR,CHECKBOX_FULLNAME_FCR,CHECKBOX_SHORTNAME_FCR,CHECKBOX_DOB_FCR,
					CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASS_ISS_DT_FCR,CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_VISA_NO_FCR,
					CHECKBOX_VISA_ISSUE_DATE_FCR,CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_NATIONALITY_FCR,CHECKBOX_MOTHERSNAME_FCR,
					CHECKBOX_EIDANO_FCR,CHECKBOX_CORR_POB_FCR,CHECKBOX_CITY_FCR,CHECKBOX_STATE_FCR,CHECKBOX_COUNTRY_PER_RES_FCR,
					CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_MOB_FCR,CHECKBOX_EMAIL_FCR,CHECKBOX_PROFESSION_FCR,CHECKBOX_CNTRY_OF_CORR_FCR,
					CHECKBOX_GENDER_FCR,CHECKBOX_EMP_NAME_FCR,CHECKBOX_COUNTRY_RES_FCR,CHECKBOX_PREFIX_EIDA,CHECKBOX_FULLNAME_EIDA,
					CHECKBOX_SHORTNAME_EIDA,CHECKBOX_DOB_EIDA,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASS_ISS_DT_EIDA,CHECKBOX_PASS_EXP_DT_EIDA,
					CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_ISSUE_DATE_EIDA,CHECKBOX_VISA_EXPIRY_DATE_EIDA,CHECKBOX_NATIONALITY_EIDA,CHECKBOX_MOTHERSNAME_EIDA,
					CHECKBOX_EIDANO_EIDA,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CITY_EIDA,CHECKBOX_STATE_EIDA,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_COUNTRY_PER_RES_EIDA,CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_EMAIL_EIDA,CHECKBOX_PROFESSION_EIDA,CHECKBOX_GENDER_EIDA,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_COUNTRY_RES_EIDA,CHECKBOX_PREFIX_MANUAL,CHECKBOX_FULLNAME_MANUAL,CHECKBOX_SHORTNAME_MANUAL,CHECKBOX_PASSPORT_NO_MANUAL,CHECKBOX_PASS_ISS_DT_MANUAL,CHECKBOX_PASS_EXP_DT_MANUAL,CHECKBOX_VISA_NO_MANUAL,CHECKBOX_VISA_ISSUE_DATE_MANUAL,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,CHECKBOX_NATIONALITY_MANUAL,CHECKBOX_MOTHERSNAME_MANUAL,CHECKBOX_CORR_POB_MANUAL,CHECKBOX_CITY_MANUAL,CHECKBOX_STATE_MANUAL,CHECKBOX_CNTRY_OF_CORR_MANUAL,CHECKBOX_COUNTRY_PER_RES_MANUAL,CHECKBOX_TELE_RES_MANUAL,CHECKBOX_PROFESSION_MANUAL,CHECKBOX_GENDER_MANUAL,CHECKBOX_EMP_NAME_MANUAL,CHECKBOX_COUNTRY_RES_MANUAL,CHECKBOX_COUNTRY_RES_MANUAL,CHECKBOX_CORR_POB_FCR,CHECKBOX_SELECTALL_EIDA,CHECKBOX_SELECTALL_MANUAL,CHECKBOX_FIRSTNAME_FCR,CHECKBOX_FIRSTNAME_EIDA,CHECKBOX_LASTNAME_FCR,CHECKBOX_LASTNAME_EIDA,CHECKBOX_LASTNAME_MANUAL,CHECKBOX_SELECTALL_FCR,CHECKBOX_CORR_POB_EIDA,CHECKBOX_CORR_POB_MANUAL,CHECKBOX_PASSPORT_TYPE_FCR,CHECKBOX_PASSPORT_TYPE_EIDA,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_COB_FCR,CHECKBOX_COB_EIDA,CHECKBOX_COB_MANUAL,BTN_DEDUPE_SEARCH,CHECKBOX_VISA_STATUS_MANUAL,CHECKBOX_VISA_STATUS_FCR,CHECKBOX_VISA_STATUS_EIDA,CHECKBOX_FIRSTNAME_MANUAL};
			disableControls(checkBoxDisable);
			populateReKey();
		} catch(Exception e) {
			logError("makeEnableRekeyFields",e);
		}
	}*/
	
	private boolean showOpenCallJSP(String sResult) {
		long st=System.currentTimeMillis();
		long end_Time=System.currentTimeMillis();
		long diff=System.currentTimeMillis();
		
		String sQuery ="SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME ='"+sWorkitemId+"' AND STATUS<>'Success' and mandate_status='Mandatory'";
		logInfo("showOpenCallJSP","Mandatory Query "+sQuery);
		List<List<String>> output = formObject.getDataFromDB(sQuery);
		logInfo("showOpenCallJSP","Mandatory output "+output);
		if(output!=null && output.size()>0){
			if(!output.get(0).get(0).equalsIgnoreCase("0")) {
				sResult="Mandatory";
			}
		}
		
		if(sResult.equalsIgnoreCase("Mandatory")) {
			sendMessageValuesList("", "Mandatory calls are not successful.Please close the workitem and try again later.");
			return false;
		} else if(!sResult.equalsIgnoreCase("Success")){
			String decision=formObject.getValue(CRO_DEC).toString();
			if(sActivityName.equalsIgnoreCase(ACTIVITY_CPD_CHECKER)){
				String sUpdateDecision="update "+sExtTable+" set cpd_chkr_dec='"+ decision +"' Where WI_NAME='"+ sWorkitemId +"'";
				formObject.saveDataInDB(sUpdateDecision);
			} else {
				sendMessageValuesList("","Please close and the workitem and try again later");
				return false;
			}
		}
		updateForAdvance_search(sWorkitemId);
		st=System.currentTimeMillis();
		sQuery ="SELECT COUNT(WI_NAME) AS COUNT FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME ='"+sWorkitemId+"' AND STATUS='Success'";
		 output = formObject.getDataFromDB(sQuery);
		end_Time=System.currentTimeMillis();
		diff=st-end_Time;
		if(output!=null && output.size()>0){
			if(output.get(0).get(0).equalsIgnoreCase("0")) {
				sendMessageValuesList("","No call is successful yet. Please close the WI and open again.");
				return false;
			}
		}
		loadIntegrationDataOnForm();
		int iResult = validateIntegrationCalls();
		if(iResult == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean validateSI(String sDate,String controlName) {
		try {					 
			logInfo("validateSI","Called validateSIDate ");
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpledateformat = new SimpleDateFormat(DATEFORMAT);
			String scurrentDate = simpledateformat.format(calendar.getTime());
			if(sDate.equalsIgnoreCase("")) {
				return true;
			}
			logInfo("validateSI","date :"+sDate + "\n "+ "scurrentDate "+scurrentDate);
			if(!scurrentDate.equals("")) {
				String [] temp =scurrentDate.split("/");
				if(temp[1].length() ==3) {
					scurrentDate=temp[0]+"/"+getMonthNumber(temp[1])+"/"+temp[2];
				} else {
					scurrentDate=temp[0]+"/"+temp[1]+"/"+temp[2];
				}
			}
			try {
				String [] temp =sDate.split("/");
				if(temp.length<3) {
					sendMessageValuesList("", "Invalid Date");
					return false;
				}
			} catch(Exception e) {
				logError("Exception in validateSI",e);
			}
			Date currentDate = simpledateformat.parse(scurrentDate);
			Date nDate = simpledateformat.parse(sDate);   
			logInfo("validateSI","currentDate :"+currentDate +"\n "+"nDate "+nDate);
			if(nDate.compareTo(currentDate) < 0) {
				sendMessageValuesList("", controlName+""+CA0129);
			} else if(nDate.compareTo(currentDate) == 0) {
				sendMessageValuesList("", controlName+""+"Date can not be same as system date");
				return false;
			} else if(Integer.parseInt(sDate.substring(sDate.lastIndexOf("/")+1, sDate.length()))>= 2099) {    
				sendMessageValuesList("", controlName+" Date can not be greater than 2099");
				return false;
			}
		} catch(Exception exp) {
			logInfo("validateSI","Caught the exception "+exp.getMessage());
			logError("Exception in validateSI",exp);
		}	
		return true;
	}
	
	public boolean saveAndNextValidation(String data) {
		try {
			logInfo("saveAndNextValidation", "INSIDE");
			int selectedSheetIndex = Integer.parseInt(data);
			logInfo("saveAndNextValidation","selectedSheetIndex: "+selectedSheetIndex);
			if(selectedSheetIndex == 1){
				if(!validationTab()) {
					return false;
				}
				if(!reKeyLogic()) {
					logInfo("saveAndNextValidation","rekey failed");
					insertIntoReKeyTemp("CPD");
					return false;
				}
				updateFlag("true"); //9aug2021
			}
//			updateFlag("false"); //9aug2021
		} catch (Exception e) {
			logError("Exception in saveAndNextValidation",e);
		}
		return true;
	}
	
}
