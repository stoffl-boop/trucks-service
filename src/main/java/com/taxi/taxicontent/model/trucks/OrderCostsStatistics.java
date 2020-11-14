package com.taxi.taxicontent.model.trucks;

import java.math.BigDecimal;

public class OrderCostsStatistics {

    private int channels;
    private BigDecimal possibilityOfEmptyQueue;
    private BigDecimal avgClientsInQueue;
    private BigDecimal relativeCosts;

    public OrderCostsStatistics(int channels, BigDecimal possibilityOfEmptyQueue, BigDecimal avgClientsInQueue, BigDecimal relativeCosts) {
        this.channels = channels;
        this.possibilityOfEmptyQueue = possibilityOfEmptyQueue;
        this.avgClientsInQueue = avgClientsInQueue;
        this.relativeCosts = relativeCosts;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public BigDecimal getPossibilityOfEmptyQueue() {
        return possibilityOfEmptyQueue;
    }

    public void setPossibilityOfEmptyQueue(BigDecimal possibilityOfEmptyQueue) {
        this.possibilityOfEmptyQueue = possibilityOfEmptyQueue;
    }

    public BigDecimal getAvgClientsInQueue() {
        return avgClientsInQueue;
    }

    public void setAvgClientsInQueue(BigDecimal avgClientsInQueue) {
        this.avgClientsInQueue = avgClientsInQueue;
    }

    public BigDecimal getRelativeCosts() {
        return relativeCosts;
    }

    public void setRelativeCosts(BigDecimal relativeCosts) {
        this.relativeCosts = relativeCosts;
    }
}
