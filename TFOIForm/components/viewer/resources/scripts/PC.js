function onPCFormLoad(){
	var workstepName = getWorkItemData('activityName');
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	setStyle('Frame15','sectionstate','expanded');
	setStyle('FRM_LLI','disable','true');
	hideControls(FRM_LLI_FIELDS);
	hideSWIFTFrame();
	lockTFOSection('FRM_VERIFY_DETAIL,PT_PROTRADE,PT_Document_Detail,PT_CUSTOMER_INS,FRM_LLI,GTEE_FRAME,GRNT_FRM_NEW,PM_REF_FRAME,PM_Decision_Frame,IELC_MKR_INPUT_FRM,LC_FRAME,ILC_Controls,FRM_DUPE,BILL_FRAME,'
			+'IF_FRAME,IFS_Final_Dec_frame,PC_ELC_Controls,frame5,frame7,ELC_Controls,frame23,frame24,IF_FRAME1,DEAL_DETAILS_FRAME,frame20,'
			+'FRM_TR_DTLS,TREASURY_FRAME,PC_ILC_Controls,FRM_GRTEE_LC_COMMON,FRM_PARTIES_DETAILS,frm_contract_limits,EC_Controls,ILCB_Controls,Common_Fields,PC_ILCB_Controls,PC_ELCB_Controls,ELCB_Controls,PT_COMMON,PT_GTEE_FRAME,PT_FFT_FRAME,PT_ILC_FRAME,PT_PROTRADE');
	hideButtons();
	genericSetStyle(DISABLE_CHARGES,'disable','true');
	setStyle('frm_contract_limits','visible','true');
	setStyle('frm_contract_limits','disable','true');
	setStyle('PM_REF_FRAME','visible','false');
	setStyle('PC_ADDTNL_MAIL','visible','true');
	setStyle('btnDiscDetails','visible','false');
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	setTabStyle("tab1",8, "visible", "false");
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType  || 'PD' == reqType || 'PDD' == reqType)) {   //ATP - 207
			setTabStyle("tab1",8, "visible", "true");
			setTabStyle("tab1",8, "disable", "true");
		
		setStyle('UTC_REQUIRED_BRMS', PROPERTY_NAME.VISIBLE, 'true');  // Added by reyaz 5082022
		setStyle('UTC_CONVERTED_AMOUNT', PROPERTY_NAME.VISIBLE, 'true'); // Added by reyaz 5082022
		setStyle('UTC_REQUIRED', PROPERTY_NAME.VISIBLE, 'true');  // Added by reyaz 5082022
		setStyle('UTC_JSTIFICATION_REQUIRED', PROPERTY_NAME.VISIBLE, 'true'); // Added by reyaz 5082022
	}
	inputDetailShowHideSection();
	finalDecisionCheckListHandlingPC();
	hideshowFinalDesicionFields();
	hidePMPCSWIFTFrame();
	setFrmLabelName();
	disableTabsNFieldsAtPC();
	duplicateCheckPPMPC("DUP_CHK_CONFIRMATION",false);
	reqCatDupCheckFilter();
	disableFieldOnDemand("SOURCE_CHANNEL,RELATIONSHIP_TYPE,DELIVERY_BRANCH");
	finalDecisionCheckListHandlingPC();
	disableFieldOnDemand(BUTTON_SUBMIT);
	setStyle("COMP_IMB_CHK","visible","true");
	setStyle("COMP_IMB_CHK_2","visible","false");
	setStyle("COMP_IMB_CHK_3","visible","false");
	disableFieldOnDemand("COMP_IMB_CHK");
	setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
	hideControls(HIDE_PC_FIELDS);
	disableFieldOnDemand(LISTVIEW_PARTY);
	setFCUBSContractNumber(); 
	disablePTFields();
	//elcIlcFramePT();
	//grntFramePT();//Shipping_gtee_81
	//ecFramePT();
	enablePTFieldsAtPCPM();
	disableForILC_AM();
	enableCustInstSection();
	disableCustInstSection();
	disableFieldsGRNT_CC_ER_CL_EPC(); //added by Rakshita
	ptCommonFieldsHandling(); //US 159
	enableFieldELCB_AM_DISC(); //PT_283-284
	ELCBFields(); //PT 138 135
	setTabStyle("tab1",PM_TRSD_SHEET_ID, "disable", "true");
	formLoadIFPIFAIFS();	//CODE BY MOKSH
	pcHybridcombo();
	enableDisableFieldsPC_SCF_PD(); //ATP -207
        handleSwiftMtUI(); //ATP-458 REYAZ 14-07-2024
}

