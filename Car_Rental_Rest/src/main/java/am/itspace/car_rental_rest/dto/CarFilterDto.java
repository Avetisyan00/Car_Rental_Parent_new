package am.itspace.car_rental_rest.dto;

import am.itspace.car_rental_common.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarFilterDto {

    private String model;
    private String name;
    private SteeringWheel steeringWheel;

    private FuelType fuelType;
    private String color;

    private int productionYear;
    private String picUrl;
    private double pricePerDay;

}

