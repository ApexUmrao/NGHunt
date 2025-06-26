/**
 * Process: TFO
 */

function onFormLoad(){
	console.log('***** inside onFormLoad *****');
	var workstepName = getWorkItemData('activityName');
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	var requestType = getValue(REQUEST_TYPE);
	var fetchModuleValue = getValue(SWIFT_FETCH_MODULE);
	var requestCat = getValue(REQUEST_CATEGORY);
	var processType = getValue(PROCESS_TYPE);
	setValues({[TFO_WI_NAME]: wi_name}, true);
 //appifreception();//Added by Ayush
	if(isReadOnlyForm || workstepName ==WORKSTEP.WORK_EXIT || workstepName ==WORKSTEP.ARCHIVAL){
		//setTabStyle("tab1",9, "visible", "false"); // hide checklist tab //final decision tab on readonly not visible
		//ATP-474 --31-MAY-2024 --JAMSHED START
		if(isReadOnlyForm && requestType=='AM'){
			setStyle('AMENDED_EFFECTIVE_DATE', PROPERTY_NAME.VISIBLE, 'true');
		}
		//ATP-474 --31-MAY-2024 --JAMSHED END
		lockTFOSection(LOCK_PPM_FRAMES);
		if(workstepName == WORKSTEP.TRAYDSTREAM){   //Traydstream |reyaz|09-10-2024| 
		   hideControls(TS_HIDE);
		   disableFieldOnDemand(TS_DISABLE);
		   showControls(TS_SHOW);
		   if('' == getValue(TS_REFERENCE)){
		     enableFieldOnDemand(TS_ENABLE);
		   }
	    }
		//onPMFormLoad(); traydstream |reyaz |atp-520| 
	}else if (workstepName == WORKSTEP.INITIATOR) {
		console.log('locking all sections..');
		setStyle(FRM_SWIFT,"lock","true");
		setStyle(SEC_REQ_DETAILS,"lock","true");
		setStyle(SUBSEC_CUST_DETAILS,"lock","true");
		setValues({['CURR_WS']: workstepName}, true);
		setStyle(FRAME_Decision_Hist, PROPERTY_NAME.VISIBLE, 'false');
		setFetchModule(SWIFT_FETCH_MODULE);		
		setReqByDefault(REQUESTED_BY, requestType, requestCat);
		setTabStyle('tab1',2, "visible", "false");
		setTabStyle('tab1',3, "visible", "false"); //Traydstream |reyaz|atp-518|17-09-2024|
		// enable-disable
		if('Generated' == getValue('IS_ACK_GEN') && !('GA' == getValue('REQUEST_TYPE')) 
				|| 'GAA' == getValue('REQUEST_TYPE')){
			console.log('disabling fields on load: ');
			disableFieldOnDemand('btnPrint,btnDiscard,btnCidFetch,FLG_ACK_GEN,CID_Txt,TRANSACTION_AMOUNT,TrnsRefNo,' +
			'SOURCE_CHANNEL,REQUEST_CATEGORY,REQUEST_TYPE,RELATIONSHIP_TYPE,TRANSACTION_CURRENCY,REQUESTED_BY');
		}
		
		enablefetchfields();	
		processTypeChangeEvent(); 	
		handleSWIFTUI();
               
		enableSwiftCID();
		fieldsEnableDisableTSLM();
		disableFieldForIFCPC();
		setStyle('Hybrid_Customer', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('COUNTER_PARTY_BRMS_FLAG', PROPERTY_NAME.VISIBLE, 'false'); // need to be un commented after the deployment
		setTabStyle("tab1",1, "visible", "false");
		initiatorhybridcombo();
                handleSwiftMtUI(); //ATP-458 REYAZ 05-07-2024
	}
	else if (workstepName == WORKSTEP.LOGISTICS) {
		console.log('locking all sections..');
		setStyle(FRM_SWIFT,"lock","true");
		setStyle(SEC_REQ_DETAILS,"lock","true");
		setStyle(SUBSEC_CUST_DETAILS,"lock","true");
		setStyle(BUTTON_CUSTOMER_ACK, PROPERTY_NAME.VISIBLE, 'false');
		setStyle(FRAME_Decision_Hist, PROPERTY_NAME.VISIBLE, 'true');
		hideControls(HIDE_AT_LOGISTICS);
		var reqType = getValue('REQUEST_TYPE');
		var reqCat = getValue('REQUEST_CATEGORY');
		setTabStyle('tab1',2, "visible", "false");
		setTabStyle('tab1',3, "visible", "false"); //Traydstream |reyaz|atp-518|17-09-2024|
		setStyle('Hybrid_Customer', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('COUNTER_PARTY_BRMS_FLAG', PROPERTY_NAME.VISIBLE, 'false'); 
		if('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat){
			showControls(PROCESSING_SYSTEM);
			disableFieldOnDemand('PROCESSING_SYSTEM');
			if('LD' == reqType || 'IFA_CTP' == reqType) {
				setStyle('UTC_REQUIRED_BRMS', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('UTC_CONVERTED_AMOUNT', PROPERTY_NAME.VISIBLE, 'true');
			}
		}
        //Traydstream |reyaz|atp-518|07-10-2024| Start
		if(TS_REQ_CAT.includes(reqCat) && TS_REQ_TYPE.includes(reqType)){
		   showControls(SKIP_TS_FLAG);
           if(true ==getValue(SKIP_TS_FLAG)){
              var tsjustication =getValue(TS_JUSTIFICATION);
			  if('DST' == tsjustication){
				showControls(TS_REFERENCE);
			   }
            }
		} 
        //Traydstream |reyaz|atp-518|07-10-2024| End
	} 
	else if (workstepName == WORKSTEP.ASSIGNMENT_QUEUE) {
		console.log('locking all sections..');
		setStyle(FRM_SWIFT,"lock","true");
		setStyle(SEC_REQ_DETAILS,"lock","true");
		setStyle(SUBSEC_CUST_DETAILS,"lock","true");
		console.log('process type value: '+getValue(PROCESS_TYPE));
		showControls(ROUTE_TO_PPM,FRAME_Decision_Hist);
		hideControls(HIDE_AT_LOGISTICS);
	} 
	else if (workstepName == WORKSTEP.PP_MAKER) {
		onPPMakerFormLoad();
		//initiatorhybridcombo();
		renameLimitTab();
	} 
	else if(workstepName == WORKSTEP.RM  || workstepName == WORKSTEP.TSD
			|| workstepName == WORKSTEP.LEGAL_TEAM || workstepName == WORKSTEP.TREASURY || workstepName == WORKSTEP.COMPLIANCE
			|| workstepName == WORKSTEP.CORRESPONDENT_BANK || workstepName == WORKSTEP.TRADE_SALES || workstepName == WORKSTEP.CUSTOMER_REVIEW ) {
		onRMFormLoad();
	}
	else if(workstepName == WORKSTEP.CUSTOMER_REFERRAL_RESPONSE) {  // customer_ref_resposne_ppm_form
		onPPMakerFormLoad();
		//customerRefFieldEnableDisable();
	}
	else if (workstepName == WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
		onPMFormLoad();
	}
	else if(workstepName == WORKSTEP.PROCESSING_MAKER) {
		onPMFormLoad();	
		//initiatorhybridcombo();		
	}
	else if(workstepName == WORKSTEP.PROCESSING_CHECKER) {
		onPCFormLoad();	
		//initiatorhybridcombo();		
	} 
	else if(workstepName == WORKSTEP.DELIVERY) {
		onDeliveryFormLoad();		
	} 
	else if(workstepName == WORKSTEP.PMPS || workstepName == WORKSTEP.PCPS || workstepName == WORKSTEP.SCC) {
		onPMPCProcessingSystemFormLoad();	
		setTabStyle("tab1",2, "visible", "false");
	} 
	else if(workstepName == WORKSTEP.REPAIR_QUEUE){
		setTabStyle("tab1",2, "visible", "false");
		setStyle('SYSTEM_ACTIVITY_NAME', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('REASON_FOR_ACTION', PROPERTY_NAME.VISIBLE, 'false');
	}
	else if(workstepName == WORKSTEP.LEGAL){   //ajeet 27/01/23
	setTabStyle('tab1',2, "visible", "false");
	setTabStyle('tab1',3, "visible", "false");
	setTabStyle('tab1',4, "visible", "false");
	setTabStyle('tab1',5, "visible", "false");
		if(getValue('EDAS_APPROVAL')=='Pending'){
		setStyle('LEGALIZATION_CHARGES',PROPERTY_NAME.DISABLE,'true');
		setStyle('EDAS_REFERENCE',PROPERTY_NAME.DISABLE,'true');
	    }
	}
	else if(workstepName == WORKSTEP.TRAYDSTREAM){   //Traydstream |reyaz|09-10-2024| 
	   hideControls(TS_HIDE);
	   disableFieldOnDemand(TS_DISABLE);
	   showControls(TS_SHOW);
	   if('' == getValue(TS_REFERENCE)){
		 enableFieldOnDemand(TS_ENABLE);
	   }
	}
	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	disablePTFields2();
	if(!(requestCat == 'IFA' || requestCat == 'IFP' ||requestCat == 'IFS')){
		hideControls('Toggle2');  //TSLM
	}
	//fillProtradeSwiftDelivInst();

	
}

function setFetchModule(controlName){
	var requestType = getValue(REQUEST_TYPE);
	if(('IC_MSM' == requestType)||('EC_MSM' == requestType) ||('ILC_MSM' == requestType)
			||('ELC_MSM' == requestType)||('ILCB_MSM' == requestType)||('ELCB_MSM' == requestType)
			||('GRNT_MSM' == requestType)||('TL_MSM' == requestType)||('DBA_MSM' == requestType)
			||('MISC_MSM' == requestType)||('IFS_MSM' == requestType)||('IFP_MSM' == requestType) 
			||('SBLC_MSM' == requestType)){                 //added by mansi
		setStyle(controlName, PROPERTY_NAME.DISABLE, 'false');
	} 
	else {
		setValues({[controlName]: ''}, true);
		setStyle(controlName, PROPERTY_NAME.DISABLE, 'true');
	}
	if('720' == getValue(SWIFT_MESSAGE_TYPE)){
		setValues({[controlName]: 'CID'}, true);
	}
}

function addRowPostHook(tableId){
	if(tableId == 'QVAR_utc_details'){
		saveWorkItem();
	}if(tableId == 'SIGN_REFERRAL_ID'||tableId == 'Doc_Review_RefID'||tableId == 'LIMIT_REFERRAL_ID'){
		sufficentBal(tableId);
	}
}

function modifyRowPostHook(tableId){
		
		if(tableId=='SIGN_REFERRAL_ID'||tableId=='LIMIT_REFERRAL_ID'||tableId=='Doc_Review_RefID'){
			sufficentBal(tableId);
		}
}
	
function enablefetchfields(){
	if('ELC_LCA' == getValue(REQUEST_TYPE) && 'SWIFT' == getValue(PROCESS_TYPE) &&
			('700' == getValue(SWIFT_MESSAGE_TYPE) ||'710' == getValue(SWIFT_MESSAGE_TYPE))){
		var processingStatus = getValue(SWIFT_PROCESSING_STATUS);
		if('P' == processingStatus){
			console.log('processingStatus is P');
			enableFieldOnDemand('TrnsRefNo,TXT_TRANSACTION_UNB_REFERENCE');
			disableFieldOnDemand('CID_Txt');
		}else if('R' == processingStatus){
			console.log('processingStatus is R');
			enableFieldOnDemand('CID_Txt');
			disableFieldOnDemand('TrnsRefNo,TXT_TRANSACTION_UNB_REFERENCE');
		}
	} else if('PD' == getValue(REQUEST_TYPE) || 'PDD' == getValue(REQUEST_TYPE)){  //ATP -191  Date 25 -10-2023 ADDED BY REYAZ
			enableFieldOnDemand('CID_Txt');
			disableFieldOnDemand('RELATIONSHIP_TYPE,PROCESSING_SYSTEM');
			setValues({[RELATIONSHIP_TYPE]: 'C'}, true);
	}
}
function setDefaultFields(){
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	setTabStyle("tab1",8, "visible", "false");
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType|| 'PDD' == reqType)) {  //ADDED BY SHIVANSHU FOR SCF ATP - 254
		setStyle('UTC_REQUIRED_BRMS', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('UTC_CONVERTED_AMOUNT', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('UTC_REQUIRED', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('UTC_JSTIFICATION_REQUIRED', PROPERTY_NAME.VISIBLE, 'true');
		enableFieldOnDemand('UTC_REQUIRED');
		var utcCheck=getValue('UTC_REQUIRED_BRMS');
		
		console.log("UTC_REQUIRED_BRMS"+UTC_REQUIRED_BRMS);
		setValue('UTC_REQUIRED',utcCheck);
		setTabStyle("tab1",8, "visible", "true");
		
	}
}
function setDefaultValue(workstepName){
	var requestCategory= getValue(REQUEST_CATEGORY);
	//var requestType=getValue(REQUEST_TYPE);
	var sourceChannel = getValue(SOURCE_CHANNEL);
	console.log("strTFO_ReqCategory  "+requestCategory);
	if('GRNT' == requestCategory){
		console.log("strTFO_ReqCategory ==  "+requestCategory);
		setGTEEDefaultScrChannel(workstepName);
	}
	else if('IFS' == requestCategory){
		if('' == sourceChannel)
		{ setValues({[SOURCE_CHANNEL]: 'IFSB'}, true); }
	}
	else if('IFP' == requestCategory){
		if('' == sourceChannel)
		{ setValues({[SOURCE_CHANNEL]: 'IFPB'}, true); }
	}
	else if('IFA' == requestCategory){
		if('' == sourceChannel)
		{ setValues({[SOURCE_CHANNEL]: 'IFAB'}, true); }
	}
	else if('SBLC' == requestCategory){
		console.log("strTFO_ReqCategory ==  "+requestCategory);
		setGRNTDefaultScrChannelStandBy(workstepName);
	}
	else if('ELC' == requestCategory){//added by mansi
		console.log("strTFO_ReqCategory ==  "+requestCategory);
		setELCDefaultScrChannelStandBy(workstepName);
	}	
	else if('SCF' == requestCategory){  //ATP -191  Date 25 -10-2023 ADDED BY REYAZ
		if('' == sourceChannel)
		{ setValues({[SOURCE_CHANNEL]: 'SCFB'}, true); }
	}

}


function setReqByDefault(controlName, requestType, requestCat){
	var workstepName = getWorkItemData('activityName');
	var processType = getValue(PROCESS_TYPE);
	if('GRNT'== requestCat){
		setGteeInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);	
		if ('NI' == requestType || 'GA' == requestType) {
			setFieldEnableDisableForIssue();
		} else if ('GAA' == requestType || 'AM' == requestType || 'CC' == requestType || 'CL' == requestType ||
				'ER' == requestType || 'EPC' == requestType ||'GRNT_SD' == requestType) { // US3000
			setFieldEnableDisableOthIssue();
		}else if('GRNT_MSM' == requestType && 'CID' == getValue('SWIFT_FETCH_MODULE')){ // US3397
			enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
		} else {
			setFieldEnableDisableAtIntro();
		}
	}
	else if('IFS' == requestCat){
		setIfsInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		ifsFieldEnableDisable(requestType);
	}
	else if('IFCPC' == requestCat){
		setIFCPCInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		enableFieldOnDemand('CID_Txt,TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
		//IFCPCFieldEnableDisable(requestType);
	}
	else if('IFP' == requestCat || 'IFA' == requestCat){
		setIfpInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		ifpFieldEnableDisable(requestType);
	}
	else if('EC' == requestCat){
		setECInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setECDefaultScrChannel(requestType);
		ecFieldEnableDisable(requestType);
	}
	else if('IC' == requestCat){
		setICInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setICDefaultScrChannel(requestType);
		icFieldEnableDisable(requestType);
	}		
	else if('ELCB' == requestCat){
		setELCBInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setELCBDefaultScrChannel(requestType);
		elcbFieldEnableDisable(requestType);
	}
	else if('ILCB' == requestCat){
		setILCBInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setILCBDefaultScrChannel(requestType);
		ilcbFieldEnableDisable(requestType);
	}else if('ELC' == requestCat){
		setELCInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setELCDefaultScrChannel(requestType);
		elcFieldEnableDisable(requestType);
	}else if('ILC' == requestCat){
		setILCInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setILCDefaultScrChannel(requestType);
		ilcFieldEnableDisable(requestType);
		
	}
	else if('TL' == requestCat){
		setTlDefaultScrChannel(requestType);
		tlFieldEnableDisable(requestType);
	}
	else if('DBA' == requestCat){
		setDbaInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		setDbaDefaultScrChannel(requestType);
		dbaFieldEnableDisable(requestType);
	}else if('MISC' == requestCat){ // US3405
		miscFieldEnableDisable(requestType);
		setMiscDefaultScrChannel(requestType);
	}else if('SG' == requestCat){     // Shipping_gtee_6
		setSGInitiatorDefaultValue(requestCat,requestType);
		setSGDefaultScrChannel(requestType); // Shipping_gtee_7
		SGFieldEnableDisable(requestType);	 // Shipping_gtee_14
	}else if('SBLC'== requestCat){          //added by mansi
		setGrntInitiatorDeafultValueStandBy(REQUESTED_BY, requestCat, requestType);	
		if ('SBLC_NI' == requestType) {
			setFieldEnableDisableForIssue();
		} else if ('SBLC_AM' == requestType || 'SBLC_CS' == requestType || 'SBLC_CR' == requestType
				|| 'SBLC_ER' == requestType || 'SBLC_SD' == requestType) { // US3000
			setFieldEnableDisableOthIssue();
		}else if('SBLC_MSM' == requestType && 'CID' == getValue('SWIFT_FETCH_MODULE')){ // US3397
			enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
		} else {
			setFieldEnableDisableAtIntro();
		}
	}else if('SCF'== requestCat){     //ATP -191  Date 25 -10-2023 ADDED BY REYAZ
		setscfInitiatorDeafultValue(REQUESTED_BY, requestCat, requestType);
		scfFieldEnableDisable(requestType);
	}if('ELC'== requestCat) {//aded by mansi
		setELCInitiatorDeafultValueStandBy(REQUESTED_BY, requestCat, requestType);	
		if ('ELC_SLCA' == requestType) {
			setFieldEnableDisableForIssue();
		} else if ('ELC_SLCAA' == requestType || 'ELC_SCL' == requestType || 'ELC_SER' == requestType ) { // US3000
			setFieldEnableDisableOthIssue();
		}else if('CID' == getValue('SWIFT_FETCH_MODULE')){ // US3397
			enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
		}else if('ELC_LCA' == requestType){ // santhosh
			enableFieldOnDemand('TRANSACTION_AMOUNT');
		} 
		else {
			setFieldEnableDisableAtIntro();
		}
	}
	
}

function handleSWIFTUI(){
	console.log('handleSwiftUI: '+getValue('PROCESS_TYPE'));
	if ('SWIFT' == getValue(PROCESS_TYPE))
	{	
		console.log('process type ...swift');
		setStyle(FRM_SWIFT,"sectionstate","expanded");
		setStyle(FRM_SWIFT,"lock","true");
		disableFieldOnDemand(BUTTON_CUSTOMER_ACK+','+BUTTON_DISCARD);
		if(getValue(REQUEST_TYPE) == 'ELCB_AOR')
		{	
			setStyle(CHKBX_SEND_MAIL,PROPERTY_NAME.DISABLE,'false');
			console.log('manually tick TFO_MANUALLY_TICKED_MAIL');
			if(null==getValue('MANUALLY_TICKED_MAIL') || '' == getValue('MANUALLY_TICKED_MAIL')){ 
				console.log('isnide manually tick');
				setValues({[CHKBX_SEND_MAIL]: 'true'}, true);
			}
		}
		setStyle(FLG_ACK_GEN,PROPERTY_NAME.DISABLE,'true');
	}else if('BAU' == getValue(PROCESS_TYPE) || '' == getValue(PROCESS_TYPE)){	
		setStyle(FRM_SWIFT,"sectionstate","collapsed");
		setStyle(FRM_SWIFT,"lock","true");
		enableFieldOnDemand(BUTTON_CUSTOMER_ACK+','+BUTTON_DISCARD+','+CHKBX_SEND_MAIL+','+FLG_ACK_GEN);
		setValues({[CHKBX_SEND_MAIL]: 'false'}, true);
	}
}

function processTypeChangeEvent(){
	console.log('inside processTypeChangeEvent >>>');
	console.log('process type value: '+getValue(PROCESS_TYPE));
	if('SWIFT' == getValue(PROCESS_TYPE)){
		setStyle(FRM_SWIFT, PROPERTY_NAME.VISIBLE, 'true');
		setStyle(FRM_SWIFT,PROPERTY_NAME.DISABLE,'false');
		setStyle(FRM_SWIFT,"sectionstate","expanded");
		if('Y' == getValue('SWIFT_UTILITY_FLAG')) // US3394
		{	
			console.log('SWIFT_UTILITY_FLAG -yes');
			disableFieldOnDemand('SWIFT_MESSAGE_TYPE,SWIFT_PROCESSING_STATUS,SWIFT_FIELD_21,SWIFT_SENDER_BIC,SWIFT_DEC_ON_SWIFT_MESSAGE');
			enableFieldOnDemand('SWIFT_DEC_ON_SWIFT_MESSAGE,SWIFT_REASON_FOR_FILING');
		}
		else{
			console.log('SWIFT_UTILITY_FLAG -no');
			enableFieldOnDemand('SWIFT_MESSAGE_TYPE,SWIFT_PROCESSING_STATUS,SWIFT_FIELD_21,SWIFT_SENDER_BIC,SWIFT_DEC_ON_SWIFT_MESSAGE');
			setStyle(SWIFT_REASON_FOR_FILING,PROPERTY_NAME.DISABLE,'false');
		}			
	}
	else if('BAU' == getValue(PROCESS_TYPE) || '' == getValue(PROCESS_TYPE)){
		setStyle(FRM_SWIFT,"sectionstate","collapsed");
		setStyle(FRM_SWIFT, PROPERTY_NAME.VISIBLE, 'false');
		setStyle(FRM_SWIFT,PROPERTY_NAME.DISABLE,'true');
		disableFieldOnDemand('SWIFT_MESSAGE_TYPE,SWIFT_PROCESSING_STATUS,SWIFT_FIELD_21,SWIFT_SENDER_BIC,SWIFT_DEC_ON_SWIFT_MESSAGE');
	}
}

function setGteeInitiatorDeafultValue(controlName, requestCat, requestType){
	console.log('inside setGteeInitiatorDeafultValue >>> ');
	var workstepName = getWorkItemData('activityName');
	var processType = getValue('PROCESS_TYPE');
	console.log('process type: '+processType);
	setStyle(REQUESTED_BY, PROPERTY_NAME.DISABLE, 'false');
	if('SWIFT' == processType){
		setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
		console.log('source channel set as ISA..');
		if('GRNT_MSM' == requestType || 'CC' == requestType || 'CL' == requestType || 'ER' == requestType ||
				'EPC' == requestType || 'GA' == requestType || 'GAA' == requestType)
		{	console.log('setting REQUESTED_BY ....');
		setValues({[REQUESTED_BY]: 'GRNT_CB'}, true);
		console.log('value set as GRNT_CB');
		}else if('NI' == requestType || 'AM' == requestType)
		{
			setValues({[REQUESTED_BY]: 'T'}, true);
		}else if('' == requestType)
		{	console.log('setting REQUESTED_BY null ...');
		setValues({[REQUESTED_BY]: ''}, true);
		}
	} else if ('NI' == requestType ||'AM' == requestType ||'CC' == requestType) {
		setValues({[REQUESTED_BY]: 'A'}, true);
		setDefaultValue(workstepName);
	} else if ('CL' == requestType || 'ER' == requestType || 'EPC' == requestType) {
		setValues({[REQUESTED_BY]: 'B'}, true);
		setDefaultValue(workstepName);
	} else if ('GA' == requestType || 'GAA' == requestType) {
		setValues({[REQUESTED_BY]: 'I'}, true);
		setDefaultValue(workstepName);
	}			
	else if ('' == requestType || '0' == requestType) {
		console.log('setting REQUESTED_BY null ...');
		setValues({[REQUESTED_BY]: ''}, true);
		setDefaultValue(workstepName);
	}
	else if('GRNT_SD' == requestType){
		setValues({[REQUESTED_BY]: 'GRNT_AT'}, true);
		setValues({[SOURCE_CHANNEL]: 'B'}, true);
	}
}

function setGTEEDefaultScrChannel(workstepName){
	console.log("inside setGTEEDefault  ");
	var requestType = getValue(REQUEST_TYPE);
	var sourceChannel = getValue(SOURCE_CHANNEL);
	if (workstepName == WORKSTEP.INITIATOR) {			
		console.log (" Request Type > " +requestType);			
		if ('GA' == requestType || 'GAA' == requestType ) {
			setValues({[SOURCE_CHANNEL]: 'S'}, true);	
		}						
		else if('' == requestType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}
		else{
			setValues({[SOURCE_CHANNEL]: 'B'}, true);
		}
	} else if (workstepName == WORKSTEP.PP_MAKER) {
		var isAccValid = getValue(IS_ACC_VALID);

		console.log('VAlica account LOV Values >>> ' + isAccValid );

		if('NI' == requestType && (null == isAccValid || '' == isAccValid) || '0' == isAccValid){
			if ('S' == sourceChannel) {
				setValues({[IS_ACC_VALID]: '2'}, true);	
			} else {
				setValues({[IS_ACC_VALID]: '1'}, true);	// S- Swift as channel
			}
		}			
	}
}

function setIfsInitiatorDeafultValue(controlName, requestCat, reqType){
	var workstepName = getWorkItemData('activityName');
	console.log("setIfsInitiatorDeafultValue controlName "+controlName +"\n reqType "+ reqType);
	enableFieldOnDemand(controlName);
	if('SWIFT' == getValue(PROCESS_TYPE)){
		setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
		if('IFS_MSM' == reqType){
			setValues({[REQUESTED_BY]: 'IFS_CB'}, true);
		}else if('' == reqType || '0' == reqType){
			setValues({[REQUESTED_BY]: ''}, true);
		}
	}
	else if ('LD' == reqType) {
		setValues({[controlName]: 'CT'}, true); // CT-Customer
		setDefaultValue(workstepName);
		disableFieldOnDemand(controlName);
	} else if ('AM' == reqType || 'STL' == reqType) {
		setValues({[controlName]: 'CT'}, true); // CT-Customer
		setDefaultValue(workstepName);				
	}		
	else if ('' == reqType || '0' == reqType) {
		setValues({[controlName]: ''}, true);				
		setDefaultValue(workstepName);
	}  
	else if('IFS_SD' == reqType){ 
		setValues({[controlName]: 'IFS_AT'}, true);	
		setValues({[SOURCE_CHANNEL]: 'IFSB'}, true);				
	}

}

function ifsFieldEnableDisable(reqType){
	if ('LD' == reqType) {
		setFieldEnableDisableForIssue();
	}else if ('AM' == reqType || 'STL' == reqType ||'IFS_SD' == reqType) { 
		setFieldEnableDisableOthIssue();
	}else if('IFS_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	}else {
		setFieldEnableDisableAtIntro();
	}
}

function setIFCPCInitiatorDeafultValue(controlName, requestCat, reqType){
	var workstepName = getWorkItemData('activityName');
	var processType = getValue('PROCESS_TYPE');
	console.log('process type: '+processType);
	console.log("setIfsInitiatorDeafultValue controlName "+controlName +"\n reqType "+ reqType);
	//enableFieldOnDemand(controlName);
	 if (('SIF' == reqType || 'APPIF' == reqType) && processType == 'BAU') {
		setValues({[controlName]: 'CT'}, true); // CT-Customer
		setValues({[SOURCE_CHANNEL]: 'IFCPCB'}, true);
		setValues({[PROCESSING_SYSTEM]: 'T'}, true);
		setStyle(PROCESSING_SYSTEM, PROPERTY_NAME.DISABLE, 'true');
		setStyle(SOURCE_CHANNEL, PROPERTY_NAME.DISABLE, 'true');
		//setDefaultValue(workstepName);
		disableFieldOnDemand(controlName);
	} 
}

function IFCPCFieldEnableDisable(reqType){
	if ('SIF' == reqType || 'APPIF' == reqType){
		//setFieldEnableDisableForIssue();
		//enableFieldOnDemand('CID_Txt');
		setStyle('CID_Txt', PROPERTY_NAME.DISABLE, 'false');
	}
}

function setIfpInitiatorDeafultValue(controlName, requestCat, reqType){
	console.log("setIfpInitiatorDeafultValue controlName "+controlName +"\n reqType:"+ reqType);
	var workstepName = getWorkItemData('activityName');
	enableFieldOnDemand(controlName);
	if('SWIFT' == getValue(PROCESS_TYPE)){
		setValues({[SOURCE_CHANNEL]: 'ISA'}, true);				
		if('IFP_MSM' == reqType)
		{	setValues({[REQUESTED_BY]: 'IFP_CB'}, true);
		}else if('' == reqType || '0' == reqType)
		{	setValues({[REQUESTED_BY]: ''}, true);
		}
	}else if ('LD' == reqType) {
		setValues({[controlName]: 'CT'}, true);
		setDefaultValue(workstepName);
		disableFieldOnDemand(controlName);
	}else if ('AM' == reqType || 'STL' == reqType || 'IFA_CTP' == reqType ) {	
		setValues({[controlName]: 'CT'}, true);
		setDefaultValue(workstepName);				
	}else if ('' == reqType || '0' == reqType) {
		setValues({[controlName]: ''}, true);
		setDefaultValue(workstepName);
	}else if('IFP_SD' == reqType){
		console.log("inLoop \n reqType "+ reqType);
		setValues({[controlName]: 'IFP_AT'}, true);
		setValues({[SOURCE_CHANNEL]: 'IFPB'}, true);
	}else if('IFA_SD' == reqType){ //code by kritika start
		console.log("inLoop \n reqType "+ reqType);
		setValues({[controlName]: 'IFA_AT'}, true);
		setValues({[SOURCE_CHANNEL]: 'IFAB'}, true);
	} //code by kritika end 
}

function ifpFieldEnableDisable(reqType){
	if ('LD' == reqType) {
		setFieldEnableDisableForIssue();
	} else if ('AM' == reqType || 'IFA_CTP' == reqType || 'STL' == reqType ||'IFP_SD' == reqType) { 
		setFieldEnableDisableOthIssue();
	}else if('IFP_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setECInitiatorDeafultValue(controlName, requestCat, reqType){
	enableFieldOnDemand(controlName);// Request Submitted by
	if ('EC_BL' == reqType ||'EC_AM' == reqType ||'EC_LDDI' == reqType ||'EC_DISC' == reqType) {
		setValues({[controlName]: 'EC_CT'}, true);
	}else if ('EC_AC' == reqType || 'EC_CA' == reqType || 'EC_BS' == reqType) {
		setValues({[controlName]: 'EC_CB'}, true);// EC_CB - Collecting Bank
	}else if ('EC_BCDR' == reqType || 'EC_CAP' == reqType) {
		setValues({[controlName]: 'EC_RB'}, true); // EC_RB-Remitting Bank
	}else if ('' == reqType || '0' == reqType) {
		setValues({[controlName]: ''}, true); // I-Issueing or Advising Bank
	}else if('EC_SD' == reqType){ 
		console.log('inLoop \n reqType: '+ reqType);
		setValues({[controlName]: 'EC_AT'}, true); 
		setValues({[SOURCE_CHANNEL]: 'EC_B'}, true); 
	} 
}

function setEmptyCombo(controlName, controlValue){
	console.log("setEmptyCombo  "+controlName + "   controlValue   " + controlValue);
	var fieldValue = getValue(controlName);
	console.log( "mode of payment " +fieldValue );

	if(undefined==fieldValue||""==fieldValue || '0' == fieldValue|| (""!=fieldValue && '0'== fieldValue.trim() && fieldValue.trim().isEmpty()) ){

		setValues({[controlName]: controlValue}, true); }
}

function setECDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {	
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true); 
			if('EC_MSM' == reqType)
			{	setValues({[REQUESTED_BY]: 'EC_COB'}, true); 
			}else if('EC_AM' == reqType)
			{	setValues({[REQUESTED_BY]: 'EC_CB'}, true); 
			}else if('0' == reqType || '0' == reqType)
			{	setValues({[REQUESTED_BY]: ''}, true);
			}

		}
		else if ('EC_BL' == reqType || 'EC_AM' == reqType || 'EC_BS' == reqType || 'EC_BCDR' == reqType || 'EC_DISC' == reqType
				|| 'EC_CAP' == reqType || 'EC_LDDI' == reqType){
			setValues({[SOURCE_CHANNEL]: 'EC_B'}, true);// EC_B - Branch as
			// channel
		} else if('EC_AC' == reqType || 'EC_CA' == reqType){
			setValues({[SOURCE_CHANNEL]: 'EC_S'}, true); // EC_S - Swift as
			// channel
		}						
		else if('' == reqType || '0' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}
	}
}

function ecFieldEnableDisable(reqType){
	if ('EC_BL' == reqType || 'EC_LDDI' == reqType) {
		setFieldEnableDisableForIssue();
	} else if ('EC_AM' == reqType || 'EC_AC' == reqType	|| 'EC_CA' == reqType || 'EC_BS' == reqType || 'EC_BCDR' == reqType
			|| 'EC_DISC' == reqType || 'EC_CAP' == reqType ||'EC_SD' == reqType) {
		setFieldEnableDisableOthIssue();
	}else if('EC_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setICInitiatorDeafultValue(controlName, requestCat, reqType){
	enableFieldOnDemand(controlName);
	if ('IC_BL' == reqType ||'IC_AM' == reqType) {
		setValues({[controlName]: 'IC_RB'}, true); // IC-RB - Remitting Bank
	} else if ('IC_AC' == reqType || 'IC_BS' == reqType || 'IC_BCDR' == reqType || 'IC_LD' == reqType 
			|| 'IC_AM' == reqType || 'IC_STL' == reqType) {
		setValues({[controlName]: 'IC_CT'}, true); // IC_CT - CUSTOMER
	} else if ('IC_PRC' == reqType) {
		setValues({[controlName]: 'IC_CB'}, true); // IC_CB-COLLETING BANK
	}			
	else if ('' == reqType || '0' == reqType) {
		setValues({[controlName]: ''}, true); // I-Issueing or Advising Bank
	}else if('IC_SD' == reqType){ 
		console.log('inLoop \n reqType '+ reqType);
		setValues({[controlName]: 'IC_AT'}, true); 
		setValues({[SOURCE_CHANNEL]: 'IC_B'}, true);
	}
}

function setICDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('IC_MSM' == reqType){
				setValues({[REQUESTED_BY]: 'IC_COB'}, true);
			}else if('IC_AM' == reqType){
				setValues({[REQUESTED_BY]: 'IC_COB'}, true);
			} else if('' == reqType || '0' == reqType){
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
		if ('IC_BL' == reqType) {
			setValues({[SOURCE_CHANNEL]: 'IC_C'}, true);
		}else if('IC_AM' == reqType){
			setValues({[SOURCE_CHANNEL]: 'IC_S'}, true);
		}else if('IC_AC' == reqType ||'IC_BS' == reqType ||'IC_BCDR' == reqType ||'IC_LD' == reqType ||'IC_AM' == reqType
				||'IC_STL' == reqType ||'IC_PRC' == reqType){
			// alert("ll");
			setValues({[SOURCE_CHANNEL]: 'IC_B'}, true);
		}
		else if('' == reqType || '0' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}
	}

}

function icFieldEnableDisable(reqType){
	if ('IC_BL' == reqType) {
		setFieldEnableDisableForIssue();
	} else if ( 'IC_AM' == reqType || 'IC_AC' == reqType || 'IC_BS' == reqType || 'IC_BCDR' == reqType || 'IC_LD' == reqType
			|| 'IC_LAM' == reqType || 'IC_STL' == reqType || 'IC_PRC' == reqType || 'IC_SD' == reqType) {
		setFieldEnableDisableOthIssue();
	}else if('IC_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setELCBInitiatorDeafultValue(controlName, requestCat, reqType){

	enableFieldOnDemand(controlName);
	if ('ELCB_BL' == reqType ||'ELCB_AM' == reqType ||'ELCB_DISC' == reqType) {
		setValues({[controlName]: 'ELCB_CT'}, true);
	} else if ('ELCB_AC' == reqType || 'ELCB_BS' == reqType){
		setValues({[controlName]: 'ELCB_IB'}, true);
	}else if ('ELCB_BCDR' == reqType || 'ELCB_CLBP' == reqType) {
		setValues({[controlName]: 'ELCB_ADCB'}, true);
	}			
	else if ('' == reqType|| '0' == reqType) {
		setValues({[controlName]: ''}, true);
	}else if('ELCB_SD' == reqType){
		console.log('inLoop \n reqType: '+ reqType);
		setValues({[controlName]: 'ELCB_ADCB'}, true);
		setValues({[SOURCE_CHANNEL]: 'ELCB_B'}, true);
	} 

}

function setELCBDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('ELCB_MSM' == reqType){
				setValues({[REQUESTED_BY]: 'ELCB_CB'}, true);
			}else if('ELCB_AOR' == reqType){
				setValues({[REQUESTED_BY]: 'ELCB_IB'}, true);
			}else if('ELCB_AM' == reqType){
				setValues({[REQUESTED_BY]: 'ELCB_IB'}, true);
			}else if('' == reqType || '0' == reqType){
				setValues({[REQUESTED_BY]: ''}, true);
			}

		}
		else if ('ELCB_BL' == reqType || 'ELCB_AM' == reqType || 'ELCB_BCDR' == reqType || 'ELCB_DISC' == reqType
				|| 'ELCB_CLBP' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ELCB_B'}, true);

		}else if('ELCB_AC' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ELCB_S'}, true);
		}else if('ELCB_BS' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ELCB_NS'}, true);
		}
		else if('' == reqType || '0' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true); 
		}
	}

}

