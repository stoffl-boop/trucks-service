package com.taxi.taxicontent.dao.taxi;

import com.taxi.taxicontent.model.taxi.TaxiOrder;
import org.springframework.data.repository.CrudRepository;

public interface TaxiOrderRepository extends CrudRepository<TaxiOrder, Integer> {
}
