var productSaveClick = false;
var lostFocusProduct = false;
var sHiddenEida;
function onFormLoad(){ 
	console.log('***** inside AO onFormLoad *****');
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	setStyle("trsd_frame", "visible", "false"); 
	if(isReadOnlyForm){
			onLoadQueryWorkStep();
	} else if (workstepName == WORKSTEPS.INTRODUCTION){
		onIntroductionLoad();
	} else if (workstepName == WORKSTEPS.ACCOUNT_RELATION){
		onAcctRelFormLoad();
	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_TEAM) {
		onCCTFormLoad();
	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_CPD) {
		onCPDFormLoad();
	}  else if (workstepName == WORKSTEPS.APP_ASSESSMENT) {
		onAppassesmentLoad();
	} else if (workstepName == WORKSTEPS.DDE_CUSTOMER_INFO || workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		onDDECustAccountInfoLoad();
	} else if (workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
		onQDECustAccountInfoLoad();
	} else if (workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK) {
		onDDECheckerLoad();
	} else if(workstepName== WORKSTEPS.CUST_SCREEN || workstepName== WORKSTEPS.CUSTOMER_SCREEN_QDE){
		onCustScreeningLoad();
	} else if (workstepName == WORKSTEPS.LEVEL_11) {
		onLevelsLoad();
	} else if (workstepName == WORKSTEPS.LEVEL_12) {
		onLevelsLoad();
	} else if (workstepName == WORKSTEPS.LEVEL_13) {
		onLevelsLoad();
	} else if (workstepName == WORKSTEPS.LEVEL_14) {
		onLevelsLoad();
	} else if (workstepName == WORKSTEPS.PHYSICAL_RECON) {
		onLoadPhysicalReconciliation();
	} else if (workstepName == WORKSTEPS.COMP_APP) {
		onLoadReferrals();
	} else if (workstepName == WORKSTEPS.PBG_VIGILANCE) {
		document.getElementById('sheet73_link').innerText = 'Vigilance'; 
		onLoadReferrals();
	} else if (workstepName == WORKSTEPS.MAIL_ROOM) {
		onLoadMailRoomOperation();
	} else if (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
		onQDECheckerLoadJs();
	} else if(workstepName == WORKSTEPS.CPD_MAKER){
		var sourceChannel = getValue(SOURCING_CHANNEL);
		if(sourceChannel == 'LAPS'){
			//setTabStyle("tab3",17, "visible", "true");
			setTabStyle("tab3",18, "visible", "true");// Changes for Family Banking
		}else{
			//setTabStyle("tab3",17, "visible", "false");
			setTabStyle("tab3",18, "visible", "false");// Changes for Family Banking
		}
		var scan_mode = getValue('SCAN_MODE');
		if(scan_mode ==  'New WMS ID'){
			onLoadCPDMakerThreeStep();
		}else{
			onLoadCPDMakerFourStep();
		}
		
	} else if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM){
		onCPDBulkEODCheckerFormLoad();
	} else if (workstepName == WORKSTEPS.COMP_APP) {
		 return onLoadReferrals();
	} else if (workstepName == WORKSTEPS.REPAIR) {
		onLoadRepair();
	} else if (workstepName == WORKSTEPS.BULK_EOD_CHECKER) {
		onCheckerHistoryLoad();
	} else if (workstepName == WORKSTEPS.DELIVERY_CHECKER) {
		onLoadDeliveryChecker();
	} else if (workstepName == WORKSTEPS.DELIVERY_MAKER) {
		onLoadDeliveryMaker();
	} else if (workstepName == WORKSTEPS.FB_MAKER) {
		onFBMakerLoad();
	} else if (workstepName == WORKSTEPS.FB_CHECKER) {
		onFBCheckerLoad();
	}  else if (workstepName == WORKSTEPS.TRSD_ACTION) {
		var scan_mode = getValue('SCAN_MODE');
		if(scan_mode ==  'New WMS ID'){
			openTrsdJspCPD();
		}else{
			openTrsdJsp();
		}
	} 
	//added for fullfilment
	var mode = getValue('DATA_ENTRY_MODE');
	if (mode == 'Quick Data Entry'){
		//setTabStyle("tab4",14, "visible", "false");
	} else if (mode == 'Detail Data Entry'){
		setTabStyle("tab3",20, "visible", "false");
	}
	
	if (formName == 'IFORMCPD') {
		setTabStyle("tab3",21, "visible", "false");
	}
	//End
	console.log('WorkItem Name: ' + wi_name);
	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	console.log('AFTER LOAD: ' + wi_name);
}


//function handleEvent(object, event){
//	console.log('Event: ' + event.target.id + ', ' + event.type);
//	var workstepName = getWorkItemData('activityName');
//	if(workstepName == WORKSTEPS.INTRODUCTION){
//		handleEventIntro(event);
//	} else if(workstepName == WORKSTEPS.ACCOUNT_RELATION){
//		handleEventAcctRel(event);
//	} else if (workstepName == WORKSTEPS.DDE_CUSTOMER_INFO) {
//		handleDDEEvent(event);
//	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
//		handleDDECheckerEventJs(event);
//	} else if(workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO){
//		handleQDEEvent(event);
//	} else if (workstepName == WORKSTEPS.CUST_SCREEN || workstepName== WORKSTEPS.CUSTOMER_SCREEN_QDE){
//		handleCustScreeningEvent(event);
//	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_TEAM) {
//		handleCCTEvent();
//	} else if (workstepName == WORKSTEPS.LEVEL_11){
//		handleLevelsEvent(event);
//	} else if (workstepName == WORKSTEPS.LEVEL_12){
//		handleLevelsEvent(event);
//	} else if (workstepName == WORKSTEPS.LEVEL_13){
//		handleLevelsEvent(event);
//	} else if (workstepName == WORKSTEPS.LEVEL_14){
//		handleLevelsEvent(event);
//	}
//}

