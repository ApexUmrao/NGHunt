function onFBCheckerLoad() {
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	var fields = ['CID_HOF','BTN_FETCH_FAMILY','LVW_FAMILY_MEMBERS'];
	disableControls(fields);
	setTabStyle("tab2", 0, "disable", "true");
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

function handleFBCheckerEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickFBCheckerEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeFBCheckerEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusFBCheckerEvent(event);
	}
}

function clickFBCheckerEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	if(event.target.id == BUTTON_SUBMIT) {		
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'ctrl2') {	
		executeServerEvent(event.target.id, event.type, '', false);
	}
}

function changeFBCheckerEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	if(event.target.id == 'ctrl1') {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'ctrl2') {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'CRO_DEC'){
		onFBDecisionLoadChange();
	}
}

function lostFocusFBCheckerEvent(event){
	
}

function postServerEventHandlerFBChecker(controlName, eventType, responseData) {
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
			if(controlName == BUTTON_SUBMIT && jsondata.success && '' == jsondata.message) {
				saveWorkItem();
				completeWorkItem();
			} else if((getValue(CRO_DEC) == 'Reject') && '' == jsondata.message){
				saveWorkItem();
				completeWorkItem();
			}
			break;
	}
}

function onClickTabFBChecker(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	if(eventCall == 2) {
		executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	}
}

function saveAndNextPreHookFBChecker(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookFBChecker: ' + input);
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