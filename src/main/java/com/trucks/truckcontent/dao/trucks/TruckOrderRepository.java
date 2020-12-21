package com.trucks.truckcontent.dao.trucks;

import com.trucks.truckcontent.model.trucks.OrderCountStatistics;
import com.trucks.truckcontent.model.trucks.OrderLoadStatistics;
import com.trucks.truckcontent.model.trucks.OrderStatusStatistics;
import com.trucks.truckcontent.model.trucks.TruckDriver;
import com.trucks.truckcontent.model.trucks.TruckOrder;
import com.trucks.truckcontent.type.TruckOrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TruckOrderRepository extends CrudRepository<TruckOrder, Integer> {

    List<TruckOrder> findAllByTruckOrderStatus(TruckOrderStatus truckOrderStatus);

    List<TruckOrder> findAllByTruckDriver(TruckDriver truckDriver);

    List<TruckOrder> findAllByCreateTimeBetween(LocalDateTime createTimeFrom, LocalDateTime createTimeTo);

    long count();

    @Query("SELECT new com.trucks.truckcontent.model.trucks.OrderCountStatistics(v.createDate, COUNT(v)) FROM TruckOrder v GROUP BY v.createDate ORDER BY v.createDate")
    List<OrderCountStatistics> collectOrderCountStatisticsForLastMonth();

    @Query("SELECT new com.trucks.truckcontent.model.trucks.OrderLoadStatistics(v.createDate, SUM(v.completionTime)) FROM TruckOrder v GROUP BY v.createDate ORDER BY v.createDate")
    List<OrderLoadStatistics> collectLoadStatisticsForLastMonth();

    @Query("SELECT new com.trucks.truckcontent.model.trucks.OrderStatusStatistics(v.truckOrderStatus, COUNT(v)) FROM TruckOrder v WHERE v.truckOrderStatus IN (:statuses) GROUP BY v.truckOrderStatus")
    List<OrderStatusStatistics> collectTruckOrderStatusStatistics(List<TruckOrderStatus> statuses);

    @Query("SELECT AVG(v.completionTime) FROM TruckOrder v")
    int countAverageServiceTime();
}
