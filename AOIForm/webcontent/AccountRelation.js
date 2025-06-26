var submitFlag = false;
function onAcctRelFormLoad() {
	console.log('***** inside onFormLoad *****');
	var workstepName = getWorkItemData('activityName');
	console.log('Workstep: ' + workstepName);
	var wi_name = getWorkItemData('processInstanceId');
	console.log('WorkItem Name: ' + wi_name);
	var controlNames = [CURR_WS_DETAIL,TXT_CURRWS,SELECTED_ROW_INDEX,FIRST_SELECTED_ROW_INDEX,
	                    BACK_ROUTE_FLAG,HD_NAME,HD_FCR_SEARCH];
	var controlValues = [workstepName,workstepName,'0','0','false','','Customer Details'];
	if(getValue(NO_OF_CUST_SEARCHED) == '') {
		setFieldValue(NO_OF_CUST_SEARCHED,'0');
	}
	setMultipleFieldValues(controlNames,controlValues);
	var accOwnType = getValue(ACC_OWN_TYPE);
	var requestType = getValue(REQUEST_TYPE);
	//disableControls(AR_DIS_FIELDS);
	if(accOwnType == 'Single'){
		var controlNames = [OPERATING_INST];
		var controlValues = ['SINGLE'];
		setMultipleFieldValues(controlNames,controlValues);
		//setFieldValue([OPERATING_INST],['SINGLE']);
	} else if(accOwnType == 'Minor'){
		var controlNames = [OPERATING_INST];
		var controlValues = ['TO BE OPERATED BY GUARDIAN'];
		setMultipleFieldValues(controlNames,controlValues);
		//setFieldValue([OPERATING_INST],['TO BE OPERATED BY GUARDIAN']);
	} else if(accOwnType == 'Joint'){
		var controlNames = [OPERATING_INST];
		var controlValues = ['TO BE OPERATED BY ANY ONE'];
		setMultipleFieldValues([OPERATING_INST],['TO BE OPERATED BY ANY ONE']);
		//setFieldValue([OPERATING_INST],['TO BE OPERATED BY ANY ONE']);
	}
	if(getGridRowCount(ACC_RELATION)>0){
		if((getValue(ACC_OWN_TYPE) == 'Minor') || (getValue(ACC_OWN_TYPE) == 'Single')) {
			setStyle('table33_acc_relation','disable','true');
		} else if(getValue(ACC_OWN_TYPE) == 'Joint'){
			setStyle('table33_acc_relation','disable','false');
			setStyle('table33_cust_relation','disable','false');
		}
	}
	lockAOSection(AR_LOCK_FRAMES);
	var acctRelGridCount = getGridRowCount(ACC_RELATION);
	console.log('acctRelGridCount:: '+acctRelGridCount);
	if(requestType == 'Category Change Only' || requestType == 'New Account with Category Change'){
		hideControls(AR_REQ_NACC_HIDE_BTNS);
		disableControls(AR_REQ_NACC_DIS_FIELDS);
	}
	//added by shivanshu for dcra
	if(requestType == ('Downgrade') || requestType == ('Family Banking')){
		setStyle("KYC_PRE-ASSESSMENT","visible", "false");
	}
	if(requestType == ('Upgrade') || requestType == ('Category Change Only')) {
		setStyle("QVAR_Product_Value",'disable', 'true');
	}
	
	hideDowngradedata();
	//Added Shivanshu new upgrade
	if(requestType == ('Upgrade')){
		setStyle("SEC_ADD_NEW_CUSTOMER","visible", "false");
	}
	setKYCFlagPrivateclient(); // added by reyaz 28-09-2023
	visiblePreAssesmentField();
//	if(getValue('IS_UAE_PASS_AUTH_DONE') == 'Y'){//Ameena commented for disabling uae pass button
//		setFieldValue('IS_UAE_PASS_INFO_CLICKED','Y');
//	}
//	var manualControls = ['UAE_PASS_INFO_EMAIL','UAE_PASS_INFO_EIDA','UAE_PASS_INFO_MOBNO'];   // cop changes
//	disableControls(manualControls);
//	//setStyle('READ_EIDA','visible','false');
	//setStyle(READ_EIDA,PROPERTY_NAME.VISIBLE,'false');
//	executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);
}

