function onPPMakerFormLoad(){
	var workstepName = getWorkItemData('activityName');
	onLoad();
	setDefaultValue(workstepName);
	disabledefaultPPMFields();
	setDefaultValues();
	enableDisableFieldsLmtCre();
	enableDisableFieldsDocRev();
	enableDisableFieldsTextVet();
	enableDisableFieldsSignAcc();
	disableAmendType(AMEND_TYPE);
	reqCatDupCheckFilter();	
	disableFieldOnDemand(FIELD_PRO_TRADE_SETTLEMENT_INST+","+FIELD_PRO_TRADE_REF_NO+","+FIELD_INSTRCTN_TO_BANK);
	disableFieldOnDemand(DISABLE_PPM);
	setButtonName(BUTTON_ADD_DOC_CHKLIST,LISTVIEW_DOC_CHKLIST,'0');
	setButtonName(BUTTON_LIMIT_ADD_CKLIST,LISTVIEW_LIMIT_CKLIST,'0');
	lockTFOSection(LOCK_PPM_FRAMES);
	disableFieldOnDemand(BUTTON_SUBMIT);
	setTabStyle("tab1",8, "visible", "false");
	setTabStyle("tab1",10, "visible", "false");
	setTabStyle("tab1",12, "visible", "false");
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {
		setTabStyle("tab1",8, "visible", "true");
		setStyle('UTC_REQUIRED_BRMS', PROPERTY_NAME.VISIBLE, 'true');  // Added
																		// by
																		// reyaz
																		// 5082022
		setStyle('UTC_CONVERTED_AMOUNT', PROPERTY_NAME.VISIBLE, 'true'); // Added
																			// by
																			// reyaz
																			// 5082022
		setStyle('UTC_REQUIRED', PROPERTY_NAME.VISIBLE, 'true');  // Added by
																	// reyaz
																	// 5082022
		setStyle('UTC_JSTIFICATION_REQUIRED', PROPERTY_NAME.VISIBLE, 'true'); // Added
																				// by
																				// reyaz
																				// 5082022
		var utcRequire = getValue('UTC_REQUIRED_BRMS');
		if(getValue('UTC_REQUIRED') == ''){
			setValue('UTC_REQUIRED',utcRequire);
		}
		//ATP-481 --JAMSHED --11-JUN-2024 START
		/*if(('IFA' == reqCat) && ('LD' == reqType)){
			setValue('UTC_REQUIRED','No');
			setValue('UTC_JSTIFICATION_REQUIRED','UTC Not Applicable for IFA Disbursement');
		}*/
		//ATP-481 --JAMSHED --11-JUN-2024 END
		//ATP -488 REYAZ 03-07-2024  START	
		if (reqCat === 'IFA' && reqType === 'IFA_CTP') { // added by krishna
			var fieldsToDisable = ['INF_TENOR_DAYS', 'INF_TENOR_BASE', 'INF_BASE_DOC_DATE'];
			fieldsToDisable.forEach(field => setStyle(field, PROPERTY_NAME.DISABLE, 'true'));
			var additionalLoanAmount = getValue('ADDITIONAL_LOAN_AMOUNT');
			var fundingRequired = additionalLoanAmount === '' || additionalLoanAmount === '0' ? '2' : '1';
			setValues({ [TSLM_FUNDING_REQUIRED]: fundingRequired }, true);
			enableFieldOnDemand(TSLM_ADDITIONAL_LOAN_AMOUNT);
		}
		//ATP -488 REYAZ 03-07-2024  END	

		}
	    
	setTabStyle("tab1",11, "visible", "false");
	disablePTFields(); // PT
	ecFramePT();
	disableForILC_AM();
	enableCustInstSection();
	ptCommonFieldsHandling(); // US 159
	enablePTHybdridTxnFieldAtPPM();
	enableGoodsDescFieldAtPPM();
	// deletePTSourceChannel();
	setRouteToPPM();
	selectchecboxTSLM();
	tslmReferral();
	disableFieldsGRNT_CC_ER_CL_EPC(); // added by Rakshita
	setComboFieldsGRNT_CC_ER_CL_EPC();// added by Rakshita
	expiryTypeDisableGRNT();
	setTextVettingComboFieldsSBLC_NI(); // BY KISHAN
	// setDefaultFields();
	// grntThirdPartyPPM();//mansi
	/*
	 * if(PT_UTILITY_FLAG == 'Y'){ disableFieldOnDemand('DELIVERY_BRANCH'); }
	 */
	disableOnFormIFCPC();
	ppmhybridcombo(); //krishna
	ppmTslmValidation();// krishna
	setProductCodeTslmFrontEnd();//krishna
	//TSLMPPMAutoTriggerJsp();
	counterPartyApproval();//krishna
	disableTabCounter();// krishna
	supportDoc();// krishna
	//sufficentBal();//commented to handle closed after referral is closed
	listViewPastDue();
	productType();
	setDefaultField();   // krishna
	renameInputDetails();//ATP-151 TO 157 ADDED by KRISHNA PANDEY
	//ADDED BY SHIVANSHU
	if(('BAU'== getValue(PROCESS_TYPE)) || ('PT'== getValue(PROCESS_TYPE))){
		if (('ELCB_AM' == reqType) || ('EC_AM' == reqType) || ('ELCB_BL' == reqType) || ('EC_BL' == reqType)){
			setStyle('IS_REMOTE_PRESENTATION', PROPERTY_NAME.VISIBLE, 'true');
		}
	}	
   //ATP-454 02/05/2024 REYAZ START
	if((reqCat == 'IFA' || reqCat == 'IFP' ||reqCat == 'IFS') && reqType == 'AM'){
		showControls('AMENDED_EFFECTIVE_DATE');
        }
	//ATP-454 02/05/2024 REYAZ ENDS	
	    //ATP-463 16/05/2024 JAMSHED START
	if(getValue('PAST_DUE_LIABILITY')!='' && getValue('PAST_DUE_LIABILITY')!=null && getValue('PAST_DUE_LIABILITY') !=undefined ){
		setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.DISABLE, 'true');
	}
	//ATP-463 16/05/2024 JAMSHED END
	handleSwiftMtUI(); //ATP-458 REYAZ 05-07-2024
}
function setRouteToPPM(){
	var routeToPPM=getValue(ROUTE_TO_PPM);
	if(null==routeToPPM || ''==routeToPPM||'0'==routeToPPM){
		setValues({[ROUTE_TO_PPM]: 'ADCB Maker'}, true);
	}
}
 function enableDisableFieldsPPM_GRNT_NI()
{
	var processType=getValue(PROCESS_TYPE);
	if(processType == 'PT' || processType == 'ET'){   //ATP-469 Shahbaz 23-05-2024 
		showControls(PPM_GRNT_NI_PT_SHOW);
		disableFieldOnDemand(PPM_GRNT_NI_PT_DISABLE);
		enableFieldOnDemand(PPM_GRNT_NI_PT_ENABLE);
	}
	else if(processType == 'BAU'){
		showControls(PPM_GRNT_NI_BAU_SHOW);
		enableFieldOnDemand(PPM_GRNT_NI_BAU_ENABLE);
	} 
	else if(processType == 'SWIFT'){
		showControls(PPM_GRNT_NI_SWIFT_SHOW);
		disableFieldOnDemand(PPM_GRNT_NI_SWIFT_DISABLE);
		enableFieldOnDemand(PPM_GRNT_NI_SWIFT_ENABLE);
		// showSection('PT_GTEE_FRAME,PT_FFT_Frame,PT_Document_Detail');
		// //sheenu
		hideControls(PPM_GRNT_NI_SWIFT_HIDE); // part of PT_GTEE_FRAME
	}
} 
 function enableDisableFieldsPPM_ELC_SLCA()
 {
 	var processType=getValue(PROCESS_TYPE);
 	if(processType == 'PT'){
		showControls(PPM_ELC_SLCA_PT_SHOW);
		disableFieldOnDemand(PPM_ELC_SLCA_PT_DISABLE);
		enableFieldOnDemand(PPM_ELC_SLCA_PT_ENABLE);
		hideControls(PPM_ELC_SLCA_PT_HIDE);
	}
 	else if(processType == 'BAU'){
 		showControls(PPM_ELC_SLCA_BAU_SHOW);
 		enableFieldOnDemand(PPM_ELC_SLCA_BAU_SHOW);
 		hideControls(PPM_ELC_SLCA_BAU_HIDE);
 	} 
 	else if(processType == 'SWIFT'){
 		showControls(PPM_ELC_SLCA_SWIFT_SHOW);
 		disableFieldOnDemand(PPM_ELC_SLCA_SWIFT_DISABLE);
 		enableFieldOnDemand(PPM_ELC_SLCA_SWIFT_ENABLE);
 		// showSection('PT_GTEE_FRAME,PT_FFT_Frame,PT_Document_Detail');
		// //sheenu
 		hideControls(PPM_ELC_SLCA_SWIFT_HIDE); // part of PT_GTEE_FRAME
 	}
 }
 function enableDisableFieldsPPM_GRNT_AM()
 {
 	var processType=getValue(PROCESS_TYPE);
 	if(processType == 'PT'){
 		showControls(PPM_GRNT_AM_PT_SHOW);
 	}
 	else if(processType == 'BAU'){
 		showControls(PPM_GRNT_AM_BAU_SHOW);
 	} 
 	else if(processType == 'SWIFT'){
 		showControls(PPM_GRNT_AM_SWIFT_SHOW);
 	}
 } 
 function enableDisableFieldsPPM_GRNT_GAA()
 {
 	var processType=getValue(PROCESS_TYPE);
 	if(processType == 'PT'){
 		showControls(PPM_GRNT_GAA_PT_SHOW);
 	}
 	else if(processType == 'BAU'){
 		showControls(PPM_GRNT_GAA_BAU_SHOW);
 	} 
 	else if(processType == 'SWIFT'){
 		showControls(PPM_GRNT_GAA_SWIFT_SHOW);
 	}
 }
//Added by Ayush.
//function TSLMfieldchangesPPM(){
//f(getValue('REQUEST_CATEGORY') == 'IFA'&&getValue('REQUEST_TYPE')=='CPT'){
//setStyle('INF_TENOR_DAYS',PROPERTY_NAME.DISABLE,'true');
//setStyle('INF_BASE_DOC_DATE',PROPERTY_NAME.DISABLE,'true');
//setStyle('INF_TENOR_BASE',PROPERTY_NAME.DISABLE,'true');
//setValue('STANDALONE_LOAN',2);
//setValue('INF_AMEND_TYPE','APTP');
//setStyle('INF_AMEND_TYPE',PROPERTY_NAME.DISABLE,'true');
//}

//}
 function enableDisableFieldsPPM_GRNT_GA()
 {
 	var processType=getValue(PROCESS_TYPE);
 	if(processType == 'BAU'){
 		showControls(PPM_GRNT_GA_BAU_SHOW);
		enableFieldOnDemand(PPM_GRNT_GA_BAU_ENABLE);
 	} 
 	else if(processType == 'SWIFT'){
 		showControls(PPM_GRNT_GA_SWIFT_SHOW);
 		disableFieldOnDemand(PPM_GRNT_GA_SWIFT_DISABLE);
		enableFieldOnDemand(PPM_GRNT_GA_SWIFT_ENABLE);
 	}
 }
