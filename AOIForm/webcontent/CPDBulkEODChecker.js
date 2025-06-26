var readOnly = false;
var saveNextClick = false;
function onCPDBulkEODCheckerFormLoad(){
	var workstepName = getWorkItemData('activityName');
	var wiName  = getWorkItemData('processInstanceId');
	console.log('WorkItem Name:::: ' +wiName);
	console.log('inside onCPDBulkEODCheckerFormLoad : ');
	console.log('SELECTED_ROW_INDEX: '+getValue(SELECTED_ROW_INDEX));
	if(getValue(SELECTED_ROW_INDEX) == null || getValue(SELECTED_ROW_INDEX) == ''){
		setFieldValue(SELECTED_ROW_INDEX,'0');
	}
	if(!(getValue('SKIPUAEPASS_REASON') == '' || getValue('SKIPUAEPASS_REASON') == null)){
		setStyle('SKIPUAEPASS_REASON','disable', 'false');
	}
	var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
	var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
	var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
	var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
	var controlNames = [WI_NAME,CURR_WS_DETAIL,'TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID','TXT_CURRWS',CRO_DEC];
	var controlValues = [wiName,workstepName,CustName,CustDob,CustId,workstepName,''];	
	setMultipleFieldValues(controlNames,controlValues);
    setStyle(IS_PROD_APP_REQ,PROPERTY_NAME.VISIBLE,'false');
	console.log('WorkItem Name:::: ' + wiName);
	/*if('' != getValue(SELECTED_ROW_INDEX)) {
		setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
	}*/
	var disableControlsPersonal = [SI_FRST_PAYMNT,SI_LST_PAYMNT,CK_PER_DET,PD_ANY_CHNG_CUST_INFO,CHK_CONTACT_DET,FRAME_CLIENTQUESTIONS,
	                               CHK_INTERNAL_SEC,SEC_CONTACT_DET_CP,SEC_CONTACT_DET_PA,SEC_CONTACT_DETAILS,SEC_INT_DETAIL];
	disableControls(disableControlsPersonal);
	var disableControlsKYC = [SEC_CONTACT_DET_RA,CHK_GEN_INFO,SEC_GEN_INFO,CHK_EMP_DETAIL,SEC_FUND_EXP_RELTNSHP,CHK_FUNDING_INFO,
	                          SEC_ASSESS_OTH_INFO,CHK_RISK_ASS,SEC_BNK_REL_UAE_OVRS,CHK_BANKING_RELATION,SEC_SIGN_OFF,'KYC_PRE-ASSESSMENT'];
	disableControls(disableControlsKYC);
	var disableControlsFATCA = [CHECKBOX_FATCA,FRAMEFATCA];
	disableControls(disableControlsFATCA);
	var disableControlsCRS = [CRS_CB,SEC_CRS_DETAILS];
	disableControls(disableControlsCRS);
	var disableControlsSancScreen = [BTN_TRSD_CHECK,SEC_SS_TRSD,SANC_FINAL_ELIGIBILITY,BTN_SANC_CALCULATE,
	                                 SEC_SS_RISK_ASSESS,BTN_NEXT_CUST_SANCT];
	disableControls(disableControlsSancScreen);
	if(getValue(CRO_DEC) == ('Approve')) {	// on loading if decision is defaulted approve then rject_reason is enabled
		setStyle(CRO_REJ_REASON, PROPERTY_NAME.DISABLE, 'true');
		var controlnames = [CRO_REJ_REASON];
		clearAOControls(controlnames);
	}
	disableControls(disableControlskyc);
	disableControls(disableEmployeeAddress);
	if(getValue('SOURCING_CHANNEL') == 'LAPS'){
		console.log('cpd maker 3 step'+getValue('SOURCING_CHANNEL'));
		setFieldValue('LAPS_REFERENCE_NUM',getValue('CHANNEL_REF_NUMBER'));
	}
	var req_type = getValue(REQUEST_TYPE);
	if(req_type.toUpperCase() == "NEW ACCOUNT WITH CATEGORY CHANGE" || req_type.toUpperCase() == "CATEGORY CHANGE ONLY"
	||req_type.toUpperCase() == "UPGRADE") {	
		setTabStyle("tab3",13, "visible", "true");
	}
	setTabStyle("tab3",6, "visible", "false");
	setTabStyle("tab3",8, "visible", "false");
//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
	if(getValue(REQUEST_TYPE) == 'New Account') {	//family banking
	    setTabStyle("tab3",17, "visible", "false");
	}
	//added by shivanshu for dcra
	if(getValue(REQUEST_TYPE) == ('Downgrade') || getValue(REQUEST_TYPE) == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	disableControls(familyBanking);
	openTrsdJspCPD();
	PepNationality();
	pepAssesment();
	visiblePreAssesmentField();//AO CCO AND UPGRADE
	openArchivalWi();  //ADDDED FOR ARCHIVAL 10-11-2023
	//setStyle("Others","visible","true");
}

function handleCPDBulkEODCheckerEvent(event){
	var workstepName = getWorkItemData('activityName');
		if (event.type == EVENT_TYPE.CLICK) { 
			clickCPDBulkEODCheckerEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeCPDBulkEODCheckerEvent(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
			lostFocusCPDBulkEODCheckerEvent(event);		
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
			gotFocusCPDBulkEODCheckerEvent(event);
		} else if (event.type == EVENT_TYPE.LOAD) {
			loadCPDBulkEODCheckerEvent(event);
		}
}

function loadCPDBulkEODCheckerEvent(event){
	console.log('inside loadCPDBulkEODCheckerEvent >>');
}

function  clickCPDBulkEODCheckerEvent(event){
	var workstepName = getWorkItemData('activityName');
	var pControlName = event.target.id;
	var pControlValue = getValue(pControlName);
	if(event.target.id == BTN_ECB_REFRSH){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == VIEW){
		executeServerEvent(event.target.id, event.type, '', false);
	}  else if(event.target.id == BUTTON_SUBMIT ) {
		console.log('Inside Submit button');
//		saveWorkItem();
		var decision = getValue(CRO_DEC);
		if(decision == ''){
			showMessage(CRO_DEC, 'Please select user decision', 'error');
			return;
		} else if(decision != 'Approve') {
			var rejectionReason=getValue(CRO_REJ_REASON);
			var rejectionRemarks=getValue(CRO_REMARKS);
			if(rejectionReason=='') {
				showMessage(CRO_REJ_REASON,'Please Select Rejection Reason.','error');
				return;
			} else if(rejectionRemarks=='') {
				showMessage(CRO_REMARKS,'Please Fill Remarks.','error');
				return;
			}
			setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
			executeServerEvent(event.target.id, event.type, '', false);
		} else {
			var nigCPDMaker = getValue(NIG_CPDMAKER);
			setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
			if(nigCPDMaker == 'yes'){
				showConfirmDialog(CA0008, confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent(event.target.id, event.type, 'confirmYes', false);
						saveWorkItem();
					} else if(result == false) {
						saveWorkItem();
						executeServerEvent(event.target.id, event.type, '', false);
					}
				});
			} else {
				executeServerEvent(event.target.id, event.type, '', false);
			}
		}
	} else if(event.target.id == 'BTN_FULFILLMENT_START'){  //changes for fulfillment
		console.log('BTN_FULFILLMENT_START');
		executeServerEvent(event.target.id, event.type,'', false);
	} else if(event.target.id == 'BTN_FULFILLMENT_REFRESH'){
		console.log('BTN_FULFILLMENT_REFRESH');
		executeServerEvent(event.target.id, event.type,'', false);
	} 
}

