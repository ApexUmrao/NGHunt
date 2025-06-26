package com.newgen.tsservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseData {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Status")
    private String status;

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
