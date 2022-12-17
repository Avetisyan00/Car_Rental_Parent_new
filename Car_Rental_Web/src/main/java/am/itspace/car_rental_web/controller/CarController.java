package am.itspace.car_rental_web.controller;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Category;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_web.customRepo.CustomCarRepo;
import am.itspace.car_rental_web.security.CurrentUser;
import am.itspace.car_rental_common.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CarController {

    private final CarService carService;
    private final CustomCarRepo customCarRepo;

    /**
     * Get list of cars using  QueryDSL
     */
    @GetMapping("/cars")
    public String cars(@RequestParam(required = false, name = "name") String name,
                       @RequestParam(required = false, name = "model") String model,
                       @RequestParam(required = false, name = "productionYear") Integer productionYear,
                       ModelMap modelMap) {
        log.info("/cars has been called");
        List<Car> cars = customCarRepo.cars(name, model, productionYear);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("name", name);
        modelMap.addAttribute("model", model);
        return "cars";
    }

    /**
     * Get list of cars by dealer
     */
    @GetMapping("/cars/getByDealer")
    public String getByDealer(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam("dealerId") int dealerId) {
        log.info("/cars/getByDealer has been called by{}", currentUser.getUser().getName());
        if (currentUser.getUser().getId() == dealerId) {
            modelMap.addAttribute("cars", carService.getByDealer(dealerId));
            return "cars";
        }
        return "redirect:/cars";
    }

    /**
     * Get list of cars by category
     */
    @GetMapping("/cars/getByCategory")
    public String getCarsByCategory(ModelMap modelMap, @RequestParam("category") Category category) {
        log.info("/cars/category has been called");
        List<Car> cars = carService.findAllByCategory(category);
        modelMap.addAttribute("cars", cars);
        return "cars";
    }
    /**
     * Add car and upload car image
     */
    @GetMapping("/cars/add")
    public String carsAddPage() {
        return "addCar";
    }

    @PostMapping("/cars/add")
    public String carsAdd(@ModelAttribute Car car,
                          @RequestParam(name = "carImage") MultipartFile file,
                          @RequestParam("dealerId") int dealerId) {
        log.info("/cars/add has been called");
        carService.saveCar(car, file, dealerId);
        return "redirect:/cars";
    }
    /**
     * Get car image
     */
    @GetMapping(value = "/cars/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return carService.getCarImage(fileName);
    }
    /**
     * Delete car by id
     */
    @GetMapping("/cars/delete")
    public String delete(@RequestParam("id") int id) throws EntityNotFoundException {
        log.info("/cars/delete has been called");
        carService.deleteById(id);
        return "redirect:/cars";

    }

    @GetMapping("/cars/edit")
    public String editPage(@RequestParam("id") int id, ModelMap modelMap) throws EntityNotFoundException {
        log.info("/cars/edit has been called");
        Optional<Car> carOptional = carService.findById(id);
        if (carOptional.isEmpty()) {
            return "redirect:/cars";
        }
        modelMap.addAttribute("car", carOptional.get());
        return "editCar";
    }

    /**
     * Update car by id
     */
    @PostMapping("/cars/edit")
    public String edit(@ModelAttribute Car car,
                       @RequestParam("dealerId") int dealerId,
                       @RequestParam(name = "carImage") MultipartFile file,
                       @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("/cars/edit has been called by {} ", currentUser.getUser().getName());
        if (currentUser.getUser().getId() == dealerId) {
            carService.updateCar(car, file, dealerId);
        }
        return "redirect:/cars";
    }


}