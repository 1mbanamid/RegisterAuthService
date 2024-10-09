package by.imbanamid.kirillporokh.emailauthservice.Controller;

import by.imbanamid.kirillporokh.emailauthservice.DTO.LoginRequest;
import by.imbanamid.kirillporokh.emailauthservice.Service.AuthenticationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller class for handling authentication requests. It includes endpoints for user
 * registration, login, email verification, and token generation.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

  @Autowired private AuthenticationService authenticationService;

  /**
   * Authenticates a user and generates a JWT token.
   *
   * @param email the email of the user
   * @param password the raw password provided by the user
   * @return a response entity containing the JWT token
   */
  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate(
      @RequestParam String email, @RequestParam String password) {

    String token = authenticationService.authenticate(email, password);
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

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Map<String, String> requestData)
      throws MessagingException {
    String email = requestData.get("email");
    String password = requestData.get("password");
    authenticationService.register(email, password);
    return ResponseEntity.ok("Email registered successfully");
  }


  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest ) {
    try {
      String token = authenticationService.login(loginRequest.getEmail(),
                                                 loginRequest.getPassword());

      return ResponseEntity.ok("Bearer " + token);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}
