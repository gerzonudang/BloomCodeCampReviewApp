package com.hcc.controllers;

import com.hcc.entities.User;
import com.hcc.repositories.UserRepository;
import com.hcc.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/email")
public class EmailVerificationController {

    @Autowired
    private UserRepository userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("code") String code) {
        try {
            if (code == null || code.isEmpty()) {
                return ResponseEntity.badRequest().body("A verification code is required");
            }

            User user = userService.findByVerificationCode(code);

            if (user == null) {
                return ResponseEntity.badRequest().body("Invalid verification code");
            }

            Date currentTime = new Date();
            long timeElapsed = (currentTime.getTime() - user.getVerificationTimeGenerated().getTime()) / (60 * 1000);
            if (timeElapsed > user.getVerificationExpiresIn()) {
                return ResponseEntity.badRequest().body("Verification code has expired");
            }

            user.setVerified(true);
            user.setVerificationCode("");
            userService.save(user);

            return ResponseEntity.ok("Email verified successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during email verification");
        }
    }
}
