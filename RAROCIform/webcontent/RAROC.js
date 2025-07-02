/**
 * Process: SME
 */
var facility_row_tick =false;//m26 checking if facility row is ticked or not.
var CFM_add_flag =false;//m26 checking if clicking + button on collateral facility grid or not.
function onFormLoad() {
	console.log('***** inside onFormLoad *****');
	var workstepName = getWorkItemData('ActivityName');
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	lockSMESection('SME_Cust_Details_Frame,Frame1');
	//start edit by mohit 13-11-2024 to disable button on read-only mode 
	//if(window.parent.READ_MODE=="R"){
		if(window.parent.wiViewMode=="R"){
		console.log("Inside read_mode  wiViewMode is R");
		disableControls('customerRealisedDetails');//FETCH REALIZED RAROC BUTTON
		disableControls('FACILITY_BUTTON_MODIFY');//MODIFY BUTTON
		disableControls('onButtonClick');//CALCULATE REPAYMENT DETAILS BUTTON
		disableControls('projectedDetails');//FETCH PROJECTED RAROC BUTTON
	}
	//end edit by mohit 13-11-2024 to disable button on read-only mode 
	if (workstepName == WORKSTEPS.Initiator) {
		onInitiatorLoad();
	} else if (workstepName == WORKSTEPS.CommonPool) {
		onCommonPoolLoad();
	} else if (workstepName == WORKSTEPS.Exit) {
		onExitLoad();
	}
	//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
}

function handleEvent(object, event) {
	console.log('Event: ' + event.target.id + ', ' + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusEvent(event);
	}
	//SetConditionalFields();
}





function clickEvent(event) {
	var workstepName = getWorkItemData('ActivityName');
	if (workstepName == WORKSTEPS.Initiator) {
		handleInitiatorEvent(event);
	} else if (workstepName == WORKSTEPS.CommonPool) {
		handleExitEvent(event);
	} else if (workstepName == WORKSTEPS.Exit) {
		handleCommonPoolEvent(event);
	}
}

function changeEvent(event) {
	var workstepName = getWorkItemData('ActivityName');
	var workstepName = getWorkItemData('ActivityName');
	if (workstepName == WORKSTEPS.Initiator) {
		handleInitiatorEvent(event);
	} else if (workstepName == WORKSTEPS.CommonPool) {
		handleExitEvent(event);
	} else if (workstepName == WORKSTEPS.Exit) {
		handleCommonPoolEvent(event);
	}

}

/*function postServerEventHandler(controlName, eventType, responseData) {
	var jsonData = handleResponse(responseData);
	var workstepName = getWorkItemData('ActivityName');
	console.log('Control Name: '+ controlName +', Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	console.log('message in post: '+jsonData.message);
/*	if((jsonData.message).includes('###')){
		var arr = jsonData.message.split('###');
		showMessage(arr[0], arr[1], 'error');
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		if(workstepName==WORKSTEP.SME_MAKER){
		}
		break;
	case EVENT_TYPE.CLICK:
		
		if (controlName == BTN_SUBMIT){
			if(!jsonData.success){
				saveWorkItem();
			} else {
				saveWorkItem();
				completeWorkItem();
			}
		} 
		break;
	}
}*/



function handleResponse(responseData) {
	var jsonData;
	try {
		jsonData = JSON.parse(responseData); // STRING TO JSON
	} catch (error) {
		jsonData = responseData; // ALREADY A JSON
	}
	return jsonData;
}

function disableControls(controlNames) {
	var arr = controlNames.split(",");
	for (var i = 0; i < arr.length; i++) {
		console.log('disabling control: ' + arr[i]);
		setStyle(arr[i], PROPERTY_NAME.DISABLE, 'true');
	}
}

function enableControls(controlNames) {
	var arr = controlNames.split(",");
	for (var i = 0; i < arr.length; i++) {
		console.log('enabling control: ' + arr[i]);
		setStyle(arr[i], PROPERTY_NAME.DISABLE, 'false');
	}
}

function showControls(controlNames) {
	var arr = controlNames.split(",");
	for (var i = 0; i < arr.length; i++) {
		console.log('showing control: ' + arr[i]);
		setStyle(arr[i], PROPERTY_NAME.VISIBLE, 'true');
	}
}

function hideControls(controlNames) {
	console.log('controlNames:' + controlNames);
	var arr = controlNames.split(",");
	for (var i = 0; i < arr.length; i++) {
		console.log('hiding control: ' + arr[i]);
		setStyle(arr[i], PROPERTY_NAME.VISIBLE, 'false');
	}
}

function lockSMESection(controls) {
	var arr = controls.split(',');
	for (var i = 0; i < arr.length; i++) {
		console.log('locking control: ' + arr[i]);
		setStyle(arr[i], 'lock', 'true');
	}
}

/*function onRowClick(listviewId,rowIndex) { 
	console.log('listviewId='+listviewId+' rowIndex='+rowIndex);
	return false;
}
*/

function selectRowHook(tableId, selectedRowsArray, isAllRowsSelected) {
	console.log("Inside selectRowHook");
	facility_row_tick=false;
	console.log('tableId=' + tableId + ' selectedRowsArray=' + selectedRowsArray);
	var listviewId = tableId;
	var rowIndex = selectedRowsArray[0];
	var data = getSelectedRowsDataFromTable(listviewId);
	console.log('row index = ' + rowIndex);
	console.log('data = ' + data);
	var row = data[0];
	var facilityID = row[4];

	if (listviewId == 'LV_FacilityDetails') {
		facility_row_tick=true;
		executeServerEvent("onRowClick", "click", facilityID, false);
		//SetConditionalFields();
	}
	//start edit by mohit 27-06-2024 collateral grid
	/*if (listviewId == 'CollateralGridDetails') {
		var CollateralRow = data[0];
		var CollateralType = CollateralRow[0];
		executeServerEvent("CollateralonRowClick", "click", CollateralType, false);
		//SetConditionalFields();
	}*/ //NOT NEEDED TO REMOVE
	//end edit by mohit 27-06-2024 collateral grid

	/*	setValue('Q_NG_RAROC_FACILITY_Product',row[1]); 
		addItemInCombo('Q_NG_RAROC_FACILITY_Product_Desc',row[2]);
		setValue('Q_NG_RAROC_FACILITY_Product_Desc',row[2]); 
		setValue('Q_NG_RAROC_FACILITY_Facility_ID',row[4]); 
		setValue('Q_NG_RAROC_FACILITY_Main_Sub_Limit',row[5]); 
		setValue('Q_NG_RAROC_FACILITY_TAB_EXP_DT_Previously_Appr',row[41]); 
		setValue('Q_NG_RAROC_FACILITY_TAB_EXP_DT_Realized',row[42]); 
		setValue('Q_NG_RAROC_FACILITY_TAB_EXP_DT_Proposed',row[43]); 
		setValue('Q_NG_RAROC_FACILITY_TAB_EXP_DT_Sensitized',row[44]); 	
	/*	if(rowIndex >= 0){
			if(LISTVIEW_CUST_DETAILS == listviewId){
				setValues({[CID]: getValueFromTableCell(listviewId,rowIndex,0)}, true);
			}
		} else {
			console.log('row index null');
			if(LISTVIEW_CUST_DETAILS == listviewId){
				clearSMEControls(SME_CUST_DETAILS_FIELDS);
			}
		}*/

}

