package com.newgen.ao.laps.cache;

import java.util.ArrayList;

public class CallEntity {
	private String stage;
	private int callID;
	private String callName;
	private boolean isMandatory;
	private boolean isIgnorable;
	private String productType;
	private String executionType;
	private int executionSequence;
	private ArrayList<Integer> dependencyCallID;
	
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public boolean isIgnorable() {
		return isIgnorable;
	}
	public void setIgnorable(boolean isIgnorable) {
		this.isIgnorable = isIgnorable;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public int getCallID() {
		return callID;
	}
	public void setCallID(int callID) {
		this.callID = callID;
	}
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getExecutionType() {
		return executionType;
	}
	public void setExecutionType(String executionType) {
		this.executionType = executionType;
	}
	public int getExecutionSequence() {
		return executionSequence;
	}
	public void setExecutionSequence(int executionSequence) {
		this.executionSequence = executionSequence;
	}
	public ArrayList<Integer> getDependencyCallID() {
		return dependencyCallID;
	}
	public void setDependencyCallID(ArrayList<Integer> dependencyCallID) {
		this.dependencyCallID = dependencyCallID;
	}
}
