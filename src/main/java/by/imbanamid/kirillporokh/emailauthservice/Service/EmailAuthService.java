package by.imbanamid.kirillporokh.emailauthservice.Service;

import by.imbanamid.kirillporokh.emailauthservice.Entity.EmailVerifyToken;
import by.imbanamid.kirillporokh.emailauthservice.Entity.User;
import by.imbanamid.kirillporokh.emailauthservice.Repository.EmailVerificationTokenRepository;
import by.imbanamid.kirillporokh.emailauthservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailAuthService {

  @Autowired UserRepository userRepository;

  @Autowired EmailService emailService;

  @Autowired KafkaTemplate<String, String> kafkaTemplate;

  @Autowired EmailVerificationTokenRepository emailVerificationTokenRepository;

  private static final String TOPIC_EMAIL_VERIFICATION = "email-verification";

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  private EmailVerificationListener emailVerificationListener;

  public void register(String mail, String RawPassword) {
    User ExistingUser = userRepository.findByEmail(mail);

    if (ExistingUser != null) {
      throw new RuntimeException("User with this email already exists");
    }

    String encodedPassword = encoder.encode(RawPassword);

    User user = new User();
    user.setEmail(mail);
    user.setPassword(encodedPassword);
    user.setVerified(false);
    userRepository.save(user);

    String token = UUID.randomUUID().toString();
    LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(120);
    EmailVerifyToken emailVerifyToken = new EmailVerifyToken(token, user, expiresAt);
    emailVerificationTokenRepository.save(emailVerifyToken);
    sendVerificationEmail(user, token);
    kafkaTemplate.send(TOPIC_EMAIL_VERIFICATION, token);

  }

  public boolean authenticate(String email, String rawPassword) {
    User user = userRepository.findByEmail(email);
    if (user == null || !user.isVerified()) {
      throw new RuntimeException("User with this email does not exist");
    }

    if (!encoder.matches(rawPassword, user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }
    return true;
  }

  private void sendVerificationEmail(User user, String token) {
    emailService.sendEmail(user.getEmail(), token);
  }

  public void verifyEmail(String token) {

    EmailVerifyToken emailVerifyToken = emailVerificationTokenRepository.findByToken(token);

    if (emailVerifyToken == null) {
      throw new RuntimeException("Email verification token not found");
    }

    if (emailVerifyToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Email verification failed: Token is expired");
    }

    User user = emailVerifyToken.getUser();
    user.setVerified(true);
    userRepository.save(user);
    emailVerificationTokenRepository.delete(emailVerifyToken);
  }
}
