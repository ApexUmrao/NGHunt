function onInitiatorLoad() {
	loadValidation();
	var workstepName = getWorkItemData('ActivityName');
	var userName = getWorkItemData('userName');
	setValue('USER_ID', userName);
	/* by mohit commented on 26-08-2024 to handle Uncaught Error: You must initialize autoNumeric('init', {options}) prior to calling the 'get' method + not required.
	var workstepName = getWorkItemData('ActivityName');
	var wiName = getWorkItemData('processInstanceId');
	var coustmerRarocApp = getValue('Q_NG_RAROC_CUSTOMER_Customer_RAROC_Approved');
	var coustmerSegmentApp = getValue('Q_NG_RAROC_CUSTOMER_Business_segment_Approved');
	var coustmerRarocRea = getValue('Q_NG_RAROC_CUSTOMER_Customer_RAROC_Realized');
	var coustmerSegmentRea = getValue('Q_NG_RAROC_CUSTOMER_Business_segment_Realized');
	var coustmerRarocPro = getValue('Q_NG_RAROC_CUSTOMER_Customer_RAROC_Proposed');
	var coustmerSegmentPro = getValue('Q_NG_RAROC_CUSTOMER_Business_segment_Proposed');
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	var userName = getWorkItemData('userName');
	setValue('USER_ID', userName);
	if ((coustmerRarocApp == '' || coustmerRarocApp == 'null') && (coustmerSegmentApp == '' || coustmerSegmentApp == 'null')) {
		var controlValues = ['No Data found'];
		setMultipleFieldSingleValues(customerPrevAppr, controlValues);
	}end */ /*if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking') {  //BU
		hideControls(customerSensetized);
		hideControls(customerSen);
		hideControls(customerApprove);
		hideControls(customerApp);
		hideControls(facilitySensetized);
		hideControls(facilityApprove);
		setStyle("Q_NG_RAROC_GROUP_Group_Sensitized", "visible", "false");
		setStyle("Q_NG_RAROC_GROUP_Approved", "visible", "false");
		setStyle("Group_Sansitized", "visible", "false");
		setStyle("Group_Appr", "visible", "false");
	} if (stageId == 'CSC_MRA_Prep' || stageId == 'HOCC_Assign' || stageId == 'CA_Raise Queries to RM' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation'
		|| stageId == 'CC' || stageId == 'CA_FRS_Prep' || stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver'
		|| stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver' || stageId == 'Credit' || stageId == 'CSU' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver') {
		hideControls(customerApprove);
		disableControls(customerProposed);
		hideControls(facilityApprove);
		disableControls(facilityProposed);
	} */executeServerEvent(FORM, EVENT_TYPE.LOAD, '', false);

	saveWorkItem();
}


function handleInitiatorEvent(event) {
	console.log('Event:>>>> ' + event.type);
	console.log('Event: ' + event.target.id + ', ' + event.type);

	if (event.type == EVENT_TYPE.CLICK) {
		console.log('inside click Event: ');
		clickInitiatorEvent(event);
	} else if (event.type == EVENT_TYPE.CHANGE) {
		changeInitiatorEvent(event);
	} else if (event.type == EVENT_TYPE.LOSTFOCUS) {
		lostFocusInitiatorEvent(event);
	}
}

