package com.taxi.taxicontent.type;

public enum DriverStatus {

    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private String status;

    DriverStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
