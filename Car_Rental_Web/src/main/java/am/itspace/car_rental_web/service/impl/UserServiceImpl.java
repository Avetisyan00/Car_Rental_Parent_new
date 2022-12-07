package am.itspace.car_rental_web.service.impl;

import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.exception.DuplicateEmailException;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_web.service.EmailService;
import am.itspace.car_rental_web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${car.rental.user.images.folder}")
    private String folderPath;

    public void saveUserAsClient(@ModelAttribute User user, MultipartFile file) {
        try {
            checkEmail(user);
            user.setRole(Role.CLIENT);
            user.setEnabled(false);
            user.setVerifyToken(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveUsersImage(user, (file));
            userRepository.save(user);
            mailService.sendEmail(user);
        } catch (DuplicateEmailException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveUserAsDriver(@ModelAttribute User user, MultipartFile file) {
        try {
            checkEmail(user);
            user.setRole(Role.DRIVER);
            user.setEnabled(false);
            user.setVerifyToken(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            saveUsersImage(user, file);
            userRepository.saveAndFlush(user);
            mailService.sendEmail(user);
        } catch (DuplicateEmailException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveUserAsDealer(@ModelAttribute User user, MultipartFile file) {
        try {
            checkEmail(user);
            user.setRole(Role.DEALER);
            saveUsersImage(user, file);
            user.setEnabled(false);
            user.setVerifyToken(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            mailService.sendEmail(user);
        } catch (DuplicateEmailException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void checkEmail(User user) throws DuplicateEmailException {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("User with this email already exists");
        }
    }

    public void saveUsersImage(User user, MultipartFile file) {
        try {
            if (!file.isEmpty() && file.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File newFile = new File(folderPath + File.separator + fileName);
                file.transferTo(newFile);
                user.setPicUrl(fileName);
            } else {
                user.setPicUrl("C:\\Users\\Edgar\\IdeaProjects\\DiplomaProject\\Car Rental\\src\\main\\resources\\static\\supercars\\assets\\images\\defaultPic.jpg");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void verifyUser(String email, String token) {
        try {
            Optional<User> userOptional = userRepository.findUserByEmailAndVerifyToken(email, token);
            if (userOptional.isEmpty()) {
                throw new Exception("User does with this email and token does not exists");
            }
            User user = userOptional.get();
            if (user.isEnabled()) {
                throw new Exception("User is already verified");
            }
            user.setEnabled(true);
            user.setVerifyToken(null);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public void saveChanges(User user) {

        userRepository.save(user);
    }
}