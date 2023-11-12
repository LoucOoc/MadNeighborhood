package tech.madneighborhood.accounts;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.madneighborhood.accounts.dto.UserInfo;
import tech.madneighborhood.accounts.dto.UserLogin;
import tech.madneighborhood.accounts.dto.UserSignup;
import tech.madneighborhood.accounts.entity.User;
import tech.madneighborhood.accounts.service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class AuthController {


    private UserService userService;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationManager authenticationManager;
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registration(@Valid @ModelAttribute("user") UserSignup userDto,
                               BindingResult result,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", "Email already exists",
                    "There is already an account registered with the same email");
        }
        System.out.println(userDto);

        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            // set response error message
            return;
        }

        System.out.println("Saving user: " + userDto);
        userService.saveUser(userDto);
        return;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @ModelAttribute("user") UserLogin userLogin,
                         BindingResult result,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        System.out.println(userLogin.getEmail());
        System.out.println(userService.findAllUsers());
        User existingUser = userService.findUserByEmail(userLogin.getEmail());
        System.out.println(existingUser);

        if (existingUser == null) {
            result.rejectValue("email", "Email doesn't exists",
                    "There is not an account registered with the email");
        }

        if (result.hasErrors()) {
            System.out.println("Error in login");
            System.out.println(result.getAllErrors());
            return ResponseEntity.badRequest().build();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            successHandler.onAuthenticationSuccess(request, response, authentication);
            return ResponseEntity.ok().build();
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        @PostMapping("/login")
    public void login(@Valid @ModelAttribute("user") UserLogin userLogin,
                         BindingResult result,
                         HttpServletRequest request,
                         HttpServletResponse response) {
    public ResponseEntity<String> login(@RequestParam("email") String email, @RequestParam("password") String password,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        System.out.println(email);
        System.out.println(userService.findAllUsers());
        User existingUser = userService.findUserByEmail(email);
        System.out.println(existingUser);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            successHandler.onAuthenticationSuccess(request, response, authentication);
            ResponseEntity<String> entity = ResponseEntity.ok().build();

            entity.getHeaders().add("Test1", "Test2");
            return entity;
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
     */

    @GetMapping("/loginpage")
    public String loginPage() {
        return "login";
    }


    // handler method to handle list of users
    @GetMapping("/users")
    public List<UserInfo> users() {
        return userService.findAllUsers();
    }

}
