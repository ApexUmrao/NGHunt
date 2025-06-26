package com.newgen.wbg.ws.kyc.request;

public class AffiliatedEntityInfo {
	private String Name;
	private String IncorpPlace;
	private String Address;
	private String CleitnRelation;
	private String LegalCountry;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getIncorpPlace() {
		return IncorpPlace;
	}
	public void setIncorpPlace(String incorpPlace) {
		IncorpPlace = incorpPlace;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCleitnRelation() {
		return CleitnRelation;
	}
	public void setCleitnRelation(String cleitnRelation) {
		CleitnRelation = cleitnRelation;
	}
	public String getLegalCountry() {
		return LegalCountry;
	}
	public void setLegalCountry(String legalCountry) {
		LegalCountry = legalCountry;
	}
}
