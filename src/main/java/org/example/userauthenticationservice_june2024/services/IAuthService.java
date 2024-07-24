package org.example.userauthenticationservice_june2024.services;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice_june2024.models.User;
import org.springframework.util.MultiValueMap;

public interface IAuthService {
    User signup(String email, String password);
    Pair<User, MultiValueMap<String,String>> login(String email, String password);

    Boolean validateToken(String token,Long userId);
}
