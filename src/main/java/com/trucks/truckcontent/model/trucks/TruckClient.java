package com.trucks.truckcontent.model.trucks;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "truck_clients")
public class TruckClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String phone;

    @OneToMany(
            mappedBy = "truckClient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TruckOrder> truckOrders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<TruckOrder> getTruckOrders() {
        return truckOrders;
    }

    public void setTruckOrders(List<TruckOrder> truckOrders) {
        this.truckOrders = truckOrders;
    }
}
