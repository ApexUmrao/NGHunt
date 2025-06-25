package com.newgen.ao.laps.cache;

import java.util.HashMap;

public class SanctionScreeningMapping {
	private String type;
	private String table;
	private String entityType;
	private HashMap<String, String> attributeMap;
	private HashMap<String, String> attributeModifyMap;
	
	
	

	public HashMap<String, String> getAttributeModifyMap() {
		return attributeModifyMap;
	}

	public void setAttributeModifyMap(HashMap<String, String> attributeModifyMap) {
		this.attributeModifyMap = attributeModifyMap;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTable() {
		return table;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public HashMap<String, String> getAttributeMap() {
		return attributeMap;
	}
	
	public void setAttributeMap(HashMap<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}
	
}
