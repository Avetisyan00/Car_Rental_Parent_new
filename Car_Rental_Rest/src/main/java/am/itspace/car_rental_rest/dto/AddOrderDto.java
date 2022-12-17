package am.itspace.car_rental_rest.dto;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AddOrderDto {

    @NotEmpty(message = "orderStart can't be null or empty")
    private LocalDate orderStart;
    @NotEmpty(message = "orderEnd can't be null or empty")
    private LocalDate orderEnd;

    private User driver;
    @NotEmpty(message = "dealer can't be null or empty")
    private User dealer;
    @NotEmpty(message = "client can't be null or empty")
    private User client;
    @NotEmpty(message = "car can't be null or empty")
    private Car car;
    @Range(min = 0)
    private double amount;
}