function onLoad(){
//TSLMfieldchangesPPM();//Added by Ayush
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var processType=getValue(PROCESS_TYPE);
	var productType= getValue(COMBOX_PRODUCT_TYPE);
	if(reqType == 'SBLC_SD' ||reqType == 'SBLC_MSM' // added by santhosh
		||reqType == 'GRNT_SD'||reqType == 'GRNT_MSM' || reqType == 'GAA' ){
		hideControls(GRNT_FRAMES_GRNT_SD_GAA);
		enableDisableFieldsChargeAccNo();
		enableDisableFieldsTrnTenor();
		 if(reqType == 'GAA'){// sheenu
		       enableDisableFieldsPPM_GRNT_GAA();
	        }
	       

	} else if (reqCat == 'GRNT' || reqCat == 'SBLC' || 
			(reqCat == 'ELC' && (reqType == 'ELC_SLCA' 
				|| reqType == 'ELC_SLCAA' || reqType == 'ELC_SER' || reqType == 'ELC_SLC'))){// added
																								// by
																								// santhosh
		showSection('GTEE_FRAME,GRNT_FRM_NEW,FRM_GRTEE_LC_COMMON');
		hideControls(GRNT_FRAMES_HIDE);	
		enableDisableFieldsChargeAccNo();
		enableDisableFieldsTrnTenor();
		if(reqType == 'NI' || reqType == 'SBLC_NI' || reqType == 'ELC_SLCA'){// added
																				// by
																				// santhosh
																				// &
																				// ELC
																				// added
																				// by
																				// mansi
			enableDisableFieldsChargeAccNo();
		if (reqType == 'ELC_SLCA'){
			enableDisableFieldsPPM_ELC_SLCA();
		} else if ( reqType == 'SBLC_NI'){
			enableDisableFieldsPPM_SBLC_NI();
		}else{
			enableDisableFieldsPPM_GRNT_NI();
		}
		if(productType.includes("T5"))	// RR
		{
			setValues({[COMBOX_TRN_THIRD_PARTY]: '2'}, true);// TRN_THIRD_PARTY
		}else{
			if(reqCat == 'GRNT' && reqType == 'NI'){
				setEmptyCombo(TRN_THIRD_PARTY,'');
					// log.info("set blank");
			}
		}
		if(reqType == 'SBLC_NI'){  // ADDED BY KISHAN
				var source_channel = getValue(SOURCE_CHANNEL);
			 	/*
				 * if(processType == 'SWIFT' && source_channel == 'S' ){
				 * setValues({[COMBOX_TRN_THIRD_PARTY]: '2'},
				 * true);//TRN_THIRD_PARTY }
				 */
			 	showControls(ANY_DOC_COURRIER);
			 	setStyle(ANY_DOC_COURRIER,PROPERTY_NAME.DISABLE,'false');
			 	setValues({[ANY_DOC_COURRIER]: '2'}, true);
			 	setStyle(PURPOSE_OF_MESSAGE,PROPERTY_NAME.DISABLE,'false');
			 	// setValues({[PURPOSE_OF_MESSAGE]:'ISSU'},true);//commented by
				// santhosh
			 	showControls(CUSTOMER_INSTRUCTION);// For swift and pt it is
													// visible only
			 	showControls('CHARGE_TYPE');
			 	showControls(REQ_CONFIRM_PARTY);
			 	// setValues({[FCUBS_PUR_OF_MSG]:'Issue'},true);//Issue//commented
				// by santhosh
			 	if(processType == 'BAU'){ // need to make mandetory
			 		setStyle(CUSTOMER_INSTRUCTION,PROPERTY_NAME.DISABLE,'false');
			 	}else{
			 		//setStyle(TRN_TENOR,PROPERTY_NAME.DISABLE,'true');  //ATP-383 REYAZ 02-05-2024
			 	}
			 	if(getValue(CUSTOMER_INSTRUCTION) == 'MA' || getValue(CUSTOMER_INSTRUCTION)  == 'CF'){ // need
																										// to
																										// make
																										// mandetory
			 		setStyle(REQ_CONFIRM_PARTY,PROPERTY_NAME.DISABLE,'false');
			 	}
			 	if(processType == 'PT' || processType == 'SWIFT'){ // santhosh
																	// changes
			 		setStyle(EXP_DATE,PROPERTY_NAME.DISABLE,'true');
			 	}
			 	showControls('PT_ILC_FRAME');
			 	hideControls(PT_ILC_FRAME_FIELD_HIDE);
			 	showControls('LC_FRAME');// LC_FRAME
			 	hideControls('LC_CONF_AMNT,LC_CORRSPNDNT_BNK,LC_CORRSPNDNT_BNK,LC_UNDER,LC_ABOVE,LC_TOLERANCE');
			}
			if(reqType == 'SBLC_NI' || reqType == 'NI')
			{
				enableFieldOnDemand(SWIFT_PUR_OF_MSG);
			}
	    } else if(reqType == 'AM' || reqType == 'SBLC_AM'|| reqType == 'ELC_SLCAA'){// added
																					// by
																					// santhosh
																					// &
																					// ELC
																					// added
																					// by
																					// mansi
	       enableDisableFieldsPPM_GRNT_AM();
	       if(reqType == 'SBLC_AM'){ // ADDED BY KISHAN
	    	   showControls('LC_FRAME');// LC_FRAME
			 	hideControls('LC_CONF_AMNT,LC_CORRSPNDNT_BNK,LC_UNDER,LC_ABOVE,LC_TOLERANCE');// santhosh
	    	   showControls(ANY_DOC_COURRIER);
	    	   setStyle(ANY_DOC_COURRIER,PROPERTY_NAME.DISABLE,'false');
	    	   setValues({[ANY_DOC_COURRIER]: '2'}, true);
	       }
            var userExpirydate =  getValue('Q_Amendment_Data_USER_EXPIRY_TYPE');
            var ptExpirydate =  getValue('Q_Amendment_Data_PT_EXPIRY_TYPE');

	        if(userExpirydate == 'OE' || ptExpirydate == 'OE'){
		    	 setValues({['Q_Amendment_Data_USER_EXPIRY_DATE']: ''}, true);
		         disableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
		    	 setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: ''}, true);
		       disableFieldOnDemand('Q_Amendment_Data_FIN_EXPIRY_DATE');
	        }
        } else if(reqType == 'GA'){// Santhosh
 	       enableDisableFieldsPPM_GRNT_GA();
         }
	}
	else if(reqCat == 'IFS' || reqCat == 'IFP' || reqCat == 'IFA'){
		hideControls(IFS_FRAMES_HIDE);
		enableDisableFieldsChargeAccNo();
		if(reqCat == 'IFS' && reqType=='LD'){
			showControls(IFS_BUYER);
			setValues({[TSLM_COMPANY_TYPE]: 'S'}, true);
		}else if((reqCat == 'IFA' || reqCat == 'IFP') && (reqType=='LD' || reqType=='IFA_CTP' )){
		setValues({[TSLM_COMPANY_TYPE]: 'B'}, true);
		}
		setProductCodeTSLM(); 
		// CODE by MOKSH
		showControls('PROCESSING_SYSTEM,STANDALONE_LOAN');
		disableFieldOnDemand('TSLM_REFERRAL_FLAG');
		 if(getValue(PROCESSING_SYSTEM) == 'T'){
			tslmReferral();
			enaDisInvoiceNoStandaLone();
			if(reqType == 'LD'){
				if(getWorkItemData('ActivityName')  == 'PP_MAKER'){
					if(reqCat == 'IFP' || reqCat == 'IFA' ){
						if(getValue(TSLM_STANDALONE_LOAN) == ''){// santhosh
							setValues({[TSLM_STANDALONE_LOAN]: '1'}, true);
						}
					}else {
						if(getValue(TSLM_STANDALONE_LOAN) == ''){// santhosh
							setValues({[TSLM_STANDALONE_LOAN]: '2'}, true);
						}
					}
				}
				if(getValue(TSLM_STANDALONE_LOAN) == '1' && getGridRowCount('Q_TSLM_Invoice_No_SA_Loan') >0){
					showControls('TSLM_DEDUPE_LABEL,BTN_TSLM_INVOICE_CHK_CONFIRM,TSLM_INVOICE_CHK_CONFIRM');
				}else if(getValue(TSLM_STANDALONE_LOAN) == '2' ){
					hideControls('TSLM_DEDUPE_LABEL,BTN_TSLM_INVOICE_CHK_CONFIRM,TSLM_INVOICE_CHK_CONFIRM');
				}
				showControls(DISBURSEMENT_LOAD_ENABLE);// A_S_CP_LOAD_ENABLE
				showControls('SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN,SEC_TSLM_COUNTER_PARTY_DETAILS');
				enableFieldOnDemand('PROCESSING_SYSTEM,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN');
				showControls('Toggle2,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE,BTN_FETCH_TSLM_CID_DETAILS');
				enableFieldOnDemand('STANDALONE_LOAN,BTN_FETCH_TSLM_CID_DETAILS');// SEC_TSLM_CUST_SPECIAL_INST
				setValues({['Toggle2']: 'true'}, true);
				showControls('SEC_TSLM_CUST_SPECIAL_INST');
			} else if(reqType == 'AM' || reqType == 'STL' || reqType == 'IFA_CTP'  ){	// Amendment,
																						// Settlement,
																						// Conversion
																						// to
																						// Purchase
				hideControls(TSLM_INST_TOGGLE);
				hideControls('TSLM_DEDUPE_LABEL,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM');
				showControls(A_S_CP_LOAD_ENABLE);
				disableFieldOnDemand('PROCESSING_SYSTEM');
				enableDisableStnTd();    // added by reyaz
				if(reqType == 'IFA_CTP'){
					showControls('BTN_FETCH_TSLM_CID_DETAILS,TSLM_COMPANY_TYPE,IF_PURCHASE_PRODUCT_CODE,FUNDING_REQUIRED,ADDITIONAL_LOAN_AMOUNT');
					enableFieldOnDemand('BTN_FETCH_TSLM_CID_DETAILS,IF_PURCHASE_PRODUCT_CODE,FUNDING_REQUIRED');
					if(reqCat == 'IFA' ){
						if(getValue(TSLM_FUNDING_REQUIRED) == '1'){
							enableFieldOnDemand(TSLM_ADDITIONAL_LOAN_AMOUNT);
						}
					}
				} 
			}				
		} else if(getValue(PROCESSING_SYSTEM) == 'F'){
			showControls(TSLM_STANDALONE_LOAN);
			setValues({[TSLM_STANDALONE_LOAN]: '1'}, true);
			disableFieldOnDemand('STANDALONE_LOAN');
			hideControls('Toggle2');
			// hideControls('TSLM_DEDUPE_LABEL,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM,BTN_FETCH_TSLM_CID_DETAILS,SEC_TSLM_REF_DET');
		}
		// END OF CODE by MOKSH
	} else if(reqCat == 'TL'){
		hideControls(TL_FRAMES_HIDE);
	} else if(reqType == 'ILCB_BL'){
		var xx=document.getElementById(IF_FRAME);
		var t=xx.childNodes[0].firstChild;
		t.textContent="Bills Input Details";
		hideControls(ILCB_BL_HIDE_FRAMES);
	} else if(reqCat == 'IC' || reqCat == 'ILCB' || reqCat == 'ELCB' || reqCat == 'DBA' || reqCat == 'EC'){

		var xx=document.getElementById(IF_FRAME);
		var t=xx.childNodes[0].firstChild;
		t.textContent="Bill Input Details";
		hideControls(IC_ILCB_ELCB_DBA_FRAMES_HIDE);	
		ecFramePT();
       if(reqType == 'ELCB_BL'){
		showControls(COMBO_CUST_INSTR);
	   }
       if(reqType == 'ELCB_AM'){
   		showControls(ELCB_AM_FIELDS);
   		enableFieldELCB_AM_DISC();  // PT_283-284
   	   }
       if(reqType == 'ELCB_DISC'){
      	showControls(ELCB_AM_FIELDS);
      	enableFieldELCB_AM_DISC();   // PT_283-284
       }
	   if(reqType == 'EC_AM'){
      	showControls(EC_AM_FIELDS);
      	enableFieldELCB_AM_DISC();   // PT_113-114
       }
	   if(reqType == 'EC_DISC'){
      	showControls(EC_AM_FIELDS);
      	enableFieldELCB_AM_DISC();   // PT_114-114
       }
	} else if(reqCat == 'ILC' || reqCat == 'ELC'){
		hideControls(ILC_ELC_FRAMES_HIDE);	 
		var xx=document.getElementById(GTEE_FRAME);
		var t=xx.childNodes[0].firstChild;
		t.textContent="LC Input Details";
		(TRN_TENOR);
		showControls(FIELD_ISSUING_BANK_LC_NO+","+FIELD_ADVISING_BANK_REF);
		hideControls(FIELD_GRNTEE_EXP_DATE+","+FIELD_GRNTEE_APP_CNTRCT_REF_NO+","+FIELD_GRNTEE_BEN_CNTRCT_REF_NO);
	    if('ILC_AM'==reqType){
	    	hideControls(NEW_TRN_AMT+','+NEW_EXP_DATE);
	    }
	    else if('ILC_UM'==reqType){		// RR
	    	showControls(FCUBS_PUR_OF_MSG);
	    	enableFieldOnDemand(FCUBS_PUR_OF_MSG);
	    	setValues({[FCUBS_PUR_OF_MSG]:'Issue'},true);
	    }
	    
	    //ATP-383 13-FEB-24 JAMSHED
	    
	    if(reqCat == 'ILC' && reqType == 'ILC_NI'){
			showFieldsPPM_ILC_NI();
		}
	    //ATP-383 13-FEB-24 JAMSHED ends
	} else if(reqCat == 'SG'){  // Shipping_gtee_28
		hideControls(GRNT_FRAMES_HIDE+","+FRM_GRTEE_LC_COMMON);
		disableFieldOnDemand(DISABLE_SG_PPM_FIELDS);
		if(reqType == 'SG_NILC' || reqType == 'SG_NIC'){
			setStyle('REF_ICG_RM', PROPERTY_NAME.VISIBLE, 'false');  // Shipping_Gtee_36
		}
    } else if(reqCat == 'SCF'){ //ADDED  26-10-2023 FOR SCF CHANGES  ATP -185,186,187
		if(reqType == 'PD' || 'PDD' == reqType){
			if('TSLM Front End'== getValue(PROCESS_TYPE)){
			  setValues({[TSLM_COMPANY_TYPE]: 'B'}, true);
			}
			setValueSCF_PD();
			enableDisableFieldsPPM_SCF_PD();
			disableFieldOnDemand(PPM_SCF_PD_BAU_DISABLE); //ATP 98
			showControls(PPM_SCF_PD_BAU_SHOW);
			hideControls(PPM_SCF_PD_BAU_FRAMES_HIDE);
			showControls(PPM_SCF_PD_BAU_FRAMES_SHOW);
			setTabStyle("tab1",6, "visible", "false");
			document.getElementById('INF_LOAN_CURR_label').innerText = 'Discount Currency'; //ATP 22
			
		}
	} 
}	

function handlePPMEvent(object, event){
	console.log("Event: " + event.target.id + ", " + event.type);
	if (event.type == EVENT_TYPE.CLICK) {
		clickPPMEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changePPMEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusPPMEvent(event);
	} else if (event.type == EVENT_TYPE.GOTFOCUS) {
		gotFocusPPMEvent(event);
	}
}
function lostFocusPPMEvent(event){

	var workstepName = getWorkItemData('activityName');
	var wi_name      = getWorkItemData('processInstanceId');
	var requestType  = getValue(REQUEST_TYPE);
	var requestCat   = getValue(REQUEST_CATEGORY);

	if(event.target.id == NEW_TRN_AMT){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == QVAR_CHRGS_LEGAL_AMT){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == QVAR_CHRGS_PENALTY_AMT){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == QVAR_CHRGS_OVERDRAWN_AMT){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == QVAR_CHRGS_OTHER_AMT){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DATE_INF_BASE_DOC){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DATE_INF_NEW_MATURITY){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == EXP_DATE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DATE_INF_NEW_MATURITY){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id.substring(0,22) == 'Q_Amendment_Data_USER_' ){
		var PTColumn='Q_Amendment_Data_PT_'+event.target.id.substring(22);
		var FCUBSColumn='Q_Amendment_Data_FC_'+event.target.id.substring(22);
		var value;
		var amendExpiryType= getValue(Q_AMENDMENT_DATA_USER_EXPIRY_TYPE);
		
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
		
	 if(event.target.id=='Q_Amendment_Data_USER_EXPIRY_DATE'){
			console.log('value='+getValue('Q_Amendment_Data_FIN_EXPIRY_DATE'));
		setValues({[NEW_EXP_DATE]: getValue('Q_Amendment_Data_FIN_EXPIRY_DATE')}, true);
		}
	 else if(event.target.id=='Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE'){
		 executeServerEvent(event.target.id, event.type, '', false);
	 }
	 if((requestType =='AM' || requestType =='SBLC_AM' || requestType =='ELC_SLCAA' || requestType =='GAA') 
			 && (amendExpiryType =='FD' || amendExpiryType =='COND')){
	  if(event.target.id=='Q_Amendment_Data_USER_EXPIRY_DATE')
		{	
		setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
		setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);	
		}
 	}
	 else if((requestType =='AM' || requestType =='SBLC_AM' || requestType =='ELC_SLCAA' || requestType =='GAA') 
			 && (amendExpiryType =='COND')
		 	&&event.target.id=='Q_Amendment_Data_USER_EXPIRY_DATE'){	
		 	setValues({[Q_AMENDMENT_DATA_FIN_EXPIRY_COND]: getValue(Q_AMENDMENT_DATA_USER_EXPIRY_COND)}, true);
		 }
	 
	}
}
function gotFocusPPMEvent(event){
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);

	if(event.target.id == AMEND_TYPE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DATE_INF_BASE_DOC){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DATE_INF_MATURITY){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DEC_CODE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DATE_INF_NEW_MATURITY){
		executeServerEvent(event.target.id, event.type, '', false);
	}

}

