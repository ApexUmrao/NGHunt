package com.newgen.iforms.user.config;

public interface Constants {

	// EVENT TYPE
	public static final String EVENT_TYPE_LOAD = "load";
	public static final String EVENT_TYPE_CLICK = "click";
	public static final String EVENT_TYPE_CHANGE = "change";
	public static final String EVENT_TYPE_LOSTFOCUS = "blur";
	public static final String EVENT_TYPE_GOTFOCUS = "focus";
	public static final String CFM_ADD_ROW = "CFM_AddRow";
	public static final String CFM_OnLoad = "CFM_OnLoad";
	public static final String CFM_GRID = "COLLATERAL_FACILITY_MAPPING_GRID";
	public static final String CFM_TABLE = "NG_RAROC_COLLATERAL_FACILITY_MAP";
	
	public static final String TAB_CUSTOMER = "sheetIndexChange1";
	public static final String TAB_GROUP = "sheetIndexChange2";
	public static final String TAB_FACILITY = "sheetIndexChange3";
	public static final String ROW_CLICK = "onRowClick";
	public static final String FACILITY_MODIFY_BTN = "FACILITY_BUTTON_MODIFY";
	public static final String ON_BUTTON_CLICK = "onButtonClick";
	public static final String COLLATERAL_ON_ROW_CLICK = "CollateralonRowClick";//collateral grid
//	public static final String COMMENT_HISTORY = "sheetIndexChange4"; //Comment_History_tab

	// Config Details
	public static final String FOLDER_CONFIG = "custom_properties";
	public static final String FILE_LOG4J_PROPERTIES = "RAROC_log4j.properties";
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy";
 	public static final String REQ_DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String LV_LOANDETAILSAPI = "LV_LoanDetailsAPI";

	// customer_ID

	public static final String Customer_ID = "Q_NG_RAROC_CUSTOMER_DETAILS_Customer_ID";
	public static final String LEAD_REF_NO = "LEAD_REF_NO";

	//

	public static final String NOLOCK = "WITH (NOLOCK)";
	public static final String NG_RAROC_API_LOGS ="NG_RAROC_AUDIT";


	// Customer
	// Field Proposed
	public static final String CUSTOMER_RAROC_PROPOSED = "Q_NG_RAROC_CUSTOMER_Customer_RAROC_Proposed";
	public static final String BUSINESS_SEGMENT_PROPOSED = "Q_NG_RAROC_CUSTOMER_Business_segment_Proposed";
	public static final String INDUSTRY_SEGEMENT_PROPOSED = "Q_NG_RAROC_CUSTOMER_Industry_Segement_Proposed";
	public static final String IFRS_STAGING_PROPOSED = "Q_NG_RAROC_CUSTOMER_IFRS_Staging_Proposed";
	public static final String COUNTERPARTY_TYPE_PROPOSED = "Q_NG_RAROC_CUSTOMER_Counterparty_Type_Proposed";
	public static final String INCOME_CASA_PROPOSED = "Q_NG_RAROC_CUSTOMER_Income_CASA_Proposed";
	public static final String EXPORT_INCOME_PROPOSED = "Q_NG_RAROC_CUSTOMER_Export_Income_Proposed";
	public static final String FX_INCOME_PROPOSED = "Q_NG_RAROC_CUSTOMER_FX_Income_Proposed";
	public static final String OTHER_INCOME_PROPOSED = "Q_NG_RAROC_CUSTOMER_Other_Income_Proposed";
	public static final String FEE_INCM_AED_PROPOSED = "Q_NG_RAROC_CUSTOMER_fee_incm_aed_Proposed";
	public static final String CROSS_SELL_INCM_PROPOSED = "Q_NG_RAROC_CUSTOMER_cross_sell_incm_Proposed";
	public static final String REALIZED_RAROC_PROPOSED = "Q_NG_RAROC_CUSTOMER_realized_raroc_Proposed";
	public static final String CUSTOMER_NAME_PROPOSED = "Q_NG_RAROC_CUSTOMER_customer_name_Proposed";
	public static final String CUST_INTRL_RATING_PROPOSED = "Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Proposed";
	public static final String TOTAL_FUNDED_AED_PROPOSED = "Q_NG_RAROC_CUSTOMER_total_funded_aed_Proposed";
	public static final String TOTAL_NONFUNDED_AED_PROPOSED = "Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Proposed";
	public static final String CUSTOMER_COUNTRY_PROPOSED = "Q_NG_RAROC_CUSTOMER_customer_country_Proposed";
	public static final String CUSTOMER_ID_PROPOSED = "Q_NG_RAROC_CUSTOMER_customer_id_Proposed";
	public static final String EXTERNAL_CODE_PROPOSED ="Q_NG_RAROC_CUSTOMER_External_Code_Proposed";
	public static final String SALES_TURNOVER_PROPOSED ="Q_NG_RAROC_CUSTOMER_sales_turnover_Proposed";
	public static final String INTERNAL_RATING_PROPOSED ="Q_NG_RAROC_CUSTOMER_internal_rating_Proposed";

	// Field Sensitized
	public static final String CUSTOMER_RAROC_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Customer_RAROC_sensitized";
	public static final String BUSINESS_SEGMENT_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Business_segment_sensitized";
	public static final String INDUSTRY_SEGEMENT_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Industry_Segement_sensitized";
	public static final String IFRS_STAGING_SENSITIZED = "Q_NG_RAROC_CUSTOMER_IFRS_Staging_sensitized";
	public static final String COUNTERPARTY_TYPE_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Counterparty_Type_sensitized";
	public static final String INCOME_CASA_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Income_CASA_sensitized";
	public static final String FD_VALUE_A_SENSITIZED = "Q_NG_RAROC_CUSTOMER_FD_Value_A_sensitized";
	public static final String FD_VALUE_B_SENSITIZED = "Q_NG_RAROC_CUSTOMER_FD_Value_B_sensitized";
	public static final String FD_VALUE_C_SENSITIZED = "Q_NG_RAROC_CUSTOMER_FD_Value_C_sensitized";
	public static final String FD_VALUE_D_SENSITIZED = "Q_NG_RAROC_CUSTOMER_FD_Value_D_sensitized";
	public static final String FD_INCOME_SENSITIZED = "Q_NG_RAROC_CUSTOMER_FD_Income_sensitized";
	public static final String EXPORT_INCOME_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Export_Income_sensitized";
	public static final String FX_INCOME_SENSITIZED = "Q_NG_RAROC_CUSTOMER_FX_Income_sensitized";
	public static final String OTHER_INCOME_SENSITIZED = "Q_NG_RAROC_CUSTOMER_Other_Income_sensitized";
	public static final String FEE_INCM_AED_SENSITIZED = "Q_NG_RAROC_CUSTOMER_fee_incm_aed_Sensitized";
	public static final String CROSS_SELL_INCM_SENSITIZED = "Q_NG_RAROC_CUSTOMER_cross_sell_incm_Sensitized";
	public static final String REALIZED_RAROC_SENSITIZED = "Q_NG_RAROC_CUSTOMER_realized_raroc_Sensitized";
	public static final String CUSTOMER_NAME_SENSITIZED = "Q_NG_RAROC_CUSTOMER_customer_name_Sensitized";
	public static final String CUST_INTRL_RATING_SENSITIZED = "Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Sensitized";
	public static final String TOTAL_FUNDED_AED_SENSITIZED = "Q_NG_RAROC_CUSTOMER_total_funded_aed_Sensitized";
	public static final String TOTAL_NONFUNDED_AED_SENSITIZED = "Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Sensitized";
	public static final String CUSTOMER_COUNTRY_SENSITIZED = "Q_NG_RAROC_CUSTOMER_customer_country_Sensitized";
//	public static final String CUSTOMER_ID_SENSITIZED = "Q_NG_RAROC_CUSTOMER_customer_id_Sensitized";
	public static final String EXTERNAL_RATING_SENSITIZED ="Q_NG_RAROC_CUSTOMER_external_rating_Sensitized";
	public static final String EXTERNAL_CODE_SENSITIZED ="Q_NG_RAROC_CUSTOMER_External_Code_Sensitized";
	public static final String SALES_TURNOVER_SENSITIZED ="Q_NG_RAROC_CUSTOMER_sales_turnover_Sensitized";
	public static final String INTERNAL_RATING_SENSITIZED ="Q_NG_RAROC_CUSTOMER_internal_rating_Sensitized";
	
	public static final String EXPECTED_YEAR_RETURN_SENSITIZED ="Exp_Ret_Sensitized";
	public static final String MARKETING_RISK_CAPITAL_SENSITIZED ="F_Mar_Ris_Sensitized";
	public static final String CREDIT_LIMIT_SENSITIZED ="F_Credit_Sensitized";
	//
	public static final String CUSTOMER_RAROC_APPROVED = "Q_NG_RAROC_CUSTOMER_Customer_RAROC_Approved";
	public static final String BUSINESS_SEGMENT_APPROVED = "Q_NG_RAROC_CUSTOMER_Business_segment_Approved";
	public static final String INDUSTRY_SEGEMENT_APPROVED = "Q_NG_RAROC_CUSTOMER_Industry_Segement_Approved";
	public static final String IFRS_STAGING_APPROVED = "Q_NG_RAROC_CUSTOMER_IFRS_Staging_Approved";
	public static final String COUNTERPARTY_TYPE_APPROVED1 = "Q_NG_RAROC_CUSTOMER_Counterparty_Type_Approved";
	public static final String INCOME_CASA_APPROVED = "Q_NG_RAROC_CUSTOMER_Income_CASA_Approved";
	public static final String EXPORT_INCOME_APPROVED = "Q_NG_RAROC_CUSTOMER_Export_Income_Approved";
	public static final String FX_INCOME_APPROVED = "Q_NG_RAROC_CUSTOMER_FX_Income_Approved";
	public static final String OTHER_INCOME_APPROVED = "Q_NG_RAROC_CUSTOMER_Other_Income_Approved";
	public static final String FEE_INCM_AED_APPROVED = "Q_NG_RAROC_CUSTOMER_fee_incm_aed_Approved";
	public static final String CROSS_SELL_INCM_APPROVED = "Q_NG_RAROC_CUSTOMER_cross_sell_incm_Approved";
	public static final String CUSTOMER_NAME_APPROVED = "Q_NG_RAROC_CUSTOMER_customer_name_Approved";
	public static final String CUST_INTRL_RATING_APPROVED = "Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Approved";
	public static final String TOTAL_FUNDED_AED_APPROVED = "Q_NG_RAROC_CUSTOMER_total_funded_aed_Approved";
	public static final String TOTAL_NONFUNDED_AED_APPROVED = "Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Approved";
	public static final String CUSTOMER_COUNTRY_APPROVED = "Q_NG_RAROC_CUSTOMER_customer_country_Approved";
	public static final String EXTERNAL_RATING_APPROVED ="Q_NG_RAROC_CUSTOMER_external_rating_Approved";
	public static final String EXTERNAL_CODE_APPROVED ="Q_NG_RAROC_CUSTOMER_External_Code_Approved";
	public static final String SALES_TURNOVER_APPROVED ="Q_NG_RAROC_CUSTOMER_sales_turnover_Approved";
	public static final String INTERNAL_RATING_APPROVED ="Q_NG_RAROC_CUSTOMER_internal_rating_Approved";