function clearSMEControls(controls) {
	/*	for (var i = 0; i < controls.length; i++) {
			console.log('clearing control: ' + controls[i]);
			setValues({[controls[i]]: ''}, true);
		}*/
}
function setMultipleFieldSingleValues(controlNames, controlValues) {
	var values = {};
	for (var i = 0; i < controlNames.length; i++) {
		console.log('setValue for control: ' + controlNames[i]);
		values[controlNames[i]] = controlValues[0];
	}
	setValues(values, true);
}
function setMultipleFieldValues(controlNames, controlValues) {
	var values = {};
	for (var i = 0; i < controlNames.length; i++) {
		console.log('setValue for control: ' + controlNames[i]);
		values[controlNames[i]] = controlValues[i];
	}
	setValues(values, true);
} function onTabClickRaroc(tabId, sheetindex, eventCall) {
	console.log('inside onClickTab, tabId: ' + tabId + ' and sheetIndex: ' + sheetindex);
	var workstepName = getWorkItemData('ActivityName');
	if (workstepName == WORKSTEPS.Initiator) {
		onClickTabInitiator(tabId, sheetindex, eventCall);
		
	} else if (workstepName == WORKSTEPS.CommonPool) {
		onClickTabCommonPool(tabId, sheetindex, eventCall);
	} else if (workstepName == WORKSTEPS.Exit) {
		onClickTabExit(tabId, sheetindex, eventCall);
	}
}



function getFDValueCalculation() {
	var fdIncome = 0;
	var fdIncomeVal = getValue("table27_FD_INCOME");
	if (isNaN(fdIncomeVal)) {
		setValue('table27_FD_INCOME', '0');
	}
	var fdValueA = getValue("table27_FD_VALUE_A");
	var fdRateC = getValue("table27_FD_RATE_C");
	var fdtenureD = getValue("table27_FD_FOR_TENURE_D");
	fdIncome = (parseFloat(fdtenureD) - parseFloat(fdRateC)) * parseFloat(fdValueA);
	setValue('table27_FD_INCOME', fdIncome);
}

