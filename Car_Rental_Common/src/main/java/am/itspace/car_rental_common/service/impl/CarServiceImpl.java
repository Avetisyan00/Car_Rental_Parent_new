package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Category;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.repository.CarDetailRepository;
import am.itspace.car_rental_common.repository.CarRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_common.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarDetailRepository carDetailRepository;


    @Value("${car.rental.images.folder}")
    private String folderPath;

    public List<Car> findAll() {
        log.info("Get all cars from database");
        return carRepository.findAll();
    }

    @Cacheable("cars")
    public List<Car> findAllByCategory(Category category) {
        log.info("Get all cars by category from database");
        return carRepository.findAllByCategory(category);
    }

    public byte[] getCarImage(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    public void deleteById(int id) throws EntityNotFoundException {
        Optional<Car> byId = carRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Car with " + id + " id does not exists");
        }
        carRepository.deleteById(id);
        log.info("The car has already been deleted the id{} ", id);
    }

    /**
     * This method saves the car.
     * we send all image of car, and set dealer
     */
    public void saveCar(Car car, MultipartFile file, int dealerId) {
        if (car == null) {
            throw new RuntimeException("Car can't be null");
        }
        if (!file.isEmpty() && file.getSize() > 0) {
            if (file.getContentType() != null && file.getContentType().contains("image")) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File newFile = new File(folderPath + File.separator + fileName);
                    file.transferTo(newFile);
                    car.setPicUrl(fileName);
                    Optional<User> byId = userRepository.findById(dealerId);
                    if (byId.isPresent() && car.getDealer() == null) {
                        car.setDealer(byId.get());
                    }
                    carRepository.save(car);
                    log.info("The car has already been saved");
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    /**
     * his method allows to update Car.
     * we need to get it by its ID
     * after getting it, we set new values and save them in db
     */
    public void updateCar(Car car, MultipartFile file, int dealerId) {
        if (car == null) {
            throw new RuntimeException("Car can't be null");
        }
        if (!file.isEmpty() && file.getSize() > 0) {
            if (file.getContentType() != null && file.getContentType().contains("image")) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File newFile = new File(folderPath + File.separator + fileName);
                    file.transferTo(newFile);
                    car.setPicUrl(fileName);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        Optional<User> byId = userRepository.findById(dealerId);
        if (byId.isPresent() && car.getDealer() == null) {
            car.setDealer(byId.get());
        }
        carRepository.save(car);
        log.info("The car has already been saved");
    }

    public Optional<Car> findById(int id) throws EntityNotFoundException {
        Optional<Car> byId = carRepository.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("Car with " + id + " id does not exists");
        }
        log.info("Get car by id {}", id + " from database");
        return byId;
    }

    /**
     * Due to this method we can show all cars, belonging to current dealer.
     * First of all we need to get dealer by id using corresponding service,
     * and after we send all cars
     */
    public List<Car> getByDealer(int dealerId) {
        List<Car> carByDealerId = carRepository.findCarByDealerId(dealerId);
        if (!carByDealerId.isEmpty()) {
            log.info("Get cars by dealerId {} ", dealerId + " from database");
            return carByDealerId;
        }
        return carRepository.findAll();
    }
}
