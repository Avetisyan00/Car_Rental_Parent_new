package am.itspace.car_rental_web.security;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findUserByEmail(username);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException("username does not exist");
        } else {
            return new CurrentUser(byEmail.get());

        }
    }
}

