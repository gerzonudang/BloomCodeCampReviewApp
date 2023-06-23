package com.hcc.DTO;

public class AuthenticationResponse {
    private final String token;
    private final String username;

    public AuthenticationResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }
}

