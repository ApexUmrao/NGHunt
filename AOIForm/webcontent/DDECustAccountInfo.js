var submitFlag = false;
function onDDECustAccountInfoLoad(event){
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	if(getValue(EMP_CITY) == 'OTHERS' || getValue(EMP_CITY) == ''){
		setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	}else{
		setFieldValue(EMP_CITY_OTHERS,'');
		setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
	
	if(getValue(EMP_STATE) == 'OTHERS' || getValue(EMP_STATE) == ''){
		setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'true');
	}else{
		setFieldValue(EMP_STATE_OTHERS,'');
		setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'false');
	}
	var controlnames = [CRO_REMARKS,WI_NAME,CURR_WS_DETAIL,'TXT_CURRWS'];
	var controlvalues = ['', wiName,workstepName,workstepName];
	setMultipleFieldValues(controlnames, controlvalues);
	var enableControlsList = [RES_WITHOUT_EIDA,CARRYING_EIDA_CARD];
	var disableControlsList = [SEC_PERSONAL_DET,RELIGION,MARITAL_STATUS,SEC_ACC_REL,L1_APP_REQ,L2_APP_REQ,L3_APP_REQ,
	                           L4_APP_REQ];
	var hideControlsList = [CURR_WS_DETAIL];
	setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, 'true');
	//lockAOSection(lock_ddeaccountinfo_frames);
//	if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName) {
//		setAutoFilledFieldsLocked(); 
//	}g
		
	var r = getGridRowCount('temp_gen_queue');
	for(var i =0;i<r;i++){
		setTableCellColor(TEMP_GEN_QUEUE_LVW,i,4,'FFFFFF');
		setTableCellColor(TEMP_GEN_QUEUE_LVW,i,5,'FFFFFF');
		setCellDisabled(TEMP_GEN_QUEUE_LVW, i,5,"true");
		
	}
	
	setTabVisible();
	setAutoFilledFieldsLocked(); 
	enableControls(enableControlsList);
	disableControls(disableControlsList);
	hideControls(hideControlsList);
	clearCombo(CRO_DEC);
	//frame81cpdDISABLE();
	setManualFieldsEnable();
	//added by reyaz ao dcra
	if(getValue('PRIVATE_CLIENT') == 'Y'){
		setStyle('PD_CUSTSEGMENT','disable','true')
	}
	if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName) {
		var disableControlsPersonal = [CK_PER_DET,PD_ANY_CHNG_CUST_INFO,CHK_CONTACT_DET,FRAME_CLIENTQUESTIONS,
		                               CHK_INTERNAL_SEC,SEC_CONTACT_DET_CP,SEC_CONTACT_DET_PA,SEC_CONTACT_DETAILS,SEC_INT_DETAIL];
		disableControls(disableControlsPersonal);
		var disableControlsKYC = [CHK_GEN_INFO,SEC_GEN_INFO,CHK_EMP_DETAIL,SEC_FUND_EXP_RELTNSHP,CHK_FUNDING_INFO,
		                          SEC_ASSESS_OTH_INFO,CHK_RISK_ASS,SEC_BNK_REL_UAE_OVRS,CHK_BANKING_RELATION,SEC_SIGN_OFF,'KYC_PRE-ASSESSMENT'];
		disableControls(disableControlsKYC);
		var disableControlsFATCA = [CHECKBOX_FATCA];
		disableControls(disableControlsFATCA);
		var disableControlsCRS = [CRS_CB,SEC_CRS_DETAILS];
		disableControls(disableControlsCRS);
		var disableControlsSancScreen = [BTN_TRSD_CHECK,SEC_SS_TRSD,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,
		                                 SEC_SS_RISK_ASSESS,BTN_NEXT_CUST_SANCT];
		disableControls(disableControlsSancScreen);
		disableControls(disableControlskyc);
		disableControls(disableEmployeeAddress);
		setStyle(EDIT1, PROPERTY_NAME.DISABLE, 'true');
		pepAssesment();// Dcra
		setKYCFlagPrivateclient(); //Dcra
		visiblePreAssesmentField();
	}
	if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName && (getValue(REQUEST_TYPE) == 'Category Change Only' 
		|| getValue(REQUEST_TYPE) == 'New Account With Category Change')){
		setTabStyle("tab3",11, "visible", "true");
	}
	if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName && getValue(REQUEST_TYPE) == 'New Account') {
	    setTabStyle("tab3",16, "visible", "false");
	}
	//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	setStyle(EDIT, PROPERTY_NAME.DISABLE, FALSE);
	openTrsdJsp();
	pepAssesment(); //Dcra
	setKYCFlagPrivateclient();//Dcra
	visiblePreAssesmentField();
}

function handleDDEEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickDDEEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeDDEEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusDDEEvent(event);
	}
}

function clickDDEEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var wiName = getWorkItemData('processInstanceId');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(event.target.id == VIEW){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (controlName == BTN_SI_ADD){
		executeServerEvent(BTN_SI_ADD, event.type,"", false);
	} else if (controlName == BTN_SI_MOD){
		executeServerEvent(BTN_SI_MOD, event.type,"", false);
	}else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	}else if(FETCH_INFO == (controlName)) {
		executeServerEvent(FETCH_INFO, event.type,"", false);
	} else if (event.target.id == DOC_APPROVAL_OBTAINED){
		docApproval();
	}  else if (event.target.id == DC_BTN_ADD){
			executeServerEvent(DC_BTN_ADD, EVENT_TYPE.CLICK, '', false);
	} else if (event.target.id == 'Command5'){ //check for save sahil
		var selectedRows = getSelectedRowsIndexes(DEL_PREFERNCE_LVW); 
		var iSelectedRow = selectedRows[0];  
		executeServerEvent(event.target.id, event.type, iSelectedRow, false);
	} else if (event.target.id == CT_BTN_EIDA_REFRESH){
		var channelType = getValue(CHANNEL_TYPE);
		if('Direct' == channelType){
			executeServerEvent(CT_BTN_EIDA_REFRESH, EVENT_TYPE.CLICK, 'DIRECT-FIRST', false);
		}else if ((getValue(CHANNEL_TYPE) == 'Alternate') && (getValue(DATA_ENTRY_MODE)== 'Detail Data Entry')){
			executeServerEvent(CT_BTN_EIDA_REFRESH, EVENT_TYPE.CLICK, 'ALTERNATE-FIRST', false);
		}
	} else if (event.target.id == BUTTON_GENERATE_TEMPLATE || event.target.id == BTN_CC_GEN_TEMP
			|| event.target.id == BUTTON_RETRY  ){
	} else if(event.target.id == 'Command32') {
		addDebitCard();
	} else if(event.target.id == CT_BTN_REFRESH) {	
		var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
		var custId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
		if(custId == '') {
			showMessage('', 'customer id not present for this customer', 'error');
			return;
		} else {
			executeServerEvent(event.target.id, event.type, custId, false);
		}
	} else if(controlName == BTN_ECD_VALIDATE) {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(event.target.id == (DEL_BRNCH_COURIER)) {  
		toggleCheckboxes(event.target.id,DEL_EXCELLENCY_CNTR);
	} else if(event.target.id == BTN_FCR_SRCH){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == ('BTN_DOC_GEN_RETRY')) {
		var workstepName = getWorkItemData('activityName');
		console.log('inside grid button');
			if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName) {
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
	} else if(event.target.id == (DEL_EXCELLENCY_CNTR)) {
		toggleCheckboxes(event.target.id,DEL_BRNCH_COURIER);
	} else if(event.target.id == (DEL_BRNCH_COURIER)) {
		if(getValue(DEL_BRNCH_COURIER) == ('true')) {
			setStyle(DEL_BRNCH_NAME, PROPERTY_NAME.DISABLE, FALSE);
			setStyle(EXCELLENCY_CENTER, PROPERTY_NAME.DISABLE, 'true');
		} else {
			setStyle(DEL_BRNCH_NAME, PROPERTY_NAME.DISABLE, 'true');
			setStyle(EXCELLENCY_CENTER, PROPERTY_NAME.DISABLE, FALSE);
		}
	} else if(event.target.id == (DEL_EXCELLENCY_CNTR)) {
		if(getValue(DEL_EXCELLENCY_CNTR) == ('true')) {
			setStyle(DEL_BRNCH_NAME, PROPERTY_NAME.DISABLE, 'true');
			setStyle(EXCELLENCY_CENTER, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(DEL_BRNCH_NAME, PROPERTY_NAME.DISABLE, FALSE);
			setStyle(EXCELLENCY_CENTER, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if (controlName == BTN_DOC_GEN_REFRESH){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (VIEW == event.target.id){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (MANUAL_VISASTATUS == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (MANUAL_VISANO == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if ('Command55' == event.target.id) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == ('product_queue.mode_of_funding')) {	//check grid row in js 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (REMOVE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (NEW_RM_CODE_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (EDIT)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CT_BTN_EIDA_REFRESH)) {
		executeServerEvent(event.target.id, event.type, workstepName+','+wiName, true);
	} else if(event.target.id == (FETCH_INFO)) {
		if(event.target.id == (FETCH_INFO) && getValue(INSTANT_DEL_NO) == ('true')) {	
			executeServerEvent(event.target.id, event.type, '', false);
		}
	} else if (event.target.id == (INSTANT_DEL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CT_BTN_REFRESH)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CT_BTN_RESETALL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (BTN_DEDUPE_SEARCH)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (BUTTON_SUBMIT)) {	
		saveWorkItem();
		var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
		var resultFATCACheck = checkAttchedDocument('FATCA#');
		console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
	/*	if(getValue(SALARY_TRANSFER) == 'Yes'){
			var countWelcome = DocTypeAttachedcount('sal_certificate');
			if(countWelcome == 0){
				showMessage(SALARY_TRANSFER, 'Please attach the Salary Transfer letter to this Work Item', 'error');
				return;
			}
		} */
//		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
		executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
//		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (BTN_VALIDATE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (BTN_VALIDATEFATCA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (BTN_SELECT_CUST)) {	
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(event.target.id, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(event.target.id, event.type, '', false);
		}
	} else if(event.target.id == (CRS_ADD)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} /*else if(event.target.id == (BTN_PRD_SEARCH)) {	
		var wi_name = getWorkItemData('processInstanceId');
		setTabStyle("tab3",18, "visible", "true");
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var jspURL = sLocationOrigin+'/AO/CustomFolder/product_list.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+
		getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
		document.getElementById('PRODUCT_JSP').src=jspURL;
		selectSheet('tab3',18);
	}*/ else if(event.target.id == (MANUAL_DOB) && !getValue(MANUAL_DOB) == '') {
		if(getValue(CHECKBOX_DOB_MANUAL).toLowerCase() == (TRUE)) {
			var controlnames = [PD_DOB];
			var controlvalues = [getValue(MANUAL_DOB)];
			setMultipleFieldValues(controlnames, controlvalues);
		}
	} else if(event.target.id == (MANUAL_PASSPORTISSDATE) && !getValue(MANUAL_PASSPORTISSDATE) == '') {
		var controlnames = [HD_PASS_ISS_DATE];
		var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
		setMultipleFieldValues(controlnames, controlvalues);
	} else if(event.target.id == (MANUAL_PASSPORTEXPDATE) && !getValue(MANUAL_PASSPORTEXPDATE) == '') {
		var controlnames = [HD_PASS_EXP_DATE];
		var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
		setMultipleFieldValues(controlnames, controlvalues);
	} else if(event.target.id == (MANUAL_VISAISSDATE) && !getValue(MANUAL_VISAISSDATE) == '') {
		var controlnames = [HD_VISA_ISSUE_DATE];
		var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
		setMultipleFieldValues(controlnames, controlvalues);
	} else if(event.target.id == (MANUAL_VISAEXPDATE) && !getValue(MANUAL_VISAEXPDATE) == '') {
		var controlnames = [HD_EXP_DATE];
		var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
		setMultipleFieldValues(controlnames, controlvalues);
	} else if(event.target.id == FETCH_BALANCE) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'BTN_FG_VALIDATE'){	//aditya code
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'BTN_APP_LEVEL_RISK'){	//added by reyaz 13-09-2023
		executeServerEvent(event.target.id, event.type, '', false);
	} 
	//BTN_PRD_SEARCH
//	if (workstepName == WORKSTEPS.DDE_CUSTOMER_INFO) {//fardeen
//		fillManualDataInBelowFields(controlName,controlValue);
//		fillFCRDataInBelowFields(controlName,controlValue);
//		fillEIDADataInBelowFields(controlName,controlValue);
//	}
}

function changeDDEEvent(event){
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
//	if (workstepName == WORKSTEPS.DDE_CUSTOMER_INFO)) {//fardeen
//	fillmanualdatainbelowfields(pcontrolname,pcontrolvalue);
//	fillfcrdatainbelowfields(pcontrolname,pcontrolvalue);
//	filleidadatainbelowfields(pcontrolname,pcontrolvalue);
//	}
	//yamini
	if (event.target.id == DOC_APPROVAL_OBTAINED) {
		var controlnames = [COURT_ORD_TRADE_LIC];
		var controlvalues;
		if(getValue(DOC_APPROVAL_OBTAINED) == TRUE){
			controlvalues = FALSE;
		} else{
			controlvalues = 'true';
		}
		setMultipleFieldValues(controlnames, controlvalues);
	}else if(controlName == (DFC_STATIONERY_AVAIL)) { 
		executeServerEvent(DFC_STATIONERY_AVAIL, event.type,"", false);
	}else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	}else if (NOM_REQ == (controlName)){
		executeServerEvent(NOM_REQ, event.type,"", false);			
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
	else if(EXISTING_NOM_PRSN == (controlName)) {
		executeServerEvent(EXISTING_NOM_PRSN, event.type,"", false);
	}  else if (event.target.id == IDS_OTH_CB_OTHERS){
		if (getValue(event.target.id) == true){
			setStyle('IDS_BNFT_CB_OTHERS', PROPERTY_NAME.DISABLE, 'false');
		}
		else {
			setStyle('IDS_BNFT_CB_OTHERS', PROPERTY_NAME.DISABLE, 'true');
		}
	} else if (event.target.id  == 'CRS_CERTIFICATION_OBTAINED_0' || event.target.id  == 'CRS_CERTIFICATION_OBTAINED_1'){
		if('Yes' == getValue(CRS_CERTIFICATION_OBTAINED)){
			var controlNames = ['CRS_Classification'];
			var controlValues = ['UPDATED-DOCUMENTED'];
			setMultipleFieldValues(controlNames,controlValues);
			disableControls([controlNames]);
			} else {
			var controlNames = ['CRS_Classification'];
			var controlValues = [''];
			setMultipleFieldValues(controlNames,controlValues);
			enableControls([controlNames]);
		}
		executeServerEvent(event.target.id, event.type,"", false);
	} else if(event.target.id == EMP_CITY) {
		if(getValue(EMP_CITY) == 'OTHERS'){
			setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}else{
			setFieldValue(EMP_CITY_OTHERS,'');
			setStyle(EMP_CITY_OTHERS, PROPERTY_NAME.DISABLE, 'true'); 
		}
	} else if(event.target.id == EMP_STATE) {
		if(getValue(EMP_STATE) == 'OTHERS'){
			setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'false');
		}else{
			setFieldValue(EMP_STATE_OTHERS,'');
			setStyle(EMP_STATE_OTHERS, PROPERTY_NAME.DISABLE, 'true');//EMP_STATE_OTHERS
		}
	} else if (event.target.id == DRP_RESEIDA) {
		setEIDA();
	} else if (event.target.id == MANUAL_PER_CNTRY) {
		var controlnames = [PA_OTHERS];//, CONTACT_DETAILS_CITY_OTHERS];
		clearAOControls(controlnames);//, controlvalues);
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_FIRSTNAME || event.target.id == MANUAL_LASTNAME) {
//		setValues({[MANUAL_NAME]: getValue(MANUAL_FIRSTNAME) + ' ' + getValue(MANUAL_LASTNAME), 
//			[CRS_FIRSTNAME]: getValue(MANUAL_FIRSTNAME), [CRS_LASTNAME]: getValue(MANUAL_LASTNAME)}, TRUE);
//		var controls = 
//		setMultipleFieldValues();
//		setFieldValue(PD_FULLNAME, getValue(MANUAL_NAME));
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == MANUAL_STATE){
		var manualstate = getValue(MANUAL_STATE);
		var controlnames = [MANUAL_CITY, CP_CITY, RA_CITY, CORR_STATE];
		var controlvalues = [manualstate, manualstate, manualstate, manualstate];
		setMultipleFieldValues(controlnames, controlvalues);
	} else if(event.target.id == ED_CUST_CRS_BRDR_PAYMENT) {
		if('YES' == getValue(ED_CUST_CRS_BRDR_PAYMENT).toUpperCase()) {
			var enablefields = [ED_PURPSE_CRS_BRDR_PAYMENT, ED_ANTCPATD_CRS_BRDER_PYMT, ED_CNTRY_PYMT_RECV, 
			                    ED_ANTCPATD_MNTHVAL_BRDER_PYMT];
			enableControls(enablefields);
		} else {
			var clearfields = [ED_PURPSE_CRS_BRDR_PAYMENT, ED_ANTCPATD_CRS_BRDER_PYMT, ED_CNTRY_PYMT_RECV, 
			                   ED_ANTCPATD_MNTHVAL_BRDER_PYMT];
			clearAOControls(clearfields);
			var disablefields = [ED_PURPSE_CRS_BRDR_PAYMENT, ED_ANTCPATD_CRS_BRDER_PYMT, ED_CNTRY_PYMT_RECV, 
			                     ED_ANTCPATD_MNTHVAL_BRDER_PYMT];
			disableControls(disablefields);
		}
	} else if(event.target.id == RA_IS_UAE_RESIDENT) {
		if('NO' == getValue(RA_IS_UAE_RESIDENT).toUpperCase()) {
			setStyle(RA_RSN_BNKNG_UAE, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			var clearfields = [RA_RSN_BNKNG_UAE];
			clearAOControls(clearfields);
			setStyle(RA_RSN_BNKNG_UAE, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == (CRS_CB)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CK_PER_DET)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == RA_IS_CUST_PEP) {
		  PepNationality();
		if('YES' == getValue(RA_IS_CUST_PEP).toUpperCase()) {
			setStyle(RA_LIST_OF_CUST_PEP, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			var clearfields = [RA_LIST_OF_CUST_PEP];
			clearAOControls(clearfields);
			setStyle(RA_LIST_OF_CUST_PEP, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(WORKSTEPS.DDE_CUST_INFO == workstepName && (event.target.id == (INDICIACOMBO) 
			|| event.target.id == (FAT_SSN) || event.target.id == (FAT_CUST_CLASSIFICATION) 
			|| event.target.id == (DATEPICKERCUST) || event.target.id == (DATEPICKERW8) 
			|| event.target.id == (COMBODOC))) {  
		var controlnames = [CHANGE_IN_FATCA_3WAY_INPUTS, 'fatcamain'];
		var controlvalues = ['yes', 'yes'];
		setMultipleFieldValues(controlnames, controlvalues);
		if('TRUE' == getValue(IDS_CB_TRB).toUpperCase()) {
			setStyle(BTN_VALIDATEFATCA, PROPERTY_NAME.DISABLE, FALSE);
		}
	} else if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName && event.target.id == DEL_STATE) {
		var controlnames = [DEL_STATE_OTHER];
		clearAOControls(controlnames);
		if(getValue(DEL_STATE).toUpperCase() == ('OTHERS')) {
			setStyle(DEL_STATE_OTHER, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(DEL_STATE_OTHER, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == CRO_DEC) {
		if(getValue(CRO_DEC).toLowerCase() == ('approve')) {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
			var controlnames = [CRO_REJ_REASON];
			clearAOControls(controlnames);
		} else {
			setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, FALSE);
		}
	} else if(event.target.id == CRO_REJ_REASON) {
		if(!getValue(CRO_REJ_REASON) == '') {
			if(getValue(CRO_DEC) == '') {
				showMessage(CRO_DEC, 'please select user decision first', 'error');
				var controlnames = [CRO_REJ_REASON];
				clearAOControls(controlnames);
				setStyle(EDIT, PROPERTY_NAME.DISABLE, FALSE);
			}
		}
	} else if(event.target.id == EXISTING_ETIHAD_CUST) {
		if(getValue(EXISTING_ETIHAD_CUST).toLowerCase() == 'yes') {
			setStyle(ETIHAD_NO, PROPERTY_NAME.DISABLE, FALSE);
			setStyle(BTN_ECD_VALIDATE, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			var controlnames = [ETIHAD_NO];
			clearAOControls(controlnames);
			setStyle(ETIHAD_NO, PROPERTY_NAME.DISABLE, 'true');
			setStyle(BTN_ECD_VALIDATE, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == CUST_PREFIX) {
		if(getValue(CUST_PREFIX) == ('OTHERS')) {
			setStyle(PD_OTHERS, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(PD_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == (MARITAL_STATUS)) {
		if(getValue(MARITAL_STATUS) == ('OTHERS')) {
			setStyle(PD_MARITALSTATUSOTHER, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(PD_MARITALSTATUSOTHER, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == (CORR_STATE)) {
		if(getValue(CORR_STATE) == ('OTHERS')  && getValue(CHK_CONTACT_DET)== true) {
			setStyle(CPD_RISK_ASSESS_MARKS, PROPERTY_NAME.DISABLE, FALSE);//text158
		} else {
			setStyle(CPD_RISK_ASSESS_MARKS, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == (RES_STATE)) {
		if(getValue(RES_STATE) == ('OTHERS') && getValue(CHK_CONTACT_DET) == true) {
			setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(RA_OTHERS, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == (PERM_STATE)) {
		if(getValue(PERM_STATE) == ('OTHERS') && getValue(CHK_CONTACT_DET) == true) {
			setStyle(PA_OTHERS, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(PA_OTHERS, PROPERTY_NAME.DISABLE, 'true');//text95
		}
	} else if(event.target.id == (PROFESSION)) {
		var prof = getValue(PROFESSION);
		if(prof.toUpperCase() == ('OTHERS')) {
			setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if(event.target.id == (EMPNAME)) {
		if(getValue(EMPNAME) == ('OTHERS')) {
			setStyle(ED_EMPNAME, PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle(ED_EMPNAME, PROPERTY_NAME.DISABLE, 'true');
		}
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (ED_EMP_TYPE)) {	
		if(getValue(ED_EMP_TYPE) == ('ADCB')) {
			setStyle(ED_SET_FLG, PROPERTY_NAME.DISABLE, FALSE);
			var controlnames = [ED_SET_FLG];
			var controlvalues = ['Yes'];
			setMultipleFieldValues(controlnames, controlvalues);
		} else {
			setStyle(ED_SET_FLG, PROPERTY_NAME.DISABLE, 'true');
			var controlnames = [ED_SET_FLG];
			var controlvalues = ['No'];
			setMultipleFieldValues(controlnames, controlvalues);
		}
	} else if(MANUAL_PREFIX == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (COMBODOC == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(CP_CITY == (event.target.id) || PA_CITY == (event.target.id) 
			|| RA_CITY == (event.target.id)) { //load also 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(PD_CUSTSEGMENT == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(MANUAL_FIRSTNAME == (event.target.id)){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(MANUAL_LASTNAME == (event.target.id)){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(MANUAL_NAME == (event.target.id)){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(MANUAL_VISASTATUS == (event.target.id)) { 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if('COMBO34' == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(EMP_STATUS == (event.target.id)) {
		var controlName = event.target.id;
		var controlNames = [EMP_COUNTRY,EMP_STREET,EMP_CITY,EMP_PO_BOX,EMP_STATE,EMP_STATE_OTHERS,EMP_CITY_OTHERS];
		if(getValue(controlName) != 'Self Employed' && getValue(controlName) != 'Employed'){
			disableControls(controlNames);
			clearAOControls(controlNames);	
		} else {
			enableControls(controlNames);
		}
		executeServerEvent(controlName, event.type, '', false);
	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(FCR_EMPLYR_NAME == (event.target.id) 
			|| EIDA_EMPLYR_NAME == (event.target.id) 
			|| MANUAL_EMPLYR_NAME == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(FCR_RESIDENT == (event.target.id) || EIDA_RESIDENT == (event.target.id) 
			|| MANUAL_RESIDENT == (event.target.id) 
			|| FCR_NATIONALITY == (event.target.id) 
			|| EIDA_NATIONALITY == (event.target.id) 
			|| MANUAL_NATIONALITY == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(MANUAL_CNTRY == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(RES_CNTRY == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(CORR_CNTRY == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(PERM_CNTRY == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_NATIONALITY_FCR) 
			|| event.target.id == (FCR_NATIONALITY) 
			|| event.target.id == (CHECKBOX_NATIONALITY_EIDA)
			|| event.target.id == (CHECKBOX_NATIONALITY_MANUAL) 
			|| event.target.id == (EIDA_NATIONALITY)
			|| event.target.id == (MANUAL_NATIONALITY) 
			|| event.target.id == (CHECKBOX_CNTRY_OF_CORR_FCR) || event.target.id == (FCR_CNTRY) 
			|| event.target.id == (CHECKBOX_CNTRY_OF_CORR_EIDA) || event.target.id == (CHECKBOX_CNTRY_OF_CORR_MANUAL) 
			|| event.target.id == (EIDA_CNTRY) || event.target.id == (MANUAL_CNTRY)
			|| event.target.id == (CHECKBOX_TELE_RES_FCR) || event.target.id == (FCR_PH) 
			|| event.target.id == (CHECKBOX_TELE_RES_EIDA) || event.target.id == (CHECKBOX_TELE_RES_MANUAL) 
			|| event.target.id == (EIDA_PH) || event.target.id == (MANUAL_PH) 
			|| event.target.id == (CHECKBOX_TELE_MOB_FCR) || event.target.id == (FCR_MOBILE) 
			|| event.target.id == (CHECKBOX_TELE_MOB_EIDA) || event.target.id == (CHECKBOX_TELE_MOB_MANUAL) 
			|| event.target.id == (EIDA_MOBILE) || event.target.id == (MANUAL_MOBILE)
			|| event.target.id == (CHECKFCR) || event.target.id == (FCR_RESIDENT) 
			|| event.target.id == (CHECKEIDA) || event.target.id == (CHECKMANUAL) 
			|| event.target.id == (EIDA_RESIDENT) 
			|| event.target.id == (MANUAL_RESIDENT) || event.target.id == (FAT_US_PERSON)
			|| event.target.id == (FAT_LIABLE_TO_PAY_TAX) || event.target.id == (CNTRY_OF_BIRTH) 
			|| event.target.id == (POACOMBO) || event.target.id == (INDICIACOMBO)
			|| event.target.id == (FAT_SSN) || event.target.id == (FAT_CUST_CLASSIFICATION)
			|| event.target.id == (DATEPICKERCUST) 
			|| event.target.id == (DATEPICKERW8) || event.target.id == (COMBODOC)
			|| event.target.id == (CHECKBOX_COB_FCR) || event.target.id == (CHECKBOX_COB_EIDA) 
			|| event.target.id == (CHECKBOX_COB_MANUAL) || event.target.id == (FCR_COUNTRYBIRTH)
			|| event.target.id == (EIDA_COUNTRYBIRTH) 
			|| event.target.id == (MANUAL_COUNTRYBIRTH)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(WORKSTEPS.DDE_CUST_INFO == (workstepName) 
			&& event.target.id == (FAT_CUST_CLASSIFICATION)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} //text79//text80-->Kritika (ACTIVITY_DDE_CUST_INFO)
	else if(WORKSTEPS.DDE_ACCOUNT_INFO == (workstepName) 
			&& event.target.id == (NEW_DEL_MODE)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (NEW_RM_CODE_CAT_CHANGE)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (PD_ANY_CHNG_CUST_INFO)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (PA_SAMEAS)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (RA_SAMEAS)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(WORKSTEPS.DDE_ACCOUNT_INFO == (workstepName) 
			&& event.target.id == (DFC_STATIONERY_AVAIL)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(PD_CUSTSEGMENT == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(ACC_INFO_PG == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(GROUP_TYPE == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(NEW_LINK == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(EMPNAME == (event.target.id)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (SI_DEB_ACC_NO)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (NEW_RM_CODE_CAT_CHANGE)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (NEW_RM_NAME_CAT_CHANGE)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (COMBODOC)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (FAT_US_PERSON)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if((event.target.id == (FCR_COUNTRYBIRTH)) 
			|| (event.target.id == (EIDA_COUNTRYBIRTH))
			|| (event.target.id == (MANUAL_COUNTRYBIRTH))) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (NEW_CUST_SEGMENT)) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_CAT_BENEFIT_OTHER == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_SALARY_TRANSFER_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_MORTAGAGE_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_INSURANCE_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_TRB_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_OTHERS_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_VVIP == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_PREVILEGE_TP_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_CAT_BENEFIT_OTHER == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_SHOPPING_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_SPORT_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_TRAVEL_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (IS_EXCELLENCY_TP_CAT_CHANGE == event.target.id){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == CHECKBOX_NATIONALITY_FCR ||  event.target.id == FCR_NATIONALITY || 
			event.target.id == CHECKBOX_NATIONALITY_EIDA || event.target.id == CHECKBOX_NATIONALITY_MANUAL || 
			event.target.id == EIDA_NATIONALITY || event.target.id == MANUAL_NATIONALITY || 
			event.target.id == CHECKBOX_CNTRY_OF_CORR_FCR || event.target.id == FCR_CNTRY || event.target.id == CHECKBOX_CNTRY_OF_CORR_EIDA || event.target.id == CHECKBOX_CNTRY_OF_CORR_MANUAL || event.target.id == EIDA_CNTRY || 
			event.target.id == MANUAL_CNTRY || event.target.id == CHECKBOX_TELE_RES_FCR || event.target.id == FCR_PH || 
			event.target.id == CHECKBOX_TELE_RES_EIDA || event.target.id == CHECKBOX_TELE_RES_MANUAL || event.target.id == EIDA_PH || 
			event.target.id == MANUAL_PH || event.target.id == CHECKBOX_TELE_MOB_FCR || 
			event.target.id == FCR_MOBILE || event.target.id == CHECKBOX_TELE_MOB_EIDA || event.target.id == CHECKBOX_TELE_MOB_MANUAL || 
			event.target.id == EIDA_MOBILE || event.target.id == MANUAL_MOBILE || 
			event.target.id == CHECKFCR || event.target.id == FCR_RESIDENT || 
			event.target.id == CHECKEIDA || event.target.id == CHECKMANUAL || 
			event.target.id == EIDA_RESIDENT || event.target.id == MANUAL_RESIDENT ||  
			event.target.id == FAT_US_PERSON || event.target.id == FAT_LIABLE_TO_PAY_TAX || 
			event.target.id == CNTRY_OF_BIRTH || 
			event.target.id == POACOMBO || 
			event.target.id == INDICIACOMBO || event.target.id == FAT_SSN || 
			event.target.id == FAT_CUST_CLASSIFICATION || event.target.id == DATEPICKERCUST || 
			event.target.id == DATEPICKERW8 || event.target.id == COMBODOC || 
			event.target.id == CHECKBOX_COB_FCR || event.target.id == CHECKBOX_COB_EIDA || 
			event.target.id == CHECKBOX_COB_MANUAL || event.target.id == FCR_COUNTRYBIRTH || 
			event.target.id == EIDA_COUNTRYBIRTH || event.target.id == MANUAL_COUNTRYBIRTH) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_CONTACT_DET)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_PASSPORT_DET)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHK_INTERNAL_SEC)) {	
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
	} else if(event.target.id == (CHECKBOX_PASSPORT_TYPE_MANUAL) || event.target.id == (CHECKBOX_PASSPORT_TYPE_FCR) 
			|| event.target.id == (CHECKBOX_PASSPORT_TYPE_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_VISA_STATUS_FCR) || event.target.id == (CHECKBOX_VISA_STATUS_EIDA) 
			|| event.target.id == (CHECKBOX_VISA_STATUS_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COB_FCR) || event.target.id == (CHECKBOX_COB_EIDA) 
			|| event.target.id == (CHECKBOX_COB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SELECTALL_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PREFIX_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FULLNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SHORTNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FIRSTNAME_FCR))	{	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FIRSTNAME_EIDA)){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FIRSTNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_LASTNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_LASTNAME_EIDA))	{	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_LASTNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_NO_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_ISS_DT_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_EXP_DT_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == ('Check17')) {	
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
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PROFESSION_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKFCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SELECTALL_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PREFIX_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FULLNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_MOTHERSNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_MOTHERSNAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_DOB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASSPORT_NO_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_ISS_DT_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PASS_EXP_DT_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == ('check62')) {	
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
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PROFESSION_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_GENDER_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_PC_CB_SHOPPING)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKEIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SELECTALL_MANUAL )) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PREFIX_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_FULLNAME_MANUAL)) {	
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
	} else if(event.target.id == (CHECKBOX_EIDANO_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CORR_POB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_STATE_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CNTRY_OF_CORR_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_TELE_MOB_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMAIL_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_PROFESSION_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_PC_CB_ENTERTAINMENT)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKMANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CORR_POB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CORR_POB_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_SALARY_TRANSFER_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_MORTAGAGE_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_INSURANCE_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_TRB_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_OTHERS_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_VVIP)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_PREVILEGE_TP_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_TRAVEL_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_SPORT_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_SHOPPING_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IS_ENTERTAINMENT_CAT_CHANGE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_BNFT_CB_TP)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_PC_CB_TP)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_CB_OTHERS)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_CB_TRB)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_CB_INSURANCE)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_CB_MORTGAGES)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_COUNTRY_PER_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (IDS_CB_SAL_TRANSFER)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMP_NAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_EMP_NAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_CITY_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SHORTNAME_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == (CHECKBOX_SHORTNAME_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_COUNTRY_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_COUNTRY_RES_EIDA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_COUNTRY_RES_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_COUNTRY_PER_RES_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_GENDER_MANUAL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_GENDER_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_EMP_NAME_FCR)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (ED_MONTHLY_INCM)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (ED_ANNUAL_INC)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (CHECKBOX_FATCA)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (MANUAL_EIDANO)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (MANUAL_ADDRESS)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (MANUAL_MOTHERNAME)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if (event.target.id == (MANUAL_EMAIL)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if (event.target.id == MANUAL_VISASTATUS){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_VISANO){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_GENDER){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_PROF){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == RM_CODE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_1)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_2)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_3)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (EMPNAME)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id  == 'SUB_PERSONAL_TAX_0' || event.target.id == 'SUB_PERSONAL_TAX_1') {
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
	}/*else if (event.target.id == 'CRS_CERTIFICATION_OBTAINED_1'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'CRS_CERTIFICATION_OBTAINED_0'){
		executeServerEvent(event.target.id, event.type, '', false);
	}*/
	    else if (event.target.id == (FINANCIAL_BROKERS)) {	
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
		} else if (event.target.id == (ED_CB_INVSTMNT_RETN_AED)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (ED_CB_INHT_AED)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (ED_CB_REAL_INC_AED)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (ED_CB_SALE_OF_ASST)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (ED_CB_OTHERS)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (CK_PER_DET)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (DDE_MODE_OF_FUND)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		} else if (event.target.id == (DDE_TRNSFR_FRM_ACC)) {	
			executeServerEvent(event.target.id, event.type, '', false);
		}else if (event.target.id == ('LISTVIEW_PUR_ACC_RELATION')) {	
			executeServerEvent(event.target.id, event.type, '', false);
	    }else if (event.target.id == ('ADDITIONAL_SOURCES_INCOME_AED')) {	
			executeServerEvent(event.target.id, event.type, '', false);
		}else if (event.target.id == 'Customer_PEP_Rad_0' || event.target.id == 'Person_Associated_Rad_0' || event.target.id =='Person_Power_Rad_0'|| event.target.id =='Customer_Entrusted_Man_Rad_0'|| event.target.id =='Customer_Authorized_Rad_0'|| event.target.id =='Customer_Entrusted_Rad_0'||event.target.id == 'Customer_PEP_Rad_1' || event.target.id == 'Person_Associated_Rad_1' || event.target.id =='Person_Power_Rad_1'|| event.target.id =='Customer_Entrusted_Man_Rad_1'|| event.target.id =='Customer_Authorized_Rad_1'|| event.target.id =='Customer_Entrusted_Rad_1') {	
		pepLogic();
		//Added by Shivanshu For TIN Country Field 13/03/25
		}else if (event.target.id == CRS_TAX_COUNTRY) {
			executeServerEvent(event.target.id, event.type, '', false);
		}
}

function lostFocusDDEEvent(event) { //yamini
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
	if(WORKSTEPS.DDE_CUSTOMER_INFO == (workstepName)) {	
		if(event.target.id == (MANUAL_DOB) && !getValue(MANUAL_DOB) == '') {
			if(getValue(CHECKBOX_DOB_MANUAL) == true) {
				var controlnames = [PD_DOB];
				var controlvalues = [getValue(MANUAL_DOB)];
				setMultipleFieldValues(controlnames, controlvalues);
			}
		} else if(event.target.id == (MANUAL_PASSPORTISSDATE) && !getValue(MANUAL_PASSPORTISSDATE) == '') {
			var controlnames = [HD_PASS_ISS_DATE];
			var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
			setMultipleFieldValues(controlnames, controlvalues);
		} else if(event.target.id == (MANUAL_PASSPORTEXPDATE) && !getValue(MANUAL_PASSPORTEXPDATE) == '') {
			var controlnames = [HD_PASS_EXP_DATE];
			var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
			setMultipleFieldValues(controlnames, controlvalues);
		} else if(event.target.id == (MANUAL_VISAISSDATE) && !getValue(MANUAL_VISAISSDATE) == '') {
			var controlnames = [HD_VISA_ISSUE_DATE];
			var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
			setMultipleFieldValues(controlnames, controlvalues);
		} else if(event.target.id == (MANUAL_VISAEXPDATE) && !getValue(MANUAL_VISAEXPDATE) == '') {
			var controlnames = [HD_EXP_DATE];
			var controlvalues = [getValue(MANUAL_PASSPORTISSDATE)];
			setMultipleFieldValues(controlnames, controlvalues);
		}


	}
}

function onClickTabDDEAccountInfo(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	if(eventCall == 2) {
		console.log('event call is 2-tab clicked');
		//added by reyaz ao dcra
		if(getValue('PRIVATE_CLIENT') == 'Y'){
			setStyle('PD_CUSTSEGMENT','disable','true')
		}
		//if(18 == getSheetIndex('tab3')) {
	        //added by shivanshu for mandatory alert for eida expiry date
		if(getSheetIndex(tabId) == 2){ 
			mandatoryEidaNo();
		}
		if(19 == getSheetIndex('tab3')) { // changes for family banking
			showMessage('', 'Please click on Done button', 'error');
		} else {
			executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
			selectSheet(tabId,sheetIndex);
		}
	} else if(eventCall == 1) {
		console.log('event call is 1-tab not clicked');
	}
}

function postServerEventHandlerDDE(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('control name::: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	disableIndicator();
	if (null != jsondata.message && '' != jsondata.message) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			if(arr.length >=2 && '' != arr[1] 
				&& !(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1)
				&& arr[0] != 'ngoAddOutput'){
				showMessage(arr[0], arr[1], 'error');
			} else if('' != arr[0] && arr[0] != 'ngoAddOutput') {
				setFocus(arr[0]);
			}
		}
	}
	switch (eventType) {
		case EVENT_TYPE.LOAD:
			setStyle(ACC_RELATION,'disable','false');
			if('' != getValue(SELECTED_ROW_INDEX)) {
				setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
			}
			if(getValue(CHECKBOX_EIDANO_MANUAL) == false) {
				setStyle(MANUAL_EIDANO, PROPERTY_NAME.DISABLE, 'true');
			}
			if(WORKSTEPS.DDE_CUSTOMER_INFO == workstepName){
				setStyle(ACC_RELATION,PROPERTY_NAME.DISABLE,'true');
			} else {
				setStyle(ACC_RELATION,PROPERTY_NAME.DISABLE,'false');
			}
			if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName) {
				selectSheet('tab3',8);
				executeServerEvent('tabClick', EVENT_TYPE.CLICK, ',8', false);
			} else {
				selectSheet('tab3',1);
				executeServerEvent('tabClick', EVENT_TYPE.CLICK, ',1', false);
			}
			managePersonalDetailsOthersFields();
			var prof = getValue(PROFESSION);
			if(prof.toUpperCase() == ('OTHERS')) {
				setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, FALSE);
			} else {
				setStyle(ED_OTHER, PROPERTY_NAME.DISABLE, 'true');
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
			enableDisableProductAddition();
			if(getValue('PRIVATE_CLIENT') == 'Y'){
		setStyle('PD_CUSTSEGMENT','disable','true')
	}
			break;
		case EVENT_TYPE.CLICK:
			if(controlName == VIEW){
				if(jsondata.success && 'dedupeselectedindex' == jsondata.message){
					//write code to set index in grid -sahil
				}
			} else if(controlName == 'BTN_DOC_GEN_RETRY'){
				if(!jsondata.success){
					if(jsondata.message.indexOf('###') != -1){
						var arr = jsondata.message.split('###');
						if(arr[0] == 'ngoAddOutput'){
							if(arr[1].indexOf('$$$$') != -1){
								var arrDoc = jsondata.message.split('$$$$');
								console.log(arrDoc);
								var len = arrDoc.length;
								console.log(len);
								for(var i = 0; i<len ; i++){
									console.log(arrDoc[i]);
									if(arrDoc[i] != ''){
									var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
									docFrameRef.customAddDoc(arrDoc[i]);
									docFrameRef.document.getElementById('docOptionsDiv').style.display='none'; 
									}
								}
							}
						}
					} 	 
				}
			} else if('saveNextTabClick' == controlName && jsondata.success) {
				/*var label = '';
				if(getSheetIndex('tab3') == 17) {
					label = 'Decision';
				} else if(getSheetIndex('tab3') == 11){
					label = 'Category Change';
				} else if(getSheetIndex('tab3') == 14){
					label = 'Document Generation';
				} else if(getSheetIndex('tab3') == 12){
					label = 'Standing Instruction';
				} else if(getSheetIndex('tab3') == 10){
					label = 'Delivery Preferences';
				}
				executeServerEvent('tabClick', EVENT_TYPE.CLICK, label+','+getSheetIndex('tab3'), false);*/
			} else if('afterSaveNext' == controlName) {
				var label = '';
				console.log('afterSaveNext sheet: '+getSheetIndex('tab3'));
				//if(getSheetIndex('tab3') == 17) {
				if(getSheetIndex('tab3') == 18) { //changes for family banking
					label = 'Decision';
					submitFlag = true;
				} else if(getSheetIndex('tab3') == 11){
					label = 'Category Change';
				} else if(getSheetIndex('tab3') == 14){
					label = 'Document Generation';
				} else if(getSheetIndex('tab3') == 12){
					label = 'Standing Instruction';
				} else if(getSheetIndex('tab3') == 10){
					label = 'Delivery Preferences';
				}
				executeServerEvent('tabClick', EVENT_TYPE.CLICK, label+','+getSheetIndex('tab3'), false);
			    } else if(controlName == 'BTN_APP_LEVEL_RISK') {  //AO dcra
			        setStyle('BTN_APP_LEVEL_RISK','disable','true');
			        setFieldValue('DCRA_RETRIGGER_FLAG','N');
			} else if('tabClick' == controlName && jsondata.success && submitFlag) {
				submitFlag = false;
				var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
				var resultFATCACheck = checkAttchedDocument('FATCA#');
				console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
				executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'+resultFATCACheck, false);
			   }else if('tabClick' == controlName){
				   if(getValue('PRIVATE_CLIENT') == 'Y' && getSheetIndex('tab3') == 2){
					setStyle('PD_CUSTSEGMENT','disable','true')
					}
			} else if(controlName == BUTTON_SUBMIT){ //yamini
				console.log('BUTTON_SUBMIT, workstep: '+workstepName+', json message: '+jsondata.message);
				if(jsondata.success) {
					if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName && jsondata.message == CA008) {
						showConfirmDialog(CA008, confirmDialogButtons, function(result) {
							if(result == true) {
//								saveWorkItem();
//								completeWorkItem();
								executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);
							} else if(result == false) {
								setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
								return;
							}
						});
					} else if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName && jsondata.message.indexOf('$$$confirm') != -1) { 
						var confirm = jsondata.message.split('$$$');
						showConfirmDialog(confirm[0], confirmDialogButtons, function(result) {
							if(result == true) {
//								executeServerEvent(event.target.id, event.type, 'FOR_YES@@@'+confirm[2], false);
								showConfirmDialog(CA008, confirmDialogButtons, function(result) {
									if(result == true) {
//										saveWorkItem();
//										completeWorkItem();
										executeServerEvent(BUTTON_SUBMIT, event.type, 'confirmSubmit', false);
									} else if(result == false) {
										setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
										return;
									}
								});
							} else if(result == false) {
//								executeServerEvent(event.target.id, event.type, 'FOR_NO@@@'+confirm[2], false);
								setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
								setFocus(PRODUCT_QUEUE);
								return;
							}
						});
					} else {
						saveWorkItem();
						completeWorkItem();
					}
				} else if(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1) {
					showConfirmDialog(jsondata.message, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'confirmAppRisk', false);
						} else if(result == false) {
							setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
							return;
						}
					});
				} else {
					setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
					if('' != jsondata.message) {
						var arr = jsondata.message.split('###');
						if('SRC_MAKER_BTN' == arr[0]) {
//							selectSheet('tab3',4);
						}
					}
				}
			} else if(event.target.id == (CT_BTN_EIDA_REFRESH)) {
				if (jsondata.success){
					refNo = jsondata.message;
					var jspURL = 'https://adcbeida.adcbapps.local/EIDAFCUBS/login.jsp?channelId=WMSBPM&channelRefNo='+refNo;
					window.open(jspURL,"newWin","location=no,menubar=no,resizable=yes,scrollbars=yes,status=no," +
							"toolbar=no,left=100,top=20"); 
				} 
			} else if(controlName == (BTN_DOC_GEN_REFRESH)) {
				var r = getGridRowCount(TEMP_GEN_QUEUE_LVW);
				for(var i =0;i<r;i++){
					setTableCellColor(TEMP_GEN_QUEUE_LVW,i,4,'FFFFFF');
					setTableCellColor(TEMP_GEN_QUEUE_LVW,i,5,'FFFFFF');
					setCellDisabled(TEMP_GEN_QUEUE_LVW, i,5,"true");
					
				}
				console.log('getting row cou nt');
				var iRows =  getGridRowCount(TEMP_GEN_QUEUE_LVW);
				console.log("GRID COUNT:"+iRows);
				for(var ii=0;ii<iRows;ii++) {
					console.log(getValueFromTableCell(TEMP_GEN_QUEUE_LVW,ii,9));
					if(getValueFromTableCell(TEMP_GEN_QUEUE_LVW,ii,9) == "Y") {
						console.log("#######enabling buttons###########");
						 setCellDisabled(TEMP_GEN_QUEUE_LVW, ii,5,"false");
						//objChkRepeater.setEnabled(ii, "Command25", true);//documentlink
						 //setTableCellColor(tableId,rowIndex,colIndex,hexcolorcode);
					}
				}
			} else if(controlName == ('BTN_DOC_GEN_RETRY') ||controlName == ('RETRY') ){
				var r = getGridRowCount(TEMP_GEN_QUEUE_LVW);
//				alert('inside post'+r);
				for(var i =0;i<r;i++){
					setTableCellColor(TEMP_GEN_QUEUE_LVW,i,4,'FFFFFF');
					setTableCellColor(TEMP_GEN_QUEUE_LVW,i,5,'FFFFFF');
					setCellDisabled(TEMP_GEN_QUEUE_LVW, i,5,'true');
				}
			} else if(controlName == BTN_FCR_SRCH){
				if(jsondata.success){
					showConfirmDialog(jsondata.message, confirmDialogButtons, function(result) {
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
			}else if(controlName == CT_BTN_EIDA_REFRESH && jsonData.message == 'DIRECT-FIRST'){
				refNo = jsonData.data;
				var jspURL = 'https://adcbeida.adcbapps.local/EIDAFCUBS/login.jsp?channelId=WMSBPM&channelRefNo='+refNo;
				var openedWindow = window.open(jspURL,"newWin","location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,left=100,top=20"); 
				if(openedWindow.closed){
					executeServerEvent(CT_BTN_EIDA_REFRESH, EVENT_TYPE.CLICK,'DIRECT-SECOND', false);
				}
			} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO && controlName == EDIT){
				saveWorkItem();
				completeWorkItem();
			} else if(controlName == 'BTN_SI_MOD'){
				checkSICountAndModifySIGrid();
			}
			break; 
		case EVENT_TYPE.CHANGE:
			if (controlName == CHECKBOX_NATIONALITY_MANUAL){ 
				if(getValue(CHECKBOX_NATIONALITY_MANUAL) == true){
					setStyle('nationality_manual',PROPERTY_NAME.DISABLE, FALSE);
				}
			} else if (controlName == CHECKBOX_EMAIL_MANUAL){ 
				if(getValue(CHECKBOX_EMAIL_MANUAL) == true){
					setStyle('email_manual',PROPERTY_NAME.DISABLE, FALSE);
				}
			} else if (controlName == CHECKBOX_DOB_MANUAL){ 
				if(getValue(CHECKBOX_DOB_MANUAL) == true){
					setStyle('dob_manual',PROPERTY_NAME.DISABLE, FALSE);
				}
			}else if (controlName == CHECKBOX_EIDANO_MANUAL){ 
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
function saveAndNextPreHookDDE(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookDDE: ' + input);
	var workstepName = getWorkItemData('activityName');
	PepNationality();//Dcra
	//added by reyaz ao dcra
	if(getValue('PRIVATE_CLIENT') == 'Y'){
		setStyle('PD_CUSTSEGMENT','disable','true')
	}
	if(getSheetIndex(tabid) == 6) { //changes for dcra
		if(getValue('DCRA_RETRIGGER_FLAG') == 'Y'){
			showMessage('BTN_APP_LEVEL_RISK', 'Please Click on Calculate Application level Risk Button', 'error');
			return false;
		}
	}
		
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('save and next response:: '+response);
	console.log('saveAndNextPreHookDDE: ' + getSheetIndex(tabid));
	if(response != undefined && response != ''){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			/*if((jsondata.message).indexOf(CA0126) != -1 || (jsondata.message).indexOf(CR0002) != -1){
				var arr = jsondata.message.split('###');
				showConfirmDialog(arr[1], confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent('postMobileConfirm', EVENT_TYPE.CLICK, 'yes', true);
//						return true;
					} else if(result == false) {
						executeServerEvent('postMobileConfirm', EVENT_TYPE.CLICK, 'no', true);
						setFocus(arr[0]);
						return false;
					}
				});
			} else {
				return false;
			}*/
			return false;
		}
		if(getSheetIndex(tabid) == 8) {
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
	       //added by shivanshu for mandatory alert for eida expiry date
	       if(getSheetIndex(tabid) == 2){ 
		  mandatoryEidaNo();
	    } 
		if(getSheetIndex(tabid) == 3){ //changes for dcra
		  mandatoryMultiDropDownField();
	    } 
		
		if(getSheetIndex(tabid) == 8) { //changes for dcra
			if(getValue('DCRA_RETRIGGER_FLAG') == 'Y'){
				selectSheet('tab3','6');
			    setStyle('BTN_APP_LEVEL_RISK','disable','false');
				return false;
			}
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, input, false);
	return true; 
}

function onRowClickDDE(listviewId,rowIndex) {
	if(listviewId == PRODUCT_QUEUE) {
		return true;
	} else if(listviewId == ACCINFO_UDF_LIST) {
		return true;
	} else {
		return false;
	}
}

function selectRowHookDDE(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId) {
		setFieldValue(SELECTED_ROW_INDEX, (getValueFromTableCell(ACC_RELATION, selectedRowsArray[0], 0)-1));
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

function prehookSaveTabDDECustAccountInfo(tabId){
//	var input = event.target.innerHTML+','+getSheetIndex(tabId);
	var input = getSheetIndex(tabId);
	console.log('input prehookSaveTabDDECustAccountInfo: ' + input);
	console.log('inside prehookSaveTabDDECustAccountInfo, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, sheetIndex, false);
}

/*function handleJSPResponseDDE(typeOfJsp, data) {
	var workstepName = getWorkItemData('activityName');
	console.log('handleJSPResponseDDE data: ' + data);
	if(typeOfJsp == 'productList'){
		if(data.length > 0){
			var selectedRows = getSelectedRowsIndexes(PRODUCT_QUEUE); 
			if(undefined != selectedRows[0]) {
				executeServerEvent(BTN_PRD_SEARCH, 'handlingJSPData', data+'@@@'+selectedRows[0],true);
			} else {
				executeServerEvent(BTN_PRD_SEARCH, 'handlingJSPData', data+'@@@',true);
			}
		}
		if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
			selectSheet("tab3",7);
			setTabStyle("tab3",12, "visible", "false");
		} else {
			selectSheet("tab3",8);
			setTabStyle("tab3",18, "visible", "false");
		}
	}
}*/

function insideDDETableButtonRetryClick() {
	console.log('inside grid button');
	var wi_name = getWorkItemData('processInstanceId');
	//etTabStyle("tab4",19, "visible", "true");
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
	var jspURL = sLocationOrigin+'/AO/CustomFolder/?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
//	alert(jspURL);
	document.getElementById('PRODUCT_JSP').src=jspURL;
	selectSheet('tab4',19);
	
	//need to add more jsp code 
	
	var workstepName = getWorkItemData('activityName');
	console.log('inside grid button');
		if(WORKSTEPS.DDE_ACCOUNT_INFO == workstepName) {
			showConfirmDialog('Do you really want to generate Documents.', confirmDialogButtons, function(result) {
				if(result == true) {
					enableIndicator();
					saveWorkItem();
					executeServerEvent('RETRY',EVENT_TYPE.CLICK, 'true', false);
				} else if(result == false) {
					showMessage('', 'Some issue in Generating Templates', 'error');
					return;
				}
			});
		}
}

function insideDDETableButtonDocLinkClick() {
	console.log('clicking document linkbutton inside table');
}
