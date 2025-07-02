package com.newgen.raroc.util;

public class RequestTableKeyDetails {

	private int attributeId;
	private String attributeName;
	private String attributeType;
	private String fetchColumnName;
	private String fetchTableName;
	public int getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getFetchColumnName() {
		return fetchColumnName;
	}
	public void setFetchColumnName(String fetchColumnName) {
		this.fetchColumnName = fetchColumnName;
	}
	public String getFetchTableName() {
		return fetchTableName;
	}
	public void setFetchTableName(String fetchTableName) {
		this.fetchTableName = fetchTableName;
	}
}
