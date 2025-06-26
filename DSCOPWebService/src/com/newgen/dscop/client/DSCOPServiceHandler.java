package com.newgen.dscop.client;

import java.util.Map;

import com.newgen.AESEncryption;
import com.newgen.dscop.client.*;
import com.newgen.dscop.utils.EncDec;

public class DSCOPServiceHandler {
	public static Map<String,String> currentCabPropertyMap=null;
	public String loggedinuser="";
	public String callHandler(String inputXml){
		try {
			WebServiceConfig.getInstance().readWebSrvConfig();
			XMLParser xmlDataParser = new XMLParser();
			xmlDataParser.setInputXML(inputXml);
			String callType=xmlDataParser.getValueOf("DSCOPCallType");
			LogGEN.writeTrace("CBG_Log", "DSCOPCallType VALUE "+callType);

			if(callType.equalsIgnoreCase("InqBusinessRules")){
				LogGEN.writeTrace("CBG_Log", "InqBusinessRules");		
				InqBusinessRules InqBusiRules = new InqBusinessRules();
				return InqBusiRules.customerEligibility(inputXml);
			}
			else if(callType.equalsIgnoreCase("ModMIBRegistration")){
				LogGEN.writeTrace("CBG_Log", "ModMIBRegistration");		
				ModMIBRegistration modMIBReg = new ModMIBRegistration();
				return modMIBReg.Mod_MIB_Registration(inputXml);
			}
			else if(callType.equalsIgnoreCase("LeadCreation")){
				LogGEN.writeTrace("CBG_Log", "LeadCreation");		
				LeadCreation modCBGCustOn = new LeadCreation();
				return modCBGCustOn.leadCreation(inputXml);
			}
			else if(callType.equalsIgnoreCase("ADD_CCPS")){
				LogGEN.writeTrace("CBG_Log", "ADD_CCPS");		
				AddCardCCPS ccps = new AddCardCCPS();
				return ccps.AddCCPS(inputXml);
			}
			else if(callType.equalsIgnoreCase("Add_CCInfo")){
				LogGEN.writeTrace("CBG_Log", "Add_CCInfo");		
				ModSBKCreditCards addCC = new ModSBKCreditCards();
				return addCC.AddCreditCard(inputXml);
			}
			else if(callType.equalsIgnoreCase("CARD_ONBOARDING")){
				LogGEN.writeTrace("CBG_Log", "CARD_ONBOARDING");		
				CardOnboarding cardOnBoard = new CardOnboarding();
				return cardOnBoard.cardOnboarding(inputXml);
			}
			else if(callType.equalsIgnoreCase("SEND_SMS_EMAIL")){ 
				LogGEN.writeTrace("CBG_Log", "SEND_SMS_EMAIL");		
				SendSMSEmail sendSMSEmail = new SendSMSEmail();
				return sendSMSEmail.sendSMSEmail(inputXml);
			}
			else if(callType.equalsIgnoreCase("COMPANY_MASTER_DETAILS")){
				LogGEN.writeTrace("CBG_Log", "COMPANY_MASTER_DETAILS");		
				InqCompanyDetails companyDetails = new InqCompanyDetails();
				return companyDetails.companyMasterDetails(inputXml);
			}
			else if(callType.equalsIgnoreCase("ECB_REPORT_DETAILS")){
				LogGEN.writeTrace("CBG_Log", "ECB_REPORT_DETAILS");		
				InqECBApplicationDetails ecbDetails = new InqECBApplicationDetails();
				return ecbDetails.ECBReportDetails(inputXml);
			}
			else if(callType.equalsIgnoreCase("EIDA_LAPS")){
				LogGEN.writeTrace("CBG_Log", "EIDA_LAPS");		
				InqCustApplicationDtl dedupeEIDALaps = new InqCustApplicationDtl();
				return dedupeEIDALaps.dedupeEIDALaps(inputXml);
			}
			else if(callType.equalsIgnoreCase("Add_Debit_Card_Issuence")){
				LogGEN.writeTrace("CBG_Log", "Add_Debit_Card_Issuence");		
				AddDebitCardIssue debitCard = new AddDebitCardIssue();
				return debitCard.AddDebitCards(inputXml);
			}
			else if(callType.equalsIgnoreCase("Tracer")){
				LogGEN.writeTrace("CBG_Log", "Tracer");		
				InqCBGCustomerOnboarding tracer = new InqCBGCustomerOnboarding();
				return tracer.CustomerScore(inputXml);
			}
			else if(callType.equalsIgnoreCase("ModifyCreditLimit")){
				LogGEN.writeTrace("CBG_Log", "ModifyCreditLimit");		
				CreditCardLimitChange creditLimit = new CreditCardLimitChange();
				return creditLimit.CreditLimitChange(inputXml);
			}
			else if(callType.equalsIgnoreCase("CardUpgradeLimit")){
				LogGEN.writeTrace("CBG_Log", "CardUpgradeLimit");		
				CardUpgradeLimit cardupgrade = new CardUpgradeLimit();
				return cardupgrade.ModCBGCustOnboarding(inputXml);
			}
			else if(callType.equalsIgnoreCase("UpdateDigitalCard")){
				LogGEN.writeTrace("CBG_Log", "UpdateDigitalCard");		
				UpdateDigitalCard digitalCard = new UpdateDigitalCard();
				return digitalCard.DigitalCardStatus(inputXml);
			}
			else if(callType.equalsIgnoreCase("CB_Customer_Rating")){
				LogGEN.writeTrace("CBG_Log", "CB_Customer_Rating");		
				CentralBank cb = new CentralBank();
				return cb.fetchCustomerRating(inputXml);
			}
			else if(callType.equalsIgnoreCase("CB_Customer_Info")){
				LogGEN.writeTrace("CBG_Log", "CB_Customer_Info");		
				CentralBank cb = new CentralBank();
				return cb.fetchCustomerInfo(inputXml);
			} 
			else if(callType.equalsIgnoreCase("Inq_Cust_EmriatesID")){
				LogGEN.writeTrace("CBG_Log", "Inq_Cust_EmriatesID");		
				InqCustEmiratesIDAuthDtls custEmirateIdAuth = new InqCustEmiratesIDAuthDtls();
				return custEmirateIdAuth.FetchCustEmiratesIdAuthDtls(inputXml);
			}
			else if(callType.equalsIgnoreCase("FETCHER_SHIPMENT_ORDER")){
				LogGEN.writeTrace("CBG_Log", "FETCHER_SHIPMENT_ORDER");		
				ModFetcherShipmentOrder fetcher = new ModFetcherShipmentOrder();
				return fetcher.FetcherShipmentOrder(inputXml);
			}
			else if(callType.equalsIgnoreCase("ADD_UPDATE_CDTS")){
				LogGEN.writeTrace("CBG_Log", "ADD_UPDATE_CDTS");		
				AddUpdateCDTSDetails fetcher = new AddUpdateCDTSDetails();
				return fetcher.addUpdateCDTS(inputXml);
			}
			else if(callType.equalsIgnoreCase("SINGLE_HOOK")){
				LogGEN.writeTrace("CBG_Log", "SINGLE_HOOK");		
				DSCOPInternalHookService single_hook = new DSCOPInternalHookService();
				return single_hook.singleHook(inputXml);
			}
			else if(callType.equalsIgnoreCase("AddCRS")){
				LogGEN.writeTrace("CBG_Log", "*****AddCRS*****");
				AddCRS addCRS= new AddCRS();
				return addCRS.addCRS(inputXml);
			}
			else if(callType.equalsIgnoreCase("ADD_Entity_Customer_WBG") || callType.equalsIgnoreCase("ADD_Customer_AUS_WBG")){
				LogGEN.writeTrace("CBG_Log", "*****ADD_Customer*****");
				AddCustomer addCustomer= new AddCustomer();
				return addCustomer.addCustomer(inputXml);
			}
			else if(callType.equalsIgnoreCase("ValidateFATCAMain"))
			{
				LogGEN.writeTrace("CBG_Log", "ValidateFATCAMain");		
				ValidateFATCAFull MainFatca = new ValidateFATCAFull();
				return MainFatca.getFATCAStatus(inputXml);
			}	
			else if(callType.equalsIgnoreCase("SaveKYCDetails"))
			{
				LogGEN.writeTrace("CBG_Log", "SaveKYCDetails");		
				SaveKYCDetails SaveKYC = new SaveKYCDetails();
				return SaveKYC.saveKYCDetailsStatus(inputXml);
			}
			else if(callType.equalsIgnoreCase("SaveFATCADetails"))
			{
				LogGEN.writeTrace("CBG_Log", "SaveFATCADetails");		
				SaveFATCADetails SaveFatca = new SaveFATCADetails();
				return SaveFatca.saveFATCADetailsStatus(inputXml);
			}
			else if(callType.equalsIgnoreCase("SaveEIDADetails"))
			{
				LogGEN.writeTrace("CBG_Log", "SaveEIDADetails");		
				SaveEIDADetails SaveEIDA = new SaveEIDADetails();
				return SaveEIDA.saveEIDADetailsStatus(inputXml);
			}
			else if(callType.equalsIgnoreCase("Add_SMS_Reg"))
			{
				LogGEN.writeTrace("CBG_Log", "Add_SMS_Reg");		
				ModSMSReg mod_sms= new ModSMSReg();
				return mod_sms.add_sms_reg(inputXml);
			}
			else if(callType.equalsIgnoreCase("estatement_registration"))
			{
				LogGEN.writeTrace("CBG_Log", "estatement_registration");		
				ModifyEstatement e_sub = new ModifyEstatement();
				return e_sub.email_subcription(inputXml);
			}
			else if(callType.equalsIgnoreCase("Modify_Debit_Card"))
			{
				LogGEN.writeTrace("CBG_Log", "Modify_Debit_Card");		
				ModifyDebitCard mod_debit= new ModifyDebitCard();
				return mod_debit.Modify_Debit_Card(inputXml);
			}
			else if(callType.equalsIgnoreCase("Modify_Customer"))
			{
				LogGEN.writeTrace("CBG_Log", "Modify_Customer");		
				ModifyCustomer mod_cust= new ModifyCustomer();
				return mod_cust.ModifyCustomerToFCR(inputXml);	
			}
			else if(callType.equalsIgnoreCase("Modify_Account"))
			{
				LogGEN.writeTrace("CBG_Log", "Modify_Account");		
				modifyCustAcctDetails acc= new modifyCustAcctDetails();
				return acc.modify_account(inputXml);
			}
			else if(callType.equalsIgnoreCase("Black_List"))
			{
				LogGEN.writeTrace("CBG_Log", "Black_List");		
				inqBlackList black= new inqBlackList();
				return black.getBlackList(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchFATCADetails"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchFATCADetails");		
				FetchFATCADetails FetchFatca = new FetchFATCADetails();
				return FetchFatca.getFATCADetails(inputXml);
			}
			else if(callType.equalsIgnoreCase("Debit_Card_Information"))
			{
				LogGEN.writeTrace("CBG_Log", "Debit_Card_Information");		
				DebitCardDetails Debit_Card_Info= new DebitCardDetails();
				return Debit_Card_Info.FetchDebitCardDetails(inputXml);	
			}
			else if(callType.equalsIgnoreCase("Cheque_Book"))
			{
				LogGEN.writeTrace("CBG_Log", "Cheque_Book");		
				ChequeBook cheque = new ChequeBook();
				return cheque.addCheque(inputXml);
			}
			else if(callType.equalsIgnoreCase("ADD_Account"))
			{
				LogGEN.writeTrace("CBG_Log", "ADD_Account");		
				AddCASAAccount add_acc= new AddCASAAccount();
				return add_acc.AddAccountToFCR(inputXml);	
			}
			else if (callType.equalsIgnoreCase("CustEIDAInfo")) {
				LogGEN.writeTrace("CBG_Log", "*****CustEIDAInfo*****");
				InqSBKCustomer inqSBKCustomer = new InqSBKCustomer();
				return inqSBKCustomer.SBKCustomer(inputXml);
			}
			else if (callType.equalsIgnoreCase("ETLDataService")) {
				LogGEN.writeTrace("CBG_Log", "*****ETLDataService*****");
				DataServicesServerClient dataServicesServer = new DataServicesServerClient();
				return dataServicesServer.dataServices(inputXml);
			}
			else if (callType.equalsIgnoreCase("Base24")) {
				LogGEN.writeTrace("CBG_Log", "*****Base24*****");
				ModSBKDebitCards modSBKDebitCards = new ModSBKDebitCards();
				return modSBKDebitCards.ModifySBKDebitCards(inputXml);
			}
			else if (callType.equalsIgnoreCase("SignatureUpoadToFlexSystem_COB"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside WebServiceHandler-SignatureUpoadToFlexSystem_COB");
				modifyCustAcctDetails_COB  addSignature = new modifyCustAcctDetails_COB();
				LogGEN.writeTrace("CBG_Log", "Returning to WFCUSTOM for call SignatureUpoadToFlexSystem_COB");
				return addSignature.modify_account(inputXml);
			}
			else if (callType.equalsIgnoreCase("UpdateWIexpiryDetails"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler-UpdateWIexpiry");
				UpdateWIexpiry  updateWIexpiry = new UpdateWIexpiry();
				LogGEN.writeTrace("CBG_Log", "Returning to WFCUSTOM for call UpdateWIexpiry");
				return updateWIexpiry.updateWIexpiryStatus(inputXml);
			}
			else if(callType.equalsIgnoreCase("AddFundTransfer"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - AddFundTransfer");		
				AddFundTransfer fndTrnsfer = new AddFundTransfer();
				return fndTrnsfer.FundTransfer(inputXml);
			}
			else if(callType.equalsIgnoreCase("BinValidation"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - BinValidation");		
				BinValidation binValidation = new BinValidation();
				return binValidation.Validate(inputXml);
			}
			else if(callType.equalsIgnoreCase("Encrypt"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - Encrypt");		
				String key = AESEncryption.decrypt(currentCabPropertyMap.get("Encrypt").toString());//"YT7uZPwbYGYJPKC8NJGcH0qnLmMoAEyfxtBkHSJs74U=";
				EncDec aesEncrypt = new EncDec(key);
				String encryptedStr = aesEncrypt.encrypt(xmlDataParser.getValueOf("PayLoad"));
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - Encrypt: "+ encryptedStr);
				return "<EncryptedPayload>" + encryptedStr + "</EncryptedPayload>";
			}
			else if(callType.equalsIgnoreCase("Decrypt"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - Decrypt");		
				String key = AESEncryption.decrypt(currentCabPropertyMap.get("Decrypt").toString());//"YT7uZPwbYGYJPKC8NJGcH0qnLmMoAEyfxtBkHSJs74U=";
				EncDec aesEncrypt = new EncDec(key,(xmlDataParser.getValueOf("EncryptedPayLoad")));
				String decryptedStr = aesEncrypt.decrypt();
				//LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - Decrypt: "+ decryptedStr);
				return "<DecryptedPayload>" + decryptedStr + "</DecryptedPayload>";
			} 
			else if(callType.equalsIgnoreCase("Decrypt602"))
			{
				LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - Decrypt602");		
				String key = AESEncryption.decrypt(currentCabPropertyMap.get("Decrypt602").toString());//"577B9FE9510FC0EF0FC8BFC8523751CAF374E04B66D74773F505A46F3C9413D4";
				EncDec aesEncrypt = new EncDec(key,(xmlDataParser.getValueOf("EncryptedPayLoad")));
				String decryptedStr = aesEncrypt.decrypt();
				//LogGEN.writeTrace("CBG_Log", "Inside DSCOPServiceHandler - Decrypt602: "+ decryptedStr);
				return "<DecryptedPayload>" + decryptedStr + "</DecryptedPayload>";
			}
			else if (callType.equalsIgnoreCase("FetchIslamicCommodityDtls")) {
				LogGEN.writeTrace("CBG_Log", "*****FetchIslamicCommodityDtls*****");
				FetchIslamicCommodityDtls fetchIslamicCommodityDtls = new FetchIslamicCommodityDtls();
				return fetchIslamicCommodityDtls.FetchIslamicCommodity(inputXml);
			}
			else if (callType.equalsIgnoreCase("AddCSAcctAmountHold")) {
				LogGEN.writeTrace("CBG_Log", "*****AddCSAcctAmountHold*****");
				AddCSAcctAmountHold addCSAcctAmountHold = new AddCSAcctAmountHold();
				return addCSAcctAmountHold.AddCSAcctAmountHoldToFCR(inputXml);
			}
			else if (callType.equalsIgnoreCase("AddCSAcctAmountUnHold")) {
				LogGEN.writeTrace("CBG_Log", "*****AddCSAcctAmountUnHold*****");
				AddCSAcctAmountUnHold addCSAcctAmountUnHold = new AddCSAcctAmountUnHold();
				return addCSAcctAmountUnHold.AddCSAcctAmountUnHoldToFCR(inputXml);
			}
			else if (callType.equalsIgnoreCase("AddDigitalLendingApplication")) {
				LogGEN.writeTrace("CBG_Log", "*****AddDigitalLendingApplication*****");
				AddDigitalLendingApplication addDigitalLendingApplication = new AddDigitalLendingApplication();
				return addDigitalLendingApplication.addDigitalLendingApplication(inputXml);
			}
			else if (callType.equalsIgnoreCase("InqLoanTopUpRequest")) {
				LogGEN.writeTrace("CBG_Log", "*****InqLoanTopUpRequest*****");
				InqLoanTopUpRequest inqLoanTopUpRequest = new InqLoanTopUpRequest();
				return inqLoanTopUpRequest.InqLoanTopUpRequestToFCR(inputXml);
			}
			else if(callType.equalsIgnoreCase("MQ_ECB_REPORT")){
				LogGEN.writeTrace("CBG_Log", "MQ_ECB_REPORT");		
				MQECBReport ecbDetails = new MQECBReport();
				return ecbDetails.ecbReport(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCustomerInformation")){
				LogGEN.writeTrace("CBG_Log", "InqCustomerInformation");		
				InqCustomerInformation custInfo = new InqCustomerInformation();
				return custInfo.FetchCustomerInformation(inputXml);
			}
			else if(callType.equalsIgnoreCase("CustomerPersonalDetails")){
				LogGEN.writeTrace("CBG_Log", "CustomerPersonalDetails");		
				CustomerPersonalDetails cpd = new CustomerPersonalDetails();
				return cpd.FetchCustomerPersonalDetails(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCustomerSearch")){
				LogGEN.writeTrace("CBG_Log", "InqCustomerSearch");		
				InqCustomerSearch cs = new InqCustomerSearch();
				return cs.SearchCustomer(inputXml);
			}
			else if(callType.equalsIgnoreCase("ModLendingAutomation")){
				LogGEN.writeTrace("CBG_Log", "ModLendingAutomation");		
				ModLendingAutomation mla = new ModLendingAutomation();
				return mla.modLending(inputXml);
			}
			else if(callType.equalsIgnoreCase("LeadInquiry"))
			{
				LogGEN.writeTrace("CBG_Log", "LeadInquiry");		
				LeadInquiry leadInq= new LeadInquiry();
				return leadInq.leadCustomerEnquiry(inputXml);	
			} 
			else if(callType.equalsIgnoreCase("InqCustomerLendingDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCustomerLendingDtls");             
				InqCustomerLendingDtls custLend= new InqCustomerLendingDtls();
				return custLend.custLending(inputXml);   
			}
			else if(callType.equalsIgnoreCase("InqSBKCreditCards"))
			{
				LogGEN.writeTrace("CBG_Log", "InqSBKCreditCards");             
				InqSBKCreditCards inqSBKCreditCards= new InqSBKCreditCards();
				return inqSBKCreditCards.getKioskList(inputXml);   
			}
			else if(callType.equalsIgnoreCase("AddLead"))
			{
				LogGEN.writeTrace("CBG_Log", "AddLead");             
				AddLead addLead = new AddLead();
				return addLead.addLeadResponse(inputXml);  
			}
			else if (callType.equalsIgnoreCase("InqCSAcctBasicInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCSAcctBasicInfo");
				InqCSAcctBasicInfo accBasic = new InqCSAcctBasicInfo();
				return accBasic.acctBasicInfo(inputXml);
			}
			else if (callType.equalsIgnoreCase("InqFinanceBasicInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "InqFinanceBasicInfo");
				InqFinanceBasicInfo basicInfo = new InqFinanceBasicInfo();
				return basicInfo.financeBasicInfo(inputXml);
			}
			else if (callType.equalsIgnoreCase("InqCustomerDetails"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCustomerDetails");
				InqCustomerDetails custDetails = new InqCustomerDetails();
				return custDetails.custDetails(inputXml);
			}
			else if (callType.equalsIgnoreCase("InqLoanBasicInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "InqLoanBasicInfo");
				InqLoanBasicInfo loanBasic = new InqLoanBasicInfo();
				return loanBasic.loanBasicInfo(inputXml);
			}
			else if (callType.equalsIgnoreCase("RemoveCardPrintHold"))
			{
				LogGEN.writeTrace("CBG_Log", "RemoveCardPrintHold");
				RemoveCardPrintHold removeCard = new RemoveCardPrintHold();
				return removeCard.removeCardPrintHold(inputXml);
			}
			else if(callType.equalsIgnoreCase("AddOnlineAppData"))
			{
				LogGEN.writeTrace("CBG_Log", "AddOnlineAppData");		
				AddOnlineAppData aoad = new AddOnlineAppData();
				return aoad.addOnlineAppDataExec(inputXml);
			}
			else if(callType.equalsIgnoreCase("IndividualRisk"))
			{
				LogGEN.writeTrace("CBG_Log", "AddOnlineAppData");		
				IndividualRisk individualRisk = new IndividualRisk();
				return individualRisk.IndividualRiskCalculate(inputXml);
			}
			else if(callType.equalsIgnoreCase("MyChoiceFunction"))
			{
				LogGEN.writeTrace("CBG_Log", "MyChoiceFunction");		
				MyChoiceFunction myChoiceFunction  = new MyChoiceFunction();
				return myChoiceFunction.AddMyChoiceFunction(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCustEmiratesDigitalWallet"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCustEmiratesDigitalWallet");		
				InqCustEmiratesDigitalWallet InqCustEmiratesDigitalWalletFunction  = new InqCustEmiratesDigitalWallet();
				return InqCustEmiratesDigitalWalletFunction.InqCustEmiratesDigitalWalletCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchReferenceDetails"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchReferenceDetails");		
				FetchReferenceDetails FetchReferenceDetails  = new FetchReferenceDetails();
				return FetchReferenceDetails.FetchReferenceDetailsCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("ModCustEmiratesDigitalWallet"))
			{
				LogGEN.writeTrace("CBG_Log", "ModCustEmiratesDigitalWallet");		
				ModCustEmiratesDigitalWallet ModCustEmiratesDigitalWalletFunction  = new ModCustEmiratesDigitalWallet();
				return ModCustEmiratesDigitalWalletFunction.ModCustEmiratesDigitalWalletCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("CPSSendSMSEmail"))
			{
				LogGEN.writeTrace("CBG_Log", "CPSSendSMSEmail");		
				CPSSendSMSEmail cpsSendSMSEmail  = new CPSSendSMSEmail();
				return cpsSendSMSEmail.sendSMSEmail(inputXml);
			}
			else if(callType.equalsIgnoreCase("AddDigitalCreditCard"))
			{
				LogGEN.writeTrace("CBG_Log", "AddDigitalCreditCard");		
				AddDigitalCreditCardRequest AddDigitalCreditCardRequest  = new AddDigitalCreditCardRequest();
				return AddDigitalCreditCardRequest.AddDigitalCreditCardRequestCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("AddCustomerConsent"))
			{
				LogGEN.writeTrace("CBG_Log", "AddCustomerConsent");		
				AddCustomerConsent addCustomerConsent  = new AddCustomerConsent();
				return addCustomerConsent.addCustomerConsentCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCCBasicInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCCBasicInfo");		
				InqCCBasicInfo inqCCBasicInfo  = new InqCCBasicInfo();
				return inqCCBasicInfo.getCCBasicInfo(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchCustomerUAEPassInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchCustomerUAEPassInfo");		
				FetchCustomerUAEPassInfo fetchCustomerUAEPassInfo  = new FetchCustomerUAEPassInfo();
				return fetchCustomerUAEPassInfo.FetchCustomerUAEPassInfo(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchCustBScore"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchCustBScore");		
				FetchCustBScore fetchCustBScore  = new FetchCustBScore();
				return fetchCustBScore.fetchCustBScore(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchCustSalaryDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchCustSalaryDtls");		
				FetchCustSalaryDtls fetchCustSalaryDtls  = new FetchCustSalaryDtls();
				return fetchCustSalaryDtls.fetchCustSalaryDtls(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqBankCodeDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "InqBankCodeDtls");		
				InqBankCodeDtls inqBankCodeDtls  = new InqBankCodeDtls();
				return inqBankCodeDtls.inqBankCodeDtls(inputXml);
			}
			else if(callType.equalsIgnoreCase("RemoveOffers"))
			{
				LogGEN.writeTrace("CBG_Log", "RemoveOffers");		
				RemoveOffers removeOffers  = new RemoveOffers();
				return removeOffers.removeOffers(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchCustCampaignDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchCustCampaignDtls");		
				FetchCustCampaignDtls FetchCustCampaignDtls  = new FetchCustCampaignDtls();
				return FetchCustCampaignDtls.fetchCustCampaignDtls(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqBancassuranceDetails"))
			{
				LogGEN.writeTrace("CBG_Log", "InqBancassuranceDetails");		
				InqBancassuranceDetails inqBancassuranceDetails  = new InqBancassuranceDetails();
				return inqBancassuranceDetails.inqBancassuranceDetails(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCustomerAlertsInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCustomerAlertsInfo");		
				InqCustomerAlertsInfo inqCustomerAlertsInfo  = new InqCustomerAlertsInfo();
				return inqCustomerAlertsInfo.inqCustomerAlertsInfo(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchDDSPaymentInfo"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchDDSPaymentInfo");		
				FetchDDSPaymentInfo fetchDDSPaymentInfo  = new FetchDDSPaymentInfo();
				return fetchDDSPaymentInfo.fetchDDSPaymentInfo(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchChequeDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchChequeDtls");		
				FetchChequeDtls fetchChequeDtls  = new FetchChequeDtls();
				return fetchChequeDtls.fetchChequeDtlsCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("AddCustStatement"))
			{
				LogGEN.writeTrace("CBG_Log", "AddCustStatement");		
				AddCustStatement addCustStatement  = new AddCustStatement();
				return addCustStatement.addCustStatement(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqServiceRequest"))
			{
				LogGEN.writeTrace("CBG_Log", "InqServiceRequest");		
				InqServiceRequest inqServiceRequest  = new InqServiceRequest();
				return inqServiceRequest.inqServiceRequest(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCustAcctMiscDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "InqCustAcctMiscDtls");		
				InqCustAcctMiscDtls inqCustAcctMiscDtls  = new InqCustAcctMiscDtls();
				return inqCustAcctMiscDtls.inqCustAcctMiscDtls(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchCustSalaryAdditionalDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchCustSalaryAdditionalDtls");		
				FetchCustSalaryAdditionalDtls fetchCustSalaryAdditionalDtls  = new FetchCustSalaryAdditionalDtls();
				return fetchCustSalaryAdditionalDtls.fetchCustSalaryAdditionalDtls(inputXml);
			}
			else if(callType.equalsIgnoreCase("InternalDBRETB")){
				LogGEN.writeTrace("CBG_Log", "InternalDBRETB");		
				InternalDBRETB internalDBRETB = new InternalDBRETB();
				return internalDBRETB.internalDBRETB(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCCDetails")){
				LogGEN.writeTrace("CBG_Log", "InqCCDetails");		
				InqCCDetails inqCCDetails = new InqCCDetails();
				return inqCCDetails.inqCCDetails(inputXml);
			}
			else if(callType.equalsIgnoreCase("ModIslamicCommodityTransaction"))
			{
				LogGEN.writeTrace("CBG_Log", "ModIslamicCommodityTransaction");		
				ModIslamicCommodityTransaction ModIslamicCommodityTransaction  = new ModIslamicCommodityTransaction();
				return ModIslamicCommodityTransaction.modIslamicCommodityTransactionCall(inputXml);
			} 
			else if(callType.equalsIgnoreCase("CreateCCPSApplication"))
			{
				LogGEN.writeTrace("CBG_Log", "CreateCCPSApplication");		
				CreateCCPSApplication createApplication  = new CreateCCPSApplication();
				return createApplication.createCCPSApplicationCall(inputXml);
			} 
			else if(callType.equalsIgnoreCase("UpdateCCPSAppStatus"))
			{
				LogGEN.writeTrace("CBG_Log", "UpdateCCPSAppStatus");		
				UpdateCCPSAppStatus updateApplication  = new UpdateCCPSAppStatus();
				return updateApplication.updateCCPSAppStatusCall(inputXml);
			} 
			else if(callType.equalsIgnoreCase("FetchSupplCardHolderDtls"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchSupplCardHolderDtls");		
				FetchSupplCardHolderDtls FetchSupplCardHolderDtls  = new FetchSupplCardHolderDtls();
				return FetchSupplCardHolderDtls.fetchSupplCardHolderDtlsDtls(inputXml);
			}else if(callType.equalsIgnoreCase("AddLoanRecord")) {
				LogGEN.writeTrace("CBG_Log", "AddLoanRecord");		
				AddLoanRecord AddLoanRecord  = new AddLoanRecord();
				return AddLoanRecord.addLoanRecordCall(inputXml);
			}else if(callType.equalsIgnoreCase("AddIslamicLoanDisb")) {
				LogGEN.writeTrace("CBG_Log", "AddIslamicLoanDisb");		
				AddIslamicLoanDisb AddIslamicLoanDisb  = new AddIslamicLoanDisb();
				return AddIslamicLoanDisb.AddIslamicLoan(inputXml);
			}else if(callType.equalsIgnoreCase("CancelLoanRecord")) {
				LogGEN.writeTrace("CBG_Log", "CancelLoanRecord");		
				CancelLoanRecord CancelLoanRecord  = new CancelLoanRecord();
				return CancelLoanRecord.cancelLoanRecordCall(inputXml);
			}else if(callType.equalsIgnoreCase("CancelIslamicLoanRecord")) {
				LogGEN.writeTrace("CBG_Log", "CancelIslamicLoanRecord");		
				CancelIslamicLoanRecord CancelIslamicLoanRecord  = new CancelIslamicLoanRecord();
				return CancelIslamicLoanRecord.cancelIslamicLoanRecordCall(inputXml);
			}else if(callType.equalsIgnoreCase("AddLoanSettlement")) {
				LogGEN.writeTrace("CBG_Log", "AddLoanSettlement");		
				AddLoanSettlement AddLoanSettlement  = new AddLoanSettlement();
				return AddLoanSettlement.addLoanSettlementCall(inputXml);
			}else if(callType.equalsIgnoreCase("AddFinanceSettlement")) {
				LogGEN.writeTrace("CBG_Log", "AddFinanceSettlement");		
				AddFinanceSettlement AddFinanceSettlement  = new AddFinanceSettlement();
				return AddFinanceSettlement.addFinanceSettlementCall(inputXml);
			}else if(callType.equalsIgnoreCase("InqFinanceOperationDtl")) {
				LogGEN.writeTrace("CBG_Log", "InqFinanceOperationDtl");		
				InqFinanceOperationDtl InqFinanceOperationDtl  = new InqFinanceOperationDtl();
				return InqFinanceOperationDtl.InqFinanceOperationDtl(inputXml);
			}else if(callType.equalsIgnoreCase("InqLoanOperationDtl")) {
				LogGEN.writeTrace("CBG_Log", "InqLoanOperationDtl");		
				InqLoanOperationDtl InqLoanOperationDtl  = new InqLoanOperationDtl();
				return InqLoanOperationDtl.InqLoanOperationDtlCall(inputXml);
			}else if(callType.equalsIgnoreCase("AddSRServiceRequest")){
				LogGEN.writeTrace("CBG_Log", "AddSRServiceRequest");		
				AddSRServiceRequest addServiceRequest = new AddSRServiceRequest();
				return addServiceRequest.callAddSRServiceRequest(inputXml);
			}else if(callType.equalsIgnoreCase("InqCSAcctOperationDtl")){
				LogGEN.writeTrace("CBG_Log", "InqCSAcctOperationDtl");		
				InqCSAcctOperationDtl inqCSAcctOperationDtl = new InqCSAcctOperationDtl();
				return inqCSAcctOperationDtl.inqCSAcctOperationDtl(inputXml);
			}else if(callType.equalsIgnoreCase("ModCustomerAddressDetails")){
				LogGEN.writeTrace("CBG_Log", "ModCustomerAddressDetails");		
				ModCustomerAddressDetails ModCustomerAddressDetails = new ModCustomerAddressDetails();
				return ModCustomerAddressDetails.modCustomerAddressDetailsCall(inputXml);
			}else if(callType.equalsIgnoreCase("AddDigiCompanyDtls")){
				LogGEN.writeTrace("CBG_Log", "AddDigiCompanyDtls");		
				AddDigiCompanyDtls addDigiCompanyDtls = new AddDigiCompanyDtls();
				return addDigiCompanyDtls.addDigiCompanyCall(inputXml);
			}else if(callType.equalsIgnoreCase("FetchCRS")){
				LogGEN.writeTrace("CBG_Log", "FetchCRS");		
				FetchCRS fetchCRS = new FetchCRS();
				return fetchCRS.fetchCRSDetails(inputXml);
			}else if(callType.equalsIgnoreCase("ModEmiratesSettleIdentity")){
				LogGEN.writeTrace("CBG_Log", "ModEmiratesSettleIdentity");		
				ModEmiratesSettleIdentity modEmiratesSettleIdentity = new ModEmiratesSettleIdentity();
				return modEmiratesSettleIdentity.settleIdentity(inputXml);
			}
			else if(callType.equalsIgnoreCase("InqCSAcctTxnDtl")){
				LogGEN.writeTrace("CBG_Log", "InqCSAcctTxnDtl");		
				InqCSAcctTxnDtl inqCSAcctTxnDtl = new InqCSAcctTxnDtl();
				return inqCSAcctTxnDtl.inqCSAcctTxnDtlCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("FetchCustomerConsent"))
			{
				LogGEN.writeTrace("CBG_Log", "FetchCustomerConsent");		
				FetchCustomerConsent fetchCustomerConsent  = new FetchCustomerConsent();
				return fetchCustomerConsent.fetchCustomerConsentCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("GetCompanyMasterList")){
				LogGEN.writeTrace("CBG_Log", "GetCompanyMasterList");		
				GetCompanyMasterList getCompanyMasterList = new GetCompanyMasterList();
				return getCompanyMasterList.getCompanyMasterListCall(inputXml);
			}
			else if(callType.equalsIgnoreCase("PILUmlMQ")){
				LogGEN.writeTrace("CBG_Log", "PILUmlMQ");		
				PILUmlMQ pilUmlMQ = new PILUmlMQ();
				return pilUmlMQ.pilUMLMQPut(inputXml);
			}
			else if(callType.equalsIgnoreCase("UpdateCustomerNPSDetails")){
				LogGEN.writeTrace("CBG_Log", "UpdateCustomerNPSDetails");		
				UpdateCustomerNPSDetails updateCustomerNPSDetails = new UpdateCustomerNPSDetails();
				return updateCustomerNPSDetails.updateCustomerNPSDetailsCall(inputXml);
			}
			else if (callType.equalsIgnoreCase("InqRetailCustomerRisk")) {
				LogGEN.writeTrace("CBG_Log", "InqRetailCustomerRisk");
				InqRetailCustomerRisk inqRetailCustomerRisk = new InqRetailCustomerRisk();
				return inqRetailCustomerRisk.inqRetailCustomerRiskCall(inputXml);
						
			}else if(callType.equalsIgnoreCase("AddDigitalSupplCard"))
			{
				LogGEN.writeTrace("CBG_Log", "AddDigitalSupplCard");		
				AddDigitalCreditCardSupplRequest AddDigitalCreditCardSupplRequest  = new AddDigitalCreditCardSupplRequest();
				return AddDigitalCreditCardSupplRequest.AddDigitalCreditCardSupplRequestCall(inputXml);
			}
			//			else if (callType.equalsIgnoreCase("AddSRServiceRequestOper")) {
//					LogGEN.writeTrace("CBG_Log", "AddSRServiceRequestOper");
//					AddSRServiceRequestOper addSRServiceRequestOper = new AddSRServiceRequestOper();
//					return addSRServiceRequestOper
//							.addSRServiceRequestOper(inputXml);
//			} 
			else if (callType.equalsIgnoreCase("RemoveProjectDelight")) {
				LogGEN.writeTrace("CBG_Log", "RemoveProjectDelight");
				RemoveProjectDelight removeProjectDelight = new RemoveProjectDelight();
				return removeProjectDelight.removeProjectDelight(inputXml);
			} else if (callType.equalsIgnoreCase("FetchCustOffersRequest")) {
				LogGEN.writeTrace("CBG_Log", "*****FetchCustOffersRequest*****");
				FetchCustOffersRequest fetchCustOffersRequest = new FetchCustOffersRequest();
				return fetchCustOffersRequest.FetchCustOffersRequestToFCR(inputXml);
//			} else if(callType.equalsIgnoreCase("PushMessageTopic")) {
//				LogGEN.writeTrace("CBG_Log", "*****PushMessageTopic*****");
//				PushMessageTopic pushMessageTopic = new PushMessageTopic();
//				return pushMessageTopic.kafkaMessage(inputXml);
			} else if(callType.equalsIgnoreCase("CustomerPersonalDetailsDA")) {
				LogGEN.writeTrace("CBG_Log", "*****CustomerPersonalDetailsDA*****");
				CustomerPersonalDetailsDA customerPersonalDetailsDA = new CustomerPersonalDetailsDA();
				return customerPersonalDetailsDA.FetchCustomerPersonalDetails(inputXml);
			} else if(callType.equalsIgnoreCase("InqCDMS")) {
				LogGEN.writeTrace("CBG_Log", "*****InqCDMS*****");
				InqCDMS InqCDMS = new InqCDMS();
				return InqCDMS.InqCDMS(inputXml);
			}else if(callType.equalsIgnoreCase("ModCreditCardLimitChange")) {
				LogGEN.writeTrace("CBG_Log", "*****ModCreditCardLimitChange*****");
				ModCreditCardLimitChange modCreditCardLimitChange = new ModCreditCardLimitChange();
				return modCreditCardLimitChange.ModCreditCardLimitChange(inputXml);
			}// CHANGES FOR SUPPLIMENTRY CARD 14 MAY 2024 BY Krishna Pandey
			 else if(callType.equalsIgnoreCase("InqSuppcardDetails")) {
				LogGEN.writeTrace("CBG_Log", "*****InqSuppcardDetails*****");
				InqSuppcardDetails creditDebit = new InqSuppcardDetails();
				return creditDebit.fetchSuppCardDetailCall(inputXml);
			}else if(callType.equalsIgnoreCase("InqCustPersonalDtl2")) {
				LogGEN.writeTrace("CBG_Log", "*****InqCustPersonalDtl2*****");
				InqCustPersonalDtl2 custDetail = new InqCustPersonalDtl2();
				return custDetail.fetchInqCustPersonalDtl2(inputXml);
			}else if(callType.equalsIgnoreCase("AddDigitalDebitCardRequest")) {
				LogGEN.writeTrace("CBG_Log", "*****AddDigitalDebitCard*****");
				AddDigitalDebitCardRequest debitCard = new AddDigitalDebitCardRequest();
				return debitCard.AddDigitalDebitCards(inputXml);
			}else if(callType.equalsIgnoreCase("AddDigitalSupplCardReq")) {
				LogGEN.writeTrace("CBG_Log", "*****AddDigitalSupplCardReq*****");
				AddDigitialCreditCardDscopRequest creditCard = new AddDigitialCreditCardDscopRequest();
				return creditCard.AddDigitalCreditCardDscopRequestCall(inputXml);
			}//END	
		} catch (Exception e) {
			LogGEN.writeTrace("CBG_Log", " exception in cbg call handler " + e);
		}
		return "";
	}

	public String setSenderId(String id) {
		if ((id == null) || ("null".equalsIgnoreCase(id.trim())) || ("".equalsIgnoreCase(id.trim()))) {
			return "WMS";
		}
		return id;
	}
}