function elcbFieldEnableDisable(reqType){
	if ('ELCB_BL' == reqType) {
		setFieldEnableDisableForIssue();
		disableFieldOnDemand('TRANSACTION_CURRENCY');  
	} else if ( 'ELCB_AM' == reqType || 'ELCB_AC' == reqType || 'ELCB_BS' == reqType || 'ELCB_BCDR' == reqType
			|| 'ELCB_DISC' == reqType || 'ELCB_CLBP' == reqType ||'ELCB_SD' == reqType) {
		setFieldEnableDisableOthIssue();
	}else if('ELCB_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setILCBInitiatorDeafultValue(controlName, requestCat, reqType){
	enableFieldOnDemand(controlName);
	if ('ILCB_BL' == reqType ||'ILCB_AM' == reqType) {
		setValues({[controlName]: 'ILCB_PB'}, true);// ILCB-PB - Presenting Bank
	} else if ('ILCB_AC' == reqType || 'ILCB_BS' == reqType || 'ILCB_BCDR' == reqType || 'ILCB_LD' == reqType 
			|| 'ILCB_AM' == reqType || 'ILCB_STL' == reqType) {
		setValues({[controlName]: 'ILCB_CT'}, true);// ILCB_CT - Customer
	}			
	else if ('' == reqType || '0' == reqType) {
		setValues({[controlName]: ''}, true); // I-Issueing or Advising Bank
	}else if('ILCB_SD' == reqType){
		console.log('inLoop \n reqType: '+ reqType);
		setValues({[controlName]: 'ILCB_AT'}, true);
		setValues({[SOURCE_CHANNEL]: 'ILCB_B'}, true); 
	} 

}

function setILCBDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('ILCB_MSM' == reqType){
				setValues({[REQUESTED_BY]: 'ILCB_CB'}, true);
				setDefaultValue('REQUESTED_BY', 'ILCB_CB');
			}else if('ILCB_AOD' == reqType){
				setValues({[REQUESTED_BY]: 'ILCB_PB'}, true);
			}else if('ILCB_AOP' == reqType){
				setValues({[REQUESTED_BY]: 'ILCB_PB'}, true);
				setDefaultValue('REQUESTED_BY', 'ILCB_PB');
			} else if('' == reqType || '0' == reqType){
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
		else if ('ILCB_BL' == reqType) {
			setValues({[SOURCE_CHANNEL]: 'ILCB_C'}, true);// ILCB-C- COURIER
		}else if('ILCB_AM' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ILCB_S'}, true);
		}else if('ILCB_AC' == reqType ||'ILCB_BS' == reqType ||'ILCB_BCDR' == reqType ||'ILCB_LD' == reqType
				||'ILCB_AM' == reqType ||'ILCB_STL' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ILCB_B'}, true);// ILCB_B- Branch as
			// channel
		}
		else if('' == reqType || '0' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true); 
		}
	}

}