function clickInitiatorEvent(event) {
	console.log('inside click Event: ');
	console.log('inside  --- Event:>>> ' + event.target.id);
	if (event.target.id == CUSTOMER_REALISED) {
		console.log('inside ------click Event:>>>> ' + event.target.id);
		executeServerEvent(event.target.id, event.type, '', false);
		//window.parent.refreshWorkitem();
	}
		if (event.target.id == PROJECTED_DETAILS) {
		console.log('inside --PROJECTED_DETAILS----click Event:>>>> ' + event.target.id);
		var stageId = getValue('CURRENT_WORKSTEPNAME');
		if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
			|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking'){
		var value = "LEAD_REF_NO#T24 Customer ID$CUSTOMER_NAME#Customer Name$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal Rating$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed#Sales Turnover$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID#Group ID$Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name#Group Name";
		if(validateBlank(value)){			
		executeServerEvent(event.target.id, event.type, '', false);
		}
		}
		else if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep'
			|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver' || stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'
			|| stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign'){
		var value = "LEAD_REF_NO#T24 Customer ID$CUSTOMER_NAME#Customer Name$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_sales_turnover_Sensitized#Sales Turnover$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business segment$Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID#Group ID$Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name#Group Name";
		if(validateBlank(value)){			
		executeServerEvent(event.target.id, event.type, '', false);
		}
		}
	}
	if (event.target.id == 'onButtonClick') {
		clearTable("LV_LoanDetailsAPI",true);
		clearTable("LV_REPAYMENT_DETAILS",true);
		executeServerEvent('onButtonClick', event.type, '', false);
	}

	if (event.target.id == 'FACILITY_BUTTON_MODIFY') {
		var facilityId = getValue('F_FACILITY_ID');
		var stageId = getValue('CURRENT_WORKSTEPNAME');
		var facilityAmtProp = getValue('F_Facility_Amount_Proposed');
		var facilityAmtPropValue = "F_Facility_Amount_Proposed#Facility Amount";
		var facilityAmtSens = getValue('F_Facility_Amount_Sensitized');
		var facilityAmtSensValue = "F_Facility_Amount_Sensitized#Facility Amount";
		if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
			|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking'){
				console.log("Inside modify btn!!!!!!");
			//09-09-2024 commented by mohit to make all fields mandatory 
			var facility_type = getValue('F_FACILITY_TYPE');//edit by mohit 16-09-2024 to make index key, index desc , index tenor unit mandatory in case of funded
			if ('Funded' == facility_type){
				console.log('inside facility_type == Funded');
				var value = "F_COLLATERAL_NAME_PROPOSED#COLLATERAL_NAME$F_COLLATRAL_TYPE_PROPOSED#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_PROPOSED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_PROPOSED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_PROPOSED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_PROPOSED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_PROPOSED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$F_Repayment_Frequency_Proposed#Repayment Frequency$F_Moratorium_Period_Proposed#Moratorium Period$F_Repayment_Type_Proposed#Repayment Type$Currency_Proposed#Currency$F_Utilization_Proposed#Utilization$Q_NG_RAROC_CUSTOMER_customer_name_Proposed#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed#Counterparty Type$F_Tenor_Proposed#Tenor (Months)$F_Expiry_Date_Proposed#Expiry Date$F_Cash_Margin_Proposed#Cash Margin$F_Value_Proposed#Value$F_INDEX_KEY_PROPOSED#INDEX KEY$F_Index_Proposed#Index$F_INDEX_TENOR_PROPOSED#INDEX TENOR";
			}else{
			var value = "F_COLLATERAL_NAME_PROPOSED#COLLATERAL_NAME$F_COLLATRAL_TYPE_PROPOSED#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_PROPOSED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_PROPOSED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_PROPOSED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_PROPOSED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_PROPOSED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$F_Repayment_Frequency_Proposed#Repayment Frequency$F_Moratorium_Period_Proposed#Moratorium Period$F_Repayment_Type_Proposed#Repayment Type$Currency_Proposed#Currency$F_Utilization_Proposed#Utilization$Q_NG_RAROC_CUSTOMER_customer_name_Proposed#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed#Counterparty Type$F_Tenor_Proposed#Tenor (Months)$F_Expiry_Date_Proposed#Expiry Date$F_Cash_Margin_Proposed#Cash Margin$F_Value_Proposed#Value";
			}
			/*var value = "Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed#Counterparty Type$Q_NG_RAROC_CUSTOMER_Income_CASA_Proposed#Average Annual CASA Balance$Q_NG_RAROC_CUSTOMER_Export_Income_Proposed#Export LC income$Q_NG_RAROC_CUSTOMER_FX_Income_Proposed#FX income$Q_NG_RAROC_CUSTOMER_Other_Income_Proposed#Other income$Q_NG_RAROC_CUSTOMER_cross_sell_incm_Proposed#Cross selling income$Q_NG_RAROC_CUSTOMER_customer_name_Proposed#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed#Customer Rating$Q_NG_RAROC_CUSTOMER_total_funded_aed_Proposed#Total facility amount Funded$Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Proposed#Total facility amount (Non funded)$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed#Commitment Fees/ Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_External_Code_Proposed#External Rating Code$Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed#Sales Turnover$Currency_Proposed#Currency$F_Facility_Amount_Proposed#Facility Amount$F_Eqv_AED_Amount_Proposed#Eqv.AED Amount$F_Utilization_Proposed#Utilization$F_Tenor_Proposed#Tenor (Months)$F_Expiry_Date_Proposed#Expiry Date$F_Cash_Margin_Proposed#Cash Margin$F_Outstanding_Amount_Proposed#Outstanding Amount$F_Pricing_Proposed#Pricing$F_INDEX_KEY_Proposed#Index Key$F_Margin_Commision_Proposed#Margin/Commision$F_Value_Proposed#Value$F_Minimum_Proposed#Minimum$F_Maximum_Proposed#Maximum$F_Rep_Freq_Proposed#Repricing frequencySpc_Cost_Proposed#Special cost of fund$F_Moratorium_Period_Proposed#Moratorium Period$F_Repayment_Type_Proposed#Repayment Type$F_Repayment_Frequency_Proposed#Repayment Frequency$F_FTP_OVR_PROPOSED#FTPOverride";*/
			if(!validateBlank(facilityAmtPropValue)){
				return false;
			}else if(facilityAmtProp !='0'){
				if(!validateBlank(value))
					{
						return false;
					}
				var IsvalueZero= "F_Utilization_Proposed#Utilization";
				if(!validateValueGreaterThanZero(IsvalueZero))
					{
						return false;
					}
			}
			
				//validateBlank(value);
			//start edit by mohit 01-07-2024 to make INCOME ABSOLUTE or INCOME PERCENTAGE mandatory based on value selected from INCOME TYPE combo
			var fIncomeType = getValue('F_INCOME_TYPE');
			var fIncomeAbs = getValue('F_INCOME_ABSOLUTE');
			var fIncomePerc = getValue('F_INCOME_PERCENTAGE');
			if (fIncomeType == 'Absolute Income' &&(fIncomeAbs == ''||fIncomeAbs == null)){
				console.log('inside fIncomeType == Absolute Income');
				var fIncomeAbsValue ="F_INCOME_ABSOLUTE#INCOME_ABSOLUTE";
				if(!validateBlank(fIncomeAbsValue))
				{
					return false;
				}
				//validateBlank(fIncomeAbsValue);
			}
			 if(fIncomeType == 'Percentage' &&(fIncomePerc == ''||fIncomePerc == null)){
				console.log('inside fIncomeType == Percentage');
				var fIncomePercValue ="F_INCOME_PERCENTAGE#INCOME_PERCENTAGE";
				if(!validateBlank(fIncomePercValue))
				{
					return false;
				}
				//validateBlank(fIncomePercValue);
			}
			//end edit by mohit 01-07-2024  to make INCOME ABSOLUTE or INCOME PERCENTAGE mandatory based on value selected from INCOME TYPE combo
			 var treasuryProduct = getValue('Treasury_Prod');
				if('Yes' == treasuryProduct){
				console.log('inside fIncomeType == Percentage');
				var valueMarketRisk ="$F_Mar_Ris_Proposed#Market Risk";
				if(!validateBlank(valueMarketRisk))
				{
					return false;
				}
				//validateBlank(valueMarketRisk);
			}
        }
	//	else if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep'
	//		|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver' || stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'
	//		|| stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'CA_HOCC_Analysis'){
				
	//			var value = "F_COLLATERAL_NAME_SENSITIZED#COLLATERAL_NAME$F_Collateral_Type_Sensitized#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_SENSITIZED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_SENSITIZED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_SENSITIZED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_SENSITIZED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_SENSITIZED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$F_INCOME_PERCENTAGE#INCOME_PERCENTAGE$F_INCOME_ABSOLUTE#INCOME_ABSOLUTE$F_FTP_SENSITIZED#FTP_RATE$Repay_Freq_Sensitized#Repricing Frequency$F_Mar_Ris_Sensitized#Market Risk$Exp_Ret_Sensitized#Expected Return$F_Credit_Sensitized#Credit Limit$F_Repayment_Frequency_Proposed#Repayment Frequency$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$F_Currency_Sensitized#Currency";
	//			validateBlank(value);
				
	//		}
		//start 27-08-2024 shikha
		else if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep'
			|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver' || stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'
			|| stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign'){
			//09-09-2024 commented by mohit to make all fields mandatory 
			var facility_type = getValue('F_FACILITY_TYPE');
				if ('Funded' == facility_type){
					console.log('inside facility_type == Funded sens');
					var value = "F_COLLATERAL_NAME_SENSITIZED#COLLATERAL_NAME$F_Collateral_Type_Sensitized#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_SENSITIZED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_SENSITIZED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_SENSITIZED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_SENSITIZED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_SENSITIZED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$Repay_Freq_Sensitized#Repayment Frequency$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$F_Currency_Sensitized#Currency$Q_NG_RAROC_CUSTOMER_customer_name_Sensitized#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_sensitized#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_sensitized#Counterparty Type$F_Tenor_Sensitized#Tenor (Months)$F_Expiry_Date_Sensitized#Expiry Date$F_Cash_Margin_Sensitized#Cash Margin$F_Value_Sensitized#Value$F_INDEX_KEY_SENSITIZED#INDEX KEY$F_Index_Sensitized#Index$F_INDEX_TENOR_SENSITIZED#INDEX TENOR$F_Utilization_Sensitized#Utilization";
				}
				else{
				var value = "F_COLLATERAL_NAME_SENSITIZED#COLLATERAL_NAME$F_Collateral_Type_Sensitized#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_SENSITIZED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_SENSITIZED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_SENSITIZED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_SENSITIZED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_SENSITIZED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$Repay_Freq_Sensitized#Repayment Frequency$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$F_Currency_Sensitized#Currency$Q_NG_RAROC_CUSTOMER_customer_name_Sensitized#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_sensitized#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_sensitized#Counterparty Type$F_Tenor_Sensitized#Tenor (Months)$F_Expiry_Date_Sensitized#Expiry Date$F_Cash_Margin_Sensitized#Cash Margin$F_Value_Sensitized#Value$F_Utilization_Sensitized#Utilization";
				}
			/*var value = "Q_NG_RAROC_CUSTOMER_Business_segment_Sensitized#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Sensitized#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Sensitized#Counterparty Type$Q_NG_RAROC_CUSTOMER_Income_CASA_Sensitized#Average Annual CASA Balance$Q_NG_RAROC_CUSTOMER_Export_Income_Sensitized#Export LC income$Q_NG_RAROC_CUSTOMER_FX_Income_Sensitized#FX income$Q_NG_RAROC_CUSTOMER_Other_Income_Sensitized#Other income$Q_NG_RAROC_CUSTOMER_cross_sell_incm_Sensitized#Cross selling income$Q_NG_RAROC_CUSTOMER_customer_name_Sensitized#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized#Customer Rating$Q_NG_RAROC_CUSTOMER_total_funded_aed_Sensitized#Total facility amount Funded$Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Sensitized#Total facility amount (Non funded)$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized#Commitment Fees/ Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_External_Code_Sensitized#External Rating Code$Q_NG_RAROC_CUSTOMER_sales_turnover_Sensitized#Sales Turnover$F_Currency_Sensitized#Currency$F_Facility_Amount_Sensitized#Facility Amount$F_Eqv_AED_Amount_Sensitized#Eqv.AED Amount$F_Utilization_Sensitized#Utilization$F_Tenor_Sensitized#Tenor (Months)$F_Expiry_Date_Sensitized#Expiry Date$F_Cash_Margin_Sensitized#Cash Margin$Outs_Amnt_Sensitized#Outstanding Amount$F_Pricing_Sensitized#Pricing$F_INDEX_KEY_Sensitized#Index Key$F_Margin_Commision_Sensitized#Margin/Commision$F_Value_Sensitized#Value$F_Minimum_Sensitized#Minimum$F_Maximum_Sensitized#Maximum$Rep_Freq_Sensitized#Repricing frequencySpc_Cost_Sensitized#Special cost of fund$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$Repay_Freq_Sensitized#Repayment Frequency$F_FTP_OVR_Sensitized#FTPOverride";*/
			console.log('validating sens values!!!!!');
			if(!validateBlank(facilityAmtSensValue)){
				return false;
			}else if(facilityAmtSens !='0'){
			if(!validateBlank(value))
				{
					return false;
				}
			//edit by mohit 11-12-2024 IsvalueZero
			var IsvalueZero= "F_Utilization_Sensitized#Utilization";
			if(!validateValueGreaterThanZero(IsvalueZero))
				{
					return false;
				}
			}
			//validateBlank(value);
			
			}
		//end 27-08-2024 shikha 
		
		executeServerEvent('FACILITY_BUTTON_MODIFY', event.type, facilityId, false);
		
	}

	saveWorkItem();
}



