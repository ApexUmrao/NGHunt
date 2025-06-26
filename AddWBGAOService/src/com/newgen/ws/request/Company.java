package com.newgen.ws.request;

import com.newgen.wbg.ws.crs.request.CrsDetails;
import com.newgen.wbg.ws.fatca.request.Fatca;
import com.newgen.wbg.ws.kyc.request.KYC;
import com.newgen.ws.request.CompanyInformation;

public class Company {
	
	private CompanyInformation CompanyInformation;
	private CrsDetails CrsDetails;
	private KYC KYC;
	private Fatca Fatca;
	private DocumentsDtls DocumentsDtls;	
	
	public KYC getKYC() {
		return KYC;
	}
	public void setKYC(KYC kYC) {
		KYC = kYC;
	}
	public Fatca getFatca() {
		return Fatca;
	}
	public void setFatca(Fatca fatca) {
		Fatca = fatca;
	}
	public CompanyInformation getCompanyInformation() {
		return CompanyInformation;
	}
	public void setCompanyInformation(CompanyInformation companyInformation) {
		CompanyInformation = companyInformation;
	}
	public DocumentsDtls getDocumentsDtls() {
		return DocumentsDtls;
	}
	public void setDocumentsDtls(DocumentsDtls documentsDtls) {
		DocumentsDtls = documentsDtls;
	}
	public CrsDetails getCrsDetails() {
		return CrsDetails;
	}
	public void setCrsDetails(CrsDetails crsDetails) {
		CrsDetails = crsDetails;
	}	
	
}