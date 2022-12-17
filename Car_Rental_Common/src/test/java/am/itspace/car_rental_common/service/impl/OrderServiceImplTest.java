package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.Car;
import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.exception.InvalidOrderDateException;
import am.itspace.car_rental_common.repository.OrderRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_common.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {OrderServiceImpl.class})
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private UserRepository userRepository;
    @Test
    void save() throws InvalidOrderDateException {
        Order order = Order.builder()
                .orderStart(LocalDate. of(2022, 11, 30))
                .orderEnd(LocalDate. of(2022, 12, 1))
                .client(new User())
                .driver(new User())
                .dealer(new User())
                .car(new Car())
                .amount(85.5)
                .build();
        when(orderRepository.save(any())).thenReturn(order);
        orderService.save(order.getCar(),
                order.getDriver(),
                order.getDealer(),
                order.getClient().getId(),
                order.getOrderStart(),
                order.getOrderEnd(),
                order.getAmount());
        verify(orderRepository,times(1)).save(any());
    }
    @Test
    void saveIfOrderStartEqualsOrderEnd() throws InvalidOrderDateException {
        Order order = Order.builder()
                .orderStart(LocalDate. of(2022, 11, 30))
                .orderEnd(LocalDate. of(2022, 11, 30))
                .client(new User())
                .driver(new User())
                .dealer(new User())
                .car(new Car())
                .amount(85.5)
                .build();
        when(orderRepository.save(any())).thenReturn(order);
        assertThrows(InvalidOrderDateException.class,()->{
            orderService.save(order.getCar(),
                    order.getDriver(),
                    order.getDealer(),
                    order.getClient().getId(),
                    order.getOrderStart(),
                    order.getOrderEnd(),
                    order.getAmount());
        });

        verify(orderRepository,times(0)).save(any());
    }

    @Test
    void findAllByClientId() {
        List<Order> orders = Arrays.asList(
                new Order(1,LocalDate. of(2022, 11, 30),
                        LocalDate. of(2022, 12, 1),
                        new User(),new User(),new User(),new Car(),86),
                new Order(2,LocalDate. of(2022, 11, 21),
                        LocalDate. of(2022, 11, 25),
                        new User(),new User(),new User(),new Car(),87)
        );
        when(orderRepository.findAllByClientId(anyInt())).thenReturn(orders);
        for (Order order : orders) {
            orderService.findAllByClientId(order.getClient().getId());
        }
        verify(orderRepository,times(2)).findAllByClientId(anyInt());
    }

    @Test
    void findAllByDealerId() {
        List<Order> orders = Arrays.asList(
                new Order(1,LocalDate. of(2022, 11, 30),
                        LocalDate. of(2022, 12, 1),
                        new User(),new User(),new User(),new Car(),86),
                new Order(2,LocalDate. of(2022, 11, 21),
                        LocalDate. of(2022, 11, 25),
                        new User(),new User(),new User(),new Car(),87)
        );
        when(orderRepository.findAllByDealerId(anyInt())).thenReturn(orders);
        for (Order order : orders) {
            orderService.findAllByDealerId(order.getDealer().getId());
        }
        verify(orderRepository,times(2)).findAllByDealerId(anyInt());
    }

    @Test
    void findAllByDriverId() {
        List<Order> orders = Arrays.asList(
                new Order(1,LocalDate. of(2022, 11, 30),
                        LocalDate. of(2022, 12, 1),
                        new User(),new User(),new User(),new Car(),86),
                new Order(2,LocalDate. of(2022, 11, 21),
                        LocalDate. of(2022, 11, 25),
                        new User(),new User(),new User(),new Car(),87)
        );
        when(orderRepository.findAllByDriverId(anyInt())).thenReturn(orders);
        for (Order order : orders) {
            orderService.findAllByDriverId(order.getDriver().getId());
        }
        verify(orderRepository,times(2)).findAllByDriverId(anyInt());
    }

    @Test
    void findAllByCar_id() {
        List<Order> orders = Arrays.asList(
                new Order(1,LocalDate. of(2022, 11, 30),
                        LocalDate. of(2022, 12, 1),
                        new User(),new User(),new User(),new Car(),86),
                new Order(2,LocalDate. of(2022, 11, 21),
                        LocalDate. of(2022, 11, 25),
                        new User(),new User(),new User(),new Car(),87)
        );
        when(orderRepository.findAllByCar_Id(anyInt())).thenReturn(orders);
        for (Order order : orders) {
            orderService.findAllByCar_id(order.getCar().getId());
        }
        verify(orderRepository,times(2)).findAllByCar_Id(anyInt());
    }
}