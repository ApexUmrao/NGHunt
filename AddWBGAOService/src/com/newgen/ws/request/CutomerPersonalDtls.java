package com.newgen.ws.request;

public class CutomerPersonalDtls {
	
	private String custtype;
	private String CustID;
	private String CustIC;
	private String CustCat;
	private String CustPrefix;
	private String HomeBrCode;
	private String CustFullName;
	private String ProfitCenter;
	private String DOB;
	private String Nationality;
	private String CountryRes;
	private String PassportNo;
	private String PassportIssueDate;
	private String PassportExpiryDate;
	private String SignatureType;
	private String TelMob;
	private String TelLandLine;
	private String VisaNo;
	private String VisaIssueDate;
	private String VisaExpiryDate;
	private String EIDANumber;
	private String EIDAIssueDate;
	private String EIDAExpiryDate;
	private String Address;
	private String AddressLine1;
	private String AddressLine2;
	private String Town;
	private String State;
	private String CorrCntry;
	private String Zip;
	private String Gender;
	private String Religion;
	private String Profession;
	private String MaritalSts;
	private DocumentsDtls DocumentsDtls;
	//added by sharan new fields 01/07/2020 
	private String sigUpdReq;
	private String sigSeqNo;
	private String emailId;
	private String nameUpdateFlag;
	private String countryResidenceFlag;
	private String countryCorrespondanceFlag;
	private String passportUpdateFlag;
	private String visaUpdateFlag;
	private String mobileUpdateFlag;
	private String emailUpdateFlag;
	//private AppAUSAttributes appAUSAttributes;
	private String requestClassification;
	private String rmCode;
    private String custUpgradeflg;
    private String embossName;
    private String cardProductGroup;
    private String instantFlag;
    private String customerClassification;
//Added by SHivanshu ATP-426
    private String  secondNationality;
    private String  pepFlag;
    private String  extrafield1;
    private String  extrafield2;
	
