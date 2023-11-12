package tech.madneighborhood.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.madneighborhood.accounts.authentication.UserAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserAuthenticationSuccessHandler authenticationSuccessHandler;

    @Value("${server.ssl.enabled:#{false}}")
    private boolean secure;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("*");
            }
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .requiresChannel(channel -> {
            if(secure) {
                channel.anyRequest().requiresSecure();
            }else{
                channel.anyRequest().requiresInsecure();
            }
        })
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/login").permitAll()
                                .requestMatchers("/loginpage").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/registerpage").permitAll()
                                .requestMatchers("/posts").permitAll()
                                .requestMatchers("/create_post").permitAll()
                                .anyRequest().permitAll().
                )
                .formLogin((customizer)->
                        customizer
                                .loginPage("/loginpage")
                                .successHandler(authenticationSuccessHandler)
                                .permitAll()
                ).logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                ).sessionManagement((customizer) ->
                        customizer
                                .sessionFixation().migrateSession()
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .invalidSessionUrl("/loginpage")
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)

                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
