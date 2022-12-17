package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Category;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.service.CarService;
import am.itspace.car_rental_rest.customRepo.CustomCarRepo;
import am.itspace.car_rental_rest.dto.AddCarDto;
import am.itspace.car_rental_rest.dto.CarFilterDto;
import am.itspace.car_rental_rest.dto.CarResponseDto;
import am.itspace.car_rental_rest.dto.UpdateCarDto;
import am.itspace.car_rental_rest.mapper.CarMapper;
import am.itspace.car_rental_rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
@Slf4j
public class CarEndpoint {

    private final CarMapper carMapper;

    private final CarService carService;
    private final CustomCarRepo customCarRepo;

    /**
     * Get list of cars using  QueryDSL
     */
    @GetMapping()
    public List<CarResponseDto> getAllCars(@RequestBody CarFilterDto carFilterDto) {
        log.info("/cars has been called");
        List<Car> cars = customCarRepo.cars(carFilterDto);
        return carMapper.carsFilter(cars);
    }

    /**
     * Get list of cars by category
     */
    @GetMapping("/{category}")
    public List<CarResponseDto> gatAllCarsByCategory(@PathVariable("category") Category category) {
        log.info("/cars/category has been called");
        return carMapper.carsFilter(carService.findAllByCategory(category));
    }
    /**
     * Get car image
     */
    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@RequestParam("fileName") String fileName) {
        byte[] carImage = carService.getCarImage(fileName);
        return ResponseEntity.ok(carImage);
    }
    /**
     * Get list of cars by dealer
     */
    @GetMapping("/getByDealer")
    public List<CarResponseDto> MyCars(@RequestParam("dealerId") int dealerId,
                                       @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("/cars/getByDealer has been called by{}", currentUser.getUser().getName());
        if (currentUser.getUser().getId() == dealerId) {
            return carMapper.carsFilter(carService.getByDealer(dealerId));
        }
        return null;
    }

    /**
     * Add car and upload car image
     */
    @PostMapping(value = "/add")
    public ResponseEntity<?> saveCar(@RequestPart("request") @Valid AddCarDto addCarDto,
                                     @RequestPart("file") MultipartFile file,
                                     @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("/cars/add has been called");
        Car cars = carMapper.car(addCarDto);
        carService.saveCar(cars, file, currentUser.getUser().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(carMapper.car(cars));
    }
    /**
     * Update car by id
     */
    @PutMapping("/{id}")
    public ResponseEntity<UpdateCarDto> updateCar(@PathVariable("id") int id,
                                                  @RequestPart(name = "request") @Valid UpdateCarDto updateCarDto,
                                                  @RequestPart(name = "file") MultipartFile file,
                                                  @AuthenticationPrincipal CurrentUser currentUser) throws EntityNotFoundException {
        log.info("/cars/delete has been called");
        if (currentUser.getUser().getId() == updateCarDto.getDealer().getId()) {
            Optional<Car> byId = carService.findById(id);
            if (byId.isPresent()) {
                Car car = carMapper.car(updateCarDto);
                carService.updateCar(car, file, currentUser.getUser().getId());
                return ResponseEntity.ok(updateCarDto);
            }
        }
        return ResponseEntity.badRequest().build();

    }
    /**
     * Delete car by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") int id,
                                       @AuthenticationPrincipal CurrentUser currentUser) throws EntityNotFoundException {
        log.info("/cars/edit has been called");
        Optional<Car> byId = carService.findById(id);
        if (currentUser.getUser().getId() == byId.get().getDealer().getId()) {
            carService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

}