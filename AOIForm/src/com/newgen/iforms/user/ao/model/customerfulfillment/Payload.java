package com.newgen.iforms.user.ao.model.customerfulfillment;

public class Payload {
	
    private EmploymentDetail employmentDetail;

    private ContactDetail contactDetail;

    private UaeNational uaeNational;

    private String custCountry;

    private BankingDetail bankingDetail;

    private Signature signature;

    private CustomerDocument customerDocument;

    private PersonalDetails personalDetails;

	public EmploymentDetail getEmploymentDetail() {
		return employmentDetail;
	}

	public void setEmploymentDetail(EmploymentDetail employmentDetail) {
		this.employmentDetail = employmentDetail;
	}

	public ContactDetail getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(ContactDetail contactDetail) {
		this.contactDetail = contactDetail;
	}

	public UaeNational getUaeNational() {
		return uaeNational;
	}

	public void setUaeNational(UaeNational uaeNational) {
		this.uaeNational = uaeNational;
	}

	public String getCustCountry() {
		return custCountry;
	}

	public void setCustCountry(String custCountry) {
		this.custCountry = custCountry;
	}

	public BankingDetail getBankingDetail() {
		return bankingDetail;
	}

	public void setBankingDetail(BankingDetail bankingDetail) {
		this.bankingDetail = bankingDetail;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public CustomerDocument getCustomerDocument() {
		return customerDocument;
	}

	public void setCustomerDocument(CustomerDocument customerDocument) {
		this.customerDocument = customerDocument;
	}

	public PersonalDetails getPersonalDetails() {
		return personalDetails;
	}

	public void setPersonalDetails(PersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}

}
