package com.newgen.tsservice.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

	@JsonProperty("Code")
    private String code;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Errors")
    private List<ErrorDetail> errors;

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public static class ErrorDetail {
    	@JsonProperty("ErrorCode")
        private String errorCode;
        @JsonProperty("Message")
        private String message;
        @JsonProperty("Path")
        private String path;
        @JsonProperty("Url")
        private String url;

        // Constructor
        public ErrorDetail(String errorCode, String message, String path, String url) {
            this.errorCode = errorCode;
            this.message = message;
            this.path = path;
            this.url = url;
        }
        // Getters and Setters
        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
