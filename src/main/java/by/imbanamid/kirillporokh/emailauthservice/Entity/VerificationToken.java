package by.imbanamid.kirillporokh.emailauthservice.Entity;

import by.imbanamid.kirillporokh.emailauthservice.Entity.RegisteredUser;
import jakarta.persistence.*;
import java.time.LocalDateTime;

// The @Entity annotation indicates that this class is a JPA entity.
@Entity
@Table(name = "EMAIL_VERIFICATION_TOKEN") // Table name in uppercase to match the database convention
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
  private Long id; // Unique identifier for the verification token

  private String token; // Verification token string

  @ManyToOne // Many tokens can be associated with one user
  @JoinColumn(name = "user_id", referencedColumnName = "id") // Foreign key column for user association
  private RegisteredUser registeredUser; // Associated registered user

  private LocalDateTime expiresAt; // Expiration date and time of the token

  // Constructor for creating a VerificationToken with specified values
  public VerificationToken(String token, RegisteredUser registeredUser, LocalDateTime expiresAt) {
    this.token = token;
    this.registeredUser = registeredUser;
    this.expiresAt = expiresAt;
  }

  // Default constructor
  public VerificationToken() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public RegisteredUser getUser() {
    return registeredUser;
  }

  public void setUser(RegisteredUser registeredUser) {
    this.registeredUser = registeredUser;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }
}
