package com.newgen.ao.laps.cache;

import java.util.ArrayList;

public class CallValidation {
	private String errorCode;
	private String errorDesc;
	private String isMandateValue;
	private String isLengthCheck;
	private String minLength;
	private String maxLength;
	private String stagingColName;
	private String executionSequence;
	private String isLOV;
	private String lovTable;
	private String lovQuery;
	private String isSpecValue;
	private String specValue;
	private String dateFormat;
	


	
	
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getIsSpecValue() {
		return isSpecValue;
	}
	public void setIsSpecValue(String isSpecValue) {
		this.isSpecValue = isSpecValue;
	}
	public String getSpecValue() {
		return specValue;
	}
	public void setSpecValue(String specValue) {
		this.specValue = specValue;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getIsMandateValue() {
		return isMandateValue;
	}
	public void setIsMandateValue(String isMandateValue) {
		this.isMandateValue = isMandateValue;
	}
	public String isLengthCheck() {
		return isLengthCheck;
	}
	public void setLengthCheck(String isLengthCheck) {
		this.isLengthCheck = isLengthCheck;
	}
	public String isMinLength() {
		return minLength;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getStagingColName() {
		return stagingColName;
	}
	public void setStagingColName(String stagingColName) {
		this.stagingColName = stagingColName;
	}
	public String getExecutionSequence() {
		return executionSequence;
	}
	public void setExecutionSequence(String executionSequence) {
		this.executionSequence = executionSequence;
	}
	public void setIsLOV(String isLOV) {
		this.isLOV = isLOV;		
	}
	
	public String getIsLOV() {
		return isLOV;
	}
	
	public void setLOVTable(String lovTable) {
		this.lovTable = lovTable;	
	}
	
	public String getLOVTable() {
		return lovTable;
	}
	
	public void setLOVQuery(String lovQuery) {
		this.lovQuery = lovQuery;	
	}
	
	public String getLOVQuery() {
		return lovQuery;
	}
	
	
		
}
