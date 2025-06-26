function setManualFieldsEnable(){
	console.log('In setManualFieldsEnable' );
	enableControls(DDE_ENABLE);
}

function manualFrameCPDDisable() { //manual_frame_CPD_disable
	var manualFrameCPDDisableFields = [MANUAL_PREFIX,MANUAL_NAME,MANUAL_MOTHERNAME,MANUAL_EIDANO,MANUAL_ADDRESS,MANUAL_CITY,MANUAL_CNTRY,MANUAL_STATE,MANUAL_PER_CNTRY,MANUAL_PH,MANUAL_MOBILE,MANUAL_EMAIL,MANUAL_PASSPORTNO,MANUAL_NATIONALITY,MANUAL_VISANO,MANUAL_PROF,MANUAL_GENDER,MANUAL_EMPLYR_NAME,BTNPROFESSION,BTNEMLOYERNAME];
	disableControls(manualFrameCPDDisableFields);
}

function setResidentWithoutEidaComboQDE(){
	console.log('Calling setResidentWithoutEidaCombo_QDE - Shivam Arora');
	var eidaNo = null;

	if(getValue(CHECKBOX_EIDANO_FCR) == 'true'){
		eidaNo = getValue(FCR_EIDANO);
	}
	else if(getValue(CHECKBOX_EIDANO_EIDA) == 'true'){
		eidaNo = getValue(EIDA_EIDANO);
	}
	else if(getValue(CHECKBOX_EIDANO_MANUAL) == 'true'){
		eidaNo = getValue(MANUAL_EIDANO);
	}
	setStyle(DRP_RESEIDA,PROPERTY_NAME.DISABLE,'false');
	console.log('Eida Value : ' + eidaNo);

	if(eidaNo == '' || eidaNo == null){//if(eidaNo.equals('') || eidaNo.equals(null)){
		var controlNames = [DRP_RESEIDA];
		var controlValues = ['Yes'];
		setMultipleFieldValues(controlNames,controlValues);
	}
	else{
		var controlNames = [DRP_RESEIDA];
		var controlValues = ['No'];
		setMultipleFieldValues(controlNames,controlValues);
	}
}

function secDelAddCPDDisable(){ //Frame48_CPD_Disable();
	var secDelAddCPDDisableFields = [DEL_NAME,DEL_PO_BOX,DEL_ADDRESS,DEL_LANDMARK,DEL_CITY,DEL_STATE,DEL_STATE_OTHER,DEL_CNTRY,DEL_FAX,DEL_ZIP_CODE,DEL_EMAIL,DEL_PREF_LANG,DEL_PH,DEL_MOBILE];
	disableControls(secDelAddCPDDisableFields);
}

function disableCustInfo(){
	console.log('***********disable_custInfo*********');
//	Frame32_CPD_ENable();
//	Frame31_CPD_ENable();
//	Frame30_CPD_ENable();
//	Frame27_CPD_ENable();
//	Frame28_CPD_ENable();
//	Frame21_CPD_ENable();
//	Frame20_CPD_ENable();
//	Frame18_CPD_ENable();
//	Frame22_CPD_ENable();
//	Frame25_CPD_ENable();
	visaFrameDisable();
	personalFrameDisable();
	frame20CPDDisable();
	secInitDetailCPDDisable();
	secGenInfoCPDDisable();
	secEmplymntDetailCPDDisable();
	secFundExpReltnshpCPDDisable();
	secAssessOthInfoCPDDisable();
	secBnkRelUaeOvrsCPDDisable();
	secSignOffCPDDisable();
	enableControls(DISABLE_CUST_INFO_FIELDS);
	makerCheckerDisable();
}

function makerCheckerDisable(){
	disableControls(MAKER_CHECKER_FIELDS);
}

function secBnkRelUaeOvrsCPDDisable(){
	disableControls(SEC_BNK_REL_CPD_DISABLE_FIELDS);
}

function secFundExpReltnshpCPDDisable() {
	disableControls(SEC_FUND_EXP_CPD_DISABLE_FIELDS);
}

function secAssessOthInfoCPDDisable() {
	disableControls(SEC_ASSESS_OTH_CPD_DISABLE_FIELDS);
	//formObject.setNGEnable('FRAME77',false);(verification pending by yamini mam
}

function secSignOffCPDDisable() {
	disableControls(SEC_SIGN_OFF_CPD_DISABLE_FIELDS);
}

function secGenInfoCPDDisable() {
	disableControls(SEC_GEN_INFO_CPD_DISABLE_FIELDS);
}

function secEmplymntDetailCPDDisable() {
	disableControls(SEC_EMPLYMNT_DET_CPD_DISABLE_FIELDS);
}

function visaFrameDisable(){
	console.log('**********visa_frame_disable**********');
	disableControls(VISA_FRAME_FIELDS);
}

function secInitDetailCPDDisable() {
	disableControls(SEC_INIT_DET_CPD_DISABLE_FIELDS);
}

function frame52Disable(){ //no need of this function
	console.log('**********Frame52disable**********');
	var workstepName = getWorkItemData('activityName');
	var disableControlsList = [SOURCING_CHANNEL,SOURCING_CENTER,REQUEST_TYPE,DATA_ENTRY_MODE,FORM_AUTO_GENERATE,ACC_OWN_TYPE,ACC_HOME_BRANCH,WMS_ID];
	disableControls(disableControlsList);
	if(workstepName == WORKSTEPS.ACCOUNT_RELATION){
		//lock controls - Text163, Mask8, cust_id
	}
}

function personalFrameDisable(){
	disableControls(PERSONAL_FRAME_DISABLE_FIELDS);
}

function frame20CPDDisable() {
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK || workstepName == WORKSTEPS.CUST_SCREEN || workstepName == WORKSTEPS.DDE_CUSTOMER_INFO || workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		setStyle('TEXT158',PROPERTY_NAME.DISABLE,'true');
		setStyle('Text95',PROPERTY_NAME.DISABLE,'true');
	}
	else{
		setStyle(CP_OTHERS,PROPERTY_NAME.DISABLE,'true');
		setStyle(RA_OTHERS,PROPERTY_NAME.DISABLE,'true');
	}
	setStyle(PERM_STATE,PROPERTY_NAME.DISABLE,'true');
	setStyle(RES_STATE,PROPERTY_NAME.DISABLE,'true');
	setStyle(RA_STREET,PROPERTY_NAME.DISABLE,'true');
	setStyle(PERM_CNTRY,PROPERTY_NAME.DISABLE,'true');
	setStyle(RES_CNTRY,PROPERTY_NAME.DISABLE,'true');
}

function frame20CPDEnable(){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	var FRAME20_CPD_FIELDS_ENABLED = [RES_STATE,RES_CNTRY,PA_STREET,PA_CITY,PA_VILLAFLATNO,PA_BUILDINGNAME,RA_SAMEAS,CP_TELEOFFICE,CP_OTHERS,CP_STREET,CP_FLOOR,PERM_CNTRY];
	enableControls(FRAME20_CPD_FIELDS_ENABLED);
	if(getValue(CORR_STATE).equalsIgnoreCase('OTHERS')) {
		if(!workstepName == WORKSTEPS.CPD_MAKER) {
			setStyle('TEXT158',PROPERTY_NAME.DISABLE,'false');
		}
		else {
			setStyle(CP_OTHERS,PROPERTY_NAME.DISABLE,'false');
		}
	}
	else {
		if(!workstepName == WORKSTEPS.CPD_MAKER) {
			setStyle('TEXT158',PROPERTY_NAME.DISABLE,'true');
		}
		else {
			setStyle(CP_OTHERS,PROPERTY_NAME.DISABLE,'true');
		}
	}
	if( getValue(RES_STATE)  =='OTHERS') {
		setStyle(PA_OTHERS,PROPERTY_NAME.DISABLE,'false');
	}
	else {
		setStyle(PA_OTHERS,PROPERTY_NAME.DISABLE,'true');
	}
	if(getValue(PERM_STATE).equalsIgnoreCase('OTHERS')) {
		setStyle('Text95',PROPERTY_NAME.DISABLE,'false');
	}
	else {
		setStyle('Text95',PROPERTY_NAME.DISABLE,'true');
	}
	setStyle(PA_SAMEAS,PROPERTY_NAME.DISABLE,'false');
	setStyle(RA_BUILDINGNAME,PROPERTY_NAME.DISABLE,'false');
	setStyle(RA_VILLAFLATNO,PROPERTY_NAME.DISABLE,'false');
	setStyle(RA_CITY,PROPERTY_NAME.DISABLE,'false');
	setStyle(PERM_STATE,PROPERTY_NAME.DISABLE,'false');
	setStyle(RA_STREET,PROPERTY_NAME.DISABLE,'false');
	setStyle(RA_OTHERS,tPROPERTY_NAME.DISABLE,'false');

}

