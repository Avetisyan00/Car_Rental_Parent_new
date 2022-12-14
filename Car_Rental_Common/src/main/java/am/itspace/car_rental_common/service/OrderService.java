package am.itspace.car_rental_common.service;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void save(Car car, User driver, User dealer, int currentUser, LocalDate orderStart, LocalDate orderEnd, double amount);
    List<Order> findAllByClientId(int clientId);
    List<Order> findAllByDealerId(int dealerId);
    List<Order> findAllByDriverId(int driverId);

    List<Order> findAllByCar_id(int id);
}
