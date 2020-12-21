package com.trucks.truckcontent.model.trucks;

import java.time.LocalDate;

public class OrderCountStatistics implements Comparable<OrderCountStatistics> {

    private LocalDate createDate;
    private long count;

    public OrderCountStatistics(LocalDate createDate, long count) {
        this.createDate = createDate;
        this.count = count;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public int compareTo(OrderCountStatistics o) {
        return this.getCreateDate().compareTo(o.getCreateDate());
    }
}
