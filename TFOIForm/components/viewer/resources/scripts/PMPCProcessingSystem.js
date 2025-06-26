var FRAME_PMPS_FINAL_DECISION='FRAME_PMPS_FINAL_DECISION';

function onPMPCProcessingSystemFormLoad(){
	setStyle(FRAME_CUST_DETAILS,"lock","true");
	setStyle(FRAME_REFERRAL_SUMMARY,"lock","true");
	setStyle(FRAME_PMPS_FINAL_DECISION,"lock","true");
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	var workstepName = getWorkItemData('activityName');
	//setTabStyle("tab1",3, "visible", "false");
	setTabStyle("tab1",4, "visible", "false");
	setTabStyle("tab1",5, "visible", "false");
	setTabStyle("tab1",3, "disable", "true");
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {
		setTabStyle("tab1",3, "visible", "true");
		setTabStyle("tab1",4, "visible", "true");
//		setTabStyle("tab1",5, "visible", "true");
		setStyle('UTC_REQUIRED_BRMS', PROPERTY_NAME.VISIBLE, 'true');  // Added by reyaz 5082022
		setStyle('UTC_CONVERTED_AMOUNT', PROPERTY_NAME.VISIBLE, 'true'); // Added by reyaz 5082022
		setStyle('UTC_REQUIRED', PROPERTY_NAME.VISIBLE, 'true');  // Added by reyaz 5082022
		setStyle('UTC_JSTIFICATION_REQUIRED', PROPERTY_NAME.VISIBLE, 'true'); // Added by reyaz 5082022
	}
	loadDeliveryTabValidation();
	clearReferralDecRemarks();
	//setValues({[DEC_CODE]: 'SBMT'}, true);
	tslmFormLoadPMSPCS();
	if(workstepName==WORKSTEP.SCC){
		setTabStyle("tab1",3, "visible", "true");
		setStyle('SYSTEM_ACTIVITY_NAME', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('LEGALIZATION_CHARGES', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('EDAS_APPROVAL', PROPERTY_NAME.VISIBLE, 'false');
		setStyle('EDAS_REFERENCE', PROPERTY_NAME.VISIBLE, 'false');
		//setValues({[DEC_CODE]: 'APP'}, true);
	}
	
	pmHybridcombo();
}

function handlePMPSEvent(object, event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickPMPSEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changePMPSEvent(event);
	}
}

function clickPMPSEvent(event)  {
	if (event.target.id == BUTTON_NEXT || event.target.id == BUTTON_PREVIOUS){
		deliveryTabNavigation(event.target.id);
	} else if (event.target.id == BUTTON_SUBMIT){
		var workstepName=getWorkItemData('activityName');
		if(workstepName==WORKSTEP.REPAIR_QUEUE){
			if(validateField('DEC_CODE', 'Please Select Decision'))	{
				executeServerEvent(event.target.id, event.type, '', false);
				}
		}else if(workstepName=='Legalization Queue'){
			if(submitEDASValidations()){
				executeServerEvent(event.target.id, event.type, '', false);
				completeWorkItem();
				}
		}else if(workstepName==WORKSTEP.SCC){
			if(validateField('DEC_CODE', 'Please Select Decision') && validateField('REASON_FOR_ACTION', 'Please Select Reason')){
				executeServerEvent(event.target.id, event.type, '', false);
				}
		}else if(submitPMPSValidations()){
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
				executeServerEvent(event.target.id, event.type, '', false);
		}else{
				saveWorkItem();
		}
	} else if (event.target.id == BUTTON_SAVE){
		saveWorkItem();
	} else if(event.target.id == TSLM_INST_TOGGLE_PMS_PCS){ //CODE BY MOKSH
		var toggleValue = getValue(TSLM_INST_TOGGLE_PMS_PCS)
		if(toggleValue == false){
			hideControls('SEC_TSLM_CUST_SPECIAL_INST');
		}else if(toggleValue == true){
			showControls('SEC_TSLM_CUST_SPECIAL_INST');
		}
	}
}

function changePMPSEvent(event)  {
	var workstepName = getWorkItemData('activityName');
	if(workstepName==WORKSTEP.SCC){
	if (event.target.id == DEC_CODE){
			executeServerEvent(event.target.id, event.type, '', false);
		}else if (event.target.id == 'REASON_FOR_ACTION'){
				if (getValue('REASON_FOR_ACTION')=='Others'){
					validateField('REMARKS', 'Please Provide Remarks')
				}
			}
	}
	var Edas = getValue('EDAS_APPROVAL');
	console.log("Edas"+Edas);
		if(Edas == 'Approved'){
	       setStyle('LEGALIZATION_CHARGES', PROPERTY_NAME.DISABLE,'false');
			setStyle('EDAS_REFERENCE', PROPERTY_NAME.DISABLE, 'false');
			}
}

function submitPMPSValidations(){
	console.log('inside submitPMPSValidations >>');
	if (validateField('DEC_CODE', 'Please Select Decision')
			&& validateField('SYSTEM_ACTIVITY_NAME', 'Please Select Target Activity Name')
			&& validateField('REASON_FOR_ACTION', 'Please Select Reason')){
		return true;
	} else{
		return false;
	}
}

function EDASValidations(){
	console.log('inside EDASValidations >>');
	if(('TSLM Front End'== getValue(PROCESS_TYPE))&&(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))){
		if (getValue('EDAS_APPROVAL')==''){
				 validateField('EDAS_APPROVAL', 'Please Select EDAS_APPROVAL')
			return 	true;
		}else{
			return false;
		}
	}else{
		return true;
	}
}


