package com.newgen.iforms.user.config;

public interface Constants {
	
	//CONTROL NAMES
	public static final String TFO_WI_NAME = "WI_NAME";
	public static final String TFO_CURR_WS = "";

	// EVENT TYPE
	public static final String EVENT_TYPE_LOAD = "load";
	public static final String EVENT_TYPE_CLICK = "click";
	public static final String EVENT_TYPE_CHANGE = "change";
	public static final String EVENT_TYPE_LOSTFOCUS = "blur";
	public static final String EVENT_TYPE_GOTFOCUS = "focus";
	
	// LOGGER
	//dev public static final String FOLDER_CONFIG = "ProcessLoggerConfig";
	public static final String FOLDER_CONFIG = "NGConfig";
	public static final String FILE_LOG4J_PROPERTIES = "log4j.properties";
	
	//BOOLEAN
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String ENABLE = "enable";
	public static final String DISABLE = "disable";
	public static final String VISIBLE = "visible";
	
	//Control sets
	public static final String CONTROL_SET_READONLY = "CONTROL_SET_READONLY";
	public static final String CONTROL_SET_REQ_TYPE_GAA = "CONTROL_SET_REQ_TYPE_GAA";
	public static final String CONTROL_SET_REQ_NI_ENABLE = "CONTROL_SET_REQ_NI_ENABLE";
	public static final String CONTROL_SET_REQ_NI_DISABLE = "CONTROL_SET_REQ_NI_DISABLE";
	public static final String CONTROL_SET_DISABLE_ON_INTRO_LOAD = "CONTROL_SET_DISABLE_ON_INTRO_LOAD"; 
	public static final String CONTROL_SET_DISABLEFETCHFIELDS = "CONTROL_SET_disableFetchFields"; 
	public static final String CONTROL_SET_SWIFT_FIELDS = "CONTROL_SET_SWIFT_FIELDS";
	public static final String CONTROL_SET_SWIFT_FIELDS_ENABLE = "CONTROL_SET_SWIFT_FIELDS_ENABLE";
	public static final String CONTROL_SET_DISABLE_TEMP_TXT = "CONTROL_SET_DISABLE_TEMP_TXT";
	public static final String CONTROL_SET_DISABLE_STATIC_INTRO = "CONTROL_SET_DISABLE_STATIC_INTRO";
	public static final String CONTROL_SET_DISABLE_STATIC_LOGIS_LOV = "CONTROL_SET_DISABLE_STATIC_LOGIS_LOV";
	
	//Control Names 
	public static final String CONTROL_NAME_FORM = "form"; // THIS IS USED AS A PLACEHOLDER FOR FORM LOAD EVENTS ONLY
	public static final String TFO_BRANCH_CITY = "BRANCH_CITY";
	public static final String TFO_ASSIGNED_CENTER = "ASSIGNED_CENTER";
	public static final String INITIATOR_BRANCH = "INITIATOR_BRANCH";

	//SWIFT FRAME
	public static final String SWIFT_FETCH_MODULE = "SWIFT_FETCH_MODULE";
	public static final String SWIFT_MESSAGE_TYPE = "SWIFT_MESSAGE_TYPE";
	public static final String SWIFT_REASON_FOR_FILING = "SWIFT_REASON_FOR_FILING";
	public static final String SWIFT_PROCESSING_STATUS = "SWIFT_PROCESSING_STATUS";
	public static final String SWIFT_DEC_ON_SWIFT_MESSAGE = "SWIFT_DEC_ON_SWIFT_MESSAGE";
	public static final String REQUEST_TYPE = "REQUEST_TYPE";
	public static final String REQUEST_CATEGORY = "REQUEST_CATEGORY"; 
	public static final String REQUESTED_BY = "REQUESTED_BY";
	public static final String CUSTOMER_ID = "CUSTOMER_ID";
	public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
	public static final String PROCESS_TYPE = "PROCESS_TYPE";
	public static final String SOURCE_CHANNEL = "SOURCE_CHANNEL";
	public static final String CHKBX_SEND_MAIL = "CHKBX_SEND_MAIL";
	public static final String FLG_ACK_GEN = "FLG_ACK_GEN";
	public static final String IS_ACK_GEN = "IS_ACK_GEN";
	public static final String SWIFT_UTILITY_FLAG = "SWIFT_UTILITY_FLAG";
	
	//Customer Details Tab Control Names
	public static final String CID = "CID_Txt";
	public static final String TRANSACTION_REFERENCE = "TRANSACTION_REFERENCE";
	public static final String TRANSACTION_CURRENCY = "TRANSACTION_CURRENCY";
	public static final String TRANSACTION_AMOUNT = "TRANSACTION_AMOUNT";
	public static final String RELATIONSHIP_TYPE = "RELATIONSHIP_TYPE";
	public static final String BUTTON_FETCH = "btnCidFetch";
	public static final String BUTTON_CUSTOMER_ACK = "btnPrint";
	public static final String BUTTON_SUBMIT = "btnSubmit";
	public static final String BUTTON_DISCARD = "btnDiscard";
	public static final String BUTTON_TSINQUIRY = "btnInquiry";
	
	//Decision
	public static final String DEC_CODE = "DEC_CODE";
	public static final String DECISION = "DECISION";

	// Frames/Sections
	public static final String FRAME_DECISION_HIST = "FRAME_Decision_Hist";
	
	//ListView Name
	public static final String LVW_DECISION_SUMARY = "LVW_Decision_Sumary";
	
	//PP Maker Constants
	public static final String PREV_WS = "PREV_WS";
	public static final String REMARKS = "REMARKS";
	public static final String REJ_RESN_PPM = "REJ_RESN_PPM";
	public static final String FINAL_DESC_PPM = "FINAL_DESC_PPM";

