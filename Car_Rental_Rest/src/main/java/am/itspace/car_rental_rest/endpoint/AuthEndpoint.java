package am.itspace.car_rental_rest.endpoint;


import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_rest.dto.AuthenticationRequest;
import am.itspace.car_rental_rest.dto.AuthenticationResponse;
import am.itspace.car_rental_rest.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthEndpoint {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/user/auth")
    public ResponseEntity<?> auth(@RequestBody AuthenticationRequest authenticationRequest) {

        Optional<User> byEmail = userRepository.findUserByEmail(authenticationRequest.getUsername());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                log.info("User with username {} get auth token", user.getEmail());
                return ResponseEntity.ok(AuthenticationResponse.builder()
                        .token(jwtTokenUtil.generateToken(user.getEmail()))
                        .build()
                );
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}