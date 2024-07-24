package org.example.userauthenticationservice_june2024.security;

import org.example.userauthenticationservice_june2024.models.User;
import org.example.userauthenticationservice_june2024.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByEmail(email);

        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("bad credentials");
        }

        User user = userOptional.get();

        return new CustomUserDetails(user);
    }
}
