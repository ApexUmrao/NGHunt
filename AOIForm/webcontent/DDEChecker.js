function onDDECheckerLoad() {
	console.log('***** inside onFormLoad *****');
	var workstepName = getWorkItemData('activityName'); 
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	var controlNames = [WI_NAME,CURR_WS_DETAIL,'TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID','TXT_CURRWS'];
	var controlValues = [wi_name,workstepName,CustName,CustDob,CustId,workstepName];
	setMultipleFieldValues(controlNames,controlValues);
	setStyle(CURR_WS_DETAIL,"visible","false");
	var DDECheckerFieldsEnable = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ,BUTTON_GENERATE_TEMPLATE];  // 'Frame41','Frame34','Frame39','Frame42','Frame53', needs to be included
	var DDECheckerFieldsDisable = [CHQ_ELIGIBILITY,SANCT_RISK_ASSESS_MARKS,BTN_TRSD_CHECK,SEC_SS_RISK_ASSESS,
	                               SEC_SS_TRSD,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,BTN_NEXT_CUST_SANCT,
	                               BTN_DEDUPE_SEARCH,CK_PER_DET,CHK_CONTACT_DET,CHK_INTERNAL_SEC,
	                               CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,CHK_BANKING_RELATION,
	                               CHECKBOX_FATCA,CRS_CB,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS_CRO,
	                               SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CRO,SEC_PRODUCTION_CPD,SEC_OPT_PROD_CPD,
	                               SEC_OPT_PROD_CRO,SEC_FACILITY_CPD,SEC_FACILITY_CRO,SEC_DOC_REQ_CPD,
	                               SEC_DOC_REQ_CRO,SEC_SI_SIRFT,SEC_SI_SWP,SEC_DEL_INST,SEC_DEL_ADD,SEC_ACC_INFO_PD,
	                               SEC_ACC_INFO_AOR_MAKER,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,IS_COMPLIANCE_RISK_ASSESS,
	                               IS_COMPLIANCE_NAME_SCR,IS_PROD_APP_REQ,IS_CALL_BACK_REQ,LVW_DEDUPE_RESULT,FRAMEFATCA,
	                               CRS_RES_PERM_ADRS_US,SEC_CRS_DETAILS,PD_ANY_CHNG_CUST_INFO,SEC_CONTACT_DET_RA,
	                               SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_SIGN_OFF,SEC_BNK_REL_UAE_OVRS,
	                               SEC_ASSESS_OTH_INFO,SEC_FUND_EXP_RELTNSHP,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,
	                               'SEC_INT_DETAIL','prod_combo',SEC_PERSONAL_DET,FRAME_CLIENTQUESTIONS,
	                               SEC_ADD_NEW_CUSTOMER,NEW_CUST_FETCH_EIDA,SI_FRST_PAYMNT,SI_LST_PAYMNT,
	                               'BTN_SELECT_CUST','KYC_PRE-ASSESSMENT']; 
	// frame 35 not in excel  to be included --- frame35 and frame 4 are whole tab
	enableControls(DDECheckerFieldsEnable);
	disableControls(DDECheckerFieldsDisable);
	disableControls(ALL_CHECKBOX_EIDA);
	disableControls(ALL_CHECKBOX_MANUAL);
	disableControls(ALL_CHECKBOX_FCR);
	//var DDECheckerFieldsVisible	=	['Tab5'];
	var DDECheckerFieldsHidden	=	[CURR_WS_DETAIL];
	//showControls(DDECheckerFieldsVisible);
	hideControls(DDECheckerFieldsHidden);	
	secAccRelCPDDisable();
	setTabVisible();			
	setStyle(SEARCH_EIDA_CARDNO, PROPERTY_NAME.DISABLE, 'false');
	setStyle(SEARCH_NATIONALITY,"disable","true");    	   // cant to be done through disableControlFunction
	if(getValue(CRO_DEC).toLowerCase() == ('approve')) {	// on loading if decision is defaulted approve then rject_reason is enabled
		setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
		var controlnames = [CRO_REJ_REASON];
		clearAOControls(controlnames);
	}
	if(getValue(CHQ_ELIGIBILITY) == 'None') {
		var controlNames = [AO_CRO_DEC,AO_CRO_REJ_REASON,AO_CRO_REMARKS];
		var controlValues = ['Return to Originator','Bounced cheques > 3','Bounced cheques are more than 3'];
		setMultipleFieldValues(controlNames,controlValues);
	}
	//setTabStyle("tab3",17, "visible", "false"); 
	//Enable_Disable_Load(sCurrTabIndex, sAcitivityName);  // function created needs to be verified
	if(getValue(REQUEST_TYPE) == 'Category Change Only' 
		|| getValue(REQUEST_TYPE) == 'New Account With Category Change'){
		setTabStyle("tab3",11, "visible", "true");
	}
	disableControls(disableControlskyc);
	disableControls(disableEmployeeAddress);
	setStyle(EDIT1, PROPERTY_NAME.DISABLE, 'true');
	openTrsdJsp();
	setKYCFlagPrivateclient();//Dcra
	visiblePreAssesmentField();//AO CCO AND UPGRADE
	if(!(getValue('SKIPUAEPASS_REASON') == '' || getValue('SKIPUAEPASS_REASON') == null)){
		setStyle('SKIPUAEPASS_REASON','disable', 'false');
	}
}

