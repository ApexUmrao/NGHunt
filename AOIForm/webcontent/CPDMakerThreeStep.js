var saveNextClick = false;
var trsdCheckDone = false;
var aoCustName='';
var aoCID= '';
function onLoadCPDMakerThreeStep(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var controlNames = [TXT_CURRWS,CURR_WS_DETAIL,CRO_DEC,CRO_REMARKS];
	var controlValues = [workstepName,workstepName,'',''];
	setMultipleFieldValues(controlNames,controlValues);
	if(getValue(SELECTED_ROW_INDEX) == '' || getValue(SELECTED_ROW_INDEX) == null ){
		setFieldValue(SELECTED_ROW_INDEX,'0');
	}
	setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
	setStyle(IS_PROD_APP_REQ,PROPERTY_NAME.VISIBLE,'false');
	setStyle('indiciacombo', PROPERTY_NAME.DISABLE, 'true');
	setStyle('FAT_CUST_CLASSIFICATION', PROPERTY_NAME.DISABLE, 'true');	
	setStyle(IDS_PROF_CENTER_CODE, PROPERTY_NAME.DISABLE, 'true');	
	setFieldValue('CRS_CERTIFICATION_OBTAINED','Yes');
	dueDiligenceEnableDisable();
	console.log('cpd maker 3 step');
		console.log('cpd maker 3 step'+SOURCING_CHANNEL);

	if(getValue('SOURCING_CHANNEL') == 'LAPS'){
		console.log('cpd maker 3 step'+getValue('SOURCING_CHANNEL'));
		setFieldValue('LAPS_REFERENCE_NUM',getValue('CHANNEL_REF_NUMBER'));
	}
	var req_type = getValue(REQUEST_TYPE);
	var BagsetOne = [SEC_CAT_CHNG,SEC_INTERNAL_DETL,SEC_ACC_INFO_ECD];
	if(req_type.toUpperCase() == "NEW ACCOUNT WITH CATEGORY CHANGE"  
		|| req_type.toUpperCase() == "CATEGORY CHANGE ONLY") {	
		setTabStyle("tab3",13, "visible", "true");
		var BagsetTwo = [OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE,OLD_CUST_SEGMENT];
		disableControls(BagsetTwo);
		enableControls(BagsetOne);
		if(req_type == "Category Change Only") {
			setStyle(SEC_ACC_INFO_PD,PROPERTY_NAME.DISABLE,"true");
		} 
	}
	setTabStyle("tab3",6, "visible", "false");
	setTabStyle("tab3",8, "visible", "false");
//	var manualControls = ['UAE_PASS_INFO_EMAIL','UAE_PASS_INFO_EIDA','UAE_PASS_INFO_MOBNO'];   // cop changes
//	disableControls(manualControls);
	onLoadCPDMakerThreeStepFunction();
	if(getValue('IS_UAE_PASS_AUTH_DONE') == 'Y'){
		setFieldValue('IS_UAE_PASS_INFO_CLICKED','Y');
	}
//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	var cpdTRSDDecision = getValue('TRSD_DECISION');
	setValues({CPD_TRSD_FINAL_DECISION:cpdTRSDDecision},true);
	openTrsdJspCPD();
	setKYCFlagPrivateclient();
	pepAssesment(); //Dcra
	visiblePreAssesmentField();//AO CCO AND UPGRADE
}

