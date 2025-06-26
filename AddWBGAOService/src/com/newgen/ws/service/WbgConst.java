package com.newgen.ws.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import com.newgen.ws.util.ConnectSocket;
import com.newgen.wbg.ws.crs.request.CRSGeneric;
import com.newgen.wbg.ws.crs.request.CrsDetails;
import com.newgen.wbg.ws.crs.request.EntityControlPersonsDtls;
import com.newgen.wbg.ws.crs.request.TaxResidenceCountriesDtls;
import com.newgen.wbg.ws.crs.request.controlPersonTaxResCountryDtls;
import com.newgen.wbg.ws.fatca.request.Fatca;
import com.newgen.wbg.ws.fatca.request.FatcaDetails;
import com.newgen.wbg.ws.fatca.request.FatcaOwnerShipDtls;
import com.newgen.wbg.ws.kyc.request.AddressProofInfo;
import com.newgen.wbg.ws.kyc.request.AffiliatedEntityInfo;
import com.newgen.wbg.ws.kyc.request.AntiAccActivityInfo;
import com.newgen.wbg.ws.kyc.request.CashPickupService;
import com.newgen.wbg.ws.kyc.request.CommercialInfo;
import com.newgen.wbg.ws.kyc.request.CorssBorderPaymentsDtls;
import com.newgen.wbg.ws.kyc.request.EnhancedDueDeligenceInfo;
import com.newgen.wbg.ws.kyc.request.ExpectedMonthlyDepositsDtls;
import com.newgen.wbg.ws.kyc.request.GenInformation;
import com.newgen.wbg.ws.kyc.request.KYC;
import com.newgen.wbg.ws.kyc.request.KYCGeneric;
import com.newgen.wbg.ws.kyc.request.LegEntRiskAssessment;
import com.newgen.wbg.ws.kyc.request.MajorClientsDtls;
import com.newgen.wbg.ws.kyc.request.MajorSuppliersDtls;
import com.newgen.wbg.ws.kyc.request.OwnershipDetails;
//Added by SHivanshu ATP-426
import com.newgen.wbg.ws.kyc.request.RISKDetails;
import com.newgen.wbg.ws.kyc.request.RISKInfo;
import com.newgen.wbg.ws.kyc.request.RiskAssessment;
import com.newgen.wbg.ws.kyc.request.SignOff;
import com.newgen.wbg.ws.kyc.request.SubsidiariesDetails;
import com.newgen.wbg.ws.kyc.request.UBODetails;
import com.newgen.wbg.ws.kyc.request.UboRiskAssessment;
import com.newgen.ws.request.AccountInfo;
import com.newgen.ws.request.AddressCorrDets;
import com.newgen.ws.request.AddressPhyDets;
import com.newgen.ws.request.AttributeDetails;
import com.newgen.ws.request.ApplicationAttributes;
import com.newgen.ws.request.Attributes;
import com.newgen.ws.request.Company;
import com.newgen.ws.request.CompanyDetails;
import com.newgen.ws.request.CompanyInformation;
import com.newgen.ws.request.ContactInfo;
import com.newgen.ws.request.CutomerPersonalDtls;
import com.newgen.ws.request.Documents;
import com.newgen.ws.request.DocumentsDtls;
import com.newgen.ws.request.Header;
import com.newgen.ws.request.IncorporationDets;
import com.newgen.ws.request.KeyContactsInfo;
import com.newgen.ws.request.UBOwnerInfo;
import com.newgen.ws.util.XMLParser;

public class WbgConst {
	// protected Header Header;
	// 	WMH Static header parameters
	protected String FolderIndex="";
	protected char fieldSep = ((char)21);
	protected char recordSep =((char)25);	
	protected String procMoveUpdate="SP_WBG_MOVE_AND_UPDATE";
	protected String procDelete="SP_WBG_DELETE";
	protected String procOldDocMove="SP_WBG_MOVE_OLDDOC_TMPDOC";
	protected ArrayList<String> aValuesNotAllowed ;	
	protected Map<String, String> mReqParamMap;
	protected Header Header;
	protected UBODetails [] uboDetails;	
	protected boolean NTBAusFound=false;
	protected boolean NTBEntityFound=false;
 
	protected OwnershipDetails [] ownershipDetails;
	protected MajorSuppliersDtls [] majorSuppliersDtls;
	protected MajorClientsDtls [] majorClientsDtls;	
	protected AffiliatedEntityInfo [] affiliatedEntityInfos;
	protected SubsidiariesDetails [] subsidiariesDetails;
	protected UBOwnerInfo [] UBOInfo1;
	protected EntityControlPersonsDtls [] entityControlPersonsDtls;
	protected controlPersonTaxResCountryDtls[] controlPersonTaxResCountryDtls;
	protected TaxResidenceCountriesDtls [] taxResidenceCountriesDtls;

