package com.yorku.parking.model;

public class RegistrationRequest implements IValidatable {
    private String requestId;
    private String status;

    public RegistrationRequest(String requestId) {
        this.requestId = requestId;
        this.status = "Pending";
    }

    @Override
    public boolean validate() {
        return requestId != null && !requestId.isEmpty();
    }

    public void setStatus(String status) { this.status = status; }
}
