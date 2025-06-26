function onLoadDeliveryMaker(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	setStyle(SEC_DECISION_LAST,'visible','true');
	setStyle(DELIVERY_DATE,'visible','true');
	setStyle(BTN_SUBMIT,'disable','true');
	setStyle(SEC_ACC_INFO_AOR_MAKER,'disable','true')
	var controlNames = [CURR_WS_NAME,TXT_CURRWS,SELECTED_ROW_INDEX,CURR_WS_DETAIL,WI_NAME,CRO_REMARKS,CRO_DEC];
	var controlValues = [workstepName,workstepName,'0',workstepName,wi_name,'',''];
	setMultipleFieldValues(controlNames,controlValues);
	setStyle(STAFF_ID,PROPERTY_NAME.DISABLE,'true');
	var iRows = getGridRowCount(FRAME58_LVW);
	lockWelcomeLetterRows();
	for(var j=0;j<iRows;j++) {
		if(getValueFromTableCell(FRAME58_LVW,j,3) != 'Y') {
			setCellDisabled(FRAME58_LVW, j,5,'false');
		}
	}
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	disableControls(familyBanking);// Changes for Family Banking
//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
}

function handleDeliveryMakerEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventDeliveryMaker(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventDeliveryMaker(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusDeliveryMakerEvent(event);
	}
}

function clickEventDeliveryMaker(event){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(event.target.id == BUTTON_SUBMIT ) {
		console.log('Inside Submit button');
		saveWorkItem();
		var decision = getValue(CRO_DEC);
		if(decision == ''){
			showMessage(CRO_DEC, 'Please select user decision', 'error');
		} else if(decision != 'Delivery is Successful') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
			}
			else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
			}
		} else {
				console.log('inside button submit');
				var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
				var resultFATCACheck = checkAttchedDocument('FATCA#');
				console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
				executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
		        //executeServerEvent(event.target.id, event.type, '', false);
		}
	} else if (event.target.id == 'GENERATETEMP' || event.target.id == BUTTON_REFRESH
			|| event.target.id == BUTTON_RETRY){
		var workstepName = getWorkItemData('activityName');
		console.log('inside grid button');
			if(WORKSTEPS.DELIVERY_MAKER == workstepName) {
				showConfirmDialog('Do you really want to generate Documents.', confirmDialogButtons, function(result) {
					if(result == true) {
						enableIndicator();
						saveWorkItem();
						executeServerEvent(event.target.id,EVENT_TYPE.CLICK, 'true', false);
					} else if(result == false) {
						showMessage('', 'Some issue in Generating Templates', 'error');
					}
				});
			}
	
		executeServerEvent(event.target.id, event.type, '', false);
	}
}

function changeEventDeliveryMaker(event){
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
			if(decision == 'Delivery is Successful') {
				//setValue(CRO_REJ_REASON,"");
				setFieldValue(CRO_REJ_REASON,'');
				setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'true');
				setStyle(DELIVERY_DATE,PROPERTY_NAME.DISABLE,'false');
				setStyle(STAFF_ID,PROPERTY_NAME.DISABLE,'false');					
			} else {
				setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'false');
				setStyle(DELIVERY_DATE,PROPERTY_NAME.DISABLE,'true');
				setStyle(STAFF_ID,PROPERTY_NAME.DISABLE,'true');	
				//setValue(DELIVERY_DATE,"");
				//setValue(STAFF_ID,"");
				setFieldValue(DELIVERY_DATE,'');
				setFieldValue(STAFF_ID,'');
			}
		}
}


function lostFocusDeliveryMakerEvent(event) {  
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
}	

function saveAndNextPreHookDeliveryMaker(tabid){
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

function onClickTabDeliveryMaker(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	var response;
	console.log('tabId:::'+tabId);
	if(sheetIndex == 1 || sheetIndex == 2 || sheetIndex == 3 || sheetIndex == 4 || sheetIndex == 5) {
		setManualChecksBlank();
		response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
		var jsonData = handleAOResponse(response);
		console.log(jsonData);
	} else {
	response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	var jsonData = handleAOResponse(response);
	console.log(jsonData);//sharanend
	console.log('sheetIndex='+sheetIndex+'eventCall='+eventCall);
	}
}



function postServerEventHandlerDeliveryMaker(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
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
		} else if(BUTTON_SUBMIT==controlName){
			if(jsonData.success) {
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
	}
}
				
function lockWelcomeLetterRows() {
	console.log("into LockWelcomeLetterRows ");
	var iRows = getGridRowCount(FRAME58_LVW);
	for(var j=0;j<iRows;j++) {				
		if(!(getValueFromTableCell(FRAME58_LVW,j,4).equalsIgnoreCase('Other_Forms'))) {
			setCellDisabled(FRAME58_LVW, j,0,'true');
			setCellDisabled(FRAME58_LVW, j,1,'true');
			setCellDisabled(FRAME58_LVW, j,2,'true');
			setCellDisabled(FRAME58_LVW, j,3,'true');
			setCellDisabled(FRAME58_LVW, j,4,'true');
			setCellDisabled(FRAME58_LVW, j,5,'true');
		} else {
			setCellDisabled(FRAME58_LVW, j,0,'true');
			setCellDisabled(FRAME58_LVW, j,1,'true');
			setCellDisabled(FRAME58_LVW, j,2,'true');
			setCellDisabled(FRAME58_LVW, j,3,'true');
			if(getValueFromTableCell(FRAME58_LVW,j,3) != 'Y')
			{
				setCellDisabled(FRAME58_LVW, j,5,'false');
			}
			}
		}

	}