	protected FatcaOwnerShipDtls [] FatcaOwnerShipDtls;
	protected CutomerPersonalDtls [] CutomerPersonalDtls;
	protected DocumentsDtls DocumentsDtls;	
	protected Documents [] Documents;	
	protected Fatca Fatca;
	protected IncorporationDets IncorporationDets;	
	protected AntiAccActivityInfo AntiAccActivityInfo;
	protected CashPickupService CashPickupService;
	protected CommercialInfo CommercialInfo;
	protected CorssBorderPaymentsDtls CorssBorderPaymentsDtls ;
	protected EnhancedDueDeligenceInfo EnhancedDueDeligenceInfo;
	protected ExpectedMonthlyDepositsDtls ExpectedMonthlyDepositsDtls;
	protected GenInformation GenInformation;
	protected AddressProofInfo AddressProofInfo;
	protected RiskAssessment kycRiskAssessment;
	protected LegEntRiskAssessment LegEntRiskAssessment;
	protected SignOff kycSignOff;
	protected UboRiskAssessment UboRiskAssessment;	

	protected XMLParser xmlobj = null;
	protected Map<String, String> mIndexNameToIndexValue = null;
	protected Map<String, String> mIndexNameToIndexId = null;

	protected Map<String, String> mIndexNameToIndexValue1 = null;
	protected final String CONNECTIONS = "USR_0_WBG_WebCONNECTION";

	protected static long iTotalTime = 0;

	protected static String sCabinetName = "";
	protected static String sPassEnc = "";
	protected static String isCallDocTrc="";
	protected static String sJtsIp = "";
	protected static int iJtsPort;
	//protected static String sJtsPort ="";		
	protected static String sIniActName="";
	protected static String sIniActId="";
	protected static String sProcessDefId="";	
	protected static String trsdURL;
	//protected static String wport="";

	protected String SourceChannel;
	protected String SourcingCentre;
	protected String LeadRefNo;
	protected String LeadSubDtTm;
	protected String HomeBranch;
	protected String IterationCount;
	protected String Iteration;
	protected String ReqType;
	protected String QueueName;
	protected String InitiatorName;
	protected String AppRiskCls;
	protected String LastProcessedByUserName;
	protected String compRMName;
	protected String compRMCode;
	protected String RMEmail;
	protected String RMMobileNo;
	protected String RMMngName;
	protected String RMMngCode;
	protected String RMMngEmail;
	//added by reyaz 
	protected String itrationValue;
	protected String reworkValue;
	protected String duallaneValue;
	//added by sharan 01/07/2020

	protected String leadType;
	//CR2019
	protected String InitiatorBy="";
	protected String InitiatedDept="";
	protected String SourcingDSACode="";
	protected String SourceOfLead="";
	protected KeyContactsInfo [] KeyContactInfo1;

	// 	request parameters
	protected String leadRefNumber;

	// REQUEST PARAMETERS
	protected KYC KYC;
	protected KYCGeneric KYCGeneric;
	protected CrsDetails CrsDetails;
	protected CRSGeneric CrsGeneric;
	protected Company Company;
	protected CompanyInformation CompanyInformation;
	protected CompanyDetails CompanyDetails;
	protected AddressCorrDets AddressCorrDets;
	protected AddressPhyDets AddressPhyDets;
	protected ContactInfo ContactInfo;
	protected FatcaDetails FatcaDetails;

	//Attribute Request
	protected AttributeDetails[] AttributesDetails;
	protected Attributes[] Attributes;

	//protected ApplicationAttributes ApplicationAttributes;
	protected String attributeKey;
	protected String attributeValue;

	//
	//protected AttribAUSDetails[] attribAUSDetails;
	//protected AUSattrib[] AUSattrib;

	protected String attribAUSKey;
	protected String attribAUSValue;

	//	Account Detail Request	
	protected AccountInfo [] AccountInfo;

	protected String AccStatus;
	protected String ProdCode;
	protected String ProdCurr;
	protected String AccBranch;
	protected String ChequeReq;
	protected String ModeFunding;
	protected String DebitAccNo;
	protected String DebitAmt;
	protected String DebitCurr;
	protected String AvBal;
	protected String srvChrgPkg;
	//added by sheenu 07_10_2019
	protected String debitCardReq;
	protected String operatingInstructions;
	protected String modeOfOperation;
	protected String accountType;
	protected String accountTitle;
	protected String cardHolderName;
	protected String debitCompName;
	protected String eStmtFlag;
	protected String accntIVRFlag;
	protected String waiverChargesFlag;
	// added by sahil for new tags in account 8-apr-2020
	protected String cardProductGroup;
	protected String noofchequeBooks;
	protected String bookSize;
	protected String Charges;
	protected String ChargesWaivedFlag;
	protected String WaiverReason;
	protected String OtherReason;
	protected String customerAddresszip;
	protected String customerAddress1;
	protected String customerAddress2;
	protected String customerAddressCountry;
	protected String customerAddressStateEmirate;
	protected String customerAddressCity;
	protected String customerMobileNo;
	protected String flagDeliveryMode;
	protected String CouriertoCustomer_3rdParty;
	protected String CollectBranch;
	protected String thirdPartyName;
	protected String thirdPartyMobileNo;
	protected String photoIdType;
	protected String photoIdNo;	
	// more new tags - sahil
	protected String ATM_Flag;
	protected String SI_Flag;
	protected String IVR_Facility;
	protected String IB_Facility;
	protected String POS_Facility;
	//KYC Generic Variable Declaration Start Here 

