var readOnly = false;
var saveNextClick = false;
var OrginalSheetIndex = 0;
function onCheckerHistoryLoad(){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	var controlnames = [CRO_REMARKS,WI_NAME,CURR_WS_DETAIL,'TXT_CURRWS'];
	var controlvalues = ['', wiName,workstepName,workstepName];
	setMultipleFieldValues(controlnames, controlvalues);
	var disableControlsList = [SEC_PERSONAL_DET,RELIGION,MARITAL_STATUS,SEC_ACC_REL,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,
	                           L4_APP_REQ];
	disableControls(disableControlsList);
	var hideControlsList = [CURR_WS_DETAIL];
	hideControls(hideControlsList);
	var sheetIndices = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15];
	for(var i=0; i<sheetIndices.length; i++){
		setTabStyle("tab3", sheetIndices[i], PROPERTY_NAME.DISABLE, 'true');
	}
	setTabVisible();
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	disableControls(familyBanking);	// Changes for Family Banking
}

function handleCPDBulkEODHistoryEvent(event){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEPS.BULK_EOD_CHECKER){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickCPDBulkEODHistoryEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeCPDBulkEODHistoryEvent(event);
		} 
	} 
}

function changeCPDBulkEODHistoryEvent(event){
	if(event.target.id == CRO_DEC) {
		console.log('inside decision');
		var decision = getValue(CRO_DEC);
		if(decision == 'Approve' || decision == ''){
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'true');
		} else if(decision != 'Approve'){
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'false');
		}
	} else if (event.target.id == 'table94_mode_of_funding') { 
		var iRows = getGridRowCount(PRODUCT_QUEUE);
		if(iRows>0) {
			var sMode = getValueFromTableCell(PRODUCT_QUEUE,0,8);
			if(sMode == "Transfer - Internal") {
				setCellDisabled(PRODUCT_QUEUE,0,9,'true');
				setCellDisabled(PRODUCT_QUEUE,0,13,'true');
				setCellDisabled(PRODUCT_QUEUE,0,7,'false');
				clearTableCellCombo(PRODUCT_QUEUE,0,9);
				executeServerEvent(controlName, event.type,'',false);
			} else {
				setCellDisabled(PRODUCT_QUEUE,0,7,'true');
				setCellDisabled(PRODUCT_QUEUE,0,9,'false');
				setCellDisabled(PRODUCT_QUEUE,0,13,'false');
			}
		} 
	}  else if(event.target.id == CP_CITY || event.target.id == RA_CITY || event.target.id == PA_CITY ) {
		executeServerEvent(event.target.id, event.type, '', false);
	}
}

function clickCPDBulkEODHistoryEvent(event){
	 if(event.target.id == VIEW ) {
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(controlName, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(controlName, event.type, '', false);
		}
	} else if(event.target.id == CP_CITY || event.target.id == RA_CITY || event.target.id == PA_CITY ) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_SUBMIT ) {
		console.log('Inside Submit button');
		var decision = getValue(CRO_DEC);
		if(decision == ''){
			showMessage(CRO_DEC, 'Please select user decision', 'error');
			return;
		} else if(decision != 'Approve') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
				return;
			} else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
				return;
			}
			
		}
		showConfirmDialog(CA008, confirmDialogButtons, function(result) {
			if(result == true) {
				executeServerEvent(BUTTON_SUBMIT, event.type, '', false);
			} else if(result == false) {
				saveWorkItem();
				return;
			}
		});
	}
}

function onClickTabCheckerHistory(tabId,sheetIndex,eventCall){
	console.log('onClickTabCheckerHistory');
	console.log(sheetIndex);
	var input = event.target.innerHTML+','+sheetIndex;
	OrginalSheetIndex = sheetIndex;
	console.log(OrginalSheetIndex);
	//if(OrginalSheetIndex == 18) {
	if(OrginalSheetIndex == 19) { // Changes for Family Banking
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
	} else {
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
	}
	if(!readOnly) {
		executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
		selectSheet(tabId,sheetIndex);
	}
	
}

function saveAndNextPreHookCheckerHistory(tabId){
	var input = event.target.innerHTML+','+(getSheetIndex(tabId));
	OrginalSheetIndex = getSheetIndex(tabId);
	console.log(OrginalSheetIndex);
	if(OrginalSheetIndex == 16) {
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
	} else {
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
	}
	console.log('input saveAndNextPreHookCheckerHistory: ' + input);
	if(!readOnly) {
		saveNextClick = true;
		var response = executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, input, true);
		if(response != '' && response != undefined){
			var jsondata = handleAOResponse(response);
			if (!jsondata.success){
				return false;
			}
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function postServerEventHandlerCheckerHistory(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	if (null != jsondata.message && '' != jsondata.message) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			if('' != arr[1] && arr[1] != CA008) {
				showMessage(arr[0], arr[1], 'error');
			} else if('' != arr[0]) {
				setFocus(arr[0]);
			}
		}
	}
	//if(OrginalSheetIndex == 18 || OrginalSheetIndex == 16) {
	if(OrginalSheetIndex == 19 || OrginalSheetIndex == 16) { // Changes for Family Banking
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
	} else {
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		setStyle(ACC_RELATION,'disable','false');
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		}
		if(decision.equalsIgnoreCase("Permanent Reject - Discard") 
				|| sCompDec.equalsIgnoreCase("Negative Advisory")) {
			readOnly = true;
		}
		break;
	case EVENT_TYPE.CLICK:
		if('saveTabClick' == controlName && saveNextClick) {
			saveNextClick = false;
			/*var sheetLabel = '';
			var sheetIndex = getSheetIndex('tab3');
			if(sheetIndex == 18) {
				sheetLabel = 'Decision';
			} else if(sheetIndex == 12) {
				sheetLabel = 'Delivery Preferences';
			}
			if(getSheetIndex('tab3') == 18) {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			} else {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
			}
			var input = sheetLabel+','+sheetIndex;
			console.log('tabclick input: '+input);
			executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);*/
		} else if('afterSaveNext' == controlName) {
			var sheetLabel = '';
			var sheetIndex = getSheetIndex('tab3');
			//if(sheetIndex == 18) {
			if(sheetIndex == 19) { // Changes for Family Banking
				sheetLabel = 'Decision';
			} else if(sheetIndex == 12) {
				sheetLabel = 'Delivery Preferences';
			}
			//if(getSheetIndex('tab3') == 18) {
			if(getSheetIndex('tab3') == 19) { // Changes for Family Banking
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			} else {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
			}
			var input = sheetLabel+','+sheetIndex;
			console.log('tabclick input: '+input);
			executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
		} else if(controlName == BUTTON_SUBMIT){
			if(jsondata.success) {
				saveWorkItem();
				completeWorkItem();
			} else {
				saveWorkItem();
			}
		} else if(controlName == VIEW){
			if(jsondata.success){
				if('' != jsondata.message) {
					//setFieldValue(LVW_DEDUPE_RESULT+'_'+jsondata.message, TRUE);
					setRowSelectionInListView(LVW_DEDUPE_RESULT,[jsondata.message]);
				}
			}
		}
		break;
	}
}