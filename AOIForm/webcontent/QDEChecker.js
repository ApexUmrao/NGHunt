//Constant of QDEChecker is   QDE_ACCOUNT_INFO_CHECK
function onQDECheckerLoadJs() {
	setStyle(DOC_APPROVAL_OBTAINED,PROPERTY_NAME.VISIBLE, TRUE);
	setStyle(COURT_ORD_TRADE_LIC,PROPERTY_NAME.VISIBLE, TRUE);
	setStyle(DOC_APPROVAL_OBTAINED,PROPERTY_NAME.DISABLE,FALSE);
	setStyle(COURT_ORD_TRADE_LIC,PROPERTY_NAME.DISABLE,FALSE);
	var workstepName = getWorkItemData('activityName');
	console.log('Event:' +workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	var iProcessedCustomer = 0;
	if(getValue(SELECTED_ROW_INDEX) != "null"){
		iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	}
	var CustName1 = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob1 =  getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId1 =   getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	
	var controlNames1 = ['CURR_WS_DETAIL','TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID','TXT_CURRWS',CRO_REMARKS, 'P_ECB_CHQ_VALIDATION'];
	var controlValues1 = [workstepName,CustName1,CustDob1,CustId1,workstepName,'','true'];
	setMultipleFieldValues(controlNames1,controlValues1);
	
	var DDECheckerFieldsDisable = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ,BUTTON_GENERATE_TEMPLATE];
	disableControls(DDECheckerFieldsDisable);
	disableControls(ALL_CHECKBOX_EIDA);
	disableControls(ALL_CHECKBOX_MANUAL);
	disableControls(ALL_CHECKBOX_FCR);
	
	setStyle(CURR_WS_DETAIL,"visible","false");
	secAccRelCPDDisable();
	setTabVisible();
	pepAssesment();// AO dcra
	setStyle(SEARCH_EIDA_CARDNO, PROPERTY_NAME.DISABLE, 'false');
	if(getValue(CRO_DEC).toLowerCase() == ('approve')) {	// on loading if decision is defaulted approve then rject_reason is enabled
		setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
		var controlnames = [CRO_REJ_REASON];
		clearAOControls(controlnames);
	}
	if(getValue("chq_eligibility") == 'None') {
		var controlNames = [AO_CRO_DEC,AO_CRO_REJ_REASON,AO_CRO_REMARKS];
		var controlValues = ['Return to Originator','Bounced cheques > 3','Bounced cheques are more than 3'];
		setMultipleFieldValues(controlNames,controlValues);
	}
	disableControls(disableControlskyc);
	setStyle(EDIT1, PROPERTY_NAME.DISABLE, 'true');
	//setTabStyle(QDE_CHECKER_TAB_ID,12, "visible", "false");
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab4",11, "visible", "false");
	}
	disableControls(familyBanking);
    openTrsdJsp();
	onLoadDowngradeQDEChq();
	setStyle('KYC_PRE-ASSESSMENT','disable','true');
	visiblePreAssesmentField();//AO CCO AND UPGRADE
	setKYCFlagPrivateclient();//Dcra
	if(!(getValue('SKIPUAEPASS_REASON') == '' || getValue('SKIPUAEPASS_REASON') == null)){
		setStyle('SKIPUAEPASS_REASON','disable', 'false');
	}
}

