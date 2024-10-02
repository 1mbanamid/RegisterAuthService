package by.imbanamid.kirillporokh.emailauthservice.Repository;

import by.imbanamid.kirillporokh.emailauthservice.Entity.EmailVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerifyToken, Long> {
  EmailVerifyToken findByToken(String token);
}
