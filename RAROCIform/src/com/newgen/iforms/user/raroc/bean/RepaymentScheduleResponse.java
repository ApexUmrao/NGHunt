package com.newgen.iforms.user.raroc.bean;

import org.codehaus.jackson.annotate.JsonProperty;

public class RepaymentScheduleResponse {
	@JsonProperty("T24ServiceResponse")
	public T24ServiceResponse T24ServiceResponse;

	public T24ServiceResponse getT24ServiceResponse() {
		return T24ServiceResponse;
	}

	public void setT24ServiceResponse(T24ServiceResponse t24ServiceResponse) {
		this.T24ServiceResponse = t24ServiceResponse;
	}

	@Override
	public String toString() {
		return "RepaymentScheduleResponse [t24ServiceResponse=" + T24ServiceResponse + "]";
	}

}
