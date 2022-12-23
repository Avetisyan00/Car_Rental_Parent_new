package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_common.exception.InvalidOrderDateException;
import am.itspace.car_rental_common.service.OrderService;
import am.itspace.car_rental_rest.dto.AddOrderDto;
import am.itspace.car_rental_rest.dto.OrderResponseDto;
import am.itspace.car_rental_rest.mapper.OrderMapper;
import am.itspace.car_rental_rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderEndpoint {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    /**
     * Add order
     */
    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@RequestBody @Valid AddOrderDto addOrderDto,
                                      @AuthenticationPrincipal CurrentUser currentUser) throws InvalidOrderDateException {
        LocalDateTime now = LocalDateTime.now();
        List<Order> allByCarId = orderService.findAllByCarId(addOrderDto.getCar().getId());
        for (Order order : allByCarId) {
            if ((now.isAfter(order.getOrderStart().atStartOfDay()) || now.equals(order.getOrderStart())) && (now.isBefore(order.getOrderEnd().atStartOfDay()))) {
               return ResponseEntity.badRequest().build();
            }
        }
        Order order = orderMapper.order(addOrderDto);
        double amount;
        if (addOrderDto.getDriver() != null){
            amount = addOrderDto.getCar().getPricePerDay() + addOrderDto.getDriver().getPricePerDay();
        }else {
            amount = addOrderDto.getCar().getPricePerDay();
        }
        orderService.save(addOrderDto.getCar(), addOrderDto.getDriver(), addOrderDto.getDealer(),
                currentUser.getUser().getId(), addOrderDto.getOrderStart(), addOrderDto.getOrderEnd(), amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.order(order));
    }

    /**
     * Get list of orders by currentUser
     */
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDto>> shoeMyAllOrders(@AuthenticationPrincipal CurrentUser currentUser) {
        List<OrderResponseDto> orders = new ArrayList<>();
        switch (currentUser.getUser().getRole()) {
            case CLIENT:
                List<OrderResponseDto> ordersByClientId = orderMapper.orders(orderService.findAllByClientId(currentUser.getUser().getId()));
                orders.addAll(ordersByClientId);
                break;
            case DEALER:
                List<OrderResponseDto> ordersByDealerId = orderMapper.orders(orderService.findAllByDealerId(currentUser.getUser().getId()));
                orders.addAll(ordersByDealerId);
                break;
            case DRIVER:
                List<OrderResponseDto> ordersByDriverId = orderMapper.orders(orderService.findAllByDriverId(currentUser.getUser().getId()));
                orders.addAll(ordersByDriverId);
                break;
        }
        return ResponseEntity.ok(orders);
    }
}