function handleEventAcctRel(event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	var workstepName = getWorkItemData('activityName');
	if(workstepName == WORKSTEPS.ACCOUNT_RELATION ){
		if (event.type == EVENT_TYPE.CLICK) { 
			clickAcctRelEvent(event);
		} else if (event.type == EVENT_TYPE.CHANGE) {
			changeAcctRelEvent(event);
		} else if (event.type == EVENT_TYPE.LOSTFOCUS) {	
		} else if (event.type == EVENT_TYPE.GOTFOCUS) {
		} else if (event.type == EVENT_TYPE.LOAD) {
		}
	} 
}

function onRowClickAccRel(listviewId,rowIndex) { 
	console.log('listviewId='+listviewId+'rowIndex='+rowIndex);
	if(listviewId == ACC_RELATION){
		var iRows = getGridRowCount('acc_relation');
		sHiddenEida = getValue('table33_eida_no');
			if(getValue('IS_INITIATED_UAE_PASS') == 'Y'){
				setStyle('table33_dob', 'disable', 'true');
			} else {
				setStyle('table33_dob', 'disable', 'false');
			}
		setStyle('table33_nationality', 'disable', 'false');
		return true;
	}
	else if(listviewId == 'UAE_PASS_INFO_GRID'){
		 return false;
	}
	return true;
}


