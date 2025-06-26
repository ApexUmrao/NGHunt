var counter=1;
var working=0;
var lastcounter=0;
var startcounter=0;
var optionallastfail=0;
var startUTCFlag = true;


function onPMFormLoad(){

	console.log('account number: '+getValue(ACCOUNT_NUMBER));
	hideControls('COMP_IMB_CHK_2,COMP_IMB_CHK_3');
	amendTypeHandling();
	setFrmLabelName();
	finalDecisionCheckListHandling(); 
	hidePMPCSWIFTFrame();
	inputDetailShowHideSection();
	reqCatDupCheckFilter();
	setStyle(PD_PARTY_ID,'disable','true');
	lockTFOSection('FRM_VERIFY_DETAIL,PT_PROTRADE,PT_Document_Detail,PT_CUSTOMER_INS,FRM_LLI,PM_REF_FRAME,GTEE_FRAME,GRNT_FRM_NEW,PM_Decision_Frame,IELC_MKR_INPUT_FRM,LC_FRAME,ILC_Controls,FRM_DUPE,BILL_FRAME,'
			+'IF_FRAME,IFS_Final_Dec_frame,PC_ELC_Controls,frame5,frame7,ELC_Controls,frame23,frame24,IF_FRAME1,DEAL_DETAILS_FRAME,frame20,'
			+'FRM_TR_DTLS,TREASURY_FRAME,PC_ILC_Controls,FRM_GRTEE_LC_COMMON,FRM_PARTIES_DETAILS,frm_contract_limits,EC_Controls,ILCB_Controls,Common_Fields,PC_ILCB_Controls,PC_ELCB_Controls,ELCB_Controls,PT_COMMON,PT_GTEE_FRAME,PT_FFT_FRAME,PT_ILC_FRAME');
	setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	setTabStyle("tab1",8, "visible", "false");
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType  || 'PD' == reqType || 'PDD' == reqType)) {  //ATP - 204,205 Added by shivanshu
		setTabStyle("tab1",8, "visible", "true");
		var utcRequired = getValue('UTC_REQUIRED');
		var utcRequiredBRMS = getValue('UTC_REQUIRED_BRMS');
		setStyle('UTC_REQUIRED_BRMS', PROPERTY_NAME.VISIBLE, 'true');  // Added by reyaz 5082022
		setStyle('UTC_CONVERTED_AMOUNT', PROPERTY_NAME.VISIBLE, 'true'); // Added by reyaz 5082022
		setStyle('UTC_REQUIRED', PROPERTY_NAME.VISIBLE, 'true');  // Added by reyaz 5082022
		setStyle('UTC_JSTIFICATION_REQUIRED', PROPERTY_NAME.VISIBLE, 'true'); // Added by reyaz 5082022
		if(getValue('HIDDEN_START_FLAG') == 'true') {
			disableFieldOnDemand('UTC_START_CHECK');
//			if(getValue('UTC_REQUIRED') == 'Yes') {
//			//setValue('IS_GETDOCUMENT_UTC_DONE','N');
//			}
		}if(utcRequiredBRMS == 'Yes' && utcRequired == ''){
			setValue('UTC_REQUIRED','Yes');
		}if(utcRequiredBRMS == 'No'  && utcRequired == ''){
			setValue('UTC_REQUIRED','No');
		}
		//ATP-481 --JAMSHED --11-JUN-2024 START
		/*if(('IFA' == reqCat) && ('LD' == reqType)){
			setValue('UTC_REQUIRED','No');
			setValue('UTC_JSTIFICATION_REQUIRED','UTC Not Applicable for IFA Disbursement');
		}*/
		//ATP-481 --JAMSHED --11-JUN-2024 END
	}
	disableFieldOnDemand(BUTTON_SUBMIT);
	disableFieldOnDemand(BUTTON_MODIFY_CONTRACT_DETAILS);
	setDecision();
	deleteNACreateAmend(CREATE_AMEND_CNTRCT_FCUBS);
	disablePTFields();
	
	ecFramePT();
	enablePTFieldsAtPCPM();
	ELCBFields(); //PT 138 135
	disableForILC_AM();
	enableCustInstSection();
	ptCommonFieldsHandling(); //US 159
	disableCustInstSection();
	disableFieldsGRNT_CC_ER_CL_EPC();//added by Rakshita
	renameInputDetails();//ATP-151 TO 157 ADDED by KRISHNA PANDEY
	if(PT_UTILITY_FLAG == "Y"){
		disableFieldOnDemand(DELIVERY_BRANCH);
	}
	enableFieldELCB_AM_DISC(); //PT_283-284
	if(getValue(ROUTE_TO_PM) == null || getValue(ROUTE_TO_PM) == ''){
		setValues({[ROUTE_TO_PM]: 'ADCB Checker'}, true);
	}
	setDefaultValuesPM();
	/*//START OF RAKSHITA CODE
	var requestCategory = getValue('REQUEST_CATEGORY');
	var reqType = getValue('REQUEST_TYPE');
	if('IFS' == requestCategory || 'IFP' == requestCategory|| 'IFA' == requestCategory){
		
		if(reqType == 'AM' || reqType == 'STL' || reqType == 'IFA_CTP'){
			hideControls(TSLM_INST_TOGGLE);
			showControls('SEC_TSLM_LOAN_INPUT_DETAILS');
			showControls('LOAN_STAGE,FINANCED_AMOUNT,OVERALL_OUTSTANDING_AMOUNT,PROCESSING_SYSTEM');
		}if(reqType =='LD'){
			showControls('TSLM_ANY_PAST_DUE_CID,FCUBS_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE');
			showControls(PROCESSING_SYSTEM);
			enableFieldOnDemand(PROCESSING_SYSTEM);
			disableFieldOnDemand('FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE');
		}if(reqType =='LD' && getGridRowCount('Q_TSLM_Invoice_No_SA_Loan')>0 && getValue(TSLM_STANDALONE_LOAN) =='1'){
			showControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,BTN_TSLM_INVOICE_CHK_CONFIRM');
			enableFieldOnDemand('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,BTN_TSLM_INVOICE_CHK_CONFIRM')//to be checked
		}if( 'IFA' == requestCategory && reqType == 'IFA_CTP'){
			showControls('SEC_TSLM_LOAN_INPUT_DETAILS');
			showControls(B_S_CP_LOAD_ENABLE);
		}if(reqType == 'AM' || reqType == 'STL' || reqType == 'IFA_CTP' || reqType == 'LD'){
			showControls(SEC_TSLM_LOAN_REF_DET);
		}
		showControls(TSLM_STANDALONE_LOAN,SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_TSLM_INVOICE_NUMBERS_FOR_STANDALONE_LOAN);
		disableFieldOnDemand(TSLM_STANDALONE_LOAN,SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_TSLM_INVOICE_NUMBERS_FOR_STANDALONE_LOAN);
		
		if(reqType =='LD' && getValue(PROCESSING_SYSTEM)=='T' && getValue(CREATE_AMEND_CNTRCT_FCUBS) =='2'){
			enableFieldOnDemand(TAB_TSLM_LOAN_REF);
		}
		if(getValue(PROCESSING_SYSTEM)== "T"){
			//disableFieldOnDemand('FCUBSConNo');
			setStyle('FCUBSConNo',PROPERTY_NAME.DISABLE,'true');
		}//END OF RAKSHITA CODE
	}else{
		hideControls(TSLM_INST_TOGGLE,SEC_TSLM_CUST_SPECIAL_INST);
	}	
	TSLMComplianceTabDisableEnable();*/
	tslmsControlsEnableDisable();
	selectchecboxTSLM();

	//ATP - 204,205  ADDED BY shivanshu
	if(!(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType  || 'PD' == reqType || 'PDD' == reqType))) {
		setTabStyle("tab1",8, "visible", "false");
	}
	if(getValue('HIDDEN_START_FLAG') == 'true'){
		disableFieldOnDemand('UTC_START_CHECK');
	}
	if(getValue('HIDDEN_START_FLAG') == 'false'){
		enableFieldOnDemand('UTC_START_CHECK');
	}
	if('TSLM Front End'== getValue(PROCESS_TYPE)){
		//ATP-363 11-01-2024 REYAZ
		//START CODE
		if('STL' == reqType){
			setStyle('PR_EXCHANGE_RATE', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('INT_EXCHANGE_RATE', PROPERTY_NAME.VISIBLE, 'true');
		}
		//END CODE
		
		if(getValue('TRSD_FLAG') == null || getValue('TRSD_FLAG') == ''){
			setValue('TRSD_FLAG','N');
			//setValues({['TRSD_FLAG']: 'N'}, true);
		}
	}
	pmTslmValidation();  // add krishna
	pmHybridcombo();
	
//	setDefaultFields();
	//ADDED BY SHIVANSHU
	if(('BAU'== getValue(PROCESS_TYPE)) || ('PT'== getValue(PROCESS_TYPE))){
		if (('ELCB_AM' == reqType) || ('EC_AM' == reqType) || ('ELCB_BL' == reqType) || ('EC_BL' == reqType)){
			if(getValue('BILL_RVSD_DOC_AVL') == '1'){ 
				setStyle('COURIER_COMPANY', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('COURIER_AWB_NUMBER', PROPERTY_NAME.VISIBLE, 'true');
			}
		}
	}	
	if(getValue('COURIER_AWB_NUMBER') == 'Not Applicable'){ 
		  disableFieldOnDemand('COURIER_AWB_NUMBER');
	}	
    
    //ATP - 204,205  Reyaz
     if(reqCat == 'SCF'){ //ADDED 26-10-2023 FOR SCF CHANGES
		if(reqType == 'PD' || 'PDD' == reqType){
			hideControls(PM_SCF_PD_BAU_HIDE);
			hideControls(PM_SCF_PD_BAU_FRAMES_HIDE);
			disableFieldOnDemand(PM_SCF_PD_BAU_DISABLE);
			document.getElementById('INF_LOAN_CURR_label').innerText = 'Discount Currency';
		    showControls('SEC_TSLM_CUST_SPECIAL_INST');
			enableFieldOnDemand('UTC_REQUIRED');
		    //ATP-326 DATE 20-12-2023 REYAZ
		    setValues({[TSLM_LOAN_AMOUNT]: getValue(TRANSACTION_AMOUNT)}, true);
			if('TSLM Front End' == getValue(PROCESS_TYPE)){ //Added to Populate Issuing Center & Standalone Loan
				setValues({[TSLM_STANDALONE_LOAN]: '2'}, true);
				setValues({[TSLM_COMPANY_TYPE]: 'B'}, true) 
				setValues({['BRANCH_CITY']: 'DXB'}, true);
				setValues({['ASSIGNED_CENTER']: 'DXB'}, true);
			}
		}
	} 

  //ATP-454 02/05/2024 REYAZ START
	if((reqCat == 'IFA' || reqCat == 'IFP' ||reqCat == 'IFS') && reqType == 'AM'){
		enableFieldOnDemand('INF_NEW_MATURITY_DATE');
		showControls('AMENDED_EFFECTIVE_DATE');
    }
  //ATP-454 02/05/2024 REYAZ ENDS	
  //ATP-463 16/05/2024 JAMSHED START
	if(getValue('PAST_DUE_LIABILITY')!='' && getValue('PAST_DUE_LIABILITY')!=null && getValue              ('PAST_DUE_LIABILITY') !=undefined ){
		setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.DISABLE, 'true');
	}
	//ATP-463 16/05/2024 JAMSHED END	

	//Added by SHivanshu ATP-458 12/07/2024
	 handleSwiftMtUI();
	 showTsRequiredField();
	 disableFetchUtcScore();
}

function genericSetStyle(fields,operation,flag){
	var temp = fields.split(",");
	var a;
	for(a in temp)
	{
		setStyle(temp[a],operation,flag);
	}
}

function handlePMEvent(object, event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PROCESSING_MAKER){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickPMEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changePMEvent(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
			lostFocusPMEvent(event);		
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
		}else if (event.type == EVENT_TYPE.LOAD) {
			loadPMEvent(event);
		}
	} else if(workstepName==WORKSTEP.PROCESSING_CHECKER){
		handlePCEvent(object, event);
	} else if ( event.target.id == DEC_CODE && WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
		if("CRCA" == getValue(DEC_CODE)){
		  enableFieldOnDemand(TFO_REMARKS);
		} else{
			disableFieldOnDemand(TFO_REMARKS);
        }
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_SUBMIT && workstepName == WORKSTEP.COMPLIANCE_REFERRAL_RESPONSE){
		executeServerEvent(event.target.id, event.type, '', false);
	}

}
function loadPMEvent(event){
	console.log('inside loadPMEvent >>');
	if (event.target.id == SUBFORM_FINAL_DECISION_ID) {

		executeServerEvent(event.target.id, EVENT_TYPE.LOAD, '', false);

	}
}

function amendTypeHandling() {
	console.log('*****************Inside amendTypeHandling***********************');
	var amndType = getValue(AMEND_TYPE);
	var reqCategory = getValue(REQUEST_CATEGORY);
	var requestType = getValue(REQUEST_TYPE);
	if(reqCategory == 'GRNT' || reqCategory == 'SBLC')	//RR
	{
		if(amndType!= null){
			console.log('[amndType] '+amndType);
			if (amndType == 'IV' || amndType == 'DV')
			{
				console.log(' if ' + amndType);
				setStyle(NEW_EXP_DATE,'disable','true');
				setStyle(NEW_TRN_AMT,'disable','false');
				setValues({[NEW_EXP_DATE]: ''}, true);
			}
			else if ((amndType == 'EE'||amndType ==  'CE'|| amndType == 'OF'))
			{
				console.log(' else if ' + amndType);
				setStyle(NEW_TRN_AMT,'disable','true');
				setStyle(NEW_EXP_DATE,'disable','false');
				setValues({[NEW_TRN_AMT]: ''}, true);
			}
			else if (amndType == 'CT')
			{   
				console.log(' else if ' + amndType);
				setStyle(NEW_TRN_AMT,'disable','false');
				setStyle(NEW_EXP_DATE,'disable','false');

			}
			else
			{
				console.log(' else ' + amndType);
				setStyle(NEW_EXP_DATE,'disable','true');
				setStyle(NEW_TRN_AMT,'disable','true');
			}
		}
		console.log('*****************END amendTypeHandling***********************');
	}else if('ELC' == reqCategory &&('ELC_SLCA' == requestType||'ELC_SLCAA' == requestType||'ELC_SER' == requestType
			||'ELC_SCL' == requestType))  //RR
	{
		if(amndType!= null){
			console.log('[amndType] '+amndType);
			if (amndType == 'IV' || amndType == 'DV')
			{
				console.log(' if ' + amndType);
				setStyle(NEW_EXP_DATE,'disable','true');
				setStyle(NEW_TRN_AMT,'disable','false');
				setValues({[NEW_EXP_DATE]: ''}, true);
			}
			else if ((amndType == 'EE'||amndType ==  'CE'|| amndType == 'OF'))
			{
				console.log(' else if ' + amndType);
				setStyle(NEW_TRN_AMT,'disable','true');
				setStyle(NEW_EXP_DATE,'disable','false');
				setValues({[NEW_TRN_AMT]: ''}, true);
			}
			else if (amndType == 'CT')
			{   
				console.log(' else if ' + amndType);
				setStyle(NEW_TRN_AMT,'disable','false');
				setStyle(NEW_EXP_DATE,'disable','false');

			}
			else
			{
				console.log(' else ' + amndType);
				setStyle(NEW_EXP_DATE,'disable','true');
				setStyle(NEW_TRN_AMT,'disable','true');
			}
		}
		console.log('*****************END amendTypeHandling***********************');
	}
}