function clickQDECheckerEventJs(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event:' +workstepName);
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	var actionnames = [];
	var actionvalues = [];
	if(controlName == 'P_ECB_CHQ_VALIDATION'){
		if(!controlValue == '' && getValue('P_ECB_CHQ_VALIDATION')== 'false') {
			enableControls('P_ECB_REASON');
		} else {
			//setValues({"P_ECB_REASON":""},true);
			setFieldValue(P_ECB_REASON,'');
			disableControls('P_ECB_REASON');
		}
	} else if(controlName== BUTTON_SUBMIT){
		console.log('inside button submit');
		var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
		var resultFATCACheck = checkAttchedDocument('FATCA#');
		console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
		executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
        //executeServerEvent(event.target.id, event.type, '', false);
	} else if(pControlName == BTN_ECB_REFRSH) {
		executeServerEvent(controlName, event.type, '', false);  //loadECBChqBookValidation(formObject);
	} else if(controlName == CRO_DEC){
		console.log('controlName1: ' + controlName);
		if(controlName == CRO_DEC && getValue(CRO_DEC) == 'Approve') {
			disableControls(CRO_REJ_REASON);
			lockAOSection(CRO_REJ_REASON);
			//setValues({CRO_REJ_REASON:"Select"},true);
			setFieldValue(CRO_REJ_REASON,'');
			if(getValue(IS_COMPLIANCE_RISK_ASSESS)== 'true'){
				  if(getValue('FINAL_RISK_VAL')== 'Increased Risk'){
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					actionvalues = ['true','true','true','false'];
					setMultipleFieldValues(controlnames, controlvalues);
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					enableControls(controlnames);
				} else if(getValue('FINAL_RISK_VAL')== 'UAE-PEP'){
					actionnames = [L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ];
					actionvalues = ['true','true','true','true'];
					setMultipleFieldValues(controlnames, controlvalues);
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					enableControls(controlnames);
				} else {
					actionnames = [L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ];
					actionvalues = ['false','false','false','false'];
					setMultipleFieldValues(controlnames, controlvalues);
					actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
					disableControls(controlnames);
				}
			}
		} else {
			actionnames = [L1_APP_REQ, L2_APP_REQ, L3_APP_REQ, L4_APP_REQ];
			actionvalues = ['false','false','false','false'];
			setMultipleFieldValues(controlnames, controlvalues);
			actionnames = [L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
			disableControls(controlnames);
			enableControls([CRO_REJ_REASON]);
		}
	} else if(controlValue ==  DOC_APPROVAL_OBTAINED){
		if(getValue(DOC_APPROVAL_OBTAINED)== 'true'){
			//setValues({COURT_ORD_TRADE_LIC:"false"},true);
			setFieldValue(COURT_ORD_TRADE_LIC,'false');
		} else{
			//setValues({COURT_ORD_TRADE_LIC:"true"},true);
			setFieldValue(COURT_ORD_TRADE_LIC,'true');
		}
	} else if(controlValue ==  COURT_ORD_TRADE_LIC){
		if(getValue(DOC_APPROVAL_OBTAINED)== 'true'){
			//setValues({DOC_APPROVAL_OBTAINED:"false"},true);
			setFieldValue(DOC_APPROVAL_OBTAINED,'false');
		} else {
			//setValues({DOC_APPROVAL_OBTAINED:"true"},true);
			setFieldValue(DOC_APPROVAL_OBTAINED,'true');
		}
	} 
	/*else if(controlName == 'BTN_FULFILLMENT_START'){  //changes for fulfillment
		console.log('BTN_FULFILLMENT_START');
		executeServerEvent(event.target.id, event.type,'', false);
	} else if(controlName == 'BTN_FULFILLMENT_REFRESH'){
		console.log('BTN_FULFILLMENT_REFRESH');
		executeServerEvent(event.target.id, event.type,'', false);
	}*/ 
}

function changeQDECheckerEventJs(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event:' +workstepName);
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
	} else if(event.target.id == (CHECKBOX_EMAIL_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EIDANO_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (CHECKBOX_EMAIL_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (CHECKBOX_TELE_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EIDANO_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (CHECKBOX_EMAIL_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (CHECKBOX_TELE_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}
}

function lostFocusQDECheckerEventJs(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event:' +workstepName);
}

function gotFocusQDECheckerEventJs(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event:' +workstepName);
}

function handleQDECheckerEventJs(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event:' +workstepName);
	if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickQDECheckerEventJs(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeQDECheckerEventJs(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
			lostFocusQDECheckerEventJs(event);		
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
			gotFocusQDECheckerEventJs(event);
		}
	} 
}

var counterQDE = false;
function postServerEventHandlerQDECheckerJs(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	if (null != jsondata.message && '' != jsondata.message) {
		if(((jsondata.message).indexOf('###') != -1) && 
				!(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1)){
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
//			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab4'), false);
		} else if('afterSaveNext' == controlName) {
			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab4'), false);
		} else if(controlName == BUTTON_SUBMIT){
			//setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
			console.log('BUTTON_SUBMIT, workstep: '+workstepName+', json message: '+jsondata.message);
			//JSP open
//			if(getValue(CRO_DEC) == 'Approve' /* && counterQDE == false */ && jsondata.success){
//			var result = showOpenCallJspTabQDE(wi_name);
//			//counterQDE = true;
//			}else{
//			//counterQDE = false;
//			}
			if(jsondata.success /*&& counterQDE == false*/ ) {
				var msg = 'Selected passport holder Residents does not meet conditions,\nHence not allowed to open Account. Do you still want to proceed with account opening?';
				if(WORKSTEPS.QDE_ACCOUNT_INFO_CHECK == workstepName && jsondata.message == msg) {
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
				} else if(WORKSTEPS.QDE_ACCOUNT_INFO_CHECK == workstepName && jsondata.message == CA008) {
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
								return;
							}
						});
					}					 
				} else if(!(getValue(CRO_DEC) == '')){
					saveWorkItem();
					completeWorkItem();
				} else {
					saveWorkItem();
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
						return;
					}
				});
			}
		} 
		/*else if (controlName == 'BTN_FULFILLMENT_START' || controlName == 'BTN_FULFILLMENT_REFRESH') {
			// SET ROW COLOR BASED ON STATUS
			setRowColorForFulfullmentGrid();
		}*/
		break;	
	}
}