function clickAcctRelEvent(event) {
	console.log('Click Event of Intro Started for '+event.target.id);
	var buttonFetch  = true;
	if(event.target.id == BUTTON_SUBMIT){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == CRO_DEC){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id  == BTN_FETCH_EIDA_INFO){
		if(buttonFetch){
			document.getElementById("fade").style.display="block";
			CreateIndicator("Application");
			buttonFetch = false;
			/*var xmlobj;
			if(window.XMLHttpRequest) {
				xmlobj=new XMLHttpRequest();
			} else {
				xmlobj=new ActiveXObject("Microsoft.XMLHTTP");
			}
	
			xmlobj.onreadystatechange=function()  {
			if(xmlobj.readyState==4 && xmlobj.status==200) {
				
			}
			}*/
			
			const Http = new XMLHttpRequest();
			const url='http://127.0.0.1:9096/ReadEida';
			Http.open("POST", url);
			Http.send();
			Http.onreadystatechange = (e) => {
				//alert(Http.responseText);
				console.log(Http.responseText);
				console.log(Http.readyState);
				console.log(Http.status);
				if(Http.readyState==4 && Http.status==200) {
					//alert('inside');
					executeServerEvent(BTN_FETCH_EIDA_INFO, EVENT_TYPE.CLICK,Http.responseText, false);
				} /*else{
					var data = '{"residencyNumber":"2435768",'+				 				
								'"occupation":"Architect",'+							  
								'"workEmail":"efsgrdhtfg",'+									
								'"companyName":"J>L Leader",'+							
								'"title":"Mr.",'+								
								'"passportExpiryDate":"01/01/2020",'+		
								'"passportCountry":"INDIA",'+						
								'"expiryDate":"07/08/2021",'+					
								'"passportIssueDate":"02/01/2015",'+			
								'"motherFullName":"ABC",'+						
								'"issueDate":"09/08/2018",'+
								'"email":"WJFBGJRF",'+									
								'"passportNumber":"3456789",'+						
								'"sex":"F",'+									
								'"mobile":"3456789",'+								
								'"fullName":"Ojasvi Saanvi",'+			
								'"passportType":"",'+						
								'"emirate":"OTHERS",'+							
								'"phoneNumber":"345678",'+							
								'"nationality":"INDIA",'+							
								'"dob":"05/07/1955",'+							
								'"residencyExpiryDate":"07/08/2021",'+			
								'"cardNumber":"23456789",'+							
								'"maritalStatus":"",'+
								'"residencyType":"",'+ 
								'"eidaNumber":"234567",'+							
								'"flatNumber":"22",'+							
								'"city":"OTHERS"	}';
					RemoveIndicator("Application");
					document.getElementById("fade").style.display="none";
					showMessage(BTN_FETCH_EIDA_INFO, 'Unable to read EIDA Details', 'error');
					//executeServerEvent(BTN_FETCH_EIDA_INFO, EVENT_TYPE.CLICK,data, false);

				}*/
			}
			buttonFetch = true;
		}
			
	} else if(event.target.id  == READ_EIDA){
		executeServerEvent(READ_EIDA, EVENT_TYPE.CLICK, '', false);
	} else if(event.target.id == BTN_SEARCH_CUSTOMER){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == HD_FCR_SEARCH){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_SEARCH_CLEAR){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == BTN_ADD_EIDA_INFO){
		var selectedRows = getSelectedRowsIndexes(EIDA_DETAILS);
		var iSelectedRow;
		if(selectedRows!=null || selectedRows!='' || selectedRows!=undefined){
			iSelectedRow = selectedRows[0]; 
		}
		console.log('iSelectedRow:' + iSelectedRow);
		executeServerEvent(event.target.id, event.type, iSelectedRow, false);
	} else if(event.target.id == SEARCH_ADD_CUST_INFO){
		//searchAddCustInfo();
		var selectedRows =getSelectedRowsIndexes(SEARCH_DETAILS_LVW); 
		var iSelectedRow;
		if(selectedRows!=null || selectedRows!='' || selectedRows!=undefined){
			iSelectedRow = selectedRows[0]; 
		}
		console.log('iSelectedRow:' + iSelectedRow);
		executeServerEvent(event.target.id, event.type, iSelectedRow, false);
	} else if(event.target.id == BTN_ADD_MANUALLY){
		executeServerEvent(event.target.id, event.type, '', false);
	}
	else if(event.target.id == 'FETCH_UAE_PASS_INFO'){	// Changes uae Pass info
		console.log('uae pass info');
		if(validateAccRelridForUaePass()){
			saveWorkItem();
			//setStyle("CRO_DEC","disable", "false");
			executeServerEvent(event.target.id, event.type, '', false);
			refreshFrame("frame35",true);

		} else {
			setFieldValue("CRO_DEC","Reject");
			setStyle("CRO_DEC","disable", "true");
		}
		
	} 
}
function changeAcctRelEvent(event) {
	console.log('Change Event of Intro Started for '+event.target.id);
	if(event.target.id == SOURCING_CHANNEL){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == DATA_ENTRY_MODE){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == REQUEST_TYPE){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if (event.target.id == 'PRIVATE_CLIENT'){
		setKYCFlagPrivateclient();
	} else if (event.target.id == 'KYC_CUST_BLACKLIST1_0' || event.target.id == 'KYC_BARRIER_0' || event.target.id == 'KYC_ENTITY_SHELL_0'
	          || event.target.id == 'KYC_CUST_NORTH_KORES_0' || event.target.id == 'kYC_PATNER_UBO_0' ||
			    event.target.id == 'KYC_MANUFACTURING_0' || event.target.id == 'KYC_LEGAL_GAMING_0' || event.target.id == 'KYC_OFFICE_VELLI_0' ||
			    event.target.id == 'KYC_VIRTUAL_CURRENCY_0' || event.target.id == 'KYC_ENTITY_RE_0' || event.target.id == 'KYC_NOMINEE_0' ||			
			    event.target.id == 'KYC_EXCEPTIONAL_0' || event.target.id == 'KYC_CUST_BLACKLIST1_1' || event.target.id == 'KYC_BARRIER_1' || 
				event.target.id == 'KYC_ENTITY_SHELL_1' || event.target.id == 'KYC_CUST_NORTH_KORES_1' || event.target.id == 'kYC_PATNER_UBO_1' ||
			    event.target.id == 'KYC_MANUFACTURING_1' || event.target.id == 'KYC_LEGAL_GAMING_1' || event.target.id == 'KYC_OFFICE_VELLI_1' ||
			    event.target.id == 'KYC_VIRTUAL_CURRENCY_1' || event.target.id == 'KYC_ENTITY_RE_1' || event.target.id == 'KYC_NOMINEE_1' ||			
			    event.target.id == 'KYC_EXCEPTIONAL_1'){
		setPreAssessment();
	}
}

function postServerEventHandlerAcctRel(controlName, eventType, responseData) {
	var jsonData = handleAOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	if(!jsonData.success){ 
		var mssg = jsonData.message;
		if ('' != jsonData.message && null != jsonData.message && !(jsonData.message.indexOf('You are proceeding without adding guardian, are you sure ?') != -1)) {
			if((jsonData.message).indexOf('###') != -1){
				var arr = jsonData.message.split('###');
				showMessage(arr[0], arr[1], 'error');
			}
		}
	}
	switch (eventType) {
	case EVENT_TYPE.LOAD:
		if(getValue(SOURCING_CHANNEL) == ('Branch')) {
			setFocus(REQUEST_TYPE);
		} else {
			setFocus(SOURCING_CHANNEL);
		}
		var manualControls = [NEW_CUST_NAME];
		
		var hideManualControl = [NEW_CUST_ADD_MANUALLY];
		console.log('getValue(REQUEST_TYPE): '+getValue(REQUEST_TYPE));
		if(getValue(REQUEST_TYPE) == 'Category Change Only' 
			|| getValue(REQUEST_TYPE) == 'New Account With Category Change'){
			hideControls(hideManualControl);
			disableControls(manualControls);
		}
		if(getValue('IS_UAE_PASS_INFO_CLICKED') == 'Y') {
			setStyle('FETCH_UAE_PASS_INFO','disable', 'true');		
			}
		if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
			setStyle('FETCH_UAE_PASS_INFO','disable', 'false');
		}
		break;
	case EVENT_TYPE.CLICK:
		if('afterSaveNext' == controlName) {
			console.log('afterSaveNext sheet: '+getSheetIndex('tab1'));
			if(getSheetIndex('tab1') == 1) {
				submitFlag = true;
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
			executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, getSheetIndex('tab1'), false);
		} else if('DELETEROW' == controlName) {
			saveWorkItem();
			window.top.refreshWorkitem();
		}
			else if(TABCLICK == controlName && submitFlag && (jsonData.success || (!jsonData.success && 
				jsonData.message.indexOf('You are proceeding without adding guardian, are you sure ?') != -1))) {
			submitFlag = false;
			setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'true');
			executeServerEvent(BUTTON_SUBMIT, EVENT_TYPE.CLICK, '', false);
		} else if(controlName == SEARCH_ADD_CUST_INFO){
			saveWorkItem();
			if((getValue(ACC_OWN_TYPE) == 'Minor') || (getValue(ACC_OWN_TYPE) == 'Single')) {
				setStyle('table33_acc_relation','disable','true');
			}
	        var requestType = getValue(REQUEST_TYPE);  // Downgrade changes done by krishna
			if(requestType == ('Downgrade') && jsonData.success){
			completeWorkItem();
			}
		} else if(controlName == BTN_ADD_MANUALLY){
			saveWorkItem();
			if((getValue(ACC_OWN_TYPE) == 'Minor') || (getValue(ACC_OWN_TYPE) == 'Single')) {
				setStyle('table33_acc_relation','disable','true');
			}
		} else if(controlName == BUTTON_SUBMIT){
			var requestType = getValue(REQUEST_TYPE); 
			if(requestType != ('Downgrade')){
			if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
				showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
				return false;
		     	}
			} if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
				setStyle("SKIPUAEPASS_REASON","disable", "false");
				//showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');  commented by shahbaz
				return false;
			}  if(getValue(skipUAEPass) != true){
				setStyle("SKIPUAEPASS_REASON","disable", "true");
			}
			if(jsonData.success) {				
				saveWorkItem();
				completeWorkItem();				
			} else if(jsonData.message.indexOf('You are proceeding without adding guardian, are you sure ?') != -1){
				showConfirmDialog(CA0176, confirmDialogButtons, function(result) {
					if(result == true) {
						saveWorkItem();
						completeWorkItem();	
					} else if(result == false) {
						setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
						return;
					}
				});
			} else {
				setStyle(BUTTON_SUBMIT,PROPERTY_NAME.DISABLE,'false');
			}
		} else if(controlName == BTN_FETCH_EIDA_INFO){
			RemoveIndicator("Application");
			document.getElementById("fade").style.display="none";
		} else if(controlName == READ_EIDA){
			const Http = new XMLHttpRequest();
			const url='http://127.0.0.1:90/ReadEida';
			Http.open("POST", url);
			Http.send();
			Http.onreadystatechange = (e) => {
				//alert(Http.responseText);
				console.log(Http.responseText);
				if(this.readyState==4 && this.status==200)
					{
						executeServerEvent(BTN_FETCH_EIDA_INFO, EVENT_TYPE.CLICK,Http.responseText, false);
					}
			}

			//refNo = jsonData.data;
			//var jspURL = 'https://adcbeida.adcbapps.local/EIDAFCUBS/login.jsp?channelId=WMSBPM&channelRefNo='+refNo;
			//var jspURL = 'http://adcm7160:8080/EIDAFCUBS/login.jsp?channelId=WMSBPM&channelRefNo='+refNo;
			//var openedWindow = window.open(jspURL,"newWin","location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,left=100,top=20"); 
			//if(openedWindow.closed){
				//setStyle(BTN_FETCH_EIDA_INFO,PROPERTY_NAME.DISABLE,'false');
				//executeServerEvent(BTN_FETCH_EIDA_INFO, EVENT_TYPE.CLICK,refNo, false);
			//}
		}
		else if (controlName == FETCH_UAE_PASS_INFO)
		{
			saveWorkItem();
		}
		
		break;
	}
}

