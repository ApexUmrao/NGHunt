var saveNextClick = false;
var trsdCheckDone = false;
function onLoadCPDMakerFourStep() {
	console.log('***** inside onFormLoad onLoadCPDMakerFourStep *****');
	var workstepName = getWorkItemData('activityName'); 
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, 'true');
	if(getValue(EMP_CITY) == 'OTHERS' || getValue(EMP_CITY) == ''){
		setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	}else{
		setFieldValue(EMP_CITY_OTHERS,'');
		setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
	if(getValue(EMP_STATE) == 'OTHERS' || getValue(EMP_STATE) == ''){
		setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	} else {
		setFieldValue(EMP_STATE_OTHERS,'');
		setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	} 
	if(getValue(SELECTED_ROW_INDEX) == null || getValue(SELECTED_ROW_INDEX) == ''){
		setFieldValue(SELECTED_ROW_INDEX,'0');
	}
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	/*var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);*/
	if(getValue(CHECKBOX_VISA_STATUS_MANUAL) == "") {
		setStyle(MANUAL_VISASTATUS, PROPERTY_NAME.DISABLE, 'false');
	}
	

	var  decision = getValue(CRO_DEC);
	setStyle(IS_PROD_APP_REQ,PROPERTY_NAME.VISIBLE,'false');
	setStyle(BUTTON_GENERATE_TEMPLATE, PROPERTY_NAME.DISABLE, 'true');
	var controlNames = [TXT_CURRWS,CURR_WS_DETAIL,CRO_DEC,CRO_REMARKS];
	var controlValues = [workstepName,workstepName,'',''];
	setMultipleFieldValues(controlNames,controlValues);
	// call a method below  fieldsOnLoadCPDMaker();
	fieldsOnLoadCPDMaker();
	var eCPDMakerFourStepFieldsEnable  = [SEC_DEC,DRP_RESEIDA,RA_CARRYNG_EID_CARD];// Frame35 not present
	var dCPDMakerFourStepFieldsDisable  = [CP_CITY,CORR_STATE,CORR_CNTRY,CUST_NATIONALITY,CUST_PREFIX,CUST_GENDER,PROF_CODE,EMPNAME,PROFESSION,SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,PD_OTHERS,PD_MARITALSTATUSOTHER]; // "COMMAND59" should be add but its name is not present
	enableControls(eCPDMakerFourStepFieldsEnable);
	disableControls(dCPDMakerFourStepFieldsDisable);
	//disableControls(ALL_CHECKBOX_EIDA);
	//disableControls(ALL_CHECKBOX_FCR);
	setStyle(CURR_WS_DETAIL,"visible","false");
	//frame52Disable();
	if(getValue(RA_CB_OTHERS) == "False") {
		setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	}
	else {
		setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
	//var iSelectedRow = getSelectedRowsIndexes(ACC_RELATION);
	if(getValue(SELECTED_ROW_INDEX) != "null"){
		iSelectedRow = getValue(SELECTED_ROW_INDEX);
	}
	var bnk_relation11 =  getValueFromTableCell(ACC_RELATION,iSelectedRow,7);
	var eEnableAcc = [IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_MORTGAGES,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP];
	var eEnableAcc1 = [IDS_PC_CB_TP,IDS_PC_CB_TRAVEL,IDS_PC_CB_SPORT,IDS_PC_CB_ENTERTAINMENT,IDS_PC_CB_SHOPPING];
	if(bnk_relation11 == "New") {
		if(getValue(IDS_OTH_CB_OTHERS) == "False") {
			setStyle(IDS_BNFT_CB_OTHERS, PROPERTY_NAME.DISABLE, 'true');
			setStyle(IDS_OTH_CB_OTHERS, PROPERTY_NAME.DISABLE, 'true');
			if(!getValue(PD_CUSTSEGMENT) == "") {
				setStyle(IDS_OTH_CB_OTHERS, PROPERTY_NAME.DISABLE, 'false');
			}
		} else {
			setStyle(IDS_BNFT_CB_OTHERS, PROPERTY_NAME.DISABLE, 'false');
			setStyle(IDS_OTH_CB_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}
		var category = getValue(PD_CUSTSEGMENT);
		if(category == "Aspire" || category == "Simplylife") {
			enableControls(eEnableAcc);
		} else if(category ==  "Emirati Excellency"  || category == "Excellency" || category == "Private Clients") {
			enableControls(eEnableAcc); 
			setStyle(IDS_BNFT_CB_TP, PROPERTY_NAME.DISABLE, 'false');
		} else if(category ==  "Privilege" || category == "Emirati") {
			enableControls(eEnableAcc);
			enableControls(eEnableAcc1);
		}	
	} else {
		setStyle(IDS_BNFT_CB_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		setStyle(IDS_OTH_CB_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	}
	//var iSelectedRow1 = getSelectedRowsIndexes(ACC_RELATION);
	var bnk_relation2 = getValueFromTableCell(ACC_RELATION,iSelectedRow,7);
	if(bnk_relation2 == "Existing" ) {
		setStyle(IDS_BNFT_CB_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	}
	
	var eEnableAcc2 = [SEC_SI,SEC_DEL_INST,SEC_INTERNAL_DETL]; // Frame4,Tab1,Tab2,Frame34 is not present
	setStyle(TXT_DOB,"visible","true");
	setStyle(TXT_CUSTOMERNAME,"visible","true"); // Label279 ,Label280 not present
	enableControls(eEnableAcc2);
	var iSearchedCustomer1 = 0;
	var iProcessedCustomer1 = 0;
	/*iSearchedCustomer1 = getValue("NO_OF_CUST_SEARCHED")); // COMMAND20 not present

	iProcessedCustomer = getValue("NO_OF_CUST_PROCESSED"))+1;
	if(iSearchedCustomer1 == iProcessedCustomer1) {
		formObject.setNGEnable("COMMAND20",true);
		setStyle(COMMAND20, PROPERTY_NAME.DISABLE, 'true');
	} else {
		formObject.setNGEnable("COMMAND20",false);
		setStyle(COMMAND20, PROPERTY_NAME.DISABLE, 'true');
	}*/  // COMMAND20 not present
	setStyle(EXISTING_NOM_PRSN, PROPERTY_NAME.DISABLE, 'true');
	setStyle(BRNCH_OF_INSTANT_ISSUE, PROPERTY_NAME.DISABLE, 'true');
	var eDisableAcc = [SEC_ACC_INFO_PD,SEC_ACC_INFO_ECD,SEC_SI]; // Frame34 not present
	var req_type = getValue("REQUEST_TYPE");
	if(req_type.toUpperCase() == "NEW ACCOUNT WITH CATEGORY CHANGE"  
		|| req_type.toUpperCase() == "CATEGORY CHANGE ONLY") {
		setTabStyle("tab3",13, "visible", "true");
	    setStyle(SEC_INTERNAL_DETL, PROPERTY_NAME.DISABLE, 'false');
		if(getValue("NEW_RM_CODE_CAT_CHANGE")  == "") {
			//setValues({NEW_RM_CODE_CAT_CHANGE:""},true);
			setFieldValue(NEW_RM_CODE_CAT_CHANGE,'');
		}
		if(getValue("NEW_RM_NAME_CAT_CHANGE") == "") {
			//setValues({NEW_RM_NAME_CAT_CHANGE:""},true);
			setFieldValue(NEW_RM_NAME_CAT_CHANGE,'');
		}
		if(req_type == "Category Change Only") {
			disableControls(eDisableAcc);
		}
	} else {
		 setStyle(SEC_CAT_CHNG, PROPERTY_NAME.DISABLE, 'true');
		 setStyle(SEC_ACC_INFO_PD, PROPERTY_NAME.DISABLE, 'false'); // Frame34 not present
	}
	var eDisableAcc1 = [ADD_PRODUCT,SEARCH,REMOVE,HD_PASSPORT_NO,HD_VISA_NO,HD_PASS_ISS_DATE,HD_VISA_ISSUE_DATE,HD_PASS_EXP_DATE,HD_EXP_DATE];
	disableControls(eDisableAcc1);
	if(!getValue(FCR_NAME) == "") {
		setStyle(PD_CUSTSEGMENT, PROPERTY_NAME.DISABLE, 'true');
	} else {
		setStyle(PD_CUSTSEGMENT, PROPERTY_NAME.DISABLE, 'false'); 
	}
	setStyle("tab5","visible","true"); // ask
	setStyle(GI_EXST_SINCE, PROPERTY_NAME.DISABLE, 'true');
	var eDisableAcc2 = ['AO_NOM_REQ',SEC_CI,CPD_CHK_REMARKS,CPD_REMARKS,CPD_WRLD_CHK_REMARKS,NEG_INFO,FATF,
	                    KYC,SEC_ACC_INFO_PD,SEC_SI_SIRFT,SEC_SI_SWP,'delete','modify','add',SI_PRODUCT]; //Frame19,Frame24 not present
	var eEnableAcc3 = [CRO_DEC,CRO_REMARKS,CRO_REJ_REASON];
	var eDisableAcc3 = [SEC_CAT_CHNG,SEC_SI,SEC_DECISION_LAST]; //Frame4,Frame2,Frame34,Frame63,Frame78
	if(getValue(CRO_DEC) == ('Approve')) {	// on loading if decision is defaulted approve then rject_reason is enabled
		setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
		var controlnames = [CRO_REJ_REASON];
		clearAOControls(controlnames);
	}
	if(CRO_DEC == 'Permanent Reject - Discard') {
    	disableControls(eDisableAcc2);
		if(decision == ("Permanent Reject - Discard")){
			enableControls(eEnableAcc3);
		}
		else{
			disableControls(eEnableAcc3);
			//Command24 do it separatly enable true
			setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
		}
		Frame81_CPD_Disable();
		manualFrameCPDDisable();
		secAssessOthInfoCPDDisable(); //Frame18_QDE_Disable(); 
		secDelAddCPDDisable();
		disableControls(eDisableAcc3);
		setStyle("Pic1","visible","false");
	}
    setStyle("Pic1","visible","false");
	if(getValue("CHECK95") == 'true') {
		FrameFATCA_CPD_Enable();
		setStyle(COMBODOC, PROPERTY_NAME.DISABLE, 'false');
	}
	if(getValue(CHECKBOX_VISA_NO_MANUAL) == "true" && (!getValue(MANUAL_VISANO) == "MDSA")) {
		setStyle(MANUAL_VISANO, PROPERTY_NAME.DISABLE, 'false');
	}
	
	setTabStyle("tab3",6, "visible", "false");
	setTabStyle("tab3",8, "visible", "false");
	//executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);//removed on 15july (unnecessarily written)
	var cpdTRSDDecision = getValue('TRSD_DECISION');
	setValues({CPD_TRSD_FINAL_DECISION:cpdTRSDDecision},true);
	//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	openTrsdJspCPD();
	PepNationality();
	pepAssesment();
	visiblePreAssesmentField();//AO CCO AND UPGRADE
	setKYCFlagPrivateclient();//Dcra
}

function fieldsOnLoadCPDMaker() {
	console.log('Event:  ' + getValue(RELIGION));
	if(getValue(RELIGION) == "") {
		//setValues({RELIGION:"Others"},true);   // Cant be set needs to be asked
	}
	console.log('Event:1  ' + getValue(MARITAL_STATUS));
	if(getValue(MARITAL_STATUS) == "") {
		//setValues({MARITAL_STATUS:"Single"},true);	//cant be set needs to be asked
	}
	if(getValue(GI_IS_CUST_VIP) == "") {
		//setValues({GI_IS_CUST_VIP:"No"},true);
		setFieldValue(GI_IS_CUST_VIP,'No');
	}
	if(getValue(RA_IS_CUST_DEALNG_ARMAMNT) == "") {
		//setValues({RA_IS_CUST_DEALNG_ARMAMNT:"No"},true);
		setFieldValue(RA_IS_CUST_DEALNG_ARMAMNT,'No');
	}
	if(getValue(RA_IS_CUST_DEALNG_HAWALA) == "") {
		//setValues({RA_IS_CUST_DEALNG_HAWALA:"No"},true);
		setFieldValue(RA_IS_CUST_DEALNG_HAWALA,'No');
	}
	if(getValue(RA_PRPSE_TAX_EVSN) == "") {
		//setValues({RA_PRPSE_TAX_EVSN:"No"},true);
		setFieldValue(RA_PRPSE_TAX_EVSN,'No');
	}
	if(getValue(RA_IS_CUST_PEP) == "") {
		//setValues({RA_IS_CUST_PEP:"No"},true);
		setFieldValue(RA_IS_CUST_PEP,'No');
	}
	if(!getValue(FCR_NATIONALITY) == ("") && getValue(FCR_NATIONALITY) == ("USA")) {
		if(getValue(FAT_US_PERSON) == "") {
			//setValues({FAT_US_PERSON:"YES"},true);
			setFieldValue(FAT_US_PERSON,'YES');
			setStyle(FAT_US_PERSON, PROPERTY_NAME.DISABLE, 'true'); 
		}
	}
	if(getValue(FAT_US_PERSON) == ("Yes") || getValue(FAT_LIABLE_TO_PAY_TAX) == ("Yes")) {
		if(getValue(FAT_CUST_CLASSIFICATION) == "") {
			//setValues({FAT_CUST_CLASSIFICATION:"Yes"},true);
			setFieldValue(FAT_CUST_CLASSIFICATION,'Yes');
			setStyle(FAT_CUST_CLASSIFICATION, PROPERTY_NAME.DISABLE, 'true'); 
		}
	}
	if(getValue(CHECKBOX_VISA_NO_MANUAL) == ("true") && (!getValue(MANUAL_VISANO) == ("MDSA"))) {
		setStyle(MANUAL_VISANO, PROPERTY_NAME.DISABLE, 'false'); 
	}
}

function handleCPDMakerFourStepEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventCPDMakerFourStep(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventCPDMakerFourStep(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusCPDMakerFourStepEvent(event);
	}
}

function saveAndNextPreHookFourStep(tabid){
	saveNextClick = true;
	var input = event.target.innerHTML+','+(getSheetIndex(tabid));
	var tab = getSheetIndex(tabid);
	PepNationality();
	
	if(tab == 0 && getSelectedRowsIndexes(ACC_RELATION).length == 0){
		showMessage(ACC_RELATION, 'Please select any one customer', 'error');
		return false;
	}
        //added by shivanshu for mandatory alert for eida expiry date
	if(getSheetIndex(tabid) == 2){ 
		mandatoryEidaNo();
	}
	//added by reyaz 16092022
	if(tab == 4){
		if(getValue('CRS_CERTIFICATION_OBTAINED') =='No' || getValue('CRS_Classification') == 'UPDATED-UNDOCUMENTED'
		    || getValue('CRS_Classification') == 'UNDOCUMENTED' || getValue('CRS_Classification') == ''){ 
		     showMessage('CRS_Classification', 'CRS Classification should be Updated Documented', 'error');
		     return false;
	     }
	}
	if(tab == 16) {
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
//		executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, false);
	}
	if(getSheetIndex(tabid) == 10) {
		if((getValue(REQUEST_TYPE) != 'Category Change Only')){
			var prodCount = getGridRowCount(PRODUCT_QUEUE);
			for(var i=0; i<prodCount; i++){
				if(getValueFromTableCell(PRODUCT_QUEUE, i, 3) == '') {
					showMessage(PRODUCT_QUEUE,"Please select currency.", 'error');
					return false;
					break;
				}
			}
			/*if(getValueFromTableCell(PRODUCT_QUEUE, 0, 3) == '') {
				showMessage(PRODUCT_QUEUE,"Please select currency.", 'error');
			}*/
		}
	}
	var trsdDec = getValue(CPD_TRSD_FINAL_DECISION);
	if(tab == 7 && trsdCheckDone == false && !getStyle(BTN_CPD_TRSD_CHK,'isdisabled') 
			&& trsdDec.toUpperCase() != 'RETURNED'){
		trsdCheckDone = true;
		showMessage(BTN_CPD_TRSD_CHK, 'Please perform TRSD Check', 'error');
		return false;
	} else{
		console.log('input saveAndNextPreHookDDE: ' + input);
		var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
		console.log('save and next response:: '+response);
		if(response != '' && response != undefined){
			var jsondata = handleAOResponse(response);
			if (!jsondata.success){
				return false;
			}
		}
	}
//	saveWorkItem();
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}


function lostFocusCPDMakerFourStepEvent(){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
	var iSelectedRow = selectedRows[0]; 
	if (controlName == "PRODUCT_QUEUE.PROD_CODE" || controlName == "PRODUCT_QUEUE.CURRENCY"){
		executeServerEvent(event.target.id, event.type, iSelectedRow, false);
	}

}

function changeEventCPDMakerFourStep(event) {
	var controlName  = event.target.id;
	if (event.target.id == COMBODOC){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if(GROUP_TYPE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == (DFC_STATIONERY_AVAIL)) { 
		executeServerEvent(DFC_STATIONERY_AVAIL, event.type,"", false);
	}else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	}else if (NOM_REQ == (controlName)){
		executeServerEvent(NOM_REQ, event.type,"", false);			
	} else if(EXISTING_NOM_PRSN == (controlName)) {
		executeServerEvent(EXISTING_NOM_PRSN, event.type,"", false);
	} else if (controlName == MANUAL_MOTHERNAME) {
		executeServerEvent(controlName, event.type, '', false);
	} 
//	else if(event.target.id == RA_CUST_SRC_WEALTH){
//		var srcWealth = getValue(RA_CUST_SRC_WEALTH);
//		console.log('srcWealth::'+srcWealth);
//		if(srcWealth!=''){
//			if(checkSpecialChars(srcWealth)){
//				showMessage(RA_CUST_SRC_WEALTH,'Special Characters Not allowed for Customer Source of Wealth','error');
//				setFieldValue(RA_CUST_SRC_WEALTH,'');
//			} 
//		}
//	} 
	else if(event.target.id == EMP_CITY) {
		if(getValue(EMP_CITY) == 'OTHERS'){
			setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}else{
			setFieldValue(EMP_CITY_OTHERS,'');
			setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'true'); 
		}
	}else if(event.target.id == EMP_STATE) {
		if(getValue(EMP_STATE) == 'OTHERS'){
			setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}else{
			setFieldValue(EMP_STATE_OTHERS,'');
			setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'true');//EMP_STATE_OTHERS
		}
	} else if (event.target.id == 'CRS_CERTIFICATION_OBTAINED_0' || event.target.id == 'CRS_CERTIFICATION_OBTAINED_1'){
		if('Yes' == getValue(CRS_CERTIFICATION_OBTAINED)){
			var controlNames = ['CRS_Classification'];
			var controlValues = ['UPDATED-DOCUMENTED'];
			setMultipleFieldValues(controlNames,controlValues);
			disableControls([controlNames]);
		}else {
			var controlNames = ['CRS_Classification'];
			var controlValues = [''];
			setMultipleFieldValues(controlNames,controlValues);
			enableControls([controlNames]);
		}
		executeServerEvent(event.target.id, event.type,"", false);
	} else if(controlName == CORR_STATE) {
		if(getValue(CORR_STATE) != 'OTHERS'){
			setFieldValue(CP_OTHERS,'');	
		}
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RES_STATE) {
		if(getValue(RES_STATE) != 'OTHERS'){
			setFieldValue(RA_OTHERS,'');	
		}
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PERM_STATE) {
		if(getValue(PERM_STATE) != 'OTHERS'){
			setFieldValue(PA_OTHERS,'');	
		}
		executeServerEvent(controlName, event.type, '', false);
	} else if (event.target.id == PD_CUSTSEGMENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == RM_CODE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == CP_CITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == drp_reseida){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == 'Text22'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_PER_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_FIRSTNAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_LASTNAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == UDF_Value){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_MOBILE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_PH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_RESIDENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_STATE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_VISASTATUS){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == 'COMBO34'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EMP_STATUS){
		var controlName = event.target.id;
		var controlNames = [EMP_COUNTRY,EMP_STREET,EMP_CITY,EMP_PO_BOX,EMP_STATE,EMP_STATE_OTHERS,EMP_CITY_OTHERS];
		if(getValue(controlName) != 'Self Employed' && getValue(controlName) != 'Employed'){
			disableControls(controlNames);
			clearAOControls(controlNames);	
		} else {
			enableControls(controlNames);
		}
		executeServerEvent(controlName, event.type, '', false);
	} else if (event.target.id == ED_CUST_CRS_BRDR_PAYMENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == RA_IS_UAE_RESIDENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == RA_IS_CUST_PEP){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == 'PRODUCTQUEUE_MODEOFFUNDING'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == INDICIACOMBO){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FAT_CUST_CLASSIFICATION){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == DATEPICKERW8){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FAT_SSN){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == DATEPICKERCUST){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == COMBODOC){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_NATIONALITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_NATIONALITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_PH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_MOBILE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == CHECKFCR){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == CHECKEIDA){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_RESIDENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_RESIDENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FAT_US_PERSON){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == CNTRY_OF_BIRTH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_COUNTRYBIRTH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_COUNTRYBIRTH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_COUNTRYBIRTH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == DATEPICKERCUST){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FAT_SSN){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == POACOMBO){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FAT_LIABLE_TO_PAY_TAX){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == CHECKMANUAL){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_RESIDENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_MOBILE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_PH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_NATIONALITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_EMPLYR_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_EMPLYR_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_EMPLYR_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == FCR_NATIONALITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EIDA_NATIONALITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_NATIONALITY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == ED_MONTHLY_INCM){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == ED_ANNUAL_INC){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == SI_DEB_ACC_NO){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == 'rowSelect'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == ACC_INFO_PG){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == DOC_APPROVAL_OBTAINED){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == COURT_ORD_TRADE_LIC){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_PREFIX){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_PC_CB_TRAVEL){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_OTH_CB_OTHERS){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_CB_SAL_TRANSFER){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_CB_MORTGAGES){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_CB_TRB){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_CB_VVIP){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_PC_CB_ENTERTAINMENT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_PC_CB_SPORT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_BNFT_CB_TP){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_CB_INSURANCE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_CB_OTHERS){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_PC_CB_TP){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_PC_CB_SHOPPING){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IDS_PC_CB_TRAVEL){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_CAT_BENEFIT_OTHER){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_SALARY_TRANSFER_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_MORTAGAGE_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_INSURANCE_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_TRB_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_OTHERS_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_VVIP){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_PREVILEGE_TP_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_ENTERTAINMENT_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_SHOPPING_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_SPORT_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_TRAVEL_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_EXCELLENCY_TP_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == IS_SALARY_TRANSFER_CAT_CHANGE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == NEW_DEL_MODE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == DEL_STATE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == SOURCE_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == SOURCE_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == SOURCE_CODE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MAKER_NAME){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MAKER_CODE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == PD_ANY_CHNG_CUST_INFO){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == MANUAL_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == DEL_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == RES_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == CORR_CNTRY){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == 'perm_cntry'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == ED_MONTHLY_INCM){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == PA_SAMEAS){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == RA_SAMEAS){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == NOM_REQ){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == EXISTING_NOM_PRSN){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == COURT_ORD_TRADE_LIC){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if(event.target.id == (CHECKBOX_SELECTALL_FCR )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PREFIX_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FULLNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_FIRSTNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_LASTNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_SHORTNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_NO_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_ISS_DT_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_EXP_DT_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_NO_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_ISSUE_DATE_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_EXPIRY_DATE_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_NATIONALITY_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_MOTHERSNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_TYPE_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_STATUS_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_GENDER_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EIDANO_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CORR_POB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_STATE_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CNTRY_OF_CORR_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PROFESSION_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SELECTALL_EIDA )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PREFIX_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FULLNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_FIRSTNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_LASTNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_SHORTNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_NO_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_ISS_DT_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_EXP_DT_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_NO_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_ISSUE_DATE_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_EXPIRY_DATE_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_NATIONALITY_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_MOTHERSNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_TYPE_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_STATUS_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_GENDER_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EIDANO_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CORR_POB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_STATE_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CNTRY_OF_CORR_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PROFESSION_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SELECTALL_MANUAL )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PREFIX_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FULLNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_FIRSTNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_LASTNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_SHORTNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_NO_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_ISS_DT_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_EXP_DT_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_NO_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_ISSUE_DATE_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_EXPIRY_DATE_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_NATIONALITY_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_MOTHERSNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_TYPE_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_STATUS_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_GENDER_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CORR_POB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_STATE_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CNTRY_OF_CORR_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EIDANO_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PROFESSION_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRO_DEC) {
		if(getValue(CRO_DEC) == ('Approve')) {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
			var controlnames = [CRO_REJ_REASON];
			clearAOControls(controlnames);
		} else {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, FALSE);
		}
	} else if(event.target.id == (CHK_CONTACT_DET)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_PASSPORT_DET)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_INTERNAL_SEC)) {	
		console.log('chk');
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_GEN_INFO)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_EMP_DETAIL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_FUNDING_INFO)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_RISK_ASS)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == ('check95')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_EDD)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_BANKING_RELATION)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (ED_CB_SAL_AED)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_1)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_2)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_3)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_FATCA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(controlName == 'SUB_PERSONAL_TAX_0' || event.target.id == 'SUB_PERSONAL_TAX_1') {
		 if (getValue(SUB_PERSONAL_TAX) == 'Yes') {
				setStyle(PER_INC_TAX_CON_1, 'disable', 'false');
			}else if( getValue(SUB_PERSONAL_TAX) == 'No') {
				setStyle(PER_INC_TAX_CON_1, 'disable', 'true');
				setStyle(PER_INC_TAX_CON_2,'disable', 'true');
				setStyle(PER_INC_TAX_CON_3,'disable', 'true');
				//var controlNames = [PER_INC_TAX_CON_1,PER_INC_TAX_CON_2,CRO_DEC,PER_INC_TAX_CON_3];
				var controlNames = [PER_INC_TAX_CON_1,PER_INC_TAX_CON_2,PER_INC_TAX_CON_3];
				var controlValues = ['','',''];
				setMultipleFieldValues(controlNames,controlValues);
				
		}
		//executeServerEvent(SUB_PERSONAL_TAX, event.type, '', false);
	}else if (event.target.id == (EMPNAME)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (NEW_LINK)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == MANUAL_GENDER) {
		//setValues({[CUST_GENDER]: controlValue}, true);
		executeServerEvent(controlName, event.type, '', false);
	} else if(event.target.id == (MANUAL_PROF)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMP_NAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMP_NAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMP_NAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (FINANCIAL_BROKERS)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (NOTARY_PUBLIC)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (RA_CB_OTHERS)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == ('BN_OTHERS')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == ('RA_CB_GEN_TRDNG_CMPNY')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == ('RA_CB_PRECIOUS_STONE_DEALER')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == ('RA_CB_BULLN_COMMDTY_BROKR')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == ('RA_CB_REAL_STATE_BROKR')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == ('RA_CB_USD_AUTO_DEALER')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_ADDRESS){
		setFieldValue(CP_POBOXNO,getValue(MANUAL_ADDRESS));
	} else if (event.target.id == CPD_RISK_CURRENT_RSK_BANK){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CPD_MODE_OF_FUND)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CPD_TRNSFR_FRM_ACC)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == 'table91_UDF Field') {	  
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == ('LISTVIEW_PUR_ACC_RELATION')) {	
			executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == ('ADDITIONAL_SOURCES_INCOME_AED')) {	
			//AddSourceIncome();
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == 'PRIVATE_CLIENT'){
		setKYCFlagPrivateclient();
	//Added by Shivanshu For TIN Country Field 13/03/25
	}else if (event.target.id == CRS_TAX_COUNTRY) {
		executeServerEvent(event.target.id, event.type, '', false);
	}	
}


