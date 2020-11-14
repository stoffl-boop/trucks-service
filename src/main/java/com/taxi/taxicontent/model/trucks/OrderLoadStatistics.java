package com.taxi.taxicontent.model.trucks;

import java.time.LocalDate;

public class OrderLoadStatistics implements Comparable<OrderLoadStatistics>{

    private LocalDate createDate;
    private long loadInMinutes;

    public OrderLoadStatistics(LocalDate createDate, long loadInMinutes) {
        this.createDate = createDate;
        this.loadInMinutes = loadInMinutes;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public long getLoadInMinutes() {
        return loadInMinutes;
    }

    public void setLoadInMinutes(long loadInMinutes) {
        this.loadInMinutes = loadInMinutes;
    }

    @Override
    public int compareTo(OrderLoadStatistics o) {
        return this.getCreateDate().compareTo(o.getCreateDate());
    }
}
