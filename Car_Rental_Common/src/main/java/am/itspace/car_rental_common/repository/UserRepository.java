package am.itspace.car_rental_common.repository;

import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByEmailAndVerifyToken(String email, String token);

    List<User> findAllByRole(Role role);

    void deleteById(Integer integer);
}
