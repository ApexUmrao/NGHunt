var submitFlag = false;
function onQDECustAccountInfoLoad(){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_Account_Info') { //Changed AMeena 03082023
		workstepName = 'QDE_Account_Info';
	}
	var wi_name = getWorkItemData('processInstanceId');
	//setTabStyle('tab4',12, 'visible', 'false');
	setTabStyle('tab4',13, 'visible', 'false'); // Application orchestration
	var controlNames = [SELECTED_ROW_INDEX,'CURR_WS_DETAIL'];
	var controlValues = ['0',workstepName];
	setMultipleFieldValues(controlNames,controlValues);
	setFieldValue('CURR_WS_DETAIL',workstepName); //Changed AMeena 03082023
	if(WORKSTEPS.QDE_ACCOUNT_INFO == workstepName) {
		setStyle(EDIT1, PROPERTY_NAME.DISABLE, 'true');
	}
	onLoadQDE();  	
}

function onLoadQDE(){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_Account_Info') { //Changed AMeena 03082023
		workstepName = 'QDE_Account_Info';
	}
	var wi_name = getWorkItemData('processInstanceId');
	if(workstepName == 'QDE_Account_Info'){
		setTabStyle("tab4",1, "disable", "true");
		selectSheet('tab4',7);
		pepAssesment();
		setKYCFlagPrivateclient(); //AO Dcra
	} else if(workstepName == 'QDE_Cust_Info'){
		selectSheet('tab4',1);
	}
	onLoadDowngradeQDE();
	pepAssesment(); //AO dcra
	setKYCFlagPrivateclient(); //AO Dcra
	//disableNewCust();
	var decision = getValue(CRO_DEC);
	var sCompDec = getValue('COMP_DEC');
	secAccRelCPDDisable();
	var req_type = getValue(REQUEST_TYPE);
	var disableBagSet = [SEC_DOC_REQ_CRO,NEW_DEL_MODE,BRNCH_OF_INSTANT_ISSUE,EXISTING_NOM_PRSN];//removed BUTTON_SUBMIT
	//var disableBagSet = [Frame17,Frame2,SEC_DOC_REQ_CRO,NEW_DEL_MODE,CHANNEL_NAME,AO_EXISTING_NOM_PRSN,AO_BRNCH_OF_INSTANT_ISSUE];
	disableControls(disableBagSet);
	//setTabStyle("tab4",12, "visible", "false");
	var BagsetOne = [SEC_CAT_CHNG,SEC_INTERNAL_DETL,SEC_ACC_INFO_ECD];
	if(req_type == "New Account With Category Change" || req_type == "Category Change Only") {	
		var BagsetTwo = [OLD_RM_CODE_CAT_CHANGE,OLD_RM_NAME_CAT_CHANGE,OLD_CUST_SEGMENT];
		disableControls(BagsetTwo);
		enableControls(BagsetOne);
		if(req_type == "Category Change Only") {
			setStyle(SEC_ACC_INFO_PD,PROPERTY_NAME.DISABLE,"true");
		}
	} else {
		disableControls(BagsetOne);
		setStyle(SEC_ACC_INFO_PD,PROPERTY_NAME.DISABLE,"false");
		enableControls(BagsetOne);
	}
	if(req_type == "Downgrade"){
		setStyle(OLD_CUST_SEGMENT,PROPERTY_NAME.DISABLE,'true');
	}

	//var clearField = [CRO_REMARKS];//PRODUCT_QUEUE
	//clearAOControls(clearField);
	setFieldValue(CRO_REMARKS, '');
	if(decision == "Permanent Reject - Discard" || sCompDec == "Negative Advisory"){
		// NGMakeFormReadOnly();
		var bagSetThree = [NEG_INFO,FATF,KYC,HIDDEN_SEC_ACC_REL,SEC_INT_DETAI,SEC_GEN_INFO,SEC_EMPLYMNT_DETAILS,
		                   SEC_ASSESS_OTH_INFO,SEC_ACC_INFO_ECD,SEC_ACC_INFO_DCL,SEC_INTERNAL_DETL]; //to be added Frame20,QDE_KYC
		var bagSetFour = [CRO_DEC,CRO_REMARKS,CRO_REJ_REASON];
		disableControls(bagSetThree);
		enableControls(bagSetFour);
		secAccRelCPDDisable();
		manualFrameCPDDisable();
		if(workstepName == "QDE_Account_Info") {
			//Frame23_CPD_Disable(); to be made
			setStyle(NOM_REQ,PROPERTY_NAME.DISABLE,"true");
			Frame_delivery();// in java sanal 
			secDelAddCPDDisable();

		}	
		if(getValue("COUNT_WI") != "0") 
		{
			setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,"true");
		}
		if(decision == "Permanent Reject - Discard" || sCompDec == "Negative Advisory")
		{
			/*clearCombo(CRO_DEC);
			 NGAddItem("AO_CRO_DEC","--Select--");// JAVA
			 NGAddItem("AO_CRO_DEC","Permanent Reject - Discard");// JAVA
			 setNGListIndex("AO_CRO_DEC",0);// JAVA
			 */		
		}
	}
	var req_type = getValue(REQUEST_TYPE);
	if(req_type == "New Account with Category Change" || req_type == "Category Change Only") {						
		if(getValue(NEW_CUST_SEGMENT) != ("")){

		}
		else{
			var bagSetFive = [IS_SALARY_TRANSFER_CAT_CHANGE,IS_MORTAGAGE_CAT_CHANGE,IS_INSURANCE_CAT_CHANGE,
			                  IS_TRB_CAT_CHANGE,IS_OTHERS_CAT_CHANGE,IS_VVIP,IS_PREVILEGE_TP_CAT_CHANGE,
			                  IS_ENTERTAINMENT_CAT_CHANGE,IS_SHOPPING_CAT_CHANGE,IS_SPORT_CAT_CHANGE,
			                  IS_TRAVEL_CAT_CHANGE,IS_EXCELLENCY_TP_CAT_CHANGE,IS_CAT_BENEFIT_OTHER,
			                  CAT_BENEFIT_OTHER];
			disableControls(bagSetFive);
		}
		setResidentWithoutEidaComboQDE();
		//deleteECBCallsDetails();//to be made in java
	}
	console.log('executeServerEvent');
	setFieldValue('TXT_CURRWS',workstepName);
	setStyle('RA_IS_CUST_VVIP',PROPERTY_NAME.DISABLE,'false');
	setStyle('PER_INC_TAX_CON_1',PROPERTY_NAME.DISABLE,'true');
	setStyle('PER_INC_TAX_CON_2',PROPERTY_NAME.DISABLE,'true');
	setStyle('PER_INC_TAX_CON_3',PROPERTY_NAME.DISABLE,'true'); 
	if(WORKSTEPS.QDE_ACCOUNT_INFO == workstepName){
		disableControls(disableControlskyc);
	}
	//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	//executeServerEvent(event.target.id, event.type, '', false);  add_product
	if(WORKSTEPS.QDE_ACCOUNT_INFO == workstepName && getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab4",11, "visible", "false");
	}
	openTrsdJsp();
	visiblePreAssesmentField();
}

function handleQDEEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventQDECustomerInfo(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventQDECustomerInfo(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusQDEEvent(event);
	}
}

function clickEventQDECustomerInfo(event){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
		pepAssesment(); //AO dcra
	}
	var pControlName = event.target.id;
	var pControlValue = getValue(pControlName);
	pepAssesment(); //AO dcra
	if(event.target.id == VIEW){
		executeServerEvent(event.target.id, event.type, '', false);
		docApproval();
	}else if(FETCH_INFO == (pControlName)) {
		executeServerEvent(FETCH_INFO, event.type,"", false);
	} else if (RD_INST_DEL == (pControlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	}else if (event.target.id == (BUTTON_SUBMIT)) {
		saveWorkItem();
		if(getValue(REQUEST_TYPE) == 'Downgrade' && getValue('NEW_RM_NAME_CAT_CHANGE') == '' ){
			//&&getValue('NEW_CUST_SEGMENT') == 'Aspire'){
			showMessage(NEW_RM_NAME_CAT_CHANGE,"Please select New RM Name.", 'error');
			return false;
		}
		var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
		var resultFATCACheck = checkAttchedDocument('FATCA#');
		console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
		executeServerEvent(event.target.id, event.type, resultEIDACheck+'%%%'+resultFATCACheck, false);
//		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == DC_BTN_ADD){
			executeServerEvent(DC_BTN_ADD, EVENT_TYPE.CLICK, '', false);
	} else if (event.target.id == BTN_VALIDATE ) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == BTN_VALIDATEFATCA ) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'Command55'){ //check for save sahils
		var selectedRows = getSelectedRowsIndexes('delivery_preference'); 
		var iSelectedRow = selectedRows[0];  
		executeServerEvent(event.target.id, event.type, iSelectedRow, false);
	}/* else if (workstepName == 'DDE_CUST_INFO') {//fardeen
		fillManualDataInBelowFields(pControlName,pControlValue);
		fillFCRDataInBelowFields(pControlName,pControlValue);
		fillEIDADataInBelowFields(pControlName,pControlValue);
	}*/ else if(event.target.id == 'product_search'){				 ///Code started By Kishan
		var wi_name = getWorkItemData('processInstanceId');
		//setTabStyle("tab3",12, "visible", "true");
		setTabStyle("tab3",13, "visible", "true"); // Application orchestration
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var jspURL = sLocationOrigin+'/AO/CustomFolder/product_list.jsp?WI_NAME='+h	+'&ACC_CLASS='+
		getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
		document.getElementById('PRODUCT_JSP').src=jspURL;
		//selectSheet('tab3',12);
		selectSheet('tab3',13);// Application orchestration
	} //CT_BTN_REFRESH
	else if(event.target.id == CT_BTN_REFRESH){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == FCR_COUNTRYBIRTH){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == EIDA_COUNTRYBIRTH){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == MANUAL_COUNTRYBIRTH){
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == DC_BTN_REMOVE){ 					 //Fardeen code Added from here
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == VIEW){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'COMMAND24'){               
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'COMMAND27'){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'COMMAND28'){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'SEARCH'){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == MANUAL_CNTRY){ 						 //Naga Sir Sir Code Added from here
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_TRSD_CHECK){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == MANUAL_RESIDENT){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == UDF_ADDROW){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRS_ADD){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRS_CERTI_YES){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRS_CERTI_NO){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRS_CHECKBOX){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == IDS_CB_VVIP){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_DEDUPE_SEARCH){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_SELECT_CUST){
		var selectedRows = getSelectedRowsIndexes(LVW_DEDUPE_RESULT); 
		if(selectedRows[0] != undefined) {
			executeServerEvent(event.target.id, event.type, selectedRows[0], false);
		} else {
			executeServerEvent(event.target.id, event.type, '', false);
		}
	} else if(event.target.id == ARE_U_PEP){ 
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == BTN_SRC_CODE){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_SRC_MAKER){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'add_AccInfo_UdfList'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (EDIT)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'BTN_FG_VALIDATE'){	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'BTN_APP_LEVEL_RISK'){	//added by reyaz  14-09-2023
		executeServerEvent(event.target.id, event.type, '', false);
	} 
