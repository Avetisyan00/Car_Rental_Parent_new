package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserInfoService;
import am.itspace.car_rental_common.service.UserService;
import am.itspace.car_rental_rest.dto.UserDetailsDto;
import am.itspace.car_rental_rest.mappper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/user/detail/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) {
        return userDetailService.getUserImage(fileName);
    }

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
}
