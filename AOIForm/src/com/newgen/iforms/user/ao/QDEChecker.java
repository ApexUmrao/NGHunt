package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class QDEChecker extends Common implements Constants, IFormServerEventHandler {
	String CompAppReq = "No";
	boolean custInfoTabLoad = false;
	String custSno = "";
	private Fulfillment fulfillment;

	public QDEChecker(IFormReference formObject) {
		super(formObject);
		
		fulfillment = new Fulfillment(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
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
	public String generateHTML(EControl arg0) {
		return null;
	}

	@Override
	public String getCustomFilterXML(FormDef arg0, IFormReference arg1, String arg2) {
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
              // unsued
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
	
	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		logInfo("Inside executeServerEvent >","");
		logInfo("executeServerEvent parameters ","Event: " + eventType + ", ControlName: " + 
		controlName + ", Data: " + data);
		sendMessageList.clear();
		List<List<String>> list;
		String sQuery = "";
		String sWMSID ="";
		String sID = "";
		String sServiceShortName = "";
		String sHomeBrShortName = "";
		sendMessageList.clear();
		logInfo("sendmessagelist : ",String.valueOf(sendMessageList));
		String retMsg = getReturnMessage(true ,controlName);
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				String fieldList = eventOnLoadQDEChecker(controlName, eventType, data);
				if(!fieldList.isEmpty()){
					return getReturnMessage(true, controlName, fieldList);
				}
				populateUAEPassInfoStatus(sWorkitemId);
				populatePreAssesmentDetails();  //shahbaz
				accountPurpose(); //AO DCRA
				additionalSource();
			   if (formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")) {
				   logInfo("loadExistingDebitCard","krishna");
				   int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
				   logInfo("loadExistingDebitCard","iPrimaryCust"+iPrimaryCust);
				   String sCustID = formObject.getTableCellValue(ACC_RELATION,
							iPrimaryCust-1, 2);
				   logInfo("loadExistingDebitCard","sCustID"+sCustID);
				   String sAccRelation = formObject.getTableCellValue(ACC_RELATION,
							iPrimaryCust-1, 7);
				   logInfo("loadExistingDebitCard","sAccRelation"+sAccRelation);
				      if (sAccRelation.equalsIgnoreCase("Existing")) {
				    	 logInfo("loadExistingDebitCard","krishna1");  
						loadExistingDebitCard("USR_0_DEBITCARD_EXISTING",
									FAC_EXST_LVW_CRO, sCustID);	
					}
			   }  

               //Mutilpe account bug
			    String sQuery5 = "SELECT CID FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME='"+ sWorkitemId+ "' ORDER BY TO_NUMBER(INSERTIONORDERID)";
				logInfo("QDE CHECKER sQuery5 ",sQuery5);
				List<List<String>> sOutput5 = formObject.getDataFromDB(sQuery5);
				for(int i = 0; i < sOutput5.size(); i++) {
					 logInfo(" QDE CHECKER sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14));
					 formObject.setTableCellValue(PRODUCT_QUEUE, i, 14 ,i+1+"");
			         logInfo(" QDE CHECKER sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14)); 
				}
			   
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) {
				if(TABCLICK.equalsIgnoreCase(controlName)) {
					logInfo("onTabClick1","onTabClick1");
					onTabClick(data);
					logInfo("onTabClick","onTabClick");
				} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
					if(saveAndNextValidation((String)data.split(",")[1])){
							return getReturnMessage(true, controlName);
						}
				} else if(controlName.equalsIgnoreCase(BTN_ECB_REFRSH)) {
					loadECBChqBookValidation();
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("afterJSP")) {
					if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
						if(afterSubmitJSP(data)) {
							return getReturnMessage(true, controlName, CA008);
						}
					}
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("%%%") 
						&& !data.contains("interSubmit") && !data.contains("afterJSP")) {
					if(submitValidation(data)) {
						insertIntoTrackChangeModify(); //Ameena 13012023
						if(sendMessageList.size()>  0) {
							return getReturnMessage(true,controlName,sendMessageList.get(1));
						} else {
							if(submitQDECheckerValidations(data)) {
								if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") 
										&& !formObject.getValue(REQUEST_TYPE).toString()
										.equalsIgnoreCase("Category Change Only")) {
									return getReturnMessage(true,controlName,"openJSP");
								} else {
									return getReturnMessage(true, controlName, CA008);
								}
							}
						}
					}
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("interSubmit")) {
					if(submitValidationNIG(controlName,data)) {
						if(submitQDECheckerValidations(data)) {
							if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") 
									&& !formObject.getValue(REQUEST_TYPE).toString()
									.equalsIgnoreCase("Category Change Only")) {
								return getReturnMessage(true,controlName,"openJSP");
							} else {
//								return getReturnMessage(true,controlName,"submitDone");
								return getReturnMessage(true,controlName,CA008);
							}
						}
					}/* else {
						return getReturnMessage(false,controlName);
					}*/
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("confirmSubmit")) {
					setRestrictedFlag();
					if(submitWorkitem(controlName,data)) {
						logInfo("executeServerEvent","submitWorkitem finallySubmitDone");
						return getReturnMessage(true,controlName);
					}
				} else if("generateInstanLetter".equalsIgnoreCase(controlName)){
					//if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))
				       if((!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only"))&& 
					     (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade"))){
					logInfo("generateInstanLetter","data :"+data); 
					String count = "-1";
						if(!"docAttachedAlready".equalsIgnoreCase(data)){
							if("true".equalsIgnoreCase(data)){
								if(instantrequestsent == 1 )//request has been sent earlier
								{
									String queryforwl = "SELECT COUNT(WORKITEM_NAME) AS COUNT_TEMP FROM"
											+ " NG_TEMPLATE_TRANSACTION WHERE WORKITEM_NAME = '"+ sWorkitemId +"' AND TMP_CATEGORY ="
											+ " 'Instant_Welcome_Letter' AND GENERATION_STATUS != 'Y'"; 
									logInfo("generateInstanLetter","query for COUNT_temp  :"+queryforwl);
									List<List<String>> outputwl = formObject.getDataFromDB(queryforwl);
									logInfo("","output for COUNT_temp  :"+outputwl);
									if (outputwl != null && outputwl.size() > 0 ){
										count = outputwl.get(0).get(0);
									}
									if(count.equalsIgnoreCase("0")) {
										logInfo("generateInstanLetter","No template that is getting generated even though request was sent earlier so requesting again");
										logInfo("generateInstanLetter","Calling generateInstLetter  :");
										return getReturnMessage(generateInstLetter(sWorkitemId));
									} else {
										logInfo("","template is getting generated");
										return getReturnMessage(true);
									}
								}
								if(instantrequestsent == 0 )
								{
									logInfo("","Calling generateInstLetter  :");
									return getReturnMessage(generateInstLetter(sWorkitemId));
								}
							} else{
								logInfo("","Does not want to generate letter here");

								logInfo("","Into generateInstLetter  :");
								String queryforwl ="select count(prod_code) as COUNT_wi from usr_0_product_selected where"
										+ " prod_code not in (select prod_code from USR_0_BLOCK_PROD_MAST) and wi_name  ='"+sWorkitemId+"'";
								logInfo("","query for unblocked product count  :"+queryforwl);
								List<List<String>> outputwl = formObject.getDataFromDB(queryforwl);
								logInfo("","output for unblocked product count  :"+outputwl);
								if (outputwl != null && outputwl.size() > 0 ){
									count = outputwl.get(0).get(0);
								}
								if(count.equalsIgnoreCase("0"))
								{
									logInfo("","No product that is not blocked");
									String sColumnWL="all_prod_blocked";
									String	 sValuesWL = "'Y'";
									String 	sWhereWL ="wi_name='"+sWorkitemId+"'";
									int	sOutputWL = updateDataInDB(sExtTable,sColumnWL,sValuesWL,sWhereWL); 
									logInfo("","sOutput templates wont be generated all products blocked "+sOutputWL);
								}
								else
								{
									logInfo("","AT accrelation CALLING FOR Instant welcome Letter  :");
									String sColumnWL1="all_prod_blocked";
									String	 sValuesWL1 = "'N'";
									String 	sWhereWL1 ="wi_name='"+sWorkitemId+"'";
									int	sOutputWL1 = updateDataInDB(sExtTable,sColumnWL1,sValuesWL1,sWhereWL1); 
									logInfo("","sOutput "+sOutputWL1);
								}

							} 
						}
						logInfo("","Instant letter-*******----");
						logInfo("","@@@@@@@@@@ AT QDE CHECKER CALLING FOR EMAIL @@@@@@@@@@ "+sWorkitemId);
						String sProcName="SP_TemplateGeneration_Email";
						List<String> paramlist = new ArrayList<String>();
						paramlist.add ("Text :"+sWorkitemId);
						paramlist.add ("Text :"+"CPDchecker"); 
						List sOutput1 = formObject.getDataFromStoredProcedure(sProcName,paramlist);
						logInfo("","Output AT QDE CHECKER CALLING FOR EMAIL  :"+sOutput1);
						if(!(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate") &&
								formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("DFC"))) {
							logInfo("","@@@@@@@@@@ AT QDE CHECKER CALLING FOR SMS @@@@@@@@@@ "+sWorkitemId);
							String sProc = "SP_TemplateGeneration_SMS";
							paramlist.add ("Text :"+sWorkitemId);
							paramlist.add ("Text :"+"QDE");
							sOutput1 = formObject.getDataFromStoredProcedure(sProc,paramlist);
							logInfo("","Output AT QDE CHECKER CALLING FOR EMAIL  :"+sOutput1);
						}
						return getReturnMessage(true); 
				}else{
					return getReturnMessage(true); 
				}
			} else if(controlName.equalsIgnoreCase("BTN_FULFILLMENT_START")) {// Added for FULLFILMENT
				logInfo("executeServerEvent", "BTN_FULFILLMENT_START");
				fulfillment.pushMessageFulfillment();
			} else if(controlName.equalsIgnoreCase("BTN_FULFILLMENT_REFRESH")) {// Added for FULLFILMENT
				logInfo("executeServerEvent", "BTN_FULFILLMENT_REFRESH");
				fulfillment.refreshStatus();
			}
				/*//return getReturnMessage(true,controlName,"firstSubmit");
				 * if(submitValidation(controlName,data) && submitQDECheckerValidations(controlName, data)) {
					return getReturnMessage(true, controlName);
				}	*/	
				/* else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("interSecSubmit")) {
				submitQDECheckerValidations(controlName,data);
			    }*/
				//eventOnClickEventQDEChecker(controlName, eventType, data);
				/*if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("finalSubmit")) {
					submitWorkitem(controlName,data);
				} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("interSubmit")) {
					submitValidationNIG(controlName,data);
				}*/
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				logInfo("Inside eventOnChangeEventQDEChecker controlName1: ",controlName+" eventType: "+
						eventType+" data: "+data);
				eventOnChangeEventQDEChecker(controlName, eventType, data);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {  
				//eventOnLostFocusEventQDEChecker(controlName, eventType, data);
			}
		} catch (Exception e) {
			logError("Exception in Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("sendMessageList = "," "+sendMessageList);
			if(!sendMessageList.isEmpty()){
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"+ 
						sendMessageList.get(1).toString());
			}
		}
		return retMsg;
	}
	
	private String eventOnLoadQDEChecker(String controlName,String  eventType,String data) {
		logInfo("Start eventOnLoadQDEChecker "," controlName : "+controlName+" eventType : "+
				eventType+" data : "+data);	
		String fieldList = "";
		try {
			prefLang();
			int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			if(isExistingCustomer()) {
				fieldList = changeFieldsBackColor(Integer.toString(iProcessedCustomer+1));
			}//added on 11july2021
			logInfo(" Before populateAuditSearch ","  "); 
			populateAuditSearch("listvew_srch_fcr");			
			logInfo(" After populateAuditSearch","  "); 
			String sQuery = "SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME AS NAME FROM PDBUSER "
					+ "WHERE UPPER(USERNAME) =UPPER('"+sUserName+"')";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo(" sQuery ",sQuery);
			logInfo(" sOutput ",sOutput.toString());
			logInfo(" sOutput ",String.valueOf(sOutput.size()));
			if(sOutput != null && sOutput.size() > 0){
				formObject.setValue("CHECKER_CODE",sOutput.get(0).get(0));
				formObject.setValue("CHECKER_NAME",sOutput.get(0).get(1));
				formObject.setValue("CHECKER_CODE_CAT_CHANGE",sOutput.get(0).get(0));
				formObject.setValue("CHECKER_NAME_CAT_CHANGE",sOutput.get(0).get(1));
			}
			sQuery ="SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"') "
					+ "AND PROCESSDEFID='"+sProcessDefId+"'";
			sOutput = formObject.getDataFromDB(sQuery);
			if(sOutput != null && sOutput.size() > 0){
				formObject.setValue("CHECKER_DEPT",sOutput.get(0).get(0));
				formObject.setValue("CHECKER_DEPARMENT_CAT_CHANGE",sOutput.get(0).get(0));
			}
			sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"+ sWorkitemId +"' "
					+ "AND STATUS='Success'";
			sOutput = formObject.getDataFromDB(sQuery);
			String sCallName  = (sOutput != null && sOutput.size() > 0) ? sOutput.get(0).get(0) : "";
			logInfo(" sOutput ",sOutput.toString());
			logInfo(" sOutput ",String.valueOf(sOutput.size())); 
			if(sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION")) {
				loadIntegrationDataOnForm();
			}	
			setTemp_usr_0_product_selected();
			fieldValueUsr_0_Risk_Data();
			sQuery = "SELECT count(1) AS COUNT FROM USR_0_CUST_TXN WHERE WI_NAME='"
					+ sWorkitemId+ "' and PASS_TYPE='Diplomat'";
			sOutput = formObject.getDataFromDB(sQuery);
			int diplomatPassType = (sOutput != null && sOutput.size() > 0)  ?
					Integer.parseInt(sOutput.get(0).get(0).toString()) : 0;
					if (diplomatPassType > 0) {
						formObject.setStyle("P_ECB_CHQ_VALIDATION", DISABLE, FALSE);
					} else {
						formObject.setStyle("P_ECB_CHQ_VALIDATION", DISABLE, TRUE);
					}
					loadECBChqBookValidation();
					custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
		
			populatePOANationality(); //Jamshed
		
		} catch(Exception e) {
			logError("Exception in eventOnLoadQDEChecker  ",e);	
		} finally {
			logInfo("End eventOnLoadQDEChecker "," controlName : "+controlName+" eventType : "+
					eventType+" data : "+data);	
		}
		return fieldList;
	}
	
	private String eventOnClickEventQDEChecker(String controlName,String  eventType,String data) {
		logInfo("Inside eventOnClickEventQDEChecker controlName: ",controlName+" eventType: "+
	    eventType+" data: "+data);
		String retMsg = "";
		try{
			String productIndex = "0";
			String arr[] = null;
			if(data.indexOf(",") != -1){
			    arr = data.split(",");
				productIndex = arr[1];
			} 
			/*if(controlName.equalsIgnoreCase(BTN_ECB_REFRSH)) {
				loadECBChqBookValidation();
			} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("%%%")) {
				if(submitValidation(controlName,data)) {
				    return getReturnMessage(true,controlName,"firstSubmit");
				}
				if(submitValidation(controlName,data) && submitQDECheckerValidations(controlName, data)) {
					return getReturnMessage(true, controlName);
				}		 
			} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("interSubmit")) {
				submitValidationNIG(controlName,data);
			} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("interSecSubmit")) {
				submitQDECheckerValidations(controlName,data);
			} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("finalSubmit")) {
				submitWorkitem(controlName,data);
			}*/
		} catch(Exception e) {
			logError("Exception in eventOnClickEventQDEChecker ",e);
		} finally {
			logInfo("Outside eventOnClickEventQDEChecker ","  ");
		}
		return retMsg;
	}
	
	private void eventOnChangeEventQDEChecker(String controlName,String  eventType,String data) {
		logInfo("Inside eventOnChangeEventQDEChecker controlName: ",controlName+" eventType: "+
				eventType+" data: "+data);
		try{
			if (controlName.equalsIgnoreCase(CHECKBOX_DOB_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
				manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
				manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
				manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
				manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_PH);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
				manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_MANUAL)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_FCR)) {
				toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
				manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EIDANO_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA);
				manageManualFields(CHECKBOX_EIDANO_MANUAL,MANUAL_EIDANO);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_EMAIL_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA);
				manageManualFields(CHECKBOX_EMAIL_MANUAL,MANUAL_EMAIL);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_TELE_RES_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_RES_EIDA);
				manageManualFields(CHECKBOX_TELE_RES_MANUAL,MANUAL_PH);
			} else if(controlName.equalsIgnoreCase(CHECKBOX_DOB_EIDA)) {
				toggleCheckbox(controlName,CHECKBOX_DOB_FCR,CHECKBOX_DOB_EIDA);
				manageManualFields(CHECKBOX_DOB_MANUAL,MANUAL_DOB);
			} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)) {
				toggleCheckbox(controlName, CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA);
				manageManualFields(CHECKBOX_TELE_MOB_MANUAL, MANUAL_MOBILE);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_MOBILE,formObject.getValue(MANUAL_MOBILE).toString());
				}
			}
		} catch(Exception e) {
			logError("eventOnChangeEventQDEChecker",e);
		} finally {
			logInfo("eventOnChangeEventQDEChecker","finally");
		}
	}
	
	private void eventOnLostFocusEventQDEChecker(String controlName,String  eventType,String data) {
		
	}
	
	private void onTabClick(String data) {
		logInfo("Inside onTabClick  data : ",data);
		try {
			String[] selectedSheetInfo = null ;
			String tabCaption = "";
			int selectedSheetIndex = 0;
			if(data.indexOf(",") != -1){
				selectedSheetInfo = data.split(",");
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[1]);
			}else{
				selectedSheetInfo[0] = data;
				selectedSheetIndex = Integer.parseInt(selectedSheetInfo[0]);
			}
		    tabCaption = selectedSheetInfo[0];
			String sQuery = "";
			List<List<String>> sOutput = null;
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 
					|| selectedSheetIndex == 3 || selectedSheetIndex == 4){ //selectedSheetIndex == 0 removed
				if(!custInfoTabLoad || !custSno.equalsIgnoreCase(formObject.getValue(SELECTED_ROW_INDEX).toString())) {
					custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
					custInfoTabLoad = true;
					setCustScreeningCombos();
					formObject.applyGroup(CONTROL_SET_MANUAL); 
					populatePersonalData();
					populateKYCData();  		
					populateKycMultiDrop();
					populatePreAssesmentDetails();  //shahbaz
					populateComparisonFields();
					populatePepAssesmentDetails();   // AO DCRA
					PopulatePrivateClientQuestions(); 
					populateRiskDataQDE();
					loadDedupeSearchData(sWorkitemId);
					makeEnableRekeyFields();
//					populateReKey();
					populateReKeyTemp("CRO");
				}
			    /*if(selectedSheetIndex == 1){   added in saveandnext fn
					if(!validationTab()) {
						return;
					}
					reKeyLogic();
					insertIntoReKeyTemp("CRO");
				}*/	
				/*if(selectedSheetIndex != 1){
					reKeyLogic();
					if(sendMessageList.size()>0) {
						return;
					}
				}*/
			    insertIntoReKeyTemp("CRO");
				formObject.setStyle("Frame19", DISABLE, FALSE);   
				formObject.setStyle("Frame4", DISABLE, FALSE);	//full customer info tab
				formObject.setStyle("Frame6", DISABLE, FALSE);	// PErsonal tab
				Frame18_CPD_Disable();
				frameFatcaCpdDisable();
				int iProcessedCustomer = Integer.parseInt(formObject.getValue("SELECTED_ROW_INDEX").toString()) ;
				String name = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer , 1);
				String dob = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer , 5);
				String cust_id = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer , 2);
				if(!name.trim().equalsIgnoreCase("")) {
					formObject.setValue(TXT_CUSTOMERNAME, name);
				}
	            if(!dob.trim().equalsIgnoreCase("")) {
					formObject.setValue(TXT_DOB, dob);
				}
				if(!cust_id.trim().equalsIgnoreCase("")) {
					formObject.setValue(TXT_CUSTOMERID, cust_id);
				}
				formObject.setStyle(DRP_RESEIDA, DISABLE, TRUE);
				formObject.setStyle(ED_MONTHLY_INCM, DISABLE, TRUE);
				logInfo("onTabClick","is CHECKBOX_TELE_MOB_MANUAL enabled: "+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
				populatePOANationality(); //Jamshed
			} else if(selectedSheetIndex == 5) { 
				setCustScreeningCombos();   
				populateScreeningDataCRO();
				frame23_CPD_Disable();
				formObject.setStyle(TRSD_CHECK, DISABLE, TRUE);
				formObject.setStyle(CALCULATE, DISABLE, TRUE);
				populateTRSD();
				/*if( formObject.getValue(TRSD_APPROVEDRESULT).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(ED_CB_NON_TML, "Eligible");
				else
					formObject.setValue(ED_CB_NON_TML, "Not Eligible");*/
				if( formObject.getValue(TRSD_FINAL_DECISION).toString().equalsIgnoreCase("Approved"))
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Eligible");
				else
					formObject.setValue(CPD_FINAL_ELIGIBILITY, "Not Eligible");
			} else if(selectedSheetIndex == 6) { 
				loadApplicationAssessmentData();
				if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
					String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED "
							+ "WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(sQuery3);
					logInfo(" sQuery ",sQuery3); 
					loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
				}
				if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
					String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' "
							+ "FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' "
									+ "ORDER BY TO_NUMBER(PRODUCT_CODE)";				
					List<List<String>> recordList = formObject.getDataFromDB(sQuery4);
					logInfo(" sQuery ",sQuery4); 
					loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",
							PROD_OFRD_CRO_LVW);
				}
				if(getGridCount(FAC_LVW_CRO) == 0) {
					String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED "
							+ "WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery5);
					logInfo(" sQuery ",sQuery5); 
					loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
				}
			} else if(selectedSheetIndex == 7) { 
				clearUdfGrid();
				int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS"
						+ " WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
				List<List<String>> listContent = formObject.getDataFromDB(sQuery2);
				if(listContent != null && listContent.size() > 0) {
					populateUDFGrid(sQuery2);
				}
			} else if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				loadSICombos();//LoadSICombos();
				populateStndInstr();//PopulateStndInstr();
			} else if(tabCaption.equalsIgnoreCase("Document Generation")) {
				
			} else if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				Frame_delivery();
			} else if(tabCaption.equalsIgnoreCase("Decision")) {
				String[] disable = {IS_COMPLIANCE_NAME_SCR, IS_COMPLIANCE_RISK_ASSESS, IS_PROD_APP_REQ, 
						IS_CALL_BACK_REQ};
				disableControls(disable);
				//Newly Added
				formObject.clearCombo(CRO_DEC);
				int iCount =getListCount(CRO_DEC);
				if(iCount == 0) {
					String sQuery1 = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name="
							+ "'"+formObject.getValue(CURR_WS_NAME).toString()+"'"
									+ " order by to_char(WS_DECISION)";
					addDataInComboFromQuery(sQuery1, CRO_DEC);
				}
				/*int iCount = getListCount(CRO_DEC);
				if(iCount == 0 || iCount == 1) {
					sQuery = "Select '--Select--' from dual Union Select to_char(WS_DECISION) 
					from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue("CURR_WS_NAME")+"'";
					formObject.getDataFromDB(sQuery);
				}
				iCount = getListCount(CRO_REJ_REASON);
				if(iCount == 0 || iCount == 1) {
					sQuery = "Select '--Select-- ' from dual Union Select to_char(ws_rej_reason) 
					from usr_0_rej_reason_mast";
					formObject.getDataFromDB(sQuery);
				}
				iCount = getListCount("LISTVIEW_DECISION") ;
				if(iCount == 0) {
					sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,
					CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')
					CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME,
					 WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_DEC_HIST
					  WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					sOutput = formObject.getDataFromDB(sQuery);
					if(sOutput != null && sOutput.size() > 0){
					  loadListView(sOutput,"LISTVIEW_DECISION","0,1,2,3,4,5,6,7,8");	
					}
				} */   // 
				/*createHistory();
				createAllHistory();*/
				if(getGridCount("DECISION_HISTORY") == 0) {
					logInfo("DECISION_LVW","DECISION_HISTORY: "+getGridCount("DECISION_HISTORY"));
					sQuery="SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,"
							+ "WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM') "
							+ "CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL,"
							+ "WS_NAME,WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A "
							+ "FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					logInfo("DECISIONTAB",sQuery);
					List<List<String>> recordList = formObject.getDataFromDB(sQuery); 
                    loadListView(recordList,"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,"
							+ "WS_NAME,WS_COMMENTS","DECISION_HISTORY");							
					formObject.setStyle(DEC_STORAGE_DETAILS,VISIBLE,TRUE);
				} 
				try {
					if(isControlVisible(DOC_APPROVAL_OBTAINED) && isControlVisible(COURT_ORD_TRADE_LIC)){
						formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, TRUE);
						formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, TRUE);
						String sQuery1 = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME ='"+sWorkitemId+"'";	
						List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
						String minorDOB = sOutput1.get(0).get(0);
						int minorAge = CalculateAge(minorDOB);
						if((minorAge >= 18) && (minorAge <= 21)){
							sQuery1 = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER"
									+ " WHERE acc_relation = 'Guardian' AND "
									+ "WI_NAME ='"+sWorkitemId+"'";
							sOutput1 = formObject.getDataFromDB(sQuery1);
							String guardianCount = sOutput1.get(0).get(0);
							if("0".equalsIgnoreCase(guardianCount)){
								formObject.setStyle(DOC_APPROVAL_OBTAINED, VISIBLE, TRUE);
								formObject.setStyle(COURT_ORD_TRADE_LIC, VISIBLE, TRUE);
								formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, FALSE);
								formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, FALSE);
							}
						}
						sQuery1 = "SELECT PRODUCT_CODE FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME ='"+sWorkitemId+"'";	
						sOutput1 = formObject.getDataFromDB(sQuery1);
						if(sOutput1 != null && sOutput1.size() > 0){
						    String sProduct = sOutput1.get(0).get(0).trim();
							if("871".equalsIgnoreCase(sProduct)){
								formObject.setStyle(DOC_APPROVAL_OBTAINED, VISIBLE, FALSE);
								formObject.setStyle(COURT_ORD_TRADE_LIC, VISIBLE, FALSE);
							}	
						}
					}
				} catch (Exception e) {
					logError("Exception in onTabClick ",e);
				} 
				if(formObject.getValue(CHQ_ELIGIBILITY).toString().equalsIgnoreCase("None")) {
					formObject.setValue(CRO_DEC,"Return to Originator");
					formObject.setValue(CRO_REJ_REASON,"Bounced cheques > 3");
					formObject.setValue(CRO_REMARKS,"Bounced cheques are more than 3");
				}
	      } else if(tabCaption.equalsIgnoreCase("Category Change")) {
				formObject.setStyle(LAST_CAT_CAHNGE_DATE,DISABLE, TRUE);
				formObject.setStyle(SEC_ACC_INFO_ECD,DISABLE,TRUE);
				formObject.setStyle("Frame38",DISABLE,FALSE);
				manageCategoryChangeSection();		
		  }
	      else if(selectedSheetIndex == 12){  //Jamshed
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO_CHECK)){
					String vig_query = "select pri_cust_segment,final_risk_val from ext_ao where wi_name='"+sWorkitemId+ "'";
					logInfo("onTabClick","vig_query= "+vig_query);
					List<List<String>> vig_list=formObject.getDataFromDB(vig_query);
					String pri_cust_segment="";
					String final_risk_val="";
					if (vig_list != null && vig_list.size() > 0){
						pri_cust_segment=vig_list.get(0).get(0);
						final_risk_val=vig_list.get(0).get(1);
					}
					logInfo("onTabClick","pri_cust_segment, final_risk_val <==> "+pri_cust_segment+", "+final_risk_val);
					/*if(pri_cust_segment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
						formObject.addItemInCombo(CRO_DEC, "Send To Vigilance", "Send To Vigilance");
						logInfo("onTabClick","Send To Vigilance Added>>>>>>>");
					}*/
					if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")   ||
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
		
		}catch(Exception e) {
			logError("Exception in onTabClick ",e);
		} finally {
			logInfo("Outside onTabClick ","  ");
		}
	}
	
	private boolean submitWorkitem(String controlName, String data) {
		logInfo("submitWorkitem","controlName: "+controlName+"data: "+data);
		try{
			createHistory();
			createAllHistory();
			String sCustNo = getPrimaryCustomerSNO();
			String sQuery1 = "SELECT CUST_SEG,CUST_ID,CUST_FULL_NAME,FINAL_MOBILE_NO FROM USR_0_CUST_TXN"
					+ " WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery1);
			String sCustSeg = "";
			String sCustID = "";
			String sCustName = "";
			String sMobile = "";
			String decision = "";
			if(sOutput != null && sOutput.size() > 0){
				sCustSeg = sOutput.get(0).get(0);
				sCustID = sOutput.get(0).get(1);
				sCustName = sOutput.get(0).get(2);
				sMobile = sOutput.get(0).get(3);
			}
			decision=  formObject.getValue(CRO_DEC).toString();
			String sQ2 = "select decode (bank_relation,'New','NTB', bank_relation) as "
					+ "pri_bank_relation from acc_relation_repeater where sno=1 and wi_name='"+sWorkitemId+"' ";
			sOutput = formObject.getDataFromDB(sQ2); 
			Date d= new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			String sDate = dateFormat.format(d);  
			String pri_bank_rel =  (sOutput != null && sOutput.size() > 0)  ? sOutput.get(0).get(0).toString() : "";
			int sOut = updateDataInDB(sExtTable,"ACCOUNT_INFO_CHECKER_DEC,PRI_CUST_SEGMENT,CUST_ID,"
					+ "PRI_CUST_NAME,PRI_CUST_MOB,WI_COMPLETED_FROM,CRM_NAME,CRM_SUBMIT_DATE,pri_bank_relation",
					"'"+decision+"'"+(char)25+"'"+sCustSeg+"'"+(char)25+"'"+sCustID+"'"+(char)25+"'"+sCustName+"'"+(char)25+"'"+sMobile+"'"+(char)25+"'"+ sActivityName
					+"'"+(char)25+"'"+sUserName+"'"+(char)25+"'"+sDate+"'"+(char)25+"'"+ pri_bank_rel+"'","WI_NAME ='"+sWorkitemId+"'");
			logInfo("submitWorkitem","sOut: "+sOut);
			long start_Time1111=System.currentTimeMillis();
			if(CompAppReq.equalsIgnoreCase("No")) {
				sOut = updateDataInDB("USR_0_INTEGRATION_CALLS","STATUS","'Pending'","WI_NAME ="
						+ "'"+sWorkitemId+"' and CALL_NAME LIKE 'CUSTOMER_MODIFY%'"); 
				logInfo("submitWorkitem","sOut: "+sOut+",CompAppReq: "+CompAppReq);
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				String param = sWorkitemId+"','"+sProcessName;
				List<String> paramlist = new ArrayList<>( );
				paramlist.add(PARAM_TEXT+sWorkitemId);
//				paramlist.add(PARAM_TEXT+sProcessName);	//Commented for invalid column on 27042023(Performanceissue) by Ameena		
				formObject.getDataFromStoredProcedure("SP_TemplateGenerationEmailDt", paramlist);
				logInfo("submitWorkitem","SP_TemplateGenerationEmailDt Procedure called");
				String queryforwl ="select count(prod_code) as COUNT_wi from usr_0_product_selected"
						+ " where prod_code not in (select prod_code from USR_0_BLOCK_PROD_MAST) "
						+ "and wi_name  ='"+sWorkitemId+"'";
				List<List<String>> outputwl = formObject.getDataFromDB(queryforwl);
				logInfo("submitWorkitem", "outputwl"+outputwl);
				String sColumnWL = "";
				String	sValuesWL = "";
				String sWhereWL = "";
				if(outputwl != null && outputwl.size() > 0){
					if(outputwl.get(0).get(0).equalsIgnoreCase("0")) {            
						sColumnWL = "all_prod_blocked";
						sValuesWL = "'Y'";
						sWhereWL = "wi_name='"+sWorkitemId+"'";
						logInfo("submitWorkitem", "sColumnWL"+sColumnWL+"sValuesWL"+sValuesWL+ "sWhereWL"+sWhereWL);
						updateDataInDB(sExtTable,sColumnWL,sValuesWL,sWhereWL);              
					} else {
						sColumnWL = "all_prod_blocked";
						sValuesWL = "'N'";
						sWhereWL = "wi_name='"+sWorkitemId+"'";
						logInfo("submitWorkitem", "sColumnWL"+sColumnWL+"sValuesWL"+sValuesWL+ "sWhereWL"+sWhereWL);
						updateDataInDB(sExtTable,sColumnWL,sValuesWL,sWhereWL); 			
					}
				}
				loadIntegrationDataOnForm();
			}
		} catch(Exception e) {
			logError("submitWorkitem",e);
		}
		return true;
	}
	
	/*private boolean submitValidation(String data) {
		logInfo("submitValidation","Inside data: "+data);
		try{
			if(isControlVisible(DOC_APPROVAL_OBTAINED) && isControlVisible(COURT_ORD_TRADE_LIC)){
				if (formObject.getValue(DOC_APPROVAL_OBTAINED).toString().equalsIgnoreCase("false")
						&& formObject.getValue(COURT_ORD_TRADE_LIC).toString().equalsIgnoreCase("false")) {
					sendMessageValuesList("", "Please select the appropriate checkbox to complete the validation");	
					return false;
				}
			}
			if (formObject.getValue(P_ECB_CHQ_VALIDATION).toString().equalsIgnoreCase("false")
					&& formObject.getValue(P_ECB_REASON).equals("")) {
					sendMessageValuesList(P_ECB_REASON, "Please enter reason for skipping the ECB check");	
					return false ;  
		    } else if(!validateEligibilityField()) {
					sendMessageValuesList(BTN_ECB_REFRSH, 
							"Please click Refresh button to check eligibility status for cheque book.");
					return false;	
			} 
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				if(!checkMandatoryDoc(data)) {
					return false;
				}
			}
			String value = formObject.getValue(NIG_MAKER).toString();
			if(value.equalsIgnoreCase("yes")) {
				sendMessageValuesList("","Selected passport holder Residents"
						+ " does not meet conditions,"
						+ "\nHence not allowed to open Account. "
						+ "Do you still want to proceed with account opening?");
				return true;
			}
		} catch(Exception e) {
			logError("submitValidation",e);
		}
		return true;														
	}*/
	
	/*private boolean submitValidationNIG(String controlName, String data) {
		logInfo("submitValidationNIG","Inside controlName: "+controlName+",data: "+data);
		try{
			String value = formObject.getValue(NIG_MAKER).toString();
			if(value.equalsIgnoreCase("yes")) {
				if(data.equalsIgnoreCase("interSubmit")) {
					formObject.setValue("NIG_CHECKER","YES");
					String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCHECKKER='YES' "
							+ "Where WI_NAME='"+sWorkitemId+"'";
					logInfo("submitValidationNIG", "updatequery: "+updatequery);
					formObject.saveDataInDB(updatequery);
//					submitQDECheckerValidations(controlName,data);
				}
			} else {
				formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
				return false;
			}			
		} catch(Exception e) {
			logError("submitValidationNIG",e);
		} 
		return true;
	}*/
	
	private boolean submitQDECheckerValidations(String data) {
		logInfo("submitQDECheckerValidations","Inside data: "+data);
		try{
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")) {
				sendMessageValuesList(CRO_DEC,"Please select user decision.");
				formObject.setStyle(BTN_SUBMIT,DISABLE, FALSE);
				return false;
			}
			String sQuery = "SELECT IS_COMPLIANCE_VISITED FROM "+sExtTable+" WHERE WI_NAME = '"+sWorkitemId+"'";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			logInfo("submitDDECheckerValidations","IS_COMPLIANCE_VISITED sQuery: "+sQuery+"; sOutput1: "+  sOutput);
			if(sOutput != null && sOutput.size() > 0) {
				if(sOutput.get(0).get(0).equalsIgnoreCase("Yes")) {
					CompAppReq = "No";
					String sUpdateDecision1 = "update "+sExtTable+" set IS_COMP_REQ='No' "
							+ "Where WI_NAME='"+ sWorkitemId +"'";
					logInfo("submitQDECheckerValidations","sUpdateDecision1 : "+sUpdateDecision1);
					formObject.saveDataInDB(sUpdateDecision1);
				} else {
					sQuery = "SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_RISK_ASSESSMENT_DATA "
							+ "WHERE APPROVAL_REQ='Yes' and wi_name = '"+sWorkitemId+"'";
					sOutput = formObject.getDataFromDB(sQuery);
					if(sOutput != null && sOutput.size() > 0) {
						if(!sOutput.get(0).get(0).equalsIgnoreCase("0")) {
							CompAppReq="Yes";
							String sUpdateDecision1 = "update "+sExtTable+" set IS_COMP_REQ='Yes' "
									+ "Where WI_NAME='"+ sWorkitemId +"'";
							logInfo("submitQDECheckerValidations","sUpdateDecision1 : "+sUpdateDecision1);
							formObject.saveDataInDB(sUpdateDecision1);
							if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
								sendMessageValuesList(CRO_DEC, "Compliance Approval is Required");
								return false;
							} //change 29JUNE2021
						}
						else {
							CompAppReq="No";
							String sUpdateDecision1 = "update "+sExtTable+" set IS_COMP_REQ='No' "
									+ "Where WI_NAME='"+ sWorkitemId +"'";
							logInfo("submitQDECheckerValidations","sUpdateDecision1 : "+sUpdateDecision1);
							formObject.saveDataInDB(sUpdateDecision1);
						}
					}
				}
			}
			if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				String rejectionReason = formObject.getValue(CRO_REJ_REASON).toString();
				String rejectionRemarks = formObject.getValue(CRO_REMARKS).toString() ;
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Permanent Reject - Discard")){
					if(rejectionReason.equalsIgnoreCase("")) {
						sendMessageValuesList(CRO_REJ_REASON,"Please Select Rejection Reason.");
						return false;
					}
				}
				if(rejectionRemarks.equalsIgnoreCase("")) {
					sendMessageValuesList(CRO_REMARKS,"Please Fill Remarks.");
					return false;
				}
			} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Compliance")) {
				logInfo("submitDDECheckerValidations", "checking checkMandatoryDoc data: "+data);
				if(checkMandatoryDoc(data)!= true) {
					logInfo("submitDDECheckerValidations", "checkMandatoryDoc Failed");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				}
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				updateFlag("false");
				if (!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")){//Added by krishna
				if(!reKeyLogic()) {
					logInfo("submitQDECheckerValidations", "reKeyLogic  Failed");
					if(formObject.getValue(MANUAL_NAME).toString().isEmpty() 
							&& formObject.getValue(EIDA_NAME).toString().isEmpty() 
							&& formObject.getValue(FCR_NAME).toString().isEmpty()) {
						sendMessageValuesList("", "Please click on Customer Info Tab in order to check any mismatch in "
								+ "customer data");
					}
					return false;  
				  }
				} else {
					logInfo("submitQDECheckerValidations", "updateRekeyCheck");
					updateRekeyCheck("true");
				}
				if(!checkRekeyDone()) {
					logInfo("submitQDECheckerValidations", "checkRekeyDone  Failed");
					return false;  
				}
				if (!searchCustCRM()) {
					logInfo("submitQDECheckerValidations", "searchCustCRM  Failed");
					return false;  
				}
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")
				&& !formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")) {
				if(CompAppReq.equalsIgnoreCase("No")){
					logInfo("submitQDECheckerValidations", "CompAppReq: "+CompAppReq);
					insertDataInIntegrationTable();
					// Fulfillment changes START
					String fulfillmentFlag = formObject.getValue("FULLFILMENT_FLAG").toString();
					if ("Y".equals(fulfillmentFlag)) {	
						fulfillment.insertFulfillmentDataInIntegrationTable();
						fulfillment.updateCallOrder();
						fulfillment.loadFulfillmentdata();
					}
					// Fulfillment changes END
				}
				if(jspFunction(CompAppReq)) {
					return true;
				} else {
					return false;
				}
				// Documentation code left 
			} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
				formObject.setValue(NO_OF_CUST_PROCESSED,"0");
				updateFlag("true"); 
			} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				if(checkMandatoryDoc(data)!= true) {
					logInfo("submitDDECheckerValidations", "checkMandatoryDoc Failed");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				}
			}
			// SUBMIT VALIDATIONS DONE 
		} catch(Exception e) {
			logError("submitQDECheckerValidations",e);
		} finally {
			logInfo("submitQDECheckerValidations","Outside");
		}
		return true;
	}
	
	private boolean afterSubmitJSP(String data) {
		try {
			logInfo("afterSubmitJSP", "INSIDE");
			loadIntegrationDataOnForm();
			if(validateIntegrationCalls() != 1) {
				return false;
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				
				if(checkMandatoryDoc(data)!= true) {
					logInfo("submitDDECheckerValidations", "checkMandatoryDoc Failed");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				}
			}
			List<String> paramlist =new ArrayList<>();
			paramlist.add (PARAM_TEXT+sWorkitemId);
			paramlist.add (PARAM_TEXT+"QDE");
			formObject.getDataFromStoredProcedure("SP_TemplateGeneration_SMS", paramlist);
			paramlist.clear();
			paramlist.add (PARAM_TEXT+sWorkitemId);
			paramlist.add (PARAM_TEXT+"CPDchecker");
			formObject.getDataFromStoredProcedure("SP_TemplateGeneration_Email", paramlist);
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
				formObject.setValue(NO_OF_CUST_PROCESSED, "0");
				if(!reKeyLogic()) {
					updateFlag("true");
				} else {
					updateFlag("false");
				}
			}
			return true;
		} catch (Exception e) {
			logError("afterSubmitJSP", e);
		}
		return true;

	}
	
	public boolean saveAndNextValidation(String data) {
		try {
			logInfo("saveAndNextValidation", "INSIDER1");
			int selectedSheetIndex = Integer.parseInt(data);
			if(!formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade")){//Added by krishna
			logInfo("saveAndNextValidation","selectedSheetIndex: "+selectedSheetIndex);
			if(selectedSheetIndex == 1){
				if(!validationTab()) {
					return false;
				}
				reKeyLogic();
			}
			}else if(selectedSheetIndex == 10){
				logInfo("saveAndNextValidation", "decision");
				formObject.clearCombo(CRO_DEC);
				int iCount =getListCount(CRO_DEC);
				if(iCount == 0) {
					String sQuery1 = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name="
							+ "'"+formObject.getValue(CURR_WS_NAME).toString()+"'"
							+ " order by to_char(WS_DECISION)";
					addDataInComboFromQuery(sQuery1, CRO_DEC);
				}
				
				//Jamshed
				if(sActivityName.equalsIgnoreCase(ACTIVITY_QDE_ACCOUNT_INFO_CHECK)){
					String vig_query = "select pri_cust_segment,final_risk_val from ext_ao where wi_name='"+sWorkitemId+ "'";
					logInfo("saveAndNextValidation","vig_query= "+vig_query);
					List<List<String>> vig_list=formObject.getDataFromDB(vig_query);
					String pri_cust_segment="";
					String final_risk_val="";
					if (vig_list != null && vig_list.size() > 0){
						pri_cust_segment=vig_list.get(0).get(0);
						final_risk_val=vig_list.get(0).get(1);
					}
					logInfo("saveAndNextValidation","pri_cust_segment, final_risk_val <==> "+pri_cust_segment+", "+final_risk_val);
					/*if(pri_cust_segment.equalsIgnoreCase("Private Clients") && final_risk_val.equalsIgnoreCase("Medium Risk")){
						formObject.addItemInCombo(CRO_DEC, "Send To Vigilance");
						logInfo("saveAndNextValidation","Send To Vigilance Added>>>>>>>");
					}*/
					if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade")   ||
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
			updateFlag("false");
		} catch (Exception e) {
			logError("Exception in saveAndNextValidation",e);
		}
		return true;
	}
}