function changeInitiatorEvent(event) {
	var workstepName = getWorkItemData('ActivityName');
	console.log('workstepName changeeeeeeeeeeeee' + workstepName);
	var wiName = getWorkItemData('processInstanceId');
	console.log('wiName chsnge' + wiName);
 
 if (event.target.id == "Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID") {
		console.log('inside ------click Event:>>>> ' + event.target.id);
		executeServerEvent(event.target.id, event.type, '', false);
		//window.parent.refreshWorkitem();
	}
/*	var treasuryProducet = getValue('Q_NG_RAROC_FACILITY_Treasury_Products');
	console.log('treasuryProducet' + treasuryProducet);
	if (treasuryProducet == 'Yes') {
		//	setStyle('Q_NG_RAROC_FACILITY_T24_Customer_ID', PROPERTY_NAME.DISABLE, 'true');
		//	setStyle('Q_NG_RAROC_FACILITY_T24_Customer_ID', PROPERTY_NAME.VISIBLE, 'false');
		document.getElementById('Prod_desc').innerHTML = 'Facility Name';
		document.getElementById('Product').innerHTML = 'Facility Type';
		//document.getElementById('').innerHTML='Principal Account Currency';
		//document.getElementById('').innerHTML='Interest/Profit Account Number';

	} 
	else{
		document.getElementById('Prod_desc').innerHTML = 'Product Description';
		document.getElementById('Product').innerHTML = 'Product';
	}*/

}

