package com.newgen.iforms.user.raroc.bean;

import java.util.ArrayList;

public class T24LoanServiceResponse {
	
	public LoanExceptionDetails loanexceptionDetails;
    public ArrayList<LoanAccountDetailsResponse> loanAccountDetailsResponse;
    
	public LoanExceptionDetails getLoanexceptionDetails() {
		return loanexceptionDetails;
	}
	public void setLoanexceptionDetails(LoanExceptionDetails loanexceptionDetails) {
		this.loanexceptionDetails = loanexceptionDetails;
	}
	public ArrayList<LoanAccountDetailsResponse> getLoanAccountDetailsResponse() {
		return loanAccountDetailsResponse;
	}
	public void setLoanAccountDetailsResponse(ArrayList<LoanAccountDetailsResponse> loanAccountDetailsResponse) {
		this.loanAccountDetailsResponse = loanAccountDetailsResponse;
	}
    

}
