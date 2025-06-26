package com.newgen.ws.request;

public class WmsHeader {
	
	private String SourceChannel;
	private String SourcingCentre;
	private String LeadRefNo;
	private String LeadSubDtTm;
	private String HomeBranch;
	private String IterationCount;
	private String Iteration;
	
	private String ReqType;
	private String QueueName;
	private String InitiatorName;
	private String AppRiskCls;
	private String LastProcessedByUserName;
	private String RMName;
	private String RMCode;
	
	private String RMEmail;
	private String RMMngName;
	private String RMMngCode;
	private String RMMngEmail;
	private String RMMobileNo;
	//CR2019
	private String InitiatorBy;
	private String InitiatedDept;
	private String SourcingDSACode;
	private String SourceOfLead;
	
	//added new tag by sharan 01/07/2020
	private String leadType;
	
	public String getSourcingDSACode() {
		return SourcingDSACode;
	}
	public void setSourcingDSACode(String sourcingDSACode) {
		SourcingDSACode = sourcingDSACode;
	}
	public String getSourceOfLead() {
		return SourceOfLead;
	}
	public void setSourceOfLead(String sourceOfLead) {
		SourceOfLead = sourceOfLead;
	}
	public String getInitiatorBy() {
		return InitiatorBy;
	}
	public void setInitiatorBy(String initiatorBy) {
		InitiatorBy = initiatorBy;
	}
	public String getInitiatedDept() {
		return InitiatedDept;
	}
	public void setInitiatedDept(String initiatedDept) {
		InitiatedDept = initiatedDept;
	}
	public String getSourceChannel() {
		return SourceChannel;
	}
	public void setSourceChannel(String sourceChannel) {
		SourceChannel = sourceChannel;
	}
	public String getSourcingCentre() {
		return SourcingCentre;
	}
	public void setSourcingCentre(String sourcingCentre) {
		SourcingCentre = sourcingCentre;
	}
	public String getLeadRefNo() {
		return LeadRefNo;
	}
	public void setLeadRefNo(String leadRefNo) {
		LeadRefNo = leadRefNo;
	}
	public String getLeadSubDtTm() {
		return LeadSubDtTm;
	}
	public void setLeadSubDtTm(String leadSubDtTm) {
		LeadSubDtTm = leadSubDtTm;
	}
	public String getHomeBranch() {
		return HomeBranch;
	}
	public void setHomeBranch(String homeBranch) {
		HomeBranch = homeBranch;
	}
	public String getIterationCount() {
		return IterationCount;
	}
	public void setIterationCount(String iterationCount) {
		IterationCount = iterationCount;
	}
	public String getIteration() {
		return Iteration;
	}
	public void setIteration(String iteration) {
		Iteration = iteration;
	}
	public String getReqType() {
		return ReqType;
	}
	public void setReqType(String reqType) {
		ReqType = reqType;
	}
	public String getQueueName() {
		return QueueName;
	}
	public void setQueueName(String queueName) {
		QueueName = queueName;
	}
	public String getInitiatorName() {
		return InitiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		InitiatorName = initiatorName;
	}
	public String getAppRiskCls() {
		return AppRiskCls;
	}
	public void setAppRiskCls(String appRiskCls) {
		AppRiskCls = appRiskCls;
	}	
	
	public String getRMName() {
		return RMName;
	}
	public void setRMName(String rMName) {
		RMName = rMName;
	}
	public String getRMCode() {
		return RMCode;
	}
	public void setRMCode(String rMCode) {
		RMCode = rMCode;
	}
	public String getRMEmail() {
		return RMEmail;
	}
	public void setRMEmail(String rMEmail) {
		RMEmail = rMEmail;
	}
	public String getRMMobileNo() {
		return RMMobileNo;
	}
	public void setRMMobileNo(String rMMobileNo) {
		RMMobileNo = rMMobileNo;
	}
	public String getRMMngName() {
		return RMMngName;
	}
	public void setRMMngName(String rMMngName) {
		RMMngName = rMMngName;
	}
	public String getRMMngCode() {
		return RMMngCode;
	}
	public void setRMMngCode(String rMMngCode) {
		RMMngCode = rMMngCode;
	}
	public String getRMMngEmail() {
		return RMMngEmail;
	}
	public void setRMMngEmail(String rMMngEmail) {
		RMMngEmail = rMMngEmail;
	}
	public String getLastProcessedByUserName() {
		return LastProcessedByUserName;
	}
	public void setLastProcessedByUserName(String lastProcessedByUserName) {
		LastProcessedByUserName = lastProcessedByUserName;
	}
	//added by sharan new tags 01/07/2020
	
	public String getleadType() {
		return leadType;
	}
	public void setleadType(String leadType) {
		this.leadType = leadType;
	}
}