	// Field Previously Approved
	public static final String RAROC_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Customer_RAROC_Prev_Approv";
	public static final String BUSINESS_SEGMENT_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Business_segment_Prev_Approv";
	public static final String INDUSTRY_SEGEMENT_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Industry_Segement_Prev_Approv";
	public static final String IFRS_STAGING_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_IFRS_Staging_Prev_Approv";
	public static final String COUNTERPARTY_TYPE_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Counterparty_Type_Prev_Approv";
	public static final String INCOME_CASA_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Income_CASA_Prev_Approv";
	public static final String EXPORT_INCOME_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Export_Income_Prev_Approv";
	public static final String FX_INCOME_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_FX_Income_Prev_Approv";
	public static final String OTHER_INCOME_PREV_APPROV = "Q_NG_RAROC_CUSTOMER_Other_Income_Prev_Approv";
	public static final String CROSS_SELL_INCM_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_cross_sell_incm_Previously_Appr";
	public static final String REALIZED_RAROC_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_realized_raroc_Previously_Appr";
	public static final String CUSTOMER_NAME_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_customer_name_Previously_Appr";
	public static final String CUST_INTRL_RATING_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_cust_intrl_rating_Previously_Appr";
	public static final String TOTAL_FUNDED_AED_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_total_funded_aed_Previously_Appr";
	public static final String TOTAL_NONFUNDED_AED_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_total_nonfunded_aed_Previously_Appr";
	public static final String CUSTOMER_COUNTRY_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_customer_country_Previously_Appr";
	public static final String CUSTOMER_ID_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_customer_id_Previously_Appr";
	public static final String FEE_INCM_AED_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_fee_incm_aed_Previously_Appr";
	public static final String INTERNAL_RATING_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_internal_rating_Previously_Appr";
	public static final String EXTERNAL_RATING_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_external_rating_Previously_Appr";
	public static final String EXTERNAL_CODE_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_External_Code_Pre_Appr";
	public static final String SALES_TURNOVER_PREVIOUSLY_APPR = "Q_NG_RAROC_CUSTOMER_sales_turnover_Previously_Appr";

	/// FACILILTY HEADER
//	public static final String T24_CUST_ID = "F_T24_CUST_ID";
	public static final String FACILITY_TYPE = "F_FACILITY_TYPE";
	public static final String FACILITY_NAME = "F_FACILITY_NAME";
	public static final String PURP_FACILITY = "F_PURP_FACILITY";
	public static final String FACILITY_ID = "F_FACILITY_ID";
	public static final String MAIN_SUB_LIMIT = "F_MAIN_SUB_LIMIT";
	public static final String FACILITY_COMIITED = "F_COMMITTED";
	public static final String FACILITY_GENERAL = "F_GENERAL";
	public static final String FACILITY_EXPECTED = "F_EXPECTED";
	public static final String FACILITY_MAIN_LIMIT = "F_MAIN_LIMIT";
	public static final String FACILITY_COMMITMENT_NO = "F_COMMITMENT_NO";
	public static final String FACILITY_PROJECT = "F_PROJECT";
	public static final String FACILITY_SENIOR = "F_SENIOR";
	public static final String FACILITY_REVOLVING = "F_REVOLVING";
	public static final String FACILITY_TREASURY = "Treasury_Prod";
	
	
	// FACILITY REALIZED
	public static final String CURRENCY_REALIZED = "F_Currency_Realized";
	public static final String FACILITY_AMOUNT_REALIZED = "F_Facility_Amount_Realized";
	public static final String EQV_AED_AMOUNT_REALIZED = "F_Eqv_AED_Amount_Realized";
	public static final String TENOR_REALIZED = "F_Tenor_Realized";
	public static final String TENOR_UNIT_REALIZED = "F_Tenor_unit_Realized";
	public static final String EXPIRY_DATE_REALIZED = "F_Expiry_Date_Realized";
	public static final String CASH_MARGIN_REALIZED = "F_Cash_Margin_Realized";
	public static final String OUTSTANDING_AMOUNT_REALIZED = "F_Outstanding_Amount_Realized";
	public static final String PRICING_REALIZED = "F_Pricing_Realized";
	public static final String INDEX_REALIZED = "F_Index_Realized";
	public static final String INDEX_RATE_REALIZED = "F_Index_Rate_Realized";
	public static final String MARGIN_COMMISION_REALIZED = "F_Margin_Commision_Realized";
	public static final String VALUE_REALIZED = "F_Value_Realized";
	public static final String MINIMUM_REALIZED = "F_Minimum_Realized";
	public static final String MAXIMUM_REALIZED = "F_Maximum_Realized";
	
	public static final String ECL_REALISED = "F_ECL_REALIZED";
	public static final String RISK_CAPITAL_REALISED = "F_RISK_CAPITAL_REALIZED";
	public static final String RISK_ADJUSTED_RETURN_REALISED = "F_RISK_RETURN_REALIZED";
	public static final String FACILITY_RAROC_REALISED = "F_FACILITY_RAROC_REALIZED";
	public static final String AVERAGE_UTILISED_REALISED = "F_AVG_REALIZED";
	public static final String FTP_RATE_REALISED = "F_FTP_REALIZED";
	public static final String INTEREST_RATE_APPLIED_REALISED = "F_INT_RATE_REALIZED";
	//start changes by mohit 24-06-2024
	public static final String COLLATERAL_NUMBER_REALIZED = "F_Collateral_Number_Realized";
	public static final String ALLOCATION_PERCENTAGE_REALIZED = "F_Allocation_Percentage_Realized";
	public static final String INDEX_KEY_REALIZED = " F_INDEX_KEY_REALIZED";
	public static final String INDEX_TENOR_REALIZED = " F_INDEX_TENOR_REALIZED";
	public static final String INDEX_TENOR_UNIT_REALIZED = "F_INDEX_TENOR_UNIT_REALIZED";
	public static final String COMMITMENT_FEE_REALIZED = "F_COMMITMENT_FEE_REALIZED";
	//end changes by mohit 24-06-2024
	
	//changes by shikha 11-07-2024
	public static final String FTP_OVERRIDE_REALIZED = "F_FTP_OVR_REALIZED";
	public static final String UTILIZATION_REALIZED = "F_Utilization_Realized";
		
	// FACILITY PROPOSED
	public static final String CURRENCY_PROPOSED = "Currency_Proposed";
	public static final String FACILITY_AMOUNT_PROPOSED = "F_Facility_Amount_Proposed";
	public static final String EQV_AED_AMOUNT_PROPOSED = "F_Eqv_AED_Amount_Proposed";
	public static final String TENOR_PROPOSED = "F_Tenor_Proposed";
	public static final String TENOR_UNIT_PROPOSED = "F_Tenor_unit_Proposed";
	public static final String EXPIRY_DATE_PROPOSED = "F_Expiry_Date_Proposed";
	public static final String CASH_MARGIN_PROPOSED = "F_Cash_Margin_Proposed";
	public static final String OUTSTANDING_AMOUNT_PROPOSED = "F_Outstanding_Amount_Proposed";
	public static final String PRICING_PROPOSED = "F_Pricing_Proposed";
	public static final String INDEX_PROPOSED = "F_Index_Proposed";
	public static final String INDEX_RATE_PROPOSED = "F_Index_Rate_Proposed";
	public static final String MARGIN_COMMISION_PROPOSED = "F_Margin_Commision_Proposed";
	public static final String VALUE_PROPOSED = "F_Value_Proposed";
	public static final String MINIMUM_PROPOSED = "F_Minimum_Proposed";
	public static final String MAXIMUM_PROPOSED = "F_Maximum_Proposed";
	public static final String REPAYMENT_FREQUENCY_PROPOSED = "F_Repayment_Frequency_Proposed";
	public static final String MORATORIUM_PERIOD_PROPOSED = "F_Moratorium_Period_Proposed";
	public static final String REPAYMENT_TYPE_PROPOSED = "F_Repayment_Type_Proposed";
	public static final String TREASURY_PROPOSED = "F_Treasury_Proposed";
	public static final String COMMITMENT_NO_PROPOSED = "F_Commitment_No_Proposed";
	public static final String SENIORSUBORDINATE_PROPOSED = "F_SeniorSubordinate_Proposed";
	public static final String GENERAL_PROJECT_SPECIFIC_PROPOSED = "F_General_Project_Specific_Proposed";
	public static final String EXPECTED_UTILISATION_PROPOSED = "F_Expected_Utilisation_Proposed";
	public static final String MAIN_LIMIT_PROPOSED = "F_Main_Limit_Proposed";
	public static final String ECL_PROPOSED = "F_ECL_PROP";
	public static final String RISK_CAPITAL_PROPOSED = "F_RISK_CAPITAL_PROP";
	public static final String RISK_ADJUSTED_RETURN_PROPOSED = "F_RISK_RETURN_PROP";
	public static final String FACILITY_RAROC_PROPOSED = "F_FACILITY_RAROC_PROP";
	public static final String AVERAGE_UTILISED_PROPOSED = "F_AVG_PROP";
	public static final String FTP_RATE_PROPOSED = "F_FTP_PROP";
	public static final String INTEREST_RATE_APPLIED_PROPOSED = "F_INT_RATE_PROP";
	public static final String COLLATERAL_NAME_PROPOSED = "F_COLLATERAL_NAME_PROPOSED";
	public static final String COLLATRAL_TYPE_PROPOSED = "F_COLLATRAL_TYPE_PROPOSED";
	public static final String COLLATERAL_AMOUNT_PROPOSED = "F_COLLATERAL_AMOUNT_PROPOSED";
	public static final String COLLATRAL_CURRENCY_PROPOSED = "F_COLLATRAL_CURRENCY_PROPOSED";
	public static final String COLLATRAL_LIEN_AMOUNT_PROPOSED = "F_COLLATRAL_LIEN_AMOUNT_PROPOSED";
	public static final String COLLATRAL_LIEN_INTEREST_PROPOSED = "F_COLLATRAL_LIEN_INTEREST_PROPOSED";
	public static final String COLLATERAL_LIAN_TENURE_PROPOSED = "F_COLLATERAL_LIAN_TENURE_PROPOSED";
	public static final String CREDIT_LIMIT_PROPOSED = "F_Credit_Proposed";
	public static final String EXPECTED_YEAR_RETURN_PROPOSED = "F_Exp_Ret_Proposed";
	public static final String MARKETING_RISK_CAPITAL_PROPOSED = "F_Mar_Ris_Proposed";
	public static final String REPRICING_FREQUENCY_PROPOSED = "F_Rep_Freq_Proposed";
	
