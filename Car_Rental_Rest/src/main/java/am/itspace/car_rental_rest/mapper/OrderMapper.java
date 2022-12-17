package am.itspace.car_rental_rest.mapper;

import am.itspace.car_rental_common.entity.Order;
import am.itspace.car_rental_rest.dto.AddOrderDto;
import am.itspace.car_rental_rest.dto.OrderResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order order(AddOrderDto addOrderDto);

    OrderResponseDto order(Order order);

    List<OrderResponseDto> orders(List<Order> orders);
}