function disableTabsNFieldsAtPC(){
	genericSetStyle(DISABLE_PM_TXT,'disable','true');
	genericSetStyle(DISABLE_PC_TXT,'disable','true');
	genericSetStyle(DISABLE_PM_LOV,'disable','true');
	genericSetStyle(DISABLE_PM_IF_CHECKBOX,'disable','true');
	genericSetStyle(DISABLE_PC_PD_DETAILS,'disable','true');
	genericSetStyle(DISABLE_SWIFT_FIELDS,'disable','true');
	genericSetStyle(DISABLE_PC_CONTRACT_LIMIT,'disable','true');
	setStyle('btnAccDtl','disable','true');	
	genericSetStyle(DISABLE_CPD_STLMNT,'disable','true');
	genericSetStyle(DISABLE_PM_IF_TXT,'disable','true');
	genericSetStyle(DISABLE_PM_IF_LOV,'disable','true');
	genericSetStyle(DISABLE_TR_TAB_COMBOS,'disable','true');
	genericSetStyle(DISABLE_TR_TAB_FIELDS,'disable','true');
	genericSetStyle(DISABLE_LLI,'disable','true');
	genericSetStyle(COMPLIANCE_FIELDS,'disable','true'); 
	setStyle('btnCPDAdd','disable','true');
	setStyle('btnCPDDel','disable','true');
	setStyle('FETCH_RATE','visible','false');	
	genericSetStyle(HIDE_PC_TXT,'visible','false');//mansi
}

function hideSWIFTFrame(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	console.log("inside swift");
	if((requestType=="DBA_AM")||(requestType=="EC_AC")||(requestType=="EC_BS")||(requestType=="EC_AM")||(requestType=="ELC_LCA")||
			(requestType=="ELC_LCAA")||(requestType=="ELC_LCC")||(requestType=="ELCB_AC")||(requestType=="ELCB_AM")||
			(requestType=="ELCB_BS")||(requestType=="IC_AM")||requestType==("ILCB_AM")
			||(requestCategory=="SBLC" &&(requestType=="SBLC_NI"||requestType=="SBLC_AM"||requestType=="SBLC_CR"||requestType=="SBLC_CS"||
					requestType=="SBLC_ER"))	//RR
			||(requestCategory=="ELC" &&(requestType=="ELC_SLCA"||requestType=="ELC_SLCAA"||requestType=="ELC_SER"||
					requestType=="ELC_SCL"))	//RR
			||(requestCategory=="GRNT" &&(requestType=="NI"||requestType=="AM"||requestType=="CC"||requestType=="CL"||
					requestType=="ER"||requestType=="EPC"))){
		genericSetStyle(FRM_SWIFT,'visible','false');
	}
}



function handleDeDupFrame(){
	var isDedup = getValue("IS_DEDUP");
	if(isDedup=="N"){
		setStyle(DUP_CHK_CONFIRMATION,'disable','true');
		setStyle(FINAL_DEDUPE,'disable','true');
	}
}
function handlePCEvent(object, event){
	console.log('Event: ' + event.target.id + ', ' + event.type);
	var workstepName = getWorkItemData('activityName');
	if (event.type == EVENT_TYPE.CLICK) { 
		clickPCEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changePCEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusPMEvent(event);	
	}  
}

function changePCEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);
	if("PC_FCUBS_REF" == event.target.id){
		setValues({[TRANSACTION_REFERENCE]:getValue('PC_FCUBS_REF')}, true);
	} else if(event.target.id == TFO_BRANCH_CITY){
		setValues({[TFO_ASSIGNED_CENTER]: getValue(TFO_BRANCH_CITY)}, true);
	} else if(event.target.id == DUP_CHK_CONFIRMATION){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == TXT_VESSELNAME){
		if(getValue(TXT_VESSELNAME) != '' && getValue(TXT_VESSELNAME) != null){
			enableFieldOnDemand('Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details');
		} else {
			disableFieldOnDemand('Btn_Basic_Vessel_Details,Btn_Ownrship_Details,Btn_Movmnt_Details');
		}
	} else if(event.target.id == UI_EXCHANGE_RATE){
		var exchangeRate = getValue(UI_EXCHANGE_RATE);
		if(exchangeRate.length>0 && (exchangeRate.length-exchangeRate.indexOf('.')) <= 1){
			setValues({[UI_EXCHANGE_RATE]: ''}, true);
			showMessage('', 'Please enter a valid number.', 'error');
		}
	} else if(event.target.id == DEC_CODE){
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id.substring(0,22) == 'Q_Amendment_Data_USER_' ){//mansi
		var PTColumn='Q_Amendment_Data_PT_'+event.target.id.substring(22);
		var FCUBSColumn='Q_Amendment_Data_FC_'+event.target.id.substring(22);
		var value;
		var amendExpiryType=getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE);
		if(getValue(event.target.id) != '' && getValue(event.target.id) != null){
			 value =  getValue(event.target.id);
		}else if(getValue(PTColumn) != '' && getValue(PTColumn) != null){
			 value =  getValue(PTColumn);
        }else{
			 value = getValue(FCUBSColumn);
        }
		var id = "Q_Amendment_Data_FIN_"  + event.target.id.substring(22);
		console.log(id);
		setValues({[id]: value}, true);
		if(event.target.id=='Q_Amendment_Data_USER_TRANSACTION_AMOUNT'){
		setValues({[NEW_TRN_AMT]: getValue("Q_Amendment_Data_FIN_TRANSACTION_AMOUNT")}, true);
		}
		
		if((requestType =='AM' || requestType =='SBLC_AM' || requestType =='ELC_SLCAA' || requestType =='GAA') 
				&& event.target.id=='Q_Amendment_Data_USER_EXPIRY_TYPE'
			 && amendExpiryType =='FD'){	//RR for user amendment frame
			
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
				&& amendExpiryType == 'COND'){
			enableFieldOnDemand(Q_AMENDMENT_DATA_USER_EXPIRY_COND);
			enableFieldOnDemand('Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE');
			enableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
			setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
			setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
		}
	}
}
function clickPCEvent(event){
	console.log('Click Event of PC Started for '+event.target.id);
	if(event.target.id  == BUTTON_SUBMIT){
		saveWorkItem();
		var reqCat=getValue(REQUEST_CATEGORY);
		var reqType=getValue(REQUEST_TYPE);
		if(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType)){
			validateField('EDAS_APPROVAL', 'PLEASE SELECT EDAS APPROVAL');	
		} 
		
		executeServerEvent(event.target.id, event.type, '', false);
	} else if(event.target.id == FINAL_DEDUPE){
		duplicateCheckJSP();
	} else if(event.target.id == VESSEL_DETAILS){
		var params = 'LLI_BVD#'+getValue('WI_NAME')+'#'+getValue('TXT_VESSELNAME');
		openLLI(params);
	} else if(event.target.id == OWNERSHIP_DETAILS){
		var params = 'LLI_OD#'+getValue('WI_NAME')+'#'+getValue('TXT_VESSELNAME');
		openLLI(params);
	} else if(event.target.id == MOVEMENT_DETAILS){
		var params = 'LLI_MD#'+getValue('WI_NAME')+'#'+getValue('TXT_VESSELNAME');
		openLLI(params);
	}//CODE BY MOKSH
	 else if(event.target.id == TSLM_INST_TOGGLE){ 
		var toggleValue = getValue(TSLM_INST_TOGGLE)
		if(toggleValue == false){
			hideControls('SEC_TSLM_CUST_SPECIAL_INST');
		}else if(toggleValue == true){
			showControls('SEC_TSLM_CUST_SPECIAL_INST');
		}
	} else if(event.target.id == BTN_TSLM_INVOICE_CHK_CONFIRM){
		invoiceDuplicateCheckJSP();
	} else if (event.target.id == 'UTC_START_CHECK'){		
		executeServerEvent(event.target.id, event.type, '', false);
	} 
} 