	public static final String COUNTERPARTY_RATE_PROPOSED = "Count_Int_Proposed";
	public static final String COUNTERPARTY_TYP_PROPOSED = "Count_Typ_Proposed";
	public static final String SPL_COST_PROPOSED = "Spc_Cost_Proposed";
	
	public static final String BORROWER_RATING_PROPOSED = "F_BORROWER_RATING_PROPOSED";
	public static final String COMMITMENT_FEE_PROPOSED = "F_COMMITMENT_FEE_PROPOSED";
	public static final String UPFRONT_FEE_PROPOSED = "F_UPFRONT_FEE_PRE_PROPOSED";
	public static final String REMAINING_TERMS_IN_MONTHS_PROPOSED = "F_REMAIN_TRM_PROP";
	
	//start changes by mohit 24-06-2024
	public static final String COLLATERAL_NUMBER_PROPOSED = "F_Collateral_Number_Proposed";
	public static final String ALLOCATION_PERCENTAGE_PROPOSED = "F_Allocation_Percentage_Proposed";
	public static final String INDEX_KEY_PROPOSED = "F_INDEX_KEY_PROPOSED";
	public static final String INDEX_TENOR_PROPOSED = "F_INDEX_TENOR_PROPOSED";
	public static final String INDEX_TENOR_UNIT_PROPOSED = "F_INDEX_TENOR_UNIT_PROPOSED";
	//end changes by mohit 24-06-2024

	//changes by shikha 11-07-24
	public static final String FTP_OVERRIDE_PROPOSED = "F_FTP_OVR_PROPOSED";
	public static final String UTILIZATION_PROPOSED = "F_Utilization_Proposed";
	
	// FACILITY SENSITIZED
	public static final String CURRENCY_SENSITIZED = "F_Currency_Sensitized";
	public static final String FACILITY_AMOUNT_SENSITIZED = "F_Facility_Amount_Sensitized";
	public static final String EQV_AED_AMOUNT_SENSITIZED = "F_Eqv_AED_Amount_Sensitized";
	public static final String TENOR_SENSITIZED = "F_Tenor_Sensitized";
	public static final String TENOR_UNIT_SENSITIZED = "F_Tenor_unit_Sensitized";
	public static final String EXPIRY_DATE_SENSITIZED = "F_Expiry_Date_Sensitized";
	public static final String CASH_MARGIN_SENSITIZED = "F_Cash_Margin_Sensitized";
	public static final String PRICING_SENSITIZED = "F_Pricing_Sensitized";
	public static final String INDEX_SENSITIZED = "F_Index_Sensitized";
	public static final String INDEX_RATE_SENSITIZED = "F_Index_Rate_Sensitized";
	public static final String MARGIN_COMMISION_SENSITIZED = "F_Margin_Commision_Sensitized";
	public static final String VALUE_SENSITIZED = "F_Value_Sensitized";
	public static final String MINIMUM_SENSITIZED = "F_Minimum_Sensitized";
	public static final String MAXIMUM_SENSITIZED = "F_Maximum_Sensitized";

	public static final String OUTS_AMNT_SENSITIZED = "Outs_Amnt_Sensitized";
	public static final String EXP_RET_SENSITIZED =  "Exp_Ret_Sensitized";
	public static final String MAR_RIS_SENSITIZED = "F_Mar_Ris_Sensitized";
	public static final String COUNT_INT_SENSITIZED = "Count_Int_Sensitized";
	public static final String COUNT_TYP_SENSITIZED = "Count_Typ_Sensitized";
	public static final String REP_FREQ_SENSITIZED = "Rep_Freq_Sensitized";
	public static final String SPC_COST_SENSITIZED = "Spc_Cost_Sensitized";
	public static final String REP_REVOLVING_SENSITIZED = "Rep_Revolving_Sensitized";
	public static final String REP_TYP_SENSITIZED = "Rep_Typ_Sensitized";
	public static final String REPAY_FREQ_SENSITIZED = "Repay_Freq_Sensitized";
	public static final String CREDIT_SENSITIZED = "F_Credit_Sensitized";
	public static final String ECL_SENSITIZED = "F_ECL_SENSITIZED";
	public static final String RISK_CAPITAL_SENSITIZED = "F_RISK_CAPITAL_SENSITIZED";
	public static final String RISK_RETURN_SENSITIZED = "F_RISK_RETURN_SENSITIZED";
	public static final String FTP_SENSITIZED = "F_FTP_SENSITIZED";
	public static final String FACILITY_RAROC_SENSITIZED = "F_FACILITY_RAROC_SENSITIZED";
	public static final String AVG_SENSITIZED = "F_AVG_SENSITIZED";
	public static final String REMAIN_TRM_SENSITIZED = "F_REMAIN_TRM_SENSITIZED";
	public static final String INT_RATE_SENSITIZED = "F_INT_RATE_SENSITIZED";
	public static final String COLLATERAL_TYPE_SENSITIZED = "F_Collateral_Type_Sensitized";
	public static final String COLLATERAL_NAME_SENSITIZED = "F_COLLATERAL_NAME_SENSITIZED";
	public static final String COLLATERAL_AMOUNT_SENSITIZED = "F_COLLATERAL_AMOUNT_SENSITIZED";
	public static final String COLLATRAL_CURRENCY_SENSITIZED = "F_COLLATRAL_CURRENCY_SENSITIZED";
	public static final String COLLATRAL_LIEN_AMOUNT_SENSITIZED = "F_COLLATRAL_LIEN_AMOUNT_SENSITIZED";
	public static final String COLLATRAL_LIEN_INTEREST_SENSITIZED = "F_COLLATRAL_LIEN_INTEREST_SENSITIZED";
	public static final String COLLATERAL_LIAN_TENURE_SENSITIZED = "F_COLLATERAL_LIAN_TENURE_SENSITIZED";
	public static final String BORROWER_RATING_SENSITIZED = "F_BORROWER_RATING_SENSITIZED";
	public static final String COMMITMENT_FEE_SENSITIZED = "F_COMMITMENT_FEE_SENSITIZED";
	public static final String UPFRONT_FEE_SENSITIZED = "F_UPFRONT_FEE_SENSITIZED";
	public static final String FACILITY_INCOME_NAME ="F_INCOME_NAME";
	public static final String FACILITY_INCOME_TYPE ="F_INCOME_TYPE";
	public static final String FACILITY_INCOME_PERCENTAGE ="F_INCOME_PERCENTAGE";
	public static final String FACILITY_INCOME_ABSOLUTE ="F_INCOME_ABSOLUTE";
	public static final String FACILITY_AVERAGE_BALANCE ="F_AVERAGE_BALANCE";
	public static final String FACILITY_FOREX_INCOME ="F_FOREX_INCOME";
	public static final String FACILITY_COMMISSION_EXPORT ="F_COMMISSION_EXPORT";
	public static final String FACILITY_OTHER_INCOME ="F_OTHER_INCOME";
	public static final String FACILITY_FIXED_DEPOSIT ="F_FIXED_DEPOSIT";
	//start changes by mohit 24-06-2024
	public static final String COLLATERAL_NUMBER_SENSITIZED = "F_Collateral_Number_Sensitized";
	public static final String ALLOCATION_PERCENTAGE_SENSITIZED = "F_Allocation_Percentage_Sensitized";
	public static final String INDEX_KEY_SENSITIZED = "F_INDEX_KEY_SENSITIZED";
	public static final String INDEX_TENOR_SENSITIZED = "F_INDEX_TENOR_SENSITIZED";
	public static final String INDEX_TENOR_UNIT_SENSITIZED = "F_INDEX_TENOR_UNIT_SENSITIZED";
		//end changes by mohit 24-06-2024
	//changes by shikha 11-07-24
	public static final String FTP_OVERRIDE_SENSITIZED = "F_FTP_OVR_SENSITIZED";
	public static final String UTILIZATION_SENSITIZED = "F_Utilization_Sensitized";
	
