package com.taxi.taxicontent.model.trucks;

import com.taxi.taxicontent.model.trucks.converter.TruckOrderStatusConverter;
import com.taxi.taxicontent.type.TruckOrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "truck_orders")
public class TruckOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fromPoint;
    private String toPoint;
    private float distance;
    private float price;
    private String additionalInfo;
    private int completionTime;

    @CreationTimestamp
    private LocalDateTime createTime;

    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private TruckDriver truckDriver;

    @ManyToOne(fetch = FetchType.LAZY)
    private TruckClient truckClient;

    @Convert(converter = TruckOrderStatusConverter.class)
    private TruckOrderStatus truckOrderStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(String fromPoint) {
        this.fromPoint = fromPoint;
    }

    public String getToPoint() {
        return toPoint;
    }

    public void setToPoint(String toPoint) {
        this.toPoint = toPoint;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public TruckClient getTruckClient() {
        return truckClient;
    }

    public void setTruckClient(TruckClient truckClient) {
        this.truckClient = truckClient;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public TruckDriver getTruckDriver() {
        return truckDriver;
    }

    public void setTruckDriver(TruckDriver truckDriver) {
        this.truckDriver = truckDriver;
    }

    public TruckOrderStatus getTruckOrderStatus() {
        return truckOrderStatus;
    }

    public void setTruckOrderStatus(TruckOrderStatus truckOrderStatus) {
        this.truckOrderStatus = truckOrderStatus;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TruckOrder)) {
            return false;
        }
        return id != null && id.equals(((TruckOrder) obj).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
