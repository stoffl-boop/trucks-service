package com.taxi.taxicontent.controller.trucks;

import com.taxi.taxicontent.dao.trucks.TruckClientRepository;
import com.taxi.taxicontent.dao.trucks.TruckDriverRepository;
import com.taxi.taxicontent.dao.trucks.TruckOrderRepository;
import com.taxi.taxicontent.model.trucks.TruckClient;
import com.taxi.taxicontent.model.trucks.TruckDriver;
import com.taxi.taxicontent.model.trucks.TruckOrder;
import com.taxi.taxicontent.type.TruckOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class TruckOrderController {

    @Autowired
    private TruckClientRepository truckClientRepository;

    @Autowired
    private TruckOrderRepository truckOrderRepository;

    @Autowired
    private TruckDriverRepository truckDriverRepository;

    @GetMapping("/createOrder")
    public String createOrder() {
        return "index";
    }

    @PostMapping(path = "/createOrder")
    public @ResponseBody
    String registerTaxistRequestPost(@RequestParam String phone,
                                     @RequestParam String fromPoint,
                                     @RequestParam String toPoint,
                                     @RequestParam float distance,
                                     @RequestParam float price,
                                     @RequestParam String additionalInfo) {
        TruckClient client = new TruckClient();
        client.setPhone(phone);
        truckClientRepository.save(client);

        TruckOrder truckOrder = new TruckOrder();
        truckOrder.setFromPoint(fromPoint);
        truckOrder.setToPoint(toPoint);
        truckOrder.setDistance(distance);
        truckOrder.setPrice(price);
        truckOrder.setCreateDate(LocalDate.now());
        truckOrder.setAdditionalInfo(additionalInfo);
        truckOrder.setTruckClient(client);
        truckOrder.setTruckOrderStatus(TruckOrderStatus.PENDING);
        truckOrderRepository.save(truckOrder);
        return "Дякуємо! Диспетчер зв'яжеться з Вами для уточнення деталей замовлення";
    }

    @PostMapping(path = "/assignDriverForOrder")
    public @ResponseBody
    String assignDriverForOrder(@RequestParam String orderId,
                                @RequestParam String driverId) {
        TruckOrder order = truckOrderRepository.findById(Integer.valueOf(orderId)).orElse(null);
        TruckDriver truckDriver = truckDriverRepository.findById(Integer.valueOf(driverId)).orElse(null);

        if (order != null && truckDriver != null) {
            order.setTruckDriver(truckDriver);
            order.setTruckOrderStatus(TruckOrderStatus.PROCESSING);
            truckOrderRepository.save(order);
            return "Замовлення №" + order.getId() + " призачено водію: " + truckDriver.getFirstName() + " " + truckDriver.getSecondName();
        }

        return "reject_driver_assign";
    }

    @PostMapping(path = "/rejectOrder")
    public @ResponseBody
    String rejectOrder(@RequestParam String id) {
        truckOrderRepository.findById(Integer.valueOf(id)).ifPresent(order -> {
            int durationMinutes = (int) Duration.between(order.getCreateTime(), LocalDateTime.now()).toMinutes();
            order.setCompletionTime(durationMinutes);
            order.setTruckOrderStatus(TruckOrderStatus.REJECTED);
            truckOrderRepository.save(order);
        });

        return "reject_order";
    }

    @PostMapping(path = "/completeOrder")
    public @ResponseBody
    String completeOrder(@RequestParam String id) {
        truckOrderRepository.findById(Integer.valueOf(id)).ifPresent(order -> {
            int durationMinutes = (int) Duration.between(order.getCreateTime(), LocalDateTime.now()).toMinutes();
            order.setCompletionTime(durationMinutes);
            order.setTruckOrderStatus(TruckOrderStatus.COMPLETED);
            truckOrderRepository.save(order);
        });

        return "complete_order";
    }
}