function clickEventCPDMakerFourStep(event){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (controlName == BUTTON_SUBMIT){
		var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
		var resultFATCACheck = checkAttchedDocument('FATCA#');
		console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
		var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
		if(getValue(SALARY_TRANSFER) == 'Yes'){
			var salCert = DocTypeAttachedcount('sal_certificate');
			var salTrnsfrLtr = DocTypeAttachedcount('sal_trnsfr_ltr');
			console.log('salCert: '+salCert);
			console.log('salTrnsfrLtr: '+salTrnsfrLtr);
			if(salCert == 0 && salTrnsfrLtr == 0){
				showMessage(SALARY_TRANSFER, 'Please attach the Salary Transfer letter to this Work Item', 'error');
				return;
			}
		}
		// added by reyaz 18-04-2023
		if(getValue('CRS_CERTIFICATION_OBTAINED') =='No' || getValue('CRS_Classification') == 'UPDATED-UNDOCUMENTED'
		    || getValue('CRS_Classification') == 'UNDOCUMENTED' || getValue('CRS_Classification') == ''){ 
		     showMessage('CRS_Classification', 'CRS Classification should be Updated Documented', 'error');
		     return false;
	    }
		if(selectedRows[0] != undefined) {
			executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck+'%%%'+selectedRows[0], false);
		} else {
			executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
		}
//		executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
	}else if(FETCH_INFO == (controlName)) {
		executeServerEvent(FETCH_INFO, event.type,"", false);
	} else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	} else if(controlName == (VIEW)) {	
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(controlName, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(controlName, event.type, '', false);
		}
	} else if (controlName ==  BTN_APP_LEVEL_RISK){
		executeServerEvent(BTN_APP_LEVEL_RISK, EVENT_TYPE.CLICK,'', false);
	} else if (controlName ==  CT_BTN_REFRESH){
		executeServerEvent(CT_BTN_REFRESH, EVENT_TYPE.CLICK,'', false);
	}  else if(event.target.id == BTN_SELECT_CUST) {	
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(event.target.id, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(event.target.id, event.type, '', false);
		}
	} else if (controlName == 'DOC_VIEW_CPD'){
		// pending 
		var wi_name = getWorkItemData('processInstanceId');
		// setTabStyle("tab4",19, "visible", "true");
		setTabStyle("tab4",20, "visible", "true"); // Changes for Family Banking
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var jspURL = sLocationOrigin+'/AO/CustomFolder/?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
		alert(jspURL);
		document.getElementById('PRODUCT_JSP').src=jspURL;
		//	selectSheet('tab4',19);
		selectSheet('tab4',20);// Changes for Family Banking
	}  else if (controlName == BTN_CHK_VIEW_DETAILS){
		var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
		var iSelectedRow = selectedRows[0]; 
		var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
		var iRowCount =  getGridRowCount(CPD_CHK_INT_BLK_LVW);
		var iListViewSelectedRow = selectedRows;
		if(iRowCount==0) {
			alert('NO row in the grid.');
		}
		else if(iRowCount>0 && iListViewSelectedRow.length==0) {
			alert('Please select a row first.');
		} else {
//			int iSelectedRow=iListViewSelectedRow[0];//viewBlackListData.jsp
//			console.log("iSelectedRow---"+iSelectedRow);
//			JSObject objJStemp = formObject.getJSObject();
//			Object sOutput;
//			Object [] ain = {sWorkitemId+"#"+iProcessedCustomer+"#"+iSelectedRow+"#"+"USR_0_BLACKLIST_DATA_CPD"};
//			sOutput = objJStemp.call("viewBlacklist",ain); 
			var wi_name = getWorkItemData('processInstanceId');
			//setTabStyle("tab4",19, "visible", "true");
			setTabStyle("tab4",20, "visible", "true");// Changes for Family Banking
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
			var jspURL = sLocationOrigin+'/AO/CustomFolder/viewBlackListData.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
			alert(jspURL);
			document.getElementById(APPLICATION_ORCHESTRATION_TAB).textContent = 'Application Orchestration';
			document.getElementById('PRODUCT_JSP').src=jspURL;
			//selectSheet('tab4',19);
			selectSheet('tab4',20);// Changes for Family Banking
		}

	} else if (controlName == BTN_CPD_VIEWDETAILS){
		var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
		var iSelectedRow = selectedRows[0]; 
		var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
		var iRowCount =  getGridRowCount("ListView19");
		var iListViewSelectedRow = selectedRows;
		if(iRowCount==0) {
			alert('NO row in the grid.');
		}
		else if(iRowCount>0 && iListViewSelectedRow.length==0) {
			alert('Please select a row first.');
		} else {
//			int iSelectedRow=iListViewSelectedRow[0];//viewBlackListData.jsp
//			console.log("iSelectedRow---"+iSelectedRow);
//			JSObject objJStemp = formObject.getJSObject();
//			Object sOutput;
//			Object [] ain = {sWorkitemId+"#"+iProcessedCustomer+"#"+iSelectedRow+"#"+"USR_0_BLACKLIST_DATA_CPD"};
//			sOutput = objJStemp.call("viewBlacklist",ain); 
			var wi_name = getWorkItemData('processInstanceId');
			//setTabStyle("tab4",19, "visible", "true");
			setTabStyle("tab4",20, "visible", "true"); // Changes for Family Banking
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
			var jspURL = sLocationOrigin+'/AO/CustomFolder/viewBlackListData.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
			alert(jspURL);
			document.getElementById(APPLICATION_ORCHESTRATION_TAB).textContent = 'Application Orchestration';
			document.getElementById('PRODUCT_JSP').src=jspURL;
			//	selectSheet('tab4',19);
			selectSheet('tab4',20); // Changes for Family Banking
		}
	} else if (controlName == EXISTING_NOM_PRSN){
		var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
		var iSelectedRow = selectedRows[0]; 
		executeServerEvent(event.target.id, event.type,iSelectedRow, false);
	} else if (controlName == 'fetch_balance'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == BTN_VIEW){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == 'AccInfo_UdfList'){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == IDS_CB_SAL_TRANSFER){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == CRS_CERTIFICATION_OBTAINED){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == CRS_CB){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == BTN_SELECT_CUST){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == DEL_MODE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == BTN_VIEWDETAILS_SANCT){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == ED_CB_SAL_AED){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == CHECKBOX_VISA_ISSUE_DATE_EIDA){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (controlName == BTN_SI_ADD){
		executeServerEvent(BTN_SI_ADD, event.type,"", false);
	} else if (controlName == BTN_SI_MOD){
		executeServerEvent(BTN_SI_MOD, event.type,"", false);
	} else if (controlName == BTN_DEDUPE_SEARCH){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if(event.target.id == (CRS_ADD)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == BTN_CPD_TRSD_CHK) {
		trsdCheckDone = true;
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (BTN_VALIDATE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (BTN_VALIDATEFATCA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == BTN_FCR_SRCH) {	  
		executeServerEvent(controlName, event.type, '', false);
	}  else if(controlName == DC_BTN_ADD) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'BTN_FG_VALIDATE'){	// Changes for Family Banking
		executeServerEvent(event.target.id, event.type, '', false);
	} 
}

function selectRowHookCPDMakerFourStep(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId) {
		if(getSelectedRowsIndexes(ACC_RELATION).length == 0){
			showMessage(ACC_RELATION, 'Please select any one customer', 'error');
			return false;
		}
		setFieldValue(SELECTED_ROW_INDEX, (getValueFromTableCell(ACC_RELATION, selectedRowsArray[0], 0)-1));
//		var iSelectedRow = getValue(SELECTED_ROW_INDEX);
//		var sName = getValueFromTableCell(ACC_RELATION, iSelectedRow, 1);
//		var sDOB = getValueFromTableCell(ACC_RELATION, iSelectedRow, 5);
//		var sCustId = getValueFromTableCell(ACC_RELATION, iSelectedRow, 2);
//		var controlNames = ['TXT_CUSTOMERNAME','TXT_DOB',TXT_CUSTOMERID];
//		var controlValues = [sName,sDOB,sCustId];
//		setMultipleFieldValues(controlNames,controlValues);
		executeServerEvent('rowSelect', EVENT_TYPE.CHANGE, '', false);
		return false;
	}if(STND_INST_LVW == tableId){
		//si
		var listviewId = tableId;
		var rowIndex = selectedRowsArray[0];
		console.log('row index = '+rowIndex);
		if(rowIndex >= 0){
			if(STND_INST_LVW == listviewId){
				setValues({[SI_DEB_ACC_NO]: getValueFromTableCell(listviewId,rowIndex,0)}, true);
				setValues({[SI_CURRENCY]: getValueFromTableCell(listviewId,rowIndex,1)}, true);
				setValues({[SI_CRED_PROD]: getValueFromTableCell(listviewId,rowIndex,2)}, true);
				setValues({[SI_FRST_PAYMNT]: getValueFromTableCell(listviewId,rowIndex,3)}, true);
				setValues({[SI_LST_PAYMNT]: getValueFromTableCell(listviewId,rowIndex,4)}, true);
				setValues({[SI_PERIOD]: getValueFromTableCell(listviewId,rowIndex,5)}, true);
				setValues({[SI_TRNS_AMT]: getValueFromTableCell(listviewId,rowIndex,6)}, true);
			}
		}else{

			setValues({[SI_DEB_ACC_NO]: ''}, true);
			setValues({[SI_CURRENCY]: ''}, true);
			setValues({[SI_CRED_PROD]:''}, true);
			setValues({[SI_FRST_PAYMNT]: ''}, true);
			setValues({[SI_LST_PAYMNT]: ''}, true);
			setValues({[SI_PERIOD]: ''}, true);
			setValues({[SI_TRNS_AMT]: ''}, true);
		
		}
	}
}

function onClickTabCPDMakerFourStep(tabId,sheetIndex,eventCall){
	console.log('onClickTabCPDMakerFourStep');
	console.log(sheetIndex);
	if(getSelectedRowsIndexes(ACC_RELATION).length == 0){
		showMessage(ACC_RELATION, 'Please select any one customer', 'error');
		return false;
	}
//	if(sheetIndex == 1) {
//		console.log('sheetIndex == 1');
//	} else if (selectedSheetIndex == 10) {
//		addDebitCard();
//	} else if(sheetIndex == 13){
//		manageCategoryChangeSectionCPDChecker();
//	} else if(sheetIndex == 18){
//		if(getValue(CRO_DEC).toLowerCase() == ('approve')) {
//			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
//			var controlnames = [CRO_REJ_REASON];
//			clearAOControls(controlnames);
//		}
//	}
        //added by shivanshu for mandatory alert for eida expiry date
	if(getSheetIndex(tabId) == 2){ 
		mandatoryEidaNo();
	}
	var input = event.target.innerHTML+','+sheetIndex;
	executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	selectSheet(tabId,sheetIndex);
}

function postServerEventHandlerCPDMakerFourStep(controlName, eventType, responseData) {

	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	if (null != jsondata.message && '' != jsondata.message) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			if('' != arr[1] && arr[1] != CA008 
					&& !(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1)) {
				showMessage(arr[0], arr[1], 'error');
			} else if('' != arr[0]) {
				setFocus(arr[0]);
			} 
		} else if(jsondata.message.indexOf('The user is not authorized to access Staff Data.') != -1){
			showMessage('', jsondata.message, 'error');
		}
	}
	switch (eventType) {
		case EVENT_TYPE.LOAD:
		PepNationality();
			setStyle(ACC_RELATION,'disable','false');
			if('' != getValue(SELECTED_ROW_INDEX)) {
				setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
				var iSelectedRow = getValue(SELECTED_ROW_INDEX);
				var sName = getValueFromTableCell(ACC_RELATION, iSelectedRow, 1);
				var sDOB = getValueFromTableCell(ACC_RELATION, iSelectedRow, 5);
				var sCustId = getValueFromTableCell(ACC_RELATION, iSelectedRow, 2);
				var controlNames = ['TXT_CUSTOMERNAME','TXT_DOB',TXT_CUSTOMERID];
				var controlValues = [sName,sDOB,sCustId];
				setMultipleFieldValues(controlNames,controlValues);
			}
			if('' == getValue(RA_SIGN_TYPE)){
				setFieldValue(RA_SIGN_TYPE,'Signature in English');
			}
			managePersonalDetailsOthersFields();
			if(getValue(ED_SET_FLG) == "") {
				//setValues({ED_SET_FLG:"No"},true);
				setFieldValue(ED_SET_FLG, 'No');
			}
			if(getValue(RELIGION) == '') {
				addItemInCombo(RELIGION, 'Others');
				setFieldValue(RELIGION, 'Others');
			}
			var val = getValue(CRS_CERTIFICATION_OBTAINED);
			console.log(val);
			if('Yes' == val){
				console.log('in yes');
				var controlNames = ['CRS_Classification'];
				var controlValues = ['UPDATED-DOCUMENTED'];
				setMultipleFieldValues(controlNames,controlValues);
				disableControls([controlNames]);
			}else {
				console.log('in no');
				var controlNames = ['CRS_Classification'];
				var controlValues = [''];
				setMultipleFieldValues(controlNames,controlValues);
				enableControls([controlNames]);
		    }
			setStyle(SEC_FB, PROPERTY_NAME.DISABLE, 'true');
			break;
		case EVENT_TYPE.CLICK:
			if(controlName == 'saveNextTabClick' && jsondata.success){
//				var input = ','+(getSheetIndex('tab3'));
//				console.log("input: "+input);
				if(saveNextClick){
					saveNextClick = false;
//					executeServerEvent('tabClick', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
				}
				if(getSheetIndex('tab3') == 5) {
					dueDiligenceEnableDisable();
				} 
			} else if(controlName == 'afterSaveNext'){
				var input = ','+(getSheetIndex('tab3'));
				console.log("input: "+input);
				if(getSheetIndex('tab3') == 5) {
					dueDiligenceEnableDisable();
				} 
				executeServerEvent('tabClick', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
			} else if(controlName == 'tabClick') {
				if(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1) {
					var arr = jsondata.message.split('###');
					showConfirmDialog(arr[1], confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('confirmAppRisk', EVENT_TYPE.CHANGE, 'confirmAppRisk', false);
						} else if(result == false) {
							selectSheet('tab3',0);
							return;
						}
					});
				}
//				if(getSheetIndex('tab3') == 18) {
				if(getSheetIndex('tab3') == 19) {
					if(getValue(CRO_DEC) != ('Approve')) {
						setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'false');
					}
				}
			} else if(controlName == VIEW){
				if(jsondata.success && 'dedupeselectedindex' == jsondata.message){
					//write code to set index in grid -sahil
				}
			} else if(controlName == BTN_FCR_SRCH) {
				if(jsonData.success){
					showConfirmDialog(jsonData.message, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('postSubmit_BTN_FCR_SRCH', event.type, 'YES_OPTION', false);
							saveWorkItem();
						} else if(result == false) {
							executeServerEvent('postSubmit_BTN_FCR_SRCH', event.type, 'NO_OPTION', false);
							saveWorkItem();
							return;
						}
					});
				}
			} else if(controlName == BUTTON_SUBMIT){
				if(jsondata.success) {
					if(jsondata.message == CA008 && (getValue(CRO_DEC)=='Return to Originator' 
						|| getValue(CRO_DEC)== 'Return' || getValue(CRO_DEC)==  'Reject')) {
						showConfirmDialog(CA008, confirmDialogButtons, function(result) {
							if(result == true) {
								executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmitOnReturnReject', false);
							} else if(result == false) {
								saveWorkItem();
								return;
							}
						});
					} else if(jsondata.message == CA008) {
						showConfirmDialog(CA008, confirmDialogButtons, function(result) {
							if(result == true) {
								executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);
							} else if(result == false) {
								saveWorkItem();
								return;
							}
						});
					} else if(jsondata.message.indexOf('$$$confirm') != -1) {
						var confirm = jsondata.message.split('$$$');
						showConfirmDialog(confirm[0], confirmDialogButtons, function(result) {
							if(result == true) {
								showConfirmDialog(CA008, confirmDialogButtons, function(result) {
									if(result == true) {
										executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);
									} else if(result == false) {
										saveWorkItem();
										setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
										return;
									}
								});
							} else if(result == false) {
								setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
								setFocus(PRODUCT_QUEUE);
							}
						});
					} else if(jsondata.message == '') {
						saveWorkItem();
						completeWorkItem();
					} else {
						saveWorkItem();
						setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
					}
				} else if(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1) {
					showConfirmDialog(jsondata.message, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'confirmAppRisk', false);
						} else if(result == false) {
							return;
						}
					});
				} else {
					setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
				}
				/*if((jsondata.message).includes('#@$')) {
					var arr = jsondata.message.split('#@$');
					var paramList = arr[0];
					var func = arr[1];
				} else if(jsondata.success && (getValue(CRO_DEC)=='Return to Originator' || getValue(CRO_DEC)== 'Return' 
					|| getValue(CRO_DEC)==  'Reject') && (jsondata.message).includes(CA008)) {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(event.target.id, event.type, 'CA008_Clear', false);
						} else if(result == false) {
							return;
						}
					});
				} else if((jsondata.message).includes('###')  && (jsondata.message).includes(CA008)) { // what condition ?
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(event.target.id, event.type, 'Finally_Decision_Clear', false);
						} else if(result == false) {
							return;
						}
					});
				}else if(jsondata.message.includes('$$$confirm')) { 
					var confirm = jsondata.message.split('$$$');
					showConfirmDialog(confirm[0], confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(event.target.id, event.type, 'FOR_YES@@@'+confirm[2], false);
						} else if(result == false) {
							executeServerEvent(event.target.id, event.type, 'FOR_NO@@@'+confirm[2], false);
						}
					});
				} else if(jsondata.success) {
					saveWorkItem();
					completeWorkItem();
				}*/ /*else if((jsondata.message).includes('###')) { //  what condition ?
					showConfirmDialog("Product with following details already added, Do you still want to add", 
							confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(event.target.id, event.type, 'Product_Clear', false);
						} else if(result == false) {
							return;
						}
					});
				} */
			} else if(event.target.id == (CT_BTN_EIDA_REFRESH)) {
				if (jsondata.success){
					refNo = jsondata.message;
					var jspURL = 'https://adcbeida.adcbapps.local/EIDAFCUBS/login.jsp?channelId=WMSBPM&channelRefNo='+refNo;
					window.open(jspURL,"newWin","location=no,menubar=no,resizable=yes,scrollbars=yes,status=no," +
							"toolbar=no,left=100,top=20"); 
				} 
			} else if (controlName == 'CNTRL_BANK_URL_CPD'){
				// pending 
				var wi_name = getWorkItemData('processInstanceId');
				//setTabStyle("tab4",19, "visible", "true");
				setTabStyle("tab4",20, "visible", "true"); // Changes for Family Banking
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
				var jspURL = sLocationOrigin+'/AO/CustomFolder/logon1.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
				alert(jspURL);
				document.getElementById('PRODUCT_JSP').src=jspURL;
				//selectSheet('tab4',19);
				selectSheet('tab4',20); // Changes for Family Banking
			} else if(controlName == 'BTN_SI_MOD'){
				checkSICountAndModifySIGrid();
			} 
			break;
	}
}
function checkSICountAndModifySIGrid()
{
	console.log('check pd count');
	var selectedRows =getSelectedRowsIndexes(STND_INST_LVW); 
	var iSelectedRow = selectedRows[0];
	console.log('STND_INST_LVW iSelectedRow'+iSelectedRow);
	if(iSelectedRow > -1){
		modifySIGridFromFields();     
	}
	else{
		showMessage(STND_INST_LVW, 'Please select a row to modify', 'error');
	}
}

function modifySIGridFromFields(){
	var selectedRows =getSelectedRowsIndexes(STND_INST_LVW); 
	var iSelectedRow = ''+selectedRows[0];
	//si
	setMultipleTableCellData(STND_INST_LVW,[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': getValue(SI_DEB_ACC_NO)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'1','cellData': getValue(SI_CURRENCY)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'2','cellData': getValue(SI_CRED_PROD)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'3','cellData': getValue(SI_FRST_PAYMNT)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'4','cellData': getValue(SI_LST_PAYMNT)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'5','cellData': getValue(SI_PERIOD)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'6','cellData': getValue(SI_TRNS_AMT)}],true); 
}
function prehookSaveTabCPDMaker(tabId){
	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	console.log('input prehookSaveTabCPDMaker: ' + input);
	console.log('inside prehookSaveTabCPDMaker, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, ','+sheetIndex, false);
}

function onRowClickCPDMakerFourStep(listviewId,rowIndex) {
	if(listviewId == PRODUCT_QUEUE) {
		return true;
	} else {
		return false;
	}
}