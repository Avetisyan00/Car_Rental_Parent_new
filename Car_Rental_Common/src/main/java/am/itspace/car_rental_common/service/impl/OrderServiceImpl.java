package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.repository.OrderRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_common.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public void save(Car car, User driver, User dealer, int currentUser, LocalDate orderStart, LocalDate orderEnd, double amount) {
        Order order = new Order();
        order.setAmount(car.getPricePerDay());
        order.setCar(car);
        order.setOrderStart(orderStart);
        order.setOrderEnd(orderEnd);
        order.setDealer(car.getDealer());
        order.setDriver(driver);
        order.setDealer(dealer);
        order.setAmount(amount);
        Optional<User> byId = userRepository.findById(currentUser);
        byId.ifPresent(order::setClient);
        orderRepository.save(order);
        log.info("Order has been saved successfully");

    }

    @Override
    public List<Order> findAllByClientId(int clientId) {
        return orderRepository.findAllByClientId(clientId);
    }

    @Override
    public List<Order> findAllByDealerId(int dealerId) {
        return orderRepository.findAllByDealerId(dealerId);
    }

    @Override
    public List<Order> findAllByDriverId(int driverId) {
        return orderRepository.findAllByDriverId(driverId);
    }

    public List<Order> findAllByCar_id(int id) {
        return orderRepository.findAllByCar_Id(id);
    }
}