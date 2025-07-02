package com.newgen.raroc.entities;

import java.util.HashMap;

public class RarocTableDetails {
	@Override
	public String toString() {
		return "RarocTableDetails [tableName=" + tableName + ", colAttrMap=" + colAttrMap + "]";
	}
	private String tableName;
	private HashMap<String, RarocAttrDetails> colAttrMap;


	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public HashMap<String, RarocAttrDetails> getColAttrMap() {
		return colAttrMap;
	}
	public void setColAttrMap(HashMap<String, RarocAttrDetails> colAttrMap) {
		this.colAttrMap = colAttrMap;
	}


}
