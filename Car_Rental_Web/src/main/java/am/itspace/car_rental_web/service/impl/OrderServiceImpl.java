package am.itspace.car_rental_web.service.impl;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.repository.CarRepository;
import am.itspace.car_rental_common.repository.OrderRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_web.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    public void save(int carId, Order order) {
        Optional<Car> byId = carRepository.findById(carId);
        if (byId.isPresent()) {
            order.setAmount(byId.get().getPricePerHour());
            order.setCar(byId.get());
            orderRepository.save(order);
            log.info("The car has been saved");
        }
    }


    public List<Order> findAllByCar_id(int id) {
        return orderRepository.findAllByCar_Id(id);
    }

}
