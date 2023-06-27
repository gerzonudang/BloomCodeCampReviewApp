package com.hcc.DTO;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private final String token;
    private final String username;

    private final String userType;
//    private final List<Authority> authorityList;



    public AuthenticationResponse(String token, String username, String userType) {
        this.token = token;
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
//    public List<Authority> getAuthorityList() {
//        return authorityList;
//    }

}

