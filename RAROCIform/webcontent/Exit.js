function onExitLoad() {
	var workstepName = getWorkItemData('ActivityName');
	var wiName = getWorkItemData('processInstanceId');
	
}

function handleExitEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickExitEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeExitEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusCommonPoolEvent(event);
	}
}

function clickExitEvent(event){
	var workstepName = getWorkItemData('ActivityName');
	var wiName = getWorkItemData('processInstanceId');

}

function changeExitEvent(event){
	var workstepName = getWorkItemData('ActivityName');
	var wiName = getWorkItemData('processInstanceId');
}

function lostFocusExitEvent(event){
	
}

function postServerEventHandlerExit(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('ActivityName');
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
		/*	if(controlName == BUTTON_SUBMIT && jsondata.success) {
				saveWorkItem();
				completeWorkItem();
			} else if(controlName == BTN_FETCH_FAMILY) {	
				saveWorkItem();
			} */
			break;
	}
}

function onClickTabCExit(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	if(eventCall == 2) {
		executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	}
}

function saveAndNextPreHookExit(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
/*	console.log('input saveAndNextPreHookFBMaker: ' + input);
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('save and next response:: '+response);
	if(response != undefined && response != ''){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, input, false);
	return true;*/ 
}