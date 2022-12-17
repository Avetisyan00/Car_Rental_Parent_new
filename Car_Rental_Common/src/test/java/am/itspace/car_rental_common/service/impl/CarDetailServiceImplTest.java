package am.itspace.car_rental_common.service.impl;


import am.itspace.car_rental_common.entity.*;
import am.itspace.car_rental_common.exception.EntityNotFoundException;
import am.itspace.car_rental_common.repository.CarDetailRepository;
import am.itspace.car_rental_common.repository.CarRepository;
import am.itspace.car_rental_common.service.CarDetailService;
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
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CarDetailServiceImpl.class})
class CarDetailServiceImplTest {

    @Autowired
    private CarDetailService carDetailService;
    @MockBean
    private CarDetailRepository carDetailRepository;
    @MockBean
    private CarRepository carRepository;
    @Test
    void save() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(1, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        MultipartFile [] file = new MockMultipartFile[]{new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","2023-bmw-x5-front-1660572027.jpg","image","C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes())};
        carDetailService.save(image.getCar().getId(),file);
        verify(carDetailRepository,times(1)).save(any());

    }
    @Test
    void testSaveContentTypeNull() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        MultipartFile [] file = new MockMultipartFile[]{new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes())};
        carDetailService.save(image.getCar().getId(),file);
        verify(carDetailRepository,times(0)).save(any());

    }
    @Test
    void testSaveContentTypeIsNotImage() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carDetailRepository.save(any())).thenReturn(image);
        MultipartFile [] file = new MockMultipartFile[]{new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","2023-bmw-x5-front-1660572027.jpg","txt",
                "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes())};
        carDetailService.save(image.getCar().getId(),file);
        verify(carDetailRepository,times(0)).save(any());

    }
    @Test
    void testFindAllByCar() {
        List<Image> images = Arrays.asList(
                new Image(1,"BmwImage",new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User())) ,
        new Image(2,"BmwImage2",new Car(3, "bmw1", "X7", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 90, new User()))
        );
        when(carDetailRepository.findAllByCar_Id(anyInt())).thenReturn(images);
        for (Image image : images) {
            carDetailService.findAllByCar(image.getCar().getId());
        }
        verify(carDetailRepository,times(2)).findAllByCar_Id(anyInt());

    }

    @Test
    void delete() throws EntityNotFoundException {
        Image image = Image.builder()
                .id(61)
                .build();
        when(carDetailRepository.findById(any())).thenReturn(Optional.of(image));
        carDetailService.delete(image.getId());
        verify(carDetailRepository,times(1)).deleteById(any());
    }
    @Test
    void testDeleteCarIdIsNotPresent() {
        when(carDetailRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->{
            carDetailService.delete(3);
        });
        verify(carDetailRepository,times(0)).deleteById(any());
    }
    @Test
    void testUpdateCarImage() {
        Image image = Image.builder()
                .id(1)
                .picUrl("BmwImage")
                .car(new Car(16, "bmw", "X6", Transmission.AUTO, DriveUnit.RWD, Category.JEEP, SteeringWheel.RIGHT, FuelType.DIESEL,
                        "Red", 2009, "carImage2", 85, new User()))
                .build();
        when(carRepository.findById(any())).thenReturn(Optional.of(image.getCar()));
        MultipartFile file = new MockMultipartFile("2023-bmw-x5-front-1660572027.jpg","2023-bmw-x5-front-1660572027.jpg","image",
                "C:\\Users\\Levon\\Desktop\\2023-bmw-x5-front-1660572027.jpg\\".getBytes());
            carDetailService.updateCarImage(file,image);
        verify(carDetailRepository,times(1)).save(any());
    }
}