function onClickTabInitiator(tabId, sheetindex, eventCall) {
	console.log('sheetIndex' + sheetindex);
	
	   //TANU
     if(sheetindex == 1){
	  	setValue('Q_NG_RAROC_CUSTOMER_Security_Level','CUSTOMER');
		executeServerEvent(SHEET_INDEX_1,EVENT_TYPE.CLICK,'', false);

	}
     if(sheetindex == 2){       //Added by shikha for group Tab 11-09-2024
    	 executeServerEvent(SHEET_INDEX_2, EVENT_TYPE.CLICK, '', false);
    	 console.log("sheetIndexChange2...");
     }
	if (sheetindex == 3) {
		
		executeServerEvent(SHEET_INDEX_3, EVENT_TYPE.CLICK, '', false);
		console.log("before sheetIndexChange3...");
		refreshFrame('FACILITY_DETAILS',true);//edit by mohit 24-06-2024 to refresh the data on facility tab.
		console.log("after sheetIndexChange3...");
		/*	var recommdPrevAppr= getValue('Q_NG_RAROC_FACILITY_TAB_Recommd_Approved');
			var currcyPrevAppr= getValue('Q_NG_RAROC_FACILITY_TAB_Currcy_Approved');
			var recommdRealized= getValue('Q_NG_RAROC_FACILITY_TAB_Recommd_Realized');
			var recommdProp= getValue('Q_NG_RAROC_FACILITY_TAB_Recommd_Proposed');
			if (recommdPrevAppr=='' && currcyPrevAppr == ''){
			   var controlValues=['No Data found'];
			   setMultipleFieldSingleValues(faclityPrevAppr,controlValues);
			  } else if (recommdPrevAppr !='' && currcyPrevAppr != ''){
					setMultipleFieldValues(faclityPrevAppr,facilityApproved);	
			  } if(recommdRealized!=''){
					setMultipleFieldValues(facilityProposed,facilityRealized);
			  } if(recommdProp!=''){
					setMultipleFieldValues(facilitySensitized,facilityProposedValue);
				}
			*/
	}
	
//	if(sheetindex == 4){       //Added by shikha for Comment History Tab 06-08-2024
//		executeServerEvent(SHEET_INDEX_4, EVENT_TYPE.CLICK, '', false);
//		console.log("before sheetIndexChange4...");
//		refreshFrame('COMMENT_HISTORY',true);
//		console.log("after sheetIndexChange4...");
//	}
}
function lostFocusInitiatorEvent(event) {

}

/*function onClickTabInitiator(tabId,sheetIndex,eventCall){
	var input = event.target.innerHTML+','+sheetIndex;
	if(eventCall == 2) {
		executeServerEvent('tabClick', EVENT_TYPE.CLICK, input, false);
	}
}*/

