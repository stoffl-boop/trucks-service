package com.trucks.truckcontent.type;

public enum TruckOrderStatus {

    PENDING("pending"),
    REJECTED("rejected"),
    PROCESSING("processing"),
    COMPLETED("completed");

    private String status;

    TruckOrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
