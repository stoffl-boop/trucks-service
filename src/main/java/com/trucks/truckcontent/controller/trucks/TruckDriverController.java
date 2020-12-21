package com.trucks.truckcontent.controller.trucks;

import com.trucks.truckcontent.dao.trucks.TruckDriverRepository;
import com.trucks.truckcontent.dao.trucks.TruckOrderRepository;
import com.trucks.truckcontent.dao.trucks.TruckRepository;
import com.trucks.truckcontent.model.trucks.Truck;
import com.trucks.truckcontent.model.trucks.TruckDriver;
import com.trucks.truckcontent.model.trucks.TruckOrder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class TruckDriverController {

    @Autowired
    private TruckDriverRepository truckDriverRepository;

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private TruckOrderRepository truckOrderRepository;

    @RequestMapping(value="/createTruckDriver", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        TruckDriver truckDriver = new TruckDriver();
        modelAndView.addObject("truckDriver", truckDriver);
        modelAndView.setViewName("trucks/create_truck_driver");
        return modelAndView;
    }

    @PostMapping(path = "/createTruckDriver")
    public @ResponseBody
    String createTruckDriver(@RequestParam String firstName,
                                     @RequestParam String secondName,
                                     @RequestParam MultipartFile image,
                                     @RequestParam String phone) throws IOException {
        if (truckDriverRepository.findByPhone(phone) != null) {
            return "Водій з номером " + phone + " вже зареєстрований в системі";
        }

        TruckDriver truckDriver = new TruckDriver();
        truckDriver.setFirstName(firstName);
        truckDriver.setSecondName(secondName);
        truckDriver.setPhone(phone);
        truckDriver.setImage(Base64.encodeBase64String(image.getBytes()));
        truckDriverRepository.save(truckDriver);
        return "Водія було зареєстровано в системі: " + truckDriver.getFirstName() + " " + truckDriver.getSecondName();
    }

    @PostMapping(path = "/assign_auto")
    public @ResponseBody
    String assignAuto(@RequestParam String driverId,
                        @RequestParam String truckId) {
        TruckDriver driver = truckDriverRepository.findById(Integer.valueOf(driverId)).orElse(null);
        Truck truck = truckRepository.findById(Integer.valueOf(truckId)).orElse(null);

        if (driver != null && truck != null) {
            Truck oldTruck = driver.getTruck();
            if (oldTruck != null) {
                oldTruck.setTruckDriver(null);
                truckRepository.save(oldTruck);
            }

            truck.setTruckDriver(driver);
            truckRepository.save(truck);
            return truck.getModel() + " " + truck.getNumber() + " призачено водію: " + driver.getFirstName() + " " + driver.getSecondName();
        }

        return "reject_auto";
    }

    @PostMapping(path = "/deleteTruckDriver")
    public @ResponseBody
    String deleteTruckDriver(@RequestParam String id) {
        TruckDriver driver = truckDriverRepository.findById(Integer.valueOf(id)).orElse(null);
        if (driver != null && driver.getTruck() != null) {
            truckRepository.findById(driver.getTruck().getId()).ifPresent(truck -> {
                truck.setTruckDriver(null);
                truckRepository.save(truck);
            });

            List<TruckOrder> driversOrders = truckOrderRepository.findAllByTruckDriver(driver);
            driversOrders.forEach(order -> order.setTruckDriver(null));
            truckOrderRepository.saveAll(driversOrders);
        }

        truckDriverRepository.delete(driver);
        return "deleteTruckDriver";
    }
}
