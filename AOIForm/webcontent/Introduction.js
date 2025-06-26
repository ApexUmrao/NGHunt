function onIntroductionLoad() {
	console.log('***** inside onFormLoad *****');
	var workstepName = getWorkItemData('activityName');
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	disableControls('SOURCING_CENTER,ACC_HOME_BRANCH,LODGEMENT_NO');
	/*setValues({['REQUEST_TYPE']: 'New Account'}, true);
	setValues({['ACC_OWN_TYPE']: 'Single'}, true);
	setValues({['TXT_CURR_WS']: workstepName}, true);
	setValues({['WI_NAME']: wi_name}, true); */
	
	if(getValue('IS_INITIATED_UAE_PASS') != 'Y'){
		var controlNames = ['REQUEST_TYPE','ACC_OWN_TYPE'];
		var controlValues = ['New Account','Single'];
		setMultipleFieldValues(controlNames,controlValues);

	}
	var controlNames = ['TXT_CURR_WS','WI_NAME'];
	var controlValues = [workstepName,wi_name];
	setMultipleFieldValues(controlNames,controlValues); 
	
}
function handleIntroEvent(object, event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	var workstepName = getWorkItemData('activityName');
	if(workstepName == WORKSTEPS.INTRODUCTION ){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickEventIntro(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeEventIntro(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {	
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
		} else if (event.type == EVENT_TYPE.LOAD) {
		}
	} 
}
function clickEventIntro(event) {
	console.log('Click Event of Intro Started for '+event.target.id);
	if(event.target.id == 'BTN_SAVE_NEXT'){
		executeServerEvent(event.target.id, event.type, '', false);
	} 
}
function changeEventIntro(event) {
	console.log('Change Event of Intro Started for '+event.target.id);
	if(event.target.id == 'SOURCING_CHANNEL'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'DATA_ENTRY_MODE'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'REQUEST_TYPE'){
		executeServerEvent(event.target.id, event.type, '', false);
	}
}
function postServerEventHandlerIntro(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		var mssg = jsonData.message;
		if ("" != jsonData.message && null != jsonData.message) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
					showMessage(arr[0], arr[1], 'error');
			}
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		if(getValue('SOURCING_CHANNEL').equals('Branch')) {
			setFocus('REQUEST_TYPE');
		} else {
			setFocus('SOURCING_CHANNEL');
		}
	break;
	case EVENT_TYPE.CLICK:
		if(controlName == 'BTN_SAVE_NEXT'){
			if(jsonData.success) {
				if(jsonData.message != '') {
					showMessage('', jsonData.message, 'error');
				}
				saveWorkItem();
				completeWorkItem();
			}
		} 
	break;
	}
}