//	var selectedRows =getSelectedRowsIndexes(LISTVIEW_PARTY); add_AccInfo_UdfList
//	var iSelectedRow = selectedRows[0];  
}

function changeEventQDECustomerInfo(event){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	var pControlName = event.target.id;
	var controlName = event.target.id;
	var pControlValue = getValue(pControlName);
	var controlvalues;
	/*if (workstepName == 'DDE_CUST_INFO') {//fardeen 
		fillManualDataInBelowFields(pControlName,pControlValue);
		fillFCRDataInBelowFields(pControlName,pControlValue);
		fillEIDADataInBelowFields(pControlName,pControlValue);
	}*/
	if (event.target.id == MANUAL_PER_CNTRY) {
		//var controlnames = [PA_OTHERS];//, CONTACT_DETAILS_CITY_OTHERS];
		//clearAOControls(controlnames);//, controlvalues);
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(controlName == (DFC_STATIONERY_AVAIL)) { 
		executeServerEvent(DFC_STATIONERY_AVAIL, event.type,"", false);
	}else if (RD_INST_DEL == (controlName)){
		executeServerEvent(RD_INST_DEL, event.type,"", false);
	}else if (NOM_REQ == (controlName)){
		executeServerEvent(NOM_REQ, event.type,"", false);			
	} else if(EXISTING_NOM_PRSN == (controlName)) {
		executeServerEvent(EXISTING_NOM_PRSN, event.type,"", false);
	}   else if (event.target.id == BTN_VALIDATE ) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == CHECKBOX_STATE_MANUAL){
		console.log(getValue(CHECKBOX_STATE_MANUAL)); 
		var val = getValue(CHECKBOX_STATE_MANUAL);
		console.log(val); 
		if(val == true){
			setFieldValue(CHECKBOX_CITY_MANUAL,'true');
			setStyle('city_manual','disable','false');
		}else{
			setFieldValue(CHECKBOX_CITY_MANUAL,'false');
			setStyle('city_manual','disable','true');
		}
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_PREFIX ) {
		executeServerEvent(event.target.id, event.type, '', false);
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
	} else if (event.target.id == BTN_VALIDATEFATCA ) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_FIRSTNAME || event.target.id == MANUAL_LASTNAME) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_MOTHERNAME) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == MANUAL_STATE){
		var manualstate = getValue(MANUAL_STATE);
		var controlnames = [MANUAL_CITY];
		var controlvalues = [manualstate];
		setMultipleFieldValues(controlnames, controlvalues);
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == (CHECKBOX_PASSPORT_TYPE_MANUAL) || event.target.id == (CHECKBOX_PASSPORT_TYPE_FCR) 
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
	} else if (event.target.id == (FAT_US_PERSON)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == ARE_U_PEP){ 
		PepNationality();
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == EMP_STATUS){
		//EmpStatusCheck();
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == RA_IS_CUST_PEP){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == COMBODOC){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRO_DEC){ 
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id ==CRS_CB){
		CRSTabSwitch();
	}else if(event.target.id =='ACC_INFO_UDF_VALUE'){ 
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id =='ACC_INFO_UDF_FIELD'){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'visaStatus_manual'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'VISANO_MANUAL'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'DEL_MODE_YES'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == 'DEL_MODE_NO'){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == RA_CB_PRECIOUS_STONE_DEALER){
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == RA_CB_BULLN_COMMDTY_BROKR){
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == RA_CB_REAL_STATE_BROKR){ 
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == RA_CB_USD_AUTO_DEALER){ //RA_CB_USD_AUTO_DEALER
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == FINANCIAL_BROKERS){ //FINANCIAL_BROKERS
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == NOTARY_PUBLIC){ //
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == SOCIAL_MEDIA_INFLUNCER){ //SOCIAL_MEDIA_INFLUNCER
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == RA_CB_OTHERS) {
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == UDF_Field){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == UDF_Value){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_FIRSTNAME || event.target.id == MANUAL_LASTNAME) {
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_1)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_2)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == (PER_INC_TAX_CON_3)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == 'SUB_PERSONAL_TAX_0' || event.target.id == 'SUB_PERSONAL_TAX_1') {
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
	} else if(event.target.id == MANUAL_CNTRY){ 						 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == MANUAL_MOBILE){
		executeServerEvent(event.target.id, event.type,"", false);
	} else if (event.target.id == RA_CB_GEN_TRDNG_CMPNY) {
		businessNature(event);
		//executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == NEW_CUST_SEGMENT){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (QDE_MODE_OF_FUND)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == (QDE_TRNSFR_FRM_ACC)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == ('LISTVIEW_PUR_ACC_RELATION')) {	
			executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == ('ADDITIONAL_SOURCES_INCOME_AED')) {	
			//AddSourceIncome();
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == 'Customer_PEP_Rad_0' || event.target.id == 'Person_Associated_Rad_0' || event.target.id =='Person_Power_Rad_0'|| event.target.id =='Customer_Entrusted_Man_Rad_0'|| event.target.id =='Customer_Authorized_Rad_0'|| event.target.id =='Customer_Entrusted_Rad_0'||event.target.id == 'Customer_PEP_Rad_1' || event.target.id == 'Person_Associated_Rad_1' || event.target.id =='Person_Power_Rad_1'|| event.target.id =='Customer_Entrusted_Man_Rad_1'|| event.target.id =='Customer_Authorized_Rad_1'|| event.target.id =='Customer_Entrusted_Rad_1') {	
		pepLogic();
	//Added by Shivanshu For TIN Country Field 13/03/25
	}else if (event.target.id == CRS_TAX_COUNTRY) {
		executeServerEvent(event.target.id, event.type, '', false);
	}
}