function handleAOEvent(object, event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(isReadOnlyForm){
		handleQueryWorkStepEvent(event);
	} else if(event.target.id == (BTN_PRD_SEARCH) || event.target.id == 'product_search') {
		showMessage('', 'Selecting new product will route to Sanction Screening Tab to recalculate Risk','error');
		var scan_mode = getValue('SCAN_MODE');
		var wi_name = getWorkItemData('processInstanceId');
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var jspURL = sLocationOrigin+'/AO/CustomFolder/product_list.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+
		getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
		if(workstepName == WORKSTEPS.CPD_MAKER) {
			if(scan_mode ==  'New WMS ID'){
				jspURL = sLocationOrigin+'/AO/CustomFolder/product_list.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+
				getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED_CPD';
			}
		}
		document.getElementById('PRODUCT_JSP').src=jspURL;
		if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
			/*setTabStyle("tab3",18, "visible", "true");
			selectSheet('tab3',18);*/
			setTabStyle("tab3",19, "visible", "true");
			selectSheet('tab3',19); // Changes for Family Banking
		} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
			/*setTabStyle("tab4",12, "visible", "true");
			selectSheet('tab4',12);*/
			setTabStyle("tab4",13, "visible", "true");// Changes for Family Banking
			selectSheet('tab4',13); 
		} else if(workstepName == WORKSTEPS.CPD_MAKER) {
			/*setTabStyle("tab3",19, "visible", "true");
			selectSheet('tab3',19);*/
			setTabStyle("tab3",20, "visible", "true");
			selectSheet('tab3',20);// Changes for Family Banking
			/*if(scan_mode ==  'New WMS ID'){
				setTabStyle("tab3",19, "visible", "true");
				selectSheet('tab3',19);
			}*/
		}
	} else if(event.target.id == BTN_DOC) {
		if(getSelectedRowsIndexes(DOC_REQ_QUEUE).length == 0) {
			showMessage(DOC_REQ_QUEUE, 'Please select any one document from Documentation Required Grid', 'error');
			return;
		}
		var selectedRows = getSelectedRowsIndexes(DOC_REQ_QUEUE); 
		var folderIndex = getValueFromTableCell(DOC_REQ_QUEUE, selectedRows[0], 9);
		var docIndex = getValueFromTableCell(DOC_REQ_QUEUE, selectedRows[0], 10);
		if(folderIndex == '') {
			showMessage(DOC_REQ_QUEUE, 'Folder index not found for selected document', 'error');
			return;
		}
		/*var jspURL = "http://10.101.109.52:8080/omnidocs/integration/foldView/advanceFolderView.jsp?Application="
			+"EDMS&FolderIndex="+folderIndex+"&sessionIndexSet=false&UserName=supervisor&UserPassword=Mnblkj@707"
			+"&sDocumentIndex="+docIndex;*/
		var jspURL = "http://10.101.109.52:8080/omnidocs/integration/foldView/advanceFolderView.jsp?"
			+"OD_UID=-4638926570523865414&Application=WMS_BWD&cabinetName=addms001&enableDCInfo=true"
			+"&DataClassName=DC_CD_BWD&sessionIndexSet=false&FolderIndex="+folderIndex+"&sDocumentIndex="+docIndex;
		console.log('jspURL: '+jspURL);
		document.getElementById('PRODUCT_JSP').src=jspURL;
		if(workstepName == WORKSTEPS.CPD_MAKER) {
			/*setTabStyle("tab3",19, "visible", "true");
			selectSheet('tab3',19);*/
			setTabStyle("tab3",20, "visible", "true");// Changes for Family Banking
			selectSheet('tab3',20);
		} else {
			/*setTabStyle("tab3",18, "visible", "true");
			selectSheet('tab3',18);*/
			console.log("Changes for Family Banking workstepName : "+workstepName);
			setTabStyle("tab3",19, "visible", "true");
			selectSheet('tab3',19);// Changes for Family Banking  
		}
	} else if(event.target.id == BTN_DOC_CPD) {
		if(getSelectedRowsIndexes(DOC_REQ_QUEUE_CPD).length == 0) {
			showMessage(DOC_REQ_QUEUE_CPD, 'Please select any one document from Documentation Required Grid', 'error');
			return;
		}
		var selectedRows = getSelectedRowsIndexes(DOC_REQ_QUEUE_CPD); 
		var folderIndex = getValueFromTableCell(DOC_REQ_QUEUE_CPD, selectedRows[0], 9);
		var docIndex = getValueFromTableCell(DOC_REQ_QUEUE_CPD, selectedRows[0], 10);
		if(folderIndex == '') {
			showMessage(DOC_REQ_QUEUE_CPD, 'Folder index not found for selected document', 'error');
			return;
		}
		var jspURL = "http://10.101.109.52:8080/omnidocs/integration/foldView/advanceFolderView.jsp?"
			+"OD_UID=-4638926570523865414&Application=WMS_BWD&cabinetName=addms001&enableDCInfo=true"
			+"&DataClassName=DC_CD_BWD&sessionIndexSet=false&FolderIndex="+folderIndex+"&sDocumentIndex="+docIndex;
		console.log('jspURL: '+jspURL);
		document.getElementById('PRODUCT_JSP').src=jspURL;
		/*setTabStyle("tab3",19, "visible", "true");
		selectSheet('tab3',19);*/
		setTabStyle("tab3",20, "visible", "true");// Changes for Family Banking
		selectSheet('tab3',20);
	} else if(event.type == EVENT_TYPE.LOSTFOCUS && (event.target.id == 'table94_prod_code' 
		|| event.target.id == 'table130_prod_code' || event.target.id == 'table103_prod_code')
		&& getValue(event.target.id) != '' && !productSaveClick) {
		if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO 
				|| workstepName == WORKSTEPS.QDE_ACCOUNT_INFO || workstepName == WORKSTEPS.CPD_MAKER) {
			console.log('Inside PRODUCT_QUEUE customListViewValidation');
			executeServerEvent(PRODUCT_QUEUE, EVENT_TYPE.CLICK, '', true);	
		}
	} else if(event.type == EVENT_TYPE.LOSTFOCUS && (event.target.id == 'table94_prod_code' 
		|| event.target.id == 'table130_prod_code' || event.target.id == 'table103_prod_code')
		&& getValue(event.target.id) == '') {
		showMessage('', 'Product code cannot be blank', 'error');
	} else if(workstepName == WORKSTEPS.INTRODUCTION){
		handleEventIntro(event);
	} else if(workstepName == WORKSTEPS.ACCOUNT_RELATION){
		handleEventAcctRel(event);
	} else if (workstepName == WORKSTEPS.DDE_CUSTOMER_INFO || workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		handleDDEEvent(event);
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
		handleDDECheckerEventJs(event);
	} else if(workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO){
		handleQDEEvent(event);
	} else if (workstepName == WORKSTEPS.CUST_SCREEN || workstepName== WORKSTEPS.CUSTOMER_SCREEN_QDE){
		handleCustScreeningEvent(event);
	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_TEAM) {
		handleCCTEvent(event);
	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_CPD) {
		handleCPDEvent(event);
	} else if (workstepName == WORKSTEPS.LEVEL_11){
		handleLevelsEvent(event);
	} else if (workstepName == WORKSTEPS.LEVEL_12){
		handleLevelsEvent(event);
	} else if (workstepName == WORKSTEPS.LEVEL_13){
		handleLevelsEvent(event);
	} else if (workstepName == WORKSTEPS.LEVEL_14){
		handleLevelsEvent(event);
	} else if (workstepName == WORKSTEPS.APP_ASSESSMENT){
		handleAppassesmentEvent(event);
	}else if (workstepName == WORKSTEPS.PHYSICAL_RECON){
		handlePhysicalReconciliationEvent(event);
	} else if (workstepName == WORKSTEPS.COMP_APP || workstepName == WORKSTEPS.PBG_VIGILANCE){
		handleReferralsEvent(event);
	} else if (workstepName == WORKSTEPS.MAIL_ROOM){
		handleMailRoomOperationEvent(event);
	} else if (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
		handleQDECheckerEventJs(event);
	} else if(workstepName == WORKSTEPS.CPD_MAKER){
		var scan_mode = getValue('SCAN_MODE');
		if(scan_mode ==  'New WMS ID'){
			handleCPDMakerThreeStepEvent(event);
		}else{
			handleCPDMakerFourStepEvent(event);
		}
	} else if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM){
		handleCPDBulkEODCheckerEvent(event);
	} else if (workstepName == WORKSTEPS.COMP_APP) {
	    handleReferralsEvent(event);
	} else if (workstepName == WORKSTEPS.REPAIR) {
		handleRepairEvent(event);
	} else if (workstepName == WORKSTEPS.BULK_EOD_CHECKER) {
		handleCPDBulkEODHistoryEvent(event);
	} else if (workstepName == WORKSTEPS.DELIVERY_CHECKER) {
		handleDeliveryCheckerEvent(event);
	} else if (workstepName == WORKSTEPS.DELIVERY_MAKER) {
		handleDeliveryMakerEvent(event);
	} else if (workstepName == WORKSTEPS.FB_MAKER) {
		handleFBMakerEvent(event);
	} else if (workstepName == WORKSTEPS.FB_CHECKER) {
		handleFBCheckerEvent(event);
	}
}

function disableUAEPassMandatoryData(){
	//KYC Fields
	setStyle('name_manual',PROPERTY_NAME.DISABLE,'true');
	setStyle('eidano_manual',PROPERTY_NAME.DISABLE,'true');
	setStyle('nationality_manual',PROPERTY_NAME.DISABLE,'true');
//	setStyle('mobile_manual',PROPERTY_NAME.DISABLE,'true');
//	setStyle('email_manual',PROPERTY_NAME.DISABLE,'true');
	setStyle('dob_manual',PROPERTY_NAME.DISABLE,'true');
}


