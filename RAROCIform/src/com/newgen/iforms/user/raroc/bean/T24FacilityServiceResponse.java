package com.newgen.iforms.user.raroc.bean;

import java.util.ArrayList;

public class T24FacilityServiceResponse {
	
	public FacilityExceptionDetails facilityexceptionDetails;
    public ArrayList<FacilitiesByLimitIdResponse> facilitiesByLimitIdResponse;
	public FacilityExceptionDetails getFacilityexceptionDetails() {
		return facilityexceptionDetails;
	}
	public void setFacilityexceptionDetails(FacilityExceptionDetails facilityexceptionDetails) {
		this.facilityexceptionDetails = facilityexceptionDetails;
	}
	public ArrayList<FacilitiesByLimitIdResponse> getFacilitiesByLimitIdResponse() {
		return facilitiesByLimitIdResponse;
	}
	public void setFacilitiesByLimitIdResponse(ArrayList<FacilitiesByLimitIdResponse> facilitiesByLimitIdResponse) {
		this.facilitiesByLimitIdResponse = facilitiesByLimitIdResponse;
	}

}