	//Alerts
	public static final String ALERT_REQ_CAT = "Please Select Request Category";
	public static final String ALERT_REQ_TYPE = "Please Select Request Type";
	public static final String ALERT_REQ_BY = "Please Select Request Resubmitted By";
	public static final String ALERT_SRC_CHNL = "Please Select Source Channel";
	public static final String ALERT_CUST_ID = "Please Enter CID";
	public static final String ALERT_CUST_NAME = "Please Enter Customer Name";
	public static final String ALERT_BRNCH_CITY = "Please select Issuing Center";
	public static final String ALERT_ASGN_CNTR = "Please select Processing Center";
	public static final String ALERT_TRNS_CURR = "Please Select Currency";
	public static final String ALERT_TRNS_AMT = "Please Enter Transaction Amount";
	public static final String ALERT_REL_TYPE = "Please Select Transaction Branch Code";
	public static final String ALERT_TRNS_REF = "Please Enter Transaction Ref. Number";
	public static final String ALERT_TRNS_REF_LENGTH = "Please enter correct Transaction Ref. Number of length 16";
	public static final String ALERT_ACK_CHK= "Please ensure the data is correct, as it can't be changed once you generate the acknowledgement?";
	public static final String ALERT_VALID_AMT = "Please enter valid amount";
	public static final String ALERT_AMT_LNGTH = "Please enter amount less than 22 length";
	public static final String ALERT_LOAN_GRP = "Please Enter Loan Group";
	
	//PPM Buttons or PM buttons
	public static final String ACCOUNT_DETAILS = "btnAccDtl";
	public static final String FETCH_DETAILS = "btnINFFetchAcc";
	public static final String CPD_ADD = "btnCPDAdd";
	public static final String CPD_DEL = "btnCPDDel";
	public static final String CPD_MOD = "btnCPDMod";
	public static final String VIEW_ACCOUNT_MANDATE = "btnFetchAccMemo";
	public static final String SIGN_FETCH = "btnFetchAccDetails";
	public static final String SIGN_ADD = "btnAddSign";
	public static final String SIGN_DEL = "btnDelSign";
	public static final String DOC_REV_MODIFY = "btnModify";
	public static final String BUTTON_RETRY = "btnRetry";
	public static final String DOC_REV_ADD = "btnAddDoc";
	public static final String DOC_REV_DELETE = "btnDelDoc";
	public static final String BTN_FETCH_TSLM_CID_DETAILS = "BTN_FETCH_TSLM_CID_DETAILS";
	
