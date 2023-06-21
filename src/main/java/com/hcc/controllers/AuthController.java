package com.hcc.controllers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.hcc.DTO.AuthResponse;
import com.hcc.DTO.LoginRequest;
import com.hcc.DTO.TokenRequest;
import com.hcc.entities.User;
import com.hcc.services.AuthService;
import com.hcc.utils.jwtUtil;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private jwtUtil jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) { //AuthResponse //@Valid
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
        System.out.println(encodedPassword);
        System.out.println(loginRequest.getUsername()  + " " + loginRequest.getPassword() + "usernamepassword");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = null;
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //user = new User(loginRequest.getUsername(), loginRequest.getPassword());
            try {
                user = (User) authentication.getPrincipal();
            } catch (Exception e) {
                System.out.println("Casting Principal to user " + e.getMessage());
                e.printStackTrace(); // Optionally print the stack trace for more detailed information
            }

            // Generate JWT token
           //String token = jwtUtils.generateToken( (User)authentication);
            try {
                String token = jwtUtils.generateToken(user);
                System.out.println(token + " token generated");
                //return ResponseEntity.ok("Post good is authen");
                AuthResponse authResponse = new AuthResponse(token, "Login successful");
                //return ResponseEntity.ok(authResponse);
            } catch (Exception e) {
                // Log the error message or perform error handling actions
                System.out.println("Error generating JWT token: " + e.getMessage());
                e.printStackTrace(); // Optionally print the stack trace for more detailed information
            }

            //return ResponseEntity.ok(authResponse);
        } else {
            // Authentication failed
            //AuthResponse authResponse = new AuthResponse(token, "Login successful");
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Authenticate the user


//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Generate the token
//        // Get the authenticated user
//        User user = (User) authentication.getPrincipal();
//        String token = jwtUtils.generateToken(user);
//////
//////        // Return the token in the response
//       AuthResponse authResponse = new AuthResponse(token);
//        //return ResponseEntity.ok(authResponse);
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        try {
//            String hql = "SELECT * FROM Users WHERE username = :username";
//            Query<User> query = session.createQuery(hql, User.class);
//            query.setParameter("username",loginRequest.getUsername() );
//            List<User> users = query.getResultList();
//            System.out.println(users.size() + "users size");
//            // Process the retrieved users as needed
//
//        } finally {
//            session.close();
//        }
        //return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        return ResponseEntity.ok(encodedPassword);
    }

//    @PostMapping("/validate")
//    public ResponseEntity<AuthResponse> validateToken(@RequestBody TokenRequest tokenRequest ) {
//        // Retrieve the token
//        String token = tokenRequest.getToken();
//
//        // Retrieve the UserDetails object
//        UserDetails userDetails = authService.getUserDetailsFromToken(token);
//
//        if (userDetails != null) {
//            Boolean isValid = jwtUtils.validateToken(token, userDetails);
//            if (isValid) {
//                return ResponseEntity.ok(new AuthResponse("Token is valid"));
//            }
//        }
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    }

    public String generateJwtToken(Authentication authentication) {
        // Get the user principal from the authentication object
        User userPrincipal = (User) authentication.getPrincipal();

        // Set the claims for the JWT token
        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put("userId", userPrincipal.getId());

        // Set the expiration time for the token (e.g., 1 hour from now)
        Date expirationDate = new Date(System.currentTimeMillis() + 3600 * 1000);

        // Generate the JWT token with the claims, signing key, and expiration time
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, "test") // Replace "yourSecretKey" with your own secret key
                .compact();

        return token;
    }
    @PostMapping("/error")
    public ResponseEntity<?> error(@RequestBody LoginRequest loginRequest) { //AuthResponse //@Valid

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());
        System.out.println(encodedPassword +  " mypassword");
            return ResponseEntity.ok(encodedPassword + " error though");
    }
}
