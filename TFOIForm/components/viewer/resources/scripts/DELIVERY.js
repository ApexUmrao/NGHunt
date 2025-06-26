var FRAME_DELIVERY_FINAL_DECISION = 'FRAME_DELIVERY_FINAL_DECISION';

function onDeliveryFormLoad(){
	setStyle(FRAME_CUST_DETAILS,"lock","true");
	setStyle(FRAME_DELIVERY_FINAL_DECISION,"lock","true");
	loadDeliveryTabValidation();
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PT_UTILITY_FLAG) == 'Y'){
		setStyle('DELIVERY_BRANCH', PROPERTY_NAME.DISABLE, 'true');
	}
}

function handleDeliveryEvent(object, event){
	console.log("Event: " + event.target.id + ", " + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickDeliveryEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeDeliveryEvent(event);
	}
}

function loadDeliveryTabValidation(){
	var currentTabIndex = getSheetIndex("tab1");
	var reqType = getValue(REQUEST_TYPE);
	if (currentTabIndex == 0) {
		setStyle(BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'false');
		setStyle(BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'true');
	} else {
		setStyle(BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'true');
		setStyle(BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'false');
		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
	}
	//Added by shivanshu
//	executeServerEvent('form', EVENT_TYPE.LOAD, '', false);
	
	//if(('BAU'== getValue(PROCESS_TYPE)) || ('PT'== getValue(PROCESS_TYPE))){
		if (('ELCB_AM' == reqType) || ('EC_AM' == reqType) || ('ELCB_BL' == reqType) || ('EC_BL' == reqType)){
				setStyle('DELIVERY_BRANCH', PROPERTY_NAME.VISIBLE, 'false');
				}
	//	}

}

function clickDeliveryEvent(event)  {
	if (event.target.id == BUTTON_NEXT || event.target.id == BUTTON_PREVIOUS){
		deliveryTabNavigation(event.target.id);
	} else if (event.target.id == BUTTON_SUBMIT){
		if(submitDeliveryValidations()){
			setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
			executeServerEvent(event.target.id, event.type, '', false);
		} else {
			saveWorkItem();
		}
	} else if (event.target.id == BUTTON_SAVE){
		saveWorkItem();
	}
}

function changeDeliveryEvent(event)  {
	if (event.target.id == DEC_CODE){
		finalDecisionHandlingDelivery();
	}
}

function deliveryTabNavigation(controlName){
	var currentTabIndex = getSheetIndex("tab1");
	console.log("currentabindex: " + currentTabIndex);
	if (controlName == BUTTON_NEXT) {
		setStyle(BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'true');
		setStyle(BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'false');
		console.log('selecting sheet 1');
		selectSheet('tab1',1);
	} else if (controlName == BUTTON_PREVIOUS) {
		setStyle(BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'false');
		setStyle(BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'true');
		console.log('selecting sheet 0');
		selectSheet('tab1',0);
	}
}

function submitDeliveryValidations(){
	var decision = '', delBranch = '';
	decision = getValue(DEC_CODE);
	var reqType = getValue(REQUEST_TYPE);
	var docPresentRemit = getValue('IS_DOCS_ATTACHED');
	var docPresentAWB = getValue('IS_AWB_DOCS_ATTACHED');
	delBranch = getValue('DELIVERY_BRANCH');
	if(decision == '' || decision == 'undefined'){
		showMessage(DEC_CODE, MESSAGE.ERROR.ALERT_DEC_CODE, 'error');
		return false;
	}else{
		if(getValue(PT_UTILITY_FLAG) != 'Y'){
			if(delBranch == ''){
				showMessage('DELIVERY_BRANCH', MESSAGE.ERROR.ALERT_DELIVERY_BRANCH, 'error');
				return false;
			}
			else
				return true;
		} else {
			return true;
	}
	}
}