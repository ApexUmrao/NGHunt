package com.newgen.ws.request;

import com.newgen.ws.request.WmsHeader;
import com.newgen.ws.request.Company;
import com.newgen.ws.request.AccountDetails;
import com.newgen.ws.request.Customers;


public class AccountOpening {
	
	private WmsHeader WmsHeader;
	private Company Company;
	private AccountDetails AccountDetails;
	private Customers Customers;	
	private UBOwnerDetails UBOwnerDetails;
	private Header Header;	
	private ApplicationAttributes[] ApplicationAttributes;
	//CR2019
	private KeyContactsDetails KeyContactsDetails;
	//end
	public WmsHeader getWmsHeader() {
		return WmsHeader;
	}
	public KeyContactsDetails getKeyContactsDetails() {
		return KeyContactsDetails;
	}
	public void setKeyContactsDetails(KeyContactsDetails keyContactsDetails) {
		KeyContactsDetails = keyContactsDetails;
	}
	public void setWmsHeader(WmsHeader wmsHeader) {
		WmsHeader = wmsHeader;
	}
	public Company getCompany() {
		return Company;
	}
	public void setCompany(Company company) {
		Company = company;
	}
	public AccountDetails getAccountDetails() {
		return AccountDetails;
	}
	public void setAccountDetails(AccountDetails accountDetails) {
		AccountDetails = accountDetails;
	}
	
	public UBOwnerDetails getUBOwnerDetails() {
		return UBOwnerDetails;
	}
	public void setUBOwnerDetails(UBOwnerDetails uBOwnerDetails) {
		UBOwnerDetails = uBOwnerDetails;
	}
	public Customers getCustomers() {
		return Customers;
	}
	public void setCustomers(Customers customers) {
		Customers = customers;
	}
	public Header getHeader() {
		return Header;
	}
	public void setHeader(Header header) {
		Header = header;
	}
	
	public ApplicationAttributes[] getApplicationAttributes() {
		return ApplicationAttributes;
	}
	public void setApplicationAttributes(ApplicationAttributes[] ApplicationAttributes) {
		this.ApplicationAttributes = ApplicationAttributes;
	}
}
