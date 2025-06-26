package com.newgen.tsservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestWrapper {
    @JsonProperty("Data")
    private RequestData data;

    // Getter and setter
    public RequestData getData() {
        return data;
    }

    public void setData(RequestData data) {
        this.data = data;
    }
}
