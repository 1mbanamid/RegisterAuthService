package by.imbanamid.kirillporokh.emailauthservice.Service;

import by.imbanamid.kirillporokh.emailauthservice.Entity.VerificationToken;
import by.imbanamid.kirillporokh.emailauthservice.Repository.EmailVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationListener {

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository; // Repository to access verification tokens

    /**
     * Listener method that listens for email verification tokens from the Kafka topic.
     *
     * @param token The email verification token received from the Kafka topic.
     */
    @KafkaListener(topics = "email-verification", groupId = "email_verification_group")
    public void listen(String token) {
        VerificationToken verificationToken = emailVerificationTokenRepository.findByToken(token); // Retrieve the verification token from the repository

        if (verificationToken == null) {
            System.out.println("Email verification token not found: " + token); // Log if token is not found
            return; // Exit method if token is not found
        }

        System.out.println("Email verification token found: " + token + ", User: " + verificationToken.getUser().getEmail()); // Log successful token retrieval
    }
}
