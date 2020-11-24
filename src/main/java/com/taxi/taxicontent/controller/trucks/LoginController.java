package com.taxi.taxicontent.controller.trucks;

import com.taxi.taxicontent.model.trucks.TruckDriver;
import com.taxi.taxicontent.service.TruckDriverService;
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

    /*
    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        TruckDriver truckDriver = truckDriverService.findTruckDriverByEmail(auth.getName());
        modelAndView.addObject("email", "Welcome " + truckDriver.getEmail() + "/" + truckDriver.getFirstName() + " " + truckDriver.getSecondName() + " (" + truckDriver.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
    */
}