	//PPM constant
	public static final String	FIELD_SIGACC_ACC_TOTAL_BAL="SIGACC_ACC_TOTAL_BAL";
	public static final String	FIELD_SIGACC_ACC_CURR_BAL="SIGACC_ACC_CURR_BAL";
	public static final String	FIELD_SIGACC_DOMCL_BRN_CODE="SIGACC_DOMCL_BRN_CODE";
	public static final String	FIELD_SIGACC_ACC_CURRENCY="SIGACC_ACC_CURRENCY";
	public static final String	FIELD_SIGACC_ACC_MNDT="SIGACC_ACC_MNDT";
	public static final String	FIELD_TXT_SIGACC_ACC_NO="SIGACC_ACC_NO";
	public static final String	FIELD_GRNTEE_EXP_DATE="GRNTEE_CNTR_EXP_DATE";
	public static final String  FIELD_INSTRCTN_TO_BANK="INSTRCTN_TO_BANK";
	public static final String 	FIELD_PRO_TRADE_SETTLEMENT_INST="PRO_TRADE_SETTLEMENT_INST";
	public static final String	FIELD_PRO_TRADE_REF_NO="PRO_TRADE_REF_NO";
	public static final String	FIELD_CPD_NAME="CP_NAME";
	public static final String	FIELD_CPD_COUNTRY="CP_CNTRY";
	public static final String	DISABLE_CPD_STLMNT= FIELD_CPD_NAME+","+FIELD_CPD_COUNTRY;
	public static final String	FIELD_TFO_SWIFT_FIELD_21="SWIFT_FIELD_21";
	public static final String	FIELD_TFO_SWIFT_SENDER_BIC="SWIFT_SENDER_BIC";	
	public static final String	FIELD_TFO_DEC_ON_SWIFT_MESSAGE="SWIFT_DEC_ON_SWIFT_MESSAGE";
	public static final String  DISABLE_PPM=FIELD_SIGACC_ACC_TOTAL_BAL+","+FIELD_SIGACC_ACC_CURR_BAL+","+FIELD_SIGACC_DOMCL_BRN_CODE+","+FIELD_SIGACC_ACC_CURRENCY+","+FIELD_SIGACC_ACC_MNDT+","+FIELD_TXT_SIGACC_ACC_NO+","+FIELD_GRNTEE_EXP_DATE+","+FIELD_PRO_TRADE_REF_NO+","+FIELD_INSTRCTN_TO_BANK+","+FIELD_PRO_TRADE_SETTLEMENT_INST+","+SWIFT_FETCH_MODULE+","+SWIFT_MESSAGE_TYPE+","+SWIFT_PROCESSING_STATUS+","+FIELD_TFO_SWIFT_FIELD_21+","+FIELD_TFO_DEC_ON_SWIFT_MESSAGE +","+FIELD_TFO_SWIFT_SENDER_BIC;
	public static final String TEXT_VET_ADD = "btnAddTxtVet";
	public static final String TEXT_VET_DELETE = "btnDelTxtVet"; 
	public static final String LIMIT_CREDIT_FETCH = "LmtCrdtBtnFetch";
	public static final String LIMIT_CREDIT_ADD = "btnAddLmtCrdt";
	public static final String LIMIT_CREDIT_DELETE = "btnDelLmtCrdt";
	public static final String FINAL_DEDUPE = "BTN_DUPE";
	public static final String COUNTER_GTE_FIELD_NOT_LIST="T551,T552,T553,T554,T555,T556,T557,T558,T575";
	public static final String COMBOX_BRN_CODE="BRN_CODE";
	public static final String REF_ICG_RM = "REF_ICG_RM";
	public static final String IS_RM_PPM = "IS_RM_PPM";
	public static final String IS_REF_PPM = "IS_REF_PPM";
	public static final String IS_LEGAL_PPM = "IS_LEGAL_PPM";
	public static final String IS_CR_PPM = "IS_CR_PPM";
	public static final String IS_CB_PPM = "IS_CB_PPM";
	public static final String IS_TRADE_PPM = "IS_TRADE_PPM";
	public static final String DOC_RVW_TAB = "DocRvw";
	public static final String LMT_CRDT_TAB = "LmtCrdt";
	public static final String ADD_LVW_DOC_RVW= "add_lvwDocRvw";
	public static final String ADD_LMY_CRDT_RVW = "add_lvwLmtCrdt"; 
	public static final String IFS_LOAN_GRP = "IFS_LOAN_GRP";
	public static final String SUFF_BAL_AVL_PPM = "SUFF_BAL_AVL_PPM";
	public static final String INF_CHARGE_ACC_NUM = "INF_CHARGE_ACC_NUM";
	public static final String INF_CHARGE_ACC_TITLE = "INF_CHARGE_ACC_TITLE";
	public static final String INF_CHARGE_ACC_CURR = "INF_CHARGE_ACC_CURR";
	public static final String INF_CREDIT_ACC_NUM = "INF_CREDIT_ACC_NUM";
	public static final String INF_CREDIT_ACC_TITLE = "INF_CREDIT_ACC_TITLE";
	public static final String INF_CREDIT_ACC_CURR = "INF_CREDIT_ACC_CURR";
	public static final String DISCREPANCY_DTLS = "DISCREPANCY_DTLS";
	public static final String INF_MATURITY_DATE = "INF_MATURITY_DATE";
	public static final String LC_DOC_COURIER = "LC_DOC_COURIER";
	public static final String REQ_SIGN_MAN_PPM = "REQ_SIGN_MAN_PPM";
	public static final String SIGACC_REF_TO = "SIGACC_REF_TO";
	public static final String DOCREV_REF_TO = "DOCREV_REF_TO";
	public static final String TXTVETT_REF_TO = "TXTVETT_REF_TO";
	public static final String LMTCRE_REF_TO = "LMTCRE_REF_TO";
	public static final String INF_AMEND_TYPE = "INF_AMEND_TYPE";
	public static final String INF_BASE_DOC_DATE = "INF_BASE_DOC_DATE";
	public static final String INF_NEW_MATURITY_DATE = "INF_NEW_MATURITY_DATE";
	public static final String LC_CONF_AMNT = "LC_CONF_AMNT";
	public static final String DOC_REV_SUCC_PPM = "DOC_REV_SUCC_PPM";
	public static final String TXT_REQ_APP_PPM = "TXT_REQ_APP_PPM";
	public static final String LMTCRE_APP_AVL_PPM = "LMTCRE_APP_AVL_PPM";
	public static final String QVAR_LINKED_CUST = "QVar_linked_cust";
	public static final String INF_SETTLEMENT_ACC_NUM = "INF_SETTLEMENT_ACC_NUM";
	public static final String INF_SETTLEMENT_ACC_TITLE = "INF_SETTLEMENT_ACC_TITLE";
	public static final String INF_SETTLEMENT_ACC_CURR = "INF_SETTLEMENT_ACC_CURR";
	public static final String SIGACC_ACC_NO = "SIGACC_ACC_NO";
	public static final String BILL_MODE_OF_PMNT = "BILL_MODE_OF_PMNT";
	public static final String INF_TENOR_DAYS = "INF_TENOR_DAYS";
	public static final String ROUTE_TO_PPM = "ROUTE_TO_PPM";
	public static final String INF_LOAN_CURR = "INF_LOAN_CURR";
	public static final String INF_TENOR_BASE = "INF_TENOR_BASE";
	public static final String INF_ACTUAL_TENOR_BASE = "INF_ACTUAL_TENOR_BASE";
	public static final String Qvar_Lmt_Crdt_ID  ="Qvar_Lmt_Crdt_ID";
	public static final String GRNT_INPUT_HIDE ="TRN_TENOR#EXP_DATE#NEW_EXP_DATE#GRNTEE_CNTR_EXP_DATE#NEW_TRN_AMT";
	public static final String PURPOSE_OF_MESSAGE = "SWIFT_PUR_OF_MSG";

	//PPM alerts
	public static final String ALERT_NO_ROW="Please Select a row from grid.";
	public static final String ALERT_REFERRAL_EXIST="Referral details already added for ";
	public static final String  ALERT_MATURITY_DATE_GREATTER="Maturity Date Cannot be less than current Date";
	public static final String  ALERT_MATURITY_DATE_BASE="Maturity Date Cannot be before the Base Document Date";
	public static final String ALERT_NEW_MATURITY_DATE_GREATER = "New Maturity Date cannot be less than Current Date";
	public static final String ALERT_BASE_DOC_DATE= "Base Document Date Cannot be after the Maturity Date";
	public static final String ALERT_TENOR_DAYS= "Tenor Days Cannot be less than 0";
	public static final String  ALERT_CNTRGTEE_DATE="Counter GTEE expiry Date should never be less than expiry date.";//mans
	//WORKSTEP RM 
	public static final String CONTROL_SET_CUSTOMER_DETAILS = "CONTROL_SET_CUSTOMER_DETAILS";
	public static final String LISTVIEW_REFERRAL_SUMMARY = "ListViewReferral";
	public static final String BUTTON_NEXT = "btnNext";
	public static final String BUTTON_PREVIOUS = "btnPrevious";
	public static final String CONTROL_SET_READONLY_RM = "CONTROL_SET_READONLY_RM";
	
	//WORKSTEP DELIVERY
	public static final String CONTROL_SET_DELIVERY_CUSTOMER_DETAILS = "CONTROL_SET_DELIVERY_CUSTOMER_DETAILS";
	public static final String CONTROL_SET_READONLY_DELIVERY = "CONTROL_SET_READONLY_DELIVERY";
	