function postServerEventHandlerPC(controlName, eventType, responseData){
	var jsonData = handleTFOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log('Control Name: '+ controlName +',Event Type: '+ eventType);
	console.log('Response Data:');
	console.log(jsonData);
	switch (eventType) {
	case EVENT_TYPE.LOAD: 
		var reqType = getValue(REQUEST_TYPE);
		var reqCat = getValue(REQUEST_CATEGORY);
		var utcRequired = getValue('UTC_REQUIRED');
		if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType)) { //ATP - 207
			setTabStyle("tab1",8, "visible", "true");
			setTabStyle("tab1",8, "disable", "true");
		}
		showAmendFrameFieldsPC();//mansi
		break;
	case EVENT_TYPE.CLICK:
		if(!jsonData.success){
			if(jsonData.message.includes('###')){
				var arr = jsonData.message.split('###');
				showMessage(arr[0], arr[1], 'error');
			}
			saveWorkItem();	 
		}
		else if(controlName == BUTTON_SUBMIT &&!jsonData.message == ''&& 'OpenLinkWIJSP'==jsonData.message){
			    var callRequestType = jsonData.message;
			    setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
//				document.getElementById('sheet100_link').textContent = 'Workitem Creation';
				var jspURL=sLocationOrigin+"/TFO/CustomFolder/FCUBS_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&callRequestType="+callRequestType;
				document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
				selectSheet("tab1",PM_TAB_IFRAME_ID);	 
				disableFieldOnDemand(BUTTON_SUBMIT);
		} else if(controlName == BUTTON_SUBMIT && 'updateDocStatus'==jsonData.message && getValue('IS_UTC_UPDATE_DOC_CALL') != 'Y' && 
			'TXNAC' == getValue('DEC_CODE') && utcRequired == 'Yes' && getValue('IS_GETDOCUMENT_UTC_DONE') == 'Y'){
			UTCUpdateDocStatusJsp();
		} else if (controlName == BUTTON_SUBMIT && getValue('IS_UTC_UPDATE_DOC_CALL') == 'Y'){
			var reqType = getValue(REQUEST_TYPE);
			var reqCat = getValue(REQUEST_CATEGORY);
			if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType)) { //ATP -207
				if(getValue('IS_UTC_UPDATE_DOC_CALL') == 'Y'){
					if(jsonData.success){
						saveWorkItem();	 
						completeWorkItem();
					}
				}
			} 
		} else if(controlName == BUTTON_SUBMIT && (('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))){
			validateField('EDAS_APPROVAL', 'PLEASE SELECT EDAS APPROVAL');	
		}
		else if (controlName == BUTTON_SUBMIT){
			if(jsonData.success){
				saveWorkItem();	 
				completeWorkItem();
			}
		}
		else if (controlName == 'UTC_START_CHECK'){
			 if(!getValue('IS_UTC_UPDATE_DOC_CALL') == 'Y'){
			 	var wd_uid=getWorkItemData("sessionId");
				setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
				var jspURL=sLocationOrigin+"/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&VesselName=updateDocumentStatusUTC&session="+wd_uid+"&WD_UID="+wd_uid;
				document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
				selectSheet("tab1",PM_TAB_IFRAME_ID);	
				document.getElementById(BUTTON_SF_CLOSE).style.display="none";
			 }
			} 
		break;
	case EVENT_TYPE.LOSTFOCUS: 
		if (!jsonData.success){
			var arr = jsonData.message.split('###');
			showMessage(arr[0],arr[1], 'error');
			return false;
		}
		break;
	}
}

function duplicateCheckPPMPC(pControlName,bChange){
	var workstepName=getWorkItemData('activityName');
	var sDuplicateCheckConf = "";
	if(bChange){
		setStyle(DEC_CODE, "0");
	}
	if(sDuplicateCheckConf == "N"){
		if(workstepName == PP_MAKER)
			setValues(DEC_CODE, "REJ");
		else if(workstepName == PROCESSING_CHECKER)
			setValues(DEC_CODE, "RPPM");
			setStyle(DEC_CODE,'disable','true');
	}else{
		setStyle(DEC_CODE,'disable','false');
	}
}


