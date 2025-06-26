package com.newgen.wbg.ws.kyc.request;

public class AntiAccActivityInfo {

	private ExpectedMonthlyDepositsDtls ExpectedMonthlyDepositsDtls;
	private CorssBorderPaymentsDtls CorssBorderPaymentsDtls;
	public ExpectedMonthlyDepositsDtls getExpectedMonthlyDepositsDtls() {
		return ExpectedMonthlyDepositsDtls;
	}
	public void setExpectedMonthlyDepositsDtls(
			ExpectedMonthlyDepositsDtls expectedMonthlyDepositsDtls) {
		ExpectedMonthlyDepositsDtls = expectedMonthlyDepositsDtls;
	}
	public CorssBorderPaymentsDtls getCorssBorderPaymentsDtls() {
		return CorssBorderPaymentsDtls;
	}
	public void setCorssBorderPaymentsDtls(
			CorssBorderPaymentsDtls corssBorderPaymentsDtls) {
		CorssBorderPaymentsDtls = corssBorderPaymentsDtls;
	}
}
