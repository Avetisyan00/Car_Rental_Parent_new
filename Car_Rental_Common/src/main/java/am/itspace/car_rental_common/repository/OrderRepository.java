package am.itspace.car_rental_common.repository;

import am.itspace.car_rental_common.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCar_Id(int carId);
    List<Order> findAllByClientId(int clientId);
    List<Order> findAllByDealerId(int dealerId);
    List<Order> findAllByDriverId(int driverId);
}
