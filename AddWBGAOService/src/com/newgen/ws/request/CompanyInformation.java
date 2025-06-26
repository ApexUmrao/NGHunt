package com.newgen.ws.request;

public class CompanyInformation {
	
	private CompanyDetails CompanyDetails;
	private AddressCorrDets AddressCorrDets;
	private AddressPhyDets AddressPhyDets;
	private IncorporationDets IncorporationDets;
	private ContactInfo ContactInfo;
	public CompanyDetails getCompanyDetails() {
		return CompanyDetails;
	}
	public void setCompanyDetails(CompanyDetails companyDetails) {
		CompanyDetails = companyDetails;
	}
	public AddressCorrDets getAddressCorrDets() {
		return AddressCorrDets;
	}
	public void setAddressCorrDets(AddressCorrDets addressCorrDets) {
		AddressCorrDets = addressCorrDets;
	}
	public AddressPhyDets getAddressPhyDets() {
		return AddressPhyDets;
	}
	public void setAddressPhyDets(AddressPhyDets addressPhyDets) {
		AddressPhyDets = addressPhyDets;
	}
	public ContactInfo getContactInfo() {
		return ContactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		ContactInfo = contactInfo;
	}
	public IncorporationDets getIncorporationDets() {
		return IncorporationDets;
	}
	public void setIncorporationDets(IncorporationDets incorporationDets) {
		IncorporationDets = incorporationDets;
	}
	

}