function setFrmLabelName() {
	var ReqCategory = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var sAcitivityName = getWorkItemData('activityName');
	if(ReqCategory=='TL')
	{	var xx=document.getElementById('BILL_LN_REFRNCE_label');

	xx.textContent='Bill Reference';
	var yy=document.getElementById('FCUBSConNo_label');

	yy.textContent='FCUBS Contract Number/Loan Reference';

	}
	else if(ReqCategory=='ELC'||ReqCategory=='ILC')
	{
		var xx=document.getElementById('GTEE_FRAME');
		var t=xx.childNodes[0].firstChild;
		t.textContent='LC Input Details';
		showSection('GTEE_FRAME');
	}

	else if(ReqCategory=='EC' ||ReqCategory== 'IC' || ReqCategory=='DBA'|| ReqCategory=='ELCB'|| ReqCategory=='ILCB')
	{
		var xx=document.getElementById('IF_FRAME');

		var t=xx.childNodes[0].firstChild;
		t.textContent='Bills Input Details';
		ecFramePT();
		if(reqType == 'ELCB_BL'){
			showControls(COMBO_CUST_INSTR);
		}
		if(reqType == 'ELCB_AM'){
			showControls(ELCB_AM_FIELDS);
		}
		if(reqType == 'ELCB_DISC'){
			showControls(ELCB_AM_FIELDS);
		}
	}
	else if(ReqCategory=='IFS' ||ReqCategory== 'IFP' ||ReqCategory== 'IFA')
	{
		setStyle(COMBOX_IFS_LOAN_GRP,'disable','true');
	}
}
function finalDecisionCheckListHandling()
{
	var requestCategory = getValue(REQUEST_CATEGORY);
	if(requestCategory == ('IFS')||requestCategory == ('IFP') ||requestCategory == ('IFA')){
		setStyle('IFS_Final_Dec_frame', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('TL')){
		setStyle('IFS_Final_Dec_frame', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('Common_Fields', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('ILC')){
		setStyle('ILC_Controls', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('ILCB')){
		setStyle('ILCB_Controls', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('IC')){
		setStyle('IC_Controls', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('Common_Fields', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('EC')){
		setStyle('EC_Controls', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('ELCB')){
		setStyle('ELCB_Controls', PROPERTY_NAME.VISIBLE, 'true');
	}else if(requestCategory == ('ELC')){
		setStyle('ELC_Controls', PROPERTY_NAME.VISIBLE, 'true');
	} 
} 

function hidePMPCSWIFTFrame(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);

	if(('DBA_AM' == requestType)||('EC_AC' == requestType)||('EC_BS' == requestType)||('EC_AM' == requestType)
			||('ELC_LCA' == requestType)||('ELC_LCAA' == requestType)||('ELC_LCC' == requestType)||('ELCB_AC' == requestType)
			||('ELCB_AM' == requestType)||('ELCB_BS' == requestType)||('IC_AM' == requestType)||('ILCB_AM' == requestType)
			||('SBLC' == requestCategory &&('SBLC_NI' == requestType||'SBLC_AM' == requestType||'SBLC_ER' == requestType
					||'SBLC_CR' == requestType||'SBLC_CS' == requestType))  //RR
			||('GRNT' == requestCategory &&('NI' == requestType
					||'AM' == requestType||'CC' == requestType
					||'CL' == requestType||'ER' == requestType
					||'EPC' == requestType||'GA' == requestType
					||'GAA' == requestType))
			||('ELC' == requestCategory &&('ELC_SLCA' == requestType||'ELC_SLCAA' == requestType||'ELC_SER' == requestType
					||'ELC_SCL' == requestType))  //RR
					||('SG' == requestCategory) //Shipping_gtee_57
	){
		setStyle('FRM_SWIFT', PROPERTY_NAME.VISIBLE, 'false');
	}
}

function inputDetailShowHideSection()
{
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	var workstepName = getWorkItemData('activityName');
	var processType=getValue(PROCESS_TYPE);
	console.log('inside inputDetailShowHideSection');
	if('GRNT' == requestCategory||'SBLC' == requestCategory ||
		('ELC' == requestCategory && (requestType == 'ELC_SLCA' || requestType == 'ELC_SLCAA' 
			|| requestType == 'ELC_SER' ||requestType == 'ELC_SLC'))){ 
			showSection('GTEE_FRAME,GRNT_FRM_NEW,FRM_GRTEE_LC_COMMON');
			//enableDisableFieldsPM_GRNT_NI();//added by santhosh
			if(requestType == 'NI' || requestType == 'SBLC_NI'|| requestType == 'ELC_SLCA'){//added by santhosh & ELC added by mansi
				//ATP-381 09-FEB-2024 Jamshed
				if( (processType == 'PT' || processType == 'ET') && ('GRNT' == requestCategory || 'SBLC' == requestCategory ) && (requestType == 'NI' || requestType == 'SBLC_NI')) {   //ATP-469 Shahbaz 23-05-2024 
					setStyle('CASHMRG_ACCNO', PROPERTY_NAME.VISIBLE, 'true');
					setStyle('CASHMRG_ACCNO', PROPERTY_NAME.DISABLE, 'true');
					setStyle('IS_100PCT_CASHBACK', PROPERTY_NAME.VISIBLE, 'true');
					setStyle('IS_100PCT_CASHBACK', PROPERTY_NAME.DISABLE, 'true');
					setStyle('LL_CODE', PROPERTY_NAME.VISIBLE, 'true');
					setStyle('LL_CODE', PROPERTY_NAME.DISABLE, 'true');
				}
				//ATP-381 09-FEB-2024 Jamshed ends
				if(workstepName==WORKSTEP.PROCESSING_CHECKER){
					enableDisableFieldsPC_GRNT_NI();
				}else{
					enableDisableFieldsPM_GRNT_NI();
				}
				if(requestType == 'SBLC_NI'){ //ADDED BY KISHAN
					showControls(ANY_DOC_COURRIER);
					showControls(CUSTOMER_INSTRUCTION);//For swift and pt it is visible only
				 	showControls(REQ_CONFIRM_PARTY);
				}
		    } else if(requestType == 'AM' || requestType == 'SBLC_AM'|| requestType == 'ELC_SLCAA'){//added by santhosh & ELC added by mansi
		       enableDisableFieldsPM_GRNT_AM();
		    } else if(requestType == 'GAA'){//sheenu
		       enableDisableFieldsPM_GRNT_GAA();
	        }else if(requestType == 'GA'){
	        	enableDisableFieldsPM_GRNT_GA();//santhosh
	        }

	}else if('ELC' == requestCategory &&('ELC_SLCA' == requestType||'ELC_SLCAA' == requestType||'ELC_SER' == requestType
			||'ELC_SCL' == requestType))  //RR
	{ 
		showSection('GTEE_FRAME,GRNT_FRM_NEW,FRM_GRTEE_LC_COMMON');

	} else if('IFS' == requestCategory || 'IFP' == requestCategory || 'IFA' == requestCategory){
		showSection('IF_FRAME,IF_FRAME1'); 
		if('LD' == requestType && 'IFS' == requestCategory){
			showSection('IFS_BUYER'); 
		}  
	} else if('TL' == requestCategory ){
		showSection('IF_FRAME,IF_FRAME1');  
	} else if('ELCB' == requestCategory  || 'ILCB' == requestCategory
			|| 'IC' == requestCategory || 'DBA' == requestCategory
			|| 'EC' == requestCategory) {
		ieccbInputFrm(); 
		showSection('FRM_GRTEE_LC_COMMON,DEAL_DETAILS_FRAME,frm_contract_limits');
		ecFramePT();
		if('ILCB_AC' == requestType) {  
			setStyle(DISC_DETAILS,'disable','false');
		} else{
			setStyle(DISC_DETAILS,'disable','true');
		}
		if('ILCB' == requestCategory && 'ILCB_BL' == requestType)
		{
			showSection('Charges_Frame');
			setStyle('Q_Charges_Frame.legalCurrency','disable','true');
		}
		if('ILCB_AC' == requestType  || 'IC_AC' == requestType  || 'ELCB_AC' == requestType  || 'EC_AC' == requestType  || 'EC_AM' == requestType  || 'EC_DISC' == requestType  || 'ELCB_AM' == requestType  || 'ELCB_DISC' == requestType)
		{
			console.log('inside inputDetailShowHideSection for ILCB/ ELCB/IC/EC');
			showSection(IELC_MKR_INPUT_FRM);
			hideControls(DISABLE_PC_TXT_GDDESC);
			setStyle(LC_GOODS_DESC, PROPERTY_NAME.DISABLE, 'true');
		}
	} else if('ELC' == requestCategory || 'ILC' == requestCategory){
		console.log('inside inputDetailShowHideSection for ILC/ ELC');
		elcIlcInputFrm();
		setStyle('ISSUING_BANK_LC_NO', PROPERTY_NAME.VISIBLE, 'true'); 
		setStyle('ADVISING_BANK_REF', PROPERTY_NAME.VISIBLE, 'true');
		if('ILC_AM'==requestType){
			hideControls(NEW_TRN_AMT+','+NEW_EXP_DATE);
		}
		//ATP-381 09-FEB-2024 Jamshed
		if('ILC' == requestCategory && 'ILC_NI' == requestType && processType == 'PT') {
			setStyle('CASHMRG_ACCNO', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('CASHMRG_ACCNO', PROPERTY_NAME.DISABLE, 'true');
			setStyle('IS_100PCT_CASHBACK', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('IS_100PCT_CASHBACK', PROPERTY_NAME.DISABLE, 'true');
			setStyle('LL_CODE', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('LL_CODE', PROPERTY_NAME.DISABLE, 'true');
		} //ATP-381 09-FEB-2024 Jamshed ends
	}else if('SG' == requestCategory) //Shipping_gtee_58
	{  
		showSection('GTEE_FRAME,GRNT_FRM_NEW');
		disableFieldOnDemand(GTEE_FRAME+','+GRNT_FRM_NEW+','+COMBOX_PRODUCT_TYPE);
	}
	
}

function reqCatDupCheckFilter(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	console.log('inside reqCatDupCheckFilter>>');
	if(('GRNT' == requestCategory && 'NI' == requestType)
			||(('IFS' == requestCategory || 'IFP' == requestCategory || 'IFA' == requestCategory || 'SCF' == requestCategory )&& ('LD' == requestType || 'PD' == requestType || 'PDD' == requestType)) //added by MOKSH //ATP - 204,205
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

function handleDeDupFrame(){
	console.log('inside handleDeDupFrame>>');
	var sIsDedup = '';
	sIsDedup = getValue('IS_DEDUP');
	if(sIsDedup == ('N')){
		disableFieldOnDemand(DUP_CHK_CONFIRMATION+','+BTN_DUPE);
	}
}

function ieccbInputFrm(){
	showSection('IF_FRAME,BILL_FRAME');
	setStyle('INF_LOAN_CURR','disable','true');
}	 

function elcIlcInputFrm(){
	var requestCategory = getValue(REQUEST_CATEGORY);
	var requestType = getValue(REQUEST_TYPE);
	console.log('inside elcIlcInputFrm');
	if(requestCategory=='ILC' || requestCategory=='ELC')
	{
		if(requestType != 'ELC_LCAA' && requestType != 'ELC_LCC'){
			showSection(LC_FRAME,FRM_GRTEE_LC_COMMON,IELC_MKR_INPUT_FRM);
			setStyle(TRN_TENOR,'disable','true');
			setStyle(FIELD_GRNT_CHRG_ACC_TITLE,'disable','true');
			setStyle(FIELD_GRNT_CHRG_ACC_CRNCY,'disable','true'); 			
		}else if(requestType == 'ELC_LCAA' || requestType == 'ELC_LCC'){
			hideControls(IELC_MKR_INPUT_FRM);
			showSection(LC_FRAME,FRM_GRTEE_LC_COMMON);
			setStyle(TRN_TENOR,'disable','true');
			setStyle(FIELD_GRNT_CHRG_ACC_TITLE,'disable','true');
			setStyle(FIELD_GRNT_CHRG_ACC_CRNCY,'disable','true');
		}
	}else if(requestType == 'ILCB_AC' || requestType == 'IC_AC' || requestType == 'ELCB_AC' || requestType == 'EC_AC' || requestType == 'EC_AM' || requestType == 'EC_DISC' || requestType == 'ELCB_AM' || requestType == 'ELCB_DISC')
	{
		showSection(IELC_MKR_INPUT_FRM);
	}
}

function  clickPMEvent(event){
	console.log('Click Event of PM Started for '+event.target.id);
	var wi_name = getWorkItemData('processInstanceId');
	if(event.target.id == FETCH_DETAILS){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == ACCOUNT_NUMBER){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == IS_ACC_VALID){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == FINAL_DEDUPE){
		duplicateCheckJSP();
	} else if(event.target.id == DISC_DETAILS){
		discDetailsJSP();
	} else if(event.target.id == ADD_COUNTER_PARTY){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHKBX_SEND_MAIL){
		var checkBoxSendEmail=getValue(CHKBX_SEND_MAIL);
		if('true' == checkBoxSendEmail){
			console.log(' in CHKBX_SEND_MAIL click event  yes ');
			setValues({[MANUALLY_TICKED_MAIL]: 'Y'}, true);
		}else{
			console.log(' in CHKBX_SEND_MAIL click event  no ');
			setValues({[MANUALLY_TICKED_MAIL]: 'N'}, true);
		}
		saveWorkItem();	
	} else if(event.target.id == VESSEL_DETAILS){
		var params = 'LLI_BVD#'+getValue('WI_NAME')+'#'+getValue(TXT_VESSELNAME);
		openLLI(params);
	} else if(event.target.id == OWNERSHIP_DETAILS){
		var params = 'LLI_OD#'+getValue('WI_NAME')+'#'+getValue(TXT_VESSELNAME);
		openLLI(params);
	} else if(event.target.id == MOVEMENT_DETAILS){
		var params = 'LLI_MD#'+getValue('WI_NAME')+'#'+getValue(TXT_VESSELNAME);
		openLLI(params);
	} else if(event.target.id == BUTTON_SUBMIT){
		submitButton();
	}else if(event.target.id == BUTTON_MODIFY_PARTY_DETAILS){
		if(validateMandatoryPDTab() && validatePartyExistAtModify())
		{
			checkPDCountAndModifyPartyGrid();
		}
		saveWorkItem();
	} else if(event.target.id == BUTTON_MODIFY_CONTRACT_DETAILS){
		if(validateMandatoryCLTab)
		{
			checkCLCountAndModifyContractGrid();
		}
		saveWorkItem();
	} else if(event.target.id == BUTTON_SAVE){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_ADD_LLI){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_FETCH_LLI){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} /*else if(event.target.id == BUTTON_GENERATE_LLI_PDF){ 
		saveWorkItem();	
		generateLLIPDF();
		executeServerEvent(event.target.id, event.type, '', false);
	} */else if(event.target.id == BUTTON_PARTY_DETAIL_FETCH){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_ADD_PARTY_DETAILS){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_ADD_CONTRACT_DETAILS){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == TR_FETCH_RATE_BUTTON){ 
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == BUTTON_SF_CLOSE){
		setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
		selectSheet("tab1",7);
		disableFieldOnDemand(BUTTON_SUBMIT);
		executeServerEvent(event.target.id, event.type,'', false);
	} else if(event.target.id == BUTTON_SAVE){
		saveWorkItem();
	}else if(event.target.id == ACCOUNT_DETAILS){ 
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BUTTON_GENERATE_LLI_PDF ){ 
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id.substring(0,22) == 'Q_Amendment_Data_USER_' ){
		var PTColumn='Q_Amendment_Data_PT_'+event.target.id.substring(22);
		var FCUBSColumn='Q_Amendment_Data_FC_'+event.target.id.substring(22);
		var value;
		
		if(getValue(event.target.id) != '' && getValue(event.target.id) != null){
			 value =  getValue(event.target.id);
		}else if(getValue(PTColumn) != '' && getValue(PTColumn) != null){
			 value =  getValue(PTColumn);
        }else{
			 value = getValue(FCUBSColumn);
        }
		//if(getValue(event.target.id) != '' ||getValue(event.target.id) != null){
			var id = "Q_Amendment_Data_FIN_"  + event.target.id.substring(22);
			console.log(id);
			var value =  getValue(event.target.id);
			setValues({[id]: value}, true);
	}else if(event.target.id == BTN_START_TRSD ){
		var  trsdListCount=getGridRowCount(LISTVIEW_TRSD_TABLE);
		var trsdListOtherCount=getGridRowCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
		if(trsdListCount == 0 && trsdListOtherCount == 0){
			showMessage('', 'Please enter data for TRSD', 'error');
			return false;
		}
	//	}else if (validateIsEntityTRSDExist()){
			saveWorkItem();
			executeServerEvent(event.target.id, event.type, '', false);
		//}
	}else if(event.target.id==BTN_IFRAME_CLOSE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == TSLM_INST_TOGGLE){//START OF RAKSHITA CODE
		var toggleValue = getValue(TSLM_INST_TOGGLE)
		if(toggleValue == false){
			hideControls('SEC_TSLM_CUST_SPECIAL_INST');
		}else if(toggleValue == true){
			showControls('SEC_TSLM_CUST_SPECIAL_INST');
		}//END OF RAKSHITA CODE	
	}else if(event.target.id == BTN_TSLM_INVOICE_CHK_CONFIRM){//CODE BY MOKSH
		invoiceDuplicateCheckJSP();
	}else if (event.target.id == TRSD_SCREENING_DATA_BTN){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == 'UTC_START_CHECK'){
		//		 setValue('HIDDEN_START_FLAG','false');
		//		 	var wd_uid=getWorkItemData("sessionId");
		//			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
		//			var urlDoc = document.URL;
		//			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
		//			var jspURL=sLocationOrigin+"/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&VesselName=updateDocumentStatusUTC&session="+wd_uid+"&WD_UID="+wd_uid;
		//			document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
		//			selectSheet("tab1",PM_TAB_IFRAME_ID);	
		//			document.getElementById(BUTTON_SF_CLOSE).style.display="none";
		executeServerEvent(event.target.id, event.type, '', false);
	}else if (event.target.id == 'UTC_BTN_REFRESH'){
	executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == 'BTN_INVOICE_BROWSE'){
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
		var jspURL=sLocationOrigin+"/webdesktop/CustomFolder/TFO/UploadFile.jsp?WI_NAME="+wi_name;
		document.getElementById('IntegrationUploadInvoice').src=jspURL;	
		
	}else if(event.target.id == 'BTN_SCREENING_BROWSE'){
		//executeServerEvent(event.target.id, event.type, '', false);
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
		var jspURL=sLocationOrigin+"/webdesktop/CustomFolder/TFO/UploadExcelFile.jsp?WI_NAME="+wi_name;
		document.getElementById('ScreeningDetails').src=jspURL;		
	}else if (event.target.id == 'BTN_UTC_SCORE'){
	   executeServerEvent(event.target.id, event.type, '', false);
	}
	
	
	console.log('Click Event of PM Ends for '+event.target.id);
}


function changePMEvent(event){
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);
	var amendExpiryType= getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE);
	console.log('Change Event of PM Started for '+event.target.id);
	if(event.target.id == TRN_TENOR){
		chkTransactionTenor(TRN_TENOR);
	} else if(event.target.id == AMEND_TYPE){
		amendTypeHandling();
	} else if(event.target.id == ACCOUNT_NUMBER){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == IS_ACC_VALID){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CREATE_AMEND_CNTRCT_FCUBS){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == OPERATION_CODE){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == DEC_CODE){
		if(getValue(DEC_CODE)=='TXNC'){
			enableFieldOnDemand(TS_REQUIRED);
		}
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == DUP_CHK_CONFIRMATION){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == TR_BUY_CUR || event.target.id == TR_SELL_CUR){
		setValues({ 
			[TR_RATE_REQ]: getValue(TR_BUY_CUR)+'-'+getValue(TR_SELL_CUR),
			[TR_EXCHANGE_RATE]: '',
			[UI_EXCHANGE_RATE]: ''}, true);
	} else if (event.target.id == TFO_BRANCH_CITY) {
		setValues({[TFO_ASSIGNED_CENTER]: getValue(TFO_BRANCH_CITY)}, true);
	} else if(event.target.id == PARTY_TYPE){ 
		setValues({[PARTY_DESC]: getValue(PARTY_TYPE)}, true);
	}else if(event.target.id == PARTY_DESC){					

	} else if(event.target.id == 'LLI_CHK_OK_0' || event.target.id == 'LLI_CHK_OK_1'
		|| event.target.id == 'LLI_CHK_OK_2'){ 
		if(getValue(LLI_CHK_OK) == 'Y'){
			enableFieldOnDemand('COMP_EXP_REMARKS');//krishna
			setValues({[COMP_REF]: ''}, true);
			if(getValue(COMP_POSITIVE_MATCH) == 'Y') {
				setValues({[COMP_POSITIVE_MATCH]: ''}, true);
				setStyle('Frame15','sectionstate','collapsed');
			}
			enableFieldOnDemand('Btn_Gen_PDF');
			setStyle('FRM_LLI','lock','true');
		} else if(getValue(LLI_CHK_OK) == 'X'){
			setValues({[COMP_REF]: 'N'}, true); //krishna
			if("N" == getValue(COMP_REF)){
			disableFieldOnDemand('COMP_EXP_REMARKS');
			}if(getValue(COMP_POSITIVE_MATCH) == 'Y') {
				setValues({[COMP_POSITIVE_MATCH]: ''}, true);
				setStyle('Frame15','sectionstate','collapsed');
			}
			setStyle('FRM_LLI','lock','true');
		} else if(getValue(LLI_CHK_OK) == 'O'){
			enableFieldOnDemand('COMP_EXP_REMARKS');//krishna
			setValues({[COMP_REF]: ''}, true);
			if(getValue(COMP_POSITIVE_MATCH) == 'Y') {
				setValues({[COMP_POSITIVE_MATCH]: ''}, true);
				setStyle('Frame15','sectionstate','collapsed');
			}
			setStyle('FRM_LLI','lock','true');
		}
		executeServerEvent(LLI_CHK_OK, event.type, '', false);
	} else if(event.target.id == 'COMP_REF_0' || event.target.id == 'COMP_REF_1'){
		if(('IFP' == requestCat || 'IFA' == requestCat || 'IFS' == requestCat || 'SCF' == requestCat) && ('LD' == requestType || 'IFA_CTP' == requestType || 'PD' == requestType || 'PDD' == reqType) || getValue('SWIFT_CHANNEL') == 'MT798'){ //ATP - 204,205 Modify Shivanshu ATP-458
    if("N" == getValue(COMP_REF)){
		disableFieldOnDemand('COMP_EXP_REMARKS'); 
		setStyle('Frame15','disable', 'true');
		setStyle('Frame15','sectionstate','collapsed');
	} else{
	    enableFieldOnDemand('COMP_EXP_REMARKS');
				setStyle('Frame15','disable', 'false');
		setStyle('Frame15','sectionstate','expanded');
    }
  }
	}else if(event.target.id == 'COMP_CHK_DONE_0' || event.target.id == 'COMP_CHK_DONE_1'){ 
		if(getValue(COMP_CHK_DONE) == 'N') {
			setFocus('COMP_CHK_DONE');
			showMessage(COMP_CHK_DONE, 'Please select compliance check done from compliance screening tab.', 'error');
			setValues({[COMP_CHK_DONE]: ''}, true);
		}
	}  
	else if(event.target.id == 'COMP_POSITIVE_MATCH_0' || event.target.id == 'COMP_POSITIVE_MATCH_1'
		|| event.target.id == 'COMP_POSITIVE_MATCH_2'){
		if(getValue(COMP_POSITIVE_MATCH) == 'Y') {
			setStyle('Frame15','sectionstate','expanded');
			setStyle('COMP_REF_1','disable','true');
		} else if(getValue(COMP_POSITIVE_MATCH) == 'N' || getValue(COMP_POSITIVE_MATCH) == 'A') {
			setStyle('Frame15','sectionstate','collapsed');
		}
		executeServerEvent(COMP_POSITIVE_MATCH, event.type, '', false);
	} else if(event.target.id == 'COMP_REF_0' || event.target.id == 'COMP_REF_1'){ 
		if(getValue(COMP_REF) == 'Y') {
			enableFieldOnDemand(COMP_EXP_REMARKS);
			setStyle('Frame15','sectionstate','expanded');
		} else if(getValue(COMP_REF) == 'N') {
			setValues({[COMP_EXP_REMARKS]: ''}, true);
			disableFieldOnDemand(COMP_EXP_REMARKS);
			setStyle('Frame15','sectionstate','collapsed');
		}
		executeServerEvent(COMP_REF, event.type, '', false);
	} else if(event.target.id == 'TR_REFER_TREASURY_0' || event.target.id == 'TR_REFER_TREASURY_1'){
		executeServerEvent(TR_REFER_TREASURY, event.type, '', false);
	} else if(event.target.id == 'COMP_IMB_CHK_0' || event.target.id == 'COMP_IMB_CHK_1'){
		if(getValue(COMP_IMB_CHK) == 'Y') {
			setStyle('IMB_DETAIL_FRM','sectionstate','collapsed');
			setStyle('IMB_DETAIL_FRM','lock','true');
		} else if(getValue(COMP_IMB_CHK) == 'N') {
			setStyle('IMB_DETAIL_FRM','sectionstate','collapsed');
			setStyle('IMB_DETAIL_FRM','lock','true');
		}
	} else if(event.target.id == TXT_VESSELNAME) {
		if(getValue(TXT_VESSELNAME) != '' && getValue(TXT_VESSELNAME) != null) {
			enableFieldOnDemand('Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details');
		} else {
			disableFieldOnDemand('Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details');
		}
	} else if(event.target.id == RELATIONSHIP_TYPE) {
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == TRSD_OTHER_ENTITY_NAME_ID){
		setValues({[TRSD_OTHER_ENTITY_NAME_ID]: getValue(TRSD_OTHER_ENTITY_NAME_ID).trim()}, true);
	}else if(event.target.id.substring(0,22) == 'Q_Amendment_Data_USER_' ){
		if(getValue(event.target.id) != '' ||getValue(event.target.id) != null){
			var id = "Q_Amendment_Data_FIN_"  + event.target.id.substring(22);
			console.log(id);
			var value =  getValue(event.target.id);
			setValues({[id]: value}, true);
			if(event.target.id=='Q_Amendment_Data_USER_TRANSACTION_AMOUNT'){
				setValues({[NEW_TRN_AMT]: getValue("Q_Amendment_Data_FIN_TRANSACTION_AMOUNT")}, true);
			}
		}
	}//CODE BY MOKSH
	else if(event.target.id == PROCESSING_SYSTEM){
		if(getValue(PROCESSING_SYSTEM) == 'T'){
			setStyle('FCUBSConNo',PROPERTY_NAME.DISABLE,'true');
			if(requestType == 'LD'){
				enableFieldOnDemand('STANDALONE_LOAN,SEC_TSLM_REF_DET');
			}if(requestType == 'LD' || requestType == 'IFA_CTP' ){
				disableFieldOnDemand('BTN_FETCH_TSLM_CID_DETAILS');
			}
			disableFieldOnDemand('TSLM_COMPANY_TYPE');
			enableFieldOnDemand('LOAN_AMOUNT');
			showControls('SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN,LOAN_AMOUNT');
			showControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,SEC_TSLM_LOAN_REF_DET,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM,SEC_TSLM_REF_DET');
			showControls('BTN_FETCH_TSLM_CID_DETAILS,Toggle2,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE');
			if(requestCat == 'IFA' && requestType == 'LD'){
				setValues({[COMBOX_PRODUCT_TYPE]: 'L092'}, true);
			}
		} else if(getValue(PROCESSING_SYSTEM) == 'F'){
			setStyle('FCUBSConNo',PROPERTY_NAME.DISABLE,'false');
			disableFieldOnDemand('STANDALONE_LOAN,BTN_FETCH_TSLM_CID_DETAILS,SEC_TSLM_REF_DET,SEC_TSLM_LOAN_REF_DET,LOAN_AMOUNT');
			hideControls('SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_TSLM_LOAN_REF_DET,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN,SEC_TSLM_CUST_SPECIAL_INST');
			hideControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM,SEC_TSLM_REF_DET');
			hideControls('BTN_FETCH_TSLM_CID_DETAILS,Toggle2,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE,LOAN_AMOUNT');
			setValues({['STANDALONE_LOAN']: '1'}, true);
			if(requestCat == 'IFA' && requestType == 'LD'){
				setValues({[COMBOX_PRODUCT_TYPE]: 'TF03'}, true);
			}
		}
	}else if(event.target.id == 'UTC_REQUIRED'){
		console.log(" UTC_REQUIRED"+UTC_REQUIRED);
		if((getValue('UTC_REQUIRED') == 'No')&& (getValue('UTC_REQUIRED_BRMS') == 'Yes') )
		{ 
			enableFieldOnDemand('UTC_JSTIFICATION_REQUIRED', true);
			setValue('HIDDEN_START_FLAG','true');
			setValue('IS_GETDOCUMENT_UTC_DONE','');
		}
		if(getValue('UTC_REQUIRED') == 'No' && getValue('UTC_REQUIRED_BRMS') == 'No')
		{ 
			setValue('HIDDEN_START_FLAG','true');
			setValue('IS_GETDOCUMENT_UTC_DONE','');
		}
		if(getValue('UTC_REQUIRED') == 'Yes')
		{ 
			setValue('UTC_JSTIFICATION_REQUIRED','');
			disableFieldOnDemand('UTC_JSTIFICATION_REQUIRED');
			setValue('HIDDEN_START_FLAG','');
			//setValue('IS_GETDOCUMENT_UTC_DONE','N');
		}
	} else if(event.target.id == OPTIONAL_ENTITY){   //Added by reyaz
		console.log(" check optional"+OPTIONAL_ENTITY);
		var entity =getValue(OPTIONAL_ENTITY);
		if(entity == 'DRAWEE/BUYER COUNTRY' || entity == 'DRAWER/SELLER COUNTRY' || entity =='Goods/Services' || 
			entity =='Country of Origin' || entity =='Beneficiary Bank Name' || entity =='Beneficiary Bank Country' 
			|| entity =='Port of Loading/Airport of Departure' || entity =='Port of Discharge/Airport of Destination' || 
			entity =='Place of Taking in Charge/Dispatch from___ /Place of Receipt' || 
			entity =='Place of Final Destination/For Transportation to__/Place of Delivery' || entity == "Beneficiary Bank Correspondence's Country"
			|| entity == "Beneficiary Bank Correspondence's Name")
		{ 
			setValue('table182_TRSD_Screening_Type','Both');
		}
		else {
			setValue('table182_TRSD_Screening_Type','');
	}
		
	} else if(event.target.id == 'COURIER_COMPANY'){   //Added by shivanshu
		 if (('ELCB_AM' == requestType) || ('EC_AM' == requestType) || ('ELCB_BL' == requestType) || ('EC_BL' == requestType)){
			  if(getValue('COURIER_COMPANY') == 'Not Applicable'){ 
			  		setValues({['COURIER_AWB_NUMBER']:'Not Applicable'}, true);
					disableFieldOnDemand('COURIER_AWB_NUMBER');
			  }else {
				  	setValues({['COURIER_AWB_NUMBER']:''}, true);
					enableFieldOnDemand('COURIER_AWB_NUMBER');

			  }
		}
	}
	
	console.log('Change Event of PM Ends for '+event.target.id);
	if((requestType =='AM' || requestType =='SBLC_AM' || requestType =='ELC_SLCAA' || requestType =='GAA') 
			&& event.target.id=='Q_Amendment_Data_USER_EXPIRY_TYPE'
		 && amendExpiryType =='FD'){	//Mansi for user amendment frame
		
		enableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
		enableFieldOnDemand('Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE');
		setValues({[Q_AMENDMENT_DATA_USER_EXPIRY_COND]: ''},true);//mansi new
		setValues({[Q_AMENDMENT_DATA_FIN_EXPIRY_COND]: ''},true);//mansi ne
		setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: ''}, true);
		setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']:''}, true);
    	setValues({['Q_Amendment_Data_USER_EXPIRY_DATE']: ''}, true);
    	setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: ''}, true);
		disableFieldOnDemand(Q_AMENDMENT_DATA_USER_EXPIRY_COND);
		setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
		setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
		
	}
	else if(requestType =='SBLC_AM' || requestType =='AM' || requestType =='ELC_SLCAA' || requestType =='GAA'){
		var userExpirydate =  getValue('Q_Amendment_Data_USER_EXPIRY_TYPE');
        var ptExpirydate =  getValue('Q_Amendment_Data_PT_EXPIRY_TYPE');
		if(ptExpirydate == 'OE' || userExpirydate == 'OE'){
			
        	 setValues({[Q_AMENDMENT_DATA_USER_EXPIRY_COND]: ''},true);//mansi new
        	 disableFieldOnDemand(Q_AMENDMENT_DATA_USER_EXPIRY_COND);
			 setValues({[Q_AMENDMENT_DATA_FIN_EXPIRY_COND]: ''},true);//mansi new
			 disableFieldOnDemand(Q_AMENDMENT_DATA_FIN_EXPIRY_COND);
			 setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: ''}, true);
			 disableFieldOnDemand('Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE');
			 setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']:''}, true);
			 disableFieldOnDemand('Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE');
	    	 setValues({['Q_Amendment_Data_USER_EXPIRY_DATE']: ''}, true);
	         disableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
	    	 setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: ''}, true);
	         disableFieldOnDemand('Q_Amendment_Data_FIN_EXPIRY_DATE');
        }else{
        	enableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
        }
   }	
	
	if((requestType == 'AM' || requestType == 'SBLC_AM' || requestType == 'GAA' || requestType == 'ELC_SLCAA')
			&& amendExpiryType == 'COND')
	{
		enableFieldOnDemand(Q_AMENDMENT_DATA_USER_EXPIRY_COND);
		enableFieldOnDemand('Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE');
		enableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
		 setValues({['Q_Amendment_Data_FIN_EXPIRY_COND']: getValue(Q_AMENDMENT_DATA_USER_EXPIRY_COND)}, true);

	}

}
function lostFocusPMEvent(event){
	console.log('inside lostFocusPMEvent >>');
	var workstepName = getWorkItemData('activityName');
	var wi_name      = getWorkItemData('processInstanceId');
	var requestType  = getValue(REQUEST_TYPE);
	var requestCat   = getValue(REQUEST_CATEGORY);
		
	if (event.target.id == NEW_TRN_AMT) {
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	} else if (event.target.id == EXP_DATE || event.target.id == NEW_EXP_DATE) {
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	} else if(event.target.id == TR_SELL_AMT || event.target.id == TR_SELL_CUR){
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	} else if(event.target.id == TR_BUY_CUR || event.target.id == TR_LOAN_AMT){
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	}else if(event.target.id == UI_EXCHANGE_RATE){
		var exchangeRate = getValue(UI_EXCHANGE_RATE);
		if(exchangeRate.length>0 && (exchangeRate.length-exchangeRate.indexOf('.')) <= 1){
			setValues({[UI_EXCHANGE_RATE]: ''}, true);
			showMessage('', 'Please enter a valid number.', 'error');
		}
	} else if(event.target.id == TXT_VESSELNAME){
		if(getValue(TXT_VESSELNAME) != '' && getValue(TXT_VESSELNAME) != null){
			enableFieldOnDemand('Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details');
		} else {
			disableFieldOnDemand('Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details');
		}
	}else if(event.target.id.substring(0,22) == 'Q_Amendment_Data_USER_' ){
		var PTColumn='Q_Amendment_Data_PT_'+event.target.id.substring(22);
		var FCUBSColumn='Q_Amendment_Data_FC_'+event.target.id.substring(22);
		var value;
		var amendExpiryType= getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE);
		
		if(getValue(event.target.id) != '' ||getValue(event.target.id) != null){
				 value =  getValue(event.target.id);
			}else if(getValue(PTColumn) != '' && getValue(PTColumn) != null){
				 value =  getValue(PTColumn);
	        }else{
				 value = getValue(FCUBSColumn);
	        }
			var id = "Q_Amendment_Data_FIN_"  + event.target.id.substring(22);
			console.log(id);
			var value =  getValue(event.target.id);
			setValues({[id]: value}, true);

			if(event.target.id=='Q_Amendment_Data_USER_EXPIRY_DATE'){
				console.log('value='+getValue('Q_Amendment_Data_FIN_EXPIRY_DATE'));
				setValues({[NEW_EXP_DATE]: getValue('Q_Amendment_Data_FIN_EXPIRY_DATE')}, true);
			}else if(event.target.id=='Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE'){
				executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
			 }
			 if((requestType =='AM' || requestType =='SBLC_AM' || requestType =='ELC_SLCAA' || requestType =='GAA') 
				&& (amendExpiryType =='FD' || amendExpiryType =='COND') &&event.target.id=='Q_Amendment_Data_USER_EXPIRY_DATE'){	//RR for user amendment frame
				setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
				setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
				}
			 else if((requestType =='AM' || requestType =='SBLC_AM' || requestType =='ELC_SLCAA'  ||requestType =='GAA')&& amendExpiryType =='COND'
				 && event.target.id=='Q_Amendment_Data_FIN_EXPIRY_COND'){
				 	 setValues({['Q_Amendment_Data_FIN_EXPIRY_COND']: getValue(Q_AMENDMENT_DATA_USER_EXPIRY_COND)}, true);
				 	 }

	}else if (event.target.id == TSLM_LOAN_AMOUNT) {
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	}else if (event.target.id == 'LOAN_VAL_DATE') { 												//Added By Shivanshu ATP-409
		executeServerEvent(event.target.id, EVENT_TYPE.LOSTFOCUS, '', false);
	}					
}

function validateCPDetails(){

}

function postServerEventHandlerPM(controlName, eventType, responseData) {
	var jsonData = handleTFOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if("Y" == getValue("LLI_CHK_OK")){
		console.log('expanding LLI on tab click');
		setStyle('FRM_LLI',"sectionstate","expanded");
		setStyle('FRM_LLI','lock', 'true');
	}else{
		setStyle('FRM_LLI',"sectionstate","collapsed");
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		var reqType = getValue(REQUEST_TYPE);
		var reqCat = getValue(REQUEST_CATEGORY);
		var sUtcRequired = getValue('UTC_REQUIRED');
		var sUtcRequiredBRMS = getValue('UTC_REQUIRED_BRMS');
		console.log('Load opearation code  :: '+getValue('OPERATION_CODE'));
		if(getValue(REQUEST_CATEGORY)=='IFS' || getValue(REQUEST_CATEGORY)=='IFP' ||getValue(REQUEST_CATEGORY) == 'IFA'){
			if(getValue(REQUEST_TYPE)=='STL' || getValue(REQUEST_TYPE)=='TL_STL'){
				disableFieldOnDemand('add_Qvar_cpd_details,delete_Qvar_cpd_details,Qvar_cpd_details');
			}
		} if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'STL' == reqType || 'AM' == reqType || 'PDD' == reqType)) { //ATP - 204,205
			setTabStyle("tab1",8, "visible", "true");
		} if(!(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType ||'PD' == reqType ||
		'STL' == reqType || 'AM' == reqType || 'PDD' == reqType))) {//ATP - 204,205
			setTabStyle("tab1",8, "visible", "false");
		}if(sUtcRequiredBRMS == 'Yes' && sUtcRequired == ''){
			setValue('UTC_REQUIRED','Yes');
		}if(sUtcRequiredBRMS == 'No'  && sUtcRequired == ''){
			setValue('UTC_REQUIRED','No');
		}
		//ATP-481 --JAMSHED --11-JUN-2024 START
		/*if(('IFA' == reqCat) && ('LD' == reqType)){
			setValue('UTC_REQUIRED','No');	
			setValue('UTC_JSTIFICATION_REQUIRED','UTC Not Applicable for IFA Disbursement');
		}*/
		//ATP-481 --JAMSHED --11-JUN-2024 END
		if('openRepairJSP'==jsonData.message){
			var callRequestType = jsonData.message;
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			//document.getElementById('sheet14_link').textContent = 'Workitem Creation';
			var jspURL=sLocationOrigin+"/TFO/CustomFolder/Repair_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&callRequestType="+callRequestType;
			document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
			disableFieldOnDemand(BUTTON_SUBMIT);
			selectSheet("tab1",PM_TAB_IFRAME_ID);
		}
		if(getValue(REQUEST_CATEGORY) == 'IFP' || getValue(REQUEST_CATEGORY) == 'IFA' ) {
			enableFieldOnDemand(ADCB_BILL_REF_NO);
		}
			// Added by reyaz 11/09/2023
		if('ELCB' == reqCat && 'ELCB_AC' == reqType ) {
			setStyle('OPERATION_CODE','disable', 'false');
		}
		if('SCF' == reqCat && ('PD' == reqType || 'PDD' == reqType)){      // ATP-409 SHIVANSHU
		 enableFieldOnDemand('UTC_REQUIRED');
				if('TSLM Front End'== getValue(PROCESS_TYPE)){
					disableFieldOnDemand(PM_SCF_PD_TSLM_DISABLE);
				}
			}
		//ecFramePT();
		TSLMComplianceTabDisableEnable();
		showAmendFrameFieldsPM();//Mansi
		break;
	case EVENT_TYPE.CLICK:	
		if(!jsonData.success){
			console.log('message in post: '+jsonData.message);
		if((jsonData.message).includes('###')){
				var arr = jsonData.message.split('###');
				showMessage(arr[0], arr[1], 'error');
			}
			saveWorkItem();	 
		}
		if(controlName == BUTTON_SAVE){ 
			saveWorkItem();	
		} else if(controlName == BUTTON_ADD_LLI){ 
			saveWorkItem();	
		} else if(controlName == BUTTON_FETCH_LLI){ 
			if(!jsonData.success){
				var wd_uid=getWorkItemData("sessionId");
				setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
				var jspURL=sLocationOrigin+"/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&VesselName="+getValue('TXT_LLI_VESSELNAME')+"&session="+wd_uid+"&WD_UID="+wd_uid;
				document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
				selectSheet("tab1",PM_TAB_IFRAME_ID);	
				document.getElementById(BUTTON_SF_CLOSE).style.display="none";
			}
		} else if(controlName == BUTTON_PARTY_DETAIL_FETCH){ 
			saveWorkItem();	 
		} else if(controlName == BUTTON_ADD_PARTY_DETAILS){ 
			saveWorkItem();	 
		} else if(controlName == BUTTON_ADD_CONTRACT_DETAILS){ 
			saveWorkItem();	 
		} else if(controlName == BUTTON_SF_CLOSE){
			saveWorkItem();	
			if(!jsonData.data == ''&&'showNotificationAlert'==jsonData.data){
				showMessage(jsonData.data,'1. As DISCOUNT_ON_ACCEP is Yes, a discounting request work item will be automatically created, post completion of acceptance step.<br/>2. The auto created work item will be available in PPM queue and can be retrieved either by Web deskstop or in Dash board. Ensure to discount this bill','error');

			}else if(jsonData.success){
				completeWorkItem();
			}
			RemoveIndicator("Application");
			document.getElementById("fade").style.display="none";
		} else if(BUTTON_GENERATE_LLI_PDF == controlName){
			var personName = jsonData.message;
			console.log(personName);
			var generatePDFLLIFlag = true;
			var gridSize = getGridRowCount(LISTVIEW_LLI);
			console.log("gridSize=>"+gridSize);
			if(gridSize > 0) {
				var JspVesselParameters = "";
				for(var gridRow = 0; gridRow < gridSize; gridRow++){
					JspVesselParameters += getValueFromTableCell("Qvar_LLI_Details", gridRow, 1)+",";
					console.log("JspVesselParameters=>"+getValueFromTableCell("Qvar_LLI_Details"));
				}
				var params ='LLI_GP#'+getValue('WI_NAME')+'#'+JspVesselParameters+'#'+personName;
				openLLI(params);
			}else {
				showMessage('', 'Please add vessel details to the grid.', 'error');
			}
		} else if (controlName == PM_TAB_CLICK) {
			disablePTFields();
			disableFSKData(); // ATP -417 REYAZ 27-02-202
			if(!jsonData.message == ''&&'enableSection'==jsonData.message){	//06/12/21			
				setStyle('Frame15','sectionstate','expanded');//06/12/21	
				//setStyle('FRM_LLI','sectionstate','expanded');//06/12/21	
			}
		} else if (controlName == SAVE_AND_NEXT) {
			disablePTFields();
                        disableFSKData(); // ATP -417 REYAZ 18-03-2024
		}else if(controlName == BTN_START_TRSD){
		//	saveWorkitem();
			// validateIsEntityTRSDExist();
			var data=jsonData.message.split('#'); 
			if(!jsonData.message == ''&& 'showTRSDJSP'==data[0]){
				var wd_uid=getWorkItemData("sessionId");
				var userName=getWorkItemData("userName");
				setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
				var cabinetName = getWorkItemData("cabinetName");
				var ipPort=document.URL.substr(document.URL.indexOf('//')+2,document.URL.indexOf('TFO')-9);
				var jspURL=sLocationOrigin+"/webdesktop/login/mailloginclient.jsf?CalledFrom=OPENWI&OAPDomHost="+ipPort+"&OAPDomPrt=https:&" +
				"ReferredFromSameOrigin=false&ReferredFromSameOrigin=false&CabinetName="+cabinetName+"&pid="+
				getValue(TRSD_WI_NAME)+"&wid=1&SessionId="+wd_uid+"&UserIndex="+data[1]+"&UserName="+userName;
				console.log(jspURL);
				document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
				selectSheet("tab1",PM_TAB_IFRAME_ID);	
				//document.getElementById(BUTTON_SF_CLOSE).style.display="none";
				//enableFieldOnDemand('BTN_IFRAME_CLOSE');
				setStyle(BTN_IFRAME_CLOSE, PROPERTY_NAME.VISIBLE, 'true');
			}
		}else if(controlName == TRSD_SCREENING_DATA_BTN){
			//	saveWorkitem();
			var data = jsonData.data ;
			var wd_uid=getWorkItemData("sessionId");
			var userName=getWorkItemData("userName");
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			var cabinetName = getWorkItemData("cabinetName");
			var ipPort=document.URL.substr(document.URL.indexOf('//')+2,document.URL.indexOf('TFO')-9);
			var jspURL=sLocationOrigin+"/webdesktop/login/mailloginclient.jsf?CalledFrom=OPENWI&OAPDomHost="+ipPort+"&OAPDomPrt=https:&" +
			"ReferredFromSameOrigin=false&ReferredFromSameOrigin=false&CabinetName="+cabinetName+"&pid="+
			getValue(TRSD_WI_NAME)+"&wid=1&SessionId="+wd_uid+"&UserIndex="+data+"&UserName="+userName;
			console.log(jspURL);
			document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
			selectSheet("tab1",PM_TAB_IFRAME_ID);	
			//document.getElementById(BUTTON_SF_CLOSE).style.display="none";
			//enableFieldOnDemand('BTN_IFRAME_CLOSE');
			setStyle(BTN_IFRAME_CLOSE, PROPERTY_NAME.VISIBLE, 'true');
	}
		else if(controlName == BTN_IFRAME_CLOSE){
			var xmlobj;
            var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			var importUrl=sLocationOrigin+"/webdesktop/custom/UnlockWorkitem.jsp?pid="+getValue(TRSD_WI_NAME);	
            if(window.XMLHttpRequest)
			{
				xmlobj=new XMLHttpRequest();
			}
			xmlobj.open("POST",importUrl,false);
			xmlobj.send("sd");
			selectSheet("tab1",PM_TRSD_SHEET_ID);	
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
			disableFSKData();
	}
	else if(controlName == 'BTN_UTC_SCORE'){
		disableFieldOnDemand('BTN_UTC_SCORE');
	}	
		break;
	case EVENT_TYPE.CHANGE:
		break;
	case EVENT_TYPE.LOSTFOCUS: 
		if(!jsonData.success){
			console.log('message in post: '+jsonData.message);
		if((jsonData.message).includes('###')){
				var arr = jsonData.message.split('###');
				showMessage(arr[0], arr[1], 'error');
			}
		saveWorkItem();	 
		}	else if(controlName == 'LOAN_AMOUNT'){ 
			if(getValue('LOAN_AMOUNT') == ''||getValue('LOAN_AMOUNT') == '0'||getValue('LOAN_AMOUNT') == '0.00'||getValue('LOAN_AMOUNT') == '0.0') {
				setValue('LOAN_AMOUNT','');
				showMessage('LOAN_AMOUNT', 'Please enter loan amount', 'error');
				return false;
			}else{
				saveWorkItem();	
			}
		} 
		break;	
	}
}

