package am.itspace.car_rental_common.repository;

import am.itspace.car_rental_common.entity.UserImage;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<UserImage, Integer> {
    List<UserImage> findAllByUserId(int userId);
}
