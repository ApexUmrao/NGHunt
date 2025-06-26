package com.newgen.wbg.ws.kyc.request;

public class KYCGeneric {
	private GenInformation GeneralInfo;
	private CommercialInfo CommercialInfo;
	private AntiAccActivityInfo AntiAccActivityInfo;
	private CashPickupService CashPickupService;
	private RiskAssessment RiskAssessment;
	private EnhancedDueDeligenceInfo EnhancedDueDeligenceInfo;
	private AddressProofInfo AddressProofInfo;
	private SignOff SignOff;
	private String SourceOfFund;
	private String OverAllRisk;
	public GenInformation getGeneralInfo() {
		return GeneralInfo;
	}
	public void setGeneralInfo(GenInformation generalInfo) {
		GeneralInfo = generalInfo;
	}
	public CommercialInfo getCommercialInfo() {
		return CommercialInfo;
	}
	public void setCommercialInfo(CommercialInfo commercialInfo) {
		CommercialInfo = commercialInfo;
	}
	public AntiAccActivityInfo getAntiAccActivityInfo() {
		return AntiAccActivityInfo;
	}
	public void setAntiAccActivityInfo(AntiAccActivityInfo antiAccActivityInfo) {
		AntiAccActivityInfo = antiAccActivityInfo;
	}
	public CashPickupService getCashPickupService() {
		return CashPickupService;
	}
	public void setCashPickupService(CashPickupService cashPickupService) {
		CashPickupService = cashPickupService;
	}
	public RiskAssessment getRiskAssessment() {
		return RiskAssessment;
	}
	public void setRiskAssessment(RiskAssessment riskAssessment) {
		RiskAssessment = riskAssessment;
	}
	public EnhancedDueDeligenceInfo getEnhancedDueDeligenceInfo() {
		return EnhancedDueDeligenceInfo;
	}
	public void setEnhancedDueDeligenceInfo(
			EnhancedDueDeligenceInfo enhancedDueDeligenceInfo) {
		EnhancedDueDeligenceInfo = enhancedDueDeligenceInfo;
	}
	public AddressProofInfo getAddressProofInfo() {
		return AddressProofInfo;
	}
	public void setAddressProofInfo(AddressProofInfo addressProofInfo) {
		AddressProofInfo = addressProofInfo;
	}
	public SignOff getSignOff() {
		return SignOff;
	}
	public void setSignOff(SignOff signOff) {
		SignOff = signOff;
	}
	public String getSourceOfFund() {
		return SourceOfFund;
	}
	public void setSourceOfFund(String sourceOfFund) {
		SourceOfFund = sourceOfFund;
	}
	public String getOverAllRisk() {
		return OverAllRisk;
	}
	public void setOverAllRisk(String overAllRisk) {
		OverAllRisk = overAllRisk;
	}
}

