package com.newgen.cbg.model.ccfulfillment;

public class Addresses {
	public Residential residential;
    public Correspondence correspondence;
    public Home home;
	public Residential getResidential() {
		return residential;
	}
	public void setResidential(Residential residential) {
		this.residential = residential;
	}
	public Correspondence getCorrespondence() {
		return correspondence;
	}
	public void setCorrespondence(Correspondence correspondence) {
		this.correspondence = correspondence;
	}
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
}
