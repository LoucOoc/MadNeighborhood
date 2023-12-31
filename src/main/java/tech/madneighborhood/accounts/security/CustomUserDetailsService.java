package tech.madneighborhood.accounts.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.madneighborhood.accounts.entity.User;
import tech.madneighborhood.accounts.repository.UserRepository;

import java.util.Collection;
import java.util.LinkedList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities() {
        return new LinkedList<>(){{
            add(new SimpleGrantedAuthority(""));
        }};
        /*
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

         */
    }
}