function fillManualDataInBelowFields(pControlName,pControlValue) {
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	console.log('inside fillManualDataInBelowFields ' +workstepName);
	if(pControlName == MANUAL_ADDRESS && pControlValue == getValue(CHECKBOX_CORR_POB_MANUAL )) {
		//setValues({[CP_POBOXNO]: pControlValue}, true);
		setFieldValue(CP_POBOXNO,pControlValue);
	}
	else if(pControlName == MANUAL_CITY && pControlValue == getValue(CHECKBOX_CITY_MANUAL))
	{
		//setValues({[CP_CITY]: pControlValue}, true);
		setFieldValue(CP_CITY,pControlValue);
	}
	else if(pControlName == MANUAL_STATE && pControlValue == getValue(CHECKBOX_STATE_MANUAL))
	{
		setFieldValue(CORR_STATE,pControlValue);
	}
	else if(pControlName == MANUAL_NATIONALITY && pControlValue == getValue(CHECKBOX_NATIONALITY_MANUAL))
	{ 
		if(pControlValue == 'UNITED ARAB EMIRATES')
		{
			setFieldValue(MANUAL_VISASTATUS,'Not Required');
		}
		else
		{
			setFieldValue(MANUAL_VISASTATUS,'');
		}
		//setValues({[CUST_NATIONALITY]: pControlValue}, true);
		setFieldValue(CUST_NATIONALITY,pControlValue);
	}
	else if(pControlName == MANUAL_CNTRY &&  pControlValue == getValue(CHECKBOX_CNTRY_OF_CORR_MANUAL))
	{
		//setValues({[CORR_CNTRY]: pControlValue}, true);
		setFieldValue(CORR_CNTRY,pControlValue);
		if(pControlValue == 'UNITED ARAB EMIRATES')
		{
			setStyle(COR_MAKANI,PROPERTY_NAME.ENABLE,'true');
		}else
		{
			setStyle(COR_MAKANI,PROPERTY_NAME.ENABLE,'false');
		}
	}
	else if(pControlName == MANUAL_PH)
	{
		setFieldValue(CP_PHONENO,pControlValue);
	}
	else if(pControlName == MANUAL_MOBILE)
	{
		setFieldValue(CP_MOBILE,pControlValue);
	}
	else if(pControlName == MANUAL_EMAIL && pControlValue == getValue(CHECKBOX_EMAIL_MANUAL))
	{
		setFieldValue(CP_EMAIL,pControlValue);
	}
	else if(pControlName == MANUAL_PREFIX && pControlValue == getValue(CHECKBOX_PREFIX_MANUAL))
	{
		setFieldValue(CUST_PREFIX,pControlValue);
	}
	else if(pControlName == MANUAL_NAME)
	{
		setFieldValue(PD_FULLNAME,pControlValue);
	}
	else if(pControlName == MANUAL_EIDANO)
	{
		setFieldValue(PD_EIDANO,pControlValue);
	}
	else if(pControlName == MANUAL_MOTHERNAME && pControlValue == getValue(CHECKBOX_MOTHERSNAME_MANUAL))
	{
		setFieldValue(PD_MOTHERMAIDENNAME,pControlValue);
	}
	else if(pControlName == MANUAL_GENDER && pControlValue == getValue(CHECKBOX_GENDER_MANUAL))
	{
		setFieldValue(CUST_GENDER,pControlValue);
	}
	else if(pControlName == MANUAL_PASSPORTNO)
	{
		setFieldValue(HD_PASSPORT_NO,pControlValue);
	}
	else if(pControlName == MANUAL_VISANO)
	{
		setFieldValue(HD_VISA_NO,pControlValue);
	}
	else if(pControlName == MANUAL_PROF &&  pControlValue == getValue(CHECKBOX_PROFESSION_MANUAL))
	{
		setFieldValue(PROFESSION,pControlValue);
	}
	else if(pControlName == MANUAL_PROF)
	{
		log.info('IN IF--select in profession');
	}
	else if(pControlName == MANUAL_PER_CNTRY && getValue(CHECKBOX_COUNTRY_PER_RES_MANUAL) == 'true')
	{
		setFieldValue(RES_CNTRY,pControlValue);
		if(pControlValue == 'UNITED ARAB EMIRATES'){
			if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO){
				setStyle(RES_MAKANI,PROPERTY_NAME.ENABLE,'true');
			}
			if(workstepName == WORKSTEPS.CPD_MAKER){
				setStyle(PERM_MAKANI,PROPERTY_NAME.ENABLE,'true');
			}
		}else{
			if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO)
				setStyle(RES_MAKANI,PROPERTY_NAME.ENABLE,'false');
			if(workstepName == WORKSTEPS.CPD_MAKER)
				setStyle(PERM_MAKANI,PROPERTY_NAME.ENABLE,'false');
		}
	}
	else if(pControlName == MANUAL_RESIDENT && getValue(CHECKMANUAL) == 'true')
	{
		setFieldValue(PERM_CNTRY,pControlValue);
		if(pControlValue == 'UNITED ARAB EMIRATES'){
			if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO){
				setStyle(RES_MAKANI,PROPERTY_NAME.ENABLE,'true');
			}
			if(workstepName == WORKSTEPS.CPD_MAKER){
				setStyle(PERM_MAKANI,PROPERTY_NAME.ENABLE,'true');
			}
		}else{
			if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO){
				setStyle(PERM_MAKANI,PROPERTY_NAME.ENABLE,'false');
			}
			if(workstepName == WORKSTEPS.CPD_MAKER){
				setStyle(RES_MAKANI,PROPERTY_NAME.ENABLE,'false');
			}
		}
	}
}

function fillFCRDataInBelowFields(pControlName,pControlValue){
	var workstepName = getWorkItemData('activityName');
	var wi_name      = getWorkItemData('processInstanceId');
	console.log('inside fillFCRDataInBelowFields ' +workstepName);
	if(pControlName == FCR_ADDRESS && !pControlValue == '' && getValue(CHECKBOX_CORR_POB_FCR) == 'true')
	{
		setFieldValue(CP_POBOXNO,pControlValue);
	}
	else if(pControlName == FCR_CITY && !pControlValue == '' && getValue(CHECKBOX_CITY_FCR) == 'true')
	{
		setFieldValue(CP_CITY,pControlValue);
	}
	else if(pControlName == FCR_STATE && !pControlValue == '' && getValue(CHECKBOX_STATE_MANUAL) =='' && getValue(CHECKBOX_STATE_FCR) == 'true')
	{
		setFieldValue(CORR_STATE,pControlValue);
	}
	else if(pControlName == FCR_CNTRY && !pControlValue == '' && getValue(CHECKBOX_CNTRY_OF_CORR_FCR) == 'true')
	{ 
		setFieldValue(CORR_CNTRY,'Not Required');
	}
	else if(pControlName == RESIDENCY_STATUS && pControlValue == 'Yes' && getValue(MANUAL_CNTRY) == 'UNITED STATES')
	{ 
		if(getValue(MANUAL_CNTRY) == 'UNITED STATES'){
		}
		else
		{
			alert('Residency status is not valid.');
			//setValues({[RESIDENCY_STATUS]: 'No'}, true);
			setFieldValue(RESIDENCY_STATUS,'No');
		}		
	}
	else if(pControlName == FCR_PH && !pControlValue == '')
	{ 
		setFieldValue(CP_PHONENO,pControlValue);
	}
	else if(pControlName == FCR_MOBILE && !pControlValue == '')
	{ 
		setFieldValue(CP_MOBILE,pControlValue);
	}
	else if(pControlName == FCR_EMAIL && !pControlValue == '' && getValue(CHECKBOX_EMAIL_FCR) == 'true')
	{ 
		setFieldValue(PD_OTHERS,pControlValue);
	}
	else if(pControlName == FCR_PREFIX &&  !pControlValue == '' && getValue(CHECKBOX_PREFIX_FCR) == 'true' )
	{
		setFieldValue(CUST_PREFIX,pControlValue);
	}
	else if(pControlName == FCR_NAME &&  !pControlValue == '' )
	{
		setFieldValue(PD_FULLNAME,pControlValue);
	}
	else if(pControlName == FCR_EIDANO &&  !pControlValue == '' )
	{
		setFieldValue(PD_EIDANO,pControlValue);
	}
	else if(pControlName == FCR_NATIONALITY &&  !pControlValue == '' && getValue(CHECKBOX_NATIONALITY_FCR) == 'true')
	{
		if(pControlValue == 'UNITED ARAB EMIRATES'){
			console.log('In side if block visaStatus_fcr ==== ');
			//setValues({[FCR_VISASTATUS]: 'Not Required'}, true);
			setFieldValue(FCR_VISASTATUS,'Not Required');
		}
		else{
			console.log('In side else block visaStatus_fcr ==== ');
			//setValues({[FCR_VISASTATUS]: ''}, true);
			setFieldValue(FCR_VISASTATUS,'');
		}
		setFieldValue(CUST_NATIONALITY,pControlValue);
	}
	else if(pControlName == FCR_MOTHERSNAME &&  !pControlValue == ''  && getValue(CHECKBOX_MOTHERSNAME_FCR) == 'true')
	{
		setFieldValue(PD_MOTHERMAIDENNAME,pControlValue);
	}
	else if(pControlName == FCR_DOB &&  !pControlValue == ''  && getValue(CHECKBOX_DOB_FCR) == 'true')
	{
		setFieldValue(PD_DOB,pControlValue);
	}
	else if(pControlName == FCR_GENDER &&  !pControlValue == ''  && getValue(CHECKBOX_GENDER_FCR) == 'true')
	{
		setFieldValue(CUST_GENDER,pControlValue);
	}
	else if(pControlName == FCR_PASSPORTNO &&  !pControlValue == '')
	{
		setFieldValue(HD_PASSPORT_NO,pControlValue);
	}
	else if(pControlName == FCR_PASSPORTISSDATE &&  !pControlValue == '')
	{
		setFieldValue(HD_PASS_ISS_DATE,pControlValue);
	}
	else if(pControlName == FCR_PASSPORTEXPDATE &&  !pControlValue == '')
	{
		setFieldValue(HD_PASS_EXP_DATE,pControlValue);
	}
	else if(pControlName == FCR_VISANO &&  !pControlValue == '')
	{
		setFieldValue(HD_VISA_NO,pControlValue);
	}
	else if(pControlName == FCR_VISAISSDATE &&  !pControlValue == '')
	{
		setFieldValue(HD_VISA_ISSUE_DATE,pControlValue);
	}
	else if(pControlName == FCR_VISAEXPDATE &&  !pControlValue == '')
	{
		setFieldValue(HD_EXP_DATE,pControlValue);
	}
	else if(pControlName == FCR_PREFESSION &&  !pControlValue == '' && getValue(CHECKBOX_DOB_FCR) == 'true')
	{
		setFieldValue(PROF_CODE,pControlValue);
		setFieldValue(PROFESSION,pControlValue);
	}
	else if(pControlName == FCR_PER_CNTRY &&  !pControlValue == '' &&getValue(CHECKBOX_COUNTRY_PER_RES_FCR) == 'true')
	{
		setFieldValue(RES_CNTRY,pControlValue);
	}
	else if(pControlName == FCR_RESIDENT &&  !pControlValue == '' && getValue('CHECKFCR') == 'true')
	{
		setFieldValue(PERM_CNTRY,pControlValue);
	}
}

