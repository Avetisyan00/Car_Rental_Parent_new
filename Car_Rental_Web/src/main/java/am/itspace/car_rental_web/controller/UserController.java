package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String userRegisterChoose() {
        return "registration";
    }

    //VERIFICATION OF USERS
    @GetMapping("/users/verify")
    public String verify(@RequestParam(name = "email") String email, @RequestParam(name = "token") String token) {
        userService.verifyUser(email, token);
        return "redirect:/";
    }
}
