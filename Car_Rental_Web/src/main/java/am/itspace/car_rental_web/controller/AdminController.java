package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    @GetMapping("")
    public String adminMainPage() {
        return "adminMainPage";
    }

    @GetMapping("/list/drivers")
    public String listOfDrivers(ModelMap modelMap) {
        modelMap.addAttribute("allDrivers", userService.findAllByRole(Role.DRIVER));
        return "driversListPage";
    }

    @GetMapping("/list/dealers")
    public String listOfDealers(ModelMap modelMap) {
        modelMap.addAttribute("allDealers", userService.findAllByRole(Role.DEALER));
        return "dealersListPage";
    }

    @GetMapping("/list/clients")
    public String listOfClients(ModelMap modelMap) {
        modelMap.addAttribute("allClients", userService.findAllByRole(Role.CLIENT));
        return "clientListPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable(name = "id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/user/change/{id}")
    public String changeUserById(@PathVariable(name = "id") int id, ModelMap modelMap) {
        Optional<User> byId = userService.findById(id);
        byId.ifPresent(user -> modelMap.addAttribute("user", user));
        return "changeUser";
    }

    @PostMapping("/user/change/{id}")
    public String changeUserById(@PathVariable(name = "id") int id, @ModelAttribute User user) {
        Optional<User> byId = userService.findById(id);
        user.setEnabled(true);
        byId.ifPresent(value -> user.setRole(value.getRole()));
        userService.saveChanges(user);
        return "redirect:/admin";
    }
}