	//	Address Info
	protected String IsTradeContract ="";
	protected String IsUtilityBill="";
	protected String IsBankAccStmt = "";
	protected String IsPhoneBill = "";
	protected String Others="";
	//	Cross Border Payment	
	protected String IsCBPayment ="";
	protected String PurposeOfPayment ="";
	protected String NoOfPaymentPerMonth ="";
	protected String MonthlyValPayment = "";
	protected String CntryMadetoRcvdFrom ="";
	//	Cash pick service provide	
	protected String IsServiceReq = "";
	protected String ServiceProviderName ="";
	//	Commercial Info	
	protected String IncorporationPlace = "";
	protected String FormationDate= "";
	protected String PhyBusinessLocation= "";
	protected String IsAnnualIncomeActualAED= "";
	protected String ISAnnualIncomeEstAED = "";
	protected String PrimaryTradeLocation= "";
	protected String NoofUaeBranch = "";
	protected String UaeBrachPrimaryLocation= "";
	protected String NoOfNonUaeBranch= "";
	protected String NonUaeBrachPrimaryLocation= "";

	protected String TotalAssetAED  = "";
	protected String AnnualSalRevnueAED= "";
	protected String NetProfitAED= "";
	//	Enhanced Due Deligence Info
	protected String IsClientVisit = "";
	protected String DateOfVisit = "";
	protected String LocationOfVisit= "";
	protected String AdcbStaffName = "";
	protected String PersonMetWith = "";
	protected String TypeOfCompany =  "";
	protected String TypeOfComSetup = "";
	protected String TypeOfCompStrength= "";
	protected String TypeOfCompLocation=  "";
	protected String ActivityConducted = "";
	protected String IsOptSubEntity=  "";
	protected String OptSubEntity=  "";
	protected String RmAssessment = "";
	//	GenInformation
	protected String CIF= "";
	protected String ProfilingPurpose = "";
	protected String RelationshipSince= "";
	protected String BusinessNature= "";
	protected String PurposeAOADCB = "";
	protected String OverAllRisk=  "";	
	//Addded by Shivanshu
	protected String ActiveAccountFlag= "";
	protected String PurposeAOADCBOthers = "";
	protected String IsDualGoods = "";
	protected String DualGoodsType = "";
	//	Legal Entity Risk Assessment
	protected String IsOffshoreLocation = "";
	protected String IsFreezone= "";
	protected String IsAMLCFTPolicy= "";
	protected String IsNUaeTradArmWeapons= "";
	protected String IsNUaedefRelEqup=  "";
	protected String IsHawala = "";
	protected String IsSanctioned= "";
	protected String IsIncRiskJuri= "";
	protected String IsShellCompany= "";
	protected String IsDeemedTaxEvasion = "";
	protected String IsBusLnkSanctioned = "";
	protected String IsVertualFlexiDesk=  "";
	protected String IsNomineeOwnShipStr =  "";
	protected String IsIssuedBearerShare = "";
	protected String IsVertualCryptoCur = "";
	protected String IsNUaeOwnedEnty ="";
	protected String IsNUaePep = "";
	protected String IsPEPRiskQ3 =""; //Added by Shivanshu
	//	Ubo Risk Assessment
	protected String IsOverAllRiskInc = "";
	protected String IsOverAllRiskUnact = "";
	protected String IsUboHawala=  "";
	protected String IsUboBusTradLnkSanctioned = "";
	protected String SourceOfFund=  "";
	//	Staff Name
	protected String bGHName = "";
	protected String bUHName = "";
	protected String lMName = "";
	protected String staffName = "";

	protected String IsCashCDM;
	protected String CashCDM;
	protected String IsChequesDraft;
	protected String ChequesDraft;
	protected String IsTransfers;
	protected String Transfers;
	//End her for KYC generic variable


	//CRS Generic Variable Declaration Start Here 

	protected String crs_CustomerType;
	protected String crsCustId;
	protected String crsCustFirstName;
	protected String crsCustLastName;
	protected String crsCustBirthCity;
	protected String crsClassificationId;
	protected String crsClassificationDate;
	protected String crsEntityTypeId;
	protected String crsChannel;
	protected String crsMakerId;
	protected String crsMakerDate;
	protected String crsCertFormObtained;
	protected String crsTermsAndCondAccepted;
	protected String crsResidenceAddressConfirmationStatus;
	protected String crsRelRefNo;
	protected String crsCertificationDate;
	// added by sharan for new tags 29-jun-2020
	protected String crsUpdateFlag;
	//		End here 	  

