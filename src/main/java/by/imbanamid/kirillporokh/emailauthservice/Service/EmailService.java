package by.imbanamid.kirillporokh.emailauthservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Email verification");
        message.setText("To verify your email, please click the link: http://localhost:8080/api/auth/verify?token=" + token);

        mailSender.send(message);

    }
}
