package org.example.userauthenticationservice_june2024.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthenticationservice_june2024.models.Session;
import org.example.userauthenticationservice_june2024.models.SessionState;
import org.example.userauthenticationservice_june2024.models.User;
import org.example.userauthenticationservice_june2024.repos.SessionRepo;
import org.example.userauthenticationservice_june2024.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecretKey secretKey;

    public User signup(String email,String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()) {
            return null;
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(user);
        return user;
    }

    public Pair<User,MultiValueMap<String,String>> login(String email,String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())) {
            return null;
        }

        //Token Generation

//        String message = "{\n" +
//                "   \"email\": \"anurag@scaler.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"25thJuly2024\"\n" +
//                "}";

        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id__",user.getId());
        claims.put("roles",user.getRoles());
        claims.put("email",user.getEmail());
        long nowInMillis = System.currentTimeMillis();
        claims.put("iat",nowInMillis);
        claims.put("exp",nowInMillis+1000000);

        //byte[] content = message.getBytes(StandardCharsets.UTF_8);
        //MacAlgorithm algorithm = Jwts.SIG.HS256;
        //SecretKey secretKey = algorithm.key().build();
        //String token = Jwts.builder().content(content).signWith(secretKey).compact();
        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);

        Session session = new Session();
        session.setSessionState(SessionState.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepo.save(session);

        return new Pair<User,MultiValueMap<String,String>>(user,headers);
    }

    public Boolean validateToken(String token,Long userId) {
        System.out.println(token);
        Optional<Session> optionalSession = sessionRepo.findByTokenAndUser_Id(token,userId);
        if(optionalSession.isEmpty()) {
            System.out.println("Token mismatch");
            return false;
        }

        Session session = optionalSession.get();
        String storedToken = session.getToken();


        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(storedToken).getPayload();

        Long tokenExpiry = (Long)claims.get("exp");

        Long currentTime = System.currentTimeMillis();

        System.out.println(tokenExpiry);
        System.out.println(currentTime);

        if(currentTime > tokenExpiry) {
            System.out.println(
                    "Token is expired");
            //set state to expired in my DB
            return false;
        }


        //Till this point, it's good to go

        User user = userRepo.findById(userId).get();
        String email = user.getEmail();
        String tokenEmail = (String)claims.get("email");
        if(!email.equals(tokenEmail)) {
            System.out.println("email mismatch");
            return false;
        }

        return true;
    }

//    public String generateRefreshToken(String token) {
//        Session session = sessionRepo.findByToken(token).get();
//        User user = session.getUser();
//
//        Map<String,Object> claims = new HashMap<>();
//        claims.put("user_id__",user.getId());
//        claims.put("roles",user.getRoles());
//        claims.put("email",user.getEmail());
//        long nowInMillis = System.currentTimeMillis();
//        claims.put("iat",nowInMillis);
//        claims.put("exp",nowInMillis+1000000);
//
//
//    }
}





//TOKEN VALIDATION
//exact token which we are getting in request - match that with
// token stored into our database

//decrypt token -> to get expiry
//if it's not expired, then it's valid