function submitEDASValidations(){
	EDASValidations();
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	console.log('inside submitEDASValidations >>');
	   if(('TSLM Front End'== getValue(PROCESS_TYPE))&&(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))){
			if(getValue('EDAS_APPROVAL')=='Approved'){
				if(validateField("LEGALIZATION_CHARGES","Please select LEGALIZATION CHARGES") &&
				    validateField("EDAS_REFERENCE","Please select EDAS_REFERENCE")){
				    return true;
				}else{
					return false;
				}
			}else if(getValue('EDAS_APPROVAL')=='Rejected'){
			    if(validateField("EDAS_REFERENCE","Please select EDAS_REFERENCE")){
					return true;
				}else{
					return false;
				}
			}else {
				return true;
			}
	}return true;
}

//CODE BY MOKSH
function tslmFormLoadPMSPCS(){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var workstepName = getWorkItemData('activityName');
	var processing_system = getValue(PROCESSING_SYSTEM);
	hideControls(TSLM_INST_TOGGLE_PMS_PCS);
	if (reqCat == 'IFS' || reqCat == 'IFP' || reqCat == 'IFA') {
		if (processing_system == 'T') {
			if (reqType == 'LD' || reqType == 'AM' || reqType == 'STL' || reqType == 'IFA_CTP' || reqType == 'IFP_CTP') {
				showControls('PROCESSING_SYSTEM,SEC_TSLM_LOAN_REF_DET,SEC_PS_SL_FINAL');
				if (reqType == 'LD') {
					showControls(TSLM_INST_TOGGLE_PMS_PCS + ','+ TSLM_COMPANY_TYPE + ',FRM_DUPE');
					var toggleValue = getValue(TSLM_INST_TOGGLE_PMS_PCS)
					if (toggleValue == false) {
						hideControls('SEC_TSLM_CUST_SPECIAL_INST');
					} else if (toggleValue == true) {
						showControls('SEC_TSLM_CUST_SPECIAL_INST');
					}
					setValues({['Toggle1']: 'true'}, true);
					showControls('SEC_TSLM_CUST_SPECIAL_INST');
				}
			}
			if (workstepName == 'PC Processing System') {
				showControls(TSLM_STANDALONE_LOAN + ','+ CREATE_AMEND_CNTRCT_FCUBS);
			}
		}
	}
}

