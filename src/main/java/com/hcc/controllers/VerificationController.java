package com.hcc.controllers;


import com.hcc.DTO.AssignmentResponse;
import com.hcc.DTO.VerifyResponse;
import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/account")
public class VerificationController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/generate-code")
    public ResponseEntity<?> generate(@AuthenticationPrincipal User user) {
        // Use authenticatedUser object
        // ...
        try {
            System.out.println("get_id "+ user.getUsername());

            if(!user.isVerified()) {
                UUID randomId = UUID.randomUUID();
                System.out.println(randomId.toString());
                user.setVerificationCode(randomId.toString());
                // Set the verification expiry time
                LocalDateTime verificationTime = LocalDateTime.now();
                LocalDateTime expiryTime = verificationTime.plus(1, ChronoUnit.MINUTES);
                user.setVerificationExpiry(expiryTime);
                userRepo.updateUserById(user.getId(), user);
                EmailService email = new EmailService(user.getVerificationCode());
                email.send();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ResponseEntity.ok(new VerifyResponse(user.getUsername(), user.getId(), user.isVerified(), user.getVerificationCode()));
    }


    @GetMapping("/verify/{code}")
    public ResponseEntity<?> verify(@PathVariable("code") String code, Model model){
        // Use authenticatedUser object
        // ...
        try {
            System.out.println(code + " code");
            User user = userRepo.findByVerificationCode(code);

            System.out.println("get_id "+ user.getUsername());

            if(!user.isVerified() && isVerificationCodeExpired(user) ) {
//                UUID randomId = UUID.randomUUID();
//                System.out.println(randomId.toString());
//                user.setVerificationCode(randomId.toString());
                // Set the verification expiry time
                LocalDateTime verificationTime = LocalDateTime.now();
                LocalDateTime expiryTime = verificationTime.plus(1, ChronoUnit.MINUTES);
                user.setVerified(true);
                userRepo.updateUserById(user.getId(), user);

                // Set the message based on verification status
                String message = user.isVerified() ? "Email verification successful" : "Email verification failed";

                // Add the message to the model
                model.addAttribute("message", message);

                return ResponseEntity.ok(new VerifyResponse(user.getUsername(), user.getId(), user.isVerified(), user.getVerificationCode()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        String message =  "Email verification failed";
        model.addAttribute("message", message);
        return ResponseEntity.ok(model);
    }
    public boolean isVerificationCodeExpired(User user) {
        LocalDateTime verificationExpiry = user.getVerificationExpiry();
        LocalDateTime currentTime = LocalDateTime.now();

        return verificationExpiry.isBefore(currentTime);
    }

}
