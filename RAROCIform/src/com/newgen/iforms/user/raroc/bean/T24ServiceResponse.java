package com.newgen.iforms.user.raroc.bean;

import java.util.ArrayList;

public class T24ServiceResponse {
	
	public ExceptionDetails exceptionDetails;
    public ExceptionDetails getExceptionDetails() {
		return exceptionDetails;
	}
	public void setExceptionDetails(ExceptionDetails exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}
	
	public ArrayList<AaPaymentScheduleResponse> aaPaymentScheduleResponse;
	public ArrayList<AaPaymentScheduleResponse> getAaPaymentScheduleResponse() {
		return aaPaymentScheduleResponse;
	}
	public void setAaPaymentScheduleResponse(ArrayList<AaPaymentScheduleResponse> aaPaymentScheduleResponse) {
		this.aaPaymentScheduleResponse = aaPaymentScheduleResponse;
	}
	@Override
	public String toString() {
		return "T24ServiceResponse [exceptionDetails=" + exceptionDetails + ", aaPaymentScheduleResponse="
				+ aaPaymentScheduleResponse + "]";
	}
	
	
}


