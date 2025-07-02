package com.newgen.raroc.entities;

public class RarocAttrDetails {

	@Override
	public String toString() {
		return "RarocAttrDetails [isDate=" + isDate + ", isClob=" + isClob + ", isContainsAmp=" + isContainsAmp
				+ ", attributeName=" + attributeName + "]";
	}
	private boolean isDate;
	private boolean isClob;
	private boolean isContainsAmp;
	private String attributeName;
	private String dataType;


	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public boolean isDate() {
		return isDate;
	}
	public void setDate(boolean isDate) {
		this.isDate = isDate;
	}
	public boolean isClob() {
		return isClob;
	}
	public void setClob(boolean isClob) {
		this.isClob = isClob;
	}
	public boolean isContainsAmp() {
		return isContainsAmp;
	}
	public void setContainsAmp(boolean isContainsAmp) {
		this.isContainsAmp = isContainsAmp;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataTypeName(String dataType) {
		this.dataType = dataType;
	}


}
