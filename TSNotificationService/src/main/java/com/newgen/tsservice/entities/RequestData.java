package com.newgen.tsservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestData {
    @JsonProperty("LcNumber")
    private String lcNumber;

    @JsonProperty("ModifiedBy")
    private String modifiedBy;

    @JsonProperty("ModifiedDate")
    private String modifiedDate;

    @JsonProperty("TransactionID")
    private String transactionID;

    @JsonProperty("TransactionStatus")
    private String transactionStatus;

    @JsonProperty("TransactionType")
    private String transactionType;

    @JsonProperty("ClientReferenceID")
    private String clientReferenceID;

    // Getters and setters
    public String getLcNumber() {
        return lcNumber;
    }

    public void setLcNumber(String lcNumber) {
        this.lcNumber = lcNumber;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getClientReferenceID() {
        return clientReferenceID;
    }

    public void setClientReferenceID(String clientReferenceID) {
        this.clientReferenceID = clientReferenceID;
    }
}