function ilcbFieldEnableDisable(reqType){
	if ('ILCB_BL' == reqType) {
		setFieldEnableDisableForIssue();
		disableFieldOnDemand('TRANSACTION_CURRENCY'); 
	} else if ('ILCB_AM' == reqType || 'ILCB_AC' == reqType || 'ILCB_BS' == reqType || 'ILCB_BCDR' == reqType
			|| 'ILCB_LD' == reqType || 'ILCB_LAM' == reqType || 'ILCB_STL' == reqType || 'ILCB_SD' == reqType){ 
		setFieldEnableDisableOthIssue();
	}else if('ILCB_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setELCInitiatorDeafultValue(controlName, requestCat, reqType){

	enableFieldOnDemand(controlName);
	if ('ELC_LCA' == reqType || 'ELC_LCAA' == reqType || 'ELC_LCCL' == reqType) {
		setValues({[controlName]: 'ELC_IB'}, true); // ELC_IB - Issuing Bank
	}else if ('ELC_LCC' == reqType || 'ELC_AOP' == reqType || 'ELC_LCT' == reqType){
		setValues({[controlName]: 'ELC_CT'}, true);// ELC_IB - Issuing Bank
	}else if('ELC_RSD' == reqType){
		setValues({[controlName]: 'ELC_ACB'}, true); // ELC_IB -
		// Advising/Confirming
		// Bank
	}
	else if ('' == reqType || '0' == reqType) {
		setValues({[controlName]: ''}, true);
	}else if('ELC_SD' == reqType){ 
		console.log('inLoop \n reqType '+ reqType);
		setValues({[controlName]: 'ELC_AT'}, true); 
		setValues({[SOURCE_CHANNEL]: 'ELC_B'}, true);
	} 

}

function setELCDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('ELC_MSM' == reqType)
			{
				setValues({[REQUESTED_BY]: 'ELC_CB'}, true);
			}else if('ELC_PA' == reqType)
			{
				setValues({[REQUESTED_BY]: 'ELC_IB'}, true);
			}else if('ELC_LCC' == reqType)
			{
				setValues({[REQUESTED_BY]: 'ELC_IB'}, true);
			}else if('' == reqType || '0' == reqType)
			{
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
		else if ('ELC_LCA' == reqType || 'ELC_LCAA' == reqType || 'ELC_LCCL' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ELC_S'}, true);// ELC_S -Swift
		}else if ('ELC_LCC' == reqType || 'ELC_AOP' == reqType || 'ELC_LCT' == reqType || 'ELC_RSD' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ELC_B'}, true);// ELC_S -Branch
		}
		else if('' == reqType || '' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true);	
		}
	}

}