function handleDDECheckerEventJs(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickDDECheckerEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeDDECheckerEvent(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
			//lostFocusDDECheckerEvent(event);		
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
			gotFocusDDECheckerEvent(event);
		}
	} 
}

function changeDDECheckerEvent(event){
	var workstepName = getWorkItemData('activityName');
	if(event.target.id == CRO_DEC) {
		if(getValue(CRO_DEC).toLowerCase() == ('approve')) {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
			var controlnames = [CRO_REJ_REASON];
			clearAOControls(controlnames);
		} else {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, FALSE);
		}
	} else if(event.target.id == CRO_REJ_REASON) {
		if(!getValue(CRO_REJ_REASON) == '') {
			if(getValue(CRO_DEC) == '') {
				showMessage(CRO_DEC, 'please select user decision first', 'error');
				var controlnames = [CRO_REJ_REASON];
				clearAOControls(controlnames);
				setStyle(EDIT, PROPERTY_NAME.DISABLE, FALSE);
			}
		}
	} else if(event.target.id == (CHECKBOX_EIDANO_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (CHECKBOX_EMAIL_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (CHECKBOX_TELE_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}
}

function  clickDDECheckerEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	var actionnames = [];
	var actionvalues = [];
	console.log('Click Event of DDE Started for '+controlName);
	if(controlName == 'P_ECB_CHQ_VALIDATION' && !controlValue == '' && getValue('P_ECB_CHQ_VALIDATION')== 'false') {
		enableControls('P_ECB_REASON');
	} else if(controlName == 'P_ECB_CHQ_VALIDATION' && !controlValue == '' && getValue('P_ECB_CHQ_VALIDATION')== 'true') {
		//setValues({"P_ECB_REASON":""},true);
		setFieldValue('P_ECB_REASON','');
		disableControls('P_ECB_REASON');
	} else if(controlName == CRO_DEC){
		console.log('controlName1: ' + controlName);
		if(controlName == CRO_DEC && getValue(CRO_REJ_REASON) == 'Approve') {
			console.log('controlValue1: ' + controlValue);
			disableControls(CRO_REJ_REASON);
			lockAOSection(CRO_REJ_REASON);
			if(getValue(IS_COMPLIANCE_RISK_ASSESS)== 'true'){
				if(getValue("FINAL_RISK_VAL")== 'Increased Risk'){
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					actionvalues = ['true','true','true','true'];
					setMultipleFieldValues(controlnames, controlvalues);

					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					enableControls(controlnames);
				}
				else if(getValue("FINAL_RISK_VAL")== 'UAE-PEP'){
					actionnames = [L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ];
					actionvalues = ['true','true','true','true'];
					setMultipleFieldValues(controlnames, controlvalues);
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					enableControls(controlnames);
				}
				else {
					actionnames = [L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ];
					actionvalues = ['false','false','false','false'];
					setMultipleFieldValues(controlnames, controlvalues);
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					disableControls(controlnames);
				}
			}
		} else {
			actionnames = [L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ,CRO_REJ_REASON];
			actionvalues = ['false','false','false','false','true'];
			setMultipleFieldValues(controlnames, controlvalues);
			console.log('controlValu2: ' + controlValue);
			actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
			disableControls(controlnames);
			lockAOSection(CRO_REJ_REASON);			
		}
	} else if(controlValue ==  DOC_APPROVAL_OBTAINED){
		if(getValue(DOC_APPROVAL_OBTAINED)== 'true'){
			//setValues({COURT_ORD_TRADE_LIC:"false"},true);
			setFieldValue(COURT_ORD_TRADE_LIC,'false');
		}
		else{
			//setValues({COURT_ORD_TRADE_LIC:"true"},true);
			setFieldValue(COURT_ORD_TRADE_LIC,'true');
		}
	} else if(controlValue ==  COURT_ORD_TRADE_LIC){
		if(getValue(DOC_APPROVAL_OBTAINED)== 'true'){
			//setValues({DOC_APPROVAL_OBTAINED:"false"},true);
			setFieldValue(DOC_APPROVAL_OBTAINED,'false');
		}
		else{
			//setValues({DOC_APPROVAL_OBTAINED:"true"},true);
			setFieldValue(DOC_APPROVAL_OBTAINED,'true');
		}
	} else if(controlName == BTN_ECB_REFRSH ){
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName== BUTTON_SUBMIT){
		console.log('inside button submit');
		var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
		var resultFATCACheck = checkAttchedDocument('FATCA#');
		console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
		executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
//		executeServerEvent(event.target.id, event.type, '', false);
		/*var value=getValue(NIG_MAKER);
		 if (getValue(DOC_APPROVAL_OBTAINED) =='false' && getValue('COURT_ORD_TRADE_LIC') =='false') {
			showMessage('', 'Please select the appropriate checkbox to complete the validation');
			return;
		} else if (getValue('P_ECB_CHQ_VALIDATION') =='false' && getValue('P_ECB_REASON')== '') {
			showMessage('P_ECB_REASON', 'Please enter reason for skipping the ECB check');
			return;
		} else if(value == 'yes'){
			console.log('***** inside NIGMAKER *****');
			showConfirmDialog(ALERT_DEC_COD,confirmDialogButtons,function(result) {
				if(result == true) {
					executeServerEvent(controlName, event.type, '', false);
				}
				else{
					actionnames = ['SUBMIT_1','static_submit','Command24'];
					enableControls(controlnames);
					return;
				}
			});
		}*/
	} else if (controlName == 'PRODUCT_QUEUE_MODE_OF_FUNDING') { 
		var selectedRows = getSelectedRowsIndexes(PRODUCT_QUEUE); 
		var iRows = getGridRowCount(PRODUCT_QUEUE);
		var iSelectedRow = selectedRows[0];
		if(iRows>1) {
			var sMode = getValueFromTableCell(PRODUCT_QUEUE,iSelectedRow,8);
			if(sMode == "Transfer - Internal") {
				setCellDisabled(PRODUCT_QUEUE,iSelectedRow,9,'true');
				setCellDisabled(PRODUCT_QUEUE,iSelectedRow,13,'true');
				setCellDisabled(PRODUCT_QUEUE,iSelectedRow,7,'false');
				clearTableCellCombo(PRODUCT_QUEUE,iSelectedRow,9);
				executeServerEvent(controlName, event.type,sMode+"#"+iSelectedRow,false);
			} else {
				setCellDisabled(PRODUCT_QUEUE,iSelectedRow,7,'true');
				setCellDisabled(PRODUCT_QUEUE,iSelectedRow,9,'false');
				setCellDisabled(PRODUCT_QUEUE,iSelectedRow,13,'false');
			}
		} 
	}
}

function gotFocusDDECheckerEvent(event){
	console.log('inside gotFocusDDECheckerEvent >>');
	if (event.target.id == 'ACC_RELATION.SNO') {//wrong
		executeServerEvent(pControlName, EVENT_TYPE.LOSTFOCUS, '', false);
	}
	// NGCHKREPEATER
}

var counter = 'false';
function postServerEventHandlerDDEChecker(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
   
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:'+jsondata);
	if (null != jsondata.message && '' != jsondata.message && 
			!(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1)) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			showMessage(arr[0], arr[1], 'error');
		}
	}
	console.log('is disabled CHECKBOX_TELE_MOB_MANUAL: '+getStyle(CHECKBOX_TELE_MOB_MANUAL,'isdisabled'));
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		}
		if((jsondata.message).indexOf('#') != -1) {
			var fieldList = (jsondata.message).split('#');
			for(var i=0; i<fieldList.length; i++){
				setStyle(fieldList[i], 'fontcolor', '00cc00');
			}
		}
		setStyle(SEC_FB, PROPERTY_NAME.DISABLE, 'true');
		break;
	case EVENT_TYPE.CLICK:	
		if(!getValue(CHECKBOX_TELE_MOB_MANUAL) && !getValue(CHECKBOX_TELE_MOB_FCR)
				&& !getValue(CHECKBOX_TELE_MOB_EIDA)) {
			setStyle('CHECKBOX_TELE_MOB_MANUAL','disable','false');
		}
		if('saveNextTabClick' == controlName) {
//			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if('afterSaveNext' == controlName) {
			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if(controlName == BUTTON_SUBMIT) { 
			console.log('BUTTON_SUBMIT, workstep: '+workstepName+', json message: '+jsondata.message);
			saveWorkItem();
			/*if(getValue(CRO_DEC)== 'Approve' && counter == 'false' && jsondata.success){
				var result = showOpenCallJspTab(wi_name);
				counter = 'true';
			}else{
				counter = 'false';
			}
			if(jsondata.success && counter == 'false') {
				if(WORKSTEPS.DDE_ACCOUNT_INFO_CHECK == workstepName && jsondata.message == '') {
					console.log(getValue(CRO_DEC));
					if(getValue(CRO_DEC).toLowerCase() == ('approve')){
						executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, '0', false);
					}else if (!(getValue(CRO_DEC).toLowerCase() == ('approve') || getValue(CRO_DEC).toLowerCase() == (''))){
						saveWorkItem();
						completeWorkItem();
					}
				} else {
					saveWorkItem();
					completeWorkItem();
				}
			} else {
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
				saveWorkItem();
				//completeWorkItem();
			}*/
			if(jsondata.success) {
				var msg = 'Selected passport holder Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?';
				if(jsondata.message == msg) {
					showConfirmDialog(msg, confirmDialogButtons, function(result) {
						if(result == true) {
							var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
							var resultFATCACheck = checkAttchedDocument('FATCA#');
							console.log('interSubmit doc result'+resultEIDACheck+', '+resultFATCACheck);
							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'
									+resultFATCACheck+'%%%interSubmit', false);
						} else if(result == false) {
							setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
							return;
						} 
					});
				} else if(jsondata.message == CA008) {
					if(getValue(CRO_DEC) == 'Approve') {
						var countWelcome = DocTypeAttachedcount('Instant_Welcome_Letter');
						if (countWelcome == 0){
							showConfirmDialog('Do you want to generate Instant letter', confirmDialogButtons, function(result) {
								if(result == true) {
									executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, 'true', false);
								} else if(result == false) {
									executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, 'false', false);
								}
							});	

						} else {
							executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, 'docAttachedAlready', false);
						}
					} else {
						showConfirmDialog(CA008, confirmDialogButtons, function(result) {
							if(result == true) {
								executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);	
							} else if(result == false) {
								saveWorkItem();
								setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
								return;
							}
						});
					}					 
				} else if(!(getValue(CRO_DEC) == '')){
					saveWorkItem();
					completeWorkItem();
				} else {
					saveWorkItem();
					setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
				}
			} else {
				var msg = 'Selected passport holder Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?';
				if(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1) {
					showConfirmDialog(msg, confirmDialogButtons, function(result) {
						if(result == true) {
							var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
							var resultFATCACheck = checkAttchedDocument('FATCA#');
							console.log('interSubmit doc result'+resultEIDACheck+', '+resultFATCACheck);
							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'
									+resultFATCACheck+'%%%interSubmit', false);
						} else if(result == false) {
							return;
						} 
					});
				}
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
			}
		}  else if (controlName == 'generateInstanLetter'){
			if(jsondata.success){
				showConfirmDialog(CA008, confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);	
					} else if(result == false) {
						saveWorkItem();
						setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
						return;
					}
				});
			}
		} /*else if (controlName == 'generateInstanLetter'){
			if(jsondata.message == 'TEMPLATESTARTED'){
				saveWorkItem();
				var countWelcome = DocTypeAttachedcount('Instant_Welcome_Letter');
				if (countWelcome == 0){
					showConfirmDialog('Do you want to generate Instant letter', confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, 'true', false);
						} else if(result == false) {
							executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, 'false', false);
						}
					});	

				} else {
					executeServerEvent('generateInstanLetter', EVENT_TYPE.CLICK, 'docAttachedAlready', false);
				} 
			}else{
				if(jsondata.success){
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							saveWorkItem();
							completeWorkItem();
						} else if(result == false) {
							return;
						}
					});	
				} else{
					showMessage(BUTTON_SUBMIT, arr[1], 'error');
					setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
				}
			}

		}*/
		break;	
	}
}