function onClickTabAcctRel(tabId,sheetindex,eventCall){
	console.log('sheetindex='+sheetindex+'eventCall='+eventCall);	
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex id: '+event.target.id+'event.target.innerHTML='+event.target.innerHTML);
	var controlID=sheetindex+','+event.target.innerHTML;
	var input=sheetindex;
	console.log('onClickTabAcctRel');
	if(tabId == 'tab1') {
		if(sheetindex == 1){
			refreshFrame("frame35",true);
			var requestType = getValue(REQUEST_TYPE); 
			if(requestType != ('Downgrade')){
			if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
				showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
				return false;
			}  if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
				setStyle("SKIPUAEPASS_REASON","disable", "false");
				showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');
				return false;
			}  if(getValue(skipUAEPass) != true){
				setStyle("SKIPUAEPASS_REASON","disable", "true");
			}
			else {
				executeServerEvent(ONCLICKTAB1, EVENT_TYPE.CLICK, input, false);
			}
		}
			
		}
	}
}

function saveAndNextPreHookAccRel(tabId){

	var input=getSheetIndex('tab1');
	console.log('input saveAndNextPreHookAccRel'+input);
	var response=executeServerEvent(TABCLICK, EVENT_TYPE.CLICK, input, true);
	console.log('save and next response:: '+response);
	setKYCFlag(); //added by shivanshu for dcra
	setPreAssessment();
	if(input == 0){
		refreshFrame("frame35",true);
		var requestType = getValue(REQUEST_TYPE); 
		var privateClient = getValue('PRIVATE_CLIENT'); //Added by reyaz 12092023
		if (requestType == ('New Account') || requestType == ('Category Change Only') 
			|| requestType == ('New Account With Category Change') || requestType == ('Family Banking')
		     || requestType == ('Upgrade')){
		 if(getValue('IS_UAE_PASS_INFO_CLICKED') != 'Y'){
			showMessage('IS_UAE_PASS_INFO_CLICKED', 'Click Fetch UAE Pass Button before proceeding', 'error');
			return false;
		 } else if(requestType != ('Family Banking') && (privateClient == '' || privateClient ==null ) 
			 && requestType != ('Upgrade') && requestType != ('Category Change Only')){
		     showMessage('PRIVATE_CLIENT', 'Please Select Value For Private Client', 'error');
			return false;
		 } else if(requestType != ('Family Banking') && privateClient !='Y' && requestType != ('Upgrade') && requestType != ('Category Change Only')){
				if (getValue('KYC_CUST_BLACKLIST') == null){
					showMessage('BTN_SUBMIT', 'Please Select Value for Question 1', 'error');
						return false;
					}else if (getValue('KYC_CUST_HAWALADRA_SERV') == null){
					showMessage('BTN_SUBMIT', 'Please Select Value for Question 2', 'error');
						return false;	
					}else if (getValue('KYC_CUST_NORTH_KORES_RESD') == null){
					showMessage('BTN_SUBMIT', 'Please Select Value for Question 3', 'error');
						return false;	
					}else if (getValue('KYC_ENTITY_RESIDENT_JURISD') == null){
					showMessage('BTN_SUBMIT', 'Please Select Value for Question 4', 'error');
						return false;
					}
				if(requestType != ('Upgrade') && requestType != ('Category Change Only')) {
					if (getValue('QVAR_Product_Value') == null){
						showMessage('BTN_SUBMIT', 'Please Select Atleast One Product', 'error');
						return false;
					}
				}
		} else if(requestType != ('Family Banking') && privateClient =='Y' && requestType != ('Upgrade') && requestType != ('Category Change Only')){
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
		else if(getValue(skipUAEPass) == true && getValue('SKIPUAEPASS_REASON') == ''){
			setStyle("SKIPUAEPASS_REASON","disable", "false");
			//showMessage('SKIPUAEPASS_REASON', 'Please select Reason For Skipping UAE Pass', 'error');  commented by shahbaz
			return false;
		} else if(getValue(skipUAEPass) != true){
			setStyle("SKIPUAEPASS_REASON","disable", "true");
			}
		} else if(getValueFromTableCell('acc_relation',0,2) != ''){
			var email =  getValueFromTableCell('acc_relation',0,6);
			var eida =  getValueFromTableCell('acc_relation',0,4);
			var mobNo =  getValueFromTableCell('acc_relation',0,3);
			if(email == ''){
				showMessage('acc_relation', 'Email Cannot be Blank in ACC INFO grid', 'error');
				return false;
		//	} else if(eida == ''){
			//	showMessage('acc_relation', 'Eida Cannot be Blank in ACC INFO grid', 'error');
				//return false;
			} else if(mobNo == ''){
				showMessage('acc_relation', 'Mobile No Cannot be Blank in ACC INFO grid', 'error');
				return false;
			}
		}
	} 
	if(response!='' && response!=undefined){
		var jsonData = handleAOResponse(response);
		if (!jsonData.success){
			if(jsonData.message.indexOf('You are proceeding without adding guardian, are you sure ?') != -1){
				executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, input, false);
				return true;
			} else { 
				return false;
			}
		}
	}
	if(validateGridDataForUAEPass()){
		executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, '', false);
		return true; 
	}
	
}

