package com.trucks.truckcontent.controller.trucks;

import com.trucks.truckcontent.model.trucks.TruckDriver;
import com.trucks.truckcontent.service.TruckDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private TruckDriverService truckDriverService;

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        TruckDriver truckDriver = new TruckDriver();
        modelAndView.addObject("truckDriver", truckDriver);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration_driver", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid TruckDriver truckDriver, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        TruckDriver truckDriverExists = truckDriverService.findTruckDriverByEmail(truckDriver.getEmail());
        if (truckDriverExists != null) {
            bindingResult
                    .rejectValue("email", "error.truckDriver",
                            "Водій з поштою " + truckDriver.getEmail() + " вже зареєстрований в системі");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("trucks/create_truck_driver");
        } else {
            truckDriverService.saveUser(truckDriver);
            modelAndView.addObject("successMessage", "Водія було зареєстровано в системі: " + truckDriver.getFirstName() + " " + truckDriver.getSecondName());
            modelAndView.addObject("truckDriver", new TruckDriver());
            modelAndView.setViewName("trucks/create_truck_driver");
        }
        return modelAndView;
    }
}