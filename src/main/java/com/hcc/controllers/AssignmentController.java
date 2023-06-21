package com.hcc.controllers;

import com.hcc.entities.Assignment;
import com.hcc.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired

    private AssignmentService assignmentService;

    @GetMapping("/")
    public List<Assignment> getAllAssignments() {
        // Implement the logic to fetch all assignments
        return assignmentService.getAllAssignments();
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

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        // Implement the logic to create a new assignment
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssignment);
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
