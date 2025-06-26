function onLoadDeliveryChecker(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	setStyle('DEC_STORAGE_DETAILS','visible','true');
	setStyle('RACK','visible','false');
	
	disableControls(disableControlskyc);
	var controlNames = [TXT_CURRWS,SELECTED_ROW_INDEX,CURR_WS_DETAIL,WI_NAME];
	var controlValues = [workstepName,'0',workstepName,wi_name];
	setMultipleFieldValues(controlNames,controlValues);
	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	disableControls(familyBanking); // Changes for Family Banking
}

function handleDeliveryCheckerEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventDeliveryChecker(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventDeliveryChecker(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusDeliveryCheckerEvent(event);
	}
}
var counter = 'false';
function clickEventDeliveryChecker(event){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(event.target.id == BUTTON_SUBMIT) {
		console.log('Inside Submit button');
		saveWorkItem();
		var decision = getValue(CRO_DEC);
		if(decision == ''){
			showMessage(CRO_DEC, 'Please select user decision', 'error');
		} else if(decision != 'Approved') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
			}
			else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
			}
		}  
			executeServerEvent(event.target.id,event.type,'AfterJSP',false);
	}
}

function changeEventDeliveryChecker(event){
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
		if(decision == 'Approved' || decision == ''){
			console.log("change");
			clearValue(CRO_REJ_REASON);
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'true');
		} else if(decision == 'Return'){
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'false');
		}
	}
}


function lostFocusDeliveryCheckerEvent(event) {  
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
	if(WORKSTEPS.PHYSICAL_RECON == (workstepName)) {	
	}
}	

function saveAndNextPreHookDeliveryChecker(tabid){
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

function onClickTabDeliveryChecker(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	var response;
	console.log('tabId:::'+tabId);
	if(sheetIndex == 1 || sheetIndex == 2 || sheetIndex == 3 || sheetIndex == 4 || sheetIndex == 5) {
//		clearComparisonFields();
//		clearPersonalData();
//		clearKYCData();
//		clearRiskData();
	}
	response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	var jsonData = handleAOResponse(response);
	console.log(jsonData);//sharanend
	console.log('sheetIndex='+sheetIndex+'eventCall='+eventCall);	
}

function showOpenCallJspTabDelivery(workItemName) {
	var workstepName = getWorkItemData('activityName');
	var returnVal="";
	var parameterJSP = workItemName;
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
	var jspURL = sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status.jsp?WI_NAME="+workItemName;
	document.getElementById('PRODUCT_JSP').src = jspURL;
	if (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
		/*setTabStyle('tab4',12, "visible", "true");
		selectSheet('tab4',12);	*/
		setTabStyle('tab4',13, "visible", "true"); // Changes for Family Banking
		selectSheet('tab4',13);	
	} else {
		/*setTabStyle('tab3',19, "visible", "true");
		selectSheet('tab3',19);	*/
		setTabStyle('tab3',20, "visible", "true"); // Changes for Family Banking
		selectSheet('tab3',20);	
	}
}

var clicked = 1;
function postServerEventHandlerDeliveryChecker(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var wiName = getWorkItemData('processInstanceId');
	console.log(jsonData.succes);
	var workstepName = getWorkItemData('activityName');
	setStyle(BTN_CPD_TRSD_CHK,PROPERTY_NAME.DISABLE,'true');
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
		} else if(BUTTON_SUBMIT == controlName){
			if(jsonData.success) {
				if(jsonData.message == CA008) {
					if(clicked == 1) {
						clicked++;
						showOpenCallJspTabDelivery(wiName);
					} else {
						showConfirmDialog(CA008, confirmDialogButtons, function(result) {
							if(result == true) {
								executeServerEvent('postSubmit', event.type, '', false);
							} else if(result == false) {
								saveWorkItem();
								return;
							}
						});
					}
				} 
			} else if(jsonData.message == 'openJSP') {
				showConfirmDialog(CA008, confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent('postSubmit', event.type, '', false);
					} else if(result == false) {
						saveWorkItem();
						return;
					}
				});
			}
		} else if ('postSubmit' == controlName){
			if(jsonData.success && jsonData.message == '') {
				saveWorkItem();
				completeWorkItem();
			} else {
				saveWorkItem();
			}
		}
		break;
	case 'handlingJSPData':
		if(BUTTON_SUBMIT==controlName && jsonData.success && jsonData.message == CA008) {
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
		break;
	}
}