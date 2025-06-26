function onLoadRepair(){
	var workstepName = getWorkItemData('activityName'); 
	var wi_name = getWorkItemData('processInstanceId');
	console.log('***** inside onFormLoad *****');
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	var controlNames = [WI_NAME,CURR_WS_DETAIL,'TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID','TXT_CURRWS'];
	var controlValues = [wi_name,workstepName,CustName,CustDob,CustId,workstepName];
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(getValue('REJECT_DEC') == 'Approve'){
		setStyle(CRO_REJ_REASON,'disable','true');
	}else{
		setStyle(CRO_REJ_REASON,'disable','false');
	}
	executeServerEvent(event.target.id, event.type, '', false);
}

function handleRepairEvent(event){
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventRepair(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventRepair(event);
	}
} 

function clickEventRepair(event){
	var workstepName = getWorkItemData('activityName'); 
	var wi_name = getWorkItemData('processInstanceId');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (controlName == CT_BTN_RESETALL){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (controlName == BUTTON_SUBMIT){
		if((getValue('REJECT_DEC') == '')) {
			showMessage('REJECT_DEC', 'Please select Decision', 'error');
			return;
		} else if((getValue('REJECT_DEC') == 'Reject') && (getValue(CRO_REJ_REASON) == '')) {
			showMessage(CRO_REJ_REASON, 'Please Select Rejection Reason', 'error');
			return;
		} else if((getValue('REJECT_DEC') == 'Reject') && (getValue(CRO_REJ_REASON)=='Others') 
				&& (getValue(CRO_REMARKS) == '') || (getValue(CRO_REMARKS) == null)) {
			showMessage(CRO_REMARKS, 'Please fill Remarks','error');
			return;
		} else if(controlName == CRO_REJ_REASON) {
			if(getValue('REJECT_DEC') == 'Reject' && controlValue == 'Others') {
				setStyle(CRO_REJ_REASON,'enable','true');
			}
		}
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (controlName == 'BTN_REPAIR_RETRY'){
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var jspURL = sLocationOrigin+'/AO/CustomFolder/LAPSRetry.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
		//alert(jspURL);
		document.getElementById('PRODUCT_JSP').src=jspURL;
		selectSheet('tab1',1);
		executeServerEvent(event.target.id, event.type, '', false);
	}//LAPSRetry
}

function changeEventRepair(event){
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (controlName == 'REJECT_DEC') {
		if(getValue('REJECT_DEC') == 'Reject'){
			setStyle(CRO_REJ_REASON,'disable','false');
		}else{
			setStyle(CRO_REJ_REASON,'disable','true');
		}
	}
	}

function postServerEventHandlerRepair(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	console.log(jsonData.succes);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ('' != jsonData.message && null != jsonData.message) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
				showMessage(arr[0], arr[1], 'error');
			}
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		break;
	case EVENT_TYPE.CLICK:
		if(controlName == BUTTON_SUBMIT){
			console.log('BUTTON_SUBMIT, workstep: '+workstepName+', json message: '+jsonData.success);
			if(jsonData.success) {
				if(WORKSTEPS.REPAIR == workstepName && jsonData.message == '') {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							saveWorkItem();
							completeWorkItem();
						} else if(result == false) {
							return;
						}
					});
				} else {
					saveWorkItem();
					completeWorkItem();
				}
			} else {
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
			}
		}
		break;
	}
}