function lostFocusQDEEvent(event) { //yamini
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
	if(WORKSTEPS.DDE_CUSTOMER_INFO == (workstepName)) {	
	}
}	

function saveAndNextPreHookQDE(tabid){
//	var input = getSheetIndex(tabid);
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	//if(getSheetIndex(tabid) == 11 ) {
	PepNationality();//AO Dcra
	AddSourceIncome();
	if(getSheetIndex(tabid) == 12 ) {// Decision Tab
		setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');

	}

	if(getSheetIndex(tabid) == 5) { //changes for dcra
		if(getValue('DCRA_RETRIGGER_FLAG') == 'Y'){
			showMessage('BTN_APP_LEVEL_RISK', 'Please Click on Calculate Application level Risk Button', 'error');
			return false;
		}
	}
//	if(getSheetIndex(tabid) == 'tab4' ) {// Decision Tab
	//	disableNewCust();
	//	}
	console.log('Input saveAndNextPreHookDDE: ' + input);
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('Save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
		if(getSheetIndex(tabid) == 7) {
			if((getValue(REQUEST_TYPE) != 'Category Change Only')){
				var prodCount = getGridRowCount(PRODUCT_QUEUE);
				for(var i=0; i<prodCount; i++){
					if(getValueFromTableCell(PRODUCT_QUEUE, i, 3) == '') {
						showMessage(PRODUCT_QUEUE,"Please select currency.", 'error');
						return false;
						break;
					}
				}			
			}
		}
		if(getSheetIndex(tabid) == 2){ //changes for dcra
		  mandatoryMultiDropDownField();
	    } 
		if(getSheetIndex('tab4') == 7) { //changes for dcra
			if(getValue('DCRA_RETRIGGER_FLAG') == 'Y'){
				selectSheet('tab4','5');
				setStyle('BTN_APP_LEVEL_RISK','disable','false');
				return false;
			}
		}if(getSheetIndex('tab4') == 9) {
			if(getValue(REQUEST_TYPE) == 'Downgrade' && getValue('NEW_RM_NAME_CAT_CHANGE') == '' ){
				//&&getValue('NEW_CUST_SEGMENT') == 'Aspire'){
				showMessage(NEW_RM_NAME_CAT_CHANGE,"Please select New RM Name.", 'error');
				return false;
			}
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, input, false);	
	disableIndicator();
	return true; 
}

function onClickTabQDEAccountInfo(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	if(tabId == 'tab4') {
		PepNationality();//AO Dcra
		//if(sheetIndex == 11){
		if(sheetIndex == 12){
			setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
		} else {
			setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
		}
	 }

	var response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
	var jsonData = handleAOResponse(response);
	console.log(jsonData);
}

function postServerEventHandlerQDECustomer(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ("" != jsonData.message && null != jsonData.message) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
				if(!(jsonData.message.indexOf('Do you still want to proceed with account opening?') != -1)) {
					showMessage(arr[0], arr[1], 'error');
				}
			}
		}
	} else if(jsonData.message.indexOf('does not start with 971') != -1 || 
			jsonData.message.indexOf('not of 12 digits') != -1 || 
			jsonData.message.indexOf('not of 11 digits') != -1) {
		var arr = jsonData.message.split('###');
		showMessage('', arr[1], 'error');
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
	var workstepName = getWorkItemData('activityName');
		setFieldValue('CURR_WS_DETAIL',workstepName); //Changed AMeena 03082023
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
		} 
		//selectSheet('tab4',1);
		if(controlName == VIEW){
			if(jsonData.success && 'DedupeSelectedIndex' == jsonData.message){
			}
		}
		if(WORKSTEPS.QDE_CUST_INFO == workstepName){
			setStyle(ACC_RELATION,PROPERTY_NAME.DISABLE,'true');
		} else {
			setStyle(ACC_RELATION,PROPERTY_NAME.DISABLE,'false');
		}
		var workstepName = getWorkItemData('activityName');
		if(workstepName == 'QDE_ Account_Info') {
			workstepName = 'QDE_Account_Info';
		}
		setStyle('IS_COMPLIANCE_NAME_SCR',PROPERTY_NAME.DISABLE,'true');
		setStyle('IS_COMPLIANCE_RISK_ASSESS',PROPERTY_NAME.DISABLE,'true');
		setStyle('IS_PROD_APP_REQ',PROPERTY_NAME.DISABLE,'true');
		setStyle('IS_CALL_BACK_REQ',PROPERTY_NAME.DISABLE,'true');
		setStyle('L1_APP_REQ',PROPERTY_NAME.DISABLE,'true');
		setStyle('L2_APP_REQ',PROPERTY_NAME.DISABLE,'true');
		setStyle('L3_APP_REQ',PROPERTY_NAME.DISABLE,'true');
		setStyle('L4_APP_REQ',PROPERTY_NAME.DISABLE,'true');

		if(workstepName == 'QDE_Cust_Info'){
			setStyle('L1_APP_REQ',PROPERTY_NAME.VISIBLE,'false');
			setStyle('L2_APP_REQ',PROPERTY_NAME.VISIBLE,'false');
			setStyle('L3_APP_REQ',PROPERTY_NAME.VISIBLE,'false');
			setStyle('L4_APP_REQ',PROPERTY_NAME.VISIBLE,'false');
		}
		//CRO_DEC
		setStyle('CRO_DEC',PROPERTY_NAME.DISABLE,'false');
		workstepName = getWorkItemData('activityName');
		if(workstepName == 'QDE_ Account_Info') {
			workstepName = 'QDE_Account_Info';
		}
		if(workstepName == 'QDE_Account_Info'){
			//disableFildSanctionScreening();
			disableTabAccountinfo();
			enableFieldsAccountinfo();
		}	  	
		setStyle('PER_INC_TAX_CON_1',PROPERTY_NAME.DISABLE,'true');
		setStyle('PER_INC_TAX_CON_2',PROPERTY_NAME.DISABLE,'true');
		setStyle('PER_INC_TAX_CON_3',PROPERTY_NAME.DISABLE,'true');
		if(workstepName == 'QDE_Account_Info' && (getValue(REQUEST_TYPE) == "New Account With Category Change" 
			|| getValue(REQUEST_TYPE) == "Category Change Only" || getValue(REQUEST_TYPE) == "Upgrade")) {	
			setTabStyle("tab4",9, "visible", "true");
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
		pepLogic();
		break;
	case EVENT_TYPE.CLICK:
		if(controlName == 'TABCLICK'){
			//if(getSheetIndex('tab4') == 11 ) {
			if(getSheetIndex('tab4') == 12 ) {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
			if(jsonData.success && submitFlag) {
				submitFlag = false;
				var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
				var resultFATCACheck = checkAttchedDocument('FATCA#');
				console.log('doc result'+resultEIDACheck+', '+resultFATCACheck);
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
				executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'+resultFATCACheck, false);
			}
		} else if('saveNextTabClick' == controlName && jsonData.success) {
			/*if(getSheetIndex('tab4') == 11 ) {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab4'), false);*/
		} else if('afterSaveNext' == controlName) {
			var label = '';
			console.log('afterSaveNext sheet: '+getSheetIndex('tab3'));
			//if(getSheetIndex('tab4') == 11 ) {
			if(getSheetIndex('tab4') == 12 ) {
				submitFlag = true;
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab4'), false);
		} else if(controlName == 'BTN_APP_LEVEL_RISK') {
			setStyle('BTN_APP_LEVEL_RISK','disable','true');
			setFieldValue('DCRA_RETRIGGER_FLAG','N');
		} else if (controlName == BUTTON_SUBMIT){ 
			if(WORKSTEPS.QDE_ACCOUNT_INFO == workstepName && jsonData.message.indexOf('$$$confirm') != -1) { 
				var confirm = jsonData.message.split('$$$');
				showConfirmDialog(confirm[0], confirmDialogButtons, function(result) {
					if(result == true) {
						showConfirmDialog(CA008, confirmDialogButtons, function(result) {
							if(result == true) {
								executeServerEvent('postSubmit', event.type, '', false);
							} else if(result == false) {
								setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
								return;
							}
						});
					} else if(result == false) {
						setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
						setFocus(PRODUCT_QUEUE);
						return;
					}
				});
			} else if(jsonData.success) {
				if(WORKSTEPS.QDE_ACCOUNT_INFO == workstepName) {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('postSubmit', event.type, '', false);
//							saveWorkItem();
//							completeWorkItem();
						} else if(result == false) {
							setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
							saveWorkItem();
							return;
						}
					});
				} else if(WORKSTEPS.QDE_CUST_INFO == workstepName) {
					saveWorkItem();
					completeWorkItem();
				} 
			} else if(jsonData.message.indexOf('Do you still want to proceed with account opening?') != -1) {
				showConfirmDialog(jsonData.message, confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'confirmAppRisk', false);
					} else if(result == false) {
						setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
						return;
					}
				});
			} else {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}			
		} else if (controlName == 'postSubmit' && jsonData.success){ 
			saveWorkItem();
			completeWorkItem();
		} else if(controlName == 'AccInfo_UdfList'){
			//clearComparisonFields();
			if(jsonData.success === false) {
				return false;
			}
		} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO && controlName == EDIT){
			saveWorkItem();
			completeWorkItem();
		}
		break;
	case EVENT_TYPE.CHANGE:
		if(getValue(CHECKBOX_DOB_MANUAL) == true){
			setStyle('dob_manual',PROPERTY_NAME.DISABLE, FALSE);
		} else {
			setStyle('dob_manual',PROPERTY_NAME.DISABLE, TRUE);
		}
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
		setStyle('product_search',PROPERTY_NAME.DISABLE,'false');  //
		setStyle('ACC_TITLE',PROPERTY_NAME.DISABLE,'false');
		setStyle('add_product',PROPERTY_NAME.DISABLE,'false');
	}

}
function prehookSaveTabQDECustAccountInfo(tabId){
	console.log('inside prehookSaveTabDDECustAccountInfo, tabId: '+tabId);
	var sheetIndex = getSheetIndex(tabId);
	executeServerEvent('SAVE_TAB_CLICK_QDE', EVENT_TYPE.CLICK, sheetIndex, false);
}