function chkTransactionTenor(pControlName){
	var requestType = getValue(REQUEST_TYPE);
	if ('GA' == requestType) {
		console.log(' GA case ' + getValue(pControlName));
		disableFieldOnDemand('NEW_EXP_DATE,NEW_TRN_AMT');
		if ('OE' == getValue(pControlName)) {
			console.log('OE');
			setValues({[EXPIRY_COND]: ''}, true);//M
			setValues({[EXP_DATE]: ''}, true);
			disableFieldOnDemand(EXP_DATE);
			disableFieldOnDemand(EXPIRY_COND);
		} else if ('FD' == getValue(pControlName)) {
			console.log('FD');
			setValues({[EXPIRY_COND]: ''}, true);//M
			setValues({[EXP_DATE]: ''}, true);//M
			enableFieldOnDemand(EXP_DATE);
			disableFieldOnDemand(EXPIRY_COND);
		} else if ('COND' == getValue(pControlName)) {
			console.log('COND');
			setValues({[EXP_DATE]: ''}, true);//M
			enableFieldOnDemand(EXP_DATE);
			enableFieldOnDemand(EXPIRY_COND);
		}
	} else if('GAA' == requestType) {
		console.log('GAA' +getValue(pControlName));
		disableFieldOnDemand(EXP_DATE);
	}
}

