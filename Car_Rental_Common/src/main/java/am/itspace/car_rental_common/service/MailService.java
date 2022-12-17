package am.itspace.car_rental_common.service;

public interface MailService {

    void sendEmail(String to, String subject, String text);
}