function changePPMEvent(event) {
	var workstepName = getWorkItemData('activityName');
	var wi_name = getWorkItemData('processInstanceId');
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);


	if (event.target.id == COMBOX_LMTCRE_APP_AVL) {
		enableDisableFieldsLmtCre();
	}
	//Traydstream |reyaz|atp-518|07-10-2024| Start
	else if(workstepName==WORKSTEP.TRAYDSTREAM && event.target.id == 'DEC_CODE'){
		if('MAPP' == getValue(DEC_CODE)){
		    executeServerEvent(event.target.id, event.type, '', false);
	    }
	}else if(workstepName==WORKSTEP.TRAYDSTREAM && event.target.id == COMBOX_REJ_RESN){
		if('MAPP' == getValue(DEC_CODE) && getValue(COMBOX_REJ_RESN)!=null){
           enableFieldOnDemand(BUTTON_SUBMIT);
	    }
	}
	//Traydstream |reyaz|atp-518|07-10-2024| End
	else if(event.target.id == COMBOX_DOC_REV_SUCC){
		enableDisableFieldsDocRev();
	}else if(event.target.id == COMBOX_TXT_REQ_APP){
		enableDisableFieldsTextVet();
	}else if(event.target.id == DUP_CHK_CONFIRMATION){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_DOCREV_REF_TO){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_LMTCRE_REF_TO){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_REQ_SIGN_MAN || event.target.id == COMBOX_SUFF_BAL_AVL){
		enableDisableFieldsSignAcc();
	}else if(event.target.id == COMBOX_TXTVETT_REF_TO){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_SIGACC_REF_TO){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id ==TFO_BRANCH_CITY){
		setValues({[TFO_ASSIGNED_CENTER]: getValue(TFO_BRANCH_CITY)}, true);
	}else if(event.target.id == IS_ACC_VALID ){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_BILL_CUST_HLDING_ACC_US ){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == TRN_TENOR ){
		enableDisableFieldsTrnTenor();
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == SOURCE_CHANNEL || event.target.id == RELATIONSHIP_TYPE){
		var workstepName = getWorkItemData('activityName');
		console.log('Workstep: ' + workstepName);
		setDefaultValue(workstepName);
		executeServerEvent(event.target.id, event.type, '', true);
		if(requestCat == 'IFA' || requestCat == 'IFP' || requestCat == 'IFS'){
			if(getValue(RELATIONSHIP_TYPE) == 'I' ){
				if(requestCat == 'IFA'){
					setValues({[COMBOX_PRODUCT_TYPE]: 'TF03'}, true);
				}
			}else{
				if(requestCat == 'IFA'){		
					setValues({[COMBOX_PRODUCT_TYPE]: 'L092'}, true);
				}
			}
		}
	}else if(event.target.id == ACCOUNT_NUMBER){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_INF_CHARGE_ACC_NUM){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == AMEND_TYPE ){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == DEC_CODE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == COMBOX_REJ_RESN ){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id ==FIELD_CPD_COUNTER_PARTY_NAME_ID){
		var d = new Date();
		var date =  d.getFullYear()+'-'+d.getMonth()+'-'+d.getDate();
		var time = d.getHours() + ':' + d.getMinutes() + ':' +d.getSeconds();

		setValues({[FIELD_CPD_ACTIVITY_NAME_ID]: workstepName}, true);

		if("LD" == requestType){
			setValues({[FIELD_CPD_TRANSACTION_ID]: '' }, true);
		}else if("AM"==requestType){
			setValues({[FIELD_CPD_TRANSACTION_ID]: getValue(TRANSACTION_REFERENCE)}, true);
		}
	} else if(event.target.id ==COMBOX_INF_SETTLEMENT_ACC_NUM){
		executeServerEvent(event.target.id, event.type, '', false);	
	}else if(event.target.id ==COMBOX_INF_CREDIT_ACC_NUM){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id ==COMBO_BC_CALL_DONE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id==COMBOX_INF_AMEND_TYPE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id.substring(0,22) == 'Q_Amendment_Data_USER_' ){
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
			 && amendExpiryType =='FD'){	// RR for user amendment frame
			
			enableFieldOnDemand('Q_Amendment_Data_USER_EXPIRY_DATE');
			enableFieldOnDemand('Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE');
			setValues({[Q_AMENDMENT_DATA_USER_EXPIRY_COND]: ''},true);// mansi
																		// new
			setValues({[Q_AMENDMENT_DATA_FIN_EXPIRY_COND]: ''},true);// mansi
																		// ne
			setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: ''}, true);
			setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']:''}, true);
	    	setValues({['Q_Amendment_Data_USER_EXPIRY_DATE']: ''}, true);
	    	// setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: ''}, true);
			disableFieldOnDemand(Q_AMENDMENT_DATA_USER_EXPIRY_COND);
			setValues({['Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
			setValues({['Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
			
			if(amendExpiryType =='FD'){// santhosh
				if(getValue('Q_Amendment_Data_USER_EXPIRY_DATE') != '' && getValue('Q_Amendment_Data_USER_EXPIRY_DATE') != null){
				setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: getValue('Q_Amendment_Data_USER_EXPIRY_DATE')}, true);
				}else if(getValue(Q_Amendment_Data_PT_EXPIRY_DATE) != '' && getValue(Q_Amendment_Data_PT_EXPIRY_DATE) != null){
				setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: getValue('Q_Amendment_Data_PT_EXPIRY_DATE')}, true);
		        }else{
				setValues({['Q_Amendment_Data_FIN_EXPIRY_DATE']: getValue('Q_Amendment_Data_FC_EXPIRY_DATE')}, true);
		        }
			}
		}
		else if(requestType =='SBLC_AM' || requestType =='AM' || requestType =='ELC_SLCAA' || requestType =='GAA'){
			var userExpirydate =  getValue('Q_Amendment_Data_USER_EXPIRY_TYPE');
	        var ptExpirydate =  getValue('Q_Amendment_Data_PT_EXPIRY_TYPE');
			if(ptExpirydate == 'OE' || userExpirydate == 'OE'){
				
	        	 setValues({[Q_AMENDMENT_DATA_USER_EXPIRY_COND]: ''},true);// mansi
																			// new
	        	 disableFieldOnDemand(Q_AMENDMENT_DATA_USER_EXPIRY_COND);
				 setValues({[Q_AMENDMENT_DATA_FIN_EXPIRY_COND]: ''},true);// mansi
																			// new
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
	}else if(event.target.id==TSLM_FUNDING_REQUIRED){	// BY MOKSH
		var toggleValue = getValue(TSLM_FUNDING_REQUIRED);
		if(toggleValue == "1"){
			enableFieldOnDemand(TSLM_ADDITIONAL_LOAN_AMOUNT);
		}else if(toggleValue == "2"){
			disableFieldOnDemand(TSLM_ADDITIONAL_LOAN_AMOUNT);
			setValues({[TSLM_ADDITIONAL_LOAN_AMOUNT]: '0'}, true);
		}
	}else if(event.target.id==PROCESSING_SYSTEM){  // BY MOKSH EDITED BY KISHAN
													// TSLMS
		fieldEnableDisableProSys();
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id=='STANDALONE_LOAN'){
		enaDisInvoiceNoStandaLone();
		if(getValue('STANDALONE_LOAN') == '1' && getGridRowCount('Q_TSLM_Invoice_No_SA_Loan') >0){
			showControls('TSLM_DEDUPE_LABEL,BTN_TSLM_INVOICE_CHK_CONFIRM,TSLM_INVOICE_CHK_CONFIRM');
		}else if(getValue('STANDALONE_LOAN') == '2' ){
			hideControls('TSLM_DEDUPE_LABEL,BTN_TSLM_INVOICE_CHK_CONFIRM,TSLM_INVOICE_CHK_CONFIRM');
		}
	}else if(event.target.id == TSLM_INVOICE_CHK_CONFIRM){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == CUSTOMER_INSTRUCTION){  // ADDED BY KISHAN
		if(getValue(CUSTOMER_INSTRUCTION) == 'MA' || getValue(CUSTOMER_INSTRUCTION)  == 'CF'){ // need
																								// to
																								// make
																								// mandetory
	 		setStyle(REQ_CONFIRM_PARTY,PROPERTY_NAME.DISABLE,'false');
	 	}else{
	 		setStyle(REQ_CONFIRM_PARTY,PROPERTY_NAME.DISABLE,'true');
	 	}
	}else if(event.target.id == COMBOX_PRODUCT_TYPE){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == 'UTC_REQUIRED'){
		console.log(" UTC_REQUIRED"+UTC_REQUIRED);
		if((getValue('UTC_REQUIRED') == 'No')&& (getValue('UTC_REQUIRED_BRMS') == 'Yes') )
		{ 
			enableFieldOnDemand('UTC_JSTIFICATION_REQUIRED', true);
		}
		if(getValue('UTC_REQUIRED') == 'Yes')
		{ 
			setValue('UTC_JSTIFICATION_REQUIRED','');
			disableFieldOnDemand('UTC_JSTIFICATION_REQUIRED');
		}
	}
}

function icgRmCheck( event){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var bIcgFlg=true;
	var bFlgEnable=false;

	if(("EC" ==reqCat && "EC_CA"==reqType)
			|| ("ELC"==reqCat&& "ELC_LCC"==reqType)
			|| ("ELCB"==reqCat && "ELCB_DISC"==reqType)){


		var iRowCount =getGridRowCount(LISTVIEW_FINAL_DECISION);
		console.log(" icgRmCheck RowCount "+iRowCount);
		for(var count=1;count<iRowCount;count++){					
			if("RM"==getValueFromColumnName(LISTVIEW_FINAL_DECISION,count,"Referred To")){

				console.log("Found RM");
				if("click"==event){
					bIcgFlg=validateField(COMBOX_REF_ICG_RM,"Please select Referred To ICG RM");
				}					
				bFlgEnable=true;					
				break;
			}
		}
		if(bFlgEnable){

			enableFieldOnDemand(COMBOX_REF_ICG_RM);

		}else{
			setValues({[COMBOX_REF_ICG_RM]: '' }, true);
			(COMBOX_REF_ICG_RM);
		}				
	}else{
		setValues({[COMBOX_REF_ICG_RM]: '' }, true);
		(COMBOX_REF_ICG_RM);
	}
	return bIcgFlg;
}

function setEmailFlag(){
	console.log("***************Inside setEmailFlag******************");

	var iRowCount =getGridRowCount(LISTVIEW_FINAL_DECISION);

	var checkBoxSendEmail=getValue(CHKBX_SEND_MAIL);
	var manuallyTicket=getValue(MANUALLY_TICKED_MAIL);
	console.log("iRowCount"+iRowCount);
	console.log("MANUALLY_TICKED_MAIL="+getValue(MANUALLY_TICKED_MAIL));
	if(null==manuallyTicket||"null"==manuallyTicket|| ""==manuallyTicket)
	{ 
		if (iRowCount>1 && !checkBoxSendEmail)
		{
			console.log("val--"+getValue(CHKBX_SEND_MAIL));
			enableFieldOnDemand(CHKBX_SEND_MAIL, true);
			setValues({[CHKBX_SEND_MAIL]: 'true' }, true);

		}
	}

}


function postServerEventHandlerPPM(controlName, eventType, responseData) {
	var jsonData = handleTFOResponse(responseData);
	var workstepName = getWorkItemData('activityName');
	console.log("Control Name: "+ controlName +", Event Type: "+ eventType);
	console.log("Response Data:");
	console.log(jsonData);

	switch (eventType) {
	case EVENT_TYPE.LOAD: 
		var reqType = getValue(REQUEST_TYPE);
		var reqCat = getValue(REQUEST_CATEGORY);
		 if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {
			setTabStyle("tab1",8, "visible", "true");
		} if(!(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType))) {
			setTabStyle("tab1",8, "visible", "false");
		}if(getValue('UTC_REQUIRED_BRMS') == 'Yes'){
			setValue('UTC_REQUIRED','Yes');
		}if(getValue('UTC_REQUIRED_BRMS') == 'No'){
			setValue('UTC_REQUIRED','No');
		}
		//ATP-481 --JAMSHED --11-JUN-2024 START
		/*if(('IFA' == reqCat) && ('LD' == reqType)){
			setValue('UTC_REQUIRED','No');	
			setValue('UTC_JSTIFICATION_REQUIRED','UTC Not Applicable for IFA Disbursement');
		}*/
		//ATP-481 --JAMSHED --11-JUN-2024 END
		if(getValue(REQUEST_CATEGORY) == 'SCF'){      // ATP 199 , ATP - 201   Date 31 -10-2023 ADDED BY REYAZ
			if(reqType == 'PD'|| 'PDD' == reqType){
				setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.VISIBLE, 'true');
				if('TSLM Front End'== getValue(PROCESS_TYPE)){
					setValues({['BILL_CUST_HLDING_ACC_US']: '2'}, true);
					setValues({['LMTCRE_APP_AVL_PPM']: '3'}, true);
				    setValues({['DOC_REV_SUCC_PPM']: '3'}, true);
					hideControls(PPM_SCF_PD_TSLM_FRONTEND_HIDE);
					hideControls(PPM_SCF_PD_TSLM_FRONTEND_FRAMES_HIDE);
					disableFieldOnDemand('BRN_CODE'); //ATP-409 SHIVANSHU
				}
			}
        }if(reqType =='STL'){
		   setValueSTL(); 
		}if(FORM==controlName){
			disablePTFields();
			showInvoiceTab();
			setAmendFrameData();// mansi
			
			if('openRepairJSP'==jsonData.message){
				var callRequestType = jsonData.message;
				setTabStyle("tab1",10, "visible", "true");
				var urlDoc = document.URL;
				var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
				document.getElementById('sheet14_link').textContent = 'Workitem Creation';
				var jspURL=sLocationOrigin+"/TFO/CustomFolder/Repair_Integration_Calls.jsp?WI_NAME="
				+getValue('WI_NAME')+"&callRequestType="+callRequestType;
				document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
				disableFieldOnDemand(BUTTON_SUBMIT);
				selectSheet("tab1",10);
			}
			
		}
		if(getValue(REQUEST_CATEGORY) == 'IFP') {
			enableFieldOnDemand(ADCB_BILL_REF_NO);
		} else if(getValue(REQUEST_CATEGORY) == 'IFA'){ // BY KISHAN TSLM
			setProductCodeTSLM();
		} 
		break;
	case EVENT_TYPE.CLICK:
		var prvWS=getValue('PREV_WS');
		console.log("prvWS=  "+prvWS);		
		var doc1=getValue('IS_GETDOCUMENT_UTC_DONE');
		console.log("doc1=  "+doc1);	
		var doc2=getValue('IS_UTC_UPDATE_DOC_CALL');
		console.log("doc2=  "+doc2);	
		if (!jsonData.success){
			var arr = jsonData.message.split('###');
			showMessage(arr[0],arr[1], 'error');
			return false;
		}
		  if(BUTTON_ADD_DOC_CHKLIST==controlName){
			callCustomJSP(event.target.id,LISTVIEW_DOC_CHKLIST,"DocRvw"); // just
																			// for
																			// testing
																			// hided.
			// showChecklistTab_CountFields();//added for testing
		}else if(BUTTON_LIMIT_ADD_CKLIST==controlName){
			callCustomJSP(event.target.id,LISTVIEW_LIMIT_CKLIST,"LmtCrdt");
		}else if((controlName == BUTTON_SUBMIT ) && !jsonData.message == ''&& 'OpenLinkWIJSP'==jsonData.message){
		    var callRequestType = jsonData.message;
		    setTabStyle("tab1",10, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			document.getElementById('sheet14_link').textContent = 'Workitem Creation';
            var jspURL=sLocationOrigin+"/TFO/CustomFolder/FCUBS_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&callRequestType="+callRequestType;
			document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
			disableFieldOnDemand(BUTTON_SUBMIT);
			selectSheet("tab1",10);
		}else if(controlName == BUTTON_SUBMIT && !jsonData.message == ''&& 'updateDocStatus'==jsonData.message){
				UTCPPMUpdateDocStatusJsp();
			}
// else if(controlName == BUTTON_SUBMIT){
// TSLMPPMAutoTriggerJsp();
// }
		else if(BUTTON_SUBMIT==controlName){
			if((('IFP' == getValue(REQUEST_CATEGORY) || 'IFS' == getValue(REQUEST_CATEGORY) || 'IFA' == getValue(REQUEST_CATEGORY)) && ('LD' == getValue(REQUEST_TYPE) || 'IFA_CTP' == getValue(REQUEST_TYPE))) 
					&& (getValue('UTC_REQUIRED_BRMS')=='')&&'TSLM Front End'== getValue(PROCESS_TYPE)){ 
				//	if(getValue("UTC_BRMS_RETRY_COUNTER") < 3 || getValue("UTC_BRMS_RETRY_COUNTER") == ''  ){
					//	setValues({["UTC_BRMS_RETRY_COUNTER"]:(1+parseInt(getValue("UTC_BRMS_RETRY_COUNTER")))}, true);
						setTabStyle('tab1',12, "visible", "true");
						selectSheet('tab1', 12);
						var urlDoc = document.URL;
						var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
					//	document.getElementById('sheet18_link').textContent = 'UTC BRMS';
						var jspURL=sLocationOrigin+"/TFO/CustomFolder/TSLM_UTC_BRMS_Calls.jsp?WI_NAME="+getValue('WI_NAME');
						document.getElementById('JSP_FRAME_UTC_PPM').src=jspURL;

			}
			if((('IFP' == getValue(REQUEST_CATEGORY) || 'IFS' == getValue(REQUEST_CATEGORY) || 'IFA' == getValue(REQUEST_CATEGORY)) && ('LD' == getValue(REQUEST_TYPE) || 'IFA_CTP' == getValue(REQUEST_TYPE))&&!validateField('PAST_DUE_LIABILITY', 'PLEASE SELECT PAST DUE LIABILITY')) ){
					return false;	
				
			}
			/*if((('IFP' == getValue(REQUEST_CATEGORY) || 'IFS' == getValue(REQUEST_CATEGORY) || 'IFA' == getValue(REQUEST_CATEGORY)) && ('LD' == getValue(REQUEST_TYPE) || 'IFA_CTP' == getValue(REQUEST_TYPE))) 
					&& (getValue('UTC_REQUIRED_BRMS')=='Yes')&& (getValue('IS_GETDOCUMENT_UTC_DONE')=='') && 'TSLM Front End'== getValue(PROCESS_TYPE) && 
				(getValue('DEC_CODE')=='APP')){ 
				//	if(getValue("UTC_BRMS_RETRY_COUNTER") < 3 || getValue("UTC_BRMS_RETRY_COUNTER") == ''  ){
					//	setValues({["UTC_BRMS_RETRY_COUNTER"]:(1+parseInt(getValue("UTC_BRMS_RETRY_COUNTER")))}, true);
						setTabStyle('tab1',12, "visible", "true");
						selectSheet('tab1', 12);
						var urlDoc = document.URL;
						var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
					//	document.getElementById('sheet18_link').textContent = 'UTC BRMS';
						var jspURL=sLocationOrigin+"/TFO/CustomFolder/TSLM_SUBMIT_INVOICE_2.jsp?WI_NAME="+getValue('WI_NAME');
						document.getElementById('JSP_FRAME_UTC_PPM').src=jspURL;

			}*/
				
			else if (jsonData.success){
				saveWorkItem();
				executeServerEvent('PUSHMSGTSLM', 'PUSHMSGTSLM', '', false);
				completeWorkItem();
			} 
			else {
				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
				if(!jsonData.message == ''){
					console.log('showing document missing error');
					showMessage('', jsonData.message, 'error');
				}
			}
		RemoveIndicator("Application");
			document.getElementById("fade").style.display="none";
		}
	else if (controlName == PM_TAB_CLICK) {
			disablePTFields();
			// ecFramePT();
		} else if (controlName == SAVE_AND_NEXT) {
			disablePTFields();
			// ecFramePT();
		} else if(BTN_FETCH_TSLM_CID_DETAILS == controlName){
			saveWorkItem();
		}else if(BUTTON_RETRY_ADD_DOC == controlName){
			window.parent.refreshWorkitem();
		} //ATP-134 REYAZ 10-05-2024 START
		else if(FETCH_DETAILS == controlName){
			enableFieldOnDemand('INF_CHARGE_ACC_NUM,INF_SETTLEMENT_ACC_NUM');
		} // END
		break;
	case EVENT_TYPE.CHANGE:

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

function addRowPostHook(tableId){
	if(tableId == 'QVAR_utc_details'){
		saveWorkItem();
	}
	if(tableId == 'SIGN_REFERRAL_ID'||tableId == 'Doc_Review_RefID'||tableId == 'LIMIT_REFERRAL_ID'){
		sufficentBal(tableId);
	}
}

function UTCPPMUpdateDocStatusJsp(){
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType)) {

		if(getValue('IS_UTC_UPDATE_DOC_CALL') != 'Y'){
			var wd_uid=getWorkItemData("sessionId");
			setTabStyle("tab1",12, "visible", "true");
			var urlDoc = document.URL;
			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
			var jspURL=sLocationOrigin+"/TFO/CustomFolder/LLI_Integration_Calls.jsp?WI_NAME="+getValue('WI_NAME')+"&VesselName=updateDocumentStatusUTC&session="+wd_uid+"&WD_UID="+wd_uid;
			document.getElementById('JSP_FRAME_UTC_PPM').src=jspURL;
			selectSheet("tab1",12);	
			// document.getElementById(BUTTON_SF_CLOSE).style.display="none";
		}
	} 
}   
//function TSLMPPMAutoTriggerJsp(){// krishna
//    var processType = getValue(PROCESS_TYPE);
//	if('FinTrade' == processType || 'TSLM Front End' == processType) {
//			setTabStyle("tab1",12, "visible", "true");
//			var urlDoc = document.URL;
//			var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
//			var jspURL=sLocationOrigin+"/TFO/CustomFolder/UTC_BRMS_Calls.jsp?WI_NAME="+getValue('WI_NAME');
//			document.getElementById('JSP_FRAME_UTC_PPM').src=jspURL;
//			selectSheet("tab1",12);	
//		else if (jsonData.success){
//				saveWorkItem();
//			} else {
//				setStyle(BUTTON_SUBMIT, PROPERTY_NAME.DISABLE, 'false');
//				if(!jsonData.message == ''){
//					console.log('showing document missing error');
//					showMessage('', jsonData.message, 'error');
//				}
//			}
//		RemoveIndicator("Application");
//			document.getElementById("fade").style.display="none";
//		
//	} 
//}

