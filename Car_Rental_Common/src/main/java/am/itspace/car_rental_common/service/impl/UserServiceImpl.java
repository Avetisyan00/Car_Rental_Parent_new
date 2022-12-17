package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.entity.UserImage;
import am.itspace.car_rental_common.exception.DuplicateEmailException;
import am.itspace.car_rental_common.repository.ImageRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_common.service.MailService;
import am.itspace.car_rental_common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    @Value("${car.rental.user.images.folder}")
    private String folderPath;


    public void saveUserAsClient(@ModelAttribute User user, MultipartFile[] files) throws DuplicateEmailException {

        try {
            if (checkEmail(user)) {

                user.setRole(Role.CLIENT);
                user.setEnabled(false);
                user.setVerifyToken(UUID.randomUUID().toString());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setTokenGivenDate(LocalDate.now());
                userRepository.save(user);
                saveUsersImage(user, (files));
                mailService.sendEmail(user.getEmail(), "Verify your account", "Hello " + user.getName() + " " + user.getSurname() + ".\nVerify your account by clicking on this link " + "<a href=\"http://localhost:8080/users/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
            }
        } catch (RuntimeException e) {
            throw new DuplicateEmailException(e.getMessage());
        }
    }

    @Override
    public User saveUserAsDriverRest(User user) {
        user.setRole(Role.DRIVER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUserAsDealerRest(User user) {
        user.setRole(Role.DEALER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public void saveUserAsDriver(@ModelAttribute User user, MultipartFile[] files) throws DuplicateEmailException {
        try {
            if (checkEmail(user)) {
                user.setRole(Role.DRIVER);
                user.setEnabled(false);
                user.setVerifyToken(UUID.randomUUID().toString());
                user.setTokenGivenDate(LocalDate.now());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                saveUsersImage(user, files);
                mailService.sendEmail(user.getEmail(), "Verify your account", "Hello " + user.getName() + " " + user.getSurname() + ".\nVerify your account by clicking on this link " + "<a href=\"http://localhost:8080/users/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
            }
        } catch (RuntimeException e) {
            throw new DuplicateEmailException(e.getMessage());
        }
    }

    public void saveUserAsDealer(@ModelAttribute User user, MultipartFile[] files) {
        checkEmail(user);
        user.setRole(Role.DEALER);
        user.setEnabled(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setTokenGivenDate(LocalDate.now());
        userRepository.save(user);
        saveUsersImage(user, files);
        mailService.sendEmail(user.getEmail(), "Verify your account", "Hello " + user.getName() + " " + user.getSurname() + ".\nVerify your account by clicking on this link " + "<a href=\"http://localhost:8080/users/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
    }

    public boolean checkEmail(User user) {
        return userRepository.findUserByEmail(user.getEmail()).isEmpty();
    }


    public void saveUsersImage(User user, MultipartFile[] files) {
        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty() && file.getSize() > 0) {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File newFile = new File(folderPath + File.separator + fileName);
                    file.transferTo(newFile);
                    user.setPicUrl(fileName);
                    UserImage userImage = UserImage.builder().user(user).picUrl(fileName).build();
                    imageRepository.save(userImage);
                } else {
                    user.setPicUrl(folderPath);
                }
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

    public User update(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveUserAsClientRest(User user) {
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}