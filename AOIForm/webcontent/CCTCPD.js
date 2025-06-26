function onCPDFormLoad(){
	var workstepName = getWorkItemData('activityName');
	var wi_name  = getWorkItemData('processInstanceId');
	console.log('inside onCCTFormLoad : ');
	var controlNames = [TXT_CURRWS,SELECTED_ROW_INDEX,CURR_WS_DETAIL,CRO_DEC,CRO_REMARKS,CRO_REJ_REASON];
	var controlValues = [workstepName,'0',workstepName,'','',''];
	setMultipleFieldValues(controlNames,controlValues);
	secAccRelCPDDisable();
	disableCustInfo();
	console.log('WorkItem Name:::: ' + wi_name);
	disableControls(disableControlskyc);
	disableControls(disableEmployeeAddress);
	setTabStyle('tab5',11, PROPERTY_NAME.DISABLE, 'true');
	var sName = getValueFromTableCell(ACC_RELATION, 0, 1);
	var sDOB = getValueFromTableCell(ACC_RELATION, 0, 5);
	var sCustId = getValueFromTableCell(ACC_RELATION, 0, 2);
	var controlNames = ['TXT_CUSTOMERNAME','TXT_DOB',TXT_CUSTOMERID];
	var controlValues = [sName,sDOB,sCustId];
	setMultipleFieldValues(controlNames,controlValues);
//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	disableControls(familyBanking);	// Changes for Family Banking 
}

function handleCPDEvent(event){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEPS.CONTACT_CENTER_CPD){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickCPDEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeCPDEvent(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
			lostFocusCPDEvent(event);		
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
			gotFocusCPDEvent(event);
		} else if (event.type == EVENT_TYPE.LOAD) {
			loadCPDEvent(event);
		}
	} 
}

function loadCPDEvent(event){
	console.log('inside loadCCTEvent >>');
}

function  clickCPDEvent(event){
	console.log(event);
	//var pControlName = event.target.id;
	//var pControlValue = getValue(pControlName);
	//console.log('Click Event of CCT Started for '+pControlName);
	if(event.target.id == BUTTON_SUBMIT) {
		console.log('Inside Submit button');
		saveWorkItem();
		var decision = getValue(CRO_DEC);
		if(decision == ''){
			showMessage(CRO_DEC, 'Please select user decision', 'error');
		} else if(decision != 'Approve') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
				return;
			}
			else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
				return;
			}
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
		} else {
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

function changeCPDEvent(event){
	//console.log('Change Event of CCT Started for '+pControlName);
	//var pControlName = event.target.id;
	//var pControlValue = getValue(pControlName);
	if(event.target.id == CRO_DEC) {
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

function lostFocusCPDEvent(event){
	console.log('inside lostFocusCCTEvent >>');
}

function gotFocusCPDEvent(){
	console.log('inside gotFocusCCTEvent >>');
}

function postServerEventHandlerCPD(controlName, eventType, responseData) {
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
//			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if('afterSaveNext' == controlName) {
			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} /*else if(BUTTON_SUBMIT==controlName){
			if(jsondata.success) {
				if(WORKSTEPS.CONTACT_CENTER_CPD == workstepName && jsondata.message == '') {
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

function onRowClickCPD(listviewId,rowIndex) {
}

function saveAndNextPreHookCCTCPD(tabId){
	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	console.log('input saveAndNextPreHookCCTCPD: ' + input);
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

function onClickTabCPD(tabId,sheetIndex,eventCall){
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
//	if(sheetIndex == 18){
	if(sheetIndex == 19){	// Changes for Family Banking 
		var disableDecisionFields = [IS_CALL_BACK_REQ,IS_COMPLIANCE_NAME_SCR,IS_COMPLIANCE_RISK_ASSESS,IS_PROD_APP_REQ];
		var disableDecisionFields1 =[L4_APP_REQ,L3_APP_REQ,L1_APP_REQ,L2_APP_REQ];
		console.log('tab decision clicked');
		disableControls(disableDecisionFields);
		hideControls(disableDecisionFields1);
		disableControls(CCT_CHECK_BOX_DISABLE);
//		response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	}
	executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
}

function selectRowHookCCTCPD(tableId,selectedRowsArray,isAllRowsSelected) {
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