function onLoadCPDMakerThreeStepFunction(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	if(getValue(EMP_CITY) != 'OTHERS'  || getValue(EMP_CITY) == ''){
		setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		//
		if(getValue(EMP_CITY) == ''){
			setFieldValue(EMP_CITY_OTHERS,'');
		}
	} else{
		setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
	if(getValue(EMP_STATE) != 'OTHERS' || getValue(EMP_STATE) == ''){
		setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		if(getValue(EMP_CITY) == ''){
			setFieldValue(EMP_STATE_OTHERS,'');
		}
	} else{
		setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
	
	if(getValue(CUST_PREFIX) == 'Others'){
		setStyle(PD_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	} else {
		setStyle(PD_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	} 
	if(getValue(CHK_EMP_DETAIL) == 'true') {
		 setStyle(ED_NATURE_OF_BUSNS, PROPERTY_NAME.DISABLE, 'false');
	}else {
		 setStyle(ED_NATURE_OF_BUSNS, PROPERTY_NAME.DISABLE, 'true');	
	}
//	if(getValue(CORR_STATE) != 'OTHERS'){
//		setFieldValue(CP_OTHERS,'');
//		setStyle(CP_OTHERS, PROPERTY_NAME.DISABLE, 'true');
//	} else{
//		setStyle(CP_OTHERS, PROPERTY_NAME.DISABLE, 'false');
//	}
//	if(getValue(PERM_STATE) != 'OTHERS'){
//		setFieldValue(PA_OTHERS,'');
//		setStyle(PA_OTHERS, PROPERTY_NAME.DISABLE, 'true');
//	} else{
//		setStyle(PA_OTHERS, PROPERTY_NAME.DISABLE, 'false');
//	}
//	if(getValue(RES_STATE) != 'OTHERS'){
//		setFieldValue(RA_OTHERS,'');
//		setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'true');
//	} else{
//		setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'false');
//	}
	setTabVisible();
}

function handleCPDMakerThreeStepEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventCPDMakerThreeStep(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventCPDMakerThreeStep(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusCPDMakerThreeStepEvent(event);
	}
}

function changeEventCPDMakerThreeStep(event){
	//var input = event.target.innerHTML+','+sheetIndex;
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (controlName == COMBODOC) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(GROUP_TYPE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == (DFC_STATIONERY_AVAIL)) { 
		executeServerEvent(DFC_STATIONERY_AVAIL, event.type,"", false);
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
	else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	}else if (NOM_REQ == (controlName)){
		executeServerEvent(NOM_REQ, event.type,"", false);			
	} else if(EXISTING_NOM_PRSN == (controlName)) {
		executeServerEvent(EXISTING_NOM_PRSN, event.type,"", false);
	}  else if (controlName == MANUAL_MOTHERNAME) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == PD_ANY_CHNG_CUST_INFO) {
		if(controlValue == 'No'){
			var disablefields = [PD_DOB,COR_MAKANI,PERM_MAKANI,RES_MAKANI,
			                     'IDS_BNFT_CB_OTHERS',OTHER_PERM_CITY,
			                     OTHER_RES_CITY];
			disableControls(disablefields);
		}
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == 'CRS_CERTIFICATION_OBTAINED_0' || controlName == 'CRS_CERTIFICATION_OBTAINED_1'){
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
	}else if(controlName == MANUAL_EIDANO) {
		setFieldValue(PD_EIDANO,getValue(MANUAL_EIDANO));
		executeServerEvent(controlName, event.type, '', false);
	}  else if(controlName == EMP_CITY) {
		if(getValue(EMP_CITY) == 'OTHERS'){
			setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}else{
			setFieldValue(EMP_CITY_OTHERS,'');
			setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'true'); 
		}
	} else if(controlName == EMP_STATE) {
		if(getValue(EMP_STATE) == 'OTHERS'){
			setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}else{
			setFieldValue(EMP_STATE_OTHERS,'');
			setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'true');//EMP_STATE_OTHERS
		}
	} else if(controlName == PA_SAMEAS) {
		if(controlValue == '') {
			clearAOControls([OTHER_PERM_CITY]);  //not done in java
		}
		if(controlValue == 'Mailing Address') { //not done in java
			if(getValue(CP_CITY) == 'OTHERS'){
				setStyle(PA_CITY, 'disable', 'true');
				setStyle(OTHER_PERM_CITY, 'disable', 'false');
			}
		}
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == RA_SAMEAS) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CHECKBOX_EIDANO_MANUAL) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == CRS_CERTIFICATION_OBTAINED){
		if('Yes' == getValue(CRS_CERTIFICATION_OBTAINED)){
			var controlNames = ['CRS_Classification'];
			var controlValues = ['UPDATED-DOCUMENTED'];
			setMultipleFieldValues(controlNames,controlValues);
		}else {
			var controlNames = ['CRS_Classification'];
			var controlValues = [''];
			setMultipleFieldValues(controlNames,controlValues);
		}
		executeServerEvent(event.target.id, event.type,"", false);
	} else if(controlName == MANUAL_VISASTATUS) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_PER_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_VISASTATUS) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'fetch_balance') {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == DOC_APPROVAL_OBTAINED) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == COURT_ORD_TRADE_LIC) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == PD_CUSTSEGMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == DRP_RESEIDA) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == UDF_Field) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == UDF_Value) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'table91_UDF Field') {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == EXISTING_ETIHAD_CUST) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == ED_CB_OTHERS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CB_SALE_OF_ASST) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CB_REAL_INC_AED) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CB_INHT_AED) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CB_INVSTMNT_RETN_AED) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CB_SAL_AED) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EMPNAME) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PROFESSION) {	  
		var prof = getValue(PROFESSION);
		if(prof.toUpperCase() == ('OTHERS')) {
			setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, 'true');
		}
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_PREF_RADIO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_BRNCH_COURIER) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_EXCELLENCY_CNTR) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_DELIVERY_MODE) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_STATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_GENDER) {
		//setValues({[CUST_GENDER]: controlValue}, true);
		setFieldValue(CUST_GENDER,controlValue);
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == NEW_CUST_SEGMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == ED_EMP_TYPE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ETIHAD_NO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FCR_COUNTRYBIRTH || controlName == EIDA_COUNTRYBIRTH || controlName == MANUAL_COUNTRYBIRTH) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FAT_US_PERSON) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRO_DEC) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PERM_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CORR_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RES_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRO_REJ_REASON) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CUST_PREFIX) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == SI_DEB_ACC_NO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == NEW_DEL_MODE) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'DEL_MODE_NO') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'DEL_MODE_YES') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FCR_EMPLYR_NAME || controlName == EIDA_EMPLYR_NAME || controlName == MANUAL_EMPLYR_NAME) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_VISANO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_VISASTATUS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RA_IS_CUST_PEP) {	  
           PepNationality();//Dcra	
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RA_IS_UAE_RESIDENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CUST_CRS_BRDR_PAYMENT) {
		if(getValue(controlName) == 'Yes'){
			setStyle('ED_CNTRY_PYMT_RECV', PROPERTY_NAME.DISABLE, 'false');
		} else {
			setFieldValue('ED_CNTRY_PYMT_RECV','');
			setStyle('ED_CNTRY_PYMT_RECV', PROPERTY_NAME.DISABLE, 'true');
		}
	
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EMP_STATUS) {
		var controlNames = [EMP_COUNTRY,EMP_STREET,EMP_CITY,EMP_PO_BOX,EMP_STATE,EMP_STATE_OTHERS,EMP_CITY_OTHERS];
		if(getValue(controlName) != 'Self Employed' && getValue(controlName) != 'Employed'){
			disableControls(controlNames);
			clearAOControls(controlNames);	
		} else {
			enableControls(controlNames);
		}
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == (IS_SALARY_TRANSFER_CAT_CHANGE)||
			controlName == IS_MORTAGAGE_CAT_CHANGE||
			controlName == IS_INSURANCE_CAT_CHANGE||
			controlName == IS_TRB_CAT_CHANGE||
			controlName == IS_OTHERS_CAT_CHANGE||
			controlName == IS_VVIP||
			controlName == IS_PREVILEGE_TP_CAT_CHANGE||
			controlName == IS_ENTERTAINMENT_CAT_CHANGE||
			controlName == IS_SHOPPING_CAT_CHANGE||
			controlName == IS_SPORT_CAT_CHANGE||
			controlName == IS_TRAVEL_CAT_CHANGE||
			controlName == IS_EXCELLENCY_TP_CAT_CHANGE||
			controlName == IS_SALARY_TRANSFER_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(event.target.id == (EXISTING_NOM_PRSN )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (NOM_REQ )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == ('INSTANT_DEL_YES')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == ('INSTANT_DEL_NO')) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == (BRNCH_OF_INSTANT_ISSUE )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == IDS_CB_SAL_TRANSFER || event.target.id == (IDS_CB_MORTGAGES)
			||event.target.id == (IDS_CB_INSURANCE)||event.target.id == (IDS_CB_TRB)
			||event.target.id == (IDS_CB_OTHERS)||event.target.id == (IDS_CB_VVIP)
			||event.target.id == (IDS_PC_CB_TP)||event.target.id == (IDS_PC_CB_ENTERTAINMENT)
			||event.target.id == (IDS_PC_CB_SHOPPING)||event.target.id == (IDS_PC_CB_SPORT)
			||event.target.id == (IDS_PC_CB_TRAVEL)||event.target.id == (IDS_BNFT_CB_TP) ) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_OTH_CB_OTHERS )) {	
		executeServerEvent(event.target.id, event.type, '', false);
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
	} else if(event.target.id == (CHECKBOX_CORR_POB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_STATE_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CNTRY_OF_CORR_FCR)) {	
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
	} else if(event.target.id == (CHECKBOX_CORR_POB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_STATE_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CNTRY_OF_CORR_EIDA)) {	
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
	} else if(event.target.id == (CHECKBOX_PROFESSION_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (controlName == RM_CODE) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == MANUAL_PREFIX) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == PD_EIDANO) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == MANUAL_FIRSTNAME || controlName == MANUAL_LASTNAME 
			|| controlName == MANUAL_NAME){
		executeServerEvent(controlName, event.type, '', false);
	}  else if (controlName == MANUAL_FIRSTNAME || controlName == MANUAL_LASTNAME) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == MANUAL_MOBILE) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == MANUAL_PH) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == INDICIACOMBO ||controlName == FAT_SSN 
			|| controlName == FAT_CUST_CLASSIFICATION || controlName == DATEPICKERCUST
			|| controlName == DATEPICKERW8 || controlName == COMBODOC) {
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == FCR_EMPLYR_NAME || controlName == EIDA_EMPLYR_NAME
			|| controlName == MANUAL_EMPLYR_NAME){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == FCR_RESIDENT || controlName == EIDA_RESIDENT 
			|| controlName == MANUAL_RESIDENT || controlName == FCR_NATIONALITY
			|| controlName == EIDA_NATIONALITY || controlName == MANUAL_NATIONALITY){
		executeServerEvent(controlName, event.type, '', false); 
	} else if (controlName == ED_MONTHLY_INCM){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == ACC_INFO_PG){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == ED_ANNUAL_INC){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == SOURCE_NAME){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == SOURCE_CODE){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == MAKER_NAME){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == MAKER_CODE){
		executeServerEvent(controlName, event.type, '', false);
	}    else if(controlName == RA_CB_OTHERS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}  else if(controlName == RA_CB_GEN_TRDNG_CMPNY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}  else if(controlName == RA_CB_PRECIOUS_STONE_DEALER) {	  
		executeServerEvent(controlName, event.type, '', false);
	}  else if(controlName == RA_CB_BULLN_COMMDTY_BROKR) {	  
		executeServerEvent(controlName, event.type, '', false);
	}  else if(controlName == RA_CB_REAL_STATE_BROKR) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CHK_GEN_INFO) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_RESIDENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRS_ADD) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_STATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRS_CERTI_YES) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRS_CERTI_NO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRS_CHECKBOX) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_OTH_CB_OTHERS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_CB_SAL_TRANSFER) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_CB_MORTGAGES) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_CB_INSURANCE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_CB_TRB) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_CB_OTHERS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_CB_VVIP) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_PC_CB_TP) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_PC_CB_ENTERTAINMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_PC_CB_SHOPPING) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_PC_CB_SPORT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_PC_CB_TRAVEL) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IDS_BNFT_CB_TP) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_CAT_BENEFIT_OTHER) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BRNCH_OF_INSTANT_ISSUE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == NOM_REQ) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EXISTING_NOM_PRSN) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_SALARY_TRANSFER_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_MORTAGAGE_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_TRB_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_OTHERS_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_VVIP) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_PREVILEGE_TP_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_ENTERTAINMENT_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_SHOPPING_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_SPORT_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_TRAVEL_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_EXCELLENCY_TP_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == IS_SALARY_TRANSFER_CAT_CHANGE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CP_CITY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PA_CITY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RA_CITY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PD_CUSTSEGMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == HD_BTN) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EMP_STATUS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CUST_CRS_BRDR_PAYMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RA_IS_UAE_RESIDENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RA_IS_CUST_PEP) {	  
         PepNationality();//Dcra	
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_VISASTATUS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_VISANO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == INDICIACOMBO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FAT_SSN) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FCR_RESIDENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EIDA_RESIDENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_RESIDENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FCR_NATIONALITY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EIDA_NATIONALITY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_NATIONALITY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == NEW_DEL_MODE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_STATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_VISASTATUS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_PER_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == MANUAL_PREFIX) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PD_CUSTSEGMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
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
	} else if(controlName == 'EMP_ADD_CITY') {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'EMP_ADD_EMIRATES') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'EMP_ADD_STATE') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'SALARY_TRANSFER') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CRS_TAX_COUNTRY) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CK_PER_DET) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == (CK_PER_DET) || controlName == (CHK_CONTACT_DET) || controlName == ('chk_passport_de') || controlName == (CHK_INTERNAL_SEC) || controlName == (CHK_GEN_INFO) || controlName == (CHK_EMP_DETAIL) || controlName == (CHK_FUNDING_INFO) || controlName == (CHK_RISK_ASS) || controlName == ('chk_add') || controlName == (CHK_BANKING_RELATION) || controlName == (CHECKBOX_FATCA) || controlName == (DRP_RESEIDA)) {
		console.log('chk');
		executeServerEvent(controlName, event.type, '', false);
		if(getValue(CHK_EMP_DETAIL) == true) {
			if(getValue(PROFESSION) == 'Others' || getValue(PROFESSION) == '') {
				enableControls([ED_OTHER]);
			} else {
					disableControls([ED_OTHER]);
			}
		} 

		if(getValue(CHK_EMP_DETAIL) == true) {
			if(getValue(EMPNAME) == 'Others' || getValue(EMPNAME) == '') {
				enableControls(['ED_EMPNAME']);
			} else {
					disableControls(['ED_EMPNAME']);
			}
		}


	}else if (event.target.id == (PER_INC_TAX_CON_1)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_2)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_3)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'SUB_PERSONAL_TAX_0' || event.target.id == 'SUB_PERSONAL_TAX_1') {
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
	} else if (event.target.id == (MANUAL_PROF)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (FAT_LIABLE_TO_PAY_TAX) || event.target.id == POACOMBO 
			|| event.target.id == FAT_US_PERSON) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_EMP_NAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (EMPNAME)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (NEW_LINK)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (FINANCIAL_BROKERS)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (NOTARY_PUBLIC)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (SOCIAL_MEDIA_INFLUNCER)) {	
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
	}else if (event.target.id == 'PRIVATE_CLIENT'){
		setKYCFlagPrivateclient();
	}else if (event.target.id == ('LISTVIEW_PUR_ACC_RELATION')) {	
			executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == ('ADDITIONAL_SOURCES_INCOME_AED')) {	
			//AddSourceIncome();//Dcra
			executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == 'Customer_PEP_Rad_0' || event.target.id == 'Person_Associated_Rad_0' || event.target.id =='Person_Power_Rad_0'|| event.target.id =='Customer_Entrusted_Man_Rad_0'|| event.target.id =='Customer_Authorized_Rad_0'|| event.target.id =='Customer_Entrusted_Rad_0'||event.target.id == 'Customer_PEP_Rad_1' || event.target.id == 'Person_Associated_Rad_1' || event.target.id =='Person_Power_Rad_1'|| event.target.id =='Customer_Entrusted_Man_Rad_1'|| event.target.id =='Customer_Authorized_Rad_1'|| event.target.id =='Customer_Entrusted_Rad_1') {	
		pepLogic();
	} else if (event.target.id == 'KYC_CUST_BLACKLIST1_0' || event.target.id == 'KYC_BARRIER_0' || event.target.id == 'KYC_ENTITY_SHELL_0'  //Jamshed
	          || event.target.id == 'KYC_CUST_NORTH_KORES_0' || event.target.id == 'kYC_PATNER_UBO_0' ||
			    event.target.id == 'KYC_MANUFACTURING_0' || event.target.id == 'KYC_LEGAL_GAMING_0' || event.target.id == 'KYC_OFFICE_VELLI_0' ||
			    event.target.id == 'KYC_VIRTUAL_CURRENCY_0' || event.target.id == 'KYC_ENTITY_RE_0' || event.target.id == 'KYC_NOMINEE_0' ||			
			    event.target.id == 'KYC_EXCEPTIONAL_0' || event.target.id == 'KYC_CUST_BLACKLIST1_1' || event.target.id == 'KYC_BARRIER_1' || 
				event.target.id == 'KYC_ENTITY_SHELL_1' || event.target.id == 'KYC_CUST_NORTH_KORES_1' || event.target.id == 'kYC_PATNER_UBO_1' ||
			    event.target.id == 'KYC_MANUFACTURING_1' || event.target.id == 'KYC_LEGAL_GAMING_1' || event.target.id == 'KYC_OFFICE_VELLI_1' ||
			    event.target.id == 'KYC_VIRTUAL_CURRENCY_1' || event.target.id == 'KYC_ENTITY_RE_1' || event.target.id == 'KYC_NOMINEE_1' ||			
			    event.target.id == 'KYC_EXCEPTIONAL_1'){
		setPreAssessment();
	//Added by Shivanshu For TIN Country Field 13/03/25
	}else if (event.target.id == CRS_TAX_COUNTRY) {
		executeServerEvent(event.target.id, event.type, '', false);
	}

}