function changeCPDBulkEODCheckerEvent(event){
	console.log('Change Event of CPDBulkEODChecker Started for');
	if(event.target.id == CHECKBOX_EIDANO_EIDA){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_EIDANO_FCR){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_EIDANO_MANUAL){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_TELE_MOB_MANUAL){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_TELE_MOB_FCR){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_TELE_MOB_EIDA){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_EMAIL_EIDA){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_EMAIL_FCR){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_EMAIL_MANUAL){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_DOB_FCR){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_DOB_EIDA){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CHECKBOX_DOB_MANUAL){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == P_ECB_CHQ_VALIDATION){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == DOC_APPROVAL_OBTAINED){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == COURT_ORD_TRADE_LIC){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CP_CITY || event.target.id == RA_CITY || event.target.id == PA_CITY){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRO_DEC) {
		console.log('inside decision');
		var decision = getValue(CRO_DEC);
		if(decision == 'Approve' || decision == ''){
			console.log("change");
			clearValue(CRO_REJ_REASON);
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'true');
		} else if(decision == 'Return' || decision == 'Return to Originator'){
			setStyle(CRO_REJ_REASON,PROPERTY_NAME.DISABLE,'false');
		}
	}
}

function lostFocusCPDBulkEODCheckerEvent(event){
	console.log('inside lostFocusCPDBulkEODCheckerEvent >>');
}

