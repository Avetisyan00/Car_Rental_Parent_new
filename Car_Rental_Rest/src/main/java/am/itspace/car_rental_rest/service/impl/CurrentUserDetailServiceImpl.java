package am.itspace.car_rental_rest.service.impl;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.repository.UserRepository;
import am.itspace.car_rental_rest.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new CurrentUser(user.get());
    }
}