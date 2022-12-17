package am.itspace.car_rental_rest.dto;

import am.itspace.car_rental_common.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCarDto {

    private int id;
    @NotEmpty(message = "model can't be null or empty")
    private String model;
    @NotEmpty(message = "name can't be null or empty")
    private String name;
    @NotNull(message = "model can't be null")
    private Transmission transmission;

    @NotNull(message = "driveUnit can't be null")
    private DriveUnit driveUnit;
    @NotNull(message = "category can't be null or empty")
    private Category category;
    @NotNull(message = "steering wheel can't be null or empty")
    private SteeringWheel steeringWheel;
    @NotNull(message = "fuel type can't be null or empty")
    private FuelType fuelType;
    @NotEmpty(message = "color can't be null or empty")
    private String color;
    @NotEmpty(message = "production year can't be null or empty")
    private int productionYear;
    @NotEmpty(message = "picture year can't be null or empty")
    private String picUrl;
    @Range(min = 0)
    private double pricePerDay;

    private User dealer;
}
