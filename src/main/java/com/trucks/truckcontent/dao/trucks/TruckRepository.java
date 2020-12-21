package com.trucks.truckcontent.dao.trucks;

import com.trucks.truckcontent.model.trucks.Truck;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TruckRepository extends PagingAndSortingRepository<Truck, Integer> {

    Truck findByNumber(String number);

    List<Truck> findByTruckDriverIsNull();

    List<Truck> findByTruckDriverIsNotNull();

    long count();
}