	// FACILITY PREVIOUSLY_APPR
	
	public static final String CURRENCY_PREV_APPR = "F_Currency_Previously_Appr";
	public static final String FACILITY_AMOUNT_PREV_APPR = "Facility_Previously_Appr";
	public static final String TENOR_PREV_APPR = "Tenor_Previously_Appr";
	public static final String TENOR_UNIT_PREV_APPR = "Tenor_Unit_Previously_Appr";
	public static final String EXPIRY_DATE_PREV_APPR = "EXP_DT_Previously_Appr";
	public static final String CASH_MARGIN_PREV_APPR = "Cash_Mar_Previously_Appr";
	public static final String OUTSTANDING_AMOUNT_PREV_APPR = "Outs_Amnt_Previously_Appr";
	public static final String PRICING_PREV_APPR = "Pricing_Previously_Appr";
	public static final String INDEX_PREV_APPR = "Index_Previously_Appr";
	public static final String INDEX_RATE_PREV_APPR = "Ind_Rat_Previously_Appr";
	public static final String MARGIN_COMMISION_PREV_APPR = "Margin_Previously_Appr";
	public static final String VALUE_PREV_APPR = "Value_Previously_Appr";
	public static final String MINIMUM_PREV_APPR = "Minim_Previously_Appr";
	public static final String MAXIMUM_PREV_APPR = "Maxim_Previously_Appr";
	public static final String EXP_1_YEAR_RATE_PREV_APPR = "Exp_Ret_Previously_Appr";
	public static final String MARKETING_RISK_PREV_APPR = "F_Mar_Ris_Previously_Appr";
	public static final String COUNTERPARTY_INTERNAL_PREV_APPR = "Count_Int_Previously_Appr";
	public static final String COUNTERPARTY_TYPE_PREV_APPR = "Count_Typ_Previously_Appr";
	public static final String REPRICING_FREQ_PREV_APPR = "Rep_Freq_Previously_Appr";
	public static final String SPECIAL_COST_PREV_APPR = "Spc_Cost_Previously_Appr";
	public static final String MORATORIUM_PERIOD_PREV_APPR = "Rep_Revolving_Previously_Appr";
	public static final String REPAYMENT_TYPE_PREV_APPR = "Rep_Typ_Previously_Appr";
	public static final String REPAYMENT_FREQUENCY_PREV_APPR = "Repay_Freq_Previously_Appr";
	public static final String CREDIT_LIMIT_PREV_APPR = "F_Credit_Previously_Appr";
	public static final String EQV_AED_AMOUNT_PREV_APPR = "Eqv_AED_Previously_Appr";
	public static final String ECL_PREV_APPR = "F_ECL_PREV_APPRV";
	public static final String RISK_CAPITAL_PREV_APPR = "F_RISK_CAPITAL_PREV_APPR";
	public static final String RISK_RETURN_PREV_APPR = "F_RISK_RETURN_PREV_APPR";
	public static final String FACILITY_RAROC_PREV_APPR = "F_FACILITY_RAROC_PREV_APPR";
	public static final String AVG_UTILISED_PREV_APPR = "F_AVG_PREV_APPR";
	public static final String REMAINING_TERM_IN_MONTH_PREV_APPR = "F_REMAIN_TRM_PREV_APPR";
	public static final String FTP_PREV_APPR = "F_FTP_PREV_APPR";
	public static final String INTEREST_RATE_PREV_APPR = "F_INT_RATE_PREV_APPR";
	public static final String COLLATRAL_TYPE_PREV_APPR = "F_COLLATRAL_TYPE_PREV_APROV";
	public static final String COLLATRAL_NAME_PREV_APPR = "F_COLLATERAL_NAME_PRE_APRVD";
	public static final String COLLATRAL_AMOUNT_PREV_APPR = "F_COLLATERAL_AMOUNT_PRE_APPRVD";
	public static final String COLLATRAL_CURRENCY_PREV_APPR = "F_COLLATRAL_CURRENCY_PREV_APROV";
	public static final String COLLATRAL_LIEN_AMOUNT_PREV_APPR = "F_COLLATRAL_LIEN_AMOUNT_PRE_APRV";
	public static final String COLLATRAL_LIEN_INTEREST_PREV_APPR = "F_COLLATRAL_LIEN_INTEREST";
	public static final String COLLATRAL_LIEN_TENURE_PREV_APPR = "F_COLLATERAL_LIAN_TENURE_PRE_APRVD";
	public static final String BORROWER_RATING_PREV_APPR = "F_BORROWER_RATING_PREVIOUSLY_APRVD";
	public static final String COMMITMENT_FEE_PREV_APPR = "F_COMMITMENT_FEE_PRE_APPROVED";
	public static final String UPFRONT_FEE_PREV_APPR = "F_UPFRONT_FEE_PRE_APPROVED";
	//start changes by mohit 24-06-2024
	public static final String COLLATERAL_NUMBER_PREVIOUSLY_APPR = "F_Collateral_Number_Previously_Appr";
	public static final String ALLOCATION_PERCENTAGE_PREVIOUSLY_APPR = "F_Allocation_Percentage_Previously_Appr";
	public static final String INDEX_KEY_PRE_APPROVED = "F_INDEX_KEY_PRE_APPROVED";
	public static final String INDEX_TENOR_PRE_APPROVED = "F_INDEX_TENOR_PRE_APPROVED";
	public static final String INDEX_TENOR_UNIT_PRE_APPROVED = "F_INDEX_TENOR_UNIT_PRE_APPROVED";
			//end changes by mohit 24-06-2024
	//changes by shikha 11-07-24
	public static final String FTP_OVERRIDE_PRE_APPROVED = "F_FTP_OVR_PRE_APPROVED";
	public static final String UTILIZATION_PREV_APPR = "F_Utilization_Previously_Appr";
	
	// FACILITY APPROVED
	public static final String CURRENCY_APPROVED = "F_Currency_Approved";
	public static final String FACILITY_AMOUNT_APPROVED = "F_Facility_Amount_Approved";
	public static final String TENOR_APPROVED = "F_Tenor_Approved";
	public static final String TENOR_UNIT_APPROVED = "F_Tenor_unit_Approved";
	public static final String EXPIRY_DATE_APPROVED = "F_Expiry_Date_Approved";
	public static final String CASH_MARGIN_APPROVED = "F_Cash_Margin_Approved";
	public static final String OUTSTANDING_AMOUNT_APPROVED = "Outs_Amnt_Approved";
	public static final String PRICING_APPROVED = "F_Pricing_Approved";
	public static final String INDEX_APPROVED = "Index_Approved"; 
	public static final String INDEX_RATE_APPROVED = "F_Index_Rate_Approved";
	public static final String MARGIN_COMMISION_APPROVED = "F_Margin_Commision_Approved";
	public static final String VALUE_APPROVED = "F_Value_Approved";
	public static final String MINIMUM_APPROVED = "F_Minimum_Approved";
	public static final String MAXIMUM_APPROVED = "F_Maximum_Approved";
	public static final String EXP_1_YEAR_RATE_APPROVED = "Exp_Ret_Approved";
	public static final String MARKETING_RISK_APPROVED = "F_Mar_Ris_Approved";
	public static final String COUNTERPARTY_INTERNAL_APPROVED = "Count_Int_Approved";
	public static final String COUNTERPARTY_TYPE_APPROVED = "Count_Typ_Approved";
	public static final String REPRICING_FREQ_APPROVED = "Rep_Freq_Approved";
	public static final String SPECIAL_COST_APPROVED = "Spc_Cost_Approved";
	public static final String MORATORIUM_PERIOD_APPROVED = "Rep_Revolving_Approved";
	public static final String REPAYMENT_TYPE_APPROVED = "Rep_Typ_Approved";
	public static final String REPAYMENT_FREQUENCY_APPROVED = "Repay_Freq_Approved";
	public static final String CREDIT_LIMIT_APPROVED = "F_Credit_Approved";
	public static final String EQV_AED_AMOUNT_APPROVED = "F_Eqv_AED_Amount_Approved";
	public static final String ECL_APPROVED = "F_ECL_APPR";
	public static final String RISK_CAPITAL_APPROVED = "F_RISK_CAPITAL_APPR";
	public static final String RISK_RETURN_APPROVED = "F_RISK_RETURN_APPR";
	public static final String FACILITY_RAROC_APPROVED = "F_FACILITY_RAROC_APPR";
	public static final String AVG_UTILISED_APPROVED = "F_AVG_APPR";
	public static final String REMAINING_TERM_IN_MONTH_APPROVED = "F_REMAIN_TRM_APPR";
	public static final String FTP_APPROVED = "F_FTP_APPR";
	public static final String INTEREST_RATE_APPROVED = "F_INT_RATE_APPR";
	public static final String COLLATRAL_TYPE_APPROVED = "F_COLLATRAL_TYPE_APROVED";
	public static final String COLLATRAL_NAME_APPROVED = "F_COLLATERAL_NAME_APPROVED";
	public static final String COLLATRAL_AMOUNT_APPROVED = "F_COLLATERAL_AMOUNT_APPROVED";
	public static final String COLLATRAL_CURRENCY_APPROVED = "F_COLLATRAL_CURRENCY_APPROVED";
	public static final String COLLATRAL_LIEN_AMOUNT_APPROVED = "F_COLLATRAL_LIEN_AMOUNT_APPROVED";
	public static final String COLLATRAL_LIEN_INTEREST_APPROVED = "F_COLLATRAL_LIEN_INTEREST_APPROVED";
	public static final String COLLATRAL_LIEN_TENURE_APPROVED = "F_COLLATERAL_LIAN_TENURE_APPROVED";
	public static final String BORROWER_RATING_APPROVED = "F_BORROWER_RATING_APPROVED";
	public static final String COMMITMENT_FEE_APPROVED = "F_COMMITMENT_FEE_APPROVED";
	public static final String UPFRONT_FEE_APPROVED = "F_UPFRONT_FEE_APPROVED";
	//start changes by mohit 24-06-2024
	public static final String COLLATERAL_NUMBER_APPROVED = "F_Collateral_Number_Approved";
	public static final String INDEX_KEY_APPROVED = "F_INDEX_KEY_APPROVED";
	public static final String INDEX_TENOR_APPROVED = "F_INDEX_TENOR_APPROVED";
	public static final String ALLOCATION_PERCENTAGE_APPROVED = "F_Allocation_Percentage_Approved";
	public static final String INDEX_TENOR_UNIT_APPROVED = "F_INDEX_TENOR_UNIT_APPROVED";
	//end changes by mohit 24-06-2024
	//changes by shikha 11-07-24
	public static final String FTP_OVERRIDE_APPROVED = "F_FTP_OVR_APPROVED";
	public static final String UTILIZATION_APPROVED = "F_Utilization_Approved";
	
