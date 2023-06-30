package com.hcc.DTO;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private final String token;
    private final Long userId;
    private final String username;
    private final String userType;
//    private final List<Authority> authorityList;



    public AuthenticationResponse(String token, Long userId, String username, String userType) {
        this.token = token;
        this.userId = userId;
        this.username = username;
//        this.userType = userType;
//        this.authorityList = authorityList;
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public Long getUserId() {
        return userId;
    }
}