function elcFieldEnableDisable(reqType){
	if ('ELC_LCA' == reqType) {
		setFieldEnableDisableForIssue();
	} else if ('ELC_LCAA' == reqType || 'ELC_LCCL' == reqType || 'ELC_LCC' == reqType || 'ELC_AOP' == reqType
			|| 'ELC_LCT' == reqType || 'ELC_RSD' == reqType ||'ELC_SD' == reqType) {
		setFieldEnableDisableOthIssue();
	}else if('ELC_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	}else if('ELC_PA' == reqType){     // US3426
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setILCInitiatorDeafultValue(controlName, requestCat, reqType){

	enableFieldOnDemand(controlName);
	if ('ILC_NI' == reqType ||'ILC_AM' == reqType ||'ILC_CL' == reqType) {
		setValues({[controlName]: 'ILC_CT'}, true);
	}
	else if ('ILC_UM' == reqType) {
		setValues({[controlName]: 'ILC_CB'}, true);
		setValues({[RELATIONSHIP_TYPE]: 'C'}, true);
		
		
	}
	else if ('0' == reqType) {
		setValues({[controlName]: ''}, true);
	}else if('ILC_SD' == reqType){ 
		console.log('inLoop \n reqType: '+ reqType);
		setValues({[controlName]: 'ILC_AT'}, true);	
		setValues({[SOURCE_CHANNEL]: 'ILC_B'}, true);	
	} 

}

function setILCDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('ILC_MSM' == reqType)
			{
				setValues({[REQUESTED_BY]: 'ILC_CB'}, true);
			}else if('0' == reqType)
			{
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
		else if ('ILC_NI' == reqType || 'ILC_AM' == reqType || 'ILC_CL' == reqType || 'ILC_UM' == reqType){
			setValues({[SOURCE_CHANNEL]: 'ILC_B'}, true);
		}
		else if('' == reqType || '0' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}
	}

}

function ilcFieldEnableDisable(reqType){
	if ('ILC_NI' == reqType || 'ILC_UM' == reqType) {
		setFieldEnableDisableForIssue();
	} else if ('ILC_AM' == reqType || 'ILC_CL' == reqType || 'ILC_SD' == reqType) { 
		setFieldEnableDisableOthIssue();
	}else if('ILC_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setTlDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('TL_MSM' == reqType)
			{	
				setValues({[REQUESTED_BY]: 'TL_CB'}, true);
			}else if('' == reqType || '0' == reqType)
			{
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
		else if ('TL_LD' == reqType || 'TL_AM' == reqType || 'TL_STL' == reqType){
			setValues({[SOURCE_CHANNEL]: 'TL_B'}, true);
		}
		else if('' == reqType || '' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}else if('TL_SD' == reqType){ 
			console.log('inLoop \n reqType: '+ reqType);
			setValues({[REQUESTED_BY]: 'TL_AT'}, true);
			setValues({[SOURCE_CHANNEL]: 'TL_B'}, true);
		}
	}
}

function tlFieldEnableDisable(reqType){ 
	if ('TL_LD' == reqType){
		setStyle(COMBOX_IFS_LOAN_GRP, PROPERTY_NAME.VISIBLE, 'true');//US145
		setStyle(CID_FIRST, PROPERTY_NAME.DISABLE, 'true');//US145
		setStyle(ADCB_BILL_REF_NO, PROPERTY_NAME.VISIBLE, 'true');//US145
		setStyle(ADCB_BILL_REF_NO, PROPERTY_NAME.DISABLE, 'true');//US145
		setFieldEnableDisableForIssue();
		//if(){
		setStyle(TRANSACTION_CURRENCY,PROPERTY_NAME.DISABLE,'true');//US146
		//}
	} else if ('TL_AM' == reqType || 'TL_STL' == reqType || 'TL_SD' == reqType) {
		setStyle(COMBOX_IFS_LOAN_GRP, PROPERTY_NAME.VISIBLE, 'false');//US145
		setStyle(CID_FIRST, PROPERTY_NAME.VISIBLE, 'false');//US145
		setStyle(ADCB_BILL_REF_NO, PROPERTY_NAME.VISIBLE, 'false');//US145
		setFieldEnableDisableOthIssue();
	}else if('TL_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		setStyle(COMBOX_IFS_LOAN_GRP, PROPERTY_NAME.VISIBLE, 'false');//US145
		setStyle(CID_FIRST, PROPERTY_NAME.VISIBLE, 'false');//US145
		setStyle(ADCB_BILL_REF_NO, PROPERTY_NAME.VISIBLE, 'false');//US145
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function setDbaInitiatorDeafultValue(controlName, requestCat, reqType){

	enableFieldOnDemand(controlName);
	if ('DBA_BL' == reqType) {
		setValues({[controlName]: 'DBA_CT'}, true);
		disableFieldOnDemand(controlName);
	}
	else if ('DBA_AM' == reqType) {
		setValues({[controlName]: 'DBA_CB'}, true);
		disableFieldOnDemand(controlName);
	}
	else if ('DBA_STL' == reqType) {
		setValues({[controlName]: 'DBA_AT'}, true);
		disableFieldOnDemand(controlName);
	}
	else if ('' == reqType || '0' == reqType){
		setValues({[controlName]: ''}, true);
	}else if('DBA_SD' == reqType){ 
		console.log('inLoop \n reqType: '+ reqType);
		setValues({[controlName]: 'DBA_AT'}, true);
		setValues({[SOURCE_CHANNEL]: 'DBA_B'}, true);
	} 
}

function setDbaDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('DBA_MSM' == reqType)
			{	
				setValues({[REQUESTED_BY]: 'DBA_CB'}, true);
			}else if('' == reqType || '0' == reqType)
			{	
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
		else if ('DBA_BL' == reqType || 'DBA_AM' == reqType || 'DBA_STL' == reqType){
			setValues({[SOURCE_CHANNEL]: 'DBA_B'}, true);
		}
		else if('' == reqType || '0' == reqType){
			setValues({[SOURCE_CHANNEL]: ''}, true);			
		}
	}

}

function dbaFieldEnableDisable(reqType){
	if ('DBA_BL' == reqType) {
		setFieldEnableDisableForIssue();
	} else if ('DBA_AM' == reqType || 'DBA_STL' == reqType ||'DBA_SD' == reqType) { 
		setFieldEnableDisableOthIssue();
	}else if('DBA_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	} else {
		setFieldEnableDisableAtIntro();
	}
}

function miscFieldEnableDisable(reqType){
	if('MISC_MSM' == reqType && 'CID' == getValue(SWIFT_FETCH_MODULE)){ 
		enableFieldOnDemand('TRANSACTION_CURRENCY,TRANSACTION_AMOUNT,RELATIONSHIP_TYPE');
	}else{
		setFieldEnableDisableAtIntro();
	}
}

function setMiscDefaultScrChannel(reqType){
	var workstepName = getWorkItemData('activityName');
	if (workstepName == WORKSTEP.INITIATOR) {		
		if('SWIFT' == getValue(PROCESS_TYPE)){
			setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
			if('MISC_MSM' == reqType)
			{	
				setValues({[REQUESTED_BY]: 'MISC_CB'}, true);
			}else if('' == reqType || '0' == reqType)
			{
				setValues({[REQUESTED_BY]: ''}, true);
			}
		}
	}
}

function setFieldEnableDisableForIssue(){
	var enable = [CID_FIRST, BUTTON_FETCH,TRANSACTION_CURRENCY, TRANSACTION_AMOUNT, RELATIONSHIP_TYPE];
	var requestType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	if(reqCat =='GRNT' && requestType == 'GA' && 'Y' == getValue(SWIFT_UTILITY_FLAG))
	{
		enable = [CID_FIRST, BUTTON_FETCH,RELATIONSHIP_TYPE];
	}
	for(i=0; i<enable.length; i++){
		console.log('enabling control: '+enable[i]);
		setStyle(enable[i], PROPERTY_NAME.DISABLE, 'false');
	}
	var disable = ["TrnsRefNo", "CUSTOMER_ID", "CUSTOMER_NAME"];
	for(i=0; i<disable.length; i++){
		console.log('disabling control: '+disable[i]);
		setStyle(disable[i], PROPERTY_NAME.DISABLE, 'true');
	}
	if(!'Y' == getValue(SWIFT_UTILITY_FLAG))
	{
		setValues({[TrnsRefNo]: '',
			[TRANSACTION_REFERENCE]: ''}, true);
		if(sfrmLoad) setFieldNullValue();
	}
}

function setFieldNullValue(){
	console.log('inside setFieldNullValue ..');
	var workstepName = getWorkItemData('activityName');
	for(var i=0; i<EMPTY_FIELD.length; i++){
		console.log('EMPTY_FIELD: '+EMPTY_FIELD[i]);
		setValues({[EMPTY_FIELD[i]]: ''}, true);
	}

	for(var i=0; i<EMPTY_AMOUNT.length; i++){
		console.log('EMPTY_AMOUNT: '+EMPTY_AMOUNT[i]);
		setValues({[EMPTY_AMOUNT[i]]: '0'}, true);
	}
	setValues({ 
		[TRANSACTION_CURRENCY]: '',
		[RELATIONSHIP_TYPE]: ''}, true);
}

function setFieldEnableDisableOthIssue(){
	var disable = [CID_FIRST, CUSTOMER_ID, CUSTOMER_NAME,TRANSACTION_CURRENCY, TRANSACTION_AMOUNT, RELATIONSHIP_TYPE];
	for(var i=0; i<disable.length; i++){
		setStyle(disable[i], PROPERTY_NAME.DISABLE, 'true');
	}
	var enable = [TrnsRefNo];
	for(var i=0; i<enable.length; i++){
		setStyle(enable[i], PROPERTY_NAME.DISABLE, 'false');
	}
	setValues({[CID_FIRST]: ''}, true);
	if(sfrmLoad) setFieldNullValue();
}

function enableFieldOnDemand(controlNames){
	var arr = controlNames.split(",");
	for(var i=0; i<arr.length; i++){
		console.log('enabling control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.DISABLE, 'false');
	}
}

function disableFieldOnDemand(controlNames){
	var arr = controlNames.split(",");
	for(var i=0; i<arr.length; i++){
		console.log('disabling control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.DISABLE, 'true');
	}
}

function showControls(controlNames){
	var arr = controlNames.split(",");
	for(var i=0; i<arr.length; i++){
		console.log('showing control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.VISIBLE, 'true');
	}
}

function hideControls(controlNames){
	var arr = controlNames.split(",");
	for(var i=0; i<arr.length; i++){
		console.log('hiding control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.VISIBLE, 'false');
	}
}

function disableAckCheckBox(){
	if('Generated' == getValue(IS_ACK_GEN)){
		setValues({[FLG_ACK_GEN]: 'true'}, true);
		disableFieldOnDemand(FLG_ACK_GEN+','+BUTTON_CUSTOMER_ACK);
	}else if(!'Generated' == getValue(IS_ACK_GEN)){
		setValues({[FLG_ACK_GEN]: 'true'}, true);
		disableFieldOnDemand(FLG_ACK_GEN+','+BUTTON_CUSTOMER_ACK);
	}
}

function setFieldEnableDisableAtIntro(){
	var disable = [CID_FIRST, BUTTON_FETCH, TrnsRefNo, CUSTOMER_ID, CUSTOMER_NAME, TRANSACTION_CURRENCY, TRANSACTION_AMOUNT, TRANSACTION_REFERENCE];
	for(i=0; i<disable.length; i++){
		setStyle(disable[i], PROPERTY_NAME.DISABLE, 'true');
	}
}

function checkProcessingStatus(){
	var messageType=getValue(SWIFT_MESSAGE_TYPE);
	var swiftChannel=getValue('SWIFT_CHANNEL');
	if('700' == messageType ||'710' == messageType){ 
	       if('MT798' != swiftChannel ){
		   return validateField(SWIFT_PROCESSING_STATUS, 'Please select Processing Status.');
	         }
		return true;
	}else{
		return true;
	}
}

function mandatoryFetchModule(){
	var requestType = getValue(REQUEST_TYPE).trim();
	if(('IC_MSM' == requestType)||('EC_MSM' == requestType)||('ILC_MSM' == requestType)
			||('ELC_MSM' == requestType)||('ILCB_MSM' == requestType)||('ELCB_MSM' == requestType)
			||('GRNT_MSM' == requestType)||('TL_MSM' == requestType)||('DBA_MSM' == requestType)
			||('MISC_MSM' == requestType)||('IFS_MSM' == requestType)||('IFP_MSM' == requestType)
			||('SBLC_MSM' == requestType)){//added by mansi
		if(getValue(SWIFT_FETCH_MODULE) == ''){
			showMessage(SWIFT_FETCH_MODULE, MESSAGE.ERROR.ALERT_FETCH_MODULE, 'error');
			return false;
		} else return true;
	} else return true;
}

function checkTrnsMandate(reqCat,reqType){
	if(('GRNT' == (reqCat) 
			&& ('AM' == (reqType) 
					|| 'CC' == (reqType) 
					|| 'CL' == (reqType) 
					|| 'ER' == (reqType)
					|| 'EPC' == (reqType)
					|| 'GAA' == (reqType))) 
					||(('SBLC' == (reqCat)) // added by mansi
			&& ('SBLC_AM' == (reqType) || 'SBLC_ER' == (reqType)
					|| 'SBLC_CR' == (reqType)
					|| 'SBLC_CS' == (reqType)))
					||(('ELC' == (reqCat)) // added by mansi
			&& ('ELC_SLCAA' == (reqType) 
					|| 'ELC_SCL' == (reqType)
					|| 'ELC_SER' == (reqType)))
					|| (( 'IFS' == (reqCat)) && ('AM' == (reqType) || 'STL' == (reqType)))
					|| (( 'IFS' == (reqCat)) 
							&& ('AM' == (reqType) 
									|| 'STL' == (reqType)))
									|| (( 'IFP' == (reqCat)) 
											&& ('AM' == (reqType) 
													|| 'STL' == (reqType)))
													|| (('EC' == (reqCat))  
															&&('EC_AM' == (reqType)
																	|| 'EC_AC' == (reqType)
																	|| 'EC_CA' == (reqType)
																	|| 'EC_BS' == (reqType)
																	|| 'EC_BCDR' == (reqType)
																	|| 'EC_DISC' == (reqType)
																	|| 'EC_CAP' == (reqType) ))
																	|| ( ('IC' == (reqCat)) 
																			&&('IC_AM' == (reqType) 
																					|| 'IC_AC' == (reqType)
																					|| 'IC_BS' == (reqType)
																					|| 'IC_BCDR' == (reqType)
																					|| 'IC_LD' == (reqType)
																					|| 'IC_LAM' == (reqType)
																					|| 'IC_STL' == (reqType)
																					|| 'IC_PRC' == (reqType)))
																					|| ( ('DBA' == (reqCat)) 
																							&&('DBA_AM' == (reqType)
																									|| 'DBA_BS' == (reqType)))
																									||	(('ILCB' == (reqCat))
																											&&('ILCB_AM' == (reqType)
																													|| 'ILCB_AC' == (reqType)
																													|| 'ILCB_BS' == (reqType)
																													|| 'ILCB_BCDR' == (reqType)
																													|| 'ILCB_LD' == (reqType)
																													|| 'ILCB_LAM' == (reqType)
																													|| 'ILCB_STL' == (reqType)))
																													|| (('ELCB' == (reqCat)) 
																															&&( 'ELCB_AM' == (reqType)
																																	|| 'ELCB_AC' == (reqType)
																																	|| 'ELCB_BS' == (reqType)
																																	|| 'ELCB_BCDR' == (reqType)
																																	|| 'ELCB_DISC' == (reqType)
																																	|| 'ELCB_CLBP' == (reqType)))
																																	|| (('ELC' == (reqCat)) 
																																			&&('ELC_LCAA' == (reqType)
																																					|| 'ELC_LCCL' == (reqType)
																																					|| 'ELC_LCC' == (reqType)
																																					|| 'ELC_AOP' == (reqType)
																																					|| 'ELC_LCT' == (reqType)
																																					|| 'ELC_RSD' == (reqType)))
																																					||	(('ILC' == (reqCat)) 
																																							&&('ILC_AM' == (reqType) 
																																									|| 'ILC_CL' == (reqType)))
																																									||	(('TL' == (reqCat)) 
																																											&&('TL_AM' == (reqType) 
																																													|| 'TL_STL' == (reqType)))
	)
	{
		return true;
	}else{
		return false;
	}
}

function validateField(controlName, message){
	var controlValue = getValue(controlName);
	if(controlValue == '' || controlValue == 'undefined'){
		showMessage(controlName, message, 'error');
		return false;
	} else return true;
}

function validateNumberFields(controlName, message){
	var controlValue = getValue(controlName);
	if(controlValue == '' || controlValue == '0' || controlValue == '0.0' || controlValue == '0.00' || controlValue == '0.000'){
		showMessage(controlName, message, 'error');
		return false;
	} else return true;
}

function checkLengthValidation(sFieldName, alertMsg, length) {
	var fieldValue = getValue(sFieldName);
	if (fieldValue.length < length) {
		showMessage(sFieldName, alertMsg, 'error');
		return false;
	} else {
		return true;
	}
}

function validateBasicFieldsAtInitiator(){
	if(validateField(CUSTOMER_ID,MESSAGE.ERROR.ALERT_CUST_ID) && validateField(CUSTOMER_NAME,MESSAGE.ERROR.ALERT_CUST_NAME)
			&& mandtoryCheckAtInitaion() && validateField(REQUEST_CATEGORY,MESSAGE.ERROR.ALERT_REQ_CAT)
			&& validateField(REQUEST_TYPE,MESSAGE.ERROR.ALERT_REQ_TYPE) && validateField(REQUESTED_BY,MESSAGE.ERROR.ALERT_REQ_BY)
			&& validateField(SOURCE_CHANNEL,MESSAGE.ERROR.ALERT_SRC_CHNL) && validateField(TFO_BRANCH_CITY,MESSAGE.ERROR.ALERT_BRNCH_CITY)
			&& validateField(TFO_ASSIGNED_CENTER,MESSAGE.ERROR.ALERT_ASGN_CNTR)
			&& validateProcessingSystemFieldValue()){
		return true;
	} else return false;
}

function mandtoryCheckAtInitaion(){
	var requestType=getValue(REQUEST_TYPE);
	if((('IC_MSM' == requestType)||('EC_MSM' == requestType)||('ILC_MSM' == requestType)
			||('ELC_MSM' == requestType)||('ILCB_MSM' == requestType)||('ELCB_MSM' == requestType)
			||('GRNT_MSM' == requestType)||('TL_MSM' == requestType)||('DBA_MSM' == requestType)
			||('MISC_MSM' == requestType)||('IFS_MSM' == requestType)||('IFP_MSM' == requestType)
			||('SBLC_MSM' == requestType))//added by mansi
			&& 'CID' == getValue(SWIFT_FETCH_MODULE)){

		return true;
	}else{
		return ( validateField(TRANSACTION_CURRENCY, MESSAGE.ERROR.ALERT_TRNS_CURR)
				&& validateNumberFields(TRANSACTION_AMOUNT, MESSAGE.ERROR.ALERT_TRNS_AMT)
				&& validateField(RELATIONSHIP_TYPE, MESSAGE.ERROR.ALERT_REL_TYPE));
	}
}

function checkTrnRefNo(reqType){
	var chk=false;
	if( checkTrnsMandate(getValue(REQUEST_CATEGORY),reqType))
	{
		if(validateField(TRANSACTION_REFERENCE,'Please Enter Transaction Ref. Number') 
				&& checkLengthValidation(TRANSACTION_REFERENCE,'Please enter correct Transaction Ref. Number of length 16',16)){
			chk= true;
		}			
	}else {
		chk= true;
	}
	return chk;
}

function finalDecisionHandlingDelivery(){
	console.log('inside finalDecisionHandlingDelivery>>')
	var decision = getValue(DEC_CODE);
	var ModGtee= getValue('MODE_OF_GTEE');	
	console.log('decision: '+decision+', modGtee: '+ModGtee);
	if(decision == '' || decision == 'undefined'){
		if('Paper Guarantee' == ModGtee){
			console.log('Paper Guarantee MODE');
			setValues({[DEC_CODE]: 'DCBC'}, true);
		}
		else if('Guarantee Issued Thru Egtee Or Fta System' == ModGtee || 'Counter Guarantee' == ModGtee
				|| 'Guarantee Advised Thru Other Bank' == ModGtee){
			console.log(' NOT Paper Guarantee MODE');
			setValues({[DEC_CODE]: 'TISE'}, true);
		}
	} else {
		setValues({['FINAL_DESC_PPM']: getSelectedItemLabel(DEC_CODE)}, true);
	}
}

function handleEvent(object, event){
	console.log("Event: " + event.target.id + ", " + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusEvent(event);
	}
}

function handleDecHistLoadEvent(){
	console.log("Decision History Load Event: ");
	executeServerEvent(FRAME_Decision_Hist, EVENT_TYPE.LOAD, '', false);
}

function handleJSPResponse(typeOfJsp,data){
	 if(typeOfJsp=='UTC_BRMS_Calls'){
		selectSheet("tab1",1);
	}
}
function clickEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var requestType = getValue(REQUEST_TYPE).trim(); 
	var requestCat = getValue(REQUEST_CATEGORY).trim();
	var reqBy = getValue(REQUESTED_BY).trim();
	var srcChnl = getValue(SOURCE_CHANNEL).trim();

	if (event.target.id == BUTTON_CUSTOMER_ACK) {
		if(validateBasicFieldsAtInitiator() && checkTrnRefNo(requestType) && getValue(FLG_ACK_GEN) == true){
			saveWorkItem();
			showConfirmDialog(MESSAGE.ERROR.ALERT_ACK_CHK, confirmDialogButtons, function(result) {
				if(result == true) {
					var disable = [BUTTON_CUSTOMER_ACK, BUTTON_DISCARD, CID_FIRST, TRANSACTION_AMOUNT, TrnsRefNo, SOURCE_CHANNEL, 
					               REQUEST_CATEGORY, REQUEST_TYPE, RELATIONSHIP_TYPE, TRANSACTION_CURRENCY, REQUESTED_BY];
					for(i=0; i<disable.length; i++){
						console.log('disabling control: '+disable[i]);
						setStyle(disable[i], PROPERTY_NAME.DISABLE, 'true');
					}	
					executeServerEvent(event.target.id, event.type, '', false);
				} else if(result == false) {
					return;
				}
			});
		}
	} else if (event.target.id == BUTTON_FETCH) {
		// 15oct2020
		if(requestCat == ''){
			showMessage(REQUEST_CATEGORY, MESSAGE.ERROR.ALERT_REQ_CAT, 'error');
		} else if(requestType == ''){
			showMessage(REQUEST_TYPE, MESSAGE.ERROR.ALERT_REQ_TYPE, 'error');
		} else if(reqBy == ''){
			showMessage(REQUESTED_BY, MESSAGE.ERROR.ALERT_REQ_BY, 'error');
		} else if(srcChnl == ''){
			showMessage(SOURCE_CHANNEL, MESSAGE.ERROR.ALERT_SRC_CHNL, 'error');
		} else{
			document.getElementById("fade").style.display="block";
			CreateIndicator("Application");
			var response=executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', true);
			
			var jsonData = handleTFOResponse(response);
			if(jsonData.success){
				ProcessingSystemAfterFetch();  //Shivanshu
				
			}
			
		}
		//ProcessingSystemAfterFetch();  //Shivanshu
		
	} else if (event.target.id == CHKBX_SEND_MAIL) {
		if (getValue(REQUEST_TYPE) == 'ELCB_AOR')
		{
			var checkBoxSendEmail=getValue(CHKBX_SEND_MAIL);
			if('true' == checkBoxSendEmail){
				console.log(' in TFO_CHKBX_SEND_MAIL click event  yes ');
				setValues({[MANUALLY_TICKED_MAIL]: 'Y'}, true);
			}else{
				console.log(' in TFO_CHKBX_SEND_MAIL click event  no ');
				setValues({[MANUALLY_TICKED_MAIL]: 'N'}, true);
			}
			saveWorkItem();
		}
	} else if (event.target.id == BUTTON_SUBMIT && workstepName == WORKSTEP.INITIATOR) {
		if('SWIFT' == getValue(PROCESS_TYPE) && 'MISC' == getValue(REQUEST_CATEGORY) &&
				'TBPF' == getValue(SWIFT_DEC_ON_SWIFT_MESSAGE)){
			showMessage(SWIFT_DEC_ON_SWIFT_MESSAGE, MESSAGE.ERROR.ALERT_JUST_FOR_FILLING, 'error');		
		} else if(getValue('PROCESS_TYPE') == 'SWIFT' && validateBasicFieldsAtInitiator()
				&& checkTrnRefNo(requestType) && mandatoryFetchModule()
				&& validateField(SWIFT_MESSAGE_TYPE,MESSAGE.ERROR.ALERT_MSG_TYPE)
				&& checkProcessingStatus()
				&& validateField(SWIFT_DEC_ON_SWIFT_MESSAGE,MESSAGE.ERROR.ALERT_SWIFT_DEC) 
				&& checkField21()
				&& validateField(SWIFT_SENDER_BIC,MESSAGE.ERROR.ALERT_BIC) 
				&& validateMandatoryFieldsForSwift(SWIFT_FETCH_MODULE,MESSAGE.ERROR.ALERT_FETCH_MODULE) 
				&& validateMandatoryFieldsForSwiftReason(SWIFT_REASON_FOR_FILING,MESSAGE.ERROR.ALERT_FILING_REASON)){
			document.getElementById("fade").style.display="block";
			CreateIndicator("Application");	
			executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', false);
		} else if(('BAU' == getValue(PROCESS_TYPE)||'TSLM Front End' == getValue(PROCESS_TYPE))  && mandatoryFetchModule() && validateBasicFieldsAtInitiator()
				&& checkTrnRefNo(requestType)){
			document.getElementById("fade").style.display="block";
			CreateIndicator("Application");
			executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', false);
		}
	} else if (event.target.id == BUTTON_SUBMIT && workstepName == WORKSTEP.LOGISTICS) {
		if(validateField(TFO_BRANCH_CITY,MESSAGE.ERROR.ALERT_BRNCH_CITY) 
				&& validateField(TFO_ASSIGNED_CENTER,MESSAGE.ERROR.ALERT_ASGN_CNTR)){
			document.getElementById("fade").style.display="block";
			CreateIndicator("Application");	
			executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', false);
		}
	} else if (event.target.id == BUTTON_SUBMIT && workstepName == WORKSTEP.ASSIGNMENT_QUEUE) {
		if(validateField(ROUTE_TO_PPM,MESSAGE.ERROR.ALERT_ROUTE_PPM) 
				&& validateField(TFO_BRANCH_CITY,MESSAGE.ERROR.ALERT_BRNCH_CITY) 
				&& validateField(TFO_ASSIGNED_CENTER,MESSAGE.ERROR.ALERT_ASGN_CNTR)){
			document.getElementById("fade").style.display="block";
			CreateIndicator("Application");	
			executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', false);
		}
	}  else if (event.target.id == BUTTON_DISCARD && workstepName == WORKSTEP.INITIATOR) {
		showConfirmDialog(MESSAGE.ERROR.CONFIRM_DISCARD_MSG, confirmDialogButtons, function(result) {
			if(result == true) {
				setValues({[DEC_CODE]: 'Discard'}, true);
				executeServerEvent(event.target.id, event.type, '', false);
			} else if(result == false) {
				saveWorkItem();
				return;
			}
		});
	}   
	//ATP-459 11-JUL-2024 --JAMSHED START
	else if (event.target.id == BUTTON_RETRY_ADD_DOC && workstepName == WORKSTEP.INITIATOR) {
		executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', false);
	}
	//ATP-459 11-JUL-2024 --JAMSHED END
	//Traydstream |reyaz|09-10-2024|  start
	else if (event.target.id == BUTTON_TSINQUIRY && workstepName == WORKSTEP.TRAYDSTREAM) {
		if(validateField(TS_REFERENCE,MESSAGE.ERROR.ALERT_TS_REFERENCE)){	
		    hideControls(BUTTON_TSINQUIRY);
			showMessage(BUTTON_TSINQUIRY, 'Please close the workitem immediately', 'error');
			executeServerEvent(event.target.id, EVENT_TYPE.CLICK, '', false);
		}
	}
	//Traydstream |reyaz|09-10-2024|  end
}

function changeEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);
	var loan_group = getValue(COMBOX_IFS_LOAN_GRP);
	if (event.target.id == PROCESS_TYPE) {
		var processType = getValue(PROCESS_TYPE);
		processTypeChangeEvent();
		handleSWIFTUI();
		setFetchModule(SWIFT_FETCH_MODULE);
		enablefetchfields();
		initiatorhybridcombo();
		enableSwiftCID();
		executeServerEvent(event.target.id, EVENT_TYPE.CHANGE, '', false);
	} else if (event.target.id == SWIFT_DEC_ON_SWIFT_MESSAGE) {
		console.log('inside change event of SWIFT_DEC_ON_SWIFT_MESSAGE'+getValue('TFO_SWIFT_DEC_ON_SWIFT_MESSAGE'));
		if('JFF' == getValue(SWIFT_DEC_ON_SWIFT_MESSAGE))
		{
			enableFieldOnDemand('SWIFT_REASON_FOR_FILING');
		}else
		{
			disableFieldOnDemand('SWIFT_REASON_FOR_FILING');
		}
	} else if (event.target.id == REQUEST_TYPE) {
		setFetchModule(SWIFT_FETCH_MODULE);
		setReqByDefault(REQUESTED_BY, requestType, requestCat);
		executeServerEvent(event.target.id, event.type, '', false);
		if(requestCat == 'ILC' && requestType == 'ILC_UM'){//BY mansi
				setValues({[RELATIONSHIP_TYPE]: 'C'}, true);
			}
		else 
			{
			setValues({[RELATIONSHIP_TYPE]: ''}, true);
			}
		enableSwiftCID();
		
		/*if(requestType != 'LD'){ 
			showControls(STANDALONE_LOAN);
		}*/
		
		if(requestType == 'SIF' || requestType == 'APPIF'){
			enableFieldOnDemand('CID_Txt');
		}
		if(requestCat == 'IFA' || requestCat == 'IFS' || requestCat == 'IFP'){
			if(requestType != 'LD'){//BY KISHAN
				disableFieldOnDemand(PROCESSING_SYSTEM);
			}else{
				enableFieldOnDemand(PROCESSING_SYSTEM);
			}
		}
	} else if (event.target.id == CID_FIRST) {
		var len = getValue(CID_FIRST).trim().length;
		if(len<6){
			showMessage(CID_FIRST, 'Please enter correct CID', 'error');
			setValues({[CID_FIRST]: ''}, true);
		}
	}else if (event.target.id == FLG_ACK_GEN) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == RELATIONSHIP_TYPE) {
		var relationshipType = getValue(RELATIONSHIP_TYPE);
		if(('IFP' == requestCat || 'IFS' == requestCat || 'IFA' == requestCat) && requestType == 'LD'){//santhosh
			if('C' == relationshipType){
				setValues({[PROCESSING_SYSTEM]: 'T'}, true);
			} else {
				setValues({[PROCESSING_SYSTEM]: 'F'}, true);
			} 
		}
		saveWorkItem();
	} else if (event.target.id == TFO_BRANCH_CITY) {
		setValues({[TFO_ASSIGNED_CENTER]: getValue(TFO_BRANCH_CITY)}, true);
	} else if (event.target.id == SWIFT_MESSAGE_TYPE) {
		executeServerEvent(event.target.id, event.type, '', false);
		enableSwiftCID();
	} else if (event.target.id == SOURCE_CHANNEL) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == REQUESTED_BY) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == SWIFT_FETCH_MODULE) {
		setReqByDefault(REQUESTED_BY, requestType, requestCat);
		enablefetchfields();
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == SWIFT_PROCESSING_STATUS) {
		enablefetchfields();
		executeServerEvent(event.target.id, event.type, '', false);
		enableSwiftCID();
	} else if (event.target.id == IS_ACK_GEN) {
		disableAckCheckBox();
	} else if (event.target.id == REQUEST_CATEGORY) {
		setStyle(COMBOX_IFS_LOAN_GRP, PROPERTY_NAME.VISIBLE, 'false');//US145
		setStyle(ADCB_BILL_REF_NO, PROPERTY_NAME.VISIBLE, 'false');   //US145
		enableSwiftCID();
		var arr = [TRANSACTION_CURRENCY, TRANSACTION_AMOUNT, RELATIONSHIP_TYPE];
		if('MISC' == requestCat){
			nonMandateControls(arr);
		} else {
			mandateControls(arr);
		}
		if('IFP' == requestCat || 'IFS' == requestCat || 'IFA' == requestCat || 'IFCPC' == requestCat ){
			showControls(PROCESSING_SYSTEM);
			setValues({[PROCESSING_SYSTEM]: 'F'}, true); //20th Nov
			mandateControls([PROCESSING_SYSTEM]); 		
		} else if('SCF' == requestCat){ //ATP -191  Date 25 -10-2023 ADDED BY REYAZ
			showControls(PROCESSING_SYSTEM);
			setValues({[PROCESSING_SYSTEM]: 'T'}, true); //20th Nov
			mandateControls([PROCESSING_SYSTEM]);
		} else {
			hideControls(PROCESSING_SYSTEM);
		} 
	}else if (event.target.id == COMBOX_IFS_LOAN_GRP) { //US145	
		executeServerEvent(event.target.id, event.type, '', false);	
	}/* else if (event.target.id == TRANSACTION_CURRENCY) { 			  	
		if(!document.getElementById(TRANSACTION_CURRENCY).value.includes("-")){
			setValues({[TRANSACTION_CURRENCY]:""}, true);
		}
	 }*/else if (event.target.id == PROCESSING_SYSTEM){
			var requestCat = getValue(REQUEST_CATEGORY);
			var requestType = getValue(REQUEST_TYPE);
		 if(requestCat == 'IFA' || requestCat == 'IFS' ||requestCat == 'IFP'){
			 if(requestType == 'AM' || requestType == 'STL' || requestType == 'IFS_SD' 
				 || requestType == 'IFA_CTP' || requestType == 'IFA_SD' || requestType == 'IFP_SD' ){
				 				
				 {   disableFetchField();
				 showMessage(PROCESSING_SYSTEM, 'Please Click on Fetch', 'error');
				 }
				 
			  }
		 }
	 } 
    //Traydstream |reyaz|atp-518|07-10-2024| Start
	else if (event.target.id == SKIP_TS_FLAG) {
             var checkBoxSkipTSFlag =getValue(SKIP_TS_FLAG);
	     if(true == checkBoxSkipTSFlag){
		   showControls(TS_JUSTIFICATION);
	     }else{
		   hideControls('TS_JUSTIFICATION,TSTXNID');
		 }
	}
	else if (event.target.id == TS_JUSTIFICATION) {
        var tsjustication =getValue(TS_JUSTIFICATION);
		if('DST' == tsjustication){
		    showControls(TS_REFERENCE);
	    }else{
			hideControls(TS_REFERENCE);
		}
	}  
    //Traydstream |reyaz|atp-518|07-10-2024| End
}

function lostFocusEvent(event){
	console.log('inside lostFocusEvent >>');
	if (event.target.id == TRANSACTION_AMOUNT) {
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	}
	if (event.target.id == 'TRANSACTION_CURRENCY') {
		var curr = getValue(TRANSACTION_CURRENCY).trim();
		setValues({[TRANSACTION_CURRENCY]: curr}, true);
	}
}

function onLoadSection(frameId){
	console.log('inside onLoadSection, frame ID: '+frameId);
	if(frameId == FRAME_Decision_Hist || frameId == PM_DECISION_HISTORY){
		console.log('inside if frame ID: '+frameId);
		var rowCount = getGridRowCount('LVW_Decision_Sumary');
		console.log('rowCount of LVW_Decision_Sumary: '+rowCount);
		if(rowCount == 0){
			console.log('loading decision history..')
			executeServerEvent(frameId, EVENT_TYPE.LOAD, '', false);
		}
	}
}
function onTableCellChange(rowIndex,colIndex,ref,controlId){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == WORKSTEP.PROCESSING_MAKER){
		onTableCellChangePM(rowIndex,colIndex,ref,controlId);
	}
}
function openBRMSJSP(){
	 if(controlName == BUTTON_SUBMIT && workstepName == WORKSTEP.LOGISTICS && getValue('UTC_REQUIRED_BRMS')==''){ 
		setTabStyle('tab1',2, "visible", "true");
		selectSheet('tab1', 2);
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
		document.getElementById('sheet3_link').textContent = 'UTC BRMS';
		var jspURL=sLocationOrigin+"/TFO/CustomFolder/UTC_BRMS_Calls.jsp?WI_NAME="+getValue('WI_NAME');
		document.getElementById('JSP_UTC_FRAME').src=jspURL;
	}
	
}
function postServerEventHandler(controlName, eventType, responseData) {
	var jsonData = handleTFOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log("Control Name: "+ controlName +", Event Type: "+ eventType);
	console.log("Response Data:");
	console.log(jsonData);
	var requestCat = getValue(REQUEST_CATEGORY);
	var requestType = getValue(REQUEST_TYPE);
	// handling of PPM event
	if(isReadOnlyForm||workstepName ==WORKSTEP.WORK_EXIT||workstepName ==WORKSTEP.ARCHIVAL){
		switch (eventType) {
		case EVENT_TYPE.LOAD: 
			if(FORM==controlName){
				customerRefFieldEnableDisable();
			}
			break;}
	}else if(workstepName == WORKSTEP.PP_MAKER){
		return postServerEventHandlerPPM(controlName, eventType, responseData);
	} else if(workstepName == WORKSTEP.PROCESSING_MAKER){
		postServerEventHandlerPM(controlName, eventType, responseData);
	} else if(workstepName == WORKSTEP.PROCESSING_CHECKER){
		postServerEventHandlerPC(controlName, eventType, responseData);
	}else if(workstepName == WORKSTEP.PMPS || workstepName == WORKSTEP.PCPS || workstepName == WORKSTEP.SCC){
		postServerEventHandlerPCPM(controlName, eventType, responseData);
	}else if(workstepName == WORKSTEP.INITIATOR || workstepName == WORKSTEP.RM || workstepName == WORKSTEP.DELIVERY || 
			workstepName == WORKSTEP.PMPS || workstepName == WORKSTEP.PCPS || workstepName == WORKSTEP.LOGISTICS ||
			workstepName == WORKSTEP.ASSIGNMENT_QUEUE || workstepName == WORKSTEP.CUSTOMER_REVIEW || 
			workstepName == WORKSTEP.TSD || workstepName == WORKSTEP.LEGAL_TEAM || workstepName == WORKSTEP.TREASURY ||
			workstepName == WORKSTEP.COMPLIANCE || workstepName == WORKSTEP.CORRESPONDENT_BANK || 
			workstepName == WORKSTEP.TRADE_SALES || workstepName == WORKSTEP.CUSTOMER_REFERRAL_RESPONSE||
			workstepName == WORKSTEP.REPAIR_QUEUE || workstepName == WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE ||
			workstepName == WORKSTEP.TRAYDSTREAM)
	{
		switch (eventType) {

		case EVENT_TYPE.LOAD: 
			 
			if((getValue(INITIATOR_BRANCH) == '' || getValue(INITIATOR_USERID) == '') &&
					workstepName == WORKSTEP.INITIATOR){
				showMessage(INITIATOR_BRANCH, MESSAGE.ERROR.READONLY_FORM, 'error');
			}
			if(!jsonData.success){
				console.log('form load success false ...');
				showMessage('', MESSAGE.ERROR.READONLY_FORM, 'error'); // 22oct
				setStyle(SEC_REQ_DETAILS, PROPERTY_NAME.READONLY, 'true');
				setStyle(FRM_SWIFT, PROPERTY_NAME.READONLY, 'true');
				setStyle(SEC_CUST_DETAILS, PROPERTY_NAME.READONLY, 'true');
				setStyle(SUBSEC_CUST_DETAILS, PROPERTY_NAME.READONLY, 'true');
			}
			if(workstepName == WORKSTEP.DELIVERY){
				finalDecisionHandlingDelivery();
			} else if(workstepName == WORKSTEP.INITIATOR && jsonData.success && jsonData.message == '' && !isReadOnlyForm){
				setReqByDefault(REQUESTED_BY, requestType, requestCat);
				executeServerEvent(FORM, EVENT_TYPE.LOAD, 'post', false);
			}else if(workstepName == WORKSTEP.REPAIR_QUEUE && 'openRepairJSP'==jsonData.message){
				openCheckListTab();
			}else if(workstepName == WORKSTEP.CUSTOMER_REFERRAL_RESPONSE && jsonData.success){
				customerRefFieldEnableDisable();
			}else if(workstepName == WORKSTEP.ARCHIVAL && jsonData.success){
				customerRefFieldEnableDisable();
			}else if('openRepairJSP'==jsonData.message && workstepName == WORKSTEP.INITIATOR){ //ADDED BY KISHAN
					var callRequestType = jsonData.message;
					setTabStyle("tab1",1, "visible", "true"); //Check the tab name
					var urlDoc = document.URL;
					var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
					document.getElementById('sheet2_link').textContent = 'Application Orchestration';
					var jspURL=sLocationOrigin+"/TFO/CustomFolder/Repair_Integration_Calls.jsp?WI_NAME="
					+getValue('WI_NAME')+"&callRequestType="+callRequestType;
					document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
					//disableFieldOnDemand(BUTTON_SUBMIT);
					//selectSheet("tab1",1);
					setReqByDefault(REQUESTED_BY, requestType, requestCat); //ADDED BY KISHAN
					executeServerEvent(FORM, EVENT_TYPE.LOAD, 'post', false);//ADDED BY KISHAN
				}
			
			if(workstepName == WORKSTEP.INITIATOR){
				var arr = [TRANSACTION_CURRENCY, TRANSACTION_AMOUNT, RELATIONSHIP_TYPE];
				var swiftChannel=getValue('SWIFT_CHANNEL');
				if('MISC' == requestCat){
					nonMandateControls(arr);
				} else {
					mandateControls(arr);
				}
				//Added by Shivanshu ATP-458
				if(swiftChannel == 'MT798')
				{
					disableFieldOnDemand(INI_ILC_NI_SWIFT_MT798_DISABLE);
				}
                                setDefaultforHybridCustomer();//SCFMVP2.5 REYAZ 30-07-2024  					
				if(swiftChannel == 'MT798' && requestCat =='ILC' &&  requestType == 'ILC_AM' ){   //ATP-490 shahbaz 30-07-2024
			            enableFieldOnDemand(INI_ILC_AM_SWIFT_MT798_ENABLE);
		                }
			}
			if(workstepName == WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
				disableFieldOnDemand('FRM_VERIFY_DETAIL,PT_PROTRADE,PT_Document_Detail,PT_CUSTOMER_INS,FRM_LLI,PM_REF_FRAME,GTEE_FRAME,GRNT_FRM_NEW,PM_Decision_Frame,IELC_MKR_INPUT_FRM,LC_FRAME,ILC_Controls,FRM_DUPE,BILL_FRAME,'
						+'IF_FRAME,IFS_Final_Dec_frame,PC_ELC_Controls,frame5,frame7,ELC_Controls,frame23,frame24,IF_FRAME1,DEAL_DETAILS_FRAME,frame20,'
						+'FRM_TR_DTLS,TREASURY_FRAME,PC_ILC_Controls,FRM_GRTEE_LC_COMMON,FRM_PARTIES_DETAILS,frm_contract_limits,EC_Controls,ILCB_Controls,Common_Fields,PC_ILCB_Controls,PC_ELCB_Controls,ELCB_Controls,PT_COMMON,'
						+'PT_GTEE_FRAME,PT_FFT_FRAME,PT_ILC_FRAME,FRM_COUNTER_PARTY,Frame15');
				enableFieldOnDemand('DEC_CODE');
				enableFieldOnDemand(COMP_EXP_REMARKS);
				enableFieldOnDemand(LISTVIEW_FINAL_DECISION);
                enableFieldOnDemand('btnSubmit');
            	setTabStyle("tab1",PM_TRSD_SHEET_ID, "disable", "true");

                if("CRCA" == getValue(DEC_CODE)){
      			  enableFieldOnDemand(TFO_REMARKS);
      			} else{
      				disableFieldOnDemand(TFO_REMARKS);
      	        }
			}
			sfrmLoad = true;
			saveWorkItem();
			break;
		case EVENT_TYPE.CLICK:
			 if (controlName == BUTTON_CUSTOMER_ACK){
				console.log('enabling fetch button ..');
				setStyle(BUTTON_FETCH, PROPERTY_NAME.DISABLE, 'false');
				if(!jsonData.success){
					if(jsonData.message.includes('###')){
						var arr = jsonData.message.split('###');
						console.log()
						if(arr[0] == 'ngoAddOutput'){
							var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
							docFrameRef.customAddDoc(arr[2]);
							//reloadapplet(arr[1]);
							docFrameRef.document.getElementById('docOptionsDiv').style.display='none';
						}
					} 	 
				}
			} else if (controlName == FLG_ACK_GEN){
				if (jsonData.success) {
					saveWorkItem();
				}
			} else if (controlName == BUTTON_SUBMIT){
				if(workstepName == WORKSTEP.INITIATOR){
					 if (!jsonData.success && jsonData.message.includes('###')) {
						var arr = jsonData.message.split('###');
						setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
						showMessage(BUTTON_CUSTOMER_ACK,arr[1], 'error');
					}else if (!jsonData.success) {
						setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
						showMessage(BUTTON_CUSTOMER_ACK, MESSAGE.ERROR.ALERT_ACK, 'error');
					} else {
						saveWorkItem();
						completeWorkItem();
					}
					RemoveIndicator("Application");
					document.getElementById("fade").style.display="none";

				} else if(workstepName == WORKSTEP.LOGISTICS){
					if((('IFP' == getValue(REQUEST_CATEGORY) || 'IFS' == getValue(REQUEST_CATEGORY) || 'IFA' == getValue(REQUEST_CATEGORY)) && ('LD' == getValue(REQUEST_TYPE) || 'IFA_CTP' == getValue(REQUEST_TYPE))) 
							&& (getValue('UTC_REQUIRED_BRMS')=='')){ 
						//	if(getValue("UTC_BRMS_RETRY_COUNTER") < 3 || getValue("UTC_BRMS_RETRY_COUNTER") == ''  ){
							//	setValues({["UTC_BRMS_RETRY_COUNTER"]:(1+parseInt(getValue("UTC_BRMS_RETRY_COUNTER")))}, true);
								setTabStyle('tab1',2, "visible", "true");
								selectSheet('tab1', 2);
								var urlDoc = document.URL;
								var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
								document.getElementById('sheet3_link').textContent = 'UTC BRMS';
								var jspURL=sLocationOrigin+"/TFO/CustomFolder/UTC_BRMS_Calls.jsp?WI_NAME="+getValue('WI_NAME');
								document.getElementById('JSP_UTC_FRAME').src=jspURL;
								
//							} else {
//								setValues({["UTC_REQUIRED_BRMS"]:'Yes'}, true);
//							}
					}
					//Traydstream |reyaz|atp-518|17-09-2024| Start
					else if(!jsonData.message == ''&&('A'==jsonData.message)){
						var callRequestType = jsonData.message;
						if('A'==callRequestType){
							setTabStyle("tab1",3, "visible", "true");
							var urlDoc = document.URL;
							var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
							var jspURL=sLocationOrigin+"/TFO/CustomFolder/TraydStream_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&callRequestType="+callRequestType;
							document.getElementById(JSP_FRAME_LOGISTICS).src=jspURL;
							selectSheet("tab1",3);	 
							disableFieldOnDemand(BUTTON_SUBMIT);
						}
					}
				    //Traydstream |reyaz|atp-518|17-09-2024| End
						
					else if (jsonData.success){
						saveWorkItem();
						completeWorkItem();
					} else {
						setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
						if(!jsonData.message == ''){
							console.log('showing document missing error');
							showMessage('', jsonData.message, 'error');
						}
					}
				RemoveIndicator("Application");
					document.getElementById("fade").style.display="none";
				} else if(workstepName == WORKSTEP.ASSIGNMENT_QUEUE){
					if (jsonData.success){
						saveWorkItem();
						completeWorkItem();
					} else {
						setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
						if(!jsonData.message == ''){
							console.log('showing document missing error');
							showMessage('', jsonData.message, 'error');
						}
					}
					RemoveIndicator("Application");
					document.getElementById("fade").style.display="none";
				} else if(workstepName == WORKSTEP.REPAIR_QUEUE){
					if('openRepairJSP'==jsonData.message ){
						openCheckListTab();
					}else if (jsonData.success){
						saveWorkItem();
						completeWorkItem();
					}
				}
				else if(workstepName == WORKSTEP.RM || workstepName == WORKSTEP.DELIVERY || workstepName == WORKSTEP.PMPS
						|| workstepName == WORKSTEP.PCPS||workstepName == WORKSTEP.CUSTOMER_REVIEW || 
						workstepName == WORKSTEP.TSD || workstepName == WORKSTEP.LEGAL_TEAM || workstepName == WORKSTEP.TREASURY ||
						workstepName == WORKSTEP.COMPLIANCE || workstepName == WORKSTEP.CORRESPONDENT_BANK || 
						workstepName == WORKSTEP.TRADE_SALES || workstepName == WORKSTEP.CUSTOMER_REFERRAL_RESPONSE ||  workstepName == WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
					if(!jsonData.message == ''){
						console.log('showing document missing error');
						showMessage('', jsonData.message, 'error');
						setStyle(RM_BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');

					}else if (jsonData.success){
						saveWorkItem();
						completeWorkItem();
					} 
				} else if(workstepName == WORKSTEP.PROCESSING_MAKER){
					if (jsonData.success){
						completeWorkItem();
					}
				} else if(workstepName == WORKSTEP.TRAYDSTREAM){
					saveWorkItem();
					completeWorkItem();
				}
			} else if (controlName == BUTTON_FETCH && workstepName == WORKSTEP.INITIATOR){
				if(!jsonData.message == ''){
					var arr = jsonData.message.split(',');
					if((arr[1]).includes('#Comma#')){
						arr[1]=arr[1].replace('#Comma#','.');
					}
					showMessage(arr[0], arr[1], 'error');
				}
				setStyle(BUTTON_FETCH, PROPERTY_NAME.DISABLE, 'false');
				saveWorkItem();
				RemoveIndicator("Application");
				document.getElementById("fade").style.display="none";
			} else if (controlName == BUTTON_DISCARD && workstepName == WORKSTEP.INITIATOR){
				if(jsonData.success){
					completeWorkItem();
				}
			} else if(workstepName == WORKSTEP.PP_MAKER){
				alert(jsonData.success);
				if (!jsonData.success){
					var arr = jsonData.message.split(',');
					showMessage(arr[1], arr[2], 'error');
				}
			} //SCFMVP2.5 REYAZ 30-07-2024  START
			else if (controlName == BUTTON_RETRY_ADD_DOC && workstepName == WORKSTEP.INITIATOR){
				window.parent.refreshWorkitem();
			}
			//SCFMVP2.5 REYAZ 30-07-2024  END
			/*else if (controlName == BUTTON_TSINQUIRY && workstepName == WORKSTEP.TRAYDSTREAM) {	
				window.parent.closeWorkdesk();
	        } */
			break;
		case EVENT_TYPE.CHANGE:
			if (controlName == REQUEST_TYPE){
				if (jsonData.success) {
					enablefetchfields();
				}
			} else if (controlName == SOURCE_CHANNEL){
				console.log('post server req cat ---');
			} else if (controlName == SWIFT_PROCESSING_STATUS) {
				enablefetchfields();
			}	
			break;
		case EVENT_TYPE.LOSTFOCUS: 

			if (!jsonData.success) {
				console.log('post server...success false..giving alert for lostfocus event of transaction amount');
				if(jsonData.message == ALERT_VALID_AMT){
					alert(MESSAGE.ERROR.ALERT_VALID_AMT);
					setFocus(controlName);
				} else if(jsonData.message == ALERT_AMT_LNGTH){
					alert(MESSAGE.ERROR.ALERT_AMT_LNGTH);
					setFocus(controlName);
				}
			} else {
				console.log('post server...success true for lostfocus event of transaction amount');
			}
			break;	
		}
	}
}

function checkField21(){

	if(!("Y" == getValue("SWIFT_UTILITY_FLAG"))){
		return validateField("SWIFT_FIELD_21", "Please enter Field 21.");
	}else{
		return true;
	}

}

function validateMandatoryFieldsForSwift(sFieldName, alertMsg) {

	if("SWIFT" == (getValue(PROCESS_TYPE)) && "MISC_MSM" == (getValue(REQUEST_TYPE)))
	{
		console.log("Validation Conrol Name :"+sFieldName);
		var fieldValue = getValue(sFieldName);
		console.log("fieldValue --validateMandatoryFields  " + fieldValue);
		return validateField(sFieldName, alertMsg);
	}else
	{
		return true;
	}

}

function validateMandatoryFieldsForSwiftReason(sFieldName, alertMsg) {
	if("JFF" == (getValue("SWIFT_DEC_ON_SWIFT_MESSAGE")))
	{
		console.log("Validation Conrol Name :"+sFieldName);
		var fieldValue = getValue(sFieldName);
		console.log("fieldValue --validateMandatoryFields  " + fieldValue);
		return validateField(sFieldName, alertMsg);
	}else
	{
		return true;
	}
}


function onClickTab(tabId,sheetindex,eventCall){
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex: '+sheetindex);
	var workstepName = getWorkItemData('activityName');
	if(isReadOnlyForm||workstepName ==WORKSTEP.WORK_EXIT||workstepName ==WORKSTEP.ARCHIVAL){
		return true;
	}
	else if (workstepName == WORKSTEP.RM || workstepName == WORKSTEP.CUSTOMER_REVIEW || workstepName == WORKSTEP.TSD
			|| workstepName == WORKSTEP.LEGAL_TEAM || workstepName == WORKSTEP.TREASURY || workstepName == WORKSTEP.COMPLIANCE
			|| workstepName == WORKSTEP.CORRESPONDENT_BANK || workstepName == WORKSTEP.TRADE_SALES
			|| workstepName == WORKSTEP.DELIVERY || workstepName == WORKSTEP.PMPS){
		if(tabId == 'tab1'){
			if(sheetindex == 0){
				setStyle(RM_BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'false');
				setStyle(RM_BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'true');
			} else if(sheetindex == 1){
				setStyle(RM_BUTTON_NEXT, PROPERTY_NAME.DISABLE, 'true');
				setStyle(RM_BUTTON_PREVIOUS, PROPERTY_NAME.DISABLE, 'false');
			}
		}
	} else if(workstepName==WORKSTEP.PP_MAKER){
		onClickTabPPM(tabId,sheetindex,eventCall);
	} else if(workstepName==WORKSTEP.PROCESSING_MAKER){
		onClickTabPM(tabId,sheetindex,eventCall);
		selectSheet(tabId,sheetindex);
	} else if(workstepName==WORKSTEP.PROCESSING_CHECKER){
		onClickTabPC(tabId,sheetindex,eventCall);
	} else if(workstepName==WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
		onClickTabPM(tabId,sheetindex,eventCall);
		
	} else if(workstepName==WORKSTEP.REPAIR_QUEUE){
		if((document.getElementById(JSP_FRAME_CONTRACT)!=null  &&document.getElementById('tab1').
				getElementsByClassName("tab-pane fade")[2].style.display!='none')
				&&sheetindex!=2 && eventCall==2 ){
			showMessage('', 'Please click on close button', 'error');
			return false;
		}
	}else if(workstepName==WORKSTEP.PCPS){
		onClickTabPMPC(tabId,sheetindex,eventCall);
	} 	
 }


function handleTFOResponse(responseData) {
	var jsonData;
	var workstep = getWorkItemData('activityName');
	try {
		jsonData = JSON.parse(responseData); // STRING TO JSON
	} catch(error) {
		jsonData = responseData; // ALREADY A JSON
	}
	return jsonData;
}

//generic function if listview save button..custom coding required
function customListViewValidation(controlId,flag){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PP_MAKER){
		return customListViewValidationPPM(controlId,flag);
	}else if( workstepName==WORKSTEP.PROCESSING_MAKER){
		return customListViewValidationPM(controlId,flag);
	}
	return true;

}