	//FACILITY USER INPUT FIELDS
	public static final String INCOME_NAME = "F_INCOME_NAME";
	public static final String INCOME_TYPE = "F_INCOME_TYPE";
	public static final String INCOME_PERCENTAGE = "F_INCOME_PERCENTAGE";
	public static final String INCOME_ABSOLUTE = "F_INCOME_ABSOLUTE";
	public static final String AVERAGE_BALANCE = "F_AVERAGE_BALANCE";
	public static final String FOREX_INCOME = "F_FOREX_INCOME";
	public static final String COMMISSION_EXPORT = "F_COMMISSION_EXPORT";
	public static final String OTHER_INCOME = "F_OTHER_INCOME";
	public static final String FIXED_DEPOSIT = "F_FIXED_DEPOSIT";
	//EXT
	

	// DB TABLE COULUMN
	// DB TABLE COULUMN REALISED & Sensitized
	public static final String RAROC_CUSTOMER_REALIZED_COLUMN = "Customer_RAROC_Realized,Business_segment_Realized,"
			+ "Industry_Segement_Realized,IFRS_Staging_Realized,Counterparty_Type_Realized,Income_CASA_Realized,"
			+ "Export_Income_Realized,FX_Income_Realized,Other_Income_Realized,fee_incm_aed_Realized,"
			+ "cross_sell_incm_Realized,realized_raroc_Realized,customer_name_Realized,cust_intrl_rating_Realized,"
			+ "total_funded_aed_Realized,total_nonfunded_aed_Realized,customer_country_Realized,External_Code_Realized,"
			+ "Internal_Rating_Realized,Sales_Turnover_Realized";
	
	public static final String RAROC_CUSTOMER_SENSITIZED_COLUMN = "Customer_RAROC_sensitized,Business_segment_Sensitized,"
			+ "Industry_Segement_Sensitized,IFRS_Staging_Sensitized,Counterparty_Type_Sensitized,Income_CASA_Sensitized,"
			+ "Export_Income_Sensitized,FX_Income_Sensitized,Other_Income_Sensitized,fee_incm_aed_Sensitized,"
			+ "cross_sell_incm_Sensitized,customer_name_Sensitized,cust_intrl_rating_Sensitized,"
			+ "total_funded_aed_Sensitized,total_nonfunded_aed_Sensitized,customer_country_Sensitized,Internal_Rating_Sensitized,"
			+ "External_Rating_sensitized,External_Code_Sensitized,Sales_Turnover_Sensitized";
	

	public static final String RAROC_CUSTOMER_PROPOSED_COLUMN = "Business_segment_Proposed,"
			+ "Industry_Segement_Proposed,IFRS_Staging_Proposed,Counterparty_Type_Proposed,Income_CASA_Proposed,"
			+ "Export_Income_Proposed,FX_Income_Proposed,Other_Income_Proposed,fee_incm_aed_Proposed,"
			+ "cross_sell_incm_Proposed,realized_raroc_Proposed,customer_name_Proposed,cust_intrl_rating_Proposed,"
			+ "total_funded_aed_Proposed,total_nonfunded_aed_Proposed,customer_country_Proposed,Internal_Rating_Proposed ,"
			+ "External_Rating_Proposed ,External_Code_Proposed,Sales_Turnover_Proposed ";//edit  by mohit Customer_RAROC_Proposed removed 04-09-2024

	public static final String RAROC_CUSTOMER_APPROVED_COLUMN = "Customer_RAROC_Approved,Business_segment_Approved,"
			+ "Industry_Segement_Approved,IFRS_Staging_Approved,Counterparty_Type_Approved,Income_CASA_Approved,"
			+ "Export_Income_Approved,FX_Income_Approved,Other_Income_Approved,fee_incm_aed_Approved,"
			+ "cross_sell_incm_Approved,realized_raroc_Approved,customer_name_Approved,cust_intrl_rating_Approved,"
			+ "total_funded_aed_Approved,total_nonfunded_aed_Approved,customer_country_Approved,"
			+ "customer_id_Approved,Internal_Rating_Approved,External_Rating_Approved,Sales_Turnover_Approved,External_Code_Approved";

	public static final String RAROC_CUSTOMER_APPROVE_UPDATE = "Customer_RAROC_Approved = Customer_RAROC_sensitized,"
			+ "Business_segment_Approved = Business_segment_Sensitized,Industry_Segement_Approved = Industry_Segement_Sensitized,"
			+ "IFRS_Staging_Approved = IFRS_Staging_Sensitized,Counterparty_Type_Approved = Counterparty_Type_Sensitized,"
			+ "Income_CASA_Approved = Income_CASA_Sensitized,Export_Income_Approved = Export_Income_Sensitized,"
			+ "FX_Income_Approved = FX_Income_Sensitized,Other_Income_Approved = Other_Income_Sensitized,"
			+ "fee_incm_aed_Approved = fee_incm_aed_Sensitized,cross_sell_incm_Approved = cross_sell_incm_Sensitized,"
			+ "realized_raroc_Approved = realized_raroc_SENSITIZED,customer_name_Approved = customer_name_Sensitized,"
			+ "cust_intrl_rating_Approved = cust_intrl_rating_Sensitized,total_funded_aed_Approved = total_funded_aed_Sensitized,"
			+ "total_nonfunded_aed_Approved = total_nonfunded_aed_Sensitized,customer_country_Approved = customer_country_Sensitized,"
			+ "customer_id_Approved = customer_id_sensitized,Internal_Rating_Approved = Internal_Rating_Sensitized,"
			+ "External_Rating_Approved = External_Rating_sensitized,Sales_Turnover_Approved = Sales_Turnover_Sensitized,"
			+ "External_Code_Approved = External_Code_Sensitized";//added by mohit 11-09-2024
	
	public static final String RAROC_FACILITY_DETAILS_COLUMN = "TREASURY_PRODUCT,FACILITY_TYPE,FACILITY_NAME,PURP_FACILITY,"
			+ "SENIOR_SUBORDINATE,COMMITMENT_NO,EXPECTED_UTILISATION,MAIN_LIMIT,GENERAL_PROJECT,PROJECT_NAME,COMMITTED_UNCOMMITTED,REVOLVING,"
			+ "FACILITY_ID,MAIN_SUB_LIMIT,Currency_Realized,Currency_Proposed,Facility_Amount_Realized,"
			+ "Facility_Amount_Proposed,Eqv_AED_Amount_Realized,"
			+ "Eqv_AED_Amount_Proposed,Tenor_Realized,Tenor_Proposed,"
			+ "Tenor_unit_Realized,Tenor_unit_Proposed,"
			+ "Expiry_Date_Realized,Expiry_Date_Proposed,"
			+ "Cash_Margin_Realized,Cash_Margin_Proposed,"
			+ "Outstanding_Amount_Realized,Outstanding_Amount_Proposed,Pricing_Realized,Pricing_Proposed,"
			+ "Index_Realized,Index_Proposed,Index_Rate_Realized,"
			+ "Index_Rate_Proposed,Margin_Commision_Realized,"
			+ "Margin_Commision_Proposed,Value_Realized,Value_Proposed,"
			+ "Minimum_Realized,Minimum_Proposed,"
			+ "Maximum_Realized,Maximum_Proposed,ECL_REALISED,ECL_PROPOSED,RISK_ADJUSTED_CAPITAL_REALISED,"
			+ "RISK_ADJUSTED_CAPITAL_PROPOSED,RISK_ADJUSTED_RETURN_REALISED,RISK_ADJUSTED_RETURN_PROPOSED,FACILITY_RAROC_REALISED,"
			+ "FACILITY_RAROC_PROPOSED,AVERAGE_UTILISED_REALISED,AVERAGE_UTILISED_PROPOSED,FTP_RATE_REALISED,FTP_RATE_PROPOSED,"
			+ "INTEREST_RATE_APPLIED_REALISED,INTEREST_RATE_APPLIED_PROPOSED,BORROWER_RATING_PROPOSED,COMMITMENT_FEE_PROPOSED,UPFRONT_FEE_PROPOSED,"
			+ "COLLATERAL_NAME_PROPOSED,COLLATERAL_TYPE_PROPOSED,COLLATERAL_AMOUNT_PROPOSED,COLLATERAL_CURRENCY_PROPOSED,"
			+ "COLLATERAL_LEAN_AMOUNT_PROPOSED,COLLATERAL_LEAN_INTEREST_PROPOSED,COLLATERAL_LEAN_TENURE_PROPOSED,Expected_year_Return_Proposed,"
			+ "REPRICING_FERQUENCY_PROPOSED,REPAYMENT_TYPE_PROPOSED,REMAINING_TERMS_IN_MONTHS_PROPOSED,"
			+ "MARKETING_RISK_CAPITAL_PROPOSED,COUNTERPARTY_INTERNAL_RATING_PROPOSED,COUNTERPARTY_TYPE_SEGMENT_PROPOSED,"
			+ "SPECIAL_COST_OF_FUNDS_PROPOSED,MORATORIUM_PERIOD_PROPOSED,REPAYMENT_FREQUENCY_PROPOSED,CREDIT_LIMIT_PROPOSED,"
			+ "INCOME_NAME,INCOME_TYPE,INCOME_PERCENTAGE,INCOME_ABSOLUTE,"
			+"Collateral_Number_Realized,"
			+"Collateral_Number_Proposed,Allocation_Percentage_Realized,Allocation_Percentage_Proposed,"
			+"INDEX_KEY_REALIZED,INDEX_KEY_PROPOSED,"
			+"INDEX_TENOR_REALIZED,INDEX_TENOR_PROPOSED,INDEX_TENOR_UNIT_REALIZED,INDEX_TENOR_UNIT_PROPOSED,"
			+"COMMITMENT_FEE_REALISED ,Utilization_Realized ,Utilization_Proposed, "
			+ "Ftp_Override_Realized,Ftp_Override_Proposed";//realized and proposed fields //bp05 //d