function duplicateCheckJSP(){
	var wiName = getValue('WI_NAME');
	var wd_uid=getWorkItemData("sessionId");
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
	var jspURL=sLocationOrigin+'/TFO/CustomFolder/DuplicateCheck.jsp?sWiName='+wiName+'&session='+wd_uid+'&WD_UID='+wd_uid;
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	selectSheet("tab1",PM_TAB_IFRAME_ID);
	document.getElementById('sheet9_link').textContent = 'Duplicate Check';
	//returnVal=window.open('/TFO/CustomFolder/DuplicateCheck.jsp?sWiName='+wiName+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=300px;dialogHeight=300px');
}

function discDetailsJSP(){
	var wiName = getValue('WI_NAME');
	var wd_uid=getWorkItemData("sessionId");
	returnVal=window.open('/TFO/CustomFolder/Discreprancy_Details.jsp?WI_NAME='+wiName+'&SessionId='+wd_uid,'','dialogWidth=1000px');
}

function openLLI(parameterDocRvw)
{	
	var wd_uid=getWorkItemData("sessionId");
	var parameterJSP = parameterDocRvw.split('#'); 

	if(parameterJSP[0] == 'LLI_Integration_Calls')
	{
		returnVal=window.open('/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME='+parameterJSP[1]+'&VesselName='+parameterJSP[2]+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=1000px');
	}
	else if(parameterJSP[0] == 'LLI_BVD')
		returnVal=window.open('/TFO/CustomFolder/LLI_BVD.jsp?WI_NAME='+parameterJSP[1]+'&VesselName='+parameterJSP[2]+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=1000px');
	else if(parameterJSP[0] == 'LLI_OD')
		returnVal=window.open('/TFO/CustomFolder/LLI_OD.jsp?WI_NAME='+parameterJSP[1]+'&VesselName='+parameterJSP[2]+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=1000px');
	else if(parameterJSP[0] == 'LLI_MD')
		returnVal=window.open('/TFO/CustomFolder/LLI_MD.jsp?WI_NAME='+parameterJSP[1]+'&VesselName='+parameterJSP[2]+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=1000px');
	else if(parameterJSP[0] == 'LLI_GP'){
		returnVal=window.open('/TFO/CustomFolder/LLI_GEN_PDF.jsp?WI_NAME='+parameterJSP[1]+'&VesselDetails='+parameterJSP[2]+'&Username='+parameterJSP[3]+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=1000px');
	}
	return returnVal;
}
function disableInitCustFrm(){
	var activityName = getWorkitemData('activityName');
	var requestType = getValue('REQUEST_TYPE');
	var DISABLE_VD_FRAME = 'SOURCE_CHANNEL,RELATIONSHIP_TYPE,DELIVERY_BRANCH';
	var DISABLE_ID_LOV_FRAME = 'BILL_CUST_HLDING_ACC_US,BILL_RVSD_DOC_AVL,BILL_MODE_OF_PMNT,LC_DOC_COURIER,IFS_LOAN_GRP,REF_ICG_RM';
	var DISABLE_ID_TXT_FRAME = 'BILL_OUR_LC_NO,BILL_CORRSPNDNT_BNK,LC_CORRSPNDNT_BNK,LC_CONF_AMNT,GRNT_CHRG_ACC_TITLE,GRNT_CHRG_ACC_CRNCY';s
	var fields = 'GRNT_CHRG_ACC_TITLE,GRNT_CHRG_ACC_CRNCY';

	if(activityName!='Initiator' || activityName!='Logistics Team' || activityName!= 'Assignment Queue')
	{	
		if (activityName == 'PROCESSING MAKER')
		{
			if(requestType == 'GA' || requestType == 'GAA')
			{
				genericSetStyle(fields,'disable', 'true');
			}
			else
			{
				genericSetStyle(DISABLE_VD_FRAME,'disable', 'true');
				genericSetStyle(DISABLE_ID_LOV_FRAME,'disable', 'true');
				genericSetStyle(DISABLE_ID_TXT_FRAME,'disable', 'true');
			}			
		}
		if(activityName == 'PROCESSING CHECKER')
		{
			genericSetStyle(DISABLE_VD_FRAME,'disable', 'true');
			genericSetStyle(DISABLE_ID_LOV_FRAME,'disable', 'true');
			genericSetStyle(DISABLE_ID_TXT_FRAME,'disable', 'true');
		}
	}
}
function onRowClickPM(listviewId,rowIndex) { 
	console.log('listviewId='+listviewId+'rowIndex='+rowIndex);
	if(listviewId == 'Qvar_cpd_details'|| LISTVIEW_FFT_DETAIL == listviewId || 'FFT Desc' == listviewId ||
			'Qvar_Final_Desc_PPM' == listviewId||LISTVIEW_TRSD_TABLE_OPTIONAL == listviewId || listviewId == 'TAB_TSLM_LOAN_REF'){
		return true;
	}else if(listviewId == 'Qvar_Final_Desc_PPM'){
		setFocus(FINAL_DECISION_GRID_ADDITIONAL_MAIL);
		return true;
	}else if(listviewId == 'QVAR_utc_details'){
		executeServerEvent('startUTCDone',EVENT_TYPE.CLICK,'', false);
		return true;
	}
	/*else if(event.target.id == 'BTN_INVOICE_BROWSE'){
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
		var jspURL=sLocationOrigin+"/webdesktop/CustomFolder/TFO/UploadFile.jsp?WI_NAME="+wi_name;
		document.getElementById('IntegrationUploadInvoice').src=jspURL;	
		
	}*/
	return false;
}

function selectRowHookPM(tableId,selectedRowsArray,isAllRowsSelected){
	console.log('tableId='+tableId+'selectedRowsArray='+selectedRowsArray);
	var listviewId = tableId;
	var rowIndex = selectedRowsArray[0];
	console.log('row index = '+rowIndex);
	if(rowIndex >= 0){
		if(LISTVIEW_PARTY == listviewId){
			setValues({[PARTY_TYPE]: getValueFromTableCell(listviewId,rowIndex,0)}, true);
			setValues({[PARTY_DESC]: getValueFromTableCell(listviewId,rowIndex,0)}, true);
			setValues({[PD_PARTY_ID]: getValueFromTableCell(listviewId,rowIndex,2)}, true);
			setValues({[PD_CUSTOMER_NAME]: getValueFromTableCell(listviewId,rowIndex,3)}, true);
			setValues({[PD_ADDRESS1]: getValueFromTableCell(listviewId,rowIndex,4)}, true);
			setValues({[PD_ADDRESS2]: getValueFromTableCell(listviewId,rowIndex,5)}, true);
			setValues({[PD_ADDRESS3]: getValueFromTableCell(listviewId,rowIndex,6)}, true);
			setValues({[PD_ADDRESS4]: getValueFromTableCell(listviewId,rowIndex,7)}, true);
			setValues({[PD_COUNTRY]: getValueFromTableCell(listviewId,rowIndex,8)}, true);
			setValues({[MEDIA_TYPE]: getValueFromTableCell(listviewId,rowIndex,9)}, true);
			setValues({[PD_ADDRESS]: getValueFromTableCell(listviewId,rowIndex,10)}, true);
			enableFieldOnDemand(BUTTON_MODIFY_PARTY_DETAILS);  
			disableFieldOnDemand(BUTTON_ADD_PARTY_DETAILS);   
		}else if(LISTVIEW_CONTRACT_LIMIT == listviewId){
			setValues({[CL_SERIALNUMBER]: getValueFromTableCell(listviewId,rowIndex,0)}, true);
			setValues({[CL_COMBO_OPERATION]: getValueFromTableCell(listviewId,rowIndex,1)}, true);
			setValues({[CL_COMBO_PARTYTYPE]: getValueFromTableCell(listviewId,rowIndex,2)}, true);
			setValues({[CL_CUSTOMER_NO]: getValueFromTableCell(listviewId,rowIndex,3)}, true);
			setValues({[CL_COMBO_TYPE]: getValueFromTableCell(listviewId,rowIndex,4)}, true);
			setValues({[CL_LINKAGEREFNO]: getValueFromTableCell(listviewId,rowIndex,5)}, true);
			setValues({[CL_PER_CONTRIBUTION]: getValueFromTableCell(listviewId,rowIndex,6)}, true);
			setValues({[CL_COMBO_AMOUNTTAG]: getValueFromTableCell(listviewId,rowIndex,7)}, true);
			enableFieldOnDemand(BUTTON_MODIFY_CONTRACT_DETAILS);  
			disableFieldOnDemand(BUTTON_ADD_CONTRACT_DETAILS);
		}else if(LISTVIEW_LLI == listviewId){
			var shipmentDate=getValueFromTableCell(listviewId,rowIndex,2);
			if(shipmentDate==null||shipmentDate==''){
				//setValue('TXT_LLI_VESSELNAME',getValueFromTableCell(listviewId,rowIndex,1));
				setValues({['TXT_LLI_VESSELNAME']: getValueFromTableCell(listviewId,rowIndex,1)}, true);			
				disableFieldOnDemand(BUTTON_ADD_LLI);
				disableFieldOnDemand(VESSEL_DETAILS);
				disableFieldOnDemand(OWNERSHIP_DETAILS);
				disableFieldOnDemand(MOVEMENT_DETAILS);
			}else{
			setValues({[TXT_VESSELNAME]: getValueFromTableCell(listviewId,rowIndex,1)}, true);
			setValues({[SHIPMENT_DATE]: getValueFromTableCell(listviewId,rowIndex,2)}, true);
			setValues({['TXT_VESSELFLAG']: getValueFromTableCell(listviewId,rowIndex,3)}, true);
			setValues({['TXT_VESSELID']: getValueFromTableCell(listviewId,rowIndex,4)}, true);
			setValues({['TXT_VESSELIMO']: getValueFromTableCell(listviewId,rowIndex,5)}, true);
			disableFieldOnDemand(BUTTON_ADD_LLI);
			enableFieldOnDemand(VESSEL_DETAILS);
			enableFieldOnDemand(OWNERSHIP_DETAILS);
			enableFieldOnDemand(MOVEMENT_DETAILS);
			}
		}else if(LISTVIEW_TRSD_TABLE == listviewId){
			disableFieldOnDemand('delete_'+LISTVIEW_TRSD_TABLE_OPTIONAL);
			setValues({[ENTITY_ID]: getValueFromTableCell(listviewId,rowIndex,0)}, true);
			setValues({[ENTITY_NAME_ID]: getValueFromTableCell(listviewId,rowIndex,2)}, true);
			setValues({[TRSD_SCREENING_TYPE_ID]: getValueFromTableCell(listviewId,rowIndex,3)}, true);
			setValues({[TRSD_SCREENING_RESULT_ID]: getValueFromTableCell(listviewId,rowIndex,4)}, true);
			setValues({[CASE_ID]: getValueFromTableCell(listviewId,rowIndex,5)}, true);
			setValues({[CASE_NUMBER_ID]: getValueFromTableCell(listviewId,rowIndex,6)}, true);
			//setValues({[PENDING_IN_TRSD_GROUP_ID]: getValueFromTableCell(listviewId,rowIndex,7)}, true);
		    setValues({[SCREENING_DATE_ID]: getValueFromTableCell(listviewId,rowIndex,8)}, true);
            setValues({[ASSESSMENT_DATE_ID]: getValueFromTableCell(listviewId,rowIndex,9)}, true);
			setValues({[CASE_CLOSED_BY_ID]: getValueFromTableCell(listviewId,rowIndex,10)}, true);
			
			setValues({[CHANNEL_REFERENCE_NO_ID]: getValueFromTableCell(listviewId,rowIndex,11)}, true);
			//setValues({[REMARKS_ID]: getValueFromTableCell(listviewId,rowIndex,12)}, true);	
			
		}
	} else {
		console.log('row index null');
		if(LISTVIEW_PARTY == listviewId){
			clearTFOControls(FORM_CONTROLS_PARTY);
			disableFieldOnDemand(BUTTON_MODIFY_PARTY_DETAILS);  
			enableFieldOnDemand(BUTTON_ADD_PARTY_DETAILS);
			disableTabsForSwift();
		} else if(LISTVIEW_CONTRACT_LIMIT == listviewId){
			clearTFOControls(FORM_CONTROLS_CONTROL_LIMITS);
			disableFieldOnDemand(BUTTON_MODIFY_CONTRACT_DETAILS);  
			enableFieldOnDemand(BUTTON_ADD_CONTRACT_DETAILS);
		} else if(LISTVIEW_LLI == listviewId){ 
			enableFieldOnDemand(BUTTON_ADD_LLI);
			clearTFOControls(FORM_CONTROLS_LLI);
			disableFieldOnDemand(VESSEL_DETAILS);
			disableFieldOnDemand(OWNERSHIP_DETAILS);
			disableFieldOnDemand(MOVEMENT_DETAILS);
		}
	}

}
function checkPDCountAndModifyPartyGrid()
{
	console.log('check pd count');
	var selectedRows =getSelectedRowsIndexes(LISTVIEW_PARTY); 
	var iSelectedRow = selectedRows[0];
	console.log('btnModifyPartyDetails iSelectedRow'+iSelectedRow);
	if(iSelectedRow > -1){
		modifyPartyGridFromFields();     
	}
	else{
		showMessage(Qvar_Party_Details, 'Please select a row to modify', 'error');
	}
}

function modifyPartyGridFromFields(){
	var selectedRows =getSelectedRowsIndexes(LISTVIEW_PARTY); 
	var iSelectedRow = ''+selectedRows[0];

	setMultipleTableCellData(LISTVIEW_PARTY,[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': getValue(PARTY_TYPE)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'1','cellData': getSelectedItemLabel(PARTY_DESC)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'2','cellData': getValue(PD_PARTY_ID)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'3','cellData': getValue(PD_CUSTOMER_NAME)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'4','cellData': getValue(PD_ADDRESS1)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'5','cellData': getValue(PD_ADDRESS2)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'6','cellData': getValue(PD_ADDRESS3)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'7','cellData': getValue(PD_ADDRESS4)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'8','cellData': getValue(PD_COUNTRY)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'9','cellData': getValue(MEDIA_TYPE)},
	                                         {'rowIndex':iSelectedRow,'colIndex':'10','cellData': getValue(PD_ADDRESS)}],true); 
	clearTFOControls(FORM_CONTROLS_PARTY);
	disableFieldOnDemand(BUTTON_MODIFY_PARTY_DETAILS+',delete_Qvar_Party_Details');  
	enableFieldOnDemand(BUTTON_ADD_PARTY_DETAILS);
	disableTabsForSwift();
	setRowColorInListView(LISTVIEW_PARTY,iSelectedRow,'FFFFFF');
	setRowSelectionInListView(LISTVIEW_PARTY,[-1]);
}


function checkComplianceValidation(){
	if(getValue(COMP_POSITIVE_MATCH) == 'Y') {
		if(validateField('COMP_INST_BNK_NAME_CNTRY', 'Please enter instructing Bank name.')
				&& validateField('COMP_ADVS_BNK_NAME_CNTRY', 'Please enter Advising Bank name.')
				&& validateField('COMP_CUST_APP_NAME_CNTRY', 'Please enter Applicant name.')
				&& validateField('COMP_BENFC_NAME_CNTRY', 'Please enter Beneficiary name.')
				&& validateField('COMP_PRPS_OF_TXN', 'Please enter purpose of transaction.')
				&& validateField('COMP_ORGN_OF_GDS', 'Please enter origin of goods.')
				&& validateField('COMP_PORT_OF_LDING', 'Please enter port of landing.')
				&& validateField('COMP_PORT_OF_DSCHRG', 'Please enter port of discharge.')
				&& validateField('COMP_ON_BRD_DATE', 'Please enter on board date.')
				&& validateField('COMP_VESSEL_NAME', 'Please enter vessel name.')
				&& validateField('COMP_END_USE_OF_GDS', 'Please enter end use of goods.')
				&& validateField('COMP_NAME_ADDR_RUS_UKR', 'Please enter name of Russian/Ukranian entities.')
				&& validateField('COMP_IMB_PORT', 'Please enter IBM Report details.')) {
			console.log('Compliance info validation done');
			return true;
		}
		else {
			return false;
		}
	} else {
		return true;
	}
}

function duplicateCheckValidation(){
	var sIsDup = '',sDuplicateCheckConfirmation='';
	sIsDup = getValue('IS_DEDUP');
	sDuplicateCheckConfirmation = getValue(DUP_CHK_CONFIRMATION);
	console.log('Dup Check '+sIsDup+' Confirmation '+sDuplicateCheckConfirmation);
	if('Y' == (sIsDup)){
		if(sDuplicateCheckConfirmation == '0' ||sDuplicateCheckConfirmation == ''){
			showMessage(DUP_CHK_CONFIRMATION, 'Please select duplicate check confirmation', 'error');
			return false;
		}
		else{
			return true;
		}
	}else{
		return true;
	}
}

function validateVerifyDtlsTab() {
	if("Y" == PT_UTILITY_FLAG){  //PT 369
		return (validateField(SOURCE_CHANNEL, "Please Select Source Channel")
				&& validateField(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code"));
	}else 
		return (validateField(SOURCE_CHANNEL, "Please Select Source Channel")
				&& validateField(RELATIONSHIP_TYPE, "Please Select Transcation Branch Code")
				&& validateField(DELIVERY_BRANCH, "Please Select Transcation Delivery Branch"));
}

function validateMandatoryPDTab() { 
	if(validateField(PD_CUSTOMER_NAME, 'Please Enter Customer Name')  
			&& validateField(PD_PARTY_ID, 'Please Enter Party Id')  
			&& validateField(PARTY_TYPE, 'Please Select Party Type')
			&& validateField(PARTY_DESC, 'Please Enter Party Description')) {
		console.log('returning true1');
		return true;
	} 
	else{
		return false;
	}
}

function validatePartyExistAtModify(){
	var partyType=getValue('PARTY_TYPE');
	var selectedRows =getSelectedRowsIndexes(LISTVIEW_PARTY); 
	var iSelectedRow = selectedRows[0];
	if(partyType == getValueFromTableCell(LISTVIEW_PARTY, iSelectedRow, 0)){
		console.log('returning true2');

		return true;
	}
	console.log('returning true3');
	return validateIsPartyExist();
}
function validateIsPartyExist(){
	var partyType=getValue('PARTY_TYPE');
	var gridListName=LISTVIEW_PARTY;
	var length = getGridRowCount(gridListName);
	console.log('length '+length);
	for(var i=0;i<length;i++){ 
		console.log('getValueFromTableCell : '+getValueFromTableCell(gridListName, i, 0));
		if(partyType == getValueFromTableCell(gridListName, i, 0))
		{
			showMessage(LISTVIEW_PARTY, 'Party Type already exist', 'error');
			return false;
		}
	}
	console.log('returning true 4');
	return true;
}

function checkCLCountAndModifyContractGrid()
{
	var selectedRows =getSelectedRowsIndexes(LISTVIEW_CONTRACT_LIMIT); 
	var iSelectedRow = selectedRows[0];
	console.log('btnModifyPartyDetails iSelectedRow'+iSelectedRow);
	if(iSelectedRow > -1){
		modifyControlLimitGridFromFields();    
	}
	else{
		showMessage(Qvar_Party_Details, 'Please select a row to modify', 'error');
	}
}

function validateMandatoryCLTab() { 

	if(validateField('CL_SERIALNUMBER', 'Please Enter Serial Number') && validateField('CL_CUSTOMER_NO', 'Please Enter Customer No')
			&& validateField('CL_PER_CONTRIBUTION', 'Please Enter % Contribution') && validateField('CL_COMBO_OPERATION', 'Please Enter Operation')  
			&& validateField('CL_COMBO_TYPE', 'Please Enter Type')   && validateField('CL_COMBO_AMOUNTTAG', 'Please Enter Amount Tag')  
			&& validateField('CL_COMBO_PARTYTYPE', 'Please Select Party Type') && validateField('CL_LINKAGEREFNO', 'Please Enter Linkage Reference Number')) {
		return true;
	}
	else{
		return false;
	}
}

function modifyControlLimitGridFromFields(){
	var selectedRows =getSelectedRowsIndexes(LISTVIEW_CONTRACT_LIMIT); 
	var iSelectedRow = ''+selectedRows[0];

	/*	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'0',getValue(CL_SERIALNUMBER),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'1',getValue(CL_COMBO_OPERATION),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'2',getValue(CL_COMBO_PARTYTYPE),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'3',getValue(CL_CUSTOMER_NO),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'4',getValue(CL_COMBO_TYPE),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'5',getValue(CL_LINKAGEREFNO),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'6',getValue(CL_PER_CONTRIBUTION),false);
	setTableCellData(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'7',getValue(CL_COMBO_AMOUNTTAG),false); */

	setMultipleTableCellData(LISTVIEW_CONTRACT_LIMIT,[{'rowIndex':iSelectedRow,'colIndex':'0','cellData': getValue(CL_SERIALNUMBER)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'1','cellData': getValue(CL_COMBO_OPERATION)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'2','cellData': getValue(CL_COMBO_PARTYTYPE)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'3','cellData': getValue(CL_CUSTOMER_NO)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'4','cellData': getValue(CL_COMBO_TYPE)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'5','cellData': getValue(CL_LINKAGEREFNO)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'6','cellData': getValue(CL_PER_CONTRIBUTION)},
	                                                  {'rowIndex':iSelectedRow,'colIndex':'7','cellData': getValue(CL_COMBO_AMOUNTTAG)} ],true); 
	clearTFOControls(FORM_CONTROLS_CONTROL_LIMITS);
	disableFieldOnDemand(BUTTON_MODIFY_CONTRACT_DETAILS+',delete_Qvar_Contract_Limits');  
	enableFieldOnDemand(BUTTON_ADD_CONTRACT_DETAILS);
	setRowSelectionInListView(LISTVIEW_CONTRACT_LIMIT,'');
	setRowColorInListView(LISTVIEW_CONTRACT_LIMIT,iSelectedRow,'FFFFFF');
	setRowSelectionInListView(LISTVIEW_CONTRACT_LIMIT,[-1]);
}

function generateLLIPDF(){
	var gridSize = getGridRowCount(LISTVIEW_LLI);
	if(gridSize > 0) {
		for(var gridRow = 0; gridRow < gridSize; gridRow++){
			JspVesselParameters += getValueFromTableCell(LISTVIEW_LLI, gridRow, 1)+",";
		}
		var params = 'LLI_Integration_Calls#'+JspVesselParameters+'#'+getWorkItemData(userName);
		var resultLLI = openLLI(params);
		reloadAttachedDoc(resultLLI);
	}else{
		showMessage(LISTVIEW_LLI, 'Please add vessel details to the grid', 'error');
	}

}
function reloadAttachedDoc(responseText){
	customAddDoc("<?xml version=\"1.0\"?>"+responseText);
	//refreshDocumentFrame("<?xml version=\"1.0\"?>"+responseText);
}
function callFCUBSContractService(callContractType){	
	returnVal=window.showModalDialog("/TFO/CustomFolder/FCUBS_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&RequestType="+getValue('REQUEST_TYPE')+"&session="+wd_uid+"&WD_UID="+wd_uid+"&callRequestType="+callContractType,"","dialogWidth=1000px");
	return returnVal;	
}
function validateInvoiceTab(){
	var invoiceGridCount = getGridRowCount('QVAR_utc_details');
	var name;
	var currency;
	var invoice_no;
	var invoice_date;
	var reqCat=getValue(REQUEST_CATEGORY);
	var reqType=getValue(REQUEST_TYPE);
	var processing_system = getValue('PROCESSING_SYSTEM');
	var utcRequired = getValue('UTC_REQUIRED');
	//ATP - 204,205
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType) &&
			(processing_system == 'T' || processing_system == 'F') && utcRequired == 'Yes') {

	if(invoiceGridCount == 0){
		showMessage('QVAR_utc_details', 'Please add atleast one row in Invoice Details Grid', 'error');
		return false;
	}	else if(invoiceGridCount > 0){
			for(var i=0; i<invoiceGridCount; i++){
				name = 	getValueFromTableCell('QVAR_utc_details', i,2);
				currency = 	getValueFromTableCell('QVAR_utc_details', i,3);
				invoice_no = 	getValueFromTableCell('QVAR_utc_details', i,0);
				invoice_date = 	getValueFromTableCell('QVAR_utc_details', i,1);
				if(name == ''){
					showMessage('QVAR_utc_details', 'Invoice Currency cannot be blank for Invoice no '+invoice_no+'', 'error');
					return false;
				}
				else if(currency == ''){
					showMessage('QVAR_utc_details', 'Invoice amount cannot be blank for Invoice no '+invoice_no+'', 'error');
					return false;
				} 
				else if(invoice_no == ''){
					showMessage('QVAR_utc_details', 'Invoice Number cannot be blank', 'error');
					return false;
				} 
				else if(invoice_date == ''){
					showMessage('QVAR_utc_details', 'Invoice Date cannot be blank for Invoice no '+invoice_no+'', 'error');
					return false;
				} 
			   else if(invoiceGridCount> 200){
				 showMessage('QVAR_utc_details', 'Invoice should not be added more than 200 on this frame','error');
				 return false;				
			}
			 return true;
	}  return true;
	}
	}return true;
}

function saveAndNextPreHookPM(tabId){
	var setFlag = getValue('HIDDEN_START_FLAG');
	if(getSheetIndex('tab1')== '8')
	{
		var workstepName = getWorkItemData('activityName');

		if(workstepName==WORKSTEP.PROCESSING_MAKER){
			var rowCount  = getGridRowCount('QVAR_utc_details');
			var processing_system = getValue('PROCESSING_SYSTEM');
			var reqType = getValue(REQUEST_TYPE);
			var reqCat = getValue(REQUEST_CATEGORY);
			var utcRequire = getValue('UTC_REQUIRED');
			if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {
				if(!validateInvoiceTab()){
					return false;
				}if((setFlag == '')&&(utcRequire == 'Yes')){
					showMessage('UTC_START_CHECK', 'Please click on Start UTC','error');
					return false;
				}
			}if(getValue('IS_GETDOCUMENT_UTC_DONE') == 'N' || getValue('IS_GETDOCUMENT_UTC_DONE') == 'Y'){
				console.log('IS_GETDOCUMENT_UTC_DONE'+IS_GETDOCUMENT_UTC_DONE);
				saveWorkItem();
				executeServerEvent('IS_GETDOCUMENT_UTC_DONE',EVENT_TYPE.CLICK,'' , false);
			}
		}
	}
	var reqCat=getValue(REQUEST_CATEGORY); //ATP - 204,205
	var reqType=getValue(REQUEST_TYPE); //ATP - 454
	var swiftChannel=getValue('SWIFT_CHANNEL'); //ATP - 458 Shivanshu
	var response;
	var input=event.target.innerHTML+','+getSheetIndex('tab1')+','+getGridRowCount('Qvar_cpd_details');
	response=executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, true);
	var jsonData = handleTFOResponse(response);
	if (!jsonData.success){
		var jsonMessage=jsonData.message.split('###');
		if(jsonMessage[0]=='SKIP_TREASRY_FLAG'){
			if(reqCat =='IFS' || reqCat =='IFP' ||reqCat =='TL' ||reqCat =='IFA' || 'SCF' == reqCat){
				selectSheet("tab1",PM_TRSD_SHEET_ID);
			} else {
				selectSheet("tab1",PM_PARTY_SHEET_ID);
			}
		}
		return false;
	}else if(getSheetIndex('tab1')== 1){
		if((getValue('UTC_REQUIRED') == 'No')&& (getValue('UTC_REQUIRED_BRMS') == 'Yes') && (getValue('UTC_JSTIFICATION_REQUIRED') == '') )
		{ 
			showMessage('UTC_JSTIFICATION_REQUIRED', 'Justification for UTC cannot be blank', 'error');
			return false;
		}
		if(getValue('UTC_REQUIRED') == 'No'){
			disableFieldOnDemand('UTC_START_CHECK');
			setValue('HIDDEN_START_FLAG','true');
			setValue('IS_GETDOCUMENT_UTC_DONE','');
		}
		if(getValue('UTC_REQUIRED') == 'Yes' && getValue('HIDDEN_START_FLAG') == ''){
			enableFieldOnDemand('UTC_START_CHECK');
			setValue('HIDDEN_START_FLAG','');
			//setValue('IS_GETDOCUMENT_UTC_DONE','N');
		}
		
		//ATP-454 REYAZ 02-05-2024
		//START CODE
		if('TSLM Front End'== getValue(PROCESS_TYPE) && ('AM' == reqType)){
			if(getValue('INF_NEW_MATURITY_DATE') ==''){
				showMessage('INF_NEW_MATURITY_DATE"', 'New maturity date cannot be blank', 'error');
				return false;	
			}
			else if(!validateTSLMNewMaturityDate()){
				showMessage('INF_NEW_MATURITY_DATE"', 'New maturity date cannot be less than current date', 'error');
				return false;	
	}
			if(getValue('AMENDED_EFFECTIVE_DATE') ==''){
				showMessage('AMENDED_EFFECTIVE_DATE"', 'Amended Effective  date  cannot be blank', 'error');
				return false;	
			}
		}
		//END CODE
		
	}
	// ATP-134 REYAZ 10-05-2024 START
	if(getSheetIndex('tab1')== '3' && ('TL_AM' == reqType || 'AM' == reqType))
	{
		enableFieldOnDemand(BUTTON_SUBMIT);
		setDefaultCompliance();  // feature/stlam reyaz 26-07-2024
	}
	// ATP-134 REYAZ 10-05-2024 END
	//ATP-458 SHIVANSHU 16-07-24 START
	if (getSheetIndex('tab1')== '2' && swiftChannel == 'MT798'){
			//hideControls(PM_ILC_NI_SWIFT_MT798_HIDE);
			setStyle('COMP_CHK_DONE', PROPERTY_NAME.VISIBLE, 'false');
	}	
	//ATP-458 SHIVANSHU 16-07-24 END
	return true; 
}


function subFormPreHookPM(buttonId){
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PROCESSING_MAKER){
		if(buttonId==BUTTON_SUBMIT){

			return false;
		}
	}
}

function submitButton(){
	var response;
	var jsonData1;
	var alert;
	
	var reqCat=getValue(REQUEST_CATEGORY);
	var reqType=getValue(REQUEST_TYPE);
	saveWorkItem();
	// ATP-feature/scfmvp2.5 shahbaz 18-07-2024 start
	if(validateTslmFrontedRequestCat()){    
		const regex =/[^A-Za-z0-9\s.,]/;
		var remarksPM=getValue('REMARKS');
		if(regex.test(remarksPM)){
			showMessage('REMARKS', 'Special Characters are not allowed in Remarks', 'error');
		    return false;
		}
	}
	
	//  ATP-feature/scfmvp2.5 shahbaz 18-07-2024 end
	if('TXNC' == getValue(DEC_CODE) && TS_REQ_FIELD_CAT.includes(reqCat) && TS_REQ_FIELD_TYPE.includes(reqType)){
	    if(!validateField('IS_TS_REQUIRED', 'Please Select  Ts Required Combo')){
			return false;	
		}
	}
	if('TSLM Front End'== getValue(PROCESS_TYPE) && !('AM' == reqType || 'TL_AM' == reqType || 'STL' == reqType || 'TL_STL' == reqType)){
		if(!validateField('LOAN_VAL_DATE', 'Please Enter Loan Value Date')){
				return false;	
			}
	}
	
	if(('TSLM Front End'== getValue(PROCESS_TYPE))&&(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))){
			if(!validateField('EDAS_APPROVAL', 'PLEASE SELECT EDAS APPROVAL')){
				return false;	
			}
			
			if(getValue('EDAS_APPROVAL')=='Approved'){
				if(!validateField("LEGALIZATION_CHARGES","Please select LEGALIZATION CHARGES")){
					return false;
				}
			}
			if(getValue('EDAS_APPROVAL')=='Approved' || getValue('EDAS_APPROVAL')=='Rejected'){
				if(!validateField("EDAS_REFERENCE","Please enter EDAS Reference")){
					return false;
				}
			}
	}
	//ADDED BY Shivanshu 
		if(('BAU'== getValue(PROCESS_TYPE)) || ('PT'== getValue(PROCESS_TYPE))){
			if (('ELCB_AM' == reqType) || ('EC_AM' == reqType)  || ('ELCB_BL' == reqType) || ('EC_BL' == reqType)){
				if((getValue('BILL_RVSD_DOC_AVL') == '1') && (getValue('COURIER_COMPANY') == '')){
					showMessage('COURIER_COMPANY', 'Courier Company cannot be blank', 'error');
					return false;	
				} else if((getValue('BILL_RVSD_DOC_AVL') == '1') && (getValue('COURIER_COMPANY') != 'Not Applicable') 
				         && (getValue('COURIER_AWB_NUMBER') == '')){
					showMessage('COURIER_AWB_NUMBER', 'AWB Number cannot be blank', 'error');
					return false;	 
				}
			}
		}
	if('TXNAU' == getValue(DEC_CODE)){
		enableIndicator();
		response= executeServerEvent(BUTTON_SUBMIT, event.type, '###', true);
	}else if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType)) { //ATP - 204,205
		if(getValue('DEC_CODE') == 'REF' && getValue('IS_GETDOCUMENT_UTC_DONE') != '' && getValue('TRSD_FLAG') != 'Y' && getValue(COMP_REF) != 'Y' && getValue('TR_REFER_TREASURY') != 'Y'){
			//setValue(DEC_CODE,'');
			response= executeServerEvent('CheckDecision', event.type, '###', true);
			var flagUTC = getValue('IS_GETDOCUMENT_UTC_DONE');
			if(flagUTC == 'Y'){
			 jsonData1 =handleTFOResponse(response);
			 alert = jsonData1.message;
			 if(jsonData1.success){
				 if(jsonData1.success && !jsonData1.message == ''&& alert==jsonData1.message){
					 showMessage(alert,'Please select Decision','error');
					 return false;
				 }
			 }
		}
		}
		if(getValue('IS_GETDOCUMENT_UTC_DONE') == '' && getValue('UTC_REQUIRED') == 'Yes' ){
			setValues({['IS_GETDOCUMENT_UTC_DONE']: 'N'}, true);
		}			
		if(!validateInvoiceTab()){
			return false;
		} else {
			response= executeServerEvent(BUTTON_SUBMIT, event.type, '###', true);
		}
	}else if(validateField(TFO_BRANCH_CITY,MESSAGE.ERROR.ALERT_BRNCH_CITY)
			&& validateField(TFO_ASSIGNED_CENTER,MESSAGE.ERROR.ALERT_ASGN_CNTR) 
			&& validateField(CREATE_AMEND_CNTRCT_FCUBS,MESSAGE.ERROR.ALERT_CREATE_AMEND)){
		disableFieldOnDemand(BUTTON_SUBMIT);
		enableIndicator();
		if(checkComplianceValidation()){
			saveWorkItem();
			console.log('checkComplianceValidation returned true');
			response= executeServerEvent(BUTTON_SUBMIT, event.type, getGridRowCount(LISTVIEW_PARTY)+'###', true);
		}
	}
	
	var jsonData =handleTFOResponse(response);
	disableIndicator();
	var alert = jsonData.message;
	if (jsonData.success){
		if('TXNAU' == getValue(DEC_CODE)){
			enableFieldOnDemand(BUTTON_SUBMIT);
			saveWorkItem();	 
			completeWorkItem();
		} else if(!jsonData.message == ''&&('C'==jsonData.message ||'A'==jsonData.message)){
			var callRequestType = jsonData.message;
			if('C'==callRequestType ||'A'==callRequestType){
				setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
				var jspURL=sLocationOrigin+"/TFO/CustomFolder/FCUBS_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&callRequestType="+callRequestType;
				document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
				selectSheet("tab1",PM_TAB_IFRAME_ID);	 
				disableFieldOnDemand(BUTTON_SUBMIT);
			}

		}else if(!jsonData.message == ''&&'showNotificationAlert'==jsonData.message){
			showMessage(jsonData.message,'1. As DISCOUNT_ON_ACCEP is Yes, a discounting request work item will be automatically created, post completion of acceptance step.<br/>2. The auto created work item will be available in PPM queue and can be retrieved either by Web deskstop or in Dash board. Ensure to discount this bill','error');

		}
		else {
			enableFieldOnDemand(BUTTON_SUBMIT);
			saveWorkItem();	 
			completeWorkItem();
		}
	} else {
		enableFieldOnDemand(BUTTON_SUBMIT);
	}
}