function lostFocusCPDMakerThreeStepEvent(event){
	var input = event.target.innerHTML+','+sheetIndex;
}

function clickEventCPDMakerThreeStep(event){
	var workstepName = getWorkItemData('activityName'); 
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (controlName == BUTTON_SUBMIT){/*
		var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
		var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
		var resultFATCACheck = checkAttchedDocument('FATCA#');
		console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
		executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck+'%%%'+selectedRows, false);*/
			var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
			var resultFATCACheck = checkAttchedDocument('FATCA#');
			console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
			var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
			if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
				showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
				return false;
			} 
			if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
				setStyle("SKIPUAEPASS_REASON","disable", "false");
				showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');
				return false;
			}  if(getValue(skipUAEPass) != true){
				setStyle("SKIPUAEPASS_REASON","disable", "true");
			}
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
		
		
	} else if (controlName ==  CT_BTN_REFRESH){
		executeServerEvent(CT_BTN_REFRESH, EVENT_TYPE.CLICK,'', false);
	} else if (controlName == BTN_SI_ADD){
		executeServerEvent(BTN_SI_ADD, event.type,"", false);
	} else if (controlName == BTN_SI_MOD){
		executeServerEvent(BTN_SI_MOD, event.type,"", false);
	}else if(FETCH_INFO == (controlName)) {
		executeServerEvent(FETCH_INFO, event.type,"", false);
	} else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	} else if(controlName == BTN_FCR_SRCH) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName ==  BTN_APP_LEVEL_RISK){
		executeServerEvent(BTN_APP_LEVEL_RISK, EVENT_TYPE.CLICK,'', false);
	}  else if(controlName == CRS_ADD) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'BtnCropSignature'){
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == BTN_DEDUPE_SEARCH) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(event.target.id == BTN_SELECT_CUST) {	
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(event.target.id, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(event.target.id, event.type, '', false);
		}
	} else if(controlName == CK_PER_DET) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == (VIEW)) {	
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(controlName, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(controlName, event.type, '', false);
		}
	} else if(controlName == COMBODOC) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == SOURCING_CHANNEL) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_CPD_TRSD_CHK) {	
		trsdCheckDone = true;
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DOC_APPROVAL_OBTAINED) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == COURT_ORD_TRADE_LIC) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RM_CODE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DRP_RESEIDA) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == UDF_Field) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == UDF_Value) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_CPD_CNTRL_URL) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == SI_DEB_ACC_NO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_CC_GEN_TEMP) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BUTTON_GENERATE_TEMPLATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CORR_STATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RES_STATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PERM_STATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CUST_PREFIX) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRO_DEC) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRO_REJ_REASON) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PD_CUSTSEGMENT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PD_ANY_CHNG_CUST_INFO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == ED_CB_SAL_AED) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_VIEWDETAILS_SANCT) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'Command60') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PA_SAMEAS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'ADD') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'MODIFY') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_CPD_ADD) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_CPD_MODIFY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_CPD_DELETE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RA_SAMEAS) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CT_BTN_EIDA_REFRESH) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_EIDA_INFO) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == DEL_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == RES_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CORR_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == PERM_CNTRY) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_ECD_VALIDATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'COMMAND42') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == CRO_DEC) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_VALIDATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == BTN_VALIDATEFATCA) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'Combodoc') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FAT_US_PERSON) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == FCR_COUNTRYBIRTH) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == EIDA_COUNTRYBIRTH) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == MANUAL_COUNTRYBIRTH) {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'COMBO44') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'ETIHAD_NO') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'COMBO56') {	  
		executeServerEvent(controlName, event.type, '', false);
	}else if(controlName == 'NEW_CUST_SEGMENT') {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'DEL_DELIVERY_MODE') {	  
		executeServerEvent(controlName, event.type, '', false);
	} if(controlName == BTN_CPD_CNTRL_URL) { 
		var returnVal="";
		var urlDoc = document.URL;
		var jspURL= data;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		jspURL="https://131.5.15.15/blacklist/logon1.jsp?param1=324";
		document.getElementById(APPLICATION_ORCHESTRATION_TAB).textContent = 'Application Orchestration';
		document.getElementById('PRODUCT_JSP').src=jspURL;
		/*setTabStyle("tab3",18, "visible", "true");
		selectSheet("tab3",18);	*/
		setTabStyle("tab3",20, "visible", "true");// Changes for Family Banking
		selectSheet("tab3",20);	
	} else if(controlName == BTN_CHK_VIEW_DETAILS) {
		var wi_name = getWorkItemData('processInstanceId');
		var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
		var iRowCount = getGridRowCount(CPD_CHK_INT_BLK_LVW);
		var iListViewSelectedRow = getSelectedIndex(CPD_CHK_INT_BLK_LVW);

		if(iRowCount==0) {
			showMessage('',"NO row in the grid.",'error');
			return;
		}
		else if(iRowCount>0 && iListViewSelectedRow==0) {
			showMessage('',"Please select a row first.",'error');
			return;
		} else {
			var urlDoc = document.URL;
			var jspURL= data;
			var tableName = 'USR_0_BLACKLIST_DATA_CPD'
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
			var url= sLocationOrigin+"/AO/viewBlackListData.jsp?WI_NAME="+wi_name+"&CUST_NO="+iProcessedCustomer+"&TYPE=INT&SLNO="+iListViewSelectedRow+"&TABLE_NAME="+tableName;
			document.getElementById(APPLICATION_ORCHESTRATION_TAB).textContent = 'Application Orchestration';
			document.getElementById('PRODUCT_JSP').src=jspURL;
			/*setTabStyle("tab3",18, "visible", "true");
			selectSheet("tab3",18);	*/
			setTabStyle("tab3",20, "visible", "true");// Changes for Family Banking
			selectSheet("tab3",20);	
		}
	} else if(controlName == BTN_CPD_VIEWDETAILS) {
		var wi_name = getWorkItemData('processInstanceId');
		var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
		var iRowCount = getGridRowCount(CPD_HD2_LVW);
		var iListViewSelectedRow = getSelectedIndex(CPD_HD2_LVW);

		if(iRowCount==0) {
			showMessage('',"NO row in the grid.",'error');
			return;
		}
		else if(iRowCount>0 && iListViewSelectedRow==0) {
			showMessage('',"Please select a row first.",'error');
			return;
		} else {
			var urlDoc = document.URL;
			var jspURL= data;
			var tableName = 'USR_0_BLACKLIST_DATA_CPD'
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
			var url= sLocationOrigin+"/AO/viewBlackListData.jsp?WI_NAME="+wi_name+"&CUST_NO="+iProcessedCustomer+"&TYPE=INT&SLNO="+iListViewSelectedRow+"&TABLE_NAME="+tableName;
			document.getElementById(APPLICATION_ORCHESTRATION_TAB).textContent = 'Application Orchestration';
			document.getElementById('PRODUCT_JSP').src=jspURL;
			/*setTabStyle("tab3",18, "visible", "true");
			selectSheet("tab3",18);	*/
			setTabStyle("tab3",20, "visible", "true");// Changes for Family Banking
			selectSheet("tab3",20);	
		}
	} else if (controlName == 'PRODUCT_QUEUE.PROD_CODE' || controlName == 'PRODUCT_QUEUE.CURRENCY'){
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CT_BTN_RESETALL) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == BTN_NEXT_CUST) {
	    executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'BTN_CUSTOMER_SEARCH') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CRS_TAX_COUNTRY) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(controlName == DC_BTN_ADD) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'BTN_FG_VALIDATE'){	// Changes for Family Banking
		executeServerEvent(event.target.id, event.type, '', false);
	}	else if(event.target.id == 'FETCH_UAE_PASS_INFO'){	// Changes uae Pass info
		console.log('uae pass info');
		var wi_name = getWorkItemData('processInstanceId');

		if(validateAccRelridForUaePass()){
			saveWorkItem();
			setStyle("CRO_DEC","disable", "false");
			executeServerEvent(event.target.id, event.type, '', false);
			//window.parent.refreshOtherDocumentForAO(wi_name);
//			window.parent.refreshWorkitem();
		} else {
			setFieldValue("CRO_DEC","Reject");
			setStyle("CRO_DEC","disable", "true");
		}
		
	} 
	} 
 //AO_DEL_DELIVERY_MODE


