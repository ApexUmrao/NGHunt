package com.newgen.ws.request;


public class AccountInfo {

	private String AccStatus;
	private String ProdCode;
	private String ProdCurr;
	private String AccBranch;
	private String ChequeReq;
	private String ModeFunding;
	private String DebitAccNo;
	private String DebitAmt;
	private String DebitCurr;
	private String AvBal;
	private String serviceChargePkg;
	//added by sheenu CR2019
	private String debitCardReq;
	private String operatingInstructions;
	private String modeOfOperation;
	private String accountType;
	private String accountTitle;
	private String cardHolderName;
	private String debitCompName;
	private String eStmtFlag;
	private String accntIVRFlag;
	private String waiverChargesFlag;
   // added by sahil for new tags in account 8-apr-2020
	private String cardProductGroup;
	private String noofchequeBooks;
	private String bookSize;
	private String Charges;
	private String ChargesWaivedFlag;
	private String WaiverReason;
	private String OtherReason;
	private String customerAddresszip;
	private String customerAddress1;
	private String customerAddress2;
	private String customerAddressCountry;
	private String customerAddressStateEmirate;
	private String customerAddressCity;
	private String customerMobileNo;
	private String flagDeliveryMode;
	private String CouriertoCustomer_3rdParty;
	private String CollectBranch;
	private String thirdPartyName;
	private String thirdPartyMobileNo;
	private String photoIdType;
	private String photoIdNo;
	// more new tags - sahil
	private String ATM_Flag;
	private String SI_Flag;
	private String IVR_Facility;
	private String IB_Facility;
	private String POS_Facility;
	


	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getDebitCompName() {
		return debitCompName;
	}
	public void setDebitCompName(String debitCompName) {
		this.debitCompName = debitCompName;
	}
	public String geteStmtFlag() {
		return eStmtFlag;
	}
	public void seteStmtFlag(String eStmtFlag) {
		this.eStmtFlag = eStmtFlag;
	}
	public String getAccntIVRFlag() {
		return accntIVRFlag;
	}
	public void setAccntIVRFlag(String accntIVRFlag) {
		this.accntIVRFlag = accntIVRFlag;
	}
	public String getWaiverChargesFlag() {
		return waiverChargesFlag;
	}
	public void setWaiverChargesFlag(String waiverChargesFlag) {
		this.waiverChargesFlag = waiverChargesFlag;
	}
	public String getAccStatus() {
		return AccStatus;
	}
	public void setAccStatus(String accStatus) {
		AccStatus = accStatus;
	}
	public String getProdCode() {
		return ProdCode;
	}
	public void setProdCode(String prodCode) {
		ProdCode = prodCode;
	}
	public String getProdCurr() {
		return ProdCurr;
	}
	public void setProdCurr(String prodCurr) {
		ProdCurr = prodCurr;
	}
	public String getAccBranch() {
		return AccBranch;
	}
	public void setAccBranch(String accBranch) {
		AccBranch = accBranch;
	}
	public String getChequeReq() {
		return ChequeReq;
	}
	public void setChequeReq(String chequeReq) {
		ChequeReq = chequeReq;
	}
	public String getModeFunding() {
		return ModeFunding;
	}
	public void setModeFunding(String modeFunding) {
		ModeFunding = modeFunding;
	}
	public String getDebitAccNo() {
		return DebitAccNo;
	}
	public void setDebitAccNo(String debitAccNo) {
		DebitAccNo = debitAccNo;
	}
	public String getDebitAmt() {
		return DebitAmt;
	}
	public void setDebitAmt(String debitAmt) {
		DebitAmt = debitAmt;
	}
	public String getDebitCurr() {
		return DebitCurr;
	}
	public void setDebitCurr(String debitCurr) {
		DebitCurr = debitCurr;
	}
	public String getAvBal() {
		return AvBal;
	}
	public void setAvBal(String avBal) {
		AvBal = avBal;
	}
	public String getServiceChargePkg() {
		return serviceChargePkg;
	}
	public void setServiceChargePkg(String serviceChargePkg) {
		this.serviceChargePkg = serviceChargePkg;
	}
	public String getDebitCardReq() {
		return debitCardReq;
	}
	public void setDebitCardReq(String debitCardReq) {
		this.debitCardReq = debitCardReq;
	}
	public String getOperatingInstructions() {
		return operatingInstructions;
	}
	public void setOperatingInstructions(String operatingInstructions) {
		this.operatingInstructions = operatingInstructions;
	}
	public String getModeOfOperation() {
		return modeOfOperation;
	}
	public void setModeOfOperation(String modeOfOperation) {
		this.modeOfOperation = modeOfOperation;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountTitle() {
		return accountTitle;
	}
	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	// by sahil for new chnages
	public String getCardProductGroup() {
		return cardProductGroup;
	}
	public void setCardProductGroup(String cardProductGroup) {
		this.cardProductGroup = cardProductGroup;
	}
	
	public String getNoofchequeBooks() {
		return noofchequeBooks;
	}
	public void setNoofchequeBooks(String noofchequeBooks) {
		this.noofchequeBooks = noofchequeBooks;
	}
	
	public String getBookSize() {
		return bookSize;
	}
	public void setBookSize(String bookSize) {
		this.bookSize = bookSize;
	}
	
	public String getCharges() {
		return Charges;
	}
	public void setCharges(String Charges) {
		this.Charges = Charges;
	}
	
	public String getChargesWaivedFlag() {
		return ChargesWaivedFlag;
	}
	public void setChargesWaivedFlag(String ChargesWaivedFlag) {
		this.ChargesWaivedFlag = ChargesWaivedFlag;
	}
	
	public String getWaiverReason() {
		return WaiverReason;
	}
	public void setWaiverReason(String WaiverReason) {
		this.WaiverReason = WaiverReason;
	}
	
	public String getOtherReason() {
		return OtherReason;
	}
	public void setOtherReason(String OtherReason) {
		this.OtherReason = OtherReason;
	}
	
	public String getCustomerAddresszip() {
		return customerAddresszip;
	}
	public void setCustomerAddresszip(String customerAddresszip) {
		this.customerAddresszip = customerAddresszip;
	}
	
	public String getCustomerAddress1() {
		return customerAddress1;
	}
	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}
	
