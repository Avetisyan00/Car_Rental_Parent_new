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
public class CarResponseDto {

    private int id;
    private String model;
    private String name;

    private Transmission transmission;

    private DriveUnit driveUnit;

    private Category category;

    private SteeringWheel steeringWheel;

    private FuelType fuelType;
    private String color;

    private int productionYear;
    private String picUrl;
    private double pricePerDay;
    private User dealer;
}
