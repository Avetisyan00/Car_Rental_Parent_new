package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class DriverController {

    private final UserService userService;

    //DRIVERS REGISTRATION
    @GetMapping("/registration/driver")
    public String driverRegistrationPage() {
        return "driverRegistration";
    }

    @PostMapping("/registration/driver")
    public String driverRegistrationPage(@ModelAttribute User user, @RequestParam("userImage") MultipartFile file) {
        userService.saveUserAsDriver(user, file);
        return "redirect:/driver";
    }
}
