package com.newgen.wbg.ws.fatca.request;

public class Fatca {
private FatcaOwnerShipInfo FatcaOwnerShipInfo;
private FatcaDetails FatcaDetails;
public FatcaOwnerShipInfo getFatcaOwnerShipInfo() {
	return FatcaOwnerShipInfo;
}
public void setFatcaOwnerShipInfo(FatcaOwnerShipInfo fatcaOwnerShipInfo) {
	FatcaOwnerShipInfo = fatcaOwnerShipInfo;
}
public FatcaDetails getFatcaDetails() {
	return FatcaDetails;
}
public void setFatcaDetails(FatcaDetails fatcaDetails) {
	FatcaDetails = fatcaDetails;
}

}
