package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserInfoService;
import am.itspace.car_rental_common.service.UserService;
import am.itspace.car_rental_rest.dto.RegistrationDto;
import am.itspace.car_rental_rest.dto.UserDetailsDto;
import am.itspace.car_rental_rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserEndpoint {
    private final UserService userService;
    private final UserInfoService userDetailService;
    private final UserMapper userMapper;

    /**
     * After saving user data in database we need to
     * send email for verification. If user doesn't verify
     * his account by clicking on verification link in 24 hours,
     * scheduler will automatically delete user's data from db
     */
    @GetMapping("/users/verify")
    public ResponseEntity<?> verify(String email, String token) {
        userService.verifyUser(email, token);
        return ResponseEntity.ok().build();
    }

    /**
     * show image by filename
     */
    @GetMapping(value = "/user/detail/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return userDetailService.getUserImage(fileName);
    }

    /**
     * Find corresponding user by id
     */

    @GetMapping("/user/details/{id}")
    public ResponseEntity<UserDetailsDto> showUserDetails(@PathVariable(name = "id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            UserDetailsDto map = userMapper.map(user);
            getImage(map.getPicUrl());
            return ResponseEntity.ok(map);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Upload of images of user
     */
    @PostMapping("/client/upload/{id}")
    public ResponseEntity<User> addUserImage(@RequestParam("pictures") MultipartFile[] files,
                                             @PathVariable("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            userService.saveUsersImage(user, files);
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Registration of client
     */
    @PostMapping("/registration/client")
    public ResponseEntity<User> clientRegistration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.saveUserAsClientRest(userMapper.map(registrationDto)));
    }

    /**
     * Registration of dealer
     */

    @PostMapping("/registration/dealer")
    public ResponseEntity<User> dealerRegistration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.saveUserAsDealerRest(userMapper.map(registrationDto)));
    }

    /**
     * Registration of driver
     */

    @PostMapping("/registration/driver")
    public ResponseEntity<User> driverRegistration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.saveUserAsDriverRest(userMapper.map(registrationDto)));
    }
}
