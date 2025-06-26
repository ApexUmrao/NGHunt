package com.newgen.cbg.response;

import com.newgen.cbg.request.BulkEnquiryApplicationAttributes;

public class BulkEnquiryResponse
{
	private String StatusCode;
	private String StatusMessage;
	private String SYSREFNO;
	private String TOTALCOUNT;
	private String FETCHCOUNT;
	private String ApplicationName;
	private BulkEnquiryApplicationAttributes[] ApplicationAttributes;
	
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	public String getStatusMessage() {
		return StatusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		StatusMessage = statusMessage;
	}
	public String getSYSREFNO() {
		return SYSREFNO;
	}
	public void setSYSREFNO(String sYSREFNO) {
		SYSREFNO = sYSREFNO;
	}
	public String getTOTALCOUNT() {
		return TOTALCOUNT;
	}
	public void setTOTALCOUNT(String tOTALCOUNT) {
		TOTALCOUNT = tOTALCOUNT;
	}
	public String getFETCHCOUNT() {
		return FETCHCOUNT;
	}
	public void setFETCHCOUNT(String fETCHCOUNT) {
		FETCHCOUNT = fETCHCOUNT;
	}
	public String getApplicationName() {
		return ApplicationName;
	}
	public void setApplicationName(String applicationName) {
		ApplicationName = applicationName;
	}
	public BulkEnquiryApplicationAttributes[] getApplicationAttributes() {
		return ApplicationAttributes;
	}
	public void setApplicationAttributes(
			BulkEnquiryApplicationAttributes[] applicationAttributes) {
		ApplicationAttributes = applicationAttributes;
	}
	
	
	
}