function subformDoneClickPM(buttonId){
	if(buttonId==BUTTON_SUBMIT)
	{
		var response=closeandReturn('subForm_Final_Decision_ListView');
		window.opener.setPMSFData(buttonId,response);
	}
}
function setPMSFData(buttonId,response){
	if(buttonId==BUTTON_SUBMIT){
		console.log('response='+response);
		executeServerEvent(BUTTON_SF_CLOSE, 'click',response, false); 
	}
}


function customListViewValidationPM(controlId,flag){
	if(controlId== LISTVIEW_TRSD_TABLE_OPTIONAL){
		setValues({['TRSD_OTHER_EXECUTION_STATUS']: 'N'}, true);
		enableFieldOnDemand(BTN_START_TRSD);
	}
	if((flag == 'A') || ( flag == 'O')){
		if("Qvar_cpd_details" == controlId){

			var listCount = getGridRowCount(controlId);

			if(listCount >50){
				showMessage("", "Max 50 rows allowed for counter party details'", 'error');
				return false;
			}
			else{return true; }

		}	
	
		else if(controlId == 'QVAR_utc_details'){
			var listCount1 = getGridRowCount(controlId);
			if(flag == 'O') {
				if(listCount1 >200){
					showMessage("", "Max 200 rows allowed for invoice details'", 'error');
					return false;
				}else{
					return true; 
				}
				//return true;
			}
			
		}
	}
	if( flag == 'M'){
		if(controlId == 'QVAR_utc_details'){
			var listCount1 = getGridRowCount(controlId);
			if(listCount1 >200){
				showMessage("", "Max 200 rows allowed for invoice details'", 'error');
				return false;
			}else{
				return true; 
			}
			
		}
		else if("Qvar_Final_Desc_PPM" == controlId){
			var inputData=getGridRowCount('Qvar_Final_Desc_PPM');
			var response= executeServerEvent(FINAL_DECISION_GRID_ADDITIONAL_MAIL,EVENT_TYPE.LOSTFOCUS,inputData, true);
			var jsonData = handleTFOResponse(response);
			if (!jsonData.success){
				return false;
			}
			else{return true; }

		}	
		}

	return true;
}

