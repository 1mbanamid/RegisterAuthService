package by.imbanamid.kirillporokh.emailauthservice.Service;

import by.imbanamid.kirillporokh.emailauthservice.Entity.EmailVerifyToken;
import by.imbanamid.kirillporokh.emailauthservice.Repository.EmailVerificationTokenRepository;
import by.imbanamid.kirillporokh.emailauthservice.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationListener {
    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;


    @KafkaListener(topics = "email-verification", groupId = "email_verification_group")
    public void listen(String token) {
        EmailVerifyToken emailVerifyToken = emailVerificationTokenRepository.findByToken(token);
        if (emailVerifyToken == null) {
            System.out.println("Email verification token not found " + token );
            return;
        }
    System.out.println("Email verification token found " + token + ", User " + emailVerifyToken.getUser().getEmail());
    }

}