function gotFocusCPDBulkEODCheckerEvent(){
	console.log('inside gotFocusCPDBulkEODCheckerEvent >>');
}

function postServerEventHandlerCPDBulkEODChecker(controlName, eventType, responseData) {
	var jsondata = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('control name: '+ controlName +',event type: '+ eventType);
	console.log('response data:');
	console.log(jsondata);
	if (null != jsondata.message && '' != jsondata.message
			&& !(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1)) {
		if((jsondata.message).indexOf('###') != -1){
			var arr = jsondata.message.split('###');
			showMessage(arr[0], arr[1], 'error');
			if(arr[1] == 'Maker & Checker cannot be same!') {
				readOnly = true;
			}
		} else if(jsondata.message.indexOf('The user is not authorized to access Staff Data.') != -1){
			showMessage('', jsondata.message, 'error');
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
	PepNationality();
		setStyle('acc_relation','disable','false');
		console.log('SELECTED_ROW_INDEX: '+getValue(SELECTED_ROW_INDEX));
		if('' != getValue(SELECTED_ROW_INDEX)) {
			setFieldValue(ACC_RELATION+'_'+getValue(SELECTED_ROW_INDEX), TRUE);
			var iProcessedCustomer = getValue(SELECTED_ROW_INDEX);
			var CustName = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 1);
			var CustDob = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 5);
			var CustId = getValueFromTableCell(ACC_RELATION, iProcessedCustomer, 2);
			var controlNames = ['TXT_CUSTOMERNAME','TXT_DOB','TXT_CUSTOMERID'];
			var controlValues = [CustName,CustDob,CustId];	
			setMultipleFieldValues(controlNames,controlValues);
		} 
		if((jsondata.message).indexOf('#') != -1) {
			var fieldList = (jsondata.message).split('#');
			for(var i=0; i<fieldList.length; i++){
				setStyle(fieldList[i], 'fontcolor', '00cc00');
			}
		}
		saveWorkItem();
		//if(getValue('LISTVIEW_PUR_ACC_RELATION')=='380'){
			setStyle("Others","visible",'true'); //Jamshed
		//}
		
		break;
	case EVENT_TYPE.CLICK:
		if('saveTabClick' == controlName) {
//			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
			/*if(saveNextClick) {
				saveNextClick = false;
				executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, ','+getSheetIndex('tab3'), false);
			}*/
		} else if('afterSaveNext' == controlName) {
			var label = '';
			//if(getSheetIndex('tab3') == 18) {
			if(getSheetIndex('tab3') == 19) {// Changes for Family Banking
				label = 'Decision';
			} else if(getSheetIndex('tab3') == 13){
				label = 'Category Change';
			} else if(getSheetIndex('tab3') == 14){
				label = 'Standing Instruction';
			} else if(getSheetIndex('tab3') == 12){
				label = 'Delivery Preferences';
			}
			executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, label+','+getSheetIndex('tab3'), false);
		} else if('TABCLICK' == controlName) {
			//if(getSheetIndex('tab3') == 18) {
			if(getSheetIndex('tab3') == 19) {// Changes for Family Banking
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
			if(jsondata.message.indexOf('Do you still want to proceed with account opening?') != -1) {
				showConfirmDialog(jsondata.message, confirmDialogButtons, function(result) {
					if(result == true) {
						executeServerEvent('confirmAppRisk', EVENT_TYPE.CHANGE, 'confirmAppRisk', false);
					} else if(result == false) {
						selectSheet('tab3',0);
						return;
					}
				});
			}
		} else if(BUTTON_SUBMIT==controlName){
			if(jsondata.success) {
				if(jsondata.message == CA008) {
					showConfirmDialog(CA008, confirmDialogButtons, function(result) {
						if(result == true) {
							executeServerEvent('postSubmit', event.type, '', false);
						//	var submitResponse = executeServerEvent('postSubmit', event.type, '', false);
						//	var jsonSubmit = handleAOResponse(submitResponse);
							/*if(jsonSubmit.success && jsonSubmit.message == '') {
								saveWorkItem();
								completeWorkItem();
							} else {
								saveWorkItem();
							} */
						} else if(result == false) {
							saveWorkItem();
							setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
							return;
						}
					});
				}
			} else {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
		} else if ('postSubmit' == controlName){
			if(jsondata.success && jsondata.message == '') {
				saveWorkItem();
				completeWorkItem();
			} else {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
				saveWorkItem();
			}
		}
		else if (controlName == 'BTN_FULFILLMENT_START' || controlName == 'BTN_FULFILLMENT_REFRESH') {
			// SET ROW COLOR BASED ON STATUS
			setRowColorForFulfullmentGrid();
		}
		break;
	case 'handlingJSPData':
		if(BUTTON_SUBMIT==controlName && jsondata.success && jsondata.message == CA008) {
				showConfirmDialog(CA008, confirmDialogButtons, function(result) {
					if(result == true) {
						var submitResponse = executeServerEvent('postSubmit', event.type, '', false);
						var jsonSubmit = handleAOResponse(submitResponse);
						if(jsonSubmit.success && jsonSubmit.message == '') {
							saveWorkItem();
							completeWorkItem();
						} else {
							saveWorkItem();
							setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
							return;
						}
					} else if(result == false) {
						saveWorkItem();
						setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
						return;
					}
				});
			}
		break;
	case EVENT_TYPE.CHANGE:
		break;
	case EVENT_TYPE.LOSTFOCUS: 				
		break;	
	}
}

