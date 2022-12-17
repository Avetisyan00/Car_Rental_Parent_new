package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.*;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.repository.CarDetailRepository;
import am.itspace.car_rental_common.repository.CarRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_common.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@SpringBootTest(classes = {CarServiceImpl.class})
class CarServiceImplTest {

    @Autowired
    private CarService carService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CarDetailRepository carDetailRepository;
    @MockBean
    private CarRepository carRepository;

    @Test
    void testFindAll() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC,
                        SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.ELECTRIC,
                        SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        when(carRepository.findAll()).thenReturn(cars);
        carService.findAll();
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindAllByCategory() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC,
                        SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP,
                        SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        when(carRepository.findAllByCategory(any())).thenReturn(cars);
        for (Car car : cars) {
            carService.findAllByCategory(car.getCategory());
        }
        verify(carRepository, times(2)).findAllByCategory(any());

    }

   @Test
    void deleteById() throws EntityNotFoundException {
        Car car = Car.builder()
                .id(1)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        carService.deleteById(car.getId());
       verify(carRepository, times(1)).deleteById(any());
    }

    @Test
    void testDeleteByIdIsNotPresent() {
        Car car = Car.builder()
                .id(0)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        assertThrows(EntityNotFoundException.class, () -> carService.deleteById(car.getId()));
        verify(carRepository, times(0)).deleteById(any());
    }

    @Test
    void testSaveCar() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .productionYear(2020)
                .picUrl("mercedesCarImage")
                .pricePerDay(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "2023-bmw-x5-front-1660572027.jpg", "image", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.saveCar(car, file, car.getDealer().getId());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void testSaveCarContentTypeNull() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .productionYear(2020)
                .picUrl("mercedesCarImage")
                .pricePerDay(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.saveCar(car, file, car.getDealer().getId());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void testSaveCarContentTypeIsNotImage() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .productionYear(2020)
                .picUrl("mercedesCarImage")
                .pricePerDay(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "2023-bmw-x5-front-1660572027.jpg", "txt", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.saveCar(car, file, car.getDealer().getId());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    void testSaveCarNull() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .productionYear(2020)
                .picUrl("mercedesCarImage")
                .pricePerDay(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        assertThrows(RuntimeException.class, () -> carService.saveCar(null, null, 0));
        verify(carRepository, times(0)).save(any());
    }
    @Test
    void testUpdateCar() {
        Car car = Car.builder()
                .id(1)
                .name("Mercedes")
                .model("C")
                .transmission(Transmission.AUTO)
                .driveUnit(DriveUnit._4WD)
                .category(Category.ELECTRIC)
                .steeringWheel(SteeringWheel.RIGHT)
                .fuelType(FuelType.DIESEL)
                .color("Black")
                .productionYear(2020)
                .picUrl("mercedesCarImage")
                .pricePerDay(89)
                .dealer(new User())
                .build();
        when(carRepository.save(any())).thenReturn(car);
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg", "2023-bmw-x5-front-1660572027.jpg", "image", "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
        carService.updateCar(car, file, car.getDealer().getId());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    void findById() throws EntityNotFoundException {
        Car car = Car.builder()
                .id(1)
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(car));
        carService.findById(car.getId());
        verify(carRepository, times(1)).findById(anyInt());
    }

    @Test
    void testFindByIdIsCarIdIsNotPresent() {
        when(carRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            carService.findById(3);
        });
        verify(carRepository, times(1)).findById(anyInt());
    }
    @Test
    void testGetByDealer() {
        List<Car> cars = Arrays.asList(
                new Car(1, "bmw", "X5", Transmission.AUTO, DriveUnit.FWD, Category.ELECTRIC, SteeringWheel.LEFT, FuelType.DIESEL,
                        "White", 2008, "carImage", 78, new User()),
                new Car(2, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.ELECTRIC, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())
        );
        when(carRepository.findCarByDealerId(anyInt())).thenReturn(cars);
        for (Car car : cars) {
            carService.getByDealer(car.getDealer().getId());
        }
        verify(carRepository, times(2)).findCarByDealerId(anyInt());

    }
}