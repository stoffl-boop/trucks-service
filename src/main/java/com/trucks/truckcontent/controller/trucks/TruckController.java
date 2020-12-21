package com.trucks.truckcontent.controller.trucks;

import com.trucks.truckcontent.dao.trucks.TruckDriverRepository;
import com.trucks.truckcontent.dao.trucks.TruckRepository;
import com.trucks.truckcontent.model.trucks.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TruckController {

    @Autowired
    private TruckRepository truckRepository;

    @Autowired
    private TruckDriverRepository truckDriverRepository;

    @GetMapping("/createTruck")
    public String createTruck() {
        return "trucks/create_truck";
    }

    @PostMapping(path = "/createTruck")
    public @ResponseBody
    String createTruck(@RequestParam String model,
                                     @RequestParam float length,
                                     @RequestParam float height,
                                     @RequestParam float volume,
                                     @RequestParam float capacity,
                                     @RequestParam String number) {
        if (truckRepository.findByNumber(number) != null) {
            return "Авто з номером " + number + " вже зареєстровано в системі";
        }

        Truck truck = new Truck();
        truck.setModel(model);
        truck.setLength(length);
        truck.setHeight(height);
        truck.setVolume(volume);
        truck.setCapacity(capacity);
        truck.setNumber(number);
        truckRepository.save(truck);
        return "Авто було зареєстровано в системі: " + truck.getModel() + ", Номер: " + truck.getNumber();
    }

    @PostMapping(path = "/deleteTruck")
    public @ResponseBody
    String deleteTruckDriver(@RequestParam String id) {
        Truck truck = truckRepository.findById(Integer.valueOf(id)).orElse(null);
        if (truck != null && truck.getTruckDriver() != null) {
            truckDriverRepository.findById(truck.getTruckDriver().getId()).ifPresent(driver -> {
                driver.setTruck(null);
                truckDriverRepository.save(driver);
            });

        }

        truckRepository.delete(truck);
        return "deleteTruck";
    }
}
