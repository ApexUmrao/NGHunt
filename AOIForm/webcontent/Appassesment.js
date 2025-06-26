function onAppassesmentLoad(event) {
	var workstepName = getWorkItemData('activityname');
	var wiName = getWorkItemData('processInstanceId');
	var hideControlNames = [TXT_DOB,TXT_CUSTOMERNAME,P_ECB_CHQ_VALIDATION,P_ECB_REASON];
	hideControls(hideControlNames);
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	var controlNames = [WI_NAME,CURR_WS_DETAIL,'TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID','TXT_CURRWS',CRO_DEC];
	var controlValues = [wiName,workstepName,CustName,CustDob,CustId,workstepName,''];
	setMultipleFieldValues(controlNames,controlValues);
	disableControls(disableControlskyc);
	disableControls(disableEmployeeAddress);
//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	disableControls(familyBanking);
}

function handleAppassesmentEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickAppassesmentEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeAppassesmentEvent(event);
	}
}

function clickAppassesmentEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);

	if (event.target.id == VIEW){
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == EDIT3) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_DOC) {
		//executeServerEvent(event.target.id, event.type, '', false);
		var wi_name = getWorkItemData('processInstanceId');
		setTabStyle("tab4",19, "visible", "true");
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var jspURL = sLocationOrigin+'/AO/CustomFolder/?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
		alert(jspURL);
		document.getElementById('PRODUCT_JSP').src=jspURL;
		selectSheet('tab4',19);
	} else if(event.target.id == BUTTON_SUBMIT ) {
		console.log('Inside Submit button');
		//setValues({CRO_DEC:"Approve"},true);
		setFieldValue(CRO_DEC,'Approve');
		var decision = getValue(CRO_DEC);
		//setValues({CRO_DEC:"Approve"},true);
		setFieldValue(CRO_DEC,'Approve');
		if(decision == 'Reject') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
			}
			else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
			}
		} else {
			executeServerEvent(event.target.id, event.type, '', false);
		}
	}
}

function changeAppassesmentEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(event.target.id == CRO_DEC) {
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

function postServerEventHandlerAppAssessment(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	if (null != jsondata.message && '' != jsondata.message) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			showMessage(arr[0], arr[1], 'error');
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		break;
	case EVENT_TYPE.CLICK:
		if(BUTTON_SUBMIT==controlName){
			console.log('response data incoming:');
			if(jsondata.success) {
				if(WORKSTEPS.APP_ASSESSMENT == workstepName && jsondata.message == '') {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('postSubmit', event.type, '', false);
							saveWorkItem();
							completeWorkItem();
						} else if(result == false) {
							saveWorkItem();
							return;
						}
					});
				}
			}
		} else if('saveNextTabClick' == controlName && jsondata.success) {
//			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if('afterSaveNext' == controlName) {
			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if(controlName == EDIT3){
			saveWorkItem();
			completeWorkItem();
		}
		break;
	}
}

function onClickTabAppAssessment(tabId,sheetIndex,eventCall){
	var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
	var iSelectedRow = selectedRows[0];  
	var input = event.target.innerHTML+','+sheetIndex+','+iSelectedRow;
	var response;
	console.log('tabId:::'+tabId);
	console.log('input onClickTabAppAssessment: ' + input);
	response = executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, false);
	var jsonData = handleAOResponse(response);
	console.log(jsonData);//sharanend
	console.log('sheetIndex='+sheetIndex+'eventCall='+eventCall);	
}

function saveAndNextPreHookAppAssessment(tabid){
	console.log('tabid saveAndNextPreHookDDE: ' + tabid);
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookDDE: ' + input);
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function prehookSaveTabAppAssessment(tabId){
	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	console.log('input prehookSaveTabAppAssessment: ' + input);
	console.log('inside prehookSaveTabAppAssessment, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
	var iSelectedRow = selectedRows[0];  
	executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, sheetIndex+','+iSelectedRow, false);
}