function saveAndNextPreHookIntiator(tabid) {
	var input = event.target.innerHTML + ',' + getSheetIndex(tabid);
	/*	console.log('input saveAndNextPreHookFBMaker: ' + input);
		var response = executeServerEvent('saveNextTabClick', EVENT_TYPE.CLICK, input, true);
		console.log('save and next response:: '+response);
		if(response != undefined && response != ''){
			var jsondata = handleAOResponse(response);
			if (!jsondata.success){
				return false;
			}
		}
		executeServerEvent('afterSaveNext', EVENT_TYPE.CLICK, input, false);
		return true;*/
}
function onCellChangeRaroc(rowIndex, colIndex, ref, controlId) {
	if (controlId == "table27") {
		getFDValueCalculation()
	}
}
function postServerEventHandlerRaroc(controlName, eventType, responseData) {
try
	{
	if (responseData != '' && responseData != 'undefined') {
		console.log("Control Name : " + controlName + ", Event Type : " + eventType
			+ " ,Response Data : " + responseData);
		// var msg = getReturnMessage(true);
		var obj = JSON.parse(responseData);
		var value = obj.data;
		var successvar = obj.success;
		var msgResponse = obj.message;

	}
	
	}
	catch(e)
	{
	
	}
	//var msgResponse = ""; 
	 if(controlName == FORM){
		 stageID();
	 }
	 
	if (controlName == 'sheetIndexChange1') {
		saveWorkItem();
	}
	//console.log(" before msgResponse" + msgResponse);
	if (controlName == CUSTOMER_REALISED) {
		saveWorkItem();
		console.log("inside CUSTOMER_REALISED");
		refreshFrame('SEC_GROUP',true);
		if (msgResponse == 'Success') {
			showMessage("", msgResponse, "error");			
		}
		else {
			showMessage("", msgResponse, "error");
		}
	}
		if (controlName == PROJECTED_DETAILS) {
		saveWorkItem();
			showMessage("", msgResponse, "error");
	}
	if (controlName == 'onRowClick') {
		$("#F_FACILITY_TYPE").trigger("change");
		if (msgResponse == 'Success') {
			showMessage("", msgResponse, "error");
			saveWorkItem();
			renameFields();
			
		}
		else {
			renameFields();
			showMessage("", msgResponse, "error");
		}
		
		
	}
	
	
	if(controlName == 'onButtonClick'){
		var message=responseData.indexOf("message");
		message=message+10;
		var messagepop=responseData.substring(message);
		messagepop=messagepop.substring(0,messagepop.length-2);
		showMessage("", messagepop, "error");
	}
	if(controlName == 'FACILITY_BUTTON_MODIFY'){
		console.log("postServerEvent modify #####");
		var stageId = getValue('CURRENT_WORKSTEPNAME');
		var facilityAmtProp = getValue('F_Facility_Amount_Proposed');
		var facilityAmtPropValue = "F_Facility_Amount_Proposed#Facility Amount";
		var facilityAmtSens = getValue('F_Facility_Amount_Sensitized');
		var facilityAmtSensValue = "F_Facility_Amount_Sensitized#Facility Amount";
		if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
			|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking'){
				console.log("postServerEvent modify if condition prop stage id!!!!!");
		//09-09-2024 commented by mohit to make all fields mandatory 
		var facility_type = getValue('F_FACILITY_TYPE'); //added by mohit 16-09-2024 to make index key, index desc , index tenor unit mandatory in case of funded
			if ('Funded' == facility_type){
				console.log('inside facility_type == Funded');
				var value = "F_COLLATERAL_NAME_PROPOSED#COLLATERAL_NAME$F_COLLATRAL_TYPE_PROPOSED#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_PROPOSED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_PROPOSED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_PROPOSED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_PROPOSED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_PROPOSED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$F_Repayment_Frequency_Proposed#Repayment Frequency$F_Moratorium_Period_Proposed#Moratorium Period$F_Repayment_Type_Proposed#Repayment Type$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed#Sales Turnover$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business Segment$Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID#Group ID$Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name#Group Name$F_FACILITY_ID#Facility ID$Currency_Proposed#Currency$F_Utilization_Proposed#Utilization$Q_NG_RAROC_CUSTOMER_customer_name_Proposed#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed#Counterparty Type$F_Tenor_Proposed#Tenor (Months)$F_Expiry_Date_Proposed#Expiry Date$F_Cash_Margin_Proposed#Cash Margin$F_Value_Proposed#Value$F_INDEX_KEY_PROPOSED#INDEX KEY$F_Index_Proposed#Index$F_INDEX_TENOR_PROPOSED#INDEX TENOR";
			}
			else{
		var value = "F_COLLATERAL_NAME_PROPOSED#COLLATERAL_NAME$F_COLLATRAL_TYPE_PROPOSED#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_PROPOSED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_PROPOSED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_PROPOSED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_PROPOSED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_PROPOSED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$F_Repayment_Frequency_Proposed#Repayment Frequency$F_Moratorium_Period_Proposed#Moratorium Period$F_Repayment_Type_Proposed#Repayment Type$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed#Sales Turnover$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business Segment$Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID#Group ID$Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name#Group Name$F_FACILITY_ID#Facility ID$Currency_Proposed#Currency$F_Utilization_Proposed#Utilization$Q_NG_RAROC_CUSTOMER_customer_name_Proposed#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed#Counterparty Type$F_Tenor_Proposed#Tenor (Months)$F_Expiry_Date_Proposed#Expiry Date$F_Cash_Margin_Proposed#Cash Margin$F_Value_Proposed#Value";
			}
		/*var value = "Q_NG_RAROC_CUSTOMER_Business_segment_Proposed#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed#Counterparty Type$Q_NG_RAROC_CUSTOMER_Income_CASA_Proposed#Average Annual CASA Balance$Q_NG_RAROC_CUSTOMER_Export_Income_Proposed#Export LC income$Q_NG_RAROC_CUSTOMER_FX_Income_Proposed#FX income$Q_NG_RAROC_CUSTOMER_Other_Income_Proposed#Other income$Q_NG_RAROC_CUSTOMER_cross_sell_incm_Proposed#Cross selling income$Q_NG_RAROC_CUSTOMER_customer_name_Proposed#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed#Customer Rating$Q_NG_RAROC_CUSTOMER_total_funded_aed_Proposed#Total facility amount Funded$Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Proposed#Total facility amount (Non funded)$Q_NG_RAROC_CUSTOMER_customer_country_Proposed#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed#Commitment Fees/ Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Proposed#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Proposed#External Rating$Q_NG_RAROC_CUSTOMER_External_Code_Proposed#External Rating Code$Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed#Sales Turnover$Currency_Proposed#Currency$F_Facility_Amount_Proposed#Facility Amount$F_Eqv_AED_Amount_Proposed#Eqv.AED Amount$F_Utilization_Proposed#Utilization$F_Tenor_Proposed#Tenor (Months)$F_Expiry_Date_Proposed#Expiry Date$F_Cash_Margin_Proposed#Cash Margin$F_Outstanding_Amount_Proposed#Outstanding Amount$F_Pricing_Proposed#Pricing$F_INDEX_KEY_Proposed#Index Key$F_Margin_Commision_Proposed#Margin/Commision$F_Value_Proposed#Value$F_Minimum_Proposed#Minimum$F_Maximum_Proposed#Maximum$F_Rep_Freq_Proposed#Repricing frequencySpc_Cost_Proposed#Special cost of fund$F_Moratorium_Period_Proposed#Moratorium Period$F_Repayment_Type_Proposed#Repayment Type$F_Repayment_Frequency_Proposed#Repayment Frequency$F_FTP_OVR_PROPOSED#FTPOverride";*/
	//	validateBlank(value);
	if(validateBlank(facilityAmtPropValue)){
			console.log("postServerEvent modify if condition facilityAmtPropValue validate values!!!!!!");
			var message=responseData.indexOf("message");
			message=message+10;
			var messagepop=responseData.substring(message);
			messagepop=messagepop.substring(0,messagepop.length-2);
			showMessage("",messagepop , "error");
			}else if(facilityAmtProp !='0'){
					if(validateBlank(value)){
				
						console.log("postServerEvent modify if condition prop validate values!!!!!!");
						var message=responseData.indexOf("message");
						message=message+10;
						var messagepop=responseData.substring(message);
						messagepop=messagepop.substring(0,messagepop.length-2);
						showMessage("",messagepop , "error");
			
					}
					var IsvalueZero= "F_Utilization_Proposed#Utilization";
					if(validateValueGreaterThanZero(IsvalueZero))
						{
							console.log("postServerEvent modify if condition prop validate values IsvalueZero!!!!!");
							var message=responseData.indexOf("message");
						message=message+10;
						var messagepop=responseData.substring(message);
						messagepop=messagepop.substring(0,messagepop.length-2);
						showMessage("",messagepop , "error");
						}
				}
			}
		else if (stageId == 'CA_Raise Queries to RM' || stageId == 'HOCC_Recommendation' || stageId == 'CCO_Recommendation' || stageId == 'CSC_MRA_Prep' || stageId == 'CC' || stageId == 'CA_FRS_Prep'
			|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver' || stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'
			|| stageId == 'CSU' || stageId == 'Credit' || stageId == 'CA_Review_Analysis' || stageId == 'HOCC_Assign'){
				console.log("postServerEvent modify if condition sens stage id!!!!!");
		//09-09-2024 commented by mohit to make all fields mandatory
		var facility_type = getValue('F_FACILITY_TYPE'); //added by mohit 16-09-2024 to make index key, index desc , index tenor unit mandatory in case of funded
			if ('Funded' == facility_type){
				console.log('inside facility_type == FUNDED sens');
				var value = "F_COLLATERAL_NAME_SENSITIZED#COLLATERAL_NAME$F_Collateral_Type_Sensitized#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_SENSITIZED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_SENSITIZED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_SENSITIZED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_SENSITIZED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_SENSITIZED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$Repay_Freq_Sensitized#Repayment Frequency$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_sales_turnover_Sensitized#Sales Turnover$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business Segment$Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID#Group ID$Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name#Group Name$F_FACILITY_ID#Facility ID$F_Currency_Sensitized#Currency$Q_NG_RAROC_CUSTOMER_customer_name_Sensitized#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_sensitized#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_sensitized#Counterparty Type$F_Tenor_Sensitized#Tenor (Months)$F_Expiry_Date_Sensitized#Expiry Date$F_Cash_Margin_Sensitized#Cash Margin$F_Value_Sensitized#Value$F_INDEX_KEY_SENSITIZED#INDEX KEY$F_Index_Sensitized#Index$F_INDEX_TENOR_SENSITIZED#INDEX TENOR$F_Utilization_Sensitized#Utilization";
			}
			else{
		var value = "F_COLLATERAL_NAME_SENSITIZED#COLLATERAL_NAME$F_Collateral_Type_Sensitized#COLLATRAL_TYPE$F_COLLATERAL_AMOUNT_SENSITIZED#COLLATERAL_AMOUNT$F_COLLATRAL_CURRENCY_SENSITIZED#COLLATRAL_CURRENCY$F_COLLATRAL_LIEN_AMOUNT_SENSITIZED#COLLATRAL_LIEN_AMOUNT$F_COLLATRAL_LIEN_INTEREST_SENSITIZED#COLLATRAL_LIEN_INTEREST$F_COLLATERAL_LIAN_TENURE_SENSITIZED#COLLATERAL_LIAN_TENURE$F_INCOME_NAME#INCOME_NAME$F_INCOME_TYPE#INCOME_TYPE$Repay_Freq_Sensitized#Repayment Frequency$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_sales_turnover_Sensitized#Sales Turnover$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business Segment$Q_NG_RAROC_CUSTOMER_DETAILS_Group_ID#Group ID$Q_NG_RAROC_CUSTOMER_DETAILS_Group_Name#Group Name$F_FACILITY_ID#Facility ID$F_Currency_Sensitized#Currency$Q_NG_RAROC_CUSTOMER_customer_name_Sensitized#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized#Customer Rating$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized#Commitment Fees/Upfront Fees$Q_NG_RAROC_CUSTOMER_Business_segment_sensitized#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_sensitized#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_sensitized#Counterparty Type$F_Tenor_Sensitized#Tenor (Months)$F_Expiry_Date_Sensitized#Expiry Date$F_Cash_Margin_Sensitized#Cash Margin$F_Value_Sensitized#Value$F_Utilization_Sensitized#Utilization";
			}
		/*var value = "Q_NG_RAROC_CUSTOMER_Business_segment_Sensitized#Business segment$Q_NG_RAROC_CUSTOMER_Industry_Segement_Sensitized#Industry Segement$Q_NG_RAROC_CUSTOMER_IFRS_Staging_Sensitized#IFRS Staging$Q_NG_RAROC_CUSTOMER_Counterparty_Type_Sensitized#Counterparty Type$Q_NG_RAROC_CUSTOMER_Income_CASA_Sensitized#Average Annual CASA Balance$Q_NG_RAROC_CUSTOMER_Export_Income_Sensitized#Export LC income$Q_NG_RAROC_CUSTOMER_FX_Income_Sensitized#FX income$Q_NG_RAROC_CUSTOMER_Other_Income_Sensitized#Other income$Q_NG_RAROC_CUSTOMER_cross_sell_incm_Sensitized#Cross selling income$Q_NG_RAROC_CUSTOMER_customer_name_Sensitized#Customer Name$Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized#Customer Rating$Q_NG_RAROC_CUSTOMER_total_funded_aed_Sensitized#Total facility amount Funded$Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Sensitized#Total facility amount (Non funded)$Q_NG_RAROC_CUSTOMER_customer_country_Sensitized#Risk Country Name$Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized#Commitment Fees/ Upfront Fees$Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized#Internal MRA Rating$Q_NG_RAROC_CUSTOMER_external_rating_Sensitized#External Rating$Q_NG_RAROC_CUSTOMER_External_Code_Sensitized#External Rating Code$Q_NG_RAROC_CUSTOMER_sales_turnover_Sensitized#Sales Turnover$F_Currency_Sensitized#Currency$F_Facility_Amount_Sensitized#Facility Amount$F_Eqv_AED_Amount_Sensitized#Eqv.AED Amount$F_Utilization_Sensitized#Utilization$F_Tenor_Sensitized#Tenor (Months)$F_Expiry_Date_Sensitized#Expiry Date$F_Cash_Margin_Sensitized#Cash Margin$Outs_Amnt_Sensitized#Outstanding Amount$F_Pricing_Sensitized#Pricing$F_INDEX_KEY_Sensitized#Index Key$F_Margin_Commision_Sensitized#Margin/Commision$F_Value_Sensitized#Value$F_Minimum_Sensitized#Minimum$F_Maximum_Sensitized#Maximum$Rep_Freq_Sensitized#Repricing frequencySpc_Cost_Sensitized#Special cost of fund$Rep_Revolving_Sensitized#Moratorium Period$Rep_Typ_Sensitized#Repayment Type$Repay_Freq_Sensitized#Repayment Frequency$F_FTP_OVR_Sensitized#FTPOverride";*/
		if(validateBlank(facilityAmtSensValue)){
			console.log("postServerEvent modify if condition facilityAmtSensValue validate values!!!!!!");
			var message=responseData.indexOf("message");
			message=message+10;
			var messagepop=responseData.substring(message);
			messagepop=messagepop.substring(0,messagepop.length-2);
			showMessage("",messagepop , "error");
			}else if(facilityAmtSens !='0'){
					if(validateBlank(value)){
						console.log("postServerEvent modify if condition sens validate values!!!!!");
						var message=responseData.indexOf("message");
						message=message+10;
						var messagepop=responseData.substring(message);
						messagepop=messagepop.substring(0,messagepop.length-2);
						showMessage("",messagepop , "error");
					
						}
					var IsvalueZero= "F_Utilization_Sensitized#Utilization";

					if(validateValueGreaterThanZero(IsvalueZero))
						{
							console.log("postServerEvent modify if condition sens validate values IsvalueZero!!!!!");
							var message=responseData.indexOf("message");
						message=message+10;
						var messagepop=responseData.substring(message);
						messagepop=messagepop.substring(0,messagepop.length-2);
						showMessage("",messagepop , "error");
						}
			}
		}
		
		
	}
  
						 
 
		
}
function stageID(){
	var stageId = getValue('CURRENT_WORKSTEPNAME');
	if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking') {  //BU
		hideControls(customerSensetized);
		hideControls(customerApprove);
		hideControls(facilitySensetized);
		hideControls(facilityApprove);
		hideControls(groupSensitized);
		hideControls(groupApproved);
		setStyle("Q_NG_RAROC_GROUP_Group_Sensitized", "visible", "false");
		setStyle("Q_NG_RAROC_GROUP_Approved", "visible", "false");
		setStyle("Group_Sansitized", "visible", "false");
		setStyle("Group_Appr", "visible", "false");
	}else if ( stageId == 'CA_Raise Queries to RM' ||stageId == 'HOCC_Recommendation'||stageId == 'CCO_Recommendation' ||stageId == 'CSC_MRA_Prep' || stageId == 'CC'||stageId == 'CA_FRS_Prep'|| stageId == 'HOCC_FRS_Review' || stageId == 'BCIC' || stageId == 'HOCC_Def_Waive' || stageId == 'CCO_Def_Waiver'
		|| stageId == 'CA_Def_Waiver' || stageId == 'CC_Def_Waiver'  || stageId == 'CSU'|| stageId == 'Credit'  || stageId == 'CA_Review_Analysis'||stageId == 'HOCC_Assign') { 
		hideControls(customerApprove);
		disableControls(customerProposed);
		hideControls(facilityApprove);
		disableControls(facilityProposedColumn);
		hideControls(groupApproved);
		disableControls(groupProposed);
		setStyle("Group_Appr", "visible", "false");
	}else{
			window.parent.document.getElementById('wdesk:title').innerHTML = window.parent.document.getElementById('wdesk:title').innerHTML + ' (Read Only )'
	        setTabStyleByName('tab1', 'Genaral Info', 'disable', 'true');
            setTabStyleByName('tab1', 'Customer', 'disable', 'true');            
            setTabStyleByName('tab1', 'Group', 'disable', 'true');
            setTabStyleByName('tab1', 'Facility', 'disable', 'true');
            setStyle("LV_FacilityDetails","disable","false");
	}
}