	//	Entity Details
	protected String CompIC;
	protected String CompCat;
	protected String CompID;
	protected String CompFullName;
	protected String CompPrefix;
	protected String compBrCode;
	protected String compCntryIncp;
	protected String TlNo;
	protected String TlIssDt;
	protected String TlExpDt;
	protected String compCmMemNo;
	protected String compIncorpDate;
	protected String compSysRiskCls;
	protected String compRMRiskCls;
	protected String compRMRiskClsDt;
	protected String compCB1Code;
	protected String compCB2Division;
	protected String compCB2GrpId;
	protected String compCB2PeerId;
	protected String compTypBusDesc;
	protected String compProfitCenter;
	protected String compAcctCls;
	protected String cRMName;
	protected String cRMCode;
	protected String compAnSaleRevnue;
	protected String compNoOfEmp;
	protected String compBankName1;
	protected String compBankName2;
	protected String compBankName3;
	protected String compCityCntry1;
	protected String compCityCntry2;
	protected String compCityCntry3;
	protected String compAppRMName;
	protected String compAppRMCode;
	protected String compSecRMName;
	protected String compSecRMCode;
	protected String compAccMOrgNRcvd;
	protected String compdt1SrOrgNRcvd;
	protected String compCSCFrmRcvd;
	protected String compSrcTurnOver;
	protected String compCorr_AddrsLine1;
	protected String compCorr_AddrsLine2;
	protected String compCorr_AddrsLine3;
	protected String compCorr_State;
	protected String compCorr_City;
	protected String compCorr_Country;
	protected String compPhy_AddrsLine1;
	protected String compPhy_AddrsLine2;
	protected String compPhy_AddrsLine3;
	protected String compPhy_State;
	protected String compPhy_City;
	protected String compPhy_Country;
	protected String compCustSrvChrgPkg;
	// added by sahil for new tags 8-apr-2020
	protected String MobileNumber;
	protected String RegPlaceKey;
	protected String RegPlaceValue;
	protected String EconomicActivityKey;
	protected String EconomicActivityValue;

	// added by sharan for new tags 01/07/2020
	protected String noOfSignatures;
	protected String tlIssuingAuthority;
	protected String tlUpdateFlag;

	protected String OffNumber;
	protected String OffNumber2;
	protected String Mobile;
	protected String Fax;
	protected String Email;
	protected String Website;
	protected String fatca_customerClsfctn;
	protected String fatca_natureOfEntity;
	protected String fatca_typeOfEntity;
	protected String fatca_FATCAStatus;
	protected String fatca_documentCollected;
	protected String fatca_dateOfDocument;
	protected String fatca_idntfctnNumRequired;
	protected String fatca_idntfctnNumber;
	protected String fatca_customerClsfctnDate;
	protected String fatca_customerFATCAClsfctnDate;
	// added by sharan for new tags 29-jun-2020
	protected String fatca_fatcaUpdateFlag;

	//	AUS Customer parameters
	protected String custtype;
	protected String address;
	protected String addLine1;
	protected String addLine2;
	protected String corCntry;
	protected String ResCntry;
	protected String custCat;
	protected String custFullName;
	protected String custIC;
	protected String custID;
	protected String custPrefix;
	protected String dob;
	protected String eidaIssDt;
	protected String eidaExpDt;
	protected String eidaNo;
	protected String gender;
	protected String homeBrCode;
	protected String maritalSts;
	protected String nationality;
	protected String profitCenter;
	protected String passExpDt;
	protected String passIssDt;
	protected String passport;
	protected String signTyp;
	protected String state;
	protected String telLandLine;
	protected String telMob;
	protected String town;
	protected String visaExpDt;
	protected String visaNo;
	protected String visaIssDt;
	protected String zip;
	//added new fields sharan 01/07/2020
	protected String sigSeqNo;           
	protected String sigUpdReq ;
	//added new fields sharan 12/07/2020
	protected String emailId;
	//added new fields reyaz 9/05/2022
	protected String requestClassification;
	protected String rmCode;
	protected String custUpgradeflg;
	protected String embossName;
	//added new fields reyaz 7/06/2022
	protected String instantFlag;
	protected String customerClassification;
	protected String nameUpdateFlag;
	protected String countryResidenceFlag;
	protected String countryCorrespondanceFlag;
	protected String passportUpdateFlag;
	protected String visaUpdateFlag;
	protected String mobileUpdateFlag;
	protected String emailUpdateFlag;
	//Added by SHivanshu ATP-426
	protected String secondNationality;
	protected String pepFlag;
	protected String extrafield1;
	protected String extrafield2;
	protected RISKDetails [] riskDetails;
	protected String A1LegalEntity;
	protected String A2EntityType;
	protected String A3Industry;
	protected String A4AccountOpeningPurpose;
	protected String A5AgeofBusiness;
	protected String A6ComplexOwnershipStructure;
	protected String A7AccountinOtherBanks;
	protected String A8PepStatus;
	protected String B1CountryofIncorporation;
	protected String B2Jurisdiction;
	protected String B3CountryofOperation;
	protected String B4Nationality;
	protected String B5Residence;
	protected String C1Onboarding;
	protected String C2Product;
	protected String C3Channel;
	//mobile update falg in ext table
	protected String mobileCCFlag;