function handleJSPResponsePMPC(typeOfJsp,finalResult){
	//link workitem jsp handling
	var workstepName=getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PCPS || workstepName==WORKSTEP.PMPS){
		if(typeOfJsp == 'UTC'){
		selectSheet("tab1",1);
		saveWorkItem();
		setTabStyle("tab1",5, "visible", "false");
		}
	}
}

function UTCUpdateDocStatusPCPS(){
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {

		if(getValue('IS_UTC_UPDATE_DOC_CALL') != 'Y'){
			var wd_uid=getWorkItemData("sessionId");
			setTabStyle("tab1",5, "visible", "true");
			var wi_name = getWorkItemData('processInstanceId');
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			var jspURL=sLocationOrigin+"/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&VesselName=updateDocumentStatusUTC&session="+wd_uid+"&WD_UID="+wd_uid;
			
			document.getElementById('JSP_Frame_UTC_Contract').src=jspURL;
			selectSheet("tab1",5);	
			document.getElementById(BUTTON_SF_CLOSE).style.display="none";
		}
	} 
}

function onClickTabPMPC(tabId,sheetindex,eventCall){
	console.log('sheetindex='+sheetindex+'eventCall='+eventCall);	
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex id: '+event.target.id+'event.target.innerHTML='+event.target.innerHTML);
	var controlID=sheetindex+','+event.target.innerHTML;
	var input=sheetindex;
	console.log('onClickTabPMPC');
	if((document.getElementById('JSP_Frame_UTC_Contract')!=null  &&document.getElementById('tab1').
			getElementsByClassName("tab-pane fade")[5].style.display!='none')
			&&sheetindex!=5 && eventCall==2 ){
		showMessage('', 'Please click on close button', 'error');
		return false;
	}
}
function postServerEventHandlerPCPM(controlName, eventType, responseData) {
	var jsonData = handleTFOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	
	switch (eventType) {
		case EVENT_TYPE.LOAD: 
			var reqType = getValue(REQUEST_TYPE);
			var reqCat = getValue(REQUEST_CATEGORY);	
		if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {
			setTabStyle("tab1",4, "visible", "true");
			setTabStyle("tab1",4, "disable", "true");
		}
		break;
	case EVENT_TYPE.CLICK:	
		var utcRequired = getValue('UTC_REQUIRED');
		if(!jsonData.success){
			console.log('message in post: '+jsonData.message);
			if((jsonData.message).includes('###')){
				var arr = jsonData.message.split('###');
				showMessage(arr[0], arr[1], 'error');
				return false;
			}
		}
		else if(controlName == BUTTON_SUBMIT && 'updateDocStatus'==jsonData.message && getValue('IS_UTC_UPDATE_DOC_CALL') != 'Y' && 'ARDD' == getValue('SYSTEM_ACTIVITY_NAME') && utcRequired == 'Yes' && getValue('IS_GETDOCUMENT_UTC_DONE') == 'Y'){
			UTCUpdateDocStatusPCPS();
		}
		 else if(BUTTON_SUBMIT  == controlName){
			saveWorkItem();
			completeWorkItem();
		}
		break;
	case EVENT_TYPE.CHANGE:
		break;
	case EVENT_TYPE.LOSTFOCUS: 				
		break;	
	}
}

function pmHybridcombo(){  //Krishna
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	
	if(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))
	{
		setStyle('EDAS_APPROVAL', PROPERTY_NAME.VISIBLE, 'true');	
		setStyle('LEGALIZATION_CHARGES', PROPERTY_NAME.VISIBLE, 'true');	
		setStyle('EDAS_REFERENCE', PROPERTY_NAME.VISIBLE, 'true');	
	}else{
		setStyle('EDAS_APPROVAL', PROPERTY_NAME.VISIBLE, 'false');	
		setStyle('LEGALIZATION_CHARGES', PROPERTY_NAME.VISIBLE, 'false');	
		setStyle('EDAS_REFERENCE', PROPERTY_NAME.VISIBLE, 'false');	
	}
}