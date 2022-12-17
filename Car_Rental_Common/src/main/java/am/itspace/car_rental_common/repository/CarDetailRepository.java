package am.itspace.car_rental_common.repository;

import am.itspace.car_rental_common.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDetailRepository extends JpaRepository<Image, Integer> {

    List<Image> findAllByCar_Id(int id);

}
