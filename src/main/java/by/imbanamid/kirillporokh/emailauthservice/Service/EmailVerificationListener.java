package by.imbanamid.kirillporokh.emailauthservice.Service;

import by.imbanamid.kirillporokh.emailauthservice.Repository.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationListener {
    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "email-verification", groupId = "email_verification_group")
    public void listen(String token) {

    }

}
