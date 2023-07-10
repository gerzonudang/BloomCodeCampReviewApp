package com.hcc.DTO;

public class VerifyResponse {
    private String email;
    private Long id;

    private Boolean isVerified;


    public VerifyResponse() {
    }

    public VerifyResponse(String email) {
        this.email = email;
    }

    public VerifyResponse(String email, Long id, Boolean isVerified) {
        this.email = email;
        this.id = id;
        this.isVerified = isVerified;
    }

    public VerifyResponse(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }
}
