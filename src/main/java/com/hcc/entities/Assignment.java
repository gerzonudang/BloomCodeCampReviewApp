package com.hcc.entities;

import javax.persistence.*;

@Entity
@Table(name="assignments")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private String status;

    @Column(name = "number")
    private Integer number;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "branch")
    private String branch;

    @Column(name = "code_review_video_url")
    private String reviewVideoUrl;

//    @Column(name = "user_id")
//    private Long userId;

//    @Column(name = "code_reviewer_id")
//    private Long codeReviewerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_reviewer_id")
    private User codeReviewer;

    public Assignment() {

    }

    public Assignment(String status, Integer number, String githubUrl, String branch, String reviewVideoUrl) {
        this.status = status;
        this.number = number;
        this.githubUrl = githubUrl;
        this.branch = branch;
        this.reviewVideoUrl = reviewVideoUrl;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