function onClickTabCPDMakerThreeStep(tabId,sheetIndex,eventCall){
	console.log('onClickTabCPDMakerThreeStep');
	console.log(sheetIndex);
	var input = event.target.innerHTML+','+sheetIndex;
	if(getSelectedRowsIndexes(ACC_RELATION).length == 0){
		showMessage(ACC_RELATION, 'Please select any one customer', 'error');
		return false;
	}
	if(sheetIndex == 1) {
		if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
			showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
			return false;
		}
		if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
			setStyle("SKIPUAEPASS_REASON","disable", "false");
			showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');
			return false;
		}  if(getValue(skipUAEPass) != true){
			setStyle("SKIPUAEPASS_REASON","disable", "true");
		}
	}
	//added by shivanshu for mandatory alert for eida expiry date
	if(getSheetIndex(tabId) == 2){ 
		mandatoryEidaNo();
	}
	if(sheetIndex == 5) {
		setFieldValue('CRS_CERTIFICATION_OBTAINED','Yes');
		console.log('sheetIndex == 1');
	} if(sheetIndex == 0) {
		setStyle(ACC_RELATION,'disable','false');
		console.log('sheetIndex == 0');
	} else if(sheetIndex == 2) {
		console.log('sheetIndex == 2');
		if(getValue(PD_ANY_CHNG_CUST_INFO) == 'Yes'){
			var enableField = [CK_PER_DET,CHK_CONTACT_DET,'chk_passport_de',CHK_INTERNAL_SEC,
			                   CHK_GEN_INFO,CHK_EMP_DETAIL,CHK_FUNDING_INFO,CHK_RISK_ASS,'chk_add',
			                   CHK_BANKING_RELATION,CHECKBOX_FATCA,DRP_RESEIDA];
			enableControls(enableField);
		}
	} else if(sheetIndex == 13){
		manageCategoryChangeSectionCPDChecker();
	//} else if (sheetIndex == 18){
	} else if (sheetIndex == 19){	 // Changes for Family Banking
		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
		if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
			setStyle("SKIPUAEPASS_REASON","disable", "false");
			showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');
			return false;
		}  if(getValue(skipUAEPass) != true){
			setStyle("SKIPUAEPASS_REASON","disable", "true");
		}
		if(getValue(CRO_DEC) == ('Approve')) {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
			var controlnames = [CRO_REJ_REASON];
			clearAOControls(controlnames);
		}
		var input = event.target.innerHTML+','+sheetIndex;
		executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	} else if(sheetIndex == 10){
		refreshFrame("SEC_ACC_INFO_PD",true);
	} else {
		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
	}
	if('' != getValue(SELECTED_ROW_INDEX)) {
		setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
	}

	executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	selectSheet(tabId,sheetIndex);
}

