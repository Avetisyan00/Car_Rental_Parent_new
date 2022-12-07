package am.itspace.car_rental_web.service;

import am.itspace.car_rental_common.entity.User;

public interface EmailService {

    void sendEmail(User user);
}