	//WORKSTEP PMPS/PCPS
	public static final String CONTROL_SET_PMPS_CUSTOMER_DETAILS = "CONTROL_SET_PMPS_CUSTOMER_DETAILS";
	public static final String CONTROL_SET_READONLY_PMPS = "CONTROL_SET_READONLY_PMPS";

	//Control Sets PM
	public static final String CONTROL_SET_COMPLIANCE_CHECK_ENABLE_FIELDS = "CONTROL_SET_COMPLIANCE_CHECK_ENABLE";
	public static final String CONTROL_SET_COMPLIANCE_CHECK_DISABLE_FIELDS = "CONTROL_SET_COMPLIANCE_CHECK_DISABLE";
	public static final String CONTROL_SET_SWIFT_FRAME_ENABLE_FIELDS = "CONTROL_SET_SWIFT_FRAME_ENABLE";
	public static final String CONTROL_SET_SWIFT_FRAME_DISABLE_FIELDS = "CONTROL_SET_SWIFT_FRAME_DISABLE";

	//controls PM disable - change for control also
	public static final String DISABLE_PM_ADVISING_TXT="ROUTE_TO_PPM,TXT_SIGACC_ACC_NO";
	public static final String HIDE_PM_TXT="GRNTEE_CNTR_EXP_DATE";
	public static final String DISABLE_PM_TXT="NEW_TRN_AMT,ROUTE_TO_PPM,EXP_DATE,NEW_EXP_DATE,TXT_SIGACC_ACC_NO,LC_UNDER,LC_ABOVE,GRNTEE_APP_CNTRCT_REF_NO,GRNTEE_BEN_CNTRCT_REF_NO,PRO_TRADE_REF_NO,INSTRCTN_TO_BANK,GRNTEE_CNTR_EXP_DATE,PRO_TRADE_SETTLEMENT_INST";
	public static final String DISABLE_PM_LOV="PRODUCT_TYPE,ACCOUNT_NUMBER,AMEND_TYPE,IS_ACC_VALID,TRN_THIRD_PARTY,TRN_TENOR,LC_TOLERANCE";
	public static final String DISABLE_PM_IF_NON_LD="TR_LOAN_AMT,TR_EXCEPTIONS,REF_FRM,TR_REFER_TREASURY,"
			+ "TR_YES,TR_NO,FETCH_RATE,TR_BUY_CUR,TR_SELL_CUR,UI_EXCHANGE_RATE,TR_SELL_AMT,TR_TREASURY_DEAL_NUM,PR_EXCHANGE_RATE,INT_EXCHANGE_RATE"; 
	// feature/stlam reyaz 26-07-2024 start
	public static final String CLEAR_PM_IF_NON_LD="TR_LOAN_AMT,TR_EXCEPTIONS,REF_FRM,TR_REFER_TREASURY,"
			+ "TR_YES,TR_NO,FETCH_RATE,TR_BUY_CUR,TR_SELL_CUR,TR_SELL_AMT,TR_TREASURY_DEAL_NUM,PR_EXCHANGE_RATE,INT_EXCHANGE_RATE"; 
	// feature/stlam reyaz 26-07-2024 end
	public static final String DISABLE_SWIFT_FIELDS="SWIFT_MESSAGE_TYPE,SWIFT_PROCESSING_STATUS,"
			+ "SWIFT_FIELD_21,SWIFT_SENDER_BIC,SWIFT_FETCH_MODULE,SWIFT_DEC_ON_SWIFT_MESSAGE,REASON_FOR_FILING";
	public static final String DISABLE_PC_PD_DETAILS="TFO_PD_CUSTOMER_ID,TFO_PARTY_TYPE,TFO_PARTY_DESC,TFO_PD_ADDRESS1,"
			+ "TFO_PD_ADDRESS2,TFO_PD_ADDRESS3,TFO_PD_ADDRESS,TFO_PD_COUNTRY,TFO_MEDIA_TYPE,TFO_PD_CUSTOMER_NAME,TFO_PD_PARTY_ID,"
			+ "TFO_PD_ADDRESS4,TFO_PARTY_TYPE_PM";
	public static final String DISABLE_PC_CONTRACT_LIMIT="CL_SERIALNUMBER,CL_CUSTOMER_NO,CL_PER_CONTRIBUTION,CL_COMBO_OPERATION,"
			+ "CL_COMBO_TYPE,CL_COMBO_AMOUNTTAG,CL_COMBO_PARTYTYPE,CL_LINKAGEREFNO,CONTRACT_LIMIT_ADD_BTN,CONTRACT_LIMIT_MODIFY_BTN,"
			+ "CONTRACT_LIMIT_DELETE_BTN";
	public static final String DISABLE_PM_IF_TXT="REMITTANCE_AMT,INF_NEW_MATURITY_DATE,INF_TENOR_DAYS,INF_BASE_DOC_DATE,"
			+ "INF_MATURITY_DATE,INF_ACTUAL_TENOR_BASE,INF_CHARGE_ACC_TITLE,INF_SETTLEMENT_ACC_TITLE,INF_CREDIT_ACC_TITLE,"
			+ "INF_CHARGE_ACC_CURR,INF_SETTLEMENT_ACC_CURR,INF_CREDIT_ACC_CURR,BC_DATE_OF_CALL,BC_REMAKS_NOT_DONE,"
			+ "BC_CALL_INFO,BC_NAME_OF_CALLER,BC_CONTACT_PERSON,BC_INVOICE_AMT,BC_RSN_OF_CALL,BC_BUYER_NAME,BC_TIME_OF_CALL,"
			+ "BC_BUYER_CONTACT_NUM,BC_INVOICE_NUMBER,PRO_TRADE_REF_NO,INSTRCTN_TO_BANK,PRO_TRADE_SETTLEMENT_INST,"
			+ "BILL_NAME_OF_VESSEL,BILL_PORT_OF_LOADING,BILL_PORT_OF_DISCHARGE,BILL_DEAL_NUMBER,BILL_DEAL_AMOUNT";
	public static final String DISABLE_PM_IF_CHECKBOX="BILL_FORWARD_EXCHG_CONTRACT_NO";
	public static final String DISABLE_PM_IF_LOV="REMITTANCE_CURR,PRODUCT_TYPE,INF_LOAN_CURR,INF_AMEND_TYPE,INF_TENOR_BASE,"
			+ "INF_CHARGE_ACC_NUM,INF_SETTLEMENT_ACC_NUM,INF_CREDIT_ACC_NUM,BC_CALL_DONE,BC_INVOICE_CURR,BILL_CURRENCY"; 
	public static final String DISABLE_CHARGES="TFO_Q_Charges_Frame.legalCurrency,TFO_Q_Charges_Frame.penaltyCurrency,"
			+ "TFO_Q_Charges_Frame.overdrawnCurrency,TFO_Q_Charges_Frame.otherCurrency,TFO_Q_Charges_Frame.legalChargesBorneBy,"
			+ "TFO_Q_Charges_Frame.penaltyChargesBorneBy,TFO_Q_Charges_Frame.overdrawnChargesBorneBy,"
			+ "TFO_Q_Charges_Frame.otherChargesBorneBy,TFO_Q_Charges_Frame.legalAmount,TFO_Q_Charges_Frame.penaltyAmount,"
			+ "TFO_Q_Charges_Frame.overdrawnAmount,TFO_Q_Charges_Frame.otherAmount,TFO_Q_Charges_Frame.remarks";
	public static final String DISABLE_LLI_TXT_FIELDS="SHIPMENT_DATE,TXT_VESSELNAME,TXT_VESSELFLAG,TXT_VESSELID,TXT_VESSELIMO";
	public static final String PM_DECISION_HISTORY = "PM_DECISION_HISTORY";
	public static final String DISABLE_VD_FRAME= "SOURCE_CHANNEL,RELATIONSHIP_TYPE,DELIVERY_BRANCH";
	public static final String DISABLE_ID_LOV_FRAME= "BILL_CUST_HLDING_ACC_US,BILL_RVSD_DOC_AVL,BILL_MODE_OF_PMNT,LC_DOC_COURIER,IFS_LOAN_GRP,REF_ICG_RM";
	public static final String DISABLE_ID_TXT_FRAME= "BILL_OUR_LC_NO,BILL_CORRSPNDNT_BNK,LC_CORRSPNDNT_BNK,LC_CONF_AMNT,GRNT_CHRG_ACC_TITLE,GRNT_CHRG_ACC_CRNCY";
	public static final String AMEND_FRAME_FIELDS = "TRANSACTION_AMOUNT#EXPIRY_DATE#"
			+ "PLACE_OF_EXPIRY#UNDER_TOLERANCE#ABOVE_TOLERANCE#CREDIT_MODE#DRAFT_TENOR#"
			+ "DRAFT_CREDIT_DAYS_FROM_DT#DRAFT_CREDIT_DAYS_FROM_DAYS#DRAFT_AMOUNT#"
			+ "DRAFT_SPECIFY_OTHERS#DRAFT_DRAWEE_BANK#DEFERRED_PAYMENT#PARTIAL_SHIPMENT#"
			+ "PARTIAL_SHIPMENT_CONDITION#TRANSSHIPMENT#TRANSSHIPMENT_CONDITION#"
			+ "PORT_OF_LOADING#PORT_OF_DISCHARGE#SHIPMENT_PERIOD#LATEST_SHIPMENT_DATE#"
			+ "SHIPMENT_FROM#SHIPMENT_TO#GOODS_CODE#DESCRIPTION_OF_GOODS#INCOTERM#"
			+ "REQUESTED_CONFIRMATION_PARTY#CHARGES#SPL_PAY_COND_FOR_BEN#PERIOD_OF_PRESENTATION_DAYS#"
			+ "PERIOD_OF_PRESENTATION_MODE#CONFIRMATION_INSTRUCTION";
	public static final String GRNT_AM_AMEND_FRAME_FIELDS = "TRANSACTION_AMOUNT#EXPIRY_TYPE#EXPIRY_COND#"
			+ "EXPIRY_DATE#CNTR_GTEE_EXP_DATE#OTHER_AMENDMENTS#DELIVERY_INSTR";
	public static final String SBLC_AM_AMEND_FRAME_FIELDS = "TRANSACTION_AMOUNT#EXPIRY_TYPE#EXPIRY_COND#"
			+ "EXPIRY_DATE#OTHER_AMENDMENTS#DELIVERY_INSTR#CONFN_INTRN#REQ_CONF_PARTY";
	public static final String SBLC_AM_ONLY_AMEND_FRAME_FIELDS = "TRANSACTION_AMOUNT#EXPIRY_TYPE#EXPIRY_COND#"
			+ "EXPIRY_DATE#OTHER_AMENDMENTS#DELIVERY_INSTR";
	public static final String GRNT_GAA_AMEND_FRAME_FIELDS = "TRANSACTION_AMOUNT#EXPIRY_TYPE#EXPIRY_COND#"
			+ "EXPIRY_DATE#OTHER_AMENDMENTS#DELIVERY_INSTR";
	public static final String SBLC_AM_LC_FRAME_FIELDS = "LC_FRAME#LC_DOC_COURIER";
	public static final String SBLC_AM_LC_FRAME_HIDE_FIELDS = "LC_CONF_AMNT#LC_CORRSPNDNT_BNK#LC_UNDER####LC_ABOVE#LC_TOLERANCE";

