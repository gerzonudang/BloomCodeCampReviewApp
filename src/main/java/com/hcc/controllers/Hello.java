package com.hcc.controllers;

import com.hcc.entities.Assignment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class Hello {

    @GetMapping("/")
    public ResponseEntity<String> getAssignmentById() {
        // Implement the logic to fetch an assignment by ID
       // Assignment assignment = assignmentService.getAssignmentById(id);
//        if (assignment == null) {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok("test");
    }
    @PostMapping
    public ResponseEntity<String> login() {
        // Implement the logic to fetch an assignment by ID
        // Assignment assignment = assignmentService.getAssignmentById(id);
//        if (assignment == null) {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok("post working...");
    }
    @GetMapping("/login")
    public ResponseEntity<String> getLogin() {
        // Implement the logic to fetch an assignment by ID
        // Assignment assignment = assignmentService.getAssignmentById(id);
//        if (assignment == null) {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok("login test get");
    }
}
