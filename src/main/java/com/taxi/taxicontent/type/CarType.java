package com.taxi.taxicontent.type;

public enum CarType {

    COMFORT("comfort"),
    BUSINESS("buisness"),
    ECONOM("econom");

    private String type;

    CarType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