	protected String DocName;
	protected String DocType;
	protected String DocumentFor;
	protected String DocumentIndex;
	protected String UploadDt;
	//Service header parameters
	protected String usecaseID;
	protected String versionNo;
	protected String serviceAction;
	protected String correlationID;
	protected String senderID;
	protected String consumer;
	protected String repTimeStamp;
	protected String username;
	protected String credentials;
	protected String sysRefNumber;
	protected String serviceName;
	protected String reqTimeStamp;
	protected String returnCode;
	protected String errorDetail;
	protected String errorDescription;
	protected String incpCountry;
	protected String incpCity;
	protected String incpState;
	protected String sOutputXML="";
	protected String sInputXML="";
	protected String SUCCESS_STATUS = "0";
	protected static String sUsername = "";
	protected static int iUserCount = 0;
	protected static String sPassword = "";
	//	protected static String dburl = "";
	//	protected static String dbuser = "";
	//	protected static String decpass="";
	//	protected static String dbpass = "";
	protected static String optDocList = "";
	//protected static String finaldburl ="";
	//protected static String dburl1= "jdbc:oracle:thin:@";

	protected int iBinaryAttachmentSize;
	protected int iFileSize;
	protected String sAppId;
	protected String sUserDBId = "";
	protected String sFolderIndex = "";
	//protected ResourceBundle pCodes;	
	protected String compCustType="";
	protected static String AUDIT_TRAIL="NG_WBG_WS_AUDIT_TRAIL";
	protected final String DATE_FORMAT="dd/MMM/yyyy HH:mm:ss";
	protected String xDocTrc="";
	protected String strUploadWi="";
	protected String compCB2Code="";
	protected String compSrvRemarks="";
	// Added by Shivanshu New Tag
	protected String digiSignFlag="";
	protected String digiSignReason="";
	protected static String Query = "INSERT INTO "+ AUDIT_TRAIL+" (REFNO,HOSTNAME,HOSTIP,SOURCEAPP,APPUSER,USERNAME,FUNCNAME,REQXML) VALUES (?,?,?,?,?,?,?,? )";
	public static ConnectSocket socket;

