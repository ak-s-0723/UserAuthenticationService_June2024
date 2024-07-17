package org.example.userauthenticationservice_june2024.controllers;

import org.example.userauthenticationservice_june2024.dtos.LoginRequestDto;
import org.example.userauthenticationservice_june2024.dtos.SignupRequestDto;
import org.example.userauthenticationservice_june2024.dtos.UserDto;
import org.example.userauthenticationservice_june2024.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //Signup
    //Login
    //ForgetPassword
    //Logout
    //...

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto) {
        User user
                 = new User();
        user.setId(1L);
        return null;
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return null;
    }
}
