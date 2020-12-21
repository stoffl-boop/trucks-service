package com.trucks.truckcontent.model.trucks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="trucks")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String model;
    private float length;
    private float height;
    private float volume;
    private float capacity;
    private String number;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private TruckDriver truckDriver;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public TruckDriver getTruckDriver() {
        return truckDriver;
    }

    public void setTruckDriver(TruckDriver driver) {
        this.truckDriver = driver;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
