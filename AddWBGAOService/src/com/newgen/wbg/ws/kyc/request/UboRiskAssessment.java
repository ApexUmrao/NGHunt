package com.newgen.wbg.ws.kyc.request;

public class UboRiskAssessment {
	private String IsOverAllRiskInc;
	private String IsOverAllRiskUnact;
	private String IsUboHawala;
	private String IsUboBusTradLnkSanctioned;
	public String getIsOverAllRiskInc() {
		return IsOverAllRiskInc;
	}
	public void setIsOverAllRiskInc(String isOverAllRiskInc) {
		IsOverAllRiskInc = isOverAllRiskInc;
	}
	public String getIsOverAllRiskUnact() {
		return IsOverAllRiskUnact;
	}
	public void setIsOverAllRiskUnact(String isOverAllRiskUnact) {
		IsOverAllRiskUnact = isOverAllRiskUnact;
	}
	public String getIsUboHawala() {
		return IsUboHawala;
	}
	public void setIsUboHawala(String isUboHawala) {
		IsUboHawala = isUboHawala;
	}
	public String getIsUboBusTradLnkSanctioned() {
		return IsUboBusTradLnkSanctioned;
	}
	public void setIsUboBusTradLnkSanctioned(String isUboBusTradLnkSanctioned) {
		IsUboBusTradLnkSanctioned = isUboBusTradLnkSanctioned;
	}
}