	private static final String const_0	  = "Success";
	private static final String const_1823  = "Error in inserting values in Audit Trail";
	private static final String const_1825  = "Error in Updating Audit Trail";
	private static final String const_3040 = "Sourcing Center is mandatory ";
	private static final String const_3041 = "ProdCode is mandatory";
	private static final String const_3042 = "Mandatory field is missing";
	private static final String const_3054 = "DOB is mandatory";
	private static final String const_3056 = "Profession is mandatory";
	private static final String const_3057 = "Gender is mandatory";
	private static final String const_3061 = "Document Type is mandatory";
	private static final String const_3062 = "Document Index is mandatory";
	private static final String const_3063 = "Authorized Signatory is mandatory";
	private static final String const_3064 = "Account Information missing";
	private static final String const_3066 = "Seq ref no already exists. Provide a different one";
	private static final String const_3074 = "Error in updating audit trail table";
	private static final String const_3087 = "DOB format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3088 = "VisaIssuedate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3089 = "VisaExpiryDate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3090 = "PassportIssueDate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3091 = "PassportExpiryDate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3092 = "KYCDate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3093 = "ExistingSince format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3094 = "W8BenSignUpdate format is not correct. Please use DD/MM/YYYY format. ";
	private static final String const_3095 = "CustClassDate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_3096 = "DOJ format is not correct. Please use DD/MM/YYYY format";
	private static final String const_3097 = "DateOfAttainingMaj format is not correct. Please use DD/MM/YYYY format";
	private static final String const_3098 = "RequestDateTime format is not correct. Please use DD/MM/YYYY format";
	private static final String const_4003 = "Error in connecting to server";
	private static final String const_11030 = "IOException in AccountOpening";
	private static final String const_11033 = "Exception in AccountOpening";
	private static final String const_11050 = "Exception in password decrption";
	private static final String const_11054 = "Error in getting existing connection from database.";
	private static final String const_11055 = "Error in getting authentication information from database.";
	private static final String const_11056 = "Invalid username or password of Omnidocs user.";
	private static final String const_11057 = "Maximum no. of connections reached. Please try after some time.";
	private static final String const_11058 = "Error in getting total no of current connections from database.";
	private static final String const_11059 = "Error in updating connection table.";
	private static final String const_11060 = "Error in inserting values into USR_0_WBG_TMP_HEADER";
	private static final String const_11061 = "Error in inserting values into USR_0_WBG_TMP_COMPANY";
	private static final String const_11062 = "Error in inserting values into USR_0_WBG_TMP_AUS";
	private static final String const_11063 = "Error in inserting values into USR_0_WBG_TMP_DOC_DTLS";
	private static final String const_11064 = "Error in inserting values into USR_0_WBG_TMP_KYC";
	private static final String const_11065 = "Error in inserting values into USR_0_WGB_TMP_ACC_INFO";
	private static final String const_11066 = "Error in inserting values into USR_O_WBG_TMP_UBOREL";
	private static final String const_11067 = "Error in inserting values into USR_0_WBG_TMP_FATCAOWNERDTLS";
	private static final String const_11068 = "Error in inserting values into USR_0_WBG_TMP_CRS_TAXRES";
	private static final String const_11069 = "Error in inserting values into USR_0_WBG_TMP_CP_DETLS";
	private static final String const_11070 = "Error in inserting values into USR_0_WBG_TMP_OWNERSHIPINFO";
	private static final String const_11071 = "Error in inserting values into USR_0_WBG_TMP_SUBSIDIARIES";
	private static final String const_11072 = "Error in inserting values into USR_0_WBG_TMP_KYCUBODETAILS";
	private static final String const_11073 = "Error in inserting values into USR_0_WBG_TMP_MAJORDTLS FOR CLIENT";
	private static final String const_11074 = "Error in inserting values into USR_0_WBG_TMP_MAJORDTLS FOR SUPPLIERS";
	private static final String const_11075 = "Error in inserting values into USR_0_WBG_TEMP_AFFENTITYDTLS";
	private static final String const_11076 = "CID is not a Number";
	private static final String const_11077 = "Wrong Format of DOB";
	private static final String const_11078 = "Not a valid Email Id for Customer";
	private static final String const_11079 = "Not a valid Email Id for Delivery Preferences";
	private static final String const_11080 = "Error while uploading Workitem,Please check with WMS support team/IT Team ";
	private static final String const_11084 = "Error while Inserting value into Audit log table";
	private static final String const_11085 = "Error while fetching document from Document tracker";
	private static final String const_11086 = "No document found for the entered lead reference Number";
	private static final String const_11087 = "Error while uploading document in workitem upload call due to wrong document information. Please check with WMS support team/IT Team";
	private static final String const_11088 = "Workflow Server is down. Please check with WMS Support Team/IT Team.";
	//CR2019 start";
	private static final String const_11089 = "Error in inserting values into USR_0_WBG_TMP_KEY_CONTACTS ";
	private static final String const_11090 = "Signature Card is mandatory for NTB AUS";
	private static final String const_11091 = "Company CB1 Code is mandatory";
	private static final String const_11092 = "Company CB2 division is mandatory";
	private static final String const_11093 = "Company CB2 group id is mandatory";
	private static final String const_11094 = "Company CB2 peer id is mandatory";
	private static final String const_11095 = "Company CB2 Code is mandatory";
	private static final String const_11096 = "Website is mandatory";
	private static final String const_11097 = "Office Number is mandatory";
	private static final String const_11098 = "Office Number 2 is mandatory";
	private static final String const_11099 = "Mobile is mandatory";
	private static final String const_11100 = "Email is mandatory";
	private static final String const_11101 = "Profit Center is mandatory";
	private static final String const_11102 = "Profit Center is mandatory for NTB AUS";
	private static final String const_11103 = "Error in inserting values into USR_0_WBG_TMP_KYCRISK"; //Added by SHivanshu ATP-426


