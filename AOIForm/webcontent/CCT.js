function onCCTFormLoad(){
	var workstepName = getWorkItemData('activityName');
	var wi_name  = getWorkItemData('processInstanceId');
	console.log('inside onCCTFormLoad : ');
	var controlNames = [TXT_CURRWS,SELECTED_ROW_INDEX,CURR_WS_DETAIL,CRO_DEC,CRO_REMARKS,CRO_REJ_REASON];
	var controlValues = [workstepName,0,workstepName,'','',''];
	setMultipleFieldValues(controlNames,controlValues);
	secAccRelCPDDisable();
	disableCustInfo();
	console.log('WorkItem Name:::: ' + wi_name);
	disableControls(disableControlskyc);
	disableControls(disableEmployeeAddress);
	setTabStyle('tab5',11, PROPERTY_NAME.DISABLE, 'true');
	//executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab5",17, "visible", "false");
	} 	
	disableControls(familyBanking);
}

function handleCCTEvent(event){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEPS.CONTACT_CENTER_TEAM){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickCCTEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeCCTEvent(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
			lostFocusCCTEvent(event);		
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
			gotFocusCCTEvent(event);
		} else if (event.type == EVENT_TYPE.LOAD) {
			loadCCTEvent(event);
		}
	} 
}

function loadCCTEvent(event){
	console.log('inside loadCCTEvent >>');
}

function  clickCCTEvent(event){
	console.log(event);
	//var pControlName = event.target.id;
	//var pControlValue = getValue(pControlName);
	//console.log('Click Event of CCT Started for '+pControlName);
	if(getValue(DATA_ENTRY_MODE)== 'Quick Data Entry' && !getValue(SCAN_MODE) == 'New WMS ID') {
		fillManualDataInBelowFields(pControlName,pControlValue);
		fillFCRDataInBelowFields(pControlName,pControlValue);
		fillEIDADataInBelowFields(pControlName,pControlValue);
		if(event.target.id == MANUAL_DOB && !getValue(MANUAL_DOB) == '' && getValue(CHECKBOX_DOB_MANUAL)== 'True') {
			console.log('In dob Manual----'+getValue(CHECKBOX_DOB_MANUAL));
			var controlNames = PD_DOB;
			var controlValues = getValue(MANUAL_DOB);
			setFieldValue(controlNames,controlValues);
			//setValues({[PD_DOB]: pControlValue}, true);
		} else if(event.target.id == MANUAL_PASSPORT_ISS_DATE && !getValue(MANUAL_PASSPORT_ISS_DATE) == '') {
			setFieldValue(HD_PASS_ISS_DATE,getValue(MANUAL_PASSPORT_ISS_DATE));
			//setValues({[HD_PASS_ISS_DATE]: pControlValue}, true);
		} else if(event.target.id == MANUAL_PASSPORT_EXP_DATE && !getValue(MANUAL_PASSPORT_EXP_DATE) == ''){
			setFieldValue(HD_PASS_EXP_DATE,getValue(MANUAL_PASSPORT_EXP_DATE));
			//setValues({[HD_PASS_EXP_DATE]: pControlValue}, true);
		} else if(event.target.id == MANUAL_VISA_ISSDATE && !getValue(MANUAL_VISA_ISSDATE) == ''){
			setFieldValue(HD_VISA_ISSUE_DATE,getValue(MANUAL_VISA_ISSDATE));
			//setValues({[HD_VISA_ISSUE_DATE]: pControlValue}, true);
		} else if(event.target.id == MANUAL_VISA_EXP_DATE && !getValue(MANUAL_VISA_EXP_DATE) == ''){
			setFieldValue(HD_EXP_DATE,getValue(MANUAL_VISA_EXP_DATE));
			//setValues({[HD_EXP_DATE]: pControlValue}, true);
		}
	} else if(event.target.id == BUTTON_SUBMIT ) {
		console.log('Inside Submit button');
		saveWorkItem();
		var decision = getValue(CRO_DEC);
		if(decision == ''){
			showMessage(CRO_DEC, 'Please select user decision', 'error');
		} /*else if(decision != 'Approve') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
			}
			else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
			}
		}*/ else {
			showConfirmDialog(CA008, confirmDialogButtons, function(result) {
				if(result == true) { 
					executeServerEvent('postSubmit', event.type, '', false);
						saveWorkItem();
						completeWorkItem();
				} else if(result == false) {
					saveWorkItem();
					return;
				}
			});
//			executeServerEvent(event.target.id, event.type, '', false);
		}
	}
}

