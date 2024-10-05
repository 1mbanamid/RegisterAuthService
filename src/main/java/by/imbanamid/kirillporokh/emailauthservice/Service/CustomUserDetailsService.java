package by.imbanamid.kirillporokh.emailauthservice.Service;

import by.imbanamid.kirillporokh.emailauthservice.Entity.RegisteredUser;
import by.imbanamid.kirillporokh.emailauthservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repository to access user data

    /**
     * Load user details by email.
     *
     * @param email The email of the user to be loaded.
     * @return UserDetails containing the user's information.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        RegisteredUser registeredUser = userRepository.findByEmail(email); // Retrieve user by email
        if (registeredUser == null) {
            throw new UsernameNotFoundException(email + " user not found"); // Throw exception if user is not found
        }

        // Build UserDetails object with user information
        return org.springframework.security.core.userdetails.User
                .withUsername(registeredUser.getEmail())
                .password(registeredUser.getPassword())
                .disabled(!registeredUser.isVerified()) // Disable user if not verified
                .authorities("USER") // Set user authorities
                .build();
    }
}