	public String getCardProductGroup() {
		return cardProductGroup;
	}
	public void setCardProductGroup(String cardProductGroup) {
		this.cardProductGroup = cardProductGroup;
	}
	public String getInstantFlag() {
		return instantFlag;
	}
	public void setInstantFlag(String instantFlag) {
		this.instantFlag = instantFlag;
	}
	public String getCustomerClassification() {
		return customerClassification;
	}
	public void setCustomerClassification(String customerClassification) {
		this.customerClassification = customerClassification;
	}
	public String getEmbossName() {
		return embossName;
	}
	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}
	public String getCustUpgradeflg() {
		return custUpgradeflg;
	}
	public void setCustUpgradeflg(String custUpgradeflg) {
		this.custUpgradeflg = custUpgradeflg;
	}
	public String getRmCode() {
		return rmCode;
	}
	public void setRmCode(String rmCode) {
		this.rmCode = rmCode;
	}
	public String getRequestClassification() {
		return requestClassification;
	}
	public void setRequestClassification(String requestClassification) {
		this.requestClassification = requestClassification;
	}
	public String getCustID() {
		return CustID;
	}
	public void setCustID(String custID) {
		CustID = custID;
	}
	public String getCustIC() {
		return CustIC;
	}
	public void setCustIC(String custIC) {
		CustIC = custIC;
	}
	public String getCustCat() {
		return CustCat;
	}
	public void setCustCat(String custCat) {
		CustCat = custCat;
	}
	public String getCustPrefix() {
		return CustPrefix;
	}
	public void setCustPrefix(String custPrefix) {
		CustPrefix = custPrefix;
	}
	public String getHomeBrCode() {
		return HomeBrCode;
	}
	public void setHomeBrCode(String homeBrCode) {
		HomeBrCode = homeBrCode;
	}
	public String getCustFullName() {
		return CustFullName;
	}
	public void setCustFullName(String custFullName) {
		CustFullName = custFullName;
	}
	public String getProfitCenter() {
		return ProfitCenter;
	}
	public void setProfitCenter(String profitCenter) {
		ProfitCenter = profitCenter;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getNationality() {
		return Nationality;
	}
	public void setNationality(String nationality) {
		Nationality = nationality;
	}
	public String getCountryRes() {
		return CountryRes;
	}
	public void setCountryRes(String countryRes) {
		CountryRes = countryRes;
	}
	public String getPassportNo() {
		return PassportNo;
	}
	public void setPassportNo(String passportNo) {
		PassportNo = passportNo;
	}
	public String getPassportIssueDate() {
		return PassportIssueDate;
	}
	public void setPassportIssueDate(String passportIssueDate) {
		PassportIssueDate = passportIssueDate;
	}
	public String getPassportExpiryDate() {
		return PassportExpiryDate;
	}
	public void setPassportExpiryDate(String passportExpiryDate) {
		PassportExpiryDate = passportExpiryDate;
	}
	public String getSignatureType() {
		return SignatureType;
	}
	public void setSignatureType(String signatureType) {
		SignatureType = signatureType;
	}
	public String getTelMob() {
		return TelMob;
	}
	public void setTelMob(String telMob) {
		TelMob = telMob;
	}
	public String getTelLandLine() {
		return TelLandLine;
	}
	public void setTelLandLine(String telLandLine) {
		TelLandLine = telLandLine;
	}
	public String getVisaNo() {
		return VisaNo;
	}
	public void setVisaNo(String visaNo) {
		VisaNo = visaNo;
	}
	public String getVisaIssueDate() {
		return VisaIssueDate;
	}
	public void setVisaIssueDate(String visaIssueDate) {
		VisaIssueDate = visaIssueDate;
	}
	public String getVisaExpiryDate() {
		return VisaExpiryDate;
	}
	public void setVisaExpiryDate(String visaExpiryDate) {
		VisaExpiryDate = visaExpiryDate;
	}
	public String getEIDANumber() {
		return EIDANumber;
	}
	public void setEIDANumber(String eIDANumber) {
		EIDANumber = eIDANumber;
	}
	public String getEIDAIssueDate() {
		return EIDAIssueDate;
	}
	public void setEIDAIssueDate(String eIDAIssueDate) {
		EIDAIssueDate = eIDAIssueDate;
	}
	public String getEIDAExpiryDate() {
		return EIDAExpiryDate;
	}
	public void setEIDAExpiryDate(String eIDAExpiryDate) {
		EIDAExpiryDate = eIDAExpiryDate;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getAddressLine1() {
		return AddressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		AddressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return AddressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		AddressLine2 = addressLine2;
	}
	public String getTown() {
		return Town;
	}
	public void setTown(String town) {
		Town = town;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCorrCntry() {
		return CorrCntry;
	}
	public void setCorrCntry(String corrCntry) {
		CorrCntry = corrCntry;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		Zip = zip;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getReligion() {
		return Religion;
	}
	public void setReligion(String religion) {
		Religion = religion;
	}
	public String getProfession() {
		return Profession;
	}
	public void setProfession(String profession) {
		Profession = profession;
	}
	public String getMaritalSts() {
		return MaritalSts;
	}
	public void setMaritalSts(String maritalSts) {
		MaritalSts = maritalSts;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public DocumentsDtls getDocumentsDtls() {
		return DocumentsDtls;
	}
	public void setDocumentsDtls(DocumentsDtls documentsDtls) {
		DocumentsDtls = documentsDtls;
	}
	//addedd by sharan new tags 01/07/2020
	public String getsigUpdReq() {
		return sigUpdReq;
	}
	public void setsigUpdReq(String sigUpdReq) {
		this.sigUpdReq = sigUpdReq;
	}
	public String getsigSeqNo() {
		return sigSeqNo;
	}
	public void setsigSeqNo(String sigSeqNo) {
		this.sigSeqNo = sigSeqNo;
	}
	
	public String getnameUpdateFlag() {
		return nameUpdateFlag;
	}
	public void setnameUpdateFlag(String nameUpdateFlag) {
		this.nameUpdateFlag = nameUpdateFlag;
	}
	
	public String getcountryResidenceFlag() {
		return countryResidenceFlag;
	}
	public void setcountryResidenceFlag(String countryResidenceFlag) {
		this.countryResidenceFlag = countryResidenceFlag;
	}
	public String getcountryCorrespondanceFlag() {
		return countryCorrespondanceFlag;
	}
	public void setcountryCorrespondanceFlag(String countryCorrespondanceFlag) {
		this.countryCorrespondanceFlag = countryCorrespondanceFlag;
	}
	public String getpassportUpdateFlag() {
		return passportUpdateFlag;
	}
	public void setpassportUpdateFlag(String passportUpdateFlag) {
		this.passportUpdateFlag = passportUpdateFlag;
	}
	public String getvisaUpdateFlag() {
		return visaUpdateFlag;
	}
	public void setvisaUpdateFlag(String visaUpdateFlag) {
		this.visaUpdateFlag = visaUpdateFlag;
	}
	public String getmobileUpdateFlag() {
		return mobileUpdateFlag;
	}
	public void setmobileUpdateFlag(String mobileUpdateFlag) {
		this.mobileUpdateFlag = mobileUpdateFlag;
	}
	public String getemailUpdateFlag() {
		return emailUpdateFlag;
	}
	public void setemailUpdateFlag(String emailUpdateFlag) {
		this.emailUpdateFlag = emailUpdateFlag;
	}
	public String getemailId() {
		return emailId;
	}
	public void setemailId(String emialId) {
		this.emailId = emialId;
	}
	/*public AppAUSAttributes getappAUSAttributes() {
	return appAUSAttributes;
	}
	public void setappAUSAttributes(AppAUSAttributes appAUSAttributes) {
	this.appAUSAttributes = appAUSAttributes;
	}
	*/
//Added by SHivanshu ATP-426
	public String getSecondNationality() {
		return secondNationality;
	}
	public void setSecondNationality(String secondNationality) {
		this.secondNationality = secondNationality;
	}
	public String getPepFlag() {
		return pepFlag;
	}
	public void setPepFlag(String pepFlag) {
		this.pepFlag = pepFlag;
	}
	public String getExtrafield1() {
		return extrafield1;
	}
	public void setExtrafield1(String extrafield1) {
		this.extrafield1 = extrafield1;
	}
	public String getExtrafield2() {
		return extrafield2;
	}
	public void setExtrafield2(String extrafield2) {
		this.extrafield2 = extrafield2;
	}
}