function SetConditionalFields() {
	var repaymentTypeProp = getValue('F_Repayment_Type_Proposed');
	var repaymentTypesensitized = getValue('Rep_Typ_Sensitized');
	var facilityType = getValue('F_FACILITY_TYPE');
	var Treasury_Prod = getValue('Treasury_Prod');
	var IncomeType = getValue('F_INCOME_TYPE');
	var repaymentFreqProp = getValue('F_Repayment_Frequency_Proposed');//bp05//d
	var repaymnetFreqSensitized = getValue('Repay_Freq_Sensitized');
	var IndexProp = getValue('F_Index_Proposed');
	var IndexSensitized = getValue('F_Index_Sensitized');
	var MarginProp = getValue('F_Margin_Commision_Proposed');
	var MarginSensitized = getValue('F_Margin_Commision_Sensitized');
	var notionalProp = getValue('F_Facility_Amount_Proposed');
	var notionalSensitized = getValue('F_Facility_Amount_Sensitized');
	var ExpectedReturnProp = getValue('F_Exp_Ret_Proposed');
	var ExpectedReturnSensitized = getValue('Exp_Ret_Sensitized');
	var MarketRiskProp = getValue('F_Mar_Ris_Proposed');
	var MarketRiskSensitized = getValue('F_Mar_Ris_Sensitized');
	var IncomeAbsolute = getValue('F_INCOME_ABSOLUTE');
	var IncomePerc = getValue('F_INCOME_PERCENTAGE');
	console.log("inside method");
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking') {
		if (repaymentTypeProp == 'Constant' || repaymentTypeProp == 'Linear') {
			EnableRepaymentFrequencyPropComboBox();
			repaymentFreqProp = getValue('F_Repayment_Frequency_Proposed');//bp05//d
			if (repaymentFreqProp == '' || repaymentFreqProp == null) {
				console.log("inside repaymentFreqProp is null condition 1 ");
				showAlertDialog("Please enter the Repayment Frequency value ", true);
				return false;
			}

		}
		if (IncomeType == 'Absolute Income') {
			EnableRepaymentFrequencyPropComboBox();
			if (IncomeAbsolute == '' || IncomeAbsolute == null) {
				console.log("inside repaymentFreqProp is null condition 2 ");
				showAlertDialog("Please enter the Income Absolute value ", true);
				return false;
			}
		}
		//start edit by mohit 01-07-2024 to make INCOME ABSOLUTE or INCOME PERCENTAGE mandatory based on value selected from INCOME TYPE combo
		if (IncomeType == 'Percentage') {
			EnableRepaymentFrequencyPropComboBox();
			if (IncomePerc == '' || IncomePerc == null) {
				console.log("inside repaymentFreqProp is null condition 222 ");
				showAlertDialog("Please enter the Income Percentage value ", true);
				return false;
			}
		}
		//end edit by mohit 01-07-2024 to make INCOME ABSOLUTE or INCOME PERCENTAGE mandatory based on value selected from INCOME TYPE combo
		//start changes by mohit 12-06-2024 for repayment_frequency --> For Bullet, can be defaulted to Bullet
		if(repaymentTypeProp == 'Bullet'){
			DisableRepaymentFrequencyPropComboBox();
		}
		//  end changes by mohit 12-06-2024 for repayment_frequency -->For Bullet, can be defaulted to Bullet
	}
	else if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep' || stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver'
		|| stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver' || stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign') {

		if (repaymentTypesensitized == 'Constant' || repaymentTypesensitized == 'Linear') {
			EnableRepaymentFrequencySensitizedComboBox();
			repaymnetFreqSensitized = getValue('Repay_Freq_Sensitized');
			if (repaymnetFreqSensitized == '' || repaymnetFreqSensitized == null) {
				showAlertDialog("Please enter the Repayment Frequency value ", true);
				return false;
			}
		}
		if (IncomeType == 'Absolute Income') {
			EnableRepaymentFrequencySensitizedComboBox();
			if (IncomeAbsolute == '' || IncomeAbsolute == null) {
				showAlertDialog("Please enter the Income Absolute value ", true);
				return false;
			}
		}
		//start edit by mohit 01-07-2024 to make INCOME ABSOLUTE or INCOME PERCENTAGE mandatory based on value selected from INCOME TYPE combo
		if (IncomeType == 'Percentage') {
			EnableRepaymentFrequencySensitizedComboBox();
			if (IncomePerc == '' || IncomePerc == null) {
				console.log("inside repaymentFreqProp is null condition 222.... ");
				showAlertDialog("Please enter the Income Percentage value ", true);
				return false;
			}
		}
		//end edit by mohit 01-07-2024 to make INCOME ABSOLUTE or INCOME PERCENTAGE mandatory based on value selected from INCOME TYPE combo
		
		//start changes by mohit 12-06-2024 for repayment_frequency -->For Bullet, can be defaulted to Bullet
		if(repaymentTypesensitized == 'Bullet'){
			DisableRepaymentFrequencySensitizedComboBox();
		}
		//  end changes by mohit 12-06-2024 for repayment_frequency -->For Bullet, can be defaulted to Bullet
	}

	/*else if(facilityType == 'Funded'){
		console.log("inside facility");
		if(IndexProp == '' || IndexProp == null){
			showAlertDialog("Please enter the Index value ",true);
			return false;
		}
		else if(IndexSensitized == '' || IndexSensitized == null){
			showAlertDialog("Please enter the Index value ",true);
			return false;
			
		}
		
		
		
	}
	else if(facilityType == 'Non-funded'){
		console.log("inside facility non");
		if(MarginProp == '' || MarginProp == null){
			showAlertDialog("Please enter the Margin/Comission value ",true);
			return false;
		}
		else if(MarginSensitized == '' || MarginSensitized == null){
			showAlertDialog("Please enter the Margin/Comission value ",true);
			return false;
		}
		
		
	}
	else if(Treasury_Prod == 'Yes'){
		
		if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
			|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking'){
				var value = "F_Facility_Amount_Proposed#Notional Amount$F_Exp_Ret_Proposed#Expected 1 year Return$MarketRiskProp#Marketing Risk Capital";
				validateBlank(value);
		}
		else if (stageId == 'CA_Raise Queries to RM' ||stageId == 'HOCC_Recommendation'||stageId == 'CCO_Recommendation' ||stageId == 'CSC_MRA_Prep' || stageId == 'CC'||stageId == 'CA_FRS_Prep'|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver'
			|| stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'  || stageId == 'CSU'|| stageId == 'Credit'  || stageId == 'CA_Review_Analysis'||stageId == 'CA_HOCC_Analysis'){
			
				
				var value = "F_Facility_Amount_Sensitized#Notional Amount$Exp_Ret_Sensitized#Expected 1 year Return$F_Mar_Ris_Sensitized#Marketing Risk Capital";
				validateBlank(value);
				
		}
		
		
		/*if((notionalProp == '' || notionalProp == null) && (ExpectedReturnProp == '' || ExpectedReturnProp == null) && (MarketRiskProp == '' || MarketRiskProp == null)){
			showAlertDialog("Please enter the Notional Amount, Expected 1 year Return and  Marketing Risk capital value ",true);
			return false;
		}
		else if((notionalProp != '' || notionalProp != null) && (ExpectedReturnProp == '' || ExpectedReturnProp == null) && (MarketRiskProp == '' || MarketRiskProp == null)){
			showAlertDialog("Please enter the Expected 1 year Return and  Marketing Risk capital value ",true);
			return false;
		}
		else if((notionalProp == '' || notionalProp == null) && (ExpectedReturnProp != '' || ExpectedReturnProp != null) && (MarketRiskProp == '' || MarketRiskProp == null)){
			showAlertDialog("Please enter the Notional Amount and Marketing Risk capital value ",true);
			return false;
		}
		else if((notionalProp == '' || notionalProp == null) && (ExpectedReturnProp != '' || ExpectedReturnProp != null) && (MarketRiskProp != '' || MarketRiskProp != null)){
			showAlertDialog("Please enter the Notional Amount value ",true);
			return false;
		}
		else if((notionalProp == '' || notionalProp == null) && (ExpectedReturnProp == '' || ExpectedReturnProp == null) && (MarketRiskProp != '' || MarketRiskProp != null)){
			showAlertDialog("Please enter the Notional Amount and  Expected 1 year Return value ",true);
			return false;
		}
		else if(MarketRiskSensitized == '' || MarketRiskSensitized == null){
			showAlertDialog("Please enter the Marketing Risk capital value ",true);
			return false;
		}
		
		
	}*/



}


