package com.newgen.iforms.user.ao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class DDEChecker extends Common implements Constants, IFormServerEventHandler{
	boolean custInfoTabLoad = false;
	String custSno = "";

	public DDEChecker(IFormReference formObject) {
		super(formObject);
	}

	@Override
	public void beforeFormLoad(FormDef arg0, IFormReference arg1) {
		String workitemName = formObject.getObjGeneralData().getM_strProcessInstanceId();
		logInfo("WorkItem Name: " , workitemName);			
	}

	@Override
	public String executeCustomService(FormDef arg0, IFormReference arg1, String arg2, String arg3, String arg4) {
		return null;
	}

	@Override
	public JSONArray executeEvent(FormDef arg0, IFormReference arg1, String arg2, String arg3) {
		return null;
	}
	
	private String eventOnLoadDDEChecker(String controlName, String eventType,String data) {
		String fieldList = "";
		try {
			logInfo("Inside eventOnLoadDDEChecker  eventType: " , eventType + ", Control Name: " + controlName + ", Data: " + data);
			//formObject.applyGroup(CONTROL_SET_MANUAL); 
			loadSICombos();
			logInfo("eventOnLoadDDEChecker", "populateCRSData ");
			populateCRSData();
			logInfo("eventOnLoadDDEChecker", "populateCRSData ");
			prefLang();
			int iProcessedCustomer = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			if(isExistingCustomer()) {
				fieldList = changeFieldsBackColor(Integer.toString(iProcessedCustomer+1));
			}//added on 11july2021
			setDDEModeCombos();
			String sQuery = "SELECT USERNAME,PERSONALNAME||' '||FAMILYNAME AS NAME FROM PDBUSER WHERE UPPER(USERNAME) =UPPER('"+sUserName+"')";
			List<List<String>> sOutput = formObject.getDataFromDB(sQuery);
			if(sOutput!=null && sOutput.size()>0){
				formObject.setValue(CHECKER_NAME,sOutput.get(0).get(1));
				formObject.setValue(CHECKER_CODE,sOutput.get(0).get(0));
				formObject.setValue(CHECKER_NAME_CAT_CHANGE,sOutput.get(0).get(1));
				formObject.setValue(CHECKER_CODE_CAT_CHANGE,sOutput.get(0).get(0));
			}
			sQuery = "SELECT DEPARTMENT FROM WFFILTERTABLE WHERE UPPER(USERNAME) =UPPER('"+sUserName+"') AND PROCESSDEFID='"+sProcessDefId+"'";
			sOutput = formObject.getDataFromDB(sQuery);
			if(sOutput != null && sOutput.size() > 0){
				formObject.setValue(CHECKER_DEPARMENT_CAT_CHANGE,sOutput.get(0).get(0));
			}
			formObject.setStyle(BUTTON_GENERATE_TEMPLATE, DISABLE,FALSE);
			String sCallName = "";
			sQuery = "SELECT CALL_NAME FROM USR_0_INTEGRATION_CALLS WHERE WI_NAME='"+ sWorkitemId +"' AND STATUS='Success'";
			sOutput = formObject.getDataFromDB(sQuery);
			if(sOutput != null && sOutput.size() > 0){
			   sCallName  = sOutput.get(0).get(0);
			}
			if(sCallName.contains("CUSTOMER_CREATION") || sCallName.contains("ACCOUNT_CREATION")) {
				loadIntegrationDataOnForm(); // correct in common . java its a utility function
			}
			setTemp_usr_0_product_selected();
			fieldValueUsr_0_Risk_Data();
			formObject.setValue(P_ECB_CHQ_VALIDATION, "True");
			sQuery = "SELECT count(1) AS COUNT FROM USR_0_CUST_TXN WHERE WI_NAME='"
					+ sWorkitemId + "' and PASS_TYPE='Diplomat'";
			sOutput = formObject.getDataFromDB(sQuery);
			int diplomatPassType = (sOutput != null && sOutput.size() > 0)  ? Integer.parseInt(sOutput.get(0).get(0).toString()) : 0;
			if (diplomatPassType > 0) {
				formObject.setStyle("P_ECB_CHQ_VALIDATION", DISABLE,FALSE);
			} else {
				formObject.setStyle("P_ECB_CHQ_VALIDATION", DISABLE,TRUE);
			}
			formObject.setStyle("P_ECB_REASON", DISABLE,TRUE);
			sQuery = "SELECT CUST_ID,CUST_NAME,CUST_IC,CUST_PASSPORT,CUST_EMAIL,CUST_MOBILE,'','',"
					+ "to_char(CUST_DOB,'"+ DATEFORMAT +"'),"
					+ "CUST_EIDA,CUST_NATIONALITY FROM USR_0_DUPLICATE_SEARCH_DATA WHERE WI_NAME='"
					+sWorkitemId+"' AND CUST_SNO ='"+iProcessedCustomer+"'";
			logInfo("Inside USR_0_DUPLICATE_SEARCH_DATA: " , sQuery);
			List<List<String>> result = formObject.getDataFromDB(sQuery);
			if(result != null && result.size() > 0){
				String listviewValues = result.get(0).get(0)+"##"+result.get(0).get(1)+"##"+result.get(0).get(2)+"##"
						+result.get(0).get(3)+"##"+result.get(0).get(4)+"##"+result.get(0).get(5)+"##"
						+result.get(0).get(6)+"##"+result.get(0).get(7)+"##"+result.get(0).get(8)+"##"
						+result.get(0).get(9)+"##"+result.get(0).get(10);
				LoadListViewWithHardCodeValues(LVW_DEDUPE_RESULT, COLUMNS_LVW_DEDUPE_RESULT, listviewValues);
			}
			

            //Mutilpe account bug
			String sQuery5 = "SELECT CID FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME='"+ sWorkitemId+ "' ORDER BY TO_NUMBER(INSERTIONORDERID)";
			logInfo("CPDMakerFourStep sQuery5 ",sQuery5);
			List<List<String>> sOutput5 = formObject.getDataFromDB(sQuery5);
			for(int i = 0; i < sOutput5.size(); i++) {
				 logInfo(" DDE  Checker sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14));
				 formObject.setTableCellValue(PRODUCT_QUEUE, i, 14 ,i+1+"");
		         logInfo(" DDE  Checker sQuery5 ","Checking product selected CID"+formObject.getTableCellValue(PRODUCT_QUEUE, i, 14)); 
			}
			loadECBChqBookValidation();
			populateStndInstr();
			populatePreAssesmentDetails(); 
			accountPurpose(); //AO DCRA
			additionalSource();
			custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
			populatePOANationality(); //Jamshed
		} catch (Exception e) {
			logError("Exception in eventOnLoadDDEChecker ",e);
		} finally {
			logInfo("Outside eventOnLoadDDEChecker ","  ");
		}
		return fieldList;
	}
	
	private String eventOnClickEventDDEChecker(String controlName, String eventType,String data) {
		logInfo("Inside eventOnClickEventDDEChecker","");
		String retMsg = getReturnMessage(true,controlName);
		if (controlName.equalsIgnoreCase(BTN_ECB_REFRSH)) {
			loadECBChqBookValidation();
		} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("%%%") 
				&& !data.contains("interSubmit") && !data.contains("afterJSP")) {
			logInfo("Before buttton submit","");
			if(submitValidation(data)) {
				insertIntoTrackChangeModify(); //Ameena 13012023
				if(sendMessageList.size()>  0) {
					return getReturnMessage(true,controlName,sendMessageList.get(1));
				} else {
					if(submitDDECheckerValidations(data)) {
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") 
								&&  !(formObject.getValue(REQUEST_TYPE).toString()
										.equalsIgnoreCase("Category Change Only")||formObject.getValue(REQUEST_TYPE).toString()
										.equalsIgnoreCase("Upgrade"))){
							return getReturnMessage(true,controlName,"openJSP");
						} else {
							return getReturnMessage(true, controlName, CA008);
						}
					}
				}
			}
			/*if(submitWorkitem() && submitDDECheckerValidations(controlName, data)) {
				logInfo("After buttton submit","");
				return getReturnMessage(true, controlName);
			}*/		 
		} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("interSubmit")) {
			if(submitValidationNIG(controlName,data)) {
				if(submitDDECheckerValidations(data)) {
					if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") 
							&& !(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||
									formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){
						return getReturnMessage(true,controlName,"openJSP");
					} else {
						return getReturnMessage(true,controlName,CA008);
					}
				}
			}
		} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.contains("afterJSP")) {
			if(BTN_SUBMIT.equalsIgnoreCase(controlName)) {
				if(afterSubmitJSP(data)) {
					return getReturnMessage(true, controlName, CA008);
				}
			}
		} else if(controlName.equalsIgnoreCase(BTN_SUBMIT) && data.equalsIgnoreCase("confirmSubmit")) {
			setRestrictedFlag();
			if(submitWorkitem(data)) {
				logInfo("executeServerEvent","submitWorkitem finallySubmitDone");
				return getReturnMessage(true,controlName);
			}
		} else if("saveNextTabClick".equalsIgnoreCase(controlName)) {
			if(saveAndNextValidation((String)data.split(",")[1])){
				return getReturnMessage(true, controlName);
			}
		} else if(controlName.equalsIgnoreCase("PRODUCT_QUEUE_MODE_OF_FUNDING")) {
			logInfo("PRODUCTQUEUE_MODEOFFUNDING Onchanging","In Transfer Mode");
			int iPrimaryCust = Integer.parseInt(getPrimaryCustomerSNO());
			String sCustID  = formObject.getTableCellValue(ACC_RELATION, iPrimaryCust , 2);
			sMode = data;
			if(sMode.indexOf("Transfer - Internal") != -1 ) {//if(sMode.equalsIgnoreCase("Transfer - Internal")) {
					String arrMode[] = null;
					if(sMode.indexOf("#")!=-1) {
						arrMode = sMode.split("#");
					}
					String sQuery1 = "SELECT ACC_NO FROM USR_0_PRODUCT_EXISTING_HIST WHERE WI_NAME ='"+sWorkitemId+"' "
							+ "AND ACC_STATUS IN (SELECT DESCRIPTION FROM USR_0_ACCOUNT_STATUS_CODE "
							+ "WHERE CODE IN ('6','8')) AND CUSTOMER_ID='"+sCustID+"'";
					List<List<String>> sOutput = formObject.getDataFromDB(sQuery1);
					logInfo("",sQuery1);
					String sDebitAccNo = sOutput.get(0).get(0);
					logInfo("PRODUCTQUEUE_MODEOFFUNDING ","sDebitAccNo---"+sDebitAccNo);
					
					if(!sDebitAccNo.equalsIgnoreCase("")) {
						String sTemp[] =sDebitAccNo.split(",");
						
						for(int i=0;i<sTemp.length;i++)
						{
							formObject.addItemInTableCellCombo(PRODUCT_QUEUE, Integer.parseInt(arrMode[1]), 3,"DEBIT_ACCOUNT_NO", sTemp[i]);
						}
					}
				}
		} else if(TABCLICK.equalsIgnoreCase(controlName)) {
			onTabClickDDEChecker(data);   
    		formObject.setValue(NIG_CHECKER,"YES");
			String updatequery = "update USR_0_CUST_TXN set NIGEXCEPTIONCHECKKER='YES' Where WI_NAME='"+formObject.getValue("WI_Name")+"'";
			formObject.saveDataInDB(updatequery);
		} else if("generateInstanLetter".equalsIgnoreCase(controlName)){
			int sOut = updateDataInDB("USR_0_INTEGRATION_CALLS","STATUS","'Pending'","WI_NAME ="
					+ "'"+sWorkitemId+"' and CALL_NAME LIKE 'CUSTOMER_MODIFY%'"); 
			logInfo("submitWorkitem","sOut: "+sOut+",CompAppR");  
			if(!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")
					||formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){
				logInfo("generateInstanLetter","data :"+data); 
				String count = "-1";
				if(!"0".equalsIgnoreCase(data)){
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
							if(instantrequestsent == 0 ) {
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
					loadIntegrationDataOnForm();
					return getReturnMessage(true, "", "TEMPLATESTARTED")	;
				}
			}else{
				
				return getReturnMessage(true); 
			}
		}
		return retMsg;
	}
	
	private void eventOnLostFocusEventDDEChecker(String controlName, String eventType,String data) {
		logInfo("Inside eventOnLostFocusEventDDEChecker controlName: ",controlName+" eventType: "+eventType+" data: "+data);
        try{
        	 if(controlName.equalsIgnoreCase(formObject.getTableCellValue(ACC_RELATION, Integer.parseInt(data) , 0))) {// control name , is it correct?
     			formObject.setValue(SELECTED_ROW_INDEX, (Integer.parseInt(data))+"");
     		}	
		} catch(Exception e) {
			logError("Exception in eventOnLostFocusEventDDEChecker ",e);
		} finally {
			logInfo("Outside eventOnLostFocusEventDDEChecker ","  ");
		}
	}
	
	private void eventOnChangeEventDDEChecker(String controlName, String eventType,String data){ 
		logInfo("eventOnChangeEventDDEChecker","controlName: "+controlName+", eventType: "+eventType+", data: "+data);
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
			} else if (controlName.equalsIgnoreCase(CHECKBOX_TELE_MOB_MANUAL)) {
				toggleCheckbox(controlName, CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA);
				manageManualFields(CHECKBOX_TELE_MOB_MANUAL, MANUAL_MOBILE);
				if(formObject.getValue(controlName).toString().equalsIgnoreCase(TRUE)) {
					formObject.setValue(CP_MOBILE,formObject.getValue(MANUAL_MOBILE).toString());
				}
			} 	
		} catch(Exception e) {
			logError("eventOnChangeEventDDEChecker",e);
		}
	}
	
	private boolean submitWorkitem() {
		logInfo("inside submitWorkitem","");
		if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO_CHECK)) {
			boolean prodChangeFlag = checkProdChngForNoEligibility();
			prodChangeFlag = true;  // for checking remove afterwards
			if(!prodChangeFlag) {
				logInfo("submitWorkitem","inside if--------");
				sendMessageValuesList("", "Customer is not eligible for cheque book. Please change the product");	
				return false;
			}
			if (formObject.getValue("P_ECB_CHQ_VALIDATION").toString().equalsIgnoreCase("false")
					&& formObject.getValue("P_ECB_REASON").equals("")) {
				sendMessageValuesList(P_ECB_REASON, "Please enter reason for skipping the ECB check");	
				return false ;  // for checking
			}
			if(!validateEligibilityField()) {
				sendMessageValuesList(BTN_ECB_REFRSH, "Please click Refresh button to check eligibility status for cheque book.");
				return false;	// for checking
			}
		}
		return true;
	}
	
	private boolean submitDDECheckerValidations(String data) {
		logInfo("inside submitDDECheckerValidations","");
		List<List<String>> output = null;
		logInfo("inside sActivityName " , sActivityName);
		if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO_CHECK)) {
			logInfo("Inside ACTIVITY_DDE_ACCOUNT_INFO_CHECK","");
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("")){
				logInfo("Inside Please select user decision","");
				sendMessageValuesList(CRO_DEC, "Please select user decision.");
				formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
				return false;
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Reject")) {
				logInfo("Inside decision reject","");
				if(formObject.getValue(CRO_REJ_REASON).toString().equalsIgnoreCase("")) {
					logInfo("Inside Please select rejection reason","");
					sendMessageValuesList(CRO_REJ_REASON, "Please select rejection reason.");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				} else if(formObject.getValue(CRO_REMARKS).toString().equalsIgnoreCase("")) {
					logInfo("Inside Please enter remarks","");
					sendMessageValuesList(CRO_REMARKS,"Please enter remarks.");
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				} else {
					updateProfitCentre();
					return true;
				}
			}
			String sQuery = "SELECT IS_COMPLIANCE_VISITED FROM "+sExtTable+" WHERE WI_NAME = '"+sWorkitemId+"'";
			output = formObject.getDataFromDB(sQuery);
			String CompAppReq = "";
			if(output != null && output.size() > 0){
				if(output.get(0).get(0).equalsIgnoreCase("Yes")) {					
					CompAppReq="No";
					String sUpdateDecision1="update "+sExtTable+" set IS_COMP_REQ='No' Where WI_NAME='"+ sWorkitemId +"'";
					System.out.println(sUpdateDecision1+"   sUpdateDecision1");
					formObject.saveDataInDB(sUpdateDecision1);
				} else {
					sQuery="SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_RISK_ASSESSMENT_DATA WHERE APPROVAL_REQ='Yes'"
							+ " and wi_name = '"+sWorkitemId+"'";
					output=formObject.getDataFromDB(sQuery);
					if(!output.get(0).get(0).equalsIgnoreCase("0")) {
						CompAppReq="Yes";
						String sUpdateDecision1="update "+sExtTable+" set IS_COMP_REQ='Yes' Where WI_NAME='"+ sWorkitemId +"'";
						logInfo("submitDDECheckerValidations","in sUpdateDecision1--"+sUpdateDecision1);
						formObject.saveDataInDB(sUpdateDecision1);
//						if(!formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Compliance")) {
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							sendMessageValuesList(CRO_DEC, "Compliance Approval is Required");
							return false;
						} //change 29JUNE2021
					}
					else {
						CompAppReq="No";
						String sUpdateDecision1="update "+sExtTable+" set IS_COMP_REQ='No' Where WI_NAME='"
								+ sWorkitemId +"'";
						logInfo("submitDDECheckerValidations","in sUpdateDecision1--"+sUpdateDecision1);
						formObject.saveDataInDB(sUpdateDecision1);
					}
				}
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")
					&& !(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||
							formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))) {
				if(CompAppReq.equalsIgnoreCase("No")){
					logInfo("submitDDECheckerValidations", "CompAppReq: "+CompAppReq);
					insertDataInIntegrationTable();
				}
			}

			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Send To Compliance")) {
				if(checkMandatoryDoc(data)!= true) {
					formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
					return false;
				}
			} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Return to Originator")) {
				formObject.setValue(NO_OF_CUST_PROCESSED, "0");
				updateFlag("true");
			} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
				logInfo("submitDDECheckerValidations", "decision  Approve");
				logInfo("submitDDECheckerValidations", "decision  kdd");
				//createHistory();
				//createAllHistory();
				String queryforwl ="select count(prod_code) as COUNT_wi from usr_0_product_selected"
						+ " where prod_code not in (select prod_code from USR_0_BLOCK_PROD_MAST) and wi_name  ='"+sWorkitemId+"'";
				List<List<String>> outputwl=formObject.getDataFromDB(queryforwl);
				logInfo("submitDDECheckerValidations", "outputwl"+outputwl);
				if(outputwl.get(0).get(0).equalsIgnoreCase("0")) {            
					String sColumnWL="all_prod_blocked";
					String	sValuesWL = "'Y'";
					String sWhereWL ="wi_name='"+sWorkitemId+"'";
					logInfo("submitDDECheckerValidations", "sColumnWL"+sColumnWL);
					logInfo("submitDDECheckerValidations", "sValuesWL"+sValuesWL);
					logInfo("submitDDECheckerValidations", "sWhereWL"+sWhereWL);
					updateDataInDB(sExtTable,sColumnWL,sValuesWL,sWhereWL);              
				} else {
					String sColumnWL1="all_prod_blocked";
					String	 sValuesWL1 = "'N'";
					String 	sWhereWL1 ="wi_name='"+sWorkitemId+"'";
					logInfo("submitDDECheckerValidations", "sColumnWL1"+sColumnWL1);
					logInfo("submitDDECheckerValidations", "sValuesWL1"+sValuesWL1);
					logInfo("submitDDECheckerValidations", "sWhereWL1"+sWhereWL1);
					updateDataInDB(sExtTable,sColumnWL1,sValuesWL1,sWhereWL1); 			
				}

				updateFlag("false");
				if(!reKeyLogic()) {
					logInfo("submitDDECheckerValidations", "reKeyLogic  Failed");
					if(formObject.getValue(MANUAL_NAME).toString().isEmpty() 
							&& formObject.getValue(EIDA_NAME).toString().isEmpty() 
							&& formObject.getValue(FCR_NAME).toString().isEmpty()) {
						sendMessageValuesList("", "Please click on Customer Info Tab in order to check any mismatch in "
								+ "customer data");
					}
					return false;  // for checking
				} else {
					logInfo("submitDDECheckerValidations", "updateRekeyCheck");
					updateRekeyCheck("true");
				}
				if(!checkRekeyDone()) {
					logInfo("submitDDECheckerValidations", "checkRekeyDone  Failed");
					return false;  // for checking
				}
				if (!searchCustCRM()) {
					logInfo("submitDDECheckerValidations", "searchCustCRM  Failed");
					return false;  //for checking
				}
				if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") &&
						!(formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Category Change Only")||
						formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade"))){
					logInfo("submitDDECheckerValidations","in cro dec kdd--"+formObject.getValue(CRO_DEC).toString());
					if(jspFunction(CompAppReq)) {
						return true;
					} else {
						return false;
					}
				} else if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
					if(checkMandatoryDoc(data)!= true) {
						logInfo("submitDDECheckerValidations", "checkMandatoryDoc Failed");
						formObject.setStyle(BTN_SUBMIT, DISABLE, FALSE);
						return false;
					}
				}
			}			
			/*createHistory();
			createAllHistory();*/
		}
		return true;
	}
	
	private void onTabClickDDEChecker(String data) { 
		logInfo("Inside onTabClickDDEChecker ","  ");
        try{
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
			if(selectedSheetIndex == 1 || selectedSheetIndex == 2 || selectedSheetIndex == 3 
					|| selectedSheetIndex == 4 || selectedSheetIndex == 5){ //selectedSheetIndex == 0 removed
				//setCustScreeningCombos();
				if(!custInfoTabLoad || !custSno.equalsIgnoreCase(formObject.getValue(SELECTED_ROW_INDEX).toString())) {
					custSno = formObject.getValue(SELECTED_ROW_INDEX).toString();
					custInfoTabLoad = true;
					formObject.applyGroup(CONTROL_SET_MANUAL); 
					setDDEModeCombos();
					populatePersonalData();
					populateKYCData();  		
					populateKycMultiDrop();
					populatePreAssesmentDetails();  //shahbaz
					populateComparisonFields();
					PopulatePrivateClientQuestions(); 
					populateRiskData();
					populatePepAssesmentDetails();   // AO DCRA
					loadDedupeSearchData(sWorkitemId);
					makeEnableRekeyFields();
//					populateReKey();
					populateReKeyTemp("CRO"); 
				}
				/*if(selectedSheetIndex == 1) {  added in saveandnext fn
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
				String bnk_relation = formObject.getTableCellValue(ACC_RELATION, iProcessedCustomer , 1);
				if(bnk_relation.equalsIgnoreCase("New") || bnk_relation.equalsIgnoreCase("Existing")) {
					visibleonSegmentBasisCPDCHECKER(formObject.getValue(PD_CUSTSEGMENT).toString()); 
				}
				logInfo("onTabClickDDEChecker","is CHECKBOX_TELE_MOB_MANUAL enabled: "
						+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
				populatePOANationality(); //Jamshed
			} else if(selectedSheetIndex == 6) { 
				//setCustScreeningCombos();  // should be commented 
				populateScreeningDataCRO();
				//setDDEModeCombos();
				//frame23_CPD_Disable();
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
			} else if(selectedSheetIndex == 7) { 
				loadApplicationAssessmentData();
				if(getGridCount(FAC_OFRD_LVW_CRO) == 0) {
					String sQuery3  =  "SELECT CUST_ID,CUST_NAME,CARD_TYPE FROM USR_0_DEBITCARD_OFFERED WHERE WI_NAME = '"+sWorkitemId+"'";		
					List<List<String>> recordList = formObject.getDataFromDB(sQuery3);
					logInfo(" sQuery3 ",sQuery3); 
					loadListView(recordList,"CUST_ID,CUST_NAME,CARD_TYPE",FAC_OFRD_LVW_CRO);
				}
				if(getGridCount (PROD_OFRD_CRO_LVW)  == 0) {
					String sQuery4  =  "SELECT PRODUCT_CODE,PRODUCT_DESC,CURRENCY_DESC,'' FROM USR_0_PRODUCT_OFFERED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(PRODUCT_CODE)";				
					List<List<String>> recordList = formObject.getDataFromDB(sQuery4);
					logInfo(" sQuery4 ",sQuery4); 
					loadListView(recordList,"PRODUCT_CODE,PRODUCT_DESCRIPTION,CURRENCY,PROD_ACC_OPENING",PROD_OFRD_CRO_LVW);
				}
				if(getGridCount(FAC_LVW_CRO) == 0) {
					String sQuery5 = "SELECT CID,FACILITY,DESCRIPTION FROM USR_0_FACILITY_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY FACILITY";
					List<List<String>> recordList = formObject.getDataFromDB(sQuery5);
					logInfo(" sQuery5 ",sQuery5); 
					loadListView(recordList,"CID,FACILITY,DESCRIPTION",FAC_LVW_CRO);
				}
			} else if(selectedSheetIndex == 8) { 
				clearUdfGrid();
				int custNo = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
				String sQuery2 = "SELECT GRADUATION_DATE, SPECIAL_CUSTOMER_IDENTIFIER FROM USR_0_UDF_DETAILS WHERE ACC_RELATION_SERIALNO='"+custNo+"' AND WI_NAME='"+sWorkitemId+"'";
				List<List<String>> listContent = formObject.getDataFromDB(sQuery2);
				if(listContent != null && listContent.size() > 0) {
					populateUDFGrid(sQuery2);
				}
			} else if(tabCaption.equalsIgnoreCase("Standing Instruction")) {
				loadSICombos();
				populateStndInstr();
			} else if(tabCaption.equalsIgnoreCase("Delivery Preferences")) {
				Frame_delivery();
			} else if(tabCaption.equalsIgnoreCase("Decision")) {
				String[] disable = {IS_COMPLIANCE_NAME_SCR, IS_COMPLIANCE_RISK_ASSESS, IS_PROD_APP_REQ, IS_CALL_BACK_REQ};
				disableControls(disable);
				formObject.clearCombo(CRO_DEC);
				int iCount =getListCount(CRO_DEC);
				if(iCount==0) {
					String sQuery1 = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue(CURR_WS_NAME)+"' order by to_char(WS_DECISION)";
					addDataInComboFromQuery(sQuery1, CRO_DEC);
				}
				/*int iCount = getListCount(CRO_DEC);
				if(iCount == 0 || iCount == 1) {
					sQuery = "Select '--Select--' from dual Union Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name='"+formObject.getValue("CURR_WS_NAME")+"'";
					formObject.getDataFromDB(sQuery);
				}
				iCount = getListCount(CRO_REJ_REASON);
				if(iCount == 0 || iCount == 1) {
					sQuery = "Select '--Select-- ' from dual Union Select to_char(ws_rej_reason) from usr_0_rej_reason_mast";
					formObject.getDataFromDB(sQuery);
				}
				iCount = getListCount("LISTVIEW_DECISION") ;
				if(iCount == 0) {
					sQuery = "SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM')CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL, WS_NAME, WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					sOutput = formObject.getDataFromDB(sQuery);
					if(sOutput != null && sOutput.size() > 0){
					  loadListView(sOutput,"LISTVIEW_DECISION","0,1,2,3,4,5,6,7,8");	
					}
				} */   //  Ask to yamini code use or not
				//createHistory();
				//createAllHistory();
				if(getGridCount("DECISION_HISTORY") == 0) {
					logInfo("DECISION_HISTORY","DECISION_HISTORY: "+getGridCount("DECISION_HISTORY"));
					sQuery="SELECT CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS FROM (SELECT TO_CHAR(CREATE_DAT,'DD/MM/YYYY HH:MI:SS AM') CREATE_DAT, USERID, USERNAME, GROUP_NAME, WS_DECISION,REJ_REASON, CHANNEL,WS_NAME,WS_COMMENTS,TO_CHAR(CREATE_DAT,'YYYYMMDDHH24:MI:SS') A FROM USR_0_AO_DEC_HIST WHERE WI_NAME = '"+sWorkitemId+"') ORDER BY A";
					logInfo("DECISIONTAB",sQuery);
					List<List<String>> recordList = formObject.getDataFromDB(sQuery); 
					loadListView(recordList,
							"CREATE_DAT,USERID,USERNAME,GROUP_NAME,WS_DECISION,REJ_REASON,CHANNEL,WS_NAME,WS_COMMENTS",
							"DECISION_HISTORY");							
					formObject.setStyle(DEC_STORAGE_DETAILS,VISIBLE,TRUE);
				} 
				try {
					formObject.setStyle(DOC_APPROVAL_OBTAINED, DISABLE, TRUE);
					formObject.setStyle(COURT_ORD_TRADE_LIC, DISABLE, TRUE);
					String sQuery1 = "SELECT DOB FROM ACC_RELATION_REPEATER WHERE WI_NAME ='"+sWorkitemId+"'";	
					List<List<String>> sOutput1 = formObject.getDataFromDB(sQuery1);
					String minorDOB = sOutput1.get(0).get(0);
					int minorAge = CalculateAge(minorDOB);
					if((minorAge >= 18) && (minorAge <= 21)){
						sQuery1 = "SELECT COUNT(1) AS COUNT FROM ACC_RELATION_REPEATER WHERE acc_relation = 'Guardian' AND "
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
				} catch (Exception e) {
					logError("Exception in onTabClick ",e);
				} 
				if(formObject.getValue("chq_eligibility").toString().equalsIgnoreCase("None")) {
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
		} catch(Exception e) {
			logError("Exception in onTabClickDDEChecker ",e);
		} finally {
			logInfo("Outside onTabClickDDEChecker ","  ");
		}
	}

	public void LoadDataOnForm() {
		try {
			String sTableCID="";
			String sCID="";
			String sQuery = "SELECT SNO,CID FROM ACC_RELATION_REPEATER WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY TO_NUMBER(SNO)";
			List<List<String>> sOutput=formObject.getDataFromDB(sQuery);
			System.out.println("sOutput1---"+sOutput);
			int iTotalCount = sOutput.size(); // Integer.parseInt(getTagValues(sOutput,"TotalRetrieved"));
			for(int i=0;i<iTotalCount;i++) {
				formObject.setTableCellValue(ACC_RELATION, i+1,2,sOutput.get(0).get(1));
			}
			sQuery = "SELECT CID,ACC_NO,ACC_STATUS,IBAN_NO,ACC_OPEN_DT FROM USR_0_PRODUCT_SELECTED WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY INSERTIONORDERID";
			sOutput=formObject.getDataFromDB(sQuery);
			System.out.println("sOutput2---"+sOutput);
			iTotalCount = sOutput.size(); // Integer.parseInt(getTagValues(sOutput,"TotalRetrieved"));
			for(int i=0;i<iTotalCount;i++) {
				sTableCID = sOutput.get(0).get(0);
					for(int j=0;j<iTotalCount;j++) {
						sCID = formObject.getTableCellValue(PRODUCT_QUEUE, j+1, 14);
						if(sTableCID.equalsIgnoreCase(sCID))
						{
							formObject.setTableCellValue(PRODUCT_QUEUE, j+1,4,sOutput.get(0).get(1));
							formObject.setTableCellValue(PRODUCT_QUEUE, j+1,0,sOutput.get(0).get(2));
							formObject.setTableCellValue(PRODUCT_QUEUE, j+1,15,sOutput.get(0).get(3));
							formObject.setTableCellValue(PRODUCT_QUEUE, j+1,18,sOutput.get(0).get(4));
							break;
						}				
					}
			}
			sQuery = "SELECT CID,CARD_NUMBER AS CARD_NUMBER FROM DEBIT_CARD_REP WHERE WI_NAME = '"+sWorkitemId+"' ORDER BY INSERTIONORDERID";
			sOutput=formObject.getDataFromDB(sQuery);
			System.out.println("sOutput3---"+sOutput);
			iTotalCount = sOutput.size(); // Integer.parseInt(getTagValues(sOutput,"TotalRetrieved"));
			for(int i=0;i<iTotalCount;i++) {
				sTableCID = sOutput.get(0).get(0);
					for(int j=0;j<iTotalCount;j++)
					{
						sCID = formObject.getTableCellValue(ACC_INFO_DC_LVW, j, 9);
						if(sTableCID.equalsIgnoreCase(sCID))
						{
							formObject.setTableCellValue(ACC_INFO_DC_LVW, j+1,6,sOutput.get(0).get(1));
							break;
						}				
					}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*public int validateIntegrationCalls() {
		int iRows = getGridCount(ACC_RELATION);  //repeat_frame
		for(int i=0;i<iRows-1;i++) {
			if(formObject.getTableCellValue(ACC_RELATION, i+1, 2).toString().equalsIgnoreCase("")) {
				sendMessageValuesList("","Customer ID is not created for all customers. Please close the workitem and open again");
				return 0;
			}
		}
		iRows = getGridCount(PRODUCT_QUEUE);
		for(int j=0;j<iRows-1;j++) {
			if(formObject.getTableCellValue(PRODUCT_QUEUE, j+1, 4).equalsIgnoreCase("")) {
				sendMessageValuesList("","Account is not created for all product codes. Please close the workitem and open again");
				return 0;
			}
		}
		return 1;
	}*/	
	
	public void visibleonSegmentBasisCPDCHECKER(String category) {
		if(category.equalsIgnoreCase("Aspire")||category.equalsIgnoreCase("Simplylife")) {
			formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);					
			formObject.setStyle(IDS_PC_CB_TP,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,FALSE);
		}
		else if(category.equalsIgnoreCase("Emirati Excellency")||category.equalsIgnoreCase("Excellency")||category.equalsIgnoreCase("Private Clients"))
		{
			formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_TP,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,FALSE);
			formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,TRUE);
		}
		else if(category.equalsIgnoreCase("Privilege") || category.equalsIgnoreCase("Emirati"))
		{
			formObject.setStyle(IDS_CB_SAL_TRANSFER,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_MORTGAGES,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_INSURANCE,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_TRB,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_OTHERS,VISIBLE,TRUE);
			formObject.setStyle(IDS_CB_VVIP,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_TP,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_TRAVEL,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_SPORT,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_SHOPPING,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,TRUE);
			formObject.setStyle(IDS_PC_CB_ENTERTAINMENT,VISIBLE,FALSE);
		}		
	}
	
	public void insertIntoReKeyTXNTable(String sCustNo,String sWIName, String sColumn,String sValue,String group) {
		logInfo("Inside insertIntoReKeyTXNTable", "");
		String sCount = "";
		String sWhere = "CUST_SNO='"+sCustNo+"' AND WI_NAME='"+sWIName+"' and activity_name='"+group+"'";
		try {
			String sQuery = "SELECT COUNT(WI_NAME) FROM USR_0_AO_REKEY WHERE CUST_SNO='"+sCustNo+"' and wi_name ='"+sWIName+"' and activity_name='"+group+"'";
			List<List<String>> output = formObject.getDataFromDB(sQuery);
			logInfo("insertIntoReKeyTXNTable", "sQuery---"+sQuery);
			logInfo("insertIntoReKeyTXNTable", "sCount---"+sCount);
			if(output != null && output.size() > 0){
				sCount = output.get(0).get(0);
				if(Integer.parseInt(sCount) > 0) {		
					updateDataInDB("USR_0_AO_REKEY",sColumn,sValue,sWhere); 
				} else {
					if(!sCustNo.equalsIgnoreCase("")) {
						insertDataIntoDB("USR_0_AO_REKEY",sColumn,sValue);
					}
				}
			}
		} catch(Exception e) {
			logError("insertIntoReKeyTXNTable", e);;
		} finally {
			logInfo("Outside insertIntoReKeyTXNTable ","  ");
		}
	}
	
	public String rekeycompare(String group) {
		logInfo("Inside rekeycompare group ",group);
		String value1 = "";
		try {
			logInfo("rekeycompare", "INSIDE");
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			String sBankRelation = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 7);
			String sFinalEIDANo = getFinalDataComparison(CHECKBOX_EIDANO_FCR,CHECKBOX_EIDANO_EIDA,CHECKBOX_EIDANO_MANUAL,FCR_EIDANO,EIDA_EIDANO,MANUAL_EIDANO);
			String sFinalEmail = getFinalDataComparison(CHECKBOX_EMAIL_FCR,CHECKBOX_EMAIL_EIDA,CHECKBOX_EMAIL_MANUAL,FCR_EMAIL,EIDA_EMAIL,MANUAL_EMAIL);
			String sFinalPhone = getFinalDataComparison(CHECKBOX_TELE_MOB_FCR,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_TELE_MOB_MANUAL,FCR_MOBILE,EIDA_MOBILE,MANUAL_MOBILE);
			String sFinalDOB = getFinalData(formObject.getValue(CHECKBOX_DOB_FCR).toString(),
					formObject.getValue(CHECKBOX_DOB_EIDA).toString(),formObject.getValue(CHECKBOX_DOB_MANUAL).toString(),
					getDateValue(FCR_DOB),getDateValue(EIDA_DOB),getDateValue(MANUAL_DOB));
			String sFinalDOB1 = sFinalDOB;
			sFinalDOB = convertInTo_To_Date(sFinalDOB);
			String sQuery = "select EIDA_CHECK ,EMAIL_CHECK,MOB_CHECK,to_char(DOB_CHECK,'dd/mm/yyyy') DOB_CHECK,"
					+ "IS_MANUAL_EIDANO,IS_MANUAL_MOBNO ,IS_MANUAL_EMAIL ,IS_MANUAL_DOB ,KEY_FLAG ,ACTIVITY_NAME "
					+ "from usr_0_ao_rekey where wi_name='"+sWorkitemId+"' and CUST_SNO = '"+sCustNo+"' and "
					+ "activity_name='CRO'";
			logInfo("rekeycompare", "sQuery: "+sQuery);
			List<List<String>> output = formObject.getDataFromDB(sQuery);
			logInfo("rekeycompare", "output: "+output);
			String eidaflag = "";
			String emailflag = "";
			String phoneflag = "";
			String dobflag = "";
			if(output != null && output.size() > 0 && null != output.get(0)) {
				String eidavalue = output.get(0).get(0);
				String emailvalue = output.get(0).get(1);
				String phonevalue = output.get(0).get(2);
				String dobvalue = output.get(0).get(3);
				String sQuery2 = "select EIDA_CHECK ,EMAIL_CHECK,MOB_CHECK,to_char(DOB_CHECK,'dd/mm/yyyy') DOB_CHECK,"
						+ "IS_MANUAL_EIDANO,IS_MANUAL_MOBNO ,IS_MANUAL_EMAIL ,IS_MANUAL_DOB ,KEY_FLAG ,ACTIVITY_NAME "
						+ "from usr_0_ao_rekey where wi_name='"+sWorkitemId+"' and CUST_SNO = '"+sCustNo+"' and "
						+ "activity_name='CPD'";
				logInfo("rekeycompare", "sQuery2: "+sQuery2);
				List<List<String>> output2 = formObject.getDataFromDB(sQuery2);
				logInfo("rekeycompare", "output2: "+output2);
				String eidavalue_2 = output2.get(0).get(0);
				String emailvalue_2 = output2.get(0).get(1);
				String phonevalue_2 = output2.get(0).get(2);
				String dobvalue_2 = output2.get(0).get(3);
				if("New".equalsIgnoreCase(sBankRelation)) {
					eidaflag = "true";
					emailflag = "true";
					phoneflag = "true";
					dobflag = "true"; 
				} else {
					if((!sFinalEIDANo.equalsIgnoreCase(eidavalue) || !sFinalEIDANo.equalsIgnoreCase(eidavalue_2))
							&& "True".equalsIgnoreCase(formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString())) {
						eidaflag="true";
					} else
						eidaflag="false";
					if((!sFinalEmail.equalsIgnoreCase(emailvalue) || !sFinalEmail.equalsIgnoreCase(emailvalue_2)) 
							&& "True".equalsIgnoreCase(formObject.getValue(CHECKBOX_EMAIL_MANUAL).toString())) {
						emailflag="true";
					} else
						emailflag="false";
					if((!sFinalPhone.equalsIgnoreCase(phonevalue) || !sFinalPhone.equalsIgnoreCase(phonevalue_2)) 
							&& "True".equalsIgnoreCase(formObject.getValue(CHECKBOX_TELE_MOB_MANUAL).toString())) {
						phoneflag="true";
					} else
						phoneflag="false";
					logInfo("rekeycompare", "OLD DOb----"+sFinalDOB+ " new dob "+dobvalue+ " "
						+formObject.getValue(CHECKBOX_DOB_MANUAL).toString());
					if((!sFinalDOB1.equalsIgnoreCase(dobvalue) || !sFinalDOB1.equalsIgnoreCase(dobvalue_2)) 
							&& "True".equalsIgnoreCase(formObject.getValue(CHECKBOX_DOB_MANUAL).toString())) {
						dobflag="true";
					} else
						dobflag="false";
				}
				value1 = "'"+group+"','"+sFinalEIDANo+"','"+sFinalEmail+"','"+sFinalPhone+"',"+sFinalDOB+",'"+sWorkitemId
						+"','"+sCustNo+"','"+formObject.getValue(CHECKBOX_EIDANO_FCR).toString()+"','"
						+formObject.getValue(CHECKBOX_EIDANO_EIDA).toString()+"','"
						+eidaflag+"','"+formObject.getValue(CHECKBOX_TELE_MOB_FCR).toString()+"','"
						+formObject.getValue(CHECKBOX_TELE_MOB_EIDA).toString()+"','"+phoneflag
						+"','"+formObject.getValue(CHECKBOX_EMAIL_FCR).toString()+"','"+formObject.getValue(CHECKBOX_EMAIL_EIDA).toString()
						+"','"+emailflag+"','"+formObject.getValue(CHECKBOX_DOB_FCR).toString()+"','"
						+formObject.getValue(CHECKBOX_DOB_EIDA).toString()+"','"+dobflag+"'";
			}
		} catch (Exception e) {
			logError("rekeycompare", e);
		} finally {
			logInfo("rekeycompare", "return value from rekeycompare: "+value1);
		}
		return value1;
	}
	
	public void updateReKeyTemp(String group) {
		logInfo("updateReKeyTemp", "INSIDE");
		try {
			List<List<String>> Output;
			String sCount = "";
			int iSelectedRow = Integer.parseInt(formObject.getValue(SELECTED_ROW_INDEX).toString());
			String sCustNo = formObject.getTableCellValue(ACC_RELATION, iSelectedRow, 0);
			String sWhere ="SNO='"+sCustNo+"' AND WI_NAME='"+sWorkitemId+"' and group_name='"+group+"'";
			String sQuery = "SELECT COUNT(WI_NAME) FROM USR_0_AO_REKEY_TEMP WHERE SNO='"+sCustNo+"' "
					+ "and wi_name ='"+sWorkitemId+"' and group_name='"+group+"'";
			List<List<String>> output = formObject.getDataFromDB(sQuery);
			if(output != null && output.size() > 0){
				sCount = output.get(0).get(0);
			}
			String EIDAFLAG =  formObject.getValue(CHECKBOX_EIDANO_MANUAL).toString();
			String EMAILFLAG =  formObject.getValue(CHECKBOX_EMAIL_MANUAL).toString();
			String PHONEFLAG = formObject.getValue(CHECKBOX_TELE_MOB_MANUAL).toString();
			String DOBFLAG =  formObject.getValue(CHECKBOX_DOB_MANUAL).toString();
			String sColumn="EIDAFLAG,EMAILFLAG,PHONEFLAG,DOBFLAG";
			String value1="'"+EIDAFLAG+"'"+(char)25+"'"+EMAILFLAG+"'"+(char)25+"'"+PHONEFLAG+"'"+(char)25+"'"+DOBFLAG
					+"'";
			if(Integer.parseInt(sCount) > 0) {		
				updateDataInDB("USR_0_AO_REKEY_TEMP",sColumn,value1,sWhere); 
			}		
		} catch(Exception e) {
			logError("Exception in updateReKeyTemp", e);
		} finally {
			logInfo("Outside updateReKeyTemp ","  ");
		}
	}
	
	@Override
	public String executeServerEvent(IFormReference formObject, String controlName, String eventType, String data) {
		logInfo("Inside executeServerEvent ","Event: " + eventType + ", ControlName: " + controlName + ", Data: " + data);
		sendMessageList.clear();
		List<List<String>> list;
		String sQuery = "";
		String sWMSID ="";
		String sID = "";
		String sServiceShortName = "";
		String sHomeBrShortName = "";
		sendMessageList.clear();
		logInfo("sendMessageList=",sendMessageList.toString());
		String retMsg = getReturnMessage(true ,controlName);
		try {
			if(eventType.equalsIgnoreCase(EVENT_TYPE_LOAD)) {
				String fieldList = eventOnLoadDDEChecker(controlName, eventType,data);
				logInfo("executeServerEvent","Is Control Enabled2: "+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
				if(!fieldList.isEmpty()){
					return getReturnMessage(true, controlName, fieldList);
				}
				populateUAEPassInfoStatus(sWorkitemId);
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CLICK)) { 
				retMsg = eventOnClickEventDDEChecker(controlName,eventType,data);
				logInfo("","retMsg 1 : "+retMsg);
				logInfo("executeServerEvent","Is Control Enabled3: "+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_CHANGE)) {
				eventOnChangeEventDDEChecker(controlName,eventType,data);
				logInfo("executeServerEvent","Is Control Enabled4: "+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
			} else if (eventType.equalsIgnoreCase(EVENT_TYPE_LOSTFOCUS)) {  
				eventOnLostFocusEventDDEChecker(controlName,eventType,data);
				logInfo("executeServerEvent","Is Control Enabled5: "+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
			}
		} catch (Exception e) {
			logError("Exception in executeServerEvent Event- " + eventType + "control name- " +controlName+ ": ", e);
		} finally{
			logInfo("sendMessageList=",sendMessageList.toString());
			if(!sendMessageList.isEmpty()){
				if(sendMessageList.get(1).contains(" does not start with 971") ||
						sendMessageList.get(1).equalsIgnoreCase("Mobile number is not of 12 digits") || 
						sendMessageList.get(1).equalsIgnoreCase("Residence Telephone Number is not of 11 digits")) {
					return getReturnMessage(true,controlName,sendMessageList.get(0)+"###"
							+ sendMessageList.get(1));
				}
				return  getReturnMessage(false,"",sendMessageList.get(0).toString()+"###"
						+ sendMessageList.get(1).toString());
			}
		}
		logInfo("","retMsg 2 : "+retMsg); 
		logInfo("executeServerEvent","Is Control Enabled1: "+isControlEnabled(CHECKBOX_TELE_MOB_MANUAL));
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
		//Unused
	}

	@Override
	public String validateDocumentConfiguration(String arg0, String arg1, File arg2, Locale arg3) {
		return null;
	}

	public boolean saveAndNextValidation(String data) {
		try {
			logInfo("saveAndNextValidation", "INSIDER");
			int selectedSheetIndex = Integer.parseInt(data);
			logInfo("saveAndNextValidation","selectedSheetIndex: "+selectedSheetIndex);
			if(selectedSheetIndex == 1){
				if(!validationTab()) {
					return false;
				}
				reKeyLogic();
			//} else if(selectedSheetIndex == 17){
			} else if(selectedSheetIndex == 18){ // changes for family banking
				logInfo("saveAndNextValidation", "decision");
				formObject.clearCombo(CRO_DEC);
				int iCount =getListCount(CRO_DEC);
				if(iCount == 0) {
					String sQuery1 = "Select to_char(WS_DECISION) from USR_0_DECISION_MASTER where ws_name="
							+ "'"+formObject.getValue(CURR_WS_NAME).toString()+"'"
							+ " order by to_char(WS_DECISION)";
					addDataInComboFromQuery(sQuery1, CRO_DEC);
				}

			}else if(selectedSheetIndex == 16){ //Jamshed
				logInfo("saveAndNextValidation","Inside 16 sheetid sheetIndex---->>>>>>> "+selectedSheetIndex);
				if(sActivityName.equalsIgnoreCase(ACTIVITY_DDE_ACCOUNT_INFO_CHECK)){
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
						formObject.addItemInCombo(CRO_DEC, "Send To Vigilance", "Send To Vigilance");
						logInfo("saveAndNextValidation","Send To Vigilance Added>>>>>>>");
					}*/
					if(! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Upgrade") && 
							! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Downgrade") && 
							! formObject.getValue(REQUEST_TYPE).toString().equalsIgnoreCase("Family Banking")){
						
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
			paramlist.add (PARAM_TEXT+"CRM_DDE");
			formObject.getDataFromStoredProcedure("SP_TemplateGeneration_SMS", paramlist);
			paramlist.clear();
			if(!(formObject.getValue(CHANNEL_TYPE).toString().equalsIgnoreCase("Alternate") 
					&& formObject.getValue(SOURCING_CHANNEL).toString().equalsIgnoreCase("DFC"))) {
				paramlist.add (PARAM_TEXT+sWorkitemId);
				paramlist.add (PARAM_TEXT+"QDE");
				formObject.getDataFromStoredProcedure("SP_TemplateGeneration_SMS", paramlist);
			}
			String sPrimaryCust =getPrimaryCustomerSNO();
			String sNomID =formObject.getValue("NOM_ID").toString();			
			String sQuery = "SELECT CID FROM ACC_RELATION_REPEATER WHERE SNO = '"+sPrimaryCust+"' AND WI_NAME  = "
					+ "(SELECT WI_NAME FROM "+sExtTable+" WHERE NOM_ID= '"+sNomID+"')";
			List<List<String>> output = formObject.getDataFromDB(sQuery);
			String cid = ""; 
			if(output.size()>0) {
				cid = output.get(0).get(0);
			}
			updateDataInDB("USR_0_NOM_MAST","CUST_ID","'"+cid+"'","NOM_ID ='"+sNomID+"'");			
			return true;
		} catch (Exception e) {
			logError("afterSubmitJSP", e);
		}
		return true;

	}
	
	private boolean submitWorkitem(String data) {
		try {
			logInfo("submitWorkitem","INSIDE");
			createHistory();
			createAllHistory();
			String CompAppReq = "";
			String sQuery = "SELECT IS_COMPLIANCE_VISITED FROM "+sExtTable+" WHERE WI_NAME = '"+sWorkitemId+"'";
			List<List<String>> output = formObject.getDataFromDB(sQuery);
			if(output != null && output.size() > 0){
				if(output.get(0).get(0).equalsIgnoreCase("Yes")) {					
					CompAppReq="No";
					String sUpdateDecision1="update "+sExtTable+" set IS_COMP_REQ='No' Where WI_NAME='"+ sWorkitemId +"'";
					logInfo("submitWorkitem",sUpdateDecision1+"   sUpdateDecision1");
					formObject.saveDataInDB(sUpdateDecision1);
				} else {
					sQuery="SELECT COUNT(WI_NAME) AS COUNT_WI FROM USR_0_RISK_ASSESSMENT_DATA WHERE APPROVAL_REQ='Yes'"
							+ " and wi_name = '"+sWorkitemId+"'";
					output=formObject.getDataFromDB(sQuery);
					if(!output.get(0).get(0).equalsIgnoreCase("0")) {
						CompAppReq="Yes";
						String sUpdateDecision1="update "+sExtTable+" set IS_COMP_REQ='Yes' Where WI_NAME='"+ sWorkitemId +"'";
						logInfo("submitDDECheckerValidations","in sUpdateDecision1--"+sUpdateDecision1);
						formObject.saveDataInDB(sUpdateDecision1);
						if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve")) {
							sendMessageValuesList(CRO_DEC, "Compliance Approval is Required");
							return false;
						} //change 29JUNE2021
					}
					else {
						CompAppReq="No";
						String sUpdateDecision1="update "+sExtTable+" set IS_COMP_REQ='No' Where WI_NAME='"+ sWorkitemId +"'";
						logInfo("submitDDECheckerValidations","in sUpdateDecision1--"+sUpdateDecision1);
						formObject.saveDataInDB(sUpdateDecision1);
					}
				}
			}
			if(formObject.getValue(CRO_DEC).toString().equalsIgnoreCase("Approve") 
					&& CompAppReq.equalsIgnoreCase("No")) {					
				int update = updateDataInDB("USR_0_INTEGRATION_CALLS","STATUS","'Pending'","WI_NAME ='"
					+sWorkitemId+"' and CALL_NAME LIKE 'CUSTOMER_MODIFY%'"); 
				String sCustNo = getPrimaryCustomerSNO();
				String sQuery1 = "SELECT CUST_SEG,CUST_ID,CUST_FULL_NAME,FINAL_MOBILE_NO FROM USR_0_CUST_TXN "
						+ "WHERE WI_NAME ='"+sWorkitemId+"' AND CUST_SNO='"+sCustNo+"'";
				output = formObject.getDataFromDB(sQuery1);
				String sCustSeg="";
				String sCustID="";
				String sCustName="";
				String sMobile= "";
				if(output.size()>0) {
					sCustSeg=output.get(0).get(0);
					sCustID=output.get(0).get(1);
					sCustName=output.get(0).get(2);
					sMobile=output.get(0).get(3);
				}
				String decision=formObject.getValue(CRO_DEC).toString();
				Date d= new Date();				
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	            String sDate = dateFormat.format(d);
	            String sQ2="select decode (bank_relation,'New','NTB', bank_relation) as pri_bank_relation from"
	            		+ "acc_relation_repeater where sno=1 and wi_name='"+sWorkitemId+"' ";
	            output =formObject.getDataFromDB(sQ2); 
	            String pri_bank_rel =  (output != null && output.size() > 0)  ? output.get(0).get(0).toString() : "";
				update = updateDataInDB(sExtTable,"ACCOUNT_INFO_CHECKER_DEC,PRI_CUST_SEGMENT,CUST_ID,"
						+ "PRI_CUST_NAME,PRI_CUST_MOB,WI_COMPLETED_FROM,CRM_NAME,CRM_SUBMIT_DATE,pri_bank_relation"
						,"'"+decision+"'"+(char)25+"'"+sCustSeg+"'"+(char)25+"'"+sCustID+"'"+(char)25+"'"+sCustName
						+"'"+(char)25+"'"+sMobile+"'"+(char)25+"'"
						+ sActivityName +"'"+(char)25+"'"+userId+"'"+(char)25+"'"+sDate+"'"+(char)25+"'"
						+ pri_bank_rel+"'","WI_NAME ='"+sWorkitemId+"'");
				String sProcName1="SP_TemplateGenerationEmailDt";
				List<String> paramlist =new ArrayList<>();
				paramlist.add (PARAM_TEXT+sWorkitemId);
				formObject.getDataFromStoredProcedure(sProcName1, paramlist);
				String queryforwl ="select count(prod_code) as COUNT_wi from usr_0_product_selected where "
						+ "prod_code not in (select prod_code from USR_0_BLOCK_PROD_MAST) and wi_name  ='"
						+sWorkitemId+"'";
				output=formObject.getDataFromDB(queryforwl);
				if(output.get(0).get(0).equalsIgnoreCase("0")) {
					String sColumnWL="all_prod_blocked";
					String	 sValuesWL = "'Y'";
					String 	sWhereWL ="wi_name='"+sWorkitemId+"'";
					int OutputWL=updateDataInDB(sExtTable,sColumnWL,sValuesWL,sWhereWL);                  
				} else {
					String sColumnWL1="all_prod_blocked";
					String	 sValuesWL1 = "'N'";
					String 	sWhereWL1 ="wi_name='"+sWorkitemId+"'";
					int sOutputWL1=updateDataInDB(sExtTable,sColumnWL1,sValuesWL1,sWhereWL1); 
				}
				String sProcName = "SP_TemplateGeneration_SMS";
				paramlist.clear();
				paramlist.add (PARAM_TEXT+sWorkitemId);
				paramlist.add (PARAM_TEXT+"CRM_DDE");
				formObject.getDataFromStoredProcedure(sProcName, paramlist);
				loadIntegrationDataOnForm();
			}
		} catch (Exception e) {
			logError("submitWorkitem", e);
		}
		return true;
	}
}