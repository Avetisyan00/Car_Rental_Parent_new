package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.DriverLicense;
import am.itspace.car_rental_common.entity.Role;
import am.itspace.car_rental_common.entity.Status;
import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.repository.ImageRepository;
import am.itspace.car_rental_common.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MailServiceImpl mailService;
    @MockBean
    private ImageRepository imageRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Test
    public void testCheckEmail() {
        when(userRepository.findUserByEmail(any())).thenThrow(new RuntimeException());
        User user = new User();
        user.setAge(1);
        user.setDriverLicense(DriverLicense.A);
        user.setEmail("petros@gmail.com");
        user.setEnabled(true);
        user.setId(1);
        user.setName("Petros");
        user.setSurname("Petrosyan");
        user.setPassword("Petros");
        user.setPhoneNumber("4105551212");
        user.setPicUrl("C:\\Users\\Edgar\\Desktop");
        user.setPricePerDay(120.5);
        user.setRating(3);
        user.setRole(Role.CLIENT);
        user.setStatus(Status.FREE);
        user.setVerifyToken("asf512");
        assertThrows(RuntimeException.class, () -> userServiceImpl.checkEmail(user));
        verify(userRepository).findUserByEmail(any());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(userRepository).deleteById(any());
        userServiceImpl.deleteById(1);
        verify(userRepository).deleteById( any());
    }

    @Test
    public void testDeleteById2() {
        doThrow(new RuntimeException()).when(userRepository).deleteById( any());
        assertThrows(RuntimeException.class, () -> userServiceImpl.deleteById(1));
        verify(userRepository).deleteById(any());
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setAge(1);
        user.setDriverLicense(DriverLicense.A);
        user.setEmail("poxos@gmail.com");
        user.setEnabled(true);
        user.setId(1);
        user.setName("Poxos");
        user.setSurname("Poxosyan");
        user.setPassword("poxos");
        user.setPhoneNumber("4105551212");
        user.setPicUrl("C:\\Users\\Edgar\\Desktop");
        user.setPricePerDay(120.5);
        user.setRating(3);
        user.setRole(Role.CLIENT);
        user.setStatus(Status.FREE);
        user.setVerifyToken("asf465");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(any())).thenReturn(ofResult);
        Optional<User> actualFindByIdResult = userServiceImpl.findById(1);
        assertSame(ofResult, actualFindByIdResult);
        assertTrue(actualFindByIdResult.isPresent());
        verify(userRepository).findById(any());
    }
}