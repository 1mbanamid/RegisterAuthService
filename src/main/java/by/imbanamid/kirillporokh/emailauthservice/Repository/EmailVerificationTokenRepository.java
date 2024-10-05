package by.imbanamid.kirillporokh.emailauthservice.Repository;

import by.imbanamid.kirillporokh.emailauthservice.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * EmailVerificationTokenRepository is an interface that provides CRUD operations
 * for VerificationToken entities. It extends JpaRepository, allowing for easy
 * data access and manipulation.
 */
public interface EmailVerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  /**
   * Finds a VerificationToken by its token string.
   *
   * @param token the token string of the VerificationToken
   * @return the VerificationToken with the given token, or null if not found
   */
  VerificationToken findByToken(String token);
}
