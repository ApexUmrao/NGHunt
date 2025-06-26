package com.newgen.dscop.cache;

public class RequestAttributeDetails {
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
	public String getMappingVarName() {
		return mappingVarName;
	}
	public void setMappingVarName(String mappingVarName) {
		this.mappingVarName = mappingVarName;
	}
	public String getMappingColumnName() {
		return mappingColumnName;
	}
	public void setMappingColumnName(String mappingColumnName) {
		this.mappingColumnName = mappingColumnName;
	}
	private int attributeId;
	private String attributeName;
	private String attributeType;
	private String mappingVarName;
	private String mappingColumnName;
	
	/* 23092022 Remove Existing Data*/
	private String deleteTableEntry;
	private String complexTableName;
	
	public String getDeleteTableEntry() {
		return deleteTableEntry;
	}
	public void setDeleteTableEntry(String deleteTableEntry) {
		this.deleteTableEntry = deleteTableEntry;
	}
	public String getComplexTableName() {
		return complexTableName;
	}
	public void setComplexTableName(String complexTableName) {
		this.complexTableName = complexTableName;
	}
	
	
}