function OnListLoad(controlId,action){
	
	if(controlId = 'LV_REPAYMENT_DETAILS'){
		
		for(var i = 0 ; i < getGridRowCount('LV_FacilityDetails') ; i++){
			
			var Commitment_no = getValueFromTableCell('LV_FacilityDetails',i,105);
			if(Commitment_no != '' && Commitment_no != null){
				addItemInCombo('table29_Limit_Id',Commitment_no,Commitment_no);
			}
			
			
		}
		//start edit by mohit 21-08-2024
		var parentWorkstep = getValue("CURRENT_WORKSTEPNAME");
		var facilityId = getValue("F_FACILITY_ID");
		setValue('ReSch_facilityId',facilityId);
		var totalAmtProp = getValue("F_Eqv_AED_Amount_Proposed");
		var utilizationPercProp = getValue("F_Utilization_Proposed");
		var principalAmtProp = totalAmtProp * (utilizationPercProp/100);
		var totalAmtSens = getValue("F_Eqv_AED_Amount_Sensitized");
		var utilizationPercSens = getValue("F_Utilization_Sensitized");
		var principalAmtSens = totalAmtSens * (utilizationPercSens/100);
		
		if (parentWorkstep=='CA_Review_Analysis'
						|| parentWorkstep=='HOCC_Assign'
						|| parentWorkstep=='CA_Raise Queries to RM'
						|| parentWorkstep=='HOCC_Recommendation'
						|| parentWorkstep=='CCO_Recommendation'
						|| parentWorkstep=='CSC_MRA_Prep' || parentWorkstep=='CC'
						|| parentWorkstep=='CA_FRS_Prep'
						|| parentWorkstep=='HOCC_FRS_Review' || parentWorkstep=='BCIC'
						|| parentWorkstep=='HOCC_Def_Waive'
						|| parentWorkstep=='CCO_Def_Waiver'
						|| parentWorkstep=='CA_Def_Waiver'
						|| parentWorkstep=='CC_Def_Waiver' || parentWorkstep=='CSU'
						|| parentWorkstep=='Credit'){
		setValue('ReSch_TotalAmt',totalAmtSens);
		setValue('ReSch_PrincipalAmt',principalAmtSens);
		
		}else if (parentWorkstep=='Initiate_Proposal'
					|| parentWorkstep=='RM_Proposal_Review'
				    || parentWorkstep=='RO_Proposal_Modify'
				    || parentWorkstep=='UH_TL_Proposal_Review'
				    || parentWorkstep=='CBO_Proposal_Review'
				    || parentWorkstep=='RM_Query Resolutions'
				    || parentWorkstep=='RM_Doc_Execution'
				    || parentWorkstep=='UH_TL_Def_Waiver'
				    || parentWorkstep=='Head_Corporate_Banking'
				    || parentWorkstep=='CA_Raise Queries to RM'
				    || parentWorkstep=='HOCC_Recommendation'
				    || parentWorkstep=='CCO_Recommendation'
				    || parentWorkstep=='CSC_MRA_Prep'
				    || parentWorkstep=='CC'
				    || parentWorkstep=='CA_FRS_Prep'
				    || parentWorkstep=='HOCC_FRS_Review'
				    || parentWorkstep=='BCIC'
				    || parentWorkstep=='HOCC_Def_Waive'
				    || parentWorkstep=='CCO_Def_Waiver'
				    || parentWorkstep=='CA_Def_Waiver'
				    || parentWorkstep=='CC_Def_Waiver'
				    || parentWorkstep=='CSU'
				    || parentWorkstep=='Credit'
				    || parentWorkstep=='CA_Review_Analysis'
				    || parentWorkstep=='CA_HOCC_Analysis'){
			setValue('ReSch_TotalAmt',totalAmtProp);
			setValue('ReSch_PrincipalAmt',principalAmtProp);
		}
		//end edit by mohit 21-08-2024
	}
	
}
//Added by Shivanshu Umrao
//------------validateBlank ---------------
function validateBlank(data)
{
	//var value ='kamal#name$kumar#middle$Rajak#last';
const dataArray = data.split("$");
for(var i=0;i<dataArray.length;i++)
{
var value= dataArray[i];
const myArray = value.split("#");
console.log("validateBlank myArray:"+myArray);
	var fieldID = myArray[0];
	var val =  getValue(fieldID);
	console.log("validateBlank value:"+val);
	var msg = myArray[1];
	console.log("validateBlank msg:"+msg);
	//	console.log(msg);
	if(val == "" || val == "Select" ||val == '')
	{
		showAlertDialog("Please enter the value in "+msg,true);
		return false;
	}
}
return true;
}