function customListViewValidationRaroc(controlId, flag) {//m26
	if(controlId == "table27" && flag == "A"){
		getFDValueCalculation();
		
	}
	else if(controlId == "COLLATERAL_FACILITY_MAPPING_GRID" && ( flag == "A" || flag=='M'))
	{
		console.log(" Inside controlId=COLLATERAL_FACILITY_MAPPING_GRID...");
		//start edit by mohit 26-09-2024
		//CFM_add_flag=false;
		console.log("--CFM_add_flag--"+CFM_add_flag);
		if (flag == "A"){
			console.log("Inside if controlId=COLLATERAL_FACILITY_MAPPING_GRID,A");
			console.log(" facility_row_tick "+facility_row_tick);
			
			if(facility_row_tick==false){
				//CFM_add_flag=true;
				console.log(" if facility_row_tick is false, set CFM_add_flag to true ,checking-"+CFM_add_flag);
			}else if (facility_row_tick==true){
				facility='';
				console.log(" facility_row_tick is true so facility is-"+facility);
			}
		}else{
		//end edit by mohit 26-09-2024
		console.log("Inside else controlId=COLLATERAL_FACILITY_MAPPING_GRID,m..."); 
		var ret= executeAddRowPostHook("COLLATERAL_FACILITY_MAPPING_GRID");
		var obj = JSON.parse(ret);
		var value = obj.data;
		var successvar = obj.success;
		var msgResponse = obj.message;
		if(msgResponse!='')
		{
			showAlertDialog(msgResponse,  true);
			return false;
			
		}
		saveWorkItem();
	}
		//console.log('return========'+msgResponse);
		
	}
	else if (controlId == "table30" && flag == 'M') {
		var guaranteeType = getValue('table30_GUARANTEE_TYPE');
		var guaranteeExternalRating = getValue('table30_EXTERNAL_RATING');
		var guaranteeInternalRating = getValue('table30_INTERNAL_RATING');
		if (guaranteeType == 'Gurantees') {
			if ((guaranteeExternalRating == '' || guaranteeExternalRating == null) && (guaranteeInternalRating == '' || guaranteeInternalRating == null)) {
				showAlertDialog("Please enter the Guarantee External & Internal Rating value ", true);
				return false;
			}
			else if ((guaranteeExternalRating != '' || guaranteeExternalRating != null) && (guaranteeInternalRating == '' || guaranteeInternalRating == null)) {
				showAlertDialog("Please enter the Guarantee Internal Rating value ", true);
				return false;
			}
			else if ((guaranteeExternalRating == '' || guaranteeExternalRating == null) && (guaranteeInternalRating != '' || guaranteeInternalRating != null)) {
				showAlertDialog("Please enter the Guarantee External Rating value ", true);
				return false;
			}

		} else {
			setValues({ "table30_EXTERNAL_RATING": "Unrated" }, true);
		}
	}
	return true;

}
//start changes by mohit 12-06-2024 for moratorium_period cannot be greater than tenor
function MoratoriumPeriodConditionalField() {
	console.log("inside MoratoriumPeriodConditionalField method");
	var moratoriumPeriodProp = getValue('F_Moratorium_Period_Proposed');
	var moratoriumPeriodSensitized = getValue('Rep_Revolving_Sensitized');
	var tenorProp = getValue('F_Tenor_Proposed');
	var tenorSensitized =  getValue('F_Tenor_Sensitized');
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking') {
			if (moratoriumPeriodProp > tenorProp) {
				console.log("inside MoratoriumPeriodConditionalField method PROPOSED condition");
				showAlertDialog("Moratorium Period cannot be greater than tenor.", true);
				return false;
			}
	}
	else if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep' || stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver'
		|| stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver' || stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign') {
		if (moratoriumPeriodSensitized > tenorSensitized ) {
			console.log("inside MoratoriumPeriodConditionalField method SENSITIZED condition");
			showAlertDialog("Moratorium Period cannot be greater than tenor.", true);
			return false;
		}
	}	
}
//end changes by mohit 12-06-2024 for moratorium_period cannot be greater than tenor

//start changes by mohit 12-06-2024 for repayment_frequency -->For Bullet, can be defaulted to Bullet
function EnableRepaymentFrequencyPropComboBox(){ // to enable and remove Bullet from combobox
	console.log("inside EnableRepaymentFrequencyComboBox method!!!!");
	enableControls("F_Repayment_Frequency_Proposed");//bp05//d
	removeItemFromCombo("F_Repayment_Frequency_Proposed",5);
	//setValue("F_Repayment_Type_Proposed",'');
}

function DisableRepaymentFrequencyPropComboBox(){ // to disable and add Bullet in combobox
	console.log(" inside DisableRepaymentFrequencyPropComboBox method!!!!");
	addItemInCombo("F_Repayment_Frequency_Proposed","Bullet","Bullet");//bp05//d
	setValue("F_Repayment_Frequency_Proposed","Bullet");
	disableControls("F_Repayment_Frequency_Proposed");
}

function EnableRepaymentFrequencySensitizedComboBox(){ // to enable and remove Bullet from combobox
	console.log("inside EnableRepaymentFrequencyComboBox method!!!!");
	enableControls("Repay_Freq_Sensitized");
	removeItemFromCombo("Repay_Freq_Sensitized",5);
	//setValue("Repay_Freq_Sensitized",'');
	return true; 
}

function DisableRepaymentFrequencySensitizedComboBox(){ //  to disable and add Bullet in combobox
	console.log("inside DisableRepaymentFrequencyComboBox method!!!!");
	addItemInCombo('Repay_Freq_Sensitized','Bullet','Bullet');
	setValue("Repay_Freq_Sensitized","Bullet");
	disableControls("Repay_Freq_Sensitized");
}
//end changes by mohit 12-06-2024 for repayment_frequency -->For Bullet, can be defaulted to Bullet


function FacilityTypeChange(){
	
}
function indexTenorChange(){
	executeServerEvent(INDEX_TENOR_CHANGE, "change", "", false);
}

function indexTenorChangeSens(){
	console.log("Inside indexTenorChangeSens");
	executeServerEvent(INDEX_TENOR_CHANGE_SENS, "change", "", false);
}

//start changes by shikha 11-07-2024 for FTP_OVERRIDE
function enableFtpOverPropCombo(){
	var ftpOverride = getValue('F_FTP_OVR_PROPOSED'); 
	if ( ftpOverride == "YES"){
		setStyle('F_FTP_PROP','disable', 'false');
		//shikha 23-07-24
		executeServerEvent(ROW_CLICK, "PopulateData",'', true);		
	}else if(ftpOverride == "" || ftpOverride == "null" || ftpOverride == undefined || ftpOverride == 'NO')
	{
		setValue('F_FTP_PROP', ''); 
		setStyle('F_FTP_PROP', 'disable', 'true');
	}	
}

function enableFtpOverSentCombo(){
	var ftpOverSent = getValue('F_FTP_OVR_SENSITIZED'); 
	if ( ftpOverSent == "YES"){
		setStyle('F_FTP_SENSITIZED', 'disable', 'false');
		executeServerEvent(ROW_CLICK, "PopulateData",'', true);
	}else if(ftpOverSent == "" || ftpOverSent == "null" || ftpOverSent == undefined || ftpOverSent == 'NO')
	{
		setValue('F_FTP_SENSITIZED', ''); 
		setStyle('F_FTP_SENSITIZED', 'disable', 'true');
	}
		
}
//end changes by shikha 11-07-2024 for FTP_OVERRIDE