function loadTabValidation() {
	currentTabIndex = getSheetIndex("Tab1");
	var sMode;
	if(currentTabIndex == 0){ 
		setStyle(BUTTON_NEXT,'disable','false');
		setStyle(BUTTON_SUBMIT,'disable','true');
		setStyle(BUTTON_PREVIOUS,'disable','true');
	} else {
		if(sMode=='R'){
			setStyle(BUTTON_NEXT,'disable','false');
			setStyle(BUTTON_SUBMIT,'disable','false');
			setStyle(BUTTON_PREVIOUS,'disable','false');
		} else {
			setStyle(BUTTON_NEXT,'disable','false');
			setStyle(BUTTON_SUBMIT,'disable','false');
			setStyle(BUTTON_PREVIOUS,'disable','false');
			setStyle(BUTTON_SAVE,'disable','false');
		}
	}
}

function onRowClickPC(listviewId,rowIndex) { 
	console.log('listviewId='+listviewId+'rowIndex='+rowIndex);
	if(listviewId == 'QVAR_utc_details'){
		return true;
	}
	return false;
}

function hideshowFinalDesicionFields(){
	genericSetStyle(HIDE_FD,'visible','false');
	genericSetStyle('PC_ADDTNL_MAIL','visible','true');
	genericSetStyle('CHKBX_SEND_MAIL','disable','true');
	
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType= getValue(REQUEST_TYPE);
	
	if('ILC' == reqCat){
		
		if('ILC_UM'==reqType){		//RR
	    	showControls(FCUBS_PUR_OF_MSG);
	    	enableFieldOnDemand(FCUBS_PUR_OF_MSG);
	    }
	}
}

function hideButtons(){
	var HIDE_PD_BUTTONS= 'btnPDFetch,btnAddPartyDetails,btnModifyPartyDetails,btnDeletePartyDetails';
	var HIDE_CL_BUTTONS= 'btnAddContractLimits,btnModifyContractLimits,btnDeleteContractLimits';
	genericSetStyle(HIDE_PD_BUTTONS,'visible','false');
	genericSetStyle(HIDE_CL_BUTTONS,'visible','false');
}