//ADDED by SHIVANSHU UMRAO
function renameFields(){
	var treasuryProduct = getValue('Treasury_Prod');
	if('No' == treasuryProduct){
		document.getElementById('label93').innerHTML='Currency';
		document.getElementById('label94').innerHTML='Facility Amount';
		document.getElementById('Prod_desc').innerHTML = 'Facility Type';//initially it was  Facility Name; changes by mohit 17-06-2024
		document.getElementById('Product').innerHTML = 'Facility Name';//initially it was Facility Type; changes by mohit 17-06-2024
	}else if ('Yes' == treasuryProduct){
		document.getElementById('label93').innerHTML='Currency of Exposure';		
		document.getElementById('label94').innerHTML='Notional Amount';
		document.getElementById('Prod_desc').innerHTML = 'Product Description';
		document.getElementById('Product').innerHTML = 'Product';
	}
}
//added suraj
function selectRimNumber(){
			console.log("Inside selectRimNumber...");
   		   var customerComboRim = getValue('Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID');
   		  // setValues({ "LEAD_REF_NO": customerComboRim }, true);   		   
   		   var btns = { confirm: { label: 'Yes, Continue',className: 'btn-success'},
	             cancel: { label: 'No, Edit',className: 'btn-danger'}}
	       var callback = function(result){ if(result == true){	
	           setValues({ "LEAD_REF_NO": customerComboRim }, true); 
	           setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID","disable","true"); 
             
               
	           } 
                                                                   		      
	         else if(result == false){			
	                setValues({ "Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID": "" }, true); 
	                  }
	              }	
                             //executeServerEvent("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "change", '', false);
	        showConfirmDialog("You are selecting customer primary RIM, You will not be able to change it again,Are you sure want to continue?",btns,callback);	     
	        executeServerEvent("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID", "change", '', false);
	 
}
function  loadValidation(){
	var customerComboRim = getValue('Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID');
	  if(customerComboRim !=""){
		setStyle("Q_NG_RAROC_CUSTOMER_DETAILS_CUSTOMER_T24_ID","disable","true"); 
	}
}
function validateValueGreaterThanZero(data) //added  by mohit 11-12-2024 for utilization field.
{
	//var value ='kamal#name$kumar#middle$Rajak#last';
const dataArray = data.split("$");
for(var i=0;i<dataArray.length;i++)
{
var value= dataArray[i];
const myArray = value.split("#");
console.log("validateValueGreaterThanZero myArray:"+myArray);
	var fieldID = myArray[0];
	var val =  getValue(fieldID);
	console.log("validateValueGreaterThanZero value:"+val);
	var msg = myArray[1];
	console.log("validateValueGreaterThanZero msg:"+msg);
	//	console.log(msg);
	if(val == '0')
	{
		showAlertDialog("Please enter the value greater than zero in "+msg,true);
		return false;
	}
}
return true;
}
function collaterallNo(){
	executeServerEvent(COLLATERAL,EVENT_TYPE.CLICK,'',false);
}

////function CommentHistory(){
////	executeServerEvent(COMMENT_HISTORY, EVENT_TYPE.CLICK, '', false);
//}