	//PM Fields Constant
	public static final String BUTTON_SF_CLOSE = "SF_Close_Button";
	public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
	public static final String ACCOUNT_VALID = "IS_ACC_VALID";
	public static final String DISC_DETAILS = "btnDiscDetails";
	public static final String ADD_COUNTER_PARTY = "btnCPDAdd";
	public static final String TRANSACTION_TENOR = "TRN_TENOR";
	public static final String TAB_CLICK = "TABCLICK";
	public static final String BUTTON_ADD_PARTY_DETAILS = "btnAddPartyDetails";
	public static final String BUTTON_MODIFY_PARTY_DETAILS = "btnModifyPartyDetails";
	public static final String BUTTON_ADD_CONTRACT_DETAILS = "btnAddContractLimits";
	public static final String BUTTON_MODIFY_CONTRACT_DETAILS = "BTNMODIFYCONTRACTLIMITS";
	public static final String BUTTON_SAVE = "btnSave";
	public static final String BUTTON_ADD_LLI = "Btn_Add_LLI";
	public static final String BUTTON_MODIFY_LLI = "Btn_Modify_LLI";
	public static final String BUTTON_FETCH_LLI = "BTN_LLI_FETCH";
	public static final String[] TABLENAMELIST = {"TFO_DJ_LLI_BASIC_VSSL_DTLS","TFO_DJ_LLI_BASIC_VSSL_TEMP","TFO_DJ_LLI_OWNERSHIP_DTLS","TFO_DJ_LLI_MVMNT_DTLS","TFO_DJ_LLI_MVMNT_DTLS_SIGHTING","TFO_DJ_LLI_MVMNT_DTLS_CALLING","TFO_DJ_INTEGRATION_CALLS_DTLS","TFO_DJ_INTEGRATION_CALLS","TFO_DJ_LLI_MVMNT_ETA_DTLS"};
	public static final String BUTTON_GENERATE_LLI_PDF = "Btn_Gen_PDF";
	public static final String BUTTON_PARTY_DETAIL_FETCH = "btnPDFetch";
	public static final String FORM_CONTROLS_PARTY = "TFO_PARTY_TYPE,TFO_PARTY_DESC,TFO_PD_PARTY_ID,TFO_PD_CUSTOMER_NAME,"
			+ "TFO_PD_ADDRESS1,TFO_PD_ADDRESS2,TFO_PD_ADDRESS3,TFO_PD_ADDRESS4,TFO_PD_COUNTRY,"
			+ "TFO_MEDIA_TYPE,TFO_PD_ADDRESS";
	public static final String LISTVIEW_CONTROLS_PARTY = "PartyType,PartyDescription#DescriptionValue,PartyID,CustomerName,"
			+ "Address1,Address2,Address3,Address4,Country,MediaType,MediaAddress,WI_Name";
	public static final String LISTVIEW_PARTY = "Qvar_Party_Details";
	public static final String LISTVIEW_CONTRACT_LIMITS = "Qvar_Contract_Limits";
	public static final String FORM_CONTROLS_CONTROL_LIMITS = "cl_serialNumber,cl_combo_operation,cl_combo_partyType,cl_customer_no,"
			+ "cl_combo_type,cl_linkageRefNo,cl_per_contribution,cl_combo_amountTag";
	public static final String LISTVIEW_CONTROL_LIMITS = "SerialNumber,Operation,PartyType,CustomerNo,Type,LinkageReferenceNo,"
			+ "PercentContribution,AmountTag";
	public static final String LISTVIEW_LLI ="Qvar_LLI_Details";
	public static final String LISTVIEW_LLI_CONTROLS = "VesselName,ShippedOnDate,VesselFlag,VesselID,VesselIMO,SNo";
	public static final String FORM_CONTROLS_LLI = "TXT_VESSELNAME,SHIPMENT_DATE,TXT_VESSELFLAG,TXT_VESSELID,TXT_VESSELIMO";
	public static final String LISTVIEW_FINAL_DECISION = "Qvar_Final_Desc_PPM";
	public static final String LISTVIEW_FINAL_DECISION_COLUMNS ="Module,Referredto,Decision,ExceptionRemarks,ExistingEmailID,"
			+ "AdditionalEmailID";
	public static final String  SUBFORM_FINAL_DECISION_ID="subForm_Final_Decision_ID";
	public static final String FINAL_DECISION_GRID_ADDITIONAL_MAIL =  "QVAR_FINAL_DESC_PPM_NEW_MAIL";
	public static final String TR_FETCH_RATE_BUTTON ="FETCH_RATE";
	public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
	public static final String DUP_CHK_CONFIRMATION = "DUP_CHK_CONFIRMATION";
	public static final String AMEND_TYPE = "AMEND_TYPE";
	public static final String FCUBS_CON_NO = "FCUBSConNo";
	public static final String LIMIT_PARTY_TYPE = "LIMIT_PARTY_TYPE";
	public static final String CREATE_AMEND_CNTRCT_FCUBS = "CREATE_AMEND_CNTRCT_FCUBS";
	public static final String TXT_LLI_VESSELNAME = "TXT_LLI_VESSELNAME";
	public static final String LLI_SHIPMENT_DATE = "LLI_SHIPMENT_DATE";
	public static final String PD_CUSTOMER_ID = "TFO_PD_CUSTOMER_ID";
	public static final String GRNT_ACC_CONTROLS = "GRNT_CHRG_ACC_TITLE,GRNT_CHRG_ACC_CRNCY";
	public static final String GRNT_CHRG_ACC_TITLE = "GRNT_CHRG_ACC_TITLE";
	public static final String GRNT_CHRG_ACC_CRNCY = "GRNT_CHRG_ACC_CRNCY";
	public static final String OPERATION_CODE = "OPERATION_CODE";
	public static final String PC_FCUBS_REF = "PC_FCUBS_REF";
	public static final String LLI_CHK_OK = "LLI_CHK_OK";
	public static final String COMP_POSITIVE_MATCH = "COMP_POSITIVE_MATCH";
	public static final String COMP_REF = "COMP_REF";
	public static final String TR_REFER_TREASURY = "TR_REFER_TREASURY";
	public static final String EXP_DATE = "EXP_DATE";
	public static final String TRN_TENOR = "TRN_TENOR";
	public static final String FRM_LLI = "FRM_LLI";
	public static final String COMP_EXP_REMARKS = "COMP_EXP_REMARKS";
	public static final String BILL_PRODUCT_CODE = "BILL_PRODUCT_CODE";
	public static final String CL_SERIAL_NUMBER = "cl_serialNumber";
	public static final String CL_PER_CONTRIBUTION = "cl_per_contribution";
	public static final String CL_COMBO_OPERATION = "cl_combo_operation";
	public static final String CL_COMBO_PARTY_TYPE = "cl_combo_partyType";
	public static final String CL_CUSTOMER_NO = "cl_customer_no";
	public static final String CL_COMBO_TYPE = "cl_combo_type";
	public static final String CL_COMBO_AMOUNT_TAG = "cl_combo_amountTag";
	public static final String TRN_THIRD_PARTY = "TRN_THIRD_PARTY";
	public static final String PARTY_TYPE = "TFO_PARTY_TYPE";
	public static final String PARTY_DESC = "TFO_PARTY_DESC";
	public static final String PD_PARTY_COUNTRY = "TFO_PD_COUNTRY";
	public static final String TABLE6_PARTY_DESC = "table6_Party Description";
	public static final String ROUTE_TO_PM = "ROUTE_TO_PM";
	public static final String CREATE_AMEND_STATUS_FCUBS = "CREATE_AMEND_STATUS_FCUBS";
	public static final String DELIVERY_BRANCH = "DELIVERY_BRANCH";
	public static final String NEW_TRN_AMT ="NEW_TRN_AMT";
	public static final String NEW_EXP_DATE = "NEW_EXP_DATE";
	public static final String TXNC = "TXNC";
	public static final String TR_SELL_AMT = "TR_SELL_AMT";
	public static final String UI_EXCHANGE_RATE = "UI_EXCHANGE_RATE";
	public static final String LIST_VIEW_BUTTONS = "btnNext,btnPrevious,btnSave";
	public static final String LIST_VIEW_BUTTONS2 = "btnSubmit,btnPrevious,btnSave";
	public static final String LIST_VIEW_BUTTONS3 = "btnPrevious,btnSave,btnNext";
	public static final String LIST_VIEW_BUTTONS4 = "btnPrevious,btnSubmit,btnSave";
	public static final String TR_LOAN_AMT = "TR_LOAN_AMT";
	public static final String BILL_LN_REFRNCE = "BILL_LN_REFRNCE";
	public static final String BILL_RVSD_DOC_AVL = "BILL_RVSD_DOC_AVL";
	public static final String COMP_CHECK_CONTROLS = "COMP_POSITIVE_MATCH,COMP_CHK_DONE,COMP_REF,COMP_EXP_REMARKS,LLI_CHK_OK";
	public static final String COMP_IMB_CHK = "COMP_IMB_CHK";
	public static final String COMP_CHK_DONE = "COMP_CHK_DONE";
	public static final String TR_EXCEPTIONS = "TR_EXCEPTIONS";
	public static final String TRADE_CUST_EMAIL_ID = "TRADE_CUST_EMAIL_ID";
	public static final String FCR_CUST_EMAIL_ID = "FCR_CUST_EMAIL_ID";
	public static final String TR_SELL_CUR = "TR_SELL_CUR";
	public static final String TR_BUY_CUR = "TR_BUY_CUR";
	public static final String PD_CUSTOMER_NAME = "TFO_PD_CUSTOMER_NAME";
	public static final String PD_PARTY_ID = "TFO_PD_PARTY_ID";
	public static final String MANUALLY_TICKED_MAIL = "MANUALLY_TICKED_MAIL";
	public static final String BILL_CUST_HLDING_ACC_US = "BILL_CUST_HLDING_ACC_US";
	public static final String DISCOUNT_ON_ACCEP="DISCOUNT_ON_ACCEP";
	public static final String COURIER_COMPANY="COURIER_COMPANY";
	public static final String COURIER_AWB_NUMBER="COURIER_AWB_NUMBER";
	public static final String INITIATOR_USERID="INITIATOR_USERID";
	public static final String PM_VERIFY_SHEET_ID="0";
	public static final String PM_INPUT_SHEET_ID="1";
	public static final String PM_CUSTOMER_SHEET_ID="2";
	public static final String PM_COUNTER_SHEET_ID="3";
    public static final String PM_COMPLIANCE_SHEET_ID="4";
	public static final String PM_TREASURY_SHEET_ID="5";
	public static final String PM_PARTY_SHEET_ID="6";
	public static final String PM_TRSD_SHEET_ID="7";
	public static final String PM_INVOICE_DETAIL_SHEET_ID="8";
    public static final String PM_DEC_SHEET_ID="9";
    public static final String BTN_START_TRSD="BTN_START_TRSD";
    public static final String BTN_IFRAME_CLOSE = "BTN_IFRAME_CLOSE";
    public static final String TRSD_WI_NAME="TRSD_WI_NAME";
    public static final String LISTVIEW_TRSD_TABLE ="TRSD_TABLE";
    public static final String LISTVIEW_TRSD_TABLE_OPTIONAL="TRSD_TABLE_OPTIONAL";
    public static final String TRSD_FLAG = "TRSD_FLAG";
    public static final String EXPIRY_COND = "EXPIRY_COND";
    public static final String FCUBS_PUR_OF_MSG = "FCUBS_PUR_OF_MSG";
    
    