function onClickTabQDEAccountInfoCheckerJs(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, false);
}

function showOpenCallJspTabQDE(workItemName) {
	var returnVal="";
	var parameterJSP = workItemName;
	var urlDoc = document.URL;
	var jspURL="";
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
	jspURL=sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status.jsp?WI_NAME="+workItemName;
	document.getElementById('sheet63_link').textContent = 'Application Orchestration';
	document.getElementById('PRODUCT_JSP').src = jspURL;
	/*setTabStyle(QDE_CHECKER_TAB_ID,12, "visible", "true");
	selectSheet(QDE_CHECKER_TAB_ID,12);	*/
	setTabStyle(QDE_CHECKER_TAB_ID,13, "visible", "true");
	selectSheet(QDE_CHECKER_TAB_ID,13);	
}

function saveAndNextPreHookQDEChecker(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookQDE: ' + input);
	PepNationality();
	AddSourceIncome();//Dcra
	pepLogic();
	//Issue for data erase
	if(getValue('gender_fcr')=='' && getValue('gender_manual') =='' ){
	showMessage('', 'Please Wait for Loading all data of Customer Information on Customer Info TAB', 'error');
	}
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
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

function customListViewValidationQDEChecker(controlId,flag){
	console.log('Inside customListViewValidationQDE');
	var response = executeServerEvent(controlId, EVENT_TYPE.CLICK, '', false);
	var jsonData = handleAOResponse(response);	
		return true;
} 
function onLoadDowngradeQDEChq() { // changes for downgrade krishna
 var requestType = getValue(REQUEST_TYPE);
 if(requestType == ('Downgrade')){
	 setTabStyle('tab4',2, 'visible', 'false');
	 setTabStyle('tab4',3, 'visible', 'false');
	 setTabStyle('tab4',4, 'visible', 'false'); 
	 setTabStyle('tab4',5, 'visible', 'false');
	 setTabStyle('tab4',6, 'visible', 'false');
	 setTabStyle('tab4',7, 'visible', 'false'); 
	 setTabStyle('tab4',8, 'visible', 'false');
	 setTabStyle('tab4',9, 'visible', 'true');
	 setTabStyle('tab4',10, 'visible', 'false');
	 setTabStyle('tab4',11, 'visible', 'false');
  }
} 