function customListViewValidationPPM(controlId,flag){
	if(LISTVIEW_REF_DocRvw == controlId){
	return toCheckGridDataOnPPM(flag);
	}
	if((flag == 'A') || ( flag == 'O')) {
		if(LISTVIEW_CPD_DETAILS == controlId){
			var listCount = getGridRowCount(controlId);
			if(listCount >50){
				showMessage("", "Max 50 rows allowed for counter party details'", 'error');
				return false;
			}
			else{return true; }
		}else if(controlId == 'Q_TSLm_Counter_Dets'){
			var listCount = getGridRowCount(controlId);
			if(listCount >50){
				showMessage("", "Max 50 rows allowed for counter party details'", 'error');
				return false;
			}else{
				return true; 
			}
		}else if(controlId == 'Q_TSLM_Invoice_No_SA_Loan'){
			var listCount = getGridRowCount(controlId);
			if(listCount >50){
				showMessage("", "Max 50 rows allowed for counter party details'", 'error');
				return false;
			}else{
				return true; 
			}
		}else if(controlId == 'QVAR_utc_details'){
			var listCount1 = getGridRowCount(controlId);
			if(listCount1 >200){
				showMessage("", "Max 200 rows allowed for invoice details'", 'error');
				return false;
			}else{
				return true; 
			}
		}
	} else if( flag == 'M'){
		if(LISTVIEW_FINAL_DECISION == controlId){
			var inputData=getGridRowCount(LISTVIEW_FINAL_DECISION);
			var response= executeServerEvent(FINAL_DECISION_GRID_ADDITIONAL_MAIL,EVENT_TYPE.LOSTFOCUS,inputData, true);
			var jsonData = handleTFOResponse(response);
			if (!jsonData.success){
				return false;
			}else{return true; }
		}
	}
	return true;
}


function enableDisableFieldsLmtCre(){
	var limitCredit=getValue(COMBOX_LMTCRE_APP_AVL);

	if('2' == limitCredit){
		setStyle("add_lvwLmtCrdt",PROPERTY_NAME.DISABLE,'false');


	} else {
		setStyle("add_lvwLmtCrdt",PROPERTY_NAME.DISABLE,'true');

	}

}

function enableDisableFieldsTrnTenor(){

	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	var trnTenor = getValue(TRN_TENOR);
	if(reqCat == "GRNT" || reqCat == "SBLC" || reqCat == "ELC" && (reqType == 'ELC_SLCA' 
		|| reqType == 'ELC_SLCAA' || reqType == 'ELC_SER' ||reqType == 'ELC_SLC')){// added
																					// by
																					// mansi

		if ( trnTenor == "OE") {					
			(FIELD_GRNTEE_EXP_DATE);
			setValues({[EXPIRY_COND]: ''}, true);// mansi
			setValues({[FIELD_GRNTEE_EXP_DATE]: ''}, true);
			setValues({[EXP_DATE]: ''}, true);
		} else if (trnTenor == "FD") {	
			setValues({[EXPIRY_COND]: ''}, true);// mansi
			enableFieldOnDemand(FIELD_GRNTEE_EXP_DATE);
		} /*
			 * else { (FIELD_GRNTEE_EXP_DATE);
			 * setValues({[FIELD_GRNTEE_EXP_DATE]: ''}, true);
			 * setValues({[EXPIRY_COND]: ''}, true); }
			 */


	}
	if( reqType == "NI" || reqType == "SBLC_NI" || reqType == "ELC_SLCA"){// added
																			// by
																			// mansi
		if ( trnTenor == "OE") {					
			(EXP_DATE);
			setValues({[EXPIRY_COND]: ''}, true);// mansi
			setValues({[EXP_DATE]: ''}, true);
		} else if (trnTenor == "FD") {	
			setValues({[EXPIRY_COND]: ''}, true);// mansi
			enableFieldOnDemand(EXP_DATE);
		} /*
			 * else { (EXP_DATE); setValues({[EXP_DATE]: ''}, true); }
			 */


	}else if (reqType == "ILC_NI") {
		enableFieldOnDemand(EXP_DATE);
	}else if (reqType == "ILC_UM") {
		if(trnTenor == "OE"){
			disableFieldOnDemand(EXP_DATE);
		}
		else{
			enableFieldOnDemand(EXP_DATE);
		}
	}else if (reqType == "ELC_LCA") {
		enableFieldOnDemand(EXP_DATE);
	}
	else {

		(EXP_DATE);
	}

}

function  getAmndCode( desc) {
	var result = "";
	if (!strAmendment.isEmpty() && !desc.isEmpty()) {
		var tempArr = strAmendment.split("#~#");
		for (var counter = 0; counter < tempArr.length; counter++) {
			if (desc == tempArr[counter].split("###")[0]) {
				result = tempArr[counter].split("###")[1];

				break;
			}
		}
	}

	return result;
}



function enableDisableFieldsChargeAccNo(){
	var custHold =  getValue(IS_ACC_VALID);
	if ('1' == custHold ){

		enableFieldOnDemand(ACCOUNT_NUMBER);

	}else{
		disableFieldOnDemand(ACCOUNT_NUMBER);
	}
}	
function enableDisableFieldBillChargeAccNo(){
	var custHold =  getValue(COMBOX_BILL_CUST_HLDING_ACC_US);
	if ('1' == custHold ){

		enableFieldOnDemand(COMBOX_INF_CHARGE_ACC_NUM);
	}else{
		disableFieldOnDemand(COMBOX_INF_CHARGE_ACC_NUM);
	}

}

function buttonRmksEnableDisable(refTo, escRmks,btnAdd, btnDel){
	if(''!= refTo ){
		enableFieldOnDemand(escRmks,btnAdd, btnDel);
	}else{
		(escRmks,btnAdd, btnDel);
	}

}

function enableDisableFieldsDocRev(){
	var DocumentReview = getValue(COMBOX_DOC_REV_SUCC);

	if(DocumentReview == '2'){
		setStyle("add_lvwDocRvw",PROPERTY_NAME.DISABLE,'false');

	} else {

		setStyle("add_lvwDocRvw",PROPERTY_NAME.DISABLE,'true');

	}
}
function enableDisableFieldsSignAcc(){
	var reqSign = getValue(COMBOX_REQ_SIGN_MAN);
	var suffBal = getValue(COMBOX_SUFF_BAL_AVL);
	if(reqSign == '2' || suffBal == '2'){
		setStyle("add_lvwSignAcc",PROPERTY_NAME.DISABLE,'false');
	}


	else{
		setStyle("add_lvwSignAcc",PROPERTY_NAME.DISABLE,'true');
	}
}

function enableDisableFieldsTextVet(){
	var textVet = getValue(COMBOX_TXT_REQ_APP);
	var referTo = getValue(COMBOX_TXTVETT_REF_TO);
	if('1' == textVet){
		setStyle("add_lvwTxtVet",PROPERTY_NAME.DISABLE,'false');

	}
	else{
		setStyle("add_lvwTxtVet",PROPERTY_NAME.DISABLE,'true');
	}
}


function  clickPPMEvent(event)  {
	var wi_name = getWorkItemData('processInstanceId');
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);
	var processing_system = getValue('PROCESSING_SYSTEM');
	var workstepName = getWorkItemData('activityName');
	if(event.target.id == ACCOUNT_DETAILS){
		console.log('calling execute for ACCOUNT_DETAILS');
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == 'BTN_INVOICE_BROWSE'){
		var urlDoc = document.URL;
		var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
		var jspURL=sLocationOrigin+"/webdesktop/CustomFolder/TFO/UploadFile.jsp?WI_NAME="+wi_name;
		document.getElementById('IntegrationUploadInvoice').src=jspURL;	
		
	}else if(workstepName==WORKSTEP.TRAYDSTREAM && event.target.id ==BUTTON_SUBMIT){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == FETCH_DETAILS){
		console.log('calling execute for ACCOUNT_DETAILS');
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == BUTTON_ADD_SIGN && validateField(COMBOX_SIGACC_REF_TO, "Please Select Refer To") 
			&& validateField(FIELD_SIGACC_EXC_RMKS,"Please Fill Exception Remarks")){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == BUTTON_ADD_DOC && validateField(COMBOX_DOCREV_REF_TO, "Please Select Refer To") 
			&& validateField(FIELD_DOCREV_EXC_RMKS,"Please Fill Exception Remarks")){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == BUTTON_ADD_TXT_VETT && validateField(COMBOX_TXTVETT_REF_TO, "Please Select Refer To") 
			&& validateField(FIELD_TXTVETT_EXC_RMKS,"Please Fill Exception Remarks")){

		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == BUTTON_LIMIT_CREDIT_ADD && validateField("LMTCRE_REF_TO", "Please Select Refer To") 
			&& validateField(FIELD_LMTCRE_EXC_RMKS,"Please Fill Exception Remarks")){
		executeServerEvent(event.target.id, event.type, '', false);

	} else if(event.target.id == BUTTON_FETCH_ACC_DETAIL ){
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id == BUTTON_SUBMIT ) {
		if(requestCat != 'IFCPC'){
		if(	!validateField("BRANCH_CITY","Please select Issuing Center")){ 
	        return false;
			}
	    if(!validateField(TFO_ASSIGNED_CENTER,"Please select Processing Center")){
			return false;
		    }
		}
		

		var reqCat = getValue(REQUEST_CATEGORY);
		var reqType = getValue(REQUEST_TYPE);
		if(('TSLM Front End'== getValue(PROCESS_TYPE))&&(('IFP' == reqCat && 'LD' == reqType)|| ('IFA' == reqCat && 'IFA_CTP' == reqType))){
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
	    //  ATP-feature/scfmvp2.5 shahbaz 18-07-2024 start
	    if(validateTslmFrontedRequestCat()){    
		 const regex =/[^A-Za-z0-9\s.,]/;
		 var remarksPM=getValue('REMARKS');
		 if(regex.test(remarksPM)){
			showMessage('REMARKS', 'Special Characters are not allowed in Remarks', 'error');
		    return false;
		}
	}
	//  ATP-feature/scfmvp2.5 shahbaz 18-07-2024 end
		
		//Added by Shivanshu
		if(('BAU'== getValue(PROCESS_TYPE)) || ('PT'== getValue(PROCESS_TYPE))){
			if (('ELCB_AM' == reqType) || ('EC_AM' == reqType)){
				if(getValue('IS_REMOTE_PRESENTATION') == undefined){
					showMessage('IS_REMOTE_PRESENTATION', 'Remote Presentation cannot be blank', 'error');
					return false;	
				}else if(getValue('BILL_RVSD_DOC_AVL') == '') {
					showMessage('BILL_RVSD_DOC_AVL', 'Revised Document Available  cannot be blank', 'error');
					return false;	
					}
			} else if ('BAU'== getValue(PROCESS_TYPE) && (('ELCB_BL' == reqType) || ('EC_BL' == reqType))){
				if(getValue('IS_REMOTE_PRESENTATION') == undefined){
					showMessage('IS_REMOTE_PRESENTATION', 'Remote Presentation cannot be blank', 'error');
					return false;
					}
		  }
		}
		
		var res = '-1';
		if(getValue('DEC_CODE')=='APP' && getValue('PAST_DUE_LIABILITY')=='Yes'){
			res=prompt("Customer has Past dues at the Liability ID level. Ensure that either the Past dues are cleared or necessary approvals are available to process the transaction.\nPlease select any one option\n1. Past Dues Cleared\n2. Approval Received\n3. Cancel","");
			if(res != '1' && res != '2' && res != '3' ){  //ATP-463 --JAMSHED 10-JUN-2024 
			   alert("Please Enter Valid Option.");
			   return false;
			}
			if(res == null || res=='3'){  //ATP-463 --JAMSHED 10-JUN-2024 
				return false;
			}
		}
		
		// EDITED BY MOKSH
		var selectedRows = getSelectedRowsIndexes(LISTVIEW_TSLM_CPD); 
		var rowCount  = getGridRowCount(LISTVIEW_TSLM_CPD);
		 if(selectedRows[0] == undefined
				&& (requestType == 'LD'||requestType == 'IFA_CTP') 
				&& processing_system == 'T' && rowCount >0
			&& (requestCat == 'IFA' || requestCat == 'IFS' ||requestCat == 'IFP')){
			showMessage(LISTVIEW_TSLM_CPD, 'Please select a row in TSLM Counter Party Details', 'error');
		}else{
			saveWorkItem();
			// Added by Shivanshu ATP-481	
			var input=event.target.innerHTML+','+'8'+','+getGridRowCount(LISTVIEW_CPD_DETAILS)+','+getGridRowCount(LISTVIEW_SIGN_ACC)+','+getGridRowCount(LISTVIEW_DOC_CHKLIST)+","+getGridRowCount(LISTVIEW_TXT_VETTING)+','+getGridRowCount(LISTVIEW_LIMIT_CKLIST)+","+getGridRowCount(LISTVIEW_FINAL_DECISION) + "," + res; 
			executeServerEvent(event.target.id, event.type, input, false);
		}
	}else if(event.target.id == BUTTON_ADD_DOC_CHKLIST ){
		executeServerEvent(event.target.id, event.type, 'DocRvw', false);
	}else if(event.target.id == BUTTON_LIMIT_ADD_CKLIST ){
		executeServerEvent(event.target.id, event.type, 'LmtCrdt', false);
	}else if(event.target.id ==BUTTON_FETCH_ACC_MEMO){
		var mendate = getValue(FIELD_SIGACC_ACC_MNDT);
		displayMendate(mendate);
	}else if(event.target.id ==FINAL_DEDUPE){
		var ain = getWorkItemData('processInstanceId');
		openDuplicateCheck(ain);
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
		var id = "Q_Amendment_Data_FIN_"  + event.target.id.substring(22);
		console.log(id);
		setValues({[id]: value}, true);
	
	}else if(event.target.id == TSLM_INST_TOGGLE){ // CODE BY MOKSH
		var toggleValue = getValue(TSLM_INST_TOGGLE)
		if(toggleValue == false){
			hideControls('SEC_TSLM_CUST_SPECIAL_INST');
		}else if(toggleValue == true){
			showControls('SEC_TSLM_CUST_SPECIAL_INST');
		}
	}else if (event.target.id == BTN_FETCH_TSLM_CID_DETAILS) {
		executeServerEvent(event.target.id, event.type, '', false);
	}else if(event.target.id ==BTN_TSLM_INVOICE_CHK_CONFIRM){
		var ain = getWorkItemData('processInstanceId');
		openInvoiceDuplicateCheck(ain);
	}else if(event.target.id ==BUTTON_RETRY_ADD_DOC){
			executeServerEvent(event.target.id, event.type, '', false);
			window.parent.refreshWorkitem();
			
	}
}

function openDuplicateCheck(parameterDocRvw){
	var parameterJSP = parameterDocRvw.split("#");
	// var wd_uid=getWorkItemData("session299L129172680034Id");
	var wd_uid=getWorkItemData("sessionId");
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	var jspURL=sLocationOrigin+"/TFO/CustomFolder/DuplicateCheck.jsp?sWiName="+parameterJSP[0]+"&session="+wd_uid+"&WD_UID="+wd_uid;
	document.getElementById('sheet14_link').textContent = 'Duplicate Check'; 
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	setTabStyle("tab1",10, "visible", "true");
	selectSheet("tab1",10);

	// returnVal=window.open("/TFO/CustomFolder/DuplicateCheck.jsp?sWiName="+parameterJSP[0]+"&session="+wd_uid+"&WD_UID="+wd_uid,"","dialogWidth=300px;dialogHeight=300px");
}
function displayMendate(mandate)
{	
	var returnVal;
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);

	mandate=mandate.replace(/=/g,'~sp~');
	// returnVal =
	// window.open("/TFO/CustomFolder/displaySignatureAndMandate.jsp?cust_id=&AccountNumber=&mandate="+mandate,"","dialogWidth=1000px");
	document.getElementById('sheet14_link').textContent = 'Account Mandate';
	var jspURL=sLocationOrigin+"/TFO/CustomFolder/displaySignatureAndMandate.jsp?cust_id=&AccountNumber=&mandate="+mandate;
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	setTabStyle("tab1",10, "visible", "true");
	selectSheet("tab1",10);	

}


function subFormLoad(buttonId){
	if(buttonId == BUTTON_ADD_DOC_CHKLIST ){

		if(getValue('hide_btnModify_ID')==1){
			callCustomJSP(event.target.id,LISTVIEW_DOC_CHKLIST,"DocRvw");
		}else{
			showMessage("", "No record present for the selected Request Category and Request Type", 'error');
			return false;

		}
	}
	if (buttonId== BUTTON_SUBMIT) 
	{
		executeServerEvent(buttonId, EVENT_TYPE.LOAD, '', false);
	}
}
function subFormPreHookPPM(buttonId){
	return false;
}

