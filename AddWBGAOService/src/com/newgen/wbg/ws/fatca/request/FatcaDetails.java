package com.newgen.wbg.ws.fatca.request;

public class FatcaDetails {
	private String customerClsfctn;
	private String natureOfEntity;
	private String typeOfEntity;
	private String FATCAStatus;
	private String documentCollected;
	private String dateOfDocument;
	private String idntfctnNumRequired;
	private String idntfctnNumber;
	private String customerClsfctnDate;
	private String customerFATCAClsfctnDate;
	private String FatcaUpdateFlag;
	public String getCustomerClsfctn() {
		return customerClsfctn;
	}
	public void setCustomerClsfctn(String customerClsfctn) {
		this.customerClsfctn = customerClsfctn;
	}
	public String getNatureOfEntity() {
		return natureOfEntity;
	}
	public void setNatureOfEntity(String natureOfEntity) {
		this.natureOfEntity = natureOfEntity;
	}
	public String getTypeOfEntity() {
		return typeOfEntity;
	}
	public void setTypeOfEntity(String typeOfEntity) {
		this.typeOfEntity = typeOfEntity;
	}
	public String getFATCAStatus() {
		return FATCAStatus;
	}
	public void setFATCAStatus(String fATCAStatus) {
		FATCAStatus = fATCAStatus;
	}
	public String getDocumentCollected() {
		return documentCollected;
	}
	public void setDocumentCollected(String documentCollected) {
		this.documentCollected = documentCollected;
	}
	public String getDateOfDocument() {
		return dateOfDocument;
	}
	public void setDateOfDocument(String dateOfDocument) {
		this.dateOfDocument = dateOfDocument;
	}
	public String getIdntfctnNumRequired() {
		return idntfctnNumRequired;
	}
	public void setIdntfctnNumRequired(String idntfctnNumRequired) {
		this.idntfctnNumRequired = idntfctnNumRequired;
	}
	public String getIdntfctnNumber() {
		return idntfctnNumber;
	}
	public void setIdntfctnNumber(String idntfctnNumber) {
		this.idntfctnNumber = idntfctnNumber;
	}
	public String getCustomerClsfctnDate() {
		return customerClsfctnDate;
	}
	public void setCustomerClsfctnDate(String customerClsfctnDate) {
		this.customerClsfctnDate = customerClsfctnDate;
	}
	public String getCustomerFATCAClsfctnDate() {
		return customerFATCAClsfctnDate;
	}
	public void setCustomerFATCAClsfctnDate(String customerFATCAClsfctnDate) {
		this.customerFATCAClsfctnDate = customerFATCAClsfctnDate;
	}
	public String getFatcaUpdateFlag() {
		return FatcaUpdateFlag;
	}
	public void setFatcaUpdateFlag(String FatcaUpdateFlag) {
		this.FatcaUpdateFlag = FatcaUpdateFlag;
	}
}
