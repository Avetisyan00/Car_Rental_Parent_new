package am.itspace.car_rental_web.service;

import am.itspace.car_rental_common.entity.Order;

import java.util.List;

public interface OrderService {

    void save(int carId, Order order);


    List<Order> findAllByCar_id(int id);
}
