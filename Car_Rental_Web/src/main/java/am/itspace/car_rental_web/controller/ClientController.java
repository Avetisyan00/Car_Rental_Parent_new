package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final UserService userService;


    //CLIENTS REGISTRATION
    @GetMapping("/registration/client")
    public String userRegistrationPage() {
        return "clientRegistration";
    }

    @PostMapping("/registration/client")
    public String userRegistration(@ModelAttribute User user, @RequestParam("userImage") MultipartFile file) {
        userService.saveUserAsClient(user, file);
        return "redirect:/";
    }
}
