package com.example.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BfhlResponse {
    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("official_email")
    private String officialEmail;

    private Object data;

    public BfhlResponse() {
    }

    public BfhlResponse(boolean isSuccess, String officialEmail, Object data) {
        this.isSuccess = isSuccess;
        this.officialEmail = officialEmail;
        this.data = data;
    }

    @JsonProperty("is_success")
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
