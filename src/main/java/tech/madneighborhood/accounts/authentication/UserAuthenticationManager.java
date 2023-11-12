package tech.madneighborhood.accounts.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import tech.madneighborhood.accounts.entity.User;
import tech.madneighborhood.accounts.service.UserService;
import tech.madneighborhood.config.SpringSecurity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class UserAuthenticationManager implements AuthenticationManager {

    private static final Map<String, Long> token_to_user = new ConcurrentHashMap<>();
    private static final Map<Long, String> user_to_token = new ConcurrentHashMap<>();

    public static Logger logger = Logger.getLogger(UserAuthenticationManager.class.getName());

    public static void addToken(String token, Long userId) {
        logger.info("Adding token " + token + " for user " + userId);
        token_to_user.put(token, userId);
        user_to_token.put(userId, token);
    }

    public static String getToken(Long userId) {
        return user_to_token.get(userId);
    }

    public static Long getUserId(String token) {
        return token_to_user.get(token);
    }


    @Autowired
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.findUserByEmail(email);
        if(user == null || !passwordMatches(user.getPassword(), password)) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return new UsernamePasswordAuthenticationToken(user, password, null);
    }

    private boolean passwordMatches(String storedPassword, String providedPassword) {
        // Implement your password comparison logic here
        // Use a secure method of comparing passwords such as BCryptPasswordEncoder
        return SpringSecurity.passwordEncoder().matches(providedPassword, storedPassword);
    }
}
