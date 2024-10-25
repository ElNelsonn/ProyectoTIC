package uy.edu.um.wtf.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uy.edu.um.wtf.entities.Client;
import uy.edu.um.wtf.entities.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uy.edu.um.wtf.entities.User;
import uy.edu.um.wtf.exceptions.EntityNotFoundException;
import uy.edu.um.wtf.repository.UserRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = determineRole(user);

        GrantedAuthority authority = new SimpleGrantedAuthority(role);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }

    private String determineRole(User user) {

        if (user instanceof Administrator) {
            return "ROLE_ADMIN";

        } else {
            return "ROLE_CLIENT";
        }
    }



}
