package com.newgen.wbg.ws.kyc.request;

public class KYC {

	private AffiliatedEntityDtls AffiliatedEntityDtls;
	private KYCGeneric KycGeneric;
	private MajorClientsInfo MajorClientsInfo;
	private MajorSuppliersInfo MajorSuppliersInfo;
	private OwnershipInfo OwnershipInfo;
	private SubsidiariesInfo SubsidiariesInfo;
	private UBOInfo UboInfo;
	private RISKInfo RiskInfo; //Added by SHivanshu ATP-426
	
	public AffiliatedEntityDtls getAffiliatedEntityDtls() {
		return AffiliatedEntityDtls;
	}
	public void setAffiliatedEntityDtls(AffiliatedEntityDtls affiliatedEntityDtls) {
		AffiliatedEntityDtls = affiliatedEntityDtls;
	}
	
	public MajorClientsInfo getMajorClientsInfo() {
		return MajorClientsInfo;
	}
	public void setMajorClientsInfo(MajorClientsInfo majorClientsInfo) {
		MajorClientsInfo = majorClientsInfo;
	}
	public MajorSuppliersInfo getMajorSuppliersInfo() {
		return MajorSuppliersInfo;
	}
	public void setMajorSuppliersInfo(MajorSuppliersInfo majorSuppliersInfo) {
		MajorSuppliersInfo = majorSuppliersInfo;
	}
	public OwnershipInfo getOwnershipInfo() {
		return OwnershipInfo;
	}
	public void setOwnershipInfo(OwnershipInfo ownershipInfo) {
		OwnershipInfo = ownershipInfo;
	}
	public SubsidiariesInfo getSubsidiariesInfo() {
		return SubsidiariesInfo;
	}
	public void setSubsidiariesInfo(SubsidiariesInfo subsidiariesInfo) {
		SubsidiariesInfo = subsidiariesInfo;
	}
	public UBOInfo getUboInfo() {
		return UboInfo;
	}
	public void setUboInfo(UBOInfo uboInfo) {
		UboInfo = uboInfo;
	}
	public KYCGeneric getKycGeneric() {
		return KycGeneric;
	}
	public void setKycGeneric(KYCGeneric kycGeneric) {
		KycGeneric = kycGeneric;
	}
//Added by SHivanshu ATP-426
	public RISKInfo getRiskInfo() {
		return RiskInfo;
	}
	public void setRiskInfo(RISKInfo riskInfo) {
		RiskInfo = riskInfo;
	}
	
}