//start changes by shikha 15-07-2024 for Collateral_Faci_Map_Onload
function Collateral_Faci_Map_Onload(facility_type,facility_name,facility){//m26
	console.log("inside Collateral_Faci_Map_Onload!!!!="+facility_type+facility_name+facility);
	CFMHideLabels();
	disableCollateralProposedCFM();
	disableCollateralSensitizedCFM();
	document.getElementById('CFM_COLLATERAL_NUMBER_PROP').style.fontsize='5px';
	document.getElementById('CFM_COLLATERAL_NUMBER_SENS').style.fontsize='5px';
	document.getElementById('CFM_COLLATERAL_NUMBER_APPROVED').style.fontsize='5px';
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking') {  //BU
	    //collateralApproved
		hideControls("CFM_APP_LABEL");
		hideControls("CFM_COLLATERAL_TYPE_APPROVED");
		hideControls("CFM_COLLATERAL_NAME_APPROVED");
		hideControls("CFM_COLLATERAL_NUMBER_APPROVED");
		hideControls("CFM_COLLATERAL_CURRENCY_APPROVED");
		hideControls("CFM_COLLATERAL_AMOUNT_APPROVED");
		hideControls("CFM_COLLATERAL_LIEN_AMOUNT_APPROVED");
		hideControls("CFM_COLLATERAL_LIEN_INTEREST_APPROVED");
		hideControls("CFM_COLLATERAL_LIEN_TENOR_APPROVED");
		hideControls("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_APPROVED");
		hideControls("CFM_COLLATERAL_TOTAL_AMOUNT_APPROVED");
		hideControls("CFM_COLLATERAL_AMOUNT_UTILIZED_APPROVED");
		//collateralSenstized
		hideControls("CFM_SENS_LABEL");
		hideControls("CFM_COLLATERAL_TYPE_SENS");
		hideControls("CFM_COLLATERAL_NAME_SENS");
		hideControls("CFM_COLLATERAL_NUMBER_SENS");
		hideControls("CFM_COLLATERAL_CURRENCY_SENS");
		hideControls("CFM_COLLATERAL_AMOUNT_SENS_label");
		hideControls("CFM_COLLATERAL_LIEN_AMOUNT_SENS");
		hideControls("CFM_COLLATERAL_LIEN_INTEREST_SENS");
		hideControls("CFM_COLLATERAL_LIEN_TENOR_SENS");
		hideControls("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_SENS");
		hideControls("CFM_COLLATERAL_TOTAL_AMOUNT_SENS");
		hideControls("CFM_COLLATERAL_AMOUNT_UTILIZED_SENS");
		setValues({ "CFM_FACILITY_TYPE": getValue('F_FACILITY_TYPE') }, true);
		setValues({ "CFM_FACILITY_NAME": getValue('F_FACILITY_NAME') }, true);
		if(facility){//existing row is clicked
			console.log(" inside global facility_type="+facility_type);
			console.log(" inside global facility_name="+facility_name);
			console.log(" inside global facility="+facility);
			setValues({ "CFM_FACILITY_TYPE": facility_type }, true);
			setValues({ "CFM_FACILITY_NAME": facility_name }, true);
			setValues({ "CFM_COMMITMENT_NO_PROP": facility }, true);
			facility='';
		}else{
		if (getValue('F_COMMITMENT_NO')){
			console.log("inside F_COMMITMENT_NO is not null....");
			setValues({ "CFM_COMMITMENT_NO_PROP": getValue('F_COMMITMENT_NO') }, true);
		}else {
			console.log("inside F_COMMITMENT_NO is  null....");
			setValues({ "CFM_COMMITMENT_NO_PROP": getValue('F_FACILITY_ID') }, true);
		}
		}
		
	}else if ( stageId == 'CA_Raise Queries to RM' ||stageId == 'HOCC_Recommendation'||stageId == 'CCO_Recommendation' ||stageId == 'CSC_MRA_Prep' || stageId == 'CC'||stageId == 'CA_FRS_Prep'|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver'
		|| stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'  || stageId == 'CSU'|| stageId == 'Credit'  || stageId == 'CA_Review_Analysis'||stageId == 'HOCC_Assign') { 
		//collateralApproved
		hideControls("CFM_APP_LABEL");
		hideControls("CFM_COLLATERAL_TYPE_APPROVED");
		hideControls("CFM_COLLATERAL_NAME_APPROVED");
		hideControls("CFM_COLLATERAL_NUMBER_APPROVED");
		hideControls("CFM_COLLATERAL_CURRENCY_APPROVED");
		hideControls("CFM_COLLATERAL_AMOUNT_APPROVED");
		hideControls("CFM_COLLATERAL_LIEN_AMOUNT_APPROVED");
		hideControls("CFM_COLLATERAL_LIEN_INTEREST_APPROVED");
		hideControls("CFM_COLLATERAL_LIEN_TENOR_APPROVED");
		hideControls("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_APPROVED");
		hideControls("CFM_COLLATERAL_TOTAL_AMOUNT_APPROVED");
		hideControls("CFM_COLLATERAL_AMOUNT_UTILIZED_APPROVED");
		setValues({ "CFM_FACILITY_TYPE": getValue('F_FACILITY_TYPE') }, true);
		setValues({ "CFM_FACILITY_NAME": getValue('F_FACILITY_NAME') }, true);
		if(facility){//existing row is clicked
			console.log(" inside global facility_type="+facility_type);
			console.log(" inside global facility_name="+facility_name);
			console.log(" inside global facility="+facility);
			setValues({ "CFM_FACILITY_TYPE": facility_type }, true);
			setValues({ "CFM_FACILITY_NAME": facility_name }, true);
			setValues({ "CFM_COMMITMENT_NO_PROP": facility }, true);
			facility='';
		}else{
		if (getValue('F_COMMITMENT_NO')){
			console.log("inside F_COMMITMENT_NO is not null sens ....");
			setValues({ "CFM_COMMITMENT_NO_PROP": getValue('F_COMMITMENT_NO') }, true);
		}else {
			console.log("inside F_COMMITMENT_NO is  null sens ....");
			setValues({ "CFM_COMMITMENT_NO_PROP": getValue('F_FACILITY_ID') }, true);
		}
		}
	}
	executeServerEvent("COLLATERAL_FACILITY_MAPPING_GRID","CFM_OnLoad","",false);
	Collateral_Faci_Map_ProptoSens();
	Collateral_Faci_Map_SensToApprove();
}

