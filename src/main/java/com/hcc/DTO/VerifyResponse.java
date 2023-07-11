package com.hcc.DTO;

public class VerifyResponse {
    private String email;
    private Long id;

    private Boolean isVerified;
    private String code;


    public VerifyResponse() {
    }

    public VerifyResponse(String email) {
        this.email = email;
    }

    public VerifyResponse(String email, Long id, Boolean isVerified, String code) {
        this.email = email;
        this.id = id;
        this.isVerified = isVerified;
        this.code = code;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
