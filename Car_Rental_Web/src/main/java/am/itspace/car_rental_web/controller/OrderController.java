package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.exception.InvalidOrderDateException;
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
import org.springframework.web.bind.annotation.*;

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
            @ModelAttribute Order order,
            @RequestParam("client") int currentUser,
            @RequestParam(required = false,name = "error") String error,
            ModelMap modelMap
    ) throws InvalidOrderDateException {
        log.info("/order/add has been called");
        double amount = order.getCar().getPricePerDay() + order.getDriver().getPricePerDay();
        if (error != null && error.equals("true")){
            modelMap.addAttribute("error", "true");
            return "orderAdd";
        }
        orderService.save(order.getCar(), order.getDriver(), order.getDealer(), currentUser, order.getOrderStart(), order.getOrderEnd(), amount);
        return "redirect:/order?id=" + order.getCar().getId();
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