function customerAppFormDocRvw(sTabName,sQvarName,sButtonName) {


	var sRequestType="",sRequestCategory="",sProductType="",sThirdParty="",sSourceChannnel="",sOldWiName="",sGteeNumber="";
	var sAmendmentType="",sSubmittedBy="",sLoanGroup="";;
	console.log("CusomerAppFormDocRv  "+getValue(REQUEST_TYPE));
	sRequestType = getValue("REQUEST_TYPE");
	console.log("RequestType LOV  "+sRequestType);
	sRequestCategory =getValue(REQUEST_CATEGORY);
	console.log("RequestCategory LOV  "+sRequestCategory);
	sProductType = getValue(COMBOX_PRODUCT_TYPE);
	console.log("sProductType LOV  "+sProductType);

	if("GRNT"==sRequestCategory || "SBLC"==sRequestCategory || "ILC"==sRequestCategory || "ELC"==sRequestCategory && ("ELC_SLCA"==sRequestType ||"ELC_SLCAA"==sRequestType
					|| "ELC_SER"==sRequestType || "ELC_SCL"==sRequestType))// added
																			// by
																			// mansi
		sAmendmentType = getValue(AMEND_TYPE);
	else if("EC"==sRequestCategory  || "IC"==sRequestCategory || "DBA"==sRequestCategory 
			|| "ELCB"==sRequestCategory || "ILCB"==sRequestCategory || "TL"==sRequestCategory 
			|| "IFS"==sRequestCategory || 	"IFP"==sRequestCategory || 	"IFA"==sRequestCategory ) // CODE
																									// BY
																									// MOKSH
		sAmendmentType = getValue(COMBOX_INF_AMEND_TYPE);			

	console.log("sAmendmentType LOV  "+sAmendmentType);

	if("TL"==sRequestCategory ){
		sLoanGroup = getValue(COMBOX_IFS_LOAN_GRP);
	}
	sSubmittedBy = getSelectedItemLabel(REQUESTED_BY); 
	console.log("sSubmittedBy LOV  "+sSubmittedBy);
	if("GRNT"==sRequestCategory || "SBLC"==sRequestCategory || "ILC"==sRequestCategory 
			|| "ELC"==sRequestCategory && ("ELC_SLCA"==sRequestType ||"ELC_SLCAA"==sRequestType
			|| "ELC_SER"==sRequestType || "ELC_SCL"==sRequestType))// added by
																	// mansi
		sThirdParty = getValue(COMBOX_TRN_THIRD_PARTY);
	sSourceChannnel = getValue(SOURCE_CHANNEL);

	console.log("OldWiName "+sOldWiName);
	sGteeNumber=getValue(TRANSACTION_REFERENCE);
	console.log("sGteeNumber=  "+sGteeNumber);		

	var ain = getValue('WI_NAME')+"#"+sTabName+"#"+sRequestType+"#"+sRequestCategory+"#"+
	sProductType+"#"+sThirdParty+"#"+sSourceChannnel+"#"+sOldWiName+"#"+sGteeNumber
	+"#"+sAmendmentType+"#"+sSubmittedBy+"#"+sRequestCategory+"#"+sRequestType+"#"+sLoanGroup+"#"+getValue(COMBO_CUST_INSTR);
	var getResult = showChecklistTab( ain,'ADD');


}

function callCustomJSP(sControlName,sQvarname,sTabName){
	console.log("btnModify  >>>> ");
	var lstCount=0;
	lstCount =getGridRowCount(sQvarname);
	if(lstCount>0){reLoadCustomerAppFormDocRvw(sTabName,sQvarname,sControlName);}
	else{
		customerAppFormDocRvw(sTabName,sQvarname,sControlName);}
}

function disabledefaultPPMFields(){
	var controlNames=DATE_INF_NEW_MATURITY+","+FIELD_INF_CHARGE_ACC_TITLE+","+FIELD_INF_CHARGE_ACC_CURR+","+FIELD_INF_SETTLEMENT_ACC_TITLE+","+FIELD_INF_SETTLEMENT_ACC_CURR+","+FIELD_INF_CREDIT_ACC_TITLE+","+FIELD_INF_CREDIT_ACC_CURR+","+FIELD_GRNT_CHRG_ACC_TITLE+","+FIELD_GRNT_CHRG_ACC_CRNCY;
	disableFieldOnDemand(controlNames);
	var requestType = getValue(REQUEST_TYPE);
	console.log("inside disabledefaultPPMFields-- ");
	if(('AM' == requestType)||('STL' == requestType) || ('SBLC_AM' == requestType) || ('ELC_SLCAA' == requestType)){// added
																													// by
																													// mansi
		setStyle(COMBOX_INF_CREDIT_ACC_NUM, PROPERTY_NAME.DISABLE, 'true');
	}
}

function setDefaultValues(){
	var requestType = getValue(REQUEST_TYPE);
	var reqCategory = getValue(REQUEST_CATEGORY);

	console.log("reqCategory++  "+reqCategory + "   requestType   " + requestType);
	if("ELC_LCA"==requestType){
		setEmptyCombo("LC_DOC_COURIER", "2");  
	}
	else if("ELC_SLCA"== requestType){
		setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
		setValues({[FCUBS_PUR_OF_MSG]:'Issue'},true);
	}
	else if("ELC_LCAA"== requestType){
		setEmptyCombo("LC_DOC_COURIER", "2");  
		setEmptyCombo(IS_ACC_VALID, "1");  
		setValues({[COMBOX_LMTCRE_APP_AVL]: '3'}, true);
		setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
	}else if("ELCB_AC"== requestType||"ELCB_BS"== requestType
			||"EC_AC"== requestType||"EC_BS" == requestType){
		setEmptyCombo(COMBOX_BILL_CUST_HLDING_ACC_US, "2");  
		if("ELCB_AC"== requestType||"ELCB_BS"== requestType)
		{setEmptyCombo(COMBOX_BILL_MODE_OF_PMNT, "ELCB_AC");}
		setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
		setValues({[COMBOX_DOC_REV_SUCC]: '3'}, true);
		setValues({[COMBOX_LMTCRE_APP_AVL]: '3'}, true);

		setValues({[DEC_CODE]: 'APP'}, true);
		if("EC"==reqCategory){
			setEmptyCombo(COMBOX_BILL_MODE_OF_PMNT, "EC_AC");
		}
	}else if("ILCB_BL" == requestType||"ELCB_BL" == requestType){
		setEmptyCombo(COMBOX_BILL_CUST_HLDING_ACC_US, "2");  
		setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
		setValues({[COMBOX_DOC_REV_SUCC]: '3'}, true);
		setEmptyCombo(COMBOX_LMTCRE_APP_AVL,"3");

	}else if("ILC_UM" == requestType){	// RR
		setValues({[IS_ACC_VALID]: '2'}, true); 
		setValues({[COMBOX_SUFF_BAL_AVL]: '3'}, true);
		setValues({[COMBOX_DOC_REV_SUCC]: '3'}, true);

	}/*
		 * else if ("GRNT"==reqCategory &&("NI"==requestType
		 * ||"AM"==requestType)&&(getValue(COMBOX_PRODUCT_TYPE).startsWith("T5"))) {
		 * setEmptyCombo(IS_ACC_VALID, "2");
		 * setEmptyCombo(COMBOX_TRN_THIRD_PARTY, "2");
		 * //setEmptyCombo(COMBOX_SUFF_BAL_AVL, "3"); }else if
		 * ("SBLC"==reqCategory &&("SBLC_NI"==requestType
		 * ||"SBLC_AM"==requestType)&&(getValue(COMBOX_PRODUCT_TYPE).startsWith("T5")))//added
		 * by mansi { setEmptyCombo(IS_ACC_VALID, "2");
		 * setEmptyCombo(COMBOX_TRN_THIRD_PARTY, "2");
		 * //setEmptyCombo(COMBOX_SUFF_BAL_AVL, "3"); } else if
		 * ("ELC"==reqCategory &&("ELC_SLCA"==requestType
		 * ||"ELC_SLCAA"==requestType))//&&(getValue(COMBOX_PRODUCT_TYPE).startsWith("T5")))//added &
		 * commented by mansi { setEmptyCombo(IS_ACC_VALID, "2");
		 * setEmptyCombo(COMBOX_TRN_THIRD_PARTY, "2");
		 * //setEmptyCombo(COMBOX_SUFF_BAL_AVL, "3"); }
		 */
	else if(requestType=='ILCB_AC'||requestType=='IC_AC'){
		var billStage = getValue('BILL_STAGE');
		if(billStage=="FIN"){
			setEmptyCombo(COMBOX_SUFF_BAL_AVL, "3");
			setEmptyCombo(COMBOX_DOC_REV_SUCC, "3");
			setEmptyCombo(COMBOX_LMTCRE_APP_AVL, "3");
			}
	} else if("ELCB"==reqCategory && "ELCB_AM"==requestType)
		{
				setEmptyCombo(COMBOX_INF_AMEND_TYPE, "ELCB_OIA");
				setEmptyCombo(COMBOX_BILL_CUST_HLDING_ACC_US, "2");
				setEmptyCombo(COMBOX_BILL_RVSD_DOC_AVL, "2");
				setEmptyCombo(COMBOX_BILL_MODE_OF_PMNT, "ELCB_AC");
				setEmptyCombo(COMBOX_REQ_SIGN_MAN, "3");
				setEmptyCombo(COMBOX_SUFF_BAL_AVL, "3");
				setEmptyCombo(COMBOX_DOC_REV_SUCC, "3");
				setEmptyCombo(COMBOX_LMTCRE_APP_AVL, "3");
		}
	// START CODE ATP-410 16-02-2024 REYAZ
	else if("EC"==reqCategory && "EC_AM"==requestType)
		{
				setEmptyCombo(COMBOX_BILL_RVSD_DOC_AVL, "2");
		}
	// END CODE ATP-410 16-02-2024 REYAZ
	else if("ELCB"==reqCategory && "ELCB_DISC"==requestType)
	{
			setEmptyCombo(COMBOX_BILL_CUST_HLDING_ACC_US, "1");
			setEmptyCombo(COMBOX_BILL_MODE_OF_PMNT, "ELCB_AC");
	}else if(reqCategory == 'IFA' || reqCategory == 'IFP' || reqCategory == 'IFS'){
		/*
		 * if(getValue(RELATIONSHIP_TYPE) == 'I' ){
		 * setValue('PROCESSING_SYSTEM','F'); }else{
		 * setValue('PROCESSING_SYSTEM','T'); }
		 */
	}
}

function normalizeString(str) {

	if (str == '') {
		return "";
	}
	if (str.trim()== '') {
		return "";
	}

}
function disableAmendType(control){
	var stReqType ="";
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);		
	if(("GRNT" == reqCat && "AM" != stReqType) || ("SBLC" == reqCat && "SBLC_AM" != stReqType)// added
																								// by
																								// mansi
			|| ("ILC" == reqCat && "ILC_AM" != reqType)
			|| ("ELC" == reqCat && ("ELC_LCAA"!= reqType || "ELC_SLCAA"!= reqType))// added
																					// by
																					// mansi
			|| ("TL" == reqCat && "TL_AM"!= reqType))
		setStyle(control,"disable","false");

}

function grid(){
	var JSONObj = {}
}






function setButtonName(sButtonName, sGridQvar,sJSP){
	console.log("SetButtonname "+sButtonName+"    GridNameQvar "+sGridQvar);

	
	var listCount=0;
	listCount = getGridRowCount(sGridQvar);

	if(listCount >0||'1'==sJSP){
		document.getElementById(sButtonName).innerHTML='Modify'
	}
	else
		document.getElementById(sButtonName).innerHTML='Add'
	
}


function showChecklistTab(parameterDocRvw,addOrModifyFlag)
{
	var returnVal="";
	var parameterJSP = parameterDocRvw.split("#");
	var wd_uid=getWorkItemData("sessionId");
	var urlDoc = document.URL;
	var jspURL="";
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);


	if(parameterJSP[1] == "DocRvw" &&'ADD'==addOrModifyFlag){
		jspURL=sLocationOrigin+"/TFO/CustomFolder/CustDocReview.jsp?WI_NAME="+parameterJSP[0]+"&gridData=&session="+wd_uid+"&sReqType="+parameterJSP[2]+"&sProdType="+parameterJSP[4]+"&Last_WI_Name="+parameterJSP[7]+"&sReqCat="+parameterJSP[3]+"&GTEE_NUMBER="+parameterJSP[8]+"&sAmendType="+parameterJSP[9]+"&sSubmittedBy="+parameterJSP[10]+"&sSourceChannel="+parameterJSP[6]+"&sReqCatCode="+parameterJSP[11]+"&sReqTypeCode="+parameterJSP[12]+"&sLoanGroup="+parameterJSP[13]+"&WD_UID="+wd_uid+"&ElcbCustInst="+parameterJSP[14]+"&Process_type="+getValue('PROCESS_TYPE');
	}else if(parameterJSP[1] == "LmtCrdt" &&'ADD'==addOrModifyFlag){
		jspURL=sLocationOrigin+"/TFO/CustomFolder/LimitCreditReview.jsp?WI_NAME="+parameterJSP[0]+"&gridData=&session="+wd_uid+"&sReqType="+parameterJSP[2]+"&sProdType="+parameterJSP[4]+"&sIsThirdParty="+parameterJSP[5]+"&sSourceChannel="+parameterJSP[6]+"&Last_WI_Name="+parameterJSP[7]+"&sReqCat="+parameterJSP[3]+"&GTEE_NUMBER="+parameterJSP[8]+"&sAmendType="+parameterJSP[9]+"&sReqCatCode="+parameterJSP[11]+"&sReqTypeCode="+parameterJSP[12]+"&sLoanGroup="+parameterJSP[13]+"&WD_UID="+wd_uid;
	}else if(parameterJSP[1] == "DocRvw" &&'MODIFY'==addOrModifyFlag){
		jspURL=sLocationOrigin+"/TFO/CustomFolder/CustDocReview.jsp?WI_NAME="+parameterJSP[0]+"&gridData="+parameterJSP[2]+"&sReqType="+parameterJSP[3]+"&sReqCat="+parameterJSP[4]+"&sProdType="+parameterJSP[5]+"&Last_WI_Name="+parameterJSP[8]+"&GTEE_NUMBER="+parameterJSP[9]+"&sAmendType="+parameterJSP[10]+"&sSubmittedBy="+parameterJSP[11]+"&sSourceChannel="+parameterJSP[7]+"&sReqCatCode="+parameterJSP[12]+"&sReqTypeCode="+parameterJSP[13]+"&sLoanGroup="+parameterJSP[14]+"&session="+wd_uid+"&WD_UID="+wd_uid+"&ElcbCustInst="+parameterJSP[15]+"&Process_type="+getValue('PROCESS_TYPE');
	}else if(parameterJSP[1] == "LmtCrdt" &&'MODIFY'==addOrModifyFlag){
		jspURL=sLocationOrigin+"/TFO/CustomFolder/LimitCreditReview.jsp?WI_NAME="+parameterJSP[0]+"&gridData="+parameterJSP[2]+"&sReqType="+parameterJSP[3]+"&sReqCat="+parameterJSP[4]+"&sProdType="+parameterJSP[5]+"&sIsThirdParty="+parameterJSP[6]+"&sSourceChannel="+parameterJSP[7]+"&Last_WI_Name="+parameterJSP[8]+"&GTEE_NUMBER="+parameterJSP[9]+"&sAmendType="+parameterJSP[10]+"&sReqCatCode="+parameterJSP[12]+"&sReqTypeCode="+parameterJSP[13]+"&sLoanGroup="+parameterJSP[14]+"&session="+wd_uid+"&WD_UID="+wd_uid;
	}
	document.getElementById('sheet14_link').textContent = 'CheckList';
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	setTabStyle("tab1",10, "visible", "true");
	selectSheet("tab1",10);		
}


function getGridData( sQvarName){
	var res="";
	var gridLength=0;
	gridLength = getGridRowCount(sQvarName);
	console.log("Grid Length "+gridLength);
	for(var Cntr = 0; Cntr< gridLength;Cntr++){
		var guideLine=getValueFromTableCell(sQvarName,Cntr,0).replace(/<>/g,'<BR>');
		res+=guideLine+"~"+getValueFromTableCell(sQvarName,Cntr,1)+"~~";
	}
	res =res.replace(/&/g, "AmpersandSign");
	res =res.replace(/%/g, "PercentSign");
	res =res.replace(/~ ~~/g, "~blankValue~~");
	return res;
}

function reloadOpenDocReviewPage(parameterDocRvw)
{
	var parameterJSP = parameterDocRvw.split("#");
	var wd_uid=getWorkItemData("sessionId");
	if(parameterJSP[1] == "DocRvw")
		returnVal=window.open("/TFO/CustomFolder/CustDocReview.jsp?WI_NAME="+parameterJSP[0]+"&gridData="+parameterJSP[2]+"&sReqType="+parameterJSP[3]+"&sReqCat="+parameterJSP[4]+"&sProdType="+parameterJSP[5]+"&Last_WI_Name="+parameterJSP[8]+"&GTEE_NUMBER="+parameterJSP[9]+"&sAmendType="+parameterJSP[10]+"&sSubmittedBy="+parameterJSP[11]+"&sSourceChannel="+parameterJSP[7]+"&sReqCatCode="+parameterJSP[12]+"&sReqTypeCode="+parameterJSP[13]+"&sLoanGroup="+parameterJSP[14]+"&session="+wd_uid+"&WD_UID="+wd_uid,"","dialogWidth=1150px");
	else if(parameterJSP[1] == "LmtCrdt")
		returnVal=window.open("/TFO/CustomFolder/LimitCreditReview.jsp?WI_NAME="+parameterJSP[0]+"&gridData="+parameterJSP[2]+"&sReqType="+parameterJSP[3]+"&sReqCat="+parameterJSP[4]+"&sProdType="+parameterJSP[5]+"&sIsThirdParty="+parameterJSP[6]+"&sSourceChannel="+parameterJSP[7]+"&Last_WI_Name="+parameterJSP[8]+"&GTEE_NUMBER="+parameterJSP[9]+"&sAmendType="+parameterJSP[10]+"&sReqCatCode="+parameterJSP[12]+"&sReqTypeCode="+parameterJSP[13]+"&sLoanGroup="+parameterJSP[14]+"&session="+wd_uid+"&WD_UID="+wd_uid,"","dialogWidth=1150px, scroll: off");
	return returnVal;	
}