function disableTabsForSwift(){
	var requestType = getValue(REQUEST_TYPE);
	if(("IC_MSM" == (requestType))||("EC_MSM" == (requestType))||("ILC_MSM" == (requestType))
			||("ELC_MSM" == (requestType))||("ILCB_MSM" == (requestType))||("ELCB_MSM" == (requestType))
			||("GRNT_MSM" == (requestType))||("TL_MSM" == (requestType))||("DBA_MSM" == (requestType))
			||("MISC_MSM" == (requestType))||("IFS_MSM" == (requestType))||("IFP_MSM" == (requestType))
			||("ELC_PA" == (requestType))||("ELCB_AOR" == (requestType))||("ILCB_AOD" == (requestType))
			||("ILCB_AOP" == (requestType))||("ILCB_AOD" == (requestType))||("SBLC_MSM" == (requestType))){ //RR

		disableFieldOnDemand(DISABLE_PC_PD_DETAILS);
		disableFieldOnDemand(DISABLE_PC_CONTRACT_LIMIT);
		disableFieldOnDemand("btnModifyPartyDetails,btnAddPartyDetails,btnPDFetch"); 
		setValues({['ROUTE_TO_PM']: 'ADCB Checker'}, true);
	}
} 

function onClickTabPM(tabId,sheetindex,eventCall){
	console.log('sheetindex='+sheetindex+'eventCall='+eventCall);	
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex id: '+event.target.id+'event.target.innerHTML='+event.target.innerHTML);
	var controlID=sheetindex+','+event.target.innerHTML;
	var input=sheetindex;
	console.log('onClickTabPPM');
	var utcRequire = getValue('UTC_REQUIRED');
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	if('SCF' == reqCat && ('PD' == reqType || 'PDD' == reqType)){      // ATP-409 SHIVANSHU
		 enableFieldOnDemand('UTC_REQUIRED');
	}
	if(eventCall == 2 && sheetindex==9) {
	//ATP - 204,205	
		if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType) &&
				utcRequire == 'Yes') {
			if(getValue('HIDDEN_START_FLAG') != 'true'){
				showMessage('UTC_START_CHECK', 'Please click on Start UTC','error');
				disableFieldOnDemand(BUTTON_SUBMIT);
				return false;
			} else {
				enableFieldOnDemand(BUTTON_SUBMIT);
			}
		}
	}
	if(eventCall == 2 && sheetindex==8) {
		if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType)) {
			if('Yes' == utcRequire && getValue('IS_GETDOCUMENT_UTC_DONE' == '')){
				enableFieldOnDemand('UTC_START_CHECK');
			}else if('No' == utcRequire){
				disableFieldOnDemand('UTC_START_CHECK');
				disableFieldOnDemand('BTN_UTC_SCORE');
			}
		}
	}
	if(eventCall == 2 ) 
	{
		saveWorkItem();
		var response=executeServerEvent('onClickTabPM', EVENT_TYPE.CLICK, input, true);
		var jsonData = handleTFOResponse(response);
		if (jsonData.success&&sheetindex==7){
			disableEnableTRSDData();
			disableFSKData();
		}
	}
	/*else if(eventCall == 1 && sheetindex==PM_TRESARY_SHEET_ID){ //save And next click and the next tab is treasry
		var response=executeServerEvent('onClickTabPM', EVENT_TYPE.CLICK, input, true);
		var jsonData = handleTFOResponse(response);
		if(!jsonData.success){
			selectSheet("tab1",PM_PARTY_SHEET_ID);
			return true;
		}
    }*/
	if((document.getElementById(JSP_FRAME_CONTRACT)!=null  &&document.getElementById('tab1').getElementsByClassName("tab-pane fade")[PM_TAB_IFRAME_ID].style.display!='none')
			&&sheetindex!=PM_TAB_IFRAME_ID && eventCall==2 ){
//		var sheetName=document.getElementById('sheet9_link').textContent ;

		showMessage('', 'Please click on close button', 'error');
		return false;
	}
	if(sheetindex == PM_TAB_IFRAME_ID){
		setStyle(BTN_IFRAME_CLOSE, PROPERTY_NAME.VISIBLE, 'false');
	}
	
    if (sheetindex == 4){
		setStyle('COMP_REF','disable', 'false');
		setStyle('COMP_EXP_REMARKS','disable', 'false');
		setDefaultCompliance();
		//ADDED BY SHIVANSHU ATP-458 16-07-24 START
		if ( getValue('SWIFT_CHANNEL') == 'MT798' && reqCat=='ILC' && (reqType=='ILC_NI' || reqType=='ILC_AM')){
			hideControls(PM_ILC_NI_SWIFT_MT798_HIDE);
			setValues({[COMP_CHK_DONE]: 'Y'}, true);
			setValues({[COMP_POSITIVE_MATCH]: 'N'}, true);
		}
		//ADDED BY SHIVANSHU ATP-458 16-07-24 END
	}
}

