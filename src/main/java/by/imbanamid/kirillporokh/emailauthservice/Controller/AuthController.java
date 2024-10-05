package by.imbanamid.kirillporokh.emailauthservice.Controller;

import by.imbanamid.kirillporokh.emailauthservice.Service.EmailAuthService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired EmailAuthService emailAuthService;

  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate (@RequestParam String email,
                                              @RequestParam String password) {

    boolean  isAuthenticated = emailAuthService.authenticate(email, password);
    if (isAuthenticated) {
      return ResponseEntity.ok("Authenticated");
    }else {
      return ResponseEntity.status(401).body("Invalid email or password");
    }
  }

  @GetMapping("/verify")
  public ResponseEntity<String> verify(@RequestParam String token) {
    emailAuthService.verifyEmail(token);
    return ResponseEntity.ok("Email verified successfully");
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestParam String email,
                                         @RequestParam String password) throws MessagingException {
    emailAuthService.register(email, password);
    return  ResponseEntity.ok("Email registered successfully");

  }
}
