package am.itspace.car_rental_web.service;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.exception.DuplicateEmailException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUserAsClient(@ModelAttribute User user, MultipartFile file);

    void saveUserAsDriver(@ModelAttribute User user, MultipartFile file);

    void saveUserAsDealer(@ModelAttribute User user, MultipartFile file);

    void checkEmail(User user) throws DuplicateEmailException;

    void saveUsersImage(User user, MultipartFile file);

    void verifyUser(String email, String token);

    List<User> findAllByRole(Role role);

    void deleteById(Integer integer);

    Optional<User> findById(int id);

    void saveChanges(User user);
}