function fillEIDADataInBelowFields(pControlName,pControlValue){
	var workstepName = getWorkItemData('activityName');
	var wi_name      = getWorkItemData('processInstanceId');
	console.log('inside fillEIDADataInBelowFields ' +workstepName);
	if(pControlName == EIDA_ADDRESS &&  !pControlValue == '' && getValue(CHECKBOX_CORR_POB_EIDA) == 'true')
	{
		setFieldValue(PERM_CNCP_POBOXNOTRY,pControlValue);
	}
	else if(pControlName == EIDA_CITY &&  !pControlValue == '' && getValue(CHECKBOX_CITY_EIDA) == 'true')
	{
		setFieldValue(CP_CITY,pControlValue);
	}
	else if(pControlName == EIDA_STATE &&  !pControlValue == '' && getValue(CHECKBOX_STATE_EIDA) == 'true')
	{
		setFieldValue(CORR_STATE,pControlValue);
	}
	else if(pControlName == EIDA_CNTRY &&  !pControlValue == '' && getValue(CHECKBOX_CNTRY_OF_CORR_EIDA) == 'true')
	{
		setFieldValue(CORR_CNTRY,pControlValue);
	}
	else if(pControlName == EIDA_PH &&  !pControlValue == '')
	{
		setFieldValue(CP_PHONENO,pControlValue);
	}
	else if(pControlName == EIDA_MOBILE &&  !pControlValue == '')
	{
		setFieldValue(CP_MOBILE,pControlValue);
	}
	else if(pControlName == EIDA_EMAIL &&  !pControlValue == '' && getValue(CHECKBOX_EMAIL_EIDA) == 'true')
	{
		setFieldValue(CP_EMAIL,pControlValue);
	}
	else if(pControlName == EIDA_PREFIX &&  !pControlValue == '' && getValue(CHECKBOX_PREFIX_EIDA) == 'true')
	{
		setFieldValue(CUST_PREFIX,pControlValue);
	}
	else if(pControlName == EIDA_NAME &&  !pControlValue == '' )
	{
		setFieldValue(PD_FULLNAME,pControlValue);
	}
	else if(pControlName == EIDANO_EIDA &&  !pControlValue == '')
	{
		setFieldValue(PD_EIDANO,pControlValue);
		setFieldValue(PD_CUSTSEGMENT2,'Yes');
	}
	else if(pControlName == EIDA_NATIONALITY &&  !pControlValue == '' && getValue(CHECKBOX_NATIONALITY_EIDA) == 'true')
	{
		if(pControlValue == 'UNITED ARAB EMIRATES'){
			console.log('In side if block visaStatus_EIDA ==== ');
			setFieldValue(PD_OTHERS3,'Not Required');
		}
		else{
			console.log('In side else block visaStatus_fcr ==== ');
			setFieldValue(PD_OTHERS3,'');
		}
		setFieldValue(CUST_NATIONALITY,pControlValue);
	}
	else if(pControlName == MOTHERNAME_EIDA &&  !pControlValue == '' && getValue(CHECKBOX_MOTHERSNAME_EIDA) == 'true')
	{
		setFieldValue(PD_MOTHERMAIDENNAME,pControlValue);
	}
	else if(pControlName == EIDA_DOB &&  !pControlValue == '' && getValue(CHECKBOX_DOB_EIDA) == 'true')
	{
		setFieldValue(PD_DOB,pControlValue);
	}
	else if(pControlName == EIDA_GENDER &&  !pControlValue == '' && getValue(CHECKBOX_GENDER_EIDA) == 'true')
	{
		setFieldValue(CUST_GENDER,pControlValue);
	}
	else if(pControlName == PASSPORTNO_EIDA &&  !pControlValue == '')
	{
		setFieldValue(HD_PASSPORT_NO,pControlValue);
	}
	else if(pControlName == PASSPORTISSDATE_EIDA &&  !pControlValue == '' )
	{
		setFieldValue(HD_PASS_ISS_DATE,pControlValue);
	}
	else if(pControlName == EIDA_PASSPORTEXPDATE &&  !pControlValue == '' )
	{
		setFieldValue(HD_PASS_ISS_DATE,pControlValue);
	}
	else if(pControlName == EIDA_VISANO &&  !pControlValue == '')
	{
		setFieldValue(HD_VISA_NO,pControlValue);
	}
	else if(pControlName == EIDA_VISAISSDATE &&  !pControlValue == '')
	{
		setFieldValue(HD_VISA_ISSUE_DATE,pControlValue);
	}
	else if(pControlName == VISAEXPDATE_EIDA &&  !pControlValue == '')
	{
		setFieldValue(HD_EXP_DATE,pControlValue);
	}
	else if(pControlName == EIDA_PREFESSION &&  !pControlValue == '' && getValue(CHECKBOX_PROFESSION_EIDA) == 'true')
	{
		setFieldValue(PROF_CODE,pControlValue);
		setFieldValue(PROF_CODE,pControlValue);//?//PROFESSION
	}
	else if(pControlName == EIDA_PER_CNTRY &&  !pControlValue == '' && getValue(CHECKBOX_COUNTRY_PER_RES_EIDA) == 'true')
	{
		setFieldValue(RES_CNTRY,pControlValue);
	}
	else if(pControlName == EIDA_RESIDENT &&  !pControlValue == '' && getValue(CHECKBOX_COUNTRY_RES_EIDA) == 'true')
	{
		setFieldValue(PERM_CNTRY,pControlValue);
	}

}

function clearKYCData() {
	var CLEARKYCCONTROLS = [GI_ACC_NO_COVERED,ED_OTHER,ED_EMPNAME,ED_EMPLYID,ED_POSITN_DESGNT,ED_LEN_OF_SERVCE,
	                        ED_PHNE_NO,TEXT75,ED_MONTHLY_INCM,ED_ANNUAL_INC,ED_SAL_AED,ED_INVSTMNT_RETN_AED,ED_INHT_AED,
	                        ED_REAL_INC_AED,ED_SALE_OF_ASST,ED_OTHERS,ED_NATURE_OF_BUSNS,TEXT113,TEXT106,TEXT101,TEXT96,
	                        TEXT112,TEXT107,TEXT102,TEXT97,TEXT111,TEXT108,TEXT103,TEXT98,
	                        FERS_EMD_CASH_ATM,FERS_EMD_CHQ_DRFT,FERS_EMD_TRNSFR_ONLN,FERS_EMW_CASH_ATM,FERS_EMW_CHQ_DRFT,
	                        FERS_EMW_TRNSFR_ONLN,FERS_GEO_CNTRY_FINANCL,FERS_NATRE_ACTVTY_FT,BR_BANK_NAME_1,
	                        BR_BANK_NAME_2,BR_BANK_NAME_3,BR_CITY_CNTRY_1,BR_CITY_CNTRY_2,BR_CITY_CNTRY_3,
	                        BR_STAFF_WHO_NAME_STMP,BR_BUSN_GRP_HEAD,BR_LINE_MAN_NAME_STMP,BR_BUSN_UNIT_HEAD,
	                        RA_ADDTNL_CMNT,GI_PURPOSE_ACC_REL,PROFESION,GI_EXST_SINCE,GI_DATE_KYC_PREP,
	                        ED_DATE_OF_JOING,EmpName,ED_EMP_TYPE,ED_SET_FLG,EMP_PH_CODE,PD_ANY_CHNG_CUST_INFO,ED_CB_TML,
	                        ED_CB_NON_TML,ED_CB_SAL_AED,ED_CB_INVSTMNT_RETN_AED,ED_CB_INHT_AED,ED_CB_REAL_INC_AED,
	                        ED_CB_SALE_OF_ASST,ED_CB_OTHERS,SPECIAL_CAT,EMP_STATUS];
	console.log('In ClearKYCData');
	clearAOControls(CLEARKYCCONTROLS);
}

function clearPersonalData(){	
	var workstepName = getWorkItemData('activityName');
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('In ClearPersonalData');
	var CLEARPERSONALCONTROL = [PD_MARITALSTATUSOTHER,PD_MOTHERMAIDENNAME,FCR_SHORTNAME,PD_CUSTRELTYPE,PD_NEWRELTYPE,PD_EIDANO,
	                            PD_FULLNAME,PD_OTHERS,CP_POBOXNO,CP_FLOOR,CP_STREET,CP_EMAIL,OTHER_CORR_CITY,CP_PHONENO,
	                            CP_TELEOFFICE,PA_BUILDINGNAME,PA_VILLAFLATNO,PA_STREET,
	                            OTHER_RESI_CITY,RA_BUILDINGNAME,RA_VILLAFLATNO,RA_STREET,HD_PASSPORT_NO,HD_VISA_NO,
	                            IDS_PROF_CENTER_CODE,IDS_REF_BY_CUST,REF_BY_STAFF,RA_SAMEAS,PA_SAMEAS,
	                            CORR_STATE,CORR_CNTRY,RES_STATE,PA_OTHERS,CP_CITY,PA_CITY,RA_CITY,RES_CNTRY,PERM_STATE,PERM_CNTRY,
	                            MARITAL_STATUS,RELIGION,PD_CUSTSEGMENT,CUST_PREFIX,CUST_NATIONALITY,CUST_GENDER,PD_DOB,PD_DATEOFATTAININGMAT,HD_PASS_TYPE,
	                            MANUAL_VISASTATUS,HD_PASS_ISS_DATE,HD_PASS_EXP_DATE,HD_VISA_ISSUE_DATE,HD_EXP_DATE,RM_CODE,RM_NAME,PROF_CODE,EXCELLENCY_CNTR,
	                            PRO_CODE,IDS_CB_SAL_TRANSFER,IDS_CB_MORTGAGES,IDS_CB_INSURANCE,IDS_CB_TRB,IDS_CB_OTHERS,IDS_CB_VVIP,IDS_PC_CB_TP,IDS_BNFT_CB_TP,IDS_PC_CB_TRAVEL,IDS_PC_CB_SPORT,
	                            IDS_PC_CB_SHOPPING,IDS_PC_CB_ENTERTAINMENT];//IDS_BNFT_CB_TP
	clearAOControls(CLEARPERSONALCONTROL);
	if(workstepName == WORKSTEPS.CPD_MAKER|| workstepName == WORKSTEPS.CPD_CHECKER|| workstepName == WORKSTEPS.CONTACT_CENTER_CPD || 
			workstepName == WORKSTEPS.LEVEL_11 || workstepName == WORKSTEPS.LEVEL_12 || workstepName == WORKSTEPS.LEVEL_13
				|| workstepName == WORKSTEPS.LEVEL_14 || workstepName == WORKSTEPS.CLOSE_REQUEST|| workstepName == WORKSTEPS.RM ||workstepName == WORKSTEPS.WORK_EXIT ||
				workstepName == WORKSTEPS.QUERY || workstepName == WORKSTEPS.MAIL_ROOM || workstepName == WORKSTEPS.BULK_EOD_CHECKER || workstepName == WORKSTEPS.PROD_APP ||
				workstepName == WORKSTEPS.CONTACT_CENTER_TEAM  || workstepName == WORKSTEPS.PROD_APP_CPD || 
				workstepName == WORKSTEPS.COMP_APP || workstepName == WORKSTEPS.DELIVERY_MAKER || workstepName == WORKSTEPS.DELIVERY_CHECKER  || workstepName == WORKSTEPS.PHYSICAL_RECON){
		clearAOControls([CP_OTHERS,RA_OTHERS]);
	}	
	else {
		clearAOControls(['Text158','Text95']);
	}
	console.log('PD_CUSTSEGMENT: '+formObject.getValue(PD_CUSTSEGMENT));
}

