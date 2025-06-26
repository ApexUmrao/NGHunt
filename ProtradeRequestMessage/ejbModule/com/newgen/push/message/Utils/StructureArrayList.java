package com.newgen.push.message.Utils;

import java.util.Map;

public class StructureArrayList {
	String structureName;
	Map<String, String> attributeMap;
	
	public StructureArrayList(String structureName, Map<String, String> attributeMap){
		this.structureName = structureName;
		this.attributeMap = attributeMap;
	}
}