/*function disableFildSanctionScreening(){
	setStyle('BTN_TRSD_CHECK',PROPERTY_NAME.DISABLE,'true');
	setStyle('TRSD_REMARKS',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANC_FINAL_ELIGIBLITY',PROPERTY_NAME.DISABLE,'true');
	setStyle('BTN_SANC_CALCULATE',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANCT_RISK_CID',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANCT_RISK_NAME',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANCT_RISK_CURRENT_RSK_SYSTEM',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANCT_RISK_CURRENT_RSK_BANK',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANCT_RISK_PREVIOUS_RSK',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANCT_RISK_COMPL_APP_REQ',PROPERTY_NAME.DISABLE,'true');
	setStyle('BTN_NEXT_CUST_SANCT',PROPERTY_NAME.DISABLE,'true');
	setStyle('SANC_FINAL_ELIGIBILITY',PROPERTY_NAME.DISABLE,'true');
	setStyle('TRSD_HISTORY_LVW',PROPERTY_NAME.DISABLE,'true');
}*/
function disableTabAccountinfo(){
	setTabStyle("tab4",8, "disable", "true"); //delivery pref
	setTabStyle("tab4",2, "disable", "true"); //kyc
	setTabStyle("tab4",5, "disable", "true"); //sanction screening
	setTabStyle("tab4",6, "disable", "true"); //application assessment
}