function postServerEventHandlerCPDMakerThreeStep(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ("" != jsonData.message && null != jsonData.message
				&& !(jsonData.message.indexOf('Do you still want to proceed with account opening?') != -1)) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
//				showMessage(arr[0], arr[1], 'error');
				if(arr.length >=2 && '' != arr[1] &&
						!(jsonData.message.indexOf('Do you still want to proceed with account opening?') != -1)){
					showMessage(arr[0], arr[1], 'error');
				} else if('' != arr[0]) {
					setFocus(arr[0]);
				} 
			} else if(jsonData.message.indexOf('The user is not authorized to access Staff Data.') != -1){
				showMessage('', jsonData.message, 'error');
			}
		}
			
//			int respose=JOptionPane.showConfirmDialog(null,"Selected passport holder Residents "
//					+ "does not meet conditions,\nHence not allowed to open Account. "
//					+ "Do you still want to proceed with account opening?",null,JOptionPane.YES_NO_OPTION);
//			if(respose==JOptionPane.YES_OPTION){
//				setFieldValue("NIG_CPDMAKER","YES");
//				String updatequery="update USR_0_CUST_TXN set NIGEXCEPTIONCPDMAKER='YES' "
//						+ "Where WI_NAME='"+getValue(sActivityName).toString()+"'";
//				saveDataInDB(updatequery);
//				logInfo("","Updated Successfully");
//			}else {
//				return false;
//			}
		} else if(jsonData.message.indexOf('does not start with 971') != -1 || 
				jsonData.message.indexOf('not of 12 digits') != -1 || 
				jsonData.message.indexOf('not of 11 digits') != -1) {
			var arr = jsonData.message.split('###');
			showMessage('', arr[1], 'error');
		}
		
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		
		setStyle(ED_OTHER, PROPERTY_NAME.DISABLE,TRUE);
		console.log("ACC_RELATION 3 step");
		setStyle(ACC_RELATION,'disable','false');
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		}
		if(controlName == VIEW){
			if(jsonData.success && 'DedupeSelectedIndex' == jsonData.message){
			}
		}
		if(getValue('profession') != 'Others'){
			setStyle(ED_OTHER, PROPERTY_NAME.DISABLE,TRUE);
		}else{
			setStyle(ED_OTHER, PROPERTY_NAME.DISABLE,FALSE);
		}
		if('' == getValue(RA_SIGN_TYPE)){
			setFieldValue(RA_SIGN_TYPE,'Signature in English');
		}
		managePersonalDetailsOthersFields();
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
		break;
	case EVENT_TYPE.CLICK:
		if(controlName == 'saveNextTabClick' && jsonData.success){
//			var input = ','+(getSheetIndex('tab3'));
//			console.log("input: "+input);
			if(saveNextClick){
				saveNextClick = false;
//				executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
			}
			if(getSheetIndex('tab3') == 5) {
				dueDiligenceEnableDisable();
			}//no need
			//clearComparisonFields();
			if(getSheetIndex('tab3') == 2) {
				console.log('CHK_EMP_DETAIL qw');
				if(getValue(CHK_EMP_DETAIL) == true) {
					if(getValue(PROFESSION) == 'Others' || getValue(PROFESSION) == '') {
						enableControls([ED_OTHER]);
					} else {
							disableControls([ED_OTHER]);
					}

					if(getValue(EMPNAME) == 'Others' || getValue(EMPNAME) == '') {
						enableControls(['ED_EMPNAME']);
					} else {
							disableControls(['ED_EMPNAME']);
					}
				
				} 

				
			}  

		} else if(controlName == 'afterSaveNext'){
			var input = ','+(getSheetIndex('tab3'));
			console.log("input: "+input);
			if(getSheetIndex('tab3') == 5) {
				dueDiligenceEnableDisable();
			}
			executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
		} else if(controlName == BTN_FCR_SRCH){
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
		} else if(controlName == 'tabClick'){
	
			if(getSheetIndex('tab3') != 0) {
				mandatoryPreAssesmentField();
			}
			if(getSheetIndex('tab3') == 5) {
				dueDiligenceEnableDisable();
			} else if(getSheetIndex('tab3') == 2) {
				if(getValue(CUST_PREFIX) == 'Others'){
					setStyle(PD_OTHERS, PROPERTY_NAME.DISABLE, 'false');
				}	else {
					setStyle(PD_OTHERS, PROPERTY_NAME.DISABLE, 'true');
				} 
			
//			} else if(getSheetIndex('tab3') == 18) {
		} else if(getSheetIndex('tab3') == 19) {
			if(getValue(CRO_DEC) != ('Approve')) {
					setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'false');
				} 

				if(getValue('IS_UAE_PASS_AUTH_DONE') == ('P')) {
					executeServerEvent('ConfirmAuthDone', EVENT_TYPE.CLICK, input, true);
				//addItemInCombo(CRO_DEC, "Send To UaePass Pending");
				//setFieldValue(CRO_DEC,"Send To UaePass Pending");
				//setValue("CRO_DEC","Send To UaePass Pending");
				//setStyle("CRO_DEC","disable", "true");
////				setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'false');
				}else{
					setStyle("CRO_DEC","disable", "false");
				}
			}
			if(jsonData.message.indexOf('Do you still want to proceed with account opening?') != -1) {
				var arr = jsonData.message.split('###');
				showConfirmDialog(arr[1], confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent('confirmAppRisk', EVENT_TYPE.CHANGE, 'confirmAppRisk', false);
					} else if(result == false) {
						selectSheet('tab3',0);
						return;
					}
				});
			}
		} else if(controlName == ('BtnCropSignature')) {
			if('' != jsonData.data) {
				aoCustName = getValue('TXT_CUSTOMERNAME');
				aoCID = getValue('TXT_CUSTOMERID');
				var arr = jsonData.data.split('$$$');
				var docIndex = arr[0];
				//var docIndex = 4563274;
				var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
				try{
					docFrameRef.reloadapplet(docIndex,false,'');		
				}
				catch(e)
				{
				}
				for(var i=0;i < docFrameRef.document.getElementById('wdesk:docCombo').options.length ; i++)
				{
					var indexInCombo =  docFrameRef.document.getElementById('wdesk:docCombo').options[i].value;
					if(indexInCombo == docIndex) {
						docFrameRef.document.getElementById('wdesk:docCombo').selectedIndex = i;
					} 
				}
				window.parent.ShowDoc();
			}
//			if (jsonData.success){
//				refNo = jsondata.message;
//				var jspURL = 'https://adcbeida.adcbapps.local/EIDAFCUBS/login.jsp?channelId=WMSBPM&channelRefNo='+refNo;
//				window.open(jspURL,"newWin","location=no,menubar=no,resizable=yes,scrollbars=yes,status=no," +
//						"toolbar=no,left=100,top=20"); 
//			} 
		}
		else if (controlName == BUTTON_SUBMIT){
			
			if(jsonData.success) {
				if(jsonData.message == CA008 && (getValue(CRO_DEC)=='Return to Originator' 
					|| getValue(CRO_DEC)== 'Return' || getValue(CRO_DEC)==  'Reject')) {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmitOnReturnReject', false);
						} else if(result == false) {
							saveWorkItem();
							return;
						}
					});
				} else if(jsonData.message == CA008) {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);
