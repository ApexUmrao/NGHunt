function onQDEAccountInfoLoad(){
//	onLoad();
//	lockTFOSection(LOCK_DDEACCOUNTINFO_FRAMES);
}

function onQDECustomerInfoLoad(){
	
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
//	var enableControlsList = [RES_WITHOUT_EIDA,CARRYING_EIDA_CARD];
//	var disableControlsList = [RELIGION,MARITAL_STATUS,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
//	var hideControlsList = [CURR_WS_DETAIL];
//	enableControls(enableControlsList);
//	disableControls(disableControlsList);
//	hideControls(hideControlsList);
//	setValues({[CRO_REMARKS]: '', [WI_NAME]: wi_name, [CURR_WS_DETAIL]: workstepName}, true);				
//	//change Label_WS_Name to activity name
//	//make header read only
//	frame81CPDDisable();
//	setManualFieldsEnable();		
}

function onLoad(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var decision = getValue(CRO_DEC);
	var sCompDec = getValue(COMP_DEC);
	secAccRelCPDDisable();
	var req_type = getValue(REQUEST_TYPE);
	var disableBagSet = [Frame17,Frame2,SEC_DOC_REQ_CRO,NEW_DEL_MODE,CHANNEL_NAME,AO_EXISTING_NOM_PRSN,AO_BRNCH_OF_INSTANT_ISSUE,'KYC_PRE-ASSESSMENT'];
	disableControls(disableBagSet);
	
	if(req_type == "New Account with Category Change" || req_type == "Category Change Only") {
		var BagsetOne = [SEC_CAT_CHNG,SEC_INTERNAL_DETL,SEC_ACC_INFO_ECD];
		var BagsetTwo = [OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE,OLD_CUST_SEGMENT];
		disableControls(BagsetTwo);
		enableControls(BagsetOne);
		if(req_type == "Category Change Only") {
			setStyle(SEC_ACC_INFO_PD,PROPERTY_NAME.DISABLE,"true");
		}
	} else {
		disableControls(BagsetOne);
		setStyle(SEC_ACC_INFO_PD,PROPERTY_NAME.DISABLE,"true");
		enableControls(enableBagsetOne);
	}
	var clearField = [CRO_REMARKS,ACC_INFO_PD_LVW];
	clearControls(clearField);
	
	if(decision == "Permanent Reject - Discard" || sCompDec == "Negative Advisory"){
		formObject.NGMakeFormReadOnly();
		var bagSetThree = [NEG_INFO,FATF,KYC,Frame18,Frame20,Frame21,Frame22,Frame28,Frame27,Frame25,Frame37,Frame54,Frame53];
		var bagSetFour = [CRO_DEC,CRO_REMARKS,CRO_REJ_REASON,BTN_SUBMIT];
		disableControls(bagSetThree);
		enableControls(bagSetFour);
		secAccRelCPDDisable();
		manualFrameCPDDisable();
		if(workstepName == "QDE_ Account_Info") {
			//Frame23_CPD_Disable(); to be made
			setStyle(NOM_REQ,PROPERTY_NAME.DISABLE,"true");
			Frame_delivery();// in javasanal 
			secDelAddCPDDisable();
		}	
		if(getValue("COUNT_WI") != "0") 
		{
			setStyle("SUBMIT_1",PROPERTY_NAME.DISABLE,"true");
		}
		if(decision == "Permanent Reject - Discard" || sCompDec == "Negative Advisory")
		{
			clearCombo(CRO_DEC);
			formObject.NGAddItem("AO_CRO_DEC","--Select--");// JAVA
			formObject.NGAddItem("AO_CRO_DEC","Permanent Reject - Discard");// JAVA
			formObject.setNGListIndex("AO_CRO_DEC",0);// JAVA
		}
	}
	var req_type = getValue(REQUEST_TYPE);
	if(req_type == "New Account with Category Change" || req_type == "Category Change Only") {						
		if(getValue(NEW_CUST_SEGMENT) != ("Select")){

		}
		else{
			var bagSetFive = [IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE,IS_VVIP,IS_PREVILEGE_TP_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_EXCELLENCY_TP_CAT_CHANGE,IS_CAT_BENEFIT_OTHER,CAT_BENEFIT_OTHER];
			disableControls(bagSetFive);
		}
		setResidentWithoutEidaComboQDE();
		deleteECBCallsDetails();//to be made in java
	}
	//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	executeServerEvent(event.target.id, event.type, '', false);
}

function clickEventDDECustomerInfo(){
	var workstepName = getWorkItemData('activityName');
	var pControlName = event.target.id;
	var pControlValue = getValue(pControlName);
	if(event.target.id == VIEW){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == VIEW){
		docApproval();
	} else if (event.target.id == 'visaStatus_manual'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'VISANO_MANUAL'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'Command55'){ //check for save sahil
		var selectedRows =getSelectedRowsIndexes('delivery_preference'); 
		var iSelectedRow = selectedRows[0];  
		executeServerEvent(event.target.id, event.type, iSelectedRow, false);
	} else if (workstepName == 'DDE_CUST_INFO')) {//fardeen
		fillManualDataInBelowFields(pControlName,pControlValue);
		fillFCRDataInBelowFields(pControlName,pControlValue);
		fillEIDADataInBelowFields(pControlName,pControlValue);
	}

	
	var selectedRows =getSelectedRowsIndexes(LISTVIEW_PARTY); 
	var iSelectedRow = selectedRows[0];  
}

function changeEventDDECustomerInfo(){
	var workstepName = getWorkItemData('activityName');
	var pControlName = event.target.id;
	var pControlValue = getValue(pControlName);
	if (workstepName == 'DDE_CUST_INFO')) {//fardeen
		fillManualDataInBelowFields(pControlName,pControlValue);
		fillFCRDataInBelowFields(pControlName,pControlValue);
		fillEIDADataInBelowFields(pControlName,pControlValue);
	}
}

function onClickTabDDEAccountInfo(tabId,sheetindex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
}

function postServerEventHandlerDDECustomer() {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ("" != jsonData.message && null != jsonData.message) {
			if((jsonData.message).includes('###')){
				var arr = jsonData.message.split('###');
					showMessage(arr[0], arr[1], 'error');
			}
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		if(controlName == VIEW){
			if(jsonData.success && 'DedupeSelectedIndex' == jsonData.message){
				//write code to set index in grid -sahil
			}
		}
	break;
	case EVENT_TYPE.CLICK:
		
	break;
	}
}