/*function tableOperation(tableId,operationType){
			console.log('tableId' +tableId);
			console.log('operationType' +operationType);
			if( operationType == DELETEROW){
				if(tableId == ACC_RELATION) {
					var selectedRows =getSelectedRowsIndexes(ACC_RELATION); 
					var iSelectedRow;
					if(selectedRows!=null || selectedRows!='' || selectedRows!=undefined){
						iSelectedRow = selectedRows[0]; 
					}
					console.log('iSelectedRow:' + iSelectedRow);
					executeServerEvent('DELETEROW', EVENT_TYPE.CLICK, iSelectedRow, false);
					return true;	
				}
			} else{
				console.log('delete row else');
				return true;	
			}
		} */


function selectRowHookAcctRel(tableId,selectedRowsArray,isAllRowsSelected) {
	console.log('tableId='+tableId+'selectedRowsArray='+selectedRowsArray);
	var listviewId = tableId;
	var iListViewSelectedRow = selectedRowsArray[0];
	console.log('iListViewSelectedRow = '+iListViewSelectedRow);
	if(iListViewSelectedRow >= 0){
		if(ACC_RELATION == listviewId){
			var acctRelCID =  getValueFromTableCell(SEARCH_DETAILS_LVW,iListViewSelectedRow,2);
			executeServerEvent(ACC_RELATION, EVENT_TYPE.CLICK, iListViewSelectedRow, false);
		}
	}
}
//added for eida
function openEidainForm()
{
	var workItemName = getWorkItemData('processInstanceId');
	var ret = Initialize();
	/*All below functions are in eida_webcomponents.js*/
	if(ret!="")
	{
		alert(ret);
		return "";
	}
	DisplayPublicData();
	DisplayPublicDataEx();
	var FormData = fetchAllData();
	return "<Val>" + FormData + "</Val>";
}