function finalDecisionCheckListHandlingPC(){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType= getValue(REQUEST_TYPE);
	console.log("inside finalDecisionPCCheckListHandling");
	console.log("REQUEST_CATEGORY---"+reqCat);
	console.log("REQUEST_TYPE--"+reqType);
	if (reqType!="GRNT_SD" &&  reqType!="IFP_SD" && reqType!="ELCB_SD" && reqType!="ILC_SD" && 
		reqType!="ELC_SD" && reqType!="IC_SD" && reqType!="TL_SD" && reqType!="IFS_SD" && 
		reqType!="EC_SD" && reqType!="ILCB_SD" && reqType!="DBA_SD" && reqType!="SG_SD" && reqType!="SBLC_SD"){  //Shipping_Gtee_82 //RR
		if(reqCat==("IFS")||reqCat==("IFP")||reqCat==("IFA") ||reqCat==("SCF")){	//UPDATED BY MOKSH
			setStyle("FIN_DEC_FRAME",'visible','true');
			setStyle('IFS_Final_Dec_frame','visible','true');
		}else if(reqCat==("TL")){
			setStyle("FIN_DEC_FRAME",'visible','true');
			setStyle("Common_Fields",'visible','true');
			setStyle('IFS_Final_Dec_frame','visible','true');
		}
		else if(reqCat==("ILC") || (reqCat==("GRNT") 
				&& (reqType=="NI" ||reqType== "AM"))||
				(reqCat==("SBLC") 
						&& (reqType=="SBLC_NI" ||reqType== "SBLC_AM"))||
				(reqCat==("ELC") 
								&& (reqType=="ELC_SLCA" ||reqType== "ELC_SLCAA"))) {//RR
			console.log("inside us3017");
			setStyle("PC_ILC_Controls",'visible','true');
			setStyle("PC_ILC_Controls",'visible','true');
			setStyle("SWIFT_LMT_VERIFY",'visible','true');
			setStyle("CA_CHCKED",'visible','true');
			setStyle("LBL_TFO_CA_CHCKED",'visible','true');
			setStyle("ILC_Controls",'visible','true');
			setStyle("ILC_Controls",'disable','true');
			if(reqCat==("GRNT") && (reqType=="NI" || reqType=="AM"))
			{
				console.log("ISNIDE IF FOR DISABLE");
				setStyle("SWIFT_LMT_VERIFY", 'disable','true');
			}else if(reqCat==("SBLC") && (reqType=="SBLC_NI" || reqType=="SBLC_AM"))	//RR
			{
				console.log("ISNIDE IF FOR DISABLE");
				setStyle("SWIFT_LMT_VERIFY", 'disable','true');
			}else if(reqCat==("ELC") && (reqType=="ELC_SLCA" || reqType=="ELC_SLCAA"))	//RR
			{
				console.log("ISNIDE IF FOR DISABLE");
				setStyle("SWIFT_LMT_VERIFY", 'disable','true');
			}
		}else if(reqCat==("ILCB")){
			setStyle("PC_ILCB_Controls",'visible','true');
			setStyle("ILCB_Controls",'visible','true');
		}else if(reqCat==("IC")){
			console.log('in IC');
			console.log('in IC'+getValue('IS_STAGE_CHANGED'));
			setStyle("IC_Controls",'visible','true');
			setStyle("IC_Controls",'disable','true');
			setStyle("IS_STAGE_CHANGED",'disable','true');
			console.log('in IC after'+getValue('IS_STAGE_CHANGED'));
			setStyle("Common_Fields",'visible','true');
		}else if(reqCat==("EC")){
			setStyle("PC_EC_Controls",'visible','true');
			setStyle("EC_Controls",'visible','true'); 
		}else if(reqCat==("ELCB")){
			setStyle("PC_ELCB_Controls",'visible','true');
		 	setStyle("ELCB_Controls",'visible','true');
		}else if(reqCat==("ELC")){
			setStyle("PC_ELC_Controls",'visible','true');
			setStyle("ELC_Controls",'visible','true');
			setStyle("ELC_Controls",'disable','true');
		}	
	}else{
		setStyle("ROUTE_TO_PM",'disable','true' );
		setStyle("PC_ADDTNL_MAIL", 'disable','true');
		setStyle("REMARKS", 'disable','true');
		setStyle('add_Qvar_cpd_details','disable','true');
	}
}

function onClickTabPC(tabId,sheetindex,eventCall){
	console.log('sheetindex='+sheetindex+'eventCall='+eventCall);	
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex id: '+event.target.id+'event.target.innerHTML='+event.target.innerHTML);
	var controlID=sheetindex+','+event.target.innerHTML;
	var input=sheetindex;
	console.log('onClickTabPC');
	if(eventCall == 1 && input == 9)
	{
		saveWorkItem();
		executeServerEvent('onClickTabPC', EVENT_TYPE.CLICK, input, false);
	}else if(!(eventCall == 1 && input == 9)){
		disableFieldOnDemand(BUTTON_SUBMIT);
	}
	if((document.getElementById('JSP_Frame_Contract')!=null  &&document.getElementById('tab1').getElementsByClassName("tab-pane fade")[10].style.display!='none')
			&&sheetindex!=10 && eventCall==2 ){
		showMessage('', 'Please click on close button', 'error');
		return false;
		}
	if(sheetindex == PM_TAB_IFRAME_ID){ //hide new button in appliaction orachestratio tab 
		setStyle(BTN_IFRAME_CLOSE, PROPERTY_NAME.VISIBLE, 'false');
	}
}
function setFCUBSContractNumber(){   //Shipping_Gtee_81
	var reqtype = getValue(REQUEST_TYPE);
	if("SG_NIC" == reqtype || "SG_NILC" == reqtype){
	  setValues({[FCUBS_CON_NO]: getValue(TRANSACTION_REFERENCE)}, true);
	  disableFieldOnDemand(FCUBS_CON_NO);
	}
}