function onRowClickCPDBulkEODChecker(listviewId,rowIndex) {
}

function saveAndNextPreHookCPDBulkEODChecker(tabid){
	saveNextClick = true;
	console.log('tabid saveAndNextPreHookCPDBulkEODChecker: ' + tabid);
	var input = event.target.innerHTML+','+getSheetIndex(tabid);
	console.log('input saveAndNextPreHookCPDBulkEODChecker: ' + input);
	//added by reyaz 04042023
	PepNationality();
    var tab = getSheetIndex(tabid);
//	if(tab == 4){
//		if(getValue('CRS_CERTIFICATION_OBTAINED') =='No' || getValue('CRS_Classification') == 'UPDATED-UNDOCUMENTED'
//		    || getValue('CRS_Classification') == 'UNDOCUMENTED' || getValue('CRS_Classification') == ''){ 
//		     showMessage('CRS_Classification', 'CRS Classification should be Updated Documented', 'error');
//	     }
//	}
	if(!readOnly) {
		var response = executeServerEvent('saveTabClick', EVENT_TYPE.CLICK, input, true);
		console.log('save and next response:: '+response);
		if(response != '' && response != undefined){
			var jsondata = handleAOResponse(response);
			if (!jsondata.success){
				return false;
			}
		}
	}
	executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
	return true; 
}

function onClickTabCPDBulkEODChecker(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	var response;
	console.log('tabId:::'+tabId);
	console.log('input onClickTabCPDBulkEODChecker: ' + input);
	if(!readOnly) {
		if(sheetIndex == 1 || sheetIndex == 2 || sheetIndex == 3 || sheetIndex == 4 || sheetIndex == 5){
			//clearPersonalData();
			//clearKYCData();
			//clearComparisonFields(); 
			//clearRiskData();
			//setManualChecksBlank();
//			response = executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
		} 
		executeServerEvent('TABCLICK', EVENT_TYPE.CLICK, input, false);
		selectSheet(tabId,sheetIndex);
	}
//	var jsonData = handleAOResponse(response);
//	console.log(jsonData);//sharanend
	console.log('sheetIndex='+sheetIndex+'eventCall='+eventCall);	
}

function selectRowHookCPDCheckerFourStep(tableId,selectedRowsArray,isAllRowsSelected) {
	if(ACC_RELATION == tableId && !readOnly) {
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
		return  false;
	}
}
/*function showOpenCallJspTabCPDBulkEODChecker(workItemName) {
	var returnVal="";
	var parameterJSP = workItemName;
	var urlDoc = document.URL;
	var jspURL="";
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('AO')-1);
	jspURL=sLocationOrigin+"/AO/CustomFolder/Integration_Call_Status.jsp?WI_NAME="+workItemName;
	document.getElementById('sheet63_link').textContent = 'Application Orchestration';
	document.getElementById('PRODUCT_JSP').src = jspURL;
	setTabStyle('tab3',19, "visible", "true");
	selectSheet('tab3',19);	
}*/
