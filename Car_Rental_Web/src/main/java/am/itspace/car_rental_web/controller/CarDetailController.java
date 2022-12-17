package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Image;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.service.CarDetailService;
import am.itspace.car_rental_common.service.CarService;
import am.itspace.car_rental_common.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CarDetailController {

    private final CarDetailService carDetailService;

    private final CarService carService;

    private final OrderService orderService;

    @GetMapping("/car-detail/add")
    public String carDetailAddPage() {
        return "car-detail";
    }

    /**
     * Upload Car images
     */
    @PostMapping("/car-detail/add")
    public String carDetailAdd(@RequestParam("carId") int carId,
                               @RequestParam("carImage") MultipartFile[] files) {
        log.info("/car-detail/add has been called");
        carDetailService.save(carId, files);
        return "redirect:/cars-detail?id=" + carId;
    }

    @GetMapping(value = "/cars-detail/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return carDetailService.getCarService(fileName);
    }

    /**
     * Get list of car images
     */
    @GetMapping("/cars-detail")
    public String carDetailPage(@RequestParam("id") int id,
                                ModelMap modelMap) throws EntityNotFoundException {
        log.info("/cars-detail has been called");
        Optional<Car> byId = carService.findById(id);
        byId.ifPresent(car -> modelMap.addAttribute("car", car));
        List<Image> all = carDetailService.findAllByCar(id);
        modelMap.addAttribute("images", all);
        LocalDateTime now = LocalDateTime.now();
        List<Order> allByCarId = orderService.findAllByCarId(id);
        for (Order order : allByCarId) {
            if ((now.isAfter(order.getOrderStart().atStartOfDay()) || now.equals(order.getOrderStart())) && (now.isBefore(order.getOrderEnd().atStartOfDay()))) {
                modelMap.addAttribute("error", "The car is busy");
            }
        }
        return "car-detail";
    }

    @GetMapping("/cars-detail/update")
    public String updateImagePg(@RequestParam("id") int id, ModelMap modelMap) {
        log.info("/cars-detail/update has been called");
        Optional<Image> byId = carDetailService.findById(id);
        if (byId.isPresent()) {
            modelMap.addAttribute("image", byId.get());
            return "editCarImage";
        }
        return "redirect:/cars";
    }
    /**
     * Update car image by id
     */
    @PostMapping("/cars-detail/update")
    public String updateImage(@RequestParam("carImage") MultipartFile file,
                              @ModelAttribute Image image) {
        carDetailService.updateCarImage(file, image);
        return "redirect:/cars-detail?id=" + image.getCar().getId();
    }
    /**
     * Delete car image by id
     */
    @GetMapping("/cars-detail/remove")
    public String deleteCarDetail(@RequestParam("id") int id) throws EntityNotFoundException {
        log.info("/cars-detail/remove has been called");
        carDetailService.delete(id);
        return "redirect:/cars";
    }
}