	public static final String  RAROC_FACILITY_PROPOSED_COLUMN = "CURRENCY_PROPOSED,FACILITY_AMOUNT_PROPOSED,TENOR_PROPOSED,"
			+ "TENOR_UNIT_PROPOSED,EXPIRY_DATE_PROPOSED,CASH_MARGIN_PROPOSED,OUTSTANDING_AMOUNT_PROPOSED,PRICING_PROPOSED,"
			+ "INDEX_PROPOSED,INDEX_RATE_PROPOSED,MARGIN_COMMISION_PROPOSED,VALUE_PROPOSED,MINIMUM_PROPOSED,MAXIMUM_PROPOSED,"
			+ "EXPECTED_YEAR_RETURN_PROPOSED,MARKETING_RISK_CAPITAL_PROPOSED,COUNTERPARTY_INTERNAL_RATING_PROPOSED,"
			+ "COUNTERPARTY_TYPE_SEGMENT_PROPOSED,REPRICING_FERQUENCY_PROPOSED,SPECIAL_COST_OF_FUNDS_PROPOSED,"
			+ "REPAYMENT_FREQUENCY_PROPOSED,MORATORIUM_PERIOD_PROPOSED,REPAYMENT_TYPE_PROPOSED ,CREDIT_LIMIT_PROPOSED,"
			+ "EQV_AED_AMOUNT_PROPOSED,ECL_PROPOSED,RISK_ADJUSTED_CAPITAL_PROPOSED,RISK_ADJUSTED_RETURN_PROPOSED,FACILITY_RAROC_PROPOSED,"
			+ "AVERAGE_UTILISED_PROPOSED,REMAINING_TERMS_IN_MONTHS_PROPOSED,FTP_RATE_PROPOSED,INTEREST_RATE_APPLIED_PROPOSED,"
			+ "COLLATERAL_NAME_PROPOSED,COLLATERAL_TYPE_PROPOSED,COLLATERAL_AMOUNT_PROPOSED,COLLATERAL_CURRENCY_PROPOSED,"
			+ "COLLATERAL_LEAN_AMOUNT_PROPOSED,COLLATERAL_LEAN_INTEREST_PROPOSED,COLLATERAL_LEAN_TENURE_PROPOSED,"
			+ "BORROWER_RATING_PROPOSED,COMMITMENT_FEE_PROPOSED,UPFRONT_FEE_PROPOSED,INCOME_NAME,INCOME_TYPE,"
			+ "INCOME_PERCENTAGE,INCOME_ABSOLUTE,Collateral_Number_Proposed,Allocation_Percentage_Proposed,"
			+"INDEX_KEY_PROPOSED,INDEX_TENOR_PROPOSED,INDEX_TENOR_UNIT_PROPOSED,UTILIZATION_PROPOSED,FTP_OVERRIDE_PROPOSED";//bp05 //d
	
	public static final String RAROC_FACILITY_PREV_APPR_COLUMN = "Currency_Previously_Appr, Facility_Amount_Previously_Appr,"
			+ "  Tenor_Previously_Appr, Tenor_unit_Previously_Appr, Expiry_Date_Previously_Appr,"
			+ " Cash_Margin_Previously_Appr, Outstanding_Amount_Previously_Appr, Pricing_Previously_Appr, Index_Previously_Appr,"
			+ " Index_Rate_Previously_Appr, Margin_Commision_Previously_Appr, Value_Previously_Appr, Minimum_Previously_Appr,"
			+ " Maximum_Previously_Appr, Expected_year_Return_Previously_Appr,Marketing_Risk_Capital_Previously_Appr,Counterparty_Internal_Rating_Previously_Appr,"
			+ " Counterparty_Type_Segment_Previously_Appr,REPRICING_FERQUENCY_PRE_APPROVED,Special_Cost_of_funds_Previously_Appr,"
			+ " Repayment_Frequency_Previously_Appr,Moratorium_Period_Previously_Appr,Repayment_Type_Previously_Appr,"
			+ " Credit_Limit_Previously_Appr,Eqv_AED_Amount_Previously_Appr,ECL_PRE_APPROVED,RISK_ADJUSTED_CAPITAL_PRE_APPROVED,RISK_ADJUSTED_RETURN_PRE_APPROVED,"
			+ " FACILITY_RAROC_PRE_APPROVED,AVERAGE_UTILISED_PRE_APPROVED,"
			+ " REMAINING_TERMS_IN_MONTHS_PRE_APPROVED,FTP_RATE_PRE_APPROVED,INTEREST_RATE_APPLIED_PRE_APPROVED,"
			+ " COLLATERAL_TYPE_PRE_APPROVED,COLLATERAL_NAME_PRE_APPROVED,COLLATERAL_AMOUNT_PRE_APPROVED,"
			+ " COLLATERAL_CURRENCY_PRE_APPROVED,COLLATERAL_LEAN_AMOUNT_PRE_APPROVED,COLLATERAL_LEAN_INTEREST_PRE_APPROVED,"
			+ " COLLATERAL_LEAN_TENURE_PRE_APPROVED,BORROWER_RATING_PRE_APPROVED,COMMITMENT_FEE_PRE_APPROVED,UPFRONT_FEE_PRE_APPROVED,Collateral_Number_Previously_Appr,Allocation_Percentage_Previously_Appr,"
			+ "INDEX_KEY_PRE_APPROVED,INDEX_TENOR_PRE_APPROVED,INDEX_TENOR_UNIT_PRE_APPROVED,Utilization_Previously_Appr, Ftp_Override_Pre_Approved";

	public static final String RAROC_GROUP_REALIZED_COLUMN = "BORR_RAT,FUNDED_AMOUNT,NON_FUNDED_AMOUNT,COMMT_FEES,UPFR_FEES,"
			+ "CROS_SEL_INCOME,CUSTOMER_LVL_RAROC";

	public static final String RAROC_FACILITY_APPROVE_COLUMN = "CURRENCY_Approved,FACILITY_AMOUNT_Approved,TENOR_Approved,"
			+ "TENOR_UNIT_Approved,EXPIRY_DATE_Approved,CASH_MARGIN_Approved,OUTSTANDING_AMOUNT_Approved,PRICING_Approved,"
			+ "INDEX_Approved,INDEX_RATE_Approved,MARGIN_COMMISION_Approved,VALUE_Approved,MINIMUM_Approved,MAXIMUM_Approved,"
			+ "EXPECTED_YEAR_RETURN_Approved,MARKETING_RISK_CAPITAL_Approved,COUNTERPARTY_INTERNAL_Approved,"
			+ "COUNTERPARTY_TYPE_SEGMENT_Approved,REPRICING_FERQUENCY_Approved,SPECIAL_COST_OF_FUNDS_Approved,"
			+ "REPAYMENT_FREQUENCY_Approved,MORATORIUM_PERIOD_Approved,REPAYMENT_TYPE_Approved,CREDIT_LIMIT_Approved,"
			+ "EQV_AED_AMOUNT_Approved,ECL_Approved,RISK_ADJUSTED_CAPITAL_Approved,RISK_ADJUSTED_RETURN_Approved,FACILITY_RAROC_Approved,"
			+ "AVERAGE_UTILISED_Approved,REMAINING_TERMS_IN_MONTHS_Approved,FTP_RATE_Approved,INTEREST_RATE_APPLIED_Approved,"
			+ "COLLATERAL_NAME_Approved,COLLATERAL_TYPE_Approved,COLLATERAL_AMOUNT_Approved,COLLATERAL_CURRENCY_Approved,"
			+ "COLLATERAL_LEAN_AMOUNT_Approved,COLLATERAL_LEAN_INTEREST_Approved,COLLATERAL_LEAN_TENURE_Approved,"
			+ "BORROWER_RATING_Approved,COMMITMENT_FEE_Approved,UPFRONT_FEE_Approved,"
			+"Collateral_Number_Approved,Allocation_Percentage_Approved,INDEX_KEY_Approved,INDEX_TENOR_Approved,INDEX_TENOR_UNIT_Approved,UTILIZATION_Approved,FTP_OVERRIDE_Approved";
	

