package com.newgen.raroc.entities;

import java.util.HashMap;

public class RarocRequestAttrDetails {

	@Override
	public String toString() {
		return "RarocRequestAttrDetails [radMap=" + radMap + "]";
	}
	private HashMap<String,RarocTableDetails> radMap;

	public HashMap<String, RarocTableDetails> getRadMap() {
		return radMap;
	}
	public void setRadMap(HashMap<String, RarocTableDetails> radMap) {
		this.radMap = radMap;
	}


}