function disableEnableTRSDData(){
	var gridSize = getGridRowCount(LISTVIEW_TRSD_TABLE);
	//disableFieldOnDemand(LISTVIEW_TRSD_TABLE);
	var checkFlag=1;
	for(var i=0;i<gridSize;i++){
		checkFlag=checkFlag+1;
		console.log('checkFlag='+checkFlag);
		setRowsDisabled(LISTVIEW_TRSD_TABLE,[i]);
		setCellDisabled(LISTVIEW_TRSD_TABLE,i,3,"false");
		//setTableCellColor(LISTVIEW_TRSD_TABLE,i,2,"ffffff");
		//enableFieldOnDemand(LISTVIEW_TRSD_TABLE+'_'+checkFlag);
	}
}

function handleJSPResponse(typeOfJsp,finalResult){
	var workstepName=getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PROCESSING_MAKER){
		if(typeOfJsp == 'LLI')
		{
			if(finalResult == 'Success'){
				enableFieldOnDemand(VESSEL_DETAILS);
				enableFieldOnDemand(OWNERSHIP_DETAILS);
				enableFieldOnDemand(MOVEMENT_DETAILS);
				setRowSelectionInListView('Qvar_LLI_Details',[-1]);
			}else{
				disableFieldOnDemand(VESSEL_DETAILS);
				disableFieldOnDemand(OWNERSHIP_DETAILS);
				disableFieldOnDemand(MOVEMENT_DETAILS);
			}
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
			selectSheet("tab1",4);
			executeServerEvent(BUTTON_FETCH_LLI,EVENT_TYPE.CLICK,finalResult , false);
		} else if(typeOfJsp == 'FCUBS_INTEGRATION_CALLS'){
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
			selectSheet("tab1",9); 
			executeServerEvent(BUTTON_SF_CLOSE, EVENT_TYPE.CLICK,finalResult, false);

		} else if(typeOfJsp == 'LLI_PDF'){
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
			window.parent.customAddDoc("<?xml version=\"1.0\"?>"+finalResult);
			//refreshDocumentFrame("<?xml version=\"1.0\"?>"+finalResult);
		} else if(typeOfJsp == 'openRepairJSP'){
			var status=finalResult.split('@@@');
			if("N"==status[0]){
				showMessage('', 'Please retry the failed calls.', 'error');
			}else{
				if(status.length>=3 && status[2]!=''){
					refreshDocumentFrame(status[2]);
				}
				selectSheet("tab1",0); 
				window.parent.refreshWorkitem();
				setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
			}
		} else if(typeOfJsp=='ScreeningDetails'){
			if(finalResult.length>0){
				showMessage('',finalResult, 'error');
				if(finalResult=='Rows in Uploaded File Cannot Exceed 1000'){
					return false;
				} 
			} else {
				executeServerEvent('BTN_SCREENING_BROWSE', EVENT_TYPE.CLICK, '',false);
			}
		
		saveWorkItem();
		//window.parent.refreshWorkitem();
		}
	} else if(workstepName==WORKSTEP.PP_MAKER){
		if(typeOfJsp == 'openRepairJSP'){
			var status=finalResult.split('@@@');
			if("N"==status[0]){
				showMessage('', 'Please retry the failed calls.', 'error');
			}else{
				if(status.length>=3 && status[2]!=''){
					refreshDocumentFrame(status[2]);
				}
				selectSheet("tab1",0);
				window.parent.refreshWorkitem();
				setTabStyle("tab1",9, "visible", "false");
			}
		} else if(typeOfJsp == 'UTC'){
			if(finalResult == 'Success'){
				setValues({['IS_UTC_UPDATE_DOC_CALL']: 'Y'}, true);
			}
			selectSheet("tab1",1);
			setTabStyle("tab1",12, "visible", "false");
			enableFieldOnDemand(BUTTON_SUBMIT);
		}
	}	else if(workstepName==WORKSTEP.REPAIR_QUEUE){
		if(typeOfJsp == 'openRepairJSP'){
			var status=finalResult.split('@@@');
			if("N"==status[0]){
				showMessage('', 'Please retry the failed calls.', 'error');
			}else{
				setValues({[DEC_CODE]: 'APP'}, true);
				executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, '', false);
				/*
			  selectSheet("tab1",1); 
				setTabStyle("tab1",2, "visible", "false");
				//executeServerEvent(BUTTON_SF_CLOSE, 'click',status[1], false); 
                 enableFieldOnDemand(BUTTON_SUBMIT);
                 window.parent.refreshWorkitem();*/
			}
		}
	} else if(workstepName==WORKSTEP.PROCESSING_CHECKER){
		if(typeOfJsp == 'UTC'){
			if(finalResult == 'Success'){
				setValues({['IS_UTC_UPDATE_DOC_CALL']: 'Y'}, true);
			}
			selectSheet("tab1",9);
			setTabStyle("tab1",10, "visible", "false");
			enableFieldOnDemand(BUTTON_SUBMIT);
		}
	}else if(workstepName==WORKSTEP.PCPS){
		if(typeOfJsp == 'UTC'){
			if(finalResult == 'Success'){
				setValues({['IS_UTC_UPDATE_DOC_CALL']: 'Y'}, true);
			}
			selectSheet("tab1",1);
			setTabStyle("tab1",5, "visible", "false");
			enableFieldOnDemand(BUTTON_SUBMIT);
		}
	}else if(workstepName==WORKSTEP.LOGISTICS){
		if(typeOfJsp=='UTC_BRMS_Calls'){
		selectSheet("tab1",0);
		window.parent.refreshWorkitem();
		}else if(typeOfJsp == 'TRAYDSTREAM_INTEGRATION_CALLS'){
			setTabStyle("tab1",3, "visible", "false");
			selectSheet("tab1",0); 
			enableFieldOnDemand(BUTTON_SUBMIT);
		}
	}
}

function tableOperation(tableId,operationType){
	console.log('tableId' +tableId);
	console.log('operationType' +operationType);
	if( operationType == 'DeleteRow'){
		if(tableId == 'Qvar_LLI_Details') {
			if(confirm(LLI_ALERT)) {
				console.log('result true if condition');
				disableFieldOnDemand(VESSEL_DETAILS);
				disableFieldOnDemand(OWNERSHIP_DETAILS);
				disableFieldOnDemand(MOVEMENT_DETAILS);
				clearTFOControls(FORM_CONTROLS_LLI);
				return true;
			} else {
				console.log('result false if condition');
				return false;
			}
		}else if(tableId == LISTVIEW_TRSD_TABLE_OPTIONAL) {
			var array=getSelectedRowsDataFromTable(LISTVIEW_TRSD_TABLE_OPTIONAL);
			var reqType = getValue('REQUEST_TYPE');
		    var reqCat = getValue('REQUEST_CATEGORY');
			if(array[0][10]!='' && TS_REQ_CAT.includes(reqCat) && TS_REQ_TYPE.includes(reqType)){
				showMessage('',"Not available for deletion", 'error');
				return false;
			}else if(array[0][12]=='Y'){
				showMessage('',"Not available for deletion", 'error');
				return false;
			}else{
				return true;
			}
		}else if(tableId == 'QVAR_utc_details') {
			var array=getSelectedRowsDataFromTable('QVAR_utc_details');
			console.log('array[0][7]' +array[0][7]);
			if(array[0][7] != ''){
				showMessage('',"Not available for deletion", 'error');
				return false;
			}else{
				return true;
			}
		}
		else{
			console.log('table id else');
			return true;	
		}
	} else{
		console.log('delete row else');
		return true;	
	}
}
function onInvoiceDetailLoad(){
	var invoiceGridCount = getGridRowCount('QVAR_utc_details');
	var name;
	for(var i=0; i<invoiceGridCount; i++){
	name = 	getValue('table183_UTC_REF_NO');
	if(name == ''){
	enableFieldOnDemand('Invoice_No');
	enableFieldOnDemand('table183_INVOICE_DATE');
	enableFieldOnDemand('Invoice_Amount');
	enableFieldOnDemand('Invoice_Currency');
	enableFieldOnDemand('Supplier_Name');
	enableFieldOnDemand('Buyer_Name');
	enableFieldOnDemand('table64_INVOICE_DATE');
	}
	}
	console.log("UTC_ON_LOAD_INVOICE inside");
	if('TSLM Front End'== getValue(PROCESS_TYPE)){
			disableFieldOnDemand('table64_SHIP_TO_DELIVERY');
			disableFieldOnDemand('table64_SHIP_FROM_DELIVERY');
			disableFieldOnDemand('table64_VESSEL_NAME');
			disableFieldOnDemand('table64_GOOD_SERVICE_NAME');
			disableFieldOnDemand('table64_ORIGIN_GOOD_SERVICE');
			disableFieldOnDemand('table64_INVOICE_MATURITYDT');
			disableFieldOnDemand('table64_TRANSPORT_DOCDT');
			disableFieldOnDemand('table64_Invoice Acknowledgement Date');
		}
	console.log("name = "+name);
	executeServerEvent('UTC_ON_LOAD_INVOICE', 'click', '', false);
}

function addInvoiceDetSNO(){
	onInvoiceDetailLoad();
	customListViewValidationPM('QVAR_utc_details','O');
}
function deleteRowPostHook(tableId,rowIndices){
	saveWorkItem();
	if(LISTVIEW_PARTY == tableId){
		clearTFOControls(FORM_CONTROLS_PARTY);
		disableFieldOnDemand(BUTTON_MODIFY_PARTY_DETAILS);  
		enableFieldOnDemand(BUTTON_ADD_PARTY_DETAILS);   
	}else if(LISTVIEW_CONTRACT_LIMIT == tableId){
		disableFieldOnDemand(BUTTON_MODIFY_CONTRACT_DETAILS);  
		enableFieldOnDemand(BUTTON_ADD_CONTRACT_DETAILS);
		clearTFOControls(FORM_CONTROLS_CONTROL_LIMITS);
	}else if(LISTVIEW_LLI == tableId){
		enableFieldOnDemand(BUTTON_ADD_LLI);
		disableFieldOnDemand(VESSEL_DETAILS);
		disableFieldOnDemand(OWNERSHIP_DETAILS);
		disableFieldOnDemand(MOVEMENT_DETAILS);
		clearTFOControls(FORM_CONTROLS_LLI);
	}

}
function setDecision(){
	var reqCat=getValue(REQUEST_CATEGORY);
	var reqType=getValue(REQUEST_TYPE);
	var msgType=getValue(SWIFT_MESSAGE_TYPE);
	var processsingCenter=getValue(SWIFT_PROCESSING_STATUS);

	if(reqCat == 'ELC' &&  reqType == 'ELC_LCA' && (msgType == '700' || msgType == '710') &&  processsingCenter == 'P')
	{
		//setEmptyCombo(DEC_CODE,'TXNC');

		setValues({[DEC_CODE]: 'TXNC'}, true);
	}else if('ILC' == reqCat){
		
		if('ILC_UM'==reqType){		//RR
	    	showControls(FCUBS_PUR_OF_MSG);
	    	enableFieldOnDemand(FCUBS_PUR_OF_MSG);
	    }
	}
}

function deleteNACreateAmend(comboId){
	var count=getItemCountInCombo(comboId);
	var ptFlag;
	var NAFlag;
	console.log(count);
	for(var i=0;i<count;i++){
		var value=	getItemLabel(comboId,i);
		console.log(i+','+value);
		if("ProTrade/INC_SWIFT Txn"==value && "Y"==getValue(PT_UTILITY_FLAG)){
			ptFlag=i;
		}else if("NA"==value){
			NAFlag=i;
		}
	}
	console.log(ptFlag+','+NAFlag);
	if(NAFlag!=null){
		removeItemFromCombo(comboId,NAFlag);
	}
	if(ptFlag!=null){
		if(NAFlag!=null){
			ptFlag=ptFlag-1;
		}
		removeItemFromCombo(comboId,ptFlag);
	}
}
function clearDecision(comboId){
	var count=getItemCountInCombo(comboId);
	console.log(count);
	for(var i=0;i<count;i++){
		var value=getItemLabel(comboId,i);
		removeItemFromCombo(comboId,value);
	}
	
}

function refreshDocumentFrame(ngAddDocOutput){
	var arr = ngAddDocOutput.split('$$$');
	for(var i=0; i<arr.length; i++){
		console.log('refrsh doc '+i+' : '+arr[i]);
		var docFrameRef = window.parent.document.getElementById('docframe').contentWindow;
		docFrameRef.customAddDoc(arr[i]);
		docFrameRef.document.getElementById('docOptionsDiv').style.display='none';
	}
}

function onTableCellChangePM(rowIndex,colIndex,ref,controlId){
	console.log(rowIndex+colIndex+controlId);
    if(controlId ==LISTVIEW_TRSD_TABLE){
		setTableCellData(controlId,rowIndex,13,'N',true);
		enableFieldOnDemand(BTN_START_TRSD);
	}
}

function TSLMComplianceTabDisableEnable(){
	var reqCat=getValue(REQUEST_CATEGORY);
	var reqType=getValue(REQUEST_TYPE);
	//ATP - 204,205
	if(reqCat == 'IFS'|| reqCat == 'IFP'|| reqCat == 'IFA' || 'SCF' == reqCat){
		if(reqType == 'LD' || reqType == 'IFA_CTP' || 'PD' == reqType || 'STL' == reqType || 'AM' == reqType || 'PDD' == reqType){ //ATP-151 
			setTabStyle('tab1',4, 'visible', 'true');
			setTabStyle('tab1',5, 'visible', 'true');
			setTabStyle('tab1',7, 'visible', 'true');
			//ATP -434 REYAZ 20-03-2024
			//START CODE
			if('SCF' == reqCat && ('PD' == reqType || 'PDD' == reqType)){
				var trSellCur =getValue(TR_SELL_CUR);
				var trBuyCur =getValue(TR_BUY_CUR);
				if((trSellCur == trBuyCur) && trBuyCur!='' && trSellCur !=''){
					setTabStyle('tab1',5, 'visible', 'false');
				}
			}
			//END CODE
			
		}else{
			setTabStyle('tab1',4, 'visible', 'false');
			setTabStyle('tab1',5, 'visible', 'false');
			setTabStyle('tab1',7, 'visible', 'false');
		}
	}
}

