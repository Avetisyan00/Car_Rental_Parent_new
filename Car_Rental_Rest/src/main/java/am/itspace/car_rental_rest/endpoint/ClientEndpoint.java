package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserInfoService;
import am.itspace.car_rental_common.service.UserService;
import am.itspace.car_rental_rest.dto.RegistrationDto;
import am.itspace.car_rental_rest.mappper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class ClientEndpoint {
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserInfoService userInfoService;

    @PostMapping("/registration/client")
    public ResponseEntity<User> clientRegistration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.saveUserAsClientRest(userMapper.map(registrationDto)));
    }

    @PostMapping("/client/upload/{id}")
    public User clientRegistration(@RequestParam("pictures") MultipartFile[] files,
                                   @PathVariable("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            userService.saveUsersImage(user, files);
        }
        return byId.get();
    }

//    @GetMapping(value = "/client/download/", produces = "image/jfif")
//    public @ResponseBody byte[] getImage(@RequestParam("pictures") String files) {
//        return userInfoService.getUserImage(files);
//
//    }
}