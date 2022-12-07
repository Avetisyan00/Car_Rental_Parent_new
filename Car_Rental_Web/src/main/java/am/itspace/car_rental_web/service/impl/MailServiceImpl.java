package am.itspace.car_rental_web.service.impl;

import am.itspace.car_rental_common.entity.User;
import am.itspace.car_rental_web.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(User user) {
        try {
            final javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(user.getEmail());
            helper.setSubject("[CAR RENT] Verify your account");
            helper.setText(String.format("Dear " + user.getName() + " " + user.getSurname() + "," + "<br/>"
                    + "\\nVerify your account by clicking on this link \" +\n" +
                    "<a href=\\\"http://localhost:8080/users/verify?email=\" + user.getEmail() + \"&token=\" + user.getVerifyToken() + \"\\\">Activate</a>\""));

            emailSender.send(mimeMessage);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }

}
