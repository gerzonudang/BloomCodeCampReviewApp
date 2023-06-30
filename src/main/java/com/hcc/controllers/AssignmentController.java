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

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        if (assignments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<AssignmentResponse> assignmentResponses = new ArrayList<>();
        for (Assignment assignment : assignments) {
            UserResponse user = new UserResponse();
            UserResponse codeReviewer = new UserResponse();
            codeReviewer.setUsername(assignment.getCodeReviewer().getUsername()==null ? "" : assignment.getCodeReviewer().getUsername());
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



    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable("id") Long id) {
        // Implement the logic to fetch an assignment by ID
        Assignment assignment = assignmentService.getAssignmentById(id);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignment);
    }
    @GetMapping("/learner/{learner_id}")
    public ResponseEntity<Assignment> getAssignmentByUserId(@PathVariable("learner_id") Long learner_id) {
        // Implement the logic to fetch an assignment by ID
        Assignment assignment = assignmentService.getAssignmentById(learner_id);
        if (assignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assignment);
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody Assignment assignment) {
        // Implement the logic to create a new assignment
        System.out.println("submitting post"+ assignment.getUser().getUsername());
        User user = (User) userDetailService.loadUserByUsername(assignment.getUser().getUsername());
        assignment.setUser(user);
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        AssignmentResponse assignmentResponse = new AssignmentResponse(assignment.getId(), assignment.getStatus(),
                assignment.getNumber(), assignment.getGithubUrl(), assignment.getBranch(), assignment.getReviewVideoUrl(),
                userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable("id") Long id, @RequestBody Assignment assignment) {
        // Implement the logic to update an existing assignment
        Assignment updatedAssignment = assignmentService.updateAssignment(id, assignment);
        if (updatedAssignment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAssignment);
    }
}