function  manageCategoryChangeSectionCPDChecker(){		
	var showManageCPD = [IS_VVIP,IS_OTHERS_CAT_CHANGE,IS_TRB_CAT_CHANGE,IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,
	                     IS_INSURANCE_CAT_CHANGE];
	var hideManageCPD = [IS_EXCELLENCY_TP_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
	                     IS_PREVILEGE_TP_CAT_CHANGE,IS_ENTERTAINMENT_CAT_CHANGE];
	var showManageCPD1 = [IS_MORTAGAGE_CAT_CHANGE,IS_TRAVEL_CAT_CHANGE,IS_SPORT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,
	                      IS_ENTERTAINMENT_CAT_CHANGE,IS_PREVILEGE_TP_CAT_CHANGE,IS_VVIP,IS_OTHERS_CAT_CHANGE,IS_TRB_CAT_CHANGE,
	                      IS_INSURANCE_CAT_CHANGE,IS_SALARY_TRANSFER_CAT_CHANGE];
	var hideManageCPD1 = [IS_EXCELLENCY_TP_CAT_CHANGE];
	if(getValue(NEW_CUST_SEGMENT) == 'Aspire' || getValue(NEW_CUST_SEGMENT) == 'Simplylife')
	{
		showControls(showManageCPD);
		hideControls(hideManageCPD);
	}
	else if(getValue(NEW_CUST_SEGMENT) == 'Emirati Excellency'|| getValue(NEW_CUST_SEGMENT) == 'Excellency'||getValue(NEW_CUST_SEGMENT) == 'Private Clients')
	{
		console.log('INSIDE fardeen CHANGES');
		showControls(showManageCPD1);
		hideControls(hideManageCPD1);
	}
	else if( getValue(NEW_CUST_SEGMENT) == 'Signatory')
	{
		alert('Signatory not allowed. Please select another type.');
		Console.log('b4 read only');
		setFieldValue(NEW_CUST_SEGMENT,'');
		Console.log('in category change--------');
	}	
}

function secAccRelCPDDisable(){//Frame81
	console.log('inside secAccRelCPDDisable');
	var workstepName = getWorkItemData('activityName');
	var disableFields =  [SEARCH_CID,SEARCH_NATIONALITY,SEARCH_DOB,SEARCH_MOB_NO,SEARCH_EIDA_CARDNO];
	var disableFields1 = [HD_NAME,SEARCH_PASS_NO,HD_CREDIT_NO];
	var disableFields2 = [HD_NAME,ACC_INFO_EDC_LVW,PD_CUSTSEGMENT,SEARCH_PASS_NO];
	var disableFields3 = [SEARCH_CID,SEARCH_PASS_NO,SEARCH_MOB_NO,SEARCH_DOB,SEARCH_NATIONALITY];
	var disableFields4 = [SEARCH_ADD_CUST_INFO,BTN_ADD_CUST_INFO,BTN_ADD_MANUALLY,BTN_EIDA_INFO,
	                      BTN_FETCH_EIDA_INFO,BTN_SEARCH_CUSTOMER,BTN_SEARCH_CLEAR,NEW_CUST_FETCH_EIDA];
	disableControls(disableFields);
	if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO || workstepName == 'QDE_Acc_Info_Chk' || workstepName == 'Customer_Screen_QDE' || 
			workstepName == WORKSTEPS.QDE_CUST_INFO){
		disableControls(disableFields2);
	} else if (workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK) {
		disableControls(disableFields3);
	} else {
		disableControls(disableFields1);
	}
	// disable Grid also
	if(workstepName == WORKSTEPS.CPD_MAKER){
		setStyle(OPERATING_INST,PROPERTY_NAME.ENABLE,'true');
	}
	else{
		setStyle(OPERATING_INST,PROPERTY_NAME.DISABLE,'true');
	}
	disableControls(disableFields4);
}

function TopFrame(){
	var TOP_FRAME_FIELDS = [FORM_AUTO_GENERATE,SOURCING_CHANNEL,REQUEST_TYPE,ACC_CLASS,SOURCING_CENTER,DATA_ENTRY_MODE,ACC_HOME_BRANCH,ACC_OWN_TYPE];
	disableControls(TOP_FRAME_FIELDS);
}
//SANAL Constants to be checked by Fardeen
function clearComparisonFields(){
	console.log('in ClearComparisonFields--');
	var COMPARISON_CONTROL = [MANUAL_DOB,MANUAL_PASSPORTISSDATE,MANUAL_PASSPORTEXPDATE,MANUAL_VISAEXPDATE,
	                          MANUAL_VISAISSDATE,CHECKBOX_PREFIX_FCR,CHECKBOX_FULLNAME_FCR,CHECKBOX_SHORTNAME_FCR,
	                          CHECKBOX_DOB_FCR,CHECKBOX_PASSPORT_NO_FCR,CHECKBOX_PASS_ISS_DT_FCR,
	                          CHECKBOX_PASS_EXP_DT_FCR,CHECKBOX_VISA_NO_FCR,CHECKBOX_VISA_ISSUE_DATE_FCR,
	                          CHECKBOX_VISA_EXPIRY_DATE_FCR,CHECKBOX_NATIONALITY_FCR,CHECKBOX_MOTHERSNAME_FCR,
	                          CHECKBOX_EIDANO_FCR,CHECKBOX_CORR_POB_FCR,CHECKBOX_CITY_FCR,CHECKBOX_STATE_FCR,
	                          CHECKBOX_CNTRY_OF_CORR_FCR,CHECKBOX_COUNTRY_PER_RES_FCR,CHECKBOX_TELE_RES_FCR,CHECKBOX_TELE_MOB_FCR,CHECKBOX_EMAIL_FCR,
	                          CHECKBOX_DOB_FCR,CHECKBOX_GENDER_FCR,CHECKBOX_EMP_NAME_FCR,CHECKBOX_PREFIX_EIDA,CHECKBOX_FULLNAME_EIDA,
	                          CHECKBOX_SHORTNAME_EIDA,CHECKBOX_DOB_EIDA,CHECKBOX_PASSPORT_NO_EIDA,CHECKBOX_PASS_ISS_DT_EIDA,
	                          CHECKBOX_PASS_EXP_DT_EIDA,CHECKBOX_VISA_NO_EIDA,CHECKBOX_VISA_ISSUE_DATE_EIDA,
	                          CHECKBOX_VISA_EXPIRY_DATE_EIDA, CHECKBOX_NATIONALITY_EIDA,CHECKBOX_MOTHERSNAME_EIDA,CHECKBOX_EIDANO_EIDA,CHECKBOX_CORR_POB_EIDA,
	                          CHECKBOX_CITY_EIDA,CHECKBOX_STATE_EIDA,CHECKBOX_CNTRY_OF_CORR_EIDA,CHECKBOX_COUNTRY_PER_RES_EIDA,
	                          CHECKBOX_TELE_RES_EIDA,CHECKBOX_TELE_MOB_EIDA,CHECKBOX_EMAIL_EIDA,CHECKBOX_PROFESSION_EIDA,
	                          CHECKBOX_GENDER_EIDA,CHECKBOX_EMP_NAME_EIDA,CHECKBOX_PREFIX_MANUAL,CHECKBOX_FULLNAME_MANUAL,
	                          CHECKBOX_SHORTNAME_MANUAL,CHECKBOX_DOB_MANUAL,CHECKBOX_PASSPORT_NO_MANUAL,
	                          CHECKBOX_PASS_ISS_DT_MANUAL,CHECKBOX_PASS_EXP_DT_MANUAL,CHECKBOX_VISA_NO_MANUAL,
	                          CHECKBOX_VISA_ISSUE_DATE_MANUAL,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,
	                          CHECKBOX_NATIONALITY_MANUAL,CHECKBOX_MOTHERSNAME_MANUAL,CHECKBOX_EIDANO_MANUAL,
	                          CHECKBOX_CORR_POB_MANUAL,CHECKBOX_CITY_MANUAL,CHECKBOX_STATE_MANUAL,
	                          CHECKBOX_CNTRY_OF_CORR_MANUAL,CHECKBOX_COUNTRY_PER_RES_MANUAL,CHECKBOX_TELE_RES_MANUAL,
	                          CHECKBOX_TELE_MOB_MANUAL,CHECKBOX_EMAIL_MANUAL,CHECKBOX_PROFESSION_MANUAL,
	                          CHECKBOX_GENDER_MANUAL,FCR_VISANO,FCR_NATIONALITY,FCR_MOTHERSNAME,FCR_EIDANO,FCR_ADDRESS,
	                          FCR_CITY,FCR_STATE,FCR_CNTRY,FCR_PER_CNTRY,FCR_PH,FCR_MOBILE,FCR_EMAIL,FCR_PREFESSION,
	                          FCR_GENDER,FCR_EMPLYR_NAME,EIDA_PREFIX,EIDA_NAME,EIDA_PASSPORTNO,PASSPORTEXPPLACE_EIDA,
	                          EIDA_VISANO,EIDA_NATIONALITY,EIDA_MOTHERNAME,EIDA_EIDANO,EIDA_ADDRESS,EIDA_CITY,
	                          EIDA_STATE,EIDA_CNTRY,EIDA_PER_CNTRY,EIDA_PH,EIDA_MOBILE,EIDA_EMAIL,
	                          EIDA_PROFESSION,EIDA_GENDER,EIDA_EMPLYR_NAME,MANUAL_NAME,MANUAL_PASSPORTNO,
	                          MANUAL_PASSPORTEXPPLACE,MANUAL_VISANO,MANUAL_MOTHERNAME,MANUAL_EIDANO,MANUAL_ADDRESS,
	                          MANUAL_CITY,MANUAL_PH,MANUAL_MOBILE,MANUAL_EMAIL,MANUAL_PROF,MANUAL_EMPLYR_NAME,FCR_DOB,
	                          EIDA_DOB, EIDA_GENDER,MANUAL_NATIONALITY,MANUAL_STATE,MANUAL_CNTRY,MANUAL_PER_CNTRY,MANUAL_GENDER,
	                          MANUAL_PREFIX,MANUAL_RESIDENT,FCR_FIRSTNAME,
	                          FCR_LASTNAME,FCR_PASSTYPE,FCR_COUNTRYBIRTH,EIDA_FIRSTNAME,EIDA_LASTNAME,
	                          EIDA_PSSTYPE,EIDA_COUNTRYBIRTH,CITYBIRTH_EIDA,MANUAL_FIRSTNAME,MANUAL_LASTNAME,
	                          MANUAL_COUNTRYBIRTH,'CRS_CITYOFBIRTH'];
	//FCR_PASS_EXPIRE,FCR_PASS_ISSUE,FCR_VISA_EXPIRE,FCR_VISA_ISSUE,
	//EIDA_PASS_EXPIRE,EIDA_PASS_ISSUE,EIDA_VISA_EXPIRE,EIDA_VISA_ISSUE,
	//CHECK17,CHECK62,CHECK39,'SERVICE_PACKAGE','ESTATEMENT_FLAG','FCR_RISK',CITYBIRTH_FCR,
	clearAOControls(COMPARISON_CONTROL);
	setDateValue(FCR_DOB,'FCR_DOB');
	setDateValue(FCR_PASSPORTEXPDATE,'FCR_PASS_EXPIRE');
	setDateValue(FCR_PASSPORTISSDATE,'FCR_PASS_ISSUE');
	setDateValue(FCR_VISAEXPDATE,'FCR_VISA_EXPIRE');
	setDateValue(FCR_VISAISSDATE,'FCR_VISA_ISSUE');
	setDateValue(EIDA_DOB,'EIDA_DOB');
	setDateValue(PASSPORTEXPDATE_EIDA,'EIDA_PASS_EXPIRE');
	setDateValue(PASSPORTISSDATE_EIDA,'EIDA_PASS_ISSUE');
	setDateValue(VISAEXPDATE_EIDA,'EIDA_VISA_EXPIRE');
	setDateValue(EIDA_VISAISSDATE,'EIDA_VISA_ISSUE');
}

