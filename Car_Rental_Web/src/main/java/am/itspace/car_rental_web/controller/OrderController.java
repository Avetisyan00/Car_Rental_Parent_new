package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_web.service.CarService;
import am.itspace.car_rental_web.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final CarService carService;

    private final OrderService orderService;

    @GetMapping("/order")
    public String order(@RequestParam("id") int id,ModelMap modelMap){
        log.info("/order has been called");
        Optional<Car> car = carService.findById(id);
        car.ifPresent(value -> modelMap.addAttribute("car", value));
        return "about-us";
    }
    @GetMapping("/order/add")
    public String orderAddPage(){
        return "about-us";
    }
    @PostMapping("/order/add")
    public String orderAdd(@RequestParam("carId") int carId,
                           @ModelAttribute Order order){
        log.info("/order/add has been called");
        orderService.save(carId,order);
        return "redirect:/order?id=" + carId;
    }

}
