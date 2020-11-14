package com.taxi.taxicontent.dao.taxi;

import com.taxi.taxicontent.model.taxi.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Integer> {

    Driver findByEmail(String email);
    Driver findByAutoNumber(String autoNumber);
}
