package am.itspace.car_rental_rest.dto;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponseDto {

    private int id;
    private LocalDate orderStart;

    private LocalDate orderEnd;

    private User driver;

    private User dealer;

    private User client;

    private Car car;
    private double amount;
}