	//PC fields
	public static final String FRM_LLI_FIELDS = "TXT_LLI_VESSELNAME,LLI_SHIPMENT_DATE,BTN_LLI_FETCH,Btn_Gen_PDF,Btn_Add_LLI,Btn_Del_LLI";
	public static final String MANUAL_TAB_CLICK  = "onClickTabPM";
	public static final String MANUAL_TAB_CLICK_PC = "onClickTabPC";
	public static final String PROCESSING_CHECKER = "PROCESSING CHECKER";
	
	//MISC
	public static final String MAIN_TAB = "tab1";
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	public static final String FIELD_SWIFT_CID = "SWIFT_CID";
	
	//INITIATOR
	public static final String TRNS_REF_NO = "TrnsRefNo";
	public static final String TRNS_BAL = "TRNS_BAL";
	public static final String REQ_DATE_TIME = "REQ_DATE_TIME";
	public static final String SWIFT = "SWIFT";
	public static final String MAX_LENGTH_MSG = "Please enter correct Transaction Ref. Number of max length 16";
	public static final String ENTER_CID_MSG = "Please enter correct CID";
	public static final String WHERE_REQ_TYPE = " AND REQ_TYPE = '";
	public static final String PT_CUSTOMER_IC = "PT_CUSTOMER_IC";
	public static final String PT_UTILITY_FLAG = "PT_UTILITY_FLAG";
	//PROCEDURE CALL
	public static final String PARAM_TEXT = "Text :";
	
