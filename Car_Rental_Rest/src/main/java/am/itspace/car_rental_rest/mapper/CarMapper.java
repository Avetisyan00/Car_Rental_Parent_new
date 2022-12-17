package am.itspace.car_rental_rest.mapper;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_rest.dto.AddCarDto;
import am.itspace.car_rental_rest.dto.CarResponseDto;
import am.itspace.car_rental_rest.dto.UpdateCarDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {

    List <CarResponseDto> carsFilter (List<Car> cars);
    Car car(AddCarDto addCarDto);
    Car car(UpdateCarDto updateCarDto);

    CarResponseDto car(Car car);


}