function hideDowngradedata(){
	var requestType = getValue(REQUEST_TYPE);  // Downgrade changes done by krishna
	if(requestType == ('Downgrade')){
		setStyle("SEC_ADD_NEW_CUSTOMER","visible", "false");
		setStyle("SEC_ACC_REL","visible", "false");
	//hideControls(SEC_ADD_NEW_CUSTOMER);
	//hideControls(SEC_ACC_REL);
  }
}

function setKYCFlag(){
	var requestType = getValue(REQUEST_TYPE);  // added by shivanshu for dcra
	var preKYCFlag = "";
	var privateClient = getValue('PRIVATE_CLIENT');
	if(privateClient != 'Y'){
	if(requestType == ('New Account') || requestType == ('Category Change Only') 
		|| requestType == ('New Account with Category Change') || requestType == ('Upgrade')){
			var q1 = getValue("KYC_CUST_BLACKLIST");
			var q2 = getValue("KYC_CUST_HAWALADRA_SERV");
			var q3 = getValue("KYC_CUST_NORTH_KORES_RESD");
			var q4 = getValue("KYC_ENTITY_RESIDENT_JURISD");
			if(q1 == 'No' && q2 == 'No' && q3 == 'No' && q4 == 'No'){
					setFieldValue("PRE_ASSESMENT_FLAG","Yes");
			}else {
					setFieldValue("PRE_ASSESMENT_FLAG","No");
				}
		 }
	}
			}


