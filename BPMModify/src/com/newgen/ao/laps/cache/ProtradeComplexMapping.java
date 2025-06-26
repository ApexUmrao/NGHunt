package com.newgen.ao.laps.cache;

public class ProtradeComplexMapping {
	private String stagingTableName;
	private String complexVarName;
	private String complexTableName;
	private String deletePrevEntry;
	private String mappingAttr;

	public String getStagingTableName() {
		return stagingTableName;
	}
	
	public void setStagingTableName(String stagingTableName) {
		this.stagingTableName = stagingTableName;
	}
	
	public String getComplexVarName() {
		return complexVarName;
	}
	
	public void setComplexVarName(String complexVarName) {
		this.complexVarName = complexVarName;
	}
	
	public String getComplexTableName() {
		return complexTableName;
	}
	
	public void setComplexTableName(String complexTableName) {
		this.complexTableName = complexTableName;
	}
	
	public String getDeletePreviousEntry() {
		return deletePrevEntry;
	}
	
	public void setDeletePreviousEntry(String deletePrevEntry) {
		this.deletePrevEntry = deletePrevEntry;
	}
	
	public String getMappingAttribute() {
		return mappingAttr;
	}
	
	public void setMappingAttribute(String mappingAttr) {
		this.mappingAttr = mappingAttr;
	}
}
