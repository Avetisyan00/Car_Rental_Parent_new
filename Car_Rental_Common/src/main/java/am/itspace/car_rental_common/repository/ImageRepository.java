package am.itspace.car_rental_common.repository;

import am.itspace.car_rental_common.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<UserImage, Integer> {
    List<UserImage> findAllByUserId(int userId);
}
