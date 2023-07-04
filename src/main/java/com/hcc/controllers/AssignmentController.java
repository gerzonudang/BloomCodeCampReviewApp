package com.hcc.controllers;

import com.hcc.DTO.AssignmentResponse;
import com.hcc.DTO.UserResponse;
import com.hcc.entities.Assignment;
import com.hcc.entities.User;
import com.hcc.services.AssignmentService;
import com.hcc.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    // Retrieve all assignments
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();

        if (assignments.isEmpty() || assignments == null) {
            return ResponseEntity.noContent().build();
        }

        List<AssignmentResponse> assignmentResponses = new ArrayList<>();

        for (Assignment assignment : assignments) {
            UserResponse user = new UserResponse();
            UserResponse codeReviewer = new UserResponse();

            codeReviewer.setUsername(assignment.getCodeReviewer().getUsername() == null ? "" : assignment.getCodeReviewer().getUsername());
            user.setUsername(assignment.getUser().getUsername());
            user.setId(assignment.getUser().getId());
            codeReviewer.setId(assignment.getCodeReviewer().getId());

            AssignmentResponse response = new AssignmentResponse(
                    assignment.getId(),
                    assignment.getStatus(),
                    assignment.getNumber(),
                    assignment.getGithubUrl(),
                    assignment.getBranch(),
                    assignment.getReviewVideoUrl(),
                    user,
                    codeReviewer
            );

            assignmentResponses.add(response);
        }

        return ResponseEntity.ok(assignmentResponses);
    }

    // Retrieve an assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.getAssignmentById(id);

        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assignment);
    }

    // Retrieve assignments by user ID
    @GetMapping("/learner/{learner_id}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentByUserId(@PathVariable("learner_id") Long learner_id) {
        List<Assignment> assignments = assignmentService.findByUserId(learner_id);

        if (assignments.isEmpty() || assignments == null) {
            return ResponseEntity.noContent().build();
        }

        List<AssignmentResponse> assignmentResponses = new ArrayList<>();

        for (Assignment assignment : assignments) {
            Optional<User> reviewer = Optional.ofNullable(assignment.getCodeReviewer());

            UserResponse user = new UserResponse();
            UserResponse codeReviewer = new UserResponse();

            codeReviewer.setUsername(reviewer.isPresent() ? assignment.getCodeReviewer().getUsername() : "");
            user.setUsername(assignment.getUser().getUsername());
            user.setId(assignment.getUser().getId());

            if (reviewer.isPresent()) {
                codeReviewer.setId(assignment.getCodeReviewer().getId());
            }

            AssignmentResponse response = new AssignmentResponse(
                    assignment.getId(),
                    assignment.getStatus(),
                    assignment.getNumber(),
                    assignment.getGithubUrl(),
                    assignment.getBranch(),
                    assignment.getReviewVideoUrl(),
                    user,
                    codeReviewer
            );

            assignmentResponses.add(response);
        }

        return ResponseEntity.ok(assignmentResponses);
    }

    // Create a new assignment
    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody Assignment assignment) {
        System.out.println("submitting post" + assignment.getUser().getUsername());

        User user = (User) userDetailService.loadUserByUsername(assignment.getUser().getUsername());
        assignment.setUser(user);

        Assignment createdAssignment = assignmentService.createAssignment(assignment);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());

        AssignmentResponse assignmentResponse = new AssignmentResponse(
                assignment.getId(),
                assignment.getStatus(),
                assignment.getNumber(),
                assignment.getGithubUrl(),
                assignment.getBranch(),
                assignment.getReviewVideoUrl(),
                userResponse
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentResponse);
    }

    // Update an existing assignment
    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable("id") Long id, @RequestBody Assignment assignment) {
        Assignment updatedAssignment = assignmentService.updateAssignment(id, assignment);

        if (updatedAssignment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedAssignment);
    }
}