function saveAndNextPreHookDDEChecker(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookDDE: ' + input);
	PepNationality(); //Dcra
	AddSourceIncome();//Dcra
	pepLogic();
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('is disabled CHECKBOX_TELE_MOB_MANUAL: '+getStyle(CHECKBOX_TELE_MOB_MANUAL,'isdisabled'));
	console.log('save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function onClickTabDDEAccountInfoChecker(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
//	if(18 == getSheetIndex('tab3')) {
	if(19 == getSheetIndex('tab3')) {	// Changes for Family Banking 
		showMessage('', 'Please click on Done button', 'error');
	} else {
		executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, false);
		selectSheet(tabId,sheetIndex);
	}
}

function showOpenCallJspTab(workItemName) {
	var returnVal="";
	var parameterJSP = workItemName;
	var urlDoc = document.URL;
	var jspURL="";
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
	jspURL=sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status.jsp?WI_NAME="+workItemName;
	document.getElementById(APPLICATION_ORCHESTRATION_TAB).textContent = 'Application Orchestration';
	document.getElementById('PRODUCT_JSP').src=jspURL;
//	setTabStyle(DDE_CHECKER_TAB_ID,18, "visible", "true");
//	selectSheet(DDE_CHECKER_TAB_ID,18);	
	setTabStyle(DDE_CHECKER_TAB_ID,19, "visible", "true");	// Changes for Family Banking 
	selectSheet(DDE_CHECKER_TAB_ID,19);	// Changes for Family Banking 
}

function selectRowHookDDEChecker(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId) {
		setFieldValue(SELECTED_ROW_INDEX, (getValueFromTableCell(ACC_RELATION, selectedRowsArray[0], 0)-1));
		var iSelectedRow = getValue(SELECTED_ROW_INDEX);
		if(iSelectedRow != '') {
			var sName = getValueFromTableCell(ACC_RELATION, iSelectedRow, 1);
			var sDOB = getValueFromTableCell(ACC_RELATION, iSelectedRow, 5);
			var sCustId = getValueFromTableCell(ACC_RELATION, iSelectedRow, 2);
			var controlNames = ['TXT_CUSTOMERNAME','TXT_DOB',TXT_CUSTOMERID];
			var controlValues = [sName,sDOB,sCustId];
			setMultipleFieldValues(controlNames,controlValues);
		}
		return false;
	}
}