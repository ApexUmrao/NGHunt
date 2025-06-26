function onLoadPhysicalReconciliation(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	onPhyRecLoad();
	setStyle('DEC_STORAGE_DETAILS','visible','true')
	setStyle('RACK','visible','false')
	disableControls(disableControlskyc);
	var controlNames = [TXT_CURRWS,SELECTED_ROW_INDEX,CURR_WS_DETAIL,WI_NAME];
	var controlValues = [workstepName,'0',workstepName,wi_name];
	setMultipleFieldValues(controlNames,controlValues);
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	disableControls(familyBanking);// Changes for Family Banking
//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
}


function onPhyRecLoad(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	//Frame85 
	console.log("inside loadjs");
	var decision = getValue(CRO_DEC);
	var sCompDec = getValue('COMP_DEC');
	//disableCustInfo();
	secAccRelCPDDisable()
	var req_type = getValue(REQUEST_TYPE);
}

function handlePhysicalReconciliationEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventPhysicalReconciliation(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventPhysicalReconciliation(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusPhysicalReconciliationEvent(event);
	}
}

function clickEventPhysicalReconciliation(event){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(event.target.id == BUTTON_SUBMIT ) {
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
			}
			else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
			}
		} else if(decision == 'Approve'){
			executeServerEvent(event.target.id, event.type, '', false);
		}
	}
}

function changeEventPhysicalReconciliation(event){
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (event.target.id == 'table94_mode_of_funding') { 
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


function lostFocusPhysicalReconciliationEvent(event) {  
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
	if(WORKSTEPS.PHYSICAL_RECON == (workstepName)) {	
	}
}	

function saveAndNextPreHookPhysicalReconciliation(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('Input saveAndNextPreHookDDE: ' + input);
	var response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, true);
	console.log('Save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsonData = handleAOResponse(response);
		if (!jsonData.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function onClickTabPhysicalReconciliation(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	var response;
	console.log('tabId:::'+tabId);
	response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	var jsonData = handleAOResponse(response);
	console.log(jsonData);//sharanend
	console.log('sheetIndex='+sheetIndex+'eventCall='+eventCall);	
}

function postServerEventHandlerPhysicalReconciliation(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	console.log(jsonData.succes);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ("" != jsonData.message && null != jsonData.message) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
					showMessage(arr[0], arr[1], 'error');
			}
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		setStyle('acc_relation','disable','false');
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		} 
		saveWorkItem();
		break;
	case EVENT_TYPE.CLICK:
		if('afterSaveNext' == controlName) {
			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if(controlName == BUTTON_SUBMIT){
			console.log('BUTTON_SUBMIT, workstep: '+workstepName+', json message: '+jsonData.success);
			if(jsonData.success) {
				if(WORKSTEPS.PHYSICAL_RECON == workstepName && jsonData.message == '') {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('postSubmit', event.type, '', false);
							saveWorkItem();
							completeWorkItem();
						} else if(result == false) {
							return;
						}
					});
				} 
			} else {
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
			}
		}
		break;
	}
}