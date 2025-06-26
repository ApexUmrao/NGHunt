function onLoadQueryWorkStep(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	console.log('iProcessedCustomer'+iProcessedCustomer);
	var controlNames = ['TXT_CURRWS','TXT_CUSTOMERID']; 
	var controlValues = [workstepName,CustId];
	setMultipleFieldValues(controlNames,controlValues);
	if(workstepName == WORKSTEPS.CPD_MAKER || workstepName == WORKSTEPS.BULK_EOD_CHECKER) {
		setTabStyle("tab5",6, "visible", "false");
		setTabStyle("tab5",8, "visible", "false"); 
	}
	if(getValue(REQUEST_TYPE) == 'New Account') {
	    setTabStyle("tab5",17, "visible", "false");
	} 
	disableControls(familyBanking);
	setKYCFlagPrivateclient();
	pepAssesment();
	openTrsdJsp();
	openArchivalWi();  //ADDDED FOR ARCHIVAL 10-11-2023
}



function handleQueryWorkStepEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventQueryWorkStep(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventQueryWorkStep(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusQueryWorkStepEvent(event);
	}
}

function changeEventQueryWorkStep(event){}

function lostFocusQueryWorkStepEvent(event){
	var input = event.target.innerHTML+','+sheetIndex;
}

function clickEventQueryWorkStep(event){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(controlName == VIEW ){
		executeServerEvent(controlName, event.type, '', false);
	}
}

function onClickTabQueryWorkStep(tabId,sheetIndex,eventCall){
	console.log('onClickTabQueryWorkStep');
	console.log(sheetIndex);
	var input = event.target.innerHTML+','+sheetIndex;
	executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, false);
}

function postServerEventHandlerQueryWorkStep(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ("" != jsonData.message && null != jsonData.message) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
					showMessage(arr[0], arr[1], 'error');
			} else if(jsonData.message.indexOf('The user is not authorized to access Staff Data.') != -1){
				showMessage('', jsonData.message, 'error');
			}
		}
			
		}
		
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		break;
	case EVENT_TYPE.CLICK:
		if(jsonData.success){
			if(VIEW == controlName && "" != jsonData.message) {
				console.log('jsonData.message'+jsonData.message);
				var selecedRow = jsonData.message;
				selectRowHook(LVW_DEDUPE_RESULT,selecedRow);
			}
			openTrsdJsp();
		}
		break;
	case EVENT_TYPE.CHANGE:
		break;
	}
}

function saveAndNextPreHookQuery(tabid){
	saveNextClick = true;
	var input = event.target.innerHTML+','+(getSheetIndex(tabid)+1);
	var tab = getSheetIndex(tabid);
	saveWorkItem();
	return true; 
}


function prehookSaveTabQueryWorkStep(tabId){
	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	console.log('input prehookSaveTabQueryWorkStep: ' + input);
	console.log('inside prehookSaveTabQueryWorkStep, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, sheetIndex, false);
}

function onRowClickQueryWorkStep(listviewId,rowIndex) {
	if(listviewId == PRODUCT_QUEUE) {
		return true;
	} else {
		return false;
	}
}