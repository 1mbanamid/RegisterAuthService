package by.imbanamid.kirillporokh.emailauthservice.Entity;

import jakarta.persistence.*;

// The @Entity annotation indicates that this class is a JPA entity.
@Entity
@Table(name = "registered_user") // Table name in snake_case for consistency with database naming
// conventions
public class RegisteredUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
  private long id; // User identifier

  private String email; // User's email address
  private String password; // User's password

  @Column(name = "is_verified") // Maps to the "is_verified" column in the database
  private boolean verified; // Flag indicating whether the account is verified

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }
}
