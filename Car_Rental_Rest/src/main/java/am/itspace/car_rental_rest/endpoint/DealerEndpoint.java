package am.itspace.car_rental_rest.endpoint;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserService;
import am.itspace.car_rental_rest.dto.RegistrationDto;
import am.itspace.car_rental_rest.mappper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DealerEndpoint {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration/dealer")
    public ResponseEntity<User> dealerRegistration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.saveUserAsDealerRest(userMapper.map(registrationDto)));
    }
}
