package com.newgen.tsservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseWrapper {

    @JsonProperty("Data")
    private ResponseData data;

    // Getter and Setter
    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }
}
