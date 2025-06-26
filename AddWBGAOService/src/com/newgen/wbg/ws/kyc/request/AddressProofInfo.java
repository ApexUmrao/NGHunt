package com.newgen.wbg.ws.kyc.request;

public class AddressProofInfo {
	private String IsTradeContract;
	private String IsUtilityBill;
	private String IsBankAccStmt;
	private String IsPhoneBill;
	private String Others;
	public String getIsTradeContract() {
		return IsTradeContract;
	}
	public void setIsTradeContract(String isTradeContract) {
		IsTradeContract = isTradeContract;
	}
	public String getIsUtilityBill() {
		return IsUtilityBill;
	}
	public void setIsUtilityBill(String isUtilityBill) {
		IsUtilityBill = isUtilityBill;
	}
	public String getIsBankAccStmt() {
		return IsBankAccStmt;
	}
	public void setIsBankAccStmt(String isBankAccStmt) {
		IsBankAccStmt = isBankAccStmt;
	}
	public String getIsPhoneBill() {
		return IsPhoneBill;
	}
	public void setIsPhoneBill(String isPhoneBill) {
		IsPhoneBill = isPhoneBill;
	}
	public String getOthers() {
		return Others;
	}
	public void setOthers(String others) {
		Others = others;
	}
}
