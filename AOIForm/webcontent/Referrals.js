function onLoadReferrals(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	disableControls(disableControlskyc);
  	onReferralsLoad();
  	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
  	    setTabStyle("tab5",17, "visible", "false");
  	}
	if(workstepName == 'PBG Vigilance') {
  	    setTabStyle("tab5",11, "visible", "false"); //Optinal Product tab
		setTabStyle("tab5",13, "visible", "false"); //Category Change tab
		//setStyle('add_acc_relation','disable','true'); //disable + button
  	}
  	disableControls(familyBanking);
}

function onReferralsLoad(){

	var workstepName = getWorkItemData('activityname');
	var wiName = getWorkItemData('processInstanceId');
	//var disableControlsList = [RELIGION,MARITAL_STATUS,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
	setStyle('L1_APP_REQ','visible','false');
	setStyle('L2_APP_REQ','visible','false');
	setStyle('L3_APP_REQ','visible','false');
	setStyle('L4_APP_REQ','visible','false');
	var LevelsFieldsDisable = [SANCT_RISK_ASSESS_MARKS,BTN_TRSD_CHECK,SEC_SS_RISK_ASSESS,SEC_SS_TRSD,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,BTN_NEXT_CUST_SANCT,BTN_DEDUPE_SEARCH,CK_PER_DET,CHK_CONTACT_DET,CHK_INTERNAL_SEC,CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,CHK_BANKING_RELATION,CHECKBOX_FATCA,CRS_CB,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS_CRO,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CRO,SEC_PRODUCTION_CPD,SEC_OPT_PROD_CPD,SEC_OPT_PROD_CRO,SEC_FACILITY_CPD,SEC_FACILITY_CRO,SEC_DOC_REQ_CPD,SEC_DOC_REQ_CRO,SEC_SI_SIRFT,SEC_SI_SWP,SEC_DEL_INST,SEC_DEL_ADD,SEC_ACC_INFO_PD,SEC_ACC_INFO_AOR_MAKER,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,IS_COMPLIANCE_RISK_ASSESS,IS_COMPLIANCE_NAME_SCR,IS_PROD_APP_REQ,IS_CALL_BACK_REQ,LVW_DEDUPE_RESULT,FRAMEFATCA,CRS_RES_PERM_ADRS_US,SEC_CRS_DETAILS,PD_ANY_CHNG_CUST_INFO,SEC_CONTACT_DET_RA,SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_SIGN_OFF,SEC_BNK_REL_UAE_OVRS,SEC_ASSESS_OTH_INFO,SEC_FUND_EXP_RELTNSHP,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,'SEC_INT_DETAIL','prod_combo',SEC_PERSONAL_DET,FRAME_CLIENTQUESTIONS,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,NEW_CUST_FETCH_EIDA,SI_FRST_PAYMNT,SI_LST_PAYMNT,'KYC_PRE-ASSESSMENT'];
	disableControls(LevelsFieldsDisable);
	//executeServerEvent(event.target.id, 'load', '', false);
	setFieldValue(CRO_REJ_REASON,'');
	openTrsdJsp();
	setKYCFlagPrivateclient();
    pepAssesment(); //Dcra

}

function handleReferralsEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventReferrals(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventReferrals(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusReferralsEvent(event);
	}
}

function clickEventReferrals(event){
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	var dec = getValue('CRO_DEC');
	if('setRemarksHistory' == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, 'setRemarksHistory', false);
	} else if(PRODUCT_QUEUE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(CRO_DEC == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == BUTTON_SUBMIT){
		saveWorkItem();
		executeServerEvent(event.target.id, event.type, 'CA008_Clear', false);
	}
}

function changeEventReferrals(event){
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(MANUAL_PASSPORTISSDATE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(MANUAL_PASSPORTEXPDATE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(MANUAL_VISAISSDATE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(MANUAL_VISAEXPDATE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == CRO_DEC)  {
		if(getValue(CRO_DEC)=='Neutral Advisory' || getValue(CRO_DEC)== 'No Objection') {
			 setFieldValue(CRO_REJ_REASON, '');
			 setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,TRUE);
		} else {
			 setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'false');
		}
	}
}

function lostFocusReferralsEvent(event) {  
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
}

function saveAndNextPreHookReferrals(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('Input saveAndNextPreHookDDE: ' + input);
	var response = executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, true);
	console.log('Save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function selectRowHookReferrals(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId) {
		setFieldValue(SELECTED_ROW_INDEX, (getValueFromTableCell(ACC_RELATION, selectedRowsArray[0], 0)-1));
		return false;
	}
}

function onClickTabReferrals(tabId,sheetIndex,eventCall){
	console.log('onClickTabReferrals'+sheetIndex);
	console.log(sheetIndex);
	executeServerEvent('tabClick', EVENT_TYPE.CLICK, sheetIndex, false);

}

function postServerEventHandlerReferrals(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	console.log(jsonData.success);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
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
		setStyle(ACC_RELATION,'disable','false');
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		}
	break;
	case EVENT_TYPE.CLICK:
		if(controlName == BUTTON_SUBMIT){
			console.log(getValue(CRO_DEC));
			if(jsonData.success) {
				showConfirmDialog(CA008, confirmDialogButtons, function(result) {
					if(result == true) {
						saveWorkItem();
						clearCombo(CRO_DEC);
						completeWorkItem();
					} else if(result == false) {
						saveWorkItem();
					}
				});
			}
			saveWorkItem();
			
		} else if('afterSaveNext' == controlName) {
			executeServerEvent('tabClick', EVENT_TYPE.CLICK, ','+getSheetIndex('tab5'), false);
		}
	}
}