//							saveWorkItem();
//							completeWorkItem();
						} else if(result == false) {
							saveWorkItem();
							return;
						}
					});
				} else if(jsonData.message.indexOf('$$$confirm') != -1) {
					var confirm = jsonData.message.split('$$$');
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
				} else if(jsonData.message == '') {
					saveWorkItem();
					completeWorkItem();
				} else {
					saveWorkItem();
					setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
				}
			} else if(jsonData.message.indexOf('Do you still want to proceed with account opening?') != -1) {
				showConfirmDialog(jsonData.message, confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'confirmAppRisk', false);
					} else if(result == false) {
						return;
					}
				});
			} else {
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
			}
		}else if(controlName == 'BTN_SI_MOD')
		{
			checkSICountAndModifySIGrid();
		}
		else if (controlName == 'FETCH_UAE_PASS_INFO')
		{
			saveWorkItem();
		}
		else if (controlName == 'BTN_CPD_TRSD_CHK')  //Added by shivanshu
		{
			openTrsdJspCPD();
		}
		break;
	case EVENT_TYPE.CHANGE:
		 if (controlName == CHECKBOX_EIDANO_MANUAL){ 
			if(getValue(CHECKBOX_EIDANO_MANUAL) == true){
				setStyle('eidano_manual',PROPERTY_NAME.DISABLE, FALSE);
			}else{
				setStyle('eidano_manual',PROPERTY_NAME.DISABLE, TRUE);
			}
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
function saveAndNextPreHookThreeStep(tabid){
	saveNextClick = true;
	var input = event.target.innerHTML+','+(getSheetIndex(tabid));
	var tab = getSheetIndex(tabid);
	if(tab == 0 && getSelectedRowsIndexes(ACC_RELATION).length == 0){
		showMessage(ACC_RELATION, 'Please select any one customer', 'error');
		return false;
	}
	setKYCFlag(); //added by shivanshu for dcra
	setPreAssessment();
	PepNationality();//Dcra
	//added by reyaz ao dcra
	if(getValue('PRIVATE_CLIENT') == 'Y'){
		setStyle('PD_CUSTSEGMENT','disable','true')
	}
	if(getSheetIndex(tabid) == 2){ 
		mandatoryEidaNo();
	}
	if(getSheetIndex(tabid) == 3){ //changes for dcra
		mandatoryMultiDropDownField();
	}
	if(getSheetIndex(tab) == 7) { //changes for dcra
		if(getValue('DCRA_RETRIGGER_FLAG') == 'Y'){
			showMessage('BTN_APP_LEVEL_RISK', 'Please Click on Calculate Application level Risk Button', 'error');
			return false;
		}
	}
	if(getSheetIndex(tabid) == 9) { 
       refreshFrame("SEC_ACC_INFO_PD",true);
	}
	if(getSheetIndex(tabid) == 10) { //changes for dcra
			if(getValue('DCRA_RETRIGGER_FLAG') == 'Y'){
				selectSheet('tab3','6');
			    setStyle('BTN_APP_LEVEL_RISK','disable','false');
	//			return false;
			}
		}
	if(tab == 0){
		var requestType = getValue(REQUEST_TYPE);
		 var requestType = getValue(REQUEST_TYPE); 
		var privateClient = getValue('PRIVATE_CLIENT'); //Added by reyaz 12092023
		if (requestType == ('New Account') || requestType == ('Category Change Only') 
			|| requestType == ('New Account With Category Change') || requestType == ('Family Banking')
		     || requestType == ('Upgrade')){
		 if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
			showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
			return false;
		 } else if(requestType != ('Family Banking') && (privateClient == '' || privateClient ==null) 
		  && requestType != ('Upgrade') && requestType != ('Category Change Only')){
		     showMessage('PRIVATE_CLIENT', 'Please Select Value For Private Client', 'error');
			return false;
		 }else if(requestType != ('Family Banking') && privateClient !='Y'){
			mandatoryPreAssesmentField();	
		} else if(requestType != ('Family Banking') && privateClient =='Y'){
			mandatoryPreAssesmentField();
		} 
		if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
			showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
			return false;
		}
		if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
			setStyle("SKIPUAEPASS_REASON","disable", "false");
			showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');
			return false;
		}  if(getValue(skipUAEPass) != true){
			setStyle("SKIPUAEPASS_REASON","disable", "true");
		}
	}
	}
	if(tab == 1){
		var mob=getValue(CHECKBOX_TELE_MOB_MANUAL);
		console.log('getValue(CHECKBOX_TELE_MOB_MANUAL)',getValue(CHECKBOX_TELE_MOB_MANUAL));
		console.log('mob'+mob);
		if(getValue(CHECKBOX_TELE_MOB_MANUAL)== true) {
			setValues({[CP_MOBILE]: getValue(MANUAL_MOBILE)}, true);
		}
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
	console.log('getSheetIndex(tabid)',getSheetIndex(tabid));
	var trsdDec = getValue(CPD_TRSD_FINAL_DECISION);
	if(tab == 7 && trsdCheckDone == false && !getStyle(BTN_CPD_TRSD_CHK,'isdisabled') 
			&& trsdDec.toUpperCase() != 'RETURNED'){
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

function prehookSaveTabCPDMakerThreeStep(tabId){
	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	console.log('input prehookSaveTabCPDMakerThreeStep: ' + input);
	console.log('inside prehookSaveTabCPDMakerThreeStep, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, sheetIndex, false);
}

function onRowClickCPDMakerThreeStep(listviewId,rowIndex) {
	if(listviewId == PRODUCT_QUEUE) {
		return true;
	} else if(listviewId == ACCINFO_UDF_LIST) {
		return true;
	} 
	else if(listviewId == ACC_RELATION){
		 return true;
	}else {
		return false;
	}
}

function selectRowHookCPDMakerThreeStep(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId) {
		if(getSelectedRowsIndexes(ACC_RELATION).length == 0){
			showMessage(ACC_RELATION, 'Please select any one customer', 'error');
			return false;
		}
		setFieldValue(SELECTED_ROW_INDEX, (getValueFromTableCell(ACC_RELATION, selectedRowsArray[0], 0)-1));
		setManualChecksBlank();
		//clearComparisonFields();
		//clearPersonalData();
		//clearKYCData();
		//clearRiskData();
		executeServerEvent('rowSelect', EVENT_TYPE.CHANGE, '', false);
		return  false;
	}
	if(STND_INST_LVW == tableId){
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

//Jamshed
function setPreAssessment(){
	
	var f1 = getValue('KYC_CUST_BLACKLIST1');
	var f2 = getValue('KYC_BARRIER');
	var f3 = getValue('KYC_ENTITY_SHELL');
	var f4 = getValue('KYC_CUST_HAWALADRA');
	var f5 = getValue('KYC_CUST_NORTH_KORES');
	var f6 = getValue('kYC_PATNER_UBO');
	var f7 = getValue('KYC_MANUFACTURING');
	var f8 = getValue('KYC_LEGAL_GAMING');
	var f9 = getValue('KYC_OFFICE_VELLI');
	var f10 = getValue('KYC_VIRTUAL_CURRENCY');
	var f11 = getValue('KYC_ENTITY_RE');
	var f12 = getValue('KYC_NOMINEE');
	var f13 = getValue('KYC_EXCEPTIONAL');
	var preKYCFlag = getValue('PRE_ASSESMENT_FLAG');
	var privateClient = getValue('PRIVATE_CLIENT');
	if(privateClient == 'Y'){
		if(f1=='Yes'|| f2=='Yes'|| f3=='Yes' || f4=='Yes'|| f5=='Yes'|| f6=='Yes' || f7=='Yes'|| f8=='Yes'|| f11=='Yes'){
			setValue('KYC_EXCEPTIONAL','');	
			setStyle('KYC_EXCEPTIONAL','disable','true');       		
		}else if (f1=='No' && f2=='No'&& f3=='No' && f4=='No'&& f5=='No' && f6=='No' && f7=='No'&& f8=='No'&& f11=='No'){
			setStyle('KYC_EXCEPTIONAL','disable','true');
			if(f9== 'Yes' || f10=='Yes' || f12=='Yes') {
				setFieldValue('PRE_ASSESMENT_FLAG','Yes');		
				setStyle('KYC_EXCEPTIONAL','disable','false');
			}	
		}
	}
}