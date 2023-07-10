package com.hcc.DTO;

public class VerifyRequest {
    private String email;
    private Boolean isVerified = false;
    private String verficationCode;

    public VerifyRequest() {
    }

    public VerifyRequest(String email) {
        this.email = email;
    }

    public VerifyRequest(String email, Boolean isVerified, String verficationCode) {
        this.email = email;
        this.isVerified = isVerified;
        this.verficationCode = verficationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getVerficationCode() {
        return verficationCode;
    }

    public void setVerficationCode(String verficationCode) {
        this.verficationCode = verficationCode;
    }
}
