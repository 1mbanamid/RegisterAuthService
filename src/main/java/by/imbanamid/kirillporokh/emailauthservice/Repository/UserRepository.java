package by.imbanamid.kirillporokh.emailauthservice.Repository;

import by.imbanamid.kirillporokh.emailauthservice.Entity.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository is an interface that provides CRUD operations for RegisteredUser entities.
 * It extends JpaRepository, allowing for easy data access and manipulation.
 */
public interface UserRepository extends JpaRepository<RegisteredUser, Long> {

  /**
   * Finds a RegisteredUser by their email address.
   *
   * @param email the email address of the RegisteredUser
   * @return the RegisteredUser with the given email, or null if not found
   */
  RegisteredUser findByEmail(String email);
}
