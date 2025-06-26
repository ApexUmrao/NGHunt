function onLevelsLoad(event){
	//alert('Levels');
	var workstepName = getWorkItemData('activityname');
	var wiName = getWorkItemData('processInstanceId');
	//var disableControlsList = [RELIGION,MARITAL_STATUS,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,L4_APP_REQ];
	setStyle("L1_APP_REQ","visible","false");
	setStyle("L2_APP_REQ","visible","false");
	setStyle("L3_APP_REQ","visible","false");
	setStyle("L4_APP_REQ","visible","false");
	var LevelsFieldsDisable = [SANCT_RISK_ASSESS_MARKS,BTN_TRSD_CHECK,SEC_SS_RISK_ASSESS,SEC_SS_TRSD,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,BTN_NEXT_CUST_SANCT,BTN_DEDUPE_SEARCH,CK_PER_DET,CHK_CONTACT_DET,CHK_INTERNAL_SEC,CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,CHK_BANKING_RELATION,CHECKBOX_FATCA,CRS_CB,SEC_RISK_ASSESS_CRO,SEC_ELIG_ANALYSIS_CRO,SEC_ELIG_ANALYSIS_CPD,SEC_PRODUCTION_CRO,SEC_PRODUCTION_CPD,SEC_OPT_PROD_CPD,SEC_OPT_PROD_CRO,SEC_FACILITY_CPD,SEC_FACILITY_CRO,SEC_DOC_REQ_CPD,SEC_DOC_REQ_CRO,SEC_SI_SIRFT,SEC_SI_SWP,SEC_DEL_INST,SEC_DEL_ADD,SEC_ACC_INFO_PD,SEC_ACC_INFO_AOR_MAKER,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,IS_COMPLIANCE_RISK_ASSESS,IS_COMPLIANCE_NAME_SCR,IS_PROD_APP_REQ,IS_CALL_BACK_REQ,LVW_DEDUPE_RESULT,FRAMEFATCA,CRS_RES_PERM_ADRS_US,SEC_CRS_DETAILS,PD_ANY_CHNG_CUST_INFO,SEC_CONTACT_DET_RA,SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_SIGN_OFF,SEC_BNK_REL_UAE_OVRS,SEC_ASSESS_OTH_INFO,SEC_FUND_EXP_RELTNSHP,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,'SEC_INT_DETAIL','prod_combo',SEC_PERSONAL_DET,FRAME_CLIENTQUESTIONS,SEC_ADD_NEW_CUSTOMER,SEC_ACC_REL,NEW_CUST_FETCH_EIDA,SI_FRST_PAYMNT,SI_LST_PAYMNT];
	disableControls(LevelsFieldsDisable);
	disableControls(disableControlskyc);
	//executeServerEvent(event.target.id, 'load', '', false);
}

function handleLevelsEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	//alert("APP assesment Handle event");
	if (event.type == EVENT_TYPE.CLICK) {
		clickLevelsEvent(event);
	}
}


function clickLevelsEvent(event) {
	alert('Sub Click LEVELLS    '+event.target.id);
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(event.target.id == 'BTN_SUBMIT'){
		executeServerEvent(event.target.id, 'click', '', false);
		
	}
		
	}



function postServerEventHandlerLevels(controlName, eventType, responseData) {
	saveWorkItem();
		completeWorkItem();
}

function saveAndNextPreHookAppassesment(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	var response = executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	console.log('save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	return true; 
}