package com.hcc.controllers;

import com.hcc.DTO.AuthenticationResponse;
import com.hcc.DTO.LoginRequest;
import com.hcc.entities.Authority;
import com.hcc.entities.User;
import com.hcc.repositories.AuthorityRepository;
import com.hcc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthorityRepository authRepo;

    @Autowired
    private JWTUtils jwtUtils;

    // Handle login request
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = null;
        String userType = "";
        List<Authority> authorityList = null;
        Long userId = 0L;

        try {
            // Perform authentication using the provided username and password
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User user = (User) authentication.getPrincipal();
                userId = user.getId();
                authorityList = authRepo.findByUserId(user.getId());

                userType = authorityList.get(0).toString();
                System.out.println(authorityList.get(0).toString() + " authorities " + user.getId());
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Generate JWT token and return it along with user details
        final String token = jwtUtils.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new AuthenticationResponse(token, userId, loginRequest.getUsername(), userType));
    }

    // Endpoint for testing purposes
    @GetMapping("/login")
    public ResponseEntity<?> showLogin() {
        // Generate the BCrypt encoded password for demonstration
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("mypassword");
        return ResponseEntity.ok(encodedPassword + " your password");
    }
}
