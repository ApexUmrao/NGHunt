package com.newgen.ao.laps.cache;

import java.util.ArrayList;

public class SwiftRuleEntity {
	 
	String RequestCategory= "";
	String Requesttype= "";
	String RuleID="";
	ArrayList<String> subRule;
	
	public String getRuleID() {
		return RuleID;
	}
	public void setRuleID(String ruleID) {
		RuleID = ruleID;
	}	
	public String getRequestCategory() {
		return RequestCategory;
	}
	public void setRequestCategory(String requestCategory) {
		RequestCategory = requestCategory;
	}
	public String getRequesttype() {
		return Requesttype;
	}
	public void setRequesttype(String requesttype) {
		Requesttype = requesttype;
	}
	public ArrayList<String> getSubRule() {
		return subRule;
	}
	public void setSubRule(ArrayList<String> subRule) {
		this.subRule = subRule;
	} 
}
