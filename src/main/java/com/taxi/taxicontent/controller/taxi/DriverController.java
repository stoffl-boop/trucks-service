package com.taxi.taxicontent.controller.taxi;

import com.taxi.taxicontent.dao.taxi.DriverRepository;
import com.taxi.taxicontent.type.CarType;
import com.taxi.taxicontent.type.DriverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @PostMapping(path = "/reject_driver")
    public @ResponseBody
    String rejectDriver(@RequestParam String driverId) {
        driverRepository.findById(Integer.valueOf(driverId)).ifPresent(driver -> {
            driver.setStatus(DriverStatus.REJECTED);
            driverRepository.save(driver);
        });

        return "reject_driver";
    }

    @PostMapping(path = "/accept_driver")
    public @ResponseBody
    String acceptDriver(@RequestParam String driverId) {
        driverRepository.findById(Integer.valueOf(driverId)).ifPresent(driver -> {
            driver.setStatus(DriverStatus.APPROVED);
            driverRepository.save(driver);
        });

        return "accept_driver";
    }

    @PostMapping(path = "/set_car_type")
    public @ResponseBody
    String setCarType(@RequestParam String driverId, @RequestParam String carType) {
        driverRepository.findById(Integer.valueOf(driverId)).ifPresent(driver -> {
            driver.setCarType(CarType.valueOf(carType));
            driverRepository.save(driver);
        });

        return "set_car_type";
    }
}