	public static final String RAROC_FACILITY_SENSITIZED_COLUMN = "CURRENCY_SENSITIZED,FACILITY_AMOUNT_SENSITIZED,TENOR_SENSITIZED,"
			+ "TENOR_UNIT_SENSITIZED,EXPIRY_DATE_SENSITIZED,CASH_MARGIN_SENSITIZED,OUTSTANDING_AMOUNT_SENSITIZED,PRICING_SENSITIZED,"
			+ "INDEX_SENSITIZED,INDEX_RATE_SENSITIZED,MARGIN_COMMISION_SENSITIZED,VALUE_SENSITIZED,MINIMUM_SENSITIZED,MAXIMUM_SENSITIZED,"
			+ "EXPECTED_YEAR_RETURN_SENSITIZED,MARKETING_RISK_CAPITAL_SENSITIZED,COUNTERPARTY_INTERNAL_SENSITIZED,"
			+ "COUNTERPARTY_TYPE_SEGMENT_SENSITIZED,REPRICING_FERQUENCY_SENITISED,SPECIAL_COST_OF_FUNDS_SENSITIZED,"
			+ "REPAYMENT_FREQUENCY_SENSITIZED,MORATORIUM_PERIOD_SENSITIZED,REPAYMENT_TYPE_SENSITIZED,CREDIT_LIMIT_SENSITIZED,"
			+ "EQV_AED_AMOUNT_SENSITIZED,ECL_SENITISED,RISK_ADJUSTED_CAPITAL_SENITISED,RISK_ADJUSTED_RETURN_SENITISED,FACILITY_RAROC_SENITISED,"
			+ "AVERAGE_UTILISED_SENITISED,REMAINING_TERMS_IN_MONTHS_SENITISED,FTP_RATE_SENITISED,INTEREST_RATE_APPLIED_SENITISED,"
			+ "COLLATERAL_NAME_SENITISED,COLLATERAL_TYPE_SENITISED,COLLATERAL_AMOUNT_SENITISED,COLLATERAL_CURRENCY_SENITISED,"
			+ "COLLATERAL_LEAN_AMOUNT_SENITISED,COLLATERAL_LEAN_INTEREST_SENITISED,COLLATERAL_LEAN_TENURE_SENITISED,"
			+ "BORROWER_RATING_SENITISED,COMMITMENT_FEE_SENITISED,UPFRONT_FEE_SENITISED,INCOME_NAME,INCOME_TYPE,"
			+ "INCOME_PERCENTAGE,INCOME_ABSOLUTE,"
			+"Collateral_Number_Sensitized,Allocation_Percentage_Sensitized,INDEX_KEY_SENSITIZED,INDEX_TENOR_SENSITIZED,INDEX_TENOR_UNIT_SENSITIZED,UTILIZATION_SENSITIZED,FTP_OVERRIDE_SENSITIZED";
	
	public static final String NG_CA_COLUMN = "SNP_EXIS,SNP_PROP,FITCH_EXIS,FITCH_PROP,MOODY_EXIS,MOODY_PROP";
	public static final String EXISTING_MRA = "EXISTING_MRA";
	public static final String REVENUE = "REVENUE";
	
	public static final String RAROC_FACILITY_APPROVE_UPDATE = " CURRENCY_APPROVED = CURRENCY_SENSITIZED,"
			+ " FACILITY_AMOUNT_APPROVED = FACILITY_AMOUNT_SENSITIZED, TENOR_APPROVED = TENOR_SENSITIZED,"
			+ " TENOR_UNIT_APPROVED = TENOR_UNIT_SENSITIZED, EXPIRY_DATE_APPROVED = EXPIRY_DATE_SENSITIZED,"
			+ " CASH_MARGIN_APPROVED = CASH_MARGIN_SENSITIZED, OUTSTANDING_AMOUNT_APPROVED = OUTSTANDING_AMOUNT_SENSITIZED,"
			+ " PRICING_APPROVED = PRICING_SENSITIZED, INDEX_APPROVED = INDEX_SENSITIZED, INDEX_RATE_APPROVED = INDEX_RATE_SENSITIZED,"
			+ " MARGIN_COMMISION_APPROVED = MARGIN_COMMISION_SENSITIZED, VALUE_APPROVED = VALUE_SENSITIZED,"
			+ " MINIMUM_APPROVED = MINIMUM_SENSITIZED, MAXIMUM_APPROVED = MAXIMUM_SENSITIZED,"
			+ " EXPECTED_YEAR_RETURN_APPROVED = EXPECTED_YEAR_RETURN_SENSITIZED, MARKETING_RISK_CAPITAL_APPROVED = MARKETING_RISK_CAPITAL_SENSITIZED,"
			+ " COUNTERPARTY_INTERNAL_APPROVED = COUNTERPARTY_INTERNAL_SENSITIZED, COUNTERPARTY_TYPE_SEGMENT_APPROVED = COUNTERPARTY_TYPE_SEGMENT_SENSITIZED,"
			+ " REPRICING_FERQUENCY_APPROVED = REPRICING_FERQUENCY_SENITISED, SPECIAL_COST_OF_FUNDS_APPROVED = SPECIAL_COST_OF_FUNDS_SENSITIZED,"
			+ " REPAYMENT_FREQUENCY_APPROVED = REPAYMENT_FREQUENCY_SENSITIZED, MORATORIUM_PERIOD_APPROVED = MORATORIUM_PERIOD_SENSITIZED,"
			+ " REPAYMENT_TYPE_APPROVED = REPAYMENT_TYPE_SENSITIZED, CREDIT_LIMIT_APPROVED = CREDIT_LIMIT_SENSITIZED,"
			+ "	EQV_AED_AMOUNT_APPROVED = EQV_AED_AMOUNT_SENSITIZED, ECL_APPROVED = ECL_SENITISED, RISK_ADJUSTED_CAPITAL_APPROVED = RISK_ADJUSTED_CAPITAL_SENITISED,"
			+ " RISK_ADJUSTED_RETURN_APPROVED = RISK_ADJUSTED_RETURN_SENITISED, FACILITY_RAROC_APPROVED = FACILITY_RAROC_SENITISED,"
			+ " AVERAGE_UTILISED_APPROVED = AVERAGE_UTILISED_SENITISED, REMAINING_TERMS_IN_MONTHS_APPROVED = REMAINING_TERMS_IN_MONTHS_SENITISED,"
			+ " FTP_RATE_APPROVED = FTP_RATE_SENITISED, INTEREST_RATE_APPLIED_APPROVED = INTEREST_RATE_APPLIED_SENITISED, COLLATERAL_NAME_APPROVED = COLLATERAL_NAME_SENITISED,"
			+ " COLLATERAL_TYPE_APPROVED = COLLATERAL_TYPE_SENITISED, COLLATERAL_AMOUNT_APPROVED = COLLATERAL_AMOUNT_SENITISED,"
			+ " COLLATERAL_CURRENCY_APPROVED = COLLATERAL_CURRENCY_SENITISED, COLLATERAL_LEAN_AMOUNT_APPROVED = COLLATERAL_LEAN_AMOUNT_SENITISED,"
			+ " COLLATERAL_LEAN_INTEREST_APPROVED = COLLATERAL_LEAN_INTEREST_SENITISED, COLLATERAL_LEAN_TENURE_APPROVED = COLLATERAL_LEAN_TENURE_SENITISED,"
			+ " BORROWER_RATING_APPROVED = BORROWER_RATING_SENITISED, COMMITMENT_FEE_APPROVED = COMMITMENT_FEE_SENITISED, UPFRONT_FEE_APPROVED = UPFRONT_FEE_SENITISED,Collateral_Number_Approved = Collateral_Number_Sensitized,Allocation_Percentage_Approved = Allocation_Percentage_Sensitized,"
			+  " UTILIZATION_APPROVED = UTILIZATION_SENSITIZED, FTP_OVERRIDE_APPROVED  = FTP_OVERRIDE_SENSITIZED ,INDEX_KEY_APPROVED = INDEX_KEY_SENSITIZED, INDEX_TENOR_APPROVED = INDEX_TENOR_SENSITIZED ,INDEX_TENOR_UNIT_APPROVED =INDEX_TENOR_UNIT_SENSITIZED";

