package com.newgen.wbg.ws.kyc.request;

public class RiskAssessment {
private LegEntRiskAssessment LegEntRiskAssessment;
private UboRiskAssessment UboRiskAssessment;
private String IsNUaePep;
private String IsNUaeOwnedEnty;
private String isPEPRiskQ3; //Added by Shivanshu

public LegEntRiskAssessment getLegEntRiskAssessment() {
	return LegEntRiskAssessment;
}
public void setLegEntRiskAssessment(LegEntRiskAssessment legEntRiskAssessment) {
	LegEntRiskAssessment = legEntRiskAssessment;
}
public UboRiskAssessment getUboRiskAssessment() {
	return UboRiskAssessment;
}
public void setUboRiskAssessment(UboRiskAssessment uboRiskAssessment) {
	UboRiskAssessment = uboRiskAssessment;
}
public String getIsNUaePep() {
	return IsNUaePep;
}
public void setIsNUaePep(String isNUaePep) {
	IsNUaePep = isNUaePep;
}
public String getIsNUaeOwnedEnty() {
	return IsNUaeOwnedEnty;
}
public void setIsNUaeOwnedEnty(String isNUaeOwnedEnty) {
	IsNUaeOwnedEnty = isNUaeOwnedEnty;
}
//Added by Shivanshu
public String getIsPEPRiskQ3() {
	return isPEPRiskQ3;
}
public void setIsPEPRiskQ3(String isPEPRiskQ3) {
	this.isPEPRiskQ3 = isPEPRiskQ3;
}
}