function CFMHideLabels(){
	var labelIds = ['CFM_COLLATERAL_TYPE_PROP_label','CFM_COLLATERAL_NAME_PROP_label','CFM_COLLATERAL_AMOUNT_PROP_label','CFM_COLLATERAL_CURRENCY_PROP_label','CFM_COLLATERAL_NUMBER_PROP_label','CFM_COLLATERAL_LIEN_AMOUNT_PROP_label','CFM_COLLATERAL_LIEN_INTEREST_PROP_label','CFM_COLLATERAL_LIEN_TENOR_PROP_label', 
		'CFM_COLLATERAL_ALLOCATION_PERCENTAGE_PROP_label','CFM_COLLATERAL_TOTAL_AMOUNT_PROP_label','CFM_COLLATERAL_AMOUNT_UTILIZED_PROP_label','CFM_COLLATERAL_TYPE_SENS_label','CFM_COLLATERAL_NAME_SENS_label','CFM_COLLATERAL_AMOUNT_SENS_label','CFM_COLLATERAL_CURRENCY_SENS_label','CFM_COLLATERAL_NUMBER_SENS_label',
		'CFM_COLLATERAL_LIEN_AMOUNT_SENS_label','CFM_COLLATERAL_LIEN_INTEREST_SENS_label','CFM_COLLATERAL_LIEN_TENOR_SENS_label','CFM_COLLATERAL_ALLOCATION_PERCENTAGE_SENS_label','CFM_COLLATERAL_TOTAL_AMOUNT_SENS_label','CFM_COLLATERAL_AMOUNT_UTILIZED_SENS_label','CFM_COLLATERAL_TYPE_REALIZED_label','CFM_COLLATERAL_NAME_REALIZED_label',
		'CFM_COLLATERAL_AMOUNT_REALIZED_label','CFM_COLLATERAL_CURRENCY_REALIZED_label','CFM_COLLATERAL_NUMBER_REALIZED_label','CFM_COLLATERAL_LIEN_AMOUNT_REALIZED_label','CFM_COLLATERAL_LIEN_INTEREST_REALIZED_label','CFM_COLLATERAL_LIEN_TENOR_REALIZED_label','table33_COLLATERAL_ALLOCATION_PERCENTAGE_REALIZED_label',
		'CFM_COLLATERAL_TOTAL_AMOUNT_REALIZED_label','CFM_COLLATERAL_AMOUNT_UTILIZED_REALIZED_label','CFM_COLLATERAL_TYPE_PRE_APPROVED_label','CFM_COLLATERAL_NAME_PRE_APPROVED_label','CFM_COLLATERAL_AMOUNT_PRE_APPROVED_label','CFM_COLLATERAL_CURRENCY_PRE_APPROVED_label','CFM_COLLATERAL_NUMBER_PRE_APPROVED_label','CFM_COLLATERAL_LIEN_AMOUNT_PRE_APPROVED_label',
		'CFM_COLLATERAL_LIEN_INTEREST_PRE_APPROVED_label','CFM_COLLATERAL_LIEN_TENOR_PRE_APPROVED_label','CFM_COLLATERAL_ALLOCATION_PERCENTAGE_PRE_APPROVED_label','CFM_COLLATERAL_TOTAL_AMOUNT_PRE_APPROVED_label','CFM_COLLATERAL_AMOUNT_UTILIZED_PRE_APPROVED_label','CFM_COLLATERAL_TYPE_APPROVED_label','CFM_COLLATERAL_NAME_APPROVED_label', 
		'CFM_COLLATERAL_AMOUNT_APPROVED_label','CFM_COLLATERAL_CURRENCY_APPROVED_label','CFM_COLLATERAL_NUMBER_APPROVED_label','CFM_COLLATERAL_LIEN_AMOUNT_APPROVED_label','CFM_COLLATERAL_LIEN_INTEREST_APPROVED_label','CFM_COLLATERAL_LIEN_TENOR_APPROVED_label','CFM_COLLATERAL_ALLOCATION_PERCENTAGE_APPROVED_label','CFM_COLLATERAL_TOTAL_AMOUNT_APPROVED_label',
		'CFM_COLLATERAL_AMOUNT_UTILIZED_APPROVED_label'];
	
	for (var i = 0; i < labelIds.length; i++) {
        var label = document.getElementById(labelIds[i]);
        if (label) {
            label.style.display = 'none';
        }
    }
}

//end changes by shikha 15-07-2024 for Collateral_Faci_Map_Onload

//start changes by mohit 16-07-2024 for adding row in collateral facility mapping grid
function executeAddRowPostHook(tableId){
	var ret="";
	console.log("In executeAddRowPostHook function tableId=" + tableId);
	switch (tableId) {
		case "COLLATERAL_FACILITY_MAPPING_GRID":
		/*if (eventType == 'addrow_COLLATERAL_FACILITY_MAPPING_GRID' || eventType == 'addrowandnext_COLLATERAL_FACILITY_MAPPING_GRID' ){ 
		console.log("In if condition eventType  = " + eventType);
		executeServerEvent("tableId", "eventType","", false);
		}*/
		console.log("In switch case function COLLATERAL_FACILITY_MAPPING_GRID...");
		ret=executeServerEvent("COLLATERAL_FACILITY_MAPPING_GRID", "CFM_AddRow","", true);
		saveWorkItem();
		return ret;
		break;
		default:
		console.log('In executeAddRowPostHook default case');
		break;
	}
}
//end changes by mohit 16-07-2024 for adding row in collateral facility mapping grid

// start changes by reshank 18-07-2024 for enable the lein field

function enableCollateralProposed(){
	console.log('In enableCollateralProposed');
	var collateralProposed = getValue('CFM_COLLATERAL_TYPE_PROP'); 
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	console.log("value in stageId::: "+stageId);
	if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking'){
		
		if(collateralProposed.toLowerCase() == 'lien'){
				//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PRE_APPROVED', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_REALIZED', 'disable', 'false');
				setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PROP', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_SENS', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_APPROVED', 'disable', 'false');
				
				//setStyle('CFM_COLLATERAL_LIEN_INTEREST_PRE_APPROVED', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_INTEREST_REALIZED', 'disable', 'false');
				setStyle('CFM_COLLATERAL_LIEN_INTEREST_PROP', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_INTEREST_SENS', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_INTEREST_APPROVED', 'disable', 'false');
				
				//setStyle('CFM_COLLATERAL_LIEN_TENOR_PRE_APPROVED', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_TENOR_REALIZED', 'disable', 'false');
				setStyle('CFM_COLLATERAL_LIEN_TENOR_PROP', 'disable', 'false');
				//setStyle('CFM_COLLATERAL_LIEN_TENOR_SENS', 'disable', 'false');
			//	setStyle('CFM_COLLATERAL_LIEN_TENOR_APPROVED', 'disable', 'false');
				
			} else {
				disableCollateralProposedCFM();//by mohit 19-07-2024 to make fields disable on load.
			}
	} 
