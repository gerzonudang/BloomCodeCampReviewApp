package com.hcc.services;

import com.hcc.utils.jwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement the logic to retrieve the user details from the database or any other source
        // This method is responsible for loading the user details based on the provided username

        // Here's an example implementation using hard-coded user data
        if ("john".equals(username)) {
            // You can replace this with the logic to retrieve the user from the database
            // Assuming the user has the username "john" and password "password"
            return User.builder()
                    .username("john")
                    .password("$2a$10$msWQ4/yt6lXArp3Aanxt9OdXf9YUIGqYdzE82BEhYDc7JLKvZ0D6u") // Encrypted password: password
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

//    public UserDetails getUserDetailsFromToken(String token) {
//        // Implement the logic to retrieve the user details based on the token
//        // Here's an example implementation using the JwtUtil class
//
//        // Parse the token to extract the username
//        //String username = jwtUtil.extractUsername(token);
//
//        // Load the user details based on the username
//        //UserDetails userDetails = loadUserByUsername(username);
//
//        return userDetails;
//    }
}
