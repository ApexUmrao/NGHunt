function onQDECustAcctInfoFormLoad() {
//console.log('***** inside onFormLoad *****');
//var controlNames = {WI_NAME,CRO_REMARKS,CURR_WS_DETAIL,LABEL_WS_NAME};
//var controlValues = {sWorkitemID,'',sAcitivityName,sAcitivityName};
//setFieldValues(controlNames,controlValues);
//var enableFields = {L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ,SOURCING_CHANNEL,TXT_CUSTOMERNAME,TXT_DOB,TXT_CUSTOMERID,ACC_HOME_BRANCH,SOURCING_CENTER,REQUEST_TYPE,DATA_ENTRY_MODE,FORM_AUTO_GENERATE,ACC_OWN_TYPE,ACC_CLASS,WMS_ID,CURR_WS_DETAIL,LODGEMENT_NO};
//disableControls(enableFields);
//var hideControls = {CURR_WS_DETAIL,DOC_APPROVAL_OBTAINED,COURT_ORD_TRADE_LIC};
//hideControls(hideControls);
//if('true'.equalsIgnoreCase(getValue(CHECKBOX_VISA_STATUS_MANUAL)))
//{
//	setStyle(MANUAL_VISASTATUS, PROPERTY_NAME.DISABLE, 'false')
//}
}			

function handleQDEEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickQDEEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeQDEEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusQDEEvent(event);
	}
}	

function clickQDEEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var controlName  = event.target.id;
	var controlValue = getValue(controlName);
}

function changeQDEEvent(event){
	var workstepName = getWorkItemData('activityName');

}

function lostFocusQDEEvent(event) { 
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
	if(WORKSTEPS.DDE_CUSTOMER_INFO == (workstepName)) {	
		
	}
}