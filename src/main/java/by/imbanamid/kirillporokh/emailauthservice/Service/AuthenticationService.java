package by.imbanamid.kirillporokh.emailauthservice.Service;

import by.imbanamid.kirillporokh.emailauthservice.Entity.RegisteredUser;

import by.imbanamid.kirillporokh.emailauthservice.Entity.VerificationToken;
import by.imbanamid.kirillporokh.emailauthservice.Repository.EmailVerificationTokenRepository;
import by.imbanamid.kirillporokh.emailauthservice.Repository.UserRepository;
import by.imbanamid.kirillporokh.emailauthservice.Security.JwtTokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * Service class for handling email authentication and registration. It includes methods for user
 * registration, login, email verification, and token generation.
 */
@Service
public class AuthenticationService {

  @Autowired private UserRepository userRepository;

  @Autowired private EmailService emailService;

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired private EmailVerificationTokenRepository emailVerificationTokenRepository;

  @Autowired private JwtTokenUtil jwtTokenUtil;

  private static final String TOPIC_EMAIL_VERIFICATION = "email-verification";

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  private final String JWT_SECRET = "secret_key";
  private final long JWT_EXPIRATION = 604800000; // 7 Days

  /**
   * Authenticates a user and generates a JWT token.
   *
   * @param email the email of the user
   * @param rawPassword the raw password provided by the user
   * @return a JWT token for the authenticated user
   */
  public String login(String email, String rawPassword) {
    RegisteredUser user = userRepository.findByEmail(email);
    if (user == null || !user.isVerified()) {
      throw new RuntimeException("User with email " + email + " not found or not verified");
    }
    if (!encoder.matches(rawPassword, user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }
    return Jwts.builder()
        .setSubject(user.getEmail())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact();
  }

  /**
   * Registers a new user and sends a verification email.
   *
   * @param email the email of the user to register
   * @param rawPassword the raw password provided by the user
   * @throws MessagingException if an error occurs while sending the email
   */
  public void register(String email, String rawPassword) throws MessagingException {
    RegisteredUser existingUser = userRepository.findByEmail(email);

    if (existingUser != null) {
      throw new RuntimeException("User with this email already exists");
    }

    String encodedPassword = encoder.encode(rawPassword);
    RegisteredUser user = new RegisteredUser();
    user.setEmail(email);
    user.setPassword(encodedPassword);
    user.setVerified(false);
    userRepository.save(user);

    String token = UUID.randomUUID().toString();
    LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(120);
    VerificationToken emailVerifyToken = new VerificationToken(token, user, expiresAt);
    emailVerificationTokenRepository.save(emailVerifyToken);
    sendVerificationEmail(user, token);
    kafkaTemplate.send(TOPIC_EMAIL_VERIFICATION, token);
  }

  /**
   * Generates a JWT token for the authenticated user.
   *
   * @param email the email of the user
   * @param rawPassword the raw password provided by the user
   * @return a JWT token for the authenticated user
   */
  public String authenticate(String email, String rawPassword) {
    RegisteredUser user = userRepository.findByEmail(email);
    if (user == null || !user.isVerified()) {
      throw new RuntimeException("User with this email does not exist or is not verified");
    }

    if (!encoder.matches(rawPassword, user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }
    return jwtTokenUtil.generateToken(email);
  }

  /**
   * Sends a verification email to the user.
   *
   * @param user the user to send the email to
   * @param token the verification token
   * @throws MessagingException if an error occurs while sending the email
   */
  private void sendVerificationEmail(RegisteredUser user, String token) throws MessagingException {
    emailService.sendEmail(user.getEmail(), token);
  }

  /**
   * Verifies the email of a user using the provided token.
   *
   * @param token the verification token
   */
  public void verifyEmail(String token) {
    VerificationToken emailVerifyToken = emailVerificationTokenRepository.findByToken(token);

    if (emailVerifyToken == null) {
      throw new RuntimeException("Email verification token not found");
    }

    if (emailVerifyToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("Email verification failed: Token is expired");
    }

    RegisteredUser user = emailVerifyToken.getUser();
    user.setVerified(true);
    userRepository.save(user);
    emailVerificationTokenRepository.delete(emailVerifyToken);
  }
}