function enableFieldsAccountinfo(){
	//KYC Fields
	setStyle(DRP_RESEIDA,PROPERTY_NAME.ENABLE,'true');
	setStyle(ED_MONTHLY_INCM,PROPERTY_NAME.DISABLE,'false');
	setStyle(SECRET_QUES,PROPERTY_NAME.DISABLE,'false');
	setStyle(SECRET_ANS,PROPERTY_NAME.ENABLE,'true');
	setStyle(CONGCIERGE,PROPERTY_NAME.ENABLE,'true');
}

function CRSTabSwitch(){
	if(getValue('CRS_CB')== false){
		setTabStyle('tab4',4, 'disable', 'true');
		setStyle('CRS_CB',PROPERTY_NAME.DISABLE,'false');
		setStyle('PER_INC_TAX_CON_1',PROPERTY_NAME.DISABLE,'true');
		setStyle('PER_INC_TAX_CON_2',PROPERTY_NAME.DISABLE,'true');
		setStyle('PER_INC_TAX_CON_3',PROPERTY_NAME.DISABLE,'true');
	}
	else{
		setTabStyle('tab4',4, 'disable', 'false');
		setStyle('CRS_CB',PROPERTY_NAME.DISABLE,'false');
		setStyle('PER_INC_TAX_CON_1',PROPERTY_NAME.DISABLE,'true');
		setStyle('PER_INC_TAX_CON_2',PROPERTY_NAME.DISABLE,'true');
		setStyle('PER_INC_TAX_CON_3',PROPERTY_NAME.DISABLE,'true');
	}
}

function customListViewValidationQDE(controlId,flag){
	if(controlId == 'AccInfo_UdfList'){
		if (flag == 'A'){
			console.log('Inside customListViewValidationQDE');
			var response = executeServerEvent(controlId, EVENT_TYPE.CLICK, '', true);
			var jsonData = handleAOResponse(response);
			if (!jsonData.success){
				return false;
			}
			else{
				return true;
			}
		}
	}	
	return true ;	
}

