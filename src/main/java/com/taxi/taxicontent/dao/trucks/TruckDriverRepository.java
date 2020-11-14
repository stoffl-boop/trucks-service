package com.taxi.taxicontent.dao.trucks;

import com.taxi.taxicontent.model.trucks.TruckDriver;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TruckDriverRepository extends CrudRepository<TruckDriver, Integer> {

    TruckDriver findByPhone(String phone);

    List<TruckDriver> findByTruckIsNull();

    long count();
}
