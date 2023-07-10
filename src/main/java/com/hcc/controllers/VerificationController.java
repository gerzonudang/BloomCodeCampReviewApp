package com.hcc.controllers;


import com.hcc.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RestController
@RequestMapping("api/account")
public class VerificationController {
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/verify/{email}")
    public ResponseEntity<?> verify(@PathVariable("email") String email) {
        // Use authenticatedUser object
        // ...
        try {
            System.out.println("get_id "+ email);
            User user = (User) userDetailsService.loadUserByUsername(email);
            if(!user.isVerified()) {
                UUID randomId = UUID.randomUUID();
                System.out.println(randomId.toString());
                user.setVerificationCode(randomId.toString());
                // Set the verification expiry time
                LocalDateTime verificationTime = LocalDateTime.now();
                LocalDateTime expiryTime = verificationTime.plus(1, ChronoUnit.MINUTES);
                user.setVerificationExpiry(expiryTime);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.ok("nice");
    }

}
