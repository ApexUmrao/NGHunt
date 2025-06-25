package com.newgen.cbg.model.ccfulfillment;

import java.util.ArrayList;


public class Payload {
	public String dsaCode;
    public Customer customer;
    public ArrayList<CreditCard> creditCards;
	public String getDsaCode() {
		return dsaCode;
	}
	public void setDsaCode(String dsaCode) {
		this.dsaCode = dsaCode;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public ArrayList<CreditCard> getCreditCards() {
		return creditCards;
	}
	public void setCreditCards(ArrayList<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}
}
