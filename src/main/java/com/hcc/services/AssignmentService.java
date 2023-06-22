package com.hcc.services;

import com.hcc.entities.Assignment;
import com.hcc.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(Long id) {
        return (Assignment) assignmentRepository.findById(id).orElse(null);
    }

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Long id, Assignment assignment) {
        Assignment existingAssignment = (Assignment) assignmentRepository.findById(id).orElse(null);
        if (existingAssignment != null) {
            existingAssignment.setStatus(assignment.getStatus());
            existingAssignment.setReviewVideoUrl(assignment.getReviewVideoUrl());
            // Set other properties as needed

            return assignmentRepository.save(existingAssignment);
        }
        return null;
    }

    // Add other methods as needed for assignment-related operations
}