function showSection(controlNames){
	var arr = controlNames.split(',');
	for(var i=0; i<arr.length; i++){
		console.log('showing control: '+arr[i]);
		setStyle(arr[i],PROPERTY_NAME.VISIBLE, 'true');
	}
}
function clearTFOControls(controls) {
	var cName = controls.split(',');
	for (var i = 0; i < cName.length; i++) {
		console.log('clearing control: ' + cName[i]);
		setValues({[cName[i]]: ''}, true);
	}
}
function postHookDBLink(controlId , event , index ,controlId){
	console.log('postHookDBLink >> control: '+controlId+', event type: '+event);
	var workstepName = getWorkItemData('activityName');
	var requestCat = getValue(REQUEST_CATEGORY);
	var requestType = getValue(REQUEST_TYPE)
	if (controlId == REQUEST_CATEGORY && index == 2 && workstepName == WORKSTEP.INITIATOR) {
		console.log('request category change..');
		setReqByDefault(REQUESTED_BY, requestType, requestCat);
		enablefetchfields();
		executeServerEvent(controlId, event, '', false);
	}
}
function getAccountNumber(){
	var accNo="0";
	var reqCat = getValue(REQUEST_CATEGORY);
	if("GRNT"==reqCat|| "SBLC"==reqCat|| "ELC"==reqCat && ("ELC_SLCA"== reqType ||"ELC_SLCAA"==reqType
			|| "ELC_SER"==reqType|| "ELC_SCL"==reqType)|| "ILC"==reqCat){//added by mansi
		accNo= getValue("ACCOUNT_NUMBER");
	}else if("IFS"==reqCat|| "IFP"==reqCat || "IFA"==reqCat || "TL"==reqCat|| "EC"==reqCat|| "IC"==reqCat
			|| "DBA"==reqCat|| "ELCB"==reqCat|| "ILCB"==reqCat){
		accNo= getValue("INF_CHARGE_ACC_NUM");
	}
	return accNo;
} 
//row click event of any listview
function onRowClick(listviewId,rowIndex){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PP_MAKER){
		return onRowClickPPM(listviewId,rowIndex);
	} else if (workstepName == WORKSTEP.PROCESSING_MAKER || workstepName == WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE) {
		return onRowClickPM(listviewId,rowIndex);
	} else if (workstepName == WORKSTEP.PROCESSING_CHECKER) {
		return onRowClickPC(listviewId,rowIndex);
	}
	else {
		return false;
	}
}