//	else {
//		disableCollateralProposedCFM();//by mohit 19-07-2024 to make fields disable on load.	 
//	}	
}
//end changes by reshank on 18-07-2024
function disableCollateralProposedCFM(){
	console.log('In disableCollateralProposed');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PRE_APPROVED', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_REALIZED', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PROP', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_SENS', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_APPROVED', 'disable', 'true');
	 
	 
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_PRE_APPROVED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_REALIZED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_PROP', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_SENS', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_APPROVED', 'disable', 'true');
	
	
	setStyle('CFM_COLLATERAL_LIEN_TENOR_PRE_APPROVED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_REALIZED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_PROP', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_SENS', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_APPROVED', 'disable', 'true');
	
}

// start changes by reshank 30-08-2024 for enable the lein field for Sens

function enableCollateralSensitized(){
	console.log('In enableCollateralSensitized');
	var collateralSensitized = getValue('CFM_COLLATERAL_TYPE_SENS'); 
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	console.log("value in stageId::: "+stageId);
	if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep'
			|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver' || stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'
			|| stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign'){
		
		if(collateralSensitized.toLowerCase() == 'lien'){
			
			//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PRE_APPROVED', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_REALIZED', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PROP', 'disable', 'false');
			setStyle('CFM_COLLATERAL_LIEN_AMOUNT_SENS', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_AMOUNT_APPROVED', 'disable', 'false');
			
			//setStyle('CFM_COLLATERAL_LIEN_INTEREST_PRE_APPROVED', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_INTEREST_REALIZED', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_INTEREST_PROP', 'disable', 'false');
			setStyle('CFM_COLLATERAL_LIEN_INTEREST_SENS', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_INTEREST_APPROVED', 'disable', 'false');
			
			//setStyle('CFM_COLLATERAL_LIEN_TENOR_PRE_APPROVED', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_TENOR_REALIZED', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_TENOR_PROP', 'disable', 'false');
			setStyle('CFM_COLLATERAL_LIEN_TENOR_SENS', 'disable', 'false');
			//setStyle('CFM_COLLATERAL_LIEN_TENOR_APPROVED', 'disable', 'false');	
			
			} else {
				disableCollateralSensitizedCFM();
			}
	} 
//	else {
//		disableCollateralProposedCFM();//by mohit 19-07-2024 to make fields disable on load.	 
//	}	
}
function disableCollateralSensitizedCFM(){
	console.log('In disableCollateralProposed');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PRE_APPROVED', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_REALIZED', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_PROP', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_SENS', 'disable', 'true');
	 setStyle('CFM_COLLATERAL_LIEN_AMOUNT_APPROVED', 'disable', 'true');
	 
	 
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_PRE_APPROVED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_REALIZED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_PROP', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_SENS', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_INTEREST_APPROVED', 'disable', 'true');
	
	
	setStyle('CFM_COLLATERAL_LIEN_TENOR_PRE_APPROVED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_REALIZED', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_PROP', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_SENS', 'disable', 'true');
	setStyle('CFM_COLLATERAL_LIEN_TENOR_APPROVED', 'disable', 'true');
	
}

//Start change by change 22-07-24
function enableFDAndRating() {
    console.log('In enableFDAndRating');
    var collateralType = getValue('Customer_Collateral_Type');
	if ( collateralType == 'FD' || collateralType == 'Fd' ){
		setStyle('Customer_FD_Rate', 'disable', 'false');
		setStyle('Customer_FD_Tenor', 'disable', 'false');
	}	
	else if (collateralType == 'Other Guarantees' || collateralType == 'Sovereign Guarantee' || collateralType =='Financial Guarantee'){
	    setStyle('Customer_External_Rating', 'disable', 'false');
		setStyle('Customer_Internal_Rating', 'disable', 'false');
	}	
	else {
	setStyle('Customer_FD_Rate', 'disable', 'true');
	setStyle('Customer_FD_Tenor', 'disable', 'true');
	setStyle('Customer_External_Rating', 'disable', 'true');
	setStyle('Customer_Internal_Rating', 'disable', 'true');
	}	
}
//end change by reshank
// Start change by reshank on 3-09-24
function Collateral_Faci_Map_ProptoSens(){
	console.log("inside Collateral_Faci_Map_ProptoSens ::: ")
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep'
			|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver' || stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'
			|| stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign'){
		console.log("stage Id:"+ stageId)
		
		var collateralTypeSensitized = getValue('CFM_COLLATERAL_TYPE_SENS');
		console.log("collateralTypeSensitized ::" +collateralTypeSensitized);
		var collateralNameSensitized = getValue('CFM_COLLATERAL_NAME_SENS');
		console.log("collateralNameSensitized ::" +collateralNameSensitized);
		
		if (collateralTypeSensitized.length<=0 || collateralTypeSensitized === null || collateralTypeSensitized === "" || collateralNameSensitized.length<=0 || collateralNameSensitized === null || collateralNameSensitized === ""){
			var collateralTypeProp = getValue('CFM_COLLATERAL_TYPE_PROP');
			var collateralNameProp = getValue('CFM_COLLATERAL_NAME_PROP');
			var collateralNumberProp = getValue('CFM_COLLATERAL_NUMBER_PROP');
			var collateralCurrencyProp = getValue('CFM_COLLATERAL_CURRENCY_PROP');
			var collateralAmountProp = getValue('CFM_COLLATERAL_AMOUNT_PROP');
			var collateralLienAmount = getValue('CFM_COLLATERAL_LIEN_AMOUNT_PROP');
			var collateralLienInterest = getValue('CFM_COLLATERAL_LIEN_INTEREST_PROP');
			var collateralLienTenor = getValue('CFM_COLLATERAL_LIEN_TENOR_PROP');
			var collateralAllocationPercent = getValue('CFM_COLLATERAL_ALLOCATION_PERCENTAGE_PROP');
			var collateralTotAmount = getValue('CFM_COLLATERAL_TOTAL_AMOUNT_PROP');
			var collateralAmountUtilized = getValue('CFM_COLLATERAL_AMOUNT_UTILIZED_PROP');
			
			addItemInCombo("CFM_COLLATERAL_TYPE_SENS",collateralTypeProp);
			//setValue("CFM_COLLATERAL_TYPE_SENS",collateralTypeProp);
			setValues({"CFM_COLLATERAL_TYPE_SENS": collateralTypeProp}, true);
		    console.log("CFM_COLLATERAL_TYPE_SENS just checking ::"+CFM_COLLATERAL_TYPE_SENS) 
		    
		    addItemInCombo("CFM_COLLATERAL_NAME_SENS",collateralNameProp);
			//setValue("CFM_COLLATERAL_NAME_SENS",collateralNameProp);
		    setValues({"CFM_COLLATERAL_NAME_SENS": collateralNameProp}, true);
		    addItemInCombo("CFM_COLLATERAL_NUMBER_SENS",collateralNumberProp);
			//setValue("CFM_COLLATERAL_NUMBER_SENS",collateralNumberProp);
		    setValues({"CFM_COLLATERAL_NUMBER_SENS": collateralNumberProp}, true);
		    addItemInCombo("CFM_COLLATERAL_CURRENCY_SENS",collateralCurrencyProp);
			//setValue("CFM_COLLATERAL_CURRENCY_SENS",collateralCurrencyProp);
			setValues({"CFM_COLLATERAL_CURRENCY_SENS": collateralCurrencyProp}, true);
			addItemInCombo("CFM_COLLATERAL_AMOUNT_SENS",collateralAmountProp);
			//setValue("CFM_COLLATERAL_AMOUNT_SENS",collateralAmountProp);
			setValues({"CFM_COLLATERAL_AMOUNT_SENS": collateralAmountProp}, true);
			//setValue("CFM_COLLATERAL_LIEN_AMOUNT_SENS",collateralLienAmount);
			setValues({"CFM_COLLATERAL_LIEN_AMOUNT_SENS": collateralLienAmount}, true);
			//setValue("CFM_COLLATERAL_LIEN_INTEREST_SENS",collateralLienInterest);
			setValues({"CFM_COLLATERAL_LIEN_INTEREST_SENS": collateralLienInterest}, true);
			//setValue("CFM_COLLATERAL_LIEN_TENOR_SENS",collateralLienTenor);
			setValues({"CFM_COLLATERAL_LIEN_TENOR_SENS": collateralLienTenor}, true);
			//setValue("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_SENS",collateralAllocationPercent)
			setValues({"CFM_COLLATERAL_ALLOCATION_PERCENTAGE_SENS": collateralAllocationPercent}, true);
			//setValue("CFM_COLLATERAL_TOTAL_AMOUNT_SENS",collateralTotAmount);
			setValues({"CFM_COLLATERAL_TOTAL_AMOUNT_SENS": collateralTotAmount}, true);
			//setValue("CFM_COLLATERAL_AMOUNT_UTILIZED_SENS",collateralAmountUtilized)
			setValues({"CFM_COLLATERAL_AMOUNT_UTILIZED_SENS": collateralAmountUtilized}, true);
		}
		
	}
}

