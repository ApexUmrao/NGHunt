package com.newgen.wbg.ws.fatca.request;

public class FatcaOwnerShipDtls {
	private String OwnerNumber;
	private String OwnerName;
	private String OwnershipSharePercentage;
	private String OwnershipAddress;
	private String OwnerTINorSSN;
	private String OwnerW9Availability;
	public String getOwnerNumber() {
		return OwnerNumber;
	}
	public void setOwnerNumber(String ownerNumber) {
		OwnerNumber = ownerNumber;
	}
	public String getOwnerName() {
		return OwnerName;
	}
	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}
	public String getOwnershipSharePercentage() {
		return OwnershipSharePercentage;
	}
	public void setOwnershipSharePercentage(String ownershipSharePercentage) {
		OwnershipSharePercentage = ownershipSharePercentage;
	}
	public String getOwnershipAddress() {
		return OwnershipAddress;
	}
	public void setOwnershipAddress(String ownershipAddress) {
		OwnershipAddress = ownershipAddress;
	}
	public String getOwnerTINorSSN() {
		return OwnerTINorSSN;
	}
	public void setOwnerTINorSSN(String ownerTINorSSN) {
		OwnerTINorSSN = ownerTINorSSN;
	}
	public String getOwnerW9Availability() {
		return OwnerW9Availability;
	}
	public void setOwnerW9Availability(String ownerW9Availability) {
		OwnerW9Availability = ownerW9Availability;
	}
}