function handleJSPResponsePC(typeOfJsp,finalResult){
	//link workitem jsp handling
	var workstepName=getWorkItemData('activityName');
	if(workstepName==WORKSTEP.PP_MAKER){
		selectSheet("tab1",8);
		saveWorkItem();
		setTabStyle("tab1",9, "visible", "false");
	}else if(workstepName==WORKSTEP.PROCESSING_CHECKER){
		selectSheet("tab1",8);
		saveWorkItem();
		setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");	
	}
	
 //if(typeOfJsp=='OpenLinkWIJSP' && finalResult=='Success' ){
	// completeWorkItem(); 
//}
}
//CODE BY MOKSH
function formLoadIFPIFAIFS(){
	var processing_system = getValue(PROCESSING_SYSTEM);
	var requestType = getValue(REQUEST_TYPE);
	var requestCategory = getValue(REQUEST_CATEGORY);
	if (requestCategory == 'IFA' || requestCategory == 'IFP' || requestCategory == 'IFS' || requestCategory == 'SCF') {
		selectchecboxTSLM();
		showControls('PROCESSING_SYSTEM,STANDALONE_LOAN');
		if (processing_system == 'T') {
			showControls(IF_FRAME );
			showControls(IF_FRAME1);
			showControls(SEC_TSLM_LOAN_REF_DET);
			if (requestType == 'LD' || requestType == 'PD' || 'PDD' == reqType) {
				showControls('TSLM_COMPANY_TYPE,Toggle2,LOAN_AMOUNT,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				showControls('BTN_FETCH_TSLM_CID_DETAILS,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID');
				disableFieldOnDemand('PROCESSING_SYSTEM,STANDALONE_LOAN,LOAN_AMOUNT,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID');
				disableFieldOnDemand('TSLM_COMPANY_TYPE,TAB_TSLM_LOAN_REF');
				if (getValue('STANDALONE_LOAN') == '1') {
					showControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,BTN_TSLM_INVOICE_CHK_CONFIRM');
					disableFieldOnDemand('TSLM_INVOICE_CHK_CONFIRM,BTN_TSLM_INVOICE_CHK_CONFIRM');
				}
				setValues({['Toggle2']: 'true'}, true);
				showControls('SEC_TSLM_CUST_SPECIAL_INST');
			} else if (requestType == 'AM' || requestType == 'STL'|| requestType == 'IFA_CTP' ) {
				hideControls('Toggle2,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				if(requestType == 'AM'){
					showControls('SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				}
				showControls('SEC_TSLM_LOAN_INPUT_DETAILS');
				showControls('FINANCED_AMOUNT,OVERALL_OUTSTANDING_AMOUNT,LOAN_STAGE');
				if (requestType == 'IFA_CTP') {
					showControls('IF_PURCHASE_PRODUCT_CODE,FUNDING_REQUIRED,ADDITIONAL_LOAN_AMOUNT,BTN_FETCH_TSLM_CID_DETAILS');
					if (requestCategory == 'IFA') {
						disableFieldOnDemand('OVERALL_OUTSTANDING_AMOUNT,FUNDING_REQUIRED,ADDITIONAL_LOAN_AMOUNT,TAB_TSLM_LOAN_REF');
						disableFieldOnDemand('PROCESSING_SYSTEM,STANDALONE_LOAN,LOAN_STAGE,IF_PURCHASE_PRODUCT_CODE,FINANCED_AMOUNT');
					}
				} else if (requestType == 'AM' || requestType == 'STL') {
					disableFieldOnDemand('PROCESSING_SYSTEM,STANDALONE_LOAN,FINANCED_AMOUNT,OVERALL_OUTSTANDING_AMOUNT,LOAN_STAGE');
					hideControls('LOAN_AMOUNT');
				} 
			}//CODE BY KRITIKA END 
		} else if (processing_system == 'F') { 
			showControls('STANDALONE_LOAN,PROCESSING_SYSTEM');
			disableFieldOnDemand('STANDALONE_LOAN,BTN_FETCH_TSLM_CID_DETAILS,SEC_TSLM_REF_DET');
			hideControls('SEC_TSLM_REF_DET,SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_TSLM_LOAN_REF_DET,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
			hideControls('TSLM_DEDUPE_LABEL,LOAN_AMOUNT,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM,SEC_TSLM_CUST_SPECIAL_INST');
			hideControls('Toggle2,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE,BTN_FETCH_TSLM_CID_DETAILS');
			setValues({['STANDALONE_LOAN']: '1'}, true);
		}
	}	
}	
function UTCUpdateDocStatusJsp(){
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat  || 'SCF' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType || 'PD' == reqType || 'PDD' == reqType )) {  //ATP 254

		if(getValue('IS_UTC_UPDATE_DOC_CALL') != 'Y'){
			var wd_uid=getWorkItemData("sessionId");
			setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			var jspURL=sLocationOrigin+"/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&VesselName=updateDocumentStatusUTC&session="+wd_uid+"&WD_UID="+wd_uid;
			document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
			selectSheet("tab1",PM_TAB_IFRAME_ID);	
			//document.getElementById(BUTTON_SF_CLOSE).style.display="none";
		}
	} 
}
function enableDisableFieldsPC_GRNT_NI()//santhosh
{
	var processType=getValue(PROCESS_TYPE);
	var reqType = getValue(REQUEST_TYPE);
	if(reqType == 'NI' || reqType == 'SBLC_NI'|| reqType == 'ELC_SLCA'){
	if(processType == 'PT' || processType == 'ET'){  //ATP-469 Shahbaz 23-05-2024 
		showControls(PC_GRNT_NI_PT_SHOW);
		disableFieldOnDemand(PC_GRNT_NI_PT_DISABLE);
		enableFieldOnDemand(PC_GRNT_NI_PT_ENABLE);
	}
	else if(processType == 'BAU'){
		showControls(PC_GRNT_NI_BAU_SHOW);
		disableFieldOnDemand(PC_GRNT_NI_BAU_DISABLE);
		enableFieldOnDemand(PC_GRNT_NI_BAU_ENABLE);
	} 
	else if(processType == 'SWIFT'){
		showControls(PC_GRNT_NI_SWIFT_SHOW);
		disableFieldOnDemand(PC_GRNT_NI_SWIFT_DISABLE);
		enableFieldOnDemand(PC_GRNT_NI_SWIFT_ENABLE);
		//showSection('PT_GTEE_FRAME,PT_FFT_Frame,PT_Document_Detail'); //sheenu
		hideControls(PC_GRNT_NI_SWIFT_HIDE); //part of  PT_GTEE_FRAME
	}
	}
}

/*//santhosh
function EnableFieldValAnyDoc_SBLC_AM(){
if(reqType == 'SBLC_AM'){ 
	   showControls('LC_FRAME');//LC_FRAME
	 	hideControls('LC_CONF_AMNT,LC_CORRSPNDNT_BNK,LC_UNDER,LC_ABOVE,LC_TOLERANCE');//santhosh
	   showControls(ANY_DOC_COURRIER);
	   setStyle(ANY_DOC_COURRIER,PROPERTY_NAME.DISABLE,'false');
	   setValues({[ANY_DOC_COURRIER]: '2'}, true);
}
} */


function pcHybridcombo(){  //Krishna
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
//Added for SCF  ATP - 207
function enableDisableFieldsPC_SCF_PD(){
  var processType=getValue(PROCESS_TYPE);
  var reqCat = getValue(REQUEST_CATEGORY);
  var reqType = getValue(REQUEST_TYPE);
  if(reqCat == 'SCF' &&(reqType == 'PD'|| 'PDD' == reqType)){
  document.getElementById('INF_LOAN_CURR_label').innerText = 'Discount Currency';
	if(processType == 'BAU'){
		hideControls(PC_SCF_PD_BAU_FRAMES_HIDE);
		disableFieldOnDemand(PC_SCF_PD_BAU_DISABLE);
		hideControls(PC_SCF_PD_BAU_HIDE);
	} 
	else if(processType == 'TSLM Front End'){
		showControls(PC_SCF_PD_TSLM_FRONTEND_SHOW); 
		hideControls(PC_SCF_PD_TSLM_FRONTEND_HIDE);
		hideControls(PC_SCF_PD_TSLM_FRONTEND_FRAMES_HIDE);
	}
  }
}