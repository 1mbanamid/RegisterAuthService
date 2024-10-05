package by.imbanamid.kirillporokh.emailauthservice.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

  // Secret key for signing the JWT
  private final String secretKey = "secret_key"; // The key used for signing the JWT

  /**
   * Generate a JWT token for the given email.
   *
   * @param email The email for which the token is generated.
   * @return The generated JWT token.
   */
  public String generateToken(String email) {
    Map<String, Object> claims = new HashMap<>(); // Create a claims map
    return createToken(claims, email); // Create the token
  }

  /**
   * Create a JWT token with specified claims and subject (email).
   *
   * @param claims The claims to be included in the token.
   * @param subject The subject of the token (email).
   * @return The created JWT token.
   */
  private String createToken(Map<String, Object> claims, String subject) {
    long expirationTimeInSeconds = 86400000; // Set expiration time (1 day)
    return Jwts.builder()
            .setClaims(claims) // Set claims
            .setSubject(subject) // Set subject (email)
            .setIssuedAt(new Date(System.currentTimeMillis())) // Set issue date
            .signWith(SignatureAlgorithm.HS256, secretKey) // Sign the token
            .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInSeconds)) // Set expiration date
            .compact(); // Build the token
  }

  /**
   * Validate the given JWT token with the expected email.
   *
   * @param token The JWT token to validate.
   * @param email The expected email to validate against.
   * @return True if the token is valid; otherwise, false.
   */
  public boolean validateToken(String token, String email) {
    final String username = extractUsername(token); // Extract username from token
    return (username.equals(email) && !isTokenExpired(token)); // Check if username matches and token is not expired
  }

  /**
   * Check if the token has expired.
   *
   * @param token The JWT token to check.
   * @return True if the token is expired; otherwise, false.
   */
  private boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date()); // Compare expiration date with current date
  }

  /**
   * Extract username (email) from the token.
   *
   * @param token The JWT token from which to extract the username.
   * @return The extracted username (email).
   */
  public String extractUsername(String token) {
    return extractAllClaims(token).getSubject(); // Return the subject from claims
  }

  /**
   * Extract all claims from the JWT token.
   *
   * @param token The JWT token from which to extract claims.
   * @return The claims contained in the JWT token.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .setSigningKey(secretKey) // Set the signing key
            .parseClaimsJws(token) // Parse the token
            .getBody(); // Return claims body
  }
}
