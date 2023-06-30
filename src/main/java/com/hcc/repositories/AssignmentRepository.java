package com.hcc.repositories;

import com.hcc.entities.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAll();

    Optional<Assignment> findById(Long id);
    List<Assignment> findByUserId(Long userId);
    List<Assignment> findByCodeReviewerId(Long codeReviewerId);
    Assignment save(Assignment assignment);
}