//Start change by shikha 04-09-2024

function Collateral_Faci_Map_SensToApprove(){
	console.log("inside Collateral_Faci_Map_ProptoSens ::: ")
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	if (stageId == 'CAD_Doc_Assign' || stageId == 'Legal_Maker' || stageId == 'CAD_Doc_Verify' || stageId == 'CAD_CCU_Limit_Load' || stageId == 'CAD_CCU_Limit_Load_Review' || stageId == 'Exit'
			|| stageId == 'CEO_Proposal_Review' || stageId == 'CAD_Doc_Print' || stageId == 'CAD_Doc_Prep' || stageId == 'CAD_Doc_Review' || stageId == 'CAD_Doc_Approve' || stageId == 'Legal_Checker'
			|| stageId == 'TFS' || stageId == 'CAD_Def_waiver' || stageId == 'Query_Resolution' || stageId == 'Docs_Upload_Scan' || stageId == 'Hold' || stageId == 'Query' || stageId == 'Discard'
			|| stageId == 'Board_Discard' || stageId == 'Email_Submit' || stageId == 'Email_SendBack' || stageId == 'System stage' || stageId == 'Discard_CC' || stageId == 'CC_Discard' || stageId == 'Query_Resolution_Hold' 
			|| stageId == 'Recovery'){
		    console.log("stage Id....:"+ stageId)
		    //Approve
			var collateralTypeAppr = getValue('CFM_COLLATERAL_TYPE_SENS');
		    console.log("collateralTypeAppr...:"+ collateralTypeAppr)
			var collateralNameAppr = getValue('CFM_COLLATERAL_NAME_SENS');
			var collateralNumberAppr = getValue('CFM_COLLATERAL_NUMBER_SENS');
			var collateralCurrencyAppr = getValue('CFM_COLLATERAL_CURRENCY_SENS');
			var collateralAmountAppr = getValue('CFM_COLLATERAL_AMOUNT_SENS');
			var collateralLienAmountAppr = getValue('CFM_COLLATERAL_LIEN_AMOUNT_SENS');
			var collateralLienInterestAppr = getValue('CFM_COLLATERAL_LIEN_INTEREST_SENS');
			var collateralLienTenorAppr = getValue('CFM_COLLATERAL_LIEN_TENOR_SENS');
			var collateralAllocationPercentAppr = getValue('CFM_COLLATERAL_ALLOCATION_PERCENTAGE_SENS');
			var collateralTotAmountAppr = getValue('CFM_COLLATERAL_TOTAL_AMOUNT_SENS');
			var collateralAmountUtilizedAppr = getValue('CFM_COLLATERAL_AMOUNT_UTILIZED_SENS');
			

			setValue("CFM_COLLATERAL_TYPE_APPROVED",collateralTypeAppr);
			console.log("CFM_COLLATERAL_TYPE_APPROVED..:"+ CFM_COLLATERAL_TYPE_APPROVED)
			setValue("CFM_COLLATERAL_NAME_APPROVED",collateralNameAppr);
			setValue("CFM_COLLATERAL_NUMBER_APPROVED",collateralNumberAppr);
			setValue("CFM_COLLATERAL_CURRENCY_APPROVED",collateralCurrencyAppr);
			setValue("CFM_COLLATERAL_AMOUNT_APPROVED",collateralAmountAppr);
			setValue("CFM_COLLATERAL_LIEN_AMOUNT_APPROVED",collateralLienAmountAppr);
			setValue("CFM_COLLATERAL_LIEN_INTEREST_APPROVED",collateralLienInterestAppr);
			setValue("CFM_COLLATERAL_LIEN_TENOR_APPROVED",collateralLienTenorAppr);
			setValue("CFM_COLLATERAL_ALLOCATION_PERCENTAGE_APPROVED",collateralAllocationPercentAppr)
			setValue("CFM_COLLATERAL_TOTAL_AMOUNT_APPROVED",collateralTotAmountAppr);
			setValue("CFM_COLLATERAL_AMOUNT_UTILIZED_APPROVED",collateralAmountUtilizedAppr)
		
	}
}

//end change by shikha 04-09-2024