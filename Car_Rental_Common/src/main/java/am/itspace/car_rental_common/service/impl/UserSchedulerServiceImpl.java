package am.itspace.car_rental_common.service.impl;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_common.service.UserSchedulerService;
import am.itspace.car_rental_common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSchedulerServiceImpl implements UserSchedulerService {
    private final UserService userService;

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void removeExpiredActivationLinkUsers() {
        List<User> allUsers = userService.findAll();

        for (User user : allUsers) {
            if (user.getTokenGivenDate().plusDays(1).isAfter(LocalDate.now())) {
                userService.deleteById(user.getId());
            }
        }
    }
}
