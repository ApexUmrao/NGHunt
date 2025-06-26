package com.newgen.iforms.user.ao.model.casafulfillment;

import java.util.List;

public class Payload {
	
	private String customerId;
    private List<CasaAccount> casaAccounts;
    
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public List<CasaAccount> getCasaAccounts() {
		return casaAccounts;
	}
	public void setCasaAccounts(List<CasaAccount> casaAccounts) {
		this.casaAccounts = casaAccounts;
	}
}
