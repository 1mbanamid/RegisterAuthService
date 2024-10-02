package by.imbanamid.kirillporokh.emailauthservice.Repository;

import by.imbanamid.kirillporokh.emailauthservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

}