function reLoadCustomerAppFormDocRvw( sTabName,  sQvarName, sButtonName) {
	var sRequestType="", sRequestCategory="",sProductType="",sThirdParty="",sSourceChannnel="",sOldWiName="",sGteeNumber="";
	var sAmendmentType="",sSubmittedBy="",sLoanGroup="";
	console.log("reLoadCustomerAppFormDocRvw  "  +getValue(REQUEST_TYPE));
	sRequestType = getValue(REQUEST_TYPE);
	console.log("RequestType LOV  "+sRequestType);
	sRequestCategory = getValue(REQUEST_CATEGORY);
	console.log("RequestCategory LOV  "+sRequestCategory);
	sProductType = getValue(COMBOX_PRODUCT_TYPE);
	console.log("sProductType LOV  "+sProductType);

	if("GRNT"==sRequestCategory ||"SBLC"==sRequestCategory || "ILC"==sRequestCategory 
			|| "ELC"==sRequestCategory && ("ELC_SLCA"==sRequestType ||"ELC_SLCAA"==sRequestType
			|| "ELC_SER"==sRequestType || "ELC_SCL"==sRequestType))// added by
																	// mansi
		sAmendmentType = getValue(AMEND_TYPE);
	else if("EC"==sRequestCategory  || "IC"==sRequestCategory || "DBA"==sRequestCategory 
			|| "ELCB"==sRequestCategory || "ILCB"==sRequestCategory || "TL"==sRequestCategory 
			|| "IFS"==sRequestCategory || 	"IFP"==sRequestCategory || 	"IFA"==sRequestCategory )	// CODE
																									// BY
																									// MOKSH
		sAmendmentType = getValue(COMBOX_INF_AMEND_TYPE);			

	if("TL"==sRequestCategory ){
		sLoanGroup = getValue(COMBOX_IFS_LOAN_GRP);
	}
	sSubmittedBy = getSelectedItemLabel(REQUESTED_BY);
	console.log("sSubmittedBy LOV  "+sSubmittedBy);
	if("GRNT"==sRequestCategory || "SBLC"==sRequestCategory || "ILC"==sRequestCategory  
			|| "ELC"==sRequestCategory && ("ELC_SLCA"==sRequestType ||"ELC_SLCAA"==sRequestType
			|| "ELC_SER"==sRequestType || "ELC_SCL"==sRequestType))// added by
																	// mansi
		sThirdParty = getValue(COMBOX_TRN_THIRD_PARTY);
	sSourceChannnel = getValue(SOURCE_CHANNEL);
	sGteeNumber=getValue(TRANSACTION_REFERENCE);
	console.log("sGteeNumber=  "+sGteeNumber);
	var ain = getValue('WI_NAME')+"#"+sTabName+"#"+getGridData(sQvarName)+"#"+sRequestType+"#"+sRequestCategory+"#"+sProductType+"#"+sThirdParty+"#"+sSourceChannnel+"#"+sOldWiName+"#"+sGteeNumber+"#"+sAmendmentType+"#"+sSubmittedBy+"#"+sRequestCategory+"#"+sRequestType+"#"+sLoanGroup+"#"+getValue(COMBO_CUST_INSTR);
	console.log(ain);
	showChecklistTab(ain,'MODIFY');
}

function onRowClickPPM(listviewId,rowIndex){
	console.log('listviewId='+listviewId+"rowIndex="+rowIndex);
	if(LISTVIEW_LINKED_CUST==listviewId){
		var cust_id ="";
		var sAccntNumber = "";
		sAccntNumber = getAccountNumber();
		console.log(" sAccntNumber="+sAccntNumber);
		console.log("cust_id= "+getValueFromTableCell(listviewId,rowIndex,0));
		cust_id = getValueFromTableCell(listviewId,rowIndex,0);
		var ain = cust_id+"#"+sAccntNumber;
		fetchSignatureImage(ain);
		return false;
	}else if(LISTVIEW_DOC_CHKLIST==listviewId||LVW_Decision_Sumary==listviewId||LISTVIEW_LIMIT_CKLIST==listviewId|| LISTVIEW_FFT_DETAIL == listviewId || 'FFT Desc' == listviewId ||LISTVIEW_DRAFT_DETAILS == listviewId){
		return false;
	}else if( 'Q_TSLM_Referral_Details' == listviewId){
		return false;
	}else {
	return true;
	}	
}

function fetchSignatureImage(parameterDocRvw)
{	
	var returnVal;
	var parameterJSP = parameterDocRvw.split("#");
	var urlDoc = document.URL;
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	document.getElementById('sheet14_link').textContent = 'Signature';
	var jspURL=sLocationOrigin+"/TFO/CustomFolder/displaySignatureAndMandate.jsp?cust_id="+parameterJSP[0]+"&AccountNumber="+parameterJSP[1]+"&mandate=";
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	setTabStyle("tab1",10, "visible", "true");
	selectSheet("tab1",10);

	// returnVal =
	// window.open("/TFO/CustomFolder/displaySignatureAndMandate.jsp?cust_id="+parameterJSP[0]+"&AccountNumber="+parameterJSP[1]+"&mandate=","","dialogWidth=1000px");
	// return returnVal;
}

function onClickTabPPM(tabId,sheetindex,eventCall){
	(BUTTON_SUBMIT);
	console.log('sheetindex='+sheetindex+'eventCall='+eventCall);	
	console.log('inside onClickTab, tabId: '+tabId+' and sheetIndex id: '+event.target.id+'event.target.innerHTML='+event.target.innerHTML);
	var controlID=sheetindex+','+event.target.innerHTML;

	var input=event.target.innerHTML+','+sheetindex+','+getGridRowCount(LISTVIEW_CPD_DETAILS)+','+getGridRowCount(LISTVIEW_SIGN_ACC)+','+getGridRowCount(LISTVIEW_DOC_CHKLIST)+","+getGridRowCount(LISTVIEW_TXT_VETTING)+","+getGridRowCount(LISTVIEW_TXT_VETTING)+','+getGridRowCount(LISTVIEW_LIMIT_CKLIST)+","+getGridRowCount(LISTVIEW_TXT_VETTING)+","+getGridRowCount(LISTVIEW_FINAL_DECISION)+","+getGridRowCount(LISTVIEW_TSLM_CPD)+","+getGridRowCount(LISTVIEW_TSLM_INVOICE_NO);// code
																																																																																																																							// by
																																																																																																																							// MOKSH
	if((sheetindex=='9'||sheetindex=='1')&&eventCall==1){ // final decision or
															// input details
		console.log('onClickTabPPM');
		saveWorkItem();
		executeServerEvent('onClickTabPPM', EVENT_TYPE.CLICK, input, false);
	}
	// contract integration
	if((document.getElementById(JSP_FRAME_CONTRACT)!=null  &&document.getElementById('tab1').getElementsByClassName("tab-pane fade")[10].style.display!='none')
			&&sheetindex!=10 && eventCall==2 ){
		showMessage('', 'Please click on close button', 'error');
		return false;
	}
}

function saveAndNextPreHookPPM(tabId){
	var reqType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);
	var processing_system = getValue('PROCESSING_SYSTEM');
	var input=event.target.innerHTML+','+getSheetIndex('tab1')+','+getGridRowCount(LISTVIEW_CPD_DETAILS)+
					','+getGridRowCount(LISTVIEW_SIGN_ACC)+','+getGridRowCount(LISTVIEW_DOC_CHKLIST)+
					","+getGridRowCount(LISTVIEW_TXT_VETTING)+","+getGridRowCount(LISTVIEW_TXT_VETTING)+
					','+getGridRowCount(LISTVIEW_LIMIT_CKLIST)+","+getGridRowCount(LISTVIEW_TXT_VETTING)+
					","+getGridRowCount(LISTVIEW_FINAL_DECISION)+","+getGridRowCount(LISTVIEW_TSLM_CPD)+
					","+getGridRowCount(LISTVIEW_TSLM_INVOICE_NO);	// CODE By
																	// MOKSH
	
	var selectedRows = getSelectedRowsIndexes(LISTVIEW_TSLM_CPD); 
	var rowCount  = getGridRowCount(LISTVIEW_TSLM_CPD);
	if(getSheetIndex('tab1')==3 && selectedRows[0] == undefined
			&& (requestCat == 'IFA' || requestCat == 'IFS' ||requestCat == 'IFP')
			&& (reqType == 'LD' ||reqType == 'IFA_CTP' )&& processing_system == 'T' && rowCount > 0){
		showMessage(LISTVIEW_TSLM_CPD, 'Please select a row in TSLM Counter Party Details', 'error');
		return false;
	}else if(getSheetIndex('tab1')== 1){
		if((getValue('UTC_REQUIRED') == 'No')&& (getValue('UTC_REQUIRED_BRMS') == 'Yes') && (getValue('UTC_JSTIFICATION_REQUIRED') == '') )
		{ 
			showMessage('UTC_JSTIFICATION_REQUIRED', 'Justification for UTC cannot be blank', 'error');
			return false;

		}
         //ATP -488 REYAZ 03-07-2024  START	
         var loanAmount = getValue('ADDITIONAL_LOAN_AMOUNT');
         var fundingRequired = (loanAmount === '' || loanAmount === '0') ? '2' : '1';
         setValues({ [TSLM_FUNDING_REQUIRED]: fundingRequired }, true);
         //ATP -488 REYAZ 03-07-2024  END
		//shivanshu
		if(('BAU'== getValue(PROCESS_TYPE)) || ('PT'== getValue(PROCESS_TYPE))){
			if (('ELCB_AM' == reqType) || ('EC_AM' == reqType)){
				if(getValue('IS_REMOTE_PRESENTATION') == undefined){
					showMessage('IS_REMOTE_PRESENTATION', 'Remote Presentation cannot be blank', 'error');
					return false;	
				}else if(getValue('BILL_RVSD_DOC_AVL') == '') {
					showMessage('BILL_RVSD_DOC_AVL', 'Revised Document Available  cannot be blank', 'error');
					return false;	
					}
			} else if ('BAU'== getValue(PROCESS_TYPE) && (('ELCB_BL' == reqType) || ('EC_BL' == reqType))){
				if(getValue('IS_REMOTE_PRESENTATION') == undefined){
					showMessage('IS_REMOTE_PRESENTATION', 'Remote Presentation cannot be blank', 'error');
					return false;
					}
		  }
		}
	    //ATP-458 ATP-490 Shivanshu  10-07-2024 START
		var swiftChannel=getValue('SWIFT_CHANNEL');
		var accNumber = getValue('ACCOUNT_NUMBER');
		var accDRNumber = getValue('DR_ACC_NUM');
		var message = "";
		if(swiftChannel == 'MT798' && accNumber != '' && (reqType=='NI' || reqType=='GA' || reqType=='ILC_NI')){
			if(accNumber != accDRNumber){
				message = "Account selected for Charge account is different from account received via MT798. Do you wan’t proceed.";
				showMessage(accNumber, message, 'error');
				return true;
			}
		}
		//ATP-458 ATP-490 Shivanshu 10-07-2024 END
        //ATP-454 REYAZ 02-05-2024
		//START CODE
		if('TSLM Front End'== getValue(PROCESS_TYPE) && ('AM' == reqType)){
			if(getValue('INF_NEW_MATURITY_DATE') ==''){
				showMessage('INF_NEW_MATURITY_DATE"', 'New maturity date cannot be Blank', 'error');
				return false;	
			}
			else if(!validateTSLMNewMaturityDate()){
				showMessage('INF_NEW_MATURITY_DATE"', 'New maturity date cannot be less than current date', 'error');
				return false;	
			}
			if(getValue('AMENDED_EFFECTIVE_DATE') ==''){
				showMessage('AMENDED_EFFECTIVE_DATE"', 'Amended Effective  date  cannot be Blank', 'error');
				return false;	
			}
		}
		//END CODE
	}
	else{
		var response=executeServerEvent('saveAndNextPreHook', EVENT_TYPE.CLICK, input, true);
		var jsonData = handleTFOResponse(response);
		if (!jsonData.success){
			return false;
		}
	}	
	return true;
}
function handleJSPResponse(typeOfJsp,data){
	 if(typeOfJsp=='UTC_BRMS_Calls'){
		selectSheet("tab1",12);
	}
}

function handleJSPResponsePPM(typeOfJsp,data){
	var workstepName = getWorkItemData('activityName');
	
	
	if(typeOfJsp=='UTC_BRMS_Calls'){
		
		if(data=='Success'){
			setTabStyle('tab1',12, "visible", "false");
			selectSheet('tab1', 9);
		}
		 window.parent.refreshWorkitem();
	}else if(typeOfJsp=='InvoiceDetails'){

		if(data.length>0){
			showMessage('',data, 'error');
			if(data=='Rows in Uploaded File Cannot Exceed 200'){
				return false;
			} 
		}
		 window.parent.refreshWorkitem();
	}else if(typeOfJsp=='DocRvw'){
		if(data.length>0){
			setButtonName(BUTTON_ADD_DOC_CHKLIST,LISTVIEW_DOC_CHKLIST,'1');
			executeServerEvent(BUTTON_ADD_DOC_CHKLIST, 'handlingJSPData', data,true);
		}
		selectSheet("tab1",5);
		setTabStyle("tab1",10, "visible", "false");
	}else if(typeOfJsp=='LmtCrdt'){
		if(data.length>0){
			setButtonName(BUTTON_LIMIT_ADD_CKLIST,LISTVIEW_LIMIT_CKLIST,'1');
			executeServerEvent(BUTTON_LIMIT_ADD_CKLIST, 'handlingJSPData', data,true);
		}
		selectSheet("tab1",7);
		setTabStyle("tab1",10, "visible", "false");
	}else if (typeOfJsp=='SIGNANDACC'){
		setTabStyle("tab1",10, "visible", "false");
		selectSheet("tab1",4);
	}else if(typeOfJsp=='DuplicateCheck' && workstepName=='PP_MAKER'){
		setTabStyle("tab1",10, "visible", "false");
		selectSheet("tab1",9);
	}else if(typeOfJsp=='DuplicateCheck'){ // PM amd PC
		setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
		selectSheet("tab1",PM_FINAL_DEC_SHEET_ID);
	}else if(typeOfJsp=='InvoiceDuplicateCheck' && workstepName=='PP_MAKER'){	// CODE
																				// BY
																				// MOKSH
		setTabStyle("tab1",10, "visible", "false");
		selectSheet("tab1",9);
	}else if(typeOfJsp=='InvoiceDuplicateCheck'){ // PM amd PC
		setTabStyle("tab1",PM_TAB_IFRAME_ID, "visible", "false");
		selectSheet("tab1",PM_FINAL_DEC_SHEET_ID);
	}/*
		 * else if(typeOfJsp=='TfoInvoiceDetails'){ // Added by reyaz 09/08/2022
		 * executeServerEvent(BUTTON_LIMIT_ADD_CKLIST, 'handlingJSPData',
		 * data,true); // Added by reyaz 09/08/2022 }
		 */
}

function enablePTHybdridTxnFieldAtPPM(){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PT_UTILITY_FLAG) == 'Y'){
		if(reqCat == 'ILC' || reqCat == 'GRNT' || reqCat == 'SBLC' || reqCat == 'ILCB' 
		|| reqCat == 'IC' || reqCat == 'ELCB' || reqCat == 'EC' || reqCat == 'TL' || reqCat == 'ELC'){// added
																										// by
																										// mansi
			if(reqType == 'ILC_NI' || reqType == 'ILC_AM' || reqType == 'NI' || reqType == 'SBLC_NI' || reqType == 'ELC_SLCA'
				|| reqType == 'AM' || reqType == 'SBLC_AM'|| reqType == 'ELC_SLCAA'
				|| reqType == 'ILCB_AC' || reqType == 'ILCB_BS' || reqType == 'ILCB_BCDR' 
				|| reqType == 'IC_AC' || reqType == 'IC_BS' || reqType == 'TL_LD' 
			    || reqType == 'ELCB_BL' || reqType == 'ELCB_AM' || reqType == 'ELCB_DISC' 
				|| reqType == 'EC_BL' || reqType == 'EC_AM' || reqType == 'EC_DISC'){
				setStyle(PT_HYBRID_PROFILE_TXN, PROPERTY_NAME.VISIBLE, 'true');
				setStyle(PT_HYBRID_PROFILE_TXN, PROPERTY_NAME.DISABLE, 'true');
				}
				if(reqType == 'ILC_NI'){
				setStyle('PT_CUST_EMAIL_ID', PROPERTY_NAME.VISIBLE, 'true');
				}
		}
	}
}

function enableGoodsDescFieldAtPPM(){
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
		if(reqCat == 'ILCB' || reqCat == 'IC' || reqCat == 'ELCB' || reqCat == 'EC'){
			if(reqType == 'ILCB_AC' || reqType == 'IC_AC' || reqType == 'ELCB_AC' || reqType == 'EC_AC' || reqType == 'EC_AM' || reqType == 'EC_DISC' || reqType == 'ELCB_AM' || reqType == 'ELCB_DISC'){
				setStyle(LC_GOODS_DESC, PROPERTY_NAME.VISIBLE, 'true');
				setStyle(LC_GOODS_DESC, PROPERTY_NAME.DISABLE, 'true');
				}
		}
}

function setFocusOnReferralGrid(){
	setFocus(FINAL_DECISION_GRID_ADDITIONAL_MAIL);
}

