package com.hcc.DTO;

public class AuthResponse {
    private String token;
    private String messageResult;
    public AuthResponse(String token, String s) {
        this.token = token;
        this.messageResult = s;
    }
}
