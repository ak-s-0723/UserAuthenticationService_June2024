package org.example.userauthenticationservice_june2024.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice_june2024.dtos.*;
import org.example.userauthenticationservice_june2024.exceptions.InvalidTokenException;
import org.example.userauthenticationservice_june2024.exceptions.UserAlreadyExistsException;
import org.example.userauthenticationservice_june2024.models.User;
import org.example.userauthenticationservice_june2024.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    //Signup
    //Login
    //ForgetPassword
    //Logout
    //...

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        try {

            User user = authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword());

            if (user == null) {
                throw new UserAlreadyExistsException("Please try out with different email");
            }

            return new ResponseEntity<>(from(user),HttpStatus.CREATED);
        }
        catch (UserAlreadyExistsException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User, MultiValueMap<String,String>> userWithHeaders = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        User user = userWithHeaders.a;

        if(user == null) {
            throw new RuntimeException("BAD CREDENTIALS");
        }

        return new ResponseEntity<>(from(user),userWithHeaders.b,HttpStatus.OK);
    }

    @PostMapping("/validate")
     public ResponseEntity<Boolean> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        try {
            System.out.println(validateTokenRequestDto.getToken());
            Boolean response = authService.validateToken(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
            if (response == false) {
                throw new InvalidTokenException("Either Token is stale or invalid");
            }
            return new ResponseEntity<>(response,HttpStatus.OK);

        }catch(InvalidTokenException exception) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
     }

    public ResponseEntity<Boolean> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        return null;
    }

    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordDto forgetPasswordDto) {
      return null;
    }

    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
