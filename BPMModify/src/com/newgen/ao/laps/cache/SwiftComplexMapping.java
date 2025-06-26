package com.newgen.ao.laps.cache;

public class SwiftComplexMapping {
	private String stagingTableName;
	private String complexVarName;
	private String complexTableName;
	private String deletePrevEntry;
	private String mappingAttr;
	private String structureType;

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}

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
