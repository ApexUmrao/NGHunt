package com.newgen.wbg.ws.kyc.request;

public class CorssBorderPaymentsDtls {
	private String IsCBPayment;
	private String PurposeOfPayment;
	private String NoOfPaymentPerMonth;
	private String MonthlyValPayment;
	private String CntryMadetoRcvdFrom;
	public String getIsCBPayment() {
		return IsCBPayment;
	}
	public void setIsCBPayment(String isCBPayment) {
		IsCBPayment = isCBPayment;
	}
	public String getPurposeOfPayment() {
		return PurposeOfPayment;
	}
	public void setPurposeOfPayment(String purposeOfPayment) {
		PurposeOfPayment = purposeOfPayment;
	}
	public String getNoOfPaymentPerMonth() {
		return NoOfPaymentPerMonth;
	}
	public void setNoOfPaymentPerMonth(String noOfPaymentPerMonth) {
		NoOfPaymentPerMonth = noOfPaymentPerMonth;
	}
	public String getMonthlyValPayment() {
		return MonthlyValPayment;
	}
	public void setMonthlyValPayment(String monthlyValPayment) {
		MonthlyValPayment = monthlyValPayment;
	}
	public String getCntryMadetoRcvdFrom() {
		return CntryMadetoRcvdFrom;
	}
	public void setCntryMadetoRcvdFrom(String cntryMadetoRcvdFrom) {
		CntryMadetoRcvdFrom = cntryMadetoRcvdFrom;
	}
}
