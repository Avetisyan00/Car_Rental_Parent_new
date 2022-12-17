package am.itspace.car_rental_rest.dto;

import am.itspace.car_rental_common.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarImageResponse {
    private int id;
    private String picUrl;

    private Car car;
}