	//COMMON
	public static final String SANC_SCRN_MATCH_FOUND = "SANC_SCRN_MATCH_FOUND";
	public static final String TRN_STATUS = "TRN_STATUS";
	public static final String  IMB_DETAIL_FRM = "IMB_DETAIL_FRM";
	public static final String  PT_TRANSACTION_AMOUNT = "PT_TRANSACTION_AMOUNT";
	public static final String  Q_AMENDMENT_DATA_USER_TRANSACTION_AMOUNT = "Q_Amendment_Data_USER_TRANSACTION_AMOUNT";
	public static final String  Q_AMENDMENT_DATA_USER_EXPIRY_DATE = "Q_Amendment_Data_USER_EXPIRY_DATE";
	public static final String Q_AMENDMENT_DATA_USER_EXPIRY_TYPE ="Q_Amendment_Data_USER_EXPIRY_TYPE";
	public static final String Q_AMENDMENT_DATA_USER_EXPIRY_COND = "Q_Amendment_Data_USER_EXPIRY_COND";
	public static final String Q_AMENDMENT_DATA_USER_CNTR_GTEE_EXP_DATE ="Q_Amendment_Data_USER_CNTR_GTEE_EXP_DATE";
	public static final String Q_AMENDMENT_DATA_FIN_CNTR_GTEE_EXP_DATE="Q_Amendment_Data_FIN_CNTR_GTEE_EXP_DATE";
	public static final String  BILL_TYPE ="BILL_TYPE";
	public static final String  CUST_INSTRUCTION_SECTION = "CUST_INSTRUCTION_SECTION";
	public static final String  IF_SIGHT_BILL = "IF_SIGHT_BILL";
	public static final String  DISCREPANCY_INSTRUCTION ="DISCREPANCY_INSTRUCTION";
	public static final String  SETTLEMENT_INSTRUCTION ="SETTLEMENT_INSTRUCTION";
	
