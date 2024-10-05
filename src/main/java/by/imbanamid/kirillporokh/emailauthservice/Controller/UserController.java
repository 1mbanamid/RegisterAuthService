package by.imbanamid.kirillporokh.emailauthservice.Controller;

import by.imbanamid.kirillporokh.emailauthservice.DTO.LoginRequest;
import by.imbanamid.kirillporokh.emailauthservice.DTO.RegisterRequest;
import by.imbanamid.kirillporokh.emailauthservice.Service.AuthenticationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling authentication requests.
 * It includes endpoints for user registration, login, email verification, and token generation.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

  @Autowired
  private AuthenticationService authenticationService;

  /**
   * Authenticates a user and generates a JWT token.
   *
   * @param loginRequest the login request containing email and password
   * @return a response entity containing the JWT token
   */
  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate(@RequestBody @Validated LoginRequest loginRequest) {
    String token = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    return ResponseEntity.ok(token);
  }

  /**
   * Verifies the user's email using the provided token.
   *
   * @param token the verification token
   * @return a response entity confirming successful email verification
   */
  @GetMapping("/verify")
  public ResponseEntity<String> verify(@RequestParam String token) {
    authenticationService.verifyEmail(token);
    return ResponseEntity.ok("Email verified successfully");
  }

  /**
   * Registers a new user and sends a verification email.
   *
   * @param registerRequest the registration request containing email and password
   * @return a response entity confirming successful registration
   * @throws MessagingException if an error occurs while sending the email
   */
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Validated RegisterRequest registerRequest)
          throws MessagingException {
    authenticationService.register(registerRequest.getEmail(), registerRequest.getPassword());
    return ResponseEntity.ok("Email registered successfully");
  }

  /**
   * Logs in a user and generates a JWT token.
   *
   * @param loginRequest the login request containing email and password
   * @return a response entity containing the JWT token
   */
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest) {
    String token = authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
    return ResponseEntity.ok("Bearer " + token);
  }
}