function EmpStatusCheck(){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(workstepName == 'QDE_Cust_Info' && getValue(EMP_STATUS) != '') {
		if (getValue(EMP_STATUS) == 'Employed') {
			setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setFieldValue(RA_CB_GEN_TRDNG_CMPNY, 'false');
			setFieldValue(RA_CB_PRECIOUS_STONE_DEALER,'false');
			setFieldValue(RA_CB_BULLN_COMMDTY_BROKR,'false');
			setFieldValue(RA_CB_REAL_STATE_BROKR, "false");
			setFieldValue(RA_CB_USD_AUTO_DEALER, 'false');

			setStyle(EMPLYR_TYPE1,PROPERTY_NAME.DISABLE, FALSE);
			setStyle('BUSINESS_NATURE_SECTION_FRM',PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_IS_CUST_WRKNG_NON_UAE,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_IS_CUST_WRKNG_UAE,PROPERTY_NAME.DISABLE, FALSE);

			//enableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM,
			//RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
		} else if ( getValue(EMP_STATUS)== "Self Employed") {
			setFieldValue(RA_IS_CUST_WRKNG_UAE, '');
			setFieldValue(RA_IS_CUST_WRKNG_NON_UAE, '');
			setFieldValue(EMPLYR_TYPE1, '');
			setStyle(EMPLYR_TYPE1,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_NON_UAE,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_UAE,PROPERTY_NAME.DISABLE, TRUE);

			setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_OTHERS,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
			//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM,
			//	RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
		} else if ( getValue(EMP_STATUS)== "Housewife") {
			setFieldValue(RA_IS_CUST_WRKNG_UAE, '');
			setFieldValue(RA_IS_CUST_WRKNG_NON_UAE, '');
			setFieldValue(EMPLYR_TYPE1, '');
			setStyle(EMPLYR_TYPE1,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_NON_UAE,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_UAE,PROPERTY_NAME.DISABLE, TRUE);

			setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_OTHERS,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
			//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM,
			//	RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
		} else if ( getValue(EMP_STATUS)== "Others" || getValue(EMP_STATUS)== "Student" ||getValue(EMP_STATUS)== "Unemployed") {
			setFieldValue(RA_IS_CUST_WRKNG_UAE, '');
			setFieldValue(RA_IS_CUST_WRKNG_NON_UAE, '');
			setFieldValue(EMPLYR_TYPE1, '');
			setStyle(EMPLYR_TYPE1,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_NON_UAE,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_UAE,PROPERTY_NAME.DISABLE, TRUE);

			setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_OTHERS,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
			//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM,
			//	RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
		}  else {
			setStyle(EMPLYR_TYPE1,PROPERTY_NAME.DISABLE, TRUE);
			setStyle('BUSINESS_NATURE_SECTION',PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_NON_UAE,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_IS_CUST_WRKNG_UAE,PROPERTY_NAME.DISABLE, TRUE);
			//disableControls(new String[] { EMPLYR_TYPE1,BUSINESS_NATURE_SECTION_FRM, 
			//RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
			if (getValue(EMP_STATUS) =='Retired') {
				//disableControls(new String[] { BUSINESS_NATURE_SECTION_FRM });
				setStyle('BUSINESS_NATURE_SECTION',PROPERTY_NAME.DISABLE, TRUE);
				//enableControls(new String[] { RA_IS_CUST_WRKNG_NON_UAE,RA_IS_CUST_WRKNG_UAE });
				setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, TRUE);
				setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, TRUE);
				setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, TRUE);
				setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, TRUE);
				setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, TRUE);
				setStyle(RA_CB_OTHERS,PROPERTY_NAME.DISABLE, TRUE);
				setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
				setFieldValue(RA_CB_GEN_TRDNG_CMPNY,'false');
				setFieldValue(RA_CB_PRECIOUS_STONE_DEALER, 'false');
				setFieldValue(RA_CB_BULLN_COMMDTY_BROKR,'false');
				setFieldValue(RA_CB_REAL_STATE_BROKR,'false');
				setFieldValue(RA_CB_USD_AUTO_DEALER,'false');
				setFieldValue(RA_CB_OTHERS,'false');
			}
			else{
				setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, FALSE);
				setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, FALSE);
				setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, FALSE);
				setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, FALSE);
				setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, FALSE);
				setStyle(RA_CB_OTHERS,PROPERTY_NAME.DISABLE, FALSE);
				setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, FALSE);
			}
		}
		if (!getValue(EMP_STATUS)=='Self Employed') {
			setStyle(IDS_CB_VVIP,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(GI_YEARS_IN_UAE,PROPERTY_NAME.DISABLE, TRUE);
			//disableControls(IDS_CB_VVIP,GI_YEARS_IN_UAE });
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		} else {
			//enableControls(new String[] { IDS_CB_VVIP,GI_YEARS_IN_UAE });
			setStyle(IDS_CB_VVIP,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(GI_YEARS_IN_UAE,PROPERTY_NAME.DISABLE, FALSE);
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}

	}
}