function saveAndNextPreHook(tabId){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PP_MAKER){
		return saveAndNextPreHookPPM(tabId);
	} else if(workstepName==WORKSTEP.PROCESSING_MAKER){
		return saveAndNextPreHookPM(tabId);
	} else if(workstepName==WORKSTEP.PROCESSING_CHECKER){
		return saveAndNextPreHookPM(tabId);
	} else if(workstepName==WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
		return saveAndNextPreHookPM(tabId);
	}
	return true;
}

function selectRowHook(tableId,selectedRowsArray,isAllRowsSelected){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PROCESSING_MAKER){
		selectRowHookPM(tableId,selectedRowsArray,isAllRowsSelected);
	}else if(workstepName==WORKSTEP.PP_MAKER){
		selectRowHookPPM(tableId,selectedRowsArray,isAllRowsSelected); //BY KISHAN
	}
}
function subFormPreHook(buttonId){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PP_MAKER){
		return subFormPreHookPPM(buttonId);
	} else if(workstepName==WORKSTEP.PROCESSING_MAKER || workstepName==WORKSTEP.PROCESSING_CHECKER){
		return subFormPreHookPM(buttonId);
	} else if(workstepName==WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
		return false;
	}
	return true;
}

function subformDoneClick(buttonId){
	console.log('buttonId: '+buttonId);
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PROCESSING_MAKER){
		subformDoneClickPM(buttonId);
	}
}

function closeandReturn(tableId)
{

	var mandatoryfail=false;
	var error=false;
	var val="";
	var tbl=document.getElementById(tableId);
	var i=0;
	for(i=1;i<tbl.rows.length;i++)
	{
		if(tbl.rows.item(i).cells.item(3).innerText=='Success')
		{
			val='Success';
		}
		else 
		{
			val='Failed';
			break;
		}

	}
	return val;
}

function showInvoiceTab()
{
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY); //ATP - 202
 if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat  || 'SCF' == reqCat ) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType|| 'PDD' == reqType)) {
		setTabStyle("tab1",8, "visible", "true");
	} else if(!(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType|| 'PDD' == reqType))) {
		setTabStyle("tab1",8, "visible", "false");
	}
}

function lockTFOSection(controls){
	var arr = controls.split(',');
	for(var i=0; i<arr.length; i++){
		console.log('locking control: '+arr[i]);
		setStyle(arr[i],'lock', 'true');
	}
}
function removeIndicator(){
	RemoveIndicator("Application");
	document.getElementById("fade").style.display="none";
}
function applyIndicator(){
	document.getElementById("fade").style.display="block";
	CreateIndicator("Application");
}

//Added by Ayush
//function appifreception(){
//if(getValue('REQUEST_TYPE') == 'APPIF' || getValue('REQUEST_CATEGORY') == 'IFCPC'){
//setValue('INITIATOR_BRANCH','Karama,Dubai');
//setValue('INITIATOR_USERID','bpmserviceuser');
//setStyle('WI_NAME',PROPERTY_NAME.DISABLE,'TRUE');
//setStyle('PROCESS_TYPE',PROPERTY_NAME.DISABLE,'TRUE');
//}
//}

function enableSwiftCID(){
	var reqCat=getValue(REQUEST_CATEGORY);
	var reqType=getValue(REQUEST_TYPE);
	var msgType=getValue(SWIFT_MESSAGE_TYPE);
	var processsingCenter=getValue(SWIFT_PROCESSING_STATUS);
	var processtype = getValue(PROCESS_TYPE);

	if(processtype == 'SWIFT'){
		if(reqCat == 'ELC' &&  reqType == 'ELC_LCA' && (msgType == '700' || msgType == '710') &&  processsingCenter == 'P')
		{
			enableFieldOnDemand(FIELD_SWIFT_CID);
		}
		else{   	    
			disableFieldOnDemand(FIELD_SWIFT_CID);
			setValues({[FIELD_SWIFT_CID]: ''}, true);
		}
	}
}

//Shipping_gtee_6
function setSGInitiatorDefaultValue(requestCat,reqType){
	if('BAU' == getValue(PROCESS_TYPE)){
		if('SG_NILC' == reqType || 'SG_NIC' == reqType ){
			setValues({[REQUESTED_BY]: 'SG_CT'}, true);
		}else if ('SG_SD' == reqType ) {
			setValues({[REQUESTED_BY]: 'SG_AT'}, true); 							
		}
	}
}
//Shipping_gtee_7
function setSGDefaultScrChannel(requestType){
	if('BAU' == getValue(PROCESS_TYPE)){		
		setValues({[SOURCE_CHANNEL]: 'SG_B'}, true);
	}
}

//Shipping_Gtee_14
function SGFieldEnableDisable(requestType){
	if('SG_NIC' == requestType){ 
		if(sfrmLoad) {
			setFieldNullValue();
		}
		setValues({ 
			[PT_CUSTOMER_IC]:''}, true);

		enableFieldOnDemand(TRANSACTION_CURRENCY);	 //Shipping_gtee_14
		enableFieldOnDemand(TRANSACTION_AMOUNT);		 //Shipping_gtee_15
		enableFieldOnDemand(RELATIONSHIP_TYPE);		//Shipping_gtee_16
		setValues({[TRNS_BAL]: ''}, true);  			 //Shipping_gtee_13
		setValues({[TRN_STATUS]: ''}, true);			 //Shipping_gtee_13
	} else if('SG_NILC' == requestType){
		if(sfrmLoad) {
			setFieldNullValue();
		}
		setValues({
			[PT_CUSTOMER_IC]:''}, true);
		enableFieldOnDemand(TRANSACTION_AMOUNT);  	//Shipping_gtee_15
		enableFieldOnDemand(RELATIONSHIP_TYPE);		//Branch Code  //Shipping_Gtee_16
		disableFieldOnDemand(TRANSACTION_CURRENCY);   //Shipping_gtee_14
		setValues({[TRNS_BAL]: ''}, true);    			//Shipping_gtee_13
		setValues({[TRN_STATUS]: ''}, true);   			//Shipping_gtee_13
	} else if('SG_SD' == requestType){ 
		if(sfrmLoad) {
			setFieldNullValue();
		}
		setValues({ 
			[PT_CUSTOMER_IC]:''}, true);
		disableFieldOnDemand(RELATIONSHIP_TYPE); //Branch Code  //Shipping_Gtee_16
		disableFieldOnDemand(TRANSACTION_CURRENCY);   //Shipping_gtee_14
		disableFieldOnDemand(TRANSACTION_AMOUNT);     //Shipping_gtee_15
	}
}

function disablePTFields2() {
	console.log("Inside disablePTFields");
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PT_UTILITY_FLAG) == 'Y') {
		if(reqCat == 'ILC' && reqType == 'ILC_NI'){
			console.log("Inside disablePTFields2");
			disableFieldOnDemand(FIELD_LC_TOLERANCE);	
			disableFieldOnDemand(EXP_DATE);
			disableFieldOnDemand(COMBOX_TRN_THIRD_PARTY);
			disableFieldOnDemand(FIELD_LC_UNDER);
			disableFieldOnDemand(FIELD_LC_ABOVE);
		}
	}
}

function ecFramePT(){
	console.log('INSIDE ecFramePT');
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PT_UTILITY_FLAG) == 'Y'){
		if(reqCat == 'EC'){
			if(reqType == 'EC_BL'){
				showSection('PT_COMMON');
				setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
				showSection('PT_PROTRADE');
				showSection('PT_Document_Detail');
				showSection('CUST_INSTRUCTIONS');
				showSection('EC_ELCB_COMMON_FRAME');
			}
		}
	}
}

function disablePTFields() {
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PT_UTILITY_FLAG) == 'Y') {
		if(reqCat == 'GRNT' && (reqType == 'NI' || reqType == 'AM')){
			if ('NI' == reqType){
				showSection('PT_GTEE_FRAME,PT_COMMON,PT_FFT_Frame');
				setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
				disableFieldOnDemand(GRNT_DISABLE_PT_FIELDS);
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				//disableFieldOnDemand('FRM_PARTIES_DETAILS');
				disableFieldOnDemand('frm_contract_limits');
			} else if ('AM' == reqType){
				showSection('PT_COMMON');
				setStyle('CUST_REF_NO', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				disableFieldOnDemand(GRNT_DISABLE_AM_PT_FIELDS);

			}
			disableFieldOnDemand(FIELD_GRNTEE_APP_CNTRCT_REF_NO);
			disableFieldOnDemand(FIELD_GRNTEE_BEN_CNTRCT_REF_NO);

		}else if(reqCat == 'SBLC'&& (reqType == 'SBLC_NI' || reqType == 'SBLC_AM')){//added by mansi(SBLC)
			if ('SBLC_NI' == reqType){
				showSection('PT_GTEE_FRAME,PT_COMMON,PT_FFT_Frame');
				setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
				disableFieldOnDemand(GRNT_DISABLE_PT_FIELDS);
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				//disableFieldOnDemand('FRM_PARTIES_DETAILS');
				disableFieldOnDemand('frm_contract_limits');
			} else if ('SBLC_AM' == reqType){
				showSection('PT_COMMON');
				setStyle('CUST_REF_NO', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				disableFieldOnDemand(GRNT_DISABLE_AM_PT_FIELDS);

			}
			disableFieldOnDemand(FIELD_GRNTEE_APP_CNTRCT_REF_NO);
			disableFieldOnDemand(FIELD_GRNTEE_BEN_CNTRCT_REF_NO);

		}else if(reqCat == 'ELC'&& (reqType == 'ELC_SLCA' || reqType == 'ELC_SLCAA')){//added by mansi(ELC)
			if ('ELC_SLCA' == reqType){
				showSection('PT_GTEE_FRAME,PT_COMMON,PT_FFT_Frame');
				setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
				disableFieldOnDemand(GRNT_DISABLE_PT_FIELDS);
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				//disableFieldOnDemand('FRM_PARTIES_DETAILS');
				disableFieldOnDemand('frm_contract_limits');
			} else if ('ELC_SLCAA' == reqType){
				showSection('PT_COMMON');
				setStyle('CUST_REF_NO', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				disableFieldOnDemand(GRNT_DISABLE_AM_PT_FIELDS);

			}
			disableFieldOnDemand(FIELD_GRNTEE_APP_CNTRCT_REF_NO);
			disableFieldOnDemand(FIELD_GRNTEE_BEN_CNTRCT_REF_NO);

		}else if(reqCat == 'ILC' && (reqType == 'ILC_NI' || reqType == 'ILC_AM')){
			if(reqType == 'ILC_NI'){
				console.log("Inside disablePTFields");
				showSection('PT_COMMON,PT_ILC_FRAME');
				setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
				disableFieldOnDemand(ILC_NI_DISABLE_PT_FIELDS);
				disableFieldOnDemand('frm_contract_limits');
			} else if(reqType == 'ILC_AM'){
				showSection('PT_COMMON,PT_ILC_FRAME2');
				setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('CUST_REF_NO_label', PROPERTY_NAME.VISIBLE, 'false');
			}
		}
		else if(reqCat == 'EC' && reqType == 'EC_BL'){
			disableFieldOnDemand(SOURCE_CHANNEL+','+BILL_FRAME+','+DEAL_DETAILS_FRAME);
			//disableFieldOnDemand('FRM_PARTIES_DETAILS');
			enableFieldOnDemand('IS_REMOTE_PRESENTATION');
			disableFieldOnDemand('frm_contract_limits');
			setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');

		} else if( reqCat == 'ELCB' && reqType == 'ELCB_BL'){
			disableFieldOnDemand(ELCB_BL_DISABLED_FIELDS);
			showSection('PT_COMMON');
			showSection('PT_PROTRADE');
			showSection('PT_Document_Detail');
			showSection('PT_CUSTOMER_INS');
			showSection('EC_ELCB_COMMON_FRAME');
			setStyle('ISSUING_BANK_LC_NO', PROPERTY_NAME.VISIBLE, 'true'); 
			setStyle('ISSUING_BANK_LC_NO', PROPERTY_NAME.DISABLE, 'true');
			setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
			setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');

		} else if(reqType == 'ILCB_BS'){
			disableFieldOnDemand(SOURCE_CHANNEL);
		}else if(reqType == 'ILCB_BCDR'){
			setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
		}
	}
}

function enablePTFieldsAtPCPM(){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PT_UTILITY_FLAG) == 'Y'){
		if(reqCat == 'EC'){
			if(reqType == 'EC_BL'){
				enableFieldOnDemand(RELATIONSHIP_TYPE+','+COMBOX_PRODUCT_TYPE);
				enableFieldOnDemand('INF_TENOR_DAYS','INF_TENOR_BASE','INF_BASE_DOC_DATE','INF_MATURITY_DATE','INF_CHARGE_ACC_NUM','INF_SETTLEMENT_ACC_NUM');
			}
		}
	}
}

function disableForILC_AM(){  //US116
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var workstepName = getWorkItemData('activityName');
	if(reqCat == 'ILC' && reqType == 'ILC_AM'){ 
		showSection('PT_COMMON,PT_AMENDMENT_FRAME');
		disableFieldOnDemand('PT_CUST_EMAIL_ID','ADDN_CONDITION','NARRATIVE');
		if(getValue(PT_UTILITY_FLAG) == 'Y'){
			showSection('PT_ILC_FRAME2');
			setStyle(PT_CUST_EMAIL_ID, PROPERTY_NAME.VISIBLE, 'true');   	//US116
			setStyle(ADDN_CONDITION, PROPERTY_NAME.VISIBLE, 'true');  		//US116
			setStyle(NARRATIVE, PROPERTY_NAME.VISIBLE, 'true');   			//US116 	 
		} 
		else{
			setStyle(PT_CUST_EMAIL_ID, PROPERTY_NAME.VISIBLE, 'false');   //US116
		}
		setStyle(PT_AMENDMENT_FRAME,"lock","true");
		if(workstepName == WORKSTEP.PROCESSING_CHECKER){
			setStyle(PT_AMENDMENT_FRAME, PROPERTY_NAME.DISABLE, 'true');
		}
	}else if(PT_UTILITY_FLAG == 'Y'){
		if( reqType == 'ELCB_AM'){ setStyle(PT_CUST_EMAIL_ID, PROPERTY_NAME.VISIBLE, 'true');}
	}
	if( reqType == 'ELC_LCAA' || reqType == 'ELC_LCC'){ //PT_278-279
		showSection('PT_AMENDMENT_FRAME');
		disableFieldOnDemand('PT_CUST_EMAIL_ID');
		setStyle(PT_AMENDMENT_FRAME,"lock","true");
		if(workstepName == WORKSTEP.PROCESSING_CHECKER){
			setStyle(PT_AMENDMENT_FRAME, PROPERTY_NAME.DISABLE, 'true');
		}
	}
}

function okOperation(control){
	console.log('control'+control);
	if('showNotificationAlert'==control){ //PM
		saveWorkItem();	 
		completeWorkItem(); 
	}
}
function enableIndicator(){
	document.getElementById("fade").style.display="block";
	CreateIndicator("Application");	 
}
function disableIndicator(){
	RemoveIndicator("Application");
	document.getElementById("fade").style.display="none";
}

function enableCustInstSection(){  //SP_US_149 -158
	setStyle(CUST_INSTRUCTION_SECTION,"lock","true");
	var req_type = getValue(REQUEST_TYPE);
	var loan_grp = getValue(COMBOX_IFS_LOAN_GRP);
	var product_code = getValue(COMBOX_PRODUCT_TYPE);
	var workstepName = getWorkItemData('activityName');
	console.log('inside enableCustInstSection');
	if( req_type == 'ILCB_BCDR' ){
		console.log('inside enableCustInstSection for request type: '+req_type);
		showSection(CUST_INSTRUCTION_SECTION);	 
		enableFieldOnDemand('IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION'); 
		if(getValue(PT_UTILITY_FLAG) == 'Y'){
			setStyle('BILL_TYPE', PROPERTY_NAME.VISIBLE, 'false');
			setStyle('IF_SIGHT_BILL', PROPERTY_NAME.VISIBLE, 'false');
			disableFieldOnDemand('DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION,SOURCE_CHANNEL');
		}
	}else if(req_type == 'IC_AC' ||req_type == 'IC_BS'){
		showSection(CUST_INSTRUCTION_SECTION);
		enableFieldOnDemand('IF_SIGHT_BILL,SETTLEMENT_INSTRUCTION,DISCREPANCY_INSTRUCTION');
		//setStyle(DISCREPANCY_INSTRUCTION,PROPERTY_NAME.VISIBLE,'false');
		if(getValue(PT_UTILITY_FLAG) != 'Y'){
			if(getValue(BILL_TYPE) == '' || getValue(BILL_TYPE) == null){
				setValues({[BILL_TYPE]: 'IC'}, true);
			}
			if(getValue(IF_SIGHT_BILL) == '' || getValue(IF_SIGHT_BILL) == null){
				if(product_code == 'T3S2' || product_code == 'I3S2'){
					setValues({[IF_SIGHT_BILL]: '1'}, true);
				} else{
					setValues({[IF_SIGHT_BILL]: '2'}, true);
				}
			}
			if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
				if(req_type == 'IC_BS'){
					setValues({[DISCREPANCY_INSTRUCTION]: 'BL'}, true);
				}
				else{
					setValues({[DISCREPANCY_INSTRUCTION]: 'A'}, true);
				}
				/* if(getValue('BILL_STAGE') == 'FIN'){
					 setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
			 		} else{
			 			 setValues({[DISCREPANCY_INSTRUCTION]: 'A'}, true);
			 		} */
			}
		}else{
			setStyle(DISCREPANCY_INSTRUCTION,PROPERTY_NAME.VISIBLE,'true');
			setStyle(PT_CUST_EMAIL_ID, PROPERTY_NAME.VISIBLE, 'true');
			disableFieldOnDemand(SETTLEMENT_INSTRUCTION+','+SOURCE_CHANNEL);
			if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){				  
				setValues({[DISCREPANCY_INSTRUCTION]: 'A'}, true);
			}
		}	 	 
	}else if(req_type == 'ILCB_AC' ||req_type == 'ILCB_BS'){
		showSection(CUST_INSTRUCTION_SECTION);
		enableFieldOnDemand('IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION');
		if(getValue(PT_UTILITY_FLAG) != 'Y'){
			if(getValue(BILL_TYPE) == '' || getValue(BILL_TYPE) == null){
				setValues({[BILL_TYPE]: 'ILCB'}, true);
			}
			if(getValue(IF_SIGHT_BILL) == '' || getValue(IF_SIGHT_BILL) == null){
				if(product_code == 'T3S1' || product_code == 'I3S1'){
					setValues({[IF_SIGHT_BILL]: '1'}, true);
				} else{
					setValues({[IF_SIGHT_BILL]: '2'}, true);
				}
			}
			if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
				if(getValue('BILL_STAGE') == 'FIN'){
					setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
				} 
				if(req_type == 'ILCB_AC'){
					setValues({[DISCREPANCY_INSTRUCTION]: 'A'}, true);
				}else if(req_type == 'ILCB_BS'){
					setValues({[DISCREPANCY_INSTRUCTION]: 'BL'}, true);
				}
			}				
		}else{
			disableFieldOnDemand('DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION');
			if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){				  
				setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
			}
			if(workstepName != WORKSTEP.PP_MAKER){
				disableFieldOnDemand(IF_SIGHT_BILL);
			}

		} 
	}else if(req_type == 'TL_LD'){
		if(loan_grp == 'ILCB' || loan_grp == 'IC'){
			showSection(CUST_INSTRUCTION_SECTION);
			if(getValue(PT_UTILITY_FLAG) != 'Y'){
				if(getValue(BILL_TYPE) == '' || getValue(BILL_TYPE) == null){
					setValues({[BILL_TYPE]: loan_grp}, true);
				}
				if(getValue(IF_SIGHT_BILL) == '' || getValue(IF_SIGHT_BILL) == null){
					if(product_code == 'T3S1' || product_code == 'I3S1' || product_code == 'T3S2' || product_code == 'I3S2'){
						setValues({[IF_SIGHT_BILL]: '1'}, true);
					} else{
						setValues({[IF_SIGHT_BILL]: '2'}, true);
					}
				}
				/*if(loan_grp == 'IC'){
					if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
						if(getValue('BILL_STAGE') == 'FIN' && product_code != 'T3S2' && product_code != 'I3S2'){
							setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
						} else{
							setValues({[DISCREPANCY_INSTRUCTION]: 'NA'}, true);
						}
					}	}else{
						if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
							if(getValue('BILL_STAGE') == 'FIN' ){
								setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
							}
						}
					}	*/
				if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
					setValues({[DISCREPANCY_INSTRUCTION]: 'BL'}, true);
				}
				enableFieldOnDemand('IF_SIGHT_BILL,DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION');
			}	else{
				if(loan_grp == 'ILCB'){
					if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
						if(product_code != 'T3S2' || product_code != 'I3S2'){
							setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
						} else{
							setValues({[DISCREPANCY_INSTRUCTION]: 'A'}, true);
						}
					}	}else{
						if(getValue(DISCREPANCY_INSTRUCTION) == '' || getValue(DISCREPANCY_INSTRUCTION) == null){
							setValues({[DISCREPANCY_INSTRUCTION]: 'AC'}, true);
						}
					}
				enableFieldOnDemand('IF_SIGHT_BILL');
				disableFieldOnDemand('DISCREPANCY_INSTRUCTION,SETTLEMENT_INSTRUCTION');
			} 
		}
	}
}

