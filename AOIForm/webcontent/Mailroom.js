function onLoadMailRoomOperation(){
	console.log('***** inside onFormLoad *****');
	var workstepName = getWorkItemData('activityName'); 
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	var controlNames = [WI_NAME,CURR_WS_DETAIL,'TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID','TXT_CURRWS','RD_INST_DEL'];
	var controlValues = [wi_name,workstepName,CustName,CustDob,CustId,workstepName,'No'];
	setMultipleFieldValues(controlNames,controlValues);
	//alert(workstepName);
	//var disableControlsList = [RELIGION,MARITAL_STATUS,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
	setStyle("L1_APP_REQ","visible","false");
	setStyle("L2_APP_REQ","visible","false");
	setStyle("L3_APP_REQ","visible","false");
	setStyle("L4_APP_REQ","visible","false");
	setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
	//SANCT_RISK_ASSESS_MARKS,'prod_combo'
	var LevelsFieldsDisable = [SANCT_RISK_ASSESS_MARKS,
	                           SEC_SS_RISK_ASSESS,SEC_SS_TRSD,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,
	                           BTN_NEXT_CUST_SANCT,BTN_DEDUPE_SEARCH,CK_PER_DET,CHK_CONTACT_DET,CHK_INTERNAL_SEC,
	                           CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,CHK_BANKING_RELATION,CHECKBOX_FATCA,
	                           CRS_CB,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS_CRO,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CRO,
	                           SEC_PRODUCTION_CPD,SEC_OPT_PROD_CPD,SEC_OPT_PROD_CRO,SEC_FACILITY_CPD,SEC_FACILITY_CRO,
	                           SEC_DOC_REQ_CPD,SEC_DOC_REQ_CRO,SEC_SI_SIRFT,SEC_SI_SWP,SEC_DEL_INST,SEC_DEL_ADD,SEC_ACC_INFO_PD,
	                           SEC_ACC_INFO_AOR_MAKER,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,IS_COMPLIANCE_RISK_ASSESS,IS_COMPLIANCE_NAME_SCR,
	                           IS_PROD_APP_REQ,IS_CALL_BACK_REQ,LVW_DEDUPE_RESULT,FRAMEFATCA,CRS_RES_PERM_ADRS_US,SEC_CRS_DETAILS,
	                           PD_ANY_CHNG_CUST_INFO,SEC_CONTACT_DET_RA,SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_SIGN_OFF,
	                           SEC_BNK_REL_UAE_OVRS,SEC_ASSESS_OTH_INFO,SEC_FUND_EXP_RELTNSHP,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,
	                           SEC_INT_DETAIL,SEC_PERSONAL_DET,FRAME_CLIENTQUESTIONS,SEC_ADD_NEW_CUSTOMER,
	                           SEC_ACC_REL,NEW_CUST_FETCH_EIDA,SI_FRST_PAYMNT,SI_LST_PAYMNT,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,
	                           IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP,CHQ_ELIGIBILITY,FINAL_ELIGIBILITY,FINAL_CUSTOMER_RISK];
	disableControls(LevelsFieldsDisable);
	var sncCPD = [BTN_CPD_TRSD_CHK,SEC_SS_CPD_TRSD,CPD_FINAL_ELIGIBILITY,BTN_CALCULATE,CPD_RISK_ASSESS_MARKS,BTN_NEXT_CUST];
	disableControls(sncCPD);
	var sncCRO = [BTN_TRSD_CHECK,SEC_SS_TRSD,FINAL_ELIGIBILITY,SEC_SS_RISK_ASSESS,RISK_ASSESS_MARKS];
	disableControls(sncCRO);
	var compliance = [NEG_INFO,NEG_AXPLAIN,FATF,FATF_EXPLAIN,KYC,KYC_AXPLAIN];
	disableControls(compliance);
	var disableEntry = ['FINAL_CUSTOMER_RISK','FINAL_RISK_VAL_CPD','prod_combo',SEC_SS_CPD_RISK_ASSESS,'LAST_CAT_CAHNGE_DATE'];
	disableControls(disableEntry);
	setTabVisible();	
	if(getValue(CRO_DEC).toLowerCase() == ('approve')) {
		setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
		var controlnames = [CRO_REJ_REASON];
		clearAOControls(controlnames);
	}
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	disableControls(familyBanking);	// Changes for Family Banking 
	setStyle('SKIPUAEPASS_REASON', PROPERTY_NAME.DISABLE, 'true');
}

function handleMailRoomOperationEvent(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) { 
		clickMailRoomOperationEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeMailRoomOperationEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusMailRoomOperationEvent(event);		
	} else if (event.type == EVENT_TYPE.GOTFOCUS) {
		gotFocusMailRoomOperationEvent(event);
	}
}

function changeMailRoomOperationEvent(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if(event.target.id == CRO_DEC) {
		if(getValue(CRO_DEC).toLowerCase() == ('approve')) {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
			var controlnames = [CRO_REJ_REASON];
			clearAOControls(controlnames);
		} else {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, FALSE);
		}
	}
}

function lostFocusMailRoomOperationEvent(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event: ' + event.target.id + ', ' + event.type);
}

function gotFocusMailRoomOperationEvent(event){
	var pControlName = event.target.id;
	console.log('Event:' +pControlName);
	var workstepName = getWorkItemData('activityName');
	console.log('Event: ' + event.target.id + ', ' + event.type);
}

function clickMailRoomOperationEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	var dec = getValue("CRO_DEC");
	var rejectionReason = getValue("CRO_REJ_REASON");
	if(event.target.id == 'BTN_SUBMIT'){
		//alert('Submit Click');
		if("Return" == dec) {
			if("" == rejectionReason || "Select" == rejectionReason || "--Select--" == rejectionReason ) {
				alert('Please Select Rejection Reason.');
				return false;
			}
		} else {
			executeServerEvent(event.target.id, event.type,'', false);
			saveWorkItem();
			completeWorkItem();
		}
		//var response = executeServerEvent('setRemarksHistory', '', 'click', true);
		//return true;
	}
}

function postServerEventHandlerMailRoomOperation(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		setStyle(BTN_VIEW,'disable','false');
		setStyle('SKIPUAEPASS_REASON', PROPERTY_NAME.DISABLE, 'true');
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		}
		break;
	case EVENT_TYPE.CLICK:	
		if('afterSaveNext' == controlName) {
			executeServerEvent('tabClick', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if(jsondata.success && BTN_VIEW == controlName && "" != jsonData.message) {
			console.log('jsonData.message'+jsonData.message);
			var selecedRow = jsonData.message;
			selectRowHook(LVW_DEDUPE_RESULT,selecedRow);
		}
		break;
	}
}


function onClickTabMailRoomOperation(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, false);
}

function prehookSaveTabMailRoomOperation(tabId){
	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	console.log('input prehookSaveTabCPDMakerThreeStep: ' + input);
	console.log('inside prehookSaveTabCPDMakerThreeStep, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, sheetIndex, false);
}

function saveAndNextPreHookMailRoomOperation(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	var response = executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, true);
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