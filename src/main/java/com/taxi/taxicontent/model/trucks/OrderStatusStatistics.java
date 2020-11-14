package com.taxi.taxicontent.model.trucks;

import com.taxi.taxicontent.type.TruckOrderStatus;

import java.time.LocalDate;

public class OrderStatusStatistics {

    private TruckOrderStatus orderStatus;
    private long count;

    public OrderStatusStatistics(TruckOrderStatus orderStatus, long count) {
        this.orderStatus = orderStatus;
        this.count = count;
    }

    public TruckOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(TruckOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