function deletePTSourceChannel(){
	 var count=getItemCountInCombo(SOURCE_CHANNEL);
	 var processType=getValue(PROCESS_TYPE)
	 var notPTFlag;
	 var NAFlag;
	 console.log(count);
	 if(processType!='PT'){
		 for(var i=0;i<count;i++){
			 var value=	getItemLabel(SOURCE_CHANNEL,i);
			 console.log(i+','+value);
			 if("INIT ProTrade"==value){
				 notPTFlag=i;
				 break;
			 } 
		 }
	 }
	 
	 if(notPTFlag!=null){
		  removeItemFromCombo(SOURCE_CHANNEL,notPTFlag);
	 }
}
function selectRowHookPPM(tableId,selectedRowsArray,isAllRowsSelected){
	console.log("INSIDE  selectRowHookPM");
	console.log('tableId='+tableId+'selectedRowsArray='+selectedRowsArray);
	var listviewId = tableId;
	var rowIndex = selectedRowsArray[0];
	console.log('row index = '+rowIndex);
//Added for Multi Select SCF ATP -- 200
	var reqType = getValue(REQUEST_TYPE);
	var reqCat = getValue(REQUEST_CATEGORY);
	if(('SCF' == reqCat) && ('PD' == reqType || 'PDD' == reqType)){
		if(rowIndex >= 0){
			if('Q_TSLm_Counter_Dets' == listviewId){
				var rowcount = getGridRowCount('Q_TSLm_Counter_Dets');
				var rowCheckNo ='';
				if(selectedRowsArray.length > 1){
					    rowCheckNo = 'Q_TSLm_Counter_Dets_'+selectedRowsArray[1];
						setValue(rowCheckNo,'false');
						showMessage('Q_TSLm_Counter_Dets', 'Please Select Only One Value', 'error');
						return false;
				} else{
					setTableCellData(listviewId,selectedRowsArray[0],4,'true',true);
				}
			}
		}
	} else {
	if(rowIndex >= 0){
		if('Q_TSLm_Counter_Dets' == listviewId){
			var rowcount = getGridRowCount('Q_TSLm_Counter_Dets');
			for(var i=0; i<rowcount; i++){
				setTableCellData(listviewId,i,4,'false',true);
			}
			for(var j=0 ; j<selectedRowsArray.length; j++){
				setTableCellData(listviewId,selectedRowsArray[j],4,'true',true);
			}
		}
	}	
	}
	
	saveWorkItem();
}

function amendmentUserfillInFinal(){ 
}

// CODE BY MOKSH
function openInvoiceDuplicateCheck(parameterDocRvw){
	var parameterJSP = parameterDocRvw.split("#");
	var wd_uid=getWorkItemData("session299L129172680034Id");
	var urlDoc = document.URL;
	var customer_id = getValue(CUSTOMER_ID);
	var sLocationOrigin = urlDoc.substr(0,urlDoc.indexOf('TFO')-1);
	var jspURL=sLocationOrigin+"/TFO/CustomFolder/InvoiceDuplicateCheck.jsp?sWiName="+parameterJSP[0]+"&session="+wd_uid+"&WD_UID="+wd_uid+"&sCustID="+customer_id;
	document.getElementById('sheet14_link').textContent = 'Invoice Duplicate Check'; 
	document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
	setTabStyle("tab1",10, "visible", "true");
	selectSheet("tab1",10);

// //returnVal=window.open("/TFO/CustomFolder/DuplicateCheck.jsp?sWiName="+parameterJSP[0]+"&session="+wd_uid+"&WD_UID="+wd_uid,"","dialogWidth=300px;dialogHeight=300px");
}

function setProductCodeTSLM(){ // BY KISHAN TSLM
	var reqCat = getValue(REQUEST_CATEGORY);
	var reqType = getValue(REQUEST_TYPE);
	if(getValue(PROCESSING_SYSTEM) == 'T'){
		if(reqCat == 'IFA' && reqType == 'LD'){
			setValues({[COMBOX_PRODUCT_TYPE]: 'L092'}, true);
		}
	}else if(getValue(PROCESSING_SYSTEM) == 'F'){
		if(reqCat == 'IFA' && reqType == 'LD'){
			setValues({[COMBOX_PRODUCT_TYPE]: 'TF03'}, true);
		}
	}
}

function fieldEnableDisableProSys(){
	var requestType = getValue(REQUEST_TYPE);
	var requestCat = getValue(REQUEST_CATEGORY);
	var processing_system = getValue('PROCESSING_SYSTEM');
	if(getValue( PROCESSING_SYSTEM) ==  'F' ){
		setValues({[RELATIONSHIP_TYPE]: 'I'}, true);
	}else if(getValue( PROCESSING_SYSTEM) == 'T'){
		setValues({[RELATIONSHIP_TYPE]: 'C'}, true);
	}
	setDefaultValue();
	executeServerEvent('RELATIONSHIP_TYPE', 'change', '', true);
	tslmReferral();
	
	if(getValue(PROCESSING_SYSTEM) == 'T'){
		if(requestType == 'LD'){
			showControls('SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID');
			enableFieldOnDemand('STANDALONE_LOAN,BTN_FETCH_TSLM_CID_DETAILS');
		}else if( requestType == 'IFA_CTP' ){
			enableFieldOnDemand('BTN_FETCH_TSLM_CID_DETAILS');
		}
		if(requestType != 'LD'){
			showControls('SEC_TSLM_LOAN_INPUT_DETAILS');
		}
		showControls('SEC_TSLM_COUNTER_PARTY_DETAILS');
		showControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM');
		showControls('BTN_FETCH_TSLM_CID_DETAILS,Toggle2,TSLM_COMPANY_TYPE');
		if(requestCat == 'IFA' && requestType == 'LD'){
			setValues({[COMBOX_PRODUCT_TYPE]: 'L092'}, true);
		}
		disableFieldOnDemand('TSLM_REFERRAL_FLAG');
	} else if(getValue(PROCESSING_SYSTEM) == 'F'){
		disableFieldOnDemand('STANDALONE_LOAN,BTN_FETCH_TSLM_CID_DETAILS,SEC_TSLM_REF_DET');
		hideControls('SEC_TSLM_COUNTER_PARTY_DETAILS,SEC_INVOICE_NUMBERS_FOR_STANDALONE_LOAN,SEC_TSLM_CUST_SPECIAL_INST,SEC_TSLM_LOAN_INPUT_DETAILS');
		hideControls('TSLM_DEDUPE_LABEL,TSLM_INVOICE_CHK_CONFIRM,TSLM_DEDUPE_DROPDOWN,BTN_TSLM_INVOICE_CHK_CONFIRM,SEC_TSLM_REF_DET');
		hideControls('BTN_FETCH_TSLM_CID_DETAILS,Toggle2,FCUBS_ANY_PAST_DUE_CID,TSLM_ANY_PAST_DUE_CID,TSLM_COMPANY_TYPE');
		setValues({['STANDALONE_LOAN']: '1'}, true);
		if(requestCat == 'IFA' && requestType == 'LD'){
			setValues({[COMBOX_PRODUCT_TYPE]: 'TF03'}, true);
		}
	}
}
// CODE BY RAKSHITA
function tslmReferral(){
	var requestCat = getValue('REQUEST_CATEGORY');
	var requestType = getValue('REQUEST_TYPE');
	var PROCESSING_SYSTEM = getValue('PROCESSING_SYSTEM');
	var rowCount=0;
	try{
	rowCount = getGridRowCount('Q_TSLM_Referral_Details');
	}catch(err){
		
	}
	var prevWS = getValue(PREV_WS);
	
	if('IFS' == requestCat || 'IFP' == requestCat|| 'IFA' == requestCat){
		if(PROCESSING_SYSTEM == 'T' && (prevWS == WORKSTEP.PMPS ||prevWS == WORKSTEP.PCPS) && rowCount!=0 ){	
			showControls(SEC_TSLM_REF_DET);// to show the tslm referral section
		}else{
			hideControls(SEC_TSLM_REF_DET);
		}
	} 
}

