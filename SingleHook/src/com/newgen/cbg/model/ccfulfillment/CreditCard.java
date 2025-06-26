package com.newgen.cbg.model.ccfulfillment;

import java.util.ArrayList;

public class CreditCard {
	public String bankingType;
    public String cardType;
    public String creditLimit;
    public String creditFlag;
    public String embossingName;
    public String promoCode;
    public String cardLogo;
    public String statementDate;
    public String productName;
    public String productCode;
    public String directDebitAccountNumber;
    public String getDirectDebitAccountNumber() {
		return directDebitAccountNumber;
	}
	public void setDirectDebitAccountNumber(String directDebitAccountNumber) {
		this.directDebitAccountNumber = directDebitAccountNumber;
	}
	public ArrayList<SupplementaryCard> supplementaryCards;
    public Cobranding cobranding;
	public Cobranding getCobranding() {
		return cobranding;
	}
	public void setCobranding(Cobranding cobranding) {
		this.cobranding = cobranding;
	}
	public String getBankingType() {
		return bankingType;
	}
	public void setBankingType(String bankingType) {
		this.bankingType = bankingType;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}
	public String getCreditFlag() {
		return creditFlag;
	}
	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}
	
	public String getEmbossingName() {
		return embossingName;
	}
	public void setEmbossingName(String embossingName) {
		this.embossingName = embossingName;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getCardLogo() {
		return cardLogo;
	}
	public void setCardLogo(String cardLogo) {
		this.cardLogo = cardLogo;
	}
	public String getStatementDate() {
		return statementDate;
	}
	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public ArrayList<SupplementaryCard> getSupplementaryCards() {
		return supplementaryCards;
	}
	public void setSupplementaryCards(ArrayList<SupplementaryCard> supplementaryCards) {
		this.supplementaryCards = supplementaryCards;
	}
}
