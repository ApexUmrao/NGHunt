package com.newgen.cbg.model.ccfulfillment;

public class SupplementaryCard {
	public String name;
    public String embossName;
    public String limitPercentage;
    public String relationship;
    public String dateOfBirth;
    public String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmbossName() {
		return embossName;
	}
	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}
	public String getLimitPercentage() {
		return limitPercentage;
	}
	public void setLimitPercentage(String limitPercentage) {
		this.limitPercentage = limitPercentage;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