// CODE BY RAKSHITA
function enaDisInvoiceNoStandaLone(){
	var standalone_loan = getValue(TSLM_STANDALONE_LOAN);	
	var requestCat = getValue('REQUEST_CATEGORY');
	var requestType = getValue('REQUEST_TYPE');
	var PROCESSING_SYSTEM = getValue('PROCESSING_SYSTEM');
	if('IFS' == requestCat || 'IFP' == requestCat|| 'IFA' == requestCat){
		if(PROCESSING_SYSTEM == 'T' ){
			if( standalone_loan == '1' && requestType == 'LD' ){	
				enableFieldOnDemand(LISTVIEW_TSLM_INVOICE_NO);
			}else{
				disableFieldOnDemand(LISTVIEW_TSLM_INVOICE_NO);
			}
		} 
	}
}

	function expiryTypeDisableGRNT()	// RR
	{
		var reqType=getValue(REQUEST_TYPE);
		var processType=getValue(PROCESS_TYPE);
		var reqCat=getValue(REQUEST_CATEGORY);
		
		if(processType == 'BAU' || processType == 'SWIFT')
		{	
			if(reqCat == 'GRNT' && (reqType =='CC' || reqType =='ER' || reqType =='EPC' || reqType =='CL'))
			{
				disableFieldOnDemand(TRN_TENOR);
			}
		}
	}
	
	function toCheckGridDataOnPPM(flag){
		var length = getGridRowCount(LISTVIEW_REF_DocRvw);
	    var refertovalue =getValue('DOCREV_REF_TO');
	    if(flag == 'A' &&(refertovalue == 'Customer' || refertovalue == 'Customer Only Through Email')){
	    	for(var i =0; i<length; i++){
			var value = getValueFromTableCell(LISTVIEW_REF_DocRvw,i,0);
				if(value=='Customer' || value=='Customer Only Through Email'){
					showMessage(LISTVIEW_REF_DocRvw,value+' already exist', 'error');
					return false;
				} 
			}
		}
		return true;
	}
	
	function setTextVettingComboFieldsSBLC_NI() { // BY KISHAN
		var processType=getValue(PROCESS_TYPE);
		var reqType=getValue(REQUEST_TYPE);
		if(reqType =='SBLC_NI' || reqType =='SBLC_AM'){
			setValues({[COMBOX_TXT_FORMAT]: '2'}, true);
			setValues({[COMBOX_TXT_REQ_APP]: '3'}, true);
		}
	}
	
	function enableDisableFieldsPPM_SBLC_NI(){  // BY KISHAN
		var processType=getValue(PROCESS_TYPE);
		if(processType == 'PT'){
			showControls(PPM_SBLC_NI_PT_SHOW);
			disableFieldOnDemand(PPM_SBLC_NI_PT_DISABLE);
			enableFieldOnDemand(PPM_SBLC_NI_PT_ENABLE);
			hideControls(PPM_SBLC_NI_PT_HIDE); 
		}
		else if(processType == 'BAU'){
			showControls(PPM_SBLC_NI_BAU_SHOW);
			// disableFieldOnDemand(PPM_SBLC_NI_BAU_DISABLE);
			enableFieldOnDemand(PPM_SBLC_NI_BAU_ENABLE);
			hideControls(PPM_SBLC_NI_BAU_HIDE);
		} 
		else if(processType == 'SWIFT'){
			showControls(PPM_SBLC_NI_SWIFT_SHOW);
			disableFieldOnDemand(PPM_SBLC_NI_SWIFT_DISABLE);
			enableFieldOnDemand(PPM_SBLC_NI_SWIFT_ENABLE);
			hideControls(PPM_SBLC_NI_SWIFT_HIDE);
		}
	} 
	
	// santhosh added for testing of fields count
	function showChecklistTab_CountFields()
	{ 
		var wd_uid=getWorkItemData("sessionId");
		var jspURL="";
		jspURL="https://10.101.109.182:9444/TFO/CustomFolder/testcountfields.jsp?session="+wd_uid;
		
		document.getElementById(JSP_FRAME_CONTRACT).src=jspURL;
		setTabStyle("tab1",10, "visible", "true");
		selectSheet("tab1",10);		
	}
	
	// added by reyaz
	function enableDisableStnTd(){                                  
		if(getValue(TSLM_STANDALONE_LOAN) == ''){
			enableFieldOnDemand(TSLM_STANDALONE_LOAN);
		}else {
			disableFieldOnDemand(TSLM_STANDALONE_LOAN);
		}
		
		if(getValue(INF_TENOR_DAYS)== ''){
			enableFieldOnDemand(INF_TENOR_DAYS);
		}else{
			disableFieldOnDemand(INF_TENOR_DAYS);
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
		console.log("name = "+name);
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
		executeServerEvent('UTC_ON_LOAD_INVOICE', 'click', '', false);
}
	
	function addInvoiceDetSNO(){
		onInvoiceDetailLoad();
		customListViewValidationPPM('QVAR_utc_details','O');
	}
	function setDefaultField(){
		var reqType = getValue(REQUEST_TYPE);
		var reqCat = getValue(REQUEST_CATEGORY);
		var prodType = getValue('IF_PURCHASE_PRODUCT_CODE');
		if('IFA' == reqCat && 'IFA_CTP' == reqType && prodType == 'L128') {  // krishna
			setEmptyCombo(COMBOX_INF_AMEND_TYPE, "APTP");
			//setValue('INF_AMEND_TYPE','APTP');
			 
		}
	}
	function ppmhybridcombo(){  //Krishna
	    var reqType = getValue(REQUEST_TYPE);
		var reqCat = getValue(REQUEST_CATEGORY);
		if ('TSLM Front End'== getValue(PROCESS_TYPE)){
		if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType))
			{
			setStyle('SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('FRM_REFERAL_DETAILS', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('SIGN_REFERRAL_ID', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('SIGN_REFERRAL_ID', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('lvwSignAcc', PROPERTY_NAME.VISIBLE, 'false');	
			
			setStyle('FRM_REFERAL_DETAILS_DOC', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('Doc_Review_RefID', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('Doc_Review_RefID', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('lvwDocRvw', PROPERTY_NAME.VISIBLE, 'false');	
			
			setStyle('FRM_REFERAL_DETAILS_LIMIT', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('LIMIT_REFERRAL_ID', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('LIMIT_REFERRAL_ID', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('lvwLmtCrdt', PROPERTY_NAME.VISIBLE, 'false');	
				}
		  } 
		  else{
			setStyle('SIGN_REFERRAL_ID', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('SIGN_REFERRAL_ID', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('Doc_Review_RefID', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('Doc_Review_RefID', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('LIMIT_REFERRAL_ID', PROPERTY_NAME.DISABLE, 'false');	
			setStyle('LIMIT_REFERRAL_ID', PROPERTY_NAME.VISIBLE, 'true');	
			setStyle('SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL', PROPERTY_NAME.VISIBLE, 'false');
			setValue('SUPPORTING_DOCS_REQ_PER_CREDIT_APPROVAL','');
			setStyle('FRM_REFERAL_DETAILS', PROPERTY_NAME.DISABLE, 'false');					
			setStyle('FRM_REFERAL_DETAILS_DOC', PROPERTY_NAME.DISABLE, 'false');					
			setStyle('FRM_REFERAL_DETAILS_LIMIT', PROPERTY_NAME.DISABLE, 'false');	
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
			
		//ATP-463 29-05-2024 --JAMSHED START	
		/*if(('IFP' == reqCat || 'IFS' == reqCat || 'IFA' == reqCat) && ('LD' == reqType || 'IFA_CTP' == reqType))
		{
			setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.VISIBLE, 'true');	
			
		}else{
			setStyle('PAST_DUE_LIABILITY', PROPERTY_NAME.VISIBLE, 'false');	
		}*/
		//ATP-463 29-05-2024 --JAMSHED END
	}	
	function ppmTslmValidation(){ // krishna
	    
		var reqType = getValue(REQUEST_TYPE);
		var reqCat = getValue(REQUEST_CATEGORY);
		
		 if('TSLM Front End'== getValue(PROCESS_TYPE) )
		 {
			 //if(reqCat == 'IFA' && reqType == 'IFA_CTP'){//To be disbaled for all request types
				// Hybrid changes |reyaz|ATP-512|28-08-2024 start
			    if(getValue('Hybrid_Customer')!='Yes'){
				  disableFieldOnDemand('Q_TSLm_Counter_Dets,');
				}
			    // Hybrid changes |reyaz|ATP-512|28-08-2024 start end
			 //}
				setStyle('ASSET_ID', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('CUSTOMER_REFERENCE', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('FINANCE_PERCENTAGE', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('FOR_ACCOUNT_OF_FLAG', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('SUBCOMPANY_NAME', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('GOOD_SERVICE_UTIL_DESCRIPTION', PROPERTY_NAME.VISIBLE, 'true');
				setStyle('PAY_CURRENCY', PROPERTY_NAME.VISIBLE, 'false');
				setStyle('PAY_AMOUNT', PROPERTY_NAME.VISIBLE, 'false');
				//Added 
                setStyle('ASSET_ID', PROPERTY_NAME.DISABLE, 'true');
				setStyle('CUSTOMER_REFERENCE', PROPERTY_NAME.DISABLE, 'true');
				setStyle('FINANCE_PERCENTAGE', PROPERTY_NAME.DISABLE, 'true');
				setStyle('FOR_ACCOUNT_OF_FLAG', PROPERTY_NAME.DISABLE, 'true');
				setStyle('SUBCOMPANY_NAME', PROPERTY_NAME.DISABLE, 'true');
				setStyle('GOOD_SERVICE_UTIL_DESCRIPTION', PROPERTY_NAME.DISABLE, 'true');
				setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.DISABLE, 'true');
				setStyle('PAY_CURRENCY', PROPERTY_NAME.DISABLE, 'true');
				setStyle('PAY_AMOUNT', PROPERTY_NAME.DISABLE, 'true');
				//setValue('LMTCRE_APP_AVL_PPM','3');
				 setValues({['LMTCRE_APP_AVL_PPM']: '3'}, true);
				//setValue('DOC_REV_SUCC_PPM','1');
				 setValues({['DOC_REV_SUCC_PPM']: '1'}, true);
				//setValue('REQ_SIGN_MAN_PPM','3');
				//feature/scfmvp2 15082024 --Jamshed Start
				if(getValue('Hybrid_Customer')=='Yes'){
					setValues({['REQ_SIGN_MAN_PPM']: '1'}, true);
				}else{
				 setValues({['REQ_SIGN_MAN_PPM']: '3'}, true);
				}
				//feature/scfmvp2 15082024 --Jamshed End
				//setValue('SUFF_BAL_AVL_PPM','3');
				 setValues({['SUFF_BAL_AVL_PPM']: '3'}, true);
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
	function setProductCodeTslmFrontEnd(){ // krishna
		var reqCat = getValue(REQUEST_CATEGORY);
		var reqType = getValue(REQUEST_TYPE);
		
		if('TSLM Front End'== getValue(PROCESS_TYPE) )
				{
			      setValues({['STANDALONE_LOAN']: '2'}, true);
		        } 
		if (('TSLM Front End'== getValue(PROCESS_TYPE)) && 	((reqCat == 'IFP' && reqType == 'LD') ||	(reqCat == 'IFA' && reqType == 'IFA_CTP'))){
			setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.VISIBLE, 'true');
			setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.DISABLE, 'true');
		}else{
		      
			setStyle('LEGALIZATION_CHARGES_DETAIL', PROPERTY_NAME.VISIBLE, 'false');
			setValue('LEGALIZATION_CHARGES_DETAIL','');
		}
		 if ((getValue('Hybrid_Customer')=='No')||('TSLM Front End'== getValue(PROCESS_TYPE))) {
			if(reqCat == 'IFA' && reqType == 'LD'){
				setValues({[COMBOX_PRODUCT_TYPE]: 'L092'}, true);
			}
		
			if(reqCat == 'IFP' && reqType == 'LD'){
				setValues({[COMBOX_PRODUCT_TYPE]: 'L128'}, true);
			}
			if(reqCat == 'IFS' && reqType == 'LD'){
			setValues({[COMBOX_PRODUCT_TYPE]: 'L129'}, true);
			}
			//if (('TSLM Front End'== getValue(PROCESS_TYPE)) && (reqCat == 'IFP' ||reqCat == 'IFA' || reqCat == 'IFS') &&(reqType == 'LD' ||reqType == 'IFA_CTP'||reqType == 'LD')){
            //  showControls('BTN_FETCH_TSLM_CID_DETAILS');
//enableFieldOnDemand('BTN_FETCH_TSLM_CID_DETAILS');
          //   }
		 }	
	}

function listViewLoadPPM(controlId,action){
	
	if(controlId=='SIGN_REFERRAL_ID' && action=='A'){

		var GridCount = getGridRowCount(controlId);
		var sno="";
		sno=getValueFromTableCell(controlId,GridCount-1,0);
		if(sno!=undefined){
		sno=sno.substring(0,3);
		}
		
		if(sno=='WMS'){
			sno=getValueFromTableCell(controlId,GridCount-1,0);
			var num=parseInt(sno.substring(3,sno.length));
			num=num+1;
			sno='WMS'+num;
			setValue('table66_SEQNO',sno);
			
		}else{
			setValue('table66_SEQNO','WMS1');
			
		}		
			setStyle('TSLM_SIGACC_REFER_ID', PROPERTY_NAME.VISIBLE, 'true');
		    setStyle('TSLM_SIGACC_REFER_TYPE', PROPERTY_NAME.VISIBLE, 'true');
		    setStyle('table66_SEQNO','disable','true');
		    setStyle('REFF_DESC','disable','true');
		    setStyle('USER_DESC','disable','false');
		    setStyle('STATUS','disable','false');   
		    setStyle('CLOSED_BY','disable','true');
		    setStyle('CLOSED_DATE','disable','true');
			
	} if(controlId=='Doc_Review_RefID'&& action=='A'){

		var GridCount = getGridRowCount(controlId);
		var sno="";
		sno=getValueFromTableCell(controlId,GridCount-1,0);
		if(sno!=undefined){
		sno=sno.substring(0,3);
		}
		
		if(sno=='WMS'){
			sno=getValueFromTableCell(controlId,GridCount-1,0);
			var num=parseInt(sno.substring(3,sno.length));
			num=num+1;
			sno='WMS'+num;
			setValue('table68_Seqno',sno);
			
		}else{
			setValue('table68_Seqno','WMS1');
			
		}		
			setStyle('TSLM_DOCREV_REFER_ID', PROPERTY_NAME.VISIBLE, 'true');
		    setStyle('TSLM_DOCREV_REFER_TYPE', PROPERTY_NAME.VISIBLE, 'true');
		    setStyle('table68_Seqno','disable','true');
		    setStyle('REFF_DESC','disable','true');
		    setStyle('USER_DESC','disable','false');
		    setStyle('STATUS','disable','false');   
		    setStyle('CLOSED_BY','disable','true');
		    setStyle('CLOSED_DATE','disable','true');
			
	}  if(controlId=='LIMIT_REFERRAL_ID' && action=='A'){

		var GridCount = getGridRowCount(controlId);
		var sno="";
		sno=getValueFromTableCell(controlId,GridCount-1,0);
		if(sno!=undefined){
		sno=sno.substring(0,3);
		}
		
		if(sno=='WMS'){
			sno=getValueFromTableCell(controlId,GridCount-1,0);
			var num=parseInt(sno.substring(3,sno.length));
			num=num+1;
			sno='WMS'+num;
			setValue('table69_SEQNO',sno);
			
		}else{
			setValue('table69_SEQNO','WMS1');
			
		}	
			setStyle('TSLM_LMTCRE_REFER_ID', PROPERTY_NAME.VISIBLE, 'true');
		    setStyle('TSLM_LMTCRE_REFER_TYPE', PROPERTY_NAME.VISIBLE, 'true');
		    setStyle('table69_SEQNO','disable','true');
		    setStyle('REFF_DESC','disable','true');
		    setStyle('USER_DESC','disable','false');
		    setStyle('STATUS','disable','false');   
		    setStyle('CLOSED_BY','disable','true');
		    setStyle('CLOSED_DATE','disable','true');
			
	} if(controlId=='Discrepancy_Details' && action=='A'){

		var GridCount = getGridRowCount(controlId);
		var sno="";
		sno=getValueFromTableCell(controlId,GridCount-1,0);
		if(sno!=undefined){
		sno=sno.substring(0,3);
		}
		
		if(sno=='WMS'){
			sno=getValueFromTableCell(controlId,GridCount-1,0);
			var num=parseInt(sno.substring(3,sno.length));
			num=num+1;
			sno='WMS'+num;
			setValue('table78_SeqNo',sno);
			
		}else{
			setValue('table78_SeqNo','WMS1');
			
		}		
	}
	
	if(controlId=='SIGN_REFERRAL_ID' && action=='M'){
		
		    setStyle('TSLM_SIGACC_REFER_ID', PROPERTY_NAME.VISIBLE, 'false');
		    setStyle('TSLM_SIGACC_REFER_TYPE', PROPERTY_NAME.VISIBLE, 'false');
		    setStyle('table66_SEQNO','disable','true');
		    setStyle('REFF_DESC','disable','true');
		    setStyle('USER_DESC','disable','false');
		    setStyle('STATUS','disable','false');   
		    setStyle('CLOSED_BY','disable','true');
		    setStyle('CLOSED_DATE','disable','true');
	
	}	if(controlId=='Doc_Review_RefID' && action=='M'){
		
		    setStyle('TSLM_DOCREV_REFER_ID', PROPERTY_NAME.VISIBLE, 'false');
		    setStyle('TSLM_DOCREV_REFER_TYPE', PROPERTY_NAME.VISIBLE, 'false');
		    setStyle('table68_Seqno','disable','true');
		    setStyle('REFF_DESC','disable','true');
		    setStyle('USER_DESC','disable','false');
		    setStyle('STATUS','disable','false');   
		    setStyle('CLOSED_BY','disable','true');
		    setStyle('CLOSED_DATE','disable','true')
		
		
	}	if(controlId=='LIMIT_REFERRAL_ID' && action=='M'){
		
		    setStyle('TSLM_LMTCRE_REFER_ID', PROPERTY_NAME.VISIBLE, 'false');
		    setStyle('TSLM_LMTCRE_REFER_TYPE', PROPERTY_NAME.VISIBLE, 'false');
		    setStyle('table69_SEQNO','disable','true');
		    setStyle('REFF_DESC','disable','true');
		    setStyle('USER_DESC','disable','false');
		    setStyle('STATUS','disable','false');   
		    setStyle('CLOSED_BY','disable','true');
		    setStyle('CLOSED_DATE','disable','true')
	
		
	}	
	sufficentBal(controlId);
} 


function disableTabCounter(){
    var reqCat = getValue(REQUEST_CATEGORY);
    var reqType = getValue(REQUEST_TYPE);
    if(('IFCPC' == reqCat ) && ('SIF' == reqType || 'APPIF' == reqType))
	{
		setTabStyle("tab1",0, "visible", "false");
	   setTabStyle("tab1",1, "visible", "false");
	   setTabStyle("tab1",4, "visible", "false");
	   setTabStyle("tab1",5, "visible", "false");
	   setTabStyle("tab1",6, "visible", "false");
	   selectSheet("tab1",2);
	   setStyle('FRM_COUNTER_PARTY',PROPERTY_NAME.VISIBLE,'false');

	}				
}

function disableOnFormIFCPC(){
    var reqCat = getValue(REQUEST_CATEGORY);
    var reqType = getValue(REQUEST_TYPE);
    if(('IFCPC' != reqCat ) && ('SIF' != reqType || 'APPIF' != reqType))
	{
	   setStyle('frame83',PROPERTY_NAME.VISIBLE,'false');
	   setStyle('frame84',PROPERTY_NAME.VISIBLE,'false');

	}				
}


	
/*
 * function grntThirdPartyPPM(){ var reqType=getValue(REQUEST_TYPE); //var
 * productType=getValue(PRODUCT_TYPE); var reqCat=getValue(REQUEST_CATEGORY);
 * if(reqCat == 'GRNT' && reqType == 'NI'){
 * if(getValue(COMBOX_PRODUCT_TYPE).startsWith("T5")){
 * setEmptyCombo(TRN_THIRD_PARTY,"2");
 * 
 * }else{ setEmptyCombo(TRN_THIRD_PARTY,"");
 *  } } }
 */


function counterPartyApproval(){
    var reqCat = getValue(REQUEST_CATEGORY);
    var reqType = getValue(REQUEST_TYPE);
	var processType = getValue(PROCESS_TYPE);
		
    if(('IFCPC' == reqCat ) && ('SIF' == reqType || 'APPIF' == reqType)){		
	 setStyle('FRM_VALIDATION_LIMIT',PROPERTY_NAME.VISIBLE,'false');
	}
	if (('BAU' == processType) && ('IFCPC' == reqCat ) && ('SIF' == reqType || 'APPIF' == reqType)){
		setValue('LMTCRE_REF_TO','RM');
		setValue('table7_excp_remarks','Counter Party Creation Approval Required as per DLA');

	} else if (('TSLM Front End' == processType) && ('IFCPC' == reqCat ) && ('SIF' == reqType || 'APPIF' == reqType)){
		//setValue('LMTCRE_APP_AVL_PPM','1');
		setValues({['LMTCRE_APP_AVL_PPM']: '1'}, true);
	}					
}

function supportDoc(){
     if (getValue('INF_CHARGE_ACC_NUM') == '') {	
		 setValues({['BILL_CUST_HLDING_ACC_US']: '2'}, true);	
	}else{
		setValues({['BILL_CUST_HLDING_ACC_US']: '1'}, true);
		}
}
function sufficentBal(tableId){
	
	 if('TSLM Front End'== getValue(PROCESS_TYPE) ){
		 executeServerEvent(tableId, 'click', '', false);
  	 }
 }
 
  function listViewPastDue(){
	  var GridCount = getGridRowCount('LIMIT_REFERRAL_ID');
		for(var count=0;count<GridCount;count++){					
			if("PAST DUE"==getValueFromTableCell('LIMIT_REFERRAL_ID',count,3)){

			setValues({['PAST_DUE_LIABILITY']: 'Yes'}, true); //ATP-463 --20-JUN-2024 --JAMSHED
			}
		}
 }
 
 function productType(){
	var reqCat = getValue(REQUEST_CATEGORY);
    var reqType = getValue(REQUEST_TYPE);
	var processType = getValue(PROCESS_TYPE);
	var  TranCurrency  =  getValue(TRANSACTION_CURRENCY);
	if ('IFA'== reqCat && 'IFA_CTP' == reqType &&  'TSLM Front End'== processType )
	{
		 var product = getValue('TRANSACTION_REFERENCE');
	    var ProType = product.substring(3,7);
		setValues({['PRODUCT_TYPE']: ProType}, true);	
	}
	   
   }
  
  function onChangeReferralStatus(){
	  console.log('In onChangeReferralStatus');
	  
	  var status=getValue('STATUS');
		
	  if(status=='Active'){
		  setValue('TSLM_SIGACC_DEL_FLAG_ID','N');
		  setValue('TSLM_DOCREV_DEL_FLAG_ID','N');
		  setValue('TSLM_LMTCRE_DEL_FLAG_ID','N');
	  }else{
		  setValue('TSLM_SIGACC_DEL_FLAG_ID','Y');
		  setValue('TSLM_DOCREV_DEL_FLAG_ID','Y');
		  setValue('TSLM_LMTCRE_DEL_FLAG_ID','Y');
	  }
	
  }
  
  function onChangeReferralTo(){
	 console.log('In onChangeReferralTo');
	 var refer = getValue('REFF_TO');
	 console.log('In onChangeReferralTo' +event.target.id);
	 if(getValueFromTableCell('Qvar_Doc_Rvw',19,1)=='No' && getValue(REQUEST_TYPE)=='ELCB_BL' && (refer=='Customer' || refer=='Customer Only Through Email') && getValue('CUST_INSTR')=='Docs to be checked'){
		setValue('USER_DESC','We refer to the above documents and have observed the following discrepancy/cies. Kindly let us know whether any amendment has been called for the credit or alternatively rectify the discrepancy/cies or authorize us to forward the documents on approval basis only to Issuing Bank for acceptance of the discrepancies by the applicant.Your document is refused as per article 16 of UCP600.Document held with us entirely at your risk and responsibility, awaiting your further disposal instructions.');
	  }else{
		  setValue('USER_DESC','');
	  }
	 
	executeServerEvent(event.target.id,'change', refer, true);
	 
  }
 
 function onChangeReferal(){
	 console.log('In oonChangeReferralCode');
 } 
 //ADDED  26-10-2023 FOR SCF CHANGES  ATP -185,186,187
 function enableDisableFieldsPPM_SCF_PD()
{
	var processType=getValue(PROCESS_TYPE);
	if(processType == 'BAU'){
		hideControls(PPM_SCF_PD_BAU_HIDE);
		disableFieldOnDemand(PPM_SCF_PD_BAU_DISABLE);
		enableFieldOnDemand(PPM_SCF_PD_BAU_ENABLE);
		showControls('SEC_TSLM_CUST_SPECIAL_INST');

	} 
	else if(processType == 'TSLM Front End'){
		disableFieldOnDemand(PPM_SCF_PD_TSLM_DISABLE); //ATP-409 SHivanshu
		showControls(PPM_SCF_PD_TSLM_FRONTEND_SHOW); 
		hideControls(PPM_SCF_PD_TSLM_FRONTEND_HIDE);
		hideControls(PPM_SCF_PD_TSLM_FRONTEND_FRAMES_HIDE);
	}
} 

//ATP - 199   for SCF setting default values By reyaz
function setValueSCF_PD(){
	var processType=getValue(PROCESS_TYPE);
//	setValues({['BILL_CUST_HLDING_ACC_US']: '1'}, true);
	setValues({[TSLM_STANDALONE_LOAN]: '2'}, true);
	setValues({['DOC_REV_SUCC_PPM']: '3'}, true); //ATP 40
	setValues({['LMTCRE_APP_AVL_PPM']: '3'}, true); //ATP 42
	if(processType == 'TSLM Front End'){
		setValues({['BRANCH_CITY']: 'DXB'}, true);
		setValues({['ASSIGNED_CENTER']: 'DXB'}, true);
	}
}
function setValueSTL(){
	var processType=getValue(PROCESS_TYPE);
	 var reqType = getValue(REQUEST_TYPE);
	if(processType == 'TSLM Front End' && reqType=='STL'){
	setValues({['DOC_REV_SUCC_PPM']: '3'}, true); 
	setValues({['LMTCRE_APP_AVL_PPM']: '3'}, true); 
	}
}

//ATP-383 13-FEB-24 JAMSHED

function showFieldsPPM_ILC_NI()
{
	var processType=getValue(PROCESS_TYPE);
	if(processType == 'PT'){
		showControls(PPM_ILC_NI_PT_SHOW);
	}
} 
//ATP-383 13-FEB-24 JAMSHED ends
