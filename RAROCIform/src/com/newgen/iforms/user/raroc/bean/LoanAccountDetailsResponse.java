package com.newgen.iforms.user.raroc.bean;

import org.codehaus.jackson.annotate.JsonProperty;

public class LoanAccountDetailsResponse {
	 @JsonProperty("LoanCategoryName") 
	    public String loanCategoryName;
	    @JsonProperty("LoanCurrency") 
	    public String loanCurrency;
	    @JsonProperty("CurrentOutstandingAmt") 
	    public String currentOutstandingAmt;
	    @JsonProperty("LoanPrincipalAmount") 
	    public String loanPrincipalAmount;
	    @JsonProperty("LoanPaidAmount") 
	    public String loanPaidAmount;
	    @JsonProperty("Openingdate") 
	    public String openingdate;
	    @JsonProperty("EndDate") 
	    public String endDate;
	    @JsonProperty("NextAmountDetails") 
	    public String nextAmountDetails;
	    @JsonProperty("RimNumber") 
	    public String rimNumber;
	    @JsonProperty("TotalEMIsPaid") 
	    public String totalEMIsPaid;
	    @JsonProperty("PendingEMIs") 
	    public String pendingEMIs;
	    @Override
		public String toString() {
			return "LoanAccountDetailsResponse [loanCategoryName=" + loanCategoryName + ", loanCurrency=" + loanCurrency
					+ ", currentOutstandingAmt=" + currentOutstandingAmt + ", loanPrincipalAmount="
					+ loanPrincipalAmount + ", loanPaidAmount=" + loanPaidAmount + ", openingdate=" + openingdate
					+ ", endDate=" + endDate + ", nextAmountDetails=" + nextAmountDetails + ", rimNumber=" + rimNumber
					+ ", totalEMIsPaid=" + totalEMIsPaid + ", pendingEMIs=" + pendingEMIs + ", loanOverdueAmount="
					+ loanOverdueAmount + ", nextPaymentDate=" + nextPaymentDate + ", interestRate=" + interestRate
					+ ", annualInterestRate=" + annualInterestRate + ", deferredAmount=" + deferredAmount
					+ ", accruedInterest=" + accruedInterest + ", outstandingPrincipal=" + outstandingPrincipal
					+ ", accruedInterest15D=" + accruedInterest15D + ", loanArrangementID=" + loanArrangementID + "]";
		}
		@JsonProperty("LoanOverdueAmount") 
	    public String loanOverdueAmount;
	    @JsonProperty("NextPaymentDate") 
	    public String nextPaymentDate;
	    @JsonProperty("InterestRate") 
	    public String interestRate;
	    @JsonProperty("AnnualInterestRate") 
	    public String annualInterestRate;
	    @JsonProperty("DeferredAmount") 
	    public String deferredAmount;
	    @JsonProperty("AccruedInterest") 
	    public String accruedInterest;
	    @JsonProperty("OutstandingPrincipal") 
	    public String outstandingPrincipal;
	    @JsonProperty("AccruedInterest15D") 
	    public String accruedInterest15D;
	    @JsonProperty("LoanArrangementID") 
	    public String loanArrangementID;
		public String getLoanCategoryName() {
			return loanCategoryName;
		}
		public void setLoanCategoryName(String loanCategoryName) {
			this.loanCategoryName = loanCategoryName;
		}
		public String getLoanCurrency() {
			return loanCurrency;
		}
		public void setLoanCurrency(String loanCurrency) {
			this.loanCurrency = loanCurrency;
		}
		public String getCurrentOutstandingAmt() {
			return currentOutstandingAmt;
		}
		public void setCurrentOutstandingAmt(String currentOutstandingAmt) {
			this.currentOutstandingAmt = currentOutstandingAmt;
		}
		public String getLoanPrincipalAmount() {
			return loanPrincipalAmount;
		}
		public void setLoanPrincipalAmount(String loanPrincipalAmount) {
			this.loanPrincipalAmount = loanPrincipalAmount;
		}
		public String getLoanPaidAmount() {
			return loanPaidAmount;
		}
		public void setLoanPaidAmount(String loanPaidAmount) {
			this.loanPaidAmount = loanPaidAmount;
		}
		public String getOpeningdate() {
			return openingdate;
		}
		public void setOpeningdate(String openingdate) {
			this.openingdate = openingdate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getNextAmountDetails() {
			return nextAmountDetails;
		}
		public void setNextAmountDetails(String nextAmountDetails) {
			this.nextAmountDetails = nextAmountDetails;
		}
		public String getRimNumber() {
			return rimNumber;
		}
		public void setRimNumber(String rimNumber) {
			this.rimNumber = rimNumber;
		}
		public String getTotalEMIsPaid() {
			return totalEMIsPaid;
		}
		public void setTotalEMIsPaid(String totalEMIsPaid) {
			this.totalEMIsPaid = totalEMIsPaid;
		}
		public String getPendingEMIs() {
			return pendingEMIs;
		}
		public void setPendingEMIs(String pendingEMIs) {
			this.pendingEMIs = pendingEMIs;
		}
		public String getLoanOverdueAmount() {
			return loanOverdueAmount;
		}
		public void setLoanOverdueAmount(String loanOverdueAmount) {
			this.loanOverdueAmount = loanOverdueAmount;
		}
		public String getNextPaymentDate() {
			return nextPaymentDate;
		}
		public void setNextPaymentDate(String nextPaymentDate) {
			this.nextPaymentDate = nextPaymentDate;
		}
		public String getInterestRate() {
			return interestRate;
		}
		public void setInterestRate(String interestRate) {
			this.interestRate = interestRate;
		}
		public String getAnnualInterestRate() {
			return annualInterestRate;
		}
		public void setAnnualInterestRate(String annualInterestRate) {
			this.annualInterestRate = annualInterestRate;
		}
		public String getDeferredAmount() {
			return deferredAmount;
		}
		public void setDeferredAmount(String deferredAmount) {
			this.deferredAmount = deferredAmount;
		}
		public String getAccruedInterest() {
			return accruedInterest;
		}
		public void setAccruedInterest(String accruedInterest) {
			this.accruedInterest = accruedInterest;
		}
		public String getOutstandingPrincipal() {
			return outstandingPrincipal;
		}
		public void setOutstandingPrincipal(String outstandingPrincipal) {
			this.outstandingPrincipal = outstandingPrincipal;
		}
		public String getAccruedInterest15D() {
			return accruedInterest15D;
		}
		public void setAccruedInterest15D(String accruedInterest15D) {
			this.accruedInterest15D = accruedInterest15D;
		}
		public String getLoanArrangementID() {
			return loanArrangementID;
		}
		public void setLoanArrangementID(String loanArrangementID) {
			this.loanArrangementID = loanArrangementID;
		}
}
