package com.newgen.iforms.user.ao.model.casafulfillment;

public class CasaAccount {
	private CustomerAccountDetails customerAccountDetails;
    private DebitCardDetails debitCardDetails;
    private CASAAccountDetails casaAccountDetails;
    private ChequeDetails chequeDetails;
    
	public CustomerAccountDetails getCustomerAccountDetails() {
		return customerAccountDetails;
	}
	public void setCustomerAccountDetails(CustomerAccountDetails customerAccountDetails) {
		this.customerAccountDetails = customerAccountDetails;
	}
	public DebitCardDetails getDebitCardDetails() {
		return debitCardDetails;
	}
	public void setDebitCardDetails(DebitCardDetails debitCardDetails) {
		this.debitCardDetails = debitCardDetails;
	}
	public CASAAccountDetails getCasaAccountDetails() {
		return casaAccountDetails;
	}
	public void setCasaAccountDetails(CASAAccountDetails casaAccountDetails) {
		this.casaAccountDetails = casaAccountDetails;
	}
	public ChequeDetails getChequeDetails() {
		return chequeDetails;
	}
	public void setChequeDetails(ChequeDetails chequeDetails) {
		this.chequeDetails = chequeDetails;
	}
    
    

}
