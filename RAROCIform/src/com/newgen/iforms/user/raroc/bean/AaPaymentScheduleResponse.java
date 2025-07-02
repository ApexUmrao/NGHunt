package com.newgen.iforms.user.raroc.bean;

import org.codehaus.jackson.annotate.JsonProperty;

public class AaPaymentScheduleResponse {
	
	@JsonProperty("PaymentDate") 
    public String paymentDate;
    @JsonProperty("TotalAmount") 
    public String totalAmount;
    @JsonProperty("Charge") 
    public String charge;
    @JsonProperty("Interest") 
    public String interest;
    @JsonProperty("Principal") 
    public String principal;
    @JsonProperty("Outstanding") 
    public String outstanding;
    @JsonProperty("ScheduleType") 
    public String scheduleType;
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(String outstanding) {
		this.outstanding = outstanding;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	@Override
	public String toString() {
		return "AaPaymentScheduleResponse [paymentDate=" + paymentDate + ", totalAmount=" + totalAmount + ", charge="
				+ charge + ", interest=" + interest + ", principal=" + principal + ", outstanding=" + outstanding
				+ ", scheduleType=" + scheduleType + "]";
	}
    
    

}