function postServerEventHandler(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	 if (workstepName == WORKSTEPS.DDE_CUST_INFO || workstepName == WORKSTEPS.DDE_ACCOUNT_INFO ||
	 workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO || 
	 workstepName == WORKSTEPS.CPD_MAKER){
		 disableUAEPassMandatoryData();
	 }
	var wiName = getWorkItemData('processInstanceId');
	console.log('Control Name: '+ controlName +', Event Type: '+ eventType);
	console.log('Response Data:');
	console.log('wi_name::'+wiName);
	console.log(jsonData);
	console.log(eventType);
	if(isReadOnlyForm){
		postServerEventHandlerQueryWorkStep(controlName, eventType, responseData);
	}
	if(controlName == PRODUCT_QUEUE && productSaveClick) {		
		productSaveClick = false;
		/*if(jsonData.message.includes('be blank') && !lostFocusProduct) {
			showMessage('', 'Product code cannot be blank', 'error');
		}*/
		/*if(jsonData.success) {
			executeServerEvent('PRODUCT_QUEUE_POST', EVENT_TYPE.CLICK, '', false);
		}*/
	} else if(workstepName == WORKSTEPS.INTRODUCTION){
		postServerEventHandlerIntro(controlName, eventType, responseData);
	} else if(workstepName == WORKSTEPS.ACCOUNT_RELATION){
		postServerEventHandlerAcctRel(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.DDE_CUSTOMER_INFO || workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		postServerEventHandlerDDE(controlName, eventType, responseData);
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
		if(BUTTON_SUBMIT == controlName && getValue(CRO_DEC) == 'Approve' && jsonData.success 
				&& jsonData.message == 'openJSP'){
			showOpenCallJspTabChecker(wiName);
		} else {
			postServerEventHandlerDDEChecker(controlName, eventType, responseData);
		}
	} else if(workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO){
		postServerEventHandlerQDECustomer(controlName, eventType, responseData);
	} else if(workstepName == WORKSTEPS.CUST_SCREEN || workstepName== WORKSTEPS.CUSTOMER_SCREEN_QDE){
		 postServerEventHandlerCustScreening(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_TEAM) {
		postServerEventHandlerCCT(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.CONTACT_CENTER_CPD) {
		postServerEventHandlerCPD(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.LEVEL_11){
		postServerEventHandlerLevels(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.LEVEL_12){
		postServerEventHandlerLevels(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.LEVEL_13){
		postServerEventHandlerLevels(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.LEVEL_14){
		postServerEventHandlerLevels(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.PHYSICAL_RECON){
		postServerEventHandlerPhysicalReconciliation(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.COMP_APP || workstepName == WORKSTEPS.PBG_VIGILANCE){
		postServerEventHandlerReferrals(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.MAIL_ROOM){
		postServerEventHandlerMailRoomOperation(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
		if(BUTTON_SUBMIT == controlName && getValue(CRO_DEC) == 'Approve' && jsonData.success 
				&& jsonData.message == 'openJSP'){
			showOpenCallJspTabChecker(wiName);
		} else {
			postServerEventHandlerQDECheckerJs(controlName, eventType, responseData);
		}
	} else if(workstepName == WORKSTEPS.CPD_MAKER){
		console.log("WORKSTEPS.CPD_MAKER "+WORKSTEPS.CPD_MAKER);
		var scan_mode = getValue('SCAN_MODE');
		if(scan_mode ==  'New WMS ID'){
			postServerEventHandlerCPDMakerThreeStep(controlName, eventType, responseData);
		}else{
			postServerEventHandlerCPDMakerFourStep(controlName, eventType, responseData);
		}
	} else if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM){
		if(BUTTON_SUBMIT == controlName && getValue(CRO_DEC) == 'Approve' && jsonData.success 
				&& jsonData.message == 'openJSP' && workstepName == WORKSTEPS.CPD_CHECKER) {
			showOpenCallJspTabChecker(wiName);
//			executeServerEvent('showOpenCallJsp', event.type, result, false);
//			counterBOE = true;
		} else {
			postServerEventHandlerCPDBulkEODChecker(controlName, eventType, responseData);
		}
	} else if (workstepName == WORKSTEPS.APP_ASSESSMENT) {
		postServerEventHandlerAppAssessment(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.COMP_APP) {
		postServerEventHandlerReferrals(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.REPAIR) {
		postServerEventHandlerRepair(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.BULK_EOD_CHECKER) {
		postServerEventHandlerCheckerHistory(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.DELIVERY_MAKER) {
		postServerEventHandlerDeliveryMaker(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.DELIVERY_CHECKER) {
		if(BUTTON_SUBMIT == controlName && getValue(CRO_DEC) == 'Approved' && jsonData.success 
				&& jsonData.message == 'openJSP') {
			//showOpenCallJspTabChecker(wiName);
			postServerEventHandlerDeliveryChecker(controlName, eventType, responseData);
			//executeServerEvent(BUTTON_SUBMIT, event.type, 'AfterJSP', false);

		} else {
			postServerEventHandlerDeliveryChecker(controlName, eventType, responseData);
		}
	} else if (workstepName == WORKSTEPS.FB_MAKER) {
		postServerEventHandlerFBMaker(controlName, eventType, responseData);
	} else if (workstepName == WORKSTEPS.FB_CHECKER) {
		if(BUTTON_SUBMIT == controlName && jsonData.success && jsonData.message == 'openJSP') {
			setTabStyle("tab2", 2, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
			var jspURL = sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status.jsp?WI_NAME="+wiName;
			document.getElementById('PRODUCT_JSP').src = jspURL;
			selectSheet('tab2',2);
		} else {
			postServerEventHandlerFBChecker(controlName, eventType, responseData);
		}
	}
}

function handleJSPResponse(typeOfJsp, data) {
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	console.log('handleJSPResponseDDE typeOfJsp: ' + typeOfJsp);
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
			selectSheet("tab4",7);
			setFieldValue('DCRA_RETRIGGER_FLAG','Y');
			//setTabStyle("tab4",12, "visible", "false");
			setTabStyle("tab4",13, "visible", "false");// Changes for Family Banking
		} else if (workstepName == WORKSTEPS.CPD_MAKER) {
			var scan_mode = getValue('SCAN_MODE');
			/*if(scan_mode ==  'New WMS ID'){
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
				selectSheet('tab3',10);
				setTabStyle("tab3",19, "visible", "false");
			}*/
			setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
			selectSheet('tab3',10);
			//setTabStyle("tab3",19, "visible", "false");
			setTabStyle("tab3",20, "visible", "false");// Changes for Family Banking
		} else {
			selectSheet("tab3",8)
			setFieldValue('DCRA_RETRIGGER_FLAG','Y'); //added for Dcra
			//setTabStyle("tab3",18, "visible", "false");
			console.log("Changes for Family Banking workstepName : "+workstepName);
			setTabStyle("tab3",19, "visible", "false"); // Changes for Family Banking
		}
	} else if(typeOfJsp == 'integrationCallStatus') {
		if(data.length > 0){
			if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM) {
				executeServerEvent(BUTTON_SUBMIT, 'handlingJSPData', data,true);
//				selectSheet("tab3",18);
//				setTabStyle("tab3",19, "visible", "false");
			} else if(workstepName == WORKSTEPS.DELIVERY_CHECKER) {
				executeServerEvent(BUTTON_SUBMIT, 'handlingJSPData', data,true);
				/*selectSheet("tab3",18);
				setTabStyle("tab3",19, "visible", "false");*/
				selectSheet("tab3",19); // Changes for Family Banking
				setTabStyle("tab3",20, "visible", "false"); 
			} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
				if(data == 'Mandatory') {
					showMessage('', 'Mandatory calls are not successful.Please close the workitem and try again later.',
					'error');
					return;
				} else if(data != 'Success') {
					showConfirmDialog('Optional calls are not successful. Do you want to proceed?', 
							confirmDialogButtons, function(result) {
						if(result == true) {
							var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
							var resultFATCACheck = checkAttchedDocument('FATCA#');
							console.log('afterJSP doc result'+resultEIDACheck+', '+resultFATCACheck);
							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'
									+resultFATCACheck+'%%%afterJSP', false);
//							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'afterJSP', false);
						} else if(result == false) {
							showMessage('', 'Please close the workitem and try again later.', 'error');
							return;
						} 
					});
				} else {
//					executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'afterJSP', false);
					var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
					var resultFATCACheck = checkAttchedDocument('FATCA#');
					console.log('afterJSP doc result'+resultEIDACheck+', '+resultFATCACheck);
					executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'
							+resultFATCACheck+'%%%afterJSP', false);
				}
			} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
				if(data == 'Mandatory') {
					setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'true');
					showMessage('', 'Mandatory calls are not successful.Please close the workitem and try again later.',
					'error');
					return;
				} else if(data != 'Success') {
					showConfirmDialog('Optional calls are not successful. Do you want to proceed?', 
							confirmDialogButtons, function(result) {
						if(result == true) {
//							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'afterJSP', false);
							var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
							var resultFATCACheck = checkAttchedDocument('FATCA#');
							console.log('afterJSP doc result'+resultEIDACheck+', '+resultFATCACheck);
							executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'
									+resultFATCACheck+'%%%afterJSP', false);
						} else if(result == false) {
							showMessage('', 'Please close the workitem and try again later.', 'error');
							return;
						} 
					});
				} else {
//					executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'afterJSP', false);
					var resultEIDACheck = DocTypeAttachedcount('EIDA_CARD#');
					var resultFATCACheck = checkAttchedDocument('FATCA#');
					console.log('afterJSP doc result'+resultEIDACheck+', '+resultFATCACheck);
					executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, resultEIDACheck+'%%%'
							+resultFATCACheck+'%%%afterJSP', false);
				}
			} else if(workstepName == WORKSTEPS.FB_CHECKER) {
				var response = executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'afterJSP', true);
				var jsondata = handleAOResponse(response);
				if (jsondata.success){
					saveWorkItem();
					completeWorkItem();
				}
			}
		} else if(workstepName == WORKSTEPS.FB_CHECKER) {
			var response = executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, 'afterJSP', true);
			var jsondata = handleAOResponse(response);
			if (jsondata.success){
				saveWorkItem();
				completeWorkItem();
			}
		}
	}
}

function showOpenCallJspTabChecker(workItemName) {
	var workstepName = getWorkItemData('activityName');
	var fullfilmentFlag = getValue('FULLFILMENT_FLAG');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	var returnVal="";
	var parameterJSP = workItemName;
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
	/*if(fullfilmentFlag == 'Y'){
	    var jspURL = sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status_Pre_Fulfillment.jsp?WI_NAME="+workItemName;
		document.getElementById('preFulfillmentJsp').src = jspURL;
		var jspURL = sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status_Post_Fulfillment.jsp?WI_NAME="+workItemName;
		document.getElementById('postFulfillmentJsp').src = jspURL;
	}else{*/
	var jspURL = sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status.jsp?WI_NAME="+workItemName;
	document.getElementById('PRODUCT_JSP').src = jspURL;
	//}
	if (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK) {
		/*setTabStyle('tab4',13, "visible", "true");
		selectSheet('tab4',13);	
	    changes for fullfilment */
		/*if(fullfilmentFlag == 'Y'){
		setTabStyle('tab4',14, "visible", "true");// Changes for Family Banking
		selectSheet('tab4',14);	
		}else{*/
		setTabStyle('tab4',13, "visible", "true");// Changes for Family Banking
		selectSheet('tab4',13);	
		//}
	} else if (workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK) {
		/*setTabStyle('tab3',19, "visible", "true");
		selectSheet('tab3',19);	
	    changes for fullfilment */
		/*if(fullfilmentFlag == 'Y'){
		setTabStyle('tab3',20, "visible", "true");// Changes for Family Banking
		selectSheet('tab3',20);	
		}else{*/
		setTabStyle('tab3',19, "visible", "true");// Changes for Family Banking
		selectSheet('tab3',19);	
		//}
	} else if (workstepName == WORKSTEPS.CPD_CHECKER) {
		/*if (fullfilmentFlag == 'Y'){
			setTabStyle('tab3', 21, 'visible', 'true');
			selectSheet('tab3', 21);	
		} else {*/
		setTabStyle('tab3', 20, 'visible', 'true');
		selectSheet('tab3', 20);
		//}
	} else {
		/*setTabStyle('tab3',19, "visible", "true");
		selectSheet('tab3',19);	*/
		setTabStyle('tab3',20, "visible", "true");// Changes for Family Banking
		selectSheet('tab3',20);	 
	}
}


function saveAndNextPreHook(tabId){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(isReadOnlyForm){
		saveAndNextPreHookQuery(tabId);
	} else if(workstepName == WORKSTEPS.ACCOUNT_RELATION){
		return saveAndNextPreHookAccRel(tabId);
	} else if((workstepName == WORKSTEPS.DDE_CUSTOMER_INFO) || (workstepName == WORKSTEPS.DDE_ACCOUNT_INFO)){
		return saveAndNextPreHookDDE(tabId);
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
		return saveAndNextPreHookDDEChecker(tabId);
	} else if((workstepName == WORKSTEPS.QDE_CUST_INFO) || (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO)){
		return saveAndNextPreHookQDE(tabId);
	}  else if((workstepName == WORKSTEPS.PHYSICAL_RECON)){
		return saveAndNextPreHookPhysicalReconciliation(tabId);
	} else if((workstepName == WORKSTEPS.CONTACT_CENTER_TEAM)){
		return saveAndNextPreHookCCT(tabId);
	} else if((workstepName == WORKSTEPS.CONTACT_CENTER_CPD)){
		return saveAndNextPreHookCCTCPD(tabId);
	} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK){
		return saveAndNextPreHookQDEChecker(tabId);
	} else if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM){
		return saveAndNextPreHookCPDBulkEODChecker(tabId);
	} else if(workstepName == WORKSTEPS.BULK_EOD_CHECKER){
		return saveAndNextPreHookCheckerHistory(tabId);
	} else if (workstepName == WORKSTEPS.APP_ASSESSMENT) {
		return saveAndNextPreHookAppAssessment(tabId);
	} else if (workstepName == WORKSTEPS.COMP_APP) {
		return saveAndNextPreHookReferrals(tabId);
	} else if(workstepName == WORKSTEPS.CPD_MAKER){
		var scan_mode = getValue('SCAN_MODE');
		if(scan_mode ==  'New WMS ID'){
			return saveAndNextPreHookThreeStep(tabId);
		}else{
			return saveAndNextPreHookFourStep(tabId);
		}
	} else if (workstepName == WORKSTEPS.MAIL_ROOM){
		return saveAndNextPreHookMailRoomOperation(tabId);
	} else if (workstepName == WORKSTEPS.QUERY){
		return saveAndNextPreHookMailRoomOperation(tabId);
	} else if (workstepName == WORKSTEPS.BULK_EOD_CHECKER) {
		return saveAndNextPreHookCheckerHistory(tabId);
	} else if (workstepName == WORKSTEPS.DELIVERY_CHECKER) {
		return saveAndNextPreHookDeliveryChecker(tabId);
	} else if (workstepName == WORKSTEPS.DELIVERY_MAKER) {
		return saveAndNextPreHookDeliveryMaker(tabId);
	} else if(workstepName == WORKSTEPS.CUST_SCREEN || workstepName== WORKSTEPS.CUSTOMER_SCREEN_QDE) {
		return saveAndNextPreHookCustScreen(tabId);
	}
	return true;
}

function selectRowHook(tableId,selectedRowsArray,isAllRowsSelected){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(isReadOnlyForm){
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
	} else if(workstepName==WORKSTEPS.ACCOUNT_RELATION){
		selectRowHookAcctRel(tableId,selectedRowsArray,isAllRowsSelected)
	} else if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO){
		return selectRowHookDDE(tableId,selectedRowsArray,isAllRowsSelected);
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
		return selectRowHookDDEChecker(tableId,selectedRowsArray,isAllRowsSelected);
	} else if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM){
		return selectRowHookCPDCheckerFourStep(tableId,selectedRowsArray,isAllRowsSelected);
	}	else if(workstepName == 'CPD Maker'){
		var scanMode = getValue('SCAN_MODE');
		if(scanMode ==  'New WMS ID'){
			return selectRowHookCPDMakerThreeStep(tableId,selectedRowsArray,isAllRowsSelected);
		}else{
			return selectRowHookCPDMakerFourStep(tableId,selectedRowsArray,isAllRowsSelected);
		}
	} else if((workstepName == WORKSTEPS.QDE_CUST_INFO) || (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) 
			|| (workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK)){
		return selectRowHookQDEInfo(tableId,selectedRowsArray,isAllRowsSelected);
	} else if((workstepName == WORKSTEPS.CONTACT_CENTER_TEAM)){
		return selectRowHookCCT(tableId,selectedRowsArray,isAllRowsSelected);
	} else if((workstepName == WORKSTEPS.CONTACT_CENTER_CPD)){
		return selectRowHookCCTCPD(tableId,selectedRowsArray,isAllRowsSelected);
	} else if((workstepName == WORKSTEPS.COMP_APP)){
		return selectRowHookReferrals(tableId,selectedRowsArray,isAllRowsSelected);
	}

}

