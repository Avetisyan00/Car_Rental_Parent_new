package am.itspace.car_rental_rest.dto;

import am.itspace.car_rental_common.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCarImageDto {
    private int id;

    private String picUrl;
    @NotNull(message = "Car can't be null")
    private Car car;
}
