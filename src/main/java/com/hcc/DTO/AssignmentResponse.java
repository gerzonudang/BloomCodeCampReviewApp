package com.hcc.DTO;


import com.hcc.entities.User;

public class AssignmentResponse {
    private Long id;
    private String status;
    private Integer number;
    private String githubUrl;
    private String branch;
    private String reviewVideoUrl;
    private UserResponse user;
    private UserResponse codeReviewer;

// Constructors, getters, and setters

    public AssignmentResponse(Long id, String status, Integer number, String githubUrl, String branch, String reviewVideoUrl, UserResponse user, UserResponse codeReviewer) {
        this.id = id;
        this.status = status;
        this.number = number;
        this.githubUrl = githubUrl;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
        this.user = user;
        this.codeReviewer = codeReviewer;
    }

    //    // Constructor with all fields including IDs
//    public AssignmentResponse(Long id, String status, Integer number, String githubUrl, String branch, String reviewVideoUrl, Long userId, Long codeReviewerId) {
//        this(id, status, number, githubUrl, branch, reviewVideoUrl);
//        this.userId = userId;
//        this.codeReviewerId = codeReviewerId;
//    }
//    // Getters and setters for the fields

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public UserResponse getCodeReviewer() {
        return codeReviewer;
    }

    public void setCodeReviewer(UserResponse codeReviewer) {
        this.codeReviewer = codeReviewer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getReviewVideoUrl() {
        return reviewVideoUrl;
    }

    public void setReviewVideoUrl(String reviewVideoUrl) {
        this.reviewVideoUrl = reviewVideoUrl;
    }
}
