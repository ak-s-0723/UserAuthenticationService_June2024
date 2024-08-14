package org.example.userauthenticationservice_june2024.controllers;

import org.example.userauthenticationservice_june2024.dtos.UserDto;
import org.example.userauthenticationservice_june2024.models.User;
import org.example.userauthenticationservice_june2024.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public UserDto getUserDetails(@PathVariable Long id) {
        User user = userService.getUser(id);
        System.out.println("USER = "+user.getEmail());
        return from(user);
    }

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        //userDto.setRoles(user.getRoles());
        return userDto;
    }
}
