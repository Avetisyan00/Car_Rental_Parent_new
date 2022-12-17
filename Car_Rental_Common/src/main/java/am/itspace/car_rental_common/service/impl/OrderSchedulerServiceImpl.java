package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.entity.Status;
import am.itspace.car_rental_common.service.OrderSchedulerService;
import am.itspace.car_rental_common.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSchedulerServiceImpl implements OrderSchedulerService {
    private final OrderService orderService;

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteFinishedOrders() {
        List<Order> allOrders = orderService.findAll();
        for (Order order : allOrders) {
            if (order.getOrderEnd().equals(LocalDate.now())) {
                order.getDriver().setStatus(Status.FREE);
                orderService.deleteOrderById(order.getId());
            }
        }
    }
}
