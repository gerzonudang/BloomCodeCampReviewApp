package com.hcc.entities;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private Long id;
    private String authority;
    private User user;

    public Authority() {
    }

    public Authority(String authority, User user) {
        this.authority = authority;
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
