package am.itspace.car_rental_rest.mapper;

import am.itspace.car_rental_common.entity.Image;
import am.itspace.car_rental_rest.dto.AddCarImagesDto;
import am.itspace.car_rental_rest.dto.CarImageResponse;
import am.itspace.car_rental_rest.dto.UpdateCarImageDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarDetailMapper {
    List<CarImageResponse> carImages (List<Image> images);

    Image carImage(AddCarImagesDto addCarImagesDto);

    CarImageResponse car(Image image);

    Image updateImage(UpdateCarImageDto updateCarImageDto);
}