function ptCommonFieldsHandling(){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var loanGroup = getValue('IFS_LOAN_GRP');
	console.log('inside ptCommonFieldsHandling');
	if(reqType == 'ILCB_AC' ||reqType == 'ILCB_BS' ||reqType == 'IC_AC' ||reqType == 'IC_BS'|| reqType == 'ILCB_BCDR'){
		console.log('inside ptCommonFieldsHandling for request type');
		showSection('PT_COMMON');
		setStyle('BILL_STAGE', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('BILL_STAGE', PROPERTY_NAME.DISABLE, 'true');
		setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('CUST_REF_NO', PROPERTY_NAME.VISIBLE, 'false');
	} else if(reqType == 'TL_LD' && (loanGroup == 'ILCB' || loanGroup == 'IC')){
		showSection('PT_COMMON');
		setStyle('BILL_STAGE', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('BILL_STAGE', PROPERTY_NAME.DISABLE, 'true');
		setStyle('OTHER_AMENDMENT', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('CUST_REF_NO', PROPERTY_NAME.VISIBLE, 'false');
	}
}

function disableCustInstSection(){  //SP_US_149 -158
	if(getValue(PT_UTILITY_FLAG) == 'Y'){
		setStyle('CUST_INSTRUCTION_SECTION',PROPERTY_NAME.DISABLE,'true');
	}else{
		setStyle('CUST_INSTRUCTION_SECTION',PROPERTY_NAME.DISABLE,'true');
	}
}

function openCheckListTab(){
	var callRequestType = 'openRepairJSP';
	setTabStyle("tab1",2, "visible", "true");
	var urlDoc = document.URL;
	setStyle('SYSTEM_ACTIVITY_NAME', PROPERTY_NAME.VISIBLE, 'false');
	setStyle('REASON_FOR_ACTION', PROPERTY_NAME.VISIBLE, 'false');
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	document.getElementById('sheet3_link').textContent = 'Workitem Creation';
	//  var jspURL=sLocationOrigin+"/TFO/CustomFolder/FCUBS_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&callRequestType="+callRequestType;
	var jspURL=sLocationOrigin+"/TFO/CustomFolder/Repair_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')
	+"&callRequestType="+callRequestType;
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	disableFieldOnDemand(BUTTON_SUBMIT);
	selectSheet("tab1",2); 
}

function enableFieldELCB_AM_DISC(){  //PT_283-284
	req_type = getValue(REQUEST_TYPE);
	showSection('PT_COMMON');
	if(getValue(PT_UTILITY_FLAG) == 'Y'){
		if(req_type == 'ELCB_AM' || req_type == 'ELCB_DISC'){
			disableFieldOnDemand('PT_CUST_EMAIL_ID,INSTRUCTION_TYPE,ACCEPTANCE_MSG_DATE,DISCOUNTING_INSTRUCTIONS_1,DISCOUNTING_INSTRUCTIONS_2,SOURCE_CHANNEL');
			setStyle('PT_CUST_EMAIL_ID',PROPERTY_NAME.VISIBLE,'true');
			setStyle('INSTRUCTION_TYPE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('ACCEPTANCE_MSG_DATE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('DISCOUNTING_INSTRUCTIONS_1',PROPERTY_NAME.VISIBLE,'true');
			setStyle('DISCOUNTING_INSTRUCTIONS_2',PROPERTY_NAME.VISIBLE,'true');//discounting_inst_label
			setStyle('discounting_inst_label',PROPERTY_NAME.VISIBLE,'true'); 
		}
		if(req_type == 'EC_AM' || req_type == 'EC_DISC'){
			disableFieldOnDemand('PT_CUST_EMAIL_ID,INSTRUCTION_TYPE,ACCEPTANCE_MSG_DATE,OTHER_INSTRUCTION1,OTHER_INSTRUCTION2,OTHER_INSTRUCTION3,SOURCE_CHANNEL');
			setStyle('PT_CUST_EMAIL_ID',PROPERTY_NAME.VISIBLE,'true');
			setStyle('INSTRUCTION_TYPE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('ACCEPTANCE_MSG_DATE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('OTHER_INSTRUCTION1',PROPERTY_NAME.VISIBLE,'true');
			setStyle('OTHER_INSTRUCTION2',PROPERTY_NAME.VISIBLE,'true');
			setStyle('OTHER_INSTRUCTION3',PROPERTY_NAME.VISIBLE,'true'); 			
			setStyle('otherInstructionLabel',PROPERTY_NAME.VISIBLE,'true'); 
		}
	}else{
		if(req_type == 'ELCB_AM' ||req_type == 'ELCB_DISC'){
			setStyle('PT_CUST_EMAIL_ID',PROPERTY_NAME.VISIBLE,'true');
			setStyle('INSTRUCTION_TYPE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('ACCEPTANCE_MSG_DATE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('DISCOUNTING_INSTRUCTIONS_1',PROPERTY_NAME.VISIBLE,'true');
			setStyle('DISCOUNTING_INSTRUCTIONS_2',PROPERTY_NAME.VISIBLE,'true');
			setStyle('discounting_inst_label',PROPERTY_NAME.VISIBLE,'true');
			enableFieldOnDemand('PT_CUST_EMAIL_ID,INSTRUCTION_TYPE,ACCEPTANCE_MSG_DATE,DISCOUNTING_INSTRUCTIONS_1,DISCOUNTING_INSTRUCTIONS_2');
		}
		if(req_type == 'EC_AM' ||req_type == 'EC_DISC'){
			setStyle('PT_CUST_EMAIL_ID',PROPERTY_NAME.VISIBLE,'true');
			setStyle('INSTRUCTION_TYPE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('ACCEPTANCE_MSG_DATE',PROPERTY_NAME.VISIBLE,'true');
			setStyle('OTHER_INSTRUCTION1',PROPERTY_NAME.VISIBLE,'true');
			setStyle('OTHER_INSTRUCTION2',PROPERTY_NAME.VISIBLE,'true'); 
			setStyle('OTHER_INSTRUCTION3',PROPERTY_NAME.VISIBLE,'true');
			setStyle('otherInstructionLabel',PROPERTY_NAME.VISIBLE,'true');
			enableFieldOnDemand('PT_CUST_EMAIL_ID,INSTRUCTION_TYPE,ACCEPTANCE_MSG_DATE,OTHER_INSTRUCTION1,OTHER_INSTRUCTION2,OTHER_INSTRUCTION3');
		}
	} 
}

function ELCBFields(){
	var productCode = getValue('PRODUCT_TYPE');
	var reqType = getValue(REQUEST_TYPE);
	var activity = getWorkItemData('activityName');
	if(reqType == 'ELCB_AC' || reqType == 'ELCB_BL'){
		if(productCode == 'T3U3' || productCode == 'T3U6' || productCode == 'T3U7' || productCode == 'I3U3'){
			setStyle('DISCOUNT_ON_ACCEP','visible','true');
			if(activity == 'PROCESSING CHECKER'){
				setStyle('DISCOUNT_ON_ACCEP','disable','true');
			}
		}
	}
}

function nonMandateControls(arr){
	for(var i=0; i<arr.length; i++){
		console.log('set non mandatory control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.MANDATORY, 'false');
	}
}

function mandateControls(arr){
	for(var i=0; i<arr.length; i++){
		console.log('set mandatory control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.MANDATORY, 'true');
	}
}

function customerRefFieldEnableDisable(){
	var workstepName = getWorkItemData('activityName');
	setTabStyle("tab1",0, "disable", "true");
	setTabStyle("tab1",1, "disable", "true");
	setTabStyle("tab1",2, "disable", "true");
	setTabStyle("tab1",3, "disable", "true");
	setTabStyle("tab1",4, "disable", "true");
	setTabStyle("tab1",5, "disable", "true");
	setTabStyle("tab1",6, "disable", "true");
	setTabStyle("tab1",7, "disable", "true");
	if(workstepName == WORKSTEP.CUSTOMER_REFERRAL_RESPONSE){
		setStyle('Qvar_Final_Desc_PPM', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('Qvar_Final_Desc_PPM', PROPERTY_NAME.DISABLE, 'true');
		setStyle('ListViewReferral', PROPERTY_NAME.VISIBLE, 'true');
		setStyle(COMBOX_REJ_RESN, PROPERTY_NAME.VISIBLE, 'false');
		setStyle(ROUTE_TO_PPM, PROPERTY_NAME.VISIBLE, 'false');
		setStyle(COMBOX_REF_ICG_RM, PROPERTY_NAME.VISIBLE, 'false');
		setStyle('FRM_DUPE', PROPERTY_NAME.VISIBLE, 'false');
		setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
	}else{
		setTabStyle("tab1",8, "disable", "true");
		setTabStyle("tab1",1, "visible", "true");
	}
}

function fieldsEnableDisableTSLM(){
	var requestCat = getValue(REQUEST_CATEGORY);
	if('IFP' == requestCat || 'IFS' == requestCat || 'IFA' == requestCat || 'IFCPC' == requestCat){
		showControls(PROCESSING_SYSTEM);
		if(getValue(PROCESSING_SYSTEM)  == ''){
			setValues({[PROCESSING_SYSTEM]: 'F'}, true); //20th Nov
		}
	}
	var requestType = getValue(REQUEST_TYPE);
	if(requestType =='AM'|| requestType =='STL'|| requestType =='IFP_SD'|| requestType =='IFA_CTP' ){
		disableFieldOnDemand(PROCESSING_SYSTEM);
	}else{
		enableFieldOnDemand(PROCESSING_SYSTEM);
	}
}

function selectchecboxTSLM(){  //BY KISHAN TSLM
	var array = [];
	var counter=0;
	var length = getGridRowCount('Q_TSLm_Counter_Dets');
	for(var i =0; i<length; i++){
		 var value = getValueFromTableCell('Q_TSLm_Counter_Dets',i,4);
		 if(value == 'true'){
			 array[counter] = i;
			 counter=counter+1;
		 }	
	}
	setRowSelectionInListView('Q_TSLm_Counter_Dets',array);
}
//added by mansi
function setGrntInitiatorDeafultValueStandBy(controlName, requestCat, requestType){
	console.log('inside setGrntInitiatorDeafultValueStandBy >>> ');
	var workstepName = getWorkItemData('activityName');
	var processType = getValue('PROCESS_TYPE');
	console.log('process type: '+processType);
	setStyle(REQUESTED_BY, PROPERTY_NAME.DISABLE, 'false');
	if('SWIFT' == processType){
		setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
		console.log('source channel set as ISA..');
		if('SBLC_MSM' == requestType ||  'SBLC_ER' == requestType || 'SBLC_CR' == requestType || 'SBLC_CS' == requestType)
		{	console.log('setting REQUESTED_BY ....');
		setValues({[REQUESTED_BY]: 'SBLC_CB'}, true);
		console.log('value set as SBLC_CB');
		}else if('SBLC_NI' == requestType || 'SBLC_AM' == requestType )
		{
			setValues({[REQUESTED_BY]: 'SBLC_T'}, true);
		}else if('' == requestType)
		{	console.log('setting REQUESTED_BY null ...');
		setValues({[REQUESTED_BY]: ''}, true);
		}
	} else if ('SBLC_NI' == requestType ||'SBLC_AM' == requestType) {
		setValues({[REQUESTED_BY]: 'SBLC_A'}, true);
		setDefaultValue(workstepName);
	} else if ('SBLC_CR' == requestType || 'SBLC_ER' == requestType || 'SBLC_CS' == requestType) {
	setValues({[REQUESTED_BY]: 'SBLC_B'}, true);
	setDefaultValue(workstepName);
	} else if ('' == requestType || '0' == requestType) {
		console.log('setting REQUESTED_BY null ...');
		setValues({[REQUESTED_BY]: ''}, true);
		setDefaultValue(workstepName);
	}
	else if('SBLC_SD' == requestType){
		setValues({[REQUESTED_BY]: 'SBLC_AT'}, true);
		setValues({[SOURCE_CHANNEL]: 'B'}, true);
	}
 }
//added by mansi
function setELCInitiatorDeafultValueStandBy(controlName, requestCat, requestType){
	console.log('inside setELCInitiatorDeafultValueStandBy >>> ');
	var workstepName = getWorkItemData('activityName');
	var processType = getValue('PROCESS_TYPE');
	console.log('process type: '+processType);
	setStyle(REQUESTED_BY, PROPERTY_NAME.DISABLE, 'false');
	if('SWIFT' == processType){
		setValues({[SOURCE_CHANNEL]: 'ISA'}, true);
		console.log('source channel set as ISA..');
		if('ELC_SER' == requestType || 'ELC_SCL' == requestType)
		{	console.log('setting REQUESTED_BY ....');
		setValues({[REQUESTED_BY]: 'ELC_SI'}, true);
		console.log('value set as ELC_CB');
		}else if('ELC_SLCA' == requestType || 'ELC_SLCAA' == requestType )
		{
			setValues({[REQUESTED_BY]: 'ELC_SI'}, true);
		}else if('' == requestType)
		{	console.log('setting REQUESTED_BY null ...');
		setValues({[REQUESTED_BY]: ''}, true);
		}
	} else if ('ELC_SLCA' == requestType ||'ELC_SLCAA' == requestType) {
		setValues({[REQUESTED_BY]: 'ELC_SI'}, true);
		setDefaultValue(workstepName);
	} else if ('ELC_SCL' == requestType || 'ELC_SER' == requestType) {
	setValues({[REQUESTED_BY]: 'ELC_SI'}, true);
	setDefaultValue(workstepName);
	} else if ('' == requestType || '0' == requestType) {
		console.log('setting REQUESTED_BY null ...');
		setValues({[REQUESTED_BY]: ''}, true);
		setDefaultValue(workstepName);
	}
	
 }
function setGRNTDefaultScrChannelStandBy(workstepName){
	console.log("inside setGRNTDefaultScrChannelStandBy  ");
	var requestType = getValue(REQUEST_TYPE);
	var sourceChannel = getValue(SOURCE_CHANNEL);
	var processType = getValue(PROCESS_TYPE);
	if (workstepName == WORKSTEP.INITIATOR) {			
		console.log (" Request Type > " +requestType);			
		if('' == requestType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}
		else{
			setValues({[SOURCE_CHANNEL]: 'B'}, true);
		}
	}else if (workstepName == WORKSTEP.PP_MAKER) {			
		 if("SBLC_NI"==requestType && ("S"==sourceChannel || "SWIFT" == processType)) {
			setEmptyCombo(COMBOX_TRN_THIRD_PARTY, "2");
		}else if("SBLC_NI"==requestType){
			//setValues({[TRN_THIRD_PARTY]: ''}, true);
			setEmptyCombo(IS_ACC_VALID, "2");
			//setEmptyCombo(COMBOX_SUFF_BAL_AVL, "3");
		}
	
}
}
function setELCDefaultScrChannelStandBy(workstepName){
	console.log("inside setELCDefaultScrChannelStandBy  ");
	var requestType = getValue(REQUEST_TYPE);
	var sourceChannel = getValue(SOURCE_CHANNEL);
	if (workstepName == WORKSTEP.INITIATOR) {			
		console.log (" Request Type > " +requestType);			
		if('' == requestType){
			setValues({[SOURCE_CHANNEL]: ''}, true);
		}
		else{
			setValues({[SOURCE_CHANNEL]: 'ELC_B'}, true);
		}
	}	
}
function disableFieldsGRNT_CC_ER_CL_EPC()  //added by Rakshita
{
	var processType=getValue(PROCESS_TYPE);
	var reqCat=getValue(REQUEST_CATEGORY);
	var reqType=getValue(REQUEST_TYPE);
	
	if(processType == 'BAU' || processType == 'SWIFT')
	{
		if(reqType =='CC'){
			disableFieldOnDemand(GRNT_CC_ER_CL_EPC_DISABLE);
		}
		
		else if(reqType =='CL'||reqType =='SBLC_CR' || reqType =='SBLC_CS' || reqType =='ELC_SCL' ||
				reqType =='ER' || reqType =='SBLC_ER' || reqType =='ELC_SER'|| reqType =='EPC'){
			disableFieldOnDemand(GRNT_CC_ER_CL_EPC_DISABLE);
			disableFieldOnDemand(FIELD_GRNTEE_EXP_DATE);
			
		}
	}
}
		
function setComboFieldsGRNT_CC_ER_CL_EPC() //added by Rakshita
{
	var processType=getValue(PROCESS_TYPE);
	var reqType=getValue(REQUEST_TYPE);
	
	if(processType == 'BAU' || processType == 'SWIFT')
	{
		if(reqType =='CC'){
			setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
			setValues({[COMBOX_TXT_FORMAT]: '2'}, true);
			setValues({[COMBOX_TXT_REQ_APP]: '3'}, true);
			setValues({[COMBOX_LMTCRE_APP_AVL]: '3'}, true);
		}
		
		else if(reqType =='CL' || reqType =='SBLC_CR' || reqType =='SBLC_CS' || reqType =='ELC_SCL'
			|| reqType =='ER' || reqType =='SBLC_ER' || reqType =='ELC_SER' 
			|| reqType =='EPC'){
			setValues({[COMBOX_REQ_SIGN_MAN]: '3'}, true);
			setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
			setValues({[COMBOX_TXT_FORMAT]: '2'}, true);
			setValues({[COMBOX_TXT_REQ_APP]: '3'}, true);
			setValues({[COMBOX_LMTCRE_APP_AVL]: '3'}, true);
			
		}
	}
}

function validateProcessingSystemFieldValue(){
	var requestCat = getValue(REQUEST_CATEGORY);
	var controlValue = getValue(PROCESSING_SYSTEM);
	if(requestCat == 'IFA' || requestCat == 'IFS' ||requestCat == 'IFP'){
		if(controlValue == '' || controlValue == 'undefined'){
			showMessage(PROCESSING_SYSTEM, 'Please select Processing System.', 'error');
			return false;
		}else{
			return true;
		}
	}else return true;
}

function ProcessingSystemAfterFetch(){
	var controlValue = getValue(PROCESSING_SYSTEM);
	var requestCat = getValue(REQUEST_CATEGORY);
	var requestType = getValue(REQUEST_TYPE);
	if(requestCat == 'IFA' || requestCat == 'IFS' || requestCat == 'IFP'){
		 if(requestType == 'AM' || requestType == 'STL' || requestType == 'IFS_SD' 
			 || requestType == 'IFA_CTP' || requestType == 'IFA_SD' || requestType == 'IFP_SD' ){
			if(controlValue == '' || controlValue == 'undefined'){
				enableFieldOnDemand(PROCESSING_SYSTEM);
				}
			    else{
				disableFieldOnDemand(PROCESSING_SYSTEM);
			     }
		}
	}
}

function showInvoiceDetails(){
	var controlValue = getValue(PROCESSING_SYSTEM);
	var requestCat = getValue(REQUEST_CATEGORY);
	var requestType = getValue(REQUEST_TYPE);
	if(requestCat == 'IFA' || requestCat == 'IFS' || requestCat == 'IFP'){
		if('LD' == requestType || 'IFA_CTP' == requestType){
			setTabStyle("tab1",11, "visible", "true");
		}	
	}
}

function reqCatDupCheckFilter(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	console.log('inside reqCatDupCheckFilter>>');
	if(('GRNT' == requestCategory && 'NI' == requestType)
			||(('IFS' == requestCategory || 'IFP' == requestCategory || 'IFA' == requestCategory)&& ('LD' == requestType)) //added by MOKSH
			||('IC' == requestCategory && ('IC_BL' == requestType||'IC_BS' == requestType))
			||('SBLC' == requestCategory && 'SBLC_NI' == requestType)	//RR
			||('ELC' == requestCategory && 'ELC_SLCA' == requestType)	//RR
			||('DBA' == requestCategory && ('DBA_BL' == requestType||'DBA_STL' == requestType))
			||('EC' == requestCategory && ('EC_BL' == requestType||'EC_BS' == requestType || 'EC_DISC' == requestType||'EC_LDDI' == requestType))
			||('ELCB' == requestCategory &&('ELCB_BL' == requestType||'ELCB_BS' == requestType||'ELCB_DISC' == requestType))
			||('ILCB' == requestCategory && ('ILCB_BL' == requestType||'ILCB_BS' == requestType))
			||('ILC' == requestCategory && ('ILC_NI' == requestType || 'ILC_UM' == requestType))
			||('TL' == requestCategory && 'TL_LD' == requestType)
			||('SG' == requestCategory && ('SG_NILC' == requestType || 'SG_NIC' == requestType)) //Shipping_gtee_51
	){
		console.log('>>>' +requestCategory);
		handleDeDupFrame();
	}else{
		disableFieldOnDemand(DUP_CHK_CONFIRMATION+','+BTN_DUPE);
	}
}

function disableFetchField (){
	
		 setValue('CUSTOMER_ID', '');
		 setValue('CUSTOMER_NAME', '');
		 setValue('TRANSACTION_REFERENCE', '');
		 setValue('TRANSACTION_UNB_REFERENCE', '');
		 setValue('TRANSACTION_ADCB_LC_REFERENCE', '');
		 setValue('TRANSACTION_UNB_LC_REFERENCE', '');
		 setValue('PT_CUSTOMER_IC', '');

	 
		 
}

//subhashree,Ayush, munna
function initiatorhybridcombo(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	console.log("inside initiatorhybridcombo >>>" );
		if('TSLM Front End'== getValue(PROCESS_TYPE) && ('IFCPC' != requestCategory)){
		setStyle('Hybrid_Customer', PROPERTY_NAME.VISIBLE, 'true');
		if(getValue('Hybrid_Customer')=='No'){
			console.log('inside if No- hybridpopulate >>>');
			setValue('RELATIONSHIP_TYPE', 'C');
			setStyle('RELATIONSHIP_TYPE','disable', 'true');
			setStyle('PROCESS_TYPE', 'disable', 'false');
			setStyle('REQUEST_CATEGORY','disable', 'true');
			setStyle('REQUEST_TYPE','disable', 'true');
			setStyle('REQUESTED_BY', 'disable', 'true');
			setStyle('PROCESSING_SYSTEM','disable', 'true');
			setStyle('CID_Txt','disable', 'true');
			setStyle('TRANSACTION_CURRENCY', 'disable', 'true');
			setStyle('TRANSACTION_AMOUNT','disable','true');
			setStyle('RELATIONSHIP_TYPE','disable','true');
			setStyle('BRANCH_CITY','disable','true');
			setStyle('ASSIGNED_CENTER','disable','true');
			setStyle('PT_ASSIGNED_MAKER','disable','true');
			setStyle('TrnsRefNo','disable', 'true');
		}else if(getValue('Hybrid_Customer')=='Yes'){
			console.log(' inside if - Yes - checkhybridcust >>>' );
		//	addItemInCombo('REQUESTED_BY', 'Customer');
			setStyle('REQUESTED_BY', 'disable', 'false');
			setValue('REQUESTED_BY', 'Customer');
			setValue('RELATIONSHIP_TYPE', 'C');
			setStyle('RELATIONSHIP_TYPE','disable', 'false');
			setStyle('TrnsRefNo','disable', 'false');
			setStyle('CID_Txt', 'disable', 'false');
			setStyle('PROCESS_TYPE', 'disable', 'false');
			setStyle('REQUEST_CATEGORY', 'disable', 'false');
			setStyle('REQUEST_TYPE', 'disable', 'false');
			setStyle('TRANSACTION_CURRENCY', 'disable', 'false');
			setStyle('TRANSACTION_AMOUNT','disable','false');
			setStyle('RELATIONSHIP_TYPE','disable','false');
			setStyle('BRANCH_CITY','disable','false');
			setStyle('ASSIGNED_CENTER','disable','false');
			setStyle('PT_ASSIGNED_MAKER','disable','false');
		}
		}else{
			setStyle('Hybrid_Customer', PROPERTY_NAME.VISIBLE, 'false');
			setValue('Hybrid_Customer','');
		}
	}

function disableFieldForIFCPC(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	console.log('inside disableFieldForIFCPC>>');
	if('TSLM Front End'== getValue(PROCESS_TYPE) && requestCategory == 'IFCPC'){
		disableFieldOnDemand(PROCESSING_SYSTEM);
		setStyle('RELATIONSHIP_TYPE','disable','true');
		setStyle('SOURCE_CHANNEL','disable','true');
		
		
	}
}
function renameLimitTab(){
	var requestCategory = getValue(REQUEST_CATEGORY);
	if('IFCPC' == requestCategory){
			document.getElementById('sheet8_link').innerHTML='Approval required for Counter Party';
			document.getElementById('LMTCRE_APP_AVL_PPM_label').innerHTML='Any Approval Required for Counter Party Creation';
			}
}
//ATP -191  Date 25 -10-2023 ADDED BY REYAZ
function setscfInitiatorDeafultValue(controlName, requestCat, reqType){
	console.log("setIfpInitiatorDeafultValue controlName "+controlName +"\n reqType:"+ reqType);
	var workstepName = getWorkItemData('activityName');
	enableFieldOnDemand(controlName);
	if('PD' == reqType || 'PDD' == reqType) {
		setValues({[controlName]: 'CT'}, true);
		setDefaultValue(workstepName);
		disableFieldOnDemand(controlName);
	}
}

//ATP -191  Date 25 -10-2023 ADDED BY REYAZ
function scfFieldEnableDisable(reqType){
	if ('PD' == reqType || 'PDD' == reqType) {
		setFieldEnableDisableForIssue();
	} else {
		setFieldEnableDisableAtIntro();
	}
}
//ATP-151 TO 157 ADDED by KRISHNA PANDEY
function renameInputDetails(){
	var requestCategory = getValue(REQUEST_CATEGORY);
	var reqType = getValue('REQUEST_TYPE');
    var processType = getValue('PROCESS_TYPE');	
	setStyle('LOAN_DETAILS', PROPERTY_NAME.VISIBLE, 'false');
	if(('IFA' == requestCategory||'IFS' == requestCategory ||'IFP' == requestCategory || 'TL'== requestCategory) &&
	   ('AM' == reqType || 'STL' == reqType)){
			document.getElementById('INF_CHARGE_ACC_NUM_label').innerHTML='Principal Account Number';
			document.getElementById('INF_CHARGE_ACC_TITLE_label').innerHTML='Principal Account Title';
			document.getElementById('INF_CHARGE_ACC_CURR_label').innerHTML='Principal Account Currency';
			document.getElementById('INF_SETTLEMENT_ACC_NUM_label').innerHTML='Interest/Profit Account Number';
			document.getElementById('INF_SETTLEMENT_ACC_TITLE_label').innerHTML='Interest/Profit Account Title';
			document.getElementById('INF_SETTLEMENT_ACC_CURR_label').innerHTML='Interest/Profit Account Currency';
	}if((processType == 'TSLM Front End') && ('AM' == reqType || 'STL' == reqType)){
	        setStyle('LOAN_DETAILS', PROPERTY_NAME.VISIBLE, 'true');
	        disableFieldOnDemand(PPM_STL_TSLM_FRONTEND_DISABLE); //ATP-361 DATE -08-01-2024  REYAZ
	 if('AM' == reqType){
		    setStyle('REASON_LOAN','VISIBLE','true');
		setStyle('TRANS_AMT','VISIBLE','false');	
	}if('STL' == reqType){
		   setStyle('REASON_LOAN','VISIBLE','false');
		   setStyle('TRANS_AMT','VISIBLE','true');	
	}
	}
}

// ATP-454 REYAZ 10-05-2024 
// START CODE 
function validateTSLMNewMaturityDate(){
	var currentDate =new Date();
	var newMaturityDate =getValue('INF_NEW_MATURITY_DATE');
	if(newMaturityDate == ''){
		return false;
	} else{
		var parts =newMaturityDate.split('/');
		newMaturityDate =new Date(parts[1]+'/'+parts[0]+'/'+parts[2]);
		console.log("newMaturityDate "+newMaturityDate);
		console.log("currentDate "+currentDate);
		return newMaturityDate > currentDate;
	}
}
// END CODE ATP-454 REYAZ 10-05-2024

//ATP-458 REYAZ 05-07-2024  START
function handleSwiftMtUI(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	var workstepName = getWorkItemData('activityName');
	var swiftChannel=getValue('SWIFT_CHANNEL');
	if(swiftChannel == 'MT798' && requestCategory=='ILC' && requestType=='ILC_NI'){
		setDecodeMT789Field();
		if(workstepName == WORKSTEP.INITIATOR){
			showControls(INI_ILC_NI_SWIFT_MT798_SHOW);
			setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
            setValues({['LC_TOTAL_EXPOSURE']: getValue('TRANSACTION_AMOUNT')}, true);
		}
		else if(workstepName == WORKSTEP.PP_MAKER){
			    showControls(PPM_ILC_NI_SWIFT_MT798_SHOW);
				disableFieldOnDemand(PPM_ILC_NI_SWIFT_MT798_DISABLE);
		//		setValues({['AVL_WITH_D']: getValue('CREDIT_MODE')}, true);
				setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
				setValues({[ANY_DOC_COURRIER]: '2'}, true);
				setValue('IS_100PCT_CASHBACK','false');
		}
		else if(workstepName == WORKSTEP.PROCESSING_MAKER){
			  showControls(PM_ILC_NI_SWIFT_MT798_SHOW);
			  disableFieldOnDemand(PM_ILC_NI_SWIFT_MT798_DISABLE);
			  hideControls(PM_ILC_NI_SWIFT_MT798_HIDE);
	//		  setValues({['AVL_WITH_D']: getValue('CREDIT_MODE')}, true);
			  setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
			  document.getElementById('LC_LTST_SHIPMNT_DATE_label').innerHTML='Latest Shipment Date(44C)';
			  document.getElementById('LC_GOODS_DESC_label').innerHTML='Goods Description (45A)';
		}
	} else if(swiftChannel == 'MT798' && (requestCategory=='GRNT' || requestCategory=='SBLC') && (requestType=='NI' || requestType=='GA' || requestType=='SBLC_NI')){
		if(workstepName == WORKSTEP.INITIATOR){
			showControls(INI_GRNT_NI_SWIFT_MT798_SHOW);
			disableFieldOnDemand(INI_GRNT_NI_SWIFT_MT798_DISABLE);
			setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
		}
		else if(workstepName == WORKSTEP.PP_MAKER){
			    showControls(PPM_GRNT_NI_SWIFT_MT798_SHOW);
				disableFieldOnDemand(PPM_GRNT_NI_SWIFT_MT798_DISABLE);
				setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
				setValues({[ANY_DOC_COURRIER]: '2'}, true);
				setValue('IS_100PCT_CASHBACK','false');
		}
		else if(workstepName == WORKSTEP.PROCESSING_MAKER){
			  showControls(PM_GRNT_NI_SWIFT_MT798_SHOW);
			  disableFieldOnDemand(PM_GRNT_NI_SWIFT_MT798_DISABLE);
			 // hideControls(PM_GRNT_NI_SWIFT_MT798_HIDE);
			  setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
		}
	}
	//ATP-490 --24-07-2024 --Jamshed START
	else if(swiftChannel == 'MT798' && requestCategory=='ILC' && requestType=='ILC_AM'){
		setDecodeMT789Field();
		showControls(PM_SWIFT_MT798_LABEL_SHOW);
		document.getElementById('BANK_CHG_PBY_label').innerHTML='Amendment Bank Charges Payable By (71A)';
		if(workstepName == WORKSTEP.INITIATOR){
			showControls(INI_ILC_AM_SWIFT_MT798_SHOW);
			setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
            setValues({['LC_TOTAL_EXPOSURE']: getValue('TRANSACTION_AMOUNT')}, true);
		}
		else if(workstepName == WORKSTEP.PP_MAKER){
			    showControls(PPM_ILC_AM_SWIFT_MT798_SHOW);
				disableFieldOnDemand(PPM_ILC_AM_SWIFT_MT798_DISABLE);
				hideControls(PPM_ILC_AM_SWIFT_MT798_HIDE);
		//		setValues({['AVL_WITH_D']: getValue('CREDIT_MODE')}, true);
				setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
				setValues({[ANY_DOC_COURRIER]: '2'}, true);
				setValue('IS_100PCT_CASHBACK','false');
				document.getElementById('label121').innerHTML='PROTRADE / INCOMING SWIFT';
		}
		else if(workstepName == WORKSTEP.PROCESSING_MAKER){
			  showControls(PM_ILC_AM_SWIFT_MT798_SHOW);
			  disableFieldOnDemand(PM_ILC_AM_SWIFT_MT798_DISABLE);
			  hideControls(PM_ILC_AM_SWIFT_MT798_HIDE);
	//		  setValues({['AVL_WITH_D']: getValue('CREDIT_MODE')}, true);
			  setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
			  document.getElementById('LC_LTST_SHIPMNT_DATE_label').innerHTML='Latest Shipment Date(44C)';
			  document.getElementById('LC_GOODS_DESC_label').innerHTML='Goods Description (45A)';
			  document.getElementById('label511').innerHTML='PROTRADE / INCOMING SWIFT';
		}
	}
	//ATP-490 --24-07-2024 --Jamshed END
	
	//ATP-490 --25-07-2024 --Shivanshu START
	else if(swiftChannel == 'MT798' && requestCategory=='GRNT' && requestType=='AM'){
		setDecodeMT789Field();
		showControls(PM_SWIFT_MT798_LABEL_SHOW);
		document.getElementById('TXN_REF_NO_LC_label').innerHTML='Undertaking Number(20)';
		if(workstepName == WORKSTEP.INITIATOR){
			showControls(INI_GRNT_AM_SWIFT_MT798_SHOW);
			setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
            setValues({['LC_TOTAL_EXPOSURE']: getValue('TRANSACTION_AMOUNT')}, true);
		}
		else if(workstepName == WORKSTEP.PP_MAKER){
			    showControls(PPM_GRNT_AM_SWIFT_MT798_SHOW);
				disableFieldOnDemand(PPM_GRNT_AM_SWIFT_MT798_DISABLE);
				setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
				setValues({[ANY_DOC_COURRIER]: '2'}, true);
				setValue('IS_100PCT_CASHBACK','false');
				document.getElementById('label121').innerHTML='PROTRADE / INCOMING SWIFT';
		}
		else if(workstepName == WORKSTEP.PROCESSING_MAKER){
			  showControls(PM_GRNT_AM_SWIFT_MT798_SHOW);
			  disableFieldOnDemand(PM_GRNT_AM_SWIFT_MT798_DISABLE);
			  hideControls(PM_GRNT_AM_SWIFT_MT798_HIDE);
			  setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
			  document.getElementById('LC_LTST_SHIPMNT_DATE_label').innerHTML='Latest Shipment Date(44C)';
			  document.getElementById('LC_GOODS_DESC_label').innerHTML='Goods Description (45A)';
			  document.getElementById('label511').innerHTML='PROTRADE / INCOMING SWIFT';
		}
	}
	//ATP-490 --25-07-2024 --Shivanshu END
	
	//ATP-504 JAMSHED 09-08-2024 START
	else if(swiftChannel == 'MT798' && requestCategory=='MISC' && requestType=='MISC_MSM'){
		setDecodeMT789Field();
		showControls(PM_SWIFT_MT798_LABEL_SHOW);
		document.getElementById('TXN_REF_NO_LC_label').innerHTML='Undertaking Number(20)';
		if(workstepName == WORKSTEP.INITIATOR){
			showControls(INI_MISC_MSM_SWIFT_MT798_SHOW);
			disableFieldOnDemand(INI_MISC_MSM_SWIFT_MT798_DISABLE);
			setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
            setValues({['LC_TOTAL_EXPOSURE']: getValue('TRANSACTION_AMOUNT')}, true);
		}
		else if(workstepName == WORKSTEP.PP_MAKER){
			    showControls(PPM_MISC_MSM_SWIFT_MT798_SHOW);
				disableFieldOnDemand(PPM_MISC_MSM_SWIFT_MT798_DISABLE);
				setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
				setValues({[ANY_DOC_COURRIER]: '2'}, true);
				setValue('IS_100PCT_CASHBACK','false');
				document.getElementById('label121').innerHTML='PROTRADE / INCOMING SWIFT';
		}
		else if(workstepName == WORKSTEP.PROCESSING_MAKER){
			  showControls(PM_MISC_MSM_SWIFT_MT798_SHOW);
			  disableFieldOnDemand(PM_MISC_MSM_SWIFT_MT798_DISABLE);
			  //hideControls(PM_GRNT_AM_SWIFT_MT798_HIDE);
			  setValues({['TXN_REF_NO']: getValue('SWIFT_FIELD_21')}, true);
			  document.getElementById('LC_LTST_SHIPMNT_DATE_label').innerHTML='Latest Shipment Date(44C)';
			  document.getElementById('LC_GOODS_DESC_label').innerHTML='Goods Description (45A)';
			  document.getElementById('label511').innerHTML='PROTRADE / INCOMING SWIFT';
		}
	}
	//ATP-504 JAMSHED 09-08-2024 END
}
//ATP-458 REYAZ 05-07-2024  END

//ATP-458 SHIVANSHU 16-07-2024  START
function setDecodeMT789Field(){
	var creditMode = getValue('CREDIT_MODE')
	var avlDetail = '';
	if (creditMode == 'N'){
		avlDetail = 'Negotiation';
	}else if (creditMode == 'A'){
		avlDetail = 'Available';
	}else if(creditMode == 'M'){
		avlDetail = 'Mixed Payment';
	}else if(creditMode == 'D'){
		avlDetail = 'Deferred Payment';
	}else if(creditMode == 'P'){
		avlDetail = 'Payment';
	}
	setValues({['AVL_WITH_D']: avlDetail}, true);

}
//ATP-458 SHivanshu 16-07-2024  END

//SCFMVP2.5 SHAHBAZ 25-07-2024  START
function validateTslmFrontedRequestCat(){
	var requestCategory = getValue(REQUEST_CATEGORY);
	var reqType = getValue('REQUEST_TYPE');
    var processType = getValue('PROCESS_TYPE');	
	if(('IFA' == requestCategory||'IFS' == requestCategory ||'IFP' == requestCategory || 'TL'== requestCategory || 'SCF'== requestCategory) &&
	   ('AM' == reqType || 'STL' == reqType || 'LD' == reqType || 'PD' == reqType|| 'PDD' == reqType)){
	   return true;
	}
	 return false;
}
//SCFMVP2.5 SHAHBAZ 25-07-2024  END

//SCFMVP2.5 REYAZ 30-07-2024  START
function setDefaultforHybridCustomer(){
	var requestCategory = getValue(REQUEST_CATEGORY);
	var reqType = getValue('REQUEST_TYPE');
    var processType = getValue('PROCESS_TYPE');	
	var hybridCustomer = getValue('Hybrid_Customer');	
	if('Yes' == hybridCustomer){
		setValues({['CID_Txt']:  getValue(CUSTOMER_ID)}, true);
	    disableFieldOnDemand('CID_Txt');
	}

}
//SCFMVP2.5 REYAZ 30-07-2024  END
