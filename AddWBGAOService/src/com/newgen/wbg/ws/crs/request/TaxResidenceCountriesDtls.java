package com.newgen.wbg.ws.crs.request;

public class TaxResidenceCountriesDtls {
	private String taxResidenceCountry;
	private String taxpayerIdNumber;
	private String reasonId;
	private String reasonDesc;
	private String reportableFlag;
	public String getTaxResidenceCountry() {
		return taxResidenceCountry;
	}
	public void setTaxResidenceCountry(String taxResidenceCountry) {
		this.taxResidenceCountry = taxResidenceCountry;
	}
	public String getTaxpayerIdNumber() {
		return taxpayerIdNumber;
	}
	public void setTaxpayerIdNumber(String taxpayerIdNumber) {
		this.taxpayerIdNumber = taxpayerIdNumber;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getReportableFlag() {
		return reportableFlag;
	}
	public void setReportableFlag(String reportableFlag) {
		this.reportableFlag = reportableFlag;
	}
}