function changeCCTEvent(event){
	//console.log('Change Event of CCT Started for '+pControlName);
	//var pControlName = event.target.id;
	//var pControlValue = getValue(pControlName);
	if(getValue(DATA_ENTRY_MODE)== 'Quick Data Entry' && !getValue(SCAN_MODE) == 'New WMS ID') {
		fillManualDataInBelowFields(pControlName,pControlValue);
		fillFCRDataInBelowFields(pControlName,pControlValue);
		fillEIDADataInBelowFields(pControlName,pControlValue);
		if(event.target.id == MANUAL_DOB && !getValue(MANUAL_DOB) == '' && getValue(CHECKBOX_DOB_MANUAL)== 'True') {
			console.log('In dob Manual----'+getValue(CHECKBOX_DOB_MANUAL));
			setFieldValue(PD_DOB,getValue(MANUAL_DOB));
			//setValues({[PD_DOB]: pControlValue}, true);
		} else if(event.target.id == MANUAL_PASSPORT_ISS_DATE && !getValue(MANUAL_PASSPORT_ISS_DATE) == '') {
			setFieldValue(HD_PASS_ISS_DATE,getValue(MANUAL_PASSPORT_ISS_DATE));
			//setValues({[HD_PASS_ISS_DATE]: pControlValue}, true);
		} else if(event.target.id == MANUAL_PASSPORT_EXP_DATE && !getValue(MANUAL_PASSPORT_EXP_DATE) == '') {
			setFieldValue(HD_PASS_EXP_DATE,getValue(MANUAL_PASSPORT_EXP_DATE));
			//setValues({[HD_PASS_EXP_DATE]: pControlValue}, true);
		} else if(event.target.id == MANUAL_VISA_ISSDATE && !getValue(MANUAL_VISA_ISSDATE) == '') {
			setFieldValue(HD_VISA_ISSUE_DATE,getValue(MANUAL_PASSPORT_EXP_DATE));
			//setValues({[HD_VISA_ISSUE_DATE]: pControlValue}, true);
		} else if(event.target.id == MANUAL_VISA_EXP_DATE && !getValue(MANUAL_VISA_EXP_DATE) == '') {
			setFieldValue(HD_EXP_DATE,getValue(MANUAL_VISA_EXP_DATE));
			//setValues({[HD_EXP_DATE]: pControlValue}, true);
		}
	} else if(event.target.id == CRO_DEC) {
		console.log('inside decision');
		var decision = getValue(CRO_DEC);
		if(decision == 'Approve' || decision == ''){
			console.log("change");
			clearValue(CRO_REJ_REASON);
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'true');
		} else if(decision == 'Return'){
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'false');
		}
	}
}

function lostFocusCCTEvent(event){
	console.log('inside lostFocusCCTEvent >>');
	if (pControlName == NEW_TRN_AMT) {
		executeServerEvent(pControlName, EVENT_TYPE.LOSTFOCUS, '', false);
	} else if (pControlName == EXP_DATE || pControlName == NEW_EXP_DATE) {
		executeServerEvent(pControlName, EVENT_TYPE.LOSTFOCUS, '', false);
	}
}

function gotFocusCCTEvent(){
	console.log('inside gotFocusCCTEvent >>');
}

function postServerEventHandlerCCT(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	setStyle(BTN_CPD_TRSD_CHK,PROPERTY_NAME.DISABLE,'true');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	if (null != jsondata.message && '' != jsondata.message) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			showMessage(arr[0], arr[1], 'error');
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		setStyle(ACC_RELATION,'disable','false');
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		}
		break;
	case EVENT_TYPE.CLICK:	
		if('saveNextTabClick' == controlName) {
//			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab5'), false);
		} else if('afterSaveNext' == controlName) {
			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab5'), false);
		} /*else if(BUTTON_SUBMIT == controlName){
			if(jsondata.success) {
				if(WORKSTEPS.CONTACT_CENTER_TEAM == workstepName && jsondata.message == '') {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) { 
							executeServerEvent('postSubmit', event.type, '', false);
								saveWorkItem();
								completeWorkItem();
						} else if(result == false) {
							saveWorkItem();
							return;
						}
					});
				}
			}
		}*/
		break;
	case EVENT_TYPE.CHANGE:
		break;
	case EVENT_TYPE.LOSTFOCUS: 				
		break;	
	}
}

function onRowClickCCT(listviewId,rowIndex) {
}

function saveAndNextPreHookCCT(tabid){
	console.log('tabid saveAndNextPreHookDDE: ' + tabid);
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookDDE: ' + input);
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

function onClickTabCCT(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	console.log('tabId:::'+tabId);
	console.log('input onClickTabCCT: ' + input);
	/*if(sheetIndex == 1 || sheetIndex == 2 || sheetIndex == 3 || sheetIndex == 4 || sheetIndex == 5){
		//clearPersonalData();
		//clearKYCData();
		//clearComparisonFields(); 
		//clearRiskData();
		response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	}  else */
	//if(sheetIndex == 18){
	if(sheetIndex == 19){ // Changes for Family Banking
		var disableDecisionFields = [IS_CALL_BACK_REQ,IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ];
		var disableDecisionFields1 =[L4_APP_REQ,L3_APP_REQ,L1_APP_REQ,L2_APP_REQ];
		console.log('tab decision clicked');
		disableControls(disableDecisionFields);
		hideControls(disableDecisionFields1);
		disableControls(CCT_CHECK_BOX_DISABLE);
		enableControls([BUTTON_SUBMIT]);
//		response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	}
	executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
}

function selectRowHookCCT(tableId,selectedRowsArray,isAllRowsSelected) {
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
		return  false;
	}
}