	//end
	private static final String const_4004 = "EidaIssuedate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_4005 = "EidaExpiryDate format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_4006 = "Incorporation Date format is not correct. Please use DD/MM/YYYY format.";
	private static final String const_4007 = "Trade License Issue Date format is not correct. Please use DD/MM/YYYY format";
	private static final String const_4008 = "Trade License Expiry Date format is not correct. Please use DD/MM/YYYY format";
	private static final String const_4009 = "Formation Date Date format is not correct. Please use DD/MM/YYYY format";
	private static final String const_4010 = "Date of Visit Date format is not correct. Please use DD/MM/YYYY format";
	private static final String const_4011 = "AUS Address Line1 is mandatory";
	private static final String const_4012 = "AUS Address Line2 is mandatory";
	private static final String const_4013 = "AUS Address Line3 is mandatory";
	private static final String const_4014 = "AUS City is mandatory";
	private static final String const_4015 = "AUS State/Country is mandatory";
	//	private static final String const_4015 = "AUS Country is mandatory";
	private static final String const_4016 = "AUS Correspondence Country is mandatory";
	private static final String const_4017 = "AUS Country of Residence is mandatory";
	private static final String const_4018 = "AUS Nationality is mandatory";
	private static final String const_4019 = "AUS State is mandatory";
	private static final String const_4020 = "AUS Town is mandatory";
	private static final String const_4021 = "AUS zip is mandatory";
	private static final String const_4022 = "AUS Category is mandatory";
	private static final String const_4023 = "AUS Full Name is mandatory";
	private static final String const_4024 = "AUS Prefix is mandatory";
	private static final String const_4025 = "AUS Customer Type is mandatory";
	private static final String const_4026 = "AUS DOB is mandatory";
	private static final String const_4027 = "AUS Marital Status is mandatory";
	private static final String const_4028 = "AUS Profit Center is mandatory";
	private static final String const_4029 = "AUS Signature Type is mandatory";
	private static final String const_4030 = "FATCA Owner Number is mandatory";
	private static final String const_4031 = "FATCA Owner Name is mandatory";
	private static final String const_4032 = "FATCA Ownership Address is mandatory";
	private static final String const_4033 = "FATCA Ownership Share Percentage is mandatory";
	private static final String const_4034 = "FATCA Owner TIN or SSN is mandatory";
	private static final String const_4035 = "FATCA Owner W9 Availability is mandatory";
	private static final String const_4036 = "CRS Control Person Information is mandatory";
	private static final String const_4037 = "CRS Control Person Birth City is mandatory";
	private static final String const_4038 = "CRS Control Person Birth Country is mandatory";
	private static final String const_4039 = "CRS Control Person Building Name is mandatory";
	private static final String const_4040 = "CRS Control Person City is mandatory";
	private static final String const_4041 = "CRS Control Person Country is mandatory";
	private static final String const_4042 = "CRS Control Person DOB is mandatory";
	//	private static final String const_4042 = "CRS Control Person DOB is mandatory";
	private static final String const_4043 = "CRS Control Person Emirate is mandatory";
	private static final String const_4044 = "CRS Control Person First Name is mandatory";
	private static final String const_4045 = "CRS Control Person Flat Villa No is mandatory";
	private static final String const_4046 = "CRS Control Person Last Name is mandatory";
	private static final String const_4047 = "CRS Control Person Street is mandatory";
	private static final String const_4048 = "CRS Control Person ControlTypeId is mandatory";
	private static final String const_4049 = "CRS Control Person PersonId is mandatory";
	private static final String const_4050 = "CRS Control Person PrimaryKey is mandatory";
	private static final String const_4051 = "CRS Control Person ReasonId is mandatory";
	private static final String const_4052 = "CRS Control Person TaxpayerIdNo is mandatory";
	private static final String const_4053 = "CRS Control Person TaxResCountry is mandatory   ";
	private static final String const_4054 = "CRS Tax Residence Country is mandatory";
	private static final String const_4055 = "CRS Tax Residence Country TaxpayerIdNumber is mandatory";
	private static final String const_4056 = "CRS Tax Residence Country TaxResidenceCountry is mandatory";
	private static final String const_4057 = "CRS Tax Residence Country ReasonId is mandatory";
	private static final String const_4059 = "CRS Tax Residence Country ReasonDesc is mandatory";
	private static final String const_4060 = "AUS Details are mandatory";
	private static final String const_4061 = "Lead Submission Date format is wrong.Please use DD/MM/YYYY HH:MM:SS format.";
	private static final String const_4062 = "AUS Telephone Landline Number Status is mandatory";
	private static final String const_4063 = "AUS Mobile Number is mandatory";
	private static final String const_4064 = "AUS Passport Number is mandatory";
	private static final String const_4065 = "AUS VISA Number is mandatory";
	private static final String const_4066 = "QueueName is mandatory for Reprivate static final String const_submission Request";
	//added by reyaz
	private static final String const_4067 = "Invalid request. Emboss name must be present to issue Debit card";
	private static final String const_4068 = "Passport is mandatory for Card holder customer";
	private static final String const_4069 = "EIDA is mandatory for Card holder customer";
	private static final String const_4070 = "Relationship Manager code should be present";
	private static final String const_4071 = "EIDA Number is mandatory";