function setPreAssessment(){
		
	var f1 = getValue('KYC_CUST_BLACKLIST1');
	var f2 = getValue('KYC_BARRIER');
	var f3 = getValue('KYC_ENTITY_SHELL');
	var f4 = getValue('KYC_CUST_HAWALADRA');
	var f5 = getValue('KYC_CUST_NORTH_KORES');
	var f6 = getValue('kYC_PATNER_UBO');
	var f7 = getValue('KYC_MANUFACTURING');
	var f8 = getValue('KYC_LEGAL_GAMING');
	var f9 = getValue('KYC_OFFICE_VELLI');
	var f10 = getValue('KYC_VIRTUAL_CURRENCY');
	var f11 = getValue('KYC_ENTITY_RE');
	var f12 = getValue('KYC_NOMINEE');
	var f13 = getValue('KYC_EXCEPTIONAL');
	var preKYCFlag = getValue('PRE_ASSESMENT_FLAG');
	var privateClient = getValue('PRIVATE_CLIENT');
	if(privateClient == 'Y'){
		if(f1=='Yes'|| f2=='Yes'|| f3=='Yes' || f4=='Yes'|| f5=='Yes'|| f6=='Yes' || f7=='Yes'|| f8=='Yes'|| f11=='Yes'){
			setValue('KYC_EXCEPTIONAL','');	
			setStyle('KYC_EXCEPTIONAL','disable','true');       		
		}else if (f1=='No' && f2=='No'&& f3=='No' && f4=='No'&& f5=='No' && f6=='No' && f7=='No'&& f8=='No'&& f11=='No'){
			setValue('KYC_EXCEPTIONAL','');
			setStyle('KYC_EXCEPTIONAL','disable','true');
			if(f9== 'Yes' || f10=='Yes' || f12=='Yes') {
				setFieldValue('PRE_ASSESMENT_FLAG','Yes');		
				setStyle('KYC_EXCEPTIONAL','disable','false');
			}	
		}
  }
}