function clearRiskData(){
	console.log('In ClearRiskData');
	var RISK_DATA=[ED_NATURE_OF_BUSNS,ED_LEN_OF_SERVCE,SIGN_STYLE1,GI_ACC_NO_COVERED,ED_OTHER,ED_EMPNAME,ED_EMPLYID,
	               ED_CB_TML,ED_CB_NON_TML,ED_POSITN_DESGNT,ED_PHNE_NO,ED_MONTHLY_INCM,ED_ANNUAL_INC,ED_CB_SAL_AED,
	               ED_CB_INVSTMNT_RETN_AED,ED_CB_INHT_AED,ED_CB_REAL_INC_AED,ED_CB_SALE_OF_ASST,ED_CB_OTHERS,ED_SAL_AED,
	               ED_INVSTMNT_RETN_AED,ED_INHT_AED,ED_REAL_INC_AED,ED_SALE_OF_ASST,ED_OTHERS,
	               TEXT113,TEXT106,TEXT101,TEXT96,TEXT112,TEXT107,TEXT102,TEXT97,TEXT111,TEXT108,TEXT103,TEXT98,
	               FERS_EMD_CASH_ATM,FERS_EMD_CHQ_DRFT,FERS_EMD_TRNSFR_ONLN,FERS_EMW_CASH_ATM,FERS_EMW_CHQ_DRFT,
	               FERS_EMW_TRNSFR_ONLN,FERS_GEO_CNTRY_FINANCL,FERS_NATRE_ACTVTY_FT,BR_BANK_NAME_1,BR_BANK_NAME_2,
	               BR_BANK_NAME_3,BR_CITY_CNTRY_1,BR_CITY_CNTRY_2,BR_CITY_CNTRY_3,BR_STAFF_WHO_NAME_STMP,
	               BR_BUSN_GRP_HEAD,BR_LINE_MAN_NAME_STMP,BR_BUSN_UNIT_HEAD,RA_ADDTNL_CMNT,GI_EXST_SINCE,
	               GI_DATE_KYC_PREP,ED_DATE_OF_JOING, GI_PURPOSE_ACC_REL,PROFESION,EmpName,ED_EMP_TYPE,
	               ED_SET_FLG,EMP_PH_CODE,PD_ANY_CHNG_CUST_INFO,RA_CARRYNG_EID_CARD,CNTRY_OF_BIRTH,RA_PRPSE_TAX_EVSN,
	               RA_IS_UAE_RESIDENT,RA_IS_CUST_DEALNG_ARMAMNT,RA_IS_CUST_PEP,RA_IS_CUST_DEALNG_HAWALA,FAT_US_PERSON,
	               FAT_LIABLE_TO_PAY_TAX,FAT_SSN,FAT_CUST_CLASSIFICATION,CRS_RES_PERM_ADRS_US,GI_IS_CUST_VIP,HD_THREE,
	               ED_CUST_CRS_BRDR_PAYMENT,RA_IS_CUST_WRKNG_UAE,RA_IS_CUST_WRKNG_NON_UAE,SPECIAL_CAT,EMP_STATUS,
	               POACOMBO,INDICIACOMBO,US_INDICIA_MDM,COMBODOC,DATEPICKERCUST,FATCAOPTION,DATEPICKERW8,
	               CHANGE_IN_FATCA_3WAY_INPUTS,FATCAMAIN,FATCAMINI];
	clearAOControls(RISK_DATA);
}

function setDateValue(sFieldName,sFieldName2){
	var sValue = getValue(sFieldName2);
	if(!sValue == '')
	{
		var temp =sValue.split(' ');
		temp=temp[0].split('-');
		sValue=temp[2]+'/'+temp[1]+'/'+temp[0];
		console.log('date field is ---'+sValue);
		setFieldValue(sFieldName,sValue);
	}
	else
	{
		setFieldValue(sFieldName,'');
	}
}

/*yamini*/
function setAutoFilledFieldsLocked(){
	var disableControlsList = [CP_POBOXNO,CP_CITY,CORR_STATE,CORR_CNTRY,CUST_NATIONALITY,CUST_PREFIX,CUST_GENDER,
	                           CP_PHONENO,CP_EMAIL,CP_MOBILE,PROF_CODE,EMPNAME,PROFESSION,PD_FULLNAME,PD_EIDANO,
	                           PD_DOB,PD_MOTHERMAIDENNAME,HD_PASSPORT_NO,HD_VISA_NO,HD_PASS_ISS_DATE,HD_EXP_DATE,
	                           HD_VISA_ISSUE_DATE,HD_PASSPORT_NO,HD_VISA_ISSUE_DATE,HD_PASS_ISS_DATE,HD_EXP_DATE,
	                           HD_PASS_EXP_DATE,HD_VISA_NO];
	disableControls(disableControlsList);
}

function docApproval(){
	console.log('INSIDE AO_DOC_APPROVAL_OBTAINED Click');
	if('true'.equalsIgnoreCase(getValue(DOC_APPROVAL_OBTAINED))){
		console.log('INSIDE AO_DOC_APPROVAL_OBTAINED true');
		setFieldValue(COURT_ORD_TRADE_LIC,'false');
	}
	else{
		console.log('INSIDE AO_DOC_APPROVAL_OBTAINED false');
		setFieldValue(COURT_ORD_TRADE_LIC,'true');
	}
}

