package by.imbanamid.kirillporokh.emailauthservice.Security;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JwtRequestFilter is a filter that processes incoming requests for JWT authentication.
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  JwtTokenUtil jwtTokenUtil; // Utility class for JWT operations

  @Autowired
  UserDetailsService userDetailsService; // Service for loading user details

  /**
   * This method filters requests to check for JWT authentication.
   *
   * @param request the incoming HTTP request
   * @param response the outgoing HTTP response
   * @param filterChain the filter chain for further processing
   * @throws ServletException if an error occurs during request processing
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain)
          throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization"); // Retrieve the authorization header

    String jwt = null;
    String email = null;

    // Check if the authorization header is present and starts with "Bearer "
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7); // Extract the JWT from the header
      email = jwtTokenUtil.extractUsername(jwt); // Extract the username from the JWT
    }

    // If the email is not null and no authentication is present in the security context
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(email); // Load user details

      // Validate the token and set authentication in the security context
      if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()); // Create authentication token
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication in the security context
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
      }
    }
    filterChain.doFilter(request, response); // Proceed with the filter chain
  }
}
