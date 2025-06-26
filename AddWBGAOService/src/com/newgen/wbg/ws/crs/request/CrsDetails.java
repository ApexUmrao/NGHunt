package com.newgen.wbg.ws.crs.request;

public class CrsDetails {

	private CRSGeneric CrsGeneric;
	private DocumentsInfo DocumentsInfo;
	private EntityControlPersonInfo EntityControlPersonInfo;
	private TaxResidenceCountryInfo TaxResidenceCountryInfo;
	public CRSGeneric getCrsGeneric() {
		return CrsGeneric;
	}
	public void setCrsGeneric(CRSGeneric crsGeneric) {
		CrsGeneric = crsGeneric;
	}
	public DocumentsInfo getDocumentsInfo() {
		return DocumentsInfo;
	}
	public void setDocumentsInfo(DocumentsInfo documentsInfo) {
		DocumentsInfo = documentsInfo;
	}
	public EntityControlPersonInfo getEntityControlPersonInfo() {
		return EntityControlPersonInfo;
	}
	public void setEntityControlPersonInfo(
			EntityControlPersonInfo entityControlPersonInfo) {
		EntityControlPersonInfo = entityControlPersonInfo;
	}
	
	public TaxResidenceCountryInfo getTaxResidenceCountryInfo() {
		return TaxResidenceCountryInfo;
	}
	public void setTaxResidenceCountryInfo(
			TaxResidenceCountryInfo taxResidenceCountryInfo) {
		TaxResidenceCountryInfo = taxResidenceCountryInfo;
	}
}