	public String getCustomerAddress2() {
		return customerAddress2;
	}
	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}
	
	public String getCustomerAddressCountry() {
		return customerAddressCountry;
	}
	public void setCustomerAddressCountry(String customerAddressCountry) {
		this.customerAddressCountry = customerAddressCountry;
	}
	
	public String getCustomerAddressStateEmirate() {
		return customerAddressStateEmirate;
	}
	public void setCustomerAddressStateEmirate(String customerAddressStateEmirate) {
		this.customerAddressStateEmirate = customerAddressStateEmirate;
	}
	
	public String getCustomerAddressCity() {
		return customerAddressCity;
	}
	public void setCustomerAddressCity(String customerAddressCity) {
		this.customerAddressCity = customerAddressCity;
	}
	
	public String getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(String customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}
	
	public String getFlagDeliveryMode() {
		return flagDeliveryMode;
	}
	public void setFlagDeliveryMode(String flagDeliveryMode) {
		this.flagDeliveryMode = flagDeliveryMode;
	}
	
	public String getCouriertoCustomer_3rdParty() {
		return CouriertoCustomer_3rdParty;
	}
	public void setCouriertoCustomer_3rdParty(String CouriertoCustomer_3rdParty) {
		this.CouriertoCustomer_3rdParty = CouriertoCustomer_3rdParty;
	}
	
	public String getCollectBranch() {
		return CollectBranch;
	}
	public void setCollectBranch(String CollectBranch) {
		this.CollectBranch = CollectBranch;
	}
	public String getThirdPartyName() {
		return thirdPartyName;
	}
	public void setThirdPartyName(String thirdPartyName) {
		this.thirdPartyName = thirdPartyName;
	}
	

	public String getThirdPartyMobileNo() {
		return thirdPartyMobileNo;
	}
	public void setThirdPartyMobileNo(String thirdPartyMobileNo) {
		this.thirdPartyMobileNo = thirdPartyMobileNo;
	}
	
	public String getPhotoIdType() {
		return photoIdType;
	}
	public void setPhotoIdType(String photoIdType) {
		this.photoIdType = photoIdType;
	}
	
	
	public String getPhotoIdNo() {
		return photoIdNo;
	}
	public void setPhotoIdNo(String photoIdNo) {
		this.photoIdNo = photoIdNo;
	}
	
	// more new tags -m sahil
	
	public String getATM_Flag() {
		return ATM_Flag;
	}
	public void setATM_Flag(String ATM_Flag) {
		this.ATM_Flag = ATM_Flag;
	}
	
	public String getSI_Flag() {
		return SI_Flag;
	}
	public void setSI_Flag(String SI_Flag) {
		this.SI_Flag = SI_Flag;
	}
	
	
	public String getIVR_Facility() {
		return IVR_Facility;
	}
	public void setIVR_Facility(String IVR_Facility) {
		this.IVR_Facility = IVR_Facility;
	}
	
	
	public String getIB_Facility() {
		return IB_Facility;
	}
	public void setIB_Facility(String IB_Facility) {
		this.IB_Facility = IB_Facility;
	}
	
	
	public String getPOS_Facility() {
		return POS_Facility;
	}
	public void setPOS_Facility(String POS_Facility) {
		this.POS_Facility = POS_Facility;
	}
	
	
	
}
