package com.newgen.ws.request;

public class Documents 
{
	private String DocumentIndex;
	private String DocType;
	private String DocName;
	private String DocumentFor;
	private String UploadDt;
	
	public String getDocumentIndex() {
		return DocumentIndex;
	}
	public void setDocumentIndex(String documentIndex) {
		DocumentIndex = documentIndex;
	}
	public String getDocType() {
		return DocType;
	}
	public void setDocType(String docType) {
		DocType = docType;
	}
	public String getDocName() {
		return DocName;
	}
	public void setDocName(String docName) {
		DocName = docName;
	}
	public String getDocumentFor() {
		return DocumentFor;
	}
	public void setDocumentFor(String documentFor) {
		DocumentFor = documentFor;
	}
	public String getUploadDt() {
		return UploadDt;
	}
	public void setUploadDt(String uploadDt) {
		UploadDt = uploadDt;
	}
	
}
