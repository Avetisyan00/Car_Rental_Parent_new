package am.itspace.car_rental_common.service;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.exception.InvalidOrderDateException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    void save(Car car, User driver, User dealer, int currentUser, LocalDate orderStart, LocalDate orderEnd, double amount) throws InvalidOrderDateException;

    List<Order> findAllByClientId(int clientId);

    List<Order> findAllByDealerId(int dealerId);

    List<Order> findAllByDriverId(int driverId);

    List<Order> findAllByCarId(int id);

    List<Order> findAll();

    void deleteOrderById(int id);
}
