package com.newgen.iforms.user.ao.model.customerfulfillment;

public class BankingDetail {
	private String customerTypeCOD;
    private String customerClass;
    private String misCod1;
    private String misCod2;
    private String customerTypeFlag;
    private String homeBranch;
    private String misCod3;
    private String misCod4;
    // COLMP-10761|07072024|KRISHAN
    private String dsaCode;
	private String riskClassification;
    private String promoCode;
    
	public String getCustomerTypeCOD() {
		return customerTypeCOD;
	}
	public void setCustomerTypeCOD(String customerTypeCOD) {
		this.customerTypeCOD = customerTypeCOD;
	}
	public String getCustomerClass() {
		return customerClass;
	}
	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}
	public String getMisCod1() {
		return misCod1;
	}
	public void setMisCod1(String misCod1) {
		this.misCod1 = misCod1;
	}
	public String getMisCod2() {
		return misCod2;
	}
	public void setMisCod2(String misCod2) {
		this.misCod2 = misCod2;
	}
	public String getCustomerTypeFlag() {
		return customerTypeFlag;
	}
	public void setCustomerTypeFlag(String customerTypeFlag) {
		this.customerTypeFlag = customerTypeFlag;
	}
	public String getHomeBranch() {
		return homeBranch;
	}
	public void setHomeBranch(String homeBranch) {
		this.homeBranch = homeBranch;
	}
	public String getMisCod3() {
		return misCod3;
	}
	public void setMisCod3(String misCod3) {
		this.misCod3 = misCod3;
	}
	public String getMisCod4() {
		return misCod4;
	}
	public void setMisCod4(String misCod4) {
		this.misCod4 = misCod4;
	}
	public String getDsaCode() {
		return dsaCode;
	}
	public void setDsaCode(String dsaCode) {
		this.dsaCode = dsaCode;
	}
	public String getRiskClassification() {
		return riskClassification;
	}
	public void setRiskClassification(String riskClassification) {
		this.riskClassification = riskClassification;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

}
