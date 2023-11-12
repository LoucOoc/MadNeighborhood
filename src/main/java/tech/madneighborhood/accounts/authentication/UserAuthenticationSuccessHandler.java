package tech.madneighborhood.accounts.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import tech.madneighborhood.accounts.entity.User;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final int TOKEN_LENGTH = 32; // Adjust the length of the token as needed

    public static String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(randomBytes);
        return "example_token"; //Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Success Handler Called");
        String token = generateToken();
        Long userId = ((User) authentication.getPrincipal()).getId();
        UserAuthenticationManager.addToken(token, userId);

        Cookie personalIdCookie = new Cookie("personal_id", token);
        response.addCookie(personalIdCookie);

        Cookie userIdCookie = new Cookie("user_id", userId.toString());
        response.addCookie(userIdCookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