function invoiceDuplicateCheckJSP(){
	var wiName = getValue('WI_NAME');
	var wd_uid=getWorkItemData("sessionId");
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
	var customer_id = getValue(CUSTOMER_ID);
	var jspURL=sLocationOrigin+'/TFO/CustomFolder/InvoiceDuplicateCheck.jsp?sWiName='+wiName+'&session='+wd_uid+'&WD_UID='+wd_uid+"&sCustID="+customer_id;
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	selectSheet("tab1",PM_TAB_IFRAME_ID);
	document.getElementById('sheet9_link').textContent = 'Duplicate Check';
	//returnVal=window.open('/TFO/CustomFolder/DuplicateCheck.jsp?sWiName='+wiName+'&session='+wd_uid+'&WD_UID='+wd_uid,'','dialogWidth=300px;dialogHeight=300px');
}

function tslmsControlsEnableDisable(){
	var requestCategory = getValue('REQUEST_CATEGORY');
	var reqType = getValue('REQUEST_TYPE');
	var processing_system = getValue(PROCESSING_SYSTEM);
	if(getValue(TSLM_LOAN_AMOUNT) == ''){
			if(('IFP' == requestCategory || 'IFS' == requestCategory || 'IFA' == requestCategory || 'SCF' == requestCategory) && (reqType == 'LD' || reqType == 'IFA_CTP' 
			|| 'PD' == reqType || 'PDD' == reqType)){//santhosh//krishna
		    setValues({[TSLM_LOAN_AMOUNT]:''},true);
		}else{
			setValues({[TSLM_LOAN_AMOUNT]:'0.0'},true); //25//11/21	
		}
	}
//ATP - 204,205
	if('IFS' == requestCategory || 'IFP' == requestCategory|| 'IFA' == requestCategory || 'SCF' == requestCategory){
		showControls('STANDALONE_LOAN,PROCESSING_SYSTEM');
		disableFieldOnDemand('TAB_TSLM_LOAN_REF,Q_TSLm_Counter_Dets');
		/*if(getValue(TSLM_LOAN_AMOUNT) == ''){
			setValues({[TSLM_LOAN_AMOUNT]:'0.0'},true); //20th Nov 21
		}*/
		if(processing_system == 'T'){		
			if(reqType == 'AM' || reqType == 'STL' || reqType == 'IFA_CTP' ){
				hideControls(TSLM_INST_TOGGLE);
				hideControls('SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				if(reqType == 'AM'){
					showControls('SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				}
				showControls('SEC_TSLM_LOAN_INPUT_DETAILS,SEC_TSLM_LOAN_REF_DET');
				showControls('LOAN_STAGE,FINANCED_AMOUNT,OVERALL_OUTSTANDING_AMOUNT,PROCESSING_SYSTEM');
				if( 'IFA' == requestCategory && reqType == 'IFA_CTP' ){
					showControls('SEC_TSLM_LOAN_INPUT_DETAILS,BTN_FETCH_TSLM_CID_DETAILS,TSLM_COMPANY_TYPE,LOAN_AMOUNT');
					showControls(B_S_CP_LOAD_ENABLE);
					enableFieldOnDemand('LOAN_AMOUNT');
				}
			} else if(reqType =='LD' || reqType =='PD'){//ATP - 204,205
				showControls('SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				showControls('FCUBS_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE,SEC_TSLM_LOAN_REF_DET,LOAN_AMOUNT');
				showControls('PROCESSING_SYSTEM,TSLM_ANY_PAST_DUE_CID,BTN_FETCH_TSLM_CID_DETAILS');
				enableFieldOnDemand('LOAN_AMOUNT');
				disableFieldOnDemand('FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE,PROCESSING_SYSTEM');//santhosh
				if( getGridRowCount('Q_TSLM_Invoice_No_SA_Loan')>0 && getValue(TSLM_STANDALONE_LOAN) =='1'){
					showControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,BTN_TSLM_INVOICE_CHK_CONFIRM');
					enableFieldOnDemand('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,BTN_TSLM_INVOICE_CHK_CONFIRM')//to be checked
				}
				if(  getValue(CREATE_AMEND_CNTRCT_FCUBS) =='2'){
					enableFieldOnDemand(TAB_TSLM_LOAN_REF);
				}
				setValues({['Toggle2']: 'true'}, true);
				showControls('SEC_TSLM_CUST_SPECIAL_INST');
			}
			showControls(SEC_TSLM_COUNTER_PARTY_DETAILS);
			disableFieldOnDemand(TSLM_STANDALONE_LOAN,SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_TSLM_INVOICE_NUMBERS_FOR_STANDALONE_LOAN);		
			disableFieldOnDemand('FCUBSConNo,BILL_LN_REFRNCE')
			//END OF RAKSHITA CODE
		} else if(processing_system == 'F'){
			enableFieldOnDemand(PROCESSING_SYSTEM);
			disableFieldOnDemand('STANDALONE_LOAN,BTN_FETCH_TSLM_CID_DETAILS,SEC_TSLM_REF_DET,LOAN_AMOUNT');
			hideControls('SEC_TSLM_REF_DET,SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_TSLM_LOAN_REF_DET,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
			hideControls('TSLM_DEDUPE_LABEL,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM,SEC_TSLM_CUST_SPECIAL_INST');
			hideControls('Toggle2,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE,BTN_FETCH_TSLM_CID_DETAILS,LOAN_AMOUNT');
			setValues({['STANDALONE_LOAN']: '1'}, true);
		}	
	}
	TSLMComplianceTabDisableEnable();
}


function enableLoanRefFields(){
	var Request_CAT = getValue(REQUEST_CATEGORY);
	var Request_TYPE = getValue(REQUEST_TYPE);
	var Processing_System = getValue(PROCESSING_SYSTEM);
	var createAmendValue = getValue("CREATE_AMEND_CNTRCT_FCUBS");//ATP - 204,205 shivanshu
	if ((Request_CAT == 'IFA'|| Request_CAT=='IFS'||Request_CAT=='IFP' || Request_CAT=='SCF')
			&& (Request_TYPE=='LD' || Request_TYPE=='PD' || 'PDD' == reqType)  && Processing_System=='T') {
		if(createAmendValue== 'N'){
			setStyle('LOAN_REF_TSLM_FIELD',PROPERTY_NAME.DISABLE,'false');//savechanges_TAB_TSLM_LOAN_REF
			setStyle('savechanges_TAB_TSLM_LOAN_REF',PROPERTY_NAME.DISABLE,'false');
		}else{
			setStyle('LOAN_REF_TSLM_FIELD',PROPERTY_NAME.DISABLE,'true');
		}
	}
}

function enableDisableFieldsPM_GRNT_NI()
{
	var processType=getValue(PROCESS_TYPE);
	var reqType = getValue(REQUEST_TYPE);
	if(reqType == 'NI' || reqType == 'SBLC_NI'|| reqType == 'ELC_SLCA'){
	if(processType == 'PT' || processType == 'ET'){   //ATP-469 Shahbaz 23-05-2024 
		showControls(PM_GRNT_NI_PT_SHOW);
		disableFieldOnDemand(PM_GRNT_NI_PT_DISABLE);
		enableFieldOnDemand(PM_GRNT_NI_PT_ENABLE);
	}
	else if(processType == 'BAU'){
		showControls(PM_GRNT_NI_BAU_SHOW);
		enableFieldOnDemand(PM_GRNT_NI_BAU_ENABLE);
	} 
	else if(processType == 'SWIFT'){
		showControls(PM_GRNT_NI_SWIFT_SHOW);
		disableFieldOnDemand(PM_GRNT_NI_SWIFT_DISABLE);
		enableFieldOnDemand(PM_GRNT_NI_SWIFT_ENABLE);
		//showSection('PT_GTEE_FRAME,PT_FFT_Frame,PT_Document_Detail'); //sheenu
		hideControls(PM_GRNT_NI_SWIFT_HIDE); //part of  PT_GTEE_FRAME
	}
	}
}
	function enableDisableFieldsPM_GRNT_AM()
	 {
	 	var processType=getValue(PROCESS_TYPE);
	 	if(processType == 'PT'){
	 		showControls(PM_GRNT_AM_PT_SHOW);
	 	}
	 	else if(processType == 'BAU'){
	 		showControls(PM_GRNT_AM_BAU_SHOW);
	 	} 
	 	else if(processType == 'SWIFT'){
	 		showControls(PM_GRNT_AM_SWIFT_SHOW);
	 	}
	 } 
	 function enableDisableFieldsPM_GRNT_GAA()
	 {
	 	var processType=getValue(PROCESS_TYPE);
	 	if(processType == 'PT'){
	 		showControls(PM_GRNT_GAA_PT_SHOW);
	 	}
	 	else if(processType == 'BAU'){
	 		showControls(PM_GRNT_GAA_BAU_SHOW);
	 	} 
	 	else if(processType == 'SWIFT'){
	 		showControls(PM_GRNT_GAA_SWIFT_SHOW);
	 	}
	 
}
	 function enableDisableFieldsPM_GRNT_GA()//santhosh
	 {
	 	var processType=getValue(PROCESS_TYPE);
	 	if(processType == 'SWIFT'){
	 		showControls(PM_GRNT_GA_SWIFT_SHOW);
	 		disableFieldOnDemand(PM_GRNT_GA_SWIFT_DISABLE);
	 	} 
	 	else if(processType == 'BAU'){
	 		showControls(PM_GRNT_GA_BAU_SHOW);
	 		enableFieldOnDemand(PM_GRNT_GA_BAU_ENABLE);
	 	}
	 }

	 
function setDefaultValuesPM(){
 	var requestCategory = getValue('REQUEST_CATEGORY');
 	var requestType = getValue('REQUEST_TYPE');
	if('GRNT' ==requestCategory){
		setDefaultValues_GRNT();
	}
	if('GRNT' ==requestCategory && 'GA'== requestType)
	{
		setValues({[COMBOX_TRN_THIRD_PARTY]: '2'}, true);	//RR
	}

}
function pmTslmValidation(){ // krishna
    
	 if('TSLM Front End'== getValue(PROCESS_TYPE) )
	 {
			setStyle('ASSET_ID', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('CUSTOMER_REFERENCE', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('FINANCE_PERCENTAGE', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('FOR_ACCOUNT_OF_FLAG', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('SUBCOMPANY_NAME', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('GOOD_SERVICE_UTIL_DESCRIPTION', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('PAY_CURRENCY', PROPERTY_NAME.VISIBLE, 'false');
			setStyle('PAY_AMOUNT', PROPERTY_NAME.VISIBLE, 'false');
			setStyle('ASSET_ID', PROPERTY_NAME.DISABLE, 'true');
			setStyle('CUSTOMER_REFERENCE', PROPERTY_NAME.DISABLE, 'true');
			setStyle('FINANCE_PERCENTAGE', PROPERTY_NAME.DISABLE, 'true');
			setStyle('FOR_ACCOUNT_OF_FLAG', PROPERTY_NAME.DISABLE, 'true');
			setStyle('SUBCOMPANY_NAME', PROPERTY_NAME.DISABLE, 'true');
			setStyle('GOOD_SERVICE_UTIL_DESCRIPTION', PROPERTY_NAME.DISABLE, 'true');
			setStyle('PAY_CURRENCY', PROPERTY_NAME.DISABLE, 'true');
			setStyle('PAY_AMOUNT', PROPERTY_NAME.DISABLE, 'true');
			setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.DISABLE, 'true');
			setStyle('TSLM_COMPANY_TYPE', PROPERTY_NAME.DISABLE, 'true');
			}
			else{
				setStyle('ASSET_ID', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('CUSTOMER_REFERENCE', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('FINANCE_PERCENTAGE', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('FOR_ACCOUNT_OF_FLAG', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('SUBCOMPANY_NAME', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('GOOD_SERVICE_UTIL_DESCRIPTION', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('PAY_CURRENCY', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('PAY_AMOUNT', PROPERTY_NAME.VISIBLE, 'false');
				setValue('ASSET_ID','');
				setValue('CUSTOMER_REFERENCE','');
				setValue('FINANCE_PERCENTAGE','');
				setValue('FOR_ACCOUNT_OF_FLAG','');
				setValue('SUBCOMPANY_NAME','');
				setValue('GOOD_SERVICE_UTIL_DESCRIPTION','');
			}
		}
function pmHybridcombo(){  //Krishna
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
 if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'CTP' == reqType) && ('TSLM Front End'== getValue(PROCESS_TYPE)))
		{
		setStyle('SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL', PROPERTY_NAME.VISIBLE, 'true');	
		}
		else{
			setStyle('SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL', PROPERTY_NAME.VISIBLE, 'false');
			setValue('SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL','');
		}
 if (('TSLM Front End'== getValue(PROCESS_TYPE)) && 	((reqCat == 'IFP' && reqType == 'LD') ||	(reqCat == 'IFA' && reqType == 'CTP'))){
		setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.VISIBLE, 'true');
		setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.DISABLE, 'true');
		
	} else{
	      
		setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.VISIBLE, 'false');
		setValue('LEGALIZATION_CHARGES_DETAIL','');
	}
	 if(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))
	{
		setStyle('EDAS_APPROVAL', PROPERTY_NAME.VISIBLE, 'true');	
		setStyle('LEGALIZATION_CHARGES', PROPERTY_NAME.VISIBLE, 'true');	
		setStyle('EDAS_REFERENCE', PROPERTY_NAME.VISIBLE, 'true');	
	} else{
		setStyle('EDAS_APPROVAL', PROPERTY_NAME.VISIBLE, 'false');	
		setStyle('LEGALIZATION_CHARGES', PROPERTY_NAME.VISIBLE, 'false');	
		setStyle('EDAS_REFERENCE', PROPERTY_NAME.VISIBLE, 'false');	
	}
		//ATP - 204,205	shivanshu
		//ATP-463 29-05-2024 --JAMSHED START
		/*if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType ))
	{
		setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.VISIBLE, 'true');	
		
	}else{
			setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.VISIBLE, 'false');	//ATP-463 29-05-2024 --JAMSHED
		}*/
		//ATP-463 29-05-2024 --JAMSHED END
}
	


function setDefaultValues_GRNT(){
 	var reqType=getValue('REQUEST_TYPE');
 	var processType=getValue(PROCESS_TYPE);

	if('GA' ==reqType && ('BAU'==processType ||'SWIFT'== processType)){
		setValues({[FCUBS_PUR_OF_MSG]: 'Issue'}, true);
		enableFieldOnDemand(FCUBS_PUR_OF_MSG);
		
	}

}

function setDefaultCompliance(){
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
    var validCategories = new Set(['IFP','IFS','IFA','SCF','IC', 'EC','ILCB','ELCB']);
    var validTypes = new Set(['AM','TL_AM','LD','IFA_CTP','PD','IC_BL','EC_BL','ILCB_BL','ELCB_BL','PDD','EC_BS','IC_BS','ILCB_BS','ELCB_BS']);
	 if(validCategories.has(reqCat) && validTypes.has(reqType)){ //ATP - 204,205
		 setStyle('COMP_CHK_DONE', PROPERTY_NAME.VISIBLE, 'false');
		 setStyle('COMP_POSITIVE_MATCH', PROPERTY_NAME.VISIBLE, 'false');
		 setValues({[COMP_IMB_CHK]: 'N'}, true);
		 setValues({[COMP_CHK_DONE]: 'Y'}, true);
		 setValues({[COMP_POSITIVE_MATCH]: 'N'}, true);
	 } else{
		 setStyle('COMP_CHK_DONE', PROPERTY_NAME.VISIBLE, 'true');
		 setStyle('COMP_POSITIVE_MATCH', PROPERTY_NAME.VISIBLE, 'true');		 
	 }
	if (('IFS' == reqCat || 'IFA' == reqCat || 'IC' == reqCat || 'EC' == reqCat || 'ILCB' == reqCat || 'ELCB' == reqCat) &&
	('LD' == reqType || 'EC_BS' == reqType || 'IC_BS' == reqType || 'ELCB_BS' == reqType || 'ILCB_BS' == reqType)){
		setValues({[LLI_CHK_OK]: 'X'}, true);
		setValues({[COMP_REF]: 'N'}, true);
		if("N" == getValue(COMP_REF)){
			disableFieldOnDemand('COMP_EXP_REMARKS');
		}
	}
	
}

function disableFSKData(){
	setStyle('TRSD_TABLE','disable','true');
	var gridSize = getGridRowCount(LISTVIEW_TRSD_TABLE_OPTIONAL);
	for(var i=0;i<gridSize;i++){
		// ATP-417 REYAZ 27-02-2024
		// START CODE
		if(getValueFromTableCell(LISTVIEW_TRSD_TABLE_OPTIONAL,i,1)!=''){
		if(getValueFromTableCell(LISTVIEW_TRSD_TABLE_OPTIONAL,i,3)!='' || getValueFromTableCell(LISTVIEW_TRSD_TABLE_OPTIONAL,i,10)!=''){
			console.log('disabled trsd field');
		setRowsDisabled(LISTVIEW_TRSD_TABLE_OPTIONAL,[i]);
	}
		}
		// START CODE END
	}
}

function showTsRequiredField() {
    var reqType = getValue(REQUEST_TYPE);
    var reqCat = getValue(REQUEST_CATEGORY);
    if (TS_REQ_FIELD_CAT.includes(reqCat) && TS_REQ_FIELD_TYPE.includes(reqType)) {
        showControls(TS_REQUIRED);
		if(getValue(DEC_CODE)=='TXNC'){
			enableFieldOnDemand(TS_REQUIRED);
		}
    }
}


function disableFetchUtcScore() {
    var invoiceGridCount = getGridRowCount('QVAR_utc_details');
	var utcScore;
	var utcRefNo;
	for(var i=0; i<invoiceGridCount; i++){
	     utcScore= getValueFromTableCell('QVAR_utc_details', i,11);
		 utcRefNo= getValueFromTableCell('QVAR_utc_details', i,7);
		  if(utcScore != '' || utcRefNo ==''){
             disableFieldOnDemand('BTN_UTC_SCORE');
		   }	
	}
}