var RM_BUTTON_NEXT = 'btnNext';
var RM_BUTTON_PREVIOUS = 'btnPrevious';
var RM_BUTTON_SUBMIT = 'btnSubmit';
var RM_BUTTON_SAVE = 'btnSave';
var FRAME_CUST_DETAILS ='FRAME_CUST_DETAILS';
var FRAME_REFERRAL_SUMMARY = 'FRAME_REFERRAL_SUMMARY';
var FRAME_RM_FINAL_DECISION = 'FRAME_RM_FINAL_DECISION';

function onRMFormLoad(){
	console.log('locking RM sections..');
	setStyle(FRAME_CUST_DETAILS,"lock","true");
	setStyle(FRAME_REFERRAL_SUMMARY,"lock","true");
	setStyle(FRAME_RM_FINAL_DECISION,"lock","true");
//	setStyle('sheet3_link', PROPERTY_NAME.VISIBLE, 'false');
        listViewPastDue();   // ATP -463 JAMSHED 20-06-2024
	clearReferralDecRemarks();
	showTabCounterParty();
}

function loadTabValidation(){
	var currentTabIndex = getSheetIndex("tab1");
	if (currentTabIndex == 0) {
		setStyle(RM_BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'false');
		setStyle(RM_BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'true');
	} else {
		setStyle(RM_BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'true');
		setStyle(RM_BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'false');
		setStyle(RM_BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
	}
}

function clearReferralDecRemarks(){
	setValues({[TFO_REMARKS]: ''}, true);
	setValues({[DECISION]: ''}, true);
	setValues({[DEC_CODE]: ''}, true);
	saveWorkItem();
}

function handleRMEvent(object, event){
	console.log("Event: " + event.target.id + ", " + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickRMEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeRMEvent(event);
	}
}

function clickRMEvent(event)  {
	if (event.target.id == RM_BUTTON_NEXT || event.target.id == RM_BUTTON_PREVIOUS){
		referralTabNavigation(event.target.id);
	} else if (event.target.id == RM_BUTTON_SUBMIT){
		if(submitReferalValidations()){
			saveWorkItem();
			setStyle(RM_BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
			executeServerEvent(event.target.id, event.type, '', false);
		} else {
			saveWorkItem();
		}
	} else if (event.target.id == RM_BUTTON_SAVE){
		saveWorkItem();
	}
}

function changeRMEvent(event){
	var reqCat = getValue(REQUEST_CATEGORY);
	if (event.target.id == DEC_CODE){
		var decision = getSelectedItemLabel(DEC_CODE);
		setValues({[DECISION]: decision}, true);
		if(('IFCPC' == reqCat ) && (decision == 'Approve')){
			setValue('DISCLAIMER_COUNTER_PARTY','Yes');
		}else if(('IFCPC' == reqCat ) && (decision == 'Reject')){
			setValue('DISCLAIMER_COUNTER_PARTY','No');
		}
	}
}

function submitReferalValidations(){
	var sDecision='',sRefRemarks='';
	sDecision = getValue(DEC_CODE);
	sRefRemarks = getValue(TFO_REMARKS);
	if(sDecision == ''){
		showMessage(DEC_CODE, MESSAGE.ERROR.ALERT_DEC_CODE, 'error');
		return false;
	}else{
		if(sRefRemarks == '' && sDecision!='APP'){
			showMessage(TFO_REMARKS, MESSAGE.ERROR.ALERT_REMARKS, 'error');
			return false;
		}
		else
			return true;				
	}
}

function referralTabNavigation(controlName){
	var currentTabIndex = getSheetIndex("tab1");
	console.log("currentabindex: " + currentTabIndex);
	if (controlName == RM_BUTTON_NEXT) {
		setStyle(RM_BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'true');
		setStyle(RM_BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'false');
		console.log('selecting sheet 1');
		selectSheet('tab1',2);
	} else if (controlName == RM_BUTTON_PREVIOUS) {
		setStyle(RM_BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'false');
		setStyle(RM_BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'true');
		console.log('selecting sheet 0');
		selectSheet('tab1',0);
	}
}



function showTabCounterParty(){
	var reqCat = getValue(REQUEST_CATEGORY);
    var reqType = getValue(REQUEST_TYPE);
	var processType = getValue(PROCESS_TYPE);
	//sRefRemarks = getValue(TFO_REMARKS);
	if(('IFCPC' == reqCat ) && ('SIF' == reqType || 'APPIF' == reqType)){
	//	setStyle('sheet3_link', PROPERTY_NAME.VISIBLE, 'false');
		setTabStyle('tab1',1, "visible", 'true');
		setStyle('DISCLAIMER_COUNTER_PARTY', PROPERTY_NAME.VISIBLE, 'true');
      	}else{	
         setTabStyle('tab1',1, "visible", 'false');
		setStyle('DISCLAIMER_COUNTER_PARTY', PROPERTY_NAME.VISIBLE, 'false');
		}
}