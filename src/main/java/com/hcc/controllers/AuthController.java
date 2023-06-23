package com.hcc.controllers;

import com.hcc.DTO.AuthenticationResponse;
import com.hcc.DTO.LoginRequest;
import com.hcc.entities.User;
import com.hcc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) { //AuthResponse //@Valid
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User user = (User) authentication.getPrincipal();
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        //final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        //final String token = jwtUtils.generateToken((User) userDetails);
        //return ResponseEntity.ok(new AuthenticationResponse(token, userDetails.getUsername()));
        final String token = jwtUtils.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new AuthenticationResponse(token, loginRequest.getUsername()));
    }
    @GetMapping("/login")
    public ResponseEntity<?> showLogin() { //AuthResponse //@Valid
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("mypassword");
        return ResponseEntity.ok(encodedPassword + " your password");
    }
}
