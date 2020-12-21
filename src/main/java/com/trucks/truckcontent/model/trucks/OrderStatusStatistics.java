package com.trucks.truckcontent.model.trucks;

import com.trucks.truckcontent.type.TruckOrderStatus;

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
