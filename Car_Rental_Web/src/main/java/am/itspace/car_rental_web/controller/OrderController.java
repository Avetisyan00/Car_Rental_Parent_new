package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.CarService;
import am.itspace.car_rental_common.service.OrderService;
import am.itspace.car_rental_common.service.UserService;
import am.itspace.car_rental_web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/order")
public class OrderController {

    private final CarService carService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/add")
    public String orderAddPage(ModelMap modelMap, @RequestParam("id") int id) {
        Optional<Car> car = carService.findById(id);
        car.ifPresent(value -> modelMap.addAttribute("car", value));
        modelMap.addAttribute("drivers", userService.findAllByRole(Role.DRIVER));
        return "orderAdd";
    }

    @PostMapping("/add")
    public String orderAdd(
            @RequestParam("car") Car car,
            @RequestParam("driver") User driver,
            @RequestParam("dealer") User dealer,
            @RequestParam("client") int currentUser,
            @RequestParam("orderStart")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,
            @RequestParam("orderEnd")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end
    ) {
        log.info("/order/add has been called");
        double amount = car.getPricePerDay() + driver.getPricePerDay();
        orderService.save(car, driver, dealer, currentUser, start, end, amount);
        return "redirect:/order?id=" + car.getId();
    }

    @GetMapping("/my")
    public String showMyAllOrders(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {

        switch (currentUser.getUser().getRole()) {
            case CLIENT:
                modelMap.addAttribute("orders", orderService.findAllByClientId(currentUser.getUser().getId()));
                break;
            case DEALER:
                modelMap.addAttribute("orders", orderService.findAllByDealerId(currentUser.getUser().getId()));
                break;
            case DRIVER:
                modelMap.addAttribute("orders", orderService.findAllByDriverId(currentUser.getUser().getId()));
                break;
        }
        return "my-orders";
    }
}