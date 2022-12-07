package am.itspace.car_rental_common.repository;

import am.itspace.car_rental_common.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CarDetailRepository extends JpaRepository<Image,Integer> {

    List<Image> findAllByCar_Id(int id);
    Optional<Image> deleteByCar_Id(int carId);
}
