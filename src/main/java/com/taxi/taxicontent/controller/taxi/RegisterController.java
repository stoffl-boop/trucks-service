package com.taxi.taxicontent.controller.taxi;

import com.taxi.taxicontent.dao.taxi.DriverRepository;
import com.taxi.taxicontent.model.taxi.Driver;
import com.taxi.taxicontent.type.DriverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class RegisterController {

    @Autowired
    private DriverRepository driverRepository;

    @GetMapping("/register")
    public String register() {
        return "taxi/register";
    }

    @PostMapping(path = "/requestTaxistApproval")
    public @ResponseBody
    String registerTaxistRequestPost(@RequestParam String firstName,
                                     @RequestParam String secondName,
                                     @RequestParam MultipartFile image,
                                     @RequestParam String email,
                                     @RequestParam String phone,
                                     @RequestParam String autoName,
                                     @RequestParam String autoNumber) throws IOException {
        if (driverRepository.findByEmail(email) != null) {
            return "Водій з ел. поштою " + email + " вже подавав заявку на реєстрацію";
        }
        if (driverRepository.findByAutoNumber(autoNumber) != null) {
            return "Авто з номером " + autoNumber + " вже на розгляді адміністрації";
        }

        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("/src/main/java/resources/static/users/" + autoNumber + ".jpg")))) {
            byte[] bytes = image.getBytes();
            stream.write(bytes);
        }

        Driver driver = new Driver();
        driver.setFirstName(firstName);
        driver.setSecondName(secondName);
        driver.setPhone(phone);
        driver.setEmail(email);
        driver.setAuto(autoName);
        driver.setAutoNumber(autoNumber);
        driver.setStatus(DriverStatus.PENDING);
        driverRepository.save(driver);
        return "Дякуємо за подану заявку. Заявка буде розглянута протягом кількох днів. Очікуйте листа з підтвердженням на свою ел. пошту, вказану при реєстрації";
    }
}
