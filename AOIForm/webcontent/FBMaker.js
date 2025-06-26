function onFBMakerLoad() {
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	setFieldValue(CURR_WS_DETAIL, workstepName);
	setFieldValue(CRO_DEC, 'Approve');
	onFBDecisionLoadChange();
	var count = getGridRowCount('LVW_FAMILY_MEMBERS');
	for(var i=0; i<count; i++) {
		if(getValueFromTableCell('LVW_FAMILY_MEMBERS', i, 8) == 'D') {
			setRowColorInListView('LVW_FAMILY_MEMBERS', i, 'FF5050');
		} else if(getValueFromTableCell('LVW_FAMILY_MEMBERS', i, 8) == 'M') {
			setRowColorInListView('LVW_FAMILY_MEMBERS', i, 'FFFF66');
		}
	}
	var section = ['FB_SECTION','SEC_DEC'];//DCSN_LVW_SECTION
	lockAOSection(section);
}

function handleFBMakerEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickFBMakerEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeFBMakerEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusFBMakerEvent(event);
	}
}

function clickFBMakerEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	if(event.target.id == BTN_FETCH_FAMILY) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_SUBMIT) {
		if(getValue('CID_HOF') == '') {
			showMessage('CID_HOF', 'Please enter valid CID', 'error');
		} else if(getGridRowCount('LVW_FAMILY_MEMBERS') == 0) {
			showMessage('', 'Please enter family details', 'error');
			return;
		} else {
			var formCount = DocTypeAttachedcount('Family_Banking_Form#');
			executeServerEvent(event.target.id, event.type, formCount, false);
		}
	} else if(event.target.id == BTN_SAVE) {	
		saveWorkItem();
	}
}

function changeFBMakerEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	if(event.target.id == 'table5_cid') {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'table5_relation') {	
		var rltn = getValue('table5_relation');
		var count = getGridRowCount('LVW_FAMILY_MEMBERS');
		if(rltn == 'HEAD OF FAMILY') {
			for(var i=0; i<count; i++) {
				if(getValueFromTableCell('LVW_FAMILY_MEMBERS', i, 6) == 'HEAD OF FAMILY') {
					setFieldValue('table5_relation','');
					showMessage('', 'Head of family already exists', 'error');
				}
			}
		}
	} else if(event.target.id == 'CRO_DEC'){
		onFBDecisionLoadChange();
	}
}

function lostFocusFBMakerEvent(event){
	
}

function postServerEventHandlerFBMaker(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('control name::: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	disableIndicator();
	if (null != jsondata.message && '' != jsondata.message) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			if(arr.length >=2 && '' != arr[1]) {
				showMessage(arr[0], arr[1], 'error');
			} else if('' != arr[0]) {
				setFocus(arr[0]);
			}
		}
	}
	switch (eventType) {
		case EVENT_TYPE.LOAD:
			break;
		case EVENT_TYPE.CLICK:
			if(controlName == BUTTON_SUBMIT && jsondata.success) {
				saveWorkItem();
				completeWorkItem();
			} else if(controlName == BTN_FETCH_FAMILY) {	
				saveWorkItem();
			} 
			break;
	}
}

function onClickTabFBMaker(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	if(eventCall == 2) {
		executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	}
}

function saveAndNextPreHookFBMaker(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookFBMaker: ' + input);
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('save and next response:: '+response);
	if(response != undefined && response != ''){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, input, false);
	return true; 
}

function onFDLoad() {
	var workstepName = getWorkItemData('activityName');
	if(workstepName == WORKSTEPS.FB_MAKER) {
		if(getValue('table5_newExisting') == 'Existing') {
			var fields = ['table5_newExisting','table5_sno','table5_customerCategory','table5_dob','table5_name',
			              'table5_validationResult','table5_cid','table5_status'];
			disableControls(fields);
			if(getValue('table5_status') != 'D') {
				setStyle('table5_revertDeletion', PROPERTY_NAME.DISABLE, 'true');
			} else {
				setFieldValue('table5_revertDeletion', false);
			}
		} else {
			if(getValue('table5_newExisting') == ''){
				var count = getGridRowCount('LVW_FAMILY_MEMBERS')+1;
				setFieldValue('table5_newExisting','New');
				setFieldValue('table5_sno',count);
				setFieldValue('table5_status','A');
			}			
			var fields = ['table5_newExisting','table5_sno','table5_customerCategory','table5_dob','table5_name',
			              'table5_validationResult','table5_status','table5_revertDeletion'];
			disableControls(fields);
		}
	}		
}