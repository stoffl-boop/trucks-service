package com.taxi.taxicontent.dao.trucks;

import com.taxi.taxicontent.model.trucks.Truck;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TruckRepository extends PagingAndSortingRepository<Truck, Integer> {

    Truck findByNumber(String number);

    List<Truck> findByTruckDriverIsNull();

    List<Truck> findByTruckDriverIsNotNull();

    long count();
}
