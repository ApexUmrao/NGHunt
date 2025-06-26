package com.newgen.ao.laps.cache;

import java.util.List;
import java.util.Map;

public class ProtradeMapping {
	private String structureType;
	private Map<String, List<String>> attributeMap;
	public String getStructureType() {
		return structureType;
	}
	public void setStructureType(String structureType) {
		this.structureType = structureType;
	}
	public Map<String, List<String>> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<String, List<String>> attributeMap) {
		this.attributeMap = attributeMap;
	}

}