	//TSLM
	public static final String STANDALONE_LOAN = "STANDALONE_LOAN";
	public static final String PROCESSING_SYSTEM = "PROCESSING_SYSTEM";
	public static final String TSLM_COMPANY_TYPE = "TSLM_COMPANY_TYPE";
	public static final String LISTVIEW_TSLM_CPD = "Q_TSLM_Counter_Det";
	public static final String TSLM_FINANCED_AMOUNT = "FINANCED_AMOUNT";
	public static final String TSLM_OVERALL_OUTSTANDING_AMOUNT = "OVERALL_OUTSTANDING_AMOUNT";
	public static final String TSLM_LOAN_STAGE = "LOAN_STAGE";
	public static final String TSLM_IF_PURCHASE_PRODUCT_CODE = "IF_PURCHASE_PRODUCT_CODE";
	public static final String TSLM_FUNDING_REQUIRED = "FUNDING_REQUIRED";
	public static final String TSLM_ADDITIONAL_LOAN_AMOUNT = "ADDITIONAL_LOAN_AMOUNT";
	public static final String TSLM_LOAN_AMOUNT = "LOAN_AMOUNT";
	public static final String TSLM_INVOICE_CHK_CONFIRM = "TSLM_INVOICE_CHK_CONFIRM";
	public static final String PM_TSLM_PUSH_MSG_FLAG = "PM_TSLM_PUSH_MSG_FLAG";
	public static final String TRSD_SCREENING_DATA_BTN = "TRSD_SCREENING_DATA_BTN";
	public static final String FCUBS_ANY_PAST_DUE_CID = "FCUBS_ANY_PAST_DUE_CID";
	public static final String PAST_DUE_LIABILITY = "PAST_DUE_LIABILITY";    //SCF1 CHANGES   ATP 200
	public static final String Qvar_cpd_details = "Qvar_cpd_details";
	public static final String Q_TSLm_Counter_Dets = "Q_TSLm_Counter_Dets";
	
	//Added by Shivanshu ATP-458
	//MT798 Field Constant
	public static final String DR_ACC_NUM = "DR_ACC_NUM";
	public static final String SWIFT_CHANNEL = "SWIFT_CHANNEL";
	public static final String HYBRID_CUSTOMER = "Hybrid_Customer"; ////ATP-459 11-JUL-2024 --JAMSHED START
	public static final String SKIP_TRAYDSTREAM = "SKIP_TS_FLAG"; ////ATP-459 11-JUL-2024 --JAMSHED START
	public static final String TS_JUSTIFICATION = "TS_JUSTIFICATION"; ////ATP-459 11-JUL-2024 --JAMSHED START
	public static final String TS_REQUIRED = "IS_TS_REQUIRED"; ////ATP-459 11-JUL-2024 --JAMSHED START
    public static final String LISTVIEW_DISCREPANCY_DETAILS="Discrepancy_Details";
    public static final String IFRAME_DISCREPANCY="IFRAME_DISCREPANCY";




}