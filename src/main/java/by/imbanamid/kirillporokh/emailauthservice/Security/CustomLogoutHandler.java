package by.imbanamid.kirillporokh.emailauthservice.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * CustomLogoutHandler is responsible for handling user logout events.
 * This class implements the LogoutHandler interface to provide custom logout logic.
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

    /**
     * Logs out the user by performing actions upon logout.
     *
     * @param request the HttpServletRequest that represents the client's request
     * @param response the HttpServletResponse that represents the response to the client
     * @param authentication the Authentication object representing the current user's authentication
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName(); // Retrieve the username of the authenticated user
            System.out.println("Logged out user: " + username); // Log the logout event
        }
        // Additional logout actions can be added here (e.g., clearing sessions, revoking tokens)
    }
}