	public static final String RAROC_FACILITY_SENSITIZED_UPDATE = " CURRENCY_SENSITIZED = CURRENCY_PROPOSED,"
			+ " FACILITY_AMOUNT_SENSITIZED = FACILITY_AMOUNT_PROPOSED, TENOR_SENSITIZED = TENOR_PROPOSED,"
			+ " TENOR_UNIT_SENSITIZED = TENOR_UNIT_PROPOSED, EXPIRY_DATE_SENSITIZED = EXPIRY_DATE_PROPOSED,"
			+ " CASH_MARGIN_SENSITIZED = CASH_MARGIN_PROPOSED, OUTSTANDING_AMOUNT_SENSITIZED = OUTSTANDING_AMOUNT_PROPOSED,"
			+ " PRICING_SENSITIZED = PRICING_PROPOSED, INDEX_SENSITIZED = INDEX_PROPOSED, INDEX_RATE_SENSITIZED = INDEX_RATE_PROPOSED,"
			+ " MARGIN_COMMISION_SENSITIZED = MARGIN_COMMISION_PROPOSED, VALUE_SENSITIZED = VALUE_PROPOSED,"
			+ " MINIMUM_SENSITIZED = MINIMUM_PROPOSED, MAXIMUM_SENSITIZED = MAXIMUM_PROPOSED,"
			+ " EXPECTED_YEAR_RETURN_SENSITIZED = EXPECTED_YEAR_RETURN_PROPOSED, MARKETING_RISK_CAPITAL_SENSITIZED = MARKETING_RISK_CAPITAL_PROPOSED,"
			+ " COUNTERPARTY_INTERNAL_SENSITIZED = COUNTERPARTY_INTERNAL_RATING_PROPOSED, COUNTERPARTY_TYPE_SEGMENT_SENSITIZED = COUNTERPARTY_TYPE_SEGMENT_PROPOSED,"
			+ " REPRICING_FERQUENCY_SENITISED = REPRICING_FERQUENCY_PROPOSED, SPECIAL_COST_OF_FUNDS_SENSITIZED = SPECIAL_COST_OF_FUNDS_PROPOSED,"
			+ " REPAYMENT_FREQUENCY_SENSITIZED = REPAYMENT_FREQUENCY_PROPOSED, MORATORIUM_PERIOD_SENSITIZED = MORATORIUM_PERIOD_PROPOSED,"
			+ " REPAYMENT_TYPE_SENSITIZED = REPAYMENT_TYPE_PROPOSED, CREDIT_LIMIT_SENSITIZED = CREDIT_LIMIT_PROPOSED,"
			+ "	EQV_AED_AMOUNT_SENSITIZED = EQV_AED_AMOUNT_PROPOSED,"
			+ " FTP_RATE_SENITISED = FTP_RATE_PROPOSED, COLLATERAL_NAME_SENITISED = COLLATERAL_NAME_PROPOSED,"
			+ " COLLATERAL_TYPE_SENITISED = COLLATERAL_TYPE_PROPOSED, COLLATERAL_AMOUNT_SENITISED = COLLATERAL_AMOUNT_PROPOSED,"
			+ " COLLATERAL_CURRENCY_SENITISED = COLLATERAL_CURRENCY_PROPOSED, COLLATERAL_LEAN_AMOUNT_SENITISED = COLLATERAL_LEAN_AMOUNT_PROPOSED,"
			+ " COLLATERAL_LEAN_INTEREST_SENITISED = COLLATERAL_LEAN_INTEREST_PROPOSED, COLLATERAL_LEAN_TENURE_SENITISED = COLLATERAL_LEAN_TENURE_PROPOSED,"
			+ " Collateral_Number_Sensitized = Collateral_Number_Proposed,Allocation_Percentage_Sensitized = Allocation_Percentage_Proposed,"
			+ "UTILIZATION_SENSITIZED = UTILIZATION_PROPOSED, FTP_OVERRIDE_SENSITIZED  = FTP_OVERRIDE_PROPOSED,"
			+ "INDEX_KEY_SENSITIZED = INDEX_KEY_PROPOSED, INDEX_TENOR_SENSITIZED = INDEX_TENOR_PROPOSED, INDEX_TENOR_UNIT_SENSITIZED = INDEX_TENOR_UNIT_PROPOSED ";//bp05 //nc

//start edit by mohit 29-07-2024 for CFM Grid Sens data to be copied from prop fields
	public static final String CFM_SENSITIZED_UPDATE = "COLLATERAL_TYPE_SENS = COLLATERAL_TYPE_PROP,"
			+"COLLATERAL_NAME_SENS = COLLATERAL_NAME_PROP,"
			+"COLLATERAL_NUMBER_SENS = COLLATERAL_NUMBER_PROP,"
			+"COLLATERAL_CURRENCY_SENS = COLLATERAL_CURRENCY_PROP,"
			+"COLLATERAL_AMOUNT_SENS = COLLATERAL_AMOUNT_PROP,"
			+"COLLATERAL_LIEN_AMOUNT_SENS = COLLATERAL_LIEN_AMOUNT_PROP,"
			+"COLLATERAL_LIEN_INTEREST_SENS = COLLATERAL_LIEN_INTEREST_PROP,"
			+"COLLATERAL_LIEN_TENOR_SENS = COLLATERAL_LIEN_TENOR_PROP,"
			+"COLLATERAL_ALLOCATION_PERCENTAGE_SENS = COLLATERAL_ALLOCATION_PERCENTAGE_PROP,"
			+"COLLATERAL_TOTAL_AMOUNT_SENS = COLLATERAL_TOTAL_AMOUNT_PROP,"
			+"COLLATERAL_AMOUNT_UTILIZED_SENS = COLLATERAL_AMOUNT_UTILIZED_PROP";
	
	public static final String COLLATERAL_TYPE_SENS = "CFM_COLLATERAL_TYPE_SENS";
	public static final String COLLATERAL_NAME_SENS = "CFM_COLLATERAL_NAME_SENS";
	public static final String COLLATERAL_NUMBER_SENS = "CFM_COLLATERAL_NUMBER_SENS";
	public static final String COLLATERAL_CURRENCY_SENS = "CFM_COLLATERAL_CURRENCY_SENS";
	public static final String COLLATERAL_AMOUNT_SENS = "CFM_COLLATERAL_AMOUNT_SENS";
	public static final String COLLATERAL_LIEN_AMOUNT_SENS = "CFM_COLLATERAL_LIEN_AMOUNT_SENS";
	public static final String COLLATERAL_LIEN_INTEREST_SENS = "CFM_COLLATERAL_LIEN_INTEREST_SENS";
	public static final String COLLATERAL_LIEN_TENOR_SENS = "CFM_COLLATERAL_LIEN_TENOR_SENS";
	public static final String COLLATERAL_ALLOCATION_PERCENTAGE_SENS = "CFM_COLLATERAL_ALLOCATION_PERCENTAGE_SENS";
	public static final String COLLATERAL_TOTAL_AMOUNT_SENS = "CFM_COLLATERAL_TOTAL_AMOUNT_SENS";
	public static final String COLLATERAL_AMOUNT_UTILIZED_SENS = "CFM_COLLATERAL_AMOUNT_UTILIZED_SENS";
	
	public static final String CFM_SENSITIZED_COLUMN ="COLLATERAL_TYPE_SENS,COLLATERAL_NAME_SENS,COLLATERAL_NUMBER_SENS,"
			+ "COLLATERAL_CURRENCY_SENS,COLLATERAL_AMOUNT_SENS,COLLATERAL_LIEN_AMOUNT_SENS,COLLATERAL_LIEN_INTEREST_SENS,"
			+ "COLLATERAL_LIEN_TENOR_SENS,COLLATERAL_ALLOCATION_PERCENTAGE_SENS,COLLATERAL_TOTAL_AMOUNT_SENS,COLLATERAL_AMOUNT_UTILIZED_SENS";
	
//end edit by mohit 29-07-2024 for CFM Grid Sens data to be copied from prop fields
	
	//Start changes by shikha 13-09-2024
	public static final String CFM_APPROVE_UPDATE = "COLLATERAL_TYPE_APPROVED = COLLATERAL_TYPE_SENS, COLLATERAL_NAME_APPROVED = COLLATERAL_NAME_SENS,"
			+ "COLLATERAL_NUMBER_APPROVED = COLLATERAL_NUMBER_SENS, COLLATERAL_CURRENCY_APPROVED = COLLATERAL_CURRENCY_SENS,"
	+ "COLLATERAL_AMOUNT_APPROVED = COLLATERAL_AMOUNT_SENS, COLLATERAL_LIEN_AMOUNT_APPROVED = COLLATERAL_LIEN_AMOUNT_SENS,"
	+ "COLLATERAL_LIEN_INTEREST_APPROVED = COLLATERAL_LIEN_INTEREST_SENS, COLLATERAL_LIEN_TENOR_APPROVED = COLLATERAL_LIEN_TENOR_SENS,"
	+ "COLLATERAL_ALLOCATION_PERCENTAGE_APPROVED = COLLATERAL_ALLOCATION_PERCENTAGE_SENS, COLLATERAL_TOTAL_AMOUNT_APPROVED = COLLATERAL_TOTAL_AMOUNT_SENS,"
	+ "COLLATERAL_AMOUNT_UTILIZED_APPROVED = COLLATERAL_AMOUNT_UTILIZED_SENS ";

	//End changes by shikha 13-09-2024

	// DB TABLE NAME
	public static final String RAROC_CUSTOMER_TABLE = "NG_RAROC_CUSTOMER";
	public static final String RAROC_SECURITY_CUSTOMER_TABLE = "NG_RAROC_SECURITY_CUSTOMER";
	public static final String RAROC_GROUP_DETAILS_TABLE = "NG_RAROC_GROUP_DETAILS";
	public static final String RAROC_FACILITY_DETAILS_TABLE = "NG_RAROC_FACILITY_DETAILS";
	public static final String RAROC_CUSTOMER_DETAILS_TABLE = "NG_RAROC_CUSTOMER_DETAILS";
	public static final String RAROC_GROUP_TABLE = "NG_RAROC_GROUP";
	public static final String RAROC_FACILITY_TABLE = "NG_RAROC_FACILITY";
	public static final String RAROC_FACILITY_TAB_TABLE = "NG_RAROC_FACILITY_TAB";
	public static final String CA_CUSTOMER_DETAILS = "NG_CA_CUSTOMER_DETAILS";
	public static final String CA_MRA = "NG_CA_MRA";
	public static final String CA_INHOUSE_FINANCIALS = "NG_CA_INHOUSE_FINANCIALS";
	public static final String RAROC_COMMENT_HISTORY = "NG_RAROC_COMMENT_HISTORY";
	public static final String RAROC_COLLATERAL_FACILITY_MAP = "NG_RAROC_COLLATERAL_FACILITY_MAP";

	// XML
	public static final String TOTAL_RETRIEVED = "TotalRetrieved";
	public static final String DATA = "Data";
	public static final String ROW = "Row";
	public static final String DATE_FORMAT_SQL = "yyyy-MM-dd";

	public static final String CUSTOMER_REALISED_DETAILS = "customerRealisedDetails";
	public static final String PROJECTED_DETAILS = "projectedDetails";
	public static final String SHEET_INDEX_1 = "sheetIndexChange1";
	public static final String SHEET_INDEX_2 = "sheetIndexChange2";
	public static final String SHEET_INDEX_3 = "sheetIndexChange3";
	public static final String INDEX_TENOR_CHANGE = "indexTenorChange";
	public static final String INDEX_TENOR_CHANGE_SENS ="indexTenorChangeSens";
	public static final String SHEET_INDEX_4 = "sheetIndexChange4"; //Added by shikha for Comment History Tab 06-08-2024
	
	
	//QVAR GRID NAME
	public static final String LISTVIEW_FACILITY = "LV_FacilityDetails";
	
	//start edit by mohit 27-06-2024 for GUARANTEE/COLLATERAL Grid.
	
	//end  edit by mohit 27-06-2024 for GUARANTEE/COLLATERAL Grid.
	
	public static final String COLLATERAL = "collateral";
//	public static final String USER_NAME = "USER_ID";
//	public static final String COMMENT_HISTORY = "Comment_History";
}
