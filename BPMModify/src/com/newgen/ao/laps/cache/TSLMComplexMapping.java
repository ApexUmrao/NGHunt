package com.newgen.ao.laps.cache;

public class TSLMComplexMapping {
	private String processDefId;
	private String sourcingChannel;
	private String stagingTableName;
	private String complexVarName;
	private String complexTableName;
	private String deletePrevEntry;
	private String mappingAttr;
	
	public String getProcessDefId() {
		return processDefId;
	}
	
	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	
	public String getSourcingChannel() {
		return sourcingChannel;
	}
	
	public void setSourcingChannel(String sourcingChannel) {
		this.sourcingChannel = sourcingChannel;
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