function handleAOResponse(responseData) {
	var jsonData;
	var workstep = getWorkItemData('activityName');
	try {
		jsonData = JSON.parse(responseData); // STRING TO JSON
	} catch(error) {
		jsonData = responseData; // ALREADY A JSON
	}
	return jsonData;
}

function clearAOControls(cName) {
	//var cName = controls.split(',');
	var blank = '';
	var values = {};
	for (var i = 0; i < cName.length; i++) {
		console.log('clearing control: ' + cName[i]);
		values[cName[i]] = blank;
	}
	setValues(values, true);
}

function onClickTab(tabId,sheetIndex,eventCall){
	console.log('sheetIndex='+sheetIndex+'eventCall='+eventCall);	
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex id: '+event.target.id+'event.target.innerHTML='+event.target.innerHTML);
	var controlID=sheetIndex+','+event.target.innerHTML;
	var input=sheetIndex;
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex: '+sheetIndex);
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(eventCall == 2) {
		if(isReadOnlyForm){
			onClickTabQueryWorkStep(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if(workstepName == WORKSTEPS.ACCOUNT_RELATION) {
			onClickTabAcctRel(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO || workstepName == WORKSTEPS.DDE_CUSTOMER_INFO) {
			onClickTabDDEAccountInfo(tabId,sheetIndex,eventCall);
		} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO_CHECK){
			onClickTabDDEAccountInfoChecker(tabId,sheetIndex,eventCall);
//			selectSheet(tabId,sheetIndex);
		} else if(workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
			onClickTabQDEAccountInfo(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if(workstepName == WORKSTEPS.CUST_SCREEN || workstepName== WORKSTEPS.CUSTOMER_SCREEN_QDE) {
			onClickTabCustScreening(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if (workstepName == WORKSTEPS.PHYSICAL_RECON){
			onClickTabPhysicalReconciliation(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if (workstepName == WORKSTEPS.CONTACT_CENTER_TEAM){
			onClickTabCCT(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		}else if (workstepName == WORKSTEPS.CONTACT_CENTER_CPD){
			onClickTabCPD(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		}else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO_CHECK){
			return onClickTabQDEAccountInfoCheckerJs(tabId,sheetIndex,eventCall);
		} else if(workstepName == WORKSTEPS.CPD_MAKER){
			var scan_mode = getValue('SCAN_MODE');
			if(scan_mode ==  'New WMS ID'){
				onClickTabCPDMakerThreeStep(tabId,sheetIndex,eventCall);
			}else{
				onClickTabCPDMakerFourStep(tabId,sheetIndex,eventCall);
			}
		} else if(workstepName == WORKSTEPS.CPD_CHECKER   || workstepName == WORKSTEPS.RM){
			onClickTabCPDBulkEODChecker(tabId,sheetIndex,eventCall);
		} else if (workstepName == WORKSTEPS.APP_ASSESSMENT) {
			onClickTabAppAssessment(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if (workstepName == WORKSTEPS.COMP_APP) {
			onClickTabReferrals(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if (workstepName == WORKSTEPS.MAIL_ROOM){
			onClickTabMailRoomOperation(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if (workstepName == WORKSTEPS.BULK_EOD_CHECKER) {
			onClickTabCheckerHistory(tabId,sheetIndex,eventCall);
		} else if (workstepName == WORKSTEPS.DELIVERY_CHECKER) {
			onClickTabDeliveryChecker(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		} else if (workstepName == WORKSTEPS.DELIVERY_MAKER) {
			onClickTabDeliveryMaker(tabId,sheetIndex,eventCall);
			selectSheet(tabId,sheetIndex);
		}
	}
}

function enableControls(controlNames){
	for(var i=0; i<controlNames.length; i++){
		console.log('enabling control: '+controlNames[i]);
		setStyle(controlNames[i], PROPERTY_NAME.DISABLE, 'false');
	}
}

function modifyRowPostHook(tableId){
	if(tableId == ACC_RELATION){
		saveWorkItem();
	}
}



function onRowClick(listviewId,rowIndex){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(listviewId == 'DELIVERY_PREFRENCES_GRID' || listviewId == 'LVW_FAMILY_MEMBERS'){
		return true;
	} /*else if(listviewId == PRODUCT_QUEUE) {
		executeServerEvent('PRODUCT_ROW_CLICK', EVENT_TYPE.CLICK, '', false);
		return true;
	}*/
	if(workstepName == WORKSTEPS.ACCOUNT_RELATION){
		return onRowClickAccRel(listviewId,rowIndex);
	} else if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO){
		/*if(listviewId == PRODUCT_QUEUE && getValueFromTableCell(PRODUCT_QUEUE,rowIndex,4) != '') {
			return false;
		}*///21sep2021
		return onRowClickDDE(listviewId,rowIndex);
	} else if(workstepName == WORKSTEPS.DDE_CUSTOMER_INFO_CHECK){
		return onRowClickDDE(listviewId,rowIndex);
	} else if(workstepName == WORKSTEPS.CPD_MAKER){
		var scan_mode = getValue('SCAN_MODE');
		/*if(listviewId == PRODUCT_QUEUE && getValueFromTableCell(PRODUCT_QUEUE,rowIndex,4) != '') {
			return false;
		}*///21sep2021
		if(scan_mode ==  'New WMS ID'){
			return onRowClickCPDMakerThreeStep(listviewId,rowIndex);
		} else {
			return onRowClickCPDMakerFourStep(listviewId,rowIndex);
		}
	} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
		if(listviewId == PRODUCT_QUEUE && getValueFromTableCell(PRODUCT_QUEUE,rowIndex,4) != '') {
			return false;
		}
		return onRowClickQDE(listviewId,rowIndex);
	} else {
		return false;
	}	
}

function disableControls(controlNames){
	//var arr = controlNames.split(',');
	for(var i=0; i<controlNames.length; i++){
		console.log('disabling control: '+controlNames[i]);
		setStyle(controlNames[i], PROPERTY_NAME.DISABLE, 'true');
	}
}

function lockAOSection(controlNames){
	for(var i=0; i<controlNames.length; i++){
		console.log('locking control: '+controlNames[i]);
		setStyle(controlNames[i],PROPERTY_NAME.LOCK, 'true');
	}
}

function showControls(controlNames){
	for(var i=0; i<controlNames.length; i++){
		console.log('showing control: '+controlNames[i]);
		setStyle(controlNames[i], PROPERTY_NAME.VISIBLE, 'true');
	}
}

function hideControls(controlNames){
	for(var i=0; i<controlNames.length; i++){
		console.log('hiding control: '+controlNames[i]);
		setStyle(controlNames[i], PROPERTY_NAME.VISIBLE, 'false');
	}
}

//yamini
function setMultipleFieldValues(controlNames, controlValues) {
	var values = {};
	for(var i=0; i<controlNames.length; i++){
		console.log('setValue for control: '+controlNames[i]);
		values[controlNames[i]] = controlValues[i];
	}
	setValues(values, true);
}

function showTabs(tabs) {
	for(var i=0; i<tabs.length; i++){
		console.log('showing tab: '+tabs[i]);
		setTabStyle("tab1", tabs[i], PROPERTY_NAME.VISIBLE, "true");
	}
}

function hideTabs(tabs) {
	for(var i=0; i<tabs.length; i++){
		console.log('hiding tab: '+tabs[i]);
		setTabStyle("tab1", tabs[i], PROPERTY_NAME.VISIBLE, 'false');
	}
}

function enableTabs(tabs) {
	for(var i=0; i<tabs.length; i++){
		console.log('enabling tab: '+tabs[i]);
		setTabStyle("tab1", tabs[i], PROPERTY_NAME.DISABLE, 'false');
	}
}

function disableTabs(tabs) {
	for(var i=0; i<tabs.length; i++){
		console.log('disabling tab: '+tabs[i]);
		setTabStyle("tab1", tabs[i], PROPERTY_NAME.DISABLE, 'true');
	}
}

function lockAOSection(controls){
	for(var i=0; i<controls.length; i++){
		console.log('locking control: '+controls[i]);
		setStyle(controls[i],'lock', 'true');
	}
}

function setFieldValue(controlName, controlValue) {
	var values = {};
	values[controlName] = controlValue;
	setValues(values, true);
}

function prehookSaveTab(tabId){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO || workstepName == WORKSTEPS.DDE_CUSTOMER_INFO){
		prehookSaveTabDDECustAccountInfo(tabId);
	} else if(workstepName == WORKSTEPS.QDE_CUST_INFO || workstepName == WORKSTEPS.QDE_ACCOUNT_INFO){
		prehookSaveTabQDECustAccountInfo(tabId);
	} else if(workstepName == WORKSTEPS.CPD_MAKER){
		var scan_mode = getValue('SCAN_MODE');
		if(scan_mode ==  'New WMS ID'){
			prehookSaveTabCPDMakerThreeStep(tabId);
		}else{
			prehookSaveTabCPDMaker(tabId);
		}
	} else if (workstepName == WORKSTEPS.MAIL_ROOM){
		prehookSaveTabMailRoomOperation(tabId);
	} 
	return true;
}

function customListViewValidation(controlId,flag){
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	/*if(workstepName==WORKSTEPS.QDE_ACCOUNT_INFO)
		 return customListViewValidationQDE(controlId,flag);*/	
	if(controlId == 'AccInfo_UdfList'){
		if (flag == 'A'){
			console.log('Inside customListViewValidation');
			var response = executeServerEvent(controlId, EVENT_TYPE.CLICK, '', true);
			var jsonData = handleAOResponse(response);
			if (!jsonData.success){
				return false;
			}
			else{
				return true;
			}
		}
	}else if(controlId == ACC_RELATION) {
		if (flag == 'M'){
			if(sHiddenEida != getValue('table33_eida_no') && 'Minor' != getValue('table33_acc_relation')){
				setStyle('FETCH_UAE_PASS_INFO','disable', 'false');
				setValues({['IS_UAE_PASS_INFO_CLICKED']: ''}, true);
			}
		}
	}
	
	else if(controlId == PRODUCT_QUEUE){
		productSaveClick = true;
		/*var prodCode = '';
		if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
			prodCode = getValue('table94_prod_code');
		} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
			prodCode = getValue('table130_prod_code');
		} else if(workstepName == WORKSTEPS.CPD_MAKER) {
			prodCode = getValue('table103_prod_code');
		}
		if(prodCode == '') {
			showMessage('', 'Product code cannot be blank', 'error');
			return false;
		}*/
		 if (workstepName == WORKSTEPS.ACCOUNT_RELATION) {
			 if (flag == 'M'){
				 if(getValue('IS_INITIATED_UAE_PASS') == 'Y'){
					 setStyle('table33_dob', PROPERTY_NAME.DISABLE, 'true');
				 } else {
					 setStyle('table33_dob', 'disable', 'false');

					}
				 setStyle('table33_nationality', PROPERTY_NAME.DISABLE, 'false');
				}
		 }
		if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO 
			|| workstepName == WORKSTEPS.QDE_ACCOUNT_INFO || workstepName == WORKSTEPS.CPD_MAKER) {
			if (flag == 'A'){
				console.log('Inside PRODUCT_QUEUE customListViewValidation');
				var response = executeServerEvent(controlId, EVENT_TYPE.CLICK, '', true);
				var jsonData = handleAOResponse(response);
				if (!jsonData.success){	
					if (null != jsonData.message && '' != jsonData.message) {
						if((jsonData.message).indexOf('###') != -1){
							var arr = jsonData.message.split('###');
							if('' != arr[1]) {
								showMessage(arr[0], arr[1], 'error');
							}
						}
					}
					return false;
				} else{
					return true;
				}
			} else if (flag == 'M'){
				var response = executeServerEvent('VALIDATE_PRODUCT_MODIFY', EVENT_TYPE.CLICK, '', true);
				var jsonData = handleAOResponse(response);
				if (!jsonData.success){	
					/*if (null != jsonData.message && '' != jsonData.message) {
						if((jsonData.message).indexOf('###') != -1){
							var arr = jsonData.message.split('###');
							if('' != arr[1]) {
								showMessage(arr[0], arr[1], 'error');
							}
						}
					}*/
					return false;
				} else{
					return true;
				}
			}
		} else return false;
	} else if(controlId == 'LVW_FAMILY_MEMBERS'){
		/*if(getValue('table3_name') == '' && getValue('table3_newExisting') == 'New') {
		showMessage('', 'Invalid CID', 'error');
		return false;
	}*/
		if(getValue('table5_cid') == '') {
			showMessage('table5_cid', 'Please enter valid CID', 'error');
			return false;
		} else if(getValue('table5_relation') == '') {
			showMessage('table5_relation', 'Please select member relationship', 'error');
			return false;
		} else if(getValue('table5_customerCategory') != 'Excellency' && 
				getValue('table5_customerCategory') != 'Private Clients' && 
				getValue('table5_customerCategory') != 'Emirati Excellency' 
					&& getValue('table5_newExisting') == 'New') {
			showMessage('table5_relation', 'Invalid Customer Category', 'error');
			return false;
		} 
		if(flag == 'A' && !checkCidAlreadyExists(getValue('table5_cid'))) {
			showMessage('table5_cid', 'CID already exists', 'error');
			return false;
		}
		if(workstepName == WORKSTEPS.FB_MAKER){
			if(getValue('table5_status') != 'A'){
				if(getValue('table5_status') == 'D' && getValue('table5_revertDeletion') == true) {
					setFieldValue('table5_status', 'E');
					setRowColorInListView('LVW_FAMILY_MEMBERS', getValue('table5_sno')-1, 'FFFFFF');
				}
				if(getValue('table5_status') != 'D') {
					executeServerEvent('MODIFY_EXISTING_MEMBER', EVENT_TYPE.CHANGE, '', true);
					if(getValue('table5_status') == 'M') {
						setRowColorInListView('LVW_FAMILY_MEMBERS', getValue('table5_sno')-1, 'FFFF66');
					} else if(getValue('table5_status') != 'D'){
						setRowColorInListView('LVW_FAMILY_MEMBERS', getValue('table5_sno')-1, 'FFFFFF');
					}
				}								
			}
		}
		saveWorkItem();
	}
	return true;

}  

function insideTableButtonRetryClick() {
	var workstepName = getWorkItemData('activityName');
	console.log('inside table button clock');
	if (workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		insideDDETableButtonRetryClick();
		}
}

function insideTableButtonDocLinkClick() { 
	var workstepName = getWorkItemData('activityName');
	console.log('inside table button clock');
	if (workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		insideDDETableButtonDocLinkClick();
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

function tableOperation(tableId,operationType){
	console.log('tableId' +tableId);
	console.log('operationType' +operationType);
	if( operationType == 'DeleteRow'){
			console.log('operationType' +operationType);
		if(tableId == 'CRS_TaxCountryDetails') {
			console.log('tableId' +tableId);
			executeServerEvent('DELETEGRID', EVENT_TYPE.CLICK, '', false);
			return true;
		} else if(tableId == ACC_RELATION) {
			var selectedRows =getSelectedRowsIndexes(ACC_RELATION); 
			var iSelectedRow;
			if(selectedRows!=null || selectedRows!='' || selectedRows!=undefined){
				iSelectedRow = selectedRows[0]; 
			}
			console.log('iSelectedRow:' + iSelectedRow);
			executeServerEvent('DELETEROW', EVENT_TYPE.CLICK, iSelectedRow, false);
			return true;	
		} else if(tableId == PRODUCT_QUEUE){
			var selectedRows =getSelectedRowsIndexes(PRODUCT_QUEUE); 
			var iSelectedRow;
			if(selectedRows!=null || selectedRows!='' || selectedRows!=undefined){
				iSelectedRow = selectedRows[0]; 
			}
			showMessage('', 'Deleting product will route to Sanction Screening Tab to recalculate Risk','error'); //dcra
			setFieldValue('DCRA_RETRIGGER_FLAG','Y');  //dcra
			//saveWorkItem();
			console.log('iSelectedRow:' + iSelectedRow);
			var response = executeServerEvent(REMOVE_PRODUCT, EVENT_TYPE.CLICK, iSelectedRow, true);
			console.log('REMOVE_PRODUCT:: '+response);
			if(response != '' && response != undefined){
				var jsondata = handleAOResponse(response);
				if (jsondata.success){
					return true;
				} else {
					return false;
				}
			}
		} else if(tableId == 'queue_dc'){
			var selectedRows =getSelectedRowsIndexes('queue_dc'); 
			var iSelectedRow;
			if(selectedRows!=null || selectedRows!='' || selectedRows!=undefined){
				iSelectedRow = selectedRows[0]; 
			}
			console.log('iSelectedRow:' + iSelectedRow);
			executeServerEvent('DELETEROW', EVENT_TYPE.CLICK, iSelectedRow, false);
			return true;
		} else if(tableId == 'LVW_FAMILY_MEMBERS') {
			var selectedRows =getSelectedRowsIndexes('LVW_FAMILY_MEMBERS'); 
			console.log('selectedRows [LVW_FAMILY_MEMBERS] :' + selectedRows);
			
			// AYUSH 11/11/2022 : TO BE MODIFIED
			for(let index = 0; index < selectedRows.length; index++)
			{
				console.log('selectedRows[index] in for :' + index);
				if(getValueFromTableCell('LVW_FAMILY_MEMBERS', selectedRows[index], 1) == 'Existing') {
					console.log('selectedRows[index] in if :' + index);
					executeServerEvent('DELETE_EXISTING_MEMBER', EVENT_TYPE.CLICK, selectedRows[index], true);
	//				setTableCellData('LVW_FAMILY_MEMBERS', selectedRows[0], 8, 'D', 'false');
					setRowColorInListView(tableId, selectedRows[index], 'FF5050');
					saveWorkItem();
					//return false;
					console.log('selectedRows[index] return false :' + index);
				} else {
					console.log('selectedRows[index] inside else :' + index);
					executeServerEvent('DELETE_NEW_MEMBER', EVENT_TYPE.CLICK, selectedRows[index], true);
					//return true;
				}
			}
			return false;
		}
	} else if( operationType == 'AddRow'){
		
	}
}

function deleteRowPostHook(tableId,rowIndices){
	if(tableId == PRODUCT_QUEUE){
			saveWorkItem();
			//alert('after hook'+rowIndices);
//			var deletedRow = rowIndices.split(',') + 1;
			//alert('after hook'+deletedRow);
			var deletedRow = rowIndices.split(',');
			var cid = parseInt(deletedRow[0]) + 1;
			executeServerEvent('DELETEPRODUCTROW', EVENT_TYPE.CLICK, cid, false);
		}
}

function addRowPostHook(tableId){
	if(tableId == PRODUCT_QUEUE){
		executeServerEvent('PRODUCT_QUEUE_POST', EVENT_TYPE.CLICK, '', false);
	}
}

function checkSpecialChars(str){
	for(var i=0; i<specialChars.length; i++){
		if(str.indexOf(specialChars[i])>-1){
			return true;
		}
	}
	return false;
}

function onProductListViewLoad() {
	var workstepName = getWorkItemData('activityName');
	if(workstepName == 'QDE_ Account_Info') {
		workstepName = 'QDE_Account_Info';
	}
	if(workstepName == WORKSTEPS.DDE_ACCOUNT_INFO) {
		var fields = ['table94_acc_status','table94_prod_desc','table94_acc_no','table94_acc_brnch',
		              'table94_wiNumber','table94_cid'];
		disableControls(fields);
		if(getValue('table94_acc_no') != '') {
			var fields2 = ['table94_prod_code','table94_currency','table94_iban_no','table94_date_acc_opening'];
			disableControls(fields2);
		}
	} else if(workstepName == WORKSTEPS.QDE_ACCOUNT_INFO) {
		var fields = ['table130_acc_status','table130_prod_desc','table130_acc_no','table130_acc_brnch',
		              'table130_wiNumber','table130_cid'];
		disableControls(fields);
		if(getValue('table130_acc_no') != '') {
			var fields2 = ['table130_prod_code','table130_currency','table130_iban_no','table130_date_acc_opening'];
			disableControls(fields2);
		}
	} else if(workstepName == WORKSTEPS.CPD_MAKER) {
		var fields = ['table103_acc_status','table103_prod_desc','table103_acc_no','table103_acc_brnch',
		              'table103_wiNumber','table103_cid'];
		disableControls(fields);
		if(getValue('table103_acc_no') != '') {
			var fields2 = ['table103_prod_code','table103_currency','table103_iban_no','table103_date_acc_opening'];
			disableControls(fields2);
		}
	} 
	executeServerEvent('PRODUCT_ROW_CLICK', EVENT_TYPE.CLICK, '', false);
}

function checkCidAlreadyExists(cid) {
	var count = getGridRowCount('LVW_FAMILY_MEMBERS');
	for(var i=0; i<count; i++) {
		if(getValueFromTableCell('LVW_FAMILY_MEMBERS', i, 2) == cid) {
			return false;
		}
	}
	return true;
}

function enableDisableProductAddition() {
	var count = getGridRowCount(PRODUCT_QUEUE);
	var acc = false;
	for(var i=0; i<count; i++) {
		if(getValueFromTableCell(PRODUCT_QUEUE, i, 4) != '') {
			acc = true;
			break;
		}
	}
	if(acc) {
		setStyle(PRODUCT_QUEUE, PROPERTY_NAME.DISABLE, 'true');
	}
}

//Added by reyaz 03/05/2023
function openTrsdJsp() {	
	if(getValue('TRSD_WI_NAME') != ''){
        hideTrsdControls('TRSD_HISTORY_LVW,SEC_SS_TRSD_QDE,SEC_SS_TRSD');
		hideTrsdControls(trsd_grid_name);
		setStyle("trsd_frame", "visible", "true"); 
		setStyle("BTN_TRSD_CHECK", "disable", "true");
		var wd_uid=getWorkItemData("sessionId");
		var userName=getWorkItemData("userName");
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var cabinetName = getWorkItemData("cabinetName");
		var ipPort=document.URL.substr(document.URL.indexOf('//')+2,document.URL.indexOf('AO')-9);
		var jspURL=sLocationOrigin+"/webdesktop/login/mailloginclient.jsf?CalledFrom=OPENWI&OAPDomHost="+ipPort+"&OAPDomPrt=https:&" +
		"ReferredFromSameOrigin=false&ReferredFromSameOrigin=false&CabinetName="+cabinetName+"&pid="+
		getValue('TRSD_WI_NAME')+"&wid=1&SessionId="+wd_uid+"&UserIndex="+"&UserName="+userName;
		console.log(jspURL);
		document.getElementById('TRSD_Integration_Iframe').src=jspURL;
	}	
}



function openTrsdJspCPD() {	
	if(getValue('TRSD_WI_NAME') != ''){
        hideTrsdControls('CPD_TRSD_HISTORY_LVW,SEC_SS_CPD_TRSD');
		hideTrsdControls(cpd_trsd_grid_name);
		setStyle("cpd_trsd_frame", "visible", "true"); 
		setStyle("BTN_CPD_TRSD_CHK", "disable", "true");
		var wd_uid=getWorkItemData("sessionId");
		var userName=getWorkItemData("userName");
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var cabinetName = getWorkItemData("cabinetName");
		var ipPort=document.URL.substr(document.URL.indexOf('//')+2,document.URL.indexOf('AO')-9);
		var jspURL=sLocationOrigin+"/webdesktop/login/mailloginclient.jsf?CalledFrom=OPENWI&OAPDomHost="+ipPort+"&OAPDomPrt=https:&" +
		"ReferredFromSameOrigin=false&ReferredFromSameOrigin=false&CabinetName="+cabinetName+"&pid="+
		getValue('TRSD_WI_NAME')+"&wid=1&SessionId="+wd_uid+"&UserIndex="+"&UserName="+userName;
		console.log(jspURL);
		document.getElementById('CPD_TRSD_Integration_Iframe').src=jspURL;
	}	
}

function hideTrsdControls(controlNames){
	var arr = controlNames.split(",");
	for(var i=0; i<arr.length; i++){
		console.log('hiding control: '+arr[i]);
		setStyle(arr[i], PROPERTY_NAME.VISIBLE, 'false');
	}
}
function pepAssesment() {
	
	var custSegment = getValue('PRIVATE_CLIENT');
    if (custSegment == 'Y'){
		    setStyle("Overall_Risk_Assessment_Pri","visible", "true");
			setStyle("Customer_PEP","visible", "true");
			setStyle("Customer_PEP_Rad","visible", "true");
			setStyle("Person_Associated","visible", "true");
			setStyle("Person_Associated_Rad","visible", "true");
			setStyle("Person_Power","visible", "true");
			setStyle("Person_Power_Rad","visible", "true");
			setStyle("Customer_Entrusted_Man","visible", "true");
			setStyle("Customer_Entrusted_Man_Rad","visible", "true");
			
			setStyle("Overall_Risk_Assessment","visible", "false");
			setStyle("Customer_Authorized","visible", "false");
			setStyle("Customer_Authorized_Rad","visible", "false");
			setStyle("Customer_Entrusted","visible", "false");
			setStyle("Customer_Entrusted_Rad","visible", "false");
	}else{
		    setStyle("Overall_Risk_Assessment_Pri","visible", "false");
			setStyle("Customer_PEP","visible", "false");
			setStyle("Customer_PEP_Rad","visible", "false");
			setStyle("Person_Associated","visible", "false");
			setStyle("Person_Associated_Rad","visible", "false");
			setStyle("Person_Power","visible", "false");
			setStyle("Person_Power_Rad","visible", "false");
			setStyle("Customer_Entrusted_Man","visible", "false");
			setStyle("Customer_Entrusted_Man_Rad","visible", "false");
			
			setStyle("Overall_Risk_Assessment","visible", "true");
			setStyle("Customer_Authorized","visible", "true");
			setStyle("Customer_Authorized_Rad","visible", "true");
			setStyle("Customer_Entrusted","visible", "true");
			setStyle("Customer_Entrusted_Rad","visible", "true");
	}
}
function setKYCFlagPrivateclient(){
	var requestType = getValue(REQUEST_TYPE);  // DCRA
	var privateClient= getValue('PRIVATE_CLIENT'); 
	if (privateClient== 'Y'){
	if(requestType == ('New Account') || requestType == ('New Account With Category Change')) 
		{
			setStyle("KYC_CUST_BLACKLIST1","visible", "true");
			setStyle("KYC_BARRIER","visible", "true");
			setStyle("KYC_ENTITY_SHELL","visible", "true");
			setStyle("KYC_CUST_NORTH_KORES","visible", "true");
			setStyle("kYC_PATNER_UBO","visible", "true");
			setStyle("KYC_MANUFACTURING","visible", "true");
			setStyle("KYC_LEGAL_GAMING","visible", "true");
			setStyle("KYC_OFFICE_VELLI","visible", "true");
			setStyle("KYC_VIRTUAL_CURRENCY","visible", "true");
			setStyle("KYC_ENTITY_RE","visible", "true");
			setStyle("KYC_NOMINEE","visible", "true");
			setStyle("KYC_EXCEPTIONAL","visible", "true");
			setStyle("KYC_CUST_HAWALADRA","visible", "true");
			
			setStyle("KYC_CUST_BLACKLIST","visible", "false");
			setStyle("KYC_CUST_HAWALADRA_SERV","visible", "false");
			setStyle("KYC_CUST_NORTH_KORES_RESD","visible", "false");
			setStyle("KYC_ENTITY_RESIDENT_JURISD","visible", "false");
			
			
			setStyle("Que5","visible", "true");
			setStyle("Que6","visible", "true");
			setStyle("Que7","visible", "true");
			setStyle("Que8","visible", "true");
			setStyle("Que9","visible", "true");
			setStyle("Que10","visible", "true");
			setStyle("Que11","visible", "true");
			setStyle("Que12","visible", "true");
			setStyle("Que13","visible", "true");
			setStyle("Que14","visible", "true");
			setStyle("Que15","visible", "true");
			setStyle("Que16","visible", "true");
			setStyle("Que17","visible", "true");
			setStyle("Que1","visible", "false");
			setStyle("Que2","visible", "false");
			setStyle("Que3","visible", "false");
			setStyle("Que4","visible", "false");
			
		}
  }else {
	        setStyle("KYC_CUST_BLACKLIST1","visible", "false");
			setStyle("KYC_BARRIER","visible", "false");
			setStyle("KYC_ENTITY_SHELL","visible", "false");
			setStyle("KYC_CUST_NORTH_KORES","visible", "false");
			setStyle("kYC_PATNER_UBO","visible", "false");
			setStyle("KYC_MANUFACTURING","visible", "false");
			setStyle("KYC_LEGAL_GAMING","visible", "false");
			setStyle("KYC_OFFICE_VELLI","visible", "false");
			setStyle("KYC_VIRTUAL_CURRENCY","visible", "false");
			setStyle("KYC_ENTITY_RE","visible", "false");
			setStyle("KYC_NOMINEE","visible", "false");
			setStyle("KYC_EXCEPTIONAL","visible", "false");
			setStyle("KYC_CUST_HAWALADRA","visible", "false");
			
			setStyle("KYC_CUST_BLACKLIST","visible", "true");
			setStyle("KYC_CUST_HAWALADRA_SERV","visible", "true");
			setStyle("KYC_CUST_NORTH_KORES_RESD","visible", "true");
			setStyle("KYC_ENTITY_RESIDENT_JURISD","visible", "true");
			
			setStyle("Que1","visible", "true");
			setStyle("Que2","visible", "true");
			setStyle("Que3","visible", "true");
			setStyle("Que4","visible", "true");
			setStyle("Que5","visible", "false");
			setStyle("Que6","visible", "false");
			setStyle("Que7","visible", "false");
			setStyle("Que8","visible", "false");
			setStyle("Que9","visible", "false");
			setStyle("Que10","visible", "false");
			setStyle("Que11","visible", "false");
			setStyle("Que12","visible", "false");
			setStyle("Que13","visible", "false");
			setStyle("Que14","visible", "false");
			setStyle("Que15","visible", "false");
			setStyle("Que16","visible", "false");
			setStyle("Que17","visible", "false");
  }
}
function PepNationality() { // changes for downgrade krishna

 var value  = getValue('RA_IS_CUST_PEP');
 var value1 = getValue(ARE_U_PEP);
 var valueAdd  = getValue('ADDITIONAL_SOURCES_INCOME_AED');
    if (value =='Yes'||value1=='Yes'){
	 setStyle('POA_Nationality','visible','true');
	 setStyle('POA_RESIDENCY','visible','true'); //Changes by shivanshu ATP-455
   }else{
	 setStyle('POA_Nationality','visible','false');
	 setStyle('POA_RESIDENCY','visible','false'); //Changes by shivanshu ATP-455
	}
}
function AddSourceIncome() { // changes for downgrade krishna
 var value  = getValue('ADDITIONAL_SOURCES_INCOME_AED');
    if (value =='Business Income'|| value =='Freelance Income'){
	 setStyle('QVAR_IND_RISK','visible','true');
   }else{
	 setStyle('QVAR_IND_RISK','visible','false');
	}
  }

 function pepLogic(){
	var custSegment = getValue('PRIVATE_CLIENT');
	var workstepName = getWorkItemData('activityName');
	if( custSegment == 'Y'){
		var q1 = getValue('Customer_PEP_Rad');
		var q2 = getValue('Person_Associated_Rad');
		var q3 = getValue('Person_Power_Rad');
		var q4 = getValue('Customer_Entrusted_Man_Rad');
		if ((q1 == 'Yes'|| q2== 'Yes'||q3 == 'Yes')&& q4 == 'No'){
			setStyle("HIO_PEP_AO","disable", "false");
				setFieldValue('HIO_PEP_AO', 'UAE PEP');
		}else{
				setFieldValue('HIO_PEP_AO', '');
			setStyle("HIO_PEP_AO","disable", "false");
		}
	} else {
		var q5 = getValue('Customer_Authorized_Rad');
		var q6 = getValue('Customer_Entrusted_Rad');
		if (q5 == 'Yes' && q6== 'No'){
			setStyle("HIO_PEP_AO","disable", "false");
				if(getValue('HIO_PEP_AO') == ''){
				setFieldValue('HIO_PEP_AO', 'UAE PEP');
				}
			
		}else{
			setStyle("HIO_PEP_AO","disable", "true");
				setFieldValue('HIO_PEP_AO', '');
			

		}
	}
} 

function mandatoryMultiDropDownField() { 
    var privateClient= getValue('PRIVATE_CLIENT');
	var requestType = getValue(REQUEST_TYPE);
	if(!(requestType == ('Upgrade') || requestType == ('Category Change Only'))) {

	if(getValue('LISTVIEW_SRCOFINCOME') ==null){
		showMessage('LISTVIEW_SRCOFINCOME', 'Please Select Primary Source of Income/Industry', 'error');
		return false; 
	}
	if(getValue('LISTVIEW_PUR_ACC_RELATION') ==null){
		showMessage('LISTVIEW_PUR_ACC_RELATION', 'Please Select Purpose of Account Opening', 'error');
		return false; 
	}
	if(getValue('ADDITIONAL_SOURCES_INCOME_AED') ==null){
		showMessage('ADDITIONAL_SOURCES_INCOME_AED', 'Please Select Additional Source of Income', 'error');
		return false; 
	}
	if(!getValue('ADDITIONAL_SOURCES_INCOME_AED').includes('80') && getValue('LISTVIEW_ADD_SRCOFCNTRY') ==null){
		showMessage('LISTVIEW_ADD_SRCOFCNTRY', 'Please Select Additional Source of Income Country', 'error');
		return false; 
	}
	if (privateClient== 'Y'){
	 if(getValue('Customer_PEP_Rad') ==undefined || getValue('Customer_PEP_Rad') ==''){
		showMessage('Customer_PEP_Rad', 'Please Select Pep Question 1', 'error');
		return false; 
	}
	if(getValue('Person_Associated_Rad') ==undefined || getValue('Person_Associated_Rad') ==''){
		showMessage('Person_Associated_Rad', 'Please Select Pep Question 2', 'error');
		return false; 
	}
	if(getValue('Person_Power_Rad') ==undefined || getValue('Person_Power_Rad') ==''){
		showMessage('Person_Power_Rad', 'Please Select Pep Question 3', 'error');
		return false; 
	}
	if(getValue('Customer_Entrusted_Man_Rad') ==undefined || getValue('Customer_Entrusted_Man_Rad') ==''){
		showMessage('LISTVIEW_ADD_SRCOFCNTRY', 'Please Select Pep Question 4', 'error');
		return false; 
	}
	}if (privateClient== 'N') {
		if(getValue('Customer_Authorized_Rad') ==undefined || getValue('Customer_Authorized_Rad') ==''){
		showMessage('Customer_Authorized_Rad', 'Please Select Pep Question 1', 'error');
		return false; 
	}
	if(getValue('Customer_Entrusted_Rad') ==undefined || getValue('Customer_Entrusted_Rad') ==''){
		showMessage('Customer_Entrusted_Rad', 'Please Select Pep Question 2', 'error');
		return false; 
	}
  }
 }
}


function mandatoryPreAssesmentField() { 
    var privateClient= getValue('PRIVATE_CLIENT');
	var requestType = getValue(REQUEST_TYPE);
	   //Issue Mandetory field bot required for Upgrade and Category change Only
		if(!(requestType == ('Upgrade') || requestType == ('Category Change Only'))) {
	 if (getValue('PRIVATE_CLIENT') == null){
		showMessage('PRIVATE_CLIENT', 'Please Select Value For Private Client', 'error');
		return false;
	 }
	if(privateClient !='Y'){
        if (getValue('KYC_CUST_BLACKLIST') == null){
			showMessage('KYC_CUST_BLACKLIST', 'Please Select Value for Question 1', 'error');
			return false;
		}else if (getValue('KYC_CUST_HAWALADRA_SERV') == null){
			showMessage('KYC_CUST_HAWALADRA_SERV', 'Please Select Value for Question 2', 'error');
			return false;	
		}else if (getValue('KYC_CUST_NORTH_KORES_RESD') == null){
			showMessage('KYC_CUST_NORTH_KORES_RESD', 'Please Select Value for Question 3', 'error');
			return false;	
		}else if (getValue('KYC_ENTITY_RESIDENT_JURISD') == null){
			showMessage('KYC_ENTITY_RESIDENT_JURISD', 'Please Select Value for Question 4', 'error');
			return false;
		}
		if(requestType != ('Upgrade') && requestType != ('Category Change Only')) {
			if (getValue('QVAR_Product_Value') == null){
				showMessage('BTN_SUBMIT', 'Please Select Atleast One Product', 'error');
				return false;
			}
		}
	} else if(privateClient =='Y'){
		    if (getValue('KYC_CUST_BLACKLIST1') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 1', 'error');
				return false;
			}else if (getValue('KYC_BARRIER') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 2', 'error');
				return false;	
			}else if (getValue('KYC_ENTITY_SHELL') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 3', 'error');
				return false;	
			}else if (getValue('KYC_CUST_HAWALADRA') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 4', 'error');
				return false;
			}else if (getValue('KYC_CUST_NORTH_KORES') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 5', 'error');
				return false;	
			}else if (getValue('kYC_PATNER_UBO') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 6', 'error');
				return false;	
			}else if (getValue('KYC_MANUFACTURING') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 7', 'error');
				return false;	
			}else if (getValue('KYC_LEGAL_GAMING') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 8', 'error');
				return false;
			}else if (getValue('KYC_OFFICE_VELLI') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 9', 'error');
				return false;
			}else if (getValue('KYC_VIRTUAL_CURRENCY') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 10', 'error');
				return false;
			}else if (getValue('KYC_ENTITY_RE') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 11', 'error');
				return false;
			}else if (getValue('KYC_NOMINEE') == null){
				showMessage('BTN_SUBMIT', 'Please Select Value for Question 12', 'error');
				return false;
			}
			if(requestType != ('Upgrade') && requestType != ('Category Change Only')) {
				if (getValue('QVAR_Product_Value') == null){
                    showMessage('BTN_SUBMIT', 'Please Select Atleast One Product', 'error');
					return false;
				}
			}
	}
 }

}
function visiblePreAssesmentField(){//AO CCO AND UPGRADE
var requestType = getValue(REQUEST_TYPE);
if(requestType == ('Upgrade') || requestType == ('Category Change Only')) {
		setStyle('KYC_PRE-ASSESSMENT','visible', 'false');
	}
}



//ADDDED FOR ARCHIVAL 10-11-2023
function openArchivalWi(){
	
 if(getValue('ARCHIVAL_WI_NAME') != ''){ 
        setStyle('Archival_Integration_Iframe', 'visible', 'true'); 
	    var wd_uid=getWorkItemData("sessionId");
	    var userName=getWorkItemData("userName");
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
		var cabinetName = getWorkItemData("cabinetName");
		var ipPort=document.URL.substr(document.URL.indexOf('//')+2,document.URL.indexOf('AO')-9);
		var jspURL=sLocationOrigin+"/webdesktop/login/mailloginclient.jsf?CalledFrom=OPENWI&OAPDomHost="+ipPort+"&OAPDomPrt=https:&" +
		"ReferredFromSameOrigin=false&ReferredFromSameOrigin=false&CabinetName="+cabinetName+"&pid="+
		getValue('ARCHIVAL_WI_NAME')+"&wid=1&SessionId="+wd_uid+"&UserIndex="+"&UserName="+userName;
		console.log(jspURL);
		document.getElementById('Archival_Integration_Iframe').src=jspURL;
	}
	
}

function mandatoryEidaNo(){
	var resEida = getValue('drp_reseida');
	var eidaExpiry = getValue('EIDA_EXPIRY_DATE');
	console.log('resEida: ' + resEida);
	console.log('eidaExpiry: ' + eidaExpiry);
 if(resEida != 'Yes' &&(eidaExpiry == null || eidaExpiry == '')){ 
       showMessage('EIDA_EXPIRY_DATE', 'Please Select EIDA_EXPIRY_DATE', 'error');
		return false;
	}	
}

/*
function setRowColorForFulfullmentGrid() {
	var rowCount = getGridRowCount('fulfillmentTable');
	for (var row=0; row<rowCount; row++) {
		var status = getValueFromTableCell('fulfillmentTable', row, 1);
		if (status == 'Success') {
			setRowColorInListView('fulfillmentTable', row, '32CD32');
		} else if (status == 'Error') {
			setRowColorInListView('fulfillmentTable', row, 'FF0000');
		} else {
			setRowColorInListView('fulfillmentTable', row, 'FFFFFF');
		}
	}
}
*/