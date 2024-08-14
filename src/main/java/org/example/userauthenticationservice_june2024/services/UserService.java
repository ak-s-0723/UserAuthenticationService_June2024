package org.example.userauthenticationservice_june2024.services;

import org.example.userauthenticationservice_june2024.models.User;
import org.example.userauthenticationservice_june2024.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User getUser(Long id) {
       Optional<User> userOptional = userRepo.findById(id);
       if(userOptional.isPresent()) {
           return userOptional.get();
       }

       return null;
    }
}
