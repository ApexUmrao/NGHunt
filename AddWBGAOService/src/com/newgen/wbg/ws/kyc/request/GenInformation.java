package com.newgen.wbg.ws.kyc.request;

public class GenInformation {
	private String CIF;
	private String ProfilingPurpose;
	private String RelationshipSince;
	private String BusinessNature;
	private String PurposeAOADCB;
	//Added by Shivanshu
	private String ActiveAccountFlag;
	private String PurposeAOADCBOthers;
	private String IsDualGoods;
	private String DualGoodsType;
	
	public String getCIF() {
		return CIF;
	}
	public void setCIF(String cIF) {
		CIF = cIF;
	}
	public String getProfilingPurpose() {
		return ProfilingPurpose;
	}
	public void setProfilingPurpose(String profilingPurpose) {
		ProfilingPurpose = profilingPurpose;
	}
	public String getRelationshipSince() {
		return RelationshipSince;
	}
	public void setRelationshipSince(String relationshipSince) {
		RelationshipSince = relationshipSince;
	}
	public String getBusinessNature() {
		return BusinessNature;
	}
	public void setBusinessNature(String businessNature) {
		BusinessNature = businessNature;
	}
	public String getPurposeAOADCB() {
		return PurposeAOADCB;
	}
	public void setPurposeAOADCB(String purposeAOADCB) {
		PurposeAOADCB = purposeAOADCB;
	}
	//Added by SHivanshu
	public String getActiveAccountFlag() {
		return ActiveAccountFlag;
	}
	public void setActiveAccountFlag(String activeAccountFlag) {
		ActiveAccountFlag = activeAccountFlag;
	}
	public String getPurposeAOADCBOthers() {
		return PurposeAOADCBOthers;
	}
	public void setPurposeAOADCBOthers(String purposeAOADCBOthers) {
		PurposeAOADCBOthers = purposeAOADCBOthers;
	}
	public String getIsDualGoods() {
		return IsDualGoods;
	}
	public void setIsDualGoods(String isDualGoods) {
		IsDualGoods = isDualGoods;
	}
	public String getDualGoodsType() {
		return DualGoodsType;
	}
	public void setDualGoodsType(String dualGoodsType) {
		DualGoodsType = dualGoodsType;
	}
}