function businessNature(event) {
	if (event.target.id == RA_CB_PRECIOUS_STONE_DEALER) {   //Pending for check  RA_CB_OTHERS
		if (getValue(IDS_CB_VVIP) == 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == RA_CB_BULLN_COMMDTY_BROKR) {     //Pending for check
		if (getValue(IDS_CB_VVIP) == 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == RA_CB_REAL_STATE_BROKR) {       //Pending for check
		if (getValue(IDS_CB_VVIP)== 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == RA_CB_USD_AUTO_DEALER) {   	//pending for check	
		if (getValue(IDS_CB_VVIP) == 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == RA_CB_GEN_TRDNG_CMPNY) {   	//pending for check	
		if (getValue(IDS_CB_VVIP) == 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		} 
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == FINANCIAL_BROKERS) {     //Pending for check
			if (getValue(IDS_CB_VVIP) == 'true') {
				setFieldValue(IDS_CB_VVIP, 'false');
				setFieldValue(GI_YEARS_IN_UAE, '');
			}
			setFieldValue(RA_CB_OTHERS,'false');
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
			setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == NOTARY_PUBLIC) {     //Pending for check 
		if (getValue(IDS_CB_VVIP) == 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	}else if (event.target.id == SOCIAL_MEDIA_INFLUNCER) {     //Pending for check SOCIAL_MEDIA_INFLUNCER
		if (getValue(IDS_CB_VVIP) == 'true') {
			setFieldValue(IDS_CB_VVIP, 'false');
			setFieldValue(GI_YEARS_IN_UAE, '');
		}
		setFieldValue(RA_CB_OTHERS,'false');
		setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
		setFieldValue(RA_CB_OTHERS_FLD,'');
	} else if (event.target.id == 'RA_CB_OTHERS') {   	//pending for check	
		var value = getValue('RA_CB_OTHERS');
		if (value) {
			//setFieldValue(IDS_CB_VVIP, 'false');
			//setFieldValue(GI_YEARS_IN_UAE, '');
			/*setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, TRUE);
			//setStyle(RA_CB_OTHERS,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(FINANCIAL_BROKERS,PROPERTY_NAME.DISABLE, TRUE);
			setStyle(NOTARY_PUBLIC,PROPERTY_NAME.DISABLE, TRUE);*/
			setFieldValue(RA_CB_GEN_TRDNG_CMPNY,'false');
			setFieldValue(RA_CB_PRECIOUS_STONE_DEALER, 'false');
			setFieldValue(RA_CB_BULLN_COMMDTY_BROKR,'false');
			setFieldValue(RA_CB_REAL_STATE_BROKR,'false');
			setFieldValue(RA_CB_USD_AUTO_DEALER,'false');
			//setFieldValue(RA_CB_OTHERS,'false');
			setFieldValue(FINANCIAL_BROKERS,'false');
			setFieldValue(NOTARY_PUBLIC, 'false');
			setFieldValue(SOCIAL_MEDIA_INFLUNCER, 'false');
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, FALSE);
		}else {
			/*setStyle(RA_CB_GEN_TRDNG_CMPNY,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_PRECIOUS_STONE_DEALER,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_BULLN_COMMDTY_BROKR,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_REAL_STATE_BROKR,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_USD_AUTO_DEALER,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(FINANCIAL_BROKERS,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(NOTARY_PUBLIC,PROPERTY_NAME.DISABLE, FALSE);
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);*/
			setStyle(RA_CB_OTHERS_FLD,PROPERTY_NAME.DISABLE, TRUE);
			setFieldValue(RA_CB_OTHERS_FLD,'');
		}
	}
}

function accountInfoUDFGrid(){
	executeServerEvent('accountInfoUDFGrid_dialog', 'click', '', true); 
}

function selectRowHookQDEInfo(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId) {
		setFieldValue(SELECTED_ROW_INDEX, (getValueFromTableCell(ACC_RELATION, selectedRowsArray[0], 0)-1));
		var iSelectedRow = getValue(SELECTED_ROW_INDEX);
		if(iSelectedRow != '') {
			var sName = getValueFromTableCell(ACC_RELATION, iSelectedRow, 1);
			var sDOB = getValueFromTableCell(ACC_RELATION, iSelectedRow, 5);
			var sCustId = getValueFromTableCell(ACC_RELATION, iSelectedRow, 2);
			var controlNames = ['TXT_CUSTOMERNAME','TXT_DOB',TXT_CUSTOMERID];
			var controlValues = [sName,sDOB,sCustId];
			setMultipleFieldValues(controlNames,controlValues);
		}
		return false;
	}
}

function onRowClickQDE(listviewId,rowIndex) {
	if(listviewId == PRODUCT_QUEUE) {
		return true;
	} else if(listviewId == ACCINFO_UDF_LIST) {
		return true;
	} else {
		return false;
	}
}
function onLoadDowngradeQDE() { // changes for downgrade krishna
 var requestType = getValue(REQUEST_TYPE);
 if(requestType == ('Downgrade')){
	 selectSheet('tab4',1);
	 setTabStyle('tab4',2, 'visible', 'false');
	 setTabStyle('tab4',3, 'visible', 'false');
	 setTabStyle('tab4',4, 'visible', 'false'); 
	 setTabStyle('tab4',5, 'visible', 'false');
	 setTabStyle('tab4',6, 'visible', 'false');
	 setTabStyle('tab4',7, 'visible', 'false'); 
	 setTabStyle('tab4',8, 'visible', 'false');
	 setTabStyle('tab4',9, 'visible', 'true');
	 setTabStyle('tab4',10, 'visible', 'false');
	 setTabStyle('tab4',11, 'visible', 'false');
  }
} 
