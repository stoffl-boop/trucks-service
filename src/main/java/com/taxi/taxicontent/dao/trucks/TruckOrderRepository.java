package com.taxi.taxicontent.dao.trucks;

import com.taxi.taxicontent.model.trucks.OrderCountStatistics;
import com.taxi.taxicontent.model.trucks.OrderLoadStatistics;
import com.taxi.taxicontent.model.trucks.OrderStatusStatistics;
import com.taxi.taxicontent.model.trucks.TruckDriver;
import com.taxi.taxicontent.model.trucks.TruckOrder;
import com.taxi.taxicontent.type.TruckOrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TruckOrderRepository extends CrudRepository<TruckOrder, Integer> {

    List<TruckOrder> findAllByTruckOrderStatus(TruckOrderStatus truckOrderStatus);

    List<TruckOrder> findAllByTruckDriver(TruckDriver truckDriver);

    List<TruckOrder> findAllByCreateTimeBetween(LocalDateTime createTimeFrom, LocalDateTime createTimeTo);

    long count();

    @Query("SELECT new com.taxi.taxicontent.model.trucks.OrderCountStatistics(v.createDate, COUNT(v)) FROM TruckOrder v GROUP BY v.createDate ORDER BY v.createDate")
    List<OrderCountStatistics> collectOrderCountStatisticsForLastMonth();

    @Query("SELECT new com.taxi.taxicontent.model.trucks.OrderLoadStatistics(v.createDate, SUM(v.completionTime)) FROM TruckOrder v GROUP BY v.createDate ORDER BY v.createDate")
    List<OrderLoadStatistics> collectLoadStatisticsForLastMonth();

    @Query("SELECT new com.taxi.taxicontent.model.trucks.OrderStatusStatistics(v.truckOrderStatus, COUNT(v)) FROM TruckOrder v WHERE v.truckOrderStatus IN (:statuses) GROUP BY v.truckOrderStatus")
    List<OrderStatusStatistics> collectTruckOrderStatusStatistics(List<TruckOrderStatus> statuses);

    @Query("SELECT AVG(v.completionTime) FROM TruckOrder v")
    int countAverageServiceTime();
}
