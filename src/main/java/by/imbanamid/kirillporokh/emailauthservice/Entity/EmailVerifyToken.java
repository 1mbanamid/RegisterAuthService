package by.imbanamid.kirillporokh.emailauthservice.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmailAuthToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String token;

  @ManyToOne private User user;

  private LocalDateTime expiresAt;

  public EmailAuthToken(String token, User user, LocalDateTime expiresAt) {
    this.token = token;
    this.user = user;
    this.expiresAt = expiresAt;
  }

  public EmailAuthToken() {}

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(LocalDateTime expiresAt) {
    this.expiresAt = expiresAt;
  }
}
