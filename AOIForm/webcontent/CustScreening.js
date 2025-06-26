function onCustScreeningLoad(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	setStyle(CRO_DEC,PROPERTY_NAME.DISABLE,TRUE);
	setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,TRUE);
	disableControls(disableControlskyc);
	disableControls(disableEmployeeAddress);
  	onLoadCustScreening();
  	disableControls(familyBanking);
  	openTrsdJsp() //added by reyaz 26-05-2023
}

function onLoadCustScreening(){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var decision = getValue(CRO_DEC);
	secAccRelCPDDisable();
	//setStyle(SEARCH_EIDA_CARDNO,PROPERTY_NAME.DISABLE,TRUE);
	var SEC_CUST_DIS = [CRO_SYS_DEC,SANC_FINAL_ELIGIBILITY,HIDDEN_SEC_ACC_REL,SEC_CRS_DETAILS,
			FRAME_CLIENTQUESTIONS,SEC_CONTACT_DET_RA,SEC_CONTACT_DET_PA,SEC_CONTACT_DET_CP,SEC_INT_DETAIL];
	var CHK_CUST_DIS = [CK_PER_DET,CHK_CONTACT_DET,CHK_INTERNAL_SEC,CHECKBOX_FATCA,CRS_CB,'KYC_PRE-ASSESSMENT','LISTVIEW_ADD_SRCOFCNTRY','LISTVIEW_PUR_ACC_RELATION','ACC_IN_ANTHR_BNK_UAE','DUAL_NATIONALITY','CHANNEL_RISK_AO','LISTVIEW_SRCOFINCOME','LISTVIEW_ADD_SRCOFINCOME','Customer_Authorized_Rad','Customer_Entrusted_Rad'];
	disableControls(SEC_CUST_DIS);// added 6 sections in frame4 as frame 4 is entire custinfotab
	disableControls(CHK_CUST_DIS);
	
	var req_type = getValue(REQUEST_TYPE);
	setTabVisible();
	//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	setKYCFlagPrivateclient(); //AO Dcra
	pepAssesment();
	visiblePreAssesmentField();//AO CCO AND UPGRADE
}

function handleCustScreeningEvent(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEventCustScreening(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEventCustScreening(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusCustScreeningEvent(event);
	}
}
function clickEventCustScreening(event){
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if(controlName == BTN_CNTRL_URL){
		executeServerEvent(controlName, event.type, '', false);
	} else if (controlName == BTN_CHECK_VIEW_DETAILS){
		var iListViewSelectedRow = getSelectedRowsIndexes(CHECK_TYPE_LVW);
		var iProcessedCustomer=getValue(SELECTED_ROW_INDEX);
		var iRowCount = getGridRowCount(CHECK_TYPE_LVW);
		if(iRowCount==0) {
			showMessage('', 'NO row in the grid.', 'error');
		}
		else if(iRowCount>0 && iListViewSelectedRow.length==0) {
			showMessage('', 'Please select a row first.', 'error');
		} else {
			var iSelectedRow=iListViewSelectedRow[0];
			console.log("iSelectedRow---"+iSelectedRow);
			var wi_name = getWorkItemData('processInstanceId');
			//setTabStyle("tab4",12, "visible", "true");
			setTabStyle("tab4",13, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
			//var jspURL = sLocationOrigin+'/AO/CustomFolder/product_list.jsp?WI_NAME='+wi_name+'&ACC_CLASS='+getValue(ACC_HOME_BRANCH)+'&TABLE=USR_0_PRODUCT_OFFERED';
			var jspUrl= "/webdesktop/AO/viewBlackListData.jsp?WI_NAME="+wi_name+"&CUST_NO="+iProcessedCustomer+"&TYPE=INT&SLNO="+iSelectedRow+"&TABLE_NAME="+USR_0_BLACKLIST_DATA;
			alert(jspURL);
			document.getElementById('PRODUCT_JSP').src=jspURL;
			//selectSheet('tab4',12);
			selectSheet('tab4',13);
		}
	}else if (controlName == BTN_VIEWDETAILS_SANCT){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BUTTON_SUBMIT){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_DOCUMENT_LINK){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_RECHECK_SANC){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_ADD_SANC_SCRN){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_MODIFY_SANC_SCRN){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_DELETE_SANC_SCRN){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == TRSD_SYS_CALC_RES){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_TRSD_CHECK){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == VIEW){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == SANCT_RISK_CURRENT_RSK_BANK){
		executeServerEvent(controlName, event.type, '', false);
	}else if (controlName == BTN_SANC_CALCULATE){
		//setValues({[CPD_MTCH_FOUND]: 'VALUE' }, true);
		setFieldValue(CPD_MTCH_FOUND,'VALUE');
	}else if (controlName == BTN_APP_LEVEL_RISK){
		executeServerEvent(controlName, EVENT_TYPE.CLICK, '', false);
	} else if (event.target.id == (EDIT)) {	
		executeServerEvent(event.target.id, event.type, '', false);
	}
 }

function changeEventCustScreening(event) {
	var workstepName = getWorkItemData('activityName');
	var controlName = event.target.id;
	var controlValue = getValue(controlName);
	if (controlName == SANCT_RISK_CURRENT_RSK_BANK){
		executeServerEvent(controlName, event.type,controlValue, false);
	}
	
}

function lostFocusCustScreeningEvent(event) { //yamini
	var workstepName = getWorkItemData('activityName');
	var winame = getWorkItemData('processInstanceId');
	if(WORKSTEPS.DDE_CUSTOMER_INFO == (workstepName)) {	
	}
}	

function saveAndNextPreHookCustScreen(tabid){
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	pepLogic();
	console.log('Input saveAndNextPreHookCustScreen: ' + input);
	var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
	console.log('Save and next response:: '+response);
	if(response != '' && response != undefined){
		var jsondata = handleAOResponse(response);
		if (!jsondata.success){
			return false;
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function onClickTabCustScreening(tabId,sheetIndex,eventCall){
	if(sheetIndex == 1){
		if(getValue(DATA_ENTRY_MODE) == ('Quick Data Entry')){
			//clearComparisonFields();
		} 
		else {
			setAutoFilledFieldsLocked();	
		}
	}

	console.log(sheetIndex);
	var input = event.target.innerHTML+','+sheetIndex;
	executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, false);
}

function postServerEventHandlerCustScreening(controlName, eventType, responseData) {
	
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		if ("" != jsonData.message && null != jsonData.message) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
					showMessage(arr[0], arr[1], 'error');
			}
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		if(controlName == VIEW){
			if(jsonData.success && 'DedupeSelectedIndex' == jsonData.message){
				//write code to set index in grid - sahil
			}
		}
		break;
	case EVENT_TYPE.CLICK:
		if (controlName == BUTTON_SUBMIT){ 
			if(jsonData.success) {
				saveWorkItem();
				completeWorkItem();
			}
		} else if (controlName == 'BTN_TRSD_CHECK'){  //added by reyaz 03-05-2023
			openTrsdJsp();   
		} else if(WORKSTEPS.CUST_SCREEN == (workstepName) && 'saveNextTabClick' == controlName && jsonData.success) {
			openTrsdJsp();   //added by reyaz 03-05-2023
//			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if('afterSaveNext' == controlName) {
			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
		} else if(WORKSTEPS.CUSTOMER_SCREEN_QDE == (workstepName) && 'saveNextTabClick' == controlName 
				&& jsonData.success){
			openTrsdJsp();   //added by reyaz 03-05-2023
			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, ','+getSheetIndex('tab4'), false);
		} else if(controlName == EDIT){
			saveWorkItem();
			completeWorkItem();
		}
		break;
	}
}