function manageFundTransfer()
{
	console.log('In Transfer Mode');
	var iPrimaryCust = getPrimaryCustomerSNO();
	setMultipleTableCellData(LISTVIEW_CONTRACT_LIMIT,[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': getValue(CL_SERIALNUMBER)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'1','cellData': getValue(CL_COMBO_OPERATION)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'2','cellData': getValue(CL_COMBO_PARTYTYPE)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'3','cellData': getValue(CL_CUSTOMER_NO)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'4','cellData': getValue(CL_COMBO_TYPE)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'5','cellData': getValue(CL_LINKAGEREFNO)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'6','cellData': getValue(CL_PER_CONTRIBUTION)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'7','cellData': getValue(CL_COMBO_AMOUNTTAG)} ],true); 
	var sCustID  = getSelectedRowsDataFromTable('ACC_RELATION.CID',iPrimaryCust,0);		//CHECK COLUMN INDEX
	var sRelation = getSelectedRowsDataFromTable('ACC_RELATION.ACC_RELATION',iPrimaryCust,0); 
	console.log('sRelation----'+sRelation);
	var iRows = getGridRowCount('acc_repeater');	
	var iSelectedRow = getSelectedIndex('acc_repeater');
	console.log('iRows---'+iRows);
	var sMode = getSelectedRowsDataFromTable('PRODUCT_QUEUE.MODE_OF_FUNDING',iSelectedRow, 0);

	if(iRows>1)
	{	
		if(sMode.equals('Transfer - Internal'))
		{
			if(sRelation.equals('JAF'))
			{
				showMessage('', 'Fund Transfer is not allowed for JAF customer', 'error');
				setMultipleTableCellData('PRODUCT_QUEUE.MODE_OF_FUNDING',[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': ''} ],true); //check with repeater index
				return;
			}

		/*	objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', true);
			objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED', true);
			objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.CHEQUE_BOOK_NO', false);
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.CHEQUE_BOOK_NO', false);//garima
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', true);//garima
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED', true);//garima */
			
			setMultipleTableCellData('PRODUCT_QUEUE.CHEQUE_BOOK_NO',[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': ''} ],true); //check with repeater index
			clearAOControls('PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO');
			//CR_Ref_0012
			response = executeServerEvent('PRODUCT_QUEUE.MODE_OF_FUNDING', event.type, sCustID, true);
			var jsonData =handleTFOResponse(response);
			if (jsonData.success){
				var sDebitAccNo = jsonData.message();
			}

			if(!sDebitAccNo.equals(''))
			{
				var sTemp = sDebitAccNo.split(',');

				for(var i=0;i<sTemp.length;i++)
				{
					addItemInCombo('PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', sTemp[i]);

				}
				setMultipleTableCellData('PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO',[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': ''} ],true); //check with repeater index
			}
		}
		else if(sMode.equalsIgnoreCase('Cheque'))
		{
		/*	objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.CHEQUE_BOOK_NO', true);
			objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', false); check
			objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED', false);
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.CHEQUE_BOOK_NO', true);//garima
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', false);//garima
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED', false);//garima */
			setMultipleTableCellData('PRODUCT_QUEUE',[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': ''},
			                                                  {'rowIndex':iSelectedRow,'colIndex':'1','cellData': ''},
			                                                  {'rowIndex':iSelectedRow,'colIndex':'2','cellData': ''},
			                                                  {'rowIndex':iSelectedRow,'colIndex':'3','cellData':''}],true);
		/*	objChkRepeater.setValue(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.FROM_ACC_BAL','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.TRNSFR_FROM_CURRENCY','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO',''); */
		}
		else
		{
		/*	objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.CHEQUE_BOOK_NO', false);
			objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', false);
			objChkRepeater.setEnabled(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED', false);
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.CHEQUE_BOOK_NO', false);//garima
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO', false);//garima
			objChkRepeater.setEditable(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED', false);//garima */
			setMultipleTableCellData('PRODUCT_QUEUE',[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': ''},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'1','cellData': ''},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'2','cellData': ''},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'3','cellData':''} ],true);
			/*objChkRepeater.setValue(iSelectedRow, 'PRODUCT_QUEUE.AMT_TRNSFERED','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.FROM_ACC_BAL','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.TRNSFR_FROM_CURRENCY','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.CHEQUE_BOOK_NO','');
			objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO',''); */

			if(sRelation.equalsIgnoreCase('JAF') && sMode.equalsIgnoreCase('Transfer-External'))
			{
				showMessage('', 'Fund Transfer is not allowed for JAF customer', 'error');
				setMultipleTableCellData('PRODUCT_QUEUE.MODE_OF_FUNDING',[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': ''} ],true); //check with repeater index
				//objChkRepeater.setValue(iSelectedRow,'PRODUCT_QUEUE.MODE_OF_FUNDING','--Select--');
			}
		}
	}
}

function setTabVisible(){

	var workstepName = getWorkItemData('activityName');
	if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO) {
		selectSheet('tab3',1);// need to ask 
		var ddeCustFields = [FAT_CUST_CLASSIFICATION,INDICIACOMBO];
		disableControls(ddeCustFields);
	} else if(workstepName == WORKSTEPS.CUST_SCREEN ||workstepName == WORKSTEPS.CUSTOMER_SCREEN_QDE ) {
		selectSheet('tab4',5);
		selectSheet('tab3',6)
	} else if(workstepName == WORKSTEPS.PROD_APP || workstepName == WORKSTEPS.CONTACT_CENTER_TEAM 
			||workstepName == WORKSTEPS.PROD_APP_CPD || workstepName == WORKSTEPS.COMP_APP) {
		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab4',7, PROPERTY_NAME.VISIBLE, 'false');
		}
		else {
			setTabStyle('tab4',7, PROPERTY_NAME.VISIBLE, 'true');
		}
		if(workstepName == WORKSTEPS.COMP_APP ) {
		}				
		else {
			setTabStyle('tab4',9, PROPERTY_NAME.VISIBLE, 'true');
		}
		setTabStyle('tab4',5, PROPERTY_NAME.VISIBLE, 'false');
		var sFields = [SEC_CI,SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO];
		disableControls(sFields);
		formObject.setNGEnable('Frame23' , false);//Frame23 is in sanction screening
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK) {
		var ddeFields = [SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,SEC_INTERNAL_DETL,PRODUCT_QUEUE,FRAME58_LVW,
		                 BUTTON_REFRESH,BUTTON_RETRY];
		disableControls(ddeFields);
		//Frame23_CPD_Disable();//frame23 is not available
		//setStyle('Frame34' ,PROPERTY_NAME.ENABLE, "false");//frame34 is not available
		if(!(getValue(CHANNEL_TYPE) == 'Alternate')) {
			setStyle(SEC_DEL_INST,PROPERTY_NAME.DISABLE ,'true');
		}
		else if(getValue(CHANNEL_TYPE) == 'Alternate') {
			setStyle(SEC_DEL_INST,PROPERTY_NAME.DISABLE ,'false');
		}

		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab3',11, PROPERTY_NAME.VISIBLE, 'false');
		}
		else {
			setTabStyle('tab3',11, PROPERTY_NAME.VISIBLE, 'true');
		}
	} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
		console.log('QDE_ACC_INFO_CHK Name');
		var qdeFields = [SEC_CAT_CHNG,SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,SEC_DEL_INST,SEC_INTERNAL_DETL,BUTTON_REFRESH,BUTTON_RETRY];
		disableControls(qdeFields);
		var qdefields1 = [SEC_ADD_NEW_CUSTOMER,LVW_DEDUPE_RESULT,'QDE_KYC','QDE_KYC_CLIENT',FRAMEFATCA,SEC_CRS_DETAILS,LVW_CRS_TAX_COUNTRY,
		                  SEC_CRS_DETAILS,LVW_CRS_TAX_COUNTRY,SEC_SS_RISK_ASSESS,SEC_ACC_INFO_PD,SEC_ACC_INFO_AOR_MAKER,
		                  SEC_INTERNAL_DETL,'frame164','frame173',BTN_NEXT_CUST_SANCT,CHQ_ELIGIBILITY,TRSD_REMARKS,
		                  BTN_DEDUPE_SEARCH,BTN_TRSD_CHECK,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,CRS_CB,CHQ_ELIGIBILITY];
		disableControls(qdefields1);
		//disableControls([SEC_CAT_CHNG]);
		//formObject.setNGEnable('Frame79' , true);//frame79 hidden

		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab4',6, PROPERTY_NAME.VISIBLE, 'false');
		}
		else {
			console.log('REQUEST_TYPE:' + REQUEST_TYPE);
			setTabStyle('tab4',6, PROPERTY_NAME.VISIBLE, 'true');
		}
	} else if(workstepName == WORKSTEPS.APP_ASSESSMENT) {
		selectSheet('tab4',0);
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab3',11, PROPERTY_NAME.VISIBLE, 'false');
		} else {
			setTabStyle('tab3',11, PROPERTY_NAME.VISIBLE, 'true');
			setStyle(BTN_ACC_INFO_GEN_TEMP,PROPERTY_NAME.DISABLE,'true');
		}
		var sValue = getValue('form_auto_generate');
		if(sValue == 'Yes') {
			setStyle(BTN_ACC_INFO_GEN_TEMP,PROPERTY_NAME.VISIBLE,'true');
			setTabStyle('tab3',13, PROPERTY_NAME.VISIBLE, 'true');
		}
		else {
			setStyle(BTN_ACC_INFO_GEN_TEMP,PROPERTY_NAME.VISIBLE,'false');
		}
		//Frame81_CPD_Disable();
		//Frame23_CPD_Disable();
		var ddeFields = [SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO,OLD_CUST_SEGMENT,OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE];
		disableControls(ddeFields);
		setTabStyle('tab3',1, PROPERTY_NAME.DISABLE, 'true');
		//formObject.setEnable('Frame4', false);// full customer info tab
		selectSheet('tab3',9);
		setStyle(BTN_ACC_INFO_GEN_TEMP,PROPERTY_NAME.VISIBLE,'true');
		setStyle(BTN_ACC_INFO_GEN_TEMP,PROPERTY_NAME.VISIBLE,'true');

		if(getValue(REQUEST_TYPE) == 'Category Change Only' || getValue(REQUEST_TYPE) == 'New Account with Category Change') {
			setTabStyle('tab3',9, PROPERTY_NAME.DISABLE, 'true');//Frame41
		}
	} else if(workstepName == WORKSTEPS.CPD_MAKER) {
		secAccRelCPDDisable();
		setStyle(SEARCH_EIDA_CARDNO,PROPERTY_NAME.DISABLE,'false');
		selectSheet('tab4',0);
		var cpdFields = [OLD_CUST_SEGMENT,OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE];
		disableControls(cpdFields);
		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab4',11, PROPERTY_NAME.VISIBLE, 'false');
		}
		else {
			setTabStyle('tab4',11, PROPERTY_NAME.VISIBLE, 'true');
		}
	}

	if(workstepName == WORKSTEPS.CPD_CHECKER || workstepName == WORKSTEPS.BULK_EOD_CHECKER || workstepName == WORKSTEPS.MAIL_ROOM||workstepName == WORKSTEPS.RM ||workstepName == WORKSTEPS.QUERY ||workstepName == WORKSTEPS.WORK_EXIT ||workstepName == WORKSTEPS.DELIVERY_MAKER || workstepName == WORKSTEPS.DELIVERY_CHECKER  || workstepName == WORKSTEPS.PHYSICAL_RECON) {
		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab4',11, PROPERTY_NAME.VISIBLE, 'false');
		}
		else {
			setTabStyle('tab4',11, PROPERTY_NAME.VISIBLE, 'true');
		}
		console.log("setTabVisible workstepName: "+workstepName);
		if(workstepName == WORKSTEPS.MAIL_ROOM) {
			setStyle(SEARCH_EIDA_CARDNO,PROPERTY_NAME.DISABLE,'true');
			var mailFields = [SEC_CI,SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO];
			disableControls(mailFields);
			setStyle('Frame23',PROPERTY_NAME.DISABLE,'true');//sanction screening
		}
	}
	if(workstepName == WORKSTEPS.QDE_CUST_INFO) {
//		if(getValue(REQUEST_TYPE) == 'New Account') {
//			setTabStyle('tab4',11, PROPERTY_NAME.VISIBLE, 'false');
//		}
//		else {
//			setTabStyle('tab4',11, PROPERTY_NAME.VISIBLE, 'true');
//		}
		selectSheet('tab4',1);
		var qdeAccInfoFieldsEnable = [SEC_OPT_PROD_CRO,SEC_DOC_REQ_CRO];
		var qdeAccInfoFieldsDisable = [SEC_DEL_INST,OLD_CUST_SEGMENT,OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE];
		enableControls(qdeAccInfoFieldsEnable);
		disableControls(qdeAccInfoFieldsDisable);
	}

	if(workstepName == WORKSTEPS.LEVEL_11 || workstepName == WORKSTEPS.LEVEL_12 || workstepName == WORKSTEPS.LEVEL_13 || workstepName == WORKSTEPS.LEVEL_14 ) {

		if(getValue(REQUEST_TYPE) == 'New Account') {
			setTabStyle('tab4',12, PROPERTY_NAME.VISIBLE, 'false');
		}
		else {
			setTabStyle('tab4',12, PROPERTY_NAME.VISIBLE, 'true');
		}
	}
	//TopFrame();
}

function getPrimaryCustomerSNO() {
	var iRows = getGridRowCount(ACC_RELATION);
	var sAccRelation = '';
	var sNo = '';
	if(iRows == 0) {
		sNo = '0';
	}
	else
	{
		for(var i=0;i<iRows;i++)
		{	
			sAccRelation = getSelectedRowsDataFromTable(ACC_RELATION,iRows,9);

			if(sAccRelation.equals('SOW'))
			{
				sNo='1';
				break;
			}					
			else if(sAccRelation.equals('JAF')||sAccRelation.equals('JOF')||sAccRelation.equals('Minor'))
			{
				sNo= getSelectedRowsDataFromTable(ACC_RELATION,i,0); 
				break;
			}
		}
	}
	return sNo;
}

//yamini

function toggleCheckboxes(sControl1, sControl2) {//toggleCheckbox_2
	var controlValues = [FALSE];
	var controlNames;
	if(getValue(sControl1) == TRUE) {
		controlNames = [sControl2];
	} else {
		controlNames = [sControl1];
	}	
	setMultipleFieldValues(controlNames, controlValues);		
}

function addDebitCard() {
	var sProductType = getValue(ACC_INFO_PG); 
	var Grptype = getValue(GROUP_TYPE);
	var CardType = getValue(CARD_TYPE);
	var EmbossName = getValue(EMBOSS_NAME);
	var NewLink = getValue(NEW_LINK);
	var existingCardNo = getValue(AO_EXISTING_CARD_NO);

	if(sProductType == '') {
		showMessage(ACC_INFO_PG,'Please Select Product Type', 'error');
		return;
	}
	if(Grptype == '') {
		showMessage(GROUP_TYPE,'Please Select Group Type', 'error');
		return;
	}
	if(NewLink == '') {
		showMessage(NEW_LINK,'Please Select New/Link', 'error');
		return;
	}
	if(NewLink == ('New')) {
		if(CardType == '') {
			showMessage(CARD_TYPE,'Please Select Card Type', 'error');
			return;
		} 
		if(EmbossName == '') {
			showMessage(EMBOSS_NAME,'Please fill Emboss Name', 'error');
			return;
		}
	} else {
		if(existingCardNo == '') {
			showMessage(AO_EXISTING_CARD_NO,'Please Select Exisiting Card No', 'error');
			return;
		}
	}
	var iRows = getGridRowCount(QUEUE_DC);
	if(NewLink == ('Link')) {
		for(var i=1; i<iRows; i++) {
			if(sProductType == (getValueFromTableCell(QUEUE_DC, i, 0)) 
					&& NewLink == (getValueFromTableCell(QUEUE_DC, i, 4)) 
					&& existingCardNo == (getValueFromTableCell(QUEUE_DC, i, 5))) {
				showMessage(null, 'This mapping is already available', 'error');
				return;
			}
		}
	}
	var sno = 1;
	if(NewLink == ('New')) {
		for(var i=1; i<iRows; i++) {
			if('New' == (getValueFromTableCell(QUEUE_DC, i, 4))) {
				sno = sno+1;
			}
		}
	}
	setTableCellData(QUEUE_DC, iRows, 1, Grptype, 'false');
	setTableCellData(QUEUE_DC, iRows, 2, CardType, 'false');
	setTableCellData(QUEUE_DC, iRows, 0, sProductType, 'false');
	setTableCellData(QUEUE_DC, iRows, 3, EmbossName, 'false');
	setTableCellData(QUEUE_DC, iRows, 4, NewLink, 'false');
	setTableCellData(QUEUE_DC, iRows, 5, existingCardNo, 'false');
	setTableCellData(QUEUE_DC, iRows, 7, sWorkitemId, 'false');
	setRowsDisabled(QUEUE_DC, [iRows]);
	if(NewLink == ('New')) {
		setTableCellData(QUEUE_DC, iRows, 6, 'CARD_'+sno, 'false');
	}
	var controlNames = [ACC_INFO_PG, GROUP_TYPE, CARD_TYPE, EMBOSS_NAME, NEW_LINK, AO_EXISTING_CARD_NO];
	clearAOControls(controlNames);	
}

function setEIDA() {
	var eidaNo = getValue(PD_EIDANO);
	var enable = [RA_CARRYNG_EID_CARD];
	enableControls(enable);
	if('' == eidaNo){
		var controlNames = [RA_CARRYNG_EID_CARD, DRP_RESEIDA];
		var controlValues = ['No', 'Yes'];
		setMultipleFieldValues(controlNames, controlValues);
	} else {
		var controlNames = [RA_CARRYNG_EID_CARD, DRP_RESEIDA];
		var controlValues = ['Yes', 'No'];
		setMultipleFieldValues(controlNames, controlValues);
	}
}
function setManualChecksBlank() {
	var controlNames = [CHECKBOX_PREFIX_MANUAL,CHECKBOX_SELECTALL_MANUAL,CHECKBOX_STATE_MANUAL,CHECKBOX_FULLNAME_MANUAL,	                    CHECKBOX_CITY_MANUAL,CHECKBOX_CORR_POB_MANUAL,CHECKBOX_EIDANO_MANUAL,CHECKBOX_MOTHERSNAME_MANUAL,
                   CHECKBOX_CNTRY_OF_CORR_MANUAL,CHECKBOX_COUNTRY_PER_RES_MANUAL,CHECKBOX_TELE_RES_MANUAL,	                    CHECKBOX_TELE_MOB_MANUAL,CHECKBOX_EMAIL_MANUAL,CHECKBOX_DOB_MANUAL,CHECKBOX_PASSPORT_NO_MANUAL,
                    CHECKBOX_PASS_ISS_DT_MANUAL,CHECKBOX_PASS_EXP_DT_MANUAL,CHECKBOX_NATIONALITY_MANUAL,CHECKBOX_VISA_NO_MANUAL,	                    CHECKBOX_VISA_ISSUE_DATE_MANUAL,CHECKBOX_VISA_EXPIRY_DATE_MANUAL,
                    CHECKBOX_PROFESSION_MANUAL,CHECKBOX_GENDER_MANUAL,CHECKBOX_COUNTRY_RES_MANUAL,
                    CHECKBOX_FIRSTNAME_MANUAL,CHECKBOX_LASTNAME_MANUAL,CHECKBOX_PASSPORT_TYPE_MANUAL,CHECKBOX_COB_MANUAL,HD_SIX];
	var controlValues = [FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,
	                     FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,
	                     FALSE,FALSE,FALSE,FALSE,FALSE];
	setMultipleFieldValues(controlNames, controlValues);
}

function DocTypeAttachedcount(sDocTypeNames) {
	var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
	var arrAvailableDocList = docFrameRef.document.getElementById('wdesk:docCombo');
	// alert("available doc list"+arrAvailableDocList);
	var arrSearchDocList = sDocTypeNames.split("#");
	// alert("available arrSearchDocList list"+arrSearchDocList);
	var bResult=0;
	//alert("available arrSearchDocList listvvvvvvvvvvvvvv"+arrSearchDocList[0]);
	for(var iDocCounter=0;iDocCounter<arrAvailableDocList.length;iDocCounter++) {
		//alert("arrAvailableDocList-->"+arrAvailableDocList[iDocCounter].text);
		if(arrAvailableDocList[iDocCounter].text.substring(0,arrAvailableDocList[iDocCounter].text.indexOf("("))
				.toUpperCase()==arrSearchDocList[0].toUpperCase()) {
			//alert("arrAvailableDocList-->"+arrAvailableDocList[iDocCounter].text.substring(0,arrAvailableDocList[iDocCounter].text.indexOf("(")).toUpperCase());
			bResult = bResult+1;
		}
	}
	// alert('dsfsdf no of instant welcome letter'+bResult);
	return bResult;
}

function checkAttchedDocument(sDocName) {
	return DocTypeAttached(sDocName,1);
}

function DocTypeAttached(sDocTypeNames,Mandatory) {
	var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
	var arrAvailableDocList = docFrameRef.document.getElementById('wdesk:docCombo');
	var arrSearchDocList = sDocTypeNames.split("#");
	var bResult=false;
	if(Mandatory) {
		for(var iSearchCounter=0;iSearchCounter<arrSearchDocList.length;iSearchCounter++) {
			bResult=false;
			for(var iDocCounter=0;iDocCounter<arrAvailableDocList.length;iDocCounter++) {
				if(arrAvailableDocList[iDocCounter].text.toUpperCase()
						.indexOf(arrSearchDocList[iSearchCounter].toUpperCase())>=0)  {
					bResult = true;
				}
			}
			if(!bResult) {
				return bResult;
			}
		}
	}
	return true;
}

function dueDiligenceEnableDisable() {
	if (getValue(SUB_PERSONAL_TAX) == 'Yes') {
		setStyle(PER_INC_TAX_CON_1, 'disable', 'false');
	} else {
		setStyle(PER_INC_TAX_CON_1, 'disable', 'true');
		setStyle(PER_INC_TAX_CON_2,'disable', 'true');
		setStyle(PER_INC_TAX_CON_3,'disable', 'true');
		var controlNames = [PER_INC_TAX_CON_1,PER_INC_TAX_CON_2,CRO_DEC,PER_INC_TAX_CON_3];
		var controlValues = ['','',''];
		setMultipleFieldValues(controlNames,controlValues);

	}
}

function managePersonalDetailsOthersFields(){
	if(getValue(PA_CITY) != 'OTHERS' || getValue(PA_CITY) == ''){
		setStyle(OTHER_PERM_CITY, PROPERTY_NAME.DISABLE, 'true');
		if(getValue(PA_CITY) == ''){
			setFieldValue(OTHER_PERM_CITY,'');
		}
	} else{
		setStyle(OTHER_PERM_CITY, PROPERTY_NAME.DISABLE, 'false');
	}
	if(getValue(RA_CITY) != 'OTHERS' || getValue(RA_CITY) == ''){
		setStyle(OTHER_RES_CITY, PROPERTY_NAME.DISABLE, 'true');
		if(getValue(RA_CITY) == ''){
			setFieldValue(OTHER_RES_CITY,'');
		}
	} else{
		setStyle(OTHER_RES_CITY, PROPERTY_NAME.DISABLE, 'false');
	}
	if(getValue(CP_CITY) != 'OTHERS' || getValue(CP_CITY) == ''){
		setStyle(OTHER_CORR_CITY, PROPERTY_NAME.DISABLE, 'true');
		if(getValue(CP_CITY) == ''){
			setFieldValue(OTHER_CORR_CITY,'');
		}
	} else{
		setStyle(OTHER_CORR_CITY, PROPERTY_NAME.DISABLE, 'false');
	}
	//
	 if(getValue(CORR_STATE) != 'OTHERS' || getValue(CORR_STATE) == ''){
		setStyle(CP_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		setFieldValue(CP_OTHERS,'');
	} else {
		setStyle(CP_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	} if(getValue(RES_STATE) != 'OTHERS' || getValue(RES_STATE) == ''){
		setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		setFieldValue(RA_OTHERS,'');
	} else {
		setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	} if(getValue(PERM_STATE) != 'OTHERS' || getValue(PERM_STATE) == ''){
		setStyle(PA_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		setFieldValue(PA_OTHERS,'');
	} else {
		setStyle(PA_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
}

function onFBDecisionLoadChange(){
	var val = getValue('CRO_DEC');
	if(val == 'Approve'){
		setStyle('CRO_REJ_REASON','disable','true');
		setStyle('CRO_REMARKS','disable','true');
	} else {//if(val == 'Reject')
		setStyle('CRO_REJ_REASON','disable','false');
		setStyle('CRO_REMARKS','disable','false');
	}
}

function validateAccRelGridForUaePassThreeStep(){

	var iRows = getGridRowCount(ACC_RELATION);	

	for(var i=0 ; i< iRows ; i++){
		var sEida= getValueFromTableCell(ACC_RELATION, i, 4);	
		var sDOB= getValueFromTableCell(ACC_RELATION, i, 5);
		var sEmail= getValueFromTableCell(ACC_RELATION, i, 10);			
		var sMobNo= getValueFromTableCell(ACC_RELATION, i, 3);	
		var accType = getValueFromTableCell(ACC_RELATION, i,9);
		var name = getValueFromTableCell(ACC_RELATION, i,1);
		//var yearOfBirth = new Date(getValueFromTableCell(ACC_RELATION, i,5)).getFullYear();
		var yearOfBirth = getValueFromTableCell(ACC_RELATION, i,5).slice(-4);
		var nationality = getValueFromTableCell(ACC_RELATION, i,7);
		if(sDOB == ""){
			showMessage(ACC_RELATION, 'DOB Cannot be blank for '+name+' ', 'error');
			return false;
		}
		if(accType == "")
		{
			showMessage(ACC_RELATION, 'Account Relationship cannot be blank for '+name+' ', 'error');
			return false;
		}
		if(nationality == "")
		{
			showMessage(ACC_RELATION, 'Nationality cannot be blank for '+name+' ', 'error');
			return false;
		}
		if(sEida == ""){
			showMessage(ACC_RELATION, 'Please select skipUAEPass for '+name+' ', 'error');
			return false;
		}

		if(!(accType == "Minor")){
			if(sEmail == ""){
				showMessage(ACC_RELATION, 'Please Fill Email for '+name+' ', 'error');
				return false;
			} else if(sMobNo == ""){
				showMessage(ACC_RELATION, 'Please Fill Mobile No for '+name+' ', 'error');
				return false;

			} 
//			else if(sEida == ""){
//				showMessage(ACC_RELATION, 'Please Fill EIDA No for '+name+' ', 'error');
//				return false;
//			}
			else if(sEida.length != 15 && sEida != ""){
				showMessage(ACC_RELATION, 'Invalid Eida No for '+name+' ', 'error');
				return false;
			} 
			/*else if(sEida != "" && !sEida.startsWith('784'+yearOfBirth)){
				showMessage(ACC_RELATION, 'Invalid Eida No for '+name+' ', 'error');
				return false;
			}*/
		}
	}
	return true;
}

function validateGridDataForUAEPass(){
	var iRows = getGridRowCount(ACC_RELATION);	

	for(var i=0 ; i< iRows ; i++){
		var sEida= getValueFromTableCell(ACC_RELATION, i, 4);	
		var sDOB= getValueFromTableCell(ACC_RELATION, i, 5);
		var sEmail= getValueFromTableCell(ACC_RELATION, i, 10);			
		var sMobNo= getValueFromTableCell(ACC_RELATION, i, 3);	
		var accType = getValueFromTableCell(ACC_RELATION, i,9);
		var name = getValueFromTableCell(ACC_RELATION, i,1);
		var acRel = getValueFromTableCell(ACC_RELATION, i,1);
		//var yearOfBirth = new Date(getValueFromTableCell(ACC_RELATION, i,5)).getFullYear();
		var yearOfBirth = getValueFromTableCell(ACC_RELATION, i,5).slice(-4);
		var nationality = getValueFromTableCell(ACC_RELATION, i,6);
		if(sDOB == "" && getValue('IS_INITIATED_UAE_PASS') != 'Y'){
			showMessage(ACC_RELATION, 'DOB Cannot be blank for '+name+' ', 'error');
			return false;
		}
		if(accType == "")
		{
			showMessage(ACC_RELATION, 'Account Relationship cannot be blank for '+name+' ', 'error');
			return false;
		}
		
		if(nationality == "")
		{
			showMessage(ACC_RELATION, 'Nationality cannot be blank for '+name+' ', 'error');
			return false;
		}


		if(!(accType == "Minor")){
			if(sEmail == ""){
				showMessage(ACC_RELATION, 'Please Fill Email for '+name+' ', 'error');
				return false;
			} else if(sMobNo == ""){
				showMessage(ACC_RELATION, 'Please Fill Mobile No for '+name+' ', 'error');
				return false;

			} 
//			else if(sEida == ""){
//				showMessage(ACC_RELATION, 'Please Fill EIDA No for '+name+' ', 'error');
//				return false;
//			}
			else if(sEida.length != 15 && sEida != ""){
				showMessage(ACC_RELATION, 'Invalid Eida No for '+name+' ', 'error');
				return false;
			} 
			/*else if(sEida != "" && !sEida.startsWith('784'+yearOfBirth)){
				showMessage(ACC_RELATION, 'Invalid Eida No for '+name+' ', 'error');
				return false;
			}*/
		}
	}
	return true;
}

function validateAccRelridForUaePass(){

	var iRows = getGridRowCount(ACC_RELATION);	

	for(var i=0 ; i< iRows ; i++){
		var sEida= getValueFromTableCell(ACC_RELATION, i, 4);	
		var sDOB= getValueFromTableCell(ACC_RELATION, i, 5);
		var sEmail= getValueFromTableCell(ACC_RELATION, i, 10);			
		var sMobNo= getValueFromTableCell(ACC_RELATION, i, 3);	
		var accType = getValueFromTableCell(ACC_RELATION, i,9);
		var name = getValueFromTableCell(ACC_RELATION, i,1);
		var acRel = getValueFromTableCell(ACC_RELATION, i,1);
		var skipUaePass = getValueFromTableCell(ACC_RELATION, i,13);
		//var yearOfBirth = new Date(getValueFromTableCell(ACC_RELATION, i,5)).getFullYear();
		var yearOfBirth = getValueFromTableCell(ACC_RELATION, i,5).slice(-4);
		var nationality = getValueFromTableCell(ACC_RELATION, i,6);
		if(sDOB == "" && getValue('IS_INITIATED_UAE_PASS') != 'Y'){
			showMessage(ACC_RELATION, 'DOB Cannot be blank for '+name+' ', 'error');
			return false;
		}
		if(accType == "")
		{
			showMessage(ACC_RELATION, 'Account Relationship cannot be blank for '+name+' ', 'error');
			return false;
		}
		
		if(nationality == "")
		{
			showMessage(ACC_RELATION, 'Nationality cannot be blank for '+name+' ', 'error');
			return false;
		}
		if(sEida == "" && (skipUaePass == 'false' || skipUaePass == "")){
			showMessage(ACC_RELATION, 'Please select skipUAEPass for '+name+' ', 'error');
			return false;
		}


		if(!(accType == "Minor")){
			if(sEmail == ""){
				showMessage(ACC_RELATION, 'Please Fill Email for '+name+' ', 'error');
				return false;
			} else if(sMobNo == ""){
				showMessage(ACC_RELATION, 'Please Fill Mobile No for '+name+' ', 'error');
				return false;

			} 
//			else if(sEida == ""){
//				showMessage(ACC_RELATION, 'Please Fill EIDA No for '+name+' ', 'error');
//				return false;
//			}
			else if(sEida.length != 15 && sEida != ""){
				showMessage(ACC_RELATION, 'Invalid Eida No for '+name+' ', 'error');
				return false;
			} 
			/*else if(sEida != "" && !sEida.startsWith('784'+yearOfBirth)){
				showMessage(ACC_RELATION, 'Invalid Eida No for '+name+' ', 'error');
				return false;
			}*/
		} 
	}
	return true;
}

/*function attachedDocData() {
	var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
	var arrAvailableDocList = docFrameRef.document.getElementById('wdesk:docCombo');
	var docType = '';
	for(var iDocCounter=0; iDocCounter<arrAvailableDocList.length; iDocCounter++) {
		docType = docType+arrAvailableDocList[iDocCounter].text+',';
	}
	return docType;
}*/