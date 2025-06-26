package com.newgen.iforms.user.ao.model.customerfulfillment;

public class CustomerDocument {
	
	private LabourCard labourCard;
    private Passport passport;
    private Visa visa;
    
	public LabourCard getLabourCard() {
		return labourCard;
	}
	public void setLabourCard(LabourCard labourCard) {
		this.labourCard = labourCard;
	}
	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}
	public Visa getVisa() {
		return visa;
	}
	public void setVisa(Visa visa) {
		this.visa = visa;
	}

}