	//Added by shivanshu ATP-514 FOR Routing Condition Mandatory
	private static final String const_4072 = "Invalid Request. Active Account Flag should be present in General Info";

	
	public String getStatuMessageFromCode(String statusCode){
		int status = Integer.parseInt(statusCode);
		try {
			switch(status){
			case 0:
				return const_0;
			case 1823:
				return const_1823;
			case 1825:
				return const_1825;
			case -3040:
				return const_3040;
			case -3041:
				return const_3041;
			case -3042:
				return const_3042;
			case -3054:
				return const_3054;
			case -3056:
				return const_3056;
			case -3057:
				return const_3057;
			case -3061:
				return const_3061;
			case -3062:
				return const_3062;
			case -3063:
				return const_3063;
			case -3064:
				return const_3064;
			case -3066:
				return const_3066;
			case -3074:
				return const_3074;
			case -3087:
				return const_3087;
			case -3088:
				return const_3088;
			case -3089:
				return const_3089;
			case -3090:
				return const_3090;
			case -3091:
				return const_3091;
			case -3092:
				return const_3092;
			case -3093:
				return const_3093;
			case -3094:
				return const_3094;
			case -3095:
				return const_3095;
			case -3096:
				return const_3096;
			case -3097:
				return const_3097;
			case -3098:
				return const_3098;
			case -4003:
				return const_4003;
			case -11030:
				return const_11030;
			case -11033:
				return const_11033;
			case -11050:
				return const_11050;
			case -11054:
				return const_11054;
			case -11055:
				return const_11055;
			case -11056:
				return const_11056;
			case -11057:
				return const_11057;
			case -11058:
				return const_11058;
			case -11059:
				return const_11059;
			case -11060:
				return const_11060;
			case -11061:
				return const_11061;
			case -11062:
				return const_11062;
			case -11063:
				return const_11063;
			case -11064:
				return const_11064;
			case -11065:
				return const_11065;
			case -11066:
				return const_11066;
			case -11067:
				return const_11067;
			case -11068:
				return const_11068;
			case -11069:
				return const_11069;
			case -11070:
				return const_11070;
			case -11071:
				return const_11071;
			case -11072:
				return const_11072;
			case -11073:
				return const_11073;
			case -11074:
				return const_11074;
			case -11075:
				return const_11075;
			case -11076:
				return const_11076;
			case -11077:
				return const_11077;
			case -11078:
				return const_11078;
			case -11079:
				return const_11079;
			case -11080:
				return const_11080;
			case -11084:
				return const_11084;
			case -11085:
				return const_11085;
			case -11086:
				return const_11086;
			case -11087:
				return const_11087;
			case -11088:
				return const_11088;
			case -11089:
				return const_11089;
			case -11090:
				return const_11090;
			case -11091:
				return const_11091;
			case -11092:
				return const_11092;
			case -11093:
				return const_11093;
			case -11094:
				return const_11094;
			case -11095:
				return const_11095;
			case -11096:
				return const_11096;
			case -11097:
				return const_11097;
			case -11098:
				return const_11098;
			case -11099:
				return const_11099;
			case -11100:
				return const_11100;
			case -11101:
				return const_11101;
			case -11102:
				return const_11102;
			case -11103:
				return const_11103; //Added by Shivanshu ATP-426
			case -4004:
				return const_4004;
			case -4005:
				return const_4005;
			case -4006:
				return const_4006;
			case -4007:
				return const_4007;
			case -4008:
				return const_4008;
			case -4009:
				return const_4009;
			case -4010:
				return const_4010;
			case -4011:
				return const_4011;
			case -4012:
				return const_4012;
			case -4013:
				return const_4013;
			case -4014:
				return const_4014;
			case -4015:
				return const_4015;
			case -4016:
				return const_4016;
			case -4017:
				return const_4017;
			case -4018:
				return const_4018;
			case -4019:
				return const_4019;
			case -4020:
				return const_4020;
			case -4021:
				return const_4021;
			case -4022:
				return const_4022;
			case -4023:
				return const_4023;
			case -4024:
				return const_4024;
			case -4025:
				return const_4025;
			case -4026:
				return const_4026;
			case -4027:
				return const_4027;
			case -4028:
				return const_4028;
			case -4029:
				return const_4029;
			case -4030:
				return const_4030;
			case -4031:
				return const_4031;
			case -4032:
				return const_4032;
			case -4033:
				return const_4033;
			case -4034:
				return const_4034;
			case -4035:
				return const_4035;
			case -4036:
				return const_4036;
			case -4037:
				return const_4037;
			case -4038:
				return const_4038;
			case -4039:
				return const_4039;
			case -4040:
				return const_4040;
			case -4041:
				return const_4041;
			case -4042:
				return const_4042;
			case -4043:
				return const_4043;
			case -4044:
				return const_4044;
			case -4045:
				return const_4045;
			case -4046:
				return const_4046;
			case -4047:
				return const_4047;
			case -4048:
				return const_4048;
			case -4049:
				return const_4049;
			case -4050:
				return const_4050;
			case -4051:
				return const_4051;
			case -4052:
				return const_4052;
			case -4053:
				return const_4053;
			case -4054:
				return const_4054;
			case -4055:
				return const_4055;
			case -4056:
				return const_4056;
			case -4057:
				return const_4057;
			case -4059:
				return const_4059;
			case -4060:
				return const_4060;
			case -4061:
				return const_4061;
			case -4062:
				return const_4062;
			case -4063:
				return const_4063;
			case -4064:
				return const_4064;
			case -4065:
				return const_4065;
			case -4066:
				return const_4066;
			case -4067:
				return const_4067;
			case -4068:
				return const_4068;
			case -4069:
				return const_4069;
			case -4070:
				return const_4070;	
			case -4071:
				return const_4071;	
			case -4072:
				return const_4072;	//Added by Shivanshu FOR ACTIVE ACCOUNT FLAG
			} 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}