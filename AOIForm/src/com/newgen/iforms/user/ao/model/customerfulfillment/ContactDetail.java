package com.newgen.iforms.user.ao.model.customerfulfillment;

public class ContactDetail {
	private String phone;
    private ResidentialAddress residentialAddress;
    private String mobile;
    private String emailId;
    private PermanentAddress permanentAddress;
    private OfficeAddress officeAddress;
    private CorrespondenceAddress correspondenceAddress;
    
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public ResidentialAddress getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(ResidentialAddress residentialAddress) {
		this.residentialAddress = residentialAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public PermanentAddress getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(PermanentAddress permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public OfficeAddress getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(OfficeAddress officeAddress) {
		this.officeAddress = officeAddress;
	}
	public CorrespondenceAddress getCorrespondenceAddress() {
		return correspondenceAddress;
	}
	public void setCorrespondenceAddress(CorrespondenceAddress correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}
}
